package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.covid19.CandidatoTransmisionCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.DatosAislamientoVisitaCasoCovid19;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

public class DatosAislamientoVisitaCasoCovid19Helper {
    public static ContentValues crearDatosAislamientoVisitaCasoCovid19ContentValues(DatosAislamientoVisitaCasoCovid19 aislamientoVisitaCasoCovid19){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.codigoAislamiento, aislamientoVisitaCasoCovid19.getCodigoAislamiento());
        if (aislamientoVisitaCasoCovid19.getFechaDato() != null) cv.put(Covid19DBConstants.fechaDato, aislamientoVisitaCasoCovid19.getFechaDato().getTime());
        cv.put(Covid19DBConstants.codigoCasoVisita, aislamientoVisitaCasoCovid19.getCodigoCasoVisita().getCodigoCasoVisita());
        cv.put(Covid19DBConstants.aislado, aislamientoVisitaCasoCovid19.getAislado());
        cv.put(Covid19DBConstants.dormirSoloComparte, aislamientoVisitaCasoCovid19.getDormirSoloComparte());
        cv.put(Covid19DBConstants.banioPropioComparte, aislamientoVisitaCasoCovid19.getBanioPropioComparte());
        cv.put(Covid19DBConstants.alejadoFamilia, aislamientoVisitaCasoCovid19.getAlejadoFamilia());
        cv.put(Covid19DBConstants.tieneContacto, aislamientoVisitaCasoCovid19.getTieneContacto());
        cv.put(Covid19DBConstants.conQuienTieneContacto, aislamientoVisitaCasoCovid19.getConQuienTieneContacto());
        cv.put(Covid19DBConstants.lugares, aislamientoVisitaCasoCovid19.getLugares());
        cv.put(Covid19DBConstants.otrosLugares, aislamientoVisitaCasoCovid19.getOtrosLugares());

        if (aislamientoVisitaCasoCovid19.getRecordDate() != null) cv.put(MainDBConstants.recordDate, aislamientoVisitaCasoCovid19.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, aislamientoVisitaCasoCovid19.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(aislamientoVisitaCasoCovid19.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(aislamientoVisitaCasoCovid19.getEstado()));
        cv.put(MainDBConstants.deviceId, aislamientoVisitaCasoCovid19.getDeviceid());
        return cv;
    }

    public static DatosAislamientoVisitaCasoCovid19 crearDatosAislamientoVisitaCasoCovid19(Cursor cursor){
        DatosAislamientoVisitaCasoCovid19 mCandidato = new DatosAislamientoVisitaCasoCovid19();

        mCandidato.setCodigoAislamiento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoAislamiento)));
        mCandidato.setCodigoCasoVisita(null);
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaDato))>0) mCandidato.setFechaDato(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaDato))));
        mCandidato.setAislado(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.aislado)));
        mCandidato.setDormirSoloComparte(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.dormirSoloComparte)));
        mCandidato.setBanioPropioComparte(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.banioPropioComparte)));
        mCandidato.setAlejadoFamilia(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.alejadoFamilia)));
        mCandidato.setTieneContacto(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.tieneContacto)));
        mCandidato.setConQuienTieneContacto(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.conQuienTieneContacto)));
        mCandidato.setLugares(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.lugares)));
        mCandidato.setOtrosLugares(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.otrosLugares)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mCandidato.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mCandidato.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mCandidato.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mCandidato.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mCandidato.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mCandidato;
    }

    public static void fillDatosAislamientoVisitaCasoCovid19Statement(SQLiteStatement stat, DatosAislamientoVisitaCasoCovid19 aislamientoVisitaCasoCovid19){
        stat.bindString(1, aislamientoVisitaCasoCovid19.getCodigoAislamiento());
        bindString(stat,2, aislamientoVisitaCasoCovid19.getCodigoCasoVisita().getCodigoCasoVisita());
        bindDate(stat,3, aislamientoVisitaCasoCovid19.getFechaDato());
        bindString(stat,4, aislamientoVisitaCasoCovid19.getAislado());
        bindString(stat,5, aislamientoVisitaCasoCovid19.getDormirSoloComparte());
        bindString(stat,6, aislamientoVisitaCasoCovid19.getBanioPropioComparte());
        bindString(stat,7, aislamientoVisitaCasoCovid19.getAlejadoFamilia());
        bindString(stat,8, aislamientoVisitaCasoCovid19.getTieneContacto());
        bindString(stat,9, aislamientoVisitaCasoCovid19.getConQuienTieneContacto());
        bindString(stat,10, aislamientoVisitaCasoCovid19.getLugares());
        bindString(stat,11, aislamientoVisitaCasoCovid19.getOtrosLugares());

        bindDate(stat,12, aislamientoVisitaCasoCovid19.getRecordDate());
        bindString(stat,13, aislamientoVisitaCasoCovid19.getRecordUser());
        stat.bindString(14, String.valueOf(aislamientoVisitaCasoCovid19.getPasive()));
        bindString(stat,15, aislamientoVisitaCasoCovid19.getDeviceid());
        stat.bindString(16, String.valueOf(aislamientoVisitaCasoCovid19.getEstado()));
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
