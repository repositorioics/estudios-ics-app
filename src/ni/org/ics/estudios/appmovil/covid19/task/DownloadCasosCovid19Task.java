package ni.org.ics.estudios.appmovil.covid19.task;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.DownloadTask;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.covid19.*;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class DownloadCasosCovid19Task extends DownloadTask {

	private final Context mContext;

	public DownloadCasosCovid19Task(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadCasosCovid19Task.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;

	private List<CasoCovid19> mCasos = null;
    private List<ParticipanteCasoCovid19> mParticipantesCasos = null;
    private List<VisitaSeguimientoCasoCovid19> mVisitas = null;
    private List<SintomasVisitaCasoCovid19> mSintomas = null;
    private List<Muestra> mMuestras = null;
    private List<VisitaFallidaCasoCovid19> mVisitasFall = null;
    private List<CandidatoTransmisionCovid19> mCandidatos = null;
    private List<DatosAislamientoVisitaCasoCovid19> mAislamientos = null;
    private List<ParticipanteProcesos> mParticipantesProc = null;
    private List<VisitaFinalCasoCovid19> mVisitasFinales = null;
    private List<SintomasVisitaFinalCovid19> mSintomasFinal = null;
    private List<OtrosPositivosCovid> mOtrosPositivos = null;


    public static final String CASOS_COVID19 = "1";
    public static final String PARTICIPANTE_COVID19 = "2";
    public static final String VISITAS_CASOS_COVID19 = "3";
    public static final String MUESTRAS_COVID19 = "4";
    public static final String SINT_VISITAS_CASOS_COVID19 = "5";
    public static final String VISITAS_FALL_CASOS_COVID19 = "6";
    public static final String CANDIDATOS_COVID19 = "7";
    public static final String DATOS_AISLAMIENTO_COVID19 = "8";
    public static final String PARTICIPANTE_PROC = "9";
    public static final String VISITAS_FINALES_COVID19 = "10";
    public static final String SINT_VISITAS_FINALES_COVID19 = "11";
    public static final String OTROS_POSITIVOS = "12";

    private static final String TOTAL_TASK_CASOS = "12";

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

        
		try {
            estudioAdapter.open();
            //Borrar los datos de la base de datos
            estudioAdapter.borrarTodosParticipantesProcesos();
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
            estudioAdapter.borrarOtrosPositivosCovid();
            if (mCasos != null){
                publishProgress("Insertando seguimientos COVID19 en la base de datos...",CASOS_COVID19 , TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCovidBySql(Covid19DBConstants.COVID_CASOS_TABLE, mCasos);
            }
            if (mParticipantesCasos != null){
                publishProgress("Insertando participantes en seguimiento COVID19 en la base de datos...", PARTICIPANTE_COVID19, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCovidBySql(Covid19DBConstants.COVID_PARTICIPANTES_CASOS_TABLE, mParticipantesCasos);
            }
            if (mVisitas != null){
                publishProgress("Insertando visitas de participantes en seguimiento COVID19 en la base de datos...", VISITAS_CASOS_COVID19, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCovidBySql(Covid19DBConstants.COVID_VISITAS_CASOS_TABLE, mVisitas);
            }
            if (mMuestras != null){
                publishProgress("Insertando muestras de casos COVID19 en la base de datos...", MUESTRAS_COVID19, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertMuestrasChfBySql(mMuestras);
            }
            if (mSintomas != null){
                publishProgress("Insertando sintomas de participantes en seguimiento COVID19 en la base de datos...", SINT_VISITAS_CASOS_COVID19, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCovidBySql(Covid19DBConstants.COVID_SINTOMAS_VISITA_CASO_TABLE, mSintomas);
            }
            if (mVisitasFall != null){
                publishProgress("Insertando visitas fallidas de casos COVID19 en la base de datos...", VISITAS_FALL_CASOS_COVID19, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCovidBySql(Covid19DBConstants.COVID_VISITAS_FALLIDAS_CASOS_TABLE, mVisitasFall);
            }
            if (mCandidatos != null){
                publishProgress("Insertando candidatos transmisión COVID19 en la base de datos...", CANDIDATOS_COVID19, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCovidBySql(Covid19DBConstants.COVID_CANDIDATO_TRANSMISION_TABLE, mCandidatos);
            }
            if (mAislamientos != null){
                publishProgress("Insertando datos aislamiento COVID19 en la base de datos...", DATOS_AISLAMIENTO_COVID19, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCovidBySql(Covid19DBConstants.COVID_DATOS_AISLAMIENTO_VC_TABLE, mAislamientos);
            }
            if (mParticipantesProc != null){
                publishProgress("Insertando procesos en la base de datpos...", PARTICIPANTE_PROC, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertParticipantesProcBySql(mParticipantesProc);
            }
            if (mVisitasFinales != null){
                publishProgress("Insertando datos visitas finales COVID19 en la base de datos...", VISITAS_FINALES_COVID19, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCovidBySql(Covid19DBConstants.COVID_VISITA_FINAL_CASO_TABLE, mVisitasFinales);
            }
            if (mSintomasFinal != null){
                publishProgress("Insertando datos síntomas visitas finales COVID19 en la base de datos...", SINT_VISITAS_FINALES_COVID19, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCovidBySql(Covid19DBConstants.COVID_SINT_VISITA_FINAL_CASO_TABLE, mSintomasFinal);
            }
            if (mOtrosPositivos != null){
                publishProgress("Insertando datos otros positivos COVID19 en la base de datos...", OTROS_POSITIVOS, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCovidBySql(Covid19DBConstants.COVID_OTROS_POSITIVOS_TABLE, mOtrosPositivos);
            }

        } catch (Exception e) {
            // Regresa error al insertar
            e.printStackTrace();
            return e.getLocalizedMessage();
        } finally {
            estudioAdapter.close();
        }

		return error;
	}

    
    // url, username, password
    protected String descargarDatosCasos() throws Exception{
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

            // Descargando participantes COVID19
            urlRequest = url + "/movil/casosCovid19";
            publishProgress("Solicitando seguimientos COVID19", CASOS_COVID19,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<CasoCovid19[]> responseEntityCasos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    CasoCovid19[].class);

            // convert the array to a list and return it
            mCasos = Arrays.asList(responseEntityCasos.getBody());

            // Descargando participantes COVID19
            urlRequest = url + "/movil/participantesCasoCovid19";
            publishProgress("Solicitando participantes seguimiento COVID19", PARTICIPANTE_COVID19,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<ParticipanteCasoCovid19[]> responseEntityPart = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteCasoCovid19[].class);

            // convert the array to a list and return it
            mParticipantesCasos = Arrays.asList(responseEntityPart.getBody());

            // Descargando visitas seguimiento participantes COVID19
            urlRequest = url + "/movil/visitasSeguimientosCasoCovid19";
            publishProgress("Solicitando participantes seguimiento COVID19", VISITAS_CASOS_COVID19,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<VisitaSeguimientoCasoCovid19[]> responseEntityVis = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    VisitaSeguimientoCasoCovid19[].class);

            // convert the array to a list and return it
            mVisitas = Arrays.asList(responseEntityVis.getBody());

            // Descargando MUESTRAS Covid19
            urlRequest = url + "/movil/muestrasCasosCovid19";
            publishProgress("Solicitando muestras COVID19",MUESTRAS_COVID19,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<Muestra[]> responseEntityMx = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Muestra[].class);

            // convert the array to a list and return it
            mMuestras = Arrays.asList(responseEntityMx.getBody());

            // Descargando Sintomas Casos Covid19
            urlRequest = url + "/movil/sintomasCasosCovid19";
            publishProgress("Solicitando síntomas casos COVID19",SINT_VISITAS_CASOS_COVID19,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<SintomasVisitaCasoCovid19[]> responseEntitySin = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    SintomasVisitaCasoCovid19[].class);

            // convert the array to a list and return it
            mSintomas = Arrays.asList(responseEntitySin.getBody());

            // Descargando visitas fallidas participantes COVID19
            urlRequest = url + "/movil/visitasFallidasCasoCovid19";
            publishProgress("Solicitando participantes seguimiento COVID19", VISITAS_FALL_CASOS_COVID19,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<VisitaFallidaCasoCovid19[]> responseEntityVisF = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    VisitaFallidaCasoCovid19[].class);

            // convert the array to a list and return it
            mVisitasFall = Arrays.asList(responseEntityVisF.getBody());

            // Descargando Candidatos estudio Transmision COVID19
            urlRequest = url + "/movil/candidatosTCovid19";
            publishProgress("Solicitando participantes seguimiento COVID19", CANDIDATOS_COVID19,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<CandidatoTransmisionCovid19[]> responseEntityCand = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    CandidatoTransmisionCovid19[].class);

            // convert the array to a list and return it
            mCandidatos = Arrays.asList(responseEntityCand.getBody());

            // Descargando Datos Aislamientos seguimientos COVID19
            urlRequest = url + "/movil/datosAislamientoCovid19";
            publishProgress("Solicitando Datos Aislamientos seguimientos COVID19", DATOS_AISLAMIENTO_COVID19,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<DatosAislamientoVisitaCasoCovid19[]> responseEntityDA = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    DatosAislamientoVisitaCasoCovid19[].class);

            // convert the array to a list and return it
            mAislamientos = Arrays.asList(responseEntityDA.getBody());

            //Descargando participantes procesos covid19
            // The URL for making the GET request
            urlRequest = url + "/movil/participantesprocesos";
            publishProgress("Solicitando procesos de participantes",PARTICIPANTE_PROC,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<ParticipanteProcesos[]> responseEntityPartProc = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteProcesos[].class);

            // convert the array to a list and return it
            mParticipantesProc = Arrays.asList(responseEntityPartProc.getBody());

            //Descargando visitas finales covid19
            // The URL for making the GET request
            urlRequest = url + "/movil/visitasFinalesCovid19";
            publishProgress("Solicitando visitas finales COVID19",VISITAS_FINALES_COVID19,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<VisitaFinalCasoCovid19[]> responseEntityVF = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    VisitaFinalCasoCovid19[].class);

            // convert the array to a list and return it
            mVisitasFinales = Arrays.asList(responseEntityVF.getBody());

            //Descargando síntomas visitas finales covid19
            // The URL for making the GET request
            urlRequest = url + "/movil/sintVisitasFinalesCovid19";
            publishProgress("Solicitando síntomas visitas finales COVID19",SINT_VISITAS_FINALES_COVID19,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<SintomasVisitaFinalCovid19[]> responseEntitySVF = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    SintomasVisitaFinalCovid19[].class);

            // convert the array to a list and return it
            mSintomasFinal = Arrays.asList(responseEntitySVF.getBody());

            //Descargando otros positivos covid19 que no son indices
            // The URL for making the GET request
            urlRequest = url + "/movil/otrosPositivosCovid19";
            publishProgress("Solicitando otros positivos COVID19",OTROS_POSITIVOS,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<OtrosPositivosCovid[]> responseEntityOP = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    OtrosPositivosCovid[].class);

            // convert the array to a list and return it
            mOtrosPositivos = Arrays.asList(responseEntityOP.getBody());


            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
