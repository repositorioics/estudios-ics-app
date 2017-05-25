package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
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
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas))>0) objeto.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        objeto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo)));
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
        cv.put(MainDBConstants.codigoHabitacion, objeto.getCodigoHabitacion());
        if (objeto.getCantidadCamas() > 0) cv.put(MainDBConstants.cantidadCamas, objeto.getCantidadCamas());
        cv.put(MainDBConstants.tipo, objeto.getTipo());
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
        objeto.setCodigoHabitacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoHabitacion)));
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.cantidadCamas))>0) objeto.setCantidadCamas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.cantidadCamas)));
        objeto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo)));
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
        cv.put(MainDBConstants.conVentana, String.valueOf(objeto.getConVentana()));
        cv.put(MainDBConstants.tipo, objeto.getTipo());
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
        objeto.setConVentana(cursor.getString(cursor.getColumnIndex(MainDBConstants.conVentana)).charAt(0));
        objeto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo)));
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
        cv.put(MainDBConstants.casa, objeto.getCasa().getCodigoCHF());
        if (objeto.getAncho() != null) cv.put(MainDBConstants.ancho, objeto.getAncho());
        if (objeto.getLargo() != null) cv.put(MainDBConstants.largo, objeto.getLargo());
        if (objeto.getTotalM2() != null) cv.put(MainDBConstants.totalM2, objeto.getTotalM2());
        if (objeto.getNumVentanas() != null) cv.put(MainDBConstants.numVentanas, objeto.getNumVentanas());
        cv.put(MainDBConstants.abierta, String.valueOf(objeto.getAbierta()));
        cv.put(MainDBConstants.tipo, objeto.getTipo());
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
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) objeto.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) objeto.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) objeto.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas))>0) objeto.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        objeto.setAbierta(cursor.getString(cursor.getColumnIndex(MainDBConstants.abierta)).charAt(0));
        objeto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) objeto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        objeto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        objeto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        objeto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        objeto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return objeto;
    }

}
