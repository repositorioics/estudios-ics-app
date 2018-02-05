package ni.org.ics.estudios.appmovil.muestreoanual.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.NewVacuna;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.NewVacunaId;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.Date;

public class NewVacunaHelper {
	
	public static ContentValues crearNewVacunaContentValues(NewVacuna newVacuna){
		ContentValues cv = new ContentValues();
		cv.put(ConstantsDB.codigo, newVacuna.getVacunaId().getCodigo());
		if (newVacuna.getVacunaId().getFechaRegistroVacuna() != null) cv.put(ConstantsDB.fechaRegistroVacuna, newVacuna.getVacunaId().getFechaRegistroVacuna().getTime());
		cv.put(ConstantsDB.vacuna_sn, newVacuna.getVacuna_sn());
		cv.put(ConstantsDB.tvacunano, newVacuna.getTvacunano());
		cv.put(ConstantsDB.otroMotivoTvacunano, newVacuna.getOtroMotivoTvacunano());
		cv.put(ConstantsDB.otrorecurso1, newVacuna.getOtrorecurso1());
		cv.put(ConstantsDB.ID_INSTANCIA, newVacuna.getMovilInfo().getIdInstancia());
		cv.put(ConstantsDB.FILE_PATH, newVacuna.getMovilInfo().getInstancePath());
		cv.put(ConstantsDB.STATUS, newVacuna.getMovilInfo().getEstado());
		cv.put(ConstantsDB.WHEN_UPDATED, newVacuna.getMovilInfo().getUltimoCambio());
		cv.put(ConstantsDB.START, newVacuna.getMovilInfo().getStart());
		cv.put(ConstantsDB.END, newVacuna.getMovilInfo().getEnd());
		cv.put(ConstantsDB.DEVICE_ID, newVacuna.getMovilInfo().getDeviceid());
		cv.put(ConstantsDB.SIM_SERIAL, newVacuna.getMovilInfo().getSimserial());
		cv.put(ConstantsDB.PHONE_NUMBER, newVacuna.getMovilInfo().getPhonenumber());
		cv.put(ConstantsDB.TODAY, newVacuna.getMovilInfo().getToday().getTime());
		cv.put(ConstantsDB.USUARIO, newVacuna.getMovilInfo().getUsername());
		cv.put(ConstantsDB.DELETED, newVacuna.getMovilInfo().getEliminado());
		cv.put(ConstantsDB.REC1, newVacuna.getMovilInfo().getRecurso1());
		cv.put(ConstantsDB.REC2, newVacuna.getMovilInfo().getRecurso2());
		return cv; 
	}	
	
	
	public static NewVacuna crearNewVacuna(Cursor cursorNewVacuna){
		
		NewVacuna mNewVacuna = new NewVacuna();
		NewVacunaId mNewVacunaId = new NewVacunaId();
		mNewVacunaId.setCodigo(cursorNewVacuna.getInt(cursorNewVacuna.getColumnIndex(ConstantsDB.codigo)));
		mNewVacunaId.setFechaRegistroVacuna(new Date(cursorNewVacuna.getLong(cursorNewVacuna.getColumnIndex(ConstantsDB.fechaRegistroVacuna))));
		mNewVacuna.setVacunaId(mNewVacunaId);
		mNewVacuna.setVacuna_sn(cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.vacuna_sn)));
		mNewVacuna.setTvacunano(cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.tvacunano)));
		mNewVacuna.setOtroMotivoTvacunano(cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.otroMotivoTvacunano)));
		if(!cursorNewVacuna.isNull(cursorNewVacuna.getColumnIndex(ConstantsDB.otrorecurso1))) mNewVacuna.setOtrorecurso1(cursorNewVacuna.getInt(cursorNewVacuna.getColumnIndex(ConstantsDB.otrorecurso1)));
		Date fecha = new Date(cursorNewVacuna.getLong(cursorNewVacuna.getColumnIndex(ConstantsDB.TODAY)));
		Boolean borrado = cursorNewVacuna.getInt(cursorNewVacuna.getColumnIndex(ConstantsDB.DELETED))>0;
		mNewVacuna.setMovilInfo(new MovilInfo(cursorNewVacuna.getInt(cursorNewVacuna.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
				cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.FILE_PATH)),
				cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.STATUS)),
				cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
				cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.START)),
				cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.END)),
				cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.DEVICE_ID)),
				cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.SIM_SERIAL)),
				cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
				fecha,
				cursorNewVacuna.getString(cursorNewVacuna.getColumnIndex(ConstantsDB.USUARIO)),
				borrado,
				cursorNewVacuna.getInt(cursorNewVacuna.getColumnIndex(ConstantsDB.REC1)),
				cursorNewVacuna.getInt(cursorNewVacuna.getColumnIndex(ConstantsDB.REC2))));
		return mNewVacuna;
	}
	
	
}
