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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaMuestrasSuperficieCasoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.TamizajeFormLabels;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.TamizajePersonaSupForm;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.CartaConsentimiento;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.Tamizaje;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCaso;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
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


public class NuevoTamizajeMxSuperficieActivity extends FragmentActivity implements
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
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private static VisitaSeguimientoCaso visitaCaso = new VisitaSeguimientoCaso();
    private static ParticipanteCohorteFamilia participanteCHF = new ParticipanteCohorteFamilia();
    private Integer edadAnios = 0;
    private String[] catVerifTutAlf; //cosas a verificar cuando tutor es alfabeto
    private String[] catVerifTutNoAlf; //cosas a verificar cuando tutor no es alfabeto
    private boolean pedirAsentimientoCasa;
    private boolean pedirConsentimiento;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FileUtils.storageReady()) {
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        setContentView(R.layout.activity_data_enter);
        visitaCaso = (VisitaSeguimientoCaso) getIntent().getExtras().getSerializable(Constants.VISITA);
        pedirAsentimientoCasa = getIntent().getBooleanExtra(Constants.MX_SUPERFICIE_ASEN,true);
        pedirConsentimiento  = getIntent().getBooleanExtra(Constants.MX_SUPERFICIE_CON,true);
        if (visitaCaso!=null){
            participanteCHF = visitaCaso.getCodigoParticipanteCaso().getParticipante();
        }
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevoTamizajeMxSuperficieActivity.this);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new TamizajePersonaSupForm(this,mPass);
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


        estudiosAdapter.open();
        catVerifTutNoAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
        catVerifTutAlf = estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catKey + " in ('1','2','3','6') and " + CatalogosDBConstants.catRoot + "='CP_CAT_VERIFTUTOR'", CatalogosDBConstants.order);
        estudiosAdapter.close();
        String[] edad = participanteCHF.getParticipante().getEdad().split("/");
        edadAnios = Integer.parseInt(edad[0]);
        SingleFixedChoicePage tmpPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAceptaParticipar());
        tmpPage.setmVisible(pedirConsentimiento);
        tmpPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAsentimientoVerbalMxSuper());
        tmpPage.setmVisible(pedirAsentimientoCasa);

        onPageTreeChanged();
        updateBottomBar();
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
            if (page.getTitle().equals(labels.getAceptaParticipar())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParticipar()), !visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParticipar()), !visible);
                notificarCambios = false;
                if(edadAnios>5 && edadAnios<18){
                    changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), visible);
                }
                if (visible) {
                    if (edadAnios > 17) {
                        changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), false);
                    } else {
                        changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), true);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), true);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), true);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), true);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), true);
                        notificarCambios = false;
                    }
                    changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), true);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), true);
                    notificarCambios = false;
                }else {
                    resetForm(97);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noAceptaParticipar),Toast.LENGTH_LONG);
                    toast.show();
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaParticipar())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParticipar()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAsentimientoVerbal())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                if(!visible) {
                    resetForm(96);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noDaAsentimiento),Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    if (edadAnios > 17) {
                        changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), false);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), false);
                    } else {
                        changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), true);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), true);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), true);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), true);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), true);
                        notificarCambios = false;
                    }
                    changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), true);
                    notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), true);
                    notificarCambios = false;
                }
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getParticipanteOTutorAlfabeto())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteDChf()), visible);
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
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteDChf()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaParteDChf())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParteDChf()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaParteDChf())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParteDChf()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAsentimientoVerbalMxSuper())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNombre1MxSuperficie()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNombre2MxSuperficie()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido1MxSuperficie()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getApellido2MxSuperficie()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void resetForm(int preg){
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParticipar()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParticipar()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getAceptaParteDChf()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParteDChf()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParteDChf()), false);
        if (preg>94) changeStatus(mWizardModel.findByKey(labels.getFinTamizajeLabel()), false);
    }

    public void changeStatus(Page page, boolean visible){
        String clase = page.getClass().toString();
        if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage")){
            SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.BarcodePage")){
            BarcodePage modifPage = (BarcodePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
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
            MovilInfo movilInfo = new MovilInfo();
            movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
            movilInfo.setDeviceid(infoMovil.getDeviceId());
            movilInfo.setUsername(username);
            movilInfo.setToday(new Date());
            //Recupera el estudio de la base de datos para el tamizaje
            Estudio estudio = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=1", null);

            ParticipanteProcesos procesosPartActual = participanteCHF.getParticipante().getProcesos();

            String aceptaParticipar = datos.getString(this.getString(R.string.aceptaParticipar));
            String razonNoAceptaParticipar = datos.getString(this.getString(R.string.razonNoAceptaParticipar));
            String otraRazonNoAceptaParticipar = datos.getString(this.getString(R.string.otraRazonNoAceptaParticipar));
            String asentimientoVerbal = datos.getString(this.getString(R.string.asentimientoVerbal));
            String asentimientoVerbalMxSuper = datos.getString(this.getString(R.string.asentimientoVerbalMxSuper));
            String aceptaParteD = datos.getString(this.getString(R.string.aceptaParteDChf));
            //Crea un Nuevo Registro de tamizaje casa
            Tamizaje tCasa = new Tamizaje();
            tCasa.setCodigo(infoMovil.getId());
            tCasa.setCohorte("CHF");
            tCasa.setEstudio(estudio);
            tCasa.setRecordDate(new Date());
            tCasa.setRecordUser(username);
            tCasa.setDeviceid(infoMovil.getDeviceId());
            tCasa.setEstado('0');
            tCasa.setPasive('0');
            //Crea un Nuevo Registro de tamizaje participante
            Tamizaje t = new Tamizaje();
            t.setCodigo(id);
            t.setCohorte("CHF");
            t.setEstudio(estudio);
            t.setSexo(participanteCHF.getParticipante().getSexo());
            t.setFechaNacimiento(participanteCHF.getParticipante().getFechaNac());

            MessageResource catAceptaTamizaje = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + Constants.YES + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
            if (catAceptaTamizaje != null) {
                t.setAceptaTamizajePersona(catAceptaTamizaje.getCatKey());
                tCasa.setAceptaTamizajePersona(catAceptaTamizaje.getCatKey());
                tCasa.setAceptaParticipar(catAceptaTamizaje.getCatKey());
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
            if (tieneValor(asentimientoVerbalMxSuper)) {
                MessageResource catAsentimientoVerbal = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + asentimientoVerbalMxSuper + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (catAsentimientoVerbal != null) tCasa.setAsentimientoVerbal(catAsentimientoVerbal.getCatKey());
            }
            t.setOtraRazonNoAceptaTamizajePersona(null);
            t.setOtraRazonNoAceptaParticipar(otraRazonNoAceptaParticipar);
            t.setRecordDate(new Date());
            t.setRecordUser(username);
            t.setDeviceid(infoMovil.getDeviceId());
            t.setEstado('0');
            t.setPasive('0');
            //Inserta un Nuevo Registro de Tamizaje
            estudiosAdapter.crearTamizaje(tCasa);
            //carta consentimiento casa mx superficie
            if (tieneValor(asentimientoVerbalMxSuper)){
                //Inserta un nuevo consentimiento
                CartaConsentimiento cc = new CartaConsentimiento();
                cc.setCodigo(infoMovil.getId());
                cc.setFechaFirma(new Date());
                cc.setTamizaje(tCasa);
                cc.setCasaChf(visitaCaso.getCodigoParticipanteCaso().getCodigoCaso().getCasa().getCodigoCHF());

                String nombre1MxSuperficie = datos.getString(this.getString(R.string.nombre1MxSuperficie));
                String nombre2MxSuperficie = datos.getString(this.getString(R.string.nombre2MxSuperficie));
                String apellido1MxSuperficie = datos.getString(this.getString(R.string.apellido1MxSuperficie));
                String apellido2MxSuperficie = datos.getString(this.getString(R.string.apellido2MxSuperficie));

                cc.setNombre1MxSuperficie(nombre1MxSuperficie);
                cc.setNombre2MxSuperficie(nombre2MxSuperficie);
                cc.setApellido1MxSuperficie(apellido1MxSuperficie);
                cc.setApellido2MxSuperficie(apellido2MxSuperficie);
                cc.setReconsentimiento("0");
                cc.setVersion(Constants.VERSION_CC_CHF);
                cc.setRecordDate(new Date());
                cc.setRecordUser(username);
                cc.setDeviceid(infoMovil.getDeviceId());
                cc.setEstado('0');
                cc.setPasive('0');
                //Ingresa la carta
                estudiosAdapter.crearCartaConsentimiento(cc);
                if (asentimientoVerbalMxSuper.equalsIgnoreCase(Constants.YES)) {
                    List<ParticipanteCohorteFamiliaCaso> parCasos = estudiosAdapter.getParticipanteCohorteFamiliaCasos(CasosDBConstants.codigoCaso + "='" + visitaCaso.getCodigoParticipanteCaso().getCodigoCaso().getCodigoCaso() + "'", null);
                    for (ParticipanteCohorteFamiliaCaso parCaso : parCasos) {
                        ParticipanteProcesos procesoPartCaso = parCaso.getParticipante().getParticipante().getProcesos();
                        if (procesoPartCaso != null && !procesoPartCaso.getCodigo().equals(procesosPartActual.getCodigo())) {
                            if (parCaso.getEnfermo().equalsIgnoreCase("S")) procesoPartCaso.setMxSuperficie("2");
                            else procesoPartCaso.setMxSuperficie("0");
                            procesoPartCaso.setMovilInfo(movilInfo);
                            estudiosAdapter.actualizarParticipanteProcesos(procesoPartCaso);
                        } else {
                            if (visitaCaso.getCodigoParticipanteCaso().getEnfermo().equalsIgnoreCase("S"))
                                procesosPartActual.setMxSuperficie("2");
                            else
                                procesosPartActual.setMxSuperficie("0");
                            estudiosAdapter.actualizarParticipanteProcesos(procesosPartActual);
                        }
                    }
                }
                if (asentimientoVerbalMxSuper.equalsIgnoreCase(Constants.YES) && !tieneValor(aceptaParteD)) {
                    estudiosAdapter.actualizarParticipanteProcesos(procesosPartActual);
                    Bundle arguments = new Bundle();
                    Intent i;
                    arguments.putSerializable(Constants.VISITA, visitaCaso);
                    i = new Intent(getApplicationContext(),
                            NuevaMuestrasSuperficieActivity.class);
                    i.putExtra(Constants.MX_SUPERFICIE, 1); //solo muestras superficie
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtras(arguments);
                    startActivity(i);
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            //Pregunta si acepta realizar el tamizaje
            if (t.getAceptaTamizajePersona()!=null && tieneValor(aceptaParticipar)) {
                estudiosAdapter.crearTamizaje(t);

                //Pregunta si es elegible y acepta participar
                if (t.getAceptaTamizajePersona()!=null && t.getAceptaParticipar().equals(Constants.YESKEYSND)) {

                    //Obtener datos del bundle para el consentimiento
                    String razonNoAceptaParteDChf = datos.getString(this.getString(R.string.razonNoAceptaParteDChf));
                    String otraRazonNoAceptaParteDChf = datos.getString(this.getString(R.string.otraRazonNoAceptaParteDChf));
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
                    String verifTutor = datos.getString(this.getString(R.string.verifTutor).replaceAll("25. ", ""));
                    //Inserta un nuevo consentimiento
                    CartaConsentimiento cc = new CartaConsentimiento();
                    cc.setCodigo(id);
                    cc.setFechaFirma(new Date());
                    cc.setTamizaje(t);
                    cc.setReconsentimiento("2"); //Anexo carta
                    cc.setParticipante(participanteCHF.getParticipante());
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
                    if (tieneValor(aceptaParteD)) {
                        MessageResource catAceptaParteD = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteD + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                        if (catAceptaParteD != null) cc.setAceptaParteD(catAceptaParteD.getCatKey());
                    }
                    if (tieneValor(razonNoAceptaParteDChf)) {
                        MessageResource catMotivoRechazoParteA = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaParteDChf + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
                        if (catMotivoRechazoParteA != null)
                            cc.setMotivoRechazoParteDExt(catMotivoRechazoParteA.getCatKey());
                        cc.setOtroMotivoRechazoParteDExt(otraRazonNoAceptaParteDChf);
                    }
                    cc.setVersion(Constants.VERSION_CC_CHF);
                    cc.setRecordDate(new Date());
                    cc.setRecordUser(username);
                    cc.setDeviceid(infoMovil.getDeviceId());
                    cc.setEstado('0');
                    cc.setPasive('0');
                    //Ingresa la carta
                    estudiosAdapter.crearCartaConsentimiento(cc);
                    if (pedirAsentimientoCasa && asentimientoVerbalMxSuper.equalsIgnoreCase(Constants.NO)) {
                        procesosPartActual.setMxSuperficie("1");
                    }else {
                        procesosPartActual.setMxSuperficie("0");
                    }
                    procesosPartActual.setMovilInfo(movilInfo);
                    estudiosAdapter.actualizarParticipanteProcesos(procesosPartActual);
                    if (aceptaParteD.equalsIgnoreCase(Constants.YES)) {
                        boolean asiente = tieneValor(asentimientoVerbalMxSuper) && asentimientoVerbalMxSuper.equalsIgnoreCase(Constants.YES);
                        Bundle arguments = new Bundle();
                        Intent i;
                        arguments.putSerializable(Constants.VISITA, visitaCaso);
                        i = new Intent(getApplicationContext(),
                                NuevaMuestrasSuperficieActivity.class);
                        i.putExtra(Constants.MX_SUPERFICIE, asiente?3:2); //2: solo muestras de manos, 3: todas
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
                        toast.show();
                    }else {
                        Bundle arguments = new Bundle();
                        Intent i;
                        arguments.putSerializable(Constants.VISITA, visitaCaso);
                        i = new Intent(getApplicationContext(),
                                ListaMuestrasSuperficieCasoActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noAceptaParticipar), Toast.LENGTH_LONG);
                        toast.show();
                    }

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noAceptaParticipar), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            //Cierra la base de datos
            estudiosAdapter.close();
            finish();
        }catch (Exception e){
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);
            toast.show();
            finish();
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
