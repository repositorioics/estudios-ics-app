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
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaVacunaUO1;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.list.ListaVisitasVacunaUO1Activity;
import ni.org.ics.estudios.appmovil.influenzauo1.forms.VisitaVacunaUO1Form;
import ni.org.ics.estudios.appmovil.influenzauo1.forms.VisitaVacunaUO1FormLabels;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;
import org.joda.time.DateMidnight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class NuevaVisitaVacunaUO1Activity extends FragmentActivity implements
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
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static Participante participante = new Participante();
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private VisitaVacunaUO1FormLabels labels = new VisitaVacunaUO1FormLabels();

    final Calendar calendar = Calendar.getInstance();
    private String fechaVisita;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FileUtils.storageReady()) {
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        setContentView(R.layout.activity_data_enter);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevaVisitaVacunaUO1Activity.this);

        participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new VisitaVacunaUO1Form(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);

        fechaVisita = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)<10? "0"+ calendar.get(Calendar.DAY_OF_MONTH): calendar.get(Calendar.DAY_OF_MONTH))+"/"+
                String.valueOf((calendar.get(Calendar.MONTH)+1)<10? "0"+(calendar.get(Calendar.MONTH)+1):(calendar.get(Calendar.MONTH)+1))+"/"+String.valueOf(calendar.get(Calendar.YEAR))+" "+
                String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)<10? "0"+ calendar.get(Calendar.HOUR_OF_DAY): calendar.get(Calendar.HOUR_OF_DAY))+":"+
                String.valueOf(calendar.get(Calendar.MINUTE)<10? "0"+ calendar.get(Calendar.MINUTE): calendar.get(Calendar.MINUTE));

        mWizardModel.findByKey(labels.getFechaVisita()).setHint(fechaVisita);
        DateMidnight minDate = new DateMidnight(participante.getFechaNac());

        NewDatePage pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFechaVacuna());
        pageFecha.setmLaterThan(minDate);
        pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFechaSegundaDosis());
        pageFecha.setmLaterThan(minDate);
        //fecha de repogramación dentro de 15 dias min. Reprograme cita para toma de muestra en 2 a 5 semanas.
        calendar.add(Calendar.WEEK_OF_YEAR, 2);
        minDate = new DateMidnight(calendar.getTime());
        calendar.add(Calendar.WEEK_OF_YEAR, 3); //40 dias max
        DateMidnight maxDate = new DateMidnight(calendar.getTime());
        pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFechaReprogramacionTomaMx());
        pageFecha.setRangeValidation(true, minDate, maxDate);

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
                        Bundle arguments = new Bundle();
                        Intent i;
                        if (participante !=null) arguments.putSerializable(Constants.PARTICIPANTE , participante);
                        i = new Intent(getApplicationContext(),
                                ListaVisitasVacunaUO1Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.err_cancel),Toast.LENGTH_LONG);
                        toast.show();
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
            if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NumberPage")) {
                NumberPage np = (NumberPage) page;
                String valor = np.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if((np.ismValRange() && (np.getmGreaterOrEqualsThan() > Integer.valueOf(valor) || np.getmLowerOrEqualsThan() < Integer.valueOf(valor)))
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
            if (page.getTitle().equals(labels.getVisita())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals("Inicial"));
                changeStatus(mWizardModel.findByKey(labels.getVacuna()), visible);
                changeStatus(mWizardModel.findByKey(labels.getFechaVacuna()), false);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTomaMxAntes()), visible);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoTomaMx()), false);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getVisitaExitosa()), !visible);
                changeStatus(mWizardModel.findByKey(labels.getRazonVisitaFallida()), false);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazon()), false);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getSegundaDosis()), !visible);
                changeStatus(mWizardModel.findByKey(labels.getFechaSegundaDosis()), false);
                changeStatus(mWizardModel.findByKey(labels.getReprogramarTomaMx()), false);
                changeStatus(mWizardModel.findByKey(labels.getFechaReprogramacionTomaMx()), false);
                notificarCambios = false;

                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getVisitaExitosa())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getRazonVisitaFallida()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazon()), false);
                changeStatus(mWizardModel.findByKey(labels.getSegundaDosis()), !visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getRazonVisitaFallida())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals("Otra razon"));
                changeStatus(mWizardModel.findByKey(labels.getOtraRazon()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getVacuna())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(Constants.YES));
                changeStatus(mWizardModel.findByKey(labels.getFechaVacuna()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTomaMxAntes())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(Constants.NO));
                changeStatus(mWizardModel.findByKey(labels.getRazonNoTomaMx()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getSegundaDosis())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(Constants.YES));
                changeStatus(mWizardModel.findByKey(labels.getFechaSegundaDosis()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getReprogramarTomaMx()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getFechaReprogramacionTomaMx()), visible);
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
            LabelPage modifPage = (LabelPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
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

    public void saveData(){
        Map<String, String> mapa = mWizardModel.getAnswers();
        //Guarda las respuestas en un bundle
        Bundle datos = new Bundle();
        for (Map.Entry<String, String> entry : mapa.entrySet()){
            datos.putString(entry.getKey(), entry.getValue());
        }

        //Abre la base de datos
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
        estudiosAdapter.open();

        String visita = datos.getString(this.getString(R.string.visitaUO1));
        String visitaExitosa = datos.getString(this.getString(R.string.visitaExitosaUO1));
        String razonVisitaFallida = datos.getString(this.getString(R.string.razonVisitaFallidaUO1));
        String otraRazon = datos.getString(this.getString(R.string.otraRazonUO1));
        String vacunaInfluenza = datos.getString(this.getString(R.string.administraVacFluUO1));
        String fechaVacuna = datos.getString(this.getString(R.string.fechaVacunaUO1));
        String tomamxAntesVacuna = datos.getString(this.getString(R.string.tomamxAntesVacuna));
        String razonNoTomamxAntes = datos.getString(this.getString(R.string.razonNoTomamxAntes));
        String segundaDosis = datos.getString(this.getString(R.string.segundaDosis));
        String fechaVacunaSegundaDosis = datos.getString(this.getString(R.string.fechaVacunaSegundaDosis));
        String fechaReprogramacionTomaMx = datos.getString(this.getString(R.string.fechaReprogramacionTomaMx));

        //Crea un nueva visita final
        VisitaVacunaUO1 visitaVacunaUO1 = new VisitaVacunaUO1();
        visitaVacunaUO1.setParticipante(participante);
        DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        boolean procesarVisita = true;
        try {
            visitaVacunaUO1.setFechaVisita(mDateFormat.parse(fechaVisita));
            DateFormat mDateFormatLim = new SimpleDateFormat("dd/MM/yyyy");
            /*if (visita.equalsIgnoreCase("Final")) {
                Date dVis = mDateFormatLim.parse(fechaVisita);
                Calendar calLimiteFecVisita = Calendar.getInstance();
                calLimiteFecVisita.setTime(participante.getFechaIngreso());
                calLimiteFecVisita.add(Calendar.DATE, 45);//60 dias después de la fecha de ingreso
                if (dVis.after(calLimiteFecVisita.getTime())) {//si la fecha de visita es posterior a los 60 dias después de la fecha de inicio no permitir registro
                    Toast.makeText(this, this.getString(R.string.wrong_visit_date_OU1), Toast.LENGTH_LONG).show();
                    procesarVisita = false;
                }

            }
            */

            List<VisitaVacunaUO1> mVisitasCasos = estudiosAdapter.getVisitasVacunasUO1(InfluenzaUO1DBConstants.participante + " = " + participante.getCodigo() + " and visitaExitosa = '1' ",
                    InfluenzaUO1DBConstants.fechaVisita + " desc ");
            Date dVis = mDateFormatLim.parse(fechaVisita);
            if (mVisitasCasos.size()>0){
                VisitaVacunaUO1 ultimaVisita = mVisitasCasos.get(0);
                Calendar calLimiteFecVisita = Calendar.getInstance();
                calLimiteFecVisita.setTime(ultimaVisita.getFechaVisita());
                if (visita.equalsIgnoreCase("Inicial")){
                    if (ultimaVisita.getVisita().equalsIgnoreCase("I")){
                        calLimiteFecVisita.add(Calendar.DATE, 30);//30 dias después de la fecha de otra visita inicial
                        if (dVis.before(calLimiteFecVisita.getTime())) {//si la fecha de visita es anterior a los 30 dias después de la fecha de ultima visita inicial no permitir registro
                            Toast.makeText(this, this.getString(R.string.wrong_visit_date_OU1_1), Toast.LENGTH_LONG).show();
                            procesarVisita = false;
                        }
                    }
                }else if (visita.equalsIgnoreCase("Final")){
                    if (ultimaVisita.getVisita().equalsIgnoreCase("I")){
                        calLimiteFecVisita.setTime(ultimaVisita.getFechaVacuna()!=null?ultimaVisita.getFechaVacuna():ultimaVisita.getFechaVisita());
                        String nombreCampo = ultimaVisita.getFechaVacuna()!=null?"Primera dosis":"Visita Inicial";
                        //fecha minima para ingresar visita final
                        calLimiteFecVisita.add(Calendar.DATE, 29);//29 dias después de la fecha de visita inicial. Se solicita bajar a 29 Dr. Plaza, 26052020
                        if (dVis.before(calLimiteFecVisita.getTime())) {//si la fecha de visita no es posterior a los 30 dias después de la fecha de inicio no permitir registro
                            Toast.makeText(this, this.getString(R.string.wrong_visit_date_OU1_2, nombreCampo), Toast.LENGTH_LONG).show();
                            procesarVisita = false;
                        }
                        //fecha limite para ingresar visita final
                        calLimiteFecVisita.add(Calendar.DATE, 45);//45 dias después de la fecha de visita inicial
                        if (dVis.after(calLimiteFecVisita.getTime())) {//si la fecha de visita es posterior a los 45 dias después de la fecha de inicio no permitir registro
                            Toast.makeText(this, this.getString(R.string.wrong_visit_date_OU1_3, nombreCampo), Toast.LENGTH_LONG).show();
                            procesarVisita = false;
                        }
                    }else{
                        if (ultimaVisita.getFechaReprogramacion()==null){
                            Toast.makeText(this, this.getString(R.string.wrong_visit_date_OU1_4), Toast.LENGTH_LONG).show();
                            procesarVisita = false;
                        }
                    }
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (procesarVisita) {
            if (tieneValor(visita)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + visita + "' and " + CatalogosDBConstants.catRoot + "='UO1_CAT_VISITA'", null);
                visitaVacunaUO1.setVisita(catSino.getCatKey());
            }
            if (tieneValor(visitaExitosa)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + visitaExitosa + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                visitaVacunaUO1.setVisitaExitosa(catSino.getCatKey());
            }else {
                if (visita.equalsIgnoreCase("Inicial"))
                    visitaVacunaUO1.setVisitaExitosa(Constants.YESKEYSND);
            }
            if (tieneValor(razonVisitaFallida)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonVisitaFallida + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_VISITA_NO_P'", null);
                visitaVacunaUO1.setRazonVisitaFallida(catSino.getCatKey());
            }
            visitaVacunaUO1.setOtraRazon(otraRazon);
            if (tieneValor(tomamxAntesVacuna)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tomamxAntesVacuna + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                visitaVacunaUO1.setTomaMxAntes(catSino.getCatKey());
            }
            visitaVacunaUO1.setRazonNoTomaMx(razonNoTomamxAntes);

            mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (tieneValor(vacunaInfluenza)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + vacunaInfluenza + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                visitaVacunaUO1.setVacuna(catSino.getCatKey());
            }
            if (tieneValor(fechaVacuna)) {
                try {
                    visitaVacunaUO1.setFechaVacuna(mDateFormat.parse(fechaVacuna));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (tieneValor(segundaDosis)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + segundaDosis + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                visitaVacunaUO1.setSegundaDosis(catSino.getCatKey());
            }
            if (tieneValor(fechaVacunaSegundaDosis)) {
                try {
                    visitaVacunaUO1.setFechaSegundaDosis(mDateFormat.parse(fechaVacunaSegundaDosis));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (tieneValor(fechaReprogramacionTomaMx)){
                try {
                    visitaVacunaUO1.setFechaReprogramacion(mDateFormat.parse(fechaReprogramacionTomaMx));
                }catch (ParseException e){
                    e.printStackTrace();
                }
            }

            visitaVacunaUO1.setRecordDate(new Date());
            visitaVacunaUO1.setRecordUser(username);
            visitaVacunaUO1.setDeviceid(infoMovil.getDeviceId());
            visitaVacunaUO1.setEstado('0');
            visitaVacunaUO1.setPasive('0');
            visitaVacunaUO1.setCodigoVisita(infoMovil.getId());
            //Guarda el contacto
            try {
                estudiosAdapter.crearVisitaVacunaUO1(visitaVacunaUO1);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            estudiosAdapter.close();
            Bundle arguments = new Bundle();
            Intent i;
            if (participante != null) arguments.putSerializable(Constants.PARTICIPANTE, participante);
            i = new Intent(getApplicationContext(),
                    ListaVisitasVacunaUO1Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtras(arguments);
            startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
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
