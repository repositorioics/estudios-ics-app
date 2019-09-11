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
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaVacunaUO1;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
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

    public static final String PARTICIPANTE_UO1 = "1";
    public static final String VISITAS_UO1 = "2";
    public static final String VISITAS_VAC_UO1 = "3";
    public static final String MUESTRAS_UO1 = "4";

    private static final String TOTAL_TASK_CASOS = "4";

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
            if (mParticipantesUO1 != null){
                v = mParticipantesUO1.size();
                ListIterator<ParticipanteCasoUO1> iter = mParticipantesUO1.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearParticipanteCasoUO1(iter.next());
                    publishProgress("Insertando positivos UO1 en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mParticipantesUO1 = null;
            }
            if (mVisitasUO1 != null){
                v = mVisitasUO1.size();
                ListIterator<VisitaCasoUO1> iter = mVisitasUO1.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVisitaCasoUO1(iter.next());
                    publishProgress("Insertando visitas de casos UO1 en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVisitasUO1 = null;
            }
            if (mVisitasVacunasUO1 != null){
                v = mVisitasVacunasUO1.size();
                ListIterator<VisitaVacunaUO1> iter = mVisitasVacunasUO1.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVisitaVacunaUO1(iter.next());
                    publishProgress("Insertando visitas de vacunas UO1 en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVisitasVacunasUO1 = null;
            }
            if (mMuestras != null){
                v = mMuestras.size();
                ListIterator<Muestra> iter = mMuestras.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearMuestras(iter.next());
                    publishProgress("Insertando muestras de casos UO1 en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mMuestras = null;
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
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
