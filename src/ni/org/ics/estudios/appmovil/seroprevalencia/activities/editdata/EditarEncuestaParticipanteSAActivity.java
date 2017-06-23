package ni.org.ics.estudios.appmovil.seroprevalencia.activities.editdata;

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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuParticipanteActivity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaParticipanteSA;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.seroprevalencia.forms.EncuestaParticipanteSAForm;
import ni.org.ics.estudios.appmovil.seroprevalencia.forms.EncuestaParticipanteSAFormLabels;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.util.*;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class EditarEncuestaParticipanteSAActivity extends FragmentActivity implements
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

    private EncuestaParticipanteSAFormLabels labels = new EncuestaParticipanteSAFormLabels();
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static ParticipanteCohorteFamilia participanteCHF = new ParticipanteCohorteFamilia();
    private static EncuestaParticipanteSA encuesta = new EncuestaParticipanteSA();
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    public static final String SIMPLE_DATA_KEY = "_";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_enter);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(EditarEncuestaParticipanteSAActivity.this);
        participanteCHF = (ParticipanteCohorteFamilia) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        encuesta = (EncuestaParticipanteSA) getIntent().getExtras().getSerializable(Constants.ENCUESTA);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new EncuestaParticipanteSAForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
        estudiosAdapter.open();

        if (encuesta!=null){
            Bundle dato = null;
            Page modifPage;
            if (encuesta.getEscuchadoZikaSn() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEscuchadoZikaSn());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getEscuchadoZikaSn()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuesta.getQueEsSika()!= null){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQueEsSika());
                String codigos = encuesta.getQueEsSika().replaceAll("," , "','");
                List<String> descripciones = new ArrayList<String>();
                List<MessageResource> msOtraAlim = estudiosAdapter.getMessageResources(CatalogosDBConstants.catKey + " in ('" + codigos + "') and " + CatalogosDBConstants.catRoot + "='SA_CAT_DEF_ZIKA'", null);
                for(MessageResource ms : msOtraAlim){
                    descripciones.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descripciones);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getOtroQueEsSika()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getOtroQueEsSika());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getOtroQueEsSika());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuesta.getTransmiteZika()!= null){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getTransmiteZika());
                String codigos = encuesta.getTransmiteZika().replaceAll("," , "','");
                List<String> descripciones = new ArrayList<String>();
                List<MessageResource> msOtraAlim = estudiosAdapter.getMessageResources(CatalogosDBConstants.catKey + " in ('" + codigos + "') and " + CatalogosDBConstants.catRoot + "='SA_CAT_TRA_ZIKA'", null);
                for(MessageResource ms : msOtraAlim){
                    descripciones.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descripciones);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getOtroTransmiteZika()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getOtroTransmiteZika());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getOtroTransmiteZika());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuesta.getSintomas()!= null){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getSintomas());
                String codigos = encuesta.getSintomas().replaceAll("," , "','");
                List<String> descripciones = new ArrayList<String>();
                List<MessageResource> msOtraAlim = estudiosAdapter.getMessageResources(CatalogosDBConstants.catKey + " in ('" + codigos + "') and " + CatalogosDBConstants.catRoot + "='SA_CAT_SINTOMAS'", null);
                for(MessageResource ms : msOtraAlim){
                    descripciones.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descripciones);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getTenidoZikaSn() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTenidoZikaSn());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getTenidoZikaSn()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getFechaZika()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getFechaZika());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getFechaZika());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuesta.getSintomasZika()!= null){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getSintomasZika());
                String codigos = encuesta.getSintomasZika().replaceAll("," , "','");
                List<String> descripciones = new ArrayList<String>();
                List<MessageResource> msOtraAlim = estudiosAdapter.getMessageResources(CatalogosDBConstants.catKey + " in ('" + codigos + "') and " + CatalogosDBConstants.catRoot + "='SA_CAT_SINTOMAS'", null);
                for(MessageResource ms : msOtraAlim){
                    descripciones.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descripciones);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getZikaConfirmadoMedico() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getZikaConfirmadoMedico());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getZikaConfirmadoMedico()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getTenidoDengueSn() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTenidoDengueSn());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getTenidoDengueSn()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getFechaDengue()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getFechaDengue());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getFechaDengue());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getDengueConfirmadoMedico() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getDengueConfirmadoMedico());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getDengueConfirmadoMedico()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getTenidoChikSn() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTenidoChikSn());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getTenidoChikSn()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getFechaChik()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getFechaChik());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getFechaChik());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getChikConfirmadoMedico() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getChikConfirmadoMedico());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getChikConfirmadoMedico()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getVacunaFiebreAmarillaSn() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getVacunaFiebreAmarillaSn());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getVacunaFiebreAmarillaSn()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getFechaVacunaFiebreAmar()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getFechaVacunaFiebreAmar());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getFechaVacunaFiebreAmar());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getTransfusionSangreSn() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTransfusionSangreSn());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getTransfusionSangreSn()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getFechaTransfusionSangre()!=null){
                modifPage = (NewDatePage) mWizardModel.findByKey(labels.getFechaTransfusionSangre());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getFechaTransfusionSangre());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getUsaRepelentes() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getUsaRepelentes());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getUsaRepelentes()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getConoceLarvas() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getConoceLarvas());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getConoceLarvas()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuesta.getLugaresLarvas()!= null){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getLugaresLarvas());
                String codigos = encuesta.getLugaresLarvas().replaceAll("," , "','");
                List<String> descripciones = new ArrayList<String>();
                List<MessageResource> msOtraAlim = estudiosAdapter.getMessageResources(CatalogosDBConstants.catKey + " in ('" + codigos + "') and " + CatalogosDBConstants.catRoot + "='SA_CAT_LUG_LARVA'", null);
                for(MessageResource ms : msOtraAlim){
                    descripciones.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descripciones);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getOtrosLugaresLarvas()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getOtrosLugaresLarvas());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getOtrosLugaresLarvas());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getTenidoHijos() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTenidoHijos());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getTenidoHijos()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getUsaPlanificacionFam() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getUsaPlanificacionFam());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getUsaPlanificacionFam()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getUsaCondon() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getUsaCondon());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getUsaCondon()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getUsaOtroMetodo() != null) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getUsaOtroMetodo());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getUsaOtroMetodo()
                        + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }

        }
        estudiosAdapter.close();

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

        if (encuesta.getParticipanteSA().getParticipante()!=null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(encuesta.getParticipanteSA().getParticipante().getFechaNac());
            int anioNac = calendar.get(Calendar.YEAR);
            calendar.setTime(new Date());
            int anioActual = calendar.get(Calendar.YEAR);
            NumberPage p1 = (NumberPage)mWizardModel.findByKey(labels.getFechaVacunaFiebreAmar());
            p1.setRangeValidation(true, anioNac, anioActual);
        }

        onPageTreeChangedInitial();
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

    public void onPageTreeChangedInitial() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        if (recalculateCutOffPage()) {
            updateBottomBar();
        }
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
        try {
            boolean visible = false;
            if (page.getTitle().equals(labels.getEscuchadoZikaSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getQueEsSika()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTransmiteZika()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getQueEsSika())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains("Otra");
                changeStatus(mWizardModel.findByKey(labels.getOtroQueEsSika()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTransmiteZika())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains("Otra");
                changeStatus(mWizardModel.findByKey(labels.getOtroTransmiteZika()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTenidoZikaSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFechaZika()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getSintomasZika()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getZikaConfirmadoMedico()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTenidoDengueSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFechaDengue()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDengueConfirmadoMedico()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTenidoChikSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFechaChik()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getChikConfirmadoMedico()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getVacunaFiebreAmarillaSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFechaVacunaFiebreAmar()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTransfusionSangreSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFechaTransfusionSangre()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getConoceLarvas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getLugaresLarvas()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getLugaresLarvas())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains("Otros lugares");
                changeStatus(mWizardModel.findByKey(labels.getOtrosLugaresLarvas()), visible);
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
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.DatePage")){
            DatePage modifPage = (DatePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NewDatePage")){
            NewDatePage modifPage = (NewDatePage) page;
            modifPage.resetData(new Bundle());
            modifPage.setmVisible(visible);
        }
    }

    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
    }

    public void saveData() {
        try {
            Map<String, String> mapa = mWizardModel.getAnswers();
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }

            String escuchadoZikaSn = datos.getString(this.getString(R.string.escuchadoZikaSn));
            String queEsSika = datos.getString(this.getString(R.string.queEsSika));
            String otroQueEsSika = datos.getString(this.getString(R.string.otroQueEsSika));
            String transmiteZika = datos.getString(this.getString(R.string.transmiteZika));
            String otroTransmiteZika = datos.getString(this.getString(R.string.otroTransmiteZika));
            String sintomas = datos.getString(this.getString(R.string.sintomas));
            String tenidoZikaSn = datos.getString(this.getString(R.string.tenidoZikaSn));
            String fechaZika = datos.getString(this.getString(R.string.fechaZika));
            String sintomasZika = datos.getString(this.getString(R.string.sintomasZika));
            String zikaConfirmadoMedico = datos.getString(this.getString(R.string.zikaConfirmadoMedico));
            String tenidoDengueSn = datos.getString(this.getString(R.string.tenidoDengueSn));
            String fechaDengue = datos.getString(this.getString(R.string.fechaDengue));
            String dengueConfirmadoMedico = datos.getString(this.getString(R.string.dengueConfirmadoMedico));
            String tenidoChikSn = datos.getString(this.getString(R.string.tenidoChikSn));
            String fechaChik = datos.getString(this.getString(R.string.fechaChik));
            String chikConfirmadoMedico = datos.getString(this.getString(R.string.chikConfirmadoMedico));
            String vacunaFiebreAmarillaSn = datos.getString(this.getString(R.string.vacunaFiebreAmarillaSn));
            String fechaVacunaFiebreAmar = datos.getString(this.getString(R.string.fechaVacunaFiebreAmar));
            String transfusionSangreSn = datos.getString(this.getString(R.string.transfusionSangreSn));
            String fechaTransfusionSangre = datos.getString(this.getString(R.string.fechaTransfusionSangre));
            String usaRepelentes = datos.getString(this.getString(R.string.usaRepelentes));
            String conoceLarvas = datos.getString(this.getString(R.string.conoceLarvas));
            String lugaresLarvas = datos.getString(this.getString(R.string.lugaresLarvas));
            String otrosLugaresLarvas = datos.getString(this.getString(R.string.otrosLugaresLarvas));
            String tenidoHijos = datos.getString(this.getString(R.string.tenidoHijos));
            String usaPlanificacionFam = datos.getString(this.getString(R.string.usaPlanificacionFam));
            String usaCondon = datos.getString(this.getString(R.string.usaCondon));
            String usaOtroMetodo = datos.getString(this.getString(R.string.usaOtroMetodo));

            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();

            estudiosAdapter.open();

            //listas
            if (tieneValor(escuchadoZikaSn)){
                MessageResource msescuchadoZikaSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + escuchadoZikaSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msescuchadoZikaSn != null) encuesta.setEscuchadoZikaSn(msescuchadoZikaSn.getCatKey());
            } else {
                encuesta.setEscuchadoZikaSn(null);
            }
            if (tieneValor(queEsSika)){
                String keys = "";
                queEsSika = queEsSika.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msqueEsSika = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + queEsSika + "') and "
                        + CatalogosDBConstants.catRoot + "='SA_CAT_DEF_ZIKA'", null);
                for(MessageResource ms : msqueEsSika) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty())
                    keys = keys.substring(0, keys.length() - 1);
                encuesta.setQueEsSika(keys);
            } else {
                encuesta.setQueEsSika(null);
            }
            if (tieneValor(transmiteZika)){
                String keys = "";
                transmiteZika = transmiteZika.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> mstransmiteZikaList = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + transmiteZika + "') and "
                        + CatalogosDBConstants.catRoot + "='SA_CAT_TRA_ZIKA'", null);
                for(MessageResource ms : mstransmiteZikaList) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty())
                    keys = keys.substring(0, keys.length() - 1);
                encuesta.setTransmiteZika(keys);
            } else {
                encuesta.setTransmiteZika(null);
            }
            if (tieneValor(sintomas)){
                String keys = "";
                sintomas = sintomas.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> mssintomasList = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + sintomas + "') and "
                        + CatalogosDBConstants.catRoot + "='SA_CAT_SINTOMAS'", null);
                for(MessageResource ms : mssintomasList) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty())
                    keys = keys.substring(0, keys.length() - 1);
                encuesta.setSintomas(keys);

            } else {
                encuesta.setSintomas(null);
            }
            if (tieneValor(tenidoZikaSn)){
                MessageResource mstenidoZikaSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tenidoZikaSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (mstenidoZikaSn != null) encuesta.setTenidoZikaSn(mstenidoZikaSn.getCatKey());
            } else {
                encuesta.setTenidoZikaSn(null);
            }
            if (tieneValor(sintomasZika)){
                String keys = "";
                sintomasZika = sintomasZika.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> mssintomasList = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + sintomasZika + "') and "
                        + CatalogosDBConstants.catRoot + "='SA_CAT_SINT_ZIKA'", null);
                for(MessageResource ms : mssintomasList) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty())
                    keys = keys.substring(0, keys.length() - 1);
                encuesta.setSintomasZika(keys);
            } else {
                encuesta.setSintomasZika(null);
            }
            if (tieneValor(zikaConfirmadoMedico)){
                MessageResource mszikaConfirmadoMedico = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + zikaConfirmadoMedico + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (mszikaConfirmadoMedico != null) encuesta.setZikaConfirmadoMedico(mszikaConfirmadoMedico.getCatKey());
            } else {
                encuesta.setZikaConfirmadoMedico(null);
            }
            if (tieneValor(tenidoDengueSn)){
                MessageResource mstenidoDengueSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tenidoDengueSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (mstenidoDengueSn != null) encuesta.setTenidoDengueSn(mstenidoDengueSn.getCatKey());
            } else {
                encuesta.setTenidoDengueSn(null);
            }
            if (tieneValor(dengueConfirmadoMedico)){
                MessageResource msdengueConfirmadoMedico = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dengueConfirmadoMedico + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msdengueConfirmadoMedico != null) encuesta.setDengueConfirmadoMedico(msdengueConfirmadoMedico.getCatKey());
            } else {
                encuesta.setDengueConfirmadoMedico(null);
            }
            if (tieneValor(tenidoChikSn)){
                MessageResource mstenidoChikSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tenidoChikSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (mstenidoChikSn != null) encuesta.setTenidoChikSn(mstenidoChikSn.getCatKey());
            } else {
                encuesta.setTenidoChikSn(null);
            }
            if (tieneValor(chikConfirmadoMedico)){
                MessageResource mschikConfirmadoMedico = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + chikConfirmadoMedico + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (mschikConfirmadoMedico != null) encuesta.setChikConfirmadoMedico(mschikConfirmadoMedico.getCatKey());
            } else {
                encuesta.setChikConfirmadoMedico(null);
            }
            if (tieneValor(vacunaFiebreAmarillaSn)){
                MessageResource msvacunaFiebreAmarillaSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + vacunaFiebreAmarillaSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msvacunaFiebreAmarillaSn != null) encuesta.setVacunaFiebreAmarillaSn(msvacunaFiebreAmarillaSn.getCatKey());
            } else {
                encuesta.setVacunaFiebreAmarillaSn(null);
            }
            if (tieneValor(transfusionSangreSn)){
                MessageResource mstransfusionSangreSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + transfusionSangreSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (mstransfusionSangreSn != null) encuesta.setTransfusionSangreSn(mstransfusionSangreSn.getCatKey());
            } else {
                encuesta.setTransfusionSangreSn(null);
            }
            if (tieneValor(usaRepelentes)){
                MessageResource msusaRepelentes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + usaRepelentes + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msusaRepelentes != null) encuesta.setUsaRepelentes(msusaRepelentes.getCatKey());
            } else {
                encuesta.setUsaRepelentes(null);
            }
            if (tieneValor(conoceLarvas)){
                MessageResource msconoceLarvas = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + conoceLarvas + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msconoceLarvas != null) encuesta.setConoceLarvas(msconoceLarvas.getCatKey());
            } else {
                encuesta.setConoceLarvas(null);
            }
            if (tieneValor(lugaresLarvas)){
                String keys = "";
                lugaresLarvas = lugaresLarvas.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> mslugaresLarvasList = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + lugaresLarvas + "') and "
                        + CatalogosDBConstants.catRoot + "='SA_CAT_LUG_LARVA'", null);
                for(MessageResource ms : mslugaresLarvasList) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty())
                    keys = keys.substring(0, keys.length() - 1);
                encuesta.setLugaresLarvas(keys);
            } else {
                encuesta.setLugaresLarvas(null);
            }
            if (tieneValor(tenidoHijos)){
                MessageResource mstenidoHijos = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tenidoHijos + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (mstenidoHijos != null) encuesta.setTenidoHijos(mstenidoHijos.getCatKey());
            } else {
                encuesta.setTenidoHijos(null);
            }
            if (tieneValor(usaPlanificacionFam)){
                MessageResource msusaPlanificacionFam = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + usaPlanificacionFam + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msusaPlanificacionFam != null) encuesta.setUsaPlanificacionFam(msusaPlanificacionFam.getCatKey());
            } else {
                encuesta.setUsaPlanificacionFam(null);
            }
            if (tieneValor(usaCondon)){
                MessageResource msusaCondon = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + usaCondon + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msusaCondon != null) encuesta.setUsaCondon(msusaCondon.getCatKey());
            } else {
                encuesta.setUsaCondon(null);
            }
            if (tieneValor(usaOtroMetodo)){
                MessageResource msusaOtroMetodo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + usaOtroMetodo + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msusaOtroMetodo != null) encuesta.setUsaOtroMetodo(msusaOtroMetodo.getCatKey());
            } else {
                encuesta.setUsaOtroMetodo(null);
            }

            encuesta.setFechaZika(fechaZika);
            encuesta.setFechaDengue(fechaDengue);
            encuesta.setFechaChik(fechaChik);
            encuesta.setFechaVacunaFiebreAmar(fechaVacunaFiebreAmar);
            encuesta.setFechaTransfusionSangre(fechaTransfusionSangre);

            encuesta.setOtroQueEsSika(otroQueEsSika);
            encuesta.setOtroTransmiteZika(otroTransmiteZika);
            encuesta.setOtrosLugaresLarvas(otrosLugaresLarvas);


            //Metadata
            encuesta.setRecordDate(new Date());
            encuesta.setRecordUser(username);
            encuesta.setDeviceid(infoMovil.getDeviceId());
            encuesta.setEstado('0');
            encuesta.setPasive('0');
            estudiosAdapter.editarEncuestaParticipanteSA(encuesta);
            Bundle arguments = new Bundle();
            arguments.putSerializable(Constants.PARTICIPANTE, participanteCHF);
            Intent i = new Intent(getApplicationContext(),
                    MenuParticipanteActivity.class);
            i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
            toast.show();
            finish();
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error),Toast.LENGTH_LONG);
            toast.show();
        } finally {
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
