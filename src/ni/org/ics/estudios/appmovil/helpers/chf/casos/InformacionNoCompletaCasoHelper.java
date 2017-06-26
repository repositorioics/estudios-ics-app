package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.InformacionNoCompletaCaso;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by William Aviles 6/9/2017.
 * V1.0
 */
public class InformacionNoCompletaCasoHelper {

    public static ContentValues crearInformacionNoCompletaCasoContentValues(InformacionNoCompletaCaso noData){
        ContentValues cv = new ContentValues();
        cv.put(CasosDBConstants.codigoNoDataVisita, noData.getCodigoNoDataVisita());
        cv.put(CasosDBConstants.codigoVisitaCaso, noData.getCodigoVisitaCaso().getCodigoCasoVisita());        
        cv.put(CasosDBConstants.razonNoCompletaInformacion, noData.getRazonNoCompletaInformacion());
        cv.put(CasosDBConstants.otraRazon, noData.getOtraRazon());
        
        if (noData.getRecordDate() != null) cv.put(MainDBConstants.recordDate, noData.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, noData.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(noData.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(noData.getEstado()));
        cv.put(MainDBConstants.deviceId, noData.getDeviceid());
        return cv;
    }

    public static InformacionNoCompletaCaso crearInformacionNoCompletaCaso(Cursor cursor){
    	InformacionNoCompletaCaso mInformacionNoCompletaCaso = new InformacionNoCompletaCaso();     
    	mInformacionNoCompletaCaso.setCodigoNoDataVisita(cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoNoDataVisita)));
    	mInformacionNoCompletaCaso.setCodigoVisitaCaso(null);
    	mInformacionNoCompletaCaso.setRazonNoCompletaInformacion(cursor.getString(cursor.getColumnIndex(CasosDBConstants.razonNoCompletaInformacion)));
    	mInformacionNoCompletaCaso.setOtraRazon(cursor.getString(cursor.getColumnIndex(CasosDBConstants.otraRazon)));
    	
    	if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mInformacionNoCompletaCaso.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mInformacionNoCompletaCaso.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mInformacionNoCompletaCaso.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mInformacionNoCompletaCaso.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mInformacionNoCompletaCaso.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mInformacionNoCompletaCaso;
    }
}
