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

	public static void fillCasaCohorteFamiliaStatement(SQLiteStatement stmt,CasaCohorteFamilia casaCHF){
		stmt.bindString(1, casaCHF.getCodigoCHF());
		stmt.bindLong(2, casaCHF.getCasa().getCodigo());
		if (casaCHF.getNombre1JefeFamilia() != null) stmt.bindString(3, casaCHF.getNombre1JefeFamilia());
		if (casaCHF.getNombre2JefeFamilia() != null) stmt.bindString(4, casaCHF.getNombre2JefeFamilia());
		if (casaCHF.getApellido1JefeFamilia() != null) stmt.bindString(5, casaCHF.getApellido1JefeFamilia());
		if (casaCHF.getApellido2JefeFamilia() != null) stmt.bindString(6, casaCHF.getApellido2JefeFamilia());
		if (casaCHF.getLatitud() != null) stmt.bindDouble(7, casaCHF.getLatitud());
		if (casaCHF.getLongitud() != null) stmt.bindDouble(8, casaCHF.getLongitud());
		if (casaCHF.getRecordDate() != null) stmt.bindLong(9, casaCHF.getRecordDate().getTime());
		if (casaCHF.getRecordUser() != null) stmt.bindString(10, casaCHF.getRecordUser());
		stmt.bindString(11, String.valueOf(casaCHF.getPasive()));
		if (casaCHF.getDeviceid() != null) stmt.bindString(12, casaCHF.getDeviceid());
		stmt.bindString(13, String.valueOf(casaCHF.getEstado()));
	}
	
}
