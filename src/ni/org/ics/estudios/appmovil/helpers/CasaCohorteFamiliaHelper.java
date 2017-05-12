package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
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
		if(cursorCasa.getLong(cursorCasa.getColumnIndex(MainDBConstants.recordDate))>0) mCasaCHF.setRecordDate(new Date(cursorCasa.getLong(cursorCasa.getColumnIndex(MainDBConstants.recordDate))));
		mCasaCHF.setRecordUser(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.recordUser)));
		mCasaCHF.setPasive(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mCasaCHF.setEstado(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mCasaCHF.setDeviceid(cursorCasa.getString(cursorCasa.getColumnIndex(MainDBConstants.deviceId)));
		return mCasaCHF;
	}
	
}
