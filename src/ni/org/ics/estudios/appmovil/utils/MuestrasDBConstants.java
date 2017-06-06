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

}
