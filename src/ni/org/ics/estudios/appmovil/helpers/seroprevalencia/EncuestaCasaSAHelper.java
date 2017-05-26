package ni.org.ics.estudios.appmovil.helpers.seroprevalencia;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaCasaSA;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.SeroprevalenciaDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/25/2017.
 * V1.0
 */
public class EncuestaCasaSAHelper {
    public static ContentValues crearEncuestaCasaSAContentValues(EncuestaCasaSA encuesta){
        ContentValues cv = new ContentValues();

        cv.put(SeroprevalenciaDBConstants.casaCHF, encuesta.getCasaCHF().getCodigoCHF());
        cv.put(SeroprevalenciaDBConstants.sedazoPuertasVentanas, encuesta.getSedazoPuertasVentanas());
        cv.put(SeroprevalenciaDBConstants.compraProdEvitarZancudos, encuesta.getCompraProdEvitarZancudos());
        cv.put(SeroprevalenciaDBConstants.tienePatioJardin, encuesta.getTienePatioJardin());
        cv.put(SeroprevalenciaDBConstants.utilizaAbate, encuesta.getUtilizaAbate());
        cv.put(SeroprevalenciaDBConstants.fumiga, encuesta.getFumiga());
        cv.put(SeroprevalenciaDBConstants.cadaCuantoFumiga, encuesta.getCadaCuantoFumiga());

        cv.put(SeroprevalenciaDBConstants.miembroFamConZikaSn, encuesta.getMiembroFamConZikaSn());
        cv.put(SeroprevalenciaDBConstants.cantMiembrosZika, encuesta.getCantMiembrosZika());
        cv.put(SeroprevalenciaDBConstants.fechaDxZika, encuesta.getFechaDxZika());
        cv.put(SeroprevalenciaDBConstants.relacionFamZika, encuesta.getRelacionFamZika());
        cv.put(SeroprevalenciaDBConstants.otraRelacionFamZika, encuesta.getOtraRelacionFamZika());

        cv.put(SeroprevalenciaDBConstants.miembroFamConDengueSn, encuesta.getMiembroFamConDengueSn());
        cv.put(SeroprevalenciaDBConstants.cantMiembrosDengue, encuesta.getCantMiembrosDengue());
        cv.put(SeroprevalenciaDBConstants.fechaDxDengue, encuesta.getFechaDxDengue());
        cv.put(SeroprevalenciaDBConstants.relacionFamDengue, encuesta.getRelacionFamDengue());
        cv.put(SeroprevalenciaDBConstants.otraRelacionFamDengue, encuesta.getOtraRelacionFamDengue());

        cv.put(SeroprevalenciaDBConstants.miembroFamConChikSn, encuesta.getMiembroFamConChikSn());
        cv.put(SeroprevalenciaDBConstants.cantMiembrosChik, encuesta.getCantMiembrosChik());
        cv.put(SeroprevalenciaDBConstants.fechaDxChik, encuesta.getFechaDxChik());
        cv.put(SeroprevalenciaDBConstants.relacionFamChik, encuesta.getRelacionFamChik());
        cv.put(SeroprevalenciaDBConstants.otraRelacionFamChik, encuesta.getOtraRelacionFamChik());

        if (encuesta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, encuesta.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, encuesta.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(encuesta.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));
        cv.put(MainDBConstants.deviceId, encuesta.getDeviceid());
        return cv;
    }

    public static EncuestaCasaSA crearEncuestaCasaSA(Cursor cursor){
        EncuestaCasaSA mEncuesta = new EncuestaCasaSA();

        mEncuesta.setCasaCHF(null);
        mEncuesta.setSedazoPuertasVentanas(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.sedazoPuertasVentanas)));
        mEncuesta.setCompraProdEvitarZancudos(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.compraProdEvitarZancudos)));
        mEncuesta.setTienePatioJardin(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.tienePatioJardin)));
        mEncuesta.setUtilizaAbate(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.utilizaAbate)));
        mEncuesta.setFumiga(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.fumiga)));
        mEncuesta.setCadaCuantoFumiga(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.cadaCuantoFumiga)));

        mEncuesta.setMiembroFamConZikaSn(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.miembroFamConZikaSn)));
        if (cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.cantMiembrosZika))>0) mEncuesta.setCantMiembrosZika(cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.cantMiembrosZika)));
        mEncuesta.setFechaDxZika(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.fechaDxZika)));
        mEncuesta.setRelacionFamZika(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.relacionFamZika)));
        mEncuesta.setOtraRelacionFamZika(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.otraRelacionFamZika)));

        mEncuesta.setMiembroFamConDengueSn(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.miembroFamConDengueSn)));
        if (cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.cantMiembrosDengue))>0) mEncuesta.setCantMiembrosDengue(cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.cantMiembrosDengue)));
        mEncuesta.setFechaDxDengue(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.fechaDxDengue)));
        mEncuesta.setRelacionFamDengue(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.relacionFamDengue)));
        mEncuesta.setOtraRelacionFamDengue(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.otraRelacionFamDengue)));

        mEncuesta.setMiembroFamConChikSn(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.miembroFamConChikSn)));
        if (cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.cantMiembrosChik))>0) mEncuesta.setCantMiembrosChik(cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.cantMiembrosChik)));
        mEncuesta.setFechaDxChik(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.fechaDxChik)));
        mEncuesta.setRelacionFamChik(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.relacionFamChik)));
        mEncuesta.setOtraRelacionFamChik(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.otraRelacionFamChik)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mEncuesta.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mEncuesta.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mEncuesta.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mEncuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mEncuesta.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mEncuesta;
    }

}
