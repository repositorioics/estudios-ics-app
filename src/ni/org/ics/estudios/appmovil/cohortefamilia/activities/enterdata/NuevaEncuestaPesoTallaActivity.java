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
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaPesoTallaForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaPesoTallaFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaPesoTalla;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class NuevaEncuestaPesoTallaActivity extends FragmentActivity implements
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

    private EncuestaPesoTallaFormLabels labels;
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
        infoMovil = new DeviceInfo(NuevaEncuestaPesoTallaActivity.this);
        participanteCHF = (ParticipanteCohorteFamilia) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new EncuestaPesoTallaForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        labels = new EncuestaPesoTallaFormLabels();

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

    public void updateModel(Page page){
        try {
            boolean visible = false;
            if (page.getTitle().equals(labels.getTalla1())) {
                Page peso = mWizardModel.findByKey(labels.getPeso1());
                String valorPeso = peso.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorTalla = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorPeso != null && !valorPeso.isEmpty()) {
                    Double dPeso = Double.valueOf(valorPeso);
                    if (valorTalla != null && !valorTalla.isEmpty()) {
                        Double dTalla = Double.valueOf(valorTalla)/100; // se convierte a metro
                        Double imc = dPeso/Math.pow(dTalla, 2);
                        changeStatus(mWizardModel.findByKey(labels.getImc1()), true, String.valueOf(imc));
                    }
                }
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTalla2())) {
                Page peso1 = mWizardModel.findByKey(labels.getPeso1());
                Page peso2 = mWizardModel.findByKey(labels.getPeso2());
                Page talla1 = mWizardModel.findByKey(labels.getTalla1());

                String valorPeso1 = peso1.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorTalla1 = talla1.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorPeso2 = peso2.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorTalla2 = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorPeso2 != null && !valorPeso2.isEmpty()) {
                    Double dPeso2 = Double.valueOf(valorPeso2);
                    if (valorTalla2 != null && !valorTalla2.isEmpty()) {
                        Double dTalla2M = Double.valueOf(valorTalla2)/100; // se convierte a metro
                        Double imc = dPeso2/Math.pow(dTalla2M, 2);
                        changeStatus(mWizardModel.findByKey(labels.getImc2()), true, String.valueOf(imc));
                        //calcular diferencia de mediciones
                        Double difTalla;
                        Double difPeso;
                        Double dPeso1 = Double.valueOf(valorPeso1);
                        Double dTalla1 = Double.valueOf(valorTalla1);
                        Double dTalla2 = Double.valueOf(valorTalla2);
                        if (dPeso1 - dPeso2 > 0)
                            difPeso = ((((dPeso1 - dPeso2) * 10 + 0.5) / 10) / dPeso1 * 100);
                        else
                            difPeso = ((((dPeso2 - dPeso1) * 10 - 0.5) / 10) / dPeso1 * 100);
                        if (dTalla1 - dTalla2 > 0)
                            difTalla = ((((dTalla1 - dTalla2) * 10 + 0.5) / 10) / dTalla1 * 100);
                        else
                            difTalla = ((((dTalla2 - dTalla1) * 10 - 0.5) / 10) / dTalla1 * 100);

                        if (difPeso >= 5 || difTalla >= 5) {
                            visible = true;
                        }else{
                            visible = false;
                        }
                        changeStatus(mWizardModel.findByKey(labels.getDifMediciones()), visible, null);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getTalla3()), visible, null);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getPeso3()), visible, null);
                        notificarCambios = false;
                        changeStatus(mWizardModel.findByKey(labels.getImc3()), visible, null);
                    }
                }
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTalla3())) {
                Page peso = mWizardModel.findByKey(labels.getPeso3());
                String valorPeso = peso.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorTalla = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorPeso != null && !valorPeso.isEmpty()) {
                    Double dPeso = Double.valueOf(valorPeso);
                    if (valorTalla != null && !valorTalla.isEmpty()) {
                        Double dTalla = Double.valueOf(valorTalla)/100; // se convierte a metro
                        Double imc = dPeso/Math.pow(dTalla, 2);
                        changeStatus(mWizardModel.findByKey(labels.getImc3()), true, String.valueOf(imc));
                    }
                }
                notificarCambios = false;
                onPageTreeChanged();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void changeStatus(Page page, boolean visible, String hint){
        String clase = page.getClass().toString();
        if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage")){
            SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.BarcodePage")){
            BarcodePage modifPage = (BarcodePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.LabelPage")){
            LabelPage modifPage = (LabelPage) page;
            if (hint!=null)
                modifPage.setHint(hint);
            modifPage.setmVisible(visible);
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

            String peso1 = datos.getString(this.getString(R.string.peso1));
            String peso2 = datos.getString(this.getString(R.string.peso2));
            String peso3 = datos.getString(this.getString(R.string.peso3));
            String talla1 = datos.getString(this.getString(R.string.talla1));
            String talla2 = datos.getString(this.getString(R.string.talla2));
            String talla3 = datos.getString(this.getString(R.string.talla3));

            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();
            EncuestaPesoTalla encuesta = new EncuestaPesoTalla();
            encuesta.setParticipante(participanteCHF);

            if (tieneValor(peso1)) encuesta.setPeso1(Double.valueOf(peso1));
            if (tieneValor(peso2)) encuesta.setPeso2(Double.valueOf(peso2));
            if (tieneValor(peso3)) encuesta.setPeso3(Double.valueOf(peso3));
            if (tieneValor(talla1)) encuesta.setTalla1(Double.valueOf(talla1));
            if (tieneValor(talla2)) encuesta.setTalla2(Double.valueOf(talla2));
            if (tieneValor(talla3)) encuesta.setTalla3(Double.valueOf(talla3));

            if (tieneValor(peso1) && tieneValor(talla1)){
                Double dPeso = Double.valueOf(peso1);
                Double dTalla = Double.valueOf(talla1)/100; // se convierte a metro
                Double imc = dPeso/Math.pow(dTalla, 2);
                encuesta.setImc1(imc);
            }
            if (tieneValor(peso2) && tieneValor(talla2)){
                Double dPeso = Double.valueOf(peso2);
                Double dTalla = Double.valueOf(talla2)/100; // se convierte a metro
                Double imc = dPeso/Math.pow(dTalla, 2);
                encuesta.setImc2(imc);
            }
            if (tieneValor(peso3) && tieneValor(talla3)){
                Double dPeso = Double.valueOf(peso3);
                Double dTalla = Double.valueOf(talla3)/100; // se convierte a metro
                Double imc = dPeso/Math.pow(dTalla, 2);
                encuesta.setImc3(imc);
            }
            if (tieneValor(peso1) && tieneValor(peso2)) {
                Double dPeso1 = Double.valueOf(peso1);
                Double dPeso2 = Double.valueOf(peso2);
                Double difPeso;
                if (dPeso1 - dPeso2 > 0)
                    difPeso = ((((dPeso1 - dPeso2) * 10 + 0.5) / 10) / dPeso1 * 100);
                else
                    difPeso = ((((dPeso2 - dPeso1) * 10 - 0.5) / 10) / dPeso1 * 100);
                encuesta.setDifPeso(difPeso);
            }
            if (tieneValor(talla1) && tieneValor(talla2)) {
                Double dTalla1 = Double.valueOf(talla1);
                Double dTalla2 = Double.valueOf(talla2);
                Double difTalla;
                if (dTalla1 - dTalla2 > 0)
                    difTalla = ((((dTalla1 - dTalla2) * 10 + 0.5) / 10) / dTalla1 * 100);
                else
                    difTalla = ((((dTalla2 - dTalla1) * 10 - 0.5) / 10) / dTalla1 * 100);
                encuesta.setDifTalla(difTalla);
            }
            encuesta.setRecurso1(username);

            //Metadata
            encuesta.setRecordDate(new Date());
            encuesta.setRecordUser(username);
            encuesta.setDeviceid(infoMovil.getDeviceId());
            encuesta.setEstado('0');
            encuesta.setPasive('0');
            boolean actualizada = false;
            EncuestaPesoTalla encuestaExiste = estudiosAdapter.getEncuestasPesoTalla(EncuestasDBConstants.participante_chf + "='" + participanteCHF.getParticipanteCHF()+"'", EncuestasDBConstants.participante_chf);
            if (encuestaExiste != null && encuestaExiste.getParticipante() != null && encuestaExiste.getParticipante().getParticipanteCHF() != null)
                actualizada = estudiosAdapter.editarEncuestasPesoTalla(encuesta);
            else estudiosAdapter.crearEncuestasPesoTalla(encuesta);
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
