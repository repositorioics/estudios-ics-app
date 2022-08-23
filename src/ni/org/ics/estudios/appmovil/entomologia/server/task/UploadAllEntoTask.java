package ni.org.ics.estudios.appmovil.entomologia.server.task;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.UploadAllTask;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.UploadTask;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogar;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogarPoblacion;
import ni.org.ics.estudios.appmovil.listeners.UploadListener;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class UploadAllEntoTask extends UploadTask {
    private final Context mContext;

    public UploadAllEntoTask(Context context) {
        mContext = context;
    }

    protected static final String TAG = UploadAllTask.class.getSimpleName();
    private EstudiosAdapter estudioAdapter = null;
    private String url = null;
    private String username = null;
    private String password = null;
    private String error = null;
    protected UploadListener mStateListener;
    private List<CuestionarioHogar> mCuestHogar = new ArrayList<CuestionarioHogar>();
    private List<CuestionarioHogarPoblacion> mCuestHogarPob = new ArrayList<CuestionarioHogarPoblacion>();

    public static final String ENTO_CUESTIONARIO_HOGAR = "1";
    public static final String ENTO_CUESTIONARIO_HOGAR_POB = "2";

    private static final String TOTAL_TASK_CASOS = "2";

    @Override
    protected String doInBackground(String... values) {
        url = values[0];
        username = values[1];
        password = values[2];
        try{
            publishProgress("Obteniendo registros de la base de datos", "1", "2");
            estudioAdapter = new EstudiosAdapter(mContext, password, false,false);
            estudioAdapter.open();
            String filtro = MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'";
            mCuestHogar = estudioAdapter.getCuestionariosHogar(filtro, null);
            mCuestHogarPob = estudioAdapter.getCuestionariosHogarPoblacion(filtro, null);

            publishProgress("Datos completos!", "2", "2");

            if (noHayDatosEnviar()) {
                error = Constants.NO_DATA;
            } else {

                actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENTO_CUESTIONARIO_HOGAR);
                error = cargarCuestionarios(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENTO_CUESTIONARIO_HOGAR);
                    return error;
                }

                actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENTO_CUESTIONARIO_HOGAR_POB);
                error = cargarPoblacion(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENTO_CUESTIONARIO_HOGAR_POB);
                    return error;
                }

            }
        } catch (Exception e1) {

            e1.printStackTrace();
            return e1.getLocalizedMessage();
        }finally {
            estudioAdapter.close();
        }
        return error;
    }

    private boolean noHayDatosEnviar() {
        return mCuestHogar.size() <= 0 &&
                mCuestHogarPob.size() <= 0;
    }

    private void actualizarBaseDatos(String estado, String opcion) throws Exception {
        int c;
        if(opcion.equalsIgnoreCase(ENTO_CUESTIONARIO_HOGAR)){
            c = mCuestHogar.size();
            if(c>0){
                for (CuestionarioHogar cuestionarioHogar : mCuestHogar) {
                    cuestionarioHogar.setEstado(estado.charAt(0));
                    estudioAdapter.editarCuestionarioHogar(cuestionarioHogar);
                    publishProgress("Actualizando cuestionarios base de datos local", Integer.valueOf(mCuestHogar.indexOf(cuestionarioHogar)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENTO_CUESTIONARIO_HOGAR_POB)){
            c = mCuestHogarPob.size();
            if(c>0){
                for (CuestionarioHogarPoblacion hogarPoblacion : mCuestHogarPob) {
                    hogarPoblacion.setEstado(estado.charAt(0));
                    estudioAdapter.editarCuestionarioHogarPoblacion(hogarPoblacion);
                    publishProgress("Actualizando población en base de datos local", Integer.valueOf(mCuestHogarPob.indexOf(hogarPoblacion)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
    }

    /***************************************************/
    /********************* Visitas Positivos ************************/
    /***************************************************/
    // url, username, password
    protected String cargarCuestionarios(String url, String username,
                                         String password) throws Exception {
        try {
            if(mCuestHogar.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando cuestionarios!", ENTO_CUESTIONARIO_HOGAR, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/cuestionariosHogar";
                CuestionarioHogar[] envio = mCuestHogar.toArray(new CuestionarioHogar[mCuestHogar.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CuestionarioHogar[]> requestEntity =
                        new HttpEntity<CuestionarioHogar[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone los participantes y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getMessage();
        }
    }


    /***************************************************/
    /********************* Visitas Vacunas ************************/
    /***************************************************/
    // url, username, password
    protected String cargarPoblacion(String url, String username,
                                     String password) throws Exception {
        try {
            if(mCuestHogarPob.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando poblacion!", ENTO_CUESTIONARIO_HOGAR_POB, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/cuestionariosHogarPob";
                CuestionarioHogarPoblacion[] envio = mCuestHogarPob.toArray(new CuestionarioHogarPoblacion[mCuestHogarPob.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CuestionarioHogarPoblacion[]> requestEntity =
                        new HttpEntity<CuestionarioHogarPoblacion[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone los participantes y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getMessage();
        }
    }

}
