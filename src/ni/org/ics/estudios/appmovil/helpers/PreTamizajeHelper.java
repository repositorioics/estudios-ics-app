package ni.org.ics.estudios.appmovil.helpers;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.PreTamizaje;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

/**
 * Created by Miguel Salinas on 5/9/2017.
 * V1.0
 */
public class PreTamizajeHelper {

    public static ContentValues crearPreTamizajeContentValues(PreTamizaje preTamizaje){
        ContentValues cv = new ContentValues();

        cv.put(MainDBConstants.codigo, preTamizaje.getCodigo());
        cv.put(MainDBConstants.aceptaTamizaje, String.valueOf(preTamizaje.getAceptaTamizaje()));
        cv.put(MainDBConstants.razonNoParticipa, preTamizaje.getRazonNoParticipa());
        cv.put(MainDBConstants.casa, preTamizaje.getCasa().getCodigo());
        cv.put(MainDBConstants.estudio, preTamizaje.getEstudio().getCodigo());
        if (preTamizaje.getRecordDate() != null) cv.put(MainDBConstants.recordDate, preTamizaje.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, preTamizaje.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(preTamizaje.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(preTamizaje.getEstado()));
		cv.put(MainDBConstants.deviceId, preTamizaje.getDeviceid());

        return cv;
    }

    public static PreTamizaje crearPreTamizaje(Cursor cursor){
        PreTamizaje mPreTamizaje = new PreTamizaje();
        mPreTamizaje.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        mPreTamizaje.setAceptaTamizaje(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaTamizaje)).charAt(0));
        mPreTamizaje.setRazonNoParticipa(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonNoParticipa)));
        mPreTamizaje.setCasa(null);
        mPreTamizaje.setEstudio(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mPreTamizaje.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mPreTamizaje.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mPreTamizaje.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mPreTamizaje.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mPreTamizaje.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mPreTamizaje;
    }
}
