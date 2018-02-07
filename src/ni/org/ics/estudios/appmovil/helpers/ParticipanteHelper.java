package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/9/2017.
 * V1.0
 */
public class ParticipanteHelper {

    public static ContentValues crearParticipanteContentValues(Participante participante){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, participante.getCodigo());
        cv.put(MainDBConstants.nombre1, participante.getNombre1());
        cv.put(MainDBConstants.nombre2, participante.getNombre2());
        cv.put(MainDBConstants.apellido1, participante.getApellido1());
        cv.put(MainDBConstants.apellido2, participante.getApellido2());
        cv.put(MainDBConstants.sexo, participante.getSexo());
        if (participante.getFechaNac()!=null) cv.put(MainDBConstants.fechaNac, participante.getFechaNac().getTime());
        cv.put(MainDBConstants.nombre1Padre, participante.getNombre1Padre());
        cv.put(MainDBConstants.nombre2Padre, participante.getNombre2Padre());
        cv.put(MainDBConstants.apellido1Padre, participante.getApellido1Padre());
        cv.put(MainDBConstants.apellido2Padre, participante.getApellido2Padre());
        cv.put(MainDBConstants.nombre1Madre, participante.getNombre1Madre());
        cv.put(MainDBConstants.nombre2Madre, participante.getNombre2Madre());
        cv.put(MainDBConstants.apellido1Madre, participante.getApellido1Madre());
        cv.put(MainDBConstants.apellido2Madre, participante.getApellido2Madre());
        if (participante.getCasa() != null) cv.put(MainDBConstants.casa, participante.getCasa().getCodigo());
        if (participante.getRecordDate() != null) cv.put(MainDBConstants.recordDate, participante.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, participante.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(participante.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(participante.getEstado()));
        cv.put(MainDBConstants.deviceId, participante.getDeviceid());
        return cv;
    }

    public static Participante crearParticipante(Cursor cursor){
        Participante mParticipante = new Participante();
        mParticipante.setCodigo(cursor.getInt(cursor.getColumnIndex(MainDBConstants.codigo)));
        mParticipante.setNombre1(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1)));
        mParticipante.setNombre2(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2)));
        mParticipante.setApellido1(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1)));
        mParticipante.setApellido2(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2)));
        mParticipante.setSexo(cursor.getString(cursor.getColumnIndex(MainDBConstants.sexo)));
        mParticipante.setFechaNac(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaNac))));
        mParticipante.setNombre1Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1Padre)));
        mParticipante.setNombre2Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2Padre)));
        mParticipante.setApellido1Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1Padre)));
        mParticipante.setApellido2Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2Padre)));
        mParticipante.setNombre1Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1Madre)));
        mParticipante.setNombre2Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2Madre)));
        mParticipante.setApellido1Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1Madre)));
        mParticipante.setApellido2Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2Madre)));
        mParticipante.setCasa(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mParticipante.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mParticipante.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mParticipante.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mParticipante.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mParticipante.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mParticipante;
    }


    /**
     * Crea un participante
     *
     * @return Participante
     */
    public static ParticipanteProcesos crearParticipanteProcesos(Cursor participantes){
        ParticipanteProcesos mPart = new ParticipanteProcesos();
        Date fecha = new Date(participantes.getLong(participantes.getColumnIndex(ConstantsDB.TODAY)));
        mPart.setCodigo(participantes.getInt(participantes.getColumnIndex(ConstantsDB.CODIGO)));
        mPart.setReConsDeng(participantes.getString(participantes.getColumnIndex(ConstantsDB.RECONSDENG)));
        mPart.setConPto(participantes.getString(participantes.getColumnIndex(ConstantsDB.CONPTO)));
        mPart.setEstPart(participantes.getInt(participantes.getColumnIndex(ConstantsDB.ESTADO_PAR)));
        mPart.setEstudio(participantes.getString(participantes.getColumnIndex(ConstantsDB.ESTUDIO)));
        mPart.setPbmc(participantes.getString(participantes.getColumnIndex(ConstantsDB.PBMC)));
        mPart.setConsDeng(participantes.getString(participantes.getColumnIndex(ConstantsDB.CONSDENG)));
        mPart.setZika(participantes.getString(participantes.getColumnIndex(ConstantsDB.ZIKA)));
        mPart.setAdn(participantes.getString(participantes.getColumnIndex(ConstantsDB.ADN)));
        mPart.setConsFlu(participantes.getString(participantes.getColumnIndex(ConstantsDB.CONSFLU)));
        mPart.setConsChik(participantes.getString(participantes.getColumnIndex(ConstantsDB.CONSCHIK)));
        mPart.setConmx(participantes.getString(participantes.getColumnIndex(ConstantsDB.MUESTRA)));
        mPart.setConmxbhc(participantes.getString(participantes.getColumnIndex(ConstantsDB.MUESTRABHC)));
        mPart.setEncLacMat(participantes.getString(participantes.getColumnIndex(ConstantsDB.LACT_MAT)));
        mPart.setPesoTalla(participantes.getString(participantes.getColumnIndex(ConstantsDB.PESOTALLA)));
        mPart.setEncPart(participantes.getString(participantes.getColumnIndex(ConstantsDB.ENC_PAR)));
        mPart.setEnCasa(participantes.getString(participantes.getColumnIndex(ConstantsDB.ENC_CASA)));
        mPart.setObsequio(participantes.getString(participantes.getColumnIndex(ConstantsDB.OBSEQUIO)));
        mPart.setConvalesciente(participantes.getString(participantes.getColumnIndex(ConstantsDB.CONVAL)));
        mPart.setInfoVacuna(participantes.getString(participantes.getColumnIndex(ConstantsDB.INFOVAC)));
        mPart.setRelacionFam(participantes.getInt(participantes.getColumnIndex(ConstantsDB.RELFAMT)));
        mPart.setPaxgene(participantes.getString(participantes.getColumnIndex(ConstantsDB.PAXGENE)));
        mPart.setRetoma(participantes.getString(participantes.getColumnIndex(ConstantsDB.RETOMA)));
        mPart.setVolRetoma(participantes.getDouble(participantes.getColumnIndex(ConstantsDB.VOLRETOMA)));
        mPart.setCuantasPers(participantes.getInt(participantes.getColumnIndex(ConstantsDB.NUMPERS)));
        mPart.setPosZika(participantes.getString(participantes.getColumnIndex(ConstantsDB.posZika)));
        mPart.setDatosParto(participantes.getString(participantes.getColumnIndex(ConstantsDB.datosParto)));
        mPart.setDatosVisita(participantes.getString(participantes.getColumnIndex(ConstantsDB.datosVisita)));
        mPart.setMi(participantes.getString(participantes.getColumnIndex(ConstantsDB.mi)));
        mPart.setCasaCHF(participantes.getString(participantes.getColumnIndex(ConstantsDB.casaCHF)));
        mPart.setEnCasaChf(participantes.getString(participantes.getColumnIndex(ConstantsDB.enCasaChf)));
        mPart.setEnCasaSa(participantes.getString(participantes.getColumnIndex(ConstantsDB.enCasaSa)));
        mPart.setEncPartSa(participantes.getString(participantes.getColumnIndex(ConstantsDB.encPartSa)));
        mPart.setTutor(participantes.getString(participantes.getColumnIndex(ConstantsDB.tutor)));

        Boolean borrado = participantes.getInt(participantes.getColumnIndex(ConstantsDB.DELETED))>0;
		mPart.setMovilInfo(new MovilInfo(participantes.getInt(participantes.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
				participantes.getString(participantes.getColumnIndex(ConstantsDB.FILE_PATH)),
				participantes.getString(participantes.getColumnIndex(ConstantsDB.STATUS)),
				participantes.getString(participantes.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
				participantes.getString(participantes.getColumnIndex(ConstantsDB.START)),
				participantes.getString(participantes.getColumnIndex(ConstantsDB.END)),
				participantes.getString(participantes.getColumnIndex(ConstantsDB.DEVICE_ID)),
				participantes.getString(participantes.getColumnIndex(ConstantsDB.SIM_SERIAL)),
				participantes.getString(participantes.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
				fecha,
				participantes.getString(participantes.getColumnIndex(ConstantsDB.USUARIO)),
				borrado,
				participantes.getInt(participantes.getColumnIndex(ConstantsDB.REC1)),
				participantes.getInt(participantes.getColumnIndex(ConstantsDB.REC2))));
        return mPart;
    }

    /**
     * Inserta un participantes_procesos en la base de datos
     *
     * @param participante
     *            Objeto Participantes_procesos que contiene la informacion
     *
     */
    public static ContentValues crearParticipanteProcesos(ParticipanteProcesos participante) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CODIGO, participante.getCodigo());
        cv.put(ConstantsDB.ESTADO_PAR, participante.getEstPart());
        cv.put(ConstantsDB.RECONSDENG, participante.getReConsDeng());
        cv.put(ConstantsDB.CONPTO, participante.getConPto());
        cv.put(ConstantsDB.ESTUDIO, participante.getEstudio());
        cv.put(ConstantsDB.PBMC, participante.getPbmc());
        cv.put(ConstantsDB.CONSDENG, participante.getConsDeng());
        cv.put(ConstantsDB.ZIKA, participante.getZika());
        cv.put(ConstantsDB.ADN, participante.getAdn());
        cv.put(ConstantsDB.CONSFLU, participante.getConsFlu());
        cv.put(ConstantsDB.CONSCHIK, participante.getConsChik());
        cv.put(ConstantsDB.MUESTRA, participante.getConmx());
        cv.put(ConstantsDB.MUESTRABHC, participante.getConmxbhc());
        cv.put(ConstantsDB.LACT_MAT, participante.getEncLacMat());
        cv.put(ConstantsDB.PESOTALLA, participante.getPesoTalla());
        cv.put(ConstantsDB.ENC_PAR, participante.getEncPart());
        cv.put(ConstantsDB.ENC_CASA, participante.getEnCasa());
        cv.put(ConstantsDB.OBSEQUIO, participante.getObsequio());
        cv.put(ConstantsDB.CONVAL, participante.getConvalesciente());
        cv.put(ConstantsDB.INFOVAC, participante.getInfoVacuna());
        cv.put(ConstantsDB.RELFAMT, participante.getRelacionFam());
        cv.put(ConstantsDB.PAXGENE, participante.getPaxgene());
        cv.put(ConstantsDB.RETOMA, participante.getRetoma());
        cv.put(ConstantsDB.VOLRETOMA, participante.getVolRetoma());
        cv.put(ConstantsDB.NUMPERS, participante.getCuantasPers());
        cv.put(ConstantsDB.posZika, participante.getPosZika());
        cv.put(ConstantsDB.datosParto, participante.getDatosParto());
        cv.put(ConstantsDB.datosVisita, participante.getDatosVisita());
        cv.put(ConstantsDB.mi, participante.getMi());
        cv.put(ConstantsDB.casaCHF, participante.getCasaCHF());
        cv.put(ConstantsDB.enCasaChf, participante.getEnCasaChf());
        cv.put(ConstantsDB.enCasaSa, participante.getEnCasaSa());
        cv.put(ConstantsDB.encPartSa, participante.getEncPartSa());
        cv.put(ConstantsDB.tutor, participante.getTutor());

        cv.put(ConstantsDB.ID_INSTANCIA, participante.getMovilInfo().getIdInstancia());
        cv.put(ConstantsDB.FILE_PATH, participante.getMovilInfo().getInstancePath());
        cv.put(ConstantsDB.STATUS, participante.getMovilInfo().getEstado());
        cv.put(ConstantsDB.WHEN_UPDATED, participante.getMovilInfo().getUltimoCambio());
        cv.put(ConstantsDB.START, participante.getMovilInfo().getStart());
        cv.put(ConstantsDB.END, participante.getMovilInfo().getEnd());
        cv.put(ConstantsDB.DEVICE_ID, participante.getMovilInfo().getDeviceid());
        cv.put(ConstantsDB.SIM_SERIAL, participante.getMovilInfo().getSimserial());
        cv.put(ConstantsDB.PHONE_NUMBER, participante.getMovilInfo().getPhonenumber());
        cv.put(ConstantsDB.TODAY, participante.getMovilInfo().getToday().getTime());
        cv.put(ConstantsDB.USUARIO, participante.getMovilInfo().getUsername());
        cv.put(ConstantsDB.DELETED, participante.getMovilInfo().getEliminado());
        cv.put(ConstantsDB.REC1, participante.getMovilInfo().getRecurso1());
        cv.put(ConstantsDB.REC2, participante.getMovilInfo().getRecurso2());
        return cv;
    }
}
