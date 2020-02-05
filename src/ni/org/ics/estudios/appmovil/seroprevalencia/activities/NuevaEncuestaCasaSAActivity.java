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
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuCasaActivity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaCasaSA;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuInfoActivity;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.seroprevalencia.forms.EncuestaCasaSAForm;
import ni.org.ics.estudios.appmovil.seroprevalencia.forms.EncuestaCasaSAFormLabels;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Deprecated
public class NuevaEncuestaCasaSAActivity extends FragmentActivity implements
        PageFragmentCallbacks,
        ReviewFragment.Callbacks,
        ModelCallbacks

{
    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;
    private boolean mEditingAfterReview;
    private AbstractWizardModel mWizardModel;
    private boolean mConsumePageSelectedEvent;
    private Button mNextButton;
    private Button mPrevButton;

    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;

    private EncuestaCasaSAFormLabels labels;
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static CasaCohorteFamilia casaCHF = new CasaCohorteFamilia();
    private static Casa casa = new Casa();
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private static boolean desdeMA = false;
    private boolean visExitosa = false;
    private static Integer codigo = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_enter);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevaEncuestaCasaSAActivity.this);
        casaCHF = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASACHF);
        casa = (Casa) getIntent().getExtras().getSerializable(Constants.CASA);
        codigo = getIntent().getIntExtra(ConstantsDB.CODIGO,-1);
        desdeMA = getIntent().getBooleanExtra(Constants.MENU_INFO, false);
        visExitosa = getIntent().getBooleanExtra(ConstantsDB.VIS_EXITO,false);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new EncuestaCasaSAForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        labels = new EncuestaCasaSAFormLabels();

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
                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            createDialog(EXIT);
                                        }
                                    }).create();
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
        updateModel(page);
        updateConstrains();
        if (recalculateCutOffPage()) {
            if (notificarCambios) mPagerAdapter.notifyDataSetChanged();
            updateBottomBar();
        }
        notificarCambios = true;
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

        try {
            boolean visible = false;

            if (page.getTitle().equals(labels.getFumiga())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCadaCuantoFumiga()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMiembroFamConZikaSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantMiembrosZika()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getFechaDxZika()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamZika()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRelacionFamZika())) {
                    visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains("Otra relacion familiar");
                    changeStatus(mWizardModel.findByKey(labels.getOtraRelacionFamZika()), visible);
                    notificarCambios = false;
                    onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMiembroFamConDengueSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantMiembrosDengue()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getFechaDxDengue()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamDengue()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRelacionFamDengue())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains("Otra relacion familiar");
                changeStatus(mWizardModel.findByKey(labels.getOtraRelacionFamDengue()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMiembroFamConChikSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantMiembrosChik()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getFechaDxChik()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamChik()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRelacionFamChik())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains("Otra relacion familiar");
                changeStatus(mWizardModel.findByKey(labels.getOtraRelacionFamChik()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void changeStatus(Page page, boolean visible){
        String clase = page.getClass().toString();
        if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage")){
            SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.setValue("").setmVisible(visible);//modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.BarcodePage")){
            BarcodePage modifPage = (BarcodePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.LabelPage")){
            LabelPage modifPage = (LabelPage) page; modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.TextPage")){
            TextPage modifPage = (TextPage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NumberPage")){
            NumberPage modifPage = (NumberPage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage")){
            MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
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
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }
            String sedazoPuertasVentanas = datos.getString(this.getString(R.string.sedazoPuertasVentanas));
            String compraProdEvitarZancudos = datos.getString(this.getString(R.string.compraProdEvitarZancudos));
            String tienePatioJardin = datos.getString(this.getString(R.string.tienePatioJardin));
            String utilizaAbate = datos.getString(this.getString(R.string.utilizaAbate));
            String fumiga = datos.getString(this.getString(R.string.fumiga));
            String cadaCuantoFumiga = datos.getString(this.getString(R.string.cadaCuantoFumiga));

            String miembroFamConZikaSn = datos.getString(this.getString(R.string.miembroFamConZikaSn));
            String cantMiembrosZika = datos.getString(this.getString(R.string.cantMiembrosZika));
            String fechaDxZika = datos.getString(this.getString(R.string.fechaDxZika));
            String relacionFamZika = datos.getString(this.getString(R.string.relacionFamZika));
            String otraRelacionFamZika = datos.getString(this.getString(R.string.otraRelacionFamZika));

            String miembroFamConDengueSn = datos.getString(this.getString(R.string.miembroFamConDengueSn));
            String cantMiembrosDengue = datos.getString(this.getString(R.string.cantMiembrosDengue));
            String fechaDxDengue = datos.getString(this.getString(R.string.fechaDxDengue));
            String relacionFamDengue = datos.getString(this.getString(R.string.relacionFamDengue));
            String otraRelacionFamDengue = datos.getString(this.getString(R.string.otraRelacionFamDengue));

            String miembroFamConChikSn = datos.getString(this.getString(R.string.miembroFamConChikSn));
            String cantMiembrosChik = datos.getString(this.getString(R.string.cantMiembrosChik));
            String fechaDxChik = datos.getString(this.getString(R.string.fechaDxChik));
            String relacionFamChik = datos.getString(this.getString(R.string.relacionFamChik));
            String otraRelacionFamChik = datos.getString(this.getString(R.string.otraRelacionFamChik));


            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();


            EncuestaCasaSA encuestaCasa = new EncuestaCasaSA();
            encuestaCasa.setCodigo(infoMovil.getId());
            encuestaCasa.setCasa(casa);
            encuestaCasa.setCasaChf(casaCHF);

            if (tieneValor(sedazoPuertasVentanas)) {
                MessageResource mssedazoPuertasVentanas = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + sedazoPuertasVentanas + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (mssedazoPuertasVentanas != null) encuestaCasa.setSedazoPuertasVentanas(mssedazoPuertasVentanas.getCatKey());
            }
            if (tieneValor(compraProdEvitarZancudos)) {
                MessageResource mscompraProdEvitarZancudos = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + compraProdEvitarZancudos + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (mscompraProdEvitarZancudos != null) encuestaCasa.setCompraProdEvitarZancudos(mscompraProdEvitarZancudos.getCatKey());
            }
            if (tieneValor(tienePatioJardin)) {
                MessageResource mstienePatioJardin = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tienePatioJardin + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (mstienePatioJardin != null) encuestaCasa.setTienePatioJardin(mstienePatioJardin.getCatKey());
            }
            if (tieneValor(utilizaAbate)) {
                MessageResource msutilizaAbate = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + utilizaAbate + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msutilizaAbate != null) encuestaCasa.setUtilizaAbate(msutilizaAbate.getCatKey());
            }
            if (tieneValor(fumiga)) {
                MessageResource msfumiga = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + fumiga + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msfumiga != null) encuestaCasa.setFumiga(msfumiga.getCatKey());
            }
            if (tieneValor(cadaCuantoFumiga)) {
                MessageResource mscadaCuantoFumiga = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + cadaCuantoFumiga + "' and "
                        + CatalogosDBConstants.catRoot + "='SA_CAT_FUMIGA'", null);
                if (mscadaCuantoFumiga != null) encuestaCasa.setCadaCuantoFumiga(mscadaCuantoFumiga.getCatKey());
            }
            if (tieneValor(miembroFamConZikaSn)) {
                MessageResource msmiembroFamConZikaSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + miembroFamConZikaSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msmiembroFamConZikaSn != null) encuestaCasa.setMiembroFamConZikaSn(msmiembroFamConZikaSn.getCatKey());
            }
            if (tieneValor(miembroFamConDengueSn)) {
                MessageResource msmiembroFamConDengueSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + miembroFamConDengueSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msmiembroFamConDengueSn != null) encuestaCasa.setMiembroFamConDengueSn(msmiembroFamConDengueSn.getCatKey());
            }
            if (tieneValor(miembroFamConChikSn)) {
                MessageResource msmiembroFamConChikSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + miembroFamConChikSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msmiembroFamConChikSn != null) encuestaCasa.setMiembroFamConChikSn(msmiembroFamConChikSn.getCatKey());
            }
            if (tieneValor(relacionFamZika)) {
                String keys = "";
                relacionFamZika = relacionFamZika.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msrelacionFamZikaList = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + relacionFamZika + "') and "
                        + CatalogosDBConstants.catRoot + "='SA_CAT_RELFAM'", null);
                for(MessageResource ms : msrelacionFamZikaList) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty())
                    keys = keys.substring(0, keys.length() - 1);
                encuestaCasa.setRelacionFamZika(keys);
            }
            if (tieneValor(relacionFamDengue)) {
                String keys = "";
                relacionFamDengue = relacionFamDengue.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msrelacionFamDengueList = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + relacionFamDengue + "') and "
                        + CatalogosDBConstants.catRoot + "='SA_CAT_RELFAM'", null);
                for(MessageResource ms : msrelacionFamDengueList) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty())
                    keys = keys.substring(0, keys.length() - 1);
                encuestaCasa.setRelacionFamDengue(keys);

            }
            if (tieneValor(relacionFamChik)) {
                String keys = "";
                relacionFamChik = relacionFamChik.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msrelacionFamChikList = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + relacionFamChik + "') and "
                        + CatalogosDBConstants.catRoot + "='SA_CAT_RELFAM'", null);
                for(MessageResource ms : msrelacionFamChikList) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty())
                    keys = keys.substring(0, keys.length() - 1);
                encuestaCasa.setRelacionFamChik(keys);
            }

            encuestaCasa.setFechaDxZika(fechaDxZika);
            encuestaCasa.setFechaDxDengue(fechaDxDengue);
            encuestaCasa.setFechaDxChik(fechaDxChik);
            encuestaCasa.setOtraRelacionFamZika(otraRelacionFamZika);
            encuestaCasa.setOtraRelacionFamDengue(otraRelacionFamDengue);
            encuestaCasa.setOtraRelacionFamChik(otraRelacionFamChik);

            if (tieneValor(cantMiembrosZika)) encuestaCasa.setCantMiembrosZika(Integer.valueOf(cantMiembrosZika));
            if (tieneValor(cantMiembrosDengue)) encuestaCasa.setCantMiembrosDengue(Integer.valueOf(cantMiembrosDengue));
            if (tieneValor(cantMiembrosChik)) encuestaCasa.setCantMiembrosChik(Integer.valueOf(cantMiembrosChik));

            //Metadata
            encuestaCasa.setRecordDate(new Date());
            encuestaCasa.setRecordUser(username);
            encuestaCasa.setDeviceid(infoMovil.getDeviceId());
            encuestaCasa.setEstado('0');
            encuestaCasa.setPasive('0');
            //boolean actualizada = false;
            //EncuestaCasaSA encuestaExiste = estudiosAdapter.getEncuestaCasaSA(SeroprevalenciaDBConstants.casaCHF + "=" + casaCHF.getCodigoCHF(), SeroprevalenciaDBConstants.casaCHF);
            //if (encuestaExiste != null && encuestaExiste.getCasaCHF() != null && encuestaExiste.getCasaCHF().getCodigoCHF() != null)
                //actualizada = estudiosAdapter.editarEncuestaCasaSA(encuestaCasa);
            //else
            estudiosAdapter.crearEncuestaCasaSA(encuestaCasa);
            List<Participante> mParticipantes = estudiosAdapter.getParticipantes(MainDBConstants.casa + "=" + casa.getCodigo(), null);
            for(Participante participante:mParticipantes){
                if (participante.getCasa().getCodigo()!=9999) {
                    ParticipanteProcesos procesos = participante.getProcesos();
                    procesos.setEnCasaSa("No");
                    MovilInfo movilInfo = procesos.getMovilInfo();
                    movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                    movilInfo.setDeviceid(infoMovil.getDeviceId());
                    movilInfo.setUsername(username);
                    procesos.setMovilInfo(movilInfo);
                    estudiosAdapter.actualizarParticipanteProcesos(procesos);
                }
            }

            estudiosAdapter.close();
            if (desdeMA){
                Intent i = new Intent(getApplicationContext(),
                        MenuInfoActivity.class);
                i.putExtra(ConstantsDB.COD_CASA, casa.getCodigo());
                i.putExtra(ConstantsDB.CODIGO, codigo);
                i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }else {
                Bundle arguments = new Bundle();
                arguments.putSerializable(Constants.CASA, casaCHF);
                Intent i = new Intent(getApplicationContext(),
                        MenuCasaActivity.class);
                i.putExtras(arguments);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
            toast.show();

        }catch (Exception ex){
            ex.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error),Toast.LENGTH_LONG);
            toast.show();
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
