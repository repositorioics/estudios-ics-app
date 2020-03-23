package ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.ObsequioGeneral;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.MuestraSuperficie;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.FormularioContactoCaso;
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



public class DownloadCasosGeneral2Task extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadCasosGeneral2Task(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadCasosGeneral2Task.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;
	
    private List<FormularioContactoCaso> mFormularioContactoCasos = null;
    private List<ObsequioGeneral> mObsequios = null;
    private List<MuestraSuperficie> mMuestrasSup = null;
	
    public static final String CONTACTOS_CASOS = "1";
    public static final String NODATA_CASOS = "2";
    public static final String OBSEQUIOS = "3";
    public static final String MUESTRAS_SUP = "4";

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
		estudioAdapter.open();
		//Borrar los datos de la base de datos
        estudioAdapter.borrarFormularioContactoCaso();
        estudioAdapter.borrarObsequiosGenerales();
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
            
            //Descargar contactos de casas con casos
            urlRequest = url + "/movil/contactoscasos/";
            publishProgress("Solicitando contactos de los participantes de casas de casos",CONTACTOS_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<FormularioContactoCaso[]> responseEntityFormularioContactoCaso = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		FormularioContactoCaso[].class);
            // convert the array to a list and return it
            mFormularioContactoCasos = Arrays.asList(responseEntityFormularioContactoCaso.getBody());
            responseEntityFormularioContactoCaso = null;
            
            //Descargar obsequios
            urlRequest = url + "/movil/obsequiosgen/";
            publishProgress("Solicitando obsequios", OBSEQUIOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<ObsequioGeneral[]> responseEntityObsequios = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ObsequioGeneral[].class);
            // convert the array to a list and return it
            mObsequios = Arrays.asList(responseEntityObsequios.getBody());

            //Descargar muestras de superficie del los casos activos
            urlRequest = url + "/movil/muestrasSuperficie/";
            publishProgress("Solicitando muesstras de superficie casos activos", MUESTRAS_SUP,TOTAL_TASK_CASOS);
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
