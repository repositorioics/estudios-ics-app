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
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DateUtil;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import org.joda.time.DateMidnight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NuevoCuestionarioCovid19v2Fragment extends Fragment {

    private static final String TAG = "NuevoCuestionarioCovid19v2Fragment";
    protected static final int FECHA_SINTOMA_E1 = 101;
    protected static final int FECHA_ADMISION_E1 = 102;
    protected static final int FECHA_ALTA_E1 = 103;
    protected static final int FECHA_SINTOMA_E2 = 104;
    protected static final int FECHA_ADMISION_E2 = 105;
    protected static final int FECHA_ALTA_E2 = 106;
    protected static final int FECHA_SINTOMA_E3 = 107;
    protected static final int FECHA_ADMISION_E3 = 108;
    protected static final int FECHA_ALTA_E3 = 109;
    protected static final int FECHA_EVENTO1 = 110;
    protected static final int FECHA_EVENTO2 = 111;
    protected static final int FECHA_EVENTO3 = 112;
    protected static final int FECHA_VACUNA1 = 113;
    protected static final int FECHA_VACUNA2 = 114;
    protected static final int FECHA_VACUNA3 = 115;
    protected static final int FECHA_ENFPOSTVAC = 116;

    protected static final String ENFERMOCOVID19_CONS = "ENFERMOCOVID19";
    protected static final String CUANTASVECESENFERMO_CONS = "CUANTASVECESENFERMO";
    protected static final String SABEEVENTO1_CONS = "SABEEVENTO1";
    protected static final String EVENTO1_CONS = "EVENTO1";
    protected static final String ANIOEVENTO1_CONS = "ANIOEVENTO1";
    protected static final String MESEVENTO1_CONS = "MESEVENTO1";
    protected static final String SABEEVENTO2_CONS = "SABEEVENTO2";
    protected static final String EVENTO2_CONS = "EVENTO2";
    protected static final String ANIOEVENTO2_CONS = "ANIOEVENTO2";
    protected static final String MESEVENTO2_CONS = "MESEVENTO2";
    protected static final String SABEEVENTO3_CONS = "SABEEVENTO3";
    protected static final String EVENTO3_CONS = "EVENTO3";
    protected static final String ANIOEVENTO3_CONS = "ANIOEVENTO3";
    protected static final String MESEVENTO3_CONS = "MESEVENTO3";
    protected static final String E1FEBRICULA_CONS = "E1FEBRICULA";
    protected static final String E1FIEBRE_CONS = "E1FIEBRE";
    protected static final String E1ESCALOFRIO_CONS = "E1ESCALOFRIO";
    protected static final String E1TEMBLORESCALOFRIO_CONS = "E1TEMBLORESCALOFRIO";
    protected static final String E1DOLORMUSCULAR_CONS = "E1DOLORMUSCULAR";
    protected static final String E1DOLORARTICULAR_CONS = "E1DOLORARTICULAR";
    protected static final String E1SECRESIONNASAL_CONS = "E1SECRESIONNASAL";
    protected static final String E1DOLORGARGANTA_CONS = "E1DOLORGARGANTA";
    protected static final String E1TOS_CONS = "E1TOS";
    protected static final String E1DIFICULTADRESP_CONS = "E1DIFICULTADRESP";
    protected static final String E1DOLORPECHO_CONS = "E1DOLORPECHO";
    protected static final String E1NAUSEASVOMITO_CONS = "E1NAUSEASVOMITO";
    protected static final String E1DOLORCABEZA_CONS = "E1DOLORCABEZA";
    protected static final String E1DOLORABDOMINAL_CONS = "E1DOLORABDOMINAL";
    protected static final String E1DIARREA_CONS = "E1DIARREA";
    protected static final String E1DIFICULTADDORMIR_CONS = "E1DIFICULTADDORMIR";
    protected static final String E1DEBILIDAD_CONS = "E1DEBILIDAD";
    protected static final String E1PERDIDAOLFATOGUSTO_CONS = "E1PERDIDAOLFATOGUSTO";
    protected static final String E1MAREO_CONS = "E1MAREO";
    protected static final String E1SARPULLIDO_CONS = "E1SARPULLIDO";
    protected static final String E1DESMAYO_CONS = "E1DESMAYO";
    protected static final String E1QUEDOCAMA_CONS = "E1QUEDOCAMA";
    protected static final String E2FEBRICULA_CONS = "E2FEBRICULA";
    protected static final String E2FIEBRE_CONS = "E2FIEBRE";
    protected static final String E2ESCALOFRIO_CONS = "E2ESCALOFRIO";
    protected static final String E2TEMBLORESCALOFRIO_CONS = "E2TEMBLORESCALOFRIO";
    protected static final String E2DOLORMUSCULAR_CONS = "E2DOLORMUSCULAR";
    protected static final String E2DOLORARTICULAR_CONS = "E2DOLORARTICULAR";
    protected static final String E2SECRESIONNASAL_CONS = "E2SECRESIONNASAL";
    protected static final String E2DOLORGARGANTA_CONS = "E2DOLORGARGANTA";
    protected static final String E2TOS_CONS = "E2TOS";
    protected static final String E2DIFICULTADRESP_CONS = "E2DIFICULTADRESP";
    protected static final String E2DOLORPECHO_CONS = "E2DOLORPECHO";
    protected static final String E2NAUSEASVOMITO_CONS = "E2NAUSEASVOMITO";
    protected static final String E2DOLORCABEZA_CONS = "E2DOLORCABEZA";
    protected static final String E2DOLORABDOMINAL_CONS = "E2DOLORABDOMINAL";
    protected static final String E2DIARREA_CONS = "E2DIARREA";
    protected static final String E2DIFICULTADDORMIR_CONS = "E2DIFICULTADDORMIR";
    protected static final String E2DEBILIDAD_CONS = "E2DEBILIDAD";
    protected static final String E2PERDIDAOLFATOGUSTO_CONS = "E2PERDIDAOLFATOGUSTO";
    protected static final String E2MAREO_CONS = "E2MAREO";
    protected static final String E2SARPULLIDO_CONS = "E2SARPULLIDO";
    protected static final String E2DESMAYO_CONS = "E2DESMAYO";
    protected static final String E2QUEDOCAMA_CONS = "E2QUEDOCAMA";
    protected static final String E3FEBRICULA_CONS = "E3FEBRICULA";
    protected static final String E3FIEBRE_CONS = "E3FIEBRE";
    protected static final String E3ESCALOFRIO_CONS = "E3ESCALOFRIO";
    protected static final String E3TEMBLORESCALOFRIO_CONS = "E3TEMBLORESCALOFRIO";
    protected static final String E3DOLORMUSCULAR_CONS = "E3DOLORMUSCULAR";
    protected static final String E3DOLORARTICULAR_CONS = "E3DOLORARTICULAR";
    protected static final String E3SECRESIONNASAL_CONS = "E3SECRESIONNASAL";
    protected static final String E3DOLORGARGANTA_CONS = "E3DOLORGARGANTA";
    protected static final String E3TOS_CONS = "E3TOS";
    protected static final String E3DIFICULTADRESP_CONS = "E3DIFICULTADRESP";
    protected static final String E3DOLORPECHO_CONS = "E3DOLORPECHO";
    protected static final String E3NAUSEASVOMITO_CONS = "E3NAUSEASVOMITO";
    protected static final String E3DOLORCABEZA_CONS = "E3DOLORCABEZA";
    protected static final String E3DOLORABDOMINAL_CONS = "E3DOLORABDOMINAL";
    protected static final String E3DIARREA_CONS = "E3DIARREA";
    protected static final String E3DIFICULTADDORMIR_CONS = "E3DIFICULTADDORMIR";
    protected static final String E3DEBILIDAD_CONS = "E3DEBILIDAD";
    protected static final String E3PERDIDAOLFATOGUSTO_CONS = "E3PERDIDAOLFATOGUSTO";
    protected static final String E3MAREO_CONS = "E3MAREO";
    protected static final String E3SARPULLIDO_CONS = "E3SARPULLIDO";
    protected static final String E3DESMAYO_CONS = "E3DESMAYO";
    protected static final String E3QUEDOCAMA_CONS = "E3QUEDOCAMA";

    protected static final String E1SABEFIS_CONS = "E1SABEFIS";
    protected static final String E1FIS_CONS = "E1FIS";
    protected static final String E1MESINICIOSINTOMA_CONS = "E1MESINICIOSINTOMA";
    protected static final String E1ANIOINICIOSINTOMA_CONS = "E1ANIOINICIOSINTOMA";
    protected static final String E1CONOCELUGAREXPOSICION_CONS = "E1CONOCELUGAREXPOSICION";
    protected static final String E1LUGAREXPOSICION_CONS = "E1LUGAREXPOSICION";
    protected static final String E1BUSCOAYUDA_CONS = "E1BUSCOAYUDA";
    protected static final String E1DONDEBUSCOAYUDA_CONS = "E1DONDEBUSCOAYUDA";
    protected static final String E1NOMBRECENTROSALUD_CONS = "E1NOMBRECENTROSALUD";
    protected static final String E1NOMBREHOSPITAL_CONS = "E1NOMBREHOSPITAL";
    protected static final String E1RECIBIOSEGUIMIENTO_CONS = "E1RECIBIOSEGUIMIENTO";
    protected static final String E1TIPOSEGUIMIENTO_CONS = "E1TIPOSEGUIMIENTO";
    protected static final String E1TMPDESPUESBUSCOAYUDA_CONS = "E1TMPDESPUESBUSCOAYUDA";
    protected static final String E1UNANOCHEHOSPITAL_CONS = "E1UNANOCHEHOSPITAL";
    protected static final String E1QUEHOSPITAL_CONS = "E1QUEHOSPITAL";
    protected static final String E1SABECUANTASNOCHES_CONS = "E1SABECUANTASNOCHES";
    protected static final String E1CUANTASNOCHESHOSP_CONS = "E1CUANTASNOCHESHOSP";
    protected static final String E1SABEFECHAADMISION_CONS = "E1SABEFECHAADMISION";
    protected static final String E1FECHAADMISIONHOSP_CONS = "E1FECHAADMISIONHOSP";
    protected static final String E1SABEFECHAALTA_CONS = "E1SABEFECHAALTA";
    protected static final String E1FECHAALTAHOSP_CONS = "E1FECHAALTAHOSP";
    protected static final String E1UTILIZOOXIGENO_CONS = "E1UTILIZOOXIGENO";
    protected static final String E1ESTUVOUCI_CONS = "E1ESTUVOUCI";
    protected static final String E1FUEINTUBADO_CONS = "E1FUEINTUBADO";
    protected static final String E1RECUPERADOCOVID19_CONS = "E1RECUPERADOCOVID19";
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
    protected static final String E1TOMOMEDICAMENTO_CONS = "E1TOMOMEDICAMENTO";
    protected static final String E1QUEMEDICAMENTO_CONS = "E1QUEMEDICAMENTO";
    protected static final String E1OTROMEDICAMENTO_CONS = "E1OTROMEDICAMENTO";
    protected static final String E1OXIGENODOMICILIO_CONS = "E1OXIGENODOMICILIO";
    protected static final String E1TIEMPOOXIGENODOM_CONS = "E1TIEMPOOXIGENODOM";

    protected static final String E2SABEFIS_CONS = "E2SABEFIS";
    protected static final String E2FIS_CONS = "E2FIS";
    protected static final String E2MESINICIOSINTOMA_CONS = "E2MESINICIOSINTOMA";
    protected static final String E2ANIOINICIOSINTOMA_CONS = "E2ANIOINICIOSINTOMA";
    protected static final String E2CONOCELUGAREXPOSICION_CONS = "E2CONOCELUGAREXPOSICION";
    protected static final String E2LUGAREXPOSICION_CONS = "E2LUGAREXPOSICION";
    protected static final String E2BUSCOAYUDA_CONS = "E2BUSCOAYUDA";
    protected static final String E2DONDEBUSCOAYUDA_CONS = "E2DONDEBUSCOAYUDA";
    protected static final String E2NOMBRECENTROSALUD_CONS = "E2NOMBRECENTROSALUD";
    protected static final String E2NOMBREHOSPITAL_CONS = "E2NOMBREHOSPITAL";
    protected static final String E2RECIBIOSEGUIMIENTO_CONS = "E2RECIBIOSEGUIMIENTO";
    protected static final String E2TIPOSEGUIMIENTO_CONS = "E2TIPOSEGUIMIENTO";
    protected static final String E2TMPDESPUESBUSCOAYUDA_CONS = "E2TMPDESPUESBUSCOAYUDA";
    protected static final String E2UNANOCHEHOSPITAL_CONS = "E2UNANOCHEHOSPITAL";
    protected static final String E2QUEHOSPITAL_CONS = "E2QUEHOSPITAL";
    protected static final String E2SABECUANTASNOCHES_CONS = "E2SABECUANTASNOCHES";
    protected static final String E2CUANTASNOCHESHOSP_CONS = "E2CUANTASNOCHESHOSP";
    protected static final String E2SABEFECHAADMISION_CONS = "E2SABEFECHAADMISION";
    protected static final String E2FECHAADMISIONHOSP_CONS = "E2FECHAADMISIONHOSP";
    protected static final String E2SABEFECHAALTA_CONS = "E2SABEFECHAALTA";
    protected static final String E2FECHAALTAHOSP_CONS = "E2FECHAALTAHOSP";
    protected static final String E2UTILIZOOXIGENO_CONS = "E2UTILIZOOXIGENO";
    protected static final String E2ESTUVOUCI_CONS = "E2ESTUVOUCI";
    protected static final String E2FUEINTUBADO_CONS = "E2FUEINTUBADO";
    protected static final String E2RECUPERADOCOVID19_CONS = "E2RECUPERADOCOVID19";
    protected static final String E2TIENEFEBRICULA_CONS = "E2TIENEFEBRICULA";
    protected static final String E2TIENECANSANCIO_CONS = "E2TIENECANSANCIO";
    protected static final String E2TIENEDOLORCABEZA_CONS = "E2TIENEDOLORCABEZA";
    protected static final String E2TIENEPERDIDAOLFATO_CONS = "E2TIENEPERDIDAOLFATO";
    protected static final String E2TIENEPERDIDAGUSTO_CONS = "E2TIENEPERDIDAGUSTO";
    protected static final String E2TIENETOS_CONS = "E2TIENETOS";
    protected static final String E2TIENEDIFICULTADRESPIRAR_CONS = "E2TIENEDIFICULTADRESPIRAR";
    protected static final String E2TIENEDOLORPECHO_CONS = "E2TIENEDOLORPECHO";
    protected static final String E2TIENEPALPITACIONES_CONS = "E2TIENEPALPITACIONES";
    protected static final String E2TIENEDOLORARTICULACIONES_CONS = "E2TIENEDOLORARTICULACIONES";
    protected static final String E2TIENEPARALISIS_CONS = "E2TIENEPARALISIS";
    protected static final String E2TIENEMAREOS_CONS = "E2TIENEMAREOS";
    protected static final String E2TIENEPENSAMIENTONUBLADO_CONS = "E2TIENEPENSAMIENTONUBLADO";
    protected static final String E2TIENEPROBLEMASDORMIR_CONS = "E2TIENEPROBLEMASDORMIR";
    protected static final String E2TIENEDEPRESION_CONS = "E2TIENEDEPRESION";
    protected static final String E2TIENEOTROSSINTOMAS_CONS = "E2TIENEOTROSSINTOMAS";
    protected static final String E2TIENECUALESSINTOMAS_CONS = "E2TIENECUALESSINTOMAS";
    protected static final String E2SABETIEMPORECUPERACION_CONS = "E2SABETIEMPORECUPERACION";
    protected static final String E2TIEMPORECUPERACION_CONS = "E2TIEMPORECUPERACION";
    protected static final String E2TIEMPORECUPERACIONEN_CONS = "E2TIEMPORECUPERACIONEN";
    protected static final String E2SEVERIDADENFERMEDAD_CONS = "E2SEVERIDADENFERMEDAD";
    protected static final String E2TOMOMEDICAMENTO_CONS = "E2TOMOMEDICAMENTO";
    protected static final String E2QUEMEDICAMENTO_CONS = "E2QUEMEDICAMENTO";
    protected static final String E2OTROMEDICAMENTO_CONS = "E2OTROMEDICAMENTO";
    protected static final String E2OXIGENODOMICILIO_CONS = "E2OXIGENODOMICILIO";
    protected static final String E2TIEMPOOXIGENODOM_CONS = "E2TIEMPOOXIGENODOM";

    protected static final String E3SABEFIS_CONS = "E3SABEFIS";
    protected static final String E3FIS_CONS = "E3FIS";
    protected static final String E3MESINICIOSINTOMA_CONS = "E3MESINICIOSINTOMA";
    protected static final String E3ANIOINICIOSINTOMA_CONS = "E3ANIOINICIOSINTOMA";
    protected static final String E3CONOCELUGAREXPOSICION_CONS = "E3CONOCELUGAREXPOSICION";
    protected static final String E3LUGAREXPOSICION_CONS = "E3LUGAREXPOSICION";
    protected static final String E3BUSCOAYUDA_CONS = "E3BUSCOAYUDA";
    protected static final String E3DONDEBUSCOAYUDA_CONS = "E3DONDEBUSCOAYUDA";
    protected static final String E3NOMBRECENTROSALUD_CONS = "E3NOMBRECENTROSALUD";
    protected static final String E3NOMBREHOSPITAL_CONS = "E3NOMBREHOSPITAL";
    protected static final String E3RECIBIOSEGUIMIENTO_CONS = "E3RECIBIOSEGUIMIENTO";
    protected static final String E3TIPOSEGUIMIENTO_CONS = "E3TIPOSEGUIMIENTO";
    protected static final String E3TMPDESPUESBUSCOAYUDA_CONS = "E3TMPDESPUESBUSCOAYUDA";
    protected static final String E3UNANOCHEHOSPITAL_CONS = "E3UNANOCHEHOSPITAL";
    protected static final String E3QUEHOSPITAL_CONS = "E3QUEHOSPITAL";
    protected static final String E3SABECUANTASNOCHES_CONS = "E3SABECUANTASNOCHES";
    protected static final String E3CUANTASNOCHESHOSP_CONS = "E3CUANTASNOCHESHOSP";
    protected static final String E3SABEFECHAADMISION_CONS = "E3SABEFECHAADMISION";
    protected static final String E3FECHAADMISIONHOSP_CONS = "E3FECHAADMISIONHOSP";
    protected static final String E3SABEFECHAALTA_CONS = "E3SABEFECHAALTA";
    protected static final String E3FECHAALTAHOSP_CONS = "E3FECHAALTAHOSP";
    protected static final String E3UTILIZOOXIGENO_CONS = "E3UTILIZOOXIGENO";
    protected static final String E3ESTUVOUCI_CONS = "E3ESTUVOUCI";
    protected static final String E3FUEINTUBADO_CONS = "E3FUEINTUBADO";
    protected static final String E3RECUPERADOCOVID19_CONS = "E3RECUPERADOCOVID19";
    protected static final String E3TIENEFEBRICULA_CONS = "E3TIENEFEBRICULA";
    protected static final String E3TIENECANSANCIO_CONS = "E3TIENECANSANCIO";
    protected static final String E3TIENEDOLORCABEZA_CONS = "E3TIENEDOLORCABEZA";
    protected static final String E3TIENEPERDIDAOLFATO_CONS = "E3TIENEPERDIDAOLFATO";
    protected static final String E3TIENEPERDIDAGUSTO_CONS = "E3TIENEPERDIDAGUSTO";
    protected static final String E3TIENETOS_CONS = "E3TIENETOS";
    protected static final String E3TIENEDIFICULTADRESPIRAR_CONS = "E3TIENEDIFICULTADRESPIRAR";
    protected static final String E3TIENEDOLORPECHO_CONS = "E3TIENEDOLORPECHO";
    protected static final String E3TIENEPALPITACIONES_CONS = "E3TIENEPALPITACIONES";
    protected static final String E3TIENEDOLORARTICULACIONES_CONS = "E3TIENEDOLORARTICULACIONES";
    protected static final String E3TIENEPARALISIS_CONS = "E3TIENEPARALISIS";
    protected static final String E3TIENEMAREOS_CONS = "E3TIENEMAREOS";
    protected static final String E3TIENEPENSAMIENTONUBLADO_CONS = "E3TIENEPENSAMIENTONUBLADO";
    protected static final String E3TIENEPROBLEMASDORMIR_CONS = "E3TIENEPROBLEMASDORMIR";
    protected static final String E3TIENEDEPRESION_CONS = "E3TIENEDEPRESION";
    protected static final String E3TIENEOTROSSINTOMAS_CONS = "E3TIENEOTROSSINTOMAS";
    protected static final String E3TIENECUALESSINTOMAS_CONS = "E3TIENECUALESSINTOMAS";
    protected static final String E3SABETIEMPORECUPERACION_CONS = "E3SABETIEMPORECUPERACION";
    protected static final String E3TIEMPORECUPERACION_CONS = "E3TIEMPORECUPERACION";
    protected static final String E3TIEMPORECUPERACIONEN_CONS = "E3TIEMPORECUPERACIONEN";
    protected static final String E3SEVERIDADENFERMEDAD_CONS = "E3SEVERIDADENFERMEDAD";
    protected static final String E3TOMOMEDICAMENTO_CONS = "E3TOMOMEDICAMENTO";
    protected static final String E3QUEMEDICAMENTO_CONS = "E3QUEMEDICAMENTO";
    protected static final String E3OTROMEDICAMENTO_CONS = "E3OTROMEDICAMENTO";
    protected static final String E3OXIGENODOMICILIO_CONS = "E3OXIGENODOMICILIO";
    protected static final String E3TIEMPOOXIGENODOM_CONS = "E3TIEMPOOXIGENODOM";

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

    protected static final String E2FUMADOPREVIOENFERMEDAD_CONS = "E2FUMADOPREVIOENFERMEDAD";
    protected static final String E2FUMAACTUALMENTE_CONS = "E2FUMAACTUALMENTE";
    protected static final String E2EMBARAZADA_CONS = "E2EMBARAZADA_CONS";
    protected static final String E2RECUERDASEMANASEMB_CONS = "E2RECUERDASEMANASEMB_CONS";
    protected static final String E2FINALEMBARAZO_CONS = "E2FINALEMBARAZO_CONS";
    protected static final String E2DABAPECHO_CONS = "E2DABAPECHO_CONS";

    protected static final String E3FUMADOPREVIOENFERMEDAD_CONS = "E3FUMADOPREVIOENFERMEDAD";
    protected static final String E3FUMAACTUALMENTE_CONS = "E3FUMAACTUALMENTE";
    protected static final String E3EMBARAZADA_CONS = "E3EMBARAZADA_CONS";
    protected static final String E3RECUERDASEMANASEMB_CONS = "E3RECUERDASEMANASEMB_CONS";
    protected static final String E3FINALEMBARAZO_CONS = "E3FINALEMBARAZO_CONS";
    protected static final String E3DABAPECHO_CONS = "E3DABAPECHO_CONS";

    protected static final String TRABAJADORSALUD_CONS = "TRABAJADORSALUD_CONS";
    protected static final String VACUNADOCOVID19_CONS = "VACUNADOCOVID19";
    protected static final String MUESTRATARJETAVAC_CONS = "MUESTRATARJETAVAC";
    protected static final String SABENOMBREVACUNA_CONS = "SABENOMBREVACUNA";
    protected static final String MESVACUNA_CONS = "MESVACUNA";
    protected static final String CUANTASDOSIS_CONS = "CUANTASDOSIS";
    protected static final String NOMBREDOSIS1_CONS = "NOMBREDOSIS1";
    protected static final String NOMBREDOSIS2_CONS = "NOMBREDOSIS2";
    protected static final String NOMBREDOSIS3_CONS = "NOMBREDOSIS3";

    protected static final String PRESENTOSINTOMASDOSIS1_CONS = "PRESENTOSINTOMASDOSIS1";
    protected static final String DOLORSITIODOSIS1_CONS = "DOLORSITIODOSIS1";
    protected static final String FIEBREDOSIS1_CONS = "FIEBREDOSIS1";
    protected static final String CANSANCIODOSIS1_CONS = "CANSANCIODOSIS1";
    protected static final String DOLORCABEZADOSIS1_CONS = "DOLORCABEZADOSIS1";
    protected static final String DIARREADOSIS1_CONS = "DIARREADOSIS1";
    protected static final String VOMITODOSIS1_CONS = "VOMITODOSIS1";
    protected static final String DOLORMUSCULARDOSIS1_CONS = "DOLORMUSCULARDOSIS1";
    protected static final String ESCALOFRIOSDOSIS1_CONS = "ESCALOFRIOSDOSIS1";
    protected static final String REACCIONALERGICADOSIS1_CONS = "REACCIONALERGICADOSIS1";
    protected static final String INFECCIONSITIODOSIS1_CONS = "INFECCIONSITIODOSIS1";
    protected static final String NAUSEASDOSIS1_CONS = "NAUSEASDOSIS1";
    protected static final String BULTOSDOSIS1_CONS = "BULTOSDOSIS1";
    protected static final String OTROSDOSIS1_CONS = "OTROSDOSIS1";

    protected static final String PRESENTOSINTOMASDOSIS2_CONS = "PRESENTOSINTOMASDOSIS2";
    protected static final String DOLORSITIODOSIS2_CONS = "DOLORSITIODOSIS2";
    protected static final String FIEBREDOSIS2_CONS = "FIEBREDOSIS2";
    protected static final String CANSANCIODOSIS2_CONS = "CANSANCIODOSIS2";
    protected static final String DOLORCABEZADOSIS2_CONS = "DOLORCABEZADOSIS2";
    protected static final String DIARREADOSIS2_CONS = "DIARREADOSIS2";
    protected static final String VOMITODOSIS2_CONS = "VOMITODOSIS2";
    protected static final String DOLORMUSCULARDOSIS2_CONS = "DOLORMUSCULARDOSIS2";
    protected static final String ESCALOFRIOSDOSIS2_CONS = "ESCALOFRIOSDOSIS2";
    protected static final String REACCIONALERGICADOSIS2_CONS = "REACCIONALERGICADOSIS2";
    protected static final String INFECCIONSITIODOSIS2_CONS = "INFECCIONSITIODOSIS2";
    protected static final String NAUSEASDOSIS2_CONS = "NAUSEASDOSIS2";
    protected static final String BULTOSDOSIS2_CONS = "BULTOSDOSIS2";
    protected static final String OTROSDOSIS2_CONS = "OTROSDOSIS2";

    protected static final String PRESENTOSINTOMASDOSIS3_CONS = "PRESENTOSINTOMASDOSIS3";
    protected static final String DOLORSITIODOSIS3_CONS = "DOLORSITIODOSIS3";
    protected static final String FIEBREDOSIS3_CONS = "FIEBREDOSIS3";
    protected static final String CANSANCIODOSIS3_CONS = "CANSANCIODOSIS3";
    protected static final String DOLORCABEZADOSIS3_CONS = "DOLORCABEZADOSIS3";
    protected static final String DIARREADOSIS3_CONS = "DIARREADOSIS3";
    protected static final String VOMITODOSIS3_CONS = "VOMITODOSIS3";
    protected static final String DOLORMUSCULARDOSIS3_CONS = "DOLORMUSCULARDOSIS3";
    protected static final String ESCALOFRIOSDOSIS3_CONS = "ESCALOFRIOSDOSIS3";
    protected static final String REACCIONALERGICADOSIS3_CONS = "REACCIONALERGICADOSIS3";
    protected static final String INFECCIONSITIODOSIS3_CONS = "INFECCIONSITIODOSIS3";
    protected static final String NAUSEASDOSIS3_CONS = "NAUSEASDOSIS3";
    protected static final String BULTOSDOSIS3_CONS = "BULTOSDOSIS3";
    protected static final String OTROSDOSIS3_CONS = "OTROSDOSIS3";

    protected static final String COVID19POSTERIORVACUNA_CONS = "COVID19POSTERIORVACUNA";
    protected static final String SABEFECHAENFPOSTVAC_CONS= "SABEFECHAENFPOSTVAC";
    protected static final String FECHAEVENTOENFERMOPOSTVAC_CONS = "FECHAEVENTOENFERMOPOSTVAC";
    //protected static final String MESFECHAENFPOSTVAC_CONS= "MESFECHAENFPOSTVAC";

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
    private List<MessageResource> mCatalogoFuma;
    private List<String> mMedicamentos = new ArrayList<String>();
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

    //Objeto que se va a hacer
    private CuestionarioCovid19 mCuestionario = new CuestionarioCovid19();

    //Widgets en el View
    private LinearLayout layoutEvento1;
    private LinearLayout layoutEvento2;
    private LinearLayout layoutEvento3;
    private LinearLayout layoutE1FIS;
    private LinearLayout layoutE1FechaAdmision;
    private LinearLayout layoutE1FechaAlta;
    private LinearLayout layoutE2FIS;
    private LinearLayout layoutE2FechaAdmision;
    private LinearLayout layoutE2FechaAlta;
    private LinearLayout layoutE3FIS;
    private LinearLayout layoutE3FechaAdmision;
    private LinearLayout layoutE3FechaAlta;
    private LinearLayout layoutFechaDosis1;
    private LinearLayout layoutFechaDosis2;
    private LinearLayout layoutFechaDosis3;
    //private LinearLayout layoutFechaEnfPostVac;

    private TextView mTitleView;
    private EditText mNameView;
    private Button mSaveView;
    //evento1
    private TextView textE1SabeInicioSintoma;
    private TextView textE1FIS;
    private TextView textE1MesInicioSintoma;
    private TextView textE1AnioInicioSintoma;
    private TextView textE1ConoceLugarExposicion;
    private TextView textE1LugarExposicion;
    private TextView textE1BuscoAyuda;
    private TextView textE1DondeBuscoAyuda;
    private TextView textE1NombreCentro;
    private TextView textE1NombreHospital;
    private TextView textE1RecibioSeguimiento;
    private TextView textE1TipoSeguimiento;
    private TextView textE1TmpDespuesBuscoAyuda;
    private TextView textE1UnaNocheHospital;
    private TextView textE1QueHospital;
    private TextView textE1SabeFechaAlta;
    private TextView textE1SabeFechaAdmision;
    private TextView textE1SabeCuantasNoches;
    private TextView textE1CuantasNochesHosp;
    private TextView textE1FechaAdmisionHosp;
    private TextView textE1FechaAltaHosp;
    private TextView textE1UtilizoOxigeno;
    private TextView textE1EstuvoUCI;
    private TextView textE1FueIntubado;
    private TextView textE1RecuperadoCovid19;
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
    private TextView textE1TomoMedicamento;
    private TextView textE1QueMedicamento;
    private TextView textE1OtroMedicamento;
    private TextView textE1OxigenoDomicilio;
    private TextView textE1TiempoOxigenoDom;
    //evento2
    private TextView textE2SabeInicioSintoma;
    private TextView textE2FIS;
    private TextView textE2MesInicioSintoma;
    private TextView textE2AnioInicioSintoma;
    private TextView textE2ConoceLugarExposicion;
    private TextView textE2LugarExposicion;
    private TextView textE2BuscoAyuda;
    private TextView textE2DondeBuscoAyuda;
    private TextView textE2NombreCentro;
    private TextView textE2NombreHospital;
    private TextView textE2RecibioSeguimiento;
    private TextView textE2TipoSeguimiento;
    private TextView textE2TmpDespuesBuscoAyuda;
    private TextView textE2UnaNocheHospital;
    private TextView textE2QueHospital;
    private TextView textE2SabeFechaAlta;
    private TextView textE2SabeFechaAdmision;
    private TextView textE2SabeCuantasNoches;
    private TextView textE2CuantasNochesHosp;
    private TextView textE2FechaAdmisionHosp;
    private TextView textE2FechaAltaHosp;
    private TextView textE2UtilizoOxigeno;
    private TextView textE2EstuvoUCI;
    private TextView textE2FueIntubado;
    private TextView textE2RecuperadoCovid19;
    private TextView textE2TieneFebricula;
    private TextView textE2TieneCansancio;
    private TextView textE2TieneDolorCabeza;
    private TextView textE2TienePerdidaOlfato;
    private TextView textE2TienePerdidaGusto;
    private TextView textE2TieneTos;
    private TextView textE2TieneDificultadRespirar;
    private TextView textE2TieneDolorPecho;
    private TextView textE2TienePalpitaciones;
    private TextView textE2TieneDolorArticulaciones;
    private TextView textE2TieneParalisis;
    private TextView textE2TieneMareos;
    private TextView textE2TienePensamientoNublado;
    private TextView textE2TieneProblemasDormir;
    private TextView textE2TieneDepresion;
    private TextView textE2TieneOtrosSintomas;
    private TextView textE2TieneCualesSintomas;
    private TextView textE2TiempoRecuperacion;
    private TextView textE2SabeTiempoRecuperacion;
    private TextView textE2SeveridadEnfermedad;
    private TextView textE2TomoMedicamento;
    private TextView textE2QueMedicamento;
    private TextView textE2OtroMedicamento;
    private TextView textE2OxigenoDomicilio;
    private TextView textE2TiempoOxigenoDom;
    //evento3
    private TextView textE3SabeInicioSintoma;
    private TextView textE3FIS;
    private TextView textE3MesInicioSintoma;
    private TextView textE3AnioInicioSintoma;
    private TextView textE3ConoceLugarExposicion;
    private TextView textE3LugarExposicion;
    private TextView textE3BuscoAyuda;
    private TextView textE3DondeBuscoAyuda;
    private TextView textE3NombreCentro;
    private TextView textE3NombreHospital;
    private TextView textE3RecibioSeguimiento;
    private TextView textE3TipoSeguimiento;
    private TextView textE3TmpDespuesBuscoAyuda;
    private TextView textE3UnaNocheHospital;
    private TextView textE3QueHospital;
    private TextView textE3SabeFechaAlta;
    private TextView textE3SabeFechaAdmision;
    private TextView textE3SabeCuantasNoches;
    private TextView textE3CuantasNochesHosp;
    private TextView textE3FechaAdmisionHosp;
    private TextView textE3FechaAltaHosp;
    private TextView textE3UtilizoOxigeno;
    private TextView textE3EstuvoUCI;
    private TextView textE3FueIntubado;
    private TextView textE3RecuperadoCovid19;
    private TextView textE3TieneFebricula;
    private TextView textE3TieneCansancio;
    private TextView textE3TieneDolorCabeza;
    private TextView textE3TienePerdidaOlfato;
    private TextView textE3TienePerdidaGusto;
    private TextView textE3TieneTos;
    private TextView textE3TieneDificultadRespirar;
    private TextView textE3TieneDolorPecho;
    private TextView textE3TienePalpitaciones;
    private TextView textE3TieneDolorArticulaciones;
    private TextView textE3TieneParalisis;
    private TextView textE3TieneMareos;
    private TextView textE3TienePensamientoNublado;
    private TextView textE3TieneProblemasDormir;
    private TextView textE3TieneDepresion;
    private TextView textE3TieneOtrosSintomas;
    private TextView textE3TieneCualesSintomas;
    private TextView textE3TiempoRecuperacion;
    private TextView textE3SabeTiempoRecuperacion;
    private TextView textE3SeveridadEnfermedad;
    private TextView textE3TomoMedicamento;
    private TextView textE3QueMedicamento;
    private TextView textE3OtroMedicamento;
    private TextView textE3OxigenoDomicilio;
    private TextView textE3TiempoOxigenoDom;
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
    //evento2
    private TextView textE2FumadoPrevioEnfermedad;
    private TextView textE2FumaActualmente;
    private TextView textE2QueSintomasPresenta;
    private TextView textE2Embarazada;
    private TextView textE2RecuerdaSemanasEmb;
    private TextView textE2SemanasEmbarazo;
    private TextView textE2FinalEmbarazo;
    private TextView textE2OtroFinalEmbarazo;
    private TextView textE2DabaPecho;
    //evento3
    private TextView textE3FumadoPrevioEnfermedad;
    private TextView textE3FumaActualmente;
    private TextView textE3QueSintomasPresenta;
    private TextView textE3Embarazada;
    private TextView textE3RecuerdaSemanasEmb;
    private TextView textE3SemanasEmbarazo;
    private TextView textE3FinalEmbarazo;
    private TextView textE3OtroFinalEmbarazo;
    private TextView textE3DabaPecho;
    //solo una vez
    private TextView textTrabajadorSalud;
    //Muestreo rojo Noviemnbre 2021
    private TextView textEnfermoCovid19;
    private TextView textCuantasVecesEnfermo;
    private TextView textSabeEvento1;
    private TextView textEvento1;
    private TextView textAnioEvento1;
    private TextView textMesEvento1;
    private TextView textSabeEvento2;
    private TextView textEvento2;
    private TextView textAnioEvento2;
    private TextView textMesEvento2;
    private TextView textSabeEvento3;
    private TextView textEvento3;
    private TextView textAnioEvento3;
    private TextView textMesEvento3;
    private TextView textDesdeSintomasE1;

    private TextView textE1Febricula;
    private TextView textE1Fiebre;
    private TextView textE1Escalofrio;
    private TextView textE1TemblorEscalofrio;
    private TextView textE1DolorMuscular;
    private TextView textE1DolorArticular;
    private TextView textE1SecresionNasal;
    private TextView textE1DolorGarganta;
    private TextView textE1Tos;
    private TextView textE1DificultadResp;
    private TextView textE1DolorPecho;
    private TextView textE1NauseasVomito;
    private TextView textE1DolorCabeza;
    private TextView textE1DolorAbdominal;
    private TextView textE1Diarrea;
    private TextView textE1DificultadDormir;
    private TextView textE1Debilidad;
    private TextView textE1PerdidaOlfatoGusto;
    private TextView textE1Mareo;
    private TextView textE1Sarpullido;
    private TextView textE1Desmayo;
    private TextView textE1QuedoCama;
    private TextView textDesdeSintomasE2;
    private TextView textE2Febricula;
    private TextView textE2Fiebre;
    private TextView textE2Escalofrio;
    private TextView textE2TemblorEscalofrio;
    private TextView textE2DolorMuscular;
    private TextView textE2DolorArticular;
    private TextView textE2SecresionNasal;
    private TextView textE2DolorGarganta;
    private TextView textE2Tos;
    private TextView textE2DificultadResp;
    private TextView textE2DolorPecho;
    private TextView textE2NauseasVomito;
    private TextView textE2DolorCabeza;
    private TextView textE2DolorAbdominal;
    private TextView textE2Diarrea;
    private TextView textE2DificultadDormir;
    private TextView textE2Debilidad;
    private TextView textE2PerdidaOlfatoGusto;
    private TextView textE2Mareo;
    private TextView textE2Sarpullido;
    private TextView textE2Desmayo;
    private TextView textE2QuedoCama;
    private TextView textDesdeSintomasE3;
    private TextView textE3Febricula;
    private TextView textE3Fiebre;
    private TextView textE3Escalofrio;
    private TextView textE3TemblorEscalofrio;
    private TextView textE3DolorMuscular;
    private TextView textE3DolorArticular;
    private TextView textE3SecresionNasal;
    private TextView textE3DolorGarganta;
    private TextView textE3Tos;
    private TextView textE3DificultadResp;
    private TextView textE3DolorPecho;
    private TextView textE3NauseasVomito;
    private TextView textE3DolorCabeza;
    private TextView textE3DolorAbdominal;
    private TextView textE3Diarrea;
    private TextView textE3DificultadDormir;
    private TextView textE3Debilidad;
    private TextView textE3PerdidaOlfatoGusto;
    private TextView textE3Mareo;
    private TextView textE3Sarpullido;
    private TextView textE3Desmayo;
    private TextView textE3QuedoCama;
    private TextView textVacunadoCovid19;
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
    //dosis1
    private TextView textPresentoSintomasDosis1;
    private TextView textSintomasDosis1;
    private TextView textDolorSitioDosis1;
    private TextView textFiebreDosis1;
    private TextView textCansancioDosis1;
    private TextView textDolorCabezaDosis1;
    private TextView textDiarreaDosis1;
    private TextView textVomitoDosis1;
    private TextView textDolorMuscularDosis1;
    private TextView textEscalofriosDosis1;
    private TextView textReaccionAlergicaDosis1;
    private TextView textInfeccionSitioDosis1;
    private TextView textNauseasDosis1;
    private TextView textBultosDosis1;
    private TextView textOtrosDosis1;
    private TextView textDesOtrosDosis1;
    //Dosis2
    private TextView textPresentoSintomasDosis2;
    private TextView textSintomasDosis2;
    private TextView textDolorSitioDosis2;
    private TextView textFiebreDosis2;
    private TextView textCansancioDosis2;
    private TextView textDolorCabezaDosis2;
    private TextView textDiarreaDosis2;
    private TextView textVomitoDosis2;
    private TextView textDolorMuscularDosis2;
    private TextView textEscalofriosDosis2;
    private TextView textReaccionAlergicaDosis2;
    private TextView textInfeccionSitioDosis2;
    private TextView textNauseasDosis2;
    private TextView textBultosDosis2;
    private TextView textOtrosDosis2;
    private TextView textDesOtrosDosis2;
    //Dosis3
    private TextView textPresentoSintomasDosis3;
    private TextView textSintomasDosis3;
    private TextView textDolorSitioDosis3;
    private TextView textFiebreDosis3;
    private TextView textCansancioDosis3;
    private TextView textDolorCabezaDosis3;
    private TextView textDiarreaDosis3;
    private TextView textVomitoDosis3;
    private TextView textDolorMuscularDosis3;
    private TextView textEscalofriosDosis3;
    private TextView textReaccionAlergicaDosis3;
    private TextView textInfeccionSitioDosis3;
    private TextView textNauseasDosis3;
    private TextView textBultosDosis3;
    private TextView textOtrosDosis3;
    private TextView textDesOtrosDosis3;

    private TextView textCovid19PosteriorVacuna;
    private TextView textFechaEventoEnfermoPostVac;
    //private TextView textSabeFechaEnfPostVac;
    //private TextView textFechaEnfPostVac;
    //private TextView textAnioEnfPostVac;
    //private TextView textMesEnfPostVac;

    //evento1
    private Spinner spinE1SabeInicioSintoma;
    private EditText inputE1FIS;
    private Spinner spinE1MesInicioSintoma;
    private EditText inputE1AnioInicioSintoma;
    private Spinner spinE1ConoceLugarExposicion;
    private EditText inputE1LugarExposicion;
    private Spinner spinE1BuscoAyuda;
    private Spinner spinE1DondeBuscoAyuda;
    private EditText inputE1NombreCentro;
    private EditText inputE1NombreHospital;
    private Spinner spinE1RecibioSeguimiento;
    private Spinner spinE1TipoSeguimiento;
    private Spinner spinE1TmpDespuesBuscoAyuda;
    private Spinner spinE1UnaNocheHospital;
    private Spinner spinE1SabeFechaAlta;
    private Spinner spinE1SabeFechaAdmision;
    private Spinner spinE1SabeCuantasNoches;
    private EditText inputE1QueHospital;
    private EditText inputE1CuantasNochesHosp;
    private EditText inputE1FechaAdmisionHosp;
    private EditText inputE1FechaAltaHosp;
    private Spinner spinE1UtilizoOxigeno;
    private Spinner spinE1EstuvoUCI;
    private Spinner spinE1FueIntubado;
    private Spinner spinE1RecuperadoCovid19;
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
    private Spinner spinE1TomoMedicamento;
    private MultiSpinner spinE1QueMedicamento;
    private EditText inputE1OtroMedicamento;
    private Spinner spinE1OxigenoDomicilio;
    private Spinner spinE1TiempoOxigenoDom;
    //evento2
    private Spinner spinE2SabeInicioSintoma;
    private EditText inputE2FIS;
    private Spinner spinE2MesInicioSintoma;
    private EditText inputE2AnioInicioSintoma;
    private Spinner spinE2ConoceLugarExposicion;
    private EditText inputE2LugarExposicion;
    private Spinner spinE2BuscoAyuda;
    private Spinner spinE2DondeBuscoAyuda;
    private EditText inputE2NombreCentro;
    private EditText inputE2NombreHospital;
    private Spinner spinE2RecibioSeguimiento;
    private Spinner spinE2TipoSeguimiento;
    private Spinner spinE2TmpDespuesBuscoAyuda;
    private Spinner spinE2UnaNocheHospital;
    private Spinner spinE2SabeFechaAlta;
    private Spinner spinE2SabeFechaAdmision;
    private Spinner spinE2SabeCuantasNoches;
    private EditText inputE2QueHospital;
    private EditText inputE2CuantasNochesHosp;
    private EditText inputE2FechaAdmisionHosp;
    private EditText inputE2FechaAltaHosp;
    private Spinner spinE2UtilizoOxigeno;
    private Spinner spinE2EstuvoUCI;
    private Spinner spinE2FueIntubado;
    private Spinner spinE2RecuperadoCovid19;
    private Spinner spinE2TieneFebricula;
    private Spinner spinE2TieneCansancio;
    private Spinner spinE2TieneDolorCabeza;
    private Spinner spinE2TienePerdidaOlfato;
    private Spinner spinE2TienePerdidaGusto;
    private Spinner spinE2TieneTos;
    private Spinner spinE2TieneDificultadRespirar;
    private Spinner spinE2TieneDolorPecho;
    private Spinner spinE2TienePalpitaciones;
    private Spinner spinE2TieneDolorArticulaciones;
    private Spinner spinE2TieneParalisis;
    private Spinner spinE2TieneMareos;
    private Spinner spinE2TienePensamientoNublado;
    private Spinner spinE2TieneProblemasDormir;
    private Spinner spinE2TieneDepresion;
    private Spinner spinE2TieneOtrosSintomas;
    private EditText inputE2TieneCualesSintomas;
    private Spinner spinE2SabeTiempoRecuperacion;
    private EditText inputE2TiempoRecuperacion;
    private Spinner spinE2TiempoRecuperacionEn;
    private Spinner spinE2SeveridadEnfermedad;
    private Spinner spinE2TomoMedicamento;
    private MultiSpinner spinE2QueMedicamento;
    private EditText inputE2OtroMedicamento;
    private Spinner spinE2OxigenoDomicilio;
    private Spinner spinE2TiempoOxigenoDom;
    //evento3
    private Spinner spinE3SabeInicioSintoma;
    private EditText inputE3FIS;
    private Spinner spinE3MesInicioSintoma;
    private EditText inputE3AnioInicioSintoma;
    private Spinner spinE3ConoceLugarExposicion;
    private EditText inputE3LugarExposicion;
    private Spinner spinE3BuscoAyuda;
    private Spinner spinE3DondeBuscoAyuda;
    private EditText inputE3NombreCentro;
    private EditText inputE3NombreHospital;
    private Spinner spinE3RecibioSeguimiento;
    private Spinner spinE3TipoSeguimiento;
    private Spinner spinE3TmpDespuesBuscoAyuda;
    private Spinner spinE3UnaNocheHospital;
    private Spinner spinE3SabeFechaAlta;
    private Spinner spinE3SabeFechaAdmision;
    private Spinner spinE3SabeCuantasNoches;
    private EditText inputE3QueHospital;
    private EditText inputE3CuantasNochesHosp;
    private EditText inputE3FechaAdmisionHosp;
    private EditText inputE3FechaAltaHosp;
    private Spinner spinE3UtilizoOxigeno;
    private Spinner spinE3EstuvoUCI;
    private Spinner spinE3FueIntubado;
    private Spinner spinE3RecuperadoCovid19;
    private Spinner spinE3TieneFebricula;
    private Spinner spinE3TieneCansancio;
    private Spinner spinE3TieneDolorCabeza;
    private Spinner spinE3TienePerdidaOlfato;
    private Spinner spinE3TienePerdidaGusto;
    private Spinner spinE3TieneTos;
    private Spinner spinE3TieneDificultadRespirar;
    private Spinner spinE3TieneDolorPecho;
    private Spinner spinE3TienePalpitaciones;
    private Spinner spinE3TieneDolorArticulaciones;
    private Spinner spinE3TieneParalisis;
    private Spinner spinE3TieneMareos;
    private Spinner spinE3TienePensamientoNublado;
    private Spinner spinE3TieneProblemasDormir;
    private Spinner spinE3TieneDepresion;
    private Spinner spinE3TieneOtrosSintomas;
    private EditText inputE3TieneCualesSintomas;
    private Spinner spinE3SabeTiempoRecuperacion;
    private EditText inputE3TiempoRecuperacion;
    private Spinner spinE3TiempoRecuperacionEn;
    private Spinner spinE3SeveridadEnfermedad;
    private Spinner spinE3TomoMedicamento;
    private MultiSpinner spinE3QueMedicamento;
    private EditText inputE3OtroMedicamento;
    private Spinner spinE3OxigenoDomicilio;
    private Spinner spinE3TiempoOxigenoDom;
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
    //evento2
    private Spinner spinE2FumadoPrevioEnfermedad;
    private Spinner spinE2FumaActualmente;
    private Spinner spinE2Embarazada;
    private Spinner spinE2RecuerdaSemanasEmb;
    private EditText inputE2SemanasEmbarazo;
    private Spinner spinE2FinalEmbarazo;
    private EditText inputE2OtroFinalEmbarazo;
    private Spinner spinE2DabaPecho;
    //evento3
    private Spinner spinE3FumadoPrevioEnfermedad;
    private Spinner spinE3FumaActualmente;
    private Spinner spinE3Embarazada;
    private Spinner spinE3RecuerdaSemanasEmb;
    private EditText inputE3SemanasEmbarazo;
    private Spinner spinE3FinalEmbarazo;
    private EditText inputE3OtroFinalEmbarazo;
    private Spinner spinE3DabaPecho;

    private Spinner spinTrabajadorSalud;
    //Muestreo rojo Noviemnbre 2021
    private Spinner spinEnfermoCovid19;
    private Spinner spinCuantasVecesEnfermo;
    private Spinner spinSabeEvento1;
    private EditText inputEvento1;
    private EditText inputAnioEvento1;
    private Spinner spinMesEvento1;
    private Spinner spinSabeEvento2;
    private EditText inputEvento2;
    private EditText inputAnioEvento2;
    private Spinner spinMesEvento2;
    private Spinner spinSabeEvento3;
    private EditText inputEvento3;
    private EditText inputAnioEvento3;
    private Spinner spinMesEvento3;
    private Spinner spinE1Febricula;
    private Spinner spinE1Fiebre;
    private Spinner spinE1Escalofrio;
    private Spinner spinE1TemblorEscalofrio;
    private Spinner spinE1DolorMuscular;
    private Spinner spinE1DolorArticular;
    private Spinner spinE1SecresionNasal;
    private Spinner spinE1DolorGarganta;
    private Spinner spinE1Tos;
    private Spinner spinE1DificultadResp;
    private Spinner spinE1DolorPecho;
    private Spinner spinE1NauseasVomito;
    private Spinner spinE1DolorCabeza;
    private Spinner spinE1DolorAbdominal;
    private Spinner spinE1Diarrea;
    private Spinner spinE1DificultadDormir;
    private Spinner spinE1Debilidad;
    private Spinner spinE1PerdidaOlfatoGusto;
    private Spinner spinE1Mareo;
    private Spinner spinE1Sarpullido;
    private Spinner spinE1Desmayo;
    private Spinner spinE1QuedoCama;
    private Spinner spinE2Febricula;
    private Spinner spinE2Fiebre;
    private Spinner spinE2Escalofrio;
    private Spinner spinE2TemblorEscalofrio;
    private Spinner spinE2DolorMuscular;
    private Spinner spinE2DolorArticular;
    private Spinner spinE2SecresionNasal;
    private Spinner spinE2DolorGarganta;
    private Spinner spinE2Tos;
    private Spinner spinE2DificultadResp;
    private Spinner spinE2DolorPecho;
    private Spinner spinE2NauseasVomito;
    private Spinner spinE2DolorCabeza;
    private Spinner spinE2DolorAbdominal;
    private Spinner spinE2Diarrea;
    private Spinner spinE2DificultadDormir;
    private Spinner spinE2Debilidad;
    private Spinner spinE2PerdidaOlfatoGusto;
    private Spinner spinE2Mareo;
    private Spinner spinE2Sarpullido;
    private Spinner spinE2Desmayo;
    private Spinner spinE2QuedoCama;
    private Spinner spinE3Febricula;
    private Spinner spinE3Fiebre;
    private Spinner spinE3Escalofrio;
    private Spinner spinE3TemblorEscalofrio;
    private Spinner spinE3DolorMuscular;
    private Spinner spinE3DolorArticular;
    private Spinner spinE3SecresionNasal;
    private Spinner spinE3DolorGarganta;
    private Spinner spinE3Tos;
    private Spinner spinE3DificultadResp;
    private Spinner spinE3DolorPecho;
    private Spinner spinE3NauseasVomito;
    private Spinner spinE3DolorCabeza;
    private Spinner spinE3DolorAbdominal;
    private Spinner spinE3Diarrea;
    private Spinner spinE3DificultadDormir;
    private Spinner spinE3Debilidad;
    private Spinner spinE3PerdidaOlfatoGusto;
    private Spinner spinE3Mareo;
    private Spinner spinE3Sarpullido;
    private Spinner spinE3Desmayo;
    private Spinner spinE3QuedoCama;
    private Spinner spinVacunadoCovid19;
    private Spinner spinMuestraTarjetaVac;
    private Spinner spinSabeNombreVacuna;
    private EditText inputNombreVacuna;
    private EditText inputAnioVacuna;
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
    //dosis1
    private Spinner spinPresentoSintomasDosis1;
    private Spinner spinDolorSitioDosis1;
    private Spinner spinFiebreDosis1;
    private Spinner spinCansancioDosis1;
    private Spinner spinDolorCabezaDosis1;
    private Spinner spinDiarreaDosis1;
    private Spinner spinVomitoDosis1;
    private Spinner spinDolorMuscularDosis1;
    private Spinner spinEscalofriosDosis1;
    private Spinner spinReaccionAlergicaDosis1;
    private Spinner spinInfeccionSitioDosis1;
    private Spinner spinNauseasDosis1;
    private Spinner spinBultosDosis1;
    private Spinner spinOtrosDosis1;
    //dosis2
    private Spinner spinPresentoSintomasDosis2;
    private Spinner spinDolorSitioDosis2;
    private Spinner spinFiebreDosis2;
    private Spinner spinCansancioDosis2;
    private Spinner spinDolorCabezaDosis2;
    private Spinner spinDiarreaDosis2;
    private Spinner spinVomitoDosis2;
    private Spinner spinDolorMuscularDosis2;
    private Spinner spinEscalofriosDosis2;
    private Spinner spinReaccionAlergicaDosis2;
    private Spinner spinInfeccionSitioDosis2;
    private Spinner spinNauseasDosis2;
    private Spinner spinBultosDosis2;
    private Spinner spinOtrosDosis2;
    //dosis3
    private Spinner spinPresentoSintomasDosis3;
    private Spinner spinDolorSitioDosis3;
    private Spinner spinFiebreDosis3;
    private Spinner spinCansancioDosis3;
    private Spinner spinDolorCabezaDosis3;
    private Spinner spinDiarreaDosis3;
    private Spinner spinVomitoDosis3;
    private Spinner spinDolorMuscularDosis3;
    private Spinner spinEscalofriosDosis3;
    private Spinner spinReaccionAlergicaDosis3;
    private Spinner spinInfeccionSitioDosis3;
    private Spinner spinNauseasDosis3;
    private Spinner spinBultosDosis3;
    private Spinner spinOtrosDosis3;

    private Spinner spinCovid19PosteriorVacuna;
    private Spinner spinFechaEventoEnfermoPostVac;
    //private Spinner spinSabeFechaEnfPostVac;
    //private Spinner spinMesEnfPostVac;
    //private TextView inputFechaEnfPostVac;
    //private TextView inputAnioEnfPostVac;

    private ImageButton imbE1FIS;
    private ImageButton imbE1FechaAdmisionHosp;
    private ImageButton imbE1FechaAltaHosp;
    private ImageButton imbE2FIS;
    private ImageButton imbE2FechaAdmisionHosp;
    private ImageButton imbE2FechaAltaHosp;
    private ImageButton imbE3FIS;
    private ImageButton imbE3FechaAdmisionHosp;
    private ImageButton imbE3FechaAltaHosp;
    private ImageButton imbEvento1;
    private ImageButton imbEvento2;
    private ImageButton imbEvento3;
    private ImageButton imbFechaDosis1;
    private ImageButton imbFechaDosis2;
    private ImageButton imbFechaDosis3;
    //private ImageButton imbFechaEnfPostVac;

    private boolean mostrarConfirmacion = true;
    //Variables donde se captura la entrada de datos
    private int enfermoCovid19Pos = 0;
    private int cuantasVecesEnfermoPos = 0;

    private int sabeEvento1Pos = 0;
    private int evento1Pos = 0;
    private int anioEvento1Pos = 0;
    private int mesEvento1Pos = 0;
    private int sabeEvento2Pos = 0;
    private int evento2Pos = 0;
    private int anioEvento2Pos = 0;
    private int mesEvento2Pos = 0;
    private int sabeEvento3Pos = 0;
    private int evento3Pos = 0;
    private int anioEvento3Pos = 0;
    private int mesEvento3Pos = 0;
    private int e1FebriculaPos = 0;
    private int e1FiebrePos = 0;
    private int e1EscalofrioPos = 0;
    private int e1TemblorEscalofrioPos = 0;
    private int e1DolorMuscularPos = 0;
    private int e1DolorArticularPos = 0;
    private int e1SecresionNasalPos = 0;
    private int e1DolorGargantaPos = 0;
    private int e1TosPos = 0;
    private int e1DificultadRespPos = 0;
    private int e1DolorPechoPos = 0;
    private int e1NauseasVomitoPos = 0;
    private int e1DolorCabezaPos = 0;
    private int e1DolorAbdominalPos = 0;
    private int e1DiarreaPos = 0;
    private int e1DificultadDormirPos = 0;
    private int e1DebilidadPos = 0;
    private int e1PerdidaOlfatoGustoPos = 0;
    private int e1MareoPos = 0;
    private int e1SarpullidoPos = 0;
    private int e1DesmayoPos = 0;
    private int e1QuedoCamaPos = 0;
    private int e2FebriculaPos = 0;
    private int e2FiebrePos = 0;
    private int e2EscalofrioPos = 0;
    private int e2TemblorEscalofrioPos = 0;
    private int e2DolorMuscularPos = 0;
    private int e2DolorArticularPos = 0;
    private int e2SecresionNasalPos = 0;
    private int e2DolorGargantaPos = 0;
    private int e2TosPos = 0;
    private int e2DificultadRespPos = 0;
    private int e2DolorPechoPos = 0;
    private int e2NauseasVomitoPos = 0;
    private int e2DolorCabezaPos = 0;
    private int e2DolorAbdominalPos = 0;
    private int e2DiarreaPos = 0;
    private int e2DificultadDormirPos = 0;
    private int e2DebilidadPos = 0;
    private int e2PerdidaOlfatoGustoPos = 0;
    private int e2MareoPos = 0;
    private int e2SarpullidoPos = 0;
    private int e2DesmayoPos = 0;
    private int e2QuedoCamaPos = 0;
    private int e3FebriculaPos = 0;
    private int e3FiebrePos = 0;
    private int e3EscalofrioPos = 0;
    private int e3TemblorEscalofrioPos = 0;
    private int e3DolorMuscularPos = 0;
    private int e3DolorArticularPos = 0;
    private int e3SecresionNasalPos = 0;
    private int e3DolorGargantaPos = 0;
    private int e3TosPos = 0;
    private int e3DificultadRespPos = 0;
    private int e3DolorPechoPos = 0;
    private int e3NauseasVomitoPos = 0;
    private int e3DolorCabezaPos = 0;
    private int e3DolorAbdominalPos = 0;
    private int e3DiarreaPos = 0;
    private int e3DificultadDormirPos = 0;
    private int e3DebilidadPos = 0;
    private int e3PerdidaOlfatoGustoPos = 0;
    private int e3MareoPos = 0;
    private int e3SarpullidoPos = 0;
    private int e3DesmayoPos = 0;
    private int e3QuedoCamaPos = 0;

    private int e1SabeFISPos = 0;
    private int e1MesInicioSintomaPos = 0;
    private int e1ConoceLugarExposicionPos = 0;
    private int e1BuscoAyudaPos = 0;
    private int e1DondeBuscoAyudaPos = 0;
    private int e1RecibioSeguimientoPos = 0;
    private int e1TipoSeguimientoPos = 0;
    private int e1TmpDespuesBuscoAyudaPos = 0;
    private int e1UnaNocheHospitalPos = 0;
    private int e1SabeCuantasNochesPos = 0;
    private int e1sabeFechaAdmisionPos = 0;
    private int e1SabeFechaAltaPos = 0;
    private int e1UtilizoOxigenoPos = 0;
    private int e1EstuvoUCIPos = 0;
    private int e1FueIntubadoPos = 0;
    private int e1RecuperadoCovid19Pos = 0;
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
    private int e1TomoMedicamentoPos = 0;
    private int e1QueMedicamentoPos = 0;
    private int e1OxigenoDomicilioPos = 0;
    private int e1TiempoOxigenoDomPos = 0;
    private int e2SabeFISPos = 0;
    private int e2MesInicioSintomaPos = 0;
    private int e2ConoceLugarExposicionPos = 0;
    private int e2BuscoAyudaPos = 0;
    private int e2DondeBuscoAyudaPos = 0;
    private int e2RecibioSeguimientoPos = 0;
    private int e2TipoSeguimientoPos = 0;
    private int e2TmpDespuesBuscoAyudaPos = 0;
    private int e2UnaNocheHospitalPos = 0;
    private int e2SabeCuantasNochesPos = 0;
    private int e2sabeFechaAdmisionPos = 0;
    private int e2SabeFechaAltaPos = 0;
    private int e2UtilizoOxigenoPos = 0;
    private int e2EstuvoUCIPos = 0;
    private int e2FueIntubadoPos = 0;
    private int e2RecuperadoCovid19Pos = 0;
    private int e2TieneFebriculaPos = 0;
    private int e2TieneCansancioPos = 0;
    private int e2TieneDolorCabezaPos = 0;
    private int e2TienePerdidaOlfatoPos = 0;
    private int e2TienePerdidaGustoPos = 0;
    private int e2TieneTosPos = 0;
    private int e2TieneDificultadRespirarPos = 0;
    private int e2TieneDolorPechoPos = 0;
    private int e2TienePalpitacionesPos = 0;
    private int e2TieneDolorArticulacionesPos = 0;
    private int e2TieneParalisisPos = 0;
    private int e2TieneMareosPos = 0;
    private int e2TienePensamientoNubladoPos = 0;
    private int e2TieneProblemasDormirPos = 0;
    private int e2TieneDepresionPos = 0;
    private int e2TieneOtrosSintomasPos = 0;
    private int e2SabeTiempoRecuperacionPos = 0;
    private int e2TiempoRecuperacionEnPos = 0;
    private int e2SeveridadEnfermedadPos = 0;
    private int e2TomoMedicamentoPos = 0;
    private int e2QueMedicamentoPos = 0;
    private int e2OxigenoDomicilioPos = 0;
    private int e2TiempoOxigenoDomPos = 0;
    private int e3SabeFISPos = 0;
    private int e3MesInicioSintomaPos = 0;
    private int e3ConoceLugarExposicionPos = 0;
    private int e3BuscoAyudaPos = 0;
    private int e3DondeBuscoAyudaPos = 0;
    private int e3RecibioSeguimientoPos = 0;
    private int e3TipoSeguimientoPos = 0;
    private int e3TmpDespuesBuscoAyudaPos = 0;
    private int e3UnaNocheHospitalPos = 0;
    private int e3SabeCuantasNochesPos = 0;
    private int e3sabeFechaAdmisionPos = 0;
    private int e3SabeFechaAltaPos = 0;
    private int e3UtilizoOxigenoPos = 0;
    private int e3EstuvoUCIPos = 0;
    private int e3FueIntubadoPos = 0;
    private int e3RecuperadoCovid19Pos = 0;
    private int e3TieneFebriculaPos = 0;
    private int e3TieneCansancioPos = 0;
    private int e3TieneDolorCabezaPos = 0;
    private int e3TienePerdidaOlfatoPos = 0;
    private int e3TienePerdidaGustoPos = 0;
    private int e3TieneTosPos = 0;
    private int e3TieneDificultadRespirarPos = 0;
    private int e3TieneDolorPechoPos = 0;
    private int e3TienePalpitacionesPos = 0;
    private int e3TieneDolorArticulacionesPos = 0;
    private int e3TieneParalisisPos = 0;
    private int e3TieneMareosPos = 0;
    private int e3TienePensamientoNubladoPos = 0;
    private int e3TieneProblemasDormirPos = 0;
    private int e3TieneDepresionPos = 0;
    private int e3TieneOtrosSintomasPos = 0;
    private int e3SabeTiempoRecuperacionPos = 0;
    private int e3TiempoRecuperacionEnPos = 0;
    private int e3SeveridadEnfermedadPos = 0;
    private int e3TomoMedicamentoPos = 0;
    private int e3QueMedicamentoPos = 0;
    private int e3OxigenoDomicilioPos = 0;
    private int e3TiempoOxigenoDomPos = 0;

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
    private int e2FumadoPrevioEnfermedadPos = 0;
    private int e2FumaActualmentePos = 0;
    private int e2EmbarazadaPos = 0;
    private int e2RecuerdaSemanasEmbPos = 0;
    private int e2FinalEmbarazoPos = 0;
    private int e2DabaPechoPos = 0;
    private int e3FumadoPrevioEnfermedadPos = 0;
    private int e3FumaActualmentePos = 0;
    private int e3EmbarazadaPos = 0;
    private int e3RecuerdaSemanasEmbPos = 0;
    private int e3FinalEmbarazoPos = 0;
    private int e3DabaPechoPos = 0;

    private int trabajadorSaludPos = 0;
    private int vacunadoCovid19Pos = 0;
    private int muestraTarjetaVacPos = 0;
    private int sabeNombreVacunaPos = 0;
    private int mesVacunaPos = 0;
    private int cuantasDosisPos = 0;
    private int nombreDosis1Pos = 0;
    private int nombreDosis2Pos = 0;
    private int nombreDosis3Pos = 0;

    private int presentoSintomasDosis1Pos = 0;
    private int dolorSitioDosis1Pos = 0;
    private int fiebreDosis1Pos = 0;
    private int cansancioDosis1Pos = 0;
    private int dolorCabezaDosis1Pos = 0;
    private int diarreaDosis1Pos = 0;
    private int vomitoDosis1Pos = 0;
    private int dolorMuscularDosis1Pos = 0;
    private int escalofriosDosis1Pos = 0;
    private int reaccionAlergicaDosis1Pos = 0;
    private int infeccionSitioDosis1Pos = 0;
    private int nauseasDosis1Pos = 0;
    private int bultosDosis1Pos = 0;
    private int otrosDosis1Pos = 0;
    private int presentoSintomasDosis2Pos = 0;
    private int dolorSitioDosis2Pos = 0;
    private int fiebreDosis2Pos = 0;
    private int cansancioDosis2Pos = 0;
    private int dolorCabezaDosis2Pos = 0;
    private int diarreaDosis2Pos = 0;
    private int vomitoDosis2Pos = 0;
    private int dolorMuscularDosis2Pos = 0;
    private int escalofriosDosis2Pos = 0;
    private int reaccionAlergicaDosis2Pos = 0;
    private int infeccionSitioDosis2Pos = 0;
    private int nauseasDosis2Pos = 0;
    private int bultosDosis2Pos = 0;
    private int otrosDosis2Pos = 0;
    private int presentoSintomasDosis3Pos = 0;
    private int dolorSitioDosis3Pos = 0;
    private int fiebreDosis3Pos = 0;
    private int cansancioDosis3Pos = 0;
    private int dolorCabezaDosis3Pos = 0;
    private int diarreaDosis3Pos = 0;
    private int vomitoDosis3Pos = 0;
    private int dolorMuscularDosis3Pos = 0;
    private int escalofriosDosis3Pos = 0;
    private int reaccionAlergicaDosis3Pos = 0;
    private int infeccionSitioDosis3Pos = 0;
    private int nauseasDosis3Pos = 0;
    private int bultosDosis3Pos = 0;
    private int otrosDosis3Pos = 0;
    private int covid19PosteriorVacunaPos = 0;
    private int sabeFechaEnfPostVacPos = 0;
    private int fechaEventoEnfermoPostVacPos = 0;
    private int mesEnfPostVacPos = 0;

    private String e1SabeFIS;
    private String e1Fis;
    private String e1MesInicioSintoma;
    private String e1AnioInicioSintoma;
    private String e1ConoceLugarExposicion;
    private String e1LugarExposicion;
    private String e1BuscoAyuda;
    private String e1DondeBuscoAyuda;
    private String e1NombreCentroSalud;
    private String e1NombreHospital;
    private String e1RecibioSeguimiento;
    private String e1TipoSeguimiento;
    private String e1TmpDespuesBuscoAyuda;
    private String e1UnaNocheHospital;
    private String e1QueHospital;
    private String e1SabeCuantasNoches;
    private Integer e1CuantasNochesHosp;
    private String e1SabeFechaAdmision;
    private String e1FechaAdmisionHosp;
    private String e1SabeFechaAlta;
    private String e1FechaAltaHosp;
    private String e1UtilizoOxigeno;
    private String e1EstuvoUCI;
    private String e1FueIntubado;
    private String e1RecuperadoCovid19;
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
    private String e1TomoMedicamento;
    private String e1QueMedicamento;
    private String e1OtroMedicamento;
    private String e1OxigenoDomicilio;
    private String e1TiempoOxigenoDom;

    private String e2SabeFIS;
    private String e2Fis;
    private String e2MesInicioSintoma;
    private String e2AnioInicioSintoma;
    private String e2ConoceLugarExposicion;
    private String e2LugarExposicion;
    private String e2BuscoAyuda;
    private String e2DondeBuscoAyuda;
    private String e2NombreCentroSalud;
    private String e2NombreHospital;
    private String e2RecibioSeguimiento;
    private String e2TipoSeguimiento;
    private String e2TmpDespuesBuscoAyuda;
    private String e2UnaNocheHospital;
    private String e2QueHospital;
    private String e2SabeCuantasNoches;
    private Integer e2CuantasNochesHosp;
    private String e2SabeFechaAdmision;
    private String e2FechaAdmisionHosp;
    private String e2SabeFechaAlta;
    private String e2FechaAltaHosp;
    private String e2UtilizoOxigeno;
    private String e2EstuvoUCI;
    private String e2FueIntubado;
    private String e2RecuperadoCovid19;
    private String e2TieneFebricula;
    private String e2TieneCansancio;
    private String e2TieneDolorCabeza;
    private String e2TienePerdidaOlfato;
    private String e2TienePerdidaGusto;
    private String e2TieneTos;
    private String e2TieneDificultadRespirar;
    private String e2TieneDolorPecho;
    private String e2TienePalpitaciones;
    private String e2TieneDolorArticulaciones;
    private String e2TieneParalisis;
    private String e2TieneMareos;
    private String e2TienePensamientoNublado;
    private String e2TieneProblemasDormir;
    private String e2TieneDepresion;
    private String e2TieneOtrosSintomas;
    private String e2TieneCualesSintomas;
    private String e2SabeTiempoRecuperacion;
    private String e2TiempoRecuperacion;
    private String e2TiempoRecuperacionEn;
    private String e2SeveridadEnfermedad;
    private String e2TomoMedicamento;
    private String e2QueMedicamento;
    private String e2OtroMedicamento;
    private String e2OxigenoDomicilio;
    private String e2TiempoOxigenoDom;

    private String e3SabeFIS;
    private String e3Fis;
    private String e3MesInicioSintoma;
    private String e3AnioInicioSintoma;
    private String e3ConoceLugarExposicion;
    private String e3LugarExposicion;
    private String e3BuscoAyuda;
    private String e3DondeBuscoAyuda;
    private String e3NombreCentroSalud;
    private String e3NombreHospital;
    private String e3RecibioSeguimiento;
    private String e3TipoSeguimiento;
    private String e3TmpDespuesBuscoAyuda;
    private String e3UnaNocheHospital;
    private String e3QueHospital;
    private String e3SabeCuantasNoches;
    private Integer e3CuantasNochesHosp;
    private String e3SabeFechaAdmision;
    private String e3FechaAdmisionHosp;
    private String e3SabeFechaAlta;
    private String e3FechaAltaHosp;
    private String e3UtilizoOxigeno;
    private String e3EstuvoUCI;
    private String e3FueIntubado;
    private String e3RecuperadoCovid19;
    private String e3TieneFebricula;
    private String e3TieneCansancio;
    private String e3TieneDolorCabeza;
    private String e3TienePerdidaOlfato;
    private String e3TienePerdidaGusto;
    private String e3TieneTos;
    private String e3TieneDificultadRespirar;
    private String e3TieneDolorPecho;
    private String e3TienePalpitaciones;
    private String e3TieneDolorArticulaciones;
    private String e3TieneParalisis;
    private String e3TieneMareos;
    private String e3TienePensamientoNublado;
    private String e3TieneProblemasDormir;
    private String e3TieneDepresion;
    private String e3TieneOtrosSintomas;
    private String e3TieneCualesSintomas;
    private String e3SabeTiempoRecuperacion;
    private String e3TiempoRecuperacion;
    private String e3TiempoRecuperacionEn;
    private String e3SeveridadEnfermedad;
    private String e3TomoMedicamento;
    private String e3QueMedicamento;
    private String e3OtroMedicamento;
    private String e3OxigenoDomicilio;
    private String e3TiempoOxigenoDom;

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

    private String e2FumadoPrevioEnfermedad;
    private String e2FumaActualmente;
    private String e2Embarazada;
    private String e2RecuerdaSemanasEmb;
    private Integer e2SemanasEmbarazo;
    private String e2FinalEmbarazo;
    private String e2OtroFinalEmbarazo;
    private String e2DabaPecho;

    private String e3FumadoPrevioEnfermedad;
    private String e3FumaActualmente;
    private String e3Embarazada;
    private String e3RecuerdaSemanasEmb;
    private Integer e3SemanasEmbarazo;
    private String e3FinalEmbarazo;
    private String e3OtroFinalEmbarazo;
    private String e3DabaPecho;

    private String trabajadorSalud;
    private String periodoSintomas;
    private String enfermoCovid19;
    private String cuantasVecesEnfermo;
    private String sabeEvento1;
    private String fechaEvento1;
    private String anioEvento1;
    private String mesEvento1;
    private String sabeEvento2;
    private String fechaEvento2;
    private String anioEvento2;
    private String mesEvento2;
    private String sabeEvento3;
    private String fechaEvento3;
    private String anioEvento3;
    private String mesEvento3;
    private String e1Febricula;
    private String e1Fiebre;
    private String e1Escalofrio;
    private String e1TemblorEscalofrio;
    private String e1DolorMuscular;
    private String e1DolorArticular;
    private String e1SecresionNasal;
    private String e1DolorGarganta;
    private String e1Tos;
    private String e1DificultadResp;
    private String e1DolorPecho;
    private String e1NauseasVomito;
    private String e1DolorCabeza;
    private String e1DolorAbdominal;
    private String e1Diarrea;
    private String e1DificultadDormir;
    private String e1Debilidad;
    private String e1PerdidaOlfatoGusto;
    private String e1Mareo;
    private String e1Sarpullido;
    private String e1Desmayo;
    private String e1QuedoCama;
    private String e2Febricula;
    private String e2Fiebre;
    private String e2Escalofrio;
    private String e2TemblorEscalofrio;
    private String e2DolorMuscular;
    private String e2DolorArticular;
    private String e2SecresionNasal;
    private String e2DolorGarganta;
    private String e2Tos;
    private String e2DificultadResp;
    private String e2DolorPecho;
    private String e2NauseasVomito;
    private String e2DolorCabeza;
    private String e2DolorAbdominal;
    private String e2Diarrea;
    private String e2DificultadDormir;
    private String e2Debilidad;
    private String e2PerdidaOlfatoGusto;
    private String e2Mareo;
    private String e2Sarpullido;
    private String e2Desmayo;
    private String e2QuedoCama;
    private String e3Febricula;
    private String e3Fiebre;
    private String e3Escalofrio;
    private String e3TemblorEscalofrio;
    private String e3DolorMuscular;
    private String e3DolorArticular;
    private String e3SecresionNasal;
    private String e3DolorGarganta;
    private String e3Tos;
    private String e3DificultadResp;
    private String e3DolorPecho;
    private String e3NauseasVomito;
    private String e3DolorCabeza;
    private String e3DolorAbdominal;
    private String e3Diarrea;
    private String e3DificultadDormir;
    private String e3Debilidad;
    private String e3PerdidaOlfatoGusto;
    private String e3Mareo;
    private String e3Sarpullido;
    private String e3Desmayo;
    private String e3QuedoCama;
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

    private String presentoSintomasDosis1;
    private String dolorSitioDosis1;
    private String fiebreDosis1;
    private String cansancioDosis1;
    private String dolorCabezaDosis1;
    private String diarreaDosis1;
    private String vomitoDosis1;
    private String dolorMuscularDosis1;
    private String escalofriosDosis1;
    private String reaccionAlergicaDosis1;
    private String infeccionSitioDosis1;
    private String nauseasDosis1;
    private String bultosDosis1;
    private String otrosDosis1;
    private String desOtrosDosis1;

    private String presentoSintomasDosis2;
    private String dolorSitioDosis2;
    private String fiebreDosis2;
    private String cansancioDosis2;
    private String dolorCabezaDosis2;
    private String diarreaDosis2;
    private String vomitoDosis2;
    private String dolorMuscularDosis2;
    private String escalofriosDosis2;
    private String reaccionAlergicaDosis2;
    private String infeccionSitioDosis2;
    private String nauseasDosis2;
    private String bultosDosis2;
    private String otrosDosis2;
    private String desOtrosDosis2;

    private String presentoSintomasDosis3;
    private String dolorSitioDosis3;
    private String fiebreDosis3;
    private String cansancioDosis3;
    private String dolorCabezaDosis3;
    private String diarreaDosis3;
    private String vomitoDosis3;
    private String dolorMuscularDosis3;
    private String escalofriosDosis3;
    private String reaccionAlergicaDosis3;
    private String infeccionSitioDosis3;
    private String nauseasDosis3;
    private String bultosDosis3;
    private String otrosDosis3;
    private String desOtrosDosis3;

    private String covid19PosteriorVacuna;
    private String fechaEventoEnfermoPostVac;
    //private String sabeFechaEnfPostVac;
    //private String fechaEnfPostVac;
    //private String anioEnfPostVac;
    //private String mesEnfPostVac;


    public static NuevoCuestionarioCovid19v2Fragment create() {
        NuevoCuestionarioCovid19v2Fragment fragment = new NuevoCuestionarioCovid19v2Fragment();
        return fragment;
    }

    public NuevoCuestionarioCovid19v2Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
        participante = (Participante)getActivity().getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        infoMovil = new DeviceInfo(getActivity());
        visExitosa = getActivity().getIntent().getBooleanExtra(ConstantsDB.VIS_EXITO,false);
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
        View rootView = inflater.inflate(R.layout.fragment_nuevo_cuestionario_covid19_v2, container, false);
        mTitleView = ((TextView) rootView.findViewById(android.R.id.title));
        mNameView = ((EditText) rootView.findViewById(R.id.inputParticipante));
        mNameView.setText(participante.getNombreCompleto());
        mNameView.setEnabled(false);

        textE1SabeInicioSintoma = (TextView) rootView.findViewById(R.id.textE1SabeInicioSintoma);
        textE1FIS = (TextView) rootView.findViewById(R.id.textE1FIS);
        textE1FIS.setVisibility(View.GONE);
        textE1MesInicioSintoma = (TextView) rootView.findViewById(R.id.textE1MesInicioSintoma);
        textE1MesInicioSintoma.setVisibility(View.GONE);
        textE1AnioInicioSintoma = (TextView) rootView.findViewById(R.id.textE1AnioInicioSintoma);
        textE1AnioInicioSintoma.setVisibility(View.GONE);
        textE1ConoceLugarExposicion = (TextView) rootView.findViewById(R.id.textE1ConoceLugarExposicion);
        textE1ConoceLugarExposicion.setVisibility(View.GONE);
        textE1LugarExposicion = (TextView) rootView.findViewById(R.id.textE1LugarExposicion);
        textE1LugarExposicion.setVisibility(View.GONE);
        textE1BuscoAyuda = (TextView) rootView.findViewById(R.id.textE1BuscoAyuda);
        textE1BuscoAyuda.setVisibility(View.GONE);
        textE1DondeBuscoAyuda = (TextView) rootView.findViewById(R.id.textE1DondeBuscoAyuda);
        textE1DondeBuscoAyuda.setVisibility(View.GONE);
        textE1NombreCentro = (TextView) rootView.findViewById(R.id.textE1NombreCentro);
        textE1NombreCentro.setVisibility(View.GONE);
        textE1NombreHospital = (TextView) rootView.findViewById(R.id.textE1NombreHospital);
        textE1NombreHospital.setVisibility(View.GONE);
        textE1RecibioSeguimiento = (TextView) rootView.findViewById(R.id.textE1RecibioSeguimiento);
        textE1RecibioSeguimiento.setVisibility(View.GONE);
        textE1TipoSeguimiento = (TextView) rootView.findViewById(R.id.textE1TipoSeguimiento);
        textE1TipoSeguimiento.setVisibility(View.GONE);
        textE1TmpDespuesBuscoAyuda = (TextView) rootView.findViewById(R.id.textE1TmpDespuesBuscoAyuda);
        textE1TmpDespuesBuscoAyuda.setVisibility(View.GONE);
        textE1UnaNocheHospital = (TextView) rootView.findViewById(R.id.textE1UnaNocheHospital);
        textE1UnaNocheHospital.setVisibility(View.GONE);
        textE1QueHospital = (TextView) rootView.findViewById(R.id.textE1QueHospital);
        textE1QueHospital.setVisibility(View.GONE);
        textE1CuantasNochesHosp = (TextView) rootView.findViewById(R.id.textE1CuantasNochesHosp);
        textE1CuantasNochesHosp.setVisibility(View.GONE);
        textE1FechaAdmisionHosp = (TextView) rootView.findViewById(R.id.textE1FechaAdmisionHosp);
        textE1FechaAdmisionHosp.setVisibility(View.GONE);
        textE1FechaAltaHosp = (TextView) rootView.findViewById(R.id.textE1FechaAltaHosp);
        textE1FechaAltaHosp.setVisibility(View.GONE);
        textE1UtilizoOxigeno = (TextView) rootView.findViewById(R.id.textE1UtilizoOxigeno);
        textE1UtilizoOxigeno.setVisibility(View.GONE);
        textE1EstuvoUCI = (TextView) rootView.findViewById(R.id.textE1EstuvoUCI);
        textE1EstuvoUCI.setVisibility(View.GONE);
        textE1FueIntubado = (TextView) rootView.findViewById(R.id.textE1FueIntubado);
        textE1FueIntubado.setVisibility(View.GONE);
        textE1RecuperadoCovid19 = (TextView) rootView.findViewById(R.id.textE1RecuperadoCovid19);
        textE1RecuperadoCovid19.setVisibility(View.GONE);
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
        textE1TomoMedicamento = (TextView) rootView.findViewById(R.id.textE1TomoMedicamento);
        textE1TomoMedicamento.setVisibility(View.GONE);
        textE1QueMedicamento = (TextView) rootView.findViewById(R.id.textE1QueMedicamento);
        textE1QueMedicamento.setVisibility(View.GONE);

        textE2SabeInicioSintoma = (TextView) rootView.findViewById(R.id.textE2SabeInicioSintoma);
        textE2FIS = (TextView) rootView.findViewById(R.id.textE2FIS);
        textE2FIS.setVisibility(View.GONE);
        textE2MesInicioSintoma = (TextView) rootView.findViewById(R.id.textE2MesInicioSintoma);
        textE2MesInicioSintoma.setVisibility(View.GONE);
        textE2AnioInicioSintoma = (TextView) rootView.findViewById(R.id.textE2AnioInicioSintoma);
        textE2AnioInicioSintoma.setVisibility(View.GONE);
        textE2ConoceLugarExposicion = (TextView) rootView.findViewById(R.id.textE2ConoceLugarExposicion);
        textE2ConoceLugarExposicion.setVisibility(View.GONE);
        textE2LugarExposicion = (TextView) rootView.findViewById(R.id.textE2LugarExposicion);
        textE2LugarExposicion.setVisibility(View.GONE);
        textE2BuscoAyuda = (TextView) rootView.findViewById(R.id.textE2BuscoAyuda);
        textE2BuscoAyuda.setVisibility(View.GONE);
        textE2DondeBuscoAyuda = (TextView) rootView.findViewById(R.id.textE2DondeBuscoAyuda);
        textE2DondeBuscoAyuda.setVisibility(View.GONE);
        textE2NombreCentro = (TextView) rootView.findViewById(R.id.textE2NombreCentro);
        textE2NombreCentro.setVisibility(View.GONE);
        textE2NombreHospital = (TextView) rootView.findViewById(R.id.textE2NombreHospital);
        textE2NombreHospital.setVisibility(View.GONE);
        textE2RecibioSeguimiento = (TextView) rootView.findViewById(R.id.textE2RecibioSeguimiento);
        textE2RecibioSeguimiento.setVisibility(View.GONE);
        textE2TipoSeguimiento = (TextView) rootView.findViewById(R.id.textE2TipoSeguimiento);
        textE2TipoSeguimiento.setVisibility(View.GONE);
        textE2TmpDespuesBuscoAyuda = (TextView) rootView.findViewById(R.id.textE2TmpDespuesBuscoAyuda);
        textE2TmpDespuesBuscoAyuda.setVisibility(View.GONE);
        textE2UnaNocheHospital = (TextView) rootView.findViewById(R.id.textE2UnaNocheHospital);
        textE2UnaNocheHospital.setVisibility(View.GONE);
        textE2QueHospital = (TextView) rootView.findViewById(R.id.textE2QueHospital);
        textE2QueHospital.setVisibility(View.GONE);
        textE2CuantasNochesHosp = (TextView) rootView.findViewById(R.id.textE2CuantasNochesHosp);
        textE2CuantasNochesHosp.setVisibility(View.GONE);
        textE2FechaAdmisionHosp = (TextView) rootView.findViewById(R.id.textE2FechaAdmisionHosp);
        textE2FechaAdmisionHosp.setVisibility(View.GONE);
        textE2FechaAltaHosp = (TextView) rootView.findViewById(R.id.textE2FechaAltaHosp);
        textE2FechaAltaHosp.setVisibility(View.GONE);
        textE2UtilizoOxigeno = (TextView) rootView.findViewById(R.id.textE2UtilizoOxigeno);
        textE2UtilizoOxigeno.setVisibility(View.GONE);
        textE2EstuvoUCI = (TextView) rootView.findViewById(R.id.textE2EstuvoUCI);
        textE2EstuvoUCI.setVisibility(View.GONE);
        textE2FueIntubado = (TextView) rootView.findViewById(R.id.textE2FueIntubado);
        textE2FueIntubado.setVisibility(View.GONE);
        textE2RecuperadoCovid19 = (TextView) rootView.findViewById(R.id.textE2RecuperadoCovid19);
        textE2RecuperadoCovid19.setVisibility(View.GONE);
        textE2TieneFebricula = (TextView) rootView.findViewById(R.id.textE2TieneFebricula);
        textE2TieneFebricula.setVisibility(View.GONE);
        textE2TieneCansancio = (TextView) rootView.findViewById(R.id.textE2TieneCansancio);
        textE2TieneCansancio.setVisibility(View.GONE);
        textE2TieneDolorCabeza = (TextView) rootView.findViewById(R.id.textE2TieneDolorCabeza);
        textE2TieneDolorCabeza.setVisibility(View.GONE);
        textE2TienePerdidaOlfato = (TextView) rootView.findViewById(R.id.textE2TienePerdidaOlfato);
        textE2TienePerdidaOlfato.setVisibility(View.GONE);
        textE2TienePerdidaGusto = (TextView) rootView.findViewById(R.id.textE2TienePerdidaGusto);
        textE2TienePerdidaGusto.setVisibility(View.GONE);
        textE2TieneTos = (TextView) rootView.findViewById(R.id.textE2TieneTos);
        textE2TieneTos.setVisibility(View.GONE);
        textE2TieneDificultadRespirar = (TextView) rootView.findViewById(R.id.textE2TieneDificultadRespirar);
        textE2TieneDificultadRespirar.setVisibility(View.GONE);
        textE2TieneDolorPecho = (TextView) rootView.findViewById(R.id.textE2TieneDolorPecho);
        textE2TieneDolorPecho.setVisibility(View.GONE);
        textE2TienePalpitaciones = (TextView) rootView.findViewById(R.id.textE2TienePalpitaciones);
        textE2TienePalpitaciones.setVisibility(View.GONE);
        textE2TieneDolorArticulaciones = (TextView) rootView.findViewById(R.id.textE2TieneDolorArticulaciones);
        textE2TieneDolorArticulaciones.setVisibility(View.GONE);
        textE2TieneParalisis = (TextView) rootView.findViewById(R.id.textE2TieneParalisis);
        textE2TieneParalisis.setVisibility(View.GONE);
        textE2TieneMareos = (TextView) rootView.findViewById(R.id.textE2TieneMareos);
        textE2TieneMareos.setVisibility(View.GONE);
        textE2TienePensamientoNublado = (TextView) rootView.findViewById(R.id.textE2TienePensamientoNublado);
        textE2TienePensamientoNublado.setVisibility(View.GONE);
        textE2TieneProblemasDormir = (TextView) rootView.findViewById(R.id.textE2TieneProblemasDormir);
        textE2TieneProblemasDormir.setVisibility(View.GONE);
        textE2TieneDepresion = (TextView) rootView.findViewById(R.id.textE2TieneDepresion);
        textE2TieneDepresion.setVisibility(View.GONE);
        textE2TieneOtrosSintomas = (TextView) rootView.findViewById(R.id.textE2TieneOtrosSintomas);
        textE2TieneOtrosSintomas.setVisibility(View.GONE);
        textE2TieneCualesSintomas = (TextView) rootView.findViewById(R.id.textE2CualesSintomas);
        textE2TieneCualesSintomas.setVisibility(View.GONE);
        textE2SabeTiempoRecuperacion = (TextView) rootView.findViewById(R.id.textE2SabeTiempoRecuperacion);
        textE2SabeTiempoRecuperacion.setVisibility(View.GONE);
        textE2TiempoRecuperacion = (TextView) rootView.findViewById(R.id.textE2TiempoRecuperacion);
        textE2TiempoRecuperacion.setVisibility(View.GONE);
        textE2SeveridadEnfermedad = (TextView) rootView.findViewById(R.id.textE2SeveridadEnfermedad);
        textE2SeveridadEnfermedad.setVisibility(View.GONE);
        textE2TomoMedicamento = (TextView) rootView.findViewById(R.id.textE2TomoMedicamento);
        textE2TomoMedicamento.setVisibility(View.GONE);
        textE2QueMedicamento = (TextView) rootView.findViewById(R.id.textE2QueMedicamento);
        textE2QueMedicamento.setVisibility(View.GONE);

        textE3SabeInicioSintoma = (TextView) rootView.findViewById(R.id.textE3SabeInicioSintoma);
        textE3FIS = (TextView) rootView.findViewById(R.id.textE3FIS);
        textE3FIS.setVisibility(View.GONE);
        textE3MesInicioSintoma = (TextView) rootView.findViewById(R.id.textE3MesInicioSintoma);
        textE3MesInicioSintoma.setVisibility(View.GONE);
        textE3AnioInicioSintoma = (TextView) rootView.findViewById(R.id.textE3AnioInicioSintoma);
        textE3AnioInicioSintoma.setVisibility(View.GONE);
        textE3ConoceLugarExposicion = (TextView) rootView.findViewById(R.id.textE3ConoceLugarExposicion);
        textE3ConoceLugarExposicion.setVisibility(View.GONE);
        textE3LugarExposicion = (TextView) rootView.findViewById(R.id.textE3LugarExposicion);
        textE3LugarExposicion.setVisibility(View.GONE);
        textE3BuscoAyuda = (TextView) rootView.findViewById(R.id.textE3BuscoAyuda);
        textE3BuscoAyuda.setVisibility(View.GONE);
        textE3DondeBuscoAyuda = (TextView) rootView.findViewById(R.id.textE3DondeBuscoAyuda);
        textE3DondeBuscoAyuda.setVisibility(View.GONE);
        textE3NombreCentro = (TextView) rootView.findViewById(R.id.textE3NombreCentro);
        textE3NombreCentro.setVisibility(View.GONE);
        textE3NombreHospital = (TextView) rootView.findViewById(R.id.textE3NombreHospital);
        textE3NombreHospital.setVisibility(View.GONE);
        textE3RecibioSeguimiento = (TextView) rootView.findViewById(R.id.textE3RecibioSeguimiento);
        textE3RecibioSeguimiento.setVisibility(View.GONE);
        textE3TipoSeguimiento = (TextView) rootView.findViewById(R.id.textE3TipoSeguimiento);
        textE3TipoSeguimiento.setVisibility(View.GONE);
        textE3TmpDespuesBuscoAyuda = (TextView) rootView.findViewById(R.id.textE3TmpDespuesBuscoAyuda);
        textE3TmpDespuesBuscoAyuda.setVisibility(View.GONE);
        textE3UnaNocheHospital = (TextView) rootView.findViewById(R.id.textE3UnaNocheHospital);
        textE3UnaNocheHospital.setVisibility(View.GONE);
        textE3QueHospital = (TextView) rootView.findViewById(R.id.textE3QueHospital);
        textE3QueHospital.setVisibility(View.GONE);
        textE3CuantasNochesHosp = (TextView) rootView.findViewById(R.id.textE3CuantasNochesHosp);
        textE3CuantasNochesHosp.setVisibility(View.GONE);
        textE3FechaAdmisionHosp = (TextView) rootView.findViewById(R.id.textE3FechaAdmisionHosp);
        textE3FechaAdmisionHosp.setVisibility(View.GONE);
        textE3FechaAltaHosp = (TextView) rootView.findViewById(R.id.textE3FechaAltaHosp);
        textE3FechaAltaHosp.setVisibility(View.GONE);
        textE3UtilizoOxigeno = (TextView) rootView.findViewById(R.id.textE3UtilizoOxigeno);
        textE3UtilizoOxigeno.setVisibility(View.GONE);
        textE3EstuvoUCI = (TextView) rootView.findViewById(R.id.textE3EstuvoUCI);
        textE3EstuvoUCI.setVisibility(View.GONE);
        textE3FueIntubado = (TextView) rootView.findViewById(R.id.textE3FueIntubado);
        textE3FueIntubado.setVisibility(View.GONE);
        textE3RecuperadoCovid19 = (TextView) rootView.findViewById(R.id.textE3RecuperadoCovid19);
        textE3RecuperadoCovid19.setVisibility(View.GONE);
        textE3TieneFebricula = (TextView) rootView.findViewById(R.id.textE3TieneFebricula);
        textE3TieneFebricula.setVisibility(View.GONE);
        textE3TieneCansancio = (TextView) rootView.findViewById(R.id.textE3TieneCansancio);
        textE3TieneCansancio.setVisibility(View.GONE);
        textE3TieneDolorCabeza = (TextView) rootView.findViewById(R.id.textE3TieneDolorCabeza);
        textE3TieneDolorCabeza.setVisibility(View.GONE);
        textE3TienePerdidaOlfato = (TextView) rootView.findViewById(R.id.textE3TienePerdidaOlfato);
        textE3TienePerdidaOlfato.setVisibility(View.GONE);
        textE3TienePerdidaGusto = (TextView) rootView.findViewById(R.id.textE3TienePerdidaGusto);
        textE3TienePerdidaGusto.setVisibility(View.GONE);
        textE3TieneTos = (TextView) rootView.findViewById(R.id.textE3TieneTos);
        textE3TieneTos.setVisibility(View.GONE);
        textE3TieneDificultadRespirar = (TextView) rootView.findViewById(R.id.textE3TieneDificultadRespirar);
        textE3TieneDificultadRespirar.setVisibility(View.GONE);
        textE3TieneDolorPecho = (TextView) rootView.findViewById(R.id.textE3TieneDolorPecho);
        textE3TieneDolorPecho.setVisibility(View.GONE);
        textE3TienePalpitaciones = (TextView) rootView.findViewById(R.id.textE3TienePalpitaciones);
        textE3TienePalpitaciones.setVisibility(View.GONE);
        textE3TieneDolorArticulaciones = (TextView) rootView.findViewById(R.id.textE3TieneDolorArticulaciones);
        textE3TieneDolorArticulaciones.setVisibility(View.GONE);
        textE3TieneParalisis = (TextView) rootView.findViewById(R.id.textE3TieneParalisis);
        textE3TieneParalisis.setVisibility(View.GONE);
        textE3TieneMareos = (TextView) rootView.findViewById(R.id.textE3TieneMareos);
        textE3TieneMareos.setVisibility(View.GONE);
        textE3TienePensamientoNublado = (TextView) rootView.findViewById(R.id.textE3TienePensamientoNublado);
        textE3TienePensamientoNublado.setVisibility(View.GONE);
        textE3TieneProblemasDormir = (TextView) rootView.findViewById(R.id.textE3TieneProblemasDormir);
        textE3TieneProblemasDormir.setVisibility(View.GONE);
        textE3TieneDepresion = (TextView) rootView.findViewById(R.id.textE3TieneDepresion);
        textE3TieneDepresion.setVisibility(View.GONE);
        textE3TieneOtrosSintomas = (TextView) rootView.findViewById(R.id.textE3TieneOtrosSintomas);
        textE3TieneOtrosSintomas.setVisibility(View.GONE);
        textE3TieneCualesSintomas = (TextView) rootView.findViewById(R.id.textE3CualesSintomas);
        textE3TieneCualesSintomas.setVisibility(View.GONE);
        textE3SabeTiempoRecuperacion = (TextView) rootView.findViewById(R.id.textE3SabeTiempoRecuperacion);
        textE3SabeTiempoRecuperacion.setVisibility(View.GONE);
        textE3TiempoRecuperacion = (TextView) rootView.findViewById(R.id.textE3TiempoRecuperacion);
        textE3TiempoRecuperacion.setVisibility(View.GONE);
        textE3SeveridadEnfermedad = (TextView) rootView.findViewById(R.id.textE3SeveridadEnfermedad);
        textE3SeveridadEnfermedad.setVisibility(View.GONE);
        textE3TomoMedicamento = (TextView) rootView.findViewById(R.id.textE3TomoMedicamento);
        textE3TomoMedicamento.setVisibility(View.GONE);
        textE3QueMedicamento = (TextView) rootView.findViewById(R.id.textE3QueMedicamento);
        textE3QueMedicamento.setVisibility(View.GONE);

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
        textE1OtroMedicamento = (TextView) rootView.findViewById(R.id.textE1OtroMedicamento);
        textE1OtroMedicamento.setVisibility(View.GONE);
        textE1OxigenoDomicilio = (TextView) rootView.findViewById(R.id.textE1OxigenoDomicilio);
        textE1OxigenoDomicilio.setVisibility(View.GONE);
        textE1TiempoOxigenoDom = (TextView) rootView.findViewById(R.id.textE1TiempoOxigenoDom);
        textE1TiempoOxigenoDom.setVisibility(View.GONE);
        textE1QueSintomasPresenta = (TextView) rootView.findViewById(R.id.textE1QueSintomasPresenta);
        textE1QueSintomasPresenta.setVisibility(View.GONE);
        textE1SabeFechaAlta = (TextView) rootView.findViewById(R.id.textE1SabeFechaAlta);
        textE1SabeFechaAlta.setVisibility(View.GONE);
        textE1SabeFechaAdmision = (TextView) rootView.findViewById(R.id.textE1SabeFechaAdmision);
        textE1SabeFechaAdmision.setVisibility(View.GONE);
        textE1SabeCuantasNoches = (TextView) rootView.findViewById(R.id.textE1SabeCuantasNoches);
        textE1SabeCuantasNoches.setVisibility(View.GONE);
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

        textE2FumadoPrevioEnfermedad = (TextView) rootView.findViewById(R.id.textE2FumadoPrevioEnfermedad);
        textE2FumadoPrevioEnfermedad.setVisibility(View.GONE);
        textE2FumaActualmente = (TextView) rootView.findViewById(R.id.textE2FumaActualmente);
        textE2FumaActualmente.setVisibility(View.GONE);
        textE2OtroMedicamento = (TextView) rootView.findViewById(R.id.textE2OtroMedicamento);
        textE2OtroMedicamento.setVisibility(View.GONE);
        textE2OxigenoDomicilio = (TextView) rootView.findViewById(R.id.textE2OxigenoDomicilio);
        textE2OxigenoDomicilio.setVisibility(View.GONE);
        textE2TiempoOxigenoDom = (TextView) rootView.findViewById(R.id.textE2TiempoOxigenoDom);
        textE2TiempoOxigenoDom.setVisibility(View.GONE);
        textE2QueSintomasPresenta = (TextView) rootView.findViewById(R.id.textE2QueSintomasPresenta);
        textE2QueSintomasPresenta.setVisibility(View.GONE);
        textE2SabeFechaAlta = (TextView) rootView.findViewById(R.id.textE2SabeFechaAlta);
        textE2SabeFechaAlta.setVisibility(View.GONE);
        textE2SabeFechaAdmision = (TextView) rootView.findViewById(R.id.textE2SabeFechaAdmision);
        textE2SabeFechaAdmision.setVisibility(View.GONE);
        textE2SabeCuantasNoches = (TextView) rootView.findViewById(R.id.textE2SabeCuantasNoches);
        textE2SabeCuantasNoches.setVisibility(View.GONE);
        textE2Embarazada = (TextView) rootView.findViewById(R.id.textE2Embarazada);
        textE2Embarazada.setVisibility(View.GONE);
        textE2RecuerdaSemanasEmb = (TextView) rootView.findViewById(R.id.textE2RecuerdaSemanasEmb);
        textE2RecuerdaSemanasEmb.setVisibility(View.GONE);
        textE2SemanasEmbarazo = (TextView) rootView.findViewById(R.id.textE2SemanasEmbarazo);
        textE2SemanasEmbarazo.setVisibility(View.GONE);
        textE2FinalEmbarazo = (TextView) rootView.findViewById(R.id.textE2FinalEmbarazo);
        textE2FinalEmbarazo.setVisibility(View.GONE);
        textE2OtroFinalEmbarazo = (TextView) rootView.findViewById(R.id.textE2OtroFinalEmbarazo);
        textE2OtroFinalEmbarazo.setVisibility(View.GONE);
        textE2DabaPecho = (TextView) rootView.findViewById(R.id.textE2DabaPecho);
        textE2DabaPecho.setVisibility(View.GONE);

        textE3FumadoPrevioEnfermedad = (TextView) rootView.findViewById(R.id.textE3FumadoPrevioEnfermedad);
        textE3FumadoPrevioEnfermedad.setVisibility(View.GONE);
        textE3FumaActualmente = (TextView) rootView.findViewById(R.id.textE3FumaActualmente);
        textE3FumaActualmente.setVisibility(View.GONE);
        textE3OtroMedicamento = (TextView) rootView.findViewById(R.id.textE3OtroMedicamento);
        textE3OtroMedicamento.setVisibility(View.GONE);
        textE3OxigenoDomicilio = (TextView) rootView.findViewById(R.id.textE3OxigenoDomicilio);
        textE3OxigenoDomicilio.setVisibility(View.GONE);
        textE3TiempoOxigenoDom = (TextView) rootView.findViewById(R.id.textE3TiempoOxigenoDom);
        textE3TiempoOxigenoDom.setVisibility(View.GONE);
        textE3QueSintomasPresenta = (TextView) rootView.findViewById(R.id.textE3QueSintomasPresenta);
        textE3QueSintomasPresenta.setVisibility(View.GONE);
        textE3SabeFechaAlta = (TextView) rootView.findViewById(R.id.textE3SabeFechaAlta);
        textE3SabeFechaAlta.setVisibility(View.GONE);
        textE3SabeFechaAdmision = (TextView) rootView.findViewById(R.id.textE3SabeFechaAdmision);
        textE3SabeFechaAdmision.setVisibility(View.GONE);
        textE3SabeCuantasNoches = (TextView) rootView.findViewById(R.id.textE3SabeCuantasNoches);
        textE3SabeCuantasNoches.setVisibility(View.GONE);
        textE3Embarazada = (TextView) rootView.findViewById(R.id.textE3Embarazada);
        textE3Embarazada.setVisibility(View.GONE);
        textE3RecuerdaSemanasEmb = (TextView) rootView.findViewById(R.id.textE3RecuerdaSemanasEmb);
        textE3RecuerdaSemanasEmb.setVisibility(View.GONE);
        textE3SemanasEmbarazo = (TextView) rootView.findViewById(R.id.textE3SemanasEmbarazo);
        textE3SemanasEmbarazo.setVisibility(View.GONE);
        textE3FinalEmbarazo = (TextView) rootView.findViewById(R.id.textE3FinalEmbarazo);
        textE3FinalEmbarazo.setVisibility(View.GONE);
        textE3OtroFinalEmbarazo = (TextView) rootView.findViewById(R.id.textE3OtroFinalEmbarazo);
        textE3OtroFinalEmbarazo.setVisibility(View.GONE);
        textE3DabaPecho = (TextView) rootView.findViewById(R.id.textE3DabaPecho);
        textE3DabaPecho.setVisibility(View.GONE);

        textTrabajadorSalud = (TextView) rootView.findViewById(R.id.textTrabajadorSalud);
        textEnfermoCovid19 = (TextView) rootView.findViewById(R.id.textEnfermoCovid19);
        textCuantasVecesEnfermo = (TextView) rootView.findViewById(R.id.textCuantasVecesEnfermo);
        textCuantasVecesEnfermo.setVisibility(View.GONE);
        textSabeEvento1 = (TextView) rootView.findViewById(R.id.textSabeEvento1);
        textSabeEvento1.setVisibility(View.GONE);
        textEvento1 = (TextView) rootView.findViewById(R.id.textEvento1);
        textEvento1.setVisibility(View.GONE);
        textAnioEvento1 = (TextView) rootView.findViewById(R.id.textAnioEvento1);
        textAnioEvento1.setVisibility(View.GONE);
        textMesEvento1 = (TextView) rootView.findViewById(R.id.textMesEvento1);
        textMesEvento1.setVisibility(View.GONE);
        textSabeEvento2 = (TextView) rootView.findViewById(R.id.textSabeEvento2);
        textSabeEvento2.setVisibility(View.GONE);
        textEvento2 = (TextView) rootView.findViewById(R.id.textEvento2);
        textEvento2.setVisibility(View.GONE);
        textAnioEvento2 = (TextView) rootView.findViewById(R.id.textAnioEvento2);
        textAnioEvento2.setVisibility(View.GONE);
        textMesEvento2 = (TextView) rootView.findViewById(R.id.textMesEvento2);
        textMesEvento2.setVisibility(View.GONE);
        textSabeEvento3 = (TextView) rootView.findViewById(R.id.textSabeEvento3);
        textSabeEvento3.setVisibility(View.GONE);
        textEvento3 = (TextView) rootView.findViewById(R.id.textEvento3);
        textEvento3.setVisibility(View.GONE);
        textAnioEvento3 = (TextView) rootView.findViewById(R.id.textAnioEvento3);
        textAnioEvento3.setVisibility(View.GONE);
        textMesEvento3 = (TextView) rootView.findViewById(R.id.textMesEvento3);
        textMesEvento3.setVisibility(View.GONE);
        textDesdeSintomasE1 = (TextView) rootView.findViewById(R.id.textDesdeSintomasE1);
        textDesdeSintomasE1.setVisibility(View.GONE);
        textE1Febricula = (TextView) rootView.findViewById(R.id.textE1Febricula);
        textE1Fiebre = (TextView) rootView.findViewById(R.id.textE1Fiebre);
        textE1Escalofrio = (TextView) rootView.findViewById(R.id.textE1Escalofrio);
        textE1TemblorEscalofrio = (TextView) rootView.findViewById(R.id.textE1TemblorEscalofrio);
        textE1DolorMuscular = (TextView) rootView.findViewById(R.id.textE1DolorMuscular);
        textE1DolorArticular = (TextView) rootView.findViewById(R.id.textE1DolorArticular);
        textE1SecresionNasal = (TextView) rootView.findViewById(R.id.textE1SecresionNasal);
        textE1DolorGarganta = (TextView) rootView.findViewById(R.id.textE1DolorGarganta);
        textE1Tos = (TextView) rootView.findViewById(R.id.textE1Tos);
        textE1DificultadResp = (TextView) rootView.findViewById(R.id.textE1DificultadResp);
        textE1DolorPecho = (TextView) rootView.findViewById(R.id.textE1DolorPecho);
        textE1NauseasVomito = (TextView) rootView.findViewById(R.id.textE1NauseasVomito);
        textE1DolorCabeza = (TextView) rootView.findViewById(R.id.textE1DolorCabeza);
        textE1DolorAbdominal = (TextView) rootView.findViewById(R.id.textE1DolorAbdominal);
        textE1Diarrea = (TextView) rootView.findViewById(R.id.textE1Diarrea);
        textE1DificultadDormir = (TextView) rootView.findViewById(R.id.textE1DificultadDormir);
        textE1Debilidad = (TextView) rootView.findViewById(R.id.textE1Debilidad);
        textE1PerdidaOlfatoGusto = (TextView) rootView.findViewById(R.id.textE1PerdidaOlfatoGusto);
        textE1Mareo = (TextView) rootView.findViewById(R.id.textE1Mareo);
        textE1Sarpullido = (TextView) rootView.findViewById(R.id.textE1Sarpullido);
        textE1Desmayo = (TextView) rootView.findViewById(R.id.textE1Desmayo);
        textE1QuedoCama = (TextView) rootView.findViewById(R.id.textE1QuedoCama);
        textDesdeSintomasE2 = (TextView) rootView.findViewById(R.id.textDesdeSintomasE2);
        textDesdeSintomasE2.setVisibility(View.GONE);
        textE2Febricula = (TextView) rootView.findViewById(R.id.textE2Febricula);
        textE2Fiebre = (TextView) rootView.findViewById(R.id.textE2Fiebre);
        textE2Escalofrio = (TextView) rootView.findViewById(R.id.textE2Escalofrio);
        textE2TemblorEscalofrio = (TextView) rootView.findViewById(R.id.textE2TemblorEscalofrio);
        textE2DolorMuscular = (TextView) rootView.findViewById(R.id.textE2DolorMuscular);
        textE2DolorArticular = (TextView) rootView.findViewById(R.id.textE2DolorArticular);
        textE2SecresionNasal = (TextView) rootView.findViewById(R.id.textE2SecresionNasal);
        textE2DolorGarganta = (TextView) rootView.findViewById(R.id.textE2DolorGarganta);
        textE2Tos = (TextView) rootView.findViewById(R.id.textE2Tos);
        textE2DificultadResp = (TextView) rootView.findViewById(R.id.textE2DificultadResp);
        textE2DolorPecho = (TextView) rootView.findViewById(R.id.textE2DolorPecho);
        textE2NauseasVomito = (TextView) rootView.findViewById(R.id.textE2NauseasVomito);
        textE2DolorCabeza = (TextView) rootView.findViewById(R.id.textE2DolorCabeza);
        textE2DolorAbdominal = (TextView) rootView.findViewById(R.id.textE2DolorAbdominal);
        textE2Diarrea = (TextView) rootView.findViewById(R.id.textE2Diarrea);
        textE2DificultadDormir = (TextView) rootView.findViewById(R.id.textE2DificultadDormir);
        textE2Debilidad = (TextView) rootView.findViewById(R.id.textE2Debilidad);
        textE2PerdidaOlfatoGusto = (TextView) rootView.findViewById(R.id.textE2PerdidaOlfatoGusto);
        textE2Mareo = (TextView) rootView.findViewById(R.id.textE2Mareo);
        textE2Sarpullido = (TextView) rootView.findViewById(R.id.textE2Sarpullido);
        textE2Desmayo = (TextView) rootView.findViewById(R.id.textE2Desmayo);
        textE2QuedoCama = (TextView) rootView.findViewById(R.id.textE2QuedoCama);
        textDesdeSintomasE3 = (TextView) rootView.findViewById(R.id.textDesdeSintomasE3);
        textDesdeSintomasE3.setVisibility(View.GONE);
        textE3Febricula = (TextView) rootView.findViewById(R.id.textE3Febricula);
        textE3Fiebre = (TextView) rootView.findViewById(R.id.textE3Fiebre);
        textE3Escalofrio = (TextView) rootView.findViewById(R.id.textE3Escalofrio);
        textE3TemblorEscalofrio = (TextView) rootView.findViewById(R.id.textE3TemblorEscalofrio);
        textE3DolorMuscular = (TextView) rootView.findViewById(R.id.textE3DolorMuscular);
        textE3DolorArticular = (TextView) rootView.findViewById(R.id.textE3DolorArticular);
        textE3SecresionNasal = (TextView) rootView.findViewById(R.id.textE3SecresionNasal);
        textE3DolorGarganta = (TextView) rootView.findViewById(R.id.textE3DolorGarganta);
        textE3Tos = (TextView) rootView.findViewById(R.id.textE3Tos);
        textE3DificultadResp = (TextView) rootView.findViewById(R.id.textE3DificultadResp);
        textE3DolorPecho = (TextView) rootView.findViewById(R.id.textE3DolorPecho);
        textE3NauseasVomito = (TextView) rootView.findViewById(R.id.textE3NauseasVomito);
        textE3DolorCabeza = (TextView) rootView.findViewById(R.id.textE3DolorCabeza);
        textE3DolorAbdominal = (TextView) rootView.findViewById(R.id.textE3DolorAbdominal);
        textE3Diarrea = (TextView) rootView.findViewById(R.id.textE3Diarrea);
        textE3DificultadDormir = (TextView) rootView.findViewById(R.id.textE3DificultadDormir);
        textE3Debilidad = (TextView) rootView.findViewById(R.id.textE3Debilidad);
        textE3PerdidaOlfatoGusto = (TextView) rootView.findViewById(R.id.textE3PerdidaOlfatoGusto);
        textE3Mareo = (TextView) rootView.findViewById(R.id.textE3Mareo);
        textE3Sarpullido = (TextView) rootView.findViewById(R.id.textE3Sarpullido);
        textE3Desmayo = (TextView) rootView.findViewById(R.id.textE3Desmayo);
        textE3QuedoCama = (TextView) rootView.findViewById(R.id.textE3QuedoCama);
        textVacunadoCovid19 = (TextView) rootView.findViewById(R.id.textVacunadoCovid19);
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

        textPresentoSintomasDosis1 = (TextView) rootView.findViewById(R.id.textPresentoSintomasDosis1);
        textSintomasDosis1 = (TextView) rootView.findViewById(R.id.textSintomasDosis1);
        textDolorSitioDosis1 = (TextView) rootView.findViewById(R.id.textDolorSitioDosis1);
        textFiebreDosis1 = (TextView) rootView.findViewById(R.id.textFiebreDosis1);
        textCansancioDosis1 = (TextView) rootView.findViewById(R.id.textCansancioDosis1);
        textDolorCabezaDosis1 = (TextView) rootView.findViewById(R.id.textDolorCabezaDosis1);
        textDiarreaDosis1 = (TextView) rootView.findViewById(R.id.textDiarreaDosis1);
        textVomitoDosis1 = (TextView) rootView.findViewById(R.id.textVomitoDosis1);
        textDolorMuscularDosis1 = (TextView) rootView.findViewById(R.id.textDolorMuscularDosis1);
        textEscalofriosDosis1 = (TextView) rootView.findViewById(R.id.textEscalofriosDosis1);
        textReaccionAlergicaDosis1 = (TextView) rootView.findViewById(R.id.textReaccionAlergicaDosis1);
        textInfeccionSitioDosis1 = (TextView) rootView.findViewById(R.id.textInfeccionSitioDosis1);
        textNauseasDosis1 = (TextView) rootView.findViewById(R.id.textNauseasDosis1);
        textBultosDosis1 = (TextView) rootView.findViewById(R.id.textBultosDosis1);
        textOtrosDosis1 = (TextView) rootView.findViewById(R.id.textOtrosDosis1);
        textDesOtrosDosis1 = (TextView) rootView.findViewById(R.id.textDesOtrosDosis1);

        textPresentoSintomasDosis2 = (TextView) rootView.findViewById(R.id.textPresentoSintomasDosis2);
        textSintomasDosis2 = (TextView) rootView.findViewById(R.id.textSintomasDosis2);
        textDolorSitioDosis2 = (TextView) rootView.findViewById(R.id.textDolorSitioDosis2);
        textFiebreDosis2 = (TextView) rootView.findViewById(R.id.textFiebreDosis2);
        textCansancioDosis2 = (TextView) rootView.findViewById(R.id.textCansancioDosis2);
        textDolorCabezaDosis2 = (TextView) rootView.findViewById(R.id.textDolorCabezaDosis2);
        textDiarreaDosis2 = (TextView) rootView.findViewById(R.id.textDiarreaDosis2);
        textVomitoDosis2 = (TextView) rootView.findViewById(R.id.textVomitoDosis2);
        textDolorMuscularDosis2 = (TextView) rootView.findViewById(R.id.textDolorMuscularDosis2);
        textEscalofriosDosis2 = (TextView) rootView.findViewById(R.id.textEscalofriosDosis2);
        textReaccionAlergicaDosis2 = (TextView) rootView.findViewById(R.id.textReaccionAlergicaDosis2);
        textInfeccionSitioDosis2 = (TextView) rootView.findViewById(R.id.textInfeccionSitioDosis2);
        textNauseasDosis2 = (TextView) rootView.findViewById(R.id.textNauseasDosis2);
        textBultosDosis2 = (TextView) rootView.findViewById(R.id.textBultosDosis2);
        textOtrosDosis2 = (TextView) rootView.findViewById(R.id.textOtrosDosis2);
        textDesOtrosDosis2 = (TextView) rootView.findViewById(R.id.textDesOtrosDosis2);

        textPresentoSintomasDosis3 = (TextView) rootView.findViewById(R.id.textPresentoSintomasDosis3);
        textSintomasDosis3 = (TextView) rootView.findViewById(R.id.textSintomasDosis3);
        textDolorSitioDosis3 = (TextView) rootView.findViewById(R.id.textDolorSitioDosis3);
        textFiebreDosis3 = (TextView) rootView.findViewById(R.id.textFiebreDosis3);
        textCansancioDosis3 = (TextView) rootView.findViewById(R.id.textCansancioDosis3);
        textDolorCabezaDosis3 = (TextView) rootView.findViewById(R.id.textDolorCabezaDosis3);
        textDiarreaDosis3 = (TextView) rootView.findViewById(R.id.textDiarreaDosis3);
        textVomitoDosis3 = (TextView) rootView.findViewById(R.id.textVomitoDosis3);
        textDolorMuscularDosis3 = (TextView) rootView.findViewById(R.id.textDolorMuscularDosis3);
        textEscalofriosDosis3 = (TextView) rootView.findViewById(R.id.textEscalofriosDosis3);
        textReaccionAlergicaDosis3 = (TextView) rootView.findViewById(R.id.textReaccionAlergicaDosis3);
        textInfeccionSitioDosis3 = (TextView) rootView.findViewById(R.id.textInfeccionSitioDosis3);
        textNauseasDosis3 = (TextView) rootView.findViewById(R.id.textNauseasDosis3);
        textBultosDosis3 = (TextView) rootView.findViewById(R.id.textBultosDosis3);
        textOtrosDosis3 = (TextView) rootView.findViewById(R.id.textOtrosDosis3);
        textDesOtrosDosis3 = (TextView) rootView.findViewById(R.id.textDesOtrosDosis3);

        textCovid19PosteriorVacuna = (TextView) rootView.findViewById(R.id.textCovid19PosteriorVacuna);
        textFechaEventoEnfermoPostVac = (TextView) rootView.findViewById(R.id.textFechaEventoEnfermoPostVac);
        //textSabeFechaEnfPostVac = (TextView) rootView.findViewById(R.id.textSabeFechaEnfPostVac);
        //textFechaEnfPostVac = (TextView) rootView.findViewById(R.id.textFechaEnfPostVac);
        //textAnioEnfPostVac = (TextView) rootView.findViewById(R.id.textAnioEnfPostVac);
        //textMesEnfPostVac = (TextView) rootView.findViewById(R.id.textMesEnfPostVac);

        textE1Febricula.setVisibility(View.GONE);
        textE1Fiebre.setVisibility(View.GONE);
        textE1Escalofrio.setVisibility(View.GONE);
        textE1TemblorEscalofrio.setVisibility(View.GONE);
        textE1DolorMuscular.setVisibility(View.GONE);
        textE1DolorArticular.setVisibility(View.GONE);
        textE1SecresionNasal.setVisibility(View.GONE);
        textE1DolorGarganta.setVisibility(View.GONE);
        textE1Tos.setVisibility(View.GONE);
        textE1DificultadResp.setVisibility(View.GONE);
        textE1DolorPecho.setVisibility(View.GONE);
        textE1NauseasVomito.setVisibility(View.GONE);
        textE1DolorCabeza.setVisibility(View.GONE);
        textE1DolorAbdominal.setVisibility(View.GONE);
        textE1Diarrea.setVisibility(View.GONE);
        textE1DificultadDormir.setVisibility(View.GONE);
        textE1Debilidad.setVisibility(View.GONE);
        textE1PerdidaOlfatoGusto.setVisibility(View.GONE);
        textE1Mareo.setVisibility(View.GONE);
        textE1Sarpullido.setVisibility(View.GONE);
        textE1Desmayo.setVisibility(View.GONE);
        textE1QuedoCama.setVisibility(View.GONE);
        textE2Febricula.setVisibility(View.GONE);
        textE2Fiebre.setVisibility(View.GONE);
        textE2Escalofrio.setVisibility(View.GONE);
        textE2TemblorEscalofrio.setVisibility(View.GONE);
        textE2DolorMuscular.setVisibility(View.GONE);
        textE2DolorArticular.setVisibility(View.GONE);
        textE2SecresionNasal.setVisibility(View.GONE);
        textE2DolorGarganta.setVisibility(View.GONE);
        textE2Tos.setVisibility(View.GONE);
        textE2DificultadResp.setVisibility(View.GONE);
        textE2DolorPecho.setVisibility(View.GONE);
        textE2NauseasVomito.setVisibility(View.GONE);
        textE2DolorCabeza.setVisibility(View.GONE);
        textE2DolorAbdominal.setVisibility(View.GONE);
        textE2Diarrea.setVisibility(View.GONE);
        textE2DificultadDormir.setVisibility(View.GONE);
        textE2Debilidad.setVisibility(View.GONE);
        textE2PerdidaOlfatoGusto.setVisibility(View.GONE);
        textE2Mareo.setVisibility(View.GONE);
        textE2Sarpullido.setVisibility(View.GONE);
        textE2Desmayo.setVisibility(View.GONE);
        textE2QuedoCama.setVisibility(View.GONE);
        textE3Febricula.setVisibility(View.GONE);
        textE3Fiebre.setVisibility(View.GONE);
        textE3Escalofrio.setVisibility(View.GONE);
        textE3TemblorEscalofrio.setVisibility(View.GONE);
        textE3DolorMuscular.setVisibility(View.GONE);
        textE3DolorArticular.setVisibility(View.GONE);
        textE3SecresionNasal.setVisibility(View.GONE);
        textE3DolorGarganta.setVisibility(View.GONE);
        textE3Tos.setVisibility(View.GONE);
        textE3DificultadResp.setVisibility(View.GONE);
        textE3DolorPecho.setVisibility(View.GONE);
        textE3NauseasVomito.setVisibility(View.GONE);
        textE3DolorCabeza.setVisibility(View.GONE);
        textE3DolorAbdominal.setVisibility(View.GONE);
        textE3Diarrea.setVisibility(View.GONE);
        textE3DificultadDormir.setVisibility(View.GONE);
        textE3Debilidad.setVisibility(View.GONE);
        textE3PerdidaOlfatoGusto.setVisibility(View.GONE);
        textE3Mareo.setVisibility(View.GONE);
        textE3Sarpullido.setVisibility(View.GONE);
        textE3Desmayo.setVisibility(View.GONE);
        textE3QuedoCama.setVisibility(View.GONE);
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

        textPresentoSintomasDosis1.setVisibility(View.GONE);
        textSintomasDosis1.setVisibility(View.GONE);
        textDolorSitioDosis1.setVisibility(View.GONE);
        textFiebreDosis1.setVisibility(View.GONE);
        textCansancioDosis1.setVisibility(View.GONE);
        textDolorCabezaDosis1.setVisibility(View.GONE);
        textDiarreaDosis1.setVisibility(View.GONE);
        textVomitoDosis1.setVisibility(View.GONE);
        textDolorMuscularDosis1.setVisibility(View.GONE);
        textEscalofriosDosis1.setVisibility(View.GONE);
        textReaccionAlergicaDosis1.setVisibility(View.GONE);
        textInfeccionSitioDosis1.setVisibility(View.GONE);
        textNauseasDosis1.setVisibility(View.GONE);
        textBultosDosis1.setVisibility(View.GONE);
        textOtrosDosis1.setVisibility(View.GONE);
        textDesOtrosDosis1.setVisibility(View.GONE);

        textPresentoSintomasDosis2.setVisibility(View.GONE);
        textSintomasDosis2.setVisibility(View.GONE);
        textDolorSitioDosis2.setVisibility(View.GONE);
        textFiebreDosis2.setVisibility(View.GONE);
        textCansancioDosis2.setVisibility(View.GONE);
        textDolorCabezaDosis2.setVisibility(View.GONE);
        textDiarreaDosis2.setVisibility(View.GONE);
        textVomitoDosis2.setVisibility(View.GONE);
        textDolorMuscularDosis2.setVisibility(View.GONE);
        textEscalofriosDosis2.setVisibility(View.GONE);
        textReaccionAlergicaDosis2.setVisibility(View.GONE);
        textInfeccionSitioDosis2.setVisibility(View.GONE);
        textNauseasDosis2.setVisibility(View.GONE);
        textBultosDosis2.setVisibility(View.GONE);
        textOtrosDosis2.setVisibility(View.GONE);
        textDesOtrosDosis2.setVisibility(View.GONE);

        textPresentoSintomasDosis3.setVisibility(View.GONE);
        textSintomasDosis3.setVisibility(View.GONE);
        textDolorSitioDosis3.setVisibility(View.GONE);
        textFiebreDosis3.setVisibility(View.GONE);
        textCansancioDosis3.setVisibility(View.GONE);
        textDolorCabezaDosis3.setVisibility(View.GONE);
        textDiarreaDosis3.setVisibility(View.GONE);
        textVomitoDosis3.setVisibility(View.GONE);
        textDolorMuscularDosis3.setVisibility(View.GONE);
        textEscalofriosDosis3.setVisibility(View.GONE);
        textReaccionAlergicaDosis3.setVisibility(View.GONE);
        textInfeccionSitioDosis3.setVisibility(View.GONE);
        textNauseasDosis3.setVisibility(View.GONE);
        textBultosDosis3.setVisibility(View.GONE);
        textOtrosDosis3.setVisibility(View.GONE);
        textDesOtrosDosis3.setVisibility(View.GONE);

        textCovid19PosteriorVacuna.setVisibility(View.GONE);
        textFechaEventoEnfermoPostVac.setVisibility(View.GONE);
        //textSabeFechaEnfPostVac.setVisibility(View.GONE);
        //textFechaEnfPostVac.setVisibility(View.GONE);
        //textAnioEnfPostVac.setVisibility(View.GONE);
        //textMesEnfPostVac.setVisibility(View.GONE);

        spinE1SabeInicioSintoma = (Spinner) rootView.findViewById(R.id.spinE1SabeInicioSintoma);
        inputE1FIS = (EditText) rootView.findViewById(R.id.inputE1FIS);
        spinE1MesInicioSintoma = (Spinner) rootView.findViewById(R.id.spinE1MesInicioSintoma);
        inputE1AnioInicioSintoma = (EditText) rootView.findViewById(R.id.inputE1AnioInicioSintoma);
        //poner el año actual, no hay mas opciones
        inputE1AnioInicioSintoma.setText(String.valueOf(c.get(Calendar.YEAR)));
        e1AnioInicioSintoma = inputE1AnioInicioSintoma.getText().toString();
        inputE1AnioInicioSintoma.setEnabled(false);
        spinE1ConoceLugarExposicion = (Spinner) rootView.findViewById(R.id.spinE1ConoceLugarExposicion);
        inputE1LugarExposicion = (EditText) rootView.findViewById(R.id.inputE1LugarExposicion);
        spinE1BuscoAyuda = (Spinner) rootView.findViewById(R.id.spinE1BuscoAyuda);
        spinE1DondeBuscoAyuda = (Spinner) rootView.findViewById(R.id.spinE1DondeBuscoAyuda);
        inputE1NombreCentro = (EditText) rootView.findViewById(R.id.inputE1NombreCentro);
        inputE1NombreHospital = (EditText) rootView.findViewById(R.id.inputE1NombreHospital);
        spinE1RecibioSeguimiento = (Spinner) rootView.findViewById(R.id.spinE1RecibioSeguimiento);
        spinE1TipoSeguimiento = (Spinner) rootView.findViewById(R.id.spinE1TipoSeguimiento);
        spinE1TmpDespuesBuscoAyuda = (Spinner) rootView.findViewById(R.id.spinE1TmpDespuesBuscoAyuda);
        spinE1UnaNocheHospital = (Spinner) rootView.findViewById(R.id.spinE1UnaNocheHospital);
        inputE1QueHospital = (EditText) rootView.findViewById(R.id.inputE1QueHospital);
        inputE1CuantasNochesHosp = (EditText) rootView.findViewById(R.id.inputE1CuantasNochesHosp);
        inputE1FechaAdmisionHosp = (EditText) rootView.findViewById(R.id.inputE1FechaAdmisionHosp);
        inputE1FechaAltaHosp = (EditText) rootView.findViewById(R.id.inputE1FechaAltaHosp);
        spinE1UtilizoOxigeno = (Spinner) rootView.findViewById(R.id.spinE1UtilizoOxigeno);
        spinE1EstuvoUCI = (Spinner) rootView.findViewById(R.id.spinE1EstuvoUCI);
        spinE1FueIntubado = (Spinner) rootView.findViewById(R.id.spinE1FueIntubado);
        spinE1RecuperadoCovid19 = (Spinner) rootView.findViewById(R.id.spinE1RecuperadoCovid19);
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
        spinE1TomoMedicamento = (Spinner) rootView.findViewById(R.id.spinE1TomoMedicamento);
        spinE1QueMedicamento = (MultiSpinner) rootView.findViewById(R.id.spinE1QueMedicamento);
        spinE1OxigenoDomicilio = (Spinner) rootView.findViewById(R.id.spinE1OxigenoDomicilio);
        spinE1TiempoOxigenoDom = (Spinner) rootView.findViewById(R.id.spinE1TiempoOxigenoDom);

        spinE2SabeInicioSintoma = (Spinner) rootView.findViewById(R.id.spinE2SabeInicioSintoma);
        inputE2FIS = (EditText) rootView.findViewById(R.id.inputE2FIS);
        spinE2MesInicioSintoma = (Spinner) rootView.findViewById(R.id.spinE2MesInicioSintoma);
        inputE2AnioInicioSintoma = (EditText) rootView.findViewById(R.id.inputE2AnioInicioSintoma);
        //poner el año actual, no hay mas opciones
        inputE2AnioInicioSintoma.setText(String.valueOf(c.get(Calendar.YEAR)));
        e2AnioInicioSintoma = inputE2AnioInicioSintoma.getText().toString();
        inputE2AnioInicioSintoma.setEnabled(false);
        spinE2ConoceLugarExposicion = (Spinner) rootView.findViewById(R.id.spinE2ConoceLugarExposicion);
        inputE2LugarExposicion = (EditText) rootView.findViewById(R.id.inputE2LugarExposicion);
        spinE2BuscoAyuda = (Spinner) rootView.findViewById(R.id.spinE2BuscoAyuda);
        spinE2DondeBuscoAyuda = (Spinner) rootView.findViewById(R.id.spinE2DondeBuscoAyuda);
        inputE2NombreCentro = (EditText) rootView.findViewById(R.id.inputE2NombreCentro);
        inputE2NombreHospital = (EditText) rootView.findViewById(R.id.inputE2NombreHospital);
        spinE2RecibioSeguimiento = (Spinner) rootView.findViewById(R.id.spinE2RecibioSeguimiento);
        spinE2TipoSeguimiento = (Spinner) rootView.findViewById(R.id.spinE2TipoSeguimiento);
        spinE2TmpDespuesBuscoAyuda = (Spinner) rootView.findViewById(R.id.spinE2TmpDespuesBuscoAyuda);
        spinE2UnaNocheHospital = (Spinner) rootView.findViewById(R.id.spinE2UnaNocheHospital);
        inputE2QueHospital = (EditText) rootView.findViewById(R.id.inputE2QueHospital);
        inputE2CuantasNochesHosp = (EditText) rootView.findViewById(R.id.inputE2CuantasNochesHosp);
        inputE2FechaAdmisionHosp = (EditText) rootView.findViewById(R.id.inputE2FechaAdmisionHosp);
        inputE2FechaAltaHosp = (EditText) rootView.findViewById(R.id.inputE2FechaAltaHosp);
        spinE2UtilizoOxigeno = (Spinner) rootView.findViewById(R.id.spinE2UtilizoOxigeno);
        spinE2EstuvoUCI = (Spinner) rootView.findViewById(R.id.spinE2EstuvoUCI);
        spinE2FueIntubado = (Spinner) rootView.findViewById(R.id.spinE2FueIntubado);
        spinE2RecuperadoCovid19 = (Spinner) rootView.findViewById(R.id.spinE2RecuperadoCovid19);
        spinE2TieneFebricula = (Spinner) rootView.findViewById(R.id.spinE2TieneFebricula);
        spinE2TieneCansancio = (Spinner) rootView.findViewById(R.id.spinE2TieneCansancio);
        spinE2TieneDolorCabeza = (Spinner) rootView.findViewById(R.id.spinE2TieneDolorCabeza);
        spinE2TienePerdidaOlfato = (Spinner) rootView.findViewById(R.id.spinE2TienePerdidaOlfato);
        spinE2TienePerdidaGusto = (Spinner) rootView.findViewById(R.id.spinE2TienePerdidaGusto);
        spinE2TieneTos = (Spinner) rootView.findViewById(R.id.spinE2TieneTos);
        spinE2TieneDificultadRespirar = (Spinner) rootView.findViewById(R.id.spinE2TieneDificultadRespirar);
        spinE2TieneDolorPecho = (Spinner) rootView.findViewById(R.id.spinE2TieneDolorPecho);
        spinE2TienePalpitaciones = (Spinner) rootView.findViewById(R.id.spinE2TienePalpitaciones);
        spinE2TieneDolorArticulaciones = (Spinner) rootView.findViewById(R.id.spinE2TieneDolorArticulaciones);
        spinE2TieneParalisis = (Spinner) rootView.findViewById(R.id.spinE2TieneParalisis);
        spinE2TieneMareos = (Spinner) rootView.findViewById(R.id.spinE2TieneMareos);
        spinE2TienePensamientoNublado = (Spinner) rootView.findViewById(R.id.spinE2TienePensamientoNublado);
        spinE2TieneProblemasDormir = (Spinner) rootView.findViewById(R.id.spinE2TieneProblemasDormir);
        spinE2TieneDepresion = (Spinner) rootView.findViewById(R.id.spinE2TieneDepresion);
        spinE2TieneOtrosSintomas = (Spinner) rootView.findViewById(R.id.spinE2TieneOtrosSintomas);
        inputE2TieneCualesSintomas = (EditText) rootView.findViewById(R.id.inputE2CualesSintomas);
        spinE2SabeTiempoRecuperacion = (Spinner) rootView.findViewById(R.id.spinE2SabeTiempoRecuperacion);
        inputE2TiempoRecuperacion = (EditText) rootView.findViewById(R.id.inputE2TiempoRecuperacion);
        spinE2TiempoRecuperacionEn = (Spinner) rootView.findViewById(R.id.spinE2TiempoRecuperacionEn);
        spinE2SeveridadEnfermedad = (Spinner) rootView.findViewById(R.id.spinE2SeveridadEnfermedad);
        spinE2TomoMedicamento = (Spinner) rootView.findViewById(R.id.spinE2TomoMedicamento);
        spinE2QueMedicamento = (MultiSpinner) rootView.findViewById(R.id.spinE2QueMedicamento);
        spinE2OxigenoDomicilio = (Spinner) rootView.findViewById(R.id.spinE2OxigenoDomicilio);
        spinE2TiempoOxigenoDom = (Spinner) rootView.findViewById(R.id.spinE2TiempoOxigenoDom);

        spinE3SabeInicioSintoma = (Spinner) rootView.findViewById(R.id.spinE3SabeInicioSintoma);
        inputE3FIS = (EditText) rootView.findViewById(R.id.inputE3FIS);
        spinE3MesInicioSintoma = (Spinner) rootView.findViewById(R.id.spinE3MesInicioSintoma);
        inputE3AnioInicioSintoma = (EditText) rootView.findViewById(R.id.inputE3AnioInicioSintoma);
        //poner el año actual, no hay mas opciones
        inputE3AnioInicioSintoma.setText(String.valueOf(c.get(Calendar.YEAR)));
        e3AnioInicioSintoma = inputE3AnioInicioSintoma.getText().toString();
        inputE3AnioInicioSintoma.setEnabled(false);
        spinE3ConoceLugarExposicion = (Spinner) rootView.findViewById(R.id.spinE3ConoceLugarExposicion);
        inputE3LugarExposicion = (EditText) rootView.findViewById(R.id.inputE3LugarExposicion);
        spinE3BuscoAyuda = (Spinner) rootView.findViewById(R.id.spinE3BuscoAyuda);
        spinE3DondeBuscoAyuda = (Spinner) rootView.findViewById(R.id.spinE3DondeBuscoAyuda);
        inputE3NombreCentro = (EditText) rootView.findViewById(R.id.inputE3NombreCentro);
        inputE3NombreHospital = (EditText) rootView.findViewById(R.id.inputE3NombreHospital);
        spinE3RecibioSeguimiento = (Spinner) rootView.findViewById(R.id.spinE3RecibioSeguimiento);
        spinE3TipoSeguimiento = (Spinner) rootView.findViewById(R.id.spinE3TipoSeguimiento);
        spinE3TmpDespuesBuscoAyuda = (Spinner) rootView.findViewById(R.id.spinE3TmpDespuesBuscoAyuda);
        spinE3UnaNocheHospital = (Spinner) rootView.findViewById(R.id.spinE3UnaNocheHospital);
        inputE3QueHospital = (EditText) rootView.findViewById(R.id.inputE3QueHospital);
        inputE3CuantasNochesHosp = (EditText) rootView.findViewById(R.id.inputE3CuantasNochesHosp);
        inputE3FechaAdmisionHosp = (EditText) rootView.findViewById(R.id.inputE3FechaAdmisionHosp);
        inputE3FechaAltaHosp = (EditText) rootView.findViewById(R.id.inputE3FechaAltaHosp);
        spinE3UtilizoOxigeno = (Spinner) rootView.findViewById(R.id.spinE3UtilizoOxigeno);
        spinE3EstuvoUCI = (Spinner) rootView.findViewById(R.id.spinE3EstuvoUCI);
        spinE3FueIntubado = (Spinner) rootView.findViewById(R.id.spinE3FueIntubado);
        spinE3RecuperadoCovid19 = (Spinner) rootView.findViewById(R.id.spinE3RecuperadoCovid19);
        spinE3TieneFebricula = (Spinner) rootView.findViewById(R.id.spinE3TieneFebricula);
        spinE3TieneCansancio = (Spinner) rootView.findViewById(R.id.spinE3TieneCansancio);
        spinE3TieneDolorCabeza = (Spinner) rootView.findViewById(R.id.spinE3TieneDolorCabeza);
        spinE3TienePerdidaOlfato = (Spinner) rootView.findViewById(R.id.spinE3TienePerdidaOlfato);
        spinE3TienePerdidaGusto = (Spinner) rootView.findViewById(R.id.spinE3TienePerdidaGusto);
        spinE3TieneTos = (Spinner) rootView.findViewById(R.id.spinE3TieneTos);
        spinE3TieneDificultadRespirar = (Spinner) rootView.findViewById(R.id.spinE3TieneDificultadRespirar);
        spinE3TieneDolorPecho = (Spinner) rootView.findViewById(R.id.spinE3TieneDolorPecho);
        spinE3TienePalpitaciones = (Spinner) rootView.findViewById(R.id.spinE3TienePalpitaciones);
        spinE3TieneDolorArticulaciones = (Spinner) rootView.findViewById(R.id.spinE3TieneDolorArticulaciones);
        spinE3TieneParalisis = (Spinner) rootView.findViewById(R.id.spinE3TieneParalisis);
        spinE3TieneMareos = (Spinner) rootView.findViewById(R.id.spinE3TieneMareos);
        spinE3TienePensamientoNublado = (Spinner) rootView.findViewById(R.id.spinE3TienePensamientoNublado);
        spinE3TieneProblemasDormir = (Spinner) rootView.findViewById(R.id.spinE3TieneProblemasDormir);
        spinE3TieneDepresion = (Spinner) rootView.findViewById(R.id.spinE3TieneDepresion);
        spinE3TieneOtrosSintomas = (Spinner) rootView.findViewById(R.id.spinE3TieneOtrosSintomas);
        inputE3TieneCualesSintomas = (EditText) rootView.findViewById(R.id.inputE3CualesSintomas);
        spinE3SabeTiempoRecuperacion = (Spinner) rootView.findViewById(R.id.spinE3SabeTiempoRecuperacion);
        inputE3TiempoRecuperacion = (EditText) rootView.findViewById(R.id.inputE3TiempoRecuperacion);
        spinE3TiempoRecuperacionEn = (Spinner) rootView.findViewById(R.id.spinE3TiempoRecuperacionEn);
        spinE3SeveridadEnfermedad = (Spinner) rootView.findViewById(R.id.spinE3SeveridadEnfermedad);
        spinE3TomoMedicamento = (Spinner) rootView.findViewById(R.id.spinE3TomoMedicamento);
        spinE3QueMedicamento = (MultiSpinner) rootView.findViewById(R.id.spinE3QueMedicamento);
        spinE3OxigenoDomicilio = (Spinner) rootView.findViewById(R.id.spinE3OxigenoDomicilio);
        spinE3TiempoOxigenoDom = (Spinner) rootView.findViewById(R.id.spinE3TiempoOxigenoDom);

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
        imbE1FIS = (ImageButton) rootView.findViewById(R.id.imbE1FIS);
        imbE1FechaAdmisionHosp = (ImageButton) rootView.findViewById(R.id.imbE1FechaAdmisionHosp);
        imbE1FechaAltaHosp = (ImageButton) rootView.findViewById(R.id.imbE1FechaAltaHosp);
        inputE1OtroMedicamento = (EditText) rootView.findViewById(R.id.inputE1OtroMedicamento);
        spinE1SabeFechaAlta = (Spinner) rootView.findViewById(R.id.spinE1SabeFechaAlta);
        spinE1SabeFechaAdmision = (Spinner) rootView.findViewById(R.id.spinE1SabeFechaAdmision);
        spinE1SabeCuantasNoches = (Spinner) rootView.findViewById(R.id.spinE1SabeCuantasNoches);
        spinE1Embarazada = (Spinner) rootView.findViewById(R.id.spinE1Embarazada);
        spinE1RecuerdaSemanasEmb = (Spinner) rootView.findViewById(R.id.spinE1RecuerdaSemanasEmb);
        inputE1SemanasEmbarazo = (EditText) rootView.findViewById(R.id.inputE1SemanasEmbarazo);
        spinE1FinalEmbarazo = (Spinner) rootView.findViewById(R.id.spinE1FinalEmbarazo);
        inputE1OtroFinalEmbarazo = (EditText) rootView.findViewById(R.id.inputE1OtroFinalEmbarazo);
        spinE1DabaPecho = (Spinner) rootView.findViewById(R.id.spinE1DabaPecho);

        spinE2FumadoPrevioEnfermedad = (Spinner) rootView.findViewById(R.id.spinE2FumadoPrevioEnfermedad);
        spinE2FumaActualmente = (Spinner) rootView.findViewById(R.id.spinE2FumaActualmente);
        imbE2FIS = (ImageButton) rootView.findViewById(R.id.imbE2FIS);
        imbE2FechaAdmisionHosp = (ImageButton) rootView.findViewById(R.id.imbE2FechaAdmisionHosp);
        imbE2FechaAltaHosp = (ImageButton) rootView.findViewById(R.id.imbE2FechaAltaHosp);
        inputE2OtroMedicamento = (EditText) rootView.findViewById(R.id.inputE2OtroMedicamento);
        spinE2SabeFechaAlta = (Spinner) rootView.findViewById(R.id.spinE2SabeFechaAlta);
        spinE2SabeFechaAdmision = (Spinner) rootView.findViewById(R.id.spinE2SabeFechaAdmision);
        spinE2SabeCuantasNoches = (Spinner) rootView.findViewById(R.id.spinE2SabeCuantasNoches);
        spinE2Embarazada = (Spinner) rootView.findViewById(R.id.spinE2Embarazada);
        spinE2RecuerdaSemanasEmb = (Spinner) rootView.findViewById(R.id.spinE2RecuerdaSemanasEmb);
        inputE2SemanasEmbarazo = (EditText) rootView.findViewById(R.id.inputE2SemanasEmbarazo);
        spinE2FinalEmbarazo = (Spinner) rootView.findViewById(R.id.spinE2FinalEmbarazo);
        inputE2OtroFinalEmbarazo = (EditText) rootView.findViewById(R.id.inputE2OtroFinalEmbarazo);
        spinE2DabaPecho = (Spinner) rootView.findViewById(R.id.spinE2DabaPecho);

        spinE3FumadoPrevioEnfermedad = (Spinner) rootView.findViewById(R.id.spinE3FumadoPrevioEnfermedad);
        spinE3FumaActualmente = (Spinner) rootView.findViewById(R.id.spinE3FumaActualmente);
        imbE3FIS = (ImageButton) rootView.findViewById(R.id.imbE3FIS);
        imbE3FechaAdmisionHosp = (ImageButton) rootView.findViewById(R.id.imbE3FechaAdmisionHosp);
        imbE3FechaAltaHosp = (ImageButton) rootView.findViewById(R.id.imbE3FechaAltaHosp);
        inputE3OtroMedicamento = (EditText) rootView.findViewById(R.id.inputE3OtroMedicamento);
        spinE3SabeFechaAlta = (Spinner) rootView.findViewById(R.id.spinE3SabeFechaAlta);
        spinE3SabeFechaAdmision = (Spinner) rootView.findViewById(R.id.spinE3SabeFechaAdmision);
        spinE3SabeCuantasNoches = (Spinner) rootView.findViewById(R.id.spinE3SabeCuantasNoches);
        spinE3Embarazada = (Spinner) rootView.findViewById(R.id.spinE3Embarazada);
        spinE3RecuerdaSemanasEmb = (Spinner) rootView.findViewById(R.id.spinE3RecuerdaSemanasEmb);
        inputE3SemanasEmbarazo = (EditText) rootView.findViewById(R.id.inputE3SemanasEmbarazo);
        spinE3FinalEmbarazo = (Spinner) rootView.findViewById(R.id.spinE3FinalEmbarazo);
        inputE3OtroFinalEmbarazo = (EditText) rootView.findViewById(R.id.inputE3OtroFinalEmbarazo);
        spinE3DabaPecho = (Spinner) rootView.findViewById(R.id.spinE3DabaPecho);

        spinTrabajadorSalud = (Spinner) rootView.findViewById(R.id.spinTrabajadorSalud);
        spinEnfermoCovid19 = (Spinner) rootView.findViewById(R.id.spinEnfermoCovid19);
        spinCuantasVecesEnfermo = (Spinner) rootView.findViewById(R.id.spinCuantasVecesEnfermo);
        spinCuantasVecesEnfermo.setVisibility(View.GONE);
        spinSabeEvento1 = (Spinner) rootView.findViewById(R.id.spinSabeEvento1);
        spinSabeEvento1.setVisibility(View.GONE);
        inputEvento1 = (EditText) rootView.findViewById(R.id.inputEvento1);
        inputEvento1.setVisibility(View.GONE);
        imbEvento1 = (ImageButton) rootView.findViewById(R.id.imbEvento1);
        imbEvento2 = (ImageButton) rootView.findViewById(R.id.imbEvento2);
        imbEvento3 = (ImageButton) rootView.findViewById(R.id.imbEvento3);
        imbEvento1.setVisibility(View.GONE);
        imbEvento2.setVisibility(View.GONE);
        imbEvento3.setVisibility(View.GONE);
        inputAnioEvento1 = (EditText) rootView.findViewById(R.id.inputAnioEvento1);
        //poner el año actual, no hay mas opciones
        inputAnioEvento1.setText(String.valueOf(c.get(Calendar.YEAR)));
        anioEvento1 = inputAnioEvento1.getText().toString();
        inputAnioEvento1.setEnabled(false);
        inputAnioEvento1.setVisibility(View.GONE);
        spinMesEvento1 = (Spinner) rootView.findViewById(R.id.spinMesEvento1);
        spinMesEvento1.setVisibility(View.GONE);
        spinSabeEvento2 = (Spinner) rootView.findViewById(R.id.spinSabeEvento2);
        spinSabeEvento2.setVisibility(View.GONE);
        inputEvento2 = (EditText) rootView.findViewById(R.id.inputEvento2);
        inputEvento2.setVisibility(View.GONE);
        inputAnioEvento2 = (EditText) rootView.findViewById(R.id.inputAnioEvento2);
        //poner el año actual, no hay mas opciones
        inputAnioEvento2.setText(String.valueOf(c.get(Calendar.YEAR)));
        anioEvento2 = inputAnioEvento1.getText().toString();
        inputAnioEvento2.setEnabled(false);
        inputAnioEvento2.setVisibility(View.GONE);
        spinMesEvento2 = (Spinner) rootView.findViewById(R.id.spinMesEvento2);
        spinMesEvento2.setVisibility(View.GONE);
        spinSabeEvento3 = (Spinner) rootView.findViewById(R.id.spinSabeEvento3);
        spinSabeEvento3.setVisibility(View.GONE);
        inputEvento3 = (EditText) rootView.findViewById(R.id.inputEvento3);
        inputEvento3.setVisibility(View.GONE);
        inputAnioEvento3 = (EditText) rootView.findViewById(R.id.inputAnioEvento3);
        //poner el año actual, no hay mas opciones
        inputAnioEvento3.setText(String.valueOf(c.get(Calendar.YEAR)));
        anioEvento3 = inputAnioEvento1.getText().toString();
        inputAnioEvento3.setEnabled(false);
        inputAnioEvento3.setVisibility(View.GONE);
        spinMesEvento3 = (Spinner) rootView.findViewById(R.id.spinMesEvento3);
        spinMesEvento3.setVisibility(View.GONE);
        spinE1Febricula = (Spinner) rootView.findViewById(R.id.spinE1Febricula);
        spinE1Fiebre = (Spinner) rootView.findViewById(R.id.spinE1Fiebre);
        spinE1Escalofrio = (Spinner) rootView.findViewById(R.id.spinE1Escalofrio);
        spinE1TemblorEscalofrio = (Spinner) rootView.findViewById(R.id.spinE1TemblorEscalofrio);
        spinE1DolorMuscular = (Spinner) rootView.findViewById(R.id.spinE1DolorMuscular);
        spinE1DolorArticular = (Spinner) rootView.findViewById(R.id.spinE1DolorArticular);
        spinE1SecresionNasal = (Spinner) rootView.findViewById(R.id.spinE1SecresionNasal);
        spinE1DolorGarganta = (Spinner) rootView.findViewById(R.id.spinE1DolorGarganta);
        spinE1Tos = (Spinner) rootView.findViewById(R.id.spinE1Tos);
        spinE1DificultadResp = (Spinner) rootView.findViewById(R.id.spinE1DificultadResp);
        spinE1DolorPecho = (Spinner) rootView.findViewById(R.id.spinE1DolorPecho);
        spinE1NauseasVomito = (Spinner) rootView.findViewById(R.id.spinE1NauseasVomito);
        spinE1DolorCabeza = (Spinner) rootView.findViewById(R.id.spinE1DolorCabeza);
        spinE1DolorAbdominal = (Spinner) rootView.findViewById(R.id.spinE1DolorAbdominal);
        spinE1Diarrea = (Spinner) rootView.findViewById(R.id.spinE1Diarrea);
        spinE1DificultadDormir = (Spinner) rootView.findViewById(R.id.spinE1DificultadDormir);
        spinE1Debilidad = (Spinner) rootView.findViewById(R.id.spinE1Debilidad);
        spinE1PerdidaOlfatoGusto = (Spinner) rootView.findViewById(R.id.spinE1PerdidaOlfatoGusto);
        spinE1Mareo = (Spinner) rootView.findViewById(R.id.spinE1Mareo);
        spinE1Sarpullido = (Spinner) rootView.findViewById(R.id.spinE1Sarpullido);
        spinE1Desmayo = (Spinner) rootView.findViewById(R.id.spinE1Desmayo);
        spinE1QuedoCama = (Spinner) rootView.findViewById(R.id.spinE1QuedoCama);
        spinE2Febricula = (Spinner) rootView.findViewById(R.id.spinE2Febricula);
        spinE2Fiebre = (Spinner) rootView.findViewById(R.id.spinE2Fiebre);
        spinE2Escalofrio = (Spinner) rootView.findViewById(R.id.spinE2Escalofrio);
        spinE2TemblorEscalofrio = (Spinner) rootView.findViewById(R.id.spinE2TemblorEscalofrio);
        spinE2DolorMuscular = (Spinner) rootView.findViewById(R.id.spinE2DolorMuscular);
        spinE2DolorArticular = (Spinner) rootView.findViewById(R.id.spinE2DolorArticular);
        spinE2SecresionNasal = (Spinner) rootView.findViewById(R.id.spinE2SecresionNasal);
        spinE2DolorGarganta = (Spinner) rootView.findViewById(R.id.spinE2DolorGarganta);
        spinE2Tos = (Spinner) rootView.findViewById(R.id.spinE2Tos);
        spinE2DificultadResp = (Spinner) rootView.findViewById(R.id.spinE2DificultadResp);
        spinE2DolorPecho = (Spinner) rootView.findViewById(R.id.spinE2DolorPecho);
        spinE2NauseasVomito = (Spinner) rootView.findViewById(R.id.spinE2NauseasVomito);
        spinE2DolorCabeza = (Spinner) rootView.findViewById(R.id.spinE2DolorCabeza);
        spinE2DolorAbdominal = (Spinner) rootView.findViewById(R.id.spinE2DolorAbdominal);
        spinE2Diarrea = (Spinner) rootView.findViewById(R.id.spinE2Diarrea);
        spinE2DificultadDormir = (Spinner) rootView.findViewById(R.id.spinE2DificultadDormir);
        spinE2Debilidad = (Spinner) rootView.findViewById(R.id.spinE2Debilidad);
        spinE2PerdidaOlfatoGusto = (Spinner) rootView.findViewById(R.id.spinE2PerdidaOlfatoGusto);
        spinE2Mareo = (Spinner) rootView.findViewById(R.id.spinE2Mareo);
        spinE2Sarpullido = (Spinner) rootView.findViewById(R.id.spinE2Sarpullido);
        spinE2Desmayo = (Spinner) rootView.findViewById(R.id.spinE2Desmayo);
        spinE2QuedoCama = (Spinner) rootView.findViewById(R.id.spinE2QuedoCama);
        spinE3Febricula = (Spinner) rootView.findViewById(R.id.spinE3Febricula);
        spinE3Fiebre = (Spinner) rootView.findViewById(R.id.spinE3Fiebre);
        spinE3Escalofrio = (Spinner) rootView.findViewById(R.id.spinE3Escalofrio);
        spinE3TemblorEscalofrio = (Spinner) rootView.findViewById(R.id.spinE3TemblorEscalofrio);
        spinE3DolorMuscular = (Spinner) rootView.findViewById(R.id.spinE3DolorMuscular);
        spinE3DolorArticular = (Spinner) rootView.findViewById(R.id.spinE3DolorArticular);
        spinE3SecresionNasal = (Spinner) rootView.findViewById(R.id.spinE3SecresionNasal);
        spinE3DolorGarganta = (Spinner) rootView.findViewById(R.id.spinE3DolorGarganta);
        spinE3Tos = (Spinner) rootView.findViewById(R.id.spinE3Tos);
        spinE3DificultadResp = (Spinner) rootView.findViewById(R.id.spinE3DificultadResp);
        spinE3DolorPecho = (Spinner) rootView.findViewById(R.id.spinE3DolorPecho);
        spinE3NauseasVomito = (Spinner) rootView.findViewById(R.id.spinE3NauseasVomito);
        spinE3DolorCabeza = (Spinner) rootView.findViewById(R.id.spinE3DolorCabeza);
        spinE3DolorAbdominal = (Spinner) rootView.findViewById(R.id.spinE3DolorAbdominal);
        spinE3Diarrea = (Spinner) rootView.findViewById(R.id.spinE3Diarrea);
        spinE3DificultadDormir = (Spinner) rootView.findViewById(R.id.spinE3DificultadDormir);
        spinE3Debilidad = (Spinner) rootView.findViewById(R.id.spinE3Debilidad);
        spinE3PerdidaOlfatoGusto = (Spinner) rootView.findViewById(R.id.spinE3PerdidaOlfatoGusto);
        spinE3Mareo = (Spinner) rootView.findViewById(R.id.spinE3Mareo);
        spinE3Sarpullido = (Spinner) rootView.findViewById(R.id.spinE3Sarpullido);
        spinE3Desmayo = (Spinner) rootView.findViewById(R.id.spinE3Desmayo);
        spinE3QuedoCama = (Spinner) rootView.findViewById(R.id.spinE3QuedoCama);
        inputNombreVacuna = (EditText) rootView.findViewById(R.id.inputNombreVacuna);
        inputAnioVacuna = (EditText) rootView.findViewById(R.id.inputAnioVacuna);
        //poner el año actual, no hay mas opciones
        inputAnioVacuna.setText(String.valueOf(c.get(Calendar.YEAR)));
        anioVacuna = inputAnioVacuna.getText().toString();
        inputAnioVacuna.setEnabled(false);
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

        spinPresentoSintomasDosis1 = (Spinner) rootView.findViewById(R.id.spinPresentoSintomasDosis1);
        spinDolorSitioDosis1 = (Spinner) rootView.findViewById(R.id.spinDolorSitioDosis1);
        spinFiebreDosis1 = (Spinner) rootView.findViewById(R.id.spinFiebreDosis1);
        spinCansancioDosis1 = (Spinner) rootView.findViewById(R.id.spinCansancioDosis1);
        spinDolorCabezaDosis1 = (Spinner) rootView.findViewById(R.id.spinDolorCabezaDosis1);
        spinDiarreaDosis1 = (Spinner) rootView.findViewById(R.id.spinDiarreaDosis1);
        spinVomitoDosis1 = (Spinner) rootView.findViewById(R.id.spinVomitoDosis1);
        spinDolorMuscularDosis1 = (Spinner) rootView.findViewById(R.id.spinDolorMuscularDosis1);
        spinEscalofriosDosis1 = (Spinner) rootView.findViewById(R.id.spinEscalofriosDosis1);
        spinReaccionAlergicaDosis1 = (Spinner) rootView.findViewById(R.id.spinReaccionAlergicaDosis1);
        spinInfeccionSitioDosis1 = (Spinner) rootView.findViewById(R.id.spinInfeccionSitioDosis1);
        spinNauseasDosis1 = (Spinner) rootView.findViewById(R.id.spinNauseasDosis1);
        spinBultosDosis1 = (Spinner) rootView.findViewById(R.id.spinBultosDosis1);
        spinOtrosDosis1 = (Spinner) rootView.findViewById(R.id.spinOtrosDosis1);

        imbFechaDosis1 = (ImageButton) rootView.findViewById(R.id.imbFechaDosis1);
        imbFechaDosis2 = (ImageButton) rootView.findViewById(R.id.imbFechaDosis2);
        imbFechaDosis3 = (ImageButton) rootView.findViewById(R.id.imbFechaDosis3);
        //imbFechaEnfPostVac = (ImageButton) rootView.findViewById(R.id.imbFechaEnfPostVac);

        spinPresentoSintomasDosis2 = (Spinner) rootView.findViewById(R.id.spinPresentoSintomasDosis2);
        spinDolorSitioDosis2 = (Spinner) rootView.findViewById(R.id.spinDolorSitioDosis2);
        spinFiebreDosis2 = (Spinner) rootView.findViewById(R.id.spinFiebreDosis2);
        spinCansancioDosis2 = (Spinner) rootView.findViewById(R.id.spinCansancioDosis2);
        spinDolorCabezaDosis2 = (Spinner) rootView.findViewById(R.id.spinDolorCabezaDosis2);
        spinDiarreaDosis2 = (Spinner) rootView.findViewById(R.id.spinDiarreaDosis2);
        spinVomitoDosis2 = (Spinner) rootView.findViewById(R.id.spinVomitoDosis2);
        spinDolorMuscularDosis2 = (Spinner) rootView.findViewById(R.id.spinDolorMuscularDosis2);
        spinEscalofriosDosis2 = (Spinner) rootView.findViewById(R.id.spinEscalofriosDosis2);
        spinReaccionAlergicaDosis2 = (Spinner) rootView.findViewById(R.id.spinReaccionAlergicaDosis2);
        spinInfeccionSitioDosis2 = (Spinner) rootView.findViewById(R.id.spinInfeccionSitioDosis2);
        spinNauseasDosis2 = (Spinner) rootView.findViewById(R.id.spinNauseasDosis2);
        spinBultosDosis2 = (Spinner) rootView.findViewById(R.id.spinBultosDosis2);
        spinOtrosDosis2 = (Spinner) rootView.findViewById(R.id.spinOtrosDosis2);

        spinPresentoSintomasDosis3 = (Spinner) rootView.findViewById(R.id.spinPresentoSintomasDosis3);
        spinDolorSitioDosis3 = (Spinner) rootView.findViewById(R.id.spinDolorSitioDosis3);
        spinFiebreDosis3 = (Spinner) rootView.findViewById(R.id.spinFiebreDosis3);
        spinCansancioDosis3 = (Spinner) rootView.findViewById(R.id.spinCansancioDosis3);
        spinDolorCabezaDosis3 = (Spinner) rootView.findViewById(R.id.spinDolorCabezaDosis3);
        spinDiarreaDosis3 = (Spinner) rootView.findViewById(R.id.spinDiarreaDosis3);
        spinVomitoDosis3 = (Spinner) rootView.findViewById(R.id.spinVomitoDosis3);
        spinDolorMuscularDosis3 = (Spinner) rootView.findViewById(R.id.spinDolorMuscularDosis3);
        spinEscalofriosDosis3 = (Spinner) rootView.findViewById(R.id.spinEscalofriosDosis3);
        spinReaccionAlergicaDosis3 = (Spinner) rootView.findViewById(R.id.spinReaccionAlergicaDosis3);
        spinInfeccionSitioDosis3 = (Spinner) rootView.findViewById(R.id.spinInfeccionSitioDosis3);
        spinNauseasDosis3 = (Spinner) rootView.findViewById(R.id.spinNauseasDosis3);
        spinBultosDosis3 = (Spinner) rootView.findViewById(R.id.spinBultosDosis3);
        spinOtrosDosis3 = (Spinner) rootView.findViewById(R.id.spinOtrosDosis3);

        spinCovid19PosteriorVacuna = (Spinner) rootView.findViewById(R.id.spinCovid19PosteriorVacuna);
        spinFechaEventoEnfermoPostVac = (Spinner) rootView.findViewById(R.id.spinFechaEventoEnfermoPostVac);
        //spinMesEnfPostVac = (Spinner) rootView.findViewById(R.id.spinMesEnfPostVac);
        //spinSabeFechaEnfPostVac = (Spinner) rootView.findViewById(R.id.spinSabeFechaEnfPostVac);
        //inputFechaEnfPostVac = (EditText) rootView.findViewById(R.id.inputFechaEnfPostVac);
        //inputAnioEnfPostVac = (EditText) rootView.findViewById(R.id.inputAnioEnfPostVac);
        //poner el año actual, no hay mas opciones
        //inputAnioEnfPostVac.setText(String.valueOf(c.get(Calendar.YEAR)));
        //anioEnfPostVac = inputAnioEnfPostVac.getText().toString();
        //inputAnioEnfPostVac.setEnabled(false);

        spinE1Febricula.setVisibility(View.GONE);
        spinE1Fiebre.setVisibility(View.GONE);
        spinE1Escalofrio.setVisibility(View.GONE);
        spinE1TemblorEscalofrio.setVisibility(View.GONE);
        spinE1DolorMuscular.setVisibility(View.GONE);
        spinE1DolorArticular.setVisibility(View.GONE);
        spinE1SecresionNasal.setVisibility(View.GONE);
        spinE1DolorGarganta.setVisibility(View.GONE);
        spinE1Tos.setVisibility(View.GONE);
        spinE1DificultadResp.setVisibility(View.GONE);
        spinE1DolorPecho.setVisibility(View.GONE);
        spinE1NauseasVomito.setVisibility(View.GONE);
        spinE1DolorCabeza.setVisibility(View.GONE);
        spinE1DolorAbdominal.setVisibility(View.GONE);
        spinE1Diarrea.setVisibility(View.GONE);
        spinE1DificultadDormir.setVisibility(View.GONE);
        spinE1Debilidad.setVisibility(View.GONE);
        spinE1PerdidaOlfatoGusto.setVisibility(View.GONE);
        spinE1Mareo.setVisibility(View.GONE);
        spinE1Sarpullido.setVisibility(View.GONE);
        spinE1Desmayo.setVisibility(View.GONE);
        spinE1QuedoCama.setVisibility(View.GONE);
        spinE2Febricula.setVisibility(View.GONE);
        spinE2Fiebre.setVisibility(View.GONE);
        spinE2Escalofrio.setVisibility(View.GONE);
        spinE2TemblorEscalofrio.setVisibility(View.GONE);
        spinE2DolorMuscular.setVisibility(View.GONE);
        spinE2DolorArticular.setVisibility(View.GONE);
        spinE2SecresionNasal.setVisibility(View.GONE);
        spinE2DolorGarganta.setVisibility(View.GONE);
        spinE2Tos.setVisibility(View.GONE);
        spinE2DificultadResp.setVisibility(View.GONE);
        spinE2DolorPecho.setVisibility(View.GONE);
        spinE2NauseasVomito.setVisibility(View.GONE);
        spinE2DolorCabeza.setVisibility(View.GONE);
        spinE2DolorAbdominal.setVisibility(View.GONE);
        spinE2Diarrea.setVisibility(View.GONE);
        spinE2DificultadDormir.setVisibility(View.GONE);
        spinE2Debilidad.setVisibility(View.GONE);
        spinE2PerdidaOlfatoGusto.setVisibility(View.GONE);
        spinE2Mareo.setVisibility(View.GONE);
        spinE2Sarpullido.setVisibility(View.GONE);
        spinE2Desmayo.setVisibility(View.GONE);
        spinE2QuedoCama.setVisibility(View.GONE);
        spinE3Febricula.setVisibility(View.GONE);
        spinE3Fiebre.setVisibility(View.GONE);
        spinE3Escalofrio.setVisibility(View.GONE);
        spinE3TemblorEscalofrio.setVisibility(View.GONE);
        spinE3DolorMuscular.setVisibility(View.GONE);
        spinE3DolorArticular.setVisibility(View.GONE);
        spinE3SecresionNasal.setVisibility(View.GONE);
        spinE3DolorGarganta.setVisibility(View.GONE);
        spinE3Tos.setVisibility(View.GONE);
        spinE3DificultadResp.setVisibility(View.GONE);
        spinE3DolorPecho.setVisibility(View.GONE);
        spinE3NauseasVomito.setVisibility(View.GONE);
        spinE3DolorCabeza.setVisibility(View.GONE);
        spinE3DolorAbdominal.setVisibility(View.GONE);
        spinE3Diarrea.setVisibility(View.GONE);
        spinE3DificultadDormir.setVisibility(View.GONE);
        spinE3Debilidad.setVisibility(View.GONE);
        spinE3PerdidaOlfatoGusto.setVisibility(View.GONE);
        spinE3Mareo.setVisibility(View.GONE);
        spinE3Sarpullido.setVisibility(View.GONE);
        spinE3Desmayo.setVisibility(View.GONE);
        spinE3QuedoCama.setVisibility(View.GONE);

        inputE1FIS.setVisibility(View.GONE);
        spinE1MesInicioSintoma.setVisibility(View.GONE);
        inputE1AnioInicioSintoma.setVisibility(View.GONE);
        //siempre preguntar la 4
        spinE1ConoceLugarExposicion.setVisibility(View.GONE);
        inputE1LugarExposicion.setVisibility(View.GONE);
        spinE1BuscoAyuda.setVisibility(View.GONE);
        spinE1DondeBuscoAyuda.setVisibility(View.GONE);
        inputE1NombreCentro.setVisibility(View.GONE);
        inputE1NombreHospital.setVisibility(View.GONE);
        spinE1RecibioSeguimiento.setVisibility(View.GONE);
        spinE1TipoSeguimiento.setVisibility(View.GONE);
        spinE1TmpDespuesBuscoAyuda.setVisibility(View.GONE);
        spinE1UnaNocheHospital.setVisibility(View.GONE);
        inputE1QueHospital.setVisibility(View.GONE);
        inputE1CuantasNochesHosp.setVisibility(View.GONE);
        inputE1FechaAdmisionHosp.setVisibility(View.GONE);
        inputE1FechaAltaHosp.setVisibility(View.GONE);
        spinE1UtilizoOxigeno.setVisibility(View.GONE);
        spinE1EstuvoUCI.setVisibility(View.GONE);
        spinE1FueIntubado.setVisibility(View.GONE);
        spinE1RecuperadoCovid19.setVisibility(View.GONE);
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
        spinE1TomoMedicamento.setVisibility(View.GONE);
        spinE1QueMedicamento.setVisibility(View.GONE);
        spinE1OxigenoDomicilio.setVisibility(View.GONE);
        spinE1TiempoOxigenoDom.setVisibility(View.GONE);

        inputE2FIS.setVisibility(View.GONE);
        spinE2MesInicioSintoma.setVisibility(View.GONE);
        inputE2AnioInicioSintoma.setVisibility(View.GONE);
        //siempre preguntar la 4
        spinE2ConoceLugarExposicion.setVisibility(View.GONE);
        inputE2LugarExposicion.setVisibility(View.GONE);
        spinE2BuscoAyuda.setVisibility(View.GONE);
        spinE2DondeBuscoAyuda.setVisibility(View.GONE);
        inputE2NombreCentro.setVisibility(View.GONE);
        inputE2NombreHospital.setVisibility(View.GONE);
        spinE2RecibioSeguimiento.setVisibility(View.GONE);
        spinE2TipoSeguimiento.setVisibility(View.GONE);
        spinE2TmpDespuesBuscoAyuda.setVisibility(View.GONE);
        spinE2UnaNocheHospital.setVisibility(View.GONE);
        inputE2QueHospital.setVisibility(View.GONE);
        inputE2CuantasNochesHosp.setVisibility(View.GONE);
        inputE2FechaAdmisionHosp.setVisibility(View.GONE);
        inputE2FechaAltaHosp.setVisibility(View.GONE);
        spinE2UtilizoOxigeno.setVisibility(View.GONE);
        spinE2EstuvoUCI.setVisibility(View.GONE);
        spinE2FueIntubado.setVisibility(View.GONE);
        spinE2RecuperadoCovid19.setVisibility(View.GONE);
        spinE2TieneFebricula.setVisibility(View.GONE);
        spinE2TieneCansancio.setVisibility(View.GONE);
        spinE2TieneDolorCabeza.setVisibility(View.GONE);
        spinE2TienePerdidaOlfato.setVisibility(View.GONE);
        spinE2TienePerdidaGusto.setVisibility(View.GONE);
        spinE2TieneTos.setVisibility(View.GONE);
        spinE2TieneDificultadRespirar.setVisibility(View.GONE);
        spinE2TieneDolorPecho.setVisibility(View.GONE);
        spinE2TienePalpitaciones.setVisibility(View.GONE);
        spinE2TieneDolorArticulaciones.setVisibility(View.GONE);
        spinE2TieneParalisis.setVisibility(View.GONE);
        spinE2TieneMareos.setVisibility(View.GONE);
        spinE2TienePensamientoNublado.setVisibility(View.GONE);
        spinE2TieneProblemasDormir.setVisibility(View.GONE);
        spinE2TieneDepresion.setVisibility(View.GONE);
        spinE2TieneOtrosSintomas.setVisibility(View.GONE);
        inputE2TieneCualesSintomas.setVisibility(View.GONE);
        spinE2SabeTiempoRecuperacion.setVisibility(View.GONE);
        inputE2TiempoRecuperacion.setVisibility(View.GONE);
        spinE2TiempoRecuperacionEn.setVisibility(View.GONE);
        spinE2SeveridadEnfermedad.setVisibility(View.GONE);
        spinE2TomoMedicamento.setVisibility(View.GONE);
        spinE2QueMedicamento.setVisibility(View.GONE);
        spinE2OxigenoDomicilio.setVisibility(View.GONE);
        spinE2TiempoOxigenoDom.setVisibility(View.GONE);

        inputE3FIS.setVisibility(View.GONE);
        spinE3MesInicioSintoma.setVisibility(View.GONE);
        inputE3AnioInicioSintoma.setVisibility(View.GONE);
        //siempre preguntar la 4
        spinE3ConoceLugarExposicion.setVisibility(View.GONE);
        inputE3LugarExposicion.setVisibility(View.GONE);
        spinE3BuscoAyuda.setVisibility(View.GONE);
        spinE3DondeBuscoAyuda.setVisibility(View.GONE);
        inputE3NombreCentro.setVisibility(View.GONE);
        inputE3NombreHospital.setVisibility(View.GONE);
        spinE3RecibioSeguimiento.setVisibility(View.GONE);
        spinE3TipoSeguimiento.setVisibility(View.GONE);
        spinE3TmpDespuesBuscoAyuda.setVisibility(View.GONE);
        spinE3UnaNocheHospital.setVisibility(View.GONE);
        inputE3QueHospital.setVisibility(View.GONE);
        inputE3CuantasNochesHosp.setVisibility(View.GONE);
        inputE3FechaAdmisionHosp.setVisibility(View.GONE);
        inputE3FechaAltaHosp.setVisibility(View.GONE);
        spinE3UtilizoOxigeno.setVisibility(View.GONE);
        spinE3EstuvoUCI.setVisibility(View.GONE);
        spinE3FueIntubado.setVisibility(View.GONE);
        spinE3RecuperadoCovid19.setVisibility(View.GONE);
        spinE3TieneFebricula.setVisibility(View.GONE);
        spinE3TieneCansancio.setVisibility(View.GONE);
        spinE3TieneDolorCabeza.setVisibility(View.GONE);
        spinE3TienePerdidaOlfato.setVisibility(View.GONE);
        spinE3TienePerdidaGusto.setVisibility(View.GONE);
        spinE3TieneTos.setVisibility(View.GONE);
        spinE3TieneDificultadRespirar.setVisibility(View.GONE);
        spinE3TieneDolorPecho.setVisibility(View.GONE);
        spinE3TienePalpitaciones.setVisibility(View.GONE);
        spinE3TieneDolorArticulaciones.setVisibility(View.GONE);
        spinE3TieneParalisis.setVisibility(View.GONE);
        spinE3TieneMareos.setVisibility(View.GONE);
        spinE3TienePensamientoNublado.setVisibility(View.GONE);
        spinE3TieneProblemasDormir.setVisibility(View.GONE);
        spinE3TieneDepresion.setVisibility(View.GONE);
        spinE3TieneOtrosSintomas.setVisibility(View.GONE);
        inputE3TieneCualesSintomas.setVisibility(View.GONE);
        spinE3SabeTiempoRecuperacion.setVisibility(View.GONE);
        inputE3TiempoRecuperacion.setVisibility(View.GONE);
        spinE3TiempoRecuperacionEn.setVisibility(View.GONE);
        spinE3SeveridadEnfermedad.setVisibility(View.GONE);
        spinE3TomoMedicamento.setVisibility(View.GONE);
        spinE3QueMedicamento.setVisibility(View.GONE);
        spinE3OxigenoDomicilio.setVisibility(View.GONE);
        spinE3TiempoOxigenoDom.setVisibility(View.GONE);

        inputQueOtraCondicion.setVisibility(View.GONE);
        //Preguntar a mayores de 14 años
        if (participante.getEdadMeses() < 168) {
            textFumado.setVisibility(View.GONE);
            spinFumado.setVisibility(View.GONE);
        }
        spinFumadoCienCigarrillos.setVisibility(View.GONE);

        spinE1FumadoPrevioEnfermedad.setVisibility(View.GONE);
        spinE1FumaActualmente.setVisibility(View.GONE);
        spinE1SabeFechaAlta.setVisibility(View.GONE);
        spinE1SabeFechaAdmision.setVisibility(View.GONE);
        spinE1SabeCuantasNoches.setVisibility(View.GONE);
        inputE1OtroMedicamento.setVisibility(View.GONE);
        imbE1FIS.setVisibility(View.GONE);
        imbE1FechaAltaHosp.setVisibility(View.GONE);
        imbE1FechaAdmisionHosp.setVisibility(View.GONE);
        spinE1Embarazada.setVisibility(View.GONE);
        spinE1RecuerdaSemanasEmb.setVisibility(View.GONE);
        inputE1SemanasEmbarazo.setVisibility(View.GONE);
        spinE1FinalEmbarazo.setVisibility(View.GONE);
        inputE1OtroFinalEmbarazo.setVisibility(View.GONE);
        spinE1DabaPecho.setVisibility(View.GONE);

        spinE2FumadoPrevioEnfermedad.setVisibility(View.GONE);
        spinE2FumaActualmente.setVisibility(View.GONE);
        spinE2SabeFechaAlta.setVisibility(View.GONE);
        spinE2SabeFechaAdmision.setVisibility(View.GONE);
        spinE2SabeCuantasNoches.setVisibility(View.GONE);
        inputE2OtroMedicamento.setVisibility(View.GONE);
        imbE2FIS.setVisibility(View.GONE);
        imbE2FechaAltaHosp.setVisibility(View.GONE);
        imbE2FechaAdmisionHosp.setVisibility(View.GONE);
        spinE2Embarazada.setVisibility(View.GONE);
        spinE2RecuerdaSemanasEmb.setVisibility(View.GONE);
        inputE2SemanasEmbarazo.setVisibility(View.GONE);
        spinE2FinalEmbarazo.setVisibility(View.GONE);
        inputE2OtroFinalEmbarazo.setVisibility(View.GONE);
        spinE2DabaPecho.setVisibility(View.GONE);

        spinE3FumadoPrevioEnfermedad.setVisibility(View.GONE);
        spinE3FumaActualmente.setVisibility(View.GONE);
        spinE3SabeFechaAlta.setVisibility(View.GONE);
        spinE3SabeFechaAdmision.setVisibility(View.GONE);
        spinE3SabeCuantasNoches.setVisibility(View.GONE);
        inputE3OtroMedicamento.setVisibility(View.GONE);
        imbE3FIS.setVisibility(View.GONE);
        imbE3FechaAltaHosp.setVisibility(View.GONE);
        imbE3FechaAdmisionHosp.setVisibility(View.GONE);
        spinE3Embarazada.setVisibility(View.GONE);
        spinE3RecuerdaSemanasEmb.setVisibility(View.GONE);
        inputE3SemanasEmbarazo.setVisibility(View.GONE);
        spinE3FinalEmbarazo.setVisibility(View.GONE);
        inputE3OtroFinalEmbarazo.setVisibility(View.GONE);
        spinE3DabaPecho.setVisibility(View.GONE);

        if (participante.getEdadMeses() < 216) {//menores de 18
            textTrabajadorSalud.setVisibility(View.GONE);
            spinTrabajadorSalud.setVisibility(View.GONE);
        }
        inputNombreVacuna.setVisibility(View.GONE);
        inputAnioVacuna.setVisibility(View.GONE);
        inputOtraVacunaDosis1.setVisibility(View.GONE);
        inputLoteDosis1.setVisibility(View.GONE);
        inputOtraVacunaDosis2.setVisibility(View.GONE);
        inputLoteDosis2.setVisibility(View.GONE);
        inputOtraVacunaDosis3.setVisibility(View.GONE);
        inputLoteDosis3.setVisibility(View.GONE);
        inputFechaDosis1.setVisibility(View.GONE);
        inputFechaDosis2.setVisibility(View.GONE);
        inputFechaDosis3.setVisibility(View.GONE);
        inputDesOtrosDosis1.setVisibility(View.GONE);
        inputDesOtrosDosis2.setVisibility(View.GONE);
        inputDesOtrosDosis3.setVisibility(View.GONE);
        //inputFechaEnfPostVac.setVisibility(View.GONE);
        //inputAnioEnfPostVac.setVisibility(View.GONE);

        spinMuestraTarjetaVac.setVisibility(View.GONE);
        spinSabeNombreVacuna.setVisibility(View.GONE);
        spinMesVacuna.setVisibility(View.GONE);
        spinCuantasDosis.setVisibility(View.GONE);
        spinNombreDosis1.setVisibility(View.GONE);
        spinNombreDosis2.setVisibility(View.GONE);
        spinNombreDosis3.setVisibility(View.GONE);
        spinPresentoSintomasDosis1.setVisibility(View.GONE);
        spinDolorSitioDosis1.setVisibility(View.GONE);
        spinFiebreDosis1.setVisibility(View.GONE);
        spinCansancioDosis1.setVisibility(View.GONE);
        spinDolorCabezaDosis1.setVisibility(View.GONE);
        spinDiarreaDosis1.setVisibility(View.GONE);
        spinVomitoDosis1.setVisibility(View.GONE);
        spinDolorMuscularDosis1.setVisibility(View.GONE);
        spinEscalofriosDosis1.setVisibility(View.GONE);
        spinReaccionAlergicaDosis1.setVisibility(View.GONE);
        spinInfeccionSitioDosis1.setVisibility(View.GONE);
        spinNauseasDosis1.setVisibility(View.GONE);
        spinBultosDosis1.setVisibility(View.GONE);
        spinOtrosDosis1.setVisibility(View.GONE);
        imbFechaDosis1.setVisibility(View.GONE);
        imbFechaDosis2.setVisibility(View.GONE);
        imbFechaDosis3.setVisibility(View.GONE);
        //imbFechaEnfPostVac.setVisibility(View.GONE);
        spinCovid19PosteriorVacuna.setVisibility(View.GONE);
        spinFechaEventoEnfermoPostVac.setVisibility(View.GONE);
        //spinSabeFechaEnfPostVac.setVisibility(View.GONE);
        //spinMesEnfPostVac.setVisibility(View.GONE);

        layoutEvento1 = (LinearLayout) rootView.findViewById(R.id.layoutEvento1);
        layoutEvento2 = (LinearLayout) rootView.findViewById(R.id.layoutEvento2);
        layoutEvento3 = (LinearLayout) rootView.findViewById(R.id.layoutEvento3);

        layoutE1FIS = (LinearLayout) rootView.findViewById(R.id.layoutE1FIS);
        layoutE1FechaAdmision = (LinearLayout) rootView.findViewById(R.id.layoutE1FechaAdmision);
        layoutE1FechaAlta = (LinearLayout) rootView.findViewById(R.id.layoutE1FechaAlta);
        layoutE2FIS = (LinearLayout) rootView.findViewById(R.id.layoutE2FIS);
        layoutE2FechaAdmision = (LinearLayout) rootView.findViewById(R.id.layoutE2FechaAdmision);
        layoutE2FechaAlta = (LinearLayout) rootView.findViewById(R.id.layoutE2FechaAlta);
        layoutE3FIS = (LinearLayout) rootView.findViewById(R.id.layoutE3FIS);
        layoutE3FechaAdmision = (LinearLayout) rootView.findViewById(R.id.layoutE3FechaAdmision);
        layoutE3FechaAlta = (LinearLayout) rootView.findViewById(R.id.layoutE3FechaAlta);

        layoutFechaDosis1 = (LinearLayout) rootView.findViewById(R.id.layoutFechaDosis1);
        layoutFechaDosis2 = (LinearLayout) rootView.findViewById(R.id.layoutFechaDosis2);
        layoutFechaDosis3 = (LinearLayout) rootView.findViewById(R.id.layoutFechaDosis3);
        //layoutFechaEnfPostVac = (LinearLayout) rootView.findViewById(R.id.layoutFechaEnfPostVac);

        layoutEvento1.setVisibility(View.GONE);
        layoutEvento2.setVisibility(View.GONE);
        layoutEvento3.setVisibility(View.GONE);

        layoutE1FIS.setVisibility(View.GONE);
        layoutE1FechaAdmision.setVisibility(View.GONE);
        layoutE1FechaAlta.setVisibility(View.GONE);
        layoutE2FIS.setVisibility(View.GONE);
        layoutE2FechaAdmision.setVisibility(View.GONE);
        layoutE2FechaAlta.setVisibility(View.GONE);
        layoutE3FIS.setVisibility(View.GONE);
        layoutE3FechaAdmision.setVisibility(View.GONE);
        layoutE3FechaAlta.setVisibility(View.GONE);

        layoutFechaDosis1.setVisibility(View.GONE);
        layoutFechaDosis2.setVisibility(View.GONE);
        layoutFechaDosis3.setVisibility(View.GONE);
        //layoutFechaEnfPostVac.setVisibility(View.GONE);


        if (participante.getProcesos().getCuestCovid().equalsIgnoreCase("1a")){
            textEnfermoCovid19.setText(getString(R.string.feb20));
            periodoSintomas = Constants.PERIODO_CUEST_COVID19_1;
        }else if (participante.getProcesos().getCuestCovid().equalsIgnoreCase("2a")){
            textEnfermoCovid19.setText(getString(R.string.oct20));
            periodoSintomas = Constants.PERIODO_CUEST_COVID19_2;
        } else if (participante.getProcesos().getCuestCovid().equalsIgnoreCase("3a")){
            textEnfermoCovid19.setText(getString(R.string.feb21));
            periodoSintomas = Constants.PERIODO_CUEST_COVID19_3;
        } else {
            textEnfermoCovid19.setText(getString(R.string.nov21));
            periodoSintomas = Constants.PERIODO_CUEST_COVID19_4;
        }

        imbE1FIS.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_SINTOMA_E1);
            }
        });

        imbE1FechaAdmisionHosp.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_ADMISION_E1);
            }
        });

        imbE1FechaAltaHosp.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_ALTA_E1);
            }
        });

        imbEvento1.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_EVENTO1);
            }
        });

        imbEvento2.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_EVENTO2);
            }
        });

        imbEvento3.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_EVENTO3);
            }
        });

        imbFechaDosis1.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_VACUNA1);
            }
        });

        imbFechaDosis2.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_VACUNA2);
            }
        });

        imbFechaDosis3.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_VACUNA3);
            }
        });

        /*
        imbFechaEnfPostVac.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_ENFPOSTVAC);
            }
        });*/

        inputDesOtrosDosis1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                desOtrosDosis1 = inputDesOtrosDosis1.getText().toString();
            }
        });

        inputDesOtrosDosis2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                desOtrosDosis2 = inputDesOtrosDosis2.getText().toString();
            }
        });

        inputDesOtrosDosis3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                desOtrosDosis3 = inputDesOtrosDosis3.getText().toString();
            }
        });

        inputE1LugarExposicion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                    e1LugarExposicion = inputE1LugarExposicion.getText().toString();
            }
        });

        //al ser solo un año, se pone por defecto 2021
        /*inputAnioInicioSintoma.addTextChangedListener(new TextWatcher() {
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
        });*/

        inputE1NombreCentro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e1NombreCentroSalud = inputE1NombreCentro.getText().toString();
            }
        });

        inputE1NombreHospital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e1NombreHospital = inputE1NombreHospital.getText().toString();
            }
        });

        inputE1QueHospital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e1QueHospital = inputE1QueHospital.getText().toString();
            }
        });

        inputE1CuantasNochesHosp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (inputE1CuantasNochesHosp.getText()!= null && !inputE1CuantasNochesHosp.getText().toString().isEmpty())
                    e1CuantasNochesHosp = Integer.valueOf(inputE1CuantasNochesHosp.getText().toString());
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

        inputE1OtroMedicamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e1OtroMedicamento = inputE1OtroMedicamento.getText().toString();
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
                if (inputE1SemanasEmbarazo.getText()!= null && !inputE1SemanasEmbarazo.getText().toString().isEmpty())
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
//evento2
        imbE2FIS.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_SINTOMA_E2);
            }
        });

        imbE2FechaAdmisionHosp.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_ADMISION_E2);
            }
        });

        imbE2FechaAltaHosp.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_ALTA_E2);
            }
        });

        inputE2LugarExposicion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e2LugarExposicion = inputE2LugarExposicion.getText().toString();
            }
        });

        inputE2NombreCentro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e2NombreCentroSalud = inputE2NombreCentro.getText().toString();
            }
        });

        inputE2NombreHospital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e2NombreHospital = inputE2NombreHospital.getText().toString();
            }
        });

        inputE2QueHospital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e2QueHospital = inputE2QueHospital.getText().toString();
            }
        });

        inputE2CuantasNochesHosp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (inputE2CuantasNochesHosp.getText()!= null && !inputE2CuantasNochesHosp.getText().toString().isEmpty())
                    e2CuantasNochesHosp = Integer.valueOf(inputE2CuantasNochesHosp.getText().toString());
            }
        });

        inputE2TieneCualesSintomas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e2TieneCualesSintomas = inputE2TieneCualesSintomas.getText().toString();
            }
        });

        inputE2TiempoRecuperacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e2TiempoRecuperacion = inputE2TiempoRecuperacion.getText().toString();
            }
        });

        inputE2OtroMedicamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e2OtroMedicamento = inputE2OtroMedicamento.getText().toString();
            }
        });

        inputE2SemanasEmbarazo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (inputE2SemanasEmbarazo.getText()!= null && !inputE2SemanasEmbarazo.getText().toString().isEmpty())
                    e2SemanasEmbarazo = Integer.valueOf(inputE2SemanasEmbarazo.getText().toString());
            }
        });

        inputE2OtroFinalEmbarazo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e2OtroFinalEmbarazo = inputE2OtroFinalEmbarazo.getText().toString();
            }
        });

//evento3
        imbE3FIS.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_SINTOMA_E3);
            }
        });

        imbE3FechaAdmisionHosp.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_ADMISION_E3);
            }
        });

        imbE3FechaAltaHosp.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(FECHA_ALTA_E3);
            }
        });

        inputE3LugarExposicion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e3LugarExposicion = inputE3LugarExposicion.getText().toString();
            }
        });

        inputE3NombreCentro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e3NombreCentroSalud = inputE3NombreCentro.getText().toString();
            }
        });

        inputE3NombreHospital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e3NombreHospital = inputE3NombreHospital.getText().toString();
            }
        });

        inputE3QueHospital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e3QueHospital = inputE3QueHospital.getText().toString();
            }
        });

        inputE3CuantasNochesHosp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (inputE3CuantasNochesHosp.getText()!= null && !inputE3CuantasNochesHosp.getText().toString().isEmpty())
                    e3CuantasNochesHosp = Integer.valueOf(inputE3CuantasNochesHosp.getText().toString());
            }
        });

        inputE3TieneCualesSintomas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e3TieneCualesSintomas = inputE3TieneCualesSintomas.getText().toString();
            }
        });

        inputE3TiempoRecuperacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e3TiempoRecuperacion = inputE3TiempoRecuperacion.getText().toString();
            }
        });

        inputE3OtroMedicamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e3OtroMedicamento = inputE3OtroMedicamento.getText().toString();
            }
        });

        inputE3SemanasEmbarazo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (inputE3SemanasEmbarazo.getText()!= null && !inputE3SemanasEmbarazo.getText().toString().isEmpty())
                    e3SemanasEmbarazo = Integer.valueOf(inputE3SemanasEmbarazo.getText().toString());
            }
        });

        inputE3OtroFinalEmbarazo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                e3OtroFinalEmbarazo = inputE3OtroFinalEmbarazo.getText().toString();
            }
        });

        spinEnfermoCovid19.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(ENFERMOCOVID19_CONS, getString(R.string.enfermoCovid19), enfermoCovid19, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                enfermoCovid19 = mr.getCatKey();
                if (enfermoCovid19.equals(Constants.YESKEYSND)) {
                    spinEnfermoCovid19.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta1a(View.VISIBLE);
                } else {
                    spinEnfermoCovid19.setBackgroundColor(Color.WHITE);
                    /*MostrarOcultarPregunta1a(View.GONE);
                    MostrarOcultarPregunta5E1(View.GONE);
                    MostrarOcultarPregunta15E1(View.GONE);
                    MostrarOcultarPregunta18E1(View.GONE);
                    MostrarOcultarPregunta19E1(View.GONE);
                    MostrarOcultarPregunta20E1(View.GONE);
                    MostrarOcultarPregunta26E1(View.GONE);
                    MostrarOcultarPregunta27E1(View.GONE);*/
                    MostrarOcultarPregunta1a(View.GONE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinCuantasVecesEnfermo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(CUANTASVECESENFERMO_CONS, getString(R.string.cuantasVecesEnfermo), cuantasVecesEnfermo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                cuantasVecesEnfermo = mr.getCatKey();
                anioEvento1 = null;
                anioEvento2 = null;
                anioEvento3 = null;

                if (cuantasVecesEnfermo.equals("1")) {
                    MostrarOcultarPreguntaE1(View.VISIBLE);
                    MostrarOcultarPreguntaE2(View.GONE);
                    MostrarOcultarPreguntaE3(View.GONE);
                }else if (cuantasVecesEnfermo.equals("2")) {
                    MostrarOcultarPreguntaE1(View.VISIBLE);
                    MostrarOcultarPreguntaE2(View.VISIBLE);
                    MostrarOcultarPreguntaE3(View.GONE);
                }else if (cuantasVecesEnfermo.equals("3")) {
                    MostrarOcultarPreguntaE1(View.VISIBLE);
                    MostrarOcultarPreguntaE2(View.VISIBLE);
                    MostrarOcultarPreguntaE3(View.VISIBLE);
                } else {
                    MostrarOcultarPreguntaE1(View.GONE);
                    MostrarOcultarPreguntaE2(View.GONE);
                    MostrarOcultarPreguntaE3(View.GONE);
                    fechaEvento1 = null;
                    fechaEvento2 = null;
                    fechaEvento3 = null;
                }
                MostrarOcultarPregunta3E1();
                MostrarOcultarPregunta3E2();
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinSabeEvento1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SABEEVENTO1_CONS, getString(R.string.sabeEvento1), sabeEvento1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                sabeEvento1 = mr.getCatKey();
                if(sabeEvento1.equals(Constants.YESKEYSND)){
                    spinSabeEvento1.setBackgroundColor(Color.RED);
                    MostrarOcultarE1_FechaCompleta(View.VISIBLE);
                    MostrarOcultarE1_MesAnio(View.GONE);
                    anioEvento1 = null;
                }
                else if(sabeEvento1.equals(Constants.NOKEYSND)){
                    spinSabeEvento1.setBackgroundColor(Color.WHITE);
                    MostrarOcultarE1_FechaCompleta(View.GONE);
                    MostrarOcultarE1_MesAnio(View.VISIBLE);
                    anioEvento1 = inputAnioEvento1.getText().toString();
                    fechaEvento1 = null;
                } else {
                    spinSabeEvento1.setBackgroundColor(Color.WHITE);
                    MostrarOcultarE1_FechaCompleta(View.GONE);
                    MostrarOcultarE1_MesAnio(View.GONE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinMesEvento1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(MESEVENTO1_CONS, getString(R.string.mesEvento), mesEvento1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                mesEvento1 = mr.getCatKey();
                actualizarListaEventos();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinSabeEvento2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SABEEVENTO2_CONS, getString(R.string.sabeEvento2), sabeEvento2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                sabeEvento2 = mr.getCatKey();
                if(sabeEvento2.equals(Constants.YESKEYSND)){
                    spinSabeEvento2.setBackgroundColor(Color.RED);
                    MostrarOcultarE2_FechaCompleta(View.VISIBLE);
                    MostrarOcultarE2_MesAnio(View.GONE);
                    anioEvento2 = null;
                }
                else if(sabeEvento2.equals(Constants.NOKEYSND)){
                    spinSabeEvento2.setBackgroundColor(Color.WHITE);
                    MostrarOcultarE2_FechaCompleta(View.GONE);
                    MostrarOcultarE2_MesAnio(View.VISIBLE);
                    anioEvento2 = inputAnioEvento2.getText().toString();
                    fechaEvento2 = null;
                } else {
                    spinSabeEvento2.setBackgroundColor(Color.WHITE);
                    MostrarOcultarE2_FechaCompleta(View.GONE);
                    MostrarOcultarE2_MesAnio(View.GONE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinMesEvento2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(MESEVENTO2_CONS, getString(R.string.mesEvento), mesEvento2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                mesEvento2 = mr.getCatKey();
                actualizarListaEventos();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinSabeEvento3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SABEEVENTO3_CONS, getString(R.string.sabeEvento3), sabeEvento3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                sabeEvento3 = mr.getCatKey();
                if(sabeEvento3.equals(Constants.YESKEYSND)){
                    spinSabeEvento3.setBackgroundColor(Color.RED);
                    MostrarOcultarE3_FechaCompleta(View.VISIBLE);
                    MostrarOcultarE3_MesAnio(View.GONE);
                    anioEvento3 = null;
                }
                else if(sabeEvento3.equals(Constants.NOKEYSND)){
                    spinSabeEvento3.setBackgroundColor(Color.WHITE);
                    MostrarOcultarE3_FechaCompleta(View.GONE);
                    MostrarOcultarE3_MesAnio(View.VISIBLE);
                    anioEvento3 = inputAnioEvento3.getText().toString();
                    fechaEvento3 = null;
                } else {
                    spinSabeEvento3.setBackgroundColor(Color.WHITE);
                    MostrarOcultarE3_FechaCompleta(View.GONE);
                    MostrarOcultarE3_MesAnio(View.GONE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinMesEvento3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(MESEVENTO3_CONS, getString(R.string.mesEvento), mesEvento3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                mesEvento3 = mr.getCatKey();
                actualizarListaEventos();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*SINTOMAS EVENTO1*/
        spinE1Febricula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1FEBRICULA_CONS, getString(R.string.feb20Febricula), e1Febricula, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1Febricula = mr.getCatKey();
                if (e1Febricula.equals(Constants.YESKEYSND)) {
                    spinE1Febricula.setBackgroundColor(Color.RED);
                    spinE1Fiebre.setVisibility(View.GONE);
                    textE1Fiebre.setVisibility(View.GONE);
                    spinE1Fiebre.setSelection(0, false);
                } else if (e1Febricula.equals(Constants.NOKEYSND)) {
                    spinE1Febricula.setBackgroundColor(Color.WHITE);
                    spinE1Fiebre.setVisibility(View.VISIBLE);
                    textE1Fiebre.setVisibility(View.VISIBLE);
                } else {
                    spinE1Fiebre.setVisibility(View.GONE);
                    textE1Fiebre.setVisibility(View.GONE);
                    spinE1Fiebre.setSelection(0, false);
                }
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1Fiebre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1FIEBRE_CONS, getString(R.string.feb20Fiebre), e1Fiebre, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1Fiebre = mr.getCatKey();
                spinE1Fiebre.setBackgroundColor(e1Fiebre.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1Escalofrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1ESCALOFRIO_CONS, getString(R.string.feb20Escalofrio), e1Escalofrio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1Escalofrio = mr.getCatKey();
                spinE1Escalofrio.setBackgroundColor(e1Escalofrio.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TemblorEscalofrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TEMBLORESCALOFRIO_CONS, getString(R.string.feb20TemblorEscalofrio), e1TemblorEscalofrio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TemblorEscalofrio = mr.getCatKey();
                spinE1TemblorEscalofrio.setBackgroundColor(e1TemblorEscalofrio.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1DolorMuscular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DOLORMUSCULAR_CONS, getString(R.string.feb20DolorMuscular), e1DolorMuscular, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1DolorMuscular = mr.getCatKey();
                spinE1DolorMuscular.setBackgroundColor(e1DolorMuscular.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1DolorArticular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DOLORARTICULAR_CONS, getString(R.string.feb20DolorArticular), e1DolorArticular, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1DolorArticular = mr.getCatKey();
                spinE1DolorArticular.setBackgroundColor(e1DolorArticular.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1SecresionNasal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1SECRESIONNASAL_CONS, getString(R.string.feb20SecresionNasal), e1SecresionNasal, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1SecresionNasal = mr.getCatKey();
                spinE1SecresionNasal.setBackgroundColor(e1SecresionNasal.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1DolorGarganta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DOLORGARGANTA_CONS, getString(R.string.feb20DolorGarganta), e1DolorGarganta, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1DolorGarganta = mr.getCatKey();
                spinE1DolorGarganta.setBackgroundColor(e1DolorGarganta.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1Tos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TOS_CONS, getString(R.string.feb20Tos), e1Tos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1Tos = mr.getCatKey();
                spinE1Tos.setBackgroundColor(e1Tos.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1DificultadResp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DIFICULTADRESP_CONS, getString(R.string.feb20DificultadResp), e1DificultadResp, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1DificultadResp = mr.getCatKey();
                spinE1DificultadResp.setBackgroundColor(e1DificultadResp.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1DolorPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DOLORPECHO_CONS, getString(R.string.feb20DolorPecho), e1DolorPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1DolorPecho = mr.getCatKey();
                spinE1DolorPecho.setBackgroundColor(e1DolorPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1NauseasVomito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1NAUSEASVOMITO_CONS, getString(R.string.feb20NauseasVomito), e1NauseasVomito, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1NauseasVomito = mr.getCatKey();
                spinE1NauseasVomito.setBackgroundColor(e1NauseasVomito.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1DolorCabeza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DOLORCABEZA_CONS, getString(R.string.feb20DolorCabeza), e1DolorCabeza, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1DolorCabeza = mr.getCatKey();
                spinE1DolorCabeza.setBackgroundColor(e1DolorCabeza.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1DolorAbdominal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DOLORABDOMINAL_CONS, getString(R.string.feb20DolorAbdominal), e1DolorAbdominal, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1DolorAbdominal = mr.getCatKey();
                spinE1DolorAbdominal.setBackgroundColor(e1DolorAbdominal.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1Diarrea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DIARREA_CONS, getString(R.string.feb20Diarrea), e1Diarrea, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1Diarrea = mr.getCatKey();
                spinE1Diarrea.setBackgroundColor(e1Diarrea.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1DificultadDormir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DIFICULTADDORMIR_CONS, getString(R.string.feb20DificultadDormir), e1DificultadDormir, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1DificultadDormir = mr.getCatKey();
                spinE1DificultadDormir.setBackgroundColor(e1DificultadDormir.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1Debilidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DEBILIDAD_CONS, getString(R.string.feb20Debilidad), e1Debilidad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1Debilidad = mr.getCatKey();
                spinE1Debilidad.setBackgroundColor(e1Debilidad.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1PerdidaOlfatoGusto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1PERDIDAOLFATOGUSTO_CONS, getString(R.string.feb20PerdidaOlfatoGusto), e1PerdidaOlfatoGusto, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1PerdidaOlfatoGusto = mr.getCatKey();
                spinE1PerdidaOlfatoGusto.setBackgroundColor(e1PerdidaOlfatoGusto.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1Mareo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1MAREO_CONS, getString(R.string.feb20Mareo), e1Mareo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1Mareo = mr.getCatKey();
                spinE1Mareo.setBackgroundColor(e1Mareo.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1Sarpullido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1SARPULLIDO_CONS, getString(R.string.feb20Sarpullido), e1Sarpullido, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1Sarpullido = mr.getCatKey();
                spinE1Sarpullido.setBackgroundColor(e1Sarpullido.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1Desmayo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DESMAYO_CONS, getString(R.string.feb20Desmayo), e1Desmayo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1Desmayo = mr.getCatKey();
                spinE1Desmayo.setBackgroundColor(e1Desmayo.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1QuedoCama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1QUEDOCAMA_CONS, getString(R.string.feb20QuedoCama), e1QuedoCama, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1QuedoCama = mr.getCatKey();
                spinE1QuedoCama.setBackgroundColor(e1QuedoCama.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E1();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*FIN SINTOMAS EVENTO 1*/

        /*SINTOMAS EVENTO2*/
        spinE2Febricula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2FEBRICULA_CONS, getString(R.string.feb20Febricula), e2Febricula, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2Febricula = mr.getCatKey();
                if (e2Febricula.equals(Constants.YESKEYSND)) {
                    spinE2Febricula.setBackgroundColor(Color.RED);
                    spinE2Fiebre.setVisibility(View.GONE);
                    textE2Fiebre.setVisibility(View.GONE);
                    spinE2Fiebre.setSelection(0, false);
                } else if (e2Febricula.equals(Constants.NOKEYSND)) {
                    spinE2Febricula.setBackgroundColor(Color.WHITE);
                    spinE2Fiebre.setVisibility(View.VISIBLE);
                    textE2Fiebre.setVisibility(View.VISIBLE);
                } else {
                    spinE2Fiebre.setVisibility(View.GONE);
                    textE2Fiebre.setVisibility(View.GONE);
                    spinE2Fiebre.setSelection(0, false);
                }


                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2Fiebre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2FIEBRE_CONS, getString(R.string.feb20Fiebre), e2Fiebre, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2Fiebre = mr.getCatKey();
                spinE2Fiebre.setBackgroundColor(e2Fiebre.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2Escalofrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2ESCALOFRIO_CONS, getString(R.string.feb20Escalofrio), e2Escalofrio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2Escalofrio = mr.getCatKey();
                spinE2Escalofrio.setBackgroundColor(e2Escalofrio.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TemblorEscalofrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TEMBLORESCALOFRIO_CONS, getString(R.string.feb20TemblorEscalofrio), e2TemblorEscalofrio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TemblorEscalofrio = mr.getCatKey();
                spinE2TemblorEscalofrio.setBackgroundColor(e2TemblorEscalofrio.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2DolorMuscular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DOLORMUSCULAR_CONS, getString(R.string.feb20DolorMuscular), e2DolorMuscular, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2DolorMuscular = mr.getCatKey();
                spinE2DolorMuscular.setBackgroundColor(e2DolorMuscular.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2DolorArticular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DOLORARTICULAR_CONS, getString(R.string.feb20DolorArticular), e2DolorArticular, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2DolorArticular = mr.getCatKey();
                spinE2DolorArticular.setBackgroundColor(e2DolorArticular.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2SecresionNasal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2SECRESIONNASAL_CONS, getString(R.string.feb20SecresionNasal), e2SecresionNasal, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2SecresionNasal = mr.getCatKey();
                spinE2SecresionNasal.setBackgroundColor(e2SecresionNasal.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2DolorGarganta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DOLORGARGANTA_CONS, getString(R.string.feb20DolorGarganta), e2DolorGarganta, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2DolorGarganta = mr.getCatKey();
                spinE2DolorGarganta.setBackgroundColor(e2DolorGarganta.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2Tos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TOS_CONS, getString(R.string.feb20Tos), e2Tos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2Tos = mr.getCatKey();
                spinE2Tos.setBackgroundColor(e2Tos.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2DificultadResp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DIFICULTADRESP_CONS, getString(R.string.feb20DificultadResp), e2DificultadResp, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2DificultadResp = mr.getCatKey();
                spinE2DificultadResp.setBackgroundColor(e2DificultadResp.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2DolorPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DOLORPECHO_CONS, getString(R.string.feb20DolorPecho), e2DolorPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2DolorPecho = mr.getCatKey();
                spinE2DolorPecho.setBackgroundColor(e2DolorPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2NauseasVomito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2NAUSEASVOMITO_CONS, getString(R.string.feb20NauseasVomito), e2NauseasVomito, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2NauseasVomito = mr.getCatKey();
                spinE2NauseasVomito.setBackgroundColor(e2NauseasVomito.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2DolorCabeza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DOLORCABEZA_CONS, getString(R.string.feb20DolorCabeza), e2DolorCabeza, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2DolorCabeza = mr.getCatKey();
                spinE2DolorCabeza.setBackgroundColor(e2DolorCabeza.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2DolorAbdominal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DOLORABDOMINAL_CONS, getString(R.string.feb20DolorAbdominal), e2DolorAbdominal, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2DolorAbdominal = mr.getCatKey();
                spinE2DolorAbdominal.setBackgroundColor(e2DolorAbdominal.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2Diarrea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DIARREA_CONS, getString(R.string.feb20Diarrea), e2Diarrea, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2Diarrea = mr.getCatKey();
                spinE2Diarrea.setBackgroundColor(e2Diarrea.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2DificultadDormir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DIFICULTADDORMIR_CONS, getString(R.string.feb20DificultadDormir), e2DificultadDormir, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2DificultadDormir = mr.getCatKey();
                spinE2DificultadDormir.setBackgroundColor(e2DificultadDormir.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2Debilidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DEBILIDAD_CONS, getString(R.string.feb20Debilidad), e2Debilidad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2Debilidad = mr.getCatKey();
                spinE2Debilidad.setBackgroundColor(e2Debilidad.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2PerdidaOlfatoGusto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2PERDIDAOLFATOGUSTO_CONS, getString(R.string.feb20PerdidaOlfatoGusto), e2PerdidaOlfatoGusto, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2PerdidaOlfatoGusto = mr.getCatKey();
                spinE2PerdidaOlfatoGusto.setBackgroundColor(e2PerdidaOlfatoGusto.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2Mareo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2MAREO_CONS, getString(R.string.feb20Mareo), e2Mareo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2Mareo = mr.getCatKey();
                spinE2Mareo.setBackgroundColor(e2Mareo.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2Sarpullido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2SARPULLIDO_CONS, getString(R.string.feb20Sarpullido), e2Sarpullido, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2Sarpullido = mr.getCatKey();
                spinE2Sarpullido.setBackgroundColor(e2Sarpullido.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2Desmayo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DESMAYO_CONS, getString(R.string.feb20Desmayo), e2Desmayo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2Desmayo = mr.getCatKey();
                spinE2Desmayo.setBackgroundColor(e2Desmayo.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2QuedoCama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2QUEDOCAMA_CONS, getString(R.string.feb20QuedoCama), e2QuedoCama, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2QuedoCama = mr.getCatKey();
                spinE2QuedoCama.setBackgroundColor(e2QuedoCama.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*FIN SINTOMAS EVENTO 2*/

        /*SINTOMAS EVENTO3*/
        spinE3Febricula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3FEBRICULA_CONS, getString(R.string.feb20Febricula), e3Febricula, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3Febricula = mr.getCatKey();
                if (e3Febricula.equals(Constants.YESKEYSND)) {
                    spinE3Febricula.setBackgroundColor(Color.RED);
                    spinE3Fiebre.setVisibility(View.GONE);
                    textE3Fiebre.setVisibility(View.GONE);
                    spinE3Fiebre.setSelection(0, false);
                } else if (e3Febricula.equals(Constants.NOKEYSND)) {
                    spinE3Febricula.setBackgroundColor(Color.WHITE);
                    spinE3Fiebre.setVisibility(View.VISIBLE);
                    textE3Fiebre.setVisibility(View.VISIBLE);
                } else {
                    spinE3Fiebre.setVisibility(View.GONE);
                    textE3Fiebre.setVisibility(View.GONE);
                    spinE3Fiebre.setSelection(0, false);
                }
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3Fiebre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3FIEBRE_CONS, getString(R.string.feb20Fiebre), e3Fiebre, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3Fiebre = mr.getCatKey();
                spinE3Fiebre.setBackgroundColor(e3Fiebre.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3Escalofrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3ESCALOFRIO_CONS, getString(R.string.feb20Escalofrio), e3Escalofrio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3Escalofrio = mr.getCatKey();
                spinE3Escalofrio.setBackgroundColor(e3Escalofrio.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TemblorEscalofrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TEMBLORESCALOFRIO_CONS, getString(R.string.feb20TemblorEscalofrio), e3TemblorEscalofrio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TemblorEscalofrio = mr.getCatKey();
                spinE3TemblorEscalofrio.setBackgroundColor(e3TemblorEscalofrio.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3DolorMuscular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DOLORMUSCULAR_CONS, getString(R.string.feb20DolorMuscular), e3DolorMuscular, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3DolorMuscular = mr.getCatKey();
                spinE3DolorMuscular.setBackgroundColor(e3DolorMuscular.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3DolorArticular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DOLORARTICULAR_CONS, getString(R.string.feb20DolorArticular), e3DolorArticular, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3DolorArticular = mr.getCatKey();
                spinE3DolorArticular.setBackgroundColor(e3DolorArticular.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3SecresionNasal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3SECRESIONNASAL_CONS, getString(R.string.feb20SecresionNasal), e3SecresionNasal, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3SecresionNasal = mr.getCatKey();
                spinE3SecresionNasal.setBackgroundColor(e3SecresionNasal.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3DolorGarganta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DOLORGARGANTA_CONS, getString(R.string.feb20DolorGarganta), e3DolorGarganta, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3DolorGarganta = mr.getCatKey();
                spinE3DolorGarganta.setBackgroundColor(e3DolorGarganta.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3Tos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TOS_CONS, getString(R.string.feb20Tos), e3Tos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3Tos = mr.getCatKey();
                spinE3Tos.setBackgroundColor(e3Tos.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3DificultadResp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DIFICULTADRESP_CONS, getString(R.string.feb20DificultadResp), e3DificultadResp, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3DificultadResp = mr.getCatKey();
                spinE3DificultadResp.setBackgroundColor(e3DificultadResp.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3DolorPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DOLORPECHO_CONS, getString(R.string.feb20DolorPecho), e3DolorPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3DolorPecho = mr.getCatKey();
                spinE3DolorPecho.setBackgroundColor(e3DolorPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3NauseasVomito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3NAUSEASVOMITO_CONS, getString(R.string.feb20NauseasVomito), e3NauseasVomito, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3NauseasVomito = mr.getCatKey();
                spinE3NauseasVomito.setBackgroundColor(e3NauseasVomito.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3DolorCabeza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DOLORCABEZA_CONS, getString(R.string.feb20DolorCabeza), e3DolorCabeza, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3DolorCabeza = mr.getCatKey();
                spinE3DolorCabeza.setBackgroundColor(e3DolorCabeza.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3DolorAbdominal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DOLORABDOMINAL_CONS, getString(R.string.feb20DolorAbdominal), e3DolorAbdominal, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3DolorAbdominal = mr.getCatKey();
                spinE3DolorAbdominal.setBackgroundColor(e3DolorAbdominal.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3Diarrea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DIARREA_CONS, getString(R.string.feb20Diarrea), e3Diarrea, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3Diarrea = mr.getCatKey();
                spinE3Diarrea.setBackgroundColor(e3Diarrea.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3DificultadDormir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DIFICULTADDORMIR_CONS, getString(R.string.feb20DificultadDormir), e3DificultadDormir, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3DificultadDormir = mr.getCatKey();
                spinE3DificultadDormir.setBackgroundColor(e3DificultadDormir.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3Debilidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DEBILIDAD_CONS, getString(R.string.feb20Debilidad), e3Debilidad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3Debilidad = mr.getCatKey();
                spinE3Debilidad.setBackgroundColor(e3Debilidad.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3PerdidaOlfatoGusto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3PERDIDAOLFATOGUSTO_CONS, getString(R.string.feb20PerdidaOlfatoGusto), e3PerdidaOlfatoGusto, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3PerdidaOlfatoGusto = mr.getCatKey();
                spinE3PerdidaOlfatoGusto.setBackgroundColor(e3PerdidaOlfatoGusto.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3Mareo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3MAREO_CONS, getString(R.string.feb20Mareo), e3Mareo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3Mareo = mr.getCatKey();
                spinE3Mareo.setBackgroundColor(e3Mareo.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3Sarpullido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3SARPULLIDO_CONS, getString(R.string.feb20Sarpullido), e3Sarpullido, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3Sarpullido = mr.getCatKey();
                spinE3Sarpullido.setBackgroundColor(e3Sarpullido.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3Desmayo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DESMAYO_CONS, getString(R.string.feb20Desmayo), e3Desmayo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3Desmayo = mr.getCatKey();
                spinE3Desmayo.setBackgroundColor(e3Desmayo.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3QuedoCama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3QUEDOCAMA_CONS, getString(R.string.feb20QuedoCama), e3QuedoCama, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3QuedoCama = mr.getCatKey();
                spinE3QuedoCama.setBackgroundColor(e3QuedoCama.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
                MostrarOcultarPregunta3E3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*FIN SINTOMAS EVENTO 3*/
        //evento1
        spinE1SabeInicioSintoma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1SABEFIS_CONS, getString(R.string.sabeInicioSintoma), e1SabeFIS, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1SabeFIS = mr.getCatKey();
                if(e1SabeFIS.equals(Constants.YESKEYSND)){
                    spinE1SabeInicioSintoma.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta3_FISE1(View.VISIBLE);
                    MostrarOcultarPregunta3_MesAnioE1(View.GONE);
                    e1AnioInicioSintoma = null;
                    e1MesInicioSintoma = null;
                }
                else if(e1SabeFIS.equals(Constants.NOKEYSND)){
                    spinE1SabeInicioSintoma.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta3_FISE1(View.GONE);
                    MostrarOcultarPregunta3_MesAnioE1(View.VISIBLE);
                    e1AnioInicioSintoma = inputE1AnioInicioSintoma.getText().toString();
                    e1Fis = null;
                } else {
                    spinE1SabeInicioSintoma.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta3_MesAnioE1(View.GONE);
                    MostrarOcultarPregunta3_FISE1(View.GONE);
                    e1AnioInicioSintoma = null;
                    e1MesInicioSintoma = null;
                    e1Fis = null;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1MesInicioSintoma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1MESINICIOSINTOMA_CONS, getString(R.string.mesInicioSintoma), e1MesInicioSintoma, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1MesInicioSintoma = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1ConoceLugarExposicion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1CONOCELUGAREXPOSICION_CONS, getString(R.string.conoceLugarExposicion), e1ConoceLugarExposicion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1ConoceLugarExposicion = mr.getCatKey();
                if(e1ConoceLugarExposicion.equals(Constants.YESKEYSND)){
                    spinE1ConoceLugarExposicion.setBackgroundColor(Color.RED);
                    textE1LugarExposicion.setVisibility(View.VISIBLE);
                    inputE1LugarExposicion.setVisibility(View.VISIBLE);
                }
                else{
                    spinE1ConoceLugarExposicion.setBackgroundColor(Color.WHITE);
                    inputE1LugarExposicion.setText("");
                    textE1LugarExposicion.setVisibility(View.GONE);
                    inputE1LugarExposicion.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1BuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1BUSCOAYUDA_CONS, getString(R.string.buscoAyuda), e1BuscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1BuscoAyuda = mr.getCatKey();
                if(e1BuscoAyuda.equals(Constants.YESKEYSND)){//vaya a pregunta 6
                    spinE1BuscoAyuda.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta6E1(View.VISIBLE);
                    MostrarOcultarPregunta7E1(View.VISIBLE);
                    MostrarOcultarPregunta8E1(View.VISIBLE);

                }
                else{ //vaya a pregunta 15
                    spinE1BuscoAyuda.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta6E1(View.GONE);
                    MostrarOcultarPregunta7E1(View.GONE);
                    MostrarOcultarPregunta8E1(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1DondeBuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1DONDEBUSCOAYUDA_CONS, getString(R.string.dondeBuscoAyuda), e1DondeBuscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1DondeBuscoAyuda = mr.getCatKey();
                if (e1DondeBuscoAyuda.equalsIgnoreCase("2")){ //Otro Centro Salud
                    textE1NombreCentro.setVisibility(View.VISIBLE);
                    inputE1NombreCentro.setVisibility(View.VISIBLE);
                    textE1NombreHospital.setVisibility(View.GONE);
                    inputE1NombreHospital.setVisibility(View.GONE);
                    MostrarOcultarPregunta6AE1(View.VISIBLE);
                } else if (e1DondeBuscoAyuda.equalsIgnoreCase("3")){ //Hospital
                    textE1NombreCentro.setVisibility(View.GONE);
                    inputE1NombreCentro.setVisibility(View.GONE);
                    textE1NombreHospital.setVisibility(View.VISIBLE);
                    inputE1NombreHospital.setVisibility(View.VISIBLE);
                    MostrarOcultarPregunta6AE1(View.VISIBLE);
                } else {
                    inputE1NombreCentro.setText("");
                    inputE1NombreHospital.setText("");
                    textE1NombreCentro.setVisibility(View.GONE);
                    inputE1NombreCentro.setVisibility(View.GONE);
                    textE1NombreHospital.setVisibility(View.GONE);
                    inputE1NombreHospital.setVisibility(View.GONE);
                    if (e1DondeBuscoAyuda.isEmpty() || e1DondeBuscoAyuda.equalsIgnoreCase("5")) //No busco ayuda
                        MostrarOcultarPregunta6AE1(View.GONE);
                    else
                        MostrarOcultarPregunta6AE1(View.VISIBLE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1RecibioSeguimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1RECIBIOSEGUIMIENTO_CONS, getString(R.string.recibioSeguimiento), e1RecibioSeguimiento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1RecibioSeguimiento = mr.getCatKey();
                if(e1RecibioSeguimiento.equals(Constants.YESKEYSND)){
                    spinE1RecibioSeguimiento.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta6ASiE1(View.VISIBLE);
                }
                else{
                    spinE1RecibioSeguimiento.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta6ASiE1(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TmpDespuesBuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TMPDESPUESBUSCOAYUDA_CONS, getString(R.string.tmpDespuesBuscoAyuda), e1TmpDespuesBuscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TmpDespuesBuscoAyuda = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TipoSeguimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIPOSEGUIMIENTO_CONS, getString(R.string.tipoSeguimiento), e1TipoSeguimiento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TipoSeguimiento = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1UnaNocheHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1UNANOCHEHOSPITAL_CONS, getString(R.string.unaNocheHospital), e1UnaNocheHospital, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1UnaNocheHospital = mr.getCatKey();
                if(e1UnaNocheHospital.equals(Constants.YESKEYSND)){//vaya a pregunta 9
                    spinE1UnaNocheHospital.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta9E1(View.VISIBLE);
                    MostrarOcultarPregunta10E1(View.VISIBLE);
                    MostrarOcultarPregunta11E1(View.VISIBLE);
                    MostrarOcultarPregunta12E1(View.VISIBLE);
                    MostrarOcultarPregunta13E1(View.VISIBLE);
                    MostrarOcultarPregunta14E1(View.VISIBLE);
                }
                else{ //vaya a pregunta 15
                    spinE1UnaNocheHospital.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta9E1(View.GONE);
                    MostrarOcultarPregunta10E1(View.GONE);
                    MostrarOcultarPregunta11E1(View.GONE);
                    MostrarOcultarPregunta12E1(View.GONE);
                    MostrarOcultarPregunta13E1(View.GONE);
                    MostrarOcultarPregunta14E1(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1SabeCuantasNoches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1SABECUANTASNOCHES_CONS, getString(R.string.sabeCuantasNoches), e1SabeCuantasNoches, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1SabeCuantasNoches = mr.getCatKey();
                if(e1SabeCuantasNoches.equals(Constants.YESKEYSND)){
                    spinE1SabeCuantasNoches.setBackgroundColor(Color.RED);
                    textE1CuantasNochesHosp.setVisibility(View.VISIBLE);
                    inputE1CuantasNochesHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinE1SabeCuantasNoches.setBackgroundColor(Color.WHITE);
                    textE1CuantasNochesHosp.setVisibility(View.GONE);
                    inputE1CuantasNochesHosp.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1SabeFechaAdmision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1SABEFECHAADMISION_CONS, getString(R.string.sabeFechaAdmision), e1SabeFechaAdmision, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1SabeFechaAdmision = mr.getCatKey();
                if(e1SabeFechaAdmision.equals(Constants.YESKEYSND)){
                    spinE1SabeFechaAdmision.setBackgroundColor(Color.RED);
                    layoutE1FechaAdmision.setVisibility(View.VISIBLE);
                    textE1FechaAdmisionHosp.setVisibility(View.VISIBLE);
                    inputE1FechaAdmisionHosp.setVisibility(View.VISIBLE);
                    imbE1FechaAdmisionHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinE1SabeFechaAdmision.setBackgroundColor(Color.WHITE);
                    layoutE1FechaAdmision.setVisibility(View.GONE);
                    textE1FechaAdmisionHosp.setVisibility(View.GONE);
                    inputE1FechaAdmisionHosp.setVisibility(View.GONE);
                    imbE1FechaAdmisionHosp.setVisibility(View.GONE);
                    e1FechaAdmisionHosp = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1SabeFechaAlta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1SABEFECHAALTA_CONS, getString(R.string.sabeFechaAlta), e1SabeFechaAlta, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1SabeFechaAlta = mr.getCatKey();
                if(e1SabeFechaAlta.equals(Constants.YESKEYSND)){
                    spinE1SabeFechaAlta.setBackgroundColor(Color.RED);
                    layoutE1FechaAlta.setVisibility(View.VISIBLE);
                    textE1FechaAltaHosp.setVisibility(View.VISIBLE);
                    inputE1FechaAltaHosp.setVisibility(View.VISIBLE);
                    imbE1FechaAltaHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinE1SabeFechaAlta.setBackgroundColor(Color.WHITE);
                    layoutE1FechaAlta.setVisibility(View.GONE);
                    textE1FechaAltaHosp.setVisibility(View.GONE);
                    inputE1FechaAltaHosp.setVisibility(View.GONE);
                    imbE1FechaAltaHosp.setVisibility(View.GONE);
                    e1FechaAltaHosp = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1EstuvoUCI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1ESTUVOUCI_CONS, getString(R.string.estuvoUCI), e1EstuvoUCI, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1EstuvoUCI = mr.getCatKey();
                if(e1EstuvoUCI.equals(Constants.YESKEYSND)){
                    spinE1EstuvoUCI.setBackgroundColor(Color.RED);
                    textE1FueIntubado.setVisibility(View.VISIBLE);
                    spinE1FueIntubado.setVisibility(View.VISIBLE);
                }
                else{
                    spinE1EstuvoUCI.setBackgroundColor(Color.WHITE);
                    spinE1FueIntubado.setSelection(0, false);
                    textE1FueIntubado.setVisibility(View.GONE);
                    spinE1FueIntubado.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1UtilizoOxigeno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1UTILIZOOXIGENO_CONS, getString(R.string.utilizoOxigeno), e1UtilizoOxigeno, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1UtilizoOxigeno = mr.getCatKey();
                if(e1UtilizoOxigeno.equals(Constants.YESKEYSND)){
                    spinE1UtilizoOxigeno.setBackgroundColor(Color.RED);
                }
                else{
                    spinE1UtilizoOxigeno.setBackgroundColor(Color.WHITE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1FueIntubado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1FUEINTUBADO_CONS, getString(R.string.fueIntubado), e1FueIntubado, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1FueIntubado = mr.getCatKey();
                if(e1FueIntubado.equals(Constants.YESKEYSND)){
                    spinE1FueIntubado.setBackgroundColor(Color.RED);
                }
                else{
                    spinE1FueIntubado.setBackgroundColor(Color.WHITE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1RecuperadoCovid19.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1RECUPERADOCOVID19_CONS, getString(R.string.recuperadoCovid19), e1RecuperadoCovid19, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1RecuperadoCovid19 = mr.getCatKey();
                if(e1RecuperadoCovid19.equals(Constants.YESKEYSND)){
                    spinE1RecuperadoCovid19.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta16E1(View.GONE);
                    MostrarOcultarPregunta17E1(View.VISIBLE);
                }
                else{
                    spinE1RecuperadoCovid19.setBackgroundColor(Color.WHITE);
                    if(e1RecuperadoCovid19.isEmpty()) MostrarOcultarPregunta16E1(View.GONE);
                    else MostrarOcultarPregunta16E1(View.VISIBLE);
                    MostrarOcultarPregunta17E1(View.GONE);
                }
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
                createConfirmDialog(E1SABETIEMPORECUPERACION_CONS, getString(R.string.sabeTiempoRecuperacion), e1SabeTiempoRecuperacion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1SabeTiempoRecuperacion = mr.getCatKey();
                if(e1SabeTiempoRecuperacion.equals(Constants.YESKEYSND)){
                    spinE1SabeTiempoRecuperacion.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta17AE1(View.VISIBLE);
                }
                else{
                    spinE1SabeTiempoRecuperacion.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta17AE1(View.GONE);
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
                createConfirmDialog(E1SEVERIDADENFERMEDAD_CONS, getString(R.string.severidadEnfermedad), e1SeveridadEnfermedad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1SeveridadEnfermedad = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TomoMedicamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TOMOMEDICAMENTO_CONS, getString(R.string.tomoMedicamento), e1TomoMedicamento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TomoMedicamento = mr.getCatKey();
                if(e1TomoMedicamento.equals(Constants.YESKEYSND)){
                    spinE1TomoMedicamento.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta19AE1(View.VISIBLE);
                }
                else{
                    spinE1TomoMedicamento.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta19AE1(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1OxigenoDomicilio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1OXIGENODOMICILIO_CONS, getString(R.string.oxigenoDomicilio), e1OxigenoDomicilio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1OxigenoDomicilio = mr.getCatKey();
                if(e1OxigenoDomicilio.equals(Constants.YESKEYSND)){
                    spinE1OxigenoDomicilio.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta20AE1(View.VISIBLE);
                }
                else{
                    spinE1OxigenoDomicilio.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta20AE1(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE1TiempoOxigenoDom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E1TIEMPOOXIGENODOM_CONS, getString(R.string.tiempoOxigenoDom), e1TiempoOxigenoDom, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1TiempoOxigenoDom = mr.getCatKey();
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
                if(e1TieneOtrosSintomas.equals(Constants.YESKEYSND)){
                    spinE1TieneOtrosSintomas.setBackgroundColor(Color.RED);
                    textE1TieneCualesSintomas.setVisibility(View.VISIBLE);
                    inputE1TieneCualesSintomas.setVisibility(View.VISIBLE);
                }
                else{
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
                    MostrarOcultarPregunta23(View.VISIBLE);
                }
                else{
                    spinFumado.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta23(View.GONE);
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
                    MostrarOcultarPregunta24E1(View.GONE);
                    MostrarOcultarPregunta25E1(View.GONE);
                    MostrarOcultarPregunta24E2(View.GONE);
                    MostrarOcultarPregunta25E2(View.GONE);
                    MostrarOcultarPregunta24E3(View.GONE);
                    MostrarOcultarPregunta25E3(View.GONE);


                }
                else{
                    if (enfermoCovid19 != null && !enfermoCovid19.isEmpty() && enfermoCovid19.equals(Constants.YESKEYSND)) {
                        if (cuantasVecesEnfermo.equals("1")) {
                            MostrarOcultarPregunta24E1(View.VISIBLE);
                            MostrarOcultarPregunta24E2(View.GONE);
                            MostrarOcultarPregunta24E3(View.GONE);
                        }else if (cuantasVecesEnfermo.equals("2")) {
                            MostrarOcultarPregunta24E1(View.VISIBLE);
                            MostrarOcultarPregunta24E2(View.VISIBLE);
                            MostrarOcultarPregunta24E3(View.GONE);
                        }else if (cuantasVecesEnfermo.equals("3")) {
                            MostrarOcultarPregunta24E1(View.VISIBLE);
                            MostrarOcultarPregunta24E2(View.VISIBLE);
                            MostrarOcultarPregunta24E3(View.VISIBLE);
                        } else {
                            MostrarOcultarPregunta24E1(View.GONE);
                            MostrarOcultarPregunta24E2(View.GONE);
                            MostrarOcultarPregunta24E3(View.GONE);
                        }
                    }
                    if (cuantasVecesEnfermo.equals("1")) {
                        MostrarOcultarPregunta25E1(View.VISIBLE);
                        MostrarOcultarPregunta25E2(View.GONE);
                        MostrarOcultarPregunta25E3(View.GONE);
                    }else if (cuantasVecesEnfermo.equals("2")) {
                        MostrarOcultarPregunta25E1(View.VISIBLE);
                        MostrarOcultarPregunta25E2(View.VISIBLE);
                        MostrarOcultarPregunta25E3(View.GONE);
                    }else if (cuantasVecesEnfermo.equals("3")) {
                        MostrarOcultarPregunta25E1(View.VISIBLE);
                        MostrarOcultarPregunta25E2(View.VISIBLE);
                        MostrarOcultarPregunta25E3(View.VISIBLE);
                    } else {
                        MostrarOcultarPregunta25E1(View.GONE);
                        MostrarOcultarPregunta25E2(View.GONE);
                        MostrarOcultarPregunta25E3(View.GONE);
                    }
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
                createConfirmDialog(E1FUMADOPREVIOENFERMEDAD_CONS, getString(R.string.fumadoPrevioEnfermedad), e1FumadoPrevioEnfermedad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
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
                createConfirmDialog(E1FUMAACTUALMENTE_CONS, getString(R.string.fumaActualmente), e1FumaActualmente, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
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
                createConfirmDialog(E1EMBARAZADA_CONS, getString(R.string.embarazada), e1Embarazada, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1Embarazada = mr.getCatKey();
                if(e1Embarazada.equals(Constants.YESKEYSND)){
                    spinE1Embarazada.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta26AE1(View.VISIBLE);
                    MostrarOcultarPregunta26BE1(View.VISIBLE);
                    MostrarOcultarPregunta27E1(View.VISIBLE);
                }
                else{
                    spinE1Embarazada.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta26AE1(View.GONE);
                    MostrarOcultarPregunta26BE1(View.GONE);
                    MostrarOcultarPregunta27E1(View.GONE);
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
                if(e1RecuerdaSemanasEmb.equals(Constants.YESKEYSND)){
                    spinE1RecuerdaSemanasEmb.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta26A1E1(View.VISIBLE);
                }
                else{
                    spinE1RecuerdaSemanasEmb.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta26A1E1(View.GONE);
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
                if(e1FinalEmbarazo.equals("998")){
                    spinE1FinalEmbarazo.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta26B1E1(View.VISIBLE);
                }
                else{
                    spinE1FinalEmbarazo.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta26B1E1(View.GONE);
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
                createConfirmDialog(E1DABAPECHO_CONS, getString(R.string.dabaPecho), e1DabaPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e1DabaPecho = mr.getCatKey();
                spinE1DabaPecho.setBackgroundColor(e1DabaPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        //evento2
        spinE2SabeInicioSintoma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2SABEFIS_CONS, getString(R.string.sabeInicioSintoma), e2SabeFIS, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2SabeFIS = mr.getCatKey();
                if(e2SabeFIS.equals(Constants.YESKEYSND)){
                    spinE2SabeInicioSintoma.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta3_FISE2(View.VISIBLE);
                    MostrarOcultarPregunta3_MesAnioE2(View.GONE);
                    e2AnioInicioSintoma = null;
                    e2MesInicioSintoma = null;
                }
                else if(e2SabeFIS.equals(Constants.NOKEYSND)){
                    spinE2SabeInicioSintoma.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta3_FISE2(View.GONE);
                    MostrarOcultarPregunta3_MesAnioE2(View.VISIBLE);
                    e2AnioInicioSintoma = inputE2AnioInicioSintoma.getText().toString();
                    e2Fis = null;
                } else {
                    spinE2SabeInicioSintoma.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta3_MesAnioE2(View.GONE);
                    MostrarOcultarPregunta3_FISE2(View.GONE);
                    e2AnioInicioSintoma = null;
                    e2MesInicioSintoma = null;
                    e2Fis = null;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2MesInicioSintoma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2MESINICIOSINTOMA_CONS, getString(R.string.mesInicioSintoma), e2MesInicioSintoma, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2MesInicioSintoma = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2ConoceLugarExposicion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2CONOCELUGAREXPOSICION_CONS, getString(R.string.conoceLugarExposicion), e2ConoceLugarExposicion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2ConoceLugarExposicion = mr.getCatKey();
                if(e2ConoceLugarExposicion.equals(Constants.YESKEYSND)){
                    spinE2ConoceLugarExposicion.setBackgroundColor(Color.RED);
                    textE2LugarExposicion.setVisibility(View.VISIBLE);
                    inputE2LugarExposicion.setVisibility(View.VISIBLE);
                }
                else{
                    spinE2ConoceLugarExposicion.setBackgroundColor(Color.WHITE);
                    inputE2LugarExposicion.setText("");
                    textE2LugarExposicion.setVisibility(View.GONE);
                    inputE2LugarExposicion.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2BuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2BUSCOAYUDA_CONS, getString(R.string.buscoAyuda), e2BuscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2BuscoAyuda = mr.getCatKey();
                if(e2BuscoAyuda.equals(Constants.YESKEYSND)){//vaya a pregunta 6
                    spinE2BuscoAyuda.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta6E2(View.VISIBLE);
                    MostrarOcultarPregunta7E2(View.VISIBLE);
                    MostrarOcultarPregunta8E2(View.VISIBLE);

                }
                else{ //vaya a pregunta 15
                    spinE2BuscoAyuda.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta6E2(View.GONE);
                    MostrarOcultarPregunta7E2(View.GONE);
                    MostrarOcultarPregunta8E2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2DondeBuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DONDEBUSCOAYUDA_CONS, getString(R.string.dondeBuscoAyuda), e2DondeBuscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2DondeBuscoAyuda = mr.getCatKey();
                if (e2DondeBuscoAyuda.equalsIgnoreCase("2")){ //Otro Centro Salud
                    textE2NombreCentro.setVisibility(View.VISIBLE);
                    inputE2NombreCentro.setVisibility(View.VISIBLE);
                    textE2NombreHospital.setVisibility(View.GONE);
                    inputE2NombreHospital.setVisibility(View.GONE);
                    MostrarOcultarPregunta6AE2(View.VISIBLE);
                } else if (e2DondeBuscoAyuda.equalsIgnoreCase("3")){ //Hospital
                    textE2NombreCentro.setVisibility(View.GONE);
                    inputE2NombreCentro.setVisibility(View.GONE);
                    textE2NombreHospital.setVisibility(View.VISIBLE);
                    inputE2NombreHospital.setVisibility(View.VISIBLE);
                    MostrarOcultarPregunta6AE2(View.VISIBLE);
                } else {
                    inputE2NombreCentro.setText("");
                    inputE2NombreHospital.setText("");
                    textE2NombreCentro.setVisibility(View.GONE);
                    inputE2NombreCentro.setVisibility(View.GONE);
                    textE2NombreHospital.setVisibility(View.GONE);
                    inputE2NombreHospital.setVisibility(View.GONE);
                    if (e2DondeBuscoAyuda.isEmpty() || e2DondeBuscoAyuda.equalsIgnoreCase("5")) //No busco ayuda
                        MostrarOcultarPregunta6AE2(View.GONE);
                    else
                        MostrarOcultarPregunta6AE2(View.VISIBLE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2RecibioSeguimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2RECIBIOSEGUIMIENTO_CONS, getString(R.string.recibioSeguimiento), e2RecibioSeguimiento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2RecibioSeguimiento = mr.getCatKey();
                if(e2RecibioSeguimiento.equals(Constants.YESKEYSND)){
                    spinE2RecibioSeguimiento.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta6ASiE2(View.VISIBLE);
                }
                else{
                    spinE2RecibioSeguimiento.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta6ASiE2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TmpDespuesBuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TMPDESPUESBUSCOAYUDA_CONS, getString(R.string.tmpDespuesBuscoAyuda), e2TmpDespuesBuscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TmpDespuesBuscoAyuda = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TipoSeguimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIPOSEGUIMIENTO_CONS, getString(R.string.tipoSeguimiento), e2TipoSeguimiento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TipoSeguimiento = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2UnaNocheHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2UNANOCHEHOSPITAL_CONS, getString(R.string.unaNocheHospital), e2UnaNocheHospital, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2UnaNocheHospital = mr.getCatKey();
                if(e2UnaNocheHospital.equals(Constants.YESKEYSND)){//vaya a pregunta 9
                    spinE2UnaNocheHospital.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta9E2(View.VISIBLE);
                    MostrarOcultarPregunta10E2(View.VISIBLE);
                    MostrarOcultarPregunta11E2(View.VISIBLE);
                    MostrarOcultarPregunta12E2(View.VISIBLE);
                    MostrarOcultarPregunta13E2(View.VISIBLE);
                    MostrarOcultarPregunta14E2(View.VISIBLE);
                }
                else{ //vaya a pregunta 15
                    spinE2UnaNocheHospital.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta9E2(View.GONE);
                    MostrarOcultarPregunta10E2(View.GONE);
                    MostrarOcultarPregunta11E2(View.GONE);
                    MostrarOcultarPregunta12E2(View.GONE);
                    MostrarOcultarPregunta13E2(View.GONE);
                    MostrarOcultarPregunta14E2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2SabeCuantasNoches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2SABECUANTASNOCHES_CONS, getString(R.string.sabeCuantasNoches), e2SabeCuantasNoches, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2SabeCuantasNoches = mr.getCatKey();
                if(e2SabeCuantasNoches.equals(Constants.YESKEYSND)){
                    spinE2SabeCuantasNoches.setBackgroundColor(Color.RED);
                    textE2CuantasNochesHosp.setVisibility(View.VISIBLE);
                    inputE2CuantasNochesHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinE2SabeCuantasNoches.setBackgroundColor(Color.WHITE);
                    textE2CuantasNochesHosp.setVisibility(View.GONE);
                    inputE2CuantasNochesHosp.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2SabeFechaAdmision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2SABEFECHAADMISION_CONS, getString(R.string.sabeFechaAdmision), e2SabeFechaAdmision, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2SabeFechaAdmision = mr.getCatKey();
                if(e2SabeFechaAdmision.equals(Constants.YESKEYSND)){
                    spinE2SabeFechaAdmision.setBackgroundColor(Color.RED);
                    layoutE2FechaAdmision.setVisibility(View.VISIBLE);
                    textE2FechaAdmisionHosp.setVisibility(View.VISIBLE);
                    inputE2FechaAdmisionHosp.setVisibility(View.VISIBLE);
                    imbE2FechaAdmisionHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinE2SabeFechaAdmision.setBackgroundColor(Color.WHITE);
                    layoutE2FechaAdmision.setVisibility(View.GONE);
                    textE2FechaAdmisionHosp.setVisibility(View.GONE);
                    inputE2FechaAdmisionHosp.setVisibility(View.GONE);
                    imbE2FechaAdmisionHosp.setVisibility(View.GONE);
                    e2FechaAdmisionHosp = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2SabeFechaAlta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2SABEFECHAALTA_CONS, getString(R.string.sabeFechaAlta), e2SabeFechaAlta, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2SabeFechaAlta = mr.getCatKey();
                if(e2SabeFechaAlta.equals(Constants.YESKEYSND)){
                    spinE2SabeFechaAlta.setBackgroundColor(Color.RED);
                    layoutE2FechaAlta.setVisibility(View.VISIBLE);
                    textE2FechaAltaHosp.setVisibility(View.VISIBLE);
                    inputE2FechaAltaHosp.setVisibility(View.VISIBLE);
                    imbE2FechaAltaHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinE2SabeFechaAlta.setBackgroundColor(Color.WHITE);
                    layoutE2FechaAlta.setVisibility(View.GONE);
                    textE2FechaAltaHosp.setVisibility(View.GONE);
                    inputE2FechaAltaHosp.setVisibility(View.GONE);
                    imbE2FechaAltaHosp.setVisibility(View.GONE);
                    e2FechaAltaHosp = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2EstuvoUCI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2ESTUVOUCI_CONS, getString(R.string.estuvoUCI), e2EstuvoUCI, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2EstuvoUCI = mr.getCatKey();
                if(e2EstuvoUCI.equals(Constants.YESKEYSND)){
                    spinE2EstuvoUCI.setBackgroundColor(Color.RED);
                    textE2FueIntubado.setVisibility(View.VISIBLE);
                    spinE2FueIntubado.setVisibility(View.VISIBLE);
                }
                else{
                    spinE2EstuvoUCI.setBackgroundColor(Color.WHITE);
                    spinE2FueIntubado.setSelection(0, false);
                    textE2FueIntubado.setVisibility(View.GONE);
                    spinE2FueIntubado.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2UtilizoOxigeno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2UTILIZOOXIGENO_CONS, getString(R.string.utilizoOxigeno), e2UtilizoOxigeno, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2UtilizoOxigeno = mr.getCatKey();
                if(e2UtilizoOxigeno.equals(Constants.YESKEYSND)){
                    spinE2UtilizoOxigeno.setBackgroundColor(Color.RED);
                }
                else{
                    spinE2UtilizoOxigeno.setBackgroundColor(Color.WHITE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2FueIntubado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2FUEINTUBADO_CONS, getString(R.string.fueIntubado), e2FueIntubado, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2FueIntubado = mr.getCatKey();
                if(e2FueIntubado.equals(Constants.YESKEYSND)){
                    spinE2FueIntubado.setBackgroundColor(Color.RED);
                }
                else{
                    spinE2FueIntubado.setBackgroundColor(Color.WHITE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2RecuperadoCovid19.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2RECUPERADOCOVID19_CONS, getString(R.string.recuperadoCovid19), e2RecuperadoCovid19, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2RecuperadoCovid19 = mr.getCatKey();
                if(e2RecuperadoCovid19.equals(Constants.YESKEYSND)){
                    spinE2RecuperadoCovid19.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta16E2(View.GONE);
                    MostrarOcultarPregunta17E2(View.VISIBLE);
                }
                else{
                    spinE2RecuperadoCovid19.setBackgroundColor(Color.WHITE);
                    if(e2RecuperadoCovid19.isEmpty()) MostrarOcultarPregunta16E2(View.GONE);
                    else MostrarOcultarPregunta16E2(View.VISIBLE);
                    MostrarOcultarPregunta17E2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2SabeTiempoRecuperacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2SABETIEMPORECUPERACION_CONS, getString(R.string.sabeTiempoRecuperacion), e2SabeTiempoRecuperacion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2SabeTiempoRecuperacion = mr.getCatKey();
                if(e2SabeTiempoRecuperacion.equals(Constants.YESKEYSND)){
                    spinE2SabeTiempoRecuperacion.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta17AE2(View.VISIBLE);
                }
                else{
                    spinE2SabeTiempoRecuperacion.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta17AE2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TiempoRecuperacionEn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIEMPORECUPERACIONEN_CONS, getString(R.string.tiempoRecuperacionEn), e2TiempoRecuperacionEn, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TiempoRecuperacionEn = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2SeveridadEnfermedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2SEVERIDADENFERMEDAD_CONS, getString(R.string.severidadEnfermedad), e2SeveridadEnfermedad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2SeveridadEnfermedad = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TomoMedicamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TOMOMEDICAMENTO_CONS, getString(R.string.tomoMedicamento), e2TomoMedicamento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TomoMedicamento = mr.getCatKey();
                if(e2TomoMedicamento.equals(Constants.YESKEYSND)){
                    spinE2TomoMedicamento.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta19AE2(View.VISIBLE);
                }
                else{
                    spinE2TomoMedicamento.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta19AE2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2OxigenoDomicilio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2OXIGENODOMICILIO_CONS, getString(R.string.oxigenoDomicilio), e2OxigenoDomicilio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2OxigenoDomicilio = mr.getCatKey();
                if(e2OxigenoDomicilio.equals(Constants.YESKEYSND)){
                    spinE2OxigenoDomicilio.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta20AE2(View.VISIBLE);
                }
                else{
                    spinE2OxigenoDomicilio.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta20AE2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TiempoOxigenoDom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIEMPOOXIGENODOM_CONS, getString(R.string.tiempoOxigenoDom), e2TiempoOxigenoDom, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TiempoOxigenoDom = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /*SINTOMAS ACTUALES*/
        spinE2TieneFebricula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEFEBRICULA_CONS, getString(R.string.febricula), e2TieneFebricula, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneFebricula = mr.getCatKey();
                spinE2TieneFebricula.setBackgroundColor(e2TieneFebricula.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TieneCansancio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENECANSANCIO_CONS, getString(R.string.cansancio), e2TieneCansancio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneCansancio = mr.getCatKey();
                spinE2TieneCansancio.setBackgroundColor(e2TieneCansancio.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TieneDolorCabeza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEDOLORCABEZA_CONS, getString(R.string.dolorCabeza), e2TieneDolorCabeza, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneDolorCabeza = mr.getCatKey();
                spinE2TieneDolorCabeza.setBackgroundColor(e2TieneDolorCabeza.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TienePerdidaOlfato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEPERDIDAOLFATO_CONS, getString(R.string.perdidaOlfato), e2TienePerdidaOlfato, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TienePerdidaOlfato = mr.getCatKey();
                spinE2TienePerdidaOlfato.setBackgroundColor(e2TienePerdidaOlfato.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TienePerdidaGusto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEPERDIDAGUSTO_CONS, getString(R.string.perdidaGusto), e2TienePerdidaGusto, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TienePerdidaGusto = mr.getCatKey();
                spinE2TienePerdidaGusto.setBackgroundColor(e2TienePerdidaGusto.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TieneTos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENETOS_CONS, getString(R.string.tos), e2TieneTos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneTos = mr.getCatKey();
                spinE2TieneTos.setBackgroundColor(e2TieneTos.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TieneDificultadRespirar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEDIFICULTADRESPIRAR_CONS, getString(R.string.dificultadRespirar), e2TieneDificultadRespirar, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneDificultadRespirar = mr.getCatKey();
                spinE2TieneDificultadRespirar.setBackgroundColor(e2TieneDificultadRespirar.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TieneDolorPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEDOLORPECHO_CONS, getString(R.string.dolorPecho), e2TieneDolorPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneDolorPecho = mr.getCatKey();
                spinE2TieneDolorPecho.setBackgroundColor(e2TieneDolorPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TienePalpitaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEPALPITACIONES_CONS, getString(R.string.palpitaciones), e2TienePalpitaciones, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TienePalpitaciones = mr.getCatKey();
                spinE2TienePalpitaciones.setBackgroundColor(e2TienePalpitaciones.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TieneDolorArticulaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEDOLORARTICULACIONES_CONS, getString(R.string.dolorArticulaciones), e2TieneDolorArticulaciones, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneDolorArticulaciones = mr.getCatKey();
                spinE2TieneDolorArticulaciones.setBackgroundColor(e2TieneDolorArticulaciones.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TieneParalisis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEPARALISIS_CONS, getString(R.string.paralisis), e2TieneParalisis, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneParalisis = mr.getCatKey();
                spinE2TieneParalisis.setBackgroundColor(e2TieneParalisis.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TieneMareos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEMAREOS_CONS, getString(R.string.mareos), e2TieneMareos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneMareos = mr.getCatKey();
                spinE2TieneMareos.setBackgroundColor(e2TieneMareos.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TienePensamientoNublado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEPENSAMIENTONUBLADO_CONS, getString(R.string.pensamientoNublado), e2TienePensamientoNublado, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TienePensamientoNublado = mr.getCatKey();
                spinE2TienePensamientoNublado.setBackgroundColor(e2TienePensamientoNublado.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TieneProblemasDormir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEPROBLEMASDORMIR_CONS, getString(R.string.problemasDormir), e2TieneProblemasDormir, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneProblemasDormir = mr.getCatKey();
                spinE2TieneProblemasDormir.setBackgroundColor(e2TieneProblemasDormir.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TieneDepresion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEDEPRESION_CONS, getString(R.string.depresion), e2TieneDepresion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneDepresion = mr.getCatKey();
                spinE2TieneDepresion.setBackgroundColor(e2TieneDepresion.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2TieneOtrosSintomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2TIENEOTROSSINTOMAS_CONS, getString(R.string.otrosSintomas), e2TieneOtrosSintomas, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2TieneOtrosSintomas = mr.getCatKey();
                if(e2TieneOtrosSintomas.equals(Constants.YESKEYSND)){
                    spinE2TieneOtrosSintomas.setBackgroundColor(Color.RED);
                    textE2TieneCualesSintomas.setVisibility(View.VISIBLE);
                    inputE2TieneCualesSintomas.setVisibility(View.VISIBLE);
                }
                else{
                    spinE2TieneOtrosSintomas.setBackgroundColor(Color.WHITE);
                    textE2TieneCualesSintomas.setVisibility(View.GONE);
                    inputE2TieneCualesSintomas.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*FIN SINTOMAS ACTUALES EVENTO1*/

        spinE2FumadoPrevioEnfermedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2FUMADOPREVIOENFERMEDAD_CONS, getString(R.string.fumadoPrevioEnfermedad), e2FumadoPrevioEnfermedad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2FumadoPrevioEnfermedad = mr.getCatKey();
                spinE2FumadoPrevioEnfermedad.setBackgroundColor(e2FumadoPrevioEnfermedad.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2FumaActualmente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2FUMAACTUALMENTE_CONS, getString(R.string.fumaActualmente), e2FumaActualmente, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2FumaActualmente = mr.getCatKey();
                spinE2FumaActualmente.setBackgroundColor(e2FumaActualmente.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2Embarazada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2EMBARAZADA_CONS, getString(R.string.embarazada), e2Embarazada, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2Embarazada = mr.getCatKey();
                if(e2Embarazada.equals(Constants.YESKEYSND)){
                    spinE2Embarazada.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta26AE2(View.VISIBLE);
                    MostrarOcultarPregunta26BE2(View.VISIBLE);
                    MostrarOcultarPregunta27E2(View.VISIBLE);
                }
                else{
                    spinE2Embarazada.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta26AE2(View.GONE);
                    MostrarOcultarPregunta26BE2(View.GONE);
                    MostrarOcultarPregunta27E2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2RecuerdaSemanasEmb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2RECUERDASEMANASEMB_CONS, getString(R.string.recuerdaSemanasEmb), e2RecuerdaSemanasEmb, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2RecuerdaSemanasEmb = mr.getCatKey();
                if(e2RecuerdaSemanasEmb.equals(Constants.YESKEYSND)){
                    spinE2RecuerdaSemanasEmb.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta26A1E2(View.VISIBLE);
                }
                else{
                    spinE2RecuerdaSemanasEmb.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta26A1E2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2FinalEmbarazo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2FINALEMBARAZO_CONS, getString(R.string.finalEmbarazo), e2FinalEmbarazo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2FinalEmbarazo = mr.getCatKey();
                if(e2FinalEmbarazo.equals("998")){
                    spinE2FinalEmbarazo.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta26B1E2(View.VISIBLE);
                }
                else{
                    spinE2FinalEmbarazo.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta26B1E2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE2DabaPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E2DABAPECHO_CONS, getString(R.string.dabaPecho), e2DabaPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e2DabaPecho = mr.getCatKey();
                spinE2DabaPecho.setBackgroundColor(e2DabaPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
//evento3
        spinE3SabeInicioSintoma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3SABEFIS_CONS, getString(R.string.sabeInicioSintoma), e3SabeFIS, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3SabeFIS = mr.getCatKey();
                if(e3SabeFIS.equals(Constants.YESKEYSND)){
                    spinE3SabeInicioSintoma.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta3_FISE3(View.VISIBLE);
                    MostrarOcultarPregunta3_MesAnioE3(View.GONE);
                    e3AnioInicioSintoma = null;
                    e3MesInicioSintoma = null;
                }
                else if(e3SabeFIS.equals(Constants.NOKEYSND)){
                    spinE3SabeInicioSintoma.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta3_FISE3(View.GONE);
                    MostrarOcultarPregunta3_MesAnioE3(View.VISIBLE);
                    e3AnioInicioSintoma = inputE3AnioInicioSintoma.getText().toString();
                    e3Fis = null;
                } else {
                    spinE3SabeInicioSintoma.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta3_MesAnioE3(View.GONE);
                    MostrarOcultarPregunta3_FISE3(View.GONE);
                    e3AnioInicioSintoma = null;
                    e3MesInicioSintoma = null;
                    e3Fis = null;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3MesInicioSintoma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3MESINICIOSINTOMA_CONS, getString(R.string.mesInicioSintoma), e3MesInicioSintoma, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3MesInicioSintoma = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3ConoceLugarExposicion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3CONOCELUGAREXPOSICION_CONS, getString(R.string.conoceLugarExposicion), e3ConoceLugarExposicion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3ConoceLugarExposicion = mr.getCatKey();
                if(e3ConoceLugarExposicion.equals(Constants.YESKEYSND)){
                    spinE3ConoceLugarExposicion.setBackgroundColor(Color.RED);
                    textE3LugarExposicion.setVisibility(View.VISIBLE);
                    inputE3LugarExposicion.setVisibility(View.VISIBLE);
                }
                else{
                    spinE3ConoceLugarExposicion.setBackgroundColor(Color.WHITE);
                    inputE3LugarExposicion.setText("");
                    textE3LugarExposicion.setVisibility(View.GONE);
                    inputE3LugarExposicion.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3BuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3BUSCOAYUDA_CONS, getString(R.string.buscoAyuda), e3BuscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3BuscoAyuda = mr.getCatKey();
                if(e3BuscoAyuda.equals(Constants.YESKEYSND)){//vaya a pregunta 6
                    spinE3BuscoAyuda.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta6E3(View.VISIBLE);
                    MostrarOcultarPregunta7E3(View.VISIBLE);
                    MostrarOcultarPregunta8E3(View.VISIBLE);

                }
                else{ //vaya a pregunta 15
                    spinE3BuscoAyuda.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta6E3(View.GONE);
                    MostrarOcultarPregunta7E3(View.GONE);
                    MostrarOcultarPregunta8E3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3DondeBuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DONDEBUSCOAYUDA_CONS, getString(R.string.dondeBuscoAyuda), e3DondeBuscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3DondeBuscoAyuda = mr.getCatKey();
                if (e3DondeBuscoAyuda.equalsIgnoreCase("2")){ //Otro Centro Salud
                    textE3NombreCentro.setVisibility(View.VISIBLE);
                    inputE3NombreCentro.setVisibility(View.VISIBLE);
                    textE3NombreHospital.setVisibility(View.GONE);
                    inputE3NombreHospital.setVisibility(View.GONE);
                    MostrarOcultarPregunta6AE3(View.VISIBLE);
                } else if (e3DondeBuscoAyuda.equalsIgnoreCase("3")){ //Hospital
                    textE3NombreCentro.setVisibility(View.GONE);
                    inputE3NombreCentro.setVisibility(View.GONE);
                    textE3NombreHospital.setVisibility(View.VISIBLE);
                    inputE3NombreHospital.setVisibility(View.VISIBLE);
                    MostrarOcultarPregunta6AE3(View.VISIBLE);
                } else {
                    inputE3NombreCentro.setText("");
                    inputE3NombreHospital.setText("");
                    textE3NombreCentro.setVisibility(View.GONE);
                    inputE3NombreCentro.setVisibility(View.GONE);
                    textE3NombreHospital.setVisibility(View.GONE);
                    inputE3NombreHospital.setVisibility(View.GONE);
                    if (e3DondeBuscoAyuda.isEmpty() || e3DondeBuscoAyuda.equalsIgnoreCase("5")) //No busco ayuda
                        MostrarOcultarPregunta6AE3(View.GONE);
                    else
                        MostrarOcultarPregunta6AE3(View.VISIBLE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3RecibioSeguimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3RECIBIOSEGUIMIENTO_CONS, getString(R.string.recibioSeguimiento), e3RecibioSeguimiento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3RecibioSeguimiento = mr.getCatKey();
                if(e3RecibioSeguimiento.equals(Constants.YESKEYSND)){
                    spinE3RecibioSeguimiento.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta6ASiE3(View.VISIBLE);
                }
                else{
                    spinE3RecibioSeguimiento.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta6ASiE3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TmpDespuesBuscoAyuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TMPDESPUESBUSCOAYUDA_CONS, getString(R.string.tmpDespuesBuscoAyuda), e3TmpDespuesBuscoAyuda, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TmpDespuesBuscoAyuda = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TipoSeguimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIPOSEGUIMIENTO_CONS, getString(R.string.tipoSeguimiento), e3TipoSeguimiento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TipoSeguimiento = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3UnaNocheHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3UNANOCHEHOSPITAL_CONS, getString(R.string.unaNocheHospital), e3UnaNocheHospital, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3UnaNocheHospital = mr.getCatKey();
                if(e3UnaNocheHospital.equals(Constants.YESKEYSND)){//vaya a pregunta 9
                    spinE3UnaNocheHospital.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta9E3(View.VISIBLE);
                    MostrarOcultarPregunta10E3(View.VISIBLE);
                    MostrarOcultarPregunta11E3(View.VISIBLE);
                    MostrarOcultarPregunta12E3(View.VISIBLE);
                    MostrarOcultarPregunta13E3(View.VISIBLE);
                    MostrarOcultarPregunta14E3(View.VISIBLE);
                }
                else{ //vaya a pregunta 15
                    spinE3UnaNocheHospital.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta9E3(View.GONE);
                    MostrarOcultarPregunta10E3(View.GONE);
                    MostrarOcultarPregunta11E3(View.GONE);
                    MostrarOcultarPregunta12E3(View.GONE);
                    MostrarOcultarPregunta13E3(View.GONE);
                    MostrarOcultarPregunta14E3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3SabeCuantasNoches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3SABECUANTASNOCHES_CONS, getString(R.string.sabeCuantasNoches), e3SabeCuantasNoches, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3SabeCuantasNoches = mr.getCatKey();
                if(e3SabeCuantasNoches.equals(Constants.YESKEYSND)){
                    spinE3SabeCuantasNoches.setBackgroundColor(Color.RED);
                    textE3CuantasNochesHosp.setVisibility(View.VISIBLE);
                    inputE3CuantasNochesHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinE3SabeCuantasNoches.setBackgroundColor(Color.WHITE);
                    textE3CuantasNochesHosp.setVisibility(View.GONE);
                    inputE3CuantasNochesHosp.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3SabeFechaAdmision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3SABEFECHAADMISION_CONS, getString(R.string.sabeFechaAdmision), e3SabeFechaAdmision, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3SabeFechaAdmision = mr.getCatKey();
                if(e3SabeFechaAdmision.equals(Constants.YESKEYSND)){
                    spinE3SabeFechaAdmision.setBackgroundColor(Color.RED);
                    layoutE3FechaAdmision.setVisibility(View.VISIBLE);
                    textE3FechaAdmisionHosp.setVisibility(View.VISIBLE);
                    inputE3FechaAdmisionHosp.setVisibility(View.VISIBLE);
                    imbE3FechaAdmisionHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinE3SabeFechaAdmision.setBackgroundColor(Color.WHITE);
                    layoutE3FechaAdmision.setVisibility(View.GONE);
                    textE3FechaAdmisionHosp.setVisibility(View.GONE);
                    inputE3FechaAdmisionHosp.setVisibility(View.GONE);
                    imbE3FechaAdmisionHosp.setVisibility(View.GONE);
                    e3FechaAdmisionHosp = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3SabeFechaAlta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3SABEFECHAALTA_CONS, getString(R.string.sabeFechaAlta), e3SabeFechaAlta, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3SabeFechaAlta = mr.getCatKey();
                if(e3SabeFechaAlta.equals(Constants.YESKEYSND)){
                    spinE3SabeFechaAlta.setBackgroundColor(Color.RED);
                    layoutE3FechaAlta.setVisibility(View.VISIBLE);
                    textE3FechaAltaHosp.setVisibility(View.VISIBLE);
                    inputE3FechaAltaHosp.setVisibility(View.VISIBLE);
                    imbE3FechaAltaHosp.setVisibility(View.VISIBLE);
                }
                else{
                    spinE3SabeFechaAlta.setBackgroundColor(Color.WHITE);
                    layoutE3FechaAlta.setVisibility(View.GONE);
                    textE3FechaAltaHosp.setVisibility(View.GONE);
                    inputE3FechaAltaHosp.setVisibility(View.GONE);
                    imbE3FechaAltaHosp.setVisibility(View.GONE);
                    e3FechaAltaHosp = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3EstuvoUCI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3ESTUVOUCI_CONS, getString(R.string.estuvoUCI), e3EstuvoUCI, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3EstuvoUCI = mr.getCatKey();
                if(e3EstuvoUCI.equals(Constants.YESKEYSND)){
                    spinE3EstuvoUCI.setBackgroundColor(Color.RED);
                    textE3FueIntubado.setVisibility(View.VISIBLE);
                    spinE3FueIntubado.setVisibility(View.VISIBLE);
                }
                else{
                    spinE3EstuvoUCI.setBackgroundColor(Color.WHITE);
                    spinE3FueIntubado.setSelection(0, false);
                    textE3FueIntubado.setVisibility(View.GONE);
                    spinE3FueIntubado.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3UtilizoOxigeno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3UTILIZOOXIGENO_CONS, getString(R.string.utilizoOxigeno), e3UtilizoOxigeno, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3UtilizoOxigeno = mr.getCatKey();
                if(e3UtilizoOxigeno.equals(Constants.YESKEYSND)){
                    spinE3UtilizoOxigeno.setBackgroundColor(Color.RED);
                }
                else{
                    spinE3UtilizoOxigeno.setBackgroundColor(Color.WHITE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3FueIntubado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3FUEINTUBADO_CONS, getString(R.string.fueIntubado), e3FueIntubado, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3FueIntubado = mr.getCatKey();
                if(e3FueIntubado.equals(Constants.YESKEYSND)){
                    spinE3FueIntubado.setBackgroundColor(Color.RED);
                }
                else{
                    spinE3FueIntubado.setBackgroundColor(Color.WHITE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3RecuperadoCovid19.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3RECUPERADOCOVID19_CONS, getString(R.string.recuperadoCovid19), e3RecuperadoCovid19, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3RecuperadoCovid19 = mr.getCatKey();
                if(e3RecuperadoCovid19.equals(Constants.YESKEYSND)){
                    spinE3RecuperadoCovid19.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta16E3(View.GONE);
                    MostrarOcultarPregunta17E3(View.VISIBLE);
                }
                else{
                    spinE3RecuperadoCovid19.setBackgroundColor(Color.WHITE);
                    if(e3RecuperadoCovid19.isEmpty()) MostrarOcultarPregunta16E3(View.GONE);
                    else MostrarOcultarPregunta16E3(View.VISIBLE);
                    MostrarOcultarPregunta17E3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3SabeTiempoRecuperacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3SABETIEMPORECUPERACION_CONS, getString(R.string.sabeTiempoRecuperacion), e3SabeTiempoRecuperacion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3SabeTiempoRecuperacion = mr.getCatKey();
                if(e3SabeTiempoRecuperacion.equals(Constants.YESKEYSND)){
                    spinE3SabeTiempoRecuperacion.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta17AE3(View.VISIBLE);
                }
                else{
                    spinE3SabeTiempoRecuperacion.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta17AE3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TiempoRecuperacionEn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIEMPORECUPERACIONEN_CONS, getString(R.string.tiempoRecuperacionEn), e3TiempoRecuperacionEn, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TiempoRecuperacionEn = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3SeveridadEnfermedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3SEVERIDADENFERMEDAD_CONS, getString(R.string.severidadEnfermedad), e3SeveridadEnfermedad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3SeveridadEnfermedad = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TomoMedicamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TOMOMEDICAMENTO_CONS, getString(R.string.tomoMedicamento), e3TomoMedicamento, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TomoMedicamento = mr.getCatKey();
                if(e3TomoMedicamento.equals(Constants.YESKEYSND)){
                    spinE3TomoMedicamento.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta19AE3(View.VISIBLE);
                }
                else{
                    spinE3TomoMedicamento.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta19AE3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3OxigenoDomicilio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3OXIGENODOMICILIO_CONS, getString(R.string.oxigenoDomicilio), e3OxigenoDomicilio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3OxigenoDomicilio = mr.getCatKey();
                if(e3OxigenoDomicilio.equals(Constants.YESKEYSND)){
                    spinE3OxigenoDomicilio.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta20AE3(View.VISIBLE);
                }
                else{
                    spinE3OxigenoDomicilio.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta20AE3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TiempoOxigenoDom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIEMPOOXIGENODOM_CONS, getString(R.string.tiempoOxigenoDom), e3TiempoOxigenoDom, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TiempoOxigenoDom = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /*SINTOMAS ACTUALES*/
        spinE3TieneFebricula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEFEBRICULA_CONS, getString(R.string.febricula), e3TieneFebricula, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneFebricula = mr.getCatKey();
                spinE3TieneFebricula.setBackgroundColor(e3TieneFebricula.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TieneCansancio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENECANSANCIO_CONS, getString(R.string.cansancio), e3TieneCansancio, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneCansancio = mr.getCatKey();
                spinE3TieneCansancio.setBackgroundColor(e3TieneCansancio.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TieneDolorCabeza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEDOLORCABEZA_CONS, getString(R.string.dolorCabeza), e3TieneDolorCabeza, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneDolorCabeza = mr.getCatKey();
                spinE3TieneDolorCabeza.setBackgroundColor(e3TieneDolorCabeza.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TienePerdidaOlfato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEPERDIDAOLFATO_CONS, getString(R.string.perdidaOlfato), e3TienePerdidaOlfato, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TienePerdidaOlfato = mr.getCatKey();
                spinE3TienePerdidaOlfato.setBackgroundColor(e3TienePerdidaOlfato.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TienePerdidaGusto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEPERDIDAGUSTO_CONS, getString(R.string.perdidaGusto), e3TienePerdidaGusto, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TienePerdidaGusto = mr.getCatKey();
                spinE3TienePerdidaGusto.setBackgroundColor(e3TienePerdidaGusto.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TieneTos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENETOS_CONS, getString(R.string.tos), e3TieneTos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneTos = mr.getCatKey();
                spinE3TieneTos.setBackgroundColor(e3TieneTos.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TieneDificultadRespirar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEDIFICULTADRESPIRAR_CONS, getString(R.string.dificultadRespirar), e3TieneDificultadRespirar, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneDificultadRespirar = mr.getCatKey();
                spinE3TieneDificultadRespirar.setBackgroundColor(e3TieneDificultadRespirar.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TieneDolorPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEDOLORPECHO_CONS, getString(R.string.dolorPecho), e3TieneDolorPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneDolorPecho = mr.getCatKey();
                spinE3TieneDolorPecho.setBackgroundColor(e3TieneDolorPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TienePalpitaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEPALPITACIONES_CONS, getString(R.string.palpitaciones), e3TienePalpitaciones, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TienePalpitaciones = mr.getCatKey();
                spinE3TienePalpitaciones.setBackgroundColor(e3TienePalpitaciones.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TieneDolorArticulaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEDOLORARTICULACIONES_CONS, getString(R.string.dolorArticulaciones), e3TieneDolorArticulaciones, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneDolorArticulaciones = mr.getCatKey();
                spinE3TieneDolorArticulaciones.setBackgroundColor(e3TieneDolorArticulaciones.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TieneParalisis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEPARALISIS_CONS, getString(R.string.paralisis), e3TieneParalisis, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneParalisis = mr.getCatKey();
                spinE3TieneParalisis.setBackgroundColor(e3TieneParalisis.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TieneMareos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEMAREOS_CONS, getString(R.string.mareos), e3TieneMareos, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneMareos = mr.getCatKey();
                spinE3TieneMareos.setBackgroundColor(e3TieneMareos.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TienePensamientoNublado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEPENSAMIENTONUBLADO_CONS, getString(R.string.pensamientoNublado), e3TienePensamientoNublado, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TienePensamientoNublado = mr.getCatKey();
                spinE3TienePensamientoNublado.setBackgroundColor(e3TienePensamientoNublado.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TieneProblemasDormir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEPROBLEMASDORMIR_CONS, getString(R.string.problemasDormir), e3TieneProblemasDormir, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneProblemasDormir = mr.getCatKey();
                spinE3TieneProblemasDormir.setBackgroundColor(e3TieneProblemasDormir.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TieneDepresion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEDEPRESION_CONS, getString(R.string.depresion), e3TieneDepresion, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneDepresion = mr.getCatKey();
                spinE3TieneDepresion.setBackgroundColor(e3TieneDepresion.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3TieneOtrosSintomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3TIENEOTROSSINTOMAS_CONS, getString(R.string.otrosSintomas), e3TieneOtrosSintomas, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3TieneOtrosSintomas = mr.getCatKey();
                if(e3TieneOtrosSintomas.equals(Constants.YESKEYSND)){
                    spinE3TieneOtrosSintomas.setBackgroundColor(Color.RED);
                    textE3TieneCualesSintomas.setVisibility(View.VISIBLE);
                    inputE3TieneCualesSintomas.setVisibility(View.VISIBLE);
                }
                else{
                    spinE3TieneOtrosSintomas.setBackgroundColor(Color.WHITE);
                    textE3TieneCualesSintomas.setVisibility(View.GONE);
                    inputE3TieneCualesSintomas.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*FIN SINTOMAS ACTUALES EVENTO1*/

        spinE3FumadoPrevioEnfermedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3FUMADOPREVIOENFERMEDAD_CONS, getString(R.string.fumadoPrevioEnfermedad), e3FumadoPrevioEnfermedad, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3FumadoPrevioEnfermedad = mr.getCatKey();
                spinE3FumadoPrevioEnfermedad.setBackgroundColor(e3FumadoPrevioEnfermedad.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3FumaActualmente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3FUMAACTUALMENTE_CONS, getString(R.string.fumaActualmente), e3FumaActualmente, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3FumaActualmente = mr.getCatKey();
                spinE3FumaActualmente.setBackgroundColor(e3FumaActualmente.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3Embarazada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3EMBARAZADA_CONS, getString(R.string.embarazada), e3Embarazada, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3Embarazada = mr.getCatKey();
                if(e3Embarazada.equals(Constants.YESKEYSND)){
                    spinE3Embarazada.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta26AE3(View.VISIBLE);
                    MostrarOcultarPregunta26BE3(View.VISIBLE);
                    MostrarOcultarPregunta27E3(View.VISIBLE);
                }
                else{
                    spinE3Embarazada.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta26AE3(View.GONE);
                    MostrarOcultarPregunta26BE3(View.GONE);
                    MostrarOcultarPregunta27E3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3RecuerdaSemanasEmb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3RECUERDASEMANASEMB_CONS, getString(R.string.recuerdaSemanasEmb), e3RecuerdaSemanasEmb, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3RecuerdaSemanasEmb = mr.getCatKey();
                if(e3RecuerdaSemanasEmb.equals(Constants.YESKEYSND)){
                    spinE3RecuerdaSemanasEmb.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta26A1E3(View.VISIBLE);
                }
                else{
                    spinE3RecuerdaSemanasEmb.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta26A1E3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3FinalEmbarazo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3FINALEMBARAZO_CONS, getString(R.string.finalEmbarazo), e3FinalEmbarazo, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3FinalEmbarazo = mr.getCatKey();
                if(e3FinalEmbarazo.equals("998")){
                    spinE3FinalEmbarazo.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta26B1E3(View.VISIBLE);
                }
                else{
                    spinE3FinalEmbarazo.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta26B1E3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinE3DabaPecho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(E3DABAPECHO_CONS, getString(R.string.dabaPecho), e3DabaPecho, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                e3DabaPecho = mr.getCatKey();
                spinE3DabaPecho.setBackgroundColor(e3DabaPecho.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
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

        spinVacunadoCovid19.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(VACUNADOCOVID19_CONS, getString(R.string.vacunadoCovid19), vacunadoCovid19, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                vacunadoCovid19 = mr.getCatKey();
                if(vacunadoCovid19.equals(Constants.YESKEYSND)){
                    spinVacunadoCovid19.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta30(View.VISIBLE);
                    MostrarOcultarPregunta32(View.VISIBLE);
                } else {
                    spinVacunadoCovid19.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta30(View.GONE);
                    MostrarOcultarPregunta32(View.GONE);
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
                createConfirmDialog(MUESTRATARJETAVAC_CONS, getString(R.string.muestraTarjetaVac), muestraTarjetaVac, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                muestraTarjetaVac = mr.getCatKey();
                if(muestraTarjetaVac.equals(Constants.YESKEYSND)){
                    spinMuestraTarjetaVac.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta30_Dosis(View.VISIBLE);
                    MostrarOcultarPregunta30A(View.GONE);
                } else if(muestraTarjetaVac.equals(Constants.NOKEYSND)){
                    spinMuestraTarjetaVac.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta30_Dosis(View.GONE);
                    MostrarOcultarPregunta30A(View.VISIBLE);
                } else {
                    spinMuestraTarjetaVac.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta30_Dosis(View.GONE);
                    MostrarOcultarPregunta30A(View.GONE);
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
                if(sabeNombreVacuna.equals(Constants.YESKEYSND)){
                    spinSabeNombreVacuna.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta30A_Vacuna(View.VISIBLE);
                } else {
                    spinSabeNombreVacuna.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta30A_Vacuna(View.GONE);
                }
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
                        MostrarOcultarPregunta30_Dosis1(View.VISIBLE);
                        MostrarOcultarPregunta30_Dosis2(View.GONE);
                        MostrarOcultarPregunta30_Dosis3(View.GONE);
                        MostrarOcultarPregunta31Dosis1(View.VISIBLE);
                        MostrarOcultarPregunta31Dosis2(View.GONE);
                        MostrarOcultarPregunta31Dosis3(View.GONE);
                        break;
                    case "2":
                        MostrarOcultarPregunta30_Dosis1(View.VISIBLE);
                        MostrarOcultarPregunta30_Dosis2(View.VISIBLE);
                        MostrarOcultarPregunta30_Dosis3(View.GONE);
                        MostrarOcultarPregunta31Dosis1(View.VISIBLE);
                        MostrarOcultarPregunta31Dosis2(View.VISIBLE);
                        MostrarOcultarPregunta31Dosis3(View.GONE);
                        break;
                    case "3":
                        MostrarOcultarPregunta30_Dosis1(View.VISIBLE);
                        MostrarOcultarPregunta30_Dosis2(View.VISIBLE);
                        MostrarOcultarPregunta30_Dosis3(View.VISIBLE);
                        MostrarOcultarPregunta31Dosis1(View.VISIBLE);
                        MostrarOcultarPregunta31Dosis2(View.VISIBLE);
                        MostrarOcultarPregunta31Dosis3(View.VISIBLE);
                        break;
                    default:
                        MostrarOcultarPregunta30_Dosis1(View.GONE);
                        MostrarOcultarPregunta30_Dosis2(View.GONE);
                        MostrarOcultarPregunta30_Dosis3(View.GONE);
                        MostrarOcultarPregunta31Dosis1(View.GONE);
                        MostrarOcultarPregunta31Dosis2(View.GONE);
                        MostrarOcultarPregunta31Dosis3(View.GONE);
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
                if(nombreDosis1.equals("998")){
                    spinNombreDosis1.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta30_Dosis1_Otro(View.VISIBLE);
                } else {
                    spinNombreDosis1.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta30_Dosis1_Otro(View.GONE);
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
                if(nombreDosis2.equals("998")){
                    spinNombreDosis2.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta30_Dosis2_Otro(View.VISIBLE);
                } else {
                    spinNombreDosis2.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta30_Dosis2_Otro(View.GONE);
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
                if(nombreDosis3.equals("998")){
                    spinNombreDosis3.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta30_Dosis3_Otro(View.VISIBLE);
                } else {
                    spinNombreDosis3.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta30_Dosis3_Otro(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinPresentoSintomasDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PRESENTOSINTOMASDOSIS1_CONS, getString(R.string.presentoSintomasVacuna1), presentoSintomasDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                presentoSintomasDosis1 = mr.getCatKey();
                if(presentoSintomasDosis1.equals(Constants.YESKEYSND)){
                    spinPresentoSintomasDosis1.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta31_SintomasDosis1(View.VISIBLE);
                } else {
                    spinPresentoSintomasDosis1.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta31_SintomasDosis1(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorSitioDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORSITIODOSIS1_CONS, getString(R.string.dolorSitioVac), dolorSitioDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorSitioDosis1 = mr.getCatKey();
                spinDolorSitioDosis1.setBackgroundColor(dolorSitioDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFiebreDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FIEBREDOSIS1_CONS, getString(R.string.fiebreVac), fiebreDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                fiebreDosis1 = mr.getCatKey();
                spinFiebreDosis1.setBackgroundColor(fiebreDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinCansancioDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(CANSANCIODOSIS1_CONS, getString(R.string.cansancioVac), cansancioDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                cansancioDosis1 = mr.getCatKey();
                spinCansancioDosis1.setBackgroundColor(cansancioDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorCabezaDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORCABEZADOSIS1_CONS, getString(R.string.dolorCabezaVac), dolorCabezaDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorCabezaDosis1 = mr.getCatKey();
                spinDolorCabezaDosis1.setBackgroundColor(dolorCabezaDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDiarreaDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DIARREADOSIS1_CONS, getString(R.string.diarreaVac), diarreaDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                diarreaDosis1 = mr.getCatKey();
                spinDiarreaDosis1.setBackgroundColor(diarreaDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinVomitoDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(VOMITODOSIS1_CONS, getString(R.string.vomitoVac), vomitoDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                vomitoDosis1 = mr.getCatKey();
                spinVomitoDosis1.setBackgroundColor(vomitoDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorMuscularDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORMUSCULARDOSIS1_CONS, getString(R.string.dolorMuscularVac), dolorMuscularDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorMuscularDosis1 = mr.getCatKey();
                spinDolorMuscularDosis1.setBackgroundColor(dolorMuscularDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinEscalofriosDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(ESCALOFRIOSDOSIS1_CONS, getString(R.string.escalofriosVac), escalofriosDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                escalofriosDosis1 = mr.getCatKey();
                spinEscalofriosDosis1.setBackgroundColor(escalofriosDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinReaccionAlergicaDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(REACCIONALERGICADOSIS1_CONS, getString(R.string.reaccionAlergicaVac), reaccionAlergicaDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                reaccionAlergicaDosis1 = mr.getCatKey();
                spinReaccionAlergicaDosis1.setBackgroundColor(reaccionAlergicaDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinInfeccionSitioDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(INFECCIONSITIODOSIS1_CONS, getString(R.string.infeccionSitioVac), infeccionSitioDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                infeccionSitioDosis1 = mr.getCatKey();
                spinInfeccionSitioDosis1.setBackgroundColor(infeccionSitioDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinNauseasDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(NAUSEASDOSIS1_CONS, getString(R.string.nauseasVac), nauseasDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                nauseasDosis1 = mr.getCatKey();
                spinNauseasDosis1.setBackgroundColor(nauseasDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinBultosDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(BULTOSDOSIS1_CONS, getString(R.string.bultosVac), bultosDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                bultosDosis1 = mr.getCatKey();
                spinBultosDosis1.setBackgroundColor(bultosDosis1.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinOtrosDosis1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(OTROSDOSIS1_CONS, getString(R.string.otrosVac), otrosDosis1, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                otrosDosis1 = mr.getCatKey();
                if(otrosDosis1.equals(Constants.YESKEYSND)){
                    spinOtrosDosis1.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta31_OtroSintomaDosis1(View.VISIBLE);
                } else {
                    spinOtrosDosis1.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta31_OtroSintomaDosis1(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
//dosis2
                spinPresentoSintomasDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PRESENTOSINTOMASDOSIS2_CONS, getString(R.string.presentoSintomasVacuna2), presentoSintomasDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                presentoSintomasDosis2 = mr.getCatKey();
                if(presentoSintomasDosis2.equals(Constants.YESKEYSND)){
                    spinPresentoSintomasDosis2.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta31_SintomasDosis2(View.VISIBLE);
                } else {
                    spinPresentoSintomasDosis2.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta31_SintomasDosis2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorSitioDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORSITIODOSIS2_CONS, getString(R.string.dolorSitioVac), dolorSitioDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorSitioDosis2 = mr.getCatKey();
                spinDolorSitioDosis2.setBackgroundColor(dolorSitioDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFiebreDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FIEBREDOSIS2_CONS, getString(R.string.fiebreVac), fiebreDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                fiebreDosis2 = mr.getCatKey();
                spinFiebreDosis2.setBackgroundColor(fiebreDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinCansancioDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(CANSANCIODOSIS2_CONS, getString(R.string.cansancioVac), cansancioDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                cansancioDosis2 = mr.getCatKey();
                spinCansancioDosis2.setBackgroundColor(cansancioDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorCabezaDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORCABEZADOSIS2_CONS, getString(R.string.dolorCabezaVac), dolorCabezaDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorCabezaDosis2 = mr.getCatKey();
                spinDolorCabezaDosis2.setBackgroundColor(dolorCabezaDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDiarreaDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DIARREADOSIS2_CONS, getString(R.string.diarreaVac), diarreaDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                diarreaDosis2 = mr.getCatKey();
                spinDiarreaDosis2.setBackgroundColor(diarreaDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinVomitoDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(VOMITODOSIS2_CONS, getString(R.string.vomitoVac), vomitoDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                vomitoDosis2 = mr.getCatKey();
                spinVomitoDosis2.setBackgroundColor(vomitoDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorMuscularDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORMUSCULARDOSIS2_CONS, getString(R.string.dolorMuscularVac), dolorMuscularDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorMuscularDosis2 = mr.getCatKey();
                spinDolorMuscularDosis2.setBackgroundColor(dolorMuscularDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinEscalofriosDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(ESCALOFRIOSDOSIS2_CONS, getString(R.string.escalofriosVac), escalofriosDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                escalofriosDosis2 = mr.getCatKey();
                spinEscalofriosDosis2.setBackgroundColor(escalofriosDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinReaccionAlergicaDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(REACCIONALERGICADOSIS2_CONS, getString(R.string.reaccionAlergicaVac), reaccionAlergicaDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                reaccionAlergicaDosis2 = mr.getCatKey();
                spinReaccionAlergicaDosis2.setBackgroundColor(reaccionAlergicaDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinInfeccionSitioDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(INFECCIONSITIODOSIS2_CONS, getString(R.string.infeccionSitioVac), infeccionSitioDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                infeccionSitioDosis2 = mr.getCatKey();
                spinInfeccionSitioDosis2.setBackgroundColor(infeccionSitioDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinNauseasDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(NAUSEASDOSIS2_CONS, getString(R.string.nauseasVac), nauseasDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                nauseasDosis2 = mr.getCatKey();
                spinNauseasDosis2.setBackgroundColor(nauseasDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinBultosDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(BULTOSDOSIS2_CONS, getString(R.string.bultosVac), bultosDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                bultosDosis2 = mr.getCatKey();
                spinBultosDosis2.setBackgroundColor(bultosDosis2.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinOtrosDosis2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(OTROSDOSIS2_CONS, getString(R.string.otrosVac), otrosDosis2, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                otrosDosis2 = mr.getCatKey();
                if(otrosDosis2.equals(Constants.YESKEYSND)){
                    spinOtrosDosis2.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta31_OtroSintomaDosis2(View.VISIBLE);
                } else {
                    spinOtrosDosis2.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta31_OtroSintomaDosis2(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        //dosis3
        spinPresentoSintomasDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(PRESENTOSINTOMASDOSIS3_CONS, getString(R.string.presentoSintomasVacuna3), presentoSintomasDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                presentoSintomasDosis3 = mr.getCatKey();
                if(presentoSintomasDosis3.equals(Constants.YESKEYSND)){
                    spinPresentoSintomasDosis3.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta31_SintomasDosis3(View.VISIBLE);
                } else {
                    spinPresentoSintomasDosis3.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta31_SintomasDosis3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorSitioDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORSITIODOSIS3_CONS, getString(R.string.dolorSitioVac), dolorSitioDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorSitioDosis3 = mr.getCatKey();
                spinDolorSitioDosis3.setBackgroundColor(dolorSitioDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinFiebreDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FIEBREDOSIS3_CONS, getString(R.string.fiebreVac), fiebreDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                fiebreDosis3 = mr.getCatKey();
                spinFiebreDosis3.setBackgroundColor(fiebreDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinCansancioDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(CANSANCIODOSIS3_CONS, getString(R.string.cansancioVac), cansancioDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                cansancioDosis3 = mr.getCatKey();
                spinCansancioDosis3.setBackgroundColor(cansancioDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorCabezaDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORCABEZADOSIS3_CONS, getString(R.string.dolorCabezaVac), dolorCabezaDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorCabezaDosis3 = mr.getCatKey();
                spinDolorCabezaDosis3.setBackgroundColor(dolorCabezaDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDiarreaDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DIARREADOSIS3_CONS, getString(R.string.diarreaVac), diarreaDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                diarreaDosis3 = mr.getCatKey();
                spinDiarreaDosis3.setBackgroundColor(diarreaDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinVomitoDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(VOMITODOSIS3_CONS, getString(R.string.vomitoVac), vomitoDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                vomitoDosis3 = mr.getCatKey();
                spinVomitoDosis3.setBackgroundColor(vomitoDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinDolorMuscularDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(DOLORMUSCULARDOSIS3_CONS, getString(R.string.dolorMuscularVac), dolorMuscularDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                dolorMuscularDosis3 = mr.getCatKey();
                spinDolorMuscularDosis3.setBackgroundColor(dolorMuscularDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinEscalofriosDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(ESCALOFRIOSDOSIS3_CONS, getString(R.string.escalofriosVac), escalofriosDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                escalofriosDosis3 = mr.getCatKey();
                spinEscalofriosDosis3.setBackgroundColor(escalofriosDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinReaccionAlergicaDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(REACCIONALERGICADOSIS3_CONS, getString(R.string.reaccionAlergicaVac), reaccionAlergicaDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                reaccionAlergicaDosis3 = mr.getCatKey();
                spinReaccionAlergicaDosis3.setBackgroundColor(reaccionAlergicaDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinInfeccionSitioDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(INFECCIONSITIODOSIS3_CONS, getString(R.string.infeccionSitioVac), infeccionSitioDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                infeccionSitioDosis3 = mr.getCatKey();
                spinInfeccionSitioDosis3.setBackgroundColor(infeccionSitioDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinNauseasDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(NAUSEASDOSIS3_CONS, getString(R.string.nauseasVac), nauseasDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                nauseasDosis3 = mr.getCatKey();
                spinNauseasDosis3.setBackgroundColor(nauseasDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinBultosDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(BULTOSDOSIS3_CONS, getString(R.string.bultosVac), bultosDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                bultosDosis3 = mr.getCatKey();
                spinBultosDosis3.setBackgroundColor(bultosDosis3.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinOtrosDosis3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(OTROSDOSIS3_CONS, getString(R.string.otrosVac), otrosDosis3, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                otrosDosis3 = mr.getCatKey();
                if(otrosDosis3.equals(Constants.YESKEYSND)){
                    spinOtrosDosis3.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta31_OtroSintomaDosis3(View.VISIBLE);
                } else {
                    spinOtrosDosis3.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta31_OtroSintomaDosis3(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinCovid19PosteriorVacuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(COVID19POSTERIORVACUNA_CONS, getString(R.string.covid19PosteriorVacuna), covid19PosteriorVacuna, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                covid19PosteriorVacuna = mr.getCatKey();
                if(covid19PosteriorVacuna.equals(Constants.YESKEYSND)){
                    spinCovid19PosteriorVacuna.setBackgroundColor(Color.RED);
                    MostrarOcultarPregunta32_A_Fecha(View.VISIBLE);
                } else {
                    spinCovid19PosteriorVacuna.setBackgroundColor(Color.WHITE);
                    MostrarOcultarPregunta32_A_Fecha(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /*spinSabeFechaEnfPostVac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(SABEFECHAENFPOSTVAC_CONS, getString(R.string.sabeFechaEnfPostVac), sabeFechaEnfPostVac, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                sabeFechaEnfPostVac = mr.getCatKey();
                if(sabeFechaEnfPostVac.equals(Constants.YESKEYSND)){
                    spinSabeFechaEnfPostVac.setBackgroundColor(Color.RED);
                    //MostrarOcultarPregunta32_FIS(View.VISIBLE);
                    //MostrarOcultarPregunta32_MesAnio(View.GONE);
                    //anioEnfPostVac = null;
                    //mesEnfPostVac = null;
                }
                else if(sabeFechaEnfPostVac.equals(Constants.NOKEYSND)){
                    spinSabeFechaEnfPostVac.setBackgroundColor(Color.WHITE);
                    //MostrarOcultarPregunta32_FIS(View.GONE);
                    //MostrarOcultarPregunta32_MesAnio(View.VISIBLE);
                    //anioEnfPostVac = inputAnioEnfPostVac.getText().toString();
                    //fechaEnfPostVac = null;
                } else {
                    spinSabeFechaEnfPostVac.setBackgroundColor(Color.WHITE);
                    //MostrarOcultarPregunta32_MesAnio(View.GONE);
                    //MostrarOcultarPregunta32_FIS(View.GONE);
                    //anioEnfPostVac = null;
                    //mesEnfPostVac = null;
                    //fechaEnfPostVac = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });*/

        spinFechaEventoEnfermoPostVac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(FECHAEVENTOENFERMOPOSTVAC_CONS, getString(R.string.fechaEnfPostVac), fechaEventoEnfermoPostVac, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                fechaEventoEnfermoPostVac = mr.getCatKey();
                spinFechaEventoEnfermoPostVac.setBackgroundColor(fechaEventoEnfermoPostVac.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /*spinMesEnfPostVac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                //Pedir confirmación para cada respuesta
                createConfirmDialog(MESFECHAENFPOSTVAC_CONS, getString(R.string.mesEvento), mesEnfPostVac, mr.getCatKey(), mr.getSpanish(), spinner.getSelectedItemPosition());
                mostrarConfirmacion = true;
                mesEnfPostVac = mr.getCatKey();
                spinMesEnfPostVac.setBackgroundColor(mesEnfPostVac.equals(Constants.YESKEYSND) ? Color.RED : Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });*/

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

    /*private boolean tuvoAlMenosUnSintoma(){
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
    }*/

    private boolean tuvoAlMenosUnSintomaE1(){
        if (e1Febricula!=null && e1Febricula.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1Fiebre!=null && e1Fiebre.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1TemblorEscalofrio!=null && e1TemblorEscalofrio.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1Escalofrio!=null && e1Escalofrio.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1DolorMuscular!=null && e1DolorMuscular.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1DolorArticular!=null && e1DolorArticular.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1SecresionNasal!=null && e1SecresionNasal.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1DolorGarganta!=null && e1DolorGarganta.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1Tos!=null && e1Tos.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1DificultadResp!=null && e1DificultadResp.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1DolorPecho!=null && e1DolorPecho.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1NauseasVomito!=null && e1NauseasVomito.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1DolorCabeza!=null && e1DolorCabeza.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1DolorAbdominal!=null && e1DolorAbdominal.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1Diarrea!=null && e1Diarrea.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1DificultadDormir!=null && e1DificultadDormir.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1Debilidad!=null && e1Debilidad.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1PerdidaOlfatoGusto!=null && e1PerdidaOlfatoGusto.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1Mareo!=null && e1Mareo.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1Sarpullido!=null && e1Sarpullido.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1Desmayo!=null && e1Desmayo.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e1QuedoCama!=null && e1QuedoCama.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        return false;
    }

    private boolean tuvoAlMenosUnSintomaE2(){
        if (e2Febricula!=null && e2Febricula.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2Fiebre!=null && e2Fiebre.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2TemblorEscalofrio!=null && e2TemblorEscalofrio.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2Escalofrio!=null && e2Escalofrio.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2DolorMuscular!=null && e2DolorMuscular.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2DolorArticular!=null && e2DolorArticular.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2SecresionNasal!=null && e2SecresionNasal.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2DolorGarganta!=null && e2DolorGarganta.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2Tos!=null && e2Tos.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2DificultadResp!=null && e2DificultadResp.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2DolorPecho!=null && e2DolorPecho.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2NauseasVomito!=null && e2NauseasVomito.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2DolorCabeza!=null && e2DolorCabeza.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2DolorAbdominal!=null && e2DolorAbdominal.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2Diarrea!=null && e2Diarrea.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2DificultadDormir!=null && e2DificultadDormir.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2Debilidad!=null && e2Debilidad.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2PerdidaOlfatoGusto!=null && e2PerdidaOlfatoGusto.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2Mareo!=null && e2Mareo.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2Sarpullido!=null && e2Sarpullido.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2Desmayo!=null && e2Desmayo.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e2QuedoCama!=null && e2QuedoCama.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        return false;
    }

    private boolean tuvoAlMenosUnSintomaE3(){
        if (e3Febricula!=null && e3Febricula.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3Fiebre!=null && e3Fiebre.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3TemblorEscalofrio!=null && e3TemblorEscalofrio.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3Escalofrio!=null && e3Escalofrio.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3DolorMuscular!=null && e3DolorMuscular.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3DolorArticular!=null && e3DolorArticular.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3SecresionNasal!=null && e3SecresionNasal.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3DolorGarganta!=null && e3DolorGarganta.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3Tos!=null && e3Tos.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3DificultadResp!=null && e3DificultadResp.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3DolorPecho!=null && e3DolorPecho.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3NauseasVomito!=null && e3NauseasVomito.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3DolorCabeza!=null && e3DolorCabeza.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3DolorAbdominal!=null && e3DolorAbdominal.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3Diarrea!=null && e3Diarrea.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3DificultadDormir!=null && e3DificultadDormir.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3Debilidad!=null && e3Debilidad.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3PerdidaOlfatoGusto!=null && e3PerdidaOlfatoGusto.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3Mareo!=null && e3Mareo.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3Sarpullido!=null && e3Sarpullido.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3Desmayo!=null && e3Desmayo.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        if (e3QuedoCama!=null && e3QuedoCama.equalsIgnoreCase(Constants.YESKEYSND)) return true;
        return false;
    }

    private void MostrarOcultarPregunta1a(int estado){
        if (estado == View.GONE) spinCuantasVecesEnfermo.setSelection(0, false);
        textCuantasVecesEnfermo.setVisibility(estado);
        spinCuantasVecesEnfermo.setVisibility(estado);
    }

    /*¿Presentó alguno de los siguientes síntomas durante este evento?**/
    private void MostrarOcultarPreguntaE1(int estado) {
        if (estado == View.GONE) {
            spinSabeEvento1.setSelection(0, false);
            spinE1Febricula.setSelection(0, false);
            spinE1Fiebre.setSelection(0, false);
            spinE1Escalofrio.setSelection(0, false);
            spinE1TemblorEscalofrio.setSelection(0, false);
            spinE1DolorMuscular.setSelection(0, false);
            spinE1DolorArticular.setSelection(0, false);
            spinE1SecresionNasal.setSelection(0, false);
            spinE1DolorGarganta.setSelection(0, false);
            spinE1Tos.setSelection(0, false);
            spinE1DificultadResp.setSelection(0, false);
            spinE1DolorPecho.setSelection(0, false);
            spinE1NauseasVomito.setSelection(0, false);
            spinE1DolorCabeza.setSelection(0, false);
            spinE1DolorAbdominal.setSelection(0, false);
            spinE1Diarrea.setSelection(0, false);
            spinE1DificultadDormir.setSelection(0, false);
            spinE1Debilidad.setSelection(0, false);
            spinE1PerdidaOlfatoGusto.setSelection(0, false);
            spinE1Mareo.setSelection(0, false);
            spinE1Sarpullido.setSelection(0, false);
            spinE1Desmayo.setSelection(0, false);
            spinE1QuedoCama.setSelection(0, false);
        }
        textSabeEvento1.setVisibility(estado);
        spinSabeEvento1.setVisibility(estado);
        textEvento1.setVisibility(estado);
        textEvento1.setVisibility(estado);
        textDesdeSintomasE1.setVisibility(estado);
        textE1Febricula.setVisibility(estado);
        textE1Fiebre.setVisibility(estado);
        textE1Escalofrio.setVisibility(estado);
        textE1TemblorEscalofrio.setVisibility(estado);
        textE1DolorMuscular.setVisibility(estado);
        textE1DolorArticular.setVisibility(estado);
        textE1SecresionNasal.setVisibility(estado);
        textE1DolorGarganta.setVisibility(estado);
        textE1Tos.setVisibility(estado);
        textE1DificultadResp.setVisibility(estado);
        textE1DolorPecho.setVisibility(estado);
        textE1NauseasVomito.setVisibility(estado);
        textE1DolorCabeza.setVisibility(estado);
        textE1DolorAbdominal.setVisibility(estado);
        textE1Diarrea.setVisibility(estado);
        textE1DificultadDormir.setVisibility(estado);
        textE1Debilidad.setVisibility(estado);
        textE1PerdidaOlfatoGusto.setVisibility(estado);
        textE1Mareo.setVisibility(estado);
        textE1Sarpullido.setVisibility(estado);
        textE1Desmayo.setVisibility(estado);
        textE1QuedoCama.setVisibility(estado);
        spinE1Febricula.setVisibility(estado);
        spinE1Fiebre.setVisibility(estado);
        spinE1Escalofrio.setVisibility(estado);
        spinE1TemblorEscalofrio.setVisibility(estado);
        spinE1DolorMuscular.setVisibility(estado);
        spinE1DolorArticular.setVisibility(estado);
        spinE1SecresionNasal.setVisibility(estado);
        spinE1DolorGarganta.setVisibility(estado);
        spinE1Tos.setVisibility(estado);
        spinE1DificultadResp.setVisibility(estado);
        spinE1DolorPecho.setVisibility(estado);
        spinE1NauseasVomito.setVisibility(estado);
        spinE1DolorCabeza.setVisibility(estado);
        spinE1DolorAbdominal.setVisibility(estado);
        spinE1Diarrea.setVisibility(estado);
        spinE1DificultadDormir.setVisibility(estado);
        spinE1Debilidad.setVisibility(estado);
        spinE1PerdidaOlfatoGusto.setVisibility(estado);
        spinE1Mareo.setVisibility(estado);
        spinE1Sarpullido.setVisibility(estado);
        spinE1Desmayo.setVisibility(estado);
        spinE1QuedoCama.setVisibility(estado);
        MostrarOcultarPregunta4E1(estado);
        MostrarOcultarPregunta5E1(estado);
        MostrarOcultarPregunta15E1(estado);
        MostrarOcultarPregunta18E1(estado);
        MostrarOcultarPregunta19E1(estado);
        MostrarOcultarPregunta20E1(estado);
        if (participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) {//solo si es mujer entre 18 y 50
            MostrarOcultarPregunta26E1(estado);
            //MostrarOcultarPregunta27E1(estado);
        } else {
            MostrarOcultarPregunta26E1(View.GONE);
            //MostrarOcultarPregunta27E1(View.GONE);
        }
    }

    /*¿Presentó alguno de los siguientes síntomas durante este evento?**/
    private void MostrarOcultarPreguntaE2(int estado) {
        if (estado == View.GONE) {
            spinSabeEvento2.setSelection(0, false);
            spinE2Febricula.setSelection(0, false);
            spinE2Fiebre.setSelection(0, false);
            spinE2Escalofrio.setSelection(0, false);
            spinE2TemblorEscalofrio.setSelection(0, false);
            spinE2DolorMuscular.setSelection(0, false);
            spinE2DolorArticular.setSelection(0, false);
            spinE2SecresionNasal.setSelection(0, false);
            spinE2DolorGarganta.setSelection(0, false);
            spinE2Tos.setSelection(0, false);
            spinE2DificultadResp.setSelection(0, false);
            spinE2DolorPecho.setSelection(0, false);
            spinE2NauseasVomito.setSelection(0, false);
            spinE2DolorCabeza.setSelection(0, false);
            spinE2DolorAbdominal.setSelection(0, false);
            spinE2Diarrea.setSelection(0, false);
            spinE2DificultadDormir.setSelection(0, false);
            spinE2Debilidad.setSelection(0, false);
            spinE2PerdidaOlfatoGusto.setSelection(0, false);
            spinE2Mareo.setSelection(0, false);
            spinE2Sarpullido.setSelection(0, false);
            spinE2Desmayo.setSelection(0, false);
            spinE2QuedoCama.setSelection(0, false);
        }
        textSabeEvento2.setVisibility(estado);
        spinSabeEvento2.setVisibility(estado);
        textEvento2.setVisibility(estado);
        textDesdeSintomasE2.setVisibility(estado);
        textE2Febricula.setVisibility(estado);
        textE2Fiebre.setVisibility(estado);
        textE2Escalofrio.setVisibility(estado);
        textE2TemblorEscalofrio.setVisibility(estado);
        textE2DolorMuscular.setVisibility(estado);
        textE2DolorArticular.setVisibility(estado);
        textE2SecresionNasal.setVisibility(estado);
        textE2DolorGarganta.setVisibility(estado);
        textE2Tos.setVisibility(estado);
        textE2DificultadResp.setVisibility(estado);
        textE2DolorPecho.setVisibility(estado);
        textE2NauseasVomito.setVisibility(estado);
        textE2DolorCabeza.setVisibility(estado);
        textE2DolorAbdominal.setVisibility(estado);
        textE2Diarrea.setVisibility(estado);
        textE2DificultadDormir.setVisibility(estado);
        textE2Debilidad.setVisibility(estado);
        textE2PerdidaOlfatoGusto.setVisibility(estado);
        textE2Mareo.setVisibility(estado);
        textE2Sarpullido.setVisibility(estado);
        textE2Desmayo.setVisibility(estado);
        textE2QuedoCama.setVisibility(estado);
        spinE2Febricula.setVisibility(estado);
        spinE2Fiebre.setVisibility(estado);
        spinE2Escalofrio.setVisibility(estado);
        spinE2TemblorEscalofrio.setVisibility(estado);
        spinE2DolorMuscular.setVisibility(estado);
        spinE2DolorArticular.setVisibility(estado);
        spinE2SecresionNasal.setVisibility(estado);
        spinE2DolorGarganta.setVisibility(estado);
        spinE2Tos.setVisibility(estado);
        spinE2DificultadResp.setVisibility(estado);
        spinE2DolorPecho.setVisibility(estado);
        spinE2NauseasVomito.setVisibility(estado);
        spinE2DolorCabeza.setVisibility(estado);
        spinE2DolorAbdominal.setVisibility(estado);
        spinE2Diarrea.setVisibility(estado);
        spinE2DificultadDormir.setVisibility(estado);
        spinE2Debilidad.setVisibility(estado);
        spinE2PerdidaOlfatoGusto.setVisibility(estado);
        spinE2Mareo.setVisibility(estado);
        spinE2Sarpullido.setVisibility(estado);
        spinE2Desmayo.setVisibility(estado);
        spinE2QuedoCama.setVisibility(estado);
        MostrarOcultarPregunta4E2(estado);
        MostrarOcultarPregunta5E2(estado);
        MostrarOcultarPregunta15E2(estado);
        MostrarOcultarPregunta18E2(estado);
        MostrarOcultarPregunta19E2(estado);
        MostrarOcultarPregunta20E2(estado);
        if (participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) {//solo si es mujer entre 18 y 50
            MostrarOcultarPregunta26E2(estado);
            //MostrarOcultarPregunta27E2(estado);
        } else {
            MostrarOcultarPregunta26E2(View.GONE);
            //MostrarOcultarPregunta27E2(View.GONE);
        }
    }

    /*¿Presentó alguno de los siguientes síntomas durante este evento?**/
    private void MostrarOcultarPreguntaE3(int estado) {
        if (estado == View.GONE) {
            spinSabeEvento3.setSelection(0, false);
            spinE3Febricula.setSelection(0, false);
            spinE3Fiebre.setSelection(0, false);
            spinE3Escalofrio.setSelection(0, false);
            spinE3TemblorEscalofrio.setSelection(0, false);
            spinE3DolorMuscular.setSelection(0, false);
            spinE3DolorArticular.setSelection(0, false);
            spinE3SecresionNasal.setSelection(0, false);
            spinE3DolorGarganta.setSelection(0, false);
            spinE3Tos.setSelection(0, false);
            spinE3DificultadResp.setSelection(0, false);
            spinE3DolorPecho.setSelection(0, false);
            spinE3NauseasVomito.setSelection(0, false);
            spinE3DolorCabeza.setSelection(0, false);
            spinE3DolorAbdominal.setSelection(0, false);
            spinE3Diarrea.setSelection(0, false);
            spinE3DificultadDormir.setSelection(0, false);
            spinE3Debilidad.setSelection(0, false);
            spinE3PerdidaOlfatoGusto.setSelection(0, false);
            spinE3Mareo.setSelection(0, false);
            spinE3Sarpullido.setSelection(0, false);
            spinE3Desmayo.setSelection(0, false);
            spinE3QuedoCama.setSelection(0, false);
        }
        textSabeEvento3.setVisibility(estado);
        spinSabeEvento3.setVisibility(estado);
        textEvento1.setVisibility(estado);
        textDesdeSintomasE3.setVisibility(estado);
        textE3Febricula.setVisibility(estado);
        textE3Fiebre.setVisibility(estado);
        textE3Escalofrio.setVisibility(estado);
        textE3TemblorEscalofrio.setVisibility(estado);
        textE3DolorMuscular.setVisibility(estado);
        textE3DolorArticular.setVisibility(estado);
        textE3SecresionNasal.setVisibility(estado);
        textE3DolorGarganta.setVisibility(estado);
        textE3Tos.setVisibility(estado);
        textE3DificultadResp.setVisibility(estado);
        textE3DolorPecho.setVisibility(estado);
        textE3NauseasVomito.setVisibility(estado);
        textE3DolorCabeza.setVisibility(estado);
        textE3DolorAbdominal.setVisibility(estado);
        textE3Diarrea.setVisibility(estado);
        textE3DificultadDormir.setVisibility(estado);
        textE3Debilidad.setVisibility(estado);
        textE3PerdidaOlfatoGusto.setVisibility(estado);
        textE3Mareo.setVisibility(estado);
        textE3Sarpullido.setVisibility(estado);
        textE3Desmayo.setVisibility(estado);
        textE3QuedoCama.setVisibility(estado);
        spinE3Febricula.setVisibility(estado);
        spinE3Fiebre.setVisibility(estado);
        spinE3Escalofrio.setVisibility(estado);
        spinE3TemblorEscalofrio.setVisibility(estado);
        spinE3DolorMuscular.setVisibility(estado);
        spinE3DolorArticular.setVisibility(estado);
        spinE3SecresionNasal.setVisibility(estado);
        spinE3DolorGarganta.setVisibility(estado);
        spinE3Tos.setVisibility(estado);
        spinE3DificultadResp.setVisibility(estado);
        spinE3DolorPecho.setVisibility(estado);
        spinE3NauseasVomito.setVisibility(estado);
        spinE3DolorCabeza.setVisibility(estado);
        spinE3DolorAbdominal.setVisibility(estado);
        spinE3Diarrea.setVisibility(estado);
        spinE3DificultadDormir.setVisibility(estado);
        spinE3Debilidad.setVisibility(estado);
        spinE3PerdidaOlfatoGusto.setVisibility(estado);
        spinE3Mareo.setVisibility(estado);
        spinE3Sarpullido.setVisibility(estado);
        spinE3Desmayo.setVisibility(estado);
        spinE3QuedoCama.setVisibility(estado);
        MostrarOcultarPregunta4E3(estado);
        MostrarOcultarPregunta5E3(estado);
        MostrarOcultarPregunta15E3(estado);
        MostrarOcultarPregunta18E3(estado);
        MostrarOcultarPregunta19E3(estado);
        MostrarOcultarPregunta20E3(estado);
        if (participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) {//solo si es mujer entre 18 y 50
            MostrarOcultarPregunta26E3(estado);
            //MostrarOcultarPregunta27E3(estado);
        } else {
            MostrarOcultarPregunta26E3(View.GONE);
            //MostrarOcultarPregunta27E3(View.GONE);
        }
    }

    private void MostrarOcultarE1_FechaCompleta(int estado){
        layoutEvento1.setVisibility(estado);
        textEvento1.setVisibility(estado);
        inputEvento1.setVisibility(estado);
        imbEvento1.setVisibility(estado);
    }

    private void MostrarOcultarE2_FechaCompleta(int estado){
        layoutEvento2.setVisibility(estado);
        textEvento2.setVisibility(estado);
        inputEvento2.setVisibility(estado);
        imbEvento2.setVisibility(estado);
    }

    private void MostrarOcultarE3_FechaCompleta(int estado){
        layoutEvento3.setVisibility(estado);
        textEvento3.setVisibility(estado);
        inputEvento3.setVisibility(estado);
        imbEvento3.setVisibility(estado);
    }

    private void MostrarOcultarE1_MesAnio(int estado){
        if (estado == View.GONE) spinMesEvento1.setSelection(0, false);
        textMesEvento1.setVisibility(estado);
        spinMesEvento1.setVisibility(estado);
        textAnioEvento1.setVisibility(estado);
        inputAnioEvento1.setVisibility(estado);
    }

    private void MostrarOcultarE2_MesAnio(int estado){
        if (estado == View.GONE) spinMesEvento2.setSelection(0, false);
        textMesEvento2.setVisibility(estado);
        spinMesEvento2.setVisibility(estado);
        textAnioEvento2.setVisibility(estado);
        inputAnioEvento2.setVisibility(estado);
    }

    private void MostrarOcultarE3_MesAnio(int estado){
        if (estado == View.GONE) spinMesEvento3.setSelection(0, false);
        textMesEvento3.setVisibility(estado);
        spinMesEvento3.setVisibility(estado);
        textAnioEvento3.setVisibility(estado);
        inputAnioEvento3.setVisibility(estado);
    }

    /*Que fecha exacta o aproximada empezaron estos síntomas*/
    private void MostrarOcultarPregunta3E1(){
        if (tuvoAlMenosUnSintomaE1()) {
            layoutE1FIS.setVisibility(View.VISIBLE);
            spinE1SabeInicioSintoma.setVisibility(View.VISIBLE);
            textE1SabeInicioSintoma.setVisibility(View.VISIBLE);
        }else {
            layoutE1FIS.setVisibility(View.GONE);
            spinE1SabeInicioSintoma.setSelection(0, false);
            spinE1SabeInicioSintoma.setVisibility(View.GONE);
            textE1SabeInicioSintoma.setVisibility(View.GONE);
        }
    }
    
    private void MostrarOcultarPregunta3_MesAnioE1(int estado){
        if (estado == View.GONE) spinE1MesInicioSintoma.setSelection(0, false);
        textE1MesInicioSintoma.setVisibility(estado);
        spinE1MesInicioSintoma.setVisibility(estado);
        textE1AnioInicioSintoma.setVisibility(estado);
        inputE1AnioInicioSintoma.setVisibility(estado);
    }

    private void MostrarOcultarPregunta3_FISE1(int estado){
        layoutE1FIS.setVisibility(estado);
        textE1FIS.setVisibility(estado);
        inputE1FIS.setVisibility(estado);
        imbE1FIS.setVisibility(estado);
    }

    /*4. ¿Conoce en donde usted o su niño/a estuvieron expuestos a COVID-19?*/
    private void MostrarOcultarPregunta4E1(int estado){
        if (estado == View.GONE) spinE1ConoceLugarExposicion.setSelection(0, false);
        textE1ConoceLugarExposicion.setVisibility(estado);
        spinE1ConoceLugarExposicion.setVisibility(estado);
    }
    /*5. ¿Busco ayuda médica?*/
    private void MostrarOcultarPregunta5E1(int estado){
        if (estado == View.GONE) spinE1BuscoAyuda.setSelection(0, false);
        textE1BuscoAyuda.setVisibility(estado);
        spinE1BuscoAyuda.setVisibility(estado);
    }
    /*6. [Si P6==”Si”] Donde busco ayuda médica?*/
    private void MostrarOcultarPregunta6E1(int estado){
        if (estado == View.GONE) spinE1DondeBuscoAyuda.setSelection(0, false);
        textE1DondeBuscoAyuda.setVisibility(estado);
        spinE1DondeBuscoAyuda.setVisibility(estado);
    }

    private void MostrarOcultarPregunta6AE1(int estado){
        if (estado == View.GONE) spinE1RecibioSeguimiento.setSelection(0, false);
        textE1RecibioSeguimiento.setVisibility(estado);
        spinE1RecibioSeguimiento.setVisibility(estado);
    }

    private void MostrarOcultarPregunta6ASiE1(int estado){
        if (estado == View.GONE) spinE1TipoSeguimiento.setSelection(0, false);
        textE1TipoSeguimiento.setVisibility(estado);
        spinE1TipoSeguimiento.setVisibility(estado);
    }
    /*7. [Si P6==”Si”] Cuanto tiempo después de que iniciaron los síntomas de COVID-19 busco ayuda médica?*/
    private void MostrarOcultarPregunta7E1(int estado){
        if (estado == View.GONE) spinE1TmpDespuesBuscoAyuda.setSelection(0, false);
        textE1TmpDespuesBuscoAyuda.setVisibility(estado);
        spinE1TmpDespuesBuscoAyuda.setVisibility(estado);
    }
    /*8. [Si P6==”Si”] Paso al menos una noche en el hospital por COVID-19?*/
    private void MostrarOcultarPregunta8E1(int estado){
        if (estado == View.GONE) spinE1UnaNocheHospital.setSelection(0, false);
        textE1UnaNocheHospital.setVisibility(estado);
        spinE1UnaNocheHospital.setVisibility(estado);
    }
    /*9. [Si P8==”Si”] ¿A qué hospital acudió?*/
    private void MostrarOcultarPregunta9E1(int estado){
        if (estado == View.GONE) inputE1QueHospital.setText("");
        textE1QueHospital.setVisibility(estado);
        inputE1QueHospital.setVisibility(estado);
    }
    /*10. [Si P8 == “Si”] Cuantas noches estuvo en el hospital?*/
    private void MostrarOcultarPregunta10E1(int estado){
        if (estado == View.GONE) spinE1SabeCuantasNoches.setSelection(0, false);
        textE1SabeCuantasNoches.setVisibility(estado);
        spinE1SabeCuantasNoches.setVisibility(estado);
    }
    /*11. [Si P8== “Si”] Cual fue su fecha de admisión al hospital?*/
    private void MostrarOcultarPregunta11E1(int estado){
        if (estado == View.GONE) spinE1SabeFechaAdmision.setSelection(0, false);
        textE1SabeFechaAdmision.setVisibility(estado);
        spinE1SabeFechaAdmision.setVisibility(estado);
    }
    /*12. [Si P8 == “Si”] Cual fue su fecha de alta médica?*/
    private void MostrarOcultarPregunta12E1(int estado){
        if (estado == View.GONE) spinE1SabeFechaAlta.setSelection(0, false);
        textE1SabeFechaAlta.setVisibility(estado);
        spinE1SabeFechaAlta.setVisibility(estado);
    }
    /*13. [Si P8==”Si”] Mientras estuvo en el hospital, requirió oxigeno?*/
    private void MostrarOcultarPregunta13E1(int estado){
        if (estado == View.GONE) spinE1UtilizoOxigeno.setSelection(0, false);
        textE1UtilizoOxigeno.setVisibility(estado);
        spinE1UtilizoOxigeno.setVisibility(estado);
    }
    /*14. [Si P8==”Si”] Mientras usted o su niño/a estuvo en el hospital, en algún momento requirió de Unidad de Cuidados Intensivos?*/
    private void MostrarOcultarPregunta14E1(int estado){
        if (estado == View.GONE) spinE1EstuvoUCI.setSelection(0, false);
        textE1EstuvoUCI.setVisibility(estado);
        spinE1EstuvoUCI.setVisibility(estado);
    }
    /*15. [Si la P4 == “Si”] Se ha recuperado del COVID-19 y posee ahora un estado de salud normal o igual al que tenía antes de enfermedad?*/
    private void MostrarOcultarPregunta15E1(int estado){
        if (estado == View.GONE) spinE1RecuperadoCovid19.setSelection(0, false);
        textE1RecuperadoCovid19.setVisibility(estado);
        spinE1RecuperadoCovid19.setVisibility(estado);
    }

    /*16. [Si P16 == “No” o “No Sabe/No está seguro” /no contesto] ¿Usted o su niño/a que síntomas presenta aun?*/
    private void MostrarOcultarPregunta16E1(int estado){
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
    /*17. [Si P16 == “Si”] ¿A usted o su niño/a cuánto tiempo le tomo en recuperarse de los síntomas secundarios a COVID-19 y recobrar su salud normal?*/
    private void MostrarOcultarPregunta17E1(int estado){
        if (estado == View.GONE) spinE1SabeTiempoRecuperacion.setSelection(0, false);
        textE1SabeTiempoRecuperacion.setVisibility(estado);
        spinE1SabeTiempoRecuperacion.setVisibility(estado);
    }

    private void MostrarOcultarPregunta17AE1(int estado){
        if (estado == View.GONE) spinE1TiempoRecuperacionEn.setSelection(0, false);
        textE1TiempoRecuperacion.setVisibility(estado);
        inputE1TiempoRecuperacion.setVisibility(estado);
        spinE1TiempoRecuperacionEn.setVisibility(estado);
    }
    /*18. [Si P1==”Si”] En el momento más severo de su enfermedad, diría que su enfermedad o la de su niño/a fue?*/
    private void MostrarOcultarPregunta18E1(int estado){
        if (estado == View.GONE) spinE1SeveridadEnfermedad.setSelection(0, false);
        textE1SeveridadEnfermedad.setVisibility(estado);
        spinE1SeveridadEnfermedad.setVisibility(estado);
    }

    private void MostrarOcultarPregunta19E1(int estado){
        if (estado == View.GONE) spinE1TomoMedicamento.setSelection(0, false);
        textE1TomoMedicamento.setVisibility(estado);
        spinE1TomoMedicamento.setVisibility(estado);
    }

    private void MostrarOcultarPregunta20E1(int estado){
        if (estado == View.GONE) spinE1OxigenoDomicilio.setSelection(0, false);
        textE1OxigenoDomicilio.setVisibility(estado);
        spinE1OxigenoDomicilio.setVisibility(estado);
    }

    private void MostrarOcultarPregunta20AE1(int estado){
        if (estado == View.GONE) spinE1TiempoOxigenoDom.setSelection(0, false);
        textE1TiempoOxigenoDom.setVisibility(estado);
        spinE1TiempoOxigenoDom.setVisibility(estado);
    }

    private void MostrarOcultarPregunta19AE1(int estado){
        textE1QueMedicamento.setVisibility(estado);
        spinE1QueMedicamento.setVisibility(estado);
    }
    /*23 ¿Ha fumado al menos 100 cigarrillos en su vida?*/
    private void MostrarOcultarPregunta23(int estado){
        if (estado == View.GONE) spinFumadoCienCigarrillos.setSelection(0, false);
        textFumadoCienCigarrillos.setVisibility(estado);
        spinFumadoCienCigarrillos.setVisibility(estado);
    }
    /*24[Si P3==”Si”] ¿En los días previos a que se enfermara, usted fumaba cigarrillos todos los días o algunos días previo a que se enfermara?*/
    private void MostrarOcultarPregunta24E1(int estado){
        if (estado == View.GONE) spinE1FumadoPrevioEnfermedad.setSelection(0, false);
        textE1FumadoPrevioEnfermedad.setVisibility(estado);
        spinE1FumadoPrevioEnfermedad.setVisibility(estado);
    }
    /*25.¿Usted fumaba cigarrillos todos los días o algunos días ahora?*/
    private void MostrarOcultarPregunta25E1(int estado){
        if (estado == View.GONE) spinE1FumaActualmente.setSelection(0, false);
        textE1FumaActualmente.setVisibility(estado);
        spinE1FumaActualmente.setVisibility(estado);
    }

    /*26 ¿Usted estaba embarazada al momento de ser diagnosticada o sospechosa de COVID19?*/
    private void MostrarOcultarPregunta26E1(int estado){
        if (estado == View.GONE) spinE1Embarazada.setSelection(0, false);
        textE1Embarazada.setVisibility(estado);
        spinE1Embarazada.setVisibility(estado);
    }
    /*Recuerda las semanas de embarazo que tenia**/
    private void MostrarOcultarPregunta26AE1(int estado){
        if (estado == View.GONE) spinE1RecuerdaSemanasEmb.setSelection(0, false);
        textE1RecuerdaSemanasEmb.setVisibility(estado);
        spinE1RecuerdaSemanasEmb.setVisibility(estado);
    }

    private void MostrarOcultarPregunta26A1E1(int estado){
        textE1SemanasEmbarazo.setVisibility(estado);
        inputE1SemanasEmbarazo.setVisibility(estado);
    }
    /*como finalizo el embarazo*/
    private void MostrarOcultarPregunta26BE1(int estado){
        if (estado == View.GONE) spinE1FinalEmbarazo.setSelection(0, false);
        textE1FinalEmbarazo.setVisibility(estado);
        spinE1FinalEmbarazo.setVisibility(estado);
    }

    private void MostrarOcultarPregunta26B1E1(int estado){
        textE1OtroFinalEmbarazo.setVisibility(estado);
        inputE1OtroFinalEmbarazo.setVisibility(estado);
    }

    /*27. Si estaba embarazada recientemente al momento de ser diagnosticada con COVID19, ¿Usted estaba dando pecho materno?*/
    private void MostrarOcultarPregunta27E1(int estado){
        if (estado == View.GONE) spinE1DabaPecho.setSelection(0, false);
        textE1DabaPecho.setVisibility(estado);
        spinE1DabaPecho.setVisibility(estado);
    }

    //evento2
    /*Que fecha exacta o aproximada empezaron estos síntomas*/
    private void MostrarOcultarPregunta3E2(){
        if (tuvoAlMenosUnSintomaE2()) {
            layoutE2FIS.setVisibility(View.VISIBLE);
            spinE2SabeInicioSintoma.setVisibility(View.VISIBLE);
            textE2SabeInicioSintoma.setVisibility(View.VISIBLE);
        }else {
            layoutE2FIS.setVisibility(View.GONE);
            spinE2SabeInicioSintoma.setSelection(0, false);
            spinE2SabeInicioSintoma.setVisibility(View.GONE);
            textE2SabeInicioSintoma.setVisibility(View.GONE);
        }
    }

    private void MostrarOcultarPregunta3_MesAnioE2(int estado){
        if (estado == View.GONE) spinE2MesInicioSintoma.setSelection(0, false);
        textE2MesInicioSintoma.setVisibility(estado);
        spinE2MesInicioSintoma.setVisibility(estado);
        textE2AnioInicioSintoma.setVisibility(estado);
        inputE2AnioInicioSintoma.setVisibility(estado);
    }

    private void MostrarOcultarPregunta3_FISE2(int estado){
        layoutE2FIS.setVisibility(estado);
        textE2FIS.setVisibility(estado);
        inputE2FIS.setVisibility(estado);
        imbE2FIS.setVisibility(estado);
    }

    /*4. ¿Conoce en donde usted o su niño/a estuvieron expuestos a COVID-19?*/
    private void MostrarOcultarPregunta4E2(int estado){
        if (estado == View.GONE) spinE2ConoceLugarExposicion.setSelection(0, false);
        textE2ConoceLugarExposicion.setVisibility(estado);
        spinE2ConoceLugarExposicion.setVisibility(estado);
    }
    /*5. ¿Busco ayuda médica?*/
    private void MostrarOcultarPregunta5E2(int estado){
        if (estado == View.GONE) spinE2BuscoAyuda.setSelection(0, false);
        textE2BuscoAyuda.setVisibility(estado);
        spinE2BuscoAyuda.setVisibility(estado);
    }
    /*6. [Si P6==”Si”] Donde busco ayuda médica?*/
    private void MostrarOcultarPregunta6E2(int estado){
        if (estado == View.GONE) spinE2DondeBuscoAyuda.setSelection(0, false);
        textE2DondeBuscoAyuda.setVisibility(estado);
        spinE2DondeBuscoAyuda.setVisibility(estado);
    }

    private void MostrarOcultarPregunta6AE2(int estado){
        if (estado == View.GONE) spinE2RecibioSeguimiento.setSelection(0, false);
        textE2RecibioSeguimiento.setVisibility(estado);
        spinE2RecibioSeguimiento.setVisibility(estado);
    }

    private void MostrarOcultarPregunta6ASiE2(int estado){
        if (estado == View.GONE) spinE2TipoSeguimiento.setSelection(0, false);
        textE2TipoSeguimiento.setVisibility(estado);
        spinE2TipoSeguimiento.setVisibility(estado);
    }
    /*7. [Si P6==”Si”] Cuanto tiempo después de que iniciaron los síntomas de COVID-19 busco ayuda médica?*/
    private void MostrarOcultarPregunta7E2(int estado){
        if (estado == View.GONE) spinE2TmpDespuesBuscoAyuda.setSelection(0, false);
        textE2TmpDespuesBuscoAyuda.setVisibility(estado);
        spinE2TmpDespuesBuscoAyuda.setVisibility(estado);
    }
    /*8. [Si P6==”Si”] Paso al menos una noche en el hospital por COVID-19?*/
    private void MostrarOcultarPregunta8E2(int estado){
        if (estado == View.GONE) spinE2UnaNocheHospital.setSelection(0, false);
        textE2UnaNocheHospital.setVisibility(estado);
        spinE2UnaNocheHospital.setVisibility(estado);
    }
    /*9. [Si P8==”Si”] ¿A qué hospital acudió?*/
    private void MostrarOcultarPregunta9E2(int estado){
        if (estado == View.GONE) inputE2QueHospital.setText("");
        textE2QueHospital.setVisibility(estado);
        inputE2QueHospital.setVisibility(estado);
    }
    /*10. [Si P8 == “Si”] Cuantas noches estuvo en el hospital?*/
    private void MostrarOcultarPregunta10E2(int estado){
        if (estado == View.GONE) spinE2SabeCuantasNoches.setSelection(0, false);
        textE2SabeCuantasNoches.setVisibility(estado);
        spinE2SabeCuantasNoches.setVisibility(estado);
    }
    /*11. [Si P8== “Si”] Cual fue su fecha de admisión al hospital?*/
    private void MostrarOcultarPregunta11E2(int estado){
        if (estado == View.GONE) spinE2SabeFechaAdmision.setSelection(0, false);
        textE2SabeFechaAdmision.setVisibility(estado);
        spinE2SabeFechaAdmision.setVisibility(estado);
    }
    /*12. [Si P8 == “Si”] Cual fue su fecha de alta médica?*/
    private void MostrarOcultarPregunta12E2(int estado){
        if (estado == View.GONE) spinE2SabeFechaAlta.setSelection(0, false);
        textE2SabeFechaAlta.setVisibility(estado);
        spinE2SabeFechaAlta.setVisibility(estado);
    }
    /*13. [Si P8==”Si”] Mientras estuvo en el hospital, requirió oxigeno?*/
    private void MostrarOcultarPregunta13E2(int estado){
        if (estado == View.GONE) spinE2UtilizoOxigeno.setSelection(0, false);
        textE2UtilizoOxigeno.setVisibility(estado);
        spinE2UtilizoOxigeno.setVisibility(estado);
    }
    /*14. [Si P8==”Si”] Mientras usted o su niño/a estuvo en el hospital, en algún momento requirió de Unidad de Cuidados Intensivos?*/
    private void MostrarOcultarPregunta14E2(int estado){
        if (estado == View.GONE) spinE2EstuvoUCI.setSelection(0, false);
        textE2EstuvoUCI.setVisibility(estado);
        spinE2EstuvoUCI.setVisibility(estado);
    }
    /*15. [Si la P4 == “Si”] Se ha recuperado del COVID-19 y posee ahora un estado de salud normal o igual al que tenía antes de enfermedad?*/
    private void MostrarOcultarPregunta15E2(int estado){
        if (estado == View.GONE) spinE2RecuperadoCovid19.setSelection(0, false);
        textE2RecuperadoCovid19.setVisibility(estado);
        spinE2RecuperadoCovid19.setVisibility(estado);
    }

    /*16. [Si P16 == “No” o “No Sabe/No está seguro” /no contesto] ¿Usted o su niño/a que síntomas presenta aun?*/
    private void MostrarOcultarPregunta16E2(int estado){
        if (estado == View.GONE) {
            spinE2TieneFebricula.setSelection(0, false);
            spinE2TieneCansancio.setSelection(0, false);
            spinE2TieneDolorCabeza.setSelection(0, false);
            spinE2TienePerdidaOlfato.setSelection(0, false);
            spinE2TienePerdidaGusto.setSelection(0, false);
            spinE2TieneTos.setSelection(0, false);
            spinE2TieneDificultadRespirar.setSelection(0, false);
            spinE2TieneDolorPecho.setSelection(0, false);
            spinE2TienePalpitaciones.setSelection(0, false);
            spinE2TieneDolorArticulaciones.setSelection(0, false);
            spinE2TieneParalisis.setSelection(0, false);
            spinE2TieneMareos.setSelection(0, false);
            spinE2TienePensamientoNublado.setSelection(0, false);
            spinE2TieneProblemasDormir.setSelection(0, false);
            spinE2TieneDepresion.setSelection(0, false);
            spinE2TieneOtrosSintomas.setSelection(0, false);

        }
        textE2QueSintomasPresenta.setVisibility(estado);
        spinE2TieneFebricula.setVisibility(estado);
        spinE2TieneCansancio.setVisibility(estado);
        spinE2TieneDolorCabeza.setVisibility(estado);
        spinE2TienePerdidaOlfato.setVisibility(estado);
        spinE2TienePerdidaGusto.setVisibility(estado);
        spinE2TieneTos.setVisibility(estado);
        spinE2TieneDificultadRespirar.setVisibility(estado);
        spinE2TieneDolorPecho.setVisibility(estado);
        spinE2TienePalpitaciones.setVisibility(estado);
        spinE2TieneDolorArticulaciones.setVisibility(estado);
        spinE2TieneParalisis.setVisibility(estado);
        spinE2TieneMareos.setVisibility(estado);
        spinE2TienePensamientoNublado.setVisibility(estado);
        spinE2TieneProblemasDormir.setVisibility(estado);
        spinE2TieneDepresion.setVisibility(estado);
        spinE2TieneOtrosSintomas.setVisibility(estado);
        textE2TieneFebricula.setVisibility(estado);
        textE2TieneCansancio.setVisibility(estado);
        textE2TieneDolorCabeza.setVisibility(estado);
        textE2TienePerdidaOlfato.setVisibility(estado);
        textE2TienePerdidaGusto.setVisibility(estado);
        textE2TieneTos.setVisibility(estado);
        textE2TieneDificultadRespirar.setVisibility(estado);
        textE2TieneDolorPecho.setVisibility(estado);
        textE2TienePalpitaciones.setVisibility(estado);
        textE2TieneDolorArticulaciones.setVisibility(estado);
        textE2TieneParalisis.setVisibility(estado);
        textE2TieneMareos.setVisibility(estado);
        textE2TienePensamientoNublado.setVisibility(estado);
        textE2TieneProblemasDormir.setVisibility(estado);
        textE2TieneDepresion.setVisibility(estado);
        textE2TieneOtrosSintomas.setVisibility(estado);
    }
    /*17. [Si P16 == “Si”] ¿A usted o su niño/a cuánto tiempo le tomo en recuperarse de los síntomas secundarios a COVID-19 y recobrar su salud normal?*/
    private void MostrarOcultarPregunta17E2(int estado){
        if (estado == View.GONE) spinE2SabeTiempoRecuperacion.setSelection(0, false);
        textE2SabeTiempoRecuperacion.setVisibility(estado);
        spinE2SabeTiempoRecuperacion.setVisibility(estado);
    }

    private void MostrarOcultarPregunta17AE2(int estado){
        if (estado == View.GONE) spinE2TiempoRecuperacionEn.setSelection(0, false);
        textE2TiempoRecuperacion.setVisibility(estado);
        inputE2TiempoRecuperacion.setVisibility(estado);
        spinE2TiempoRecuperacionEn.setVisibility(estado);
    }
    /*18. [Si P1==”Si”] En el momento más severo de su enfermedad, diría que su enfermedad o la de su niño/a fue?*/
    private void MostrarOcultarPregunta18E2(int estado){
        if (estado == View.GONE) spinE2SeveridadEnfermedad.setSelection(0, false);
        textE2SeveridadEnfermedad.setVisibility(estado);
        spinE2SeveridadEnfermedad.setVisibility(estado);
    }

    private void MostrarOcultarPregunta19E2(int estado){
        if (estado == View.GONE) spinE2TomoMedicamento.setSelection(0, false);
        textE2TomoMedicamento.setVisibility(estado);
        spinE2TomoMedicamento.setVisibility(estado);
    }

    private void MostrarOcultarPregunta20E2(int estado){
        if (estado == View.GONE) spinE2OxigenoDomicilio.setSelection(0, false);
        textE2OxigenoDomicilio.setVisibility(estado);
        spinE2OxigenoDomicilio.setVisibility(estado);
    }

    private void MostrarOcultarPregunta20AE2(int estado){
        if (estado == View.GONE) spinE2TiempoOxigenoDom.setSelection(0, false);
        textE2TiempoOxigenoDom.setVisibility(estado);
        spinE2TiempoOxigenoDom.setVisibility(estado);
    }

    private void MostrarOcultarPregunta19AE2(int estado){
        textE2QueMedicamento.setVisibility(estado);
        spinE2QueMedicamento.setVisibility(estado);
    }

    /*24[Si P3==”Si”] ¿En los días previos a que se enfermara, usted fumaba cigarrillos todos los días o algunos días previo a que se enfermara?*/
    private void MostrarOcultarPregunta24E2(int estado){
        if (estado == View.GONE) spinE2FumadoPrevioEnfermedad.setSelection(0, false);
        textE2FumadoPrevioEnfermedad.setVisibility(estado);
        spinE2FumadoPrevioEnfermedad.setVisibility(estado);
    }
    /*25.¿Usted fumaba cigarrillos todos los días o algunos días ahora?*/
    private void MostrarOcultarPregunta25E2(int estado){
        if (estado == View.GONE) spinE2FumaActualmente.setSelection(0, false);
        textE2FumaActualmente.setVisibility(estado);
        spinE2FumaActualmente.setVisibility(estado);
    }

    /*26 ¿Usted estaba embarazada al momento de ser diagnosticada o sospechosa de COVID19?*/
    private void MostrarOcultarPregunta26E2(int estado){
        if (estado == View.GONE) spinE2Embarazada.setSelection(0, false);
        textE2Embarazada.setVisibility(estado);
        spinE2Embarazada.setVisibility(estado);
    }
    /*Recuerda las semanas de embarazo que tenia**/
    private void MostrarOcultarPregunta26AE2(int estado){
        if (estado == View.GONE) spinE2RecuerdaSemanasEmb.setSelection(0, false);
        textE2RecuerdaSemanasEmb.setVisibility(estado);
        spinE2RecuerdaSemanasEmb.setVisibility(estado);
    }

    private void MostrarOcultarPregunta26A1E2(int estado){
        textE2SemanasEmbarazo.setVisibility(estado);
        inputE2SemanasEmbarazo.setVisibility(estado);
    }
    /*como finalizo el embarazo*/
    private void MostrarOcultarPregunta26BE2(int estado){
        if (estado == View.GONE) spinE2FinalEmbarazo.setSelection(0, false);
        textE2FinalEmbarazo.setVisibility(estado);
        spinE2FinalEmbarazo.setVisibility(estado);
    }

    private void MostrarOcultarPregunta26B1E2(int estado){
        textE2OtroFinalEmbarazo.setVisibility(estado);
        inputE2OtroFinalEmbarazo.setVisibility(estado);
    }

    /*27. Si estaba embarazada recientemente al momento de ser diagnosticada con COVID19, ¿Usted estaba dando pecho materno?*/
    private void MostrarOcultarPregunta27E2(int estado){
        if (estado == View.GONE) spinE2DabaPecho.setSelection(0, false);
        textE2DabaPecho.setVisibility(estado);
        spinE2DabaPecho.setVisibility(estado);
    }
    //evento3
    /*Que fecha exacta o aproximada empezaron estos síntomas*/
    private void MostrarOcultarPregunta3E3(){
        if (tuvoAlMenosUnSintomaE3()) {
            layoutE3FIS.setVisibility(View.VISIBLE);
            spinE3SabeInicioSintoma.setVisibility(View.VISIBLE);
            textE3SabeInicioSintoma.setVisibility(View.VISIBLE);
        }else {
            layoutE3FIS.setVisibility(View.GONE);
            spinE3SabeInicioSintoma.setSelection(0, false);
            spinE3SabeInicioSintoma.setVisibility(View.GONE);
            textE3SabeInicioSintoma.setVisibility(View.GONE);
        }
    }

    private void MostrarOcultarPregunta3_MesAnioE3(int estado){
        if (estado == View.GONE) spinE3MesInicioSintoma.setSelection(0, false);
        textE3MesInicioSintoma.setVisibility(estado);
        spinE3MesInicioSintoma.setVisibility(estado);
        textE3AnioInicioSintoma.setVisibility(estado);
        inputE3AnioInicioSintoma.setVisibility(estado);
    }

    private void MostrarOcultarPregunta3_FISE3(int estado){
        layoutE3FIS.setVisibility(estado);
        textE3FIS.setVisibility(estado);
        inputE3FIS.setVisibility(estado);
        imbE3FIS.setVisibility(estado);
    }

    /*4. ¿Conoce en donde usted o su niño/a estuvieron expuestos a COVID-19?*/
    private void MostrarOcultarPregunta4E3(int estado){
        if (estado == View.GONE) spinE3ConoceLugarExposicion.setSelection(0, false);
        textE3ConoceLugarExposicion.setVisibility(estado);
        spinE3ConoceLugarExposicion.setVisibility(estado);
    }
    /*5. ¿Busco ayuda médica?*/
    private void MostrarOcultarPregunta5E3(int estado){
        if (estado == View.GONE) spinE3BuscoAyuda.setSelection(0, false);
        textE3BuscoAyuda.setVisibility(estado);
        spinE3BuscoAyuda.setVisibility(estado);
    }
    /*6. [Si P6==”Si”] Donde busco ayuda médica?*/
    private void MostrarOcultarPregunta6E3(int estado){
        if (estado == View.GONE) spinE3DondeBuscoAyuda.setSelection(0, false);
        textE3DondeBuscoAyuda.setVisibility(estado);
        spinE3DondeBuscoAyuda.setVisibility(estado);
    }

    private void MostrarOcultarPregunta6AE3(int estado){
        if (estado == View.GONE) spinE3RecibioSeguimiento.setSelection(0, false);
        textE3RecibioSeguimiento.setVisibility(estado);
        spinE3RecibioSeguimiento.setVisibility(estado);
    }

    private void MostrarOcultarPregunta6ASiE3(int estado){
        if (estado == View.GONE) spinE3TipoSeguimiento.setSelection(0, false);
        textE3TipoSeguimiento.setVisibility(estado);
        spinE3TipoSeguimiento.setVisibility(estado);
    }
    /*7. [Si P6==”Si”] Cuanto tiempo después de que iniciaron los síntomas de COVID-19 busco ayuda médica?*/
    private void MostrarOcultarPregunta7E3(int estado){
        if (estado == View.GONE) spinE3TmpDespuesBuscoAyuda.setSelection(0, false);
        textE3TmpDespuesBuscoAyuda.setVisibility(estado);
        spinE3TmpDespuesBuscoAyuda.setVisibility(estado);
    }
    /*8. [Si P6==”Si”] Paso al menos una noche en el hospital por COVID-19?*/
    private void MostrarOcultarPregunta8E3(int estado){
        if (estado == View.GONE) spinE3UnaNocheHospital.setSelection(0, false);
        textE3UnaNocheHospital.setVisibility(estado);
        spinE3UnaNocheHospital.setVisibility(estado);
    }
    /*9. [Si P8==”Si”] ¿A qué hospital acudió?*/
    private void MostrarOcultarPregunta9E3(int estado){
        if (estado == View.GONE) inputE3QueHospital.setText("");
        textE3QueHospital.setVisibility(estado);
        inputE3QueHospital.setVisibility(estado);
    }
    /*10. [Si P8 == “Si”] Cuantas noches estuvo en el hospital?*/
    private void MostrarOcultarPregunta10E3(int estado){
        if (estado == View.GONE) spinE3SabeCuantasNoches.setSelection(0, false);
        textE3SabeCuantasNoches.setVisibility(estado);
        spinE3SabeCuantasNoches.setVisibility(estado);
    }
    /*11. [Si P8== “Si”] Cual fue su fecha de admisión al hospital?*/
    private void MostrarOcultarPregunta11E3(int estado){
        if (estado == View.GONE) spinE3SabeFechaAdmision.setSelection(0, false);
        textE3SabeFechaAdmision.setVisibility(estado);
        spinE3SabeFechaAdmision.setVisibility(estado);
    }
    /*12. [Si P8 == “Si”] Cual fue su fecha de alta médica?*/
    private void MostrarOcultarPregunta12E3(int estado){
        if (estado == View.GONE) spinE3SabeFechaAlta.setSelection(0, false);
        textE3SabeFechaAlta.setVisibility(estado);
        spinE3SabeFechaAlta.setVisibility(estado);
    }
    /*13. [Si P8==”Si”] Mientras estuvo en el hospital, requirió oxigeno?*/
    private void MostrarOcultarPregunta13E3(int estado){
        if (estado == View.GONE) spinE3UtilizoOxigeno.setSelection(0, false);
        textE3UtilizoOxigeno.setVisibility(estado);
        spinE3UtilizoOxigeno.setVisibility(estado);
    }
    /*14. [Si P8==”Si”] Mientras usted o su niño/a estuvo en el hospital, en algún momento requirió de Unidad de Cuidados Intensivos?*/
    private void MostrarOcultarPregunta14E3(int estado){
        if (estado == View.GONE) spinE3EstuvoUCI.setSelection(0, false);
        textE3EstuvoUCI.setVisibility(estado);
        spinE3EstuvoUCI.setVisibility(estado);
    }
    /*15. [Si la P4 == “Si”] Se ha recuperado del COVID-19 y posee ahora un estado de salud normal o igual al que tenía antes de enfermedad?*/
    private void MostrarOcultarPregunta15E3(int estado){
        if (estado == View.GONE) spinE3RecuperadoCovid19.setSelection(0, false);
        textE3RecuperadoCovid19.setVisibility(estado);
        spinE3RecuperadoCovid19.setVisibility(estado);
    }

    /*16. [Si P16 == “No” o “No Sabe/No está seguro” /no contesto] ¿Usted o su niño/a que síntomas presenta aun?*/
    private void MostrarOcultarPregunta16E3(int estado){
        if (estado == View.GONE) {
            spinE3TieneFebricula.setSelection(0, false);
            spinE3TieneCansancio.setSelection(0, false);
            spinE3TieneDolorCabeza.setSelection(0, false);
            spinE3TienePerdidaOlfato.setSelection(0, false);
            spinE3TienePerdidaGusto.setSelection(0, false);
            spinE3TieneTos.setSelection(0, false);
            spinE3TieneDificultadRespirar.setSelection(0, false);
            spinE3TieneDolorPecho.setSelection(0, false);
            spinE3TienePalpitaciones.setSelection(0, false);
            spinE3TieneDolorArticulaciones.setSelection(0, false);
            spinE3TieneParalisis.setSelection(0, false);
            spinE3TieneMareos.setSelection(0, false);
            spinE3TienePensamientoNublado.setSelection(0, false);
            spinE3TieneProblemasDormir.setSelection(0, false);
            spinE3TieneDepresion.setSelection(0, false);
            spinE3TieneOtrosSintomas.setSelection(0, false);

        }
        textE3QueSintomasPresenta.setVisibility(estado);
        spinE3TieneFebricula.setVisibility(estado);
        spinE3TieneCansancio.setVisibility(estado);
        spinE3TieneDolorCabeza.setVisibility(estado);
        spinE3TienePerdidaOlfato.setVisibility(estado);
        spinE3TienePerdidaGusto.setVisibility(estado);
        spinE3TieneTos.setVisibility(estado);
        spinE3TieneDificultadRespirar.setVisibility(estado);
        spinE3TieneDolorPecho.setVisibility(estado);
        spinE3TienePalpitaciones.setVisibility(estado);
        spinE3TieneDolorArticulaciones.setVisibility(estado);
        spinE3TieneParalisis.setVisibility(estado);
        spinE3TieneMareos.setVisibility(estado);
        spinE3TienePensamientoNublado.setVisibility(estado);
        spinE3TieneProblemasDormir.setVisibility(estado);
        spinE3TieneDepresion.setVisibility(estado);
        spinE3TieneOtrosSintomas.setVisibility(estado);
        textE3TieneFebricula.setVisibility(estado);
        textE3TieneCansancio.setVisibility(estado);
        textE3TieneDolorCabeza.setVisibility(estado);
        textE3TienePerdidaOlfato.setVisibility(estado);
        textE3TienePerdidaGusto.setVisibility(estado);
        textE3TieneTos.setVisibility(estado);
        textE3TieneDificultadRespirar.setVisibility(estado);
        textE3TieneDolorPecho.setVisibility(estado);
        textE3TienePalpitaciones.setVisibility(estado);
        textE3TieneDolorArticulaciones.setVisibility(estado);
        textE3TieneParalisis.setVisibility(estado);
        textE3TieneMareos.setVisibility(estado);
        textE3TienePensamientoNublado.setVisibility(estado);
        textE3TieneProblemasDormir.setVisibility(estado);
        textE3TieneDepresion.setVisibility(estado);
        textE3TieneOtrosSintomas.setVisibility(estado);
    }
    /*17. [Si P16 == “Si”] ¿A usted o su niño/a cuánto tiempo le tomo en recuperarse de los síntomas secundarios a COVID-19 y recobrar su salud normal?*/
    private void MostrarOcultarPregunta17E3(int estado){
        if (estado == View.GONE) spinE3SabeTiempoRecuperacion.setSelection(0, false);
        textE3SabeTiempoRecuperacion.setVisibility(estado);
        spinE3SabeTiempoRecuperacion.setVisibility(estado);
    }

    private void MostrarOcultarPregunta17AE3(int estado){
        if (estado == View.GONE) spinE3TiempoRecuperacionEn.setSelection(0, false);
        textE3TiempoRecuperacion.setVisibility(estado);
        inputE3TiempoRecuperacion.setVisibility(estado);
        spinE3TiempoRecuperacionEn.setVisibility(estado);
    }
    /*18. [Si P1==”Si”] En el momento más severo de su enfermedad, diría que su enfermedad o la de su niño/a fue?*/
    private void MostrarOcultarPregunta18E3(int estado){
        if (estado == View.GONE) spinE3SeveridadEnfermedad.setSelection(0, false);
        textE3SeveridadEnfermedad.setVisibility(estado);
        spinE3SeveridadEnfermedad.setVisibility(estado);
    }

    private void MostrarOcultarPregunta19E3(int estado){
        if (estado == View.GONE) spinE3TomoMedicamento.setSelection(0, false);
        textE3TomoMedicamento.setVisibility(estado);
        spinE3TomoMedicamento.setVisibility(estado);
    }

    private void MostrarOcultarPregunta20E3(int estado){
        if (estado == View.GONE) spinE3OxigenoDomicilio.setSelection(0, false);
        textE3OxigenoDomicilio.setVisibility(estado);
        spinE3OxigenoDomicilio.setVisibility(estado);
    }

    private void MostrarOcultarPregunta20AE3(int estado){
        if (estado == View.GONE) spinE3TiempoOxigenoDom.setSelection(0, false);
        textE3TiempoOxigenoDom.setVisibility(estado);
        spinE3TiempoOxigenoDom.setVisibility(estado);
    }

    private void MostrarOcultarPregunta19AE3(int estado){
        textE3QueMedicamento.setVisibility(estado);
        spinE3QueMedicamento.setVisibility(estado);
    }

    /*24[Si P3==”Si”] ¿En los días previos a que se enfermara, usted fumaba cigarrillos todos los días o algunos días previo a que se enfermara?*/
    private void MostrarOcultarPregunta24E3(int estado){
        if (estado == View.GONE) spinE3FumadoPrevioEnfermedad.setSelection(0, false);
        textE3FumadoPrevioEnfermedad.setVisibility(estado);
        spinE3FumadoPrevioEnfermedad.setVisibility(estado);
    }
    /*25.¿Usted fumaba cigarrillos todos los días o algunos días ahora?*/
    private void MostrarOcultarPregunta25E3(int estado){
        if (estado == View.GONE) spinE3FumaActualmente.setSelection(0, false);
        textE3FumaActualmente.setVisibility(estado);
        spinE3FumaActualmente.setVisibility(estado);
    }

    /*26 ¿Usted estaba embarazada al momento de ser diagnosticada o sospechosa de COVID19?*/
    private void MostrarOcultarPregunta26E3(int estado){
        if (estado == View.GONE) spinE3Embarazada.setSelection(0, false);
        textE3Embarazada.setVisibility(estado);
        spinE3Embarazada.setVisibility(estado);
    }
    /*Recuerda las semanas de embarazo que tenia**/
    private void MostrarOcultarPregunta26AE3(int estado){
        if (estado == View.GONE) spinE3RecuerdaSemanasEmb.setSelection(0, false);
        textE3RecuerdaSemanasEmb.setVisibility(estado);
        spinE3RecuerdaSemanasEmb.setVisibility(estado);
    }

    private void MostrarOcultarPregunta26A1E3(int estado){
        textE3SemanasEmbarazo.setVisibility(estado);
        inputE3SemanasEmbarazo.setVisibility(estado);
    }
    /*como finalizo el embarazo*/
    private void MostrarOcultarPregunta26BE3(int estado){
        if (estado == View.GONE) spinE3FinalEmbarazo.setSelection(0, false);
        textE3FinalEmbarazo.setVisibility(estado);
        spinE3FinalEmbarazo.setVisibility(estado);
    }

    private void MostrarOcultarPregunta26B1E3(int estado){
        textE3OtroFinalEmbarazo.setVisibility(estado);
        inputE3OtroFinalEmbarazo.setVisibility(estado);
    }

    /*27. Si estaba embarazada recientemente al momento de ser diagnosticada con COVID19, ¿Usted estaba dando pecho materno?*/
    private void MostrarOcultarPregunta27E3(int estado){
        if (estado == View.GONE) spinE3DabaPecho.setSelection(0, false);
        textE3DabaPecho.setVisibility(estado);
        spinE3DabaPecho.setVisibility(estado);
    }

    /*28. ¿Usted estuvo empleado como trabajador de la salud desde el 1 de febrero de 2021?*/
    private void MostrarOcultarPregunta28(int estado){
        if (estado == View.GONE) spinTrabajadorSalud.setSelection(0, false);
        textTrabajadorSalud.setVisibility(estado);
        spinTrabajadorSalud.setVisibility(estado);
    }

    /*30. ¿Puede mostrarnos su tarjeta de vacunación contra la COVID 19?*/
    private void MostrarOcultarPregunta30(int estado){
        if (estado == View.GONE) spinMuestraTarjetaVac.setSelection(0, false);
        textMuestraTarjetaVac.setVisibility(estado);
        spinMuestraTarjetaVac.setVisibility(estado);
    }

    private void MostrarOcultarPregunta30A(int estado){
        if (estado == View.GONE) spinSabeNombreVacuna.setSelection(0, false);
        textSabeNombreVacuna.setVisibility(estado);
        spinSabeNombreVacuna.setVisibility(estado);
    }

    private void MostrarOcultarPregunta30A_Vacuna(int estado){
        textNombreVacuna.setVisibility(estado);
        inputNombreVacuna.setVisibility(estado);
        textAnioVacuna.setVisibility(estado);
        inputAnioVacuna.setVisibility(estado);
        if (estado == View.GONE) spinMesVacuna.setSelection(0, false);
        textMesVacuna.setVisibility(estado);
        spinMesVacuna.setVisibility(estado);
    }

    private void MostrarOcultarPregunta30_Dosis(int estado){
        if (estado == View.GONE) spinCuantasDosis.setSelection(0, false);
        textCuantasDosis.setVisibility(estado);
        spinCuantasDosis.setVisibility(estado);
    }

    private void MostrarOcultarPregunta30_Dosis1(int estado){
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

    private void MostrarOcultarPregunta30_Dosis1_Otro(int estado){
        textOtraVacunaDosis1.setVisibility(estado);
        inputOtraVacunaDosis1.setVisibility(estado);
    }

    private void MostrarOcultarPregunta30_Dosis2(int estado){
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

    private void MostrarOcultarPregunta30_Dosis2_Otro(int estado){
        textOtraVacunaDosis2.setVisibility(estado);
        inputOtraVacunaDosis2.setVisibility(estado);
    }

    private void MostrarOcultarPregunta30_Dosis3(int estado){
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

    private void MostrarOcultarPregunta30_Dosis3_Otro(int estado){
        textOtraVacunaDosis3.setVisibility(estado);
        inputOtraVacunaDosis3.setVisibility(estado);
    }


    /*31. ¿Posterior a ser vacunado contra la COVID19 Ud. o su niño/a presento alguna molestia o síntoma posterior a un máximo de 72 horas a la vacunación?*/
    private void MostrarOcultarPregunta31Dosis1(int estado){
        if (estado == View.GONE) spinPresentoSintomasDosis1.setSelection(0, false);
        textPresentoSintomasDosis1.setVisibility(estado);
        spinPresentoSintomasDosis1.setVisibility(estado);
    }

    private void MostrarOcultarPregunta31_SintomasDosis1(int estado){
        if (estado == View.GONE) spinDolorSitioDosis1.setSelection(0, false);
        if (estado == View.GONE) spinFiebreDosis1.setSelection(0, false);
        if (estado == View.GONE) spinCansancioDosis1.setSelection(0, false);
        if (estado == View.GONE) spinDolorCabezaDosis1.setSelection(0, false);
        if (estado == View.GONE) spinDiarreaDosis1.setSelection(0, false);
        if (estado == View.GONE) spinVomitoDosis1.setSelection(0, false);
        if (estado == View.GONE) spinDolorMuscularDosis1.setSelection(0, false);
        if (estado == View.GONE) spinEscalofriosDosis1.setSelection(0, false);
        if (estado == View.GONE) spinReaccionAlergicaDosis1.setSelection(0, false);
        if (estado == View.GONE) spinInfeccionSitioDosis1.setSelection(0, false);
        if (estado == View.GONE) spinNauseasDosis1.setSelection(0, false);
        if (estado == View.GONE) spinBultosDosis1.setSelection(0, false);
        if (estado == View.GONE) spinOtrosDosis1.setSelection(0, false);
        textDolorSitioDosis1.setVisibility(estado);
        textFiebreDosis1.setVisibility(estado);
        textCansancioDosis1.setVisibility(estado);
        textDolorCabezaDosis1.setVisibility(estado);
        textDiarreaDosis1.setVisibility(estado);
        textVomitoDosis1.setVisibility(estado);
        textDolorMuscularDosis1.setVisibility(estado);
        textEscalofriosDosis1.setVisibility(estado);
        textReaccionAlergicaDosis1.setVisibility(estado);
        textInfeccionSitioDosis1.setVisibility(estado);
        textNauseasDosis1.setVisibility(estado);
        textBultosDosis1.setVisibility(estado);
        textOtrosDosis1.setVisibility(estado);
        spinDolorSitioDosis1.setVisibility(estado);
        spinFiebreDosis1.setVisibility(estado);
        spinCansancioDosis1.setVisibility(estado);
        spinDolorCabezaDosis1.setVisibility(estado);
        spinDiarreaDosis1.setVisibility(estado);
        spinVomitoDosis1.setVisibility(estado);
        spinDolorMuscularDosis1.setVisibility(estado);
        spinEscalofriosDosis1.setVisibility(estado);
        spinReaccionAlergicaDosis1.setVisibility(estado);
        spinInfeccionSitioDosis1.setVisibility(estado);
        spinNauseasDosis1.setVisibility(estado);
        spinBultosDosis1.setVisibility(estado);
        spinOtrosDosis1.setVisibility(estado);
    }

    private void MostrarOcultarPregunta31_OtroSintomaDosis1(int estado){
        textDesOtrosDosis1.setVisibility(estado);
        inputDesOtrosDosis1.setVisibility(estado);
    }
    //Dosis2
    /*31. ¿Posterior a ser vacunado contra la COVID19 Ud. o su niño/a presento alguna molestia o síntoma posterior a un máximo de 72 horas a la vacunación?*/
    private void MostrarOcultarPregunta31Dosis2(int estado){
        if (estado == View.GONE) spinPresentoSintomasDosis2.setSelection(0, false);
        textPresentoSintomasDosis2.setVisibility(estado);
        spinPresentoSintomasDosis2.setVisibility(estado);
    }

    private void MostrarOcultarPregunta31_SintomasDosis2(int estado){
        if (estado == View.GONE) spinDolorSitioDosis2.setSelection(0, false);
        if (estado == View.GONE) spinFiebreDosis2.setSelection(0, false);
        if (estado == View.GONE) spinCansancioDosis2.setSelection(0, false);
        if (estado == View.GONE) spinDolorCabezaDosis2.setSelection(0, false);
        if (estado == View.GONE) spinDiarreaDosis2.setSelection(0, false);
        if (estado == View.GONE) spinVomitoDosis2.setSelection(0, false);
        if (estado == View.GONE) spinDolorMuscularDosis2.setSelection(0, false);
        if (estado == View.GONE) spinEscalofriosDosis2.setSelection(0, false);
        if (estado == View.GONE) spinReaccionAlergicaDosis2.setSelection(0, false);
        if (estado == View.GONE) spinInfeccionSitioDosis2.setSelection(0, false);
        if (estado == View.GONE) spinNauseasDosis2.setSelection(0, false);
        if (estado == View.GONE) spinBultosDosis2.setSelection(0, false);
        if (estado == View.GONE) spinOtrosDosis2.setSelection(0, false);
        textDolorSitioDosis2.setVisibility(estado);
        textFiebreDosis2.setVisibility(estado);
        textCansancioDosis2.setVisibility(estado);
        textDolorCabezaDosis2.setVisibility(estado);
        textDiarreaDosis2.setVisibility(estado);
        textVomitoDosis2.setVisibility(estado);
        textDolorMuscularDosis2.setVisibility(estado);
        textEscalofriosDosis2.setVisibility(estado);
        textReaccionAlergicaDosis2.setVisibility(estado);
        textInfeccionSitioDosis2.setVisibility(estado);
        textNauseasDosis2.setVisibility(estado);
        textBultosDosis2.setVisibility(estado);
        textOtrosDosis2.setVisibility(estado);
        spinDolorSitioDosis2.setVisibility(estado);
        spinFiebreDosis2.setVisibility(estado);
        spinCansancioDosis2.setVisibility(estado);
        spinDolorCabezaDosis2.setVisibility(estado);
        spinDiarreaDosis2.setVisibility(estado);
        spinVomitoDosis2.setVisibility(estado);
        spinDolorMuscularDosis2.setVisibility(estado);
        spinEscalofriosDosis2.setVisibility(estado);
        spinReaccionAlergicaDosis2.setVisibility(estado);
        spinInfeccionSitioDosis2.setVisibility(estado);
        spinNauseasDosis2.setVisibility(estado);
        spinBultosDosis2.setVisibility(estado);
        spinOtrosDosis2.setVisibility(estado);
    }

    private void MostrarOcultarPregunta31_OtroSintomaDosis2(int estado){
        textDesOtrosDosis2.setVisibility(estado);
        inputDesOtrosDosis2.setVisibility(estado);
    }
//evento3
/*31. ¿Posterior a ser vacunado contra la COVID19 Ud. o su niño/a presento alguna molestia o síntoma posterior a un máximo de 72 horas a la vacunación?*/
private void MostrarOcultarPregunta31Dosis3(int estado){
    if (estado == View.GONE) spinPresentoSintomasDosis3.setSelection(0, false);
    textPresentoSintomasDosis3.setVisibility(estado);
    spinPresentoSintomasDosis3.setVisibility(estado);
}

    private void MostrarOcultarPregunta31_SintomasDosis3(int estado){
        if (estado == View.GONE) spinDolorSitioDosis3.setSelection(0, false);
        if (estado == View.GONE) spinFiebreDosis3.setSelection(0, false);
        if (estado == View.GONE) spinCansancioDosis3.setSelection(0, false);
        if (estado == View.GONE) spinDolorCabezaDosis3.setSelection(0, false);
        if (estado == View.GONE) spinDiarreaDosis3.setSelection(0, false);
        if (estado == View.GONE) spinVomitoDosis3.setSelection(0, false);
        if (estado == View.GONE) spinDolorMuscularDosis3.setSelection(0, false);
        if (estado == View.GONE) spinEscalofriosDosis3.setSelection(0, false);
        if (estado == View.GONE) spinReaccionAlergicaDosis3.setSelection(0, false);
        if (estado == View.GONE) spinInfeccionSitioDosis3.setSelection(0, false);
        if (estado == View.GONE) spinNauseasDosis3.setSelection(0, false);
        if (estado == View.GONE) spinBultosDosis3.setSelection(0, false);
        if (estado == View.GONE) spinOtrosDosis3.setSelection(0, false);
        textDolorSitioDosis3.setVisibility(estado);
        textFiebreDosis3.setVisibility(estado);
        textCansancioDosis3.setVisibility(estado);
        textDolorCabezaDosis3.setVisibility(estado);
        textDiarreaDosis3.setVisibility(estado);
        textVomitoDosis3.setVisibility(estado);
        textDolorMuscularDosis3.setVisibility(estado);
        textEscalofriosDosis3.setVisibility(estado);
        textReaccionAlergicaDosis3.setVisibility(estado);
        textInfeccionSitioDosis3.setVisibility(estado);
        textNauseasDosis3.setVisibility(estado);
        textBultosDosis3.setVisibility(estado);
        textOtrosDosis3.setVisibility(estado);
        spinDolorSitioDosis3.setVisibility(estado);
        spinFiebreDosis3.setVisibility(estado);
        spinCansancioDosis3.setVisibility(estado);
        spinDolorCabezaDosis3.setVisibility(estado);
        spinDiarreaDosis3.setVisibility(estado);
        spinVomitoDosis3.setVisibility(estado);
        spinDolorMuscularDosis3.setVisibility(estado);
        spinEscalofriosDosis3.setVisibility(estado);
        spinReaccionAlergicaDosis3.setVisibility(estado);
        spinInfeccionSitioDosis3.setVisibility(estado);
        spinNauseasDosis3.setVisibility(estado);
        spinBultosDosis3.setVisibility(estado);
        spinOtrosDosis3.setVisibility(estado);
    }

    private void MostrarOcultarPregunta31_OtroSintomaDosis3(int estado){
        textDesOtrosDosis3.setVisibility(estado);
        inputDesOtrosDosis3.setVisibility(estado);
    }



    /*32. ¿Ud. o su niño presentaron síntomas que sospecharon enfermedad por Covid-19 Posterior a haber recibido la vacuna de Covid-19?*/
    private void MostrarOcultarPregunta32(int estado){
        if (estado == View.GONE) spinCovid19PosteriorVacuna.setSelection(0, false);
        textCovid19PosteriorVacuna.setVisibility(estado);
        spinCovid19PosteriorVacuna.setVisibility(estado);
    }

    private void MostrarOcultarPregunta32_A_Fecha(int estado){
        if (estado == View.GONE) spinFechaEventoEnfermoPostVac.setSelection(0, false);
        textFechaEventoEnfermoPostVac.setVisibility(estado);
        spinFechaEventoEnfermoPostVac.setVisibility(estado);
    }

/*    private void MostrarOcultarPregunta32_A(int estado){
        if (estado == View.GONE) spinSabeFechaEnfPostVac.setSelection(0, false);
        textSabeFechaEnfPostVac.setVisibility(estado);
        spinSabeFechaEnfPostVac.setVisibility(estado);
    }

    private void MostrarOcultarPregunta32_MesAnio(int estado){
        if (estado == View.GONE) spinMesEnfPostVac.setSelection(0, false);
        textMesEnfPostVac.setVisibility(estado);
        spinMesEnfPostVac.setVisibility(estado);
        textAnioEnfPostVac.setVisibility(estado);
        inputAnioEnfPostVac.setVisibility(estado);
    }

    private void MostrarOcultarPregunta32_FIS(int estado){
        layoutFechaEnfPostVac.setVisibility(estado);
        textFechaEnfPostVac.setVisibility(estado);
        inputFechaEnfPostVac.setVisibility(estado);
        imbFechaEnfPostVac.setVisibility(estado);
    }
    */

    private void actualizarListaEventos(){
        List<MessageResource> meses  = new ArrayList<MessageResource>();
        meses.add(new MessageResource("", 0, getActivity().getString(R.string.select)));
        if (fechaEvento1 != null && !fechaEvento1.isEmpty()) {
            meses.add(new MessageResource(fechaEvento1, 0, fechaEvento1));
        } else if (mesEvento1 != null && !mesEvento1.isEmpty()) {
            meses.add(new MessageResource(mesEvento1 + "/" + anioEvento1, 0, mesEvento1 + "/" + anioEvento1));
        }
        if (fechaEvento2 != null && !fechaEvento2.isEmpty()) {
            meses.add(new MessageResource(fechaEvento2, 0, fechaEvento2));
        } else if (mesEvento2 != null && !mesEvento2.isEmpty()) {
            meses.add(new MessageResource(mesEvento2 + "/" + anioEvento2, 0, mesEvento2 + "/" + anioEvento2));
        }
        if (fechaEvento3 != null && !fechaEvento3.isEmpty()) {
            meses.add(new MessageResource(fechaEvento3, 0, fechaEvento3));
        } else if (mesEvento3 != null && !mesEvento3.isEmpty()) {
            meses.add(new MessageResource(mesEvento3 + "/" + anioEvento3, 0, mesEvento3 + "/" + anioEvento3));
        }
        Collections.sort(meses);
        ArrayAdapter<MessageResource> dataAdapterMeses = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, meses);
        dataAdapterMeses.setDropDownViewResource(R.layout.spinner_item);
        spinFechaEventoEnfermoPostVac.setAdapter(dataAdapterMeses);
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
                        case ENFERMOCOVID19_CONS:
                            enfermoCovid19 = nuevoValor;
                            enfermoCovid19Pos = position;
                            break;
                        case CUANTASVECESENFERMO_CONS:
                            cuantasVecesEnfermo = nuevoValor;
                            cuantasVecesEnfermoPos = position;
                            break;
                        case MESEVENTO1_CONS:
                            mesEvento1 = nuevoValor;
                            mesEvento1Pos = position;
                            break;
                        case MESEVENTO2_CONS:
                            mesEvento2 = nuevoValor;
                            mesEvento2Pos = position;
                            break;
                        case MESEVENTO3_CONS:
                            mesEvento3 = nuevoValor;
                            mesEvento3Pos = position;
                            break;
                        case E1FEBRICULA_CONS:
                            e1Febricula = nuevoValor;
                            e1FebriculaPos = position;
                            break;
                        case E1FIEBRE_CONS:
                            e1Fiebre = nuevoValor;
                            e1FiebrePos = position;
                            break;
                        case E1ESCALOFRIO_CONS:
                            e1Escalofrio = nuevoValor;
                            e1EscalofrioPos = position;
                            break;
                        case E1TEMBLORESCALOFRIO_CONS:
                            e1TemblorEscalofrio = nuevoValor;
                            e1TemblorEscalofrioPos = position;
                            break;
                        case E1DOLORMUSCULAR_CONS:
                            e1DolorMuscular = nuevoValor;
                            e1DolorMuscularPos = position;
                            break;
                        case E1DOLORARTICULAR_CONS:
                            e1DolorArticular = nuevoValor;
                            e1DolorArticularPos = position;
                            break;
                        case E1SECRESIONNASAL_CONS:
                            e1SecresionNasal = nuevoValor;
                            e1SecresionNasalPos = position;
                            break;
                        case E1DOLORGARGANTA_CONS:
                            e1DolorGargantaPos = position;
                            e1DolorGarganta = nuevoValor;
                            break;
                        case E1TOS_CONS:
                            e1TosPos = position;
                            e1Tos = nuevoValor;
                            break;
                        case E1DIFICULTADRESP_CONS:
                            e1DificultadResp = nuevoValor;
                            e1DificultadRespPos = position;
                            break;
                        case E1DOLORPECHO_CONS:
                            e1DolorPecho = nuevoValor;
                            e1DolorPechoPos = position;
                            break;
                        case E1NAUSEASVOMITO_CONS:
                            e1NauseasVomito = nuevoValor;
                            e1NauseasVomitoPos = position;
                            break;
                        case E1DOLORCABEZA_CONS:
                            e1DolorCabeza = nuevoValor;
                            e1DolorCabezaPos = position;
                            break;
                        case E1DOLORABDOMINAL_CONS:
                            e1DolorAbdominal = nuevoValor;
                            e1DolorAbdominalPos = position;
                            break;
                        case E1DIARREA_CONS:
                            e1Diarrea = nuevoValor;
                            e1DiarreaPos = position;
                            break;
                        case E1DIFICULTADDORMIR_CONS:
                            e1DificultadDormirPos = position;
                            e1DificultadDormir = nuevoValor;
                            break;
                        case E1DEBILIDAD_CONS:
                            e1Debilidad = nuevoValor;
                            e1DebilidadPos = position;
                            break;
                        case E1PERDIDAOLFATOGUSTO_CONS:
                            e1PerdidaOlfatoGusto = nuevoValor;
                            e1PerdidaOlfatoGustoPos = position;
                            break;
                        case E1MAREO_CONS:
                            e1Mareo = nuevoValor;
                            e1MareoPos = position;
                            break;
                        case E1SARPULLIDO_CONS:
                            e1Sarpullido = nuevoValor;
                            e1SarpullidoPos = position;
                            break;
                        case E1DESMAYO_CONS:
                            e1Desmayo = nuevoValor;
                            e1DesmayoPos = position;
                            break;
                        case E1QUEDOCAMA_CONS:
                            e1QuedoCama = nuevoValor;
                            e1QuedoCamaPos = position;
                            break;
                        case E2FEBRICULA_CONS:
                            e2Febricula = nuevoValor;
                            e2FebriculaPos = position;
                            break;
                        case E2FIEBRE_CONS:
                            e2Fiebre = nuevoValor;
                            e2FiebrePos = position;
                            break;
                        case E2ESCALOFRIO_CONS:
                            e2Escalofrio = nuevoValor;
                            e2EscalofrioPos = position;
                            break;
                        case E2TEMBLORESCALOFRIO_CONS:
                            e2TemblorEscalofrio = nuevoValor;
                            e2TemblorEscalofrioPos = position;
                            break;
                        case E2DOLORMUSCULAR_CONS:
                            e2DolorMuscular = nuevoValor;
                            e2DolorMuscularPos = position;
                            break;
                        case E2DOLORARTICULAR_CONS:
                            e2DolorArticular = nuevoValor;
                            e2DolorArticularPos = position;
                            break;
                        case E2SECRESIONNASAL_CONS:
                            e2SecresionNasal = nuevoValor;
                            e2SecresionNasalPos = position;
                            break;
                        case E2DOLORGARGANTA_CONS:
                            e2DolorGargantaPos = position;
                            e2DolorGarganta = nuevoValor;
                            break;
                        case E2TOS_CONS:
                            e2TosPos = position;
                            e2Tos = nuevoValor;
                            break;
                        case E2DIFICULTADRESP_CONS:
                            e2DificultadResp = nuevoValor;
                            e2DificultadRespPos = position;
                            break;
                        case E2DOLORPECHO_CONS:
                            e2DolorPecho = nuevoValor;
                            e2DolorPechoPos = position;
                            break;
                        case E2NAUSEASVOMITO_CONS:
                            e2NauseasVomito = nuevoValor;
                            e2NauseasVomitoPos = position;
                            break;
                        case E2DOLORCABEZA_CONS:
                            e2DolorCabeza = nuevoValor;
                            e2DolorCabezaPos = position;
                            break;
                        case E2DOLORABDOMINAL_CONS:
                            e2DolorAbdominal = nuevoValor;
                            e2DolorAbdominalPos = position;
                            break;
                        case E2DIARREA_CONS:
                            e2Diarrea = nuevoValor;
                            e2DiarreaPos = position;
                            break;
                        case E2DIFICULTADDORMIR_CONS:
                            e2DificultadDormirPos = position;
                            e2DificultadDormir = nuevoValor;
                            break;
                        case E2DEBILIDAD_CONS:
                            e2Debilidad = nuevoValor;
                            e2DebilidadPos = position;
                            break;
                        case E2PERDIDAOLFATOGUSTO_CONS:
                            e2PerdidaOlfatoGusto = nuevoValor;
                            e2PerdidaOlfatoGustoPos = position;
                            break;
                        case E2MAREO_CONS:
                            e2Mareo = nuevoValor;
                            e2MareoPos = position;
                            break;
                        case E2SARPULLIDO_CONS:
                            e2Sarpullido = nuevoValor;
                            e2SarpullidoPos = position;
                            break;
                        case E2DESMAYO_CONS:
                            e2Desmayo = nuevoValor;
                            e2DesmayoPos = position;
                            break;
                        case E2QUEDOCAMA_CONS:
                            e2QuedoCama = nuevoValor;
                            e2QuedoCamaPos = position;
                            break;
                        case E3FEBRICULA_CONS:
                            e3Febricula = nuevoValor;
                            e3FebriculaPos = position;
                            break;
                        case E3FIEBRE_CONS:
                            e3Fiebre = nuevoValor;
                            e3FiebrePos = position;
                            break;
                        case E3ESCALOFRIO_CONS:
                            e3Escalofrio = nuevoValor;
                            e3EscalofrioPos = position;
                            break;
                        case E3TEMBLORESCALOFRIO_CONS:
                            e3TemblorEscalofrio = nuevoValor;
                            e3TemblorEscalofrioPos = position;
                            break;
                        case E3DOLORMUSCULAR_CONS:
                            e3DolorMuscular = nuevoValor;
                            e3DolorMuscularPos = position;
                            break;
                        case E3DOLORARTICULAR_CONS:
                            e3DolorArticular = nuevoValor;
                            e3DolorArticularPos = position;
                            break;
                        case E3SECRESIONNASAL_CONS:
                            e3SecresionNasal = nuevoValor;
                            e3SecresionNasalPos = position;
                            break;
                        case E3DOLORGARGANTA_CONS:
                            e3DolorGargantaPos = position;
                            e3DolorGarganta = nuevoValor;
                            break;
                        case E3TOS_CONS:
                            e3TosPos = position;
                            e3Tos = nuevoValor;
                            break;
                        case E3DIFICULTADRESP_CONS:
                            e3DificultadResp = nuevoValor;
                            e3DificultadRespPos = position;
                            break;
                        case E3DOLORPECHO_CONS:
                            e3DolorPecho = nuevoValor;
                            e3DolorPechoPos = position;
                            break;
                        case E3NAUSEASVOMITO_CONS:
                            e3NauseasVomito = nuevoValor;
                            e3NauseasVomitoPos = position;
                            break;
                        case E3DOLORCABEZA_CONS:
                            e3DolorCabeza = nuevoValor;
                            e3DolorCabezaPos = position;
                            break;
                        case E3DOLORABDOMINAL_CONS:
                            e3DolorAbdominal = nuevoValor;
                            e3DolorAbdominalPos = position;
                            break;
                        case E3DIARREA_CONS:
                            e3Diarrea = nuevoValor;
                            e3DiarreaPos = position;
                            break;
                        case E3DIFICULTADDORMIR_CONS:
                            e3DificultadDormirPos = position;
                            e3DificultadDormir = nuevoValor;
                            break;
                        case E3DEBILIDAD_CONS:
                            e3Debilidad = nuevoValor;
                            e3DebilidadPos = position;
                            break;
                        case E3PERDIDAOLFATOGUSTO_CONS:
                            e3PerdidaOlfatoGusto = nuevoValor;
                            e3PerdidaOlfatoGustoPos = position;
                            break;
                        case E3MAREO_CONS:
                            e3Mareo = nuevoValor;
                            e3MareoPos = position;
                            break;
                        case E3SARPULLIDO_CONS:
                            e3Sarpullido = nuevoValor;
                            e3SarpullidoPos = position;
                            break;
                        case E3DESMAYO_CONS:
                            e3Desmayo = nuevoValor;
                            e3DesmayoPos = position;
                            break;
                        case E3QUEDOCAMA_CONS:
                            e3QuedoCama = nuevoValor;
                            e3QuedoCamaPos = position;
                            break;

                        case E1SABEFIS_CONS:
                            e1SabeFIS = nuevoValor;
                            e1SabeFISPos = position;
                            break;
                        case E1MESINICIOSINTOMA_CONS:
                            e1MesInicioSintoma = nuevoValor;
                            e1MesInicioSintomaPos = position;
                            break;
                        case E1CONOCELUGAREXPOSICION_CONS:
                            e1ConoceLugarExposicion = nuevoValor;
                            e1ConoceLugarExposicionPos = position;
                            break;
                        case E1BUSCOAYUDA_CONS:
                            e1BuscoAyuda = nuevoValor;
                            e1BuscoAyudaPos = position;
                            break;
                        case E1DONDEBUSCOAYUDA_CONS:
                            e1DondeBuscoAyuda = nuevoValor;
                            e1DondeBuscoAyudaPos = position;
                            break;
                        case E1RECIBIOSEGUIMIENTO_CONS:
                            e1RecibioSeguimiento = nuevoValor;
                            e1RecibioSeguimientoPos = position;
                            break;
                        case E1TIPOSEGUIMIENTO_CONS:
                            e1TipoSeguimiento = nuevoValor;
                            e1TipoSeguimientoPos = position;
                            break;
                        case E1TMPDESPUESBUSCOAYUDA_CONS:
                            e1TmpDespuesBuscoAyuda = nuevoValor;
                            e1TmpDespuesBuscoAyudaPos = position;
                            break;
                        case E1UNANOCHEHOSPITAL_CONS:
                            e1UnaNocheHospital = nuevoValor;
                            e1UnaNocheHospitalPos = position;
                            break;
                        case E1SABECUANTASNOCHES_CONS:
                            e1SabeCuantasNoches = nuevoValor;
                            e1SabeCuantasNochesPos = position;
                            break;
                        case E1SABEFECHAADMISION_CONS:
                            e1SabeFechaAdmision = nuevoValor;
                            e1sabeFechaAdmisionPos = position;
                            break;
                        case E1SABEFECHAALTA_CONS:
                            e1SabeFechaAlta = nuevoValor;
                            e1SabeFechaAltaPos = position;
                            break;
                        case E1UTILIZOOXIGENO_CONS:
                            e1UtilizoOxigeno = nuevoValor;
                            e1UtilizoOxigenoPos = position;
                            break;
                        case E1ESTUVOUCI_CONS:
                            e1EstuvoUCI = nuevoValor;
                            e1EstuvoUCIPos = position;
                            break;
                        case E1FUEINTUBADO_CONS:
                            e1FueIntubado = nuevoValor;
                            e1FueIntubadoPos = position;
                            break;
                        case E1RECUPERADOCOVID19_CONS:
                            e1RecuperadoCovid19 = nuevoValor;
                            e1RecuperadoCovid19Pos = position;
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
                        case E1TOMOMEDICAMENTO_CONS:
                            e1TomoMedicamento = nuevoValor;
                            e1TomoMedicamentoPos = position;
                            break;
                        case E1QUEMEDICAMENTO_CONS:
                            e1QueMedicamento = nuevoValor;
                            e1QueMedicamentoPos = position;
                            break;
                        case E1OXIGENODOMICILIO_CONS:
                            e1OxigenoDomicilio = nuevoValor;
                            e1OxigenoDomicilioPos = position;
                            break;
                        case E1TIEMPOOXIGENODOM_CONS:
                            e1TiempoOxigenoDom = nuevoValor;
                            e1TiempoOxigenoDomPos = position;
                            break;

                        case E2SABEFIS_CONS:
                            e2SabeFIS = nuevoValor;
                            e2SabeFISPos = position;
                            break;
                        case E2MESINICIOSINTOMA_CONS:
                            e2MesInicioSintoma = nuevoValor;
                            e2MesInicioSintomaPos = position;
                            break;
                        case E2CONOCELUGAREXPOSICION_CONS:
                            e2ConoceLugarExposicion = nuevoValor;
                            e2ConoceLugarExposicionPos = position;
                            break;
                        case E2BUSCOAYUDA_CONS:
                            e2BuscoAyuda = nuevoValor;
                            e2BuscoAyudaPos = position;
                            break;
                        case E2DONDEBUSCOAYUDA_CONS:
                            e2DondeBuscoAyuda = nuevoValor;
                            e2DondeBuscoAyudaPos = position;
                            break;
                        case E2RECIBIOSEGUIMIENTO_CONS:
                            e2RecibioSeguimiento = nuevoValor;
                            e2RecibioSeguimientoPos = position;
                            break;
                        case E2TIPOSEGUIMIENTO_CONS:
                            e2TipoSeguimiento = nuevoValor;
                            e2TipoSeguimientoPos = position;
                            break;
                        case E2TMPDESPUESBUSCOAYUDA_CONS:
                            e2TmpDespuesBuscoAyuda = nuevoValor;
                            e2TmpDespuesBuscoAyudaPos = position;
                            break;
                        case E2UNANOCHEHOSPITAL_CONS:
                            e2UnaNocheHospital = nuevoValor;
                            e2UnaNocheHospitalPos = position;
                            break;
                        case E2SABECUANTASNOCHES_CONS:
                            e2SabeCuantasNoches = nuevoValor;
                            e2SabeCuantasNochesPos = position;
                            break;
                        case E2SABEFECHAADMISION_CONS:
                            e2SabeFechaAdmision = nuevoValor;
                            e2sabeFechaAdmisionPos = position;
                            break;
                        case E2SABEFECHAALTA_CONS:
                            e2SabeFechaAlta = nuevoValor;
                            e2SabeFechaAltaPos = position;
                            break;
                        case E2UTILIZOOXIGENO_CONS:
                            e2UtilizoOxigeno = nuevoValor;
                            e2UtilizoOxigenoPos = position;
                            break;
                        case E2ESTUVOUCI_CONS:
                            e2EstuvoUCI = nuevoValor;
                            e2EstuvoUCIPos = position;
                            break;
                        case E2FUEINTUBADO_CONS:
                            e2FueIntubado = nuevoValor;
                            e2FueIntubadoPos = position;
                            break;
                        case E2RECUPERADOCOVID19_CONS:
                            e2RecuperadoCovid19 = nuevoValor;
                            e2RecuperadoCovid19Pos = position;
                            break;
                        case E2TIENEFEBRICULA_CONS:
                            e2TieneFebricula = nuevoValor;
                            e2TieneFebriculaPos = position;
                            break;
                        case E2TIENECANSANCIO_CONS:
                            e2TieneCansancio = nuevoValor;
                            e2TieneCansancioPos = position;
                            break;
                        case E2TIENEDOLORCABEZA_CONS:
                            e2TieneDolorCabeza = nuevoValor;
                            e2TieneDolorCabezaPos = position;
                            break;
                        case E2TIENEPERDIDAOLFATO_CONS:
                            e2TienePerdidaOlfato = nuevoValor;
                            e2TienePerdidaOlfatoPos = position;
                            break;
                        case E2TIENEPERDIDAGUSTO_CONS:
                            e2TienePerdidaGusto = nuevoValor;
                            e2TienePerdidaGustoPos = position;
                            break;
                        case E2TIENETOS_CONS:
                            e2TieneTosPos = position;
                            e2TieneTos = nuevoValor;
                            break;
                        case E2TIENEDIFICULTADRESPIRAR_CONS:
                            e2TieneDificultadRespirar = nuevoValor;
                            e2TieneDificultadRespirarPos = position;
                            break;
                        case E2TIENEDOLORPECHO_CONS:
                            e2TieneDolorPecho = nuevoValor;
                            e2TieneDolorPechoPos = position;
                            break;
                        case E2TIENEPALPITACIONES_CONS:
                            e2TienePalpitaciones = nuevoValor;
                            e2TienePalpitacionesPos = position;
                            break;
                        case E2TIENEDOLORARTICULACIONES_CONS:
                            e2TieneDolorArticulaciones = nuevoValor;
                            e2TieneDolorArticulacionesPos = position;
                            break;
                        case E2TIENEPARALISIS_CONS:
                            e2TieneParalisis = nuevoValor;
                            e2TieneParalisisPos = position;
                            break;
                        case E2TIENEMAREOS_CONS:
                            e2TieneMareos = nuevoValor;
                            e2TieneMareosPos = position;
                            break;
                        case E2TIENEPENSAMIENTONUBLADO_CONS:
                            e2TienePensamientoNublado = nuevoValor;
                            e2TienePensamientoNubladoPos = position;
                            break;
                        case E2TIENEPROBLEMASDORMIR_CONS:
                            e2TieneProblemasDormir = nuevoValor;
                            e2TieneProblemasDormirPos = position;
                            break;
                        case E2TIENEDEPRESION_CONS:
                            e2TieneDepresion = nuevoValor;
                            e2TieneDepresionPos = position;
                            break;
                        case E2TIENEOTROSSINTOMAS_CONS:
                            e2TieneOtrosSintomas = nuevoValor;
                            e2TieneOtrosSintomasPos = position;
                            break;
                        case E2SABETIEMPORECUPERACION_CONS:
                            e2SabeTiempoRecuperacion = nuevoValor;
                            e2SabeTiempoRecuperacionPos = position;
                            break;
                        case E2TIEMPORECUPERACIONEN_CONS:
                            e2TiempoRecuperacionEn = nuevoValor;
                            e2TiempoRecuperacionEnPos = position;
                            break;
                        case E2SEVERIDADENFERMEDAD_CONS:
                            e2SeveridadEnfermedad = nuevoValor;
                            e2SeveridadEnfermedadPos = position;
                            break;
                        case E2TOMOMEDICAMENTO_CONS:
                            e2TomoMedicamento = nuevoValor;
                            e2TomoMedicamentoPos = position;
                            break;
                        case E2QUEMEDICAMENTO_CONS:
                            e2QueMedicamento = nuevoValor;
                            e2QueMedicamentoPos = position;
                            break;
                        case E2OXIGENODOMICILIO_CONS:
                            e2OxigenoDomicilio = nuevoValor;
                            e2OxigenoDomicilioPos = position;
                            break;
                        case E2TIEMPOOXIGENODOM_CONS:
                            e2TiempoOxigenoDom = nuevoValor;
                            e2TiempoOxigenoDomPos = position;
                            break;

                        case E3SABEFIS_CONS:
                            e3SabeFIS = nuevoValor;
                            e3SabeFISPos = position;
                            break;
                        case E3MESINICIOSINTOMA_CONS:
                            e3MesInicioSintoma = nuevoValor;
                            e3MesInicioSintomaPos = position;
                            break;
                        case E3CONOCELUGAREXPOSICION_CONS:
                            e3ConoceLugarExposicion = nuevoValor;
                            e3ConoceLugarExposicionPos = position;
                            break;
                        case E3BUSCOAYUDA_CONS:
                            e3BuscoAyuda = nuevoValor;
                            e3BuscoAyudaPos = position;
                            break;
                        case E3DONDEBUSCOAYUDA_CONS:
                            e3DondeBuscoAyuda = nuevoValor;
                            e3DondeBuscoAyudaPos = position;
                            break;
                        case E3RECIBIOSEGUIMIENTO_CONS:
                            e3RecibioSeguimiento = nuevoValor;
                            e3RecibioSeguimientoPos = position;
                            break;
                        case E3TIPOSEGUIMIENTO_CONS:
                            e3TipoSeguimiento = nuevoValor;
                            e3TipoSeguimientoPos = position;
                            break;
                        case E3TMPDESPUESBUSCOAYUDA_CONS:
                            e3TmpDespuesBuscoAyuda = nuevoValor;
                            e3TmpDespuesBuscoAyudaPos = position;
                            break;
                        case E3UNANOCHEHOSPITAL_CONS:
                            e3UnaNocheHospital = nuevoValor;
                            e3UnaNocheHospitalPos = position;
                            break;
                        case E3SABECUANTASNOCHES_CONS:
                            e3SabeCuantasNoches = nuevoValor;
                            e3SabeCuantasNochesPos = position;
                            break;
                        case E3SABEFECHAADMISION_CONS:
                            e3SabeFechaAdmision = nuevoValor;
                            e3sabeFechaAdmisionPos = position;
                            break;
                        case E3SABEFECHAALTA_CONS:
                            e3SabeFechaAlta = nuevoValor;
                            e3SabeFechaAltaPos = position;
                            break;
                        case E3UTILIZOOXIGENO_CONS:
                            e3UtilizoOxigeno = nuevoValor;
                            e3UtilizoOxigenoPos = position;
                            break;
                        case E3ESTUVOUCI_CONS:
                            e3EstuvoUCI = nuevoValor;
                            e3EstuvoUCIPos = position;
                            break;
                        case E3FUEINTUBADO_CONS:
                            e3FueIntubado = nuevoValor;
                            e3FueIntubadoPos = position;
                            break;
                        case E3RECUPERADOCOVID19_CONS:
                            e3RecuperadoCovid19 = nuevoValor;
                            e3RecuperadoCovid19Pos = position;
                            break;
                        case E3TIENEFEBRICULA_CONS:
                            e3TieneFebricula = nuevoValor;
                            e3TieneFebriculaPos = position;
                            break;
                        case E3TIENECANSANCIO_CONS:
                            e3TieneCansancio = nuevoValor;
                            e3TieneCansancioPos = position;
                            break;
                        case E3TIENEDOLORCABEZA_CONS:
                            e3TieneDolorCabeza = nuevoValor;
                            e3TieneDolorCabezaPos = position;
                            break;
                        case E3TIENEPERDIDAOLFATO_CONS:
                            e3TienePerdidaOlfato = nuevoValor;
                            e3TienePerdidaOlfatoPos = position;
                            break;
                        case E3TIENEPERDIDAGUSTO_CONS:
                            e3TienePerdidaGusto = nuevoValor;
                            e3TienePerdidaGustoPos = position;
                            break;
                        case E3TIENETOS_CONS:
                            e3TieneTosPos = position;
                            e3TieneTos = nuevoValor;
                            break;
                        case E3TIENEDIFICULTADRESPIRAR_CONS:
                            e3TieneDificultadRespirar = nuevoValor;
                            e3TieneDificultadRespirarPos = position;
                            break;
                        case E3TIENEDOLORPECHO_CONS:
                            e3TieneDolorPecho = nuevoValor;
                            e3TieneDolorPechoPos = position;
                            break;
                        case E3TIENEPALPITACIONES_CONS:
                            e3TienePalpitaciones = nuevoValor;
                            e3TienePalpitacionesPos = position;
                            break;
                        case E3TIENEDOLORARTICULACIONES_CONS:
                            e3TieneDolorArticulaciones = nuevoValor;
                            e3TieneDolorArticulacionesPos = position;
                            break;
                        case E3TIENEPARALISIS_CONS:
                            e3TieneParalisis = nuevoValor;
                            e3TieneParalisisPos = position;
                            break;
                        case E3TIENEMAREOS_CONS:
                            e3TieneMareos = nuevoValor;
                            e3TieneMareosPos = position;
                            break;
                        case E3TIENEPENSAMIENTONUBLADO_CONS:
                            e3TienePensamientoNublado = nuevoValor;
                            e3TienePensamientoNubladoPos = position;
                            break;
                        case E3TIENEPROBLEMASDORMIR_CONS:
                            e3TieneProblemasDormir = nuevoValor;
                            e3TieneProblemasDormirPos = position;
                            break;
                        case E3TIENEDEPRESION_CONS:
                            e3TieneDepresion = nuevoValor;
                            e3TieneDepresionPos = position;
                            break;
                        case E3TIENEOTROSSINTOMAS_CONS:
                            e3TieneOtrosSintomas = nuevoValor;
                            e3TieneOtrosSintomasPos = position;
                            break;
                        case E3SABETIEMPORECUPERACION_CONS:
                            e3SabeTiempoRecuperacion = nuevoValor;
                            e3SabeTiempoRecuperacionPos = position;
                            break;
                        case E3TIEMPORECUPERACIONEN_CONS:
                            e3TiempoRecuperacionEn = nuevoValor;
                            e3TiempoRecuperacionEnPos = position;
                            break;
                        case E3SEVERIDADENFERMEDAD_CONS:
                            e3SeveridadEnfermedad = nuevoValor;
                            e3SeveridadEnfermedadPos = position;
                            break;
                        case E3TOMOMEDICAMENTO_CONS:
                            e3TomoMedicamento = nuevoValor;
                            e3TomoMedicamentoPos = position;
                            break;
                        case E3QUEMEDICAMENTO_CONS:
                            e3QueMedicamento = nuevoValor;
                            e3QueMedicamentoPos = position;
                            break;
                        case E3OXIGENODOMICILIO_CONS:
                            e3OxigenoDomicilio = nuevoValor;
                            e3OxigenoDomicilioPos = position;
                            break;
                        case E3TIEMPOOXIGENODOM_CONS:
                            e3TiempoOxigenoDom = nuevoValor;
                            e3TiempoOxigenoDomPos = position;
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

                        case E2FUMADOPREVIOENFERMEDAD_CONS:
                            e2FumadoPrevioEnfermedad = nuevoValor;
                            e2FumadoPrevioEnfermedadPos = position;
                            break;
                        case E2FUMAACTUALMENTE_CONS:
                            e2FumaActualmente = nuevoValor;
                            e2FumaActualmentePos = position;
                            break;
                        case E2EMBARAZADA_CONS:
                            e2Embarazada = nuevoValor;
                            e2EmbarazadaPos = position;
                            break;
                        case E2RECUERDASEMANASEMB_CONS:
                            e2RecuerdaSemanasEmb = nuevoValor;
                            e2RecuerdaSemanasEmbPos = position;
                            break;
                        case E2FINALEMBARAZO_CONS:
                            e2FinalEmbarazo = nuevoValor;
                            e2FinalEmbarazoPos = position;
                            break;
                        case E2DABAPECHO_CONS:
                            e2DabaPecho = nuevoValor;
                            e2DabaPechoPos = position;
                            break;

                        case E3FUMADOPREVIOENFERMEDAD_CONS:
                            e3FumadoPrevioEnfermedad = nuevoValor;
                            e3FumadoPrevioEnfermedadPos = position;
                            break;
                        case E3FUMAACTUALMENTE_CONS:
                            e3FumaActualmente = nuevoValor;
                            e3FumaActualmentePos = position;
                            break;
                        case E3EMBARAZADA_CONS:
                            e3Embarazada = nuevoValor;
                            e3EmbarazadaPos = position;
                            break;
                        case E3RECUERDASEMANASEMB_CONS:
                            e3RecuerdaSemanasEmb = nuevoValor;
                            e3RecuerdaSemanasEmbPos = position;
                            break;
                        case E3FINALEMBARAZO_CONS:
                            e3FinalEmbarazo = nuevoValor;
                            e3FinalEmbarazoPos = position;
                            break;
                        case E3DABAPECHO_CONS:
                            e3DabaPecho = nuevoValor;
                            e3DabaPechoPos = position;
                            break;

                        case TRABAJADORSALUD_CONS:
                            trabajadorSalud = nuevoValor;
                            trabajadorSaludPos = position;
                            break;
                        case VACUNADOCOVID19_CONS :
                            vacunadoCovid19 = nuevoValor;
                            vacunadoCovid19Pos = position;
                            break;
                        case MUESTRATARJETAVAC_CONS :
                            muestraTarjetaVac = nuevoValor;
                            muestraTarjetaVacPos = position;
                            break;
                        case SABENOMBREVACUNA_CONS :
                            sabeNombreVacuna = nuevoValor;
                            sabeNombreVacunaPos = position;
                            break;
                        case MESVACUNA_CONS :
                            mesVacuna = nuevoValor;
                            mesVacunaPos = position;
                            break;
                        case CUANTASDOSIS_CONS :
                            cuantasDosis = nuevoValor;
                            cuantasDosisPos = position;
                            break;
                        case NOMBREDOSIS1_CONS :
                            nombreDosis1 = nuevoValor;
                            nombreDosis1Pos = position;
                            break;
                        case NOMBREDOSIS2_CONS :
                            nombreDosis2 = nuevoValor;
                            nombreDosis2Pos = position;
                            break;
                        case NOMBREDOSIS3_CONS :
                            nombreDosis3 = nuevoValor;
                            nombreDosis3Pos = position;
                            break;

                        case PRESENTOSINTOMASDOSIS1_CONS:
                            presentoSintomasDosis1 = nuevoValor;
                            presentoSintomasDosis1Pos = position;
                            break;
                        case DOLORSITIODOSIS1_CONS:
                            dolorSitioDosis1 = nuevoValor;
                            dolorSitioDosis1Pos = position;
                            break;
                        case FIEBREDOSIS1_CONS:
                            fiebreDosis1 = nuevoValor;
                            fiebreDosis1Pos = position;
                            break;
                        case CANSANCIODOSIS1_CONS:
                            cansancioDosis1 = nuevoValor;
                            cansancioDosis1Pos = position;
                            break;
                        case DOLORCABEZADOSIS1_CONS:
                            dolorCabezaDosis1 = nuevoValor;
                            dolorCabezaDosis1Pos = position;
                            break;
                        case DIARREADOSIS1_CONS:
                            diarreaDosis1 = nuevoValor;
                            diarreaDosis1Pos = position;
                            break;
                        case VOMITODOSIS1_CONS:
                            vomitoDosis1 = nuevoValor;
                            vomitoDosis1Pos = position;
                            break;
                        case DOLORMUSCULARDOSIS1_CONS:
                            dolorMuscularDosis1 = nuevoValor;
                            dolorMuscularDosis1Pos = position;
                            break;
                        case ESCALOFRIOSDOSIS1_CONS:
                            escalofriosDosis1 = nuevoValor;
                            escalofriosDosis1Pos = position;
                            break;
                        case REACCIONALERGICADOSIS1_CONS:
                            reaccionAlergicaDosis1 = nuevoValor;
                            reaccionAlergicaDosis1Pos = position;
                            break;
                        case INFECCIONSITIODOSIS1_CONS:
                            infeccionSitioDosis1 = nuevoValor;
                            infeccionSitioDosis1Pos = position;
                            break;
                        case NAUSEASDOSIS1_CONS:
                            nauseasDosis1 = nuevoValor;
                            nauseasDosis1Pos = position;
                            break;
                        case BULTOSDOSIS1_CONS:
                            bultosDosis1 = nuevoValor;
                            bultosDosis1Pos = position;
                            break;
                        case OTROSDOSIS1_CONS:
                            otrosDosis1 = nuevoValor;
                            otrosDosis1Pos = position;
                            break;

                        case PRESENTOSINTOMASDOSIS2_CONS:
                            presentoSintomasDosis2 = nuevoValor;
                            presentoSintomasDosis2Pos = position;
                            break;
                        case DOLORSITIODOSIS2_CONS:
                            dolorSitioDosis2 = nuevoValor;
                            dolorSitioDosis2Pos = position;
                            break;
                        case FIEBREDOSIS2_CONS:
                            fiebreDosis2 = nuevoValor;
                            fiebreDosis2Pos = position;
                            break;
                        case CANSANCIODOSIS2_CONS:
                            cansancioDosis2 = nuevoValor;
                            cansancioDosis2Pos = position;
                            break;
                        case DOLORCABEZADOSIS2_CONS:
                            dolorCabezaDosis2 = nuevoValor;
                            dolorCabezaDosis2Pos = position;
                            break;
                        case DIARREADOSIS2_CONS:
                            diarreaDosis2 = nuevoValor;
                            diarreaDosis2Pos = position;
                            break;
                        case VOMITODOSIS2_CONS:
                            vomitoDosis2 = nuevoValor;
                            vomitoDosis2Pos = position;
                            break;
                        case DOLORMUSCULARDOSIS2_CONS:
                            dolorMuscularDosis2 = nuevoValor;
                            dolorMuscularDosis2Pos = position;
                            break;
                        case ESCALOFRIOSDOSIS2_CONS:
                            escalofriosDosis2 = nuevoValor;
                            escalofriosDosis2Pos = position;
                            break;
                        case REACCIONALERGICADOSIS2_CONS:
                            reaccionAlergicaDosis2 = nuevoValor;
                            reaccionAlergicaDosis2Pos = position;
                            break;
                        case INFECCIONSITIODOSIS2_CONS:
                            infeccionSitioDosis2 = nuevoValor;
                            infeccionSitioDosis2Pos = position;
                            break;
                        case NAUSEASDOSIS2_CONS:
                            nauseasDosis2 = nuevoValor;
                            nauseasDosis2Pos = position;
                            break;
                        case BULTOSDOSIS2_CONS:
                            bultosDosis2 = nuevoValor;
                            bultosDosis2Pos = position;
                            break;
                        case OTROSDOSIS2_CONS:
                            otrosDosis2 = nuevoValor;
                            otrosDosis2Pos = position;
                            break;

                        case PRESENTOSINTOMASDOSIS3_CONS:
                            presentoSintomasDosis3 = nuevoValor;
                            presentoSintomasDosis3Pos = position;
                            break;
                        case DOLORSITIODOSIS3_CONS:
                            dolorSitioDosis3 = nuevoValor;
                            dolorSitioDosis3Pos = position;
                            break;
                        case FIEBREDOSIS3_CONS:
                            fiebreDosis3 = nuevoValor;
                            fiebreDosis3Pos = position;
                            break;
                        case CANSANCIODOSIS3_CONS:
                            cansancioDosis3 = nuevoValor;
                            cansancioDosis3Pos = position;
                            break;
                        case DOLORCABEZADOSIS3_CONS:
                            dolorCabezaDosis3 = nuevoValor;
                            dolorCabezaDosis3Pos = position;
                            break;
                        case DIARREADOSIS3_CONS:
                            diarreaDosis3 = nuevoValor;
                            diarreaDosis3Pos = position;
                            break;
                        case VOMITODOSIS3_CONS:
                            vomitoDosis3 = nuevoValor;
                            vomitoDosis3Pos = position;
                            break;
                        case DOLORMUSCULARDOSIS3_CONS:
                            dolorMuscularDosis3 = nuevoValor;
                            dolorMuscularDosis3Pos = position;
                            break;
                        case ESCALOFRIOSDOSIS3_CONS:
                            escalofriosDosis3 = nuevoValor;
                            escalofriosDosis3Pos = position;
                            break;
                        case REACCIONALERGICADOSIS3_CONS:
                            reaccionAlergicaDosis3 = nuevoValor;
                            reaccionAlergicaDosis3Pos = position;
                            break;
                        case INFECCIONSITIODOSIS3_CONS:
                            infeccionSitioDosis3 = nuevoValor;
                            infeccionSitioDosis3Pos = position;
                            break;
                        case NAUSEASDOSIS3_CONS:
                            nauseasDosis3 = nuevoValor;
                            nauseasDosis3Pos = position;
                            break;
                        case BULTOSDOSIS3_CONS:
                            bultosDosis3 = nuevoValor;
                            bultosDosis3Pos = position;
                            break;
                        case OTROSDOSIS3_CONS:
                            otrosDosis3 = nuevoValor;
                            otrosDosis3Pos = position;
                            break;
                        case COVID19POSTERIORVACUNA_CONS :
                            covid19PosteriorVacuna = nuevoValor;
                            covid19PosteriorVacunaPos = position;
                            break;
                        case FECHAEVENTOENFERMOPOSTVAC_CONS:
                            fechaEventoEnfermoPostVac = nuevoValor;
                            fechaEventoEnfermoPostVacPos = position;
                            break;
                        /*case SABEFECHAENFPOSTVAC_CONS :
                            sabeFechaEnfPostVac = nuevoValor;
                            sabeFechaEnfPostVacPos = position;
                            break;
                        case MESFECHAENFPOSTVAC_CONS :
                            mesEnfPostVac = nuevoValor;
                            mesEnfPostVacPos = position;
                            break;*/
                        default: break;
                    }
                }
            });
            builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    mostrarConfirmacion = false;
                    switch (pregunta){
                        case ENFERMOCOVID19_CONS:
                            spinEnfermoCovid19.setSelection(enfermoCovid19Pos, false);
                            break;
                        case CUANTASVECESENFERMO_CONS:
                            spinCuantasVecesEnfermo.setSelection(cuantasVecesEnfermoPos, false);
                            break;
                        case MESEVENTO1_CONS:
                            spinMesEvento1.setSelection(mesEvento1Pos, false);
                            break;
                        case MESEVENTO2_CONS:
                            spinMesEvento2.setSelection(mesEvento2Pos, false);
                            break;
                        case MESEVENTO3_CONS:
                            spinMesEvento3.setSelection(mesEvento3Pos, false);
                            break;
                        case E1FEBRICULA_CONS:
                            spinE1Febricula.setSelection(e1FebriculaPos, false);
                            break;
                        case E1FIEBRE_CONS:
                            spinE1Fiebre.setSelection(e1FiebrePos, false);
                            break;
                        case E1ESCALOFRIO_CONS:
                            spinE1Escalofrio.setSelection(e1EscalofrioPos, false);
                            break;
                        case E1TEMBLORESCALOFRIO_CONS:
                            spinE1TemblorEscalofrio.setSelection(e1TemblorEscalofrioPos, false);
                            break;
                        case E1DOLORMUSCULAR_CONS:
                            spinE1DolorMuscular.setSelection(e1DolorMuscularPos, false);
                            break;
                        case E1DOLORARTICULAR_CONS:
                            spinE1DolorArticular.setSelection(e1DolorArticularPos, false);
                            break;
                        case E1SECRESIONNASAL_CONS:
                            spinE1SecresionNasal.setSelection(e1SecresionNasalPos, false);
                            break;
                        case E1DOLORGARGANTA_CONS:
                            spinE1DolorGarganta.setSelection(e1DolorGargantaPos, false);
                            break;
                        case E1TOS_CONS:
                            spinE1Tos.setSelection(e1TosPos, false);
                            break;
                        case E1DIFICULTADRESP_CONS:
                            spinE1DificultadResp.setSelection(e1DificultadRespPos, false);
                            break;
                        case E1DOLORPECHO_CONS:
                            spinE1DolorPecho.setSelection(e1DolorPechoPos, false);
                            break;
                        case E1NAUSEASVOMITO_CONS:
                            spinE1NauseasVomito.setSelection(e1NauseasVomitoPos, false);
                            break;
                        case E1DOLORCABEZA_CONS:
                            spinE1DolorCabeza.setSelection(e1DolorCabezaPos, false);
                            break;
                        case E1DOLORABDOMINAL_CONS:
                            spinE1DolorAbdominal.setSelection(e1DolorAbdominalPos, false);
                            break;
                        case E1DIARREA_CONS:
                            spinE1Diarrea.setSelection(e1DiarreaPos, false);
                            break;
                        case E1DIFICULTADDORMIR_CONS:
                            spinE1DificultadDormir.setSelection(e1DificultadDormirPos, false);
                            break;
                        case E1DEBILIDAD_CONS:
                            spinE1Debilidad.setSelection(e1DebilidadPos, false);
                            break;
                        case E1PERDIDAOLFATOGUSTO_CONS:
                            spinE1PerdidaOlfatoGusto.setSelection(e1PerdidaOlfatoGustoPos, false);
                            break;
                        case E1MAREO_CONS:
                            spinE1Mareo.setSelection(e1MareoPos, false);
                            break;
                        case E1SARPULLIDO_CONS:
                            spinE1Sarpullido.setSelection(e1SarpullidoPos, false);
                            break;
                        case E1DESMAYO_CONS:
                            spinE1Desmayo.setSelection(e1DesmayoPos, false);
                            break;
                        case E1QUEDOCAMA_CONS:
                            spinE1QuedoCama.setSelection(e1QuedoCamaPos, false);
                            break;
                        case E2FEBRICULA_CONS:
                            spinE2Febricula.setSelection(e2FebriculaPos, false);
                            break;
                        case E2FIEBRE_CONS:
                            spinE2Fiebre.setSelection(e2FiebrePos, false);
                            break;
                        case E2ESCALOFRIO_CONS:
                            spinE2Escalofrio.setSelection(e2EscalofrioPos, false);
                            break;
                        case E2TEMBLORESCALOFRIO_CONS:
                            spinE2TemblorEscalofrio.setSelection(e2TemblorEscalofrioPos, false);
                            break;
                        case E2DOLORMUSCULAR_CONS:
                            spinE2DolorMuscular.setSelection(e2DolorMuscularPos, false);
                            break;
                        case E2DOLORARTICULAR_CONS:
                            spinE2DolorArticular.setSelection(e2DolorArticularPos, false);
                            break;
                        case E2SECRESIONNASAL_CONS:
                            spinE2SecresionNasal.setSelection(e2SecresionNasalPos, false);
                            break;
                        case E2DOLORGARGANTA_CONS:
                            spinE2DolorGarganta.setSelection(e2DolorGargantaPos, false);
                            break;
                        case E2TOS_CONS:
                            spinE2Tos.setSelection(e2TosPos, false);
                            break;
                        case E2DIFICULTADRESP_CONS:
                            spinE2DificultadResp.setSelection(e2DificultadRespPos, false);
                            break;
                        case E2DOLORPECHO_CONS:
                            spinE2DolorPecho.setSelection(e2DolorPechoPos, false);
                            break;
                        case E2NAUSEASVOMITO_CONS:
                            spinE2NauseasVomito.setSelection(e2NauseasVomitoPos, false);
                            break;
                        case E2DOLORCABEZA_CONS:
                            spinE2DolorCabeza.setSelection(e2DolorCabezaPos, false);
                            break;
                        case E2DOLORABDOMINAL_CONS:
                            spinE2DolorAbdominal.setSelection(e2DolorAbdominalPos, false);
                            break;
                        case E2DIARREA_CONS:
                            spinE2Diarrea.setSelection(e2DiarreaPos, false);
                            break;
                        case E2DIFICULTADDORMIR_CONS:
                            spinE2DificultadDormir.setSelection(e2DificultadDormirPos, false);
                            break;
                        case E2DEBILIDAD_CONS:
                            spinE2Debilidad.setSelection(e2DebilidadPos, false);
                            break;
                        case E2PERDIDAOLFATOGUSTO_CONS:
                            spinE2PerdidaOlfatoGusto.setSelection(e2PerdidaOlfatoGustoPos, false);
                            break;
                        case E2MAREO_CONS:
                            spinE2Mareo.setSelection(e2MareoPos, false);
                            break;
                        case E2SARPULLIDO_CONS:
                            spinE2Sarpullido.setSelection(e2SarpullidoPos, false);
                            break;
                        case E2DESMAYO_CONS:
                            spinE2Desmayo.setSelection(e2DesmayoPos, false);
                            break;
                        case E2QUEDOCAMA_CONS:
                            spinE2QuedoCama.setSelection(e2QuedoCamaPos, false);
                            break;
                        case E3FEBRICULA_CONS:
                            spinE3Febricula.setSelection(e3FebriculaPos, false);
                            break;
                        case E3FIEBRE_CONS:
                            spinE3Fiebre.setSelection(e3FiebrePos, false);
                            break;
                        case E3ESCALOFRIO_CONS:
                            spinE3Escalofrio.setSelection(e3EscalofrioPos, false);
                            break;
                        case E3TEMBLORESCALOFRIO_CONS:
                            spinE3TemblorEscalofrio.setSelection(e3TemblorEscalofrioPos, false);
                            break;
                        case E3DOLORMUSCULAR_CONS:
                            spinE3DolorMuscular.setSelection(e3DolorMuscularPos, false);
                            break;
                        case E3DOLORARTICULAR_CONS:
                            spinE3DolorArticular.setSelection(e3DolorArticularPos, false);
                            break;
                        case E3SECRESIONNASAL_CONS:
                            spinE3SecresionNasal.setSelection(e3SecresionNasalPos, false);
                            break;
                        case E3DOLORGARGANTA_CONS:
                            spinE3DolorGarganta.setSelection(e3DolorGargantaPos, false);
                            break;
                        case E3TOS_CONS:
                            spinE3Tos.setSelection(e3TosPos, false);
                            break;
                        case E3DIFICULTADRESP_CONS:
                            spinE3DificultadResp.setSelection(e3DificultadRespPos, false);
                            break;
                        case E3DOLORPECHO_CONS:
                            spinE3DolorPecho.setSelection(e3DolorPechoPos, false);
                            break;
                        case E3NAUSEASVOMITO_CONS:
                            spinE3NauseasVomito.setSelection(e3NauseasVomitoPos, false);
                            break;
                        case E3DOLORCABEZA_CONS:
                            spinE3DolorCabeza.setSelection(e3DolorCabezaPos, false);
                            break;
                        case E3DOLORABDOMINAL_CONS:
                            spinE3DolorAbdominal.setSelection(e3DolorAbdominalPos, false);
                            break;
                        case E3DIARREA_CONS:
                            spinE3Diarrea.setSelection(e3DiarreaPos, false);
                            break;
                        case E3DIFICULTADDORMIR_CONS:
                            spinE3DificultadDormir.setSelection(e3DificultadDormirPos, false);
                            break;
                        case E3DEBILIDAD_CONS:
                            spinE3Debilidad.setSelection(e3DebilidadPos, false);
                            break;
                        case E3PERDIDAOLFATOGUSTO_CONS:
                            spinE3PerdidaOlfatoGusto.setSelection(e3PerdidaOlfatoGustoPos, false);
                            break;
                        case E3MAREO_CONS:
                            spinE3Mareo.setSelection(e3MareoPos, false);
                            break;
                        case E3SARPULLIDO_CONS:
                            spinE3Sarpullido.setSelection(e3SarpullidoPos, false);
                            break;
                        case E3DESMAYO_CONS:
                            spinE3Desmayo.setSelection(e3DesmayoPos, false);
                            break;
                        case E3QUEDOCAMA_CONS:
                            spinE3QuedoCama.setSelection(e3QuedoCamaPos, false);
                            break;

                        case E1SABEFIS_CONS:
                            spinE1SabeInicioSintoma.setSelection(e1SabeFISPos, false);
                            break;
                        case E1MESINICIOSINTOMA_CONS:
                            spinE1MesInicioSintoma.setSelection(e1MesInicioSintomaPos, false);
                            break;
                        case E1CONOCELUGAREXPOSICION_CONS:
                            spinE1ConoceLugarExposicion.setSelection(e1ConoceLugarExposicionPos, false);
                            break;
                        case E1BUSCOAYUDA_CONS:
                            spinE1BuscoAyuda.setSelection(e1BuscoAyudaPos, false);
                            break;
                        case E1DONDEBUSCOAYUDA_CONS:
                            spinE1DondeBuscoAyuda.setSelection(e1DondeBuscoAyudaPos, false);
                            break;
                        case E1RECIBIOSEGUIMIENTO_CONS:
                            spinE1RecibioSeguimiento.setSelection(e1RecibioSeguimientoPos, false);
                            break;
                        case E1TIPOSEGUIMIENTO_CONS:
                            spinE1TipoSeguimiento.setSelection(e1TipoSeguimientoPos, false);
                            break;
                        case E1TMPDESPUESBUSCOAYUDA_CONS:
                            spinE1TmpDespuesBuscoAyuda.setSelection(e1TmpDespuesBuscoAyudaPos, false);
                            break;
                        case E1UNANOCHEHOSPITAL_CONS:
                            spinE1UnaNocheHospital.setSelection(e1UnaNocheHospitalPos, false);
                            break;
                        case E1SABECUANTASNOCHES_CONS:
                            spinE1SabeCuantasNoches.setSelection(e1SabeCuantasNochesPos, false);
                            break;
                        case E1SABEFECHAADMISION_CONS:
                            spinE1SabeFechaAdmision.setSelection(e1sabeFechaAdmisionPos, false);
                            break;
                        case E1SABEFECHAALTA_CONS:
                            spinE1SabeFechaAlta.setSelection(e1SabeFechaAltaPos, false);
                            break;
                        case E1UTILIZOOXIGENO_CONS:
                            spinE1UtilizoOxigeno.setSelection(e1UtilizoOxigenoPos, false);
                            break;
                        case E1ESTUVOUCI_CONS:
                            spinE1EstuvoUCI.setSelection(e1EstuvoUCIPos, false);
                            break;
                        case E1FUEINTUBADO_CONS:
                            spinE1FueIntubado.setSelection(e1FueIntubadoPos, false);
                            break;
                        case E1RECUPERADOCOVID19_CONS:
                            spinE1RecuperadoCovid19.setSelection(e1RecuperadoCovid19Pos, false);
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
                        case E1SABETIEMPORECUPERACION_CONS:
                            spinE1SabeTiempoRecuperacion.setSelection(e1SabeTiempoRecuperacionPos, false);
                            break;
                        case E1TIEMPORECUPERACION_CONS:
                            break;
                        case E1TIEMPORECUPERACIONEN_CONS:
                            spinE1TiempoRecuperacionEn.setSelection(e1TiempoRecuperacionEnPos, false);
                            break;
                        case E1SEVERIDADENFERMEDAD_CONS:
                            spinE1SeveridadEnfermedad.setSelection(e1SeveridadEnfermedadPos, false);
                            break;
                        case E1TOMOMEDICAMENTO_CONS:
                            spinE1TomoMedicamento.setSelection(e1TomoMedicamentoPos, false);
                            break;
                        case E1QUEMEDICAMENTO_CONS:
                            spinE1QueMedicamento.setSelection(e1QueMedicamentoPos, false);
                            break;
                        case E1OTROMEDICAMENTO_CONS:
                            break;
                        case E1OXIGENODOMICILIO_CONS:
                            spinE1OxigenoDomicilio.setSelection(e1OxigenoDomicilioPos, false);
                            break;
                        case E1TIEMPOOXIGENODOM_CONS:
                            spinE1TiempoOxigenoDom.setSelection(e1TiempoOxigenoDomPos, false);
                            break;

                        case E2SABEFIS_CONS:
                            spinE2SabeInicioSintoma.setSelection(e2SabeFISPos, false);
                            break;
                        case E2MESINICIOSINTOMA_CONS:
                            spinE2MesInicioSintoma.setSelection(e2MesInicioSintomaPos, false);
                            break;
                        case E2CONOCELUGAREXPOSICION_CONS:
                            spinE2ConoceLugarExposicion.setSelection(e2ConoceLugarExposicionPos, false);
                            break;
                        case E2BUSCOAYUDA_CONS:
                            spinE2BuscoAyuda.setSelection(e2BuscoAyudaPos, false);
                            break;
                        case E2DONDEBUSCOAYUDA_CONS:
                            spinE2DondeBuscoAyuda.setSelection(e2DondeBuscoAyudaPos, false);
                            break;
                        case E2RECIBIOSEGUIMIENTO_CONS:
                            spinE2RecibioSeguimiento.setSelection(e2RecibioSeguimientoPos, false);
                            break;
                        case E2TIPOSEGUIMIENTO_CONS:
                            spinE2TipoSeguimiento.setSelection(e2TipoSeguimientoPos, false);
                            break;
                        case E2TMPDESPUESBUSCOAYUDA_CONS:
                            spinE2TmpDespuesBuscoAyuda.setSelection(e2TmpDespuesBuscoAyudaPos, false);
                            break;
                        case E2UNANOCHEHOSPITAL_CONS:
                            spinE2UnaNocheHospital.setSelection(e2UnaNocheHospitalPos, false);
                            break;
                        case E2SABECUANTASNOCHES_CONS:
                            spinE2SabeCuantasNoches.setSelection(e2SabeCuantasNochesPos, false);
                            break;
                        case E2SABEFECHAADMISION_CONS:
                            spinE2SabeFechaAdmision.setSelection(e2sabeFechaAdmisionPos, false);
                            break;
                        case E2SABEFECHAALTA_CONS:
                            spinE2SabeFechaAlta.setSelection(e2SabeFechaAltaPos, false);
                            break;
                        case E2UTILIZOOXIGENO_CONS:
                            spinE2UtilizoOxigeno.setSelection(e2UtilizoOxigenoPos, false);
                            break;
                        case E2ESTUVOUCI_CONS:
                            spinE2EstuvoUCI.setSelection(e2EstuvoUCIPos, false);
                            break;
                        case E2FUEINTUBADO_CONS:
                            spinE2FueIntubado.setSelection(e2FueIntubadoPos, false);
                            break;
                        case E2RECUPERADOCOVID19_CONS:
                            spinE2RecuperadoCovid19.setSelection(e2RecuperadoCovid19Pos, false);
                            break;
                        case E2TIENEFEBRICULA_CONS:
                            spinE2TieneFebricula.setSelection(e2TieneFebriculaPos, false);
                            break;
                        case E2TIENECANSANCIO_CONS:
                            spinE2TieneCansancio.setSelection(e2TieneCansancioPos, false);
                            break;
                        case E2TIENEDOLORCABEZA_CONS:
                            spinE2TieneDolorCabeza.setSelection(e2TieneDolorCabezaPos, false);
                            break;
                        case E2TIENEPERDIDAOLFATO_CONS:
                            spinE2TienePerdidaOlfato.setSelection(e2TienePerdidaOlfatoPos, false);
                            break;
                        case E2TIENEPERDIDAGUSTO_CONS:
                            spinE2TienePerdidaGusto.setSelection(e2TienePerdidaGustoPos, false);
                            break;
                        case E2TIENETOS_CONS:
                            spinE2TieneTos.setSelection(e2TieneTosPos, false);
                            break;
                        case E2TIENEDIFICULTADRESPIRAR_CONS:
                            spinE2TieneDificultadRespirar.setSelection(e2TieneDificultadRespirarPos, false);
                            break;
                        case E2TIENEDOLORPECHO_CONS:
                            spinE2TieneDolorPecho.setSelection(e2TieneDolorPechoPos, false);
                            break;
                        case E2TIENEPALPITACIONES_CONS:
                            spinE2TienePalpitaciones.setSelection(e2TienePalpitacionesPos, false);
                            break;
                        case E2TIENEDOLORARTICULACIONES_CONS:
                            spinE2TieneDolorArticulaciones.setSelection(e2TieneDolorArticulacionesPos, false);
                            break;
                        case E2TIENEPARALISIS_CONS:
                            spinE2TieneParalisis.setSelection(e2TieneParalisisPos, false);
                            break;
                        case E2TIENEMAREOS_CONS:
                            spinE2TieneMareos.setSelection(e2TieneMareosPos, false);
                            break;
                        case E2TIENEPENSAMIENTONUBLADO_CONS:
                            spinE2TienePensamientoNublado.setSelection(e2TienePensamientoNubladoPos, false);
                            break;
                        case E2TIENEPROBLEMASDORMIR_CONS:
                            spinE2TieneProblemasDormir.setSelection(e2TieneProblemasDormirPos, false);
                            break;
                        case E2TIENEDEPRESION_CONS:
                            spinE2TieneDepresion.setSelection(e2TieneDepresionPos, false);
                            break;
                        case E2TIENEOTROSSINTOMAS_CONS:
                            spinE2TieneOtrosSintomas.setSelection(e2TieneOtrosSintomasPos, false);
                            break;
                        case E2TIENECUALESSINTOMAS_CONS:
                            break;
                        case E2SABETIEMPORECUPERACION_CONS:
                            spinE2SabeTiempoRecuperacion.setSelection(e2SabeTiempoRecuperacionPos, false);
                            break;
                        case E2TIEMPORECUPERACION_CONS:
                            break;
                        case E2TIEMPORECUPERACIONEN_CONS:
                            spinE2TiempoRecuperacionEn.setSelection(e2TiempoRecuperacionEnPos, false);
                            break;
                        case E2SEVERIDADENFERMEDAD_CONS:
                            spinE2SeveridadEnfermedad.setSelection(e2SeveridadEnfermedadPos, false);
                            break;
                        case E2TOMOMEDICAMENTO_CONS:
                            spinE2TomoMedicamento.setSelection(e2TomoMedicamentoPos, false);
                            break;
                        case E2QUEMEDICAMENTO_CONS:
                            spinE2QueMedicamento.setSelection(e2QueMedicamentoPos, false);
                            break;
                        case E2OTROMEDICAMENTO_CONS:
                            break;
                        case E2OXIGENODOMICILIO_CONS:
                            spinE2OxigenoDomicilio.setSelection(e2OxigenoDomicilioPos, false);
                            break;
                        case E2TIEMPOOXIGENODOM_CONS:
                            spinE2TiempoOxigenoDom.setSelection(e2TiempoOxigenoDomPos, false);
                            break;

                        case E3SABEFIS_CONS:
                            spinE3SabeInicioSintoma.setSelection(e3SabeFISPos, false);
                            break;
                        case E3MESINICIOSINTOMA_CONS:
                            spinE3MesInicioSintoma.setSelection(e3MesInicioSintomaPos, false);
                            break;
                        case E3CONOCELUGAREXPOSICION_CONS:
                            spinE3ConoceLugarExposicion.setSelection(e3ConoceLugarExposicionPos, false);
                            break;
                        case E3BUSCOAYUDA_CONS:
                            spinE3BuscoAyuda.setSelection(e3BuscoAyudaPos, false);
                            break;
                        case E3DONDEBUSCOAYUDA_CONS:
                            spinE3DondeBuscoAyuda.setSelection(e3DondeBuscoAyudaPos, false);
                            break;
                        case E3RECIBIOSEGUIMIENTO_CONS:
                            spinE3RecibioSeguimiento.setSelection(e3RecibioSeguimientoPos, false);
                            break;
                        case E3TIPOSEGUIMIENTO_CONS:
                            spinE3TipoSeguimiento.setSelection(e3TipoSeguimientoPos, false);
                            break;
                        case E3TMPDESPUESBUSCOAYUDA_CONS:
                            spinE3TmpDespuesBuscoAyuda.setSelection(e3TmpDespuesBuscoAyudaPos, false);
                            break;
                        case E3UNANOCHEHOSPITAL_CONS:
                            spinE3UnaNocheHospital.setSelection(e3UnaNocheHospitalPos, false);
                            break;
                        case E3SABECUANTASNOCHES_CONS:
                            spinE3SabeCuantasNoches.setSelection(e3SabeCuantasNochesPos, false);
                            break;
                        case E3SABEFECHAADMISION_CONS:
                            spinE3SabeFechaAdmision.setSelection(e3sabeFechaAdmisionPos, false);
                            break;
                        case E3SABEFECHAALTA_CONS:
                            spinE3SabeFechaAlta.setSelection(e3SabeFechaAltaPos, false);
                            break;
                        case E3UTILIZOOXIGENO_CONS:
                            spinE3UtilizoOxigeno.setSelection(e3UtilizoOxigenoPos, false);
                            break;
                        case E3ESTUVOUCI_CONS:
                            spinE3EstuvoUCI.setSelection(e3EstuvoUCIPos, false);
                            break;
                        case E3FUEINTUBADO_CONS:
                            spinE3FueIntubado.setSelection(e3FueIntubadoPos, false);
                            break;
                        case E3RECUPERADOCOVID19_CONS:
                            spinE3RecuperadoCovid19.setSelection(e3RecuperadoCovid19Pos, false);
                            break;
                        case E3TIENEFEBRICULA_CONS:
                            spinE3TieneFebricula.setSelection(e3TieneFebriculaPos, false);
                            break;
                        case E3TIENECANSANCIO_CONS:
                            spinE3TieneCansancio.setSelection(e3TieneCansancioPos, false);
                            break;
                        case E3TIENEDOLORCABEZA_CONS:
                            spinE3TieneDolorCabeza.setSelection(e3TieneDolorCabezaPos, false);
                            break;
                        case E3TIENEPERDIDAOLFATO_CONS:
                            spinE3TienePerdidaOlfato.setSelection(e3TienePerdidaOlfatoPos, false);
                            break;
                        case E3TIENEPERDIDAGUSTO_CONS:
                            spinE3TienePerdidaGusto.setSelection(e3TienePerdidaGustoPos, false);
                            break;
                        case E3TIENETOS_CONS:
                            spinE3TieneTos.setSelection(e3TieneTosPos, false);
                            break;
                        case E3TIENEDIFICULTADRESPIRAR_CONS:
                            spinE3TieneDificultadRespirar.setSelection(e3TieneDificultadRespirarPos, false);
                            break;
                        case E3TIENEDOLORPECHO_CONS:
                            spinE3TieneDolorPecho.setSelection(e3TieneDolorPechoPos, false);
                            break;
                        case E3TIENEPALPITACIONES_CONS:
                            spinE3TienePalpitaciones.setSelection(e3TienePalpitacionesPos, false);
                            break;
                        case E3TIENEDOLORARTICULACIONES_CONS:
                            spinE3TieneDolorArticulaciones.setSelection(e3TieneDolorArticulacionesPos, false);
                            break;
                        case E3TIENEPARALISIS_CONS:
                            spinE3TieneParalisis.setSelection(e3TieneParalisisPos, false);
                            break;
                        case E3TIENEMAREOS_CONS:
                            spinE3TieneMareos.setSelection(e3TieneMareosPos, false);
                            break;
                        case E3TIENEPENSAMIENTONUBLADO_CONS:
                            spinE3TienePensamientoNublado.setSelection(e3TienePensamientoNubladoPos, false);
                            break;
                        case E3TIENEPROBLEMASDORMIR_CONS:
                            spinE3TieneProblemasDormir.setSelection(e3TieneProblemasDormirPos, false);
                            break;
                        case E3TIENEDEPRESION_CONS:
                            spinE3TieneDepresion.setSelection(e3TieneDepresionPos, false);
                            break;
                        case E3TIENEOTROSSINTOMAS_CONS:
                            spinE3TieneOtrosSintomas.setSelection(e3TieneOtrosSintomasPos, false);
                            break;
                        case E3TIENECUALESSINTOMAS_CONS:
                            break;
                        case E3SABETIEMPORECUPERACION_CONS:
                            spinE3SabeTiempoRecuperacion.setSelection(e3SabeTiempoRecuperacionPos, false);
                            break;
                        case E3TIEMPORECUPERACION_CONS:
                            break;
                        case E3TIEMPORECUPERACIONEN_CONS:
                            spinE3TiempoRecuperacionEn.setSelection(e3TiempoRecuperacionEnPos, false);
                            break;
                        case E3SEVERIDADENFERMEDAD_CONS:
                            spinE3SeveridadEnfermedad.setSelection(e3SeveridadEnfermedadPos, false);
                            break;
                        case E3TOMOMEDICAMENTO_CONS:
                            spinE3TomoMedicamento.setSelection(e3TomoMedicamentoPos, false);
                            break;
                        case E3QUEMEDICAMENTO_CONS:
                            spinE3QueMedicamento.setSelection(e3QueMedicamentoPos, false);
                            break;
                        case E3OTROMEDICAMENTO_CONS:
                            break;
                        case E3OXIGENODOMICILIO_CONS:
                            spinE3OxigenoDomicilio.setSelection(e3OxigenoDomicilioPos, false);
                            break;
                        case E3TIEMPOOXIGENODOM_CONS:
                            spinE3TiempoOxigenoDom.setSelection(e3TiempoOxigenoDomPos, false);
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

                        case E2FUMADOPREVIOENFERMEDAD_CONS:
                            spinE2FumadoPrevioEnfermedad.setSelection(e2FumadoPrevioEnfermedadPos, false);
                            break;
                        case E2FUMAACTUALMENTE_CONS:
                            spinE2FumaActualmente.setSelection(e2FumaActualmentePos, false);
                            break;
                        case E2EMBARAZADA_CONS:
                            spinE2Embarazada.setSelection(e2EmbarazadaPos, false);
                            break;
                        case E2RECUERDASEMANASEMB_CONS:
                            spinE2RecuerdaSemanasEmb.setSelection(e2RecuerdaSemanasEmbPos, false);
                            break;
                        case E2FINALEMBARAZO_CONS:
                            spinE2FinalEmbarazo.setSelection(e2FinalEmbarazoPos, false);
                            break;
                        case E2DABAPECHO_CONS:
                            spinE2DabaPecho.setSelection(e2DabaPechoPos, false);
                            break;

                        case E3FUMADOPREVIOENFERMEDAD_CONS:
                            spinE3FumadoPrevioEnfermedad.setSelection(e3FumadoPrevioEnfermedadPos, false);
                            break;
                        case E3FUMAACTUALMENTE_CONS:
                            spinE3FumaActualmente.setSelection(e3FumaActualmentePos, false);
                            break;
                        case E3EMBARAZADA_CONS:
                            spinE3Embarazada.setSelection(e3EmbarazadaPos, false);
                            break;
                        case E3RECUERDASEMANASEMB_CONS:
                            spinE3RecuerdaSemanasEmb.setSelection(e3RecuerdaSemanasEmbPos, false);
                            break;
                        case E3FINALEMBARAZO_CONS:
                            spinE3FinalEmbarazo.setSelection(e3FinalEmbarazoPos, false);
                            break;
                        case E3DABAPECHO_CONS:
                            spinE3DabaPecho.setSelection(e3DabaPechoPos, false);
                            break;

                        case TRABAJADORSALUD_CONS:
                            spinTrabajadorSalud.setSelection(trabajadorSaludPos, false);
                            break;
                        case VACUNADOCOVID19_CONS :
                            spinVacunadoCovid19.setSelection(vacunadoCovid19Pos, false);
                            break;
                        case MUESTRATARJETAVAC_CONS :
                            spinMuestraTarjetaVac.setSelection(muestraTarjetaVacPos, false);
                            break;
                        case SABENOMBREVACUNA_CONS :
                            spinSabeNombreVacuna.setSelection(sabeNombreVacunaPos, false);
                            break;
                        case MESVACUNA_CONS :
                            spinMesVacuna.setSelection(mesVacunaPos, false);
                            break;
                        case CUANTASDOSIS_CONS :
                            spinCuantasDosis.setSelection(cuantasDosisPos, false);
                            break;
                        case NOMBREDOSIS1_CONS :
                            spinNombreDosis1.setSelection(nombreDosis1Pos, false);
                            break;
                        case NOMBREDOSIS2_CONS :
                            spinNombreDosis2.setSelection(nombreDosis2Pos, false);
                            break;
                        case NOMBREDOSIS3_CONS :
                            spinNombreDosis3.setSelection(nombreDosis3Pos, false);
                            break;

                        case PRESENTOSINTOMASDOSIS1_CONS:
                            spinPresentoSintomasDosis1.setSelection(presentoSintomasDosis1Pos, false);
                            break;
                        case DOLORSITIODOSIS1_CONS:
                            spinDolorSitioDosis1.setSelection(dolorSitioDosis1Pos, false);
                            break;
                        case FIEBREDOSIS1_CONS:
                            spinFiebreDosis1.setSelection(fiebreDosis1Pos, false);
                            break;
                        case CANSANCIODOSIS1_CONS:
                            spinCansancioDosis1.setSelection(cansancioDosis1Pos, false);
                            break;
                        case DOLORCABEZADOSIS1_CONS:
                            spinDolorCabezaDosis1.setSelection(dolorCabezaDosis1Pos, false);
                            break;
                        case DIARREADOSIS1_CONS:
                            spinDiarreaDosis1.setSelection(diarreaDosis1Pos, false);
                            break;
                        case VOMITODOSIS1_CONS:
                            spinVomitoDosis1.setSelection(vomitoDosis1Pos, false);
                            break;
                        case DOLORMUSCULARDOSIS1_CONS:
                            spinDolorMuscularDosis1.setSelection(dolorMuscularDosis1Pos, false);
                            break;
                        case ESCALOFRIOSDOSIS1_CONS:
                            spinEscalofriosDosis1.setSelection(escalofriosDosis1Pos, false);
                            break;
                        case REACCIONALERGICADOSIS1_CONS:
                            spinReaccionAlergicaDosis1.setSelection(reaccionAlergicaDosis1Pos, false);
                            break;
                        case INFECCIONSITIODOSIS1_CONS:
                            spinInfeccionSitioDosis1.setSelection(infeccionSitioDosis1Pos, false);
                            break;
                        case NAUSEASDOSIS1_CONS:
                            spinNauseasDosis1.setSelection(nauseasDosis1Pos, false);
                            break;
                        case BULTOSDOSIS1_CONS:
                            spinBultosDosis1.setSelection(bultosDosis1Pos, false);
                            break;
                        case OTROSDOSIS1_CONS:
                            spinOtrosDosis1.setSelection(otrosDosis1Pos, false);
                            break;

                        case PRESENTOSINTOMASDOSIS2_CONS:
                            spinPresentoSintomasDosis2.setSelection(presentoSintomasDosis2Pos, false);
                            break;
                        case DOLORSITIODOSIS2_CONS:
                            spinDolorSitioDosis2.setSelection(dolorSitioDosis2Pos, false);
                            break;
                        case FIEBREDOSIS2_CONS:
                            spinFiebreDosis2.setSelection(fiebreDosis2Pos, false);
                            break;
                        case CANSANCIODOSIS2_CONS:
                            spinCansancioDosis2.setSelection(cansancioDosis2Pos, false);
                            break;
                        case DOLORCABEZADOSIS2_CONS:
                            spinDolorCabezaDosis2.setSelection(dolorCabezaDosis2Pos, false);
                            break;
                        case DIARREADOSIS2_CONS:
                            spinDiarreaDosis2.setSelection(diarreaDosis2Pos, false);
                            break;
                        case VOMITODOSIS2_CONS:
                            spinVomitoDosis2.setSelection(vomitoDosis2Pos, false);
                            break;
                        case DOLORMUSCULARDOSIS2_CONS:
                            spinDolorMuscularDosis2.setSelection(dolorMuscularDosis2Pos, false);
                            break;
                        case ESCALOFRIOSDOSIS2_CONS:
                            spinEscalofriosDosis2.setSelection(escalofriosDosis2Pos, false);
                            break;
                        case REACCIONALERGICADOSIS2_CONS:
                            spinReaccionAlergicaDosis2.setSelection(reaccionAlergicaDosis2Pos, false);
                            break;
                        case INFECCIONSITIODOSIS2_CONS:
                            spinInfeccionSitioDosis2.setSelection(infeccionSitioDosis2Pos, false);
                            break;
                        case NAUSEASDOSIS2_CONS:
                            spinNauseasDosis2.setSelection(nauseasDosis2Pos, false);
                            break;
                        case BULTOSDOSIS2_CONS:
                            spinBultosDosis2.setSelection(bultosDosis2Pos, false);
                            break;
                        case OTROSDOSIS2_CONS:
                            spinOtrosDosis2.setSelection(otrosDosis2Pos, false);
                            break;

                        case PRESENTOSINTOMASDOSIS3_CONS:
                            spinPresentoSintomasDosis3.setSelection(presentoSintomasDosis3Pos, false);
                            break;
                        case DOLORSITIODOSIS3_CONS:
                            spinDolorSitioDosis3.setSelection(dolorSitioDosis3Pos, false);
                            break;
                        case FIEBREDOSIS3_CONS:
                            spinFiebreDosis3.setSelection(fiebreDosis3Pos, false);
                            break;
                        case CANSANCIODOSIS3_CONS:
                            spinCansancioDosis3.setSelection(cansancioDosis3Pos, false);
                            break;
                        case DOLORCABEZADOSIS3_CONS:
                            spinDolorCabezaDosis3.setSelection(dolorCabezaDosis3Pos, false);
                            break;
                        case DIARREADOSIS3_CONS:
                            spinDiarreaDosis3.setSelection(diarreaDosis3Pos, false);
                            break;
                        case VOMITODOSIS3_CONS:
                            spinVomitoDosis3.setSelection(vomitoDosis3Pos, false);
                            break;
                        case DOLORMUSCULARDOSIS3_CONS:
                            spinDolorMuscularDosis3.setSelection(dolorMuscularDosis3Pos, false);
                            break;
                        case ESCALOFRIOSDOSIS3_CONS:
                            spinEscalofriosDosis3.setSelection(escalofriosDosis3Pos, false);
                            break;
                        case REACCIONALERGICADOSIS3_CONS:
                            spinReaccionAlergicaDosis3.setSelection(reaccionAlergicaDosis3Pos, false);
                            break;
                        case INFECCIONSITIODOSIS3_CONS:
                            spinInfeccionSitioDosis3.setSelection(infeccionSitioDosis3Pos, false);
                            break;
                        case NAUSEASDOSIS3_CONS:
                            spinNauseasDosis3.setSelection(nauseasDosis3Pos, false);
                            break;
                        case BULTOSDOSIS3_CONS:
                            spinBultosDosis3.setSelection(bultosDosis3Pos, false);
                            break;
                        case OTROSDOSIS3_CONS:
                            spinOtrosDosis3.setSelection(otrosDosis3Pos, false);
                            break;

                        case COVID19POSTERIORVACUNA_CONS :
                            spinCovid19PosteriorVacuna.setSelection(covid19PosteriorVacunaPos, false);
                            break;
                        case FECHAEVENTOENFERMOPOSTVAC_CONS:
                            spinFechaEventoEnfermoPostVac.setSelection(fechaEventoEnfermoPostVacPos, false);
                            break;
                        /*case SABEFECHAENFPOSTVAC_CONS :
                            spinSabeFechaEnfPostVac.setSelection(sabeFechaEnfPostVacPos, false);
                            break;
                        case MESFECHAENFPOSTVAC_CONS :
                            spinMesEnfPostVac.setSelection(mesEnfPostVacPos, false);
                            break;*/
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
        int anio = 2021;
        switch(dialog) {
            case FECHA_SINTOMA_E1:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        e1Fis = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputE1FIS.setText(e1Fis);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_ADMISION_E1:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        e1FechaAdmisionHosp = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputE1FechaAdmisionHosp.setText(e1FechaAdmisionHosp);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_ALTA_E1:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        e1FechaAltaHosp = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputE1FechaAltaHosp.setText(e1FechaAltaHosp);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_SINTOMA_E2:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        e2Fis = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputE2FIS.setText(e2Fis);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_ADMISION_E2:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        e2FechaAdmisionHosp = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputE2FechaAdmisionHosp.setText(e2FechaAdmisionHosp);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_ALTA_E2:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        e2FechaAltaHosp = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputE2FechaAltaHosp.setText(e2FechaAltaHosp);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_SINTOMA_E3:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        e3Fis = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputE3FIS.setText(e3Fis);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_ADMISION_E3:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        e3FechaAdmisionHosp = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputE3FechaAdmisionHosp.setText(e3FechaAdmisionHosp);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_ALTA_E3:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        e3FechaAltaHosp = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputE3FechaAltaHosp.setText(e3FechaAltaHosp);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_EVENTO1:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaEvento1 = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputEvento1.setText(fechaEvento1);
                        actualizarListaEventos();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio,2,1);
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_EVENTO2:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaEvento2 = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputEvento2.setText(fechaEvento2);
                        actualizarListaEventos();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                if (fechaEvento1 != null && !fechaEvento1.isEmpty()) {
                    try {
                        Date fechaInicio = DateUtil.StringToDate(fechaEvento1, "dd/MM/yyyy");
                        minDate = new DateMidnight(fechaInicio);
                    } catch (Exception ex){
                        ex.printStackTrace();
                        minDate = new DateMidnight(anio,2,1);
                    }
                } else {
                    minDate = new DateMidnight(anio, 2, 1);
                }
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_EVENTO3:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaEvento3 = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputEvento3.setText(fechaEvento3);
                        actualizarListaEventos();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                if (fechaEvento2 != null && !fechaEvento2.isEmpty()) {
                    try {
                        Date fechaInicio = DateUtil.StringToDate(fechaEvento2, "dd/MM/yyyy");
                        minDate = new DateMidnight(fechaInicio);
                    } catch (Exception ex){
                        ex.printStackTrace();
                        minDate = new DateMidnight(anio,2,1);
                    }
                } else {
                    minDate = new DateMidnight(anio, 2, 1);
                }
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case FECHA_VACUNA1:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaDosis1 = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputFechaDosis1.setText(fechaDosis1);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                minDate = new DateMidnight(anio,1,1);
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
                    } catch (Exception ex){
                     ex.printStackTrace();
                        minDate = new DateMidnight(anio,1,1);
                    }
                } else {
                    minDate = new DateMidnight(anio, 1, 1);
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
                    } catch (Exception ex){
                        ex.printStackTrace();
                        minDate = new DateMidnight(anio,1,1);
                    }
                } else {
                    minDate = new DateMidnight(anio, 1, 1);
                }
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            /*case FECHA_ENFPOSTVAC:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaEnfPostVac = (picker != null) ? String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + String.valueOf(year) : null;
                        inputFechaEnfPostVac.setText(fechaEnfPostVac);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                if (fechaDosis1 != null && !fechaDosis1.isEmpty()) {
                    try {
                        Date fechaInicio = DateUtil.StringToDate(fechaDosis1, "dd/MM/yyyy");
                        minDate = new DateMidnight(fechaInicio);
                    } catch (Exception ex){
                        ex.printStackTrace();
                        minDate = new DateMidnight(anio,1,1);
                    }
                } else {
                    minDate = new DateMidnight(anio, 1, 1);
                }
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;*/
            default:
                break;
        }
    }

    private boolean validarEntrada() throws ParseException {
        //Valida la entrada
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        if (faltaDatoRequerido(enfermoCovid19, R.string.enfermoCovid19)) return false;
        else if (faltaDatoRequeridoHijo(cuantasVecesEnfermo, R.string.cuantasVecesEnfermo, enfermoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoContador(sabeEvento1, R.string.sabeEvento1, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(sabeEvento2, R.string.sabeEvento2, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(sabeEvento3, R.string.sabeEvento3, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijo(mesEvento1, R.string.mesEvento, sabeEvento1, Constants.NOKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(mesEvento2, R.string.mesEvento, sabeEvento2, Constants.NOKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(mesEvento3, R.string.mesEvento, sabeEvento3, Constants.NOKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(fechaEvento1, R.string.fechaEvento1, sabeEvento1, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(fechaEvento2, R.string.fechaEvento2, sabeEvento2, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(fechaEvento3, R.string.fechaEvento3, sabeEvento3, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoContador(e1Febricula, R.string.feb20Febricula, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1Fiebre, R.string.feb20Fiebre, e1Febricula, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoContador(e1Escalofrio, R.string.feb20Escalofrio, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1TemblorEscalofrio, R.string.feb20TemblorEscalofrio, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1DolorMuscular, R.string.feb20DolorMuscular, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1DolorArticular, R.string.feb20DolorArticular, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1SecresionNasal, R.string.feb20SecresionNasal, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1DolorGarganta, R.string.feb20DolorGarganta, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1Tos, R.string.feb20Tos, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1DificultadResp, R.string.feb20DificultadResp, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1DolorPecho, R.string.feb20DolorPecho, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1NauseasVomito, R.string.feb20NauseasVomito, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1DolorCabeza, R.string.feb20DolorCabeza, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1DolorAbdominal, R.string.feb20DolorAbdominal, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1Diarrea, R.string.feb20Diarrea, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1DificultadDormir, R.string.feb20DificultadDormir, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1Debilidad, R.string.feb20Debilidad, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1PerdidaOlfatoGusto, R.string.feb20PerdidaOlfatoGusto, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1Mareo, R.string.feb20Mareo, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1Sarpullido, R.string.feb20Sarpullido, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1Desmayo, R.string.feb20Desmayo, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1QuedoCama, R.string.feb20QuedoCama, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e2Febricula, R.string.feb20Febricula, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2Fiebre, R.string.feb20Fiebre, e2Febricula, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoContador(e2Escalofrio, R.string.feb20Escalofrio, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2TemblorEscalofrio, R.string.feb20TemblorEscalofrio, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2DolorMuscular, R.string.feb20DolorMuscular, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2DolorArticular, R.string.feb20DolorArticular, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2SecresionNasal, R.string.feb20SecresionNasal, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2DolorGarganta, R.string.feb20DolorGarganta, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2Tos, R.string.feb20Tos, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2DificultadResp, R.string.feb20DificultadResp, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2DolorPecho, R.string.feb20DolorPecho, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2NauseasVomito, R.string.feb20NauseasVomito, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2DolorCabeza, R.string.feb20DolorCabeza, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2DolorAbdominal, R.string.feb20DolorAbdominal, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2Diarrea, R.string.feb20Diarrea, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2DificultadDormir, R.string.feb20DificultadDormir, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2Debilidad, R.string.feb20Debilidad, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2PerdidaOlfatoGusto, R.string.feb20PerdidaOlfatoGusto, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2Mareo, R.string.feb20Mareo, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2Sarpullido, R.string.feb20Sarpullido, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2Desmayo, R.string.feb20Desmayo, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2QuedoCama, R.string.feb20QuedoCama, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e3Febricula, R.string.feb20Febricula, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3Fiebre, R.string.feb20Fiebre, e3Febricula, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoContador(e3Escalofrio, R.string.feb20Escalofrio, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3TemblorEscalofrio, R.string.feb20TemblorEscalofrio, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3DolorMuscular, R.string.feb20DolorMuscular, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3DolorArticular, R.string.feb20DolorArticular, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3SecresionNasal, R.string.feb20SecresionNasal, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3DolorGarganta, R.string.feb20DolorGarganta, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3Tos, R.string.feb20Tos, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3DificultadResp, R.string.feb20DificultadResp, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3DolorPecho, R.string.feb20DolorPecho, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3NauseasVomito, R.string.feb20NauseasVomito, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3DolorCabeza, R.string.feb20DolorCabeza, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3DolorAbdominal, R.string.feb20DolorAbdominal, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3Diarrea, R.string.feb20Diarrea, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3DificultadDormir, R.string.feb20DificultadDormir, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3Debilidad, R.string.feb20Debilidad, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3PerdidaOlfatoGusto, R.string.feb20PerdidaOlfatoGusto, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3Mareo, R.string.feb20Mareo, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3Sarpullido, R.string.feb20Sarpullido, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3Desmayo, R.string.feb20Desmayo, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3QuedoCama, R.string.feb20QuedoCama, cuantasVecesEnfermo, 3)) return false;
        /*else if (faltaDatoRequerido(feb20Febricula, R.string.feb20Febricula)) return false;
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
        */
        else if (tuvoAlMenosUnSintomaE1() && faltaDatoRequeridoHijoContador(e1SabeFIS, R.string.sabeInicioSintoma, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijo(e1Fis, R.string.fis, e1SabeFIS, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e1AnioInicioSintoma, R.string.anioInicioSintoma, e1SabeFIS, Constants.NOKEYSND)) {
            inputE1AnioInicioSintoma.requestFocus();
            return false;
        }
        else if (e1AnioInicioSintoma != null && !e1AnioInicioSintoma.equalsIgnoreCase("2020") && !e1AnioInicioSintoma.equalsIgnoreCase("2021")) {
            Toast.makeText(getActivity(), getActivity().getString( R.string.wrongYear, "2020", "2021"),Toast.LENGTH_LONG).show();
            inputE1AnioInicioSintoma.requestFocus();
            return false;
        }
        else if (faltaDatoRequeridoHijo(e1MesInicioSintoma, R.string.mesInicioSintoma, e1SabeFIS, Constants.NOKEYSND)) return false;
        //else if (faltaDatoRequerido(padecidoCovid19, R.string.padecidoCovid19)) return false;
        //else if (faltaDatoRequeridoHijo(conoceLugarExposicion, R.string.conoceLugarExposicion, enfermoCovid19)) return false;
        else if (faltaDatoRequeridoHijoContador(e1ConoceLugarExposicion, R.string.conoceLugarExposicion, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijo(e1LugarExposicion, R.string.lugarExposicion, e1ConoceLugarExposicion)) return false;
        //else if (faltaDatoRequeridoHijo(buscoAyuda, R.string.buscoAyuda, enfermoCovid19)) return false;
        else if (faltaDatoRequeridoHijoContador(e1BuscoAyuda, R.string.buscoAyuda, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijo(e1DondeBuscoAyuda, R.string.dondeBuscoAyuda, e1BuscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(e1NombreCentroSalud, R.string.otroCentroSalud, e1DondeBuscoAyuda, "2")) return false;
        else if (faltaDatoRequeridoHijo(e1NombreHospital, R.string.cualHospital, e1DondeBuscoAyuda, "3")) return false;
        else if (faltaDatoRequeridoHijoNegado(e1RecibioSeguimiento, R.string.recibioSeguimiento, e1DondeBuscoAyuda, "5")) return false;
        else if (faltaDatoRequeridoHijo(e1TipoSeguimiento, R.string.tipoSeguimiento, e1RecibioSeguimiento)) return false;
        else if (faltaDatoRequeridoHijo(e1TmpDespuesBuscoAyuda, R.string.tmpDespuesBuscoAyuda, e1BuscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(e1UnaNocheHospital, R.string.unaNocheHospital, e1BuscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(e1QueHospital, R.string.queHospital, e1UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e1SabeCuantasNoches, R.string.sabeCuantasNoches, e1UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e1CuantasNochesHosp, R.string.cuantasNochesHosp, e1SabeCuantasNoches)) return false;
        else if (faltaDatoRequeridoHijo(e1SabeFechaAdmision, R.string.sabeFechaAdmision, e1UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e1FechaAdmisionHosp, R.string.fechaAdmisionHosp, e1SabeFechaAdmision)) return false;
        else if (faltaDatoRequeridoHijo(e1SabeFechaAlta, R.string.sabeFechaAlta, e1UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e1FechaAltaHosp, R.string.fechaAltaHosp, e1SabeFechaAlta)) return false;
        else if (faltaDatoRequeridoHijo(e1UtilizoOxigeno, R.string.utilizoOxigenoCuest, e1UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e1EstuvoUCI, R.string.estuvoUCI, e1UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e1FueIntubado, R.string.fueIntubadoCuest, e1EstuvoUCI)) return false;
        else if (faltaDatoRequeridoHijoContador(e1RecuperadoCovid19, R.string.recuperadoCovid19, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneFebricula, R.string.febricula, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneCansancio, R.string.cansancio, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneDolorCabeza, R.string.dolorCabeza, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TienePerdidaOlfato, R.string.perdidaOlfato, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TienePerdidaGusto, R.string.perdidaGusto, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneTos, R.string.tos, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneDificultadRespirar, R.string.dificultadRespirar, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneDolorPecho, R.string.dolorPecho, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TienePalpitaciones, R.string.palpitaciones, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneDolorArticulaciones, R.string.dolorArticulaciones, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneParalisis, R.string.paralisis, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneMareos, R.string.mareos, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TienePensamientoNublado, R.string.pensamientoNublado, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneProblemasDormir, R.string.problemasDormir, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneDepresion, R.string.depresion, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e1TieneOtrosSintomas, R.string.otrosSintomas, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e1TieneCualesSintomas, R.string.cualesSintomas, e1TieneOtrosSintomas, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e1SabeTiempoRecuperacion, R.string.sabeTiempoRecuperacion, e1RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e1TiempoRecuperacion, R.string.tiempoRecuperacion, e1SabeTiempoRecuperacion)) return false;
        else if (faltaDatoRequeridoHijo(e1TiempoRecuperacionEn, R.string.tiempoRecuperacionEn, e1SabeTiempoRecuperacion)) return false;
        else if (faltaDatoRequeridoHijoContador(e1SeveridadEnfermedad, R.string.severidadEnfermedad, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijoContador(e1TomoMedicamento, R.string.tomoMedicamento, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijo(e1QueMedicamento, R.string.queMedicamento, e1TomoMedicamento)) return false;
        else if (faltaDatoRequeridoHijoContains(e1OtroMedicamento, R.string.otroMedicamento, e1QueMedicamento, "otro")) return false;
        else if (faltaDatoRequeridoHijoContador(e1OxigenoDomicilio, R.string.oxigenoDomicilio, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijo(e1TiempoOxigenoDom, R.string.tiempoOxigenoDom, e1OxigenoDomicilio)) return false;
        else if (tuvoAlMenosUnSintomaE2() && faltaDatoRequeridoHijoContador(e2SabeFIS, R.string.sabeInicioSintoma, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijo(e2Fis, R.string.fis, e2SabeFIS, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e2AnioInicioSintoma, R.string.anioInicioSintoma, e2SabeFIS, Constants.NOKEYSND)) {
            inputE2AnioInicioSintoma.requestFocus();
            return false;
        }
        else if (e2AnioInicioSintoma != null && !e2AnioInicioSintoma.equalsIgnoreCase("2020") && !e2AnioInicioSintoma.equalsIgnoreCase("2021")) {
            Toast.makeText(getActivity(), getActivity().getString( R.string.wrongYear, "2020", "2021"),Toast.LENGTH_LONG).show();
            inputE2AnioInicioSintoma.requestFocus();
            return false;
        }
        else if (faltaDatoRequeridoHijo(e2MesInicioSintoma, R.string.mesInicioSintoma, e2SabeFIS, Constants.NOKEYSND)) return false;
        else if (faltaDatoRequeridoHijoContador(e2ConoceLugarExposicion, R.string.conoceLugarExposicion, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijo(e2LugarExposicion, R.string.lugarExposicion, e2ConoceLugarExposicion)) return false;
        else if (faltaDatoRequeridoHijoContador(e2BuscoAyuda, R.string.buscoAyuda, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijo(e2DondeBuscoAyuda, R.string.dondeBuscoAyuda, e2BuscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(e2NombreCentroSalud, R.string.otroCentroSalud, e2DondeBuscoAyuda, "2")) return false;
        else if (faltaDatoRequeridoHijo(e2NombreHospital, R.string.cualHospital, e2DondeBuscoAyuda, "3")) return false;
        else if (faltaDatoRequeridoHijoNegado(e2RecibioSeguimiento, R.string.recibioSeguimiento, e2DondeBuscoAyuda, "5")) return false;
        else if (faltaDatoRequeridoHijo(e2TipoSeguimiento, R.string.tipoSeguimiento, e2RecibioSeguimiento)) return false;
        else if (faltaDatoRequeridoHijo(e2TmpDespuesBuscoAyuda, R.string.tmpDespuesBuscoAyuda, e2BuscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(e2UnaNocheHospital, R.string.unaNocheHospital, e2BuscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(e2QueHospital, R.string.queHospital, e2UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e2SabeCuantasNoches, R.string.sabeCuantasNoches, e2UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e2CuantasNochesHosp, R.string.cuantasNochesHosp, e2SabeCuantasNoches)) return false;
        else if (faltaDatoRequeridoHijo(e2SabeFechaAdmision, R.string.sabeFechaAdmision, e2UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e2FechaAdmisionHosp, R.string.fechaAdmisionHosp, e2SabeFechaAdmision)) return false;
        else if (faltaDatoRequeridoHijo(e2SabeFechaAlta, R.string.sabeFechaAlta, e2UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e2FechaAltaHosp, R.string.fechaAltaHosp, e2SabeFechaAlta)) return false;
        else if (faltaDatoRequeridoHijo(e2UtilizoOxigeno, R.string.utilizoOxigenoCuest, e2UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e2EstuvoUCI, R.string.estuvoUCI, e2UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e2FueIntubado, R.string.fueIntubadoCuest, e2EstuvoUCI)) return false;
        else if (faltaDatoRequeridoHijoContador(e2RecuperadoCovid19, R.string.recuperadoCovid19, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneFebricula, R.string.febricula, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneCansancio, R.string.cansancio, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneDolorCabeza, R.string.dolorCabeza, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TienePerdidaOlfato, R.string.perdidaOlfato, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TienePerdidaGusto, R.string.perdidaGusto, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneTos, R.string.tos, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneDificultadRespirar, R.string.dificultadRespirar, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneDolorPecho, R.string.dolorPecho, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TienePalpitaciones, R.string.palpitaciones, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneDolorArticulaciones, R.string.dolorArticulaciones, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneParalisis, R.string.paralisis, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneMareos, R.string.mareos, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TienePensamientoNublado, R.string.pensamientoNublado, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneProblemasDormir, R.string.problemasDormir, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneDepresion, R.string.depresion, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e2TieneOtrosSintomas, R.string.otrosSintomas, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e2TieneCualesSintomas, R.string.cualesSintomas, e2TieneOtrosSintomas, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e2SabeTiempoRecuperacion, R.string.sabeTiempoRecuperacion, e2RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e2TiempoRecuperacion, R.string.tiempoRecuperacion, e2SabeTiempoRecuperacion)) return false;
        else if (faltaDatoRequeridoHijo(e2TiempoRecuperacionEn, R.string.tiempoRecuperacionEn, e2SabeTiempoRecuperacion)) return false;
        else if (faltaDatoRequeridoHijoContador(e2SeveridadEnfermedad, R.string.severidadEnfermedad, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijoContador(e2TomoMedicamento, R.string.tomoMedicamento, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijo(e2QueMedicamento, R.string.queMedicamento, e2TomoMedicamento)) return false;
        else if (faltaDatoRequeridoHijoContains(e2OtroMedicamento, R.string.otroMedicamento, e2QueMedicamento, "otro")) return false;
        else if (faltaDatoRequeridoHijoContador(e2OxigenoDomicilio, R.string.oxigenoDomicilio, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijo(e2TiempoOxigenoDom, R.string.tiempoOxigenoDom, e2OxigenoDomicilio)) return false;
        else if (tuvoAlMenosUnSintomaE3() && faltaDatoRequeridoHijoContador(e3SabeFIS, R.string.sabeInicioSintoma, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijo(e3Fis, R.string.fis, e3SabeFIS, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e3AnioInicioSintoma, R.string.anioInicioSintoma, e3SabeFIS, Constants.NOKEYSND)) {
            inputE3AnioInicioSintoma.requestFocus();
            return false;
        }
        else if (e3AnioInicioSintoma != null && !e3AnioInicioSintoma.equalsIgnoreCase("2020") && !e3AnioInicioSintoma.equalsIgnoreCase("2021")) {
            Toast.makeText(getActivity(), getActivity().getString( R.string.wrongYear, "2020", "2021"),Toast.LENGTH_LONG).show();
            inputE3AnioInicioSintoma.requestFocus();
            return false;
        }
        else if (faltaDatoRequeridoHijo(e3MesInicioSintoma, R.string.mesInicioSintoma, e3SabeFIS, Constants.NOKEYSND)) return false;
        else if (faltaDatoRequeridoHijoContador(e3ConoceLugarExposicion, R.string.conoceLugarExposicion, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijo(e3LugarExposicion, R.string.lugarExposicion, e3ConoceLugarExposicion)) return false;
        else if (faltaDatoRequeridoHijoContador(e3BuscoAyuda, R.string.buscoAyuda, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijo(e3DondeBuscoAyuda, R.string.dondeBuscoAyuda, e3BuscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(e3NombreCentroSalud, R.string.otroCentroSalud, e3DondeBuscoAyuda, "2")) return false;
        else if (faltaDatoRequeridoHijo(e3NombreHospital, R.string.cualHospital, e3DondeBuscoAyuda, "3")) return false;
        else if (faltaDatoRequeridoHijoNegado(e3RecibioSeguimiento, R.string.recibioSeguimiento, e3DondeBuscoAyuda, "5")) return false;
        else if (faltaDatoRequeridoHijo(e3TipoSeguimiento, R.string.tipoSeguimiento, e3RecibioSeguimiento)) return false;
        else if (faltaDatoRequeridoHijo(e3TmpDespuesBuscoAyuda, R.string.tmpDespuesBuscoAyuda, e3BuscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(e3UnaNocheHospital, R.string.unaNocheHospital, e3BuscoAyuda)) return false;
        else if (faltaDatoRequeridoHijo(e3QueHospital, R.string.queHospital, e3UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e3SabeCuantasNoches, R.string.sabeCuantasNoches, e3UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e3CuantasNochesHosp, R.string.cuantasNochesHosp, e3SabeCuantasNoches)) return false;
        else if (faltaDatoRequeridoHijo(e3SabeFechaAdmision, R.string.sabeFechaAdmision, e3UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e3FechaAdmisionHosp, R.string.fechaAdmisionHosp, e3SabeFechaAdmision)) return false;
        else if (faltaDatoRequeridoHijo(e3SabeFechaAlta, R.string.sabeFechaAlta, e3UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e3FechaAltaHosp, R.string.fechaAltaHosp, e3SabeFechaAlta)) return false;
        else if (faltaDatoRequeridoHijo(e3UtilizoOxigeno, R.string.utilizoOxigenoCuest, e3UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e3EstuvoUCI, R.string.estuvoUCI, e3UnaNocheHospital)) return false;
        else if (faltaDatoRequeridoHijo(e3FueIntubado, R.string.fueIntubadoCuest, e3EstuvoUCI)) return false;
        else if (faltaDatoRequeridoHijoContador(e3RecuperadoCovid19, R.string.recuperadoCovid19, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneFebricula, R.string.febricula, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneCansancio, R.string.cansancio, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneDolorCabeza, R.string.dolorCabeza, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TienePerdidaOlfato, R.string.perdidaOlfato, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TienePerdidaGusto, R.string.perdidaGusto, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneTos, R.string.tos, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneDificultadRespirar, R.string.dificultadRespirar, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneDolorPecho, R.string.dolorPecho, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TienePalpitaciones, R.string.palpitaciones, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneDolorArticulaciones, R.string.dolorArticulaciones, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneParalisis, R.string.paralisis, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneMareos, R.string.mareos, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TienePensamientoNublado, R.string.pensamientoNublado, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneProblemasDormir, R.string.problemasDormir, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneDepresion, R.string.depresion, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijoNegado(e3TieneOtrosSintomas, R.string.otrosSintomas, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e3TieneCualesSintomas, R.string.cualesSintomas, e3TieneOtrosSintomas, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e3SabeTiempoRecuperacion, R.string.sabeTiempoRecuperacion, e3RecuperadoCovid19, Constants.YESKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(e3TiempoRecuperacion, R.string.tiempoRecuperacion, e3SabeTiempoRecuperacion)) return false;
        else if (faltaDatoRequeridoHijo(e3TiempoRecuperacionEn, R.string.tiempoRecuperacionEn, e3SabeTiempoRecuperacion)) return false;
        else if (faltaDatoRequeridoHijoContador(e3SeveridadEnfermedad, R.string.severidadEnfermedad, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijoContador(e3TomoMedicamento, R.string.tomoMedicamento, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijo(e3QueMedicamento, R.string.queMedicamento, e3TomoMedicamento)) return false;
        else if (faltaDatoRequeridoHijoContains(e3OtroMedicamento, R.string.otroMedicamento, e3QueMedicamento, "otro")) return false;
        else if (faltaDatoRequeridoHijoContador(e3OxigenoDomicilio, R.string.oxigenoDomicilio, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijo(e3TiempoOxigenoDom, R.string.tiempoOxigenoDom, e3OxigenoDomicilio)) return false;
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
        else if (participante.getEdadMeses() >= 168 &&
                ((cuantasVecesEnfermo != null && !cuantasVecesEnfermo.isEmpty()) && (Integer.parseInt(cuantasVecesEnfermo) >= 1)) && fumadoCienCigarrillos.equals(Constants.YESKEYSND)
                && (e1FumadoPrevioEnfermedad == null || e1FumadoPrevioEnfermedad.equals(""))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fumadoPrevioEnfermedad)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (participante.getEdadMeses() >= 168 &&
                ((cuantasVecesEnfermo != null && !cuantasVecesEnfermo.isEmpty()) && (Integer.parseInt(cuantasVecesEnfermo) >= 2)) && fumadoCienCigarrillos.equals(Constants.YESKEYSND)
                && (e2FumadoPrevioEnfermedad == null || e2FumadoPrevioEnfermedad.equals(""))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fumadoPrevioEnfermedad)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (participante.getEdadMeses() >= 168 &&
                ((cuantasVecesEnfermo != null && !cuantasVecesEnfermo.isEmpty()) && (Integer.parseInt(cuantasVecesEnfermo) >= 3)) && fumadoCienCigarrillos.equals(Constants.YESKEYSND)
                && (e3FumadoPrevioEnfermedad == null || e3FumadoPrevioEnfermedad.equals(""))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fumadoPrevioEnfermedad)),Toast.LENGTH_LONG).show();
            return false;
        }
        else if (participante.getEdadMeses() >= 168 &&
                ((cuantasVecesEnfermo != null && !cuantasVecesEnfermo.isEmpty()) && (Integer.parseInt(cuantasVecesEnfermo) >= 1)) && faltaDatoRequeridoHijoNegado(e1FumaActualmente, R.string.fumaActualmente, fumadoCienCigarrillos, Constants.NOKEYSND)) return false;
        else if ((e1FechaAdmisionHosp !=null && !e1FechaAdmisionHosp.isEmpty()) && (e1FechaAltaHosp !=null && !e1FechaAltaHosp.isEmpty()) && formatter.parse(e1FechaAltaHosp).before(formatter.parse(e1FechaAdmisionHosp))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.fecBefRegistro, "Fecha Alta", "Fecha Admisión"),Toast.LENGTH_LONG).show();
            return false;
        }
        else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600)
                && faltaDatoRequeridoHijoContador(e1Embarazada, R.string.embarazada, cuantasVecesEnfermo, 1)) return false;
        else if (faltaDatoRequeridoHijo(e1RecuerdaSemanasEmb, R.string.recuerdaSemanasEmb, e1Embarazada)) return false;
        else if (faltaDatoRequeridoHijo(e1SemanasEmbarazo, R.string.semanasEmbarazoCovid, e1RecuerdaSemanasEmb)) return false;
        //else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) && faltaDatoRequeridoHijo(dabaPecho, R.string.dabaPecho, enfermoCovid19)) return false;
        else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) && faltaDatoRequeridoHijo(e1DabaPecho, R.string.dabaPecho, e1Embarazada)) return false;
        else if (participante.getEdadMeses() >= 168 &&
                ((cuantasVecesEnfermo != null && !cuantasVecesEnfermo.isEmpty()) && (Integer.parseInt(cuantasVecesEnfermo) >= 2)) && faltaDatoRequeridoHijoNegado(e2FumaActualmente, R.string.fumaActualmente, fumadoCienCigarrillos, Constants.NOKEYSND)) return false;
        else if ((e2FechaAdmisionHosp !=null && !e2FechaAdmisionHosp.isEmpty()) && (e2FechaAltaHosp !=null && !e2FechaAltaHosp.isEmpty()) && formatter.parse(e2FechaAltaHosp).before(formatter.parse(e2FechaAdmisionHosp))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.fecBefRegistro, "Fecha Alta", "Fecha Admisión"),Toast.LENGTH_LONG).show();
            return false;
        }
        else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600)
                && faltaDatoRequeridoHijoContador(e2Embarazada, R.string.embarazada, cuantasVecesEnfermo, 2)) return false;
        else if (faltaDatoRequeridoHijo(e2RecuerdaSemanasEmb, R.string.recuerdaSemanasEmb, e2Embarazada)) return false;
        else if (faltaDatoRequeridoHijo(e2SemanasEmbarazo, R.string.semanasEmbarazoCovid, e2RecuerdaSemanasEmb)) return false;
        else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) && faltaDatoRequeridoHijo(e2DabaPecho, R.string.dabaPecho, e2Embarazada)) return false;
        else if (participante.getEdadMeses() >= 168 &&
                ((cuantasVecesEnfermo != null && !cuantasVecesEnfermo.isEmpty()) && (Integer.parseInt(cuantasVecesEnfermo) >= 3)) && faltaDatoRequeridoHijoNegado(e3FumaActualmente, R.string.fumaActualmente, fumadoCienCigarrillos, Constants.NOKEYSND)) return false;
        else if ((e3FechaAdmisionHosp !=null && !e3FechaAdmisionHosp.isEmpty()) && (e3FechaAltaHosp !=null && !e3FechaAltaHosp.isEmpty()) && formatter.parse(e3FechaAltaHosp).before(formatter.parse(e3FechaAdmisionHosp))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.fecBefRegistro, "Fecha Alta", "Fecha Admisión"),Toast.LENGTH_LONG).show();
            return false;
        }
        else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) &&
                faltaDatoRequeridoHijoContador(e3Embarazada, R.string.embarazada, cuantasVecesEnfermo, 3)) return false;
        else if (faltaDatoRequeridoHijo(e3RecuerdaSemanasEmb, R.string.recuerdaSemanasEmb, e3Embarazada)) return false;
        else if (faltaDatoRequeridoHijo(e3SemanasEmbarazo, R.string.semanasEmbarazoCovid, e3RecuerdaSemanasEmb)) return false;
        else if ((participante.getSexo().equalsIgnoreCase("F") && participante.getEdadMeses() >= 216 &&  participante.getEdadMeses() <= 600) && faltaDatoRequeridoHijo(e3DabaPecho, R.string.dabaPecho, e3Embarazada)) return false;
        else if (participante.getEdadMeses() >= 216 && faltaDatoRequerido(trabajadorSalud, R.string.trabajadorSalud)) return false; //18 anios
        else if (faltaDatoRequerido(vacunadoCovid19, R.string.vacunadoCovid19)) return false;
        else if (faltaDatoRequeridoHijo(muestraTarjetaVac, R.string.muestraTarjetaVac, vacunadoCovid19)) return false;
        else if (faltaDatoRequeridoHijo(sabeNombreVacuna, R.string.sabeNombreVacuna, muestraTarjetaVac, Constants.NOKEYSND)) return false;
        else if (faltaDatoRequeridoHijo(nombreVacuna, R.string.nombreVacuna, sabeNombreVacuna)) return false;
        else if (faltaDatoRequeridoHijo(mesVacuna, R.string.mesVacuna, sabeNombreVacuna)) return false;
        else if (faltaDatoRequeridoHijo(anioVacuna, R.string.anioVacuna, sabeNombreVacuna)) return false;
        else if (faltaDatoRequeridoHijo(cuantasDosis, R.string.cuantasDosis, muestraTarjetaVac, Constants.YESKEYSND)) return false;
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
        else if (faltaDatoRequeridoHijoContador(presentoSintomasDosis1, R.string.presentoSintomasVacuna1, cuantasDosis, 1)) return false;
        else if (faltaDatoRequeridoHijo(dolorSitioDosis1, R.string.dolorSitioVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(fiebreDosis1, R.string.fiebreVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(cansancioDosis1, R.string.cansancioVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(dolorCabezaDosis1, R.string.dolorCabezaVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(diarreaDosis1, R.string.diarreaVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(vomitoDosis1, R.string.vomitoVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(dolorMuscularDosis1, R.string.dolorMuscularVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(escalofriosDosis1, R.string.escalofriosVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(reaccionAlergicaDosis1, R.string.reaccionAlergicaVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(infeccionSitioDosis1, R.string.infeccionSitioVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(nauseasDosis1, R.string.nauseasVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(bultosDosis1, R.string.bultosVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(otrosDosis1, R.string.otrosVac, presentoSintomasDosis1)) return false;
        else if (faltaDatoRequeridoHijo(desOtrosDosis1, R.string.desOtrosVac, otrosDosis1)) return false;
        else if (faltaDatoRequeridoHijoContador(presentoSintomasDosis2, R.string.presentoSintomasVacuna2, cuantasDosis, 2)) return false;
        else if (faltaDatoRequeridoHijo(dolorSitioDosis2, R.string.dolorSitioVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(fiebreDosis2, R.string.fiebreVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(cansancioDosis2, R.string.cansancioVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(dolorCabezaDosis2, R.string.dolorCabezaVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(diarreaDosis2, R.string.diarreaVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(vomitoDosis2, R.string.vomitoVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(dolorMuscularDosis2, R.string.dolorMuscularVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(escalofriosDosis2, R.string.escalofriosVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(reaccionAlergicaDosis2, R.string.reaccionAlergicaVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(infeccionSitioDosis2, R.string.infeccionSitioVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(nauseasDosis2, R.string.nauseasVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(bultosDosis2, R.string.bultosVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(otrosDosis2, R.string.otrosVac, presentoSintomasDosis2)) return false;
        else if (faltaDatoRequeridoHijo(desOtrosDosis2, R.string.desOtrosVac, otrosDosis2)) return false;
        else if (faltaDatoRequeridoHijoContador(presentoSintomasDosis3, R.string.presentoSintomasVacuna3, cuantasDosis, 3)) return false;
        else if (faltaDatoRequeridoHijo(dolorSitioDosis3, R.string.dolorSitioVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(fiebreDosis3, R.string.fiebreVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(cansancioDosis3, R.string.cansancioVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(dolorCabezaDosis3, R.string.dolorCabezaVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(diarreaDosis3, R.string.diarreaVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(vomitoDosis3, R.string.vomitoVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(dolorMuscularDosis3, R.string.dolorMuscularVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(escalofriosDosis3, R.string.escalofriosVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(reaccionAlergicaDosis3, R.string.reaccionAlergicaVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(infeccionSitioDosis3, R.string.infeccionSitioVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(nauseasDosis3, R.string.nauseasVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(bultosDosis3, R.string.bultosVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(otrosDosis3, R.string.otrosVac, presentoSintomasDosis3)) return false;
        else if (faltaDatoRequeridoHijo(desOtrosDosis3, R.string.desOtrosVac, otrosDosis3)) return false;
        else if (faltaDatoRequeridoHijo(covid19PosteriorVacuna, R.string.covid19PosteriorVacuna, vacunadoCovid19)) return false;
        else if (((cuantasVecesEnfermo != null && !cuantasVecesEnfermo.isEmpty()) && (Integer.parseInt(cuantasVecesEnfermo) > 0)) && faltaDatoRequeridoHijo(fechaEventoEnfermoPostVac, R.string.fechaEnfPostVac, covid19PosteriorVacuna)) return false;
        //else if (faltaDatoRequeridoHijo(sabeFechaEnfPostVac, R.string.sabeFechaEnfPostVac, covid19PosteriorVacuna)) return false;
        //else if (faltaDatoRequeridoHijo(fechaEnfPostVac, R.string.fechaEnfPostVac, sabeFechaEnfPostVac, Constants.YESKEYSND)) return false;
        //else if (faltaDatoRequeridoHijo(mesEnfPostVac, R.string.mesEvento, sabeFechaEnfPostVac, Constants.NOKEYSND)) return false;
        else {
            mCuestionario.setCodigo(infoMovil.getId());
            mCuestionario.setParticipante(participante);
            mCuestionario.setPeriodoSintomas(periodoSintomas);

            mCuestionario.setE1SabeFIS(e1SabeFIS);
            if (e1Fis !=null && !e1Fis.isEmpty()) mCuestionario.setE1Fis(formatter.parse(e1Fis));
            mCuestionario.setE1MesInicioSintoma(e1MesInicioSintoma);
            mCuestionario.setE1AnioInicioSintoma(e1AnioInicioSintoma);
            mCuestionario.setE1ConoceLugarExposicion(e1ConoceLugarExposicion);
            mCuestionario.setE1LugarExposicion(e1LugarExposicion);
            mCuestionario.setE1BuscoAyuda(e1BuscoAyuda);
            mCuestionario.setE1DondeBuscoAyuda(e1DondeBuscoAyuda);
            mCuestionario.setE1NombreCentroSalud(e1NombreCentroSalud);
            mCuestionario.setE1NombreHospital(e1NombreHospital);
            mCuestionario.setE1RecibioSeguimiento(e1RecibioSeguimiento);
            mCuestionario.setE1TipoSeguimiento(e1TipoSeguimiento);
            mCuestionario.setE1TmpDespuesBuscoAyuda(e1TmpDespuesBuscoAyuda);
            mCuestionario.setE1UnaNocheHospital(e1UnaNocheHospital);
            mCuestionario.setE1QueHospital(e1QueHospital);
            mCuestionario.setE1SabeCuantasNoches(e1SabeCuantasNoches);
            mCuestionario.setE1CuantasNochesHosp(e1CuantasNochesHosp);
            mCuestionario.setE1SabeFechaAdmision(e1SabeFechaAdmision);
            if (e1FechaAdmisionHosp !=null && !e1FechaAdmisionHosp.isEmpty()) mCuestionario.setE1FechaAdmisionHosp(formatter.parse(e1FechaAdmisionHosp));
            mCuestionario.setE1SabeFechaAlta(e1SabeFechaAlta);
            if (e1FechaAltaHosp !=null && !e1FechaAltaHosp.isEmpty()) mCuestionario.setE1FechaAltaHosp(formatter.parse(e1FechaAltaHosp));
            mCuestionario.setE1UtilizoOxigeno(e1UtilizoOxigeno);
            mCuestionario.setE1EstuvoUCI(e1EstuvoUCI);
            mCuestionario.setE1FueIntubado(e1FueIntubado);
            mCuestionario.setE1RecuperadoCovid19(e1RecuperadoCovid19);
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
            mCuestionario.setE1TomoMedicamento(e1TomoMedicamento);
            if (e1QueMedicamento !=null && !e1QueMedicamento.isEmpty()) {
                String keysCriterios = "";
                e1QueMedicamento = e1QueMedicamento.replaceAll(",", "','");
                estudiosAdapter.open();
                List<MessageResource> catMedi = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + e1QueMedicamento + "') and "
                        + CatalogosDBConstants.catRoot + "='COVID_MEDICAMENTO_SVC'", null);
                estudiosAdapter.close();
                for (MessageResource ms : catMedi) {
                    keysCriterios += ms.getCatKey() + ",";
                }
                if (!keysCriterios.isEmpty())
                    keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                mCuestionario.setE1QueMedicamento(keysCriterios);
            }
            mCuestionario.setE1OtroMedicamento(e1OtroMedicamento);
            mCuestionario.setE1OxigenoDomicilio(e1OxigenoDomicilio);
            mCuestionario.setE1TiempoOxigenoDom(e1TiempoOxigenoDom);
            //evento2
            mCuestionario.setE2SabeFIS(e2SabeFIS);
            if (e2Fis !=null && !e2Fis.isEmpty()) mCuestionario.setE2Fis(formatter.parse(e2Fis));
            mCuestionario.setE2MesInicioSintoma(e2MesInicioSintoma);
            mCuestionario.setE2AnioInicioSintoma(e2AnioInicioSintoma);
            mCuestionario.setE2ConoceLugarExposicion(e2ConoceLugarExposicion);
            mCuestionario.setE2LugarExposicion(e2LugarExposicion);
            mCuestionario.setE2BuscoAyuda(e2BuscoAyuda);
            mCuestionario.setE2DondeBuscoAyuda(e2DondeBuscoAyuda);
            mCuestionario.setE2NombreCentroSalud(e2NombreCentroSalud);
            mCuestionario.setE2NombreHospital(e2NombreHospital);
            mCuestionario.setE2RecibioSeguimiento(e2RecibioSeguimiento);
            mCuestionario.setE2TipoSeguimiento(e2TipoSeguimiento);
            mCuestionario.setE2TmpDespuesBuscoAyuda(e2TmpDespuesBuscoAyuda);
            mCuestionario.setE2UnaNocheHospital(e2UnaNocheHospital);
            mCuestionario.setE2QueHospital(e2QueHospital);
            mCuestionario.setE2SabeCuantasNoches(e2SabeCuantasNoches);
            mCuestionario.setE2CuantasNochesHosp(e2CuantasNochesHosp);
            mCuestionario.setE2SabeFechaAdmision(e2SabeFechaAdmision);
            if (e2FechaAdmisionHosp !=null && !e2FechaAdmisionHosp.isEmpty()) mCuestionario.setE2FechaAdmisionHosp(formatter.parse(e2FechaAdmisionHosp));
            mCuestionario.setE2SabeFechaAlta(e2SabeFechaAlta);
            if (e2FechaAltaHosp !=null && !e2FechaAltaHosp.isEmpty()) mCuestionario.setE2FechaAltaHosp(formatter.parse(e2FechaAltaHosp));
            mCuestionario.setE2UtilizoOxigeno(e2UtilizoOxigeno);
            mCuestionario.setE2EstuvoUCI(e2EstuvoUCI);
            mCuestionario.setE2FueIntubado(e2FueIntubado);
            mCuestionario.setE2RecuperadoCovid19(e2RecuperadoCovid19);
            mCuestionario.setE2TieneFebricula(e2TieneFebricula);
            mCuestionario.setE2TieneCansancio(e2TieneCansancio);
            mCuestionario.setE2TieneDolorCabeza(e2TieneDolorCabeza);
            mCuestionario.setE2TienePerdidaOlfato(e2TienePerdidaOlfato);
            mCuestionario.setE2TienePerdidaGusto(e2TienePerdidaGusto);
            mCuestionario.setE2TieneTos(e2TieneTos);
            mCuestionario.setE2TieneDificultadRespirar(e2TieneDificultadRespirar);
            mCuestionario.setE2TieneDolorPecho(e2TieneDolorPecho);
            mCuestionario.setE2TienePalpitaciones(e2TienePalpitaciones);
            mCuestionario.setE2TieneDolorArticulaciones(e2TieneDolorArticulaciones);
            mCuestionario.setE2TieneParalisis(e2TieneParalisis);
            mCuestionario.setE2TieneMareos(e2TieneMareos);
            mCuestionario.setE2TienePensamientoNublado(e2TienePensamientoNublado);
            mCuestionario.setE2TieneProblemasDormir(e2TieneProblemasDormir);
            mCuestionario.setE2TieneDepresion(e2TieneDepresion);
            mCuestionario.setE2TieneOtrosSintomas(e2TieneOtrosSintomas);
            mCuestionario.setE2cualesSintomas(e2TieneCualesSintomas);
            mCuestionario.setE2SabeTiempoRecuperacion(e2SabeTiempoRecuperacion);
            mCuestionario.setE2TiempoRecuperacion(e2TiempoRecuperacion);
            mCuestionario.setE2TiempoRecuperacionEn(e2TiempoRecuperacionEn);
            mCuestionario.setE2SeveridadEnfermedad(e2SeveridadEnfermedad);
            mCuestionario.setE2TomoMedicamento(e2TomoMedicamento);
            if (e2QueMedicamento !=null && !e2QueMedicamento.isEmpty()) {
                String keysCriterios = "";
                e2QueMedicamento = e2QueMedicamento.replaceAll(",", "','");
                estudiosAdapter.open();
                List<MessageResource> catMedi = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + e2QueMedicamento + "') and "
                        + CatalogosDBConstants.catRoot + "='COVID_MEDICAMENTO_SVC'", null);
                estudiosAdapter.close();
                for (MessageResource ms : catMedi) {
                    keysCriterios += ms.getCatKey() + ",";
                }
                if (!keysCriterios.isEmpty())
                    keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                mCuestionario.setE2QueMedicamento(keysCriterios);
            }
            mCuestionario.setE2OtroMedicamento(e2OtroMedicamento);
            mCuestionario.setE2OxigenoDomicilio(e2OxigenoDomicilio);
            mCuestionario.setE2TiempoOxigenoDom(e2TiempoOxigenoDom);
            //evento3
            mCuestionario.setE3SabeFIS(e3SabeFIS);
            if (e3Fis !=null && !e3Fis.isEmpty()) mCuestionario.setE3Fis(formatter.parse(e3Fis));
            mCuestionario.setE3MesInicioSintoma(e3MesInicioSintoma);
            mCuestionario.setE3AnioInicioSintoma(e3AnioInicioSintoma);
            mCuestionario.setE3ConoceLugarExposicion(e3ConoceLugarExposicion);
            mCuestionario.setE3LugarExposicion(e3LugarExposicion);
            mCuestionario.setE3BuscoAyuda(e3BuscoAyuda);
            mCuestionario.setE3DondeBuscoAyuda(e3DondeBuscoAyuda);
            mCuestionario.setE3NombreCentroSalud(e3NombreCentroSalud);
            mCuestionario.setE3NombreHospital(e3NombreHospital);
            mCuestionario.setE3RecibioSeguimiento(e3RecibioSeguimiento);
            mCuestionario.setE3TipoSeguimiento(e3TipoSeguimiento);
            mCuestionario.setE3TmpDespuesBuscoAyuda(e3TmpDespuesBuscoAyuda);
            mCuestionario.setE3UnaNocheHospital(e3UnaNocheHospital);
            mCuestionario.setE3QueHospital(e3QueHospital);
            mCuestionario.setE3SabeCuantasNoches(e3SabeCuantasNoches);
            mCuestionario.setE3CuantasNochesHosp(e3CuantasNochesHosp);
            mCuestionario.setE3SabeFechaAdmision(e3SabeFechaAdmision);
            if (e3FechaAdmisionHosp !=null && !e3FechaAdmisionHosp.isEmpty()) mCuestionario.setE3FechaAdmisionHosp(formatter.parse(e3FechaAdmisionHosp));
            mCuestionario.setE3SabeFechaAlta(e3SabeFechaAlta);
            if (e3FechaAltaHosp !=null && !e3FechaAltaHosp.isEmpty()) mCuestionario.setE3FechaAltaHosp(formatter.parse(e3FechaAltaHosp));
            mCuestionario.setE3UtilizoOxigeno(e3UtilizoOxigeno);
            mCuestionario.setE3EstuvoUCI(e3EstuvoUCI);
            mCuestionario.setE3FueIntubado(e3FueIntubado);
            mCuestionario.setE3RecuperadoCovid19(e3RecuperadoCovid19);
            mCuestionario.setE3TieneFebricula(e3TieneFebricula);
            mCuestionario.setE3TieneCansancio(e3TieneCansancio);
            mCuestionario.setE3TieneDolorCabeza(e3TieneDolorCabeza);
            mCuestionario.setE3TienePerdidaOlfato(e3TienePerdidaOlfato);
            mCuestionario.setE3TienePerdidaGusto(e3TienePerdidaGusto);
            mCuestionario.setE3TieneTos(e3TieneTos);
            mCuestionario.setE3TieneDificultadRespirar(e3TieneDificultadRespirar);
            mCuestionario.setE3TieneDolorPecho(e3TieneDolorPecho);
            mCuestionario.setE3TienePalpitaciones(e3TienePalpitaciones);
            mCuestionario.setE3TieneDolorArticulaciones(e3TieneDolorArticulaciones);
            mCuestionario.setE3TieneParalisis(e3TieneParalisis);
            mCuestionario.setE3TieneMareos(e3TieneMareos);
            mCuestionario.setE3TienePensamientoNublado(e3TienePensamientoNublado);
            mCuestionario.setE3TieneProblemasDormir(e3TieneProblemasDormir);
            mCuestionario.setE3TieneDepresion(e3TieneDepresion);
            mCuestionario.setE3TieneOtrosSintomas(e3TieneOtrosSintomas);
            mCuestionario.setE3cualesSintomas(e3TieneCualesSintomas);
            mCuestionario.setE3SabeTiempoRecuperacion(e3SabeTiempoRecuperacion);
            mCuestionario.setE3TiempoRecuperacion(e3TiempoRecuperacion);
            mCuestionario.setE3TiempoRecuperacionEn(e3TiempoRecuperacionEn);
            mCuestionario.setE3SeveridadEnfermedad(e3SeveridadEnfermedad);
            mCuestionario.setE3TomoMedicamento(e3TomoMedicamento);
            if (e3QueMedicamento !=null && !e3QueMedicamento.isEmpty()) {
                String keysCriterios = "";
                e3QueMedicamento = e3QueMedicamento.replaceAll(",", "','");
                estudiosAdapter.open();
                List<MessageResource> catMedi = estudiosAdapter.getMessageResources(CatalogosDBConstants.spanish + " in ('" + e3QueMedicamento + "') and "
                        + CatalogosDBConstants.catRoot + "='COVID_MEDICAMENTO_SVC'", null);
                estudiosAdapter.close();
                for (MessageResource ms : catMedi) {
                    keysCriterios += ms.getCatKey() + ",";
                }
                if (!keysCriterios.isEmpty())
                    keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                mCuestionario.setE3QueMedicamento(keysCriterios);
            }
            mCuestionario.setE3OtroMedicamento(e3OtroMedicamento);
            mCuestionario.setE3OxigenoDomicilio(e3OxigenoDomicilio);
            mCuestionario.setE3TiempoOxigenoDom(e3TiempoOxigenoDom);

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
            mCuestionario.setE2FumadoPrevioEnfermedad(e2FumadoPrevioEnfermedad);
            mCuestionario.setE2FumaActualmente(e2FumaActualmente);
            mCuestionario.setE2Embarazada(e2Embarazada);
            mCuestionario.setE2RecuerdaSemanasEmb(e2RecuerdaSemanasEmb);
            mCuestionario.setE2SemanasEmbarazo(e2SemanasEmbarazo);
            mCuestionario.setE2FinalEmbarazo(e2FinalEmbarazo);
            mCuestionario.setE2OtroFinalEmbarazo(e2OtroFinalEmbarazo);
            mCuestionario.setE2DabaPecho(e2DabaPecho);
            mCuestionario.setE3FumadoPrevioEnfermedad(e3FumadoPrevioEnfermedad);
            mCuestionario.setE3FumaActualmente(e3FumaActualmente);
            mCuestionario.setE3Embarazada(e3Embarazada);
            mCuestionario.setE3RecuerdaSemanasEmb(e3RecuerdaSemanasEmb);
            mCuestionario.setE3SemanasEmbarazo(e3SemanasEmbarazo);
            mCuestionario.setE3FinalEmbarazo(e3FinalEmbarazo);
            mCuestionario.setE3OtroFinalEmbarazo(e3OtroFinalEmbarazo);
            mCuestionario.setE3DabaPecho(e3DabaPecho);

            mCuestionario.setTrabajadorSalud(trabajadorSalud);
            mCuestionario.setEnfermoCovid19(enfermoCovid19);
            mCuestionario.setCuantasVecesEnfermo(cuantasVecesEnfermo);
            mCuestionario.setSabeEvento1(sabeEvento1);
            if (fechaEvento1 !=null && !fechaEvento1.isEmpty()) mCuestionario.setEvento1(formatter.parse(fechaEvento1));
            mCuestionario.setAnioEvento1(anioEvento1);
            mCuestionario.setMesEvento1(mesEvento1);
            mCuestionario.setSabeEvento2(sabeEvento2);
            if (fechaEvento2 !=null && !fechaEvento2.isEmpty()) mCuestionario.setEvento2(formatter.parse(fechaEvento2));
            mCuestionario.setAnioEvento2(anioEvento2);
            mCuestionario.setMesEvento2(mesEvento2);
            mCuestionario.setSabeEvento3(sabeEvento3);
            if (fechaEvento3 !=null && !fechaEvento3.isEmpty()) mCuestionario.setEvento3(formatter.parse(fechaEvento3));
            mCuestionario.setAnioEvento3(anioEvento3);
            mCuestionario.setMesEvento3(mesEvento3);
            mCuestionario.setE1Febricula(e1Febricula);
            mCuestionario.setE1Fiebre(e1Fiebre);
            mCuestionario.setE1Escalofrio(e1Escalofrio);
            mCuestionario.setE1TemblorEscalofrio(e1TemblorEscalofrio);
            mCuestionario.setE1DolorMuscular(e1DolorMuscular);
            mCuestionario.setE1DolorArticular(e1DolorArticular);
            mCuestionario.setE1SecresionNasal(e1SecresionNasal);
            mCuestionario.setE1DolorGarganta(e1DolorGarganta);
            mCuestionario.setE1Tos(e1Tos);
            mCuestionario.setE1DificultadResp(e1DificultadResp);
            mCuestionario.setE1DolorPecho(e1DolorPecho);
            mCuestionario.setE1NauseasVomito(e1NauseasVomito);
            mCuestionario.setE1DolorCabeza(e1DolorCabeza);
            mCuestionario.setE1DolorAbdominal(e1DolorAbdominal);
            mCuestionario.setE1Diarrea(e1Diarrea);
            mCuestionario.setE1DificultadDormir(e1DificultadDormir);
            mCuestionario.setE1Debilidad(e1Debilidad);
            mCuestionario.setE1PerdidaOlfatoGusto(e1PerdidaOlfatoGusto);
            mCuestionario.setE1Mareo(e1Mareo);
            mCuestionario.setE1Sarpullido(e1Sarpullido);
            mCuestionario.setE1Desmayo(e1Desmayo);
            mCuestionario.setE1QuedoCama(e1QuedoCama);
            mCuestionario.setE2Febricula(e2Febricula);
            mCuestionario.setE2Fiebre(e2Fiebre);
            mCuestionario.setE2Escalofrio(e2Escalofrio);
            mCuestionario.setE2TemblorEscalofrio(e2TemblorEscalofrio);
            mCuestionario.setE2DolorMuscular(e2DolorMuscular);
            mCuestionario.setE2DolorArticular(e2DolorArticular);
            mCuestionario.setE2SecresionNasal(e2SecresionNasal);
            mCuestionario.setE2DolorGarganta(e2DolorGarganta);
            mCuestionario.setE2Tos(e2Tos);
            mCuestionario.setE2DificultadResp(e2DificultadResp);
            mCuestionario.setE2DolorPecho(e2DolorPecho);
            mCuestionario.setE2NauseasVomito(e2NauseasVomito);
            mCuestionario.setE2DolorCabeza(e2DolorCabeza);
            mCuestionario.setE2DolorAbdominal(e2DolorAbdominal);
            mCuestionario.setE2Diarrea(e2Diarrea);
            mCuestionario.setE2DificultadDormir(e2DificultadDormir);
            mCuestionario.setE2Debilidad(e2Debilidad);
            mCuestionario.setE2PerdidaOlfatoGusto(e2PerdidaOlfatoGusto);
            mCuestionario.setE2Mareo(e2Mareo);
            mCuestionario.setE2Sarpullido(e2Sarpullido);
            mCuestionario.setE2Desmayo(e2Desmayo);
            mCuestionario.setE2QuedoCama(e2QuedoCama);
            mCuestionario.setE3Febricula(e3Febricula);
            mCuestionario.setE3Fiebre(e3Fiebre);
            mCuestionario.setE3Escalofrio(e3Escalofrio);
            mCuestionario.setE3TemblorEscalofrio(e3TemblorEscalofrio);
            mCuestionario.setE3DolorMuscular(e3DolorMuscular);
            mCuestionario.setE3DolorArticular(e3DolorArticular);
            mCuestionario.setE3SecresionNasal(e3SecresionNasal);
            mCuestionario.setE3DolorGarganta(e3DolorGarganta);
            mCuestionario.setE3Tos(e3Tos);
            mCuestionario.setE3DificultadResp(e3DificultadResp);
            mCuestionario.setE3DolorPecho(e3DolorPecho);
            mCuestionario.setE3NauseasVomito(e3NauseasVomito);
            mCuestionario.setE3DolorCabeza(e3DolorCabeza);
            mCuestionario.setE3DolorAbdominal(e3DolorAbdominal);
            mCuestionario.setE3Diarrea(e3Diarrea);
            mCuestionario.setE3DificultadDormir(e3DificultadDormir);
            mCuestionario.setE3Debilidad(e3Debilidad);
            mCuestionario.setE3PerdidaOlfatoGusto(e3PerdidaOlfatoGusto);
            mCuestionario.setE3Mareo(e3Mareo);
            mCuestionario.setE3Sarpullido(e3Sarpullido);
            mCuestionario.setE3Desmayo(e3Desmayo);
            mCuestionario.setE3QuedoCama(e3QuedoCama);
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
            if (fechaDosis1!=null && !fechaDosis1.isEmpty()) mCuestionario.setFechaDosis1(formatter.parse(fechaDosis1));
            mCuestionario.setNombreDosis2(nombreDosis2);
            mCuestionario.setOtraVacunaDosis2(otraVacunaDosis2);
            mCuestionario.setLoteDosis2(loteDosis2);
            if (fechaDosis2!=null && !fechaDosis2.isEmpty()) mCuestionario.setFechaDosis2(formatter.parse(fechaDosis2));
            mCuestionario.setNombreDosis3(nombreDosis3);
            mCuestionario.setOtraVacunaDosis3(otraVacunaDosis3);
            mCuestionario.setLoteDosis3(loteDosis3);
            if (fechaDosis3!=null && !fechaDosis3.isEmpty()) mCuestionario.setFechaDosis3(formatter.parse(fechaDosis3));

            mCuestionario.setPresentoSintomasDosis1(presentoSintomasDosis1);
            mCuestionario.setDolorSitioDosis1(dolorSitioDosis1);
            mCuestionario.setFiebreDosis1(fiebreDosis1);
            mCuestionario.setCansancioDosis1(cansancioDosis1);
            mCuestionario.setDolorCabezaDosis1(dolorCabezaDosis1);
            mCuestionario.setDiarreaDosis1(diarreaDosis1);
            mCuestionario.setVomitoDosis1(vomitoDosis1);
            mCuestionario.setDolorMuscularDosis1(dolorMuscularDosis1);
            mCuestionario.setEscalofriosDosis1(escalofriosDosis1);
            mCuestionario.setReaccionAlergicaDosis1(reaccionAlergicaDosis1);
            mCuestionario.setInfeccionSitioDosis1(infeccionSitioDosis1);
            mCuestionario.setNauseasDosis1(nauseasDosis1);
            mCuestionario.setBultosDosis1(bultosDosis1);
            mCuestionario.setOtrosDosis1(otrosDosis1);
            mCuestionario.setDesOtrosDosis1(desOtrosDosis1);

            mCuestionario.setPresentoSintomasDosis3(presentoSintomasDosis3);
            mCuestionario.setDolorSitioDosis3(dolorSitioDosis3);
            mCuestionario.setFiebreDosis3(fiebreDosis3);
            mCuestionario.setCansancioDosis3(cansancioDosis3);
            mCuestionario.setDolorCabezaDosis3(dolorCabezaDosis3);
            mCuestionario.setDiarreaDosis3(diarreaDosis3);
            mCuestionario.setVomitoDosis3(vomitoDosis3);
            mCuestionario.setDolorMuscularDosis3(dolorMuscularDosis3);
            mCuestionario.setEscalofriosDosis3(escalofriosDosis3);
            mCuestionario.setReaccionAlergicaDosis3(reaccionAlergicaDosis3);
            mCuestionario.setInfeccionSitioDosis3(infeccionSitioDosis3);
            mCuestionario.setNauseasDosis3(nauseasDosis3);
            mCuestionario.setBultosDosis3(bultosDosis3);
            mCuestionario.setOtrosDosis3(otrosDosis3);
            mCuestionario.setDesOtrosDosis3(desOtrosDosis3);

            mCuestionario.setPresentoSintomasDosis2(presentoSintomasDosis2);
            mCuestionario.setDolorSitioDosis2(dolorSitioDosis2);
            mCuestionario.setFiebreDosis2(fiebreDosis2);
            mCuestionario.setCansancioDosis2(cansancioDosis2);
            mCuestionario.setDolorCabezaDosis2(dolorCabezaDosis2);
            mCuestionario.setDiarreaDosis2(diarreaDosis2);
            mCuestionario.setVomitoDosis2(vomitoDosis2);
            mCuestionario.setDolorMuscularDosis2(dolorMuscularDosis2);
            mCuestionario.setEscalofriosDosis2(escalofriosDosis2);
            mCuestionario.setReaccionAlergicaDosis2(reaccionAlergicaDosis2);
            mCuestionario.setInfeccionSitioDosis2(infeccionSitioDosis2);
            mCuestionario.setNauseasDosis2(nauseasDosis2);
            mCuestionario.setBultosDosis2(bultosDosis2);
            mCuestionario.setOtrosDosis2(otrosDosis2);
            mCuestionario.setDesOtrosDosis2(desOtrosDosis2);

            mCuestionario.setCovid19PosteriorVacuna(covid19PosteriorVacuna);
            mCuestionario.setFechaEventoEnfermoPostVac(fechaEventoEnfermoPostVac);

            //mCuestionario.setSabeFechaEnfPostVac(sabeFechaEnfPostVac);
            //if (fechaEnfPostVac!=null && !fechaEnfPostVac.isEmpty()) mCuestionario.setFechaEnfPostVac(formatter.parse(fechaEnfPostVac));
            //mCuestionario.setAnioEnfPostVac(anioEnfPostVac);
            //mCuestionario.setMesEnfPostVac(mesEnfPostVac);

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

    /***
     * Validar si no se ha ingresado respuesta a una pregunta requerida que depende(hijo) de la respuesta de otra pregunta anterior(padre).
     * Por defecto la respuesta de la pregunta padre debe ser Constants.YESKEYSND para que sea requerida la respuesta hija
     * @param variable valor de la variable hija
     * @param resId id del recurso en el fragment
     * @param padre valor de la variable padre
     * @return true si la respuesta de la pregunta padre es Constants.YESKEYSND y no se ha ingresado respuesta a la hija+, false en caso contrario
     */
    private boolean faltaDatoRequeridoHijo(String variable, int resId, String padre){
        if (padre!=null && padre.equals(Constants.YESKEYSND) && (variable == null || variable.equals(""))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(resId)),Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijo(Integer variable, int resId, String padre){
        if (padre!=null && padre.equals(Constants.YESKEYSND) && (variable == null || variable <= 0)){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(resId)),Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijo(String variable, int resId, String padre, String valorPadre){
        if (padre!=null && padre.equals(valorPadre) && (variable == null || variable.equals(""))){
            Toast.makeText(getActivity(), getActivity().getString(R.string.wrongSelect,getActivity().getString(resId)),Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean faltaDatoRequeridoHijoNegado(String variable, int resId, String padre, String valorPadre){
        if (padre!=null && !padre.isEmpty() && !padre.equals(valorPadre) && (variable == null || variable.equals(""))){
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

    private boolean faltaDatoRequeridoHijoContador(String variable, int resId, String padre, Integer valorPadre){
        if (padre == null || padre.isEmpty()) return false;
        if (Integer.parseInt(padre) >= valorPadre && (variable == null || variable.isEmpty())){
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
                //mCatalogoMeses = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", CatalogosDBConstants.order);
                mCatalogoVecesEnfermo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_VECES_ENF'", CatalogosDBConstants.order);
                List<MessageResource> meses = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_MESES'", CatalogosDBConstants.order);
                mCatalogoTiempoOxigeno = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_TIEMPO_OXI'", CatalogosDBConstants.order);
                mCatalogoVacunas = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_VACUNA'", CatalogosDBConstants.order);
                mCatalogoDosis = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_DOSIS'", CatalogosDBConstants.order);
                //desde febrero al mes actual
                for(MessageResource mes : meses){
                    if (Integer.parseInt(mes.getCatKey()) >= 2 && Integer.parseInt(mes.getCatKey()) <= c.get(Calendar.MONTH)+1){
                        mCatalogoMeses.add(mes);
                    }
                    if (Integer.parseInt(mes.getCatKey()) <= c.get(Calendar.MONTH)+1){
                        mCatalogoMesesVacuna.add(mes);
                    }
                }
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

            mCatalogoMeses.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoMeses);
            ArrayAdapter<MessageResource> dataAdapterMeses = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoMeses);
            dataAdapterMeses.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoFinalEmb.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoFinalEmb);
            ArrayAdapter<MessageResource> dataAdapterFinalEmbarazo = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoFinalEmb);
            dataAdapterFinalEmbarazo.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoVecesEnfermo.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoVecesEnfermo);
            ArrayAdapter<MessageResource> dataAdapterVecesEnfermo = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoVecesEnfermo);
            dataAdapterVecesEnfermo.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoTiempoOxigeno.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoTiempoOxigeno);
            ArrayAdapter<MessageResource> dataAdapterTmpOxigeno = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoTiempoOxigeno);
            dataAdapterTmpOxigeno.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoVacunas.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoVacunas);
            ArrayAdapter<MessageResource> dataAdapterVacunas = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoVacunas);
            dataAdapterVacunas.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoDosis.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoDosis);
            ArrayAdapter<MessageResource> dataAdapterDosis = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoDosis);
            dataAdapterDosis.setDropDownViewResource(R.layout.spinner_item);

            mCatalogoMesesVacuna.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatalogoMesesVacuna);
            ArrayAdapter<MessageResource> dataAdapterMesesVac = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatalogoMesesVacuna);
            dataAdapterMesesVac.setDropDownViewResource(R.layout.spinner_item);

            spinEnfermoCovid19.setAdapter(dataAdapterSiNoNsNc);
            spinCuantasVecesEnfermo.setAdapter(dataAdapterVecesEnfermo);
            spinSabeEvento1.setAdapter(dataAdapterSiNo);
            spinSabeEvento2.setAdapter(dataAdapterSiNo);
            spinSabeEvento3.setAdapter(dataAdapterSiNo);
            spinMesEvento1.setAdapter(dataAdapterMeses);
            spinMesEvento2.setAdapter(dataAdapterMeses);
            spinMesEvento3.setAdapter(dataAdapterMeses);

            spinE1BuscoAyuda.setAdapter(dataAdapterSiNoNc);
            spinE1RecibioSeguimiento.setAdapter(dataAdapterSiNo);
            spinE1FueIntubado.setAdapter(dataAdapterSiNo);
            spinE2BuscoAyuda.setAdapter(dataAdapterSiNoNc);
            spinE2RecibioSeguimiento.setAdapter(dataAdapterSiNo);
            spinE2FueIntubado.setAdapter(dataAdapterSiNo);
            spinE3BuscoAyuda.setAdapter(dataAdapterSiNoNc);
            spinE3RecibioSeguimiento.setAdapter(dataAdapterSiNo);
            spinE3FueIntubado.setAdapter(dataAdapterSiNo);

            spinFumado.setAdapter(dataAdapterSiNo);

            spinE1SabeCuantasNoches.setAdapter(dataAdapterSiNsNc);
            spinE1SabeFechaAdmision.setAdapter(dataAdapterSiNsNc);
            spinE1SabeFechaAlta.setAdapter(dataAdapterSiNsNc);
            spinE1SabeTiempoRecuperacion.setAdapter(dataAdapterSiNsNc);
            spinE1ConoceLugarExposicion.setAdapter(dataAdapterSiNoNc);
            spinE1DondeBuscoAyuda.setAdapter(dataAdapterDBA);
            spinE1TipoSeguimiento.setAdapter(dataAdapterTipoSeg);
            spinE1TmpDespuesBuscoAyuda.setAdapter(dataAdapterTiempo);
            spinE1SeveridadEnfermedad.setAdapter(dataAdapterSev);
            spinE1TiempoRecuperacionEn.setAdapter(dataAdapterUM);
            spinE1FumadoPrevioEnfermedad.setAdapter(dataAdapterFuma);
            spinE1FumaActualmente.setAdapter(dataAdapterFuma);
            spinE1MesInicioSintoma.setAdapter(dataAdapterMeses);
            spinE1TomoMedicamento.setAdapter(dataAdapterSiNoNsNc);
            spinE1SabeInicioSintoma.setAdapter(dataAdapterSiNo);
            spinE1UnaNocheHospital.setAdapter(dataAdapterSiNoNsNc);
            spinE1UtilizoOxigeno.setAdapter(dataAdapterSiNoNsNc);
            spinE1EstuvoUCI.setAdapter(dataAdapterSiNoNsNc);
            spinE1RecuperadoCovid19.setAdapter(dataAdapterSiNoNsNc);
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
            spinE1OxigenoDomicilio.setAdapter(dataAdapterSiNoNsNc);
            spinE1TiempoOxigenoDom.setAdapter(dataAdapterTmpOxigeno);

            spinE2SabeCuantasNoches.setAdapter(dataAdapterSiNsNc);
            spinE2SabeFechaAdmision.setAdapter(dataAdapterSiNsNc);
            spinE2SabeFechaAlta.setAdapter(dataAdapterSiNsNc);
            spinE2SabeTiempoRecuperacion.setAdapter(dataAdapterSiNsNc);
            spinE2ConoceLugarExposicion.setAdapter(dataAdapterSiNoNc);
            spinE2DondeBuscoAyuda.setAdapter(dataAdapterDBA);
            spinE2TipoSeguimiento.setAdapter(dataAdapterTipoSeg);
            spinE2TmpDespuesBuscoAyuda.setAdapter(dataAdapterTiempo);
            spinE2SeveridadEnfermedad.setAdapter(dataAdapterSev);
            spinE2TiempoRecuperacionEn.setAdapter(dataAdapterUM);
            spinE2FumadoPrevioEnfermedad.setAdapter(dataAdapterFuma);
            spinE2FumaActualmente.setAdapter(dataAdapterFuma);
            spinE2MesInicioSintoma.setAdapter(dataAdapterMeses);
            spinE2TomoMedicamento.setAdapter(dataAdapterSiNoNsNc);
            spinE2SabeInicioSintoma.setAdapter(dataAdapterSiNo);
            spinE2UnaNocheHospital.setAdapter(dataAdapterSiNoNsNc);
            spinE2UtilizoOxigeno.setAdapter(dataAdapterSiNoNsNc);
            spinE2EstuvoUCI.setAdapter(dataAdapterSiNoNsNc);
            spinE2RecuperadoCovid19.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneFebricula.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneCansancio.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneDolorCabeza.setAdapter(dataAdapterSiNoNsNc);
            spinE2TienePerdidaOlfato.setAdapter(dataAdapterSiNoNsNc);
            spinE2TienePerdidaGusto.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneTos.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneDificultadRespirar.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneDolorPecho.setAdapter(dataAdapterSiNoNsNc);
            spinE2TienePalpitaciones.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneDolorArticulaciones.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneParalisis.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneMareos.setAdapter(dataAdapterSiNoNsNc);
            spinE2TienePensamientoNublado.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneProblemasDormir.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneDepresion.setAdapter(dataAdapterSiNoNsNc);
            spinE2TieneOtrosSintomas.setAdapter(dataAdapterSiNoNsNc);
            spinE2OxigenoDomicilio.setAdapter(dataAdapterSiNoNsNc);
            spinE2TiempoOxigenoDom.setAdapter(dataAdapterTmpOxigeno);

            spinE3SabeCuantasNoches.setAdapter(dataAdapterSiNsNc);
            spinE3SabeFechaAdmision.setAdapter(dataAdapterSiNsNc);
            spinE3SabeFechaAlta.setAdapter(dataAdapterSiNsNc);
            spinE3SabeTiempoRecuperacion.setAdapter(dataAdapterSiNsNc);
            spinE3ConoceLugarExposicion.setAdapter(dataAdapterSiNoNc);
            spinE3DondeBuscoAyuda.setAdapter(dataAdapterDBA);
            spinE3TipoSeguimiento.setAdapter(dataAdapterTipoSeg);
            spinE3TmpDespuesBuscoAyuda.setAdapter(dataAdapterTiempo);
            spinE3SeveridadEnfermedad.setAdapter(dataAdapterSev);
            spinE3TiempoRecuperacionEn.setAdapter(dataAdapterUM);
            spinE3FumadoPrevioEnfermedad.setAdapter(dataAdapterFuma);
            spinE3FumaActualmente.setAdapter(dataAdapterFuma);
            spinE3MesInicioSintoma.setAdapter(dataAdapterMeses);
            spinE3TomoMedicamento.setAdapter(dataAdapterSiNoNsNc);
            spinE3SabeInicioSintoma.setAdapter(dataAdapterSiNo);
            spinE3UnaNocheHospital.setAdapter(dataAdapterSiNoNsNc);
            spinE3UtilizoOxigeno.setAdapter(dataAdapterSiNoNsNc);
            spinE3EstuvoUCI.setAdapter(dataAdapterSiNoNsNc);
            spinE3RecuperadoCovid19.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneFebricula.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneCansancio.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneDolorCabeza.setAdapter(dataAdapterSiNoNsNc);
            spinE3TienePerdidaOlfato.setAdapter(dataAdapterSiNoNsNc);
            spinE3TienePerdidaGusto.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneTos.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneDificultadRespirar.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneDolorPecho.setAdapter(dataAdapterSiNoNsNc);
            spinE3TienePalpitaciones.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneDolorArticulaciones.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneParalisis.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneMareos.setAdapter(dataAdapterSiNoNsNc);
            spinE3TienePensamientoNublado.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneProblemasDormir.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneDepresion.setAdapter(dataAdapterSiNoNsNc);
            spinE3TieneOtrosSintomas.setAdapter(dataAdapterSiNoNsNc);
            spinE3OxigenoDomicilio.setAdapter(dataAdapterSiNoNsNc);
            spinE3TiempoOxigenoDom.setAdapter(dataAdapterTmpOxigeno);

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
            spinTrabajadorSalud.setAdapter(dataAdapterSiNoNc);

            spinE1Embarazada.setAdapter(dataAdapterSiNoNc);
            spinE1RecuerdaSemanasEmb.setAdapter(dataAdapterSiNo);
            spinE1DabaPecho.setAdapter(dataAdapterSiNoNc);
            spinE1FinalEmbarazo.setAdapter(dataAdapterFinalEmbarazo);

            spinE2Embarazada.setAdapter(dataAdapterSiNoNc);
            spinE2RecuerdaSemanasEmb.setAdapter(dataAdapterSiNo);
            spinE2DabaPecho.setAdapter(dataAdapterSiNoNc);
            spinE2FinalEmbarazo.setAdapter(dataAdapterFinalEmbarazo);

            spinE3Embarazada.setAdapter(dataAdapterSiNoNc);
            spinE3RecuerdaSemanasEmb.setAdapter(dataAdapterSiNo);
            spinE3DabaPecho.setAdapter(dataAdapterSiNoNc);
            spinE3FinalEmbarazo.setAdapter(dataAdapterFinalEmbarazo);

            spinE1Febricula.setAdapter(dataAdapterSiNoNsNc);
            spinE1Fiebre.setAdapter(dataAdapterSiNoNsNc);
            spinE1Escalofrio.setAdapter(dataAdapterSiNoNsNc);
            spinE1TemblorEscalofrio.setAdapter(dataAdapterSiNoNsNc);
            spinE1DolorMuscular.setAdapter(dataAdapterSiNoNsNc);
            spinE1DolorArticular.setAdapter(dataAdapterSiNoNsNc);
            spinE1SecresionNasal.setAdapter(dataAdapterSiNoNsNc);
            spinE1DolorGarganta.setAdapter(dataAdapterSiNoNsNc);
            spinE1Tos.setAdapter(dataAdapterSiNoNsNc);
            spinE1DificultadResp.setAdapter(dataAdapterSiNoNsNc);
            spinE1DolorPecho.setAdapter(dataAdapterSiNoNsNc);
            spinE1NauseasVomito.setAdapter(dataAdapterSiNoNsNc);
            spinE1DolorCabeza.setAdapter(dataAdapterSiNoNsNc);
            spinE1DolorAbdominal.setAdapter(dataAdapterSiNoNsNc);
            spinE1Diarrea.setAdapter(dataAdapterSiNoNsNc);
            spinE1DificultadDormir.setAdapter(dataAdapterSiNoNsNc);
            spinE1Debilidad.setAdapter(dataAdapterSiNoNsNc);
            spinE1PerdidaOlfatoGusto.setAdapter(dataAdapterSiNoNsNc);
            spinE1Mareo.setAdapter(dataAdapterSiNoNsNc);
            spinE1Sarpullido.setAdapter(dataAdapterSiNoNsNc);
            spinE1Desmayo.setAdapter(dataAdapterSiNoNsNc);
            spinE1QuedoCama.setAdapter(dataAdapterSiNoNsNc);
            spinE2Febricula.setAdapter(dataAdapterSiNoNsNc);
            spinE2Fiebre.setAdapter(dataAdapterSiNoNsNc);
            spinE2Escalofrio.setAdapter(dataAdapterSiNoNsNc);
            spinE2TemblorEscalofrio.setAdapter(dataAdapterSiNoNsNc);
            spinE2DolorMuscular.setAdapter(dataAdapterSiNoNsNc);
            spinE2DolorArticular.setAdapter(dataAdapterSiNoNsNc);
            spinE2SecresionNasal.setAdapter(dataAdapterSiNoNsNc);
            spinE2DolorGarganta.setAdapter(dataAdapterSiNoNsNc);
            spinE2Tos.setAdapter(dataAdapterSiNoNsNc);
            spinE2DificultadResp.setAdapter(dataAdapterSiNoNsNc);
            spinE2DolorPecho.setAdapter(dataAdapterSiNoNsNc);
            spinE2NauseasVomito.setAdapter(dataAdapterSiNoNsNc);
            spinE2DolorCabeza.setAdapter(dataAdapterSiNoNsNc);
            spinE2DolorAbdominal.setAdapter(dataAdapterSiNoNsNc);
            spinE2Diarrea.setAdapter(dataAdapterSiNoNsNc);
            spinE2DificultadDormir.setAdapter(dataAdapterSiNoNsNc);
            spinE2Debilidad.setAdapter(dataAdapterSiNoNsNc);
            spinE2PerdidaOlfatoGusto.setAdapter(dataAdapterSiNoNsNc);
            spinE2Mareo.setAdapter(dataAdapterSiNoNsNc);
            spinE2Sarpullido.setAdapter(dataAdapterSiNoNsNc);
            spinE2Desmayo.setAdapter(dataAdapterSiNoNsNc);
            spinE2QuedoCama.setAdapter(dataAdapterSiNoNsNc);
            spinE3Febricula.setAdapter(dataAdapterSiNoNsNc);
            spinE3Fiebre.setAdapter(dataAdapterSiNoNsNc);
            spinE3Escalofrio.setAdapter(dataAdapterSiNoNsNc);
            spinE3TemblorEscalofrio.setAdapter(dataAdapterSiNoNsNc);
            spinE3DolorMuscular.setAdapter(dataAdapterSiNoNsNc);
            spinE3DolorArticular.setAdapter(dataAdapterSiNoNsNc);
            spinE3SecresionNasal.setAdapter(dataAdapterSiNoNsNc);
            spinE3DolorGarganta.setAdapter(dataAdapterSiNoNsNc);
            spinE3Tos.setAdapter(dataAdapterSiNoNsNc);
            spinE3DificultadResp.setAdapter(dataAdapterSiNoNsNc);
            spinE3DolorPecho.setAdapter(dataAdapterSiNoNsNc);
            spinE3NauseasVomito.setAdapter(dataAdapterSiNoNsNc);
            spinE3DolorCabeza.setAdapter(dataAdapterSiNoNsNc);
            spinE3DolorAbdominal.setAdapter(dataAdapterSiNoNsNc);
            spinE3Diarrea.setAdapter(dataAdapterSiNoNsNc);
            spinE3DificultadDormir.setAdapter(dataAdapterSiNoNsNc);
            spinE3Debilidad.setAdapter(dataAdapterSiNoNsNc);
            spinE3PerdidaOlfatoGusto.setAdapter(dataAdapterSiNoNsNc);
            spinE3Mareo.setAdapter(dataAdapterSiNoNsNc);
            spinE3Sarpullido.setAdapter(dataAdapterSiNoNsNc);
            spinE3Desmayo.setAdapter(dataAdapterSiNoNsNc);
            spinE3QuedoCama.setAdapter(dataAdapterSiNoNsNc);

            spinVacunadoCovid19.setAdapter(dataAdapterSiNoNsNc);
            spinMuestraTarjetaVac.setAdapter(dataAdapterSiNoNc);
            spinSabeNombreVacuna.setAdapter(dataAdapterSiNoNc);
            spinMesVacuna.setAdapter(dataAdapterMesesVac);
            spinCuantasDosis.setAdapter(dataAdapterDosis);
            spinNombreDosis1.setAdapter(dataAdapterVacunas);
            spinNombreDosis2.setAdapter(dataAdapterVacunas);
            spinNombreDosis3.setAdapter(dataAdapterVacunas);

            spinPresentoSintomasDosis1.setAdapter(dataAdapterSiNoNc);
            spinDolorSitioDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinFiebreDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinCansancioDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinDolorCabezaDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinDiarreaDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinVomitoDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinDolorMuscularDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinEscalofriosDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinReaccionAlergicaDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinInfeccionSitioDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinNauseasDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinBultosDosis1.setAdapter(dataAdapterSiNoNsNc);
            spinOtrosDosis1.setAdapter(dataAdapterSiNoNsNc);

            spinPresentoSintomasDosis2.setAdapter(dataAdapterSiNoNc);
            spinDolorSitioDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinFiebreDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinCansancioDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinDolorCabezaDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinDiarreaDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinVomitoDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinDolorMuscularDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinEscalofriosDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinReaccionAlergicaDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinInfeccionSitioDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinNauseasDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinBultosDosis2.setAdapter(dataAdapterSiNoNsNc);
            spinOtrosDosis2.setAdapter(dataAdapterSiNoNsNc);

            spinPresentoSintomasDosis3.setAdapter(dataAdapterSiNoNc);
            spinDolorSitioDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinFiebreDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinCansancioDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinDolorCabezaDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinDiarreaDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinVomitoDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinDolorMuscularDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinEscalofriosDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinReaccionAlergicaDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinInfeccionSitioDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinNauseasDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinBultosDosis3.setAdapter(dataAdapterSiNoNsNc);
            spinOtrosDosis3.setAdapter(dataAdapterSiNoNsNc);

            spinCovid19PosteriorVacuna.setAdapter(dataAdapterSiNoNsNc);
            //spinSabeFechaEnfPostVac.setAdapter(dataAdapterSiNoNc);
            //spinMesEnfPostVac.setAdapter(dataAdapterMesesVac);

            spinE1QueMedicamento.setItems(mMedicamentos, getString(R.string.select), new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(boolean[] selected) {
                    int indice = 0;
                    e1QueMedicamento = null;
                    for(boolean item : selected){
                        if (item) {
                            if (e1QueMedicamento ==null) e1QueMedicamento = mMedicamentos.get(indice);
                            else  e1QueMedicamento += "," + mMedicamentos.get(indice);
                        }
                        indice++;
                    }
                    if (e1QueMedicamento !=null && e1QueMedicamento.toLowerCase().contains("otro")){
                        textE1OtroMedicamento.setVisibility(View.VISIBLE);
                        inputE1OtroMedicamento.setVisibility(View.VISIBLE);
                    }else{
                        textE1OtroMedicamento.setVisibility(View.GONE);
                        inputE1OtroMedicamento.setVisibility(View.GONE);
                    }
                }
            });

            spinE2QueMedicamento.setItems(mMedicamentos, getString(R.string.select), new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(boolean[] selected) {
                    int indice = 0;
                    e2QueMedicamento = null;
                    for(boolean item : selected){
                        if (item) {
                            if (e2QueMedicamento ==null) e2QueMedicamento = mMedicamentos.get(indice);
                            else  e2QueMedicamento += "," + mMedicamentos.get(indice);
                        }
                        indice++;
                    }
                    if (e2QueMedicamento !=null && e2QueMedicamento.toLowerCase().contains("otro")){
                        textE2OtroMedicamento.setVisibility(View.VISIBLE);
                        inputE2OtroMedicamento.setVisibility(View.VISIBLE);
                    }else{
                        textE2OtroMedicamento.setVisibility(View.GONE);
                        inputE2OtroMedicamento.setVisibility(View.GONE);
                    }
                }
            });

            spinE3QueMedicamento.setItems(mMedicamentos, getString(R.string.select), new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(boolean[] selected) {
                    int indice = 0;
                    e3QueMedicamento = null;
                    for(boolean item : selected){
                        if (item) {
                            if (e3QueMedicamento ==null) e3QueMedicamento = mMedicamentos.get(indice);
                            else  e3QueMedicamento += "," + mMedicamentos.get(indice);
                        }
                        indice++;
                    }
                    if (e3QueMedicamento !=null && e3QueMedicamento.toLowerCase().contains("otro")){
                        textE3OtroMedicamento.setVisibility(View.VISIBLE);
                        inputE3OtroMedicamento.setVisibility(View.VISIBLE);
                    }else{
                        textE3OtroMedicamento.setVisibility(View.GONE);
                        inputE3OtroMedicamento.setVisibility(View.GONE);
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
                i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtras(arguments);
                startActivity(i);
                getActivity().finish();
            }
        }

    }
}
