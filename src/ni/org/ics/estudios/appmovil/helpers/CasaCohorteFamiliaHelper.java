package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

public class CasaCohorteFamiliaHelper {
	
	public static ContentValues crearCasaCHFontentValues(CasaCohorteFamilia casaCHF){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.codigoCHF, casaCHF.getCodigoCHF());
		cv.put(MainDBConstants.casa, casaCHF.getCasa().getCodigo());
		cv.put(MainDBConstants.nombre1JefeFamilia, casaCHF.getNombre1JefeFamilia());
		cv.put(MainDBConstants.nombre2JefeFamilia, casaCHF.getNombre2JefeFamilia());
		cv.put(MainDBConstants.apellido1JefeFamilia, casaCHF.getApellido1JefeFamilia());
		cv.put(MainDBConstants.apellido2JefeFamilia, casaCHF.getApellido2JefeFamilia());
		if (casaCHF.getLatitud() != null) cv.put(MainDBConstants.latitud, casaCHF.getLatitud());
		if (casaCHF.getLongitud() != null) cv.put(MainDBConstants.longitud, casaCHF.getLongitud());
		if (casaCHF.getRecordDate() != null) cv.put(MainDBConstants.recordDate, casaCHF.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, casaCHF.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(casaCHF.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(casaCHF.getEstado()));
		cv.put(MainDBConstants.deviceId, casaCHF.getDeviceid());
		return cv; 
	}	
	
	public static CasaCohorteFamilia crearCasaCHF(Cursor cursorCasa){
		
		CasaCohorteFamilia mCasaCHF = new CasaCohorteFamilia();
		mCasaCHF.setCodigoCHF(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.codigoCHF)));
		mCasaCHF.setCasa(null);
		mCasaCHF.setNombre1JefeFamilia(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.nombre1JefeFamilia)));
		mCasaCHF.setNombre2JefeFamilia(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.nombre2JefeFamilia)));
		mCasaCHF.setApellido1JefeFamilia(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.apellido1JefeFamilia)));
		mCasaCHF.setApellido2JefeFamilia(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.apellido2JefeFamilia)));
		if (cursorCasa.getDouble(cursorCasa.getColumnIndex(MainDBConstants.latitud))!= 0) mCasaCHF.setLatitud(cursorCasa.getDouble(cursorCasa.getColumnIndex(MainDBConstants.latitud)));
		if (cursorCasa.getDouble(cursorCasa.getColumnIndex(MainDBConstants.longitud))!= 0) mCasaCHF.setLongitud(cursorCasa.getDouble(cursorCasa.getColumnIndex(MainDBConstants.longitud)));
		if(cursorCasa.getLong(cursorCasa.getColumnIndex(MainDBConstants.recordDate))>0) mCasaCHF.setRecordDate(new Date(cursorCasa.getLong(cursorCasa.getColumnIndex(MainDBConstants.recordDate))));
		mCasaCHF.setRecordUser(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.recordUser)));
		mCasaCHF.setPasive(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mCasaCHF.setEstado(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mCasaCHF.setDeviceid(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.deviceId)));
		return mCasaCHF;
	}

	public static void fillCasaCohorteFamiliaStatement(SQLiteStatement stat,CasaCohorteFamilia casaCHF){
		stat.bindString(1, casaCHF.getCodigoCHF());
		stat.bindLong(2, casaCHF.getCasa().getCodigo());
		bindString(stat,3, casaCHF.getNombre1JefeFamilia());
		bindString(stat,4, casaCHF.getNombre2JefeFamilia());
		bindString(stat,5, casaCHF.getApellido1JefeFamilia());
		bindString(stat,6, casaCHF.getApellido2JefeFamilia());
		bindDouble(stat, 7, casaCHF.getLatitud());
		bindDouble(stat, 8, casaCHF.getLongitud());
		bindDate( stat,9, casaCHF.getRecordDate());
		bindString(stat,10, casaCHF.getRecordUser());
		stat.bindString(11, String.valueOf(casaCHF.getPasive()));
		bindString(stat,12, casaCHF.getDeviceid());
		stat.bindString(13, String.valueOf(casaCHF.getEstado()));
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
}
