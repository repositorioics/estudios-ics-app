package ni.org.ics.estudios.appmovil.muestreoanual.tasks;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.DownloadTask;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.ContactoParticipante;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCovid19;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.domain.users.Authority;
import ni.org.ics.estudios.appmovil.domain.users.UserPermissions;
import ni.org.ics.estudios.appmovil.domain.users.UserSistema;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class DownloadBaseTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadBaseTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadBaseTask.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;

    private List<UserSistema> usuarios = null;
    private List<Authority> roles = null;
    private List<UserPermissions> permisos = null;
    private List<Estudio> mEstudios = null;
	private List<Barrio> mBarrios = null;
	private List<Casa> mCasas = null;
    private List<CasaCohorteFamilia> mCasasCHF = null;
	private List<Participante> mParticipantes = null;
    private List<ParticipanteCohorteFamilia> mParticipantesCHF = null;
    private List<ParticipanteSeroprevalencia> mParticipantesSA = null;
    private List<ParticipanteProcesos> mParticipantesProc = null;
    private List<MessageResource> mCatalogos = null;
    private List<ContactoParticipante> mContactosParticipante = null;
    private List<ParticipanteCovid19> mParticipantesCovid = null;
    private List<Muestra> mMuestras = null;

    public static final String CATALOGOS = "1";
    public static final String USUARIOS = "2";
    public static final String ROLES= "3";
    public static final String USU_PERMISOS = "4";
	public static final String ESTUDIO = "5";
	public static final String BARRIO = "6";
	public static final String CASA = "7";
	public static final String PARTICIPANTE = "8";
    public static final String PARTICIPANTE_PROC = "9";
    public static final String CASA_CHF = "10";
    public static final String PARTICIPANTE_CHF = "11";
    public static final String PARTICIPANTE_SA = "12";
    public static final String CONTACTOS_PART = "13";
    public static final String PARTICIPANTE_CV = "14";
    public static final String MUESTRAS_MA = "15";
    private static final String TOTAL_TASK_GENERALES = "15";
	
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
            error = descargarMuestrasMA();
            error = descargarCatalogos();
            error = descargarUsuarios();
			error = descargarDatosGenerales();
            error = descargarChf();
            error = descargarDatosSeroprevalencia();
            error = descargarContactosParticipantes();
            error = descargarParticipantesCovid();

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
        estudioAdapter.borrarMessageResource();
        estudioAdapter.borrarTodosUsuarios();
        estudioAdapter.borrarTodosRoles();
        estudioAdapter.borrarTodosPermisos();
		estudioAdapter.borrarEstudios();
		estudioAdapter.borrarBarrios();
		estudioAdapter.borrarCasas();
		estudioAdapter.borrarParticipantes();
        estudioAdapter.borrarTodosParticipantesProcesos();
        estudioAdapter.borrarCasaCohorteFamilias();
        estudioAdapter.borrarParticipanteCohorteFamilias();
        estudioAdapter.borrarParticipanteSeroprevalencia();
        estudioAdapter.borrarTamizajes();
        estudioAdapter.borrarCartasConsentimiento();
        estudioAdapter.borrarContactosParticipantes();
        estudioAdapter.borrarVisitasTerrenoParticipante();
        estudioAdapter.borrarEnfermedadesCronicas();
        estudioAdapter.borrarObsequiosGenerales();
        estudioAdapter.borrarDatosCoordenadas();
        estudioAdapter.borrarEncuestaCasas();
        estudioAdapter.borrarAreasAmbiente();
        estudioAdapter.borrarCuartos();
        estudioAdapter.borrarCamas();
        estudioAdapter.borrarPersonasCama();
        estudioAdapter.borrarEncuestasParticipantes();
        estudioAdapter.borrarEncuestasDatosPartoBBs();
        estudioAdapter.borrarEncuestasPesoTallas();
        estudioAdapter.borrarEncuestasLactanciaMaternas();
        estudioAdapter.borrarMuestras();
        estudioAdapter.borrarVisitasTerreno();
        estudioAdapter.borrarTelefonoContacto();
        estudioAdapter.borrarParticipanteSeroprevalencia();
        estudioAdapter.borrarEncuestaCasaSA();
        estudioAdapter.borrarEncuestaParticipanteSA();
        estudioAdapter.borrarCasaCohorteFamiliaCaso();
        estudioAdapter.borrarParticipanteCohorteFamiliaCaso();
        estudioAdapter.borrarVisitaSeguimientoCaso();
        estudioAdapter.borrarVisitaFallidaCaso();
        estudioAdapter.borrarVisitaSeguimientoCasoSintomas();
        estudioAdapter.borrarFormularioContactoCaso();
        estudioAdapter.borrarVisitaFinalCaso();
        estudioAdapter.borrarMuestrasSuperficie();
        estudioAdapter.borrarSensoresCasos();
        estudioAdapter.borrarParticipantesCasoUO1();
        estudioAdapter.borrarMuestrasUO1();
        estudioAdapter.borrarVisitaCasoUO1();
        estudioAdapter.borrarVisitaVacunaUO1();
        estudioAdapter.borrarSintomasVisitaCasoUO1();
        estudioAdapter.borrarTodasEncCasas();
        estudioAdapter.borrarTodasEncParticipantes();
        estudioAdapter.borrarTodasLactanciaMaterna();
        estudioAdapter.borrarTodasPT();
        estudioAdapter.borrarTodasMuestras();
        estudioAdapter.borrarTodosOB();
        estudioAdapter.borrarNewVacuna();
        estudioAdapter.borrarDatosPartoBB();
        estudioAdapter.borrarTodasVisitaTerrenos();
        estudioAdapter.borrarTodasDatosVisitaTerrenos();
        estudioAdapter.borrarRecepcionBHC();
        estudioAdapter.borrarRecepcionSero();
        estudioAdapter.borrarPinchazo();
        estudioAdapter.borrarTempRojoBhc();
        estudioAdapter.borrarTempPbmc();
        estudioAdapter.borrarTodasEncuestaSatisfaccion();
        estudioAdapter.borrarRazonNoData();
        estudioAdapter.borrarParticipanteCovid19();
        estudioAdapter.borrarCasoCovid19();
        estudioAdapter.borrarParticipanteCasoCovid19();
        estudioAdapter.borrarVisitaSeguimientoCasoCovid19();
        estudioAdapter.borrarMuestrasCovid19();
        estudioAdapter.borrarVisitaFallidaCasoCovid19();
        estudioAdapter.borrarCandidatoTransmisionCovid19();
        estudioAdapter.borrarSintomasVisitaCasoCovid19();
        estudioAdapter.borrarDatosAislamientoVisitaCasoCovid19();
        estudioAdapter.borrarVisitaFinalCasoCovid19();
        estudioAdapter.borrarSintomasVisitaFinalCovid19();
        try {
            if (mCatalogos != null){
                publishProgress("Insertando catalogos", CATALOGOS, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertMessageResourceBySql(mCatalogos);
            }
            if (usuarios != null){
                publishProgress("Insertando usuarios", USUARIOS, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertUsuariosBySql(usuarios);
             }
            if (roles != null){
                publishProgress("Insertando roles", ROLES, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertRolesBySql(roles);
            }
            if (permisos != null){
                publishProgress("Insertando permisos", USU_PERMISOS, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertUserPermissionsBySql(permisos);
            }
            if (mEstudios != null){
                publishProgress("Insertando estudios", ESTUDIO, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertEstutiosBySql(mEstudios);
            }
            if (mBarrios != null){
                publishProgress("Insertando barrios", BARRIO, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertBarriosBySql(mBarrios);
            }
            if (mCasas != null){
                publishProgress("Insertando casas", CASA, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertCasasBySql(mCasas);
            }
            if (mParticipantes != null){
                publishProgress("Insertando participantes", PARTICIPANTE, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertParticipantesBySql(mParticipantes);
            }
            if (mParticipantesProc != null){
                publishProgress("Insertando procesos participantes", PARTICIPANTE_PROC, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertParticipantesProcBySql(mParticipantesProc);
            }
            if (mCasasCHF != null){
                publishProgress("Insertando casas CHF", CASA_CHF, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertCasasChfBySql(mCasasCHF);
            }
            if (mParticipantesCHF != null){
                publishProgress("Insertando participantes CHF", PARTICIPANTE_CHF, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertParticipantesChfBySql(mParticipantesCHF);
            }
            if (mParticipantesSA != null){
                publishProgress("Insertando participantes SA", PARTICIPANTE_SA, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertParticipantesSABySql(mParticipantesSA);
            }
            if (mContactosParticipante != null){
                publishProgress("Insertando contactos participantes", CONTACTOS_PART, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertContactoParticipantesBySql(mContactosParticipante);
            }
            if (mParticipantesCovid != null){
                publishProgress("Insertando contactos participantes", PARTICIPANTE_CV, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertCovidBySql(Covid19DBConstants.PARTICIPANTE_COVID_TABLE, mParticipantesCovid);
            }
            if (mMuestras != null){
                publishProgress("Insertando muestras anuales", MUESTRAS_MA, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertMuestrasMABySql(mMuestras);
            }
        } catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}finally {
            estudioAdapter.close();

        }
		return error;
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
            publishProgress("Solicitando catalogos",CATALOGOS,TOTAL_TASK_GENERALES);
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

    // url, username, password
    protected String descargarUsuarios() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;

            urlRequest = url + "/movil/usuarios";

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
            publishProgress("Solicitando usuarios",USUARIOS,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<UserSistema[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    UserSistema[].class);

            // convert the array to a list and return it
            usuarios = Arrays.asList(responseEntity.getBody());

            // The URL for making the GET request
            urlRequest = url + "/movil/roles";

            // Set the Accept header for "application/json"
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);

            // Create a new RestTemplate instance
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            publishProgress("Solicitando roles",ROLES,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Authority[]> responseEntityRoles = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Authority[].class);

            // convert the array to a list and return it
            roles = Arrays.asList(responseEntityRoles.getBody());

            // The URL for making the GET request
            urlRequest = url + "/movil/permisos";

            // Set the Accept header for "application/json"
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            publishProgress("Solicitando permisos de usuarios",USU_PERMISOS,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<UserPermissions[]> responseEntityPerm = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    UserPermissions[].class);

            // convert the array to a list and return it
            permisos = Arrays.asList(responseEntityPerm.getBody());

            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
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
            // The URL for making the GET request
            urlRequest = url + "/movil/participantesprocesos";
            publishProgress("Solicitando procesos de participantes",PARTICIPANTE_PROC,TOTAL_TASK_GENERALES);

            // Perform the HTTP GET request
            ResponseEntity<ParticipanteProcesos[]> responseEntityPartProc = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteProcesos[].class);

            // convert the array to a list and return it
            mParticipantesProc = Arrays.asList(responseEntityPartProc.getBody());
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
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
            publishProgress("Solicitando participantes seroprevalencia",PARTICIPANTE_SA,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteSeroprevalencia[]> responseEntityPartiSa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteSeroprevalencia[].class);
            // convert the array to a list and return it
            mParticipantesSA = Arrays.asList(responseEntityPartiSa.getBody());
            responseEntityPartiSa = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarChf() throws Exception {
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

            //Descargar casascohorte familia
            urlRequest = url + "/movil/casasCHF/";
            publishProgress("Solicitando casas cohorte familia",CASA_CHF,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<CasaCohorteFamilia[]> responseEntityCasasCHF = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    CasaCohorteFamilia[].class);
            // convert the array to a list and return it
            mCasasCHF = Arrays.asList(responseEntityCasasCHF.getBody());
            responseEntityCasasCHF = null;
            //Descargar participantes cohorte
            urlRequest = url + "/movil/participantesCHF/";
            publishProgress("Solicitando participantes de cohorte familia",PARTICIPANTE_CHF,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteCohorteFamilia[]> responseEntityParticipantesCHF = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteCohorteFamilia[].class);
            // convert the array to a list and return it
            mParticipantesCHF = Arrays.asList(responseEntityParticipantesCHF.getBody());
            responseEntityParticipantesCHF = null;

            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarContactosParticipantes() throws Exception {
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
            urlRequest = url + "/movil/contactosparticipantes/";
            publishProgress("Solicitando teléfonos participantes",CONTACTOS_PART,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<ContactoParticipante[]> responseEntityContactos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ContactoParticipante[].class);
            // convert the array to a list and return it
            mContactosParticipante = Arrays.asList(responseEntityContactos.getBody());
            responseEntityContactos = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarParticipantesCovid() throws Exception {
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
            urlRequest = url + "/movil/participantesCovid19/";
            publishProgress("Solicitando participantes covid",PARTICIPANTE_CV,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteCovid19[]> responseEntityCovid = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteCovid19[].class);
            // convert the array to a list and return it
            mParticipantesCovid = Arrays.asList(responseEntityCovid.getBody());
            responseEntityCovid = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarMuestrasMA() throws Exception {
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

            //Descargar muestras anuales actuales
            urlRequest = url + "/movil/muestrasMA/";
            publishProgress("Solicitando muestras MA",MUESTRAS_MA,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Muestra[]> responseEntityCovid = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Muestra[].class);
            // convert the array to a list and return it
            mMuestras = Arrays.asList(responseEntityCovid.getBody());
            responseEntityCovid = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
