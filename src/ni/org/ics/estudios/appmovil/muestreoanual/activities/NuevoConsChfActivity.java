package ni.org.ics.estudios.appmovil.muestreoanual.activities;

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
import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.*;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.muestreoanual.forms.ConsChfForm;
import ni.org.ics.estudios.appmovil.muestreoanual.forms.ConsChfFormLabels;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * MA2022
 */
public class NuevoConsChfActivity extends FragmentActivity implements
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
    private ConsChfFormLabels labels = new ConsChfFormLabels();
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private GPSTracker gps;
	private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	private static Participante participante = new Participante();
    private Integer edadMeses = 0;
    private boolean esElegible = true;
    private boolean visExitosa = false;
    private List<MessageResource> catRelacionFamiliar = new ArrayList<MessageResource>();
    private String[] catRelFamSegunEdad;
    private List<MessageResource> catMeses = new ArrayList<MessageResource>();
    private String[] catVerifTutAlf; //cosas a verificar cuando tutor es alfabeto
    private String[] catVerifTutNoAlf; //cosas a verificar cuando tutor no es alfabeto
    private int totalVerifTutor = 0;
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
		infoMovil = new DeviceInfo(NuevoConsChfActivity.this);
        gps = new GPSTracker(NuevoConsChfActivity.this);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
        mWizardModel = new ConsChfForm(this,mPass);
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

        edadMeses = participante.getEdadMeses();
        estudiosAdapter.open();
        if (edadMeses < 216) {
            catRelFamSegunEdad = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR' and "+CatalogosDBConstants.catKey + " not in( '8', '9')", CatalogosDBConstants.order);
        } else {
            catRelFamSegunEdad = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", CatalogosDBConstants.order);
        }
        catRelacionFamiliar = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", CatalogosDBConstants.order);
        catVerifTutNoAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
        catVerifTutAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catKey + " in ('1','2','3','6') and " + CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
        estudiosAdapter.close();

        SingleFixedChoicePage pagetmp = (SingleFixedChoicePage)mWizardModel.findByKey(labels.getRelacionFam());
        pagetmp.setChoices(catRelFamSegunEdad);

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
                        openMenuInfo();
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
            if ((!page.isRequired() && !page.getData().isEmpty()) || (page.isRequired() && page.isCompleted())) {
                if(clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NumberPage")){
                    if (!page.getData().getString(NumberPage.SIMPLE_DATA_KEY).matches("")){
                        NumberPage np = (NumberPage) page;
                        if ((np.ismValRange() || np.ismValPattern())) {
                            String valor = np.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                            if((np.ismValRange() && (np.getmGreaterOrEqualsThan() > Integer.valueOf(valor) || np.getmLowerOrEqualsThan() < Integer.valueOf(valor)))
                                    || (np.ismValPattern() && !valor.matches(np.getmPattern()))){
                                cutOffPage = i;
                                break;
                            }
                        }
                    }
                }
                else if(clase.equals("ni.org.ics.estudios.appmovil.wizard.model.TextPage")){
                    if (!page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("")){
                        TextPage tp = (TextPage) page;
                        if (tp.ismValPattern()) {
                            String valor = tp.getData().getString(TextPage.SIMPLE_DATA_KEY);
                            if(!valor.matches(tp.getmPattern())){
                                cutOffPage = i;
                                break;
                            }
                        }
                    }
                } else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage")) {
                    ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                    //validación solo para la pregunta de verificación del tutor
                    if (page.getTitle().equalsIgnoreCase(this.getString(R.string.verifTutor))) {
                        assert test != null;
                        if (test.size() != totalVerifTutor) {
                            cutOffPage = i;
                            break;
                        }
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
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRelacionFamPersonaCasa())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otra relación familiar");
                changeStatus(mWizardModel.findByKey(labels.getOtraRelacionPersonaCasa()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAceptaTamizajePersona())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAsentimiento()), visible && (edadMeses >= 72 && edadMeses < 216));//6 y menores de 18
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteA()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), !visible);
                //changeStatus(mWizardModel.findByKey(labels.getCriteriosInclusionIndice()), visible);
                onPageTreeChanged();
            }
            //No preguntar criterios. Brenda, 07/03/2022
            /*if(page.getTitle().equals(labels.getCriteriosInclusionIndice())){
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                visible = test!=null && test.size()==4;
                changeStatus(mWizardModel.findByKey(labels.getAsentimiento()), visible && (edadMeses >= 72 && edadMeses < 216));//6 y menores de 18
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteA()), visible);
                if(!visible){
                    resetForm(99);
                    if (test!=null) {
                        Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.noCumpleCriteriosInclusion), Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                onPageTreeChanged();
            }*/
            if (page.getTitle().equals(labels.getAceptaParteA())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParteA()), !visible);
                //notificarCambios = false;
                //if (visible && edadMeses < 72){
                    changeStatus(mWizardModel.findByKey(labels.getTutor()), visible && edadMeses < 216);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getMismoTutorSN()), visible && edadMeses < 216);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAlfabetoTutor()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDomicilio()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCmDomicilio()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getPadre()), visible && edadMeses < 216);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCambiarPadre()), visible && edadMeses < 216);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getMadre()), visible && edadMeses < 216);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCambiarMadre()), visible && edadMeses < 216);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                    //notificarCambios = false;

                    LabelPage pagetmp = (LabelPage) mWizardModel.findByKey(labels.getDomicilio());
                    String domicilio;
                    if (participante.getCasa().getBarrio()!=null){
                        domicilio = participante.getCasa().getDireccion() + " - ("+ participante.getCasa().getBarrio().getNombre()+")";
                    }else
                        domicilio = participante.getCasa().getDireccion();
                    pagetmp.setHint(domicilio);

                    String relacion = MessageResourceUtil.getRelacionFamiliar(catRelacionFamiliar, participante.getRelacionFamiliarTutor());
                    pagetmp = (LabelPage) mWizardModel.findByKey(labels.getTutor());
                    pagetmp.setHint(participante.getTutor()+" - ("+ relacion +")");

                    pagetmp = (LabelPage) mWizardModel.findByKey(labels.getPadre());
                    String padre = participante.getNombre1Padre();
                    if (participante.getNombre2Padre()!=null) padre = padre + " "+  participante.getNombre2Padre();
                    padre = padre +" "+ participante.getApellido1Padre();
                    if (participante.getApellido2Padre()!=null) padre = padre + " "+  participante.getApellido2Padre();
                    pagetmp.setHint(padre);

                    pagetmp = (LabelPage) mWizardModel.findByKey(labels.getMadre());
                    String madre = participante.getNombre1Madre();
                    if (participante.getNombre2Madre()!=null) madre = madre + " "+  participante.getNombre2Madre();
                    madre = madre +" "+ participante.getApellido1Madre();
                    if (participante.getApellido2Madre()!=null) madre = madre + " "+  participante.getApellido2Madre();
                    pagetmp.setHint(madre);
                    esElegible = visible;
                //}
                //esElegible = visible;
                if (!visible){
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.noAceptaParticipar),Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(92);
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRazonNoAceptaParteA())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otros motivos");
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParteA()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAsentimiento())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteA()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTutor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMismoTutorSN()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAlfabetoTutor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDomicilio()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCmDomicilio()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarPadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarMadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                //notificarCambios = false;

                LabelPage pagetmp = (LabelPage) mWizardModel.findByKey(labels.getDomicilio());
                String domicilio;
                if (participante.getCasa().getBarrio()!=null){
                    domicilio = participante.getCasa().getDireccion() + " - ("+ participante.getCasa().getBarrio().getNombre()+")";
                }else
                    domicilio = participante.getCasa().getDireccion();
                pagetmp.setHint(domicilio);

                String relacion = MessageResourceUtil.getRelacionFamiliar(catRelacionFamiliar, participante.getRelacionFamiliarTutor());
                pagetmp = (LabelPage) mWizardModel.findByKey(labels.getTutor());
                pagetmp.setHint(participante.getTutor()+" - ("+ relacion +")");

                pagetmp = (LabelPage) mWizardModel.findByKey(labels.getPadre());
                String padre = participante.getNombre1Padre();
                if (participante.getNombre2Padre()!=null) padre = padre + " "+  participante.getNombre2Padre();
                padre = padre +" "+ participante.getApellido1Padre();
                if (participante.getApellido2Padre()!=null) padre = padre + " "+  participante.getApellido2Padre();
                pagetmp.setHint(padre);

                pagetmp = (LabelPage) mWizardModel.findByKey(labels.getMadre());
                String madre = participante.getNombre1Madre();
                if (participante.getNombre2Madre()!=null) madre = madre + " "+  participante.getNombre2Madre();
                madre = madre +" "+ participante.getApellido1Madre();
                if (participante.getApellido2Madre()!=null) madre = madre + " "+  participante.getApellido2Madre();
                pagetmp.setHint(madre);

                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), labels.getNoAsentimiento(), Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(91);
                }
                esElegible = visible;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMismoTutorSN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getNombrept()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombrept2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidopt()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidopt2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFam()), visible);
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
            if (page.getTitle().equals(labels.getRelacionFam())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otra relación familiar");
                changeStatus(mWizardModel.findByKey(labels.getOtraRelacionFam()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlfabetoTutor())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getTestigoSN()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombretest1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombretest2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidotest1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidotest2()), visible);
                //notificarCambios = false;
                MultipleFixedChoicePage pagetmp = (MultipleFixedChoicePage)mWizardModel.findByKey(labels.getVerifTutor());
                pagetmp.setChoices(visible?catVerifTutNoAlf:catVerifTutAlf);
                totalVerifTutor = visible?catVerifTutNoAlf.length:catVerifTutAlf.length;

                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTestigoSN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNombretest1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombretest2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidotest1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidotest2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDomicilio()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCmDomicilio()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarPadre()), visible && edadMeses < 216);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarMadre()), visible && edadMeses < 216);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPadre()), visible && edadMeses < 216);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMadre()), visible && edadMeses < 216);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                //notificarCambios = false;
                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), labels.getNoCumpleIncDen(), Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(90);
                }
                esElegible = visible;
                onPageTreeChanged();
            }
            //datos de cambio de domicilio se pasan a formulario independiente
            if (page.getTitle().equals(labels.getCmDomicilio())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNotaCmDomicilio()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefono1SN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoCel1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoOper1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefono2SN()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefonoClasif1())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getTelefonoCel1());
                if (visible){
                    pagetmp.setRangeValidation(true, Constants.MINIMO_NUM_CELULAR, Constants.MAXIMO_NUM_CELULAR);
                }else{
                    pagetmp.setRangeValidation(true, Constants.MINIMO_NUM_CONVENCIONAL, Constants.MAXIMO_NUM_CONVENCIONAL);
                }
                changeStatus(mWizardModel.findByKey(labels.getTelefonoOper1()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefono2SN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoCel2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefono3SN()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefonoClasif2())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getTelefonoCel2());
                if (visible){
                    pagetmp.setRangeValidation(true, Constants.MINIMO_NUM_CELULAR, Constants.MAXIMO_NUM_CELULAR);
                }else{
                    pagetmp.setRangeValidation(true, Constants.MINIMO_NUM_CONVENCIONAL, Constants.MAXIMO_NUM_CONVENCIONAL);
                }
                changeStatus(mWizardModel.findByKey(labels.getTelefonoOper2()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefono3SN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif3()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoCel3()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefonoClasif3())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getTelefonoCel3());
                if (visible){
                    pagetmp.setRangeValidation(true, Constants.MINIMO_NUM_CELULAR, Constants.MAXIMO_NUM_CELULAR);
                }else{
                    pagetmp.setRangeValidation(true, Constants.MINIMO_NUM_CONVENCIONAL, Constants.MAXIMO_NUM_CONVENCIONAL);
                }
                changeStatus(mWizardModel.findByKey(labels.getTelefonoOper3()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCambiarPadre())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNombrepadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombrepadre2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidopadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidopadre2()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCambiarMadre())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNombremadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombremadre2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidomadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidomadre2()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }

    	}catch (Exception ex){
            ex.printStackTrace();
        }
    	
    }

    private void resetForm(int preg){
        try {
            if (preg > 93) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParteA()), false);
            if (preg > 93) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParteA()), false);

            if (preg > 92) changeStatus(mWizardModel.findByKey(labels.getAsentimiento()), false);

            //no esta dispuesto a ir al centro
            if (preg > 91) changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), false);
            if (preg > 91) changeStatus(mWizardModel.findByKey(labels.getTutor()), false);
            if (preg > 91) changeStatus(mWizardModel.findByKey(labels.getMismoTutorSN()), false);
            if (preg > 91) changeStatus(mWizardModel.findByKey(labels.getTestigoSN()), false);
            //asentimiento verbal
            if (preg > 90) changeStatus(mWizardModel.findByKey(labels.getNombrept()), false);
            if (preg > 90) changeStatus(mWizardModel.findByKey(labels.getNombrept2()), false);
            if (preg > 90) changeStatus(mWizardModel.findByKey(labels.getApellidopt()), false);
            if (preg > 90) changeStatus(mWizardModel.findByKey(labels.getApellidopt2()), false);
            if (preg > 90) changeStatus(mWizardModel.findByKey(labels.getRelacionFam()), false);
            if (preg > 90) changeStatus(mWizardModel.findByKey(labels.getOtraRelacionFam()), false);
            if (preg > 90) changeStatus(mWizardModel.findByKey(labels.getMotivoDifTutor()), false);
            if (preg > 90) changeStatus(mWizardModel.findByKey(labels.getOtroMotivoDifTutor()), false);
            if (preg > 90) changeStatus(mWizardModel.findByKey(labels.getAlfabetoTutor()), false);
            //no hay testigo
            if (preg > 89) changeStatus(mWizardModel.findByKey(labels.getDomicilio()), false);
            if (preg > 89) changeStatus(mWizardModel.findByKey(labels.getCmDomicilio()), false);
            if (preg > 89) changeStatus(mWizardModel.findByKey(labels.getNombretest1()), false);
            if (preg > 89) changeStatus(mWizardModel.findByKey(labels.getNombretest2()), false);
            if (preg > 89) changeStatus(mWizardModel.findByKey(labels.getApellidotest1()), false);
            if (preg > 89) changeStatus(mWizardModel.findByKey(labels.getApellidotest2()), false);
            //if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getCambiarJefe()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getCambiarPadre()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getCambiarMadre()), false);
            //if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getJefeFam()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getPadre()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getMadre()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif1()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefonoCel1()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefonoOper1()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefono2SN()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif2()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefonoCel2()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefonoOper2()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefono3SN()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif3()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefonoCel3()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getTelefonoOper3()), false);

            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getNombrepadre()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getNombrepadre2()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getApellidopadre()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getApellidopadre2()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getNombremadre()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getNombremadre2()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getApellidomadre()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getApellidomadre2()), false);
            if (preg > 88) changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), false);
            if (preg > 87) changeStatus(mWizardModel.findByKey(labels.getPersonaCasa()), false);
            if (preg > 87) changeStatus(mWizardModel.findByKey(labels.getRelacionFamPersonaCasa()), false);
            if (preg > 87) changeStatus(mWizardModel.findByKey(labels.getOtraRelacionPersonaCasa()), false);
            if (preg > 87) changeStatus(mWizardModel.findByKey(labels.getTelefonoPersonaCasa()), false);
        }catch (Exception ex){
            ex.printStackTrace();
        }

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
            MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page;
            modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
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
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }
            //Abre la base de datos
            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            String visExit = datos.getString(this.getString(R.string.visExit));
            String razonVisNoExit = datos.getString(this.getString(R.string.razonVisNoExit));
            String otraRazonVisitaNoExitosa = datos.getString(this.getString(R.string.otraRazonVisitaNoExitosa));
            String personaCasa = datos.getString(this.getString(R.string.personaCasa));
            String relacionFamPersonaCasa = datos.getString(this.getString(R.string.relacionFamPersonaCasa));
            String otraRelacionPersonaCasa = datos.getString(this.getString(R.string.otraRelacionPersonaCasa));
            String telefonoPersonaCasa = datos.getString(this.getString(R.string.telefonoPersonaCasa));

            String aceptaTamizajePersona = datos.getString(this.getString(R.string.aceptaTamizajePersona));
            String razonNoParticipaPersona = datos.getString(this.getString(R.string.razonNoParticipaPersona));
            String otraRazonNoParticipaPersona = datos.getString(this.getString(R.string.otraRazonNoParticipaPersona));
            //No preguntar criterios. Brenda, 07/03/2022
            //String criteriosInclusionIndice = datos.getString(this.getString(R.string.criteriosInclusion));
            String asentimiento = datos.getString(this.getString(R.string.asentimientoVerbal));
            String aceptaParteA = datos.getString(this.getString(R.string.aceptaParteA));
            String razonNoAceptaParteA = datos.getString(this.getString(R.string.razonNoAceptaParteA));
            String otraRazonNoAceptaParteA = datos.getString(this.getString(R.string.otraRazonNoAceptaParteACovid));
            String aceptaContactoFuturo = datos.getString(this.getString(R.string.aceptaContactoFuturo));
            String aceptaParteB = datos.getString(this.getString(R.string.aceptaParteB));
            String aceptaParteC = datos.getString(this.getString(R.string.aceptaParteC));

            String nombrept = datos.getString(this.getString(R.string.nombrept));
            String nombrept2 = datos.getString(this.getString(R.string.nombrept2));
            String apellidopt = datos.getString(this.getString(R.string.apellidopt));
            String apellidopt2 = datos.getString(this.getString(R.string.apellidopt2));
            String relacionFam = datos.getString(this.getString(R.string.relacionFam));
            String otraRelacionFam = datos.getString(this.getString(R.string.otraRelacionFam));//agregar en carta
            String mismoTutorSN = datos.getString(this.getString(R.string.mismoTutorSN));//agregar en carta
            String motivoDifTutor = datos.getString(this.getString(R.string.motivoDifTutor));//agregar en carta
            String otroMotivoDifTutor = datos.getString(this.getString(R.string.otroMotivoDifTutor));//agregar en carta
            String alfabetoTutor = datos.getString(this.getString(R.string.participanteOTutorAlfabetoHint));
            String testigoSN = datos.getString(this.getString(R.string.testigoSN));
            String nombretest1 = datos.getString(this.getString(R.string.nombretest1));
            String nombretest2 = datos.getString(this.getString(R.string.nombretest2));
            String apellidotest1 = datos.getString(this.getString(R.string.apellidotest1));
            String apellidotest2 = datos.getString(this.getString(R.string.apellidotest2));
            String cmDomicilio = datos.getString(this.getString(R.string.cmDomicilio));
            String telefonoClasif1 = datos.getString(this.getString(R.string.telefonoClasif1));
            String telefonoCel1 = datos.getString(this.getString(R.string.telefonoCel1));
            String telefonoOper1 = datos.getString(this.getString(R.string.telefonoOper1));
            String telefonoClasif2 = datos.getString(this.getString(R.string.telefonoClasif2));
            String telefonoCel2 = datos.getString(this.getString(R.string.telefonoCel2));
            String telefonoOper2 = datos.getString(this.getString(R.string.telefonoOper2));
            String telefonoClasif3 = datos.getString(this.getString(R.string.telefonoClasif3));
            String telefonoCel3 = datos.getString(this.getString(R.string.telefonoCel3));
            String telefonoOper3 = datos.getString(this.getString(R.string.telefonoOper3));
            String cambiarPadre = datos.getString(this.getString(R.string.cambiarPadre));
            String nombrepadre = datos.getString(this.getString(R.string.nombrepadre));
            String nombrepadre2 = datos.getString(this.getString(R.string.nombrepadre2));
            String apellidopadre = datos.getString(this.getString(R.string.apellidopadre));
            String apellidopadre2 = datos.getString(this.getString(R.string.apellidopadre2));
            String cambiarMadre = datos.getString(this.getString(R.string.cambiarMadre));
            String nombremadre = datos.getString(this.getString(R.string.nombremadre));
            String nombremadre2 = datos.getString(this.getString(R.string.nombremadre2));
            String apellidomadre = datos.getString(this.getString(R.string.apellidomadre));
            String apellidomadre2 = datos.getString(this.getString(R.string.apellidomadre2));
            String verifTutor = datos.getString(this.getString(R.string.verifTutor));//agregar en carta

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
            /*Pedir descripción cuándo visita no es exitosa y se selecciona 'Otro motivo'. Brenda 30/08/2022*/
            visita.setOtraRazonVisitaNoExitosa(otraRazonVisitaNoExitosa);
            visita.setPersonaCasa(personaCasa);
            visita.setOtraRelacionPersonaCasa(otraRelacionPersonaCasa);
            visita.setTelefonoPersonaCasa(telefonoPersonaCasa);
            visita.setCodigoVisita(infoMovil.getId());
            visita.setEstudio("CHF");
            estudiosAdapter.crearVisitaTerrenoParticipante(visita);

            //si visita es exitosa registrar tamizaje, carta de consentimiento, y actualizar datos del participante participante
            if (visita.getVisitaExitosa().equals(Constants.YESKEYSND)) {
                //Crea un Nuevo Registro de tamizaje
                Tamizaje tamizaje =  new Tamizaje();
                tamizaje.setCodigo(infoMovil.getId());
                tamizaje.setCohorte("CHF");
                tamizaje.setSexo(participante.getSexo());
                tamizaje.setFechaNacimiento(participante.getFechaNac());
                tamizaje.setCodigoParticipanteRecon(participante.getCodigo());
                if (tieneValor(aceptaTamizajePersona)) {
                    MessageResource catAceptaTam = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaTamizajePersona + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catAceptaTam != null) {
                        tamizaje.setAceptaTamizajePersona(catAceptaTam.getCatKey());
                    }
                }
                if (tieneValor(razonNoParticipaPersona)) {
                    MessageResource catRazonNoAceptaTam = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoParticipaPersona + "' and " + CatalogosDBConstants.catRoot + "='CPD_CAT_MOTRECHAZO'", null);
                    if (catRazonNoAceptaTam != null)
                        tamizaje.setRazonNoAceptaTamizajePersona(catRazonNoAceptaTam.getCatKey());
                }
                tamizaje.setOtraRazonNoAceptaTamizajePersona(otraRazonNoParticipaPersona);
                if (tieneValor(aceptaParteA)) {
                    MessageResource catAceptaParteA = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteA + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catAceptaParteA != null) {
                        tamizaje.setAceptaParticipar(catAceptaParteA.getCatKey());
                    }
                }
                tamizaje.setOtraRazonNoAceptaParticipar(null);
                tamizaje.setEsElegible(esElegible?Constants.YESKEYSND:Constants.NOKEYSND);
                /*if (tieneValor(criteriosInclusionIndice)) {
                    String keysCriterios = "";
                    criteriosInclusionIndice = criteriosInclusionIndice.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','");
                    List<MessageResource> mcriteriosInclusion = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + criteriosInclusionIndice + "') and "
                            + CatalogosDBConstants.catRoot + "='CHF_CAT_CI'", null);
                    for (MessageResource ms : mcriteriosInclusion) {
                        keysCriterios += ms.getCatKey() + ",";
                    }
                    if (!keysCriterios.isEmpty())
                        keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                    tamizaje.setCriteriosInclusion(keysCriterios);
                }*/
                if (tieneValor(asentimiento)) {
                    MessageResource catAsentimientoVerbal = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + asentimiento + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catAsentimientoVerbal != null)
                        tamizaje.setAsentimientoVerbal(catAsentimientoVerbal.getCatKey());
                }
                tamizaje.setRecordDate(new Date());
                tamizaje.setRecordUser(username);
                tamizaje.setDeviceid(infoMovil.getDeviceId());
                tamizaje.setEstado('0');
                tamizaje.setPasive('0');
                //Registrar chf
                Estudio estudioNuevo = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=" + Constants.COD_EST_CHF, null);
                tamizaje.setEstudio(estudioNuevo);
                estudiosAdapter.crearTamizaje(tamizaje);

                //Pregunta si acepta realizar el tamizaje. Aca siempre va a entrar porque en este caso por defecto siempre es Si
                if (tamizaje.getAceptaTamizajePersona().equals(Constants.YESKEYSND)) {
                    if (tamizaje.getAceptaParticipar() != null && tamizaje.getAceptaParticipar().equals(Constants.YESKEYSND)) {
                        CartaConsentimiento cc = new CartaConsentimiento();
                        cc.setRecordDate(new Date());
                        cc.setRecordUser(username);
                        cc.setDeviceid(infoMovil.getDeviceId());
                        cc.setEstado('0');
                        cc.setPasive('0');
                        cc.setFechaFirma(new Date());
                        cc.setParticipante(participante);
                        if (tieneValor(nombrept)) {
                            cc.setNombre1Tutor(nombrept);
                        }
                        if (tieneValor(nombrept2)) {
                            cc.setNombre2Tutor(nombrept2);
                        }
                        if (tieneValor(apellidopt)) {
                            cc.setApellido1Tutor(apellidopt);
                        }
                        if (tieneValor(apellidopt2)) {
                            cc.setApellido2Tutor(apellidopt2);
                        }
                        if (tieneValor(relacionFam)) {
                            MessageResource catRelacionFamiliarTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relacionFam + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", null);
                            if (catRelacionFamiliarTutor != null)
                                cc.setRelacionFamiliarTutor(catRelacionFamiliarTutor.getCatKey());
                        } else {
                            cc.setRelacionFamiliarTutor(Constants.REL_FAM_MISMO_PART);
                        }
                        if (tieneValor(alfabetoTutor)) {
                            MessageResource catParticipanteOTutorAlfabeto = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + alfabetoTutor + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                            if (catParticipanteOTutorAlfabeto != null)
                                cc.setParticipanteOTutorAlfabeto(catParticipanteOTutorAlfabeto.getCatKey());
                        }
                        if (tieneValor(testigoSN)) {
                            MessageResource catTestigoPresente = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + testigoSN + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                            if (catTestigoPresente != null) cc.setTestigoPresente(catTestigoPresente.getCatKey());
                        }

                        if (tieneValor(nombretest1)) cc.setNombre1Testigo(nombretest1);
                        if (tieneValor(nombretest2)) cc.setNombre2Testigo(nombretest2);
                        if (tieneValor(apellidotest1)) cc.setApellido1Testigo(apellidotest1);
                        if (tieneValor(apellidotest2)) cc.setApellido2Testigo(apellidotest2);

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
                        } else if (edadMeses >= 216) { //cuando es mayor de edad no se pregunta tutor.. poner el mismo participante
                            cc.setNombre1Tutor(participante.getNombre1());
                            cc.setNombre2Tutor(participante.getNombre2());
                            cc.setApellido1Tutor(participante.getApellido1());
                            cc.setApellido2Tutor(participante.getApellido2());
                            cc.setRelacionFamiliarTutor(Constants.REL_FAM_MISMO_PART);
                        }
                        if (tieneValor(motivoDifTutor)) {
                            MessageResource catDifTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + motivoDifTutor + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_DIFTUTOR'", null);
                            if (catDifTutor != null) cc.setMotivoDifTutor(catDifTutor.getCatKey());
                        }
                        cc.setOtroMotivoDifTutor(otroMotivoDifTutor);
                        cc.setOtraRelacionFamTutor(otraRelacionFam);
                        if (tieneValor(aceptaContactoFuturo)) {
                            MessageResource catConFut = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaContactoFuturo + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                            if (catConFut != null) {
                                cc.setAceptaContactoFuturo(catConFut.getCatKey());
                            }
                        }
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
                        if (tieneValor(aceptaContactoFuturo)) {
                            MessageResource catConFut = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaContactoFuturo + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                            if (catConFut != null) {
                                cc.setAceptaContactoFuturo(catConFut.getCatKey());
                            }
                        }
                        if (tieneValor(aceptaParteA)) {
                            MessageResource catAceptaParteA = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteA + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                            if (catAceptaParteA != null) cc.setAceptaParteA(catAceptaParteA.getCatKey());
                        }
                        if (tieneValor(razonNoAceptaParteA)) {
                            MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaParteA + "' and " + CatalogosDBConstants.catRoot + "='CPD_CAT_MOTRECHAZO'", null);
                            if (catRazonNoAceptaParticipar != null)
                                cc.setMotivoRechazoParteA(catRazonNoAceptaParticipar.getCatKey());
                        }
                        cc.setOtroMotivoRechazoParteA(otraRazonNoAceptaParteA);
                        if (tieneValor(aceptaParteB)) {
                            MessageResource catAcepta = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteB + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                            if (catAcepta != null) {
                                cc.setAceptaParteB(catAcepta.getCatKey());
                            }
                        }
                        if (tieneValor(aceptaParteC)) {
                            MessageResource catAcepta = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteC + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                            if (catAcepta != null) {
                                cc.setAceptaParteC(catAcepta.getCatKey());
                            }
                        }
                        cc.setTamizaje(tamizaje);
                        cc.setVersion(Constants.VERSION_CC_CHF);
                        cc.setCodigo(infoMovil.getId());
                        cc.setReconsentimiento(Constants.YESKEYSND);
                        estudiosAdapter.crearCartaConsentimiento(cc);

                        MovilInfo movilInfo = new MovilInfo();
                        movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                        movilInfo.setDeviceid(infoMovil.getDeviceId());
                        movilInfo.setUsername(username);
                        movilInfo.setToday(new Date());
                        ParticipanteProcesos procesos = participante.getProcesos();
                        procesos.setMovilInfo(movilInfo);
                        if (esElegible) {
                            int ceroDefaul = 0;
                            if (tieneValor(cmDomicilio) && cmDomicilio.equals(Constants.YES)) {
                                procesos.setCoordenadas("2");
                            } else {
                                procesos.setCoordenadas("0");
                            }
                            if (tieneValor(cambiarPadre) && cambiarPadre.equals(Constants.YES)) {
                                if (tieneValor(nombrepadre)) participante.setNombre1Padre(nombrepadre);
                                if (tieneValor(nombrepadre2)) participante.setNombre2Padre(nombrepadre2);
                                if (tieneValor(apellidopadre)) participante.setApellido1Padre(apellidopadre);
                                if (tieneValor(apellidopadre2)) participante.setApellido2Padre(apellidopadre2);
                            }
                            if (tieneValor(cambiarMadre) && cambiarMadre.equals(Constants.YES)) {
                                if (tieneValor(nombremadre)) participante.setNombre1Madre(nombremadre);
                                if (tieneValor(nombremadre2)) participante.setNombre2Madre(nombremadre2);
                                if (tieneValor(apellidomadre)) participante.setApellido1Madre(apellidomadre);
                                if (tieneValor(apellidomadre2)) participante.setApellido2Madre(apellidomadre2);
                            }
                            if (tieneValor(mismoTutorSN) && mismoTutorSN.equals(Constants.NO)) {
                                participante.setRelacionFamiliarTutor(cc.getRelacionFamiliarTutor());
                                participante.setNombre1Tutor(cc.getNombre1Tutor());
                                participante.setNombre2Tutor(cc.getNombre2Tutor());
                                participante.setApellido1Tutor(cc.getApellido1Tutor());
                                participante.setApellido2Tutor(cc.getApellido2Tutor());
                            } else if (edadMeses >= 216) { //cuando es mayor de edad no se pregunta tutor.. poner el mismo participante
                                participante.setNombre1Tutor(participante.getNombre1());
                                participante.setNombre2Tutor(participante.getNombre2());
                                participante.setApellido1Tutor(participante.getApellido1());
                                participante.setApellido2Tutor(participante.getApellido2());
                                participante.setRelacionFamiliarTutor(Constants.REL_FAM_MISMO_PART);
                            }
                            participante.setRecordDate(new Date());
                            participante.setRecordUser(username);
                            participante.setDeviceid(infoMovil.getDeviceId());
                            participante.setEstado('0');
                            participante.setPasive('0');
                            //Guarda nuevo participante
                            estudiosAdapter.editarParticipante(participante);

                            if (tieneValor(telefonoCel1) || tieneValor(telefonoCel2) || tieneValor(telefonoCel3)) {
                                guardarContactoParticipante(Constants.YESKEYSND, null, null, participante.getCasa().getBarrio().getCodigo().toString(), null, telefonoCel1, telefonoClasif1, telefonoOper1,
                                        telefonoCel2, telefonoClasif2, telefonoOper2, telefonoCel3, telefonoClasif3, telefonoOper3);
                            }

                            if (procesos.getEstPart().equals(0)) {
                                procesos.setEstPart(1);
                            }
                            procesos.setConsChf(Constants.NO);
                            estudiosAdapter.actualizarParticipanteProcesos(procesos);

                            openMenuInfo();

                            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
                            toast.show();
                            finish();
                        } else {
                            //Si no es elegible entonces desactivar el proceso de Covid19
                            procesos.setConsChf(Constants.NO);
                            procesos.setMovilInfo(movilInfo);
                            estudiosAdapter.actualizarParticipanteProcesos(procesos);
                            openMenuInfo();
                            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noRegistraIngreso), Toast.LENGTH_LONG);
                            toast.show();
                            finish();
                        }

                    } else {
                        //Si no acepta participar entonces desactivar el proceso de Covid19
                        MovilInfo movilInfo = new MovilInfo();
                        movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                        movilInfo.setDeviceid(infoMovil.getDeviceId());
                        movilInfo.setUsername(username);
                        movilInfo.setToday(new Date());
                        ParticipanteProcesos procesos = participante.getProcesos();
                        procesos.setConsChf(Constants.NO);
                        procesos.setMovilInfo(movilInfo);
                        estudiosAdapter.actualizarParticipanteProcesos(procesos);

                        openMenuInfo();

                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noRegistraIngreso), Toast.LENGTH_LONG);
                        toast.show();
                        finish();
                    }
                }else {
                    MovilInfo movilInfo = new MovilInfo();
                    movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                    movilInfo.setDeviceid(infoMovil.getDeviceId());
                    movilInfo.setUsername(username);
                    movilInfo.setToday(new Date());
                    ParticipanteProcesos procesos = participante.getProcesos();
                    procesos.setConsChf(Constants.NO);
                    procesos.setMovilInfo(movilInfo);
                    estudiosAdapter.actualizarParticipanteProcesos(procesos);

                    openMenuInfo();

                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noAceptaTamizajePersona), Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
            }else {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.visitaNoExitosa)+ " " +razonVisNoExit, Toast.LENGTH_LONG);
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

    private void openMenuInfo(){
        Intent i = new Intent(getApplicationContext(),
                MenuInfoActivity.class);
        i.putExtra(ConstantsDB.CODIGO, participante.getCodigo());
        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

   private void guardarContactoParticipante(String propio, String nomContacto, String direContacto, String barrioContacto, String otrobarrioContacto,
                                             String numero1, String tipo1, String operadora1, String numero2, String tipo2, String operadora2, String numero3, String tipo3, String operadora3){
        ContactoParticipante contacto = new ContactoParticipante();
        contacto.setId(infoMovil.getId());
        contacto.setRecordDate(new Date());
        contacto.setRecordUser(username);
        contacto.setDeviceid(infoMovil.getDeviceId());
        contacto.setEstado('0');
        contacto.setPasive('0');
        contacto.setParticipante(participante);
        contacto.setNombre(nomContacto);
        contacto.setDireccion(direContacto);
        if (tieneValor(barrioContacto)) {
            Barrio barrio1 = estudiosAdapter.getBarrio(CatalogosDBConstants.nombre + "= '" + barrioContacto + "'", null);
            contacto.setBarrio(barrio1);
        }else{
            if (propio.equals(Constants.YESKEYSND)) contacto.setBarrio(participante.getCasa().getBarrio());
        }
        contacto.setOtroBarrio(otrobarrioContacto);
        contacto.setNumero1(numero1);
        contacto.setNumero2(numero2);
        contacto.setNumero3(numero3);
        if (tieneValor(tipo1)) {
            MessageResource catTipo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tipo1 + "' and " + CatalogosDBConstants.catRoot + "='CAT_TIPO_TEL'", null);
            contacto.setTipo1(catTipo.getCatKey());
        }
        if (tieneValor(operadora1)) {
            MessageResource catOperadora = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + operadora1 + "' and " + CatalogosDBConstants.catRoot + "='CAT_OPER_TEL'", null);
            contacto.setOperadora1(catOperadora.getCatKey());
        }
        if (tieneValor(tipo2)) {
            MessageResource catTipo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tipo2 + "' and " + CatalogosDBConstants.catRoot + "='CAT_TIPO_TEL'", null);
            contacto.setTipo2(catTipo.getCatKey());
        }
        if (tieneValor(operadora2)) {
            MessageResource catOperadora = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + operadora2 + "' and " + CatalogosDBConstants.catRoot + "='CAT_OPER_TEL'", null);
            contacto.setOperadora2(catOperadora.getCatKey());
        }
        if (tieneValor(tipo3)) {
            MessageResource catTipo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tipo3 + "' and " + CatalogosDBConstants.catRoot + "='CAT_TIPO_TEL'", null);
            contacto.setTipo3(catTipo.getCatKey());
        }
        if (tieneValor(operadora3)) {
            MessageResource catOperadora = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + operadora3 + "' and " + CatalogosDBConstants.catRoot + "='CAT_OPER_TEL'", null);
            contacto.setOperadora3(catOperadora.getCatKey());
        }
        contacto.setEsPropio(propio);
        estudiosAdapter.crearContactoParticipante(contacto);
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
