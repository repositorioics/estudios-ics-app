package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaFallidaCaso;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaFallidaCasoCovid19;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by William Aviles 6/9/2017.
 * V1.0
 */
public class VisitaFallidaCasoCovid19Helper {

    public static ContentValues crearVisitaFallidaCasoCovid19ContentValues(VisitaFallidaCasoCovid19 visitaFallida){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.codigoFallaVisita, visitaFallida.getCodigoFallaVisita());
        cv.put(Covid19DBConstants.codigoParticipanteCaso, visitaFallida.getCodigoParticipanteCaso().getCodigoCasoParticipante());        
        if (visitaFallida.getFechaVisita() != null) cv.put(Covid19DBConstants.fechaVisita, visitaFallida.getFechaVisita().getTime());
        cv.put(Covid19DBConstants.razonVisitaFallida, visitaFallida.getRazonVisitaFallida());
        cv.put(Covid19DBConstants.otraRazon, visitaFallida.getOtraRazon());
        cv.put(Covid19DBConstants.visita, visitaFallida.getVisita());
        
        if (visitaFallida.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaFallida.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, visitaFallida.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(visitaFallida.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(visitaFallida.getEstado()));
        cv.put(MainDBConstants.deviceId, visitaFallida.getDeviceid());
        return cv;
    }

    public static VisitaFallidaCasoCovid19 crearVisitaFallidaCasoCovid19(Cursor cursor){
        VisitaFallidaCasoCovid19 mVisitaFallidaCaso = new VisitaFallidaCasoCovid19();
        
    	mVisitaFallidaCaso.setCodigoFallaVisita(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoFallaVisita)));
    	mVisitaFallidaCaso.setCodigoParticipanteCaso(null);
    	if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaVisita))>0) mVisitaFallidaCaso.setFechaVisita(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaVisita))));
    	mVisitaFallidaCaso.setRazonVisitaFallida(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.razonVisitaFallida)));
    	mVisitaFallidaCaso.setOtraRazon(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.otraRazon)));
        mVisitaFallidaCaso.setVisita(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.visita)));
        
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mVisitaFallidaCaso.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mVisitaFallidaCaso.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mVisitaFallidaCaso.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mVisitaFallidaCaso.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mVisitaFallidaCaso.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mVisitaFallidaCaso;
    }

    public static void fillVisitaFallidaCasoCovid19Statement(SQLiteStatement stat, VisitaFallidaCasoCovid19 visitaFallida){
        if (visitaFallida.getCodigoFallaVisita() != null) stat.bindString(1, visitaFallida.getCodigoFallaVisita());
        if (visitaFallida.getCodigoParticipanteCaso() != null) stat.bindString(2, visitaFallida.getCodigoParticipanteCaso().getCodigoCasoParticipante());
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
