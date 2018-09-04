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
        cv.put(MainDBConstants.otraRazonNoAceptaTamizajePersona, tamizaje.getOtraRazonNoAceptaTamizajePersona());
        cv.put(MainDBConstants.criteriosInclusion, tamizaje.getCriteriosInclusion());
        cv.put(MainDBConstants.enfermedad, tamizaje.getEnfermedad());
        cv.put(MainDBConstants.dondeAsisteProblemasSalud, tamizaje.getDondeAsisteProblemasSalud());
        cv.put(MainDBConstants.otroCentroSalud, tamizaje.getOtroCentroSalud());
        cv.put(MainDBConstants.puestoSalud, tamizaje.getPuestoSalud());
        cv.put(MainDBConstants.aceptaAtenderCentro, tamizaje.getAceptaAtenderCentro());
        cv.put(MainDBConstants.esElegible, tamizaje.getEsElegible());
        cv.put(MainDBConstants.aceptaParticipar, tamizaje.getAceptaParticipar());
        cv.put(MainDBConstants.razonNoAceptaParticipar, tamizaje.getRazonNoAceptaParticipar());
        cv.put(MainDBConstants.otraRazonNoAceptaParticipar, tamizaje.getOtraRazonNoAceptaParticipar());
        cv.put(MainDBConstants.asentimientoVerbal, tamizaje.getAsentimientoVerbal());
        //nuevo ingreso MA2018
        cv.put(MainDBConstants.pretermino, tamizaje.getPretermino());
        cv.put(MainDBConstants.cohorte, tamizaje.getCohorte());
        cv.put(MainDBConstants.enfermedadInmuno, tamizaje.getEnfermedadInmuno());
        //cv.put(MainDBConstants.cualEnfermedad, tamizaje.getCualEnfermedad());
        cv.put(MainDBConstants.enfermedadCronica, tamizaje.getEnfermedadCronica());
        cv.put(MainDBConstants.tratamiento, tamizaje.getTratamiento());
        cv.put(MainDBConstants.cualTratamiento, tamizaje.getCualTratamiento());
        cv.put(MainDBConstants.diagDengue, tamizaje.getDiagDengue());
        if (tamizaje.getFechaDiagDengue() != null) cv.put(MainDBConstants.fechaDiagDengue, tamizaje.getFechaDiagDengue().getTime());
        cv.put(MainDBConstants.hospDengue, tamizaje.getHospDengue());
        if (tamizaje.getFechaHospDengue() != null) cv.put(MainDBConstants.fechaHospDengue, tamizaje.getFechaHospDengue().getTime());
        cv.put(MainDBConstants.tiempoResidencia, tamizaje.getTiempoResidencia());
        //reconsentimiento dengue 2018
        cv.put(MainDBConstants.tipoVivienda, tamizaje.getTipoVivienda());
        //cv.put(MainDBConstants.otraEnfCronica, tamizaje.getOtraEnfCronica());
        //cv.put(MainDBConstants.enfCronicaAnio, tamizaje.getEnfCronicaAnio());
        //cv.put(MainDBConstants.enfCronicaMes, tamizaje.getEnfCronicaMes());
        cv.put(MainDBConstants.otroTx, tamizaje.getOtroTx());
        cv.put(MainDBConstants.autorizaSupervisor, tamizaje.getAutorizaSupervisor());
        cv.put(MainDBConstants.emancipado, tamizaje.getEmancipado());
        cv.put(MainDBConstants.razonEmancipacion, tamizaje.getRazonEmancipacion());
        cv.put(MainDBConstants.otraRazonEmancipacion, tamizaje.getOtraRazonEmancipacion());
        if (tamizaje.getCodigoParticipanteRecon()!=null) cv.put(MainDBConstants.codigoParticipanteRecon, tamizaje.getCodigoParticipanteRecon());

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
        //Fechas menores a 1 de enero de 1970 se guardan como negativo
        if (cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaNacimiento)) != 0) mTamizaje.setFechaNacimiento(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaNacimiento))));
        mTamizaje.setAceptaTamizajePersona(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaTamizajePersona)));
        mTamizaje.setRazonNoAceptaTamizajePersona(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonNoAceptaTamizajePersona)));
        mTamizaje.setOtraRazonNoAceptaTamizajePersona(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonNoAceptaTamizajePersona)));
        mTamizaje.setCriteriosInclusion(cursor.getString(cursor.getColumnIndex(MainDBConstants.criteriosInclusion)));
        mTamizaje.setEnfermedad(cursor.getString(cursor.getColumnIndex(MainDBConstants.enfermedad)));
        mTamizaje.setDondeAsisteProblemasSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.dondeAsisteProblemasSalud)));
        mTamizaje.setOtroCentroSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.otroCentroSalud)));
        mTamizaje.setPuestoSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.puestoSalud)));
        mTamizaje.setAceptaAtenderCentro(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaAtenderCentro)));
        mTamizaje.setEsElegible(cursor.getString(cursor.getColumnIndex(MainDBConstants.esElegible)));
        mTamizaje.setAceptaParticipar(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaParticipar)));
        mTamizaje.setRazonNoAceptaParticipar(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonNoAceptaParticipar)));
        mTamizaje.setOtraRazonNoAceptaParticipar(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonNoAceptaParticipar)));
        mTamizaje.setAsentimientoVerbal(cursor.getString(cursor.getColumnIndex(MainDBConstants.asentimientoVerbal)));
        //nuevo ingreso MA2018
        mTamizaje.setPretermino(cursor.getString(cursor.getColumnIndex(MainDBConstants.pretermino)));
        mTamizaje.setCohorte(cursor.getString(cursor.getColumnIndex(MainDBConstants.cohorte)));
        mTamizaje.setEnfermedadInmuno(cursor.getString(cursor.getColumnIndex(MainDBConstants.enfermedadInmuno)));
        //mTamizaje.setCualEnfermedad(cursor.getString(cursor.getColumnIndex(MainDBConstants.cualEnfermedad)));
        mTamizaje.setEnfermedadCronica(cursor.getString(cursor.getColumnIndex(MainDBConstants.enfermedadCronica)));
        mTamizaje.setTratamiento(cursor.getString(cursor.getColumnIndex(MainDBConstants.tratamiento)));
        mTamizaje.setCualTratamiento(cursor.getString(cursor.getColumnIndex(MainDBConstants.cualTratamiento)));
        mTamizaje.setDiagDengue(cursor.getString(cursor.getColumnIndex(MainDBConstants.diagDengue)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaDiagDengue))>0) mTamizaje.setFechaDiagDengue(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaDiagDengue))));
        mTamizaje.setHospDengue(cursor.getString(cursor.getColumnIndex(MainDBConstants.hospDengue)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaHospDengue))>0) mTamizaje.setFechaHospDengue(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaHospDengue))));
        mTamizaje.setTiempoResidencia(cursor.getString(cursor.getColumnIndex(MainDBConstants.tiempoResidencia)));
        //reconsentimiento dengue 2018
        mTamizaje.setTipoVivienda(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipoVivienda)));
        //mTamizaje.setOtraEnfCronica(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraEnfCronica)));
        //mTamizaje.setEnfCronicaAnio(cursor.getString(cursor.getColumnIndex(MainDBConstants.enfCronicaAnio)));
        //mTamizaje.setEnfCronicaMes(cursor.getString(cursor.getColumnIndex(MainDBConstants.enfCronicaMes)));
        mTamizaje.setOtroTx(cursor.getString(cursor.getColumnIndex(MainDBConstants.otroTx)));
        mTamizaje.setAutorizaSupervisor(cursor.getString(cursor.getColumnIndex(MainDBConstants.autorizaSupervisor)));
        mTamizaje.setEmancipado(cursor.getString(cursor.getColumnIndex(MainDBConstants.emancipado)));
        mTamizaje.setRazonEmancipacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonEmancipacion)));
        mTamizaje.setOtraRazonEmancipacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonEmancipacion)));
        if(cursor.getInt(cursor.getColumnIndex(MainDBConstants.codigoParticipanteRecon))>0) mTamizaje.setCodigoParticipanteRecon(cursor.getInt(cursor.getColumnIndex(MainDBConstants.codigoParticipanteRecon)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mTamizaje.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mTamizaje.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mTamizaje.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mTamizaje.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mTamizaje.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mTamizaje;
    }
}
