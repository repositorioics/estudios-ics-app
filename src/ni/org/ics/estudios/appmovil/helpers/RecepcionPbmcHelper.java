package ni.org.ics.estudios.appmovil.helpers;

import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.RecepcionPbmc;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

/**
 * Created by Santiago Carballo on 08/02/2024.
 * V1.0
 */
public class RecepcionPbmcHelper {
    public static RecepcionPbmc crearRecepcionPbmc(Cursor cursor) {
        RecepcionPbmc recepcionPbmc = new RecepcionPbmc();
        recepcionPbmc.setCodigo(cursor.getInt(cursor.getColumnIndex(ConstantsDB.codigo)));
        recepcionPbmc.setFechaPbmc(cursor.getString(cursor.getColumnIndex(ConstantsDB.FECHA_PBMC)));
        recepcionPbmc.setVolPbmc(cursor.getDouble(cursor.getColumnIndex(ConstantsDB.VOLPBMC)));
        String rojoAdicional = cursor.getString(cursor.getColumnIndex(ConstantsDB.ROJO_ADICIONAL));
        if (rojoAdicional.equals("1")) {
            recepcionPbmc.setRojoAdicional(true);
        } else {
            recepcionPbmc.setRojoAdicional(false);
        }
        recepcionPbmc.setVolRojoAdicional(cursor.getDouble(cursor.getColumnIndex(ConstantsDB.VOLROJO_ADICIONAL)));
        recepcionPbmc.setLugar(cursor.getString(cursor.getColumnIndex(ConstantsDB.LUGAR)));
        recepcionPbmc.setObservacion(cursor.getString(cursor.getColumnIndex(ConstantsDB.OBSPBMC)));
        recepcionPbmc.setUsername(cursor.getString(cursor.getColumnIndex(ConstantsDB.USUARIO)));
        recepcionPbmc.setEstudio(cursor.getString(cursor.getColumnIndex(ConstantsDB.ESTUDIO)));
        recepcionPbmc.setEstado(cursor.getString(cursor.getColumnIndex(ConstantsDB.STATUS)));
        recepcionPbmc.setFechaCreacion(cursor.getString(cursor.getColumnIndex(ConstantsDB.FECHA_RECEPCION_PBMC)));
        recepcionPbmc.setFechaRegistro(null);
        return recepcionPbmc;
    }
}
