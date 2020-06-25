package ni.org.ics.estudios.appmovil.covid19.activities.fragments;


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
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
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaVisitasParticipanteCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaSeguimientoCasoCovid19;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import org.joda.time.DateMidnight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NuevaVisitaSeguimientoCovid19Fragment extends Fragment {

	private static final String TAG = "NuevaVisitaSeguimientoCovid19Fragment";
	protected static final int DATE_DIALOG_ID = 101;
	protected static final int TIME_DIALOG_ID = 102;
	protected static final int DATE_FIRST_SINT = 103;

	private EstudiosAdapter estudiosAdapter;
	private ParticipanteCasoCovid19 mParticipanteCaso;
	private VisitaSeguimientoCasoCovid19 vsc = new VisitaSeguimientoCasoCovid19();
	private List<MessageResource> mCatalogoSn;
    private List<MessageResource> mVisitas;
	private TextView mTitleView;
	private TextView mNameView;
	private TextView inputFechaVisita;
	protected ImageButton mButtonChangeDate;
	private TextView inputHoraProbableVisita;
	protected ImageButton mButtonChangeTime;
    private Spinner spinVisita;
	//private TextView mVisita;
	private Spinner spinExpCS;
	private TextView mTempView;
	private TextView mSaturacionView;
	private TextView mFrecRespView;
	private TextView textFecIniPrimerSintoma;
	private EditText inputFecIniPrimerSintoma;
	private TextView textPrimerSintoma;
	private ImageButton fecIniPrimerSintoma_button;
	private EditText inputPrimerSintoma;

	private Button mSaveView;

	private int horaDefectoTimer = 7;
	private int minutoDefectoTimer = 0;
	private boolean primerSintomaIngresado = false;

	private String fechaVisita;
	private String visita;
	private String horaProbableVisita;
	private String expCs;
	private float temp;
	private int saturacion;
	private int frecResp;
	private String fecIniPrimerSintoma;
	private String primerSintoma;
	final Calendar c = Calendar.getInstance();
    final Calendar calLimiteFecVisita = Calendar.getInstance();
    private boolean esTCovid = false;

	private DeviceInfo infoMovil;
	private String username;
	private SharedPreferences settings;


    public static NuevaVisitaSeguimientoCovid19Fragment create() {
        NuevaVisitaSeguimientoCovid19Fragment fragment = new NuevaVisitaSeguimientoCovid19Fragment();
        return fragment;
    }

    public NuevaVisitaSeguimientoCovid19Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
        mParticipanteCaso = (ParticipanteCasoCovid19) getActivity().getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        primerSintomaIngresado = this.getActivity().getIntent().getBooleanExtra(Constants.PRIMER_SINTOMA, false);
        calLimiteFecVisita.setTime(mParticipanteCaso.getCodigoCaso().getFechaIngreso());
        calLimiteFecVisita.add(Calendar.DATE,30);//12 dias después de la fecha de ingreso
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
        View rootView = inflater.inflate(R.layout.fragment_nueva_visita_covid19, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((TextView) rootView.findViewById(R.id.inputCodigoParticipanteCaso));
        mNameView.setText(mParticipanteCaso.getParticipante().getNombreCompleto());
        mNameView.setEnabled(false);
		//mVisita = (TextView) rootView.findViewById(R.id.inputVisita);

        spinVisita = (Spinner) rootView.findViewById(R.id.spinVisita);
        spinVisita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                visita = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
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
				createDialog(DATE_DIALOG_ID);
			}
		});
        mButtonChangeDate.setEnabled(false);
        inputHoraProbableVisita = (TextView) rootView.findViewById(R.id.inputHoraProbableVisita);
        inputHoraProbableVisita.setEnabled(false);
        mButtonChangeTime = (ImageButton) rootView.findViewById(R.id.changetime_button);
        mButtonChangeTime.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(TIME_DIALOG_ID);
			}
		});
		spinExpCS = (Spinner) rootView.findViewById(R.id.spinExpCS);
		spinExpCS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	expCs = mr.getCatKey();
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });

		textFecIniPrimerSintoma = (TextView) rootView.findViewById(R.id.textFecIniPrimerSintoma);
		inputFecIniPrimerSintoma = (EditText) rootView.findViewById(R.id.inputFecIniPrimerSintoma);
		fecIniPrimerSintoma_button = (ImageButton) rootView.findViewById(R.id.fecIniPrimerSintoma_button);
		fecIniPrimerSintoma_button.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DATE_FIRST_SINT);
			}
		});
		textPrimerSintoma = (TextView) rootView.findViewById(R.id.textPrimerSintoma);
		inputPrimerSintoma = ((EditText) rootView.findViewById(R.id.inputPrimerSintoma));
		inputPrimerSintoma.addTextChangedListener(new TextWatcher() {
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
					primerSintoma = inputPrimerSintoma.getText().toString();
				}
				catch(Exception e){
					inputPrimerSintoma.setError(e.toString());
					inputPrimerSintoma.requestFocus();
					return;
				}
			}

		});
		if (primerSintomaIngresado) {
			textFecIniPrimerSintoma.setVisibility(View.GONE);
			textPrimerSintoma.setVisibility(View.GONE);
			inputFecIniPrimerSintoma.setVisibility(View.GONE);
			fecIniPrimerSintoma_button.setVisibility(View.GONE);
			inputPrimerSintoma.setVisibility(View.GONE);
		}

		spinExpCS = (Spinner) rootView.findViewById(R.id.spinExpCS);
		spinExpCS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				expCs = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		mTempView = ((TextView) rootView.findViewById(R.id.inputTemp));
		mTempView.addTextChangedListener(new TextWatcher() {
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
					if (!mTempView.getText().toString().isEmpty()) {
						temp = Float.valueOf(mTempView.getText().toString());
						if (temp < 36 || temp > 41) mTempView.setError(getString(R.string.wrongTemp, 36, 41));
					}
					mTempView.requestFocus();
				}
				catch(Exception e){
					mTempView.setError(e.toString());
					mTempView.requestFocus();
					return;
				}
			}

		});
		mSaturacionView = ((TextView) rootView.findViewById(R.id.inputSaturacion));
		mSaturacionView.addTextChangedListener(new TextWatcher() {
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
					if (!mSaturacionView.getText().toString().isEmpty()) {
						saturacion = Integer.valueOf(mSaturacionView.getText().toString());
						if (saturacion < 0 || saturacion > 100)
							mSaturacionView.setError(getString(R.string.wrongSaturation, 0, 100));
					}
					mSaturacionView.requestFocus();
				}
				catch(Exception e){
					mSaturacionView.setError(e.toString());
					mSaturacionView.requestFocus();
					return;
				}
			}

		});
		mFrecRespView = ((TextView) rootView.findViewById(R.id.inputFrecResp));
		mFrecRespView.addTextChangedListener(new TextWatcher() {
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
					if (!mFrecRespView.getText().toString().isEmpty()) {
						frecResp = Integer.valueOf(mFrecRespView.getText().toString());
						if (frecResp < 10 || frecResp > 70)
							mFrecRespView.setError(getString(R.string.wrongFrecResp, 10, 70));
					}
					mFrecRespView.requestFocus();
				}
				catch(Exception e){
					mFrecRespView.setError(e.toString());
					mFrecRespView.requestFocus();
					return;
				}
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
			case DATE_FIRST_SINT:
				final DatePickerDialog dpDs = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
						fecIniPrimerSintoma = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
						inputFecIniPrimerSintoma.setText(fecIniPrimerSintoma);
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
				dpDs.getDatePicker().setMinDate(minDate.getMillis());
				dpDs.getDatePicker().setMaxDate(maxDate.getMillis());
				dpDs.show();
				break;
			case TIME_DIALOG_ID:
				final TimePickerDialog tmD = new TimePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker picker, int hourOfDay, int minute) {
						horaProbableVisita = (picker != null) ? String.valueOf(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + String.valueOf(minute < 10 ? "0" + minute : minute) : null;
						inputHoraProbableVisita.setText(horaProbableVisita);
						horaDefectoTimer = hourOfDay;
						minutoDefectoTimer = minute;
					}
				}, horaDefectoTimer, minutoDefectoTimer, true);
				tmD.show();
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
        }
        else if (visita == null || visita.isEmpty()){
            Toast.makeText(getActivity(), getActivity().getString( R.string.wrongSelect,getActivity().getString(R.string.visit)),Toast.LENGTH_LONG).show();
            spinVisita.requestFocus();
            return false;
        }
        else if (horaProbableVisita == null || horaProbableVisita.equals("")){
        	inputHoraProbableVisita.setError(getActivity().getString(R.string.wrongHoraProbableVisita));
        	inputHoraProbableVisita.requestFocus();
            return false;
        }
        else if (expCs == null || expCs.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString( R.string.wrongExpCS),Toast.LENGTH_LONG).show();
        	spinExpCS.requestFocus();
            return false;
        }
		else if ((fecIniPrimerSintoma!= null && !fecIniPrimerSintoma.isEmpty()) &&  (primerSintoma == null || primerSintoma.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString( R.string.wrongSelect,getActivity().getString(R.string.primerSintoma)),Toast.LENGTH_LONG).show();
			inputPrimerSintoma.requestFocus();
			return false;
		}
        else if (mTempView.getText().toString().equals("") || (temp < 36 || temp > 41)){
        	mTempView.setError(getActivity().getString(R.string.wrongTemp, 36, 41));
        	mTempView.requestFocus();
            return false;
        }
		else if (mSaturacionView.getText().toString().equals("") || (saturacion < 0 || saturacion > 100)){
			mSaturacionView.setError(getActivity().getString(R.string.wrongSaturation, 0, 100));
			mSaturacionView.requestFocus();
			return false;
		}
		else if (mFrecRespView.getText().toString().equals("") || (frecResp < 10 || frecResp > 70)){
			mFrecRespView.setError(getActivity().getString( R.string.wrongFrecResp, 10, 70));
			mFrecRespView.requestFocus();
			return false;
		}
        else if(dVis.before(mParticipanteCaso.getCodigoCaso().getFechaIngreso())){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrong_fecha_visita),Toast.LENGTH_LONG).show();
        	return false;
        }
        else if (dVis.after(calLimiteFecVisita.getTime())){//si la fecha de visita es posterior a los 12 dias después de la fecha de inicio no permitir registro
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrong_fecha_visita2),Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            if (visita != null || !visita.isEmpty()){
                try {
                    estudiosAdapter.open();
                    VisitaSeguimientoCasoCovid19 visitaExiste = estudiosAdapter.getVisitaSeguimientoCasoCovid19(Covid19DBConstants.codigoCasoParticipante+"='"+mParticipanteCaso.getCodigoCasoParticipante()+"' and "+ Covid19DBConstants.visita + "='"+visita+"'", null);
                    if (visitaExiste!=null) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.duplicateVisit), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }catch (Exception e){
                    Log.e(TAG, e.getLocalizedMessage(), e);
                }finally {
                    estudiosAdapter.close();
                }
            }
        	vsc.setCodigoCasoVisita(infoMovil.getId());
        	vsc.setCodigoParticipanteCaso(mParticipanteCaso);
        	vsc.setVisita(visita);
        	vsc.setHoraProbableVisita(horaProbableVisita);
        	vsc.setExpCS(expCs);
        	vsc.setTemp(temp);
        	vsc.setSaturacionO2(saturacion);
        	vsc.setFrecResp(frecResp);
        	if (fecIniPrimerSintoma!=null && !fecIniPrimerSintoma.isEmpty()) vsc.setFecIniPrimerSintoma(formatter.parse(fecIniPrimerSintoma));
        	vsc.setPrimerSintoma(primerSintoma);
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
                if (!mParticipanteCaso.getParticipante().getProcesos().getEstudio().contains(Constants.T_COVID19)) {
             		mVisitas = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_VIS_SEG' and "+CatalogosDBConstants.messageKey + " = 'COVID_CAT_VIS_SEG_I'" , CatalogosDBConstants.order);
					visita = mVisitas.get(0).getCatKey();
				}else {
					esTCovid = true;
					mVisitas = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_VIS_SEG' and "+CatalogosDBConstants.catKey + " in ('1','2','3','4','5','6')" , CatalogosDBConstants.order);
				}
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
			mTitleView.setText(getActivity().getString(R.string.new_visit));

			//mVisita.setText(mVisitas.get(0).getSpanish());
            if (esTCovid) mVisitas.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mVisitas);
            ArrayAdapter<MessageResource> dataAdapterVisit = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mVisitas);
            dataAdapterVisit.setDropDownViewResource(R.layout.spinner_item);
            spinVisita.setAdapter(dataAdapterVisit);

			mCatalogoSn.add(new MessageResource("",0,getActivity().getString(R.string.select)));
			Collections.sort(mCatalogoSn);
			ArrayAdapter<MessageResource> dataAdapter = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSn);
			dataAdapter.setDropDownViewResource(R.layout.spinner_item);
			spinExpCS.setAdapter(dataAdapter);
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
				estudiosAdapter.crearVisitaSeguimientoCasoCovid19(vsc);
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
			Bundle arguments = new Bundle();
			Intent i;
			arguments.putSerializable(Constants.PARTICIPANTE , mParticipanteCaso);
			i = new Intent(getActivity(),
					ListaVisitasParticipanteCasoCovid19Activity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        i.putExtras(arguments);
			startActivity(i);
			getActivity().finish();
		}

	}	
    
}
