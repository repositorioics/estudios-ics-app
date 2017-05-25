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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuParticipanteActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.TamizajeForm;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.TamizajeFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.CartaConsentimiento;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.Tamizaje;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CalcularEdad;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.BarcodePage;
import ni.org.ics.estudios.appmovil.wizard.model.DatePage;
import ni.org.ics.estudios.appmovil.wizard.model.LabelPage;
import ni.org.ics.estudios.appmovil.wizard.model.ModelCallbacks;
import ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage;
import ni.org.ics.estudios.appmovil.wizard.model.NewDatePage;
import ni.org.ics.estudios.appmovil.wizard.model.NumberPage;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.SelectParticipantPage;
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
    private TamizajeFormLabels labels = new TamizajeFormLabels();
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    //private static CasaCohorteFamilia casa = new CasaCohorteFamilia();
	private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private boolean notificarCambios = true;
	private static CasaCohorteFamilia casaCHF = new CasaCohorteFamilia();
	private Integer edadAnios = 0;

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
    	try{
        updateModel(page);
    	updateConstrains();
        if (recalculateCutOffPage()) {
        	if (notificarCambios) mPagerAdapter.notifyDataSetChanged();
        	updateBottomBar();
        }
        notificarCambios = true;
    	}
    	catch(Exception e){
    		e.printStackTrace();
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
        
    }
    
    public void updateModel(Page page){
       	try{
			boolean visible = false;
			if (page.getTitle().equals(labels.getFechaNacimiento())) {
				DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date fechaNacimiento = null;
				try {
					fechaNacimiento = mDateFormat.parse(page.getData().getString(DatePage.SIMPLE_DATA_KEY));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
					toast.show();
					finish();
				}
				String[] edad = new CalcularEdad(fechaNacimiento).getEdad().split("/");
				edadAnios = Integer.parseInt(edad[0]);
				if(mWizardModel.findByKey(labels.getAceptaParticipar()).getData().getString(TextPage.SIMPLE_DATA_KEY)!=null){
					visible = mWizardModel.findByKey(labels.getAceptaParticipar()).getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
	    			if(edadAnios>5 && edadAnios<18){
	    				changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), visible);
	    				if(!visible) resetForm(97);
	    			}
	    			else{
	    				changeStatus(mWizardModel.findByKey(labels.getParticipadoCohortePediatrica()), visible);
	    				if(!visible) resetForm(96);
	    			}
	                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParticipar()), !visible);
	                notificarCambios = true;
	                onPageTreeChanged();
				}
			}
			if(page.getTitle().equals(labels.getAceptaTamizajePersona())){
	            visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
	            changeStatus(mWizardModel.findByKey(labels.getCriteriosInclusion()), visible);
	            changeStatus(mWizardModel.findByKey(labels.getRazonNoParticipaPersona()), !visible);
	            if(!visible) {
	            	resetForm(100);
	            	Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noAceptaTamizajePersona),Toast.LENGTH_LONG);
	    			toast.show();
	            }
	            notificarCambios = false;
	            onPageTreeChanged();
	        }
			if (page.getTitle().equals(labels.getDondeAsisteProblemasSalud())) {
				if(page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Centro de Salud Sócrates Flores")){
					changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
	                changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
					changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), false);
				}
				else if(page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro centro de salud")){
	            	changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), true);
	                changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
	                changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
	            }
	            else if(page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Puesto de Salud")){
	            	changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
	                changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), true);
	                changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
	            }
	            else{
	            	changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
	                changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
	                changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), true);
	            }
	            notificarCambios = false;
	            onPageTreeChanged();
	        }
			if(page.getTitle().equals(labels.getCriteriosInclusion())){
				ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
	            visible = test.size()>3;
	            changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), visible);
	            changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), visible);
	            changeStatus(mWizardModel.findByKey(labels.getEsElegible()), visible);
	            if(!visible){
	            	resetForm(99);
	            	Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noCumpleCriteriosInclusion),Toast.LENGTH_LONG);
	    			toast.show();
	            }
	            notificarCambios = false;
	            onPageTreeChanged();
	        }
			if(page.getTitle().equals(labels.getEsElegible())){
	            visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
	            changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), visible);
	            if(!visible){
	            	resetForm(98);
	            	Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noEsElegible),Toast.LENGTH_LONG);
	    			toast.show();
	            }
	            notificarCambios = false;
	            onPageTreeChanged();
	        }
			if (page.getTitle().equals(labels.getAceptaParticipar())) {
				visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
				if(edadAnios>5 && edadAnios<18){
					changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), visible);
				}
				else{
					changeStatus(mWizardModel.findByKey(labels.getParticipadoCohortePediatrica()), visible);
				}
	            changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParticipar()), !visible);
	            if(!visible){
	            	resetForm(97);
	            	Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noAceptaParticipar),Toast.LENGTH_LONG);
	    			toast.show();
	            }
	            notificarCambios = false;
	            onPageTreeChanged();
	        }
			if(page.getTitle().equals(labels.getAsentimientoVerbal())){
				visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
				changeStatus(mWizardModel.findByKey(labels.getParticipadoCohortePediatrica()), visible);
	            if(!visible) {
	            	resetForm(96);
	            	Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noDaAsentimiento),Toast.LENGTH_LONG);
	    			toast.show();
	            }
	            notificarCambios = false;
	            onPageTreeChanged();
	        }
			if(page.getTitle().equals(labels.getParticipadoCohortePediatrica())){
				visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
				changeStatus(mWizardModel.findByKey(labels.getCodigoCohorte()), visible);
				changeStatus(mWizardModel.findByKey(labels.getCodigoNuevoParticipante()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getNombre1()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getNombre2()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getApellido1()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getApellido2()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getNombre1Padre()), !visible && edadAnios<18);
				changeStatus(mWizardModel.findByKey(labels.getNombre2Padre()), !visible && edadAnios<18);
				changeStatus(mWizardModel.findByKey(labels.getApellido1Padre()), !visible && edadAnios<18);
				changeStatus(mWizardModel.findByKey(labels.getApellido2Padre()), !visible && edadAnios<18);
				changeStatus(mWizardModel.findByKey(labels.getNombre1Madre()), !visible && edadAnios<18);
				changeStatus(mWizardModel.findByKey(labels.getNombre2Madre()), !visible && edadAnios<18);
				changeStatus(mWizardModel.findByKey(labels.getApellido1Madre()), !visible && edadAnios<18);
				changeStatus(mWizardModel.findByKey(labels.getApellido2Madre()), !visible && edadAnios<18);

				if(edadAnios>18){
					changeStatus(mWizardModel.findByKey(labels.getEmancipado()), false);
					changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), false);
	    			changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), false);
	    			changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), false);
	    			changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), false);
	    			changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), false);
				}
				else{
					changeStatus(mWizardModel.findByKey(labels.getEmancipado()), true);
				}
				changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), true);
	            notificarCambios = false;
	            onPageTreeChanged();
	        }
			if(page.getTitle().equals(labels.getParticipanteOTutorAlfabeto())){
				visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
				changeStatus(mWizardModel.findByKey(labels.getAceptaParteA()), visible);
				changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible);
				changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible);
				changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible);
				changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), !visible);
	            notificarCambios = false;
	            if(!visible) {
	            	resetForm(95);
	            	Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noDaAsentimiento),Toast.LENGTH_LONG);
	    			toast.show();
	            }
	            onPageTreeChanged();
	        }
			if(page.getTitle().equals(labels.getEmancipado())){
				visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
				changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), !visible);
	            notificarCambios = false;
	            onPageTreeChanged();
	        }
			if(page.getTitle().equals(labels.getTestigoPresente())){
				visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
				changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), visible);
				changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), visible);
				changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), visible);
				changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), visible);
				changeStatus(mWizardModel.findByKey(labels.getAceptaParteA()), visible);
				notificarCambios = false;
	            onPageTreeChanged();
	        }
			if(page.getTitle().equals(labels.getAceptaParteA())){
				visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
				changeStatus(mWizardModel.findByKey(labels.getMotivoRechazoParteA()), !visible);
				changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible);
				changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible);
				changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible);
	            
	            notificarCambios = false;
	            onPageTreeChanged();
	        }
    	}catch (Exception ex){
            ex.printStackTrace();
        }
    	
    }
    
    public void resetForm(int preg){
    	if (preg>99) changeStatus(mWizardModel.findByKey(labels.getEnfermedad()), false);
    	if (preg>99) changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), false);
    	if (preg>98) changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
    	if (preg>98) changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
    	if (preg>98) changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), false);
    	if (preg>98) changeStatus(mWizardModel.findByKey(labels.getEsElegible()), false);
    	if (preg>97) changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), false);
    	if (preg>97) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParticipar()), false);
    	if (preg>96) changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getParticipadoCohortePediatrica()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getCodigoCohorte()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getCodigoNuevoParticipante()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre1()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre2()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido1()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido2()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre1Padre()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre2Padre()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido1Padre()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido2Padre()), false);
       	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre1Madre()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre2Madre()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido1Madre()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido2Madre()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getEmancipado()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), false);
    	if (preg>95) changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), false);
    	if (preg>94) changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), false);
    	if (preg>94) changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), false);
    	if (preg>94) changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), false);
    	if (preg>94) changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), false);
    	if (preg>94) changeStatus(mWizardModel.findByKey(labels.getAceptaParteA()), false);
    	if (preg>94) changeStatus(mWizardModel.findByKey(labels.getMotivoRechazoParteA()), false);
    	if (preg>94) changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), false);
    	if (preg>94) changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), false);
    	if (preg>94) changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), false);
    	if (preg>94) changeStatus(mWizardModel.findByKey(labels.getFinTamizajeLabel()), false);
    }
    
    public void changeStatus(Page page, boolean visible){
    	String clase = page.getClass().toString();
    	if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage")){
    		SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.BarcodePage")){
    		BarcodePage modifPage = (BarcodePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);;
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.LabelPage")){
    		LabelPage modifPage = (LabelPage) page; modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.TextPage")){
    		TextPage modifPage = (TextPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NumberPage")){
    		NumberPage modifPage = (NumberPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage")){
    		MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.DatePage")){
    		DatePage modifPage = (DatePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.SelectParticipantPage")){
    		SelectParticipantPage modifPage = (SelectParticipantPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.org.ics.estudios.appmovil.wizard.model.NewDatePage")){
    		NewDatePage modifPage = (NewDatePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    }
    
    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
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
    	if (tieneValor(sexo)) {
			MessageResource catSexo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + sexo + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SEXO'", null);
			if (catSexo!=null) t.setSexo(catSexo.getCatKey());
		}
    	t.setFechaNacimiento(fechaNacimiento);
    	if (tieneValor(aceptaTamizajePersona)) {
			MessageResource catAceptaTamizajePersona = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaTamizajePersona + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catAceptaTamizajePersona!=null) t.setAceptaTamizajePersona(catAceptaTamizajePersona.getCatKey());
		}
    	if (tieneValor(razonNoAceptaTamizajePersona)) {
			MessageResource catRazonNoAceptaTamizajePersona = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaTamizajePersona + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
			if (catRazonNoAceptaTamizajePersona!=null) t.setRazonNoAceptaTamizajePersona(catRazonNoAceptaTamizajePersona.getCatKey());
		}
    	if (tieneValor(criteriosInclusion)) {
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
    	if (tieneValor(enfermedad)) {
			MessageResource catEnfermedad = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + enfermedad + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catEnfermedad!=null) t.setEnfermedad(catEnfermedad.getCatKey());
		}
    	if (tieneValor(dondeAsisteProblemasSalud)) {
			MessageResource catDondeAsisteProblemasSalud = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + dondeAsisteProblemasSalud + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_DONDEASISTE'", null);
			if (catDondeAsisteProblemasSalud!=null) t.setDondeAsisteProblemasSalud(catDondeAsisteProblemasSalud.getCatKey());
		}
        if(tieneValor(otroCentroSalud)) t.setOtroCentroSalud(otroCentroSalud);
        if (tieneValor(puestoSalud)) {
			MessageResource catPuestoSalud = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + puestoSalud + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_PUESTO'", null);
			if (catPuestoSalud!=null) t.setPuestoSalud(catPuestoSalud.getCatKey());
		}
        if (tieneValor(aceptaAtenderCentro)) {
			MessageResource catAceptaAtenderCentro = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaAtenderCentro + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catAceptaAtenderCentro!=null) t.setAceptaAtenderCentro(catAceptaAtenderCentro.getCatKey());
		}
        if (tieneValor(esElegible)) {
			MessageResource catEsElegible = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + esElegible + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catEsElegible!=null) t.setEsElegible(catEsElegible.getCatKey());
		}
        if (tieneValor(aceptaParticipar)) {
			MessageResource catAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParticipar + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catAceptaParticipar!=null) t.setAceptaParticipar(catAceptaParticipar.getCatKey());
		}
        if (tieneValor(razonNoAceptaParticipar)) {
			MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNoAceptaParticipar + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_NPP'", null);
			if (catRazonNoAceptaParticipar!=null) t.setRazonNoAceptaParticipar(catRazonNoAceptaParticipar.getCatKey());
		}
        if (tieneValor(asentimientoVerbal)) {
			MessageResource catAsentimientoVerbal = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + asentimientoVerbal + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
			if (catAsentimientoVerbal!=null) t.setAsentimientoVerbal(catAsentimientoVerbal.getCatKey());
		}
	    
    	t.setRecordDate(new Date());
    	t.setRecordUser(username);
    	t.setDeviceid(infoMovil.getDeviceId());
    	t.setEstado('0');
    	t.setPasive('0');
    	//Inserta un Nuevo Registro de Tamizaje
    	estudiosAdapter.crearTamizaje(t);
    	//Pregunta si acepta realizar el tamizaje
    	if (t.getAceptaTamizajePersona().equals("S")) {
	    	//Pregunta si es elegible y acepta participar
	    	if (t.getAceptaParticipar().equals("S")) {
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
	    		Integer codigo = 0;
	    		if(catParticipadoCohortePediatrica.getCatKey().matches("S")){
	    			//Si la respuesta es si, buscamos al participante
	    			if (tieneValor(codigoCohorte)) codigo = Integer.parseInt(codigoCohorte);
	    			participante = estudiosAdapter.getParticipante(MainDBConstants.codigo +" = "+ codigo , null);
	    		}
	    		else{
	    			//Creamos un nuevo participante
	    			participante = new Participante();
	    			if (tieneValor(codigoNuevoParticipante)) codigo = Integer.parseInt(codigoNuevoParticipante);
	    			participante.setCodigo(codigo);
	    			if (tieneValor(nombre1)) participante.setNombre1(nombre1);
	    			if (tieneValor(nombre2)) participante.setNombre2(nombre2);
	    			if (tieneValor(apellido1)) participante.setApellido1(apellido1);
	    			if (tieneValor(apellido2)) participante.setApellido2(apellido2);
	    			if (tieneValor(nombre1Padre)) {
	    				if (tieneValor(nombre1Padre)) participante.setNombre1Padre(nombre1Padre);
	    				if (tieneValor(nombre2Padre)) participante.setNombre2Padre(nombre2Padre);
	        			if (tieneValor(apellido1Padre)) participante.setApellido1Padre(apellido1Padre);
	        			if (tieneValor(apellido2Padre)) participante.setApellido2Padre(apellido2Padre);
	        			if (tieneValor(nombre1Madre)) participante.setNombre1Madre(nombre1Madre);
	        			if (tieneValor(nombre2Madre)) participante.setNombre2Madre(nombre2Madre);
	        			if (tieneValor(apellido1Madre)) participante.setApellido1Madre(apellido1Madre);
	        			if (tieneValor(apellido2Madre)) participante.setApellido2Madre(apellido2Madre);
	    			}
	    			else{
	    				participante.setNombre1Padre("NA");
	    				participante.setNombre2Padre("NA");
	        			participante.setApellido1Padre("NA");
	        			participante.setApellido2Padre("NA");
	        			participante.setNombre1Madre("NA");
	        			participante.setNombre2Madre("NA");
	        			participante.setApellido1Madre("NA");
	        			participante.setApellido2Madre("NA");
	    			}
	    			participante.setSexo(t.getSexo());
	    			participante.setFechaNac(t.getFechaNacimiento());
	    			participante.setCasa(casaCHF.getCasa());
	    			participante.setRecordDate(new Date());
	    			participante.setRecordUser(username);
	    			participante.setDeviceid(infoMovil.getDeviceId());
	    			participante.setEstado('0');
	    			participante.setPasive('0');
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
	    		if (tieneValor(emancipado)) {
	    			MessageResource catEmancipado = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + emancipado + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
	    			if (catEmancipado!=null) cc.setEmancipado(catEmancipado.getCatKey());
	    		}
	    		if (tieneValor(nombre1Tutor)) cc.setNombre1Tutor(nombre1Tutor);
	    		if (tieneValor(nombre2Tutor)) cc.setNombre2Tutor(nombre2Tutor);
	    		if (tieneValor(apellido1Tutor)) cc.setApellido1Tutor(apellido1Tutor);
	    		if (tieneValor(apellido2Tutor)) cc.setApellido2Tutor(apellido2Tutor);
	    		if (tieneValor(relacionFamiliarTutor)) {
	    			MessageResource catRelacionFamiliarTutor = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + relacionFamiliarTutor + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_RFTUTOR'", null);
	    			if (catRelacionFamiliarTutor!=null) cc.setRelacionFamiliarTutor(catRelacionFamiliarTutor.getCatKey());
	    		}
	    		if (tieneValor(participanteOTutorAlfabeto)) {
	    			MessageResource catParticipanteOTutorAlfabeto = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + participanteOTutorAlfabeto + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
	    			if (catParticipanteOTutorAlfabeto!=null) cc.setParticipanteOTutorAlfabeto(catParticipanteOTutorAlfabeto.getCatKey());
	    		}
	    		if (tieneValor(testigoPresente)) {
	    			MessageResource catTestigoPresente = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + testigoPresente + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
	    			if (catTestigoPresente!=null) cc.setTestigoPresente(catTestigoPresente.getCatKey());
	    		}
	
	    		if (tieneValor(nombre1Testigo)) cc.setNombre1Testigo(nombre1Testigo);
	    		if (tieneValor(nombre2Testigo)) cc.setNombre2Testigo(nombre2Testigo);
	    		if (tieneValor(apellido1Testigo)) cc.setApellido1Testigo(apellido1Testigo);
	    		if (tieneValor(apellido2Testigo)) cc.setApellido2Testigo(apellido2Testigo);
	    	    if (tieneValor(aceptaParteA)) {
	    			MessageResource catAceptaParteA = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteA + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
	    			if (catAceptaParteA!=null) cc.setAceptaParteA(catAceptaParteA.getCatKey());
	    		}
	    	    if (tieneValor(motivoRechazoParteA)) {
	    			MessageResource catMotivoRechazoParteA = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + motivoRechazoParteA + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
	    			if (catMotivoRechazoParteA!=null) cc.setMotivoRechazoParteA(catMotivoRechazoParteA.getCatKey());
	    		}
	    	    if (tieneValor(aceptaContactoFuturo)) {
	    			MessageResource catAceptaContactoFuturo = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaContactoFuturo + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
	    			if (catAceptaContactoFuturo!=null) cc.setAceptaContactoFuturo(catAceptaContactoFuturo.getCatKey());
	    		}
	    	    if (tieneValor(aceptaParteB)) {
	    			MessageResource catAceptaParteB = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteB + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
	    			if (catAceptaParteB!=null) cc.setAceptaParteB(catAceptaParteB.getCatKey());
	    		}
	    	    if (tieneValor(aceptaParteC)) {
	    			MessageResource catAceptaParteC = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + aceptaParteC + "' and " + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
	    			if (catAceptaParteC!=null) cc.setAceptaParteC(catAceptaParteC.getCatKey());
	    		}
	    	    cc.setRecordDate(new Date());
	        	cc.setRecordUser(username);
	        	cc.setDeviceid(infoMovil.getDeviceId());
	        	cc.setEstado('0');
	        	cc.setPasive('0');
	    	    //Ingresa la carta
	        	estudiosAdapter.crearCartaConsentimiento(cc);
	        	//Crea un nuevo participantes de chf
	        	ParticipanteCohorteFamilia pchf = new ParticipanteCohorteFamilia();
	        	pchf.setParticipanteCHF(id);
	        	pchf.setParticipante(participante);
	        	pchf.setCasaCHF(casaCHF);
	        	pchf.setRecordDate(new Date());
	        	pchf.setRecordUser(username);
	        	pchf.setDeviceid(infoMovil.getDeviceId());
	        	pchf.setEstado('0');
	        	pchf.setPasive('0');
	        	//Guarda el participante de chf
	        	estudiosAdapter.crearParticipanteCohorteFamilia(pchf);
	        	//Cierra la base de datos
	        	estudiosAdapter.close();
	        	
	        	//Abre el menu para este participante
	        	Bundle arguments = new Bundle();
	            if (pchf!=null) arguments.putSerializable(Constants.PARTICIPANTE , pchf);
	            Intent i = new Intent(getApplicationContext(),
	            		MenuParticipanteActivity.class);
	            i.putExtras(arguments);
	            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(i);
	            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
	    		toast.show();
	            finish();
	    	}
	    	else{
	    		Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.noAceptaParticipar),Toast.LENGTH_LONG);
	    		toast.show();
	    		finish();
	    	}
    	}
    	else{
    		Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.noAceptaTamizajePersona),Toast.LENGTH_LONG);
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
