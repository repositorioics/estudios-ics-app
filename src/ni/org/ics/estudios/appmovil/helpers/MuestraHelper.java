package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.MuestraSuperficie;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.MuestrasDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class MuestraHelper {

    public static ContentValues crearMuestraContentValues(Muestra muestra){
        ContentValues cv = new ContentValues();
        cv.put(MuestrasDBConstants.codigo, muestra.getCodigo());
        cv.put(MuestrasDBConstants.tomaMxSn, muestra.getTomaMxSn());
        cv.put(MuestrasDBConstants.codigoMx, muestra.getCodigoMx());
        cv.put(MuestrasDBConstants.hora, muestra.getHora());
        cv.put(MuestrasDBConstants.horaFin, muestra.getHoraFin());
        cv.put(MuestrasDBConstants.volumen, muestra.getVolumen());
        cv.put(MuestrasDBConstants.observacion, muestra.getObservacion());
        cv.put(MuestrasDBConstants.descOtraObservacion, muestra.getDescOtraObservacion());
        cv.put(MuestrasDBConstants.numPinchazos, muestra.getNumPinchazos());
        cv.put(MuestrasDBConstants.razonNoToma, muestra.getRazonNoToma());
        cv.put(MuestrasDBConstants.descOtraRazonNoToma, muestra.getDescOtraRazonNoToma());
        cv.put(MuestrasDBConstants.tubo, muestra.getTubo());
        cv.put(MuestrasDBConstants.tipoMuestra, muestra.getTipoMuestra());
        cv.put(MuestrasDBConstants.proposito, muestra.getProposito());
        cv.put(MuestrasDBConstants.participante, muestra.getParticipante().getCodigo());
        cv.put(MuestrasDBConstants.realizaPaxgene, muestra.getRealizaPaxgene());
        cv.put(MuestrasDBConstants.horaInicioPax, muestra.getHoraInicioPax());
        cv.put(MuestrasDBConstants.horaFinPax, muestra.getHoraFinPax());

        if (muestra.getRecordDate() != null) cv.put(MainDBConstants.recordDate, muestra.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, muestra.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(muestra.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(muestra.getEstado()));
        cv.put(MainDBConstants.deviceId, muestra.getDeviceid());
        return cv;
    }

    public static Muestra crearMuestra(Cursor cursor){
        Muestra mMuestra = new Muestra();
        mMuestra.setCodigo(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.codigo)));
        mMuestra.setTomaMxSn(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.tomaMxSn)));
        mMuestra.setCodigoMx(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.codigoMx)));
        mMuestra.setHora(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.hora)));
        mMuestra.setHoraFin(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.horaFin)));
        if (cursor.getDouble(cursor.getColumnIndex(MuestrasDBConstants.volumen))>0) mMuestra.setVolumen(cursor.getDouble(cursor.getColumnIndex(MuestrasDBConstants.volumen)));
        mMuestra.setObservacion(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.observacion)));
        mMuestra.setDescOtraObservacion(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.descOtraObservacion)));
        mMuestra.setNumPinchazos(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.numPinchazos)));
        mMuestra.setRazonNoToma(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.razonNoToma)));
        mMuestra.setDescOtraRazonNoToma(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.descOtraRazonNoToma)));
        mMuestra.setTubo(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.tubo)));
        mMuestra.setTipoMuestra(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.tipoMuestra)));
        mMuestra.setProposito(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.proposito)));
        mMuestra.setParticipante(null);
        mMuestra.setRealizaPaxgene(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.realizaPaxgene)));
        mMuestra.setHoraInicioPax(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.horaInicioPax)));
        mMuestra.setHoraFinPax(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.horaFinPax)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mMuestra.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mMuestra.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mMuestra.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mMuestra.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mMuestra.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mMuestra;
    }

    //MUESTRAS DE SUPERFICIE
    public static ContentValues crearMuestraSuperficieContentValues(MuestraSuperficie muestra){
        ContentValues cv = new ContentValues();
        cv.put(MuestrasDBConstants.codigo, muestra.getCodigo());
        cv.put(MuestrasDBConstants.codigoMx, muestra.getCodigoMx());
        cv.put(MuestrasDBConstants.tipoMuestra, muestra.getTipoMuestra());
        cv.put(MuestrasDBConstants.otraSuperficie, muestra.getOtraSuperficie());
        cv.put(MuestrasDBConstants.visita, muestra.getVisita());
        cv.put(MuestrasDBConstants.caso, muestra.getCaso());
        if (muestra.getCasaChf()!=null) cv.put(MuestrasDBConstants.casaChf, muestra.getCasaChf().getCodigoCHF());
        if (muestra.getParticipanteChf()!=null) cv.put(MuestrasDBConstants.participanteChf, muestra.getParticipanteChf().getParticipante().getCodigo());

        if (muestra.getRecordDate() != null) cv.put(MainDBConstants.recordDate, muestra.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, muestra.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(muestra.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(muestra.getEstado()));
        cv.put(MainDBConstants.deviceId, muestra.getDeviceid());
        return cv;
    }

    public static MuestraSuperficie crearMuestraSuperficie(Cursor cursor){
        MuestraSuperficie mMuestra = new MuestraSuperficie();
        mMuestra.setCodigo(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.codigo)));
        mMuestra.setCodigoMx(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.codigoMx)));
        mMuestra.setTipoMuestra(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.tipoMuestra)));
        mMuestra.setOtraSuperficie(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.otraSuperficie)));
        mMuestra.setVisita(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.visita)));
        mMuestra.setCaso(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.caso)));
        mMuestra.setParticipanteChf(null);
        mMuestra.setCasaChf(null);

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mMuestra.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mMuestra.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mMuestra.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mMuestra.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mMuestra.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mMuestra;
    }

    public static void fillMuestraChfStatement(SQLiteStatement stat, Muestra muestra){
        stat.bindString(1, muestra.getCodigo());
        if (muestra.getTomaMxSn() != null) stat.bindString(2, muestra.getTomaMxSn());
        if (muestra.getCodigoMx() != null) stat.bindString(3, muestra.getCodigoMx());
        if (muestra.getHora() != null) stat.bindString(4, muestra.getHora());
        if (muestra.getHoraFin() != null) stat.bindString(5, muestra.getHoraFin());
        if (muestra.getVolumen() != null) stat.bindDouble(6, muestra.getVolumen());
        if (muestra.getObservacion() != null) stat.bindString(7, muestra.getObservacion());
        if (muestra.getDescOtraObservacion() != null) stat.bindString(8, muestra.getDescOtraObservacion());
        if (muestra.getNumPinchazos() != null) stat.bindString(9, muestra.getNumPinchazos());
        if (muestra.getRazonNoToma() != null) stat.bindString(10, muestra.getRazonNoToma());
        if (muestra.getDescOtraRazonNoToma() != null) stat.bindString(11, muestra.getDescOtraRazonNoToma());
        if (muestra.getTubo() != null) stat.bindString(12, muestra.getTubo());
        if (muestra.getTipoMuestra() != null) stat.bindString(13, muestra.getTipoMuestra());
        if (muestra.getProposito() != null) stat.bindString(14, muestra.getProposito());
        if (muestra.getParticipante() != null) stat.bindLong(15, muestra.getParticipante().getCodigo());
        if (muestra.getRealizaPaxgene() != null) stat.bindString(16, muestra.getRealizaPaxgene());
        if (muestra.getHoraInicioPax() != null) stat.bindString(17, muestra.getHoraInicioPax());
        if (muestra.getHoraFinPax() != null) stat.bindString(18, muestra.getHoraFinPax());

        if (muestra.getRecordDate() != null) stat.bindLong(19, muestra.getRecordDate().getTime());
        if (muestra.getRecordUser() != null) stat.bindString(20, muestra.getRecordUser());
        stat.bindString(21, String.valueOf(muestra.getPasive()));
        if (muestra.getDeviceid() != null) stat.bindString(22, muestra.getDeviceid());
        stat.bindString(23, String.valueOf(muestra.getEstado()));
    }

    public static void fillMuestraSuperficieStatement(SQLiteStatement stat, MuestraSuperficie muestra){
        stat.bindString(1, muestra.getCodigo());
        if (muestra.getTipoMuestra() != null) stat.bindString(2, muestra.getTipoMuestra());
        if (muestra.getOtraSuperficie() != null) stat.bindString(3, muestra.getOtraSuperficie());
        if (muestra.getCodigoMx() != null) stat.bindString(4, muestra.getCodigoMx());
        if (muestra.getCasaChf()!=null) stat.bindString(5, muestra.getCasaChf().getCodigoCHF());
        if (muestra.getParticipanteChf()!=null) stat.bindLong(6, muestra.getParticipanteChf().getParticipante().getCodigo());
        if (muestra.getVisita() != null) stat.bindString(7, muestra.getVisita());
        if (muestra.getCaso() != null) stat.bindString(8, muestra.getCaso());
        if (muestra.getRecordDate() != null) stat.bindLong(9, muestra.getRecordDate().getTime());
        if (muestra.getRecordUser() != null) stat.bindString(10, muestra.getRecordUser());
        stat.bindString(11, String.valueOf(muestra.getPasive()));
        if (muestra.getDeviceid() != null) stat.bindString(12, muestra.getDeviceid());
        stat.bindString(13, String.valueOf(muestra.getEstado()));
    }
}
