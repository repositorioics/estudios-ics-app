package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.covid19.SintomasVisitaFinalCovid19;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas 01/07/2020.
 * V1.0
 */
public class SintomasVisitaFinalCovid19Helper {

    public static ContentValues crearSintomasVisitaFinalCovid19ContentValues(SintomasVisitaFinalCovid19 sintomaCaso){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.codigoCasoSintoma, sintomaCaso.getCodigoCasoSintoma());
        cv.put(Covid19DBConstants.codigoVisitaFinal, sintomaCaso.getCodigoVisitaFinal().getCodigoVisitaFinal());

        cv.put(Covid19DBConstants.fiebre, sintomaCaso.getFiebre());
        cv.put(Covid19DBConstants.tos, sintomaCaso.getTos());
        cv.put(Covid19DBConstants.dolorCabeza, sintomaCaso.getDolorCabeza());
        cv.put(Covid19DBConstants.dolorArticular, sintomaCaso.getDolorArticular());
        cv.put(Covid19DBConstants.dolorMuscular, sintomaCaso.getDolorMuscular());
        cv.put(Covid19DBConstants.dificultadRespiratoria, sintomaCaso.getDificultadRespiratoria());
        cv.put(Covid19DBConstants.pocoApetito, sintomaCaso.getPocoApetito());
        cv.put(Covid19DBConstants.dolorGarganta, sintomaCaso.getDolorGarganta());
        cv.put(Covid19DBConstants.secrecionNasal, sintomaCaso.getSecrecionNasal());
        cv.put(Covid19DBConstants.picorGarganta, sintomaCaso.getPicorGarganta());
        cv.put(Covid19DBConstants.expectoracion, sintomaCaso.getExpectoracion());
        cv.put(Covid19DBConstants.rash, sintomaCaso.getRash());
        cv.put(Covid19DBConstants.urticaria, sintomaCaso.getUrticaria());
        cv.put(Covid19DBConstants.conjuntivitis, sintomaCaso.getConjuntivitis());
        cv.put(Covid19DBConstants.diarrea, sintomaCaso.getDiarrea());
        cv.put(Covid19DBConstants.vomito, sintomaCaso.getVomito());
        cv.put(Covid19DBConstants.quedoCama, sintomaCaso.getQuedoCama());
        cv.put(Covid19DBConstants.respiracionRuidosa, sintomaCaso.getRespiracionRuidosa());
        cv.put(Covid19DBConstants.respiracionRapida, sintomaCaso.getRespiracionRapida());
        cv.put(Covid19DBConstants.perdidaOlfato, sintomaCaso.getPerdidaOlfato());
        cv.put(Covid19DBConstants.congestionNasal, sintomaCaso.getCongestionNasal());
        cv.put(Covid19DBConstants.perdidaGusto, sintomaCaso.getPerdidaGusto());
        cv.put(Covid19DBConstants.desmayos, sintomaCaso.getDesmayos());
        cv.put(Covid19DBConstants.sensacionPechoApretado, sintomaCaso.getSensacionPechoApretado());
        cv.put(Covid19DBConstants.dolorPecho, sintomaCaso.getDolorPecho());
        cv.put(Covid19DBConstants.sensacionFaltaAire, sintomaCaso.getSensacionFaltaAire());
        cv.put(Covid19DBConstants.fatiga, sintomaCaso.getFatiga());

        if (sintomaCaso.getFiebreFecIni() != null) cv.put(Covid19DBConstants.fiebreFecIni, sintomaCaso.getFiebreFecIni().getTime());
        if (sintomaCaso.getTosFecIni() != null) cv.put(Covid19DBConstants.tosFecIni, sintomaCaso.getTosFecIni().getTime());
        if (sintomaCaso.getDolorCabezaFecIni() != null) cv.put(Covid19DBConstants.dolorCabezaFecIni, sintomaCaso.getDolorCabezaFecIni().getTime());
        if (sintomaCaso.getDolorArticularFecIni() != null) cv.put(Covid19DBConstants.dolorArticularFecIni, sintomaCaso.getDolorArticularFecIni().getTime());
        if (sintomaCaso.getDolorMuscularFecIni() != null) cv.put(Covid19DBConstants.dolorMuscularFecIni, sintomaCaso.getDolorMuscularFecIni().getTime());
        if (sintomaCaso.getDificultadRespiratoriaFecIni() != null) cv.put(Covid19DBConstants.dificultadRespiratoriaFecIni, sintomaCaso.getDificultadRespiratoriaFecIni().getTime());
        if (sintomaCaso.getPocoApetitoFecIni() != null) cv.put(Covid19DBConstants.pocoApetitoFecIni, sintomaCaso.getPocoApetitoFecIni().getTime());
        if (sintomaCaso.getDolorGargantaFecIni() != null) cv.put(Covid19DBConstants.dolorGargantaFecIni, sintomaCaso.getDolorGargantaFecIni().getTime());
        if (sintomaCaso.getSecrecionNasalFecIni() != null) cv.put(Covid19DBConstants.secrecionNasalFecIni, sintomaCaso.getSecrecionNasalFecIni().getTime());
        if (sintomaCaso.getPicorGargantaFecIni() != null) cv.put(Covid19DBConstants.picorGargantaFecIni, sintomaCaso.getPicorGargantaFecIni().getTime());
        if (sintomaCaso.getExpectoracionFecIni() != null) cv.put(Covid19DBConstants.expectoracionFecIni, sintomaCaso.getExpectoracionFecIni().getTime());
        if (sintomaCaso.getRashFecIni() != null) cv.put(Covid19DBConstants.rashFecIni, sintomaCaso.getRashFecIni().getTime());
        if (sintomaCaso.getUrticariaFecIni() != null) cv.put(Covid19DBConstants.urticariaFecIni, sintomaCaso.getUrticariaFecIni().getTime());
        if (sintomaCaso.getConjuntivitisFecIni() != null) cv.put(Covid19DBConstants.conjuntivitisFecIni, sintomaCaso.getConjuntivitisFecIni().getTime());
        if (sintomaCaso.getDiarreaFecIni() != null) cv.put(Covid19DBConstants.diarreaFecIni, sintomaCaso.getDiarreaFecIni().getTime());
        if (sintomaCaso.getVomitoFecIni() != null) cv.put(Covid19DBConstants.vomitoFecIni, sintomaCaso.getVomitoFecIni().getTime());
        if (sintomaCaso.getQuedoCamaFecIni() != null) cv.put(Covid19DBConstants.quedoCamaFecIni, sintomaCaso.getQuedoCamaFecIni().getTime());
        if (sintomaCaso.getRespiracionRuidosaFecIni() != null) cv.put(Covid19DBConstants.respiracionRuidosaFecIni, sintomaCaso.getRespiracionRuidosaFecIni().getTime());
        if (sintomaCaso.getRespiracionRapidaFecIni() != null) cv.put(Covid19DBConstants.respiracionRapidaFecIni, sintomaCaso.getRespiracionRapidaFecIni().getTime());
        if (sintomaCaso.getPerdidaOlfatoFecIni() != null) cv.put(Covid19DBConstants.perdidaOlfatoFecIni, sintomaCaso.getPerdidaOlfatoFecIni().getTime());
        if (sintomaCaso.getCongestionNasalFecIni() != null) cv.put(Covid19DBConstants.congestionNasalFecIni, sintomaCaso.getCongestionNasalFecIni().getTime());
        if (sintomaCaso.getPerdidaGustoFecIni() != null) cv.put(Covid19DBConstants.perdidaGustoFecIni, sintomaCaso.getPerdidaGustoFecIni().getTime());
        if (sintomaCaso.getDesmayosFecIni() != null) cv.put(Covid19DBConstants.desmayosFecIni, sintomaCaso.getDesmayosFecIni().getTime());
        if (sintomaCaso.getSensacionPechoApretadoFecIni() != null) cv.put(Covid19DBConstants.sensacionPechoApretadoFecIni, sintomaCaso.getSensacionPechoApretadoFecIni().getTime());
        if (sintomaCaso.getDolorPechoFecIni() != null) cv.put(Covid19DBConstants.dolorPechoFecIni, sintomaCaso.getDolorPechoFecIni().getTime());
        if (sintomaCaso.getSensacionFaltaAireFecIni() != null) cv.put(Covid19DBConstants.sensacionFaltaAireFecIni, sintomaCaso.getSensacionFaltaAireFecIni().getTime());
        if (sintomaCaso.getFatigaFecIni() != null) cv.put(Covid19DBConstants.fatigaFecIni, sintomaCaso.getFatigaFecIni().getTime());

        if (sintomaCaso.getFiebreFecFin() != null) cv.put(Covid19DBConstants.fiebreFecFin, sintomaCaso.getFiebreFecFin().getTime());
        if (sintomaCaso.getTosFecFin() != null) cv.put(Covid19DBConstants.tosFecFin, sintomaCaso.getTosFecFin().getTime());
        if (sintomaCaso.getDolorCabezaFecFin() != null) cv.put(Covid19DBConstants.dolorCabezaFecFin, sintomaCaso.getDolorCabezaFecFin().getTime());
        if (sintomaCaso.getDolorArticularFecFin() != null) cv.put(Covid19DBConstants.dolorArticularFecFin, sintomaCaso.getDolorArticularFecFin().getTime());
        if (sintomaCaso.getDolorMuscularFecFin() != null) cv.put(Covid19DBConstants.dolorMuscularFecFin, sintomaCaso.getDolorMuscularFecFin().getTime());
        if (sintomaCaso.getDificultadRespiratoriaFecFin() != null) cv.put(Covid19DBConstants.dificultadRespiratoriaFecFin, sintomaCaso.getDificultadRespiratoriaFecFin().getTime());
        if (sintomaCaso.getPocoApetitoFecFin() != null) cv.put(Covid19DBConstants.pocoApetitoFecFin, sintomaCaso.getPocoApetitoFecFin().getTime());
        if (sintomaCaso.getDolorGargantaFecFin() != null) cv.put(Covid19DBConstants.dolorGargantaFecFin, sintomaCaso.getDolorGargantaFecFin().getTime());
        if (sintomaCaso.getSecrecionNasalFecFin() != null) cv.put(Covid19DBConstants.secrecionNasalFecFin, sintomaCaso.getSecrecionNasalFecFin().getTime());
        if (sintomaCaso.getPicorGargantaFecFin() != null) cv.put(Covid19DBConstants.picorGargantaFecFin, sintomaCaso.getPicorGargantaFecFin().getTime());
        if (sintomaCaso.getExpectoracionFecFin() != null) cv.put(Covid19DBConstants.expectoracionFecFin, sintomaCaso.getExpectoracionFecFin().getTime());
        if (sintomaCaso.getRashFecFin() != null) cv.put(Covid19DBConstants.rashFecFin, sintomaCaso.getRashFecFin().getTime());
        if (sintomaCaso.getUrticariaFecFin() != null) cv.put(Covid19DBConstants.urticariaFecFin, sintomaCaso.getUrticariaFecFin().getTime());
        if (sintomaCaso.getConjuntivitisFecFin() != null) cv.put(Covid19DBConstants.conjuntivitisFecFin, sintomaCaso.getConjuntivitisFecFin().getTime());
        if (sintomaCaso.getDiarreaFecFin() != null) cv.put(Covid19DBConstants.diarreaFecFin, sintomaCaso.getDiarreaFecFin().getTime());
        if (sintomaCaso.getVomitoFecFin() != null) cv.put(Covid19DBConstants.vomitoFecFin, sintomaCaso.getVomitoFecFin().getTime());
        if (sintomaCaso.getQuedoCamaFecFin() != null) cv.put(Covid19DBConstants.quedoCamaFecFin, sintomaCaso.getQuedoCamaFecFin().getTime());
        if (sintomaCaso.getRespiracionRuidosaFecFin() != null) cv.put(Covid19DBConstants.respiracionRuidosaFecFin, sintomaCaso.getRespiracionRuidosaFecFin().getTime());
        if (sintomaCaso.getRespiracionRapidaFecFin() != null) cv.put(Covid19DBConstants.respiracionRapidaFecFin, sintomaCaso.getRespiracionRapidaFecFin().getTime());
        if (sintomaCaso.getPerdidaOlfatoFecFin() != null) cv.put(Covid19DBConstants.perdidaOlfatoFecFin, sintomaCaso.getPerdidaOlfatoFecFin().getTime());
        if (sintomaCaso.getCongestionNasalFecFin() != null) cv.put(Covid19DBConstants.congestionNasalFecFin, sintomaCaso.getCongestionNasalFecFin().getTime());
        if (sintomaCaso.getPerdidaGustoFecFin() != null) cv.put(Covid19DBConstants.perdidaGustoFecFin, sintomaCaso.getPerdidaGustoFecFin().getTime());
        if (sintomaCaso.getDesmayosFecFin() != null) cv.put(Covid19DBConstants.desmayosFecFin, sintomaCaso.getDesmayosFecFin().getTime());
        if (sintomaCaso.getSensacionPechoApretadoFecFin() != null) cv.put(Covid19DBConstants.sensacionPechoApretadoFecFin, sintomaCaso.getSensacionPechoApretadoFecFin().getTime());
        if (sintomaCaso.getDolorPechoFecFin() != null) cv.put(Covid19DBConstants.dolorPechoFecFin, sintomaCaso.getDolorPechoFecFin().getTime());
        if (sintomaCaso.getSensacionFaltaAireFecFin() != null) cv.put(Covid19DBConstants.sensacionFaltaAireFecFin, sintomaCaso.getSensacionFaltaAireFecFin().getTime());
        if (sintomaCaso.getFatigaFecFin() != null) cv.put(Covid19DBConstants.fatigaFecFin, sintomaCaso.getFatigaFecFin().getTime());

        if (sintomaCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, sintomaCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, sintomaCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(sintomaCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(sintomaCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, sintomaCaso.getDeviceid());
        return cv;
    }

    public static SintomasVisitaFinalCovid19 crearSintomasVisitaFinalCovid19(Cursor cursor){
        SintomasVisitaFinalCovid19 mSintomasVisitaFinalCovid19 = new SintomasVisitaFinalCovid19();
        mSintomasVisitaFinalCovid19.setCodigoCasoSintoma(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCasoSintoma)));
        mSintomasVisitaFinalCovid19.setCodigoVisitaFinal(null);
        mSintomasVisitaFinalCovid19.setFiebre(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fiebre)));
        mSintomasVisitaFinalCovid19.setTos(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tos)));
        mSintomasVisitaFinalCovid19.setDolorCabeza(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorCabeza)));
        mSintomasVisitaFinalCovid19.setDolorArticular(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorArticular)));
        mSintomasVisitaFinalCovid19.setDolorMuscular(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorMuscular)));
        mSintomasVisitaFinalCovid19.setDificultadRespiratoria(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dificultadRespiratoria)));
        mSintomasVisitaFinalCovid19.setPocoApetito(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.pocoApetito)));
        mSintomasVisitaFinalCovid19.setDolorGarganta(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorGarganta)));
        mSintomasVisitaFinalCovid19.setSecrecionNasal(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.secrecionNasal)));
        mSintomasVisitaFinalCovid19.setPicorGarganta(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.picorGarganta)));
        mSintomasVisitaFinalCovid19.setExpectoracion(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.expectoracion)));
        mSintomasVisitaFinalCovid19.setRash(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.rash)));
        mSintomasVisitaFinalCovid19.setUrticaria(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.urticaria)));
        mSintomasVisitaFinalCovid19.setConjuntivitis(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.conjuntivitis)));
        mSintomasVisitaFinalCovid19.setDiarrea(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.diarrea)));
        mSintomasVisitaFinalCovid19.setVomito(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.vomito)));
        mSintomasVisitaFinalCovid19.setQuedoCama(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.quedoCama)));
        mSintomasVisitaFinalCovid19.setRespiracionRuidosa(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.respiracionRuidosa)));
        mSintomasVisitaFinalCovid19.setRespiracionRapida(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.respiracionRapida)));
        mSintomasVisitaFinalCovid19.setPerdidaOlfato(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.perdidaOlfato)));
        mSintomasVisitaFinalCovid19.setCongestionNasal(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.congestionNasal)));
        mSintomasVisitaFinalCovid19.setPerdidaGusto(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.perdidaGusto)));
        mSintomasVisitaFinalCovid19.setDesmayos(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.desmayos)));
        mSintomasVisitaFinalCovid19.setSensacionPechoApretado(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.sensacionPechoApretado)));
        mSintomasVisitaFinalCovid19.setDolorPecho(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorPecho)));
        mSintomasVisitaFinalCovid19.setSensacionFaltaAire(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.sensacionFaltaAire)));
        mSintomasVisitaFinalCovid19.setFatiga(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fatiga)));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fiebreFecIni))>0) mSintomasVisitaFinalCovid19.setFiebreFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fiebreFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.tosFecIni))>0) mSintomasVisitaFinalCovid19.setTosFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.tosFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorCabezaFecIni))>0) mSintomasVisitaFinalCovid19.setDolorCabezaFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorCabezaFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorArticularFecIni))>0) mSintomasVisitaFinalCovid19.setDolorArticularFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorArticularFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorMuscularFecIni))>0) mSintomasVisitaFinalCovid19.setDolorMuscularFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorMuscularFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dificultadRespiratoriaFecIni))>0) mSintomasVisitaFinalCovid19.setDificultadRespiratoriaFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dificultadRespiratoriaFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.pocoApetitoFecIni))>0) mSintomasVisitaFinalCovid19.setPocoApetitoFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.pocoApetitoFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorGargantaFecIni))>0) mSintomasVisitaFinalCovid19.setDolorGargantaFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorGargantaFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.secrecionNasalFecIni))>0) mSintomasVisitaFinalCovid19.setSecrecionNasalFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.secrecionNasalFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.picorGargantaFecIni))>0) mSintomasVisitaFinalCovid19.setPicorGargantaFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.picorGargantaFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.expectoracionFecIni))>0) mSintomasVisitaFinalCovid19.setExpectoracionFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.expectoracionFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.rashFecIni))>0) mSintomasVisitaFinalCovid19.setRashFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.rashFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.urticariaFecIni))>0) mSintomasVisitaFinalCovid19.setUrticariaFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.urticariaFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.conjuntivitisFecIni))>0) mSintomasVisitaFinalCovid19.setConjuntivitisFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.conjuntivitisFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.diarreaFecIni))>0) mSintomasVisitaFinalCovid19.setDiarreaFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.diarreaFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.vomitoFecIni))>0) mSintomasVisitaFinalCovid19.setVomitoFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.vomitoFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.quedoCamaFecIni))>0) mSintomasVisitaFinalCovid19.setQuedoCamaFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.quedoCamaFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.respiracionRuidosaFecIni))>0) mSintomasVisitaFinalCovid19.setRespiracionRuidosaFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.respiracionRuidosaFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.respiracionRapidaFecIni))>0) mSintomasVisitaFinalCovid19.setRespiracionRapidaFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.respiracionRapidaFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.perdidaOlfatoFecIni))>0) mSintomasVisitaFinalCovid19.setPerdidaOlfatoFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.perdidaOlfatoFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.congestionNasalFecIni))>0) mSintomasVisitaFinalCovid19.setCongestionNasalFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.congestionNasalFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.perdidaGustoFecIni))>0) mSintomasVisitaFinalCovid19.setPerdidaGustoFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.perdidaGustoFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.desmayosFecIni))>0) mSintomasVisitaFinalCovid19.setDesmayosFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.desmayosFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.sensacionPechoApretadoFecIni))>0) mSintomasVisitaFinalCovid19.setSensacionPechoApretadoFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.sensacionPechoApretadoFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorPechoFecIni))>0) mSintomasVisitaFinalCovid19.setDolorPechoFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorPechoFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.sensacionFaltaAireFecIni))>0) mSintomasVisitaFinalCovid19.setSensacionFaltaAireFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.sensacionFaltaAireFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fatigaFecIni))>0) mSintomasVisitaFinalCovid19.setFatigaFecIni(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fatigaFecIni))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fiebreFecFin))>0) mSintomasVisitaFinalCovid19.setFiebreFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fiebreFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.tosFecFin))>0) mSintomasVisitaFinalCovid19.setTosFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.tosFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorCabezaFecFin))>0) mSintomasVisitaFinalCovid19.setDolorCabezaFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorCabezaFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorArticularFecFin))>0) mSintomasVisitaFinalCovid19.setDolorArticularFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorArticularFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorMuscularFecFin))>0) mSintomasVisitaFinalCovid19.setDolorMuscularFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorMuscularFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dificultadRespiratoriaFecFin))>0) mSintomasVisitaFinalCovid19.setDificultadRespiratoriaFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dificultadRespiratoriaFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.pocoApetitoFecFin))>0) mSintomasVisitaFinalCovid19.setPocoApetitoFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.pocoApetitoFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorGargantaFecFin))>0) mSintomasVisitaFinalCovid19.setDolorGargantaFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorGargantaFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.secrecionNasalFecFin))>0) mSintomasVisitaFinalCovid19.setSecrecionNasalFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.secrecionNasalFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.picorGargantaFecFin))>0) mSintomasVisitaFinalCovid19.setPicorGargantaFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.picorGargantaFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.expectoracionFecFin))>0) mSintomasVisitaFinalCovid19.setExpectoracionFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.expectoracionFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.rashFecFin))>0) mSintomasVisitaFinalCovid19.setRashFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.rashFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.urticariaFecFin))>0) mSintomasVisitaFinalCovid19.setUrticariaFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.urticariaFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.conjuntivitisFecFin))>0) mSintomasVisitaFinalCovid19.setConjuntivitisFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.conjuntivitisFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.diarreaFecFin))>0) mSintomasVisitaFinalCovid19.setDiarreaFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.diarreaFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.vomitoFecFin))>0) mSintomasVisitaFinalCovid19.setVomitoFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.vomitoFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.quedoCamaFecFin))>0) mSintomasVisitaFinalCovid19.setQuedoCamaFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.quedoCamaFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.respiracionRuidosaFecFin))>0) mSintomasVisitaFinalCovid19.setRespiracionRuidosaFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.respiracionRuidosaFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.respiracionRapidaFecFin))>0) mSintomasVisitaFinalCovid19.setRespiracionRapidaFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.respiracionRapidaFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.perdidaOlfatoFecFin))>0) mSintomasVisitaFinalCovid19.setPerdidaOlfatoFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.perdidaOlfatoFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.congestionNasalFecFin))>0) mSintomasVisitaFinalCovid19.setCongestionNasalFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.congestionNasalFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.perdidaGustoFecFin))>0) mSintomasVisitaFinalCovid19.setPerdidaGustoFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.perdidaGustoFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.desmayosFecFin))>0) mSintomasVisitaFinalCovid19.setDesmayosFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.desmayosFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.sensacionPechoApretadoFecFin))>0) mSintomasVisitaFinalCovid19.setSensacionPechoApretadoFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.sensacionPechoApretadoFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorPechoFecFin))>0) mSintomasVisitaFinalCovid19.setDolorPechoFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.dolorPechoFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.sensacionFaltaAireFecFin))>0) mSintomasVisitaFinalCovid19.setSensacionFaltaAireFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.sensacionFaltaAireFecFin))));
        if (cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fatigaFecFin))>0) mSintomasVisitaFinalCovid19.setFatigaFecFin(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fatigaFecFin))));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mSintomasVisitaFinalCovid19.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mSintomasVisitaFinalCovid19.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mSintomasVisitaFinalCovid19.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mSintomasVisitaFinalCovid19.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mSintomasVisitaFinalCovid19.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mSintomasVisitaFinalCovid19;
    }
}
