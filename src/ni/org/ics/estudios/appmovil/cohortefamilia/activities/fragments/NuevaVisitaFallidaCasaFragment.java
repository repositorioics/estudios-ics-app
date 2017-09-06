package ni.org.ics.estudios.appmovil.cohortefamilia.activities.fragments;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaParticipantesCasosActivity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.CasaCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaFallidaCaso;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaVisitaFallidaCasaFragment extends Fragment {
	
	private static final String TAG = "NuevaVisitaSeguimientoFragment";
	protected static final int DATE_DIALOG_ID = 101;
	protected static final int TIME_DIALOG_ID = 102;
	
	private EstudiosAdapter estudiosAdapter;
	private CasaCohorteFamiliaCaso casaCaso;
	private List<ParticipanteCohorteFamiliaCaso> mParticipanteCohorteFamiliaCasos = new ArrayList<ParticipanteCohorteFamiliaCaso>();
	private List<MessageResource> mCatalogoRazonNoVisita;
	private TextView mTitleView;
	private TextView mNameView;
	private TextView textNameView;
	private TextView inputFechaVisita;
	protected ImageButton mButtonChangeDate;
	private Spinner spinRazonVisitaFallida;
	private TextView textOtraRazon;
	private EditText inputOtraRazon;
	private Button mSaveView;
	
	
	private String fechaVisita;
	private String razonVisitaFallida;
	private String otraRazon;
	final Calendar c = Calendar.getInstance();
	
	private DeviceInfo infoMovil;
	private String username;
	private SharedPreferences settings;
	

    public static NuevaVisitaFallidaCasaFragment create() {
        NuevaVisitaFallidaCasaFragment fragment = new NuevaVisitaFallidaCasaFragment();
        return fragment;
    }

    public NuevaVisitaFallidaCasaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
		casaCaso = (CasaCohorteFamiliaCaso) getActivity().getIntent().getExtras().getSerializable(Constants.CASA);
        infoMovil = new DeviceInfo(getActivity());
        settings =
				PreferenceManager.getDefaultSharedPreferences(getActivity());
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
        new FetchCatalogosTask().execute(casaCaso.getCodigoCaso());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nueva_visita_fallida, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((TextView) rootView.findViewById(R.id.inputCodigoParticipanteCaso));
        mNameView.setVisibility(View.GONE);
        textNameView = ((TextView) rootView.findViewById(R.id.textCodigoParticipanteCaso));
        textNameView.setVisibility(View.GONE);
        inputFechaVisita = (TextView) rootView.findViewById(R.id.inputFechaVisita);
        inputFechaVisita.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)<10? "0"+c.get(Calendar.DAY_OF_MONTH):c.get(Calendar.DAY_OF_MONTH))+"/"+
        							String.valueOf((c.get(Calendar.MONTH)+1)<10? "0"+(c.get(Calendar.MONTH)+1):(c.get(Calendar.MONTH)+1))+"/"+String.valueOf(c.get(Calendar.YEAR))+" "+
        							String.valueOf(c.get(Calendar.HOUR_OF_DAY)<10? "0"+c.get(Calendar.HOUR_OF_DAY):c.get(Calendar.HOUR_OF_DAY))+":"+
        							String.valueOf(c.get(Calendar.MINUTE)<10? "0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE)));
        inputFechaVisita.setEnabled(false);
        fechaVisita = inputFechaVisita.getText().toString();
        mButtonChangeDate = (ImageButton) rootView.findViewById(R.id.changedate_button);
        mButtonChangeDate.setEnabled(false);
        spinRazonVisitaFallida = (Spinner) rootView.findViewById(R.id.spinRazonVisitaFallida);
		spinRazonVisitaFallida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	razonVisitaFallida = mr.getCatKey();
	        	if(razonVisitaFallida.equals("9")){
	        		textOtraRazon.setVisibility(View.VISIBLE);
	        		inputOtraRazon.setVisibility(View.VISIBLE);
	        	}
	        	else{
	        		textOtraRazon.setVisibility(View.GONE);
	        		inputOtraRazon.setVisibility(View.GONE);
	        		inputOtraRazon.setText("");
	        		otraRazon = null;
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
		textOtraRazon = ((TextView) rootView.findViewById(R.id.textOtraRazon));
		textOtraRazon.setVisibility(View.GONE);
		inputOtraRazon = ((EditText) rootView.findViewById(R.id.inputOtraRazon));
		inputOtraRazon.setVisibility(View.GONE);
		inputOtraRazon.addTextChangedListener(new TextWatcher() {
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
				otraRazon = inputOtraRazon.getText().toString();
				
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
    
    
    private boolean validarEntrada() {
        //Valida la entrada
        if (fechaVisita == null || fechaVisita.equals("")){
        	inputFechaVisita.setError(getActivity().getString(R.string.wrong_fecha_visita));
        	inputFechaVisita.requestFocus();
            return false;
        }
        else if (razonVisitaFallida == null || razonVisitaFallida.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString( R.string.wrongSelect,getActivity().getString(R.string.razonVisitaFallida)),Toast.LENGTH_LONG).show();
        	spinRazonVisitaFallida.requestFocus();
            return false;
        }
        else if (razonVisitaFallida.equals("9") && inputOtraRazon.getText().toString().equals("")){
        	inputOtraRazon.setError(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.otraRazon)));
        	inputOtraRazon.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }
    
    private class FetchCatalogosTask extends AsyncTask<String, Void, String> {
    	private String codigoCaso = null;
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
				codigoCaso = values[0];
				estudiosAdapter.open();
				mParticipanteCohorteFamiliaCasos = estudiosAdapter.getParticipanteCohorteFamiliaCasos(CasosDBConstants.codigoCaso +" = '" + codigoCaso +"'", MainDBConstants.participante);
				mCatalogoRazonNoVisita = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_VISITA_NO_C'", CatalogosDBConstants.order);
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
			mTitleView.setText(getActivity().getString(R.string.new_fail_visit));
			mCatalogoRazonNoVisita.add(new MessageResource("",0,getActivity().getString(R.string.select)));
			Collections.sort(mCatalogoRazonNoVisita);
			ArrayAdapter<MessageResource> dataAdapter = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoRazonNoVisita);
			dataAdapter.setDropDownViewResource(R.layout.spinner_item);
			spinRazonVisitaFallida.setAdapter(dataAdapter);
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
				for(ParticipanteCohorteFamiliaCaso part: mParticipanteCohorteFamiliaCasos){
					VisitaFallidaCaso vfc = new VisitaFallidaCaso();
					vfc.setCodigoFallaVisita(infoMovil.getId());
		        	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		        	Date dVis = null;
					try {
						dVis = formatter.parse(fechaVisita);
					} catch (ParseException e) {
						Toast.makeText(getActivity(), e.toString() ,Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
		        	vfc.setFechaVisita(dVis);
		        	vfc.setRazonVisitaFallida(razonVisitaFallida);
		        	vfc.setOtraRazon(otraRazon);
		        	vfc.setRecordDate(new Date());
		        	vfc.setRecordUser(username);
		        	vfc.setDeviceid(infoMovil.getDeviceId());
		        	vfc.setEstado('0');
		        	vfc.setPasive('0');
					vfc.setCodigoParticipanteCaso(part);
					estudiosAdapter.crearVisitaFallidaCaso(vfc);
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
			arguments.putSerializable(Constants.CASA , casaCaso);
			i = new Intent(getActivity(),
					ListaParticipantesCasosActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        i.putExtras(arguments);
			startActivity(i);
			getActivity().finish();
		}

	}	
    
}
