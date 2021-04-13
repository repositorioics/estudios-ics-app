package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by William Aviles 6/9/2017.
 * V1.0
 */
public class ParticipanteCohorteFamiliaCasoHelper {

    public static ContentValues crearParticipanteCohorteFamiliaCasoContentValues(ParticipanteCohorteFamiliaCaso participanteCaso){
        ContentValues cv = new ContentValues();
        cv.put(CasosDBConstants.codigoCasoParticipante, participanteCaso.getCodigoCasoParticipante());
        cv.put(CasosDBConstants.codigoCaso, participanteCaso.getCodigoCaso().getCodigoCaso());
        cv.put(CasosDBConstants.participante, participanteCaso.getParticipante().getParticipante().getCodigo());
        cv.put(CasosDBConstants.enfermo, participanteCaso.getEnfermo());
        if (participanteCaso.getFechaEnfermedad() != null) cv.put(CasosDBConstants.fechaEnfermedad, participanteCaso.getFechaEnfermedad().getTime());
        if (participanteCaso.getFis() != null) cv.put(CasosDBConstants.fis, participanteCaso.getFis().getTime());
        
        if (participanteCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, participanteCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, participanteCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(participanteCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(participanteCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, participanteCaso.getDeviceid());
        return cv;
    }

    public static ParticipanteCohorteFamiliaCaso crearParticipanteCohorteFamiliaCaso(Cursor cursor){
    	ParticipanteCohorteFamiliaCaso mParticipanteCohorteFamiliaCaso = new ParticipanteCohorteFamiliaCaso();
        
    	mParticipanteCohorteFamiliaCaso.setCodigoCasoParticipante(cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCasoParticipante)));
    	mParticipanteCohorteFamiliaCaso.setCodigoCaso(null);
        mParticipanteCohorteFamiliaCaso.setParticipante(null);
        mParticipanteCohorteFamiliaCaso.setEnfermo(cursor.getString(cursor.getColumnIndex(CasosDBConstants.enfermo)));
        if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaEnfermedad))>0) mParticipanteCohorteFamiliaCaso.setFechaEnfermedad(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaEnfermedad))));
        if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fis))>0) mParticipanteCohorteFamiliaCaso.setFis(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fis))));
        
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mParticipanteCohorteFamiliaCaso.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mParticipanteCohorteFamiliaCaso.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mParticipanteCohorteFamiliaCaso.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mParticipanteCohorteFamiliaCaso.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mParticipanteCohorteFamiliaCaso.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mParticipanteCohorteFamiliaCaso;
    }

    public static void fillParticipanteCohorteFamiliaCasoStatement(SQLiteStatement stat, ParticipanteCohorteFamiliaCaso participanteCaso){
        stat.bindString(1, participanteCaso.getCodigoCasoParticipante());
        stat.bindString(2, participanteCaso.getCodigoCaso().getCodigoCaso());
        stat.bindLong(3, participanteCaso.getParticipante().getParticipante().getCodigo());
        bindString(stat,4, participanteCaso.getEnfermo());
        bindDate(stat,5, participanteCaso.getFechaEnfermedad());
        bindDate(stat,6, participanteCaso.getFis());
        bindDate(stat,7, participanteCaso.getRecordDate());
        bindString(stat,8, participanteCaso.getRecordUser());
        stat.bindString(9, String.valueOf(participanteCaso.getPasive()));
        bindString(stat,10, participanteCaso.getDeviceid());
        stat.bindString(11, String.valueOf(participanteCaso.getEstado()));
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
