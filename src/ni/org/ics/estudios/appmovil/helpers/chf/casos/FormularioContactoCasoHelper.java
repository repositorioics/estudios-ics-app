package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.FormularioContactoCaso;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by William Aviles 6/9/2017.
 * V1.0
 */
public class FormularioContactoCasoHelper {

    public static ContentValues crearFormularioContactoCasoContentValues(FormularioContactoCaso contactoCaso){
        ContentValues cv = new ContentValues();
        cv.put(CasosDBConstants.codigoCasoContacto, contactoCaso.getCodigoCasoContacto());
        cv.put(CasosDBConstants.codigoVisitaCaso, contactoCaso.getCodigoVisitaCaso().getCodigoCasoVisita());        
        cv.put(CasosDBConstants.partContacto, contactoCaso.getPartContacto().getParticipante().getCodigo());
        if (contactoCaso.getFechaContacto() != null) cv.put(CasosDBConstants.fechaContacto, contactoCaso.getFechaContacto().getTime());
        cv.put(CasosDBConstants.tiempoInteraccion, contactoCaso.getTiempoInteraccion());
        cv.put(CasosDBConstants.tipoInteraccion, contactoCaso.getTipoInteraccion());
        
        if (contactoCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, contactoCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, contactoCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(contactoCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(contactoCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, contactoCaso.getDeviceid());
        return cv;
    }

    public static FormularioContactoCaso crearFormularioContactoCaso(Cursor cursor){
    	FormularioContactoCaso mFormularioContactoCaso = new FormularioContactoCaso();
        
    	mFormularioContactoCaso.setCodigoCasoContacto(cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCasoContacto)));
    	mFormularioContactoCaso.setCodigoVisitaCaso(null);
    	mFormularioContactoCaso.setPartContacto(null);
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaContacto))>0) mFormularioContactoCaso.setFechaContacto(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaContacto))));
    	mFormularioContactoCaso.setTiempoInteraccion(cursor.getString(cursor.getColumnIndex(CasosDBConstants.tiempoInteraccion)));
    	mFormularioContactoCaso.setTipoInteraccion(cursor.getString(cursor.getColumnIndex(CasosDBConstants.tipoInteraccion)));
        
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mFormularioContactoCaso.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mFormularioContactoCaso.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mFormularioContactoCaso.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mFormularioContactoCaso.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mFormularioContactoCaso.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mFormularioContactoCaso;
    }

    public static void fillFormularioContactoCasoStatement(SQLiteStatement stat, FormularioContactoCaso contactoCaso){
        stat.bindString(1, contactoCaso.getCodigoCasoContacto());
        stat.bindString(2, contactoCaso.getCodigoVisitaCaso().getCodigoCasoVisita());
        stat.bindLong(3, contactoCaso.getPartContacto().getParticipante().getCodigo());
        bindDate(stat,4, contactoCaso.getFechaContacto());
        bindString(stat,5, contactoCaso.getTiempoInteraccion());
        bindString(stat,6, contactoCaso.getTipoInteraccion());

        bindDate(stat,7, contactoCaso.getRecordDate());
        bindString(stat,8, contactoCaso.getRecordUser());
        stat.bindString(9, String.valueOf(contactoCaso.getPasive()));
        bindString(stat,10, contactoCaso.getDeviceid());
        stat.bindString(11, String.valueOf(contactoCaso.getEstado()));
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
