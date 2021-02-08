package ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata;

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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaAreasActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.AreaAmbienteCasaForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.AreaAmbienteCasaFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.AreaAmbiente;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Banio;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class EditarAreaActivity extends FragmentActivity implements
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
    private static CasaCohorteFamilia casaCHF = new CasaCohorteFamilia();
    private static AreaAmbiente areaCasa = new AreaAmbiente();
	private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	private AreaAmbienteCasaFormLabels labels = new AreaAmbienteCasaFormLabels();
	public static final String SIMPLE_DATA_KEY = "_";

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
		infoMovil = new DeviceInfo(EditarAreaActivity.this);
        casaCHF = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASA);
        areaCasa = (AreaAmbiente) getIntent().getExtras().getSerializable(Constants.AREA);
		
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new AreaAmbienteCasaForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        //Abre la base de datos
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		estudiosAdapter.open();

        if (areaCasa != null) {
        	Bundle dato = null;
        	Page modifPage;
            if (tieneValor(areaCasa.getTipo())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTipo());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + areaCasa.getTipo() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_TIPO_AREA'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmEnabled(false);
            }
	        if(areaCasa.getLargo()!= null){
	        	modifPage = (NumberPage) mWizardModel.findByKey(labels.getLargo());
	        	dato = new Bundle();
	        	dato.putString(SIMPLE_DATA_KEY, areaCasa.getLargo().toString());
	        	modifPage.resetData(dato);
	        }	
	        if(areaCasa.getAncho()!= null){
	        	modifPage = (NumberPage) mWizardModel.findByKey(labels.getAncho());
		        dato = new Bundle();
		        dato.putString(SIMPLE_DATA_KEY, areaCasa.getAncho().toString());
		        modifPage.resetData(dato);
	        }
            if(areaCasa.getLargo()!= null && areaCasa.getAncho()!= null){
                modifPage = (LabelPage) mWizardModel.findByKey(labels.getTotalM2());
                modifPage.setHint(String.valueOf(areaCasa.getAncho() * areaCasa.getLargo()));
                modifPage.setmVisible(true);
            }

            if(areaCasa.getTipo()!=null && areaCasa.getTipo().matches("banio")) {
                Banio banio = estudiosAdapter.getBanio(MainDBConstants.codigo + "='" +areaCasa.getCodigo() + "'",null);
                if (banio != null && banio.getConVentana()!=null){
                    modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getConVentana());
                    MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + banio.getConVentana() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    dato = new Bundle();
                    dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                    modifPage.resetData(dato);
                    modifPage.setmVisible(true);
                }
            }else{
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumVentanas());
                dato = new Bundle();
                if(areaCasa.getNumVentanas()!= null) {
                    dato.putString(SIMPLE_DATA_KEY, areaCasa.getNumVentanas().toString());
                }
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }

        }

        estudiosAdapter.close();
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
        
        onPageTreeChangedInitial(); 
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
                        if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
                        i = new Intent(getApplicationContext(),
                                ListaAreasActivity.class);
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
    
    public void onPageTreeChangedInitial() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        if (recalculateCutOffPage()) {
            updateBottomBar();
        }
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
    	try{
	    	updateModel(page);
	    	updateConstrains();
	        if (recalculateCutOffPage()) {
	        	if (notificarCambios) mPagerAdapter.notifyDataSetChanged();
	            updateBottomBar();
	        }
	        notificarCambios = true;
	    }catch (Exception ex){
	        ex.printStackTrace();
	    }
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
            if (page.getTitle().equals(labels.getTipo())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Baño");
                changeStatus(mWizardModel.findByKey(labels.getNumVentanas()), !visible);
                changeStatus(mWizardModel.findByKey(labels.getConVentana()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAncho())) {
                if (tieneValor(page.getData().get(Page.SIMPLE_DATA_KEY).toString()) && tieneValor(mWizardModel.findByKey(labels.getLargo()).getData().get(Page.SIMPLE_DATA_KEY).toString())) {
                    Double area = Double.valueOf(page.getData().get(Page.SIMPLE_DATA_KEY).toString()) * Double.valueOf(mWizardModel.findByKey(labels.getLargo()).getData().get(Page.SIMPLE_DATA_KEY).toString());
                    mWizardModel.findByKey(labels.getTotalM2()).setHint(area.toString());
                } else {
                    mWizardModel.findByKey(labels.getTotalM2()).setHint("");
                }
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getLargo())) {
                if (tieneValor(page.getData().get(Page.SIMPLE_DATA_KEY).toString()) && tieneValor(mWizardModel.findByKey(labels.getAncho()).getData().get(Page.SIMPLE_DATA_KEY).toString())) {
                    Double area = Double.valueOf(page.getData().get(Page.SIMPLE_DATA_KEY).toString()) * Double.valueOf(mWizardModel.findByKey(labels.getAncho()).getData().get(Page.SIMPLE_DATA_KEY).toString());
                    mWizardModel.findByKey(labels.getTotalM2()).setHint(area.toString());
                } else {
                    mWizardModel.findByKey(labels.getTotalM2()).setHint("");
                }
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
    		BarcodePage modifPage = (BarcodePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);;
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
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NewDatePage")){
    		NewDatePage modifPage = (NewDatePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
    }
    
    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
    }
    
    
    public void saveData(){
        try {
            Map<String, String> mapa = mWizardModel.getAnswers();
            //Guarda las respuestas en un bundle
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }

            //Obtener datos del bundle para el area
            String tipo = datos.getString(this.getString(R.string.tipo));
            String largo = datos.getString(this.getString(R.string.largo));
            String ancho = datos.getString(this.getString(R.string.ancho));
            String numVentanas = datos.getString(this.getString(R.string.numVentanas));
            String conVentana = datos.getString(this.getString(R.string.conVentana));
            String conVent = null;

            //Abre la base de datos
            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();
            if (tieneValor(tipo)) {
                MessageResource catTipo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tipo + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_TIPO_AREA'", null);
                areaCasa.setTipo(catTipo.getCatKey());
            }

            if (tieneValor(largo)) {
                areaCasa.setLargo(Double.parseDouble(largo));
            } else {
                areaCasa.setLargo(null);
            }
            if (tieneValor(ancho)) {
                areaCasa.setAncho(Double.parseDouble(ancho));
            } else {
                areaCasa.setAncho(null);
            }
            Page area = mWizardModel.findByKey(labels.getTotalM2());
            if (tieneValor(area.getHint())) {
                areaCasa.setTotalM2(Double.valueOf(area.getHint()));
            }
            if (tieneValor(numVentanas)) {
                areaCasa.setNumVentanas(Integer.valueOf(numVentanas));
            }
            areaCasa.setRecordDate(new Date());
            areaCasa.setRecordUser(username);
            areaCasa.setDeviceid(infoMovil.getDeviceId());
            areaCasa.setEstado('0');
            areaCasa.setPasive('0');

            //Guarda el areaambiente
            if(areaCasa.getTipo().matches("banio")){
                if (tieneValor(conVentana)) {
                    MessageResource catConVentana = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + conVentana + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    conVent = catConVentana.getCatKey();
                }
                Banio b = new Banio(areaCasa.getCodigo(), areaCasa.getLargo(), areaCasa.getAncho(), areaCasa.getTotalM2(), areaCasa.getNumVentanas(), areaCasa.getCasa(), areaCasa.getTipo(), conVent);
                b.setRecordDate(new Date());
                b.setRecordUser(username);
                b.setDeviceid(infoMovil.getDeviceId());
                b.setEstado('0');
                b.setPasive('0');
                estudiosAdapter.editarBanio(b);
            }
            else{
                estudiosAdapter.editarAreaAmbiente(areaCasa);
            }
            Bundle arguments = new Bundle();
            Intent i;
            if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
            i = new Intent(getApplicationContext(),
                    ListaAreasActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtras(arguments);
            startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
            toast.show();
            finish();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (estudiosAdapter != null)
                estudiosAdapter.close();
        }
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
