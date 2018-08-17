package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.EnfermedadCronica;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

public class EnfermedadCronicaHelper {
	
	public static ContentValues crearEnfermedadCronicaContentValues(EnfermedadCronica enfermedadCronica){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.id, enfermedadCronica.getId());
        cv.put(MainDBConstants.tamizaje, enfermedadCronica.getTamizaje().getCodigo());
		cv.put(MainDBConstants.enfermedad, enfermedadCronica.getEnfermedad());
		cv.put(MainDBConstants.otraEnfCronica, enfermedadCronica.getOtraEnfermedad());
		cv.put(MainDBConstants.enfCronicaAnio, enfermedadCronica.getAnioEnfermedad());
		cv.put(MainDBConstants.enfCronicaMes, enfermedadCronica.getMesEnfermedad());

        if (enfermedadCronica.getRecordDate() != null) cv.put(MainDBConstants.recordDate, enfermedadCronica.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, enfermedadCronica.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(enfermedadCronica.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(enfermedadCronica.getEstado()));
		cv.put(MainDBConstants.deviceId, enfermedadCronica.getDeviceid());
		return cv; 
	}	
	
	public static EnfermedadCronica crearEnfermedadCronica(Cursor cursorEnfCro){

        EnfermedadCronica mCambio = new EnfermedadCronica();
		mCambio.setId(cursorEnfCro.getString(cursorEnfCro.getColumnIndex(MainDBConstants.id)));
		mCambio.setTamizaje(null);
		mCambio.setEnfermedad(cursorEnfCro.getString(cursorEnfCro.getColumnIndex(MainDBConstants.enfermedad)));
		mCambio.setOtraEnfermedad(cursorEnfCro.getString(cursorEnfCro.getColumnIndex(MainDBConstants.otraEnfCronica)));
		mCambio.setMesEnfermedad(cursorEnfCro.getString(cursorEnfCro.getColumnIndex(MainDBConstants.enfCronicaMes)));
		mCambio.setAnioEnfermedad(cursorEnfCro.getString(cursorEnfCro.getColumnIndex(MainDBConstants.enfCronicaAnio)));

		if(cursorEnfCro.getLong(cursorEnfCro.getColumnIndex(MainDBConstants.recordDate))>0) mCambio.setRecordDate(new Date(cursorEnfCro.getLong(cursorEnfCro.getColumnIndex(MainDBConstants.recordDate))));
		mCambio.setRecordUser(cursorEnfCro.getString(cursorEnfCro.getColumnIndex(MainDBConstants.recordUser)));
		mCambio.setPasive(cursorEnfCro.getString(cursorEnfCro.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mCambio.setEstado(cursorEnfCro.getString(cursorEnfCro.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mCambio.setDeviceid(cursorEnfCro.getString(cursorEnfCro.getColumnIndex(MainDBConstants.deviceId)));
		return mCambio;
	}
	
}
