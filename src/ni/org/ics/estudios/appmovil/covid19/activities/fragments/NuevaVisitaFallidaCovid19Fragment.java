package ni.org.ics.estudios.appmovil.covid19.activities.fragments;


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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaVisitasFallidasCasosActivity;
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaVisitasFallidasCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaFallidaCaso;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaFallidaCasoCovid19;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NuevaVisitaFallidaCovid19Fragment extends Fragment {

	private static final String TAG = "NuevaVisitaFallidaCovid19Fragment";

	private EstudiosAdapter estudiosAdapter;
	private ParticipanteCasoCovid19 mParticipanteCaso;
	private VisitaFallidaCasoCovid19 vfc = new VisitaFallidaCasoCovid19();
	private List<MessageResource> mCatalogoRazonNoVisita;
    private List<MessageResource> mVisitas;
	private TextView mTitleView;
	private TextView mNameView;
	private TextView inputFechaVisita;
	protected ImageButton mButtonChangeDate;
    private Spinner spinVisita;
	private Spinner spinRazonVisitaFallida;
	private TextView textOtraRazon;
	private EditText inputOtraRazon;
	private Button mSaveView;


	private String fechaVisita;
	private String razonVisitaFallida;
	private String otraRazon;
    private String visita;
	final Calendar c = Calendar.getInstance();

	private DeviceInfo infoMovil;
	private String username;
	private SharedPreferences settings;


    public static NuevaVisitaFallidaCovid19Fragment create() {
        NuevaVisitaFallidaCovid19Fragment fragment = new NuevaVisitaFallidaCovid19Fragment();
        return fragment;
    }

    public NuevaVisitaFallidaCovid19Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
        mParticipanteCaso = (ParticipanteCasoCovid19) getActivity().getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
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
        View rootView = inflater.inflate(R.layout.fragment_nueva_visita_fallida, container, false);
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
        mButtonChangeDate.setEnabled(false);
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
        else if (visita == null || visita.equals("")){
            Toast.makeText(getActivity(), getActivity().getString( R.string.wrongSelect,getActivity().getString(R.string.visit)),Toast.LENGTH_LONG).show();
            spinVisita.requestFocus();
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
        	
        	vfc.setCodigoFallaVisita(infoMovil.getId());
        	vfc.setCodigoParticipanteCaso(mParticipanteCaso);
        	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        	Date dVis = null;
			try {
				dVis = formatter.parse(fechaVisita);
			} catch (ParseException e) {
				Toast.makeText(getActivity(), e.toString() ,Toast.LENGTH_LONG).show();
				e.printStackTrace();
				return false;
			}
        	vfc.setFechaVisita(dVis);
        	vfc.setRazonVisitaFallida(razonVisitaFallida);
        	vfc.setOtraRazon(otraRazon);
            vfc.setVisita(visita);
        	vfc.setRecordDate(new Date());
        	vfc.setRecordUser(username);
        	vfc.setDeviceid(infoMovil.getDeviceId());
        	vfc.setEstado('0');
        	vfc.setPasive('0');
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
				mCatalogoRazonNoVisita = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_VISITA_NO_P'", CatalogosDBConstants.order);
				mVisitas = estudiosAdapter.getMessageResources(CatalogosDBConstants.messageKey + "='COVID_CAT_VIS_SEG_I' or "+CatalogosDBConstants.messageKey + " = 'COVID_CAT_VIS_SEG_F'" , CatalogosDBConstants.order);
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

            mVisitas.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mVisitas);
            ArrayAdapter<MessageResource> dataAdapterVisit = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mVisitas);
            dataAdapterVisit.setDropDownViewResource(R.layout.spinner_item);
            spinVisita.setAdapter(dataAdapterVisit);

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
				estudiosAdapter.crearVisitaFallidaCasoCovid19(vfc);
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
					ListaVisitasFallidasCasoCovid19Activity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        i.putExtras(arguments);
			startActivity(i);
			getActivity().finish();
		}

	}	
    
}
