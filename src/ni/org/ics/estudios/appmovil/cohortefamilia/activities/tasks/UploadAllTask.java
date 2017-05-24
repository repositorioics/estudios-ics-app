package ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks;

import java.util.ArrayList;
import java.util.List;


import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.CartaConsentimiento;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.Tamizaje;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.*;
import ni.org.ics.estudios.appmovil.listeners.UploadListener;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import ni.org.ics.estudios.appmovil.utils.MuestrasDBConstants;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.util.Log;

public class UploadAllTask extends UploadTask {
	
	private final Context mContext;

	public UploadAllTask(Context context) {
		mContext = context;
	}

	protected static final String TAG = UploadAllTask.class.getSimpleName();
    
	private EstudiosAdapter estudioAdapter = null;
    private List<PreTamizaje> mPreTamizajes = new ArrayList<PreTamizaje>();
	private List<CasaCohorteFamilia> mCasasCHF = new ArrayList<CasaCohorteFamilia>();
    private List<Tamizaje> mTamizajes = new ArrayList<Tamizaje>();
    private List<Participante> mParticipantes = new ArrayList<Participante>();
    private List<ParticipanteCohorteFamilia> mParticipantesCHF = new ArrayList<ParticipanteCohorteFamilia>();
    private List<CartaConsentimiento> mCartasConsent = new ArrayList<CartaConsentimiento>();
    private List<EncuestaCasa> mEncuestasCasas = new ArrayList<EncuestaCasa>();
    private List<Cocina> mCocinas = new ArrayList<Cocina>();
    private List<Comedor> mComedores = new ArrayList<Comedor>();
    private List<Sala> mSalas = new ArrayList<Sala>();
    private List<Habitacion> mHabitaciones = new ArrayList<Habitacion>();
    private List<Banio> mBanios = new ArrayList<Banio>();
    private List<Ventana> mVentanas = new ArrayList<Ventana>();
    private List<Cama> mCamas = new ArrayList<Cama>();
    private List<PersonaCama> mPersonaCamas = new ArrayList<PersonaCama>();
    private List<EncuestaParticipante> mEncuestasParticipante = new ArrayList<EncuestaParticipante>();
    private List<EncuestaDatosPartoBB> mEncuestasDatosPartoBB = new ArrayList<EncuestaDatosPartoBB>();
    private List<EncuestaPesoTalla> mEncuestasPesoTalla = new ArrayList<EncuestaPesoTalla>();
    private List<EncuestaLactanciaMaterna> mEncuestasLacMat = new ArrayList<EncuestaLactanciaMaterna>();
    private List<Muestra> mMuestras = new ArrayList<Muestra>();
    private List<Paxgene> mPaxgenes = new ArrayList<Paxgene>();

	private String url = null;
	private String username = null;
	private String password = null;
	private String error = null;
	protected UploadListener mStateListener;

    public static final String PRETAMIZAJE = "1";
    public static final String CASACHF = "2";
    public static final String TAMIZAJE = "3";
    public static final String PARTICIPANTE = "4";
    public static final String PARTICIPANTECHF = "5";
    public static final String CARTAS_CONSENT = "6";
    public static final String ENCUESTA_CASACHF = "7";
    public static final String COCINA = "8";
    public static final String COMEDOR = "9";
    public static final String SALA = "10";
    public static final String HABITACION = "11";
    public static final String BANIOS = "12";
    public static final String VENTANAS = "13";
    public static final String CAMAS = "14";
    public static final String PERSONAS_CAMA = "15";
    public static final String ENCUESTA_PARTICIPANTECHF = "16";
    public static final String ENCUESTA_DATOSPBB = "17";
    public static final String ENCUESTA_PESOTALLA = "18";
    public static final String ENCUESTA_LACTMAT = "19";
    public static final String MUESTRAS = "20";
    public static final String MUESTRAS_PAXGENE = "21";
	private static final String TOTAL_TASK = "21";
	

	@Override
	protected String doInBackground(String... values) {
		url = values[0];
		username = values[1];
		password = values[2];

		try {
			publishProgress("Obteniendo registros de la base de datos", "1", "2");
			estudioAdapter = new EstudiosAdapter(mContext, password, false,false);
			estudioAdapter.open();
            String filtro = MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'";
            /*mPreTamizajes = estudioAdapter.getPreTamizajes(filtro, MainDBConstants.codigo);
			mCasasCHF = estudioAdapter.getCasaCohorteFamilias(filtro, MainDBConstants.codigoCHF);
            mTamizajes = estudioAdapter.getTamizajes(filtro, MainDBConstants.codigo);
            mParticipantes = estudioAdapter.getParticipantes(filtro, MainDBConstants.codigo);
            mParticipantesCHF = estudioAdapter.getParticipanteCohorteFamilias(filtro, null);
            mCartasConsent = estudioAdapter.getCartasConsentimientos(filtro, MainDBConstants.codigo);
            mEncuestasCasas = estudioAdapter.getEncuestaCasas(filtro, null);
            */mPreTamizajes = estudioAdapter.getPreTamizajes(null, MainDBConstants.codigo);
            mCasasCHF = estudioAdapter.getCasaCohorteFamilias(null, MainDBConstants.codigoCHF);
            mTamizajes = estudioAdapter.getTamizajes(null, MainDBConstants.codigo);
            mParticipantes = estudioAdapter.getParticipantes(filtro, MainDBConstants.codigo);
            mParticipantesCHF = estudioAdapter.getParticipanteCohorteFamilias(null, null);
            mCartasConsent = estudioAdapter.getCartasConsentimientos(null, MainDBConstants.codigo);
            mEncuestasCasas = estudioAdapter.getEncuestaCasas(null, null);
            actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PRETAMIZAJE);
            actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CASACHF);
            actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, TAMIZAJE);
            actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PARTICIPANTECHF);
            actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CARTAS_CONSENT);
            actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENCUESTA_CASACHF);
            //mCocinas = estudioAdapter.get(filtro, MainDBConstants.codigo);
            //mComedores = estudioAdapter.get(filtro, MainDBConstants.codigo);
            //mSalas = estudioAdapter.get(filtro, MainDBConstants.codigo);
            mHabitaciones = estudioAdapter.getHabitaciones(filtro, null);
            //mBanios = estudioAdapter.get(filtro, MainDBConstants.codigo);
            //mVentanas = estudioAdapter.get(filtro, MainDBConstants.codigo);
            mCamas = estudioAdapter.getCamas(filtro, null);
            //mPersonaCamas = estudioAdapter.get(filtro, MainDBConstants.codigo);
            mEncuestasParticipante = estudioAdapter.getEncuestasParticipantes(filtro, null);
            mEncuestasDatosPartoBB = estudioAdapter.getEncuestasDatosPartoBBs(filtro, null);
            mEncuestasPesoTalla = estudioAdapter.getEncuestasPesoTallas(filtro, null);
            mEncuestasLacMat = estudioAdapter.getEncuestasLactanciaMaternas(filtro, null);
            mMuestras = estudioAdapter.getMuestras(filtro, null);
            //mPaxgenes = estudioAdapter.getPaxgenes(filtro, null);

			publishProgress("Datos completos!", "2", "2");
			actualizarBaseDatos(Constants.STATUS_SUBMITTED, PRETAMIZAJE);
			error = cargarPretamizajes(url, username, password);
			if (!error.matches("Datos recibidos!")){
				actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PRETAMIZAJE);
				return error;
			}
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, CASACHF);
            error = cargarCasasCHF(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CASACHF);
                return error;
            }
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
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, PARTICIPANTECHF);
            error = cargarParticipantesCHF(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PARTICIPANTECHF);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, CARTAS_CONSENT);
            error = cargarCartasConsentimientos(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CARTAS_CONSENT);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENCUESTA_CASACHF);
            error = cargarEncuestasCasas(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENCUESTA_CASACHF);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, COCINA);
            error = cargarCocinas(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, COCINA);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, COMEDOR);
            error = cargarComedores(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, COMEDOR);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, SALA);
            error = cargarSalas(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, SALA);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, HABITACION);
            error = cargarHabitaciones(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, HABITACION);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, BANIOS);
            error = cargarBanios(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, BANIOS);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, VENTANAS);
            error = cargarVentanas(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, VENTANAS);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, CAMAS);
            error = cargarCamas(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CAMAS);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, PERSONAS_CAMA);
            error = cargarPersonasCamas(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PERSONAS_CAMA);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENCUESTA_PARTICIPANTECHF);
            error = cargarEncuestasParticipantes(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENCUESTA_PARTICIPANTECHF);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENCUESTA_DATOSPBB);
            error = cargarEncuestasDatosPartoBB(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENCUESTA_DATOSPBB);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENCUESTA_PESOTALLA);
            error = cargarEncuestasPesoTalla(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENCUESTA_PESOTALLA);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENCUESTA_LACTMAT);
            error = cargarEncuestasLactancia(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENCUESTA_LACTMAT);
                return error;
            }
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, MUESTRAS);
            error = cargarMuestras(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, MUESTRAS);
                return error;
            }
            /*
            actualizarBaseDatos(Constants.STATUS_SUBMITTED, MUESTRAS_PAXGENE);
            error = cargarPaxgene(url, username, password);
            if (!error.matches("Datos recibidos!")){
                actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, MUESTRAS_PAXGENE);
                return error;
            }*/
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
        if(opcion.equalsIgnoreCase(PRETAMIZAJE)){
            c = mPreTamizajes.size();
            if(c>0){
                for (PreTamizaje preTamizaje : mPreTamizajes) {
                    preTamizaje.setEstado(estado.charAt(0));
                    estudioAdapter.editarPreTamizaje(preTamizaje);
                    publishProgress("Actualizando pretamizajes en base de datos local", Integer.valueOf(mPreTamizajes.indexOf(preTamizaje)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(CASACHF)){
			c = mCasasCHF.size();
			if(c>0){
				for (CasaCohorteFamilia casa : mCasasCHF) {
					casa.setEstado(estado.charAt(0));
					estudioAdapter.editarCasaCohorteFamilia(casa);
					publishProgress("Actualizando casas cohorte familia en base de datos local", Integer.valueOf(mCasasCHF.indexOf(casa)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
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
        if(opcion.equalsIgnoreCase(PARTICIPANTECHF)){
            c = mParticipantesCHF.size();
            if(c>0){
                for (ParticipanteCohorteFamilia participante : mParticipantesCHF) {
                    participante.setEstado(estado.charAt(0));
                    estudioAdapter.editarParticipanteCohorteFamilia(participante);
                    publishProgress("Actualizando participantes cohorte familia en base de datos local", Integer.valueOf(mParticipantesCHF.indexOf(participante)).toString(), Integer
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
        if(opcion.equalsIgnoreCase(ENCUESTA_CASACHF)){
            c = mEncuestasCasas.size();
            if(c>0){
                for (EncuestaCasa encuestaCasa : mEncuestasCasas) {
                    encuestaCasa.setEstado(estado.charAt(0));
                    estudioAdapter.editarEncuestaCasa(encuestaCasa);
                    publishProgress("Actualizando encuestas de casas en base de datos local", Integer.valueOf(mEncuestasCasas.indexOf(encuestaCasa)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        //TODO: ACTUALIZAR ESTADO DEL OBJETO
        if(opcion.equalsIgnoreCase(COCINA)){
            c = mCocinas.size();
            if(c>0){
                for (Cocina cocina : mCocinas) {
                    cocina.setEstado(estado.charAt(0));
                    //estudioAdapter.editarCocina(cocina);
                    publishProgress("Actualizando cocinas en base de datos local", Integer.valueOf(mCocinas.indexOf(cocina)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        //TODO: ACTUALIZAR ESTADO DEL OBJETO
        if(opcion.equalsIgnoreCase(COMEDOR)){
            c = mComedores.size();
            if(c>0){
                for (Comedor comedor : mComedores) {
                    comedor.setEstado(estado.charAt(0));
                    //estudioAdapter.editarCocina(comedor);
                    publishProgress("Actualizando comedores en base de datos local", Integer.valueOf(mComedores.indexOf(comedor)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        //TODO: ACTUALIZAR ESTADO DEL OBJETO
        if(opcion.equalsIgnoreCase(SALA)){
            c = mSalas.size();
            if(c>0){
                for (Sala sala : mSalas) {
                    sala.setEstado(estado.charAt(0));
                    //estudioAdapter.editarCocina(sala);
                    publishProgress("Actualizando salas en base de datos local", Integer.valueOf(mSalas.indexOf(sala)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(HABITACION)){
            c = mHabitaciones.size();
            if(c>0){
                for (Habitacion habitacion : mHabitaciones) {
                    habitacion.setEstado(estado.charAt(0));
                    estudioAdapter.editarHabitacion(habitacion);
                    publishProgress("Actualizando habitaciones en base de datos local", Integer.valueOf(mHabitaciones.indexOf(habitacion)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        //TODO: ACTUALIZAR ESTADO DEL OBJETO
        if(opcion.equalsIgnoreCase(BANIOS)){
            c = mBanios.size();
            if(c>0){
                for (Banio banio : mBanios) {
                    banio.setEstado(estado.charAt(0));
                    //estudioAdapter.editarCocina(banio);
                    publishProgress("Actualizando baños en base de datos local", Integer.valueOf(mBanios.indexOf(banio)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        //TODO: ACTUALIZAR ESTADO DEL OBJETO
        if(opcion.equalsIgnoreCase(VENTANAS)){
            c = mVentanas.size();
            if(c>0){
                for (Ventana ventana : mVentanas) {
                    ventana.setEstado(estado.charAt(0));
                    //estudioAdapter.editarCocina(ventana);
                    publishProgress("Actualizando ventanas en base de datos local", Integer.valueOf(mVentanas.indexOf(ventana)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(CAMAS)){
            c = mCamas.size();
            if(c>0){
                for (Cama cama : mCamas) {
                    cama.setEstado(estado.charAt(0));
                    estudioAdapter.editarCama(cama);
                    publishProgress("Actualizando camas en base de datos local", Integer.valueOf(mCamas.indexOf(cama)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        //TODO: ACTUALIZAR ESTADO DEL OBJETO
        if(opcion.equalsIgnoreCase(PERSONAS_CAMA)){
            c = mPersonaCamas.size();
            if(c>0){
                for (PersonaCama personaCama : mPersonaCamas) {
                    personaCama.setEstado(estado.charAt(0));
                    //estudioAdapter.editarPersonaCama(personaCama);
                    publishProgress("Actualizando personas camas en base de datos local", Integer.valueOf(mPersonaCamas.indexOf(personaCama)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENCUESTA_PARTICIPANTECHF)){
            c = mEncuestasParticipante.size();
            if(c>0){
                for (EncuestaParticipante encuestaParticipante : mEncuestasParticipante) {
                    encuestaParticipante.setEstado(estado.charAt(0));
                    estudioAdapter.editarEncuestasParticipante(encuestaParticipante);
                    publishProgress("Actualizando encuestas de participantes en base de datos local", Integer.valueOf(mEncuestasParticipante.indexOf(encuestaParticipante)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENCUESTA_DATOSPBB)){
            c = mEncuestasDatosPartoBB.size();
            if(c>0){
                for (EncuestaDatosPartoBB encuestaDatosPartoBB : mEncuestasDatosPartoBB) {
                    encuestaDatosPartoBB.setEstado(estado.charAt(0));
                    estudioAdapter.editarEncuestasDatosPartoBB(encuestaDatosPartoBB);
                    publishProgress("Actualizando encuestas de datos del partos en base de datos local", Integer.valueOf(mEncuestasDatosPartoBB.indexOf(encuestaDatosPartoBB)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENCUESTA_PESOTALLA)){
            c = mEncuestasPesoTalla.size();
            if(c>0){
                for (EncuestaPesoTalla encuestaPesoTalla : mEncuestasPesoTalla) {
                    encuestaPesoTalla.setEstado(estado.charAt(0));
                    estudioAdapter.editarEncuestasPesoTalla(encuestaPesoTalla);
                    publishProgress("Actualizando encuestas de peso y talla en base de datos local", Integer.valueOf(mEncuestasPesoTalla.indexOf(encuestaPesoTalla)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENCUESTA_LACTMAT)){
            c = mEncuestasLacMat.size();
            if(c>0){
                for (EncuestaLactanciaMaterna encuestaLactanciaMat : mEncuestasLacMat) {
                    encuestaLactanciaMat.setEstado(estado.charAt(0));
                    estudioAdapter.editarEncuestasLactanciaMaterna(encuestaLactanciaMat);
                    publishProgress("Actualizando encuestas lactancia materna en base de datos local", Integer.valueOf(mEncuestasLacMat.indexOf(encuestaLactanciaMat)).toString(), Integer
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
                    publishProgress("Actualizando muestras en base de datos local", Integer.valueOf(mMuestras.indexOf(muestra)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(MUESTRAS_PAXGENE)){
            c = mPaxgenes.size();
            if(c>0){
                for (Paxgene paxgene : mPaxgenes) {
                    paxgene.setEstado(estado.charAt(0));
                    estudioAdapter.editarPaxgenes(paxgene);
                    publishProgress("Actualizando datos de PaxGene en base de datos local", Integer.valueOf(mPaxgenes.indexOf(paxgene)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }

	}

    /***************************************************/
    /********************* Pretamizajes ************************/
    /***************************************************/
    // url, username, password
    protected String cargarPretamizajes(String url, String username,
                                    String password) throws Exception {
        try {
            if(mPreTamizajes.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando pretamizaje de casas cohorte familia!", PRETAMIZAJE, TOTAL_TASK);
                final String urlRequest = url + "/movil/preTamizajes";
                PreTamizaje[] envio = mPreTamizajes.toArray(new PreTamizaje[mPreTamizajes.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<PreTamizaje[]> requestEntity =
                        new HttpEntity<PreTamizaje[]>(envio, requestHeaders);
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
	/********************* Casa CHF ************************/
	/***************************************************/
    // url, username, password
    protected String cargarCasasCHF(String url, String username,
    		String password) throws Exception {
    	try {
    		if(mCasasCHF.size()>0){
    			// La URL de la solicitud POST
    			publishProgress("Enviando casas cohorte familia!", CASACHF, TOTAL_TASK);
    			final String urlRequest = url + "/movil/casasCHF";
    			CasaCohorteFamilia[] envio = mCasasCHF.toArray(new CasaCohorteFamilia[mCasasCHF.size()]);
    			HttpHeaders requestHeaders = new HttpHeaders();
    			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
    			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    			requestHeaders.setAuthorization(authHeader);
    			HttpEntity<CasaCohorteFamilia[]> requestEntity =
    					new HttpEntity<CasaCohorteFamilia[]>(envio, requestHeaders);
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
                publishProgress("Enviando tamizaje de personas cohorte familia!", TAMIZAJE, TOTAL_TASK);
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
                publishProgress("Enviando participantes!", PARTICIPANTE, TOTAL_TASK);
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
    /********************* Participantes chf ************************/
    /***************************************************/
    // url, username, password
    protected String cargarParticipantesCHF(String url, String username,
                                         String password) throws Exception {
        try {
            if(mParticipantesCHF.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando participantes cohorte familia!", PARTICIPANTECHF, TOTAL_TASK);
                final String urlRequest = url + "/movil/participantesCHF";
                ParticipanteCohorteFamilia[] envio = mParticipantesCHF.toArray(new ParticipanteCohorteFamilia[mParticipantesCHF.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ParticipanteCohorteFamilia[]> requestEntity =
                        new HttpEntity<ParticipanteCohorteFamilia[]>(envio, requestHeaders);
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
                publishProgress("Enviando cartas de consentimiento!", CARTAS_CONSENT, TOTAL_TASK);
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
    /********************* Encuestas casa CHF ************************/
    /***************************************************/
    // url, username, password
    protected String cargarEncuestasCasas(String url, String username,
                                                 String password) throws Exception {
        try {
            if(mEncuestasCasas.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando encuestas de casas!", ENCUESTA_CASACHF, TOTAL_TASK);
                final String urlRequest = url + "/movil/encuestasCasa";
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
    /********************* Areas Ambientes Cocinas ************************/
    /***************************************************/
    // url, username, password
    protected String cargarCocinas(String url, String username,
                                          String password) throws Exception {
        try {
            if(mCocinas.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando cocinas!", COCINA, TOTAL_TASK);
                final String urlRequest = url + "/movil/cocinas";
                Cocina[] envio = mCocinas.toArray(new Cocina[mCocinas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Cocina[]> requestEntity =
                        new HttpEntity<Cocina[]>(envio, requestHeaders);
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
    /********************* Areas Ambientes Comedores ************************/
    /***************************************************/
    // url, username, password
    protected String cargarComedores(String url, String username,
                                   String password) throws Exception {
        try {
            if(mComedores.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando comedores!", COMEDOR, TOTAL_TASK);
                final String urlRequest = url + "/movil/comedores";
                Comedor[] envio = mComedores.toArray(new Comedor[mComedores.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Comedor[]> requestEntity =
                        new HttpEntity<Comedor[]>(envio, requestHeaders);
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
    /********************* Areas Ambientes Salas ************************/
    /***************************************************/
    // url, username, password
    protected String cargarSalas(String url, String username,
                                     String password) throws Exception {
        try {
            if(mSalas.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando salas!", SALA, TOTAL_TASK);
                final String urlRequest = url + "/movil/salas";
                Sala[] envio = mSalas.toArray(new Sala[mSalas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Sala[]> requestEntity =
                        new HttpEntity<Sala[]>(envio, requestHeaders);
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
    /********************* Areas Ambientes habitaciones ************************/
    /***************************************************/
    // url, username, password
    protected String cargarHabitaciones(String url, String username,
                                 String password) throws Exception {
        try {
            if(mHabitaciones.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando habitaciones!", HABITACION, TOTAL_TASK);
                final String urlRequest = url + "/movil/habitaciones";
                Habitacion[] envio = mHabitaciones.toArray(new Habitacion[mHabitaciones.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Habitacion[]> requestEntity =
                        new HttpEntity<Habitacion[]>(envio, requestHeaders);
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
    /********************* Areas Ambientes banios ************************/
    /***************************************************/
    // url, username, password
    protected String cargarBanios(String url, String username,
                                        String password) throws Exception {
        try {
            if(mBanios.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando baños!", BANIOS, TOTAL_TASK);
                final String urlRequest = url + "/movil/banios";
                Banio[] envio = mBanios.toArray(new Banio[mBanios.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Banio[]> requestEntity =
                        new HttpEntity<Banio[]>(envio, requestHeaders);
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
    /********************* Areas Ambientes ventanas ************************/
    /***************************************************/
    // url, username, password
    protected String cargarVentanas(String url, String username,
                                  String password) throws Exception {
        try {
            if(mVentanas.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando ventanas!", VENTANAS, TOTAL_TASK);
                final String urlRequest = url + "/movil/ventanas";
                Ventana[] envio = mVentanas.toArray(new Ventana[mVentanas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Ventana[]> requestEntity =
                        new HttpEntity<Ventana[]>(envio, requestHeaders);
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
    /********************* Camas ************************/
    /***************************************************/
    // url, username, password
    protected String cargarCamas(String url, String username,
                                    String password) throws Exception {
        try {
            if(mCamas.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando camas!", CAMAS, TOTAL_TASK);
                final String urlRequest = url + "/movil/camas";
                Cama[] envio = mCamas.toArray(new Cama[mCamas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Cama[]> requestEntity =
                        new HttpEntity<Cama[]>(envio, requestHeaders);
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
    /********************* Personas Camas ************************/
    /***************************************************/
    // url, username, password
    protected String cargarPersonasCamas(String url, String username,
                                 String password) throws Exception {
        try {
            if(mPersonaCamas.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando personas camas!", PERSONAS_CAMA, TOTAL_TASK);
                final String urlRequest = url + "/movil/personasCamas";
                PersonaCama[] envio = mPersonaCamas.toArray(new PersonaCama[mPersonaCamas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<PersonaCama[]> requestEntity =
                        new HttpEntity<PersonaCama[]>(envio, requestHeaders);
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
    /********************* Encuestas Participantes ************************/
    /***************************************************/
    // url, username, password
    protected String cargarEncuestasParticipantes(String url, String username,
                                         String password) throws Exception {
        try {
            if(mEncuestasParticipante.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando encuestas participantes!", ENCUESTA_PARTICIPANTECHF, TOTAL_TASK);
                final String urlRequest = url + "/movil/encuestasParticipante";
                EncuestaParticipante[] envio = mEncuestasParticipante.toArray(new EncuestaParticipante[mEncuestasParticipante.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaParticipante[]> requestEntity =
                        new HttpEntity<EncuestaParticipante[]>(envio, requestHeaders);
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
    /********************* Encuestas Datos Parto bb ************************/
    /***************************************************/
    // url, username, password
    protected String cargarEncuestasDatosPartoBB(String url, String username,
                                                  String password) throws Exception {
        try {
            if(mEncuestasDatosPartoBB.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando encuestas datos partos BB!", ENCUESTA_DATOSPBB, TOTAL_TASK);
                final String urlRequest = url + "/movil/encuestasDatosPartoBB";
                EncuestaDatosPartoBB[] envio = mEncuestasDatosPartoBB.toArray(new EncuestaDatosPartoBB[mEncuestasDatosPartoBB.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaDatosPartoBB[]> requestEntity =
                        new HttpEntity<EncuestaDatosPartoBB[]>(envio, requestHeaders);
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
    /********************* Encuestas Peso y talla ************************/
    /***************************************************/
    // url, username, password
    protected String cargarEncuestasPesoTalla(String url, String username,
                                                 String password) throws Exception {
        try {
            if(mEncuestasPesoTalla.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando encuestas de peso y talla!", ENCUESTA_PESOTALLA, TOTAL_TASK);
                final String urlRequest = url + "/movil/encuestasPesoTalla";
                EncuestaPesoTalla[] envio = mEncuestasPesoTalla.toArray(new EncuestaPesoTalla[mEncuestasPesoTalla.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaPesoTalla[]> requestEntity =
                        new HttpEntity<EncuestaPesoTalla[]>(envio, requestHeaders);
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
    /********************* Encuestas lactancia materna ************************/
    /***************************************************/
    // url, username, password
    protected String cargarEncuestasLactancia(String url, String username,
                                              String password) throws Exception {
        try {
            if(mEncuestasLacMat.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando encuestas de lactancia materna!", ENCUESTA_LACTMAT, TOTAL_TASK);
                final String urlRequest = url + "/movil/encuestasLactanciaMaterna";
                EncuestaLactanciaMaterna[] envio = mEncuestasLacMat.toArray(new EncuestaLactanciaMaterna[mEncuestasLacMat.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaLactanciaMaterna[]> requestEntity =
                        new HttpEntity<EncuestaLactanciaMaterna[]>(envio, requestHeaders);
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
                publishProgress("Enviando muestras!", MUESTRAS, TOTAL_TASK);
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
    /********************* Muestras Paxgene ************************/
    /***************************************************/
    // url, username, password
    protected String cargarPaxgene(String url, String username,
                                    String password) throws Exception {
        try {
            if(mPaxgenes.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando datos de PaxGene!", MUESTRAS_PAXGENE, TOTAL_TASK);
                final String urlRequest = url + "/movil/paxgene";
                Paxgene[] envio = mPaxgenes.toArray(new Paxgene[mPaxgenes.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Paxgene[]> requestEntity =
                        new HttpEntity<Paxgene[]>(envio, requestHeaders);
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