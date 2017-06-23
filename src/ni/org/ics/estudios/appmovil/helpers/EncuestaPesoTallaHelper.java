package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaPesoTalla;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class EncuestaPesoTallaHelper {

    public static ContentValues crearEncuestaPesoTallaContentValues(EncuestaPesoTalla encuesta){
        ContentValues cv = new ContentValues();

        cv.put(EncuestasDBConstants.participante, encuesta.getParticipante().getParticipante().getCodigo());
        cv.put(EncuestasDBConstants.tomoMedidaSn, encuesta.getTomoMedidaSn());
        cv.put(EncuestasDBConstants.razonNoTomoMedidas, encuesta.getRazonNoTomoMedidas());
        cv.put(EncuestasDBConstants.peso1, encuesta.getPeso1());
        cv.put(EncuestasDBConstants.peso2, encuesta.getPeso2());
        cv.put(EncuestasDBConstants.peso3, encuesta.getPeso3());
        cv.put(EncuestasDBConstants.talla1, encuesta.getTalla1());
        cv.put(EncuestasDBConstants.talla2, encuesta.getTalla2());
        cv.put(EncuestasDBConstants.talla3, encuesta.getTalla3());
        cv.put(EncuestasDBConstants.imc1, encuesta.getImc1());
        cv.put(EncuestasDBConstants.imc2, encuesta.getImc2());
        cv.put(EncuestasDBConstants.imc3, encuesta.getImc3());
        cv.put(EncuestasDBConstants.difPeso, encuesta.getDifPeso());
        cv.put(EncuestasDBConstants.difTalla, encuesta.getDifTalla());
        cv.put(EncuestasDBConstants.recurso1, encuesta.getRecurso1());
        cv.put(EncuestasDBConstants.otrorecurso1, encuesta.getOtrorecurso1());
        if (encuesta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, encuesta.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, encuesta.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(encuesta.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));
        cv.put(MainDBConstants.deviceId, encuesta.getDeviceid());

        return cv;
    }

    public static EncuestaPesoTalla crearEncuestaPesoTalla(Cursor cursor){
        EncuestaPesoTalla mEncuesta = new EncuestaPesoTalla();

        mEncuesta.setParticipante(null);
        mEncuesta.setTomoMedidaSn(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tomoMedidaSn)));
        mEncuesta.setRazonNoTomoMedidas(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.razonNoTomoMedidas)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso1)) > 0) mEncuesta.setPeso1(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso1)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso2)) > 0) mEncuesta.setPeso2(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso2)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso3)) > 0) mEncuesta.setPeso3(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso3)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla1)) > 0) mEncuesta.setTalla1(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla1)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla2)) > 0) mEncuesta.setTalla2(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla2)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla3)) > 0) mEncuesta.setTalla3(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla3)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc1)) > 0) mEncuesta.setImc1(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc1)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc2)) > 0) mEncuesta.setImc2(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc2)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc3)) > 0) mEncuesta.setImc3(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc3)));
        mEncuesta.setDifPeso(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.difPeso)));
        mEncuesta.setDifTalla(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.difTalla)));
        mEncuesta.setRecurso1(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.recurso1)));
        mEncuesta.setOtrorecurso1(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mEncuesta.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mEncuesta.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mEncuesta.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mEncuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mEncuesta.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mEncuesta;
    }
}
