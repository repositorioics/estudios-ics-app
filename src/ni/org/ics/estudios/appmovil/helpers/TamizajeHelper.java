package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.Tamizaje;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

/**
 * Created by Miguel Salinas on 5/9/2017.
 * V1.0
 */
public class TamizajeHelper {

    public static ContentValues crearTamizajeContentValues(Tamizaje tamizaje){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, tamizaje.getCodigo());
        if (tamizaje.getParticipante() != null) cv.put(MainDBConstants.participante, tamizaje.getParticipante().getCodigo());
        cv.put(MainDBConstants.aceptaTamizaje, String.valueOf(tamizaje.getAceptaTamizaje()));
        cv.put(MainDBConstants.razonNoParticipa, tamizaje.getRazonNoParticipa());
        cv.put(MainDBConstants.areaCovertura, String.valueOf(tamizaje.getAreaCovertura()));
        cv.put(MainDBConstants.ninoMenor12Anios, String.valueOf(tamizaje.getNinoMenor12Anios()));
        cv.put(MainDBConstants.intencionPermanecerArea, String.valueOf(tamizaje.getIntencionPermanecerArea()));
        cv.put(MainDBConstants.tieneTarjetaVacunaOIdentificacion, String.valueOf(tamizaje.getTieneTarjetaVacunaOIdentificacion()));
        cv.put(MainDBConstants.enfermedadAgudaCronica, String.valueOf(tamizaje.getEnfermedadAgudaCronica()));
        cv.put(MainDBConstants.elegible, String.valueOf(tamizaje.getElegible()));
        cv.put(MainDBConstants.aceptaSeroprevalenciaZik, String.valueOf(tamizaje.getAceptaSeroprevalenciaZik()));
        cv.put(MainDBConstants.dondeAsisteProblemasSalud, tamizaje.getDondeAsisteProblemasSalud());
        cv.put(MainDBConstants.asisteCSSF, String.valueOf(tamizaje.getAsisteCSSF()));
        cv.put(MainDBConstants.otroCentroSalud, tamizaje.getOtroCentroSalud());
        cv.put(MainDBConstants.puestoSalud, tamizaje.getPuestoSalud());
        cv.put(MainDBConstants.siEnfermaSoloAsistirCSSF, String.valueOf(tamizaje.getSiEnfermaSoloAsistirCSSF()));
        cv.put(MainDBConstants.tomaPuntoGPSCasa, String.valueOf(tamizaje.getTomaPuntoGPSCasa()));
        cv.put(MainDBConstants.razonNoGeoreferenciacion, tamizaje.getRazonNoGeoreferenciacion());
        cv.put(MainDBConstants.otraRazonNoGeoreferenciacion, tamizaje.getOtraRazonNoGeoreferenciacion());
        return cv;
    }

    public static Tamizaje crearTamizaje(Cursor cursor){
        Tamizaje mTamizaje = new Tamizaje();

        mTamizaje.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        mTamizaje.setParticipante(null);
        mTamizaje.setAceptaTamizaje(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaTamizaje)).charAt(0));
        mTamizaje.setRazonNoParticipa(null);
        mTamizaje.setAreaCovertura(cursor.getString(cursor.getColumnIndex(MainDBConstants.areaCovertura)).charAt(0));
        mTamizaje.setNinoMenor12Anios(cursor.getString(cursor.getColumnIndex(MainDBConstants.ninoMenor12Anios)).charAt(0));
        mTamizaje.setIntencionPermanecerArea(cursor.getString(cursor.getColumnIndex(MainDBConstants.intencionPermanecerArea)).charAt(0));
        mTamizaje.setTieneTarjetaVacunaOIdentificacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.tieneTarjetaVacunaOIdentificacion)).charAt(0));
        mTamizaje.setEnfermedadAgudaCronica(cursor.getString(cursor.getColumnIndex(MainDBConstants.enfermedadAgudaCronica)).charAt(0));
        mTamizaje.setElegible(cursor.getString(cursor.getColumnIndex(MainDBConstants.elegible)).charAt(0));
        mTamizaje.setAceptaSeroprevalenciaZik(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaSeroprevalenciaZik)).charAt(0));
        mTamizaje.setDondeAsisteProblemasSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.dondeAsisteProblemasSalud)));
        mTamizaje.setAsisteCSSF(cursor.getString(cursor.getColumnIndex(MainDBConstants.asisteCSSF)).charAt(0));
        mTamizaje.setOtroCentroSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.otroCentroSalud)));
        mTamizaje.setPuestoSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.puestoSalud)));
        mTamizaje.setSiEnfermaSoloAsistirCSSF(cursor.getString(cursor.getColumnIndex(MainDBConstants.siEnfermaSoloAsistirCSSF)).charAt(0));
        mTamizaje.setTomaPuntoGPSCasa(cursor.getString(cursor.getColumnIndex(MainDBConstants.tomaPuntoGPSCasa)).charAt(0));
        mTamizaje.setRazonNoGeoreferenciacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonNoGeoreferenciacion)));
        mTamizaje.setOtraRazonNoGeoreferenciacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonNoGeoreferenciacion)));

        return mTamizaje;
    }
}
