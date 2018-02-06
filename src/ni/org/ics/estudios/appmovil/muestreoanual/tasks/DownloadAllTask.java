package ni.org.ics.estudios.appmovil.muestreoanual.tasks;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import ni.org.ics.estudios.appmovil.database.muestreoanual.CohorteAdapter;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.*;
import ni.org.ics.estudios.appmovil.domain.users.Authority;
import ni.org.ics.estudios.appmovil.domain.users.UserPermissions;
import ni.org.ics.estudios.appmovil.domain.users.UserSistema;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class DownloadAllTask extends DownloadTask {

    private final Context mContext;

    public DownloadAllTask(Context context) {
        mContext = context;
    }

    private String error = null;
    private String url = null;
    private String username = null;
    private String password = null;
    private String activos = null;
    private String terreno = null;
    //private List<UserSistema> mUsuarios = null;
    //private List<Authority> mRoles = null;
    //private List<UserPermissions> permisos = null;
    //private List<Casa> mCasas = null;
    //private List<Participante> mParticipantes = null;
    //private List<ParticipanteProcesos> mParticipantesProc = null;
    private List<EncuestaCasa> mEncuestasCasas = null;
    private List<EncuestaParticipante> mEncuestasParticipantes = null;
    private List<LactanciaMaterna> mEncuestasLactancias = null;
    private List<PesoyTalla> mPyTs = null;
    private List<Muestra> mMuestras = null;
    private List<Obsequio> mObsequios = null;
    private List<Vacuna> mVacunas = null;
    private List<NewVacuna> mNewVacunas = null;
    private List<DatosPartoBB> mDatosPartoBBs = null;
    private List<VisitaTerreno> mVisitasTerreno = null;
    private List<DatosVisitaTerreno> mDatosVisitasTerreno = null;
    private List<ReConsentimientoDen> mReconsentimientos = null;
    private List<ReConsentimientoDen2015> mReconsentimientos2015 = null;
    private List<ConsentimientoZika> mConsentimientosZika = null;
    private List<ReConsentimientoFlu2015> mReconsentimientosFlu2015 = null;
    private List<CodigosCasas> mCodigosCasas = null;
    private List<RecepcionBHC> mRecepcionBHCs = null;
    private List<RecepcionSero> mRecepcionSeros = null;
    private List<Pinchazo> mPinchazos = null;
    private List<TempRojoBhc> mTempRojoBhcs = null;
    private List<TempPbmc> mTempPbmcs = null;
    private List<EncuestaSatisfaccion> mEncuestaSatisfaccions = null;

    private CohorteAdapter ca = null;

    private static final String NO_PERMISSION="No tiene acceso a esta opción";

    @Override
    protected String doInBackground(String... values) {

        url = values[0];
        username = values[1];
        password = values[2];
        activos = values[4];
        terreno = values[5];

        try {
            ca = new CohorteAdapter(mContext, password, false, false);
            ca.open();

            try {
                error = checkRole();
                if (error != null) {
                    return error;
                }
            } catch (Exception e) {
                // Regresa error al descargar
                e.printStackTrace();
                return e.getLocalizedMessage();
            }
/*
            try {
                error = descargarCasas();
                if (error != null) {
                    return error;
                }
            } catch (Exception e) {
                // Regresa error al descargar
                e.printStackTrace();
                return e.getLocalizedMessage();
            }

/
            if (mCasas != null) {
                // open db and clean entries
                ////ca.open();
                ca.borrarTodasCasas();
                // download and insert usuarios
                try {
                    addCasas(mCasas);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
                // close db and stream
                //ca.close();
            }

            try {
                error = descargarUsuarios();
                if (error != null) {
                    return error;
                }
            } catch (Exception e) {
                // Regresa error al descargar
                e.printStackTrace();
                return e.getLocalizedMessage();
            }


            if (mUsuarios != null) {
                // open db and clean entries
                ////ca.open();
                //ca.borrarTodosUsuarios();
                // download and insert usuarios
                try {
                    addUsuarios(mUsuarios);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
                // close db and stream
                //ca.close();
            }

            try {
                error = descargarRoles();
                if (error != null) {
                    return error;
                }
            } catch (Exception e) {
                // Regresa error al descargar
                e.printStackTrace();
                return e.getLocalizedMessage();
            }

            if (mRoles != null) {
                // open db and clean entries
                ////ca.open();
                ca.borrarTodosRoles();
                // download and insert barrios
                try {
                    addRoles(mRoles);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
                // close db and stream
                //ca.close();
            }

            try {
                error = descargarPermisosUsuarios();
            } catch (Exception e) {
                // Regresa error al descargar
                e.printStackTrace();
                return e.getLocalizedMessage();
            }

            if (permisos != null){
                // open db and clean entries
                //ca.open();
                ca.borrarTodosPermisos();
                // download and insert barrios
                try {
                    addPermisos(permisos);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
                // close db and stream
                //ca.close();
            }

            try {
                error = descargarParticipantes();
                if (error != null) {
                    return error;
                }
            } catch (Exception e) {
                // Regresa error al descargar
                e.printStackTrace();
                return e.getLocalizedMessage();
            }

            if (mParticipantes != null) {
                // open db and clean entries
                ////ca.open();
                ca.borrarTodosParticipantes();
                // download and insert
                try {
                    addParticipantes(mParticipantes);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
                // close db and stream
                //ca.close();
            }

            try {
                error = descargarParticipantesProcesos();
                if (error != null) {
                    return error;
                }
            } catch (Exception e) {
                // Regresa error al descargar
                e.printStackTrace();
                return e.getLocalizedMessage();
            }

            if (mParticipantesProc != null) {
                // open db and clean entries
                ////ca.open();
                ca.borrarTodosParticipantesProcesos();
                // download and insert
                try {
                    addParticipantesProcesos(mParticipantesProc);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
                // close db and stream
                //ca.close();
            }
*/
            if (!terreno.matches("Si")) {
                try {
                    error = descargarEncCasas();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mEncuestasCasas != null) {
                    // open db and clean entries
                    ////ca.open();
                    ca.borrarTodasEncCasas();
                    // download and insert
                    try {
                        addEncCasas(mEncuestasCasas);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarEncParticipantes();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mEncuestasParticipantes != null) {
                    // open db and clean entries
                    ////ca.open();
                    ca.borrarTodasEncParticipantes();
                    // download and insert
                    try {
                        addEncParticipantes(mEncuestasParticipantes);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }


                try {
                    error = descargarEncLactancia();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mEncuestasLactancias != null) {
                    // open db and clean entries
                    ////ca.open();
                    ca.borrarTodasLactanciaMaterna();
                    // download and insert
                    try {
                        addEncLactancias(mEncuestasLactancias);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }


                try {
                    error = descargarPyTs();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mPyTs != null) {
                    // open db and clean entries
                    ////ca.open();
                    ca.borrarTodasPT();
                    // download and insert
                    try {
                        addPyTs(mPyTs);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarMuestras();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mMuestras != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTodasMuestras();
                    // download and insert
                    try {
                        addMuestras(mMuestras);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }
                try {
                    error = descargarObsequios();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mObsequios != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTodosOB();
                    // download and insert
                    try {
                        addObsequios(mObsequios);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarVacunas();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mVacunas != null){
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTodasVacunas();
                    // download and insert
                    try {
                        addVacunas(mVacunas);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }


                try {
                    error = descargarNewVacunas();
                    if (error!=null){
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mNewVacunas != null){
                    // open db and clean entries
                    //ca.open();
                    ca.borrarNewVacuna();
                    // download and insert
                    try {
                        addNewVacunas(mNewVacunas);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }


                try {
                    error = descargarDatosPartoBB();
                    if (error!=null){
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mDatosPartoBBs != null){
                    // open db and clean entries
                    //ca.open();
                    ca.borrarDatosPartoBB();
                    // download and insert
                    try {
                        addDatosPartoBBs(mDatosPartoBBs);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarVisitas();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mVisitasTerreno != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTodasVisitaTerrenos();
                    // download and insert
                    try {
                        addVisitas(mVisitasTerreno);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarDatosVisitasTerreno();
                    if (error!=null){
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mDatosVisitasTerreno != null){
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTodasDatosVisitaTerrenos();
                    // download and insert
                    try {
                        addDatosVisitasTerreno(mDatosVisitasTerreno);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }


                try {
                    error = descargarReconsentimientos();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mReconsentimientos != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTodosReConsentimientos();
                    // download and insert
                    try {
                        addReconsentimientos(mReconsentimientos);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }


                try {
                    error = descargarReconsentimientos2015();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mReconsentimientos2015 != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTodosReConsentimientos2015();
                    // download and insert
                    try {
                        addReconsentimientos2015(mReconsentimientos2015);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarConsentimientosZika();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mConsentimientosZika != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTodosConsentimientoZika();
                    // download and insert
                    try {
                        addConsentimientoZika(mConsentimientosZika);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarReconsentimientosFlu2015();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mReconsentimientosFlu2015 != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTodosReConsentimientosFlu2015();
                    // download and insert
                    try {
                        addReconsentimientosFlu2015(mReconsentimientosFlu2015);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarCodigosCasas();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mCodigosCasas != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarCodigosCasas();
                    // download and insert
                    try {
                        addCodigosCasas(mCodigosCasas);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarRecepcionBHCs();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mRecepcionBHCs != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarRecepcionBHC();
                    // download and insert
                    try {
                        addRecepcionBHCs(mRecepcionBHCs);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }


                try {
                    error = descargarRecepcionSeros();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mRecepcionSeros != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarRecepcionSero();
                    // download and insert
                    try {
                        addRecepcionSeros(mRecepcionSeros);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }


                try {
                    error = descargarPinchazos();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mPinchazos != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarPinchazo();
                    // download and insert
                    try {
                        addPinchazos(mPinchazos);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarTempRojoBhcs();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mTempRojoBhcs != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTempRojoBhc();
                    // download and insert
                    try {
                        addTempRojoBhcs(mTempRojoBhcs);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarTempPbmcs();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mTempPbmcs != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTempPbmc();
                    // download and insert
                    try {
                        addTempPbmcs(mTempPbmcs);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }

                try {
                    error = descargarEncuestaSatisfaccions();
                    if (error != null) {
                        return error;
                    }
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (mEncuestaSatisfaccions != null) {
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTodasEncuestaSatisfaccion();
                    // download and insert
                    try {
                        addEncuestaSatisfaccions(mEncuestaSatisfaccions);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }
            } else {
                //ca.open();
                ca.borrarTodasEncCasas();
                ca.borrarTodasEncParticipantes();
                ca.borrarTodasLactanciaMaterna();
                ca.borrarTodasPT();
                ca.borrarPinchazo();
                ca.borrarRazonNoData();
                ca.borrarRecepcionBHC();
                ca.borrarRecepcionSero();
                ca.borrarTempPbmc();
                ca.borrarTempRojoBhc();
                ca.borrarTodasEncuestaSatisfaccion();
                ca.borrarTodasMuestras();
                ca.borrarTodasVacunas();
                ca.borrarTodasVisitaTerrenos();
                ca.borrarTodosOB();
                ca.borrarTodosReConsentimientos();
                ca.borrarTodosConsentimientoChik();
                ca.borrarTodosReConsentimientosFlu2015();
                ca.borrarTodosReConsentimientos2015();
                ca.borrarTodosConsentimientoZika();
                //ca.close();

            }
        }catch (Exception ex){
            ex.printStackTrace();
            return ex.getLocalizedMessage();
        }finally {
            ca.close();
        }

        return error;


    }

    // url, username, password
    protected String checkRole() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/role/{username}";

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

            // Perform the HTTP GET request
            ResponseEntity<Boolean> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Boolean.class, username);

            if (responseEntity.getBody()){
                return null;
            }
            else{
                return NO_PERMISSION;
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
/*
    private void addUsuarios(List<UserSistema> usuarios) throws Exception {

        int v = usuarios.size();

        ListIterator<UserSistema> iter = usuarios.listIterator();

        while (iter.hasNext()){
            UserSistema usuario = iter.next();
            if (!ca.existeUsuario(usuario.getUsername())){
                ca.crearUsuario(usuario);
            }
            else{
                ca.actualizarUsuario(usuario);
            }
            publishProgress("Usuarios", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }
    }
    // url, username, password
    protected String descargarUsuarios() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/usuarios";

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

            // Perform the HTTP GET request
            ResponseEntity<UserSistema[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    UserSistema[].class);

            // convert the array to a list and return it
            mUsuarios = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    private void addRoles(List<Authority> roles) throws Exception {

        int v = roles.size();

        ListIterator<Authority> iter = roles.listIterator();

        while (iter.hasNext()){
            ca.crearRol(iter.next());
            publishProgress("Roles", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    // url, username, password
    protected String descargarRoles() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/roles";

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

            // Perform the HTTP GET request
            ResponseEntity<Authority[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Authority[].class,username);

            // convert the array to a list and return it
            mRoles = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    private void addPermisos(List<UserPermissions> permisos) throws Exception {

        int v = permisos.size();

        ListIterator<UserPermissions> iter = permisos.listIterator();

        while (iter.hasNext()){
            ca.crearPermisosUsuario(iter.next());
            publishProgress("Permisos", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    // url, username, password
    protected String descargarPermisosUsuarios() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/permisos";

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

            // Perform the HTTP GET request
            ResponseEntity<UserPermissions[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    UserPermissions[].class);

            // convert the array to a list and return it
            permisos = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    private void addCasas(List<Casa> casas) throws Exception {

        int v = casas.size();

        ListIterator<Casa> iter = casas.listIterator();

        while (iter.hasNext()){
            ca.crearCasa(iter.next());
            publishProgress("Casas", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    // url, username, password
    protected String descargarCasas() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/casas";

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

            // Perform the HTTP GET request
            ResponseEntity<Casa[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Casa[].class);

            // convert the array to a list and return it
            mCasas = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    private void addParticipantes(List<Participante> participantes) throws Exception {

        int v = participantes.size();

        ListIterator<Participante> iter = participantes.listIterator();

        while (iter.hasNext()){
            ca.crearParticipante(iter.next());
            publishProgress("Participantes", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    // url, username, password
	protected String descargarParticipantes() throws Exception {
		try {
			// The URL for making the GET request
			final String urlRequest;
			if (!activos.matches("Si")){
				urlRequest = url + "/movil/participantes";
			}
			else{
				urlRequest = url + "/movil/participantesactivos";
			}


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

			// Perform the HTTP GET request
			ResponseEntity<Participante[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
					Participante[].class);

			// convert the array to a list and return it
			mParticipantes = Arrays.asList(responseEntity.getBody());
			return null;

		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			return e.getLocalizedMessage();
		}
	}

    private void addParticipantesProcesos(List<ParticipanteProcesos> participantes) throws Exception {

        int v = participantes.size();

        ListIterator<ParticipanteProcesos> iter = participantes.listIterator();

        while (iter.hasNext()){
            ca.crearParticipanteProcesos(iter.next());
            publishProgress("Participantes Procesos", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    // url, username, password
    protected String descargarParticipantesProcesos() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/participantesprocesos";

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

            // Perform the HTTP GET request
            ResponseEntity<ParticipanteProcesos[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteProcesos[].class);

            // convert the array to a list and return it
            mParticipantesProc = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
*/
    private void addEncCasas(List<EncuestaCasa> encuestasc) throws Exception {

        int v = encuestasc.size();

        ListIterator<EncuestaCasa> iter = encuestasc.listIterator();

        while (iter.hasNext()){
            ca.crearEncuestaCasa(iter.next());
            publishProgress("Encuestas casa", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addEncParticipantes(List<EncuestaParticipante> encuestasp) throws Exception {

        int v = encuestasp.size();

        ListIterator<EncuestaParticipante> iter = encuestasp.listIterator();

        while (iter.hasNext()){
            ca.crearEncuestaParticipante(iter.next());
            publishProgress("Encuestas participantes", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addEncLactancias(List<LactanciaMaterna> lactancias) throws Exception {

        int v = lactancias.size();

        ListIterator<LactanciaMaterna> iter = lactancias.listIterator();

        while (iter.hasNext()){
            ca.crearLactanciaMaterna(iter.next());
            publishProgress("Encuestas lactancia materna", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addPyTs(List<PesoyTalla> pesos) throws Exception {

        int v = pesos.size();

        ListIterator<PesoyTalla> iter = pesos.listIterator();

        while (iter.hasNext()){
            ca.crearPT(iter.next());
            publishProgress("Peso y Talla", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addMuestras(List<Muestra> muestras) throws Exception {

        int v = muestras.size();

        ListIterator<Muestra> iter = muestras.listIterator();

        while (iter.hasNext()){
            ca.crearMuestra(iter.next());
            publishProgress("Muestras", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addObsequios(List<Obsequio> obsequios) throws Exception {

        int v = obsequios.size();

        ListIterator<Obsequio> iter = obsequios.listIterator();

        while (iter.hasNext()){
            ca.crearOB(iter.next());
            publishProgress("Obsequios", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addVacunas(List<Vacuna> vacunas) throws Exception {

        int v = vacunas.size();

        ListIterator<Vacuna> iter = vacunas.listIterator();

        while (iter.hasNext()){
            ca.crearVacuna(iter.next());
            publishProgress("Vacunas", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }


    private void addNewVacunas(List<NewVacuna> vacunas) throws Exception {

        int v = vacunas.size();

        ListIterator<NewVacuna> iter = vacunas.listIterator();

        while (iter.hasNext()){
            ca.crearNewVacuna(iter.next());
            publishProgress("Vacunas", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addDatosPartoBBs(List<DatosPartoBB> datosPartoBB) throws Exception {

        int v = datosPartoBB.size();

        ListIterator<DatosPartoBB> iter = datosPartoBB.listIterator();

        while (iter.hasNext()){
            ca.crearDatosPartoBB(iter.next());
            publishProgress("DatosPartoBB", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addVisitas(List<VisitaTerreno> visitas) throws Exception {

        int v = visitas.size();

        ListIterator<VisitaTerreno> iter = visitas.listIterator();

        while (iter.hasNext()){
            ca.crearVisita(iter.next());
            publishProgress("Visitas de Terreno", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addDatosVisitasTerreno(List<DatosVisitaTerreno> visitas) throws Exception {

        int v = visitas.size();

        ListIterator<DatosVisitaTerreno> iter = visitas.listIterator();

        while (iter.hasNext()){
            ca.crearDatosVisita(iter.next());
            publishProgress("Datos de visitas de Terreno", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }


    private void addReconsentimientos(List<ReConsentimientoDen> recons) throws Exception {

        int v = recons.size();

        ListIterator<ReConsentimientoDen> iter = recons.listIterator();

        while (iter.hasNext()){
            ca.crearReConsentimiento(iter.next());
            publishProgress("Reconsentimientos", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }


    private void addReconsentimientos2015(List<ReConsentimientoDen2015> recons) throws Exception {

        int v = recons.size();

        ListIterator<ReConsentimientoDen2015> iter = recons.listIterator();

        while (iter.hasNext()){
            ca.crearReConsentimiento2015(iter.next());
            publishProgress("Reconsentimientos2015", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addConsentimientoZika(List<ConsentimientoZika> cons) throws Exception {

        int v = cons.size();

        ListIterator<ConsentimientoZika> iter = cons.listIterator();

        while (iter.hasNext()){
            ca.crearConsentimientoZika(iter.next());
            publishProgress("ConsentimientoZika", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }


    private void addReconsentimientosFlu2015(List<ReConsentimientoFlu2015> recons) throws Exception {

        int v = recons.size();

        ListIterator<ReConsentimientoFlu2015> iter = recons.listIterator();

        while (iter.hasNext()){
            ca.crearReConsentimientoFlu2015(iter.next());
            publishProgress("ReconsentimientosFlu2015", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addCodigosCasas(List<CodigosCasas> cca) throws Exception {

        int v = cca.size();

        ListIterator<CodigosCasas> iter = cca.listIterator();

        while (iter.hasNext()){
            ca.crearCodigosCasas(iter.next());
            publishProgress("CodigosCasas", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addRecepcionBHCs(List<RecepcionBHC> recbhcs) throws Exception {

        int v = recbhcs.size();

        ListIterator<RecepcionBHC> iter = recbhcs.listIterator();

        while (iter.hasNext()){
            ca.crearRecepcionBHC(iter.next());
            publishProgress("Recepcion BHC", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }


    private void addRecepcionSeros(List<RecepcionSero> recseros) throws Exception {

        int v = recseros.size();

        ListIterator<RecepcionSero> iter = recseros.listIterator();

        while (iter.hasNext()){
            ca.crearRecepcionSero(iter.next());
            publishProgress("Recepcion Serologias", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }



    private void addPinchazos(List<Pinchazo> pinchazos) throws Exception {

        int v = pinchazos.size();

        ListIterator<Pinchazo> iter = pinchazos.listIterator();

        while (iter.hasNext()){
            ca.crearPinchazo(iter.next());
            publishProgress("Pinchazos", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addTempRojoBhcs(List<TempRojoBhc> temps) throws Exception {

        int v = temps.size();

        ListIterator<TempRojoBhc> iter = temps.listIterator();

        while (iter.hasNext()){
            ca.crearTempRojoBhc(iter.next());
            publishProgress("Temperaturas", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }



    private void addTempPbmcs(List<TempPbmc> temps) throws Exception {

        int v = temps.size();

        ListIterator<TempPbmc> iter = temps.listIterator();

        while (iter.hasNext()){
            ca.crearTempPbmc(iter.next());
            publishProgress("Temperaturas", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addEncuestaSatisfaccions(List<EncuestaSatisfaccion> encuestas) throws Exception {

        int v = encuestas.size();

        ListIterator<EncuestaSatisfaccion> iter = encuestas.listIterator();

        while (iter.hasNext()){
            ca.crearEncuestaSatisfaccion(iter.next());
            publishProgress("Encuestas Satisfaccion", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    // url, username, password
    protected String descargarEncCasas() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/encuestascasas";

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

            // Perform the HTTP GET request
            ResponseEntity<EncuestaCasa[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaCasa[].class);

            // convert the array to a list and return it
            mEncuestasCasas = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarEncParticipantes() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/encuestasparticipantes";

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

            // Perform the HTTP GET request
            ResponseEntity<EncuestaParticipante[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaParticipante[].class);

            // convert the array to a list and return it
            mEncuestasParticipantes = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarEncLactancia() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/lactmaterna";

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

            // Perform the HTTP GET request
            ResponseEntity<LactanciaMaterna[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    LactanciaMaterna[].class);

            // convert the array to a list and return it
            mEncuestasLactancias = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarPyTs() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/pts";

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

            // Perform the HTTP GET request
            ResponseEntity<PesoyTalla[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    PesoyTalla[].class);

            // convert the array to a list and return it
            mPyTs = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarMuestras() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/muestrasMA";

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

            // Perform the HTTP GET request
            ResponseEntity<Muestra[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Muestra[].class);

            // convert the array to a list and return it
            mMuestras = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarObsequios() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/obsequios";

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

            // Perform the HTTP GET request
            ResponseEntity<Obsequio[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Obsequio[].class);

            // convert the array to a list and return it
            mObsequios = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarVacunas() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/vacunas";

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

            // Perform the HTTP GET request
            ResponseEntity<Vacuna[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Vacuna[].class);

            // convert the array to a list and return it
            mVacunas = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    // url, username, password
    protected String descargarNewVacunas() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/newvacunas";

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

            // Perform the HTTP GET request
            ResponseEntity<NewVacuna[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    NewVacuna[].class);

            // convert the array to a list and return it
            mNewVacunas = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    // url, username, password
    protected String descargarDatosPartoBB() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/datospartobb";

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

            // Perform the HTTP GET request
            ResponseEntity<DatosPartoBB[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    DatosPartoBB[].class);

            // convert the array to a list and return it
            mDatosPartoBBs = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }




    // url, username, password
    protected String descargarVisitas() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/visitasMA";

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

            // Perform the HTTP GET request
            ResponseEntity<VisitaTerreno[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    VisitaTerreno[].class);

            // convert the array to a list and return it
            mVisitasTerreno = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    // url, username, password
    protected String descargarDatosVisitasTerreno() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/visitasn";

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

            // Perform the HTTP GET request
            ResponseEntity<DatosVisitaTerreno[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    DatosVisitaTerreno[].class);

            // convert the array to a list and return it
            mDatosVisitasTerreno = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    // url, username, password
    protected String descargarReconsentimientos() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/recons";

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

            // Perform the HTTP GET request
            ResponseEntity<ReConsentimientoDen[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ReConsentimientoDen[].class);

            // convert the array to a list and return it
            mReconsentimientos = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    // url, username, password
    protected String descargarReconsentimientos2015() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/recons2015";

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

            // Perform the HTTP GET request
            ResponseEntity<ReConsentimientoDen2015[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ReConsentimientoDen2015[].class);

            // convert the array to a list and return it
            mReconsentimientos2015 = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    // url, username, password
    protected String descargarConsentimientosZika() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/conszika";

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

            // Perform the HTTP GET request
            ResponseEntity<ConsentimientoZika[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ConsentimientoZika[].class);

            // convert the array to a list and return it
            mConsentimientosZika = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    // url, username, password
    protected String descargarReconsentimientosFlu2015() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/reconsflu2015";

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

            // Perform the HTTP GET request
            ResponseEntity<ReConsentimientoFlu2015[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ReConsentimientoFlu2015[].class);

            // convert the array to a list and return it
            mReconsentimientosFlu2015 = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    // url, username, password
    protected String descargarCodigosCasas() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/codigoscasas";

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

            // Perform the HTTP GET request
            ResponseEntity<CodigosCasas[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    CodigosCasas[].class);

            // convert the array to a list and return it
            mCodigosCasas = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    // url, username, password
    protected String descargarRecepcionBHCs() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/bhcs";

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

            // Perform the HTTP GET request
            ResponseEntity<RecepcionBHC[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    RecepcionBHC[].class);

            // convert the array to a list and return it
            mRecepcionBHCs = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }




    // url, username, password
    protected String descargarRecepcionSeros() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/seros";

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

            // Perform the HTTP GET request
            ResponseEntity<RecepcionSero[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    RecepcionSero[].class);

            // convert the array to a list and return it
            mRecepcionSeros = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    // url, username, password
    protected String descargarPinchazos() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/pins";

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

            // Perform the HTTP GET request
            ResponseEntity<Pinchazo[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Pinchazo[].class);

            // convert the array to a list and return it
            mPinchazos = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }


    // url, username, password
    protected String descargarTempRojoBhcs() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/trojos";

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

            // Perform the HTTP GET request
            ResponseEntity<TempRojoBhc[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    TempRojoBhc[].class);

            // convert the array to a list and return it
            mTempRojoBhcs = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }



    // url, username, password
    protected String descargarTempPbmcs() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/tpbmcs";

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

            // Perform the HTTP GET request
            ResponseEntity<TempPbmc[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    TempPbmc[].class);

            // convert the array to a list and return it
            mTempPbmcs = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarEncuestaSatisfaccions() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/encuestassatisfaccion";

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

            // Perform the HTTP GET request
            ResponseEntity<EncuestaSatisfaccion[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaSatisfaccion[].class);

            // convert the array to a list and return it
            mEncuestaSatisfaccions = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
