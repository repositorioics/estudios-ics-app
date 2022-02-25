package ni.org.ics.estudios.appmovil.utils;


import android.net.Uri;

import java.util.Arrays;
import java.util.List;

/**
 * Constantes usadas en multiples clases de la aplicacion
 * 
 * @author William Aviles
 * 
 */
public class Constants {
	
	
	// status for records
    public static final String STATUS_SUBMITTED = "1";
    public static final String STATUS_NOT_SUBMITTED = "0";
    
	//nombres de objetos
	public static final String TITLE = "titulo";
	public static final String CASA = "casa";
    public static final String CASACHF = "casaChf";
	public static final String PARTICIPANTES = "participantes";
    public static final String PARTICIPANTE = "participante";
    public static final String HABITACION = "habitacion";
    public static final String VISITA = "visita";
    public static final String CUARTO = "cuarto";
    public static final String CAMA = "cama";
    public static final String PERSONACAMA = "personacama";
    public static final String AREA = "area";
    public static final String VENTANA = "ventana";
    public static final String BANIO = "banio";
    public static final String TELEFONO = "telefono";
    public static final String VOLUMEN = "volumen";
    public static final String ENCUESTA = "encuesta";
    public static final String VISITA_FINAL = "visita_final";
    public static final String CASO = "caso";
    public static final String ES_CANDIDATO = "candidato";
    
    //Form wizard
    public static final String FORM_NAME = "form";
	public static final String FORM_NUEVO_TAMIZAJE_CASA = "tamizaje_casa";
    public static final String FORM_NUEVO_TAMIZAJE_PERS = "tamizaje_persona";
    public static final String FORM_NUEVA_ENCUESTA_DPBB = "encuesta_datosp_bb";
    public static final String FORM_NUEVA_ENCUESTA_CASA = "encuesta_casa";
	public static final String ROJO = "#db0000";
	public static final String WIZARD = "#ff0099cc";
	public static final String HINT_TEXT = "#66000000";

    public static final String YES = "Si";
    public static final String OTRO = "Otro";
    public static final String NO = "No";
    public static final String NA = "NA";
    public static final String OTRARELFAM = "Otra relación familiar";

    public static final String YESKEYSND = "1";
    public static final String OTROKEYSND = "999";
    public static final String NOKEYSND = "0";
    
    public static final String ACCION = "accion";
    public static final String SENDING = "enviando";
    public static final String RECEIVING = "recibiendo";
    public static final String REVIEWING = "revisando";
    public static final String ENTERING = "ingresando";
    public static final String DESDE_CASOS = "desde_form_casos";

    public static final int COD_EST_CHF = 1;
    public static final int COD_EST_SEROPREVALENCIA = 2;
    public static final int COD_EST_COHORTEDENGUE = 3;
    public static final int COD_EST_COHORTEINFLUENZA = 4;
    public static final int COD_EST_UO1 = 5;
    public static final int COD_EST_TCOVID = 6; //Transmisión Covid

    //VERSIONES CARTA DE CONSENTIMIENTO
    public static final String VERSION_CC_CHF = "3"; //COHORTE FAMILIA//Version 3 MA2022
    public static final String VERSION_CC_SA = "1"; //SEROPREVALENCIA
    public static final String VERSION_CC_CD = "15"; //COHORTE DENGUE //Version 13 MA2020//Version 14 MA2021//Version 15 MA2022
    public static final String VERSION_CC_CD_D = "1"; //COHORTE DENGUE Extensión edad 2018
    public static final String VERSION_CC_CI = "1"; //COHORTE INFLUENZA. Parte F. Pasa a ser version 1. MA2022
    public static final String VERSION_CC_CI_COVID = "1"; //COHORTE INFLUENZA. Cuando se agrega la parte D de Covid pasa a verson 1
    public static final String VERSION_CC_UO1 = "1"; //COHORTE UO1
    public static final String VERSION_CC_UO1_COVID = "1"; //COHORTE UO1 + consentimiento covid
    public static final String VERSION_CC_TCOVID = "1"; //Transmisión Covid
    public static final String VERSION_CC_CD_E = "1"; //COHORTE DENGUE PERMISO PARA OBTENER UNA MUESTRA DE SANGRE ADICIONAL

    public static final String PARTICIPANTE_SA = "participanteSA";

    public static final String CODIGO_PROPOSITO_MA = "1";
    public static final String CODIGO_PROPOSITO_TX = "3";
    public static final String CODIGO_PROPOSITO_UO1 = "4";
    public static final String CODIGO_PROPOSITO_VC_UO1 = "5"; //VACUNA UO1
    public static final String CODIGO_PROPOSITO_COVID_CP = "7";//Covid Flu y UO1
    public static final String CODIGO_PROPOSITO_T_COVID = "8"; //Seguimiento Transmisión Covid
    public static final String CODIGO_PROPOSITO_ADIC_COVID = "9"; //Muestra adicional CHF Covid19
    public static final String CODIGO_PROPOSITO_ADIC_DEN = "10"; //Muestra adicional Dengue Parte E
    //public static final String CODIGO_PROPOSITO_UO1_CHF = "6"; //Compartido Positivo UO1 y positivo familia
    public static final String CODIGO_PROPOSITO_POS_FLU = "11"; //FLU positivos para influenza CEIRS
    public static final String CODIGO_TIPO_SANGRE = "1";
    public static final String CODIGO_TIPO_RESP = "9";
    public static final String CODIGO_TIPO_HF = "2";
    public static final String CODIGO_TIPO_HN = "3";
    public static final String CODIGO_TIPO_LN = "4";
    public static final String CODIGO_TUBO_ROJO = "1";
    public static final String CODIGO_TUBO_BHC = "2";
    public static final String CODIGO_TUBO_PBMC = "3";
    public static final String CODIGO_MEDIO = "4";
    public static final String CODIGO_MEM = "5";
    public static final String CODIGO_TUBO_PBMC_EDTA = "6";
    public static final String CODIGO_TUBO_PBMC_CITRATO = "7";
    public static final String FEC_VISITA = "fecha_visita";

    //MUESTREO ANUAL
    //nombres de extras
    public static final String OBJECTO = "objeto";

    //Opciones de la actividad de seleccionar participante
    public static final String MENU_INFO = "menu_info";
    public static final String MENU_ZIKA = "menu_zika";
    public static final String MENU_UO1 = "menu_uo1";

    //Providers
    public static final String AUTHORITY = "org.odk.collect.android.provider.odk.forms";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/forms");
    public static final String AUTHORITY_I = "org.odk.collect.android.provider.odk.instances";
    public static final Uri CONTENT_URI_I = Uri.parse("content://" + AUTHORITY_I + "/instances");

    public static final String OK = "OK";

    public static final String NUEVO_INGRESO = "nuevo_ngreso";
    public static final String INGRESO_CHF = "nuevo_ngreso_chf";
    public static final String MX_SUPERFICIE = "mx_superficie";
    public static final String MX_SUPERFICIE_ASEN = "mx_superficie_asen";
    public static final String MX_SUPERFICIE_CON = "mx_superficie_con";
    public static final String MX_SUPERFICIE_1 = "1";
    public static final String MX_SUPERFICIE_2 = "2";

    public static final String NOM_EST_COHORTEDENGUE = "Dengue";
    public static final String NOM_EST_UO1 = "UO1";

    public static final String SUB_ESTUDIO_NA = "0";
    public static final String SUB_ESTUDIO_ARBOVIRUS = "1";
    public static final String SUB_ESTUDIO_COVID19 = "2";
    public static final String T_COVID19 = "Tcovid";
    public static final String PRIMER_SINTOMA = "primer_sintoma";
    public static final String PERIODO_CUEST_COVID19_1 = "Febrero2020";
    public static final String PERIODO_CUEST_COVID19_2 = "Octubre2020";
    public static final String PERIODO_CUEST_COVID19_3 = "Febrero2021";

    public static final int MINIMO_NUM_CELULAR = 30000000;
    public static final int MAXIMO_NUM_CELULAR = 89999999;
    public static final int MINIMO_NUM_CONVENCIONAL = 20000000;
    public static final int MAXIMO_NUM_CONVENCIONAL = 29999999;

    public static final String DESDE_FOTO = "desde_fotografia";

    public static final String REL_FAM_MISMO_PART = "8";

    //EDTA y Citrato
    public static final String CANDIDATO_EDTA_CITRATO = "C3";
    public static final String MX_EDTA_CITRATO = "3";

}

    