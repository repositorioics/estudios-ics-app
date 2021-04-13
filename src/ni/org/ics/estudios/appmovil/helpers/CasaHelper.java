package ni.org.ics.estudios.appmovil.helpers;

import java.util.Date;

import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class CasaHelper {
	
	public static ContentValues crearCasaContentValues(Casa casa){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.codigo, casa.getCodigo());
		cv.put(MainDBConstants.barrio, casa.getBarrio().getCodigo());
		cv.put(MainDBConstants.direccion, casa.getDireccion());
		cv.put(MainDBConstants.manzana, casa.getManzana());
		if (casa.getLatitud() != null) cv.put(MainDBConstants.latitud, casa.getLatitud());
		if (casa.getLongitud() != null) cv.put(MainDBConstants.longitud, casa.getLongitud());
		cv.put(MainDBConstants.nombre1JefeFamilia, casa.getNombre1JefeFamilia());
		cv.put(MainDBConstants.nombre2JefeFamilia, casa.getNombre2JefeFamilia());
		cv.put(MainDBConstants.apellido1JefeFamilia, casa.getApellido1JefeFamilia());
		cv.put(MainDBConstants.apellido2JefeFamilia, casa.getApellido2JefeFamilia());
		if (casa.getRecordDate() != null) cv.put(MainDBConstants.recordDate, casa.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, casa.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(casa.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(casa.getEstado()));
		cv.put(MainDBConstants.deviceId, casa.getDeviceid());
		return cv; 
	}	
	
	public static Casa crearCasa(Cursor cursorCasa){
		
		Casa mCasa = new Casa();
		mCasa.setCodigo(cursorCasa.getInt(cursorCasa.getColumnIndex(MainDBConstants.codigo)));
		mCasa.setBarrio(null);
		mCasa.setDireccion(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.direccion)));
		mCasa.setManzana(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.manzana)));
		if (cursorCasa.getDouble(cursorCasa.getColumnIndex(MainDBConstants.latitud))!= 0) mCasa.setLatitud(cursorCasa.getDouble(cursorCasa.getColumnIndex(MainDBConstants.latitud)));
		if (cursorCasa.getDouble(cursorCasa.getColumnIndex(MainDBConstants.longitud))!= 0) mCasa.setLongitud(cursorCasa.getDouble(cursorCasa.getColumnIndex(MainDBConstants.longitud)));
		mCasa.setNombre1JefeFamilia(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.nombre1JefeFamilia)));
		mCasa.setNombre2JefeFamilia(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.nombre2JefeFamilia)));
		mCasa.setApellido1JefeFamilia(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.apellido1JefeFamilia)));
		mCasa.setApellido2JefeFamilia(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.apellido2JefeFamilia)));
		if(cursorCasa.getLong(cursorCasa.getColumnIndex(MainDBConstants.recordDate))>0) mCasa.setRecordDate(new Date(cursorCasa.getLong(cursorCasa.getColumnIndex(MainDBConstants.recordDate))));
		mCasa.setRecordUser(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.recordUser)));
		mCasa.setPasive(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mCasa.setEstado(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mCasa.setDeviceid(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.deviceId)));
		return mCasa;
	}

	public static void fillCasaStatement(SQLiteStatement stat, Casa casa){
		stat.bindLong(1, casa.getCodigo());
		stat.bindLong(2, casa.getBarrio().getCodigo());
		stat.bindString(3, casa.getDireccion());
		stat.bindString(4, casa.getManzana());
		bindDouble(stat,5, casa.getLatitud());
		bindDouble(stat,6, casa.getLongitud());
		stat.bindString(7, casa.getNombre1JefeFamilia());
		bindString(stat,8, casa.getNombre2JefeFamilia());
		stat.bindString(9, casa.getApellido1JefeFamilia());
		bindString(stat,10, casa.getApellido2JefeFamilia());
		bindDate(stat, 11, casa.getRecordDate());
		bindString(stat, 12, casa.getRecordUser());
		stat.bindString(13, String.valueOf(casa.getPasive()));
		bindString(stat,14, casa.getDeviceid());
		stat.bindString(15, String.valueOf(casa.getEstado()));
	}

	/**
	 * Seter string
 	 * @param stat SQLiteStatement
	 * @param index posicion en stat
	 * @param value valor a setear
	 */
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

	public static void bindInteger(SQLiteStatement stat, int index, Integer value){
		if (value == null) {
			stat.bindNull(index);
		} else {
			stat.bindLong(index, value);
		}
	}

	public static void bindLong(SQLiteStatement stat, int index, Long value){
		if (value == null) {
			stat.bindNull(index);
		} else {
			stat.bindLong(index, value);
		}
	}

	public static void bindDouble(SQLiteStatement stat, int index, Double value){
		if (value == null) {
			stat.bindNull(index);
		} else {
			stat.bindDouble(index, value);
		}
	}
}
