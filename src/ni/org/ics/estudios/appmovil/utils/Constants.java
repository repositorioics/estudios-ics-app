package ni.org.ics.estudios.appmovil.utils;


import android.net.Uri;

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

    public static final String YESKEYSND = "1";
    public static final String OTROKEYSND = "999";
    public static final String NOKEYSND = "0";
    
    public static final String ACCION = "accion";
    public static final String SENDING = "enviando";
    public static final String RECEIVING = "recibiendo";
    public static final String REVIEWING = "revisando";
    public static final String ENTERING = "ingresando";
    public static final String DESDE_CASOS = "desde_form_casos";

    public static final int COD_EST_SEROPREVALENCIA = 2;
    public static final int COD_EST_COHORTEDENGUE = 3;
    public static final int COD_EST_COHORTEINFLUENZA = 4;

    //VERSIONES CARTA DE CONSENTIMIENTO
    public static final String VERSION_CC_CHF = "1"; //COHORTE FAMILIA
    public static final String VERSION_CC_SA = "1"; //SEROPREVALENCIA
    public static final String VERSION_CC_CD = "11"; //COHORTE DENGUE
    public static final String VERSION_CC_CI = "7"; //COHORTE INFLUENZA

    public static final String PARTICIPANTE_SA = "participanteSA";

    public static final String CODIGO_PROPOSITO_MA = "1";
    public static final String CODIGO_PROPOSITO_TX = "3";
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
    public static final String FEC_VISITA = "fecha_visita";

    //MUESTREO ANUAL
    //nombres de extras
    public static final String OBJECTO = "objeto";

    //Opciones de la actividad de seleccionar participante
    public static final String MENU_INFO = "menu_info";
    public static final String MENU_ZIKA = "menu_zika";

    //Providers
    public static final String AUTHORITY = "org.odk.collect.android.provider.odk.forms";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/forms");
    public static final String AUTHORITY_I = "org.odk.collect.android.provider.odk.instances";
    public static final Uri CONTENT_URI_I = Uri.parse("content://" + AUTHORITY_I + "/instances");

    public static final String OK = "OK";

    public static final String NUEVO_INGRESO = "nuevo_ngreso";
}

    