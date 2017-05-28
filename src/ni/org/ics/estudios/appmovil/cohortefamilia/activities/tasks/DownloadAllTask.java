package ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.*;

import ni.org.ics.estudios.appmovil.domain.cohortefamilia.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.*;
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



public class DownloadAllTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadAllTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadAllTask.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;
	private List<Estudio> mEstudios = null;
	private List<Barrio> mBarrios = null;
	private List<Casa> mCasas = null;
	private List<Participante> mParticipantes = null;
    private List<PreTamizaje> mPreTamizajes = null;
    private List<CasaCohorteFamilia> mCasasCHF = null;
    private List<Tamizaje> mTamizajes = null;
    private List<VisitaTerreno> mVisitas = null;
    private List<ParticipanteCohorteFamilia> mParticipantesCHF = null;
    private List<CartaConsentimiento> mCartasConsent = null;
    private List<EncuestaCasa> mEncuestasCasas = null;
    private List<Cocina> mCocinas = null;
    private List<Comedor> mComedores = null;
    private List<Sala> mSalas = null;
    private List<Habitacion> mHabitaciones = null;
    private List<Banio> mBanios = null;
    private List<Ventana> mVentanas = null;
    private List<Cama> mCamas = null;
    private List<PersonaCama> mPersonaCamas = null;
    private List<EncuestaParticipante> mEncuestasParticipante = null;
    private List<EncuestaDatosPartoBB> mEncuestasDatosPartoBB = null;
    private List<EncuestaPesoTalla> mEncuestasPesoTalla = null;
    private List<EncuestaLactanciaMaterna> mEncuestasLacMat = null;
    private List<Muestra> mMuestras = null;
    private List<ParticipanteSeroprevalencia> mParticipantesSA = null;
    private List<EncuestaCasaSA> mEncuestasCasaSA = null;
    private List<EncuestaParticipanteSA> mEncuestasParticipanteSA = null;
	
	public static final String ESTUDIO = "1";
	public static final String BARRIO = "2";
	public static final String CASA = "3";
	public static final String PARTICIPANTE = "4";
    public static final String VISITAS = "5";
    public static final String PRETAMIZAJE = "6";
    public static final String CASACHF = "7";
    public static final String TAMIZAJE = "8";
    public static final String PARTICIPANTECHF = "9";
    public static final String CARTAS_CONSENT = "10";
    public static final String ENCUESTA_CASACHF = "11";
    public static final String COCINA = "12";
    public static final String COMEDOR = "13";
    public static final String SALA = "14";
    public static final String HABITACION = "15";
    public static final String BANIOS = "16";
    public static final String VENTANAS = "17";
    public static final String CAMAS = "18";
    public static final String PERSONAS_CAMA = "19";
    public static final String ENCUESTA_PARTICIPANTECHF = "20";
    public static final String ENCUESTA_DATOSPBB = "21";
    public static final String ENCUESTA_PESOTALLA = "22";
    public static final String ENCUESTA_LACTMAT = "23";
    public static final String MUESTRAS = "24";
    public static final String PARTICIPANTESA = "25";
    public static final String ENCUESTA_PARTICIPANTESA = "26";
    public static final String ENCUESTA_CASASA = "27";

    private static final String TOTAL_TASK = "27";

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
			error = descargarDatos();
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
        estudioAdapter.borrarPreTamizajes();
        estudioAdapter.borrarCasaCohorteFamilias();
        estudioAdapter.borrarTamizajes();
        estudioAdapter.borrarParticipanteCohorteFamilias();
        estudioAdapter.borrarCartasConsentimiento();
        estudioAdapter.borrarEncuestaCasas();
        estudioAdapter.borrarAreasAmbiente();
        estudioAdapter.borrarCamas();
        estudioAdapter.borrarPersonasCama();
        estudioAdapter.borrarEncuestasParticipantes();
        estudioAdapter.borrarEncuestasDatosPartoBBs();
        estudioAdapter.borrarEncuestasPesoTallas();
        estudioAdapter.borrarEncuestasLactanciaMaternas();
        estudioAdapter.borrarMuestras();
        estudioAdapter.borrarVisitasTerreno();
        estudioAdapter.borrarParticipanteSeroprevalencia();
        estudioAdapter.borrarEncuestaCasaSA();
        estudioAdapter.borrarEncuestaParticipanteSA();
		try {
			if (mEstudios != null){
				v = mEstudios.size();
				ListIterator<Estudio> iter = mEstudios.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearEstudio(iter.next());
					publishProgress("Insertando estudios en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			if (mBarrios != null){
				v = mBarrios.size();
				ListIterator<Barrio> iter = mBarrios.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearBarrio(iter.next());
					publishProgress("Insertando barrios en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			if (mCasas != null){
				v = mCasas.size();
				ListIterator<Casa> iter = mCasas.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearCasa(iter.next());
					publishProgress("Insertando casas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			if (mParticipantes != null){
				v = mParticipantes.size();
				ListIterator<Participante> iter = mParticipantes.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearParticipante(iter.next());
					publishProgress("Insertando participantes en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
            if (mVisitas != null){
                v = mVisitas.size();
                ListIterator<VisitaTerreno> iter = mVisitas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVisitaTereno(iter.next());
                    publishProgress("Insertando visitas de terreno en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mPreTamizajes != null){
                v = mPreTamizajes.size();
                ListIterator<PreTamizaje> iter = mPreTamizajes.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearPreTamizaje(iter.next());
                    publishProgress("Insertando pretamizajes en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
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
            }
            if (mTamizajes != null){
                v = mTamizajes.size();
                ListIterator<Tamizaje> iter = mTamizajes.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearTamizaje(iter.next());
                    publishProgress("Insertando tamizajes en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mParticipantesCHF != null){
                v = mParticipantesCHF.size();
                ListIterator<ParticipanteCohorteFamilia> iter = mParticipantesCHF.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearParticipanteCohorteFamilia(iter.next());
                    publishProgress("Insertando participantes cohorte familia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mCartasConsent != null){
                v = mCartasConsent.size();
                ListIterator<CartaConsentimiento> iter = mCartasConsent.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCartaConsentimiento(iter.next());
                    publishProgress("Insertando cartas de consentimiento en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mEncuestasCasas != null){
                v = mEncuestasCasas.size();
                ListIterator<EncuestaCasa> iter = mEncuestasCasas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestaCasa(iter.next());
                    publishProgress("Insertando encuestas de casas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            //TODO: insertar cocina
            if (mCocinas != null){
                v = mCocinas.size();
                ListIterator<Cocina> iter = mCocinas.listIterator();
                while (iter.hasNext()){
                    //estudioAdapter.crea(iter.next());
                    publishProgress("Insertando cocinas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            //TODO: insertar comedor
            if (mComedores != null){
                v = mComedores.size();
                ListIterator<Comedor> iter = mComedores.listIterator();
                while (iter.hasNext()){
                    //estudioAdapter.crea(iter.next());
                    publishProgress("Insertando comedores en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            //TODO: insertar sala
            if (mSalas != null){
                v = mSalas.size();
                ListIterator<Sala> iter = mSalas.listIterator();
                while (iter.hasNext()){
                    //estudioAdapter.crea(iter.next());
                    publishProgress("Insertando salas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mHabitaciones != null){
                v = mHabitaciones.size();
                ListIterator<Habitacion> iter = mHabitaciones.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearHabitacion(iter.next());
                    publishProgress("Insertando habitaciones en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            //TODO: insertar banios
            if (mBanios != null){
                v = mBanios.size();
                ListIterator<Banio> iter = mBanios.listIterator();
                while (iter.hasNext()){
                    //estudioAdapter.crea(iter.next());
                    publishProgress("Insertando baños en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            //TODO: insertar ventanas
            if (mVentanas != null){
                v = mVentanas.size();
                ListIterator<Ventana> iter = mVentanas.listIterator();
                while (iter.hasNext()){
                    //estudioAdapter.crea(iter.next());
                    publishProgress("Insertando cocinas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mCamas != null){
                v = mCamas.size();
                ListIterator<Cama> iter = mCamas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCama(iter.next());
                    publishProgress("Insertando camas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mPersonaCamas != null){
                v = mPersonaCamas.size();
                ListIterator<PersonaCama> iter = mPersonaCamas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearPersonaCama(iter.next());
                    publishProgress("Insertando asociación persona cama en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mEncuestasParticipante != null){
                v = mEncuestasParticipante.size();
                ListIterator<EncuestaParticipante> iter = mEncuestasParticipante.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestasParticipante(iter.next());
                    publishProgress("Insertando encuestas de participantes en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mEncuestasDatosPartoBB != null){
                v = mEncuestasDatosPartoBB.size();
                ListIterator<EncuestaDatosPartoBB> iter = mEncuestasDatosPartoBB.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestasDatosPartoBB(iter.next());
                    publishProgress("Insertando encuestas de datos partos BB en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mEncuestasPesoTalla != null){
                v = mEncuestasPesoTalla.size();
                ListIterator<EncuestaPesoTalla> iter = mEncuestasPesoTalla.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestasPesoTalla(iter.next());
                    publishProgress("Insertando encuestas de peso y talla en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mEncuestasLacMat != null){
                v = mEncuestasLacMat.size();
                ListIterator<EncuestaLactanciaMaterna> iter = mEncuestasLacMat.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestasLactanciaMaterna(iter.next());
                    publishProgress("Insertando encuestas de lactancia materna en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mMuestras != null){
                v = mMuestras.size();
                ListIterator<Muestra> iter = mMuestras.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearMuestras(iter.next());
                    publishProgress("Insertando muestras en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mParticipantesSA != null){
                v = mParticipantesSA.size();
                ListIterator<ParticipanteSeroprevalencia> iter = mParticipantesSA.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearParticipanteSeroprevalencia(iter.next());
                    publishProgress("Insertando participantes seroprevalencia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mEncuestasCasaSA != null){
                v = mEncuestasCasaSA.size();
                ListIterator<EncuestaCasaSA> iter = mEncuestasCasaSA.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestaCasaSA(iter.next());
                    publishProgress("Insertando encuestas de casa seroprevalencia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
            }
            if (mEncuestasParticipanteSA != null){
                v = mEncuestasParticipanteSA.size();
                ListIterator<EncuestaParticipanteSA> iter = mEncuestasParticipanteSA.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestaParticipanteSA(iter.next());
                    publishProgress("Insertando encuestas de participantes seroprevalencia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
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
    protected String descargarDatos() throws Exception {
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
            publishProgress("Solicitando estudios",ESTUDIO,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Estudio[]> responseEntityEstudio = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Estudio[].class);
            // convert the array to a list and return it
            mEstudios = Arrays.asList(responseEntityEstudio.getBody());
            //Descargar barrios
            urlRequest = url + "/movil/barrios/";
            publishProgress("Solicitando barrios",BARRIO,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Barrio[]> responseEntityBarrio = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Barrio[].class);
            // convert the array to a list and return it
            mBarrios = Arrays.asList(responseEntityBarrio.getBody());
            //Descargar casas

            urlRequest = url + "/movil/casas/";
            publishProgress("Solicitando casas",CASA,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Casa[]> responseEntityCasa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Casa[].class);
            // convert the array to a list and return it
            mCasas = Arrays.asList(responseEntityCasa.getBody());
            //Descargar participantes
            urlRequest = url + "/movil/participantes/";
            publishProgress("Solicitando participantes",PARTICIPANTE,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Participante[]> responseEntityParticipante = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Participante[].class);
            // convert the array to a list and return it
            mParticipantes = Arrays.asList(responseEntityParticipante.getBody());

            //Descargar visitas terreno
            urlRequest = url + "/movil/visitas/";
            publishProgress("Solicitando visitas de terreno",VISITAS,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<VisitaTerreno[]> responseEntityVisitas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    VisitaTerreno[].class);
            // convert the array to a list and return it
            mVisitas = Arrays.asList(responseEntityVisitas.getBody());

            //Descargar pretamizajes
            urlRequest = url + "/movil/preTamizajes/";
            publishProgress("Solicitando pretamizajes",PRETAMIZAJE,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<PreTamizaje[]> responseEntityPretamizaje = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    PreTamizaje[].class);
            // convert the array to a list and return it
            mPreTamizajes = Arrays.asList(responseEntityPretamizaje.getBody());

            //Descargar casascohorte familia
            urlRequest = url + "/movil/casasCHF/";
            publishProgress("Solicitando casas cohorte familia",CASACHF,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<CasaCohorteFamilia[]> responseEntityCasasCHF = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    CasaCohorteFamilia[].class);
            // convert the array to a list and return it
            mCasasCHF = Arrays.asList(responseEntityCasasCHF.getBody());

            //Descargar tamizajes
            urlRequest = url + "/movil/tamizajes/";
            publishProgress("Solicitando tamizajes",TAMIZAJE,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Tamizaje[]> responseEntityTamizajes = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Tamizaje[].class);
            // convert the array to a list and return it
            mTamizajes = Arrays.asList(responseEntityTamizajes.getBody());

            //Descargar participantes cohorte
            urlRequest = url + "/movil/participantesCHF/";
            publishProgress("Solicitando participantes de cohorte familia",PARTICIPANTECHF,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteCohorteFamilia[]> responseEntityParticipantesCHF = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteCohorteFamilia[].class);
            // convert the array to a list and return it
            mParticipantesCHF = Arrays.asList(responseEntityParticipantesCHF.getBody());

            //Descargar cartas de consetimiento
            urlRequest = url + "/movil/cartasConsen/";
            publishProgress("Solicitando cartas de consentimiento",CARTAS_CONSENT,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<CartaConsentimiento[]> responseEntityCartas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    CartaConsentimiento[].class);
            // convert the array to a list and return it
            mCartasConsent = Arrays.asList(responseEntityCartas.getBody());

            //Descargar cartas de consetimiento
            urlRequest = url + "/movil/encuestasCasa/";
            publishProgress("Solicitando encuestas de casas",ENCUESTA_CASACHF,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaCasa[]> responseEntityEncCasa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaCasa[].class);
            // convert the array to a list and return it
            mEncuestasCasas = Arrays.asList(responseEntityEncCasa.getBody());

            //Descargar cocinas
            urlRequest = url + "/movil/cocinas/";
            publishProgress("Solicitando cocinas",COCINA,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Cocina[]> responseEntityCocinas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Cocina[].class);
            // convert the array to a list and return it
            mCocinas = Arrays.asList(responseEntityCocinas.getBody());

            //Descargar comedores
            urlRequest = url + "/movil/comedores/";
            publishProgress("Solicitando comedores",COMEDOR,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Comedor[]> responseEntityComedor = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Comedor[].class);
            // convert the array to a list and return it
            mComedores = Arrays.asList(responseEntityComedor.getBody());

            //Descargar salas
            urlRequest = url + "/movil/salas/";
            publishProgress("Solicitando salas",SALA,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Sala[]> responseEntitySalas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Sala[].class);
            // convert the array to a list and return it
            mSalas = Arrays.asList(responseEntitySalas.getBody());

            //Descargar habitaciones
            urlRequest = url + "/movil/habitaciones/";
            publishProgress("Solicitando habitaciones",HABITACION,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Habitacion[]> responseEntityHab = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Habitacion[].class);
            // convert the array to a list and return it
            mHabitaciones = Arrays.asList(responseEntityHab.getBody());

            //Descargar banios
            urlRequest = url + "/movil/banios/";
            publishProgress("Solicitando baños",BANIOS,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Banio[]> responseEntityBanios = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Banio[].class);
            // convert the array to a list and return it
            mBanios = Arrays.asList(responseEntityBanios.getBody());

            //Descargar ventanas
            urlRequest = url + "/movil/ventanas/";
            publishProgress("Solicitando ventanas",VENTANAS,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Ventana[]> responseEntityVentanas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Ventana[].class);
            // convert the array to a list and return it
            mVentanas = Arrays.asList(responseEntityVentanas.getBody());

            //Descargar Camas
            urlRequest = url + "/movil/camas/";
            publishProgress("Solicitando camas",CAMAS,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Cama[]> responseEntityCamas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Cama[].class);
            // convert the array to a list and return it
            mCamas = Arrays.asList(responseEntityCamas.getBody());

            //Descargar Personas Camas
            urlRequest = url + "/movil/personasCamas/";
            publishProgress("Solicitando Personas Camas",PERSONAS_CAMA,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<PersonaCama[]> responseEntityPerCamas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    PersonaCama[].class);
            // convert the array to a list and return it
            mPersonaCamas = Arrays.asList(responseEntityPerCamas.getBody());

            //Descargar encuestasParticipante
            urlRequest = url + "/movil/encuestasParticipante/";
            publishProgress("Solicitando Encuestas de Participantes",ENCUESTA_PARTICIPANTECHF,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaParticipante[]> responseEntityEncPar = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaParticipante[].class);
            // convert the array to a list and return it
            mEncuestasParticipante = Arrays.asList(responseEntityEncPar.getBody());

            //Descargar encuestas datos parto bb
            urlRequest = url + "/movil/encuestasDatosPartoBB/";
            publishProgress("Solicitando Encuestas de datos parto BB",ENCUESTA_DATOSPBB,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaDatosPartoBB[]> responseEntityEncParto = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaDatosPartoBB[].class);
            // convert the array to a list and return it
            mEncuestasDatosPartoBB = Arrays.asList(responseEntityEncParto.getBody());

            //Descargar encuestas peso y talla
            urlRequest = url + "/movil/encuestasPesoTalla/";
            publishProgress("Solicitando Encuestas de peso y talla",ENCUESTA_PESOTALLA,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaPesoTalla[]> responseEntityEncPT = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaPesoTalla[].class);
            // convert the array to a list and return it
            mEncuestasPesoTalla = Arrays.asList(responseEntityEncPT.getBody());

            //Descargar encuestas peso y talla
            urlRequest = url + "/movil/encuestasLactanciaMaterna/";
            publishProgress("Solicitando Encuestas de lactancia materna",ENCUESTA_LACTMAT,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaLactanciaMaterna[]> responseEntityEncLactMat = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaLactanciaMaterna[].class);
            // convert the array to a list and return it
            mEncuestasLacMat = Arrays.asList(responseEntityEncLactMat.getBody());

            //Descargar muestras
            urlRequest = url + "/movil/muestras/";
            publishProgress("Solicitando Muestras",MUESTRAS,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Muestra[]> responseEntityMuestras = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Muestra[].class);
            // convert the array to a list and return it
            mMuestras = Arrays.asList(responseEntityMuestras.getBody());

            //Descargar participantes seroprevalencia
            urlRequest = url + "/movil/participantesSA/";
            publishProgress("Solicitando participantes seroprevalencia",PARTICIPANTESA,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteSeroprevalencia[]> responseEntityPartiSa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteSeroprevalencia[].class);
            // convert the array to a list and return it
            mParticipantesSA = Arrays.asList(responseEntityPartiSa.getBody());

            //Descargar encuestas casas seroprevalencia
            urlRequest = url + "/movil/encuestasCasaSA/";
            publishProgress("Solicitando encuestas de casas seroprevalencia",ENCUESTA_CASASA,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaCasaSA[]> responseEntityEncuCasaSa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaCasaSA[].class);
            // convert the array to a list and return it
            mEncuestasCasaSA = Arrays.asList(responseEntityEncuCasaSa.getBody());

            //Descargar encuestas participantes seroprevalencia
            urlRequest = url + "/movil/encuestasParticipanteSA/";
            publishProgress("Solicitando encuestas de participantes seroprevalencia",ENCUESTA_PARTICIPANTESA,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaParticipanteSA[]> responseEntityEncuPartiSa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaParticipanteSA[].class);
            // convert the array to a list and return it
            mEncuestasParticipanteSA = Arrays.asList(responseEntityEncuPartiSa.getBody());
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
