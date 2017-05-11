package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaCasa;
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
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));

        return cv;
    }

    public static EncuestaDatosPartoBB crearEncuestaDatosPartoBB(Cursor cursor){
        EncuestaDatosPartoBB encuesta = new EncuestaDatosPartoBB();

        encuesta.setParticipante(null);
        encuesta.setTipoParto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tipoParto)));
        encuesta.setTiempoEmb_sndr(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tiempoEmb_sndr)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiempoEmbSemana)) > 0) encuesta.setTiempoEmbSemana(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiempoEmbSemana)));
        encuesta.setDocMedTiempoEmb_sn(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedTiempoEmb_sn)));
        encuesta.setDocMedTiempoEmb(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedTiempoEmb)));
        encuesta.setOtroDocMedTiempoEmb(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroDocMedTiempoEmb)));
        if (cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fum)) > 0) encuesta.setFum(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fum))));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.sg)) > 0) encuesta.setSg(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.sg)));
        encuesta.setFumFueraRango_sn(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fumFueraRango_sn)));
        encuesta.setFumFueraRango_razon(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fumFueraRango_razon)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadGest)) > 0) encuesta.setEdadGest(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadGest)));
        encuesta.setDocMedEdadGest_sn(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedEdadGest_sn)));
        encuesta.setDocMedEdadGest(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedEdadGest)));
        encuesta.setOtroDocMedEdadGest(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.OtroDocMedEdadGest)));
        encuesta.setPrematuro_sndr(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.prematuro_sndr)));
        encuesta.setPesoBB_sndr(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.pesoBB_sndr)));
        encuesta.setPesoBB(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.pesoBB)));
        encuesta.setPesoBB_sndr(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedPesoBB_sn)));
        encuesta.setDocMedPesoBB(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.docMedPesoBB)));
        encuesta.setOtroDocMedPesoBB(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroDocMedPesoBB)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)) > 0) encuesta.setOtrorecurso1(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso2)) > 0)encuesta.setOtrorecurso2(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso2)));
        encuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));

        return encuesta;
    }
}
