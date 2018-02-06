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
	
    //private List<CasaCohorteFamilia> mCasasCHF = null;
    //private List<ParticipanteCohorteFamilia> mParticipantesCHF = null;
    private List<EncuestaCasa> mEncuestasCasas = null;
    private List<Cocina> mCocinas = null;
    private List<Comedor> mComedores = null;
    private List<Sala> mSalas = null;
    private List<Habitacion> mHabitaciones = null;
    private List<Banio> mBanios = null;
    private List<Ventana> mVentanas = null;
    private List<Cuarto> mCuartos = null;
    private List<Cama> mCamas = null;
    private List<PersonaCama> mPersonaCamas = null;
    
    
    
	public static final String CASACHF = "1";
    public static final String PARTICIPANTECHF = "2";
    public static final String ENCUESTA_CASACHF = "3";
    public static final String COCINA = "4";
    public static final String COMEDOR = "5";
    public static final String SALA = "6";
    public static final String HABITACION = "7";
    public static final String BANIOS = "8";
    public static final String VENTANAS = "9";
    public static final String CUARTOS = "10";
    public static final String CAMAS = "11";
    public static final String PERSONAS_CAMA = "12";
    
    private static final String TOTAL_TASK_RECLUTAMIENTO = "12";
    
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
            /*if (mCasasCHF != null){
                v = mCasasCHF.size();
                ListIterator<CasaCohorteFamilia> iter = mCasasCHF.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCasaCohorteFamilia(iter.next());
                    publishProgress("Insertando casas cohorte familia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mCasasCHF = null;
            }
            if (mParticipantesCHF != null){
                v = mParticipantesCHF.size();
                ListIterator<ParticipanteCohorteFamilia> iter = mParticipantesCHF.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearParticipanteCohorteFamilia(iter.next());
                    publishProgress("Insertando participantes cohorte familia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mParticipantesCHF = null;
            }*/
            if (mEncuestasCasas != null){
                v = mEncuestasCasas.size();
                ListIterator<EncuestaCasa> iter = mEncuestasCasas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestaCasa(iter.next());
                    publishProgress("Insertando encuestas de casas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mEncuestasCasas = null;
            }
            if (mCocinas != null){
                v = mCocinas.size();
                ListIterator<Cocina> iter = mCocinas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCocina(iter.next());
                    publishProgress("Insertando cocinas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mCocinas = null;
            }
            if (mComedores != null){
                v = mComedores.size();
                ListIterator<Comedor> iter = mComedores.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearComedor(iter.next());
                    publishProgress("Insertando comedores en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mComedores = null;
            }
            if (mSalas != null){
                v = mSalas.size();
                ListIterator<Sala> iter = mSalas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearSala(iter.next());
                    publishProgress("Insertando salas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mSalas = null;
            }
            if (mHabitaciones != null){
                v = mHabitaciones.size();
                ListIterator<Habitacion> iter = mHabitaciones.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearHabitacion(iter.next());
                    publishProgress("Insertando habitaciones en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mHabitaciones = null;
            }
            if (mBanios != null){
                v = mBanios.size();
                ListIterator<Banio> iter = mBanios.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearBanio(iter.next());
                    publishProgress("Insertando baños en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mBanios = null;
            }
            if (mVentanas != null){
                v = mVentanas.size();
                ListIterator<Ventana> iter = mVentanas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVentana(iter.next());
                    publishProgress("Insertando ventanas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVentanas = null;
            }
            if (mCuartos != null){
                v = mCuartos.size();
                ListIterator<Cuarto> iter = mCuartos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCuarto(iter.next());
                    publishProgress("Insertando cuartos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mCuartos = null;
            }
            if (mCamas != null){
                v = mCamas.size();
                ListIterator<Cama> iter = mCamas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCama(iter.next());
                    publishProgress("Insertando camas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mCamas = null;
            }
            if (mPersonaCamas != null){
                v = mPersonaCamas.size();
                ListIterator<PersonaCama> iter = mPersonaCamas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearPersonaCama(iter.next());
                    publishProgress("Insertando asociación persona cama en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mPersonaCamas = null;
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
            /*
            //Descargar casascohorte familia
            urlRequest = url + "/movil/casasCHF/";
            publishProgress("Solicitando casas cohorte familia",CASACHF,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<CasaCohorteFamilia[]> responseEntityCasasCHF = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    CasaCohorteFamilia[].class);
            // convert the array to a list and return it
            mCasasCHF = Arrays.asList(responseEntityCasasCHF.getBody());
            responseEntityCasasCHF = null;
            //Descargar participantes cohorte
            urlRequest = url + "/movil/participantesCHF/";
            publishProgress("Solicitando participantes de cohorte familia",PARTICIPANTECHF,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteCohorteFamilia[]> responseEntityParticipantesCHF = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteCohorteFamilia[].class);
            // convert the array to a list and return it
            mParticipantesCHF = Arrays.asList(responseEntityParticipantesCHF.getBody());
            responseEntityParticipantesCHF = null;
            */
            //Descargar encuestas de casa
            urlRequest = url + "/movil/encuestasCasa/";
            publishProgress("Solicitando encuestas de casas",ENCUESTA_CASACHF,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaCasa[]> responseEntityEncCasa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaCasa[].class);
            // convert the array to a list and return it
            mEncuestasCasas = Arrays.asList(responseEntityEncCasa.getBody());
            responseEntityEncCasa = null;
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
