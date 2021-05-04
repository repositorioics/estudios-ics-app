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
import ni.org.ics.estudios.appmovil.domain.VisitaTerrenoParticipante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuInfoActivity;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.SelecPartActivity;
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
import java.util.ArrayList;
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
    private static ParticipanteCohorteFamilia participanteChf = new ParticipanteCohorteFamilia();
	private Integer edadAnios = 0;
    private boolean esElegible = true;
    private List<MessageResource> catRelacionFamiliar = new ArrayList<MessageResource>();
    private String[] catVerifTutAlf; //cosas a verificar cuando tutor es alfabeto
    private String[] catVerifTutNoAlf; //cosas a verificar cuando tutor no es alfabeto
    private int totalVerifTutor = 0;
    private boolean visExitosa = false;
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
        visExitosa = getIntent().getBooleanExtra(ConstantsDB.VIS_EXITO,false);
        settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		infoMovil = new DeviceInfo(NuevoConsentimientoSAActivity.this);
        gps = new GPSTracker(NuevoConsentimientoSAActivity.this);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
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
        estudiosAdapter.open();
        catRelacionFamiliar = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", CatalogosDBConstants.order);
        catVerifTutNoAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
        catVerifTutAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catKey + " in ('1','2','3','6') and " + CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
        participanteChf = estudiosAdapter.getParticipanteCohorteFamilia(MainDBConstants.participante  + "="+participante.getCodigo(), null);
        estudiosAdapter.close();
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
            } else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage")) {
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                //validación solo para la pregunta de verificación del tutor
                if (page.getTitle().equalsIgnoreCase(this.getString(R.string.verifTutor)) && test.size() != totalVerifTutor) {
                    cutOffPage = i;
                    break;
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
            if (page.getTitle().equals(labels.getVisExit())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRazonVisNoExit()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), visible);
                //notificarCambios = false;
                if (!visible) {
                    resetForm(100);
                    esElegible =false;
                }
                else {
                    resetForm(88);
                    esElegible =true;
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRazonVisNoExit())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("No se encontro tutor");
                boolean opcion7 = visible;
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null &&
                        (page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains("Niño ausente")
                                || page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Padres Ausentes o Adultos Ausentes")
                                || page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Acude a Consulta Medica sin Tutor")));
                changeStatus(mWizardModel.findByKey(labels.getPersonaCasa()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamPersonaCasa()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoPersonaCasa()), (opcion7 || visible));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRelacionPersonaCasa()), false);
                //notificarCambios = false;
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null &&
                        page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains("Otro motivo"));
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonVisitaNoExitosa()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRelacionFamPersonaCasa())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otra relación familiar");
                changeStatus(mWizardModel.findByKey(labels.getOtraRelacionPersonaCasa()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaTamizajePersona())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoParticipaPersona()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), visible && edadAnios>5 && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaSeroprevalencia()), visible);
                //notificarCambios = false;

                if(!visible) {
                    resetForm(100);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noAceptaTamizajePersona),Toast.LENGTH_LONG);
                    toast.show();
                    esElegible =false;
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoParticipaPersona())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoParticipaPersona()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAsentimientoVerbal())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
                changeStatus(mWizardModel.findByKey(labels.getAceptaSeroprevalencia()), visible);
                //notificarCambios = false;
                esElegible = visible;
                if(!visible) {
                    resetForm(96);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noDaAsentimiento),Toast.LENGTH_LONG);
                    toast.show();
                }
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaSeroprevalencia())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                esElegible = visible;
                String relacion = MessageResourceUtil.getRelacionFamiliar(catRelacionFamiliar, participante.getRelacionFamiliarTutor());
                LabelPage pagetmp = (LabelPage) mWizardModel.findByKey(labels.getTutor());
                pagetmp.setHint(participante.getTutor() + " - (" + relacion + ")");

                changeStatus(mWizardModel.findByKey(labels.getTutor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMismoTutorSN()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaSeroprevalencia()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                //notificarCambios = false;
                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noAceptaParticipar), Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(95);
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaSeroprevalencia())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaSeroprevalencia()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMismoTutorSN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMotivoDifTutor()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMotivoDifTutor())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro Motivo");
                changeStatus(mWizardModel.findByKey(labels.getOtroMotivoDifTutor()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getParticipanteOTutorAlfabeto())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), false);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), false);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), false);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), false);
                //notificarCambios = false;
                MultipleFixedChoicePage pagetmp = (MultipleFixedChoicePage)mWizardModel.findByKey(labels.getVerifTutor());
                pagetmp.setChoices(visible?catVerifTutNoAlf:catVerifTutAlf);
                totalVerifTutor = visible?catVerifTutNoAlf.length:catVerifTutAlf.length;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTestigoPresente())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
                esElegible = visible;
                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), labels.getNoCumpleIncDen(), Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(95);
                }
            }
    	}catch (Exception ex){
            ex.printStackTrace();
        }
    	
    }

    private void resetForm(int preg){
        if (preg > 96) changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), false);
        if (preg > 96) changeStatus(mWizardModel.findByKey(labels.getAceptaSeroprevalencia()), false);
        if (preg > 96) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaSeroprevalencia()), false);
        if (preg > 96) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaSeroprevalencia()), false);
        if (preg > 95) changeStatus(mWizardModel.findByKey(labels.getTutor()), false);
        if (preg > 95) changeStatus(mWizardModel.findByKey(labels.getMismoTutorSN()), false);
        if (preg > 95) changeStatus(mWizardModel.findByKey(labels.getMotivoDifTutor()), false);
        if (preg > 95) changeStatus(mWizardModel.findByKey(labels.getOtroMotivoDifTutor()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), false);
        if (preg > 94) changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), false);
        if (preg > 87) changeStatus(mWizardModel.findByKey(labels.getPersonaCasa()), false);
        if (preg > 87) changeStatus(mWizardModel.findByKey(labels.getRelacionFamPersonaCasa()), false);
        if (preg > 87) changeStatus(mWizardModel.findByKey(labels.getOtraRelacionPersonaCasa()), false);
        if (preg > 87) changeStatus(mWizardModel.findByKey(labels.getTelefonoPersonaCasa()), false);
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
            estudiosAdapter.open();

            //Obtener datos del bundle para el tamizaje
            String visExit = datos.getString(this.getString(R.string.visExit));
            String razonVisNoExit = datos.getString(this.getString(R.string.razonVisNoExit));
            String otraRazonVisitaNoExitosa = datos.getString(this.getString(R.string.otraRazonVisitaNoExitosa));
            String personaCasa = datos.getString(this.getString(R.string.personaCasa));
            String relacionFamPersonaCasa = datos.getString(this.getString(R.string.relacionFamPersonaCasa));
            String otraRelacionPersonaCasa = datos.getString(this.getString(R.string.otraRelacionPersonaCasa));
            String telefonoPersonaCasa = datos.getString(this.getString(R.string.telefonoPersonaCasa));

            String aceptaTamizajePersona = datos.getString(this.getString(R.string.aceptaTamizajePersona));
            String razonNoAceptaTamizajePersona = datos.getString(this.getString(R.string.razonNoParticipaPersona));
            String otraRazonNoAceptaTamizajePersona = datos.getString(this.getString(R.string.otraRazonNoParticipaPersona));
            String asentimientoVerbal = datos.getString(this.getString(R.string.asentimientoVerbal));

            String aceptaSeroprevalencia = datos.getString(this.getString(R.string.aceptaSeroprevalencia));
            String razonNoAceptaSeroprevalencia = datos.getString(this.getString(R.string.razonNoAceptaSeroprevalencia));
            String otraRazonNoAceptaSeroprevalencia = datos.getString(this.getString(R.string.otraRazonNoAceptaSeroprevalencia));
            String verifTutor = datos.getString(this.getString(R.string.verifTutor));//agregar en carta
            //Registrar visita de terreno
            VisitaTerrenoParticipante visita = new VisitaTerrenoParticipante();
            visita.setFechaVisita(new Date());
            visita.setRecordDate(new Date());
            visita.setRecordUser(username);
            visita.setDeviceid(infoMovil.getDeviceId());
            visita.setEstado('0');
            visita.setPasive('0');
            visita.setParticipante(participante);
            if (tieneValor(visExit)) {
                MessageResource visitaExitosa = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + visExit + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                visita.setVisitaExitosa(visitaExitosa.getCatKey());
            }
            if (tieneValor(razonVisNoExit)) {
                MessageResource visitaNoExitosa = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonVisNoExit + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_NV'", null);
                visita.setRazonVisitaNoExitosa(visitaNoExitosa.getCatKey());
            }
            if (tieneValor(relacionFamPersonaCasa)) {
                MessageResource relFamiliar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relacionFamPersonaCasa + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", null);
                visita.setRelacionFamPersonaCasa(relFamiliar.getCatKey());
            }
            visita.setOtraRazonVisitaNoExitosa(otraRazonVisitaNoExitosa);
            visita.setPersonaCasa(personaCasa);
            visita.setOtraRelacionPersonaCasa(otraRelacionPersonaCasa);
            visita.setTelefonoPersonaCasa(telefonoPersonaCasa);
            visita.setCodigoVisita(infoMovil.getId());
            visita.setEstudio("Seroprevalencia");
            estudiosAdapter.crearVisitaTerrenoParticipante(visita);
            //si visita es exitosa registrar tamizaje, carta de consentimiento, y actualizar datos del participante participante
            if (visita.getVisitaExitosa().equals(Constants.YESKEYSND)) {
                //Crea un Nuevo Registro de tamizaje
                Tamizaje tamizaje = new Tamizaje();
                tamizaje.setCohorte("CHF");
                tamizaje.setSexo(participante.getSexo());
                tamizaje.setFechaNacimiento(participante.getFechaNac());
                if (tieneValor(aceptaTamizajePersona)) {
                    MessageResource catAceptaTamizajePersona = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaTamizajePersona + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catAceptaTamizajePersona != null)
                        tamizaje.setAceptaTamizajePersona(catAceptaTamizajePersona.getCatKey());
                } else {//si no tiene valor, es porque no tiene la edad según el estudio seleccionado
                    tamizaje.setAceptaTamizajePersona(Constants.NOKEYSND);
                }
                if (tieneValor(razonNoAceptaTamizajePersona)) {
                    MessageResource catRazonNoAceptaTamizajePersona = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaTamizajePersona + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
                    if (catRazonNoAceptaTamizajePersona != null)
                        tamizaje.setRazonNoAceptaTamizajePersona(catRazonNoAceptaTamizajePersona.getCatKey());
                }
                if (tieneValor(asentimientoVerbal)) {
                    MessageResource catAsentimientoVerbal = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + asentimientoVerbal + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catAsentimientoVerbal != null)
                        tamizaje.setAsentimientoVerbal(catAsentimientoVerbal.getCatKey());
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
                if (catAceptaParticipar != null) tamizaje.setAceptaParticipar(catAceptaParticipar.getCatKey());
                if (tieneValor(razonNoAceptaSeroprevalencia)) {
                    MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaSeroprevalencia + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
                    if (catRazonNoAceptaParticipar != null)
                        tamizaje.setRazonNoAceptaParticipar(catRazonNoAceptaParticipar.getCatKey());
                }
                tamizaje.setOtraRazonNoAceptaParticipar(otraRazonNoAceptaSeroprevalencia);
                tamizaje.setEmancipado("0");
                tamizaje.setEsElegible(esElegible?Constants.YESKEYSND:Constants.NOKEYSND);
                estudiosAdapter.crearTamizaje(tamizaje);

                //Pregunta si acepta realizar el tamizaje
                if (tamizaje.getAceptaTamizajePersona().equals(Constants.YESKEYSND)) {

                    String mismoTutorSN = datos.getString(this.getString(R.string.mismoTutorSN));//agregar en carta
                    String motivoDifTutor = datos.getString(this.getString(R.string.motivoDifTutor));//agregar en carta
                    String otroMotivoDifTutor = datos.getString(this.getString(R.string.otroMotivoDifTutor));//agregar en carta

                    String nombre1Tutor = datos.getString(this.getString(R.string.nombre1Tutor));
                    String nombre2Tutor = datos.getString(this.getString(R.string.nombre2Tutor));
                    String apellido1Tutor = datos.getString(this.getString(R.string.apellido1Tutor));
                    String apellido2Tutor = datos.getString(this.getString(R.string.apellido2Tutor));
                    String relacionFamiliarTutor = datos.getString(this.getString(R.string.relacionFamiliarTutor));
                    String otraRelacionFam = datos.getString(this.getString(R.string.otraRelacionFam));//agregar en carta

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
                        MessageResource catRelacionFamiliarTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relacionFamiliarTutor + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", null);
                        if (catRelacionFamiliarTutor != null)
                            cc.setRelacionFamiliarTutor(catRelacionFamiliarTutor.getCatKey());
                    }

                    if (tieneValor(participanteOTutorAlfabeto)) {
                        MessageResource catParticipanteOTutorAlfabeto = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + participanteOTutorAlfabeto + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catParticipanteOTutorAlfabeto != null)
                            cc.setParticipanteOTutorAlfabeto(catParticipanteOTutorAlfabeto.getCatKey());
                    }
                    if (tieneValor(testigoPresente)) {
                        MessageResource catTestigoPresente = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + testigoPresente + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catTestigoPresente != null) cc.setTestigoPresente(catTestigoPresente.getCatKey());
                    }

                    if (tieneValor(nombre1Testigo)) cc.setNombre1Testigo(nombre1Testigo);
                    if (tieneValor(nombre2Testigo)) cc.setNombre2Testigo(nombre2Testigo);
                    if (tieneValor(apellido1Testigo)) cc.setApellido1Testigo(apellido1Testigo);
                    if (tieneValor(apellido2Testigo)) cc.setApellido2Testigo(apellido2Testigo);
                    if (tieneValor(mismoTutorSN)) {
                        MessageResource catMismoTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + mismoTutorSN + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catMismoTutor != null) cc.setMismoTutor(catMismoTutor.getCatKey());
                        if (mismoTutorSN.equals(Constants.YES)) { //Si es el mismo tutor, poner el tutor actual en la carta y no dejar en blanco
                            cc.setNombre1Tutor(participante.getNombre1Tutor());
                            cc.setNombre2Tutor(participante.getNombre2Tutor());
                            cc.setApellido1Tutor(participante.getApellido1Tutor());
                            cc.setApellido2Tutor(participante.getApellido2Tutor());
                            cc.setRelacionFamiliarTutor(participante.getRelacionFamiliarTutor());
                        }
                    }
                    if (tieneValor(motivoDifTutor)) {
                        MessageResource catDifTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + motivoDifTutor + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_DIFTUTOR'", null);
                        if (catDifTutor != null) cc.setMotivoDifTutor(catDifTutor.getCatKey());
                    }
                    cc.setOtroMotivoDifTutor(otroMotivoDifTutor);
                    cc.setOtraRelacionFamTutor(otraRelacionFam);
                    if (tieneValor(verifTutor)) {
                        String keysCriterios = "";
                        verifTutor = verifTutor.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','");
                        List<MessageResource> catVerificaT = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + verifTutor + "') and "
                                + CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", null);
                        for (MessageResource ms : catVerificaT) {
                            keysCriterios += ms.getCatKey() + ",";
                        }
                        if (!keysCriterios.isEmpty())
                            keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                        cc.setVerifTutor(keysCriterios);
                    }
                    //crear carta de consentimiento para seroprevalencia
                    cc.setAceptaContactoFuturo(null);
                    cc.setCodigo(infoMovil.getId());
                    cc.setTamizaje(tamizaje);
                    cc.setReconsentimiento(Constants.NOKEYSND);
                    cc.setVersion(Constants.VERSION_CC_SA);
                    estudiosAdapter.crearCartaConsentimiento(cc);
                    ParticipanteProcesos procesos = participante.getProcesos();
                    if (esElegible) {
                        ParticipanteSeroprevalencia pSA = new ParticipanteSeroprevalencia();
                        pSA.setParticipante(participante);

                        pSA.setCasaCHF(participanteChf.getCasaCHF());
                        pSA.setRecordDate(new Date());
                        pSA.setRecordUser(username);
                        pSA.setDeviceid(infoMovil.getDeviceId());
                        pSA.setEstado('0');
                        pSA.setPasive('0');
                        //Guarda el participante de chf
                        estudiosAdapter.crearParticipanteSeroprevalencia(pSA);

                        int ceroDefaul = 0;
                        procesos.setEnCasaSa(Constants.NO);
                        procesos.setEncPartSa(Constants.NO);
                        procesos.setConsSa(Constants.NO);
                        if (tieneValor(mismoTutorSN) && mismoTutorSN.equals(Constants.NO)) {
                            participante.setRelacionFamiliarTutor(cc.getRelacionFamiliarTutor());
                            participante.setNombre1Tutor(cc.getNombre1Tutor());
                            participante.setNombre2Tutor(cc.getNombre2Tutor());
                            participante.setApellido1Tutor(cc.getApellido1Tutor());
                            participante.setApellido2Tutor(cc.getApellido2Tutor());
                        }
                        if (procesos.getSubEstudios() != null && !procesos.getSubEstudios().equalsIgnoreCase("0")) {
                            if (!procesos.getSubEstudios().contains("1"))
                                procesos.setSubEstudios(procesos.getSubEstudios() + "," + Constants.SUB_ESTUDIO_ARBOVIRUS);
                        } else {
                            procesos.setSubEstudios(Constants.SUB_ESTUDIO_ARBOVIRUS);
                        }

                        MovilInfo movilInfo = new MovilInfo();
                        movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                        movilInfo.setDeviceid(infoMovil.getDeviceId());
                        movilInfo.setUsername(username);
                        movilInfo.setToday(new Date());
                        procesos.setMovilInfo(movilInfo);
                        estudiosAdapter.actualizarParticipanteProcesos(procesos);
                        openMenuInfo();
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
                        toast.show();
                        finish();
                    } else {
                        procesos.setEnCasaSa(Constants.NO);
                        procesos.setEncPartSa(Constants.NO);
                        procesos.setConsSa(Constants.NO);
                        estudiosAdapter.actualizarParticipanteProcesos(procesos);
                        openMenuInfo();
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noRegistraIngreso), Toast.LENGTH_LONG);
                        toast.show();
                        finish();
                    }

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noRegistraIngreso), Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
            }else {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.visitaNoExitosa)+ " " +razonVisNoExit, Toast.LENGTH_LONG);
                toast.show();
                Intent i = new Intent(getApplicationContext(),
                        SelecPartActivity.class);
                i.putExtra(Constants.MENU_INFO, true);
                startActivity(i);
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

    private void openMenuInfo(){
        Intent i = new Intent(getApplicationContext(),
                MenuInfoActivity.class);
        i.putExtra(ConstantsDB.CODIGO, participante.getCodigo());
        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
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
