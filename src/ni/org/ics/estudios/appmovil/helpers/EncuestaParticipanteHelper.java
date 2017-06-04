package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaParticipante;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class EncuestaParticipanteHelper {

    public static ContentValues crearEncuestaParticipanteContentValues(EncuestaParticipante encuesta){
        ContentValues cv = new ContentValues();

        cv.put(EncuestasDBConstants.participante, encuesta.getParticipante().getParticipante().getCodigo());
        //cv.put(EncuestasDBConstants.emancipado, encuesta.getEmancipado());
        //cv.put(EncuestasDBConstants.motivoEmacipacion, encuesta.getMotivoEmacipacion());
        //cv.put(EncuestasDBConstants.otroMotivoEmancipacion, encuesta.getOtroMotivoEmancipacion());
        cv.put(EncuestasDBConstants.estaEmbarazada, encuesta.getEstaEmbarazada());
        cv.put(EncuestasDBConstants.semanasEmbarazo, encuesta.getSemanasEmbarazo());
        cv.put(EncuestasDBConstants.esAlfabeto, encuesta.getEsAlfabeto());
        cv.put(EncuestasDBConstants.nivelEducacion, encuesta.getNivelEducacion());
        cv.put(EncuestasDBConstants.trabaja, encuesta.getTrabaja());
        cv.put(EncuestasDBConstants.tipoTrabajo, encuesta.getTipoTrabajo());
        cv.put(EncuestasDBConstants.ocupacionActual, encuesta.getOcupacionActual());
        cv.put(EncuestasDBConstants.vaNinoEscuela, encuesta.getVaNinoEscuela());
        cv.put(EncuestasDBConstants.gradoCursa, encuesta.getGradoCursa());
        cv.put(EncuestasDBConstants.turno, encuesta.getTurno());
        cv.put(EncuestasDBConstants.centroEstudio, encuesta.getCentroEstudio());
        cv.put(EncuestasDBConstants.nombreCentroEstudio, encuesta.getNombreCentroEstudio());
        cv.put(EncuestasDBConstants.dondeCuidanNino, encuesta.getDondeCuidanNino());
        cv.put(EncuestasDBConstants.ninoTrabaja, encuesta.getNinoTrabaja());
        cv.put(EncuestasDBConstants.ocupacionActualNino, encuesta.getOcupacionActual());
        cv.put(EncuestasDBConstants.cantNinosLugarCuidan, encuesta.getCantNinosLugarCuidan());
        cv.put(EncuestasDBConstants.conQuienViveNino, encuesta.getConQuienViveNino());
        cv.put(EncuestasDBConstants.descOtroViveNino, encuesta.getDescOtroViveNino());
        cv.put(EncuestasDBConstants.padreEnEstudio, encuesta.getPadreEnEstudio());
        cv.put(EncuestasDBConstants.codigoPadreEstudio, encuesta.getCodigoPadreEstudio());
        cv.put(EncuestasDBConstants.padreAlfabeto, encuesta.getPadreAlfabeto());
        cv.put(EncuestasDBConstants.nivelEducacionPadre, encuesta.getNivelEducacionPadre());
        cv.put(EncuestasDBConstants.trabajaPadre, encuesta.getTrabajaPadre());
        cv.put(EncuestasDBConstants.tipoTrabajoPadre, encuesta.getTipoTrabajoPadre());
        cv.put(EncuestasDBConstants.madreEnEstudio, encuesta.getMadreEnEstudio());
        cv.put(EncuestasDBConstants.codigoMadreEstudio, encuesta.getCodigoMadreEstudio());
        cv.put(EncuestasDBConstants.madreAlfabeto, encuesta.getMadreAlfabeto());
        cv.put(EncuestasDBConstants.nivelEducacionMadre, encuesta.getNivelEducacionMadre());
        cv.put(EncuestasDBConstants.trabajaMadre, encuesta.getTrabajaMadre());
        cv.put(EncuestasDBConstants.tipoTrabajoMadre, encuesta.getTipoTrabajoMadre());
        cv.put(EncuestasDBConstants.fuma, encuesta.getFuma());
        cv.put(EncuestasDBConstants.periodicidadFuma, encuesta.getPeriodicidadFuma());
        cv.put(EncuestasDBConstants.cantidadCigarrillos, encuesta.getCantidadCigarrillos());
        cv.put(EncuestasDBConstants.fumaDentroCasa, encuesta.getFumaDentroCasa());
        cv.put(EncuestasDBConstants.tuberculosisPulmonarActual, encuesta.getTuberculosisPulmonarActual());
        cv.put(EncuestasDBConstants.fechaDiagnosticoTubPulActual, encuesta.getFechaDiagnosticoTubPulActual());
        cv.put(EncuestasDBConstants.tomaTratamientoTubPulActual, encuesta.getTomaTratamientoTubPulActual());
        cv.put(EncuestasDBConstants.completoTratamientoTubPulActual, encuesta.getCompletoTratamientoTubPulActual());
        cv.put(EncuestasDBConstants.tuberculosisPulmonarPasado, encuesta.getTuberculosisPulmonarPasado());
        cv.put(EncuestasDBConstants.fechaDiagnosticoTubPulPasado, encuesta.getFechaDiagnosticoTubPulPasado());
        cv.put(EncuestasDBConstants.fechaDiagnosticoTubPulPasadoDes, encuesta.getFechaDiagnosticoTubPulPasadoDes());
        cv.put(EncuestasDBConstants.tomaTratamientoTubPulPasado, encuesta.getTomaTratamientoTubPulPasado());
        cv.put(EncuestasDBConstants.completoTratamientoTubPulPasado, encuesta.getCompletoTratamientoTubPulPasado());
        cv.put(EncuestasDBConstants.alergiaRespiratoria, encuesta.getAlergiaRespiratoria());
        cv.put(EncuestasDBConstants.cardiopatia, encuesta.getCardiopatia());
        cv.put(EncuestasDBConstants.enfermedadPulmonarOC, encuesta.getEnfermedadPulmonarOC());
        cv.put(EncuestasDBConstants.diabetes, encuesta.getDiabetes());
        cv.put(EncuestasDBConstants.presionAlta, encuesta.getPresionAlta());
        cv.put(EncuestasDBConstants.asma, encuesta.getAsma());
        cv.put(EncuestasDBConstants.silvidoRespirarPechoApretado, encuesta.getSilbidoRespirarPechoApretado());
        cv.put(EncuestasDBConstants.tosSinFiebreResfriado, encuesta.getTosSinFiebreResfriado());
        cv.put(EncuestasDBConstants.usaInhaladoresSpray, encuesta.getUsaInhaladoresSpray());
        cv.put(EncuestasDBConstants.crisisAsma, encuesta.getCrisisAsma());
        cv.put(EncuestasDBConstants.cantidadCrisisAsma, encuesta.getCantidadCrisisAsma());
        cv.put(EncuestasDBConstants.vecesEnfermoEnfermedadesRes, encuesta.getVecesEnfermoEnfermedadesRes());
        cv.put(EncuestasDBConstants.otrasEnfermedades, encuesta.getOtrasEnfermedades());
        cv.put(EncuestasDBConstants.descOtrasEnfermedades, encuesta.getDescOtrasEnfermedades());
        cv.put(EncuestasDBConstants.vacunaInfluenza, encuesta.getVacunaInfluenza());
        cv.put(EncuestasDBConstants.anioVacunaInfluenza, encuesta.getAnioVacunaInfluenza());
        if (encuesta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, encuesta.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, encuesta.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(encuesta.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));
        cv.put(MainDBConstants.deviceId, encuesta.getDeviceid());

        return cv;
    }

    public static EncuestaParticipante crearEncuestaParticipante(Cursor cursor){
        EncuestaParticipante mEncuesta = new EncuestaParticipante();

        mEncuesta.setParticipante(null);
        //if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.emancipado))!=null) mEncuesta.setEmancipado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.emancipado)));
        //mEncuesta.setMotivoEmacipacion(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.motivoEmacipacion)));
        //mEncuesta.setOtroMotivoEmancipacion(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMotivoEmancipacion)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.estaEmbarazada))!=null) mEncuesta.setEstaEmbarazada(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.estaEmbarazada)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.semanasEmbarazo))>0) mEncuesta.setSemanasEmbarazo(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.semanasEmbarazo)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.esAlfabeto))!=null) mEncuesta.setEsAlfabeto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.esAlfabeto)));
        mEncuesta.setNivelEducacion(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.nivelEducacion)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabaja))!=null) mEncuesta.setTrabaja(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabaja)));
        mEncuesta.setTipoTrabajo(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tipoTrabajo)));
        mEncuesta.setOcupacionActual(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ocupacionActual)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.vaNinoEscuela))!=null) mEncuesta.setVaNinoEscuela(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.vaNinoEscuela)));
        mEncuesta.setGradoCursa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.gradoCursa)));
        mEncuesta.setTurno(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.turno)));
        mEncuesta.setCentroEstudio(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.centroEstudio)));
        mEncuesta.setNombreCentroEstudio(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.nombreCentroEstudio)));
        mEncuesta.setDondeCuidanNino(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.dondeCuidanNino)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ninoTrabaja))!=null) mEncuesta.setNinoTrabaja(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ninoTrabaja)));
        mEncuesta.setOcupacionActualNino(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ocupacionActualNino)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantNinosLugarCuidan))>0) mEncuesta.setCantNinosLugarCuidan(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantNinosLugarCuidan)));
        mEncuesta.setConQuienViveNino(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.conQuienViveNino)));
        mEncuesta.setDescOtroViveNino(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.descOtroViveNino)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.padreEnEstudio))!=null) mEncuesta.setPadreEnEstudio(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.padreEnEstudio)));
        mEncuesta.setCodigoPadreEstudio(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.codigoPadreEstudio)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.padreAlfabeto))!=null) mEncuesta.setPadreAlfabeto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.padreAlfabeto)));
        mEncuesta.setNivelEducacionPadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.nivelEducacionPadre)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabajaPadre))!=null) mEncuesta.setTrabajaPadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabajaPadre)));
        mEncuesta.setTipoTrabajoPadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tipoTrabajoPadre)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.madreEnEstudio))!=null) mEncuesta.setMadreEnEstudio(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.madreEnEstudio)));
        mEncuesta.setCodigoMadreEstudio(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.codigoMadreEstudio)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.madreAlfabeto))!=null) mEncuesta.setMadreAlfabeto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.madreAlfabeto)));
        mEncuesta.setNivelEducacionMadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.nivelEducacionMadre)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabajaMadre))!=null) mEncuesta.setTrabajaMadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabajaMadre)));
        mEncuesta.setTipoTrabajoMadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tipoTrabajoMadre)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fuma))!=null) mEncuesta.setFuma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fuma)));
        mEncuesta.setPeriodicidadFuma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.periodicidadFuma)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillos))>0) mEncuesta.setCantidadCigarrillos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillos)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fumaDentroCasa))!=null) mEncuesta.setFumaDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fumaDentroCasa)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tuberculosisPulmonarActual))!=null) mEncuesta.setTuberculosisPulmonarActual(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tuberculosisPulmonarActual)));
        mEncuesta.setFechaDiagnosticoTubPulActual(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fechaDiagnosticoTubPulActual)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tomaTratamientoTubPulActual))!=null) mEncuesta.setTomaTratamientoTubPulActual(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tomaTratamientoTubPulActual)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.completoTratamientoTubPulActual))!=null) mEncuesta.setCompletoTratamientoTubPulActual(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.completoTratamientoTubPulActual)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tuberculosisPulmonarPasado))!=null) mEncuesta.setTuberculosisPulmonarPasado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tuberculosisPulmonarPasado)));
        mEncuesta.setFechaDiagnosticoTubPulPasado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fechaDiagnosticoTubPulPasado)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fechaDiagnosticoTubPulPasadoDes))!=null) mEncuesta.setFechaDiagnosticoTubPulPasadoDes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fechaDiagnosticoTubPulPasadoDes)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tomaTratamientoTubPulPasado))!=null) mEncuesta.setTomaTratamientoTubPulPasado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tomaTratamientoTubPulPasado)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.completoTratamientoTubPulPasado))!=null) mEncuesta.setCompletoTratamientoTubPulPasado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.completoTratamientoTubPulPasado)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.alergiaRespiratoria))!=null) mEncuesta.setAlergiaRespiratoria(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.alergiaRespiratoria)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.cardiopatia))!=null) mEncuesta.setCardiopatia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.cardiopatia)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.enfermedadPulmonarOC))!=null) mEncuesta.setEnfermedadPulmonarOC(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.enfermedadPulmonarOC)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.diabetes))!=null) mEncuesta.setDiabetes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.diabetes)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.presionAlta))!=null) mEncuesta.setPresionAlta(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.presionAlta)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.asma))!=null) mEncuesta.setAsma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.asma)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.silvidoRespirarPechoApretado))!=null) mEncuesta.setSilbidoRespirarPechoApretado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.silvidoRespirarPechoApretado)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tosSinFiebreResfriado))!=null) mEncuesta.setTosSinFiebreResfriado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tosSinFiebreResfriado)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.usaInhaladoresSpray))!=null) mEncuesta.setUsaInhaladoresSpray(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.usaInhaladoresSpray)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.crisisAsma))!=null) mEncuesta.setCrisisAsma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.crisisAsma)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCrisisAsma))>0) mEncuesta.setCantidadCrisisAsma(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCrisisAsma)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.vecesEnfermoEnfermedadesRes))>0) mEncuesta.setVecesEnfermoEnfermedadesRes(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.vecesEnfermoEnfermedadesRes)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrasEnfermedades))!=null) mEncuesta.setOtrasEnfermedades(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrasEnfermedades)));
        mEncuesta.setDescOtrasEnfermedades(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.descOtrasEnfermedades)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.vacunaInfluenza))!=null) mEncuesta.setVacunaInfluenza(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.vacunaInfluenza)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.anioVacunaInfluenza))>0) mEncuesta.setAnioVacunaInfluenza(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.anioVacunaInfluenza)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mEncuesta.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mEncuesta.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mEncuesta.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mEncuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mEncuesta.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mEncuesta;
    }
}
