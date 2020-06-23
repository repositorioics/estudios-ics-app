package ni.org.ics.estudios.appmovil.covid19.activities.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import ni.org.ics.estudios.appmovil.covid19.activities.MenuVisitaCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.covid19.DatosAislamientoVisitaCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaSeguimientoCasoCovid19;
import ni.org.ics.estudios.appmovil.multiselector.gui.MultiSpinner;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NuevoDAislamientoVisitaCasoCovid19Fragment extends Fragment {

	private static final String TAG = "NuevoSintomaVisitaCasoCovid19Fragment";

	private EstudiosAdapter estudiosAdapter;
	//Viene de la llamada
	private VisitaSeguimientoCasoCovid19 mVisitaSeguimientoCaso;
	//Objeto que se va a hacer
	private DatosAislamientoVisitaCasoCovid19 mDatosAislamiento = new DatosAislamientoVisitaCasoCovid19();
	//Catalogos
	private List<MessageResource> mCatalogoSNNR;
	private List<String> mLugares = new ArrayList<String>();
	private List<String> mParticipantes = new ArrayList<String>();
	//Widgets en el View
	private TextView mTitleView;
	private EditText mNameView;
	private EditText inputFechaDato;
	private TextView textDormir;
	private TextView textBanio;
	private TextView textAlejadoFamilia;
	private TextView textTieneContacto;
	private TextView textConQuienTC;
	private TextView textLugares;
	private TextView textOtrosLugares;
	private Spinner spinAislado;
	private Spinner spinDormir;
	private Spinner spinBanio;
	private Spinner spinAlejadoFamilia;
	private Spinner spinTieneContacto;
	private MultiSpinner spinConQuienTC;
	private MultiSpinner spinLugares;
	private EditText inputOtrosLugares;
	private TextView textSiAislado;


	private Button mSaveView;

	private String fechaDato;
	private String aislado;
	private String dormirSoloComparte;
	private String banioPropioComparte;
	private String alejadoFamilia;
	private String tieneContacto;
	private String conQuienTieneContacto;
	private String lugares;
	private String otrosLugares;

	//Variables donde se captura la entrada de datos

	final Calendar c = Calendar.getInstance();
	//Para el id
	private DeviceInfo infoMovil;
	private String username;
	private SharedPreferences settings;


    public static NuevoDAislamientoVisitaCasoCovid19Fragment create() {
        NuevoDAislamientoVisitaCasoCovid19Fragment fragment = new NuevoDAislamientoVisitaCasoCovid19Fragment();
        return fragment;
    }

    public NuevoDAislamientoVisitaCasoCovid19Fragment() {
    	
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
        View rootView = inflater.inflate(R.layout.fragment_nuevo_datoislamiento_covid19, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((EditText) rootView.findViewById(R.id.inputCodigoParticipanteCaso));
        mNameView.setText(mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getParticipante().getNombreCompleto());
        mNameView.setEnabled(false);
		inputFechaDato = (EditText) rootView.findViewById(R.id.inputFechaDato);
        if (mVisitaSeguimientoCaso.getFechaVisita().compareTo(new Date())<0){
            c.setTime(mVisitaSeguimientoCaso.getFechaVisita());
        }
		inputFechaDato.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)<10? "0"+c.get(Calendar.DAY_OF_MONTH):c.get(Calendar.DAY_OF_MONTH))+"/"+
        							String.valueOf((c.get(Calendar.MONTH)+1)<10? "0"+(c.get(Calendar.MONTH)+1):(c.get(Calendar.MONTH)+1))+"/"+String.valueOf(c.get(Calendar.YEAR)));
        fechaDato = inputFechaDato.getText().toString();
        spinAislado = (Spinner) rootView.findViewById(R.id.spinAislado);
		spinAislado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	aislado = mr.getCatKey();
	        	if(aislado.equals(Constants.YESKEYSND)){
					textSiAislado.setVisibility(View.VISIBLE);
	        		spinDormir.setVisibility(View.VISIBLE);
					textDormir.setVisibility(View.VISIBLE);
					spinBanio.setVisibility(View.VISIBLE);
					textBanio.setVisibility(View.VISIBLE);
					spinAlejadoFamilia.setVisibility(View.VISIBLE);
					textAlejadoFamilia.setVisibility(View.VISIBLE);
					spinTieneContacto.setVisibility(View.VISIBLE);
					textTieneContacto.setVisibility(View.VISIBLE);
					spinLugares.setVisibility(View.VISIBLE);
					textLugares.setVisibility(View.VISIBLE);
					spinConQuienTC.setVisibility(View.GONE);
					textConQuienTC.setVisibility(View.GONE);
					inputOtrosLugares.setVisibility(View.GONE);
					textOtrosLugares.setVisibility(View.GONE);
	        	}
	        	else{
					textSiAislado.setVisibility(View.GONE);
					spinDormir.setVisibility(View.GONE);
					textDormir.setVisibility(View.GONE);
					spinBanio.setVisibility(View.GONE);
					textBanio.setVisibility(View.GONE);
					spinAlejadoFamilia.setVisibility(View.GONE);
					textAlejadoFamilia.setVisibility(View.GONE);
					spinTieneContacto.setVisibility(View.GONE);
					textTieneContacto.setVisibility(View.GONE);
					spinLugares.setVisibility(View.GONE);
					textLugares.setVisibility(View.GONE);
					spinConQuienTC.setVisibility(View.GONE);
					textConQuienTC.setVisibility(View.GONE);
					inputOtrosLugares.setVisibility(View.GONE);
					textOtrosLugares.setVisibility(View.GONE);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });

		textSiAislado = (TextView) rootView.findViewById(R.id.textSiAislado);
		textSiAislado.setVisibility(View.GONE);
		textDormir = (TextView) rootView.findViewById(R.id.textDormir);
		textDormir.setVisibility(View.GONE);
		spinDormir = (Spinner) rootView.findViewById(R.id.spinDormir);
		spinDormir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> spinner, View v,
	                int arg2, long arg3) {
	        	MessageResource mr = (MessageResource) spinner.getSelectedItem();
	        	dormirSoloComparte = mr.getCatKey();
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	    });
		spinDormir.setVisibility(View.GONE);

		textBanio = (TextView) rootView.findViewById(R.id.textBanio);
		textBanio.setVisibility(View.GONE);
		spinBanio = (Spinner) rootView.findViewById(R.id.spinBanio);
		spinBanio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				banioPropioComparte = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinBanio.setVisibility(View.GONE);

		textAlejadoFamilia = (TextView) rootView.findViewById(R.id.textAlejadoFamilia);
		textAlejadoFamilia.setVisibility(View.GONE);
		spinAlejadoFamilia = (Spinner) rootView.findViewById(R.id.spinAlejadoFamilia);
		spinAlejadoFamilia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				alejadoFamilia = mr.getCatKey();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinAlejadoFamilia.setVisibility(View.GONE);

		textTieneContacto = (TextView) rootView.findViewById(R.id.textTieneContacto);
		textTieneContacto.setVisibility(View.GONE);
		spinTieneContacto = (Spinner) rootView.findViewById(R.id.spinTieneContacto);
		spinTieneContacto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				tieneContacto = mr.getCatKey();
				if (tieneContacto.equals(Constants.YESKEYSND) && mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getParticipante().getProcesos().getEstudio().contains(Constants.T_COVID19)) {
					textConQuienTC.setVisibility(View.VISIBLE);
					spinConQuienTC.setVisibility(View.VISIBLE);
				} else {
					textConQuienTC.setVisibility(View.GONE);
					spinConQuienTC.setVisibility(View.GONE);
					conQuienTieneContacto = null;
				}

			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinTieneContacto.setVisibility(View.GONE);

		textConQuienTC = (TextView) rootView.findViewById(R.id.textConQuienTC);
		textConQuienTC.setVisibility(View.GONE);
		spinConQuienTC = (MultiSpinner) rootView.findViewById(R.id.spinConQuienTC);
		spinConQuienTC.setVisibility(View.GONE);

		textLugares = (TextView) rootView.findViewById(R.id.textLugares);
		textLugares.setVisibility(View.GONE);
		spinLugares = (MultiSpinner) rootView.findViewById(R.id.spinLugares);

		textOtrosLugares = (TextView) rootView.findViewById(R.id.textOtrosLugares);
		textOtrosLugares.setVisibility(View.GONE);
		inputOtrosLugares = (EditText) rootView.findViewById(R.id.inputOtrosLugares);
		inputOtrosLugares.addTextChangedListener(new TextWatcher() {
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
					otrosLugares = inputOtrosLugares.getText().toString();
				}
				catch(Exception e){
					inputOtrosLugares.setError(e.toString());
					inputOtrosLugares.requestFocus();
					return;
				}
			}
		});
		inputOtrosLugares.setVisibility(View.GONE);

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
    
    private boolean validarEntrada() throws ParseException {
        //Valida la entrada
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if (fechaDato == null || fechaDato.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fechaDatoAisla)),Toast.LENGTH_LONG).show();
        	inputFechaDato.setError(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fechaDatoAisla)));
            return false;
        }
        else if (aislado == null || aislado.equals("")){
        	Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.aislado)),Toast.LENGTH_LONG).show();
            return false;
        }
		else if (aislado.equals(Constants.YESKEYSND) && (dormirSoloComparte == null || dormirSoloComparte.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.dormirSoloComparte)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (aislado.equals(Constants.YESKEYSND) && (banioPropioComparte == null || banioPropioComparte.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.banioPropioComparte)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (aislado.equals(Constants.YESKEYSND) && (alejadoFamilia == null || alejadoFamilia.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.alejadoFamilia)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (aislado.equals(Constants.YESKEYSND) && (tieneContacto == null || tieneContacto.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tieneContacto)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (aislado.equals(Constants.YESKEYSND) && (tieneContacto.equals(Constants.YESKEYSND) && mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getParticipante().getProcesos().getEstudio().contains(Constants.T_COVID19))
				&& (conQuienTieneContacto == null || conQuienTieneContacto.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.conQuienTieneContacto)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (aislado.equals(Constants.YESKEYSND) && (lugares == null || lugares.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.lugares)),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (aislado.equals(Constants.YESKEYSND) && lugares.toLowerCase().contains("otros") && (otrosLugares == null || otrosLugares.equals(""))){
			Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.otrosLugares)),Toast.LENGTH_LONG).show();
			return false;
		}
        else{
        	mDatosAislamiento.setCodigoAislamiento(infoMovil.getId());
        	mDatosAislamiento.setCodigoCasoVisita(mVisitaSeguimientoCaso);
        	mDatosAislamiento.setFechaDato(formatter.parse(fechaDato));
        	mDatosAislamiento.setAislado(aislado);
        	mDatosAislamiento.setDormirSoloComparte(dormirSoloComparte);
        	mDatosAislamiento.setBanioPropioComparte(banioPropioComparte);
        	mDatosAislamiento.setAlejadoFamilia(alejadoFamilia);
        	mDatosAislamiento.setTieneContacto(tieneContacto);
        	estudiosAdapter.open();
        	mDatosAislamiento.setConQuienTieneContacto(conQuienTieneContacto);
			if (lugares!=null && !lugares.isEmpty()) {
				String keysCriterios = "";
				lugares = lugares.replaceAll(",", "','");
				List<MessageResource> catFR = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + lugares + "') and "
						+ CatalogosDBConstants.catRoot + "='COVID_LUGARES_AVC'", null);
				for (MessageResource ms : catFR) {
					keysCriterios += ms.getCatKey() + ",";
				}
				if (!keysCriterios.isEmpty())
					keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
				mDatosAislamiento.setLugares(keysCriterios);
			}
			estudiosAdapter.close();
			mDatosAislamiento.setOtrosLugares(otrosLugares);
			mDatosAislamiento.setRecordDate(new Date());
			mDatosAislamiento.setRecordUser(username);
			mDatosAislamiento.setDeviceid(infoMovil.getDeviceId());
			mDatosAislamiento.setEstado('0');
			mDatosAislamiento.setPasive('0');
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
				mCatalogoSNNR = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_SNNR'", CatalogosDBConstants.order);
				mLugares = Arrays.asList(estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='COVID_LUGARES_AVC'", CatalogosDBConstants.order));
				List<ParticipanteCasoCovid19> participanteCasoCovid19List = estudiosAdapter.getParticipantesCasosCovid19(Covid19DBConstants.codigoCaso+"='"+mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getCodigoCaso().getCodigoCaso()+"' and "+ MainDBConstants.pasive + "='0'", null);
				estudiosAdapter.close();
				for(ParticipanteCasoCovid19 p: participanteCasoCovid19List){
					if (!mVisitaSeguimientoCaso.getCodigoParticipanteCaso().getParticipante().getCodigo().equals(p.getParticipante().getCodigo()))
					mParticipantes.add(p.getParticipante().getCodigo()+ " - "+ p.getParticipante().getNombre1() + " "+ p.getParticipante().getApellido1());
				}

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
			mCatalogoSNNR.add(new MessageResource("",0,getActivity().getString(R.string.select)));
			Collections.sort(mCatalogoSNNR);
			ArrayAdapter<MessageResource> dataAdapter = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSNNR);
			dataAdapter.setDropDownViewResource(R.layout.spinner_item);

			spinAislado.setAdapter(dataAdapter);
			spinDormir.setAdapter(dataAdapter);
			spinBanio.setAdapter(dataAdapter);
			spinAlejadoFamilia.setAdapter(dataAdapter);
			spinTieneContacto.setAdapter(dataAdapter);
			spinConQuienTC.setItems(mParticipantes, getString(R.string.select), new MultiSpinner.MultiSpinnerListener() {
				@Override
				public void onItemsSelected(boolean[] selected) {
					int indice = 0;
					conQuienTieneContacto = null;
					for(boolean item : selected){
						if (item) {
							String participante = mParticipantes.get(indice);
							participante = participante.substring(0, participante.indexOf("-")-1).trim();
							if (conQuienTieneContacto==null) {
								conQuienTieneContacto = participante;
							}
							else {
								conQuienTieneContacto += "," + participante;
							}
						}
						indice++;
					}
				}
			});
			spinLugares.setItems(mLugares, getString(R.string.select), new MultiSpinner.MultiSpinnerListener() {
				@Override
				public void onItemsSelected(boolean[] selected) {
					int indice = 0;
					lugares = null;
					for(boolean item : selected){
						if (item) {
							if (lugares==null) lugares = mLugares.get(indice);
							else  lugares += "," + mLugares.get(indice);
						}
						indice++;
					}
					if (lugares !=null && lugares.toLowerCase().contains("otros")) {
						textOtrosLugares.setVisibility(View.VISIBLE);
						inputOtrosLugares.setVisibility(View.VISIBLE);
					} else {
						textOtrosLugares.setVisibility(View.GONE);
						inputOtrosLugares.setVisibility(View.GONE);
						otrosLugares = null;
					}
				}
			});
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
				estudiosAdapter.crearDatosAislamientoVisitaCasoCovid19(mDatosAislamiento);
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
						MenuVisitaCasoCovid19Activity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        i.putExtras(arguments);
				startActivity(i);
				getActivity().finish();
			}
		}

	}	
    
}
