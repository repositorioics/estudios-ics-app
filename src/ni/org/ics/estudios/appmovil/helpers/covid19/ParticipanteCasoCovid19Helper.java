package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

public class ParticipanteCasoCovid19Helper {
    public static ContentValues crearParticipanteCasoCovid19ContentValues(ParticipanteCasoCovid19 participanteCaso){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.codigoCasoParticipante, participanteCaso.getCodigoCasoParticipante());
        cv.put(Covid19DBConstants.codigoCaso, participanteCaso.getCodigoCaso().getCodigoCaso());
        cv.put(Covid19DBConstants.participante, participanteCaso.getParticipante().getCodigo());
        cv.put(Covid19DBConstants.enfermo, participanteCaso.getEnfermo());
        cv.put(Covid19DBConstants.consentimiento, participanteCaso.getConsentimiento());
        cv.put(Covid19DBConstants.positivoPor, participanteCaso.getPositivoPor());
        if (participanteCaso.getFis() != null) cv.put(Covid19DBConstants.fis, participanteCaso.getFis().getTime());
        if (participanteCaso.getFif() != null) cv.put(Covid19DBConstants.fif, participanteCaso.getFif().getTime());

        if (participanteCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, participanteCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, participanteCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(participanteCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(participanteCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, participanteCaso.getDeviceid());
        return cv;
    }

    public static ParticipanteCasoCovid19 crearParticipanteCasoCovid19(Cursor cursor){
        ParticipanteCasoCovid19 mParticipanteCasoCovid19 = new ParticipanteCasoCovid19();

        mParticipanteCasoCovid19.setCodigoCasoParticipante(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCasoParticipante)));
        mParticipanteCasoCovid19.setCodigoCaso(null);
        mParticipanteCasoCovid19.setParticipante(null);
        mParticipanteCasoCovid19.setEnfermo(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.enfermo)));
        mParticipanteCasoCovid19.setConsentimiento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.consentimiento)));
        mParticipanteCasoCovid19.setPositivoPor(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.positivoPor)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fis))>0) mParticipanteCasoCovid19.setFis(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fis))));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fif))>0) mParticipanteCasoCovid19.setFif(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fif))));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mParticipanteCasoCovid19.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mParticipanteCasoCovid19.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mParticipanteCasoCovid19.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mParticipanteCasoCovid19.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mParticipanteCasoCovid19.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mParticipanteCasoCovid19;
    }

    public static void fillParticipanteCasoCovid19Statement(SQLiteStatement stat, ParticipanteCasoCovid19 participanteCaso){
        if (participanteCaso.getCodigoCasoParticipante() != null) stat.bindString(1, participanteCaso.getCodigoCasoParticipante());
        bindString(stat,2, participanteCaso.getCodigoCaso().getCodigoCaso());
        stat.bindLong(3, participanteCaso.getParticipante().getCodigo());
        bindString(stat,4, participanteCaso.getEnfermo());
        bindString(stat,5, participanteCaso.getPositivoPor());
        bindDate(stat,6, participanteCaso.getFis());
        bindDate(stat,7, participanteCaso.getFif());
        bindString(stat,8, participanteCaso.getConsentimiento());
        bindDate(stat,9, participanteCaso.getRecordDate());
        bindString(stat,10, participanteCaso.getRecordUser());
        stat.bindString(11, String.valueOf(participanteCaso.getPasive()));
        bindString(stat,12, participanteCaso.getDeviceid());
        stat.bindString(13, String.valueOf(participanteCaso.getEstado()));
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
}
