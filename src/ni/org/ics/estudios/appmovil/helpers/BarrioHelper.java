package ni.org.ics.estudios.appmovil.helpers;

import java.util.Date;

import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class BarrioHelper {
	
	public static ContentValues crearBarrioContentValues(Barrio barrio){
		ContentValues cv = new ContentValues();
		cv.put(CatalogosDBConstants.codigo, barrio.getCodigo());
		cv.put(CatalogosDBConstants.nombre, barrio.getNombre());
		if (barrio.getRecordDate() != null) cv.put(MainDBConstants.recordDate, barrio.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, barrio.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(barrio.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(barrio.getEstado()));
		cv.put(MainDBConstants.deviceId, barrio.getDeviceid());
		return cv; 
	}	
	
	public static Barrio crearBarrio(Cursor cursorBarrio){
		
		Barrio mBarrio = new Barrio();
		mBarrio.setCodigo(cursorBarrio.getInt(cursorBarrio.getColumnIndex(CatalogosDBConstants.codigo)));
		mBarrio.setNombre(cursorBarrio.getString(cursorBarrio.getColumnIndex(CatalogosDBConstants.nombre)));
		if(cursorBarrio.getLong(cursorBarrio.getColumnIndex(MainDBConstants.recordDate))>0) mBarrio.setRecordDate(new Date(cursorBarrio.getLong(cursorBarrio.getColumnIndex(MainDBConstants.recordDate))));
		mBarrio.setRecordUser(cursorBarrio.getString(cursorBarrio.getColumnIndex(MainDBConstants.recordUser)));
		mBarrio.setPasive(cursorBarrio.getString(cursorBarrio.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mBarrio.setEstado(cursorBarrio.getString(cursorBarrio.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mBarrio.setDeviceid(cursorBarrio.getString(cursorBarrio.getColumnIndex(MainDBConstants.deviceId)));
		return mBarrio;
	}
	
}
