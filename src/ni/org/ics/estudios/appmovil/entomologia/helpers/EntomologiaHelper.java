package ni.org.ics.estudios.appmovil.entomologia.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.entomologia.constants.EntomologiaBConstants;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogar;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogarPoblacion;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas 22/08/2019.
 * V1.0
 */
public class EntomologiaHelper {

    public static ContentValues crearCuestionarioHogarContentValues(CuestionarioHogar cuest){
        ContentValues cv = new ContentValues();
        cv.put(EntomologiaBConstants.codigoEncuesta, cuest.getCodigoEncuesta());
        cv.put(EntomologiaBConstants.codigoCasa, cuest.getCodigoCasa());
        cv.put(EntomologiaBConstants.quienContesta, cuest.getQuienContesta());
        cv.put(EntomologiaBConstants.quienContestaOtro, cuest.getQuienContestaOtro());
        cv.put(EntomologiaBConstants.edadContest, cuest.getEdadContest());
        cv.put(EntomologiaBConstants.escolaridadContesta, cuest.getEscolaridadContesta());
        cv.put(EntomologiaBConstants.tiempoVivirBarrio, cuest.getTiempoVivirBarrio());
        cv.put(EntomologiaBConstants.cuantasPersonasViven, cuest.getCuantasPersonasViven());
        cv.put(EntomologiaBConstants.usaronMosquitero, cuest.getUsaronMosquitero());
        cv.put(EntomologiaBConstants.quienesUsaronMosquitero, cuest.getQuienesUsaronMosquitero());
        cv.put(EntomologiaBConstants.usaronRepelente, cuest.getUsaronRepelente());
        cv.put(EntomologiaBConstants.quienesUsaronRepelente, cuest.getQuienesUsaronRepelente());
        cv.put(EntomologiaBConstants.conoceLarvas, cuest.getConoceLarvas());
        cv.put(EntomologiaBConstants.alguienVisEliminarLarvas, cuest.getAlguienVisEliminarLarvas());
        cv.put(EntomologiaBConstants.quienVisEliminarLarvas, cuest.getQuienVisEliminarLarvas());
        cv.put(EntomologiaBConstants.quienVisEliminarLarvasOtro, cuest.getQuienVisEliminarLarvasOtro());
        cv.put(EntomologiaBConstants.alguienDedicaElimLarvasCasa, cuest.getAlguienDedicaElimLarvasCasa());
        cv.put(EntomologiaBConstants.quienDedicaElimLarvasCasa, cuest.getQuienDedicaElimLarvasCasa());
        cv.put(EntomologiaBConstants.tiempoElimanCridaros, cuest.getTiempoElimanCridaros());
        cv.put(EntomologiaBConstants.hayBastanteZancudos, cuest.getHayBastanteZancudos());
        cv.put(EntomologiaBConstants.queFaltaCasaEvitarZancudos, cuest.getQueFaltaCasaEvitarZancudos());
        cv.put(EntomologiaBConstants.queFaltaCasaEvitarZancudosOtros, cuest.getQueFaltaCasaEvitarZancudosOtros());
        cv.put(EntomologiaBConstants.gastaronDineroProductos, cuest.getGastaronDineroProductos());
        cv.put(EntomologiaBConstants.queProductosCompraron, cuest.getQueProductosCompraron());
        cv.put(EntomologiaBConstants.queProductosCompraronOtros, cuest.getQueProductosCompraronOtros());
        cv.put(EntomologiaBConstants.cuantoGastaron, cuest.getCuantoGastaron());
        cv.put(EntomologiaBConstants.ultimaVisitaMinsaBTI, cuest.getUltimaVisitaMinsaBTI());
        cv.put(EntomologiaBConstants.ultimaVezMinsaFumigo, cuest.getUltimaVezMinsaFumigo());
        cv.put(EntomologiaBConstants.riesgoCasaDengue, cuest.getRiesgoCasaDengue());
        cv.put(EntomologiaBConstants.problemasAbastecimiento, cuest.getProblemasAbastecimiento());
        cv.put(EntomologiaBConstants.cadaCuantoVaAgua, cuest.getCadaCuantoVaAgua());
        cv.put(EntomologiaBConstants.cadaCuantoVaAguaOtro, cuest.getCadaCuantoVaAguaOtro());
        cv.put(EntomologiaBConstants.horasSinAguaDia, cuest.getHorasSinAguaDia());
        cv.put(EntomologiaBConstants.queHacenBasuraHogar, cuest.getQueHacenBasuraHogar());
        cv.put(EntomologiaBConstants.queHacenBasuraHogarOtro, cuest.getQueHacenBasuraHogarOtro());
        cv.put(EntomologiaBConstants.riesgoBarrioDengue, cuest.getRiesgoBarrioDengue());
        cv.put(EntomologiaBConstants.accionesCriaderosBarrio, cuest.getAccionesCriaderosBarrio());
        cv.put(EntomologiaBConstants.queAcciones, cuest.getQueAcciones());
        cv.put(EntomologiaBConstants.queAccionesOtro, cuest.getQueAccionesOtro());
        cv.put(EntomologiaBConstants.mayorCriaderoBarrio, cuest.getMayorCriaderoBarrio());
        cv.put(EntomologiaBConstants.edadesFemenino, cuest.getEdadesFemenino());
        cv.put(EntomologiaBConstants.edadesMasculino, cuest.getEdadesMasculino());
        cv.put(EntomologiaBConstants.alguienParticipo, cuest.getAlguienParticipo());
        cv.put(EntomologiaBConstants.quienParticipo, cuest.getQuienParticipo());

        if (cuest.getRecordDate() != null) cv.put(MainDBConstants.recordDate, cuest.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, cuest.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(cuest.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(cuest.getEstado()));
        cv.put(MainDBConstants.deviceId, cuest.getDeviceid());
        return cv;
    }

    public static CuestionarioHogar crearCuestionarioHogar(Cursor cursor){
        CuestionarioHogar cuest = new CuestionarioHogar();
        cuest.setCodigoEncuesta(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoEncuesta)));

        if(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.codigoCasa))>0) cuest.setCodigoCasa(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.codigoCasa)));

        cuest.setQuienContesta(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienContesta)));
        cuest.setQuienContestaOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienContestaOtro)));
        cuest.setEdadContest(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.edadContest)));
        cuest.setEscolaridadContesta(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.escolaridadContesta)));
        cuest.setTiempoVivirBarrio(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tiempoVivirBarrio)));

        if(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.cuantasPersonasViven))>0) cuest.setCuantasPersonasViven(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.cuantasPersonasViven)));

        cuest.setUsaronMosquitero(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.usaronMosquitero)));
        cuest.setQuienesUsaronMosquitero(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienesUsaronMosquitero)));
        cuest.setUsaronRepelente(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.usaronRepelente)));
        cuest.setQuienesUsaronRepelente(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienesUsaronRepelente)));
        cuest.setConoceLarvas(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.conoceLarvas)));
        cuest.setAlguienVisEliminarLarvas(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.alguienVisEliminarLarvas)));
        cuest.setQuienVisEliminarLarvas(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienVisEliminarLarvas)));
        cuest.setQuienVisEliminarLarvasOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienVisEliminarLarvasOtro)));
        cuest.setAlguienDedicaElimLarvasCasa(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.alguienDedicaElimLarvasCasa)));
        cuest.setQuienDedicaElimLarvasCasa(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienDedicaElimLarvasCasa)));
        cuest.setTiempoElimanCridaros(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tiempoElimanCridaros)));
        cuest.setHayBastanteZancudos(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.hayBastanteZancudos)));
        cuest.setQueFaltaCasaEvitarZancudos(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queFaltaCasaEvitarZancudos)));
        cuest.setQueFaltaCasaEvitarZancudosOtros(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queFaltaCasaEvitarZancudosOtros)));
        cuest.setGastaronDineroProductos(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.gastaronDineroProductos)));
        cuest.setQueProductosCompraron(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queProductosCompraron)));
        cuest.setQueProductosCompraronOtros(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queProductosCompraronOtros)));
        cuest.setCuantoGastaron(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.cuantoGastaron)));
        cuest.setUltimaVisitaMinsaBTI(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.ultimaVisitaMinsaBTI)));
        cuest.setUltimaVezMinsaFumigo(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.ultimaVezMinsaFumigo)));
        cuest.setRiesgoCasaDengue(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.riesgoCasaDengue)));
        cuest.setProblemasAbastecimiento(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.problemasAbastecimiento)));
        cuest.setCadaCuantoVaAgua(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.cadaCuantoVaAgua)));
        cuest.setCadaCuantoVaAguaOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.cadaCuantoVaAguaOtro)));

        if(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.horasSinAguaDia))>0) cuest.setHorasSinAguaDia(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.horasSinAguaDia)));

        cuest.setQueHacenBasuraHogar(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queHacenBasuraHogar)));
        cuest.setQueHacenBasuraHogarOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queHacenBasuraHogarOtro)));
        cuest.setRiesgoBarrioDengue(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.riesgoBarrioDengue)));
        cuest.setAccionesCriaderosBarrio(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.accionesCriaderosBarrio)));
        cuest.setQueAcciones(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queAcciones)));
        cuest.setQueAccionesOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queAccionesOtro)));
        cuest.setMayorCriaderoBarrio(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.mayorCriaderoBarrio)));
        cuest.setEdadesFemenino(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.edadesFemenino)));
        cuest.setEdadesMasculino(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.edadesMasculino)));
        cuest.setAlguienParticipo(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.alguienParticipo)));
        cuest.setQuienParticipo(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienParticipo)));


        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) cuest.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        cuest.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        cuest.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        cuest.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        cuest.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return cuest;
    }

    public static void fillCuestionarioHogarStatement(SQLiteStatement stat, CuestionarioHogar part){
        bindString(stat,1, part.getCodigoEncuesta());
        stat.bindLong(2, part.getCodigoCasa());
        bindString(stat,3, part.getQuienContesta());
        bindString(stat,4, part.getQuienContestaOtro());
        bindString(stat,5, part.getEdadContest());
        bindString(stat,6, part.getEscolaridadContesta());
        bindString(stat,7, part.getTiempoVivirBarrio());
        stat.bindLong(8, part.getCuantasPersonasViven());
        bindString(stat,9, part.getUsaronMosquitero());
        bindString(stat,10, part.getQuienesUsaronMosquitero());
        bindString(stat,11, part.getUsaronRepelente());
        bindString(stat,12, part.getQuienesUsaronRepelente());
        bindString(stat,13, part.getConoceLarvas());
        bindString(stat,14, part.getAlguienVisEliminarLarvas());
        bindString(stat,15, part.getQuienVisEliminarLarvas());
        bindString(stat,16, part.getQuienVisEliminarLarvasOtro());
        bindString(stat,17, part.getAlguienDedicaElimLarvasCasa());
        bindString(stat,18, part.getQuienDedicaElimLarvasCasa());
        bindString(stat,19, part.getTiempoElimanCridaros());
        bindString(stat,20, part.getHayBastanteZancudos());
        bindString(stat,21, part.getQueFaltaCasaEvitarZancudos());
        bindString(stat,22, part.getQueFaltaCasaEvitarZancudosOtros());
        bindString(stat,23, part.getGastaronDineroProductos());
        bindString(stat,24, part.getQueProductosCompraron());
        bindString(stat,25, part.getQueProductosCompraronOtros());
        bindString(stat,26, part.getCuantoGastaron());
        bindString(stat,27, part.getUltimaVisitaMinsaBTI());
        bindString(stat,28, part.getUltimaVezMinsaFumigo());
        bindString(stat,29, part.getRiesgoCasaDengue());
        bindString(stat,30, part.getProblemasAbastecimiento());
        bindString(stat,31, part.getCadaCuantoVaAgua());
        bindString(stat,32, part.getCadaCuantoVaAguaOtro());
        stat.bindLong(33, part.getHorasSinAguaDia());
        bindString(stat,34, part.getQueHacenBasuraHogar());
        bindString(stat,35, part.getQueHacenBasuraHogarOtro());
        bindString(stat,36, part.getRiesgoBarrioDengue());
        bindString(stat,37, part.getAccionesCriaderosBarrio());
        bindString(stat,38, part.getQueAcciones());
        bindString(stat,39, part.getQueAccionesOtro());
        bindString(stat,40, part.getMayorCriaderoBarrio());

        bindString(stat,41, part.getEdadesFemenino());
        bindString(stat,42, part.getEdadesMasculino());
        bindString(stat,43, part.getAlguienParticipo());
        bindString(stat,44, part.getQuienParticipo());

        bindDate(stat,45, part.getRecordDate());
        bindString(stat,46, part.getRecordUser());
        stat.bindString(47, String.valueOf(part.getPasive()));
        bindString(stat,48, part.getDeviceid());
        stat.bindString(49, String.valueOf(part.getEstado()));
    }


    public static ContentValues crearCuestionarioHogarPoblacionContentValues(CuestionarioHogarPoblacion cuest){
        ContentValues cv = new ContentValues();
        cv.put(EntomologiaBConstants.codigoPoblacion, cuest.getCodigoPoblacion());
        cv.put(EntomologiaBConstants.codigoEncuesta, cuest.getCodigoEncuesta());
        cv.put(EntomologiaBConstants.codificado, cuest.getCodificado());
        cv.put(EntomologiaBConstants.edad, cuest.getEdad());
        cv.put(EntomologiaBConstants.sexo, cuest.getSexo());

        if (cuest.getRecordDate() != null) cv.put(MainDBConstants.recordDate, cuest.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, cuest.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(cuest.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(cuest.getEstado()));
        cv.put(MainDBConstants.deviceId, cuest.getDeviceid());
        return cv;
    }

    public static CuestionarioHogarPoblacion crearCuestionarioHogarPoblacion(Cursor cursor){
        CuestionarioHogarPoblacion cuest = new CuestionarioHogarPoblacion();
        cuest.setCodigoPoblacion(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoPoblacion)));
        cuest.setCodigoEncuesta(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoEncuesta)));
        cuest.setCodificado(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codificado)));
        cuest.setEdad(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.edad)));
        cuest.setSexo(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.sexo)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) cuest.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        cuest.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        cuest.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        cuest.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        cuest.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return cuest;
    }

    public static void fillCuestionarioHogarPoblacionStatement(SQLiteStatement stat, CuestionarioHogarPoblacion part){
        bindString(stat,1, part.getCodigoPoblacion());
        bindString(stat,2, part.getCodigoEncuesta());
        bindString(stat,3, part.getCodificado());
        bindString(stat,4, part.getEdad());
        bindString(stat,5, part.getSexo());

        bindDate(stat,6, part.getRecordDate());
        bindString(stat,7, part.getRecordUser());
        stat.bindString(8, String.valueOf(part.getPasive()));
        bindString(stat,9, part.getDeviceid());
        stat.bindString(10, String.valueOf(part.getEstado()));
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
