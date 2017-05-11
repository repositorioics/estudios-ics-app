package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaDatosPartoBB;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaParticipante;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class EncuestaParticipanteHelper {

    public static ContentValues crearEncuestaParticipanteContentValues(EncuestaParticipante encuesta){
        ContentValues cv = new ContentValues();

        cv.put(EncuestasDBConstants.participante, encuesta.getParticipante().getCodigo());
        cv.put(EncuestasDBConstants.emancipado, String.valueOf(encuesta.getEmancipado()));
        cv.put(EncuestasDBConstants.motivoEmacipacion, encuesta.getMotivoEmacipacion());
        cv.put(EncuestasDBConstants.otroMotivoEmancipacion, encuesta.getOtroMotivoEmancipacion());
        cv.put(EncuestasDBConstants.estaEmbarazada, String.valueOf(encuesta.getEstaEmbarazada()));
        cv.put(EncuestasDBConstants.semanasEmbarazo, encuesta.getSemanasEmbarazo());
        cv.put(EncuestasDBConstants.esAlfabeto, String.valueOf(encuesta.getEsAlfabeto()));
        cv.put(EncuestasDBConstants.nivelEducacion, encuesta.getNivelEducacion());
        cv.put(EncuestasDBConstants.trabaja, String.valueOf(encuesta.getTrabaja()));
        cv.put(EncuestasDBConstants.tipoTrabajo, encuesta.getTipoTrabajo());
        cv.put(EncuestasDBConstants.ocupacionActual, encuesta.getOcupacionActual());
        cv.put(EncuestasDBConstants.vaNinoEscuela, String.valueOf(encuesta.getVaNinoEscuela()));
        cv.put(EncuestasDBConstants.gradoCursa, encuesta.getGradoCursa());
        cv.put(EncuestasDBConstants.turno, encuesta.getTurno());
        cv.put(EncuestasDBConstants.nombreCentroEstudio, encuesta.getNombreCentroEstudio());
        cv.put(EncuestasDBConstants.dondeCuidanNino, encuesta.getDondeCuidanNino());
        cv.put(EncuestasDBConstants.ninoTrabaja, String.valueOf(encuesta.getNinoTrabaja()));
        cv.put(EncuestasDBConstants.ocupacionActualNino, encuesta.getOcupacionActual());
        cv.put(EncuestasDBConstants.cantNinosLugarCuidan, encuesta.getCantNinosLugarCuidan());
        cv.put(EncuestasDBConstants.conQuienViveNino, encuesta.getConQuienViveNino());
        cv.put(EncuestasDBConstants.descOtroViveNino, encuesta.getDescOtroViveNino());
        cv.put(EncuestasDBConstants.padreEnEstudio, String.valueOf(encuesta.getPadreEnEstudio()));
        cv.put(EncuestasDBConstants.codigoPadreEstudio, encuesta.getCodigoPadreEstudio());
        cv.put(EncuestasDBConstants.padreAlfabeto, String.valueOf(encuesta.getPadreAlfabeto()));
        cv.put(EncuestasDBConstants.nivelEducacionPadre, encuesta.getNivelEducacionPadre());
        cv.put(EncuestasDBConstants.trabajaPadre, String.valueOf(encuesta.getTrabajaPadre()));
        cv.put(EncuestasDBConstants.tipoTrabajoPadre, encuesta.getTipoTrabajoPadre());
        cv.put(EncuestasDBConstants.madreEnEstudio, String.valueOf(encuesta.getMadreEnEstudio()));
        cv.put(EncuestasDBConstants.codigoMadreEstudio, encuesta.getCodigoMadreEstudio());
        cv.put(EncuestasDBConstants.madreAlfabeto, String.valueOf(encuesta.getMadreAlfabeto()));
        cv.put(EncuestasDBConstants.nivelEducacionMadre, encuesta.getNivelEducacionMadre());
        cv.put(EncuestasDBConstants.trabajaMadre, String.valueOf(encuesta.getTrabajaMadre()));
        cv.put(EncuestasDBConstants.tipoTrabajoMadre, encuesta.getTipoTrabajoMadre());
        cv.put(EncuestasDBConstants.fuma, String.valueOf(encuesta.getFuma()));
        cv.put(EncuestasDBConstants.periodicidadFuma, encuesta.getPeriodicidadFuma());
        cv.put(EncuestasDBConstants.cantidadCigarrillos, encuesta.getCantidadCigarrillos());
        cv.put(EncuestasDBConstants.fumaDentroCasa, String.valueOf(encuesta.getFumaDentroCasa()));
        cv.put(EncuestasDBConstants.tuberculosisPulmonarActual, String.valueOf(encuesta.getTuberculosisPulmonarActual()));
        cv.put(EncuestasDBConstants.fechaDiagnosticoTubPulActual, encuesta.getFechaDiagnosticoTubPulActual());
        cv.put(EncuestasDBConstants.tomaTratamientoTubPulActual, String.valueOf(encuesta.getTomaTratamientoTubPulActual()));
        cv.put(EncuestasDBConstants.completoTratamientoTubPulActual, String.valueOf(encuesta.getCompletoTratamientoTubPulActual()));
        cv.put(EncuestasDBConstants.tuberculosisPulmonarPasado, String.valueOf(encuesta.getTuberculosisPulmonarPasado()));
        cv.put(EncuestasDBConstants.fechaDiagnosticoTubPulPasado, encuesta.getFechaDiagnosticoTubPulPasado());
        cv.put(EncuestasDBConstants.tomaTratamientoTubPulPasado, String.valueOf(encuesta.getTomaTratamientoTubPulPasado()));
        cv.put(EncuestasDBConstants.completoTratamientoTubPulPasado, String.valueOf(encuesta.getCompletoTratamientoTubPulPasado()));
        cv.put(EncuestasDBConstants.alergiaRespiratoria, String.valueOf(encuesta.getAlergiaRespiratoria()));
        cv.put(EncuestasDBConstants.cardiopatia, String.valueOf(encuesta.getCardiopatia()));
        cv.put(EncuestasDBConstants.enfermedadPulmonarOC, String.valueOf(encuesta.getEnfermedadPulmonarOC()));
        cv.put(EncuestasDBConstants.diabetes, String.valueOf(encuesta.getDiabetes()));
        cv.put(EncuestasDBConstants.presionAlta, String.valueOf(encuesta.getPresionAlta()));
        cv.put(EncuestasDBConstants.asma, String.valueOf(encuesta.getAsma()));
        cv.put(EncuestasDBConstants.silvidoRespirarPechoApretado, String.valueOf(encuesta.getSilvidoRespirarPechoApretado()));
        cv.put(EncuestasDBConstants.tosSinFiebreResfriado, String.valueOf(encuesta.getTosSinFiebreResfriado()));
        cv.put(EncuestasDBConstants.usaInhaladoresSpray, String.valueOf(encuesta.getUsaInhaladoresSpray()));
        cv.put(EncuestasDBConstants.crisisAsma, String.valueOf(encuesta.getCrisisAsma()));
        cv.put(EncuestasDBConstants.cantidadCrisisAsma, encuesta.getCantidadCrisisAsma());
        cv.put(EncuestasDBConstants.vecesEnfermoEnfermedadesRes, encuesta.getVecesEnfermoEnfermedadesRes());
        cv.put(EncuestasDBConstants.otrasEnfermedades, String.valueOf(encuesta.getOtrasEnfermedades()));
        cv.put(EncuestasDBConstants.descOtrasEnfermedades, encuesta.getDescOtrasEnfermedades());
        cv.put(EncuestasDBConstants.vacunaInfluenza, String.valueOf(encuesta.getVacunaInfluenza()));
        cv.put(EncuestasDBConstants.anioVacunaInfluenza, encuesta.getAnioVacunaInfluenza());
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));

        return cv;
    }

    public static EncuestaParticipante crearEncuestaParticipante(Cursor cursor){
        EncuestaParticipante encuesta = new EncuestaParticipante();

        encuesta.setParticipante(null);
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.emancipado))!=null) encuesta.setEmancipado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.emancipado)).charAt(0));
        encuesta.setMotivoEmacipacion(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.motivoEmacipacion)));
        encuesta.setOtroMotivoEmancipacion(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMotivoEmancipacion)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.estaEmbarazada))!=null) encuesta.setEstaEmbarazada(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.estaEmbarazada)).charAt(0));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.semanasEmbarazo))>0) encuesta.setSemanasEmbarazo(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.semanasEmbarazo)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.esAlfabeto))!=null) encuesta.setEsAlfabeto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.esAlfabeto)).charAt(0));
        encuesta.setNivelEducacion(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.nivelEducacion)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabaja))!=null) encuesta.setTrabaja(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabaja)).charAt(0));
        encuesta.setTipoTrabajo(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tipoTrabajo)));
        encuesta.setOcupacionActual(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ocupacionActual)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.vaNinoEscuela))!=null) encuesta.setVaNinoEscuela(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.vaNinoEscuela)).charAt(0));
        encuesta.setGradoCursa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.gradoCursa)));
        encuesta.setTurno(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.turno)));
        encuesta.setNombreCentroEstudio(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.nombreCentroEstudio)));
        encuesta.setDondeCuidanNino(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.dondeCuidanNino)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ninoTrabaja))!=null) encuesta.setNinoTrabaja(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ninoTrabaja)).charAt(0));
        encuesta.setOcupacionActualNino(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ocupacionActualNino)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantNinosLugarCuidan))>0) encuesta.setCantNinosLugarCuidan(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantNinosLugarCuidan)));
        encuesta.setConQuienViveNino(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.conQuienViveNino)));
        encuesta.setDescOtroViveNino(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.descOtroViveNino)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.padreEnEstudio))!=null) encuesta.setPadreEnEstudio(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.padreEnEstudio)).charAt(0));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.codigoPadreEstudio))>0) encuesta.setCodigoPadreEstudio(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.codigoPadreEstudio)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.padreAlfabeto))!=null) encuesta.setPadreAlfabeto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.padreAlfabeto)).charAt(0));
        encuesta.setNivelEducacionPadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.nivelEducacionPadre)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabajaPadre))!=null) encuesta.setTrabajaPadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabajaPadre)).charAt(0));
        encuesta.setTipoTrabajoPadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tipoTrabajoPadre)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.madreEnEstudio))!=null) encuesta.setMadreEnEstudio(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.madreEnEstudio)).charAt(0));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.codigoMadreEstudio))>0) encuesta.setCodigoMadreEstudio(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.codigoMadreEstudio)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.madreAlfabeto))!=null) encuesta.setMadreAlfabeto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.madreAlfabeto)).charAt(0));
        encuesta.setNivelEducacionMadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.nivelEducacionMadre)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabajaMadre))!=null) encuesta.setTrabajaMadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.trabajaMadre)).charAt(0));
        encuesta.setTipoTrabajoMadre(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tipoTrabajoMadre)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fuma))!=null) encuesta.setFuma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fuma)).charAt(0));
        encuesta.setPeriodicidadFuma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.periodicidadFuma)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillos))>0) encuesta.setCantidadCigarrillos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillos)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fumaDentroCasa))!=null) encuesta.setFumaDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fumaDentroCasa)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tuberculosisPulmonarActual))!=null) encuesta.setTuberculosisPulmonarActual(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tuberculosisPulmonarActual)).charAt(0));
        encuesta.setFechaDiagnosticoTubPulActual(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fechaDiagnosticoTubPulActual)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tomaTratamientoTubPulActual))!=null) encuesta.setTomaTratamientoTubPulActual(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tomaTratamientoTubPulActual)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.completoTratamientoTubPulActual))!=null) encuesta.setCompletoTratamientoTubPulActual(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.completoTratamientoTubPulActual)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tuberculosisPulmonarPasado))!=null) encuesta.setTuberculosisPulmonarPasado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tuberculosisPulmonarPasado)).charAt(0));
        encuesta.setFechaDiagnosticoTubPulPasado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.fechaDiagnosticoTubPulPasado)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tomaTratamientoTubPulPasado))!=null) encuesta.setTomaTratamientoTubPulPasado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tomaTratamientoTubPulPasado)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.completoTratamientoTubPulPasado))!=null) encuesta.setCompletoTratamientoTubPulPasado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.completoTratamientoTubPulPasado)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.alergiaRespiratoria))!=null) encuesta.setAlergiaRespiratoria(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.alergiaRespiratoria)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.cardiopatia))!=null) encuesta.setCardiopatia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.cardiopatia)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.enfermedadPulmonarOC))!=null) encuesta.setEnfermedadPulmonarOC(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.enfermedadPulmonarOC)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.diabetes))!=null) encuesta.setDiabetes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.diabetes)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.presionAlta))!=null) encuesta.setPresionAlta(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.presionAlta)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.asma))!=null) encuesta.setAsma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.asma)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.silvidoRespirarPechoApretado))!=null) encuesta.setSilvidoRespirarPechoApretado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.silvidoRespirarPechoApretado)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tosSinFiebreResfriado))!=null) encuesta.setTosSinFiebreResfriado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tosSinFiebreResfriado)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.usaInhaladoresSpray))!=null) encuesta.setUsaInhaladoresSpray(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.usaInhaladoresSpray)).charAt(0));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.crisisAsma))!=null) encuesta.setCrisisAsma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.crisisAsma)).charAt(0));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCrisisAsma))>0) encuesta.setCantidadCrisisAsma(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCrisisAsma)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.vecesEnfermoEnfermedadesRes))>0) encuesta.setVecesEnfermoEnfermedadesRes(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.vecesEnfermoEnfermedadesRes)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrasEnfermedades))!=null) encuesta.setOtrasEnfermedades(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrasEnfermedades)).charAt(0));
        encuesta.setDescOtrasEnfermedades(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.descOtrasEnfermedades)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.vacunaInfluenza))!=null) encuesta.setVacunaInfluenza(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.vacunaInfluenza)).charAt(0));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.anioVacunaInfluenza))>0) encuesta.setAnioVacunaInfluenza(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.anioVacunaInfluenza)));

        encuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));

        return encuesta;
    }
}
