package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaFinalCaso;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by William Aviles 6/9/2017.
 * V1.0
 */
public class VisitaFinalCasoHelper {

    public static ContentValues crearVisitaFinalCasoContentValues(VisitaFinalCaso visitaFinalCaso){
        ContentValues cv = new ContentValues();
        cv.put(CasosDBConstants.codigoParticipanteCaso, visitaFinalCaso.getCodigoParticipanteCaso().getCodigoCasoParticipante());
        if (visitaFinalCaso.getFechaVisita() != null) cv.put(CasosDBConstants.fechaVisita, visitaFinalCaso.getFechaVisita().getTime());
        cv.put(CasosDBConstants.enfermo, visitaFinalCaso.getEnfermo());
        cv.put(CasosDBConstants.consTerreno, visitaFinalCaso.getConsTerreno());       
        cv.put(CasosDBConstants.referidoCs, visitaFinalCaso.getReferidoCs());
        cv.put(CasosDBConstants.tratamiento, visitaFinalCaso.getTratamiento());
        cv.put(CasosDBConstants.cualAntibiotico, visitaFinalCaso.getCualAntibiotico());
        cv.put(CasosDBConstants.sintResp, visitaFinalCaso.getSintResp());
        cv.put(CasosDBConstants.fiebre, visitaFinalCaso.getFiebre());
        cv.put(CasosDBConstants.tos, visitaFinalCaso.getTos());
        cv.put(CasosDBConstants.dolorGarganta, visitaFinalCaso.getDolorGarganta());
        cv.put(CasosDBConstants.secrecionNasal, visitaFinalCaso.getSecrecionNasal());    
        
        if (visitaFinalCaso.getFif() != null) cv.put(CasosDBConstants.fif, visitaFinalCaso.getFif().getTime());
        if (visitaFinalCaso.getFff() != null) cv.put(CasosDBConstants.fff, visitaFinalCaso.getFff().getTime());
        if (visitaFinalCaso.getFitos() != null) cv.put(CasosDBConstants.fitos, visitaFinalCaso.getFitos().getTime());
        if (visitaFinalCaso.getFftos() != null) cv.put(CasosDBConstants.fftos, visitaFinalCaso.getFftos().getTime());
        if (visitaFinalCaso.getFigg() != null) cv.put(CasosDBConstants.figg, visitaFinalCaso.getFigg().getTime());
        if (visitaFinalCaso.getFfgg() != null) cv.put(CasosDBConstants.ffgg, visitaFinalCaso.getFfgg().getTime());
        if (visitaFinalCaso.getFisn() != null) cv.put(CasosDBConstants.fisn, visitaFinalCaso.getFisn().getTime());
        if (visitaFinalCaso.getFfsn() != null) cv.put(CasosDBConstants.ffsn, visitaFinalCaso.getFfsn().getTime());
        
        if (visitaFinalCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaFinalCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, visitaFinalCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(visitaFinalCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(visitaFinalCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, visitaFinalCaso.getDeviceid());
        return cv;
    }

    public static VisitaFinalCaso crearVisitaFinalCaso(Cursor cursor){
    	VisitaFinalCaso mVisitaFinalCaso = new VisitaFinalCaso();
        
    	mVisitaFinalCaso.setCodigoParticipanteCaso(null);
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaVisita))>0) mVisitaFinalCaso.setFechaVisita(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaVisita))));
    	mVisitaFinalCaso.setEnfermo(cursor.getString(cursor.getColumnIndex(CasosDBConstants.enfermo)));
    	mVisitaFinalCaso.setConsTerreno(cursor.getString(cursor.getColumnIndex(CasosDBConstants.consTerreno)));
    	mVisitaFinalCaso.setReferidoCs(cursor.getString(cursor.getColumnIndex(CasosDBConstants.referidoCs)));
    	mVisitaFinalCaso.setTratamiento(cursor.getString(cursor.getColumnIndex(CasosDBConstants.tratamiento)));
    	mVisitaFinalCaso.setCualAntibiotico(cursor.getString(cursor.getColumnIndex(CasosDBConstants.cualAntibiotico)));
    	mVisitaFinalCaso.setSintResp(cursor.getString(cursor.getColumnIndex(CasosDBConstants.sintResp)));
    	mVisitaFinalCaso.setFiebre(cursor.getString(cursor.getColumnIndex(CasosDBConstants.fiebre)));
    	mVisitaFinalCaso.setTos(cursor.getString(cursor.getColumnIndex(CasosDBConstants.tos)));
    	mVisitaFinalCaso.setDolorGarganta(cursor.getString(cursor.getColumnIndex(CasosDBConstants.dolorGarganta)));
    	mVisitaFinalCaso.setSecrecionNasal(cursor.getString(cursor.getColumnIndex(CasosDBConstants.secrecionNasal)));
        
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fif))>0) mVisitaFinalCaso.setFif(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fif))));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fff))>0) mVisitaFinalCaso.setFff(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fff))));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fitos))>0) mVisitaFinalCaso.setFitos(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fitos))));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fftos))>0) mVisitaFinalCaso.setFftos(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fftos))));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.figg))>0) mVisitaFinalCaso.setFigg(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.figg))));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.ffgg))>0) mVisitaFinalCaso.setFfgg(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.ffgg))));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fisn))>0) mVisitaFinalCaso.setFisn(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fisn))));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.ffsn))>0) mVisitaFinalCaso.setFfsn(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.ffsn))));
    	
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mVisitaFinalCaso.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mVisitaFinalCaso.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mVisitaFinalCaso.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mVisitaFinalCaso.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mVisitaFinalCaso.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mVisitaFinalCaso;
    }

}
