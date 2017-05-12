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

	public static ContentValues crearBanioContentValues(Banio areaAmbiente){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, areaAmbiente.getCodigo());
        cv.put(MainDBConstants.ancho, areaAmbiente.getAncho());
        cv.put(MainDBConstants.largo, areaAmbiente.getLargo());
        cv.put(MainDBConstants.totalM2, areaAmbiente.getTotalM2());
        cv.put(MainDBConstants.casa, areaAmbiente.getCasa().getCodigoCHF());
        cv.put(MainDBConstants.habitacion, areaAmbiente.getHabitacion().getCodigo());
        cv.put(MainDBConstants.conVentana, String.valueOf(areaAmbiente.getConVentana()));
        if (areaAmbiente.getRecordDate() != null) cv.put(MainDBConstants.recordDate, areaAmbiente.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, areaAmbiente.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(areaAmbiente.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(areaAmbiente.getEstado()));
        cv.put(MainDBConstants.deviceId, areaAmbiente.getDeviceid());

        return cv;
    }

	public static Banio crearBanio(Cursor cursor){
        Banio mAreaAmbiente = new Banio();
        mAreaAmbiente.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) mAreaAmbiente.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) mAreaAmbiente.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) mAreaAmbiente.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        mAreaAmbiente.setCasa(null);
        mAreaAmbiente.setConVentana(cursor.getString(cursor.getColumnIndex(MainDBConstants.conVentana)).charAt(0));
        mAreaAmbiente.setHabitacion(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mAreaAmbiente.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mAreaAmbiente.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mAreaAmbiente.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mAreaAmbiente.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mAreaAmbiente.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mAreaAmbiente;
    }

	public static ContentValues crearCocinaContentValues(Cocina areaAmbiente){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, areaAmbiente.getCodigo());
        cv.put(MainDBConstants.ancho, areaAmbiente.getAncho());
        cv.put(MainDBConstants.largo, areaAmbiente.getLargo());
        cv.put(MainDBConstants.totalM2, areaAmbiente.getTotalM2());
        cv.put(MainDBConstants.casa, areaAmbiente.getCasa().getCodigoCHF());
        cv.put(MainDBConstants.numVentanas, areaAmbiente.getNumVentanas());
        if (areaAmbiente.getRecordDate() != null) cv.put(MainDBConstants.recordDate, areaAmbiente.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, areaAmbiente.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(areaAmbiente.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(areaAmbiente.getEstado()));
        cv.put(MainDBConstants.deviceId, areaAmbiente.getDeviceid());

        return cv;
    }

	public static Cocina crearCocina(Cursor cursor){
        Cocina mAreaAmbiente = new Cocina();
        mAreaAmbiente.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)) > 0) mAreaAmbiente.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) mAreaAmbiente.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) mAreaAmbiente.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        mAreaAmbiente.setCasa(null);
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)) > 0) mAreaAmbiente.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mAreaAmbiente.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mAreaAmbiente.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mAreaAmbiente.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mAreaAmbiente.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mAreaAmbiente.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mAreaAmbiente;
    }

	public static ContentValues crearComedorContentValues(Comedor areaAmbiente){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, areaAmbiente.getCodigo());
        cv.put(MainDBConstants.ancho, areaAmbiente.getAncho());
        cv.put(MainDBConstants.largo, areaAmbiente.getLargo());
        cv.put(MainDBConstants.totalM2, areaAmbiente.getTotalM2());
        cv.put(MainDBConstants.casa, areaAmbiente.getCasa().getCodigoCHF());
        cv.put(MainDBConstants.numVentanas, areaAmbiente.getNumVentanas());
        if (areaAmbiente.getRecordDate() != null) cv.put(MainDBConstants.recordDate, areaAmbiente.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, areaAmbiente.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(areaAmbiente.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(areaAmbiente.getEstado()));
        cv.put(MainDBConstants.deviceId, areaAmbiente.getDeviceid());

        return cv;
    }

	public static Comedor crearComedor(Cursor cursor){
        Comedor mAreaAmbiente = new Comedor();
        mAreaAmbiente.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) mAreaAmbiente.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) mAreaAmbiente.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) mAreaAmbiente.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        mAreaAmbiente.setCasa(null);
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)) > 0) mAreaAmbiente.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mAreaAmbiente.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mAreaAmbiente.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mAreaAmbiente.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mAreaAmbiente.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mAreaAmbiente.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mAreaAmbiente;
    }

	public static ContentValues crearHabitacionContentValues(Habitacion areaAmbiente){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, areaAmbiente.getCodigo());
        cv.put(MainDBConstants.ancho, areaAmbiente.getAncho());
        cv.put(MainDBConstants.largo, areaAmbiente.getLargo());
        cv.put(MainDBConstants.totalM2, areaAmbiente.getTotalM2());
        cv.put(MainDBConstants.casa, areaAmbiente.getCasa().getCodigoCHF());
        cv.put(MainDBConstants.numVentanas, areaAmbiente.getNumVentanas());
        if (areaAmbiente.getRecordDate() != null) cv.put(MainDBConstants.recordDate, areaAmbiente.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, areaAmbiente.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(areaAmbiente.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(areaAmbiente.getEstado()));
        cv.put(MainDBConstants.deviceId, areaAmbiente.getDeviceid());

        return cv;
    }

	public static Habitacion crearHabitacion(Cursor cursor){
        Habitacion mAreaAmbiente = new Habitacion();
        mAreaAmbiente.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) mAreaAmbiente.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) mAreaAmbiente.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) mAreaAmbiente.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        mAreaAmbiente.setCasa(null);
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)) > 0) mAreaAmbiente.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mAreaAmbiente.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mAreaAmbiente.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mAreaAmbiente.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mAreaAmbiente.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mAreaAmbiente.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mAreaAmbiente;
    }

	public static ContentValues crearSalaContentValues(Sala areaAmbiente){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, areaAmbiente.getCodigo());
        cv.put(MainDBConstants.ancho, areaAmbiente.getAncho());
        cv.put(MainDBConstants.largo, areaAmbiente.getLargo());
        cv.put(MainDBConstants.totalM2, areaAmbiente.getTotalM2());
        cv.put(MainDBConstants.casa, areaAmbiente.getCasa().getCodigoCHF());
        cv.put(MainDBConstants.numVentanas, areaAmbiente.getNumVentanas());
        if (areaAmbiente.getRecordDate() != null) cv.put(MainDBConstants.recordDate, areaAmbiente.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, areaAmbiente.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(areaAmbiente.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(areaAmbiente.getEstado()));
        cv.put(MainDBConstants.deviceId, areaAmbiente.getDeviceid());

        return cv;
    }

	public static Sala crearSala(Cursor cursor){
        Sala mAreaAmbiente = new Sala();
        mAreaAmbiente.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) mAreaAmbiente.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)) > 0) mAreaAmbiente.setLargo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.largo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) mAreaAmbiente.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        mAreaAmbiente.setCasa(null);
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)) > 0) mAreaAmbiente.setNumVentanas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVentanas)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mAreaAmbiente.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mAreaAmbiente.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mAreaAmbiente.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mAreaAmbiente.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mAreaAmbiente.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mAreaAmbiente;
    }

	public static ContentValues crearVentanaContentValues(Ventana areaAmbiente){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, areaAmbiente.getCodigo());
        cv.put(MainDBConstants.ancho, areaAmbiente.getAncho());
        cv.put(MainDBConstants.alto, areaAmbiente.getLargo());
        cv.put(MainDBConstants.totalM2, areaAmbiente.getTotalM2());
        cv.put(MainDBConstants.casa, areaAmbiente.getCasa().getCodigoCHF());
        cv.put(MainDBConstants.abierta, String.valueOf(areaAmbiente.getAbierta()));
        cv.put(MainDBConstants.areaAmbiente, areaAmbiente.getAreaAmbiente().getCodigo());
        if (areaAmbiente.getRecordDate() != null) cv.put(MainDBConstants.recordDate, areaAmbiente.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, areaAmbiente.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(areaAmbiente.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(areaAmbiente.getEstado()));
        cv.put(MainDBConstants.deviceId, areaAmbiente.getDeviceid());

        return cv;
    }

	public static Ventana crearVentana(Cursor cursor){
        Ventana mAreaAmbiente = new Ventana();
        mAreaAmbiente.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho))>0) mAreaAmbiente.setAncho(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.ancho)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.alto)) > 0) mAreaAmbiente.setAlto(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.alto)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2))>0) mAreaAmbiente.setTotalM2(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.totalM2)));
        mAreaAmbiente.setCasa(null);
        mAreaAmbiente.setAbierta(cursor.getString(cursor.getColumnIndex(MainDBConstants.abierta)).charAt(0));
        mAreaAmbiente.setAreaAmbiente(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mAreaAmbiente.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mAreaAmbiente.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mAreaAmbiente.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mAreaAmbiente.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mAreaAmbiente.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mAreaAmbiente;
    }

}
