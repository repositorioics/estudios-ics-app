package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaCasa;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class EncuestaCasaHelper {

    public static ContentValues crearEncuestaCasaContentValues(EncuestaCasa encuesta){
        ContentValues cv = new ContentValues();

        cv.put(EncuestasDBConstants.casa_chf, encuesta.getCasa().getCodigoCHF());
        cv.put(EncuestasDBConstants.cantidadCuartos, encuesta.getCantidadCuartos());
        cv.put(EncuestasDBConstants.cantidadCuartosDormir, encuesta.getCantidadCuartosDormir());
        if (encuesta.getFechaEncuestas() !=null) cv.put(EncuestasDBConstants.fechaEncuestas, encuesta.getFechaEncuestas().getTime());
        cv.put(EncuestasDBConstants.encuestador, encuesta.getEncuestador());
        cv.put(EncuestasDBConstants.hrsSinServicioAgua, encuesta.getHrsSinServicioAgua());
        cv.put(EncuestasDBConstants.ubicacionLlaveAgua, encuesta.getUbicacionLlaveAgua());
        cv.put(EncuestasDBConstants.llaveCompartida, encuesta.getLlaveCompartida());
        cv.put(EncuestasDBConstants.almacenaAgua, encuesta.getAlmacenaAgua());
        cv.put(EncuestasDBConstants.almacenaEnBarriles, encuesta.getAlmacenaEnBarriles());
        cv.put(EncuestasDBConstants.almacenaEnTanques, encuesta.getAlmacenaEnTanques());
        cv.put(EncuestasDBConstants.almacenaEnPilas, encuesta.getAlmacenaEnPilas());
        cv.put(EncuestasDBConstants.almacenaOtrosRecipientes, encuesta.getAlmacenaOtrosRecipientes());
        cv.put(EncuestasDBConstants.otrosRecipientes, encuesta.getOtrosRecipientes());
        cv.put(EncuestasDBConstants.numBarriles, encuesta.getNumBarriles());
        cv.put(EncuestasDBConstants.numTanques, encuesta.getNumTanques());
        cv.put(EncuestasDBConstants.numPilas, encuesta.getNumPilas());
        cv.put(EncuestasDBConstants.numOtrosRecipientes, encuesta.getNumOtrosRecipientes());
        cv.put(EncuestasDBConstants.barrilesTapados, encuesta.getBarrilesTapados());
        cv.put(EncuestasDBConstants.tanquesTapados, encuesta.getTanquesTapados());
        cv.put(EncuestasDBConstants.pilasTapadas, encuesta.getPilasTapadas());
        cv.put(EncuestasDBConstants.otrosRecipientesTapados, encuesta.getOtrosRecipientesTapados());
        cv.put(EncuestasDBConstants.barrilesConAbate, encuesta.getBarrilesConAbate());
        cv.put(EncuestasDBConstants.tanquesConAbate, encuesta.getTanquesConAbate());
        cv.put(EncuestasDBConstants.pilasConAbate, encuesta.getPilasConAbate());
        cv.put(EncuestasDBConstants.otrosRecipientesConAbate, encuesta.getOtrosRecipientesConAbate());
        cv.put(EncuestasDBConstants.ubicacionLavandero, encuesta.getUbicacionLavandero());
        cv.put(EncuestasDBConstants.materialParedes, encuesta.getMaterialParedes());
        cv.put(EncuestasDBConstants.materialPiso, encuesta.getMaterialPiso());
        cv.put(EncuestasDBConstants.materialTecho, encuesta.getMaterialTecho());
        cv.put(EncuestasDBConstants.otroMaterialParedes, encuesta.getOtroMaterialParedes());
        cv.put(EncuestasDBConstants.otroMaterialPiso, encuesta.getOtroMaterialPiso());
        cv.put(EncuestasDBConstants.otroMaterialTecho, encuesta.getOtroMaterialTecho());
        cv.put(EncuestasDBConstants.casaPropia, encuesta.getCasaPropia());
        cv.put(EncuestasDBConstants.tieneAbanico, encuesta.getTieneAbanico());
        cv.put(EncuestasDBConstants.tieneTelevisor, encuesta.getTieneTelevisor());
        cv.put(EncuestasDBConstants.tieneRefrigerador, encuesta.getTieneRefrigerador());
        cv.put(EncuestasDBConstants.tienAireAcondicionado, encuesta.getTienAireAcondicionado());
        cv.put(EncuestasDBConstants.aireAcondicionadoFuncionando, encuesta.getAireAcondicionadoFuncionando());
        cv.put(EncuestasDBConstants.numAbanicos, encuesta.getNumAbanicos());
        cv.put(EncuestasDBConstants.numTelevisores, encuesta.getNumTelevisores());
        cv.put(EncuestasDBConstants.numRefrigeradores, encuesta.getNumRefrigeradores());
        cv.put(EncuestasDBConstants.tieneMoto, encuesta.getTieneMoto());
        cv.put(EncuestasDBConstants.tieneCarro, encuesta.getTieneCarro());
        cv.put(EncuestasDBConstants.tienMicrobus, encuesta.getTienMicrobus());
        cv.put(EncuestasDBConstants.tieneCamioneta, encuesta.getTieneCamioneta());
        cv.put(EncuestasDBConstants.tieneCamion, encuesta.getTieneCamion());
        cv.put(EncuestasDBConstants.tieneOtroMedioTransAuto, encuesta.getTieneOtroMedioTransAuto());
        cv.put(EncuestasDBConstants.otroMedioTransAuto, encuesta.getOtroMedioTransAuto());
        cv.put(EncuestasDBConstants.cocinaConLenia, encuesta.getCocinaConLenia());
        cv.put(EncuestasDBConstants.ubicacionCocinaLenia, encuesta.getUbicacionCocinaLenia());
        cv.put(EncuestasDBConstants.periodicidadCocinaLenia, encuesta.getPeriodicidadCocinaLenia());
        cv.put(EncuestasDBConstants.numDiarioCocinaLenia, encuesta.getNumDiarioCocinaLenia());   //# de veces que cocina
        cv.put(EncuestasDBConstants.numSemanalCocinaLenia, encuesta.getNumSemanalCocinaLenia());  //# de veces que cocina semanalmente
        cv.put(EncuestasDBConstants.numQuincenalCocinaLenia, encuesta.getNumQuincenalCocinaLenia());    //# de veces que cocina quincenalmente
        cv.put(EncuestasDBConstants.numMensualCocinaLenia, encuesta.getNumMensualCocinaLenia());  //# de veces que cocina al mes
        cv.put(EncuestasDBConstants.tieneAnimales, encuesta.getTieneAnimales());
        cv.put(EncuestasDBConstants.tieneGallinas, encuesta.getTieneGallinas());
        cv.put(EncuestasDBConstants.tienePatos, encuesta.getTienePatos());
        cv.put(EncuestasDBConstants.tieneCerdos, encuesta.getTieneCerdos());
        cv.put(EncuestasDBConstants.cantidadGallinas, encuesta.getCantidadGallinas());
        cv.put(EncuestasDBConstants.cantidadPatos, encuesta.getCantidadPatos());
        cv.put(EncuestasDBConstants.cantidadCerdos, encuesta.getCantidadCerdos());
        cv.put(EncuestasDBConstants.gallinasDentroCasa, encuesta.getGallinasDentroCasa());
        cv.put(EncuestasDBConstants.patosDentroCasa, encuesta.getPatosDentroCasa());
        cv.put(EncuestasDBConstants.cerdosDentroCasa, encuesta.getCerdosDentroCasa());
        cv.put(EncuestasDBConstants.personaFumaDentroCasa, encuesta.getPersonaFumaDentroCasa());  //Alguna persona que no pertenece al estudio fuma dentro de la casa
        cv.put(EncuestasDBConstants.madreFuma, encuesta.getMadreFuma());
        cv.put(EncuestasDBConstants.padreFuma, encuesta.getPadreFuma());
        cv.put(EncuestasDBConstants.otrosFuman, encuesta.getOtrosFuman());
        cv.put(EncuestasDBConstants.cantidadOtrosFuman, encuesta.getCantidadOtrosFuman());
        cv.put(EncuestasDBConstants.cantidadCigarrilosMadre, encuesta.getCantidadCigarrilosMadre()); // diarios
        cv.put(EncuestasDBConstants.cantidadCigarrillosPadre, encuesta.getCantidadCigarrillosPadre()); // diarios
        cv.put(EncuestasDBConstants.cantidadCigarrillosOtros, encuesta.getCantidadCigarrillosOtros()); // diarios
        if (encuesta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, encuesta.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, encuesta.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(encuesta.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));
        cv.put(MainDBConstants.deviceId, encuesta.getDeviceid());

        return cv;
    }

    public static EncuestaCasa crearEncuestaCasa(Cursor cursor){
        EncuestaCasa mEncuesta = new EncuestaCasa();

        mEncuesta.setCasa(null);
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCuartos)) > 0) mEncuesta.setCantidadCuartos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCuartos)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCuartosDormir)) > 0) mEncuesta.setCantidadCuartosDormir(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCuartosDormir)));
        if (cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaEncuestas))>0) mEncuesta.setFechaEncuestas(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaEncuestas))));
        mEncuesta.setEncuestador(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.encuestador)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.hrsSinServicioAgua)) > 0) mEncuesta.setHrsSinServicioAgua(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.hrsSinServicioAgua)));
        mEncuesta.setUbicacionLlaveAgua(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ubicacionLlaveAgua)));
        mEncuesta.setLlaveCompartida(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.llaveCompartida)));
        mEncuesta.setAlmacenaAgua(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaAgua)));
        mEncuesta.setAlmacenaEnBarriles(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaEnBarriles)));
        mEncuesta.setAlmacenaEnTanques(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaEnTanques)));
        mEncuesta.setAlmacenaEnPilas(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaEnPilas)));
        mEncuesta.setAlmacenaOtrosRecipientes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaOtrosRecipientes)));
        mEncuesta.setOtrosRecipientes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosRecipientes)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numBarriles)) > 0) mEncuesta.setNumBarriles(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numBarriles)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTanques)) > 0) mEncuesta.setNumTanques(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTanques)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numPilas)) > 0) mEncuesta.setNumPilas(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numPilas)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numOtrosRecipientes)) > 0) mEncuesta.setNumOtrosRecipientes(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numOtrosRecipientes)));
        mEncuesta.setBarrilesTapados(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.barrilesTapados)));
        mEncuesta.setTanquesTapados(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tanquesTapados)));
        mEncuesta.setPilasTapadas(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.pilasTapadas)));
        mEncuesta.setOtrosRecipientesTapados(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosRecipientesTapados)));
        mEncuesta.setBarrilesConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.barrilesConAbate)));
        mEncuesta.setTanquesConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tanquesConAbate)));
        mEncuesta.setPilasConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.pilasConAbate)));
        mEncuesta.setOtrosRecipientesConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosRecipientesConAbate)));
        mEncuesta.setUbicacionLavandero(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ubicacionLavandero)));
        mEncuesta.setMaterialParedes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.materialParedes)));
        mEncuesta.setMaterialPiso(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.materialPiso)));
        mEncuesta.setMaterialTecho(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.materialTecho)));
        mEncuesta.setOtroMaterialParedes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMaterialParedes)));
        mEncuesta.setOtroMaterialPiso(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMaterialPiso)));
        mEncuesta.setOtroMaterialTecho(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMaterialTecho)));
        mEncuesta.setCasaPropia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.casaPropia)));
        mEncuesta.setTieneAbanico(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneAbanico)));
        mEncuesta.setTieneTelevisor(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneTelevisor)));
        mEncuesta.setTieneRefrigerador(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneRefrigerador)));
        mEncuesta.setTienAireAcondicionado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tienAireAcondicionado)));
        mEncuesta.setAireAcondicionadoFuncionando(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.aireAcondicionadoFuncionando)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numAbanicos)) > 0) mEncuesta.setNumAbanicos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numAbanicos)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTelevisores)) > 0) mEncuesta.setNumTelevisores(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTelevisores)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numRefrigeradores)) > 0) mEncuesta.setNumRefrigeradores(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numRefrigeradores)));
        mEncuesta.setTieneMoto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneMoto)));
        mEncuesta.setTieneCarro(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCarro)));
        mEncuesta.setTienMicrobus(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tienMicrobus)));
        mEncuesta.setTieneCamioneta(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCamioneta)));
        mEncuesta.setTieneCamion(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCamion)));
        if (cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneOtroMedioTransAuto)) != null) mEncuesta.setTieneOtroMedioTransAuto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneOtroMedioTransAuto)));
        mEncuesta.setOtroMedioTransAuto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMedioTransAuto)));
        mEncuesta.setCocinaConLenia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.cocinaConLenia)));
        mEncuesta.setUbicacionCocinaLenia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ubicacionCocinaLenia)));
        mEncuesta.setPeriodicidadCocinaLenia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.periodicidadCocinaLenia)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numDiarioCocinaLenia)) > 0) mEncuesta.setNumDiarioCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numDiarioCocinaLenia)));   //# de veces que cocina
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numSemanalCocinaLenia)) > 0) mEncuesta.setNumSemanalCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numSemanalCocinaLenia)));  //# de veces que cocina semanalmente
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numQuincenalCocinaLenia)) > 0) mEncuesta.setNumQuincenalCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numQuincenalCocinaLenia)));    //# de veces que cocina quincenalmente
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numMensualCocinaLenia)) > 0) mEncuesta.setNumMensualCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numMensualCocinaLenia)));  //# de veces que cocina al mes
        mEncuesta.setTieneAnimales(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneAnimales)));
        mEncuesta.setTieneGallinas(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneGallinas)));
        mEncuesta.setTienePatos(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tienePatos)));
        mEncuesta.setTieneCerdos(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCerdos)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadGallinas)) > 0) mEncuesta.setCantidadGallinas(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadGallinas)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadPatos)) > 0) mEncuesta.setCantidadPatos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadPatos)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCerdos)) > 0) mEncuesta.setCantidadCerdos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCerdos)));
        mEncuesta.setGallinasDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.gallinasDentroCasa)));
        mEncuesta.setPatosDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.patosDentroCasa)));
        mEncuesta.setCerdosDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.cerdosDentroCasa)));
        mEncuesta.setPersonaFumaDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.personaFumaDentroCasa)));  //Alguna persona que no pertenece al estudio fuma dentro de la casa
        mEncuesta.setMadreFuma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.madreFuma)));
        mEncuesta.setPadreFuma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.padreFuma)));
        mEncuesta.setOtrosFuman(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosFuman)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadOtrosFuman)) > 0) mEncuesta.setCantidadOtrosFuman(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadOtrosFuman)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrilosMadre)) > 0) mEncuesta.setCantidadCigarrilosMadre(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrilosMadre))); // diarios
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillosPadre)) > 0) mEncuesta.setCantidadCigarrillosPadre(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillosPadre))); // diarios
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillosOtros)) > 0) mEncuesta.setCantidadCigarrillosOtros(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillosOtros))); // diarios
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mEncuesta.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mEncuesta.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mEncuesta.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mEncuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mEncuesta.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mEncuesta;
    }
}
