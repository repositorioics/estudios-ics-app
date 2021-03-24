package ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
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



public class DownloadChfTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadChfTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadChfTask.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;
	
    private List<Cocina> mCocinas = null;
    private List<Comedor> mComedores = null;
    private List<Sala> mSalas = null;
    private List<Habitacion> mHabitaciones = null;
    private List<Banio> mBanios = null;
    private List<Ventana> mVentanas = null;
    private List<Cuarto> mCuartos = null;
    private List<Cama> mCamas = null;
    private List<PersonaCama> mPersonaCamas = null;
    
    public static final String COCINA = "1";
    public static final String COMEDOR = "2";
    public static final String SALA = "3";
    public static final String HABITACION = "4";
    public static final String BANIOS = "5";
    public static final String VENTANAS = "6";
    public static final String CUARTOS = "7";
    public static final String CAMAS = "8";
    public static final String PERSONAS_CAMA = "9";
    
    private static final String TOTAL_TASK_RECLUTAMIENTO = "9";
    
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
			error = descargarDatosReclutamiento();
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
        //estudioAdapter.borrarCasaCohorteFamilias();
        //estudioAdapter.borrarParticipanteCohorteFamilias();
        estudioAdapter.borrarEncuestaCasas();
        estudioAdapter.borrarAreasAmbiente();
        estudioAdapter.borrarCuartos();
        estudioAdapter.borrarCamas();
        estudioAdapter.borrarPersonasCama();
        try {
            if (mCocinas != null){
                estudioAdapter.bulkInsertCocinasBySql(mCocinas);
                publishProgress("Insertando cocinas en la base de datos...", COCINA, TOTAL_TASK_RECLUTAMIENTO);
            }
            if (mComedores != null){
                estudioAdapter.bulkInsertComedoresBySql(mComedores);
                publishProgress("Insertando comedores en la base de datos...", COMEDOR, TOTAL_TASK_RECLUTAMIENTO);
            }
            if (mSalas != null){
                estudioAdapter.bulkInsertSalasBySql(mSalas);
                publishProgress("Insertando salas en la base de datos...", SALA, TOTAL_TASK_RECLUTAMIENTO);
            }
            if (mHabitaciones != null){
                estudioAdapter.bulkInsertHabitacionesBySql(mHabitaciones);
                publishProgress("Insertando habitaciones en la base de datos...", HABITACION, TOTAL_TASK_RECLUTAMIENTO);
            }
            if (mBanios != null){
                estudioAdapter.bulkInsertBaniosBySql(mBanios);
                publishProgress("Insertando baños en la base de datos...", BANIOS, TOTAL_TASK_RECLUTAMIENTO);
            }
            if (mVentanas != null){
                estudioAdapter.bulkInsertVentanasBySql(mVentanas);
                publishProgress("Insertando ventanas en la base de datos...", VENTANAS, TOTAL_TASK_RECLUTAMIENTO);
            }
            if (mCuartos != null){
                estudioAdapter.bulkInsertCuartosBySql(mCuartos);
                publishProgress("Insertando cuartos en la base de datos...", CUARTOS, TOTAL_TASK_RECLUTAMIENTO);
            }
            if (mCamas != null){
                estudioAdapter.bulkInsertCamasBySql(mCamas);
                publishProgress("Insertando camas en la base de datos...", CAMAS, TOTAL_TASK_RECLUTAMIENTO);
            }
            if (mPersonaCamas != null){
                estudioAdapter.bulkInsertPersonasCamasBySql(mPersonaCamas);
                publishProgress("Insertando asociación persona cama en la base de datos...", PERSONAS_CAMA, TOTAL_TASK_RECLUTAMIENTO);
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
    protected String descargarDatosReclutamiento() throws Exception {
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
            //Descargar cocinas
            urlRequest = url + "/movil/cocinas/";
            publishProgress("Solicitando cocinas",COCINA,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Cocina[]> responseEntityCocinas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Cocina[].class);
            // convert the array to a list and return it
            mCocinas = Arrays.asList(responseEntityCocinas.getBody());
            responseEntityCocinas = null;
            //Descargar comedores
            urlRequest = url + "/movil/comedores/";
            publishProgress("Solicitando comedores",COMEDOR,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Comedor[]> responseEntityComedor = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Comedor[].class);
            // convert the array to a list and return it
            mComedores = Arrays.asList(responseEntityComedor.getBody());
            responseEntityComedor = null;
            //Descargar salas
            urlRequest = url + "/movil/salas/";
            publishProgress("Solicitando salas",SALA,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Sala[]> responseEntitySalas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Sala[].class);
            // convert the array to a list and return it
            mSalas = Arrays.asList(responseEntitySalas.getBody());
            responseEntitySalas = null;
            //Descargar habitaciones
            urlRequest = url + "/movil/habitaciones/";
            publishProgress("Solicitando habitaciones",HABITACION,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Habitacion[]> responseEntityHab = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Habitacion[].class);
            // convert the array to a list and return it
            mHabitaciones = Arrays.asList(responseEntityHab.getBody());
            responseEntityHab = null;
            //Descargar banios
            urlRequest = url + "/movil/banios/";
            publishProgress("Solicitando baños",BANIOS,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Banio[]> responseEntityBanios = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Banio[].class);
            // convert the array to a list and return it
            mBanios = Arrays.asList(responseEntityBanios.getBody());
            responseEntityBanios = null;
            //Descargar ventanas
            urlRequest = url + "/movil/ventanas/";
            publishProgress("Solicitando ventanas",VENTANAS,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Ventana[]> responseEntityVentanas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Ventana[].class);
            // convert the array to a list and return it
            mVentanas = Arrays.asList(responseEntityVentanas.getBody());
            responseEntityVentanas = null;
            //Descargar Cuartos
            urlRequest = url + "/movil/cuartos/";
            publishProgress("Solicitando cuartos",CUARTOS,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Cuarto[]> responseEntityCuartos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Cuarto[].class);
            // convert the array to a list and return it
            mCuartos = Arrays.asList(responseEntityCuartos.getBody());
            responseEntityCuartos = null;
            //Descargar Camas
            urlRequest = url + "/movil/camas/";
            publishProgress("Solicitando camas",CAMAS,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Cama[]> responseEntityCamas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Cama[].class);
            // convert the array to a list and return it
            mCamas = Arrays.asList(responseEntityCamas.getBody());
            responseEntityCamas = null;
            //Descargar Personas Camas
            urlRequest = url + "/movil/personasCamas/";
            publishProgress("Solicitando Personas Camas",PERSONAS_CAMA,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<PersonaCama[]> responseEntityPerCamas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    PersonaCama[].class);
            // convert the array to a list and return it
            mPersonaCamas = Arrays.asList(responseEntityPerCamas.getBody());
            responseEntityPerCamas = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
   
}
