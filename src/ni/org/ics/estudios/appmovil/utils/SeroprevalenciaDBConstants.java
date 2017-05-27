package ni.org.ics.estudios.appmovil.utils;

/**
 * Created by Miguel Salinas on 5/25/2017.
 * V1.0
 */
public class SeroprevalenciaDBConstants {

    //TABla ParticipanteSeroprevalencia
    public static final String PARTICIPANTESA_TABLE = "sa_participante_seroprevalencia";

    //CAMPOS  tabla ParticipanteSeroprevalencia
    public static final String participanteSA = "participanteSA";
    public static final String participante = "participante";
    public static final String casaCHF = "casaCHF";

    //crear tabla ParticipanteSeroprevalencia
    public static final String CREATE_PARTICIPANTESA_TABLE = "create table if not exists "
            + PARTICIPANTESA_TABLE + " ("
            + participanteSA + " text not null, "
            + participante + " integer not null, "
            + casaCHF + " text not null, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + participante + "));";

    //Tabla EncuestaCasaSA
    public static final String ENCUESTA_CASASA_TABLE = "sa_encuestas_casa";

    //Campos EncuestaCasaSA
    public static final  String sedazoPuertasVentanas="sedazoPuertasVentanas";
    public static final  String compraProdEvitarZancudos="compraProdEvitarZancudos";
    public static final  String tienePatioJardin="tienePatioJardin";
    public static final  String utilizaAbate="utilizaAbate";
    public static final  String fumiga="fumiga";
    public static final  String cadaCuantoFumiga="cadaCuantoFumiga";

    public static final String miembroFamConZikaSn="miembroFamConZikaSn";
    public static final String cantMiembrosZika= "cantMiembrosZika";
    public static final String fechaDxZika= "fechaDxZika";
    public static final String relacionFamZika= "relacionFamZika";
    public static final String otraRelacionFamZika= "otraRelacionFamZika";
    public static final String miembroFamConDengueSn="miembroFamConDengueSn";
    public static final String cantMiembrosDengue= "cantMiembrosDengue";
    public static final String fechaDxDengue= "fechaDxDengue";
    public static final String relacionFamDengue= "relacionFamDengue";
    public static final String otraRelacionFamDengue= "otraRelacionFamDengue";
    public static final String miembroFamConChikSn="miembroFamConChikSn";
    public static final String cantMiembrosChik= "cantMiembrosChik";
    public static final String fechaDxChik= "fechaDxChik";
    public static final String relacionFamChik= "relacionFamChik";
    public static final String otraRelacionFamChik= "otraRelacionFamChik";

    //Crear tabla EncuestaCasaSA
    public static final String CREATE_ENCUESTA_CASASA_TABLE = "create table if not exists "
            + ENCUESTA_CASASA_TABLE + " ("
            + casaCHF + " text not null, "
            + sedazoPuertasVentanas + " text, "
            + compraProdEvitarZancudos + " text, "
            + tienePatioJardin + " text, "
            + utilizaAbate + " text, "
            + fumiga + " text, "
            + cadaCuantoFumiga + " text, "
            + miembroFamConZikaSn + " text, "
            + cantMiembrosZika + " text, "
            + fechaDxZika + " text, "
            + relacionFamZika + " text, "
            + otraRelacionFamZika + " text, "
            + miembroFamConDengueSn + " text, "
            + cantMiembrosDengue + " text, "
            + fechaDxDengue + " text, "
            + relacionFamDengue + " text, "
            + otraRelacionFamDengue + " text, "
            + miembroFamConChikSn + " text, "
            + cantMiembrosChik + " text, "
            + fechaDxChik + " text, "
            + relacionFamChik + " text, "
            + otraRelacionFamChik + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + casaCHF + "));";

    //Tabla EncuestaParticipanteSA
    public static final String ENCUESTA_PARTICIPANTESA_TABLE = "sa_encuestas_participante";

    //Campos tabla EncuestaParticipanteSA
    public static final String escuchadoZikaSn= "escuchadoZikaSn";
    public static final String queEsSika= "queEsSika";
    public static final String otroQueEsSika= "otroQueEsSika";
    public static final String transmiteZika= "transmiteZika";
    public static final String otroTransmiteZika= "otroTransmiteZika";
    public static final String sintomas= "sintomas";
    public static final String tenidoZikaSn= "tenidoZikaSn";
    public static final String conoceFechaZika= "conoceFechaZika";
    public static final String fechaZika= "fechaZika";
    public static final String sintomasZika= "sintomasZika";
    public static final String zikaConfirmadoMedico= "zikaConfirmadoMedico";
    public static final String tenidoDengueSn= "tenidoDengueSn";
    public static final String conoceFechaDengue= "conoceFechaDengue";
    public static final String fechaDengue= "fechaDengue";
    public static final String dengueConfirmadoMedico= "dengueConfirmadoMedico";
    public static final String tenidoChikSn= "tenidoChikSn";
    public static final String conoceFechaChik= "conoceFechaChik";
    public static final String fechaChik= "fechaChik";
    public static final String chikConfirmadoMedico= "chikConfirmadoMedico";
    public static final String vacunaFiebreAmarillaSn= "vacunaFiebreAmarillaSn";
    public static final String conoceFechaVacFiebreAmar= "conoceFechaVacFiebreAmar";
    public static final String fechaVacunaFiebreAmar= "fechaVacunaFiebreAmar";
    public static final String transfusionSangreSn= "transfusionSangreSn";
    public static final String conoceFechaTransfusion= "conoceFechaTransfusion";
    public static final String fechaTransfusionSangre= "fechaTransfusionSangre";
    public static final String usaRepelentes= "usaRepelentes";
    public static final  String conoceLarvas="conoceLarvas";
    public static final  String lugaresLarvas="lugaresLarvas";
    public static final  String otrosLugaresLarvas="otrosLugaresLarvas";
    public static final String tenidoHijos= "tenidoHijos";
    public static final String usaPlanificacionFam= "usaPlanificacionFam";
    public static final String usaCondon= "usaCondon";
    public static final String usaOtroMetodo= "usaOtroMetodo";

    //Crear tabla EncuestaParticipanteSA
    public static final String CREATE_ENCUESTA_PARTICIPANTESA_TABLE = "create table if not exists "
            + ENCUESTA_PARTICIPANTESA_TABLE + " ("
            + participanteSA + " text not null, "
            + escuchadoZikaSn + " text, "
            + queEsSika + " text, "
            + otroQueEsSika + " text, "
            + transmiteZika + " text, "
            + otroTransmiteZika + " text, "
            + sintomas + " text, "
            + tenidoZikaSn + " text, "
            + conoceFechaZika + " text, "
            + fechaZika + " date, "
            + sintomasZika + " text, "
            + zikaConfirmadoMedico + " text, "
            + tenidoDengueSn + " text, "
            + conoceFechaDengue + " text, "
            + fechaDengue + " date, "
            + dengueConfirmadoMedico + " text, "
            + tenidoChikSn + " text, "
            + conoceFechaChik + " text, "
            + fechaChik + " date, "
            + chikConfirmadoMedico + " text, "
            + vacunaFiebreAmarillaSn + " text, "
            + conoceFechaVacFiebreAmar + " text, "
            + fechaVacunaFiebreAmar + " date, "
            + transfusionSangreSn + " text, "
            + conoceFechaTransfusion + " text, "
            + fechaTransfusionSangre + " date, "
            + usaRepelentes + " text, "
            + conoceLarvas + " text, "
            + lugaresLarvas + " text, "
            + otrosLugaresLarvas + " text, "
            + tenidoHijos + " text, "
            + usaPlanificacionFam + " text, "
            + usaCondon + " text, "
            + usaOtroMetodo + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + participanteSA + "));";

}
