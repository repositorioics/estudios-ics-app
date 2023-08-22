package ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaSensoresCasoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.SensoresForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.SensoresFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.AreaAmbiente;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cuarto;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.CasaCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.SensorCaso;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;
import org.joda.time.DateMidnight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class NuevoSensorCasoActivity extends FragmentActivity implements
        PageFragmentCallbacks,
        ReviewFragment.Callbacks,
        ModelCallbacks {
	private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;
    private boolean mEditingAfterReview;
    private AbstractWizardModel mWizardModel;
    private boolean mConsumePageSelectedEvent;
    private Button mNextButton;
    private Button mPrevButton;
    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static CasaCohorteFamiliaCaso casaCaso = new CasaCohorteFamiliaCaso();
	private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	private SensoresFormLabels labels = new SensoresFormLabels();

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
        setContentView(R.layout.activity_data_enter);
        settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		infoMovil = new DeviceInfo(NuevoSensorCasoActivity.this);
        casaCaso = (CasaCohorteFamiliaCaso) getIntent().getExtras().getSerializable(Constants.CASA);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new SensoresForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        Page pageCuarto = mWizardModel.findByKey(labels.getHabitacionSensor());
        pageCuarto.setCasaCHF(casaCaso.getCasa().getCodigoCHF());
        Page pageArea = mWizardModel.findByKey(labels.getAreaSensor());
        pageArea.setCasaCHF(casaCaso.getCodigoCaso());

        DateMidnight dmDesde = new DateMidnight(casaCaso.getFechaInicio().getTime());
        DateMidnight dmHasta = new DateMidnight(new Date().getTime());
        NewDatePage pageFechaC = (NewDatePage)mWizardModel.findByKey(labels.getFechaSensor());
        pageFechaC.setRangeValidation(true, dmDesde, dmHasta);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (mPager.getCurrentItem() != position) {
                    mPager.setCurrentItem(position);
                }
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mStepPagerStrip.setCurrentPage(position);

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return; 
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    DialogFragment dg = new DialogFragment() {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            return new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.submit_confirm_message)
                                    .setPositiveButton(R.string.submit_confirm_button, new DialogInterface.OnClickListener() {
                                    	@Override
										public void onClick(DialogInterface arg0, int arg1) {
                                    		saveData();
										}
                                    })
                                    .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                                    	@Override
										public void onClick(DialogInterface arg0, int arg1) {
                                    		createDialog(EXIT);
										}
                                    })
                                    .create();
                        }
                    };
                    dg.show(getSupportFragmentManager(), "guardar_dialog");
                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });
        onPageTreeChanged(); 
    }
    
	@Override
	public void onBackPressed (){
		createDialog(EXIT);
	}
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case EXIT:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.exiting));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Finish app
					Bundle arguments = new Bundle();
					Intent i;
					if (casaCaso!=null) arguments.putSerializable(Constants.CASA , casaCaso);
					i = new Intent(getApplicationContext(),
							ListaSensoresCasoActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtras(arguments);
					startActivity(i);
					Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.err_cancel),Toast.LENGTH_LONG);
					toast.show();
					dialog.dismiss();
					finish();
				}
			});
			builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Do nothing
					dialog.dismiss();
				}
			});
			break;		
		default:
			break;
		}
		alertDialog = builder.create();
		alertDialog.show();
	}

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview
                    ? R.string.review
                    : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }
        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String key) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(Page page) {
    	updateModel(page);
    	updateConstrains();
        if (recalculateCutOffPage()) {
        	if (notificarCambios) mPagerAdapter.notifyDataSetChanged();
            updateBottomBar();
        }
        notificarCambios = true;
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            String clase = page.getClass().toString();
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }     
            if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NumberPage")) {
            	NumberPage np = (NumberPage) page;
            	String valor = np.getData().getString(NumberPage.SIMPLE_DATA_KEY);
        		if((np.ismValRange() && (np.getmGreaterOrEqualsThan() > Integer.valueOf(valor) || np.getmLowerOrEqualsThan() < Integer.valueOf(valor)))
        				|| (np.ismValPattern() && !valor.matches(np.getmPattern()))){
        			cutOffPage = i;
        			break;
        		}
            }
            if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.TextPage")) {
            	TextPage tp = (TextPage) page;
            	if (tp.ismValPattern()) {
            		String valor = tp.getData().getString(TextPage.SIMPLE_DATA_KEY);
            		if(!valor.matches(tp.getmPattern())){
            			cutOffPage = i;
            			break;
            		}
            	}
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }
    
    
    public void updateConstrains(){
        
    }
    
    public void updateModel(Page page){
    	try{
    		boolean visible = false;
            if (page.getTitle().equals(labels.getSensorSN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumSensor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnHabitacion()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getFechaSensor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRazonNoColocaSensor()), !visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
    		if (page.getTitle().equals(labels.getEnHabitacion())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getHabitacionSensor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAreaSensor()), !visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
    		
    	}catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void changeStatus(Page page, boolean visible){
    	String clase = page.getClass().toString();
    	if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage")){
    		SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.BarcodePage")){
    		BarcodePage modifPage = (BarcodePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.LabelPage")){
    		LabelPage modifPage = (LabelPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.TextPage")){
    		TextPage modifPage = (TextPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NumberPage")){
    		NumberPage modifPage = (NumberPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage")){
    		MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.DatePage")){
    		DatePage modifPage = (DatePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SelectParticipantPage")){
    		SelectParticipantPage modifPage = (SelectParticipantPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SelectCuartoPage")){
            SelectCuartoPage modifPage = (SelectCuartoPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SelectAreaAmbientePage")){
            SelectAreaAmbientePage modifPage = (SelectAreaAmbientePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NewDatePage")){
    		NewDatePage modifPage = (NewDatePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
    }
    
    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
    }
    
    public void saveData(){
		Map<String, String> mapa = mWizardModel.getAnswers();
        DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//Guarda las respuestas en un bundle
		Bundle datos = new Bundle();
		for (Map.Entry<String, String> entry : mapa.entrySet()){
			datos.putString(entry.getKey(), entry.getValue());
		}
        Date fechaSensor = null;
        try {
            String fecha = datos.getString(this.getString(R.string.fechaSensor));
            if (tieneValor(fecha)) fechaSensor = mDateFormat.parse(fecha);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
		//Abre la base de datos
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		estudiosAdapter.open();
		
		//Obtener datos del bundle para la habitacion
		String id = infoMovil.getId();
		
		String sensorSN = datos.getString(this.getString(R.string.sensorSN));
        String razonNoColocaSensor = datos.getString(this.getString(R.string.razonNoColocaSensor));
        String numero = datos.getString(this.getString(R.string.numSensor));
        SensorCaso sensorExistente = estudiosAdapter.getSensorCaso(CasosDBConstants.codigoCaso + "='"+casaCaso.getCodigoCaso()+"' and "+CasosDBConstants.numeroSensor+"='"+numero+"'",null);
        if (sensorExistente!=null){
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.sensor_exists), Toast.LENGTH_LONG);
            toast.show();
        }else {
            String habitacionSensor = datos.getString(this.getString(R.string.habitacionSensor));
            String areaSensor = datos.getString(this.getString(R.string.areaSensor));

            //Crea un nuevo SensorCaso
            SensorCaso sensor = new SensorCaso();
            sensor.setCodigoSensor(id);
            sensor.setCodigoCaso(casaCaso);
            sensor.setFechaColocacion(fechaSensor);

            if (tieneValor(sensorSN)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + sensorSN + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                sensor.setSensorSN(catSino.getCatKey());
            }
            sensor.setRazonNoColocaSensor(razonNoColocaSensor);

            if (tieneValor(habitacionSensor)) {
                Cuarto cuarto = estudiosAdapter.getCuarto(MainDBConstants.codigo + " = '" + habitacionSensor + "'", null);
                sensor.setCuarto(cuarto);
            }

            if (tieneValor(areaSensor)) {
                AreaAmbiente ambiente = estudiosAdapter.getAreaAmbiente(MainDBConstants.codigo + " = '" + areaSensor + "'", null);
                sensor.setArea(ambiente);
            } else {
                AreaAmbiente areaAmbiente = estudiosAdapter.getAreaAmbiente(CasosDBConstants.casa + "='"+sensor.getCuarto().getCasa().getCodigoCHF()
                        +"' and "+MainDBConstants.numeroCuarto+"='"+sensor.getCuarto().getCodigoHabitacion()+"'",null);
                sensor.setArea(areaAmbiente);
            }

            sensor.setNumeroSensor(numero);
            sensor.setRecordDate(new Date());
            sensor.setRecordUser(username);
            sensor.setDeviceid(infoMovil.getDeviceId());
            sensor.setEstado('0');
            sensor.setPasive('0');

            //Guarda el Sensor
            estudiosAdapter.crearSensorCaso(sensor);

            Bundle arguments = new Bundle();
            Intent i;
            if (casaCaso != null) arguments.putSerializable(Constants.CASA, casaCaso);
            i = new Intent(getApplicationContext(),
                    ListaSensoresCasoActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtras(arguments);
            startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        estudiosAdapter.close();
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }

            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public int getItemPosition(Object object) {
        	if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }
            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }

        @Override
        public int getCount() {
            return Math.min(mCutOffPage + 1, (mCurrentPageSequence != null ? mCurrentPageSequence.size() : 0) + 1);
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }
    }
}
