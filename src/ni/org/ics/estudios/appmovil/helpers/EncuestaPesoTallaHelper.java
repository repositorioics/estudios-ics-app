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

        cv.put(EncuestasDBConstants.participante_chf, encuesta.getParticipante().getCodigo());
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
        cv.put(EncuestasDBConstants.otrorecurso1, encuesta.getOtrorecurso1());
        cv.put(EncuestasDBConstants.otrorecurso2, encuesta.getOtrorecurso2());
        if (encuesta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, encuesta.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, encuesta.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(encuesta.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));
        cv.put(MainDBConstants.deviceId, encuesta.getDeviceid());

        return cv;
    }

    public static EncuestaPesoTalla crearEncuestaPesoTalla(Cursor cursor){
        EncuestaPesoTalla mEencuesta = new EncuestaPesoTalla();

        mEencuesta.setParticipante(null);
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso1)) > 0) mEencuesta.setPeso1(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso1)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso2)) > 0) mEencuesta.setPeso2(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso2)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso3)) > 0) mEencuesta.setPeso3(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso3)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla1)) > 0) mEencuesta.setTalla1(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla1)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla2)) > 0) mEencuesta.setTalla2(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla2)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla3)) > 0) mEencuesta.setTalla3(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla3)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc1)) > 0) mEencuesta.setImc1(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc1)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc2)) > 0) mEencuesta.setImc2(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc2)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc3)) > 0) mEencuesta.setImc3(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc3)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.difPeso)) > 0) mEencuesta.setDifPeso(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.difPeso)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.difTalla)) > 0) mEencuesta.setDifTalla(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.difTalla)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)) > 0) mEencuesta.setOtrorecurso1(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso2)) > 0) mEencuesta.setOtrorecurso2(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso2)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mEencuesta.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mEencuesta.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mEencuesta.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mEencuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mEencuesta.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mEencuesta;
    }
}
