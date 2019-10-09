package ni.org.ics.estudios.appmovil.influenzauo1.task;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.UploadAllTask;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.UploadTask;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.SintomasVisitaCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaVacunaUO1;
import ni.org.ics.estudios.appmovil.listeners.UploadListener;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class UploadAllCasosUO1Task extends UploadTask {
    private final Context mContext;

    public UploadAllCasosUO1Task(Context context) {
        mContext = context;
    }

    protected static final String TAG = UploadAllTask.class.getSimpleName();
    private EstudiosAdapter estudioAdapter = null;
    private String url = null;
    private String username = null;
    private String password = null;
    private String error = null;
    protected UploadListener mStateListener;
    private List<VisitaCasoUO1> mVisitasUO1 = new ArrayList<VisitaCasoUO1>();
    private List<VisitaVacunaUO1> mVisitasVacunasUO1 = new ArrayList<VisitaVacunaUO1>();
    private List<Muestra> mMuestras = new ArrayList<Muestra>();
    private List<SintomasVisitaCasoUO1> mSintomas = new ArrayList<SintomasVisitaCasoUO1>();

    public static final String VISITAS_UO1 = "1";
    public static final String VISITAS_VAC_UO1 = "2";
    public static final String MUESTRAS_UO1 = "3";
    public static final String SINTOMAS_UO1 = "4";

    private static final String TOTAL_TASK_CASOS = "4";

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
            mMuestras = estudioAdapter.getMuestras(filtro, null);
            mVisitasUO1 = estudioAdapter.getVisitasCasosUO1(filtro, null);
            mVisitasVacunasUO1 = estudioAdapter.getVisitasVacunasUO1(filtro, null);
            mSintomas = estudioAdapter.getSintomasVisitasCasosUO1(filtro, null);

            publishProgress("Datos completos!", "2", "2");

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, VISITAS_UO1);
            error = cargarVisitas(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, VISITAS_UO1);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, VISITAS_VAC_UO1);
            error = cargarVisitasVacunas(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, VISITAS_VAC_UO1);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, MUESTRAS_UO1);
            error = cargarMuestras(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, MUESTRAS_UO1);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, SINTOMAS_UO1);
            error = cargarSintomas(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, SINTOMAS_UO1);
                return error;
            }
        } catch (Exception e1) {

            e1.printStackTrace();
            return e1.getLocalizedMessage();
        }finally {
            estudioAdapter.close();
        }
        return error;
    }

    private void actualizarBaseDatos(String estado, String opcion) {
        int c;
        if(opcion.equalsIgnoreCase(VISITAS_UO1)){
            c = mVisitasUO1.size();
            if(c>0){
                for (VisitaCasoUO1 visitaCasoUO1 : mVisitasUO1) {
                    visitaCasoUO1.setEstado(estado.charAt(0));
                    estudioAdapter.editarVisitaCasoUO1(visitaCasoUO1);
                    publishProgress("Actualizando visitas positivos UO1 en base de datos local", Integer.valueOf(mVisitasUO1.indexOf(visitaCasoUO1)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(VISITAS_VAC_UO1)){
            c = mVisitasVacunasUO1.size();
            if(c>0){
                for (VisitaVacunaUO1 visitaVacunaUO1 : mVisitasVacunasUO1) {
                    visitaVacunaUO1.setEstado(estado.charAt(0));
                    estudioAdapter.editarVisitaVacunaUO1(visitaVacunaUO1);
                    publishProgress("Actualizando visitas vacunas UO1 en base de datos local", Integer.valueOf(mVisitasVacunasUO1.indexOf(visitaVacunaUO1)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(MUESTRAS_UO1)){
            c = mMuestras.size();
            if(c>0){
                for (Muestra muestra : mMuestras) {
                    muestra.setEstado(estado.charAt(0));
                    estudioAdapter.editarMuestras(muestra);
                    publishProgress("Actualizando muestras UO1 en base de datos local", Integer.valueOf(mMuestras.indexOf(muestra)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(SINTOMAS_UO1)){
            c = mSintomas.size();
            if(c>0){
                for (SintomasVisitaCasoUO1 sintoma : mSintomas) {
                    sintoma.setEstado(estado.charAt(0));
                    estudioAdapter.editarSintomasVisitaCasoUO1(sintoma);
                    publishProgress("Actualizando sintomas UO1 en base de datos local", Integer.valueOf(mSintomas.indexOf(sintoma)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
    }

    /***************************************************/
    /********************* Visitas Positivos ************************/
    /***************************************************/
    // url, username, password
    protected String cargarVisitas(String url, String username,
                                    String password) throws Exception {
        try {
            if(mVisitasUO1.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando visitas casos UO1!", VISITAS_UO1, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/visitasCasoUO1";
                VisitaCasoUO1[] envio = mVisitasUO1.toArray(new VisitaCasoUO1[mVisitasUO1.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<VisitaCasoUO1[]> requestEntity =
                        new HttpEntity<VisitaCasoUO1[]>(envio, requestHeaders);
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
    /********************* Muestras ************************/
    /***************************************************/
    // url, username, password
    protected String cargarMuestras(String url, String username,
                                    String password) throws Exception {
        try {
            if(mMuestras.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando muestras UO1!", MUESTRAS_UO1, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/muestras";
                Muestra[] envio = mMuestras.toArray(new Muestra[mMuestras.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Muestra[]> requestEntity =
                        new HttpEntity<Muestra[]>(envio, requestHeaders);
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
    protected String cargarVisitasVacunas(String url, String username,
                                   String password) throws Exception {
        try {
            if(mVisitasVacunasUO1.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando visitas vacunas UO1!", VISITAS_VAC_UO1, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/visitasVacunasUO1";
                VisitaVacunaUO1[] envio = mVisitasVacunasUO1.toArray(new VisitaVacunaUO1[mVisitasVacunasUO1.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<VisitaVacunaUO1[]> requestEntity =
                        new HttpEntity<VisitaVacunaUO1[]>(envio, requestHeaders);
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
    /********************* Sintomas visitas casos ************************/
    /***************************************************/
    // url, username, password
    protected String cargarSintomas(String url, String username,
                                    String password) throws Exception {
        try {
            if(mSintomas.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando sintomas UO1!", SINTOMAS_UO1, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/sintomasUO1";
                SintomasVisitaCasoUO1[] envio = mSintomas.toArray(new SintomasVisitaCasoUO1[mSintomas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<SintomasVisitaCasoUO1[]> requestEntity =
                        new HttpEntity<SintomasVisitaCasoUO1[]>(envio, requestHeaders);
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
