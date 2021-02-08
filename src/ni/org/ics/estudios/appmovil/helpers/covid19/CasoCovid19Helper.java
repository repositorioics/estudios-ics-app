package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.covid19.CasoCovid19;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

public class CasoCovid19Helper {
    public static ContentValues crearCasoCovid19ContentValues(CasoCovid19 casaCaso){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.codigoCaso, casaCaso.getCodigoCaso());
        if (casaCaso.getCasa()!=null) cv.put(Covid19DBConstants.casa, casaCaso.getCasa().getCodigoCHF());
        if (casaCaso.getFechaIngreso() != null) cv.put(Covid19DBConstants.fechaIngreso, casaCaso.getFechaIngreso().getTime());
        cv.put(Covid19DBConstants.inactivo, casaCaso.getInactivo());
        if (casaCaso.getFechaInactivo() != null) cv.put(Covid19DBConstants.fechaInactivo, casaCaso.getFechaInactivo().getTime());

        if (casaCaso.getRecordDate() != null) cv.put(MainDBConstants.recordDate, casaCaso.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, casaCaso.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(casaCaso.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(casaCaso.getEstado()));
        cv.put(MainDBConstants.deviceId, casaCaso.getDeviceid());
        return cv;
    }

    public static CasoCovid19 crearCasoCovid19(Cursor cursor){
        CasoCovid19 mCasoCovid19 = new CasoCovid19();

        mCasoCovid19.setCodigoCaso(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCaso)));
        mCasoCovid19.setCasa(null);
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaIngreso))>0) mCasoCovid19.setFechaIngreso(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaIngreso))));
        mCasoCovid19.setInactivo(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.inactivo)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaInactivo))>0) mCasoCovid19.setFechaInactivo(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaInactivo))));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mCasoCovid19.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mCasoCovid19.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mCasoCovid19.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mCasoCovid19.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mCasoCovid19.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mCasoCovid19;
    }
}