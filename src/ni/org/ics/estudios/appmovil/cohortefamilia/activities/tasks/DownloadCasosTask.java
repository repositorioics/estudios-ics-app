package ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.ObsequioGeneral;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.MuestraSuperficie;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.*;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
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



public class DownloadCasosTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadCasosTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadCasosTask.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;
	
    
    private List<CasaCohorteFamiliaCaso> mCasaCohorteFamiliaCasos = null;
    private List<ParticipanteCohorteFamiliaCaso> mParticipanteCohorteFamiliaCasos = null;
    private List<VisitaSeguimientoCaso> mVisitaSeguimientoCasos = null;
    private List<VisitaFallidaCaso> mVisitaFallidaCasos = null;
    private List<VisitaSeguimientoCasoSintomas> mVisitaSeguimientoSintomasCasos = null;
    private List<FormularioContactoCaso> mFormularioContactoCasos = null;
    private List<Muestra> mMuestras = null;
    private List<MuestraSuperficie> mMuestrasSup = null;
	private List<VisitaFinalCaso> mVisitaFinalCasos = null;
    private List<ObsequioGeneral> mObsequios = null;
    private List<ParticipanteProcesos> mParticipantesProc = null;
    private List<SensorCaso> mSensoresCasos = null;

    public static final String PARTICIPANTE_PROC = "1";
    public static final String CASAS_CASOS = "2";
    public static final String PART_CASOS = "3";
    public static final String VISITAS_CASOS = "4";
    public static final String VISITAS_FALLIDAS_CASOS = "5";
    public static final String SINTOMAS_CASOS = "6";
    public static final String VISITAS_FINALES = "7";
    public static final String OBSEQUIOS = "8";
    public static final String CONTACTOS_CASOS = "1";
    public static final String MUESTRAS = "2";
    public static final String NODATA_CASOS = "3";
    public static final String MUESTRAS_SUP = "4";
    public static final String SENSORES_CASOS = "9";

    private static final String TOTAL_TASK_CASOS = "9";
    private static final String TOTAL_TASK_CASOS_CONTACTO = "4";

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
			error = descargarDatosCasos();
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
        estudioAdapter.borrarTodosParticipantesProcesos();
        estudioAdapter.borrarCasaCohorteFamiliaCaso();
        estudioAdapter.borrarParticipanteCohorteFamiliaCaso();
        estudioAdapter.borrarVisitaSeguimientoCaso();
        estudioAdapter.borrarVisitaFallidaCaso();
        estudioAdapter.borrarVisitaSeguimientoCasoSintomas();
        estudioAdapter.borrarVisitaFinalCaso();
        estudioAdapter.borrarObsequiosGenerales();
        estudioAdapter.borrarSensoresCasos();
        /*estudioAdapter.borrarInformacionNoCompletaCaso();
        estudioAdapter.borrarFormularioContactoCaso();
        estudioAdapter.borrarMuestrasTx();*/
        
		try {
            if (mParticipantesProc != null){
                v = mParticipantesProc.size();
                ListIterator<ParticipanteProcesos> iter = mParticipantesProc.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearParticipanteProcesos(iter.next());
                    publishProgress("Insertando procesos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mParticipantesProc = null;
            }
            if (mCasaCohorteFamiliaCasos != null){
                v = mCasaCohorteFamiliaCasos.size();
                ListIterator<CasaCohorteFamiliaCaso> iter = mCasaCohorteFamiliaCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCasaCohorteFamiliaCaso(iter.next());
                    publishProgress("Insertando casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mCasaCohorteFamiliaCasos = null;
            }
            if (mParticipanteCohorteFamiliaCasos != null){
                v = mParticipanteCohorteFamiliaCasos.size();
                ListIterator<ParticipanteCohorteFamiliaCaso> iter = mParticipanteCohorteFamiliaCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearParticipanteCohorteFamiliaCaso(iter.next());
                    publishProgress("Insertando participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mParticipanteCohorteFamiliaCasos = null;
            }
            
            if (mVisitaSeguimientoCasos != null){
                v = mVisitaSeguimientoCasos.size();
                ListIterator<VisitaSeguimientoCaso> iter = mVisitaSeguimientoCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVisitaSeguimientoCaso(iter.next());
                    publishProgress("Insertando visitas de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVisitaSeguimientoCasos = null;
            }
            
            if (mVisitaFallidaCasos != null){
                v = mVisitaFallidaCasos.size();
                ListIterator<VisitaFallidaCaso> iter = mVisitaFallidaCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVisitaFallidaCaso(iter.next());
                    publishProgress("Insertando visitas fallidas de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVisitaFallidaCasos = null;
            }

            if (mVisitaSeguimientoSintomasCasos != null){
                v = mVisitaSeguimientoSintomasCasos.size();
                ListIterator<VisitaSeguimientoCasoSintomas> iter = mVisitaSeguimientoSintomasCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVisitaSeguimientoCasoSintomas(iter.next());
                    publishProgress("Insertando sintomas de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVisitaSeguimientoSintomasCasos = null;
            }
            if (mVisitaFinalCasos != null){
                v = mVisitaFinalCasos.size();
                ListIterator<VisitaFinalCaso> iter = mVisitaFinalCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVisitaFinalCaso(iter.next());
                    publishProgress("Insertando visitas finales de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVisitaFinalCasos = null;
            }
            if (mObsequios != null){
                v = mObsequios.size();
                ListIterator<ObsequioGeneral> iter = mObsequios.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearObsequioGeneral(iter.next());
                    publishProgress("Insertando obsequios en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mObsequios = null;
            }
            if (mSensoresCasos != null){
                v = mSensoresCasos.size();
                ListIterator<SensorCaso> iter = mSensoresCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearSensorCaso(iter.next());
                    publishProgress("Insertando sensores de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mSensoresCasos = null;
            }
            /*
            if (mFormularioContactoCasos != null){
                v = mFormularioContactoCasos.size();
                ListIterator<FormularioContactoCaso> iter = mFormularioContactoCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearFormularioContactoCaso(iter.next());
                    publishProgress("Insertando contactos de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mFormularioContactoCasos = null;
            }
            
            if (mInformacionNoCompletaCasos != null){
                v = mInformacionNoCompletaCasos.size();
                ListIterator<InformacionNoCompletaCaso> iter = mInformacionNoCompletaCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearInformacionNoCompletaCaso(iter.next());
                    publishProgress("Insertando no data de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mInformacionNoCompletaCasos = null;
            }
            if (mMuestras != null){
                v = mMuestras.size();
                ListIterator<Muestra> iter = mMuestras.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearMuestras(iter.next());
                    publishProgress("Insertando muestras de transmision de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mInformacionNoCompletaCasos = null;
            }*/
        } catch (Exception e) {
            // Regresa error al insertar
            e.printStackTrace();
            estudioAdapter.close();
            return e.getLocalizedMessage();
        }

        //PARTE 2
        try {
            error = descargarDatosCasosContactos();
            if (error!=null) return error;
        } catch (Exception e) {
            // Regresa error al descargar
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
        publishProgress("Abriendo base de datos...","1","1");
        //Borrar los datos de la base de datos
        estudioAdapter.borrarFormularioContactoCaso();
        estudioAdapter.borrarMuestrasTx();
        estudioAdapter.borrarMuestrasSuperficie();

        try {

            if (mFormularioContactoCasos != null){
                v = mFormularioContactoCasos.size();
                ListIterator<FormularioContactoCaso> iter = mFormularioContactoCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearFormularioContactoCaso(iter.next());
                    publishProgress("Insertando contactos de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mFormularioContactoCasos = null;
            }

            if (mMuestras != null){
                v = mMuestras.size();
                ListIterator<Muestra> iter = mMuestras.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearMuestras(iter.next());
                    publishProgress("Insertando muestras de transmision de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mMuestras = null;
            }
            if (mMuestrasSup != null){
                v = mMuestrasSup.size();
                ListIterator<MuestraSuperficie> iter = mMuestrasSup.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearMuestraSuperficie(iter.next());
                    publishProgress("Insertando muestras de superficie de casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mMuestrasSup = null;
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
    protected String descargarDatosCasos() throws Exception {
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
            // The URL for making the GET request
            urlRequest = url + "/movil/participantesprocesos";
            publishProgress("Solicitando procesos de participantes",PARTICIPANTE_PROC,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<ParticipanteProcesos[]> responseEntityPartProc = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteProcesos[].class);

            // convert the array to a list and return it
            mParticipantesProc = Arrays.asList(responseEntityPartProc.getBody());

            //Descargar casas con casos
            urlRequest = url + "/movil/casascasos/";
            publishProgress("Solicitando casas de casos",CASAS_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<CasaCohorteFamiliaCaso[]> responseEntityCasaCohorteFamiliaCasos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		CasaCohorteFamiliaCaso[].class);
            // convert the array to a list and return it
            mCasaCohorteFamiliaCasos = Arrays.asList(responseEntityCasaCohorteFamiliaCasos.getBody());
            responseEntityCasaCohorteFamiliaCasos = null;
            
            //Descargar participantes de casas con casos
            urlRequest = url + "/movil/participantescasos/";
            publishProgress("Solicitando participantes de casas de casos",PART_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteCohorteFamiliaCaso[]> responseEntityParticipanteCohorteFamiliaCasos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		ParticipanteCohorteFamiliaCaso[].class);
            // convert the array to a list and return it
            mParticipanteCohorteFamiliaCasos = Arrays.asList(responseEntityParticipanteCohorteFamiliaCasos.getBody());
            responseEntityParticipanteCohorteFamiliaCasos = null;
            
            //Descargar visitas de casas con casos
            urlRequest = url + "/movil/visitascasos/";
            publishProgress("Solicitando visitas de los participantes de casas de casos",VISITAS_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<VisitaSeguimientoCaso[]> responseEntityVisitaSeguimientoCasos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		VisitaSeguimientoCaso[].class);
            // convert the array to a list and return it
            mVisitaSeguimientoCasos = Arrays.asList(responseEntityVisitaSeguimientoCasos.getBody());
            responseEntityVisitaSeguimientoCasos = null;
            
            //Descargar visitas fallidas de casas con casos
            urlRequest = url + "/movil/visitasfallidascasos/";
            publishProgress("Solicitando visitas fallidas de los participantes de casas de casos",VISITAS_FALLIDAS_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<VisitaFallidaCaso[]> responseEntityVisitaFallidaCasos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		VisitaFallidaCaso[].class);
            // convert the array to a list and return it
            mVisitaFallidaCasos = Arrays.asList(responseEntityVisitaFallidaCasos.getBody());
            responseEntityVisitaFallidaCasos = null;

            //Descargar sintomas de casas con casos
            urlRequest = url + "/movil/sintomascasos/";
            publishProgress("Solicitando sintomas de los participantes de casas de casos",SINTOMAS_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<VisitaSeguimientoCasoSintomas[]> responseEntityVisitaSeguimientoCasoSintomas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		VisitaSeguimientoCasoSintomas[].class);
            // convert the array to a list and return it
            mVisitaSeguimientoSintomasCasos = Arrays.asList(responseEntityVisitaSeguimientoCasoSintomas.getBody());
            responseEntityVisitaSeguimientoCasoSintomas = null;

            //Descargar visitas finales de casas con casos
            urlRequest = url + "/movil/visitasfinalescasos/";
            publishProgress("Solicitando visitas finales de los participantes de casas de casos", VISITAS_FINALES,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<VisitaFinalCaso[]> responseEntityVisitasFinales = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    VisitaFinalCaso[].class);
            // convert the array to a list and return it
            mVisitaFinalCasos = Arrays.asList(responseEntityVisitasFinales.getBody());
            responseEntityVisitasFinales = null;

            //Descargar visitas finales de casas con casos
            urlRequest = url + "/movil/obsequiosgen/";
            publishProgress("Solicitando obsequios", OBSEQUIOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<ObsequioGeneral[]> responseEntityObsequios = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ObsequioGeneral[].class);
            // convert the array to a list and return it
            mObsequios = Arrays.asList(responseEntityObsequios.getBody());
            //Descargar sensores de casos en seguimiento
            urlRequest = url + "/movil/sensorescasos/";
            publishProgress("Solicitando sensores de casos", SENSORES_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<SensorCaso[]> responseEntitySensores = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    SensorCaso[].class);
            // convert the array to a list and return it
            mSensoresCasos = Arrays.asList(responseEntitySensores.getBody());
            responseEntitySensores = null;
            /*
            //Descargar contactos de casas con casos
            urlRequest = url + "/movil/contactoscasos/";
            publishProgress("Solicitando contactos de los participantes de casas de casos",CONTACTOS_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<FormularioContactoCaso[]> responseEntityFormularioContactoCaso = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		FormularioContactoCaso[].class);
            // convert the array to a list and return it
            mFormularioContactoCasos = Arrays.asList(responseEntityFormularioContactoCaso.getBody());
            responseEntityFormularioContactoCaso = null;
            
            //Descargar info de no data de casas con casos
            urlRequest = url + "/movil/visitasnodatacasos/";
            publishProgress("Solicitando registros sin datos de los participantes de casas de casos",NODATA_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<InformacionNoCompletaCaso[]> responseEntityInformacionNoCompletaCaso = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		InformacionNoCompletaCaso[].class);
            // convert the array to a list and return it
            mInformacionNoCompletaCasos = Arrays.asList(responseEntityInformacionNoCompletaCaso.getBody());
            responseEntityInformacionNoCompletaCaso = null;
            
            //Descargar muestras de tx de casas con casos
            urlRequest = url + "/movil/mxstx/";
            publishProgress("Solicitando muestras de tx de los participantes de casas de casos",NODATA_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<Muestra[]> responseEntityMuestraCaso = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Muestra[].class);
            // convert the array to a list and return it
            mMuestras = Arrays.asList(responseEntityMuestraCaso.getBody());
            responseEntityMuestraCaso = null;
            */
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarDatosCasosContactos() throws Exception {
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

            //Descargar contactos de casas con casos
            urlRequest = url + "/movil/contactoscasos/";
            publishProgress("Solicitando contactos de los participantes de casas de casos",CONTACTOS_CASOS, TOTAL_TASK_CASOS_CONTACTO);
            // Perform the HTTP GET request
            ResponseEntity<FormularioContactoCaso[]> responseEntityFormularioContactoCaso = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    FormularioContactoCaso[].class);
            // convert the array to a list and return it
            mFormularioContactoCasos = Arrays.asList(responseEntityFormularioContactoCaso.getBody());
            responseEntityFormularioContactoCaso = null;

            //Descargar muestras de tx de casas con casos
            urlRequest = url + "/movil/mxstx/";
            publishProgress("Solicitando muestras de tx de los participantes de casas de casos",MUESTRAS, TOTAL_TASK_CASOS_CONTACTO);
            // Perform the HTTP GET request
            ResponseEntity<Muestra[]> responseEntityMuestraCaso = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Muestra[].class);
            // convert the array to a list and return it
            mMuestras = Arrays.asList(responseEntityMuestraCaso.getBody());
            responseEntityMuestraCaso = null;
            //Descargar muestras de superficie del los casos activos
            urlRequest = url + "/movil/muestrasSuperficie/";
            publishProgress("Solicitando muesstras de superficie casos activos", MUESTRAS_SUP,TOTAL_TASK_CASOS_CONTACTO);
            // Perform the HTTP GET request
            ResponseEntity<MuestraSuperficie[]> responseEntityMxSup = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    MuestraSuperficie[].class);
            // convert the array to a list and return it
            mMuestrasSup = Arrays.asList(responseEntityMxSup.getBody());

            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
