package ni.org.ics.estudios.appmovil.utils;

public class InfluenzaUO1DBConstants {
    //tabla ParticipanteCasoUO1
    public static final String UO1_PARTICIPANTES_CASOS_TABLE = "uo1_participantes_casos";
    //campos tabla ParticipanteCasoUO1
    public static final String codigoCasoParticipante = "codigoCasoParticipante";
    public static final String participante = "participante";
    public static final String activo = "activo";
    public static final String positivoPor = "positivoPor";
    public static final String fif = "fif";
    public static final String fechaIngreso = "fechaIngreso";
    public static final String fechaDesactivacion = "fechaDesactivacion";

    //crear tabla ParticipanteCasoUO1
    public static final String CREATE_UO1_PARTICIPANTES_CASOS_TABLE = "create table if not exists "
            + UO1_PARTICIPANTES_CASOS_TABLE + " ("
            + codigoCasoParticipante + " text not null, "
            + participante + " integer not null, "
            + positivoPor + " text, "
            + fif + " date, "
            + fechaIngreso + " date, "
            + fechaDesactivacion + " date, "
            + activo + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCasoParticipante + "));";


    //tabla VisitaCasoUO1
    public static final String UO1_VISITAS_CASOS_TABLE = "uo1_visitas_casos";

    //campos tabla VisitaCasoUO1
    public static final String codigoCasoVisita = "codigoCasoVisita";
    public static final String participanteCasoUO1 = "participanteCasoUO1";
    public static final String fechaVisita = "fechaVisita";
    public static final String visita = "visita";
    public static final String visitaExitosa = "visitaExitosa";
    public static final String razonVisitaFallida = "razonVisitaFallida";
    public static final String otraRazon = "otraRazon";
    public static final String vacunaFlu3Semanas = "vacunaFlu3Semanas";
    public static final String fechaVacuna = "fechaVacuna";

    //crear tabla VisitaCasoUO1
    public static final String CREATE_UO1_VISITAS_CASOS_TABLE = "create table if not exists "
            + UO1_VISITAS_CASOS_TABLE + " ("
            + codigoCasoVisita + " text not null, "
            + participanteCasoUO1 + " text not null, "
            + fechaVisita + " date, "
            + visita + " text, "
            + visitaExitosa + " text, "
            + razonVisitaFallida + " text, "
            + otraRazon + " text, "
            + positivoPor + " text, "
            + fif + " date, "
            + vacunaFlu3Semanas + " text, "
            + fechaVacuna + " date, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCasoVisita + "));";

}
