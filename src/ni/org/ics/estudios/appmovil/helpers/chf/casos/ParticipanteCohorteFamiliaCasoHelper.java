package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
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
        
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mParticipanteCohorteFamiliaCaso.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mParticipanteCohorteFamiliaCaso.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mParticipanteCohorteFamiliaCaso.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mParticipanteCohorteFamiliaCaso.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mParticipanteCohorteFamiliaCaso.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mParticipanteCohorteFamiliaCaso;
    }

}
