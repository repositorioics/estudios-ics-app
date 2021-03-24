package ni.org.ics.estudios.appmovil.influenzauo1.task;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.DownloadTask;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.ObsequioGeneral;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.MuestraSuperficie;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.*;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.ParticipanteCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.SintomasVisitaCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaVacunaUO1;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.utils.InfluenzaUO1DBConstants;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class DownloadCasosUO1Task extends DownloadTask {

	private final Context mContext;

	public DownloadCasosUO1Task(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadCasosUO1Task.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;
	
    
    private List<ParticipanteCasoUO1> mParticipantesUO1 = null;
    private List<VisitaCasoUO1> mVisitasUO1 = null;
    private List<VisitaVacunaUO1> mVisitasVacunasUO1 = null;
    private List<Muestra> mMuestras = null;
    private List<SintomasVisitaCasoUO1> mSintomas = null;

    public static final String PARTICIPANTE_UO1 = "1";
    public static final String VISITAS_UO1 = "2";
    public static final String VISITAS_VAC_UO1 = "3";
    public static final String MUESTRAS_UO1 = "4";
    public static final String SINTOMAS_UO1 = "5";

    private static final String TOTAL_TASK_CASOS = "5";

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
            estudioAdapter.borrarParticipantesCasoUO1();
            estudioAdapter.borrarMuestrasUO1();
            estudioAdapter.borrarVisitaCasoUO1();
            estudioAdapter.borrarVisitaVacunaUO1();
            estudioAdapter.borrarSintomasVisitaCasoUO1();
            if (mParticipantesUO1 != null){
                publishProgress("Insertando positivos UO1 en la base de datos...", PARTICIPANTE_UO1, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertU01BySql(InfluenzaUO1DBConstants.UO1_PARTICIPANTES_CASOS_TABLE, mParticipantesUO1);
            }
            if (mVisitasUO1 != null){
                publishProgress("Insertando visitas de casos UO1 en la base de datos...", VISITAS_UO1, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertU01BySql(InfluenzaUO1DBConstants.UO1_VISITAS_CASOS_TABLE, mVisitasUO1);
            }
            if (mVisitasVacunasUO1 != null){
                publishProgress("Insertando visitas de vacunas UO1 en la base de datos...", VISITAS_VAC_UO1, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertU01BySql(InfluenzaUO1DBConstants.UO1_VISITAS_VACUNAS_TABLE, mVisitasVacunasUO1);
            }
            if (mMuestras != null){
                publishProgress("Insertando muestras de casos UO1 en la base de datos...", MUESTRAS_UO1, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertMuestrasChfBySql(mMuestras);
            }
            if (mSintomas != null){
                publishProgress("Insertando sintomas de casos UO1 en la base de datos...", SINTOMAS_UO1, TOTAL_TASK_CASOS);
                estudioAdapter.bulkInsertU01BySql(InfluenzaUO1DBConstants.UO1_SINTOMAS_VISITA_CASO_TABLE,mSintomas);
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
            // Descargando participantes UO1
            urlRequest = url + "/movil/participantesCasoUO1";
            publishProgress("Solicitando positivos UO1",PARTICIPANTE_UO1,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<ParticipanteCasoUO1[]> responseEntityPartProc = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteCasoUO1[].class);

            // convert the array to a list and return it
            mParticipantesUO1 = Arrays.asList(responseEntityPartProc.getBody());

            // Descargando visitas positivos UO1
            urlRequest = url + "/movil/visitasCasoUO1";
            publishProgress("Solicitando visitas positivos UO1",VISITAS_UO1,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<VisitaCasoUO1[]> responseEntityVC = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    VisitaCasoUO1[].class);

            // convert the array to a list and return it
            mVisitasUO1 = Arrays.asList(responseEntityVC.getBody());

            // Descargando visitas vacunas UO1
            urlRequest = url + "/movil/visitasVacunasUO1";
            publishProgress("Solicitando visitas vacunas UO1",VISITAS_VAC_UO1,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<VisitaVacunaUO1[]> responseEntityVV = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    VisitaVacunaUO1[].class);

            // convert the array to a list and return it
            mVisitasVacunasUO1 = Arrays.asList(responseEntityVV.getBody());

            // Descargando MUESTRAS UO1
                    urlRequest = url + "/movil/muestrasCasosUO1";
            publishProgress("Solicitando muestras UO1",MUESTRAS_UO1,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<Muestra[]> responseEntityMx = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Muestra[].class);

            // convert the array to a list and return it
            mMuestras = Arrays.asList(responseEntityMx.getBody());

            // Descargando SINTOMAS UO1
            urlRequest = url + "/movil/sintomasUO1";
            publishProgress("Solicitando síntomas UO1",SINTOMAS_UO1,TOTAL_TASK_CASOS);

            // Perform the HTTP GET request
            ResponseEntity<SintomasVisitaCasoUO1[]> responseEntitySin = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    SintomasVisitaCasoUO1[].class);

            // convert the array to a list and return it
            mSintomas = Arrays.asList(responseEntitySin.getBody());
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
