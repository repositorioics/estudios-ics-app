package ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.*;

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



public class DownloadBaseTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadBaseTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadBaseTask.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;
	private List<Estudio> mEstudios = null;

	public static final String ESTUDIO = "1";
	public static final String BARRIO = "2";
	public static final String CASA = "3";
	public static final String PARTICIPANTE = "4";
	private static final String TOTAL_TASK_GENERALES = "4";
	
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
			error = descargarDatosGenerales();
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
		try {
			if (mEstudios != null){
				publishProgress("Insertando estudios en la base de datos","1","1");
				estudioAdapter.bulkInsertEstutiosBySql(mEstudios);
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
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
