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
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuCasaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaCasaForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaCasaFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaCasa;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class NuevaEncuestaCasaActivity extends FragmentActivity implements
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

    private EncuestaCasaFormLabels labels;
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static CasaCohorteFamilia casaCHF = new CasaCohorteFamilia();
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_enter);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevaEncuestaCasaActivity.this);
        casaCHF = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASA);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new EncuestaCasaForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        labels = new EncuestaCasaFormLabels();

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
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.getTitle().equals(labels.getCuartosDormir())) {
                NumberPage np = (NumberPage) page;
                Page page2 = mWizardModel.findByKey(labels.getCuantoCuartos());
                String valor = page2.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valor != null && !valor.isEmpty()) {
                    np.setmLowerOrEqualsThan(Integer.parseInt(valor));
                    np.setmValRange(true);
                    //mWizardModel.getCurrentPageSequence().remove(page2);
                }
            }
        }
    }

    public void updateModel(Page page){

        try {
            boolean visible = false;
            if (page.getTitle().equals(labels.getLlaveAgua())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Fuera");
                changeStatus(mWizardModel.findByKey(labels.getLlaveCompartida()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlmacenaAgua())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnBarriles()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnPilas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnTanques()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaEnBarriles())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumBarriles()), visible);
                changeStatus(mWizardModel.findByKey(labels.getBarrilesTapados()), visible);
                changeStatus(mWizardModel.findByKey(labels.getBarrilesConAbate()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaEnTanques())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTanques()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTanquesTapados()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTanquesConAbate()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaEnPilas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumPilas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPilasTapadas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPilasConAbate()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaOtrosRecipientes())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientes()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNumOtrosRecipientes()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientesTapados()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientesConAbate()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getMaterialParedes())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialParedes()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getMaterialPiso())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialPiso()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getMaterialTecho())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialTecho()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTieneAbanico())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumAbanicos()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneTelevisor())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTelevisores()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneRefrigerador())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumRefrigeradores()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTienAireAcondicionado())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAireAcondicionadoFuncionando()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCocinaConLenia())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getUbicacionCocinaLenia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPeriodicidadCocinaLenia()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPeriodicidadCocinaLenia())) {
                //visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Diario");
                changeStatus(mWizardModel.findByKey(labels.getNumDiarioCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Diario")));
                changeStatus(mWizardModel.findByKey(labels.getNumSemanalCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Semanal")));
                changeStatus(mWizardModel.findByKey(labels.getNumQuincenalCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Quincenal")));
                changeStatus(mWizardModel.findByKey(labels.getNumMensualCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Mensual")));
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTieneAnimales())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTieneGallinas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTienePatos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneCerdos()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneGallinas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadGallinas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getGallinasDentroCasa()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTienePatos())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadPatos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPatosDentroCasa()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTieneCerdos())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCerdos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCerdosDentroCasa()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getPersonaFumaDentroCasa())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getMadreFuma()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtrosFuman()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPadreFuma()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMadreFuma())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrilosMadre()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPadreFuma())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrillosPadre()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getOtrosFuman())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadOtrosFuman()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrillosOtros()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneOtroMedioTransAuto())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getOtroMedioTransAuto()), visible);
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
            SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.setValue("").setmVisible(visible);
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
            //catálogos
            String scLlaveAgua = datos.getString(this.getString(R.string.llaveAgua));
            String scLlaveCompartida = datos.getString(this.getString(R.string.llaveCompartida));
            String scAlmacenaAgua = datos.getString(this.getString(R.string.almacenaAgua));
            String scEnBarriles = datos.getString(this.getString(R.string.almacenaEnBarriles));
            String scBarrilesTapados = datos.getString(this.getString(R.string.barrilesTapados));
            String scBarrilesAbate = datos.getString(this.getString(R.string.barrilesConAbate));
            String scEnTanques = datos.getString(this.getString(R.string.almacenaEnTanques));
            String scTanquesTapados = datos.getString(this.getString(R.string.tanquesTapados));
            String scTanquesAbate = datos.getString(this.getString(R.string.tanquesConAbate));
            String scEnPilas = datos.getString(this.getString(R.string.almacenaEnPilas));
            String scPilasTapadas = datos.getString(this.getString(R.string.pilasTapadas));
            String scPilasAbate = datos.getString(this.getString(R.string.pilasConAbate));
            String scEnOtrosRec = datos.getString(this.getString(R.string.almacenaOtrosRecipientes));
            String scOtrosRecTapados = datos.getString(this.getString(R.string.otrosRecipientesTapados));
            String scOtrosRecAbate = datos.getString(this.getString(R.string.otrosRecipientesConAbate));
            String mcParedes = datos.getString(this.getString(R.string.materialParedes));
            String mcPiso = datos.getString(this.getString(R.string.materialPiso));
            String scLavandero = datos.getString(this.getString(R.string.ubicacionLavandero));
            String scTecho = datos.getString(this.getString(R.string.materialTecho));
            String scCasaPropia = datos.getString(this.getString(R.string.casaPropia));
            String scTelevisor = datos.getString(this.getString(R.string.tieneTelevisor));
            String scAbanico = datos.getString(this.getString(R.string.tieneAbanico));
            String scRefrigerador = datos.getString(this.getString(R.string.tieneRefrigerador));
            String scAireAcondicionado = datos.getString(this.getString(R.string.tienAireAcondicionado));
            String scAireAcondicionadoFun = datos.getString(this.getString(R.string.aireAcondicionadoFuncionando));
            String scMoto = datos.getString(this.getString(R.string.tieneMoto));
            String scMTCarro = datos.getString(this.getString(R.string.tieneCarro));
            String scMTMicrobus = datos.getString(this.getString(R.string.tienMicrobus));
            String scMTCamioneta = datos.getString(this.getString(R.string.tieneCamioneta));
            String scMTCamion = datos.getString(this.getString(R.string.tieneCamion));
            String scMTOtro = datos.getString(this.getString(R.string.tieneOtroMedioTransAuto));
            String scCocina = datos.getString(this.getString(R.string.cocinaConLenia));
            String scCocinaUbicacion = datos.getString(this.getString(R.string.ubicacionCocinaLenia));
            String scCocinaPeriodicidad = datos.getString(this.getString(R.string.periodicidadCocinaLenia));
            String scAnimales = datos.getString(this.getString(R.string.tieneAnimales));
            String scAnimalesGallinas = datos.getString(this.getString(R.string.tieneGallinas));
            String scGallinasDC = datos.getString(this.getString(R.string.gallinasDentroCasa));
            String scAnimalesPatos = datos.getString(this.getString(R.string.tienePatos));
            String scPatosDC = datos.getString(this.getString(R.string.patosDentroCasa));
            String scAnimalesCerdos = datos.getString(this.getString(R.string.tieneCerdos));
            String scCerdosDC = datos.getString(this.getString(R.string.cerdosDentroCasa));
            String scFuman = datos.getString(this.getString(R.string.personaFumaDentroCasa));
            String scMadreFuma = datos.getString(this.getString(R.string.madreFuma));
            String scPafreFuma = datos.getString(this.getString(R.string.padreFuma));
            String scOtrosFuma = datos.getString(this.getString(R.string.otrosFuman));

            //textos
            String tpDescOtrosRec = datos.getString(this.getString(R.string.otrosRecipientes));
            String tpParedesOtroDesc = datos.getString(this.getString(R.string.otroMaterialParedes));
            String tpPisoOtroDesc = datos.getString(this.getString(R.string.otroMaterialPiso));
            String tpTechoOtroDesc = datos.getString(this.getString(R.string.otroMaterialTecho));
            String tpMTOtroDesc = datos.getString(this.getString(R.string.otroMedioTransAuto));

            //numéricos
            String npCuartos = datos.getString(this.getString(R.string.cuantoCuartos));
            String npCuartosDormir = datos.getString(this.getString(R.string.cuartosDormir));
            String npHorasSinAgua = datos.getString(this.getString(R.string.horasSinAgua));
            String npNumBarriles = datos.getString(this.getString(R.string.numBarriles));
            String npNumTanques = datos.getString(this.getString(R.string.numTanques));
            String npNumPilas = datos.getString(this.getString(R.string.numPilas));
            String npNumOtrosRec = datos.getString(this.getString(R.string.numOtrosRecipientes));
            String npNumTelevisor = datos.getString(this.getString(R.string.numTelevisores));
            String npAbanico = datos.getString(this.getString(R.string.numAbanicos));
            String npNumRefrigerador = datos.getString(this.getString(R.string.numRefrigeradores));
            String npNumCocinaD = datos.getString(this.getString(R.string.numDiarioCocinaLenia));
            String npNumCocinaS = datos.getString(this.getString(R.string.numSemanalCocinaLenia));
            String npNumCocinaQ = datos.getString(this.getString(R.string.numQuincenalCocinaLenia));
            String npNumCocinaM = datos.getString(this.getString(R.string.numMensualCocinaLenia));
            String npCantGallinas = datos.getString(this.getString(R.string.cantidadGallinas));
            String npCantPatos = datos.getString(this.getString(R.string.cantidadPatos));
            String npCantCerdos = datos.getString(this.getString(R.string.cantidadCerdos));
            String npCantCigarMadre = datos.getString(this.getString(R.string.cantidadCigarrilosMadre));
            String npCantCigarPadre = datos.getString(this.getString(R.string.cantidadCigarrillosPadre));
            String npCantOtrosFuma = datos.getString(this.getString(R.string.cantidadOtrosFuman));
            String npCantCigarOtrosF = datos.getString(this.getString(R.string.cantidadCigarrillosOtros));

            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();


            EncuestaCasa encuestaCasa = new EncuestaCasa();
            encuestaCasa.setCasa(casaCHF);
            if (tieneValor(scLlaveAgua)) {
                MessageResource msLlaveAgua = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scLlaveAgua + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_DF'", null);
                if (msLlaveAgua != null) encuestaCasa.setUbicacionLlaveAgua(msLlaveAgua.getCatKey());
            }
            if (tieneValor(scLlaveCompartida)) {
                MessageResource msLLaveCompartida = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scLlaveCompartida + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_COMPARTIDO'", null);
                if (msLLaveCompartida != null) encuestaCasa.setLlaveCompartida(msLLaveCompartida.getCatKey());
            }
            if (tieneValor(scAlmacenaAgua)) {
                MessageResource msAlmacenaAgua = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAlmacenaAgua + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAlmacenaAgua != null) encuestaCasa.setAlmacenaAgua(msAlmacenaAgua.getCatKey().charAt(0));
            }
            if (tieneValor(scEnBarriles)) {
                MessageResource msEnBarriles = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scEnBarriles + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msEnBarriles != null) encuestaCasa.setAlmacenaEnBarriles(msEnBarriles.getCatKey().charAt(0));
            }
            if (tieneValor(scBarrilesTapados)) {
                MessageResource msBarrilesTapados = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scBarrilesTapados + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msBarrilesTapados != null) encuestaCasa.setBarrilesTapados(msBarrilesTapados.getCatKey().charAt(0));
            }
            if (tieneValor(scBarrilesAbate)) {
                MessageResource msBarrilesAbate = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scBarrilesAbate + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msBarrilesAbate != null) encuestaCasa.setBarrilesConAbate(msBarrilesAbate.getCatKey().charAt(0));
            }
            if (tieneValor(scEnTanques)) {
                MessageResource msEnTanques = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scEnTanques + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msEnTanques != null) encuestaCasa.setAlmacenaEnTanques(msEnTanques.getCatKey().charAt(0));
            }
            if (tieneValor(scTanquesTapados)) {
                MessageResource msTanquesTapados = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scTanquesTapados + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msTanquesTapados != null) encuestaCasa.setTanquesTapados(msTanquesTapados.getCatKey().charAt(0));
            }
            if (tieneValor(scTanquesAbate)) {
                MessageResource msTanquesAbate = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scTanquesAbate + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msTanquesAbate != null) encuestaCasa.setTanquesConAbate(msTanquesAbate.getCatKey().charAt(0));
            }
            if (tieneValor(scEnPilas)) {
                MessageResource msEnPilas = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scEnPilas + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msEnPilas != null) encuestaCasa.setAlmacenaEnPilas(msEnPilas.getCatKey().charAt(0));
            }
            if (tieneValor(scPilasTapadas)) {
                MessageResource msPilasTapadas = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scPilasTapadas + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msPilasTapadas != null) encuestaCasa.setPilasTapadas(msPilasTapadas.getCatKey().charAt(0));
            }
            if (tieneValor(scPilasAbate)) {
                MessageResource msPilasAbate = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scPilasAbate + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msPilasAbate != null) encuestaCasa.setPilasConAbate(msPilasAbate.getCatKey().charAt(0));
            }
            if (tieneValor(scEnOtrosRec)) {
                MessageResource msEnOtrosRec = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scEnOtrosRec + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msEnOtrosRec != null) encuestaCasa.setAlmacenaOtrosRecipientes(msEnOtrosRec.getCatKey().charAt(0));
            }
            if (tieneValor(scOtrosRecTapados)) {
                MessageResource msOtrosRecTapados = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scOtrosRecTapados + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msOtrosRecTapados != null)
                    encuestaCasa.setOtrosRecipientesTapados(msOtrosRecTapados.getCatKey().charAt(0));
            }
            if (tieneValor(scOtrosRecAbate)) {
                MessageResource msOtrosRecAbate = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scOtrosRecAbate + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msOtrosRecAbate != null)
                    encuestaCasa.setOtrosRecipientesConAbate(msOtrosRecAbate.getCatKey().charAt(0));
            }
            if (tieneValor(mcParedes)) {
                String keysMaterial = "";
                mcParedes = mcParedes.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msParedes = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + mcParedes + "') and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_MAT_PARED'", null);
                for(MessageResource ms : msParedes) {
                    keysMaterial += ms.getCatKey() + ",";
                }
                if (!keysMaterial.isEmpty())
                    keysMaterial = keysMaterial.substring(0, keysMaterial.length() - 1);
                encuestaCasa.setMaterialParedes(keysMaterial);
            }
            if (tieneValor(mcPiso)) {
                String keysMaterial = "";
                mcPiso = mcPiso.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msPiso = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + mcPiso + "') and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_MAT_PISO'", null);
                for(MessageResource ms : msPiso) {
                    keysMaterial += ms.getCatKey() + ",";
                }
                if (!keysMaterial.isEmpty())
                    keysMaterial = keysMaterial.substring(0, keysMaterial.length() - 1);
                encuestaCasa.setMaterialPiso(keysMaterial);
            }
            if (tieneValor(scTecho)) {
                MessageResource msTecho = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scTecho + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_MAT_TECHO'", null);
                if (msTecho != null) encuestaCasa.setMaterialTecho(msTecho.getCatKey());
            }
            if (tieneValor(scLavandero)) {
                MessageResource msLavandero = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scLavandero + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_DF'", null);
                if (msLavandero != null) encuestaCasa.setUbicacionLavandero(msLavandero.getCatKey());
            }
            if (tieneValor(scCasaPropia)) {
                MessageResource msCasaPropia = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scCasaPropia + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msCasaPropia != null) encuestaCasa.setCasaPropia(msCasaPropia.getCatKey().charAt(0));
            }
            if (tieneValor(scTelevisor)) {
                MessageResource msTelevisor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scTelevisor + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msTelevisor != null) encuestaCasa.setTieneTelevisor(msTelevisor.getCatKey().charAt(0));
            }
            if (tieneValor(scAbanico)) {
                MessageResource msAbanico = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAbanico + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAbanico != null) encuestaCasa.setTieneAbanico(msAbanico.getCatKey().charAt(0));
            }
            if (tieneValor(scRefrigerador)) {
                MessageResource msRefrigerador = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scRefrigerador + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msRefrigerador != null) encuestaCasa.setTieneRefrigerador(msRefrigerador.getCatKey().charAt(0));
            }
            if (tieneValor(scAireAcondicionado)) {
                MessageResource msAireAcondicionado = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAireAcondicionado + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAireAcondicionado != null)
                    encuestaCasa.setTienAireAcondicionado(msAireAcondicionado.getCatKey().charAt(0));
            }
            if (tieneValor(scAireAcondicionadoFun)) {
                MessageResource msAireAcondicionadoFun = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAireAcondicionadoFun + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_FUN_ABANICO'", null);
                if (msAireAcondicionadoFun != null)
                    encuestaCasa.setAireAcondicionadoFuncionando(msAireAcondicionadoFun.getCatKey());

            }
            if (tieneValor(scMoto)) {
                MessageResource msMTMoto = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMoto + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTMoto != null) encuestaCasa.setTieneMoto(msMTMoto.getCatKey().charAt(0));
            }
            if (tieneValor(scMTCarro)) {
                MessageResource msMTCarro = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMTCarro + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTCarro != null) encuestaCasa.setTieneCarro(msMTCarro.getCatKey().charAt(0));
            }
            if (tieneValor(scMTMicrobus)) {
                MessageResource msMTMicrobus = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMTMicrobus + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTMicrobus != null) encuestaCasa.setTienMicrobus(msMTMicrobus.getCatKey().charAt(0));
            }
            if (tieneValor(scMTCamioneta)) {
                MessageResource msMTCamioneta = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMTCamioneta + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTCamioneta != null) encuestaCasa.setTieneCamioneta(msMTCamioneta.getCatKey().charAt(0));
            }
            if (tieneValor(scMTCamion)) {
                MessageResource msMTCamion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMTCamion + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTCamion != null) encuestaCasa.setTieneCamion(msMTCamion.getCatKey().charAt(0));
            }
            if (tieneValor(scMTOtro)) {
                MessageResource msMTOtro = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMTOtro + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTOtro != null) encuestaCasa.setTieneOtroMedioTransAuto(msMTOtro.getCatKey().charAt(0));
            }
            if (tieneValor(scCocina)) {
                MessageResource msCocina = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scCocina + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msCocina != null) encuestaCasa.setCocinaConLenia(msCocina.getCatKey().charAt(0));
            }
            if (tieneValor(scCocinaUbicacion)) {
                MessageResource msCocinaUbicacion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scCocinaUbicacion + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_DF'", null);
                if (msCocinaUbicacion != null) encuestaCasa.setUbicacionCocinaLenia(msCocinaUbicacion.getCatKey());
            }
            if (tieneValor(scCocinaPeriodicidad)) {
                MessageResource msCocinaPeriodicidad = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scCocinaPeriodicidad + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_FREC_COCINA'", null);
                if (msCocinaPeriodicidad != null)
                    encuestaCasa.setPeriodicidadCocinaLenia(msCocinaPeriodicidad.getCatKey());
            }
            if (tieneValor(scAnimales)) {
                MessageResource msAnimales = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAnimales + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAnimales != null) encuestaCasa.setTieneAnimales(msAnimales.getCatKey().charAt(0));
            }
            if (tieneValor(scAnimalesGallinas)) {
                MessageResource msAnimalesGallinas = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAnimalesGallinas + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAnimalesGallinas != null) encuestaCasa.setTieneGallinas(msAnimalesGallinas.getCatKey().charAt(0));
            }
            if (tieneValor(scGallinasDC)) {
                MessageResource msGallinasDC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scGallinasDC + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msGallinasDC != null) encuestaCasa.setGallinasDentroCasa(msGallinasDC.getCatKey().charAt(0));
            }
            if (tieneValor(scAnimalesPatos)) {
                MessageResource msAnimalesPatos = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAnimalesPatos + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAnimalesPatos != null) encuestaCasa.setTienePatos(msAnimalesPatos.getCatKey().charAt(0));
            }
            if (tieneValor(scPatosDC)) {
                MessageResource msPatosDC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scPatosDC + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msPatosDC != null) encuestaCasa.setPatosDentroCasa(msPatosDC.getCatKey().charAt(0));
            }
            if (tieneValor(scAnimalesCerdos)) {
                MessageResource msAnimalesCerdos = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAnimalesCerdos + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAnimalesCerdos != null) encuestaCasa.setTieneCerdos(msAnimalesCerdos.getCatKey().charAt(0));
            }
            if (tieneValor(scCerdosDC)) {
                MessageResource msCerdosDC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scCerdosDC + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msCerdosDC != null) encuestaCasa.setCerdosDentroCasa(msCerdosDC.getCatKey().charAt(0));
            }
            if (tieneValor(scFuman)) {
                MessageResource msFuman = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scFuman + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msFuman != null) encuestaCasa.setPersonaFumaDentroCasa(msFuman.getCatKey().charAt(0));
            }
            if (tieneValor(scMadreFuma)) {
                MessageResource msMadreFuma = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMadreFuma + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMadreFuma != null) encuestaCasa.setMadreFuma(msMadreFuma.getCatKey().charAt(0));
            }
            if (tieneValor(scPafreFuma)) {
                MessageResource msPafreFuma = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scPafreFuma + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msPafreFuma != null) encuestaCasa.setPadreFuma(msPafreFuma.getCatKey().charAt(0));
            }
            if (tieneValor(scOtrosFuma)) {
                MessageResource msOtrosFuma = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scOtrosFuma + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msOtrosFuma != null) encuestaCasa.setOtrosFuman(msOtrosFuma.getCatKey().charAt(0));
            }

            encuestaCasa.setOtrosRecipientes(tpDescOtrosRec);
            encuestaCasa.setOtroMaterialParedes(tpParedesOtroDesc);
            encuestaCasa.setOtroMaterialPiso(tpPisoOtroDesc);
            encuestaCasa.setOtroMaterialTecho(tpTechoOtroDesc);
            encuestaCasa.setOtroMedioTransAuto(tpMTOtroDesc);

            if (tieneValor(npCuartos)) encuestaCasa.setCantidadCuartos(Integer.valueOf(npCuartos));
            if (tieneValor(npCuartosDormir)) encuestaCasa.setCantidadCuartosDormir(Integer.valueOf(npCuartosDormir));
            if (tieneValor(npHorasSinAgua)) encuestaCasa.setHrsSinServicioAgua(Integer.valueOf(npHorasSinAgua));
            if (tieneValor(npNumBarriles)) encuestaCasa.setNumBarriles(Integer.valueOf(npNumBarriles));
            if (tieneValor(npNumTanques)) encuestaCasa.setNumTanques(Integer.valueOf(npNumTanques));
            if (tieneValor(npNumPilas)) encuestaCasa.setNumPilas(Integer.valueOf(npNumPilas));
            if (tieneValor(npNumOtrosRec)) encuestaCasa.setNumOtrosRecipientes(Integer.valueOf(npNumOtrosRec));
            if (tieneValor(npNumTelevisor)) encuestaCasa.setNumTelevisores(Integer.valueOf(npNumTelevisor));
            if (tieneValor(npAbanico)) encuestaCasa.setNumAbanicos(Integer.valueOf(npAbanico));
            if (tieneValor(npNumRefrigerador)) encuestaCasa.setNumRefrigeradores(Integer.valueOf(npNumRefrigerador));
            if (tieneValor(npNumCocinaD)) encuestaCasa.setNumDiarioCocinaLenia(Integer.valueOf(npNumCocinaD));
            if (tieneValor(npNumCocinaS)) encuestaCasa.setNumSemanalCocinaLenia(Integer.valueOf(npNumCocinaS));
            if (tieneValor(npNumCocinaQ)) encuestaCasa.setNumQuincenalCocinaLenia(Integer.valueOf(npNumCocinaQ));
            if (tieneValor(npNumCocinaM)) encuestaCasa.setNumMensualCocinaLenia(Integer.valueOf(npNumCocinaM));
            if (tieneValor(npCantGallinas)) encuestaCasa.setCantidadGallinas(Integer.valueOf(npCantGallinas));
            if (tieneValor(npCantPatos)) encuestaCasa.setCantidadPatos(Integer.valueOf(npCantPatos));
            if (tieneValor(npCantCerdos)) encuestaCasa.setCantidadCerdos(Integer.valueOf(npCantCerdos));
            if (tieneValor(npCantCigarMadre))
                encuestaCasa.setCantidadCigarrilosMadre(Integer.valueOf(npCantCigarMadre));
            if (tieneValor(npCantCigarPadre))
                encuestaCasa.setCantidadCigarrillosPadre(Integer.valueOf(npCantCigarPadre));
            if (tieneValor(npCantOtrosFuma)) encuestaCasa.setCantidadOtrosFuman(Integer.valueOf(npCantOtrosFuma));
            if (tieneValor(npCantCigarOtrosF))
                encuestaCasa.setCantidadCigarrillosOtros(Integer.valueOf(npCantCigarOtrosF));

            encuestaCasa.setFechaEncuestas(new Date());
            //Metadata
            encuestaCasa.setRecordDate(new Date());
            encuestaCasa.setRecordUser(username);
            encuestaCasa.setDeviceid(infoMovil.getDeviceId());
            encuestaCasa.setEstado('0');
            encuestaCasa.setPasive('0');
            boolean actualizada = false;
            EncuestaCasa encuestaExiste = estudiosAdapter.getEncuestaCasa(EncuestasDBConstants.casa_chf + "=" + casaCHF.getCodigoCHF(), EncuestasDBConstants.casa_chf);
            if (encuestaExiste != null && encuestaExiste.getCasa() != null && encuestaExiste.getCasa().getCodigoCHF() != null)
                actualizada = estudiosAdapter.editarEncuestaCasa(encuestaCasa);
            else estudiosAdapter.crearEncuestaCasa(encuestaCasa);
            estudiosAdapter.close();
            Bundle arguments = new Bundle();
            arguments.putSerializable(Constants.CASA, casaCHF);
            Intent i = new Intent(getApplicationContext(),
                    MenuCasaActivity.class);
            i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
            toast.show();
            finish();
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
