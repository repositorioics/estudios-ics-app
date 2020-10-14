package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.covid19.CuestionarioCovid19;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

public class CuestionarioCovid19Helper {

    public static ContentValues crearCuestionarioCovid19ContentValues(CuestionarioCovid19 cuestionario){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.codigo, cuestionario.getCodigo());
        if (cuestionario.getParticipante()!=null) cv.put(Covid19DBConstants.participante, cuestionario.getParticipante().getCodigo());
        cv.put(Covid19DBConstants.feb20Febricula, cuestionario.getFeb20Febricula());
        cv.put(Covid19DBConstants.feb20Fiebre, cuestionario.getFeb20Fiebre());
        cv.put(Covid19DBConstants.feb20Escalofrio, cuestionario.getFeb20Escalofrio());
        cv.put(Covid19DBConstants.feb20TemblorEscalofrio, cuestionario.getFeb20TemblorEscalofrio());
        cv.put(Covid19DBConstants.feb20DolorMuscular, cuestionario.getFeb20DolorMuscular());
        cv.put(Covid19DBConstants.feb20DolorArticular, cuestionario.getFeb20DolorArticular());
        cv.put(Covid19DBConstants.feb20SecresionNasal, cuestionario.getFeb20SecresionNasal());
        cv.put(Covid19DBConstants.feb20DolorGarganta, cuestionario.getFeb20DolorGarganta());
        cv.put(Covid19DBConstants.feb20Tos, cuestionario.getFeb20Tos());
        cv.put(Covid19DBConstants.feb20DificultadResp, cuestionario.getFeb20DificultadResp());
        cv.put(Covid19DBConstants.feb20DolorPecho, cuestionario.getFeb20DolorPecho());
        cv.put(Covid19DBConstants.feb20NauseasVomito, cuestionario.getFeb20NauseasVomito());
        cv.put(Covid19DBConstants.feb20DolorCabeza, cuestionario.getFeb20DolorCabeza());
        cv.put(Covid19DBConstants.feb20DolorAbdominal, cuestionario.getFeb20DolorAbdominal());
        cv.put(Covid19DBConstants.feb20Diarrea, cuestionario.getFeb20Diarrea());
        cv.put(Covid19DBConstants.feb20DificultadDormir, cuestionario.getFeb20DificultadDormir());
        cv.put(Covid19DBConstants.feb20Debilidad, cuestionario.getFeb20Debilidad());
        cv.put(Covid19DBConstants.feb20PerdidaOlfatoGusto, cuestionario.getFeb20PerdidaOlfatoGusto());
        cv.put(Covid19DBConstants.feb20Mareo, cuestionario.getFeb20Mareo());
        cv.put(Covid19DBConstants.feb20Sarpullido, cuestionario.getFeb20Sarpullido());
        cv.put(Covid19DBConstants.feb20Desmayo, cuestionario.getFeb20Desmayo());
        cv.put(Covid19DBConstants.feb20QuedoCama, cuestionario.getFeb20QuedoCama());
        cv.put(Covid19DBConstants.sabeFIS, cuestionario.getSabeFIS());
        if (cuestionario.getFis() != null) cv.put(Covid19DBConstants.fis, cuestionario.getFis().getTime());
        cv.put(Covid19DBConstants.mesInicioSintoma, cuestionario.getMesInicioSintoma());
        cv.put(Covid19DBConstants.anioInicioSintoma, cuestionario.getAnioInicioSintoma());
        cv.put(Covid19DBConstants.padecidoCovid19, cuestionario.getPadecidoCovid19());
        cv.put(Covid19DBConstants.conoceLugarExposicion, cuestionario.getConoceLugarExposicion());
        cv.put(Covid19DBConstants.lugarExposicion, cuestionario.getLugarExposicion());
        cv.put(Covid19DBConstants.buscoAyuda, cuestionario.getBuscoAyuda());
        cv.put(Covid19DBConstants.dondeBuscoAyuda, cuestionario.getDondeBuscoAyuda());
        cv.put(Covid19DBConstants.nombreCentroSalud, cuestionario.getNombreCentroSalud());
        cv.put(Covid19DBConstants.nombreHospital, cuestionario.getNombreHospital());
        cv.put(Covid19DBConstants.recibioSeguimiento, cuestionario.getRecibioSeguimiento());
        cv.put(Covid19DBConstants.tipoSeguimiento, cuestionario.getTipoSeguimiento());
        cv.put(Covid19DBConstants.tmpDespuesBuscoAyuda, cuestionario.getTmpDespuesBuscoAyuda());
        cv.put(Covid19DBConstants.unaNocheHospital, cuestionario.getUnaNocheHospital());
        cv.put(Covid19DBConstants.queHospital, cuestionario.getQueHospital());
        cv.put(Covid19DBConstants.sabeCuantasNoches, cuestionario.getSabeCuantasNoches());
        cv.put(Covid19DBConstants.cuantasNochesHosp, cuestionario.getCuantasNochesHosp());
        cv.put(Covid19DBConstants.sabeFechaAdmision, cuestionario.getSabeFechaAdmision());
        if (cuestionario.getFechaAdmisionHosp() != null) cv.put(Covid19DBConstants.fechaAdmisionHosp, cuestionario.getFechaAdmisionHosp().getTime());
        cv.put(Covid19DBConstants.sabeFechaAlta, cuestionario.getSabeFechaAlta());
        if (cuestionario.getFechaAltaHosp() != null) cv.put(Covid19DBConstants.fechaAltaHosp, cuestionario.getFechaAltaHosp().getTime());
        cv.put(Covid19DBConstants.utilizoOxigeno, cuestionario.getUtilizoOxigeno());
        cv.put(Covid19DBConstants.estuvoUCI, cuestionario.getEstuvoUCI());
        cv.put(Covid19DBConstants.fueIntubado, cuestionario.getFueIntubado());
        cv.put(Covid19DBConstants.recuperadoCovid19, cuestionario.getRecuperadoCovid19());
        cv.put(Covid19DBConstants.febricula, cuestionario.getFebricula());
        cv.put(Covid19DBConstants.cansancio, cuestionario.getCansancio());
        cv.put(Covid19DBConstants.dolorCabeza, cuestionario.getDolorCabeza());
        cv.put(Covid19DBConstants.perdidaOlfato, cuestionario.getPerdidaOlfato());
        cv.put(Covid19DBConstants.perdidaGusto, cuestionario.getPerdidaGusto());
        cv.put(Covid19DBConstants.tos, cuestionario.getTos());
        cv.put(Covid19DBConstants.dificultadRespirar, cuestionario.getDificultadRespirar());
        cv.put(Covid19DBConstants.dolorPecho, cuestionario.getDolorPecho());
        cv.put(Covid19DBConstants.palpitaciones, cuestionario.getPalpitaciones());
        cv.put(Covid19DBConstants.dolorArticulaciones, cuestionario.getDolorArticulaciones());
        cv.put(Covid19DBConstants.paralisis, cuestionario.getParalisis());
        cv.put(Covid19DBConstants.mareos, cuestionario.getMareos());
        cv.put(Covid19DBConstants.pensamientoNublado, cuestionario.getPensamientoNublado());
        cv.put(Covid19DBConstants.problemasDormir, cuestionario.getProblemasDormir());
        cv.put(Covid19DBConstants.depresion, cuestionario.getDepresion());
        cv.put(Covid19DBConstants.otrosSintomas, cuestionario.getOtrosSintomas());
        cv.put(Covid19DBConstants.cualesSintomas, cuestionario.getCualesSintomas());
        cv.put(Covid19DBConstants.sabeTiempoRecuperacion, cuestionario.getSabeTiempoRecuperacion());
        cv.put(Covid19DBConstants.tiempoRecuperacion, cuestionario.getTiempoRecuperacion());
        cv.put(Covid19DBConstants.tiempoRecuperacionEn, cuestionario.getTiempoRecuperacionEn());
        cv.put(Covid19DBConstants.severidadEnfermedad, cuestionario.getSeveridadEnfermedad());
        cv.put(Covid19DBConstants.tomoMedicamento, cuestionario.getTomoMedicamento());
        cv.put(Covid19DBConstants.queMedicamento, cuestionario.getQueMedicamento());
        cv.put(Covid19DBConstants.otroMedicamento, cuestionario.getOtroMedicamento());
        cv.put(Covid19DBConstants.padeceEnfisema, cuestionario.getPadeceEnfisema());
        cv.put(Covid19DBConstants.padeceAsma, cuestionario.getPadeceAsma());
        cv.put(Covid19DBConstants.padeceDiabetes, cuestionario.getPadeceDiabetes());
        cv.put(Covid19DBConstants.padeceEnfCoronaria, cuestionario.getPadeceEnfCoronaria());
        cv.put(Covid19DBConstants.padecePresionAlta, cuestionario.getPadecePresionAlta());
        cv.put(Covid19DBConstants.padeceEnfHigado, cuestionario.getPadeceEnfHigado());
        cv.put(Covid19DBConstants.padeceEnfRenal, cuestionario.getPadeceEnfRenal());
        cv.put(Covid19DBConstants.padeceInfartoDerrameCer, cuestionario.getPadeceInfartoDerrameCer());
        cv.put(Covid19DBConstants.padeceCancer, cuestionario.getPadeceCancer());
        cv.put(Covid19DBConstants.padeceCondicionInmuno, cuestionario.getPadeceCondicionInmuno());
        cv.put(Covid19DBConstants.padeceEnfAutoinmune, cuestionario.getPadeceEnfAutoinmune());
        cv.put(Covid19DBConstants.padeceDiscapacidadFis, cuestionario.getPadeceDiscapacidadFis());
        cv.put(Covid19DBConstants.padeceCondPsicPsiq, cuestionario.getPadeceCondPsicPsiq());
        cv.put(Covid19DBConstants.padeceOtraCondicion, cuestionario.getPadeceOtraCondicion());
        cv.put(Covid19DBConstants.queOtraCondicion, cuestionario.getQueOtraCondicion());
        cv.put(Covid19DBConstants.fumado, cuestionario.getFumado());
        cv.put(Covid19DBConstants.fumadoCienCigarrillos, cuestionario.getFumadoCienCigarrillos());
        cv.put(Covid19DBConstants.fumadoPrevioEnfermedad, cuestionario.getFumadoPrevioEnfermedad());
        cv.put(Covid19DBConstants.fumaActualmente, cuestionario.getFumaActualmente());

        if (cuestionario.getRecordDate() != null) cv.put(MainDBConstants.recordDate, cuestionario.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, cuestionario.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(cuestionario.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(cuestionario.getEstado()));
        cv.put(MainDBConstants.deviceId, cuestionario.getDeviceid());
        return cv;
    }

    public static CuestionarioCovid19 crearCuestionarioCovid19(Cursor cursor){
        CuestionarioCovid19 cuestionarioCovid19 = new CuestionarioCovid19();

        cuestionarioCovid19.setCodigo(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigo)));
        cuestionarioCovid19.setParticipante(null);
        cuestionarioCovid19.setFeb20Febricula(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20Febricula)));
        cuestionarioCovid19.setFeb20Fiebre(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20Fiebre)));
        cuestionarioCovid19.setFeb20Escalofrio(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20Escalofrio)));
        cuestionarioCovid19.setFeb20TemblorEscalofrio(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20TemblorEscalofrio)));
        cuestionarioCovid19.setFeb20DolorMuscular(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20DolorMuscular)));
        cuestionarioCovid19.setFeb20DolorArticular(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20DolorArticular)));
        cuestionarioCovid19.setFeb20SecresionNasal(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20SecresionNasal)));
        cuestionarioCovid19.setFeb20DolorGarganta(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20DolorGarganta)));
        cuestionarioCovid19.setFeb20Tos(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20Tos)));
        cuestionarioCovid19.setFeb20DificultadResp(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20DificultadResp)));
        cuestionarioCovid19.setFeb20DolorPecho(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20DolorPecho)));
        cuestionarioCovid19.setFeb20NauseasVomito(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20NauseasVomito)));
        cuestionarioCovid19.setFeb20DolorCabeza(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20DolorCabeza)));
        cuestionarioCovid19.setFeb20DolorAbdominal(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20DolorAbdominal)));
        cuestionarioCovid19.setFeb20Diarrea(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20Diarrea)));
        cuestionarioCovid19.setFeb20DificultadDormir(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20DificultadDormir)));
        cuestionarioCovid19.setFeb20Debilidad(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20Debilidad)));
        cuestionarioCovid19.setFeb20PerdidaOlfatoGusto(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20PerdidaOlfatoGusto)));
        cuestionarioCovid19.setFeb20Mareo(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20Mareo)));
        cuestionarioCovid19.setFeb20Sarpullido(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20Sarpullido)));
        cuestionarioCovid19.setFeb20Desmayo(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20Desmayo)));
        cuestionarioCovid19.setFeb20QuedoCama(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.feb20QuedoCama)));
        cuestionarioCovid19.setSabeFIS(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.sabeFIS)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fis))>0) cuestionarioCovid19.setFis(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fis))));
        cuestionarioCovid19.setMesInicioSintoma(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.mesInicioSintoma)));
        cuestionarioCovid19.setAnioInicioSintoma(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.anioInicioSintoma)));
        cuestionarioCovid19.setPadecidoCovid19(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padecidoCovid19)));
        cuestionarioCovid19.setConoceLugarExposicion(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.conoceLugarExposicion)));
        cuestionarioCovid19.setLugarExposicion(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.lugarExposicion)));
        cuestionarioCovid19.setBuscoAyuda(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.buscoAyuda)));
        cuestionarioCovid19.setDondeBuscoAyuda(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dondeBuscoAyuda)));
        cuestionarioCovid19.setNombreCentroSalud(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.nombreCentroSalud)));
        cuestionarioCovid19.setNombreHospital(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.nombreHospital)));
        cuestionarioCovid19.setRecibioSeguimiento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.recibioSeguimiento)));
        cuestionarioCovid19.setTipoSeguimiento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tipoSeguimiento)));
        cuestionarioCovid19.setTmpDespuesBuscoAyuda(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tmpDespuesBuscoAyuda)));
        cuestionarioCovid19.setUnaNocheHospital(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.unaNocheHospital)));
        cuestionarioCovid19.setQueHospital(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.queHospital)));
        cuestionarioCovid19.setSabeCuantasNoches(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.sabeCuantasNoches)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.cuantasNochesHosp))>0) cuestionarioCovid19.setCuantasNochesHosp(cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.cuantasNochesHosp)));
        cuestionarioCovid19.setSabeFechaAdmision(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.sabeFechaAdmision)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaAdmisionHosp))>0) cuestionarioCovid19.setFechaAdmisionHosp(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaAdmisionHosp))));
        cuestionarioCovid19.setSabeFechaAlta(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.sabeFechaAlta)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaAltaHosp))>0) cuestionarioCovid19.setFechaAltaHosp(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaAltaHosp))));
        cuestionarioCovid19.setUtilizoOxigeno(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.utilizoOxigeno)));
        cuestionarioCovid19.setEstuvoUCI(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.estuvoUCI)));
        cuestionarioCovid19.setFueIntubado(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fueIntubado)));
        cuestionarioCovid19.setRecuperadoCovid19(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.recuperadoCovid19)));
        cuestionarioCovid19.setFebricula(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.febricula)));
        cuestionarioCovid19.setCansancio(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.cansancio)));
        cuestionarioCovid19.setDolorCabeza(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorCabeza)));
        cuestionarioCovid19.setPerdidaOlfato(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.perdidaOlfato)));
        cuestionarioCovid19.setPerdidaGusto(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.perdidaGusto)));
        cuestionarioCovid19.setTos(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tos)));
        cuestionarioCovid19.setDificultadRespirar(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dificultadRespirar)));
        cuestionarioCovid19.setDolorPecho(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorPecho)));
        cuestionarioCovid19.setPalpitaciones(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.palpitaciones)));
        cuestionarioCovid19.setDolorArticulaciones(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dolorArticulaciones)));
        cuestionarioCovid19.setParalisis(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.paralisis)));
        cuestionarioCovid19.setMareos(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.mareos)));
        cuestionarioCovid19.setPensamientoNublado(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.pensamientoNublado)));
        cuestionarioCovid19.setProblemasDormir(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.problemasDormir)));
        cuestionarioCovid19.setDepresion(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.depresion)));
        cuestionarioCovid19.setOtrosSintomas(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.otrosSintomas)));
        cuestionarioCovid19.setCualesSintomas(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.cualesSintomas)));
        cuestionarioCovid19.setSabeTiempoRecuperacion(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.sabeTiempoRecuperacion)));
        cuestionarioCovid19.setTiempoRecuperacion(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tiempoRecuperacion)));
        cuestionarioCovid19.setTiempoRecuperacionEn(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tiempoRecuperacionEn)));
        cuestionarioCovid19.setSeveridadEnfermedad(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.severidadEnfermedad)));
        cuestionarioCovid19.setTomoMedicamento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tomoMedicamento)));
        cuestionarioCovid19.setQueMedicamento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.queMedicamento)));
        cuestionarioCovid19.setOtroMedicamento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.otroMedicamento)));
        cuestionarioCovid19.setPadeceEnfisema(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceEnfisema)));
        cuestionarioCovid19.setPadeceAsma(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceAsma)));
        cuestionarioCovid19.setPadeceDiabetes(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceDiabetes)));
        cuestionarioCovid19.setPadeceEnfCoronaria(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceEnfCoronaria)));
        cuestionarioCovid19.setPadecePresionAlta(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padecePresionAlta)));
        cuestionarioCovid19.setPadeceEnfHigado(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceEnfHigado)));
        cuestionarioCovid19.setPadeceEnfRenal(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceEnfRenal)));
        cuestionarioCovid19.setPadeceInfartoDerrameCer(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceInfartoDerrameCer)));
        cuestionarioCovid19.setPadeceCancer(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceCancer)));
        cuestionarioCovid19.setPadeceCondicionInmuno(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceCondicionInmuno)));
        cuestionarioCovid19.setPadeceEnfAutoinmune(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceEnfAutoinmune)));
        cuestionarioCovid19.setPadeceDiscapacidadFis(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceDiscapacidadFis)));
        cuestionarioCovid19.setPadeceCondPsicPsiq(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceCondPsicPsiq)));
        cuestionarioCovid19.setPadeceOtraCondicion(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.padeceOtraCondicion)));
        cuestionarioCovid19.setQueOtraCondicion(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.queOtraCondicion)));
        cuestionarioCovid19.setFumado(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fumado)));
        cuestionarioCovid19.setFumadoCienCigarrillos(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fumadoCienCigarrillos)));
        cuestionarioCovid19.setFumadoPrevioEnfermedad(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fumadoPrevioEnfermedad)));
        cuestionarioCovid19.setFumaActualmente(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.fumaActualmente)));


        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) cuestionarioCovid19.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        cuestionarioCovid19.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        cuestionarioCovid19.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        cuestionarioCovid19.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        cuestionarioCovid19.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return cuestionarioCovid19;
    }

}
