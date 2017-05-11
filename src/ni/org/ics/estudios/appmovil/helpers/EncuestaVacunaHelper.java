package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaDatosPartoBB;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaVacuna;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class EncuestaVacunaHelper {

    public static ContentValues crearEncuestaVacunaContentValues(EncuestaVacuna encuesta){
        ContentValues cv = new ContentValues();
        cv.put(EncuestasDBConstants.participante, encuesta.getParticipante().getCodigo());
        cv.put(EncuestasDBConstants.vacuna, encuesta.getVacuna());
        if (encuesta.getFechaVac() != null ) cv.put(EncuestasDBConstants.fechaVac, encuesta.getFechaVac().getTime());
        cv.put(EncuestasDBConstants.tipovacuna, encuesta.getTipovacuna());
        cv.put(EncuestasDBConstants.tarjetaSN, encuesta.getTarjetaSN());
        cv.put(EncuestasDBConstants.ndosis, encuesta.getNdosis());
        if (encuesta.getFechaInf1() != null ) cv.put(EncuestasDBConstants.fechaInf1, encuesta.getFechaInf1().getTime());
        if (encuesta.getFechaInf2() != null ) cv.put(EncuestasDBConstants.fechaInf2, encuesta.getFechaInf2().getTime());
        if (encuesta.getFechaInf3() != null ) cv.put(EncuestasDBConstants.fechaInf3, encuesta.getFechaInf3().getTime());
        if (encuesta.getFechaInf4() != null ) cv.put(EncuestasDBConstants.fechaInf4, encuesta.getFechaInf4().getTime());
        if (encuesta.getFechaInf5() != null ) cv.put(EncuestasDBConstants.fechaInf5, encuesta.getFechaInf5().getTime());
        if (encuesta.getFechaInf6() != null ) cv.put(EncuestasDBConstants.fechaInf6, encuesta.getFechaInf6().getTime());
        if (encuesta.getFechaInf7() != null ) cv.put(EncuestasDBConstants.fechaInf7, encuesta.getFechaInf7().getTime());
        if (encuesta.getFechaInf8() != null ) cv.put(EncuestasDBConstants.fechaInf8, encuesta.getFechaInf8().getTime());
        if (encuesta.getFechaInf9() != null ) cv.put(EncuestasDBConstants.fechaInf9, encuesta.getFechaInf9().getTime());
        if (encuesta.getFechaInf10() != null ) cv.put(EncuestasDBConstants.fechaInf10, encuesta.getFechaInf10().getTime());
        cv.put(EncuestasDBConstants.otrorecurso1, encuesta.getOtrorecurso1());
        if (encuesta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, encuesta.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, encuesta.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(encuesta.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));
        cv.put(MainDBConstants.deviceId, encuesta.getDeviceid());

        return cv;
    }

    public static EncuestaVacuna crearEncuestaVacuna(Cursor cursor){
        EncuestaVacuna mEncuesta = new EncuestaVacuna();

        mEncuesta.setParticipante(null);
        if ( cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.vacuna)) > 0) mEncuesta.setVacuna(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.vacuna)));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaVac)) > 0) mEncuesta.setFechaVac(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaVac))));
        mEncuesta.setTipovacuna(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tipovacuna)));
        if ( cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tarjetaSN)) > 0) mEncuesta.setTarjetaSN(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.tarjetaSN)));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.ndosis)) > 0) mEncuesta.setNdosis(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.ndosis)));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf1)) > 0) mEncuesta.setFechaInf1(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf1))));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf2)) > 0) mEncuesta.setFechaInf2(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf2))));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf3)) > 0) mEncuesta.setFechaInf3(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf3))));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf4)) > 0) mEncuesta.setFechaInf4(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf4))));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf5)) > 0) mEncuesta.setFechaInf5(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf5))));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf6)) > 0) mEncuesta.setFechaInf6(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf6))));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf7)) > 0) mEncuesta.setFechaInf7(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf7))));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf8)) > 0) mEncuesta.setFechaInf8(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf8))));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf9)) > 0) mEncuesta.setFechaInf9(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf9))));
        if ( cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf10)) > 0) mEncuesta.setFechaInf10(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaInf10))));
        if ( cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)) > 0) mEncuesta.setOtrorecurso1(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.otrorecurso1)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mEncuesta.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mEncuesta.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mEncuesta.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mEncuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mEncuesta.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mEncuesta;
    }
}
