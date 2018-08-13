package ni.org.ics.estudios.appmovil.helpers;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.VisitaTerreno;
import ni.org.ics.estudios.appmovil.domain.VisitaTerrenoParticipante;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

/**
 * Created by Miguel Salinas on 5/9/2017.
 * V1.0
 */
public class VisitaTerrenoHelper {

    public static ContentValues crearVisitaTerrenoContentValues(VisitaTerreno visitaTerreno){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigoVisita, visitaTerreno.getCodigoVisita());
        cv.put(MainDBConstants.casa, visitaTerreno.getCasa().getCodigo());
        if (visitaTerreno.getFechaVisita() != null) cv.put(MainDBConstants.fechaVisita, visitaTerreno.getFechaVisita().getTime());
        cv.put(MainDBConstants.visitaExitosa, visitaTerreno.getVisitaExitosa());
        cv.put(MainDBConstants.razonVisitaNoExitosa, visitaTerreno.getRazonVisitaNoExitosa());
        cv.put(MainDBConstants.otraRazonVisitaNoExitosa, visitaTerreno.getOtraRazonVisitaNoExitosa());
        if (visitaTerreno.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaTerreno.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, visitaTerreno.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(visitaTerreno.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(visitaTerreno.getEstado()));
		cv.put(MainDBConstants.deviceId, visitaTerreno.getDeviceid());

        return cv;
    }

    public static VisitaTerreno crearVisitaTerreno(Cursor cursor){
    	VisitaTerreno visitaTerreno = new VisitaTerreno();
        visitaTerreno.setCodigoVisita(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoVisita)));
        visitaTerreno.setCasa(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaVisita))>0) visitaTerreno.setFechaVisita(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaVisita))));
        visitaTerreno.setVisitaExitosa(cursor.getString(cursor.getColumnIndex(MainDBConstants.visitaExitosa)));
        visitaTerreno.setRazonVisitaNoExitosa(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonVisitaNoExitosa)));
        visitaTerreno.setOtraRazonVisitaNoExitosa(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonVisitaNoExitosa)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) visitaTerreno.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        visitaTerreno.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        visitaTerreno.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        visitaTerreno.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        visitaTerreno.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return visitaTerreno;
    }

    public static ContentValues crearVisitaTerrenoPartContentValues(VisitaTerrenoParticipante visitaTerreno){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigoVisita, visitaTerreno.getCodigoVisita());
        cv.put(MainDBConstants.participante, visitaTerreno.getParticipante().getCodigo());
        if (visitaTerreno.getFechaVisita() != null) cv.put(MainDBConstants.fechaVisita, visitaTerreno.getFechaVisita().getTime());
        cv.put(MainDBConstants.visitaExitosa, visitaTerreno.getVisitaExitosa());
        cv.put(MainDBConstants.razonVisitaNoExitosa, visitaTerreno.getRazonVisitaNoExitosa());
        cv.put(MainDBConstants.otraRazonVisitaNoExitosa, visitaTerreno.getOtraRazonVisitaNoExitosa());
        cv.put(MainDBConstants.dejoCarta, visitaTerreno.getDejoCarta());
        cv.put(MainDBConstants.personaDejoCarta, visitaTerreno.getPersonaDejoCarta());
        cv.put(MainDBConstants.relFamPersonaDejoCarta, visitaTerreno.getRelFamPersonaDejoCarta());
        cv.put(MainDBConstants.personaCasa, visitaTerreno.getPersonaCasa());
        cv.put(MainDBConstants.relacionFamPersonaCasa, visitaTerreno.getRelacionFamPersonaCasa());
        cv.put(MainDBConstants.telefonoPersonaCasa, visitaTerreno.getTelefonoPersonaCasa());
        cv.put(MainDBConstants.visitaExitosa, visitaTerreno.getVisitaExitosa());
        if (visitaTerreno.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaTerreno.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, visitaTerreno.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(visitaTerreno.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(visitaTerreno.getEstado()));
        cv.put(MainDBConstants.deviceId, visitaTerreno.getDeviceid());

        return cv;
    }

    public static VisitaTerrenoParticipante crearVisitaTerrenoPart(Cursor cursor){
        VisitaTerrenoParticipante visitaTerreno = new VisitaTerrenoParticipante();
        visitaTerreno.setCodigoVisita(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoVisita)));
        visitaTerreno.setParticipante(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaVisita))>0) visitaTerreno.setFechaVisita(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaVisita))));
        visitaTerreno.setVisitaExitosa(cursor.getString(cursor.getColumnIndex(MainDBConstants.visitaExitosa)));
        visitaTerreno.setRazonVisitaNoExitosa(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonVisitaNoExitosa)));
        visitaTerreno.setOtraRazonVisitaNoExitosa(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonVisitaNoExitosa)));
        visitaTerreno.setDejoCarta(cursor.getString(cursor.getColumnIndex(MainDBConstants.dejoCarta)));
        visitaTerreno.setPersonaDejoCarta(cursor.getString(cursor.getColumnIndex(MainDBConstants.personaDejoCarta)));
        visitaTerreno.setRelFamPersonaDejoCarta(cursor.getString(cursor.getColumnIndex(MainDBConstants.relFamPersonaDejoCarta)));
        visitaTerreno.setPersonaCasa(cursor.getString(cursor.getColumnIndex(MainDBConstants.personaCasa)));
        visitaTerreno.setRelacionFamPersonaCasa(cursor.getString(cursor.getColumnIndex(MainDBConstants.relacionFamPersonaCasa)));
        visitaTerreno.setOtraRelacionPersonaCasa(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRelacionPersonaCasa)));
        visitaTerreno.setTelefonoPersonaCasa(cursor.getString(cursor.getColumnIndex(MainDBConstants.telefonoPersonaCasa)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) visitaTerreno.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        visitaTerreno.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        visitaTerreno.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        visitaTerreno.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        visitaTerreno.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return visitaTerreno;
    }
}
