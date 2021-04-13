package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCaso;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by William Aviles 6/9/2017.
 * V1.0
 */
public class VisitaSeguimientoCasoHelper {

    public static ContentValues crearVisitaSeguimientoCasoContentValues(VisitaSeguimientoCaso visitaCaso){
        ContentValues cv = new ContentValues();
        cv.put(CasosDBConstants.codigoCasoVisita, visitaCaso.getCodigoCasoVisita());
        cv.put(CasosDBConstants.codigoCasoParticipante, visitaCaso.getCodigoParticipanteCaso().getCodigoCasoParticipante());        
        if (visitaCaso.getFechaVisita() != null) cv.put(CasosDBConstants.fechaVisita, visitaCaso.getFechaVisita().getTime());
        cv.put(CasosDBConstants.visita, visitaCaso.getVisita());
        cv.put(CasosDBConstants.horaProbableVisita, visitaCaso.getHoraProbableVisita());
        cv.put(CasosDBConstants.expCS, visitaCaso.getExpCS());
        cv.put(CasosDBConstants.temp, visitaCaso.getTemp());
        
        if (visitaCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, visitaCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(visitaCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(visitaCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, visitaCaso.getDeviceid());
        return cv;
    }

    public static VisitaSeguimientoCaso crearVisitaSeguimientoCaso(Cursor cursor){
    	VisitaSeguimientoCaso mVisitaSeguimientoCaso = new VisitaSeguimientoCaso();
        
    	mVisitaSeguimientoCaso.setCodigoCasoVisita(cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCasoVisita)));
    	mVisitaSeguimientoCaso.setCodigoParticipanteCaso(null);
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaVisita))>0) mVisitaSeguimientoCaso.setFechaVisita(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaVisita))));
    	mVisitaSeguimientoCaso.setVisita(cursor.getString(cursor.getColumnIndex(CasosDBConstants.visita)));
    	mVisitaSeguimientoCaso.setHoraProbableVisita(cursor.getString(cursor.getColumnIndex(CasosDBConstants.horaProbableVisita)));
        mVisitaSeguimientoCaso.setExpCS(cursor.getString(cursor.getColumnIndex(CasosDBConstants.expCS)));
        mVisitaSeguimientoCaso.setTemp(cursor.getFloat(cursor.getColumnIndex(CasosDBConstants.temp)));
        
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mVisitaSeguimientoCaso.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mVisitaSeguimientoCaso.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mVisitaSeguimientoCaso.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mVisitaSeguimientoCaso.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mVisitaSeguimientoCaso.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mVisitaSeguimientoCaso;
    }

    public static void fillVisitaSeguimientoCasoStatement(SQLiteStatement stat, VisitaSeguimientoCaso visitaCaso){
        stat.bindString(1, visitaCaso.getCodigoCasoVisita());
        stat.bindString(2, visitaCaso.getCodigoParticipanteCaso().getCodigoCasoParticipante());
        bindDate(stat,3, visitaCaso.getFechaVisita());
        bindString(stat,4, visitaCaso.getVisita());
        bindString(stat,5, visitaCaso.getHoraProbableVisita());
        bindString(stat,6, visitaCaso.getExpCS());
        bindFloat(stat,7, visitaCaso.getTemp());

        bindDate(stat,8, visitaCaso.getRecordDate());
        bindString(stat,9, visitaCaso.getRecordUser());
        stat.bindString(10, String.valueOf(visitaCaso.getPasive()));
        bindString(stat,11, visitaCaso.getDeviceid());
        stat.bindString(12, String.valueOf(visitaCaso.getEstado()));
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

    public static void bindFloat(SQLiteStatement stat, int index, Float value){
        if (value == null) {
            stat.bindNull(index);
        } else {
            stat.bindDouble(index, value);
        }
    }
}
