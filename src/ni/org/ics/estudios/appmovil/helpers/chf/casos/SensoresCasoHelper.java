package ni.org.ics.estudios.appmovil.helpers.chf.casos;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.SensorCaso;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas 09/10/2018.
 * V1.0
 */
public class SensoresCasoHelper {

    public static ContentValues crearSensorCasoContentValues(SensorCaso sensor){
        ContentValues cv = new ContentValues();
        cv.put(CasosDBConstants.codigoSensor, sensor.getCodigoSensor());
        cv.put(CasosDBConstants.codigoCaso, sensor.getCodigoCaso().getCodigoCaso());
        cv.put(CasosDBConstants.numeroSensor, sensor.getNumeroSensor());
        if (sensor.getArea()!=null)cv.put(CasosDBConstants.area, sensor.getArea().getCodigo());
        if (sensor.getCuarto()!=null)cv.put(CasosDBConstants.habitacionSensor, sensor.getCuarto().getCodigo());
        if (sensor.getFechaColocacion() != null) cv.put(CasosDBConstants.fechaColocacion, sensor.getFechaColocacion().getTime());
        if (sensor.getFechaRetiro() != null) cv.put(CasosDBConstants.fechaRetiro, sensor.getFechaRetiro().getTime());
        cv.put(CasosDBConstants.horaRetiro, sensor.getHoraRetiro());
        cv.put(CasosDBConstants.observacionRetiro, sensor.getObservacionRetiro());
        cv.put(CasosDBConstants.sensorSN, sensor.getSensorSN());
        cv.put(CasosDBConstants.razonNoColocaSensor, sensor.getRazonNoColocaSensor());

        if (sensor.getRecordDate() != null) cv.put(MainDBConstants.recordDate, sensor.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, sensor.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(sensor.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(sensor.getEstado()));
        cv.put(MainDBConstants.deviceId, sensor.getDeviceid());
        return cv;
    }

    public static SensorCaso crearSensorCaso(Cursor cursor){
        SensorCaso mSensor = new SensorCaso();
        
    	mSensor.setCodigoSensor(cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoSensor)));
    	mSensor.setCodigoCaso(null);
        mSensor.setNumeroSensor(cursor.getString(cursor.getColumnIndex(CasosDBConstants.numeroSensor)));
        mSensor.setArea(null);
        mSensor.setCuarto(null);
    	if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaColocacion))>0) mSensor.setFechaColocacion(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaColocacion))));
        if(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaRetiro))>0) mSensor.setFechaRetiro(new Date(cursor.getLong(cursor.getColumnIndex(CasosDBConstants.fechaRetiro))));
    	mSensor.setHoraRetiro(cursor.getString(cursor.getColumnIndex(CasosDBConstants.horaRetiro)));
    	mSensor.setObservacionRetiro(cursor.getString(cursor.getColumnIndex(CasosDBConstants.observacionRetiro)));
        mSensor.setSensorSN(cursor.getString(cursor.getColumnIndex(CasosDBConstants.sensorSN)));
        mSensor.setRazonNoColocaSensor(cursor.getString(cursor.getColumnIndex(CasosDBConstants.razonNoColocaSensor)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mSensor.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mSensor.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mSensor.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mSensor.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mSensor.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mSensor;
    }

    public static void fillSensorCasoStatement(SQLiteStatement stat, SensorCaso sensor){
        stat.bindString(1, sensor.getCodigoSensor());
        stat.bindString(2, sensor.getCodigoCaso().getCodigoCaso());
        bindString(stat,3, sensor.getNumeroSensor());
        bindString(stat,4, sensor.getArea().getCodigo());
        bindString(stat,5, sensor.getCuarto() != null ? sensor.getCuarto().getCodigo() : null);
        bindDate(stat,6, sensor.getFechaColocacion());
        bindDate(stat,7, sensor.getFechaRetiro());
        bindString(stat,8, sensor.getHoraRetiro());
        bindString(stat,9, sensor.getObservacionRetiro());
        bindString(stat,10, sensor.getSensorSN());
        bindString(stat,11, sensor.getRazonNoColocaSensor());

        bindDate(stat,12, sensor.getRecordDate());
        bindString(stat,13, sensor.getRecordUser());
        stat.bindString(14, String.valueOf(sensor.getPasive()));
        bindString(stat,15, sensor.getDeviceid());
        stat.bindString(16, String.valueOf(sensor.getEstado()));
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
