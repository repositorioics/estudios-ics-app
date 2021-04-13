package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.domain.ContactoParticipante;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.Date;
import java.util.List;

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
        if (mPart.getEstudio() == null) mPart.setEstudio("");
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
        mPart.setObsequioChf(participantes.getString(participantes.getColumnIndex(ConstantsDB.OBSEQUIOCHF)));
        mPart.setConvalesciente(participantes.getString(participantes.getColumnIndex(ConstantsDB.CONVAL)));
        mPart.setInfoVacuna(participantes.getString(participantes.getColumnIndex(ConstantsDB.INFOVAC)));
        mPart.setRelacionFam(participantes.getInt(participantes.getColumnIndex(ConstantsDB.RELFAMT)));
        mPart.setPaxgene(participantes.getString(participantes.getColumnIndex(ConstantsDB.PAXGENE)));
        mPart.setRetoma(participantes.getString(participantes.getColumnIndex(ConstantsDB.RETOMA)));
        if (participantes.getDouble(participantes.getColumnIndex(ConstantsDB.VOLRETOMA))>0) mPart.setVolRetoma(participantes.getDouble(participantes.getColumnIndex(ConstantsDB.VOLRETOMA)));
        if (participantes.getDouble(participantes.getColumnIndex(ConstantsDB.VOLRETOMAPBMC))>0) mPart.setVolRetomaPbmc(participantes.getDouble(participantes.getColumnIndex(ConstantsDB.VOLRETOMAPBMC)));
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
        mPart.setCoordenadas(participantes.getString(participantes.getColumnIndex(ConstantsDB.coordenada)));
        mPart.setConsSa(participantes.getString(participantes.getColumnIndex(ConstantsDB.consSa)));
        mPart.setcDatosParto(participantes.getString(participantes.getColumnIndex(ConstantsDB.cDatosParto)));
        mPart.setReConsChf18(participantes.getString(participantes.getColumnIndex(ConstantsDB.reConsChf18)));
        mPart.setPosDengue(participantes.getString(participantes.getColumnIndex(ConstantsDB.posDengue)));
        mPart.setMxSuperficie(participantes.getString(participantes.getColumnIndex(ConstantsDB.mxSuperficie)));
        //MA2020
        mPart.setMostrarAlfabeto(participantes.getString(participantes.getColumnIndex(ConstantsDB.mostrarAlfabeto)));
        mPart.setMostrarMadreAlfabeta(participantes.getString(participantes.getColumnIndex(ConstantsDB.mostrarMadreAlfabeta)));
        mPart.setMostrarPadreAlfabeto(participantes.getString(participantes.getColumnIndex(ConstantsDB.mostrarPadreAlfabeto)));
        mPart.setMostrarNumParto(participantes.getString(participantes.getColumnIndex(ConstantsDB.mostrarNumParto)));
        mPart.setAntecedenteTutorCP(participantes.getString(participantes.getColumnIndex(ConstantsDB.antecedenteTutorCP)));
        //Covid19
        mPart.setConsCovid19(participantes.getString(participantes.getColumnIndex(ConstantsDB.consCovid19)));
        mPart.setSubEstudios(participantes.getString(participantes.getColumnIndex(ConstantsDB.subEstudios)));
        //Parte E CHF para toma mx adicional Covid19
        mPart.setConsChf(participantes.getString(participantes.getColumnIndex(ConstantsDB.consChf)));
        mPart.setCuestCovid(participantes.getString(participantes.getColumnIndex(ConstantsDB.cuestCovid)));
        mPart.setMuestraCovid(participantes.getString(participantes.getColumnIndex(ConstantsDB.muestraCovid)));
        //Mostrar si es positivo Covid19
        mPart.setPosCovid(participantes.getString(participantes.getColumnIndex(ConstantsDB.posCovid)));
        //ParteE Dengue
        mPart.setConsDenParteE(participantes.getString(participantes.getColumnIndex(ConstantsDB.consDenParteE)));
        mPart.setMxDenParteE(participantes.getString(participantes.getColumnIndex(ConstantsDB.mxDenParteE)));

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
        cv.put(ConstantsDB.OBSEQUIOCHF, participante.getObsequioChf());
        cv.put(ConstantsDB.CONVAL, participante.getConvalesciente());
        cv.put(ConstantsDB.INFOVAC, participante.getInfoVacuna());
        cv.put(ConstantsDB.RELFAMT, participante.getRelacionFam());
        cv.put(ConstantsDB.PAXGENE, participante.getPaxgene());
        cv.put(ConstantsDB.RETOMA, participante.getRetoma());
        if (participante.getVolRetoma()!=null) cv.put(ConstantsDB.VOLRETOMA, participante.getVolRetoma());
        if (participante.getVolRetomaPbmc()!=null) cv.put(ConstantsDB.VOLRETOMAPBMC, participante.getVolRetomaPbmc());
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
        cv.put(ConstantsDB.coordenada, participante.getCoordenadas());
        cv.put(ConstantsDB.consSa, participante.getConsSa());
        cv.put(ConstantsDB.cDatosParto, participante.getcDatosParto());
        cv.put(ConstantsDB.reConsChf18, participante.getReConsChf18());
        cv.put(ConstantsDB.posDengue, participante.getPosDengue());
        cv.put(ConstantsDB.mxSuperficie, participante.getMxSuperficie());
        //MA2020
        cv.put(ConstantsDB.mostrarAlfabeto, participante.getMostrarAlfabeto());
        cv.put(ConstantsDB.mostrarMadreAlfabeta, participante.getMostrarMadreAlfabeta());
        cv.put(ConstantsDB.mostrarPadreAlfabeto, participante.getMostrarPadreAlfabeto());
        cv.put(ConstantsDB.mostrarNumParto, participante.getMostrarNumParto());
        cv.put(ConstantsDB.antecedenteTutorCP, participante.getAntecedenteTutorCP());
        //Covid19
        cv.put(ConstantsDB.consCovid19, participante.getConsCovid19());
        cv.put(ConstantsDB.subEstudios, participante.getSubEstudios());
        //Parte E CHF para toma mx adicional Covid19
        cv.put(ConstantsDB.consChf, participante.getConsChf());
        cv.put(ConstantsDB.cuestCovid, participante.getCuestCovid());
        cv.put(ConstantsDB.muestraCovid, participante.getMuestraCovid());
        //Mostrar si es positivo Covid19
        cv.put(ConstantsDB.posCovid, participante.getPosCovid());
        //Parte E Dengue
        cv.put(ConstantsDB.consDenParteE, participante.getConsDenParteE());
        cv.put(ConstantsDB.mxDenParteE, participante.getMxDenParteE());

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

    public static ContentValues crearContactoParticipanteContentValues(ContactoParticipante contactoParticipante){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.id, contactoParticipante.getId());
        cv.put(MainDBConstants.nombre1, contactoParticipante.getNombre());
        cv.put(MainDBConstants.direccion, contactoParticipante.getDireccion());
        if (contactoParticipante.getBarrio() != null) cv.put(MainDBConstants.barrio, contactoParticipante.getBarrio().getCodigo());
        cv.put(MainDBConstants.numero1, contactoParticipante.getNumero1());
        cv.put(MainDBConstants.numero2, contactoParticipante.getNumero2());
        cv.put(MainDBConstants.operadora1, contactoParticipante.getOperadora1());
        cv.put(MainDBConstants.operadora2, contactoParticipante.getOperadora2());
        cv.put(MainDBConstants.tipo1, contactoParticipante.getTipo1());
        cv.put(MainDBConstants.tipo2, contactoParticipante.getTipo2());
        cv.put(MainDBConstants.numero3, contactoParticipante.getNumero3());
        cv.put(MainDBConstants.operadora3, contactoParticipante.getOperadora3());
        cv.put(MainDBConstants.tipo3, contactoParticipante.getTipo3());
        cv.put(MainDBConstants.esPropio, contactoParticipante.getEsPropio());
        cv.put(MainDBConstants.otroBarrio, contactoParticipante.getOtroBarrio());
        if (contactoParticipante.getParticipante() != null) cv.put(MainDBConstants.participante, contactoParticipante.getParticipante().getCodigo());
        if (contactoParticipante.getRecordDate() != null) cv.put(MainDBConstants.recordDate, contactoParticipante.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, contactoParticipante.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(contactoParticipante.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(contactoParticipante.getEstado()));
        cv.put(MainDBConstants.deviceId, contactoParticipante.getDeviceid());
        return cv;
    }

    public static ContactoParticipante crearContactoParticipante(Cursor cursor){
        ContactoParticipante mContacto = new ContactoParticipante();
        mContacto.setId(cursor.getString(cursor.getColumnIndex(MainDBConstants.id)));
        mContacto.setNombre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1)));
        mContacto.setDireccion(cursor.getString(cursor.getColumnIndex(MainDBConstants.direccion)));
        mContacto.setBarrio(null);
        mContacto.setNumero1(cursor.getString(cursor.getColumnIndex(MainDBConstants.numero1)));
        mContacto.setNumero2(cursor.getString(cursor.getColumnIndex(MainDBConstants.numero2)));
        mContacto.setOperadora1(cursor.getString(cursor.getColumnIndex(MainDBConstants.operadora1)));
        mContacto.setOperadora2(cursor.getString(cursor.getColumnIndex(MainDBConstants.operadora2)));
        mContacto.setTipo1(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo1)));
        mContacto.setTipo2(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo2)));
        mContacto.setNumero3(cursor.getString(cursor.getColumnIndex(MainDBConstants.numero3)));
        mContacto.setOperadora3(cursor.getString(cursor.getColumnIndex(MainDBConstants.operadora3)));
        mContacto.setTipo3(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipo3)));
        mContacto.setEsPropio(cursor.getString(cursor.getColumnIndex(MainDBConstants.esPropio)));
        mContacto.setOtroBarrio(cursor.getString(cursor.getColumnIndex(MainDBConstants.otroBarrio)));
        mContacto.setParticipante(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mContacto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mContacto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mContacto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mContacto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mContacto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mContacto;
    }
    
    public static void fillParticipanteStatement(SQLiteStatement stat, Participante participante){
        stat.bindLong(1, participante.getCodigo());
        bindString(stat,2, participante.getNombre1());
        bindString(stat, 3, participante.getNombre2()); //if (participante.getNombre2() != null)	stat.bindString(3, participante.getNombre2());
        bindString(stat,4, participante.getApellido1());
        bindString(stat, 5, participante.getApellido2());//if (participante.getApellido2() != null) stat.bindString(5, participante.getApellido2());
        bindString(stat,6, participante.getSexo());
        bindDate(stat,7, participante.getFechaNac());
        bindString(stat,8, participante.getNombre1Padre());
        bindString(stat,9, participante.getNombre2Padre());
        bindString(stat,10, participante.getApellido1Padre());
        bindString(stat,11, participante.getApellido2Padre());
        bindString(stat,12, participante.getNombre1Madre());
        bindString(stat,13, participante.getNombre2Madre());
        bindString(stat,14, participante.getApellido1Madre());
        bindString(stat,15, participante.getApellido2Madre());
        stat.bindLong(16, participante.getCasa().getCodigo());
        stat.bindLong(17, participante.getRecordDate().getTime());
        bindString(stat,18, participante.getRecordUser());
        stat.bindString(19, String.valueOf(participante.getPasive()));
        bindString(stat,20, participante.getDeviceid());
        stat.bindString(21, String.valueOf(participante.getEstado()));
    }

    public static void fillParticipanteProcesosStatement(SQLiteStatement stat, ParticipanteProcesos participante) {
        stat.bindLong(1, participante.getCodigo());
        if ( participante.getEstPart() != null ) stat.bindLong(2, participante.getEstPart()); else stat.bindNull(2);
        if ( participante.getReConsDeng() != null ) stat.bindString(3, participante.getReConsDeng()); else stat.bindNull(3);
        if ( participante.getConPto() != null ) stat.bindString(4, participante.getConPto()); else stat.bindNull(4);
        if ( participante.getEstudio() != null ) stat.bindString(5, participante.getEstudio()); else stat.bindNull(5);
        if ( participante.getPbmc() != null ) stat.bindString(6, participante.getPbmc()); else stat.bindNull(6);
        if ( participante.getConsDeng() != null ) stat.bindString(7, participante.getConsDeng()); else stat.bindNull(7);
        if ( participante.getConsFlu() != null ) stat.bindString(8, participante.getConsFlu()); else stat.bindNull(8);
        if ( participante.getConsChik() != null ) stat.bindString(9, participante.getConsChik()); else stat.bindNull(9);
        if ( participante.getConmx() != null ) stat.bindString(10, participante.getConmx()); else stat.bindNull(10);
        if ( participante.getConmxbhc() != null ) stat.bindString(11, participante.getConmxbhc()); else stat.bindNull(11);
        if ( participante.getEncLacMat() != null ) stat.bindString(12, participante.getEncLacMat()); else stat.bindNull(12);
        if ( participante.getPesoTalla() != null ) stat.bindString(13, participante.getPesoTalla()); else stat.bindNull(13);
        if ( participante.getEncPart() != null ) stat.bindString(14, participante.getEncPart()); else stat.bindNull(14);
        if ( participante.getEnCasa() != null ) stat.bindString(15, participante.getEnCasa()); else stat.bindNull(15);
        if ( participante.getObsequio() != null ) stat.bindString(16, participante.getObsequio()); else stat.bindNull(16);
        if ( participante.getConvalesciente() != null ) stat.bindString(17, participante.getConvalesciente()); else stat.bindNull(17);
        if ( participante.getInfoVacuna() != null ) stat.bindString(18, participante.getInfoVacuna()); else stat.bindNull(18);
        if ( participante.getPaxgene() != null ) stat.bindString(19, participante.getPaxgene()); else stat.bindNull(19);
        if ( participante.getRetoma() != null ) stat.bindString(20, participante.getRetoma()); else stat.bindNull(20);
        if ( participante.getVolRetoma()!=null ) stat.bindDouble(21, participante.getVolRetoma()); else stat.bindNull(21);
        if ( participante.getVolRetomaPbmc()!=null ) stat.bindDouble(22, participante.getVolRetomaPbmc()); else stat.bindNull(22);
        if ( participante.getZika() != null ) stat.bindString(23, participante.getZika()); else stat.bindNull(23);
        if ( participante.getAdn() != null ) stat.bindString(24, participante.getAdn()); else stat.bindNull(24);
        if ( participante.getRelacionFam() != null ) stat.bindLong(25, participante.getRelacionFam()); else stat.bindNull(25);
        if ( participante.getCuantasPers() != null ) stat.bindLong(26, participante.getCuantasPers()); else stat.bindNull(26);
        if ( participante.getDatosParto() != null ) stat.bindString(27, participante.getDatosParto()); else stat.bindNull(27);
        if ( participante.getPosZika() != null ) stat.bindString(28, participante.getPosZika()); else stat.bindNull(28);
        if ( participante.getDatosVisita() != null ) stat.bindString(29, participante.getDatosVisita()); else stat.bindNull(29);
        if ( participante.getMi() != null ) stat.bindString(30, participante.getMi()); else stat.bindNull(30);
        if ( participante.getCasaCHF() != null ) stat.bindString(31, participante.getCasaCHF()); else stat.bindNull(31);
        if ( participante.getEnCasaChf() != null ) stat.bindString(32, participante.getEnCasaChf()); else stat.bindNull(32);
        if ( participante.getEnCasaSa() != null ) stat.bindString(33, participante.getEnCasaSa()); else stat.bindNull(33);
        if ( participante.getEncPartSa() != null ) stat.bindString(34, participante.getEncPartSa()); else stat.bindNull(34);
        if ( participante.getTutor() != null ) stat.bindString(35, participante.getTutor()); else stat.bindNull(35);
        if ( participante.getCoordenadas() != null ) stat.bindString(36, participante.getCoordenadas()); else stat.bindNull(36);
        if ( participante.getConsSa() != null ) stat.bindString(37, participante.getConsSa()); else stat.bindNull(37);
        if ( participante.getObsequioChf() != null ) stat.bindString(38, participante.getObsequioChf()); else stat.bindNull(38);
        if ( participante.getcDatosParto() != null ) stat.bindString(39, participante.getcDatosParto()); else stat.bindNull(39);
        if ( participante.getReConsChf18() != null ) stat.bindString(40, participante.getReConsChf18()); else stat.bindNull(40);
        if ( participante.getPosDengue() != null ) stat.bindString(41, participante.getPosDengue()); else stat.bindNull(41);
        if ( participante.getMxSuperficie() != null ) stat.bindString(42, participante.getMxSuperficie()); else stat.bindNull(42);
        if ( participante.getMostrarAlfabeto() != null ) stat.bindString(43, participante.getMostrarAlfabeto()); else stat.bindNull(43);
        if ( participante.getMostrarMadreAlfabeta() != null ) stat.bindString(44, participante.getMostrarMadreAlfabeta()); else stat.bindNull(44);
        if ( participante.getMostrarNumParto() != null ) stat.bindString(45, participante.getMostrarNumParto()); else stat.bindNull(45);
        if ( participante.getMostrarPadreAlfabeto() != null ) stat.bindString(46, participante.getMostrarPadreAlfabeto()); else stat.bindNull(46);
        if ( participante.getAntecedenteTutorCP() != null ) stat.bindString(47, participante.getAntecedenteTutorCP()); else stat.bindNull(47);
        if ( participante.getConsCovid19() != null ) stat.bindString(48, participante.getConsCovid19()); else stat.bindNull(48);
        if ( participante.getSubEstudios() != null ) stat.bindString(49, participante.getSubEstudios()); else stat.bindNull(49);
        if ( participante.getConsChf() != null ) stat.bindString(50, participante.getConsChf()); else stat.bindNull(50);
        if ( participante.getCuestCovid() != null ) stat.bindString(51, participante.getCuestCovid()); else stat.bindNull(51);
        if ( participante.getMuestraCovid() != null ) stat.bindString(52, participante.getMuestraCovid()); else stat.bindNull(52);
        if ( participante.getPosCovid() != null ) stat.bindString(53, participante.getPosCovid()); else stat.bindNull(53);
        if ( participante.getConsDenParteE() != null ) stat.bindString(54, participante.getConsDenParteE()); else stat.bindNull(54);
        if ( participante.getMxDenParteE() != null ) stat.bindString(55, participante.getMxDenParteE()); else stat.bindNull(55);
        if ( participante.getMovilInfo().getIdInstancia() != null ) stat.bindLong(56, participante.getMovilInfo().getIdInstancia()); else stat.bindNull(56);
        if ( participante.getMovilInfo().getInstancePath() != null ) stat.bindString(57, participante.getMovilInfo().getInstancePath()); else stat.bindNull(57);
        if ( participante.getMovilInfo().getEstado() != null ) stat.bindString(58, participante.getMovilInfo().getEstado()); else stat.bindNull(58);
        if ( participante.getMovilInfo().getUltimoCambio() != null ) stat.bindString(59, participante.getMovilInfo().getUltimoCambio()); else stat.bindNull(59);
        if ( participante.getMovilInfo().getStart() != null ) stat.bindString(60, participante.getMovilInfo().getStart()); else stat.bindNull(60);
        if ( participante.getMovilInfo().getEnd() != null ) stat.bindString(61, participante.getMovilInfo().getEnd()); else stat.bindNull(61);
        if ( participante.getMovilInfo().getDeviceid() != null ) stat.bindString(62, participante.getMovilInfo().getDeviceid()); else stat.bindNull(62);
        if ( participante.getMovilInfo().getSimserial() != null ) stat.bindString(63, participante.getMovilInfo().getSimserial()); else stat.bindNull(63);
        if ( participante.getMovilInfo().getPhonenumber() != null ) stat.bindString(64, participante.getMovilInfo().getPhonenumber()); else stat.bindNull(64);
        if ( participante.getMovilInfo().getToday() != null ) stat.bindLong(65, participante.getMovilInfo().getToday().getTime()); else stat.bindNull(65);
        if ( participante.getMovilInfo().getUsername() != null ) stat.bindString(66, participante.getMovilInfo().getUsername()); else stat.bindNull(66);
        if ( participante.getMovilInfo().getEliminado() != null ) stat.bindLong(67, (participante.getMovilInfo().getEliminado() ? 1 : 0)); else stat.bindNull(67);
        if ( participante.getMovilInfo().getRecurso1() != null ) stat.bindLong(68, participante.getMovilInfo().getRecurso1()); else stat.bindNull(68);
        if ( participante.getMovilInfo().getRecurso2() != null ) stat.bindLong(69, participante.getMovilInfo().getRecurso2()); else stat.bindNull(69);
    }

    public static void fillContactoParticipanteStatement(SQLiteStatement stat, ContactoParticipante contactoParticipante){
        stat.bindString(1, contactoParticipante.getId());
        bindString(stat,2, contactoParticipante.getNombre());
        bindString(stat,3, contactoParticipante.getDireccion());
        if (contactoParticipante.getBarrio() != null) stat.bindLong(4, contactoParticipante.getBarrio().getCodigo()); else stat.bindNull(4);
        bindString(stat,5, contactoParticipante.getNumero1());
        bindString(stat,6, contactoParticipante.getOperadora1());
        bindString(stat,7, contactoParticipante.getTipo1());
        bindString(stat,8, contactoParticipante.getNumero2());
        bindString(stat,9, contactoParticipante.getOperadora2());
        bindString(stat,10, contactoParticipante.getTipo2());
        bindString(stat,11, contactoParticipante.getNumero3());
        bindString(stat,12, contactoParticipante.getOperadora3());
        bindString(stat,13, contactoParticipante.getTipo3());
        bindString(stat,14, contactoParticipante.getEsPropio());
        bindString(stat,15, contactoParticipante.getOtroBarrio());
        bindInteger(stat,16, contactoParticipante.getParticipante().getCodigo());
        bindDate(stat, 17, contactoParticipante.getRecordDate());
        bindString(stat,18, contactoParticipante.getRecordUser());
        stat.bindString(19, String.valueOf(contactoParticipante.getPasive()));
        bindString(stat,20, contactoParticipante.getDeviceid());
        stat.bindString(21, String.valueOf(contactoParticipante.getEstado()));
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

    public static void bindInteger(SQLiteStatement stat, int index, Integer value){
        if (value == null) {
            stat.bindNull(index);
        } else {
            stat.bindLong(index, value);
        }
    }
}
