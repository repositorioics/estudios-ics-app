package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.RecepcionMuestra;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.MuestrasDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 6/6/2017.
 * V1.0
 */
public class RecepcionMuestraHelper {

    public static ContentValues crearRecepcionMuestraContentValues(RecepcionMuestra recepcionMuestra){
        ContentValues cv = new ContentValues();
        cv.put(MuestrasDBConstants.codigoMx, recepcionMuestra.getCodigoMx());
        if (recepcionMuestra.getFechaRecepcion()!=null) cv.put(MuestrasDBConstants.fechaRecepcion, recepcionMuestra.getFechaRecepcion().getTime());
        cv.put(MuestrasDBConstants.volumen, recepcionMuestra.getVolumen());
        cv.put(MuestrasDBConstants.observacion, recepcionMuestra.getObservacion());
        cv.put(MuestrasDBConstants.lugar, recepcionMuestra.getLugar());
        cv.put(MuestrasDBConstants.paxgene, recepcionMuestra.getPaxgene());
        cv.put(MuestrasDBConstants.tubo, recepcionMuestra.getTubo());
        cv.put(MuestrasDBConstants.tipoMuestra, recepcionMuestra.getTipoMuestra());
        cv.put(MuestrasDBConstants.proposito, recepcionMuestra.getProposito());

        if (recepcionMuestra.getRecordDate() != null) cv.put(MainDBConstants.recordDate, recepcionMuestra.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, recepcionMuestra.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(recepcionMuestra.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(recepcionMuestra.getEstado()));
        cv.put(MainDBConstants.deviceId, recepcionMuestra.getDeviceid());
        return cv;
    }

    public static RecepcionMuestra crearRecepcionMuestra(Cursor cursor){
        RecepcionMuestra mRecepcionMuestra = new RecepcionMuestra();
        mRecepcionMuestra.setCodigoMx(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.codigoMx)));
        if(cursor.getLong(cursor.getColumnIndex(MuestrasDBConstants.fechaRecepcion))>0) mRecepcionMuestra.setFechaRecepcion(new Date(cursor.getLong(cursor.getColumnIndex(MuestrasDBConstants.fechaRecepcion))));
        if (cursor.getDouble(cursor.getColumnIndex(MuestrasDBConstants.volumen))>0) mRecepcionMuestra.setVolumen(cursor.getDouble(cursor.getColumnIndex(MuestrasDBConstants.volumen)));
        mRecepcionMuestra.setObservacion(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.observacion)));
        mRecepcionMuestra.setLugar(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.lugar)));
        mRecepcionMuestra.setPaxgene(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.paxgene)));
        mRecepcionMuestra.setTubo(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.tubo)));
        mRecepcionMuestra.setTipoMuestra(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.tipoMuestra)));
        mRecepcionMuestra.setProposito(cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.proposito)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mRecepcionMuestra.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mRecepcionMuestra.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mRecepcionMuestra.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mRecepcionMuestra.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mRecepcionMuestra.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mRecepcionMuestra;
    }
}
