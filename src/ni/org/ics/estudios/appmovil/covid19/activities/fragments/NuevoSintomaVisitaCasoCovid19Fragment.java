package ni.org.ics.estudios.appmovil.covid19.activities.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaSintomasVisitaCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.covid19.SintomasVisitaCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaSeguimientoCasoCovid19;
import ni.org.ics.estudios.appmovil.multiselector.gui.MultiSpinner;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import org.joda.time.DateMidnight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NuevoSintomaVisitaCasoCovid19Fragment extends Fragment {

	private static final String TAG = "NuevoSintomaVisitaCasoCovid19Fragment";
	protected static final int DATE_SINTOMA = 101;
	protected static final int DATE_FIF = 102;
	protected static final int DATE_INITX = 103;
	protected static final int DATE_ALTA = 104;

	private EstudiosAdapter estudiosAdapter;
	//Viene de la llamada
	private VisitaSeguimientoCasoCovid19 mVisitaSeguimientoCaso;
	//Objeto que se va a hacer
	private SintomasVisitaCasoCovid19 mSintoma = new SintomasVisitaCasoCovid19();
	//Catalogos
	private List<MessageResource> mCatalogoSnd;
	private List<MessageResource> mCatalogoSiNo;
	private List<MessageResource> mCatalogoIntensidad;
	private List<String> mMedicamentos = new ArrayList<String>();
	private List<String> mFactoresRiesgo = new ArrayList<String>();
	private List<VisitaSeguimientoCasoCovid19> mVisitasSeguimientoCasoCovid19 = new ArrayList<VisitaSeguimientoCasoCovid19>();
	private List<SintomasVisitaCasoCovid19> mSintomasVisitaCasoCovid19 = new ArrayList<SintomasVisitaCasoCovid19>();
	//Widgets en el View
	private TextView mTitleView;
	private EditText mNameView;
	private EditText inputFechaSintoma;
	private ImageButton mButtonChangeFechaSintoma;
	private Spinner spinFiebre;
	//private TextView textFif;
	//private EditText inputFif;
	//private ImageButton mButtonChangeFif;
	private Spinner spinFiebreCuantificada;
	private TextView textValorFiebreCuantificada;
	private EditText inputValorFiebreCuantificada;
	private Spinner spinDolorCabeza;
	private Spinner spinDolorArticular;
	private Spinner spinDolorMuscular;
	private Spinner spinDificultadRespiratoria;
	private Spinner spinSecrecionNasal;
	private Spinner spinTos;
	private Spinner spinPocoApetito;
	private Spinner spinDolorGarganta;
	private Spinner spinPicor;
	private Spinner spinCongestionNasal;
	private Spinner spinRash;
	private Spinner spinUrticaria;
	private Spinner spinConjuntivitis;
	private Spinner spinExpectoracion;
	private Spinner spinDiarrea;
	private Spinner spinVomito;
	private Spinner spinFatiga;
	private Spinner spinRespiracionRuidosa;
	private Spinner spinRespiracionRapida;
	private Spinner spinPerdidaOlfato;
	private Spinner spinPerdidaGusto;
	private Spinner spinDesmayos;
	private Spinner spinSensacionPA;
	private Spinner spinDolorPecho;
	private Spinner spinSensacionFA;
	private Spinner spinQuedoCama;
	private Spinner spinTratamiento;
	private TextView textFechaInicioTx;
	private EditText inputFechaInicioTx;
	private ImageButton fechaInicioTx_button;
	private MultiSpinner spinCualMedicamento;

	private TextView textCualMedicamento;
	private TextView textOtroMedicamento;
	private EditText inputOtroMedicamento;
	private Spinner spinAntibiotico;
	private TextView textPrescritoMedico;
	private Spinner spinPrescritoMedico;
	private MultiSpinner spinFactorRiesgo;
	private TextView textOtroFactorRiesgo;
	private EditText inputOtroFactorRiesgo;
	private Spinner spinOtraPersonaEnferma;
	private TextView textCuantasPersonas;
	private EditText inputCuantasPersonas;
	private Spinner spinTrasladoHospital;
	private TextView textCualHospital;
	private EditText inputCualHospital;
	private Spinner spinHospitalizado;
	private TextView textHospitalizado;
	private TextView textFechaAlta;
	private EditText inputFechaAlta;
	private ImageButton fechaAlta_button;

	//private Spinner spinOseltamivir;
	//private TextView textCualAntibiotico;
	//private EditText inputCualAntibiotico;
	//intensidad sintomas
	private Spinner spinFiebreIntensidad;
	private Spinner spinDolorCabezaIntensidad;
	private Spinner spinDolorArticularIntensidad;
	private Spinner spinDolorMuscularIntensidad;
	private Spinner spinSecrecionNasalIntensidad;
	private Spinner spinTosIntensidad;
	private Spinner spinDolorGargantaIntensidad;
	private TextView textFiebreIntensidad;
	private TextView textDolorCabezaIntensidad;
	private TextView textDolorArticularIntensidad;
	private TextView textDolorMuscularIntensidad;
	private TextView textSecrecionNasalIntensidad;
	private TextView textTosIntensidad;
	private TextView textDolorGargantaIntensidad;

	private Button mSaveView;

	//Variables donde se captura la entrada de datos
	private String fechaSintoma;
	private String fiebre;
	private String fiebreIntensidad;
	//private String fif;
	private String fiebreCuantificada;
	private Float valorFiebreCuantificada;
	private String dolorCabeza;
	private String dolorCabezaIntensidad;
	private String dolorArticular;
	private String dolorArticularIntensidad;
	private String dolorMuscular;
	private String dolorMuscularIntensidad;
	private String dificultadRespiratoria;
	private String secrecionNasal;
	private String secrecionNasalIntensidad;
	private String tos;
	private String tosIntensidad;
	private String pocoApetito;
	private String dolorGarganta;
	private String dolorGargantaIntensidad;
	private String picorGarganta;
	private String congestionNasal;
	private String rash;
	private String urticaria;
	private String conjuntivitis;
	private String expectoracion;
	private String diarrea;
	private String vomito;
	private String fatiga;
	private String respiracionRuidosa;
	private String respiracionRapida;
	private String perdidaOlfato;
	private String perdidaGusto;
	private String desmayos;
	private String sensacionPechoApretado;
	private String dolorPecho;
	private String sensacionFaltaAire;
	private String quedoCama;
	private String tratamiento;
	private String fechaInicioTx;
	private String cualMedicamento;
	private String otroMedicamento;
	private String antibiotico;
	private String prescritoMedico;
	private String factorRiesgo;
	private String otroFactorRiesgo;
	private String otraPersonaEnferma;
	private Integer cuantasPersonas;
	private String trasladoHospital;
	private String cualHospital;
	private String hospitalizado;
	private String fechaAlta;
	//private String oseltamivir;
	//private String cualAntibiotico;

	final Calendar c = Calendar.getInstance();
	//Para el id
	private DeviceInfo infoMovil;
	private String username;
	private SharedPreferences settings;


    public static NuevoSintomaVisitaCasoCovid19Fragment create() {
        NuevoSintomaVisitaCasoCovid19Fragment fragment = new NuevoSintomaVisitaCasoCovid19Fragment();
        return fragment;
    }

    public NuevoSintomaVisitaCasoCovid19Fragment() {
    	
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
		mVisitaSeguimientoCaso = (VisitaSeguimientoCasoCovid19) getActivity().getIntent().getExtras().getSerializable(Constants.VISITA);
        infoMovil = new DeviceInfo(getActivity());
        settings =
				PreferenceManager.getDefaultSharedPreferences(getActivity());
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		new FetchCatalogosTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nuevo_sintoma_covid19, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((EditText) rootView.findViewById(R.id.inputCodigoParticipanteCaso));
        mNameView.setText(mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getParticipante().getNombreCompleto());
        mNameView.setEnabled(false);
        inputFechaSintoma = (EditText) rootView.findViewById(R.id.inputFechaSintoma);
        if (mVisitaSeguimientoCaso.getFechaVisita().compareTo(new Date())<0){
            c.setTime(mVisitaSeguimientoCaso.getFechaVisita());
        }
        inputFechaSintoma.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)<10? "0"+c.get(Calendar.DAY_OF_MONTH):c.get(Calendar.DAY_OF_MONTH))+"/"+
        							String.valueOf((c.get(Calendar.MONTH)+1)<10? "0"+(c.get(Calendar.MONTH)+1):(c.get(Calendar.MONTH)+1))+"/"+String.valueOf(c.get(Calendar.YEAR)));
        fechaSintoma = inputFechaSintoma.getText().toString();
        mButtonChangeFechaSintoma = (ImageButton) rootView.findViewById(R.id.fecha_sintoma_button);
        mButtonChangeFechaSintoma.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_SINTOMA);
			}
		});
        spinFiebre = (Spinner) rootView.findViewById(R.id.spinFiebre);
        spinFiebre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	fiebre = mr.getCatKey();
	        	if(fiebre.equals(Constants.YESKEYSND)){
	        		/*textFif.setVisibility(View.VISIBLE);
	        		inputFif.setVisibility(View.VISIBLE);
	        		mButtonChangeFif.setVisibility(View.VISIBLE);*/
	        		spinFiebre.setBackgroundColor(Color.RED);
	        		spinFiebreIntensidad.setVisibility(View.VISIBLE);
					textFiebreIntensidad.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		/*textFif.setVisibility(View.GONE);
	        		inputFif.setVisibility(View.GONE);
	        		mButtonChangeFif.setVisibility(View.GONE);
	        		inputFif.setText("");
	        		fif = "";*/
	        		spinFiebre.setBackgroundColor(Color.WHITE);
					spinFiebreIntensidad.setVisibility(View.GONE);
					textFiebreIntensidad.setVisibility(View.GONE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        /*textFif = (TextView) rootView.findViewById(R.id.textFif);
        textFif.setVisibility(View.GONE);
        inputFif = (EditText) rootView.findViewById(R.id.inputFif);
        inputFif.setVisibility(View.GONE);
        mButtonChangeFif = (ImageButton) rootView.findViewById(R.id.fif_button);
        mButtonChangeFif.setVisibility(View.GONE);
        mButtonChangeFif.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_FIF);
			}
		});*/
        spinFiebreCuantificada = (Spinner) rootView.findViewById(R.id.spinFiebreCuantificada);
        spinFiebreCuantificada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	fiebreCuantificada = mr.getCatKey();
	        	if(fiebreCuantificada.equals(Constants.YESKEYSND)){
	        		textValorFiebreCuantificada.setVisibility(View.VISIBLE);
	        		inputValorFiebreCuantificada.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		textValorFiebreCuantificada.setVisibility(View.GONE);
	        		inputValorFiebreCuantificada.setVisibility(View.GONE);
	        		inputValorFiebreCuantificada.setText("");
	        		valorFiebreCuantificada = null;
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        textValorFiebreCuantificada = (TextView) rootView.findViewById(R.id.textValorFiebreCuantificada);
        textValorFiebreCuantificada.setVisibility(View.GONE);
        inputValorFiebreCuantificada = (EditText) rootView.findViewById(R.id.inputValorFiebreCuantificada);
        inputValorFiebreCuantificada.setVisibility(View.GONE);
        inputValorFiebreCuantificada.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
			@Override
			public void afterTextChanged(Editable editable) {
				try{
					valorFiebreCuantificada = Float.valueOf(inputValorFiebreCuantificada.getText().toString());
					if (valorFiebreCuantificada<34 || valorFiebreCuantificada>44) inputValorFiebreCuantificada.setError(getString(R.string.wrongTemp,34,44));
					inputValorFiebreCuantificada.requestFocus();
				}
				catch(Exception e){
					inputValorFiebreCuantificada.setError(e.toString());
					inputValorFiebreCuantificada.requestFocus();
					return;
				}
			}

		});
        spinDolorCabeza = (Spinner) rootView.findViewById(R.id.spinDolorCabeza);
        spinDolorCabeza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	dolorCabeza = mr.getCatKey();
	        	if(dolorCabeza.equals(Constants.YESKEYSND)){
	        		spinDolorCabeza.setBackgroundColor(Color.RED);
					spinDolorCabezaIntensidad.setVisibility(View.VISIBLE);
					textDolorCabezaIntensidad.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		spinDolorCabeza.setBackgroundColor(Color.WHITE);
					spinDolorCabezaIntensidad.setVisibility(View.GONE);
					textDolorCabezaIntensidad.setVisibility(View.GONE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        spinDolorArticular = (Spinner) rootView.findViewById(R.id.spinDolorArticular);
        spinDolorArticular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	dolorArticular = mr.getCatKey();
	        	if(dolorArticular.equals(Constants.YESKEYSND)){
	        		spinDolorArticular.setBackgroundColor(Color.RED);
					spinDolorArticularIntensidad.setVisibility(View.VISIBLE);
					textDolorArticularIntensidad.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		spinDolorArticular.setBackgroundColor(Color.WHITE);
					spinDolorArticularIntensidad.setVisibility(View.GONE);
					textDolorArticularIntensidad.setVisibility(View.GONE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        spinDolorMuscular = (Spinner) rootView.findViewById(R.id.spinDolorMuscular);
        spinDolorMuscular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	dolorMuscular = mr.getCatKey();
	        	if(dolorMuscular.equals(Constants.YESKEYSND)){
	        		spinDolorMuscular.setBackgroundColor(Color.RED);
					spinDolorMuscularIntensidad.setVisibility(View.VISIBLE);
					textDolorMuscularIntensidad.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		spinDolorMuscular.setBackgroundColor(Color.WHITE);
					spinDolorMuscularIntensidad.setVisibility(View.GONE);
					textDolorMuscularIntensidad.setVisibility(View.GONE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        spinDificultadRespiratoria = (Spinner) rootView.findViewById(R.id.spinDificultadRespiratoria);
        spinDificultadRespiratoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	dificultadRespiratoria = mr.getCatKey();
	        	if(dificultadRespiratoria.equals(Constants.YESKEYSND)){
	        		spinDificultadRespiratoria.setBackgroundColor(Color.RED);
	        	}
	        	else{
	        		spinDificultadRespiratoria.setBackgroundColor(Color.WHITE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        
        spinSecrecionNasal = (Spinner) rootView.findViewById(R.id.spinSecrecionNasal);
        spinSecrecionNasal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	secrecionNasal = mr.getCatKey();
	        	if(secrecionNasal.equals(Constants.YESKEYSND)){
	        		spinSecrecionNasal.setBackgroundColor(Color.RED);
					spinSecrecionNasalIntensidad.setVisibility(View.VISIBLE);
					textSecrecionNasalIntensidad.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		spinSecrecionNasal.setBackgroundColor(Color.WHITE);
					spinSecrecionNasalIntensidad.setVisibility(View.GONE);
					textSecrecionNasalIntensidad.setVisibility(View.GONE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        spinTos = (Spinner) rootView.findViewById(R.id.spinTos);
        spinTos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	tos = mr.getCatKey();
	        	if(tos.equals(Constants.YESKEYSND)){
	        		spinTos.setBackgroundColor(Color.RED);
					spinTosIntensidad.setVisibility(View.VISIBLE);
					textTosIntensidad.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		spinTos.setBackgroundColor(Color.WHITE);
					spinTosIntensidad.setVisibility(View.GONE);
					textTosIntensidad.setVisibility(View.GONE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        spinPocoApetito = (Spinner) rootView.findViewById(R.id.spinPocoApetito);
        spinPocoApetito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	pocoApetito = mr.getCatKey();
	        	if(pocoApetito.equals(Constants.YESKEYSND)){
	        		spinPocoApetito.setBackgroundColor(Color.RED);
	        	}
	        	else{
	        		spinPocoApetito.setBackgroundColor(Color.WHITE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        spinDolorGarganta = (Spinner) rootView.findViewById(R.id.spinDolorGarganta);
        spinDolorGarganta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	dolorGarganta = mr.getCatKey();
	        	if(dolorGarganta.equals(Constants.YESKEYSND)){
	        		spinDolorGarganta.setBackgroundColor(Color.RED);
					spinDolorGargantaIntensidad.setVisibility(View.VISIBLE);
					textDolorGargantaIntensidad.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		spinDolorGarganta.setBackgroundColor(Color.WHITE);
					spinDolorGargantaIntensidad.setVisibility(View.GONE);
					textDolorGargantaIntensidad.setVisibility(View.GONE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
		spinPicor = (Spinner) rootView.findViewById(R.id.spinPicor);
		spinPicor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				picorGarganta = mr.getCatKey();
				if(picorGarganta.equals(Constants.YESKEYSND)){
					spinPicor.setBackgroundColor(Color.RED);
				}
				else{
					spinPicor.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spinCongestionNasal = (Spinner) rootView.findViewById(R.id.spinCongestionNasal);
		spinCongestionNasal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				congestionNasal = mr.getCatKey();
				if(congestionNasal.equals(Constants.YESKEYSND)){
					spinCongestionNasal.setBackgroundColor(Color.RED);
				}
				else{
					spinCongestionNasal.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spinRash = (Spinner) rootView.findViewById(R.id.spinRash);
		spinRash.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				rash = mr.getCatKey();
				if(rash.equals(Constants.YESKEYSND)){
					spinRash.setBackgroundColor(Color.RED);
				}
				else{
					spinRash.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinUrticaria = (Spinner) rootView.findViewById(R.id.spinUrticaria);
		spinUrticaria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				urticaria = mr.getCatKey();
				if(urticaria.equals(Constants.YESKEYSND)){
					spinUrticaria.setBackgroundColor(Color.RED);
				}
				else{
					spinUrticaria.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinConjuntivitis = (Spinner) rootView.findViewById(R.id.spinConjuntivitis);
		spinConjuntivitis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				conjuntivitis = mr.getCatKey();
				if(conjuntivitis.equals(Constants.YESKEYSND)){
					spinConjuntivitis.setBackgroundColor(Color.RED);
				}
				else{
					spinConjuntivitis.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinExpectoracion = (Spinner) rootView.findViewById(R.id.spinExpectoracion);
		spinExpectoracion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				expectoracion = mr.getCatKey();
				if(expectoracion.equals(Constants.YESKEYSND)){
					spinExpectoracion.setBackgroundColor(Color.RED);
				}
				else{
					spinExpectoracion.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinDiarrea = (Spinner) rootView.findViewById(R.id.spinDiarrea);
        spinDiarrea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	diarrea = mr.getCatKey();
	        	if(diarrea.equals(Constants.YESKEYSND)){
	        		spinDiarrea.setBackgroundColor(Color.RED);
	        	}
	        	else{
	        		spinDiarrea.setBackgroundColor(Color.WHITE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
		spinVomito = (Spinner) rootView.findViewById(R.id.spinVomito);
		spinVomito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				vomito = mr.getCatKey();
				if(vomito.equals(Constants.YESKEYSND)){
					spinVomito.setBackgroundColor(Color.RED);
				}
				else{
					spinVomito.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinFatiga = (Spinner) rootView.findViewById(R.id.spinFatiga);
		spinFatiga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				fatiga = mr.getCatKey();
				if(fatiga.equals(Constants.YESKEYSND)){
					spinFatiga.setBackgroundColor(Color.RED);
				}
				else{
					spinFatiga.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
        spinRespiracionRuidosa = (Spinner) rootView.findViewById(R.id.spinRespiracionRuidosa);
        spinRespiracionRuidosa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	respiracionRuidosa = mr.getCatKey();
	        	if(respiracionRuidosa.equals(Constants.YESKEYSND)){
	        		spinRespiracionRuidosa.setBackgroundColor(Color.RED);
	        	}
	        	else{
	        		spinRespiracionRuidosa.setBackgroundColor(Color.WHITE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        spinRespiracionRapida = (Spinner) rootView.findViewById(R.id.spinRespiracionRapida);
        spinRespiracionRapida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                respiracionRapida = mr.getCatKey();
                if(respiracionRapida.equals(Constants.YESKEYSND)){
                    spinRespiracionRapida.setBackgroundColor(Color.RED);
                }
                else{
                    spinRespiracionRapida.setBackgroundColor(Color.WHITE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
		spinPerdidaOlfato = (Spinner) rootView.findViewById(R.id.spinPerdidaOlfato);
		spinPerdidaOlfato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				perdidaOlfato = mr.getCatKey();
				if(perdidaOlfato.equals(Constants.YESKEYSND)){
					spinPerdidaOlfato.setBackgroundColor(Color.RED);
				}
				else{
					spinPerdidaOlfato.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinPerdidaGusto = (Spinner) rootView.findViewById(R.id.spinPerdidaGusto);
		spinPerdidaGusto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				perdidaGusto = mr.getCatKey();
				if(perdidaGusto.equals(Constants.YESKEYSND)){
					spinPerdidaGusto.setBackgroundColor(Color.RED);
				}
				else{
					spinPerdidaGusto.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinDesmayos = (Spinner) rootView.findViewById(R.id.spinDesmayos);
		spinDesmayos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				desmayos = mr.getCatKey();
				if(desmayos.equals(Constants.YESKEYSND)){
					spinDesmayos.setBackgroundColor(Color.RED);
				}
				else{
					spinDesmayos.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinSensacionPA = (Spinner) rootView.findViewById(R.id.spinSensacionPA);
		spinSensacionPA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				sensacionPechoApretado = mr.getCatKey();
				if(sensacionPechoApretado.equals(Constants.YESKEYSND)){
					spinSensacionPA.setBackgroundColor(Color.RED);
				}
				else{
					spinSensacionPA.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinDolorPecho = (Spinner) rootView.findViewById(R.id.spinDolorPecho);
		spinDolorPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				dolorPecho = mr.getCatKey();
				if(dolorPecho.equals(Constants.YESKEYSND)){
					spinDolorPecho.setBackgroundColor(Color.RED);
				}
				else{
					spinDolorPecho.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinSensacionFA = (Spinner) rootView.findViewById(R.id.spinSensacionFA);
		spinSensacionFA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				sensacionFaltaAire = mr.getCatKey();
				if(sensacionFaltaAire.equals(Constants.YESKEYSND)){
					spinSensacionFA.setBackgroundColor(Color.RED);
				}
				else{
					spinSensacionFA.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinQuedoCama = (Spinner) rootView.findViewById(R.id.spinQuedoCama);
		spinQuedoCama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				quedoCama = mr.getCatKey();
				if(quedoCama.equals(Constants.YESKEYSND)){
					spinQuedoCama.setBackgroundColor(Color.RED);
				}
				else{
					spinQuedoCama.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		textFechaInicioTx = (TextView) rootView.findViewById(R.id.textFechaInicioTx);
		textFechaInicioTx.setVisibility(View.GONE);
		inputFechaInicioTx = (EditText) rootView.findViewById(R.id.inputFechaInicioTx);
		inputFechaInicioTx.setVisibility(View.GONE);
		fechaInicioTx_button = (ImageButton) rootView.findViewById(R.id.fechaInicioTx_button);
		fechaInicioTx_button.setVisibility(View.GONE);
		fechaInicioTx_button.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_INITX);
			}
		});
		textCualMedicamento = (TextView) rootView.findViewById(R.id.textCualMedicamento);
		textCualMedicamento.setVisibility(View.GONE);
		spinCualMedicamento = (MultiSpinner) rootView.findViewById(R.id.spinCualMedicamento);
		spinCualMedicamento.setVisibility(View.GONE);

		textOtroMedicamento = (TextView) rootView.findViewById(R.id.textOtroMedicamento);
		textOtroMedicamento.setVisibility(View.GONE);
		inputOtroMedicamento = (EditText) rootView.findViewById(R.id.inputOtroMedicamento);
		inputOtroMedicamento.setVisibility(View.GONE);
		inputOtroMedicamento.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
			}
			@Override
			public void afterTextChanged(Editable editable) {
				try{
					otroMedicamento = inputOtroMedicamento.getText().toString();
				}
				catch(Exception e){
					inputOtroMedicamento.setError(e.toString());
					inputOtroMedicamento.requestFocus();
					return;
				}
			}

		});

		spinTratamiento = (Spinner) rootView.findViewById(R.id.spinTratamiento);
		spinTratamiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				tratamiento = mr.getCatKey();
				if(tratamiento.equals(Constants.YESKEYSND)){
					spinTratamiento.setBackgroundColor(Color.RED);
					inputFechaInicioTx.setVisibility(View.VISIBLE);
					textFechaInicioTx.setVisibility(View.VISIBLE);
					fechaInicioTx_button.setVisibility(View.VISIBLE);
					textCualMedicamento.setVisibility(View.VISIBLE);
					spinCualMedicamento.setVisibility(View.VISIBLE);
					textOtroMedicamento.setVisibility(View.GONE);
					inputOtroMedicamento.setVisibility(View.GONE);

				}
				else{
					spinTratamiento.setBackgroundColor(Color.WHITE);
					inputFechaInicioTx.setVisibility(View.GONE);
					textFechaInicioTx.setVisibility(View.GONE);
					fechaInicioTx_button.setVisibility(View.GONE);
					textCualMedicamento.setVisibility(View.GONE);
					spinCualMedicamento.setVisibility(View.GONE);
					textOtroMedicamento.setVisibility(View.GONE);
					inputOtroMedicamento.setVisibility(View.GONE);
					fechaInicioTx = null;
					cualMedicamento = null;
					otroMedicamento = null;

				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
        spinAntibiotico = (Spinner) rootView.findViewById(R.id.spinAntibiotico);
        spinAntibiotico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	antibiotico = mr.getCatKey();
	        	if(antibiotico.equals(Constants.YESKEYSND)){
	        		textPrescritoMedico.setVisibility(View.VISIBLE);
	        		spinPrescritoMedico.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		textPrescritoMedico.setVisibility(View.GONE);
	        		spinPrescritoMedico.setVisibility(View.GONE);
	        		prescritoMedico = null;
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        textPrescritoMedico = (TextView) rootView.findViewById(R.id.textPrescritoMedico);
        textPrescritoMedico.setVisibility(View.GONE);
        spinPrescritoMedico = (Spinner) rootView.findViewById(R.id.spinPrescritoMedico);
        spinPrescritoMedico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	prescritoMedico = mr.getCatKey();
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        spinPrescritoMedico.setVisibility(View.GONE);

		spinFactorRiesgo = (MultiSpinner) rootView.findViewById(R.id.spinFactorRiesgo);
		if (!mVisitaSeguimientoCaso.getVisita().equals("1")) spinFactorRiesgo.setVisibility(View.GONE);

		textOtroFactorRiesgo = (TextView) rootView.findViewById(R.id.textOtroFactorRiesgo);
		textOtroFactorRiesgo.setVisibility(View.GONE);
		inputOtroFactorRiesgo = (EditText) rootView.findViewById(R.id.inputOtroFactorRiesgo);
		inputOtroFactorRiesgo.setVisibility(View.GONE);
		inputOtroFactorRiesgo.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
			}
			@Override
			public void afterTextChanged(Editable editable) {
				try{
					otroFactorRiesgo = inputOtroFactorRiesgo.getText().toString();
				}
				catch(Exception e){
					inputOtroFactorRiesgo.setError(e.toString());
					inputOtroFactorRiesgo.requestFocus();
					return;
				}
			}

		});


		spinOtraPersonaEnferma = (Spinner) rootView.findViewById(R.id.spinOtraPersonaEnferma);
		spinOtraPersonaEnferma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				otraPersonaEnferma = mr.getCatKey();
				if(otraPersonaEnferma.equals(Constants.YESKEYSND)){
					spinOtraPersonaEnferma.setBackgroundColor(Color.RED);
					textCuantasPersonas.setVisibility(View.VISIBLE);
					inputCuantasPersonas.setVisibility(View.VISIBLE);
				}
				else{
					spinOtraPersonaEnferma.setBackgroundColor(Color.WHITE);
					textCuantasPersonas.setVisibility(View.GONE);
					inputCuantasPersonas.setVisibility(View.GONE);
					cuantasPersonas = null;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		textCuantasPersonas = (TextView) rootView.findViewById(R.id.textCuantasPersonas);
		textCuantasPersonas.setVisibility(View.GONE);
		inputCuantasPersonas = (EditText) rootView.findViewById(R.id.inputCuantasPersonas);
		inputCuantasPersonas.setVisibility(View.GONE);
		inputCuantasPersonas.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
			}
			@Override
			public void afterTextChanged(Editable editable) {
				try{
					cuantasPersonas = Integer.valueOf(inputCuantasPersonas.getText().toString());
				}
				catch(Exception e){
					inputCuantasPersonas.setError(e.toString());
					inputCuantasPersonas.requestFocus();
					return;
				}
			}

		});
		textCualHospital = (TextView) rootView.findViewById(R.id.textCualHospital);
		textCualHospital.setVisibility(View.GONE);
		inputCualHospital = (EditText) rootView.findViewById(R.id.inputCualHospital);
		inputCualHospital.setVisibility(View.GONE);
		inputCualHospital.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
			}
			@Override
			public void afterTextChanged(Editable editable) {
				try{
					cualHospital = inputCualHospital.getText().toString();
				}
				catch(Exception e){
					inputCualHospital.setError(e.toString());
					inputCualHospital.requestFocus();
					return;
				}
			}

		});
		spinTrasladoHospital = (Spinner) rootView.findViewById(R.id.spinTrasladoHospital);
		spinTrasladoHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				trasladoHospital = mr.getCatKey();
				if(trasladoHospital.equals(Constants.YESKEYSND)){
					spinTrasladoHospital.setBackgroundColor(Color.RED);
					textCualHospital.setVisibility(View.VISIBLE);
					inputCualHospital.setVisibility(View.VISIBLE);
					spinHospitalizado.setVisibility(View.VISIBLE);
					textHospitalizado.setVisibility(View.VISIBLE);
				}
				else{
					spinTrasladoHospital.setBackgroundColor(Color.WHITE);
					textCualHospital.setVisibility(View.GONE);
					inputCualHospital.setVisibility(View.GONE);
					spinHospitalizado.setVisibility(View.GONE);
					textHospitalizado.setVisibility(View.GONE);
					cualHospital = null;
					hospitalizado = null;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinTrasladoHospital.setVisibility(View.VISIBLE);

		textHospitalizado = (TextView) rootView.findViewById(R.id.textHospitalizado);
		textHospitalizado.setVisibility(View.GONE);
		spinHospitalizado = (Spinner) rootView.findViewById(R.id.spinHospitalizado);
		spinHospitalizado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				hospitalizado = mr.getCatKey();
				if(hospitalizado.equals(Constants.YESKEYSND)){
					spinHospitalizado.setBackgroundColor(Color.RED);
					textFechaAlta.setVisibility(View.VISIBLE);
					inputFechaAlta.setVisibility(View.VISIBLE);
					fechaAlta_button.setVisibility(View.VISIBLE);
				}
				else{
					spinHospitalizado.setBackgroundColor(Color.WHITE);
					textFechaAlta.setVisibility(View.GONE);
					inputFechaAlta.setVisibility(View.GONE);
					fechaAlta_button.setVisibility(View.GONE);
					fechaAlta = null;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinHospitalizado.setVisibility(View.GONE);
		textFechaAlta = (TextView) rootView.findViewById(R.id.textFechaAlta);
		textFechaAlta.setVisibility(View.GONE);
		inputFechaAlta = (EditText) rootView.findViewById(R.id.inputFechaAlta);
		inputFechaAlta.setVisibility(View.GONE);
		fechaAlta_button = (ImageButton) rootView.findViewById(R.id.FechaAlta_button);
		fechaAlta_button.setVisibility(View.GONE);
		fechaAlta_button.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_ALTA);
			}
		});

		mSaveView = (Button) getActivity().findViewById(R.id.save_button);
		mSaveView.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				try {
					if(validarEntrada()){
					    new SaveDataTask().execute();
					}
				} catch (ParseException e) {
					Toast.makeText(getActivity(), e.toString() ,Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		});
		//intensidad de los sintomas
		textFiebreIntensidad = (TextView) rootView.findViewById(R.id.textFiebreIntensidad);
		textFiebreIntensidad.setVisibility(View.GONE);
		spinFiebreIntensidad = (Spinner) rootView.findViewById(R.id.spinFiebreIntensidad);
		spinFiebreIntensidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				fiebreIntensidad = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinFiebreIntensidad.setVisibility(View.GONE);

		textDolorCabezaIntensidad = (TextView) rootView.findViewById(R.id.textDolorCabezaIntensidad);
		textDolorCabezaIntensidad.setVisibility(View.GONE);
		spinDolorCabezaIntensidad = (Spinner) rootView.findViewById(R.id.spinDolorCabezaIntensidad);
		spinDolorCabezaIntensidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				dolorCabezaIntensidad = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinDolorCabezaIntensidad.setVisibility(View.GONE);

		textDolorArticularIntensidad = (TextView) rootView.findViewById(R.id.textDolorArticularIntensidad);
		textDolorArticularIntensidad.setVisibility(View.GONE);
		spinDolorArticularIntensidad = (Spinner) rootView.findViewById(R.id.spinDolorArticularIntensidad);
		spinDolorArticularIntensidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				dolorArticularIntensidad = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinDolorArticularIntensidad.setVisibility(View.GONE);

		textDolorMuscularIntensidad = (TextView) rootView.findViewById(R.id.textDolorMuscularIntensidad);
		textDolorMuscularIntensidad.setVisibility(View.GONE);
		spinDolorMuscularIntensidad = (Spinner) rootView.findViewById(R.id.spinDolorMuscularIntensidad);
		spinDolorMuscularIntensidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				dolorMuscularIntensidad = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinDolorMuscularIntensidad.setVisibility(View.GONE);

		textSecrecionNasalIntensidad = (TextView) rootView.findViewById(R.id.textSecrecionNasalIntensidad);
		textSecrecionNasalIntensidad.setVisibility(View.GONE);
		spinSecrecionNasalIntensidad = (Spinner) rootView.findViewById(R.id.spinSecrecionNasalIntensidad);
		spinSecrecionNasalIntensidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				secrecionNasalIntensidad = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinSecrecionNasalIntensidad.setVisibility(View.GONE);

		textTosIntensidad = (TextView) rootView.findViewById(R.id.textTosIntensidad);
		textTosIntensidad.setVisibility(View.GONE);
		spinTosIntensidad = (Spinner) rootView.findViewById(R.id.spinTosIntensidad);
		spinTosIntensidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				tosIntensidad = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinTosIntensidad.setVisibility(View.GONE);

		textDolorGargantaIntensidad = (TextView) rootView.findViewById(R.id.textDolorGargantaIntensidad);
		textDolorGargantaIntensidad.setVisibility(View.GONE);
		spinDolorGargantaIntensidad = (Spinner) rootView.findViewById(R.id.spinDolorGargantaIntensidad);
		spinDolorGargantaIntensidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				dolorGargantaIntensidad = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinDolorGargantaIntensidad.setVisibility(View.GONE);

		return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    
    private void createDialog(int dialog) {
    	final DatePickerDialog dpD;
    	DateMidnight minDate;
    	DateMidnight maxDate;
		switch(dialog) {
			case DATE_SINTOMA:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						fechaSintoma = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputFechaSintoma.setText(fechaSintoma);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaSeguimientoCaso.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_INITX:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						fechaInicioTx = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputFechaInicioTx.setText(fechaInicioTx);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaSeguimientoCaso.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_ALTA:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						fechaAlta = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputFechaAlta.setText(fechaAlta);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaSeguimientoCaso.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
		/*case DATE_FIF:
			dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
		        @Override
		        public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
		        	fif = (picker != null) ? String.valueOf(dayOfMonth<10? "0"+dayOfMonth:dayOfMonth)+"/"+String.valueOf((monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1))+"/"+String.valueOf(year) : null;
                	inputFif.setText(fif);
		        }
		    },c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            if (mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getFis()!=null){
                Date fechaInicio = mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
                Date fechaEnfermedad = mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getFis();
                //si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
                if (fechaInicio.compareTo(fechaEnfermedad)>=0)
                    minDate = new DateMidnight(fechaEnfermedad);
                else //sino tomar la fecha de inicio
                    minDate = new DateMidnight(fechaInicio);
            }else {
                minDate = new DateMidnight(mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
            }
            maxDate = new DateMidnight(mVisitaSeguimientoCaso.getFechaVisita());
			dpD.getDatePicker().setMinDate(minDate.getMillis());
			dpD.getDatePicker().setMaxDate(maxDate.getMillis());
			dpD.show();
			break;*/
			default:
				break;
		}
	}
    
    private boolean validarEntrada() throws ParseException {
        //Valida la entrada
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if (fechaSintoma == null || fechaSintoma.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fecha_sintoma)),Toast.LENGTH_LONG).show();
        	inputFechaSintoma.setError(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fecha_sintoma))); 
            return false;
        }
        else if (fiebre == null || fiebre.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fiebre)),Toast.LENGTH_LONG).show();
            return false;
        }
        /*else if (fiebre.equals(Constants.YESKEYSND) && (fif == null || fif.equals(""))){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fif)),Toast.LENGTH_LONG).show();
        	inputFif.setError(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fif))); 
            return false;
        }*/
		else if (fiebre.equals(Constants.YESKEYSND) && (fiebreIntensidad == null || fiebreIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fiebreIntensidad)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (fiebreCuantificada == null || fiebreCuantificada.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fiebreCuantificada)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (fiebreCuantificada.equals(Constants.YESKEYSND) && inputValorFiebreCuantificada.getText().toString().equals("")){
        	inputValorFiebreCuantificada.setError(getActivity().getString(R.string.wrongTemp,34,44));
        	inputValorFiebreCuantificada.requestFocus();
            return false;
        }
        else if (dolorCabeza == null || dolorCabeza.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorCabeza)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (dolorCabeza.equals(Constants.YESKEYSND) && (dolorCabezaIntensidad == null || dolorCabezaIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorCabezaIntensidad)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (dolorArticular == null || dolorArticular.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorArticular)),Toast.LENGTH_LONG).show();
            return false;
        }else if (dolorArticular.equals(Constants.YESKEYSND) && (dolorArticularIntensidad == null || dolorArticularIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorArticularIntensidad)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (dolorMuscular == null || dolorMuscular.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorMuscular)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (dolorMuscular.equals(Constants.YESKEYSND) && (dolorMuscularIntensidad == null || dolorMuscularIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorMuscularIntensidad)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (dificultadRespiratoria == null || dificultadRespiratoria.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dificultadRespiratoria)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (secrecionNasal == null || secrecionNasal.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.secrecionNasal)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (secrecionNasal.equals(Constants.YESKEYSND) && (secrecionNasalIntensidad == null || secrecionNasalIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.secrecionNasalIntensidad)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (tos == null || tos.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tos)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (tos.equals(Constants.YESKEYSND) && (tosIntensidad == null || tosIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tosIntensidad)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (pocoApetito == null || pocoApetito.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.pocoApetito)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (dolorGarganta == null || dolorGarganta.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorGarganta)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (dolorGarganta.equals(Constants.YESKEYSND) && (dolorGargantaIntensidad == null || dolorGargantaIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorGargantaIntensidad)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (picorGarganta == null || picorGarganta.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.picorGarganta)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (congestionNasal == null || congestionNasal.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.congestionNasal)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (rash == null || rash.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.rash)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (urticaria == null || urticaria.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.urticaria)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (conjuntivitis == null || conjuntivitis.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.conjuntivitis)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (expectoracion == null || expectoracion.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.expectoracion)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (diarrea == null || diarrea.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.diarrea)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (vomito == null || vomito.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.vomito)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (fatiga == null || fatiga.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fatiga)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (respiracionRuidosa == null || respiracionRuidosa.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.respiracionRuidosa)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (respiracionRapida == null || respiracionRapida.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.respiracionRapida)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (perdidaOlfato == null || perdidaOlfato.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.perdidaOlfato)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (perdidaGusto == null || perdidaGusto.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.perdidaGusto)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (desmayos == null || desmayos.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.desmayos)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (sensacionPechoApretado == null || sensacionPechoApretado.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.sensacionPechoApretado)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dolorPecho == null || dolorPecho.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorPecho)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (sensacionFaltaAire == null || sensacionFaltaAire.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.sensacionFaltaAire)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (quedoCama == null || quedoCama.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.quedoCama)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (tratamiento == null || tratamiento.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tratamientoCovid19)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (tratamiento.equals(Constants.YESKEYSND) && (fechaInicioTx == null || fechaInicioTx.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fechaInicioTx)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (tratamiento.equals(Constants.YESKEYSND) && (cualMedicamento == null || cualMedicamento.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.cualMedicamento)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (cualMedicamento!=null && cualMedicamento.toLowerCase().contains("otro") && (otroMedicamento == null || otroMedicamento.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.otroMedicamento)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (antibiotico == null || antibiotico.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.antibiotico)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (antibiotico.equals(Constants.YESKEYSND) && (prescritoMedico == null || prescritoMedico.equals(""))){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.prescritoMedico)),Toast.LENGTH_LONG).show();
            return false;
        }
		/*else if (factorRiesgo == null || factorRiesgo.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.factorRiesgo)),Toast.LENGTH_LONG).show();
			return false;
		}*/
		else if ((factorRiesgo!=null && factorRiesgo.toLowerCase().contains("otro")) && (otroFactorRiesgo == null || otroFactorRiesgo.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.otroFactorRiesgo)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (otraPersonaEnferma == null || otraPersonaEnferma.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.otraPersonaEnferma)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (otraPersonaEnferma.equals(Constants.YESKEYSND) && (cuantasPersonas == null || cuantasPersonas < 0)){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.cuantasPersonas)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (trasladoHospital == null || trasladoHospital.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.trasladoHospital)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (trasladoHospital.equals(Constants.YESKEYSND) && (cualHospital == null || cualHospital.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.cualHospital)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (trasladoHospital.equals(Constants.YESKEYSND) && (hospitalizado == null || hospitalizado.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.hospitalizado)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if ((hospitalizado!= null && hospitalizado.equals(Constants.YESKEYSND)) && (fechaAlta == null || fechaAlta.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fechaAlta)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getFis()==null && formatter.parse(fechaSintoma).before(mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso())){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.fecBefRegistro,getActivity().getString(R.string.fecha_sintoma),getActivity().getString(R.string.fechaIngreso)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (tratamiento.equals(Constants.YESKEYSND) && (formatter.parse(fechaInicioTx).after(formatter.parse(fechaSintoma)))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.fecAftRegistro,getActivity().getString(R.string.fechaInicioTx),getActivity().getString(R.string.fecha_sintoma)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if ((hospitalizado!=null && hospitalizado.equals(Constants.YESKEYSND)) && (formatter.parse(fechaAlta).after(formatter.parse(fechaSintoma)))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.fecAftRegistro,getActivity().getString(R.string.fechaAlta),getActivity().getString(R.string.fecha_sintoma)),Toast.LENGTH_LONG).show();
			return false;
		}
        /*else if (fiebre.equals(Constants.YESKEYSND) && (formatter.parse(fif).after(formatter.parse(fechaSintoma)))){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.fecAftRegistro,getActivity().getString(R.string.fif),getActivity().getString(R.string.fecha_sintoma)),Toast.LENGTH_LONG).show();
            return false;
        }*/
        else{
        	mSintoma.setCodigoCasoSintoma(infoMovil.getId());
        	mSintoma.setCodigoCasoVisita(mVisitaSeguimientoCaso);
        	mSintoma.setFechaSintomas(formatter.parse(fechaSintoma));
        	mSintoma.setFiebre(fiebre);
			mSintoma.setFiebreIntensidad(fiebreIntensidad);
        	//if (fiebre.equals(Constants.YESKEYSND)) mSintoma.setFif(formatter.parse(fif));
			mSintoma.setFiebreCuantificada(fiebreCuantificada);
			mSintoma.setValorFiebreCuantificada(valorFiebreCuantificada);
			mSintoma.setDolorCabeza(dolorCabeza);
			mSintoma.setDolorCabezaIntensidad(dolorCabezaIntensidad);
			mSintoma.setDolorArticular(dolorArticular);
			mSintoma.setDolorArticularIntensidad(dolorArticularIntensidad);
			mSintoma.setDolorMuscular(dolorMuscular);
			mSintoma.setDolorMuscularIntensidad(dolorMuscularIntensidad);
			mSintoma.setDificultadRespiratoria(dificultadRespiratoria);
			mSintoma.setSecrecionNasal(secrecionNasal);
			mSintoma.setSecrecionNasalIntensidad(secrecionNasalIntensidad);
			mSintoma.setTos(tos);
			mSintoma.setTosIntensidad(tosIntensidad);
			mSintoma.setPocoApetito(pocoApetito);
			mSintoma.setDolorGarganta(dolorGarganta);
			mSintoma.setDolorGargantaIntensidad(dolorGargantaIntensidad);
			mSintoma.setPicorGarganta(picorGarganta);
			mSintoma.setCongestionNasal(congestionNasal);
			mSintoma.setRash(rash);
			mSintoma.setUrticaria(urticaria);
			mSintoma.setConjuntivitis(conjuntivitis);
			mSintoma.setExpectoracion(expectoracion);
			mSintoma.setDiarrea(diarrea);
			mSintoma.setVomito(vomito);
			mSintoma.setFatiga(fatiga);
			mSintoma.setRespiracionRuidosa(respiracionRuidosa);
			mSintoma.setRespiracionRapida(respiracionRapida);
			mSintoma.setPerdidaOlfato(perdidaOlfato);
			mSintoma.setPerdidaGusto(perdidaGusto);
			mSintoma.setDesmayos(desmayos);
			mSintoma.setSensacionPechoApretado(sensacionPechoApretado);
			mSintoma.setDolorPecho(dolorPecho);
			mSintoma.setSensacionFaltaAire(sensacionFaltaAire);
			mSintoma.setQuedoCama(quedoCama);
			mSintoma.setTratamiento(tratamiento);
			if (fechaInicioTx!=null && !fechaInicioTx.isEmpty()) mSintoma.setFechaInicioTx(formatter.parse(fechaInicioTx));
			estudiosAdapter.open();
			if (cualMedicamento!=null && !cualMedicamento.isEmpty()) {
				String keysCriterios = "";
				cualMedicamento = cualMedicamento.replaceAll(",", "','");
				List<MessageResource> catMedi = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + cualMedicamento + "') and "
						+ CatalogosDBConstants.catRoot + "='COVID_MEDICAMENTO_SVC'", null);
				for (MessageResource ms : catMedi) {
					keysCriterios += ms.getCatKey() + ",";
				}
				if (!keysCriterios.isEmpty())
					keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
				mSintoma.setCualMedicamento(keysCriterios);
			}
			//mSintoma.setCualMedicamento(cualMedicamento);
			mSintoma.setOtroMedicamento(otroMedicamento);
			mSintoma.setAntibiotico(antibiotico);
			mSintoma.setPrescritoMedico(prescritoMedico);
			if (factorRiesgo!=null && !factorRiesgo.isEmpty()) {
				String keysCriterios = "";
				factorRiesgo = factorRiesgo.replaceAll(",", "','");
				List<MessageResource> catFR = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + factorRiesgo + "') and "
						+ CatalogosDBConstants.catRoot + "='COVID_FACTOR_RI_SVC'", null);
				for (MessageResource ms : catFR) {
					keysCriterios += ms.getCatKey() + ",";
				}
				if (!keysCriterios.isEmpty())
					keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
				mSintoma.setFactorRiesgo(keysCriterios);
			}
			estudiosAdapter.close();
			//mSintoma.setFactorRiesgo(factorRiesgo);
			mSintoma.setOtroFactorRiesgo(otroFactorRiesgo);
			mSintoma.setOtraPersonaEnferma(otraPersonaEnferma);
			mSintoma.setCuantasPersonas(cuantasPersonas);
			mSintoma.setTrasladoHospital(trasladoHospital);
			mSintoma.setCualHospital(cualHospital);
			mSintoma.setHospitalizado(hospitalizado);
			if (fechaAlta!=null && !fechaAlta.isEmpty()) mSintoma.setFechaAlta(formatter.parse(fechaAlta));

			mSintoma.setRecordDate(new Date());
			mSintoma.setRecordUser(username);
			mSintoma.setDeviceid(infoMovil.getDeviceId());
			mSintoma.setEstado('0');
			mSintoma.setPasive('0');
            return true;
        }
    }
    
    private class FetchCatalogosTask extends AsyncTask<String, Void, String> {
    	private ProgressDialog nDialog;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			super.onPreExecute();
		    nDialog = new ProgressDialog(getActivity()); 
		    nDialog.setMessage(getActivity().getString(R.string.pleasewait));
		    nDialog.setTitle(getActivity().getString(R.string.loading));
		    nDialog.setIndeterminate(false);
		    nDialog.setCancelable(true);
		    nDialog.show();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				mCatalogoSnd = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_SND'", CatalogosDBConstants.order);
				mCatalogoSiNo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", CatalogosDBConstants.order);
				mCatalogoIntensidad = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CAT_INTENSIDAD_SIN'", CatalogosDBConstants.order);
				mMedicamentos = Arrays.asList(estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='COVID_MEDICAMENTO_SVC'", CatalogosDBConstants.order));
				mFactoresRiesgo = Arrays.asList(estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='COVID_FACTOR_RI_SVC'", CatalogosDBConstants.order));
				mVisitasSeguimientoCasoCovid19 = estudiosAdapter.getVisitasSeguimientosCasosCovid19(Covid19DBConstants.codigoCasoParticipante +" = '" + mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCasoParticipante() +"'", Covid19DBConstants.fechaVisita);
				String filtro = "";
				for (VisitaSeguimientoCasoCovid19 vsc: mVisitasSeguimientoCasoCovid19){
					if (filtro.equals("")){ filtro = Covid19DBConstants.codigoCasoVisita + "='" + vsc.getCodigoCasoVisita()+"'";} else{
					if (!filtro.equals("")) filtro = filtro + " or " + Covid19DBConstants.codigoCasoVisita + "='" + vsc.getCodigoCasoVisita()+"'";}
				}
				mSintomasVisitaCasoCovid19 = estudiosAdapter.getSintomasVisitasCasosCovid19(filtro, CasosDBConstants.fechaSintomas);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			nDialog.dismiss();
			mTitleView.setText(getActivity().getString(R.string.new_sint));
			mCatalogoSnd.add(new MessageResource("",0,getActivity().getString(R.string.select)));
			Collections.sort(mCatalogoSnd);
			ArrayAdapter<MessageResource> dataAdapter = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSnd);
			dataAdapter.setDropDownViewResource(R.layout.spinner_item);

			mCatalogoSiNo.add(new MessageResource("",0,getActivity().getString(R.string.select)));
			Collections.sort(mCatalogoSiNo);
			ArrayAdapter<MessageResource> dataAdapter2 = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSiNo);
			dataAdapter2.setDropDownViewResource(R.layout.spinner_item);

			spinFiebre.setAdapter(dataAdapter);
			spinFiebreCuantificada.setAdapter(dataAdapter);
			spinDolorCabeza.setAdapter(dataAdapter);
			spinDolorArticular.setAdapter(dataAdapter);
			spinDolorMuscular.setAdapter(dataAdapter);
			spinDificultadRespiratoria.setAdapter(dataAdapter);
			
			spinSecrecionNasal.setAdapter(dataAdapter);
			spinTos.setAdapter(dataAdapter);
			spinPocoApetito.setAdapter(dataAdapter);
			spinDolorGarganta.setAdapter(dataAdapter);
			spinPicor.setAdapter(dataAdapter);
			spinCongestionNasal.setAdapter(dataAdapter);
			spinCongestionNasal.setAdapter(dataAdapter);
			spinRash.setAdapter(dataAdapter);
			spinUrticaria.setAdapter(dataAdapter);
			spinConjuntivitis.setAdapter(dataAdapter);
			spinExpectoracion.setAdapter(dataAdapter);
			spinDiarrea.setAdapter(dataAdapter);
			spinVomito.setAdapter(dataAdapter);
			spinFatiga.setAdapter(dataAdapter);
			spinRespiracionRuidosa.setAdapter(dataAdapter);
			spinRespiracionRapida.setAdapter(dataAdapter);
			spinPerdidaOlfato.setAdapter(dataAdapter);
			spinPerdidaGusto.setAdapter(dataAdapter);
			spinDesmayos.setAdapter(dataAdapter);
			spinSensacionPA.setAdapter(dataAdapter);
			spinDolorPecho.setAdapter(dataAdapter);
			spinSensacionFA.setAdapter(dataAdapter);
			spinQuedoCama.setAdapter(dataAdapter);
			spinTratamiento.setAdapter(dataAdapter);
			//spinCualMedicamento.setAdapter(dataAdapter);
			spinAntibiotico.setAdapter(dataAdapter);
			spinPrescritoMedico.setAdapter(dataAdapter);
			//spinFactorRiesgo.setAdapter(dataAdapter);
			spinOtraPersonaEnferma.setAdapter(dataAdapter2);
			spinTrasladoHospital.setAdapter(dataAdapter2);
			spinHospitalizado.setAdapter(dataAdapter2);

			spinCualMedicamento.setItems(mMedicamentos, getString(R.string.select), new MultiSpinner.MultiSpinnerListener() {
				@Override
				public void onItemsSelected(boolean[] selected) {
					int indice = 0;
					cualMedicamento = null;
					for(boolean item : selected){
						if (item) {
							if (cualMedicamento==null) cualMedicamento = mMedicamentos.get(indice);
							else  cualMedicamento += "," + mMedicamentos.get(indice);
						}
						indice++;
					}
					if (cualMedicamento!=null && cualMedicamento.toLowerCase().contains("otro")){
						textOtroMedicamento.setVisibility(View.VISIBLE);
						inputOtroMedicamento.setVisibility(View.VISIBLE);
					}else{
						textOtroMedicamento.setVisibility(View.GONE);
						inputOtroMedicamento.setVisibility(View.GONE);
					}
				}
			});
			spinFactorRiesgo.setItems(mFactoresRiesgo, getString(R.string.select), new MultiSpinner.MultiSpinnerListener() {
				@Override
				public void onItemsSelected(boolean[] selected) {
					int indice = 0;
					factorRiesgo = null;
					for(boolean item : selected){
						if (item) {
							if (factorRiesgo==null) factorRiesgo = mFactoresRiesgo.get(indice);
							else  factorRiesgo += "," + mFactoresRiesgo.get(indice);
						}
						indice++;
					}
					if (factorRiesgo!=null && factorRiesgo.toLowerCase().contains("otro")){
						textOtroFactorRiesgo.setVisibility(View.VISIBLE);
						inputOtroFactorRiesgo.setVisibility(View.VISIBLE);
					}else{
						textOtroFactorRiesgo.setVisibility(View.GONE);
						inputOtroFactorRiesgo.setVisibility(View.GONE);
					}
				}
			});


			mCatalogoIntensidad.add(new MessageResource("",0,getActivity().getString(R.string.select)));
			Collections.sort(mCatalogoIntensidad);
			ArrayAdapter<MessageResource> dataAdapterInt = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoIntensidad);
			dataAdapterInt.setDropDownViewResource(R.layout.spinner_item);
			spinFiebreIntensidad.setAdapter(dataAdapterInt);
			spinDolorCabezaIntensidad.setAdapter(dataAdapterInt);
			spinDolorArticularIntensidad.setAdapter(dataAdapterInt);
			spinDolorMuscularIntensidad.setAdapter(dataAdapterInt);
			spinSecrecionNasalIntensidad.setAdapter(dataAdapterInt);
			spinTosIntensidad.setAdapter(dataAdapterInt);
			spinDolorGargantaIntensidad.setAdapter(dataAdapterInt);

		}

	}

    private class SaveDataTask extends AsyncTask<String, Void, String> {
    	private ProgressDialog nDialog;
    	private AlertDialog alertDialog;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			super.onPreExecute();
		    nDialog = new ProgressDialog(getActivity()); 
		    nDialog.setMessage(getActivity().getString(R.string.pleasewait));
		    nDialog.setTitle(getActivity().getString(R.string.loading));
		    nDialog.setIndeterminate(false);
		    nDialog.setCancelable(false);
		    nDialog.show();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				for (SintomasVisitaCasoCovid19 vscc: mSintomasVisitaCasoCovid19){
					if(vscc.getFechaSintomas().equals(mSintoma.getFechaSintomas())){
						return getActivity().getString(R.string.duplicateDate) + " - " + fechaSintoma + " / " + getActivity().getString(R.string.visit) + " - " + vscc.getCodigoCasoVisita().getVisita();
					}
				}
				estudiosAdapter.crearSintomasVisitaCasoCovid19(mSintoma);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			nDialog.dismiss();
			if(!resultado.equals("exito")){
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(getActivity().getString(R.string.error));
				builder.setMessage(resultado);
				builder.setPositiveButton(getActivity().getString(R.string.ok), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Finish app
						dialog.dismiss();
					}
				});
				alertDialog = builder.create();
				alertDialog.show();
			}
			else{
				Bundle arguments = new Bundle();
				Intent i;
				arguments.putSerializable(Constants.VISITA , mVisitaSeguimientoCaso);
				i = new Intent(getActivity(),
						ListaSintomasVisitaCasoCovid19Activity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        i.putExtras(arguments);
				startActivity(i);
				getActivity().finish();
			}
		}

	}	
    
}
