package ni.org.ics.estudios.appmovil.utils;

import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

public class Covid19DBConstants {
    //Tabla covid_participantes
    public static final String PARTICIPANTE_COVID_TABLE = "covid_participantes";

    // Campos Participantes cohorte familia
    public static final String participante= "participante";

    //Crear tabla participantes cohorte familia
    public static final String CREATE_PARTICIPANTE_COVID_TABLE = "create table if not exists "
            + PARTICIPANTE_COVID_TABLE + " ("
            + participante + " integer not null, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + participante + "));";

}
