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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private List<MessageResource> catRelacionFamiliar = new ArrayList<MessageResource>();
    private List<MessageResource> catMeses = new ArrayList<MessageResource>();
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

        retirado = participante.getProcesos().getEstPart().equals(0);
        edadMeses = participante.getEdadMeses();
        estudiosAdapter.open();
        catRelacionFamiliar = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", null);
        catMeses = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
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
        /*for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.getTitle().equals(labels.getEnfCronicaAnio())) {
                NumberPage np = (NumberPage) page;
                Calendar fechas = Calendar.getInstance();
                int anioActual = fechas.get(Calendar.YEAR);
                fechas.setTime(participante.getFechaNac());
                int anioNac = fechas.get(Calendar.YEAR);
                np.setRangeValidation(true, anioNac, anioActual);
            }
        }*/
    }
    
    public void updateModel(Page page){
       	try{
			boolean visible = false;
            if (page.getTitle().equals(labels.getVisExit())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRazonVisNoExit()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEmancipado()), visible && participante.getEdadMeses()>=156);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), visible);
                notificarCambios = false;
                if (!visible) {
                    resetForm(99);
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
                notificarCambios = false;
                boolean opcion7 = visible;
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null &&
                        (page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains("Niño ausente")
                                || page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Padres Ausentes o Adultos Ausentes")
                                || page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Acude a Consulta Medica sin Tutor")));
                changeStatus(mWizardModel.findByKey(labels.getPersonaCasa()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamPersonaCasa()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoPersonaCasa()), (opcion7 || visible));
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDejoCarta())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getPersonaDejoCarta()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelFamPersonaDejoCarta()), visible);
                notificarCambios = false;
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getNoDejoCarta()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPersonaCasa()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamPersonaCasa()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRelacionFamPersonaCasa())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otra relación familiar");
                changeStatus(mWizardModel.findByKey(labels.getOtraRelacionPersonaCasa()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAceptaParticipar())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getEmancipado()), visible && participante.getEdadMeses()>=156);
                notificarCambios = false;
                //Sólo se preguntará a los retirados
                changeStatus(mWizardModel.findByKey(labels.getIncDen()), (visible && retirado));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaDengue()), !visible);
                notificarCambios = false;
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
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getIncDen())) {
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                visible = test.size() > 1;
                //Sólo se preguntará a los retirados
                changeStatus(mWizardModel.findByKey(labels.getVivienda()), (visible && retirado));
                notificarCambios = false;
                //Sólo se preguntará a los retirados
                changeStatus(mWizardModel.findByKey(labels.getTiempoResidencia()), (visible && retirado));
                notificarCambios = false;
                if (!visible){
                    Toast toast = Toast.makeText(getApplicationContext(),labels.getNoCumpleIncDen(),Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(98);
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
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), (visible && tiempoValido));
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), (visible && tiempoValido));
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAsiste()), (visible && tiempoValido));
                    notificarCambios = false;
                    //es alquilada y tiene tiempo valido
                    if (!visible) {
                        tiempoValido = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Dos Años ó Más");
                        if (!tiempoValido) {
                            Toast toast = Toast.makeText(getApplicationContext(), labels.getNoCumpleIncDen(), Toast.LENGTH_LONG);
                            toast.show();
                            resetForm(93);
                        }
                        esElegible = tiempoValido;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), tiempoValido);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), tiempoValido);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAsiste()), tiempoValido);
                        notificarCambios = false;
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
                changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), (esPropia && visible));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), (esPropia && visible));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsiste()), (esPropia && visible));
                notificarCambios = false;

                if (!esPropia){
                    visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Dos Años ó Más");
                    if (!visible) {
                        Toast toast = Toast.makeText(getApplicationContext(), labels.getNoCumpleIncDen(), Toast.LENGTH_LONG);
                        toast.show();
                        resetForm(93);
                    }
                    esElegible = visible;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), visible);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), visible);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAsiste()), visible);
                    notificarCambios = false;

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
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsiste()), visible);
                notificarCambios = false;
                //de 2 a 14 anio
                changeStatus(mWizardModel.findByKey(labels.getParteADen()), visible && (edadMeses >=24 && edadMeses < 180));
                notificarCambios = false;
                //de 2 a 14 anio
                changeStatus(mWizardModel.findByKey(labels.getParteBDen()), visible && (edadMeses >=24 && edadMeses < 180));
                notificarCambios = false;
                //de 2 a 14 anio
                changeStatus(mWizardModel.findByKey(labels.getParteCDen()), visible && (edadMeses >=24 && edadMeses < 180));
                notificarCambios = false;
                //de 14 a 15 anio y si tiene 15 tiene que estar retirado
                changeStatus(mWizardModel.findByKey(labels.getParteDDen()), visible && ((edadMeses >= 168 && edadMeses < 180) || (edadMeses >= 180 && retirado)));
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronSN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getEnfCronica()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTomaTx()), visible);
                notificarCambios = false;
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio());
                Calendar fechas = Calendar.getInstance();
                int anioActual = fechas.get(Calendar.YEAR);
                fechas.setTime(participante.getFechaNac());
                int anioNac = fechas.get(Calendar.YEAR);
                pagetmp.setRangeValidation(true, anioNac, anioActual);

                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronicaMes())) {
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getEnfCronicaAnio());
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
                            page.resetData(new Bundle());
                        }
                    }
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfCronica())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otra");
                changeStatus(mWizardModel.findByKey(labels.getoEnfCronica()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTomaTx())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCualesTx()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCualesTx())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroTx()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAsiste())) {
                if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null) {
                    if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Centro de Salud Sócrates Flores")) {
                        changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), false);
                        notificarCambios = false;
                    } else if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro centro de salud")) {
                        changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), true);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), false);
                        notificarCambios = false;
                    } else if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Puesto de Salud")) {
                        changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), true);
                        notificarCambios = false;
                    } else {
                        changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), false);
                        notificarCambios = false;
                    }
                }else{
                    changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), false);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), false);
                    notificarCambios = false;
                }
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getParteADen())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRechDen()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsentimiento()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRechDen())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otros motivos");
                changeStatus(mWizardModel.findByKey(labels.getOtroRechDen()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getParteDDen())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRechDenExtEdad()), !visible);
                notificarCambios = false;
                if (retirado && !visible){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.noAceptaParticipar, Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(92);
                    esElegible = false;
                }else {
                    esElegible = true;
                    changeStatus(mWizardModel.findByKey(labels.getAsentimiento()), true);
                    notificarCambios = false;
                }

                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRechDenExtEdad())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otros motivos");
                changeStatus(mWizardModel.findByKey(labels.getOtroRechDenExtEdad()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAsentimiento())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTutor()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMismoTutorSN()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAlfabetoTutor()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDomicilio()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCmDomicilio()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefeFam()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarJefe()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNomContacto()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDireContacto()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContacto1SN()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarPadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarMadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                notificarCambios = false;

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
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombrept2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidopt()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidopt2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFam()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMotivoDifTutor()), visible);
                notificarCambios = false;
                /*changeStatus(mWizardModel.findByKey(labels.getAlfabetoTutor()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTestigoSN()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombretest1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombretest2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidotest1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidotest2()), visible);
                notificarCambios = false;
                */
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMotivoDifTutor())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro Motivo");
                changeStatus(mWizardModel.findByKey(labels.getOtroMotivoDifTutor()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlfabetoTutor())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getTestigoSN()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombretest1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombretest2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidotest1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidotest2()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTestigoSN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNombretest1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombretest2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidotest1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidotest2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDomicilio()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCmDomicilio()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarJefe()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarPadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarMadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefeFam()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNomContacto()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDireContacto()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContacto1SN()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                notificarCambios = false;
                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), labels.getNoCumpleIncDen(), Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(90);
                }
                esElegible = visible;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCmDomicilio())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getBarrio()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtrobarrio()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAutsup()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDire()), visible);
                notificarCambios = false;
                //TODO: desarrollar funcionalidad para obtener punto gps
                //changeStatus(mWizardModel.findByKey(labels.getGeoref()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getBarrio())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Fuera de Sector");
                changeStatus(mWizardModel.findByKey(labels.getOtrobarrio()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAutsup()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefeFam()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarJefe()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPadre()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarPadre()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMadre()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarMadre()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAutsup())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelefono1SN()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefeFam()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarJefe()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarPadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getMadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCambiarMadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                notificarCambios = false;
                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), labels.getAutsupHint(), Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(89);
                }
                esElegible = visible;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefono1SN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoCel1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoOper1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefono2SN()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefonoClasif1())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getTelefonoCel1());
                if (visible){
                    pagetmp.setRangeValidation(true, 50000000, 89999999);
                }else{
                    pagetmp.setRangeValidation(true, 20000000, 29999999);
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefono2SN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoCel2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoOper2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefono3SN()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefonoClasif2())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getTelefonoCel2());
                if (visible){
                    pagetmp.setRangeValidation(true, 50000000, 89999999);
                }else{
                    pagetmp.setRangeValidation(true, 20000000, 29999999);
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefono3SN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelefonoClasif3()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoCel3()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelefonoOper3()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefonoClasif3())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getTelefonoCel3());
                if (visible){
                    pagetmp.setRangeValidation(true, 50000000, 89999999);
                }else{
                    pagetmp.setRangeValidation(true, 20000000, 29999999);
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCambiarJefe())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getJefenom()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefenom2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefeap()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getJefeap2()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelContacto1SN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelContacto1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContactoCel1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContactoOper1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContacto2SN()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelContacto1())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getTelContactoCel1());
                if (visible){
                    pagetmp.setRangeValidation(true, 50000000, 89999999);
                }else{
                    pagetmp.setRangeValidation(true, 20000000, 29999999);
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelContacto2SN())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTelContactoClasif2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContactoCel2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTelContactoOper2()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelContactoClasif2())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Celular");
                NumberPage pagetmp = (NumberPage) mWizardModel.findByKey(labels.getTelContactoCel2());
                if (visible){
                    pagetmp.setRangeValidation(true, 50000000, 89999999);
                }else{
                    pagetmp.setRangeValidation(true, 20000000, 29999999);
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCambiarPadre())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNombrepadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombrepadre2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidopadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidopadre2()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCambiarMadre())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNombremadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombremadre2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidomadre()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellidomadre2()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            //TODO: desarrollar funcionalidad para obtener punto gps
            /*if (page.getTitle().equals(labels.getGeoref())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getGeoref_razon()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getGeoref_razon())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getGeoref_otraRazon()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }*/


    	}catch (Exception ex){
            ex.printStackTrace();
        }
    	
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
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getIncDen()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaDengue()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getDejoCarta()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getVivienda()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getTiempoResidencia()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getEnfCronSN()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getAsiste()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getOcentrosalud()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getPuestosalud()), false);
        //tiempo de residencia
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getParteADen()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getRechDen()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getOtroRechDen()), false);
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
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaAnio()), false);
        if (preg>92) changeStatus(mWizardModel.findByKey(labels.getEnfCronicaMes()), false);
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
        if (preg>89) changeStatus(mWizardModel.findByKey(labels.getBarrio()), false);
        if (preg>89) changeStatus(mWizardModel.findByKey(labels.getOtrobarrio()), false);
        if (preg>89) changeStatus(mWizardModel.findByKey(labels.getDire()), false);
        if (preg>89) changeStatus(mWizardModel.findByKey(labels.getAutsup()), false);
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
        //TODO: desarrollar funcionalidad para obtener punto gps
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
            String incDen = datos.getString(this.getString(R.string.incDen));
            String vivienda = datos.getString(this.getString(R.string.vivienda));//agregar en tamizaje
            String tiempoResidencia = datos.getString(this.getString(R.string.tiempoResid));
            String aceptaAtenderCentro = datos.getString(this.getString(R.string.aceptaAtenderCentroRC2018));
            String enfCronSN = datos.getString(this.getString(R.string.enfCronSN));
            String enfCronica = datos.getString(this.getString(R.string.enfCronica));
            String oEnfCronica = datos.getString(this.getString(R.string.oEnfCronica));//agregar en tamizaje
            String enfCronicaAnio = datos.getString(this.getString(R.string.enfCronicaAnio));//agregar en tamizaje
            String enfCronicaMes = datos.getString(this.getString(R.string.enfCronicaMes));//agregar en tamizaje
            String tomaTx = datos.getString(this.getString(R.string.tomaTx));
            String cualesTx = datos.getString(this.getString(R.string.cualesTx));
            String otroTx = datos.getString(this.getString(R.string.otroTx));//agregar en tamizaje
            String asiste = datos.getString(this.getString(R.string.asiste));
            String ocentrosalud = datos.getString(this.getString(R.string.ocentrosalud));
            String puestosalud = datos.getString(this.getString(R.string.puestosalud));
            String asentimiento = datos.getString(this.getString(R.string.asentimiento));
            String parteADen = datos.getString(this.getString(R.string.parteADen));
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
            String barrio = datos.getString(this.getString(R.string.barrioRC2018));
            String otrobarrio = datos.getString(this.getString(R.string.otrobarrio));
            String dire = datos.getString(this.getString(R.string.dire));
            String autsup = datos.getString(this.getString(R.string.autsup));//agregar en tamizaje
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
            String georef = datos.getString(this.getString(R.string.georef));
            String manzana = datos.getString(this.getString(R.string.Manzana));
            String georef_razon = datos.getString(this.getString(R.string.georef_razon));
            String georef_otraRazon = datos.getString(this.getString(R.string.georef_otraRazon));

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
            estudiosAdapter.crearVisitaTerrenoParticipante(visita);

            //si visita es exitosa registrar tamizaje, carta de consentimiento, y actualizar datos del participante participante
            if (visita.getVisitaExitosa().equals(Constants.YESKEYSND)) {
                //Crea un Nuevo Registro de tamizaje
                Tamizaje tamizaje = new Tamizaje();
                tamizaje.setCohorte("CP");
                tamizaje.setSexo(participante.getSexo());
                tamizaje.setFechaNacimiento(participante.getFechaNac());
                tamizaje.setAceptaTamizajePersona(Constants.YESKEYSND);
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
                    if (catEnfermedad != null) tamizaje.setEnfermedad(catEnfermedad.getCatKey());
                }
                if (tieneValor(enfCronicaMes)) {
                    MessageResource catEnfermedadMes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronicaMes + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                    if (catEnfermedadMes != null) tamizaje.setEnfCronicaMes(catEnfermedadMes.getCatKey());
                }
                if (tieneValor(enfCronica)) {
                    MessageResource catEnfermedad = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfCronica + "' and " + CatalogosDBConstants.catRoot + "='ENFERMEDAD_CRN'", null);
                    if (catEnfermedad != null) tamizaje.setCualEnfermedad(catEnfermedad.getCatKey());
                }
                tamizaje.setOtraEnfCronica(oEnfCronica);
                tamizaje.setEnfCronicaAnio(enfCronicaAnio);

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
                if (tieneValor(autsup)) {
                    MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + autsup + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (catSino != null)
                        tamizaje.setAutorizaSupervisor(catSino.getCatKey());
                }
                tamizaje.setRecordDate(new Date());
                tamizaje.setRecordUser(username);
                tamizaje.setDeviceid(infoMovil.getDeviceId());
                tamizaje.setEstado('0');
                tamizaje.setPasive('0');

                //Registrar tamizaje dengue
                Estudio estudioCDengue = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=" + Constants.COD_EST_COHORTEDENGUE, null);
                tamizaje.setCodigo(infoMovil.getId());
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
                    if (tieneValor(emancipado)) {
                        MessageResource catEman = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + emancipado + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catEman != null)
                            cc.setEmancipado(catEman.getCatKey());
                    }
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
                    if (tieneValor(parteDDen)) {
                        MessageResource catAceptaParteD = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + parteDDen + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catAceptaParteD != null) cc.setAceptaParteD(catAceptaParteD.getCatKey());
                    }
                    if (tieneValor(rechDenExtEdad)) {
                        MessageResource catAceptaParteD = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + rechDenExtEdad + "' and " + CatalogosDBConstants.catRoot + "='CPD_CAT_MOTRECHAZO'", null);
                        if (catAceptaParteD != null) cc.setMotivoRechazoParteDExt(catAceptaParteD.getCatKey());
                        cc.setOtroMotivoRechazoParteDExt(otroRechDenExtEdad);
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
                    cc.setAceptaContactoFuturo(null);
                    cc.setCodigo(infoMovil.getId());
                    cc.setTamizaje(tamizaje);
                    cc.setVersion(Constants.VERSION_CC_CD);
                    cc.setReconsentimiento(Constants.YESKEYSND);
                    //crear carta de consentimiento para dengue
                    estudiosAdapter.crearCartaConsentimiento(cc);
                    ParticipanteProcesos procesos = participante.getProcesos();
                    if (procesos.getReConsDeng() != null) {
                        procesos.setReConsDeng("No");
                    }
                    procesos.setConsDeng("No");
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
                        if (tieneValor(cmDomicilio) && cmDomicilio.equals(Constants.YES)) {
                            CambioDomicilio cambioDomicilio = new CambioDomicilio();
                            cambioDomicilio.setRecordDate(new Date());
                            cambioDomicilio.setRecordUser(username);
                            cambioDomicilio.setDeviceid(infoMovil.getDeviceId());
                            cambioDomicilio.setEstado('0');
                            cambioDomicilio.setPasive('0');
                            cambioDomicilio.setCodigo(infoMovil.getId());
                            cambioDomicilio.setParticipante(participante);
                            if (tieneValor(dire)) cambioDomicilio.setDireccion(dire);
                            if (tieneValor(barrio)) {
                                Barrio barrio1 = estudiosAdapter.getBarrio(CatalogosDBConstants.nombre + "= '" + barrio + "'", null);
                                cambioDomicilio.setBarrio(barrio1);
                            }
                            cambioDomicilio.setOtroBarrio(otrobarrio);
                            cambioDomicilio.setManzana(manzana);
                            if (tieneValor(georef)) {
                                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + georef + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                                if (catSino != null) cambioDomicilio.setPuntoGps(catSino.getCatKey());
                            }
                            cambioDomicilio.setLatitud(null);
                            cambioDomicilio.setLongitud(null);
                            if (tieneValor(georef_razon)) {
                                MessageResource catNoGeo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + georef_razon + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_NOGEO'", null);
                                if (catNoGeo != null) cambioDomicilio.setRazonNoGeoref(catNoGeo.getCatKey());
                            }
                            cambioDomicilio.setOtraRazonNoGeoref(georef_otraRazon);
                            estudiosAdapter.crearCambioDomicilio(cambioDomicilio);
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

                            ContactoParticipante contactoPropio = new ContactoParticipante();
                            contactoPropio.setId(infoMovil.getId());
                            contactoPropio.setRecordDate(new Date());
                            contactoPropio.setRecordUser(username);
                            contactoPropio.setDeviceid(infoMovil.getDeviceId());
                            contactoPropio.setEstado('0');
                            contactoPropio.setPasive('0');
                            contactoPropio.setParticipante(participante);
                            contactoPropio.setNombre(null);
                            contactoPropio.setDireccion(null);
                            if (tieneValor(barrio)) {
                                Barrio barrio1 = estudiosAdapter.getBarrio(CatalogosDBConstants.nombre + "= '" + barrio + "'", null);
                                contactoPropio.setBarrio(barrio1);
                            }else{
                                contactoPropio.setBarrio(participante.getCasa().getBarrio());
                            }
                            contactoPropio.setNumero1(telefonoCel1);
                            contactoPropio.setNumero2(telefonoCel2);
                            contactoPropio.setNumero3(telefonoCel3);
                            if (tieneValor(telefonoClasif1)) {
                                MessageResource catTipo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + telefonoClasif1 + "' and " + CatalogosDBConstants.catRoot + "='CAT_TIPO_TEL'", null);
                                contactoPropio.setTipo1(catTipo.getCatKey());
                            }
                            if (tieneValor(telefonoOper1)) {
                                MessageResource catOperadora = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + telefonoOper1 + "' and " + CatalogosDBConstants.catRoot + "='CAT_OPER_TEL'", null);
                                contactoPropio.setOperadora1(catOperadora.getCatKey());
                            }
                            if (tieneValor(telefonoClasif2)) {
                                MessageResource catTipo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + telefonoClasif2 + "' and " + CatalogosDBConstants.catRoot + "='CAT_TIPO_TEL'", null);
                                contactoPropio.setTipo2(catTipo.getCatKey());
                            }
                            if (tieneValor(telefonoOper2)) {
                                MessageResource catOperadora = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + telefonoOper2 + "' and " + CatalogosDBConstants.catRoot + "='CAT_OPER_TEL'", null);
                                contactoPropio.setOperadora2(catOperadora.getCatKey());
                            }
                            if (tieneValor(telefonoClasif3)) {
                                MessageResource catTipo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + telefonoClasif3 + "' and " + CatalogosDBConstants.catRoot + "='CAT_TIPO_TEL'", null);
                                contactoPropio.setTipo3(catTipo.getCatKey());
                            }
                            if (tieneValor(telefonoOper3)) {
                                MessageResource catOperadora = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + telefonoOper3 + "' and " + CatalogosDBConstants.catRoot + "='CAT_OPER_TEL'", null);
                                contactoPropio.setOperadora3(catOperadora.getCatKey());
                            }
                            contactoPropio.setEsPropio("1");

                            estudiosAdapter.crearContactoParticipante(contactoPropio);
                        }

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
                        }
                        contacto.setOtroBarrio(otrobarrioContacto);
                        contacto.setNumero1(telContactoCel1);
                        contacto.setNumero2(telContactoCel2);
                        if (tieneValor(telContacto1)) {
                            MessageResource catTipo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + telContacto1 + "' and " + CatalogosDBConstants.catRoot + "='CAT_TIPO_TEL'", null);
                            contacto.setTipo1(catTipo.getCatKey());
                        }
                        if (tieneValor(telContactoOper1)) {
                            MessageResource catOperadora = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + telContactoOper1 + "' and " + CatalogosDBConstants.catRoot + "='CAT_OPER_TEL'", null);
                            contacto.setOperadora1(catOperadora.getCatKey());
                        }
                        if (tieneValor(telContactoClasif2)) {
                            MessageResource catTipo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + telContactoClasif2 + "' and " + CatalogosDBConstants.catRoot + "='CAT_TIPO_TEL'", null);
                            contacto.setTipo2(catTipo.getCatKey());
                        }
                        if (tieneValor(telContactoOper2)) {
                            MessageResource catOperadora = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + telContactoOper2 + "' and " + CatalogosDBConstants.catRoot + "='CAT_OPER_TEL'", null);
                            contacto.setOperadora2(catOperadora.getCatKey());
                        }
                        contacto.setEsPropio("0");
                        estudiosAdapter.crearContactoParticipante(contacto);
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
