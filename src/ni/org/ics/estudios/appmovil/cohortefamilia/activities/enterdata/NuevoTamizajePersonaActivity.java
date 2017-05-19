package ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuParticipanteActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.TamizajeForm;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.CartaConsentimiento;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.Tamizaje;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.BarcodePage;
import ni.org.ics.estudios.appmovil.wizard.model.LabelPage;
import ni.org.ics.estudios.appmovil.wizard.model.ModelCallbacks;
import ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage;
import ni.org.ics.estudios.appmovil.wizard.model.NumberPage;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage;
import ni.org.ics.estudios.appmovil.wizard.model.TextPage;
import ni.org.ics.estudios.appmovil.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.estudios.appmovil.wizard.ui.ReviewFragment;
import ni.org.ics.estudios.appmovil.wizard.ui.StepPagerStrip;


public class NuevoTamizajePersonaActivity extends FragmentActivity implements
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
    //private TamizajeFormLabels labels = new TamizajeFormLabels();
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    //private static CasaCohorteFamilia casa = new CasaCohorteFamilia();
	private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	private static CasaCohorteFamilia casaCHF = new CasaCohorteFamilia();

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
        setContentView(R.layout.activity_data_enter);
        casaCHF = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASA);
        settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		infoMovil = new DeviceInfo(NuevoTamizajePersonaActivity.this);
		//casa = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASA);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new TamizajeForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
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
    	//Guarda las respuestas en un bundle
		Map<String, String> mapa = mWizardModel.getAnswers();
		Bundle datos = new Bundle();
		DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		for (Map.Entry<String, String> entry : mapa.entrySet()){
			datos.putString(entry.getKey(), entry.getValue());
		}
		//Abre la base de datos
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		estudiosAdapter.open();
		
		//Obtener datos del bundle para el tamizaje
		String id = infoMovil.getId();
		//Recupera el estudio de la base de datos para el tamizaje
		Estudio estudio = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=1", null);
		String sexo = datos.getString(this.getString(R.string.sexo));
		Date fechaNacimiento = null;
		try {
			fechaNacimiento = mDateFormat.parse(datos.getString(this.getString(R.string.fechaNacimiento)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
		String aceptaTamizajePersona = datos.getString(this.getString(R.string.aceptaTamizajePersona));
		String razonNoAceptaTamizajePersona = datos.getString(this.getString(R.string.razonNoParticipaPersona));
		String criteriosInclusion = datos.getString(this.getString(R.string.criteriosInclusion));
		String enfermedad = datos.getString(this.getString(R.string.enfermedad));
		String dondeAsisteProblemasSalud = datos.getString(this.getString(R.string.dondeAsisteProblemasSalud));
		String otroCentroSalud = datos.getString(this.getString(R.string.otroCentroSalud));
		String puestoSalud = datos.getString(this.getString(R.string.puestoSalud));
		String aceptaAtenderCentro = datos.getString(this.getString(R.string.aceptaAtenderCentro));
		String esElegible = datos.getString(this.getString(R.string.esElegible));
		String aceptaParticipar = datos.getString(this.getString(R.string.aceptaParticipar));
		String razonNoAceptaParticipar = datos.getString(this.getString(R.string.razonNoAceptaParticipar));
		String asentimientoVerbal = datos.getString(this.getString(R.string.asentimientoVerbal));

	    
		//Crea un Nuevo Registro de tamizaje
    	Tamizaje t =  new Tamizaje();
    	t.setCodigo(id);
    	t.setEstudio(estudio);
    	if (!sexo.matches("")) {
			MessageResource catSexo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + sexo + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SEXO'", null);
			if (catSexo!=null) t.setSexo(catSexo.getCatKey());
		}
    	t.setFechaNacimiento(fechaNacimiento);
    	if (!aceptaTamizajePersona.matches("")) {
			MessageResource catAceptaTamizajePersona = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaTamizajePersona + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catAceptaTamizajePersona!=null) t.setAceptaTamizajePersona(catAceptaTamizajePersona.getCatKey().charAt(0));
		}
    	if (!razonNoAceptaTamizajePersona.matches("")) {
			MessageResource catRazonNoAceptaTamizajePersona = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaTamizajePersona + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
			if (catRazonNoAceptaTamizajePersona!=null) t.setRazonNoAceptaTamizajePersona(catRazonNoAceptaTamizajePersona.getCatKey());
		}
    	if (!criteriosInclusion.matches("")) {
    		String keysCriterios = "";
    		criteriosInclusion = criteriosInclusion.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
            List<MessageResource> mcriteriosInclusion = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + criteriosInclusion + "') and "
                    + CatalogosDBConstants.catRoot + "='CHF_CAT_CI'", null);
            for(MessageResource ms : mcriteriosInclusion) {
                keysCriterios += ms.getCatKey() + ",";
            }
            if (!keysCriterios.isEmpty())
                keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
			t.setCriteriosInclusion(keysCriterios);
		}
    	if (!enfermedad.matches("")) {
			MessageResource catEnfermedad = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfermedad + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catEnfermedad!=null) t.setEnfermedad(catEnfermedad.getCatKey());
		}
    	if (!dondeAsisteProblemasSalud.matches("")) {
			MessageResource catDondeAsisteProblemasSalud = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dondeAsisteProblemasSalud + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_DONDEASISTE'", null);
			if (catDondeAsisteProblemasSalud!=null) t.setDondeAsisteProblemasSalud(catDondeAsisteProblemasSalud.getCatKey());
		}
        t.setOtroCentroSalud(otroCentroSalud);
        if (!puestoSalud.matches("")) {
			MessageResource catPuestoSalud = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + puestoSalud + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_PUESTO'", null);
			if (catPuestoSalud!=null) t.setPuestoSalud(catPuestoSalud.getCatKey());
		}
        if (!aceptaAtenderCentro.matches("")) {
			MessageResource catAceptaAtenderCentro = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaAtenderCentro + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catAceptaAtenderCentro!=null) t.setAceptaAtenderCentro(catAceptaAtenderCentro.getCatKey().charAt(0));
		}
        if (!esElegible.matches("")) {
			MessageResource catEsElegible = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + esElegible + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catEsElegible!=null) t.setEsElegible(catEsElegible.getCatKey().charAt(0));
		}
        if (!aceptaParticipar.matches("")) {
			MessageResource catAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParticipar + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catAceptaParticipar!=null) t.setAceptaParticipar(catAceptaParticipar.getCatKey().charAt(0));
		}
        if (!razonNoAceptaParticipar.matches("")) {
			MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaParticipar + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
			if (catRazonNoAceptaParticipar!=null) t.setAceptaParticipar(catRazonNoAceptaParticipar.getCatKey().charAt(0));
		}
        if (!asentimientoVerbal.matches("")) {
			MessageResource catAsentimientoVerbal = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + asentimientoVerbal + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catAsentimientoVerbal!=null) t.setAsentimientoVerbal(catAsentimientoVerbal.getCatKey().charAt(0));
		}
	    
    	t.setRecordDate(new Date());
    	t.setRecordUser(username);
    	t.setDeviceid(infoMovil.getDeviceId());
    	t.setEstado('0');
    	t.setPasive('0');
    	//Inserta un Nuevo Registro de Tamizaje
    	estudiosAdapter.crearTamizaje(t);
    	
    	//Pregunta si es elegible
    	if (t.getAceptaParticipar()=='S') {
        	//Si la respuesta es si crea o busca un participante
    		//Obtener datos del bundle para el participante
    		
    		String participadoCohortePediatrica = datos.getString(this.getString(R.string.participadoCohortePediatrica));
    		String codigoCohorte = datos.getString(this.getString(R.string.codigoCohorte));
    		String codigoNuevoParticipante = datos.getString(this.getString(R.string.codigoNuevoParticipante));
    		String nombre1 = datos.getString(this.getString(R.string.nombre1));
    		String nombre2 = datos.getString(this.getString(R.string.nombre2));
    		String apellido1 = datos.getString(this.getString(R.string.apellido1));
    		String apellido2 = datos.getString(this.getString(R.string.apellido2));
    		String nombre1Padre = datos.getString(this.getString(R.string.nombre1Padre));
    		String nombre2Padre = datos.getString(this.getString(R.string.nombre2Padre));
    		String apellido1Padre = datos.getString(this.getString(R.string.apellido1Padre));
    		String apellido2Padre = datos.getString(this.getString(R.string.apellido2Padre));
    		String nombre1Madre = datos.getString(this.getString(R.string.nombre1Madre));
    		String nombre2Madre = datos.getString(this.getString(R.string.nombre2Madre));
    		String apellido1Madre = datos.getString(this.getString(R.string.apellido1Madre));
    		String apellido2Madre = datos.getString(this.getString(R.string.apellido2Madre));
    		
    		Participante participante;
    		MessageResource catParticipadoCohortePediatrica = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + participadoCohortePediatrica + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
    		//Pregunta si es de la cohorte pediatrica
    		if(catParticipadoCohortePediatrica.getCatKey().matches("S")){
    			//Si la respuesta es si, buscamos al participante
    			Integer codigo = Integer.parseInt(codigoCohorte);
    			participante = estudiosAdapter.getParticipante(MainDBConstants.codigo +" = "+ codigo , null);
    		}
    		else{
    			//Creamos un nuevo participante
    			participante = new Participante();
    			Integer codigo = Integer.parseInt(codigoNuevoParticipante);
    			participante.setCodigo(codigo);
    			participante.setNombre1(nombre1);
    			participante.setNombre2(nombre2);
    			participante.setApellido1(apellido1);
    			participante.setApellido2(apellido2);
    			participante.setNombre1Padre(nombre1Padre);
    			participante.setNombre2Padre(nombre2Padre);
    			participante.setApellido1Padre(apellido1Padre);
    			participante.setApellido2Padre(apellido2Padre);
    			participante.setNombre1Madre(nombre1Madre);
    			participante.setNombre2Madre(nombre2Madre);
    			participante.setApellido1Madre(apellido1Madre);
    			participante.setApellido2Madre(apellido2Madre);
    			participante.setSexo(t.getSexo());
    			participante.setFechaNac(t.getFechaNacimiento());
    			participante.setCasa(casaCHF.getCasa());
    			//Guarda nuevo participante
    			estudiosAdapter.crearParticipante(participante);
    		}
    		
    		//Obtener datos del bundle para el consentimiento
    		
    		String emancipado = datos.getString(this.getString(R.string.emancipado));
    		String nombre1Tutor = datos.getString(this.getString(R.string.nombre1Tutor));
    		String nombre2Tutor = datos.getString(this.getString(R.string.nombre2Tutor));
    		String apellido1Tutor = datos.getString(this.getString(R.string.apellido1Tutor));
    		String apellido2Tutor = datos.getString(this.getString(R.string.apellido2Tutor));
    		String relacionFamiliarTutor = datos.getString(this.getString(R.string.relacionFamiliarTutor));
    		String participanteOTutorAlfabeto = datos.getString(this.getString(R.string.participanteOTutorAlfabeto));
    		String testigoPresente = datos.getString(this.getString(R.string.testigoPresente));
    		String nombre1Testigo = datos.getString(this.getString(R.string.nombre1Testigo));
    		String nombre2Testigo = datos.getString(this.getString(R.string.nombre2Testigo));
    		String apellido1Testigo = datos.getString(this.getString(R.string.apellido1Testigo));
    		String apellido2Testigo = datos.getString(this.getString(R.string.apellido2Testigo));
    		String aceptaParteA = datos.getString(this.getString(R.string.aceptaParteA));
    		String motivoRechazoParteA = datos.getString(this.getString(R.string.motivoRechazoParteA));
    		String aceptaContactoFuturo = datos.getString(this.getString(R.string.aceptaContactoFuturo));
    		String aceptaParteB = datos.getString(this.getString(R.string.aceptaParteB));
    		String aceptaParteC = datos.getString(this.getString(R.string.aceptaParteC));
    	    
        	//Inserta un nuevo consentimiento
    		CartaConsentimiento cc = new CartaConsentimiento();
    		cc.setCodigo(id);
    		cc.setFechaFirma(new Date());
    		cc.setTamizaje(t);
    		cc.setParticipante(participante);
    		if (!emancipado.matches("")) {
    			MessageResource catEmancipado = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + emancipado + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
    			if (catEmancipado!=null) cc.setEmancipado(catEmancipado.getCatKey().charAt(0));
    		}
    		cc.setNombre1Tutor(nombre1Tutor);
    		cc.setNombre2Tutor(nombre2Tutor);
    		cc.setApellido1Tutor(apellido1Tutor);
    		cc.setApellido2Tutor(apellido2Tutor);
    		if (!relacionFamiliarTutor.matches("")) {
    			MessageResource catRelacionFamiliarTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relacionFamiliarTutor + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_RFTUTOR'", null);
    			if (catRelacionFamiliarTutor!=null) cc.setRelacionFamiliarTutor(catRelacionFamiliarTutor.getCatKey());
    		}
    		if (!participanteOTutorAlfabeto.matches("")) {
    			MessageResource catParticipanteOTutorAlfabeto = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + participanteOTutorAlfabeto + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
    			if (catParticipanteOTutorAlfabeto!=null) cc.setParticipanteOTutorAlfabeto(catParticipanteOTutorAlfabeto.getCatKey().charAt(0));
    		}
    		if (!testigoPresente.matches("")) {
    			MessageResource catTestigoPresente = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + testigoPresente + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
    			if (catTestigoPresente!=null) cc.setTestigoPresente(catTestigoPresente.getCatKey().charAt(0));
    		}

    	    cc.setNombre1Testigo(nombre1Testigo);
    	    cc.setNombre2Testigo(nombre2Testigo);
    	    cc.setApellido1Testigo(apellido1Testigo);
    	    cc.setApellido2Testigo(apellido2Testigo);
    	    if (!aceptaParteA.matches("")) {
    			MessageResource catAceptaParteA = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteA + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
    			if (catAceptaParteA!=null) cc.setAceptaParteA(catAceptaParteA.getCatKey().charAt(0));
    		}
    	    if (!motivoRechazoParteA.matches("")) {
    			MessageResource catMotivoRechazoParteA = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + motivoRechazoParteA + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
    			if (catMotivoRechazoParteA!=null) cc.setMotivoRechazoParteA(catMotivoRechazoParteA.getCatKey());
    		}
    	    if (!aceptaContactoFuturo.matches("")) {
    			MessageResource catAceptaContactoFuturo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaContactoFuturo + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
    			if (catAceptaContactoFuturo!=null) cc.setAceptaContactoFuturo(catAceptaContactoFuturo.getCatKey().charAt(0));
    		}
    	    if (!aceptaParteB.matches("")) {
    			MessageResource catAceptaParteB = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteB + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
    			if (catAceptaParteB!=null) cc.setAceptaParteB(catAceptaParteB.getCatKey().charAt(0));
    		}
    	    if (!aceptaParteC.matches("")) {
    			MessageResource catAceptaParteC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteC + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
    			if (catAceptaParteC!=null) cc.setAceptaParteC(catAceptaParteC.getCatKey().charAt(0));
    		}
   	    
        	estudiosAdapter.crearCartaConsentimiento(cc);
        	
        	Bundle arguments = new Bundle();
            if (participante!=null) arguments.putSerializable(Constants.PARTICIPANTE , participante);
            Intent i = new Intent(getApplicationContext(),
            		MenuParticipanteActivity.class);
            i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
    	}
    	else{
    		Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
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
