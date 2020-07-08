package ni.org.ics.estudios.appmovil.covid19.activities.fragments;


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaVisitasFinalesCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaFinalCasoCovid19;
import ni.org.ics.estudios.appmovil.multiselector.gui.MultiSpinner;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import org.joda.time.DateMidnight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NuevaVisitaFinalCovid19Fragment extends Fragment {

	private static final String TAG = "NuevaVisitaFinalCovid19Fragment";
	protected static final int DATE_DIALOG_ID = 101;
	protected static final int DATE_ADMISSION = 102;
	protected static final int DATE_EGRESS = 103;

	private EstudiosAdapter estudiosAdapter;
	private ParticipanteCasoCovid19 mParticipanteCaso;
	private VisitaFinalCasoCovid19 vsc = new VisitaFinalCasoCovid19();
	private List<MessageResource> mCatalogoSn;
	private List<MessageResource> mCatalogoEstSalud;
	private List<String> mTratamientos = new ArrayList<String>();
	private TextView mTitleView;
	private TextView mNameView;
	private TextView inputFechaVisita;
	protected ImageButton mButtonChangeDate;

	private TextView textConsTerreno;
	private TextView textReferidoCs;
	private TextView textTratamiento;
	private TextView textQueAntibiotico;
	private TextView textOtroMedicamento;
	private TextView textFechaIngresoHosp;
	private TextView textFechaEgresoHosp;
	private TextView textCualHospital;
	private TextView textUtilizoOxigeno;
	private TextView textFueIntubado;
	private TextView textEstadoSalud;
	private TextView textDiasFaltoTrabajoEscuela;
	private EditText inputQueAntibiotico;
	private EditText inputOtroMedicamento;
	private EditText inputFechaIngresoHosp;
	private EditText inputFechaEgresoHosp;
	private EditText inputCualHospital;
	private EditText inputDiasFaltoTrabajoEscuela;
	private Spinner spinEnfermo;
	private Spinner spinConsTerreno;
	private Spinner spinReferidoCs;
	private Spinner spinSintResp;
	private Spinner spinFueHospitalizado;
	private Spinner spinUtilizoOxigeno;
	private Spinner spinFueIntubado;
	private Spinner spinEstadoSalud;
	private Spinner spinFaltoTrabajoEscuela;
	private MultiSpinner spinTratamiento;
	private ImageButton fechaIngresoHosp_button;
	private ImageButton fechaEgresoHosp_button;


	private Button mSaveView;

	private String fechaVisita;
	private String enfermo;
	private String consTerreno;
	private String referidoCS;
	private String tratamiento;
	private String queAntibiotico;
	private String otroMedicamento;
	private String sintResp;
	private String fueHospitalizado;
	private String fechaIngreso;
	private String fechaEgreso;
	private Integer diasHospitalizado;
	private String cualHospital;
	private String utilizoOxigeno;
	private String fueIntubado;
	private String estadoSalud;
	private String faltoTrabajoEscuela;
	private Integer diasFaltoTrabajoEscuela;

	final Calendar c = Calendar.getInstance();
    final Calendar calLimiteFecVisita = Calendar.getInstance();
    private boolean esTCovid = false;

	private DeviceInfo infoMovil;
	private String username;
	private SharedPreferences settings;


    public static NuevaVisitaFinalCovid19Fragment create() {
        NuevaVisitaFinalCovid19Fragment fragment = new NuevaVisitaFinalCovid19Fragment();
        return fragment;
    }

    public NuevaVisitaFinalCovid19Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
        mParticipanteCaso = (ParticipanteCasoCovid19) getActivity().getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        calLimiteFecVisita.setTime(mParticipanteCaso.getCodigoCaso().getFechaIngreso());
        calLimiteFecVisita.add(Calendar.DATE,90);//90 dias después de la fecha de ingreso
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
        View rootView = inflater.inflate(R.layout.fragment_nueva_visita_final_covid19, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((TextView) rootView.findViewById(R.id.inputCodigoParticipanteCaso));
        mNameView.setText(mParticipanteCaso.getParticipante().getNombreCompleto());
        mNameView.setEnabled(false);
        inputFechaVisita = (TextView) rootView.findViewById(R.id.inputFechaVisita);
        inputFechaVisita.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)<10? "0"+c.get(Calendar.DAY_OF_MONTH):c.get(Calendar.DAY_OF_MONTH))+"/"+
        							String.valueOf((c.get(Calendar.MONTH)+1)<10? "0"+(c.get(Calendar.MONTH)+1):(c.get(Calendar.MONTH)+1))+"/"+String.valueOf(c.get(Calendar.YEAR))+" "+
        							String.valueOf(c.get(Calendar.HOUR_OF_DAY)<10? "0"+c.get(Calendar.HOUR_OF_DAY):c.get(Calendar.HOUR_OF_DAY))+":"+
        							String.valueOf(c.get(Calendar.MINUTE)<10? "0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE)));
        inputFechaVisita.setEnabled(false);
        fechaVisita = inputFechaVisita.getText().toString();
        mButtonChangeDate = (ImageButton) rootView.findViewById(R.id.changedate_button);
        mButtonChangeDate.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				try {
					createDialog(DATE_DIALOG_ID);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
        mButtonChangeDate.setEnabled(false);

        spinEnfermo = (Spinner) rootView.findViewById(R.id.spinEnfermo);
		spinEnfermo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				enfermo = mr.getCatKey();
				if(enfermo.equals(Constants.YESKEYSND)){
					spinConsTerreno.setVisibility(View.VISIBLE);
					textConsTerreno.setVisibility(View.VISIBLE);
					spinReferidoCs.setVisibility(View.VISIBLE);
					textReferidoCs.setVisibility(View.VISIBLE);
					spinTratamiento.setVisibility(View.VISIBLE);
					textTratamiento.setVisibility(View.VISIBLE);
					textQueAntibiotico.setVisibility(View.GONE);
					inputQueAntibiotico.setVisibility(View.GONE);
					textOtroMedicamento.setVisibility(View.GONE);
					inputOtroMedicamento.setVisibility(View.GONE);

				}
				else{
					spinConsTerreno.setVisibility(View.GONE);
					textConsTerreno.setVisibility(View.GONE);
					spinReferidoCs.setVisibility(View.GONE);
					textReferidoCs.setVisibility(View.GONE);
					spinTratamiento.setVisibility(View.GONE);
					textTratamiento.setVisibility(View.GONE);
					textQueAntibiotico.setVisibility(View.GONE);
					inputQueAntibiotico.setVisibility(View.GONE);
					textOtroMedicamento.setVisibility(View.GONE);
					inputOtroMedicamento.setVisibility(View.GONE);
					consTerreno = null;
					referidoCS = null;
					tratamiento = null;
					queAntibiotico = null;
					otroMedicamento = null;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		textConsTerreno = (TextView) rootView.findViewById(R.id.textConsTerreno);
		spinConsTerreno = (Spinner) rootView.findViewById(R.id.spinConsTerreno);
		spinConsTerreno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				consTerreno = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinConsTerreno.setVisibility(View.GONE);

		textReferidoCs = (TextView) rootView.findViewById(R.id.textReferidoCs);
		spinReferidoCs = (Spinner) rootView.findViewById(R.id.spinReferidoCs);
		spinReferidoCs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				referidoCS = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinReferidoCs.setVisibility(View.GONE);

		textTratamiento = (TextView) rootView.findViewById(R.id.textTratamiento);
		textTratamiento.setVisibility(View.GONE);
		spinTratamiento = (MultiSpinner) rootView.findViewById(R.id.spinTratamiento);
		spinTratamiento.setVisibility(View.GONE);
		textQueAntibiotico = (TextView) rootView.findViewById(R.id.textQueAntibiotico);
		textQueAntibiotico.setVisibility(View.GONE);
		inputQueAntibiotico = (EditText) rootView.findViewById(R.id.inputQueAntibiotico);
		inputQueAntibiotico.setVisibility(View.GONE);
		textOtroMedicamento = (TextView) rootView.findViewById(R.id.textOtroMedicamento);
		textOtroMedicamento.setVisibility(View.GONE);
		inputOtroMedicamento = (EditText) rootView.findViewById(R.id.inputOtroMedicamento);
		inputOtroMedicamento.setVisibility(View.GONE);

		inputQueAntibiotico.addTextChangedListener(new TextWatcher() {
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
				queAntibiotico = inputQueAntibiotico.getText().toString();
			}

		});
		inputQueAntibiotico.setVisibility(View.GONE);

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
				otroMedicamento = inputOtroMedicamento.getText().toString();
			}

		});
		inputOtroMedicamento.setVisibility(View.GONE);

		spinSintResp = (Spinner) rootView.findViewById(R.id.spinSintResp);
		spinSintResp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				sintResp = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		textCualHospital = (TextView) rootView.findViewById(R.id.textCualHospital);
		textFueIntubado = (TextView) rootView.findViewById(R.id.textFueIntubado);
		textEstadoSalud = (TextView) rootView.findViewById(R.id.textEstadoSalud);
		textUtilizoOxigeno = (TextView) rootView.findViewById(R.id.textUtilizoOxigeno);

		spinFueHospitalizado = (Spinner) rootView.findViewById(R.id.spinFueHospitalizado);
		spinFueHospitalizado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				fueHospitalizado = mr.getCatKey();
				if(fueHospitalizado.equals(Constants.YESKEYSND)){
					textFechaIngresoHosp.setVisibility(View.VISIBLE);
					inputFechaIngresoHosp.setVisibility(View.VISIBLE);
					fechaEgresoHosp_button.setVisibility(View.VISIBLE);
					fechaIngresoHosp_button.setVisibility(View.VISIBLE);
					textFechaEgresoHosp.setVisibility(View.VISIBLE);
					inputFechaEgresoHosp.setVisibility(View.VISIBLE);
					textCualHospital.setVisibility(View.VISIBLE);
					inputCualHospital.setVisibility(View.VISIBLE);
					spinUtilizoOxigeno.setVisibility(View.VISIBLE);
					textUtilizoOxigeno.setVisibility(View.VISIBLE);
					spinFueIntubado.setVisibility(View.VISIBLE);
					textFueIntubado.setVisibility(View.VISIBLE);
					textEstadoSalud.setVisibility(View.VISIBLE);
					spinEstadoSalud.setVisibility(View.VISIBLE);
				}
				else{
					textFechaIngresoHosp.setVisibility(View.GONE);
					inputFechaIngresoHosp.setVisibility(View.GONE);
					fechaEgresoHosp_button.setVisibility(View.GONE);
					fechaIngresoHosp_button.setVisibility(View.GONE);
					textFechaEgresoHosp.setVisibility(View.GONE);
					inputFechaEgresoHosp.setVisibility(View.GONE);
					textCualHospital.setVisibility(View.GONE);
					inputCualHospital.setVisibility(View.GONE);
					spinUtilizoOxigeno.setVisibility(View.GONE);
					textUtilizoOxigeno.setVisibility(View.GONE);
					spinFueIntubado.setVisibility(View.GONE);
					textFueIntubado.setVisibility(View.GONE);
					textEstadoSalud.setVisibility(View.GONE);
					spinEstadoSalud.setVisibility(View.GONE);
					fechaIngreso = null;
					fechaEgreso = null;
					cualHospital = null;
					utilizoOxigeno = null;
					fueIntubado = null;
					estadoSalud = null;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spinUtilizoOxigeno = (Spinner) rootView.findViewById(R.id.spinUtilizoOxigeno);
		spinUtilizoOxigeno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				utilizoOxigeno = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinUtilizoOxigeno.setVisibility(View.GONE);

		spinFueIntubado = (Spinner) rootView.findViewById(R.id.spinFueIntubado);
		spinFueIntubado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				fueIntubado = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinFueIntubado.setVisibility(View.GONE);

		spinEstadoSalud = (Spinner) rootView.findViewById(R.id.spinEstadoSalud);
		spinEstadoSalud.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				estadoSalud = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinEstadoSalud.setVisibility(View.GONE);

		textFechaIngresoHosp = (TextView) rootView.findViewById(R.id.textFechaIngresoHosp);
		textFechaIngresoHosp.setVisibility(View.GONE);
		inputFechaIngresoHosp = (EditText) rootView.findViewById(R.id.inputFechaIngresoHosp);
		inputFechaIngresoHosp.setVisibility(View.GONE);
		fechaIngresoHosp_button = (ImageButton) rootView.findViewById(R.id.fechaIngresoHosp_button);
		fechaIngresoHosp_button.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				try {
					createDialog(DATE_ADMISSION);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		fechaIngresoHosp_button.setVisibility(View.GONE);

		textFechaEgresoHosp = (TextView) rootView.findViewById(R.id.textFechaEgresoHosp);
		textFechaEgresoHosp.setVisibility(View.GONE);
		inputFechaEgresoHosp = (EditText) rootView.findViewById(R.id.inputFechaEgresoHosp);
		inputFechaEgresoHosp.setVisibility(View.GONE);
		fechaEgresoHosp_button = (ImageButton) rootView.findViewById(R.id.fechaEgresoHosp_button);
		fechaEgresoHosp_button.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				try {
					createDialog(DATE_EGRESS);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		fechaEgresoHosp_button.setVisibility(View.GONE);

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
					cualHospital = inputCualHospital.getText().toString();
			}

		});
		inputCualHospital.setVisibility(View.GONE);

		spinFaltoTrabajoEscuela = (Spinner) rootView.findViewById(R.id.spinFaltoTrabajoEscuela);
		spinFaltoTrabajoEscuela.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				faltoTrabajoEscuela = mr.getCatKey();
				if(faltoTrabajoEscuela.equals(Constants.YESKEYSND)){
					textDiasFaltoTrabajoEscuela.setVisibility(View.VISIBLE);
					inputDiasFaltoTrabajoEscuela.setVisibility(View.VISIBLE);

				}
				else{
					textDiasFaltoTrabajoEscuela.setVisibility(View.GONE);
					inputDiasFaltoTrabajoEscuela.setVisibility(View.GONE);
					diasFaltoTrabajoEscuela = null;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		textDiasFaltoTrabajoEscuela = (TextView) rootView.findViewById(R.id.textDiasFaltoTrabajoEscuela);
		textDiasFaltoTrabajoEscuela.setVisibility(View.GONE);
		inputDiasFaltoTrabajoEscuela = (EditText) rootView.findViewById(R.id.inputDiasFaltoTrabajoEscuela);
		inputDiasFaltoTrabajoEscuela.addTextChangedListener(new TextWatcher() {
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
					diasFaltoTrabajoEscuela = Integer.valueOf(inputDiasFaltoTrabajoEscuela.getText().toString());
				}
				catch(Exception e){
					inputDiasFaltoTrabajoEscuela.setError(e.toString());
					inputDiasFaltoTrabajoEscuela.requestFocus();
					return;
				}
			}

		});
		inputDiasFaltoTrabajoEscuela.setVisibility(View.GONE);

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
    
    private void createDialog(int dialog) throws ParseException {
		final DatePickerDialog dpD;
		DateMidnight minDate;
		DateMidnight maxDate;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		switch(dialog) {
			case DATE_DIALOG_ID:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						fechaVisita = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputFechaVisita.setText(fechaVisita);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				minDate = DateMidnight.now().minusDays(3);
				maxDate = DateMidnight.now();
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_ADMISSION:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						fechaIngreso = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputFechaIngresoHosp.setText(fechaIngreso);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (mParticipanteCaso.getFis() != null) {
					Date fechaInicio = mParticipanteCaso.getCodigoCaso().getFechaIngreso();
					Date fechaEnfermedad = mParticipanteCaso.getFis();
					//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
					if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
						minDate = new DateMidnight(fechaEnfermedad);
					else //sino tomar la fecha de inicio
						minDate = new DateMidnight(fechaInicio);
				} else {
					minDate = new DateMidnight(mParticipanteCaso.getCodigoCaso().getFechaIngreso());
				}
				maxDate = DateMidnight.now();
				dpD.getDatePicker().setMinDate(minDate.getMillis());
				dpD.getDatePicker().setMaxDate(maxDate.getMillis());
				dpD.show();
				break;
			case DATE_EGRESS:
				dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						fechaEgreso = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputFechaEgresoHosp.setText(fechaEgreso);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				if (fechaIngreso!=null){
					Date ingreso = formatter.parse(fechaIngreso);
					minDate = new DateMidnight(ingreso);
				}else {
					if (mParticipanteCaso.getFis() != null) {
						Date fechaInicio = mParticipanteCaso.getCodigoCaso().getFechaIngreso();
						Date fechaEnfermedad = mParticipanteCaso.getFis();
						//si la fecha de enfermedad es menor a la fecha de inicio, tomar la fecha de enfermedad
						if (fechaInicio.compareTo(fechaEnfermedad) >= 0)
							minDate = new DateMidnight(fechaEnfermedad);
						else //sino tomar la fecha de inicio
							minDate = new DateMidnight(fechaInicio);
					} else {
						minDate = new DateMidnight(mParticipanteCaso.getCodigoCaso().getFechaIngreso());
					}
				}
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
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	Date dVis = null;
		try {
			dVis = formatter.parse(fechaVisita);
            vsc.setFechaVisita(dVis);
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            dVis = formatter.parse(fechaVisita);
		} catch (ParseException e) {
			Toast.makeText(getActivity(), e.toString() ,Toast.LENGTH_LONG).show();
			e.printStackTrace();
			return false;
		}
        if (fechaVisita == null || fechaVisita.equals("")){
        	inputFechaVisita.setError(getActivity().getString(R.string.wrong_fecha_visita));
        	inputFechaVisita.requestFocus();
            return false;
        } else if (enfermo == null || enfermo.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.enfermo)),Toast.LENGTH_LONG).show();
			return false;
		} else if (enfermo.equals(Constants.YESKEYSND) && (consTerreno == null || consTerreno.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.consTerreno)),Toast.LENGTH_LONG).show();
			return false;
		} else if (enfermo.equals(Constants.YESKEYSND) && (referidoCS == null || referidoCS.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.referidoCs)),Toast.LENGTH_LONG).show();
			return false;
		} else if (enfermo.equals(Constants.YESKEYSND) && (tratamiento == null || tratamiento.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tratamientoVFCovid19)),Toast.LENGTH_LONG).show();
			return false;
		} else if (tratamiento!=null && tratamiento.toLowerCase().contains("antibiótico") && (queAntibiotico == null || queAntibiotico.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.queAntibiotico)),Toast.LENGTH_LONG).show();
			inputQueAntibiotico.requestFocus();
			return false;
		} else if (tratamiento!=null && tratamiento.toLowerCase().contains("otro") && (otroMedicamento == null || otroMedicamento.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.otroMedicamento)),Toast.LENGTH_LONG).show();
			inputOtroMedicamento.requestFocus();
			return false;
		}else if (sintResp == null || sintResp.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.sintResp)),Toast.LENGTH_LONG).show();
			return false;
		} else if (fueHospitalizado == null || fueHospitalizado.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fueHospitalizado)),Toast.LENGTH_LONG).show();
			return false;
		} else if (fueHospitalizado.equals(Constants.YESKEYSND) && (fechaIngreso == null || fechaIngreso.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fechaIngresoHosp)),Toast.LENGTH_LONG).show();
			inputFechaIngresoHosp.requestFocus();
			return false;
		} else if (fueHospitalizado.equals(Constants.YESKEYSND) && (fechaEgreso == null || fechaEgreso.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fechaEgresoHosp)),Toast.LENGTH_LONG).show();
			inputFechaIngresoHosp.requestFocus();
			return false;
		}  else if (fueHospitalizado.equals(Constants.YESKEYSND) && (cualHospital == null || cualHospital.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.cualHospitalVF)),Toast.LENGTH_LONG).show();
			inputCualHospital.requestFocus();
			return false;
		}  else if (fueHospitalizado.equals(Constants.YESKEYSND) && (utilizoOxigeno == null || utilizoOxigeno.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.utilizoOxigeno)),Toast.LENGTH_LONG).show();
			return false;
		}  else if (fueHospitalizado.equals(Constants.YESKEYSND) && (fueIntubado == null || fueIntubado.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fueIntubado)),Toast.LENGTH_LONG).show();
			return false;
		}  else if (fueHospitalizado.equals(Constants.YESKEYSND) && (estadoSalud == null || estadoSalud.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.estadoSalud)),Toast.LENGTH_LONG).show();
			return false;
		} else if (faltoTrabajoEscuela == null || faltoTrabajoEscuela.equals("")){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.faltoTrabajoEscuela)),Toast.LENGTH_LONG).show();
			return false;
		}  else if (faltoTrabajoEscuela.equals(Constants.YESKEYSND) && (diasFaltoTrabajoEscuela == null)){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.diasFaltoTrabajoEscuela)),Toast.LENGTH_LONG).show();
			inputDiasFaltoTrabajoEscuela.requestFocus();
			return false;
		}
		else if(dVis.before(mParticipanteCaso.getCodigoCaso().getFechaIngreso())){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrong_fecha_visita),Toast.LENGTH_LONG).show();
        	return false;
        }
        else if (dVis.after(calLimiteFecVisita.getTime())){//si la fecha de visita es posterior a los 90 dias después de la fecha de inicio no permitir registro
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrong_fecha_visita4),Toast.LENGTH_LONG).show();
            return false;
        }
        else{
                try {
                    estudiosAdapter.open();
                    VisitaFinalCasoCovid19 visitaExiste = estudiosAdapter.getVisitaFinalCasoCovid19(Covid19DBConstants.codigoParticipanteCaso+"='"+mParticipanteCaso.getCodigoCasoParticipante()+"'", null);
                    if (visitaExiste!=null) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.duplicateVisit), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }catch (Exception e){
                    Log.e(TAG, e.getLocalizedMessage(), e);
                }finally {
                    estudiosAdapter.close();
                }
        	vsc.setCodigoVisitaFinal(infoMovil.getId());
        	vsc.setCodigoParticipanteCaso(mParticipanteCaso);
        	vsc.setEnfermo(enfermo);
        	vsc.setConsTerreno(consTerreno);
			vsc.setReferidoCS(referidoCS);
			estudiosAdapter.open();
			if (tratamiento!=null && !tratamiento.isEmpty()) {
				String keysTx = "";
				tratamiento = tratamiento.replaceAll(",", "','");
				List<MessageResource> catMedi = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + tratamiento + "') and "
						+ CatalogosDBConstants.catRoot + "='COVID_TRATAMIENTO_VFC'", null);
				for (MessageResource ms : catMedi) {
					keysTx += ms.getCatKey() + ",";
				}
				if (!keysTx.isEmpty())
					keysTx = keysTx.substring(0, keysTx.length() - 1);
				vsc.setTratamiento(keysTx);
			}
			estudiosAdapter.close();
			vsc.setQueAntibiotico(queAntibiotico);
			vsc.setOtroMedicamento(otroMedicamento);
			vsc.setSintResp(sintResp);
			vsc.setFueHospitalizado(fueHospitalizado);
			if (fechaIngreso!=null && !fechaIngreso.isEmpty()) vsc.setFechaIngreso(formatter.parse(fechaIngreso));
			if (fechaEgreso!=null && !fechaEgreso.isEmpty()) vsc.setFechaEgreso(formatter.parse(fechaEgreso));
			if (fechaIngreso!=null && !fechaIngreso.isEmpty() && fechaEgreso!=null && !fechaEgreso.isEmpty()) {
				Long dias = DateUtil.getDateDiff(vsc.getFechaIngreso(), vsc.getFechaEgreso(), TimeUnit.DAYS);
				vsc.setDiasHospitalizado(dias.intValue());
			}
			vsc.setCualHospital(cualHospital);
			vsc.setUtilizoOxigeno(utilizoOxigeno);
			vsc.setFueIntubado(fueIntubado);
			vsc.setEstadoSalud(estadoSalud);
			vsc.setFaltoTrabajoEscuela(faltoTrabajoEscuela);
			vsc.setDiasFaltoTrabajoEscuela(diasFaltoTrabajoEscuela);
        	vsc.setRecordDate(new Date());
        	vsc.setRecordUser(username);
        	vsc.setDeviceid(infoMovil.getDeviceId());
        	vsc.setEstado('0');
        	vsc.setPasive('0');
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
				mCatalogoSn = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", CatalogosDBConstants.order);
				mCatalogoEstSalud = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_ESTSALUD_HOSP'", CatalogosDBConstants.order);
				mTratamientos = Arrays.asList(estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='COVID_TRATAMIENTO_VFC'", CatalogosDBConstants.order));
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			} finally {
				estudiosAdapter.close();
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			nDialog.dismiss();
			mTitleView.setText(getActivity().getString(R.string.new_final_visit));

			mCatalogoSn.add(new MessageResource("",0,getActivity().getString(R.string.select)));
			Collections.sort(mCatalogoSn);
			ArrayAdapter<MessageResource> dataAdapter = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSn);
			dataAdapter.setDropDownViewResource(R.layout.spinner_item);

			mCatalogoEstSalud.add(new MessageResource("",0,getActivity().getString(R.string.select)));
			Collections.sort(mCatalogoEstSalud);
			ArrayAdapter<MessageResource> dataAdapter2 = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoEstSalud);
			dataAdapter2.setDropDownViewResource(R.layout.spinner_item);

			spinEnfermo.setAdapter(dataAdapter);
			spinConsTerreno.setAdapter(dataAdapter);
			spinReferidoCs.setAdapter(dataAdapter);
			spinSintResp.setAdapter(dataAdapter);
			spinFueHospitalizado.setAdapter(dataAdapter);
			spinUtilizoOxigeno.setAdapter(dataAdapter);
			spinFueIntubado.setAdapter(dataAdapter);
			spinEstadoSalud.setAdapter(dataAdapter2);
			spinFaltoTrabajoEscuela.setAdapter(dataAdapter);

			spinTratamiento.setItems(mTratamientos, getString(R.string.select), new MultiSpinner.MultiSpinnerListener() {
				@Override
				public void onItemsSelected(boolean[] selected) {
					int indice = 0;
					tratamiento = null;
					for(boolean item : selected){
						if (item) {
							if (tratamiento==null) tratamiento = mTratamientos.get(indice);
							else  tratamiento += "," + mTratamientos.get(indice);
						}
						indice++;
					}
					if (tratamiento!=null && tratamiento.toLowerCase().contains("otro")){
						textOtroMedicamento.setVisibility(View.VISIBLE);
						inputOtroMedicamento.setVisibility(View.VISIBLE);
					}else{
						textOtroMedicamento.setVisibility(View.GONE);
						inputOtroMedicamento.setVisibility(View.GONE);
					}
					if (tratamiento!=null && tratamiento.toLowerCase().contains("antibiótico")){
						textQueAntibiotico.setVisibility(View.VISIBLE);
						inputQueAntibiotico.setVisibility(View.VISIBLE);
					}else{
						textQueAntibiotico.setVisibility(View.GONE);
						inputQueAntibiotico.setVisibility(View.GONE);
					}
				}
			});
		}

	}
    
    
    private class SaveDataTask extends AsyncTask<String, Void, String> {
    	private ProgressDialog nDialog;
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
				estudiosAdapter.crearVisitaFinalCasoCovid19(vsc);
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
			Bundle arguments = new Bundle();
			Intent i;
			arguments.putSerializable(Constants.PARTICIPANTE , mParticipanteCaso);
			i = new Intent(getActivity(),
					ListaVisitasFinalesCasoCovid19Activity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        i.putExtras(arguments);
			startActivity(i);
			getActivity().finish();
		}

	}	
    
}
