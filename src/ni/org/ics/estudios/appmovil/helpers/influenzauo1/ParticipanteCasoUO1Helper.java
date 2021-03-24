package ni.org.ics.estudios.appmovil.helpers.influenzauo1;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.ParticipanteCasoUO1;
import ni.org.ics.estudios.appmovil.utils.InfluenzaUO1DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;
/**
 * Created by Miguel Salinas 22/08/2019.
 * V1.0
 */
public class ParticipanteCasoUO1Helper {
    public static ContentValues crearParticipanteCasoUO1ContentValues(ParticipanteCasoUO1 part){
        ContentValues cv = new ContentValues();
        cv.put(InfluenzaUO1DBConstants.codigoCasoParticipante, part.getCodigoCasoParticipante());
        if (part.getParticipante()!=null) cv.put(InfluenzaUO1DBConstants.participante, part.getParticipante().getCodigo());
        cv.put(InfluenzaUO1DBConstants.positivoPor, part.getPositivoPor());
        if (part.getFif() != null) cv.put(InfluenzaUO1DBConstants.fif, part.getFif().getTime());
        if (part.getFechaIngreso() != null) cv.put(InfluenzaUO1DBConstants.fechaIngreso, part.getFechaIngreso().getTime());
        if (part.getFechaDesactivacion() != null) cv.put(InfluenzaUO1DBConstants.fechaDesactivacion, part.getFechaDesactivacion().getTime());
        cv.put(InfluenzaUO1DBConstants.activo, part.getActivo());

        if (part.getRecordDate() != null) cv.put(MainDBConstants.recordDate, part.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, part.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(part.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(part.getEstado()));
        cv.put(MainDBConstants.deviceId, part.getDeviceid());
        return cv;
    }

    public static ParticipanteCasoUO1 crearParticipanteCasoUO1(Cursor cursor){
        ParticipanteCasoUO1 mParticipanteCasoUO1 = new ParticipanteCasoUO1();
        mParticipanteCasoUO1.setCodigoCasoParticipante(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.codigoCasoParticipante)));
        mParticipanteCasoUO1.setParticipante(null);
        mParticipanteCasoUO1.setPositivoPor(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.positivoPor)));
        mParticipanteCasoUO1.setActivo(cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.activo)));
        if(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fif))>0) mParticipanteCasoUO1.setFif(new Date(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fif))));
        if(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaIngreso))>0) mParticipanteCasoUO1.setFechaIngreso(new Date(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaIngreso))));
        if(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaDesactivacion))>0) mParticipanteCasoUO1.setFechaDesactivacion(new Date(cursor.getLong(cursor.getColumnIndex(InfluenzaUO1DBConstants.fechaDesactivacion))));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mParticipanteCasoUO1.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mParticipanteCasoUO1.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mParticipanteCasoUO1.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mParticipanteCasoUO1.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mParticipanteCasoUO1.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mParticipanteCasoUO1;
    }

    public static void fillParticipanteCasoUO1Statement(SQLiteStatement stat, ParticipanteCasoUO1 part){
        stat.bindString(1, part.getCodigoCasoParticipante());
        if (part.getParticipante()!=null) stat.bindLong(2, part.getParticipante().getCodigo());
        if (part.getPositivoPor() != null) stat.bindString(3, part.getPositivoPor());
        if (part.getFif() != null) stat.bindLong(4, part.getFif().getTime());
        if (part.getFechaIngreso() != null) stat.bindLong(5, part.getFechaIngreso().getTime());
        if (part.getFechaDesactivacion() != null) stat.bindLong(6, part.getFechaDesactivacion().getTime());
        if (part.getActivo() != null) stat.bindString(7, part.getActivo());

        if (part.getRecordDate() != null) stat.bindLong(8, part.getRecordDate().getTime());
        if (part.getRecordUser() != null) stat.bindString(9, part.getRecordUser());
        stat.bindString(10, String.valueOf(part.getPasive()));
        if (part.getDeviceid() != null) stat.bindString(11, part.getDeviceid());
        stat.bindString(12, String.valueOf(part.getEstado()));
    }

}
