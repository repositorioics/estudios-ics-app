package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaLactanciaMaterna;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class EncuestaLactanciaMatHelper {

    public static ContentValues crearEncuestaLactanciaMaternaContentValues(EncuestaLactanciaMaterna encuesta){
        ContentValues cv = new ContentValues();
        cv.put(EncuestasDBConstants.participante_chf, encuesta.getParticipante().getCodigo());
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
        cv.put(EncuestasDBConstants.recurso1, encuesta.getRecurso1());
        cv.put(EncuestasDBConstants.otrorecurso1, encuesta.getOtrorecurso1());

        if (encuesta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, encuesta.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, encuesta.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(encuesta.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));
        cv.put(MainDBConstants.deviceId, encuesta.getDeviceid());

        return cv;
    }

    public static EncuestaLactanciaMaterna crearEncuestaLactanciaMaterna(Cursor cursor){
        EncuestaLactanciaMaterna mEncuesta = new EncuestaLactanciaMaterna();

        mEncuesta.setParticipante(null);
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edad)) > 0) mEncuesta.setEdad(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edad)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.dioPecho)) > 0) mEncuesta.setDioPecho(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.dioPecho)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiemPecho)) > 0) mEncuesta.setTiemPecho(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiemPecho)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioPecho)) > 0) mEncuesta.setMesDioPecho(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioPecho)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.pechoExc)) > 0) mEncuesta.setPechoExc(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.pechoExc)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.pechoExcAntes)) > 0) mEncuesta.setPechoExcAntes(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.pechoExcAntes)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiempPechoExcAntes)) > 0) mEncuesta.setTiempPechoExcAntes(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tiempPechoExcAntes)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mestPechoExc)) > 0) mEncuesta.setMestPechoExc(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mestPechoExc)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.formAlim)) > 0) mEncuesta.setFormAlim(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.formAlim)));
        mEncuesta.setOtraAlim(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otraAlim)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadLiqDistPecho)) > 0) mEncuesta.setEdadLiqDistPecho(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadLiqDistPecho)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioLiqDisPecho)) > 0) mEncuesta.setMesDioLiqDisPecho(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioLiqDisPecho)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadLiqDistLeche)) > 0) mEncuesta.setEdadLiqDistLeche(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edadLiqDistLeche)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioLiqDisLeche)) > 0) mEncuesta.setMesDioLiqDisLeche(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioLiqDisLeche)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edAlimSolidos)) > 0) mEncuesta.setEdAlimSolidos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.edAlimSolidos)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioAlimSol)) > 0) mEncuesta.setMesDioAlimSol(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.mesDioAlimSol)));
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
