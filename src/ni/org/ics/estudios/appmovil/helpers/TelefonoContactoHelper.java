package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.TelefonoContacto;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/9/2017.
 * V1.0
 */
public class TelefonoContactoHelper {

    public static ContentValues crearTelefContactoContentValues(TelefonoContacto telefonoContacto){
        ContentValues cv = new ContentValues();

        cv.put(MainDBConstants.id, telefonoContacto.getId());
        cv.put(MainDBConstants.numero, telefonoContacto.getNumero());
        cv.put(MainDBConstants.operadora, telefonoContacto.getOperadora());
        cv.put(MainDBConstants.tipotel, telefonoContacto.getTipo());
        if (telefonoContacto.getCasa() != null) cv.put(MainDBConstants.casa, telefonoContacto.getCasa().getCodigo());
        if (telefonoContacto.getParticipante() != null) cv.put(MainDBConstants.participante, telefonoContacto.getParticipante().getCodigo());
        if (telefonoContacto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, telefonoContacto.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, telefonoContacto.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(telefonoContacto.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(telefonoContacto.getEstado()));
        cv.put(MainDBConstants.deviceId, telefonoContacto.getDeviceid());

        return cv;
    }

    public static TelefonoContacto crearTelefonoContacto(Cursor cursor){
        TelefonoContacto mTelefonoContacto = new TelefonoContacto();

        mTelefonoContacto.setId(cursor.getString(cursor.getColumnIndex(MainDBConstants.id)));
        mTelefonoContacto.setNumero(cursor.getString(cursor.getColumnIndex(MainDBConstants.numero)));
        mTelefonoContacto.setOperadora(cursor.getString(cursor.getColumnIndex(MainDBConstants.operadora)));
        mTelefonoContacto.setTipo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipotel)));
        mTelefonoContacto.setCasa(null);
        mTelefonoContacto.setParticipante(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mTelefonoContacto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mTelefonoContacto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mTelefonoContacto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mTelefonoContacto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mTelefonoContacto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mTelefonoContacto;
    }

    public static void fillTelefContactoStatement(SQLiteStatement stat, TelefonoContacto telefonoContacto){
        stat.bindString(1, telefonoContacto.getId());
        bindString(stat,2, telefonoContacto.getNumero());
        bindString(stat,3, telefonoContacto.getOperadora());
        bindString(stat,4, telefonoContacto.getTipo());
        bindInteger(stat,5, telefonoContacto.getCasa().getCodigo());
        if (telefonoContacto.getParticipante() != null) stat.bindLong(6, telefonoContacto.getParticipante().getCodigo()); else stat.bindNull(6);
        bindDate(stat,7, telefonoContacto.getRecordDate());
        bindString(stat,8, telefonoContacto.getRecordUser());
        stat.bindString(9, String.valueOf(telefonoContacto.getPasive()));
        bindString(stat,10, telefonoContacto.getDeviceid());
        stat.bindString(11, String.valueOf(telefonoContacto.getEstado()));
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

    public static void bindInteger(SQLiteStatement stat, int index, Integer value){
        if (value == null) {
            stat.bindNull(index);
        } else {
            stat.bindLong(index, value);
        }
    }
}
