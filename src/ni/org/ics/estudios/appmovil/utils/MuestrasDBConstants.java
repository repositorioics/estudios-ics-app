package ni.org.ics.estudios.appmovil.utils;
/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class MuestrasDBConstants {

    //tabla Muestras
    public static final String MUESTRA_TABLE = "chf_muestras";
    //campos tabla Muestras
    public static final String codigo = "codigo";
    public static final String tomaMxSn = "tomaMxSn";
    public static final String codigoMx = "codigoMx";
    public static final String hora = "hora";
    public static final String horaFin = "horaFin";
    public static final String volumen = "volumen";
    public static final String observacion = "observacion";
    public static final String descOtraObservacion = "descOtraObservacion";
    public static final String numPinchazos = "numPinchazos";
    public static final String razonNoToma = "razonNoToma";
    public static final String descOtraRazonNoToma = "descOtraRazonNoToma";
    public static final String tubo = "tubo";
    public static final String tipoMuestra = "tipoMuestra";
    public static final String proposito = "proposito";
    public static final String participante = "participante";
    public static final String realizaPaxgene = "realizaPaxgene";
    public static final String horaInicioPax = "horaInicioPax";
    public static final String horaFinPax = "horaFinPax";

    //crear tabla Muestras
    public static final String CREATE_MUESTRA_TABLE = "create table if not exists "
            + MUESTRA_TABLE + " ("
            + codigo + " text not null, "
            + tomaMxSn + " text not null, "
            + codigoMx + " text, "
            + hora + " text null, "
            + horaFin + " text, "
            + volumen + " real null, "
            + observacion + " text, "
            + descOtraObservacion + " text, "
            + numPinchazos + " text, "
            + razonNoToma + " text, "
            + descOtraRazonNoToma + " text, "
            + tubo + " text, "
            + tipoMuestra + " text, "
            + proposito + " text, "
            + participante + " integer not null, "
            + realizaPaxgene + " text, "
            + horaInicioPax + " text, "
            + horaFinPax + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigo + "));";

    //Tabla Recepción de muestras
    public static final String RECEPCION_MUESTRA_TABLE = "recepcion_muestras";

    //Campos Recepción de muestra
    public static final String fechaRecepcion = "fechaRecepcion";
    public static final String paxgene = "paxgene";
    public static final String lugar = "lugar";

    //Crear tabla Recepción de muestra
    public static final String CREATE_RECEPCION_MUESTRA_TABLE = "create table if not exists "
            + RECEPCION_MUESTRA_TABLE + " ("
            + codigo + " text not null, "
            + codigoMx + " text not null, "
            + fechaRecepcion + " date not null, "
            + volumen + " real null, "
            + lugar + " text, "
            + observacion + " text, "
            + paxgene + " text, "
            + tubo + " text, "
            + tipoMuestra + " text, "
            + proposito + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigo +"));";

    //tabla Muestras de superficie
    public static final String MUESTRA_SUPERFICIE_TABLE = "chf_muestras_superficie";

    //campos tabla Muestras de superficie
    public static final String otraSuperficie = "otraSuperficie";
    public static final String casaChf = "casaChf";
    public static final String participanteChf = "participanteChf";
    public static final String visita = "visita";
    public static final String caso = "caso";

    //crear tabla Muestras de superficie
    public static final String CREATE_MUESTRA_SUPERFICIE_TABLE = "create table if not exists "
            + MUESTRA_SUPERFICIE_TABLE + " ("
            + codigo + " text not null, "
            + tipoMuestra + " text, "
            + otraSuperficie + " text, "
            + codigoMx + " text, "
            + casaChf + " text, "
            + participanteChf + " integer, "
            + visita + " text, "
            + caso + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigo + "));";

}
