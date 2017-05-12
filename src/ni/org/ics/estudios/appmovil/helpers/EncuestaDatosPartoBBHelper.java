package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaDatosPartoBB;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class EncuestaDatosPartoBBHelper {

    public static ContentValues crearEncuestaDatosPartoBBContentValues(EncuestaDatosPartoBB encuesta){
        ContentValues cv = new ContentValues();
        cv.put(EncuestasDBConstants.participante, encuesta.getParticipante().getCodigo());
        cv.put(EncuestasDBConstants.tipoParto, encuesta.getTipoParto());
        cv.put(EncuestasDBConstants.tiempoEmb_sndr, encuesta.getTiempoEmb_sndr());
        cv.put(EncuestasDBConstants.tiempoEmbSemana, encuesta.getTiempoEmbSemana());
        cv.put(EncuestasDBConstants.docMedTiempoEmb_sn, encuesta.getDocMedTiempoEmb_sn());
        cv.put(EncuestasDBConstants.docMedTiempoEmb, encuesta.getDocMedTiempoEmb());
        cv.put(EncuestasDBConstants.otroDocMedTiempoEmb, encuesta.getOtroDocMedTiempoEmb());
        if (encuesta.getFum()!=null) cv.put(EncuestasDBConstants.fum, encuesta.getFum().getTime());
        cv.put(EncuestasDBConstants.sg, encuesta.getSg());
        cv.put(EncuestasDBConstants.fumFueraRango_sn, encuesta.getFumFueraRango_sn());
        cv.put(EncuestasDBConstants.fumFueraRango_razon, encuesta.getFumFueraRango_razon());
        cv.put(EncuestasDBConstants.edadGest, encuesta.getEdadGest());
        cv.put(EncuestasDBConstants.docMedEdadGest_sn, encuesta.getDocMedEdadGest_sn());
        cv.put(EncuestasDBConstants.docMedEdadGest, encuesta.getDocMedEdadGest());
        cv.put(EncuestasDBConstants.OtroDocMedEdadGest, encuesta.getOtroDocMedEdadGest());
        cv.put(EncuestasDBConstants.prematuro_sndr, encuesta.getPrematuro_sndr());
        cv.put(EncuestasDBConstants.pesoBB_sndr, encuesta.getPesoBB_sndr());
        cv.put(EncuestasDBConstants.pesoBB, encuesta.getPesoBB());
        cv.put(EncuestasDBConstants.docMedPesoBB_sn, encuesta.getDocMedPesoBB_sn());
        cv.put(EncuestasDBConstants.docMedPesoBB, encuesta.getDocMedPesoBB());
        cv.put(EncuestasDBConstants.otroDocMedPesoBB, encuesta.getOtroDocMedPesoBB());
        cv.put(EncuestasDBConstants.otrorecurso1, encuesta.getOtrorecurso1());
        cv.put(EncuestasDBConstants.otrorecurso2, encuesta.getOtrorecurso2());
        if (encuesta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, encuesta.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, encuesta.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(encuesta.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));
        cv.put(MainDBConstants.deviceId, encuesta.getDeviceid());

        return cv;
    }

    public static EncuestaDatosPartoBB crearEncuestaDatosPartoBB(Cursor cursor){
        EncuestaDatosPartoBB mEncuesta = new EncuestaDatosPartoBB();

        mEncuesta.setParticipante(null);
        mEncuesta.setTipoParto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tipoParto)));
        mEncuesta.setTiempoEmb_sndr(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tiempoEmb_sndr)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiempoEmbSemana)) > 0) mEncuesta.setTiempoEmbSemana(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiempoEmbSemana)));
        mEncuesta.setDocMedTiempoEmb_sn(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedTiempoEmb_sn)));
        mEncuesta.setDocMedTiempoEmb(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedTiempoEmb)));
        mEncuesta.setOtroDocMedTiempoEmb(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroDocMedTiempoEmb)));
        if (cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fum)) > 0) mEncuesta.setFum(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fum))));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.sg)) > 0) mEncuesta.setSg(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.sg)));
        mEncuesta.setFumFueraRango_sn(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fumFueraRango_sn)));
        mEncuesta.setFumFueraRango_razon(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fumFueraRango_razon)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadGest)) > 0) mEncuesta.setEdadGest(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadGest)));
        mEncuesta.setDocMedEdadGest_sn(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedEdadGest_sn)));
        mEncuesta.setDocMedEdadGest(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedEdadGest)));
        mEncuesta.setOtroDocMedEdadGest(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.OtroDocMedEdadGest)));
        mEncuesta.setPrematuro_sndr(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.prematuro_sndr)));
        mEncuesta.setPesoBB_sndr(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.pesoBB_sndr)));
        mEncuesta.setPesoBB(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.pesoBB)));
        mEncuesta.setPesoBB_sndr(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedPesoBB_sn)));
        mEncuesta.setDocMedPesoBB(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedPesoBB)));
        mEncuesta.setOtroDocMedPesoBB(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroDocMedPesoBB)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)) > 0) mEncuesta.setOtrorecurso1(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso2)) > 0)mEncuesta.setOtrorecurso2(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso2)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mEncuesta.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mEncuesta.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mEncuesta.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mEncuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mEncuesta.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mEncuesta;
    }
}
