package ni.org.ics.estudios.appmovil.cohortefamilia.activities.fragments;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaVisitasParticipantesCasosActivity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCaso;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NuevaVisitaSeguimientoFragment extends Fragment {
	
	private static final String TAG = "NuevaVisitaSeguimientoFragment";
	protected static final int DATE_DIALOG_ID = 101;
	protected static final int TIME_DIALOG_ID = 102;
	
	private EstudiosAdapter estudiosAdapter;
	private ParticipanteCohorteFamiliaCaso mParticipanteCaso;
	private VisitaSeguimientoCaso vsc = new VisitaSeguimientoCaso();
	private List<MessageResource> mCatalogoSn;
	private TextView mTitleView;
	private TextView mNameView;
	private TextView inputFechaVisita;
	protected ImageButton mButtonChangeDate;
	private TextView inputHoraProbableVisita;
	protected ImageButton mButtonChangeTime;
	private Spinner spinExpCS;
	private TextView mTempView;
	private Button mSaveView;
	
	
	private String fechaVisita;
	private Integer visita;
	private String horaProbableVisita;
	private String expCs;
	private float temp;
	final Calendar c = Calendar.getInstance();
	
	private DeviceInfo infoMovil;
	private String username;
	private SharedPreferences settings;
	

    public static NuevaVisitaSeguimientoFragment create() {
        NuevaVisitaSeguimientoFragment fragment = new NuevaVisitaSeguimientoFragment();
        return fragment;
    }

    public NuevaVisitaSeguimientoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
        mParticipanteCaso = (ParticipanteCohorteFamiliaCaso) getActivity().getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
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
        View rootView = inflater.inflate(R.layout.fragment_nueva_visita, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((TextView) rootView.findViewById(R.id.inputCodigoParticipanteCaso));
        mNameView.setText(mParticipanteCaso.getParticipante().getParticipante().getNombreCompleto());
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
					temp = Float.valueOf(mTempView.getText().toString());
					if (temp<34 || temp>44) mTempView.setError(getString(R.string.wrongTemp,34,44));
					mTempView.requestFocus();
				}
				catch(Exception e){
					mTempView.setError(e.toString());
					mTempView.requestFocus();
					return;
				}
			}

		});
		mSaveView = (Button) getActivity().findViewById(R.id.save_button);
		mSaveView.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				if(validarEntrada()){
                    new SaveDataTask().execute();
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
		switch(dialog){
		case DATE_DIALOG_ID:
			final DatePickerDialog dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnDateSetListener() {
		        @Override
		        public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
		        	fechaVisita = (picker != null) ? String.valueOf(dayOfMonth<10? "0"+dayOfMonth:dayOfMonth)+"/"+String.valueOf((monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1))+"/"+String.valueOf(year) : null;
                	inputFechaVisita.setText(fechaVisita);
		        }
		    },c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
			DateMidnight minDate = DateMidnight.now().minusDays(3);
			DateMidnight maxDate = DateMidnight.now();
			dpD.getDatePicker().setMinDate(minDate.getMillis());
			dpD.getDatePicker().setMaxDate(maxDate.getMillis());
			dpD.show();
			break;
		case TIME_DIALOG_ID:
			final TimePickerDialog tmD = new TimePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnTimeSetListener() {
		        @Override
		        public void onTimeSet(TimePicker picker, int hourOfDay, int minute) {
		        	if(hourOfDay<7||hourOfDay>19){
		        		horaProbableVisita = null;
		        		inputHoraProbableVisita.setText("");
		        		Toast.makeText(getActivity(),getActivity().getString(R.string.wrongTime) , Toast.LENGTH_LONG).show();
		        	}
		        	else{
		        		horaProbableVisita= (picker != null) ? String.valueOf(hourOfDay<10? "0"+hourOfDay:hourOfDay)+":"+String.valueOf(minute<10?"0"+minute:minute) : null;
		        		inputHoraProbableVisita.setText(horaProbableVisita);
		        	}
		        }
		    }, 7, 0,true);
			tmD.show();	
		default:
			break;
		}
	}
    
    private boolean validarEntrada() {
        //Valida la entrada
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	Date dVis = null;
		try {
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
        else if (visita == null || visita < 1){
            Toast.makeText(getActivity(), getActivity().getString( R.string.error),Toast.LENGTH_LONG).show();
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
        else if (mTempView.getText().toString().equals("")){
        	mTempView.setError(getActivity().getString(R.string.wrongTemp,34,44));
        	mTempView.requestFocus();
            return false;
        }
        else if(dVis.before(mParticipanteCaso.getCodigoCaso().getFechaInicio())){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrong_fecha_visita),Toast.LENGTH_LONG).show();
        	return false;
        }
        else{
        	
        	vsc.setCodigoCasoVisita(infoMovil.getId());
        	vsc.setCodigoParticipanteCaso(mParticipanteCaso);
        	vsc.setFechaVisita(dVis);
        	vsc.setVisita(visita.toString());
        	vsc.setHoraProbableVisita(horaProbableVisita);
        	vsc.setExpCS(expCs);
        	vsc.setTemp(temp);
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
				visita = estudiosAdapter.selectUltimaVisitaSeguimientoCaso(mParticipanteCaso.getCodigoCasoParticipante())+1;
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
			mTitleView.setText(getActivity().getString(R.string.new_visit)+ " " + visita);
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
				estudiosAdapter.crearVisitaSeguimientoCaso(vsc);
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
					ListaVisitasParticipantesCasosActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        i.putExtras(arguments);
			startActivity(i);
			getActivity().finish();
		}

	}	
    
}
