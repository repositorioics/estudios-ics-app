package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCaso;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaSeguimientoCasoCovid19;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas 12/06/2020.
 * V1.0
 */
public class VisitaSeguimientoCasoCovid19Helper {

    public static ContentValues crearVisitaSeguimientoCasoCovid19ContentValues(VisitaSeguimientoCasoCovid19 visitaCaso){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.codigoCasoVisita, visitaCaso.getCodigoCasoVisita());
        cv.put(Covid19DBConstants.codigoCasoParticipante, visitaCaso.getCodigoParticipanteCaso().getCodigoCasoParticipante());        
        if (visitaCaso.getFechaVisita() != null) cv.put(Covid19DBConstants.fechaVisita, visitaCaso.getFechaVisita().getTime());
        cv.put(Covid19DBConstants.visita, visitaCaso.getVisita());
        cv.put(Covid19DBConstants.horaProbableVisita, visitaCaso.getHoraProbableVisita());
        cv.put(Covid19DBConstants.expCS, visitaCaso.getExpCS());
        cv.put(Covid19DBConstants.temp, visitaCaso.getTemp());
        cv.put(Covid19DBConstants.frecResp, visitaCaso.getFrecResp());
        cv.put(Covid19DBConstants.saturacionO2, visitaCaso.getSaturacionO2());
        if (visitaCaso.getFecIniPrimerSintoma() != null) cv.put(Covid19DBConstants.fecIniPrimerSintoma, visitaCaso.getFecIniPrimerSintoma().getTime());
        cv.put(Covid19DBConstants.primerSintoma, visitaCaso.getPrimerSintoma());

        if (visitaCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, visitaCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(visitaCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(visitaCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, visitaCaso.getDeviceid());
        return cv;
    }

    public static VisitaSeguimientoCasoCovid19 crearVisitaSeguimientoCasoCovid19(Cursor cursor){
    	VisitaSeguimientoCasoCovid19 mVisitaSeguimientoCaso = new VisitaSeguimientoCasoCovid19();
        
    	mVisitaSeguimientoCaso.setCodigoCasoVisita(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCasoVisita)));
    	mVisitaSeguimientoCaso.setCodigoParticipanteCaso(null);
    	if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaVisita))>0) mVisitaSeguimientoCaso.setFechaVisita(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaVisita))));
    	mVisitaSeguimientoCaso.setVisita(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.visita)));
    	mVisitaSeguimientoCaso.setHoraProbableVisita(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.horaProbableVisita)));
        mVisitaSeguimientoCaso.setExpCS(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.expCS)));
        mVisitaSeguimientoCaso.setTemp(cursor.getFloat(cursor.getColumnIndex(Covid19DBConstants.temp)));
        mVisitaSeguimientoCaso.setFrecResp(cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.frecResp)));
        mVisitaSeguimientoCaso.setSaturacionO2(cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.saturacionO2)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fecIniPrimerSintoma))>0) mVisitaSeguimientoCaso.setFecIniPrimerSintoma(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fecIniPrimerSintoma))));
        mVisitaSeguimientoCaso.setPrimerSintoma(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.primerSintoma)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mVisitaSeguimientoCaso.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mVisitaSeguimientoCaso.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mVisitaSeguimientoCaso.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mVisitaSeguimientoCaso.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mVisitaSeguimientoCaso.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mVisitaSeguimientoCaso;
    }

    public static void fillVisitaSeguimientoCasoCovid19Statement(SQLiteStatement stat, VisitaSeguimientoCasoCovid19 visitaCaso){
        if (visitaCaso.getCodigoCasoVisita() != null) stat.bindString(1, visitaCaso.getCodigoCasoVisita());
        if (visitaCaso.getCodigoParticipanteCaso() != null) stat.bindString(2, visitaCaso.getCodigoParticipanteCaso().getCodigoCasoParticipante());
        if (visitaCaso.getFechaVisita() != null) stat.bindLong(3, visitaCaso.getFechaVisita().getTime());
        if (visitaCaso.getVisita() != null) stat.bindString(4, visitaCaso.getVisita());
        if (visitaCaso.getHoraProbableVisita() != null) stat.bindString(5, visitaCaso.getHoraProbableVisita());
        if (visitaCaso.getExpCS() != null) stat.bindString(6, visitaCaso.getExpCS());
        if (visitaCaso.getTemp() != null) stat.bindDouble(7, visitaCaso.getTemp());
        if (visitaCaso.getSaturacionO2() != null) stat.bindLong(8, visitaCaso.getSaturacionO2());
        if (visitaCaso.getFrecResp() != null) stat.bindLong(9, visitaCaso.getFrecResp());
        if (visitaCaso.getFecIniPrimerSintoma() != null) stat.bindLong(10, visitaCaso.getFecIniPrimerSintoma().getTime());
        if (visitaCaso.getPrimerSintoma() != null) stat.bindString(11, visitaCaso.getPrimerSintoma());

        if (visitaCaso.getRecordDate() != null) stat.bindLong(12, visitaCaso.getRecordDate().getTime());
        if (visitaCaso.getRecordUser() != null) stat.bindString(13, visitaCaso.getRecordUser());
        stat.bindString(14, String.valueOf(visitaCaso.getPasive()));
        if (visitaCaso.getDeviceid() != null) stat.bindString(15, visitaCaso.getDeviceid());
        stat.bindString(16, String.valueOf(visitaCaso.getEstado()));
    }

}
