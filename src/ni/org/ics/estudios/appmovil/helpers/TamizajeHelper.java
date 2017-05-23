package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.Tamizaje;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/9/2017.
 * V1.0
 */
public class TamizajeHelper {

    public static ContentValues crearTamizajeContentValues(Tamizaje tamizaje){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, tamizaje.getCodigo());
        cv.put(MainDBConstants.estudio, tamizaje.getEstudio().getCodigo());
        cv.put(MainDBConstants.sexo, tamizaje.getSexo());
        if (tamizaje.getFechaNacimiento()!=null) cv.put(MainDBConstants.fechaNacimiento, tamizaje.getFechaNacimiento().getTime());
        cv.put(MainDBConstants.aceptaTamizajePersona, String.valueOf(tamizaje.getAceptaTamizajePersona()));
        cv.put(MainDBConstants.razonNoAceptaTamizajePersona, tamizaje.getRazonNoAceptaTamizajePersona());
        cv.put(MainDBConstants.criteriosInclusion, tamizaje.getCriteriosInclusion());
        cv.put(MainDBConstants.enfermedad, tamizaje.getEnfermedad());
        cv.put(MainDBConstants.dondeAsisteProblemasSalud, tamizaje.getDondeAsisteProblemasSalud());
        cv.put(MainDBConstants.otroCentroSalud, tamizaje.getOtroCentroSalud());
        cv.put(MainDBConstants.puestoSalud, tamizaje.getPuestoSalud());
        cv.put(MainDBConstants.aceptaAtenderCentro, String.valueOf(tamizaje.getAceptaAtenderCentro()));
        cv.put(MainDBConstants.esElegible, String.valueOf(tamizaje.getEsElegible()));
        cv.put(MainDBConstants.aceptaParticipar, String.valueOf(tamizaje.getAceptaParticipar()));
        cv.put(MainDBConstants.razonNoAceptaParticipar, String.valueOf(tamizaje.getRazonNoAceptaParticipar()));
        cv.put(MainDBConstants.asentimientoVerbal, String.valueOf(tamizaje.getAsentimientoVerbal()));
        if (tamizaje.getRecordDate() != null) cv.put(MainDBConstants.recordDate, tamizaje.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, tamizaje.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(tamizaje.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(tamizaje.getEstado()));
        cv.put(MainDBConstants.deviceId, tamizaje.getDeviceid());
        return cv;
    }

    public static Tamizaje crearTamizaje(Cursor cursor){
        Tamizaje mTamizaje = new Tamizaje();

        mTamizaje.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        mTamizaje.setEstudio(null);
        mTamizaje.setSexo(cursor.getString(cursor.getColumnIndex(MainDBConstants.sexo)));
        if (cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaNacimiento)) > 0) mTamizaje.setFechaNacimiento(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaNacimiento))));
        mTamizaje.setAceptaTamizajePersona(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaTamizajePersona)));
        mTamizaje.setRazonNoAceptaTamizajePersona(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonNoAceptaTamizajePersona)));
        mTamizaje.setCriteriosInclusion(cursor.getString(cursor.getColumnIndex(MainDBConstants.criteriosInclusion)));
        mTamizaje.setDondeAsisteProblemasSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.dondeAsisteProblemasSalud)));
        mTamizaje.setOtroCentroSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.otroCentroSalud)));
        mTamizaje.setPuestoSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.puestoSalud)));
        mTamizaje.setAceptaAtenderCentro(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaAtenderCentro)));
        mTamizaje.setEsElegible(cursor.getString(cursor.getColumnIndex(MainDBConstants.esElegible)));
        mTamizaje.setAceptaParticipar(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaParticipar)));
        mTamizaje.setRazonNoAceptaParticipar(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonNoAceptaParticipar)));
        mTamizaje.setAsentimientoVerbal(cursor.getString(cursor.getColumnIndex(MainDBConstants.asentimientoVerbal)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mTamizaje.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mTamizaje.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mTamizaje.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mTamizaje.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mTamizaje.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mTamizaje;
    }
}
