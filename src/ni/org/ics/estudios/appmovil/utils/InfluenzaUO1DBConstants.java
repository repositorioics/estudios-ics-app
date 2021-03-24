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
    public static final String lugar = "lugar";
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
            + lugar + " text, "
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

    //tabla VisitaVacunaUO1
    public static final String UO1_VISITAS_VACUNAS_TABLE = "uo1_visitas_vacunas";

    //campos Tabla VisitaVacunaUO1
    public static final String codigoVisita = "codigoVisita";
    public static final String vacuna = "vacuna";
    public static final String tomaMxAntes = "tomaMxAntes";
    public static final String razonNoTomaMx = "razonNoTomaMx";
    public static final String segundaDosis = "segundaDosis";
    public static final String fechaSegundaDosis = "fechaSegundaDosis";
    public static final String fechaReprogramacion = "fechaReprogramacion";

    //create
    public static final String CREATE_UO1_VISITAS_VACUNAS_TABLE = "create table if not exists "
            + UO1_VISITAS_VACUNAS_TABLE + " ("
            + codigoVisita + " text not null, "
            + participante + " integer not null, "
            + fechaVisita + " date, "
            + visita + " text, "
            + visitaExitosa + " text, "
            + razonVisitaFallida + " text, "
            + otraRazon + " text, "
            + vacuna + " text, "
            + fechaVacuna + " date, "
            + tomaMxAntes + " text, "
            + razonNoTomaMx + " text, "
            + segundaDosis + " text, "
            + fechaSegundaDosis + " date, "
            + fechaReprogramacion + " date, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoVisita + "));";

    //tabla SintomasVisitaCasoUO1
    public static final String UO1_SINTOMAS_VISITA_CASO_TABLE = "uo1_sintomas_visita_caso";

    public static final String codigoSintoma = "codigoSintoma";
    public static final String fechaSintomas = "fechaSintomas";
    public static final String dia = "dia";
    public static final String consultaInicial = "consultaInicial";
    public static final String fiebre = "fiebre";
    public static final String fiebreIntensidad = "fiebreIntensidad";
    public static final String tos = "tos";
    public static final String tosIntensidad = "tosIntensidad";
    public static final String secrecionNasal = "secrecionNasal";
    public static final String secrecionNasalIntensidad = "secrecionNasalIntensidad";
    public static final String dolorGarganta = "dolorGarganta";
    public static final String dolorGargantaIntensidad = "dolorGargantaIntensidad";
    public static final String congestionNasal = "congestionNasal";
    public static final String dolorCabeza = "dolorCabeza";
    public static final String dolorCabezaIntensidad = "dolorCabezaIntensidad";
    public static final String faltaApetito = "faltaApetito";
    public static final String dolorMuscular = "dolorMuscular";
    public static final String dolorMuscularIntensidad = "dolorMuscularIntensidad";
    public static final String dolorArticular = "dolorArticular";
    public static final String dolorArticularIntensidad = "dolorArticularIntensidad";
    public static final String dolorOido = "dolorOido";
    public static final String respiracionRapida = "respiracionRapida";
    public static final String dificultadRespiratoria = "dificultadRespiratoria";
    public static final String faltaEscuelta = "faltaEscuelta";
    public static final String quedoCama = "quedoCama";

    //create
    public static final String CREATE_UO1_SINTOMAS_VISITA_CASO_TABLE = "create table if not exists "
            + UO1_SINTOMAS_VISITA_CASO_TABLE + " ("
            + codigoSintoma + " text not null, "
            + codigoCasoVisita + " text not null, "
            + fechaSintomas + " date, "
            + dia + " text, "
            + consultaInicial + " text, "
            + fiebre + " text, "
            + fiebreIntensidad + " text, "
            + tos + " text, "
            + tosIntensidad + " text, "
            + secrecionNasal + " text, "
            + secrecionNasalIntensidad + " text, "
            + dolorGarganta + " text, "
            + dolorGargantaIntensidad + " text, "
            + congestionNasal + " text, "
            + dolorCabeza + " text, "
            + dolorCabezaIntensidad + " text, "
            + faltaApetito + " text, "
            + dolorMuscular + " text, "
            + dolorMuscularIntensidad + " text, "
            + dolorArticular + " text, "
            + dolorArticularIntensidad + " text, "
            + dolorOido + " text, "
            + respiracionRapida + " text, "
            + dificultadRespiratoria + " text, "
            + faltaEscuelta + " text, "
            + quedoCama + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoSintoma + "));";


    /*****BULK INSERT****/
    public static final String INSERT_UO1_PARTICIPANTES_CASOS_TABLE = "insert into "
            + UO1_PARTICIPANTES_CASOS_TABLE + " ("
            + codigoCasoParticipante + ","
            + participante + ","
            + positivoPor + ","
            + fif + ","
            + fechaIngreso + ","
            + fechaDesactivacion + ","
            + activo + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values(?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_UO1_VISITAS_CASOS_TABLE = "insert into "
            + UO1_VISITAS_CASOS_TABLE + " ("
            + codigoCasoVisita + ","
            + participanteCasoUO1 + ","
            + fechaVisita + ","
            + visita + ","
            + lugar + ","
            + visitaExitosa + ","
            + razonVisitaFallida + ","
            + otraRazon + ","
            + positivoPor + ","
            + fif + ","
            + vacunaFlu3Semanas + ","
            + fechaVacuna + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_UO1_VISITAS_VACUNAS_TABLE = "insert into "
            + UO1_VISITAS_VACUNAS_TABLE + " ("
            + codigoVisita + ","
            + participante + ","
            + fechaVisita + ","
            + visita + ","
            + visitaExitosa + ","
            + razonVisitaFallida + ","
            + otraRazon + ","
            + vacuna + ","
            + fechaVacuna + ","
            + tomaMxAntes + ","
            + razonNoTomaMx + ","
            + segundaDosis + ","
            + fechaSegundaDosis + ","
            + fechaReprogramacion + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_UO1_SINTOMAS_VISITA_CASO_TABLE = "insert into "
            + UO1_SINTOMAS_VISITA_CASO_TABLE + " ("
            + codigoSintoma + ","
            + codigoCasoVisita + ","
            + fechaSintomas + ","
            + dia + ","
            + consultaInicial + ","
            + fiebre + ","
            + fiebreIntensidad + ","
            + tos + ","
            + tosIntensidad + ","
            + secrecionNasal + ","
            + secrecionNasalIntensidad + ","
            + dolorGarganta + ","
            + dolorGargantaIntensidad + ","
            + congestionNasal + ","
            + dolorCabeza + ","
            + dolorCabezaIntensidad + ","
            + faltaApetito + ","
            + dolorMuscular + ","
            + dolorMuscularIntensidad + ","
            + dolorArticular + ","
            + dolorArticularIntensidad + ","
            + dolorOido + ","
            + respiracionRapida + ","
            + dificultadRespiratoria + ","
            + faltaEscuelta + ","
            + quedoCama + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
}
