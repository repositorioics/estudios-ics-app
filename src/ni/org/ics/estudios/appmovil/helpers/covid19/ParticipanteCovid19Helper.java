package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCovid19;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

public class ParticipanteCovid19Helper {

    public static ContentValues crearParticipanteCovid19ContentValues(ParticipanteCovid19 participanteCaso){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.participante, participanteCaso.getParticipante().getCodigo());

        if (participanteCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, participanteCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, participanteCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(participanteCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(participanteCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, participanteCaso.getDeviceid());
        return cv;
    }

    public static ParticipanteCovid19 crearParticipanteCovid19(Cursor cursor){
        ParticipanteCovid19 mParticipanteCasoCovid19 = new ParticipanteCovid19();

        mParticipanteCasoCovid19.setParticipante(null);

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mParticipanteCasoCovid19.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mParticipanteCasoCovid19.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mParticipanteCasoCovid19.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mParticipanteCasoCovid19.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mParticipanteCasoCovid19.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mParticipanteCasoCovid19;
    }

    public static void fillParticipanteCovid19Statement(SQLiteStatement stat, ParticipanteCovid19 participanteCaso){
        stat.bindLong(1, participanteCaso.getParticipante().getCodigo());
        bindDate(stat, 2, participanteCaso.getRecordDate());
        bindString(stat, 3, participanteCaso.getRecordUser());
        stat.bindString(4, String.valueOf(participanteCaso.getPasive()));
        bindString(stat, 5, participanteCaso.getDeviceid());
        stat.bindString(6, String.valueOf(participanteCaso.getEstado()));
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
