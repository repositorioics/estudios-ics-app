package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
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
}
