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
        bindString(stat,2, visitaCaso.getCodigoParticipanteCaso().getCodigoCasoParticipante());
        bindDate(stat,3, visitaCaso.getFechaVisita());
        bindString(stat,4, visitaCaso.getVisita());
        bindString(stat,5, visitaCaso.getHoraProbableVisita());
        bindString(stat,6, visitaCaso.getExpCS());
        bindFloat(stat,7, visitaCaso.getTemp());
        bindInteger(stat,8, visitaCaso.getSaturacionO2());
        bindInteger(stat,9, visitaCaso.getFrecResp());
        bindDate(stat,10, visitaCaso.getFecIniPrimerSintoma());
        bindString(stat,11, visitaCaso.getPrimerSintoma());

        bindDate(stat,12, visitaCaso.getRecordDate());
        bindString(stat,13, visitaCaso.getRecordUser());
        stat.bindString(14, String.valueOf(visitaCaso.getPasive()));
        bindString(stat,15, visitaCaso.getDeviceid());
        stat.bindString(16, String.valueOf(visitaCaso.getEstado()));
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

    public static void bindFloat(SQLiteStatement stat, int index, Float value){
        if (value == null) {
            stat.bindNull(index);
        } else {
            stat.bindDouble(index, value);
        }
    }
}
