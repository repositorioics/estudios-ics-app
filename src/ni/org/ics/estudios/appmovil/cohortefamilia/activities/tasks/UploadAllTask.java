package ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks;

import java.util.ArrayList;
import java.util.List;


import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.listeners.UploadListener;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.util.Log;

public class UploadAllTask extends UploadTask {
	
	private final Context mContext;

	public UploadAllTask(Context context) {
		mContext = context;
	}

	protected static final String TAG = UploadAllTask.class.getSimpleName();
    
	private EstudiosAdapter estudioAdapter = null;
	private List<Casa> mCasas = new ArrayList<Casa>();
	

	private String url = null;
	private String username = null;
	private String password = null;
	private String error = null;
	protected UploadListener mStateListener;
	
	public static final String CASA = "1";
	private static final String TOTAL_TASK = "1";
	

	@Override
	protected String doInBackground(String... values) {
		url = values[0];
		username = values[1];
		password = values[2];

		try {
			publishProgress("Obteniendo registros de la base de datos", "1", "2");
			estudioAdapter = new EstudiosAdapter(mContext, password, false,false);
			estudioAdapter.open();
			String filtro = MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'";
			mCasas = estudioAdapter.getCasas(filtro, MainDBConstants.codigo);
			publishProgress("Datos completos!", "2", "2");
			actualizarBaseDatos(Constants.STATUS_SUBMITTED, CASA);
			error = cargarCasas(url, username, password);
			if (!error.matches("Datos recibidos!")){
				actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CASA);
				return error;
			}
            estudioAdapter.close();
		} catch (Exception e1) {
			estudioAdapter.close();
			e1.printStackTrace();
			return e1.getLocalizedMessage();
		}
		return error;
	}
	
	private void actualizarBaseDatos(String estado, String opcion) {
		int c;
		if(opcion==CASA){
			c = mCasas.size();
			if(c>0){
				for (Casa casa : mCasas) {
					casa.setEstado(estado.charAt(0));
					estudioAdapter.editarCasa(casa);
					publishProgress("Actualizando casas base de datos local", Integer.valueOf(mCasas.indexOf(casa)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
	}

	
	/***************************************************/
	/********************* Casa ************************/
	/***************************************************/
    // url, username, password
    protected String cargarCasas(String url, String username, 
    		String password) throws Exception {
    	try {
    		if(mCasas.size()>0){
    			// La URL de la solicitud POST
    			publishProgress("Enviando casas!", CASA, TOTAL_TASK);
    			final String urlRequest = url + "/movil/casas";
    			Casa[] envio = mCasas.toArray(new Casa[mCasas.size()]);
    			HttpHeaders requestHeaders = new HttpHeaders();
    			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
    			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    			requestHeaders.setAuthorization(authHeader);
    			HttpEntity<Casa[]> requestEntity = 
    					new HttpEntity<Casa[]>(envio, requestHeaders);
    					RestTemplate restTemplate = new RestTemplate();
    					restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    					restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
    					// Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
    					ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
    							String.class);
    					return response.getBody();
    		}
    		else{
    			return "Datos recibidos!";
    		}
    	} catch (Exception e) {
    		Log.e(TAG, e.getMessage(), e);
    		return e.getMessage();
    	}
    }
}