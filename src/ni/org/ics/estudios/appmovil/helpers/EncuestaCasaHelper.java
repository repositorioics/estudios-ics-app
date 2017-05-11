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
        cv.put(EncuestasDBConstants.llaveCompartida, String.valueOf(encuesta.getLlaveCompartida()));
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
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));

        return cv;
    }

    public static EncuestaCasa crearEncuestaCasa(Cursor cursor){
        EncuestaCasa encuesta = new EncuestaCasa();

        encuesta.setCasa(null);
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCuartos)) > 0) encuesta.setCantidadCuartos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCuartos)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCuartosDormir)) > 0) encuesta.setCantidadCuartosDormir(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCuartosDormir)));
        if (cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaEncuestas))>0) encuesta.setFechaEncuestas(new Date(cursor.getLong(cursor.getColumnIndex(EncuestasDBConstants.fechaEncuestas))));
        encuesta.setEncuestador(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.encuestador)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.hrsSinServicioAgua)) > 0) encuesta.setHrsSinServicioAgua(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.hrsSinServicioAgua)));
        encuesta.setUbicacionLlaveAgua(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ubicacionLlaveAgua)));
        encuesta.setLlaveCompartida(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.llaveCompartida)).charAt(0));
        encuesta.setAlmacenaAgua(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaAgua)).charAt(0));
        encuesta.setAlmacenaEnBarriles(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaEnBarriles)).charAt(0));
        encuesta.setAlmacenaEnTanques(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaEnTanques)).charAt(0));
        encuesta.setAlmacenaEnPilas(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaEnPilas)).charAt(0));
        encuesta.setAlmacenaOtrosRecipientes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.almacenaOtrosRecipientes)).charAt(0));
        encuesta.setOtrosRecipientes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosRecipientes)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numBarriles)) > 0) encuesta.setNumBarriles(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numBarriles)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTanques)) > 0) encuesta.setNumTanques(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTanques)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numPilas)) > 0) encuesta.setNumPilas(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numPilas)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numOtrosRecipientes)) > 0) encuesta.setNumOtrosRecipientes(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numOtrosRecipientes)));
        encuesta.setBarrilesTapados(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.barrilesTapados)).charAt(0));
        encuesta.setTanquesTapados(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tanquesTapados)).charAt(0));
        encuesta.setPilasTapadas(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.pilasTapadas)).charAt(0));
        encuesta.setOtrosRecipientesTapados(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosRecipientesTapados)).charAt(0));
        encuesta.setBarrilesConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.barrilesConAbate)).charAt(0));
        encuesta.setTanquesConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tanquesConAbate)).charAt(0));
        encuesta.setPilasConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.pilasConAbate)).charAt(0));
        encuesta.setOtrosRecipientesConAbate(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosRecipientesConAbate)).charAt(0));
        encuesta.setUbicacionLavandero(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ubicacionLavandero)));
        encuesta.setMaterialParedes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.materialParedes)));
        encuesta.setMaterialPiso(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.materialPiso)));
        encuesta.setMaterialTecho(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.materialTecho)));
        encuesta.setOtroMaterialParedes(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMaterialParedes)));
        encuesta.setOtroMaterialPiso(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMaterialPiso)));
        encuesta.setOtroMaterialTecho(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMaterialTecho)));
        encuesta.setCasaPropia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.casaPropia)).charAt(0));
        encuesta.setTieneAbanico(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneAbanico)).charAt(0));
        encuesta.setTieneTelevisor(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneTelevisor)).charAt(0));
        encuesta.setTieneRefrigerador(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneRefrigerador)).charAt(0));
        encuesta.setTienAireAcondicionado(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tienAireAcondicionado)).charAt(0));
        encuesta.setAireAcondicionadoFuncionando(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.aireAcondicionadoFuncionando)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numAbanicos)) > 0) encuesta.setNumAbanicos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numAbanicos)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTelevisores)) > 0) encuesta.setNumTelevisores(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numTelevisores)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numRefrigeradores)) > 0) encuesta.setNumRefrigeradores(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numRefrigeradores)));
        encuesta.setTieneMoto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneMoto)).charAt(0));
        encuesta.setTieneCarro(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCarro)).charAt(0));
        encuesta.setTienMicrobus(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tienMicrobus)).charAt(0));
        encuesta.setTieneCamioneta(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCamioneta)).charAt(0));
        encuesta.setTieneCamion(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCamion)).charAt(0));
        encuesta.setTieneOtroMedioTransAuto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneOtroMedioTransAuto)).charAt(0));
        encuesta.setOtroMedioTransAuto(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otroMedioTransAuto)));
        encuesta.setCocinaConLenia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.cocinaConLenia)).charAt(0));
        encuesta.setUbicacionCocinaLenia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.ubicacionCocinaLenia)));
        encuesta.setPeriodicidadCocinaLenia(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.periodicidadCocinaLenia)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numDiarioCocinaLenia)) > 0) encuesta.setNumDiarioCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numDiarioCocinaLenia)));   //# de veces que cocina
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numSemanalCocinaLenia)) > 0) encuesta.setNumSemanalCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numSemanalCocinaLenia)));  //# de veces que cocina semanalmente
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numQuincenalCocinaLenia)) > 0) encuesta.setNumQuincenalCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numQuincenalCocinaLenia)));    //# de veces que cocina quincenalmente
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numMensualCocinaLenia)) > 0) encuesta.setNumMensualCocinaLenia(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.numMensualCocinaLenia)));  //# de veces que cocina al mes
        encuesta.setTieneAnimales(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneAnimales)).charAt(0));
        encuesta.setTieneGallinas(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneGallinas)).charAt(0));
        encuesta.setTienePatos(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tienePatos)).charAt(0));
        encuesta.setTieneCerdos(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.tieneCerdos)).charAt(0));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadGallinas)) > 0) encuesta.setCantidadGallinas(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadGallinas)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadPatos)) > 0) encuesta.setCantidadPatos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadPatos)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCerdos)) > 0) encuesta.setCantidadCerdos(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCerdos)));
        encuesta.setGallinasDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.gallinasDentroCasa)).charAt(0));
        encuesta.setPatosDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.patosDentroCasa)).charAt(0));
        encuesta.setCerdosDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.cerdosDentroCasa)).charAt(0));
        encuesta.setPersonaFumaDentroCasa(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.personaFumaDentroCasa)).charAt(0));  //Alguna persona que no pertenece al estudio fuma dentro de la casa
        encuesta.setMadreFuma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.madreFuma)).charAt(0));
        encuesta.setPadreFuma(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.padreFuma)).charAt(0));
        encuesta.setOtrosFuman(cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.otrosFuman)).charAt(0));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadOtrosFuman)) > 0) encuesta.setCantidadOtrosFuman(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadOtrosFuman)));
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrilosMadre)) > 0) encuesta.setCantidadCigarrilosMadre(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrilosMadre))); // diarios
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillosPadre)) > 0) encuesta.setCantidadCigarrillosPadre(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillosPadre))); // diarios
        if (cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillosOtros)) > 0) encuesta.setCantidadCigarrillosOtros(cursor.getInt(cursor.getColumnIndex(EncuestasDBConstants.cantidadCigarrillosOtros))); // diarios
        encuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));

        return encuesta;
    }
}
