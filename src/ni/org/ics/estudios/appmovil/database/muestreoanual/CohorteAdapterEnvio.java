package ni.org.ics.estudios.appmovil.database.muestreoanual;

/**
 * Adaptador de la base de datos Cohorte
 *
 *
 * @author William Aviles
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import ni.org.ics.estudios.appmovil.database.EstudiosSQLiteOpenHelper;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.*;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.*;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import ni.org.ics.estudios.appmovil.utils.FileUtils;

import java.util.Date;

public class CohorteAdapterEnvio {

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mContext;
    private final String mPassword;
    private final boolean mFromServer;
    private final boolean mCleanDb;

    public CohorteAdapterEnvio(Context context, String password, boolean fromServer, boolean cleanDb) {
        mContext = context;
        mPassword = password;
        mFromServer = fromServer;
        mCleanDb = cleanDb;
    }

    private static class DatabaseHelper extends EstudiosSQLiteOpenHelper {

        DatabaseHelper(Context context, String password, boolean fromServer, boolean cleanDb) {
            super(FileUtils.DATABASE_PATH, MainDBConstants.DATABASE_NAME, MainDBConstants.DATABASE_VERSION, context,
                    password, fromServer, cleanDb);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public CohorteAdapterEnvio open() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext,mPassword,mFromServer,mCleanDb);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    /**
     * Actualiza una casa en la base de datos.
     *
     * @param casa
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean updateCasasSent(Casa casa) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.estado, String.valueOf(casa.getEstado()));
        return mDb.update(MainDBConstants.CASA_TABLE, cv,
                MainDBConstants.codigo + "="
                        + casa.getCodigo(), null) > 0;
    }


    /**
     * Actualiza un participante en la base de datos.
     *
     * @param participante
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean updateParticipantesSent(Participante participante) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.estado, String.valueOf(participante.getEstado()));
        return mDb.update(MainDBConstants.PARTICIPANTE_TABLE, cv,
                MainDBConstants.codigo + "="
                        + participante.getCodigo(), null) > 0;
    }

    /**
     * Actualiza un participanteProc en la base de datos.
     *
     * @param participanteProc
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean updateParticipanteProcSent(ParticipanteProcesos participanteProc) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.estado, String.valueOf(participanteProc.getMovilInfo().getEstado()));
        return mDb.update(ConstantsDB.PART_PROCESOS_TABLE, cv,
                ConstantsDB.CODIGO + "="
                        + participanteProc.getCodigo(), null) > 0;
    }

    /**
     * Actualiza un EncuestaCasa en la base de datos.
     *
     * @param encuesta
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean updateEncuestasCasasSent(EncuestaCasa encuesta) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, encuesta.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.ENC_CASA_TABLE, cv,
                ConstantsDB.codigo + "='"
                        + encuesta.getCodigo() + "'", null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateLacMatSent(LactanciaMaterna lactanciaMaterna) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, lactanciaMaterna.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.LACT_TABLE, cv,
                ConstantsDB.CODIGO + "=" + lactanciaMaterna.getLmId().getCodigo() + " and " +
                        ConstantsDB.FECHA_ENC_LACT + "=" + lactanciaMaterna.getLmId().getFechaEncLM().getTime(), null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateEncPartSent(EncuestaParticipante encparticipante) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, encparticipante.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.ENC_PART_TABLE, cv,
                ConstantsDB.CODIGO + "=" + encparticipante.getEpId().getCodigo() + " and " +
                        ConstantsDB.FECHA_ENC_PAR + "=" + encparticipante.getEpId().getFechaEncPar().getTime(), null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updatePTsSent(PesoyTalla pyt) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, pyt.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.PT_TABLE, cv,
                ConstantsDB.CODIGO + "=" + pyt.getPtId().getCodigo() + " and " +
                        ConstantsDB.FECHA_PT + "=" + pyt.getPtId().getFechaPT().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateMuestraSent(Muestra muestra) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, muestra.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.MUESTRA_TABLE, cv,
                ConstantsDB.CODIGO + "=" + muestra.getmId().getCodigo() + " and " +
                        ConstantsDB.FECHA_MUESTRA + "=" + muestra.getmId().getFechaMuestra().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateObsequioSent(Obsequio obsequio) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, obsequio.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.OB_TABLE, cv,
                ConstantsDB.CODIGO + "=" + obsequio.getObId().getCodigo() + " and " +
                        ConstantsDB.FECHA_OB + "=" + obsequio.getObId().getFechaEntrega().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateVacSent(Vacuna vacuna) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, vacuna.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.VAC_TABLE, cv,
                ConstantsDB.CODIGO + "=" + vacuna.getVacunaId().getCodigo() + " and " +
                        ConstantsDB.FECHA_VACUNA + "=" + vacuna.getVacunaId().getFechaVacuna().getTime(), null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateNewVacSent(NewVacuna vacuna) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, vacuna.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.NEWVAC_TABLE, cv,
                ConstantsDB.CODIGO + "=" + vacuna.getVacunaId().getCodigo() + " and " +
                        ConstantsDB.fechaRegistroVacuna + "=" + vacuna.getVacunaId().getFechaRegistroVacuna().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateDatosPartoBB(DatosPartoBB datosPartoBB) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, datosPartoBB.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.DATOSPARTOBB_TABLE, cv,
                ConstantsDB.CODIGO + "=" + datosPartoBB.getDatosPartoId().getCodigo() + " and " +
                        ConstantsDB.fechaDatosParto + "=" + datosPartoBB.getDatosPartoId().getFechaDatosParto().getTime(), null) > 0;
    }
    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateVisitasSent(VisitaTerreno visita) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, visita.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.VIS_TABLE, cv,
                ConstantsDB.CODIGO + "=" + visita.getVisitaId().getCodigo() + " and " +
                        ConstantsDB.FECHA_VISITA + "=" + visita.getVisitaId().getFechaVisita().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateDatosVisitasSent(DatosVisitaTerreno visita) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, visita.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.DAT_VIS_TABLE, cv,
                ConstantsDB.CODIGO + "=" + visita.getVisitaId().getCodigo() + " and " +
                        ConstantsDB.FECHA_VISITA + "=" + visita.getVisitaId().getFechaVisita().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateReconsSent(ReConsentimientoDen recons) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, recons.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.RECONS_TABLE, cv,
                ConstantsDB.CODIGO + "=" + recons.getReconsdenId().getCodigo() + " and " +
                        ConstantsDB.FECHA_CONS + "=" + recons.getReconsdenId().getFechaCons().getTime(), null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateRecons2015Sent(ReConsentimientoDen2015 recons) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, recons.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.RECONS_TABLE_2015, cv,
                ConstantsDB.CODIGO + "=" + recons.getReconsdenId().getCodigo() + " and " +
                        ConstantsDB.FECHA_CONS + "=" + recons.getReconsdenId().getFechaCons().getTime(), null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateConsZikaSent(ConsentimientoZika cons) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, cons.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.ZIKA_TABLE, cv,
                ConstantsDB.CODIGO + "=" + cons.getConsZikaId().getCodigo() + " and " +
                        ConstantsDB.FECHA_CONS + "=" + cons.getConsZikaId().getFechaCons().getTime(), null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateReconsFlu2015Sent(ReConsentimientoFlu2015 recons) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, recons.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.RECONSFLU_TABLE_2015, cv,
                ConstantsDB.CODIGO + "=" + recons.getReconsfluId().getCodigo() + " and " +
                        ConstantsDB.FECHA_CONS + "=" + recons.getReconsfluId().getFechaCons().getTime(), null) > 0;
    }



    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateConsChikSent(ConsentimientoChik cons) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, cons.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.CONSCHIK_TABLE, cv,
                ConstantsDB.CODIGO + "=" + cons.getConsChikId().getCodigo() + " and " +
                        ConstantsDB.FECHA_CONS + "=" + cons.getConsChikId().getFechaCons().getTime(), null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateCambiosEstudioSent(CambioEstudio cambio) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, cambio.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.CEST_TABLE, cv,
                ConstantsDB.CODIGO + "=" + cambio.getCambioEstudioId().getCodigo() + " and " +
                        ConstantsDB.FECHA_CAMBIO + "=" + cambio.getCambioEstudioId().getFechaCambio().getTime(), null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateBHCsSent(RecepcionBHC tubo) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, tubo.getEstado());
        return mDb.update(ConstantsDB.BHC_TABLE, cv,
                ConstantsDB.CODIGO + "=" + tubo.getRecBhcId().getCodigo() + " and " +
                        ConstantsDB.FECHA_BHC + "=" + tubo.getRecBhcId().getFechaRecBHC().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateSerosSent(RecepcionSero tubo) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, tubo.getEstado());
        return mDb.update(ConstantsDB.SERO_TABLE, cv,
                ConstantsDB.CODIGO + "=" + tubo.getRecSeroId().getCodigo() + " and " +
                        ConstantsDB.FECHA_SERO + "=" + tubo.getRecSeroId().getFechaRecSero().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateTpbmcsSent(TempPbmc temp) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, temp.getEstado());
        return mDb.update(ConstantsDB.TPBMC_TABLE, cv,
                ConstantsDB.RECURSO + "='" + temp.getTempPbmcId().getRecurso() + "' and " +
                        ConstantsDB.FECHA_TEMP + "=" + temp.getTempPbmcId().getFechaTempPbmc().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateTrojosSent(TempRojoBhc temp) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, temp.getEstado());
        return mDb.update(ConstantsDB.TRB_TABLE, cv,
                ConstantsDB.RECURSO + "='" + temp.getTempRojoBhcId().getRecurso() + "' and " +
                        ConstantsDB.FECHA_TEMP + "=" + temp.getTempRojoBhcId().getFechaTempRojoBhc().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updatePinchazosSent(Pinchazo pinchazo) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, pinchazo.getEstado());
        return mDb.update(ConstantsDB.PIN_TABLE, cv,
                ConstantsDB.CODIGO + "=" + pinchazo.getPinId().getCodigo() + " and " +
                        ConstantsDB.FECHA_PIN + "=" + pinchazo.getPinId().getFechaPinchazo().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateRazonNoDataSent(RazonNoData pinchazo) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, pinchazo.getEstado());
        return mDb.update(ConstantsDB.NO_DATA_TABLE, cv,
                ConstantsDB.CODIGO + "=" + pinchazo.getRndId().getCodigo() + " and " +
                        ConstantsDB.TODAY + "=" + pinchazo.getRndId().getFechaRegistro().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateDocumentosSent(Documentos documento) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, documento.getEstado());
        return mDb.update(ConstantsDB.DOCS_TABLE, cv,
                ConstantsDB.CODIGO + "=" + documento.getDocsId().getCodigo() + " and " +
                        ConstantsDB.fechaDocumento + "=" + documento.getDocsId().getFechaDocumento().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateCodigosCasasSent(CodigosCasas ccs) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, ccs.getEstado());
        return mDb.update(ConstantsDB.COD_REL_TABLE, cv,
                ConstantsDB.TODAY + "=" + ccs.getFechaRegistro().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateCambiosCasasSent(CambiosCasas ccs) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, ccs.getEstado());
        return mDb.update(ConstantsDB.CAMB_CASA_TABLE, cv,
                ConstantsDB.TODAY + "=" + ccs.getFechaRegistro().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateEncSatSent(EncuestaSatisfaccion encuesta) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, encuesta.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.ENC_SAT_TABLE, cv,
                ConstantsDB.FECHA_ENC_SAT + "=" + encuesta.getFechaEncuesta().getTime(), null) > 0;
    }

    public Boolean verificarData() throws SQLException{
        Cursor c = null;
        c = mDb.query(true, ConstantsDB.CASA_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.PART_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.ENC_PART_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.LACT_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.PT_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.MUESTRA_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.OB_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.VAC_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.VIS_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.RECONS_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.RECONS_TABLE_2015, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.RECONSFLU_TABLE_2015, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.CONSCHIK_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.BHC_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.SERO_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.PIN_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.NO_DATA_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.COD_REL_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.TPBMC_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();

        c = mDb.query(true, ConstantsDB.TRB_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.ENC_SAT_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.COD_REL_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.CAMB_CASA_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.NEWVAC_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.DATOSPARTOBB_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.DOCS_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        c = mDb.query(true, ConstantsDB.DAT_VIS_TABLE, null,
                ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    /**
     * Actualiza un participante en la base de datos.
     *
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean updateCasaParticipante(Integer codCasa, Integer codigo,String username, String enCasa) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.casa, codCasa);
        //cv.put(ConstantsDB.ENC_CASA, enCasa);
        cv.put(ConstantsDB.STATUS, Constants.STATUS_NOT_SUBMITTED);
        cv.put(MainDBConstants.recordUser, username);
        return mDb.update(MainDBConstants.PARTICIPANTE_TABLE, cv,
                ConstantsDB.CODIGO + "="
                        + codigo, null) > 0;
    }

    public void createCambioCasa(Integer codigo, Integer codigoCasaAnterior, Integer codCasaActual,String username) {
        ContentValues cv = new ContentValues();
        Date hoy = new Date();
        cv.put(ConstantsDB.TODAY, hoy.getTime());
        cv.put(ConstantsDB.codigo, codigo);
        cv.put(ConstantsDB.codCasaAnterior, codigoCasaAnterior);
        cv.put(ConstantsDB.codCasaActual, codCasaActual);
        cv.put(ConstantsDB.STATUS, Constants.STATUS_NOT_SUBMITTED);
        cv.put(ConstantsDB.USUARIO, username);
        mDb.insert(ConstantsDB.CAMB_CASA_TABLE, null, cv);
    }

    public boolean checkCodigosCasas(Integer codigo) {
        Cursor c;
        c = mDb.query(true, ConstantsDB.COD_REL_TABLE, null,
                ConstantsDB.COD_RELA + "="  + codigo + "", null, null, null, null, null);
        if (c != null && c.getCount()>0) {
            c.close();
            return true;
        }
        c.close();
        return false;
    }


}
