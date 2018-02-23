package ni.org.ics.estudios.appmovil.muestreoanual.tasks;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.DownloadTask;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class DownloadProcessesTask extends DownloadTask {

	private final Context mContext;

	public DownloadProcessesTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadProcessesTask.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;

    private List<ParticipanteProcesos> mParticipantesProc = null;

    public static final String PARTICIPANTE_PROC = "1";
    private static final String TOTAL_TASK_GENERALES = "1";
	
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
            error = descargarProcesosPar();
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

        try {
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
    protected String descargarProcesosPar() throws Exception {
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
            //Descargar procesos
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

    private void addParticipantesProcesos(List<ParticipanteProcesos> procesos) throws Exception {
        int v = procesos.size();
        ListIterator<ParticipanteProcesos> iter = procesos.listIterator();
        while (iter.hasNext()){
            estudioAdapter.crearParticipanteProcesos(iter.next());
            publishProgress("Insertando Procesos de Participantes", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }
    }

}
