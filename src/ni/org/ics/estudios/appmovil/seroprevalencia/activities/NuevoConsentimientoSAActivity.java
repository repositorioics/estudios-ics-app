package ni.org.ics.estudios.appmovil.seroprevalencia.activities;

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
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.CartaConsentimiento;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.Tamizaje;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuInfoActivity;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.seroprevalencia.forms.ConsentimientoSAForm;
import ni.org.ics.estudios.appmovil.seroprevalencia.forms.ConsentimientoSAFormLabels;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class NuevoConsentimientoSAActivity extends FragmentActivity implements
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
    private ConsentimientoSAFormLabels labels = new ConsentimientoSAFormLabels();
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private GPSTracker gps;
	private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	private static Participante participante = new Participante();
	private Integer edadAnios = 0;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
        setContentView(R.layout.activity_data_enter);
        participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		infoMovil = new DeviceInfo(NuevoConsentimientoSAActivity.this);
        gps = new GPSTracker(NuevoConsentimientoSAActivity.this);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new ConsentimientoSAForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        String edad[] = participante.getEdad().split("/");
        if (edad.length > 0) {
            edadAnios = Integer.valueOf(edad[0]);
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

        onPageTreeChanged();
        updateBottomBar();
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
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    @Override
    public void onBackPressed (){
        createDialog(EXIT);
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

    private void createDialog(int dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(dialog){
            case EXIT:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.exiting));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish app
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
            if(page.getTitle().equals(labels.getAceptaTamizajePersona())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoParticipaPersona()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), visible && edadAnios>5 && edadAnios<10); //10 porque es el limite de edad q se va a permitir al momento del desarrollo (marzo 2018)
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaSeroprevalencia()), visible && (edadAnios >= 2 && edadAnios < 10));
                notificarCambios = false;

                if(!visible) {
                    resetForm(100);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noAceptaTamizajePersona),Toast.LENGTH_LONG);
                    toast.show();
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoParticipaPersona())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoParticipaPersona()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAsentimientoVerbal())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
                changeStatus(mWizardModel.findByKey(labels.getAceptaSeroprevalencia()), visible && (edadAnios >= 2 && edadAnios < 10));
                notificarCambios = false;
                if(!visible) {
                    resetForm(98);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noDaAsentimiento),Toast.LENGTH_LONG);
                    toast.show();
                }
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaSeroprevalencia())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), visible && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), visible && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), visible && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), visible && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), visible && edadAnios<18);
                notificarCambios = false;


                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaSeroprevalencia()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaSeroprevalencia())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaSeroprevalencia()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getParticipanteOTutorAlfabeto())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), false);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), false);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), false);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), false);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTestigoPresente())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
    	}catch (Exception ex){
            ex.printStackTrace();
        }
    	
    }

    private void resetForm(int preg){
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), false);
    }

    public void changeStatus(Page page, boolean visible){
        String clase = page.getClass().toString();
        if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage")){
            SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.BarcodePage")){
            BarcodePage modifPage = (BarcodePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.LabelPage")){
            LabelPage modifPage = (LabelPage) page; modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.TextPage")){
            TextPage modifPage = (TextPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible); //modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NumberPage")){
            NumberPage modifPage = (NumberPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible); //modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage")){
            MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.DatePage")){
            DatePage modifPage = (DatePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SelectParticipantPage")){
            SelectParticipantPage modifPage = (SelectParticipantPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NewDatePage")){
            NewDatePage modifPage = (NewDatePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SelectCasaPage")){
            SelectCasaPage modifPage = (SelectCasaPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
    }

    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
    }
    
    public void saveData() {
        try {

            //Guarda las respuestas en un bundle
            Map<String, String> mapa = mWizardModel.getAnswers();
            Bundle datos = new Bundle();
            DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }
            //Abre la base de datos
            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            //Obtener datos del bundle para el tamizaje

            String aceptaTamizajePersona = datos.getString(this.getString(R.string.aceptaTamizajePersona));
            String razonNoAceptaTamizajePersona = datos.getString(this.getString(R.string.razonNoParticipaPersona));
            String otraRazonNoAceptaTamizajePersona = datos.getString(this.getString(R.string.otraRazonNoParticipaPersona));
            String asentimientoVerbal = datos.getString(this.getString(R.string.asentimientoVerbal));

            String aceptaSeroprevalencia = datos.getString(this.getString(R.string.aceptaSeroprevalencia));
            String razonNoAceptaSeroprevalencia = datos.getString(this.getString(R.string.razonNoAceptaSeroprevalencia));
            String otraRazonNoAceptaSeroprevalencia = datos.getString(this.getString(R.string.otraRazonNoAceptaSeroprevalencia));

            //Crea un Nuevo Registro de tamizaje
            Tamizaje tamizaje =  new Tamizaje();
            Tamizaje tamizajeInf = new Tamizaje();
            tamizaje.setCohorte("CP");
            tamizaje.setSexo(participante.getSexo());
            tamizaje.setFechaNacimiento(participante.getFechaNac());
            if (tieneValor(aceptaTamizajePersona)) {
                MessageResource catAceptaTamizajePersona = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaTamizajePersona + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAceptaTamizajePersona!=null) tamizaje.setAceptaTamizajePersona(catAceptaTamizajePersona.getCatKey());
            }else{//si no tiene valor, es porque no tiene la edad según el estudio seleccionado
                tamizaje.setAceptaTamizajePersona(Constants.NOKEYSND);
            }
            if (tieneValor(razonNoAceptaTamizajePersona)) {
                MessageResource catRazonNoAceptaTamizajePersona = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaTamizajePersona + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
                if (catRazonNoAceptaTamizajePersona!=null) tamizaje.setRazonNoAceptaTamizajePersona(catRazonNoAceptaTamizajePersona.getCatKey());
            }
            if (tieneValor(asentimientoVerbal)) {
                MessageResource catAsentimientoVerbal = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + asentimientoVerbal + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAsentimientoVerbal!=null) tamizaje.setAsentimientoVerbal(catAsentimientoVerbal.getCatKey());
            }
            tamizaje.setOtraRazonNoAceptaTamizajePersona(otraRazonNoAceptaTamizajePersona);
            tamizaje.setRecordDate(new Date());
            tamizaje.setRecordUser(username);
            tamizaje.setDeviceid(infoMovil.getDeviceId());
            tamizaje.setEstado('0');
            tamizaje.setPasive('0');

            //Registrar tamizaje seroprevalencia

            Estudio estudioSA = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=" + Constants.COD_EST_SEROPREVALENCIA, null);
            tamizaje.setCodigo(infoMovil.getId());
            tamizaje.setEstudio(estudioSA);
            //Si acepta o no participar, siempre registrar tamizaje
            MessageResource catAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaSeroprevalencia + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
            if (catAceptaParticipar!=null) tamizaje.setAceptaParticipar(catAceptaParticipar.getCatKey());
            if (tieneValor(razonNoAceptaSeroprevalencia)) {
                MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaSeroprevalencia + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
                if (catRazonNoAceptaParticipar!=null) tamizaje.setRazonNoAceptaParticipar(catRazonNoAceptaParticipar.getCatKey());
            }
            tamizaje.setOtraRazonNoAceptaParticipar(otraRazonNoAceptaSeroprevalencia);
            estudiosAdapter.crearTamizaje(tamizaje);

            //Pregunta si acepta realizar el tamizaje
            if (tamizaje.getAceptaTamizajePersona().equals(Constants.YESKEYSND)) {

                String nombre1Tutor = datos.getString(this.getString(R.string.nombre1Tutor));
                String nombre2Tutor = datos.getString(this.getString(R.string.nombre2Tutor));
                String apellido1Tutor = datos.getString(this.getString(R.string.apellido1Tutor));
                String apellido2Tutor = datos.getString(this.getString(R.string.apellido2Tutor));
                String relacionFamiliarTutor = datos.getString(this.getString(R.string.relacionFamiliarTutor));

                String participanteOTutorAlfabeto = datos.getString(this.getString(R.string.participanteOTutorAlfabeto));
                String testigoPresente = datos.getString(this.getString(R.string.testigoPresente));
                String nombre1Testigo = datos.getString(this.getString(R.string.nombre1Testigo));
                String nombre2Testigo = datos.getString(this.getString(R.string.nombre2Testigo));
                String apellido1Testigo = datos.getString(this.getString(R.string.apellido1Testigo));
                String apellido2Testigo = datos.getString(this.getString(R.string.apellido2Testigo));

                CartaConsentimiento cc = new CartaConsentimiento();
                cc.setRecordDate(new Date());
                cc.setRecordUser(username);
                cc.setDeviceid(infoMovil.getDeviceId());
                cc.setEstado('0');
                cc.setPasive('0');
                cc.setFechaFirma(new Date());
                cc.setParticipante(participante);
                cc.setEmancipado("0"); //por defecto no es emancipado
                cc.setAceptaParteA(Constants.YESKEYSND);
                if (tieneValor(nombre1Tutor)) {
                    cc.setNombre1Tutor(nombre1Tutor);
                }
                if (tieneValor(nombre2Tutor)) {
                    cc.setNombre2Tutor(nombre2Tutor);
                }
                if (tieneValor(apellido1Tutor)) {
                    cc.setApellido1Tutor(apellido1Tutor);
                }
                if (tieneValor(apellido2Tutor)) {
                    cc.setApellido2Tutor(apellido2Tutor);
                }
                if (tieneValor(relacionFamiliarTutor)) {
                    MessageResource catRelacionFamiliarTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relacionFamiliarTutor + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_RFTUTOR'", null);
                    if (catRelacionFamiliarTutor!=null) cc.setRelacionFamiliarTutor(catRelacionFamiliarTutor.getCatKey());
                }

                if (tieneValor(participanteOTutorAlfabeto)) {
                    MessageResource catParticipanteOTutorAlfabeto = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + participanteOTutorAlfabeto + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catParticipanteOTutorAlfabeto!=null) cc.setParticipanteOTutorAlfabeto(catParticipanteOTutorAlfabeto.getCatKey());
                }
                if (tieneValor(testigoPresente)) {
                    MessageResource catTestigoPresente = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + testigoPresente + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catTestigoPresente!=null) cc.setTestigoPresente(catTestigoPresente.getCatKey());
                }

                if (tieneValor(nombre1Testigo)) cc.setNombre1Testigo(nombre1Testigo);
                if (tieneValor(nombre2Testigo)) cc.setNombre2Testigo(nombre2Testigo);
                if (tieneValor(apellido1Testigo)) cc.setApellido1Testigo(apellido1Testigo);
                if (tieneValor(apellido2Testigo)) cc.setApellido2Testigo(apellido2Testigo);

                //crear carta de consentimiento para seroprevalencia
                cc.setAceptaContactoFuturo(null);
                cc.setCodigo(infoMovil.getId());
                cc.setTamizaje(tamizaje);
                cc.setVersion(Constants.VERSION_CC_SA);
                estudiosAdapter.crearCartaConsentimiento(cc);

                ParticipanteSeroprevalencia pSA = new ParticipanteSeroprevalencia();
                //pSA.setParticipanteSA(id);
                pSA.setParticipante(participante);
                //pSA.setCasaCHF(casaCHF);
                pSA.setRecordDate(new Date());
                pSA.setRecordUser(username);
                pSA.setDeviceid(infoMovil.getDeviceId());
                pSA.setEstado('0');
                pSA.setPasive('0');
                //Guarda el participante de chf
                estudiosAdapter.crearParticipanteSeroprevalencia(pSA);

                ParticipanteProcesos procesos;
                int ceroDefaul = 0;
                procesos = participante.getProcesos();
                procesos.setEnCasaSa(Constants.YES);
                procesos.setEncPartSa(Constants.YES);
                procesos.setConsSa(Constants.NO);

                String nombreTutor = nombre1Tutor;
                if (tieneValor(nombre2Tutor)) nombreTutor = nombreTutor + " " + nombre2Tutor;
                nombreTutor = nombreTutor + " " + apellido1Tutor;
                if (tieneValor(apellido2Tutor)) nombreTutor = nombreTutor + " " + apellido2Tutor;
                procesos.setTutor(nombreTutor);
                if (tieneValor(relacionFamiliarTutor)) {
                    MessageResource catRelacionFamiliarTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relacionFamiliarTutor + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_RFTUTOR'", null);
                    if (catRelacionFamiliarTutor != null)
                        procesos.setRelacionFam(Integer.valueOf(catRelacionFamiliarTutor.getCatKey()));
                } else {
                    procesos.setRelacionFam(ceroDefaul);
                }

                MovilInfo movilInfo = new MovilInfo();
                movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                movilInfo.setDeviceid(infoMovil.getDeviceId());
                movilInfo.setUsername(username);
                movilInfo.setToday(new Date());
                procesos.setMovilInfo(movilInfo);
                estudiosAdapter.actualizarParticipanteProcesos(procesos);

                Intent i = new Intent(getApplicationContext(),
                        MenuInfoActivity.class);
                i.putExtra(ConstantsDB.CODIGO, participante.getCodigo());
                i.putExtra(ConstantsDB.VIS_EXITO, false);
                startActivity(i);
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
                toast.show();
                finish();

            }else {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noRegistraIngreso), Toast.LENGTH_LONG);
                toast.show();
                finish();
            }

        } catch (Exception ex){
            ex.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), ex.getLocalizedMessage(), Toast.LENGTH_LONG);
            toast.show();
            //finish();
        }finally {
            //Cierra la base de datos
            if (estudiosAdapter!=null)
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
