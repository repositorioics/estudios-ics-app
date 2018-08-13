package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.CambioDomicilio;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

public class CambioDomicilioHelper {
	
	public static ContentValues crearCambioDomicilioContentValues(CambioDomicilio cambioDomicilio){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.codigo, cambioDomicilio.getCodigo());
        cv.put(MainDBConstants.participante, cambioDomicilio.getParticipante().getCodigo());
		cv.put(MainDBConstants.barrio, cambioDomicilio.getBarrio().getCodigo());
		cv.put(MainDBConstants.direccion, cambioDomicilio.getDireccion());
		cv.put(MainDBConstants.manzana, cambioDomicilio.getManzana());
		if (cambioDomicilio.getLatitud() != null) cv.put(MainDBConstants.latitud, cambioDomicilio.getLatitud());
		if (cambioDomicilio.getLongitud() != null) cv.put(MainDBConstants.longitud, cambioDomicilio.getLongitud());
		cv.put(MainDBConstants.otroBarrio, cambioDomicilio.getOtroBarrio());
		cv.put(MainDBConstants.puntoGps, cambioDomicilio.getPuntoGps());
		cv.put(MainDBConstants.razonNoGeoref, cambioDomicilio.getRazonNoGeoref());
		cv.put(MainDBConstants.otraRazonNoGeoref, cambioDomicilio.getOtraRazonNoGeoref());
		if (cambioDomicilio.getRecordDate() != null) cv.put(MainDBConstants.recordDate, cambioDomicilio.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, cambioDomicilio.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(cambioDomicilio.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(cambioDomicilio.getEstado()));
		cv.put(MainDBConstants.deviceId, cambioDomicilio.getDeviceid());
		return cv; 
	}	
	
	public static CambioDomicilio crearCambioDomicilio(Cursor cursorCambio){

        CambioDomicilio mCambio = new CambioDomicilio();
		mCambio.setCodigo(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.codigo)));
		mCambio.setBarrio(null);
        mCambio.setParticipante(null);
		mCambio.setDireccion(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.direccion)));
		mCambio.setManzana(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.manzana)));
		if (cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.latitud))!= 0) mCambio.setLatitud(cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.latitud)));
		if (cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.longitud))!= 0) mCambio.setLongitud(cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.longitud)));
		mCambio.setOtroBarrio(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.otroBarrio)));
		mCambio.setPuntoGps(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.puntoGps)));
		mCambio.setRazonNoGeoref(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.razonNoGeoref)));
		mCambio.setOtraRazonNoGeoref(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.otraRazonNoGeoref)));
		if(cursorCambio.getLong(cursorCambio.getColumnIndex(MainDBConstants.recordDate))>0) mCambio.setRecordDate(new Date(cursorCambio.getLong(cursorCambio.getColumnIndex(MainDBConstants.recordDate))));
		mCambio.setRecordUser(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.recordUser)));
		mCambio.setPasive(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mCambio.setEstado(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mCambio.setDeviceid(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.deviceId)));
		return mCambio;
	}
	
}
