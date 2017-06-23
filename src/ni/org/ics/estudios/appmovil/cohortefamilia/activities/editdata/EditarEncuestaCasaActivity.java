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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuCasaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaCasaForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaCasaFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaCasa;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class EditarEncuestaCasaActivity extends FragmentActivity implements
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
    private static EncuestaCasa encuestaCasa = new EncuestaCasa();
	private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	private EncuestaCasaFormLabels labels = new EncuestaCasaFormLabels();
	public static final String SIMPLE_DATA_KEY = "_";

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
		infoMovil = new DeviceInfo(EditarEncuestaCasaActivity.this);
        encuestaCasa = (EncuestaCasa) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
		
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new EncuestaCasaForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        //Abre la base de datos
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		estudiosAdapter.open();
        if (encuestaCasa != null) {
        	Bundle dato = null;
        	Page modifPage;
            modifPage = (NumberPage) mWizardModel.findByKey(labels.getCuantoCuartos());
            dato = new Bundle();
            dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getCantidadCuartos()));
            modifPage.resetData(dato);

            modifPage = (NumberPage) mWizardModel.findByKey(labels.getCuartosDormir());
            dato = new Bundle();
            dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getCantidadCuartosDormir()));
            modifPage.resetData(dato);

            modifPage = (NumberPage) mWizardModel.findByKey(labels.getHorasSinAgua());
            dato = new Bundle();
            dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getHrsSinServicioAgua()));
            modifPage.resetData(dato);

            if(encuestaCasa.getUbicacionLlaveAgua()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getLlaveAgua());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getUbicacionLlaveAgua() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_DF'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getLlaveCompartida()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getLlaveCompartida());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getLlaveCompartida() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_COMPARTIDO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getAlmacenaAgua()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAlmacenaAgua());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getAlmacenaAgua() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getAlmacenaEnBarriles()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAlmacenaEnBarriles());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getAlmacenaEnBarriles() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getNumBarriles()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumBarriles());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getNumBarriles()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getBarrilesTapados()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getBarrilesTapados());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getBarrilesTapados() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getBarrilesConAbate()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getBarrilesConAbate());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getBarrilesConAbate() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }

            if(encuestaCasa.getAlmacenaEnTanques()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAlmacenaEnTanques());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getAlmacenaEnTanques() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getNumTanques()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumTanques());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getNumTanques()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTanquesTapados()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTanquesTapados());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTanquesTapados() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTanquesConAbate()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTanquesConAbate());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTanquesConAbate() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }

            if(encuestaCasa.getAlmacenaEnPilas()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAlmacenaEnPilas());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getAlmacenaEnPilas() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getNumPilas()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumPilas());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getNumPilas()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getPilasTapadas()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPilasTapadas());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getPilasTapadas() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getPilasConAbate()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPilasConAbate());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getPilasConAbate() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }

            if(encuestaCasa.getAlmacenaOtrosRecipientes()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAlmacenaOtrosRecipientes());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getAlmacenaOtrosRecipientes() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getOtrosRecipientes()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getOtrosRecipientes());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getOtrosRecipientes()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getNumOtrosRecipientes()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumOtrosRecipientes());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getNumOtrosRecipientes()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getOtrosRecipientesTapados()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getOtrosRecipientesTapados());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getOtrosRecipientesTapados() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getOtrosRecipientesConAbate()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getOtrosRecipientesConAbate());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getOtrosRecipientesConAbate() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getUbicacionLavandero()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getUbicacionLavandero());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getUbicacionLavandero() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_DF'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getMaterialParedes()!= null){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getMaterialParedes());
                String codParedes = encuestaCasa.getMaterialParedes().replaceAll("," , "','");
                List<String> descParedes = new ArrayList<String>();
                List<MessageResource> msParedes = estudiosAdapter.getMessageResources(CatalogosDBConstants.catKey + " in ('" + codParedes + "') and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MAT_PARED'", null);
                for(MessageResource ms : msParedes){
                    descParedes.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descParedes);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getOtroMaterialParedes()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getOtroMaterialParedes());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getOtroMaterialParedes()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getMaterialPiso()!= null){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getMaterialPiso());
                String codigos = encuestaCasa.getMaterialParedes().replaceAll("," , "','");
                List<String> descripciones = new ArrayList<String>();
                List<MessageResource> msParedes = estudiosAdapter.getMessageResources(CatalogosDBConstants.catKey + " in ('" + codigos + "') and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MAT_PISO'", null);
                for(MessageResource ms : msParedes){
                    descripciones.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descripciones);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getOtroMaterialPiso()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getOtroMaterialPiso());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getOtroMaterialPiso()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getMaterialTecho()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getMaterialTecho());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getMaterialTecho() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_MAT_TECHO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getOtroMaterialTecho()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getOtroMaterialTecho());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getOtroMaterialTecho()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getCasaPropia()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getCasaPropia());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getCasaPropia() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTieneTelevisor()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTieneTelevisor());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTieneTelevisor() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getNumTelevisores()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumTelevisores());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getNumTelevisores()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTieneAbanico()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTieneAbanico());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTieneAbanico() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getNumAbanicos()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumAbanicos());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getNumAbanicos()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTieneRefrigerador()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTieneRefrigerador());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTieneRefrigerador() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getNumRefrigeradores()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumRefrigeradores());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getNumRefrigeradores()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTienAireAcondicionado()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTienAireAcondicionado());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTienAireAcondicionado() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getAireAcondicionadoFuncionando()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAireAcondicionadoFuncionando());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getAireAcondicionadoFuncionando() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTieneMoto()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTieneMoto());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTieneMoto() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTieneCarro()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTieneCarro());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTieneCarro() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTienMicrobus()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTienMicrobus());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTienMicrobus() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTieneCamioneta()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTieneCamioneta());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTieneCamioneta() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTieneCamion()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTieneCamion());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTieneCamion() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTieneOtroMedioTransAuto()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTieneOtroMedioTransAuto());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTieneOtroMedioTransAuto() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getOtroMedioTransAuto()!=null){
                modifPage = (TextPage) mWizardModel.findByKey(labels.getOtroMedioTransAuto());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getOtroMedioTransAuto()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getCocinaConLenia()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getCocinaConLenia());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getCocinaConLenia() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getUbicacionCocinaLenia()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getUbicacionCocinaLenia());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getUbicacionCocinaLenia() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_DF'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getPeriodicidadCocinaLenia()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPeriodicidadCocinaLenia());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getPeriodicidadCocinaLenia() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_FREC_COCINA'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getNumDiarioCocinaLenia()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumDiarioCocinaLenia());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getNumDiarioCocinaLenia()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getNumSemanalCocinaLenia()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumSemanalCocinaLenia());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getNumSemanalCocinaLenia()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getNumQuincenalCocinaLenia()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumQuincenalCocinaLenia());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getNumQuincenalCocinaLenia()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getNumMensualCocinaLenia()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumMensualCocinaLenia());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getNumMensualCocinaLenia()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTieneAnimales()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTieneAnimales());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTieneAnimales() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTieneGallinas()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTieneGallinas());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTieneGallinas() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getCantidadGallinas()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getCantidadGallinas());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getCantidadGallinas()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getGallinasDentroCasa()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getGallinasDentroCasa());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getGallinasDentroCasa() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTienePatos()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTienePatos());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTienePatos() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getCantidadPatos()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getCantidadPatos());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getCantidadPatos()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getPatosDentroCasa()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatosDentroCasa());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getPatosDentroCasa() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getTieneCerdos()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTieneCerdos());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getTieneCerdos() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getCantidadCerdos()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getCantidadCerdos());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getCantidadCerdos()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getCerdosDentroCasa()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getCerdosDentroCasa());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getCerdosDentroCasa() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getPersonaFumaDentroCasa()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPersonaFumaDentroCasa());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getPersonaFumaDentroCasa() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getMadreFuma()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getMadreFuma());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getMadreFuma() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getCantidadCigarrilosMadre()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getCantidadCigarrilosMadre());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getCantidadCigarrilosMadre()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getPadreFuma()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPadreFuma());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getPadreFuma() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getCantidadCigarrillosPadre()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getCantidadCigarrillosPadre());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getCantidadCigarrillosPadre()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getOtrosFuman()!= null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getOtrosFuman());
                MessageResource catSiNo = estudiosAdapter.getMessageResource(CatalogosDBConstants.catKey + "='" + encuestaCasa.getOtrosFuman() + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, catSiNo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getCantidadOtrosFuman()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getCantidadOtrosFuman());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getCantidadOtrosFuman()));
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
            if(encuestaCasa.getCantidadCigarrillosOtros()!=null){
                modifPage = (NumberPage) mWizardModel.findByKey(labels.getCantidadCigarrillosOtros());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuestaCasa.getCantidadCigarrillosOtros()));
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
        
        onPageTreeChangedInitial(); 
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
    
    public void onPageTreeChangedInitial() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        if (recalculateCutOffPage()) {
            updateBottomBar();
        }
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
    	try{
	    	updateModel(page);
	    	updateConstrains();
	        if (recalculateCutOffPage()) {
	        	if (notificarCambios) mPagerAdapter.notifyDataSetChanged();
	            updateBottomBar();
	        }
	        notificarCambios = true;
	    }catch (Exception ex){
	        ex.printStackTrace();
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
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Fuera");
                changeStatus(mWizardModel.findByKey(labels.getLlaveCompartida()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlmacenaAgua())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnBarriles()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnPilas()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnTanques()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaOtrosRecipientes()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaEnBarriles())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumBarriles()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrilesTapados()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getBarrilesConAbate()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaEnTanques())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTanques()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTanquesTapados()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTanquesConAbate()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaEnPilas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumPilas()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPilasTapadas()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPilasConAbate()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getAlmacenaOtrosRecipientes())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientes()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumOtrosRecipientes()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientesTapados()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientesConAbate()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getMaterialParedes())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialParedes()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getMaterialPiso())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialPiso()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getMaterialTecho())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialTecho()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTieneAbanico())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumAbanicos()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneTelevisor())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTelevisores()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneRefrigerador())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumRefrigeradores()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTienAireAcondicionado())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAireAcondicionadoFuncionando()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCocinaConLenia())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getUbicacionCocinaLenia()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPeriodicidadCocinaLenia()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPeriodicidadCocinaLenia())) {
                //visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Diario");
                changeStatus(mWizardModel.findByKey(labels.getNumDiarioCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Diario")));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumSemanalCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Semanal")));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumQuincenalCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Quincenal")));
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getNumMensualCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Mensual")));
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTieneAnimales())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTieneGallinas()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTienePatos()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getTieneCerdos()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneGallinas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadGallinas()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getGallinasDentroCasa()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTienePatos())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadPatos()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPatosDentroCasa()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTieneCerdos())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCerdos()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCerdosDentroCasa()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getPersonaFumaDentroCasa())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getMadreFuma()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getOtrosFuman()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getPadreFuma()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMadreFuma())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrilosMadre()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPadreFuma())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrillosPadre()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getOtrosFuman())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadOtrosFuman()), visible);
                notificarCambios = false;
                changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrillosOtros()), visible);
                notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneOtroMedioTransAuto())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getOtroMedioTransAuto()), visible);
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
    		BarcodePage modifPage = (BarcodePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);;
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
        try {
            Map<String, String> mapa = mWizardModel.getAnswers();
            //Guarda las respuestas en un bundle
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }

            //Obtener datos del bundle para la encuesta de casa
            //catálogos
            String scLlaveAgua = datos.getString(this.getString(R.string.llaveAgua));
            String scLlaveCompartida = datos.getString(this.getString(R.string.llaveCompartida));
            String scAlmacenaAgua = datos.getString(this.getString(R.string.almacenaAgua));
            String scEnBarriles = datos.getString(this.getString(R.string.almacenaEnBarriles));
            String scBarrilesTapados = datos.getString(this.getString(R.string.barrilesTapados));
            String scBarrilesAbate = datos.getString(this.getString(R.string.barrilesConAbate));
            String scEnTanques = datos.getString(this.getString(R.string.almacenaEnTanques));
            String scTanquesTapados = datos.getString(this.getString(R.string.tanquesTapados));
            String scTanquesAbate = datos.getString(this.getString(R.string.tanquesConAbate));
            String scEnPilas = datos.getString(this.getString(R.string.almacenaEnPilas));
            String scPilasTapadas = datos.getString(this.getString(R.string.pilasTapadas));
            String scPilasAbate = datos.getString(this.getString(R.string.pilasConAbate));
            String scEnOtrosRec = datos.getString(this.getString(R.string.almacenaOtrosRecipientes));
            String scOtrosRecTapados = datos.getString(this.getString(R.string.otrosRecipientesTapados));
            String scOtrosRecAbate = datos.getString(this.getString(R.string.otrosRecipientesConAbate));
            String mcParedes = datos.getString(this.getString(R.string.materialParedes));
            String mcPiso = datos.getString(this.getString(R.string.materialPiso));
            String scLavandero = datos.getString(this.getString(R.string.ubicacionLavandero));
            String scTecho = datos.getString(this.getString(R.string.materialTecho));
            String scCasaPropia = datos.getString(this.getString(R.string.casaPropia));
            String scTelevisor = datos.getString(this.getString(R.string.tieneTelevisor));
            String scAbanico = datos.getString(this.getString(R.string.tieneAbanico));
            String scRefrigerador = datos.getString(this.getString(R.string.tieneRefrigerador));
            String scAireAcondicionado = datos.getString(this.getString(R.string.tienAireAcondicionado));
            String scAireAcondicionadoFun = datos.getString(this.getString(R.string.aireAcondicionadoFuncionando));
            String scMoto = datos.getString(this.getString(R.string.tieneMoto));
            String scMTCarro = datos.getString(this.getString(R.string.tieneCarro));
            String scMTMicrobus = datos.getString(this.getString(R.string.tienMicrobus));
            String scMTCamioneta = datos.getString(this.getString(R.string.tieneCamioneta));
            String scMTCamion = datos.getString(this.getString(R.string.tieneCamion));
            String scMTOtro = datos.getString(this.getString(R.string.tieneOtroMedioTransAuto));
            String scCocina = datos.getString(this.getString(R.string.cocinaConLenia));
            String scCocinaUbicacion = datos.getString(this.getString(R.string.ubicacionCocinaLenia));
            String scCocinaPeriodicidad = datos.getString(this.getString(R.string.periodicidadCocinaLenia));
            String scAnimales = datos.getString(this.getString(R.string.tieneAnimales));
            String scAnimalesGallinas = datos.getString(this.getString(R.string.tieneGallinas));
            String scGallinasDC = datos.getString(this.getString(R.string.gallinasDentroCasa));
            String scAnimalesPatos = datos.getString(this.getString(R.string.tienePatos));
            String scPatosDC = datos.getString(this.getString(R.string.patosDentroCasa));
            String scAnimalesCerdos = datos.getString(this.getString(R.string.tieneCerdos));
            String scCerdosDC = datos.getString(this.getString(R.string.cerdosDentroCasa));
            String scFuman = datos.getString(this.getString(R.string.personaFumaDentroCasa));
            String scMadreFuma = datos.getString(this.getString(R.string.madreFuma));
            String scPafreFuma = datos.getString(this.getString(R.string.padreFuma));
            String scOtrosFuma = datos.getString(this.getString(R.string.otrosFuman));

            //textos
            String tpDescOtrosRec = datos.getString(this.getString(R.string.otrosRecipientes));
            String tpParedesOtroDesc = datos.getString(this.getString(R.string.otroMaterialParedes));
            String tpPisoOtroDesc = datos.getString(this.getString(R.string.otroMaterialPiso));
            String tpTechoOtroDesc = datos.getString(this.getString(R.string.otroMaterialTecho));
            String tpMTOtroDesc = datos.getString(this.getString(R.string.otroMedioTransAuto));

            //numéricos
            String npCuartos = datos.getString(this.getString(R.string.cuantoCuartos));
            String npCuartosDormir = datos.getString(this.getString(R.string.cuartosDormir));
            String npHorasSinAgua = datos.getString(this.getString(R.string.horasSinAgua));
            String npNumBarriles = datos.getString(this.getString(R.string.numBarriles));
            String npNumTanques = datos.getString(this.getString(R.string.numTanques));
            String npNumPilas = datos.getString(this.getString(R.string.numPilas));
            String npNumOtrosRec = datos.getString(this.getString(R.string.numOtrosRecipientes));
            String npNumTelevisor = datos.getString(this.getString(R.string.numTelevisores));
            String npAbanico = datos.getString(this.getString(R.string.numAbanicos));
            String npNumRefrigerador = datos.getString(this.getString(R.string.numRefrigeradores));
            String npNumCocinaD = datos.getString(this.getString(R.string.numDiarioCocinaLenia));
            String npNumCocinaS = datos.getString(this.getString(R.string.numSemanalCocinaLenia));
            String npNumCocinaQ = datos.getString(this.getString(R.string.numQuincenalCocinaLenia));
            String npNumCocinaM = datos.getString(this.getString(R.string.numMensualCocinaLenia));
            String npCantGallinas = datos.getString(this.getString(R.string.cantidadGallinas));
            String npCantPatos = datos.getString(this.getString(R.string.cantidadPatos));
            String npCantCerdos = datos.getString(this.getString(R.string.cantidadCerdos));
            String npCantCigarMadre = datos.getString(this.getString(R.string.cantidadCigarrilosMadre));
            String npCantCigarPadre = datos.getString(this.getString(R.string.cantidadCigarrillosPadre));
            String npCantOtrosFuma = datos.getString(this.getString(R.string.cantidadOtrosFuman));
            String npCantCigarOtrosF = datos.getString(this.getString(R.string.cantidadCigarrillosOtros));

            //Abre la base de datos
            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();


            if (tieneValor(scLlaveAgua)) {
                MessageResource msLlaveAgua = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scLlaveAgua + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_DF'", null);
                if (msLlaveAgua != null) encuestaCasa.setUbicacionLlaveAgua(msLlaveAgua.getCatKey());
            } else {
                encuestaCasa.setUbicacionLlaveAgua(null);
            }
            if (tieneValor(scLlaveCompartida)) {
                MessageResource msLLaveCompartida = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scLlaveCompartida + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_COMPARTIDO'", null);
                if (msLLaveCompartida != null) encuestaCasa.setLlaveCompartida(msLLaveCompartida.getCatKey());
            } else{
                encuestaCasa.setLlaveCompartida(null);
            }
            if (tieneValor(scAlmacenaAgua)) {
                MessageResource msAlmacenaAgua = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAlmacenaAgua + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAlmacenaAgua != null) encuestaCasa.setAlmacenaAgua(msAlmacenaAgua.getCatKey());
            } else {
                encuestaCasa.setAlmacenaAgua(null);
            }
            if (tieneValor(scEnBarriles)) {
                MessageResource msEnBarriles = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scEnBarriles + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msEnBarriles != null) encuestaCasa.setAlmacenaEnBarriles(msEnBarriles.getCatKey());
            } else {
                encuestaCasa.setAlmacenaEnBarriles(null);
            }
            if (tieneValor(scBarrilesTapados)) {
                MessageResource msBarrilesTapados = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scBarrilesTapados + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msBarrilesTapados != null) encuestaCasa.setBarrilesTapados(msBarrilesTapados.getCatKey());
            } else {
                encuestaCasa.setBarrilesTapados(null);
            }
            if (tieneValor(scBarrilesAbate)) {
                MessageResource msBarrilesAbate = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scBarrilesAbate + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msBarrilesAbate != null) encuestaCasa.setBarrilesConAbate(msBarrilesAbate.getCatKey());
            } else {
                encuestaCasa.setBarrilesConAbate(null);
            }
            if (tieneValor(scEnTanques)) {
                MessageResource msEnTanques = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scEnTanques + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msEnTanques != null) encuestaCasa.setAlmacenaEnTanques(msEnTanques.getCatKey());
            } else {
                encuestaCasa.setAlmacenaEnTanques(null);
            }
            if (tieneValor(scTanquesTapados)) {
                MessageResource msTanquesTapados = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scTanquesTapados + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msTanquesTapados != null) encuestaCasa.setTanquesTapados(msTanquesTapados.getCatKey());
            } else {
                encuestaCasa.setTanquesTapados(null);
            }
            if (tieneValor(scTanquesAbate)) {
                MessageResource msTanquesAbate = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scTanquesAbate + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msTanquesAbate != null) encuestaCasa.setTanquesConAbate(msTanquesAbate.getCatKey());
            } else {
                encuestaCasa.setTanquesConAbate(null);
            }
            if (tieneValor(scEnPilas)) {
                MessageResource msEnPilas = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scEnPilas + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msEnPilas != null) encuestaCasa.setAlmacenaEnPilas(msEnPilas.getCatKey());
            } else {
                encuestaCasa.setAlmacenaEnPilas(null);
            }
            if (tieneValor(scPilasTapadas)) {
                MessageResource msPilasTapadas = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scPilasTapadas + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msPilasTapadas != null) encuestaCasa.setPilasTapadas(msPilasTapadas.getCatKey());
            } else {
                encuestaCasa.setPilasTapadas(null);
            }
            if (tieneValor(scPilasAbate)) {
                MessageResource msPilasAbate = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scPilasAbate + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msPilasAbate != null) encuestaCasa.setPilasConAbate(msPilasAbate.getCatKey());
            } else {
                encuestaCasa.setPilasConAbate(null);
            }
            if (tieneValor(scEnOtrosRec)) {
                MessageResource msEnOtrosRec = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scEnOtrosRec + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msEnOtrosRec != null) encuestaCasa.setAlmacenaOtrosRecipientes(msEnOtrosRec.getCatKey());
            } else {
                encuestaCasa.setAlmacenaOtrosRecipientes(null);
            }
            if (tieneValor(scOtrosRecTapados)) {
                MessageResource msOtrosRecTapados = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scOtrosRecTapados + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msOtrosRecTapados != null)
                    encuestaCasa.setOtrosRecipientesTapados(msOtrosRecTapados.getCatKey());
            } else {
                encuestaCasa.setOtrosRecipientesTapados(null);
            }
            if (tieneValor(scOtrosRecAbate)) {
                MessageResource msOtrosRecAbate = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scOtrosRecAbate + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msOtrosRecAbate != null)
                    encuestaCasa.setOtrosRecipientesConAbate(msOtrosRecAbate.getCatKey());
            } else {
                encuestaCasa.setOtrosRecipientesConAbate(null);
            }
            if (tieneValor(mcParedes)) {
                String keysMaterial = "";
                mcParedes = mcParedes.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msParedes = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + mcParedes + "') and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_MAT_PARED'", null);
                for(MessageResource ms : msParedes) {
                    keysMaterial += ms.getCatKey() + ",";
                }
                if (!keysMaterial.isEmpty())
                    keysMaterial = keysMaterial.substring(0, keysMaterial.length() - 1);
                encuestaCasa.setMaterialParedes(keysMaterial);
            } else {
                encuestaCasa.setMaterialParedes(null);
            }
            if (tieneValor(mcPiso)) {
                String keysMaterial = "";
                mcPiso = mcPiso.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msPiso = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + mcPiso + "') and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_MAT_PISO'", null);
                for(MessageResource ms : msPiso) {
                    keysMaterial += ms.getCatKey() + ",";
                }
                if (!keysMaterial.isEmpty())
                    keysMaterial = keysMaterial.substring(0, keysMaterial.length() - 1);
                encuestaCasa.setMaterialPiso(keysMaterial);
            } else {
                encuestaCasa.setMaterialPiso(null);
            }
            if (tieneValor(scTecho)) {
                MessageResource msTecho = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scTecho + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_MAT_TECHO'", null);
                if (msTecho != null) encuestaCasa.setMaterialTecho(msTecho.getCatKey());
            } else {
                encuestaCasa.setMaterialTecho(null);
            }
            if (tieneValor(scLavandero)) {
                MessageResource msLavandero = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scLavandero + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_DF'", null);
                if (msLavandero != null) encuestaCasa.setUbicacionLavandero(msLavandero.getCatKey());
            } else {
                encuestaCasa.setUbicacionLavandero(null);
            }
            if (tieneValor(scCasaPropia)) {
                MessageResource msCasaPropia = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scCasaPropia + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msCasaPropia != null) encuestaCasa.setCasaPropia(msCasaPropia.getCatKey());
            } else {
                encuestaCasa.setCasaPropia(null);
            }
            if (tieneValor(scTelevisor)) {
                MessageResource msTelevisor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scTelevisor + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msTelevisor != null) encuestaCasa.setTieneTelevisor(msTelevisor.getCatKey());
            } else {
                encuestaCasa.setTieneTelevisor(null);
            }
            if (tieneValor(scAbanico)) {
                MessageResource msAbanico = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAbanico + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAbanico != null) encuestaCasa.setTieneAbanico(msAbanico.getCatKey());
            } else {
                encuestaCasa.setTieneAbanico(null);
            }
            if (tieneValor(scRefrigerador)) {
                MessageResource msRefrigerador = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scRefrigerador + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msRefrigerador != null) encuestaCasa.setTieneRefrigerador(msRefrigerador.getCatKey());
            } else {
                encuestaCasa.setTieneRefrigerador(null);
            }
            if (tieneValor(scAireAcondicionado)) {
                MessageResource msAireAcondicionado = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAireAcondicionado + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAireAcondicionado != null)
                    encuestaCasa.setTienAireAcondicionado(msAireAcondicionado.getCatKey());
            } else {
                encuestaCasa.setTienAireAcondicionado(null);
            }
            if (tieneValor(scAireAcondicionadoFun)) {
                MessageResource msAireAcondicionadoFun = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAireAcondicionadoFun + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_FUN_ABANICO'", null);
                if (msAireAcondicionadoFun != null)
                    encuestaCasa.setAireAcondicionadoFuncionando(msAireAcondicionadoFun.getCatKey());
            } else {
                encuestaCasa.setAireAcondicionadoFuncionando(null);
            }
            if (tieneValor(scMoto)) {
                MessageResource msMTMoto = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMoto + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTMoto != null) encuestaCasa.setTieneMoto(msMTMoto.getCatKey());
            } else {
                encuestaCasa.setTieneMoto(null);
            }
            if (tieneValor(scMTCarro)) {
                MessageResource msMTCarro = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMTCarro + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTCarro != null) encuestaCasa.setTieneCarro(msMTCarro.getCatKey());
            } else {
                encuestaCasa.setTieneCarro(null);
            }
            if (tieneValor(scMTMicrobus)) {
                MessageResource msMTMicrobus = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMTMicrobus + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTMicrobus != null) encuestaCasa.setTienMicrobus(msMTMicrobus.getCatKey());
            } else {
                encuestaCasa.setTienMicrobus(null);
            }
            if (tieneValor(scMTCamioneta)) {
                MessageResource msMTCamioneta = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMTCamioneta + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTCamioneta != null) encuestaCasa.setTieneCamioneta(msMTCamioneta.getCatKey());
            } else {
                encuestaCasa.setTieneCamioneta(null);
            }
            if (tieneValor(scMTCamion)) {
                MessageResource msMTCamion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMTCamion + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTCamion != null) encuestaCasa.setTieneCamion(msMTCamion.getCatKey());
            } else {
                encuestaCasa.setTieneCamion(null);
            }
            if (tieneValor(scMTOtro)) {
                MessageResource msMTOtro = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMTOtro + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMTOtro != null) encuestaCasa.setTieneOtroMedioTransAuto(msMTOtro.getCatKey());
            } else {
                encuestaCasa.setTieneOtroMedioTransAuto(null);
            }
            if (tieneValor(scCocina)) {
                MessageResource msCocina = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scCocina + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msCocina != null) encuestaCasa.setCocinaConLenia(msCocina.getCatKey());
            } else {
                encuestaCasa.setCocinaConLenia(null);
            }
            if (tieneValor(scCocinaUbicacion)) {
                MessageResource msCocinaUbicacion = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scCocinaUbicacion + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_DF'", null);
                if (msCocinaUbicacion != null) encuestaCasa.setUbicacionCocinaLenia(msCocinaUbicacion.getCatKey());
            } else {
                encuestaCasa.setUbicacionCocinaLenia(null);
            }
            if (tieneValor(scCocinaPeriodicidad)) {
                MessageResource msCocinaPeriodicidad = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scCocinaPeriodicidad + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_FREC_COCINA'", null);
                if (msCocinaPeriodicidad != null)
                    encuestaCasa.setPeriodicidadCocinaLenia(msCocinaPeriodicidad.getCatKey());
            } else {
                encuestaCasa.setPeriodicidadCocinaLenia(null);
            }
            if (tieneValor(scAnimales)) {
                MessageResource msAnimales = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAnimales + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAnimales != null) encuestaCasa.setTieneAnimales(msAnimales.getCatKey());
            } else {
                encuestaCasa.setTieneAnimales(null);
            }
            if (tieneValor(scAnimalesGallinas)) {
                MessageResource msAnimalesGallinas = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAnimalesGallinas + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAnimalesGallinas != null) encuestaCasa.setTieneGallinas(msAnimalesGallinas.getCatKey());
            } else {
                encuestaCasa.setTieneGallinas(null);
            }
            if (tieneValor(scGallinasDC)) {
                MessageResource msGallinasDC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scGallinasDC + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msGallinasDC != null) encuestaCasa.setGallinasDentroCasa(msGallinasDC.getCatKey());
            } else {
                encuestaCasa.setGallinasDentroCasa(null);
            }
            if (tieneValor(scAnimalesPatos)) {
                MessageResource msAnimalesPatos = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAnimalesPatos + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAnimalesPatos != null) encuestaCasa.setTienePatos(msAnimalesPatos.getCatKey());
            } else {
                encuestaCasa.setTienePatos(null);
            }
            if (tieneValor(scPatosDC)) {
                MessageResource msPatosDC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scPatosDC + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msPatosDC != null) encuestaCasa.setPatosDentroCasa(msPatosDC.getCatKey());
            } else {
                encuestaCasa.setPatosDentroCasa(null);
            }
            if (tieneValor(scAnimalesCerdos)) {
                MessageResource msAnimalesCerdos = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scAnimalesCerdos + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msAnimalesCerdos != null) encuestaCasa.setTieneCerdos(msAnimalesCerdos.getCatKey());
            } else {
                encuestaCasa.setTieneCerdos(null);
            }
            if (tieneValor(scCerdosDC)) {
                MessageResource msCerdosDC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scCerdosDC + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msCerdosDC != null) encuestaCasa.setCerdosDentroCasa(msCerdosDC.getCatKey());
            } else {
                encuestaCasa.setCerdosDentroCasa(null);
            }
            if (tieneValor(scFuman)) {
                MessageResource msFuman = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scFuman + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msFuman != null) encuestaCasa.setPersonaFumaDentroCasa(msFuman.getCatKey());
            } else {
                encuestaCasa.setPersonaFumaDentroCasa(null);
            }
            if (tieneValor(scMadreFuma)) {
                MessageResource msMadreFuma = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scMadreFuma + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msMadreFuma != null) encuestaCasa.setMadreFuma(msMadreFuma.getCatKey());
            } else {
                encuestaCasa.setMadreFuma(null);
            }
            if (tieneValor(scPafreFuma)) {
                MessageResource msPafreFuma = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scPafreFuma + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msPafreFuma != null) encuestaCasa.setPadreFuma(msPafreFuma.getCatKey());
            } else {
                encuestaCasa.setPadreFuma(null);
            }
            if (tieneValor(scOtrosFuma)) {
                MessageResource msOtrosFuma = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + scOtrosFuma + "' and "
                        + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                if (msOtrosFuma != null) encuestaCasa.setOtrosFuman(msOtrosFuma.getCatKey());
            } else {
                encuestaCasa.setOtrosFuman(null);
            }

            if (tieneValor(tpDescOtrosRec)){
                encuestaCasa.setOtrosRecipientes(tpDescOtrosRec);
            } else {
                encuestaCasa.setOtrosRecipientes(null);
            }
            if (tieneValor(tpParedesOtroDesc)){
                encuestaCasa.setOtroMaterialParedes(tpParedesOtroDesc);
            } else {
                encuestaCasa.setOtroMaterialParedes(null);
            }
            if (tieneValor(tpPisoOtroDesc)){
                encuestaCasa.setOtroMaterialPiso(tpPisoOtroDesc);
            } else {
                encuestaCasa.setOtroMaterialPiso(null);
            }
            if (tieneValor(tpTechoOtroDesc)){
                encuestaCasa.setOtroMaterialTecho(tpTechoOtroDesc);
            } else {
                encuestaCasa.setOtroMaterialTecho(null);
            }
            if (tieneValor(tpMTOtroDesc)){
                encuestaCasa.setOtroMedioTransAuto(tpMTOtroDesc);
            } else {
                encuestaCasa.setOtroMedioTransAuto(null);
            }

            if (tieneValor(npCuartos)) encuestaCasa.setCantidadCuartos(Integer.valueOf(npCuartos));
            if (tieneValor(npCuartosDormir)) encuestaCasa.setCantidadCuartosDormir(Integer.valueOf(npCuartosDormir));
            if (tieneValor(npHorasSinAgua)) encuestaCasa.setHrsSinServicioAgua(Integer.valueOf(npHorasSinAgua));
            if (tieneValor(npNumBarriles)) {
                encuestaCasa.setNumBarriles(Integer.valueOf(npNumBarriles));
            } else {
                encuestaCasa.setNumBarriles(null);
            }
            if (tieneValor(npNumTanques)) {
                encuestaCasa.setNumTanques(Integer.valueOf(npNumTanques));
            } else {
                encuestaCasa.setNumTanques(null);
            }
            if (tieneValor(npNumPilas)) {
                encuestaCasa.setNumPilas(Integer.valueOf(npNumPilas));
            } else {
                encuestaCasa.setNumPilas(null);
            }
            if (tieneValor(npNumOtrosRec)) {
                encuestaCasa.setNumOtrosRecipientes(Integer.valueOf(npNumOtrosRec));
            } else {
                encuestaCasa.setNumOtrosRecipientes(null);
            }
            if (tieneValor(npNumTelevisor)) {
                encuestaCasa.setNumTelevisores(Integer.valueOf(npNumTelevisor));
            } else {
                encuestaCasa.setNumTelevisores(null);
            }
            if (tieneValor(npAbanico)) {
                encuestaCasa.setNumAbanicos(Integer.valueOf(npAbanico));
            } else {
                encuestaCasa.setNumAbanicos(null);
            }
            if (tieneValor(npNumRefrigerador)) {
                encuestaCasa.setNumRefrigeradores(Integer.valueOf(npNumRefrigerador));
            } else {
                encuestaCasa.setNumRefrigeradores(null);
            }
            if (tieneValor(npNumCocinaD)) {
                encuestaCasa.setNumDiarioCocinaLenia(Integer.valueOf(npNumCocinaD));
            } else {
                encuestaCasa.setNumDiarioCocinaLenia(null);
            }
            if (tieneValor(npNumCocinaS)) {
                encuestaCasa.setNumSemanalCocinaLenia(Integer.valueOf(npNumCocinaS));
            } else {
                encuestaCasa.setNumSemanalCocinaLenia(null);
            }
            if (tieneValor(npNumCocinaQ)) {
                encuestaCasa.setNumQuincenalCocinaLenia(Integer.valueOf(npNumCocinaQ));
            } else {
                encuestaCasa.setNumQuincenalCocinaLenia(null);
            }
            if (tieneValor(npNumCocinaM)) {
                encuestaCasa.setNumMensualCocinaLenia(Integer.valueOf(npNumCocinaM));
            } else {
                encuestaCasa.setNumMensualCocinaLenia(null);
            }
            if (tieneValor(npCantGallinas)) {
                encuestaCasa.setCantidadGallinas(Integer.valueOf(npCantGallinas));
            } else {
                encuestaCasa.setCantidadGallinas(null);
            }
            if (tieneValor(npCantPatos)) {
                encuestaCasa.setCantidadPatos(Integer.valueOf(npCantPatos));
            } else {
                encuestaCasa.setCantidadPatos(null);
            }
            if (tieneValor(npCantCerdos)) {
                encuestaCasa.setCantidadCerdos(Integer.valueOf(npCantCerdos));
            } else {
                encuestaCasa.setCantidadCerdos(null);
            }
            if (tieneValor(npCantCigarMadre)) {
                encuestaCasa.setCantidadCigarrilosMadre(Integer.valueOf(npCantCigarMadre));
            } else {
                encuestaCasa.setCantidadCigarrilosMadre(null);
            }
            if (tieneValor(npCantCigarPadre)) {
                encuestaCasa.setCantidadCigarrillosPadre(Integer.valueOf(npCantCigarPadre));
            } else {
                encuestaCasa.setCantidadCigarrillosPadre(null);
            }
            if (tieneValor(npCantOtrosFuma)) {
                encuestaCasa.setCantidadOtrosFuman(Integer.valueOf(npCantOtrosFuma));
            } else {
                encuestaCasa.setCantidadOtrosFuman(null);
            }
            if (tieneValor(npCantCigarOtrosF)) {
                encuestaCasa.setCantidadCigarrillosOtros(Integer.valueOf(npCantCigarOtrosF));
            } else {
                encuestaCasa.setCantidadCigarrillosOtros(null);
            }
            encuestaCasa.setRecordDate(new Date());
            encuestaCasa.setRecordUser(username);
            encuestaCasa.setDeviceid(infoMovil.getDeviceId());
            encuestaCasa.setEstado('0');
            encuestaCasa.setPasive('0');

            //Guarda el encuestaCasa
            estudiosAdapter.editarEncuestaCasa(encuestaCasa);
            Bundle arguments = new Bundle();
            arguments.putSerializable(Constants.CASA, encuestaCasa.getCasa());
            Intent i = new Intent(getApplicationContext(),
                    MenuCasaActivity.class);
            i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
            toast.show();
            finish();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (estudiosAdapter != null)
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
