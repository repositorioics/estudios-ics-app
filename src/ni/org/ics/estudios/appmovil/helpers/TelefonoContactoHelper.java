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
        if (telefonoContacto.getNumero() != null) stat.bindString(2, telefonoContacto.getNumero());
        if (telefonoContacto.getOperadora() != null) stat.bindString(3, telefonoContacto.getOperadora());
        if (telefonoContacto.getTipo() != null) stat.bindString(4, telefonoContacto.getTipo());
        if (telefonoContacto.getCasa() != null) stat.bindLong(5, telefonoContacto.getCasa().getCodigo());
        if (telefonoContacto.getParticipante() != null) stat.bindLong(6, telefonoContacto.getParticipante().getCodigo());
        if (telefonoContacto.getRecordDate() != null) stat.bindLong(7, telefonoContacto.getRecordDate().getTime());
        if (telefonoContacto.getRecordUser() != null) stat.bindString(8, telefonoContacto.getRecordUser());
        stat.bindString(9, String.valueOf(telefonoContacto.getPasive()));
        if (telefonoContacto.getDeviceid() != null) stat.bindString(10, telefonoContacto.getDeviceid());
        stat.bindString(11, String.valueOf(telefonoContacto.getEstado()));
        }
}
