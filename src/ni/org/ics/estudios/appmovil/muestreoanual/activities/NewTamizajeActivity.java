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
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCovid19;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.muestreoanual.forms.TamizajeForm;
import ni.org.ics.estudios.appmovil.muestreoanual.forms.TamizajeFormLabels;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class NewTamizajeActivity extends FragmentActivity implements
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
    private TamizajeFormLabels labels = new TamizajeFormLabels();
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private GPSTracker gps;
    //private static CasaCohorteFamilia casa = new CasaCohorteFamilia();
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    //private static CasaCohorteFamilia casaCHF = new CasaCohorteFamilia();
    private Integer edadAnios = 0;
    private Integer edadSemanas = 0;
    private String tipoIngreso = "";
    private final String TIPO_DENGUE = "Dengue";
    private final String TIPO_INFLUENZA = "Influenza";
    //private final String TIPO_AMBOS = "Ambos";
    private final String TIPO_INFLUENZA_UO1 = "UO1";
    private List<MessageResource> catMeses = new ArrayList<MessageResource>();
    private String[] catRelFamMenorEdad; //relación familiar del tutor cuando es menor de edad
    private String[] catRelFamMayorEdad; //relación familiar del tutor cuando es mayor de edad
    private String[] catVerifTutAlf; //cosas a verificar cuando tutor es alfabeto
    private String[] catVerifTutNoAlf; //cosas a verificar cuando tutor no es alfabeto
    private Date fechaNacimiento = null;
    private final int EDAD_LIMITE_INGRESO = 11; //justo antes de cumplir 11 anios
    private final int EDAD_MINIMA_DENGUE = 2; //ANIOS
    private final int EDAD_MINIMA_FLU = 0; //ANIOS
    private final int EDAD_MAXIMA_FLU_UO1 = 4; //SEMANAS
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FileUtils.storageReady()) {
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        setContentView(R.layout.activity_data_enter);
        //casaCHF = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASA);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NewTamizajeActivity.this);
        gps = new GPSTracker(NewTamizajeActivity.this);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
        mWizardModel = new TamizajeForm(this,mPass);
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
        onPageTreeChanged();
        updateBottomBar();

        estudiosAdapter.open();
        catMeses = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", CatalogosDBConstants.order);
        catRelFamMayorEdad = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", CatalogosDBConstants.order);
        catRelFamMenorEdad = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR' and "+CatalogosDBConstants.catKey + " not in( '8', '9')", CatalogosDBConstants.order);
        catVerifTutNoAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
        catVerifTutAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catKey + " in ('1','2','3','6') and " + CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
        estudiosAdapter.close();

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
            if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.BarcodePage")) {
                BarcodePage bp = (BarcodePage) page;
                if (bp.ismValRange() || bp.ismValPattern()) {
                    String valor = bp.getData().getString(TextPage.SIMPLE_DATA_KEY);
                    if ((bp.ismValRange() && (bp.getmGreaterOrEqualsThan() > Double.valueOf(valor) || bp.getmLowerOrEqualsThan() < Double.valueOf(valor)))
                            || (bp.ismValPattern() && !valor.matches(bp.getmPattern()))){
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
            if (page.getTitle().equals(labels.getTipoIngreso())) {
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(TIPO_DENGUE)) {
                    tipoIngreso = TIPO_DENGUE;
                }
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(TIPO_INFLUENZA)) {
                    tipoIngreso = TIPO_INFLUENZA;
                }
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(TIPO_INFLUENZA_UO1)) {
                    tipoIngreso = TIPO_INFLUENZA_UO1;
                }
                changeStatus(mWizardModel.findByKey(labels.getFechaNacimiento()), true);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFechaNacimiento())) {
                DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    fechaNacimiento = mDateFormat.parse(page.getData().getString(DatePage.SIMPLE_DATA_KEY));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
                CalcularEdad calEdad = new CalcularEdad(fechaNacimiento);
                String[] edad = calEdad.getEdad().split("/");
                edadAnios = Integer.parseInt(edad[0]);
                edadSemanas = calEdad.getEdadSemanas();
                if (!(edadAnios >= EDAD_MINIMA_DENGUE && edadAnios < EDAD_LIMITE_INGRESO) && !(edadAnios >= EDAD_MINIMA_FLU && edadAnios < EDAD_LIMITE_INGRESO)){
                    changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), false);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), true);
                    //notificarCambios = false;
                    Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.noEsElegible) + " Dengue", Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(99);
                }else {
                    if (!(edadAnios >= EDAD_MINIMA_DENGUE && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE))){
                        Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noEsElegible) + " Dengue",Toast.LENGTH_LONG);
                        toast.show();
                    }
                    if (!(edadAnios >= EDAD_MINIMA_FLU && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.contains(TIPO_INFLUENZA))){
                        Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.noEsElegible) + " Influenza", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    if (edadSemanas > EDAD_MAXIMA_FLU_UO1 && tipoIngreso.contains(TIPO_INFLUENZA_UO1)){
                        Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.noEsElegible) + " UO1 ", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    if (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE)){
                        if (!(edadAnios >= EDAD_MINIMA_DENGUE && edadAnios < EDAD_LIMITE_INGRESO)) {
                            changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), false);
                            //notificarCambios = false;
                            changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), false);
                            //notificarCambios = false;
                            resetForm(99);
                        }else{
                            changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), true);
                            //notificarCambios = false;
                        }
                    }
                    if (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA)){
                        if (!(edadAnios >= EDAD_MINIMA_FLU && edadAnios < EDAD_LIMITE_INGRESO)) {
                            changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), false);
                            //notificarCambios = false;
                            changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), true);
                            //notificarCambios = false;
                            resetForm(99);
                        }else{
                            changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), true);
                            //notificarCambios = false;
                        }
                    }
                    if (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1)){
                        if (edadSemanas > EDAD_MAXIMA_FLU_UO1) {
                            changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), false);
                            //notificarCambios = false;
                            changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), true);
                            //notificarCambios = false;
                            resetForm(99);
                        }else{
                            changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), true);
                            //notificarCambios = false;
                        }
                    }
                }
                SingleFixedChoicePage pagetmp = (SingleFixedChoicePage)mWizardModel.findByKey(labels.getRelacionFamiliarTutor());
                pagetmp.setChoices(edadAnios<18?catRelFamMenorEdad:catRelFamMayorEdad);


                NewDatePage dxDengue = (NewDatePage)mWizardModel.findByKey(labels.getFechaDiagDengue());
                NewDatePage hospDate = (NewDatePage)mWizardModel.findByKey(labels.getFechaHospDengue());

                DateMidnight fromDate = DateMidnight.parse(page.getData().getString(DatePage.SIMPLE_DATA_KEY), DateTimeFormat.forPattern("dd/MM/yyyy"));
                DateMidnight toDate = new DateMidnight(new Date().getTime());
                //Validacion de campos segun fecha de nac de participante y fecha actual
                dxDengue.setRangeValidation(true,fromDate, toDate);
                hospDate.setRangeValidation(true, fromDate, toDate);

                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaTamizajePersona())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCriteriosInclusion()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoParticipaPersona()), !visible);
                //notificarCambios = false;
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
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getCriteriosInclusion())){
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                visible = test!=null && test.size()==2;
                changeStatus(mWizardModel.findByKey(labels.getVivienda()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTiempoResidencia()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDiagDengue()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), visible && edadAnios>5 && edadAnios < EDAD_LIMITE_INGRESO); //10 porque es el limite de edad q se va a permitir al momento del desarrollo (marzo 2018)
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteDengue()), visible && (edadAnios >= EDAD_MINIMA_DENGUE && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE)));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenza()), visible && (edadAnios >= EDAD_MINIMA_FLU && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA)));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenzaUO1()), visible && (edadSemanas <= EDAD_MAXIMA_FLU_UO1 && tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1)));
                //notificarCambios = false;
                if(test!=null && test.size()!=2){
                    resetForm(99);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noCumpleCriteriosInclusion),Toast.LENGTH_LONG);
                    toast.show();
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getVivienda())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Propia"));
                Page pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTiempoResidencia());
                if (tieneValor(pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY))) {
                    boolean tiempoValido = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null &&
                            (pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Seis Meses a Dos Años")
                                    || pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Dos Años ó Más"));
                    //propia y con tiempo de residencia válida
                    if (visible && !tiempoValido) {
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.noCumpleCriteriosInclusion, Toast.LENGTH_LONG);
                        toast.show();
                        resetForm(99);
                    }
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), (visible && tiempoValido));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), (visible && tiempoValido));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), (visible && tiempoValido));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDiagDengue()), (visible && tiempoValido));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), (visible && tiempoValido) && edadAnios>5 && edadAnios < EDAD_LIMITE_INGRESO); //10 porque es el limite de edad q se va a permitir al momento del desarrollo (marzo 2018)
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteDengue()), (visible && tiempoValido) && (edadAnios >= EDAD_MINIMA_DENGUE && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE)));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenza()), (visible && tiempoValido) && (edadAnios >= EDAD_MINIMA_FLU && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA)));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenzaUO1()), (visible && tiempoValido) && (edadSemanas <= EDAD_MAXIMA_FLU_UO1 && tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1)));
                    //notificarCambios = false;
                    //es alquilada y tiene tiempo valido
                    if (!visible) {
                        tiempoValido = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Dos Años ó Más");
                        if (!tiempoValido) {
                            Toast toast = Toast.makeText(getApplicationContext(), R.string.noCumpleCriteriosInclusion, Toast.LENGTH_LONG);
                            toast.show();
                            resetForm(99);
                        }
                        changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), tiempoValido);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), tiempoValido);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), tiempoValido);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getDiagDengue()), tiempoValido);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), tiempoValido && edadAnios>5 && edadAnios < EDAD_LIMITE_INGRESO); //10 porque es el limite de edad q se va a permitir al momento del desarrollo (marzo 2018)
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteDengue()), tiempoValido && (edadAnios >= EDAD_MINIMA_DENGUE && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE)));
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenza()), tiempoValido && (edadAnios >= EDAD_MINIMA_FLU && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA)));
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenzaUO1()), tiempoValido && (edadSemanas <= EDAD_MAXIMA_FLU_UO1 && tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1)));
                        //notificarCambios = false;
                    }
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTiempoResidencia())) {
                Page pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getVivienda());
                boolean esPropia = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Propia");
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null &&
                        (page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Seis Meses a Dos Años")
                                || page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Dos Años ó Más"));
                if (esPropia && !visible){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.noCumpleCriteriosInclusion,Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(99);
                }
                changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), (esPropia && visible));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), (esPropia && visible));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), (esPropia && visible));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDiagDengue()), (esPropia && visible));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), (esPropia && visible) && edadAnios>5 && edadAnios < EDAD_LIMITE_INGRESO); //10 porque es el limite de edad q se va a permitir al momento del desarrollo (marzo 2018)
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteDengue()), (esPropia && visible) && (edadAnios >= EDAD_MINIMA_DENGUE && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE)));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenza()), (esPropia && visible) && (edadAnios >= EDAD_MINIMA_FLU && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA)));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenzaUO1()), (esPropia && visible) && (edadSemanas <= EDAD_MAXIMA_FLU_UO1 && tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1)));
                //notificarCambios = false;

                if (!esPropia){
                    visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Dos Años ó Más");
                    if (!visible) {
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.noCumpleCriteriosInclusion, Toast.LENGTH_LONG);
                        toast.show();
                        resetForm(99);
                    }
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDiagDengue()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), visible && edadAnios>5 && edadAnios < EDAD_LIMITE_INGRESO); //10 porque es el limite de edad q se va a permitir al momento del desarrollo (marzo 2018)
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteDengue()), visible && (edadAnios >= EDAD_MINIMA_DENGUE && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE)));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenza()), visible && (edadAnios >= EDAD_MINIMA_FLU && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA)));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenzaUO1()), visible && (edadSemanas <= EDAD_MAXIMA_FLU_UO1 && tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1)));
                    //notificarCambios = false;
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDondeAsisteProblemasSalud())) {
                if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null) {
                    if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Centro de Salud Sócrates Flores")) {
                        changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true); //aunque sea CSF preguntar si esta dispuesto a ir solamente al CSF
                    } else if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro centro de salud")) {
                        changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), true);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
                    } else if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Puesto de Salud")) {
                        changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), true);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
                    } else {
                        changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
                    }
                }else{
                    changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), false);
                }
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfermedad())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCualEnfermedad()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTratamiento()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtroTratamiento()), false);
                //notificarCambios = false;

                Calendar fechas = Calendar.getInstance();
                int anioActual = fechas.get(Calendar.YEAR);
                fechas.setTime(fechaNacimiento);
                int anioNac = fechas.get(Calendar.YEAR);

                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio1());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio2());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio3());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio4());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio5());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio6());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio7());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio8());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio9());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio10());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio11());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio12());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio13());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio14());
                pagetmp.setRangeValidation(true, anioNac, anioActual);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes1())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio1())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes2())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio2())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes3())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio3())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes4())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio4())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes5())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio5())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes6())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio6())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes7())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio7())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes8())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio8())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes9())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio9())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes10())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio10())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes11())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio11())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes12())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio12())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes13())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio13())) page.resetData(new Bundle());
                onPageTreeChanged();
            }if (page.getTitle().equals(labels.getEnfCronicaMes14())) {
                if (!esMesValido(page, labels.getEnfCronicaAnio14())) page.resetData(new Bundle());
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCualEnfermedad())) {
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                visible = test != null && test.contains("Cáncer de cualquier tipo");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes1()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Cardiopatías");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes2()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Enfermedades hematológicas");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio3()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes3()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Enfermedades Inmunosupresoras");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio4()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes4()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Enfermedades Renales");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio5()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes5()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Epilepsia");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio6()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes6()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Leucemia");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio7()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes7()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Metabólica crónica (Diabetes)");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio8()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes8()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Otra");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio9()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes9()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraEnfCronica()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Trastornos Psiquiatricos/Depresión");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio10()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes10()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Asma");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio11()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes11()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Hipertensión arterial");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio12()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes12()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Artritis Reumatoide");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio13()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes13()), visible);
                //notificarCambios = false;

                visible = test != null && test.contains("Tuberculosis");
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio14()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes14()), visible);
                //notificarCambios = false;

                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDiagDengue())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFechaDiagDengue()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getHospDengue()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getHospDengue())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFechaHospDengue()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTratamiento())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCualTratamiento()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCualTratamiento())) {
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                visible = test != null && test.contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroTratamiento()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAsentimientoVerbal())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteDengue()), visible && (edadAnios >= EDAD_MINIMA_DENGUE && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE)));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenza()), visible && (edadAnios >= EDAD_MINIMA_FLU && edadAnios < EDAD_LIMITE_INGRESO) && (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA)));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenzaUO1()), visible && (edadSemanas <= EDAD_MAXIMA_FLU_UO1 && tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1)));
                //notificarCambios = false;
                if(!visible) {
                    resetForm(98);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noDaAsentimiento),Toast.LENGTH_LONG);
                    toast.show();
                }
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaCohorteDengue())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);

                Page pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaCohorteInfluenza());
                boolean visibleInf = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);

                Page pagetmp2 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEnfermedadInmuno());
                boolean esInmuno = pagetmp2.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp2.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);

                Page pagetmp3 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPretermino());
                boolean esPretermino = pagetmp3.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp3.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);

                changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                //En nuevo ingreso ya no se pregunta por parte D. 08/2018
                //changeStatus(mWizardModel.findByKey(labels.getAceptaParteD()), visible || (visibleInf && !esInmuno && !esPretermino));
                ////notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCasaPerteneceCohorte()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrio()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getManzana()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDireccion()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoNuevoParticipante()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1Padre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Padre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Padre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Padre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1Madre()), (visible || (visibleInf && !esInmuno && !esPretermino))&& edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Madre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Madre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Madre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRelacionFamTutor()), false);

                changeStatus(mWizardModel.findByKey(labels.getNombreContacto()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDireccionContacto()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTieneTelefono()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono1()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefono()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible || (visibleInf && !esInmuno && !esPretermino));
                //notificarCambios = false;

                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaDengue()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaDengue()), false);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaDengue())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaDengue()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaCohorteInfluenza())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getPretermino()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfermedadInmuno()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible);
                //notificarCambios = false;
                //changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), visible);
                ////notificarCambios = false;
                //changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), visible);
                //notificarCambios = false;
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaInfluenza()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaInfluenza()), false);
                //notificarCambios = false;
                if (!visible) {
                    Page pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaCohorteDengue());
                    boolean visibleDen = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                    if (!visibleDen) {
                        resetForm(97);
                    }
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaCohorteInfluenzaUO1())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getPretermino()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfermedadInmuno()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible);
                //notificarCambios = false;
                //changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), visible);
                ////notificarCambios = false;
                //changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), visible);
                //notificarCambios = false;
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaInfluenzaUO1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaInfluenzaUO1()), false);
                //notificarCambios = false;
                if (!visible) {
                    Page pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaCohorteDengue());
                    boolean visibleDen = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                    if (!visibleDen) {
                        resetForm(97);
                    }
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPretermino())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                Page pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaCohorteDengue());
                boolean visibleDen = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.noEsElegible) + " " +tipoIngreso, Toast.LENGTH_LONG);
                    toast.show();
                    if (!visibleDen) {
                        resetForm(97);
                    }
                }else {
                    Page pagetmp2 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEnfermedadInmuno());
                    boolean esInmuno = pagetmp2.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp2.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);

                    changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteUO1ParteDCovid()), ((visible && !esInmuno) || visibleDen) && tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCasaPerteneceCohorte()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getBarrio()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getManzana()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDireccion()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoNuevoParticipante()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Padre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Padre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Padre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Padre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Madre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Madre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Madre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Madre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getOtraRelacionFamTutor()), false);

                    changeStatus(mWizardModel.findByKey(labels.getNombreContacto()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDireccionContacto()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTieneTelefono()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTipoTelefono1()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefono()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), (visible && !esInmuno) || visibleDen);
                    //notificarCambios = false;
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfermedadInmuno())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                Page pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaCohorteDengue());
                boolean visibleDen = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.noEsElegible) + " "+tipoIngreso, Toast.LENGTH_LONG);
                    toast.show();
                    if (!visibleDen) {
                        resetForm(97);
                    }
                }else {
                    Page pagetmp2 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPretermino());
                    boolean esPretermino = pagetmp2.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp2.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);

                    changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteUO1ParteDCovid()), ((visible && !esPretermino) || visibleDen) && tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCasaPerteneceCohorte()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoNuevoParticipante()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Padre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Padre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Padre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Padre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Madre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Madre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Madre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Madre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getOtraRelacionFamTutor()), false);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombreContacto()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDireccionContacto()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTieneTelefono()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTipoTelefono1()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefono()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getBarrio()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getManzana()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDireccion()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), (visible && !esPretermino) || visibleDen);
                    //notificarCambios = false;
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaInfluenza())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaInfluenza()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaInfluenzaUO1())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaInfluenzaUO1()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAceptaCohorteUO1ParteDCovid())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParteD()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRazonNoAceptaParteD())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otros motivos");
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParteD()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getCasaPerteneceCohorte())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), visible);
                //notificarCambios = false;
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrio()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getManzana()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDireccion()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getRelacionFamiliarTutor())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRARELFAM);
                changeStatus(mWizardModel.findByKey(labels.getOtraRelacionFamTutor()), visible);
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
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), !visible);
                //notificarCambios = false;
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
                changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteD()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteUO1ParteDCovid()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombreContacto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDireccionContacto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTieneTelefono()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefono()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                //notificarCambios = false;


                Page pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaCohorteDengue());
                boolean visibleTmp = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible && visibleTmp);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible && visibleTmp);
                //notificarCambios = false;
                //En nuevo ingreso ya no se pregunta por parte D. 08/2018
                //changeStatus(mWizardModel.findByKey(labels.getAceptaParteD()), visible && visibleTmp);
                ////notificarCambios = false;

                pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaCohorteInfluenza());
                visibleTmp = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), visible && visibleTmp);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), visible && visibleTmp);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteUO1ParteDCovid()), visible && visibleTmp && tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1));
                //notificarCambios = false;
                //si no es influenza, es influenza UO1
                if (pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) == null || pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).isEmpty()) {
                    pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaCohorteInfluenzaUO1());
                    visibleTmp = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                    changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), visible && visibleTmp);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), visible && visibleTmp);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteUO1ParteDCovid()), visible && visibleTmp && tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1));
                    //notificarCambios = false;
                }

                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTieneTelefono())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefono()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), false);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), false);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), false);
                //notificarCambios = false;

                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTipoTelefono1())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), visible);
                //notificarCambios = false;
                TextPage pagetmp = (TextPage) mWizardModel.findByKey(labels.getNumTelefono1());
                if (visible){
                    pagetmp.setPatternValidation(true, "^$|^[8|5|7]{1}\\d{7}$");
                }else{
                    pagetmp.setPatternValidation(true, "^$|^[2]{1}\\d{7}$");
                }

                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTipoTelefono2())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), visible);
                //notificarCambios = false;
                TextPage pagetmp = (TextPage) mWizardModel.findByKey(labels.getNumTelefono2());
                if (visible){
                    pagetmp.setPatternValidation(true, "^$|^[8|5|7]{1}\\d{7}$");
                }else{
                    pagetmp.setPatternValidation(true, "^$|^[2]{1}\\d{7}$");
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTieneOtroTelefono())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void resetForm(int preg){
        if (preg>99) changeStatus(mWizardModel.findByKey(labels.getVivienda()), false);
        if (preg>99) changeStatus(mWizardModel.findByKey(labels.getTiempoResidencia()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getCualEnfermedad()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getOtraEnfCronica()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio1()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes1()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio2()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes2()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio3()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes3()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio4()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes4()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio5()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes5()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio6()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes6()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio7()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes7()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio8()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes8()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio9()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes9()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio10()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes10()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio11()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes11()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio12()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes12()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio13()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes13()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio14()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes14()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getTratamiento()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getCualTratamiento()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getOtroTratamiento()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getDiagDengue()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getFechaDiagDengue()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getHospDengue()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getFechaHospDengue()), false);

        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteDengue()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaDengue()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaDengue()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenza()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenzaUO1()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaInfluenza()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaInfluenza()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaInfluenzaUO1()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaInfluenzaUO1()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getPretermino()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getEnfermedadInmuno()), false);

        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getCasaPerteneceCohorte()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getBarrio()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getManzana()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getDireccion()), false);

        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getCodigoNuevoParticipante()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1Padre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2Padre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1Padre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2Padre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1Madre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2Madre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1Madre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2Madre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getOtraRelacionFamTutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaParteD()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteUO1ParteDCovid()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombreContacto()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getDireccionContacto()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getTieneTelefono()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefono()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getTipoTelefono1()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), false);
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

    private boolean esMesValido(Page page, String labelAnio){
        boolean esValido = true;
        NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labelAnio);
        Calendar fechas = Calendar.getInstance();
        int anioActual = fechas.get(Calendar.YEAR);
        String anio = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY);
        if (tieneValor(anio) && Integer.valueOf(anio) == anioActual ) {
            int mesA = fechas.get(Calendar.MONTH);//enero inicia en 0
            if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null) {
                Integer mesS = getMes(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
                if (mesS > mesA+1){
                    Toast toast = Toast.makeText(getApplicationContext(),R.string.mesInvalido, Toast.LENGTH_LONG);
                    toast.show();
                    esValido = false;
                }
            }
        }
        return esValido;
    }

    public Integer getMes(String mes) {
        Integer numMes = null;
        try {
            for (MessageResource message : catMeses) {
                if (message.getSpanish().equalsIgnoreCase(mes)) {
                    numMes = Integer.valueOf(message.getCatKey());
                    break;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return numMes;
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
            String sexo = datos.getString(this.getString(R.string.sexo));
            Date fechaNacimiento = null;
            Date fechaDengue = null;
            Date fechaHospitalizado = null;
            try {
                fechaNacimiento = mDateFormat.parse(datos.getString(this.getString(R.string.fechaNacimiento)));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);
                toast.show();
                finish();
            }

            String aceptaTamizajePersona = datos.getString(this.getString(R.string.aceptaTamizajePersona));
            String razonNoAceptaTamizajePersona = datos.getString(this.getString(R.string.razonNoParticipaPersona));
            String otraRazonNoAceptaTamizajePersona = datos.getString(this.getString(R.string.otraRazonNoParticipaPersona));
            String criteriosInclusion = datos.getString(this.getString(R.string.criteriosInclusion));
            String vivienda = datos.getString(this.getString(R.string.tipoVivienda));
            String tiempoResidencia = datos.getString(this.getString(R.string.tiempoResidencia));
            String enfermedad = datos.getString(this.getString(R.string.enfermedadCronica));
            String cualEnfermedad = datos.getString(this.getString(R.string.cualEnfermedad));
            String diagDengue = datos.getString(this.getString(R.string.diagDengue));
            String fechaDiagDengue = datos.getString(this.getString(R.string.fechaDiagDengue));
            String hospDengue = datos.getString(this.getString(R.string.hospDengue));
            String fechaHospDengue = datos.getString(this.getString(R.string.fechaHospDengue));
            String tratamientoIngreso = datos.getString(this.getString(R.string.tratamientoIngreso));
            String cualTratamiento = datos.getString(this.getString(R.string.cualTratamiento));
            String otroTx = datos.getString(this.getString(R.string.otroTx));//agregar en tamizaje
            String dondeAsisteProblemasSalud = datos.getString(this.getString(R.string.dondeAsisteProblemasSalud));
            String otroCentroSalud = datos.getString(this.getString(R.string.otroCentroSalud));
            String puestoSalud = datos.getString(this.getString(R.string.puestoSalud));
            String aceptaAtenderCentro = datos.getString(this.getString(R.string.aceptaAtenderCentro));
            String asentimientoVerbal = datos.getString(this.getString(R.string.asentimientoVerbal));

            String aceptaCohorteDengue = datos.getString(this.getString(R.string.aceptaCohorteDengue));
            String razonNoAceptaDengue = datos.getString(this.getString(R.string.razonNoAceptaDengue));
            String otraRazonNoAceptaDengue = datos.getString(this.getString(R.string.otraRazonNoAceptaDengue));

            String aceptaCohorteInfluenza = datos.getString(this.getString(R.string.aceptaCohorteInfluenza));
            String aceptaCohorteInfluenzaUO1 = datos.getString(this.getString(R.string.aceptaCohorteInfluenzaUO1));
            String pretermino = datos.getString(this.getString(R.string.pretermino));
            String enfermedadInmuno = datos.getString(this.getString(R.string.enfermedadInmuno));
            String razonNoAceptaInfluenza = datos.getString(this.getString(R.string.razonNoAceptaInfluenza));
            String otraRazonNoAceptaInfluenza = datos.getString(this.getString(R.string.otraRazonNoAceptaInfluenza));
            String razonNoAceptaInfluenzaUO1 = datos.getString(this.getString(R.string.razonNoAceptaInfluenzaUO1));
            String otraRazonNoAceptaInfluenzaUO1 = datos.getString(this.getString(R.string.otraRazonNoAceptaInfluenzaUO1));
            //Crea un Nuevo Registro de tamizaje
            Tamizaje tamizaje =  new Tamizaje();
            Tamizaje tamizajeInf = new Tamizaje();
            Tamizaje tamizajeInfUO1 = new Tamizaje();
            tamizaje.setCodigo(infoMovil.getId());
            tamizaje.setCohorte("CP");
            if (tieneValor(sexo)) {
                MessageResource catSexo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + sexo + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SEXO'", null);
                if (catSexo!=null) tamizaje.setSexo(catSexo.getCatKey());
            }
            tamizaje.setFechaNacimiento(fechaNacimiento);
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
            int totalCriterios = 0;
            if (tieneValor(criteriosInclusion)) {
                String keysCriterios = "";
                criteriosInclusion = criteriosInclusion.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> mcriteriosInclusion = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + criteriosInclusion + "') and "
                        + CatalogosDBConstants.catRoot + "='CP_CAT_CI'", null);
                totalCriterios = mcriteriosInclusion.size();
                for(MessageResource ms : mcriteriosInclusion) {
                    keysCriterios += ms.getCatKey() + ",";
                }
                if (!keysCriterios.isEmpty())
                    keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                tamizaje.setCriteriosInclusion(keysCriterios);
            }
            if (tieneValor(vivienda)) {
                MessageResource catVivienda = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + vivienda + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_TV'", null);
                if (catVivienda != null) tamizaje.setTipoVivienda(catVivienda.getCatKey());
            }
            if (tieneValor(tiempoResidencia)) {
                MessageResource cattiempoResidencia = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tiempoResidencia + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_TR'", null);
                if (cattiempoResidencia!=null) tamizaje.setTiempoResidencia(cattiempoResidencia.getCatKey());
            }
            if (tieneValor(enfermedad)) {
                MessageResource catEnfermedad = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfermedad + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catEnfermedad!=null) tamizaje.setEnfermedadCronica(catEnfermedad.getCatKey());

                if (enfermedad.equals(Constants.YES))
                    guardarEnfermedadesCronicas(cualEnfermedad.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','"), datos, tamizaje);
            }

            if (tieneValor(diagDengue)) {
                MessageResource catDen = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + diagDengue + "' and " + CatalogosDBConstants.catRoot + "='CAT_SND_SINFECHA'", null);
                if (catDen!=null) tamizaje.setDiagDengue(catDen.getCatKey());
            }
            if (tieneValor(hospDengue)) {
                MessageResource catHospDen = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + hospDengue + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (catHospDen!=null) tamizaje.setHospDengue(catHospDen.getCatKey());
            }
            try {
                if (tieneValor(fechaDiagDengue)) {
                    fechaDengue = mDateFormat.parse(fechaDiagDengue);
                    tamizaje.setFechaDiagDengue(fechaDengue);
                }
                if (tieneValor(fechaHospDengue)) {
                    fechaHospitalizado = mDateFormat.parse(fechaHospDengue);
                    tamizaje.setFechaHospDengue(fechaHospitalizado);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (tieneValor(tratamientoIngreso)) {
                MessageResource catTratami = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tratamientoIngreso + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (catTratami!=null) tamizaje.setTratamiento(catTratami.getCatKey());
            }
            if (tieneValor(cualTratamiento)) {
                String keysCriterios = "";
                cualTratamiento = cualTratamiento.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','");
                List<MessageResource> catTx = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + cualTratamiento + "') and "
                        + CatalogosDBConstants.catRoot + "='CPD_CAT_TRATAMIENTO'", null);
                for (MessageResource ms : catTx) {
                    keysCriterios += ms.getCatKey() + ",";
                }
                if (!keysCriterios.isEmpty())
                    keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                tamizaje.setCualTratamiento(keysCriterios);
            }
            tamizaje.setOtroTx(otroTx);
            if (tieneValor(dondeAsisteProblemasSalud)) {
                MessageResource catDondeAsisteProblemasSalud = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dondeAsisteProblemasSalud + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_DONDEASISTE'", null);
                if (catDondeAsisteProblemasSalud!=null) tamizaje.setDondeAsisteProblemasSalud(catDondeAsisteProblemasSalud.getCatKey());
            }
            if(tieneValor(otroCentroSalud)) tamizaje.setOtroCentroSalud(otroCentroSalud);
            if (tieneValor(puestoSalud)) {
                MessageResource catPuestoSalud = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + puestoSalud + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_PUESTO'", null);
                if (catPuestoSalud!=null) tamizaje.setPuestoSalud(catPuestoSalud.getCatKey());
            }
            if (tieneValor(aceptaAtenderCentro)) {
                MessageResource catAceptaAtenderCentro = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaAtenderCentro + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAceptaAtenderCentro!=null) tamizaje.setAceptaAtenderCentro(catAceptaAtenderCentro.getCatKey());
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
            tamizaje.setEmancipado("0"); //por defecto no es emancipado
            //Registrar tamizaje dengue si aplica
            if (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE)){
                Estudio estudioCDengue = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=" +Constants.COD_EST_COHORTEDENGUE, null);
                tamizaje.setEstudio(estudioCDengue);
                //Si acepta o no participar, siempre registrar tamizaje
                MessageResource catAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaCohorteDengue + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAceptaParticipar!=null) tamizaje.setAceptaParticipar(catAceptaParticipar.getCatKey());
                if (tieneValor(razonNoAceptaDengue)) {
                    MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaDengue + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
                    if (catRazonNoAceptaParticipar!=null) tamizaje.setRazonNoAceptaParticipar(catRazonNoAceptaParticipar.getCatKey());
                }
                tamizaje.setOtraRazonNoAceptaParticipar(otraRazonNoAceptaDengue);
                boolean esElegible = ((edadAnios >= EDAD_MINIMA_DENGUE && edadAnios < EDAD_LIMITE_INGRESO)
                        && (totalCriterios==2)
                        && (((tieneValor(vivienda) && vivienda.matches("Propia"))
                        && (tieneValor(tiempoResidencia) && (tiempoResidencia.matches("Seis Meses a Dos Años") || tiempoResidencia.matches("Dos Años ó Más"))))
                        || ((tieneValor(vivienda) &&vivienda.matches("Alquilada")) && (tieneValor(tiempoResidencia) && tiempoResidencia.matches("Dos Años ó Más"))))
                );
                tamizaje.setEsElegible(esElegible?Constants.YESKEYSND:Constants.NOKEYSND);
                estudiosAdapter.crearTamizaje(tamizaje);
            }
            //Registrar tamizaje influenza si aplica
            if (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA)){
                tamizajeInf = tamizaje;
                //Si acepta o no participar, siempre registrar tamizaje
                MessageResource catAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaCohorteInfluenza + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAceptaParticipar!=null) tamizajeInf.setAceptaParticipar(catAceptaParticipar.getCatKey());

                if (tieneValor(pretermino)) {
                    MessageResource catPretermino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + pretermino + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catPretermino != null) tamizajeInf.setPretermino(catPretermino.getCatKey());
                }
                if (tieneValor(enfermedadInmuno)) {
                    MessageResource catInmuno = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfermedadInmuno + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catInmuno != null) tamizajeInf.setEnfermedadInmuno(catInmuno.getCatKey());
                }
                //Recupera el estudio de la base de datos para el tamizaje
                Estudio estudioCInfluenza = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=" +Constants.COD_EST_COHORTEINFLUENZA, null);
                tamizajeInf.setCodigo(infoMovil.getId());
                tamizajeInf.setEstudio(estudioCInfluenza);
                if (tieneValor(razonNoAceptaInfluenza)) {
                    MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaInfluenza + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
                    if (catRazonNoAceptaParticipar!=null) tamizajeInf.setRazonNoAceptaParticipar(catRazonNoAceptaParticipar.getCatKey());
                }
                tamizajeInf.setOtraRazonNoAceptaParticipar(otraRazonNoAceptaInfluenza);
                if (tieneValor(enfermedad) && enfermedad.equals(Constants.YES)){
                    guardarEnfermedadesCronicas(cualEnfermedad.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','"), datos, tamizajeInf);
                }
                boolean esElegible = ((edadAnios >= EDAD_MINIMA_FLU && edadAnios < EDAD_LIMITE_INGRESO)
                        && (totalCriterios==2)
                        && (((tieneValor(vivienda) && vivienda.matches("Propia"))
                        && (tieneValor(tiempoResidencia) && (tiempoResidencia.matches("Seis Meses a Dos Años") || tiempoResidencia.matches("Dos Años ó Más"))))
                        || ((tieneValor(vivienda) &&vivienda.matches("Alquilada")) && (tieneValor(tiempoResidencia) && tiempoResidencia.matches("Dos Años ó Más"))))
                        && (tieneValor(pretermino) && pretermino.equalsIgnoreCase(Constants.NO))
                        && (tieneValor(enfermedadInmuno) && enfermedadInmuno.equalsIgnoreCase(Constants.NO))
                );
                tamizajeInf.setEsElegible(esElegible?Constants.YESKEYSND:Constants.NOKEYSND);
                estudiosAdapter.crearTamizaje(tamizajeInf);
            }
            //Registrar tamizaje influenza uo1 si aplica
            if (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1)){
                tamizajeInfUO1 = tamizaje;
                //Si acepta o no participar, siempre registrar tamizaje
                MessageResource catAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaCohorteInfluenzaUO1 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAceptaParticipar!=null) tamizajeInfUO1.setAceptaParticipar(catAceptaParticipar.getCatKey());

                if (tieneValor(pretermino)) {
                    MessageResource catPretermino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + pretermino + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catPretermino != null) tamizajeInfUO1.setPretermino(catPretermino.getCatKey());
                }
                if (tieneValor(enfermedadInmuno)) {
                    MessageResource catInmuno = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfermedadInmuno + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catInmuno != null) tamizajeInfUO1.setEnfermedadInmuno(catInmuno.getCatKey());
                }
                //Recupera el estudio de la base de datos para el tamizaje
                Estudio estudioCInfluenza = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=" +Constants.COD_EST_UO1, null);
                tamizajeInfUO1.setCodigo(infoMovil.getId());
                tamizajeInfUO1.setEstudio(estudioCInfluenza);
                if (tieneValor(razonNoAceptaInfluenzaUO1)) {
                    MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaInfluenzaUO1 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
                    if (catRazonNoAceptaParticipar!=null) tamizajeInfUO1.setRazonNoAceptaParticipar(catRazonNoAceptaParticipar.getCatKey());
                }
                tamizajeInfUO1.setOtraRazonNoAceptaParticipar(otraRazonNoAceptaInfluenzaUO1);
                if (tieneValor(enfermedad) && enfermedad.equals(Constants.YES)){
                    guardarEnfermedadesCronicas(cualEnfermedad.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','"), datos, tamizajeInfUO1);
                }
                boolean esElegible = ((edadSemanas <= EDAD_MAXIMA_FLU_UO1)
                        && (totalCriterios==2)
                        && (((tieneValor(vivienda) && vivienda.matches("Propia"))
                        && (tieneValor(tiempoResidencia) && (tiempoResidencia.matches("Seis Meses a Dos Años") || tiempoResidencia.matches("Dos Años ó Más"))))
                        || ((tieneValor(vivienda) &&vivienda.matches("Alquilada")) && (tieneValor(tiempoResidencia) && tiempoResidencia.matches("Dos Años ó Más"))))
                        && (tieneValor(pretermino) && pretermino.equalsIgnoreCase(Constants.NO))
                        && (tieneValor(enfermedadInmuno) && enfermedadInmuno.equalsIgnoreCase(Constants.NO))
                );
                tamizajeInfUO1.setEsElegible(esElegible?Constants.YESKEYSND:Constants.NOKEYSND);
                estudiosAdapter.crearTamizaje(tamizajeInfUO1);
            }

            //Pregunta si acepta realizar el tamizaje
            if (tamizaje.getAceptaTamizajePersona().equals(Constants.YESKEYSND)) {

                String aceptaParteB = datos.getString(this.getString(R.string.aceptaParteBDengue));
                String aceptaParteC = datos.getString(this.getString(R.string.aceptaParteCDengue));
                String aceptaParteD = datos.getString(this.getString(R.string.aceptaParteDDengue));

                String aceptaParteBInf = datos.getString(this.getString(R.string.aceptaParteBInfUO1));
                String aceptaParteCInf = datos.getString(this.getString(R.string.aceptaParteCInfUO1));
                String aceptaCohorteUO1ParteDCovid = datos.getString(this.getString(R.string.aceptaCohorteUO1ParteDCovid));
                String razonNoAceptaParteDCovid = datos.getString(this.getString(R.string.razonNoAceptaParteDCovid));
                String otraRazonNoAceptaParteDCovid = datos.getString(this.getString(R.string.otraRazonNoAceptaParteDCovid));

                String aceptaContactoFuturo = datos.getString(this.getString(R.string.aceptaContactoFuturo));

                String codigoCasaCohorte = datos.getString(this.getString(R.string.codigoCasaCohorte));
                String codigoNuevaCasaCohorte = datos.getString(this.getString(R.string.codigoNuevaCasaCohorte));
                String nombre1JefeFamilia = datos.getString(this.getString(R.string.nombre1JefeFamilia));
                String nombre2JefeFamilia = datos.getString(this.getString(R.string.nombre2JefeFamilia));
                String apellido1JefeFamilia = datos.getString(this.getString(R.string.apellido1JefeFamilia));
                String apellido2JefeFamilia = datos.getString(this.getString(R.string.apellido2JefeFamilia));
                String barrio = datos.getString(this.getString(R.string.barrio));
                String manzana = datos.getString(this.getString(R.string.manzana));
                String direccion = datos.getString(this.getString(R.string.direccion));

                String codigoNuevoParticipante = datos.getString(this.getString(R.string.codigoNuevoParticipante));
                String nombre1 = datos.getString(this.getString(R.string.nombre1));
                String nombre2 = datos.getString(this.getString(R.string.nombre2));
                String apellido1 = datos.getString(this.getString(R.string.apellido1));
                String apellido2 = datos.getString(this.getString(R.string.apellido2));
                String nombre1Padre = datos.getString(this.getString(R.string.nombre1Padre));
                String nombre2Padre = datos.getString(this.getString(R.string.nombre2Padre));
                String apellido1Padre = datos.getString(this.getString(R.string.apellido1Padre));
                String apellido2Padre = datos.getString(this.getString(R.string.apellido2Padre));
                String nombre1Madre = datos.getString(this.getString(R.string.nombre1Madre));
                String nombre2Madre = datos.getString(this.getString(R.string.nombre2Madre));
                String apellido1Madre = datos.getString(this.getString(R.string.apellido1Madre));
                String apellido2Madre = datos.getString(this.getString(R.string.apellido2Madre));
                String nombre1Tutor = datos.getString(this.getString(R.string.nombre1Tutor));
                String nombre2Tutor = datos.getString(this.getString(R.string.nombre2Tutor));
                String apellido1Tutor = datos.getString(this.getString(R.string.apellido1Tutor));
                String apellido2Tutor = datos.getString(this.getString(R.string.apellido2Tutor));
                String relacionFamiliarTutor = datos.getString(this.getString(R.string.relacionFamiliarTutor));
                String otraRelacionFamTutor = datos.getString(this.getString(R.string.otraRelacionFam));

                String participanteOTutorAlfabeto = datos.getString(this.getString(R.string.participanteOTutorAlfabeto));
                String testigoPresente = datos.getString(this.getString(R.string.testigoPresente));
                String nombre1Testigo = datos.getString(this.getString(R.string.nombre1Testigo));
                String nombre2Testigo = datos.getString(this.getString(R.string.nombre2Testigo));
                String apellido1Testigo = datos.getString(this.getString(R.string.apellido1Testigo));
                String apellido2Testigo = datos.getString(this.getString(R.string.apellido2Testigo));

                String nombreContacto = datos.getString(this.getString(R.string.nombreContacto));
                String barrioContacto = datos.getString(this.getString(R.string.barrioContacto));
                String direccionContacto = datos.getString(this.getString(R.string.direccionContacto));
                String numTelefono1 = datos.getString(this.getString(R.string.numTelefono1));
                String numTelefono2 = datos.getString(this.getString(R.string.numTelefono2));
                String operadoraTelefono1 = datos.getString(this.getString(R.string.operadoraTelefono1));
                String operadoraTelefono2 = datos.getString(this.getString(R.string.operadoraTelefono2));
                String tipoTelefono1 = datos.getString(this.getString(R.string.tipoTelefono1));
                String tipoTelefono2 = datos.getString(this.getString(R.string.tipoTelefono2));

                String verifTutor = datos.getString(this.getString(R.string.verifTutor).replaceAll("25. ",""));//agregar en carta

                Boolean aceptaDengue = tieneValor(aceptaCohorteDengue) && aceptaCohorteDengue.equalsIgnoreCase(Constants.YES);
                Boolean aceptaInfluenza = tieneValor(aceptaCohorteInfluenza) && aceptaCohorteInfluenza.equalsIgnoreCase(Constants.YES);
                Boolean aceptaInfluenzaUO1 = tieneValor(aceptaCohorteInfluenzaUO1) && aceptaCohorteInfluenzaUO1.equalsIgnoreCase(Constants.YES);
                Boolean esPretermino = tieneValor(pretermino) && pretermino.equalsIgnoreCase(Constants.YES);
                Boolean padeceEnfInmuno = tieneValor(enfermedadInmuno) && enfermedadInmuno.equalsIgnoreCase(Constants.YES);

                //Registrar casa (si es nueva), participante y consentimiento sólo si acepta participar en alguno de los estudios
                if (aceptaDengue || ((aceptaInfluenza || aceptaInfluenzaUO1)&& !esPretermino && !padeceEnfInmuno)) {
                    String estudios = "";
                    Participante participante;
                    ParticipanteProcesos procesos;
                    String na = "NA";
                    int ceroDefaul = 0;
                    Integer codigo = 0;
                    //Creamos un nuevo participante
                    participante = new Participante();
                    procesos = new ParticipanteProcesos();
                    codigo = Integer.parseInt(codigoNuevoParticipante);

                    Participante existeParti = estudiosAdapter.getParticipante(codigo);
                    if (existeParti.getCodigo() == null) {
                        Casa casaCohorte = null;
                        if (tieneValor(codigoCasaCohorte)) {
                            casaCohorte = estudiosAdapter.getCasa(Integer.valueOf(codigoCasaCohorte));
                        }else{
                            //se creará casa
                            casaCohorte = new Casa();
                            casaCohorte.setCodigo(Integer.valueOf(codigoNuevaCasaCohorte));
                            Barrio barrio1 = estudiosAdapter.getBarrio(CatalogosDBConstants.nombre + "= '" + barrio + "'", null);
                            casaCohorte.setBarrio(barrio1);
                            casaCohorte.setManzana(manzana);
                            casaCohorte.setDireccion(direccion);
                            casaCohorte.setNombre1JefeFamilia(nombre1JefeFamilia);
                            casaCohorte.setNombre2JefeFamilia(nombre2JefeFamilia);
                            casaCohorte.setApellido1JefeFamilia(apellido1JefeFamilia);
                            casaCohorte.setApellido2JefeFamilia(apellido2JefeFamilia);
                            casaCohorte.setRecordDate(new Date());
                            casaCohorte.setRecordUser(username);
                            casaCohorte.setDeviceid(infoMovil.getDeviceId());
                            casaCohorte.setEstado('0');
                            casaCohorte.setPasive('0');
                            if (gps.canGetLocation()) {
                                casaCohorte.setLatitud(gps.getLatitude());
                                casaCohorte.setLongitud(gps.getLongitude());
                            }
                            estudiosAdapter.crearCasa(casaCohorte);
                        }
                        participante.setCasa(casaCohorte);
                        //crear participante
                        participante.setCodigo(codigo);
                        if (tieneValor(nombre1)) participante.setNombre1(nombre1);
                        if (tieneValor(nombre2)) participante.setNombre2(nombre2);
                        if (tieneValor(apellido1)) participante.setApellido1(apellido1);
                        if (tieneValor(apellido2)) participante.setApellido2(apellido2);
                        if (tieneValor(nombre1Padre)) {
                            if (tieneValor(nombre1Padre)) participante.setNombre1Padre(nombre1Padre);
                            if (tieneValor(nombre2Padre)) participante.setNombre2Padre(nombre2Padre);
                            if (tieneValor(apellido1Padre)) participante.setApellido1Padre(apellido1Padre);
                            if (tieneValor(apellido2Padre)) participante.setApellido2Padre(apellido2Padre);
                        } else {
                            participante.setNombre1Padre(na);
                            participante.setNombre2Padre(na);
                            participante.setApellido1Padre(na);
                            participante.setApellido2Padre(na);
                        }
                        if (tieneValor(nombre1Madre)) {
                            if (tieneValor(nombre1Madre)) participante.setNombre1Madre(nombre1Madre);
                            if (tieneValor(nombre2Madre)) participante.setNombre2Madre(nombre2Madre);
                            if (tieneValor(apellido1Madre)) participante.setApellido1Madre(apellido1Madre);
                            if (tieneValor(apellido2Madre)) participante.setApellido2Madre(apellido2Madre);
                        } else {
                            participante.setNombre1Madre(na);
                            participante.setNombre2Madre(na);
                            participante.setApellido1Madre(na);
                            participante.setApellido2Madre(na);
                        }

                        if (tieneValor(sexo)) {
                            MessageResource catSexo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + sexo + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SEXO'", null);
                            if (catSexo != null) participante.setSexo(catSexo.getCatKey());
                        }
                        participante.setFechaNac(fechaNacimiento);
                        participante.setRecordDate(new Date());
                        participante.setRecordUser(username);
                        participante.setDeviceid(infoMovil.getDeviceId());
                        participante.setEstado('0');
                        participante.setPasive('0');
                        //ahora datos de tutor en tabla participante
                        if (tieneValor(nombre1Tutor)) {
                            participante.setNombre1Tutor(nombre1Tutor);
                        }
                        if (tieneValor(nombre2Tutor)) {
                            participante.setNombre2Tutor(nombre2Tutor);
                        }
                        if (tieneValor(apellido1Tutor)) {
                            participante.setApellido1Tutor(apellido1Tutor);
                        }
                        if (tieneValor(apellido2Tutor)) {
                            participante.setApellido2Tutor(apellido2Tutor);
                        }
                        if (tieneValor(relacionFamiliarTutor)) {
                            MessageResource catRelacionFamiliarTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relacionFamiliarTutor + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_RFTUTOR'", null);
                            if (catRelacionFamiliarTutor!=null) participante.setRelacionFamiliarTutor(catRelacionFamiliarTutor.getCatKey());
                        } else {
                            participante.setRelacionFamiliarTutor(Constants.REL_FAM_MISMO_PART);
                            participante.setNombre1Tutor(participante.getNombre1());
                            participante.setNombre2Tutor(participante.getNombre2());
                            participante.setApellido1Tutor(participante.getApellido1());
                            participante.setApellido2Tutor(participante.getApellido2());
                        }
                        //Guarda nuevo participante
                        estudiosAdapter.crearParticipante(participante);

                        ContactoParticipante contacto = new ContactoParticipante();
                        contacto.setId(infoMovil.getId());
                        contacto.setRecordDate(new Date());
                        contacto.setRecordUser(username);
                        contacto.setDeviceid(infoMovil.getDeviceId());
                        contacto.setEstado('0');
                        contacto.setPasive('0');
                        contacto.setParticipante(participante);
                        contacto.setNombre(nombreContacto);
                        contacto.setDireccion(direccionContacto);
                        if (tieneValor(barrioContacto)) {
                            Barrio barrio1 = estudiosAdapter.getBarrio(CatalogosDBConstants.nombre + "= '" + barrioContacto + "'", null);
                            contacto.setBarrio(barrio1);
                        }
                        contacto.setNumero1(numTelefono1);
                        contacto.setNumero2(numTelefono2);
                        if (tieneValor(tipoTelefono1)) {
                            MessageResource catTipo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tipoTelefono1 + "' and " + CatalogosDBConstants.catRoot + "='CAT_TIPO_TEL'", null);
                            contacto.setTipo1(catTipo.getCatKey());
                        }
                        if (tieneValor(operadoraTelefono1)) {
                            MessageResource catOperadora = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + operadoraTelefono1 + "' and " + CatalogosDBConstants.catRoot + "='CAT_OPER_TEL'", null);
                            contacto.setOperadora1(catOperadora.getCatKey());
                        }
                        if (tieneValor(tipoTelefono2)) {
                            MessageResource catTipo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tipoTelefono2 + "' and " + CatalogosDBConstants.catRoot + "='CAT_TIPO_TEL'", null);
                            contacto.setTipo2(catTipo.getCatKey());
                        }
                        if (tieneValor(operadoraTelefono2)) {
                            MessageResource catOperadora = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + operadoraTelefono2 + "' and " + CatalogosDBConstants.catRoot + "='CAT_OPER_TEL'", null);
                            contacto.setOperadora2(catOperadora.getCatKey());
                        }
                        contacto.setEsPropio(Constants.NOKEYSND);
                        estudiosAdapter.crearContactoParticipante(contacto);

                        procesos.setCodigo(participante.getCodigo());
                        procesos.setEstPart(1);
                        procesos.setRetoma(Constants.NO);
                        procesos.setAdn(Constants.NO);
                        procesos.setPbmc(Constants.NO);
                        /*if (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE))
                            procesos.setPaxgene(Constants.NO);
                        else procesos.setPaxgene(Constants.YES);*/
                        procesos.setPaxgene(Constants.NO);//MA2022, no hay paxgene
                        procesos.setConPto(Constants.NO);
                        procesos.setConsDeng(Constants.NO);
                        procesos.setObsequio(Constants.YES);
                        procesos.setObsequioChf(Constants.NO);
                        procesos.setConsChik(Constants.NO);
                        procesos.setConsFlu(Constants.NO);
                        procesos.setReConsDeng(Constants.NO);
                        procesos.setConvalesciente(Constants.NO);
                        procesos.setMi(Constants.NO);
                        procesos.setZika(Constants.NO);
                        procesos.setPosZika(Constants.NO);
                        procesos.setDatosVisita(Constants.YES);//siempre pedir datos de contacto
                        procesos.setConsCovid19(Constants.NO);
                        procesos.setSubEstudios(Constants.SUB_ESTUDIO_NA);//NA
                        //aca siempre va a marcar si, porque no hay registro de encuestas, ya que no se descargan del server
                        ArrayList<EncuestaCasa> mEncuestasCasas = estudiosAdapter.getListaEncuestaCasas(casaCohorte.getCodigo());
                        if (mEncuestasCasas.size() <= 0) {
                            procesos.setEnCasa(Constants.YES);
                        } else {
                            procesos.setEnCasa(Constants.NO);
                        }
                        //MA2020. Ya no preguntar encuestas seroprevalencia
                        /*if (aceptaDengue && participante.getEdadMeses() >= 24) {
                            procesos.setConsSa(Constants.YES);
                            procesos.setEncPartSa(Constants.YES);
                            procesos.setEnCasaSa(Constants.YES);
                        } else {*/
                            procesos.setEnCasaSa(Constants.NO);
                            procesos.setEncPartSa(Constants.NO);
                            procesos.setConsSa(Constants.NO);//no se usa
                        //}

                        if (participante.getEdadMeses() <= 36)
                            procesos.setEncLacMat(Constants.YES);
                        else
                            procesos.setEncLacMat(Constants.NO);

                        procesos.setEncPart(Constants.YES);
                        procesos.setPesoTalla(Constants.YES);
                        procesos.setDatosParto(Constants.YES);
                        procesos.setcDatosParto(Constants.NO);
                        procesos.setInfoVacuna(Constants.YES);
                        //Perimetro Abdominal
                        procesos.setPerimetroAbdominal(Constants.YES);
                        procesos.setEsatUsuario(Constants.YES);
                        procesos.setEsatUsuarioCc(Constants.YES);

                        procesos.setCasaCHF(null);
                        procesos.setEnCasaChf(Constants.NO);
                        procesos.setCuantasPers(0);
                        procesos.setVolRetoma(null);
                        procesos.setVolRetomaPbmc(null);
                        if (aceptaDengue) {
                            estudios = "Dengue";
                            procesos.setConmx(Constants.NO);
                            procesos.setConmxbhc(Constants.NO);
                        }
                        if (aceptaInfluenza && !esPretermino && !padeceEnfInmuno) {
                            if (estudios.isEmpty()) {
                                estudios = "Influenza";
                            } else
                                estudios += "  " + "Influenza";
                            procesos.setConmx(Constants.NO);
                            procesos.setConmxbhc(Constants.NO);
                        }
                        if (aceptaInfluenzaUO1 && !esPretermino && !padeceEnfInmuno) {
                            if (estudios.isEmpty()) {
                                estudios = "UO1";
                            } else
                                estudios += "  " + "UO1";
                            procesos.setConmx(Constants.NO);
                            procesos.setConmxbhc(Constants.NO);
                        }

                        procesos.setEstudio(estudios);
                        procesos.setCoordenadas("1");
                        procesos.setReConsChf18(Constants.NO);
                        MovilInfo movilInfo = new MovilInfo();
                        movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                        movilInfo.setDeviceid(infoMovil.getDeviceId());
                        movilInfo.setUsername(username);
                        movilInfo.setToday(new Date());
                        procesos.setMovilInfo(movilInfo);
                        //MA2020
                        procesos.setAntecedenteTutorCP(Constants.YES);
                        procesos.setMostrarAlfabeto(Constants.YES);
                        procesos.setMostrarPadreAlfabeto(Constants.YES);
                        procesos.setMostrarMadreAlfabeta(Constants.YES);
                        procesos.setMostrarNumParto(Constants.YES);
                        //Covid19
                        procesos.setSubEstudios("0");
                        if (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA_UO1))//nuevos ingresos UO1 activar cuestionario covid19.Brenda MA2021 04/03/2021
                            procesos.setCuestCovid("4a");//Noviembre2021. MA2022

                        estudiosAdapter.crearParticipanteProcesos(procesos);

                        CartaConsentimiento cc = new CartaConsentimiento();
                        cc.setRecordDate(new Date());
                        cc.setRecordUser(username);
                        cc.setDeviceid(infoMovil.getDeviceId());
                        cc.setEstado('0');
                        cc.setPasive('0');
                        cc.setFechaFirma(new Date());
                        cc.setParticipante(participante);
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

                        cc.setOtraRelacionFamTutor(otraRelacionFamTutor);

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
                        //crear carta de consentimiento para dengue
                        if (aceptaDengue){
                            cc.setAceptaParteA(Constants.YESKEYSND);
                            if (tieneValor(aceptaParteB)) {
                                MessageResource catAceptaParteB = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteB + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                                if (catAceptaParteB!=null) {
                                    cc.setAceptaParteB(catAceptaParteB.getCatKey());
                                }
                            }
                            if (tieneValor(aceptaParteC)) {
                                MessageResource catAceptaParteC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteC + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                                if (catAceptaParteC!=null) {
                                    cc.setAceptaParteC(catAceptaParteC.getCatKey());
                                }
                            }
                            if (tieneValor(aceptaParteD)) {
                                MessageResource catAceptaParteD = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteD + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                                if (catAceptaParteD!=null) cc.setAceptaParteD(catAceptaParteD.getCatKey());
                            }
                            cc.setCodigo(infoMovil.getId());
                            cc.setTamizaje(tamizaje);
                            cc.setReconsentimiento(Constants.NOKEYSND);
                            cc.setVersion(Constants.VERSION_CC_CD);
                            estudiosAdapter.crearCartaConsentimiento(cc);
                        }

                        //crear carta de consentimiento para influenza
                        if (aceptaInfluenza && !esPretermino && !padeceEnfInmuno){
                            cc.setAceptaParteA(Constants.YESKEYSND);
                            if (tieneValor(aceptaParteBInf)) {
                                MessageResource catAceptaParteB = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteBInf + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                                if (catAceptaParteB!=null) {
                                    cc.setAceptaParteB(catAceptaParteB.getCatKey());
                                }
                            }
                            if (tieneValor(aceptaParteCInf)) {
                                MessageResource catAceptaParteC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteCInf + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                                if (catAceptaParteC!=null) {
                                    cc.setAceptaParteC(catAceptaParteC.getCatKey());
                                }
                            }
                            cc.setCodigo(infoMovil.getId());
                            cc.setTamizaje(tamizajeInf);
                            cc.setReconsentimiento(Constants.NOKEYSND);
                            cc.setVersion(Constants.VERSION_CC_CI);
                            cc.setAceptaParteD(null);
                            estudiosAdapter.crearCartaConsentimiento(cc);
                        }

                        //crear carta de consentimiento para influenza
                        if (aceptaInfluenzaUO1 && !esPretermino && !padeceEnfInmuno){
                            cc.setAceptaParteA(Constants.YESKEYSND);
                            if (tieneValor(aceptaParteBInf)) {
                                MessageResource catAceptaParteB = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteBInf + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                                if (catAceptaParteB!=null) {
                                    cc.setAceptaParteB(catAceptaParteB.getCatKey());
                                }
                            }
                            if (tieneValor(aceptaParteCInf)) {
                                MessageResource catAceptaParteC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteCInf + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                                if (catAceptaParteC!=null) {
                                    cc.setAceptaParteC(catAceptaParteC.getCatKey());
                                }
                            }
                            cc.setCodigo(infoMovil.getId());
                            cc.setTamizaje(tamizajeInfUO1);
                            cc.setReconsentimiento(Constants.NOKEYSND);
                            cc.setVersion(Constants.VERSION_CC_UO1);
                            cc.setAceptaParteD(null);
                            estudiosAdapter.crearCartaConsentimiento(cc);
                            //Covid19
                            if (tieneValor(aceptaCohorteUO1ParteDCovid)){
                                //Tamizaje tamizajeInfUO1ParteD = tamizajeInfUO1;
                                tamizajeInfUO1.setCodigo(infoMovil.getId());
                                estudiosAdapter.crearTamizaje(tamizajeInfUO1);
                                cc.setAceptaParteA(null);
                                cc.setAceptaParteB(null);
                                cc.setAceptaParteC(null);
                                cc.setAceptaContactoFuturo(null);
                                MessageResource catAceptaParteD = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaCohorteUO1ParteDCovid + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                                if (catAceptaParteD!=null) {
                                    cc.setAceptaParteD(catAceptaParteD.getCatKey());
                                }
                                if (tieneValor(razonNoAceptaParteDCovid)) {
                                    MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaParteDCovid + "' and " + CatalogosDBConstants.catRoot + "='CPD_CAT_MOTRECHAZO'", null);
                                    if (catRazonNoAceptaParticipar != null)
                                        cc.setMotivoRechazoParteDExt(catRazonNoAceptaParticipar.getCatKey());
                                }
                                cc.setOtroMotivoRechazoParteDExt(otraRazonNoAceptaParteDCovid);
                                cc.setCodigo(infoMovil.getId());
                                cc.setTamizaje(tamizajeInfUO1);
                                cc.setReconsentimiento(Constants.NOKEYSND);
                                cc.setVersion(Constants.VERSION_CC_UO1_COVID);
                                estudiosAdapter.crearCartaConsentimiento(cc);

                                if (aceptaCohorteUO1ParteDCovid.equalsIgnoreCase(Constants.YES)) {
                                    //si no existe participante covid19, crearlo
                                    if (estudiosAdapter.getParticipanteCovid19(Covid19DBConstants.participante + "="+participante.getCodigo() ,null)==null) {
                                        ParticipanteCovid19 participanteCovid19 = new ParticipanteCovid19();
                                        participanteCovid19.setParticipante(participante);
                                        participanteCovid19.setRecordDate(new Date());
                                        participanteCovid19.setRecordUser(username);
                                        participanteCovid19.setDeviceid(infoMovil.getDeviceId());
                                        participanteCovid19.setEstado('0');
                                        participanteCovid19.setPasive('0');
                                        estudiosAdapter.crearParticipanteCovid19(participanteCovid19);
                                    }
                                    procesos.setSubEstudios(Constants.SUB_ESTUDIO_COVID19);//Covid-19
                                    estudiosAdapter.actualizarParticipanteProcesos(procesos);
                                }
                            }
                        }

                        Intent i = new Intent(getApplicationContext(),
                                MenuInfoActivity.class);
                        i.putExtra(ConstantsDB.CODIGO, codigo);
                        i.putExtra(ConstantsDB.VIS_EXITO, false);
                        startActivity(i);
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
                        toast.show();
                        finish();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.participanteExiste), Toast.LENGTH_LONG);
                        toast.show();
                        //finish();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noRegistraIngreso), Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
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

    private void guardarEnfermedadesCronicas(String enfCronica, Bundle datos, Tamizaje tamizaje) throws Exception{
        String oEnfCronica = datos.getString(this.getString(R.string.oEnfCronica));
        String enfCronicaAnio1 = datos.getString(this.getString(R.string.enfCronicaAnio1));
        String enfCronicaMes1 = datos.getString(this.getString(R.string.enfCronicaMes1));
        String enfCronicaAnio2 = datos.getString(this.getString(R.string.enfCronicaAnio2));
        String enfCronicaMes2 = datos.getString(this.getString(R.string.enfCronicaMes2));
        String enfCronicaAnio3 = datos.getString(this.getString(R.string.enfCronicaAnio3));
        String enfCronicaMes3 = datos.getString(this.getString(R.string.enfCronicaMes3));
        String enfCronicaAnio4 = datos.getString(this.getString(R.string.enfCronicaAnio4));
        String enfCronicaMes4 = datos.getString(this.getString(R.string.enfCronicaMes4));
        String enfCronicaAnio5 = datos.getString(this.getString(R.string.enfCronicaAnio5));
        String enfCronicaMes5 = datos.getString(this.getString(R.string.enfCronicaMes5));
        String enfCronicaAnio6 = datos.getString(this.getString(R.string.enfCronicaAnio6));
        String enfCronicaMes6 = datos.getString(this.getString(R.string.enfCronicaMes6));
        String enfCronicaAnio7 = datos.getString(this.getString(R.string.enfCronicaAnio7));
        String enfCronicaMes7 = datos.getString(this.getString(R.string.enfCronicaMes7));
        String enfCronicaAnio8 = datos.getString(this.getString(R.string.enfCronicaAnio8));
        String enfCronicaMes8 = datos.getString(this.getString(R.string.enfCronicaMes8));
        String enfCronicaAnio9 = datos.getString(this.getString(R.string.enfCronicaAnio9));
        String enfCronicaMes9 = datos.getString(this.getString(R.string.enfCronicaMes9));
        String enfCronicaAnio10 = datos.getString(this.getString(R.string.enfCronicaAnio10));
        String enfCronicaMes10 = datos.getString(this.getString(R.string.enfCronicaMes10));
        String enfCronicaAnio11 = datos.getString(this.getString(R.string.enfCronicaAnio11));
        String enfCronicaMes11 = datos.getString(this.getString(R.string.enfCronicaMes11));
        String enfCronicaAnio12 = datos.getString(this.getString(R.string.enfCronicaAnio12));
        String enfCronicaMes12 = datos.getString(this.getString(R.string.enfCronicaMes12));
        String enfCronicaAnio13 = datos.getString(this.getString(R.string.enfCronicaAnio13));
        String enfCronicaMes13 = datos.getString(this.getString(R.string.enfCronicaMes13));
        String enfCronicaAnio14 = datos.getString(this.getString(R.string.enfCronicaAnio14));
        String enfCronicaMes14 = datos.getString(this.getString(R.string.enfCronicaMes14));

        List<MessageResource> catTx = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + enfCronica + "') and "
                + CatalogosDBConstants.catRoot + "='ENFERMEDAD_CRN'", null);

        for (MessageResource ms : catTx) {
            EnfermedadCronica enfermedad = new EnfermedadCronica();
            enfermedad.setId(infoMovil.getId());
            enfermedad.setTamizaje(tamizaje);
            enfermedad.setEnfermedad(ms.getCatKey());
            if (ms.getCatKey().equals("1")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio1);
                if (tieneValor(enfCronicaMes1)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes1 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("2")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio2);
                if (tieneValor(enfCronicaMes2)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes2 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("3")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio3);
                if (tieneValor(enfCronicaMes3)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes3 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("4")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio4);
                if (tieneValor(enfCronicaMes4)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes4 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("5")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio5);
                if (tieneValor(enfCronicaMes5)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes5 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("6")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio6);
                if (tieneValor(enfCronicaMes6)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes6 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("7")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio7);
                if (tieneValor(enfCronicaMes7)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes7 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("8")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio8);
                if (tieneValor(enfCronicaMes8)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes8 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("9")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio9);
                if (tieneValor(enfCronicaMes9)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes9 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
                enfermedad.setOtraEnfermedad(oEnfCronica);
            }
            if (ms.getCatKey().equals("10")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio10);
                if (tieneValor(enfCronicaMes10)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes10 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("11")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio11);
                if (tieneValor(enfCronicaMes11)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes11 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("12")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio12);
                if (tieneValor(enfCronicaMes12)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes12 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("13")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio13);
                if (tieneValor(enfCronicaMes13)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes13 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            if (ms.getCatKey().equals("14")) {
                enfermedad.setAnioEnfermedad(enfCronicaAnio14);
                if (tieneValor(enfCronicaMes14)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes14 + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) enfermedad.setMesEnfermedad(catEnfermedadMes.getCatKey());
                }
            }
            enfermedad.setRecordDate(new Date());
            enfermedad.setRecordUser(username);
            enfermedad.setDeviceid(infoMovil.getDeviceId());
            enfermedad.setEstado('0');
            enfermedad.setPasive('0');

            estudiosAdapter.crearEnfermedadCronica(enfermedad);
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
