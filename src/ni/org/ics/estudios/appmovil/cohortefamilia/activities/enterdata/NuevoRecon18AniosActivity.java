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
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaParticipantesCasosActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.ReconChf18AniosForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.ReconChf18AniosFormLabels;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.TamizajeForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.TamizajeFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.CartaConsentimiento;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.Tamizaje;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.CasaCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuInfoActivity;
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


public class NuevoRecon18AniosActivity extends FragmentActivity implements
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
    private ReconChf18AniosFormLabels labels = new ReconChf18AniosFormLabels();
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private static ParticipanteCohorteFamilia participanteCHF = new ParticipanteCohorteFamilia();
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
        participanteCHF = (ParticipanteCohorteFamilia) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevoRecon18AniosActivity.this);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new ReconChf18AniosForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
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
        catVerifTutNoAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
        catVerifTutAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catKey + " in ('1','2','3','6') and " + CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
        estudiosAdapter.close();
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
        try{
            updateModel(page);
            updateConstrains();
            if (recalculateCutOffPage()) {
                if (notificarCambios) mPagerAdapter.notifyDataSetChanged();
                updateBottomBar();
            }
            notificarCambios = true;
        }
        catch(Exception e){
            e.printStackTrace();
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

            if(page.getTitle().equals(labels.getAceptaTamizajePersona())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
                changeStatus(mWizardModel.findByKey(labels.getCriteriosInclusion()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), !visible);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoParticipaPersona()), !visible);
                if(!visible) {
                    resetForm(100);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noAceptaTamizajePersona),Toast.LENGTH_LONG);
                    toast.show();
                }
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoParticipaPersona())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoParticipaPersona()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDondeAsisteProblemasSalud())) {
                if(page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Centro de Salud Sócrates Flores")){
                    changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), false);
                }
                else if(page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro centro de salud")){
                    changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), true);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
                }
                else if(page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Puesto de Salud")){
                    changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), true);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
                }
                else{
                    changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
                }
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getCriteriosInclusion())){
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                visible = test.size()>3;
                changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEsElegible()), visible);
                if(!visible){
                    resetForm(99);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noCumpleCriteriosInclusion),Toast.LENGTH_LONG);
                    toast.show();
                }
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getEsElegible())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
                changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), visible);
                if(!visible){
                    resetForm(98);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noEsElegible),Toast.LENGTH_LONG);
                    toast.show();
                }
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAceptaParticipar())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
                changeStatus(mWizardModel.findByKey(labels.getNombre1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParticipar()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParticipar()), !visible);
                changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), true);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), true);
                notificarCambios = false;

                if(!visible){
                    resetForm(97);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noAceptaParticipar),Toast.LENGTH_LONG);
                    toast.show();
                }
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaParticipar())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParticipar()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getParticipanteOTutorAlfabeto())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteA()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), !visible);
                notificarCambios = false;
                MultipleFixedChoicePage pagetmp = (MultipleFixedChoicePage)mWizardModel.findByKey(labels.getVerifTutor());
                pagetmp.setChoices(!visible?catVerifTutNoAlf:catVerifTutAlf);
                notificarCambios = false;
                if(!visible) {
                    resetForm(95);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noDaAsentimiento),Toast.LENGTH_LONG);
                    toast.show();
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTestigoPresente())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
                changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteA()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaParteA())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
                changeStatus(mWizardModel.findByKey(labels.getMotivoRechazoParteA()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void resetForm(int preg){
        if (preg>99) changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), false);
        if (preg>99) changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEsElegible()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParticipar()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParticipar()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getAceptaParteA()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getMotivoRechazoParteA()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getFinReconLabel()), false);
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
            //estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            //Obtener datos del bundle para el tamizaje
            String id = infoMovil.getId();
            //Recupera el estudio de la base de datos para el tamizaje
            Estudio estudio = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=1", null);
            String aceptaTamizajePersona = datos.getString(this.getString(R.string.aceptaTamizajePersona));
            String razonNoAceptaTamizajePersona = datos.getString(this.getString(R.string.razonNoParticipaPersona));
            String otraRazonNoAceptaTamizajePersona = datos.getString(this.getString(R.string.otraRazonNoParticipaPersona));
            String criteriosInclusion = datos.getString(this.getString(R.string.criteriosInclusion));
            String enfermedad = datos.getString(this.getString(R.string.enfermedad));
            String dondeAsisteProblemasSalud = datos.getString(this.getString(R.string.dondeAsisteProblemasSalud));
            String otroCentroSalud = datos.getString(this.getString(R.string.otroCentroSalud));
            String puestoSalud = datos.getString(this.getString(R.string.puestoSalud));
            String aceptaAtenderCentro = datos.getString(this.getString(R.string.aceptaAtenderCentro));
            String esElegible = datos.getString(this.getString(R.string.esElegible));
            String aceptaParticipar = datos.getString(this.getString(R.string.aceptaParticipar));
            String razonNoAceptaParticipar = datos.getString(this.getString(R.string.razonNoAceptaParticipar));
            String otraRazonNoAceptaParticipar = datos.getString(this.getString(R.string.otraRazonNoAceptaParticipar));
            String asentimientoVerbal = datos.getString(this.getString(R.string.asentimientoVerbal));
            String emancipado = datos.getString(this.getString(R.string.emancipado));

            //Crea un Nuevo Registro de tamizaje
            Tamizaje t = new Tamizaje();
            t.setCodigo(id);
            t.setCohorte("CHF");
            t.setEstudio(estudio);
            t.setSexo(participanteCHF.getParticipante().getSexo());
            t.setFechaNacimiento(participanteCHF.getParticipante().getFechaNac());
            if (tieneValor(aceptaTamizajePersona)) {
                MessageResource catAceptaTamizajePersona = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaTamizajePersona + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAceptaTamizajePersona != null)
                    t.setAceptaTamizajePersona(catAceptaTamizajePersona.getCatKey());
            }
            if (tieneValor(razonNoAceptaTamizajePersona)) {
                MessageResource catRazonNoAceptaTamizajePersona = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaTamizajePersona + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
                if (catRazonNoAceptaTamizajePersona != null)
                    t.setRazonNoAceptaTamizajePersona(catRazonNoAceptaTamizajePersona.getCatKey());
            }
            if (tieneValor(criteriosInclusion)) {
                String keysCriterios = "";
                criteriosInclusion = criteriosInclusion.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','");
                List<MessageResource> mcriteriosInclusion = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + criteriosInclusion + "') and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_CI'", null);
                for (MessageResource ms : mcriteriosInclusion) {
                    keysCriterios += ms.getCatKey() + ",";
                }
                if (!keysCriterios.isEmpty())
                    keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                t.setCriteriosInclusion(keysCriterios);
            }
            if (tieneValor(enfermedad)) {
                MessageResource catEnfermedad = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfermedad + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catEnfermedad != null) t.setEnfermedad(catEnfermedad.getCatKey());
            }
            if (tieneValor(dondeAsisteProblemasSalud)) {
                MessageResource catDondeAsisteProblemasSalud = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dondeAsisteProblemasSalud + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_DONDEASISTE'", null);
                if (catDondeAsisteProblemasSalud != null)
                    t.setDondeAsisteProblemasSalud(catDondeAsisteProblemasSalud.getCatKey());
            }
            if (tieneValor(otroCentroSalud)) t.setOtroCentroSalud(otroCentroSalud);
            if (tieneValor(puestoSalud)) {
                MessageResource catPuestoSalud = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + puestoSalud + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_PUESTO'", null);
                if (catPuestoSalud != null) t.setPuestoSalud(catPuestoSalud.getCatKey());
            }
            if (tieneValor(aceptaAtenderCentro)) {
                MessageResource catAceptaAtenderCentro = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaAtenderCentro + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAceptaAtenderCentro != null) t.setAceptaAtenderCentro(catAceptaAtenderCentro.getCatKey());
            }
            if (tieneValor(esElegible)) {
                MessageResource catEsElegible = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + esElegible + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catEsElegible != null) t.setEsElegible(catEsElegible.getCatKey());
            }
            if (tieneValor(aceptaParticipar)) {
                MessageResource catAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParticipar + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAceptaParticipar != null) t.setAceptaParticipar(catAceptaParticipar.getCatKey());
            }
            if (tieneValor(razonNoAceptaParticipar)) {
                MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaParticipar + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
                if (catRazonNoAceptaParticipar != null)
                    t.setRazonNoAceptaParticipar(catRazonNoAceptaParticipar.getCatKey());
            }
            if (tieneValor(asentimientoVerbal)) {
                MessageResource catAsentimientoVerbal = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + asentimientoVerbal + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAsentimientoVerbal != null) t.setAsentimientoVerbal(catAsentimientoVerbal.getCatKey());
            }
            if (tieneValor(emancipado)) {
                MessageResource catEmancipado = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + emancipado + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catEmancipado != null) t.setEmancipado(catEmancipado.getCatKey());
            }
            t.setCodigoParticipanteRecon(participanteCHF.getParticipante().getCodigo());
            t.setOtraRazonNoAceptaTamizajePersona(otraRazonNoAceptaTamizajePersona);
            t.setOtraRazonNoAceptaParticipar(otraRazonNoAceptaParticipar);
            t.setRecordDate(new Date());
            t.setRecordUser(username);
            t.setDeviceid(infoMovil.getDeviceId());
            t.setEstado('0');
            t.setPasive('0');
            //Inserta un Nuevo Registro de Tamizaje
            estudiosAdapter.crearTamizaje(t);
            //Pregunta si acepta realizar el tamizaje
            if (t.getAceptaTamizajePersona().equals(Constants.YESKEYSND)) {
                //Pregunta si es elegible y acepta participar
                if (t.getAceptaParticipar() != null && t.getAceptaParticipar().equals(Constants.YESKEYSND)) {
                    //Si la respuesta es si crea o busca un participante
                    //Obtener datos del bundle para el participante

                    String nombre1 = datos.getString(this.getString(R.string.nombre1));
                    String nombre2 = datos.getString(this.getString(R.string.nombre2));
                    String apellido1 = datos.getString(this.getString(R.string.apellido1));
                    String apellido2 = datos.getString(this.getString(R.string.apellido2));

                    String tutor = "";

                    //Obtener datos del bundle para el consentimiento

                    String relacionFamiliarTutor = datos.getString(this.getString(R.string.relacionFamiliarTutor));
                    String participanteOTutorAlfabeto = datos.getString(this.getString(R.string.participanteOTutorAlfabeto));
                    String testigoPresente = datos.getString(this.getString(R.string.testigoPresente));
                    String nombre1Testigo = datos.getString(this.getString(R.string.nombre1Testigo));
                    String nombre2Testigo = datos.getString(this.getString(R.string.nombre2Testigo));
                    String apellido1Testigo = datos.getString(this.getString(R.string.apellido1Testigo));
                    String apellido2Testigo = datos.getString(this.getString(R.string.apellido2Testigo));
                    String aceptaParteA = datos.getString(this.getString(R.string.aceptaParteA));
                    String motivoRechazoParteA = datos.getString(this.getString(R.string.motivoRechazoParteA));
                    String aceptaContactoFuturo = datos.getString(this.getString(R.string.aceptaContactoFuturo));
                    String aceptaParteB = datos.getString(this.getString(R.string.aceptaParteB));
                    String aceptaParteC = datos.getString(this.getString(R.string.aceptaParteC));
                    String verifTutor = datos.getString(this.getString(R.string.verifTutor).replaceAll("25. ", ""));//agregar en carta
                    //Inserta un nuevo consentimiento
                    CartaConsentimiento cc = new CartaConsentimiento();
                    cc.setCodigo(id);
                    cc.setFechaFirma(new Date());
                    cc.setTamizaje(t);
                    cc.setReconsentimiento(Constants.NOKEYSND);
                    cc.setParticipante(participanteCHF.getParticipante());
                    if (tieneValor(nombre1)) {
                        cc.setNombre1Tutor(nombre1);
                        tutor += nombre1;
                    }
                    if (tieneValor(nombre2)) {
                        cc.setNombre2Tutor(nombre2);
                        tutor += " " + nombre2;
                    }
                    if (tieneValor(apellido1)) {
                        cc.setApellido1Tutor(apellido1);
                        tutor += " " + apellido1;
                    }
                    if (tieneValor(apellido2)) {
                        cc.setApellido2Tutor(apellido2);
                        tutor += " " + apellido2;
                    }
                    if (tieneValor(relacionFamiliarTutor)) {
                        MessageResource catRelacionFamiliarTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relacionFamiliarTutor + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_RFTUTOR'", null);
                        if (catRelacionFamiliarTutor != null)
                            cc.setRelacionFamiliarTutor(catRelacionFamiliarTutor.getCatKey());
                    } else {
                        cc.setRelacionFamiliarTutor("8");//El mismo participante
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
                    if (tieneValor(aceptaParteA)) {
                        MessageResource catAceptaParteA = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteA + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catAceptaParteA != null) cc.setAceptaParteA(catAceptaParteA.getCatKey());
                    }
                    if (tieneValor(motivoRechazoParteA)) {
                        MessageResource catMotivoRechazoParteA = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + motivoRechazoParteA + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catMotivoRechazoParteA != null)
                            cc.setMotivoRechazoParteA(catMotivoRechazoParteA.getCatKey());
                    }
                    if (tieneValor(aceptaContactoFuturo)) {
                        MessageResource catAceptaContactoFuturo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaContactoFuturo + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catAceptaContactoFuturo != null) {
                            cc.setAceptaContactoFuturo(catAceptaContactoFuturo.getCatKey());
                        }
                    }
                    if (tieneValor(aceptaParteB)) {
                        MessageResource catAceptaParteB = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteB + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catAceptaParteB != null) {
                            cc.setAceptaParteB(catAceptaParteB.getCatKey());
                        }
                    }
                    if (tieneValor(aceptaParteC)) {
                        MessageResource catAceptaParteC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteC + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catAceptaParteC != null) {
                            cc.setAceptaParteC(catAceptaParteC.getCatKey());
                        }
                    }
                    cc.setReconsentimiento(Constants.YESKEYSND);
                    cc.setVersion(Constants.VERSION_CC_CHF);
                    cc.setRecordDate(new Date());
                    cc.setRecordUser(username);
                    cc.setDeviceid(infoMovil.getDeviceId());
                    cc.setEstado('0');
                    cc.setPasive('0');
                    //Ingresa la carta
                    estudiosAdapter.crearCartaConsentimiento(cc);


                    //Validar si la casa a la que pertenece esta actualmente en seguimiento.. si es asi, agregar el participante al seguimiento
                    CasaCohorteFamiliaCaso casaCaso = estudiosAdapter.getCasaCohorteFamiliaCaso(CasosDBConstants.casa + "='" + participanteCHF.getCasaCHF().getCodigoCHF() + "'", null);
                    if (casaCaso != null) {
                        ParticipanteCohorteFamiliaCaso existePartCaso = estudiosAdapter.getParticipanteCohorteFamiliaCaso(CasosDBConstants.codigoCaso + "='"+casaCaso.getCodigoCaso()+"' and "+ CasosDBConstants.participante+"="+participanteCHF.getParticipante().getCodigo(),null);
                        //solo agregar si no existe
                        if (existePartCaso==null) {
                            ParticipanteCohorteFamiliaCaso pCaso = new ParticipanteCohorteFamiliaCaso();
                            pCaso.setCodigoCasoParticipante(infoMovil.getId());
                            pCaso.setParticipante(participanteCHF);
                            pCaso.setCodigoCaso(casaCaso);
                            pCaso.setEnfermo(Constants.NOKEYSND);
                            pCaso.setRecordDate(new Date());
                            pCaso.setRecordUser(username);
                            pCaso.setDeviceid(infoMovil.getDeviceId());
                            pCaso.setEstado('0');
                            pCaso.setPasive('0');
                            try {
                                estudiosAdapter.crearParticipanteCohorteFamiliaCaso(pCaso);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Participante participante = participanteCHF.getParticipante();
                    participante.setNombre1(nombre1);
                    participante.setNombre2(nombre2);
                    participante.setApellido1(apellido1);
                    participante.setApellido2(apellido2);
                    estudiosAdapter.editarParticipante(participante);
                    ParticipanteProcesos procesos = participanteCHF.getParticipante().getProcesos();
                    if (tieneValor(cc.getRelacionFamiliarTutor()))
                        procesos.setRelacionFam(Integer.valueOf(cc.getRelacionFamiliarTutor()));
                    else {
                        procesos.setRelacionFam(8);//El mismo Participante
                    }
                    if (tieneValor(tutor))
                        procesos.setTutor(tutor);
                    else
                        procesos.setTutor(Constants.NA);
                    procesos.setReConsChf18(Constants.NO);
                    MovilInfo movilInfo = new MovilInfo();
                    movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                    movilInfo.setDeviceid(infoMovil.getDeviceId());
                    movilInfo.setUsername(username);
                    movilInfo.setToday(new Date());
                    procesos.setMovilInfo(movilInfo);
                    estudiosAdapter.actualizarParticipanteProcesos(procesos);


                    Intent i = new Intent(getApplicationContext(),
                            MenuInfoActivity.class);
                    i.putExtra(ConstantsDB.CODIGO, participanteCHF.getParticipante().getCodigo());
                    i.putExtra(Constants.INGRESO_CHF, true);
                    i.putExtra(ConstantsDB.VIS_EXITO, true);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                } else {
                    ParticipanteProcesos procesos = participanteCHF.getParticipante().getProcesos();
                    procesos.setReConsChf18(Constants.NO);
                    MovilInfo movilInfo = new MovilInfo();
                    movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                    movilInfo.setDeviceid(infoMovil.getDeviceId());
                    movilInfo.setUsername(username);
                    movilInfo.setToday(new Date());
                    procesos.setMovilInfo(movilInfo);
                    estudiosAdapter.actualizarParticipanteProcesos(procesos);
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noAceptaParticipar), Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
            } else {
                ParticipanteProcesos procesos = participanteCHF.getParticipante().getProcesos();
                procesos.setReConsChf18(Constants.NO);
                MovilInfo movilInfo = new MovilInfo();
                movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                movilInfo.setDeviceid(infoMovil.getDeviceId());
                movilInfo.setUsername(username);
                movilInfo.setToday(new Date());
                procesos.setMovilInfo(movilInfo);
                estudiosAdapter.actualizarParticipanteProcesos(procesos);
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noAceptaTamizajePersona), Toast.LENGTH_LONG);
                toast.show();
                finish();
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);
            toast.show();
            finish();
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
