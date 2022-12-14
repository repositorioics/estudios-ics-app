package ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.*;

import ni.org.ics.estudios.appmovil.domain.cohortefamilia.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.*;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.util.Log;



public class DownloadChfEncuestasTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadChfEncuestasTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadChfEncuestasTask.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;
	
    //private List<EncuestaParticipante> mEncuestasParticipante = null;
    //private List<EncuestaDatosPartoBB> mEncuestasDatosPartoBB = null;
    //private List<EncuestaPesoTalla> mEncuestasPesoTalla = null;
    //private List<EncuestaLactanciaMaterna> mEncuestasLacMat = null;
    private List<Muestra> mMuestras = null;
    private List<TelefonoContacto> mTelefonos = null;
    
	//public static final String ENCUESTA_PARTICIPANTECHF = "1";
    //public static final String ENCUESTA_DATOSPBB = "2";
    //public static final String ENCUESTA_PESOTALLA = "3";
    //public static final String ENCUESTA_LACTMAT = "4";
    public static final String MUESTRAS = "1";
    public static final String TELEFONOS = "2";
    
    private static final String TOTAL_TASK_RECLUTAMIENTO_CHF = "2";
    
	private String error = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private int v =0;

	@Override
	protected String doInBackground(String... values) {
		url = values[0];
		username = values[1];
		password = values[2];
		
		
        try {
			error = descargarDatosReclutamientoCHF();
			if (error!=null) return error;
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		publishProgress("Abriendo base de datos...","1","1");
		estudioAdapter = new EstudiosAdapter(mContext, password, false,false);
		estudioAdapter.open();
		//Borrar los datos de la base de datos
        estudioAdapter.borrarEncuestasParticipantes();
        estudioAdapter.borrarEncuestasDatosPartoBBs();
        estudioAdapter.borrarEncuestasPesoTallas();
        estudioAdapter.borrarEncuestasLactanciaMaternas();
        estudioAdapter.borrarMuestras();
        estudioAdapter.borrarVisitasTerreno();
        estudioAdapter.borrarTelefonoContacto();
        try {
            if (mMuestras != null){
                publishProgress("Insertando muestras en la base de datos...", MUESTRAS, TOTAL_TASK_RECLUTAMIENTO_CHF);
                estudioAdapter.bulkInsertMuestrasChfBySql(mMuestras);
            }
            if (mTelefonos != null){
                publishProgress("Insertando tel??fonos en la base de datos...", TELEFONOS, TOTAL_TASK_RECLUTAMIENTO_CHF);
                estudioAdapter.bulkInsertTelefonosContactoBySql(mTelefonos);
            }
            
		} catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			estudioAdapter.close();
			return e.getLocalizedMessage();
		}
		estudioAdapter.close();
		return error;
	}

    
	// url, username, password
    protected String descargarDatosReclutamientoCHF() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;
            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            //Descargar muestras
            urlRequest = url + "/movil/muestras/";
            publishProgress("Solicitando Muestras",MUESTRAS,TOTAL_TASK_RECLUTAMIENTO_CHF);
            // Perform the HTTP GET request
            ResponseEntity<Muestra[]> responseEntityMuestras = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Muestra[].class);
            // convert the array to a list and return it
            mMuestras = Arrays.asList(responseEntityMuestras.getBody());
            responseEntityMuestras = null;
            
            //Descargar telefonos
            urlRequest = url + "/movil/telefonos/";
            publishProgress("Solicitando telefonos",TELEFONOS,TOTAL_TASK_RECLUTAMIENTO_CHF);
            // Perform the HTTP GET request
            ResponseEntity<TelefonoContacto[]> responseEntityTelefonos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		TelefonoContacto[].class);
            // convert the array to a list and return it
            mTelefonos = Arrays.asList(responseEntityTelefonos.getBody());
            responseEntityTelefonos = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
