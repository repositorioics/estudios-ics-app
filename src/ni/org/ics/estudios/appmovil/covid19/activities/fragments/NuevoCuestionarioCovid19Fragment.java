package ni.org.ics.estudios.appmovil.covid19.activities.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.covid19.CuestionarioCovid19;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuInfoActivity;
import ni.org.ics.estudios.appmovil.multiselector.gui.MultiSpinner;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import org.joda.time.DateMidnight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NuevoCuestionarioCovid19Fragment extends Fragment {

    private static final String TAG = "NuevoCuestionarioCovid19Fragment";
    protected static final int FECHA_SINTOMA = 101;
    protected static final int FECHA_ADMISION = 102;
    protected static final int FECHA_ALTA = 103;

    protected static final String FEB20FEBRICULA_CONS = "FEB20FEBRICULA";
    protected static final String FEB20FIEBRE_CONS = "FEB20FIEBRE";
    protected static final String FEB20ESCALOFRIO_CONS = "FEB20ESCALOFRIO";
    protected static final String FEB20TEMBLORESCALOFRIO_CONS = "FEB20TEMBLORESCALOFRIO";
    protected static final String FEB20DOLORMUSCULAR_CONS = "FEB20DOLORMUSCULAR";
    protected static final String FEB20DOLORARTICULAR_CONS = "FEB20DOLORARTICULAR";
    protected static final String FEB20SECRESIONNASAL_CONS = "FEB20SECRESIONNASAL";
    protected static final String FEB20DOLORGARGANTA_CONS = "FEB20DOLORGARGANTA";
    protected static final String FEB20TOS_CONS = "FEB20TOS";
    protected static final String FEB20DIFICULTADRESP_CONS = "FEB20DIFICULTADRESP";
    protected static final String FEB20DOLORPECHO_CONS = "FEB20DOLORPECHO";
    protected static final String FEB20NAUSEASVOMITO_CONS = "FEB20NAUSEASVOMITO";
    protected static final String FEB20DOLORCABEZA_CONS = "FEB20DOLORCABEZA";
    protected static final String FEB20DOLORABDOMINAL_CONS = "FEB20DOLORABDOMINAL";
    protected static final String FEB20DIARREA_CONS = "FEB20DIARREA";
    protected static final String FEB20DIFICULTADDORMIR_CONS = "FEB20DIFICULTADDORMIR";
    protected static final String FEB20DEBILIDAD_CONS = "FEB20DEBILIDAD";
    protected static final String FEB20PERDIDAOLFATOGUSTO_CONS = "FEB20PERDIDAOLFATOGUSTO";
    protected static final String FEB20MAREO_CONS = "FEB20MAREO";
    protected static final String FEB20SARPULLIDO_CONS = "FEB20SARPULLIDO";
    protected static final String FEB20DESMAYO_CONS = "FEB20DESMAYO";
    protected static final String FEB20QUEDOCAMA_CONS = "FEB20QUEDOCAMA";
    protected static final String SABEFIS_CONS = "SABEFIS";
    protected static final String FIS_CONS = "FIS";
    protected static final String MESINICIOSINTOMA_CONS = "MESINICIOSINTOMA";
    protected static final String ANIOINICIOSINTOMA_CONS = "ANIOINICIOSINTOMA";
    protected static final String PADECIDOCOVID19_CONS = "PADECIDOCOVID19";
    protected static final String CONOCELUGAREXPOSICION_CONS = "CONOCELUGAREXPOSICION";
    protected static final String LUGAREXPOSICION_CONS = "LUGAREXPOSICION";
    protected static final String BUSCOAYUDA_CONS = "BUSCOAYUDA";
    protected static final String DONDEBUSCOAYUDA_CONS = "DONDEBUSCOAYUDA";
    protected static final String NOMBRECENTROSALUD_CONS = "NOMBRECENTROSALUD";
    protected static final String NOMBREHOSPITAL_CONS = "NOMBREHOSPITAL";
    protected static final String RECIBIOSEGUIMIENTO_CONS = "RECIBIOSEGUIMIENTO";
    protected static final String TIPOSEGUIMIENTO_CONS = "TIPOSEGUIMIENTO";
    protected static final String TMPDESPUESBUSCOAYUDA_CONS = "TMPDESPUESBUSCOAYUDA";
    protected static final String UNANOCHEHOSPITAL_CONS = "UNANOCHEHOSPITAL";
    protected static final String QUEHOSPITAL_CONS = "QUEHOSPITAL";
    protected static final String SABECUANTASNOCHES_CONS = "SABECUANTASNOCHES";
    protected static final String CUANTASNOCHESHOSP_CONS = "CUANTASNOCHESHOSP";
    protected static final String SABEFECHAADMISION_CONS = "SABEFECHAADMISION";
    protected static final String FECHAADMISIONHOSP_CONS = "FECHAADMISIONHOSP";
    protected static final String SABEFECHAALTA_CONS = "SABEFECHAALTA";
    protected static final String FECHAALTAHOSP_CONS = "FECHAALTAHOSP";
    protected static final String UTILIZOOXIGENO_CONS = "UTILIZOOXIGENO";
    protected static final String ESTUVOUCI_CONS = "ESTUVOUCI";
    protected static final String FUEINTUBADO_CONS = "FUEINTUBADO";
    protected static final String RECUPERADOCOVID19_CONS = "RECUPERADOCOVID19";
    protected static final String FEBRICULA_CONS = "FEBRICULA";
    protected static final String CANSANCIO_CONS = "CANSANCIO";
    protected static final String DOLORCABEZA_CONS = "DOLORCABEZA";
    protected static final String PERDIDAOLFATO_CONS = "PERDIDAOLFATO";
    protected static final String PERDIDAGUSTO_CONS = "PERDIDAGUSTO";
    protected static final String TOS_CONS = "TOS";
    protected static final String DIFICULTADRESPIRAR_CONS = "DIFICULTADRESPIRAR";
    protected static final String DOLORPECHO_CONS = "DOLORPECHO";
    protected static final String PALPITACIONES_CONS = "PALPITACIONES";
    protected static final String DOLORARTICULACIONES_CONS = "DOLORARTICULACIONES";
    protected static final String PARALISIS_CONS = "PARALISIS";
    protected static final String MAREOS_CONS = "MAREOS";
    protected static final String PENSAMIENTONUBLADO_CONS = "PENSAMIENTONUBLADO";
    protected static final String PROBLEMASDORMIR_CONS = "PROBLEMASDORMIR";
    protected static final String DEPRESION_CONS = "DEPRESION";
    protected static final String OTROSSINTOMAS_CONS = "OTROSSINTOMAS";
    protected static final String CUALESSINTOMAS_CONS = "CUALESSINTOMAS";
    protected static final String SABETIEMPORECUPERACION_CONS = "SABETIEMPORECUPERACION";
    protected static final String TIEMPORECUPERACION_CONS = "TIEMPORECUPERACION";
    protected static final String TIEMPORECUPERACIONEN_CONS = "TIEMPORECUPERACIONEN";
    protected static final String SEVERIDADENFERMEDAD_CONS = "SEVERIDADENFERMEDAD";
    protected static final String TOMOMEDICAMENTO_CONS = "TOMOMEDICAMENTO";
    protected static final String QUEMEDICAMENTO_CONS = "QUEMEDICAMENTO";
    protected static final String OTROMEDICAMENTO_CONS = "OTROMEDICAMENTO";
    protected static final String PADECEENFISEMA_CONS = "PADECEENFISEMA";
    protected static final String PADECEASMA_CONS = "PADECEASMA";
    protected static final String PADECEDIABETES_CONS = "PADECEDIABETES";
    protected static final String PADECEENFCORONARIA_CONS = "PADECEENFCORONARIA";
    protected static final String PADECEPRESIONALTA_CONS = "PADECEPRESIONALTA";
    protected static final String PADECEENFHIGADO_CONS = "PADECEENFHIGADO";
    protected static final String PADECEENFRENAL_CONS = "PADECEENFRENAL";
    protected static final String PADECEINFARTODERRAMECER_CONS = "PADECEINFARTODERRAMECER";
    protected static final String PADECECANCER_CONS = "PADECECANCER";
    protected static final String PADECECONDICIONINMUNO_CONS = "PADECECONDICIONINMUNO";
    protected static final String PADECEENFAUTOINMUNE_CONS = "PADECEENFAUTOINMUNE";
    protected static final String PADECEDISCAPACIDADFIS_CONS = "PADECEDISCAPACIDADFIS";
    protected static final String PADECECONDPSICPSIQ_CONS = "PADECECONDPSICPSIQ";
    protected static final String PADECEOTRACONDICION_CONS = "PADECEOTRACONDICION";
    protected static final String QUEOTRACONDICION_CONS = "QUEOTRACONDICION";
    protected static final String FUMADO_CONS = "FUMADO";
    protected static final String FUMADOCIENCIGARRILLOS_CONS = "FUMADOCIENCIGARRILLOS";
    protected static final String FUMADOPREVIOENFERMEDAD_CONS = "FUMADOPREVIOENFERMEDAD";
    protected static final String FUMAACTUALMENTE_CONS = "FUMAACTUALMENTE";
    protected static final String EMBARAZADA_CONS = "EMBARAZADA_CONS";
    protected static final String RECUERDASEMANASEMB_CONS = "RECUERDASEMANASEMB_CONS";
    protected static final String FINALEMBARAZO_CONS = "FINALEMBARAZO_CONS";
    protected static final String DABAPECHO_CONS = "DABAPECHO_CONS";
    protected static final String TRABAJADORSALUD_CONS = "TRABAJADORSALUD_CONS";

    //Base de datos
    private EstudiosAdapter estudiosAdapter;
    final Calendar c = Calendar.getInstance();

    //Para el id
    private DeviceInfo infoMovil;
    private String username;
    private SharedPreferences settings;

    //Viene de la llamada
    private Participante participante;

    //Catalogos
    private List<MessageResource> mCatalogoSiNo;//Si, No
    private List<MessageResource> mCatalogoSiNoNsNc;//Si, No, No Sabe, No Contesto
    private List<MessageResource> mCatalogoSiNsNc;//Si, No Sabe, No Contesto
    private List<MessageResource> mCatalogoSiNoNc;//Si, No, No Contesto
    private List<MessageResource> mCatalogoFuma;
    private List<String> mMedicamentos = new ArrayList<String>();
    private List<MessageResource> mCatalogoSeveridad;
    private List<MessageResource> mCatalogoTiempo;
    private List<MessageResource> mCatalogoDondeBA;
    private List<MessageResource> mCatalogoTipoSeg;
    private List<MessageResource> mCatalogoUnidadMedTR;
    private List<MessageResource> mCatalogoMeses = new ArrayList<MessageResource>();
    private List<MessageResource> mCatalogoFinalEmb;

    //Objeto que se va a hacer
    private CuestionarioCovid19 mCuestionario = new CuestionarioCovid19();

    //Widgets en el View
    private TextView mTitleView;
    private EditText mNameView;
    private Button mSaveView;
    private TextView textFeb20Febricula;
    private TextView textFeb20Fiebre;
    private TextView textFeb20Escalofrio;
    private TextView textFeb20TemblorEscalofrio;
    private TextView textFeb20DolorMuscular;
    private TextView textFeb20DolorArticular;
    private TextView textFeb20SecresionNasal;
    private TextView textFeb20DolorGarganta;
    private TextView textFeb20Tos;
    private TextView textFeb20DificultadResp;
    private TextView textFeb20DolorPecho;
    private TextView textFeb20NauseasVomito;
    private TextView textFeb20DolorCabeza;
    private TextView textFeb20DolorAbdominal;
    private TextView textFeb20Diarrea;
    private TextView textFeb20DificultadDormir;
    private TextView textFeb20Debilidad;
    private TextView textFeb20PerdidaOlfatoGusto;
    private TextView textFeb20Mareo;
    private TextView textFeb20Sarpullido;
    private TextView textFeb20Desmayo;
    private TextView textFeb20QuedoCama;
    private TextView textSabeInicioSintoma;
    private TextView textFIS;
    //private TextView textDiaInicioSintoma;
    private TextView textMesInicioSintoma;
    private TextView textAnioInicioSintoma;
    private TextView textPadecidoCovid19;
    private TextView textConoceLugarExposicion;
    private TextView textLugarExposicion;
    private TextView textBuscoAyuda;
    private TextView textDondeBuscoAyuda;
    private TextView textNombreCentro;
    private TextView textNombreHospital;
    private TextView textRecibioSeguimiento;
    private TextView textTipoSeguimiento;
    private TextView textTmpDespuesBuscoAyuda;
    private TextView textUnaNocheHospital;
    private TextView textQueHospital;
    private TextView textCuantasNochesHosp;
    private TextView textFechaAdmisionHosp;
    private TextView textFechaAltaHosp;
    private TextView textUtilizoOxigeno;
    private TextView textEstuvoUCI;
    private TextView textFueIntubado;
    private TextView textRecuperadoCovid19;
    private TextView textFebricula;
    private TextView textCansancio;
    private TextView textDolorCabeza;
    private TextView textPerdidaOlfato;
    private TextView textPerdidaGusto;
    private TextView textTos;
    private TextView textDificultadRespirar;
    private TextView textDolorPecho;
    private TextView textPalpitaciones;
    private TextView textDolorArticulaciones;
    private TextView textParalisis;
    private TextView textMareos;
    private TextView textPensamientoNublado;
    private TextView textProblemasDormir;
    private TextView textDepresion;
    private TextView textOtrosSintomas;
    private TextView textCualesSintomas;
    private TextView textTiempoRecuperacion;
    private TextView textSabeTiempoRecuperacion;
    private TextView textSeveridadEnfermedad;
    private TextView textTomoMedicamento;
    private TextView textQueMedicamento;
    private TextView textOtroMedicamento;
    private TextView textPadeceEnfisema;
    private TextView textPadeceAsma;
    private TextView textPadeceDiabetes;
    private TextView textPadeceEnfCoronaria;
    private TextView textPadecePresionAlta;
    private TextView textPadeceEnfHigado;
    private TextView textPadeceEnfRenal;
    private TextView textPadeceInfartoDerrameCer;
    private TextView textPadeceCancer;
    private TextView textPadeceCondicionInmuno;
    private TextView textPadeceEnfAutoinmune;
    private TextView textPadeceDiscapacidadFis;
    private TextView textPadeceCondPsicPsiq;
    private TextView textPadeceOtraCondicion;
    private TextView textQueOtraCondicion;
    private TextView textFumado;
    private TextView textFumadoCienCigarrillos;
    private TextView textFumadoPrevioEnfermedad;
    private TextView textFumaActualmente;
    //private TextView textInicioSintoma;
    private TextView textQueSintomasPresenta;
    private TextView textCondiciones;
    private TextView textSabeFechaAlta;
    private TextView textSabeFechaAdmision;
    private TextView textSabeCuantasNoches;
    //MA2021
    private TextView textDesdeSintomas;
    private TextView textEmbarazada;
    private TextView textRecuerdaSemanasEmb;
    private TextView textSemanasEmbarazo;
    private TextView textFinalEmbarazo;
    private TextView textOtroFinalEmbarazo;
    private TextView textDabaPecho;
    private TextView textTrabajadorSalud;

    private Spinner spinFeb20Febricula;
    private Spinner spinFeb20Fiebre;
    private Spinner spinFeb20Escalofrio;
    private Spinner spinFeb20TemblorEscalofrio;
    private Spinner spinFeb20DolorMuscular;
    private Spinner spinFeb20DolorArticular;
    private Spinner spinFeb20SecresionNasal;
    private Spinner spinFeb20DolorGarganta;
    private Spinner spinFeb20Tos;
    private Spinner spinFeb20DificultadResp;
    private Spinner spinFeb20DolorPecho;
    private Spinner spinFeb20NauseasVomito;
    private Spinner spinFeb20DolorCabeza;
    private Spinner spinFeb20DolorAbdominal;
    private Spinner spinFeb20Diarrea;
    private Spinner spinFeb20DificultadDormir;
    private Spinner spinFeb20Debilidad;
    private Spinner spinFeb20PerdidaOlfatoGusto;
    private Spinner spinFeb20Mareo;
    private Spinner spinFeb20Sarpullido;
    private Spinner spinFeb20Desmayo;
    private Spinner spinFeb20QuedoCama;
    private Spinner spinSabeInicioSintoma;
    private EditText inputFIS;
    //private Spinner spinDiaInicioSintoma;
    private Spinner spinMesInicioSintoma;
    private EditText inputAnioInicioSintoma;
    private Spinner spinPadecidoCovid19;
    private Spinner spinConoceLugarExposicion;
    private EditText inputLugarExposicion;
    private Spinner spinBuscoAyuda;
    private Spinner spinDondeBuscoAyuda;
    private EditText inputNombreCentro;
    private EditText inputNombreHospital;
    private Spinner spinRecibioSeguimiento;
    private Spinner spinTipoSeguimiento;
    private Spinner spinTmpDespuesBuscoAyuda;
    private Spinner spinUnaNocheHospital;
    private EditText inputQueHospital;
    private EditText inputCuantasNochesHosp;
    private EditText inputFechaAdmisionHosp;
    private EditText inputFechaAltaHosp;
    private Spinner spinUtilizoOxigeno;
    private Spinner spinEstuvoUCI;
    private Spinner spinFueIntubado;
    private Spinner spinRecuperadoCovid19;
    private Spinner spinFebricula;
    private Spinner spinCansancio;
    private Spinner spinDolorCabeza;
    private Spinner spinPerdidaOlfato;
    private Spinner spinPerdidaGusto;
    private Spinner spinTos;
    private Spinner spinDificultadRespirar;
    private Spinner spinDolorPecho;
    private Spinner spinPalpitaciones;
    private Spinner spinDolorArticulaciones;
    private Spinner spinParalisis;
    private Spinner spinMareos;
    private Spinner spinPensamientoNublado;
    private Spinner spinProblemasDormir;
    private Spinner spinDepresion;
    private Spinner spinOtrosSintomas;
    private EditText inputCualesSintomas;
    private Spinner spinSabeTiempoRecuperacion;
    private EditText inputTiempoRecuperacion;
    private Spinner spinTiempoRecuperacionEn;
    private Spinner spinSeveridadEnfermedad;
    private Spinner spinTomoMedicamento;
    private MultiSpinner spinQueMedicamento;
    private EditText inputOtroMedicamento;
    private Spinner spinPadeceEnfisema;
    private Spinner spinPadeceAsma;
    private Spinner spinPadeceDiabetes;
    private Spinner spinPadeceEnfCoronaria;
    private Spinner spinPadecePresionAlta;
    private Spinner spinPadeceEnfHigado;
    private Spinner spinPadeceEnfRenal;
    private Spinner spinPadeceInfartoDerrameCer;
    private Spinner spinPadeceCancer;
    private Spinner spinPadeceCondicionInmuno;
    private Spinner spinPadeceEnfAutoinmune;
    private Spinner spinPadeceDiscapacidadFis;
    private Spinner spinPadeceCondPsicPsiq;
    private Spinner spinPadeceOtraCondicion;
    private EditText inputQueOtraCondicion;
    private Spinner spinFumado;
    private Spinner spinFumadoCienCigarrillos;
    private Spinner spinFumadoPrevioEnfermedad;
    private Spinner spinFumaActualmente;
    private Spinner spinSabeFechaAlta;
    private Spinner spinSabeFechaAdmision;
    private Spinner spinSabeCuantasNoches;
    //MA2021
    private Spinner spinEmbarazada;
    private Spinner spinRecuerdaSemanasEmb;
    private EditText inputSemanasEmbarazo;
    private Spinner spinFinalEmbarazo;
    private EditText inputOtroFinalEmbarazo;
    private Spinner spinDabaPecho;
    private Spinner spinTrabajadorSalud;

    private ImageButton imbFIS;
    private ImageButton imbFechaAdmisionHosp;
    private ImageButton imbFechaAltaHosp;

    private boolean mostrarConfirmacion = true;
    //Variables donde se captura la entrada de datos
    private int feb20FebriculaPos = 0;
    private int feb20FiebrePos = 0;
    private int feb20EscalofrioPos = 0;
    private int feb20TemblorEscalofrioPos = 0;
    private int feb20DolorMuscularPos = 0;
    private int feb20DolorArticularPos = 0;
    private int feb20SecresionNasalPos = 0;
    private int feb20DolorGargantaPos = 0;
    private int feb20TosPos = 0;
    private int feb20DificultadRespPos = 0;
    private int feb20DolorPechoPos = 0;
    private int feb20NauseasVomitoPos = 0;
    private int feb20DolorCabezaPos = 0;
    private int feb20DolorAbdominalPos = 0;
    private int feb20DiarreaPos = 0;
    private int feb20DificultadDormirPos = 0;
    private int feb20DebilidadPos = 0;
    private int feb20PerdidaOlfatoGustoPos = 0;
    private int feb20MareoPos = 0;
    private int feb20SarpullidoPos = 0;
    private int feb20DesmayoPos = 0;
    private int feb20QuedoCamaPos = 0;
    private int sabeFISPos = 0;
    private int fisPos = 0;
    private int diaInicioSintomaPos = 0;
    private int mesInicioSintomaPos = 0;
    private int anioInicioSintomaPos = 0;
    private int padecidoCovid19Pos = 0;
    private int conoceLugarExposicionPos = 0;
    private int lugarExposicionPos = 0;
    private int buscoAyudaPos = 0;
    private int dondeBuscoAyudaPos = 0;
    private int nombreCentroSaludPos = 0;
    private int nombreHospitalPos = 0;
    private int recibioSeguimientoPos = 0;
    private int tipoSeguimientoPos = 0;
    private int tmpDespuesBuscoAyudaPos = 0;
    private int unaNocheHospitalPos = 0;
    private int queHospitalPos = 0;
    private int sabeCuantasNochesPos = 0;
    private int cuantasNochesHospPos = 0;
    private int sabeFechaAdmisionPos = 0;
    private int fechaAdmisionHospPos = 0;
    private int sabeFechaAltaPos = 0;
    private int fechaAltaHospPos = 0;
    private int utilizoOxigenoPos = 0;
    private int estuvoUCIPos = 0;
    private int fueIntubadoPos = 0;
    private int recuperadoCovid19Pos = 0;
    private int febriculaPos = 0;
    private int cansancioPos = 0;
    private int dolorCabezaPos = 0;
    private int perdidaOlfatoPos = 0;
    private int perdidaGustoPos = 0;
    private int tosPos = 0;
    private int dificultadRespirarPos = 0;
    private int dolorPechoPos = 0;
    private int palpitacionesPos = 0;
    private int dolorArticulacionesPos = 0;
    private int paralisisPos = 0;
    private int mareosPos = 0;
    private int pensamientoNubladoPos = 0;
    private int problemasDormirPos = 0;
    private int depresionPos = 0;
    private int otrosSintomasPos = 0;
    private int cualesSintomasPos = 0;
    private int sabeTiempoRecuperacionPos = 0;
    private int tiempoRecuperacionPos = 0;
    private int tiempoRecuperacionEnPos = 0;
    private int severidadEnfermedadPos = 0;
    private int tomoMedicamentoPos = 0;
    private int queMedicamentoPos = 0;
    private int otroMedicamentoPos = 0;
    private int padeceEnfisemaPos = 0;
    private int padeceAsmaPos = 0;
    private int padeceDiabetesPos = 0;
    private int padeceEnfCoronariaPos = 0;
    private int padecePresionAltaPos = 0;
    private int padeceEnfHigadoPos = 0;
    private int padeceEnfRenalPos = 0;
    private int padeceInfartoDerrameCerPos = 0;
    private int padeceCancerPos = 0;
    private int padeceCondicionInmunoPos = 0;
    private int padeceEnfAutoinmunePos = 0;
    private int padeceDiscapacidadFisPos = 0;
    private int padeceCondPsicPsiqPos = 0;
    private int padeceOtraCondicionPos = 0;
    private int queOtraCondicionPos = 0;
    private int fumadoPos = 0;
    private int fumadoCienCigarrillosPos = 0;
    private int fumadoPrevioEnfermedadPos = 0;
    private int fumaActualmentePos = 0;
    //MA2021
    private int embarazadaPos = 0;
    private int recuerdaSemanasEmbPos = 0;
    private int finalEmbarazoPos = 0;
    private int dabaPechoPos = 0;
    private int trabajadorSaludPos = 0;

    private String feb20Febricula;
    private String feb20Fiebre;
    private String feb20Escalofrio;
    private String feb20TemblorEscalofrio;
    private String feb20DolorMuscular;
    private String feb20DolorArticular;
    private String feb20SecresionNasal;
    private String feb20DolorGarganta;
    private String feb20Tos;
    private String feb20DificultadResp;
    private String feb20DolorPecho;
    private String feb20NauseasVomito;
    private String feb20DolorCabeza;
    private String feb20DolorAbdominal;
    private String feb20Diarrea;
    private String feb20DificultadDormir;
    private String feb20Debilidad;
    private String feb20PerdidaOlfatoGusto;
    private String feb20Mareo;
    private String feb20Sarpullido;
    private String feb20Desmayo;
    private String feb20QuedoCama;
    private String sabeFIS;
    private String fis;
    private String mesInicioSintoma;
    private String anioInicioSintoma;
    private String padecidoCovid19;
    private String conoceLugarExposicion;
    private String lugarExposicion;
    private String buscoAyuda;
    private String dondeBuscoAyuda;
    private String nombreCentroSalud;
    private String nombreHospital;
    private String recibioSeguimiento;
    private String tipoSeguimiento;
    private String tmpDespuesBuscoAyuda;
    private String unaNocheHospital;
    private String queHospital;
    private String sabeCuantasNoches;
    private Integer cuantasNochesHosp;
    private String sabeFechaAdmision;
    private String fechaAdmisionHosp;
    private String sabeFechaAlta;
    private String fechaAltaHosp;
    private String utilizoOxigeno;
    private String estuvoUCI;
    private String fueIntubado;
    private String recuperadoCovid19;
    private String febricula;
    private String cansancio;
    private String dolorCabeza;
    private String perdidaOlfato;
    private String perdidaGusto;
    private String tos;
    private String dificultadRespirar;
    private String dolorPecho;
    private String palpitaciones;
    private String dolorArticulaciones;
    private String paralisis;
    private String mareos;
    private String pensamientoNublado;
    private String problemasDormir;
    private String depresion;
    private String otrosSintomas;
    private String cualesSintomas;
    private String sabeTiempoRecuperacion;
    private String tiempoRecuperacion;
    private String tiempoRecuperacionEn;
    private String severidadEnfermedad;
    private String tomoMedicamento;
    private String queMedicamento;
    private String otroMedicamento;
    private String padeceEnfisema;
    private String padeceAsma;
    private String padeceDiabetes;
    private String padeceEnfCoronaria;
    private String padecePresionAlta;
    private String padeceEnfHigado;
    private String padeceEnfRenal;
    private String padeceInfartoDerrameCer;
    private String padeceCancer;
    private String padeceCondicionInmuno;
    private String padeceEnfAutoinmune;
    private String padeceDiscapacidadFis;
    private String padeceCondPsicPsiq;
    private String padeceOtraCondicion;
    private String queOtraCondicion;
    private String fumado;
    private String fumadoCienCigarrillos;
    private String fumadoPrevioEnfermedad;
    private String fumaActualmente;
    //MA2021
    private String embarazada;
    private String recuerdaSemanasEmb;
    private Integer semanasEmbarazo;
    private String finalEmbarazo;
    private String otroFinalEmbarazo;
    private String dabaPecho;
    private String trabajadorSalud;
    private String periodoSintomas;

    public static NuevoCuestionarioCovid19Fragment create() {
        NuevoCuestionarioCovid19Fragment fragment = new NuevoCuestionarioCovid19Fragment();
        return fragment;
    }

    public NuevoCuestionarioCovid19Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
        participante = (Participante)getActivity().getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        infoMovil = new DeviceInfo(getActivity());
        settings =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        new FetchCatalogosTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nuevo_cuestionario_covid19, container, false);
        mTitleView = ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((EditText) rootView.findViewById(R.id.inputParticipante));
        mNameView.setText(participante.getNombreCompleto());
        mNameView.setEnabled(false);
        textDesdeSintomas = (TextView) rootView.findViewById(R.id.textDesdeSintomas);
        textFeb20Febricula = (TextView) rootView.findViewById(R.id.textFeb20Febricula);
        textFeb20Fiebre = (TextView) rootView.findViewById(R.id.textFeb20Fiebre);
        textFeb20Escalofrio = (TextView) rootView.findViewById(R.id.textFeb20Escalofrio);
        textFeb20TemblorEscalofrio = (TextView) rootView.findViewById(R.id.textFeb20TemblorEscalofrio);
        textFeb20DolorMuscular = (TextView) rootView.findViewById(R.id.textFeb20DolorMuscular);
        textFeb20DolorArticular = (TextView) rootView.findViewById(R.id.textFeb20DolorArticular);
        textFeb20SecresionNasal = (TextView) rootView.findViewById(R.id.textFeb20SecresionNasal);
        textFeb20DolorGarganta = (TextView) rootView.findViewById(R.id.textFeb20DolorGarganta);
        textFeb20Tos = (TextView) rootView.findViewById(R.id.textFeb20Tos);
        textFeb20DificultadResp = (TextView) rootView.findViewById(R.id.textFeb20DificultadResp);
        textFeb20DolorPecho = (TextView) rootView.findViewById(R.id.textFeb20DolorPecho);
        textFeb20NauseasVomito = (TextView) rootView.findViewById(R.id.textFeb20NauseasVomito);
        textFeb20DolorCabeza = (TextView) rootView.findViewById(R.id.textFeb20DolorCabeza);
        textFeb20DolorAbdominal = (TextView) rootView.findViewById(R.id.textFeb20DolorAbdominal);
        textFeb20Diarrea = (TextView) rootView.findViewById(R.id.textFeb20Diarrea);
        textFeb20DificultadDormir = (TextView) rootView.findViewById(R.id.textFeb20DificultadDormir);
        textFeb20Debilidad = (TextView) rootView.findViewById(R.id.textFeb20Debilidad);
        textFeb20PerdidaOlfatoGusto = (TextView) rootView.findViewById(R.id.textFeb20PerdidaOlfatoGusto);
        textFeb20Mareo = (TextView) rootView.findViewById(R.id.textFeb20Mareo);
        textFeb20Sarpullido = (TextView) rootView.findViewById(R.id.textFeb20Sarpullido);
        textFeb20Desmayo = (TextView) rootView.findViewById(R.id.textFeb20Desmayo);
        textFeb20QuedoCama = (TextView) rootView.findViewById(R.id.textFeb20QuedoCama);
        textSabeInicioSintoma = (TextView) rootView.findViewById(R.id.textSabeInicioSintoma);
        textFIS = (TextView) rootView.findViewById(R.id.textFIS);
        textFIS.setVisibility(View.GONE);
        textMesInicioSintoma = (TextView) rootView.findViewById(R.id.textMesInicioSintoma);
        textMesInicioSintoma.setVisibility(View.GONE);
        textAnioInicioSintoma = (TextView) rootView.findViewById(R.id.textAnioInicioSintoma);
        textAnioInicioSintoma.setVisibility(View.GONE);
        textPadecidoCovid19 = (TextView) rootView.findViewById(R.id.textPadecidoCovid19);
        textConoceLugarExposicion = (TextView) rootView.findViewById(R.id.textConoceLugarExposicion);
        textConoceLugarExposicion.setVisibility(View.GONE);
        textLugarExposicion = (TextView) rootView.findViewById(R.id.textLugarExposicion);
        textLugarExposicion.setVisibility(View.GONE);
        textBuscoAyuda = (TextView) rootView.findViewById(R.id.textBuscoAyuda);
        textBuscoAyuda.setVisibility(View.GONE);
        textDondeBuscoAyuda = (TextView) rootView.findViewById(R.id.textDondeBuscoAyuda);
        textDondeBuscoAyuda.setVisibility(View.GONE);
        textNombreCentro = (TextView) rootView.findViewById(R.id.textNombreCentro);
        textNombreCentro.setVisibility(View.GONE);
        textNombreHospital = (TextView) rootView.findViewById(R.id.textNombreHospital);
        textNombreHospital.setVisibility(View.GONE);
        textRecibioSeguimiento = (TextView) rootView.findViewById(R.id.textRecibioSeguimiento);
        textRecibioSeguimiento.setVisibility(View.GONE);
        textTipoSeguimiento = (TextView) rootView.findViewById(R.id.textTipoSeguimiento);
        textTipoSeguimiento.setVisibility(View.GONE);
        textTmpDespuesBuscoAyuda = (TextView) rootView.findViewById(R.id.textTmpDespuesBuscoAyuda);
        textTmpDespuesBuscoAyuda.setVisibility(View.GONE);
        textUnaNocheHospital = (TextView) rootView.findViewById(R.id.textUnaNocheHospital);
        textUnaNocheHospital.setVisibility(View.GONE);
        textQueHospital = (TextView) rootView.findViewById(R.id.textQueHospital);
        textQueHospital.setVisibility(View.GONE);
        textCuantasNochesHosp = (TextView) rootView.findViewById(R.id.textCuantasNochesHosp);
        textCuantasNochesHosp.setVisibility(View.GONE);
        textFechaAdmisionHosp = (TextView) rootView.findViewById(R.id.textFechaAdmisionHosp);
        textFechaAdmisionHosp.setVisibility(View.GONE);
        textFechaAltaHosp = (TextView) rootView.findViewById(R.id.textFechaAltaHosp);
        textFechaAltaHosp.setVisibility(View.GONE);
        textUtilizoOxigeno = (TextView) rootView.findViewById(R.id.textUtilizoOxigeno);
        textUtilizoOxigeno.setVisibility(View.GONE);
        textEstuvoUCI = (TextView) rootView.findViewById(R.id.textEstuvoUCI);
        textEstuvoUCI.setVisibility(View.GONE);
        textFueIntubado = (TextView) rootView.findViewById(R.id.textFueIntubado);
        textFueIntubado.setVisibility(View.GONE);
        textRecuperadoCovid19 = (TextView) rootView.findViewById(R.id.textRecuperadoCovid19);
        textRecuperadoCovid19.setVisibility(View.GONE);
        textFebricula = (TextView) rootView.findViewById(R.id.textFebricula);
        textFebricula.setVisibility(View.GONE);
        textCansancio = (TextView) rootView.findViewById(R.id.textCansancio);
        textCansancio.setVisibility(View.GONE);
        textDolorCabeza = (TextView) rootView.findViewById(R.id.textDolorCabeza);
        textDolorCabeza.setVisibility(View.GONE);
        textPerdidaOlfato = (TextView) rootView.findViewById(R.id.textPerdidaOlfato);
        textPerdidaOlfato.setVisibility(View.GONE);
        textPerdidaGusto = (TextView) rootView.findViewById(R.id.textPerdidaGusto);
        textPerdidaGusto.setVisibility(View.GONE);
        textTos = (TextView) rootView.findViewById(R.id.textTos);
        textTos.setVisibility(View.GONE);
        textDificultadRespirar = (TextView) rootView.findViewById(R.id.textDificultadRespirar);
        textDificultadRespirar.setVisibility(View.GONE);
        textDolorPecho = (TextView) rootView.findViewById(R.id.textDolorPecho);
        textDolorPecho.setVisibility(View.GONE);
        textPalpitaciones = (TextView) rootView.findViewById(R.id.textPalpitaciones);
        textPalpitaciones.setVisibility(View.GONE);
        textDolorArticulaciones = (TextView) rootView.findViewById(R.id.textDolorArticulaciones);
        textDolorArticulaciones.setVisibility(View.GONE);
        textParalisis = (TextView) rootView.findViewById(R.id.textParalisis);
        textParalisis.setVisibility(View.GONE);
        textMareos = (TextView) rootView.findViewById(R.id.textMareos);
        textMareos.setVisibility(View.GONE);
        textPensamientoNublado = (TextView) rootView.findViewById(R.id.textPensamientoNublado);
        textPensamientoNublado.setVisibility(View.GONE);
        textProblemasDormir = (TextView) rootView.findViewById(R.id.textProblemasDormir);
        textProblemasDormir.setVisibility(View.GONE);
        textDepresion = (TextView) rootView.findViewById(R.id.textDepresion);
        textDepresion.setVisibility(View.GONE);
        textOtrosSintomas = (TextView) rootView.findViewById(R.id.textOtrosSintomas);
        textOtrosSintomas.setVisibility(View.GONE);
        textCualesSintomas = (TextView) rootView.findViewById(R.id.textCualesSintomas);
        textCualesSintomas.setVisibility(View.GONE);
        textSabeTiempoRecuperacion = (TextView) rootView.findViewById(R.id.textSabeTiempoRecuperacion);
        textSabeTiempoRecuperacion.setVisibility(View.GONE);
        textTiempoRecuperacion = (TextView) rootView.findViewById(R.id.textTiempoRecuperacion);
        textTiempoRecuperacion.setVisibility(View.GONE);
        textSeveridadEnfermedad = (TextView) rootView.findViewById(R.id.textSeveridadEnfermedad);
        textSeveridadEnfermedad.setVisibility(View.GONE);
        textTomoMedicamento = (TextView) rootView.findViewById(R.id.textTomoMedicamento);
        textTomoMedicamento.setVisibility(View.GONE);
        textQueMedicamento = (TextView) rootView.findViewById(R.id.textQueMedicamento);
        textQueMedicamento.setVisibility(View.GONE);
        textPadeceEnfisema = (TextView) rootView.findViewById(R.id.textPadeceEnfisema);
        textPadeceAsma = (TextView) rootView.findViewById(R.id.textPadeceAsma);
        textPadeceDiabetes = (TextView) rootView.findViewById(R.id.textPadeceDiabetes);
        textPadeceEnfCoronaria = (TextView) rootView.findViewById(R.id.textPadeceEnfCoronaria);
        textPadecePresionAlta = (TextView) rootView.findViewById(R.id.textPadecePresionAlta);
        textPadeceEnfHigado = (TextView) rootView.findViewById(R.id.textPadeceEnfHigado);
        textPadeceEnfRenal = (TextView) rootView.findViewById(R.id.textPadeceEnfRenal);
        textPadeceInfartoDerrameCer = (TextView) rootView.findViewById(R.id.textPadeceInfartoDerrameCer);
        textPadeceCancer = (TextView) rootView.findViewById(R.id.textPadeceCancer);
        textPadeceCondicionInmuno = (TextView) rootView.findViewById(R.id.textPadeceCondicionInmuno);
        textPadeceEnfAutoinmune = (TextView) rootView.findViewById(R.id.textPadeceEnfAutoinmune);
        textPadeceDiscapacidadFis = (TextView) rootView.findViewById(R.id.textPadeceDiscapacidadFis);
        textPadeceCondPsicPsiq = (TextView) rootView.findViewById(R.id.textPadeceCondPsicPsiq);
        textPadeceOtraCondicion = (TextView) rootView.findViewById(R.id.textPadeceOtraCondicion);
        textQueOtraCondicion = (TextView) rootView.findViewById(R.id.textQueOtraCondicion);
        textQueOtraCondicion.setVisibility(View.GONE);
        textFumado = (TextView) rootView.findViewById(R.id.textFumado);
        textFumadoCienCigarrillos = (TextView) rootView.findViewById(R.id.textFumadoCienCigarrillos);
        textFumadoCienCigarrillos.setVisibility(View.GONE);
        textFumadoPrevioEnfermedad = (TextView) rootView.findViewById(R.id.textFumadoPrevioEnfermedad);
        textFumadoPrevioEnfermedad.setVisibility(View.GONE);
        textFumaActualmente = (TextView) rootView.findViewById(R.id.textFumaActualmente);
        textFumaActualmente.setVisibility(View.GONE);
        textOtroMedicamento = (TextView) rootView.findViewById(R.id.textOtroMedicamento);
        textOtroMedicamento.setVisibility(View.GONE);
        //textInicioSintoma = (TextView) rootView.findViewById(R.id.textInicioSintoma);
        //textInicioSintoma.setVisibility(View.GONE);
        textQueSintomasPresenta = (TextView) rootView.findViewById(R.id.textQueSintomasPresenta);
        textQueSintomasPresenta.setVisibility(View.GONE);
        textCondiciones = (TextView) rootView.findViewById(R.id.textCondiciones);
        textSabeFechaAlta = (TextView) rootView.findViewById(R.id.textSabeFechaAlta);
        textSabeFechaAlta.setVisibility(View.GONE);
        textSabeFechaAdmision = (TextView) rootView.findViewById(R.id.textSabeFechaAdmision);
        textSabeFechaAdmision.setVisibility(View.GONE);
        textSabeCuantasNoches = (TextView) rootView.findViewById(R.id.textSabeCuantasNoches);
        textSabeCuantasNoches.setVisibility(View.GONE);
        textEmbarazada = (TextView) rootView.findViewById(R.id.textEmbarazada);
        textEmbarazada.setVisibility(View.GONE);
        textRecuerdaSemanasEmb = (TextView) rootView.findViewById(R.id.textRecuerdaSemanasEmb);
        textRecuerdaSemanasEmb.setVisibility(View.GONE);
        textSemanasEmbarazo = (TextView) rootView.findViewById(R.id.textSemanasEmbarazo);
        textSemanasEmbarazo.setVisibility(View.GONE);
        textFinalEmbarazo = (TextView) rootView.findViewById(R.id.textFinalEmbarazo);
        textFinalEmbarazo.setVisibility(View.GONE);
        textOtroFinalEmbarazo = (TextView) rootView.findViewById(R.id.textOtroFinalEmbarazo);
        textOtroFinalEmbarazo.setVisibility(View.GONE);
        textDabaPecho = (TextView) rootView.findViewById(R.id.textDabaPecho);
        textDabaPecho.setVisibility(View.GONE);
        textTrabajadorSalud = (TextView) rootView.findViewById(R.id.textTrabajadorSalud);

        spinFeb20Febricula = (Spinner) rootView.findViewById(R.id.spinFeb20Febricula);
        spinFeb20Fiebre = (Spinner) rootView.findViewById(R.id.spinFeb20Fiebre);
        spinFeb20Escalofrio = (Spinner) rootView.findViewById(R.id.spinFeb20Escalofrio);
        spinFeb20TemblorEscalofrio = (Spinner) rootView.findViewById(R.id.spinFeb20TemblorEscalofrio);
        spinFeb20DolorMuscular = (Spinner) rootView.findViewById(R.id.spinFeb20DolorMuscular);
        spinFeb20DolorArticular = (Spinner) rootView.findViewById(R.id.spinFeb20DolorArticular);
        spinFeb20SecresionNasal = (Spinner) rootView.findViewById(R.id.spinFeb20SecresionNasal);
        spinFeb20DolorGarganta = (Spinner) rootView.findViewById(R.id.spinFeb20DolorGarganta);
        spinFeb20Tos = (Spinner) rootView.findViewById(R.id.spinFeb20Tos);
        spinFeb20DificultadResp = (Spinner) rootView.findViewById(R.id.spinFeb20DificultadResp);
        spinFeb20DolorPecho = (Spinner) rootView.findViewById(R.id.spinFeb20DolorPecho);
        spinFeb20NauseasVomito = (Spinner) rootView.findViewById(R.id.spinFeb20NauseasVomito);
        spinFeb20DolorCabeza = (Spinner) rootView.findViewById(R.id.spinFeb20DolorCabeza);
        spinFeb20DolorAbdominal = (Spinner) rootView.findViewById(R.id.spinFeb20DolorAbdominal);
        spinFeb20Diarrea = (Spinner) rootView.findViewById(R.id.spinFeb20Diarrea);
        spinFeb20DificultadDormir = (Spinner) rootView.findViewById(R.id.spinFeb20DificultadDormir);
        spinFeb20Debilidad = (Spinner) rootView.findViewById(R.id.spinFeb20Debilidad);
        spinFeb20PerdidaOlfatoGusto = (Spinner) rootView.findViewById(R.id.spinFeb20PerdidaOlfatoGusto);
        spinFeb20Mareo = (Spinner) rootView.findViewById(R.id.spinFeb20Mareo);
        spinFeb20Sarpullido = (Spinner) rootView.findViewById(R.id.spinFeb20Sarpullido);
        spinFeb20Desmayo = (Spinner) rootView.findViewById(R.id.spinFeb20Desmayo);
        spinFeb20QuedoCama = (Spinner) rootView.findViewById(R.id.spinFeb20QuedoCama);
        spinSabeInicioSintoma = (Spinner) rootView.findViewById(R.id.spinSabeInicioSintoma);
        inputFIS = (EditText) rootView.findViewById(R.id.inputFIS);
        spinMesInicioSintoma = (Spinner) rootView.findViewById(R.id.spinMesInicioSintoma);
        inputAnioInicioSintoma = (EditText) rootView.findViewById(R.id.inputAnioInicioSintoma);
        //inputAnioInicioSintoma.setText(String.valueOf(c.get(Calendar.YEAR)));
        //anioInicioSintoma = inputAnioInicioSintoma.getText().toString();
        //inputAnioInicioSintoma.setEnabled(false);
        spinPadecidoCovid19 = (Spinner) rootView.findViewById(R.id.spinPadecidoCovid19);
        spinConoceLugarExposicion = (Spinner) rootView.findViewById(R.id.spinConoceLugarExposicion);
        inputLugarExposicion = (EditText) rootView.findViewById(R.id.inputLugarExposicion);
        spinBuscoAyuda = (Spinner) rootView.findViewById(R.id.spinBuscoAyuda);
        spinDondeBuscoAyuda = (Spinner) rootView.findViewById(R.id.spinDondeBuscoAyuda);
        inputNombreCentro = (EditText) rootView.findViewById(R.id.inputNombreCentro);
        inputNombreHospital = (EditText) rootView.findViewById(R.id.inputNombreHospital);
        spinRecibioSeguimiento = (Spinner) rootView.findViewById(R.id.spinRecibioSeguimiento);
        spinTipoSeguimiento = (Spinner) rootView.findViewById(R.id.spinTipoSeguimiento);
        spinTmpDespuesBuscoAyuda = (Spinner) rootView.findViewById(R.id.spinTmpDespuesBuscoAyuda);
        spinUnaNocheHospital = (Spinner) rootView.findViewById(R.id.spinUnaNocheHospital);
        inputQueHospital = (EditText) rootView.findViewById(R.id.inputQueHospital);
        inputCuantasNochesHosp = (EditText) rootView.findViewById(R.id.inputCuantasNochesHosp);
        inputFechaAdmisionHosp = (EditText) rootView.findViewById(R.id.inputFechaAdmisionHosp);
        inputFechaAltaHosp = (EditText) rootView.findViewById(R.id.inputFechaAltaHosp);
        spinUtilizoOxigeno = (Spinner) rootView.findViewById(R.id.spinUtilizoOxigeno);
        spinEstuvoUCI = (Spinner) rootView.findViewById(R.id.spinEstuvoUCI);
        spinFueIntubado = (Spinner) rootView.findViewById(R.id.spinFueIntubado);
        spinRecuperadoCovid19 = (Spinner) rootView.findViewById(R.id.spinRecuperadoCovid19);
        spinFebricula = (Spinner) rootView.findViewById(R.id.spinFebricula);
        spinCansancio = (Spinner) rootView.findViewById(R.id.spinCansancio);
        spinDolorCabeza = (Spinner) rootView.findViewById(R.id.spinDolorCabeza);
        spinPerdidaOlfato = (Spinner) rootView.findViewById(R.id.spinPerdidaOlfato);
        spinPerdidaGusto = (Spinner) rootView.findViewById(R.id.spinPerdidaGusto);
        spinTos = (Spinner) rootView.findViewById(R.id.spinTos);
        spinDificultadRespirar = (Spinner) rootView.findViewById(R.id.spinDificultadRespirar);
        spinDolorPecho = (Spinner) rootView.findViewById(R.id.spinDolorPecho);
        spinPalpitaciones = (Spinner) rootView.findViewById(R.id.spinPalpitaciones);
        spinDolorArticulaciones = (Spinner) rootView.findViewById(R.id.spinDolorArticulaciones);
        spinParalisis = (Spinner) rootView.findViewById(R.id.spinParalisis);
        spinMareos = (Spinner) rootView.findViewById(R.id.spinMareos);
        spinPensamientoNublado = (Spinner) rootView.findViewById(R.id.spinPensamientoNublado);
        spinProblemasDormir = (Spinner) rootView.findViewById(R.id.spinProblemasDormir);
        spinDepresion = (Spinner) rootView.findViewById(R.id.spinDepresion);
        spinOtrosSintomas = (Spinner) rootView.findViewById(R.id.spinOtrosSintomas);
        inputCualesSintomas = (EditText) rootView.findViewById(R.id.inputCualesSintomas);
        spinSabeTiempoRecuperacion = (Spinner) rootView.findViewById(R.id.spinSabeTiempoRecuperacion);
        inputTiempoRecuperacion = (EditText) rootView.findViewById(R.id.inputTiempoRecuperacion);
        spinTiempoRecuperacionEn = (Spinner) rootView.findViewById(R.id.spinTiempoRecuperacionEn);
        spinSeveridadEnfermedad = (Spinner) rootView.findViewById(R.id.spinSeveridadEnfermedad);
        spinTomoMedicamento = (Spinner) rootView.findViewById(R.id.spinTomoMedicamento);
        spinQueMedicamento = (MultiSpinner) rootView.findViewById(R.id.spinQueMedicamento);
        spinPadeceEnfisema = (Spinner) rootView.findViewById(R.id.spinPadeceEnfisema);
        spinPadeceAsma = (Spinner) rootView.findViewById(R.id.spinPadeceAsma);
        spinPadeceDiabetes = (Spinner) rootView.findViewById(R.id.spinPadeceDiabetes);
        spinPadeceEnfCoronaria = (Spinner) rootView.findViewById(R.id.spinPadeceEnfCoronaria);
        spinPadecePresionAlta = (Spinner) rootView.findViewById(R.id.spinPadecePresionAlta);
        spinPadeceEnfHigado = (Spinner) rootView.findViewById(R.id.spinPadeceEnfHigado);
        spinPadeceEnfRenal = (Spinner) rootView.findViewById(R.id.spinPadeceEnfRenal);
        spinPadeceInfartoDerrameCer = (Spinner) rootView.findViewById(R.id.spinPadeceInfartoDerrameCer);
        spinPadeceCancer = (Spinner) rootView.findViewById(R.id.spinPadeceCancer);
        spinPadeceCondicionInmuno = (Spinner) rootView.findViewById(R.id.spinPadeceCondicionInmuno);
        spinPadeceEnfAutoinmune = (Spinner) rootView.findViewById(R.id.spinPadeceEnfAutoinmune);
        spinPadeceDiscapacidadFis = (Spinner) rootView.findViewById(R.id.spinPadeceDiscapacidadFis);
        spinPadeceCondPsicPsiq = (Spinner) rootView.findViewById(R.id.spinPadeceCondPsicPsiq);
        spinPadeceOtraCondicion = (Spinner) rootView.findViewById(R.id.spinPadeceOtraCondicion);
        inputQueOtraCondicion = (EditText) rootView.findViewById(R.id.inputQueOtraCondicion);
        spinFumado = (Spinner) rootView.findViewById(R.id.spinFumado);
        spinFumadoCienCigarrillos = (Spinner) rootView.findViewById(R.id.spinFumadoCienCigarrillos);
        spinFumadoPrevioEnfermedad = (Spinner) rootView.findViewById(R.id.spinFumadoPrevioEnfermedad);
        spinFumaActualmente = (Spinner) rootView.findViewById(R.id.spinFumaActualmente);
        imbFIS = (ImageButton) rootView.findViewById(R.id.imbFIS);
        imbFechaAdmisionHosp = (ImageButton) rootView.findViewById(R.id.imbFechaAdmisionHosp);
        imbFechaAltaHosp = (ImageButton) rootView.findViewById(R.id.imbFechaAltaHosp);
        inputOtroMedicamento = (EditText) rootView.findViewById(R.id.inputOtroMedicamento);
        spinSabeFechaAlta = (Spinner) rootView.findViewById(R.id.spinSabeFechaAlta);
        spinSabeFechaAdmision = (Spinner) rootView.findViewById(R.id.spinSabeFechaAdmision);
        spinSabeCuantasNoches = (Spinner) rootView.findViewById(R.id.spinSabeCuantasNoches);
        spinEmbarazada = (Spinner) rootView.findViewById(R.id.spinEmbarazada);
        spinRecuerdaSemanasEmb = (Spinner) rootView.findViewById(R.id.spinRecuerdaSemanasEmb);
        inputSemanasEmbarazo = (EditText) rootView.findViewById(R.id.inputSemanasEmbarazo);
        spinFinalEmbarazo = (Spinner) rootView.findViewById(R.id.spinFinalEmbarazo);
        inputOtroFinalEmbarazo = (EditText) rootView.findViewById(R.id.inputOtroFinalEmbarazo);
        spinDabaPecho = (Spinner) rootView.findViewById(R.id.spinDabaPecho);
        spinTrabajadorSalud = (Spinner) rootView.findViewById(R.id.spinTrabajadorSalud);

        inputFIS.setVisibility(View.GONE);
        spinMesInicioSintoma.setVisibility(View.GONE);
        inputAnioInicioSintoma.setVisibility(View.GONE);
        spinConoceLugarExposicion.setVisibility(View.GONE);
        inputLugarExposicion.setVisibility(View.GONE);
        spinBuscoAyuda.setVisibility(View.GONE);
        spinDondeBuscoAyuda.setVisibility(View.GONE);
        inputNombreCentro.setVisibility(View.GONE);
        inputNombreHospital.setVisibility(View.GONE);
        spinRecibioSeguimiento.setVisibility(View.GONE);
        spinTipoSeguimiento.setVisibility(View.GONE);
        spinTmpDespuesBuscoAyuda.setVisibility(View.GONE);
        spinUnaNocheHospital.setVisibility(View.GONE);
        inputQueHospital.setVisibility(View.GONE);
        inputCuantasNochesHosp.setVisibility(View.GONE);
        inputFechaAdmisionHosp.setVisibility(View.GONE);
        inputFechaAltaHosp.setVisibility(View.GONE);
        spinUtilizoOxigeno.setVisibility(View.GONE);
        spinEstuvoUCI.setVisibility(View.GONE);
        spinFueIntubado.setVisibility(View.GONE);
        spinRecuperadoCovid19.setVisibility(View.GONE);
        spinFebricula.setVisibility(View.GONE);
        spinCansancio.setVisibility(View.GONE);
        spinDolorCabeza.setVisibility(View.GONE);
        spinPerdidaOlfato.setVisibility(View.GONE);
        spinPerdidaGusto.setVisibility(View.GONE);
        spinTos.setVisibility(View.GONE);
        spinDificultadRespirar.setVisibility(View.GONE);
        spinDolorPecho.setVisibility(View.GONE);
        spinPalpitaciones.setVisibility(View.GONE);
        spinDolorArticulaciones.setVisibility(View.GONE);
        spinParalisis.setVisibility(View.GONE);
        spinMareos.setVisibility(View.GONE);
        spinPensamientoNublado.setVisibility(View.GONE);
        spinProblemasDormir.setVisibility(View.GONE);
        spinDepresion.setVisibility(View.GONE);
        spinOtrosSintomas.setVisibility(View.GONE);
        inputCualesSintomas.setVisibility(View.GONE);
        spinSabeTiempoRecuperacion.setVisibility(View.GONE);
        inputTiempoRecuperacion.setVisibility(View.GONE);
        spinTiempoRecuperacionEn.setVisibility(View.GONE);
        spinSeveridadEnfermedad.setVisibility(View.GONE);
        spinTomoMedicamento.setVisibility(View.GONE);
        spinQueMedicamento.setVisibility(View.GONE);
        inputQueOtraCondicion.setVisibility(View.GONE);
        if (participante.getEdadMeses() < 168) {
            textFumado.setVisibility(View.GONE);
            spinFumado.setVisibility(View.GONE);
        }

        spinFumadoCienCigarrillos.setVisibility(View.GONE);
        spinFumadoPrevioEnfermedad.setVisibility(View.GONE);
        spinFumaActualmente.setVisibility(View.GONE);
        spinSabeFechaAlta.setVisibility(View.GONE);
        spinSabeFechaAdmision.setVisibility(View.GONE);
        spinSabeCuantasNoches.setVisibility(View.GONE);
        inputOtroMedicamento.setVisibility(View.GONE);
        imbFIS.setVisibility(View.GONE);
        imbFechaAltaHosp.setVisibility(View.GONE);
        imbFechaAdmisionHosp.setVisibility(View.GONE);
        spinEmbarazada.setVisibility(View.GONE);
        spinRecuerdaSemanasEmb.setVisibility(View.GONE);
        inputSemanasEmbarazo.setVisibility(View.GONE);
        spinFinalEmbarazo.setVisibility(View.GONE);
        inputOtroFinalEmbarazo.setVisibility(View.GONE);
        spinDabaPecho.setVisibility(View.GONE);
        if (participante.getEdadMeses() < 216) {//menores de 18
            textTrabajadorSalud.setVisibility(View.GONE);
            spinTrabajadorSalud.setVisibility(View.GONE);
        }

        if (participante.getProcesos().getCuestCovid().equalsIgnoreCase("1a")){
            textDesdeSintomas.setText(getString(R.string.feb20));
            periodoSintomas = Constants.PERIODO_CUEST_COVID19_1;
        }else if (participante.getProcesos().getCuestCovid().equalsIgnoreCase("2a")){
            textDesdeSintomas.setText(getString(R.string.oct20));
            periodoSintomas = Constants.PERIODO_CUEST_COVID19_2;
        }

        imbFIS.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_SINTOMA);
            }
        });

        imbFechaAdmisionHosp.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_ADMISION);
            }
        });

        imbFechaAltaHosp.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_ALTA);
            }
        });

        inputLugarExposicion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                    lugarExposicion = inputLugarExposicion.getText().toString();
            }
        });

        inputAnioInicioSintoma.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                anioInicioSintoma = inputAnioInicioSintoma.getText().toString();
                List<MessageResource> meses  = new ArrayList<MessageResource>();
                if (anioInicioSintoma != null && anioInicioSintoma.equalsIgnoreCase("2020")) {
                    for (MessageResource mes : mCatalogoMeses) {
                        if (Integer.parseInt(mes.getCatKey()) >= 2 && Integer.parseInt(mes.getCatKey()) <= 12) {
                            meses.add(mes);
                        }
                    }
                } else if (anioInicioSintoma != null && anioInicioSintoma.equalsIgnoreCase("2021")) {
                    for (MessageResource mes : mCatalogoMeses) {
                        if (Integer.parseInt(mes.getCatKey()) >= 1 && Integer.parseInt(mes.getCatKey()) <= c.get(Calendar.MONTH) + 1) {
                            meses.add(mes);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.wrongYear, "2020", "2021"), Toast.LENGTH_LONG).show();
                    inputAnioInicioSintoma.requestFocus();
                }
                meses.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
                Collections.sort(meses);
                ArrayAdapter<MessageResource> dataAdapterMeses = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, meses);
                dataAdapterMeses.setDropDownViewResource(R.layout.spinner_item);
                spinMesInicioSintoma.setAdapter(dataAdapterMeses);
            }
        });

        inputNombreCentro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                nombreCentroSalud = inputNombreCentro.getText().toString();
            }
        });

        inputNombreHospital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                nombreHospital = inputNombreHospital.getText().toString();
            }
        });

        inputQueHospital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                queHospital = inputQueHospital.getText().toString();
            }
        });

        inputCuantasNochesHosp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (inputCuantasNochesHosp.getText()!= null && !inputCuantasNochesHosp.getText().toString().isEmpty())
                    cuantasNochesHosp = Integer.valueOf(inputCuantasNochesHosp.getText().toString());
            }
        });

        inputCualesSintomas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                cualesSintomas = inputCualesSintomas.getText().toString();
            }
        });

        inputTiempoRecuperacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                tiempoRecuperacion = inputTiempoRecuperacion.getText().toString();
            }
        });

        inputOtroMedicamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otroMedicamento = inputOtroMedicamento.getText().toString();
            }
        });

        inputQueOtraCondicion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                queOtraCondicion = inputQueOtraCondicion.getText().toString();
            }
        });

        inputSemanasEmbarazo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (inputSemanasEmbarazo.getText()!= null && !inputSemanasEmbarazo.getText().toString().isEmpty())
                    semanasEmbarazo = Integer.valueOf(inputSemanasEmbarazo.getText().toString());
            }
        });

        inputOtroFinalEmbarazo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otroFinalEmbarazo = inputOtroFinalEmbarazo.getText().toString();
            }
        });

        /*SINTOMAS ANTES FEBRERO 2020*/
        spinFeb20Febricula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20FEBRICULA_CONS, getString(R.string.feb20Febricula), feb20Febricula, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20Febricula = mr.getCatKey();
                if (feb20Febricula.equals(Constants.YESKEYSND)) {
                    spinFeb20Febricula.setBackgroundColor(Color.RED);
                    spinFeb20Fiebre.setVisibility(View.GONE);
                    textFeb20Fiebre.setVisibility(View.GONE);
                    spinFeb20Fiebre.setSelection(0, false);
                } else {
                    spinFeb20Febricula.setBackgroundColor(Color.WHITE);
                    spinFeb20Fiebre.setVisibility(View.VISIBLE);
                    textFeb20Fiebre.setVisibility(View.VISIBLE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20Fiebre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20FIEBRE_CONS, getString(R.string.feb20Fiebre), feb20Fiebre, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20Fiebre = mr.getCatKey();
                if(feb20Fiebre.equals(Constants.YESKEYSND)){
                    spinFeb20Fiebre.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20Fiebre.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20Escalofrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20ESCALOFRIO_CONS, getString(R.string.feb20Escalofrio), feb20Escalofrio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20Escalofrio = mr.getCatKey();
                if(feb20Escalofrio.equals(Constants.YESKEYSND)){
                    spinFeb20Escalofrio.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20Escalofrio.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20TemblorEscalofrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20TEMBLORESCALOFRIO_CONS, getString(R.string.feb20TemblorEscalofrio), feb20TemblorEscalofrio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20TemblorEscalofrio = mr.getCatKey();
                if(feb20TemblorEscalofrio.equals(Constants.YESKEYSND)){
                    spinFeb20TemblorEscalofrio.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20TemblorEscalofrio.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20DolorMuscular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20DOLORMUSCULAR_CONS, getString(R.string.feb20DolorMuscular), feb20DolorMuscular, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20DolorMuscular = mr.getCatKey();
                if(feb20DolorMuscular.equals(Constants.YESKEYSND)){
                    spinFeb20DolorMuscular.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20DolorMuscular.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20DolorArticular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20DOLORARTICULAR_CONS, getString(R.string.feb20DolorArticular), feb20DolorArticular, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20DolorArticular = mr.getCatKey();
                if(feb20DolorArticular.equals(Constants.YESKEYSND)){
                    spinFeb20DolorArticular.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20DolorArticular.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20SecresionNasal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20SECRESIONNASAL_CONS, getString(R.string.feb20SecresionNasal), feb20SecresionNasal, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20SecresionNasal = mr.getCatKey();
                if(feb20SecresionNasal.equals(Constants.YESKEYSND)){
                    spinFeb20SecresionNasal.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20SecresionNasal.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20DolorGarganta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20DOLORGARGANTA_CONS, getString(R.string.feb20DolorGarganta), feb20DolorGarganta, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20DolorGarganta = mr.getCatKey();
                if(feb20DolorGarganta.equals(Constants.YESKEYSND)){
                    spinFeb20DolorGarganta.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20DolorGarganta.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20Tos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20TOS_CONS, getString(R.string.feb20Tos), feb20Tos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20Tos = mr.getCatKey();
                if(feb20Tos.equals(Constants.YESKEYSND)){
                    spinFeb20Tos.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20Tos.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20DificultadResp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20DIFICULTADRESP_CONS, getString(R.string.feb20DificultadResp), feb20DificultadResp, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20DificultadResp = mr.getCatKey();
                if(feb20DificultadResp.equals(Constants.YESKEYSND)){
                    spinFeb20DificultadResp.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20DificultadResp.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20DolorPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20DOLORPECHO_CONS, getString(R.string.feb20DolorPecho), feb20DolorPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20DolorPecho = mr.getCatKey();
                if(feb20DolorPecho.equals(Constants.YESKEYSND)){
                    spinFeb20DolorPecho.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20DolorPecho.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20NauseasVomito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20NAUSEASVOMITO_CONS, getString(R.string.feb20NauseasVomito), feb20NauseasVomito, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20NauseasVomito = mr.getCatKey();
                if(feb20NauseasVomito.equals(Constants.YESKEYSND)){
                    spinFeb20NauseasVomito.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20NauseasVomito.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20DolorCabeza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20DOLORCABEZA_CONS, getString(R.string.feb20DolorCabeza), feb20DolorCabeza, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20DolorCabeza = mr.getCatKey();
                if(feb20DolorCabeza.equals(Constants.YESKEYSND)){
                    spinFeb20DolorCabeza.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20DolorCabeza.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20DolorAbdominal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20DOLORABDOMINAL_CONS, getString(R.string.feb20DolorAbdominal), feb20DolorAbdominal, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20DolorAbdominal = mr.getCatKey();
                if(feb20DolorAbdominal.equals(Constants.YESKEYSND)){
                    spinFeb20DolorAbdominal.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20DolorAbdominal.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20Diarrea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20DIARREA_CONS, getString(R.string.feb20Diarrea), feb20Diarrea, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20Diarrea = mr.getCatKey();
                if(feb20Diarrea.equals(Constants.YESKEYSND)){
                    spinFeb20Diarrea.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20Diarrea.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20DificultadDormir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20DIFICULTADDORMIR_CONS, getString(R.string.feb20DificultadDormir), feb20DificultadDormir, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20DificultadDormir = mr.getCatKey();
                if(feb20DificultadDormir.equals(Constants.YESKEYSND)){
                    spinFeb20DificultadDormir.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20DificultadDormir.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20Debilidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20DEBILIDAD_CONS, getString(R.string.feb20Debilidad), feb20Debilidad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20Debilidad = mr.getCatKey();
                if(feb20Debilidad.equals(Constants.YESKEYSND)){
                    spinFeb20Debilidad.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20Debilidad.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20PerdidaOlfatoGusto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20PERDIDAOLFATOGUSTO_CONS, getString(R.string.feb20PerdidaOlfatoGusto), feb20PerdidaOlfatoGusto, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20PerdidaOlfatoGusto = mr.getCatKey();
                if(feb20PerdidaOlfatoGusto.equals(Constants.YESKEYSND)){
                    spinFeb20PerdidaOlfatoGusto.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20PerdidaOlfatoGusto.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20Mareo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20MAREO_CONS, getString(R.string.feb20Mareo), feb20Mareo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20Mareo = mr.getCatKey();
                if(feb20Mareo.equals(Constants.YESKEYSND)){
                    spinFeb20Mareo.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20Mareo.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20Sarpullido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20SARPULLIDO_CONS, getString(R.string.feb20Sarpullido), feb20Sarpullido, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20Sarpullido = mr.getCatKey();
                if(feb20Sarpullido.equals(Constants.YESKEYSND)){
                    spinFeb20Sarpullido.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20Sarpullido.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20Desmayo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20DESMAYO_CONS, getString(R.string.feb20Desmayo), feb20Desmayo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20Desmayo = mr.getCatKey();
                if(feb20Desmayo.equals(Constants.YESKEYSND)){
                    spinFeb20Desmayo.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20Desmayo.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFeb20QuedoCama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEB20QUEDOCAMA_CONS, getString(R.string.feb20QuedoCama), feb20QuedoCama, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                feb20QuedoCama = mr.getCatKey();
                if(feb20QuedoCama.equals(Constants.YESKEYSND)){
                    spinFeb20QuedoCama.setBackgroundColor(Color.RED);
                }
                else{
                    spinFeb20QuedoCama.setBackgroundColor(Color.WHITE);
                }
                MostrarOcultarPregunta2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*FIN SINTOMAS ANTES FEBRERO 2020*/

        spinSabeInicioSintoma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SABEFIS_CONS, getString(R.string.sabeInicioSintoma), sabeFIS, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                sabeFIS = mr.getCatKey();
                if(sabeFIS.equals(Constants.YESKEYSND)){
                    spinSabeInicioSintoma.setBackgroundColor(Color.RED);
                    textFIS.setVisibility(View.VISIBLE);
                    inputFIS.setVisibility(View.VISIBLE);
                    imbFIS.setVisibility(View.VISIBLE);
                    //textInicioSintoma.setVisibility(View.GONE);
                    MostrarOcultarPregunta2_Mes(View.GONE);
                    textAnioInicioSintoma.setVisibility(View.GONE);
                    inputAnioInicioSintoma.setVisibility(View.GONE);
                    anioInicioSintoma = null;
                }
                else if(sabeFIS.equals(Constants.NOKEYSND)){
                    spinSabeInicioSintoma.setBackgroundColor(Color.WHITE);
                    textFIS.setVisibility(View.GONE);
                    inputFIS.setVisibility(View.GONE);
                    imbFIS.setVisibility(View.GONE);
                    //textInicioSintoma.setVisibility(View.VISIBLE);
                    MostrarOcultarPregunta2_Mes(View.VISIBLE);
                    textAnioInicioSintoma.setVisibility(View.VISIBLE);
                    inputAnioInicioSintoma.setVisibility(View.VISIBLE);
                    anioInicioSintoma = inputAnioInicioSintoma.getText().toString();
                    fis = null;
                } else {
                    spinSabeInicioSintoma.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta2_Mes(View.GONE);
                    textAnioInicioSintoma.setVisibility(View.GONE);
                    inputAnioInicioSintoma.setVisibility(View.GONE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinMesInicioSintoma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(MESINICIOSINTOMA_CONS, getString(R.string.mesInicioSintoma), mesInicioSintoma, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                mesInicioSintoma = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadecidoCovid19.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECIDOCOVID19_CONS, getString(R.string.padecidoCovid19), padecidoCovid19, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padecidoCovid19 = mr.getCatKey();
                if(padecidoCovid19.equals(Constants.YESKEYSND)){ //vaya a pregunta 4
                    spinPadecidoCovid19.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta4(View.VISIBLE);
                    MostrarOcultarPregunta5(View.VISIBLE);
                    MostrarOcultarPregunta15(View.VISIBLE);
                    MostrarOcultarPregunta18(View.VISIBLE);
                    MostrarOcultarPregunta19(View.VISIBLE);
                    if (participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) {//solo si es mujer entre 18 y 50
                        MostrarOcultarPregunta25(View.VISIBLE);
                        MostrarOcultarPregunta26(View.VISIBLE);
                    } else {
                        MostrarOcultarPregunta25(View.GONE);
                        MostrarOcultarPregunta26(View.GONE);
                    }
                }
                else{ //vaya a pregunta 20
                    spinPadecidoCovid19.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta4(View.GONE);
                    MostrarOcultarPregunta5(View.GONE);
                    MostrarOcultarPregunta15(View.GONE);
                    MostrarOcultarPregunta18(View.GONE);
                    MostrarOcultarPregunta19(View.GONE);
                    MostrarOcultarPregunta25(View.GONE);
                    MostrarOcultarPregunta26(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinConoceLugarExposicion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(CONOCELUGAREXPOSICION_CONS, getString(R.string.conoceLugarExposicion), conoceLugarExposicion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                conoceLugarExposicion = mr.getCatKey();
                if(conoceLugarExposicion.equals(Constants.YESKEYSND)){
                    spinConoceLugarExposicion.setBackgroundColor(Color.RED);
                    textLugarExposicion.setVisibility(View.VISIBLE);
                    inputLugarExposicion.setVisibility(View.VISIBLE);
                }
                else{
                    spinConoceLugarExposicion.setBackgroundColor(Color.WHITE);
                    inputLugarExposicion.setText("");
                    textLugarExposicion.setVisibility(View.GONE);
                    inputLugarExposicion.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinBuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(BUSCOAYUDA_CONS, getString(R.string.buscoAyuda), buscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                buscoAyuda = mr.getCatKey();
                if(buscoAyuda.equals(Constants.YESKEYSND)){//vaya a pregunta 6
                    spinBuscoAyuda.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta6(View.VISIBLE);
                    MostrarOcultarPregunta7(View.VISIBLE);
                    MostrarOcultarPregunta8(View.VISIBLE);

                }
                else{ //vaya a pregunta 15
                    spinBuscoAyuda.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta6(View.GONE);
                    MostrarOcultarPregunta7(View.GONE);
                    MostrarOcultarPregunta8(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDondeBuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DONDEBUSCOAYUDA_CONS, getString(R.string.dondeBuscoAyuda), dondeBuscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dondeBuscoAyuda = mr.getCatKey();
                if (dondeBuscoAyuda.equalsIgnoreCase("2")){ //Otro Centro Salud
                    textNombreCentro.setVisibility(View.VISIBLE);
                    inputNombreCentro.setVisibility(View.VISIBLE);
                    textNombreHospital.setVisibility(View.GONE);
                    inputNombreHospital.setVisibility(View.GONE);
                    MostrarOcultarPregunta6A(View.VISIBLE);
                } else if (dondeBuscoAyuda.equalsIgnoreCase("3")){ //Hospital
                    textNombreCentro.setVisibility(View.GONE);
                    inputNombreCentro.setVisibility(View.GONE);
                    textNombreHospital.setVisibility(View.VISIBLE);
                    inputNombreHospital.setVisibility(View.VISIBLE);
                    MostrarOcultarPregunta6A(View.VISIBLE);
                } else {
                    inputNombreCentro.setText("");
                    inputNombreHospital.setText("");
                    textNombreCentro.setVisibility(View.GONE);
                    inputNombreCentro.setVisibility(View.GONE);
                    textNombreHospital.setVisibility(View.GONE);
                    inputNombreHospital.setVisibility(View.GONE);
                    if (dondeBuscoAyuda.isEmpty() || dondeBuscoAyuda.equalsIgnoreCase("5")) //No busco ayuda
                        MostrarOcultarPregunta6A(View.GONE);
                    else
                        MostrarOcultarPregunta6A(View.VISIBLE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinRecibioSeguimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(RECIBIOSEGUIMIENTO_CONS, getString(R.string.recibioSeguimiento), recibioSeguimiento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                recibioSeguimiento = mr.getCatKey();
                if(recibioSeguimiento.equals(Constants.YESKEYSND)){
                    spinRecibioSeguimiento.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta6ASi(View.VISIBLE);
                }
                else{
                    spinRecibioSeguimiento.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta6ASi(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinTmpDespuesBuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(TMPDESPUESBUSCOAYUDA_CONS, getString(R.string.tmpDespuesBuscoAyuda), tmpDespuesBuscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                tmpDespuesBuscoAyuda = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinTipoSeguimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(TIPOSEGUIMIENTO_CONS, getString(R.string.tipoSeguimiento), tipoSeguimiento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                tipoSeguimiento = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinUnaNocheHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(UNANOCHEHOSPITAL_CONS, getString(R.string.unaNocheHospital), unaNocheHospital, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                unaNocheHospital = mr.getCatKey();
                if(unaNocheHospital.equals(Constants.YESKEYSND)){//vaya a pregunta 9
                    spinUnaNocheHospital.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta9(View.VISIBLE);
                    MostrarOcultarPregunta10(View.VISIBLE);
                    MostrarOcultarPregunta11(View.VISIBLE);
                    MostrarOcultarPregunta12(View.VISIBLE);
                    MostrarOcultarPregunta13(View.VISIBLE);
                    MostrarOcultarPregunta14(View.VISIBLE);
                }
                else{ //vaya a pregunta 15
                    spinUnaNocheHospital.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta9(View.GONE);
                    MostrarOcultarPregunta10(View.GONE);
                    MostrarOcultarPregunta11(View.GONE);
                    MostrarOcultarPregunta12(View.GONE);
                    MostrarOcultarPregunta13(View.GONE);
                    MostrarOcultarPregunta14(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinSabeCuantasNoches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SABECUANTASNOCHES_CONS, getString(R.string.sabeCuantasNoches), sabeCuantasNoches, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                sabeCuantasNoches = mr.getCatKey();
                if(sabeCuantasNoches.equals(Constants.YESKEYSND)){
                    spinSabeCuantasNoches.setBackgroundColor(Color.RED);
                    textCuantasNochesHosp.setVisibility(View.VISIBLE);
                    inputCuantasNochesHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinSabeCuantasNoches.setBackgroundColor(Color.WHITE);
                    textCuantasNochesHosp.setVisibility(View.GONE);
                    inputCuantasNochesHosp.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinSabeFechaAdmision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SABEFECHAADMISION_CONS, getString(R.string.sabeFechaAdmision), sabeFechaAdmision, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                sabeFechaAdmision = mr.getCatKey();
                if(sabeFechaAdmision.equals(Constants.YESKEYSND)){
                    spinSabeFechaAdmision.setBackgroundColor(Color.RED);
                    textFechaAdmisionHosp.setVisibility(View.VISIBLE);
                    inputFechaAdmisionHosp.setVisibility(View.VISIBLE);
                    imbFechaAdmisionHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinSabeFechaAdmision.setBackgroundColor(Color.WHITE);
                    textFechaAdmisionHosp.setVisibility(View.GONE);
                    inputFechaAdmisionHosp.setVisibility(View.GONE);
                    imbFechaAdmisionHosp.setVisibility(View.GONE);
                    fechaAdmisionHosp = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinSabeFechaAlta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SABEFECHAALTA_CONS, getString(R.string.sabeFechaAlta), sabeFechaAlta, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                sabeFechaAlta = mr.getCatKey();
                if(sabeFechaAlta.equals(Constants.YESKEYSND)){
                    spinSabeFechaAlta.setBackgroundColor(Color.RED);
                    textFechaAltaHosp.setVisibility(View.VISIBLE);
                    inputFechaAltaHosp.setVisibility(View.VISIBLE);
                    imbFechaAltaHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinSabeFechaAlta.setBackgroundColor(Color.WHITE);
                    textFechaAltaHosp.setVisibility(View.GONE);
                    inputFechaAltaHosp.setVisibility(View.GONE);
                    imbFechaAltaHosp.setVisibility(View.GONE);
                    fechaAltaHosp = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinEstuvoUCI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(ESTUVOUCI_CONS, getString(R.string.estuvoUCI), estuvoUCI, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                estuvoUCI = mr.getCatKey();
                if(estuvoUCI.equals(Constants.YESKEYSND)){
                    spinEstuvoUCI.setBackgroundColor(Color.RED);
                    textFueIntubado.setVisibility(View.VISIBLE);
                    spinFueIntubado.setVisibility(View.VISIBLE);
                }
                else{
                    spinEstuvoUCI.setBackgroundColor(Color.WHITE);
                    spinFueIntubado.setSelection(0, false);
                    textFueIntubado.setVisibility(View.GONE);
                    spinFueIntubado.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinUtilizoOxigeno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(UTILIZOOXIGENO_CONS, getString(R.string.utilizoOxigeno), utilizoOxigeno, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                utilizoOxigeno = mr.getCatKey();
                if(utilizoOxigeno.equals(Constants.YESKEYSND)){
                    spinUtilizoOxigeno.setBackgroundColor(Color.RED);
                }
                else{
                    spinUtilizoOxigeno.setBackgroundColor(Color.WHITE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFueIntubado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FUEINTUBADO_CONS, getString(R.string.fueIntubado), fueIntubado, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                fueIntubado = mr.getCatKey();
                if(fueIntubado.equals(Constants.YESKEYSND)){
                    spinFueIntubado.setBackgroundColor(Color.RED);
                }
                else{
                    spinFueIntubado.setBackgroundColor(Color.WHITE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinRecuperadoCovid19.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(RECUPERADOCOVID19_CONS, getString(R.string.recuperadoCovid19), recuperadoCovid19, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                recuperadoCovid19 = mr.getCatKey();
                if(recuperadoCovid19.equals(Constants.YESKEYSND)){
                    spinRecuperadoCovid19.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta16(View.GONE);
                    MostrarOcultarPregunta17(View.VISIBLE);
                }
                else{
                    spinRecuperadoCovid19.setBackgroundColor(Color.WHITE);
                    if(recuperadoCovid19.isEmpty()) MostrarOcultarPregunta16(View.GONE);
                    else MostrarOcultarPregunta16(View.VISIBLE);
                    MostrarOcultarPregunta17(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinSabeTiempoRecuperacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SABETIEMPORECUPERACION_CONS, getString(R.string.sabeTiempoRecuperacion), sabeTiempoRecuperacion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                sabeTiempoRecuperacion = mr.getCatKey();
                if(sabeTiempoRecuperacion.equals(Constants.YESKEYSND)){
                    spinSabeTiempoRecuperacion.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta17A(View.VISIBLE);
                }
                else{
                    spinSabeTiempoRecuperacion.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta17A(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinTiempoRecuperacionEn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(TIEMPORECUPERACIONEN_CONS, getString(R.string.tiempoRecuperacionEn), tiempoRecuperacionEn, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                tiempoRecuperacionEn = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinSeveridadEnfermedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SEVERIDADENFERMEDAD_CONS, getString(R.string.severidadEnfermedad), severidadEnfermedad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                severidadEnfermedad = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinTomoMedicamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(TOMOMEDICAMENTO_CONS, getString(R.string.tomoMedicamento), tomoMedicamento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                tomoMedicamento = mr.getCatKey();
                if(tomoMedicamento.equals(Constants.YESKEYSND)){
                    spinTomoMedicamento.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta19A(View.VISIBLE);
                }
                else{
                    spinTomoMedicamento.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta19A(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /*SINTOMAS ACTUALES*/
        spinFebricula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FEBRICULA_CONS, getString(R.string.febricula), febricula, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                febricula = mr.getCatKey();
                spinFebricula.setBackgroundColor(febricula.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinCansancio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(CANSANCIO_CONS, getString(R.string.cansancio), cansancio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                cansancio = mr.getCatKey();
                spinCansancio.setBackgroundColor(cansancio.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorCabeza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORCABEZA_CONS, getString(R.string.dolorCabeza), dolorCabeza, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorCabeza = mr.getCatKey();
                spinDolorCabeza.setBackgroundColor(dolorCabeza.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPerdidaOlfato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PERDIDAOLFATO_CONS, getString(R.string.perdidaOlfato), perdidaOlfato, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                perdidaOlfato = mr.getCatKey();
                spinPerdidaOlfato.setBackgroundColor(perdidaOlfato.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPerdidaGusto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PERDIDAGUSTO_CONS, getString(R.string.perdidaGusto), perdidaGusto, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                perdidaGusto = mr.getCatKey();
                spinPerdidaGusto.setBackgroundColor(perdidaGusto.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinTos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(TOS_CONS, getString(R.string.tos), tos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                tos = mr.getCatKey();
                spinTos.setBackgroundColor(tos.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDificultadRespirar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DIFICULTADRESPIRAR_CONS, getString(R.string.dificultadRespirar), dificultadRespirar, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dificultadRespirar = mr.getCatKey();
                spinDificultadRespirar.setBackgroundColor(dificultadRespirar.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORPECHO_CONS, getString(R.string.dolorPecho), dolorPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorPecho = mr.getCatKey();
                spinDolorPecho.setBackgroundColor(dolorPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPalpitaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PALPITACIONES_CONS, getString(R.string.palpitaciones), palpitaciones, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                palpitaciones = mr.getCatKey();
                spinPalpitaciones.setBackgroundColor(palpitaciones.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorArticulaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORARTICULACIONES_CONS, getString(R.string.dolorArticulaciones), dolorArticulaciones, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorArticulaciones = mr.getCatKey();
                spinDolorArticulaciones.setBackgroundColor(dolorArticulaciones.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinParalisis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PARALISIS_CONS, getString(R.string.paralisis), paralisis, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                paralisis = mr.getCatKey();
                spinParalisis.setBackgroundColor(paralisis.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinMareos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(MAREOS_CONS, getString(R.string.mareos), mareos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                mareos = mr.getCatKey();
                spinMareos.setBackgroundColor(mareos.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPensamientoNublado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PENSAMIENTONUBLADO_CONS, getString(R.string.pensamientoNublado), pensamientoNublado, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                pensamientoNublado = mr.getCatKey();
                spinPensamientoNublado.setBackgroundColor(pensamientoNublado.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinProblemasDormir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PROBLEMASDORMIR_CONS, getString(R.string.problemasDormir), problemasDormir, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                problemasDormir = mr.getCatKey();
                spinProblemasDormir.setBackgroundColor(problemasDormir.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDepresion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DEPRESION_CONS, getString(R.string.depresion), depresion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                depresion = mr.getCatKey();
                spinDepresion.setBackgroundColor(depresion.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinOtrosSintomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(OTROSSINTOMAS_CONS, getString(R.string.otrosSintomas), otrosSintomas, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                otrosSintomas = mr.getCatKey();
                if(otrosSintomas.equals(Constants.YESKEYSND)){
                    spinOtrosSintomas.setBackgroundColor(Color.RED);
                    textCualesSintomas.setVisibility(View.VISIBLE);
                    inputCualesSintomas.setVisibility(View.VISIBLE);
                }
                else{
                    spinOtrosSintomas.setBackgroundColor(Color.WHITE);
                    textCualesSintomas.setVisibility(View.GONE);
                    inputCualesSintomas.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*FIN SINTOMAS ACTUALES*/

        /*CONDICIONES*/
        spinPadeceEnfisema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECEENFISEMA_CONS, getString(R.string.padeceEnfisema), padeceEnfisema, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceEnfisema = mr.getCatKey();
                spinPadeceEnfisema.setBackgroundColor(padeceEnfisema.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinPadeceAsma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECEASMA_CONS, getString(R.string.padeceAsma), padeceAsma, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceAsma = mr.getCatKey();
                spinPadeceAsma.setBackgroundColor(padeceAsma.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadeceDiabetes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECEDIABETES_CONS, getString(R.string.padeceDiabetes), padeceDiabetes, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceDiabetes = mr.getCatKey();
                spinPadeceDiabetes.setBackgroundColor(padeceDiabetes.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadeceEnfCoronaria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECEENFCORONARIA_CONS, getString(R.string.padeceEnfCoronaria), padeceEnfCoronaria, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceEnfCoronaria = mr.getCatKey();
                spinPadeceEnfCoronaria.setBackgroundColor(padeceEnfCoronaria.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadecePresionAlta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECEPRESIONALTA_CONS, getString(R.string.padecePresionAlta), padecePresionAlta, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padecePresionAlta = mr.getCatKey();
                spinPadecePresionAlta.setBackgroundColor(padecePresionAlta.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadeceEnfHigado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECEENFHIGADO_CONS, getString(R.string.padeceEnfHigado), padeceEnfHigado, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceEnfHigado = mr.getCatKey();
                spinPadeceEnfHigado.setBackgroundColor(padeceEnfHigado.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadeceEnfRenal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECEENFRENAL_CONS, getString(R.string.padeceEnfRenal), padeceEnfRenal, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceEnfRenal = mr.getCatKey();
                spinPadeceEnfRenal.setBackgroundColor(padeceEnfRenal.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadeceInfartoDerrameCer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECEINFARTODERRAMECER_CONS, getString(R.string.padeceInfartoDerrameCer), padeceInfartoDerrameCer, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceInfartoDerrameCer = mr.getCatKey();
                spinPadeceInfartoDerrameCer.setBackgroundColor(padeceInfartoDerrameCer.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadeceCancer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECECANCER_CONS, getString(R.string.padeceCancer), padeceCancer, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceCancer = mr.getCatKey();
                spinPadeceCancer.setBackgroundColor(padeceCancer.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadeceCondicionInmuno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECECONDICIONINMUNO_CONS, getString(R.string.padeceCondicionInmuno), padeceCondicionInmuno, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceCondicionInmuno = mr.getCatKey();
                spinPadeceCondicionInmuno.setBackgroundColor(padeceCondicionInmuno.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadeceEnfAutoinmune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECEENFAUTOINMUNE_CONS, getString(R.string.padeceEnfAutoinmune), padeceEnfAutoinmune, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceEnfAutoinmune = mr.getCatKey();
                spinPadeceEnfAutoinmune.setBackgroundColor(padeceEnfAutoinmune.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadeceDiscapacidadFis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECEDISCAPACIDADFIS_CONS, getString(R.string.padeceDiscapacidadFis), padeceDiscapacidadFis, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceDiscapacidadFis = mr.getCatKey();
                spinPadeceDiscapacidadFis.setBackgroundColor(padeceDiscapacidadFis.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadeceCondPsicPsiq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECECONDPSICPSIQ_CONS, getString(R.string.padeceCondPsicPsiq), padeceCondPsicPsiq, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceCondPsicPsiq = mr.getCatKey();
                spinPadeceCondPsicPsiq.setBackgroundColor(padeceCondPsicPsiq.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPadeceOtraCondicion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PADECEOTRACONDICION_CONS, getString(R.string.padeceOtraCondicion), padeceOtraCondicion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                padeceOtraCondicion = mr.getCatKey();
                if(padeceOtraCondicion.equals(Constants.YESKEYSND)){
                    spinPadeceOtraCondicion.setBackgroundColor(Color.RED);
                    textQueOtraCondicion.setVisibility(View.VISIBLE);
                    inputQueOtraCondicion.setVisibility(View.VISIBLE);
                }
                else{
                    spinPadeceOtraCondicion.setBackgroundColor(Color.WHITE);
                    textQueOtraCondicion.setVisibility(View.GONE);
                    inputQueOtraCondicion.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*Fin CONDICIONES*/

        spinFumado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FUMADO_CONS, getString(R.string.fumado), fumado, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                fumado = mr.getCatKey();
                if(fumado.equals(Constants.YESKEYSND)){
                    spinFumado.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta22(View.VISIBLE);
                }
                else{
                    spinFumado.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta22(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFumadoCienCigarrillos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FUMADOCIENCIGARRILLOS_CONS, getString(R.string.fumadoCienCigarrillos), fumadoCienCigarrillos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                fumadoCienCigarrillos = mr.getCatKey();
                if(fumadoCienCigarrillos.isEmpty() || fumadoCienCigarrillos.equals(Constants.NOKEYSND)){
                    spinFumado.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta23(View.GONE);
                    MostrarOcultarPregunta24(View.GONE);
                }
                else{
                    if (padecidoCovid19 != null && !padecidoCovid19.isEmpty() && padecidoCovid19.equals(Constants.YESKEYSND)) {
                        MostrarOcultarPregunta23(View.VISIBLE);
                    }
                    MostrarOcultarPregunta24(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFumadoPrevioEnfermedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FUMADOPREVIOENFERMEDAD_CONS, getString(R.string.fumadoPrevioEnfermedad), fumadoPrevioEnfermedad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                fumadoPrevioEnfermedad = mr.getCatKey();
                spinFumadoPrevioEnfermedad.setBackgroundColor(fumadoPrevioEnfermedad.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFumaActualmente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FUMAACTUALMENTE_CONS, getString(R.string.fumaActualmente), fumaActualmente, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                fumaActualmente = mr.getCatKey();
                spinFumaActualmente.setBackgroundColor(fumaActualmente.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinEmbarazada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(EMBARAZADA_CONS, getString(R.string.embarazada), embarazada, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                embarazada = mr.getCatKey();
                if(embarazada.equals(Constants.YESKEYSND)){
                    spinEmbarazada.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta25A(View.VISIBLE);
                    MostrarOcultarPregunta25B(View.VISIBLE);
                }
                else{
                    spinEmbarazada.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta25A(View.GONE);
                    MostrarOcultarPregunta25B(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinRecuerdaSemanasEmb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(RECUERDASEMANASEMB_CONS, getString(R.string.recuerdaSemanasEmb), recuerdaSemanasEmb, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                recuerdaSemanasEmb = mr.getCatKey();
                if(recuerdaSemanasEmb.equals(Constants.YESKEYSND)){
                    spinRecuerdaSemanasEmb.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta25A1(View.VISIBLE);
                }
                else{
                    spinRecuerdaSemanasEmb.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta25A1(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFinalEmbarazo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FINALEMBARAZO_CONS, getString(R.string.finalEmbarazo), finalEmbarazo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                finalEmbarazo = mr.getCatKey();
                if(finalEmbarazo.equals(Constants.YESKEYSND)){
                    spinFinalEmbarazo.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta25B1(View.VISIBLE);
                }
                else{
                    spinFinalEmbarazo.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta25B1(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDabaPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DABAPECHO_CONS, getString(R.string.dabaPecho), dabaPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dabaPecho = mr.getCatKey();
                spinDabaPecho.setBackgroundColor(dabaPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinTrabajadorSalud.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(TRABAJADORSALUD_CONS, getString(R.string.trabajadorSalud), trabajadorSalud, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                trabajadorSalud = mr.getCatKey();
                spinTrabajadorSalud.setBackgroundColor(trabajadorSalud.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mSaveView = (Button) getActivity().findViewById(R.id.save_button);
        mSaveView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                try {
                    if(validarEntrada()){
                        new SaveDataTask().execute();
                    }
                } catch (ParseException e) {
                    Toast.makeText(getActivity(), e.toString() ,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private boolean tuvoAlMenosUnSintoma(){
        if (feb20Febricula!=null && feb20Febricula.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20Fiebre!=null && feb20Fiebre.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20TemblorEscalofrio!=null && feb20TemblorEscalofrio.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20Escalofrio!=null && feb20Escalofrio.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20DolorMuscular!=null && feb20DolorMuscular.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20DolorArticular!=null && feb20DolorArticular.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20SecresionNasal!=null && feb20SecresionNasal.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20DolorGarganta!=null && feb20DolorGarganta.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20Tos!=null && feb20Tos.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20DificultadResp!=null && feb20DificultadResp.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20DolorPecho!=null && feb20DolorPecho.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20NauseasVomito!=null && feb20NauseasVomito.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20DolorCabeza!=null && feb20DolorCabeza.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20DolorAbdominal!=null && feb20DolorAbdominal.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20Diarrea!=null && feb20Diarrea.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20DificultadDormir!=null && feb20DificultadDormir.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20Debilidad!=null && feb20Debilidad.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20PerdidaOlfatoGusto!=null && feb20PerdidaOlfatoGusto.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20Mareo!=null && feb20Mareo.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20Sarpullido!=null && feb20Sarpullido.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20Desmayo!=null && feb20Desmayo.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (feb20QuedoCama!=null && feb20QuedoCama.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        return false;
    }
    private void MostrarOcultarPregunta2(){
        if (tuvoAlMenosUnSintoma()) {
            spinSabeInicioSintoma.setVisibility(View.VISIBLE);
            textSabeInicioSintoma.setVisibility(View.VISIBLE);            
        }else {
            spinSabeInicioSintoma.setSelection(0, false);
            spinSabeInicioSintoma.setVisibility(View.GONE);
            textSabeInicioSintoma.setVisibility(View.GONE);
        }        
    }
    
    private void MostrarOcultarPregunta2_Mes(int estado){
        if (estado == View.GONE) spinMesInicioSintoma.setSelection(0, false);
        textMesInicioSintoma.setVisibility(estado);
        spinMesInicioSintoma.setVisibility(estado);
    }

    private void MostrarOcultarPregunta4(int estado){
        if (estado == View.GONE) spinConoceLugarExposicion.setSelection(0, false);
        textConoceLugarExposicion.setVisibility(estado);
        spinConoceLugarExposicion.setVisibility(estado);
    }

    private void MostrarOcultarPregunta5(int estado){
        if (estado == View.GONE) spinBuscoAyuda.setSelection(0, false);
        textBuscoAyuda.setVisibility(estado);
        spinBuscoAyuda.setVisibility(estado);
    }

    private void MostrarOcultarPregunta6(int estado){
        if (estado == View.GONE) spinDondeBuscoAyuda.setSelection(0, false);
        textDondeBuscoAyuda.setVisibility(estado);
        spinDondeBuscoAyuda.setVisibility(estado);
    }

    private void MostrarOcultarPregunta6A(int estado){
        if (estado == View.GONE) spinRecibioSeguimiento.setSelection(0, false);
        textRecibioSeguimiento.setVisibility(estado);
        spinRecibioSeguimiento.setVisibility(estado);
    }

    private void MostrarOcultarPregunta6ASi(int estado){
        if (estado == View.GONE) spinTipoSeguimiento.setSelection(0, false);
        textTipoSeguimiento.setVisibility(estado);
        spinTipoSeguimiento.setVisibility(estado);
    }

    private void MostrarOcultarPregunta7(int estado){
        if (estado == View.GONE) spinTmpDespuesBuscoAyuda.setSelection(0, false);
        textTmpDespuesBuscoAyuda.setVisibility(estado);
        spinTmpDespuesBuscoAyuda.setVisibility(estado);
    }

    private void MostrarOcultarPregunta8(int estado){
        if (estado == View.GONE) spinUnaNocheHospital.setSelection(0, false);
        textUnaNocheHospital.setVisibility(estado);
        spinUnaNocheHospital.setVisibility(estado);
    }

    private void MostrarOcultarPregunta9(int estado){
        if (estado == View.GONE) inputQueHospital.setText("");
        textQueHospital.setVisibility(estado);
        inputQueHospital.setVisibility(estado);
    }

    private void MostrarOcultarPregunta10(int estado){
        if (estado == View.GONE) spinSabeCuantasNoches.setSelection(0, false);
        textSabeCuantasNoches.setVisibility(estado);
        spinSabeCuantasNoches.setVisibility(estado);
    }

    private void MostrarOcultarPregunta11(int estado){
        if (estado == View.GONE) spinSabeFechaAdmision.setSelection(0, false);
        textSabeFechaAdmision.setVisibility(estado);
        spinSabeFechaAdmision.setVisibility(estado);
    }

    private void MostrarOcultarPregunta12(int estado){
        if (estado == View.GONE) spinSabeFechaAlta.setSelection(0, false);
        textSabeFechaAlta.setVisibility(estado);
        spinSabeFechaAlta.setVisibility(estado);
    }

    private void MostrarOcultarPregunta13(int estado){
        if (estado == View.GONE) spinUtilizoOxigeno.setSelection(0, false);
        textUtilizoOxigeno.setVisibility(estado);
        spinUtilizoOxigeno.setVisibility(estado);
    }

    private void MostrarOcultarPregunta14(int estado){
        if (estado == View.GONE) spinEstuvoUCI.setSelection(0, false);
        textEstuvoUCI.setVisibility(estado);
        spinEstuvoUCI.setVisibility(estado);
    }

    private void MostrarOcultarPregunta15(int estado){
        if (estado == View.GONE) spinRecuperadoCovid19.setSelection(0, false);
        textRecuperadoCovid19.setVisibility(estado);
        spinRecuperadoCovid19.setVisibility(estado);
    }

    private void MostrarOcultarPregunta16(int estado){
        if (estado == View.GONE) {
            spinFebricula.setSelection(0, false);
            spinCansancio.setSelection(0, false);
            spinDolorCabeza.setSelection(0, false);
            spinPerdidaOlfato.setSelection(0, false);
            spinPerdidaGusto.setSelection(0, false);
            spinTos.setSelection(0, false);
            spinDificultadRespirar.setSelection(0, false);
            spinDolorPecho.setSelection(0, false);
            spinPalpitaciones.setSelection(0, false);
            spinDolorArticulaciones.setSelection(0, false);
            spinParalisis.setSelection(0, false);
            spinMareos.setSelection(0, false);
            spinPensamientoNublado.setSelection(0, false);
            spinProblemasDormir.setSelection(0, false);
            spinDepresion.setSelection(0, false);
            spinOtrosSintomas.setSelection(0, false);

        }
        textQueSintomasPresenta.setVisibility(estado);
        spinFebricula.setVisibility(estado);
        spinCansancio.setVisibility(estado);
        spinDolorCabeza.setVisibility(estado);
        spinPerdidaOlfato.setVisibility(estado);
        spinPerdidaGusto.setVisibility(estado);
        spinTos.setVisibility(estado);
        spinDificultadRespirar.setVisibility(estado);
        spinDolorPecho.setVisibility(estado);
        spinPalpitaciones.setVisibility(estado);
        spinDolorArticulaciones.setVisibility(estado);
        spinParalisis.setVisibility(estado);
        spinMareos.setVisibility(estado);
        spinPensamientoNublado.setVisibility(estado);
        spinProblemasDormir.setVisibility(estado);
        spinDepresion.setVisibility(estado);
        spinOtrosSintomas.setVisibility(estado);
        textFebricula.setVisibility(estado);
        textCansancio.setVisibility(estado);
        textDolorCabeza.setVisibility(estado);
        textPerdidaOlfato.setVisibility(estado);
        textPerdidaGusto.setVisibility(estado);
        textTos.setVisibility(estado);
        textDificultadRespirar.setVisibility(estado);
        textDolorPecho.setVisibility(estado);
        textPalpitaciones.setVisibility(estado);
        textDolorArticulaciones.setVisibility(estado);
        textParalisis.setVisibility(estado);
        textMareos.setVisibility(estado);
        textPensamientoNublado.setVisibility(estado);
        textProblemasDormir.setVisibility(estado);
        textDepresion.setVisibility(estado);
        textOtrosSintomas.setVisibility(estado);
    }

    private void MostrarOcultarPregunta17(int estado){
        if (estado == View.GONE) spinSabeTiempoRecuperacion.setSelection(0, false);
        textSabeTiempoRecuperacion.setVisibility(estado);
        spinSabeTiempoRecuperacion.setVisibility(estado);
    }

    private void MostrarOcultarPregunta17A(int estado){
        if (estado == View.GONE) spinTiempoRecuperacionEn.setSelection(0, false);
        textTiempoRecuperacion.setVisibility(estado);
        inputTiempoRecuperacion.setVisibility(estado);
        spinTiempoRecuperacionEn.setVisibility(estado);
    }

    private void MostrarOcultarPregunta18(int estado){
        if (estado == View.GONE) spinSeveridadEnfermedad.setSelection(0, false);
        textSeveridadEnfermedad.setVisibility(estado);
        spinSeveridadEnfermedad.setVisibility(estado);
    }

    private void MostrarOcultarPregunta19(int estado){
        if (estado == View.GONE) spinTomoMedicamento.setSelection(0, false);
        textTomoMedicamento.setVisibility(estado);
        spinTomoMedicamento.setVisibility(estado);
    }

    private void MostrarOcultarPregunta19A(int estado){
        textQueMedicamento.setVisibility(estado);
        spinQueMedicamento.setVisibility(estado);
    }

    private void MostrarOcultarPregunta22(int estado){
        if (estado == View.GONE) spinFumadoCienCigarrillos.setSelection(0, false);
        textFumadoCienCigarrillos.setVisibility(estado);
        spinFumadoCienCigarrillos.setVisibility(estado);
    }

    private void MostrarOcultarPregunta23(int estado){
        if (estado == View.GONE) spinFumadoPrevioEnfermedad.setSelection(0, false);
        textFumadoPrevioEnfermedad.setVisibility(estado);
        spinFumadoPrevioEnfermedad.setVisibility(estado);
    }

    private void MostrarOcultarPregunta24(int estado){
        if (estado == View.GONE) spinFumaActualmente.setSelection(0, false);
        textFumaActualmente.setVisibility(estado);
        spinFumaActualmente.setVisibility(estado);
    }

    private void MostrarOcultarPregunta25(int estado){
        if (estado == View.GONE) spinEmbarazada.setSelection(0, false);
        textEmbarazada.setVisibility(estado);
        spinEmbarazada.setVisibility(estado);
    }

    private void MostrarOcultarPregunta25A(int estado){
        if (estado == View.GONE) spinRecuerdaSemanasEmb.setSelection(0, false);
        textRecuerdaSemanasEmb.setVisibility(estado);
        spinRecuerdaSemanasEmb.setVisibility(estado);
    }

    private void MostrarOcultarPregunta25A1(int estado){
        textSemanasEmbarazo.setVisibility(estado);
        inputSemanasEmbarazo.setVisibility(estado);
    }

    private void MostrarOcultarPregunta25B(int estado){
        if (estado == View.GONE) spinFinalEmbarazo.setSelection(0, false);
        textFinalEmbarazo.setVisibility(estado);
        spinFinalEmbarazo.setVisibility(estado);
    }

    private void MostrarOcultarPregunta25B1(int estado){
        textOtroFinalEmbarazo.setVisibility(estado);
        inputOtroFinalEmbarazo.setVisibility(estado);
    }

    private void MostrarOcultarPregunta26(int estado){
        if (estado == View.GONE) spinDabaPecho.setSelection(0, false);
        textDabaPecho.setVisibility(estado);
        spinDabaPecho.setVisibility(estado);
    }

    private void MostrarOcultarPregunta27(int estado){
        if (estado == View.GONE) spinTrabajadorSalud.setSelection(0, false);
        textTrabajadorSalud.setVisibility(estado);
        spinTrabajadorSalud.setVisibility(estado);
    }

    private void createConfirmDialog(final String pregunta, String textoPregunta, String valorActual, final String nuevoValor, final String nuevoTexto, final int position){
        if (mostrarConfirmacion && !nuevoValor.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getActivity().getString(R.string.confirmarValor, nuevoTexto, textoPregunta));
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    mostrarConfirmacion = true;
                    switch (pregunta){
                        case FEB20FEBRICULA_CONS:
                            feb20Febricula = nuevoValor;
                            feb20FebriculaPos = position;
                            break;
                        case FEB20FIEBRE_CONS:
                            feb20Fiebre = nuevoValor;
                            feb20FiebrePos = position;
                            break;
                        case FEB20ESCALOFRIO_CONS:
                            feb20Escalofrio = nuevoValor;
                            feb20EscalofrioPos = position;
                            break;
                        case FEB20TEMBLORESCALOFRIO_CONS:
                            feb20TemblorEscalofrio = nuevoValor;
                            feb20TemblorEscalofrioPos = position;
                            break;
                        case FEB20DOLORMUSCULAR_CONS:
                            feb20DolorMuscular = nuevoValor;
                            feb20DolorMuscularPos = position;
                            break;
                        case FEB20DOLORARTICULAR_CONS:
                            feb20DolorArticular = nuevoValor;
                            feb20DolorArticularPos = position;
                            break;
                        case FEB20SECRESIONNASAL_CONS:
                            feb20SecresionNasal = nuevoValor;
                            feb20SecresionNasalPos = position;
                            break;
                        case FEB20DOLORGARGANTA_CONS:
                            feb20DolorGargantaPos = position;
                            feb20DolorGarganta = nuevoValor;
                            break;
                        case FEB20TOS_CONS:
                            feb20TosPos = position;
                            feb20Tos = nuevoValor;
                            break;
                        case FEB20DIFICULTADRESP_CONS:
                            feb20DificultadResp = nuevoValor;
                            feb20DificultadRespPos = position;
                            break;
                        case FEB20DOLORPECHO_CONS:
                            feb20DolorPecho = nuevoValor;
                            feb20DolorPechoPos = position;
                            break;
                        case FEB20NAUSEASVOMITO_CONS:
                            feb20NauseasVomito = nuevoValor;
                            feb20NauseasVomitoPos = position;
                            break;
                        case FEB20DOLORCABEZA_CONS:
                            feb20DolorCabeza = nuevoValor;
                            feb20DolorCabezaPos = position;
                            break;
                        case FEB20DOLORABDOMINAL_CONS:
                            feb20DolorAbdominal = nuevoValor;
                            feb20DolorAbdominalPos = position;
                            break;
                        case FEB20DIARREA_CONS:
                            feb20Diarrea = nuevoValor;
                            feb20DiarreaPos = position;
                            break;
                        case FEB20DIFICULTADDORMIR_CONS:
                            feb20DificultadDormirPos = position;
                            feb20DificultadDormir = nuevoValor;
                            break;
                        case FEB20DEBILIDAD_CONS:
                            feb20Debilidad = nuevoValor;
                            feb20DebilidadPos = position;
                            break;
                        case FEB20PERDIDAOLFATOGUSTO_CONS:
                            feb20PerdidaOlfatoGusto = nuevoValor;
                            feb20PerdidaOlfatoGustoPos = position;
                            break;
                        case FEB20MAREO_CONS:
                            feb20Mareo = nuevoValor;
                            feb20MareoPos = position;
                            break;
                        case FEB20SARPULLIDO_CONS:
                            feb20Sarpullido = nuevoValor;
                            feb20SarpullidoPos = position;
                            break;
                        case FEB20DESMAYO_CONS:
                            feb20Desmayo = nuevoValor;
                            feb20DesmayoPos = position;
                            break;
                        case FEB20QUEDOCAMA_CONS:
                            feb20QuedoCama = nuevoValor;
                            feb20QuedoCamaPos = position;
                            break;
                        case SABEFIS_CONS:
                            sabeFIS = nuevoValor;
                            sabeFISPos = position;
                            break;
                        case FIS_CONS:
                            fis = nuevoValor;
                            fisPos = position;
                            break;
                        case MESINICIOSINTOMA_CONS:
                            mesInicioSintoma = nuevoValor;
                            mesInicioSintomaPos = position;
                            break;
                        case ANIOINICIOSINTOMA_CONS:
                            anioInicioSintoma = nuevoValor;
                            anioInicioSintomaPos = position;
                            break;
                        case PADECIDOCOVID19_CONS:
                            padecidoCovid19 = nuevoValor;
                            padecidoCovid19Pos = position;
                            break;
                        case CONOCELUGAREXPOSICION_CONS:
                            conoceLugarExposicion = nuevoValor;
                            conoceLugarExposicionPos = position;
                            break;
                        case LUGAREXPOSICION_CONS:
                            lugarExposicion = nuevoValor;
                            lugarExposicionPos = position;
                            break;
                        case BUSCOAYUDA_CONS:
                            buscoAyuda = nuevoValor;
                            buscoAyudaPos = position;
                            break;
                        case DONDEBUSCOAYUDA_CONS:
                            dondeBuscoAyuda = nuevoValor;
                            dondeBuscoAyudaPos = position;
                            break;
                        case NOMBRECENTROSALUD_CONS:
                            nombreCentroSalud = nuevoValor;
                            nombreCentroSaludPos = position;
                            break;
                        case NOMBREHOSPITAL_CONS:
                            nombreHospital = nuevoValor;
                            nombreHospitalPos = position;
                            break;
                        case RECIBIOSEGUIMIENTO_CONS:
                            recibioSeguimiento = nuevoValor;
                            recibioSeguimientoPos = position;
                            break;
                        case TIPOSEGUIMIENTO_CONS:
                            tipoSeguimiento = nuevoValor;
                            tipoSeguimientoPos = position;
                            break;
                        case TMPDESPUESBUSCOAYUDA_CONS:
                            tmpDespuesBuscoAyuda = nuevoValor;
                            tmpDespuesBuscoAyudaPos = position;
                            break;
                        case UNANOCHEHOSPITAL_CONS:
                            unaNocheHospital = nuevoValor;
                            unaNocheHospitalPos = position;
                            break;
                        case QUEHOSPITAL_CONS:
                            queHospital = nuevoValor;
                            queHospitalPos = position;
                            break;
                        case SABECUANTASNOCHES_CONS:
                            sabeCuantasNoches = nuevoValor;
                            sabeCuantasNochesPos = position;
                            break;
                        case CUANTASNOCHESHOSP_CONS:
                            cuantasNochesHosp = Integer.parseInt(nuevoValor);
                            cuantasNochesHospPos = position;
                            break;
                        case SABEFECHAADMISION_CONS:
                            sabeFechaAdmision = nuevoValor;
                            sabeFechaAdmisionPos = position;
                            break;
                        case FECHAADMISIONHOSP_CONS:
                            fechaAdmisionHosp = nuevoValor;
                            fechaAdmisionHospPos = position;
                            break;
                        case SABEFECHAALTA_CONS:
                            sabeFechaAlta = nuevoValor;
                            sabeFechaAltaPos = position;
                            break;
                        case FECHAALTAHOSP_CONS:
                            fechaAltaHosp = nuevoValor;
                            fechaAltaHospPos = position;
                            break;
                        case UTILIZOOXIGENO_CONS:
                            utilizoOxigeno = nuevoValor;
                            utilizoOxigenoPos = position;
                            break;
                        case ESTUVOUCI_CONS:
                            estuvoUCI = nuevoValor;
                            estuvoUCIPos = position;
                            break;
                        case FUEINTUBADO_CONS:
                            fueIntubado = nuevoValor;
                            fueIntubadoPos = position;
                            break;
                        case RECUPERADOCOVID19_CONS:
                            recuperadoCovid19 = nuevoValor;
                            recuperadoCovid19Pos = position;
                            break;
                        case FEBRICULA_CONS:
                            febricula = nuevoValor;
                            febriculaPos = position;
                            break;
                        case CANSANCIO_CONS:
                            cansancio = nuevoValor;
                            cansancioPos = position;
                            break;
                        case DOLORCABEZA_CONS:
                            dolorCabeza = nuevoValor;
                            dolorCabezaPos = position;
                            break;
                        case PERDIDAOLFATO_CONS:
                            perdidaOlfato = nuevoValor;
                            perdidaOlfatoPos = position;
                            break;
                        case PERDIDAGUSTO_CONS:
                            perdidaGusto = nuevoValor;
                            perdidaGustoPos = position;
                            break;
                        case TOS_CONS:
                            tosPos = position;
                            tos = nuevoValor;
                            break;
                        case DIFICULTADRESPIRAR_CONS:
                            dificultadRespirar = nuevoValor;
                            dificultadRespirarPos = position;
                            break;
                        case DOLORPECHO_CONS:
                            dolorPecho = nuevoValor;
                            dolorPechoPos = position;
                            break;
                        case PALPITACIONES_CONS:
                            palpitaciones = nuevoValor;
                            palpitacionesPos = position;
                            break;
                        case DOLORARTICULACIONES_CONS:
                            dolorArticulaciones = nuevoValor;
                            dolorArticulacionesPos = position;
                            break;
                        case PARALISIS_CONS:
                            paralisis = nuevoValor;
                            paralisisPos = position;
                            break;
                        case MAREOS_CONS:
                            mareos = nuevoValor;
                            mareosPos = position;
                            break;
                        case PENSAMIENTONUBLADO_CONS:
                            pensamientoNublado = nuevoValor;
                            pensamientoNubladoPos = position;
                            break;
                        case PROBLEMASDORMIR_CONS:
                            problemasDormir = nuevoValor;
                            problemasDormirPos = position;
                            break;
                        case DEPRESION_CONS:
                            depresion = nuevoValor;
                            depresionPos = position;
                            break;
                        case OTROSSINTOMAS_CONS:
                            otrosSintomas = nuevoValor;
                            otrosSintomasPos = position;
                            break;
                        case CUALESSINTOMAS_CONS:
                            cualesSintomas = nuevoValor;
                            cualesSintomasPos = position;
                            break;
                        case SABETIEMPORECUPERACION_CONS:
                            sabeTiempoRecuperacion = nuevoValor;
                            sabeTiempoRecuperacionPos = position;
                            break;
                        case TIEMPORECUPERACION_CONS:
                            tiempoRecuperacion = nuevoValor;
                            tiempoRecuperacionPos = position;
                            break;
                        case TIEMPORECUPERACIONEN_CONS:
                            tiempoRecuperacionEn = nuevoValor;
                            tiempoRecuperacionEnPos = position;
                            break;
                        case SEVERIDADENFERMEDAD_CONS:
                            severidadEnfermedad = nuevoValor;
                            severidadEnfermedadPos = position;
                            break;
                        case TOMOMEDICAMENTO_CONS:
                            tomoMedicamento = nuevoValor;
                            tomoMedicamentoPos = position;
                            break;
                        case QUEMEDICAMENTO_CONS:
                            queMedicamento = nuevoValor;
                            queMedicamentoPos = position;
                            break;
                        case OTROMEDICAMENTO_CONS:
                            otroMedicamento = nuevoValor;
                            otroMedicamentoPos = position;
                            break;
                        case PADECEENFISEMA_CONS:
                            padeceEnfisema = nuevoValor;
                            padeceEnfisemaPos = position;
                            break;
                        case PADECEASMA_CONS:
                            padeceAsma = nuevoValor;
                            padeceAsmaPos = position;
                            break;
                        case PADECEDIABETES_CONS:
                            padeceDiabetes = nuevoValor;
                            padeceDiabetesPos = position;
                            break;
                        case PADECEENFCORONARIA_CONS:
                            padeceEnfCoronaria = nuevoValor;
                            padeceEnfCoronariaPos = position;
                            break;
                        case PADECEPRESIONALTA_CONS:
                            padecePresionAlta = nuevoValor;
                            padecePresionAltaPos = position;
                            break;
                        case PADECEENFHIGADO_CONS:
                            padeceEnfHigado = nuevoValor;
                            padeceEnfHigadoPos = position;
                            break;
                        case PADECEENFRENAL_CONS:
                            padeceEnfRenal = nuevoValor;
                            padeceEnfRenalPos = position;
                            break;
                        case PADECEINFARTODERRAMECER_CONS:
                            padeceInfartoDerrameCer = nuevoValor;
                            padeceInfartoDerrameCerPos = position;
                            break;
                        case PADECECANCER_CONS:
                            padeceCancer = nuevoValor;
                            padeceCancerPos = position;
                            break;
                        case PADECECONDICIONINMUNO_CONS:
                            padeceCondicionInmuno = nuevoValor;
                            padeceCondicionInmunoPos = position;
                            break;
                        case PADECEENFAUTOINMUNE_CONS:
                            padeceEnfAutoinmune = nuevoValor;
                            padeceEnfAutoinmunePos = position;
                            break;
                        case PADECEDISCAPACIDADFIS_CONS:
                            padeceDiscapacidadFis = nuevoValor;
                            padeceDiscapacidadFisPos = position;
                            break;
                        case PADECECONDPSICPSIQ_CONS:
                            padeceCondPsicPsiq = nuevoValor;
                            padeceCondPsicPsiqPos = position;
                            break;
                        case PADECEOTRACONDICION_CONS:
                            padeceOtraCondicion = nuevoValor;
                            padeceOtraCondicionPos = position;
                            break;
                        case QUEOTRACONDICION_CONS:
                            queOtraCondicion = nuevoValor;
                            queOtraCondicionPos = position;
                            break;
                        case FUMADO_CONS:
                            fumado = nuevoValor;
                            fumadoPos = position;
                            break;
                        case FUMADOCIENCIGARRILLOS_CONS:
                            fumadoCienCigarrillos = nuevoValor;
                            fumadoCienCigarrillosPos = position;
                            break;
                        case FUMADOPREVIOENFERMEDAD_CONS:
                            fumadoPrevioEnfermedad = nuevoValor;
                            fumadoPrevioEnfermedadPos = position;
                            break;
                        case FUMAACTUALMENTE_CONS:
                            fumaActualmente = nuevoValor;
                            fumaActualmentePos = position;
                            break;
                        case EMBARAZADA_CONS:
                            embarazada = nuevoValor;
                            embarazadaPos = position;
                            break;
                        case RECUERDASEMANASEMB_CONS:
                            recuerdaSemanasEmb = nuevoValor;
                            recuerdaSemanasEmbPos = position;
                            break;
                        case FINALEMBARAZO_CONS:
                            finalEmbarazo = nuevoValor;
                            finalEmbarazoPos = position;
                            break;
                        case DABAPECHO_CONS:
                            dabaPecho = nuevoValor;
                            dabaPechoPos = position;
                            break;
                        case TRABAJADORSALUD_CONS:
                            trabajadorSalud = nuevoValor;
                            trabajadorSaludPos = position;
                            break;
                        default: break;
                    }
                }
            });
            builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    mostrarConfirmacion = false;
                    spinFeb20Febricula.setSelection(feb20FebriculaPos, false);
                    switch (pregunta){
                        case FEB20FEBRICULA_CONS:
                            spinFeb20Febricula.setSelection(feb20FebriculaPos, false);
                            break;
                        case FEB20FIEBRE_CONS:
                            spinFeb20Fiebre.setSelection(feb20FiebrePos, false);
                            break;
                        case FEB20ESCALOFRIO_CONS:
                            spinFeb20Escalofrio.setSelection(feb20EscalofrioPos, false);
                            break;
                        case FEB20TEMBLORESCALOFRIO_CONS:
                            spinFeb20TemblorEscalofrio.setSelection(feb20TemblorEscalofrioPos, false);
                            break;
                        case FEB20DOLORMUSCULAR_CONS:
                            spinFeb20DolorMuscular.setSelection(feb20DolorMuscularPos, false);
                            break;
                        case FEB20DOLORARTICULAR_CONS:
                            spinFeb20DolorArticular.setSelection(feb20DolorArticularPos, false);
                            break;
                        case FEB20SECRESIONNASAL_CONS:
                            spinFeb20SecresionNasal.setSelection(feb20SecresionNasalPos, false);
                            break;
                        case FEB20DOLORGARGANTA_CONS:
                            spinFeb20DolorGarganta.setSelection(feb20DolorGargantaPos, false);
                            break;
                        case FEB20TOS_CONS:
                            spinFeb20Tos.setSelection(feb20TosPos, false);
                            break;
                        case FEB20DIFICULTADRESP_CONS:
                            spinFeb20DificultadResp.setSelection(feb20DificultadRespPos, false);
                            break;
                        case FEB20DOLORPECHO_CONS:
                            spinFeb20DolorPecho.setSelection(feb20DolorPechoPos, false);
                            break;
                        case FEB20NAUSEASVOMITO_CONS:
                            spinFeb20NauseasVomito.setSelection(feb20NauseasVomitoPos, false);
                            break;
                        case FEB20DOLORCABEZA_CONS:
                            spinFeb20DolorCabeza.setSelection(feb20DolorCabezaPos, false);
                            break;
                        case FEB20DOLORABDOMINAL_CONS:
                            spinFeb20DolorAbdominal.setSelection(feb20DolorAbdominalPos, false);
                            break;
                        case FEB20DIARREA_CONS:
                            spinFeb20Diarrea.setSelection(feb20DiarreaPos, false);
                            break;
                        case FEB20DIFICULTADDORMIR_CONS:
                            spinFeb20DificultadDormir.setSelection(feb20DificultadDormirPos, false);
                            break;
                        case FEB20DEBILIDAD_CONS:
                            spinFeb20Debilidad.setSelection(feb20DebilidadPos, false);
                            break;
                        case FEB20PERDIDAOLFATOGUSTO_CONS:
                            spinFeb20PerdidaOlfatoGusto.setSelection(feb20PerdidaOlfatoGustoPos, false);
                            break;
                        case FEB20MAREO_CONS:
                            spinFeb20Mareo.setSelection(feb20MareoPos, false);
                            break;
                        case FEB20SARPULLIDO_CONS:
                            spinFeb20Sarpullido.setSelection(feb20SarpullidoPos, false);
                            break;
                        case FEB20DESMAYO_CONS:
                            spinFeb20Desmayo.setSelection(feb20DesmayoPos, false);
                            break;
                        case FEB20QUEDOCAMA_CONS:
                            spinFeb20QuedoCama.setSelection(feb20QuedoCamaPos, false);
                            break;
                        case SABEFIS_CONS:
                            spinSabeInicioSintoma.setSelection(sabeFISPos, false);
                            break;
                        case FIS_CONS:
                            break;
                        case MESINICIOSINTOMA_CONS:
                            spinMesInicioSintoma.setSelection(mesInicioSintomaPos, false);
                            break;
                        case ANIOINICIOSINTOMA_CONS:
                            break;
                        case PADECIDOCOVID19_CONS:
                            spinPadecidoCovid19.setSelection(padecidoCovid19Pos, false);
                            break;
                        case CONOCELUGAREXPOSICION_CONS:
                            spinConoceLugarExposicion.setSelection(conoceLugarExposicionPos, false);
                            break;
                        case LUGAREXPOSICION_CONS:
                            break;
                        case BUSCOAYUDA_CONS:
                            spinBuscoAyuda.setSelection(buscoAyudaPos, false);
                            break;
                        case DONDEBUSCOAYUDA_CONS:
                            spinDondeBuscoAyuda.setSelection(dondeBuscoAyudaPos, false);
                            break;
                        case NOMBRECENTROSALUD_CONS:
                            break;
                        case NOMBREHOSPITAL_CONS:
                            break;
                        case RECIBIOSEGUIMIENTO_CONS:
                            spinRecibioSeguimiento.setSelection(recibioSeguimientoPos, false);
                            break;
                        case TIPOSEGUIMIENTO_CONS:
                            spinTipoSeguimiento.setSelection(tipoSeguimientoPos, false);
                            break;
                        case TMPDESPUESBUSCOAYUDA_CONS:
                            spinTmpDespuesBuscoAyuda.setSelection(tmpDespuesBuscoAyudaPos, false);
                            break;
                        case UNANOCHEHOSPITAL_CONS:
                            spinUnaNocheHospital.setSelection(unaNocheHospitalPos, false);
                            break;
                        case QUEHOSPITAL_CONS:
                            break;
                        case SABECUANTASNOCHES_CONS:
                            spinSabeCuantasNoches.setSelection(sabeCuantasNochesPos, false);
                            break;
                        case CUANTASNOCHESHOSP_CONS:
                            break;
                        case SABEFECHAADMISION_CONS:
                            spinSabeFechaAdmision.setSelection(sabeFechaAdmisionPos, false);
                            break;
                        case FECHAADMISIONHOSP_CONS:
                            break;
                        case SABEFECHAALTA_CONS:
                            spinSabeFechaAlta.setSelection(sabeFechaAltaPos, false);
                            break;
                        case FECHAALTAHOSP_CONS:
                            break;
                        case UTILIZOOXIGENO_CONS:
                            spinUtilizoOxigeno.setSelection(utilizoOxigenoPos, false);
                            break;
                        case ESTUVOUCI_CONS:
                            spinEstuvoUCI.setSelection(estuvoUCIPos, false);
                            break;
                        case FUEINTUBADO_CONS:
                            spinFueIntubado.setSelection(fueIntubadoPos, false);
                            break;
                        case RECUPERADOCOVID19_CONS:
                            spinRecuperadoCovid19.setSelection(recuperadoCovid19Pos, false);
                            break;
                        case FEBRICULA_CONS:
                            spinFebricula.setSelection(febriculaPos, false);
                            break;
                        case CANSANCIO_CONS:
                            spinCansancio.setSelection(cansancioPos, false);
                            break;
                        case DOLORCABEZA_CONS:
                            spinDolorCabeza.setSelection(dolorCabezaPos, false);
                            break;
                        case PERDIDAOLFATO_CONS:
                            spinPerdidaOlfato.setSelection(perdidaOlfatoPos, false);
                            break;
                        case PERDIDAGUSTO_CONS:
                            spinPerdidaGusto.setSelection(perdidaGustoPos, false);
                            break;
                        case TOS_CONS:
                            spinTos.setSelection(tosPos, false);
                            break;
                        case DIFICULTADRESPIRAR_CONS:
                            spinDificultadRespirar.setSelection(dificultadRespirarPos, false);
                            break;
                        case DOLORPECHO_CONS:
                            spinDolorPecho.setSelection(dolorPechoPos, false);
                            break;
                        case PALPITACIONES_CONS:
                            spinPalpitaciones.setSelection(palpitacionesPos, false);
                            break;
                        case DOLORARTICULACIONES_CONS:
                            spinDolorArticulaciones.setSelection(dolorArticulacionesPos, false);
                            break;
                        case PARALISIS_CONS:
                            spinParalisis.setSelection(paralisisPos, false);
                            break;
                        case MAREOS_CONS:
                            spinMareos.setSelection(mareosPos, false);
                            break;
                        case PENSAMIENTONUBLADO_CONS:
                            spinPensamientoNublado.setSelection(pensamientoNubladoPos, false);
                            break;
                        case PROBLEMASDORMIR_CONS:
                            spinProblemasDormir.setSelection(problemasDormirPos, false);
                            break;
                        case DEPRESION_CONS:
                            spinDepresion.setSelection(depresionPos, false);
                            break;
                        case OTROSSINTOMAS_CONS:
                            spinOtrosSintomas.setSelection(otrosSintomasPos, false);
                            break;
                        case CUALESSINTOMAS_CONS:
                            break;
                        case SABETIEMPORECUPERACION_CONS:
                            spinSabeTiempoRecuperacion.setSelection(sabeTiempoRecuperacionPos, false);
                            break;
                        case TIEMPORECUPERACION_CONS:
                            break;
                        case TIEMPORECUPERACIONEN_CONS:
                            spinTiempoRecuperacionEn.setSelection(tiempoRecuperacionEnPos, false);
                            break;
                        case SEVERIDADENFERMEDAD_CONS:
                            spinSeveridadEnfermedad.setSelection(severidadEnfermedadPos, false);
                            break;
                        case TOMOMEDICAMENTO_CONS:
                            spinTomoMedicamento.setSelection(tomoMedicamentoPos, false);
                            break;
                        case QUEMEDICAMENTO_CONS:
                            spinQueMedicamento.setSelection(queMedicamentoPos, false);
                            break;
                        case OTROMEDICAMENTO_CONS:
                            break;
                        case PADECEENFISEMA_CONS:
                            spinPadeceEnfisema.setSelection(padeceEnfisemaPos, false);
                            break;
                        case PADECEASMA_CONS:
                            spinPadeceAsma.setSelection(padeceAsmaPos, false);
                            break;
                        case PADECEDIABETES_CONS:
                            spinPadeceDiabetes.setSelection(padeceDiabetesPos, false);
                            break;
                        case PADECEENFCORONARIA_CONS:
                            spinPadeceEnfCoronaria.setSelection(padeceEnfCoronariaPos, false);
                            break;
                        case PADECEPRESIONALTA_CONS:
                            spinPadecePresionAlta.setSelection(padecePresionAltaPos, false);
                            break;
                        case PADECEENFHIGADO_CONS:
                            spinPadeceEnfHigado.setSelection(padeceEnfHigadoPos, false);
                            break;
                        case PADECEENFRENAL_CONS:
                            spinPadeceEnfRenal.setSelection(padeceEnfRenalPos, false);
                            break;
                        case PADECEINFARTODERRAMECER_CONS:
                            spinPadeceInfartoDerrameCer.setSelection(padeceInfartoDerrameCerPos, false);
                            break;
                        case PADECECANCER_CONS:
                            spinPadeceCancer.setSelection(padeceCancerPos, false);
                            break;
                        case PADECECONDICIONINMUNO_CONS:
                            spinPadeceCondicionInmuno.setSelection(padeceCondicionInmunoPos, false);
                            break;
                        case PADECEENFAUTOINMUNE_CONS:
                            spinPadeceEnfAutoinmune.setSelection(padeceEnfAutoinmunePos, false);
                            break;
                        case PADECEDISCAPACIDADFIS_CONS:
                            spinPadeceDiscapacidadFis.setSelection(padeceDiscapacidadFisPos, false);
                            break;
                        case PADECECONDPSICPSIQ_CONS:
                            spinPadeceCondPsicPsiq.setSelection(padeceCondPsicPsiqPos, false);
                            break;
                        case PADECEOTRACONDICION_CONS:
                            spinPadeceOtraCondicion.setSelection(padeceOtraCondicionPos, false);
                            break;
                        case QUEOTRACONDICION_CONS:
                            break;
                        case FUMADO_CONS:
                            spinFumado.setSelection(fumadoPos, false);
                            break;
                        case FUMADOCIENCIGARRILLOS_CONS:
                            spinFumadoCienCigarrillos.setSelection(fumadoCienCigarrillosPos, false);
                            break;
                        case FUMADOPREVIOENFERMEDAD_CONS:
                            spinFumadoPrevioEnfermedad.setSelection(fumadoPrevioEnfermedadPos, false);
                            break;
                        case FUMAACTUALMENTE_CONS:
                            spinFumaActualmente.setSelection(fumaActualmentePos, false);
                            break;
                        case EMBARAZADA_CONS:
                            spinEmbarazada.setSelection(embarazadaPos, false);
                            break;
                        case RECUERDASEMANASEMB_CONS:
                            spinRecuerdaSemanasEmb.setSelection(recuerdaSemanasEmbPos, false);
                            break;
                        case FINALEMBARAZO_CONS:
                            spinFinalEmbarazo.setSelection(finalEmbarazoPos, false);
                            break;
                        case DABAPECHO_CONS:
                            spinDabaPecho.setSelection(dabaPechoPos, false);
                            break;
                        case TRABAJADORSALUD_CONS:
                            spinTrabajadorSalud.setSelection(trabajadorSaludPos, false);
                            break;
                        default: break;
                    }
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }

    private void createDialog(int dialog) {
        final DatePickerDialog dpD;
        DateMidnight minDate;
        DateMidnight maxDate;
        switch(dialog) {
            case FECHA_SINTOMA:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fis= (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputFIS.setText(fis);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(2020,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_ADMISION:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaAdmisionHosp = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputFechaAdmisionHosp.setText(fechaAdmisionHosp);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(2020,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_ALTA:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaAltaHosp = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputFechaAltaHosp.setText(fechaAltaHosp);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(2020,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            default:
                break;
        }
    }

    private boolean validarEntrada() throws ParseException {
        //Valida la entrada
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if (faltaDatoRequerido(feb20Febricula, R.string.feb20Febricula)) return false;
        else if (faltaDatoRequeridoHijoNegado(feb20Fiebre, R.string.feb20Fiebre, feb20Febricula, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequerido(feb20Escalofrio, R.string.feb20Escalofrio)) return false;
        else if (faltaDatoRequerido(feb20TemblorEscalofrio, R.string.feb20TemblorEscalofrio)) return false;
        else if (faltaDatoRequerido(feb20DolorMuscular, R.string.feb20DolorMuscular)) return false;
        else if (faltaDatoRequerido(feb20DolorArticular, R.string.feb20DolorArticular)) return false;
        else if (faltaDatoRequerido(feb20SecresionNasal, R.string.feb20SecresionNasal)) return false;
        else if (faltaDatoRequerido(feb20DolorGarganta, R.string.feb20DolorGarganta)) return false;
        else if (faltaDatoRequerido(feb20Tos, R.string.feb20Tos)) return false;
        else if (faltaDatoRequerido(feb20DificultadResp, R.string.feb20DificultadResp)) return false;
        else if (faltaDatoRequerido(feb20DolorPecho, R.string.feb20DolorPecho)) return false;
        else if (faltaDatoRequerido(feb20NauseasVomito, R.string.feb20NauseasVomito)) return false;
        else if (faltaDatoRequerido(feb20DolorCabeza, R.string.feb20DolorCabeza)) return false;
        else if (faltaDatoRequerido(feb20DolorAbdominal, R.string.feb20DolorAbdominal)) return false;
        else if (faltaDatoRequerido(feb20Diarrea, R.string.feb20Diarrea)) return false;
        else if (faltaDatoRequerido(feb20DificultadDormir, R.string.feb20DificultadDormir)) return false;
        else if (faltaDatoRequerido(feb20Debilidad, R.string.feb20Debilidad)) return false;
        else if (faltaDatoRequerido(feb20PerdidaOlfatoGusto, R.string.feb20PerdidaOlfatoGusto)) return false;
        else if (faltaDatoRequerido(feb20Mareo, R.string.feb20Mareo)) return false;
        else if (faltaDatoRequerido(feb20Sarpullido, R.string.feb20Sarpullido)) return false;
        else if (faltaDatoRequerido(feb20Desmayo, R.string.feb20Desmayo)) return false;
        else if (faltaDatoRequerido(feb20QuedoCama, R.string.feb20QuedoCama)) return false;
        else if (tuvoAlMenosUnSintoma() && faltaDatoRequerido(sabeFIS, R.string.sabeInicioSintoma)) return false;
        else if (faltaDatoRequeridoHijo(fis, R.string.fis, sabeFIS, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(anioInicioSintoma, R.string.anioInicioSintoma, sabeFIS, Constants.NOKEYSND)) {
            inputAnioInicioSintoma.requestFocus();
            return false;
        }
        else if (anioInicioSintoma != null && !anioInicioSintoma.equalsIgnoreCase("2020") && !anioInicioSintoma.equalsIgnoreCase("2021")) {
            Toast.makeText(getActivity(), getActivity().getString( R.string.wrongYear, "2020", "2021"),Toast.LENGTH_LONG).show();
            inputAnioInicioSintoma.requestFocus();
            return false;
        }
        else if (faltaDatoRequeridoHijo(mesInicioSintoma, R.string.mesInicioSintoma, sabeFIS, Constants.NOKEYSND)) return false;
        else if (faltaDatoRequerido(padecidoCovid19, R.string.padecidoCovid19)) return false;
        else if (faltaDatoRequeridoHijo(conoceLugarExposicion, R.string.conoceLugarExposicion, padecidoCovid19)) return false;
        else if (faltaDatoRequeridoHijo(lugarExposicion, R.string.lugarExposicion, conoceLugarExposicion)) return false;
        else if (faltaDatoRequeridoHijo(buscoAyuda, R.string.buscoAyuda, padecidoCovid19)) return false;
        else if (faltaDatoRequeridoHijo(dondeBuscoAyuda, R.string.dondeBuscoAyuda, buscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(nombreCentroSalud, R.string.otroCentroSalud, dondeBuscoAyuda, "2")) return false;
        else if (faltaDatoRequeridoHijo(nombreHospital, R.string.cualHospital, dondeBuscoAyuda, "3")) return false;
        else if (faltaDatoRequeridoHijoNegado(recibioSeguimiento, R.string.recibioSeguimiento, dondeBuscoAyuda, "5")) return false;
        else if (faltaDatoRequeridoHijo(tipoSeguimiento, R.string.tipoSeguimiento, recibioSeguimiento)) return false;
        else if (faltaDatoRequeridoHijo(tmpDespuesBuscoAyuda, R.string.tmpDespuesBuscoAyuda, buscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(unaNocheHospital, R.string.unaNocheHospital, buscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(queHospital, R.string.queHospital, unaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(sabeCuantasNoches, R.string.sabeCuantasNoches, unaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(cuantasNochesHosp, R.string.cuantasNochesHosp, sabeCuantasNoches)) return false;
        else if (faltaDatoRequeridoHijo(sabeFechaAdmision, R.string.sabeFechaAdmision, unaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(fechaAdmisionHosp, R.string.fechaAdmisionHosp, sabeFechaAdmision)) return false;
        else if (faltaDatoRequeridoHijo(sabeFechaAlta, R.string.sabeFechaAlta, unaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(fechaAltaHosp, R.string.fechaAltaHosp, sabeFechaAlta)) return false;
        else if (faltaDatoRequeridoHijo(utilizoOxigeno, R.string.utilizoOxigenoCuest, unaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(estuvoUCI, R.string.estuvoUCI, unaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(fueIntubado, R.string.fueIntubadoCuest, estuvoUCI)) return false;
        else if (faltaDatoRequeridoHijo(recuperadoCovid19, R.string.recuperadoCovid19, padecidoCovid19)) return false;
        else if (faltaDatoRequeridoHijoNegado(febricula, R.string.febricula, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(cansancio, R.string.cansancio, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(dolorCabeza, R.string.dolorCabeza, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(perdidaOlfato, R.string.perdidaOlfato, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(perdidaGusto, R.string.perdidaGusto, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(tos, R.string.tos, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(dificultadRespirar, R.string.dificultadRespirar, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(dolorPecho, R.string.dolorPecho, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(palpitaciones, R.string.palpitaciones, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(dolorArticulaciones, R.string.dolorArticulaciones, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(paralisis, R.string.paralisis, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(mareos, R.string.mareos, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(pensamientoNublado, R.string.pensamientoNublado, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(problemasDormir, R.string.problemasDormir, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(depresion, R.string.depresion, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(otrosSintomas, R.string.otrosSintomas, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(cualesSintomas, R.string.cualesSintomas, otrosSintomas, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(sabeTiempoRecuperacion, R.string.sabeTiempoRecuperacion, recuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(tiempoRecuperacion, R.string.tiempoRecuperacion, sabeTiempoRecuperacion)) return false;
        else if (faltaDatoRequeridoHijo(tiempoRecuperacionEn, R.string.tiempoRecuperacionEn, sabeTiempoRecuperacion)) return false;
        else if (faltaDatoRequeridoHijo(severidadEnfermedad, R.string.severidadEnfermedad, padecidoCovid19)) return false;
        else if (faltaDatoRequeridoHijo(tomoMedicamento, R.string.tomoMedicamento, padecidoCovid19)) return false;
        else if (faltaDatoRequeridoHijo(queMedicamento, R.string.queMedicamento, tomoMedicamento)) return false;
        else if (faltaDatoRequeridoHijoContains(otroMedicamento, R.string.otroMedicamento, queMedicamento, "otro")) return false;
        else if (faltaDatoRequerido(padeceEnfisema, R.string.padeceEnfisema)) return false;
        else if (faltaDatoRequerido(padeceAsma, R.string.padeceAsma)) return false;
        else if (faltaDatoRequerido(padeceDiabetes, R.string.padeceDiabetes)) return false;
        else if (faltaDatoRequerido(padeceEnfCoronaria, R.string.padeceEnfCoronaria)) return false;
        else if (faltaDatoRequerido(padecePresionAlta, R.string.padecePresionAlta)) return false;
        else if (faltaDatoRequerido(padeceEnfHigado, R.string.padeceEnfHigado)) return false;
        else if (faltaDatoRequerido(padeceEnfRenal, R.string.padeceEnfRenal)) return false;
        else if (faltaDatoRequerido(padeceInfartoDerrameCer, R.string.padeceInfartoDerrameCer)) return false;
        else if (faltaDatoRequerido(padeceCancer, R.string.padeceCancer)) return false;
        else if (faltaDatoRequerido(padeceCondicionInmuno, R.string.padeceCondicionInmuno)) return false;
        else if (faltaDatoRequerido(padeceEnfAutoinmune, R.string.padeceEnfAutoinmune)) return false;
        else if (faltaDatoRequerido(padeceDiscapacidadFis, R.string.padeceDiscapacidadFis)) return false;
        else if (faltaDatoRequerido(padeceCondPsicPsiq, R.string.padeceCondPsicPsiq)) return false;
        else if (faltaDatoRequerido(padeceOtraCondicion, R.string.padeceOtraCondicion)) return false;
        else if (faltaDatoRequeridoHijo(queOtraCondicion, R.string.queOtraCondicion, padeceOtraCondicion)) return false;
        else if (participante.getEdadMeses() >= 168 && faltaDatoRequerido(fumado, R.string.fumado)) return false;
        else if (participante.getEdadMeses() >= 168 && faltaDatoRequeridoHijo(fumadoCienCigarrillos, R.string.fumadoCienCigarrillos, fumado)) return false;
        else if (participante.getEdadMeses() >= 168 && padecidoCovid19.equals(Constants.YESKEYSND) && fumadoCienCigarrillos.equals(Constants.YESKEYSND) && (fumadoPrevioEnfermedad == null || fumadoPrevioEnfermedad.equals(""))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fumadoPrevioEnfermedad)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (participante.getEdadMeses() >= 168 && faltaDatoRequeridoHijoNegado(fumaActualmente, R.string.fumaActualmente, fumadoCienCigarrillos, Constants.NOKEYSND)) return false;
        else if ((fechaAdmisionHosp!=null && !fechaAdmisionHosp.isEmpty()) && (fechaAltaHosp!=null && !fechaAltaHosp.isEmpty()) && formatter.parse(fechaAltaHosp).before(formatter.parse(fechaAdmisionHosp))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.fecBefRegistro, "Fecha Alta", "Fecha Admisión"),Toast.LENGTH_LONG).show();
            return false;
        }
        else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) && faltaDatoRequeridoHijo(embarazada, R.string.embarazada, padecidoCovid19)) return false;
        else if (faltaDatoRequeridoHijo(recuerdaSemanasEmb, R.string.recuerdaSemanasEmb, embarazada)) return false;
        else if (faltaDatoRequeridoHijo(semanasEmbarazo, R.string.semanasEmbarazoCovid, recuerdaSemanasEmb)) return false;
        else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) && faltaDatoRequeridoHijo(dabaPecho, R.string.dabaPecho, padecidoCovid19)) return false;
        else if (participante.getEdadMeses() >= 216 && faltaDatoRequerido(trabajadorSalud, R.string.trabajadorSalud)) return false; //18 anios
        else {
            mCuestionario.setCodigo(infoMovil.getId());
            mCuestionario.setParticipante(participante);
            mCuestionario.setPeriodoSintomas(periodoSintomas);

            mCuestionario.setFeb20Febricula(feb20Febricula);
            mCuestionario.setFeb20Fiebre(feb20Fiebre);
            mCuestionario.setFeb20Escalofrio(feb20Escalofrio);
            mCuestionario.setFeb20TemblorEscalofrio(feb20TemblorEscalofrio);
            mCuestionario.setFeb20DolorMuscular(feb20DolorMuscular);
            mCuestionario.setFeb20DolorArticular(feb20DolorArticular);
            mCuestionario.setFeb20SecresionNasal(feb20SecresionNasal);
            mCuestionario.setFeb20DolorGarganta(feb20DolorGarganta);
            mCuestionario.setFeb20Tos(feb20Tos);
            mCuestionario.setFeb20DificultadResp(feb20DificultadResp);
            mCuestionario.setFeb20DolorPecho(feb20DolorPecho);
            mCuestionario.setFeb20NauseasVomito(feb20NauseasVomito);
            mCuestionario.setFeb20DolorCabeza(feb20DolorCabeza);
            mCuestionario.setFeb20DolorAbdominal(feb20DolorAbdominal);
            mCuestionario.setFeb20Diarrea(feb20Diarrea);
            mCuestionario.setFeb20DificultadDormir(feb20DificultadDormir);
            mCuestionario.setFeb20Debilidad(feb20Debilidad);
            mCuestionario.setFeb20PerdidaOlfatoGusto(feb20PerdidaOlfatoGusto);
            mCuestionario.setFeb20Mareo(feb20Mareo);
            mCuestionario.setFeb20Sarpullido(feb20Sarpullido);
            mCuestionario.setFeb20Desmayo(feb20Desmayo);
            mCuestionario.setFeb20QuedoCama(feb20QuedoCama);
            mCuestionario.setSabeFIS(sabeFIS);
            if (fis!=null && !fis.isEmpty()) mCuestionario.setFis(formatter.parse(fis));
            mCuestionario.setMesInicioSintoma(mesInicioSintoma);
            mCuestionario.setAnioInicioSintoma(anioInicioSintoma);
            mCuestionario.setPadecidoCovid19(padecidoCovid19);
            mCuestionario.setConoceLugarExposicion(conoceLugarExposicion);
            mCuestionario.setLugarExposicion(lugarExposicion);
            mCuestionario.setBuscoAyuda(buscoAyuda);
            mCuestionario.setDondeBuscoAyuda(dondeBuscoAyuda);
            mCuestionario.setNombreCentroSalud(nombreCentroSalud);
            mCuestionario.setNombreHospital(nombreHospital);
            mCuestionario.setRecibioSeguimiento(recibioSeguimiento);
            mCuestionario.setTipoSeguimiento(tipoSeguimiento);
            mCuestionario.setTmpDespuesBuscoAyuda(tmpDespuesBuscoAyuda);
            mCuestionario.setUnaNocheHospital(unaNocheHospital);
            mCuestionario.setQueHospital(queHospital);
            mCuestionario.setSabeCuantasNoches(sabeCuantasNoches);
            mCuestionario.setCuantasNochesHosp(cuantasNochesHosp);
            mCuestionario.setSabeFechaAdmision(sabeFechaAdmision);
            if (fechaAdmisionHosp!=null && !fechaAdmisionHosp.isEmpty()) mCuestionario.setFechaAdmisionHosp(formatter.parse(fechaAdmisionHosp));
            mCuestionario.setSabeFechaAlta(sabeFechaAlta);
            if (fechaAltaHosp!=null && !fechaAltaHosp.isEmpty()) mCuestionario.setFechaAltaHosp(formatter.parse(fechaAltaHosp));
            mCuestionario.setUtilizoOxigeno(utilizoOxigeno);
            mCuestionario.setEstuvoUCI(estuvoUCI);
            mCuestionario.setFueIntubado(fueIntubado);
            mCuestionario.setRecuperadoCovid19(recuperadoCovid19);
            mCuestionario.setFebricula(febricula);
            mCuestionario.setCansancio(cansancio);
            mCuestionario.setDolorCabeza(dolorCabeza);
            mCuestionario.setPerdidaOlfato(perdidaOlfato);
            mCuestionario.setPerdidaGusto(perdidaGusto);
            mCuestionario.setTos(tos);
            mCuestionario.setDificultadRespirar(dificultadRespirar);
            mCuestionario.setDolorPecho(dolorPecho);
            mCuestionario.setPalpitaciones(palpitaciones);
            mCuestionario.setDolorArticulaciones(dolorArticulaciones);
            mCuestionario.setParalisis(paralisis);
            mCuestionario.setMareos(mareos);
            mCuestionario.setPensamientoNublado(pensamientoNublado);
            mCuestionario.setProblemasDormir(problemasDormir);
            mCuestionario.setDepresion(depresion);
            mCuestionario.setOtrosSintomas(otrosSintomas);
            mCuestionario.setCualesSintomas(cualesSintomas);
            mCuestionario.setSabeTiempoRecuperacion(sabeTiempoRecuperacion);
            mCuestionario.setTiempoRecuperacion(tiempoRecuperacion);
            mCuestionario.setTiempoRecuperacionEn(tiempoRecuperacionEn);
            mCuestionario.setSeveridadEnfermedad(severidadEnfermedad);
            mCuestionario.setTomoMedicamento(tomoMedicamento);
            if (queMedicamento!=null && !queMedicamento.isEmpty()) {
                String keysCriterios = "";
                queMedicamento = queMedicamento.replaceAll(",", "','");
                estudiosAdapter.open();
                List<MessageResource> catMedi = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + queMedicamento + "') and "
                        + CatalogosDBConstants.catRoot + "='COVID_MEDICAMENTO_SVC'", null);
                estudiosAdapter.close();
                for (MessageResource ms : catMedi) {
                    keysCriterios += ms.getCatKey() + ",";
                }
                if (!keysCriterios.isEmpty())
                    keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                mCuestionario.setQueMedicamento(keysCriterios);
            }
            mCuestionario.setOtroMedicamento(otroMedicamento);
            mCuestionario.setPadeceEnfisema(padeceEnfisema);
            mCuestionario.setPadeceAsma(padeceAsma);
            mCuestionario.setPadeceDiabetes(padeceDiabetes);
            mCuestionario.setPadeceEnfCoronaria(padeceEnfCoronaria);
            mCuestionario.setPadecePresionAlta(padecePresionAlta);
            mCuestionario.setPadeceEnfHigado(padeceEnfHigado);
            mCuestionario.setPadeceEnfRenal(padeceEnfRenal);
            mCuestionario.setPadeceInfartoDerrameCer(padeceInfartoDerrameCer);
            mCuestionario.setPadeceCancer(padeceCancer);
            mCuestionario.setPadeceCondicionInmuno(padeceCondicionInmuno);
            mCuestionario.setPadeceEnfAutoinmune(padeceEnfAutoinmune);
            mCuestionario.setPadeceDiscapacidadFis(padeceDiscapacidadFis);
            mCuestionario.setPadeceCondPsicPsiq(padeceCondPsicPsiq);
            mCuestionario.setPadeceOtraCondicion(padeceOtraCondicion);
            mCuestionario.setQueOtraCondicion(queOtraCondicion);
            mCuestionario.setFumado(fumado);
            mCuestionario.setFumadoCienCigarrillos(fumadoCienCigarrillos);
            mCuestionario.setFumadoPrevioEnfermedad(fumadoPrevioEnfermedad);
            mCuestionario.setFumaActualmente(fumaActualmente);
            mCuestionario.setEmbarazada(embarazada);
            mCuestionario.setRecuerdaSemanasEmb(recuerdaSemanasEmb);
            mCuestionario.setSemanasEmbarazo(semanasEmbarazo);
            mCuestionario.setFinalEmbarazo(finalEmbarazo);
            mCuestionario.setOtroFinalEmbarazo(otroFinalEmbarazo);
            mCuestionario.setDabaPecho(dabaPecho);
            mCuestionario.setTrabajadorSalud(trabajadorSalud);

            mCuestionario.setRecordDate(new Date());
            mCuestionario.setRecordUser(username);
            mCuestionario.setDeviceid(infoMovil.getDeviceId());
            mCuestionario.setEstado('0');
            mCuestionario.setPasive('0');
            return true;
        }

    }

    private boolean faltaDatoRequerido(String variable, int resId){
        if (variable == null || variable.equals("")){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(resId)),Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijo(String variable, int resId, String padre){
        if (padre.equals(Constants.YESKEYSND) && (variable == null || variable.equals(""))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(resId)),Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijo(Integer variable, int resId, String padre){
        if (padre.equals(Constants.YESKEYSND) && (variable == null || variable <= 0)){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(resId)),Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijo(String variable, int resId, String padre, String valorPadre){
        if (padre.equals(valorPadre) && (variable == null || variable.equals(""))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(resId)),Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijoNegado(String variable, int resId, String padre, String valorPadre){
        if (!padre.isEmpty() && !padre.equals(valorPadre) && (variable == null || variable.equals(""))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(resId)),Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijoContains(String variable, int resId, String padre, String valorPadre){
        if (padre!=null && padre.toLowerCase().contains(valorPadre) && (variable == null || variable.equals(""))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(resId)),Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private class FetchCatalogosTask extends AsyncTask<String, Void, String> {
        private ProgressDialog nDialog;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage(getActivity().getString(R.string.pleasewait));
            nDialog.setTitle(getActivity().getString(R.string.loading));
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                estudiosAdapter.open();
                mCatalogoSiNo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", CatalogosDBConstants.order);
                mCatalogoSiNoNsNc = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_SNNSNC'", CatalogosDBConstants.order);
                mCatalogoSiNsNc = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_SNNSNC' and " + CatalogosDBConstants.messageKey + "<> 'COVID_CAT_SNNSNC_02'", CatalogosDBConstants.order);
                mCatalogoSiNoNc = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_SNNSNC' and " + CatalogosDBConstants.messageKey + "<> 'COVID_CAT_SNNSNC_999'", CatalogosDBConstants.order);
                mCatalogoFuma = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_FUMA'", CatalogosDBConstants.order);
                mMedicamentos = Arrays.asList(estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='COVID_MEDICAMENTO_SVC'", CatalogosDBConstants.order));
                mCatalogoSeveridad = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_SEVERIDAD'", CatalogosDBConstants.order);
                mCatalogoTiempo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_TIEMPO_BA'", CatalogosDBConstants.order);
                mCatalogoDondeBA = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_DONDE_BA'", CatalogosDBConstants.order);
                mCatalogoTipoSeg = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_TIPO_SEG'", CatalogosDBConstants.order);
                mCatalogoUnidadMedTR = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_UNIDAD_MED_TR'", CatalogosDBConstants.order);
                mCatalogoMeses = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", CatalogosDBConstants.order);
                /*List<MessageResource> meses = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", CatalogosDBConstants.order);
                for(MessageResource mes : meses){
                    if (Integer.parseInt(mes.getCatKey()) >= 2 && Integer.parseInt(mes.getCatKey()) <= c.get(Calendar.MONTH)+1){
                        mCatalogoMeses.add(mes);
                    }
                }*/
                mCatalogoFinalEmb = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_FINAL_EMB'", CatalogosDBConstants.order);
                estudiosAdapter.close();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            nDialog.dismiss();
            mTitleView.setText(getActivity().getString(R.string.covid19_questionnaire));
            mTitleView.setTextSize(33);
            mCatalogoSiNo.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoSiNo);
            ArrayAdapter<MessageResource> dataAdapterSiNo = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSiNo);
            dataAdapterSiNo.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoSiNoNc.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoSiNoNc);
            ArrayAdapter<MessageResource> dataAdapterSiNoNc = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSiNoNc);
            dataAdapterSiNoNc.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoSiNsNc.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoSiNsNc);
            ArrayAdapter<MessageResource> dataAdapterSiNsNc = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSiNsNc);
            dataAdapterSiNsNc.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoSiNoNsNc.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoSiNoNsNc);
            ArrayAdapter<MessageResource> dataAdapterSiNoNsNc = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSiNoNsNc);
            dataAdapterSiNoNsNc.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoFuma.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoFuma);
            ArrayAdapter<MessageResource> dataAdapterFuma = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoFuma);
            dataAdapterFuma.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoSeveridad.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoSeveridad);
            ArrayAdapter<MessageResource> dataAdapterSev = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSeveridad);
            dataAdapterSev.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoTiempo.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoTiempo);
            ArrayAdapter<MessageResource> dataAdapterTiempo = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoTiempo);
            dataAdapterTiempo.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoDondeBA.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoDondeBA);
            ArrayAdapter<MessageResource> dataAdapterDBA = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoDondeBA);
            dataAdapterDBA.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoUnidadMedTR.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoUnidadMedTR);
            ArrayAdapter<MessageResource> dataAdapterUM = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoUnidadMedTR);
            dataAdapterUM.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoTipoSeg.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoTipoSeg);
            ArrayAdapter<MessageResource> dataAdapterTipoSeg = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoTipoSeg);
            dataAdapterTipoSeg.setDropDownViewResource(R.layout.spinner_item);

            /*mCatalogoMeses.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoMeses);
            ArrayAdapter<MessageResource> dataAdapterMeses = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoMeses);
            dataAdapterMeses.setDropDownViewResource(R.layout.spinner_item);*/

            mCatalogoFinalEmb.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoFinalEmb);
            ArrayAdapter<MessageResource> dataAdapterFinalEmbarazo = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoFinalEmb);
            dataAdapterFinalEmbarazo.setDropDownViewResource(R.layout.spinner_item);

            spinBuscoAyuda.setAdapter(dataAdapterSiNoNc);
            spinRecibioSeguimiento.setAdapter(dataAdapterSiNo);
            spinFueIntubado.setAdapter(dataAdapterSiNo);
            spinFumado.setAdapter(dataAdapterSiNo);
            spinSabeCuantasNoches.setAdapter(dataAdapterSiNsNc);
            spinSabeFechaAdmision.setAdapter(dataAdapterSiNsNc);
            spinSabeFechaAlta.setAdapter(dataAdapterSiNsNc);
            spinSabeTiempoRecuperacion.setAdapter(dataAdapterSiNsNc);
            spinConoceLugarExposicion.setAdapter(dataAdapterSiNoNc);
            spinDondeBuscoAyuda.setAdapter(dataAdapterDBA);
            spinTipoSeguimiento.setAdapter(dataAdapterTipoSeg);
            spinTmpDespuesBuscoAyuda.setAdapter(dataAdapterTiempo);
            spinSeveridadEnfermedad.setAdapter(dataAdapterSev);
            spinTiempoRecuperacionEn.setAdapter(dataAdapterUM);
            spinFumadoPrevioEnfermedad.setAdapter(dataAdapterFuma);
            spinFumaActualmente.setAdapter(dataAdapterFuma);
            //spinMesInicioSintoma.setAdapter(dataAdapterMeses);
            spinTomoMedicamento.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20Febricula.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20Fiebre.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20Escalofrio.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20TemblorEscalofrio.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20DolorMuscular.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20DolorArticular.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20SecresionNasal.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20DolorGarganta.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20Tos.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20DificultadResp.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20DolorPecho.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20NauseasVomito.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20DolorCabeza.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20DolorAbdominal.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20Diarrea.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20DificultadDormir.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20Debilidad.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20PerdidaOlfatoGusto.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20Mareo.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20Sarpullido.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20Desmayo.setAdapter(dataAdapterSiNoNsNc);
            spinFeb20QuedoCama.setAdapter(dataAdapterSiNoNsNc);
            spinSabeInicioSintoma.setAdapter(dataAdapterSiNo);
            spinPadecidoCovid19.setAdapter(dataAdapterSiNoNsNc);
            spinUnaNocheHospital.setAdapter(dataAdapterSiNoNsNc);
            spinUtilizoOxigeno.setAdapter(dataAdapterSiNoNsNc);
            spinEstuvoUCI.setAdapter(dataAdapterSiNoNsNc);
            spinRecuperadoCovid19.setAdapter(dataAdapterSiNoNsNc);
            spinFebricula.setAdapter(dataAdapterSiNoNsNc);
            spinCansancio.setAdapter(dataAdapterSiNoNsNc);
            spinDolorCabeza.setAdapter(dataAdapterSiNoNsNc);
            spinPerdidaOlfato.setAdapter(dataAdapterSiNoNsNc);
            spinPerdidaGusto.setAdapter(dataAdapterSiNoNsNc);
            spinTos.setAdapter(dataAdapterSiNoNsNc);
            spinDificultadRespirar.setAdapter(dataAdapterSiNoNsNc);
            spinDolorPecho.setAdapter(dataAdapterSiNoNsNc);
            spinPalpitaciones.setAdapter(dataAdapterSiNoNsNc);
            spinDolorArticulaciones.setAdapter(dataAdapterSiNoNsNc);
            spinParalisis.setAdapter(dataAdapterSiNoNsNc);
            spinMareos.setAdapter(dataAdapterSiNoNsNc);
            spinPensamientoNublado.setAdapter(dataAdapterSiNoNsNc);
            spinProblemasDormir.setAdapter(dataAdapterSiNoNsNc);
            spinDepresion.setAdapter(dataAdapterSiNoNsNc);
            spinOtrosSintomas.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceEnfisema.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceAsma.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceDiabetes.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceEnfCoronaria.setAdapter(dataAdapterSiNoNsNc);
            spinPadecePresionAlta.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceEnfHigado.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceEnfRenal.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceInfartoDerrameCer.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceCancer.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceCondicionInmuno.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceEnfAutoinmune.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceDiscapacidadFis.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceCondPsicPsiq.setAdapter(dataAdapterSiNoNsNc);
            spinPadeceOtraCondicion.setAdapter(dataAdapterSiNoNsNc);
            spinFumadoCienCigarrillos.setAdapter(dataAdapterSiNoNsNc);
            spinEmbarazada.setAdapter(dataAdapterSiNoNc);
            spinRecuerdaSemanasEmb.setAdapter(dataAdapterSiNo);
            spinDabaPecho.setAdapter(dataAdapterSiNoNc);
            spinTrabajadorSalud.setAdapter(dataAdapterSiNoNc);
            spinFinalEmbarazo.setAdapter(dataAdapterFinalEmbarazo);

            spinQueMedicamento.setItems(mMedicamentos, getString(R.string.select), new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(boolean[] selected) {
                    int indice = 0;
                    queMedicamento = null;
                    for(boolean item : selected){
                        if (item) {
                            if (queMedicamento==null) queMedicamento = mMedicamentos.get(indice);
                            else  queMedicamento += "," + mMedicamentos.get(indice);
                        }
                        indice++;
                    }
                    if (queMedicamento!=null && queMedicamento.toLowerCase().contains("otro")){
                        textOtroMedicamento.setVisibility(View.VISIBLE);
                        inputOtroMedicamento.setVisibility(View.VISIBLE);
                    }else{
                        textOtroMedicamento.setVisibility(View.GONE);
                        inputOtroMedicamento.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private class SaveDataTask extends AsyncTask<String, Void, String> {
        private ProgressDialog nDialog;
        private AlertDialog alertDialog;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage(getActivity().getString(R.string.pleasewait));
            nDialog.setTitle(getActivity().getString(R.string.loading));
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                estudiosAdapter.open();
                estudiosAdapter.crearCuestionarioCovid19(mCuestionario);
                //deshabilitar proceso
                MovilInfo movilInfo = new MovilInfo();
                movilInfo.setEstado(Constants.STATUS_NOT_SUBMITTED);
                movilInfo.setDeviceid(infoMovil.getDeviceId());
                movilInfo.setUsername(username);
                movilInfo.setToday(new Date());
                ParticipanteProcesos procesos = participante.getProcesos();
                procesos.setCuestCovid(Constants.NO);
                procesos.setMovilInfo(movilInfo);
                estudiosAdapter.actualizarParticipanteProcesos(procesos);
                estudiosAdapter.close();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            nDialog.dismiss();
            if(!resultado.equals("exito")){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getString(R.string.error));
                builder.setMessage(resultado);
                builder.setPositiveButton(getActivity().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish app
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
            else{
                Bundle arguments = new Bundle();
                Intent i = new Intent(getActivity(),
                        MenuInfoActivity.class);
                i.putExtra(ConstantsDB.CODIGO, participante.getCodigo());
                i.putExtra(ConstantsDB.VIS_EXITO, true);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtras(arguments);
                startActivity(i);
                getActivity().finish();
            }
        }

    }
}
