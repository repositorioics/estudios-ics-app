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
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DateUtil;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import org.joda.time.DateMidnight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NuevoCuestionarioCovid19v3Fragment extends Fragment {

    private static final String TAG = "NuevoCuestionarioCovid19v3Fragment";
    protected static final int FECHA_VACUNA1 = 113;
    protected static final int FECHA_VACUNA2 = 114;
    protected static final int FECHA_VACUNA3 = 115;


    //MA 2024
    protected static final String DXENFERMOCOVID19_CONS = "DXENFERMOCOVID19";
    protected static final String SABE_FECHA_ULT_ENF = "SABEFECHAULTENF";
    protected static final String ANIO_ULTIMA_ENFERMEDAD = "ANIOULTIMAENFERMEDAD";
    protected static final String MES_ULTIMA_ENFERMEDAD = "MESULTIMAENFERMEDAD";
    protected static final String E1TIENEFEBRICULA_CONS = "E1TIENEFEBRICULA";
    protected static final String E1TIENECANSANCIO_CONS = "E1TIENECANSANCIO";
    protected static final String E1TIENEDOLORCABEZA_CONS = "E1TIENEDOLORCABEZA";
    protected static final String E1TIENEPERDIDAOLFATO_CONS = "E1TIENEPERDIDAOLFATO";
    protected static final String E1TIENEPERDIDAGUSTO_CONS = "E1TIENEPERDIDAGUSTO";
    protected static final String E1TIENETOS_CONS = "E1TIENETOS";
    protected static final String E1TIENEDIFICULTADRESPIRAR_CONS = "E1TIENEDIFICULTADRESPIRAR";
    protected static final String E1TIENEDOLORPECHO_CONS = "E1TIENEDOLORPECHO";
    protected static final String E1TIENEPALPITACIONES_CONS = "E1TIENEPALPITACIONES";
    protected static final String E1TIENEDOLORARTICULACIONES_CONS = "E1TIENEDOLORARTICULACIONES";
    protected static final String E1TIENEPARALISIS_CONS = "E1TIENEPARALISIS";
    protected static final String E1TIENEMAREOS_CONS = "E1TIENEMAREOS";
    protected static final String E1TIENEPENSAMIENTONUBLADO_CONS = "E1TIENEPENSAMIENTONUBLADO";
    protected static final String E1TIENEPROBLEMASDORMIR_CONS = "E1TIENEPROBLEMASDORMIR";
    protected static final String E1TIENEDEPRESION_CONS = "E1TIENEDEPRESION";
    protected static final String E1TIENEOTROSSINTOMAS_CONS = "E1TIENEOTROSSINTOMAS";
    protected static final String E1TIENECUALESSINTOMAS_CONS = "E1TIENECUALESSINTOMAS";
    protected static final String E1SABETIEMPORECUPERACION_CONS = "E1SABETIEMPORECUPERACION";
    protected static final String E1TIEMPORECUPERACION_CONS = "E1TIEMPORECUPERACION";
    protected static final String E1TIEMPORECUPERACIONEN_CONS = "E1TIEMPORECUPERACIONEN";
    protected static final String E1SEVERIDADENFERMEDAD_CONS = "E1SEVERIDADENFERMEDAD";

   //Condiciones
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

    protected static final String E1FUMADOPREVIOENFERMEDAD_CONS = "E1FUMADOPREVIOENFERMEDAD";
    protected static final String E1FUMAACTUALMENTE_CONS = "E1FUMAACTUALMENTE";
    protected static final String E1EMBARAZADA_CONS = "E1EMBARAZADA_CONS";
    protected static final String E1RECUERDASEMANASEMB_CONS = "E1RECUERDASEMANASEMB_CONS";
    protected static final String E1FINALEMBARAZO_CONS = "E1FINALEMBARAZO_CONS";
    protected static final String E1DABAPECHO_CONS = "E1DABAPECHO_CONS";

    // protected static final String TRABAJADORSALUD_CONS = "TRABAJADORSALUD_CONS";
    protected static final String VACUNADOCOVID19_CONS = "VACUNADOCOVID19";
    protected static final String MUESTRATARJETAVAC_CONS = "MUESTRATARJETAVAC";
    protected static final String SABENOMBREVACUNA_CONS = "SABENOMBREVACUNA";
    protected static final String ANIOVACUNA_CONS = "ANIOVACUNA";
    protected static final String MESVACUNA_CONS = "MESVACUNA";
    protected static final String CUANTASDOSIS_CONS = "CUANTASDOSIS";
    protected static final String NOMBREDOSIS1_CONS = "NOMBREDOSIS1";
    protected static final String NOMBREDOSIS2_CONS = "NOMBREDOSIS2";
    protected static final String NOMBREDOSIS3_CONS = "NOMBREDOSIS3";

    //Base de datos
    private EstudiosAdapter estudiosAdapter;
    final Calendar c = Calendar.getInstance();

    //Para el id
    private DeviceInfo infoMovil;
    private String username;
    private SharedPreferences settings;
    private boolean visExitosa = false;

    //Viene de la llamada
    private Participante participante;

    //Catalogos
    private List<MessageResource> mCatalogoSiNo;//Si, No
    private List<MessageResource> mCatalogoSiNoNsNc;//Si, No, No Sabe, No Contesto
    private List<MessageResource> mCatalogoSiNsNc;//Si, No Sabe, No Contesto
    private List<MessageResource> mCatalogoSiNoNc;//Si, No, No Contesto
    private List<MessageResource> mCatalogoSiNoD;//Si, No, Desconocido
    private List<MessageResource> mCatalogoFuma;
    private List<MessageResource> mCatalogoSeveridad;
    private List<MessageResource> mCatalogoTiempo;
    private List<MessageResource> mCatalogoDondeBA;
    private List<MessageResource> mCatalogoTipoSeg;
    private List<MessageResource> mCatalogoUnidadMedTR;
    private List<MessageResource> mCatalogoMeses = new ArrayList<MessageResource>();
    private List<MessageResource> mCatalogoFinalEmb;
    private List<MessageResource> mCatalogoVecesEnfermo;
    private List<MessageResource> mCatalogoTiempoOxigeno;
    private List<MessageResource> mCatalogoVacunas;
    private List<MessageResource> mCatalogoDosis;
    private List<MessageResource> mCatalogoMesesVacuna = new ArrayList<>();
    private List<MessageResource> mCatalogoAniosVac;
    private List<MessageResource> mCatalogoAnios;

    //Objeto que se va a hacer
    private CuestionarioCovid19 mCuestionario = new CuestionarioCovid19();

    //Widgets en el View

    private LinearLayout layoutFechaDosis1;
    private LinearLayout layoutFechaDosis2;
    private LinearLayout layoutFechaDosis3;

    private TextView mTitleView;
    private EditText mNameView;
    private Button mSaveView;
    //evento1
    private TextView textE1TieneFebricula;
    private TextView textE1TieneCansancio;
    private TextView textE1TieneDolorCabeza;
    private TextView textE1TienePerdidaOlfato;
    private TextView textE1TienePerdidaGusto;
    private TextView textE1TieneTos;
    private TextView textE1TieneDificultadRespirar;
    private TextView textE1TieneDolorPecho;
    private TextView textE1TienePalpitaciones;
    private TextView textE1TieneDolorArticulaciones;
    private TextView textE1TieneParalisis;
    private TextView textE1TieneMareos;
    private TextView textE1TienePensamientoNublado;
    private TextView textE1TieneProblemasDormir;
    private TextView textE1TieneDepresion;
    private TextView textE1TieneOtrosSintomas;
    private TextView textE1TieneCualesSintomas;
    private TextView textE1TiempoRecuperacion;
    private TextView textE1SabeTiempoRecuperacion;
    private TextView textE1SeveridadEnfermedad;

    //solo una vez
    private TextView textCondiciones;
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
    //evento1
    private TextView textE1FumadoPrevioEnfermedad;
    private TextView textE1FumaActualmente;
    private TextView textE1QueSintomasPresenta;
    private TextView textE1Embarazada;
    private TextView textE1RecuerdaSemanasEmb;
    private TextView textE1SemanasEmbarazo;
    private TextView textE1FinalEmbarazo;
    private TextView textE1OtroFinalEmbarazo;
    private TextView textE1DabaPecho;

    //solo una vez
    private TextView textDxEnfermoCovid19;
    private TextView textSabeFechaUltEnf;
    private TextView textAnioUltEnf;
    private TextView textMesUltEnf;
   // private TextView textVacunadoCovid19;
    private TextView textMuestraTarjetaVac;
    private TextView textSabeNombreVacuna;
    private TextView textNombreVacuna;
    private TextView textRecordarFoto;
    private TextView textAnioVacuna;
    private TextView textMesVacuna;
    private TextView textCuantasDosis;
    private TextView textNombreDosis1;
    private TextView textOtraVacunaDosis1;
    private TextView textLoteDosis1;
    private TextView textFechaDosis1;
    private TextView textNombreDosis2;
    private TextView textOtraVacunaDosis2;
    private TextView textLoteDosis2;
    private TextView textFechaDosis2;
    private TextView textNombreDosis3;
    private TextView textOtraVacunaDosis3;
    private TextView textLoteDosis3;
    private TextView textFechaDosis3;

    private Spinner spinE1TieneFebricula;
    private Spinner spinE1TieneCansancio;
    private Spinner spinE1TieneDolorCabeza;
    private Spinner spinE1TienePerdidaOlfato;
    private Spinner spinE1TienePerdidaGusto;
    private Spinner spinE1TieneTos;
    private Spinner spinE1TieneDificultadRespirar;
    private Spinner spinE1TieneDolorPecho;
    private Spinner spinE1TienePalpitaciones;
    private Spinner spinE1TieneDolorArticulaciones;
    private Spinner spinE1TieneParalisis;
    private Spinner spinE1TieneMareos;
    private Spinner spinE1TienePensamientoNublado;
    private Spinner spinE1TieneProblemasDormir;
    private Spinner spinE1TieneDepresion;
    private Spinner spinE1TieneOtrosSintomas;
    private EditText inputE1TieneCualesSintomas;
    private Spinner spinE1SabeTiempoRecuperacion;
    private EditText inputE1TiempoRecuperacion;
    private Spinner spinE1TiempoRecuperacionEn;
    private Spinner spinE1SeveridadEnfermedad;

    //solo una vez
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
    //evento1
    private Spinner spinE1FumadoPrevioEnfermedad;
    private Spinner spinE1FumaActualmente;
    private Spinner spinE1Embarazada;
    private Spinner spinE1RecuerdaSemanasEmb;
    private EditText inputE1SemanasEmbarazo;
    private Spinner spinE1FinalEmbarazo;
    private EditText inputE1OtroFinalEmbarazo;
    private Spinner spinE1DabaPecho;

    private Spinner spinDxEnfermoCovid19;
    //  private Spinner spinCuantasVecesEnfermo;
    private Spinner spinSabeFechaUltEnf;
  //  private EditText inputEvento1;
    private Spinner spinAnioUltEnf;
    private Spinner spinMesUltEnf;

    private Spinner spinVacunadoCovid19;
    private Spinner spinMuestraTarjetaVac;
    private Spinner spinSabeNombreVacuna;
    private EditText inputNombreVacuna;
    //private EditText inputAnioVacuna;
    private Spinner spinAnioVacuna;
    private Spinner spinMesVacuna;
    private Spinner spinCuantasDosis;
    private Spinner spinNombreDosis1;
    private Spinner spinNombreDosis2;
    private Spinner spinNombreDosis3;
    private EditText inputOtraVacunaDosis1;
    private EditText inputLoteDosis1;
    private EditText inputOtraVacunaDosis2;
    private EditText inputLoteDosis2;
    private EditText inputOtraVacunaDosis3;
    private EditText inputLoteDosis3;
    private EditText inputDesOtrosDosis1;
    private EditText inputDesOtrosDosis2;
    private EditText inputDesOtrosDosis3;
    private EditText inputFechaDosis1;
    private EditText inputFechaDosis2;
    private EditText inputFechaDosis3;

    private ImageButton imbFechaDosis1;
    private ImageButton imbFechaDosis2;
    private ImageButton imbFechaDosis3;

    private boolean mostrarConfirmacion = true;
    //Variables donde se captura la entrada de datos
    private int dxEnfermoCovid19Pos = 0;
    private int e1TieneFebriculaPos = 0;
    private int e1TieneCansancioPos = 0;
    private int e1TieneDolorCabezaPos = 0;
    private int e1TienePerdidaOlfatoPos = 0;
    private int e1TienePerdidaGustoPos = 0;
    private int e1TieneTosPos = 0;
    private int e1TieneDificultadRespirarPos = 0;
    private int e1TieneDolorPechoPos = 0;
    private int e1TienePalpitacionesPos = 0;
    private int e1TieneDolorArticulacionesPos = 0;
    private int e1TieneParalisisPos = 0;
    private int e1TieneMareosPos = 0;
    private int e1TienePensamientoNubladoPos = 0;
    private int e1TieneProblemasDormirPos = 0;
    private int e1TieneDepresionPos = 0;
    private int e1TieneOtrosSintomasPos = 0;
    private int e1SabeTiempoRecuperacionPos = 0;
    private int e1TiempoRecuperacionEnPos = 0;
    private int e1SeveridadEnfermedadPos = 0;

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
    private int fumadoPos = 0;
    private int fumadoCienCigarrillosPos = 0;

    private int e1FumadoPrevioEnfermedadPos = 0;
    private int e1FumaActualmentePos = 0;
    private int e1EmbarazadaPos = 0;
    private int e1RecuerdaSemanasEmbPos = 0;
    private int e1FinalEmbarazoPos = 0;
    private int e1DabaPechoPos = 0;
    private int vacunadoCovid19Pos = 0;
    private int muestraTarjetaVacPos = 0;
    private int sabeNombreVacunaPos = 0;
    private int mesVacunaPos = 0;
    private int cuantasDosisPos = 0;
    private int nombreDosis1Pos = 0;
    private int nombreDosis2Pos = 0;
    private int nombreDosis3Pos = 0;

    private String e1TieneFebricula;
    private String e1TieneCansancio;
    private String e1TieneDolorCabeza;
    private String e1TienePerdidaOlfato;
    private String e1TienePerdidaGusto;
    private String e1TieneTos;
    private String e1TieneDificultadRespirar;
    private String e1TieneDolorPecho;
    private String e1TienePalpitaciones;
    private String e1TieneDolorArticulaciones;
    private String e1TieneParalisis;
    private String e1TieneMareos;
    private String e1TienePensamientoNublado;
    private String e1TieneProblemasDormir;
    private String e1TieneDepresion;
    private String e1TieneOtrosSintomas;
    private String e1TieneCualesSintomas;
    private String e1SabeTiempoRecuperacion;
    private String e1TiempoRecuperacion;
    private String e1TiempoRecuperacionEn;
    private String e1SeveridadEnfermedad;

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

    private String e1FumadoPrevioEnfermedad;
    private String e1FumaActualmente;
    private String e1Embarazada;
    private String e1RecuerdaSemanasEmb;
    private Integer e1SemanasEmbarazo;
    private String e1FinalEmbarazo;
    private String e1OtroFinalEmbarazo;
    private String e1DabaPecho;
    private String periodoSintomas;
    private String dxEnfermoCovid19;
    private String dxEnfermoCovid19Preg;
    private String sabeFechaUltEnf;
    private String anioUltEnf;
    private String mesUltEnf;
    private String vacunadoCovid19;
    private String muestraTarjetaVac;
    private String sabeNombreVacuna;
    private String nombreVacuna;
    private String anioVacuna;
    private String mesVacuna;
    private String cuantasDosis;
    private String nombreDosis1;
    private String otraVacunaDosis1;
    private String loteDosis1;
    private String fechaDosis1;
    private String nombreDosis2;
    private String otraVacunaDosis2;
    private String loteDosis2;
    private String fechaDosis2;
    private String nombreDosis3;
    private String otraVacunaDosis3;
    private String loteDosis3;
    private String fechaDosis3;

    private TextView postCovidV3;

    public static NuevoCuestionarioCovid19v3Fragment create() {
        NuevoCuestionarioCovid19v3Fragment fragment = new NuevoCuestionarioCovid19v3Fragment();
        return fragment;
    }

    public NuevoCuestionarioCovid19v3Fragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getActivity(), mPass, false, false);
        participante = (Participante) getActivity().getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        infoMovil = new DeviceInfo(getActivity());
        visExitosa = getActivity().getIntent().getBooleanExtra(ConstantsDB.VIS_EXITO, false);
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
        //cambio fragment temporal a nuevo cuestionario V3
        View rootView = inflater.inflate(R.layout.fragment_nuevo_cuestionario_covid19_v3, container, false);
        mTitleView = ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((EditText) rootView.findViewById(R.id.inputParticipante));
        mNameView.setText(participante.getNombreCompleto());
        mNameView.setEnabled(false);

        textDxEnfermoCovid19 = (TextView) rootView.findViewById(R.id.textDxEnfermoCovid19);
        textE1TieneFebricula = (TextView) rootView.findViewById(R.id.textE1TieneFebricula);
        textE1TieneFebricula.setVisibility(View.GONE);
        textE1TieneCansancio = (TextView) rootView.findViewById(R.id.textE1TieneCansancio);
        textE1TieneCansancio.setVisibility(View.GONE);
        textE1TieneDolorCabeza = (TextView) rootView.findViewById(R.id.textE1TieneDolorCabeza);
        textE1TieneDolorCabeza.setVisibility(View.GONE);
        textE1TienePerdidaOlfato = (TextView) rootView.findViewById(R.id.textE1TienePerdidaOlfato);
        textE1TienePerdidaOlfato.setVisibility(View.GONE);
        textE1TienePerdidaGusto = (TextView) rootView.findViewById(R.id.textE1TienePerdidaGusto);
        textE1TienePerdidaGusto.setVisibility(View.GONE);
        textE1TieneTos = (TextView) rootView.findViewById(R.id.textE1TieneTos);
        textE1TieneTos.setVisibility(View.GONE);
        textE1TieneDificultadRespirar = (TextView) rootView.findViewById(R.id.textE1TieneDificultadRespirar);
        textE1TieneDificultadRespirar.setVisibility(View.GONE);
        textE1TieneDolorPecho = (TextView) rootView.findViewById(R.id.textE1TieneDolorPecho);
        textE1TieneDolorPecho.setVisibility(View.GONE);
        textE1TienePalpitaciones = (TextView) rootView.findViewById(R.id.textE1TienePalpitaciones);
        textE1TienePalpitaciones.setVisibility(View.GONE);
        textE1TieneDolorArticulaciones = (TextView) rootView.findViewById(R.id.textE1TieneDolorArticulaciones);
        textE1TieneDolorArticulaciones.setVisibility(View.GONE);
        textE1TieneParalisis = (TextView) rootView.findViewById(R.id.textE1TieneParalisis);
        textE1TieneParalisis.setVisibility(View.GONE);
        textE1TieneMareos = (TextView) rootView.findViewById(R.id.textE1TieneMareos);
        textE1TieneMareos.setVisibility(View.GONE);
        textE1TienePensamientoNublado = (TextView) rootView.findViewById(R.id.textE1TienePensamientoNublado);
        textE1TienePensamientoNublado.setVisibility(View.GONE);
        textE1TieneProblemasDormir = (TextView) rootView.findViewById(R.id.textE1TieneProblemasDormir);
        textE1TieneProblemasDormir.setVisibility(View.GONE);
        textE1TieneDepresion = (TextView) rootView.findViewById(R.id.textE1TieneDepresion);
        textE1TieneDepresion.setVisibility(View.GONE);
        textE1TieneOtrosSintomas = (TextView) rootView.findViewById(R.id.textE1TieneOtrosSintomas);
        textE1TieneOtrosSintomas.setVisibility(View.GONE);
        textE1TieneCualesSintomas = (TextView) rootView.findViewById(R.id.textE1CualesSintomas);
        textE1TieneCualesSintomas.setVisibility(View.GONE);
        textE1SabeTiempoRecuperacion = (TextView) rootView.findViewById(R.id.textE1SabeTiempoRecuperacion);
        textE1SabeTiempoRecuperacion.setVisibility(View.GONE);
        textE1TiempoRecuperacion = (TextView) rootView.findViewById(R.id.textE1TiempoRecuperacion);
        textE1TiempoRecuperacion.setVisibility(View.GONE);
        textE1SeveridadEnfermedad = (TextView) rootView.findViewById(R.id.textE1SeveridadEnfermedad);
        textE1SeveridadEnfermedad.setVisibility(View.GONE);
        textCondiciones = (TextView) rootView.findViewById(R.id.textCondiciones);
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

        textE1FumadoPrevioEnfermedad = (TextView) rootView.findViewById(R.id.textE1FumadoPrevioEnfermedad);
        textE1FumadoPrevioEnfermedad.setVisibility(View.GONE);
        textE1FumaActualmente = (TextView) rootView.findViewById(R.id.textE1FumaActualmente);
        textE1FumaActualmente.setVisibility(View.GONE);
        textE1QueSintomasPresenta = (TextView) rootView.findViewById(R.id.textE1QueSintomasPresenta);
        textE1QueSintomasPresenta.setVisibility(View.GONE);
        textE1Embarazada = (TextView) rootView.findViewById(R.id.textE1Embarazada);
        textE1Embarazada.setVisibility(View.GONE);
        textE1RecuerdaSemanasEmb = (TextView) rootView.findViewById(R.id.textE1RecuerdaSemanasEmb);
        textE1RecuerdaSemanasEmb.setVisibility(View.GONE);
        textE1SemanasEmbarazo = (TextView) rootView.findViewById(R.id.textE1SemanasEmbarazo);
        textE1SemanasEmbarazo.setVisibility(View.GONE);
        textE1FinalEmbarazo = (TextView) rootView.findViewById(R.id.textE1FinalEmbarazo);
        textE1FinalEmbarazo.setVisibility(View.GONE);
        textE1OtroFinalEmbarazo = (TextView) rootView.findViewById(R.id.textE1OtroFinalEmbarazo);
        textE1OtroFinalEmbarazo.setVisibility(View.GONE);
        textE1DabaPecho = (TextView) rootView.findViewById(R.id.textE1DabaPecho);
        textE1DabaPecho.setVisibility(View.GONE);
        textDxEnfermoCovid19 = (TextView) rootView.findViewById(R.id.textEnfermoCovid19);
        textSabeFechaUltEnf = (TextView) rootView.findViewById(R.id.textSabeFechaUltEnf);
        textSabeFechaUltEnf.setVisibility(View.GONE);
        textAnioUltEnf = (TextView) rootView.findViewById(R.id.textAnioUltEnf);
        textAnioUltEnf.setVisibility(View.GONE);
        textMesUltEnf = (TextView) rootView.findViewById(R.id.textMesUltEnf);
        textMesUltEnf.setVisibility(View.GONE);
        textMuestraTarjetaVac = (TextView) rootView.findViewById(R.id.textMuestraTarjetaVac);
        textSabeNombreVacuna = (TextView) rootView.findViewById(R.id.textSabeNombreVacuna);
        textNombreVacuna = (TextView) rootView.findViewById(R.id.textNombreVacuna);
        textRecordarFoto = (TextView) rootView.findViewById(R.id.textRecordarFoto);
        textAnioVacuna = (TextView) rootView.findViewById(R.id.textAnioVacuna);
        textMesVacuna = (TextView) rootView.findViewById(R.id.textMesVacuna);
        textCuantasDosis = (TextView) rootView.findViewById(R.id.textCuantasDosis);
        textNombreDosis1 = (TextView) rootView.findViewById(R.id.textNombreDosis1);
        textOtraVacunaDosis1 = (TextView) rootView.findViewById(R.id.textOtraVacunaDosis1);
        textLoteDosis1 = (TextView) rootView.findViewById(R.id.textLoteDosis1);
        textFechaDosis1 = (TextView) rootView.findViewById(R.id.textFechaDosis1);
        textNombreDosis2 = (TextView) rootView.findViewById(R.id.textNombreDosis2);
        textOtraVacunaDosis2 = (TextView) rootView.findViewById(R.id.textOtraVacunaDosis2);
        textLoteDosis2 = (TextView) rootView.findViewById(R.id.textLoteDosis2);
        textFechaDosis2 = (TextView) rootView.findViewById(R.id.textFechaDosis2);
        textNombreDosis3 = (TextView) rootView.findViewById(R.id.textNombreDosis3);
        textOtraVacunaDosis3 = (TextView) rootView.findViewById(R.id.textOtraVacunaDosis3);
        textLoteDosis3 = (TextView) rootView.findViewById(R.id.textLoteDosis3);
        textFechaDosis3 = (TextView) rootView.findViewById(R.id.textFechaDosis3);
        textMuestraTarjetaVac.setVisibility(View.GONE);
        textSabeNombreVacuna.setVisibility(View.GONE);
        textNombreVacuna.setVisibility(View.GONE);
        textRecordarFoto.setVisibility(View.GONE);
        textAnioVacuna.setVisibility(View.GONE);
        textMesVacuna.setVisibility(View.GONE);
        textCuantasDosis.setVisibility(View.GONE);
        textNombreDosis1.setVisibility(View.GONE);
        textOtraVacunaDosis1.setVisibility(View.GONE);
        textLoteDosis1.setVisibility(View.GONE);
        textFechaDosis1.setVisibility(View.GONE);
        textNombreDosis2.setVisibility(View.GONE);
        textOtraVacunaDosis2.setVisibility(View.GONE);
        textLoteDosis2.setVisibility(View.GONE);
        textFechaDosis2.setVisibility(View.GONE);
        textNombreDosis3.setVisibility(View.GONE);
        textOtraVacunaDosis3.setVisibility(View.GONE);
        textLoteDosis3.setVisibility(View.GONE);
        textFechaDosis3.setVisibility(View.GONE);


        spinE1TieneFebricula = (Spinner) rootView.findViewById(R.id.spinE1TieneFebricula);
        spinE1TieneCansancio = (Spinner) rootView.findViewById(R.id.spinE1TieneCansancio);
        spinE1TieneDolorCabeza = (Spinner) rootView.findViewById(R.id.spinE1TieneDolorCabeza);
        spinE1TienePerdidaOlfato = (Spinner) rootView.findViewById(R.id.spinE1TienePerdidaOlfato);
        spinE1TienePerdidaGusto = (Spinner) rootView.findViewById(R.id.spinE1TienePerdidaGusto);
        spinE1TieneTos = (Spinner) rootView.findViewById(R.id.spinE1TieneTos);
        spinE1TieneDificultadRespirar = (Spinner) rootView.findViewById(R.id.spinE1TieneDificultadRespirar);
        spinE1TieneDolorPecho = (Spinner) rootView.findViewById(R.id.spinE1TieneDolorPecho);
        spinE1TienePalpitaciones = (Spinner) rootView.findViewById(R.id.spinE1TienePalpitaciones);
        spinE1TieneDolorArticulaciones = (Spinner) rootView.findViewById(R.id.spinE1TieneDolorArticulaciones);
        spinE1TieneParalisis = (Spinner) rootView.findViewById(R.id.spinE1TieneParalisis);
        spinE1TieneMareos = (Spinner) rootView.findViewById(R.id.spinE1TieneMareos);
        spinE1TienePensamientoNublado = (Spinner) rootView.findViewById(R.id.spinE1TienePensamientoNublado);
        spinE1TieneProblemasDormir = (Spinner) rootView.findViewById(R.id.spinE1TieneProblemasDormir);
        spinE1TieneDepresion = (Spinner) rootView.findViewById(R.id.spinE1TieneDepresion);
        spinE1TieneOtrosSintomas = (Spinner) rootView.findViewById(R.id.spinE1TieneOtrosSintomas);
        inputE1TieneCualesSintomas = (EditText) rootView.findViewById(R.id.inputE1CualesSintomas);
        spinE1SabeTiempoRecuperacion = (Spinner) rootView.findViewById(R.id.spinE1SabeTiempoRecuperacion);
        inputE1TiempoRecuperacion = (EditText) rootView.findViewById(R.id.inputE1TiempoRecuperacion);
        spinE1TiempoRecuperacionEn = (Spinner) rootView.findViewById(R.id.spinE1TiempoRecuperacionEn);
        spinE1SeveridadEnfermedad = (Spinner) rootView.findViewById(R.id.spinE1SeveridadEnfermedad);
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

        spinE1FumadoPrevioEnfermedad = (Spinner) rootView.findViewById(R.id.spinE1FumadoPrevioEnfermedad);
        spinE1FumaActualmente = (Spinner) rootView.findViewById(R.id.spinE1FumaActualmente);
        spinE1Embarazada = (Spinner) rootView.findViewById(R.id.spinE1Embarazada);
        spinE1RecuerdaSemanasEmb = (Spinner) rootView.findViewById(R.id.spinE1RecuerdaSemanasEmb);
        inputE1SemanasEmbarazo = (EditText) rootView.findViewById(R.id.inputE1SemanasEmbarazo);
        spinE1FinalEmbarazo = (Spinner) rootView.findViewById(R.id.spinE1FinalEmbarazo);
        inputE1OtroFinalEmbarazo = (EditText) rootView.findViewById(R.id.inputE1OtroFinalEmbarazo);
        spinE1DabaPecho = (Spinner) rootView.findViewById(R.id.spinE1DabaPecho);
        spinDxEnfermoCovid19 = (Spinner) rootView.findViewById(R.id.spinDxEnfermoCovid19);
        spinSabeFechaUltEnf = (Spinner) rootView.findViewById(R.id.spinSabeFechaUltEnf);
        spinAnioUltEnf = (Spinner) rootView.findViewById(R.id.spinAnioUltEnf);
        spinAnioUltEnf.setVisibility(View.GONE);
        spinMesUltEnf = (Spinner) rootView.findViewById(R.id.spinMesUltEnf);
        spinMesUltEnf.setVisibility(View.GONE);

        inputNombreVacuna = (EditText) rootView.findViewById(R.id.inputNombreVacuna);
        spinAnioVacuna = (Spinner) rootView.findViewById(R.id.spinAnioVacuna);
        //poner el año actual, no hay mas opciones
        //inputAnioVacuna.setText(String.valueOf(c.get(Calendar.YEAR)));
        //anioVacuna = inputAnioVacuna.getText().toString();
        //inputAnioVacuna.setEnabled(false);
        inputOtraVacunaDosis1 = (EditText) rootView.findViewById(R.id.inputOtraVacunaDosis1);
        inputLoteDosis1 = (EditText) rootView.findViewById(R.id.inputLoteDosis1);
        inputOtraVacunaDosis2 = (EditText) rootView.findViewById(R.id.inputOtraVacunaDosis2);
        inputLoteDosis2 = (EditText) rootView.findViewById(R.id.inputLoteDosis2);
        inputOtraVacunaDosis3 = (EditText) rootView.findViewById(R.id.inputOtraVacunaDosis3);
        inputLoteDosis3 = (EditText) rootView.findViewById(R.id.inputLoteDosis3);
        inputFechaDosis1 = (EditText) rootView.findViewById(R.id.inputFechaDosis1);
        inputFechaDosis2 = (EditText) rootView.findViewById(R.id.inputFechaDosis2);
        inputFechaDosis3 = (EditText) rootView.findViewById(R.id.inputFechaDosis3);
        inputDesOtrosDosis1 = (EditText) rootView.findViewById(R.id.inputDesOtrosDosis1);
        inputDesOtrosDosis2 = (EditText) rootView.findViewById(R.id.inputDesOtrosDosis2);
        inputDesOtrosDosis3 = (EditText) rootView.findViewById(R.id.inputDesOtrosDosis3);
        spinVacunadoCovid19 = (Spinner) rootView.findViewById(R.id.spinVacunadoCovid19);
        spinMuestraTarjetaVac = (Spinner) rootView.findViewById(R.id.spinMuestraTarjetaVac);
        spinSabeNombreVacuna = (Spinner) rootView.findViewById(R.id.spinSabeNombreVacuna);
        spinMesVacuna = (Spinner) rootView.findViewById(R.id.spinMesVacuna);
        spinCuantasDosis = (Spinner) rootView.findViewById(R.id.spinCuantasDosis);
        spinNombreDosis1 = (Spinner) rootView.findViewById(R.id.spinNombreDosis1);
        spinNombreDosis2 = (Spinner) rootView.findViewById(R.id.spinNombreDosis2);
        spinNombreDosis3 = (Spinner) rootView.findViewById(R.id.spinNombreDosis3);
        imbFechaDosis1 = (ImageButton) rootView.findViewById(R.id.imbFechaDosis1);
        imbFechaDosis2 = (ImageButton) rootView.findViewById(R.id.imbFechaDosis2);
        imbFechaDosis3 = (ImageButton) rootView.findViewById(R.id.imbFechaDosis3);

        spinE1TieneFebricula.setVisibility(View.GONE);
        spinE1TieneCansancio.setVisibility(View.GONE);
        spinE1TieneDolorCabeza.setVisibility(View.GONE);
        spinE1TienePerdidaOlfato.setVisibility(View.GONE);
        spinE1TienePerdidaGusto.setVisibility(View.GONE);
        spinE1TieneTos.setVisibility(View.GONE);
        spinE1TieneDificultadRespirar.setVisibility(View.GONE);
        spinE1TieneDolorPecho.setVisibility(View.GONE);
        spinE1TienePalpitaciones.setVisibility(View.GONE);
        spinE1TieneDolorArticulaciones.setVisibility(View.GONE);
        spinE1TieneParalisis.setVisibility(View.GONE);
        spinE1TieneMareos.setVisibility(View.GONE);
        spinE1TienePensamientoNublado.setVisibility(View.GONE);
        spinE1TieneProblemasDormir.setVisibility(View.GONE);
        spinE1TieneDepresion.setVisibility(View.GONE);
        spinE1TieneOtrosSintomas.setVisibility(View.GONE);
        inputE1TieneCualesSintomas.setVisibility(View.GONE);
        spinE1SabeTiempoRecuperacion.setVisibility(View.GONE);
        inputE1TiempoRecuperacion.setVisibility(View.GONE);
        spinE1TiempoRecuperacionEn.setVisibility(View.GONE);
        spinE1SeveridadEnfermedad.setVisibility(View.GONE);

        //Preguntar a mayores de 14 años
        if (participante.getEdadMeses() < 168) {
            textFumado.setVisibility(View.GONE);
            spinFumado.setVisibility(View.GONE);
        }
        spinFumadoCienCigarrillos.setVisibility(View.GONE);

        spinE1FumadoPrevioEnfermedad.setVisibility(View.GONE);
        spinE1FumaActualmente.setVisibility(View.GONE);
        spinE1Embarazada.setVisibility(View.GONE);
        spinE1RecuerdaSemanasEmb.setVisibility(View.GONE);
        inputE1SemanasEmbarazo.setVisibility(View.GONE);
        spinE1FinalEmbarazo.setVisibility(View.GONE);
        inputE1OtroFinalEmbarazo.setVisibility(View.GONE);
        spinE1DabaPecho.setVisibility(View.GONE);
        spinMuestraTarjetaVac.setVisibility(View.GONE);
        spinSabeNombreVacuna.setVisibility(View.GONE);
        spinMesVacuna.setVisibility(View.GONE);
        spinCuantasDosis.setVisibility(View.GONE);
        spinNombreDosis1.setVisibility(View.GONE);
        spinNombreDosis2.setVisibility(View.GONE);
        spinNombreDosis3.setVisibility(View.GONE);
        imbFechaDosis1.setVisibility(View.GONE);
        imbFechaDosis2.setVisibility(View.GONE);
        imbFechaDosis3.setVisibility(View.GONE);

        layoutFechaDosis1 = (LinearLayout) rootView.findViewById(R.id.layoutFechaDosis1);
        layoutFechaDosis2 = (LinearLayout) rootView.findViewById(R.id.layoutFechaDosis2);
        layoutFechaDosis3 = (LinearLayout) rootView.findViewById(R.id.layoutFechaDosis3);

        periodoSintomas = Constants.PERIODO_CUEST_COVID19_5;

        layoutFechaDosis1.setVisibility(View.GONE);
        layoutFechaDosis2.setVisibility(View.GONE);
        layoutFechaDosis3.setVisibility(View.GONE);
        imbFechaDosis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_VACUNA1);
            }
        });

        imbFechaDosis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_VACUNA2);
            }
        });

        imbFechaDosis3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_VACUNA3);
            }
        });
        inputE1TieneCualesSintomas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                e1TieneCualesSintomas = inputE1TieneCualesSintomas.getText().toString();
            }
        });

        inputE1TiempoRecuperacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                e1TiempoRecuperacion = inputE1TiempoRecuperacion.getText().toString();
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

        inputE1SemanasEmbarazo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (inputE1SemanasEmbarazo.getText() != null && !inputE1SemanasEmbarazo.getText().toString().isEmpty())
                    e1SemanasEmbarazo = Integer.valueOf(inputE1SemanasEmbarazo.getText().toString());
            }
        });

        inputE1OtroFinalEmbarazo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                e1OtroFinalEmbarazo = inputE1OtroFinalEmbarazo.getText().toString();
            }
        });


        inputNombreVacuna.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                nombreVacuna = inputNombreVacuna.getText().toString();
            }
        });

        inputOtraVacunaDosis1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                otraVacunaDosis1 = inputOtraVacunaDosis1.getText().toString();
            }
        });

        inputOtraVacunaDosis2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                otraVacunaDosis2 = inputOtraVacunaDosis2.getText().toString();
            }
        });

        inputOtraVacunaDosis3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                otraVacunaDosis3 = inputOtraVacunaDosis3.getText().toString();
            }
        });

        inputLoteDosis1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                loteDosis1 = inputLoteDosis1.getText().toString();
            }
        });

        inputLoteDosis2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                loteDosis2 = inputLoteDosis2.getText().toString();
            }
        });

        inputLoteDosis3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                loteDosis3 = inputLoteDosis3.getText().toString();
            }
        });

        spinDxEnfermoCovid19.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DXENFERMOCOVID19_CONS, getString(R.string.dxEnfermoCovid19), dxEnfermoCovid19, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dxEnfermoCovid19 = mr.getCatKey();
                if (dxEnfermoCovid19.equals(Constants.YESKEYSND)) {
                    spinDxEnfermoCovid19.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta2_4(View.VISIBLE);
                    MostrarOcultarPregunta3(View.VISIBLE);

                    if (fumado != null){
                        if (fumado.equals(Constants.YESKEYSND)){
                            MostrarOcultarPregunta9(View.VISIBLE);
                        }else{
                            MostrarOcultarPregunta9(View.GONE);
                        }
                    }


                } else {
                    spinDxEnfermoCovid19.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta2_4(View.GONE);
                    MostrarOcultarPregunta3(View.GONE);
                    MostrarOcultarPregunta9(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinSabeFechaUltEnf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SABE_FECHA_ULT_ENF, getString(R.string.sabeFechaUltimaEnf), sabeFechaUltEnf, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                sabeFechaUltEnf = mr.getCatKey();
                if (sabeFechaUltEnf.equals(Constants.YESKEYSND)) {
                    spinSabeFechaUltEnf.setBackgroundColor(Color.RED);
                    MostrarOcultarE1_MesAnio(View.VISIBLE);
                } else {
                    spinSabeFechaUltEnf.setBackgroundColor(Color.WHITE);
                    anioUltEnf = null;
                    mesUltEnf = null;
                    MostrarOcultarE1_MesAnio(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinAnioUltEnf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(ANIO_ULTIMA_ENFERMEDAD, getString(R.string.anioEvento), anioUltEnf, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                anioUltEnf = mr.getCatKey();
                List<MessageResource> meses = new ArrayList<MessageResource>();
                meses = getMesesByAnio(anioUltEnf);
                setMesesSpinner(meses, spinMesUltEnf);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinMesUltEnf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(MES_ULTIMA_ENFERMEDAD, getString(R.string.mesEvento), mesUltEnf, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                mesUltEnf = mr.getCatKey();
                actualizarListaEventos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1SabeTiempoRecuperacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1SABETIEMPORECUPERACION_CONS, getString(R.string.sabeTiempoRecuperacion2), e1SabeTiempoRecuperacion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1SabeTiempoRecuperacion = mr.getCatKey();
                if (e1SabeTiempoRecuperacion.equals(Constants.YESKEYSND)) {
                    spinE1SabeTiempoRecuperacion.setBackgroundColor(Color.RED);

                    MostrarOcultarPregunta4a(View.VISIBLE);
                } else {
                    spinE1SabeTiempoRecuperacion.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta4a(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TiempoRecuperacionEn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIEMPORECUPERACIONEN_CONS, getString(R.string.tiempoRecuperacionEn), e1TiempoRecuperacionEn, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TiempoRecuperacionEn = mr.getCatKey();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1SeveridadEnfermedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1SEVERIDADENFERMEDAD_CONS, getString(R.string.severidadEnfermedad2), e1SeveridadEnfermedad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1SeveridadEnfermedad = mr.getCatKey();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /*SINTOMAS ACTUALES*/
        spinE1TieneFebricula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEFEBRICULA_CONS, getString(R.string.febricula), e1TieneFebricula, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneFebricula = mr.getCatKey();
                spinE1TieneFebricula.setBackgroundColor(e1TieneFebricula.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TieneCansancio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENECANSANCIO_CONS, getString(R.string.cansancio), e1TieneCansancio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneCansancio = mr.getCatKey();
                spinE1TieneCansancio.setBackgroundColor(e1TieneCansancio.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TieneDolorCabeza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEDOLORCABEZA_CONS, getString(R.string.dolorCabeza), e1TieneDolorCabeza, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneDolorCabeza = mr.getCatKey();
                spinE1TieneDolorCabeza.setBackgroundColor(e1TieneDolorCabeza.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TienePerdidaOlfato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEPERDIDAOLFATO_CONS, getString(R.string.perdidaOlfato), e1TienePerdidaOlfato, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TienePerdidaOlfato = mr.getCatKey();
                spinE1TienePerdidaOlfato.setBackgroundColor(e1TienePerdidaOlfato.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TienePerdidaGusto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEPERDIDAGUSTO_CONS, getString(R.string.perdidaGusto), e1TienePerdidaGusto, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TienePerdidaGusto = mr.getCatKey();
                spinE1TienePerdidaGusto.setBackgroundColor(e1TienePerdidaGusto.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TieneTos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENETOS_CONS, getString(R.string.tos), e1TieneTos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneTos = mr.getCatKey();
                spinE1TieneTos.setBackgroundColor(e1TieneTos.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TieneDificultadRespirar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEDIFICULTADRESPIRAR_CONS, getString(R.string.dificultadRespirar), e1TieneDificultadRespirar, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneDificultadRespirar = mr.getCatKey();
                spinE1TieneDificultadRespirar.setBackgroundColor(e1TieneDificultadRespirar.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TieneDolorPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEDOLORPECHO_CONS, getString(R.string.dolorPecho), e1TieneDolorPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneDolorPecho = mr.getCatKey();
                spinE1TieneDolorPecho.setBackgroundColor(e1TieneDolorPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TienePalpitaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEPALPITACIONES_CONS, getString(R.string.palpitaciones), e1TienePalpitaciones, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TienePalpitaciones = mr.getCatKey();
                spinE1TienePalpitaciones.setBackgroundColor(e1TienePalpitaciones.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TieneDolorArticulaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEDOLORARTICULACIONES_CONS, getString(R.string.dolorArticulaciones), e1TieneDolorArticulaciones, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneDolorArticulaciones = mr.getCatKey();
                spinE1TieneDolorArticulaciones.setBackgroundColor(e1TieneDolorArticulaciones.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TieneParalisis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEPARALISIS_CONS, getString(R.string.paralisis), e1TieneParalisis, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneParalisis = mr.getCatKey();
                spinE1TieneParalisis.setBackgroundColor(e1TieneParalisis.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TieneMareos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEMAREOS_CONS, getString(R.string.mareos), e1TieneMareos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneMareos = mr.getCatKey();
                spinE1TieneMareos.setBackgroundColor(e1TieneMareos.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TienePensamientoNublado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEPENSAMIENTONUBLADO_CONS, getString(R.string.pensamientoNublado), e1TienePensamientoNublado, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TienePensamientoNublado = mr.getCatKey();
                spinE1TienePensamientoNublado.setBackgroundColor(e1TienePensamientoNublado.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TieneProblemasDormir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEPROBLEMASDORMIR_CONS, getString(R.string.problemasDormir), e1TieneProblemasDormir, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneProblemasDormir = mr.getCatKey();
                spinE1TieneProblemasDormir.setBackgroundColor(e1TieneProblemasDormir.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TieneDepresion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEDEPRESION_CONS, getString(R.string.depresion), e1TieneDepresion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneDepresion = mr.getCatKey();
                spinE1TieneDepresion.setBackgroundColor(e1TieneDepresion.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TieneOtrosSintomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIENEOTROSSINTOMAS_CONS, getString(R.string.otrosSintomas), e1TieneOtrosSintomas, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TieneOtrosSintomas = mr.getCatKey();
                if (e1TieneOtrosSintomas.equals(Constants.YESKEYSND)) {
                    spinE1TieneOtrosSintomas.setBackgroundColor(Color.RED);
                    textE1TieneCualesSintomas.setVisibility(View.VISIBLE);
                    inputE1TieneCualesSintomas.setVisibility(View.VISIBLE);
                } else {
                    spinE1TieneOtrosSintomas.setBackgroundColor(Color.WHITE);
                    textE1TieneCualesSintomas.setVisibility(View.GONE);
                    inputE1TieneCualesSintomas.setVisibility(View.GONE);
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
                spinPadeceDiscapacidadFis.setBackgroundColor(padeceDiscapacidadFis.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

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
                if (padeceOtraCondicion.equals(Constants.YESKEYSND)) {
                    spinPadeceOtraCondicion.setBackgroundColor(Color.RED);
                    textQueOtraCondicion.setVisibility(View.VISIBLE);
                    inputQueOtraCondicion.setVisibility(View.VISIBLE);
                } else {
                    spinPadeceOtraCondicion.setBackgroundColor(Color.WHITE);
                    textQueOtraCondicion.setVisibility(View.GONE);
                    inputQueOtraCondicion.setVisibility(View.GONE);
                    queOtraCondicion = null;
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
                createConfirmDialog(FUMADO_CONS, getString(R.string.fumado2), fumado, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                fumado = mr.getCatKey();
                if (fumado.equals(Constants.YESKEYSND)) {
                    spinFumado.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta8_10(View.VISIBLE);

                    if(dxEnfermoCovid19 != null){
                        if(dxEnfermoCovid19.equals(Constants.YESKEYSND)){
                            MostrarOcultarPregunta9(View.VISIBLE);
                        }else {
                            MostrarOcultarPregunta9(View.GONE);
                        }
                    }

                } else {
                    spinFumado.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta8_10(View.GONE);
                    MostrarOcultarPregunta9(View.GONE);
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
                createConfirmDialog(FUMADOCIENCIGARRILLOS_CONS, getString(R.string.fumadoCienCigarrillos2), fumadoCienCigarrillos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                fumadoCienCigarrillos = mr.getCatKey();
                if (fumadoCienCigarrillos.isEmpty() || fumadoCienCigarrillos.equals(Constants.NOKEYSND)) {
                    spinFumado.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1FumadoPrevioEnfermedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1FUMADOPREVIOENFERMEDAD_CONS, getString(R.string.fumadoPrevioEnfermedad2), e1FumadoPrevioEnfermedad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1FumadoPrevioEnfermedad = mr.getCatKey();
                spinE1FumadoPrevioEnfermedad.setBackgroundColor(e1FumadoPrevioEnfermedad.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1FumaActualmente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1FUMAACTUALMENTE_CONS, getString(R.string.fumaActualmente2), e1FumaActualmente, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1FumaActualmente = mr.getCatKey();
                spinE1FumaActualmente.setBackgroundColor(e1FumaActualmente.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1Embarazada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1EMBARAZADA_CONS, getString(R.string.embarazada2), e1Embarazada, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1Embarazada = mr.getCatKey();
                if (e1Embarazada.equals(Constants.YESKEYSND)) {
                    spinE1Embarazada.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta11a(View.VISIBLE);
                    MostrarOcultarPregunta11c(View.VISIBLE);
                    MostrarOcultarPregunta12(View.VISIBLE);
                } else {
                    spinE1Embarazada.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta11a(View.GONE);
                    MostrarOcultarPregunta11c(View.GONE);
                    MostrarOcultarPregunta12(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1RecuerdaSemanasEmb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1RECUERDASEMANASEMB_CONS, getString(R.string.recuerdaSemanasEmb), e1RecuerdaSemanasEmb, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1RecuerdaSemanasEmb = mr.getCatKey();
                if (e1RecuerdaSemanasEmb.equals(Constants.YESKEYSND)) {
                    spinE1RecuerdaSemanasEmb.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta11b(View.VISIBLE);
                } else {
                    spinE1RecuerdaSemanasEmb.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta11b(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1FinalEmbarazo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1FINALEMBARAZO_CONS, getString(R.string.finalEmbarazo), e1FinalEmbarazo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1FinalEmbarazo = mr.getCatKey();
                if (e1FinalEmbarazo.equals("998")) {
                    spinE1FinalEmbarazo.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta11d(View.VISIBLE);
                } else {
                    spinE1FinalEmbarazo.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta11d(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1DabaPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DABAPECHO_CONS, getString(R.string.dabaPecho2), e1DabaPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1DabaPecho = mr.getCatKey();
                spinE1DabaPecho.setBackgroundColor(e1DabaPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        spinVacunadoCovid19.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(VACUNADOCOVID19_CONS, getString(R.string.vacunadoCovid192), vacunadoCovid19, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                vacunadoCovid19 = mr.getCatKey();
                if (vacunadoCovid19.equals(Constants.YESKEYSND)) {
                    spinVacunadoCovid19.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta14(View.VISIBLE);
                   // MostrarOcultarPregunta32(View.VISIBLE);
                } else {
                    spinVacunadoCovid19.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta14(View.GONE);
                  //  MostrarOcultarPregunta32(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinMuestraTarjetaVac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(MUESTRATARJETAVAC_CONS, getString(R.string.muestraTarjetaVac2), muestraTarjetaVac, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                muestraTarjetaVac = mr.getCatKey();
                if (muestraTarjetaVac.equals(Constants.YESKEYSND)) {
                    spinMuestraTarjetaVac.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta14_Dosis(View.VISIBLE);
                    MostrarOcultarPregunta14a(View.GONE);
                } else if (muestraTarjetaVac.equals(Constants.NOKEYSND)) {
                    spinMuestraTarjetaVac.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta14_Dosis(View.GONE);
                    MostrarOcultarPregunta14a(View.VISIBLE);
                } else {
                    spinMuestraTarjetaVac.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta14_Dosis(View.GONE);
                    MostrarOcultarPregunta14a(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinSabeNombreVacuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SABENOMBREVACUNA_CONS, getString(R.string.sabeNombreVacuna), sabeNombreVacuna, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                sabeNombreVacuna = mr.getCatKey();
                if (sabeNombreVacuna.equals(Constants.YESKEYSND)) {
                    spinSabeNombreVacuna.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta14a_vacuna(View.VISIBLE);
                } else {
                    spinSabeNombreVacuna.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta14a_vacuna(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinAnioVacuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(ANIOVACUNA_CONS, getString(R.string.anioVacuna), anioVacuna, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                anioVacuna = mr.getCatKey();
                List<MessageResource> meses = new ArrayList<MessageResource>();
                //Agregar año 2022 para mostrar todos los meses 25/03/23
                if (anioVacuna != null && (anioVacuna.equalsIgnoreCase("2023"))) {
                    //  meses = mCatalogoMesesVacuna;
                    for (MessageResource mes : mCatalogoMesesVacuna) {
                        //No mostrar Enero para las vacunas MA2023 02/03/23
                        if (!mes.getCatKey().isEmpty() && mes.getOrder() != 1) {
                            meses.add(mes);
                        }
                    }

                } else /*if (anioVacuna != null && anioVacuna.equalsIgnoreCase("2022"))*/ { //Cambio para obtener los años del 2021 en adelante
                    for (MessageResource mes : mCatalogoMesesVacuna) {
                        if (!mes.getCatKey().isEmpty() && Integer.parseInt(mes.getCatKey()) >= 1 && Integer.parseInt(mes.getCatKey()) <= c.get(Calendar.MONTH) + 1) {
                            meses.add(mes);
                        }
                    }
                }
                meses.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
                Collections.sort(meses);
                ArrayAdapter<MessageResource> dataAdapterMeses = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, meses);
                dataAdapterMeses.setDropDownViewResource(R.layout.spinner_item);
                spinMesVacuna.setAdapter(dataAdapterMeses);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinMesVacuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(MESVACUNA_CONS, getString(R.string.mesVacuna), mesVacuna, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                mesVacuna = mr.getCatKey();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinCuantasDosis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(CUANTASDOSIS_CONS, getString(R.string.cuantasDosis), cuantasDosis, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                cuantasDosis = mr.getCatKey();
                switch (cuantasDosis) {
                    case "1":
                        MostrarOcultarPregunta14_Dosis1(View.VISIBLE);
                        MostrarOcultarPregunta14_Dosis2(View.GONE);
                        MostrarOcultarPregunta14_Dosis3(View.GONE);
                        break;
                    case "2":
                        MostrarOcultarPregunta14_Dosis1(View.VISIBLE);
                        MostrarOcultarPregunta14_Dosis2(View.VISIBLE);
                        MostrarOcultarPregunta14_Dosis3(View.GONE);
                        break;
                    case "3":
                        MostrarOcultarPregunta14_Dosis1(View.VISIBLE);
                        MostrarOcultarPregunta14_Dosis2(View.VISIBLE);
                        MostrarOcultarPregunta14_Dosis3(View.VISIBLE);
                        break;
                    default:
                        MostrarOcultarPregunta14_Dosis1(View.GONE);
                        MostrarOcultarPregunta14_Dosis2(View.GONE);
                        MostrarOcultarPregunta14_Dosis3(View.GONE);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinNombreDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(NOMBREDOSIS1_CONS, getString(R.string.nombreDosis1), nombreDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                nombreDosis1 = mr.getCatKey();
                if (nombreDosis1.equals("998")) {
                    spinNombreDosis1.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta14_Dosis1_Otro(View.VISIBLE);
                } else {
                    spinNombreDosis1.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta14_Dosis1_Otro(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinNombreDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(NOMBREDOSIS2_CONS, getString(R.string.nombreDosis2), nombreDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                nombreDosis2 = mr.getCatKey();
                if (nombreDosis2.equals("998")) {
                    spinNombreDosis2.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta14_Dosis2_Otro(View.VISIBLE);
                } else {
                    spinNombreDosis2.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta14_Dosis2_Otro(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinNombreDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(NOMBREDOSIS3_CONS, getString(R.string.nombreDosis3), nombreDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                nombreDosis3 = mr.getCatKey();
                if (nombreDosis3.equals("998")) {
                    spinNombreDosis3.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta14_Dosis3_Otro(View.VISIBLE);
                } else {
                    spinNombreDosis3.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta14_Dosis3_Otro(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mSaveView = (Button) getActivity().findViewById(R.id.save_button);
        mSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (validarEntrada()) {
                        new SaveDataTask().execute();
                    }
                } catch (ParseException e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        postCovidV3 = (TextView) rootView.findViewById(R.id.postCovidV3);
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

    private List<MessageResource> getMesesByAnio(String anio) {
        List<MessageResource> meses = new ArrayList<MessageResource>();
        //  if (anio != null && anio.equalsIgnoreCase("2021")) {24/02/23
        if (anio != null && !anio.equalsIgnoreCase("2024")) {
            for (MessageResource mes : mCatalogoMeses) {
                // Si es 2024 solo mostrar mes enero febrero
              //  if (periodoSintomas.equalsIgnoreCase(Constants.PERIODO_CUEST_COVID19_4)) {
                    if (!mes.getCatKey().isEmpty() && Integer.parseInt(mes.getCatKey()) >= 1 && Integer.parseInt(mes.getCatKey()) <= 12) {
                        meses.add(mes);
                    }
                /*else {
                    if (!mes.getCatKey().isEmpty() && Integer.parseInt(mes.getCatKey()) >= 11 && Integer.parseInt(mes.getCatKey()) <= 12) {
                        meses.add(mes);
                    }
                }*/
            }
        }


        /*else if (anio != null && anio.equalsIgnoreCase("2022")) {
            for (MessageResource mes : mCatalogoMeses) {
                if (!mes.getCatKey().isEmpty() && Integer.parseInt(mes.getCatKey()) >= 1 && Integer.parseInt(mes.getCatKey()) <= c.get(Calendar.MONTH) + 1) {
                    meses.add(mes);
                }
            }
        }*/
        /*Se realiza cambio para que acepte años despues de 2021*/
        else {
            for (MessageResource mes : mCatalogoMeses) {
                if (!mes.getCatKey().isEmpty() && Integer.parseInt(mes.getCatKey()) >= 1 && Integer.parseInt(mes.getCatKey()) <= c.get(Calendar.MONTH) + 1) {
                    meses.add(mes);
                }
            }
        }
        return meses;
    }

    private void setMesesSpinner(List<MessageResource> meses, Spinner spin) {
        meses.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
        Collections.sort(meses);
        ArrayAdapter<MessageResource> dataAdapterMeses = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, meses);
        dataAdapterMeses.setDropDownViewResource(R.layout.spinner_item);
        spin.setAdapter(dataAdapterMeses);
    }

    private String getAnioEvento(String fechaEvento, String anioEvento) {
        if (fechaEvento != null && !fechaEvento.isEmpty()) {
            Calendar c = Calendar.getInstance();
            try {
                Date fechaInicio = DateUtil.StringToDate(fechaEvento, "dd/MM/yyyy");
                c.setTime(fechaInicio);
                return String.valueOf(c.get(Calendar.YEAR));

            } catch (Exception ex) {
                ex.printStackTrace();
                return String.valueOf(c.get(Calendar.YEAR));
            }
        } else {
            return anioEvento;
        }
    }

    private DateMidnight getMinDateEvento(String fechaEvento, int anioEventoPeriodo, int mesEventoPeriodo, String anioEventoSelec, String mesEventoSelec) {
        DateMidnight minDate;
        if (fechaEvento != null && !fechaEvento.isEmpty()) {
            try {
                Date fechaInicio = DateUtil.StringToDate(fechaEvento, "dd/MM/yyyy");
                minDate = new DateMidnight(fechaInicio);
            } catch (Exception ex) {
                ex.printStackTrace();
                minDate = new DateMidnight(anioEventoPeriodo, mesEventoPeriodo, 1);
            }
        } else {
            minDate = new DateMidnight(Integer.parseInt(anioEventoSelec), Integer.parseInt(mesEventoSelec), 1);
        }
        return minDate;
    }

    /*2.¿Sabe cual fue la fecha de su última enfermedad de COVID-19?
    4.¿Sabe cuanto tiempo le tomo a usted o su niño/a recuperarse de los síntomas secundarios a covid-19 y recobrar su salud normal? */
    private void MostrarOcultarPregunta2_4(int estado) {
        if (estado == View.GONE) {
            spinSabeFechaUltEnf.setSelection(0, false);
            spinE1SabeTiempoRecuperacion.setSelection(0, false);

        }
        textSabeFechaUltEnf.setVisibility(estado);
        spinSabeFechaUltEnf.setVisibility(estado);
        textE1SabeTiempoRecuperacion.setVisibility(estado);
        spinE1SabeTiempoRecuperacion.setVisibility(estado);

        MostrarOcultarPregunta5(estado);
        if (participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 && participante.getEdadMeses() <= 600) {//solo si es mujer entre 18 y 50
            MostrarOcultarPregunta11(estado);

        } else {
            MostrarOcultarPregunta11(View.GONE);
        }
    }

    private void MostrarOcultarE1_MesAnio(int estado) {
        if (estado == View.GONE) {
            spinMesUltEnf.setSelection(0, false);
            spinAnioUltEnf.setSelection(0, false);
        }
        textMesUltEnf.setVisibility(estado);
        spinMesUltEnf.setVisibility(estado);
        textAnioUltEnf.setVisibility(estado);
        spinAnioUltEnf.setVisibility(estado);
    }

    /*3. ¿Usted o su niño/a que síntomas presenta aun?*/
    private void MostrarOcultarPregunta3(int estado) {
        if (estado == View.GONE) {
            spinE1TieneFebricula.setSelection(0, false);
            spinE1TieneCansancio.setSelection(0, false);
            spinE1TieneDolorCabeza.setSelection(0, false);
            spinE1TienePerdidaOlfato.setSelection(0, false);
            spinE1TienePerdidaGusto.setSelection(0, false);
            spinE1TieneTos.setSelection(0, false);
            spinE1TieneDificultadRespirar.setSelection(0, false);
            spinE1TieneDolorPecho.setSelection(0, false);
            spinE1TienePalpitaciones.setSelection(0, false);
            spinE1TieneDolorArticulaciones.setSelection(0, false);
            spinE1TieneParalisis.setSelection(0, false);
            spinE1TieneMareos.setSelection(0, false);
            spinE1TienePensamientoNublado.setSelection(0, false);
            spinE1TieneProblemasDormir.setSelection(0, false);
            spinE1TieneDepresion.setSelection(0, false);
            spinE1TieneOtrosSintomas.setSelection(0, false);

        }
        textE1QueSintomasPresenta.setVisibility(estado);
        spinE1TieneFebricula.setVisibility(estado);
        spinE1TieneCansancio.setVisibility(estado);
        spinE1TieneDolorCabeza.setVisibility(estado);
        spinE1TienePerdidaOlfato.setVisibility(estado);
        spinE1TienePerdidaGusto.setVisibility(estado);
        spinE1TieneTos.setVisibility(estado);
        spinE1TieneDificultadRespirar.setVisibility(estado);
        spinE1TieneDolorPecho.setVisibility(estado);
        spinE1TienePalpitaciones.setVisibility(estado);
        spinE1TieneDolorArticulaciones.setVisibility(estado);
        spinE1TieneParalisis.setVisibility(estado);
        spinE1TieneMareos.setVisibility(estado);
        spinE1TienePensamientoNublado.setVisibility(estado);
        spinE1TieneProblemasDormir.setVisibility(estado);
        spinE1TieneDepresion.setVisibility(estado);
        spinE1TieneOtrosSintomas.setVisibility(estado);
        textE1TieneFebricula.setVisibility(estado);
        textE1TieneCansancio.setVisibility(estado);
        textE1TieneDolorCabeza.setVisibility(estado);
        textE1TienePerdidaOlfato.setVisibility(estado);
        textE1TienePerdidaGusto.setVisibility(estado);
        textE1TieneTos.setVisibility(estado);
        textE1TieneDificultadRespirar.setVisibility(estado);
        textE1TieneDolorPecho.setVisibility(estado);
        textE1TienePalpitaciones.setVisibility(estado);
        textE1TieneDolorArticulaciones.setVisibility(estado);
        textE1TieneParalisis.setVisibility(estado);
        textE1TieneMareos.setVisibility(estado);
        textE1TienePensamientoNublado.setVisibility(estado);
        textE1TieneProblemasDormir.setVisibility(estado);
        textE1TieneDepresion.setVisibility(estado);
        textE1TieneOtrosSintomas.setVisibility(estado);
    }

    private void MostrarOcultarPregunta4a(int estado) {
        if (estado == View.GONE) spinE1TiempoRecuperacionEn.setSelection(0, false);
        textE1TiempoRecuperacion.setVisibility(estado);
        inputE1TiempoRecuperacion.setVisibility(estado);
        spinE1TiempoRecuperacionEn.setVisibility(estado);
    }

    /*5. [Si P1==”Si”] En el momento más severo de su enfermedad, diría que su enfermedad o la de su niño/a fue?*/
    private void MostrarOcultarPregunta5(int estado) {
        if (estado == View.GONE) spinE1SeveridadEnfermedad.setSelection(0, false);
        textE1SeveridadEnfermedad.setVisibility(estado);
        spinE1SeveridadEnfermedad.setVisibility(estado);
    }

    /*8 ¿Ha fumado al menos 100 cigarrillos en su vida?10. ¿Actualmente usted fuma cigarrillos todos los días o algunos días?*/
    private void MostrarOcultarPregunta8_10(int estado) {
        if (estado == View.GONE){
            spinFumadoCienCigarrillos.setSelection(0, false);
            spinE1FumaActualmente.setSelection(0, false);
        }
        textFumadoCienCigarrillos.setVisibility(estado);
        textE1FumaActualmente.setVisibility(estado);
        spinFumadoCienCigarrillos.setVisibility(estado);
        spinE1FumaActualmente.setVisibility(estado);
    }
// 9. ¿En los dias previos a que se enfermara, usted fumaba cigarrillos todos los días o algunos dias previo a que se enfermara?
    private void MostrarOcultarPregunta9(int estado) {
        if (estado == View.GONE){
            spinE1FumadoPrevioEnfermedad.setSelection(0, false);
        }
        textE1FumadoPrevioEnfermedad.setVisibility(estado);
        spinE1FumadoPrevioEnfermedad.setVisibility(estado);

    }

    /*11 ¿Usted estaba embarazada al momento de ser diagnosticada o sospechosa de COVID19?*/
    private void MostrarOcultarPregunta11(int estado) {
        if (estado == View.GONE) spinE1Embarazada.setSelection(0, false);
        textE1Embarazada.setVisibility(estado);
        spinE1Embarazada.setVisibility(estado);
    }

    /*Recuerda las semanas de embarazo que tenia**/
    private void MostrarOcultarPregunta11a(int estado) {
        if (estado == View.GONE) spinE1RecuerdaSemanasEmb.setSelection(0, false);
        textE1RecuerdaSemanasEmb.setVisibility(estado);
        spinE1RecuerdaSemanasEmb.setVisibility(estado);
    }
    /*Semanas de embarazo */
    private void MostrarOcultarPregunta11b(int estado) {
        textE1SemanasEmbarazo.setVisibility(estado);
        inputE1SemanasEmbarazo.setVisibility(estado);
    }

    /*como finalizo el embarazo*/
    private void MostrarOcultarPregunta11c(int estado) {
        if (estado == View.GONE) spinE1FinalEmbarazo.setSelection(0, false);
        textE1FinalEmbarazo.setVisibility(estado);
        spinE1FinalEmbarazo.setVisibility(estado);
    }

    private void MostrarOcultarPregunta11d(int estado) {
        textE1OtroFinalEmbarazo.setVisibility(estado);
        inputE1OtroFinalEmbarazo.setVisibility(estado);
    }

    /*12. Si estaba embarazada recientemente al momento de ser diagnosticada con COVID19, ¿Usted estaba dando pecho materno?*/
    private void MostrarOcultarPregunta12(int estado) {
        if (estado == View.GONE) spinE1DabaPecho.setSelection(0, false);
        textE1DabaPecho.setVisibility(estado);
        spinE1DabaPecho.setVisibility(estado);
    }


    /*14. ¿Puede mostrarnos su tarjeta de vacunación contra la COVID 19?*/
    private void MostrarOcultarPregunta14(int estado) {
        if (estado == View.GONE) spinMuestraTarjetaVac.setSelection(0, false);
        textMuestraTarjetaVac.setVisibility(estado);
        spinMuestraTarjetaVac.setVisibility(estado);
    }

    private void MostrarOcultarPregunta14a(int estado) {
        if (estado == View.GONE) spinSabeNombreVacuna.setSelection(0, false);
        textSabeNombreVacuna.setVisibility(estado);
        spinSabeNombreVacuna.setVisibility(estado);
    }

    private void MostrarOcultarPregunta14a_vacuna(int estado) {
        textNombreVacuna.setVisibility(estado);
        inputNombreVacuna.setVisibility(estado);
        textAnioVacuna.setVisibility(estado);
        if (estado == View.GONE) spinAnioVacuna.setSelection(0, false);
        spinAnioVacuna.setVisibility(estado);
        if (estado == View.GONE) spinMesVacuna.setSelection(0, false);
        textMesVacuna.setVisibility(estado);
        spinMesVacuna.setVisibility(estado);
    }

    private void MostrarOcultarPregunta14_Dosis(int estado) {
        if (estado == View.GONE) spinCuantasDosis.setSelection(0, false);
        textCuantasDosis.setVisibility(estado);
        spinCuantasDosis.setVisibility(estado);
    }

    private void MostrarOcultarPregunta14_Dosis1(int estado) {
        if (estado == View.GONE) spinNombreDosis1.setSelection(0, false);
        textNombreDosis1.setVisibility(estado);
        spinNombreDosis1.setVisibility(estado);
        textLoteDosis1.setVisibility(estado);
        inputLoteDosis1.setVisibility(estado);
        layoutFechaDosis1.setVisibility(estado);
        textFechaDosis1.setVisibility(estado);
        inputFechaDosis1.setVisibility(estado);
        imbFechaDosis1.setVisibility(estado);
    }

    private void MostrarOcultarPregunta14_Dosis1_Otro(int estado) {
        textOtraVacunaDosis1.setVisibility(estado);
        inputOtraVacunaDosis1.setVisibility(estado);
    }

    private void MostrarOcultarPregunta14_Dosis2(int estado) {
        if (estado == View.GONE) spinNombreDosis2.setSelection(0, false);
        textNombreDosis2.setVisibility(estado);
        spinNombreDosis2.setVisibility(estado);
        textLoteDosis2.setVisibility(estado);
        inputLoteDosis2.setVisibility(estado);
        layoutFechaDosis2.setVisibility(estado);
        textFechaDosis2.setVisibility(estado);
        inputFechaDosis2.setVisibility(estado);
        imbFechaDosis2.setVisibility(estado);
    }

    private void MostrarOcultarPregunta14_Dosis2_Otro(int estado) {
        textOtraVacunaDosis2.setVisibility(estado);
        inputOtraVacunaDosis2.setVisibility(estado);
    }

    private void MostrarOcultarPregunta14_Dosis3(int estado) {
        if (estado == View.GONE) spinNombreDosis3.setSelection(0, false);
        textNombreDosis3.setVisibility(estado);
        spinNombreDosis3.setVisibility(estado);
        textLoteDosis3.setVisibility(estado);
        inputLoteDosis3.setVisibility(estado);
        layoutFechaDosis3.setVisibility(estado);
        textFechaDosis3.setVisibility(estado);
        inputFechaDosis3.setVisibility(estado);
        imbFechaDosis3.setVisibility(estado);
    }

    private void MostrarOcultarPregunta14_Dosis3_Otro(int estado) {
        textOtraVacunaDosis3.setVisibility(estado);
        inputOtraVacunaDosis3.setVisibility(estado);
    }

    private void actualizarListaEventos() {
        List<MessageResource> meses = new ArrayList<MessageResource>();
        meses.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
       /* if (fechaEvento1 != null && !fechaEvento1.isEmpty()) {
            meses.add(new MessageResource(fechaEvento1, 0, fechaEvento1));
            spinE1SabeInicioSintoma.setSelection(0, false);

        }else */ if (mesUltEnf != null && !mesUltEnf.isEmpty()) {
            meses.add(new MessageResource(mesUltEnf + "/" + anioUltEnf, 0, mesUltEnf + "/" + anioUltEnf));
        }

        Collections.sort(meses);
        ArrayAdapter<MessageResource> dataAdapterMeses = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, meses);
        dataAdapterMeses.setDropDownViewResource(R.layout.spinner_item);
        //spinFechaEventoEnfermoPostVac.setAdapter(dataAdapterMeses);
    }

    private void createConfirmDialog(final String pregunta, String textoPregunta, String valorActual, final String nuevoValor, final String nuevoTexto, final int position) {
        if (mostrarConfirmacion && !nuevoValor.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getActivity().getString(R.string.confirmarValor, nuevoTexto, textoPregunta));
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    mostrarConfirmacion = true;
                    switch (pregunta) {
                        case DXENFERMOCOVID19_CONS:
                            dxEnfermoCovid19 = nuevoValor;
                            dxEnfermoCovid19Pos = position;
                            break;
                        case E1TIENEFEBRICULA_CONS:
                            e1TieneFebricula = nuevoValor;
                            e1TieneFebriculaPos = position;
                            break;
                        case E1TIENECANSANCIO_CONS:
                            e1TieneCansancio = nuevoValor;
                            e1TieneCansancioPos = position;
                            break;
                        case E1TIENEDOLORCABEZA_CONS:
                            e1TieneDolorCabeza = nuevoValor;
                            e1TieneDolorCabezaPos = position;
                            break;
                        case E1TIENEPERDIDAOLFATO_CONS:
                            e1TienePerdidaOlfato = nuevoValor;
                            e1TienePerdidaOlfatoPos = position;
                            break;
                        case E1TIENEPERDIDAGUSTO_CONS:
                            e1TienePerdidaGusto = nuevoValor;
                            e1TienePerdidaGustoPos = position;
                            break;
                        case E1TIENETOS_CONS:
                            e1TieneTosPos = position;
                            e1TieneTos = nuevoValor;
                            break;
                        case E1TIENEDIFICULTADRESPIRAR_CONS:
                            e1TieneDificultadRespirar = nuevoValor;
                            e1TieneDificultadRespirarPos = position;
                            break;
                        case E1TIENEDOLORPECHO_CONS:
                            e1TieneDolorPecho = nuevoValor;
                            e1TieneDolorPechoPos = position;
                            break;
                        case E1TIENEPALPITACIONES_CONS:
                            e1TienePalpitaciones = nuevoValor;
                            e1TienePalpitacionesPos = position;
                            break;
                        case E1TIENEDOLORARTICULACIONES_CONS:
                            e1TieneDolorArticulaciones = nuevoValor;
                            e1TieneDolorArticulacionesPos = position;
                            break;
                        case E1TIENEPARALISIS_CONS:
                            e1TieneParalisis = nuevoValor;
                            e1TieneParalisisPos = position;
                            break;
                        case E1TIENEMAREOS_CONS:
                            e1TieneMareos = nuevoValor;
                            e1TieneMareosPos = position;
                            break;
                        case E1TIENEPENSAMIENTONUBLADO_CONS:
                            e1TienePensamientoNublado = nuevoValor;
                            e1TienePensamientoNubladoPos = position;
                            break;
                        case E1TIENEPROBLEMASDORMIR_CONS:
                            e1TieneProblemasDormir = nuevoValor;
                            e1TieneProblemasDormirPos = position;
                            break;
                        case E1TIENEDEPRESION_CONS:
                            e1TieneDepresion = nuevoValor;
                            e1TieneDepresionPos = position;
                            break;
                        case E1TIENEOTROSSINTOMAS_CONS:
                            e1TieneOtrosSintomas = nuevoValor;
                            e1TieneOtrosSintomasPos = position;
                            break;
                        case E1SABETIEMPORECUPERACION_CONS:
                            e1SabeTiempoRecuperacion = nuevoValor;
                            e1SabeTiempoRecuperacionPos = position;
                            break;
                        case E1TIEMPORECUPERACIONEN_CONS:
                            e1TiempoRecuperacionEn = nuevoValor;
                            e1TiempoRecuperacionEnPos = position;
                            break;
                        case E1SEVERIDADENFERMEDAD_CONS:
                            e1SeveridadEnfermedad = nuevoValor;
                            e1SeveridadEnfermedadPos = position;
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
                        case FUMADO_CONS:
                            fumado = nuevoValor;
                            fumadoPos = position;
                            break;
                        case FUMADOCIENCIGARRILLOS_CONS:
                            fumadoCienCigarrillos = nuevoValor;
                            fumadoCienCigarrillosPos = position;
                            break;

                        case E1FUMADOPREVIOENFERMEDAD_CONS:
                            e1FumadoPrevioEnfermedad = nuevoValor;
                            e1FumadoPrevioEnfermedadPos = position;
                            break;
                        case E1FUMAACTUALMENTE_CONS:
                            e1FumaActualmente = nuevoValor;
                            e1FumaActualmentePos = position;
                            break;
                        case E1EMBARAZADA_CONS:
                            e1Embarazada = nuevoValor;
                            e1EmbarazadaPos = position;
                            break;
                        case E1RECUERDASEMANASEMB_CONS:
                            e1RecuerdaSemanasEmb = nuevoValor;
                            e1RecuerdaSemanasEmbPos = position;
                            break;
                        case E1FINALEMBARAZO_CONS:
                            e1FinalEmbarazo = nuevoValor;
                            e1FinalEmbarazoPos = position;
                            break;
                        case E1DABAPECHO_CONS:
                            e1DabaPecho = nuevoValor;
                            e1DabaPechoPos = position;
                            break;
                        case VACUNADOCOVID19_CONS:
                            vacunadoCovid19 = nuevoValor;
                            vacunadoCovid19Pos = position;
                            break;
                        case MUESTRATARJETAVAC_CONS:
                            muestraTarjetaVac = nuevoValor;
                            muestraTarjetaVacPos = position;
                            break;
                        case SABENOMBREVACUNA_CONS:
                            sabeNombreVacuna = nuevoValor;
                            sabeNombreVacunaPos = position;
                            break;
                        case MESVACUNA_CONS:
                            mesVacuna = nuevoValor;
                            mesVacunaPos = position;
                            break;
                        case CUANTASDOSIS_CONS:
                            cuantasDosis = nuevoValor;
                            cuantasDosisPos = position;
                            break;
                        case NOMBREDOSIS1_CONS:
                            nombreDosis1 = nuevoValor;
                            nombreDosis1Pos = position;
                            break;
                        case NOMBREDOSIS2_CONS:
                            nombreDosis2 = nuevoValor;
                            nombreDosis2Pos = position;
                            break;
                        case NOMBREDOSIS3_CONS:
                            nombreDosis3 = nuevoValor;
                            nombreDosis3Pos = position;
                            break;
                        default:
                            break;
                    }
                }
            });
            builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    mostrarConfirmacion = false;
                    switch (pregunta) {
                        case DXENFERMOCOVID19_CONS:
                            spinDxEnfermoCovid19.setSelection(dxEnfermoCovid19Pos, false);
                            break;
                        case E1TIENEFEBRICULA_CONS:
                            spinE1TieneFebricula.setSelection(e1TieneFebriculaPos, false);
                            break;
                        case E1TIENECANSANCIO_CONS:
                            spinE1TieneCansancio.setSelection(e1TieneCansancioPos, false);
                            break;
                        case E1TIENEDOLORCABEZA_CONS:
                            spinE1TieneDolorCabeza.setSelection(e1TieneDolorCabezaPos, false);
                            break;
                        case E1TIENEPERDIDAOLFATO_CONS:
                            spinE1TienePerdidaOlfato.setSelection(e1TienePerdidaOlfatoPos, false);
                            break;
                        case E1TIENEPERDIDAGUSTO_CONS:
                            spinE1TienePerdidaGusto.setSelection(e1TienePerdidaGustoPos, false);
                            break;
                        case E1TIENETOS_CONS:
                            spinE1TieneTos.setSelection(e1TieneTosPos, false);
                            break;
                        case E1TIENEDIFICULTADRESPIRAR_CONS:
                            spinE1TieneDificultadRespirar.setSelection(e1TieneDificultadRespirarPos, false);
                            break;
                        case E1TIENEDOLORPECHO_CONS:
                            spinE1TieneDolorPecho.setSelection(e1TieneDolorPechoPos, false);
                            break;
                        case E1TIENEPALPITACIONES_CONS:
                            spinE1TienePalpitaciones.setSelection(e1TienePalpitacionesPos, false);
                            break;
                        case E1TIENEDOLORARTICULACIONES_CONS:
                            spinE1TieneDolorArticulaciones.setSelection(e1TieneDolorArticulacionesPos, false);
                            break;
                        case E1TIENEPARALISIS_CONS:
                            spinE1TieneParalisis.setSelection(e1TieneParalisisPos, false);
                            break;
                        case E1TIENEMAREOS_CONS:
                            spinE1TieneMareos.setSelection(e1TieneMareosPos, false);
                            break;
                        case E1TIENEPENSAMIENTONUBLADO_CONS:
                            spinE1TienePensamientoNublado.setSelection(e1TienePensamientoNubladoPos, false);
                            break;
                        case E1TIENEPROBLEMASDORMIR_CONS:
                            spinE1TieneProblemasDormir.setSelection(e1TieneProblemasDormirPos, false);
                            break;
                        case E1TIENEDEPRESION_CONS:
                            spinE1TieneDepresion.setSelection(e1TieneDepresionPos, false);
                            break;
                        case E1TIENEOTROSSINTOMAS_CONS:
                            spinE1TieneOtrosSintomas.setSelection(e1TieneOtrosSintomasPos, false);
                            break;
                        case E1TIENECUALESSINTOMAS_CONS:
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
                        case FUMADO_CONS:
                            spinFumado.setSelection(fumadoPos, false);
                            break;
                        case FUMADOCIENCIGARRILLOS_CONS:
                            spinFumadoCienCigarrillos.setSelection(fumadoCienCigarrillosPos, false);
                            break;

                        case E1FUMADOPREVIOENFERMEDAD_CONS:
                            spinE1FumadoPrevioEnfermedad.setSelection(e1FumadoPrevioEnfermedadPos, false);
                            break;
                        case E1FUMAACTUALMENTE_CONS:
                            spinE1FumaActualmente.setSelection(e1FumaActualmentePos, false);
                            break;
                        case E1EMBARAZADA_CONS:
                            spinE1Embarazada.setSelection(e1EmbarazadaPos, false);
                            break;
                        case E1RECUERDASEMANASEMB_CONS:
                            spinE1RecuerdaSemanasEmb.setSelection(e1RecuerdaSemanasEmbPos, false);
                            break;
                        case E1FINALEMBARAZO_CONS:
                            spinE1FinalEmbarazo.setSelection(e1FinalEmbarazoPos, false);
                            break;
                        case E1DABAPECHO_CONS:
                            spinE1DabaPecho.setSelection(e1DabaPechoPos, false);
                            break;
                        case VACUNADOCOVID19_CONS:
                            spinVacunadoCovid19.setSelection(vacunadoCovid19Pos, false);
                            break;
                        case MUESTRATARJETAVAC_CONS:
                            spinMuestraTarjetaVac.setSelection(muestraTarjetaVacPos, false);
                            break;
                        case SABENOMBREVACUNA_CONS:
                            spinSabeNombreVacuna.setSelection(sabeNombreVacunaPos, false);
                            break;
                        case MESVACUNA_CONS:
                            spinMesVacuna.setSelection(mesVacunaPos, false);
                            break;
                        case CUANTASDOSIS_CONS:
                            spinCuantasDosis.setSelection(cuantasDosisPos, false);
                            break;
                        case NOMBREDOSIS1_CONS:
                            spinNombreDosis1.setSelection(nombreDosis1Pos, false);
                            break;
                        case NOMBREDOSIS2_CONS:
                            spinNombreDosis2.setSelection(nombreDosis2Pos, false);
                            break;
                        case NOMBREDOSIS3_CONS:
                            spinNombreDosis3.setSelection(nombreDosis3Pos, false);
                            break;
                        default:
                            break;
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
        int anio1 = 2023;
        int anio2 = 2024;
        int mesEventos = 2;
      /*  if (periodoSintomas.equalsIgnoreCase(Constants.PERIODO_CUEST_COVID19_4))
            mesEventos = 11;*/
        switch (dialog) {
            case FECHA_VACUNA1:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaDosis1 = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputFechaDosis1.setText(fechaDosis1);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio1, 2, 1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_VACUNA2:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaDosis2 = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputFechaDosis2.setText(fechaDosis2);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                if (fechaDosis1 != null && !fechaDosis1.isEmpty()) {
                    try {
                        Date fechaInicio = DateUtil.StringToDate(fechaDosis1, "dd/MM/yyyy");
                        minDate = new DateMidnight(fechaInicio);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        minDate = new DateMidnight(anio2, 1, 1);
                    }
                } else {
                    minDate = new DateMidnight(anio2, 1, 1);
                }
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_VACUNA3:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaDosis3 = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputFechaDosis3.setText(fechaDosis3);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                if (fechaDosis2 != null && !fechaDosis2.isEmpty()) {
                    try {
                        Date fechaInicio = DateUtil.StringToDate(fechaDosis2, "dd/MM/yyyy");
                        minDate = new DateMidnight(fechaInicio);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        minDate = new DateMidnight(anio2, 1, 1);
                    }
                } else {
                    minDate = new DateMidnight(anio2, 1, 1);
                }
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

        if (faltaDatoRequerido(dxEnfermoCovid19, R.string.dxEnfermoCovid19)) return false;
        else if (faltaDatoRequeridoHijo(anioUltEnf, R.string.anioEvento, sabeFechaUltEnf, Constants.YESKEYSND))
            return false;
        else if (faltaDatoRequeridoHijo(mesUltEnf, R.string.mesEvento, sabeFechaUltEnf, Constants.YESKEYSND))
            return false;
        else if (faltaDatoRequeridoHijo(sabeFechaUltEnf, R.string.sabeFechaUltimaEnf, dxEnfermoCovid19, Constants.YESKEYSND))
            return false;
        else if (faltaDatoRequeridoHijo(sabeFechaUltEnf, R.string.sabeFechaUltimaEnf, dxEnfermoCovid19, Constants.YESKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneFebricula, R.string.febricula, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneCansancio, R.string.cansancio, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneDolorCabeza, R.string.dolorCabeza, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TienePerdidaOlfato, R.string.perdidaOlfato, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TienePerdidaGusto, R.string.perdidaGusto, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneTos, R.string.tos, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneDificultadRespirar, R.string.dificultadRespirar, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneDolorPecho, R.string.dolorPecho, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TienePalpitaciones, R.string.palpitaciones, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneDolorArticulaciones, R.string.dolorArticulaciones, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneParalisis, R.string.paralisis, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneMareos, R.string.mareos, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TienePensamientoNublado, R.string.pensamientoNublado, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneProblemasDormir, R.string.problemasDormir, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneDepresion, R.string.depresion, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneOtrosSintomas, R.string.otrosSintomas, dxEnfermoCovid19, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijo(e1TieneCualesSintomas, R.string.cualesSintomas, e1TieneOtrosSintomas, Constants.YESKEYSND))
            return false;
       // else if (faltaDatoRequeridoHijo(e1SabeTiempoRecuperacion, R.string.sabeTiempoRecuperacion2, e1RecuperadoCovid19, Constants.YESKEYSND))
         else if (faltaDatoRequeridoHijo(e1SabeTiempoRecuperacion, R.string.sabeTiempoRecuperacion2, dxEnfermoCovid19, Constants.YESKEYSND))
            return false;
        else if (faltaDatoRequeridoHijo(e1TiempoRecuperacion, R.string.tiempoRecuperacion, e1SabeTiempoRecuperacion))
            return false;
        else if (faltaDatoRequeridoHijo(e1TiempoRecuperacionEn, R.string.tiempoRecuperacionEn, e1SabeTiempoRecuperacion))
            return false;
        else if (faltaDatoRequeridoHijoContador(e1SeveridadEnfermedad, R.string.severidadEnfermedad2, dxEnfermoCovid19, 1))
            return false;
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
        else if (participante.getEdadMeses() >= 168 && faltaDatoRequerido(fumado, R.string.fumado2)) return false;
        else if (participante.getEdadMeses() >= 168 && faltaDatoRequeridoHijo(fumadoCienCigarrillos, R.string.fumadoCienCigarrillos2, fumado))
            return false;
        else if (participante.getEdadMeses() >= 168 &&
                ((dxEnfermoCovid19 != null && !dxEnfermoCovid19.isEmpty()) && dxEnfermoCovid19.equals(Constants.YESKEYSND)) && fumado.equals(Constants.YESKEYSND)
                && (e1FumadoPrevioEnfermedad == null || e1FumadoPrevioEnfermedad.equals(""))) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect, getActivity().getString(R.string.fumadoPrevioEnfermedad2)), Toast.LENGTH_LONG).show();
            return false;
        }   else if (participante.getEdadMeses() >= 168 &&
                 faltaDatoRequeridoHijoNegado(e1FumaActualmente, R.string.fumaActualmente2, fumado, Constants.NOKEYSND))
            return false;
        else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 && participante.getEdadMeses() <= 600)
               // && faltaDatoRequeridoHijoContador(e1Embarazada, R.string.embarazada2, cuantasVecesEnfermo, 1))
               && faltaDatoRequeridoHijoContador(e1Embarazada, R.string.embarazada2, dxEnfermoCovid19, 1))
            return false;
        else if (faltaDatoRequeridoHijo(e1RecuerdaSemanasEmb, R.string.recuerdaSemanasEmb, e1Embarazada)) return false;
        else if (faltaDatoRequeridoHijo(e1SemanasEmbarazo, R.string.semanasEmbarazoCovid, e1RecuerdaSemanasEmb))return false;
        else if (faltaDatoRequeridoHijo(e1FinalEmbarazo, R.string.finalEmbarazo, e1Embarazada))return false;
            //else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) && faltaDatoRequeridoHijo(dabaPecho, R.string.dabaPecho, enfermoCovid19)) return false;
        else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 && participante.getEdadMeses() <= 600) && faltaDatoRequeridoHijo(e1DabaPecho, R.string.dabaPecho2, e1Embarazada))
            return false;
        else if (faltaDatoRequerido(vacunadoCovid19, R.string.vacunadoCovid192)) return false;
        else if (faltaDatoRequeridoHijo(muestraTarjetaVac, R.string.muestraTarjetaVac2, vacunadoCovid19)) return false;
        else if (faltaDatoRequeridoHijo(sabeNombreVacuna, R.string.sabeNombreVacuna, muestraTarjetaVac, Constants.NOKEYSND))
            return false;
        else if (faltaDatoRequeridoHijo(nombreVacuna, R.string.nombreVacuna, sabeNombreVacuna)) return false;
        else if (faltaDatoRequeridoHijo(mesVacuna, R.string.mesVacuna, sabeNombreVacuna)) return false;
        else if (faltaDatoRequeridoHijo(anioVacuna, R.string.anioVacuna, sabeNombreVacuna)) return false;
        else if (faltaDatoRequeridoHijo(cuantasDosis, R.string.cuantasDosis, muestraTarjetaVac, Constants.YESKEYSND))
            return false;
        else if (faltaDatoRequeridoHijoContador(nombreDosis1, R.string.nombreDosis1, cuantasDosis, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(loteDosis1, R.string.loteDosis1, cuantasDosis, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(fechaDosis1, R.string.fechaDosis1, cuantasDosis, 1)) return false;
        else if (faltaDatoRequeridoHijo(otraVacunaDosis1, R.string.cualVacuna, nombreDosis1, "998")) return false;
        else if (faltaDatoRequeridoHijoContador(nombreDosis2, R.string.nombreDosis2, cuantasDosis, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(loteDosis2, R.string.loteDosis2, cuantasDosis, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(fechaDosis2, R.string.fechaDosis2, cuantasDosis, 2)) return false;
        else if (faltaDatoRequeridoHijo(otraVacunaDosis2, R.string.cualVacuna, nombreDosis2, "998")) return false;
        else if (faltaDatoRequeridoHijoContador(nombreDosis3, R.string.nombreDosis3, cuantasDosis, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(loteDosis3, R.string.loteDosis3, cuantasDosis, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(fechaDosis3, R.string.fechaDosis3, cuantasDosis, 3)) return false;
        else if (faltaDatoRequeridoHijo(otraVacunaDosis3, R.string.cualVacuna, nombreDosis3, "998")) return false;
        else {
            mCuestionario.setCodigo(infoMovil.getId());
            mCuestionario.setParticipante(participante);
            mCuestionario.setPeriodoSintomas(periodoSintomas);
            mCuestionario.setE1TieneFebricula(e1TieneFebricula);
            mCuestionario.setE1TieneCansancio(e1TieneCansancio);
            mCuestionario.setE1TieneDolorCabeza(e1TieneDolorCabeza);
            mCuestionario.setE1TienePerdidaOlfato(e1TienePerdidaOlfato);
            mCuestionario.setE1TienePerdidaGusto(e1TienePerdidaGusto);
            mCuestionario.setE1TieneTos(e1TieneTos);
            mCuestionario.setE1TieneDificultadRespirar(e1TieneDificultadRespirar);
            mCuestionario.setE1TieneDolorPecho(e1TieneDolorPecho);
            mCuestionario.setE1TienePalpitaciones(e1TienePalpitaciones);
            mCuestionario.setE1TieneDolorArticulaciones(e1TieneDolorArticulaciones);
            mCuestionario.setE1TieneParalisis(e1TieneParalisis);
            mCuestionario.setE1TieneMareos(e1TieneMareos);
            mCuestionario.setE1TienePensamientoNublado(e1TienePensamientoNublado);
            mCuestionario.setE1TieneProblemasDormir(e1TieneProblemasDormir);
            mCuestionario.setE1TieneDepresion(e1TieneDepresion);
            mCuestionario.setE1TieneOtrosSintomas(e1TieneOtrosSintomas);
            mCuestionario.setE1cualesSintomas(e1TieneCualesSintomas);
            mCuestionario.setE1SabeTiempoRecuperacion(e1SabeTiempoRecuperacion);
            mCuestionario.setE1TiempoRecuperacion(e1TiempoRecuperacion);
            mCuestionario.setE1TiempoRecuperacionEn(e1TiempoRecuperacionEn);
            mCuestionario.setE1SeveridadEnfermedad(e1SeveridadEnfermedad);
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

            mCuestionario.setE1FumadoPrevioEnfermedad(e1FumadoPrevioEnfermedad);
            mCuestionario.setE1FumaActualmente(e1FumaActualmente);
            mCuestionario.setE1Embarazada(e1Embarazada);
            mCuestionario.setE1RecuerdaSemanasEmb(e1RecuerdaSemanasEmb);
            mCuestionario.setE1SemanasEmbarazo(e1SemanasEmbarazo);
            mCuestionario.setE1FinalEmbarazo(e1FinalEmbarazo);
            mCuestionario.setE1OtroFinalEmbarazo(e1OtroFinalEmbarazo);
            mCuestionario.setE1DabaPecho(e1DabaPecho);
            mCuestionario.setVacunadoCovid19(vacunadoCovid19);
            mCuestionario.setMuestraTarjetaVac(muestraTarjetaVac);
            mCuestionario.setSabeNombreVacuna(sabeNombreVacuna);
            mCuestionario.setNombreVacuna(nombreVacuna);
            mCuestionario.setAnioVacuna(anioVacuna);
            mCuestionario.setMesVacuna(mesVacuna);
            mCuestionario.setCuantasDosis(cuantasDosis);
            mCuestionario.setNombreDosis1(nombreDosis1);
            mCuestionario.setOtraVacunaDosis1(otraVacunaDosis1);
            mCuestionario.setLoteDosis1(loteDosis1);
            if (fechaDosis1 != null && !fechaDosis1.isEmpty())
                mCuestionario.setFechaDosis1(formatter.parse(fechaDosis1));
            mCuestionario.setNombreDosis2(nombreDosis2);
            mCuestionario.setOtraVacunaDosis2(otraVacunaDosis2);
            mCuestionario.setLoteDosis2(loteDosis2);
            if (fechaDosis2 != null && !fechaDosis2.isEmpty())
                mCuestionario.setFechaDosis2(formatter.parse(fechaDosis2));
            mCuestionario.setNombreDosis3(nombreDosis3);
            mCuestionario.setOtraVacunaDosis3(otraVacunaDosis3);
            mCuestionario.setLoteDosis3(loteDosis3);
            if (fechaDosis3 != null && !fechaDosis3.isEmpty())
                mCuestionario.setFechaDosis3(formatter.parse(fechaDosis3));

            //MA 2024
            mCuestionario.setDxEnfermoCovid19(dxEnfermoCovid19);
            mCuestionario.setSabeFechaUltEnf(sabeFechaUltEnf);
            mCuestionario.setAnioUltEnf(anioUltEnf);
            mCuestionario.setMesUltEnf(mesUltEnf);

            mCuestionario.setRecordDate(new Date());
            mCuestionario.setRecordUser(username);
            mCuestionario.setDeviceid(infoMovil.getDeviceId());

            mCuestionario.setEstado('0');
            mCuestionario.setPasive('0');
            return true;
        }

    }

    //Mensaje para validar Preg #1 Si En caso de Síntomas Covid PostVacuna
    private boolean respuestaPosRequerida(String variable, int resId) {
        if (variable == null || variable.equals("")) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.enfermoCovid19_yes, getActivity().getString(resId)), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequerido(String variable, int resId) {
        if (variable == null || variable.equals("")) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect, getActivity().getString(resId)), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    /***
     * Validar si no se ha ingresado respuesta a una pregunta requerida que depende(hijo) de la respuesta de otra pregunta anterior(padre).
     * Por defecto la respuesta de la pregunta padre debe ser Constants.YESKEYSND para que sea requerida la respuesta hija
     * @param variable valor de la variable hija
     * @param resId id del recurso en el fragment
     * @param padre valor de la variable padre
     * @return true si la respuesta de la pregunta padre es Constants.YESKEYSND y no se ha ingresado respuesta a la hija+, false en caso contrario
     */
    private boolean faltaDatoRequeridoHijo(String variable, int resId, String padre) {
        if (padre != null && padre.equals(Constants.YESKEYSND) && (variable == null || variable.equals(""))) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect, getActivity().getString(resId)), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijo(Integer variable, int resId, String padre) {
        if (padre != null && padre.equals(Constants.YESKEYSND) && (variable == null || variable <= 0)) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect, getActivity().getString(resId)), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijo(String variable, int resId, String padre, String valorPadre) {
        if (padre != null && padre.equals(valorPadre) && (variable == null || variable.equals(""))) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect, getActivity().getString(resId)), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijoNegado(String variable, int resId, String padre, String valorPadre) {
        if (padre != null && !padre.isEmpty() && !padre.equals(valorPadre) && (variable == null || variable.equals(""))) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect, getActivity().getString(resId)), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijoContains(String variable, int resId, String padre, String valorPadre) {
        if (padre != null && padre.toLowerCase().contains(valorPadre) && (variable == null || variable.equals(""))) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect, getActivity().getString(resId)), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijoContador(String variable, int resId, String padre, Integer valorPadre) {
        if (padre == null || padre.isEmpty()) return false;
        if (Integer.parseInt(padre) >= valorPadre && (variable == null || variable.isEmpty())) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect, getActivity().getString(resId)), Toast.LENGTH_LONG).show();
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
             //   mMedicamentos = Arrays.asList(estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='COVID_MEDICAMENTO_SVC'", CatalogosDBConstants.order));
                mCatalogoSeveridad = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_SEVERIDAD'", CatalogosDBConstants.order);
                mCatalogoTiempo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_TIEMPO_BA'", CatalogosDBConstants.order);
                mCatalogoDondeBA = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_DONDE_BA'", CatalogosDBConstants.order);
                mCatalogoTipoSeg = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_TIPO_SEG'", CatalogosDBConstants.order);
                mCatalogoUnidadMedTR = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_UNIDAD_MED_TR'", CatalogosDBConstants.order);
                //mCatalogoMeses = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", CatalogosDBConstants.order);
                mCatalogoVecesEnfermo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_VECES_ENF'", CatalogosDBConstants.order);
                List<MessageResource> meses = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", CatalogosDBConstants.order);
                mCatalogoTiempoOxigeno = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_TIEMPO_OXI'", CatalogosDBConstants.order);
                mCatalogoVacunas = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_VACUNA'", CatalogosDBConstants.order);
                mCatalogoDosis = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_DOSIS'", CatalogosDBConstants.order);
                mCatalogoAniosVac = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_ANIO_VAC'", CatalogosDBConstants.order);
               //MA 2024 no mostrar años para vacuna menor a 2023
                mCatalogoAnios = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_ANIO_VAC' and " + CatalogosDBConstants.catKey + " !='2021' and " + CatalogosDBConstants.catKey + " !='2020' and " + CatalogosDBConstants.catKey + " !='2022'", CatalogosDBConstants.order);

                //MA 2024
                mCatalogoSiNoD = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_SND'", CatalogosDBConstants.order);

                mCatalogoMeses = meses;
                mCatalogoMesesVacuna = meses;
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
            mCatalogoSiNo.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoSiNo);
            ArrayAdapter<MessageResource> dataAdapterSiNo = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSiNo);
            dataAdapterSiNo.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoSiNoNc.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoSiNoNc);
            ArrayAdapter<MessageResource> dataAdapterSiNoNc = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSiNoNc);
            dataAdapterSiNoNc.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoSiNsNc.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoSiNsNc);
            ArrayAdapter<MessageResource> dataAdapterSiNsNc = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSiNsNc);
            dataAdapterSiNsNc.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoSiNoNsNc.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoSiNoNsNc);
            ArrayAdapter<MessageResource> dataAdapterSiNoNsNc = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSiNoNsNc);
            dataAdapterSiNoNsNc.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoFuma.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoFuma);
            ArrayAdapter<MessageResource> dataAdapterFuma = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoFuma);
            dataAdapterFuma.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoSeveridad.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoSeveridad);
            ArrayAdapter<MessageResource> dataAdapterSev = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSeveridad);
            dataAdapterSev.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoTiempo.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoTiempo);
            ArrayAdapter<MessageResource> dataAdapterTiempo = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoTiempo);
            dataAdapterTiempo.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoDondeBA.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoDondeBA);
            ArrayAdapter<MessageResource> dataAdapterDBA = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoDondeBA);
            dataAdapterDBA.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoUnidadMedTR.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoUnidadMedTR);
            ArrayAdapter<MessageResource> dataAdapterUM = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoUnidadMedTR);
            dataAdapterUM.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoTipoSeg.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoTipoSeg);
            ArrayAdapter<MessageResource> dataAdapterTipoSeg = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoTipoSeg);
            dataAdapterTipoSeg.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoMeses.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoMeses);
            ArrayAdapter<MessageResource> dataAdapterMeses = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoMeses);
            dataAdapterMeses.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoFinalEmb.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoFinalEmb);
            ArrayAdapter<MessageResource> dataAdapterFinalEmbarazo = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoFinalEmb);
            dataAdapterFinalEmbarazo.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoVecesEnfermo.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoVecesEnfermo);
            ArrayAdapter<MessageResource> dataAdapterVecesEnfermo = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoVecesEnfermo);
            dataAdapterVecesEnfermo.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoTiempoOxigeno.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoTiempoOxigeno);
            ArrayAdapter<MessageResource> dataAdapterTmpOxigeno = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoTiempoOxigeno);
            dataAdapterTmpOxigeno.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoVacunas.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoVacunas);
            ArrayAdapter<MessageResource> dataAdapterVacunas = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoVacunas);
            dataAdapterVacunas.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoDosis.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoDosis);
            ArrayAdapter<MessageResource> dataAdapterDosis = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoDosis);
            dataAdapterDosis.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoMesesVacuna.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoMesesVacuna);
            ArrayAdapter<MessageResource> dataAdapterMesesVac = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoMesesVacuna);
            dataAdapterMesesVac.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoAnios.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoAnios);
            ArrayAdapter<MessageResource> dataAdapterAnio = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoAnios);
            dataAdapterAnio.setDropDownViewResource(R.layout.spinner_item);

            //MA 2024
            mCatalogoSiNoD.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoSiNoD);
            ArrayAdapter<MessageResource> dataAdapterSiNoD = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoSiNoD);
            dataAdapterSiNoD.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoAniosVac.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoAniosVac);
            ArrayAdapter<MessageResource> dataAdapterAnioVac = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoAniosVac);
            dataAdapterAnioVac.setDropDownViewResource(R.layout.spinner_item);

            spinDxEnfermoCovid19.setAdapter(dataAdapterSiNoD);
            spinSabeFechaUltEnf.setAdapter(dataAdapterSiNoD);
            spinAnioUltEnf.setAdapter(dataAdapterAnioVac);
            spinFumado.setAdapter(dataAdapterSiNo);
            spinE1SabeTiempoRecuperacion.setAdapter(dataAdapterSiNsNc);
            spinE1SeveridadEnfermedad.setAdapter(dataAdapterSev);
            spinE1TiempoRecuperacionEn.setAdapter(dataAdapterUM);
            spinE1FumadoPrevioEnfermedad.setAdapter(dataAdapterFuma);
            spinE1FumaActualmente.setAdapter(dataAdapterFuma);
            spinE1TieneFebricula.setAdapter(dataAdapterSiNoNsNc);
            spinE1TieneCansancio.setAdapter(dataAdapterSiNoNsNc);
            spinE1TieneDolorCabeza.setAdapter(dataAdapterSiNoNsNc);
            spinE1TienePerdidaOlfato.setAdapter(dataAdapterSiNoNsNc);
            spinE1TienePerdidaGusto.setAdapter(dataAdapterSiNoNsNc);
            spinE1TieneTos.setAdapter(dataAdapterSiNoNsNc);
            spinE1TieneDificultadRespirar.setAdapter(dataAdapterSiNoNsNc);
            spinE1TieneDolorPecho.setAdapter(dataAdapterSiNoNsNc);
            spinE1TienePalpitaciones.setAdapter(dataAdapterSiNoNsNc);
            spinE1TieneDolorArticulaciones.setAdapter(dataAdapterSiNoNsNc);
            spinE1TieneParalisis.setAdapter(dataAdapterSiNoNsNc);
            spinE1TieneMareos.setAdapter(dataAdapterSiNoNsNc);
            spinE1TienePensamientoNublado.setAdapter(dataAdapterSiNoNsNc);
            spinE1TieneProblemasDormir.setAdapter(dataAdapterSiNoNsNc);
            spinE1TieneDepresion.setAdapter(dataAdapterSiNoNsNc);
            spinE1TieneOtrosSintomas.setAdapter(dataAdapterSiNoNsNc);
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
            spinE1Embarazada.setAdapter(dataAdapterSiNoNc);
            spinE1RecuerdaSemanasEmb.setAdapter(dataAdapterSiNo);
            spinE1DabaPecho.setAdapter(dataAdapterSiNoNc);
            spinE1FinalEmbarazo.setAdapter(dataAdapterFinalEmbarazo);
            spinVacunadoCovid19.setAdapter(dataAdapterSiNoNsNc);
            spinMuestraTarjetaVac.setAdapter(dataAdapterSiNoNc);
            spinSabeNombreVacuna.setAdapter(dataAdapterSiNoNc);
            spinAnioVacuna.setAdapter(dataAdapterAnio);
            spinMesVacuna.setAdapter(dataAdapterMesesVac);
            spinCuantasDosis.setAdapter(dataAdapterDosis);
            spinNombreDosis1.setAdapter(dataAdapterVacunas);
            spinNombreDosis2.setAdapter(dataAdapterVacunas);
            spinNombreDosis3.setAdapter(dataAdapterVacunas);
            //verificandoCovid();
        }
    }

    /*public void verificandoCovid() {
        if (participante != null) {
            if (participante.getProcesos() != null) {
                if(participante.getProcesos().getPosCovid() != null) {
                    if (participante.getProcesos().getPosCovid().contains("SARS-COV2")) {
                        spinDxEnfermoCovid19.setSelection(1);
                        spinSabeFechaUltEnf.setSelection(1);
                        postCovidV3.setVisibility(View.VISIBLE);
                        postCovidV3.setText(participante.getProcesos().getPosCovid());
                        postCovidV3.setTextColor(Color.parseColor("#C70039"));
                    } else {
                        postCovidV3.setVisibility(View.GONE);
                        postCovidV3.setText("");
                        postCovidV3.setTextColor(Color.parseColor("#66000000"));
                    }
                }
            }
        }
    }*/

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
            if (!resultado.equals("exito")) {
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
            } else {
                Bundle arguments = new Bundle();
                Intent i = new Intent(getActivity(),
                        MenuInfoActivity.class);
                i.putExtra(ConstantsDB.CODIGO, participante.getCodigo());
                i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtras(arguments);
                startActivity(i);
                getActivity().finish();
            }
        }

    }
}
