package ni.org.ics.estudios.appmovil.entomologia.server.task;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.DownloadTask;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogar;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DownloadAllEntoTask extends DownloadTask {

	private final Context mContext;

	public DownloadAllEntoTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadAllEntoTask.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;
	private List<Estudio> mEstudios = null;
	private List<Barrio> mBarrios = null;
	private List<Casa> mCasas = null;
	private List<Participante> mParticipantes = null;
	private List<MessageResource> mCatalogos = null;
	private List<CuestionarioHogar> mCuestionarios = null;

	public static final String ESTUDIO = "1";
	public static final String BARRIO = "2";
	public static final String CASA = "3";
	public static final String PARTICIPANTE = "4";
	public static final String CATALOGOS = "5";
	public static final String CUESTIONARIOS = "6";
	private static final String TOTAL_TASK_GENERALES = "6";
	
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
			error = descargarDatosGenerales();
			error = descargarCatalogos();
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
		estudioAdapter.borrarEstudios();
		estudioAdapter.borrarBarrios();
		estudioAdapter.borrarCasas();
		estudioAdapter.borrarParticipantes();
		estudioAdapter.borrarMessageResource();
		estudioAdapter.borrarCuestionarioHogar();
		estudioAdapter.borrarCuestionarioHogarPoblacion();
		try {
			if (mEstudios != null){
				publishProgress("Insertando estudios en la base de datos","1","1");
				estudioAdapter.bulkInsertEstutiosBySql(mEstudios);
			}
			if (mBarrios != null){
				publishProgress("Insertando barrios en la base de datos","1","1");
				estudioAdapter.bulkInsertBarriosBySql(mBarrios);
			}
			if (mCasas != null){
				publishProgress("Insertando casas en la base de datos","1","1");
				estudioAdapter.bulkInsertCasasBySql(mCasas);
			}
			if (mParticipantes != null){
				publishProgress("Insertando participantes en la base de datos","1","1");
				estudioAdapter.bulkInsertParticipantesBySql(mParticipantes);
			}
			if (mCatalogos != null){
				publishProgress("Insertando catalogos en la base de datos", "1","1");
				estudioAdapter.bulkInsertMessageResourceBySql(mCatalogos);
			}
			if (mCuestionarios != null){
				publishProgress("Insertando cuestionarios en la base de datos", "1","1");
				estudioAdapter.bulkInsertCuestionarioHogarBySql(mCuestionarios);
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
    protected String descargarDatosGenerales() throws Exception {
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

            //Descargar estudios
            urlRequest = url + "/movil/estudios/";
            publishProgress("Solicitando estudios",ESTUDIO,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Estudio[]> responseEntityEstudio = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Estudio[].class);
            // convert the array to a list and return it
            mEstudios = Arrays.asList(responseEntityEstudio.getBody());
            responseEntityEstudio = null;

            //Descargar barrios
            urlRequest = url + "/movil/barrios/";
            publishProgress("Solicitando barrios",BARRIO,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Barrio[]> responseEntityBarrio = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Barrio[].class);
            // convert the array to a list and return it
            mBarrios = Arrays.asList(responseEntityBarrio.getBody());
            responseEntityBarrio = null;

            //Descargar casas
            urlRequest = url + "/movil/casas/";
            publishProgress("Solicitando casas",CASA,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Casa[]> responseEntityCasa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Casa[].class);
            // convert the array to a list and return it
            mCasas = Arrays.asList(responseEntityCasa.getBody());
            responseEntityCasa = null;

            //Descargar participantes
            urlRequest = url + "/movil/participantes/";
            publishProgress("Solicitando participantes",PARTICIPANTE,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Participante[]> responseEntityParticipante = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Participante[].class);
            // convert the array to a list and return it
            mParticipantes = Arrays.asList(responseEntityParticipante.getBody());
            responseEntityParticipante = null;

			//Descargar participantes
			urlRequest = url + "/movil/cuestionariosHogar/";
			publishProgress("Solicitando cuestionarios",CUESTIONARIOS,TOTAL_TASK_GENERALES);
			// Perform the HTTP GET request
			ResponseEntity<CuestionarioHogar[]> responseEntityCues = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
					CuestionarioHogar[].class);
			// convert the array to a list and return it
			mCuestionarios = Arrays.asList(responseEntityCues.getBody());
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

	// url, username, password
	protected String descargarCatalogos() throws Exception {
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
			//Descargar catalogos
			urlRequest = url + "/movil/catalogos";
			publishProgress("Solicitando catalogos", CATALOGOS, TOTAL_TASK_GENERALES);
			// Perform the HTTP GET request
			ResponseEntity<MessageResource[]> responseEntityMessageResource = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
					MessageResource[].class);
			// convert the array to a list and return it
			mCatalogos = Arrays.asList(responseEntityMessageResource.getBody());
			return null;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			return e.getLocalizedMessage();
		}
	}
}
