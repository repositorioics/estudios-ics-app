package ni.org.ics.estudios.appmovil.cohortefamilia.activities.fragments;


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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaSensoresCasoActivity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.CasaCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.SensorCaso;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.*;

public class RetirarSensoresFragment extends Fragment {

	private static final String TAG = "RetirarSensoresFragment";
	protected static final int DATE_DIALOG_ID = 101;
	protected static final int TIME_DIALOG_ID = 102;

	private EstudiosAdapter estudiosAdapter;
	private CasaCohorteFamiliaCaso mCasaCaso;
	private List<SensorCaso> sensorCasoList = new ArrayList<SensorCaso>();
	private TextView mTitleView;
	private TextView inputObservacionRetiro;
	private TextView inputHoraProbableVisita;
	protected ImageButton mButtonChangeTime;
	private Button mSaveView;

	private int horaDefectoTimer = 7;
	private int minutoDefectoTimer = 0;

	private String horaRetiroSensor;
    private String observacionRetiro;

	private DeviceInfo infoMovil;
	private String username;
	private SharedPreferences settings;


    public static RetirarSensoresFragment create() {
        RetirarSensoresFragment fragment = new RetirarSensoresFragment();
        return fragment;
    }

    public RetirarSensoresFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
        mCasaCaso = (CasaCohorteFamiliaCaso) getActivity().getIntent().getExtras().getSerializable(Constants.CASA);
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
        View rootView = inflater.inflate(R.layout.fragment_retirar_sensores, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));

        inputHoraProbableVisita = (TextView) rootView.findViewById(R.id.inputHoraRetiro);
        inputHoraProbableVisita.setEnabled(false);

        inputObservacionRetiro = ((TextView) rootView.findViewById(R.id.inputObservacionRetiro));
        inputObservacionRetiro.addTextChangedListener(new TextWatcher() {
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
                    observacionRetiro = inputObservacionRetiro.getText().toString();
                }
                catch(Exception e){
                    inputObservacionRetiro.setError(e.toString());
                    inputObservacionRetiro.requestFocus();
                    return;
                }
            }

        });
        mButtonChangeTime = (ImageButton) rootView.findViewById(R.id.changetime_button);
        mButtonChangeTime.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(TIME_DIALOG_ID);
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
		case TIME_DIALOG_ID:
			final TimePickerDialog tmD = new TimePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker picker, int hourOfDay, int minute) {
                    horaRetiroSensor = (picker != null) ? String.valueOf(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + String.valueOf(minute < 10 ? "0" + minute : minute) : null;
                    inputHoraProbableVisita.setText(horaRetiroSensor);
					horaDefectoTimer = hourOfDay;
					minutoDefectoTimer = minute;
                }
            }, horaDefectoTimer, minutoDefectoTimer,true);
			tmD.show();	
		default:
			break;
		}
	}
    
    private boolean validarEntrada() {
        //Valida la entrada

        if (horaRetiroSensor == null || horaRetiroSensor.equals("")){
        	inputHoraProbableVisita.setError(getActivity().getString(R.string.wrongHoraProbableVisita));
        	inputHoraProbableVisita.requestFocus();
            return false;
        }
        else{
            Date fechaRetiro = new Date();
        	for(int i=0; i< sensorCasoList.size(); i++){
                sensorCasoList.get(i).setFechaRetiro(fechaRetiro);
                sensorCasoList.get(i).setHoraRetiro(horaRetiroSensor);
                sensorCasoList.get(i).setObservacionRetiro(observacionRetiro);
                sensorCasoList.get(i).setRecordDate(new Date());
                sensorCasoList.get(i).setRecordUser(username);
                sensorCasoList.get(i).setDeviceid(infoMovil.getDeviceId());
                sensorCasoList.get(i).setEstado('0');
                sensorCasoList.get(i).setPasive('0');
            }

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
				sensorCasoList = estudiosAdapter.getSensoresCasos(CasosDBConstants.codigoCaso + "='"+mCasaCaso.getCodigoCaso()+"' and "+CasosDBConstants.sensorSN+"='1' and "+CasosDBConstants.horaRetiro+" IS NULL and " + MainDBConstants.pasive + " ='0'", null);
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
			//mTitleView.setText(getActivity().getString(R.string.new_visit));
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
                for(SensorCaso sensorCaso: sensorCasoList) {
                    estudiosAdapter.editarSensorCaso(sensorCaso);
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
			Bundle arguments = new Bundle();
			Intent i;
			arguments.putSerializable(Constants.CASA , mCasaCaso);
			i = new Intent(getActivity(),
					ListaSensoresCasoActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        i.putExtras(arguments);
			startActivity(i);
			getActivity().finish();
		}

	}	
    
}
