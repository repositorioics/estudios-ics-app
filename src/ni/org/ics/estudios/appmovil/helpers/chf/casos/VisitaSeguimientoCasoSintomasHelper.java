package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCasoSintomas;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by William Aviles 6/9/2017.
 * V1.0
 */
public class VisitaSeguimientoCasoSintomasHelper {

    public static ContentValues crearVisitaSeguimientoCasoSintomasContentValues(VisitaSeguimientoCasoSintomas sintomaCaso){
        ContentValues cv = new ContentValues();
        cv.put(CasosDBConstants.codigoCasoSintoma, sintomaCaso.getCodigoCasoSintoma());
        cv.put(CasosDBConstants.codigoCasoVisita, sintomaCaso.getCodigoVisitaCaso().getCodigoCasoVisita());        
        if (sintomaCaso.getFechaSintomas() != null) cv.put(CasosDBConstants.fechaSintomas, sintomaCaso.getFechaSintomas().getTime());
        cv.put(CasosDBConstants.periodo, sintomaCaso.getPeriodo());
        cv.put(CasosDBConstants.fiebre, sintomaCaso.getFiebre());
        if (sintomaCaso.getFif() != null) cv.put(CasosDBConstants.fif, sintomaCaso.getFif().getTime());
        cv.put(CasosDBConstants.fiebreCuantificada, sintomaCaso.getFiebreCuantificada());
        cv.put(CasosDBConstants.valorFiebreCuantificada, sintomaCaso.getValorFiebreCuantificada());
        cv.put(CasosDBConstants.dolorCabeza, sintomaCaso.getDolorCabeza());
        cv.put(CasosDBConstants.dolorArticular, sintomaCaso.getDolorArticular());
        cv.put(CasosDBConstants.dolorMuscular, sintomaCaso.getDolorMuscular());
        cv.put(CasosDBConstants.dificultadRespiratoria, sintomaCaso.getDificultadRespiratoria());
        if (sintomaCaso.getFdr() != null) cv.put(CasosDBConstants.fdr, sintomaCaso.getFdr().getTime());
        cv.put(CasosDBConstants.secrecionNasal, sintomaCaso.getSecrecionNasal());
        if (sintomaCaso.getFsn() != null) cv.put(CasosDBConstants.fsn, sintomaCaso.getFsn().getTime());
        cv.put(CasosDBConstants.tos, sintomaCaso.getTos());
        if (sintomaCaso.getFtos() != null) cv.put(CasosDBConstants.ftos, sintomaCaso.getFtos().getTime());
        cv.put(CasosDBConstants.pocoApetito, sintomaCaso.getPocoApetito());
        cv.put(CasosDBConstants.dolorGarganta, sintomaCaso.getDolorGarganta());
        cv.put(CasosDBConstants.diarrea, sintomaCaso.getDiarrea());
        cv.put(CasosDBConstants.quedoCama, sintomaCaso.getQuedoCama());
        cv.put(CasosDBConstants.respiracionRuidosa, sintomaCaso.getRespiracionRuidosa());
        if (sintomaCaso.getFrr() != null) cv.put(CasosDBConstants.frr, sintomaCaso.getFrr().getTime());
        cv.put(CasosDBConstants.oseltamivir, sintomaCaso.getOseltamivir());
        cv.put(CasosDBConstants.antibiotico, sintomaCaso.getAntibiotico());
        cv.put(CasosDBConstants.cualAntibiotico, sintomaCaso.getCualAntibiotico());
        cv.put(CasosDBConstants.prescritoMedico, sintomaCaso.getPrescritoMedico());
        cv.put(CasosDBConstants.respiracionRapida, sintomaCaso.getRespiracionRapida());
        
        if (sintomaCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, sintomaCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, sintomaCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(sintomaCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(sintomaCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, sintomaCaso.getDeviceid());
        return cv;
    }

    public static VisitaSeguimientoCasoSintomas crearVisitaSeguimientoCasoSintomas(Cursor cursor){
    	VisitaSeguimientoCasoSintomas mVisitaSeguimientoCasoSintomas = new VisitaSeguimientoCasoSintomas();     
    	mVisitaSeguimientoCasoSintomas.setCodigoCasoSintoma(cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCasoSintoma)));
    	mVisitaSeguimientoCasoSintomas.setCodigoVisitaCaso(null);
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaSintomas))>0) mVisitaSeguimientoCasoSintomas.setFechaSintomas(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaSintomas))));
    	mVisitaSeguimientoCasoSintomas.setPeriodo(cursor.getString(cursor.getColumnIndex(CasosDBConstants.periodo)));
    	mVisitaSeguimientoCasoSintomas.setFiebre(cursor.getString(cursor.getColumnIndex(CasosDBConstants.fiebre)));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fif))>0) mVisitaSeguimientoCasoSintomas.setFif(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fif))));
    	mVisitaSeguimientoCasoSintomas.setFiebreCuantificada(cursor.getString(cursor.getColumnIndex(CasosDBConstants.fiebreCuantificada)));
    	mVisitaSeguimientoCasoSintomas.setValorFiebreCuantificada(cursor.getFloat(cursor.getColumnIndex(CasosDBConstants.valorFiebreCuantificada)));
    	mVisitaSeguimientoCasoSintomas.setDolorCabeza(cursor.getString(cursor.getColumnIndex(CasosDBConstants.dolorCabeza)));
    	mVisitaSeguimientoCasoSintomas.setDolorArticular(cursor.getString(cursor.getColumnIndex(CasosDBConstants.dolorArticular)));
    	mVisitaSeguimientoCasoSintomas.setDolorMuscular(cursor.getString(cursor.getColumnIndex(CasosDBConstants.dolorMuscular)));
    	mVisitaSeguimientoCasoSintomas.setDificultadRespiratoria(cursor.getString(cursor.getColumnIndex(CasosDBConstants.dificultadRespiratoria)));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fdr))>0) mVisitaSeguimientoCasoSintomas.setFdr(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fdr))));
    	mVisitaSeguimientoCasoSintomas.setSecrecionNasal(cursor.getString(cursor.getColumnIndex(CasosDBConstants.secrecionNasal)));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fsn))>0) mVisitaSeguimientoCasoSintomas.setFsn(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fsn))));
    	mVisitaSeguimientoCasoSintomas.setTos(cursor.getString(cursor.getColumnIndex(CasosDBConstants.tos)));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.ftos))>0) mVisitaSeguimientoCasoSintomas.setFtos(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.ftos))));
    	mVisitaSeguimientoCasoSintomas.setPocoApetito(cursor.getString(cursor.getColumnIndex(CasosDBConstants.pocoApetito)));
    	mVisitaSeguimientoCasoSintomas.setDolorGarganta(cursor.getString(cursor.getColumnIndex(CasosDBConstants.dolorGarganta)));
    	mVisitaSeguimientoCasoSintomas.setDiarrea(cursor.getString(cursor.getColumnIndex(CasosDBConstants.diarrea)));
    	mVisitaSeguimientoCasoSintomas.setQuedoCama(cursor.getString(cursor.getColumnIndex(CasosDBConstants.quedoCama)));
    	mVisitaSeguimientoCasoSintomas.setRespiracionRuidosa(cursor.getString(cursor.getColumnIndex(CasosDBConstants.respiracionRuidosa)));
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.frr))>0) mVisitaSeguimientoCasoSintomas.setFrr(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.frr))));   	
    	mVisitaSeguimientoCasoSintomas.setOseltamivir(cursor.getString(cursor.getColumnIndex(CasosDBConstants.oseltamivir)));
    	mVisitaSeguimientoCasoSintomas.setAntibiotico(cursor.getString(cursor.getColumnIndex(CasosDBConstants.antibiotico)));
    	mVisitaSeguimientoCasoSintomas.setCualAntibiotico(cursor.getString(cursor.getColumnIndex(CasosDBConstants.cualAntibiotico)));
    	mVisitaSeguimientoCasoSintomas.setPrescritoMedico(cursor.getString(cursor.getColumnIndex(CasosDBConstants.prescritoMedico)));
        mVisitaSeguimientoCasoSintomas.setRespiracionRapida(cursor.getString(cursor.getColumnIndex(CasosDBConstants.respiracionRapida)));
    	
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mVisitaSeguimientoCasoSintomas.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mVisitaSeguimientoCasoSintomas.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mVisitaSeguimientoCasoSintomas.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mVisitaSeguimientoCasoSintomas.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mVisitaSeguimientoCasoSintomas.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mVisitaSeguimientoCasoSintomas;
    }
}
