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

        cv.put(EncuestasDBConstants.casa, encuesta.getCasa().getCodigoCHF());
        cv.put(EncuestasDBConstants.cantidadCuartos, encuesta.getCantidadCuartos());
        cv.put(EncuestasDBConstants.cantidadCuartosDormir, encuesta.getCantidadCuartosDormir());
        if (encuesta.getFechaEncuestas() !=null) cv.put(EncuestasDBConstants.fechaEncuestas, encuesta.getFechaEncuestas().getTime());
        cv.put(EncuestasDBConstants.encuestador, encuesta.getEncuestador());
        cv.put(EncuestasDBConstants.hrsSinServicioAgua, encuesta.getHrsSinServicioAgua());
        cv.put(EncuestasDBConstants.ubicacionLlaveAgua, encuesta.getUbicacionLlaveAgua());
        cv.put(EncuestasDBConstants.llaveCompartida, encuesta.getLlaveCompartida());
        cv.put(EncuestasDBConstants.almacenaAgua, String.valueOf(encuesta.getAlmacenaAgua()));
        cv.put(EncuestasDBConstants.almacenaEnBarriles, String.valueOf(encuesta.getAlmacenaEnBarriles()));
        cv.put(EncuestasDBConstants.almacenaEnTanques, String.valueOf(encuesta.getAlmacenaEnTanques()));
        cv.put(EncuestasDBConstants.almacenaEnPilas, String.valueOf(encuesta.getAlmacenaEnPilas()));
        cv.put(EncuestasDBConstants.almacenaOtrosRecipientes, String.valueOf(encuesta.getAlmacenaOtrosRecipientes()));
        cv.put(EncuestasDBConstants.otrosRecipientes, encuesta.getOtrosRecipientes());
        cv.put(EncuestasDBConstants.numBarriles, encuesta.getNumBarriles());
        cv.put(EncuestasDBConstants.numTanques, encuesta.getNumTanques());
        cv.put(EncuestasDBConstants.numPilas, encuesta.getNumPilas());
        cv.put(EncuestasDBConstants.numOtrosRecipientes, encuesta.getNumOtrosRecipientes());
        cv.put(EncuestasDBConstants.barrilesTapados, String.valueOf(encuesta.getBarrilesTapados()));
        cv.put(EncuestasDBConstants.tanquesTapados, String.valueOf(encuesta.getTanquesTapados()));
        cv.put(EncuestasDBConstants.pilasTapadas, String.valueOf(encuesta.getPilasTapadas()));
        cv.put(EncuestasDBConstants.otrosRecipientesTapados, String.valueOf(encuesta.getOtrosRecipientesTapados()));
        cv.put(EncuestasDBConstants.barrilesConAbate, String.valueOf(encuesta.getBarrilesConAbate()));
        cv.put(EncuestasDBConstants.tanquesConAbate, String.valueOf(encuesta.getTanquesConAbate()));
        cv.put(EncuestasDBConstants.pilasConAbate, String.valueOf(encuesta.getPilasConAbate()));
        cv.put(EncuestasDBConstants.otrosRecipientesConAbate, String.valueOf(encuesta.getOtrosRecipientesConAbate()));
        cv.put(EncuestasDBConstants.ubicacionLavandero, encuesta.getUbicacionLavandero());
        cv.put(EncuestasDBConstants.materialParedes, encuesta.getMaterialParedes());
        cv.put(EncuestasDBConstants.materialPiso, encuesta.getMaterialPiso());
        cv.put(EncuestasDBConstants.materialTecho, encuesta.getMaterialTecho());
        cv.put(EncuestasDBConstants.otroMaterialParedes, encuesta.getOtroMaterialParedes());
        cv.put(EncuestasDBConstants.otroMaterialPiso, encuesta.getOtroMaterialPiso());
        cv.put(EncuestasDBConstants.otroMaterialTecho, encuesta.getOtroMaterialTecho());
        cv.put(EncuestasDBConstants.casaPropia, String.valueOf(encuesta.getCasaPropia()));
        cv.put(EncuestasDBConstants.tieneAbanico, String.valueOf(encuesta.getTieneAbanico()));
        cv.put(EncuestasDBConstants.tieneTelevisor, String.valueOf(encuesta.getTieneTelevisor()));
        cv.put(EncuestasDBConstants.tieneRefrigerador, String.valueOf(encuesta.getTieneRefrigerador()));
        cv.put(EncuestasDBConstants.tienAireAcondicionado, String.valueOf(encuesta.getTienAireAcondicionado()));
        cv.put(EncuestasDBConstants.aireAcondicionadoFuncionando, encuesta.getAireAcondicionadoFuncionando());
        cv.put(EncuestasDBConstants.numAbanicos, encuesta.getNumAbanicos());
        cv.put(EncuestasDBConstants.numTelevisores, encuesta.getNumTelevisores());
        cv.put(EncuestasDBConstants.numRefrigeradores, encuesta.getNumRefrigeradores());
        cv.put(EncuestasDBConstants.tieneMoto, String.valueOf(encuesta.getTieneMoto()));
        cv.put(EncuestasDBConstants.tieneCarro, String.valueOf(encuesta.getTieneCarro()));
        cv.put(EncuestasDBConstants.tienMicrobus, String.valueOf(encuesta.getTienMicrobus()));
        cv.put(EncuestasDBConstants.tieneCamioneta, String.valueOf(encuesta.getTieneCamioneta()));
        cv.put(EncuestasDBConstants.tieneCamion, String.valueOf(encuesta.getTieneCamion()));
        cv.put(EncuestasDBConstants.otroMedioTransAuto, encuesta.getOtroMedioTransAuto());
        cv.put(EncuestasDBConstants.cocinaConLenia, String.valueOf(encuesta.getCocinaConLenia()));
        cv.put(EncuestasDBConstants.ubicacionCocinaLenia, encuesta.getUbicacionCocinaLenia());
        cv.put(EncuestasDBConstants.periodicidadCocinaLenia, encuesta.getPeriodicidadCocinaLenia());
        cv.put(EncuestasDBConstants.numDiarioCocinaLenia, encuesta.getNumDiarioCocinaLenia());   //# de veces que cocina
        cv.put(EncuestasDBConstants.numSemanalCocinaLenia, encuesta.getNumSemanalCocinaLenia());  //# de veces que cocina semanalmente
        cv.put(EncuestasDBConstants.numQuincenalCocinaLenia, encuesta.getNumQuincenalCocinaLenia());    //# de veces que cocina quincenalmente
        cv.put(EncuestasDBConstants.numMensualCocinaLenia, encuesta.getNumMensualCocinaLenia());  //# de veces que cocina al mes
        cv.put(EncuestasDBConstants.tieneAnimales, String.valueOf(encuesta.getTieneAnimales()));
        cv.put(EncuestasDBConstants.tieneGallinas, String.valueOf(encuesta.getTieneGallinas()));
        cv.put(EncuestasDBConstants.tienePatos, String.valueOf(encuesta.getTienePatos()));
        cv.put(EncuestasDBConstants.tieneCerdos, String.valueOf(encuesta.getTieneCerdos()));
        cv.put(EncuestasDBConstants.cantidadGallinas, encuesta.getCantidadGallinas());
        cv.put(EncuestasDBConstants.cantidadPatos, encuesta.getCantidadPatos());
        cv.put(EncuestasDBConstants.cantidadCerdos, encuesta.getCantidadCerdos());
        cv.put(EncuestasDBConstants.gallinasDentroCasa, String.valueOf(encuesta.getGallinasDentroCasa()));
        cv.put(EncuestasDBConstants.patosDentroCasa, String.valueOf(encuesta.getPatosDentroCasa()));
        cv.put(EncuestasDBConstants.cerdosDentroCasa, String.valueOf(encuesta.getCerdosDentroCasa()));
        cv.put(EncuestasDBConstants.personaFumaDentroCasa, String.valueOf(encuesta.getPersonaFumaDentroCasa()));  //Alguna persona que no pertenece al estudio fuma dentro de la casa
        cv.put(EncuestasDBConstants.madreFuma, String.valueOf(encuesta.getMadreFuma()));
        cv.put(EncuestasDBConstants.padreFuma, String.valueOf(encuesta.getPadreFuma()));
        cv.put(EncuestasDBConstants.otrosFuman, String.valueOf(encuesta.getOtrosFuman()));
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
        mEncuesta.setAlmacenaAgua(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaAgua)).charAt(0));
        mEncuesta.setAlmacenaEnBarriles(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaEnBarriles)).charAt(0));
        mEncuesta.setAlmacenaEnTanques(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaEnTanques)).charAt(0));
        mEncuesta.setAlmacenaEnPilas(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaEnPilas)).charAt(0));
        mEncuesta.setAlmacenaOtrosRecipientes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaOtrosRecipientes)).charAt(0));
        mEncuesta.setOtrosRecipientes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosRecipientes)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numBarriles)) > 0) mEncuesta.setNumBarriles(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numBarriles)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTanques)) > 0) mEncuesta.setNumTanques(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTanques)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numPilas)) > 0) mEncuesta.setNumPilas(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numPilas)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numOtrosRecipientes)) > 0) mEncuesta.setNumOtrosRecipientes(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numOtrosRecipientes)));
        mEncuesta.setBarrilesTapados(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.barrilesTapados)).charAt(0));
        mEncuesta.setTanquesTapados(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tanquesTapados)).charAt(0));
        mEncuesta.setPilasTapadas(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.pilasTapadas)).charAt(0));
        mEncuesta.setOtrosRecipientesTapados(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosRecipientesTapados)).charAt(0));
        mEncuesta.setBarrilesConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.barrilesConAbate)).charAt(0));
        mEncuesta.setTanquesConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tanquesConAbate)).charAt(0));
        mEncuesta.setPilasConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.pilasConAbate)).charAt(0));
        mEncuesta.setOtrosRecipientesConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosRecipientesConAbate)).charAt(0));
        mEncuesta.setUbicacionLavandero(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ubicacionLavandero)));
        mEncuesta.setMaterialParedes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.materialParedes)));
        mEncuesta.setMaterialPiso(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.materialPiso)));
        mEncuesta.setMaterialTecho(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.materialTecho)));
        mEncuesta.setOtroMaterialParedes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMaterialParedes)));
        mEncuesta.setOtroMaterialPiso(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMaterialPiso)));
        mEncuesta.setOtroMaterialTecho(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMaterialTecho)));
        mEncuesta.setCasaPropia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.casaPropia)).charAt(0));
        mEncuesta.setTieneAbanico(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneAbanico)).charAt(0));
        mEncuesta.setTieneTelevisor(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneTelevisor)).charAt(0));
        mEncuesta.setTieneRefrigerador(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneRefrigerador)).charAt(0));
        mEncuesta.setTienAireAcondicionado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tienAireAcondicionado)).charAt(0));
        mEncuesta.setAireAcondicionadoFuncionando(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.aireAcondicionadoFuncionando)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numAbanicos)) > 0) mEncuesta.setNumAbanicos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numAbanicos)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTelevisores)) > 0) mEncuesta.setNumTelevisores(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTelevisores)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numRefrigeradores)) > 0) mEncuesta.setNumRefrigeradores(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numRefrigeradores)));
        mEncuesta.setTieneMoto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneMoto)).charAt(0));
        mEncuesta.setTieneCarro(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCarro)).charAt(0));
        mEncuesta.setTienMicrobus(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tienMicrobus)).charAt(0));
        mEncuesta.setTieneCamioneta(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCamioneta)).charAt(0));
        mEncuesta.setTieneCamion(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCamion)).charAt(0));
        mEncuesta.setTieneOtroMedioTransAuto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneOtroMedioTransAuto)).charAt(0));
        mEncuesta.setOtroMedioTransAuto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMedioTransAuto)));
        mEncuesta.setCocinaConLenia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.cocinaConLenia)).charAt(0));
        mEncuesta.setUbicacionCocinaLenia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ubicacionCocinaLenia)));
        mEncuesta.setPeriodicidadCocinaLenia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.periodicidadCocinaLenia)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numDiarioCocinaLenia)) > 0) mEncuesta.setNumDiarioCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numDiarioCocinaLenia)));   //# de veces que cocina
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numSemanalCocinaLenia)) > 0) mEncuesta.setNumSemanalCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numSemanalCocinaLenia)));  //# de veces que cocina semanalmente
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numQuincenalCocinaLenia)) > 0) mEncuesta.setNumQuincenalCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numQuincenalCocinaLenia)));    //# de veces que cocina quincenalmente
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numMensualCocinaLenia)) > 0) mEncuesta.setNumMensualCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numMensualCocinaLenia)));  //# de veces que cocina al mes
        mEncuesta.setTieneAnimales(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneAnimales)).charAt(0));
        mEncuesta.setTieneGallinas(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneGallinas)).charAt(0));
        mEncuesta.setTienePatos(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tienePatos)).charAt(0));
        mEncuesta.setTieneCerdos(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCerdos)).charAt(0));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadGallinas)) > 0) mEncuesta.setCantidadGallinas(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadGallinas)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadPatos)) > 0) mEncuesta.setCantidadPatos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadPatos)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCerdos)) > 0) mEncuesta.setCantidadCerdos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCerdos)));
        mEncuesta.setGallinasDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.gallinasDentroCasa)).charAt(0));
        mEncuesta.setPatosDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.patosDentroCasa)).charAt(0));
        mEncuesta.setCerdosDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.cerdosDentroCasa)).charAt(0));
        mEncuesta.setPersonaFumaDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.personaFumaDentroCasa)).charAt(0));  //Alguna persona que no pertenece al estudio fuma dentro de la casa
        mEncuesta.setMadreFuma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.madreFuma)).charAt(0));
        mEncuesta.setPadreFuma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.padreFuma)).charAt(0));
        mEncuesta.setOtrosFuman(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosFuman)).charAt(0));
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
