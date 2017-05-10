package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.TelefonoContacto;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

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
        if (telefonoContacto.getCasa() != null) cv.put(MainDBConstants.casa, telefonoContacto.getCasa().getCodigo());
        if (telefonoContacto.getParticipante() != null) cv.put(MainDBConstants.participante, telefonoContacto.getParticipante().getCodigo());
        cv.put(MainDBConstants.estado, String.valueOf(telefonoContacto.getEstado()));

        return cv;
    }

    public static TelefonoContacto crearTelefonoContacto(Cursor cursor){
        TelefonoContacto mTelefonoContacto = new TelefonoContacto();

        mTelefonoContacto.setId(cursor.getInt(cursor.getColumnIndex(MainDBConstants.id)));
        mTelefonoContacto.setNumero(cursor.getString(cursor.getColumnIndex(MainDBConstants.numero)));
        mTelefonoContacto.setOperadora(null);
        mTelefonoContacto.setCasa(null);
        mTelefonoContacto.setParticipante(null);
        mTelefonoContacto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));

        return mTelefonoContacto;
    }
}
