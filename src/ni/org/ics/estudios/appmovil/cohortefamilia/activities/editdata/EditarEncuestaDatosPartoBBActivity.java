package ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata;

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
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaDatosPartoBBForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaDatosPartoBBFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaDatosPartoBB;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;
import org.codehaus.jackson.sym.BytesToNameCanonicalizer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class EditarEncuestaDatosPartoBBActivity extends FragmentActivity implements
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

    private EncuestaDatosPartoBBFormLabels labels = new EncuestaDatosPartoBBFormLabels();
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static EncuestaDatosPartoBB encuesta = new EncuestaDatosPartoBB();
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
        infoMovil = new DeviceInfo(EditarEncuestaDatosPartoBBActivity.this);
        encuesta = (EncuestaDatosPartoBB) getIntent().getExtras().getSerializable(Constants.ENCUESTA);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new EncuestaDatosPartoBBForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
        estudiosAdapter.open();
        if (encuesta != null) {
            Bundle dato = null;
            Page modifPage;
            if (tieneValor(encuesta.getTipoParto())) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTipoParto());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getTipoParto() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_TIPO_PARTO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (tieneValor(encuesta.getTiempoEmb_sndr())) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTiempoEmbSndr());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getTiempoEmb_sndr() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SNDR'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuesta.getTiempoEmbSemana()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getTiempoEmbSemana());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuesta.getTiempoEmbSemana()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (tieneValor(encuesta.getDocMedTiempoEmb_sn())) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getDocMedTiempoEmbSn());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getDocMedTiempoEmb_sn() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (tieneValor(encuesta.getDocMedTiempoEmb())) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getDocMedTiempoEmb());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getDocMedTiempoEmb() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_DOC_MEDICO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getOtroDocMedTiempoEmb()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getOtroDocMedTiempoEmb());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getOtroDocMedTiempoEmb());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getFum()!=null){
                modifPage = (NewDatePage) mWizardModel.findByKey(labels.getFum());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, DateToString(encuesta.getFum(),"dd/MM/yyyy"));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (tieneValor(encuesta.getFumFueraRango_sn())) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getFumFueraRangoSn());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getFumFueraRango_sn() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
                if (encuesta.getFumFueraRango_sn().equalsIgnoreCase(Constants.NOKEYSND)){
                    modifPage = (LabelPage) mWizardModel.findByKey(labels.getReingresarFUM());
                    modifPage.setmVisible(true);
                }
            }
            if (encuesta.getFumFueraRango_razon()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getFumFueraRangoRazon());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getFumFueraRango_razon());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }

            if (tieneValor(encuesta.getDocMedEdadGest_sn())) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getDocMedEdadGestSn());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getDocMedEdadGest_sn() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuesta.getEdadGest()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getEdadGest());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuesta.getEdadGest()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (tieneValor(encuesta.getDocMedEdadGest())) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getDocMedEdadGest());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getDocMedEdadGest() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_DOC_MEDICO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getOtroDocMedEdadGest()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getOtroDocMedEdadGest());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getOtroDocMedEdadGest());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (tieneValor(encuesta.getPrematuro_sndr())) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPrematuroSndr());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getPrematuro_sndr() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SNDR'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (tieneValor(encuesta.getPesoBB_sndr())) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPesoBBSndr());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getPesoBB_sndr() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SNDR'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuesta.getPesoBB()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getPesoBB());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuesta.getPesoBB()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (tieneValor(encuesta.getDocMedPesoBB_sn())) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getDocMedPesoBBSn());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getDocMedPesoBB_sn() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (tieneValor(encuesta.getDocMedPesoBB())) {
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getDocMedPesoBB());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuesta.getDocMedPesoBB() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_DOC_MEDICO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if (encuesta.getOtroDocMedPesoBB()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getOtroDocMedPesoBB());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getOtroDocMedPesoBB());
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

    /**
     * Convierte una Date a String, seg??n el formato indicado
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

    /**
     * Convierte un string a Date seg??n el formato indicado
     * @param strFecha cadena a convertir
     * @param formato formato solicitado
     * @return Fecha
     * @throws java.text.ParseException
     */
    public static Date StringToDate(String strFecha, String formato) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        return simpleDateFormat.parse(strFecha);
    }

    public static int diferenciaEnDias(Date fecha1, Date fecha2) {
        long diferenciaEn_ms = fecha1.getTime() - fecha2.getTime();
        long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
        return (int) dias;
    }

    public static int diferenciaEnSemanas(Date fecha1, Date fecha2) {
        long dias = diferenciaEnDias(fecha1, fecha2);
        return (int) dias / 7;
    }

    public void updateModel(Page page){
        try {
            boolean visible = false;
            if (page.getTitle().equals(labels.getTiempoEmbSndr())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTiempoEmbSemana()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDocMedTiempoEmbSn()), visible);
                //notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDocMedEdadGestSn()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDocMedTiempoEmbSn())) {
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null) {
                    visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                    changeStatus(mWizardModel.findByKey(labels.getDocMedTiempoEmb()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getFum()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getFumFueraRangoSn()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getFumFueraRangoRazon()), visible);
                }
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDocMedTiempoEmb())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroDocMedTiempoEmb()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFum())) {
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null) {
                    String fum = page.getData().getString(TextPage.SIMPLE_DATA_KEY);
                    Date dFum = StringToDate(fum, "dd/MM/yyyy");
                    int semanaGest = diferenciaEnSemanas(encuesta.getParticipante().getParticipante().getFechaNac(), dFum);
                    if (semanaGest < 25 || semanaGest > 45) visible = true;
                    changeStatus(mWizardModel.findByKey(labels.getFumFueraRangoSn()), visible);
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getFumFueraRangoRazon()), visible);
                    //notificarCambios = false;
                }
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFumFueraRangoSn())) {
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null) {
                    changeStatus(mWizardModel.findByKey(labels.getFumFueraRangoRazon()), page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES));
                    //notificarCambios = false;
                    changeStatus(mWizardModel.findByKey(labels.getReingresarFUM()), page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO));
                }
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDocMedEdadGestSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getEdadGest()), visible);
                ////notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDocMedEdadGest()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtroDocMedEdadGest()), false);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDocMedEdadGest())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroDocMedEdadGest()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPesoBBSndr())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getPesoBB()), visible);
                ////notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getDocMedPesoBB()), false);
                changeStatus(mWizardModel.findByKey(labels.getDocMedPesoBBSn()), visible);
                //changeStatus(mWizardModel.findByKey(labels.getOtroDocMedPesoBB()), false);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDocMedPesoBBSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getDocMedPesoBB()), visible);
                //changeStatus(mWizardModel.findByKey(labels.getOtroDocMedPesoBB()), false);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getDocMedPesoBB())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroDocMedPesoBB()), visible);
                //notificarCambios = false;
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
            TextPage modifPage = (TextPage) page; modifPage.resetData(new Bundle());modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NumberPage")){
            NumberPage modifPage = (NumberPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage")){
            MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.DatePage")){
            DatePage modifPage = (DatePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SelectParticipantPage")){
            SelectParticipantPage modifPage = (SelectParticipantPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
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

            String scTipoParto = datos.getString(this.getString(R.string.tipoParto));
            String scTiempoEmbSndr = datos.getString(this.getString(R.string.tiempoEmb_sndr));
            String npTiempoEmbSemana = datos.getString(this.getString(R.string.tiempoEmbSemana));
            String scDocMedTiempoEmbSn = datos.getString(this.getString(R.string.docMedTiempoEmb_sn));
            String scDocMedTiempoEmb = datos.getString(this.getString(R.string.docMedTiempoEmb));
            String tpOtroDocMedTiempoEmb = datos.getString(this.getString(R.string.otroDocMedTiempoEmb));
            String dpFum = datos.getString(this.getString(R.string.fum));
            String scFumFueraRangoSn = datos.getString(this.getString(R.string.fumFueraRango_sn));
            String tpFumFueraRangoRazon = datos.getString(this.getString(R.string.fumFueraRango_razon));
            String scDocMedEdadGestSn = datos.getString(this.getString(R.string.docMedEdadGest_sn));
            String npEdadGest = datos.getString(this.getString(R.string.edadGest));
            String scDocMedEdadGest = datos.getString(this.getString(R.string.docMedEdadGest));
            String tpOtroDocMedEdadGest = datos.getString(this.getString(R.string.otroDocMedEdadGest));
            String scPrematuroSndr = datos.getString(this.getString(R.string.prematuro_sndr));
            String scPesoBBSndr = datos.getString(this.getString(R.string.pesoBB_sndr));
            String npPesoBB = datos.getString(this.getString(R.string.pesoBB));
            String scDocMedPesoBBSn = datos.getString(this.getString(R.string.docMedPesoBB_sn));
            String scDocMedPesoBB = datos.getString(this.getString(R.string.docMedPesoBB));
            String tpOtroDocMedPesoBB = datos.getString(this.getString(R.string.otroDocMedPesoBB));


            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            //listas
            if (tieneValor(scTipoParto)){
                MessageResource msTipoParto = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scTipoParto + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_TIPO_PARTO'", null);
                if (msTipoParto != null) encuesta.setTipoParto(msTipoParto.getCatKey());
            } else {
                encuesta.setTipoParto(null);
            }
            if (tieneValor(scTiempoEmbSndr)){
                MessageResource msTiempoEmbSndr = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scTiempoEmbSndr + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SNDR'", null);
                if (msTiempoEmbSndr != null) encuesta.setTiempoEmb_sndr(msTiempoEmbSndr.getCatKey());
            } else {
                encuesta.setTiempoEmb_sndr(null);
            }
            if (tieneValor(scDocMedTiempoEmbSn)){
                MessageResource msDocMedTiempoEmbSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scDocMedTiempoEmbSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msDocMedTiempoEmbSn != null) encuesta.setDocMedTiempoEmb_sn(msDocMedTiempoEmbSn.getCatKey());
            } else {
                encuesta.setDocMedTiempoEmb_sn(null);
            }
            if (tieneValor(scDocMedTiempoEmb)){
                MessageResource msDocMedTiempoEmb = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scDocMedTiempoEmb + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_DOC_MEDICO'", null);
                if (msDocMedTiempoEmb != null) encuesta.setDocMedTiempoEmb(msDocMedTiempoEmb.getCatKey());
            } else {
                encuesta.setDocMedTiempoEmb(null);
            }
            if (tieneValor(scFumFueraRangoSn)){
                MessageResource msFumFueraRangoSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scFumFueraRangoSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msFumFueraRangoSn != null) encuesta.setFumFueraRango_sn(msFumFueraRangoSn.getCatKey());
            } else {
                encuesta.setFumFueraRango_sn(null);
            }
            if (tieneValor(scDocMedEdadGestSn)){
                MessageResource msDocMedEdadGestSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scDocMedEdadGestSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msDocMedEdadGestSn != null) encuesta.setDocMedEdadGest_sn(msDocMedEdadGestSn.getCatKey());
            } else {
                encuesta.setDocMedEdadGest_sn(null);
            }
            if (tieneValor(scDocMedEdadGest)){
                MessageResource msDocMedEdadGest = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scDocMedEdadGest + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_DOC_MEDICO'", null);
                if (msDocMedEdadGest != null) encuesta.setDocMedEdadGest(msDocMedEdadGest.getCatKey());
            } else {
                encuesta.setDocMedEdadGest(null);
            }
            if (tieneValor(scPrematuroSndr)){
                MessageResource msPrematuroSndr = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scPrematuroSndr + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SNDR'", null);
                if (msPrematuroSndr != null) encuesta.setPrematuro_sndr(msPrematuroSndr.getCatKey());
            } else {
                encuesta.setPrematuro_sndr(null);
            }
            if (tieneValor(scPesoBBSndr)){
                MessageResource msPesoBBSndr = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scPesoBBSndr + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SNDR'", null);
                if (msPesoBBSndr != null) encuesta.setPesoBB_sndr(msPesoBBSndr.getCatKey());
            } else {
                encuesta.setPesoBB_sndr(null);
            }
            if (tieneValor(scDocMedPesoBBSn)){
                MessageResource msDocMedPesoBBSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scDocMedPesoBBSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msDocMedPesoBBSn != null) encuesta.setDocMedPesoBB_sn(msDocMedPesoBBSn.getCatKey());
            } else {
                encuesta.setDocMedPesoBB_sn(null);
            }
            if (tieneValor(scDocMedPesoBB)){
                MessageResource msDocMedPesoBB = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scDocMedPesoBB + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_DOC_MEDICO'", null);
                if (msDocMedPesoBB != null) encuesta.setDocMedPesoBB(msDocMedPesoBB.getCatKey());
            } else {
                encuesta.setDocMedPesoBB(null);
            }
            //fechas
            try {
                if (tieneValor(dpFum)) encuesta.setFum(StringToDate(dpFum, "dd/MM/yyyy"));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
                toast.show();
                finish();
            }
            //Numericos
            if (tieneValor(npTiempoEmbSemana)) {
                encuesta.setTiempoEmbSemana(Integer.valueOf(npTiempoEmbSemana));
            } else {
                encuesta.setTiempoEmbSemana(null);
            }
            if (tieneValor(npEdadGest)) {
                encuesta.setEdadGest(Integer.valueOf(npEdadGest));
            } else {
                encuesta.setEdadGest(null);
            }
            if (tieneValor(npPesoBB)) {
                encuesta.setPesoBB(Integer.valueOf(npPesoBB));
            } else {
                encuesta.setPesoBB(null);
            }
            //textos
            encuesta.setOtroDocMedTiempoEmb(tpOtroDocMedTiempoEmb);
            encuesta.setFumFueraRango_razon(tpFumFueraRangoRazon);
            encuesta.setOtroDocMedEdadGest(tpOtroDocMedEdadGest);
            encuesta.setOtroDocMedPesoBB(tpOtroDocMedPesoBB);
            encuesta.setRecurso1(username);
            //Metadata
            encuesta.setRecordDate(new Date());
            encuesta.setRecordUser(username);
            encuesta.setDeviceid(infoMovil.getDeviceId());
            encuesta.setEstado('0');
            encuesta.setPasive('0');
            estudiosAdapter.editarEncuestasDatosPartoBB(encuesta);

            Bundle arguments = new Bundle();
            arguments.putSerializable(Constants.PARTICIPANTE, encuesta.getParticipante());
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
