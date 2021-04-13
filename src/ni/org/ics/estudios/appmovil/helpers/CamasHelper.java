package ni.org.ics.estudios.appmovil.helpers;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cama;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.PersonaCama;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class CamasHelper {

    public static ContentValues crearCamaContentValues(Cama cama){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigoCama, cama.getCodigoCama());
        if (cama.getCuarto()!=null) cv.put(MainDBConstants.habitacion, cama.getCuarto().getCodigo());
        cv.put(MainDBConstants.descCama, cama.getDescCama());
        if (cama.getRecordDate() != null) cv.put(MainDBConstants.recordDate, cama.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, cama.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(cama.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(cama.getEstado()));
		cv.put(MainDBConstants.deviceId, cama.getDeviceid());
        return cv;
    }

    public static Cama crearCama(Cursor cursor){
        Cama cama = new Cama();
        cama.setCodigoCama(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoCama)));
        cama.setCuarto(null);
        cama.setDescCama(cursor.getString(cursor.getColumnIndex(MainDBConstants.descCama)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) cama.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        cama.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        cama.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        cama.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        cama.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return cama;
    }

    public static ContentValues crearPersonaCamaContentValues(PersonaCama personaCama){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigoPersona, personaCama.getCodigoPersona());
        cv.put(MainDBConstants.cama, personaCama.getCama().getCodigoCama());
        cv.put(MainDBConstants.estaEnEstudio, personaCama.getEstaEnEstudio());
        if (personaCama.getEdad() != null) cv.put(MainDBConstants.edad, personaCama.getEdad());
        if (personaCama.getSexo() != null) cv.put(MainDBConstants.sexo, personaCama.getSexo());
        if (personaCama.getParticipante() != null) cv.put(MainDBConstants.participante, personaCama.getParticipante().getCodigo());
        if (personaCama.getRecordDate() != null) cv.put(MainDBConstants.recordDate, personaCama.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, personaCama.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(personaCama.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(personaCama.getEstado()));
		cv.put(MainDBConstants.deviceId, personaCama.getDeviceid());
        return cv;
    }

    public static PersonaCama crearPersonaCama(Cursor cursor){
        PersonaCama personaCama = new PersonaCama();
        personaCama.setCodigoPersona(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoPersona)));
        personaCama.setCama(null);
        personaCama.setEstaEnEstudio(cursor.getString(cursor.getColumnIndex(MainDBConstants.estaEnEstudio)));
        if (cursor.getInt(cursor.getColumnIndex(MainDBConstants.edad)) > 0) personaCama.setEdad(cursor.getInt(cursor.getColumnIndex(MainDBConstants.edad)));
        personaCama.setSexo(cursor.getString(cursor.getColumnIndex(MainDBConstants.sexo)));
        personaCama.setParticipante(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) personaCama.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        personaCama.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        personaCama.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        personaCama.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        personaCama.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return personaCama;
    }

    public static void fillCamaStatement(SQLiteStatement stat, Cama cama){
        stat.bindString(1, cama.getCodigoCama());
        bindString(stat,2, cama.getCuarto().getCodigo());
        bindString(stat,3, cama.getDescCama());
        bindDate(stat,4, cama.getRecordDate());
        bindString(stat,5, cama.getRecordUser());
        stat.bindString(6, String.valueOf(cama.getPasive()));
        bindString(stat,7, cama.getDeviceid());
        stat.bindString(8, String.valueOf(cama.getEstado()));
    }

    public static void fillPersonaCamaStatement(SQLiteStatement stat, PersonaCama personaCama){
        stat.bindString(1, personaCama.getCodigoPersona());
        bindString(stat,2, personaCama.getCama().getCodigoCama());
        bindString(stat,3, personaCama.getEstaEnEstudio());
        if (personaCama.getParticipante() != null) stat.bindLong(4, personaCama.getParticipante().getCodigo()); else stat.bindNull(4);
        bindInteger(stat,5, personaCama.getEdad());
        bindString(stat,6, personaCama.getSexo());
        bindDate(stat,7, personaCama.getRecordDate());
        bindString(stat,8, personaCama.getRecordUser());
        stat.bindString(9, String.valueOf(personaCama.getPasive()));
        bindString(stat,10, personaCama.getDeviceid());
        stat.bindString(11, String.valueOf(personaCama.getEstado()));
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
