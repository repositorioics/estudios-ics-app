package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaDatosPartoBB;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaLactanciaMaterna;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class EncuestaLactanciaMatHelper {

    public static ContentValues crearEncuestaLactanciaMaternaContentValues(EncuestaLactanciaMaterna encuesta){
        ContentValues cv = new ContentValues();
        cv.put(EncuestasDBConstants.participante, encuesta.getParticipante().getCodigo());
        cv.put(EncuestasDBConstants.edad, encuesta.getEdad());
        cv.put(EncuestasDBConstants.dioPecho, encuesta.getDioPecho());
        cv.put(EncuestasDBConstants.tiemPecho, encuesta.getTiemPecho());
        cv.put(EncuestasDBConstants.mesDioPecho, encuesta.getMesDioPecho());
        cv.put(EncuestasDBConstants.pechoExc, encuesta.getPechoExc());
        cv.put(EncuestasDBConstants.pechoExcAntes, encuesta.getPechoExcAntes());
        cv.put(EncuestasDBConstants.tiempPechoExcAntes, encuesta.getTiempPechoExcAntes());
        cv.put(EncuestasDBConstants.mestPechoExc, encuesta.getMestPechoExc());
        cv.put(EncuestasDBConstants.formAlim, encuesta.getFormAlim());
        cv.put(EncuestasDBConstants.otraAlim, encuesta.getOtraAlim());
        cv.put(EncuestasDBConstants.edadLiqDistPecho, encuesta.getEdadLiqDistPecho());
        cv.put(EncuestasDBConstants.mesDioLiqDisPecho, encuesta.getMesDioLiqDisPecho());
        cv.put(EncuestasDBConstants.edadLiqDistLeche, encuesta.getEdadLiqDistLeche());
        cv.put(EncuestasDBConstants.mesDioLiqDisLeche, encuesta.getMesDioLiqDisLeche());
        cv.put(EncuestasDBConstants.edAlimSolidos, encuesta.getEdAlimSolidos());
        cv.put(EncuestasDBConstants.mesDioAlimSol, encuesta.getMesDioAlimSol());
        cv.put(EncuestasDBConstants.otrorecurso1, encuesta.getOtrorecurso1());
        cv.put(EncuestasDBConstants.otrorecurso2, encuesta.getOtrorecurso2());

        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));

        return cv;
    }

    public static EncuestaLactanciaMaterna crearEncuestaLactanciaMaterna(Cursor cursor){
        EncuestaLactanciaMaterna encuesta = new EncuestaLactanciaMaterna();

        encuesta.setParticipante(null);
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edad)) > 0) encuesta.setEdad(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edad)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.dioPecho)) > 0) encuesta.setDioPecho(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.dioPecho)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiemPecho)) > 0) encuesta.setTiemPecho(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiemPecho)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioPecho)) > 0) encuesta.setMesDioPecho(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioPecho)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.pechoExc)) > 0) encuesta.setPechoExc(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.pechoExc)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.pechoExcAntes)) > 0) encuesta.setPechoExcAntes(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.pechoExcAntes)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiempPechoExcAntes)) > 0) encuesta.setTiempPechoExcAntes(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiempPechoExcAntes)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mestPechoExc)) > 0) encuesta.setMestPechoExc(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mestPechoExc)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.formAlim)) > 0) encuesta.setFormAlim(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.formAlim)));
        encuesta.setOtraAlim(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otraAlim)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadLiqDistPecho)) > 0) encuesta.setEdadLiqDistPecho(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadLiqDistPecho)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioLiqDisPecho)) > 0) encuesta.setMesDioLiqDisPecho(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioLiqDisPecho)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadLiqDistLeche)) > 0) encuesta.setEdadLiqDistLeche(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadLiqDistLeche)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioLiqDisLeche)) > 0) encuesta.setMesDioLiqDisLeche(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioLiqDisLeche)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edAlimSolidos)) > 0) encuesta.setEdAlimSolidos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edAlimSolidos)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioAlimSol)) > 0) encuesta.setMesDioAlimSol(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioAlimSol)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)) > 0) encuesta.setOtrorecurso1(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso2)) > 0) encuesta.setOtrorecurso2(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso2)));
        encuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));

        return encuesta;
    }
}
