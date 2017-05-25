package ni.org.ics.estudios.appmovil.helpers;

import java.util.Date;

import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cuarto;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class CuartoHelper {
	
	public static ContentValues crearCuartoContentValues(Cuarto cuarto){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.codigo, cuarto.getCodigo());
		cv.put(MainDBConstants.casa, cuarto.getCasa().getCodigoCHF());
		cv.put(MainDBConstants.codigoHabitacion, cuarto.getCodigoHabitacion());
        if (cuarto.getCantidadCamas() > 0) cv.put(MainDBConstants.cantidadCamas, cuarto.getCantidadCamas());        
		if (cuarto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, cuarto.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, cuarto.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(cuarto.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(cuarto.getEstado()));
		cv.put(MainDBConstants.deviceId, cuarto.getDeviceid());
		return cv; 
	}	
	
	public static Cuarto crearCuarto(Cursor cursor){
		
		Cuarto mCuarto = new Cuarto();
		mCuarto.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
		mCuarto.setCasa(null);
		mCuarto.setCodigoHabitacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoHabitacion)));
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.cantidadCamas))>0) mCuarto.setCantidadCamas(cursor.getInt(cursor.getColumnIndex(MainDBConstants.cantidadCamas)));
		if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mCuarto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
		mCuarto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
		mCuarto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mCuarto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mCuarto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
		return mCuarto;
	}
	
}
