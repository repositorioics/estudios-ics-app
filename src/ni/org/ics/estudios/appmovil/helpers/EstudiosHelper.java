package ni.org.ics.estudios.appmovil.helpers;

import java.util.Date;

import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class EstudiosHelper {
	
	public static ContentValues crearEstudioContentValues(Estudio estudio){
		ContentValues cv = new ContentValues();
		cv.put(CatalogosDBConstants.codigo, estudio.getCodigo());
		cv.put(CatalogosDBConstants.nombre, estudio.getNombre());
		if (estudio.getRecordDate() != null) cv.put(MainDBConstants.recordDate, estudio.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, estudio.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(estudio.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(estudio.getEstado()));
		cv.put(MainDBConstants.deviceId, estudio.getDeviceid());
		return cv; 
	}	
	
	public static Estudio crearEstudio(Cursor cursorEstudio){
		
		Estudio mEstudio = new Estudio();
		mEstudio.setCodigo(cursorEstudio.getInt(cursorEstudio.getColumnIndex(CatalogosDBConstants.codigo)));
		mEstudio.setNombre(cursorEstudio.getString(cursorEstudio.getColumnIndex(CatalogosDBConstants.nombre)));
		if(cursorEstudio.getLong(cursorEstudio.getColumnIndex(MainDBConstants.recordDate))>0) mEstudio.setRecordDate(new Date(cursorEstudio.getLong(cursorEstudio.getColumnIndex(MainDBConstants.recordDate))));
		mEstudio.setRecordUser(cursorEstudio.getString(cursorEstudio.getColumnIndex(MainDBConstants.recordUser)));
		mEstudio.setPasive(cursorEstudio.getString(cursorEstudio.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mEstudio.setEstado(cursorEstudio.getString(cursorEstudio.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mEstudio.setDeviceid(cursorEstudio.getString(cursorEstudio.getColumnIndex(MainDBConstants.deviceId)));
		return mEstudio;
	}
	
}
