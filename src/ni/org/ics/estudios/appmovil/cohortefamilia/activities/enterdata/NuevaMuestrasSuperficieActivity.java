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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaMuestrasSuperficieCasoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.MuestrasSuperficieForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.MuestrasSuperficieFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.MuestraSuperficie;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCaso;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class NuevaMuestrasSuperficieActivity extends FragmentActivity implements
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

    private MuestrasSuperficieFormLabels labels;
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static ParticipanteCohorteFamilia participanteCHF = new ParticipanteCohorteFamilia();
    private static VisitaSeguimientoCaso visitaCaso = new VisitaSeguimientoCaso();
    //private static VisitaFinalCaso visitaFinalCaso = new VisitaFinalCaso();
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private int muestrasPermitidas; //1: solo superficie, 2: solo manos, 3: todas

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_enter);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevaMuestrasSuperficieActivity.this);
        if (getIntent().getExtras().getSerializable(Constants.VISITA) instanceof VisitaSeguimientoCaso) {
            visitaCaso = (VisitaSeguimientoCaso) getIntent().getExtras().getSerializable(Constants.VISITA);
            participanteCHF = visitaCaso.getCodigoParticipanteCaso().getParticipante();
        }
        muestrasPermitidas = getIntent().getIntExtra(Constants.MX_SUPERFICIE, 1);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new MuestrasSuperficieForm(this, mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        labels = new MuestrasSuperficieFormLabels();

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

        SingleFixedChoicePage mx1 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getManecillaPuerta());
        SingleFixedChoicePage mx2 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getLlaveBanio());
        SingleFixedChoicePage mx3 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getManijaRefrigerador());
        SingleFixedChoicePage mx4 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getInterruptorLuz());
        SingleFixedChoicePage mx5 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getJugueteNino());
        SingleFixedChoicePage mx6 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTelefonoCelular());
        SingleFixedChoicePage mx7 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getMesaComedor());
        SingleFixedChoicePage mx8 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getGrifoPrincipal());
        SingleFixedChoicePage mx9 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEncimaRefrigerador());
        SingleFixedChoicePage mx10 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getMuebleCercaCama());
        SingleFixedChoicePage mx11 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getParedDetrasCama());
        SingleFixedChoicePage mx12 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getParedBanio());
        SingleFixedChoicePage mx13 = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getManos());

        mx1.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx2.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx3.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx4.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx5.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx6.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx7.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx8.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx9.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx10.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx11.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx12.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        mx13.setmVisible(muestrasPermitidas == 2 || muestrasPermitidas == 3);

        BarcodePage pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx1());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx2());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx3());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx4());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx5());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx6());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx7());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx8());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx9());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx10());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx11());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx12());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 1 || muestrasPermitidas == 3);
        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoMx13());
        pagetmp.setmFullScan(false);
        pagetmp.setmVisible(muestrasPermitidas == 2 || muestrasPermitidas == 3);
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
                        Intent i;
                        i = new Intent(getApplicationContext(),
                                ListaMuestrasSuperficieCasoActivity.class);
                        Bundle arguments = new Bundle();
                        if (visitaCaso != null) {
                            arguments.putSerializable(Constants.VISITA, visitaCaso);

                        }
                        i.putExtras(arguments);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
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
                BarcodePage tp = (BarcodePage) page;
                if (tp.ismValPattern()) {
                    String valor = tp.getData().getString(BarcodePage.SIMPLE_DATA_KEY);
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
        try {
            boolean visible = false;
            if (page.getTitle().equals(labels.getManecillaPuerta())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsManecillaPuerta()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx1()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getLlaveBanio())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsLlaveBanio()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx2()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getManijaRefrigerador())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsManijaRefrigerador()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx3()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getInterruptorLuz())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsInterruptorLuz()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx4()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getJugueteNino())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsJugueteNino()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx5()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTelefonoCelular())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsTelefonoCelular()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx6()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMesaComedor())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsMesaComedor()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx7()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getGrifoPrincipal())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsGrifoPrincipal()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx8()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEncimaRefrigerador())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsEncimaRefrigerador()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx9()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMuebleCercaCama())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsMuebleCercaCama()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx10()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getParedDetrasCama())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsParedDetrasCama()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx11()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getParedBanio())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getOsParedBanio()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCodigoMx12()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void changeStatus(Page page, boolean visible){
        String clase = page.getClass().toString();
        if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage")){
            SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.BarcodePage")){
            BarcodePage modifPage = (BarcodePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.LabelPage")){
            LabelPage modifPage = (LabelPage) page; modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.TextPage")){
            TextPage modifPage = (TextPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NumberPage")){
            NumberPage modifPage = (NumberPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage")){
            MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.DatePage")){
            DatePage modifPage = (DatePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
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

    /**
     * Convierte una Date a String, según el formato indicado
     * @param dtFecha Fecha a convertir
     * @param format formato solicitado
     * @return String
     */
    public static String DateToString(Date dtFecha, String format)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        if(dtFecha!=null)
            return simpleDateFormat.format(dtFecha);
        else
            return null;
    }

    public void saveData() {
        try {
            Map<String, String> mapa = mWizardModel.getAnswers();
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }

            String codigoMx1 = datos.getString(this.getString(R.string.codigoMx1));
            String codigoMx2 = datos.getString(this.getString(R.string.codigoMx2));
            String codigoMx3 = datos.getString(this.getString(R.string.codigoMx3));
            String codigoMx4 = datos.getString(this.getString(R.string.codigoMx4));
            String codigoMx5 = datos.getString(this.getString(R.string.codigoMx5));
            String codigoMx6 = datos.getString(this.getString(R.string.codigoMx6));
            String codigoMx7 = datos.getString(this.getString(R.string.codigoMx7));
            String codigoMx8 = datos.getString(this.getString(R.string.codigoMx8));
            String codigoMx9 = datos.getString(this.getString(R.string.codigoMx9));
            String codigoMx10 = datos.getString(this.getString(R.string.codigoMx10));
            String codigoMx11 = datos.getString(this.getString(R.string.codigoMx11));
            String codigoMx12 = datos.getString(this.getString(R.string.codigoMx12));
            String codigoMx13 = datos.getString(this.getString(R.string.codigoMx13));
            String manecillaPuerta = datos.getString(this.getString(R.string.manecillaPuerta));
            String llaveBanio = datos.getString(this.getString(R.string.llaveBanio));
            String manijaRefrigerador = datos.getString(this.getString(R.string.manijaRefrigerador));
            String interruptorLuz = datos.getString(this.getString(R.string.interruptorLuz));
            String jugueteNino = datos.getString(this.getString(R.string.jugueteNino));
            String telefonoCelular = datos.getString(this.getString(R.string.telefonoCelular));
            String mesaComedor = datos.getString(this.getString(R.string.mesaComedor));
            String grifoPrincipal = datos.getString(this.getString(R.string.grifoPrincipal));
            String encimaRefrigerador = datos.getString(this.getString(R.string.encimaRefrigerador));
            String muebleCercaCama = datos.getString(this.getString(R.string.muebleCercaCama));
            String paredDetrasCama = datos.getString(this.getString(R.string.paredDetrasCama));
            String paredBanio = datos.getString(this.getString(R.string.paredBanio));
            String manos = datos.getString(this.getString(R.string.manos));
            String osManecillaPuerta = datos.getString(this.getString(R.string.osManecillaPuerta));
            String osLlaveBanio = datos.getString(this.getString(R.string.osLlaveBanio));
            String osManijaRefrigerador = datos.getString(this.getString(R.string.osManijaRefrigerador));
            String osInterruptorLuz = datos.getString(this.getString(R.string.osInterruptorLuz));
            String osJugueteNino = datos.getString(this.getString(R.string.osJugueteNino));
            String osTelefonoCelular = datos.getString(this.getString(R.string.osTelefonoCelular));
            String osMesaComedor = datos.getString(this.getString(R.string.osMesaComedor));
            String osGrifoPrincipal = datos.getString(this.getString(R.string.osGrifoPrincipal));
            String osEncimaRefrigerador = datos.getString(this.getString(R.string.osEncimaRefrigerador));
            String osMuebleCercaCama = datos.getString(this.getString(R.string.osMuebleCercaCama));
            String osParedDetrasCama = datos.getString(this.getString(R.string.osParedDetrasCama));
            String osParedBanio = datos.getString(this.getString(R.string.osParedBanio));

            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            //listas
            MuestraSuperficie muestra = new MuestraSuperficie();
            muestra.setCasaChf(participanteCHF.getCasaCHF());
            //Metadata
            if (visitaCaso != null)
                muestra.setRecordDate(visitaCaso.getFechaVisita());
            muestra.setRecordUser(username);
            muestra.setDeviceid(infoMovil.getDeviceId());
            muestra.setEstado('0');
            muestra.setPasive('0');
            muestra.setVisita(visitaCaso.getVisita());
            muestra.setCaso(visitaCaso.getCodigoParticipanteCaso().getCodigoCaso().getCodigoCaso());

            if (tieneValor(manecillaPuerta)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("01");
                muestra.setOtraSuperficie(osManecillaPuerta);
                muestra.setCodigoMx(codigoMx1);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(llaveBanio)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("02");
                muestra.setOtraSuperficie(osLlaveBanio);
                muestra.setCodigoMx(codigoMx2);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(manijaRefrigerador)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("03");
                muestra.setOtraSuperficie(osManijaRefrigerador);
                muestra.setCodigoMx(codigoMx3);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(interruptorLuz)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("04");
                muestra.setOtraSuperficie(osInterruptorLuz);
                muestra.setCodigoMx(codigoMx4);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(jugueteNino)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("05");
                muestra.setOtraSuperficie(osJugueteNino);
                muestra.setCodigoMx(codigoMx5);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(telefonoCelular)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("06");
                muestra.setOtraSuperficie(osTelefonoCelular);
                muestra.setCodigoMx(codigoMx6);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(mesaComedor)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("07");
                muestra.setOtraSuperficie(osMesaComedor);
                muestra.setCodigoMx(codigoMx7);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(grifoPrincipal)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("08");
                muestra.setOtraSuperficie(osGrifoPrincipal);
                muestra.setCodigoMx(codigoMx8);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(encimaRefrigerador)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("09");
                muestra.setOtraSuperficie(osEncimaRefrigerador);
                muestra.setCodigoMx(codigoMx9);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(muebleCercaCama)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("10");
                muestra.setOtraSuperficie(osMuebleCercaCama);
                muestra.setCodigoMx(codigoMx10);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(paredDetrasCama)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("11");
                muestra.setOtraSuperficie(osParedDetrasCama);
                muestra.setCodigoMx(codigoMx11);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(paredBanio)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("12");
                muestra.setOtraSuperficie(osParedBanio);
                muestra.setCodigoMx(codigoMx12);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            if (tieneValor(manos)) {
                muestra.setCodigo(infoMovil.getId());
                muestra.setTipoMuestra("13");
                muestra.setCodigoMx(codigoMx13);
                muestra.setOtraSuperficie(null);
                muestra.setParticipanteChf(participanteCHF);
                muestra.setCasaChf(null);
                estudiosAdapter.crearMuestraSuperficie(muestra);
            }
            estudiosAdapter.close();
            Intent i;
            i = new Intent(getApplicationContext(),
                    ListaMuestrasSuperficieCasoActivity.class);
            Bundle arguments = new Bundle();
            if (visitaCaso != null) {
                arguments.putSerializable(Constants.VISITA, visitaCaso);

            }
            i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
            toast.show();
            finish();
        } catch (Exception ex) {
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
