package ni.org.ics.estudios.appmovil.influenzauo1.activities.fragments;


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
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.SintomasVisitaCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaCasoUO1;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.list.ListaSintomasVisitaCasoUO1Activity;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import org.joda.time.DateMidnight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NuevoSintomaVisitaCasoUO1Fragment extends Fragment {

	private static final String TAG = "NuevaVisitaSeguimientoFragment";
	protected static final int DATE_SINTOMA = 101;

	private EstudiosAdapter estudiosAdapter;
	//Viene de la llamada
	private VisitaCasoUO1 mVisitaCasoUO1;
	//Objeto que se va a hacer
	private SintomasVisitaCasoUO1 vscs = new SintomasVisitaCasoUO1();
	//Catalogos
	private List<MessageResource> mCatalogoSnd;
	private List<MessageResource> mCatalogoIntensidad;
	private List<String> mCatalogoDias = new ArrayList<>();

	private List<VisitaCasoUO1> mVisitasCasoUO1 = new ArrayList<VisitaCasoUO1>();
	private List<SintomasVisitaCasoUO1> mSintomasVisitaCasoUO1 = new ArrayList<SintomasVisitaCasoUO1>();
	//Widgets en el View
	private TextView mTitleView;
	private EditText mNameView;
	private EditText inputFechaSintoma;
	private ImageButton mButtonChangeFechaSintoma;
	private Spinner spinDiaSintoma;
	private Spinner spinConsultaInicial;
	private Spinner spinFiebre;
	private Spinner spinFiebreIntensidad;
	private Spinner spinTos;
	private Spinner spinTosIntensidad;
	private Spinner spinSecrecionNasal;
	private Spinner spinSecrecionNasalIntensidad;
	private Spinner spinDolorGarganta;
	private Spinner spinDolorGargantaIntensidad;
	private Spinner spinCongestionNasal;
	private Spinner spinDolorCabeza;
	private Spinner spinDolorCabezaIntensidad;
	private Spinner spinFaltaApetito;
	private Spinner spinDolorMuscular;
	private Spinner spinDolorMuscularIntensidad;
	private Spinner spinDolorArticular;
	private Spinner spinDolorArticularIntensidad;
	private Spinner spinDolorOido;
	private Spinner spinRespiracionRapida;
	private Spinner spinDificultadRespiratoria;
	private Spinner spinFaltaEscuela;
	private Spinner spinQuedoCama;

	private TextView textFiebreIntensidad;
	private TextView textTosIntensidad;
	private TextView textSecrecionNasalIntensidad;
	private TextView textDolorGargantaIntensidad;
	private TextView textDolorCabezaIntensidad;
	private TextView textDolorMuscularIntensidad;
	private TextView textDolorArticularIntensidad;

	private Button mSaveView;

	//Variables donde se captura la entrada de datos
	private String fechaSintoma;
	private String fiebre;
	private String dolorCabeza;
	private String dolorArticular;
	private String dolorMuscular;
	private String dificultadRespiratoria;
	private String secrecionNasal;
	private String tos;
	private String faltaApetito;
	private String dolorGarganta;
	private String congestionNasal;
	private String quedoCama;
    private String respiracionRapida;
	private String fiebreIntensidad;
	private String dolorCabezaIntensidad;
	private String dolorArticularIntensidad;
	private String dolorMuscularIntensidad;
	private String secrecionNasalIntensidad;
	private String tosIntensidad;
	private String dolorGargantaIntensidad;
	private String faltaEscuela;
	private String dolorOido;
	private String diaSintoma;
	private String consultaInicial;

	final Calendar c = Calendar.getInstance();
	//Para el id
	private DeviceInfo infoMovil;
	private String username;
	private SharedPreferences settings;


    public static NuevoSintomaVisitaCasoUO1Fragment create() {
        NuevoSintomaVisitaCasoUO1Fragment fragment = new NuevoSintomaVisitaCasoUO1Fragment();
        return fragment;
    }

    public NuevoSintomaVisitaCasoUO1Fragment() {
    	
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
		mVisitaCasoUO1 = (VisitaCasoUO1) getActivity().getIntent().getExtras().getSerializable(Constants.VISITA);
        infoMovil = new DeviceInfo(getActivity());
        settings =
				PreferenceManager.getDefaultSharedPreferences(getActivity());
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
        new FetchCatalogosTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nuevo_sintoma_uo1, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((EditText) rootView.findViewById(R.id.inputCodigoParticipanteCaso));
        mNameView.setText(mVisitaCasoUO1.getParticipanteCasoUO1().getParticipante().getNombreCompleto());
        mNameView.setEnabled(false);
        inputFechaSintoma = (EditText) rootView.findViewById(R.id.inputFechaSintoma);
        if (mVisitaCasoUO1.getFechaVisita().compareTo(new Date())<0){
            c.setTime(mVisitaCasoUO1.getFechaVisita());
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

		spinDiaSintoma = (Spinner) rootView.findViewById(R.id.spinDiaSintoma);
		spinDiaSintoma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				diaSintoma = (String) spinner.getSelectedItem();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinConsultaInicial = (Spinner) rootView.findViewById(R.id.spinConsultaInicial);
		spinConsultaInicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				consultaInicial = mr.getCatKey();
				if(consultaInicial.equals(Constants.YESKEYSND)){
					spinConsultaInicial.setBackgroundColor(Color.RED);
				}
				else{
					spinConsultaInicial.setBackgroundColor(Color.WHITE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
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
	        		spinFiebreIntensidad.setVisibility(View.VISIBLE);
					textFiebreIntensidad.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		spinFiebre.setBackgroundColor(Color.WHITE);
					spinFiebreIntensidad.setVisibility(View.GONE);
					textFiebreIntensidad.setVisibility(View.GONE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
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

		spinFaltaApetito = (Spinner) rootView.findViewById(R.id.spinFaltaApetito);
		spinFaltaApetito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				faltaApetito = mr.getCatKey();
				if(faltaApetito.equals(Constants.YESKEYSND)){
					spinFaltaApetito.setBackgroundColor(Color.RED);
				}
				else{
					spinFaltaApetito.setBackgroundColor(Color.WHITE);
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

		spinDolorOido = (Spinner) rootView.findViewById(R.id.spinDolorOido);
		spinDolorOido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				dolorOido = mr.getCatKey();
				if(dolorOido.equals(Constants.YESKEYSND)){
					spinDolorOido.setBackgroundColor(Color.RED);
				}
				else{
					spinDolorOido.setBackgroundColor(Color.WHITE);
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

		spinFaltaEscuela = (Spinner) rootView.findViewById(R.id.spinFaltaEscuela);
		spinFaltaEscuela.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				faltaEscuela = mr.getCatKey();
				if(faltaEscuela.equals(Constants.YESKEYSND)){
					spinFaltaEscuela.setBackgroundColor(Color.RED);
				}
				else{
					spinFaltaEscuela.setBackgroundColor(Color.WHITE);
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
		switch(dialog){
		case DATE_SINTOMA:
			dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
		        @Override
		        public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
		        	fechaSintoma = (picker != null) ? String.valueOf(dayOfMonth<10? "0"+dayOfMonth:dayOfMonth)+"/"+String.valueOf((monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1))+"/"+String.valueOf(year) : null;
                	inputFechaSintoma.setText(fechaSintoma);
		        }
		    },c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
			if (mVisitaCasoUO1.getParticipanteCasoUO1().getFis()!=null){
                Date fechaInicio = mVisitaCasoUO1.getParticipanteCasoUO1().getFechaIngreso();
                Date fechaEnfermedad = mVisitaCasoUO1.getParticipanteCasoUO1().getFis();
                //si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
                if (fechaInicio.compareTo(fechaEnfermedad)>=0)
                    minDate = new DateMidnight(fechaEnfermedad);
                else //sino tomar la fecha de inicio
                    minDate = new DateMidnight(fechaInicio);
            } else if (mVisitaCasoUO1.getParticipanteCasoUO1().getFif()!=null){
				Date fechaInicio = mVisitaCasoUO1.getParticipanteCasoUO1().getFechaIngreso();
				Date fechaEnfermedad = mVisitaCasoUO1.getParticipanteCasoUO1().getFif();
				//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
				if (fechaInicio.compareTo(fechaEnfermedad)>=0)
					minDate = new DateMidnight(fechaEnfermedad);
				else //sino tomar la fecha de inicio
					minDate = new DateMidnight(fechaInicio);
			} else {
                minDate = new DateMidnight(mVisitaCasoUO1.getParticipanteCasoUO1().getFechaIngreso());
            }
			maxDate = new DateMidnight(mVisitaCasoUO1.getFechaVisita());
			dpD.getDatePicker().setMinDate(minDate.getMillis());
			dpD.getDatePicker().setMaxDate(maxDate.getMillis());
			dpD.show();
			break;
		default:
			break;
		}
	}

	private boolean diaValido(String fechaSintoma, String diaSintomaUO1) {
		Date dfs = null;
		DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			dfs = mDateFormat.parse(fechaSintoma);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<SintomasVisitaCasoUO1> sintomasRegistrados = estudiosAdapter.getSintomasVisitasCasosUO1(InfluenzaUO1DBConstants.codigoCasoVisita + " = '" + mVisitaCasoUO1.getCodigoCasoVisita() + "' ", InfluenzaUO1DBConstants.fechaSintomas);
		for (SintomasVisitaCasoUO1 sintoma : sintomasRegistrados) {
			if (sintoma.getDia().equalsIgnoreCase(diaSintomaUO1)) {
				Toast.makeText(getActivity(), String.format(getString(R.string.diaRegistrado), diaSintomaUO1), Toast.LENGTH_LONG).show();
				return false;
			}
			if (sintoma.getFechaSintomas() != null && sintoma.getFechaSintomas().equals(dfs)) {
				Toast.makeText(getActivity(), String.format(getString(R.string.fechaRegistrada), fechaSintoma), Toast.LENGTH_LONG).show();
				return false;
			}
		}
		return true;
	}

    private boolean validarEntrada() throws ParseException {
        //Valida la entrada
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (diaSintoma == null || diaSintoma.equals("") || diaSintoma.equals(getActivity().getString(R.string.select))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.diaSintomaUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
    	if (consultaInicial == null || consultaInicial.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.consultaInicialUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
        if (fechaSintoma == null || fechaSintoma.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fechaSintomaUO1)),Toast.LENGTH_LONG).show();
        	inputFechaSintoma.setError(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fechaSintomaUO1)));
            return false;
        }
        else if (fiebre == null || fiebre.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fiebreUO1)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (fiebre.equals(Constants.YESKEYSND) && (fiebreIntensidad == null || fiebreIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fiebreIntensidadUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (tos == null || tos.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tosUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (tos.equals(Constants.YESKEYSND) && (tosIntensidad == null || tosIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tosIntensidadUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (secrecionNasal == null || secrecionNasal.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.secrecionNasalUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (secrecionNasal.equals(Constants.YESKEYSND) && (secrecionNasalIntensidad == null || secrecionNasalIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.secrecionNasalIntensidadUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dolorGarganta == null || dolorGarganta.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorGargantaUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dolorGarganta.equals(Constants.YESKEYSND) && (dolorGargantaIntensidad == null || dolorGargantaIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorGargantaIntensidadUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (congestionNasal == null || congestionNasal.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.congestionNasalUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (dolorCabeza == null || dolorCabeza.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorCabezaUO1)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (dolorCabeza.equals(Constants.YESKEYSND) && (dolorCabezaIntensidad == null || dolorCabezaIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorCabezaIntensidadUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (faltaApetito == null || faltaApetito.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.faltaApetitoUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dolorMuscular == null || dolorMuscular.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorMuscularUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dolorMuscular.equals(Constants.YESKEYSND) && (dolorMuscularIntensidad == null || dolorMuscularIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorMuscularIntensidadUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (dolorArticular == null || dolorArticular.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorArticularUO1)),Toast.LENGTH_LONG).show();
            return false;
        }else if (dolorArticular.equals(Constants.YESKEYSND) && (dolorArticularIntensidad == null || dolorArticularIntensidad.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorArticularIntensidadUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (dolorOido == null || dolorOido.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorOidoUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (respiracionRapida == null || respiracionRapida.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.respiracionRapidaUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (dificultadRespiratoria == null || dificultadRespiratoria.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dificultadRespiratoriaUO1)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (faltaEscuela == null || faltaEscuela.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.faltaEscueltaUO1)),Toast.LENGTH_LONG).show();
			return false;
		}
        else if (quedoCama == null || quedoCama.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.quedoCamaUO1)),Toast.LENGTH_LONG).show();
            return false;
        }
        /*else if (mVisitaCasoUO1.getParticipanteCasoUO1().getFif()==null && formatter.parse(fechaSintoma).before(mVisitaCasoUO1.getParticipanteCasoUO1().getFechaIngreso())){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.fecBefRegistro,getActivity().getString(R.string.fecha_sintoma),getActivity().getString(R.string.fechaInicio)),Toast.LENGTH_LONG).show();
            return false;
        }*/else{
			if (diaValido(fechaSintoma, diaSintoma)) {
				vscs.setCodigoSintoma(infoMovil.getId());
				vscs.setCodigoCasoVisita(mVisitaCasoUO1);
				vscs.setDia(diaSintoma);
				vscs.setFechaSintomas(formatter.parse(fechaSintoma));
				vscs.setConsultaInicial(consultaInicial);
				vscs.setFiebre(fiebre);
				vscs.setFiebreIntensidad(fiebreIntensidad);
				vscs.setTos(tos);
				vscs.setTosIntensidad(tosIntensidad);
				vscs.setSecrecionNasal(secrecionNasal);
				vscs.setSecrecionNasalIntensidad(secrecionNasalIntensidad);
				vscs.setDolorGarganta(dolorGarganta);
				vscs.setDolorGargantaIntensidad(dolorGargantaIntensidad);
				vscs.setCongestionNasal(congestionNasal);
				vscs.setDolorCabeza(dolorCabeza);
				vscs.setDolorCabezaIntensidad(dolorCabezaIntensidad);
				vscs.setFaltaApetito(faltaApetito);
				vscs.setDolorMuscular(dolorMuscular);
				vscs.setDolorMuscularIntensidad(dolorMuscularIntensidad);
				vscs.setDolorArticular(dolorArticular);
				vscs.setDolorArticularIntensidad(dolorArticularIntensidad);
				vscs.setDolorOido(dolorOido);
				vscs.setRespiracionRapida(respiracionRapida);
				vscs.setDificultadRespiratoria(dificultadRespiratoria);
				vscs.setFaltaEscuelta(faltaEscuela);
				vscs.setQuedoCama(quedoCama);

				vscs.setRecordDate(new Date());
				vscs.setRecordUser(username);
				vscs.setDeviceid(infoMovil.getDeviceId());
				vscs.setEstado('0');
				vscs.setPasive('0');
				return true;
			}else
				return false;
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
				mCatalogoSnd = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='UO1_CAT_SNDNA'", CatalogosDBConstants.order);
				mCatalogoIntensidad = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CAT_INTENSIDAD_SIN'", CatalogosDBConstants.order);
				mVisitasCasoUO1 = estudiosAdapter.getVisitasCasosUO1(CasosDBConstants.codigoCasoParticipante +" = '" + mVisitaCasoUO1.getParticipanteCasoUO1().getCodigoCasoParticipante() +"'", MainDBConstants.fechaVisita);
				String filtro = "";
				for (VisitaCasoUO1 vsc: mVisitasCasoUO1){
					if (filtro.equals("")){ filtro = CasosDBConstants.codigoCasoVisita + "='" + vsc.getCodigoCasoVisita()+"'";} else{
					if (!filtro.equals("")) filtro = filtro + " or " + CasosDBConstants.codigoCasoVisita + "='" + vsc.getCodigoCasoVisita()+"'";}
				}
				mSintomasVisitaCasoUO1 = estudiosAdapter.getSintomasVisitasCasosUO1(filtro, CasosDBConstants.fechaSintomas);
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
			//permitir de 1 a 28 dias
			mCatalogoDias.add("Seleccionar");
			for(int i=1; i<=28 ; i++){
				mCatalogoDias.add(String.valueOf(i));
			}
			ArrayAdapter<String> dataAdapterDia = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoDias);
			dataAdapterDia.setDropDownViewResource(R.layout.spinner_item);
			spinDiaSintoma.setAdapter(dataAdapterDia);

			mCatalogoSnd.add(new MessageResource("",0,getActivity().getString(R.string.select)));
			Collections.sort(mCatalogoSnd);
			ArrayAdapter<MessageResource> dataAdapter = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSnd);
			dataAdapter.setDropDownViewResource(R.layout.spinner_item);
			spinConsultaInicial.setAdapter(dataAdapter);
			spinFiebre.setAdapter(dataAdapter);
			spinTos.setAdapter(dataAdapter);
			spinSecrecionNasal.setAdapter(dataAdapter);
			spinDolorGarganta.setAdapter(dataAdapter);
			spinCongestionNasal.setAdapter(dataAdapter);
			spinDolorCabeza.setAdapter(dataAdapter);
			spinFaltaApetito.setAdapter(dataAdapter);
			spinDolorMuscular.setAdapter(dataAdapter);
			spinDolorArticular.setAdapter(dataAdapter);
			spinDolorOido.setAdapter(dataAdapter);
			spinRespiracionRapida.setAdapter(dataAdapter);
			spinDificultadRespiratoria.setAdapter(dataAdapter);
			spinFaltaEscuela.setAdapter(dataAdapter);
			spinQuedoCama.setAdapter(dataAdapter);

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
				for (SintomasVisitaCasoUO1 vscc: mSintomasVisitaCasoUO1){
					if(vscc.getFechaSintomas().equals(vscs.getFechaSintomas())){
						return getActivity().getString(R.string.duplicateDate) + " - " + fechaSintoma + " / " + getActivity().getString(R.string.visit) + " - " + vscc.getCodigoCasoVisita().getVisita();
					}
				}
				estudiosAdapter.crearSintomasVisitaCasoUO1(vscs);
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
				arguments.putSerializable(Constants.VISITA , mVisitaCasoUO1);
				i = new Intent(getActivity(),
						ListaSintomasVisitaCasoUO1Activity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        i.putExtras(arguments);
				startActivity(i);
				getActivity().finish();
			}
		}

	}	
    
}
