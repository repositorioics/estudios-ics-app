package ni.org.ics.estudios.appmovil.helpers.seroprevalencia;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.SeroprevalenciaDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/25/2017.
 * V1.0
 */
public class ParticipanteSeroprevalenciaHelper {

    public static ContentValues crearParticipanteSeroprevalenciaContentValues(ParticipanteSeroprevalencia seroprevalencia){
        ContentValues cv = new ContentValues();

        cv.put(SeroprevalenciaDBConstants.participante, seroprevalencia.getParticipante().getCodigo());
        if (seroprevalencia.getCasaCHF() != null) cv.put(SeroprevalenciaDBConstants.casaCHF, seroprevalencia.getCasaCHF().getCodigoCHF());
        if (seroprevalencia.getParticipante() != null) cv.put(SeroprevalenciaDBConstants.participante, seroprevalencia.getParticipante().getCodigo());
        if (seroprevalencia.getRecordDate() != null) cv.put(MainDBConstants.recordDate, seroprevalencia.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, seroprevalencia.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(seroprevalencia.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(seroprevalencia.getEstado()));
        cv.put(MainDBConstants.deviceId, seroprevalencia.getDeviceid());

        return cv;
    }

    public static ParticipanteSeroprevalencia crearParticipanteSeroprevalencia(Cursor cursor){
        ParticipanteSeroprevalencia mParticipanteSeroprevalencia = new ParticipanteSeroprevalencia();

        //mParticipanteSeroprevalencia.setParticipanteSA(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.participanteSA)));
        mParticipanteSeroprevalencia.setCasaCHF(null);
        mParticipanteSeroprevalencia.setParticipante(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mParticipanteSeroprevalencia.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mParticipanteSeroprevalencia.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mParticipanteSeroprevalencia.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mParticipanteSeroprevalencia.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mParticipanteSeroprevalencia.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mParticipanteSeroprevalencia;
    }

    public static void fillParticipanteSeroprevalenciaStatement(SQLiteStatement stat, ParticipanteSeroprevalencia seroprevalencia){
        stat.bindLong(1, seroprevalencia.getParticipante().getCodigo());
        if (seroprevalencia.getCasaCHF() != null) stat.bindString(2, seroprevalencia.getCasaCHF().getCodigoCHF());
        if (seroprevalencia.getRecordDate() != null) stat.bindLong(3, seroprevalencia.getRecordDate().getTime());
        if (seroprevalencia.getRecordUser() != null) stat.bindString(4, seroprevalencia.getRecordUser());
        stat.bindString(5, String.valueOf(seroprevalencia.getPasive()));
        if (seroprevalencia.getDeviceid() != null) stat.bindString(6, seroprevalencia.getDeviceid());
        stat.bindString(7, String.valueOf(seroprevalencia.getEstado()));
    }
}
