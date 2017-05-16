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
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuCasaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaCasaForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaCasaFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
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
            mPagerAdapter.notifyDataSetChanged();
            updateBottomBar();
        }
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
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Fuera");
                changeStatus(mWizardModel.findByKey(labels.getLlaveCompartida()), visible);
                //onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlmacenaAgua())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnBarriles()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnPilas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnTanques()), visible);
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaEnBarriles())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumBarriles()), visible);
                changeStatus(mWizardModel.findByKey(labels.getBarrilesTapados()), visible);
                changeStatus(mWizardModel.findByKey(labels.getBarrilesConAbate()), visible);
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaEnTanques())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTanques()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTanquesTapados()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTanquesConAbate()), visible);
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaEnPilas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumPilas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPilasTapadas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPilasConAbate()), visible);
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaOtrosRecipientes())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientes()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNumOtrosRecipientes()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientesTapados()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientesConAbate()), visible);
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getMaterialParedes())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialParedes()), visible);
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getMaterialPiso())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialPiso()), visible);
                //onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getMaterialTecho())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialTecho()), visible);
                //onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTieneAbanico())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumAbanicos()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneTelevisor())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTelevisores()), visible);
                //onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneRefrigerador())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumRefrigeradores()), visible);
                //onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTienAireAcondicionado())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAireAcondicionadoFuncionando()), visible);
                //onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCocinaConLenia())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getUbicacionCocinaLenia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPeriodicidadCocinaLenia()), visible);
                //onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPeriodicidadCocinaLenia())) {
                //visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Diario");
                changeStatus(mWizardModel.findByKey(labels.getUbicacionCocinaLenia()), page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Diario"));
                changeStatus(mWizardModel.findByKey(labels.getUbicacionCocinaLenia()), page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Semanal"));
                changeStatus(mWizardModel.findByKey(labels.getUbicacionCocinaLenia()), page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Quincenal"));
                changeStatus(mWizardModel.findByKey(labels.getUbicacionCocinaLenia()), page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Mensual"));
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTieneAnimales())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTieneGallinas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTienePatos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneCerdos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCantidadGallinas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCantidadPatos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCerdos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getGallinasDentroCasa()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPatosDentroCasa()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCerdosDentroCasa()), visible);

                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getPersonaFumaDentroCasa())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getMadreFuma()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtrosFuman()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPadreFuma()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCantidadOtrosFuman()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrilosMadre()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrillosPadre()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrillosOtros()), visible);
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
    }

    public void saveData(){
        Map<String, String> mapa = mWizardModel.getAnswers();
        Bundle datos = new Bundle();
        for (Map.Entry<String, String> entry : mapa.entrySet()){
            datos.putString(entry.getKey(), entry.getValue());
        }
        /*String acepta = datos.getString(this.getString(R.string.aceptaTamizaje));
        String razonNP = datos.getString(this.getString(R.string.razonNoParticipa));
        String codigoCasa = datos.getString(getString(R.string.codigoCHF));
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
        estudiosAdapter.open();
        //Recupera los catalogos de la base de datos
        Estudio estudio = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=1", null);
        MessageResource aceptaTamizaje = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + acepta + "' and " + CatalogosDBConstants.catRoot + "='CAT_SINO'", null);
        MessageResource razonNoParticipa = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNP + "' and " + CatalogosDBConstants.catRoot + "='CAT_RAZON_NP'", null);
        //Crea un Nuevo Registro de pretamizaje
        PreTamizaje preTamizaje =  new PreTamizaje();
        preTamizaje.setCodigo(infoMovil.getId());
        preTamizaje.setAceptaTamizaje(aceptaTamizaje.getCatKey().charAt(0));
        if (razonNoParticipa!=null) preTamizaje.setRazonNoParticipa(razonNoParticipa.getCatKey());
        preTamizaje.setCasa(casa);
        preTamizaje.setEstudio(estudio);
        preTamizaje.setRecordDate(new Date());
        preTamizaje.setRecordUser(username);
        preTamizaje.setDeviceid(infoMovil.getDeviceId());
        preTamizaje.setEstado('0');
        preTamizaje.setPasive('0');
        //Inserta un Nuevo Registro de Pretamizaje
        estudiosAdapter.crearPreTamizaje(preTamizaje);
        //Pregunta si acepta el tamizaje
        if (preTamizaje.getAceptaTamizaje()=='1') {
            //Si la respuesta es si crea un nuevo registro de casa CHF
            CasaCohorteFamilia cchf = new CasaCohorteFamilia();
            cchf.setCasa(casa);
            cchf.setCodigoCHF(codigoCasa);
            cchf.setNombre1JefeFamilia(casa.getNombre1JefeFamilia());
            cchf.setNombre2JefeFamilia(casa.getNombre2JefeFamilia());
            cchf.setApellido1JefeFamilia(casa.getApellido1JefeFamilia());
            cchf.setApellido2JefeFamilia(casa.getApellido2JefeFamilia());
            cchf.setRecordDate(new Date());
            cchf.setRecordUser(username);
            cchf.setDeviceid(infoMovil.getDeviceId());
            cchf.setEstado('0');
            cchf.setPasive('0');
            //Inserta una nueva casa CHF
            estudiosAdapter.crearCasaCohorteFamilia(cchf);
            Bundle arguments = new Bundle();
            if (cchf!=null) arguments.putSerializable(Constants.CASA , cchf);
            Intent i = new Intent(getApplicationContext(),
                    MenuCasaActivity.class);
            i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }*/

        Bundle arguments = new Bundle();
        arguments.putSerializable(Constants.CASA , casaCHF);
        Intent i = new Intent(getApplicationContext(),
                MenuCasaActivity.class);
        i.putExtras(arguments);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
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
