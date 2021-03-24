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
        if (aislamientoVisitaCasoCovid19.getCodigoAislamiento() != null) stat.bindString(1, aislamientoVisitaCasoCovid19.getCodigoAislamiento());
        if (aislamientoVisitaCasoCovid19.getCodigoCasoVisita() != null) stat.bindString(2, aislamientoVisitaCasoCovid19.getCodigoCasoVisita().getCodigoCasoVisita());
        if (aislamientoVisitaCasoCovid19.getFechaDato() != null) stat.bindLong(3, aislamientoVisitaCasoCovid19.getFechaDato().getTime());
        if (aislamientoVisitaCasoCovid19.getAislado() != null) stat.bindString(4, aislamientoVisitaCasoCovid19.getAislado());
        if (aislamientoVisitaCasoCovid19.getDormirSoloComparte() != null) stat.bindString(5, aislamientoVisitaCasoCovid19.getDormirSoloComparte());
        if (aislamientoVisitaCasoCovid19.getBanioPropioComparte() != null) stat.bindString(6, aislamientoVisitaCasoCovid19.getBanioPropioComparte());
        if (aislamientoVisitaCasoCovid19.getAlejadoFamilia() != null) stat.bindString(7, aislamientoVisitaCasoCovid19.getAlejadoFamilia());
        if (aislamientoVisitaCasoCovid19.getTieneContacto() != null) stat.bindString(8, aislamientoVisitaCasoCovid19.getTieneContacto());
        if (aislamientoVisitaCasoCovid19.getConQuienTieneContacto() != null) stat.bindString(9, aislamientoVisitaCasoCovid19.getConQuienTieneContacto());
        if (aislamientoVisitaCasoCovid19.getLugares() != null) stat.bindString(10, aislamientoVisitaCasoCovid19.getLugares());
        if (aislamientoVisitaCasoCovid19.getOtrosLugares() != null) stat.bindString(11, aislamientoVisitaCasoCovid19.getOtrosLugares());

        if (aislamientoVisitaCasoCovid19.getRecordDate() != null) stat.bindLong(12, aislamientoVisitaCasoCovid19.getRecordDate().getTime());
        if (aislamientoVisitaCasoCovid19.getRecordUser() != null) stat.bindString(13, aislamientoVisitaCasoCovid19.getRecordUser());
        stat.bindString(14, String.valueOf(aislamientoVisitaCasoCovid19.getPasive()));
        if (aislamientoVisitaCasoCovid19.getDeviceid() != null) stat.bindString(15, aislamientoVisitaCasoCovid19.getDeviceid());
        stat.bindString(16, String.valueOf(aislamientoVisitaCasoCovid19.getEstado()));
    }
}
