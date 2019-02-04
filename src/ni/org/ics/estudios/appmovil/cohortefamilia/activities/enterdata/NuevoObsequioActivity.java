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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuVisitaFinalCasoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuVisitaSeguimientoCasoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.ObsequioForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.ObsequioFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.ObsequioGeneral;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaFinalCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCaso;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuInfoActivity;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class NuevoObsequioActivity extends FragmentActivity implements
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
    private static VisitaSeguimientoCaso visita;
    private static VisitaFinalCaso visitaFinal;
    private static CasaCohorteFamilia casaChf;
    private static Integer codigoParticipante;
	private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	private ObsequioFormLabels labels = new ObsequioFormLabels();

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
		infoMovil = new DeviceInfo(NuevoObsequioActivity.this);
		if (getIntent().getExtras().getSerializable(Constants.VISITA)!=null) visita = (VisitaSeguimientoCaso) getIntent().getExtras().getSerializable(Constants.VISITA);
        if (getIntent().getExtras().getSerializable(Constants.VISITA_FINAL)!=null) visitaFinal = (VisitaFinalCaso) getIntent().getExtras().getSerializable(Constants.VISITA_FINAL);
        if (getIntent().getExtras().getSerializable(Constants.CASACHF)!=null) casaChf = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASACHF);
        codigoParticipante = getIntent().getIntExtra(Constants.PARTICIPANTE, -1);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new ObsequioForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);

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

        Page pagePart = mWizardModel.findByKey(labels.getPersonaRecibe());
        if (visita!=null) {
            pagePart.setCasaCHF(visita.getCodigoParticipanteCaso().getCodigoCaso().getCasa().getCodigoCHF());
        }else if (visitaFinal!=null){
            pagePart.setCasaCHF(visitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getCasa().getCodigoCHF());
        }else{
            pagePart.setCasaCHF(casaChf.getCodigoCHF());
        }

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
                    if (visita !=null) {
                        arguments.putSerializable(Constants.VISITA, visita);
                        i = new Intent(getApplicationContext(),
                                MenuVisitaSeguimientoCasoActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                    }else if (visitaFinal !=null){
                        arguments.putSerializable(Constants.VISITA_FINAL, visitaFinal);
                        i = new Intent(getApplicationContext(),
                                MenuVisitaFinalCasoActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                    }
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
        		if((np.ismValRange() && (np.getmGreaterOrEqualsThan() > Double.valueOf(valor) || np.getmLowerOrEqualsThan() < Double.valueOf(valor)))
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
            if (page.getTitle().equals(labels.getObsequioSN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getPersonaRecibe()), visible);
                TextPage observacion = (TextPage)mWizardModel.findByKey(labels.getObservaciones());
                observacion.setRequired(!visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
    	}catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void changeStatus(Page page, boolean visible){
    	String clase = page.getClass().toString();
    	if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage")){
    		SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.BarcodePage")){
    		BarcodePage modifPage = (BarcodePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);;
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.LabelPage")){
    		LabelPage modifPage = (LabelPage) page; modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.TextPage")){
    		TextPage modifPage = (TextPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NumberPage")){
    		NumberPage modifPage = (NumberPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage")){
    		MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.DatePage")){
    		DatePage modifPage = (DatePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SelectParticipantPage")){
    		SelectParticipantPage modifPage = (SelectParticipantPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NewDatePage")){
    		NewDatePage modifPage = (NewDatePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    }
    
    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
    }
    
    public void saveData(){
		Map<String, String> mapa = mWizardModel.getAnswers();
		//Guarda las respuestas en un bundle
		Bundle datos = new Bundle();
		for (Map.Entry<String, String> entry : mapa.entrySet()){
			datos.putString(entry.getKey(), entry.getValue());
		}
		
		//Abre la base de datos
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		estudiosAdapter.open();
		
		//Obtener datos del bundle para el obsequio
		String id = infoMovil.getId();
        String obsequioSN = datos.getString(this.getString(R.string.obsequioSN));
		String personaRecibe = datos.getString(this.getString(R.string.personaRecibe));
		String observaciones = datos.getString(this.getString(R.string.observaciones));
		
		//Crea un nuevo obsequio

		ObsequioGeneral obsequioGeneral = new ObsequioGeneral();
		obsequioGeneral.setId(id);
        if (visita!=null) {
            obsequioGeneral.setCasa(visita.getCodigoParticipanteCaso().getCodigoCaso().getCasa().getCasa().getCodigo().toString());
            obsequioGeneral.setCasaChf(visita.getCodigoParticipanteCaso().getCodigoCaso().getCasa().getCodigoCHF());
            obsequioGeneral.setSeguimiento(visita.getCodigoParticipanteCaso().getCodigoCaso().getCodigoCaso());
            obsequioGeneral.setNumVisitaSeguimiento(visita.getVisita());
            obsequioGeneral.setMotivo("1");
        }else if (visitaFinal != null){
            obsequioGeneral.setCasa(visitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getCasa().getCasa().getCodigo().toString());
            obsequioGeneral.setCasaChf(visitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getCasa().getCodigoCHF());
            obsequioGeneral.setSeguimiento(visitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getCodigoCaso());
            obsequioGeneral.setNumVisitaSeguimiento("F");
            obsequioGeneral.setMotivo("2");
        }else{
            obsequioGeneral.setCasa(casaChf.getCasa().getCodigo().toString());
            obsequioGeneral.setCasaChf(casaChf.getCodigoCHF());
            obsequioGeneral.setMotivo("3");//MA
        }
        if (tieneValor(obsequioSN)){
            MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + obsequioSN + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
            obsequioGeneral.setObsequioSN(Integer.valueOf(catSino.getCatKey()));
        }else{
            obsequioGeneral.setObsequioSN(0);
        }
        if (tieneValor(personaRecibe)) {
            Participante recibe = estudiosAdapter.getParticipante(Integer.valueOf(personaRecibe));
            obsequioGeneral.setParticipante(recibe.getCodigo());
            obsequioGeneral.setPersonaRecibe(recibe.getNombreCompleto());
        }
        obsequioGeneral.setObservaciones(observaciones);

		obsequioGeneral.setRecordDate(new Date());
		obsequioGeneral.setRecordUser(username);
		obsequioGeneral.setDeviceid(infoMovil.getDeviceId());
		obsequioGeneral.setEstado('0');
		obsequioGeneral.setPasive('0');
		estudiosAdapter.crearObsequioGeneral(obsequioGeneral);

        if (casaChf != null && obsequioSN.equalsIgnoreCase(Constants.YES)) {
            //actualizar en No a todos los participantes de la casa de familia que se le entregó obsequio
            List<ParticipanteProcesos> procesos = estudiosAdapter.getParticipantesProc(ConstantsDB.casaCHF + "='" + obsequioGeneral.getCasaChf() + "'", null);
            MovilInfo movilInfo = new MovilInfo();
            movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
            movilInfo.setDeviceid(infoMovil.getDeviceId());
            movilInfo.setUsername(username);
            movilInfo.setToday(new Date());
            for (ParticipanteProcesos proceso : procesos) {
                proceso.setObsequioChf("No");
                proceso.setMovilInfo(movilInfo);
                estudiosAdapter.actualizarParticipanteProcesos(proceso);
            }
        }

		Bundle arguments = new Bundle();
		Intent i;
		if (visita !=null) {
            arguments.putSerializable(Constants.VISITA, visita);
            i = new Intent(getApplicationContext(),
                    MenuVisitaSeguimientoCasoActivity.class);
        }else if (visitaFinal != null){
            arguments.putSerializable(Constants.VISITA_FINAL, visitaFinal);
            i = new Intent(getApplicationContext(),
                    MenuVisitaFinalCasoActivity.class);
        }else{
            i = new Intent(getApplicationContext(),
                    MenuInfoActivity.class);
            i.putExtra(ConstantsDB.COD_CASA, casaChf.getCasa().getCodigo());
            i.putExtra(ConstantsDB.CODIGO, codigoParticipante);
            i.putExtra(ConstantsDB.VIS_EXITO, true);
        }
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtras(arguments);
		startActivity(i);
		Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
		toast.show();
		finish();
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
            // TODO: be smarter about this
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
            return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
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
