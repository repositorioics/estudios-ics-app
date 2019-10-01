package ni.org.ics.estudios.appmovil.influenzauo1.activities.enterdata;

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
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.MuestrasFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.ParticipanteCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.SintomasVisitaCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaVacunaUO1;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.list.ListaMuestrasParticipanteCasoUO1Activity;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.list.ListaMuestrasVacunasUO1Activity;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.list.ListaSintomasVisitaCasoUO1Activity;
import ni.org.ics.estudios.appmovil.influenzauo1.forms.MuestraCasoUO1Form;
import ni.org.ics.estudios.appmovil.influenzauo1.forms.SintomasVisitaCasoUO1Form;
import ni.org.ics.estudios.appmovil.influenzauo1.forms.SintomasVisitaCasoUO1FormLabels;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class NuevoSintomasVisitaCasoUO1Activity extends FragmentActivity implements
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

    private SintomasVisitaCasoUO1FormLabels labels;
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static VisitaCasoUO1 visitaCaso = new VisitaCasoUO1();
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private String horaTomaMx;
    private Double volumenMaximoPermitido = 0D;
    private String accion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_enter);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevoSintomasVisitaCasoUO1Activity.this);
        accion = getIntent().getStringExtra(Constants.ACCION);
            visitaCaso = (VisitaCasoUO1) getIntent().getExtras().getSerializable(Constants.VISITA);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new SintomasVisitaCasoUO1Form(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        labels = new SintomasVisitaCasoUO1FormLabels();

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

        //seter el máximo permitido para el volumen de la muestra
        NewDatePage vol = (NewDatePage)mWizardModel.findByKey(labels.getFechaSintomas());
        DateMidnight dmDesde = new DateMidnight(visitaCaso.getFechaVisita());
        DateMidnight dmHasta = new DateMidnight(new Date().getTime());
        vol.setRangeValidation(true, dmDesde, dmHasta);

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
            if (page.getTitle().equals(labels.getFiebre())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("S");
                changeStatus(mWizardModel.findByKey(labels.getFiebreIntensidad()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTos())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("S");
                changeStatus(mWizardModel.findByKey(labels.getTosIntensidad()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getSecrecionNasal())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("S");
                changeStatus(mWizardModel.findByKey(labels.getSecrecionNasalIntensidad()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getDolorGarganta())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("S");
                changeStatus(mWizardModel.findByKey(labels.getDolorGargantaIntensidad()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getDolorCabeza())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("S");
                changeStatus(mWizardModel.findByKey(labels.getDolorCabezaIntensidad()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getDolorMuscular())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("S");
                changeStatus(mWizardModel.findByKey(labels.getDolorMuscularIntensidad()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getDolorArticular())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("S");
                changeStatus(mWizardModel.findByKey(labels.getDolorArticularIntensidad()), visible);
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

    private boolean ingresoValido(String fechaSintoma, String diaSintomaUO1){
        Date dfs = null;
        if (tieneValor(fechaSintoma)) {
            DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dfs = mDateFormat.parse(fechaSintoma);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<SintomasVisitaCasoUO1> sintomasRegistrados = estudiosAdapter.getSintomasVisitasCasosUO1(InfluenzaUO1DBConstants.codigoCasoVisita + " = '" + visitaCaso.getCodigoCasoVisita() + "' ", InfluenzaUO1DBConstants.fechaSintomas);
        for(SintomasVisitaCasoUO1 sintoma : sintomasRegistrados){
            if (sintoma.getDia().equalsIgnoreCase(diaSintomaUO1)) {
                Toast toast = Toast.makeText(getApplicationContext(),String.format(getString(R.string.diaRegistrado), diaSintomaUO1), Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
            if (sintoma.getFechaSintomas()!=null && sintoma.getFechaSintomas().equals(dfs)) {
                Toast toast = Toast.makeText(getApplicationContext(),String.format(getString(R.string.fechaRegistrada), fechaSintoma), Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        }
        return true;
    }

    public void saveData() {
        try {
            Map<String, String> mapa = mWizardModel.getAnswers();
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }

            String fechaSintoma = datos.getString(this.getString(R.string.fechaSintomaUO1));
            String diaSintomaUO1 = datos.getString(this.getString(R.string.diaSintomaUO1));
            String consultaInicialUO1 = datos.getString(this.getString(R.string.consultaInicialUO1));
            String fiebreUO1 = datos.getString(this.getString(R.string.fiebreUO1));
            String fiebreIntensidadUO1 = datos.getString(this.getString(R.string.fiebreIntensidadUO1));
            String tosUO1 = datos.getString(this.getString(R.string.tosUO1));
            String tosIntensidadUO1 = datos.getString(this.getString(R.string.tosIntensidadUO1));
            String secrecionNasalUO1 = datos.getString(this.getString(R.string.secrecionNasalUO1));
            String secrecionNasalIntensidadUO1 = datos.getString(this.getString(R.string.secrecionNasalIntensidadUO1));
            String dolorGargantaUO1 = datos.getString(this.getString(R.string.dolorGargantaUO1));
            String dolorGargantaIntensidadUO1 = datos.getString(this.getString(R.string.dolorGargantaIntensidadUO1));
            String congestionNasalUO1 = datos.getString(this.getString(R.string.congestionNasalUO1));
            String dolorCabezaUO1 = datos.getString(this.getString(R.string.dolorCabezaUO1));
            String dolorCabezaIntensidadUO1 = datos.getString(this.getString(R.string.dolorCabezaIntensidadUO1));
            String faltaApetitoUO1 = datos.getString(this.getString(R.string.faltaApetitoUO1));
            String dolorMuscularUO1 = datos.getString(this.getString(R.string.dolorMuscularUO1));
            String dolorMuscularIntensidadUO1 = datos.getString(this.getString(R.string.dolorMuscularIntensidadUO1));
            String dolorArticularUO1 = datos.getString(this.getString(R.string.dolorArticularUO1));
            String dolorArticularIntensidadUO1 = datos.getString(this.getString(R.string.dolorArticularIntensidadUO1));
            String dolorOidoUO1 = datos.getString(this.getString(R.string.dolorOidoUO1));
            String respiracionRapidaUO1 = datos.getString(this.getString(R.string.respiracionRapidaUO1));
            String dificultadRespiratoriaUO1 = datos.getString(this.getString(R.string.dificultadRespiratoriaUO1));
            String faltaEscueltaUO1 = datos.getString(this.getString(R.string.faltaEscueltaUO1));
            String quedoCamaUO1 = datos.getString(this.getString(R.string.quedoCamaUO1));

            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            if (ingresoValido(fechaSintoma, diaSintomaUO1)) {
                SintomasVisitaCasoUO1 sintoma = new SintomasVisitaCasoUO1();
                sintoma.setCodigoSintoma(infoMovil.getId());
                sintoma.setCodigoCasoVisita(visitaCaso);
                //listas
                sintoma.setDia(diaSintomaUO1);
                if (tieneValor(consultaInicialUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + consultaInicialUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setConsultaInicial(mstomaMxSn.getCatKey());
                }
                if (tieneValor(fiebreUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + fiebreUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setFiebre(mstomaMxSn.getCatKey());
                }
                if (tieneValor(fiebreIntensidadUO1)) {
                    MessageResource msobservacion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + fiebreIntensidadUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='CAT_INTENSIDAD_SIN'", null);
                    if (msobservacion != null) sintoma.setFiebreIntensidad(msobservacion.getCatKey());
                }
                if (tieneValor(tosUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tosUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setTos(mstomaMxSn.getCatKey());
                }
                if (tieneValor(tosIntensidadUO1)) {
                    MessageResource msobservacion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tosIntensidadUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='CAT_INTENSIDAD_SIN'", null);
                    if (msobservacion != null) sintoma.setTosIntensidad(msobservacion.getCatKey());
                }
                if (tieneValor(secrecionNasalUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + secrecionNasalUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setSecrecionNasal(mstomaMxSn.getCatKey());
                }
                if (tieneValor(secrecionNasalIntensidadUO1)) {
                    MessageResource msobservacion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + secrecionNasalIntensidadUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='CAT_INTENSIDAD_SIN'", null);
                    if (msobservacion != null) sintoma.setSecrecionNasalIntensidad(msobservacion.getCatKey());
                }
                if (tieneValor(dolorGargantaUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dolorGargantaUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setDolorGarganta(mstomaMxSn.getCatKey());
                }
                if (tieneValor(dolorGargantaIntensidadUO1)) {
                    MessageResource msobservacion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dolorGargantaIntensidadUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='CAT_INTENSIDAD_SIN'", null);
                    if (msobservacion != null) sintoma.setDolorGargantaIntensidad(msobservacion.getCatKey());
                }
                if (tieneValor(congestionNasalUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + congestionNasalUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setCongestionNasal(mstomaMxSn.getCatKey());
                }
                if (tieneValor(dolorCabezaUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dolorCabezaUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setDolorCabeza(mstomaMxSn.getCatKey());
                }
                if (tieneValor(dolorCabezaIntensidadUO1)) {
                    MessageResource msobservacion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dolorCabezaIntensidadUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='CAT_INTENSIDAD_SIN'", null);
                    if (msobservacion != null) sintoma.setDolorCabezaIntensidad(msobservacion.getCatKey());
                }
                if (tieneValor(faltaApetitoUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + faltaApetitoUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setFaltaApetito(mstomaMxSn.getCatKey());
                }
                if (tieneValor(dolorMuscularUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dolorMuscularUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setDolorMuscular(mstomaMxSn.getCatKey());
                }
                if (tieneValor(dolorMuscularIntensidadUO1)) {
                    MessageResource msobservacion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dolorMuscularIntensidadUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='CAT_INTENSIDAD_SIN'", null);
                    if (msobservacion != null) sintoma.setDolorMuscularIntensidad(msobservacion.getCatKey());
                }
                if (tieneValor(dolorArticularUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dolorArticularUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setDolorArticular(mstomaMxSn.getCatKey());
                }
                if (tieneValor(dolorArticularIntensidadUO1)) {
                    MessageResource msobservacion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dolorArticularIntensidadUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='CAT_INTENSIDAD_SIN'", null);
                    if (msobservacion != null) sintoma.setDolorArticularIntensidad(msobservacion.getCatKey());
                }
                if (tieneValor(dolorOidoUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dolorOidoUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setDolorOido(mstomaMxSn.getCatKey());
                }
                if (tieneValor(respiracionRapidaUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + respiracionRapidaUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setRespiracionRapida(mstomaMxSn.getCatKey());
                }
                if (tieneValor(dificultadRespiratoriaUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dificultadRespiratoriaUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setDificultadRespiratoria(mstomaMxSn.getCatKey());
                }
                if (tieneValor(faltaEscueltaUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + faltaEscueltaUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setFaltaEscuelta(mstomaMxSn.getCatKey());
                }
                if (tieneValor(quedoCamaUO1)) {
                    MessageResource mstomaMxSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + quedoCamaUO1 + "' and "
                            + CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", null);
                    if (mstomaMxSn != null) sintoma.setQuedoCama(mstomaMxSn.getCatKey());
                }
                if (tieneValor(fechaSintoma)) {
                    DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        sintoma.setFechaSintomas(mDateFormat.parse(fechaSintoma));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                //Metadata
                sintoma.setRecordDate(new Date());
                sintoma.setRecordUser(username);
                sintoma.setDeviceid(infoMovil.getDeviceId());
                sintoma.setEstado('0');
                sintoma.setPasive('0');
                estudiosAdapter.crearSintomasVisitaCasoUO1(sintoma);
                estudiosAdapter.close();
                Intent i;
                Bundle arguments = new Bundle();
                arguments.putSerializable(Constants.VISITA, visitaCaso);
                i = new Intent(getApplicationContext(),
                        ListaSintomasVisitaCasoUO1Activity.class);
                i.putExtras(arguments);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
                toast.show();
                finish();
            }
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
