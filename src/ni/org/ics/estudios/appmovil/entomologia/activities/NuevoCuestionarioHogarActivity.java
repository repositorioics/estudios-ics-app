package ni.org.ics.estudios.appmovil.entomologia.activities;

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
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogar;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogarPoblacion;
import ni.org.ics.estudios.appmovil.entomologia.forms.CuestionarioHogarForm;
import ni.org.ics.estudios.appmovil.entomologia.forms.CuestionarioHogarFormLabels;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Miguel Salinas on 29/09/2020.
 * V1.0
 */
public class NuevoCuestionarioHogarActivity extends FragmentActivity implements
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

    private CuestionarioHogarFormLabels labels;
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static Casa casa = new Casa();
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;

    final Calendar c = Calendar.getInstance();
    private int totalPoblacion = 0;
    private int totalPersonasViven = 0;
    private List<String> poblacionMujeres;
    private List<String> poblacionHombres;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_enter);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevoCuestionarioHogarActivity.this);
        casa = (Casa) getIntent().getExtras().getSerializable(Constants.CASA);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new CuestionarioHogarForm(this, mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        labels = new CuestionarioHogarFormLabels();

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
                        openBuscarCasa();
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
        boolean iniciaLabel = false;
        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            Page paginaActual = mCurrentPageSequence.get(position);
            iniciaLabel = position == 0 && paginaActual instanceof LabelPage;

            mNextButton.setText(mEditingAfterReview
                    ? R.string.review
                    : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled((position != mPagerAdapter.getCutOffPage()) || iniciaLabel);
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
                if (tp.getTitle().equals(labels.getEdadMujeres())) {
                    String valor = tp.getData().getString(TextPage.SIMPLE_DATA_KEY);
                    String[] valores = valor.split(",");
                    boolean errorEncontrado = false;
                    for(String mujer : valores) {
                        if (!mujer.trim().matches("^F\\d?\\d$")) {
                            //Toast.makeText( this.getApplicationContext(),this.getString(R.string.errorFormatoEdad, mujer), Toast.LENGTH_LONG).show();
                            cutOffPage = i;
                            errorEncontrado = true;
                            break;
                        }
                    }
                    //se valida si la respuesta al total de personas que viven en la casa no es válido, respecto a las edad de la poblacion de hombres y mujeres
                    if (totalPersonasViven < totalPoblacion) {
                        cutOffPage = i;
                        errorEncontrado = true;
                    }
                    if (errorEncontrado) break;
                }
                if (tp.getTitle().equals(labels.getEdadHombres())) {
                    String valor = tp.getData().getString(TextPage.SIMPLE_DATA_KEY);
                    String[] valores = valor.split(",");
                    boolean errorEncontrado = false;
                    for(String hombre : valores) {
                        if (!hombre.trim().matches("^M\\d?\\d$")) {
                            //Toast.makeText( this.getApplicationContext(),this.getString(R.string.errorFormatoEdad, hombre), Toast.LENGTH_LONG).show();
                            cutOffPage = i;
                            errorEncontrado = true;
                            break;
                        }
                    }
                    //se valida si la respuesta al total de personas que viven en la casa no es válido, respecto a las edad de la poblacion de hombres y mujeres
                    if (totalPersonasViven < totalPoblacion) {
                        cutOffPage = i;
                        errorEncontrado = true;
                    }

                    if (errorEncontrado) break;
                }
            }
            if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.BarcodePage")) {
                BarcodePage tp = (BarcodePage) page;
                if (tp.ismValPattern()) {
                    String valor = tp.getData().getString(BarcodePage.SIMPLE_DATA_KEY);
                    if(!valor.matches(tp.getmPattern())){
                        Toast.makeText( this.getApplicationContext(),R.string.error1CodigoMx, Toast.LENGTH_LONG).show();
                        cutOffPage = i;
                        break;
                    } else {
                        String codigoTmp = valor;
                        if (valor.contains(".")){
                            codigoTmp = valor.substring(0,valor.indexOf(".",0));
                        }
                        if (!codigoTmp.equalsIgnoreCase(casa.getCodigo().toString())){
                            Toast.makeText( this.getApplicationContext(),this.getString(R.string.error2CodigoMx,
                                    casa.getCodigo().toString()), Toast.LENGTH_LONG).show();
                            cutOffPage = i;
                            break;
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

    private void setPoblacion(boolean mujeres, String valores) {
        if (mujeres) {
            poblacionMujeres = new ArrayList<>();
            for(String mujer : valores.split(",")) {
                if (!mujer.trim().equalsIgnoreCase("F0") && mujer.trim().matches("^F\\d?\\d$")) {
                    poblacionMujeres.add(mujer);
                }
            }
        } else {
            poblacionHombres = new ArrayList<>();
            for(String hombre : valores.split(",")) {
                if (!hombre.trim().equalsIgnoreCase("M0") && hombre.trim().matches("^M\\d?\\d$")) {
                    poblacionHombres.add(hombre);
                }
            }
        }
    }

    private String[] getPoblacion() {
        /*String[] todaPoblacion = new String[(poblacionMujeres != null ? poblacionMujeres.length : 0) + (poblacionHombres != null ? poblacionHombres.length : 0)];
        int indice = 0;
        if (poblacionMujeres != null) {
            for (String mujer : poblacionMujeres) {
                if (!mujer.trim().equalsIgnoreCase("F0") && mujer.trim().matches("^F\\d?\\d$")) {
                    todaPoblacion[indice] = mujer.trim();
                    ++indice;
                }
            }
        }
        if (poblacionHombres != null) {
            for (String hombre : poblacionHombres) {
                if (!hombre.trim().equalsIgnoreCase("M0") && hombre.trim().matches("^M\\d?\\d$")) {
                    todaPoblacion[indice] = hombre.trim();
                    ++indice;
                }
            }
        }
        totalPoblacion = indice;*/
        List<String> todaPoblacion = new ArrayList<>();

        if (poblacionMujeres != null) todaPoblacion.addAll(poblacionMujeres);
        if (poblacionHombres != null) todaPoblacion.addAll(poblacionHombres);
        totalPoblacion = todaPoblacion.size();
        return todaPoblacion.toArray(new String[0]);
    }

    public void updateModel(Page page){
        try {
            boolean visible = false;
            if (page.getTitle().equals(labels.getQuienContesta())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getQuienContestaOtro()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCuantasPersonasViven())) {
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null) {
                    totalPersonasViven = Integer.parseInt(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEdadMujeres())) {
                setPoblacion(true, page.getData().getString(TextPage.SIMPLE_DATA_KEY));
                String[] todaPoblacion = getPoblacion();

                SingleFixedChoicePage pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getUsaronMosquitero());
                if (pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES)) {
                    MultipleFixedChoicePage pagetmp2 = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienesUsaronMosquitero());
                    pagetmp2.setChoices(todaPoblacion);
                }
                pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getUsaronRepelente());
                if (pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES)) {
                    MultipleFixedChoicePage pagetmp2 = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienesUsaronRepelente());
                    pagetmp2.setChoices(todaPoblacion);
                }
                pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAlguienParticipo());
                if (pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES)) {
                    MultipleFixedChoicePage pagetmp2 = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienParticipo());
                    pagetmp2.setChoices(todaPoblacion);
                }
                pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAlguienDedicaElimLarvasCasa());
                if (pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES)) {
                    MultipleFixedChoicePage pagetmp2 = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienDedicaElimLarvasCasa());
                    pagetmp2.setChoices(todaPoblacion);
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getEdadHombres())) {
                setPoblacion(false, page.getData().getString(TextPage.SIMPLE_DATA_KEY));
                String[] todaPoblacion = getPoblacion();

                SingleFixedChoicePage pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getUsaronMosquitero());
                if (pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES)) {
                    MultipleFixedChoicePage pagetmp2 = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienesUsaronMosquitero());
                    pagetmp2.setChoices(todaPoblacion);
                }
                pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getUsaronRepelente());
                if (pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES)) {
                    MultipleFixedChoicePage pagetmp2 = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienesUsaronRepelente());
                    pagetmp2.setChoices(todaPoblacion);
                }
                pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAlguienParticipo());
                if (pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES)) {
                    MultipleFixedChoicePage pagetmp2 = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienParticipo());
                    pagetmp2.setChoices(todaPoblacion);
                }
                pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAlguienDedicaElimLarvasCasa());
                if (pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES)) {
                    MultipleFixedChoicePage pagetmp2 = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienDedicaElimLarvasCasa());
                    pagetmp2.setChoices(todaPoblacion);
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getUsaronMosquitero())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getQuienesUsaronMosquitero()), visible);
                if (visible) {
                    MultipleFixedChoicePage pagetmp = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienesUsaronMosquitero());
                    pagetmp.setChoices(getPoblacion());
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getUsaronRepelente())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getQuienesUsaronRepelente()), visible);
                if (visible) {
                    MultipleFixedChoicePage pagetmp = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienesUsaronRepelente());
                    pagetmp.setChoices(getPoblacion());
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlguienVisEliminarLarvas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getQuienVisEliminarLarvas()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getQuienVisEliminarLarvas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getQuienVisEliminarLarvasOtro()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlguienDedicaElimLarvasCasa())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getQuienDedicaElimLarvasCasa()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTiempoElimanCridaros()), visible);
                if (visible) {
                    MultipleFixedChoicePage pagetmp = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienDedicaElimLarvasCasa());
                    pagetmp.setChoices(getPoblacion());
                }
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getQueFaltaCasaEvitarZancudos())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains("Otro (Escribir)");
                changeStatus(mWizardModel.findByKey(labels.getQueFaltaCasaEvitarZancudosOtros()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getGastaronDineroProductos())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getQueProductosCompraron()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCuantoGastaron()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getQueProductosCompraron())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains("Otro (Escribir)");
                changeStatus(mWizardModel.findByKey(labels.getQueProductosCompraronOtros()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getProblemasAbastecimiento())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCadaCuantoVaAgua()), visible);
                changeStatus(mWizardModel.findByKey(labels.getHorasSinAguaDia()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCadaCuantoVaAgua())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getCadaCuantoVaAguaOtro()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getQueHacenBasuraHogar())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getQueHacenBasuraHogarOtro()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAccionesCriaderosBarrio())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getQueAcciones()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAlguienParticipo()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getQueAcciones())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains("Otro");
                changeStatus(mWizardModel.findByKey(labels.getQueAccionesOtro()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlguienParticipo())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getQuienParticipo()), visible);
                if (visible) {
                    MultipleFixedChoicePage pagetmp = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getQuienParticipo());
                    pagetmp.setChoices(getPoblacion());
                }
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
            String quienContesta = datos.getString(this.getString(R.string.quienContesta));
            String quienContestaOtro = datos.getString(this.getString(R.string.quienContestaOtro));
            String edadContest = datos.getString(this.getString(R.string.edadContest));
            String escolaridadContesta = datos.getString(this.getString(R.string.escolaridadContesta));
            String tiempoVivirBarrio = datos.getString(this.getString(R.string.tiempoVivirBarrio));
            String cuantasPersonasViven = datos.getString(this.getString(R.string.cuantasPersonasViven));
            String edadMujeres = datos.getString(this.getString(R.string.edadMujeres));
            String edadHombres = datos.getString(this.getString(R.string.edadHombres));
            String usaronMosquitero = datos.getString(this.getString(R.string.usaronMosquitero));
            String quienesUsaronMosquitero = datos.getString(this.getString(R.string.quienesUsaronMosquitero));
            String usaronRepelente = datos.getString(this.getString(R.string.usaronRepelente));
            String quienesUsaronRepelente = datos.getString(this.getString(R.string.quienesUsaronRepelente));
            String conoceLarvas = datos.getString(this.getString(R.string.conoceLarvasEnto));
            String alguienVisEliminarLarvas = datos.getString(this.getString(R.string.alguienVisEliminarLarvas));
            String quienVisEliminarLarvas = datos.getString(this.getString(R.string.quienVisEliminarLarvas));
            String quienVisEliminarLarvasOtro = datos.getString(this.getString(R.string.quienVisEliminarLarvasOtro));
            String alguienDedicaElimLarvasCasa = datos.getString(this.getString(R.string.alguienDedicaElimLarvasCasa));
            String quienDedicaElimLarvasCasa = datos.getString(this.getString(R.string.quienDedicaElimLarvasCasa));
            String tiempoElimanCridaros = datos.getString(this.getString(R.string.tiempoElimanCridaros));
            String hayBastanteZancudos = datos.getString(this.getString(R.string.hayBastanteZancudos));
            String queFaltaCasaEvitarZancudos = datos.getString(this.getString(R.string.queFaltaCasaEvitarZancudos));
            String queFaltaCasaEvitarZancudosOtros = datos.getString(this.getString(R.string.queFaltaCasaEvitarZancudosOtros));
            String gastaronDineroProductos = datos.getString(this.getString(R.string.gastaronDineroProductos));
            String queProductosCompraron = datos.getString(this.getString(R.string.queProductosCompraron));
            String queProductosCompraronOtros = datos.getString(this.getString(R.string.queProductosCompraronOtros));
            String cuantoGastaron = datos.getString(this.getString(R.string.cuantoGastaron));
            String ultimaVisitaMinsaBTI = datos.getString(this.getString(R.string.ultimaVisitaMinsaBTI));
            String ultimaVezMinsaFumigo = datos.getString(this.getString(R.string.ultimaVezMinsaFumigo));
            String riesgoCasaDengue = datos.getString(this.getString(R.string.riesgoCasaDengue));
            String problemasAbastecimiento = datos.getString(this.getString(R.string.problemasAbastecimiento));
            String cadaCuantoVaAgua = datos.getString(this.getString(R.string.cadaCuantoVaAgua));
            String cadaCuantoVaAguaOtro = datos.getString(this.getString(R.string.cadaCuantoVaAguaOtro));
            String horasSinAguaDia = datos.getString(this.getString(R.string.horasSinAguaDia));
            String queHacenBasuraHogar = datos.getString(this.getString(R.string.queHacenBasuraHogar));
            String queHacenBasuraHogarOtro = datos.getString(this.getString(R.string.queHacenBasuraHogarOtro));
            String riesgoBarrioDengue = datos.getString(this.getString(R.string.riesgoBarrioDengue));
            String accionesCriaderosBarrio = datos.getString(this.getString(R.string.accionesCriaderosBarrio));
            String queAcciones = datos.getString(this.getString(R.string.queAcciones));
            String queAccionesOtro = datos.getString(this.getString(R.string.queAccionesOtro));
            String alguienParticipo = datos.getString(this.getString(R.string.alguienParticipo));
            String quienParticipo = datos.getString(this.getString(R.string.quienParticipo));
            String mayorCriaderoBarrio = datos.getString(this.getString(R.string.mayorCriaderoBarrio));

            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            CuestionarioHogar cuestionarioHogar = new CuestionarioHogar();
            cuestionarioHogar.setCodigoEncuesta(infoMovil.getId());
            cuestionarioHogar.setCodigoCasa(casa.getCodigo());
            cuestionarioHogar.setRecordDate(new Date());
            cuestionarioHogar.setRecordUser(username);
            cuestionarioHogar.setDeviceid(infoMovil.getDeviceId());
            cuestionarioHogar.setEstado('0');
            cuestionarioHogar.setPasive('0');

            //listas
            if (tieneValor(quienContesta)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + quienContesta + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P01'", null);
                if (cat != null) cuestionarioHogar.setQuienContesta(cat.getCatKey());
            }

            if (tieneValor(escolaridadContesta)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + escolaridadContesta + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P03'", null);
                if (cat != null) cuestionarioHogar.setEscolaridadContesta(cat.getCatKey());
            }

            if (tieneValor(tiempoVivirBarrio)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tiempoVivirBarrio + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P04'", null);
                if (cat != null) cuestionarioHogar.setTiempoVivirBarrio(cat.getCatKey());
            }

            if (tieneValor(usaronMosquitero)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + usaronMosquitero + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (cat != null) cuestionarioHogar.setUsaronMosquitero(cat.getCatKey());
            }

            if (tieneValor(usaronRepelente)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + usaronRepelente + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (cat != null) cuestionarioHogar.setUsaronRepelente(cat.getCatKey());
            }

            if (tieneValor(conoceLarvas)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + conoceLarvas + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (cat != null) cuestionarioHogar.setConoceLarvas(cat.getCatKey());
            }

            if (tieneValor(alguienVisEliminarLarvas)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + alguienVisEliminarLarvas + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (cat != null) cuestionarioHogar.setAlguienVisEliminarLarvas(cat.getCatKey());
            }

            if (tieneValor(quienVisEliminarLarvas)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + quienVisEliminarLarvas + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P12'", null);
                if (cat != null) cuestionarioHogar.setQuienVisEliminarLarvas(cat.getCatKey());
            }

            if (tieneValor(alguienDedicaElimLarvasCasa)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + alguienDedicaElimLarvasCasa + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (cat != null) cuestionarioHogar.setAlguienDedicaElimLarvasCasa(cat.getCatKey());
            }

            if (tieneValor(tiempoElimanCridaros)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + tiempoElimanCridaros + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P15'", null);
                if (cat != null) cuestionarioHogar.setTiempoElimanCridaros(cat.getCatKey());
            }

            if (tieneValor(hayBastanteZancudos)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + hayBastanteZancudos + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (cat != null) cuestionarioHogar.setHayBastanteZancudos(cat.getCatKey());
            }

            if (tieneValor(queFaltaCasaEvitarZancudos)){
                String keys = "";
                queFaltaCasaEvitarZancudos = queFaltaCasaEvitarZancudos.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msParedes = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + queFaltaCasaEvitarZancudos + "') and " + CatalogosDBConstants.catRoot + "='ENTO_CAT_P17'", null);
                for(MessageResource ms : msParedes) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty()) keys = keys.substring(0, keys.length() - 1);

                cuestionarioHogar.setQueFaltaCasaEvitarZancudos(keys);
            }

            if (tieneValor(gastaronDineroProductos)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + gastaronDineroProductos + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (cat != null) cuestionarioHogar.setGastaronDineroProductos(cat.getCatKey());
            }

            if (tieneValor(queProductosCompraron)){
                String keys = "";
                queProductosCompraron = queProductosCompraron.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msParedes = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + queProductosCompraron + "') and " + CatalogosDBConstants.catRoot + "='ENTO_CAT_P19'", null);
                for(MessageResource ms : msParedes) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty()) keys = keys.substring(0, keys.length() - 1);
                cuestionarioHogar.setQueProductosCompraron(keys);
            }

            if (tieneValor(cuantoGastaron)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + cuantoGastaron + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P20'", null);
                if (cat != null) cuestionarioHogar.setCuantoGastaron(cat.getCatKey());
            }

            if (tieneValor(ultimaVisitaMinsaBTI)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + ultimaVisitaMinsaBTI + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P21'", null);
                if (cat != null) cuestionarioHogar.setUltimaVisitaMinsaBTI(cat.getCatKey());
            }

            if (tieneValor(ultimaVezMinsaFumigo)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + ultimaVezMinsaFumigo + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P21'", null);
                if (cat != null) cuestionarioHogar.setUltimaVezMinsaFumigo(cat.getCatKey());
            }

            if (tieneValor(riesgoCasaDengue)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + riesgoCasaDengue + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P23'", null);
                if (cat != null) cuestionarioHogar.setRiesgoCasaDengue(cat.getCatKey());
            }

            if (tieneValor(problemasAbastecimiento)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + problemasAbastecimiento + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (cat != null) cuestionarioHogar.setProblemasAbastecimiento(cat.getCatKey());
            }

            if (tieneValor(cadaCuantoVaAgua)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + cadaCuantoVaAgua + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P25'", null);
                if (cat != null) cuestionarioHogar.setCadaCuantoVaAgua(cat.getCatKey());
            }

            if (tieneValor(queHacenBasuraHogar)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + queHacenBasuraHogar + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P27'", null);
                if (cat != null) cuestionarioHogar.setQueHacenBasuraHogar(cat.getCatKey());
            }

            if (tieneValor(riesgoBarrioDengue)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + riesgoBarrioDengue + "' and "
                        + CatalogosDBConstants.catRoot + "='ENTO_CAT_P23'", null);
                if (cat != null) cuestionarioHogar.setRiesgoBarrioDengue(cat.getCatKey());
            }

            if (tieneValor(accionesCriaderosBarrio)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + accionesCriaderosBarrio + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (cat != null) cuestionarioHogar.setAccionesCriaderosBarrio(cat.getCatKey());
            }

            if (tieneValor(queAcciones)){
                String keys = "";
                queAcciones = queAcciones.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msParedes = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + queAcciones + "') and " + CatalogosDBConstants.catRoot + "='ENTO_CAT_P30'", null);
                for(MessageResource ms : msParedes) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty()) keys = keys.substring(0, keys.length() - 1);
                cuestionarioHogar.setQueAcciones(keys);
            }

            if (tieneValor(alguienParticipo)){
                MessageResource cat = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + alguienParticipo + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (cat != null) cuestionarioHogar.setAlguienParticipo(cat.getCatKey());
            }

            //Numericos
            if (tieneValor(cuantasPersonasViven)) cuestionarioHogar.setCuantasPersonasViven(Integer.parseInt(cuantasPersonasViven));
            if (tieneValor(horasSinAguaDia)) cuestionarioHogar.setHorasSinAguaDia(Integer.parseInt(horasSinAguaDia));

            //textos
            cuestionarioHogar.setQuienContestaOtro(quienContestaOtro);
            cuestionarioHogar.setEdadContest(edadContest);
            cuestionarioHogar.setEdadesFemenino(edadMujeres);
            cuestionarioHogar.setEdadesMasculino(edadHombres);
            if (tieneValor(quienesUsaronMosquitero)) quienesUsaronMosquitero = quienesUsaronMosquitero.replaceAll("\\[", "").replaceAll("\\]", "");
            cuestionarioHogar.setQuienesUsaronMosquitero(quienesUsaronMosquitero);
            if (tieneValor(quienesUsaronRepelente)) quienesUsaronRepelente = quienesUsaronRepelente.replaceAll("\\[", "").replaceAll("\\]", "");
            cuestionarioHogar.setQuienesUsaronRepelente(quienesUsaronRepelente);
            cuestionarioHogar.setQuienVisEliminarLarvasOtro(quienVisEliminarLarvasOtro);
            if (tieneValor(quienDedicaElimLarvasCasa)) quienDedicaElimLarvasCasa = quienDedicaElimLarvasCasa.replaceAll("\\[", "").replaceAll("\\]", "");
            cuestionarioHogar.setQuienDedicaElimLarvasCasa(quienDedicaElimLarvasCasa);
            cuestionarioHogar.setQueFaltaCasaEvitarZancudosOtros(queFaltaCasaEvitarZancudosOtros);
            cuestionarioHogar.setQueProductosCompraronOtros(queProductosCompraronOtros);
            cuestionarioHogar.setCadaCuantoVaAguaOtro(cadaCuantoVaAguaOtro);
            cuestionarioHogar.setQueHacenBasuraHogarOtro(queHacenBasuraHogarOtro);
            cuestionarioHogar.setQueAccionesOtro(queAccionesOtro);
            if (tieneValor(quienParticipo)) quienParticipo = quienParticipo.replaceAll("\\[", "").replaceAll("\\]", "");
            cuestionarioHogar.setQuienParticipo(quienParticipo);
            cuestionarioHogar.setMayorCriaderoBarrio(mayorCriaderoBarrio);

            estudiosAdapter.crearCuestionarioHogar(cuestionarioHogar);
            //guardar detalle de poblacion
            guardarPoblacion(cuestionarioHogar.getCodigoEncuesta());
            estudiosAdapter.close();
            openBuscarCasa();
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
            toast.show();
            finish();
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error),Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void guardarPoblacion(String codigoEncuesta) throws Exception {

        for(String mujer : poblacionMujeres) {
            if (!mujer.trim().equalsIgnoreCase("F0")) {
                CuestionarioHogarPoblacion cuestPob = new CuestionarioHogarPoblacion();
                cuestPob.setCodigoPoblacion(infoMovil.getId());
                cuestPob.setCodigoEncuesta(codigoEncuesta);
                cuestPob.setCodificado(mujer.trim());
                cuestPob.setEdad(mujer.trim().replaceAll(Constants.FEMENINO, ""));
                cuestPob.setSexo(Constants.FEMENINO);
                cuestPob.setRecordDate(new Date());
                cuestPob.setRecordUser(username);
                cuestPob.setDeviceid(infoMovil.getDeviceId());
                cuestPob.setEstado('0');
                cuestPob.setPasive('0');
                estudiosAdapter.crearCuestionarioHogarPoblacion(cuestPob);
            }
        }
        for(String hombre : poblacionHombres) {
            if (!hombre.trim().equalsIgnoreCase("M0")) {
                CuestionarioHogarPoblacion cuestPob = new CuestionarioHogarPoblacion();
                cuestPob.setCodigoPoblacion(infoMovil.getId());
                cuestPob.setCodigoEncuesta(codigoEncuesta);
                cuestPob.setCodificado(hombre.trim());
                cuestPob.setEdad(hombre.trim().replaceAll(Constants.MASCULINO, ""));
                cuestPob.setSexo(Constants.MASCULINO);
                cuestPob.setRecordDate(new Date());
                cuestPob.setRecordUser(username);
                cuestPob.setDeviceid(infoMovil.getDeviceId());
                cuestPob.setEstado('0');
                cuestPob.setPasive('0');
                estudiosAdapter.crearCuestionarioHogarPoblacion(cuestPob);
            }
        }
    }

    private void openBuscarCasa(){
        Intent i = new Intent(getApplicationContext(),
                BuscarCasaActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
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
