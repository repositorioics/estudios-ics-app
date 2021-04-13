package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
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

    public static void fillSintomasVisitaFinalCovid19Statement(SQLiteStatement stat, SintomasVisitaFinalCovid19 sintomaCaso){
        stat.bindString(1, sintomaCaso.getCodigoCasoSintoma());
        bindString(stat,2, sintomaCaso.getCodigoVisitaFinal().getCodigoVisitaFinal());
        bindString(stat,3, sintomaCaso.getFiebre());
        bindString(stat,4, sintomaCaso.getTos());
        bindString(stat,5, sintomaCaso.getDolorCabeza());
        bindString(stat,6, sintomaCaso.getDolorArticular());
        bindString(stat,7, sintomaCaso.getDolorMuscular());
        bindString(stat,8, sintomaCaso.getDificultadRespiratoria());
        bindString(stat,9, sintomaCaso.getPocoApetito());
        bindString(stat,10, sintomaCaso.getDolorGarganta());
        bindString(stat,11, sintomaCaso.getSecrecionNasal());
        bindString(stat,12, sintomaCaso.getPicorGarganta());
        bindString(stat,13, sintomaCaso.getExpectoracion());
        bindString(stat,14, sintomaCaso.getRash());
        bindString(stat,15, sintomaCaso.getUrticaria());
        bindString(stat,16, sintomaCaso.getConjuntivitis());
        bindString(stat,17, sintomaCaso.getDiarrea());
        bindString(stat,18, sintomaCaso.getVomito());
        bindString(stat,19, sintomaCaso.getQuedoCama());
        bindString(stat,20, sintomaCaso.getRespiracionRuidosa());
        bindString(stat,21, sintomaCaso.getRespiracionRapida());
        bindString(stat,22, sintomaCaso.getPerdidaOlfato());
        bindString(stat,23, sintomaCaso.getCongestionNasal());
        bindString(stat,24, sintomaCaso.getPerdidaGusto());
        bindString(stat,25, sintomaCaso.getDesmayos());
        bindString(stat,26, sintomaCaso.getSensacionPechoApretado());
        bindString(stat,27, sintomaCaso.getDolorPecho());
        bindString(stat,28, sintomaCaso.getSensacionFaltaAire());
        bindString(stat,29, sintomaCaso.getFatiga());
        bindDate(stat,30, sintomaCaso.getFiebreFecIni());
        bindDate(stat,31, sintomaCaso.getTosFecIni());
        bindDate(stat,32, sintomaCaso.getDolorCabezaFecIni());
        bindDate(stat,33, sintomaCaso.getDolorArticularFecIni());
        bindDate(stat,34, sintomaCaso.getDolorMuscularFecIni());
        bindDate(stat,35, sintomaCaso.getDificultadRespiratoriaFecIni());
        bindDate(stat,36, sintomaCaso.getPocoApetitoFecIni());
        bindDate(stat,37, sintomaCaso.getDolorGargantaFecIni());
        bindDate(stat,38, sintomaCaso.getSecrecionNasalFecIni());
        bindDate(stat,39, sintomaCaso.getPicorGargantaFecIni());
        bindDate(stat,40, sintomaCaso.getExpectoracionFecIni());
        bindDate(stat,41, sintomaCaso.getRashFecIni());
        bindDate(stat,42, sintomaCaso.getUrticariaFecIni());
        bindDate(stat,43, sintomaCaso.getConjuntivitisFecIni());
        bindDate(stat,44, sintomaCaso.getDiarreaFecIni());
        bindDate(stat,45, sintomaCaso.getVomitoFecIni());
        bindDate(stat,46, sintomaCaso.getQuedoCamaFecIni());
        bindDate(stat,47, sintomaCaso.getRespiracionRuidosaFecIni());
        bindDate(stat,48, sintomaCaso.getRespiracionRapidaFecIni());
        bindDate(stat,49, sintomaCaso.getPerdidaOlfatoFecIni());
        bindDate(stat,50, sintomaCaso.getCongestionNasalFecIni());
        bindDate(stat,51, sintomaCaso.getPerdidaGustoFecIni());
        bindDate(stat,52, sintomaCaso.getDesmayosFecIni());
        bindDate(stat,53, sintomaCaso.getSensacionPechoApretadoFecIni());
        bindDate(stat,54, sintomaCaso.getDolorPechoFecIni());
        bindDate(stat,55, sintomaCaso.getSensacionFaltaAireFecIni());
        bindDate(stat,56, sintomaCaso.getFatigaFecIni());
        bindDate(stat,57, sintomaCaso.getFiebreFecFin());
        bindDate(stat,58, sintomaCaso.getTosFecFin());
        bindDate(stat,59, sintomaCaso.getDolorCabezaFecFin());
        bindDate(stat,60, sintomaCaso.getDolorArticularFecFin());
        bindDate(stat,61, sintomaCaso.getDolorMuscularFecFin());
        bindDate(stat,62, sintomaCaso.getDificultadRespiratoriaFecFin());
        bindDate(stat,63, sintomaCaso.getPocoApetitoFecFin());
        bindDate(stat,64, sintomaCaso.getDolorGargantaFecFin());
        bindDate(stat,65, sintomaCaso.getSecrecionNasalFecFin());
        bindDate(stat,66, sintomaCaso.getPicorGargantaFecFin());
        bindDate(stat,67, sintomaCaso.getExpectoracionFecFin());
        bindDate(stat,68, sintomaCaso.getRashFecFin());
        bindDate(stat,69, sintomaCaso.getUrticariaFecFin());
        bindDate(stat,70, sintomaCaso.getConjuntivitisFecFin());
        bindDate(stat,71, sintomaCaso.getDiarreaFecFin());
        bindDate(stat,72, sintomaCaso.getVomitoFecFin());
        bindDate(stat,73, sintomaCaso.getQuedoCamaFecFin());
        bindDate(stat,74, sintomaCaso.getRespiracionRuidosaFecFin());
        bindDate(stat,75, sintomaCaso.getRespiracionRapidaFecFin());
        bindDate(stat,76, sintomaCaso.getPerdidaOlfatoFecFin());
        bindDate(stat,77, sintomaCaso.getCongestionNasalFecFin());
        bindDate(stat,78, sintomaCaso.getPerdidaGustoFecFin());
        bindDate(stat,79, sintomaCaso.getDesmayosFecFin());
        bindDate(stat,80, sintomaCaso.getSensacionPechoApretadoFecFin());
        bindDate(stat,81, sintomaCaso.getDolorPechoFecFin());
        bindDate(stat,82, sintomaCaso.getSensacionFaltaAireFecFin());
        bindDate(stat,83, sintomaCaso.getFatigaFecFin());

        bindDate(stat,84, sintomaCaso.getRecordDate());
        bindString(stat,85, sintomaCaso.getRecordUser());
        stat.bindString(86, String.valueOf(sintomaCaso.getPasive()));
        bindString(stat,87, sintomaCaso.getDeviceid());
        stat.bindString(88, String.valueOf(sintomaCaso.getEstado()));
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
