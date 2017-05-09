package ni.org.ics.estudios.appmovil.helpers;

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
		return mCasa;
	}
	
}
