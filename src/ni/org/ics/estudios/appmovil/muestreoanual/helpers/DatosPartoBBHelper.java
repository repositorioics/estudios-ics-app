package ni.org.ics.estudios.appmovil.muestreoanual.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.DatosPartoBB;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.DatosPartoBBId;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.Date;

public class DatosPartoBBHelper {
	
	public static ContentValues crearDatosPartoBBContentValues(DatosPartoBB datosPartoBB){
		ContentValues cv = new ContentValues();
		cv.put(ConstantsDB.codigo, datosPartoBB.getDatosPartoId().getCodigo());
		if (datosPartoBB.getDatosPartoId().getFechaDatosParto() != null) cv.put(ConstantsDB.fechaDatosParto, datosPartoBB.getDatosPartoId().getFechaDatosParto().getTime());
		cv.put(ConstantsDB.tipoParto, datosPartoBB.getTipoParto());
		cv.put(ConstantsDB.tiempoEmb_sndr, datosPartoBB.getTiempoEmb_sndr());
		cv.put(ConstantsDB.tiempoEmbSemana, datosPartoBB.getTiempoEmbSemana());
		cv.put(ConstantsDB.docMedTiempoEmb_sn, datosPartoBB.getDocMedTiempoEmb_sn());
		cv.put(ConstantsDB.docMedTiempoEmb, datosPartoBB.getDocMedTiempoEmb());
		cv.put(ConstantsDB.otroDocMedTiempoEmb, datosPartoBB.getOtroDocMedTiempoEmb());
		if (datosPartoBB.getFum() != null) cv.put(ConstantsDB.fum, datosPartoBB.getFum().getTime());
		cv.put(ConstantsDB.sg, datosPartoBB.getSg());
		cv.put(ConstantsDB.fumFueraRango_sn, datosPartoBB.getFumFueraRango_sn());
		cv.put(ConstantsDB.fumFueraRango_razon, datosPartoBB.getFumFueraRango_razon());
		cv.put(ConstantsDB.edadGest, datosPartoBB.getEdadGest());
		cv.put(ConstantsDB.docMedEdadGest_sn, datosPartoBB.getDocMedEdadGest_sn());
		cv.put(ConstantsDB.docMedEdadGest, datosPartoBB.getDocMedEdadGest());
		cv.put(ConstantsDB.OtroDocMedEdadGest, datosPartoBB.getOtroDocMedEdadGest());
		cv.put(ConstantsDB.prematuro_sndr, datosPartoBB.getPrematuro_sndr());
		cv.put(ConstantsDB.pesoBB_sndr, datosPartoBB.getPesoBB_sndr());
		cv.put(ConstantsDB.pesoBB, datosPartoBB.getPesoBB());
		cv.put(ConstantsDB.docMedPesoBB_sn, datosPartoBB.getDocMedPesoBB_sn());
		cv.put(ConstantsDB.docMedPesoBB, datosPartoBB.getDocMedPesoBB());
		cv.put(ConstantsDB.otroDocMedPesoBB, datosPartoBB.getOtroDocMedPesoBB());
        cv.put(ConstantsDB.tallaBB_sndr, datosPartoBB.getTallaBB_sndr());
        cv.put(ConstantsDB.tallaBB, datosPartoBB.getTallaBB());
        cv.put(ConstantsDB.docMedTallaBB_sn, datosPartoBB.getDocMedTallaBB_sn());
        cv.put(ConstantsDB.docMedTallaBB, datosPartoBB.getDocMedTallaBB());
        cv.put(ConstantsDB.otroDocMedTallaBB, datosPartoBB.getOtroDocMedTallaBB());
        cv.put(ConstantsDB.vacFluMadre_sn, datosPartoBB.getVacFluMadre_sn());
        if (datosPartoBB.getFechaVacInf() != null) cv.put(ConstantsDB.fechaVacInf, datosPartoBB.getFechaVacInf().getTime());
        cv.put(ConstantsDB.docMedFecVacInfMadre_sn, datosPartoBB.getDocMedFecVacInfMadre_sn());
        cv.put(ConstantsDB.docMedFecVacInfMadre, datosPartoBB.getDocMedFecVacInfMadre());
        cv.put(ConstantsDB.otroDocMedFecVacInfMadre, datosPartoBB.getOtroDocMedFecVacInfMadre());
        cv.put(ConstantsDB.docMedFUM_sn, datosPartoBB.getDocMedFUM_sn());

		cv.put(ConstantsDB.otrorecurso1, datosPartoBB.getOtrorecurso1());
		cv.put(ConstantsDB.otrorecurso2, datosPartoBB.getOtrorecurso2());
		cv.put(ConstantsDB.ID_INSTANCIA, datosPartoBB.getMovilInfo().getIdInstancia());
		cv.put(ConstantsDB.FILE_PATH, datosPartoBB.getMovilInfo().getInstancePath());
		cv.put(ConstantsDB.STATUS, datosPartoBB.getMovilInfo().getEstado());
		cv.put(ConstantsDB.WHEN_UPDATED, datosPartoBB.getMovilInfo().getUltimoCambio());
		cv.put(ConstantsDB.START, datosPartoBB.getMovilInfo().getStart());
		cv.put(ConstantsDB.END, datosPartoBB.getMovilInfo().getEnd());
		cv.put(ConstantsDB.DEVICE_ID, datosPartoBB.getMovilInfo().getDeviceid());
		cv.put(ConstantsDB.SIM_SERIAL, datosPartoBB.getMovilInfo().getSimserial());
		cv.put(ConstantsDB.PHONE_NUMBER, datosPartoBB.getMovilInfo().getPhonenumber());
		cv.put(ConstantsDB.TODAY, datosPartoBB.getMovilInfo().getToday().getTime());
		cv.put(ConstantsDB.USUARIO, datosPartoBB.getMovilInfo().getUsername());
		cv.put(ConstantsDB.DELETED, datosPartoBB.getMovilInfo().getEliminado());
		cv.put(ConstantsDB.REC1, datosPartoBB.getMovilInfo().getRecurso1());
		cv.put(ConstantsDB.REC2, datosPartoBB.getMovilInfo().getRecurso2());
		return cv; 
	}	
	
	
	public static DatosPartoBB crearDatosPartoBB(Cursor cursorDatosPartoBB){
		
		DatosPartoBB mDatosPartoBB = new DatosPartoBB();
		DatosPartoBBId mDatosPartoBBId = new DatosPartoBBId();
		mDatosPartoBBId.setCodigo(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.codigo)));
		mDatosPartoBBId.setFechaDatosParto(new Date(cursorDatosPartoBB.getLong(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fechaDatosParto))));
		mDatosPartoBB.setDatosPartoId(mDatosPartoBBId);
		mDatosPartoBB.setTipoParto(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tipoParto)));
		mDatosPartoBB.setTiempoEmb_sndr(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tiempoEmb_sndr)));
		if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tiempoEmbSemana))) mDatosPartoBB.setTiempoEmbSemana(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tiempoEmbSemana)));
		mDatosPartoBB.setDocMedTiempoEmb_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedTiempoEmb_sn)));
		mDatosPartoBB.setDocMedTiempoEmb(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedTiempoEmb)));
		mDatosPartoBB.setOtroDocMedTiempoEmb(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otroDocMedTiempoEmb)));
		if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fum))) mDatosPartoBB.setFum(new Date(cursorDatosPartoBB.getLong(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fum))));
		if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.sg))) mDatosPartoBB.setSg(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.sg)));
		mDatosPartoBB.setFumFueraRango_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fumFueraRango_sn)));
		mDatosPartoBB.setFumFueraRango_razon(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fumFueraRango_razon)));
		if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.edadGest))) mDatosPartoBB.setEdadGest(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.edadGest)));
		mDatosPartoBB.setDocMedEdadGest_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedEdadGest_sn)));
		mDatosPartoBB.setDocMedEdadGest(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedEdadGest)));
		mDatosPartoBB.setOtroDocMedEdadGest(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.OtroDocMedEdadGest)));
		mDatosPartoBB.setPrematuro_sndr(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.prematuro_sndr)));
        mDatosPartoBB.setPesoBB_sndr(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.pesoBB_sndr)));
		mDatosPartoBB.setPesoBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.pesoBB)));
		mDatosPartoBB.setDocMedPesoBB_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedPesoBB_sn)));
		mDatosPartoBB.setDocMedPesoBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedPesoBB)));
		mDatosPartoBB.setOtroDocMedPesoBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otroDocMedPesoBB)));
        mDatosPartoBB.setTallaBB_sndr(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tallaBB_sndr)));
        mDatosPartoBB.setTallaBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tallaBB)));
        mDatosPartoBB.setDocMedTallaBB_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedTallaBB_sn)));
        mDatosPartoBB.setDocMedTallaBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedTallaBB)));
        mDatosPartoBB.setOtroDocMedTallaBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otroDocMedTallaBB)));
        mDatosPartoBB.setVacFluMadre_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.vacFluMadre_sn)));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fechaVacInf))) mDatosPartoBB.setFechaVacInf(new Date(cursorDatosPartoBB.getLong(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fechaVacInf))));
        mDatosPartoBB.setDocMedFecVacInfMadre_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedFecVacInfMadre_sn)));
        mDatosPartoBB.setDocMedFecVacInfMadre(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedFecVacInfMadre)));
        mDatosPartoBB.setOtroDocMedFecVacInfMadre(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otroDocMedFecVacInfMadre)));
        mDatosPartoBB.setDocMedFUM_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedFUM_sn)));

		if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso1))) mDatosPartoBB.setOtrorecurso1(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso1)));
		if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso2))) mDatosPartoBB.setOtrorecurso2(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso2)));
		Date fecha = new Date(cursorDatosPartoBB.getLong(cursorDatosPartoBB.getColumnIndex(ConstantsDB.TODAY)));
		Boolean borrado = cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.DELETED))>0;
		mDatosPartoBB.setMovilInfo(new MovilInfo(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
				cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.FILE_PATH)),
				cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.STATUS)),
				cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
				cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.START)),
				cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.END)),
				cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.DEVICE_ID)),
				cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.SIM_SERIAL)),
				cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
				fecha,
				cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.USUARIO)),
				borrado,
				cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.REC1)),
				cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.REC2))));
		return mDatosPartoBB;
	}
	
	
}
