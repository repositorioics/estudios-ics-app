package ni.org.ics.estudios.appmovil.covid19.task;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.UploadAllTask;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.UploadTask;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.covid19.*;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.listeners.UploadListener;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.MuestrasDBConstants;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class UploadAllCasosCovid19Task extends UploadTask {
    private final Context mContext;

    public UploadAllCasosCovid19Task(Context context) {
        mContext = context;
    }

    protected static final String TAG = UploadAllTask.class.getSimpleName();
    private EstudiosAdapter estudioAdapter = null;
    private String url = null;
    private String username = null;
    private String password = null;
    private String error = null;
    protected UploadListener mStateListener;

    private List<Tamizaje> mTamizajes = new ArrayList<Tamizaje>();
    private List<CartaConsentimiento> mCartasConsent = new ArrayList<CartaConsentimiento>();
    private List<Participante> mParticipantes = new ArrayList<Participante>();
    private List<ContactoParticipante> mContactos = new ArrayList<ContactoParticipante>();
    private List<VisitaTerrenoParticipante> mVisitasTerrenoP = new ArrayList<VisitaTerrenoParticipante>();

    private List<ParticipanteCovid19> mParticipantesCovid = new ArrayList<ParticipanteCovid19>();
    private List<CasoCovid19> mCasos = new ArrayList<CasoCovid19>();
    private List<ParticipanteCasoCovid19> mParticipantesCasos = new ArrayList<ParticipanteCasoCovid19>();
    private List<VisitaSeguimientoCasoCovid19> mVisitas = new ArrayList<VisitaSeguimientoCasoCovid19>();
    private List<VisitaFallidaCasoCovid19> mVisitasFall = new ArrayList<VisitaFallidaCasoCovid19>();
    private List<Muestra> mMuestras = new ArrayList<Muestra>();
    private List<CandidatoTransmisionCovid19> mCandidatos = new ArrayList<CandidatoTransmisionCovid19>();
    private List<SintomasVisitaCasoCovid19> mSintomas = new ArrayList<SintomasVisitaCasoCovid19>();
    private List<DatosAislamientoVisitaCasoCovid19> mAislamientos = new ArrayList<DatosAislamientoVisitaCasoCovid19>();
    private List<ParticipanteProcesos> mParticipantesProc = new ArrayList<ParticipanteProcesos>();
    private List<VisitaFinalCasoCovid19> mVisitasFinales = new ArrayList<VisitaFinalCasoCovid19>();
    private List<SintomasVisitaFinalCovid19> mSintomasFinal = new ArrayList<SintomasVisitaFinalCovid19>();
    private List<ObsequioGeneral> mObsequiosGeneral = new ArrayList<ObsequioGeneral>();

    public static final String TAMIZAJE = "1";
    public static final String PARTICIPANTE = "2";
    public static final String CARTAS_CONSENT = "3";
    public static final String PARTICIPANTE_PRC = "4";
    public static final String CONTACTO_PARTICIPANTE = "5";
    public static final String VISITA_TERRENO_PARTICIPANTE = "6";
    public static final String PARTICIPANTES_COVID19 = "7";
    public static final String CASOS_COVID19 = "8";
    public static final String PARTICIPANTES_CASOS_COVID19 = "9";
    public static final String VISITAS_CASOS_COVID19 = "10";
    public static final String MUESTRAS = "11";
    public static final String SINT_VISITAS_CASOS_COVID19 = "12";
    public static final String VISITAS_FALL_CASOS_COVID19 = "13";
    public static final String CANDIDATOS_COVID19 = "14";
    public static final String DATOS_AISLAMIENTO_COVID19 = "15";
    public static final String VISITAS_FINALES_COVID19 = "16";
    public static final String SINT_VISITAS_FINALES_COVID19 = "17";
    public static final String OBSEQUIOS = "18";

    private static final String TOTAL_TASK_CASOS = "18";

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
            mParticipantesCovid = estudioAdapter.getParticipantesCovid19(filtro, null);
            mCasos = estudioAdapter.getCasosCovid19(filtro, null);
            mParticipantesCasos = estudioAdapter.getParticipantesCasosCovid19(filtro, null);
            mVisitas = estudioAdapter.getVisitasSeguimientosCasosCovid19(filtro, null);
            mVisitasFall = estudioAdapter.getVisitasFallidasCasoCovid19(filtro, null);
            mMuestras = estudioAdapter.getMuestras(filtro + " and " + MuestrasDBConstants.proposito + " in ('"+Constants.CODIGO_PROPOSITO_COVID_CP+"','"+Constants.CODIGO_PROPOSITO_T_COVID+"')" , null);
            mCandidatos = estudioAdapter.getCandidatosTransmisionCovid19(filtro, null);
            mSintomas = estudioAdapter.getSintomasVisitasCasosCovid19(filtro, null);
            mAislamientos = estudioAdapter.getDatosAislamientoVisitasCasosCovid19(filtro, null);
            mParticipantesProc = estudioAdapter.getParticipantesProc(filtro, null);
            mTamizajes = estudioAdapter.getTamizajes(filtro, null);
            mCartasConsent = estudioAdapter.getCartasConsentimientos(filtro, null);
            mParticipantes = estudioAdapter.getParticipantes(filtro, null);
            mContactos = estudioAdapter.getContactosParticipantes(filtro, null);
            mVisitasTerrenoP = estudioAdapter.getVisitasTerrenoParticipantes(filtro, null);
            mVisitasFinales = estudioAdapter.getVisitasFinalesCasosCovid19(filtro, null);
            mSintomasFinal = estudioAdapter.getSintomasVisitasFinalesCovid19(filtro, null);
            mObsequiosGeneral = estudioAdapter.getObsequiosGenerales(filtro, null);

            publishProgress("Datos completos!", "2", "2");

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, TAMIZAJE);
            error = cargarTamizajes(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, TAMIZAJE);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, PARTICIPANTE);
            error = cargarParticipantes(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PARTICIPANTE);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, CARTAS_CONSENT);
            error = cargarCartasConsentimientos(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CARTAS_CONSENT);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, PARTICIPANTE_PRC);
            error = cargarParticipantesProc(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PARTICIPANTE_PRC);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, CONTACTO_PARTICIPANTE);
            error = cargarContactos(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CONTACTO_PARTICIPANTE);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, VISITA_TERRENO_PARTICIPANTE);
            error = cargarVisitaTerrenoParticipante(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, VISITA_TERRENO_PARTICIPANTE);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, PARTICIPANTES_COVID19);
            error = cargarParticipanteCovid19(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PARTICIPANTES_COVID19);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, CASOS_COVID19);
            error = cargarCasoCovid19(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CASOS_COVID19);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, PARTICIPANTES_CASOS_COVID19);
            error = cargarParticipantesCasosCovid19(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PARTICIPANTES_CASOS_COVID19);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, VISITAS_CASOS_COVID19);
            error = cargarVisitasCasosCovid19(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, VISITAS_CASOS_COVID19);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, MUESTRAS);
            error = cargarMuestras(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, MUESTRAS);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, SINT_VISITAS_CASOS_COVID19);
            error = cargarSintomas(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, SINT_VISITAS_CASOS_COVID19);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, VISITAS_FALL_CASOS_COVID19);
            error = cargarVisitasFallidasCasosCovid19(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, VISITAS_FALL_CASOS_COVID19);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, CANDIDATOS_COVID19);
            error = cargarCandidatosTransmisionCovid19(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CANDIDATOS_COVID19);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, DATOS_AISLAMIENTO_COVID19);
            error = cargarDatosAislamientoVisitaCasoCovid19(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, DATOS_AISLAMIENTO_COVID19);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, VISITAS_FINALES_COVID19);
            error = cargarVisitasFinalesCasoCovid19(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, VISITAS_FINALES_COVID19);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, SINT_VISITAS_FINALES_COVID19);
            error = cargarSintomasVisitasFinalesCovid19(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, SINT_VISITAS_FINALES_COVID19);
                return error;
            }

            actualizarBaseDatos(Constants.STATUS_SUBMITTED, OBSEQUIOS);
            error = cargarObsequioGeneral(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, OBSEQUIOS);
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

    private void actualizarBaseDatos(String estado, String opcion) throws Exception{
        int c;
        if(opcion.equalsIgnoreCase(TAMIZAJE)){
            c = mTamizajes.size();
            if(c>0){
                for (Tamizaje tamizaje : mTamizajes) {
                    tamizaje.setEstado(estado.charAt(0));
                    estudioAdapter.editarTamizaje(tamizaje);
                    publishProgress("Actualizando tamizajes en base de datos local", Integer.valueOf(mTamizajes.indexOf(tamizaje)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(CARTAS_CONSENT)){
            c = mCartasConsent.size();
            if(c>0){
                for (CartaConsentimiento consentimiento : mCartasConsent) {
                    consentimiento.setEstado(estado.charAt(0));
                    estudioAdapter.editarCartaConsentimiento(consentimiento);
                    publishProgress("Actualizando cartas de consentimiento en base de datos local", Integer.valueOf(mCartasConsent.indexOf(consentimiento)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(PARTICIPANTE)){
            c = mParticipantes.size();
            if(c>0){
                for (Participante participante : mParticipantes) {
                    participante.setEstado(estado.charAt(0));
                    estudioAdapter.editarParticipante(participante);
                    publishProgress("Actualizando participantes en base de datos local", Integer.valueOf(mParticipantes.indexOf(participante)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(CONTACTO_PARTICIPANTE)){
            c = mContactos.size();
            if(c>0){
                for (ContactoParticipante contacto : mContactos) {
                    contacto.setEstado(estado.charAt(0));
                    estudioAdapter.editarContactoParticipante(contacto);
                    publishProgress("Actualizando contactos en base de datos local", Integer.valueOf(mContactos.indexOf(contacto)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(VISITA_TERRENO_PARTICIPANTE)){
            c = mVisitasTerrenoP.size();
            if(c>0) {
                for (VisitaTerrenoParticipante visita : mVisitasTerrenoP) {
                    visita.setEstado(estado.charAt(0));
                    estudioAdapter.editarVisitaTerrenoParticipante(visita);
                    publishProgress("Actualizando visitas a participante en base de datos local", Integer.valueOf(mVisitasTerrenoP.indexOf(visita)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(PARTICIPANTES_COVID19)){
            c = mParticipantesCovid.size();
            if(c>0){
                for (ParticipanteCovid19 participanteCovid19 : mParticipantesCovid) {
                    participanteCovid19.setEstado(estado.charAt(0));
                    estudioAdapter.editarParticipanteCovid19(participanteCovid19);
                    publishProgress("Actualizando participantes COVID19 en base de datos local", Integer.valueOf(mParticipantesCovid.indexOf(participanteCovid19)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(CASOS_COVID19)){
            c = mCasos.size();
            if(c>0){
                for (CasoCovid19 casoCovid19 : mCasos) {
                    casoCovid19.setEstado(estado.charAt(0));
                    estudioAdapter.editarCasoCovid19(casoCovid19);
                    publishProgress("Actualizando casos seguimiento COVID19 en base de datos local", Integer.valueOf(mCasos.indexOf(casoCovid19)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(PARTICIPANTES_CASOS_COVID19)){
            c = mParticipantesCasos.size();
            if(c>0){
                for (ParticipanteCasoCovid19 participanteCasoCovid19 : mParticipantesCasos) {
                    participanteCasoCovid19.setEstado(estado.charAt(0));
                    estudioAdapter.editarParticipanteCasoCovid19(participanteCasoCovid19);
                    publishProgress("Actualizando participantes seguimiento COVID19 en base de datos local", Integer.valueOf(mParticipantesCasos.indexOf(participanteCasoCovid19)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(VISITAS_CASOS_COVID19)){
            c = mVisitas.size();
            if(c>0){
                for (VisitaSeguimientoCasoCovid19 visitaSeguimientoCasoCovid19 : mVisitas) {
                    visitaSeguimientoCasoCovid19.setEstado(estado.charAt(0));
                    estudioAdapter.editarVisitaSeguimientoCasoCovid19(visitaSeguimientoCasoCovid19);
                    publishProgress("Actualizando visitas de casos COVID19 en base de datos local", Integer.valueOf(mVisitas.indexOf(visitaSeguimientoCasoCovid19)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(MUESTRAS)){
            c = mMuestras.size();
            if(c>0){
                for (Muestra muestra : mMuestras) {
                    muestra.setEstado(estado.charAt(0));
                    estudioAdapter.editarMuestras(muestra);
                    publishProgress("Actualizando muestras COVID19 en base de datos local", Integer.valueOf(mMuestras.indexOf(muestra)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(SINT_VISITAS_CASOS_COVID19)){
            c = mSintomas.size();
            if(c>0){
                for (SintomasVisitaCasoCovid19 sintomasVisitaCasoCovid19 : mSintomas) {
                    sintomasVisitaCasoCovid19.setEstado(estado.charAt(0));
                    estudioAdapter.editarSintomasVisitaCasoCovid19(sintomasVisitaCasoCovid19);
                    publishProgress("Actualizando síntomas de casos COVID19 en base de datos local", Integer.valueOf(mSintomas.indexOf(sintomasVisitaCasoCovid19)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(VISITAS_FALL_CASOS_COVID19)){
            c = mVisitasFall.size();
            if(c>0){
                for (VisitaFallidaCasoCovid19 visitaFallidaCasoCovid19 : mVisitasFall) {
                    visitaFallidaCasoCovid19.setEstado(estado.charAt(0));
                    estudioAdapter.editarVisitaFallidaCasoCovid19(visitaFallidaCasoCovid19);
                    publishProgress("Actualizando visitas fallidas de casos COVID19 en base de datos local", Integer.valueOf(mVisitasFall.indexOf(visitaFallidaCasoCovid19)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(CANDIDATOS_COVID19)){
            c = mCandidatos.size();
            if(c>0){
                for (CandidatoTransmisionCovid19 candidatoTransmisionCovid19 : mCandidatos) {
                    candidatoTransmisionCovid19.setEstado(estado.charAt(0));
                    estudioAdapter.editarCandidatoTransmisionCovid19(candidatoTransmisionCovid19);
                    publishProgress("Actualizando candidatos transmisión COVID19 en base de datos local", Integer.valueOf(mCandidatos.indexOf(candidatoTransmisionCovid19)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(DATOS_AISLAMIENTO_COVID19)){
            c = mAislamientos.size();
            if(c>0){
                for (DatosAislamientoVisitaCasoCovid19 datos : mAislamientos) {
                    datos.setEstado(estado.charAt(0));
                    estudioAdapter.editarDatosAislamientoVisitaCasoCovid19(datos);
                    publishProgress("Actualizando datos aislamiento COVID19 en base de datos local", Integer.valueOf(mAislamientos.indexOf(datos)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(PARTICIPANTE_PRC)){
            c = mParticipantesProc.size();
            if(c>0){
                for (ParticipanteProcesos participanteprc : mParticipantesProc) {
                    participanteprc.getMovilInfo().setEstado(estado);
                    estudioAdapter.actualizarParticipanteProcesos(participanteprc);
                    publishProgress("Actualizando procesos participantes en base de datos local", Integer.valueOf(mParticipantesProc.indexOf(participanteprc)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(VISITAS_FINALES_COVID19)){
            c = mVisitasFinales.size();
            if(c>0){
                for (VisitaFinalCasoCovid19 visitaFinalCasoCovid19 : mVisitasFinales) {
                    visitaFinalCasoCovid19.setEstado(estado.charAt(0));
                    estudioAdapter.editarVisitaFinalCasoCovid19(visitaFinalCasoCovid19);
                    publishProgress("Actualizando visitas finales COVID19 en base de datos local", Integer.valueOf(mVisitasFinales.indexOf(visitaFinalCasoCovid19)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(SINT_VISITAS_FINALES_COVID19)){
            c = mSintomasFinal.size();
            if(c>0){
                for (SintomasVisitaFinalCovid19 sintoma : mSintomasFinal) {
                    sintoma.setEstado(estado.charAt(0));
                    estudioAdapter.editarSintomasVisitaFinalCovid19(sintoma);
                    publishProgress("Actualizando síntomas visitas finales COVID19 en base de datos local", Integer.valueOf(mSintomasFinal.indexOf(sintoma)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(OBSEQUIOS)){
            c = mObsequiosGeneral.size();
            if(c>0){
                for (ObsequioGeneral obsequio : mObsequiosGeneral) {
                    obsequio.setEstado(estado.charAt(0));
                    try {
                        estudioAdapter.editarObsequioGeneral(obsequio);
                        publishProgress("Actualizando obsequios en base de datos local", Integer.valueOf(mObsequiosGeneral.indexOf(obsequio)).toString(), Integer
                                .valueOf(c).toString());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    /********************* Participantes Covid19 ************************/
    /***************************************************/
    // url, username, password
    protected String cargarParticipanteCovid19(String url, String username, String password) throws Exception {
        try {
            if(mParticipantesCovid.size()>0){
                // La URL de la solicitud POST.
                publishProgress("Enviando participantes COVID19!", PARTICIPANTES_COVID19, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/participantesCovid19";
                ParticipanteCovid19[] envio = mParticipantesCovid.toArray(new ParticipanteCovid19[mParticipantesCovid.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ParticipanteCovid19[]> requestEntity =
                        new HttpEntity<ParticipanteCovid19[]>(envio, requestHeaders);
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

    /********************* Casos Covid19 ************************/
    /***************************************************/
    // url, username, password
    protected String cargarCasoCovid19(String url, String username, String password) throws Exception {
        try {
            if(mCasos.size()>0){
                // La URL de la solicitud POST.
                publishProgress("Enviando casos COVID19!", CASOS_COVID19, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/casosCovid19";
                CasoCovid19[] envio = mCasos.toArray(new CasoCovid19[mCasos.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CasoCovid19[]> requestEntity =
                        new HttpEntity<CasoCovid19[]>(envio, requestHeaders);
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
    /********************* Participantes Casos Covid19 ************************/
    /***************************************************/
    // url, username, password
    protected String cargarParticipantesCasosCovid19(String url, String username, String password) throws Exception {
        try {
            if(mParticipantesCasos.size()>0){
                // La URL de la solicitud POST.
                publishProgress("Enviando participantes seguimiento casos COVID19!", PARTICIPANTES_CASOS_COVID19, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/participantesCasoCovid19";
                ParticipanteCasoCovid19[] envio = mParticipantesCasos.toArray(new ParticipanteCasoCovid19[mParticipantesCasos.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ParticipanteCasoCovid19[]> requestEntity =
                        new HttpEntity<ParticipanteCasoCovid19[]>(envio, requestHeaders);
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
    /********************* Visitas Seguimientos Casos Covid19************************/
    /***************************************************/
    // url, username, password
    protected String cargarVisitasCasosCovid19(String url, String username, String password) throws Exception {
        try {
            if(mVisitas.size()>0){
                // La URL de la solicitud POST.
                publishProgress("Enviando visitas seguimiento casos COVID19!", VISITAS_CASOS_COVID19, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/visitasSeguimientosCasoCovid19";
                VisitaSeguimientoCasoCovid19[] envio = mVisitas.toArray(new VisitaSeguimientoCasoCovid19[mVisitas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<VisitaSeguimientoCasoCovid19[]> requestEntity =
                        new HttpEntity<VisitaSeguimientoCasoCovid19[]>(envio, requestHeaders);
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
    protected String cargarMuestras(String url, String username, String password) throws Exception {
        try {
            if(mMuestras.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando muestras COVID19!", MUESTRAS, TOTAL_TASK_CASOS);
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
    /********************* Visitas Fallidas Casos Covid19************************/
    /***************************************************/
    // url, username, password
    protected String cargarVisitasFallidasCasosCovid19(String url, String username, String password) throws Exception {
        try {
            if(mVisitasFall.size()>0){
                // La URL de la solicitud POST.
                publishProgress("Enviando visitas fallidas casos COVID19!", VISITAS_FALL_CASOS_COVID19, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/visitasFallidasCasoCovid19";
                VisitaFallidaCasoCovid19[] envio = mVisitasFall.toArray(new VisitaFallidaCasoCovid19[mVisitasFall.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<VisitaFallidaCasoCovid19[]> requestEntity =
                        new HttpEntity<VisitaFallidaCasoCovid19[]>(envio, requestHeaders);
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
    /********************* Candidatos Transmisión Covid19************************/
    /***************************************************/
    // url, username, password
    protected String cargarCandidatosTransmisionCovid19(String url, String username, String password) throws Exception {
        try {
            if(mCandidatos.size()>0){
                // La URL de la solicitud POST.
                publishProgress("Enviando candidatos Transmisión casos COVID19!", CANDIDATOS_COVID19, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/candidatosTCovid19";
                CandidatoTransmisionCovid19[] envio = mCandidatos.toArray(new CandidatoTransmisionCovid19[mCandidatos.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CandidatoTransmisionCovid19[]> requestEntity =
                        new HttpEntity<CandidatoTransmisionCovid19[]>(envio, requestHeaders);
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
    /********************* SintomasVisitaCasoCovid19 ************************/
    /***************************************************/
    // url, username, password
    protected String cargarSintomas(String url, String username, String password) throws Exception {
        try {
            if(mSintomas.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando síntomas casos COVID19!", SINT_VISITAS_CASOS_COVID19, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/sintomasCasosCovid19";
                SintomasVisitaCasoCovid19[] envio = mSintomas.toArray(new SintomasVisitaCasoCovid19[mSintomas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<SintomasVisitaCasoCovid19[]> requestEntity =
                        new HttpEntity<SintomasVisitaCasoCovid19[]>(envio, requestHeaders);
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
    /********************* Candidatos Transmisión Covid19************************/
    /***************************************************/
    // url, username, password
    protected String cargarDatosAislamientoVisitaCasoCovid19(String url, String username, String password) throws Exception {
        try {
            if(mAislamientos.size()>0){
                // La URL de la solicitud POST.
                publishProgress("Enviando datos de aislamiento casos COVID19!", DATOS_AISLAMIENTO_COVID19, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/datosAislamientoCovid19";
                DatosAislamientoVisitaCasoCovid19[] envio = mAislamientos.toArray(new DatosAislamientoVisitaCasoCovid19[mAislamientos.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<DatosAislamientoVisitaCasoCovid19[]> requestEntity =
                        new HttpEntity<DatosAislamientoVisitaCasoCovid19[]>(envio, requestHeaders);
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
    /*********** Participantes procesos ****************/
    /***************************************************/
    // url, username, password
    protected String cargarParticipantesProc(String url, String username,
                                             String password) throws Exception {
        try {
            if(mParticipantesProc.size()>0){
                publishProgress("Enviando participantes procesos!", PARTICIPANTE_PRC, TOTAL_TASK_CASOS);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/participantesprocesos";
                ParticipanteProcesos[] envio = mParticipantesProc.toArray(new ParticipanteProcesos[mParticipantesProc.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ParticipanteProcesos[]> requestEntity =
                        new HttpEntity<ParticipanteProcesos[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
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
    /********************* Tamizajes participantes ************************/
    /***************************************************/
    // url, username, password
    protected String cargarTamizajes(String url, String username,
                                     String password) throws Exception {
        try {
            if(mTamizajes.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando tamizaje de personas cohorte familia!", TAMIZAJE, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/tamizajes";
                Tamizaje[] envio = mTamizajes.toArray(new Tamizaje[mTamizajes.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Tamizaje[]> requestEntity =
                        new HttpEntity<Tamizaje[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
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
    /********************* Participantes ************************/
    /***************************************************/
    // url, username, password
    protected String cargarParticipantes(String url, String username,
                                         String password) throws Exception {
        try {
            if(mParticipantes.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando participantes!", PARTICIPANTE, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/participantes";
                Participante[] envio = mParticipantes.toArray(new Participante[mParticipantes.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Participante[]> requestEntity =
                        new HttpEntity<Participante[]>(envio, requestHeaders);
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
    /********************* Cartas de consentimiento ************************/
    /***************************************************/
    // url, username, password
    protected String cargarCartasConsentimientos(String url, String username,
                                                 String password) throws Exception {
        try {
            if(mCartasConsent.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando cartas de consentimiento!", CARTAS_CONSENT, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/cartasConsen";
                CartaConsentimiento[] envio = mCartasConsent.toArray(new CartaConsentimiento[mCartasConsent.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CartaConsentimiento[]> requestEntity =
                        new HttpEntity<CartaConsentimiento[]>(envio, requestHeaders);
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
    /*************** ContactosParticipantes ************/
    /***************************************************/
    // url, username, password
    protected String cargarContactos(String url, String username,
                                     String password) throws Exception {
        try {
            if(mContactos.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando contactos de participantes!", CONTACTO_PARTICIPANTE, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/contactosparticipantes";
                ContactoParticipante[] envio = mContactos.toArray(new ContactoParticipante[mContactos.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ContactoParticipante[]> requestEntity =
                        new HttpEntity<ContactoParticipante[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
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
    /*************** VisitaTerrenoParticipante ************/
    /***************************************************/
    // url, username, password
    protected String cargarVisitaTerrenoParticipante(String url, String username,
                                                     String password) throws Exception {
        try {
            if(mVisitasTerrenoP.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando visitas terreno de participantes!", VISITA_TERRENO_PARTICIPANTE, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/visitasterrenoparti";
                VisitaTerrenoParticipante[] envio = mVisitasTerrenoP.toArray(new VisitaTerrenoParticipante[mVisitasTerrenoP.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<VisitaTerrenoParticipante[]> requestEntity =
                        new HttpEntity<VisitaTerrenoParticipante[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
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
    /*************** VisitaFinalCasoCovid19 ************/
    /***************************************************/
    // url, username, password
    protected String cargarVisitasFinalesCasoCovid19(String url, String username,
                                                     String password) throws Exception {
        try {
            if(mVisitasFinales.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando visitas finales COVID19!", VISITAS_FINALES_COVID19, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/visitasFinalesCovid19";
                VisitaFinalCasoCovid19[] envio = mVisitasFinales.toArray(new VisitaFinalCasoCovid19[mVisitasFinales.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<VisitaFinalCasoCovid19[]> requestEntity =
                        new HttpEntity<VisitaFinalCasoCovid19[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
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
    /*************** SintomasVisitaFinalCovid19 ************/
    /***************************************************/
    // url, username, password
    protected String cargarSintomasVisitasFinalesCovid19(String url, String username,
                                                  String password) throws Exception {
        try {
            if(mSintomasFinal.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando síntomas visitas finales COVID19!", SINT_VISITAS_FINALES_COVID19, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/sintVisitasFinalesCovid19";
                SintomasVisitaFinalCovid19[] envio = mSintomasFinal.toArray(new SintomasVisitaFinalCovid19[mSintomasFinal.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<SintomasVisitaFinalCovid19[]> requestEntity =
                        new HttpEntity<SintomasVisitaFinalCovid19[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
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
    /*************** ObsequioGeneral ************/
    /***************************************************/
    // url, username, password
    protected String cargarObsequioGeneral(String url, String username,
                                           String password) throws Exception {
        try {
            if(mObsequiosGeneral.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando obsequios!", OBSEQUIOS, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/obsequiosgen";
                ObsequioGeneral[] envio = mObsequiosGeneral.toArray(new ObsequioGeneral[mObsequiosGeneral.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ObsequioGeneral[]> requestEntity =
                        new HttpEntity<ObsequioGeneral[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
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
