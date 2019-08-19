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
        if (carta.getParticipante() != null) cv.put(MainDBConstants.participante, carta.getParticipante().getCodigo());
        //cv.put(MainDBConstants.emancipado, carta.getEmancipado());
        cv.put(MainDBConstants.nombre1Tutor, carta.getNombre1Tutor());
        cv.put(MainDBConstants.nombre2Tutor, carta.getNombre2Tutor());
        cv.put(MainDBConstants.apellido1Tutor, carta.getApellido1Tutor());
        cv.put(MainDBConstants.apellido2Tutor, carta.getApellido2Tutor());
        cv.put(MainDBConstants.relacionFamiliarTutor, carta.getRelacionFamiliarTutor());
        cv.put(MainDBConstants.participanteOTutorAlfabeto, carta.getParticipanteOTutorAlfabeto());
        cv.put(MainDBConstants.testigoPresente, carta.getTestigoPresente());
        cv.put(MainDBConstants.nombre1Testigo, carta.getNombre1Testigo());
        cv.put(MainDBConstants.nombre2Testigo, carta.getNombre2Testigo());
        cv.put(MainDBConstants.apellido1Testigo, carta.getApellido1Testigo());
        cv.put(MainDBConstants.apellido2Testigo, carta.getApellido2Testigo());
        cv.put(MainDBConstants.aceptaParteA, carta.getAceptaParteA());
        cv.put(MainDBConstants.motivoRechazoParteA, carta.getMotivoRechazoParteA());
        cv.put(MainDBConstants.aceptaContactoFuturo, carta.getAceptaContactoFuturo());
        cv.put(MainDBConstants.aceptaParteB, carta.getAceptaParteB());
        cv.put(MainDBConstants.aceptaParteC, carta.getAceptaParteC());
        cv.put(MainDBConstants.aceptaParteD, carta.getAceptaParteD());
        cv.put(MainDBConstants.version, carta.getVersion());
        //reconsentimiento dengue 2018
        cv.put(MainDBConstants.otroMotivoRechazoParteA, carta.getOtroMotivoRechazoParteA());
        cv.put(MainDBConstants.motivoRechazoParteDExt, carta.getMotivoRechazoParteDExt());
        cv.put(MainDBConstants.otroMotivoRechazoParteDExt, carta.getOtroMotivoRechazoParteDExt());
        cv.put(MainDBConstants.mismoTutor, carta.getMismoTutor());
        cv.put(MainDBConstants.motivoDifTutor, carta.getMotivoDifTutor());
        cv.put(MainDBConstants.otroMotivoDifTutor, carta.getOtroMotivoDifTutor());
        cv.put(MainDBConstants.otraRelacionFamTutor, carta.getOtraRelacionFamTutor());
        cv.put(MainDBConstants.verifTutor, carta.getVerifTutor());
        cv.put(MainDBConstants.reconsentimiento, carta.getReconsentimiento());
        //consentimiento muestras superficie casas
        cv.put(MainDBConstants.casaCHF, carta.getCasaChf());
        cv.put(MainDBConstants.nombre1MxSuperficie, carta.getNombre1MxSuperficie());
        cv.put(MainDBConstants.nombre2MxSuperficie, carta.getNombre2MxSuperficie());
        cv.put(MainDBConstants.apellido1MxSuperficie, carta.getApellido1MxSuperficie());
        cv.put(MainDBConstants.apellido2MxSuperficie, carta.getApellido2MxSuperficie());

        if (carta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, carta.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, carta.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(carta.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(carta.getEstado()));
        cv.put(MainDBConstants.deviceId, carta.getDeviceid());
        return cv;
    }

    public static CartaConsentimiento crearCartaConsentimiento(Cursor cursor){
        CartaConsentimiento mCarta = new CartaConsentimiento();

        mCarta.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        mCarta.setFechaFirma(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaFirma))));
        mCarta.setTamizaje(null);
        mCarta.setParticipante(null);
        //mCarta.setEmancipado(cursor.getString(cursor.getColumnIndex(MainDBConstants.emancipado)));
        mCarta.setNombre1Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1Tutor)));
        mCarta.setNombre2Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2Tutor)));
        mCarta.setApellido1Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1Tutor)));
        mCarta.setApellido2Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2Tutor)));
        mCarta.setRelacionFamiliarTutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.relacionFamiliarTutor)));
        mCarta.setParticipanteOTutorAlfabeto(cursor.getString(cursor.getColumnIndex(MainDBConstants.participanteOTutorAlfabeto)));
        mCarta.setTestigoPresente(cursor.getString(cursor.getColumnIndex(MainDBConstants.testigoPresente)));
        mCarta.setNombre1Testigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1Testigo)));
        mCarta.setNombre2Testigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2Testigo)));
        mCarta.setApellido1Testigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1Testigo)));
        mCarta.setApellido2Testigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2Testigo)));
        mCarta.setAceptaParteA(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaParteA)));
        mCarta.setMotivoRechazoParteA(cursor.getString(cursor.getColumnIndex(MainDBConstants.motivoRechazoParteA)));
        mCarta.setAceptaContactoFuturo(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaContactoFuturo)));
        mCarta.setAceptaParteB(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaParteB)));
        mCarta.setAceptaParteC(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaParteC)));
        mCarta.setAceptaParteD(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaParteD)));
        mCarta.setVersion(cursor.getString(cursor.getColumnIndex(MainDBConstants.version)));
        //reconsentimiento dengue 2018
        mCarta.setOtroMotivoRechazoParteA(cursor.getString(cursor.getColumnIndex(MainDBConstants.otroMotivoRechazoParteA)));
        mCarta.setMotivoRechazoParteDExt(cursor.getString(cursor.getColumnIndex(MainDBConstants.motivoRechazoParteDExt)));
        mCarta.setOtroMotivoRechazoParteDExt(cursor.getString(cursor.getColumnIndex(MainDBConstants.otroMotivoRechazoParteDExt)));
        mCarta.setMismoTutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.mismoTutor)));
        mCarta.setMotivoDifTutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.motivoDifTutor)));
        mCarta.setOtroMotivoDifTutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.otroMotivoDifTutor)));
        mCarta.setOtraRelacionFamTutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRelacionFamTutor)));
        mCarta.setVerifTutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.verifTutor)));
        mCarta.setReconsentimiento(cursor.getString(cursor.getColumnIndex(MainDBConstants.reconsentimiento)));
        //consentimiento muestras superficie casas
        mCarta.setCasaChf(cursor.getString(cursor.getColumnIndex(MainDBConstants.casaCHF)));
        mCarta.setNombre1MxSuperficie(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1MxSuperficie)));
        mCarta.setNombre2MxSuperficie(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2MxSuperficie)));
        mCarta.setApellido1MxSuperficie(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1MxSuperficie)));
        mCarta.setApellido2MxSuperficie(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2MxSuperficie)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mCarta.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mCarta.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mCarta.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mCarta.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mCarta.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mCarta;
    }
}
