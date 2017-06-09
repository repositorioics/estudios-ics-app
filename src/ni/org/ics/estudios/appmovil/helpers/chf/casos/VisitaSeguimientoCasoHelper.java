package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
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

}
