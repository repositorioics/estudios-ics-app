package ni.org.ics.estudios.appmovil.helpers.influenzauo1;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaVacunaUO1;
import ni.org.ics.estudios.appmovil.utils.InfluenzaUO1DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas 22/08/2019.
 * V1.0
 */
public class VisitaVacunaUO1Helper {

    public static ContentValues crearVisitaVacunaUO1ContentValues(VisitaVacunaUO1 visitaVacunaUO1){
        ContentValues cv = new ContentValues();
        cv.put(InfluenzaUO1DBConstants.codigoVisita, visitaVacunaUO1.getCodigoVisita());
        cv.put(InfluenzaUO1DBConstants.participante, visitaVacunaUO1.getParticipante().getCodigo());
        if (visitaVacunaUO1.getFechaVisita() != null) cv.put(InfluenzaUO1DBConstants.fechaVisita, visitaVacunaUO1.getFechaVisita().getTime());
        cv.put(InfluenzaUO1DBConstants.visita, visitaVacunaUO1.getVisita());
        cv.put(InfluenzaUO1DBConstants.visitaExitosa, visitaVacunaUO1.getVisitaExitosa());
        cv.put(InfluenzaUO1DBConstants.razonVisitaFallida, visitaVacunaUO1.getRazonVisitaFallida());
        cv.put(InfluenzaUO1DBConstants.otraRazon, visitaVacunaUO1.getOtraRazon());
        cv.put(InfluenzaUO1DBConstants.vacuna, visitaVacunaUO1.getVacuna());
        if (visitaVacunaUO1.getFechaVacuna() != null) cv.put(InfluenzaUO1DBConstants.fechaVacuna, visitaVacunaUO1.getFechaVacuna().getTime());
        cv.put(InfluenzaUO1DBConstants.segundaDosis, visitaVacunaUO1.getSegundaDosis());
        if (visitaVacunaUO1.getFechaSegundaDosis() != null) cv.put(InfluenzaUO1DBConstants.fechaSegundaDosis, visitaVacunaUO1.getFechaSegundaDosis().getTime());
        cv.put(InfluenzaUO1DBConstants.tomaMxAntes, visitaVacunaUO1.getTomaMxAntes());
        cv.put(InfluenzaUO1DBConstants.razonNoTomaMx, visitaVacunaUO1.getRazonNoTomaMx());
        if (visitaVacunaUO1.getFechaReprogramacion() != null) cv.put(InfluenzaUO1DBConstants.fechaReprogramacion, visitaVacunaUO1.getFechaReprogramacion().getTime());
        
        if (visitaVacunaUO1.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaVacunaUO1.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, visitaVacunaUO1.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(visitaVacunaUO1.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(visitaVacunaUO1.getEstado()));
        cv.put(MainDBConstants.deviceId, visitaVacunaUO1.getDeviceid());
        return cv;
    }

    public static VisitaVacunaUO1 crearVisitaVacunaUO1(Cursor cursor){
        VisitaVacunaUO1 mVisitaVacunaUO1 = new VisitaVacunaUO1();
        
    	mVisitaVacunaUO1.setCodigoVisita(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.codigoVisita)));
    	mVisitaVacunaUO1.setParticipante(null);
    	if(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaVisita))>0) mVisitaVacunaUO1.setFechaVisita(new Date(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaVisita))));
    	mVisitaVacunaUO1.setVisita(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.visita)));
        mVisitaVacunaUO1.setVisitaExitosa(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.visitaExitosa)));
        mVisitaVacunaUO1.setRazonVisitaFallida(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.razonVisitaFallida)));
        mVisitaVacunaUO1.setOtraRazon(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.otraRazon)));
        mVisitaVacunaUO1.setVacuna(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.vacuna)));
        if(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaVacuna))>0) mVisitaVacunaUO1.setFechaVacuna(new Date(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaVacuna))));
        mVisitaVacunaUO1.setSegundaDosis(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.segundaDosis)));
        if(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaSegundaDosis))>0) mVisitaVacunaUO1.setFechaSegundaDosis(new Date(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaSegundaDosis))));
        mVisitaVacunaUO1.setTomaMxAntes(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.tomaMxAntes)));
        mVisitaVacunaUO1.setRazonNoTomaMx(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.razonNoTomaMx)));
        if(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaReprogramacion))>0) mVisitaVacunaUO1.setFechaReprogramacion(new Date(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaReprogramacion))));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mVisitaVacunaUO1.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mVisitaVacunaUO1.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mVisitaVacunaUO1.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mVisitaVacunaUO1.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mVisitaVacunaUO1.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mVisitaVacunaUO1;
    }

    public static void fillVisitaVacunaUO1ContentValues(SQLiteStatement stat, VisitaVacunaUO1 visitaVacunaUO1){
        stat.bindString(1, visitaVacunaUO1.getCodigoVisita());
        stat.bindLong(2, visitaVacunaUO1.getParticipante().getCodigo());
        bindDate(stat,3, visitaVacunaUO1.getFechaVisita());
        bindString(stat,4, visitaVacunaUO1.getVisita());
        bindString(stat,5, visitaVacunaUO1.getVisitaExitosa());
        bindString(stat,6, visitaVacunaUO1.getRazonVisitaFallida());
        bindString(stat,7, visitaVacunaUO1.getOtraRazon());
        bindString(stat,8, visitaVacunaUO1.getVacuna());
        bindDate(stat,9, visitaVacunaUO1.getFechaVacuna());
        bindString(stat,10, visitaVacunaUO1.getTomaMxAntes());
        bindString(stat,11, visitaVacunaUO1.getRazonNoTomaMx());
        bindString(stat,12, visitaVacunaUO1.getSegundaDosis());
        bindDate(stat,13, visitaVacunaUO1.getFechaSegundaDosis());
        bindDate(stat,14, visitaVacunaUO1.getFechaReprogramacion());
        bindDate(stat,15, visitaVacunaUO1.getRecordDate());
        bindString(stat,16, visitaVacunaUO1.getRecordUser());
        stat.bindString(17, String.valueOf(visitaVacunaUO1.getPasive()));
        bindString(stat,18, visitaVacunaUO1.getDeviceid());
        stat.bindString(19, String.valueOf(visitaVacunaUO1.getEstado()));
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
