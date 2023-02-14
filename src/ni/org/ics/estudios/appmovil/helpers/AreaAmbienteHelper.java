package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.*;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class AreaAmbienteHelper {

	public static ContentValues crearAreaAmbienteContentValues(AreaAmbiente objeto){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, objeto.getCodigo());
        cv.put(MainDBConstants.casa, objeto.getCasa().getCodigoCHF());
        if (objeto.getAncho() != null) cv.put(MainDBConstants.ancho, objeto.getAncho());
        if (objeto.getLargo() != null) cv.put(MainDBConstants.largo, objeto.getLargo());
        if (objeto.getTotalM2() != null) cv.put(MainDBConstants.totalM2, objeto.getTotalM2());
        if (objeto.getNumVentanas() != null) cv.put(MainDBConstants.numVentanas, objeto.getNumVentanas());
        cv.put(MainDBConstants.tipo, objeto.getTipo());
        cv.put(MainDBConstants.numeroCuarto, objeto.getNumeroCuarto());//MA2020
        if (objeto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, objeto.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, objeto.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(objeto.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(objeto.getEstado()));
        cv.put(MainDBConstants.deviceId, objeto.getDeviceid());
        return cv;
    }

	public static AreaAmbiente crearAreaAmbiente(Cursor cursor){
        AreaAmbiente objeto = new AreaAmbiente();
        objeto.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        objeto.setCasa(null);
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) objeto.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) objeto.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) objeto.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        //permitir 0
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas))>=0) objeto.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        objeto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo)));
        objeto.setNumeroCuarto(cursor.getString(cursor.getColumnIndex(MainDBConstants.numeroCuarto)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) objeto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        objeto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        objeto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        objeto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        objeto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return objeto;
    }
	
	public static ContentValues crearHabitacionContentValues(Habitacion objeto){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, objeto.getCodigo());
        cv.put(MainDBConstants.casa, objeto.getCasa().getCodigoCHF());
        if (objeto.getAncho() != null) cv.put(MainDBConstants.ancho, objeto.getAncho());
        if (objeto.getLargo() != null) cv.put(MainDBConstants.largo, objeto.getLargo());
        if (objeto.getTotalM2() != null) cv.put(MainDBConstants.totalM2, objeto.getTotalM2());
        if (objeto.getNumVentanas() != null) cv.put(MainDBConstants.numVentanas, objeto.getNumVentanas());
        cv.put(MainDBConstants.tipo, objeto.getTipo());
        cv.put(MainDBConstants.numeroCuarto, objeto.getNumeroCuarto());//MA2020
        if (objeto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, objeto.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, objeto.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(objeto.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(objeto.getEstado()));
        cv.put(MainDBConstants.deviceId, objeto.getDeviceid());
        return cv;
    }

	public static Habitacion crearHabitacion(Cursor cursor){
		Habitacion objeto = new Habitacion();
        objeto.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        objeto.setCasa(null);
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) objeto.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) objeto.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) objeto.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas))>0) objeto.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        objeto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo)));
        objeto.setNumeroCuarto(cursor.getString(cursor.getColumnIndex(MainDBConstants.numeroCuarto)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) objeto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        objeto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        objeto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        objeto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        objeto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return objeto;
    }
	
	public static ContentValues crearBanioContentValues(Banio objeto){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, objeto.getCodigo());
        cv.put(MainDBConstants.casa, objeto.getCasa().getCodigoCHF());
        if (objeto.getAncho() != null) cv.put(MainDBConstants.ancho, objeto.getAncho());
        if (objeto.getLargo() != null) cv.put(MainDBConstants.largo, objeto.getLargo());
        if (objeto.getTotalM2() != null) cv.put(MainDBConstants.totalM2, objeto.getTotalM2());
        if (objeto.getNumVentanas() != null) cv.put(MainDBConstants.numVentanas, objeto.getNumVentanas());
        cv.put(MainDBConstants.conVentana, objeto.getConVentana());
        cv.put(MainDBConstants.tipo, objeto.getTipo());
        cv.put(MainDBConstants.numeroCuarto, objeto.getNumeroCuarto());//MA2020
        if (objeto.getAreaAmbiente() != null) cv.put(MainDBConstants.areaAmbiente, objeto.getAreaAmbiente().getCodigo());
        if (objeto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, objeto.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, objeto.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(objeto.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(objeto.getEstado()));
        cv.put(MainDBConstants.deviceId, objeto.getDeviceid());
        return cv;
    }

	public static Banio crearBanio(Cursor cursor){
		Banio objeto = new Banio();
        objeto.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        objeto.setCasa(null);
        objeto.setAreaAmbiente(null);
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) objeto.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) objeto.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) objeto.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas))>0) objeto.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        objeto.setConVentana(cursor.getString(cursor.getColumnIndex(MainDBConstants.conVentana)));
        objeto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo)));
        objeto.setNumeroCuarto(cursor.getString(cursor.getColumnIndex(MainDBConstants.numeroCuarto)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) objeto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        objeto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        objeto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        objeto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        objeto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return objeto;
    }
	
	public static ContentValues crearVentanaContentValues(Ventana objeto){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, objeto.getCodigo());
        if (objeto.getCasa() != null) cv.put(MainDBConstants.casa, objeto.getCasa().getCodigoCHF());
        if (objeto.getAreaAmbiente() != null) cv.put(MainDBConstants.areaAmbiente, objeto.getAreaAmbiente().getCodigo());
        if (objeto.getAncho() != null) cv.put(MainDBConstants.ancho, objeto.getAncho());
        if (objeto.getLargo() != null) cv.put(MainDBConstants.largo, objeto.getLargo());
        if (objeto.getTotalM2() != null) cv.put(MainDBConstants.totalM2, objeto.getTotalM2());
        if (objeto.getNumVentanas() != null) cv.put(MainDBConstants.numVentanas, objeto.getNumVentanas());
        cv.put(MainDBConstants.abierta, objeto.getAbierta());
        cv.put(MainDBConstants.tipo, objeto.getTipo());
        cv.put(MainDBConstants.numeroCuarto, objeto.getNumeroCuarto());//MA2020
        if (objeto.getAreaBanio() != null) cv.put(MainDBConstants.areaBanio, objeto.getAreaBanio().getCodigo());
        if (objeto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, objeto.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, objeto.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(objeto.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(objeto.getEstado()));
        cv.put(MainDBConstants.deviceId, objeto.getDeviceid());
        return cv;
    }

	public static Ventana crearVentana(Cursor cursor){
		Ventana objeto = new Ventana();
        objeto.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        objeto.setCasa(null);
        objeto.setAreaAmbiente(null);
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) objeto.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) objeto.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) objeto.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas))>0) objeto.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        objeto.setAbierta(cursor.getString(cursor.getColumnIndex(MainDBConstants.abierta)));
        objeto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo)));
        objeto.setNumeroCuarto(cursor.getString(cursor.getColumnIndex(MainDBConstants.numeroCuarto)));
        objeto.setAreaBanio(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) objeto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        objeto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        objeto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        objeto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        objeto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return objeto;
    }
	

	public static ContentValues crearCocinaContentValues(Cocina objeto){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, objeto.getCodigo());
        cv.put(MainDBConstants.casa, objeto.getCasa().getCodigoCHF());
        if (objeto.getAncho() != null) cv.put(MainDBConstants.ancho, objeto.getAncho());
        if (objeto.getLargo() != null) cv.put(MainDBConstants.largo, objeto.getLargo());
        if (objeto.getTotalM2() != null) cv.put(MainDBConstants.totalM2, objeto.getTotalM2());
        if (objeto.getNumVentanas() != null) cv.put(MainDBConstants.numVentanas, objeto.getNumVentanas());
        cv.put(MainDBConstants.tipo, objeto.getTipo());
        cv.put(MainDBConstants.numeroCuarto, objeto.getNumeroCuarto());//MA2020
        if (objeto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, objeto.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, objeto.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(objeto.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(objeto.getEstado()));
        cv.put(MainDBConstants.deviceId, objeto.getDeviceid());
        return cv;
    }

	public static Cocina crearCocina(Cursor cursor){
		Cocina objeto = new Cocina();
        objeto.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        objeto.setCasa(null);
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) objeto.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) objeto.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) objeto.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas))>0) objeto.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        objeto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo)));
        objeto.setNumeroCuarto(cursor.getString(cursor.getColumnIndex(MainDBConstants.numeroCuarto)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) objeto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        objeto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        objeto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        objeto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        objeto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return objeto;
    }
	
	public static ContentValues crearComedorContentValues(Comedor objeto){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, objeto.getCodigo());
        cv.put(MainDBConstants.casa, objeto.getCasa().getCodigoCHF());
        if (objeto.getAncho() != null) cv.put(MainDBConstants.ancho, objeto.getAncho());
        if (objeto.getLargo() != null) cv.put(MainDBConstants.largo, objeto.getLargo());
        if (objeto.getTotalM2() != null) cv.put(MainDBConstants.totalM2, objeto.getTotalM2());
        if (objeto.getNumVentanas() != null) cv.put(MainDBConstants.numVentanas, objeto.getNumVentanas());
        cv.put(MainDBConstants.tipo, objeto.getTipo());
        cv.put(MainDBConstants.numeroCuarto, objeto.getNumeroCuarto());//MA2020
        if (objeto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, objeto.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, objeto.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(objeto.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(objeto.getEstado()));
        cv.put(MainDBConstants.deviceId, objeto.getDeviceid());
        return cv;
    }

	public static Comedor crearComedor(Cursor cursor){
		Comedor objeto = new Comedor();
        objeto.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        objeto.setCasa(null);
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) objeto.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) objeto.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) objeto.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas))>0) objeto.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        objeto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo)));
        objeto.setNumeroCuarto(cursor.getString(cursor.getColumnIndex(MainDBConstants.numeroCuarto)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) objeto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        objeto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        objeto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        objeto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        objeto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return objeto;
    }
	
	
	public static ContentValues crearSalaContentValues(Sala objeto){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, objeto.getCodigo());
        cv.put(MainDBConstants.casa, objeto.getCasa().getCodigoCHF());
        if (objeto.getAncho() != null) cv.put(MainDBConstants.ancho, objeto.getAncho());
        if (objeto.getLargo() != null) cv.put(MainDBConstants.largo, objeto.getLargo());
        if (objeto.getTotalM2() != null) cv.put(MainDBConstants.totalM2, objeto.getTotalM2());
        if (objeto.getNumVentanas() != null) cv.put(MainDBConstants.numVentanas, objeto.getNumVentanas());
        cv.put(MainDBConstants.tipo, objeto.getTipo());
        cv.put(MainDBConstants.numeroCuarto, objeto.getNumeroCuarto());//MA2020
        if (objeto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, objeto.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, objeto.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(objeto.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(objeto.getEstado()));
        cv.put(MainDBConstants.deviceId, objeto.getDeviceid());
        return cv;
    }

	public static Sala crearSala(Cursor cursor){
		Sala objeto = new Sala();
        objeto.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        objeto.setCasa(null);
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) objeto.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) objeto.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) objeto.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas))>0) objeto.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        objeto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo)));
        objeto.setNumeroCuarto(cursor.getString(cursor.getColumnIndex(MainDBConstants.numeroCuarto)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) objeto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        objeto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        objeto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        objeto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        objeto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return objeto;
    }

    public static void fillAreaAmbienteStatement(SQLiteStatement stat, AreaAmbiente objeto){
        stat.bindString(1, objeto.getCodigo());
        bindString(stat,2, objeto.getCasa().getCodigoCHF());
        bindDouble(stat,3, objeto.getLargo());
        bindDouble(stat,4, objeto.getAncho());
        bindDouble(stat,5, objeto.getTotalM2());
        bindInteger(stat,6, objeto.getNumVentanas());
        bindString(stat,7, objeto.getTipo());
        //con ventana 8
        //cantidad camas 9
        //areaambiente 10
        //abierta 11
        //codigohabitacion 12
        bindString(stat,13, objeto.getNumeroCuarto());//MA2020
        //codigobanio 14
        bindDate(stat,15, objeto.getRecordDate());
        bindString(stat,16, objeto.getRecordUser());
        stat.bindString(17, String.valueOf(objeto.getPasive()));
        bindString(stat,18, objeto.getDeviceid());
        stat.bindString(19, String.valueOf(objeto.getEstado()));
    }

    public static void fillBanioStatement(SQLiteStatement stat, Banio objeto){
        stat.bindString(1, objeto.getCodigo());
        bindString(stat,2, objeto.getCasa().getCodigoCHF());
        bindDouble(stat,3, objeto.getLargo());
        bindDouble(stat,4, objeto.getAncho());
        bindDouble(stat,5, objeto.getTotalM2());
        bindInteger(stat,6, objeto.getNumVentanas());
        bindString(stat,7, objeto.getTipo());
        bindString(stat,8, objeto.getConVentana());
        //cantidad camas 9
        if (objeto.getAreaAmbiente() != null) bindString(stat,10, objeto.getAreaAmbiente().getCodigo()); else stat.bindNull(10);
        //abierta 11
        //codigohabitacion 12
        bindString(stat,13, objeto.getNumeroCuarto());//MA2020
        //areaBanio 14
        bindDate(stat,15, objeto.getRecordDate());
        bindString(stat,16, objeto.getRecordUser());
        stat.bindString(17, String.valueOf(objeto.getPasive()));
        bindString(stat,18, objeto.getDeviceid());
        stat.bindString(19, String.valueOf(objeto.getEstado()));
    }

    public static void fillVentanaStatement(SQLiteStatement stat, Ventana objeto){
        stat.bindString(1, objeto.getCodigo());
        bindString(stat,2, objeto.getCasa().getCodigoCHF());
        bindDouble(stat,3, objeto.getLargo());
        bindDouble(stat,4, objeto.getAncho());
        bindDouble(stat,5, objeto.getTotalM2());
        bindInteger(stat,6, objeto.getNumVentanas());
        bindString(stat,7, objeto.getTipo());
        //con ventana 8
        //cantidad camas 9
        if (objeto.getAreaAmbiente() != null) bindString(stat,10, objeto.getAreaAmbiente().getCodigo()); else stat.bindNull(10);
        bindString(stat,11, objeto.getAbierta());
        //codigohabitacion 12
        bindString(stat,13, objeto.getNumeroCuarto());//
        if (objeto.getAreaBanio() != null) bindString(stat,14, objeto.getAreaBanio().getCodigo()); else stat.bindNull(14);
        bindDate(stat,15, objeto.getRecordDate());
        bindString(stat,16, objeto.getRecordUser());
        stat.bindString(17, String.valueOf(objeto.getPasive()));
        bindString(stat,18, objeto.getDeviceid());
        stat.bindString(19, String.valueOf(objeto.getEstado()));

    }

    public static void bindString(SQLiteStatement stat, int index, String value){
        if (value == null) {
            stat.bindNull(index);
        } else {
            stat.bindString(index, value);
        }
    }

    public static void bindDate(SQLiteStatement stat, int index, Date value){
        if (value == null) {
            stat.bindNull(index);
        } else {
            stat.bindLong(index, value.getTime());
        }
    }

    public static void bindDouble(SQLiteStatement stat, int index, Double value){
        if (value == null) {
            stat.bindNull(index);
        } else {
            stat.bindDouble(index, value);
        }
    }

    public static void bindInteger(SQLiteStatement stat, int index, Integer value){
        if (value == null) {
            stat.bindNull(index);
        } else {
            stat.bindLong(index, value);
        }
    }

}
