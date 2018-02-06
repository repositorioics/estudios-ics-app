package ni.org.ics.estudios.appmovil.helpers.seroprevalencia;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaParticipanteSA;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.SeroprevalenciaDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/25/2017.
 * V1.0
 */
public class EncuestaParticipanteSAHelper {

    public static ContentValues crearEncuestaParticipanteSAContentValues(EncuestaParticipanteSA encuesta){
        ContentValues cv = new ContentValues();

        cv.put(SeroprevalenciaDBConstants.codigo, encuesta.getCodigo());
        cv.put(SeroprevalenciaDBConstants.participante, encuesta.getParticipante().getCodigo());
        cv.put(SeroprevalenciaDBConstants.escuchadoZikaSn, encuesta.getEscuchadoZikaSn());
        cv.put(SeroprevalenciaDBConstants.queEsSika, encuesta.getQueEsSika());
        cv.put(SeroprevalenciaDBConstants.otroQueEsSika, encuesta.getOtroQueEsSika());
        cv.put(SeroprevalenciaDBConstants.transmiteZika, encuesta.getTransmiteZika());
        cv.put(SeroprevalenciaDBConstants.otroTransmiteZika, encuesta.getOtroTransmiteZika());
        cv.put(SeroprevalenciaDBConstants.sintomas, encuesta.getSintomas());
        cv.put(SeroprevalenciaDBConstants.tenidoZikaSn, encuesta.getTenidoZikaSn());
        cv.put(SeroprevalenciaDBConstants.fechaZika, encuesta.getFechaZika());
        cv.put(SeroprevalenciaDBConstants.sintomasZika, encuesta.getSintomasZika());
        cv.put(SeroprevalenciaDBConstants.zikaConfirmadoMedico, encuesta.getZikaConfirmadoMedico());
        cv.put(SeroprevalenciaDBConstants.tenidoDengueSn, encuesta.getTenidoDengueSn());
        cv.put(SeroprevalenciaDBConstants.fechaDengue, encuesta.getFechaDengue());
        cv.put(SeroprevalenciaDBConstants.dengueConfirmadoMedico, encuesta.getDengueConfirmadoMedico());
        cv.put(SeroprevalenciaDBConstants.tenidoChikSn, encuesta.getTenidoChikSn());
        cv.put(SeroprevalenciaDBConstants.fechaChik, encuesta.getFechaChik());
        cv.put(SeroprevalenciaDBConstants.chikConfirmadoMedico, encuesta.getChikConfirmadoMedico());
        cv.put(SeroprevalenciaDBConstants.vacunaFiebreAmarillaSn, encuesta.getVacunaFiebreAmarillaSn());
        cv.put(SeroprevalenciaDBConstants.fechaVacunaFiebreAmar, encuesta.getFechaVacunaFiebreAmar());
        cv.put(SeroprevalenciaDBConstants.transfusionSangreSn, encuesta.getTransfusionSangreSn());
        cv.put(SeroprevalenciaDBConstants.fechaTransfusionSangre, encuesta.getFechaTransfusionSangre());
        cv.put(SeroprevalenciaDBConstants.usaRepelentes, encuesta.getUsaRepelentes());
        cv.put(SeroprevalenciaDBConstants.conoceLarvas, encuesta.getConoceLarvas());
        cv.put(SeroprevalenciaDBConstants.lugaresLarvas, encuesta.getLugaresLarvas());
        cv.put(SeroprevalenciaDBConstants.otrosLugaresLarvas, encuesta.getOtrosLugaresLarvas());
        cv.put(SeroprevalenciaDBConstants.tenidoHijos, encuesta.getTenidoHijos());
        cv.put(SeroprevalenciaDBConstants.usaPlanificacionFam, encuesta.getUsaPlanificacionFam());
        cv.put(SeroprevalenciaDBConstants.usaCondon, encuesta.getUsaCondon());
        cv.put(SeroprevalenciaDBConstants.usaOtroMetodo, encuesta.getUsaOtroMetodo());
        //MA 2018
        cv.put(SeroprevalenciaDBConstants.sabeZika, encuesta.getSabeZika());
        cv.put(SeroprevalenciaDBConstants.usaRopa, encuesta.getUsaRopa());
        cv.put(SeroprevalenciaDBConstants.embarazadaUltAnio, encuesta.getEmbarazadaUltAnio());
        cv.put(SeroprevalenciaDBConstants.visitaCemeneterio, encuesta.getVisitaCemeneterio());
        cv.put(SeroprevalenciaDBConstants.cadaCuantoVisitaCem, encuesta.getCadaCuantoVisitaCem());
        cv.put(SeroprevalenciaDBConstants.mesesVisitaCementerio, encuesta.getMesesVisitaCementerio());
        cv.put(SeroprevalenciaDBConstants.descOtroMetodo, encuesta.getDescOtroMetodo());

        if (encuesta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, encuesta.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, encuesta.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(encuesta.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));
        cv.put(MainDBConstants.deviceId, encuesta.getDeviceid());

        return cv;
    }

    public static EncuestaParticipanteSA crearEncuestaParticipanteSA(Cursor cursor){
        EncuestaParticipanteSA mEncuesta = new EncuestaParticipanteSA();

        mEncuesta.setCodigo(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.codigo)));
        mEncuesta.setParticipante(null);
        mEncuesta.setEscuchadoZikaSn(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.escuchadoZikaSn)));
        mEncuesta.setQueEsSika(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.queEsSika)));
        mEncuesta.setOtroQueEsSika(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.otroQueEsSika)));
        mEncuesta.setTransmiteZika(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.transmiteZika)));
        mEncuesta.setOtroTransmiteZika(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.otroTransmiteZika)));
        mEncuesta.setSintomas(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.sintomas)));
        mEncuesta.setTenidoZikaSn(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.tenidoZikaSn)));
        mEncuesta.setFechaZika(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.fechaZika)));
        mEncuesta.setSintomasZika(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.sintomasZika)));
        mEncuesta.setZikaConfirmadoMedico(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.zikaConfirmadoMedico)));
        mEncuesta.setTenidoDengueSn(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.tenidoDengueSn)));
        mEncuesta.setFechaDengue(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.fechaDengue)));
        mEncuesta.setDengueConfirmadoMedico(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.dengueConfirmadoMedico)));
        mEncuesta.setTenidoChikSn(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.tenidoChikSn)));
        mEncuesta.setFechaChik(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.fechaChik)));
        mEncuesta.setChikConfirmadoMedico(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.chikConfirmadoMedico)));
        mEncuesta.setVacunaFiebreAmarillaSn(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.vacunaFiebreAmarillaSn)));
        mEncuesta.setFechaVacunaFiebreAmar(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.fechaVacunaFiebreAmar)));
        mEncuesta.setTransfusionSangreSn(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.transfusionSangreSn)));
        mEncuesta.setFechaTransfusionSangre(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.fechaTransfusionSangre)));
        mEncuesta.setUsaRepelentes(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.usaRepelentes)));
        mEncuesta.setConoceLarvas(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.conoceLarvas)));
        mEncuesta.setLugaresLarvas(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.lugaresLarvas)));
        mEncuesta.setOtrosLugaresLarvas(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.otrosLugaresLarvas)));
        mEncuesta.setTenidoHijos(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.tenidoHijos)));
        mEncuesta.setUsaPlanificacionFam(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.usaPlanificacionFam)));
        mEncuesta.setUsaCondon(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.usaCondon)));
        mEncuesta.setUsaOtroMetodo(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.usaOtroMetodo)));
        //MA 2018
        mEncuesta.setSabeZika(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.sabeZika)));
        mEncuesta.setUsaRopa(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.usaRopa)));
        mEncuesta.setEmbarazadaUltAnio(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.embarazadaUltAnio)));
        mEncuesta.setVisitaCemeneterio(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.visitaCemeneterio)));
        mEncuesta.setCadaCuantoVisitaCem(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.cadaCuantoVisitaCem)));
        mEncuesta.setMesesVisitaCementerio(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.mesesVisitaCementerio)));
        mEncuesta.setDescOtroMetodo(cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.descOtroMetodo)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mEncuesta.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mEncuesta.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mEncuesta.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mEncuesta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mEncuesta.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mEncuesta;
    }
}
