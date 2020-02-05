package ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.*;

import ni.org.ics.estudios.appmovil.domain.cohortefamilia.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.*;
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



public class DownloadAllTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadAllTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadAllTask.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;
	private List<Estudio> mEstudios = null;
	//private List<Barrio> mBarrios = null;
	//private List<Casa> mCasas = null;
	//private List<Participante> mParticipantes = null;
	
    //private List<CasaCohorteFamilia> mCasasCHF = null;
    //private List<ParticipanteCohorteFamilia> mParticipantesCHF = null;
    private List<EncuestaCasa> mEncuestasCasas = null;
    private List<Cocina> mCocinas = null;
    private List<Comedor> mComedores = null;
    private List<Sala> mSalas = null;
    private List<Habitacion> mHabitaciones = null;
    private List<Banio> mBanios = null;
    private List<Ventana> mVentanas = null;
    private List<Cuarto> mCuartos = null;
    private List<Cama> mCamas = null;
    private List<PersonaCama> mPersonaCamas = null;
    private List<EncuestaParticipante> mEncuestasParticipante = null;
    private List<EncuestaDatosPartoBB> mEncuestasDatosPartoBB = null;
    private List<EncuestaPesoTalla> mEncuestasPesoTalla = null;
    private List<EncuestaLactanciaMaterna> mEncuestasLacMat = null;
    private List<Muestra> mMuestras = null;
    private List<TelefonoContacto> mTelefonos = null;
    
    //private List<ParticipanteSeroprevalencia> mParticipantesSA = null;
    //MA2020 private List<EncuestaCasaSA> mEncuestasCasaSA = null;
    //MA2020 private List<EncuestaParticipanteSA> mEncuestasParticipanteSA = null;
    
    
    private List<CasaCohorteFamiliaCaso> mCasaCohorteFamiliaCasos = null;
    private List<ParticipanteCohorteFamiliaCaso> mParticipanteCohorteFamiliaCasos = null;
    private List<VisitaSeguimientoCaso> mVisitaSeguimientoCasos = null;
    private List<VisitaFallidaCaso> mVisitaFallidaCasos = null;
    private List<VisitaSeguimientoCasoSintomas> mVisitaSeguimientoSintomasCasos = null;
    private List<FormularioContactoCaso> mFormularioContactoCasos = null;
    private List<InformacionNoCompletaCaso> mInformacionNoCompletaCasos = null;
    private List<VisitaFinalCaso> mVisitaFinalCasos = null;
	
	public static final String ESTUDIO = "1";
	public static final String BARRIO = "2";
	public static final String CASA = "3";
	public static final String PARTICIPANTE = "4";
	public static final String CASACHF = "1";
    public static final String PARTICIPANTECHF = "2";
    public static final String ENCUESTA_CASACHF = "3";
    public static final String COCINA = "4";
    public static final String COMEDOR = "5";
    public static final String SALA = "6";
    public static final String HABITACION = "7";
    public static final String BANIOS = "8";
    public static final String VENTANAS = "9";
    public static final String CUARTOS = "10";
    public static final String CAMAS = "11";
    public static final String PERSONAS_CAMA = "12";
    public static final String ENCUESTA_PARTICIPANTECHF = "1";
    public static final String ENCUESTA_DATOSPBB = "2";
    public static final String ENCUESTA_PESOTALLA = "3";
    public static final String ENCUESTA_LACTMAT = "4";
    public static final String MUESTRAS = "5";
    public static final String TELEFONOS = "6";
    public static final String PARTICIPANTESA = "1";
    public static final String ENCUESTA_CASASA = "2";
    public static final String ENCUESTA_PARTICIPANTESA = "3";
    
    
    public static final String CASAS_CASOS = "1";
    public static final String PART_CASOS = "2";
    public static final String VISITAS_CASOS = "3";
    public static final String VISITAS_FALLIDAS_CASOS = "4";
    public static final String SINTOMAS_CASOS = "5";
    public static final String CONTACTOS_CASOS = "6";
    public static final String NODATA_CASOS = "7";
    public static final String VISITAS_FINALES = "8";

    private static final String TOTAL_TASK_GENERALES = "4";
    private static final String TOTAL_TASK_RECLUTAMIENTO = "12";
    private static final String TOTAL_TASK_RECLUTAMIENTO_CHF = "6";
    private static final String TOTAL_TASK_SERO = "3";
    private static final String TOTAL_TASK_CASOS = "8";

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
		//estudioAdapter.borrarBarrios();
		//estudioAdapter.borrarCasas();
		//estudioAdapter.borrarParticipantes();
		try {
			if (mEstudios != null){
				v = mEstudios.size();
				ListIterator<Estudio> iter = mEstudios.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearEstudio(iter.next());
					publishProgress("Insertando estudios en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
				mEstudios = null;
			}
			/*if (mBarrios != null){
				v = mBarrios.size();
				ListIterator<Barrio> iter = mBarrios.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearBarrio(iter.next());
					publishProgress("Insertando barrios en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
				mBarrios = null;
			}
			if (mCasas != null){
				v = mCasas.size();
				ListIterator<Casa> iter = mCasas.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearCasa(iter.next());
					publishProgress("Insertando casas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
				mCasas = null;
			}
			if (mParticipantes != null){
				v = mParticipantes.size();
				ListIterator<Participante> iter = mParticipantes.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearParticipante(iter.next());
					publishProgress("Insertando participantes en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
				mParticipantes = null;
			}*/
            
		} catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			estudioAdapter.close();
			return e.getLocalizedMessage();
		}
		try {
			error = descargarDatosReclutamiento();
			if (error!=null) return error;
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		publishProgress("Abriendo base de datos...","1","1");
		//Borrar los datos de la base de datos
        estudioAdapter.borrarCasaCohorteFamilias();
        estudioAdapter.borrarParticipanteCohorteFamilias();
        estudioAdapter.borrarEncuestaCasas();
        estudioAdapter.borrarAreasAmbiente();
        estudioAdapter.borrarCuartos();
        estudioAdapter.borrarCamas();
        estudioAdapter.borrarPersonasCama();
        try {
            /*if (mCasasCHF != null){
                v = mCasasCHF.size();
                ListIterator<CasaCohorteFamilia> iter = mCasasCHF.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCasaCohorteFamilia(iter.next());
                    publishProgress("Insertando casas cohorte familia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mCasasCHF = null;
            }
            if (mParticipantesCHF != null){
                v = mParticipantesCHF.size();
                ListIterator<ParticipanteCohorteFamilia> iter = mParticipantesCHF.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearParticipanteCohorteFamilia(iter.next());
                    publishProgress("Insertando participantes cohorte familia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mParticipantesCHF = null;
            }*/
            if (mEncuestasCasas != null){
                v = mEncuestasCasas.size();
                ListIterator<EncuestaCasa> iter = mEncuestasCasas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestaCasa(iter.next());
                    publishProgress("Insertando encuestas de casas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mEncuestasCasas = null;
            }
            if (mCocinas != null){
                v = mCocinas.size();
                ListIterator<Cocina> iter = mCocinas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCocina(iter.next());
                    publishProgress("Insertando cocinas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mCocinas = null;
            }
            if (mComedores != null){
                v = mComedores.size();
                ListIterator<Comedor> iter = mComedores.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearComedor(iter.next());
                    publishProgress("Insertando comedores en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mComedores = null;
            }
            if (mSalas != null){
                v = mSalas.size();
                ListIterator<Sala> iter = mSalas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearSala(iter.next());
                    publishProgress("Insertando salas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mSalas = null;
            }
            if (mHabitaciones != null){
                v = mHabitaciones.size();
                ListIterator<Habitacion> iter = mHabitaciones.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearHabitacion(iter.next());
                    publishProgress("Insertando habitaciones en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mHabitaciones = null;
            }
            if (mBanios != null){
                v = mBanios.size();
                ListIterator<Banio> iter = mBanios.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearBanio(iter.next());
                    publishProgress("Insertando baños en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mBanios = null;
            }
            if (mVentanas != null){
                v = mVentanas.size();
                ListIterator<Ventana> iter = mVentanas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVentana(iter.next());
                    publishProgress("Insertando ventanas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVentanas = null;
            }
            if (mCuartos != null){
                v = mCuartos.size();
                ListIterator<Cuarto> iter = mCuartos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCuarto(iter.next());
                    publishProgress("Insertando cuartos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mCuartos = null;
            }
            if (mCamas != null){
                v = mCamas.size();
                ListIterator<Cama> iter = mCamas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCama(iter.next());
                    publishProgress("Insertando camas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mCamas = null;
            }
            if (mPersonaCamas != null){
                v = mPersonaCamas.size();
                ListIterator<PersonaCama> iter = mPersonaCamas.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearPersonaCama(iter.next());
                    publishProgress("Insertando asociación persona cama en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mPersonaCamas = null;
            }
		} catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			estudioAdapter.close();
			return e.getLocalizedMessage();
		}
        
        
        
        
        try {
			error = descargarDatosReclutamientoCHF();
			if (error!=null) return error;
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		publishProgress("Abriendo base de datos...","1","1");
		//Borrar los datos de la base de datos
        estudioAdapter.borrarEncuestasParticipantes();
        estudioAdapter.borrarEncuestasDatosPartoBBs();
        estudioAdapter.borrarEncuestasPesoTallas();
        estudioAdapter.borrarEncuestasLactanciaMaternas();
        estudioAdapter.borrarMuestras();
        estudioAdapter.borrarVisitasTerreno();
        estudioAdapter.borrarTelefonoContacto();
        try {
            if (mEncuestasParticipante != null){
                v = mEncuestasParticipante.size();
                ListIterator<EncuestaParticipante> iter = mEncuestasParticipante.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestasParticipante(iter.next());
                    publishProgress("Insertando encuestas de participantes en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mEncuestasParticipante = null;
            }
            if (mEncuestasDatosPartoBB != null){
                v = mEncuestasDatosPartoBB.size();
                ListIterator<EncuestaDatosPartoBB> iter = mEncuestasDatosPartoBB.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestasDatosPartoBB(iter.next());
                    publishProgress("Insertando encuestas de datos partos BB en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mEncuestasDatosPartoBB = null;
            }
            if (mEncuestasPesoTalla != null){
                v = mEncuestasPesoTalla.size();
                ListIterator<EncuestaPesoTalla> iter = mEncuestasPesoTalla.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestasPesoTalla(iter.next());
                    publishProgress("Insertando encuestas de peso y talla en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mEncuestasPesoTalla = null;
            }
            if (mEncuestasLacMat != null){
                v = mEncuestasLacMat.size();
                ListIterator<EncuestaLactanciaMaterna> iter = mEncuestasLacMat.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestasLactanciaMaterna(iter.next());
                    publishProgress("Insertando encuestas de lactancia materna en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mEncuestasLacMat = null;
            }
            if (mMuestras != null){
                v = mMuestras.size();
                ListIterator<Muestra> iter = mMuestras.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearMuestras(iter.next());
                    publishProgress("Insertando muestras en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mMuestras = null;
            }
            if (mTelefonos != null){
                v = mTelefonos.size();
                ListIterator<TelefonoContacto> iter = mTelefonos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearTelefonoContacto(iter.next());
                    publishProgress("Insertando telefonos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mTelefonos = null;
            }
            
		} catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			estudioAdapter.close();
			return e.getLocalizedMessage();
		}
        //MA2020
		/*try {
			error = descargarDatosSeroprevalencia();
			if (error!=null) return error;
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}*/
		publishProgress("Abriendo base de datos...","1","1");
		//Borrar los datos de la base de datos
        estudioAdapter.borrarParticipanteSeroprevalencia();
        estudioAdapter.borrarEncuestaCasaSA();
        estudioAdapter.borrarEncuestaParticipanteSA();
		try {
            /*if (mParticipantesSA != null){
                v = mParticipantesSA.size();
                ListIterator<ParticipanteSeroprevalencia> iter = mParticipantesSA.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearParticipanteSeroprevalencia(iter.next());
                    publishProgress("Insertando participantes seroprevalencia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mParticipantesSA = null;
            }*/
            //MA2020
            /*if (mEncuestasCasaSA != null){
                v = mEncuestasCasaSA.size();
                ListIterator<EncuestaCasaSA> iter = mEncuestasCasaSA.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestaCasaSA(iter.next());
                    publishProgress("Insertando encuestas de casa seroprevalencia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mEncuestasCasaSA = null;
            }
            if (mEncuestasParticipanteSA != null){
                v = mEncuestasParticipanteSA.size();
                ListIterator<EncuestaParticipanteSA> iter = mEncuestasParticipanteSA.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearEncuestaParticipanteSA(iter.next());
                    publishProgress("Insertando encuestas de participantes seroprevalencia en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mEncuestasParticipanteSA = null;
            }*/
		} catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			estudioAdapter.close();
			return e.getLocalizedMessage();
		}
		
		
		try {
			error = descargarDatosCasos();
			if (error!=null) return error;
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		publishProgress("Abriendo base de datos...","1","1");
		//Borrar los datos de la base de datos
        estudioAdapter.borrarCasaCohorteFamiliaCaso();
        estudioAdapter.borrarParticipanteCohorteFamiliaCaso();
        estudioAdapter.borrarVisitaSeguimientoCaso();
        estudioAdapter.borrarVisitaFallidaCaso();
        estudioAdapter.borrarVisitaSeguimientoCasoSintomas();
        estudioAdapter.borrarInformacionNoCompletaCaso();
        estudioAdapter.borrarFormularioContactoCaso();
        estudioAdapter.borrarVisitaFinalCaso();
		try {
            if (mCasaCohorteFamiliaCasos != null){
                v = mCasaCohorteFamiliaCasos.size();
                ListIterator<CasaCohorteFamiliaCaso> iter = mCasaCohorteFamiliaCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearCasaCohorteFamiliaCaso(iter.next());
                    publishProgress("Insertando casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mCasaCohorteFamiliaCasos = null;
            }
            if (mParticipanteCohorteFamiliaCasos != null){
                v = mParticipanteCohorteFamiliaCasos.size();
                ListIterator<ParticipanteCohorteFamiliaCaso> iter = mParticipanteCohorteFamiliaCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearParticipanteCohorteFamiliaCaso(iter.next());
                    publishProgress("Insertando participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mParticipanteCohorteFamiliaCasos = null;
            }
            
            if (mVisitaSeguimientoCasos != null){
                v = mVisitaSeguimientoCasos.size();
                ListIterator<VisitaSeguimientoCaso> iter = mVisitaSeguimientoCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVisitaSeguimientoCaso(iter.next());
                    publishProgress("Insertando visitas de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVisitaSeguimientoCasos = null;
            }
            
            if (mVisitaFallidaCasos != null){
                v = mVisitaFallidaCasos.size();
                ListIterator<VisitaFallidaCaso> iter = mVisitaFallidaCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVisitaFallidaCaso(iter.next());
                    publishProgress("Insertando visitas fallidas de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVisitaFallidaCasos = null;
            }
            
            if (mVisitaSeguimientoSintomasCasos != null){
                v = mVisitaSeguimientoSintomasCasos.size();
                ListIterator<VisitaSeguimientoCasoSintomas> iter = mVisitaSeguimientoSintomasCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVisitaSeguimientoCasoSintomas(iter.next());
                    publishProgress("Insertando sintomas de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVisitaSeguimientoSintomasCasos = null;
            }
            
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
            
            if (mInformacionNoCompletaCasos != null){
                v = mInformacionNoCompletaCasos.size();
                ListIterator<InformacionNoCompletaCaso> iter = mInformacionNoCompletaCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearInformacionNoCompletaCaso(iter.next());
                    publishProgress("Insertando no data de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mInformacionNoCompletaCasos = null;
            }
            if (mVisitaFinalCasos != null){
                v = mVisitaFinalCasos.size();
                ListIterator<VisitaFinalCaso> iter = mVisitaFinalCasos.listIterator();
                while (iter.hasNext()){
                    estudioAdapter.crearVisitaFinalCaso(iter.next());
                    publishProgress("Insertando visitas finales de los participantes de casas con casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
                            .valueOf(v).toString());
                }
                mVisitaFinalCasos = null;
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
            /*
            //Descargar barrios
            urlRequest = url + "/movil/barrios/";
            publishProgress("Solicitando barrios",BARRIO,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Barrio[]> responseEntityBarrio = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Barrio[].class);
            // convert the array to a list and return it
            mBarrios = Arrays.asList(responseEntityBarrio.getBody());
            responseEntityBarrio = null;
            //Descargar casas
            urlRequest = url + "/movil/casas/";
            publishProgress("Solicitando casas",CASA,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Casa[]> responseEntityCasa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Casa[].class);
            // convert the array to a list and return it
            mCasas = Arrays.asList(responseEntityCasa.getBody());
            responseEntityCasa = null;
            //Descargar participantes
            urlRequest = url + "/movil/participantes/";
            publishProgress("Solicitando participantes",PARTICIPANTE,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Participante[]> responseEntityParticipante = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Participante[].class);
            // convert the array to a list and return it
            mParticipantes = Arrays.asList(responseEntityParticipante.getBody());
            responseEntityParticipante = null;*/
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
    
    // url, username, password
    protected String descargarDatosReclutamiento() throws Exception {
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
            /*
            //Descargar casascohorte familia
            urlRequest = url + "/movil/casasCHF/";
            publishProgress("Solicitando casas cohorte familia",CASACHF,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<CasaCohorteFamilia[]> responseEntityCasasCHF = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    CasaCohorteFamilia[].class);
            // convert the array to a list and return it
            mCasasCHF = Arrays.asList(responseEntityCasasCHF.getBody());
            responseEntityCasasCHF = null;
            //Descargar participantes cohorte
            urlRequest = url + "/movil/participantesCHF/";
            publishProgress("Solicitando participantes de cohorte familia",PARTICIPANTECHF,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteCohorteFamilia[]> responseEntityParticipantesCHF = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteCohorteFamilia[].class);
            // convert the array to a list and return it
            mParticipantesCHF = Arrays.asList(responseEntityParticipantesCHF.getBody());
            responseEntityParticipantesCHF = null;
*/
            //Descargar encuestas de casa
            urlRequest = url + "/movil/encuestasCasa/";
            publishProgress("Solicitando encuestas de casas",ENCUESTA_CASACHF,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaCasa[]> responseEntityEncCasa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaCasa[].class);
            // convert the array to a list and return it
            mEncuestasCasas = Arrays.asList(responseEntityEncCasa.getBody());
            responseEntityEncCasa = null;
            //Descargar cocinas
            urlRequest = url + "/movil/cocinas/";
            publishProgress("Solicitando cocinas",COCINA,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Cocina[]> responseEntityCocinas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Cocina[].class);
            // convert the array to a list and return it
            mCocinas = Arrays.asList(responseEntityCocinas.getBody());
            responseEntityCocinas = null;
            //Descargar comedores
            urlRequest = url + "/movil/comedores/";
            publishProgress("Solicitando comedores",COMEDOR,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Comedor[]> responseEntityComedor = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Comedor[].class);
            // convert the array to a list and return it
            mComedores = Arrays.asList(responseEntityComedor.getBody());
            responseEntityComedor = null;
            //Descargar salas
            urlRequest = url + "/movil/salas/";
            publishProgress("Solicitando salas",SALA,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Sala[]> responseEntitySalas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Sala[].class);
            // convert the array to a list and return it
            mSalas = Arrays.asList(responseEntitySalas.getBody());
            responseEntitySalas = null;
            //Descargar habitaciones
            urlRequest = url + "/movil/habitaciones/";
            publishProgress("Solicitando habitaciones",HABITACION,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Habitacion[]> responseEntityHab = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Habitacion[].class);
            // convert the array to a list and return it
            mHabitaciones = Arrays.asList(responseEntityHab.getBody());
            responseEntityHab = null;
            //Descargar banios
            urlRequest = url + "/movil/banios/";
            publishProgress("Solicitando baños",BANIOS,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Banio[]> responseEntityBanios = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Banio[].class);
            // convert the array to a list and return it
            mBanios = Arrays.asList(responseEntityBanios.getBody());
            responseEntityBanios = null;
            //Descargar ventanas
            urlRequest = url + "/movil/ventanas/";
            publishProgress("Solicitando ventanas",VENTANAS,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Ventana[]> responseEntityVentanas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Ventana[].class);
            // convert the array to a list and return it
            mVentanas = Arrays.asList(responseEntityVentanas.getBody());
            responseEntityVentanas = null;
            //Descargar Cuartos
            urlRequest = url + "/movil/cuartos/";
            publishProgress("Solicitando cuartos",CUARTOS,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Cuarto[]> responseEntityCuartos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Cuarto[].class);
            // convert the array to a list and return it
            mCuartos = Arrays.asList(responseEntityCuartos.getBody());
            responseEntityCuartos = null;
            //Descargar Camas
            urlRequest = url + "/movil/camas/";
            publishProgress("Solicitando camas",CAMAS,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<Cama[]> responseEntityCamas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Cama[].class);
            // convert the array to a list and return it
            mCamas = Arrays.asList(responseEntityCamas.getBody());
            responseEntityCamas = null;
            //Descargar Personas Camas
            urlRequest = url + "/movil/personasCamas/";
            publishProgress("Solicitando Personas Camas",PERSONAS_CAMA,TOTAL_TASK_RECLUTAMIENTO);
            // Perform the HTTP GET request
            ResponseEntity<PersonaCama[]> responseEntityPerCamas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    PersonaCama[].class);
            // convert the array to a list and return it
            mPersonaCamas = Arrays.asList(responseEntityPerCamas.getBody());
            responseEntityPerCamas = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
    
    
 // url, username, password
    protected String descargarDatosReclutamientoCHF() throws Exception {
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
            //Descargar encuestasParticipante
            urlRequest = url + "/movil/encuestasParticipante/";
            publishProgress("Solicitando Encuestas de Participantes",ENCUESTA_PARTICIPANTECHF,TOTAL_TASK_RECLUTAMIENTO_CHF);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaParticipante[]> responseEntityEncPar = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaParticipante[].class);
            // convert the array to a list and return it
            mEncuestasParticipante = Arrays.asList(responseEntityEncPar.getBody());
            responseEntityEncPar = null;
            //Descargar encuestas datos parto bb
            urlRequest = url + "/movil/encuestasDatosPartoBB/";
            publishProgress("Solicitando Encuestas de datos parto BB",ENCUESTA_DATOSPBB,TOTAL_TASK_RECLUTAMIENTO_CHF);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaDatosPartoBB[]> responseEntityEncParto = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaDatosPartoBB[].class);
            // convert the array to a list and return it
            mEncuestasDatosPartoBB = Arrays.asList(responseEntityEncParto.getBody());
            responseEntityEncParto = null;
            //Descargar encuestas peso y talla
            urlRequest = url + "/movil/encuestasPesoTalla/";
            publishProgress("Solicitando Encuestas de peso y talla",ENCUESTA_PESOTALLA,TOTAL_TASK_RECLUTAMIENTO_CHF);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaPesoTalla[]> responseEntityEncPT = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaPesoTalla[].class);
            // convert the array to a list and return it
            mEncuestasPesoTalla = Arrays.asList(responseEntityEncPT.getBody());
            responseEntityEncPT = null;
            //Descargar encuestas peso y talla
            urlRequest = url + "/movil/encuestasLactanciaMaterna/";
            publishProgress("Solicitando Encuestas de lactancia materna",ENCUESTA_LACTMAT,TOTAL_TASK_RECLUTAMIENTO_CHF);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaLactanciaMaterna[]> responseEntityEncLactMat = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaLactanciaMaterna[].class);
            // convert the array to a list and return it
            mEncuestasLacMat = Arrays.asList(responseEntityEncLactMat.getBody());
            responseEntityEncLactMat = null;
            //Descargar muestras
            urlRequest = url + "/movil/muestras/";
            publishProgress("Solicitando Muestras",MUESTRAS,TOTAL_TASK_RECLUTAMIENTO_CHF);
            // Perform the HTTP GET request
            ResponseEntity<Muestra[]> responseEntityMuestras = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Muestra[].class);
            // convert the array to a list and return it
            mMuestras = Arrays.asList(responseEntityMuestras.getBody());
            responseEntityMuestras = null;
            
            //Descargar telefonos
            urlRequest = url + "/movil/telefonos/";
            publishProgress("Solicitando telefonos",TELEFONOS,TOTAL_TASK_RECLUTAMIENTO_CHF);
            // Perform the HTTP GET request
            ResponseEntity<TelefonoContacto[]> responseEntityTelefonos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		TelefonoContacto[].class);
            // convert the array to a list and return it
            mTelefonos = Arrays.asList(responseEntityTelefonos.getBody());
            responseEntityTelefonos = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
    
 // url, username, password
    protected String descargarDatosSeroprevalencia() throws Exception {
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
            /*
            //Descargar participantes seroprevalencia
            urlRequest = url + "/movil/participantesSA/";
            publishProgress("Solicitando participantes seroprevalencia",PARTICIPANTESA,TOTAL_TASK_SERO);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteSeroprevalencia[]> responseEntityPartiSa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteSeroprevalencia[].class);
            // convert the array to a list and return it
            mParticipantesSA = Arrays.asList(responseEntityPartiSa.getBody());
            responseEntityPartiSa = null;
            */
            //Descargar encuestas casas seroprevalencia
            //MA2020
            /*urlRequest = url + "/movil/encuestasCasaSA/";
            publishProgress("Solicitando encuestas de casas seroprevalencia",ENCUESTA_CASASA,TOTAL_TASK_SERO);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaCasaSA[]> responseEntityEncuCasaSa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaCasaSA[].class);
            // convert the array to a list and return it
            mEncuestasCasaSA = Arrays.asList(responseEntityEncuCasaSa.getBody());
            responseEntityEncuCasaSa = null;
            //Descargar encuestas participantes seroprevalencia
            urlRequest = url + "/movil/encuestasParticipanteSA/";
            publishProgress("Solicitando encuestas de participantes seroprevalencia",ENCUESTA_PARTICIPANTESA,TOTAL_TASK_SERO);
            // Perform the HTTP GET request
            ResponseEntity<EncuestaParticipanteSA[]> responseEntityEncuPartiSa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    EncuestaParticipanteSA[].class);
            // convert the array to a list and return it
            mEncuestasParticipanteSA = Arrays.asList(responseEntityEncuPartiSa.getBody());
            responseEntityEncuPartiSa = null;*/
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
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

            //Descargar casas con casos
            urlRequest = url + "/movil/casascasos/";
            publishProgress("Solicitando casas de casos",CASAS_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<CasaCohorteFamiliaCaso[]> responseEntityCasaCohorteFamiliaCasos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		CasaCohorteFamiliaCaso[].class);
            // convert the array to a list and return it
            mCasaCohorteFamiliaCasos = Arrays.asList(responseEntityCasaCohorteFamiliaCasos.getBody());
            responseEntityCasaCohorteFamiliaCasos = null;
            
            //Descargar participantes de casas con casos
            urlRequest = url + "/movil/participantescasos/";
            publishProgress("Solicitando participantes de casas de casos",PART_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteCohorteFamiliaCaso[]> responseEntityParticipanteCohorteFamiliaCasos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		ParticipanteCohorteFamiliaCaso[].class);
            // convert the array to a list and return it
            mParticipanteCohorteFamiliaCasos = Arrays.asList(responseEntityParticipanteCohorteFamiliaCasos.getBody());
            responseEntityParticipanteCohorteFamiliaCasos = null;
            
            //Descargar visitas de casas con casos
            urlRequest = url + "/movil/visitascasos/";
            publishProgress("Solicitando visitas de los participantes de casas de casos",PART_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<VisitaSeguimientoCaso[]> responseEntityVisitaSeguimientoCasos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		VisitaSeguimientoCaso[].class);
            // convert the array to a list and return it
            mVisitaSeguimientoCasos = Arrays.asList(responseEntityVisitaSeguimientoCasos.getBody());
            responseEntityVisitaSeguimientoCasos = null;
            
            //Descargar visitas fallidas de casas con casos
            urlRequest = url + "/movil/visitasfallidascasos/";
            publishProgress("Solicitando visitas fallidas de los participantes de casas de casos",VISITAS_FALLIDAS_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<VisitaFallidaCaso[]> responseEntityVisitaFallidaCasos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		VisitaFallidaCaso[].class);
            // convert the array to a list and return it
            mVisitaFallidaCasos = Arrays.asList(responseEntityVisitaFallidaCasos.getBody());
            responseEntityVisitaFallidaCasos = null;
            
            //Descargar sintomas de casas con casos
            urlRequest = url + "/movil/sintomascasos/";
            publishProgress("Solicitando sintomas de los participantes de casas de casos",SINTOMAS_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<VisitaSeguimientoCasoSintomas[]> responseEntityVisitaSeguimientoCasoSintomas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		VisitaSeguimientoCasoSintomas[].class);
            // convert the array to a list and return it
            mVisitaSeguimientoSintomasCasos = Arrays.asList(responseEntityVisitaSeguimientoCasoSintomas.getBody());
            responseEntityVisitaSeguimientoCasoSintomas = null;
            
            //Descargar contactos de casas con casos
            urlRequest = url + "/movil/contactoscasos/";
            publishProgress("Solicitando contactos de los participantes de casas de casos",CONTACTOS_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<FormularioContactoCaso[]> responseEntityFormularioContactoCaso = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		FormularioContactoCaso[].class);
            // convert the array to a list and return it
            mFormularioContactoCasos = Arrays.asList(responseEntityFormularioContactoCaso.getBody());
            responseEntityFormularioContactoCaso = null;
            
            //Descargar info de no data de casas con casos
            urlRequest = url + "/movil/visitasnodatacasos/";
            publishProgress("Solicitando registros sin datos de los participantes de casas de casos",NODATA_CASOS,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<InformacionNoCompletaCaso[]> responseEntityInformacionNoCompletaCaso = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		InformacionNoCompletaCaso[].class);
            // convert the array to a list and return it
            mInformacionNoCompletaCasos = Arrays.asList(responseEntityInformacionNoCompletaCaso.getBody());
            responseEntityInformacionNoCompletaCaso = null;

            //Descargar visitas finales de casas con casos
            urlRequest = url + "/movil/visitasfinalescasos/";
            publishProgress("Solicitando visitas finales de los participantes de casas de casos", VISITAS_FINALES,TOTAL_TASK_CASOS);
            // Perform the HTTP GET request
            ResponseEntity<VisitaFinalCaso[]> responseEntityVisitasFinales = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    VisitaFinalCaso[].class);
            // convert the array to a list and return it
            mVisitaFinalCasos = Arrays.asList(responseEntityVisitasFinales.getBody());
            responseEntityVisitasFinales = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
