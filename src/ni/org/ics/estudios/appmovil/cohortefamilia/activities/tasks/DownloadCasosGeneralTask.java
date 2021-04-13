package ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.*;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
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

public class DownloadCasosGeneralTask extends DownloadTask {

    private final Context mContext;

    public DownloadCasosGeneralTask(Context context) {
        mContext = context;
    }

    protected static final String TAG = DownloadCasosGeneralTask.class.getSimpleName();
    private EstudiosAdapter estudioAdapter = null;

    private List<CasaCohorteFamiliaCaso> mCasaCohorteFamiliaCasos = null;
    private List<ParticipanteCohorteFamiliaCaso> mParticipanteCohorteFamiliaCasos = null;
    private List<VisitaSeguimientoCaso> mVisitaSeguimientoCasos = null;
    private List<VisitaFallidaCaso> mVisitaFallidaCasos = null;
    private List<VisitaSeguimientoCasoSintomas> mVisitaSeguimientoSintomasCasos = null;
    private List<SensorCaso> mSensoresCasos = null;

    public static final String CASAS_CASOS = "1";
    public static final String PART_CASOS = "2";
    public static final String VISITAS_CASOS = "3";
    public static final String VISITAS_FALLIDAS_CASOS = "4";
    public static final String SINTOMAS_CASOS = "5";
    public static final String SENSORES_CASOS = "6";

    private static final String TOTAL_TASK_CASOS = "6";

    private String error = null;
    private String url = null;
    private String username = null;
    private String password = null;

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
        estudioAdapter.borrarCasaCohorteFamiliaCaso();
        estudioAdapter.borrarParticipanteCohorteFamiliaCaso();
        estudioAdapter.borrarVisitaSeguimientoCaso();
        estudioAdapter.borrarVisitaFallidaCaso();
        estudioAdapter.borrarVisitaSeguimientoCasoSintomas();
        estudioAdapter.borrarSensoresCasos();

        try {
            if (mCasaCohorteFamiliaCasos != null){
                publishProgress("Insertando casas con casos en la base de datos...", CASAS_CASOS, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCasosChfBySql(CasosDBConstants.CASAS_CASOS_TABLE, mCasaCohorteFamiliaCasos);
            }
            if (mParticipanteCohorteFamiliaCasos != null){
                publishProgress("Insertando participantes casas con casos en la base de datos...", PART_CASOS, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCasosChfBySql(CasosDBConstants.PARTICIPANTES_CASOS_TABLE, mParticipanteCohorteFamiliaCasos);
            }

            if (mVisitaSeguimientoCasos != null){
                publishProgress("Insertando visitas de los participantes de casas con casos en la base de datos...", VISITAS_CASOS, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCasosChfBySql(CasosDBConstants.VISITAS_CASOS_TABLE, mVisitaSeguimientoCasos);
            }

            if (mVisitaFallidaCasos != null){
                publishProgress("Insertando visitas fallidas de los participantes de casas con casos en la base de datos...", VISITAS_FALLIDAS_CASOS, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCasosChfBySql(CasosDBConstants.VISITAS_FALLIDAS_CASOS_TABLE, mVisitaFallidaCasos);
            }

            if (mVisitaSeguimientoSintomasCasos != null){
                publishProgress("Insertando sintomas de los participantes de casas con casos en la base de datos...", SINTOMAS_CASOS, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCasosChfBySql(CasosDBConstants.SINTOMAS_CASOS_TABLE, mVisitaSeguimientoSintomasCasos);
            }
            if (mSensoresCasos != null){
                publishProgress("Insertando sensores de casas con casos en la base de datos...", SENSORES_CASOS, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertCasosChfBySql(CasosDBConstants.SENSORES_CASOS_TABLE, mSensoresCasos);
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

            //Descargar sensores de casos en seguimiento
            urlRequest = url + "/movil/sensorescasos/";
            publishProgress("Solicitando sensores de casos", SENSORES_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<SensorCaso[]> responseEntitySensores = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    SensorCaso[].class);
            // convert the array to a list and return it
            mSensoresCasos = Arrays.asList(responseEntitySensores.getBody());
            responseEntitySensores = null;

            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
