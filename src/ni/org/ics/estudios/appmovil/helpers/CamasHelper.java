package ni.org.ics.estudios.appmovil.helpers;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
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
        cv.put(MainDBConstants.habitacion, cama.getHabitacion().getCodigo());
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
        cama.setHabitacion(null);
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
        cv.put(MainDBConstants.estaEnEstudio, String.valueOf(personaCama.getEstaEnEstudio()));
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
        personaCama.setEstaEnEstudio(cursor.getString(cursor.getColumnIndex(MainDBConstants.estaEnEstudio)).charAt(0));
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
}
