package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/9/2017.
 * V1.0
 */
public class ParticipanteCohorteFamiliaHelper {

    public static ContentValues crearParticipanteCohorteFamiliaContentValues(ParticipanteCohorteFamilia participanteCHF){
        ContentValues cv = new ContentValues();

        cv.put(MainDBConstants.participante, participanteCHF.getParticipante().getCodigo());
        if (participanteCHF.getCasaCHF() != null) cv.put(MainDBConstants.casaCHF, participanteCHF.getCasaCHF().getCodigoCHF());
        if (participanteCHF.getParticipante() != null) cv.put(MainDBConstants.participante, participanteCHF.getParticipante().getCodigo());
        if (participanteCHF.getRecordDate() != null) cv.put(MainDBConstants.recordDate, participanteCHF.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, participanteCHF.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(participanteCHF.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(participanteCHF.getEstado()));
        cv.put(MainDBConstants.deviceId, participanteCHF.getDeviceid());

        return cv;
    }

    public static ParticipanteCohorteFamilia crearParticipanteCohorteFamilia(Cursor cursor){
    	ParticipanteCohorteFamilia mParticipanteCohorteFamilia = new ParticipanteCohorteFamilia();
        mParticipanteCohorteFamilia.setCasaCHF(null);
        mParticipanteCohorteFamilia.setParticipante(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mParticipanteCohorteFamilia.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mParticipanteCohorteFamilia.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mParticipanteCohorteFamilia.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mParticipanteCohorteFamilia.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mParticipanteCohorteFamilia.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mParticipanteCohorteFamilia;
    }

    public static void fillParticipanteCohorteFamiliaStatement(SQLiteStatement stat, ParticipanteCohorteFamilia participanteCHF){
        stat.bindLong(1, participanteCHF.getParticipante().getCodigo());
        if (participanteCHF.getCasaCHF() != null) stat.bindString(2, participanteCHF.getCasaCHF().getCodigoCHF());
        if (participanteCHF.getRecordDate() != null) stat.bindLong(3, participanteCHF.getRecordDate().getTime());
        if (participanteCHF.getRecordUser() != null) stat.bindString(4, participanteCHF.getRecordUser());
        stat.bindString(5, String.valueOf(participanteCHF.getPasive()));
        if (participanteCHF.getDeviceid() != null) stat.bindString(6, participanteCHF.getDeviceid());
        stat.bindString(7, String.valueOf(participanteCHF.getEstado()));

    }
}
