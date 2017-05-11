package ni.org.ics.estudios.appmovil.helpers;

import java.util.Date;

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
	
}
