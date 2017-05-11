package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaDatosPartoBB;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaPesoTalla;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class EncuestaPesoTallaHelper {

    public static ContentValues crearEncuestaPesoTallaContentValues(EncuestaPesoTalla encuesta){
        ContentValues cv = new ContentValues();

        cv.put(EncuestasDBConstants.participante, encuesta.getParticipante().getCodigo());
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
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));

        return cv;
    }

    public static EncuestaPesoTalla crearEncuestaPesoTalla(Cursor cursor){
        EncuestaPesoTalla encuesta = new EncuestaPesoTalla();

        encuesta.setParticipante(null);
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso1)) > 0) encuesta.setPeso1(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso1)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso2)) > 0) encuesta.setPeso2(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso2)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso3)) > 0) encuesta.setPeso3(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.peso3)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla1)) > 0) encuesta.setTalla1(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla1)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla2)) > 0) encuesta.setTalla2(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla2)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla3)) > 0) encuesta.setTalla3(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.talla3)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc1)) > 0) encuesta.setImc1(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc1)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc2)) > 0) encuesta.setImc2(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc2)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc3)) > 0) encuesta.setImc3(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.imc3)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.difPeso)) > 0) encuesta.setDifPeso(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.difPeso)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.difTalla)) > 0) encuesta.setDifTalla(cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.difTalla)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)) > 0) encuesta.setOtrorecurso1(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)));
        if (cursor.getDouble(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso2)) > 0) encuesta.setOtrorecurso2(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso2)));
        encuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));

        return encuesta;
    }
}
