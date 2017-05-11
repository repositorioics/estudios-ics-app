package ni.org.ics.estudios.appmovil.helpers;

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
        cv.put(MainDBConstants.estado, String.valueOf(preTamizaje.getEstado()));

        return cv;
    }

    public static PreTamizaje crearPretamizaje(Cursor cursor){
        PreTamizaje mPreTamizaje = new PreTamizaje();

        mPreTamizaje.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        mPreTamizaje.setAceptaTamizaje(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaTamizaje)).charAt(0));
        mPreTamizaje.setRazonNoParticipa(null);
        mPreTamizaje.setCasa(null);
        mPreTamizaje.setEstudio(null);
        mPreTamizaje.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));

        return mPreTamizaje;
    }
}
