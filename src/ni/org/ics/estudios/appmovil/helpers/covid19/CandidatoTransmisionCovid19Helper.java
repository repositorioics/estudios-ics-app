package ni.org.ics.estudios.appmovil.helpers.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.covid19.CandidatoTransmisionCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.OtrosPositivosCovid;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

public class CandidatoTransmisionCovid19Helper {
    public static ContentValues crearCandidatoTransmisionCovid19ContentValues(CandidatoTransmisionCovid19 candidato){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.codigo, candidato.getCodigo());
        cv.put(Covid19DBConstants.participante, candidato.getParticipante().getCodigo());
        cv.put(Covid19DBConstants.estActuales, candidato.getEstActuales());
        cv.put(Covid19DBConstants.consentimiento, candidato.getConsentimiento());
        cv.put(Covid19DBConstants.positivoPor, candidato.getPositivoPor());
        cv.put(Covid19DBConstants.casaCHF, candidato.getCasaCHF());
        if (candidato.getFis() != null) cv.put(Covid19DBConstants.fis, candidato.getFis().getTime());
        if (candidato.getFif() != null) cv.put(Covid19DBConstants.fif, candidato.getFif().getTime());
        if (candidato.getFechaIngreso() != null) cv.put(Covid19DBConstants.fechaIngreso, candidato.getFechaIngreso().getTime());
        cv.put(Covid19DBConstants.indice, String.valueOf(candidato.getIndice()));

        if (candidato.getRecordDate() != null) cv.put(MainDBConstants.recordDate, candidato.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, candidato.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(candidato.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(candidato.getEstado()));
        cv.put(MainDBConstants.deviceId, candidato.getDeviceid());
        return cv;
    }

    public static CandidatoTransmisionCovid19 crearCandidatoTransmisionCovid19(Cursor cursor){
        CandidatoTransmisionCovid19 mCandidato = new CandidatoTransmisionCovid19();

        mCandidato.setCodigo(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigo)));
        mCandidato.setParticipante(null);
        mCandidato.setEstActuales(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.estActuales)));
        mCandidato.setConsentimiento(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.consentimiento)));
        mCandidato.setPositivoPor(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.positivoPor)));
        mCandidato.setCasaCHF(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.casaCHF)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fis))>0) mCandidato.setFis(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fis))));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fif))>0) mCandidato.setFif(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fif))));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaIngreso))>0) mCandidato.setFechaIngreso(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fechaIngreso))));
        mCandidato.setIndice(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.indice)).charAt(0));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mCandidato.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mCandidato.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mCandidato.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mCandidato.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mCandidato.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mCandidato;
    }

    public static void fillCandidatoTransmisionCovid19Statement(SQLiteStatement stat, CandidatoTransmisionCovid19 candidato){
        if (candidato.getCodigo() != null) stat.bindString(1, candidato.getCodigo());
        stat.bindLong(2, candidato.getParticipante().getCodigo());
        bindString(stat,3, candidato.getCasaCHF());
        bindDate(stat,4, candidato.getFis());
        bindDate(stat,5, candidato.getFif());
        bindString(stat,6, candidato.getPositivoPor());
        bindString(stat,7, candidato.getConsentimiento());
        bindString(stat,8, candidato.getEstActuales());
        bindDate(stat,9, candidato.getFechaIngreso());
        bindString(stat,10, String.valueOf(candidato.getIndice()));

        bindDate(stat,11, candidato.getRecordDate());
        bindString(stat,12, candidato.getRecordUser());
        stat.bindString(13, String.valueOf(candidato.getPasive()));
        bindString(stat,14, candidato.getDeviceid());
        stat.bindString(15, String.valueOf(candidato.getEstado()));
    }

    public static ContentValues crearOtrosPositivosCovidContentValues(OtrosPositivosCovid otrosPositivosCovid){
        ContentValues cv = new ContentValues();
        cv.put(Covid19DBConstants.codigo, otrosPositivosCovid.getCodigo());
        cv.put(Covid19DBConstants.codigoCandidato, otrosPositivosCovid.getCandidatoTransmisionCovid19().getCodigo());
        cv.put(Covid19DBConstants.participante, otrosPositivosCovid.getCodigo_participante());
        cv.put(Covid19DBConstants.estActuales, otrosPositivosCovid.getEstActuales());
        cv.put(Covid19DBConstants.positivoPor, otrosPositivosCovid.getPositivoPor());
        cv.put(Covid19DBConstants.casaCHF, otrosPositivosCovid.getCasaCHF());
        if (otrosPositivosCovid.getFis() != null) cv.put(Covid19DBConstants.fis, otrosPositivosCovid.getFis().getTime());
        if (otrosPositivosCovid.getFif() != null) cv.put(Covid19DBConstants.fif, otrosPositivosCovid.getFif().getTime());

        if (otrosPositivosCovid.getRecordDate() != null) cv.put(MainDBConstants.recordDate, otrosPositivosCovid.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, otrosPositivosCovid.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(otrosPositivosCovid.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(otrosPositivosCovid.getEstado()));
        cv.put(MainDBConstants.deviceId, otrosPositivosCovid.getDeviceid());
        return cv;
    }

    public static OtrosPositivosCovid crearOtrosPositivosCovid(Cursor cursor){
        OtrosPositivosCovid mCandidato = new OtrosPositivosCovid();

        mCandidato.setCodigo(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.codigo)));
        mCandidato.setCodigo_participante(cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.participante)));
        mCandidato.setCandidatoTransmisionCovid19(null);
        mCandidato.setEstActuales(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.estActuales)));
        mCandidato.setPositivoPor(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.positivoPor)));
        mCandidato.setCasaCHF(cursor.getString(cursor.getColumnIndex(Covid19DBConstants.casaCHF)));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fis))>0) mCandidato.setFis(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fis))));
        if(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fif))>0) mCandidato.setFif(new Date(cursor.getLong(cursor.getColumnIndex(Covid19DBConstants.fif))));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mCandidato.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mCandidato.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mCandidato.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mCandidato.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mCandidato.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mCandidato;
    }

    public static void fillOtrosPositivosCovidStatement(SQLiteStatement stat, OtrosPositivosCovid candidato){
        if (candidato.getCodigo() != null) stat.bindLong(1, candidato.getCodigo());
        bindString(stat,2, candidato.getCandidatoTransmisionCovid19().getCodigo());
        stat.bindLong(3, candidato.getCodigo_participante());
        bindString(stat,4, candidato.getCasaCHF());
        bindDate(stat,5,candidato.getFis());
        bindDate(stat,6, candidato.getFif());
        bindString(stat,7, candidato.getPositivoPor());
        bindString(stat,8, candidato.getEstActuales());

        bindDate(stat,9, candidato.getRecordDate());
        bindString(stat,10, candidato.getRecordUser());
        stat.bindString(11, String.valueOf(candidato.getPasive()));
        bindString(stat,12, candidato.getDeviceid());
        stat.bindString(13, String.valueOf(candidato.getEstado()));
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
