package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaFallidaCaso;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by William Aviles 6/9/2017.
 * V1.0
 */
public class VisitaFallidaCasoHelper {

    public static ContentValues crearVisitaFallidaCasoContentValues(VisitaFallidaCaso visitaFallida){
        ContentValues cv = new ContentValues();
        cv.put(CasosDBConstants.codigoFallaVisita, visitaFallida.getCodigoFallaVisita());
        cv.put(CasosDBConstants.codigoParticipanteCaso, visitaFallida.getCodigoParticipanteCaso().getCodigoCasoParticipante());        
        if (visitaFallida.getFechaVisita() != null) cv.put(CasosDBConstants.fechaVisita, visitaFallida.getFechaVisita().getTime());
        cv.put(CasosDBConstants.razonVisitaFallida, visitaFallida.getRazonVisitaFallida());
        cv.put(CasosDBConstants.otraRazon, visitaFallida.getOtraRazon());
        cv.put(CasosDBConstants.visita, visitaFallida.getVisita());
        
        if (visitaFallida.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaFallida.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, visitaFallida.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(visitaFallida.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(visitaFallida.getEstado()));
        cv.put(MainDBConstants.deviceId, visitaFallida.getDeviceid());
        return cv;
    }

    public static VisitaFallidaCaso crearVisitaFallidaCaso(Cursor cursor){
    	VisitaFallidaCaso mVisitaFallidaCaso = new VisitaFallidaCaso();
        
    	mVisitaFallidaCaso.setCodigoFallaVisita(cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoFallaVisita)));
    	mVisitaFallidaCaso.setCodigoParticipanteCaso(null);
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaVisita))>0) mVisitaFallidaCaso.setFechaVisita(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaVisita))));
    	mVisitaFallidaCaso.setRazonVisitaFallida(cursor.getString(cursor.getColumnIndex(CasosDBConstants.razonVisitaFallida)));
    	mVisitaFallidaCaso.setOtraRazon(cursor.getString(cursor.getColumnIndex(CasosDBConstants.otraRazon)));
        mVisitaFallidaCaso.setVisita(cursor.getString(cursor.getColumnIndex(CasosDBConstants.visita)));
        
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mVisitaFallidaCaso.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mVisitaFallidaCaso.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mVisitaFallidaCaso.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mVisitaFallidaCaso.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mVisitaFallidaCaso.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mVisitaFallidaCaso;
    }

    public static void fillVisitaFallidaCasoStatement(SQLiteStatement stat, VisitaFallidaCaso visitaFallida){
        stat.bindString(1, visitaFallida.getCodigoFallaVisita());
        stat.bindString(2, visitaFallida.getCodigoParticipanteCaso().getCodigoCasoParticipante());
        if (visitaFallida.getFechaVisita() != null) stat.bindLong(3, visitaFallida.getFechaVisita().getTime());
        if (visitaFallida.getRazonVisitaFallida() != null) stat.bindString(4, visitaFallida.getRazonVisitaFallida());
        if (visitaFallida.getOtraRazon() != null) stat.bindString(5, visitaFallida.getOtraRazon());
        if (visitaFallida.getVisita() != null) stat.bindString(6, visitaFallida.getVisita());

        if (visitaFallida.getRecordDate() != null) stat.bindLong(7, visitaFallida.getRecordDate().getTime());
        if (visitaFallida.getRecordUser() != null) stat.bindString(8, visitaFallida.getRecordUser());
        stat.bindString(9, String.valueOf(visitaFallida.getPasive()));
        if (visitaFallida.getDeviceid() != null) stat.bindString(10, visitaFallida.getDeviceid());
        stat.bindString(11, String.valueOf(visitaFallida.getEstado()));
    }
}
