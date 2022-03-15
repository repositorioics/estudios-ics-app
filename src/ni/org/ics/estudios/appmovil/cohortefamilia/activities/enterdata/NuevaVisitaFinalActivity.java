package ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaVisitaFinalCasosActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.VisitaFinalCasoForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.VisitaFinalCasoFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaFinalCaso;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
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
import java.util.*;


public class NuevaVisitaFinalActivity extends FragmentActivity implements
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
    private static ParticipanteCohorteFamiliaCaso participanteChfCaso = new ParticipanteCohorteFamiliaCaso();
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private VisitaFinalCasoFormLabels labels = new VisitaFinalCasoFormLabels();

    final Calendar c = Calendar.getInstance();
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
        infoMovil = new DeviceInfo(NuevaVisitaFinalActivity.this);

        participanteChfCaso = (ParticipanteCohorteFamiliaCaso) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new VisitaFinalCasoForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);

        DateMidnight minDate = new DateMidnight(participanteChfCaso.getCodigoCaso().getFechaInicio());
        NewDatePage pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFif());
        pageFecha.setmLaterThan(minDate);
        pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFff());
        pageFecha.setmLaterThan(minDate);
        pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFitos());
        pageFecha.setmLaterThan(minDate);
        pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFftos());
        pageFecha.setmLaterThan(minDate);
        pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFigg());
        pageFecha.setmLaterThan(minDate);
        pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFfgg());
        pageFecha.setmLaterThan(minDate);
        pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFisn());
        pageFecha.setmLaterThan(minDate);
        pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFfsn());
        pageFecha.setmLaterThan(minDate);

        fechaVisita = String.valueOf(c.get(Calendar.DAY_OF_MONTH)<10? "0"+c.get(Calendar.DAY_OF_MONTH):c.get(Calendar.DAY_OF_MONTH))+"/"+
                String.valueOf((c.get(Calendar.MONTH)+1)<10? "0"+(c.get(Calendar.MONTH)+1):(c.get(Calendar.MONTH)+1))+"/"+String.valueOf(c.get(Calendar.YEAR))+" "+
                String.valueOf(c.get(Calendar.HOUR_OF_DAY)<10? "0"+c.get(Calendar.HOUR_OF_DAY):c.get(Calendar.HOUR_OF_DAY))+":"+
                String.valueOf(c.get(Calendar.MINUTE)<10? "0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE));

        mWizardModel.findByKey(labels.getFechaVisita()).setHint(fechaVisita);

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
                        if (participanteChfCaso!=null) arguments.putSerializable(Constants.PARTICIPANTE , participanteChfCaso);
                        i = new Intent(getApplicationContext(),
                                ListaVisitaFinalCasosActivity.class);
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
            if (page.getTitle().equals(labels.getEnfermo())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(Constants.YES));
                changeStatus(mWizardModel.findByKey(labels.getConsTerreno()), visible);
                changeStatus(mWizardModel.findByKey(labels.getReferidoCs()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTratamiento()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCualAntibiotico()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTratamiento())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains("Antibiótico");
                changeStatus(mWizardModel.findByKey(labels.getCualAntibiotico()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getSintResp())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(Constants.YES));
                changeStatus(mWizardModel.findByKey(labels.getTos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getFiebre()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDolorGarganta()), visible);
                changeStatus(mWizardModel.findByKey(labels.getSecrecionNasal()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFiebre())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(Constants.YES));
                changeStatus(mWizardModel.findByKey(labels.getFif()), visible);
                changeStatus(mWizardModel.findByKey(labels.getFff()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFif()) && page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null) {
                String datoFecha = page.getData().getString(TextPage.SIMPLE_DATA_KEY);
                DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateMidnight minDate = new DateMidnight(mDateFormat.parse(datoFecha));
                NewDatePage pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFff());
                pageFecha.setmLaterThan(minDate);
            }
            if (page.getTitle().equals(labels.getTos())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(Constants.YES));
                changeStatus(mWizardModel.findByKey(labels.getFitos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getFftos()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFitos()) && page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null) {
                String datoFecha = page.getData().getString(TextPage.SIMPLE_DATA_KEY);
                DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateMidnight minDate = new DateMidnight(mDateFormat.parse(datoFecha));
                NewDatePage pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFftos());
                pageFecha.setmLaterThan(minDate);
            }
            if (page.getTitle().equals(labels.getDolorGarganta())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(Constants.YES));
                changeStatus(mWizardModel.findByKey(labels.getFigg()), visible);
                changeStatus(mWizardModel.findByKey(labels.getFfgg()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFigg()) && page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null) {
                String datoFecha = page.getData().getString(TextPage.SIMPLE_DATA_KEY);
                DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateMidnight minDate = new DateMidnight(mDateFormat.parse(datoFecha));
                NewDatePage pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFfgg());
                pageFecha.setmLaterThan(minDate);
            }
            if (page.getTitle().equals(labels.getSecrecionNasal())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(Constants.YES));
                changeStatus(mWizardModel.findByKey(labels.getFisn()), visible);
                changeStatus(mWizardModel.findByKey(labels.getFfsn()), visible);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFisn()) && page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null) {
                String datoFecha = page.getData().getString(TextPage.SIMPLE_DATA_KEY);
                DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateMidnight minDate = new DateMidnight(mDateFormat.parse(datoFecha));
                NewDatePage pageFecha = (NewDatePage) mWizardModel.findByKey(labels.getFfsn());
                pageFecha.setmLaterThan(minDate);
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

        String enfermo = datos.getString(this.getString(R.string.enfermo));
        String consTerreno = datos.getString(this.getString(R.string.consTerreno));
        String referidoCs = datos.getString(this.getString(R.string.referidoCs));
        String tratamiento = datos.getString(this.getString(R.string.tratamiento));
        String cualAntibiotico = datos.getString(this.getString(R.string.cualAntibiotico));
        String sintResp = datos.getString(this.getString(R.string.sintResp));
        String fiebre = datos.getString(this.getString(R.string.fiebre));
        String tos = datos.getString(this.getString(R.string.tos));
        String dolorGarganta = datos.getString(this.getString(R.string.dolorGarganta));
        String secrecionNasal = datos.getString(this.getString(R.string.secrecionNasal));
        String fif = datos.getString(this.getString(R.string.fif));
        String fff = datos.getString(this.getString(R.string.fff));
        String fitos = datos.getString(this.getString(R.string.fitos));
        String fftos = datos.getString(this.getString(R.string.fftos));
        String figg = datos.getString(this.getString(R.string.figg));
        String ffgg = datos.getString(this.getString(R.string.ffgg));
        String fisn = datos.getString(this.getString(R.string.fisn));
        String ffsn = datos.getString(this.getString(R.string.ffsn));

        //Crea un nueva visita final
        VisitaFinalCaso vfc = new VisitaFinalCaso();
        vfc.setCodigoParticipanteCaso(participanteChfCaso);
        DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        boolean procesarVisita = true;
        try {
            vfc.setFechaVisita(mDateFormat.parse(fechaVisita));
            DateFormat mDateFormatLim = new SimpleDateFormat("dd/MM/yyyy");
            Date dVis = mDateFormatLim.parse(fechaVisita);
            Calendar calLimiteFecVisita = Calendar.getInstance();
            calLimiteFecVisita.setTime(participanteChfCaso.getCodigoCaso().getFechaInicio());
            calLimiteFecVisita.add(Calendar.DATE,60);//60 dias después de la fecha de ingreso
            if (dVis.after(calLimiteFecVisita.getTime())){//si la fecha de visita es posterior a los 60 dias después de la fecha de inicio no permitir registro
                Toast.makeText(this,this.getString(R.string.wrong_fecha_visita3),Toast.LENGTH_LONG).show();
                procesarVisita = false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (procesarVisita) {
            if (tieneValor(enfermo)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfermo + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                vfc.setEnfermo(catSino.getCatKey());
            }
            if (tieneValor(consTerreno)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + consTerreno + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                vfc.setConsTerreno(catSino.getCatKey());
            }
            if (tieneValor(referidoCs)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + referidoCs + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                vfc.setReferidoCs(catSino.getCatKey());
            }
            if (tieneValor(tratamiento)) {
                String catKeys = "";
                tratamiento = tratamiento.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','");
                List<MessageResource> mCatTipo = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + tratamiento + "') and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_TRATAMIENTO'", null);
                for (MessageResource ms : mCatTipo) {
                    catKeys += ms.getCatKey() + ",";
                }
                if (!catKeys.isEmpty())
                    catKeys = catKeys.substring(0, catKeys.length() - 1);
                vfc.setTratamiento(catKeys);
            }
            if (tieneValor(cualAntibiotico)) {
                vfc.setCualAntibiotico(cualAntibiotico);
            }
            if (tieneValor(sintResp)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + sintResp + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                vfc.setSintResp(catSino.getCatKey());
            }

            mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (tieneValor(fiebre)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + fiebre + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                vfc.setFiebre(catSino.getCatKey());
            }
            if (tieneValor(fif)) {
                try {
                    vfc.setFif(mDateFormat.parse(fif));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (tieneValor(fff)) {
                try {
                    vfc.setFff(mDateFormat.parse(fff));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (tieneValor(tos)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tos + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                vfc.setTos(catSino.getCatKey());
            }
            if (tieneValor(fitos)) {
                try {
                    vfc.setFitos(mDateFormat.parse(fitos));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (tieneValor(fftos)) {
                try {
                    vfc.setFftos(mDateFormat.parse(fftos));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (tieneValor(dolorGarganta)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dolorGarganta + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                vfc.setDolorGarganta(catSino.getCatKey());
            }
            if (tieneValor(figg)) {
                try {
                    vfc.setFigg(mDateFormat.parse(figg));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (tieneValor(ffgg)) {
                try {
                    vfc.setFfgg(mDateFormat.parse(ffgg));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (tieneValor(secrecionNasal)) {
                MessageResource catSino = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + secrecionNasal + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                vfc.setSecrecionNasal(catSino.getCatKey());
            }
            if (tieneValor(fisn)) {
                try {
                    vfc.setFisn(mDateFormat.parse(fisn));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (tieneValor(ffsn)) {
                try {
                    vfc.setFfsn(mDateFormat.parse(ffsn));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            vfc.setRecordDate(new Date());
            vfc.setRecordUser(username);
            vfc.setDeviceid(infoMovil.getDeviceId());
            vfc.setEstado('0');
            vfc.setPasive('0');

            //Guarda el contacto
            try {
                estudiosAdapter.crearVisitaFinalCaso(vfc);

                //vamos a activar los procesos de toma de MA 2022
                MovilInfo movilInfo = new MovilInfo();
                movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                movilInfo.setDeviceid(infoMovil.getDeviceId());
                movilInfo.setUsername(username);
                movilInfo.setToday(new Date());
                ParticipanteProcesos procesos = participanteChfCaso.getParticipante().getParticipante().getProcesos();
                procesos.setMovilInfo(movilInfo);
                String estudios = procesos.getEstudio().replaceAll("  Tcovid","");
                int edadMeses = participanteChfCaso.getParticipante().getParticipante().getEdadMeses();
                if (edadMeses < 6) {
                    procesos.setConmxbhc(Constants.YES); //No habilitar
                } else if (edadMeses < 24) {
                    //No habilitar para UO1 y UO1  CH Familia
                    if (estudios.equalsIgnoreCase("UO1") || estudios.equalsIgnoreCase("UO1  CH Familia")) {
                        procesos.setConmxbhc(Constants.YES);
                    } else {
                        procesos.setConmxbhc(Constants.NO);
                    }
                } else {//para el resto.. siempre habilitar
                    procesos.setConmxbhc(Constants.NO);
                }
                //rojo siempre habilitar
                procesos.setConmx(Constants.NO);
                estudiosAdapter.actualizarParticipanteProcesos(procesos);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            estudiosAdapter.close();
            Bundle arguments = new Bundle();
            Intent i;
            if (participanteChfCaso != null) arguments.putSerializable(Constants.PARTICIPANTE, participanteChfCaso);
            i = new Intent(getApplicationContext(),
                    ListaVisitaFinalCasosActivity.class);
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
