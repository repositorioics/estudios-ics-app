package ni.org.ics.estudios.appmovil.muestreoanual.tasks;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.DownloadTask;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.database.muestreoanual.CohorteAdapter;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaCasaSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaParticipanteSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.domain.users.Authority;
import ni.org.ics.estudios.appmovil.domain.users.UserPermissions;
import ni.org.ics.estudios.appmovil.domain.users.UserSistema;
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
    private CohorteAdapter ca = null;


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
    private static final String TOTAL_TASK_GENERALES = "12";
	
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
            error = descargarCatalogos();
            error = descargarUsuarios();
			error = descargarDatosGenerales();
            error = descargarChf();
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
        ca = new CohorteAdapter(mContext, password, false, false);
        ca.open();

        //Borrar los datos de la base de datos
        estudioAdapter.borrarMessageResource();
        ca.borrarTodosUsuarios();
        ca.borrarTodosRoles();
        ca.borrarTodosPermisos();
		estudioAdapter.borrarEstudios();
		estudioAdapter.borrarBarrios();
		estudioAdapter.borrarCasas();
		estudioAdapter.borrarParticipantes();
        ca.borrarTodosParticipantesProcesos();
        estudioAdapter.borrarCasaCohorteFamilias();
        estudioAdapter.borrarParticipanteCohorteFamilias();
        estudioAdapter.borrarParticipanteSeroprevalencia();

        try {
            if (mCatalogos != null){
                v = mCatalogos.size();
                ListIterator<MessageResource> iter = mCatalogos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearMessageResource(iter.next());
                    publishProgress("Insertando catalogos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (usuarios != null){
                try {
                    addUsuarios(usuarios);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
             }
            if (roles != null){
                try {
                    addRoles(roles);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
            }

            if (permisos != null){
                try {
                    addPermisos(permisos);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
            }

            if (mEstudios != null){
				v = mEstudios.size();
				ListIterator<Estudio> iter = mEstudios.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearEstudio(iter.next());
					publishProgress("Insertando estudios en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
				mEstudios = null;
			}
			if (mBarrios != null){
				v = mBarrios.size();
				ListIterator<Barrio> iter = mBarrios.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearBarrio(iter.next());
					publishProgress("Insertando barrios en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
				mBarrios = null;
			}
			if (mCasas != null){
				v = mCasas.size();
				ListIterator<Casa> iter = mCasas.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearCasa(iter.next());
					publishProgress("Insertando casas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
				mCasas = null;
			}
			if (mParticipantes != null){
				v = mParticipantes.size();
				ListIterator<Participante> iter = mParticipantes.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearParticipante(iter.next());
					publishProgress("Insertando participantes en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
				mParticipantes = null;
			}
            if (mParticipantesProc != null) {
                // download and insert
                try {
                    addParticipantesProcesos(mParticipantesProc);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
            }

            if (mCasasCHF != null){
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
            }
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

        } catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}finally {
            ca.close();
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
    private void addUsuarios(List<UserSistema> usuarios) throws Exception {
        int v = usuarios.size();
        ListIterator<UserSistema> iter = usuarios.listIterator();

        while (iter.hasNext()){
            UserSistema usuario = iter.next();
            if (!ca.existeUsuario(usuario.getUsername())){
                ca.crearUsuario(usuario);
            }
            else{
                ca.actualizarUsuario(usuario);
            }
            publishProgress("Insertando Usuarios", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }
    }

    private void addRoles(List<Authority> roles) throws Exception {

        int v = roles.size();

        ListIterator<Authority> iter = roles.listIterator();

        while (iter.hasNext()){
            ca.crearRol(iter.next());
            publishProgress("Insertando Roles", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addPermisos(List<UserPermissions> permisos) throws Exception {
        int v = permisos.size();
        ListIterator<UserPermissions> iter = permisos.listIterator();

        while (iter.hasNext()){
            ca.crearPermisosUsuario(iter.next());
            publishProgress("Insertando Permisos", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }
    }

    private void addParticipantesProcesos(List<ParticipanteProcesos> participantes) throws Exception {
        int v = participantes.size();
        ListIterator<ParticipanteProcesos> iter = participantes.listIterator();
        while (iter.hasNext()){
            ca.crearParticipanteProcesos(iter.next());
            publishProgress("Participantes Procesos", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }
    }

}
