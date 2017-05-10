package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.CartaConsentimiento;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/9/2017.
 * V1.0
 */
public class CartaConsentimientoHelper {

    public static ContentValues crearCartaConsentimientoContentValues(CartaConsentimiento carta){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, carta.getCodigo());
        if (carta.getFechaFirma() != null)
            cv.put(MainDBConstants.fechaFirma, carta.getFechaFirma().getTime());
        cv.put(MainDBConstants.tamizaje, carta.getTamizaje().getCodigo());
        cv.put(MainDBConstants.estado, carta.getEstudio().getCodigo());
        cv.put(MainDBConstants.participadoCohortePediatrica, String.valueOf(carta.getParticipadoCohortePediatrica()));
        cv.put(MainDBConstants.cohortePediatrica, carta.getCohortePediatrica());
        cv.put(MainDBConstants.codigoReactivado, String.valueOf(carta.getCodigoReactivado()));
        cv.put(MainDBConstants.emancipado, String.valueOf(carta.getEmancipado()));
        cv.put(MainDBConstants.asentimientoVerbal, String.valueOf(carta.getAsentimientoVerbal()));
        cv.put(MainDBConstants.nombre1Tutor, carta.getNombre1Tutor());
        cv.put(MainDBConstants.nombre2Tutor, carta.getNombre2Tutor());
        cv.put(MainDBConstants.apellido1Tutor, carta.getApellido1Tutor());
        cv.put(MainDBConstants.apellido2Tutor, carta.getApellido2Tutor());
        cv.put(MainDBConstants.relacionFamiliarTutor, carta.getRelacionFamiliarTutor());
        cv.put(MainDBConstants.participanteOTutorAlfabeto, String.valueOf(carta.getParticipanteOTutorAlfabeto()));
        cv.put(MainDBConstants.testigoPresente, String.valueOf(carta.getTestigoPresente()));
        cv.put(MainDBConstants.nombre1Testigo, carta.getNombre1Testigo());
        cv.put(MainDBConstants.nombre2Testigo, carta.getNombre2Testigo());
        cv.put(MainDBConstants.apellido1Testigo, carta.getApellido1Testigo());
        cv.put(MainDBConstants.apellido2Testigo, carta.getApellido2Testigo());
        cv.put(MainDBConstants.aceptaParteA, String.valueOf(carta.getAceptaParteA()));
        cv.put(MainDBConstants.motivoRechazoParteA, carta.getMotivoRechazoParteA());
        cv.put(MainDBConstants.aceptaContactoFuturo, String.valueOf(carta.getAceptaContactoFuturo()));
        cv.put(MainDBConstants.aceptaParteB, String.valueOf(carta.getAceptaParteB()));
        cv.put(MainDBConstants.aceptaParteC, String.valueOf(carta.getAceptaParteC()));

        return cv;
    }

    public static CartaConsentimiento crearCartaConsentimiento(Cursor cursor){
        CartaConsentimiento mCarta = new CartaConsentimiento();

        mCarta.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        mCarta.setFechaFirma(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaFirma))));
        mCarta.setTamizaje(null);
        mCarta.setEstudio(null);
        mCarta.setParticipadoCohortePediatrica(cursor.getString(cursor.getColumnIndex(MainDBConstants.participadoCohortePediatrica)).charAt(0));
        mCarta.setCohortePediatrica(cursor.getString(cursor.getColumnIndex(MainDBConstants.cohortePediatrica)));
        mCarta.setCodigoReactivado(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoReactivado)).charAt(0));
        mCarta.setEmancipado(cursor.getString(cursor.getColumnIndex(MainDBConstants.emancipado)).charAt(0));
        mCarta.setAsentimientoVerbal(cursor.getString(cursor.getColumnIndex(MainDBConstants.asentimientoVerbal)).charAt(0));
        mCarta.setNombre1Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1Tutor)));
        mCarta.setNombre2Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2Tutor)));
        mCarta.setApellido1Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1Tutor)));
        mCarta.setApellido2Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2Tutor)));
        mCarta.setRelacionFamiliarTutor(null);
        mCarta.setParticipanteOTutorAlfabeto(cursor.getString(cursor.getColumnIndex(MainDBConstants.participanteOTutorAlfabeto)).charAt(0));
        mCarta.setTestigoPresente(cursor.getString(cursor.getColumnIndex(MainDBConstants.testigoPresente)).charAt(0));
        mCarta.setNombre1Testigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1Testigo)));
        mCarta.setNombre2Testigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2Testigo)));
        mCarta.setApellido1Testigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1Testigo)));
        mCarta.setApellido2Testigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2Testigo)));
        mCarta.setAceptaParteA(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaParteA)).charAt(0));
        mCarta.setMotivoRechazoParteA(cursor.getString(cursor.getColumnIndex(MainDBConstants.motivoRechazoParteA)));
        mCarta.setAceptaContactoFuturo(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaContactoFuturo)).charAt(0));
        mCarta.setAceptaParteB(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaParteB)).charAt(0));
        mCarta.setAceptaParteC(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaParteC)).charAt(0));

        return mCarta;
    }
}
