package ni.org.ics.estudios.appmovil.cohortefamilia.activities.fragments;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaSintomasParticipantesCasosActivity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCasoSintomas;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaVisitaSeguimientoSintomasFragment extends Fragment {
	
	private static final String TAG = "NuevaVisitaSeguimientoFragment";
	protected static final int DATE_SINTOMA = 101;
	protected static final int DATE_FIF = 102;
	
	private EstudiosAdapter estudiosAdapter;
	//Viene de la llamada
	private VisitaSeguimientoCaso mVisitaSeguimientoCaso;
	//Objeto que se va a hacer
	private VisitaSeguimientoCasoSintomas vscs = new VisitaSeguimientoCasoSintomas();
	//Catalogos
	private List<MessageResource> mCatalogoSnd;
	private List<VisitaSeguimientoCaso> mVisitaSeguimientoCasos = new ArrayList<VisitaSeguimientoCaso>();
	private List<VisitaSeguimientoCasoSintomas> mVisitasSeguimientoCasoSintomas = new ArrayList<VisitaSeguimientoCasoSintomas>();
	//Widgets en el View
	private TextView mTitleView;
	private EditText mNameView;
	private EditText inputFechaSintoma;
	private ImageButton mButtonChangeFechaSintoma;
	private Spinner spinFiebre;
	private TextView textFif;
	private EditText inputFif;
	private ImageButton mButtonChangeFif;
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
	private Spinner spinDiarrea;
	private Spinner spinQuedoCama;
	private Spinner spinRespiracionRuidosa;
    private Spinner spinRespiracionRapida;
	private Spinner spinOseltamivir;
	private Spinner spinAntibiotico;
	private TextView textPrescritoMedico;
	private Spinner spinPrescritoMedico;
	private TextView textCualAntibiotico;
	private EditText inputCualAntibiotico;
	
	private Button mSaveView;
	
	//Variables donde se captura la entrada de datos
	private String fechaSintoma;
	private String fiebre;
	private String fif;
	private String fiebreCuantificada;
	private Float valorFiebreCuantificada;
	private String dolorCabeza;
	private String dolorArticular;
	private String dolorMuscular;
	private String dificultadRespiratoria;
	private String secrecionNasal;
	private String tos;
	private String pocoApetito;
	private String dolorGarganta;
	private String diarrea;
	private String quedoCama;
	private String respiracionRuidosa;
    private String respiracionRapida;
	private String oseltamivir;
	private String antibiotico;
	private String cualAntibiotico;
	private String prescritoMedico;
	
	final Calendar c = Calendar.getInstance();
	//Para el id
	private DeviceInfo infoMovil;
	private String username;
	private SharedPreferences settings;
	

    public static NuevaVisitaSeguimientoSintomasFragment create() {
        NuevaVisitaSeguimientoSintomasFragment fragment = new NuevaVisitaSeguimientoSintomasFragment();
        return fragment;
    }

    public NuevaVisitaSeguimientoSintomasFragment() {
    	
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
		mVisitaSeguimientoCaso = (VisitaSeguimientoCaso) getActivity().getIntent().getExtras().getSerializable(Constants.VISITA);
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
        View rootView = inflater.inflate(R.layout.fragment_nuevo_sintoma, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((EditText) rootView.findViewById(R.id.inputCodigoParticipanteCaso));
        mNameView.setText(mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getParticipante().getParticipante().getNombreCompleto());
        mNameView.setEnabled(false);
        inputFechaSintoma = (EditText) rootView.findViewById(R.id.inputFechaSintoma);
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
	        		textFif.setVisibility(View.VISIBLE);
	        		inputFif.setVisibility(View.VISIBLE);
	        		mButtonChangeFif.setVisibility(View.VISIBLE);
	        		spinFiebre.setBackgroundColor(Color.RED);
	        	}
	        	else{
	        		textFif.setVisibility(View.GONE);
	        		inputFif.setVisibility(View.GONE);
	        		mButtonChangeFif.setVisibility(View.GONE);
	        		inputFif.setText("");
	        		fif = "";
	        		spinFiebre.setBackgroundColor(Color.WHITE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
        textFif = (TextView) rootView.findViewById(R.id.textFif);
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
		});
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
	        	}
	        	else{
	        		spinDolorCabeza.setBackgroundColor(Color.WHITE);
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
	        	}
	        	else{
	        		spinDolorArticular.setBackgroundColor(Color.WHITE);
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
	        	}
	        	else{
	        		spinDolorMuscular.setBackgroundColor(Color.WHITE);
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
	        	}
	        	else{
	        		spinSecrecionNasal.setBackgroundColor(Color.WHITE);
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
	        	}
	        	else{
	        		spinTos.setBackgroundColor(Color.WHITE);
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
	        	}
	        	else{
	        		spinDolorGarganta.setBackgroundColor(Color.WHITE);
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
        spinOseltamivir = (Spinner) rootView.findViewById(R.id.spinOseltamivir);
        spinOseltamivir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	oseltamivir = mr.getCatKey();
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
	        		textCualAntibiotico.setVisibility(View.VISIBLE);
	        		inputCualAntibiotico.setVisibility(View.VISIBLE);
	        		textPrescritoMedico.setVisibility(View.VISIBLE);
	        		spinPrescritoMedico.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		textCualAntibiotico.setVisibility(View.GONE);
	        		inputCualAntibiotico.setVisibility(View.GONE);
	        		inputCualAntibiotico.setText("");
	        		textPrescritoMedico.setVisibility(View.GONE);
	        		spinPrescritoMedico.setVisibility(View.GONE);
	        		cualAntibiotico = null;
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
        textCualAntibiotico = (TextView) rootView.findViewById(R.id.textCualAntibiotico);
        textCualAntibiotico.setVisibility(View.GONE);
        inputCualAntibiotico = (EditText) rootView.findViewById(R.id.inputCualAntibiotico);
        inputCualAntibiotico.setVisibility(View.GONE);
        inputCualAntibiotico.addTextChangedListener(new TextWatcher() {
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
				cualAntibiotico = inputCualAntibiotico.getText().toString();
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
			minDate = new DateMidnight(mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaInicio());
			maxDate = DateMidnight.now();
			dpD.getDatePicker().setMinDate(minDate.getMillis());
			dpD.getDatePicker().setMaxDate(maxDate.getMillis());
			dpD.show();
			break;
		case DATE_FIF:
			dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
		        @Override
		        public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
		        	fif = (picker != null) ? String.valueOf(dayOfMonth<10? "0"+dayOfMonth:dayOfMonth)+"/"+String.valueOf((monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1))+"/"+String.valueOf(year) : null;
                	inputFif.setText(fif);
		        }
		    },c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
			minDate = new DateMidnight(mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaInicio());
			maxDate = DateMidnight.now();
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
        if (fechaSintoma == null || fechaSintoma.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fecha_sintoma)),Toast.LENGTH_LONG).show();
        	inputFechaSintoma.setError(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fecha_sintoma))); 
            return false;
        }
        else if (fiebre == null || fiebre.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fiebre)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (fiebre.equals(Constants.YESKEYSND) && (fif == null || fif.equals(""))){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fif)),Toast.LENGTH_LONG).show();
        	inputFif.setError(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fif))); 
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
        else if (dolorArticular == null || dolorArticular.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorArticular)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (dolorMuscular == null || dolorMuscular.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dolorMuscular)),Toast.LENGTH_LONG).show();
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
        else if (tos == null || tos.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tos)),Toast.LENGTH_LONG).show();
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
        else if (diarrea == null || diarrea.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.diarrea)),Toast.LENGTH_LONG).show();
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
        else if (oseltamivir == null || oseltamivir.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.oseltamivir)),Toast.LENGTH_LONG).show();
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
        else if (antibiotico.equals(Constants.YESKEYSND) && (cualAntibiotico == null || cualAntibiotico.equals(""))){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.cualAntibiotico)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (formatter.parse(fechaSintoma).before(mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaInicio())){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.fecBefRegistro,getActivity().getString(R.string.fecha_sintoma),getActivity().getString(R.string.fechaInicio)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (fiebre.equals(Constants.YESKEYSND) && (formatter.parse(fif).after(formatter.parse(fechaSintoma)))){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.fecAftRegistro,getActivity().getString(R.string.fif),getActivity().getString(R.string.fecha_sintoma)),Toast.LENGTH_LONG).show();
            return false;
        }
        else{
        	vscs.setCodigoCasoSintoma(infoMovil.getId());
        	vscs.setCodigoVisitaCaso(mVisitaSeguimientoCaso);
        	vscs.setFechaSintomas(formatter.parse(fechaSintoma));
        	vscs.setFiebre(fiebre);
        	if (fiebre.equals(Constants.YESKEYSND)) vscs.setFif(formatter.parse(fif));
			vscs.setFiebreCuantificada(fiebreCuantificada);
			vscs.setValorFiebreCuantificada(valorFiebreCuantificada);
			vscs.setDolorCabeza(dolorCabeza);
			vscs.setDolorArticular(dolorArticular);
			vscs.setDolorMuscular(dolorMuscular);
			vscs.setDificultadRespiratoria(dificultadRespiratoria);
			vscs.setSecrecionNasal(secrecionNasal);
			vscs.setTos(tos);
			vscs.setPocoApetito(pocoApetito);
			vscs.setDolorGarganta(dolorGarganta);
			vscs.setDiarrea(diarrea);
			vscs.setQuedoCama(quedoCama);
			vscs.setRespiracionRuidosa(respiracionRuidosa);
            vscs.setRespiracionRapida(respiracionRapida);
			vscs.setOseltamivir(oseltamivir);
			vscs.setAntibiotico(antibiotico);
			vscs.setPrescritoMedico(prescritoMedico);
			vscs.setCualAntibiotico(cualAntibiotico);
			
			vscs.setRecordDate(new Date());
			vscs.setRecordUser(username);
			vscs.setDeviceid(infoMovil.getDeviceId());
			vscs.setEstado('0');
			vscs.setPasive('0');
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
				mVisitaSeguimientoCasos = estudiosAdapter.getVisitaSeguimientoCasos(CasosDBConstants.codigoCasoParticipante +" = '" + mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCasoParticipante() +"'", MainDBConstants.fechaVisita);
				String filtro = "";
				for (VisitaSeguimientoCaso vsc: mVisitaSeguimientoCasos){
					if (filtro.equals("")){ filtro = CasosDBConstants.codigoCasoVisita + "='" + vsc.getCodigoCasoVisita()+"'";} else{
					if (!filtro.equals("")) filtro = filtro + " or " + CasosDBConstants.codigoCasoVisita + "='" + vsc.getCodigoCasoVisita()+"'";}
				}
				mVisitasSeguimientoCasoSintomas = estudiosAdapter.getVisitaSeguimientoCasosSintomas(filtro, CasosDBConstants.fechaSintomas);
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
			spinDiarrea.setAdapter(dataAdapter);
			spinQuedoCama.setAdapter(dataAdapter);
			spinRespiracionRuidosa.setAdapter(dataAdapter);
            spinRespiracionRapida.setAdapter(dataAdapter);
			spinOseltamivir.setAdapter(dataAdapter);
			spinAntibiotico.setAdapter(dataAdapter);
			spinPrescritoMedico.setAdapter(dataAdapter);
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
				for (VisitaSeguimientoCasoSintomas vscc:mVisitasSeguimientoCasoSintomas){
					if(vscc.getFechaSintomas().equals(vscs.getFechaSintomas())){
						return getActivity().getString(R.string.duplicateDate) + " - " + fechaSintoma + " / " + getActivity().getString(R.string.visit) + " - " + vscc.getCodigoVisitaCaso().getVisita();
					}
				}
				estudiosAdapter.crearVisitaSeguimientoCasoSintomas(vscs);
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
						ListaSintomasParticipantesCasosActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        i.putExtras(arguments);
				startActivity(i);
				getActivity().finish();
			}
		}

	}	
    
}
