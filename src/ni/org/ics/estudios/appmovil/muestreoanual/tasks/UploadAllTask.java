package ni.org.ics.estudios.appmovil.muestreoanual.tasks;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.*;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.*;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaCasaSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaParticipanteSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.listeners.UploadListener;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class UploadAllTask extends UploadTask {

    private final Context mContext;

    public UploadAllTask(Context context) {
        mContext = context;
    }

    private EstudiosAdapter estudioAdapter = null;

    protected static final String TAG = UploadAllTask.class.getSimpleName();
    private List<Casa> mCasas = new ArrayList<Casa>();
    private List<Participante> mParticipantes = new ArrayList<Participante>();
    private List<ParticipanteProcesos> mParticipantesProc = new ArrayList<ParticipanteProcesos>();
    private List<EncuestaCasa> mEncuestasCasas = new ArrayList<EncuestaCasa>();
    private List<EncuestaParticipante> mEncuestasParticipantes = new ArrayList<EncuestaParticipante>();
    private List<LactanciaMaterna> mLactanciasMaternas = new ArrayList<LactanciaMaterna>();
    private List<Muestra> mMuestras = new ArrayList<Muestra>();
    private List<PesoyTalla> mPyTs = new ArrayList<PesoyTalla>();
    private List<Obsequio> mObsequios = new ArrayList<Obsequio>();
    private List<Vacuna> mVacunas = new ArrayList<Vacuna>();
    private List<NewVacuna> mNewVacunas = new ArrayList<NewVacuna>();
    private List<DatosPartoBB> mDatosPartoBB = new ArrayList<DatosPartoBB>();
    private List<VisitaTerreno> mVisitasTerreno = new ArrayList<VisitaTerreno>();
    private List<DatosVisitaTerreno> mDatosVisitasTerreno = new ArrayList<DatosVisitaTerreno>();
    private List<ReConsentimientoDen> mReconsentimientos = new ArrayList<ReConsentimientoDen>();
    private List<ConsentimientoChik> mConsentimientoChiks = new ArrayList<ConsentimientoChik>();
    private List<CambioEstudio> mCambiosEstudio = new ArrayList<CambioEstudio>();
    private List<RecepcionBHC> mRecepcionBHCs = new ArrayList<RecepcionBHC>();
    private List<RecepcionSero> mRecepcionSeros = new ArrayList<RecepcionSero>();
    private List<Pinchazo> mPinchazos = new ArrayList<Pinchazo>();
    private List<RazonNoData> mRazonNoData = new ArrayList<RazonNoData>();
    private List<TempRojoBhc> mTempRojoBhcs = new ArrayList<TempRojoBhc>();
    private List<TempPbmc> mTempPbmcs = new ArrayList<TempPbmc>();
    private List<EncuestaSatisfaccion> mEncuestaSatisfaccions = new ArrayList<EncuestaSatisfaccion>();
    private List<ReConsentimientoDen2015> mReconsentimientos2015 = new ArrayList<ReConsentimientoDen2015>();
    private List<ReConsentimientoFlu2015> mReconsentimientosFlu2015 = new ArrayList<ReConsentimientoFlu2015>();
    private List<CodigosCasas> mCodigosCasas = new ArrayList<CodigosCasas>();
    private List<CambiosCasas> mCambiosCasas = new ArrayList<CambiosCasas>();
    private List<ConsentimientoZika> mConsentimientosZika = new ArrayList<ConsentimientoZika>();
    private List<EncuestaCasaSA> mEncuestasCasaSA = new ArrayList<EncuestaCasaSA>();
    private List<EncuestaParticipanteSA> mEncuestasParticipanteSA = new ArrayList<EncuestaParticipanteSA>();
    private List<Tamizaje> mTamizajes = new ArrayList<Tamizaje>();
    private List<CartaConsentimiento> mCartasConsent = new ArrayList<CartaConsentimiento>();
    private List<ContactoParticipante> mContactos = new ArrayList<ContactoParticipante>();
    private List<ParticipanteSeroprevalencia> mParticipantesSa = new ArrayList<ParticipanteSeroprevalencia>();
    private List<CambioDomicilio> mCambiosDom = new ArrayList<CambioDomicilio>();
    private List<VisitaTerrenoParticipante> mVisitasTerrenoP = new ArrayList<VisitaTerrenoParticipante>();
    private List<EnfermedadCronica> mEnfermedades = new ArrayList<EnfermedadCronica>();

    private String url = null;
    private String username = null;
    private String password = null;
    private String error = null;
    protected UploadListener mStateListener;

    @Override
    protected String doInBackground(String... values) {
        url = values[0];
        username = values[1];
        password = values[2];

        try {
            estudioAdapter = new EstudiosAdapter(mContext, password, false,false);
            estudioAdapter.open();

            try {
                error = cargarCasas(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarParticipantes(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarParticipantesProc(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    saveParticipantesProc(Constants.STATUS_NOT_SUBMITTED);
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarEncCasas(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarEncParticipantes(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarLactanciasMaternas(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarPyTs(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarMuestras(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarObsequios(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarVacunas(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }
            try {
                error = cargarNewVacunas(url, username, password);
                if (!error.matches("Datos recibidos!")){
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarDatosPartoBB(url, username, password);
                if (!error.matches("Datos recibidos!")){
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarVisitas(url, username, password);
                if (!error.matches("Datos recibidos!")){
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarDatosVisitasTerreno(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarReconsentimientos(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarConsentimientoChik(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarReConsentimientos2015(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarConsentimientoZika(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarReConsentimientosFlu2015(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarCodigosCasas(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarCambiosCasas(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarCambiosEstudio(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarRecepcionBHCs(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarRecepcionSeros(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarPinchazos(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarTempRojoBhcs(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarTempPbmcs(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarEncuestaSatisfaccions(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            try {
                error = cargarRazonNoData(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

            getEncuestasSA();
            try {
                saveEncuestasCasaSA(Constants.STATUS_SUBMITTED);
                error = cargarEncuestasCasaSa(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    saveEncuestasCasaSA(Constants.STATUS_NOT_SUBMITTED);
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }
            try {
                saveEncuestasParticipantesSA(Constants.STATUS_SUBMITTED);
                error = cargarEncuestasParticipantesSa(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    saveEncuestasParticipantesSA(Constants.STATUS_NOT_SUBMITTED);
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }
            try {
                getTamizajes();
                saveTamizajes(Constants.STATUS_SUBMITTED);
                error = cargarTamizajes(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    saveTamizajes(Constants.STATUS_NOT_SUBMITTED);
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }
            try {
                getCartasConsent();
                saveCartasConset(Constants.STATUS_SUBMITTED);
                error = cargarCartasConsentimientos(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    saveCartasConset(Constants.STATUS_NOT_SUBMITTED);
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }
            try {
                mContactos = estudioAdapter.getContactosParticipantes(MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'", null);
                saveContactos(Constants.STATUS_SUBMITTED);
                error = cargarContactos(url, username, password);
                if (!error.matches("Datos recibidos!")){
                    saveContactos(Constants.STATUS_NOT_SUBMITTED);
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }
            try {
                mParticipantesSa = estudioAdapter.getParticipantesSeroprevalencia(MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'", null);
                saveParticipantesSa(Constants.STATUS_SUBMITTED);
                error = cargarParticipantesSA(url, username, password);
                if (!error.matches("Datos recibidos!")){
                    saveParticipantesSa(Constants.STATUS_NOT_SUBMITTED);
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }
            try {
                mCambiosDom = estudioAdapter.getCambiosDomicilio(MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'", null);
                saveCambiosDomicilio(Constants.STATUS_SUBMITTED);
                error = cargarCambioDomicilio(url, username, password);
                if (!error.matches("Datos recibidos!")){
                    saveCambiosDomicilio(Constants.STATUS_NOT_SUBMITTED);
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }
            try {
                mVisitasTerrenoP = estudioAdapter.getVisitasTerrenoParticipantes(MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'", null);
                saveVisitaTerrenoParticipante(Constants.STATUS_SUBMITTED);
                error = cargarVisitaTerrenoParticipante(url, username, password);
                if (!error.matches("Datos recibidos!")){
                    saveVisitaTerrenoParticipante(Constants.STATUS_NOT_SUBMITTED);
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }
            try {
                mEnfermedades = estudioAdapter.getEnfermedadesCronicas(MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'", null);
                saveEnfermedadCronica(Constants.STATUS_SUBMITTED);
                error = cargarEnfermedadCronica(url, username, password);
                if (!error.matches("Datos recibidos!")){
                    saveEnfermedadCronica(Constants.STATUS_NOT_SUBMITTED);
                    return error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return e1.getLocalizedMessage();
            }

        } catch (Exception e1) {

            e1.printStackTrace();
            return e1.getLocalizedMessage();
        }finally {
            estudioAdapter.close();
        }
        return error;
    }

    private void getTamizajes(){
        mTamizajes = estudioAdapter.getTamizajes(MainDBConstants.estado + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", MainDBConstants.codigo);
    }

    private void getCartasConsent(){
        mCartasConsent = estudioAdapter.getCartasConsentimientos(MainDBConstants.estado + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", MainDBConstants.codigo);
    }

    private void saveTamizajes(String estado) {
        int c = mTamizajes.size();
        for (Tamizaje tamizaje : mTamizajes) {
            tamizaje.setEstado(estado.charAt(0));
            estudioAdapter.editarTamizaje(tamizaje);
            publishProgress("Actualizando tamizajes en base local", Integer.valueOf(mTamizajes.indexOf(tamizaje)).toString(), Integer
                    .valueOf(c).toString());
        }
    }

    private void saveCartasConset(String estado) {
        int c = mCartasConsent.size();
        for (CartaConsentimiento carta : mCartasConsent) {
            carta.setEstado(estado.charAt(0));
            estudioAdapter.editarCartaConsentimiento(carta);
            publishProgress("Actualizando cartas consentimiento en base local", Integer.valueOf(mCartasConsent.indexOf(carta)).toString(), Integer
                    .valueOf(c).toString());
        }
    }

    private void saveContactos(String estado){
        int c = mCartasConsent.size();
        for (ContactoParticipante contacto : mContactos) {
            contacto.setEstado(estado.charAt(0));
            estudioAdapter.editarContactoParticipante(contacto);
            publishProgress("Actualizando contactos en base de datos local", Integer.valueOf(mContactos.indexOf(contacto)).toString(), Integer
                    .valueOf(c).toString());
        }
    }

    private void saveParticipantesSa(String estado){
        int c = mParticipantesSa.size();
        for (ParticipanteSeroprevalencia participanteSeroprevalencia : mParticipantesSa) {
            participanteSeroprevalencia.setEstado(estado.charAt(0));
            estudioAdapter.editarParticipanteSeroprevalencia(participanteSeroprevalencia);
            publishProgress("Actualizando participantes SA en base de datos local", Integer.valueOf(mParticipantesSa.indexOf(participanteSeroprevalencia)).toString(), Integer
                    .valueOf(c).toString());
        }
    }

    private void saveCambiosDomicilio(String estado){
        int c = mCambiosDom.size();
        for (CambioDomicilio cambioDomicilio : mCambiosDom) {
            cambioDomicilio.setEstado(estado.charAt(0));
            estudioAdapter.editarCambioDomicilio(cambioDomicilio);
            publishProgress("Actualizando cambios de domicilio en base de datos local", Integer.valueOf(mCambiosDom.indexOf(cambioDomicilio)).toString(), Integer
                    .valueOf(c).toString());
        }
    }

    private void saveVisitaTerrenoParticipante(String estado){
        int c = mVisitasTerrenoP.size();
        for (VisitaTerrenoParticipante visita : mVisitasTerrenoP) {
            visita.setEstado(estado.charAt(0));
            estudioAdapter.editarVisitaTerrenoParticipante(visita);
            publishProgress("Actualizando visitas a participante en base de datos local", Integer.valueOf(mVisitasTerrenoP.indexOf(visita)).toString(), Integer
                    .valueOf(c).toString());
        }
    }

    private void saveEnfermedadCronica(String estado){
        int c = mEnfermedades.size();
        for (EnfermedadCronica cronica : mEnfermedades) {
            cronica.setEstado(estado.charAt(0));
            estudioAdapter.editarEnfermedadCronica(cronica);
            publishProgress("Actualizando enfermedades crónicas en base de datos local", Integer.valueOf(mEnfermedades.indexOf(cronica)).toString(), Integer
                    .valueOf(c).toString());
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
    /********************* Tamizajes participantes ************************/
    /***************************************************/
    // url, username, password
    protected String cargarTamizajes(String url, String username,
                                     String password) throws Exception {
        try {
            if(mTamizajes.size()>0){
                // La URL de la solicitud POST
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
    /********************* Cartas de consentimiento ************************/
    /***************************************************/
    // url, username, password
    protected String cargarCartasConsentimientos(String url, String username,
                                                 String password) throws Exception {
        try {
            if(mCartasConsent.size()>0){
                // La URL de la solicitud POST
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

    // url, username, password
    protected String cargarCasas(String url, String username,
                                         String password) throws Exception {
        try {
            getCasas();
            if(mCasas.size()>0){
                saveCasas(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/casas";
                Casa[] envio = mCasas.toArray(new Casa[mCasas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Casa[]> requestEntity =
                        new HttpEntity<Casa[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveCasas(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveCasas(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void getCasas() {
        mCasas = estudioAdapter.getCasas(MainDBConstants.estado + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null);
    }

    private void saveCasas(String estado) {
        int c = mCasas.size();
        for (Casa casa : mCasas) {
            casa.setEstado(estado.charAt(0));
            estudioAdapter.updateCasaSent(casa);
            publishProgress("Actualizando casas", Integer.valueOf(mCasas.indexOf(casa)).toString(), Integer
                    .valueOf(c).toString());
        }
    }

    // url, username, password
    protected String cargarParticipantes(String url, String username,
                                         String password) throws Exception {
        try {
            getParticipantes();
            if(mParticipantes.size()>0){
                saveParticipantes(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
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
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveParticipantes(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveParticipantes(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    // url, username, password
    protected String cargarParticipantesProc(String url, String username,
                                         String password) throws Exception {
        try {
            getParticipantesProc();
            if(mParticipantesProc.size()>0){
                saveParticipantesProc(Constants.STATUS_SUBMITTED);
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

    private void saveParticipantes(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mParticipantes.size();
        for (Participante participante : mParticipantes) {
            participante.setEstado(estado.charAt(0));
            estudioAdapter.updateParticipantesSent(participante);
            publishProgress("Actualizando participantes", Integer.valueOf(mParticipantes.indexOf(participante)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getParticipantes() {

        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mParticipantes = estudioAdapter.getParticipantes(MainDBConstants.estado + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null);
        //ca.close();
    }

    private void getParticipantesProc() {
        mParticipantesProc =  estudioAdapter.getListaParticipantesProc(Constants.STATUS_NOT_SUBMITTED);
    }

    private void saveParticipantesProc(String estado) {
        int c = mParticipantesProc.size();
        for (ParticipanteProcesos participanteProc : mParticipantesProc) {
            participanteProc.getMovilInfo().setEstado(estado);
            estudioAdapter.updateParticipanteProcSent(participanteProc);
            publishProgress("Actualizando procesos participantes", Integer.valueOf(mParticipantesProc.indexOf(participanteProc)).toString(), Integer
                    .valueOf(c).toString());
        }
    }

    /**Encuestas de casa**/
    // url, username, password
    protected String cargarEncCasas(String url, String username,
                                    String password) throws Exception {
        try {
            getEncCasas();
            if(mEncuestasCasas.size()>0){
                saveEncCasas(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/encuestascasas";
                EncuestaCasa[] envio = mEncuestasCasas.toArray(new EncuestaCasa[mEncuestasCasas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaCasa[]> requestEntity =
                        new HttpEntity<EncuestaCasa[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveEncCasas(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveEncCasas(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveEncCasas(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mEncuestasCasas.size();
        for (EncuestaCasa enccasa : mEncuestasCasas) {
            enccasa.getMovilInfo().setEstado(estado);
            estudioAdapter.updateEncuestasCasasSent(enccasa);
            publishProgress("Actualizando encuestas casas", Integer.valueOf(mEncuestasCasas.indexOf(enccasa)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getEncCasas() {

        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mEncuestasCasas= estudioAdapter.getListaEncuestaCasasSinEnviar();
        //ca.close();
    }

    /**Encuestas de participantes**/
    // url, username, password
    protected String cargarEncParticipantes(String url, String username,
                                            String password) throws Exception {
        try {
            getEncParticipantes();
            if(mEncuestasParticipantes.size()>0){
                saveEncParticipantes(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/encuestasparticipantes";
                EncuestaParticipante[] envio = mEncuestasParticipantes.toArray(new EncuestaParticipante[mEncuestasParticipantes.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaParticipante[]> requestEntity =
                        new HttpEntity<EncuestaParticipante[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveEncParticipantes(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveEncParticipantes(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void getEncParticipantes() {
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mEncuestasParticipantes = estudioAdapter.getListaEncuestaParticipantesSinEnviar();
        //ca.close();
    }

    private void saveEncParticipantes(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mEncuestasParticipantes.size();
        for (EncuestaParticipante encparticipante : mEncuestasParticipantes) {
            encparticipante.getMovilInfo().setEstado(estado);
            estudioAdapter.updateEncPartSent(encparticipante);
            publishProgress("Actualizando encuestas participantes", Integer.valueOf(mEncuestasParticipantes.indexOf(encparticipante)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    /**Encuestas de Lactancia Materna**/
    // url, username, password
    protected String cargarLactanciasMaternas(String url, String username,
                                              String password) throws Exception {
        try {
            getLactanciasMaternas();
            if(mLactanciasMaternas.size()>0){
                // La URL de la solicitud POST
                saveEncLactancias(Constants.STATUS_SUBMITTED);
                final String urlRequest = url + "/movil/lactmaterna";
                LactanciaMaterna[] envio = mLactanciasMaternas.toArray(new LactanciaMaterna[mLactanciasMaternas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<LactanciaMaterna[]> requestEntity =
                        new HttpEntity<LactanciaMaterna[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveEncLactancias(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveEncLactancias(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void getLactanciasMaternas() {

        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mLactanciasMaternas = estudioAdapter.getListaLactanciaMaternasSinEnviar();
        //ca.close();
    }

    private void saveEncLactancias(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mLactanciasMaternas.size();
        for (LactanciaMaterna enclactancia : mLactanciasMaternas) {
            enclactancia.getMovilInfo().setEstado(estado);
            estudioAdapter.updateLacMatSent(enclactancia);
            publishProgress("Actualizando encuestas lactancia", Integer.valueOf(mLactanciasMaternas.indexOf(enclactancia)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }


    /**Peso y talla**/
    // url, username, password
    protected String cargarPyTs(String url, String username,
                                String password) throws Exception {
        try {
            getPTs();
            if(mPyTs.size()>0){
                // La URL de la solicitud POST
                saveEncPyTs(Constants.STATUS_SUBMITTED);
                final String urlRequest = url + "/movil/pts";
                PesoyTalla[] envio = mPyTs.toArray(new PesoyTalla[mPyTs.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<PesoyTalla[]> requestEntity =
                        new HttpEntity<PesoyTalla[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveEncPyTs(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveEncPyTs(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveEncPyTs(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mPyTs.size();
        for (PesoyTalla pyt : mPyTs) {
            pyt.getMovilInfo().setEstado(estado);
            estudioAdapter.updatePTsSent(pyt);
            publishProgress("Actualizando peso y talla", Integer.valueOf(mPyTs.indexOf(pyt)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getPTs(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mPyTs = estudioAdapter.getListaPesoyTallasSinEnviar();
        //ca.close();
    }

    /**Muestras**/
    // url, username, password
    protected String cargarMuestras(String url, String username,
                                    String password) throws Exception {
        try {
            getMuestras();
            if(mMuestras.size()>0){
                saveMuestras(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/muestrasMA";
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
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveMuestras(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveMuestras(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveMuestras(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mMuestras.size();
        for (Muestra muestra : mMuestras) {
            muestra.getMovilInfo().setEstado(estado);
            estudioAdapter.updateMuestraSent(muestra);
            publishProgress("Actualizando muestras", Integer.valueOf(mMuestras.indexOf(muestra)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getMuestras(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mMuestras = estudioAdapter.getListaMuestrasSinEnviar();
        //ca.close();
    }


    /**Obsequios**/
    // url, username, password
    protected String cargarObsequios(String url, String username,
                                     String password) throws Exception {
        try {
            getObsequios();
            if(mObsequios.size()>0){
                saveObsequios(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/obsequios";
                Obsequio[] envio = mObsequios.toArray(new Obsequio[mObsequios.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Obsequio[]> requestEntity =
                        new HttpEntity<Obsequio[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveObsequios(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveObsequios(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveObsequios(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mObsequios.size();
        for (Obsequio obsequio : mObsequios) {
            obsequio.getMovilInfo().setEstado(estado);
            estudioAdapter.updateObsequioSent(obsequio);
            publishProgress("Actualizando Obsequios", Integer.valueOf(mObsequios.indexOf(obsequio)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getObsequios(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mObsequios = estudioAdapter.getListaObsequiosSinEnviar();
        //ca.close();
    }






    /**Vacunas**/
    // url, username, password
    protected String cargarVacunas(String url, String username,
                                   String password) throws Exception {
        try {
            getVacunas();
            if(mVacunas.size()>0){
                saveVacunas(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/vacunas";
                Vacuna[] envio = mVacunas.toArray(new Vacuna[mVacunas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Vacuna[]> requestEntity =
                        new HttpEntity<Vacuna[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveVacunas(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveVacunas(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveVacunas(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mVacunas.size();
        for (Vacuna vacuna : mVacunas) {
            vacuna.getMovilInfo().setEstado(estado);
            estudioAdapter.updateVacSent(vacuna);
            publishProgress("Actualizando Vacunas", Integer.valueOf(mVacunas.indexOf(vacuna)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getVacunas(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mVacunas = estudioAdapter.getListaVacunasSinEnviar();
        //ca.close();
    }


    /**Vacunas**/
    // url, username, password
    protected String cargarNewVacunas(String url, String username,
                                      String password) throws Exception {
        try {
            getNewVacunas();
            if(mNewVacunas.size()>0){
                saveNewVacunas(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/newvacunas";
                NewVacuna[] envio = mNewVacunas.toArray(new NewVacuna[mNewVacunas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<NewVacuna[]> requestEntity =
                        new HttpEntity<NewVacuna[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveNewVacunas(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveNewVacunas(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveNewVacunas(String estado) {
       // CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mNewVacunas.size();
        for (NewVacuna vacuna : mNewVacunas) {
            vacuna.getMovilInfo().setEstado(estado);
            estudioAdapter.updateNewVacSent(vacuna);
            publishProgress("Actualizando Vacunas", Integer.valueOf(mNewVacunas.indexOf(vacuna)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getNewVacunas(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mNewVacunas = estudioAdapter.getListaNewVacunasSinEnviar();
        //ca.close();
    }


    /**Datos Parto BB**/
    // url, username, password
    protected String cargarDatosPartoBB(String url, String username,
                                        String password) throws Exception {
        try {
            getDatosPartoBB();
            if(mDatosPartoBB.size()>0){
                saveDatosPartoBB(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/datospartobb";
                DatosPartoBB[] envio = mDatosPartoBB.toArray(new DatosPartoBB[mDatosPartoBB.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<DatosPartoBB[]> requestEntity =
                        new HttpEntity<DatosPartoBB[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveDatosPartoBB(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveDatosPartoBB(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveDatosPartoBB(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mDatosPartoBB.size();
        for (DatosPartoBB datosParto : mDatosPartoBB) {
            datosParto.getMovilInfo().setEstado(estado);
            estudioAdapter.updateDatosPartoBB(datosParto);
            publishProgress("Actualizando DatosPartoBB", Integer.valueOf(mDatosPartoBB.indexOf(datosParto)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getDatosPartoBB(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mDatosPartoBB = estudioAdapter.getListaDatosPartoBBSinEnviar();
        //ca.close();
    }


    /**Visitas**/
    // url, username, password
    protected String cargarVisitas(String url, String username,
                                   String password) throws Exception {
        try {
            getVisitas();
            if(mVisitasTerreno.size()>0){
                saveVisitas(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/visitasMA";
                VisitaTerreno[] envio = mVisitasTerreno.toArray(new VisitaTerreno[mVisitasTerreno.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<VisitaTerreno[]> requestEntity =
                        new HttpEntity<VisitaTerreno[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveVisitas(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveVisitas(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveVisitas(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mVisitasTerreno.size();
        for (VisitaTerreno visita : mVisitasTerreno) {
            visita.getMovilInfo().setEstado(estado);
            estudioAdapter.updateVisitasSent(visita);
            publishProgress("Actualizando Visitas", Integer.valueOf(mVisitasTerreno.indexOf(visita)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getVisitas(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mVisitasTerreno = estudioAdapter.getListaVisitaTerrenosSinEnviar();
        //ca.close();
    }
    // url, username, password
    protected String cargarDatosVisitasTerreno(String url, String username,
                                               String password) throws Exception {
        try {
            getDatosVisitasTerreno();
            if(mDatosVisitasTerreno.size()>0){
                saveDatosVisitasTerreno(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/visitasn";
                DatosVisitaTerreno[] envio = mDatosVisitasTerreno.toArray(new DatosVisitaTerreno[mDatosVisitasTerreno.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<DatosVisitaTerreno[]> requestEntity =
                        new HttpEntity<DatosVisitaTerreno[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveDatosVisitasTerreno(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveDatosVisitasTerreno(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveDatosVisitasTerreno(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mDatosVisitasTerreno.size();
        for (DatosVisitaTerreno visita : mDatosVisitasTerreno) {
            visita.getMovilInfo().setEstado(estado);
            estudioAdapter.updateDatosVisitasSent(visita);
            publishProgress("Actualizando Datos Visitas", Integer.valueOf(mDatosVisitasTerreno.indexOf(visita)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getDatosVisitasTerreno(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mDatosVisitasTerreno = estudioAdapter.getListaDatosVisitaTerrenosSinEnviar();
        //ca.close();
    }

    /**cargar Reconsentimientos**/
    // url, username, password
    protected String cargarReconsentimientos(String url, String username,
                                             String password) throws Exception {
        try {
            getReconsentimientos();
            if(mReconsentimientos.size()>0){
                saveReconsentimientos(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/recons";
                ReConsentimientoDen[] envio = mReconsentimientos.toArray(new ReConsentimientoDen[mReconsentimientos.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ReConsentimientoDen[]> requestEntity =
                        new HttpEntity<ReConsentimientoDen[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveReconsentimientos(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveReconsentimientos(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveReconsentimientos(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mReconsentimientos.size();
        for (ReConsentimientoDen recons : mReconsentimientos) {
            recons.getMovilInfo().setEstado(estado);
            estudioAdapter.updateReconsSent(recons);
            publishProgress("Actualizando ReConsentimientoDen", Integer.valueOf(mReconsentimientos.indexOf(recons)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getReconsentimientos(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mReconsentimientos = estudioAdapter.getListaReConsentimientoDensSinEnviar();
        //ca.close();
    }


    /**cargar ConsentimientoChik**/
    // url, username, password
    protected String cargarConsentimientoChik(String url, String username,
                                              String password) throws Exception {
        try {
            getConsentimientoChik();
            if(mConsentimientoChiks.size()>0){
                saveConsentimientoChik(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/conschik";
                ConsentimientoChik[] envio = mConsentimientoChiks.toArray(new ConsentimientoChik[mConsentimientoChiks.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ConsentimientoChik[]> requestEntity =
                        new HttpEntity<ConsentimientoChik[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveConsentimientoChik(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveConsentimientoChik(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveConsentimientoChik(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mConsentimientoChiks.size();
        for (ConsentimientoChik cons : mConsentimientoChiks) {
            cons.getMovilInfo().setEstado(estado);
            estudioAdapter.updateConsChikSent(cons);
            publishProgress("Actualizando ConsentimientoChik", Integer.valueOf(mConsentimientoChiks.indexOf(cons)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getConsentimientoChik(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mConsentimientoChiks = estudioAdapter.getListaConsentimientoChikSinEnviar();
        //ca.close();
    }


    /**cargar ReConsentimientos2015**/
    // url, username, password
    protected String cargarReConsentimientos2015(String url, String username,
                                                 String password) throws Exception {
        try {
            getReConsentimiento2015();
            if(mReconsentimientos2015.size()>0){
                saveReconsentimientos2015(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/recons2015";
                ReConsentimientoDen2015[] envio = mReconsentimientos2015.toArray(new ReConsentimientoDen2015[mReconsentimientos2015.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ReConsentimientoDen2015[]> requestEntity =
                        new HttpEntity<ReConsentimientoDen2015[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveReconsentimientos2015(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveReconsentimientos2015(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveReconsentimientos2015(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mReconsentimientos2015.size();
        for (ReConsentimientoDen2015 cons : mReconsentimientos2015) {
            cons.getMovilInfo().setEstado(estado);
            estudioAdapter.updateRecons2015Sent(cons);
            publishProgress("Actualizando ReConsentimientoDen2015", Integer.valueOf(mReconsentimientos2015.indexOf(cons)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getReConsentimiento2015(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mReconsentimientos2015 = estudioAdapter.getListaReConsentimientoDen2015sSinEnviar();
        //ca.close();
    }


    /**cargar ConsentimientoZika**/
    // url, username, password
    protected String cargarConsentimientoZika(String url, String username,
                                              String password) throws Exception {
        try {
            getConsentimientoZika();
            if(mConsentimientosZika.size()>0){
                saveConsentimientoZika(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/conszika";
                ConsentimientoZika[] envio = mConsentimientosZika.toArray(new ConsentimientoZika[mConsentimientosZika.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ConsentimientoZika[]> requestEntity =
                        new HttpEntity<ConsentimientoZika[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveConsentimientoZika(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveConsentimientoZika(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveConsentimientoZika(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mConsentimientosZika.size();
        for (ConsentimientoZika cons : mConsentimientosZika) {
            cons.getMovilInfo().setEstado(estado);
            estudioAdapter.updateConsZikaSent(cons);
            publishProgress("Actualizando ConsentimientoZika", Integer.valueOf(mConsentimientosZika.indexOf(cons)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getConsentimientoZika(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mConsentimientosZika = estudioAdapter.getListaConsentimientoZikasSinEnviar();
        //ca.close();
    }

    /**cargar CodigosCasas**/
    // url, username, password
    protected String cargarCodigosCasas(String url, String username,
                                        String password) throws Exception {
        try {
            getCodigosCasas();
            if(mCodigosCasas.size()>0){
                saveCodigosCasas(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/codigoscasas";
                CodigosCasas[] envio = mCodigosCasas.toArray(new CodigosCasas[mCodigosCasas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CodigosCasas[]> requestEntity =
                        new HttpEntity<CodigosCasas[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveCodigosCasas(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveCodigosCasas(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveCodigosCasas(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mCodigosCasas.size();
        for (CodigosCasas cons : mCodigosCasas) {
            cons.setEstado(estado);
            estudioAdapter.updateCodigosCasasSent(cons);
            publishProgress("Actualizando CodigosCasas", Integer.valueOf(mCodigosCasas.indexOf(cons)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getCodigosCasas(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mCodigosCasas = estudioAdapter.getListaCodigosCasasSinEnviar();
        //ca.close();
    }


    /**cargar CambiosCasas**/
    // url, username, password
    protected String cargarCambiosCasas(String url, String username,
                                        String password) throws Exception {
        try {
            getCambiosCasas();
            if(mCambiosCasas.size()>0){
                saveCambiosCasas(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/cambioscasas";
                CambiosCasas[] envio = mCambiosCasas.toArray(new CambiosCasas[mCambiosCasas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CambiosCasas[]> requestEntity =
                        new HttpEntity<CambiosCasas[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveCambiosCasas(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveCambiosCasas(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveCambiosCasas(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mCambiosCasas.size();
        for (CambiosCasas cons : mCambiosCasas) {
            cons.setEstado(estado);
            estudioAdapter.updateCambiosCasasSent(cons);
            publishProgress("Actualizando CambiosCasas", Integer.valueOf(mCambiosCasas.indexOf(cons)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getCambiosCasas(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mCambiosCasas = estudioAdapter.getListaCambiosCasasSinEnviar();
        //ca.close();
    }


    /**cargar CambioEstudio**/
    // url, username, password
    protected String cargarCambiosEstudio(String url, String username,
                                          String password) throws Exception {
        try {
            getCambiosEstudio();
            if(mCambiosEstudio.size()>0){
                saveCambiosEstudio(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/cambest";
                CambioEstudio[] envio = mCambiosEstudio.toArray(new CambioEstudio[mCambiosEstudio.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CambioEstudio[]> requestEntity =
                        new HttpEntity<CambioEstudio[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveCambiosEstudio(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveCambiosEstudio(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveCambiosEstudio(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mCambiosEstudio.size();
        for (CambioEstudio cons : mCambiosEstudio) {
            cons.getMovilInfo().setEstado(estado);
            estudioAdapter.updateCambiosEstudioSent(cons);
            publishProgress("Actualizando CambioEstudio", Integer.valueOf(mCambiosEstudio.indexOf(cons)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getCambiosEstudio(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mCambiosEstudio = estudioAdapter.getListaCambiosEstudioSinEnviar();
        //ca.close();
    }



    /**cargar RecepcionBHCs**/
    // url, username, password
    protected String cargarRecepcionBHCs(String url, String username,
                                         String password) throws Exception {
        try {
            getRecepcionBHCs();
            if(mRecepcionBHCs.size()>0){
                saveRecepcionBHCs(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/bhcs";
                RecepcionBHC[] envio = mRecepcionBHCs.toArray(new RecepcionBHC[mRecepcionBHCs.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<RecepcionBHC[]> requestEntity =
                        new HttpEntity<RecepcionBHC[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveRecepcionBHCs(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveRecepcionBHCs(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveRecepcionBHCs(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mRecepcionBHCs.size();
        for (RecepcionBHC bhc : mRecepcionBHCs) {
            bhc.setEstado(estado);
            estudioAdapter.updateBHCsSent(bhc);
            publishProgress("Actualizando RecepcionBHC", Integer.valueOf(mRecepcionBHCs.indexOf(bhc)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getRecepcionBHCs(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mRecepcionBHCs = estudioAdapter.getListaRecepcionBHCSinEnviar();
        //ca.close();
    }


    /**cargar RecepcionSeros**/
    // url, username, password
    protected String cargarRecepcionSeros(String url, String username,
                                          String password) throws Exception {
        try {
            getRecepcionSeros();
            if(mRecepcionSeros.size()>0){
                saveRecepcionSeros(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/seros";
                RecepcionSero[] envio = mRecepcionSeros.toArray(new RecepcionSero[mRecepcionSeros.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<RecepcionSero[]> requestEntity =
                        new HttpEntity<RecepcionSero[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveRecepcionSeros(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveRecepcionSeros(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveRecepcionSeros(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mRecepcionSeros.size();
        for (RecepcionSero rojo : mRecepcionSeros) {
            rojo.setEstado(estado);
            estudioAdapter.updateSerosSent(rojo);
            publishProgress("Actualizando RecepcionSero", Integer.valueOf(mRecepcionSeros.indexOf(rojo)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getRecepcionSeros(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mRecepcionSeros = estudioAdapter.getListaRecepcionSeroSinEnviar();
        //ca.close();
    }


    /**cargar Pinchazos**/
    // url, username, password
    protected String cargarPinchazos(String url, String username,
                                     String password) throws Exception {
        try {
            getPinchazos();
            if(mPinchazos.size()>0){
                savePinchazos(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/pins";
                Pinchazo[] envio = mPinchazos.toArray(new Pinchazo[mPinchazos.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Pinchazo[]> requestEntity =
                        new HttpEntity<Pinchazo[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    savePinchazos(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            savePinchazos(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void savePinchazos(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mPinchazos.size();
        for (Pinchazo pinchazo : mPinchazos) {
            pinchazo.setEstado(estado);
            estudioAdapter.updatePinchazosSent(pinchazo);
            publishProgress("Actualizando Pinchazos", Integer.valueOf(mPinchazos.indexOf(pinchazo)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getPinchazos(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mPinchazos = estudioAdapter.getListaPinchazosSinEnviar();
        //ca.close();
    }

    /**cargar TempRojoBhc**/
    // url, username, password
    protected String cargarTempRojoBhcs(String url, String username,
                                        String password) throws Exception {
        try {
            getTempRojoBhcs();
            if(mTempRojoBhcs.size()>0){
                saveTempRojoBhcs(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/trojos";
                TempRojoBhc[] envio = mTempRojoBhcs.toArray(new TempRojoBhc[mTempRojoBhcs.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<TempRojoBhc[]> requestEntity =
                        new HttpEntity<TempRojoBhc[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveTempRojoBhcs(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveTempRojoBhcs(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveTempRojoBhcs(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mTempRojoBhcs.size();
        for (TempRojoBhc temp : mTempRojoBhcs) {
            temp.setEstado(estado);
            estudioAdapter.updateTrojosSent(temp);
            publishProgress("Actualizando TempRojoBhc", Integer.valueOf(mTempRojoBhcs.indexOf(temp)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getTempRojoBhcs(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mTempRojoBhcs = estudioAdapter.getListaTempRojoBhcSinEnviar();
        //ca.close();
    }

    /**cargar TempPbmc**/
    // url, username, password
    protected String cargarTempPbmcs(String url, String username,
                                     String password) throws Exception {
        try {
            getTempPbmcs();
            if(mTempPbmcs.size()>0){
                saveTempPbmcs(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/tpbmcs";
                TempPbmc[] envio = mTempPbmcs.toArray(new TempPbmc[mTempPbmcs.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<TempPbmc[]> requestEntity =
                        new HttpEntity<TempPbmc[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveTempPbmcs(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveTempPbmcs(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveTempPbmcs(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mTempPbmcs.size();
        for (TempPbmc temp : mTempPbmcs) {
            temp.setEstado(estado);
            estudioAdapter.updateTpbmcsSent(temp);
            publishProgress("Actualizando TempPbmcs", Integer.valueOf(mTempPbmcs.indexOf(temp)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getTempPbmcs(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mTempPbmcs = estudioAdapter.getListaTempPbmcSinEnviar();
        //ca.close();
    }


    /**cargar Encuestas Satisfaccion**/
    // url, username, password
    protected String cargarEncuestaSatisfaccions(String url, String username,
                                                 String password) throws Exception {
        try {
            getEncuestaSatisfaccions();
            if(mEncuestaSatisfaccions.size()>0){
                saveEncuestaSatisfaccions(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/encuestassatisfaccion";
                EncuestaSatisfaccion[] envio = mEncuestaSatisfaccions.toArray(new EncuestaSatisfaccion[mEncuestaSatisfaccions.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaSatisfaccion[]> requestEntity =
                        new HttpEntity<EncuestaSatisfaccion[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveEncuestaSatisfaccions(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveEncuestaSatisfaccions(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveEncuestaSatisfaccions(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mEncuestaSatisfaccions.size();
        for (EncuestaSatisfaccion encuesta : mEncuestaSatisfaccions) {
            encuesta.getMovilInfo().setEstado(estado);
            estudioAdapter.updateEncSatSent(encuesta);
            publishProgress("Actualizando Encuestas Satisfaccion", Integer.valueOf(mEncuestaSatisfaccions.indexOf(encuesta)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getEncuestaSatisfaccions(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mEncuestaSatisfaccions = estudioAdapter.getEncuestaSatisfaccionSinEnviar();
        //ca.close();
    }


    /**cargar RazonNoData**/
    // url, username, password
    protected String cargarRazonNoData(String url, String username,
                                       String password) throws Exception {
        try {
            getRazonNoData();
            if(mRazonNoData.size()>0){
                saveRazonNoData(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/rnds";
                RazonNoData[] envio = mRazonNoData.toArray(new RazonNoData[mRazonNoData.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<RazonNoData[]> requestEntity =
                        new HttpEntity<RazonNoData[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveRazonNoData(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveRazonNoData(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveRazonNoData(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mRazonNoData.size();
        for (RazonNoData rnd : mRazonNoData) {
            rnd.setEstado(estado);
            estudioAdapter.updateRazonNoDataSent(rnd);
            publishProgress("Actualizando Razones No Datos", Integer.valueOf(mRazonNoData.indexOf(rnd)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getRazonNoData(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mRazonNoData = estudioAdapter.getListaRazonNoDataSinEnviar();
        //ca.close();
    }


    /**cargar ReConsentimientosFlu2015**/
    // url, username, password
    protected String cargarReConsentimientosFlu2015(String url, String username,
                                                    String password) throws Exception {
        try {
            getReConsentimientoFlu2015();
            if(mReconsentimientosFlu2015.size()>0){
                saveReconsentimientosFlu2015(Constants.STATUS_SUBMITTED);
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/reconsflu2015";
                ReConsentimientoFlu2015[] envio = mReconsentimientosFlu2015.toArray(new ReConsentimientoFlu2015[mReconsentimientosFlu2015.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ReConsentimientoFlu2015[]> requestEntity =
                        new HttpEntity<ReConsentimientoFlu2015[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    saveReconsentimientosFlu2015(Constants.STATUS_NOT_SUBMITTED);
                }
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            saveReconsentimientosFlu2015(Constants.STATUS_NOT_SUBMITTED);
            return e.getMessage();
        }

    }

    private void saveReconsentimientosFlu2015(String estado) {
        //CohorteAdapterEnvio actualizar = new CohorteAdapterEnvio();
        //actualizar.open();
        int c = mReconsentimientosFlu2015.size();
        for (ReConsentimientoFlu2015 cons : mReconsentimientosFlu2015) {
            cons.getMovilInfo().setEstado(estado);
            estudioAdapter.updateReconsFlu2015Sent(cons);
            publishProgress("Actualizando ReConsentimientoFlu2015", Integer.valueOf(mReconsentimientosFlu2015.indexOf(cons)).toString(), Integer
                    .valueOf(c).toString());
        }
        //actualizar.close();
    }

    private void getReConsentimientoFlu2015(){
        //CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
        //ca.open();
        mReconsentimientosFlu2015 = estudioAdapter.getListaReConsentimientoFlu2015sSinEnviar();
        //ca.close();
    }

    private void getEncuestasSA(){
        String filtro = MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'";
        mEncuestasCasaSA = estudioAdapter.getEncuestasCasaSA(filtro, null);
        mEncuestasParticipanteSA = estudioAdapter.getEncuestasParticipanteSA(filtro,null);
    }

    private void saveEncuestasCasaSA(String estado){
        int c = mEncuestasCasaSA.size();
        for (EncuestaCasaSA encuestaCasaSA : mEncuestasCasaSA) {
            encuestaCasaSA.setEstado(estado.charAt(0));
            estudioAdapter.editarEncuestaCasaSA(encuestaCasaSA);
            publishProgress("Actualizando encuestas casas seroprevalencia en base de datos local", Integer.valueOf(mEncuestasCasaSA.indexOf(encuestaCasaSA)).toString(), Integer
                    .valueOf(c).toString());
        }
    }

    private void saveEncuestasParticipantesSA(String estado){
        int c = mEncuestasParticipanteSA.size();
        if(c>0){
            for (EncuestaParticipanteSA encuestaParticipanteSA : mEncuestasParticipanteSA) {
                encuestaParticipanteSA.setEstado(estado.charAt(0));
                estudioAdapter.editarEncuestaParticipanteSA(encuestaParticipanteSA);
                publishProgress("Actualizando encuestas de participantes seroprevalencia en base de datos local", Integer.valueOf(mEncuestasParticipanteSA.indexOf(encuestaParticipanteSA)).toString(), Integer
                        .valueOf(c).toString());
            }
        }
    }

    /***************************************************/
    /********************* Encuestas Casas sero prevalencia ************************/
    /***************************************************/
    // url, username, password
    protected String cargarEncuestasCasaSa(String url, String username,
                                           String password) throws Exception {
        try {
            if(mEncuestasCasaSA.size()>0){
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/encuestasCasaSA";
                EncuestaCasaSA[] envio = mEncuestasCasaSA.toArray(new EncuestaCasaSA[mEncuestasCasaSA.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaCasaSA[]> requestEntity =
                        new HttpEntity<EncuestaCasaSA[]>(envio, requestHeaders);
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
    /********************* Encuestas Participantes sero prevalencia ************************/
    /***************************************************/
    // url, username, password
    protected String cargarEncuestasParticipantesSa(String url, String username,
                                                    String password) throws Exception {
        try {
            if(mEncuestasParticipanteSA.size()>0){
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/encuestasParticipanteSA";
                EncuestaParticipanteSA[] envio = mEncuestasParticipanteSA.toArray(new EncuestaParticipanteSA[mEncuestasParticipanteSA.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaParticipanteSA[]> requestEntity =
                        new HttpEntity<EncuestaParticipanteSA[]>(envio, requestHeaders);
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
    /*************** Participantes SA ************/
    /***************************************************/
    // url, username, password
    protected String cargarParticipantesSA(String url, String username,
                                     String password) throws Exception {
        try {
            if(mParticipantesSa.size()>0){
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/participantesSA";
                ParticipanteSeroprevalencia[] envio = mParticipantesSa.toArray(new ParticipanteSeroprevalencia[mParticipantesSa.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ParticipanteSeroprevalencia[]> requestEntity =
                        new HttpEntity<ParticipanteSeroprevalencia[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone el registro y espera un mensaje de respuesta del servidor
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
    /*************** CambioDomicilio ************/
    /***************************************************/
    // url, username, password
    protected String cargarCambioDomicilio(String url, String username,
                                     String password) throws Exception {
        try {
            if(mCambiosDom.size()>0){
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/cambiosdomicilio";
                CambioDomicilio[] envio = mCambiosDom.toArray(new CambioDomicilio[mCambiosDom.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CambioDomicilio[]> requestEntity =
                        new HttpEntity<CambioDomicilio[]>(envio, requestHeaders);
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
    /*************** CambioDomicilio ************/
    /***************************************************/
    // url, username, password
    protected String cargarEnfermedadCronica(String url, String username,
                                           String password) throws Exception {
        try {
            if(mEnfermedades.size()>0){
                // La URL de la solicitud POST
                final String urlRequest = url + "/movil/enfermedadescro";
                EnfermedadCronica[] envio = mEnfermedades.toArray(new EnfermedadCronica[mEnfermedades.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EnfermedadCronica[]> requestEntity =
                        new HttpEntity<EnfermedadCronica[]>(envio, requestHeaders);
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