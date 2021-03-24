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
        if (sintomaCaso.getCodigoCasoVisita() != null) stat.bindString(2, sintomaCaso.getCodigoCasoVisita().getCodigoCasoVisita());
        if (sintomaCaso.getFechaSintomas() != null) stat.bindLong(3, sintomaCaso.getFechaSintomas().getTime());
        if (sintomaCaso.getFiebre() != null) stat.bindString(4, sintomaCaso.getFiebre());
        if (sintomaCaso.getFiebreIntensidad() != null) stat.bindString(5, sintomaCaso.getFiebreIntensidad());
        //if (sintomaCaso.getFif() != null) stat.bindLong(fif, sintomaCaso.getFif().getTime());
        if (sintomaCaso.getFiebreCuantificada() != null) stat.bindString(7, sintomaCaso.getFiebreCuantificada());
        if (sintomaCaso.getValorFiebreCuantificada() != null) stat.bindDouble(8, sintomaCaso.getValorFiebreCuantificada());
        if (sintomaCaso.getDolorCabeza() != null) stat.bindString(9, sintomaCaso.getDolorCabeza());
        if (sintomaCaso.getDolorCabezaIntensidad() != null) stat.bindString(10, sintomaCaso.getDolorCabezaIntensidad());
        if (sintomaCaso.getDolorArticular() != null) stat.bindString(11, sintomaCaso.getDolorArticular());
        if (sintomaCaso.getDolorArticularIntensidad() != null) stat.bindString(12, sintomaCaso.getDolorArticularIntensidad());
        if (sintomaCaso.getDolorMuscular() != null) stat.bindString(13, sintomaCaso.getDolorMuscular());
        if (sintomaCaso.getDolorMuscularIntensidad() != null) stat.bindString(14, sintomaCaso.getDolorMuscularIntensidad());
        if (sintomaCaso.getDificultadRespiratoria() != null) stat.bindString(15, sintomaCaso.getDificultadRespiratoria());
        if (sintomaCaso.getSecrecionNasal() != null) stat.bindString(16, sintomaCaso.getSecrecionNasal());
        if (sintomaCaso.getSecrecionNasalIntensidad() != null) stat.bindString(17, sintomaCaso.getSecrecionNasalIntensidad());
        if (sintomaCaso.getTos() != null) stat.bindString(18, sintomaCaso.getTos());
        if (sintomaCaso.getTosIntensidad() != null) stat.bindString(19, sintomaCaso.getTosIntensidad());
        if (sintomaCaso.getPocoApetito() != null) stat.bindString(20, sintomaCaso.getPocoApetito());
        if (sintomaCaso.getDolorGarganta() != null) stat.bindString(21, sintomaCaso.getDolorGarganta());
        if (sintomaCaso.getDolorGargantaIntensidad() != null) stat.bindString(22, sintomaCaso.getDolorGargantaIntensidad());
        if (sintomaCaso.getPicorGarganta() != null) stat.bindString(23, sintomaCaso.getPicorGarganta());
        if (sintomaCaso.getCongestionNasal() != null) stat.bindString(24, sintomaCaso.getCongestionNasal());
        if (sintomaCaso.getRash() != null) stat.bindString(25, sintomaCaso.getRash());
        if (sintomaCaso.getUrticaria() != null) stat.bindString(26, sintomaCaso.getUrticaria());
        if (sintomaCaso.getConjuntivitis() != null) stat.bindString(27, sintomaCaso.getConjuntivitis());
        if (sintomaCaso.getExpectoracion() != null) stat.bindString(28, sintomaCaso.getExpectoracion());
        if (sintomaCaso.getDiarrea() != null) stat.bindString(29, sintomaCaso.getDiarrea());
        if (sintomaCaso.getVomito() != null) stat.bindString(30, sintomaCaso.getVomito());
        if (sintomaCaso.getFatiga() != null) stat.bindString(31, sintomaCaso.getFatiga());
        if (sintomaCaso.getRespiracionRuidosa() != null) stat.bindString(32, sintomaCaso.getRespiracionRuidosa());
        if (sintomaCaso.getRespiracionRapida() != null) stat.bindString(33, sintomaCaso.getRespiracionRapida());
        if (sintomaCaso.getPerdidaOlfato() != null) stat.bindString(34, sintomaCaso.getPerdidaOlfato());
        if (sintomaCaso.getPerdidaGusto() != null) stat.bindString(35, sintomaCaso.getPerdidaGusto());
        if (sintomaCaso.getDesmayos() != null) stat.bindString(36, sintomaCaso.getDesmayos());
        if (sintomaCaso.getSensacionPechoApretado() != null) stat.bindString(37, sintomaCaso.getSensacionPechoApretado());
        if (sintomaCaso.getDolorPecho() != null) stat.bindString(38, sintomaCaso.getDolorPecho());
        if (sintomaCaso.getSensacionFaltaAire() != null) stat.bindString(39, sintomaCaso.getSensacionFaltaAire());
        if (sintomaCaso.getQuedoCama() != null) stat.bindString(40, sintomaCaso.getQuedoCama());
        if (sintomaCaso.getTratamiento() != null) stat.bindString(41, sintomaCaso.getTratamiento());
        if (sintomaCaso.getFechaInicioTx() != null) stat.bindLong(42, sintomaCaso.getFechaInicioTx().getTime());
        if (sintomaCaso.getCualMedicamento() != null) stat.bindString(43, sintomaCaso.getCualMedicamento());
        if (sintomaCaso.getOtroMedicamento() != null) stat.bindString(44, sintomaCaso.getOtroMedicamento());
        if (sintomaCaso.getAntibiotico() != null) stat.bindString(45, sintomaCaso.getAntibiotico());
        if (sintomaCaso.getPrescritoMedico() != null) stat.bindString(46, sintomaCaso.getPrescritoMedico());
        if (sintomaCaso.getFactorRiesgo() != null) stat.bindString(47, sintomaCaso.getFactorRiesgo());
        if (sintomaCaso.getOtroFactorRiesgo() != null) stat.bindString(48, sintomaCaso.getOtroFactorRiesgo());
        if (sintomaCaso.getOtraPersonaEnferma() != null) stat.bindString(49, sintomaCaso.getOtraPersonaEnferma());
        if (sintomaCaso.getCuantasPersonas() != null) stat.bindLong(50, sintomaCaso.getCuantasPersonas());
        if (sintomaCaso.getTrasladoHospital() != null) stat.bindString(51, sintomaCaso.getTrasladoHospital());
        if (sintomaCaso.getCualHospital() != null) stat.bindString(52, sintomaCaso.getCualHospital());
        if (sintomaCaso.getHospitalizado() != null) stat.bindString(53, sintomaCaso.getHospitalizado());
        if (sintomaCaso.getFechaAlta() != null) stat.bindLong(54, sintomaCaso.getFechaAlta().getTime());

        if (sintomaCaso.getRecordDate() != null) stat.bindLong(55, sintomaCaso.getRecordDate().getTime());
        if (sintomaCaso.getRecordUser() != null) stat.bindString(56, sintomaCaso.getRecordUser());
        stat.bindString(57, String.valueOf(sintomaCaso.getPasive()));
        if (sintomaCaso.getDeviceid() != null) stat.bindString(58, sintomaCaso.getDeviceid());
        stat.bindString(59, String.valueOf(sintomaCaso.getEstado()));
    }
}
