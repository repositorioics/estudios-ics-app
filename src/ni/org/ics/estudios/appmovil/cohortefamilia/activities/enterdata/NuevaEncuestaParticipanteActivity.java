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
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaParticipanteForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaParticipanteFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaParticipante;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class NuevaEncuestaParticipanteActivity extends FragmentActivity implements
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

    private EncuestaParticipanteFormLabels labels;
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
        infoMovil = new DeviceInfo(NuevaEncuestaParticipanteActivity.this);
        participanteCHF = (ParticipanteCohorteFamilia) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new EncuestaParticipanteForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        labels = new EncuestaParticipanteFormLabels();

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

        if (participanteCHF.getParticipante()!=null){
            String edad[] = participanteCHF.getParticipante().getEdad().split("/");

            int anios = 0;
            if (edad.length > 0)
                anios = Integer.valueOf(edad[0]);
            if (anios > 14 && participanteCHF.getParticipante().getSexo().matches("F")){
                changeStatus(mWizardModel.findByKey(labels.getEstaEmbarazada()), true);
            }
            if (anios > 18) {
                changeStatus(mWizardModel.findByKey(labels.getEsAlfabeto()), true);
                changeStatus(mWizardModel.findByKey(labels.getNivelEducacion()), true);
                changeStatus(mWizardModel.findByKey(labels.getTrabaja()), true);
            }
            if (anios < 18){
                changeStatus(mWizardModel.findByKey(labels.getVaNinoEscuela()), true);
                changeStatus(mWizardModel.findByKey(labels.getNinoTrabaja()), true);
                changeStatus(mWizardModel.findByKey(labels.getCantNinosLugarCuidan()), true);
                changeStatus(mWizardModel.findByKey(labels.getConQuienViveNino()), true);
                changeStatus(mWizardModel.findByKey(labels.getPadreEnEstudio()), true);
                changeStatus(mWizardModel.findByKey(labels.getMadreEnEstudio()), true);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(participanteCHF.getParticipante().getFechaNac());
            int anioNac = calendar.get(Calendar.YEAR);
            calendar.setTime(new Date());
            int anioActual = calendar.get(Calendar.YEAR);
            NumberPage p1 = (NumberPage)mWizardModel.findByKey(labels.getAnioVacunaInfluenza());
            p1.setRangeValidation(true, anioNac, anioActual);
            NumberPage p2 = (NumberPage)mWizardModel.findByKey(labels.getAnioFechaDiagnosticoTubPulActual());
            p2.setRangeValidation(true, anioNac, anioActual);
            NumberPage p3 = (NumberPage)mWizardModel.findByKey(labels.getAnioFechaDiagnosticoTubPulPasado());
            p3.setRangeValidation(true, anioNac, anioActual);
        }

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


    private String[] fillCatalog(String codigoCatalogo, int seleccionado){
        int index;
        String[] catalogo;
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        if (estudiosAdapter == null)
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
        estudiosAdapter.open();
        List<MessageResource> mCatalogo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='"+codigoCatalogo+"'", CatalogosDBConstants.order);
        estudiosAdapter.close();
        catalogo = new String[mCatalogo.size()];
        index = 0;
        for (MessageResource message: mCatalogo){
            if (seleccionado>0){
                if (seleccionado == Integer.valueOf(message.getCatKey()))
                    break;
            }
            catalogo[index] = message.getSpanish();
            index++;
        }
        return catalogo;
    }

    public void updateConstrains(){

    }

    public void updateModel(Page page){
        try {
            boolean visible = false;
            if (page.getTitle().equals(labels.getEstaEmbarazada())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getSemanasEmbarazo()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTrabaja())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTipoTrabajo()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOcupacionActual()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getVaNinoEscuela())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getGradoCursa()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTurno()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombreCentroEstudio()), visible);
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getDondeCuidanNino()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getNinoTrabaja())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getOcupacionActualNino()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPadreEnEstudio())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCodigoPadreEstudio()), visible);
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getPadreAlfabeto()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNivelEducacionPadre()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTrabajaPadre()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTrabajaPadre())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTipoTrabajoPadre()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMadreEnEstudio())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCodigoMadreEstudio()), visible);
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getMadreAlfabeto()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNivelEducacionMadre()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTrabajaMadre()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTrabajaMadre())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTipoTrabajoMadre()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFuma())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getPeriodicidadFuma()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrillos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getFumaDentroCasa()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTuberculosisPulmonarActual())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioFechaDiagnosticoTubPulActual()), visible);
                changeStatus(mWizardModel.findByKey(labels.getMesFechaDiagnosticoTubPulActual()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTomaTratamientoTubPulActual()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCompletoTratamientoTubPulActual()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTuberculosisPulmonarPasado())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFechaDiagnosticoTubPulPasadoSn()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTuberculosisPulmonarPasado())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCompletoTratamientoTubPulPasado()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFechaDiagnosticoTubPulPasadoSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioFechaDiagnosticoTubPulPasado()), visible);
                changeStatus(mWizardModel.findByKey(labels.getMesFechaDiagnosticoTubPulPasado()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCrisisAsma())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCrisisAsma()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getOtrasEnfermedades())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getDescOtrasEnfermedades()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getVacunaInfluenza())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioVacunaInfluenza()), visible);
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

            String estaEmbarazada = datos.getString(this.getString(R.string.estaEmbarazada));
            String semanasEmbarazo = datos.getString(this.getString(R.string.semanasEmbarazo));
            String esAlfabeto = datos.getString(this.getString(R.string.esAlfabeto));
            String nivelEducacion = datos.getString(this.getString(R.string.nivelEducacion));
            String trabaja = datos.getString(this.getString(R.string.trabaja));
            String tipoTrabajo = datos.getString(this.getString(R.string.tipoTrabajo));
            String ocupacionActual = datos.getString(this.getString(R.string.ocupacionActual));
            String vaNinoEscuela = datos.getString(this.getString(R.string.vaNinoEscuela));
            String gradoCursa = datos.getString(this.getString(R.string.gradoCursa));
            String turno = datos.getString(this.getString(R.string.turno));
            String nombreCentroEstudio = datos.getString(this.getString(R.string.nombreCentroEstudio));
            String dondeCuidanNino = datos.getString(this.getString(R.string.dondeCuidanNino));
            String ninoTrabaja = datos.getString(this.getString(R.string.ninoTrabaja));
            String ocupacionActualNino = datos.getString(this.getString(R.string.ocupacionActualNino));
            String cantNinosLugarCuidan = datos.getString(this.getString(R.string.cantNinosLugarCuidan));
            String conQuienViveNino = datos.getString(this.getString(R.string.conQuienViveNino));
            //String descOtroViveNino = datos.getString(this.getString(R.string.descOtroViveNino));
            String padreEnEstudio = datos.getString(this.getString(R.string.padreEnEstudio));
            String codigoPadreEstudio = datos.getString(this.getString(R.string.codigoPadreEstudio));
            String padreAlfabeto = datos.getString(this.getString(R.string.padreAlfabeto));
            String nivelEducacionPadre = datos.getString(this.getString(R.string.nivelEducacionPadre));
            String trabajaPadre = datos.getString(this.getString(R.string.trabajaPadre));
            String tipoTrabajoPadre = datos.getString(this.getString(R.string.tipoTrabajoPadre));
            String madreEnEstudio = datos.getString(this.getString(R.string.madreEnEstudio));
            String codigoMadreEstudio = datos.getString(this.getString(R.string.codigoMadreEstudio));
            String madreAlfabeto = datos.getString(this.getString(R.string.madreAlfabeto));
            String nivelEducacionMadre = datos.getString(this.getString(R.string.nivelEducacionMadre));
            String trabajaMadre = datos.getString(this.getString(R.string.trabajaMadre));
            String tipoTrabajoMadre = datos.getString(this.getString(R.string.tipoTrabajoMadre));
            String fuma = datos.getString(this.getString(R.string.fuma));
            String periodicidadFuma = datos.getString(this.getString(R.string.periodicidadFuma));
            String cantidadCigarrillos = datos.getString(this.getString(R.string.cantidadCigarrillos));
            String fumaDentroCasa = datos.getString(this.getString(R.string.fumaDentroCasa));
            String tuberculosisPulmonarActual = datos.getString(this.getString(R.string.tuberculosisPulmonarActual));
            //String fechaDiagnosticoTubPulActual = datos.getString(this.getString(R.string.fechaDiagnosticoTubPulActual));
            String anioFechaDiagnosticoTubPulActual = datos.getString(this.getString(R.string.anioFechaDiagnosticoTubPulActual));
            String mesFechaDiagnosticoTubPulActual = datos.getString(this.getString(R.string.mesFechaDiagnosticoTubPulActual));
            String tomaTratamientoTubPulActual = datos.getString(this.getString(R.string.tomaTratamientoTubPulActual));
            String completoTratamientoTubPulActual = datos.getString(this.getString(R.string.completoTratamientoTubPulActual));
            String tuberculosisPulmonarPasado = datos.getString(this.getString(R.string.tuberculosisPulmonarPasado));
            //String fechaDiagnosticoTubPulPasado = datos.getString(this.getString(R.string.fechaDiagnosticoTubPulPasado));
            String fechaDiagnosticoTubPulPasadoSn = datos.getString(this.getString(R.string.fechaDiagnosticoTubPulPasadoSn));
            String anioFechaDiagnosticoTubPulPasado = datos.getString(this.getString(R.string.anioFechaDiagnosticoTubPulPasado));
            String mesFechaDiagnosticoTubPulPasado = datos.getString(this.getString(R.string.mesFechaDiagnosticoTubPulPasado));
            String tomaTratamientoTubPulPasado = datos.getString(this.getString(R.string.tomaTratamientoTubPulPasado));
            String completoTratamientoTubPulPasado = datos.getString(this.getString(R.string.completoTratamientoTubPulPasado));
            String alergiaRespiratoria = datos.getString(this.getString(R.string.alergiaRespiratoria));
            String cardiopatia = datos.getString(this.getString(R.string.cardiopatia));
            String enfermedadPulmonarOC = datos.getString(this.getString(R.string.enfermedadPulmonarOC));
            String diabetes = datos.getString(this.getString(R.string.diabetes));
            String presionAlta = datos.getString(this.getString(R.string.presionAlta));
            String asma = datos.getString(this.getString(R.string.asma));
            String silbidoRespirarPechoApretado = datos.getString(this.getString(R.string.silbidoRespirarPechoApretado));
            String tosSinFiebreResfriado = datos.getString(this.getString(R.string.tosSinFiebreResfriado));
            String usaInhaladoresSpray = datos.getString(this.getString(R.string.usaInhaladoresSpray));
            String crisisAsma = datos.getString(this.getString(R.string.crisisAsma));
            String cantidadCrisisAsma = datos.getString(this.getString(R.string.cantidadCrisisAsma));
            String vecesEnfermoEnfermedadesRes = datos.getString(this.getString(R.string.vecesEnfermoEnfermedadesRes));
            String otrasEnfermedades = datos.getString(this.getString(R.string.otrasEnfermedades));
            String descOtrasEnfermedades = datos.getString(this.getString(R.string.descOtrasEnfermedades));
            String vacunaInfluenza = datos.getString(this.getString(R.string.vacunaInfluenza));
            String anioVacunaInfluenza = datos.getString(this.getString(R.string.anioVacunaInfluenza));

            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            if (estudiosAdapter == null)
                estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            EncuestaParticipante encuesta = new EncuestaParticipante();
            encuesta.setParticipante(participanteCHF);
            //listas
            if (tieneValor(estaEmbarazada)){
                MessageResource msEstaEmbarazada = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + estaEmbarazada + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msEstaEmbarazada != null) encuesta.setEstaEmbarazada(msEstaEmbarazada.getCatKey().charAt(0));
            }
            if (tieneValor(esAlfabeto)){
                MessageResource msEsAlfabeto = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + esAlfabeto + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msEsAlfabeto != null) encuesta.setEsAlfabeto(msEsAlfabeto.getCatKey().charAt(0));
            }
            if (tieneValor(nivelEducacion)){
                MessageResource msNivelEducacion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + nivelEducacion + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_NIV_EDU'", null);
                if (msNivelEducacion != null) encuesta.setNivelEducacion(msNivelEducacion.getCatKey());
            }
            if (tieneValor(trabaja)){
                MessageResource msTrabaja = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + trabaja + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msTrabaja != null) encuesta.setTrabaja(msTrabaja.getCatKey().charAt(0));
            }
            if (tieneValor(tipoTrabajo)){
                MessageResource msTipoTrabajo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tipoTrabajo + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_TIP_TRABAJO'", null);
                if (msTipoTrabajo != null) encuesta.setTipoTrabajo(msTipoTrabajo.getCatKey());
            }
            if (tieneValor(vaNinoEscuela)){
                MessageResource msVaNinoEscuela = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + vaNinoEscuela + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msVaNinoEscuela != null) encuesta.setVaNinoEscuela(msVaNinoEscuela.getCatKey().charAt(0));
            }
            if (tieneValor(gradoCursa)){
                MessageResource msGradoCursa = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + gradoCursa + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_GRD_EDU'", null);
                if (msGradoCursa != null) encuesta.setGradoCursa(msGradoCursa.getCatKey());
            }
            if (tieneValor(turno)){
                MessageResource msTurno = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + turno + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_TURNO'", null);
                if (msTurno != null) encuesta.setTurno(msTurno.getCatKey());
            }
            if (tieneValor(dondeCuidanNino)){
                MessageResource msdondeCuidanNino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dondeCuidanNino + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_CUIDAN_NINO'", null);
                if (msdondeCuidanNino != null) encuesta.setDondeCuidanNino(msdondeCuidanNino.getCatKey());
            }
            if (tieneValor(ninoTrabaja)){
                MessageResource msninoTrabaja = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + ninoTrabaja + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msninoTrabaja != null) encuesta.setNinoTrabaja(msninoTrabaja.getCatKey().charAt(0));
            }
            if (tieneValor(conQuienViveNino)){
                MessageResource msconQuienViveNino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + conQuienViveNino + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_VIVE_NINO'", null);
                if (msconQuienViveNino != null) encuesta.setConQuienViveNino(msconQuienViveNino.getCatKey());
            }
            if (tieneValor(padreEnEstudio)){
                MessageResource mspadreEnEstudio = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + padreEnEstudio + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (mspadreEnEstudio != null) encuesta.setPadreEnEstudio(mspadreEnEstudio.getCatKey().charAt(0));
            }
            if (tieneValor(padreAlfabeto)){
                MessageResource mspadreAlfabeto = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + padreAlfabeto + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mspadreAlfabeto != null) encuesta.setPadreAlfabeto(mspadreAlfabeto.getCatKey().charAt(0));
            }
            if (tieneValor(nivelEducacionPadre)){
                MessageResource msnivelEducacionPadre = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + nivelEducacionPadre + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_NIV_EDU'", null);
                if (msnivelEducacionPadre != null) encuesta.setNivelEducacionPadre(msnivelEducacionPadre.getCatKey());
            }
            if (tieneValor(trabajaPadre)){
                MessageResource mstrabajaPadre = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + trabajaPadre + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mstrabajaPadre != null) encuesta.setTrabajaPadre(mstrabajaPadre.getCatKey().charAt(0));
            }
            if (tieneValor(tipoTrabajoPadre)){
                MessageResource msTipoTrabajoPadre = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tipoTrabajoPadre + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_TIP_TRABAJO'", null);
                if (msTipoTrabajoPadre != null) encuesta.setTipoTrabajoPadre(msTipoTrabajoPadre.getCatKey());
            }
            if (tieneValor(madreEnEstudio)){
                MessageResource msmadreEnEstudio = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + madreEnEstudio + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msmadreEnEstudio != null) encuesta.setMadreEnEstudio(msmadreEnEstudio.getCatKey().charAt(0));
            }
            if (tieneValor(madreAlfabeto)){
                MessageResource msmadreAlfabeto = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + madreAlfabeto + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (msmadreAlfabeto != null) encuesta.setMadreAlfabeto(msmadreAlfabeto.getCatKey().charAt(0));
            }
            if (tieneValor(nivelEducacionMadre)){
                MessageResource msnivelEducacionMadre = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + nivelEducacionMadre + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_NIV_EDU'", null);
                if (msnivelEducacionMadre != null) encuesta.setNivelEducacionMadre(msnivelEducacionMadre.getCatKey());
            }
            if (tieneValor(trabajaMadre)){
                MessageResource mstrabajaMadre = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + trabajaMadre + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mstrabajaMadre != null) encuesta.setTrabajaMadre(mstrabajaMadre.getCatKey().charAt(0));
            }
            if (tieneValor(tipoTrabajoMadre)){
                MessageResource msTipoTrabajoMadre = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tipoTrabajoMadre + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_TIP_TRABAJO'", null);
                if (msTipoTrabajoMadre != null) encuesta.setTipoTrabajoMadre(msTipoTrabajoMadre.getCatKey());
            }
            if (tieneValor(fuma)){
                MessageResource msfuma = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + fuma + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msfuma != null) encuesta.setFuma(msfuma.getCatKey().charAt(0));
            }
            if (tieneValor(periodicidadFuma)){
                MessageResource msperiodicidadFuma = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + periodicidadFuma + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msperiodicidadFuma != null) encuesta.setPeriodicidadFuma(msperiodicidadFuma.getCatKey());
            }
            if (tieneValor(fumaDentroCasa)){
                MessageResource msfumaDentroCasa = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + fumaDentroCasa + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msfumaDentroCasa != null) encuesta.setFumaDentroCasa(msfumaDentroCasa.getCatKey().charAt(0));
            }
            if (tieneValor(tuberculosisPulmonarActual)){
                MessageResource mstuberculosisPulmonarActual = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tuberculosisPulmonarActual + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mstuberculosisPulmonarActual != null) encuesta.setTuberculosisPulmonarActual(mstuberculosisPulmonarActual.getCatKey().charAt(0));
            }
            //setear fechaDiagnosticoTubPulActual
            String fechaDiagnosticoCompuesta = anioFechaDiagnosticoTubPulActual;
            if (tieneValor(mesFechaDiagnosticoTubPulActual)){
                MessageResource msmesFechaDiagnosticoTubPulActual = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + mesFechaDiagnosticoTubPulActual + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                if (msmesFechaDiagnosticoTubPulActual != null){
                    fechaDiagnosticoCompuesta = msmesFechaDiagnosticoTubPulActual.getCatKey()+"/"+fechaDiagnosticoCompuesta;
                }
            }
            encuesta.setFechaDiagnosticoTubPulActual(fechaDiagnosticoCompuesta);

            if (tieneValor(tomaTratamientoTubPulActual)){
                MessageResource mstomaTratamientoTubPulActual = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tomaTratamientoTubPulActual + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mstomaTratamientoTubPulActual != null) encuesta.setTomaTratamientoTubPulActual(mstomaTratamientoTubPulActual.getCatKey().charAt(0));
            }
            if (tieneValor(completoTratamientoTubPulActual)){
                MessageResource mscompletoTratamientoTubPulActual = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + completoTratamientoTubPulActual + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mscompletoTratamientoTubPulActual != null) encuesta.setCompletoTratamientoTubPulActual(mscompletoTratamientoTubPulActual.getCatKey().charAt(0));
            }
            if (tieneValor(tuberculosisPulmonarPasado)){
                MessageResource mstuberculosisPulmonarPasado = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tuberculosisPulmonarPasado + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mstuberculosisPulmonarPasado != null) encuesta.setTuberculosisPulmonarPasado(mstuberculosisPulmonarPasado.getCatKey().charAt(0));
            }
            if (tieneValor(fechaDiagnosticoTubPulPasadoSn)){
                MessageResource msfechaDiagnosticoTubPulPasadoSn = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + fechaDiagnosticoTubPulPasadoSn + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msfechaDiagnosticoTubPulPasadoSn != null){
                    if (msfechaDiagnosticoTubPulPasadoSn.getCatKey().equalsIgnoreCase("S")) {
                        encuesta.setFechaDiagnosticoTubPulPasadoDes('N');
                        //setear fechaDiagnosticoTubPulPasado
                        String fechaDiagnosticoCompuestaPas = anioFechaDiagnosticoTubPulPasado;
                        if (tieneValor(mesFechaDiagnosticoTubPulPasado)){
                            MessageResource msmesFechaDiagnosticoTubPulPasado = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + mesFechaDiagnosticoTubPulPasado + "' and "
                                    + CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", null);
                            if (msmesFechaDiagnosticoTubPulPasado != null){
                                fechaDiagnosticoCompuestaPas = msmesFechaDiagnosticoTubPulPasado.getCatKey()+"/"+fechaDiagnosticoCompuestaPas;
                            }
                        }
                        encuesta.setFechaDiagnosticoTubPulPasado(fechaDiagnosticoCompuestaPas);
                    }
                    else
                        encuesta.setFechaDiagnosticoTubPulPasadoDes('S');

                }
            }
            if (tieneValor(tomaTratamientoTubPulPasado)){
                MessageResource mstomaTratamientoTubPulPasado = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tomaTratamientoTubPulPasado + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mstomaTratamientoTubPulPasado != null) encuesta.setTomaTratamientoTubPulPasado(mstomaTratamientoTubPulPasado.getCatKey().charAt(0));
            }
            if (tieneValor(completoTratamientoTubPulPasado)){
                MessageResource mscompletoTratamientoTubPulPasado = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + completoTratamientoTubPulPasado + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mscompletoTratamientoTubPulPasado != null) encuesta.setCompletoTratamientoTubPulPasado(mscompletoTratamientoTubPulPasado.getCatKey().charAt(0));
            }
            if (tieneValor(alergiaRespiratoria)){
                MessageResource msalergiaRespiratoria = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + alergiaRespiratoria + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (msalergiaRespiratoria != null) encuesta.setAlergiaRespiratoria(msalergiaRespiratoria.getCatKey().charAt(0));
            }
            if (tieneValor(cardiopatia)){
                MessageResource mscardiopatia = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + cardiopatia + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mscardiopatia != null) encuesta.setPeriodicidadFuma(mscardiopatia.getCatKey());
            }
            if (tieneValor(enfermedadPulmonarOC)){
                MessageResource msenfermedadPulmonarOC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfermedadPulmonarOC + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (msenfermedadPulmonarOC != null) encuesta.setEnfermedadPulmonarOC(msenfermedadPulmonarOC.getCatKey().charAt(0));
            }
            if (tieneValor(diabetes)){
                MessageResource msdiabetes = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + diabetes + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (msdiabetes != null) encuesta.setDiabetes(msdiabetes.getCatKey().charAt(0));
            }
            if (tieneValor(presionAlta)){
                MessageResource mspresionAlta = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + presionAlta + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mspresionAlta != null) encuesta.setPresionAlta(mspresionAlta.getCatKey().charAt(0));
            }
            if (tieneValor(asma)){
                MessageResource msasma = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + asma + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (msasma != null) encuesta.setAsma(msasma.getCatKey().charAt(0));
            }
            if (tieneValor(silbidoRespirarPechoApretado)){
                MessageResource mssilbidoRespirarPechoApretado = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + silbidoRespirarPechoApretado + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mssilbidoRespirarPechoApretado != null) encuesta.setSilbidoRespirarPechoApretado(mssilbidoRespirarPechoApretado.getCatKey().charAt(0));
            }
            if (tieneValor(tosSinFiebreResfriado)){
                MessageResource mstosSinFiebreResfriado = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tosSinFiebreResfriado + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mstosSinFiebreResfriado != null) encuesta.setTosSinFiebreResfriado(mstosSinFiebreResfriado.getCatKey().charAt(0));
            }
            if (tieneValor(usaInhaladoresSpray)){
                MessageResource msusaInhaladoresSpray = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + usaInhaladoresSpray + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (msusaInhaladoresSpray != null) encuesta.setUsaInhaladoresSpray(msusaInhaladoresSpray.getCatKey().charAt(0));
            }
            if (tieneValor(crisisAsma)){
                MessageResource mscrisisAsma = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + crisisAsma + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (mscrisisAsma != null) encuesta.setCrisisAsma(mscrisisAsma.getCatKey().charAt(0));
            }
            if (tieneValor(otrasEnfermedades)){
                MessageResource msotrasEnfermedades = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + otrasEnfermedades + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (msotrasEnfermedades != null) encuesta.setOtrasEnfermedades(msotrasEnfermedades.getCatKey().charAt(0));
            }
            if (tieneValor(vacunaInfluenza)){
                MessageResource msvacunaInfluenza = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + vacunaInfluenza + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", null);
                if (msvacunaInfluenza != null) encuesta.setVacunaInfluenza(msvacunaInfluenza.getCatKey().charAt(0));
            }
            //Numericos
            if (tieneValor(semanasEmbarazo)) encuesta.setSemanasEmbarazo(Integer.valueOf(semanasEmbarazo));
            if (tieneValor(cantNinosLugarCuidan)) encuesta.setCantNinosLugarCuidan(Integer.valueOf(cantNinosLugarCuidan));
            if (tieneValor(cantidadCigarrillos)) encuesta.setCantidadCigarrillos(Integer.valueOf(cantidadCigarrillos));
            if (tieneValor(cantidadCrisisAsma)) encuesta.setCantidadCrisisAsma(Integer.valueOf(cantidadCrisisAsma));
            if (tieneValor(vecesEnfermoEnfermedadesRes)) encuesta.setVecesEnfermoEnfermedadesRes(Integer.valueOf(vecesEnfermoEnfermedadesRes));
            if (tieneValor(anioVacunaInfluenza)) encuesta.setAnioVacunaInfluenza(Integer.valueOf(anioVacunaInfluenza));
            //textos
            encuesta.setDescOtrasEnfermedades(descOtrasEnfermedades);
            encuesta.setOcupacionActual(ocupacionActual);
            encuesta.setNombreCentroEstudio(nombreCentroEstudio);
            encuesta.setOcupacionActualNino(ocupacionActualNino);
            encuesta.setCodigoPadreEstudio(codigoPadreEstudio);
            encuesta.setCodigoMadreEstudio(codigoMadreEstudio);

            encuesta.setRecurso1(username);

            //Metadata
            encuesta.setRecordDate(new Date());
            encuesta.setRecordUser(username);
            encuesta.setDeviceid(infoMovil.getDeviceId());
            encuesta.setEstado('0');
            encuesta.setPasive('0');
            boolean actualizada = false;
            EncuestaParticipante encuestaExiste = estudiosAdapter.getEncuestasParticipante(EncuestasDBConstants.participante_chf + "='" + participanteCHF.getParticipanteCHF() + "'", EncuestasDBConstants.participante_chf);
            if (encuestaExiste != null && encuestaExiste.getParticipante() != null && encuestaExiste.getParticipante().getParticipanteCHF() != null)
                actualizada = estudiosAdapter.editarEncuestasParticipante(encuesta);
            else estudiosAdapter.crearEncuestasParticipante(encuesta);
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
