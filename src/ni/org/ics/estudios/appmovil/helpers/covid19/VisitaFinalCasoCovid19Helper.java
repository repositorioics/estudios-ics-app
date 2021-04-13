package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaFinalCasoCovid19;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas 12/06/2020.
 * V1.0
 */
public class VisitaFinalCasoCovid19Helper {

    public static ContentValues crearVisitaFinalCasoCovid19ContentValues(VisitaFinalCasoCovid19 visitaCaso){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.codigoVisitaFinal, visitaCaso.getCodigoVisitaFinal());
        cv.put(Covid19DBConstants.codigoParticipanteCaso, visitaCaso.getCodigoParticipanteCaso().getCodigoCasoParticipante());
        if (visitaCaso.getFechaVisita() != null) cv.put(Covid19DBConstants.fechaVisita, visitaCaso.getFechaVisita().getTime());
        cv.put(Covid19DBConstants.enfermo, visitaCaso.getEnfermo());
        cv.put(Covid19DBConstants.consTerreno, visitaCaso.getConsTerreno());
        cv.put(Covid19DBConstants.referidoCS, visitaCaso.getReferidoCS());
        cv.put(Covid19DBConstants.tratamiento, visitaCaso.getTratamiento());
        cv.put(Covid19DBConstants.queAntibiotico, visitaCaso.getQueAntibiotico());
        cv.put(Covid19DBConstants.otroMedicamento, visitaCaso.getOtroMedicamento());
        cv.put(Covid19DBConstants.sintResp, visitaCaso.getSintResp());
        cv.put(Covid19DBConstants.fueHospitalizado, visitaCaso.getFueHospitalizado());
        if (visitaCaso.getFechaIngreso() != null) cv.put(Covid19DBConstants.fechaIngreso, visitaCaso.getFechaIngreso().getTime());
        if (visitaCaso.getFechaEgreso() != null) cv.put(Covid19DBConstants.fechaEgreso, visitaCaso.getFechaEgreso().getTime());
        cv.put(Covid19DBConstants.diasHospitalizado, visitaCaso.getDiasHospitalizado());
        cv.put(Covid19DBConstants.cualHospital, visitaCaso.getCualHospital());
        cv.put(Covid19DBConstants.utilizoOxigeno, visitaCaso.getUtilizoOxigeno());
        cv.put(Covid19DBConstants.fueIntubado, visitaCaso.getFueIntubado());
        cv.put(Covid19DBConstants.estadoSalud, visitaCaso.getEstadoSalud());
        cv.put(Covid19DBConstants.faltoTrabajoEscuela, visitaCaso.getFaltoTrabajoEscuela());
        cv.put(Covid19DBConstants.diasFaltoTrabajoEscuela, visitaCaso.getDiasFaltoTrabajoEscuela());

        if (visitaCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, visitaCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(visitaCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(visitaCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, visitaCaso.getDeviceid());
        return cv;
    }

    public static VisitaFinalCasoCovid19 crearVisitaFinalCasoCovid19(Cursor cursor){
    	VisitaFinalCasoCovid19 mVisitaSeguimientoCaso = new VisitaFinalCasoCovid19();
        
    	mVisitaSeguimientoCaso.setCodigoVisitaFinal(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoVisitaFinal)));
    	mVisitaSeguimientoCaso.setCodigoParticipanteCaso(null);
    	if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaVisita))>0) mVisitaSeguimientoCaso.setFechaVisita(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaVisita))));
    	mVisitaSeguimientoCaso.setEnfermo(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.enfermo)));
    	mVisitaSeguimientoCaso.setConsTerreno(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.consTerreno)));
        mVisitaSeguimientoCaso.setReferidoCS(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.referidoCS)));
        mVisitaSeguimientoCaso.setTratamiento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tratamiento)));
        mVisitaSeguimientoCaso.setQueAntibiotico(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.queAntibiotico)));
        mVisitaSeguimientoCaso.setOtroMedicamento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.otroMedicamento)));
        mVisitaSeguimientoCaso.setSintResp(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.sintResp)));
        mVisitaSeguimientoCaso.setFueHospitalizado(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fueHospitalizado)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaIngreso))>0) mVisitaSeguimientoCaso.setFechaIngreso(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaIngreso))));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaEgreso))>0) mVisitaSeguimientoCaso.setFechaEgreso(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaEgreso))));
        if(cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.diasHospitalizado))>0) mVisitaSeguimientoCaso.setDiasHospitalizado(cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.diasHospitalizado)));
        mVisitaSeguimientoCaso.setCualHospital(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.cualHospital)));
        mVisitaSeguimientoCaso.setUtilizoOxigeno(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.utilizoOxigeno)));
        mVisitaSeguimientoCaso.setFueIntubado(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fueIntubado)));
        mVisitaSeguimientoCaso.setEstadoSalud(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.estadoSalud)));
        mVisitaSeguimientoCaso.setFaltoTrabajoEscuela(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.faltoTrabajoEscuela)));
        if(cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.diasFaltoTrabajoEscuela))>0) mVisitaSeguimientoCaso.setDiasFaltoTrabajoEscuela(cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.diasFaltoTrabajoEscuela)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mVisitaSeguimientoCaso.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mVisitaSeguimientoCaso.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mVisitaSeguimientoCaso.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mVisitaSeguimientoCaso.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mVisitaSeguimientoCaso.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mVisitaSeguimientoCaso;
    }

    public static void fillVisitaFinalCasoCovid19Statement(SQLiteStatement stat, VisitaFinalCasoCovid19 visitaCaso){
        stat.bindString(1, visitaCaso.getCodigoVisitaFinal());
        bindString(stat,2, visitaCaso.getCodigoParticipanteCaso().getCodigoCasoParticipante());
        bindDate(stat,3, visitaCaso.getFechaVisita());
        bindString(stat,4, visitaCaso.getEnfermo());
        bindString(stat,5, visitaCaso.getConsTerreno());
        bindString(stat,6, visitaCaso.getReferidoCS());
        bindString(stat,7, visitaCaso.getTratamiento());
        bindString(stat,8, visitaCaso.getQueAntibiotico());
        bindString(stat,9, visitaCaso.getOtroMedicamento());
        bindString(stat,10, visitaCaso.getSintResp());
        bindString(stat,11, visitaCaso.getFueHospitalizado());
        bindDate(stat,12, visitaCaso.getFechaIngreso());
        bindDate(stat,13, visitaCaso.getFechaEgreso());
        bindInteger(stat,14, visitaCaso.getDiasHospitalizado());
        bindString(stat,15, visitaCaso.getCualHospital());
        bindString(stat,16, visitaCaso.getUtilizoOxigeno());
        bindString(stat,17, visitaCaso.getFueIntubado());
        bindString(stat,18, visitaCaso.getEstadoSalud());
        bindString(stat,19, visitaCaso.getFaltoTrabajoEscuela());
        bindInteger(stat,20, visitaCaso.getDiasFaltoTrabajoEscuela());

        bindDate(stat,21, visitaCaso.getRecordDate());
        bindString(stat,22, visitaCaso.getRecordUser());
        stat.bindString(23, String.valueOf(visitaCaso.getPasive()));
        bindString(stat,24, visitaCaso.getDeviceid());
        stat.bindString(25, String.valueOf(visitaCaso.getEstado()));
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
}
