package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.CasaCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by William Aviles 6/9/2017.
 * V1.0
 */
public class CasaCohorteFamiliaCasoHelper {

    public static ContentValues crearCasaCohorteFamiliaCasoContentValues(CasaCohorteFamiliaCaso casaCaso){
        ContentValues cv = new ContentValues();
        cv.put(CasosDBConstants.codigoCaso, casaCaso.getCodigoCaso());
        cv.put(CasosDBConstants.casa, casaCaso.getCasa().getCodigoCHF());
        if (casaCaso.getFechaInicio() != null) cv.put(CasosDBConstants.fechaInicio, casaCaso.getFechaInicio().getTime());
        cv.put(CasosDBConstants.inactiva, casaCaso.getInactiva());
        if (casaCaso.getFechaInactiva() != null) cv.put(CasosDBConstants.fechaInactiva, casaCaso.getFechaInactiva().getTime());
        
        if (casaCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, casaCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, casaCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(casaCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(casaCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, casaCaso.getDeviceid());
        return cv;
    }

    public static CasaCohorteFamiliaCaso crearCasaCohorteFamiliaCaso(Cursor cursor){
    	CasaCohorteFamiliaCaso mCasaCohorteFamiliaCaso = new CasaCohorteFamiliaCaso();
        
    	mCasaCohorteFamiliaCaso.setCodigoCaso(cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCaso)));
        mCasaCohorteFamiliaCaso.setCasa(null);
        if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaInicio))>0) mCasaCohorteFamiliaCaso.setFechaInicio(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaInicio))));
        mCasaCohorteFamiliaCaso.setInactiva(cursor.getString(cursor.getColumnIndex(CasosDBConstants.inactiva)));
        if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaInactiva))>0) mCasaCohorteFamiliaCaso.setFechaInactiva(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaInactiva))));
        
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mCasaCohorteFamiliaCaso.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mCasaCohorteFamiliaCaso.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mCasaCohorteFamiliaCaso.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mCasaCohorteFamiliaCaso.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mCasaCohorteFamiliaCaso.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mCasaCohorteFamiliaCaso;
    }

    public static void fillCasaCohorteFamiliaCasoStatement(SQLiteStatement stat, CasaCohorteFamiliaCaso casaCaso){
        stat.bindString(1, casaCaso.getCodigoCaso());
        bindString(stat,2, casaCaso.getCasa().getCodigoCHF());
        bindDate(stat,3, casaCaso.getFechaInicio());
        bindString(stat,4, casaCaso.getInactiva());
        bindDate(stat,5, casaCaso.getFechaInactiva());
        bindDate(stat,6, casaCaso.getRecordDate());
        bindString(stat,7, casaCaso.getRecordUser());
        stat.bindString(8, String.valueOf(casaCaso.getPasive()));
        bindString(stat,9, casaCaso.getDeviceid());
        stat.bindString(10, String.valueOf(casaCaso.getEstado()));
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
