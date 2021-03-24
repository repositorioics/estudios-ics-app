package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
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
        //se agrega intensidad sintomas
        cv.put(CasosDBConstants.fiebreIntensidad, sintomaCaso.getFiebreIntensidad());
        cv.put(CasosDBConstants.dolorCabezaIntensidad, sintomaCaso.getDolorCabezaIntensidad());
        cv.put(CasosDBConstants.dolorArticularIntensidad, sintomaCaso.getDolorArticularIntensidad());
        cv.put(CasosDBConstants.dolorMuscularIntensidad, sintomaCaso.getDolorMuscularIntensidad());
        cv.put(CasosDBConstants.secrecionNasalIntensidad, sintomaCaso.getSecrecionNasalIntensidad());
        cv.put(CasosDBConstants.tosIntensidad, sintomaCaso.getTosIntensidad());
        cv.put(CasosDBConstants.dolorGargantaIntensidad, sintomaCaso.getDolorGargantaIntensidad());

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
        //se agrega intensidad sintomas
        mVisitaSeguimientoCasoSintomas.setFiebreIntensidad(cursor.getString(cursor.getColumnIndex(CasosDBConstants.fiebreIntensidad)));
        mVisitaSeguimientoCasoSintomas.setDolorCabezaIntensidad(cursor.getString(cursor.getColumnIndex(CasosDBConstants.dolorCabezaIntensidad)));
        mVisitaSeguimientoCasoSintomas.setDolorArticularIntensidad(cursor.getString(cursor.getColumnIndex(CasosDBConstants.dolorArticularIntensidad)));
        mVisitaSeguimientoCasoSintomas.setDolorMuscularIntensidad(cursor.getString(cursor.getColumnIndex(CasosDBConstants.dolorMuscularIntensidad)));
        mVisitaSeguimientoCasoSintomas.setSecrecionNasalIntensidad(cursor.getString(cursor.getColumnIndex(CasosDBConstants.secrecionNasalIntensidad)));
        mVisitaSeguimientoCasoSintomas.setTosIntensidad(cursor.getString(cursor.getColumnIndex(CasosDBConstants.tosIntensidad)));
        mVisitaSeguimientoCasoSintomas.setDolorGargantaIntensidad(cursor.getString(cursor.getColumnIndex(CasosDBConstants.dolorGargantaIntensidad)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mVisitaSeguimientoCasoSintomas.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mVisitaSeguimientoCasoSintomas.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mVisitaSeguimientoCasoSintomas.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mVisitaSeguimientoCasoSintomas.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mVisitaSeguimientoCasoSintomas.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mVisitaSeguimientoCasoSintomas;
    }

    public static void fillVisitaSeguimientoCasoSintomasStatement(SQLiteStatement stat, VisitaSeguimientoCasoSintomas sintomaCaso){
        stat.bindString(1, sintomaCaso.getCodigoCasoSintoma());
        stat.bindString(2, sintomaCaso.getCodigoVisitaCaso().getCodigoCasoVisita());
        if (sintomaCaso.getFechaSintomas() != null) stat.bindLong(3, sintomaCaso.getFechaSintomas().getTime());
        if (sintomaCaso.getPeriodo() != null) stat.bindString(4, sintomaCaso.getPeriodo());
        if (sintomaCaso.getFiebre() != null) stat.bindString(5, sintomaCaso.getFiebre());
        if (sintomaCaso.getFif() != null) stat.bindLong(6, sintomaCaso.getFif().getTime());
        if (sintomaCaso.getFiebreCuantificada() != null) stat.bindString(7, sintomaCaso.getFiebreCuantificada());
        if (sintomaCaso.getValorFiebreCuantificada() != null) stat.bindDouble(8, sintomaCaso.getValorFiebreCuantificada());
        if (sintomaCaso.getDolorCabeza() != null) stat.bindString(9, sintomaCaso.getDolorCabeza());
        if (sintomaCaso.getDolorArticular() != null) stat.bindString(10, sintomaCaso.getDolorArticular());
        if (sintomaCaso.getDolorMuscular() != null) stat.bindString(11, sintomaCaso.getDolorMuscular());
        if (sintomaCaso.getDificultadRespiratoria() != null) stat.bindString(12, sintomaCaso.getDificultadRespiratoria());
        if (sintomaCaso.getFdr() != null) stat.bindLong(13, sintomaCaso.getFdr().getTime());
        if (sintomaCaso.getSecrecionNasal() != null) stat.bindString(14, sintomaCaso.getSecrecionNasal());
        if (sintomaCaso.getFsn() != null) stat.bindLong(15, sintomaCaso.getFsn().getTime());
        if (sintomaCaso.getTos() != null) stat.bindString(16, sintomaCaso.getTos());
        if (sintomaCaso.getFtos() != null) stat.bindLong(17, sintomaCaso.getFtos().getTime());
        if (sintomaCaso.getPocoApetito() != null) stat.bindString(18, sintomaCaso.getPocoApetito());
        if (sintomaCaso.getDolorGarganta() != null) stat.bindString(19, sintomaCaso.getDolorGarganta());
        if (sintomaCaso.getDiarrea() != null) stat.bindString(20, sintomaCaso.getDiarrea());
        if (sintomaCaso.getQuedoCama() != null) stat.bindString(21, sintomaCaso.getQuedoCama());
        if (sintomaCaso.getRespiracionRuidosa() != null) stat.bindString(22, sintomaCaso.getRespiracionRuidosa());
        if (sintomaCaso.getFrr() != null) stat.bindLong(23, sintomaCaso.getFrr().getTime());
        if (sintomaCaso.getOseltamivir() != null) stat.bindString(24, sintomaCaso.getOseltamivir());
        if (sintomaCaso.getAntibiotico() != null) stat.bindString(25, sintomaCaso.getAntibiotico());
        if (sintomaCaso.getCualAntibiotico() != null) stat.bindString(26, sintomaCaso.getCualAntibiotico());
        if (sintomaCaso.getPrescritoMedico() != null) stat.bindString(27, sintomaCaso.getPrescritoMedico());
        if (sintomaCaso.getRespiracionRapida() != null) stat.bindString(28, sintomaCaso.getRespiracionRapida());
        //se agrega intensidad sintomas
        if (sintomaCaso.getFiebreIntensidad() != null) stat.bindString(29, sintomaCaso.getFiebreIntensidad());
        if (sintomaCaso.getDolorCabezaIntensidad() != null) stat.bindString(30, sintomaCaso.getDolorCabezaIntensidad());
        if (sintomaCaso.getDolorArticularIntensidad() != null) stat.bindString(31, sintomaCaso.getDolorArticularIntensidad());
        if (sintomaCaso.getDolorMuscularIntensidad() != null) stat.bindString(32, sintomaCaso.getDolorMuscularIntensidad());
        if (sintomaCaso.getSecrecionNasalIntensidad() != null) stat.bindString(33, sintomaCaso.getSecrecionNasalIntensidad());
        if (sintomaCaso.getTosIntensidad() != null) stat.bindString(34, sintomaCaso.getTosIntensidad());
        if (sintomaCaso.getDolorGargantaIntensidad() != null) stat.bindString(35, sintomaCaso.getDolorGargantaIntensidad());

        if (sintomaCaso.getRecordDate() != null) stat.bindLong(36, sintomaCaso.getRecordDate().getTime());
        if (sintomaCaso.getRecordUser() != null) stat.bindString(37, sintomaCaso.getRecordUser());
        stat.bindString(38, String.valueOf(sintomaCaso.getPasive()));
        if (sintomaCaso.getDeviceid() != null) stat.bindString(39, sintomaCaso.getDeviceid());
        stat.bindString(40, String.valueOf(sintomaCaso.getEstado()));
    }
}
