package ni.org.ics.estudios.appmovil.helpers.influenzauo1;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaFallidaCaso;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaCasoUO1;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.InfluenzaUO1DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas 22/08/2019.
 * V1.0
 */
public class VisitaCasoUO1Helper {

    public static ContentValues crearVisitaCasoUO1ContentValues(VisitaCasoUO1 visitaCasoUO1){
        ContentValues cv = new ContentValues();
        cv.put(InfluenzaUO1DBConstants.codigoCasoVisita, visitaCasoUO1.getCodigoCasoVisita());
        cv.put(InfluenzaUO1DBConstants.participanteCasoUO1, visitaCasoUO1.getParticipanteCasoUO1().getCodigoCasoParticipante());
        if (visitaCasoUO1.getFechaVisita() != null) cv.put(InfluenzaUO1DBConstants.fechaVisita, visitaCasoUO1.getFechaVisita().getTime());
        cv.put(InfluenzaUO1DBConstants.visita, visitaCasoUO1.getVisita());
        cv.put(InfluenzaUO1DBConstants.lugar, visitaCasoUO1.getLugar());
        cv.put(InfluenzaUO1DBConstants.visitaExitosa, visitaCasoUO1.getVisitaExitosa());
        cv.put(InfluenzaUO1DBConstants.razonVisitaFallida, visitaCasoUO1.getRazonVisitaFallida());
        cv.put(InfluenzaUO1DBConstants.otraRazon, visitaCasoUO1.getOtraRazon());
        cv.put(InfluenzaUO1DBConstants.positivoPor, visitaCasoUO1.getPositivoPor());
        if (visitaCasoUO1.getFif() != null) cv.put(InfluenzaUO1DBConstants.fif, visitaCasoUO1.getFif().getTime());
        cv.put(InfluenzaUO1DBConstants.vacunaFlu3Semanas, visitaCasoUO1.getVacunaFlu3Semanas());
        if (visitaCasoUO1.getFechaVacuna() != null) cv.put(InfluenzaUO1DBConstants.fechaVacuna, visitaCasoUO1.getFechaVacuna().getTime());
        
        if (visitaCasoUO1.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaCasoUO1.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, visitaCasoUO1.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(visitaCasoUO1.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(visitaCasoUO1.getEstado()));
        cv.put(MainDBConstants.deviceId, visitaCasoUO1.getDeviceid());
        return cv;
    }

    public static VisitaCasoUO1 crearVisitaCasoUO1(Cursor cursor){
        VisitaCasoUO1 mVisitaCasoUO1 = new VisitaCasoUO1();
        
    	mVisitaCasoUO1.setCodigoCasoVisita(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.codigoCasoVisita)));
    	mVisitaCasoUO1.setParticipanteCasoUO1(null);
    	if(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaVisita))>0) mVisitaCasoUO1.setFechaVisita(new Date(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaVisita))));
    	mVisitaCasoUO1.setVisita(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.visita)));
        mVisitaCasoUO1.setLugar(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.lugar)));
        mVisitaCasoUO1.setVisitaExitosa(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.visitaExitosa)));
        mVisitaCasoUO1.setRazonVisitaFallida(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.razonVisitaFallida)));
        mVisitaCasoUO1.setOtraRazon(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.otraRazon)));
        mVisitaCasoUO1.setPositivoPor(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.positivoPor)));
        if(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fif))>0) mVisitaCasoUO1.setFif(new Date(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fif))));
        mVisitaCasoUO1.setVacunaFlu3Semanas(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.vacunaFlu3Semanas)));
        if(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaVacuna))>0) mVisitaCasoUO1.setFechaVacuna(new Date(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaVacuna))));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mVisitaCasoUO1.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mVisitaCasoUO1.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mVisitaCasoUO1.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mVisitaCasoUO1.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mVisitaCasoUO1.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mVisitaCasoUO1;
    }

    public static void fillVisitaCasoUO1Statement(SQLiteStatement stat, VisitaCasoUO1 visitaCasoUO1){
        stat.bindString(1, visitaCasoUO1.getCodigoCasoVisita());
        stat.bindString(2, visitaCasoUO1.getParticipanteCasoUO1().getCodigoCasoParticipante());
        bindDate(stat,3, visitaCasoUO1.getFechaVisita());
        bindString(stat,4, visitaCasoUO1.getVisita());
        bindString(stat,5, visitaCasoUO1.getLugar());
        bindString(stat,6, visitaCasoUO1.getVisitaExitosa());
        bindString(stat,7, visitaCasoUO1.getRazonVisitaFallida());
        bindString(stat,8, visitaCasoUO1.getOtraRazon());
        bindString(stat,9, visitaCasoUO1.getPositivoPor());
        bindDate(stat,10, visitaCasoUO1.getFif());
        bindString(stat,11, visitaCasoUO1.getVacunaFlu3Semanas());
        bindDate(stat,12, visitaCasoUO1.getFechaVacuna());

        bindDate(stat,13, visitaCasoUO1.getRecordDate());
        bindString(stat,14, visitaCasoUO1.getRecordUser());
        stat.bindString(15, String.valueOf(visitaCasoUO1.getPasive()));
        bindString(stat,16, visitaCasoUO1.getDeviceid());
        stat.bindString(17, String.valueOf(visitaCasoUO1.getEstado()));
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
