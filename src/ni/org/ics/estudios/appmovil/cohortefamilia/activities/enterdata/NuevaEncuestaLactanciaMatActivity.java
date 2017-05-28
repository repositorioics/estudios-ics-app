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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuParticipanteActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaLactanciaMatForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaLactanciaMatFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaLactanciaMaterna;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class NuevaEncuestaLactanciaMatActivity extends FragmentActivity implements
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

    private EncuestaLactanciaMatFormLabels labels;
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static ParticipanteCohorteFamilia participanteCHF = new ParticipanteCohorteFamilia();
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
        infoMovil = new DeviceInfo(NuevaEncuestaLactanciaMatActivity.this);
        participanteCHF = (ParticipanteCohorteFamilia) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new EncuestaLactanciaMatForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        labels = new EncuestaLactanciaMatFormLabels();

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

    /**
     * Convierte un string a Date según el formato indicado
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
            if (page.getTitle().equals(labels.getDioPecho())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTiemPecho()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPechoExc()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTiemPecho())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Aun se lo da");
                changeStatus(mWizardModel.findByKey(labels.getPechoExc()), visible);
                notificarCambios = false;
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && !page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Desconocido") && !visible;
                changeStatus(mWizardModel.findByKey(labels.getPechoExcAntes()), visible);
                notificarCambios = false;
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase("Numero de meses (02- 36)");
                changeStatus(mWizardModel.findByKey(labels.getMesDioPecho()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPechoExc())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("No Exclusivamente");
                changeStatus(mWizardModel.findByKey(labels.getFormAlim()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtraAlim()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEdadLiqDistPecho()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEdadLiqDistLeche()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getEdAlimSolidos()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPechoExcAntes())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Exclusivamente");
                changeStatus(mWizardModel.findByKey(labels.getTiempPechoExcAntes()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTiempPechoExcAntes())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase("Numero de meses (02- 36)");
                changeStatus(mWizardModel.findByKey(labels.getMestPechoExc()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEdadLiqDistPecho())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase("Meses (0-24)");
                changeStatus(mWizardModel.findByKey(labels.getMesDioLiqDisPecho()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEdadLiqDistLeche())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase("Meses (0-36)");
                changeStatus(mWizardModel.findByKey(labels.getMesDioLiqDisLeche()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEdAlimSolidos())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equalsIgnoreCase("Meses (0-36)");
                changeStatus(mWizardModel.findByKey(labels.getMesDioAlimSol()), visible);
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
            MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.DatePage")){
            DatePage modifPage = (DatePage) page; modifPage.setValue("").setmVisible(visible);
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

            String dioPecho = datos.getString(this.getString(R.string.dioPecho));
            String tiemPecho = datos.getString(this.getString(R.string.tiemPecho));
            String mesDioPecho = datos.getString(this.getString(R.string.mesDioPecho));
            String pechoExc = datos.getString(this.getString(R.string.pechoExc));
            String formAlim = datos.getString(this.getString(R.string.formAlim));
            String pechoExcAntes = datos.getString(this.getString(R.string.pechoExcAntes));
            String tiempPechoExcAntes = datos.getString(this.getString(R.string.tiempPechoExcAntes));
            String mestPechoExc = datos.getString(this.getString(R.string.mestPechoExc));
            String otraAlim = datos.getString(this.getString(R.string.otraAlim));
            String edadLiqDistPecho = datos.getString(this.getString(R.string.edadLiqDistPecho));
            String mesDioLiqDisPecho = datos.getString(this.getString(R.string.mesDioLiqDisPecho));
            String edadLiqDistLeche = datos.getString(this.getString(R.string.edadLiqDistLeche));
            String mesDioLiqDisLeche = datos.getString(this.getString(R.string.mesDioLiqDisLeche));
            String edAlimSolidos = datos.getString(this.getString(R.string.edAlimSolidos));
            String mesDioAlimSol = datos.getString(this.getString(R.string.mesDioAlimSol));

            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            EncuestaLactanciaMaterna encuesta = new EncuestaLactanciaMaterna();
            encuesta.setParticipante(participanteCHF);

            if (tieneValor(dioPecho)){
                MessageResource msdioPecho = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dioPecho + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (msdioPecho != null) encuesta.setDioPecho(msdioPecho.getCatKey());
            }
            if (tieneValor(tiemPecho)){
                MessageResource mstiemPecho = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tiemPecho + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_TIEMPECHO'", null);
                if (mstiemPecho != null) encuesta.setTiemPecho(mstiemPecho.getCatKey());
            }
            if (tieneValor(pechoExc)){
                MessageResource mspechoExc = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + pechoExc + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_EXCLU'", null);
                if (mspechoExc != null) encuesta.setPechoExc(mspechoExc.getCatKey());
            }
            if (tieneValor(formAlim)){
                MessageResource msformAlim = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + formAlim + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_FORM_ALIM'", null);
                if (msformAlim != null) encuesta.setFormAlim(msformAlim.getCatKey());
            }
            if (tieneValor(pechoExcAntes)){
                MessageResource mspechoExcAntes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + pechoExcAntes + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_EXCLU'", null);
                if (mspechoExcAntes != null) encuesta.setPechoExcAntes(mspechoExcAntes.getCatKey());
            }
            if (tieneValor(tiempPechoExcAntes)){
                MessageResource mstiempPechoExcAntes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tiempPechoExcAntes + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_ALIM_EXC'", null);
                if (mstiempPechoExcAntes != null) encuesta.setTiempPechoExcAntes(mstiempPechoExcAntes.getCatKey());
            }

            if (tieneValor(otraAlim)){
                String keys = "";
                otraAlim = otraAlim.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\), " , "\\)','");
                List<MessageResource> msParedes = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + otraAlim + "') and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_OTRA_ALIM'", null);
                for(MessageResource ms : msParedes) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty())
                    keys = keys.substring(0, keys.length() - 1);
                encuesta.setOtraAlim(keys);
            }
            if (tieneValor(edadLiqDistPecho)){
                MessageResource msedadLiqDistPecho = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + edadLiqDistPecho + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_ALIM_DLP'", null);
                if (msedadLiqDistPecho != null) encuesta.setEdadLiqDistPecho(msedadLiqDistPecho.getCatKey());
            }
            if (tieneValor(edadLiqDistLeche)){
                MessageResource msedadLiqDistLeche = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + edadLiqDistLeche + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_ALIM_DL'", null);
                if (msedadLiqDistLeche != null) encuesta.setEdadLiqDistLeche(msedadLiqDistLeche.getCatKey());
            }
            if (tieneValor(edAlimSolidos)){
                MessageResource msedAlimSolidos = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + edAlimSolidos + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_ALIM_SOL'", null);
                if (msedAlimSolidos != null) encuesta.setEdAlimSolidos(msedAlimSolidos.getCatKey());
            }

            if (tieneValor(mesDioPecho)) encuesta.setMesDioPecho(Integer.valueOf(mesDioPecho));
            if (tieneValor(mestPechoExc)) encuesta.setMestPechoExc(Integer.valueOf(mestPechoExc));
            if (tieneValor(mesDioLiqDisPecho)) encuesta.setMesDioLiqDisPecho(Integer.valueOf(mesDioLiqDisPecho));
            if (tieneValor(mesDioLiqDisLeche)) encuesta.setMesDioLiqDisLeche(Integer.valueOf(mesDioLiqDisLeche));
            if (tieneValor(mesDioAlimSol)) encuesta.setMesDioAlimSol(Integer.valueOf(mesDioAlimSol));

            encuesta.setRecurso1(username);
            //Metadata
            encuesta.setRecordDate(new Date());
            encuesta.setRecordUser(username);
            encuesta.setDeviceid(infoMovil.getDeviceId());
            encuesta.setEstado('0');
            encuesta.setPasive('0');
            boolean actualizada = false;
            EncuestaLactanciaMaterna encuestaExiste = estudiosAdapter.getEncuestasLactanciaMaterna(EncuestasDBConstants.participante_chf + "='" + participanteCHF.getParticipanteCHF() + "'", EncuestasDBConstants.participante_chf);
            if (encuestaExiste != null && encuestaExiste.getParticipante() != null && encuestaExiste.getParticipante().getParticipanteCHF() != null)
                actualizada = estudiosAdapter.editarEncuestasLactanciaMaterna(encuesta);
            else estudiosAdapter.crearEncuestasLactanciaMaterna(encuesta);
            estudiosAdapter.close();
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
