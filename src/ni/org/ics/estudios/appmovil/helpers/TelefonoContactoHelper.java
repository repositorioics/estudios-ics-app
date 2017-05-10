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

        return cv;
    }

    public static TelefonoContacto crearTelefonoContacto(Cursor cursor){
        TelefonoContacto telefonoContacto = new TelefonoContacto();

        telefonoContacto.setId(cursor.getInt(cursor.getColumnIndex(MainDBConstants.id)));
        telefonoContacto.setNumero(cursor.getString(cursor.getColumnIndex(MainDBConstants.numero)));
        telefonoContacto.setOperadora(null);
        telefonoContacto.setCasa(null);
        telefonoContacto.setParticipante(null);

        return telefonoContacto;
    }
}
