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
        cv.put(MainDBConstants.aceptaTamizaje, String.valueOf(tamizaje.getAceptaTamizaje()));
        cv.put(MainDBConstants.razonNoParticipa, tamizaje.getRazonNoParticipa());
        cv.put(MainDBConstants.areaCobertura, String.valueOf(tamizaje.getAreaCobertura()));
        cv.put(MainDBConstants.ninoMenor12Anios, String.valueOf(tamizaje.getNinoMenor12Anios()));
        cv.put(MainDBConstants.intencionPermanecerArea, String.valueOf(tamizaje.getIntencionPermanecerArea()));
        cv.put(MainDBConstants.tieneTarjetaVacunaOIdentificacion, String.valueOf(tamizaje.getTieneTarjetaVacunaOIdentificacion()));
        cv.put(MainDBConstants.enfermedadAgudaCronica, String.valueOf(tamizaje.getEnfermedadAgudaCronica()));
        cv.put(MainDBConstants.elegible, String.valueOf(tamizaje.getElegible()));
        cv.put(MainDBConstants.dondeAsisteProblemasSalud, tamizaje.getDondeAsisteProblemasSalud());
        cv.put(MainDBConstants.asisteCSSF, String.valueOf(tamizaje.getAsisteCSSF()));
        cv.put(MainDBConstants.otroCentroSalud, tamizaje.getOtroCentroSalud());
        cv.put(MainDBConstants.puestoSalud, tamizaje.getPuestoSalud());
        cv.put(MainDBConstants.siEnfermaSoloAsistirCSSF, String.valueOf(tamizaje.getSiEnfermaSoloAsistirCSSF()));
        cv.put(MainDBConstants.tomaPuntoGPSCasa, String.valueOf(tamizaje.getTomaPuntoGPSCasa()));
        cv.put(MainDBConstants.razonNoGeoreferenciacion, tamizaje.getRazonNoGeoreferenciacion());
        cv.put(MainDBConstants.otraRazonNoGeoreferenciacion, tamizaje.getOtraRazonNoGeoreferenciacion());
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
        mTamizaje.setAceptaTamizaje(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaTamizaje)).charAt(0));
        mTamizaje.setRazonNoParticipa(null);
        mTamizaje.setAreaCobertura(cursor.getString(cursor.getColumnIndex(MainDBConstants.areaCobertura)).charAt(0));
        mTamizaje.setNinoMenor12Anios(cursor.getString(cursor.getColumnIndex(MainDBConstants.ninoMenor12Anios)).charAt(0));
        mTamizaje.setIntencionPermanecerArea(cursor.getString(cursor.getColumnIndex(MainDBConstants.intencionPermanecerArea)).charAt(0));
        mTamizaje.setTieneTarjetaVacunaOIdentificacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.tieneTarjetaVacunaOIdentificacion)).charAt(0));
        mTamizaje.setEnfermedadAgudaCronica(cursor.getString(cursor.getColumnIndex(MainDBConstants.enfermedadAgudaCronica)).charAt(0));
        mTamizaje.setElegible(cursor.getString(cursor.getColumnIndex(MainDBConstants.elegible)).charAt(0));
        mTamizaje.setDondeAsisteProblemasSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.dondeAsisteProblemasSalud)));
        mTamizaje.setAsisteCSSF(cursor.getString(cursor.getColumnIndex(MainDBConstants.asisteCSSF)).charAt(0));
        mTamizaje.setOtroCentroSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.otroCentroSalud)));
        mTamizaje.setPuestoSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.puestoSalud)));
        mTamizaje.setSiEnfermaSoloAsistirCSSF(cursor.getString(cursor.getColumnIndex(MainDBConstants.siEnfermaSoloAsistirCSSF)).charAt(0));
        mTamizaje.setTomaPuntoGPSCasa(cursor.getString(cursor.getColumnIndex(MainDBConstants.tomaPuntoGPSCasa)).charAt(0));
        mTamizaje.setRazonNoGeoreferenciacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonNoGeoreferenciacion)));
        mTamizaje.setOtraRazonNoGeoreferenciacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonNoGeoreferenciacion)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mTamizaje.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mTamizaje.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mTamizaje.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mTamizaje.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mTamizaje.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mTamizaje;
    }
}
