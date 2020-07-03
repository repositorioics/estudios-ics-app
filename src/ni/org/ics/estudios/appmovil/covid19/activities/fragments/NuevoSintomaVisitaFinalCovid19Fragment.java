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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.covid19.activities.MenuVisitaFinalCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.covid19.SintomasVisitaFinalCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaFinalCasoCovid19;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import org.joda.time.DateMidnight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NuevoSintomaVisitaFinalCovid19Fragment extends Fragment {

	private static final String TAG = "NuevoSintomaVisitaFinalCovid19Fragment";
	protected static final int DATE_I1 = 101;
	protected static final int DATE_I2 = 102;
	protected static final int DATE_I3 = 103;
	protected static final int DATE_I4 = 104;
	protected static final int DATE_I5 = 105;
	protected static final int DATE_I6 = 106;
	protected static final int DATE_I7 = 107;
	protected static final int DATE_I8 = 108;
	protected static final int DATE_I9 = 109;
	protected static final int DATE_I10 = 110;
	protected static final int DATE_I11 = 111;
	protected static final int DATE_I12 = 112;
	protected static final int DATE_I13 = 113;
	protected static final int DATE_I14 = 114;
	protected static final int DATE_I15 = 115;
	protected static final int DATE_I16 = 116;
	protected static final int DATE_I17 = 117;
	protected static final int DATE_I18 = 118;
	protected static final int DATE_I19 = 119;
	protected static final int DATE_I20 = 120;
	protected static final int DATE_I21 = 121;
	protected static final int DATE_I22 = 122;
	protected static final int DATE_I23 = 123;
	protected static final int DATE_I24 = 124;
	protected static final int DATE_I25 = 125;
	protected static final int DATE_I26 = 126;
	protected static final int DATE_I27 = 127;
	protected static final int DATE_F1 = 201;
	protected static final int DATE_F2 = 202;
	protected static final int DATE_F3 = 203;
	protected static final int DATE_F4 = 204;
	protected static final int DATE_F5 = 205;
	protected static final int DATE_F6 = 206;
	protected static final int DATE_F7 = 207;
	protected static final int DATE_F8 = 208;
	protected static final int DATE_F9 = 209;
	protected static final int DATE_F10 = 210;
	protected static final int DATE_F11 = 211;
	protected static final int DATE_F12 = 212;
	protected static final int DATE_F13 = 213;
	protected static final int DATE_F14 = 214;
	protected static final int DATE_F15 = 215;
	protected static final int DATE_F16 = 216;
	protected static final int DATE_F17 = 217;
	protected static final int DATE_F18 = 218;
	protected static final int DATE_F19 = 219;
	protected static final int DATE_F20 = 220;
	protected static final int DATE_F21 = 221;
	protected static final int DATE_F22 = 222;
	protected static final int DATE_F23 = 223;
	protected static final int DATE_F24 = 224;
	protected static final int DATE_F25 = 225;
	protected static final int DATE_F26 = 226;
	protected static final int DATE_F27 = 227;


	private EstudiosAdapter estudiosAdapter;
	//Viene de la llamada
	private VisitaFinalCasoCovid19 mVisitaFinal;
	//Objeto que se va a hacer
	private SintomasVisitaFinalCovid19 mSintoma = new SintomasVisitaFinalCovid19();
	//Catalogos
	private List<MessageResource> mCatalogoSnd;
	//Widgets en el View
	private TextView mTitleView;
	private EditText mNameView;
	//private EditText inputFechaSintoma;
	//private ImageButton mButtonChangeFechaSintoma;
	private Spinner spinFiebre;
	private Spinner spinTos;
	private Spinner spinDolorCabeza;
	private Spinner spinDolorArticular;
	private Spinner spinDolorMuscular;
	private Spinner spinDificultadRespiratoria;
	private Spinner spinPocoApetito;
	private Spinner spinDolorGarganta;
	private Spinner spinSecrecionNasal;
	private Spinner spinPicorGarganta;
	private Spinner spinExpectoracion;
	private Spinner spinRash;
	private Spinner spinUrticaria;
	private Spinner spinConjuntivitis;
	private Spinner spinDiarrea;
	private Spinner spinVomito;
	private Spinner spinQuedoCama;
	private Spinner spinRespiracionRuidosa;
	private Spinner spinRespiracionRapida;
	private Spinner spinPerdidaOlfato;
	private Spinner spinCongestionNasal;
	private Spinner spinPerdidaGusto;
	private Spinner spinDesmayos;
	private Spinner spinSensacionPechoApretado;
	private Spinner spinDolorPecho;
	private Spinner spinSensacionFaltaAire;
	private Spinner spinFatiga;
	private TextView textFiebreFecIni;
	private TextView textTosFecIni;
	private TextView textDolorCabezaFecIni;
	private TextView textDolorArticularFecIni;
	private TextView textDolorMuscularFecIni;
	private TextView textDificultadRespiratoriaFecIni;
	private TextView textPocoApetitoFecIni;
	private TextView textDolorGargantaFecIni;
	private TextView textSecrecionNasalFecIni;
	private TextView textPicorGargantaFecIni;
	private TextView textExpectoracionFecIni;
	private TextView textRashFecIni;
	private TextView textUrticariaFecIni;
	private TextView textConjuntivitisFecIni;
	private TextView textDiarreaFecIni;
	private TextView textVomitoFecIni;
	private TextView textQuedoCamaFecIni;
	private TextView textRespiracionRuidosaFecIni;
	private TextView textRespiracionRapidaFecIni;
	private TextView textPerdidaOlfatoFecIni;
	private TextView textCongestionNasalFecIni;
	private TextView textPerdidaGustoFecIni;
	private TextView textDesmayosFecIni;
	private TextView textSensacionPechoApretadoFecIni;
	private TextView textDolorPechoFecIni;
	private TextView textSensacionFaltaAireFecIni;
	private TextView textFatigaFecIni;
	private EditText inputFiebreFecIni;
	private EditText inputTosFecIni;
	private EditText inputDolorCabezaFecIni;
	private EditText inputDolorArticularFecIni;
	private EditText inputDolorMuscularFecIni;
	private EditText inputDificultadRespiratoriaFecIni;
	private EditText inputPocoApetitoFecIni;
	private EditText inputDolorGargantaFecIni;
	private EditText inputSecrecionNasalFecIni;
	private EditText inputPicorGargantaFecIni;
	private EditText inputExpectoracionFecIni;
	private EditText inputRashFecIni;
	private EditText inputUrticariaFecIni;
	private EditText inputConjuntivitisFecIni;
	private EditText inputDiarreaFecIni;
	private EditText inputVomitoFecIni;
	private EditText inputQuedoCamaFecIni;
	private EditText inputRespiracionRuidosaFecIni;
	private EditText inputRespiracionRapidaFecIni;
	private EditText inputPerdidaOlfatoFecIni;
	private EditText inputCongestionNasalFecIni;
	private EditText inputPerdidaGustoFecIni;
	private EditText inputDesmayosFecIni;
	private EditText inputSensacionPechoApretadoFecIni;
	private EditText inputDolorPechoFecIni;
	private EditText inputSensacionFaltaAireFecIni;
	private EditText inputFatigaFecIni;
	private TextView textFiebreFecFin;
	private TextView textTosFecFin;
	private TextView textDolorCabezaFecFin;
	private TextView textDolorArticularFecFin;
	private TextView textDolorMuscularFecFin;
	private TextView textDificultadRespiratoriaFecFin;
	private TextView textPocoApetitoFecFin;
	private TextView textDolorGargantaFecFin;
	private TextView textSecrecionNasalFecFin;
	private TextView textPicorGargantaFecFin;
	private TextView textExpectoracionFecFin;
	private TextView textRashFecFin;
	private TextView textUrticariaFecFin;
	private TextView textConjuntivitisFecFin;
	private TextView textDiarreaFecFin;
	private TextView textVomitoFecFin;
	private TextView textQuedoCamaFecFin;
	private TextView textRespiracionRuidosaFecFin;
	private TextView textRespiracionRapidaFecFin;
	private TextView textPerdidaOlfatoFecFin;
	private TextView textCongestionNasalFecFin;
	private TextView textPerdidaGustoFecFin;
	private TextView textDesmayosFecFin;
	private TextView textSensacionPechoApretadoFecFin;
	private TextView textDolorPechoFecFin;
	private TextView textSensacionFaltaAireFecFin;
	private TextView textFatigaFecFin;
	private EditText inputFiebreFecFin;
	private EditText inputTosFecFin;
	private EditText inputDolorCabezaFecFin;
	private EditText inputDolorArticularFecFin;
	private EditText inputDolorMuscularFecFin;
	private EditText inputDificultadRespiratoriaFecFin;
	private EditText inputPocoApetitoFecFin;
	private EditText inputDolorGargantaFecFin;
	private EditText inputSecrecionNasalFecFin;
	private EditText inputPicorGargantaFecFin;
	private EditText inputExpectoracionFecFin;
	private EditText inputRashFecFin;
	private EditText inputUrticariaFecFin;
	private EditText inputConjuntivitisFecFin;
	private EditText inputDiarreaFecFin;
	private EditText inputVomitoFecFin;
	private EditText inputQuedoCamaFecFin;
	private EditText inputRespiracionRuidosaFecFin;
	private EditText inputRespiracionRapidaFecFin;
	private EditText inputPerdidaOlfatoFecFin;
	private EditText inputCongestionNasalFecFin;
	private EditText inputPerdidaGustoFecFin;
	private EditText inputDesmayosFecFin;
	private EditText inputSensacionPechoApretadoFecFin;
	private EditText inputDolorPechoFecFin;
	private EditText inputSensacionFaltaAireFecFin;
	private EditText inputFatigaFecFin;
	private ImageButton buttonFiebreFecIni;
	private ImageButton buttonTosFecIni;
	private ImageButton buttonDolorCabezaFecIni;
	private ImageButton buttonDolorArticularFecIni;
	private ImageButton buttonDolorMuscularFecIni;
	private ImageButton buttonDificultadRespiratoriaFecIni;
	private ImageButton buttonPocoApetitoFecIni;
	private ImageButton buttonDolorGargantaFecIni;
	private ImageButton buttonSecrecionNasalFecIni;
	private ImageButton buttonPicorGargantaFecIni;
	private ImageButton buttonExpectoracionFecIni;
	private ImageButton buttonRashFecIni;
	private ImageButton buttonUrticariaFecIni;
	private ImageButton buttonConjuntivitisFecIni;
	private ImageButton buttonDiarreaFecIni;
	private ImageButton buttonVomitoFecIni;
	private ImageButton buttonQuedoCamaFecIni;
	private ImageButton buttonRespiracionRuidosaFecIni;
	private ImageButton buttonRespiracionRapidaFecIni;
	private ImageButton buttonPerdidaOlfatoFecIni;
	private ImageButton buttonCongestionNasalFecIni;
	private ImageButton buttonPerdidaGustoFecIni;
	private ImageButton buttonDesmayosFecIni;
	private ImageButton buttonSensacionPechoApretadoFecIni;
	private ImageButton buttonDolorPechoFecIni;
	private ImageButton buttonSensacionFaltaAireFecIni;
	private ImageButton buttonFatigaFecIni;
	private ImageButton buttonFiebreFecFin;
	private ImageButton buttonTosFecFin;
	private ImageButton buttonDolorCabezaFecFin;
	private ImageButton buttonDolorArticularFecFin;
	private ImageButton buttonDolorMuscularFecFin;
	private ImageButton buttonDificultadRespiratoriaFecFin;
	private ImageButton buttonPocoApetitoFecFin;
	private ImageButton buttonDolorGargantaFecFin;
	private ImageButton buttonSecrecionNasalFecFin;
	private ImageButton buttonPicorGargantaFecFin;
	private ImageButton buttonExpectoracionFecFin;
	private ImageButton buttonRashFecFin;
	private ImageButton buttonUrticariaFecFin;
	private ImageButton buttonConjuntivitisFecFin;
	private ImageButton buttonDiarreaFecFin;
	private ImageButton buttonVomitoFecFin;
	private ImageButton buttonQuedoCamaFecFin;
	private ImageButton buttonRespiracionRuidosaFecFin;
	private ImageButton buttonRespiracionRapidaFecFin;
	private ImageButton buttonPerdidaOlfatoFecFin;
	private ImageButton buttonCongestionNasalFecFin;
	private ImageButton buttonPerdidaGustoFecFin;
	private ImageButton buttonDesmayosFecFin;
	private ImageButton buttonSensacionPechoApretadoFecFin;
	private ImageButton buttonDolorPechoFecFin;
	private ImageButton buttonSensacionFaltaAireFecFin;
	private ImageButton buttonFatigaFecFin;


	private Button mSaveView;

	//Variables donde se captura la entrada de datos
	private String fiebre;
	private String tos;
	private String dolorCabeza;
	private String dolorArticular;
	private String dolorMuscular;
	private String dificultadRespiratoria;
	private String pocoApetito;
	private String dolorGarganta;
	private String secrecionNasal;
	private String picorGarganta;
	private String expectoracion;
	private String rash;
	private String urticaria;
	private String conjuntivitis;
	private String diarrea;
	private String vomito;
	private String quedoCama;
	private String respiracionRuidosa;
	private String respiracionRapida;
	private String perdidaOlfato;
	private String congestionNasal;
	private String perdidaGusto;
	private String desmayos;
	private String sensacionPechoApretado;
	private String dolorPecho;
	private String sensacionFaltaAire;
	private String fatiga;
	private String fiebreFecIni;
	private String tosFecIni;
	private String dolorCabezaFecIni;
	private String dolorArticularFecIni;
	private String dolorMuscularFecIni;
	private String dificultadRespiratoriaFecIni;
	private String pocoApetitoFecIni;
	private String dolorGargantaFecIni;
	private String secrecionNasalFecIni;
	private String picorGargantaFecIni;
	private String expectoracionFecIni;
	private String rashFecIni;
	private String urticariaFecIni;
	private String conjuntivitisFecIni;
	private String diarreaFecIni;
	private String vomitoFecIni;
	private String quedoCamaFecIni;
	private String respiracionRuidosaFecIni;
	private String respiracionRapidaFecIni;
	private String perdidaOlfatoFecIni;
	private String congestionNasalFecIni;
	private String perdidaGustoFecIni;
	private String desmayosFecIni;
	private String sensacionPechoApretadoFecIni;
	private String dolorPechoFecIni;
	private String sensacionFaltaAireFecIni;
	private String fatigaFecIni;
	private String fiebreFecFin;
	private String tosFecFin;
	private String dolorCabezaFecFin;
	private String dolorArticularFecFin;
	private String dolorMuscularFecFin;
	private String dificultadRespiratoriaFecFin;
	private String pocoApetitoFecFin;
	private String dolorGargantaFecFin;
	private String secrecionNasalFecFin;
	private String picorGargantaFecFin;
	private String expectoracionFecFin;
	private String rashFecFin;
	private String urticariaFecFin;
	private String conjuntivitisFecFin;
	private String diarreaFecFin;
	private String vomitoFecFin;
	private String quedoCamaFecFin;
	private String respiracionRuidosaFecFin;
	private String respiracionRapidaFecFin;
	private String perdidaOlfatoFecFin;
	private String congestionNasalFecFin;
	private String perdidaGustoFecFin;
	private String desmayosFecFin;
	private String sensacionPechoApretadoFecFin;
	private String dolorPechoFecFin;
	private String sensacionFaltaAireFecFin;
	private String fatigaFecFin;

	final Calendar c = Calendar.getInstance();
	//Para el id
	private DeviceInfo infoMovil;
	private String username;
	private SharedPreferences settings;


    public static NuevoSintomaVisitaFinalCovid19Fragment create() {
        NuevoSintomaVisitaFinalCovid19Fragment fragment = new NuevoSintomaVisitaFinalCovid19Fragment();
        return fragment;
    }

    public NuevoSintomaVisitaFinalCovid19Fragment() {
    	
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
		mVisitaFinal = (VisitaFinalCasoCovid19) getActivity().getIntent().getExtras().getSerializable(Constants.VISITA_FINAL);
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
        View rootView = inflater.inflate(R.layout.fragment_nuevo_sintoma_vf_covid19, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((EditText) rootView.findViewById(R.id.inputCodigoParticipanteCaso));
        mNameView.setText(mVisitaFinal.getCodigoParticipanteCaso().getParticipante().getNombreCompleto());
        mNameView.setEnabled(false);
        /*
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
        */

		textFiebreFecIni = (TextView)rootView.findViewById(R.id.textFiebreFecIni);
		textFiebreFecIni.setVisibility(View.GONE);
		inputFiebreFecIni = (EditText) rootView.findViewById(R.id.inputFiebreFecIni);
		inputFiebreFecIni.setVisibility(View.GONE);
		buttonFiebreFecIni = (ImageButton) rootView.findViewById(R.id.buttonFiebreFecIni);
		buttonFiebreFecIni.setVisibility(View.GONE);

		textTosFecIni = (TextView)rootView.findViewById(R.id.textTosFecIni);
		textTosFecIni.setVisibility(View.GONE);
		inputTosFecIni = (EditText) rootView.findViewById(R.id.inputTosFecIni);
		inputTosFecIni.setVisibility(View.GONE);
		buttonTosFecIni = (ImageButton) rootView.findViewById(R.id.buttonTosFecIni);
		buttonTosFecIni.setVisibility(View.GONE);

		textDolorCabezaFecIni = (TextView)rootView.findViewById(R.id.textDolorCabezaFecIni);
		textDolorCabezaFecIni.setVisibility(View.GONE);
		inputDolorCabezaFecIni = (EditText) rootView.findViewById(R.id.inputDolorCabezaFecIni);
		inputDolorCabezaFecIni.setVisibility(View.GONE);
		buttonDolorCabezaFecIni = (ImageButton) rootView.findViewById(R.id.buttonDolorCabezaFecIni);
		buttonDolorCabezaFecIni.setVisibility(View.GONE);

		textDolorArticularFecIni = (TextView)rootView.findViewById(R.id.textDolorArticularFecIni);
		textDolorArticularFecIni.setVisibility(View.GONE);
		inputDolorArticularFecIni = (EditText) rootView.findViewById(R.id.inputDolorArticularFecIni);
		inputDolorArticularFecIni.setVisibility(View.GONE);
		buttonDolorArticularFecIni = (ImageButton) rootView.findViewById(R.id.buttonDolorArticularFecIni);
		buttonDolorArticularFecIni.setVisibility(View.GONE);

		textDolorMuscularFecIni = (TextView)rootView.findViewById(R.id.textDolorMuscularFecIni);
		textDolorMuscularFecIni.setVisibility(View.GONE);
		inputDolorMuscularFecIni = (EditText) rootView.findViewById(R.id.inputDolorMuscularFecIni);
		inputDolorMuscularFecIni.setVisibility(View.GONE);
		buttonDolorMuscularFecIni = (ImageButton) rootView.findViewById(R.id.buttonDolorMuscularFecIni);
		buttonDolorMuscularFecIni.setVisibility(View.GONE);

		textDificultadRespiratoriaFecIni = (TextView)rootView.findViewById(R.id.textDificultadRespiratoriaFecIni);
		textDificultadRespiratoriaFecIni.setVisibility(View.GONE);
		inputDificultadRespiratoriaFecIni = (EditText) rootView.findViewById(R.id.inputDificultadRespiratoriaFecIni);
		inputDificultadRespiratoriaFecIni.setVisibility(View.GONE);
		buttonDificultadRespiratoriaFecIni = (ImageButton) rootView.findViewById(R.id.buttonDificultadRespiratoriaFecIni);
		buttonDificultadRespiratoriaFecIni.setVisibility(View.GONE);

		textPocoApetitoFecIni = (TextView)rootView.findViewById(R.id.textPocoApetitoFecIni);
		textPocoApetitoFecIni.setVisibility(View.GONE);
		inputPocoApetitoFecIni = (EditText) rootView.findViewById(R.id.inputPocoApetitoFecIni);
		inputPocoApetitoFecIni.setVisibility(View.GONE);
		buttonPocoApetitoFecIni = (ImageButton) rootView.findViewById(R.id.buttonPocoApetitoFecIni);
		buttonPocoApetitoFecIni.setVisibility(View.GONE);

		textDolorGargantaFecIni = (TextView)rootView.findViewById(R.id.textDolorGargantaFecIni);
		textDolorGargantaFecIni.setVisibility(View.GONE);
		inputDolorGargantaFecIni = (EditText) rootView.findViewById(R.id.inputDolorGargantaFecIni);
		inputDolorGargantaFecIni.setVisibility(View.GONE);
		buttonDolorGargantaFecIni = (ImageButton) rootView.findViewById(R.id.buttonDolorGargantaFecIni);
		buttonDolorGargantaFecIni.setVisibility(View.GONE);

		textSecrecionNasalFecIni = (TextView)rootView.findViewById(R.id.textSecrecionNasalFecIni);
		textSecrecionNasalFecIni.setVisibility(View.GONE);
		inputSecrecionNasalFecIni = (EditText) rootView.findViewById(R.id.inputSecrecionNasalFecIni);
		inputSecrecionNasalFecIni.setVisibility(View.GONE);
		buttonSecrecionNasalFecIni = (ImageButton) rootView.findViewById(R.id.buttonSecrecionNasalFecIni);
		buttonSecrecionNasalFecIni.setVisibility(View.GONE);

		textPicorGargantaFecIni = (TextView)rootView.findViewById(R.id.textPicorGargantaFecIni);
		textPicorGargantaFecIni.setVisibility(View.GONE);
		inputPicorGargantaFecIni = (EditText) rootView.findViewById(R.id.inputPicorGargantaFecIni);
		inputPicorGargantaFecIni.setVisibility(View.GONE);
		buttonPicorGargantaFecIni = (ImageButton) rootView.findViewById(R.id.buttonPicorGargantaFecIni);
		buttonPicorGargantaFecIni.setVisibility(View.GONE);

		textExpectoracionFecIni = (TextView)rootView.findViewById(R.id.textExpectoracionFecIni);
		textExpectoracionFecIni.setVisibility(View.GONE);
		inputExpectoracionFecIni = (EditText) rootView.findViewById(R.id.inputExpectoracionFecIni);
		inputExpectoracionFecIni.setVisibility(View.GONE);
		buttonExpectoracionFecIni = (ImageButton) rootView.findViewById(R.id.buttonExpectoracionFecIni);
		buttonExpectoracionFecIni.setVisibility(View.GONE);

		textRashFecIni = (TextView)rootView.findViewById(R.id.textRashFecIni);
		textRashFecIni.setVisibility(View.GONE);
		inputRashFecIni = (EditText) rootView.findViewById(R.id.inputRashFecIni);
		inputRashFecIni.setVisibility(View.GONE);
		buttonRashFecIni = (ImageButton) rootView.findViewById(R.id.buttonRashFecIni);
		buttonRashFecIni.setVisibility(View.GONE);

		textUrticariaFecIni = (TextView)rootView.findViewById(R.id.textUrticariaFecIni);
		textUrticariaFecIni.setVisibility(View.GONE);
		inputUrticariaFecIni = (EditText) rootView.findViewById(R.id.inputUrticariaFecIni);
		inputUrticariaFecIni.setVisibility(View.GONE);
		buttonUrticariaFecIni = (ImageButton) rootView.findViewById(R.id.buttonUrticariaFecIni);
		buttonUrticariaFecIni.setVisibility(View.GONE);

		textConjuntivitisFecIni = (TextView)rootView.findViewById(R.id.textConjuntivitisFecIni);
		textConjuntivitisFecIni.setVisibility(View.GONE);
		inputConjuntivitisFecIni = (EditText) rootView.findViewById(R.id.inputConjuntivitisFecIni);
		inputConjuntivitisFecIni.setVisibility(View.GONE);
		buttonConjuntivitisFecIni = (ImageButton) rootView.findViewById(R.id.buttonConjuntivitisFecIni);
		buttonConjuntivitisFecIni.setVisibility(View.GONE);

		textDiarreaFecIni = (TextView)rootView.findViewById(R.id.textDiarreaFecIni);
		textDiarreaFecIni.setVisibility(View.GONE);
		inputDiarreaFecIni = (EditText) rootView.findViewById(R.id.inputDiarreaFecIni);
		inputDiarreaFecIni.setVisibility(View.GONE);
		buttonDiarreaFecIni = (ImageButton) rootView.findViewById(R.id.buttonDiarreaFecIni);
		buttonDiarreaFecIni.setVisibility(View.GONE);

		textVomitoFecIni = (TextView)rootView.findViewById(R.id.textVomitoFecIni);
		textVomitoFecIni.setVisibility(View.GONE);
		inputVomitoFecIni = (EditText) rootView.findViewById(R.id.inputVomitoFecIni);
		inputVomitoFecIni.setVisibility(View.GONE);
		buttonVomitoFecIni = (ImageButton) rootView.findViewById(R.id.buttonVomitoFecIni);
		buttonVomitoFecIni.setVisibility(View.GONE);

		textQuedoCamaFecIni = (TextView)rootView.findViewById(R.id.textQuedoCamaFecIni);
		textQuedoCamaFecIni.setVisibility(View.GONE);
		inputQuedoCamaFecIni = (EditText) rootView.findViewById(R.id.inputQuedoCamaFecIni);
		inputQuedoCamaFecIni.setVisibility(View.GONE);
		buttonQuedoCamaFecIni = (ImageButton) rootView.findViewById(R.id.buttonQuedoCamaFecIni);
		buttonQuedoCamaFecIni.setVisibility(View.GONE);

		textRespiracionRuidosaFecIni = (TextView)rootView.findViewById(R.id.textRespiracionRuidosaFecIni);
		textRespiracionRuidosaFecIni.setVisibility(View.GONE);
		inputRespiracionRuidosaFecIni = (EditText) rootView.findViewById(R.id.inputRespiracionRuidosaFecIni);
		inputRespiracionRuidosaFecIni.setVisibility(View.GONE);
		buttonRespiracionRuidosaFecIni = (ImageButton) rootView.findViewById(R.id.buttonRespiracionRuidosaFecIni);
		buttonRespiracionRuidosaFecIni.setVisibility(View.GONE);

		textRespiracionRapidaFecIni = (TextView)rootView.findViewById(R.id.textRespiracionRapidaFecIni);
		textRespiracionRapidaFecIni.setVisibility(View.GONE);
		inputRespiracionRapidaFecIni = (EditText) rootView.findViewById(R.id.inputRespiracionRapidaFecIni);
		inputRespiracionRapidaFecIni.setVisibility(View.GONE);
		buttonRespiracionRapidaFecIni = (ImageButton) rootView.findViewById(R.id.buttonRespiracionRapidaFecIni);
		buttonRespiracionRapidaFecIni.setVisibility(View.GONE);

		textPerdidaOlfatoFecIni = (TextView)rootView.findViewById(R.id.textPerdidaOlfatoFecIni);
		textPerdidaOlfatoFecIni.setVisibility(View.GONE);
		inputPerdidaOlfatoFecIni = (EditText) rootView.findViewById(R.id.inputPerdidaOlfatoFecIni);
		inputPerdidaOlfatoFecIni.setVisibility(View.GONE);
		buttonPerdidaOlfatoFecIni = (ImageButton) rootView.findViewById(R.id.buttonPerdidaOlfatoFecIni);
		buttonPerdidaOlfatoFecIni.setVisibility(View.GONE);

		textCongestionNasalFecIni = (TextView)rootView.findViewById(R.id.textCongestionNasalFecIni);
		textCongestionNasalFecIni.setVisibility(View.GONE);
		inputCongestionNasalFecIni = (EditText) rootView.findViewById(R.id.inputCongestionNasalFecIni);
		inputCongestionNasalFecIni.setVisibility(View.GONE);
		buttonCongestionNasalFecIni = (ImageButton) rootView.findViewById(R.id.buttonCongestionNasalFecIni);
		buttonCongestionNasalFecIni.setVisibility(View.GONE);

		textPerdidaGustoFecIni = (TextView)rootView.findViewById(R.id.textPerdidaGustoFecIni);
		textPerdidaGustoFecIni.setVisibility(View.GONE);
		inputPerdidaGustoFecIni = (EditText) rootView.findViewById(R.id.inputPerdidaGustoFecIni);
		inputPerdidaGustoFecIni.setVisibility(View.GONE);
		buttonPerdidaGustoFecIni = (ImageButton) rootView.findViewById(R.id.buttonPerdidaGustoFecIni);
		buttonPerdidaGustoFecIni.setVisibility(View.GONE);

		textDesmayosFecIni = (TextView)rootView.findViewById(R.id.textDesmayosFecIni);
		textDesmayosFecIni.setVisibility(View.GONE);
		inputDesmayosFecIni = (EditText) rootView.findViewById(R.id.inputDesmayosFecIni);
		inputDesmayosFecIni.setVisibility(View.GONE);
		buttonDesmayosFecIni = (ImageButton) rootView.findViewById(R.id.buttonDesmayosFecIni);
		buttonDesmayosFecIni.setVisibility(View.GONE);

		textSensacionPechoApretadoFecIni = (TextView)rootView.findViewById(R.id.textSensacionPechoApretadoFecIni);
		textSensacionPechoApretadoFecIni.setVisibility(View.GONE);
		inputSensacionPechoApretadoFecIni = (EditText) rootView.findViewById(R.id.inputSensacionPechoApretadoFecIni);
		inputSensacionPechoApretadoFecIni.setVisibility(View.GONE);
		buttonSensacionPechoApretadoFecIni = (ImageButton) rootView.findViewById(R.id.buttonSensacionPechoApretadoFecIni);
		buttonSensacionPechoApretadoFecIni.setVisibility(View.GONE);

		textDolorPechoFecIni = (TextView)rootView.findViewById(R.id.textDolorPechoFecIni);
		textDolorPechoFecIni.setVisibility(View.GONE);
		inputDolorPechoFecIni = (EditText) rootView.findViewById(R.id.inputDolorPechoFecIni);
		inputDolorPechoFecIni.setVisibility(View.GONE);
		buttonDolorPechoFecIni = (ImageButton) rootView.findViewById(R.id.buttonDolorPechoFecIni);
		buttonDolorPechoFecIni.setVisibility(View.GONE);

		textSensacionFaltaAireFecIni = (TextView)rootView.findViewById(R.id.textSensacionFaltaAireFecIni);
		textSensacionFaltaAireFecIni.setVisibility(View.GONE);
		inputSensacionFaltaAireFecIni = (EditText) rootView.findViewById(R.id.inputSensacionFaltaAireFecIni);
		inputSensacionFaltaAireFecIni.setVisibility(View.GONE);
		buttonSensacionFaltaAireFecIni = (ImageButton) rootView.findViewById(R.id.buttonSensacionFaltaAireFecIni);
		buttonSensacionFaltaAireFecIni.setVisibility(View.GONE);

		textFatigaFecIni = (TextView)rootView.findViewById(R.id.textFatigaFecIni);
		textFatigaFecIni.setVisibility(View.GONE);
		inputFatigaFecIni = (EditText) rootView.findViewById(R.id.inputFatigaFecIni);
		inputFatigaFecIni.setVisibility(View.GONE);
		buttonFatigaFecIni = (ImageButton) rootView.findViewById(R.id.buttonFatigaFecIni);
		buttonFatigaFecIni.setVisibility(View.GONE);

		textFiebreFecFin = (TextView)rootView.findViewById(R.id.textFiebreFecFin);
		textFiebreFecFin.setVisibility(View.GONE);
		inputFiebreFecFin = (EditText) rootView.findViewById(R.id.inputFiebreFecFin);
		inputFiebreFecFin.setVisibility(View.GONE);
		buttonFiebreFecFin = (ImageButton) rootView.findViewById(R.id.buttonFiebreFecFin);
		buttonFiebreFecFin.setVisibility(View.GONE);

		textTosFecFin = (TextView)rootView.findViewById(R.id.textTosFecFin);
		textTosFecFin.setVisibility(View.GONE);
		inputTosFecFin = (EditText) rootView.findViewById(R.id.inputTosFecFin);
		inputTosFecFin.setVisibility(View.GONE);
		buttonTosFecFin = (ImageButton) rootView.findViewById(R.id.buttonTosFecFin);
		buttonTosFecFin.setVisibility(View.GONE);

		textDolorCabezaFecFin = (TextView)rootView.findViewById(R.id.textDolorCabezaFecFin);
		textDolorCabezaFecFin.setVisibility(View.GONE);
		inputDolorCabezaFecFin = (EditText) rootView.findViewById(R.id.inputDolorCabezaFecFin);
		inputDolorCabezaFecFin.setVisibility(View.GONE);
		buttonDolorCabezaFecFin = (ImageButton) rootView.findViewById(R.id.buttonDolorCabezaFecFin);
		buttonDolorCabezaFecFin.setVisibility(View.GONE);

		textDolorArticularFecFin = (TextView)rootView.findViewById(R.id.textDolorArticularFecFin);
		textDolorArticularFecFin.setVisibility(View.GONE);
		inputDolorArticularFecFin = (EditText) rootView.findViewById(R.id.inputDolorArticularFecFin);
		inputDolorArticularFecFin.setVisibility(View.GONE);
		buttonDolorArticularFecFin = (ImageButton) rootView.findViewById(R.id.buttonDolorArticularFecFin);
		buttonDolorArticularFecFin.setVisibility(View.GONE);

		textDolorMuscularFecFin = (TextView)rootView.findViewById(R.id.textDolorMuscularFecFin);
		textDolorMuscularFecFin.setVisibility(View.GONE);
		inputDolorMuscularFecFin = (EditText) rootView.findViewById(R.id.inputDolorMuscularFecFin);
		inputDolorMuscularFecFin.setVisibility(View.GONE);
		buttonDolorMuscularFecFin = (ImageButton) rootView.findViewById(R.id.buttonDolorMuscularFecFin);
		buttonDolorMuscularFecFin.setVisibility(View.GONE);

		textDificultadRespiratoriaFecFin = (TextView)rootView.findViewById(R.id.textDificultadRespiratoriaFecFin);
		textDificultadRespiratoriaFecFin.setVisibility(View.GONE);
		inputDificultadRespiratoriaFecFin = (EditText) rootView.findViewById(R.id.inputDificultadRespiratoriaFecFin);
		inputDificultadRespiratoriaFecFin.setVisibility(View.GONE);
		buttonDificultadRespiratoriaFecFin = (ImageButton) rootView.findViewById(R.id.buttonDificultadRespiratoriaFecFin);
		buttonDificultadRespiratoriaFecFin.setVisibility(View.GONE);

		textPocoApetitoFecFin = (TextView)rootView.findViewById(R.id.textPocoApetitoFecFin);
		textPocoApetitoFecFin.setVisibility(View.GONE);
		inputPocoApetitoFecFin = (EditText) rootView.findViewById(R.id.inputPocoApetitoFecFin);
		inputPocoApetitoFecFin.setVisibility(View.GONE);
		buttonPocoApetitoFecFin = (ImageButton) rootView.findViewById(R.id.buttonPocoApetitoFecFin);
		buttonPocoApetitoFecFin.setVisibility(View.GONE);

		textDolorGargantaFecFin = (TextView)rootView.findViewById(R.id.textDolorGargantaFecFin);
		textDolorGargantaFecFin.setVisibility(View.GONE);
		inputDolorGargantaFecFin = (EditText) rootView.findViewById(R.id.inputDolorGargantaFecFin);
		inputDolorGargantaFecFin.setVisibility(View.GONE);
		buttonDolorGargantaFecFin = (ImageButton) rootView.findViewById(R.id.buttonDolorGargantaFecFin);
		buttonDolorGargantaFecFin.setVisibility(View.GONE);

		textSecrecionNasalFecFin = (TextView)rootView.findViewById(R.id.textSecrecionNasalFecFin);
		textSecrecionNasalFecFin.setVisibility(View.GONE);
		inputSecrecionNasalFecFin = (EditText) rootView.findViewById(R.id.inputSecrecionNasalFecFin);
		inputSecrecionNasalFecFin.setVisibility(View.GONE);
		buttonSecrecionNasalFecFin = (ImageButton) rootView.findViewById(R.id.buttonSecrecionNasalFecFin);
		buttonSecrecionNasalFecFin.setVisibility(View.GONE);

		textPicorGargantaFecFin = (TextView)rootView.findViewById(R.id.textPicorGargantaFecFin);
		textPicorGargantaFecFin.setVisibility(View.GONE);
		inputPicorGargantaFecFin = (EditText) rootView.findViewById(R.id.inputPicorGargantaFecFin);
		inputPicorGargantaFecFin.setVisibility(View.GONE);
		buttonPicorGargantaFecFin = (ImageButton) rootView.findViewById(R.id.buttonPicorGargantaFecFin);
		buttonPicorGargantaFecFin.setVisibility(View.GONE);

		textExpectoracionFecFin = (TextView)rootView.findViewById(R.id.textExpectoracionFecFin);
		textExpectoracionFecFin.setVisibility(View.GONE);
		inputExpectoracionFecFin = (EditText) rootView.findViewById(R.id.inputExpectoracionFecFin);
		inputExpectoracionFecFin.setVisibility(View.GONE);
		buttonExpectoracionFecFin = (ImageButton) rootView.findViewById(R.id.buttonExpectoracionFecFin);
		buttonExpectoracionFecFin.setVisibility(View.GONE);

		textRashFecFin = (TextView)rootView.findViewById(R.id.textRashFecFin);
		textRashFecFin.setVisibility(View.GONE);
		inputRashFecFin = (EditText) rootView.findViewById(R.id.inputRashFecFin);
		inputRashFecFin.setVisibility(View.GONE);
		buttonRashFecFin = (ImageButton) rootView.findViewById(R.id.buttonRashFecFin);
		buttonRashFecFin.setVisibility(View.GONE);

		textUrticariaFecFin = (TextView)rootView.findViewById(R.id.textUrticariaFecFin);
		textUrticariaFecFin.setVisibility(View.GONE);
		inputUrticariaFecFin = (EditText) rootView.findViewById(R.id.inputUrticariaFecFin);
		inputUrticariaFecFin.setVisibility(View.GONE);
		buttonUrticariaFecFin = (ImageButton) rootView.findViewById(R.id.buttonUrticariaFecFin);
		buttonUrticariaFecFin.setVisibility(View.GONE);

		textConjuntivitisFecFin = (TextView)rootView.findViewById(R.id.textConjuntivitisFecFin);
		textConjuntivitisFecFin.setVisibility(View.GONE);
		inputConjuntivitisFecFin = (EditText) rootView.findViewById(R.id.inputConjuntivitisFecFin);
		inputConjuntivitisFecFin.setVisibility(View.GONE);
		buttonConjuntivitisFecFin = (ImageButton) rootView.findViewById(R.id.buttonConjuntivitisFecFin);
		buttonConjuntivitisFecFin.setVisibility(View.GONE);

		textDiarreaFecFin = (TextView)rootView.findViewById(R.id.textDiarreaFecFin);
		textDiarreaFecFin.setVisibility(View.GONE);
		inputDiarreaFecFin = (EditText) rootView.findViewById(R.id.inputDiarreaFecFin);
		inputDiarreaFecFin.setVisibility(View.GONE);
		buttonDiarreaFecFin = (ImageButton) rootView.findViewById(R.id.buttonDiarreaFecFin);
		buttonDiarreaFecFin.setVisibility(View.GONE);

		textVomitoFecFin = (TextView)rootView.findViewById(R.id.textVomitoFecFin);
		textVomitoFecFin.setVisibility(View.GONE);
		inputVomitoFecFin = (EditText) rootView.findViewById(R.id.inputVomitoFecFin);
		inputVomitoFecFin.setVisibility(View.GONE);
		buttonVomitoFecFin = (ImageButton) rootView.findViewById(R.id.buttonVomitoFecFin);
		buttonVomitoFecFin.setVisibility(View.GONE);

		textQuedoCamaFecFin = (TextView)rootView.findViewById(R.id.textQuedoCamaFecFin);
		textQuedoCamaFecFin.setVisibility(View.GONE);
		inputQuedoCamaFecFin = (EditText) rootView.findViewById(R.id.inputQuedoCamaFecFin);
		inputQuedoCamaFecFin.setVisibility(View.GONE);
		buttonQuedoCamaFecFin = (ImageButton) rootView.findViewById(R.id.buttonQuedoCamaFecFin);
		buttonQuedoCamaFecFin.setVisibility(View.GONE);

		textRespiracionRuidosaFecFin = (TextView)rootView.findViewById(R.id.textRespiracionRuidosaFecFin);
		textRespiracionRuidosaFecFin.setVisibility(View.GONE);
		inputRespiracionRuidosaFecFin = (EditText) rootView.findViewById(R.id.inputRespiracionRuidosaFecFin);
		inputRespiracionRuidosaFecFin.setVisibility(View.GONE);
		buttonRespiracionRuidosaFecFin = (ImageButton) rootView.findViewById(R.id.buttonRespiracionRuidosaFecFin);
		buttonRespiracionRuidosaFecFin.setVisibility(View.GONE);

		textRespiracionRapidaFecFin = (TextView)rootView.findViewById(R.id.textRespiracionRapidaFecFin);
		textRespiracionRapidaFecFin.setVisibility(View.GONE);
		inputRespiracionRapidaFecFin = (EditText) rootView.findViewById(R.id.inputRespiracionRapidaFecFin);
		inputRespiracionRapidaFecFin.setVisibility(View.GONE);
		buttonRespiracionRapidaFecFin = (ImageButton) rootView.findViewById(R.id.buttonRespiracionRapidaFecFin);
		buttonRespiracionRapidaFecFin.setVisibility(View.GONE);

		textPerdidaOlfatoFecFin = (TextView)rootView.findViewById(R.id.textPerdidaOlfatoFecFin);
		textPerdidaOlfatoFecFin.setVisibility(View.GONE);
		inputPerdidaOlfatoFecFin = (EditText) rootView.findViewById(R.id.inputPerdidaOlfatoFecFin);
		inputPerdidaOlfatoFecFin.setVisibility(View.GONE);
		buttonPerdidaOlfatoFecFin = (ImageButton) rootView.findViewById(R.id.buttonPerdidaOlfatoFecFin);
		buttonPerdidaOlfatoFecFin.setVisibility(View.GONE);

		textCongestionNasalFecFin = (TextView)rootView.findViewById(R.id.textCongestionNasalFecFin);
		textCongestionNasalFecFin.setVisibility(View.GONE);
		inputCongestionNasalFecFin = (EditText) rootView.findViewById(R.id.inputCongestionNasalFecFin);
		inputCongestionNasalFecFin.setVisibility(View.GONE);
		buttonCongestionNasalFecFin = (ImageButton) rootView.findViewById(R.id.buttonCongestionNasalFecFin);
		buttonCongestionNasalFecFin.setVisibility(View.GONE);

		textPerdidaGustoFecFin = (TextView)rootView.findViewById(R.id.textPerdidaGustoFecFin);
		textPerdidaGustoFecFin.setVisibility(View.GONE);
		inputPerdidaGustoFecFin = (EditText) rootView.findViewById(R.id.inputPerdidaGustoFecFin);
		inputPerdidaGustoFecFin.setVisibility(View.GONE);
		buttonPerdidaGustoFecFin = (ImageButton) rootView.findViewById(R.id.buttonPerdidaGustoFecFin);
		buttonPerdidaGustoFecFin.setVisibility(View.GONE);

		textDesmayosFecFin = (TextView)rootView.findViewById(R.id.textDesmayosFecFin);
		textDesmayosFecFin.setVisibility(View.GONE);
		inputDesmayosFecFin = (EditText) rootView.findViewById(R.id.inputDesmayosFecFin);
		inputDesmayosFecFin.setVisibility(View.GONE);
		buttonDesmayosFecFin = (ImageButton) rootView.findViewById(R.id.buttonDesmayosFecFin);
		buttonDesmayosFecFin.setVisibility(View.GONE);

		textSensacionPechoApretadoFecFin = (TextView)rootView.findViewById(R.id.textSensacionPechoApretadoFecFin);
		textSensacionPechoApretadoFecFin.setVisibility(View.GONE);
		inputSensacionPechoApretadoFecFin = (EditText) rootView.findViewById(R.id.inputSensacionPechoApretadoFecFin);
		inputSensacionPechoApretadoFecFin.setVisibility(View.GONE);
		buttonSensacionPechoApretadoFecFin = (ImageButton) rootView.findViewById(R.id.buttonSensacionPechoApretadoFecFin);
		buttonSensacionPechoApretadoFecFin.setVisibility(View.GONE);

		textDolorPechoFecFin = (TextView)rootView.findViewById(R.id.textDolorPechoFecFin);
		textDolorPechoFecFin.setVisibility(View.GONE);
		inputDolorPechoFecFin = (EditText) rootView.findViewById(R.id.inputDolorPechoFecFin);
		inputDolorPechoFecFin.setVisibility(View.GONE);
		buttonDolorPechoFecFin = (ImageButton) rootView.findViewById(R.id.buttonDolorPechoFecFin);
		buttonDolorPechoFecFin.setVisibility(View.GONE);

		textSensacionFaltaAireFecFin = (TextView)rootView.findViewById(R.id.textSensacionFaltaAireFecFin);
		textSensacionFaltaAireFecFin.setVisibility(View.GONE);
		inputSensacionFaltaAireFecFin = (EditText) rootView.findViewById(R.id.inputSensacionFaltaAireFecFin);
		inputSensacionFaltaAireFecFin.setVisibility(View.GONE);
		buttonSensacionFaltaAireFecFin = (ImageButton) rootView.findViewById(R.id.buttonSensacionFaltaAireFecFin);
		buttonSensacionFaltaAireFecFin.setVisibility(View.GONE);

		textFatigaFecFin = (TextView)rootView.findViewById(R.id.textFatigaFecFin);
		textFatigaFecFin.setVisibility(View.GONE);
		inputFatigaFecFin = (EditText) rootView.findViewById(R.id.inputFatigaFecFin);
		inputFatigaFecFin.setVisibility(View.GONE);
		buttonFatigaFecFin = (ImageButton) rootView.findViewById(R.id.buttonFatigaFecFin);
		buttonFatigaFecFin.setVisibility(View.GONE);

		buttonFiebreFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I1);
			}
		});
		buttonTosFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I2);
			}
		});
		buttonDolorCabezaFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I3);
			}
		});
		buttonDolorArticularFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I4);
			}
		});
		buttonDolorMuscularFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I5);
			}
		});
		buttonDificultadRespiratoriaFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I6);
			}
		});

		buttonPocoApetitoFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I7);
			}
		});
		buttonDolorGargantaFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I8);
			}
		});
		buttonSecrecionNasalFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I9);
			}
		});
		buttonPicorGargantaFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I10);
			}
		});
		buttonExpectoracionFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I11);
			}
		});
		buttonRashFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I12);
			}
		});
		buttonUrticariaFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I13);
			}
		});
		buttonConjuntivitisFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I14);
			}
		});
		buttonDiarreaFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I15);
			}
		});
		buttonVomitoFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I16);
			}
		});
		buttonQuedoCamaFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I17);
			}
		});
		buttonRespiracionRuidosaFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I18);
			}
		});
		buttonRespiracionRapidaFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I19);
			}
		});
		buttonPerdidaOlfatoFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I20);
			}
		});
		buttonCongestionNasalFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I21);
			}
		});
		buttonPerdidaGustoFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I22);
			}
		});
		buttonDesmayosFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I23);
			}
		});
		buttonSensacionPechoApretadoFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I24);
			}
		});
		buttonDolorPechoFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I25);
			}
		});
		buttonSensacionFaltaAireFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I26);
			}
		});
		buttonFatigaFecIni.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_I27);
			}
		});

		buttonFiebreFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F1);
			}
		});
		buttonTosFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F2);
			}
		});
		buttonDolorCabezaFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F3);
			}
		});
		buttonDolorArticularFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F4);
			}
		});
		buttonDolorMuscularFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F5);
			}
		});
		buttonDificultadRespiratoriaFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F6);
			}
		});

		buttonPocoApetitoFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F7);
			}
		});
		buttonDolorGargantaFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F8);
			}
		});
		buttonSecrecionNasalFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F9);
			}
		});
		buttonPicorGargantaFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F10);
			}
		});
		buttonExpectoracionFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F11);
			}
		});
		buttonRashFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F12);
			}
		});
		buttonUrticariaFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F13);
			}
		});
		buttonConjuntivitisFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F14);
			}
		});
		buttonDiarreaFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F15);
			}
		});
		buttonVomitoFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F16);
			}
		});
		buttonQuedoCamaFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F17);
			}
		});
		buttonRespiracionRuidosaFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F18);
			}
		});
		buttonRespiracionRapidaFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F19);
			}
		});
		buttonPerdidaOlfatoFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F20);
			}
		});
		buttonCongestionNasalFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F21);
			}
		});
		buttonPerdidaGustoFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F22);
			}
		});
		buttonDesmayosFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F23);
			}
		});
		buttonSensacionPechoApretadoFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F24);
			}
		});
		buttonDolorPechoFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F25);
			}
		});
		buttonSensacionFaltaAireFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F26);
			}
		});
		buttonFatigaFecFin.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_F27);
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
					spinFiebre.setBackgroundColor(Color.RED);
	        		inputFiebreFecIni.setVisibility(View.VISIBLE);
	        		textFiebreFecIni.setVisibility(View.VISIBLE);
	        		buttonFiebreFecIni.setVisibility(View.VISIBLE);
					inputFiebreFecFin.setVisibility(View.VISIBLE);
					textFiebreFecFin.setVisibility(View.VISIBLE);
					buttonFiebreFecFin.setVisibility(View.VISIBLE);
	        	}
	        	else{
					spinFiebre.setBackgroundColor(Color.WHITE);
					inputFiebreFecIni.setVisibility(View.GONE);
					textFiebreFecIni.setVisibility(View.GONE);
					buttonFiebreFecIni.setVisibility(View.GONE);
					inputFiebreFecFin.setVisibility(View.GONE);
					textFiebreFecFin.setVisibility(View.GONE);
					buttonFiebreFecFin.setVisibility(View.GONE);
					fiebreFecIni = null;
					fiebreFecFin = null;
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
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
					inputDolorCabezaFecIni.setVisibility(View.VISIBLE);
					textDolorCabezaFecIni.setVisibility(View.VISIBLE);
					buttonDolorCabezaFecIni.setVisibility(View.VISIBLE);
					inputDolorCabezaFecFin.setVisibility(View.VISIBLE);
					textDolorCabezaFecFin.setVisibility(View.VISIBLE);
					buttonDolorCabezaFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinDolorCabeza.setBackgroundColor(Color.WHITE);
					inputDolorCabezaFecIni.setVisibility(View.GONE);
					textDolorCabezaFecIni.setVisibility(View.GONE);
					buttonDolorCabezaFecIni.setVisibility(View.GONE);
					inputDolorCabezaFecFin.setVisibility(View.GONE);
					textDolorCabezaFecFin.setVisibility(View.GONE);
					buttonDolorCabezaFecFin.setVisibility(View.GONE);
					dolorCabezaFecIni = null;
					dolorCabezaFecFin = null;
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
					inputDolorArticularFecIni.setVisibility(View.VISIBLE);
					textDolorArticularFecIni.setVisibility(View.VISIBLE);
					buttonDolorArticularFecIni.setVisibility(View.VISIBLE);
					inputDolorArticularFecFin.setVisibility(View.VISIBLE);
					textDolorArticularFecFin.setVisibility(View.VISIBLE);
					buttonDolorArticularFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinDolorArticular.setBackgroundColor(Color.WHITE);
					inputDolorArticularFecIni.setVisibility(View.GONE);
					textDolorArticularFecIni.setVisibility(View.GONE);
					buttonDolorArticularFecIni.setVisibility(View.GONE);
					inputDolorArticularFecFin.setVisibility(View.GONE);
					textDolorArticularFecFin.setVisibility(View.GONE);
					buttonDolorArticularFecFin.setVisibility(View.GONE);
					dolorArticularFecIni = null;
					dolorArticularFecFin = null;
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
					inputDolorMuscularFecIni.setVisibility(View.VISIBLE);
					textDolorMuscularFecIni.setVisibility(View.VISIBLE);
					buttonDolorMuscularFecIni.setVisibility(View.VISIBLE);
					inputDolorMuscularFecFin.setVisibility(View.VISIBLE);
					textDolorMuscularFecFin.setVisibility(View.VISIBLE);
					buttonDolorMuscularFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinDolorMuscular.setBackgroundColor(Color.WHITE);
					inputDolorMuscularFecIni.setVisibility(View.GONE);
					textDolorMuscularFecIni.setVisibility(View.GONE);
					buttonDolorMuscularFecIni.setVisibility(View.GONE);
					inputDolorMuscularFecFin.setVisibility(View.GONE);
					textDolorMuscularFecFin.setVisibility(View.GONE);
					buttonDolorMuscularFecFin.setVisibility(View.GONE);
					dolorMuscularFecIni = null;
					dolorMuscularFecFin = null;
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
					inputDificultadRespiratoriaFecIni.setVisibility(View.VISIBLE);
					textDificultadRespiratoriaFecIni.setVisibility(View.VISIBLE);
					buttonDificultadRespiratoriaFecIni.setVisibility(View.VISIBLE);
					inputDificultadRespiratoriaFecFin.setVisibility(View.VISIBLE);
					textDificultadRespiratoriaFecFin.setVisibility(View.VISIBLE);
					buttonDificultadRespiratoriaFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinDificultadRespiratoria.setBackgroundColor(Color.WHITE);
					inputDificultadRespiratoriaFecIni.setVisibility(View.GONE);
					textDificultadRespiratoriaFecIni.setVisibility(View.GONE);
					buttonDificultadRespiratoriaFecIni.setVisibility(View.GONE);
					inputDificultadRespiratoriaFecFin.setVisibility(View.GONE);
					textDificultadRespiratoriaFecFin.setVisibility(View.GONE);
					buttonDificultadRespiratoriaFecFin.setVisibility(View.GONE);
					dificultadRespiratoriaFecIni = null;
					dificultadRespiratoriaFecFin = null;
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
					inputSecrecionNasalFecIni.setVisibility(View.VISIBLE);
					textSecrecionNasalFecIni.setVisibility(View.VISIBLE);
					buttonSecrecionNasalFecIni.setVisibility(View.VISIBLE);
					inputSecrecionNasalFecFin.setVisibility(View.VISIBLE);
					textSecrecionNasalFecFin.setVisibility(View.VISIBLE);
					buttonSecrecionNasalFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinSecrecionNasal.setBackgroundColor(Color.WHITE);
					inputSecrecionNasalFecIni.setVisibility(View.GONE);
					textSecrecionNasalFecIni.setVisibility(View.GONE);
					buttonSecrecionNasalFecIni.setVisibility(View.GONE);
					inputSecrecionNasalFecFin.setVisibility(View.GONE);
					textSecrecionNasalFecFin.setVisibility(View.GONE);
					buttonSecrecionNasalFecFin.setVisibility(View.GONE);
					secrecionNasalFecIni = null;
					secrecionNasalFecFin = null;
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
					inputTosFecIni.setVisibility(View.VISIBLE);
					textTosFecIni.setVisibility(View.VISIBLE);
					buttonTosFecIni.setVisibility(View.VISIBLE);
					inputTosFecFin.setVisibility(View.VISIBLE);
					textTosFecFin.setVisibility(View.VISIBLE);
					buttonTosFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinTos.setBackgroundColor(Color.WHITE);
					inputTosFecIni.setVisibility(View.GONE);
					textTosFecIni.setVisibility(View.GONE);
					buttonTosFecIni.setVisibility(View.GONE);
					inputTosFecFin.setVisibility(View.GONE);
					textTosFecFin.setVisibility(View.GONE);
					buttonTosFecFin.setVisibility(View.GONE);
					tosFecIni = null;
					tosFecFin = null;
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
					inputPocoApetitoFecIni.setVisibility(View.VISIBLE);
					textPocoApetitoFecIni.setVisibility(View.VISIBLE);
					buttonPocoApetitoFecIni.setVisibility(View.VISIBLE);
					inputPocoApetitoFecFin.setVisibility(View.VISIBLE);
					textPocoApetitoFecFin.setVisibility(View.VISIBLE);
					buttonPocoApetitoFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinPocoApetito.setBackgroundColor(Color.WHITE);
					inputPocoApetitoFecIni.setVisibility(View.GONE);
					textPocoApetitoFecIni.setVisibility(View.GONE);
					buttonPocoApetitoFecIni.setVisibility(View.GONE);
					inputPocoApetitoFecFin.setVisibility(View.GONE);
					textPocoApetitoFecFin.setVisibility(View.GONE);
					buttonPocoApetitoFecFin.setVisibility(View.GONE);
					pocoApetitoFecIni = null;
					pocoApetitoFecFin = null;
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
					inputDolorGargantaFecIni.setVisibility(View.VISIBLE);
					textDolorGargantaFecIni.setVisibility(View.VISIBLE);
					buttonDolorGargantaFecIni.setVisibility(View.VISIBLE);
					inputDolorGargantaFecFin.setVisibility(View.VISIBLE);
					textDolorGargantaFecFin.setVisibility(View.VISIBLE);
					buttonDolorGargantaFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinDolorGarganta.setBackgroundColor(Color.WHITE);
					inputDolorGargantaFecIni.setVisibility(View.GONE);
					textDolorGargantaFecIni.setVisibility(View.GONE);
					buttonDolorGargantaFecIni.setVisibility(View.GONE);
					inputDolorGargantaFecFin.setVisibility(View.GONE);
					textDolorGargantaFecFin.setVisibility(View.GONE);
					buttonDolorGargantaFecFin.setVisibility(View.GONE);
					dolorGargantaFecIni = null;
					dolorGargantaFecFin = null;
				}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
		spinPicorGarganta = (Spinner) rootView.findViewById(R.id.spinPicorGarganta);
		spinPicorGarganta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				picorGarganta = mr.getCatKey();
				if(picorGarganta.equals(Constants.YESKEYSND)){
					spinPicorGarganta.setBackgroundColor(Color.RED);
					inputPicorGargantaFecIni.setVisibility(View.VISIBLE);
					textPicorGargantaFecIni.setVisibility(View.VISIBLE);
					buttonPicorGargantaFecIni.setVisibility(View.VISIBLE);
					inputPicorGargantaFecFin.setVisibility(View.VISIBLE);
					textPicorGargantaFecFin.setVisibility(View.VISIBLE);
					buttonPicorGargantaFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinPicorGarganta.setBackgroundColor(Color.WHITE);
					inputPicorGargantaFecIni.setVisibility(View.GONE);
					textPicorGargantaFecIni.setVisibility(View.GONE);
					buttonPicorGargantaFecIni.setVisibility(View.GONE);
					inputPicorGargantaFecFin.setVisibility(View.GONE);
					textPicorGargantaFecFin.setVisibility(View.GONE);
					buttonPicorGargantaFecFin.setVisibility(View.GONE);
					picorGargantaFecIni = null;
					picorGargantaFecFin = null;
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
					inputCongestionNasalFecIni.setVisibility(View.VISIBLE);
					textCongestionNasalFecIni.setVisibility(View.VISIBLE);
					buttonCongestionNasalFecIni.setVisibility(View.VISIBLE);
					inputCongestionNasalFecFin.setVisibility(View.VISIBLE);
					textCongestionNasalFecFin.setVisibility(View.VISIBLE);
					buttonCongestionNasalFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinCongestionNasal.setBackgroundColor(Color.WHITE);
					inputCongestionNasalFecIni.setVisibility(View.GONE);
					textCongestionNasalFecIni.setVisibility(View.GONE);
					buttonCongestionNasalFecIni.setVisibility(View.GONE);
					inputCongestionNasalFecFin.setVisibility(View.GONE);
					textCongestionNasalFecFin.setVisibility(View.GONE);
					buttonCongestionNasalFecFin.setVisibility(View.GONE);
					congestionNasalFecIni = null;
					congestionNasalFecFin = null;
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
					inputRashFecIni.setVisibility(View.VISIBLE);
					textRashFecIni.setVisibility(View.VISIBLE);
					buttonRashFecIni.setVisibility(View.VISIBLE);
					inputRashFecFin.setVisibility(View.VISIBLE);
					textRashFecFin.setVisibility(View.VISIBLE);
					buttonRashFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinRash.setBackgroundColor(Color.WHITE);
					inputRashFecIni.setVisibility(View.GONE);
					textRashFecIni.setVisibility(View.GONE);
					buttonRashFecIni.setVisibility(View.GONE);
					inputRashFecFin.setVisibility(View.GONE);
					textRashFecFin.setVisibility(View.GONE);
					buttonRashFecFin.setVisibility(View.GONE);
					rashFecIni = null;
					rashFecFin = null;
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
					inputUrticariaFecIni.setVisibility(View.VISIBLE);
					textUrticariaFecIni.setVisibility(View.VISIBLE);
					buttonUrticariaFecIni.setVisibility(View.VISIBLE);
					inputUrticariaFecFin.setVisibility(View.VISIBLE);
					textUrticariaFecFin.setVisibility(View.VISIBLE);
					buttonUrticariaFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinUrticaria.setBackgroundColor(Color.WHITE);
					inputUrticariaFecIni.setVisibility(View.GONE);
					textUrticariaFecIni.setVisibility(View.GONE);
					buttonUrticariaFecIni.setVisibility(View.GONE);
					inputUrticariaFecFin.setVisibility(View.GONE);
					textUrticariaFecFin.setVisibility(View.GONE);
					buttonUrticariaFecFin.setVisibility(View.GONE);
					urticariaFecIni = null;
					urticariaFecFin = null;
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
					inputConjuntivitisFecIni.setVisibility(View.VISIBLE);
					textConjuntivitisFecIni.setVisibility(View.VISIBLE);
					buttonConjuntivitisFecIni.setVisibility(View.VISIBLE);
					inputConjuntivitisFecFin.setVisibility(View.VISIBLE);
					textConjuntivitisFecFin.setVisibility(View.VISIBLE);
					buttonConjuntivitisFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinConjuntivitis.setBackgroundColor(Color.WHITE);
					inputConjuntivitisFecIni.setVisibility(View.GONE);
					textConjuntivitisFecIni.setVisibility(View.GONE);
					buttonConjuntivitisFecIni.setVisibility(View.GONE);
					inputConjuntivitisFecFin.setVisibility(View.GONE);
					textConjuntivitisFecFin.setVisibility(View.GONE);
					buttonConjuntivitisFecFin.setVisibility(View.GONE);
					conjuntivitisFecIni = null;
					conjuntivitisFecFin = null;
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
					inputExpectoracionFecIni.setVisibility(View.VISIBLE);
					textExpectoracionFecIni.setVisibility(View.VISIBLE);
					buttonExpectoracionFecIni.setVisibility(View.VISIBLE);
					inputExpectoracionFecFin.setVisibility(View.VISIBLE);
					textExpectoracionFecFin.setVisibility(View.VISIBLE);
					buttonExpectoracionFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinExpectoracion.setBackgroundColor(Color.WHITE);
					inputExpectoracionFecIni.setVisibility(View.GONE);
					textExpectoracionFecIni.setVisibility(View.GONE);
					buttonExpectoracionFecIni.setVisibility(View.GONE);
					inputExpectoracionFecFin.setVisibility(View.GONE);
					textExpectoracionFecFin.setVisibility(View.GONE);
					buttonExpectoracionFecFin.setVisibility(View.GONE);
					expectoracionFecIni = null;
					expectoracionFecFin = null;
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
					inputDiarreaFecIni.setVisibility(View.VISIBLE);
					textDiarreaFecIni.setVisibility(View.VISIBLE);
					buttonDiarreaFecIni.setVisibility(View.VISIBLE);
					inputDiarreaFecFin.setVisibility(View.VISIBLE);
					textDiarreaFecFin.setVisibility(View.VISIBLE);
					buttonDiarreaFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinDiarrea.setBackgroundColor(Color.WHITE);
					inputDiarreaFecIni.setVisibility(View.GONE);
					textDiarreaFecIni.setVisibility(View.GONE);
					buttonDiarreaFecIni.setVisibility(View.GONE);
					inputDiarreaFecFin.setVisibility(View.GONE);
					textDiarreaFecFin.setVisibility(View.GONE);
					buttonDiarreaFecFin.setVisibility(View.GONE);
					diarreaFecIni = null;
					diarreaFecFin = null;
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
					inputVomitoFecIni.setVisibility(View.VISIBLE);
					textVomitoFecIni.setVisibility(View.VISIBLE);
					buttonVomitoFecIni.setVisibility(View.VISIBLE);
					inputVomitoFecFin.setVisibility(View.VISIBLE);
					textVomitoFecFin.setVisibility(View.VISIBLE);
					buttonVomitoFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinVomito.setBackgroundColor(Color.WHITE);
					inputVomitoFecIni.setVisibility(View.GONE);
					textVomitoFecIni.setVisibility(View.GONE);
					buttonVomitoFecIni.setVisibility(View.GONE);
					inputVomitoFecFin.setVisibility(View.GONE);
					textVomitoFecFin.setVisibility(View.GONE);
					buttonVomitoFecFin.setVisibility(View.GONE);
					vomitoFecIni = null;
					vomitoFecFin = null;
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
					inputFatigaFecIni.setVisibility(View.VISIBLE);
					textFatigaFecIni.setVisibility(View.VISIBLE);
					buttonFatigaFecIni.setVisibility(View.VISIBLE);
					inputFatigaFecFin.setVisibility(View.VISIBLE);
					textFatigaFecFin.setVisibility(View.VISIBLE);
					buttonFatigaFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinFatiga.setBackgroundColor(Color.WHITE);
					inputFatigaFecIni.setVisibility(View.GONE);
					textFatigaFecIni.setVisibility(View.GONE);
					buttonFatigaFecIni.setVisibility(View.GONE);
					inputFatigaFecFin.setVisibility(View.GONE);
					textFatigaFecFin.setVisibility(View.GONE);
					buttonFatigaFecFin.setVisibility(View.GONE);
					fatigaFecIni = null;
					fatigaFecFin = null;
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
					inputRespiracionRuidosaFecIni.setVisibility(View.VISIBLE);
					textRespiracionRuidosaFecIni.setVisibility(View.VISIBLE);
					buttonRespiracionRuidosaFecIni.setVisibility(View.VISIBLE);
					inputRespiracionRuidosaFecFin.setVisibility(View.VISIBLE);
					textRespiracionRuidosaFecFin.setVisibility(View.VISIBLE);
					buttonRespiracionRuidosaFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinRespiracionRuidosa.setBackgroundColor(Color.WHITE);
					inputRespiracionRuidosaFecIni.setVisibility(View.GONE);
					textRespiracionRuidosaFecIni.setVisibility(View.GONE);
					buttonRespiracionRuidosaFecIni.setVisibility(View.GONE);
					inputRespiracionRuidosaFecFin.setVisibility(View.GONE);
					textRespiracionRuidosaFecFin.setVisibility(View.GONE);
					buttonRespiracionRuidosaFecFin.setVisibility(View.GONE);
					respiracionRuidosaFecIni = null;
					respiracionRuidosaFecFin = null;
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
					inputRespiracionRapidaFecIni.setVisibility(View.VISIBLE);
					textRespiracionRapidaFecIni.setVisibility(View.VISIBLE);
					buttonRespiracionRapidaFecIni.setVisibility(View.VISIBLE);
					inputRespiracionRapidaFecFin.setVisibility(View.VISIBLE);
					textRespiracionRapidaFecFin.setVisibility(View.VISIBLE);
					buttonRespiracionRapidaFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinRespiracionRapida.setBackgroundColor(Color.WHITE);
					inputRespiracionRapidaFecIni.setVisibility(View.GONE);
					textRespiracionRapidaFecIni.setVisibility(View.GONE);
					buttonRespiracionRapidaFecIni.setVisibility(View.GONE);
					inputRespiracionRapidaFecFin.setVisibility(View.GONE);
					textRespiracionRapidaFecFin.setVisibility(View.GONE);
					buttonRespiracionRapidaFecFin.setVisibility(View.GONE);
					respiracionRapidaFecIni = null;
					respiracionRapidaFecFin = null;
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
					inputPerdidaOlfatoFecIni.setVisibility(View.VISIBLE);
					textPerdidaOlfatoFecIni.setVisibility(View.VISIBLE);
					buttonPerdidaOlfatoFecIni.setVisibility(View.VISIBLE);
					inputPerdidaOlfatoFecFin.setVisibility(View.VISIBLE);
					textPerdidaOlfatoFecFin.setVisibility(View.VISIBLE);
					buttonPerdidaOlfatoFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinPerdidaOlfato.setBackgroundColor(Color.WHITE);
					inputPerdidaOlfatoFecIni.setVisibility(View.GONE);
					textPerdidaOlfatoFecIni.setVisibility(View.GONE);
					buttonPerdidaOlfatoFecIni.setVisibility(View.GONE);
					inputPerdidaOlfatoFecFin.setVisibility(View.GONE);
					textPerdidaOlfatoFecFin.setVisibility(View.GONE);
					buttonPerdidaOlfatoFecFin.setVisibility(View.GONE);
					perdidaOlfatoFecIni = null;
					perdidaOlfatoFecFin = null;
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
					inputPerdidaGustoFecIni.setVisibility(View.VISIBLE);
					textPerdidaGustoFecIni.setVisibility(View.VISIBLE);
					buttonPerdidaGustoFecIni.setVisibility(View.VISIBLE);
					inputPerdidaGustoFecFin.setVisibility(View.VISIBLE);
					textPerdidaGustoFecFin.setVisibility(View.VISIBLE);
					buttonPerdidaGustoFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinPerdidaGusto.setBackgroundColor(Color.WHITE);
					inputPerdidaGustoFecIni.setVisibility(View.GONE);
					textPerdidaGustoFecIni.setVisibility(View.GONE);
					buttonPerdidaGustoFecIni.setVisibility(View.GONE);
					inputPerdidaGustoFecFin.setVisibility(View.GONE);
					textPerdidaGustoFecFin.setVisibility(View.GONE);
					buttonPerdidaGustoFecFin.setVisibility(View.GONE);
					perdidaGustoFecIni = null;
					perdidaGustoFecFin = null;
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
					inputDesmayosFecIni.setVisibility(View.VISIBLE);
					textDesmayosFecIni.setVisibility(View.VISIBLE);
					buttonDesmayosFecIni.setVisibility(View.VISIBLE);
					inputDesmayosFecFin.setVisibility(View.VISIBLE);
					textDesmayosFecFin.setVisibility(View.VISIBLE);
					buttonDesmayosFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinDesmayos.setBackgroundColor(Color.WHITE);
					inputDesmayosFecIni.setVisibility(View.GONE);
					textDesmayosFecIni.setVisibility(View.GONE);
					buttonDesmayosFecIni.setVisibility(View.GONE);
					inputDesmayosFecFin.setVisibility(View.GONE);
					textDesmayosFecFin.setVisibility(View.GONE);
					buttonDesmayosFecFin.setVisibility(View.GONE);
					desmayosFecIni = null;
					desmayosFecFin = null;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinSensacionPechoApretado = (Spinner) rootView.findViewById(R.id.spinSensacionPechoApretado);
		spinSensacionPechoApretado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				sensacionPechoApretado = mr.getCatKey();
				if(sensacionPechoApretado.equals(Constants.YESKEYSND)){
					spinSensacionPechoApretado.setBackgroundColor(Color.RED);
					inputSensacionPechoApretadoFecIni.setVisibility(View.VISIBLE);
					textSensacionPechoApretadoFecIni.setVisibility(View.VISIBLE);
					buttonSensacionPechoApretadoFecIni.setVisibility(View.VISIBLE);
					inputSensacionPechoApretadoFecFin.setVisibility(View.VISIBLE);
					textSensacionPechoApretadoFecFin.setVisibility(View.VISIBLE);
					buttonSensacionPechoApretadoFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinSensacionPechoApretado.setBackgroundColor(Color.WHITE);
					inputSensacionPechoApretadoFecIni.setVisibility(View.GONE);
					textSensacionPechoApretadoFecIni.setVisibility(View.GONE);
					buttonSensacionPechoApretadoFecIni.setVisibility(View.GONE);
					inputSensacionPechoApretadoFecFin.setVisibility(View.GONE);
					textSensacionPechoApretadoFecFin.setVisibility(View.GONE);
					buttonSensacionPechoApretadoFecFin.setVisibility(View.GONE);
					sensacionPechoApretadoFecIni = null;
					sensacionPechoApretadoFecFin = null;
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
					inputDolorPechoFecIni.setVisibility(View.VISIBLE);
					textDolorPechoFecIni.setVisibility(View.VISIBLE);
					buttonDolorPechoFecIni.setVisibility(View.VISIBLE);
					inputDolorPechoFecFin.setVisibility(View.VISIBLE);
					textDolorPechoFecFin.setVisibility(View.VISIBLE);
					buttonDolorPechoFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinDolorPecho.setBackgroundColor(Color.WHITE);
					inputDolorPechoFecIni.setVisibility(View.GONE);
					textDolorPechoFecIni.setVisibility(View.GONE);
					buttonDolorPechoFecIni.setVisibility(View.GONE);
					inputDolorPechoFecFin.setVisibility(View.GONE);
					textDolorPechoFecFin.setVisibility(View.GONE);
					buttonDolorPechoFecFin.setVisibility(View.GONE);
					dolorPechoFecIni = null;
					dolorPechoFecFin = null;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinSensacionFaltaAire = (Spinner) rootView.findViewById(R.id.spinSensacionFaltaAire);
		spinSensacionFaltaAire.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				sensacionFaltaAire = mr.getCatKey();
				if(sensacionFaltaAire.equals(Constants.YESKEYSND)){
					spinSensacionFaltaAire.setBackgroundColor(Color.RED);
					inputSensacionFaltaAireFecIni.setVisibility(View.VISIBLE);
					textSensacionFaltaAireFecIni.setVisibility(View.VISIBLE);
					buttonSensacionFaltaAireFecIni.setVisibility(View.VISIBLE);
					inputSensacionFaltaAireFecFin.setVisibility(View.VISIBLE);
					textSensacionFaltaAireFecFin.setVisibility(View.VISIBLE);
					buttonSensacionFaltaAireFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinSensacionFaltaAire.setBackgroundColor(Color.WHITE);
					inputSensacionFaltaAireFecIni.setVisibility(View.GONE);
					textSensacionFaltaAireFecIni.setVisibility(View.GONE);
					buttonSensacionFaltaAireFecIni.setVisibility(View.GONE);
					inputSensacionFaltaAireFecFin.setVisibility(View.GONE);
					textSensacionFaltaAireFecFin.setVisibility(View.GONE);
					buttonSensacionFaltaAireFecFin.setVisibility(View.GONE);
					sensacionFaltaAireFecIni = null;
					sensacionFaltaAireFecFin = null;
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
					inputQuedoCamaFecIni.setVisibility(View.VISIBLE);
					textQuedoCamaFecIni.setVisibility(View.VISIBLE);
					buttonQuedoCamaFecIni.setVisibility(View.VISIBLE);
					inputQuedoCamaFecFin.setVisibility(View.VISIBLE);
					textQuedoCamaFecFin.setVisibility(View.VISIBLE);
					buttonQuedoCamaFecFin.setVisibility(View.VISIBLE);
				}
				else{
					spinQuedoCama.setBackgroundColor(Color.WHITE);
					inputQuedoCamaFecIni.setVisibility(View.GONE);
					textQuedoCamaFecIni.setVisibility(View.GONE);
					buttonQuedoCamaFecIni.setVisibility(View.GONE);
					inputQuedoCamaFecFin.setVisibility(View.GONE);
					textQuedoCamaFecFin.setVisibility(View.GONE);
					buttonQuedoCamaFecFin.setVisibility(View.GONE);
					quedoCamaFecIni = null;
					quedoCamaFecFin = null;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
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
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		switch(dialog) {
			case DATE_I1:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						fiebreFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputFiebreFecIni.setText(fiebreFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I2:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						tosFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputTosFecIni.setText(tosFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I3:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dolorCabezaFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDolorCabezaFecIni.setText(dolorCabezaFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I4:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dolorArticularFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDolorArticularFecIni.setText(dolorArticularFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I5:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dolorMuscularFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDolorMuscularFecIni.setText(dolorMuscularFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I6:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dificultadRespiratoriaFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDificultadRespiratoriaFecIni.setText(dificultadRespiratoriaFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I7:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						pocoApetitoFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputPocoApetitoFecIni.setText(pocoApetitoFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I8:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dolorGargantaFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDolorGargantaFecIni.setText(dolorGargantaFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I9:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						secrecionNasalFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputSecrecionNasalFecIni.setText(secrecionNasalFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I10:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						picorGargantaFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputPicorGargantaFecIni.setText(picorGargantaFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I11:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						expectoracionFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputExpectoracionFecIni.setText(expectoracionFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I12:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						rashFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputRashFecIni.setText(rashFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I13:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						urticariaFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputUrticariaFecIni.setText(urticariaFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I14:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						conjuntivitisFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputConjuntivitisFecIni.setText(conjuntivitisFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I15:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						diarreaFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDiarreaFecIni.setText(diarreaFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I16:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						vomitoFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputVomitoFecIni.setText(vomitoFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I17:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
							quedoCamaFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputQuedoCamaFecIni.setText(quedoCamaFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I18:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						respiracionRuidosaFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputRespiracionRuidosaFecIni.setText(respiracionRuidosaFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I19:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						respiracionRapidaFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputRespiracionRapidaFecIni.setText(respiracionRapidaFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I20:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						perdidaOlfatoFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputPerdidaOlfatoFecIni.setText(perdidaOlfatoFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I21:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						congestionNasalFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputCongestionNasalFecIni.setText(congestionNasalFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I22:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						perdidaGustoFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputPerdidaGustoFecIni.setText(perdidaGustoFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I23:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						desmayosFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDesmayosFecIni.setText(desmayosFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I24:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						sensacionPechoApretadoFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputSensacionPechoApretadoFecIni.setText(sensacionPechoApretadoFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I25:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dolorPechoFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDolorPechoFecIni.setText(dolorPechoFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I26:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						sensacionFaltaAireFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputSensacionFaltaAireFecIni.setText(sensacionFaltaAireFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_I27:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						fatigaFecIni = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputFatigaFecIni.setText(fatigaFecIni);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F1:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						fiebreFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputFiebreFecFin.setText(fiebreFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (fiebreFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(fiebreFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F2:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						tosFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputTosFecFin.setText(tosFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (tosFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(tosFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F3:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dolorCabezaFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDolorCabezaFecFin.setText(dolorCabezaFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (dolorCabezaFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(dolorCabezaFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F4:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dolorArticularFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDolorArticularFecFin.setText(dolorArticularFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (dolorArticularFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(dolorArticularFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F5:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dolorMuscularFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDolorMuscularFecFin.setText(dolorMuscularFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (dolorMuscularFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(dolorMuscularFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F6:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dificultadRespiratoriaFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDificultadRespiratoriaFecFin.setText(dificultadRespiratoriaFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (dificultadRespiratoriaFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(dificultadRespiratoriaFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F7:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						pocoApetitoFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputPocoApetitoFecFin.setText(pocoApetitoFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (pocoApetitoFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(pocoApetitoFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F8:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dolorGargantaFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDolorGargantaFecFin.setText(dolorGargantaFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (dolorGargantaFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(dolorGargantaFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F9:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						secrecionNasalFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputSecrecionNasalFecFin.setText(secrecionNasalFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (secrecionNasalFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(secrecionNasalFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F10:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						picorGargantaFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputPicorGargantaFecFin.setText(picorGargantaFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (picorGargantaFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(picorGargantaFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F11:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						expectoracionFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputExpectoracionFecFin.setText(expectoracionFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (expectoracionFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(expectoracionFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F12:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						rashFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputRashFecFin.setText(rashFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (rashFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(rashFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F13:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						urticariaFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputUrticariaFecFin.setText(urticariaFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (urticariaFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(urticariaFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F14:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						conjuntivitisFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputConjuntivitisFecFin.setText(conjuntivitisFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (conjuntivitisFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(conjuntivitisFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F15:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						diarreaFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDiarreaFecFin.setText(diarreaFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (diarreaFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(diarreaFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F16:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						vomitoFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputVomitoFecFin.setText(vomitoFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (vomitoFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(vomitoFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F17:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						quedoCamaFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputQuedoCamaFecFin.setText(quedoCamaFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (quedoCamaFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(quedoCamaFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F18:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						respiracionRuidosaFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputRespiracionRuidosaFecFin.setText(respiracionRuidosaFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (respiracionRuidosaFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(respiracionRuidosaFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F19:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						respiracionRapidaFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputRespiracionRapidaFecFin.setText(respiracionRapidaFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (respiracionRapidaFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(respiracionRapidaFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F20:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						perdidaOlfatoFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputPerdidaOlfatoFecFin.setText(perdidaOlfatoFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (perdidaOlfatoFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(perdidaOlfatoFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F21:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						congestionNasalFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputCongestionNasalFecFin.setText(congestionNasalFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (congestionNasalFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(congestionNasalFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F22:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						perdidaGustoFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputPerdidaGustoFecFin.setText(perdidaGustoFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (perdidaGustoFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(perdidaGustoFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F23:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						desmayosFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDesmayosFecFin.setText(desmayosFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (desmayosFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(desmayosFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F24:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						sensacionPechoApretadoFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputSensacionPechoApretadoFecFin.setText(sensacionPechoApretadoFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (sensacionPechoApretadoFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(sensacionPechoApretadoFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F25:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						dolorPechoFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputDolorPechoFecFin.setText(dolorPechoFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (dolorPechoFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(dolorPechoFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F26:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						sensacionFaltaAireFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputSensacionFaltaAireFecFin.setText(sensacionFaltaAireFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (sensacionFaltaAireFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(sensacionFaltaAireFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_F27:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						fatigaFecFin = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputFatigaFecFin.setText(fatigaFecFin);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (fatigaFecIni!=null){
					Date inicio = null;
					try {
						inicio = formatter.parse(fatigaFecIni);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					minDate = new DateMidnight(inicio);
				}else if (mVisitaFinal.getCodigoParticipanteCaso().getFis() != null) {
					Date fechaInicio = mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mVisitaFinal.getCodigoParticipanteCaso().getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mVisitaFinal.getCodigoParticipanteCaso().getCodigoCaso().getFechaIngreso());
				}
				maxDate = new DateMidnight(mVisitaFinal.getFechaVisita());
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			default:
				break;
		}
	}
    
    private boolean validarEntrada() throws ParseException {
        //Valida la entrada
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if (fiebre == null || fiebre.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fiebre)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (tos == null || tos.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tos)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (dolorCabeza == null || dolorCabeza.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorCabeza)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (dolorArticular == null || dolorArticular.equals("")) {
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect, getActivity().getString(R.string.dolorArticular)), Toast.LENGTH_LONG).show();
			return false;
		}else if (dolorMuscular == null || dolorMuscular.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorMuscular)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (dificultadRespiratoria == null || dificultadRespiratoria.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dificultadRespiratoria)),Toast.LENGTH_LONG).show();
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
		else if (secrecionNasal == null || secrecionNasal.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.secrecionNasal)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (picorGarganta == null || picorGarganta.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.picorGarganta)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (expectoracion == null || expectoracion.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.expectoracion)),Toast.LENGTH_LONG).show();
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
		else if (diarrea == null || diarrea.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.diarrea)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (vomito == null || vomito.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.vomito)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (quedoCama == null || quedoCama.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.quedoCama)),Toast.LENGTH_LONG).show();
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
		else if (congestionNasal == null || congestionNasal.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.congestionNasal)),Toast.LENGTH_LONG).show();
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
		else if (fatiga == null || fatiga.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fatiga)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (fiebre.equals(Constants.YESKEYSND) && (fiebreFecIni == null || fiebreFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fiebreFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (tos.equals(Constants.YESKEYSND) && (tosFecIni == null || tosFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tosFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (dolorCabeza.equals(Constants.YESKEYSND) && (dolorCabezaFecIni == null || dolorCabezaFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorCabezaFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (dolorArticular.equals(Constants.YESKEYSND) && (dolorArticularFecIni == null || dolorArticularFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect, getActivity().getString(R.string.dolorArticularFecIni)), Toast.LENGTH_LONG).show();
			return false;
		}else if (dolorMuscular.equals(Constants.YESKEYSND) && (dolorMuscularFecIni == null || dolorMuscularFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorMuscularFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (dificultadRespiratoria.equals(Constants.YESKEYSND) && (dificultadRespiratoriaFecIni == null || dificultadRespiratoriaFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dificultadRespiratoriaFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (pocoApetito.equals(Constants.YESKEYSND) && (pocoApetitoFecIni == null || pocoApetitoFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.pocoApetitoFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (dolorGarganta.equals(Constants.YESKEYSND) && (dolorGargantaFecIni == null || dolorGargantaFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorGargantaFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (secrecionNasal.equals(Constants.YESKEYSND) && (secrecionNasalFecIni == null || secrecionNasalFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.secrecionNasalFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (picorGarganta.equals(Constants.YESKEYSND) && (picorGargantaFecIni == null || picorGargantaFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.picorGargantaFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (expectoracion.equals(Constants.YESKEYSND) && (expectoracionFecIni == null || expectoracionFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.expectoracionFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (rash.equals(Constants.YESKEYSND) && (rashFecIni == null || rashFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.rashFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (urticaria.equals(Constants.YESKEYSND) && (urticariaFecIni == null || urticariaFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.urticariaFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (conjuntivitis.equals(Constants.YESKEYSND) && (conjuntivitisFecIni == null || conjuntivitisFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.conjuntivitisFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (diarrea.equals(Constants.YESKEYSND) && (diarreaFecIni == null || diarreaFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.diarreaFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (vomito.equals(Constants.YESKEYSND) && (vomitoFecIni == null || vomitoFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.vomitoFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (quedoCama.equals(Constants.YESKEYSND) && (quedoCamaFecIni == null || quedoCamaFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.quedoCamaFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (respiracionRuidosa.equals(Constants.YESKEYSND) && (respiracionRuidosaFecIni == null || respiracionRuidosaFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.respiracionRuidosaFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (respiracionRapida.equals(Constants.YESKEYSND) && (respiracionRapidaFecIni == null || respiracionRapidaFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.respiracionRapidaFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (perdidaOlfato.equals(Constants.YESKEYSND) && (perdidaOlfatoFecIni == null || perdidaOlfatoFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.perdidaOlfatoFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (congestionNasal.equals(Constants.YESKEYSND) && (congestionNasalFecIni == null || congestionNasalFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.congestionNasalFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (perdidaGusto.equals(Constants.YESKEYSND) && (perdidaGustoFecIni == null || perdidaGustoFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.perdidaGustoFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (desmayos.equals(Constants.YESKEYSND) && (desmayosFecIni == null || desmayosFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.desmayosFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (sensacionPechoApretado.equals(Constants.YESKEYSND) && (sensacionPechoApretadoFecIni == null || sensacionPechoApretadoFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.sensacionPechoApretadoFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dolorPecho.equals(Constants.YESKEYSND) && (dolorPechoFecIni == null || dolorPechoFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorPechoFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (sensacionFaltaAire.equals(Constants.YESKEYSND) && (sensacionFaltaAireFecIni == null || sensacionFaltaAireFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.sensacionFaltaAireFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (fatiga.equals(Constants.YESKEYSND) && (fatigaFecIni == null || fatigaFecIni.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fatigaFecIni)),Toast.LENGTH_LONG).show();
			return false;
		}		else if (fiebre.equals(Constants.YESKEYSND) && (fiebreFecFin == null || fiebreFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fiebreFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (tos.equals(Constants.YESKEYSND) && (tosFecFin == null || tosFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tosFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dolorCabeza.equals(Constants.YESKEYSND) && (dolorCabezaFecFin == null || dolorCabezaFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorCabezaFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dolorArticular.equals(Constants.YESKEYSND) && (dolorArticularFecFin == null || dolorArticularFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect, getActivity().getString(R.string.dolorArticularFecFin)), Toast.LENGTH_LONG).show();
			return false;
		}else if (dolorMuscular.equals(Constants.YESKEYSND) && (dolorMuscularFecFin == null || dolorMuscularFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorMuscularFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dificultadRespiratoria.equals(Constants.YESKEYSND) && (dificultadRespiratoriaFecFin == null || dificultadRespiratoriaFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dificultadRespiratoriaFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (pocoApetito.equals(Constants.YESKEYSND) && (pocoApetitoFecFin == null || pocoApetitoFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.pocoApetitoFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dolorGarganta.equals(Constants.YESKEYSND) && (dolorGargantaFecFin == null || dolorGargantaFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorGargantaFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (secrecionNasal.equals(Constants.YESKEYSND) && (secrecionNasalFecFin == null || secrecionNasalFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.secrecionNasalFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (picorGarganta.equals(Constants.YESKEYSND) && (picorGargantaFecFin == null || picorGargantaFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.picorGargantaFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (expectoracion.equals(Constants.YESKEYSND) && (expectoracionFecFin == null || expectoracionFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.expectoracionFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (rash.equals(Constants.YESKEYSND) && (rashFecFin == null || rashFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.rashFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (urticaria.equals(Constants.YESKEYSND) && (urticariaFecFin == null || urticariaFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.urticariaFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (conjuntivitis.equals(Constants.YESKEYSND) && (conjuntivitisFecFin == null || conjuntivitisFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.conjuntivitisFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (diarrea.equals(Constants.YESKEYSND) && (diarreaFecFin == null || diarreaFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.diarreaFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (vomito.equals(Constants.YESKEYSND) && (vomitoFecFin == null || vomitoFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.vomitoFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (quedoCama.equals(Constants.YESKEYSND) && (quedoCamaFecFin == null || quedoCamaFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.quedoCamaFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (respiracionRuidosa.equals(Constants.YESKEYSND) && (respiracionRuidosaFecFin == null || respiracionRuidosaFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.respiracionRuidosaFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (respiracionRapida.equals(Constants.YESKEYSND) && (respiracionRapidaFecFin == null || respiracionRapidaFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.respiracionRapidaFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (perdidaOlfato.equals(Constants.YESKEYSND) && (perdidaOlfatoFecFin == null || perdidaOlfatoFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.perdidaOlfatoFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (congestionNasal.equals(Constants.YESKEYSND) && (congestionNasalFecFin == null || congestionNasalFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.congestionNasalFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (perdidaGusto.equals(Constants.YESKEYSND) && (perdidaGustoFecFin == null || perdidaGustoFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.perdidaGustoFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (desmayos.equals(Constants.YESKEYSND) && (desmayosFecFin == null || desmayosFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.desmayosFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (sensacionPechoApretado.equals(Constants.YESKEYSND) && (sensacionPechoApretadoFecFin == null || sensacionPechoApretadoFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.sensacionPechoApretadoFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dolorPecho.equals(Constants.YESKEYSND) && (dolorPechoFecFin == null || dolorPechoFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorPechoFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (sensacionFaltaAire.equals(Constants.YESKEYSND) && (sensacionFaltaAireFecFin == null || sensacionFaltaAireFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.sensacionFaltaAireFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (fatiga.equals(Constants.YESKEYSND) && (fatigaFecFin == null || fatigaFecFin.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fatigaFecFin)),Toast.LENGTH_LONG).show();
			return false;
		}
		else{
        	mSintoma.setCodigoCasoSintoma(infoMovil.getId());
        	mSintoma.setCodigoVisitaFinal(mVisitaFinal);
        	mSintoma.setFiebre(fiebre);
			mSintoma.setTos(tos);
			mSintoma.setDolorCabeza(dolorCabeza);
			mSintoma.setDolorArticular(dolorArticular);
			mSintoma.setDolorMuscular(dolorMuscular);
			mSintoma.setDificultadRespiratoria(dificultadRespiratoria);
			mSintoma.setPocoApetito(pocoApetito);
			mSintoma.setDolorGarganta(dolorGarganta);
			mSintoma.setSecrecionNasal(secrecionNasal);
			mSintoma.setPicorGarganta(picorGarganta);
			mSintoma.setExpectoracion(expectoracion);
			mSintoma.setRash(rash);
			mSintoma.setUrticaria(urticaria);
			mSintoma.setConjuntivitis(conjuntivitis);
			mSintoma.setDiarrea(diarrea);
			mSintoma.setVomito(vomito);
			mSintoma.setQuedoCama(quedoCama);
			mSintoma.setRespiracionRuidosa(respiracionRuidosa);
			mSintoma.setRespiracionRapida(respiracionRapida);
			mSintoma.setPerdidaOlfato(perdidaOlfato);
			mSintoma.setCongestionNasal(congestionNasal);
			mSintoma.setPerdidaGusto(perdidaGusto);
			mSintoma.setDesmayos(desmayos);
			mSintoma.setSensacionPechoApretado(sensacionPechoApretado);
			mSintoma.setDolorPecho(dolorPecho);
			mSintoma.setSensacionFaltaAire(sensacionFaltaAire);
			mSintoma.setFatiga(fatiga);

			if (fiebreFecIni !=null && !fiebreFecIni.isEmpty()) mSintoma.setFiebreFecIni(formatter.parse(fiebreFecIni));
			if (tosFecIni !=null && !tosFecIni.isEmpty()) mSintoma.setTosFecIni(formatter.parse(tosFecIni));
			if (dolorCabezaFecIni !=null && !dolorCabezaFecIni.isEmpty()) mSintoma.setDolorCabezaFecIni(formatter.parse(dolorCabezaFecIni));
			if (dolorArticularFecIni !=null && !dolorArticularFecIni.isEmpty()) mSintoma.setDolorArticularFecIni(formatter.parse(dolorArticularFecIni));
			if (dolorMuscularFecIni !=null && !dolorMuscularFecIni.isEmpty()) mSintoma.setDolorMuscularFecIni(formatter.parse(dolorMuscularFecIni));
			if (dificultadRespiratoriaFecIni !=null && !dificultadRespiratoriaFecIni.isEmpty()) mSintoma.setDificultadRespiratoriaFecIni(formatter.parse(dificultadRespiratoriaFecIni));
			if (pocoApetitoFecIni !=null && !pocoApetitoFecIni.isEmpty()) mSintoma.setPocoApetitoFecIni(formatter.parse(pocoApetitoFecIni));
			if (dolorGargantaFecIni !=null && !dolorGargantaFecIni.isEmpty()) mSintoma.setDolorGargantaFecIni(formatter.parse(dolorGargantaFecIni));
			if (secrecionNasalFecIni !=null && !secrecionNasalFecIni.isEmpty()) mSintoma.setSecrecionNasalFecIni(formatter.parse(secrecionNasalFecIni));
			if (picorGargantaFecIni !=null && !picorGargantaFecIni.isEmpty()) mSintoma.setPicorGargantaFecIni(formatter.parse(picorGargantaFecIni));
			if (expectoracionFecIni !=null && !expectoracionFecIni.isEmpty()) mSintoma.setExpectoracionFecIni(formatter.parse(expectoracionFecIni));
			if (rashFecIni !=null && !rashFecIni.isEmpty()) mSintoma.setRashFecIni(formatter.parse(rashFecIni));
			if (urticariaFecIni !=null && !urticariaFecIni.isEmpty()) mSintoma.setUrticariaFecIni(formatter.parse(urticariaFecIni));
			if (conjuntivitisFecIni !=null && !conjuntivitisFecIni.isEmpty()) mSintoma.setConjuntivitisFecIni(formatter.parse(conjuntivitisFecIni));
			if (diarreaFecIni !=null && !diarreaFecIni.isEmpty()) mSintoma.setDiarreaFecIni(formatter.parse(diarreaFecIni));
			if (vomitoFecIni !=null && !vomitoFecIni.isEmpty()) mSintoma.setVomitoFecIni(formatter.parse(vomitoFecIni));
			if (quedoCamaFecIni !=null && !quedoCamaFecIni.isEmpty()) mSintoma.setQuedoCamaFecIni(formatter.parse(quedoCamaFecIni));
			if (respiracionRuidosaFecIni !=null && !respiracionRuidosaFecIni.isEmpty()) mSintoma.setRespiracionRuidosaFecIni(formatter.parse(respiracionRuidosaFecIni));
			if (respiracionRapidaFecIni !=null && !respiracionRapidaFecIni.isEmpty()) mSintoma.setRespiracionRapidaFecIni(formatter.parse(respiracionRapidaFecIni));
			if (perdidaOlfatoFecIni !=null && !perdidaOlfatoFecIni.isEmpty()) mSintoma.setPerdidaOlfatoFecIni(formatter.parse(perdidaOlfatoFecIni));
			if (congestionNasalFecIni !=null && !congestionNasalFecIni.isEmpty()) mSintoma.setCongestionNasalFecIni(formatter.parse(congestionNasalFecIni));
			if (perdidaGustoFecIni !=null && !perdidaGustoFecIni.isEmpty()) mSintoma.setPerdidaGustoFecIni(formatter.parse(perdidaGustoFecIni));
			if (desmayosFecIni !=null && !desmayosFecIni.isEmpty()) mSintoma.setDesmayosFecIni(formatter.parse(desmayosFecIni));
			if (sensacionPechoApretadoFecIni !=null && !sensacionPechoApretadoFecIni.isEmpty()) mSintoma.setSensacionPechoApretadoFecIni(formatter.parse(sensacionPechoApretadoFecIni));
			if (dolorPechoFecIni !=null && !dolorPechoFecIni.isEmpty()) mSintoma.setDolorPechoFecIni(formatter.parse(dolorPechoFecIni));
			if (sensacionFaltaAireFecIni !=null && !sensacionFaltaAireFecIni.isEmpty()) mSintoma.setSensacionFaltaAireFecIni(formatter.parse(sensacionFaltaAireFecIni));
			if (fatigaFecIni !=null && !fatigaFecIni.isEmpty()) mSintoma.setFatigaFecIni(formatter.parse(fatigaFecIni));

			if (fiebreFecFin !=null && !fiebreFecFin.isEmpty()) mSintoma.setFiebreFecFin(formatter.parse(fiebreFecFin));
			if (tosFecFin !=null && !tosFecFin.isEmpty()) mSintoma.setTosFecFin(formatter.parse(tosFecFin));
			if (dolorCabezaFecFin !=null && !dolorCabezaFecFin.isEmpty()) mSintoma.setDolorCabezaFecFin(formatter.parse(dolorCabezaFecFin));
			if (dolorArticularFecFin !=null && !dolorArticularFecFin.isEmpty()) mSintoma.setDolorArticularFecFin(formatter.parse(dolorArticularFecFin));
			if (dolorMuscularFecFin !=null && !dolorMuscularFecFin.isEmpty()) mSintoma.setDolorMuscularFecFin(formatter.parse(dolorMuscularFecFin));
			if (dificultadRespiratoriaFecFin !=null && !dificultadRespiratoriaFecFin.isEmpty()) mSintoma.setDificultadRespiratoriaFecFin(formatter.parse(dificultadRespiratoriaFecFin));
			if (pocoApetitoFecFin !=null && !pocoApetitoFecFin.isEmpty()) mSintoma.setPocoApetitoFecFin(formatter.parse(pocoApetitoFecFin));
			if (dolorGargantaFecFin !=null && !dolorGargantaFecFin.isEmpty()) mSintoma.setDolorGargantaFecFin(formatter.parse(dolorGargantaFecFin));
			if (secrecionNasalFecFin !=null && !secrecionNasalFecFin.isEmpty()) mSintoma.setSecrecionNasalFecFin(formatter.parse(secrecionNasalFecFin));
			if (picorGargantaFecFin !=null && !picorGargantaFecFin.isEmpty()) mSintoma.setPicorGargantaFecFin(formatter.parse(picorGargantaFecFin));
			if (expectoracionFecFin !=null && !expectoracionFecFin.isEmpty()) mSintoma.setExpectoracionFecFin(formatter.parse(expectoracionFecFin));
			if (rashFecFin !=null && !rashFecFin.isEmpty()) mSintoma.setRashFecFin(formatter.parse(rashFecFin));
			if (urticariaFecFin !=null && !urticariaFecFin.isEmpty()) mSintoma.setUrticariaFecFin(formatter.parse(urticariaFecFin));
			if (conjuntivitisFecFin !=null && !conjuntivitisFecFin.isEmpty()) mSintoma.setConjuntivitisFecFin(formatter.parse(conjuntivitisFecFin));
			if (diarreaFecFin !=null && !diarreaFecFin.isEmpty()) mSintoma.setDiarreaFecFin(formatter.parse(diarreaFecFin));
			if (vomitoFecFin !=null && !vomitoFecFin.isEmpty()) mSintoma.setVomitoFecFin(formatter.parse(vomitoFecFin));
			if (quedoCamaFecFin !=null && !quedoCamaFecFin.isEmpty()) mSintoma.setQuedoCamaFecFin(formatter.parse(quedoCamaFecFin));
			if (respiracionRuidosaFecFin !=null && !respiracionRuidosaFecFin.isEmpty()) mSintoma.setRespiracionRuidosaFecFin(formatter.parse(respiracionRuidosaFecFin));
			if (respiracionRapidaFecFin !=null && !respiracionRapidaFecFin.isEmpty()) mSintoma.setRespiracionRapidaFecFin(formatter.parse(respiracionRapidaFecFin));
			if (perdidaOlfatoFecFin !=null && !perdidaOlfatoFecFin.isEmpty()) mSintoma.setPerdidaOlfatoFecFin(formatter.parse(perdidaOlfatoFecFin));
			if (congestionNasalFecFin !=null && !congestionNasalFecFin.isEmpty()) mSintoma.setCongestionNasalFecFin(formatter.parse(congestionNasalFecFin));
			if (perdidaGustoFecFin !=null && !perdidaGustoFecFin.isEmpty()) mSintoma.setPerdidaGustoFecFin(formatter.parse(perdidaGustoFecFin));
			if (desmayosFecFin !=null && !desmayosFecFin.isEmpty()) mSintoma.setDesmayosFecFin(formatter.parse(desmayosFecFin));
			if (sensacionPechoApretadoFecFin !=null && !sensacionPechoApretadoFecFin.isEmpty()) mSintoma.setSensacionPechoApretadoFecFin(formatter.parse(sensacionPechoApretadoFecFin));
			if (dolorPechoFecFin !=null && !dolorPechoFecFin.isEmpty()) mSintoma.setDolorPechoFecFin(formatter.parse(dolorPechoFecFin));
			if (sensacionFaltaAireFecFin !=null && !sensacionFaltaAireFecFin.isEmpty()) mSintoma.setSensacionFaltaAireFecFin(formatter.parse(sensacionFaltaAireFecFin));
			if (fatigaFecFin !=null && !fatigaFecFin.isEmpty()) mSintoma.setFatigaFecFin(formatter.parse(fatigaFecFin));

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
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}finally {
				estudiosAdapter.close();
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

			spinFiebre.setAdapter(dataAdapter);
			spinDolorCabeza.setAdapter(dataAdapter);
			spinDolorArticular.setAdapter(dataAdapter);
			spinDolorMuscular.setAdapter(dataAdapter);
			spinDificultadRespiratoria.setAdapter(dataAdapter);
			
			spinSecrecionNasal.setAdapter(dataAdapter);
			spinTos.setAdapter(dataAdapter);
			spinPocoApetito.setAdapter(dataAdapter);
			spinDolorGarganta.setAdapter(dataAdapter);
			spinPicorGarganta.setAdapter(dataAdapter);
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
			spinSensacionPechoApretado.setAdapter(dataAdapter);
			spinDolorPecho.setAdapter(dataAdapter);
			spinSensacionFaltaAire.setAdapter(dataAdapter);
			spinQuedoCama.setAdapter(dataAdapter);

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
				estudiosAdapter.crearSintomasVisitaFinalCovid19(mSintoma);
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}finally {
				estudiosAdapter.close();
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
				arguments.putSerializable(Constants.VISITA_FINAL , mVisitaFinal);
				i = new Intent(getActivity(),
						MenuVisitaFinalCasoCovid19Activity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        i.putExtras(arguments);
				startActivity(i);
				getActivity().finish();
			}
		}

	}	
    
}
