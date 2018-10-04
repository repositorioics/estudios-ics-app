package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.DatosCoordenadas;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.Date;

public class CambioDomicilioHelper {
	
	public static ContentValues crearCambioDomicilioContentValues(DatosCoordenadas datosCoordenadas){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.codigo, datosCoordenadas.getCodigo());
        cv.put(MainDBConstants.casa, datosCoordenadas.getCodigoCasa());
        cv.put(MainDBConstants.estudios, datosCoordenadas.getEstudios());
        cv.put(MainDBConstants.participante, datosCoordenadas.getParticipante().getCodigo());
        cv.put(MainDBConstants.motivo, datosCoordenadas.getMotivo());
		cv.put(MainDBConstants.barrio, datosCoordenadas.getBarrio().getCodigo());
		cv.put(MainDBConstants.direccion, datosCoordenadas.getDireccion());
		cv.put(MainDBConstants.manzana, datosCoordenadas.getManzana());
        cv.put(MainDBConstants.conpunto, datosCoordenadas.getConpunto());
		if (datosCoordenadas.getLatitud() != null) cv.put(MainDBConstants.latitud, datosCoordenadas.getLatitud());
		if (datosCoordenadas.getLongitud() != null) cv.put(MainDBConstants.longitud, datosCoordenadas.getLongitud());
		cv.put(MainDBConstants.otroBarrio, datosCoordenadas.getOtroBarrio());
		cv.put(MainDBConstants.puntoGps, datosCoordenadas.getPuntoGps());
		cv.put(MainDBConstants.razonNoGeoref, datosCoordenadas.getRazonNoGeoref());
		cv.put(MainDBConstants.otraRazonNoGeoref, datosCoordenadas.getOtraRazonNoGeoref());
        cv.put(ConstantsDB.ID_INSTANCIA, datosCoordenadas.getMovilInfo().getIdInstancia());
        cv.put(ConstantsDB.FILE_PATH, datosCoordenadas.getMovilInfo().getInstancePath());
        cv.put(ConstantsDB.STATUS, datosCoordenadas.getMovilInfo().getEstado());
        cv.put(ConstantsDB.WHEN_UPDATED, datosCoordenadas.getMovilInfo().getUltimoCambio());
        cv.put(ConstantsDB.START, datosCoordenadas.getMovilInfo().getStart());
        cv.put(ConstantsDB.END, datosCoordenadas.getMovilInfo().getEnd());
        cv.put(ConstantsDB.DEVICE_ID, datosCoordenadas.getMovilInfo().getDeviceid());
        cv.put(ConstantsDB.SIM_SERIAL, datosCoordenadas.getMovilInfo().getSimserial());
        cv.put(ConstantsDB.PHONE_NUMBER, datosCoordenadas.getMovilInfo().getPhonenumber());
        cv.put(ConstantsDB.TODAY, datosCoordenadas.getMovilInfo().getToday().getTime());
        cv.put(ConstantsDB.USUARIO, datosCoordenadas.getMovilInfo().getUsername());
        cv.put(ConstantsDB.DELETED, datosCoordenadas.getMovilInfo().getEliminado());
        cv.put(ConstantsDB.REC1, datosCoordenadas.getMovilInfo().getRecurso1());
        cv.put(ConstantsDB.REC2, datosCoordenadas.getMovilInfo().getRecurso2());
		return cv;
	}	
	
	public static DatosCoordenadas crearCambioDomicilio(Cursor cursorCambio){

        DatosCoordenadas mCambio = new DatosCoordenadas();
		mCambio.setCodigo(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.codigo)));
        mCambio.setCodigoCasa(cursorCambio.getInt(cursorCambio.getColumnIndex(MainDBConstants.casa)));
        mCambio.setEstudios(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.estudios)));
		mCambio.setBarrio(null);
        mCambio.setParticipante(null);
        mCambio.setMotivo(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.motivo)));
		mCambio.setDireccion(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.direccion)));
		mCambio.setManzana(cursorCambio.getInt(cursorCambio.getColumnIndex(MainDBConstants.manzana)));
        mCambio.setConpunto(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.conpunto)));
		if (cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.latitud))!= 0) mCambio.setLatitud(cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.latitud)));
		if (cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.longitud))!= 0) mCambio.setLongitud(cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.longitud)));
		mCambio.setOtroBarrio(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.otroBarrio)));
		mCambio.setPuntoGps(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.puntoGps)));
		mCambio.setRazonNoGeoref(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.razonNoGeoref)));
		mCambio.setOtraRazonNoGeoref(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.otraRazonNoGeoref)));
        Date fecha = new Date(cursorCambio.getLong(cursorCambio.getColumnIndex(ConstantsDB.TODAY)));
        Boolean borrado = cursorCambio.getInt(cursorCambio.getColumnIndex(ConstantsDB.DELETED))>0;
        MovilInfo movilInfo = new MovilInfo(cursorCambio.getInt(cursorCambio.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                cursorCambio.getString(cursorCambio.getColumnIndex(ConstantsDB.FILE_PATH)),
                cursorCambio.getString(cursorCambio.getColumnIndex(ConstantsDB.STATUS)),
                cursorCambio.getString(cursorCambio.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                cursorCambio.getString(cursorCambio.getColumnIndex(ConstantsDB.START)),
                cursorCambio.getString(cursorCambio.getColumnIndex(ConstantsDB.END)),
                cursorCambio.getString(cursorCambio.getColumnIndex(ConstantsDB.DEVICE_ID)),
                cursorCambio.getString(cursorCambio.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                cursorCambio.getString(cursorCambio.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                cursorCambio.getString(cursorCambio.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                cursorCambio.getInt(cursorCambio.getColumnIndex(ConstantsDB.REC1)),
                cursorCambio.getInt(cursorCambio.getColumnIndex(ConstantsDB.REC2)));
        mCambio.setMovilInfo(movilInfo);

		return mCambio;
	}
	
}
