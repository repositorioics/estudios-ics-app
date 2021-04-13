package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.covid19.SintomasVisitaCasoCovid19;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas 09/06/2020.
 * V1.0
 */
public class SintomasVisitaCasoCovid19Helper {

    public static ContentValues crearSintomasVisitaCasoCovid19ContentValues(SintomasVisitaCasoCovid19 sintomaCaso){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.codigoCasoSintoma, sintomaCaso.getCodigoCasoSintoma());
        cv.put(Covid19DBConstants.codigoCasoVisita, sintomaCaso.getCodigoCasoVisita().getCodigoCasoVisita());


        if (sintomaCaso.getFechaSintomas() != null) cv.put(Covid19DBConstants.fechaSintomas, sintomaCaso.getFechaSintomas().getTime());
        cv.put(Covid19DBConstants.fiebre, sintomaCaso.getFiebre());
        cv.put(Covid19DBConstants.fiebreIntensidad, sintomaCaso.getFiebreIntensidad());
        //if (sintomaCaso.getFif() != null) cv.put(Covid19DBConstants.fif, sintomaCaso.getFif().getTime());
        cv.put(Covid19DBConstants.fiebreCuantificada, sintomaCaso.getFiebreCuantificada());
        cv.put(Covid19DBConstants.valorFiebreCuantificada, sintomaCaso.getValorFiebreCuantificada());
        cv.put(Covid19DBConstants.dolorCabeza, sintomaCaso.getDolorCabeza());
        cv.put(Covid19DBConstants.dolorCabezaIntensidad, sintomaCaso.getDolorCabezaIntensidad());
        cv.put(Covid19DBConstants.dolorArticular, sintomaCaso.getDolorArticular());
        cv.put(Covid19DBConstants.dolorArticularIntensidad, sintomaCaso.getDolorArticularIntensidad());
        cv.put(Covid19DBConstants.dolorMuscular, sintomaCaso.getDolorMuscular());
        cv.put(Covid19DBConstants.dolorMuscularIntensidad, sintomaCaso.getDolorMuscularIntensidad());
        cv.put(Covid19DBConstants.dificultadRespiratoria, sintomaCaso.getDificultadRespiratoria());
        //if (sintomaCaso.getFdr() != null) cv.put(Covid19DBConstants.fdr, sintomaCaso.getFdr().getTime());
        cv.put(Covid19DBConstants.secrecionNasal, sintomaCaso.getSecrecionNasal());
        cv.put(Covid19DBConstants.secrecionNasalIntensidad, sintomaCaso.getSecrecionNasalIntensidad());
        //if (sintomaCaso.getFsn() != null) cv.put(Covid19DBConstants.fsn, sintomaCaso.getFsn().getTime());
        cv.put(Covid19DBConstants.tos, sintomaCaso.getTos());
        cv.put(Covid19DBConstants.tosIntensidad, sintomaCaso.getTosIntensidad());
        //if (sintomaCaso.getFtos() != null) cv.put(Covid19DBConstants.ftos, sintomaCaso.getFtos().getTime());
        cv.put(Covid19DBConstants.pocoApetito, sintomaCaso.getPocoApetito());
        cv.put(Covid19DBConstants.dolorGarganta, sintomaCaso.getDolorGarganta());
        cv.put(Covid19DBConstants.dolorGargantaIntensidad, sintomaCaso.getDolorGargantaIntensidad());
        cv.put(Covid19DBConstants.picorGarganta, sintomaCaso.getPicorGarganta());
        cv.put(Covid19DBConstants.congestionNasal, sintomaCaso.getCongestionNasal());
        cv.put(Covid19DBConstants.rash, sintomaCaso.getRash());
        cv.put(Covid19DBConstants.urticaria, sintomaCaso.getUrticaria());
        cv.put(Covid19DBConstants.conjuntivitis, sintomaCaso.getConjuntivitis());
        cv.put(Covid19DBConstants.expectoracion, sintomaCaso.getExpectoracion());
        cv.put(Covid19DBConstants.diarrea, sintomaCaso.getDiarrea());
        cv.put(Covid19DBConstants.vomito, sintomaCaso.getVomito());
        cv.put(Covid19DBConstants.fatiga, sintomaCaso.getFatiga());
        cv.put(Covid19DBConstants.respiracionRuidosa, sintomaCaso.getRespiracionRuidosa());
        cv.put(Covid19DBConstants.respiracionRapida, sintomaCaso.getRespiracionRapida());
        cv.put(Covid19DBConstants.perdidaOlfato, sintomaCaso.getPerdidaOlfato());
        cv.put(Covid19DBConstants.perdidaGusto, sintomaCaso.getPerdidaGusto());
        cv.put(Covid19DBConstants.desmayos, sintomaCaso.getDesmayos());
        cv.put(Covid19DBConstants.sensacionPechoApretado, sintomaCaso.getSensacionPechoApretado());
        cv.put(Covid19DBConstants.dolorPecho, sintomaCaso.getDolorPecho());
        cv.put(Covid19DBConstants.sensacionFaltaAire, sintomaCaso.getSensacionFaltaAire());
        cv.put(Covid19DBConstants.quedoCama, sintomaCaso.getQuedoCama());
        cv.put(Covid19DBConstants.tratamiento, sintomaCaso.getTratamiento());
        if (sintomaCaso.getFechaInicioTx() != null) cv.put(Covid19DBConstants.fechaInicioTx, sintomaCaso.getFechaInicioTx().getTime());
        cv.put(Covid19DBConstants.cualMedicamento, sintomaCaso.getCualMedicamento());
        cv.put(Covid19DBConstants.otroMedicamento, sintomaCaso.getOtroMedicamento());
        cv.put(Covid19DBConstants.antibiotico, sintomaCaso.getAntibiotico());
        cv.put(Covid19DBConstants.prescritoMedico, sintomaCaso.getPrescritoMedico());
        cv.put(Covid19DBConstants.factorRiesgo, sintomaCaso.getFactorRiesgo());
        cv.put(Covid19DBConstants.otroFactorRiesgo, sintomaCaso.getOtroFactorRiesgo());
        cv.put(Covid19DBConstants.otraPersonaEnferma, sintomaCaso.getOtraPersonaEnferma());
        cv.put(Covid19DBConstants.cuantasPersonas, sintomaCaso.getCuantasPersonas());
        cv.put(Covid19DBConstants.trasladoHospital, sintomaCaso.getTrasladoHospital());
        cv.put(Covid19DBConstants.cualHospital, sintomaCaso.getCualHospital());
        cv.put(Covid19DBConstants.hospitalizado, sintomaCaso.getHospitalizado());
        if (sintomaCaso.getFechaAlta() != null) cv.put(Covid19DBConstants.fechaAlta, sintomaCaso.getFechaAlta().getTime());
        //if (sintomaCaso.getFrr() != null) cv.put(Covid19DBConstants.frr, sintomaCaso.getFrr().getTime());

        if (sintomaCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, sintomaCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, sintomaCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(sintomaCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(sintomaCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, sintomaCaso.getDeviceid());
        return cv;
    }

    public static SintomasVisitaCasoCovid19 crearSintomasVisitaCasoCovid19(Cursor cursor){
        SintomasVisitaCasoCovid19 mSintomasVisitaCasoCovid19 = new SintomasVisitaCasoCovid19();
        mSintomasVisitaCasoCovid19.setCodigoCasoSintoma(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCasoSintoma)));
        mSintomasVisitaCasoCovid19.setCodigoCasoVisita(null);
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaSintomas))>0) mSintomasVisitaCasoCovid19.setFechaSintomas(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaSintomas))));
        mSintomasVisitaCasoCovid19.setFiebre(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fiebre)));
        mSintomasVisitaCasoCovid19.setFiebreIntensidad(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fiebreIntensidad)));
        //if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fif))>0) mSintomasVisitaCasoCovid19.setFif(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fif))));
        mSintomasVisitaCasoCovid19.setFiebreCuantificada(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fiebreCuantificada)));
        mSintomasVisitaCasoCovid19.setValorFiebreCuantificada(cursor.getFloat(cursor.getColumnIndex(Covid19DBConstants.valorFiebreCuantificada)));
        mSintomasVisitaCasoCovid19.setDolorCabeza(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorCabeza)));
        mSintomasVisitaCasoCovid19.setDolorCabezaIntensidad(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorCabezaIntensidad)));
        mSintomasVisitaCasoCovid19.setDolorArticular(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorArticular)));
        mSintomasVisitaCasoCovid19.setDolorArticularIntensidad(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorArticularIntensidad)));
        mSintomasVisitaCasoCovid19.setDolorMuscular(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorMuscular)));
        mSintomasVisitaCasoCovid19.setDolorMuscularIntensidad(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorMuscularIntensidad)));
        mSintomasVisitaCasoCovid19.setDificultadRespiratoria(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dificultadRespiratoria)));
        //if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fdr))>0) mSintomasVisitaCasoCovid19.setFdr(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fdr))));
        mSintomasVisitaCasoCovid19.setSecrecionNasal(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.secrecionNasal)));
        mSintomasVisitaCasoCovid19.setSecrecionNasalIntensidad(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.secrecionNasalIntensidad)));
        //if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fsn))>0) mSintomasVisitaCasoCovid19.setFsn(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fsn))));
        mSintomasVisitaCasoCovid19.setTos(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tos)));
        mSintomasVisitaCasoCovid19.setTosIntensidad(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tosIntensidad)));
        //if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.ftos))>0) mSintomasVisitaCasoCovid19.setFtos(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.ftos))));
        mSintomasVisitaCasoCovid19.setPocoApetito(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.pocoApetito)));
        mSintomasVisitaCasoCovid19.setDolorGarganta(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorGarganta)));
        mSintomasVisitaCasoCovid19.setDolorGargantaIntensidad(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorGargantaIntensidad)));
        mSintomasVisitaCasoCovid19.setPicorGarganta(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.picorGarganta)));
        mSintomasVisitaCasoCovid19.setCongestionNasal(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.congestionNasal)));
        mSintomasVisitaCasoCovid19.setRash(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.rash)));
        mSintomasVisitaCasoCovid19.setUrticaria(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.urticaria)));
        mSintomasVisitaCasoCovid19.setConjuntivitis(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.conjuntivitis)));
        mSintomasVisitaCasoCovid19.setExpectoracion(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.expectoracion)));
        mSintomasVisitaCasoCovid19.setDiarrea(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.diarrea)));
        mSintomasVisitaCasoCovid19.setVomito(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.vomito)));
        mSintomasVisitaCasoCovid19.setFatiga(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fatiga)));
        mSintomasVisitaCasoCovid19.setRespiracionRuidosa(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.respiracionRuidosa)));
        mSintomasVisitaCasoCovid19.setRespiracionRapida(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.respiracionRapida)));
        mSintomasVisitaCasoCovid19.setPerdidaOlfato(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.perdidaOlfato)));
        mSintomasVisitaCasoCovid19.setPerdidaGusto(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.perdidaGusto)));
        mSintomasVisitaCasoCovid19.setDesmayos(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.desmayos)));
        mSintomasVisitaCasoCovid19.setSensacionPechoApretado(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.sensacionPechoApretado)));
        mSintomasVisitaCasoCovid19.setDolorPecho(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorPecho)));
        mSintomasVisitaCasoCovid19.setSensacionFaltaAire(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.sensacionFaltaAire)));
        mSintomasVisitaCasoCovid19.setQuedoCama(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.quedoCama)));
        mSintomasVisitaCasoCovid19.setTratamiento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tratamiento)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaInicioTx))>0) mSintomasVisitaCasoCovid19.setFechaInicioTx(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaInicioTx))));
        mSintomasVisitaCasoCovid19.setCualMedicamento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.cualMedicamento)));
        mSintomasVisitaCasoCovid19.setOtroMedicamento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.otroMedicamento)));
        mSintomasVisitaCasoCovid19.setAntibiotico(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.antibiotico)));
        mSintomasVisitaCasoCovid19.setPrescritoMedico(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.prescritoMedico)));
        mSintomasVisitaCasoCovid19.setFactorRiesgo(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.factorRiesgo)));
        mSintomasVisitaCasoCovid19.setOtroFactorRiesgo(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.otroFactorRiesgo)));
        mSintomasVisitaCasoCovid19.setOtraPersonaEnferma(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.otraPersonaEnferma)));
        mSintomasVisitaCasoCovid19.setCuantasPersonas(cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.cuantasPersonas)));
        mSintomasVisitaCasoCovid19.setTrasladoHospital(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.trasladoHospital)));
        mSintomasVisitaCasoCovid19.setCualHospital(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.cualHospital)));
        mSintomasVisitaCasoCovid19.setHospitalizado(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.hospitalizado)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaAlta))>0) mSintomasVisitaCasoCovid19.setFechaAlta(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaAlta))));



        //if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.frr))>0) mSintomasVisitaCasoCovid19.setFrr(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.frr))));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mSintomasVisitaCasoCovid19.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mSintomasVisitaCasoCovid19.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mSintomasVisitaCasoCovid19.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mSintomasVisitaCasoCovid19.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mSintomasVisitaCasoCovid19.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mSintomasVisitaCasoCovid19;
    }

    public static void fillSintomasVisitaCasoCovid19Statement(SQLiteStatement stat, SintomasVisitaCasoCovid19 sintomaCaso){
        if (sintomaCaso.getCodigoCasoSintoma() != null) stat.bindString(1, sintomaCaso.getCodigoCasoSintoma());
        bindString(stat,2, sintomaCaso.getCodigoCasoVisita().getCodigoCasoVisita());
        bindDate(stat,3, sintomaCaso.getFechaSintomas());
        bindString(stat,4, sintomaCaso.getFiebre());
        bindString(stat,5, sintomaCaso.getFiebreIntensidad());
        //bindDate(stat, fif, sintomaCaso.getFif());
        bindString(stat,7, sintomaCaso.getFiebreCuantificada());
        if (sintomaCaso.getValorFiebreCuantificada() != null) stat.bindDouble(8, sintomaCaso.getValorFiebreCuantificada());
        bindString(stat,9, sintomaCaso.getDolorCabeza());
        bindString(stat,10, sintomaCaso.getDolorCabezaIntensidad());
        bindString(stat,11, sintomaCaso.getDolorArticular());
        bindString(stat,12, sintomaCaso.getDolorArticularIntensidad());
        bindString(stat,13, sintomaCaso.getDolorMuscular());
        bindString(stat,14, sintomaCaso.getDolorMuscularIntensidad());
        bindString(stat,15, sintomaCaso.getDificultadRespiratoria());
        bindString(stat,16, sintomaCaso.getSecrecionNasal());
        bindString(stat,17, sintomaCaso.getSecrecionNasalIntensidad());
        bindString(stat,18, sintomaCaso.getTos());
        bindString(stat,19, sintomaCaso.getTosIntensidad());
        bindString(stat,20, sintomaCaso.getPocoApetito());
        bindString(stat,21, sintomaCaso.getDolorGarganta());
        bindString(stat,22, sintomaCaso.getDolorGargantaIntensidad());
        bindString(stat,23, sintomaCaso.getPicorGarganta());
        bindString(stat,24, sintomaCaso.getCongestionNasal());
        bindString(stat,25, sintomaCaso.getRash());
        bindString(stat,26, sintomaCaso.getUrticaria());
        bindString(stat,27, sintomaCaso.getConjuntivitis());
        bindString(stat,28, sintomaCaso.getExpectoracion());
        bindString(stat,29, sintomaCaso.getDiarrea());
        bindString(stat,30, sintomaCaso.getVomito());
        bindString(stat,31, sintomaCaso.getFatiga());
        bindString(stat,32, sintomaCaso.getRespiracionRuidosa());
        bindString(stat,33, sintomaCaso.getRespiracionRapida());
        bindString(stat,34, sintomaCaso.getPerdidaOlfato());
        bindString(stat,35, sintomaCaso.getPerdidaGusto());
        bindString(stat,36, sintomaCaso.getDesmayos());
        bindString(stat,37, sintomaCaso.getSensacionPechoApretado());
        bindString(stat,38, sintomaCaso.getDolorPecho());
        bindString(stat,39, sintomaCaso.getSensacionFaltaAire());
        bindString(stat,40, sintomaCaso.getQuedoCama());
        bindString(stat,41, sintomaCaso.getTratamiento());
        bindDate(stat,42, sintomaCaso.getFechaInicioTx());
        bindString(stat,43, sintomaCaso.getCualMedicamento());
        bindString(stat,44, sintomaCaso.getOtroMedicamento());
        bindString(stat,45, sintomaCaso.getAntibiotico());
        bindString(stat,46, sintomaCaso.getPrescritoMedico());
        bindString(stat,47, sintomaCaso.getFactorRiesgo());
        bindString(stat,48, sintomaCaso.getOtroFactorRiesgo());
        bindString(stat,49, sintomaCaso.getOtraPersonaEnferma());
        bindInteger(stat,50, sintomaCaso.getCuantasPersonas());
        bindString(stat,51, sintomaCaso.getTrasladoHospital());
        bindString(stat,52, sintomaCaso.getCualHospital());
        bindString(stat,53, sintomaCaso.getHospitalizado());
        bindDate(stat,54, sintomaCaso.getFechaAlta());

        bindDate(stat,55, sintomaCaso.getRecordDate());
        bindString(stat,56, sintomaCaso.getRecordUser());
        stat.bindString(57, String.valueOf(sintomaCaso.getPasive()));
        bindString(stat,58, sintomaCaso.getDeviceid());
        stat.bindString(59, String.valueOf(sintomaCaso.getEstado()));
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
