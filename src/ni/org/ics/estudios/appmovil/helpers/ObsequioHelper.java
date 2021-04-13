package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.ObsequioGeneral;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 06/11/2018.
 * V1.0
 */
public class ObsequioHelper {

    public static ContentValues crearObsequioContentValues(ObsequioGeneral obsequio){
        ContentValues cv = new ContentValues();

        cv.put(MainDBConstants.id, obsequio.getId());
        cv.put(MainDBConstants.participante, obsequio.getParticipante());
        cv.put(MainDBConstants.casa, obsequio.getCasa());
        cv.put(MainDBConstants.casaCHF, obsequio.getCasaChf());
        cv.put(MainDBConstants.seguimiento, obsequio.getSeguimiento());
        cv.put(MainDBConstants.numVisitaSeguimiento, obsequio.getNumVisitaSeguimiento());
        cv.put(MainDBConstants.motivo, obsequio.getMotivo());

        cv.put(MainDBConstants.obsequioSN, obsequio.getObsequioSN());
        cv.put(MainDBConstants.personaRecibe, obsequio.getPersonaRecibe());
        cv.put(MainDBConstants.relacionFam, obsequio.getRelacionFam());
        cv.put(MainDBConstants.otraRelacionFam, obsequio.getOtraRelacionFam());
        cv.put(MainDBConstants.telefono, obsequio.getTelefono());
        cv.put(MainDBConstants.observaciones, obsequio.getObservaciones());

        if (obsequio.getRecordDate() != null) cv.put(MainDBConstants.recordDate, obsequio.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, obsequio.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(obsequio.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(obsequio.getEstado()));
        cv.put(MainDBConstants.deviceId, obsequio.getDeviceid());

        return cv;
    }

    public static ObsequioGeneral crearObsequio(Cursor cursor){
        ObsequioGeneral mObsequio = new ObsequioGeneral();

        mObsequio.setId(cursor.getString(cursor.getColumnIndex(MainDBConstants.id)));
        mObsequio.setParticipante(cursor.getInt(cursor.getColumnIndex(MainDBConstants.participante)));
        mObsequio.setCasa(cursor.getString(cursor.getColumnIndex(MainDBConstants.casa)));
        mObsequio.setCasaChf(cursor.getString(cursor.getColumnIndex(MainDBConstants.casaCHF)));
        mObsequio.setSeguimiento(cursor.getString(cursor.getColumnIndex(MainDBConstants.seguimiento)));
        mObsequio.setNumVisitaSeguimiento(cursor.getString(cursor.getColumnIndex(MainDBConstants.numVisitaSeguimiento)));
        mObsequio.setMotivo(cursor.getString(cursor.getColumnIndex(MainDBConstants.motivo)));

        mObsequio.setObsequioSN(cursor.getInt(cursor.getColumnIndex(MainDBConstants.obsequioSN)));
        mObsequio.setPersonaRecibe(cursor.getString(cursor.getColumnIndex(MainDBConstants.personaRecibe)));
        mObsequio.setRelacionFam(cursor.getInt(cursor.getColumnIndex(MainDBConstants.relacionFam)));
        mObsequio.setOtraRelacionFam(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRelacionFam)));
        mObsequio.setTelefono(cursor.getString(cursor.getColumnIndex(MainDBConstants.telefono)));
        mObsequio.setObservaciones(cursor.getString(cursor.getColumnIndex(MainDBConstants.observaciones)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mObsequio.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mObsequio.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mObsequio.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mObsequio.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mObsequio.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mObsequio;
    }

    public static void fillObsequioStatement(SQLiteStatement stat, ObsequioGeneral obsequio){
        stat.bindString(1, obsequio.getId());
        if (obsequio.getParticipante() != null) stat.bindLong(2, obsequio.getParticipante());
        bindString(stat,3, obsequio.getCasa());
        bindString(stat,4, obsequio.getCasaChf());
        bindString(stat,5, obsequio.getSeguimiento());
        bindString(stat,6, obsequio.getNumVisitaSeguimiento());
        bindString(stat,7, obsequio.getMotivo());
        bindInteger(stat,8, obsequio.getObsequioSN());
        bindString(stat,9, obsequio.getPersonaRecibe());
        bindInteger(stat,10, obsequio.getRelacionFam());
        bindString(stat,11, obsequio.getOtraRelacionFam());
        bindString(stat,12, obsequio.getTelefono());
        bindString(stat,13, obsequio.getObservaciones());

        bindDate(stat,14, obsequio.getRecordDate());
        bindString(stat,15, obsequio.getRecordUser());
        stat.bindString(16, String.valueOf(obsequio.getPasive()));
        bindString(stat,17, obsequio.getDeviceid());
        stat.bindString(18, String.valueOf(obsequio.getEstado()));
    }

    public static void bindString(SQLiteStatement stat, int index, String value){
        if (value == null) {
            stat.bindNull(index);
        } else {
            stat.bindString(index, value);
        }
    }

    public static void bindDate(SQLiteStatement stat, int index, Date value){
        if (value == null) {
            stat.bindNull(index);
        } else {
            stat.bindLong(index, value.getTime());
        }
    }

    public static void bindInteger(SQLiteStatement stat, int index, Integer value){
        if (value == null) {
            stat.bindNull(index);
        } else {
            stat.bindLong(index, value);
        }
    }
}
