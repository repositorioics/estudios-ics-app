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
import ni.org.ics.estudios.appmovil.muestreoanual.forms.ReconDengue2018Form;
import ni.org.ics.estudios.appmovil.muestreoanual.forms.ReconDengue2018FormLabels;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.util.*;


public class NewReconDengue2018Activity extends FragmentActivity implements
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
    private ReconDengue2018FormLabels labels = new ReconDengue2018FormLabels();
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
    private boolean retirado;
    private boolean esElegible = true;
    private boolean visExitosa = false;
    private boolean consentimiento;
    private boolean reconsentimiento;
    private List<MessageResource> catRelacionFamiliar = new ArrayList<MessageResource>();
    private List<MessageResource> catMeses = new ArrayList<MessageResource>();
    private String[] catRazonEmanFem; //razones de emancipación para mujeres
    private String[] catRazonEmanMas; //razones de emancipación para hombres
    private String[] catVerifTutAlf; //cosas a verificar cuando tutor es alfabeto
    private String[] catVerifTutNoAlf; //cosas a verificar cuando tutor no es alfabeto
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
		infoMovil = new DeviceInfo(NewReconDengue2018Activity.this);
        gps = new GPSTracker(NewReconDengue2018Activity.this);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
        mWizardModel = new ReconDengue2018Form(this,mPass);
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

        retirado = (participante.getProcesos().getEstPart().equals(0) || !participante.getProcesos().getEstudio().contains("Dengue"));
        edadMeses = participante.getEdadMeses();
        consentimiento = participante.getProcesos().getConsDeng().equalsIgnoreCase(Constants.YES);
        reconsentimiento = participante.getProcesos().getReConsDeng().equalsIgnoreCase(Constants.YES);
        estudiosAdapter.open();
        catRelacionFamiliar = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", CatalogosDBConstants.order);
        catMeses = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", CatalogosDBConstants.order);
        catRazonEmanFem = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_RAZEMAN'", CatalogosDBConstants.order);
        catRazonEmanMas = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catKey + " in ('2','3','4') and " + CatalogosDBConstants.catRoot + "='CP_CAT_RAZEMAN'", CatalogosDBConstants.order);
        catVerifTutNoAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
        catVerifTutAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catKey + " in ('1','2','3','6') and " + CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
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
                changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), visible);
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
                changeStatus(mWizardModel.findByKey(labels.getDejoCarta()), visible);
                //notificarCambios = false;
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
                changeStatus(mWizardModel.findByKey(labels.getNoDejoCarta()), false);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPersonaDejoCarta()), false);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelFamPersonaDejoCarta()), false);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRelacionPersonaCasa()), false);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDejoCarta())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getPersonaDejoCarta()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelFamPersonaDejoCarta()), visible);
                //notificarCambios = false;
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getNoDejoCarta()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPersonaCasa()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamPersonaCasa()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRelacionFamPersonaCasa())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otra relación familiar");
                changeStatus(mWizardModel.findByKey(labels.getOtraRelacionPersonaCasa()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAceptaParticipar())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                //emancipado solo se pregunta de 14 a mas
                changeStatus(mWizardModel.findByKey(labels.getEmancipado()), visible && edadMeses>=168);
                //notificarCambios = false;
                //Sólo se preguntará a los retirados
                changeStatus(mWizardModel.findByKey(labels.getIncDen()), (visible && retirado));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), (visible && retirado));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaDengue()), !visible);
                //notificarCambios = false;
                if (visible && edadMeses<168){
                    changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAsiste()), visible);
                    //notificarCambios = false;
                    //de 2 a 14 anio
                    changeStatus(mWizardModel.findByKey(labels.getParteADen()), consentimiento);
                    //notificarCambios = false;
                    //de 2 a 14 anio
                    changeStatus(mWizardModel.findByKey(labels.getParteBDen()), consentimiento);
                    //notificarCambios = false;
                    //de 2 a 14 anio
                    changeStatus(mWizardModel.findByKey(labels.getParteCDen()), consentimiento);
                    //notificarCambios = false;
                    //de 14 a 15 anio y si tiene 15 tiene que estar retirado(en ningún estudio o del estudio de dengue)
                    changeStatus(mWizardModel.findByKey(labels.getParteDDen()), reconsentimiento);
                    //notificarCambios = false;
                }
                esElegible = visible;
                if (!visible){
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.noAceptaParticipar),Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(99);
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRazonNoAceptaDengue())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otros motivos");
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaDengue()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getEmancipado())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRazonEmancipacion()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getIncDen()), (!visible && retirado));
                //notificarCambios = false;
                if (visible){
                    SingleFixedChoicePage pagetmp = (SingleFixedChoicePage)mWizardModel.findByKey(labels.getRazonEmancipacion());
                    if (participante.getSexo().equals("M")) {
                        pagetmp.setChoices(catRazonEmanMas);
                    }else{
                        pagetmp.setChoices(catRazonEmanFem);
                    }
                    resetForm(98);
                }
                changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsiste()), !visible);
                //notificarCambios = false;
                //de 2 a 14 anio
                changeStatus(mWizardModel.findByKey(labels.getParteADen()), !visible && consentimiento);
                //notificarCambios = false;
                //de 2 a 14 anio
                changeStatus(mWizardModel.findByKey(labels.getParteBDen()), !visible && consentimiento);
                //notificarCambios = false;
                //de 2 a 14 anio
                changeStatus(mWizardModel.findByKey(labels.getParteCDen()), !visible && consentimiento);
                //notificarCambios = false;
                //de 14 a 15 anio y si tiene 15 tiene que estar retirado(en ningún estudio o del estudio de dengue)
                changeStatus(mWizardModel.findByKey(labels.getParteDDen()), !visible && reconsentimiento);
                //notificarCambios = false;
                esElegible = !visible;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRazonEmancipacion())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro");
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonEmancipacion()), visible);
                //notificarCambios = false;
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Embarazada");
                if (!visible){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.informarEmancipado, Toast.LENGTH_LONG);
                    toast.show();
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getIncDen())) {
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                visible = test.size() > 1;
                //Sólo se preguntará a los retirados
                changeStatus(mWizardModel.findByKey(labels.getVivienda()), (visible && retirado));
                //notificarCambios = false;
                //Sólo se preguntará a los retirados
                changeStatus(mWizardModel.findByKey(labels.getTiempoResidencia()), (visible && retirado));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), visible);
                //notificarCambios = false;
                if (!visible){
                    Toast toast = Toast.makeText(getApplicationContext(),labels.getNoCumpleIncDen(),Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(97);
                }
                esElegible = visible;
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
                        Toast toast = Toast.makeText(getApplicationContext(), labels.getNoCumpleIncDen(), Toast.LENGTH_LONG);
                        toast.show();
                        resetForm(93);
                    }
                    esElegible = visible && tiempoValido;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), (visible && tiempoValido && retirado));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), (visible && tiempoValido));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAsiste()), (visible && tiempoValido));
                    //notificarCambios = false;
                    //es alquilada y tiene tiempo valido
                    if (!visible) {
                        tiempoValido = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Dos Años ó Más");
                        if (!tiempoValido) {
                            Toast toast = Toast.makeText(getApplicationContext(), labels.getNoCumpleIncDen(), Toast.LENGTH_LONG);
                            toast.show();
                            resetForm(93);
                        }
                        esElegible = tiempoValido;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), tiempoValido && retirado);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), tiempoValido);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAsiste()), tiempoValido);
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
                    Toast toast = Toast.makeText(getApplicationContext(),labels.getNoCumpleIncDen(),Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(93);
                }
                esElegible = esPropia && visible;
                changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), (esPropia && visible && retirado));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), (esPropia && visible));
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsiste()), (esPropia && visible));
                //notificarCambios = false;

                if (!esPropia){
                    visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Dos Años ó Más");
                    if (!visible) {
                        Toast toast = Toast.makeText(getApplicationContext(), labels.getNoCumpleIncDen(), Toast.LENGTH_LONG);
                        toast.show();
                        resetForm(93);
                    }
                    esElegible = visible;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), visible && retirado);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAsiste()), visible);
                    //notificarCambios = false;

                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAceptaAtenderCentro())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), labels.getNoCumpleIncDen(), Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(92);
                }
                esElegible = visible;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsiste()), visible);
                //notificarCambios = false;
                //de 2 a 14 anio
                changeStatus(mWizardModel.findByKey(labels.getParteADen()), visible && consentimiento);
                //notificarCambios = false;
                //de 2 a 14 anio
                changeStatus(mWizardModel.findByKey(labels.getParteBDen()), visible && consentimiento);
                //notificarCambios = false;
                //de 2 a 14 anio
                changeStatus(mWizardModel.findByKey(labels.getParteCDen()), visible && consentimiento);
                //notificarCambios = false;
                //de 14 a 15 anio y si tiene 15 tiene que estar retirado(en ningún estudio o del estudio de dengue)
                changeStatus(mWizardModel.findByKey(labels.getParteDDen()), visible && reconsentimiento);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronSN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getEnfCronica()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTomaTx()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCualesTx()), false);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtroTx()), false);
                //notificarCambios = false;

                Calendar fechas = Calendar.getInstance();
                int anioActual = fechas.get(Calendar.YEAR);
                fechas.setTime(participante.getFechaNac());
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

            if (page.getTitle().equals(labels.getEnfCronica())) {
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
                changeStatus(mWizardModel.findByKey(labels.getoEnfCronica()), visible);
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
            if (page.getTitle().equals(labels.getTomaTx())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCualesTx()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCualesTx())) {
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                visible = test != null && test.contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroTx()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAsiste())) {
                if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null) {
                    if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Centro de Salud Sócrates Flores")) {
                        changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), false);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), false);
                        //notificarCambios = false;
                    } else if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro centro de salud")) {
                        changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), true);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), false);
                        //notificarCambios = false;
                    } else if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Puesto de Salud")) {
                        changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), false);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), true);
                        //notificarCambios = false;
                    } else {
                        changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), false);
                        //notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), false);
                        //notificarCambios = false;
                    }
                }else{
                    changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), false);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), false);
                    //notificarCambios = false;
                }
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getParteADen())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRechDen()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsentimiento()), visible && edadMeses >= 72);
                //notificarCambios = false;
                if (visible && edadMeses < 72){
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
                    changeStatus(mWizardModel.findByKey(labels.getJefeFam()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCambiarJefe()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNomContacto()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDireContacto()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTelContacto1SN()), visible);
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

                    String relacion = getRelacionFamiliar(participante.getProcesos().getRelacionFam());
                    pagetmp = (LabelPage) mWizardModel.findByKey(labels.getTutor());
                    pagetmp.setHint(participante.getProcesos().getTutor()+" - ("+ relacion +")");

                    pagetmp = (LabelPage) mWizardModel.findByKey(labels.getJefeFam());
                    String jefeFamilia = participante.getCasa().getNombre1JefeFamilia();
                    if (participante.getCasa().getNombre2JefeFamilia()!=null) jefeFamilia = jefeFamilia + " "+  participante.getCasa().getNombre2JefeFamilia();
                    jefeFamilia = jefeFamilia +" "+ participante.getCasa().getApellido1JefeFamilia();
                    if (participante.getCasa().getApellido2JefeFamilia()!=null) jefeFamilia = jefeFamilia + " "+  participante.getCasa().getApellido2JefeFamilia();
                    pagetmp.setHint(jefeFamilia);

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
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRechDen())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otros motivos");
                changeStatus(mWizardModel.findByKey(labels.getOtroRechDen()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getParteDDen())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRechDenExtEdad()), !visible);
                //notificarCambios = false;
                if (retirado && !visible){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.noAceptaParticipar, Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(92);
                    esElegible = false;
                }else {
                    esElegible = true;
                    changeStatus(mWizardModel.findByKey(labels.getAsentimiento()), edadMeses >= 72);
                    //notificarCambios = false;
                }

                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRechDenExtEdad())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otros motivos");
                changeStatus(mWizardModel.findByKey(labels.getOtroRechDenExtEdad()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAsentimiento())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
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
                changeStatus(mWizardModel.findByKey(labels.getJefeFam()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarJefe()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNomContacto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDireContacto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContacto1SN()), visible);
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

                String relacion = getRelacionFamiliar(participante.getProcesos().getRelacionFam());
                pagetmp = (LabelPage) mWizardModel.findByKey(labels.getTutor());
                pagetmp.setHint(participante.getProcesos().getTutor()+" - ("+ relacion +")");

                pagetmp = (LabelPage) mWizardModel.findByKey(labels.getJefeFam());
                String jefeFamilia = participante.getCasa().getNombre1JefeFamilia();
                if (participante.getCasa().getNombre2JefeFamilia()!=null) jefeFamilia = jefeFamilia + " "+  participante.getCasa().getNombre2JefeFamilia();
                jefeFamilia = jefeFamilia +" "+ participante.getCasa().getApellido1JefeFamilia();
                if (participante.getCasa().getApellido2JefeFamilia()!=null) jefeFamilia = jefeFamilia + " "+  participante.getCasa().getApellido2JefeFamilia();
                pagetmp.setHint(jefeFamilia);

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
                changeStatus(mWizardModel.findByKey(labels.getCambiarJefe()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarPadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarMadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefeFam()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMadre()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNomContacto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDireContacto()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContacto1SN()), visible);
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
            /*if (page.getTitle().equals(labels.getBarrio())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Fuera de Sector");
                changeStatus(mWizardModel.findByKey(labels.getOtrobarrio()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAutsup()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefeFam()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarJefe()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPadre()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarPadre()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMadre()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarMadre()), !visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), !visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAutsup())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefeFam()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarJefe()), visible);
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
                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), labels.getAutsupHint(), Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(89);
                }
                esElegible = visible;
                onPageTreeChanged();
            }*/
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
            if (page.getTitle().equals(labels.getCambiarJefe())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getJefenom()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefenom2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefeap()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefeap2()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelContacto1SN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelContacto1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContactoCel1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContactoOper1()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContacto2SN()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelContacto1())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getTelContactoCel1());
                if (visible){
                    pagetmp.setRangeValidation(true, Constants.MINIMO_NUM_CELULAR, Constants.MAXIMO_NUM_CELULAR);
                }else{
                    pagetmp.setRangeValidation(true, Constants.MINIMO_NUM_CONVENCIONAL, Constants.MAXIMO_NUM_CONVENCIONAL);
                }
                changeStatus(mWizardModel.findByKey(labels.getTelContactoOper1()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelContacto2SN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelContactoClasif2()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContactoCel2()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelContactoClasif2())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getTelContactoCel2());
                if (visible){
                    pagetmp.setRangeValidation(true, Constants.MINIMO_NUM_CELULAR, Constants.MAXIMO_NUM_CELULAR);
                }else{
                    pagetmp.setRangeValidation(true, Constants.MINIMO_NUM_CONVENCIONAL, Constants.MAXIMO_NUM_CONVENCIONAL);
                }
                changeStatus(mWizardModel.findByKey(labels.getTelContactoOper2()), visible);
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
            //datos de cambio de domicilio se pasan a formulario independiente
            /*if (page.getTitle().equals(labels.getGeoref())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getGeoref_razon()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getGeoref_razon())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getGeoref_otraRazon()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }*/


    	}catch (Exception ex){
            ex.printStackTrace();
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

    public String getRelacionFamiliar(Integer codigo) {
        String relacionFamiliar = this.getApplicationContext().getString(R.string.sinRelacFam);
        try {
            for (MessageResource message : catRelacionFamiliar) {
                if (message.getCatKey().equalsIgnoreCase(String.valueOf(codigo))) {
                    relacionFamiliar = message.getSpanish();
                    break;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return relacionFamiliar;
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
    private void resetForm(int preg){
        if (preg>99) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaDengue()), false);
        if (preg>99) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaDengue()), false);

        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEmancipado()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getRazonEmancipacion()), false);

        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getOtraRazonEmancipacion()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getIncDen()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getDejoCarta()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getVivienda()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getTiempoResidencia()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAsiste()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), false);
        //tiempo de residencia
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getParteADen()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getRechDen()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getOtroRechDen()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getParteBDen()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getParteCDen()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getParteDDen()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getRechDenExtEdad()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getOtroRechDenExtEdad()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronica()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getoEnfCronica()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getTomaTx()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getCualesTx()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getOtroTx()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio1()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes1()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio2()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes2()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio3()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes3()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio4()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes4()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio5()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes5()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio6()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes6()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio7()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes7()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio8()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes8()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio9()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes9()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio10()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes10()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio11()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes11()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio12()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes12()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio13()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes13()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio14()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes14()), false);
        //no esta dispuesto a ir al centro
        if (preg>91) changeStatus(mWizardModel.findByKey(labels.getAsentimiento()), false);
        if (preg>91) changeStatus(mWizardModel.findByKey(labels.getTutor()), false);
        if (preg>91) changeStatus(mWizardModel.findByKey(labels.getMismoTutorSN()), false);
        if (preg>91) changeStatus(mWizardModel.findByKey(labels.getTestigoSN()), false);
        //asentimiento verbal
        if (preg>90) changeStatus(mWizardModel.findByKey(labels.getNombrept()), false);
        if (preg>90) changeStatus(mWizardModel.findByKey(labels.getNombrept2()), false);
        if (preg>90) changeStatus(mWizardModel.findByKey(labels.getApellidopt()), false);
        if (preg>90) changeStatus(mWizardModel.findByKey(labels.getApellidopt2()), false);
        if (preg>90) changeStatus(mWizardModel.findByKey(labels.getRelacionFam()), false);
        if (preg>90) changeStatus(mWizardModel.findByKey(labels.getMotivoDifTutor()), false);
        if (preg>90) changeStatus(mWizardModel.findByKey(labels.getOtroMotivoDifTutor()), false);
        if (preg>90) changeStatus(mWizardModel.findByKey(labels.getAlfabetoTutor()), false);
        //no hay testigo
        if (preg>89) changeStatus(mWizardModel.findByKey(labels.getDomicilio()), false);
        if (preg>89) changeStatus(mWizardModel.findByKey(labels.getCmDomicilio()), false);
        if (preg>89) changeStatus(mWizardModel.findByKey(labels.getNombretest1()), false);
        if (preg>89) changeStatus(mWizardModel.findByKey(labels.getNombretest2()), false);
        if (preg>89) changeStatus(mWizardModel.findByKey(labels.getApellidotest1()), false);
        if (preg>89) changeStatus(mWizardModel.findByKey(labels.getApellidotest2()), false);
        //datos de cambio de domicilio se pasan a formulario independiente
        //if (preg>89) changeStatus(mWizardModel.findByKey(labels.getBarrio()), false);
        //if (preg>89) changeStatus(mWizardModel.findByKey(labels.getOtrobarrio()), false);
        //if (preg>89) changeStatus(mWizardModel.findByKey(labels.getDire()), false);
        //if (preg>89) changeStatus(mWizardModel.findByKey(labels.getAutsup()), false);
        //no tiene autorizacion supervisor
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getCambiarJefe()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getCambiarPadre()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getCambiarMadre()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getJefeFam()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getPadre()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getMadre()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif1()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefonoCel1()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefonoOper1()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefono2SN()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif2()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefonoCel2()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefonoOper2()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefono3SN()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif3()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefonoCel3()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelefonoOper3()), false);

        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getJefenom()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getJefenom2()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getJefeap()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getJefeap2()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getNombrepadre()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getNombrepadre2()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getApellidopadre()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getApellidopadre2()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getNombremadre()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getNombremadre2()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getApellidomadre()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getApellidomadre2()), false);

        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getNomContacto()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getDireContacto()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelContacto1SN()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelContacto1()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelContactoCel1()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelContactoOper1()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelContacto2SN()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelContactoClasif2()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelContactoCel2()), false);
        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getTelContactoOper2()), false);

        if (preg>88) changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), false);
        //datos de cambio de domicilio se pasan a formulario independiente
        //if (preg>88) changeStatus(mWizardModel.findByKey(labels.getGeoref()), false);
        //if (preg>88) changeStatus(mWizardModel.findByKey(labels.getGeoref_razon()), false);
        //if (preg>88) changeStatus(mWizardModel.findByKey(labels.getGeoref_otraRazon()), false);

        if (preg>87) changeStatus(mWizardModel.findByKey(labels.getDejoCarta()), false);
        if (preg>87) changeStatus(mWizardModel.findByKey(labels.getPersonaDejoCarta()), false);
        if (preg>87) changeStatus(mWizardModel.findByKey(labels.getRelFamPersonaDejoCarta()), false);
        if (preg>87) changeStatus(mWizardModel.findByKey(labels.getPersonaCasa()), false);
        if (preg>87) changeStatus(mWizardModel.findByKey(labels.getRelacionFamPersonaCasa()), false);
        if (preg>87) changeStatus(mWizardModel.findByKey(labels.getOtraRelacionPersonaCasa()), false);
        if (preg>87) changeStatus(mWizardModel.findByKey(labels.getTelefonoPersonaCasa()), false);

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
            String dejoCarta = datos.getString(this.getString(R.string.dejoCarta));
            String personaDejoCarta = datos.getString(this.getString(R.string.personaDejoCarta));
            String relFamPersonaDejoCarta = datos.getString(this.getString(R.string.relFamPersonaDejoCarta));
            String personaCasa = datos.getString(this.getString(R.string.personaCasa));
            String relacionFamPersonaCasa = datos.getString(this.getString(R.string.relacionFamPersonaCasa));
            String otraRelacionPersonaCasa = datos.getString(this.getString(R.string.otraRelacionPersonaCasa));
            String telefonoPersonaCasa = datos.getString(this.getString(R.string.telefonoPersonaCasa));
            String aceptaParticipar = datos.getString(this.getString(R.string.aceptaCohorteDengueRC2018));
            String razonNoAceptaDengue = datos.getString(this.getString(R.string.razonNoAceptaDengue));
            String otraRazonNoAceptaDengue = datos.getString(this.getString(R.string.otraRazonNoAceptaDengue));
            String emancipado = datos.getString(this.getString(R.string.emancipado2));
            String razonEmancipacion = datos.getString(this.getString(R.string.razonEmancipacion));
            String otraRazonEmancipacion = datos.getString(this.getString(R.string.otraRazonEmancipacion));
            String incDen = datos.getString(this.getString(R.string.incDen));
            String vivienda = datos.getString(this.getString(R.string.vivienda));//agregar en tamizaje
            String tiempoResidencia = datos.getString(this.getString(R.string.tiempoResid));
            String aceptaAtenderCentro = datos.getString(this.getString(R.string.aceptaAtenderCentroRC2018));
            String enfCronSN = datos.getString(this.getString(R.string.enfCronSN));
            String enfCronica = datos.getString(this.getString(R.string.enfCronica));
            String tomaTx = datos.getString(this.getString(R.string.tomaTx));
            String cualesTx = datos.getString(this.getString(R.string.cualesTx));
            String otroTx = datos.getString(this.getString(R.string.otroTx));//agregar en tamizaje
            String asiste = datos.getString(this.getString(R.string.asiste));
            String ocentrosalud = datos.getString(this.getString(R.string.ocentrosalud));
            String puestosalud = datos.getString(this.getString(R.string.puestosalud));
            String asentimiento = datos.getString(this.getString(R.string.asentimiento));
            String parteADen = datos.getString(this.getString(R.string.parteADen));
            String aceptaContactoFuturo = datos.getString(this.getString(R.string.aceptaContactoFuturoRC2018));
            String parteBDen = datos.getString(this.getString(R.string.parteBDen));
            String parteCDen = datos.getString(this.getString(R.string.parteCDen));
            String parteDDen = datos.getString(this.getString(R.string.parteDDen));
            String rechDenExtEdad = datos.getString(this.getString(R.string.rechDenExtEdad));//agregar en carta
            String otroRechDenExtEdad = datos.getString(this.getString(R.string.otroRechDenExtEdad));//agregar en carta
            String rechDen = datos.getString(this.getString(R.string.rechDen));
            String otroRechDen = datos.getString(this.getString(R.string.otroRechDen));
            String nombrept = datos.getString(this.getString(R.string.nombrept));
            String nombrept2 = datos.getString(this.getString(R.string.nombrept2));
            String apellidopt = datos.getString(this.getString(R.string.apellidopt));
            String apellidopt2 = datos.getString(this.getString(R.string.apellidopt2));
            String relacionFam = datos.getString(this.getString(R.string.relacionFam));
            String otraRelacionFam = datos.getString(this.getString(R.string.otraRelacionFam));//agregar en carta
            String mismoTutorSN = datos.getString(this.getString(R.string.mismoTutorSN));//agregar en carta
            String motivoDifTutor = datos.getString(this.getString(R.string.motivoDifTutor));//agregar en carta
            String otroMotivoDifTutor = datos.getString(this.getString(R.string.otroMotivoDifTutor));//agregar en carta
            String alfabetoTutor = datos.getString(this.getString(R.string.alfabetoTutor));
            String testigoSN = datos.getString(this.getString(R.string.testigoSN));
            String nombretest1 = datos.getString(this.getString(R.string.nombretest1));
            String nombretest2 = datos.getString(this.getString(R.string.nombretest2));
            String apellidotest1 = datos.getString(this.getString(R.string.apellidotest1));
            String apellidotest2 = datos.getString(this.getString(R.string.apellidotest2));
            String cmDomicilio = datos.getString(this.getString(R.string.cmDomicilio));
            //datos de cambio de domicilio se pasan a formulario independiente
            //String barrio = datos.getString(this.getString(R.string.barrioRC2018));
            //String otrobarrio = datos.getString(this.getString(R.string.otrobarrio));
            //String dire = datos.getString(this.getString(R.string.dire));
            //String autsup = datos.getString(this.getString(R.string.autsup));//agregar en tamizaje
            String telefonoClasif1 = datos.getString(this.getString(R.string.telefonoClasif1));
            String telefonoCel1 = datos.getString(this.getString(R.string.telefonoCel1));
            String telefonoOper1 = datos.getString(this.getString(R.string.telefonoOper1));
            String telefonoClasif2 = datos.getString(this.getString(R.string.telefonoClasif2));
            String telefonoCel2 = datos.getString(this.getString(R.string.telefonoCel2));
            String telefonoOper2 = datos.getString(this.getString(R.string.telefonoOper2));
            String telefonoClasif3 = datos.getString(this.getString(R.string.telefonoClasif3));
            String telefonoCel3 = datos.getString(this.getString(R.string.telefonoCel3));
            String telefonoOper3 = datos.getString(this.getString(R.string.telefonoOper3));
            String cambiarJefe = datos.getString(this.getString(R.string.cambiarJefe));
            String jefenom = datos.getString(this.getString(R.string.jefenom));
            String jefenom2 = datos.getString(this.getString(R.string.jefenom2));
            String jefeap = datos.getString(this.getString(R.string.jefeap));
            String jefeap2 = datos.getString(this.getString(R.string.jefeap2));
            String nomContacto = datos.getString(this.getString(R.string.nomContacto));
            String barrioContacto = datos.getString(this.getString(R.string.barrioContacto211));
            String otrobarrioContacto = datos.getString(this.getString(R.string.otrobarrioContacto));//agregar en contactoparticipante
            String direContacto = datos.getString(this.getString(R.string.direContacto));
            String telContacto1 = datos.getString(this.getString(R.string.telContacto1));
            String telContactoCel1 = datos.getString(this.getString(R.string.telContactoCel1));
            String telContactoOper1 = datos.getString(this.getString(R.string.telContactoOper1));
            String telContactoClasif2 = datos.getString(this.getString(R.string.telContactoClasif2));
            String telContactoCel2 = datos.getString(this.getString(R.string.telContactoCel2));
            String telContactoOper2 = datos.getString(this.getString(R.string.telContactoOper2));
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
            //datos de cambio de domicilio se pasan a formulario independiente
            //String georef = datos.getString(this.getString(R.string.georef));
            //String manzana = datos.getString(this.getString(R.string.Manzana));
            //String georef_razon = datos.getString(this.getString(R.string.georef_razon));
            //String georef_otraRazon = datos.getString(this.getString(R.string.georef_otraRazon));

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
            if (tieneValor(dejoCarta)) {
                MessageResource catDejoC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dejoCarta + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                visita.setDejoCarta(catDejoC.getCatKey());
            }
            if (tieneValor(relacionFamPersonaCasa)) {
                MessageResource relFamiliar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relacionFamPersonaCasa + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", null);
                visita.setRelacionFamPersonaCasa(relFamiliar.getCatKey());
            }
            if (tieneValor(relFamPersonaDejoCarta)) {
                MessageResource relFamiliar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relFamPersonaDejoCarta + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", null);
                visita.setRelFamPersonaDejoCarta(relFamiliar.getCatKey());
            }
            visita.setPersonaDejoCarta(personaDejoCarta);
            visita.setPersonaCasa(personaCasa);
            visita.setOtraRelacionPersonaCasa(otraRelacionPersonaCasa);
            visita.setTelefonoPersonaCasa(telefonoPersonaCasa);
            visita.setCodigoVisita(infoMovil.getId());
            visita.setEstudio("Dengue");
            estudiosAdapter.crearVisitaTerrenoParticipante(visita);

            //si visita es exitosa registrar tamizaje, carta de consentimiento, y actualizar datos del participante participante
            if (visita.getVisitaExitosa().equals(Constants.YESKEYSND)) {
                //Crea un Nuevo Registro de tamizaje
                Tamizaje tamizaje = new Tamizaje();
                tamizaje.setCodigo(infoMovil.getId());
                tamizaje.setCohorte("CP");
                tamizaje.setSexo(participante.getSexo());
                tamizaje.setFechaNacimiento(participante.getFechaNac());
                tamizaje.setAceptaTamizajePersona(Constants.YESKEYSND);
                tamizaje.setCodigoParticipanteRecon(participante.getCodigo());
                //Si acepta o no participar, siempre registrar tamizaje
                MessageResource catAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParticipar + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAceptaParticipar != null) tamizaje.setAceptaParticipar(catAceptaParticipar.getCatKey());
                if (tieneValor(razonNoAceptaDengue)) {
                    MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaDengue + "' and " + CatalogosDBConstants.catRoot + "='CPD_CAT_MOTRECHAZO'", null);
                    if (catRazonNoAceptaParticipar != null)
                        tamizaje.setRazonNoAceptaParticipar(catRazonNoAceptaParticipar.getCatKey());
                }
                tamizaje.setOtraRazonNoAceptaParticipar(otraRazonNoAceptaDengue);
                tamizaje.setEsElegible(esElegible?Constants.YESKEYSND:Constants.NOKEYSND);
                if (tieneValor(emancipado)) {
                    MessageResource catEman = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + emancipado + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catEman != null)
                        tamizaje.setEmancipado(catEman.getCatKey());
                }
                if (tieneValor(razonEmancipacion)) {
                    MessageResource catEman = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonEmancipacion + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_RAZEMAN'", null);
                    if (catEman != null)
                        tamizaje.setRazonEmancipacion(catEman.getCatKey());
                    tamizaje.setOtraRazonEmancipacion(otraRazonEmancipacion);
                }
                if (tieneValor(incDen)) {
                    String keysCriterios = "";
                    incDen = incDen.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','");
                    List<MessageResource> mcriteriosInclusion = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + incDen + "') and "
                            + CatalogosDBConstants.catRoot + "='CP_CAT_CI'", null);
                    for (MessageResource ms : mcriteriosInclusion) {
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
                    if (cattiempoResidencia != null) tamizaje.setTiempoResidencia(cattiempoResidencia.getCatKey());
                }
                if (tieneValor(enfCronSN)) {
                    MessageResource catEnfermedad = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronSN + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catEnfermedad != null) tamizaje.setEnfermedadCronica(catEnfermedad.getCatKey());

                    if (enfCronSN.equals(Constants.YES))
                        guardarEnfermedadesCronicas(enfCronica.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','"), datos, tamizaje);
                }

                if (tieneValor(tomaTx)) {
                    MessageResource catTratami = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tomaTx + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catTratami != null) tamizaje.setTratamiento(catTratami.getCatKey());
                }
                if (tieneValor(cualesTx)) {
                    String keysCriterios = "";
                    cualesTx = cualesTx.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','");
                    List<MessageResource> catTx = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + cualesTx + "') and "
                            + CatalogosDBConstants.catRoot + "='CPD_CAT_TRATAMIENTO'", null);
                    for (MessageResource ms : catTx) {
                        keysCriterios += ms.getCatKey() + ",";
                    }
                    if (!keysCriterios.isEmpty())
                        keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                    tamizaje.setCualTratamiento(keysCriterios);
                }
                tamizaje.setOtroTx(otroTx);

                if (tieneValor(asiste)) {
                    MessageResource catDondeAsisteProblemasSalud = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + asiste + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_DONDEASISTE'", null);
                    if (catDondeAsisteProblemasSalud != null)
                        tamizaje.setDondeAsisteProblemasSalud(catDondeAsisteProblemasSalud.getCatKey());
                }
                if (tieneValor(ocentrosalud)) tamizaje.setOtroCentroSalud(ocentrosalud);
                if (tieneValor(puestosalud)) {
                    MessageResource catPuestoSalud = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + puestosalud + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_PUESTO'", null);
                    if (catPuestoSalud != null) tamizaje.setPuestoSalud(catPuestoSalud.getCatKey());
                }
                if (tieneValor(aceptaAtenderCentro)) {
                    MessageResource catAceptaAtenderCentro = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaAtenderCentro + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catAceptaAtenderCentro != null)
                        tamizaje.setAceptaAtenderCentro(catAceptaAtenderCentro.getCatKey());
                }
                if (tieneValor(asentimiento)) {
                    MessageResource catAsentimientoVerbal = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + asentimiento + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catAsentimientoVerbal != null)
                        tamizaje.setAsentimientoVerbal(catAsentimientoVerbal.getCatKey());
                }
                /*//datos de cambio de domicilio se pasan a formulario independiente
                if (tieneValor(autsup)) {
                    MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + autsup + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catSino != null)
                        tamizaje.setAutorizaSupervisor(catSino.getCatKey());
                }*/
                tamizaje.setRecordDate(new Date());
                tamizaje.setRecordUser(username);
                tamizaje.setDeviceid(infoMovil.getDeviceId());
                tamizaje.setEstado('0');
                tamizaje.setPasive('0');

                //Registrar tamizaje dengue
                Estudio estudioCDengue = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=" + Constants.COD_EST_COHORTEDENGUE, null);
                tamizaje.setEstudio(estudioCDengue);
                estudiosAdapter.crearTamizaje(tamizaje);

                //Pregunta si acepta realizar el tamizaje
                if (tamizaje.getAceptaParticipar().equals(Constants.YESKEYSND)) {
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
                    }
                    if (tieneValor(motivoDifTutor)) {
                        MessageResource catDifTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + motivoDifTutor + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_DIFTUTOR'", null);
                        if (catDifTutor != null) cc.setMotivoDifTutor(catDifTutor.getCatKey());
                    }
                    cc.setOtroMotivoDifTutor(otroMotivoDifTutor);
                    cc.setOtraRelacionFamTutor(otraRelacionFam);

                    if (tieneValor(parteADen)) {
                        MessageResource catAceptaParteA = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + parteADen + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catAceptaParteA != null) {
                            cc.setAceptaParteA(catAceptaParteA.getCatKey());
                        }
                    }
                    if (tieneValor(rechDen)) {
                        MessageResource catrechazoParteA = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + rechDen + "' and " + CatalogosDBConstants.catRoot + "='CPD_CAT_MOTRECHAZO'", null);
                        if (catrechazoParteA != null) cc.setMotivoRechazoParteA(catrechazoParteA.getCatKey());
                        cc.setOtroMotivoRechazoParteA(otroRechDen);
                    }
                    if (tieneValor(parteBDen)) {
                        MessageResource catAceptaParteB = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + parteBDen + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catAceptaParteB != null) {
                            cc.setAceptaParteB(catAceptaParteB.getCatKey());
                        }
                    }
                    if (tieneValor(parteCDen)) {
                        MessageResource catAceptaParteC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + parteCDen + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catAceptaParteC != null) {
                            cc.setAceptaParteC(catAceptaParteC.getCatKey());
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
                    cc.setReconsentimiento(Constants.YESKEYSND);
                    cc.setTamizaje(tamizaje);
                    /*if (tieneValor(parteADen)) { //Se indica no se debe registrar ésta carta. MA2020 Brenda
                        cc.setCodigo(infoMovil.getId());
                        cc.setVersion(Constants.VERSION_CC_CD);
                        //crear carta de consentimiento para dengue
                        estudiosAdapter.crearCartaConsentimiento(cc);
                    }*/
                    //si se preguntó por la parte D entonces crear nuevo tamizaje y nueva carta para la parte D
                    if (tieneValor(parteDDen)) {
                        //solo crear nuevo tamizaje si se pregunto por la parte A
                        if (tieneValor(parteADen)) {
                            //se crea tamizaje para parteD
                            Tamizaje tamizajeParteD = new Tamizaje();
                            tamizajeParteD = tamizaje;
                            tamizajeParteD.setCodigo(infoMovil.getId());
                            estudiosAdapter.crearTamizaje(tamizajeParteD);
                            cc.setTamizaje(tamizajeParteD);
                            if (tieneValor(enfCronSN) && enfCronSN.equals(Constants.YES))
                                guardarEnfermedadesCronicas(enfCronica.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','"), datos, tamizajeParteD);
                        }
                        cc.setCodigo(infoMovil.getId());
                        cc.setAceptaParteA(null);
                        cc.setMotivoRechazoParteA(null);
                        cc.setOtroMotivoRechazoParteA(null);
                        cc.setAceptaContactoFuturo(null);
                        cc.setAceptaParteB(null);
                        cc.setAceptaParteC(null);
                        cc.setVersion(Constants.VERSION_CC_CD_D);
                        MessageResource catAceptaParteD = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + parteDDen + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catAceptaParteD != null) cc.setAceptaParteD(catAceptaParteD.getCatKey());

                        if (tieneValor(rechDenExtEdad)) {
                            MessageResource catRechD = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + rechDenExtEdad + "' and " + CatalogosDBConstants.catRoot + "='CPD_CAT_MOTRECHAZO'", null);
                            if (catRechD != null) cc.setMotivoRechazoParteDExt(catRechD.getCatKey());
                            cc.setOtroMotivoRechazoParteDExt(otroRechDenExtEdad);
                        }
                        //se crea tamizaje de parte D(es otra carta)
                        estudiosAdapter.crearCartaConsentimiento(cc);
                    }
                    MovilInfo movilInfo = new MovilInfo();
                    movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                    movilInfo.setDeviceid(infoMovil.getDeviceId());
                    movilInfo.setUsername(username);
                    movilInfo.setToday(new Date());
                    ParticipanteProcesos procesos = participante.getProcesos();
                    if (procesos.getReConsDeng() != null) {
                        procesos.setReConsDeng("No");
                    }
                    procesos.setConsDeng("No");
                    procesos.setMovilInfo(movilInfo);
                    estudiosAdapter.actualizarParticipanteProcesos(procesos);
                    if (esElegible) {
                        int ceroDefaul = 0;
                        Casa casa = participante.getCasa();
                        if (cambiarJefe.equals(Constants.YES)) {
                            if (tieneValor(jefenom)) casa.setNombre1JefeFamilia(jefenom);
                            if (tieneValor(jefenom2)) casa.setNombre2JefeFamilia(jefenom2);
                            if (tieneValor(jefeap)) casa.setApellido1JefeFamilia(jefeap);
                            if (tieneValor(jefeap2)) casa.setApellido2JefeFamilia(jefeap2);
                            casa.setRecordDate(new Date());
                            casa.setRecordUser(username);
                            casa.setDeviceid(infoMovil.getDeviceId());
                            casa.setEstado('0');
                        }
                        //cambio de domicilio
                        if (tieneValor(cmDomicilio) && cmDomicilio.equals(Constants.YES)) {
                            procesos.setCoordenadas("2");
                        }else{
                            procesos.setCoordenadas("0");
                        }
                        estudiosAdapter.editarCasa(casa);
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
                        participante.setRecordDate(new Date());
                        participante.setRecordUser(username);
                        participante.setDeviceid(infoMovil.getDeviceId());
                        participante.setEstado('0');
                        participante.setPasive('0');
                        //Guarda nuevo participante
                        estudiosAdapter.editarParticipante(participante);

                        if (tieneValor(telefonoCel1) || tieneValor(telefonoCel2) || tieneValor(telefonoCel3)) {
                            guardarContactoParticipante(Constants.YESKEYSND, null, null, participante.getCasa().getBarrio().getCodigo().toString(), null , telefonoCel1, telefonoClasif1, telefonoOper1,
                                    telefonoCel2, telefonoClasif2, telefonoOper2, telefonoCel3, telefonoClasif3, telefonoOper3);
                        }
                        guardarContactoParticipante(Constants.NOKEYSND, nomContacto, direContacto, barrioContacto, otrobarrioContacto,
                                telContactoCel1, telContacto1, telContactoOper1, telContactoCel2, telContactoClasif2, telContactoOper2, null, null, null);

                        if (tieneValor(mismoTutorSN) && mismoTutorSN.equals(Constants.NO)) {
                            String nombreTutor = nombrept;
                            if (tieneValor(nombrept2)) nombreTutor = nombreTutor + " " + nombrept2;
                            nombreTutor = nombreTutor + " " + apellidopt;
                            if (tieneValor(apellidopt2)) nombreTutor = nombreTutor + " " + apellidopt2;
                            procesos.setTutor(nombreTutor);
                            if (tieneValor(relacionFam)) {
                                MessageResource catRelacionFamiliarTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relacionFam + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", null);
                                if (catRelacionFamiliarTutor != null)
                                    procesos.setRelacionFam(Integer.valueOf(catRelacionFamiliarTutor.getCatKey()));
                            } else {
                                procesos.setRelacionFam(ceroDefaul);
                            }
                        }
                        if (procesos.getEstPart().equals(0)) {
                            procesos.setEstPart(1);
                        }
                        if (procesos.getEstudio() == null || procesos.getEstudio().isEmpty()) {
                            procesos.setEstudio("Dengue");
                        }else if (procesos.getEstudio().equals("Influenza")){
                            procesos.setEstudio("Dengue  Influenza");
                        }else if (procesos.getEstudio().equals("CH Familia")){
                            procesos.setEstudio("Dengue    CH Familia");
                        }else if (procesos.getEstudio().equals("Influenza  CH Familia")){
                            procesos.setEstudio("Dengue  Influenza  CH Familiaa");
                        }

                        estudiosAdapter.actualizarParticipanteProcesos(procesos);

                        Intent i = new Intent(getApplicationContext(),
                                MenuInfoActivity.class);
                        i.putExtra(ConstantsDB.CODIGO, participante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
                        toast.show();
                        finish();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noRegistraIngreso), Toast.LENGTH_LONG);
                        toast.show();
                        finish();
                    }

                } else {
                    //Si no acepta participar entonces desactivar el proceso de recon dengue
                    MovilInfo movilInfo = new MovilInfo();
                    movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                    movilInfo.setDeviceid(infoMovil.getDeviceId());
                    movilInfo.setUsername(username);
                    movilInfo.setToday(new Date());
                    ParticipanteProcesos procesos = participante.getProcesos();
                    if (procesos.getReConsDeng() != null) {
                        procesos.setReConsDeng("No");
                    }
                    procesos.setConsDeng("No");
                    procesos.setMovilInfo(movilInfo);
                    estudiosAdapter.actualizarParticipanteProcesos(procesos);
                    Intent i = new Intent(getApplicationContext(),
                            MenuInfoActivity.class);
                    i.putExtra(ConstantsDB.CODIGO, participante.getCodigo());
                    i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noRegistraIngreso), Toast.LENGTH_LONG);
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
