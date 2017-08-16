package ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaCasaSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaParticipanteSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
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



public class DownloadSeroTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadSeroTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadSeroTask.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;
	
	private List<ParticipanteSeroprevalencia> mParticipantesSA = null;
    private List<EncuestaCasaSA> mEncuestasCasaSA = null;
    private List<EncuestaParticipanteSA> mEncuestasParticipanteSA = null;
    
    public static final String PARTICIPANTESA = "1";
    public static final String ENCUESTA_CASASA = "2";
    public static final String ENCUESTA_PARTICIPANTESA = "3";
    
    
    private static final String TOTAL_TASK_SERO = "3";
    
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
			error = descargarDatosSeroprevalencia();
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
        estudioAdapter.borrarParticipanteSeroprevalencia();
        estudioAdapter.borrarEncuestaCasaSA();
        estudioAdapter.borrarEncuestaParticipanteSA();
		try {
            if (mParticipantesSA != null){
                v = mParticipantesSA.size();
                ListIterator<ParticipanteSeroprevalencia> iter = mParticipantesSA.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearParticipanteSeroprevalencia(iter.next());
                    publishProgress("Insertando participantes seroprevalencia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mParticipantesSA = null;
            }
            if (mEncuestasCasaSA != null){
                v = mEncuestasCasaSA.size();
                ListIterator<EncuestaCasaSA> iter = mEncuestasCasaSA.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestaCasaSA(iter.next());
                    publishProgress("Insertando encuestas de casa seroprevalencia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mEncuestasCasaSA = null;
            }
            if (mEncuestasParticipanteSA != null){
                v = mEncuestasParticipanteSA.size();
                ListIterator<EncuestaParticipanteSA> iter = mEncuestasParticipanteSA.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestaParticipanteSA(iter.next());
                    publishProgress("Insertando encuestas de participantes seroprevalencia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mEncuestasParticipanteSA = null;
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
    protected String descargarDatosSeroprevalencia() throws Exception {
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
            

            //Descargar participantes seroprevalencia
            urlRequest = url + "/movil/participantesSA/";
            publishProgress("Solicitando participantes seroprevalencia",PARTICIPANTESA,TOTAL_TASK_SERO);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteSeroprevalencia[]> responseEntityPartiSa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteSeroprevalencia[].class);
            // convert the array to a list and return it
            mParticipantesSA = Arrays.asList(responseEntityPartiSa.getBody());
            responseEntityPartiSa = null;
            //Descargar encuestas casas seroprevalencia
            urlRequest = url + "/movil/encuestasCasaSA/";
            publishProgress("Solicitando encuestas de casas seroprevalencia",ENCUESTA_CASASA,TOTAL_TASK_SERO);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaCasaSA[]> responseEntityEncuCasaSa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaCasaSA[].class);
            // convert the array to a list and return it
            mEncuestasCasaSA = Arrays.asList(responseEntityEncuCasaSa.getBody());
            responseEntityEncuCasaSa = null;
            //Descargar encuestas participantes seroprevalencia
            urlRequest = url + "/movil/encuestasParticipanteSA/";
            publishProgress("Solicitando encuestas de participantes seroprevalencia",ENCUESTA_PARTICIPANTESA,TOTAL_TASK_SERO);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaParticipanteSA[]> responseEntityEncuPartiSa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaParticipanteSA[].class);
            // convert the array to a list and return it
            mEncuestasParticipanteSA = Arrays.asList(responseEntityEncuPartiSa.getBody());
            responseEntityEncuPartiSa = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
