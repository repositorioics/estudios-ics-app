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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


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
    private Integer edadMeses = 0;
    private String tipoIngreso = "";
    private final String TIPO_DENGUE = "Dengue";
    private final String TIPO_INFLUENZA = "Influenza";
    private final String TIPO_AMBOS = "Ambos";

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
            if (page.getTitle().equals(labels.getTipoIngreso())) {
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(TIPO_DENGUE)) {
                    tipoIngreso = TIPO_DENGUE;
                }
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(TIPO_INFLUENZA)) {
                    tipoIngreso = TIPO_INFLUENZA;
                }
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(TIPO_AMBOS)) {
                    tipoIngreso = TIPO_AMBOS;
                }
                changeStatus(mWizardModel.findByKey(labels.getFechaNacimiento()), true);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFechaNacimiento())) {
                DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaNacimiento = null;
                try {
                    fechaNacimiento = mDateFormat.parse(page.getData().getString(DatePage.SIMPLE_DATA_KEY));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
                String[] edad = new CalcularEdad(fechaNacimiento).getEdad().split("/");
                edadAnios = Integer.parseInt(edad[0]);
                edadMeses = Integer.parseInt(edad[1]);
                if (!(edadAnios >= 2 && edadAnios < 10) && !(edadAnios >= 0 && edadAnios < 10)){
                    changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), false);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), true);
                    notificarCambios = false;
                    Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.noEsElegible) + " Dengue o Influenza", Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(99);
                }else {
                    if (!(edadAnios >= 2 && edadAnios < 10) && (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE) || tipoIngreso.equalsIgnoreCase(TIPO_AMBOS))){
                        Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noEsElegible) + " Dengue",Toast.LENGTH_LONG);
                        toast.show();
                    }
                    if (!(edadAnios >= 0 && edadAnios < 10) && (tipoIngreso.contains(TIPO_INFLUENZA) || tipoIngreso.equalsIgnoreCase(TIPO_AMBOS))){
                        Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.noEsElegible) + " Influenza", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    if (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE)){
                        if (!(edadAnios >= 2 && edadAnios < 10)) {
                            changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), false);
                            notificarCambios = false;
                            changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), true);
                            notificarCambios = false;
                            resetForm(99);
                        }else{
                            changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), true);
                            notificarCambios = false;
                        }
                    }
                    if (tipoIngreso.contains(TIPO_INFLUENZA)){
                        if (!(edadAnios >= 0 && edadAnios < 10)) {
                            changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), false);
                            notificarCambios = false;
                            changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), true);
                            notificarCambios = false;
                            resetForm(99);
                        }else{
                            changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), true);
                            notificarCambios = false;
                        }
                    }
                    if (tipoIngreso.equalsIgnoreCase(TIPO_AMBOS)){
                        changeStatus(mWizardModel.findByKey(labels.getAceptaTamizajePersona()), true);
                        notificarCambios = false;
                    }
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaTamizajePersona())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCriteriosInclusion()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoParticipaPersona()), !visible);
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
            if(page.getTitle().equals(labels.getCriteriosInclusion())){
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                visible = test!=null && test.size()==2;
                changeStatus(mWizardModel.findByKey(labels.getTiempoResidencia()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDiagDengue()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), visible && edadAnios>5 && edadAnios<10); //10 porque es el limite de edad q se va a permitir al momento del desarrollo (marzo 2018)
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteDengue()), visible && (edadAnios >= 2 && edadAnios < 10) && (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE) || tipoIngreso.equalsIgnoreCase(TIPO_AMBOS)));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenza()), visible && (edadAnios >= 0 && edadAnios < 10) && (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA) || tipoIngreso.equalsIgnoreCase(TIPO_AMBOS)));
                notificarCambios = false;
                if(test!=null && test.size()!=2){
                    resetForm(99);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noCumpleCriteriosInclusion),Toast.LENGTH_LONG);
                    toast.show();
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDondeAsisteProblemasSalud())) {
                if(page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null) {
                    if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Centro de Salud Sócrates Flores")) {
                        changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true); //aunque sea CSF preguntar si esta dispuesto a ir solamente al CSF
                    } else if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro centro de salud")) {
                        changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), true);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
                    } else if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Puesto de Salud")) {
                        changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), true);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
                    } else {
                        changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
                    }
                }else{
                    changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), false);
                }
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfermedad())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCualEnfermedad()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTratamiento()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDiagDengue())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFechaDiagDengue()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getHospDengue()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getHospDengue())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFechaHospDengue()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTratamiento())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCualTratamiento()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAsentimientoVerbal())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteDengue()), visible && (edadAnios >= 2 && edadAnios < 10) && (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE) || tipoIngreso.equalsIgnoreCase(TIPO_AMBOS)));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenza()), visible && (edadAnios >= 0 && edadAnios < 10) && (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA) || tipoIngreso.equalsIgnoreCase(TIPO_AMBOS)));
                notificarCambios = false;
                if(!visible) {
                    resetForm(98);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noDaAsentimiento),Toast.LENGTH_LONG);
                    toast.show();
                }
                notificarCambios = false;
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
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteD()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCasaPerteneceCohorte()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrio()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getManzana()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDireccion()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoNuevoParticipante()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1Padre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Padre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Padre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Padre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1Madre()), (visible || (visibleInf && !esInmuno && !esPretermino))&& edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Madre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Madre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Madre()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), (visible || (visibleInf && !esInmuno && !esPretermino)) && edadAnios<18);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombreContacto()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDireccionContacto()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTieneTelefono()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono1()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefono()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), visible || (visibleInf && !esInmuno && !esPretermino));
                notificarCambios = false;

                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaDengue()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaDengue())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaDengue()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaCohorteInfluenza())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getPretermino()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEnfermedadInmuno()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), visible);
                notificarCambios = false;
                //changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), visible);
                //notificarCambios = false;
                //changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), visible);
                notificarCambios = false;
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaInfluenza()), visible);
                notificarCambios = false;
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
                    Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.noEsElegible) + " Influenza", Toast.LENGTH_LONG);
                    toast.show();
                    if (!visibleDen) {
                        resetForm(97);
                    }
                }else {
                    Page pagetmp2 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEnfermedadInmuno());
                    boolean esInmuno = pagetmp2.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp2.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);

                    changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCasaPerteneceCohorte()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getBarrio()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getManzana()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDireccion()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoNuevoParticipante()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Padre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Padre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Padre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Padre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Madre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Madre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Madre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Madre()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), ((visible && !esInmuno) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombreContacto()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDireccionContacto()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTieneTelefono()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTipoTelefono1()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefono()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), (visible && !esInmuno) || visibleDen);
                    notificarCambios = false;
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEnfermedadInmuno())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                Page pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaCohorteDengue());
                boolean visibleDen = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.noEsElegible) + " Influenza", Toast.LENGTH_LONG);
                    toast.show();
                    if (!visibleDen) {
                        resetForm(97);
                    }
                }else {
                    Page pagetmp2 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPretermino());
                    boolean esPretermino = pagetmp2.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp2.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);

                    changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCasaPerteneceCohorte()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoNuevoParticipante()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Padre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Padre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Padre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Padre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Madre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Madre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Madre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Madre()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), ((visible && !esPretermino) || visibleDen) && edadAnios < 18);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombreContacto()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDireccionContacto()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getBarrioContacto()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTieneTelefono()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTipoTelefono1()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefono()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getBarrio()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getManzana()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getDireccion()), (visible && !esPretermino) || visibleDen);
                    notificarCambios = false;

                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaInfluenza())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaInfluenza()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getCasaPerteneceCohorte())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), visible);
                notificarCambios = false;
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrio()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getManzana()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDireccion()), visible);
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

                Page pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaCohorteDengue());
                boolean visibleTmp = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible && visibleTmp);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible && visibleTmp);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteD()), visible && visibleTmp);
                notificarCambios = false;

                pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaCohorteInfluenza());
                visibleTmp = pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), visible && visibleTmp);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), visible && visibleTmp);
                notificarCambios = false;

                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTieneTelefono())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefono()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), false);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), false);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), false);
                notificarCambios = false;

                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTieneOtroTelefono())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTipoTelefono2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
    	}catch (Exception ex){
            ex.printStackTrace();
        }
    	
    }

    private void resetForm(int preg){
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getTiempoResidencia()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getCualEnfermedad()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getTratamiento()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getCualTratamiento()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getDiagDengue()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getFechaDiagDengue()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getHospDengue()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getFechaHospDengue()), false);

        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteDengue()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaDengue()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaDengue()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getAceptaCohorteInfluenza()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaInfluenza()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaInfluenza()), false);
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
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaParteD()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaParteBInf()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAceptaParteCInf()), false);
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
            String tiempoResidencia = datos.getString(this.getString(R.string.tiempoResidencia));
            String enfermedad = datos.getString(this.getString(R.string.enfermedad));
            String cualEnfermedad = datos.getString(this.getString(R.string.cualEnfermedad));
            String diagDengue = datos.getString(this.getString(R.string.diagDengue));
            String fechaDiagDengue = datos.getString(this.getString(R.string.fechaDiagDengue));
            String hospDengue = datos.getString(this.getString(R.string.hospDengue));
            String fechaHospDengue = datos.getString(this.getString(R.string.fechaHospDengue));
            String tratamientoIngreso = datos.getString(this.getString(R.string.tratamientoIngreso));
            String cualTratamiento = datos.getString(this.getString(R.string.cualTratamiento));
            String dondeAsisteProblemasSalud = datos.getString(this.getString(R.string.dondeAsisteProblemasSalud));
            String otroCentroSalud = datos.getString(this.getString(R.string.otroCentroSalud));
            String puestoSalud = datos.getString(this.getString(R.string.puestoSalud));
            String aceptaAtenderCentro = datos.getString(this.getString(R.string.aceptaAtenderCentro));
            String asentimientoVerbal = datos.getString(this.getString(R.string.asentimientoVerbal));

            String aceptaCohorteDengue = datos.getString(this.getString(R.string.aceptaCohorteDengue));
            String razonNoAceptaDengue = datos.getString(this.getString(R.string.razonNoAceptaDengue));
            String otraRazonNoAceptaDengue = datos.getString(this.getString(R.string.otraRazonNoAceptaDengue));

            String aceptaCohorteInfluenza = datos.getString(this.getString(R.string.aceptaCohorteInfluenza));
            String pretermino = datos.getString(this.getString(R.string.pretermino));
            String enfermedadInmuno = datos.getString(this.getString(R.string.enfermedadInmuno));
            String razonNoAceptaInfluenza = datos.getString(this.getString(R.string.razonNoAceptaInfluenza));
            String otraRazonNoAceptaInfluenza = datos.getString(this.getString(R.string.otraRazonNoAceptaInfluenza));

            //Crea un Nuevo Registro de tamizaje
            Tamizaje tamizaje =  new Tamizaje();
            Tamizaje tamizajeInf = new Tamizaje();
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
            if (tieneValor(criteriosInclusion)) {
                String keysCriterios = "";
                criteriosInclusion = criteriosInclusion.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> mcriteriosInclusion = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + criteriosInclusion + "') and "
                        + CatalogosDBConstants.catRoot + "='CP_CAT_CI'", null);
                for(MessageResource ms : mcriteriosInclusion) {
                    keysCriterios += ms.getCatKey() + ",";
                }
                if (!keysCriterios.isEmpty())
                    keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                tamizaje.setCriteriosInclusion(keysCriterios);
            }
            if (tieneValor(tiempoResidencia)) {
                MessageResource cattiempoResidencia = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tiempoResidencia + "' and " + CatalogosDBConstants.catRoot + "='CP_CAT_TR'", null);
                if (cattiempoResidencia!=null) tamizaje.setTiempoResidencia(cattiempoResidencia.getCatKey());
            }
            if (tieneValor(enfermedad)) {
                MessageResource catEnfermedad = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfermedad + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catEnfermedad!=null) tamizaje.setEnfermedad(catEnfermedad.getCatKey());
            }
            tamizaje.setCualEnfermedad(cualEnfermedad);

            if (tieneValor(diagDengue)) {
                MessageResource catDen = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + diagDengue + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (tieneValor(tratamientoIngreso)) {
                MessageResource catTratami = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tratamientoIngreso + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (catTratami!=null) tamizaje.setTratamiento(catTratami.getCatKey());
            }
            tamizaje.setCualTratamiento(cualTratamiento);
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

            //Registrar tamizaje dengue si aplica
            if (tipoIngreso.equalsIgnoreCase(TIPO_DENGUE) || tipoIngreso.equalsIgnoreCase(TIPO_AMBOS)){
                Estudio estudioCDengue = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=" +Constants.COD_EST_COHORTEDENGUE, null);
                tamizaje.setCodigo(infoMovil.getId());
                tamizaje.setEstudio(estudioCDengue);
                //Si acepta o no participar, siempre registrar tamizaje
                MessageResource catAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaCohorteDengue + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAceptaParticipar!=null) tamizaje.setAceptaParticipar(catAceptaParticipar.getCatKey());
                if (tieneValor(razonNoAceptaDengue)) {
                    MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaDengue + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
                    if (catRazonNoAceptaParticipar!=null) tamizaje.setRazonNoAceptaParticipar(catRazonNoAceptaParticipar.getCatKey());
                }
                tamizaje.setOtraRazonNoAceptaParticipar(otraRazonNoAceptaDengue);
                estudiosAdapter.crearTamizaje(tamizaje);
            }
            //Registrar tamizaje dengue si aplica
            if (tipoIngreso.equalsIgnoreCase(TIPO_INFLUENZA) || tipoIngreso.equalsIgnoreCase(TIPO_AMBOS)){
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

                estudiosAdapter.crearTamizaje(tamizajeInf);
            }

            //Pregunta si acepta realizar el tamizaje
            if (tamizaje.getAceptaTamizajePersona().equals(Constants.YESKEYSND)) {

                String aceptaParteB = datos.getString(this.getString(R.string.aceptaParteBDengue));
                String aceptaParteC = datos.getString(this.getString(R.string.aceptaParteCDengue));
                String aceptaParteD = datos.getString(this.getString(R.string.aceptaParteDDengue));

                String aceptaParteBInf = datos.getString(this.getString(R.string.aceptaParteBInf));
                String aceptaParteCInf = datos.getString(this.getString(R.string.aceptaParteCInf));

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

                Boolean aceptaDengue = tieneValor(aceptaCohorteDengue) && aceptaCohorteDengue.equalsIgnoreCase(Constants.YES);
                Boolean aceptaInfluenza = tieneValor(aceptaCohorteInfluenza) && aceptaCohorteInfluenza.equalsIgnoreCase(Constants.YES);
                Boolean esPretermino = tieneValor(pretermino) && pretermino.equalsIgnoreCase(Constants.YES);
                Boolean padeceEnfInmuno = tieneValor(enfermedadInmuno) && enfermedadInmuno.equalsIgnoreCase(Constants.YES);

                //Registrar casa (si es nueva), participante y consentimiento sólo si acepta participar en alguno de los estudios
                if (aceptaDengue || (aceptaInfluenza && !esPretermino && !padeceEnfInmuno)) {
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

                        estudiosAdapter.crearContactoParticipante(contacto);

                        procesos.setCodigo(participante.getCodigo());
                        procesos.setEstPart(1);
                        procesos.setRetoma(Constants.NO);
                        procesos.setAdn(Constants.NO);
                        procesos.setConmx(Constants.NO);
                        procesos.setConmxbhc(Constants.NO);
                        procesos.setPbmc(Constants.NO);
                        procesos.setPaxgene(Constants.YES);
                        procesos.setConPto(Constants.NO);
                        procesos.setConsDeng(Constants.NO);
                        procesos.setObsequio(Constants.YES);
                        procesos.setConsChik(Constants.NO);
                        procesos.setConsFlu(Constants.NO);
                        procesos.setReConsDeng(Constants.NO);
                        procesos.setConvalesciente(Constants.NO);
                        procesos.setMi(Constants.NO);
                        procesos.setZika(Constants.NO);
                        procesos.setPosZika(Constants.NO);
                        procesos.setDatosVisita(Constants.YES);//siempre pedir datos de contacto
                        //aca siempre va a marcar si, porque no hay registro de encuestas, ya que no se descargan del server
                        ArrayList<EncuestaCasa> mEncuestasCasas = estudiosAdapter.getListaEncuestaCasas(casaCohorte.getCodigo());
                        if (mEncuestasCasas.size() <= 0) {
                            procesos.setEnCasa(Constants.YES);
                        } else {
                            procesos.setEnCasa(Constants.NO);
                        }

                        if (aceptaDengue) {
                            procesos.setEnCasaSa(Constants.YES);
                            procesos.setEncPartSa(Constants.YES);
                        } else {
                            procesos.setEnCasaSa(Constants.NO);
                            procesos.setEncPartSa(Constants.NO);
                        }

                        if (participante.getEdadMeses() <= 36)
                            procesos.setEncLacMat(Constants.YES);
                        else
                            procesos.setEncLacMat(Constants.NO);

                        procesos.setEncPart(Constants.YES);
                        procesos.setPesoTalla(Constants.YES);
                        procesos.setDatosParto(Constants.YES);
                        procesos.setInfoVacuna(Constants.YES);

                        procesos.setCasaCHF(null);
                        procesos.setEnCasaChf(Constants.NO);

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
                        procesos.setCuantasPers(0);
                        procesos.setVolRetoma(null);

                        if (aceptaDengue) {
                            estudios = "Dengue";
                        }
                        if (aceptaInfluenza && !esPretermino && !padeceEnfInmuno) {
                            if (estudios.isEmpty()) {
                                estudios = "Influenza";
                            } else
                                estudios += "  " + "Influenza";
                        }

                        procesos.setEstudio(estudios);
                        MovilInfo movilInfo = new MovilInfo();
                        movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                        movilInfo.setDeviceid(infoMovil.getDeviceId());
                        movilInfo.setUsername(username);
                        movilInfo.setToday(new Date());
                        procesos.setMovilInfo(movilInfo);
                        estudiosAdapter.crearParticipanteProcesos(procesos);

                        CartaConsentimiento cc = new CartaConsentimiento();
                        cc.setRecordDate(new Date());
                        cc.setRecordUser(username);
                        cc.setDeviceid(infoMovil.getDeviceId());
                        cc.setEstado('0');
                        cc.setPasive('0');
                        cc.setFechaFirma(new Date());
                        cc.setParticipante(participante);
                        cc.setEmancipado("0"); //por defecto no es emancipado
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

                        //crear carta de consentimiento para dengue
                        if (aceptaDengue){
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
                            cc.setAceptaContactoFuturo(null);
                            cc.setCodigo(infoMovil.getId());
                            cc.setTamizaje(tamizaje);
                            cc.setVersion(Constants.VERSION_CC_CD);
                            estudiosAdapter.crearCartaConsentimiento(cc);
                        }

                        //crear carta de consentimiento para influenza
                        if (aceptaInfluenza && !esPretermino && !padeceEnfInmuno){
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
                            cc.setVersion(Constants.VERSION_CC_CI);
                            cc.setAceptaParteD(null);
                            estudiosAdapter.crearCartaConsentimiento(cc);
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
