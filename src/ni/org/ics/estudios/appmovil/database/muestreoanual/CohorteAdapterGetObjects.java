package ni.org.ics.estudios.appmovil.database.muestreoanual;

/**
 * Adaptador de la base de datos Cohorte
 *
 *
 * @author William Aviles
 */


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import ni.org.ics.estudios.appmovil.database.EstudiosSQLiteOpenHelper;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.*;
import ni.org.ics.estudios.appmovil.helpers.CasaHelper;
import ni.org.ics.estudios.appmovil.helpers.ParticipanteHelper;
import ni.org.ics.estudios.appmovil.muestreoanual.helpers.DocumentosHelper;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import ni.org.ics.estudios.appmovil.utils.FileUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;
public class CohorteAdapterGetObjects {

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mContext;
    private final String mPassword;
    private final boolean mFromServer;
    private final boolean mCleanDb;

    public CohorteAdapterGetObjects(Context context, String password, boolean fromServer, boolean cleanDb) {
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

    public CohorteAdapterGetObjects open() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext,mPassword,mFromServer,mCleanDb);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    /**
     * Obtiene una casa
     *
     * @return Casa
     */
    public Casa getCasa(Integer codCasa) throws SQLException {
        Cursor casas = null;
        Casa casa = new Casa();
        casas = mDb.query(true, ConstantsDB.CASA_TABLE, null,
                ConstantsDB.COD_CASA + "=" + codCasa, null, null, null, null, null);
        if (casas != null && casas.getCount() > 0) {
            casas.moveToFirst();
            casa = CasaHelper.crearCasa(casas);
        }
        casas.close();
        return casa;
    }

    /**
     * Obtiene Lista todas las casas
     *
     * @return lista con casas
     */
    public List<Casa> getListaCasas(String opcion) throws SQLException {
        Cursor casas = null;
        List<Casa> mCasas = new ArrayList<Casa>();
        if(opcion.matches(Constants.STATUS_NOT_SUBMITTED)){
            casas = mDb.query(true, ConstantsDB.CASA_TABLE, null,
                    ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        }
        if (casas != null && casas.getCount() > 0) {
            casas.moveToFirst();
            mCasas.clear();
            do{
                mCasas.add(CasaHelper.crearCasa(casas));
            } while (casas.moveToNext());
        }
        casas.close();
        return mCasas;
    }
    /**
     * Obtiene un participante
     *
     * @return Participante
     */
    public Participante getParticipante(Integer codigo) throws SQLException {
        Cursor participantes = null;
        Participante participante = new Participante();
        participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            participante = ParticipanteHelper.crearParticipante(participantes);
        }
        participantes.close();
        return participante;
    }

    /**
     * Obtiene Lista todas las participantes de una casa
     *
     * @return lista con participantes
     */
    public ArrayList<Participante> getListaParticipantes(Integer codCasa) throws SQLException {
        Cursor participantes = null;
        ArrayList<Participante> mParticipantes = new ArrayList<Participante>();
        participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
                ConstantsDB.COD_CASA + "=" + codCasa, null, null, null, null, null);
        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            mParticipantes.clear();
            do{
                mParticipantes.add(ParticipanteHelper.crearParticipante(participantes));
            } while (participantes.moveToNext());
        }
        participantes.close();
        return mParticipantes;
    }

    /**
     * Obtiene Lista todas las participantes
     *
     * @return lista con participantes
     */
    public List<Participante> getListaParticipantes(String opcion) throws SQLException {
        Cursor participantes = null;
        List<Participante> mParticipantes = new ArrayList<Participante>();
        if(opcion.matches(Constants.STATUS_NOT_SUBMITTED)){
            participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
                    ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        }
        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            mParticipantes.clear();
            do{
                mParticipantes.add(ParticipanteHelper.crearParticipante(participantes));
            } while (participantes.moveToNext());
        }
        participantes.close();
        return mParticipantes;
    }

    /**
     * Obtiene Lista todas las participantes buscando por nombre
     *
     * @return lista con participantes
     */
    public List<Participante> getListaParticipantesName(String name) throws SQLException {
        Cursor participantes = null;
        List<Participante> mParticipantes = new ArrayList<Participante>();

        participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
                MainDBConstants.nombre1 + " LIKE '%" + name + "%' OR "+ ConstantsDB.nombre2 + " LIKE '%" + name + "%'", null, null, null, null, null);

        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            mParticipantes.clear();
            do{
                mParticipantes.add(ParticipanteHelper.crearParticipante(participantes));
            } while (participantes.moveToNext());
        }
        participantes.close();
        return mParticipantes;
    }

    /**
     * Obtiene Lista todas las participantes buscando por apellido
     *
     * @return lista con participantes
     */
    public List<Participante> getListaParticipantesLastName(String lastname) throws SQLException {
        Cursor participantes = null;
        List<Participante> mParticipantes = new ArrayList<Participante>();

        participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
                MainDBConstants.apellido1 + " LIKE '%" + lastname + "%' OR "+ MainDBConstants.apellido2 + " LIKE '%" + lastname + "%'", null, null, null, null, null);

        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            mParticipantes.clear();
            do{
                mParticipantes.add(ParticipanteHelper.crearParticipante(participantes));
            } while (participantes.moveToNext());
        }
        participantes.close();
        return mParticipantes;
    }

    /**
     * Obtiene Lista todas las participantes de una casa
     *
     * @return lista con participantes
     */
    /*
	public ArrayList<ParticipanteProcesos> getListaParticipantes(Integer codCasa) throws SQLException {
		Cursor participantes = null;
		ArrayList<ParticipanteProcesos> mParticipantes = new ArrayList<ParticipanteProcesos>();
		participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
				ConstantsDB.COD_CASA + "=" + codCasa, null, null, null, null, null);
		if (participantes != null && participantes.getCount() > 0) {
			participantes.moveToFirst();
			mParticipantes.clear();
			do{
				mParticipantes.add(crearParticipante(participantes));
			} while (participantes.moveToNext());
		}
		participantes.close();
		return mParticipantes;
	}
*/

    /**
     * Obtiene un participante
     *
     * @return Participante
     */
    /*
    public ParticipanteProcesos getParticipanteProceso(Integer codigo) throws SQLException {
        Cursor participantes = null;
        ParticipanteProcesos participante = new ParticipanteProcesos();
        participantes = mDb.query(true, ConstantsDB.PART_PROCESOS_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            participante = crearParticipanteProcesos(participantes);
        }
        participantes.close();
        return participante;
    }*/
    /**
     * Crea un participante
     *
     * @return Participante
     */
    /*public ParticipanteProcesos crearParticipanteProcesos(Cursor participantes){
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
    }*/

    /**
     * Obtiene Lista todas las participantes
     *
     * @return lista con participantes
     */
    public List<ParticipanteProcesos> getListaParticipantesProc(String opcion) throws SQLException {
        Cursor participantes = null;
        List<ParticipanteProcesos> mParticipantes = new ArrayList<ParticipanteProcesos>();
        if(opcion.matches(Constants.STATUS_NOT_SUBMITTED)){
            participantes = mDb.query(true, ConstantsDB.PART_PROCESOS_TABLE, null,
                    ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        }
        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            mParticipantes.clear();
            do{
                mParticipantes.add(ParticipanteHelper.crearParticipanteProcesos(participantes));
            } while (participantes.moveToNext());
        }
        if (participantes!=null && !participantes.isClosed())participantes.close();
        return mParticipantes;
    }

    /**
     * Obtiene Lista todas las encuestas casas sin enviar
     *
     * @return lista con EncuestaCasa
     */
    public List<EncuestaCasa> getListaEncuestaCasasSinEnviar() throws SQLException {
        Cursor enccasas = null;
        List<EncuestaCasa> mEncuestaCasas = new ArrayList<EncuestaCasa>();
        enccasas = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (enccasas != null && enccasas.getCount() > 0) {
            enccasas.moveToFirst();
            mEncuestaCasas.clear();
            do{
                mEncuestaCasas.add(crearEncuestaCasa(enccasas));
            } while (enccasas.moveToNext());
        }
        enccasas.close();
        return mEncuestaCasas;
    }

    /**
     * Obtiene Lista todas las encuestas casas para un codigo
     *
     * @return lista con EncuestaCasa
     */
    public ArrayList<EncuestaCasa> getListaEncuestaCasas(Integer codCasa) throws SQLException {
        Cursor enccasas = null;
        ArrayList<EncuestaCasa> mEncuestaCasas = new ArrayList<EncuestaCasa>();
        enccasas = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                ConstantsDB.COD_CASA + "=" + codCasa, null, null, null, null, null);
        if (enccasas != null && enccasas.getCount() > 0) {
            enccasas.moveToFirst();
            mEncuestaCasas.clear();
            do{
                mEncuestaCasas.add(crearEncuestaCasa(enccasas));
            } while (enccasas.moveToNext());
        }
        enccasas.close();
        return mEncuestaCasas;
    }

    /**
     * Obtiene Lista todas las encuestas casas chf para un codigo
     *
     * @return lista con EncuestaCasa
     */
    public ArrayList<EncuestaCasa> getListaEncuestaCasasChf(String codCasa) throws SQLException {
        Cursor enccasas = null;
        ArrayList<EncuestaCasa> mEncuestaCasas = new ArrayList<EncuestaCasa>();
        enccasas = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                ConstantsDB.codigoCHF + "=" + codCasa, null, null, null, null, null);
        if (enccasas != null && enccasas.getCount() > 0) {
            enccasas.moveToFirst();
            mEncuestaCasas.clear();
            do{
                mEncuestaCasas.add(crearEncuestaCasa(enccasas));
            } while (enccasas.moveToNext());
        }
        enccasas.close();
        return mEncuestaCasas;
    }

    /**
     * Obtiene Lista todas las encuestas casas para un codigo
     *
     * @return lista con EncuestaCasa
     */
    public ArrayList<EncuestaCasa> getListaEncuestaCasas() throws SQLException {
        Cursor enccasas = null;
        ArrayList<EncuestaCasa> mEncuestaCasas = new ArrayList<EncuestaCasa>();
        enccasas = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                null, null, null, null, ConstantsDB.COD_CASA + " , " +ConstantsDB.TODAY, null);
        if (enccasas != null && enccasas.getCount() > 0) {
            enccasas.moveToFirst();
            mEncuestaCasas.clear();
            do{
                mEncuestaCasas.add(crearEncuestaCasa(enccasas));
            } while (enccasas.moveToNext());
        }
        enccasas.close();
        return mEncuestaCasas;
    }
    public ArrayList<EncuestaCasa> getListaEncuestaCasasHoy() throws SQLException {
        Cursor enccasas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<EncuestaCasa> mEncuestaCasas = new ArrayList<EncuestaCasa>();
        enccasas = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.COD_CASA + " , " +ConstantsDB.TODAY, null);
        if (enccasas != null && enccasas.getCount() > 0) {
            enccasas.moveToFirst();
            mEncuestaCasas.clear();
            do{
                mEncuestaCasas.add(crearEncuestaCasa(enccasas));
            } while (enccasas.moveToNext());
        }
        enccasas.close();
        return mEncuestaCasas;
    }

    /**
     * Crea una EncuestaCasa
     *
     * @return EncuestaCasa
     */
    public EncuestaCasa crearEncuestaCasa(Cursor enccasas){
        EncuestaCasa mEncCasa = new EncuestaCasa();
        Date fecha = new Date(enccasas.getLong(enccasas.getColumnIndex(ConstantsDB.TODAY)));
        //EncuestaCasaId enccasaId = new EncuestaCasaId();
        //enccasaId.setCodCasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.COD_CASA)));
        //enccasaId.setFechaEncCasa(new Date(enccasas.getLong(enccasas.getColumnIndex(ConstantsDB.FECHA_ENC_CASA))));

        //mEncCasa.setEncCasaId(enccasaId);
        mEncCasa.setCodigo(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.codigo)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.COD_CASA))) mEncCasa.setCodCasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.COD_CASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.codigoCHF))) mEncCasa.setCodCasaChf(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.codigoCHF)));
        mEncCasa.setFechaEncCasa(new Date(enccasas.getLong(enccasas.getColumnIndex(ConstantsDB.FECHA_ENC_CASA))));

        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN1))) mEncCasa.setCvivencasa1(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN1)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN2))) mEncCasa.setCvivencasa2(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN2)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN3))) mEncCasa.setCvivencasa3(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN3)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN4))) mEncCasa.setCvivencasa4(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN4)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN5))) mEncCasa.setCvivencasa5(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN5)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN6))) mEncCasa.setCvivencasa6(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN6)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN7))) mEncCasa.setCvivencasa7(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN7)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CCUARTOS))) mEncCasa.setCcuartos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CCUARTOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.GRIFO))) mEncCasa.setGrifo(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.GRIFO)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.GRIFOCOM))) mEncCasa.setGrifoComSN(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.GRIFOCOM)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.HORASAGUA))) mEncCasa.sethorasagua(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.HORASAGUA)));
        mEncCasa.setMcasa(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.MCASA)));
        mEncCasa.setOcasa(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OCASA)));
        mEncCasa.setPiso(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PISO)));
        mEncCasa.setOpiso(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OPISO)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.TECHO))) mEncCasa.setTecho(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.TECHO)));
        mEncCasa.setOtecho(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OTECHO)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CPROPIA))) mEncCasa.setCpropia(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CPROPIA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.ABANICOS))) mEncCasa.setCabanicos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.ABANICOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.TVS))) mEncCasa.setCtelevisores(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.TVS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.REFRI))) mEncCasa.setCrefrigeradores(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.REFRI)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.MOTO))) mEncCasa.setMoto(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.MOTO)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CARRO))) mEncCasa.setCarro(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CARRO)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.LENA))) mEncCasa.setCocinalena(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.LENA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.ANIMALES))) mEncCasa.setAnimalesSN(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.ANIMALES)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.POLLOS))) mEncCasa.setPollos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.POLLOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.POLLOSCASA))) mEncCasa.setPolloscasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.POLLOSCASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.PATOS))) mEncCasa.setPatos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.PATOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.PATOSCASA))) mEncCasa.setPatoscasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.PATOSCASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.PERROS))) mEncCasa.setPerros(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.PERROS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.PERROSCASA))) mEncCasa.setPerroscasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.PERROSCASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.GATOS))) mEncCasa.setGatos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.GATOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.GATOSCASA))) mEncCasa.setGatoscasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.GATOSCASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CERDOS))) mEncCasa.setCerdos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CERDOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CERDOSCASA))) mEncCasa.setCerdoscasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CERDOSCASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.otrorecurso1))) mEncCasa.setOtrorecurso1(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.otrorecurso2))) mEncCasa.setOtrorecurso2(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.otrorecurso2)));

        //CHF + NUEVAS PREGUNTAS MA2018
        mEncCasa.setViveEmbEnCasa(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.viveEmbEnCasa)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CANTIDAD_CUARTOS))) mEncCasa.setCantidadCuartos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CANTIDAD_CUARTOS)));
        mEncCasa.setAlmacenaAgua(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.ALMACENA_AGUA)));
        mEncCasa.setAlmacenaEnBarriles(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.ALMACENA_EN_BARRILES)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUMERO_BARRILES))) mEncCasa.setNumeroBarriles(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUMERO_BARRILES)));
        mEncCasa.setBarrilesTapados(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.BARRILES_TAPADOS)));
        mEncCasa.setBarrilesConAbate(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.BARRILES_CON_ABATE)));
        mEncCasa.setAlmacenaEnTanques(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.ALMACENA_EN_TANQUES)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUMERO_TANQUES))) mEncCasa.setNumeroTanques(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUMERO_TANQUES)));
        mEncCasa.setTanquesTapados(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TANQUES_TAPADOS)));
        mEncCasa.setTanquesConAbate(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TANQUES_CON_ABATE)));
        mEncCasa.setAlmacenaEnPilas(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.ALMACENA_EN_PILAS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUMERO_PILAS))) mEncCasa.setNumeroPilas(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUMERO_PILAS)));
        mEncCasa.setPilasTapadas(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PILAS_TAPADAS)));
        mEncCasa.setPilasConAbate(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PILAS_CON_ABATE)));
        mEncCasa.setAlmacenaEnOtrosrecip(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.ALMACENA_EN_OTROSRECIP)));
        mEncCasa.setDescOtrosRecipientes(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.DESC_OTROS_RECIPIENTES)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUMERO_OTROS_RECIPIENTES))) mEncCasa.setNumeroOtrosRecipientes(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUMERO_OTROS_RECIPIENTES)));
        mEncCasa.setOtrosRecipTapados(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OTROS_RECIP_TAPADOS)));
        mEncCasa.setOtrosrecipConAbate(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OTROSRECIP_CON_ABATE)));
        mEncCasa.setUbicacionLavandero(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.UBICACION_LAVANDERO)));
        mEncCasa.setTieneAbanico(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_ABANICO)));
        mEncCasa.setTieneTelevisor(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_TELEVISOR)));
        mEncCasa.setTieneRefrigeradorFreezer(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_REFRIGERADOR_FREEZER)));
        mEncCasa.setTieneAireAcondicionado(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_AIRE_ACONDICIONADO)));
        mEncCasa.setFuncionamientoAire(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.FUNCIONAMIENTO_AIRE)));
        mEncCasa.setOpcFabCarro(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.opcFabCarro)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearNow))) mEncCasa.setYearNow(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearNow)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearFabCarro))) mEncCasa.setYearFabCarro(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearFabCarro)));
        mEncCasa.setTieneMicrobus(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_MICROBUS)));
        mEncCasa.setTieneCamioneta(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_CAMIONETA)));
        mEncCasa.setTieneCamion(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_CAMION)));
        mEncCasa.setTieneOtroMedioTrans(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_OTRO_MEDIO_TRANS)));
        mEncCasa.setDescOtroMedioTrans(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.DESC_OTRO_MEDIO_TRANS)));
        mEncCasa.setCocinaConLenia(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.COCINA_CON_LENIA)));
        mEncCasa.setUbicacionCocinaLenia(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.UBICACION_COCINA_LENIA)));
        mEncCasa.setPeriodicidadCocinaLenia(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PERIODICIDAD_COCINA_LENIA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUM_DIARIO_COCINA_LENIA))) mEncCasa.setNumDiarioCocinaLenia(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUM_DIARIO_COCINA_LENIA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUM_SEMANAL_COCINA_LENIA))) mEncCasa.setNumSemanalCocinaLenia(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUM_SEMANAL_COCINA_LENIA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUM_QUINCENAL_COCINA_LENIA))) mEncCasa.setNumQuincenalCocinaLenia(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUM_QUINCENAL_COCINA_LENIA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUM_MENSUAL_COCINA_LENIA))) mEncCasa.setNumMensualCocinaLenia(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUM_MENSUAL_COCINA_LENIA)));
        mEncCasa.setTieneGallinas(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_GALLINAS)));
        mEncCasa.setTienePatos(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_PATOS)));
        mEncCasa.setTienePerros(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_PERROS)));
        mEncCasa.setTieneGatos(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_GATOS)));
        mEncCasa.setTieneCerdos(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_CERDOS)));
        mEncCasa.setPersFumaDentroCasa(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PERS_FUMA_DENTRO_CASA)));
        mEncCasa.setMadreFuma(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.MADRE_FUMA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_MADRE))) mEncCasa.setCantCigarrillosMadre(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_MADRE)));
        mEncCasa.setPadreFuma(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PADRE_FUMA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_PADRE))) mEncCasa.setCantCigarrillosPadre(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_PADRE)));
        mEncCasa.setOtrosFuman(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OTROS_FUMAN)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CANT_OTROS_FUMAN))) mEncCasa.setCantOtrosFuman(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CANT_OTROS_FUMAN)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_OTROS))) mEncCasa.setCantCigarrillosOtros(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_OTROS)));
        mEncCasa.setServRecolBasura(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.servRecolBasura)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.frecServRecolBasura))) mEncCasa.setFrecServRecolBasura(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.frecServRecolBasura)));
        mEncCasa.setLlantasOtrosContConAgua(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.llantasOtrosContConAgua)));
        mEncCasa.setOpcFabMicrobus(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.opcFabMicrobus)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearFabMicrobus))) mEncCasa.setYearFabMicrobus(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearFabMicrobus)));
        mEncCasa.setOpcFabCamioneta(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.opcFabCamioneta)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearFabCamioneta))) mEncCasa.setYearFabCamioneta(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearFabCamioneta)));
        mEncCasa.setOpcFabCamion(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.opcFabCamion)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearFabCamion))) mEncCasa.setYearFabCamion(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearFabCamion)));
        mEncCasa.setOpcFabOtroMedioTrans(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.opcFabOtroMedioTrans)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearFabOtroMedioTrans))) mEncCasa.setYearFabOtroMedioTrans(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearFabOtroMedioTrans)));

        Boolean borrado = enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.DELETED))>0;
        mEncCasa.setMovilInfo(new MovilInfo(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.FILE_PATH)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.STATUS)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.START)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.END)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.DEVICE_ID)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.REC1)),
                enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.REC2))));

        return mEncCasa;
    }

    /**
     * Obtiene Lista todas las encuestas participantes sin enviar
     *
     * @return lista con EncuestaParticipante
     */
    public List<EncuestaParticipante> getListaEncuestaParticipantesSinEnviar() throws SQLException {
        Cursor encparts = null;
        List<EncuestaParticipante> mEncuestaParticipantes = new ArrayList<EncuestaParticipante>();
        encparts = mDb.query(true, ConstantsDB.ENC_PART_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (encparts != null && encparts.getCount() > 0) {
            encparts.moveToFirst();
            mEncuestaParticipantes.clear();
            do{
                mEncuestaParticipantes.add(crearEncuestaParticipante(encparts));
            } while (encparts.moveToNext());
        }
        encparts.close();
        return mEncuestaParticipantes;
    }

    /**
     * Obtiene Lista todas las encuestas participantes de un codigo
     *
     * @return lista con EncuestaParticipante
     */
    public ArrayList<EncuestaParticipante> getListaEncuestaParticipantes(Integer codigo) throws SQLException {
        Cursor encparts = null;
        ArrayList<EncuestaParticipante> mEncuestaParticipantes = new ArrayList<EncuestaParticipante>();
        encparts = mDb.query(true, ConstantsDB.ENC_PART_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (encparts != null && encparts.getCount() > 0) {
            encparts.moveToFirst();
            mEncuestaParticipantes.clear();
            do{
                mEncuestaParticipantes.add(crearEncuestaParticipante(encparts));
            } while (encparts.moveToNext());
        }
        encparts.close();
        return mEncuestaParticipantes;
    }

    /**
     * Obtiene Lista todas las encuestas participantes de un codigo
     *
     * @return lista con EncuestaParticipante
     */
    public ArrayList<EncuestaParticipante> getListaEncuestaParticipantes() throws SQLException {
        Cursor encparts = null;
        ArrayList<EncuestaParticipante> mEncuestaParticipantes = new ArrayList<EncuestaParticipante>();
        encparts = mDb.query(true, ConstantsDB.ENC_PART_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (encparts != null && encparts.getCount() > 0) {
            encparts.moveToFirst();
            mEncuestaParticipantes.clear();
            do{
                mEncuestaParticipantes.add(crearEncuestaParticipante(encparts));
            } while (encparts.moveToNext());
        }
        encparts.close();
        return mEncuestaParticipantes;
    }
    public ArrayList<EncuestaParticipante> getListaEncuestaParticipantesHoy() throws SQLException {
        Cursor encparts = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<EncuestaParticipante> mEncuestaParticipantes = new ArrayList<EncuestaParticipante>();
        encparts = mDb.query(true, ConstantsDB.ENC_PART_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (encparts != null && encparts.getCount() > 0) {
            encparts.moveToFirst();
            mEncuestaParticipantes.clear();
            do{
                mEncuestaParticipantes.add(crearEncuestaParticipante(encparts));
            } while (encparts.moveToNext());
        }
        encparts.close();
        return mEncuestaParticipantes;
    }

    /**
     * Crea una EncuestaParticipante
     *
     * @return EncuestaParticipante
     */
    public EncuestaParticipante crearEncuestaParticipante(Cursor encparticipantess){
        EncuestaParticipante mEncPart = new EncuestaParticipante();
        Date fecha = new Date(encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.TODAY)));
        EncuestaParticipanteId encparId = new EncuestaParticipanteId();
        encparId.setCodigo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CODIGO)));
        encparId.setFechaEncPar(new Date(encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.FECHA_ENC_PAR))));
        mEncPart.setEpId(encparId);
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.FIEBRE))) mEncPart.setFiebre(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.FIEBRE)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.TFIEBRE))) mEncPart.setTiemFieb(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.TFIEBRE)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.LUGCONS))) mEncPart.setLugarCons(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.LUGCONS)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.NOCS))) mEncPart.setNoCs(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.NOCS)));
        mEncPart.setAutomed(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.AUTOMED)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ESC))) mEncPart.setEscuela(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ESC)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.GRADO))) mEncPart.setGrado(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.GRADO)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.TURNO))) mEncPart.setTurno(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.TURNO)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.NESC))) mEncPart.setnEscuela(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.NESC)));
        mEncPart.setOtraEscuela(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.OESC)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CUIDAN))) mEncPart.setCuidan(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CUIDAN)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CCUIDAN))) mEncPart.setCuantosCuidan(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CCUIDAN)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CQVIVE))) mEncPart.setCqVive(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CQVIVE)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.LUGPARTO))) mEncPart.setLugarPart(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.LUGPARTO)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.PAPAALF))) mEncPart.setPapaAlf(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.PAPAALF)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.PAPANIVEL))) mEncPart.setPapaNivel(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.PAPANIVEL)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.PAPATRA))) mEncPart.setPapaTra(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.PAPATRA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.PAPATIPOT))) mEncPart.setPapaTipoTra(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.PAPATIPOT)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.MAMAALF))) mEncPart.setMamaAlf(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.MAMAALF)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.MAMANIVEL))) mEncPart.setMamaNivel(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.MAMANIVEL)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.MAMATRA))) mEncPart.setMamaTra(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.MAMATRA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.MAMATIPOT))) mEncPart.setMamaTipoTra(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.MAMATIPOT)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.COMPARTEHAB))) mEncPart.setComparteHab(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.COMPARTEHAB)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB1))) mEncPart.setHab1(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB1)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB2))) mEncPart.setHab2(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB2)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB3))) mEncPart.setHab3(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB3)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB4))) mEncPart.setHab4(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB4)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB5))) mEncPart.setHab5(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB5)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB6))) mEncPart.setHab6(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB6)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.COMPARTECAMA))) mEncPart.setComparteCama(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.COMPARTECAMA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA1))) mEncPart.setCama1(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA1)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA2))) mEncPart.setCama2(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA2)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA3))) mEncPart.setCama3(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA3)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA4))) mEncPart.setCama4(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA4)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA5))) mEncPart.setCama5(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA5)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA6))) mEncPart.setCama6(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA6)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ASMA))) mEncPart.setAsma(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ASMA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SILB12))) mEncPart.setSilb12m(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SILB12)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SIT1))) mEncPart.setSitResf(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SIT1)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SIT2))) mEncPart.setSitEjer(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SIT2)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SILB01))) mEncPart.setSilbMesPas(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SILB01)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.DIFHAB))) mEncPart.setDifHablar(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.DIFHAB)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.VECDIFHAB))) mEncPart.setVecHablar(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.VECDIFHAB)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.DIFDOR))) mEncPart.setDifDormir(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.DIFDOR)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SUENOPER))) mEncPart.setSuenoPer(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SUENOPER)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.TOS12))) mEncPart.setTos12m(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.TOS12)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.VECESTOS))) mEncPart.setVecesTos(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.VECESTOS)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.TOS3DIAS))) mEncPart.setTos3Dias(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.TOS3DIAS)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HOSP12M))) mEncPart.setHosp12m(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HOSP12M)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.MED12M))) mEncPart.setMed12m(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.MED12M)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.DEP12M))) mEncPart.setDep12m(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.DEP12M)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CRISIS))) mEncPart.setCrisis(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CRISIS)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.FRECASMA))) mEncPart.setFrecAsma(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.FRECASMA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.FUMA))) mEncPart.setFumaSN(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.FUMA)));
        mEncPart.setQuienFuma(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.QUIENFUMA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CIGMADRE))) mEncPart.setCantCigarrosMadre(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CIGMADRE)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CIGPADRE))) mEncPart.setCantCigarrosPadre(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CIGPADRE)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CIGOTRO))) mEncPart.setCantCigarrosOtros(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CIGOTRO)));

        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rash))) mEncPart.setRash(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rash)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.mesActual))) mEncPart.setMesActual(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.mesActual)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.yearActual))) mEncPart.setYearActual(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.yearActual)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rash_year))) mEncPart.setRash_year(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rash_year)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rash_mes))) mEncPart.setRash_mes(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rash_mes)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rash_mesact))) mEncPart.setRash_mesact(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rash_mesact)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashCara))) mEncPart.setRashCara(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashCara)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashMiembrosSup))) mEncPart.setRashMiembrosSup(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashMiembrosSup)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashTorax))) mEncPart.setRashTorax(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashTorax)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashAbdomen))) mEncPart.setRashAbdomen(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashAbdomen)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashMiembrosInf))) mEncPart.setRashMiembrosInf(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashMiembrosInf)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashDias))) mEncPart.setRashDias(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashDias)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo))) mEncPart.setOjoRojo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_year))) mEncPart.setOjoRojo_year(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_year)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_mes))) mEncPart.setOjoRojo_mes(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_mes)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_mesact))) mEncPart.setOjoRojo_mesact(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_mesact)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_Dias))) mEncPart.setOjoRojo_Dias(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_Dias)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.hormigueo))) mEncPart.setHormigueo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.hormigueo)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_year))) mEncPart.setHormigueo_year(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_year)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_mes))) mEncPart.setHormigueo_mes(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_mes)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_mesact))) mEncPart.setHormigueo_mesact(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_mesact)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_Dias))) mEncPart.setHormigueo_Dias(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_Dias)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.consultaRashHormigueo))) mEncPart.setConsultaRashHormigueo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.consultaRashHormigueo)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.uSaludRashHormigueo))) mEncPart.setuSaludRashHormigueo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.uSaludRashHormigueo)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.cSaludRashHormigueo))) mEncPart.setcSaludRashHormigueo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.cSaludRashHormigueo)));
        mEncPart.setoCSRashHormigueo(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.oCSRashHormigueo)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.pSRashHormigueo))) mEncPart.setpSRashHormigueo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.pSRashHormigueo)));
        mEncPart.setoPSRashHormigueo(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.oPSRashHormigueo)));
        mEncPart.setDiagRashHormigueo(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.diagRashHormigueo)));
        mEncPart.setChPapaMama(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.chPapaMama)));
        if (encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.fechana_papa)) > 0) mEncPart.setFechana_papa(new Date(encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.fechana_papa))));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.cal_edad_papa))) mEncPart.setCal_edad_papa(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.cal_edad_papa)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.cal_edad2_papa))) mEncPart.setCal_edad2_papa(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.cal_edad2_papa)));
        mEncPart.setNombpapa1(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.nombpapa1)));
        mEncPart.setNombpapa2(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.nombpapa2)));
        mEncPart.setApellipapa1(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.apellipapa1)));
        mEncPart.setApellipapa2(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.apellipapa2)));
        if (encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.fechana_mama)) > 0) mEncPart.setFechana_mama(new Date(encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.fechana_mama))));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.cal_edad_mama))) mEncPart.setCal_edad_mama(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.cal_edad_mama)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.cal_edad2_mama))) mEncPart.setCal_edad2_mama(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.cal_edad2_mama)));
        mEncPart.setNombmama1(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.nombmama1)));
        mEncPart.setNombmama2(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.nombmama2)));
        mEncPart.setApellimama1(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.apellimama1)));
        mEncPart.setApellimama2(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.apellimama2)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.otrorecurso1))) mEncPart.setOtrorecurso1(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.otrorecurso2))) mEncPart.setOtrorecurso2(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.otrorecurso2)));

        //CHF + NUEVAS PREGUNTAS MA2018
        mEncPart.setEmancipado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.EMANCIPADO)));
        mEncPart.setDescEnmancipado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.descEnmancipado)));
        mEncPart.setOtraDescEmanc(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.otraDescEmanc)));
        mEncPart.setEmbarazada(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.EMBARAZADA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SEMANAS_EMBARAZO))) mEncPart.setSemanasEmbarazo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SEMANAS_EMBARAZO)));
        mEncPart.setAlfabeto(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.ALFABETO)));
        mEncPart.setNivelEducacion(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.NIVEL_EDUCACION)));
        mEncPart.setTrabaja(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TRABAJA)));
        mEncPart.setTipoTrabajo(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TIPO_TRABAJO)));
        mEncPart.setOcupacionActual(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.OCUPACION_ACTUAL)));
        mEncPart.setNinoAsisteEscuela(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.NINO_ASISTE_ESCUELA)));
        mEncPart.setGradoEstudiaNino(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.GRADO_ESTUDIA_NINO)));
        mEncPart.setNinoTrabaja(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.NINO_TRABAJA)));
        mEncPart.setOcupacionActualNino(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.OCUPACION_ACTUAL_NINO)));
        mEncPart.setPadreEstudio(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.PADRE_ESTUDIO)));
        mEncPart.setCodigoPadreEstudio(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.CODIGO_PADRE_ESTUDIO)));
        mEncPart.setPadreAlfabeto(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.PADRE_ALFABETO)));
        mEncPart.setTrabajaPadre(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TRABAJA_PADRE)));
        mEncPart.setMadreEstudio(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.MADRE_ESTUDIO)));
        mEncPart.setCodigoMadreEstudio(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.CODIGO_MADRE_ESTUDIO)));
        mEncPart.setMadreAlfabeta(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.MADRE_ALFABETA)));
        mEncPart.setTrabajaMadre(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TRABAJA_MADRE)));
        mEncPart.setFuma(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FUMACHF)));
        mEncPart.setPeriodicidadFuna(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.PERIODICIDAD_FUNA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CANTIDAD_CIGARRILLOS))) mEncPart.setCantidadCigarrillos(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CANTIDAD_CIGARRILLOS)));
        mEncPart.setFumaDentroCasa(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FUMA_DENTRO_CASA)));
        mEncPart.setTuberculosisPulmonarActual(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TUBERCULOSIS_PULMONAR_ACTUAL)));
        mEncPart.setFechaDiagTubpulActual(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FECHA_DIAG_TUBPUL_ACTUAL)));
        mEncPart.setTratamientoTubpulActual(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TRATAMIENTO_TUBPUL_ACTUAL)));
        mEncPart.setCompletoTratamientoTubpulActual(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.COMPLETO_TRATAMIENTO_TUBPUL_ACTUAL)));
        mEncPart.setTuberculosisPulmonarPasado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TUBERCULOSIS_PULMONAR_PASADO)));
        mEncPart.setFechaDiagTubpulPasadoDes(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FECHA_DIAG_TUBPUL_PASADO_DES)));
        mEncPart.setFechaDiagTubpulPasado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FECHA_DIAG_TUBPUL_PASADO)));
        mEncPart.setTratamientoTubpulPasado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TRATAMIENTO_TUBPUL_PASADO)));
        mEncPart.setCompletoTratamientoTubpulPas(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.COMPLETO_TRATAMIENTO_TUBPUL_PAS)));
        mEncPart.setAlergiaRespiratoria(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.ALERGIA_RESPIRATORIA)));
        mEncPart.setCardiopatia(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.CARDIOPATIA)));
        mEncPart.setEnfermPulmonarObstCronica(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.ENFERM_PULMONAR_OBST_CRONICA)));
        mEncPart.setDiabetes(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.DIABETES)));
        mEncPart.setPresionAlta(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.PRESION_ALTA)));
        mEncPart.setTosSinFiebreResfriado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TOS_SIN_FIEBRE_RESFRIADO)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CANT_ENFERM_CUADROS_RESP))) mEncPart.setCantEnfermCuadrosResp(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CANT_ENFERM_CUADROS_RESP)));
        mEncPart.setOtrasEnfermedades(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.OTRAS_ENFERMEDADES)));
        mEncPart.setDescOtrasEnfermedades(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.DESC_OTRAS_ENFERMEDADES)));
        mEncPart.setVacunaInfluenza(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.VACUNA_INFLUENZA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ANIO_VACUNA_INFLUENZA))) mEncPart.setAnioVacunaInfluenza(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ANIO_VACUNA_INFLUENZA)));
        mEncPart.setRash6m(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.rash6m)));
        mEncPart.setEstudiosAct(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.estudiosAct)));
        mEncPart.setOjoRojo6m(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo6m)));


        Boolean borrado = encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.DELETED))>0;
        mEncPart.setMovilInfo(new MovilInfo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FILE_PATH)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.STATUS)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.START)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.END)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.DEVICE_ID)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.REC1)),
                encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.REC2))));
        return mEncPart;
    }


    /**
     * Obtiene Lista todas las encuestas de lactancia sin enviar
     *
     * @return lista con LactanciaMaterna
     */
    public List<LactanciaMaterna> getListaLactanciaMaternasSinEnviar() throws SQLException {
        Cursor enclacts = null;
        List<LactanciaMaterna> mLactanciaMaternas = new ArrayList<LactanciaMaterna>();
        enclacts = mDb.query(true, ConstantsDB.LACT_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (enclacts != null && enclacts.getCount() > 0) {
            enclacts.moveToFirst();
            mLactanciaMaternas.clear();
            do{
                mLactanciaMaternas.add(crearLactanciaMaterna(enclacts));
            } while (enclacts.moveToNext());
        }
        enclacts.close();
        return mLactanciaMaternas;
    }

    /**
     * Obtiene Lista todas las encuestas de lactancia de un codigo
     *
     * @return lista con LactanciaMaterna
     */
    public ArrayList<LactanciaMaterna> getListaLactanciaMaternas(Integer codigo) throws SQLException {
        Cursor enclacts = null;
        ArrayList<LactanciaMaterna> mLactanciaMaternas = new ArrayList<LactanciaMaterna>();
        enclacts = mDb.query(true, ConstantsDB.LACT_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (enclacts != null && enclacts.getCount() > 0) {
            enclacts.moveToFirst();
            mLactanciaMaternas.clear();
            do{
                mLactanciaMaternas.add(crearLactanciaMaterna(enclacts));
            } while (enclacts.moveToNext());
        }
        enclacts.close();
        return mLactanciaMaternas;
    }



    /**
     * Obtiene Lista todas las encuestas de lactancia de un codigo
     *
     * @return lista con LactanciaMaterna
     */
    public ArrayList<LactanciaMaterna> getListaLactanciaMaternas() throws SQLException {
        Cursor enclacts = null;
        ArrayList<LactanciaMaterna> mLactanciaMaternas = new ArrayList<LactanciaMaterna>();
        enclacts = mDb.query(true, ConstantsDB.LACT_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (enclacts != null && enclacts.getCount() > 0) {
            enclacts.moveToFirst();
            mLactanciaMaternas.clear();
            do{
                mLactanciaMaternas.add(crearLactanciaMaterna(enclacts));
            } while (enclacts.moveToNext());
        }
        enclacts.close();
        return mLactanciaMaternas;
    }
    public ArrayList<LactanciaMaterna> getListaLactanciaMaternasHoy() throws SQLException {
        Cursor enclacts = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<LactanciaMaterna> mLactanciaMaternas = new ArrayList<LactanciaMaterna>();
        enclacts = mDb.query(true, ConstantsDB.LACT_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (enclacts != null && enclacts.getCount() > 0) {
            enclacts.moveToFirst();
            mLactanciaMaternas.clear();
            do{
                mLactanciaMaternas.add(crearLactanciaMaterna(enclacts));
            } while (enclacts.moveToNext());
        }
        enclacts.close();
        return mLactanciaMaternas;
    }

    /**
     * Crea una LactanciaMaterna
     *
     * @return LactanciaMaterna
     */
    public LactanciaMaterna crearLactanciaMaterna(Cursor lactanciasmaternas){
        LactanciaMaterna mLacMat = new LactanciaMaterna();
        Date fecha = new Date(lactanciasmaternas.getLong(lactanciasmaternas.getColumnIndex(ConstantsDB.TODAY)));
        LactanciaMaternaId lacMatId = new LactanciaMaternaId();
        lacMatId.setCodigo(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.CODIGO)));
        lacMatId.setFechaEncLM(new Date(lactanciasmaternas.getLong(lactanciasmaternas.getColumnIndex(ConstantsDB.FECHA_ENC_LACT))));

        mLacMat.setLmId(lacMatId);
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.EDAD))) mLacMat.setEdad(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.EDAD)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.DIOPECHO))) mLacMat.setDioPecho(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.DIOPECHO)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.TIEMPECHO))) mLacMat.setTiemPecho(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.TIEMPECHO)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.MESDIOPECHO))) mLacMat.setMesDioPecho(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.MESDIOPECHO)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.PECHOEXC))) mLacMat.setPechoExc(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.PECHOEXC)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.PECHOEXCANT))) mLacMat.setPechoExcAntes(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.PECHOEXCANT)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.TPECHOEXCANT))) mLacMat.setTiempPechoExcAntes(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.TPECHOEXCANT)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.MPECHOEXCANT))) mLacMat.setMestPechoExc(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.MPECHOEXCANT)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.FORMALIM))) mLacMat.setFormAlim(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.FORMALIM)));
        mLacMat.setOtraAlim(lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.OTRALIM)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADLIQDP))) mLacMat.setEdadLiqDistPecho(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADLIQDP)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.MESLIQDP))) mLacMat.setMesDioLiqDisPecho(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.MESLIQDP)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADLIQDL))) mLacMat.setEdadLiqDistLeche(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADLIQDL)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.MESLIQDL))) mLacMat.setMesDioLiqDisLeche(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.MESLIQDL)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADALIMS))) mLacMat.setEdAlimSolidos(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADALIMS)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.MESALIMS))) mLacMat.setMesDioAlimSol(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.MESALIMS)));

        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.otrorecurso1))) mLacMat.setOtrorecurso1(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.otrorecurso2))) mLacMat.setOtrorecurso2(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.otrorecurso2)));

        Boolean borrado = lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.DELETED))>0;
        mLacMat.setMovilInfo(new MovilInfo(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.FILE_PATH)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.STATUS)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.START)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.END)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.DEVICE_ID)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.REC1)),
                lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.REC2))));
        return mLacMat;
    }


    /**
     * Obtiene Lista todas los PesoyTalla sin enviar
     *
     * @return lista con PesoyTalla
     */
    public List<PesoyTalla> getListaPesoyTallasSinEnviar() throws SQLException {
        Cursor pyts = null;
        List<PesoyTalla> mPesoyTallas = new ArrayList<PesoyTalla>();
        pyts = mDb.query(true, ConstantsDB.PT_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (pyts != null && pyts.getCount() > 0) {
            pyts.moveToFirst();
            mPesoyTallas.clear();
            do{
                mPesoyTallas.add(crearPesoyTalla(pyts));
            } while (pyts.moveToNext());
        }
        pyts.close();
        return mPesoyTallas;
    }

    /**
     * Obtiene Lista todas los PesoyTalla de un codigo
     *
     * @return lista con PesoyTalla
     */
    public ArrayList<PesoyTalla> getListaPesoyTallas(Integer codigo) throws SQLException {
        Cursor pyts = null;
        ArrayList<PesoyTalla> mPesoyTallas = new ArrayList<PesoyTalla>();
        pyts = mDb.query(true, ConstantsDB.PT_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (pyts != null && pyts.getCount() > 0) {
            pyts.moveToFirst();
            mPesoyTallas.clear();
            do{
                mPesoyTallas.add(crearPesoyTalla(pyts));
            } while (pyts.moveToNext());
        }
        pyts.close();
        return mPesoyTallas;
    }

    public ArrayList<PesoyTalla> getListaPesoyTallas() throws SQLException {
        Cursor pyts = null;
        ArrayList<PesoyTalla> mPesoyTallas = new ArrayList<PesoyTalla>();
        pyts = mDb.query(true, ConstantsDB.PT_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (pyts != null && pyts.getCount() > 0) {
            pyts.moveToFirst();
            mPesoyTallas.clear();
            do{
                mPesoyTallas.add(crearPesoyTalla(pyts));
            } while (pyts.moveToNext());
        }
        pyts.close();
        return mPesoyTallas;
    }

    public ArrayList<PesoyTalla> getListaPesoyTallasHoy() throws SQLException {
        Cursor pyts = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<PesoyTalla> mPesoyTallas = new ArrayList<PesoyTalla>();
        pyts = mDb.query(true, ConstantsDB.PT_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (pyts != null && pyts.getCount() > 0) {
            pyts.moveToFirst();
            mPesoyTallas.clear();
            do{
                mPesoyTallas.add(crearPesoyTalla(pyts));
            } while (pyts.moveToNext());
        }
        pyts.close();
        return mPesoyTallas;
    }


    /**
     * Crea un PesoyTalla
     *
     * @return PesoyTalla
     */
    public PesoyTalla crearPesoyTalla(Cursor pesajes){
        PesoyTalla mPyT = new PesoyTalla();
        Date fecha = new Date(pesajes.getLong(pesajes.getColumnIndex(ConstantsDB.TODAY)));
        PesoyTallaId pytId = new PesoyTallaId();
        pytId.setCodigo(pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.CODIGO)));
        pytId.setFechaPT(new Date(pesajes.getLong(pesajes.getColumnIndex(ConstantsDB.FECHA_PT))));

        mPyT.setPtId(pytId);

        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.PESO1))) mPyT.setPeso1(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.PESO1)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.PESO2))) mPyT.setPeso2(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.PESO2)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.PESO3))) mPyT.setPeso3(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.PESO3)));

        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.TALLA1))) mPyT.setTalla1(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.TALLA1)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.TALLA2))) mPyT.setTalla2(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.TALLA2)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.TALLA3))) mPyT.setTalla3(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.TALLA3)));

        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.IMC1))) mPyT.setImc1(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.IMC1)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.IMC2))) mPyT.setImc2(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.IMC2)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.IMC3))) mPyT.setImc3(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.IMC3)));

        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.DIFPESO))) mPyT.setDifPeso(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.DIFPESO)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.DIFTALLA))) mPyT.setDifTalla(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.DIFTALLA)));

        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.otrorecurso1))) mPyT.setOtrorecurso1(pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.otrorecurso2))) mPyT.setOtrorecurso2(pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.otrorecurso2)));

        Boolean borrado = pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.DELETED))>0;
        mPyT.setMovilInfo(new MovilInfo(pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.FILE_PATH)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.STATUS)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.START)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.END)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.DEVICE_ID)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.REC1)),
                pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.REC2))));
        return mPyT;
    }



    /**
     * Obtiene Lista todas las muestras sin enviar
     *
     * @return lista con Muestra
     */
    public List<Muestra> getListaMuestrasSinEnviar() throws SQLException {
        Cursor muestreo = null;
        List<Muestra> mMuestras = new ArrayList<Muestra>();
        muestreo = mDb.query(true, ConstantsDB.MUESTRA_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (muestreo != null && muestreo.getCount() > 0) {
            muestreo.moveToFirst();
            mMuestras.clear();
            do{
                mMuestras.add(crearMuestra(muestreo));
            } while (muestreo.moveToNext());
        }
        muestreo.close();
        return mMuestras;
    }

    /**
     * Obtiene Lista todas los Muestra de un codigo
     *
     * @return lista con Muestra
     */
    public ArrayList<Muestra> getListaMuestras(Integer codigo) throws SQLException {
        Cursor muestras = null;
        ArrayList<Muestra> mMuestras = new ArrayList<Muestra>();
        muestras = mDb.query(true, ConstantsDB.MUESTRA_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (muestras != null && muestras.getCount() > 0) {
            muestras.moveToFirst();
            mMuestras.clear();
            do{
                mMuestras.add(crearMuestra(muestras));
            } while (muestras.moveToNext());
        }
        muestras.close();
        return mMuestras;
    }


    public ArrayList<Muestra> getListaMuestras() throws SQLException {
        Cursor muestras = null;
        ArrayList<Muestra> mMuestras = new ArrayList<Muestra>();
        muestras = mDb.query(true, ConstantsDB.MUESTRA_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (muestras != null && muestras.getCount() > 0) {
            muestras.moveToFirst();
            mMuestras.clear();
            do{
                mMuestras.add(crearMuestra(muestras));
            } while (muestras.moveToNext());
        }
        muestras.close();
        return mMuestras;
    }

    public ArrayList<Muestra> getListaMuestrasHoy() throws SQLException {
        Cursor muestras = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<Muestra> mMuestras = new ArrayList<Muestra>();
        muestras = mDb.query(true, ConstantsDB.MUESTRA_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (muestras != null && muestras.getCount() > 0) {
            muestras.moveToFirst();
            mMuestras.clear();
            do{
                mMuestras.add(crearMuestra(muestras));
            } while (muestras.moveToNext());
        }
        muestras.close();
        return mMuestras;
    }

    public ArrayList<DatosPartoBB> getListaDatosPartoBBHoy() throws SQLException {
        Cursor datos = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<DatosPartoBB> mDatosPartoBBs = new ArrayList<DatosPartoBB>();
        datos = mDb.query(true, ConstantsDB.DATOSPARTOBB_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (datos != null && datos.getCount() > 0) {
            datos.moveToFirst();
            mDatosPartoBBs.clear();
            do{
                mDatosPartoBBs.add(crearDatosPartoBB(datos));
            } while (datos.moveToNext());
        }
        datos.close();
        return mDatosPartoBBs;
    }

    public ArrayList<Documentos> getListaDocumentosHoy() throws SQLException {
        Cursor docs = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<Documentos> mDocumentos = new ArrayList<Documentos>();
        docs = mDb.query(true, ConstantsDB.DOCS_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (docs != null && docs.getCount() > 0) {
            docs.moveToFirst();
            mDocumentos.clear();
            do{
                mDocumentos.add(DocumentosHelper.crearDocumentos2(docs));
            } while (docs.moveToNext());
        }
        docs.close();
        return mDocumentos;
    }

    /**
     * Crea una Muestra
     *
     * @return Muestra
     */
    public Muestra crearMuestra(Cursor muestreo){
        Date fecha = new Date(muestreo.getLong(muestreo.getColumnIndex(ConstantsDB.TODAY)));
        Muestra mMuestra = new Muestra();
        MuestraId muestraId = new MuestraId();
        muestraId.setCodigo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.CODIGO)));
        muestraId.setFechaMuestra(new Date(muestreo.getLong(muestreo.getColumnIndex(ConstantsDB.FECHA_MUESTRA))));

        mMuestra.setmId(muestraId);

        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.MFIEBRE))) mMuestra.setFiebreM(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.MFIEBRE)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.CONSULTA))) mMuestra.setConsulta(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.CONSULTA)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.BHC))) mMuestra.setTuboBHC(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.BHC)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.ROJO))) mMuestra.setTuboRojo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.ROJO)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.LEU))) mMuestra.setTuboLeu(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.LEU)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.PIN))) mMuestra.setPinchazos(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.PIN)));

        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.bhc_razonNo))) mMuestra.setBhc_razonNo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.bhc_razonNo)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.rojo_razonNo))) mMuestra.setRojo_razonNo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.rojo_razonNo)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.pbmc_razonNo))) mMuestra.setPbmc_razonNo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.pbmc_razonNo)));

        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.bhc_otraRazonNo))) mMuestra.setBhc_otraRazonNo(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.bhc_otraRazonNo)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.rojo_otraRazonNo))) mMuestra.setRojo_otraRazonNo(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.rojo_otraRazonNo)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.pbmc_otraRazonNo))) mMuestra.setPbmc_otraRazonNo(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.pbmc_otraRazonNo)));

        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.horaBHC))) mMuestra.setHoraBHC(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.horaBHC)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.horaPBMC))) mMuestra.setHoraPBMC(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.horaPBMC)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.horaInicioPax))) mMuestra.setHoraInicioPax(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.horaInicioPax)));

        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.horaFinPax))) mMuestra.setHoraFinPax(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.horaFinPax)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.codPax))) mMuestra.setCodPax(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.codPax)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.terreno))) mMuestra.setTerreno(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.terreno)));

        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.otrorecurso1))) mMuestra.setOtrorecurso1(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.otrorecurso2))) mMuestra.setOtrorecurso2(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.otrorecurso2)));

        Boolean borrado = muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.DELETED))>0;
        mMuestra.setMovilInfo(new MovilInfo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.FILE_PATH)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.STATUS)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.START)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.END)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.DEVICE_ID)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.REC1)),
                muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.REC2))));
        return mMuestra;
    }


    /**
     * Obtiene Lista todas los obsequios sin enviar
     *
     * @return lista con Obsequio
     */
    public List<Obsequio> getListaObsequiosSinEnviar() throws SQLException {
        Cursor obsentregados = null;
        List<Obsequio> mObsequios = new ArrayList<Obsequio>();
        obsentregados = mDb.query(true, ConstantsDB.OB_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (obsentregados != null && obsentregados.getCount() > 0) {
            obsentregados.moveToFirst();
            mObsequios.clear();
            do{
                mObsequios.add(crearObsequio(obsentregados));
            } while (obsentregados.moveToNext());
        }
        obsentregados.close();
        return mObsequios;
    }

    /**
     * Obtiene Lista todas los Obsequio de un codigo
     *
     * @return lista con Obsequio
     */
    public ArrayList<Obsequio> getListaObsequios(Integer codigo) throws SQLException {
        Cursor obsequios = null;
        ArrayList<Obsequio> mObsequios = new ArrayList<Obsequio>();
        obsequios = mDb.query(true, ConstantsDB.OB_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (obsequios != null && obsequios.getCount() > 0) {
            obsequios.moveToFirst();
            mObsequios.clear();
            do{
                mObsequios.add(crearObsequio(obsequios));
            } while (obsequios.moveToNext());
        }
        obsequios.close();
        return mObsequios;
    }



    public ArrayList<Obsequio> getListaObsequios() throws SQLException {
        Cursor obsequios = null;
        ArrayList<Obsequio> mObsequios = new ArrayList<Obsequio>();
        obsequios = mDb.query(true, ConstantsDB.OB_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (obsequios != null && obsequios.getCount() > 0) {
            obsequios.moveToFirst();
            mObsequios.clear();
            do{
                mObsequios.add(crearObsequio(obsequios));
            } while (obsequios.moveToNext());
        }
        obsequios.close();
        return mObsequios;
    }

    public ArrayList<Obsequio> getListaObsequiosHoy() throws SQLException {
        Cursor obsequios = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<Obsequio> mObsequios = new ArrayList<Obsequio>();
        obsequios = mDb.query(true, ConstantsDB.OB_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (obsequios != null && obsequios.getCount() > 0) {
            obsequios.moveToFirst();
            mObsequios.clear();
            do{
                mObsequios.add(crearObsequio(obsequios));
            } while (obsequios.moveToNext());
        }
        obsequios.close();
        return mObsequios;
    }


    public ArrayList<EncuestaSatisfaccion> getEncuestaSatisfaccionHoy() throws SQLException {
        Cursor encuestas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<EncuestaSatisfaccion> mEncuestas = new ArrayList<EncuestaSatisfaccion>();
        encuestas = mDb.query(true, ConstantsDB.ENC_SAT_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null,ConstantsDB.TODAY, null);
        if (encuestas != null && encuestas.getCount() > 0) {
            encuestas.moveToFirst();
            mEncuestas.clear();
            do{
                mEncuestas.add(crearEncuestaSatisfaccion(encuestas));
            } while (encuestas.moveToNext());
        }
        encuestas.close();
        return mEncuestas;
    }

    /**
     * Crea un Obsequio
     *
     * @return Obsequio
     */
    public Obsequio crearObsequio(Cursor entregas){
        Date fecha = new Date(entregas.getLong(entregas.getColumnIndex(ConstantsDB.TODAY)));
        Obsequio mObsequio = new Obsequio();
        ObsequioId obseId = new ObsequioId();
        obseId.setCodigo(entregas.getInt(entregas.getColumnIndex(ConstantsDB.CODIGO)));
        obseId.setFechaEntrega(new Date(entregas.getLong(entregas.getColumnIndex(ConstantsDB.FECHA_OB))));
        mObsequio.setObId(obseId);
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.OBSEQ))) mObsequio.setObseqSN(entregas.getInt(entregas.getColumnIndex(ConstantsDB.OBSEQ)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.CARNET))) mObsequio.setCarnetSN(entregas.getInt(entregas.getColumnIndex(ConstantsDB.CARNET)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.PERRETIRA))) mObsequio.setPersRecCarnet(entregas.getString(entregas.getColumnIndex(ConstantsDB.PERRETIRA)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.PERRETIRAREL))) mObsequio.setRelacionFam(entregas.getInt(entregas.getColumnIndex(ConstantsDB.PERRETIRAREL)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.PERRETIRAOREL))) mObsequio.setOtroRelacionFam(entregas.getString(entregas.getColumnIndex(ConstantsDB.PERRETIRAOREL)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.OBS_TEL))) mObsequio.setTelefono(entregas.getInt(entregas.getColumnIndex(ConstantsDB.OBS_TEL)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.CDOM))) mObsequio.setCmDomicilio(entregas.getInt(entregas.getColumnIndex(ConstantsDB.CDOM)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.BARRIO))) mObsequio.setBarrio(entregas.getInt(entregas.getColumnIndex(ConstantsDB.BARRIO)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.DIRECCION))) mObsequio.setDire(entregas.getString(entregas.getColumnIndex(ConstantsDB.DIRECCION)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.OBS))) mObsequio.setObservaciones(entregas.getString(entregas.getColumnIndex(ConstantsDB.OBS)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.otrorecurso1))) mObsequio.setOtrorecurso1(entregas.getInt(entregas.getColumnIndex(ConstantsDB.otrorecurso1)));
        Boolean borrado = entregas.getInt(entregas.getColumnIndex(ConstantsDB.DELETED))>0;
        mObsequio.setMovilInfo(new MovilInfo(entregas.getInt(entregas.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.FILE_PATH)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.STATUS)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.START)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.END)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.DEVICE_ID)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                entregas.getString(entregas.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                entregas.getInt(entregas.getColumnIndex(ConstantsDB.REC1)),
                entregas.getInt(entregas.getColumnIndex(ConstantsDB.REC2))));
        return mObsequio;
    }


    /**
     * Obtiene Lista todas las vacunas sin enviar
     *
     * @return lista con Vacuna
     */
    public List<Vacuna> getListaVacunasSinEnviar() throws SQLException {
        Cursor vacunas = null;
        List<Vacuna> mVacunas = new ArrayList<Vacuna>();
        vacunas = mDb.query(true, ConstantsDB.VAC_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (vacunas != null && vacunas.getCount() > 0) {
            vacunas.moveToFirst();
            mVacunas.clear();
            do{
                mVacunas.add(crearVacuna(vacunas));
            } while (vacunas.moveToNext());
        }
        vacunas.close();
        return mVacunas;
    }

    /**
     * Obtiene Lista todas las vacunas sin enviar
     *
     * @return lista con Vacuna
     */
    public List<NewVacuna> getListaNewVacunasSinEnviar() throws SQLException {
        Cursor vacunas = null;
        List<NewVacuna> mNewVacunas = new ArrayList<NewVacuna>();
        vacunas = mDb.query(true, ConstantsDB.NEWVAC_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (vacunas != null && vacunas.getCount() > 0) {
            vacunas.moveToFirst();
            mNewVacunas.clear();
            do{
                mNewVacunas.add(crearNewVacuna(vacunas));
            } while (vacunas.moveToNext());
        }
        vacunas.close();
        return mNewVacunas;
    }


    /**
     * Obtiene Lista todas las DatosPartoBB sin enviar
     *
     * @return lista con DatosPartoBB
     */
    public List<DatosPartoBB> getListaDatosPartoBBSinEnviar() throws SQLException {
        Cursor datosPartoBB = null;
        List<DatosPartoBB> mDatosPartoBB = new ArrayList<DatosPartoBB>();
        datosPartoBB = mDb.query(true, ConstantsDB.DATOSPARTOBB_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (datosPartoBB != null && datosPartoBB.getCount() > 0) {
            datosPartoBB.moveToFirst();
            mDatosPartoBB.clear();
            do{
                mDatosPartoBB.add(crearDatosPartoBB(datosPartoBB));
            } while (datosPartoBB.moveToNext());
        }
        datosPartoBB.close();
        return mDatosPartoBB;
    }

    /**
     * Obtiene Lista todas los Vacunas de un codigo
     *
     * @return lista con Vacuna
     */
    public ArrayList<Vacuna> getListaVacunas(Integer codigo) throws SQLException {
        Cursor vacunas = null;
        ArrayList<Vacuna> mVacunas = new ArrayList<Vacuna>();
        vacunas = mDb.query(true, ConstantsDB.VAC_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (vacunas != null && vacunas.getCount() > 0) {
            vacunas.moveToFirst();
            mVacunas.clear();
            do{
                mVacunas.add(crearVacuna(vacunas));
            } while (vacunas.moveToNext());
        }
        vacunas.close();
        return mVacunas;
    }

    public ArrayList<Vacuna> getListaVacunas() throws SQLException {
        Cursor vacunas = null;
        ArrayList<Vacuna> mVacunas = new ArrayList<Vacuna>();
        vacunas = mDb.query(true, ConstantsDB.VAC_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (vacunas != null && vacunas.getCount() > 0) {
            vacunas.moveToFirst();
            mVacunas.clear();
            do{
                mVacunas.add(crearVacuna(vacunas));
            } while (vacunas.moveToNext());
        }
        vacunas.close();
        return mVacunas;
    }

    public ArrayList<Vacuna> getListaVacunasHoy() throws SQLException {
        Cursor vacunas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<Vacuna> mVacunas = new ArrayList<Vacuna>();
        vacunas = mDb.query(true, ConstantsDB.VAC_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (vacunas != null && vacunas.getCount() > 0) {
            vacunas.moveToFirst();
            mVacunas.clear();
            do{
                mVacunas.add(crearVacuna(vacunas));
            } while (vacunas.moveToNext());
        }
        vacunas.close();
        return mVacunas;
    }


    public ArrayList<NewVacuna> getListaNewVacunasHoy() throws SQLException {
        Cursor vacunas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<NewVacuna> mVacunas = new ArrayList<NewVacuna>();
        vacunas = mDb.query(true, ConstantsDB.NEWVAC_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (vacunas != null && vacunas.getCount() > 0) {
            vacunas.moveToFirst();
            mVacunas.clear();
            do{
                mVacunas.add(crearNewVacuna(vacunas));
            } while (vacunas.moveToNext());
        }
        vacunas.close();
        return mVacunas;
    }

    /**
     * Crea una Vacuna
     *
     * @return Vacuna
     */
    public Vacuna crearVacuna(Cursor vacunacion){
        Date fecha = new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.TODAY)));
        Vacuna mVac = new Vacuna();
        VacunaId lacMatId = new VacunaId();
        lacMatId.setCodigo(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.CODIGO)));
        lacMatId.setFechaVacuna(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHA_VACUNA))));

        mVac.setVacunaId(lacMatId);
        mVac.setVacuna(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.VACUNA)));
        if(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FV))>0) mVac.setFechaVac(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FV))));
        mVac.setTipovacuna(vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.TIPOVAC)));
        if(!vacunacion.isNull(vacunacion.getColumnIndex(ConstantsDB.TARJETA))) mVac.setTarjetaSN(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.TARJETA)));
        if(!vacunacion.isNull(vacunacion.getColumnIndex(ConstantsDB.NDOSIS))) mVac.setNdosis(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.NDOSIS)));
        if(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF1))>0) mVac.setFechaInf1(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF1))));
        if(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF2))>0) mVac.setFechaInf2(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF2))));
        if(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF3))>0) mVac.setFechaInf3(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF3))));
        if(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF4))>0) mVac.setFechaInf4(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF4))));
        if(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF5))>0) mVac.setFechaInf5(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF5))));
        if(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF6))>0) mVac.setFechaInf6(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF6))));
        if(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF7))>0) mVac.setFechaInf7(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF7))));
        if(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF8))>0) mVac.setFechaInf8(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF8))));
        if(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF9))>0) mVac.setFechaInf9(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF9))));
        if(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF10))>0) mVac.setFechaInf10(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.FECHAINF10))));
        if(!vacunacion.isNull(vacunacion.getColumnIndex(ConstantsDB.otrorecurso1))) mVac.setOtrorecurso1(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.otrorecurso1)));
        Boolean borrado = vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.DELETED))>0;
        mVac.setMovilInfo(new MovilInfo(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.FILE_PATH)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.STATUS)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.START)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.END)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.DEVICE_ID)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.REC1)),
                vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.REC2))));
        return mVac;
    }


    /**
     * Crea una NewVacuna
     *
     * @return NewVacuna
     */
    public NewVacuna crearNewVacuna(Cursor vacunacion){
        Date fecha = new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.TODAY)));
        NewVacuna mVac = new NewVacuna();
        NewVacunaId vacunaId = new NewVacunaId();
        vacunaId.setCodigo(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.CODIGO)));
        vacunaId.setFechaRegistroVacuna(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.fechaRegistroVacuna))));

        mVac.setVacunaId(vacunaId);
        mVac.setVacuna_sn(vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.vacuna_sn)));
        mVac.setTvacunano(vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.tvacunano)));
        mVac.setOtroMotivoTvacunano(vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.otroMotivoTvacunano)));
        if(!vacunacion.isNull(vacunacion.getColumnIndex(ConstantsDB.otrorecurso1))) mVac.setOtrorecurso1(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.otrorecurso1)));
        Boolean borrado = vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.DELETED))>0;
        mVac.setMovilInfo(new MovilInfo(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.FILE_PATH)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.STATUS)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.START)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.END)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.DEVICE_ID)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.REC1)),
                vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.REC2))));
        return mVac;
    }


    /**
     * Crea una DatosPartoBB
     *
     * @return DatosPartoBB
     */
    public DatosPartoBB crearDatosPartoBB(Cursor cursorDatosPartoBB){
        DatosPartoBB mDatosPartoBB = new DatosPartoBB();
        DatosPartoBBId mDatosPartoBBId = new DatosPartoBBId();
        mDatosPartoBBId.setCodigo(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.codigo)));
        mDatosPartoBBId.setFechaDatosParto(new Date(cursorDatosPartoBB.getLong(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fechaDatosParto))));
        mDatosPartoBB.setDatosPartoId(mDatosPartoBBId);
        mDatosPartoBB.setTipoParto(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tipoParto)));
        mDatosPartoBB.setTiempoEmb_sndr(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tiempoEmb_sndr)));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tiempoEmbSemana))) mDatosPartoBB.setTiempoEmbSemana(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tiempoEmbSemana)));
        mDatosPartoBB.setDocMedTiempoEmb_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedTiempoEmb_sn)));
        mDatosPartoBB.setDocMedTiempoEmb(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedTiempoEmb)));
        mDatosPartoBB.setOtroDocMedTiempoEmb(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otroDocMedTiempoEmb)));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fum))) mDatosPartoBB.setFum(new Date(cursorDatosPartoBB.getLong(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fum))));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.sg))) mDatosPartoBB.setSg(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.sg)));
        mDatosPartoBB.setFumFueraRango_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fumFueraRango_sn)));
        mDatosPartoBB.setFumFueraRango_razon(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fumFueraRango_razon)));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.edadGest))) mDatosPartoBB.setEdadGest(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.edadGest)));
        mDatosPartoBB.setDocMedEdadGest_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedEdadGest_sn)));
        mDatosPartoBB.setDocMedEdadGest(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedEdadGest)));
        mDatosPartoBB.setOtroDocMedEdadGest(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.OtroDocMedEdadGest)));
        mDatosPartoBB.setPrematuro_sndr(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.prematuro_sndr)));
        mDatosPartoBB.setPesoBB_sndr(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.pesoBB_sndr)));
        mDatosPartoBB.setPesoBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.pesoBB)));
        mDatosPartoBB.setDocMedPesoBB_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedPesoBB_sn)));
        mDatosPartoBB.setDocMedPesoBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedPesoBB)));
        mDatosPartoBB.setOtroDocMedPesoBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otroDocMedPesoBB)));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso1))) mDatosPartoBB.setOtrorecurso1(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso2))) mDatosPartoBB.setOtrorecurso2(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso2)));
        Date fecha = new Date(cursorDatosPartoBB.getLong(cursorDatosPartoBB.getColumnIndex(ConstantsDB.TODAY)));
        Boolean borrado = cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.DELETED))>0;
        mDatosPartoBB.setMovilInfo(new MovilInfo(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.FILE_PATH)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.STATUS)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.START)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.END)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.DEVICE_ID)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.REC1)),
                cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.REC2))));
        return mDatosPartoBB;
    }


    /**
     * Obtiene Lista todas las visitas sin enviar
     *
     * @return lista con VisitaTerreno
     */
    public List<VisitaTerreno> getListaVisitaTerrenosSinEnviar() throws SQLException {
        Cursor visitas = null;
        List<VisitaTerreno> mVisitaTerrenos = new ArrayList<VisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.VIS_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }

    /**
     * Obtiene Lista todas los VisitaTerreno de un codigo
     *
     * @return lista con VisitaTerreno
     */
    public ArrayList<VisitaTerreno> getListaVisitaTerreno(Integer codigo) throws SQLException {
        Cursor visitas = null;
        ArrayList<VisitaTerreno> mVisitaTerrenos = new ArrayList<VisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.VIS_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }


    public ArrayList<VisitaTerreno> getListaVisitaTerreno() throws SQLException {
        Cursor visitas = null;
        ArrayList<VisitaTerreno> mVisitaTerrenos = new ArrayList<VisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.VIS_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }

    public ArrayList<VisitaTerreno> getListaVisitaTerrenoHoy() throws SQLException {
        Cursor visitas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<VisitaTerreno> mVisitaTerrenos = new ArrayList<VisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.VIS_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }

    /**
     * Obtiene Lista todas las visitas sin enviar
     *
     * @return lista con VisitaTerreno
     */
    public List<DatosVisitaTerreno> getListaDatosVisitaTerrenosSinEnviar() throws SQLException {
        Cursor visitas = null;
        List<DatosVisitaTerreno> mVisitaTerrenos = new ArrayList<DatosVisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.DAT_VIS_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearDatosVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }


    /**
     * Obtiene Lista todas los DatosVisitaTerreno de un codigo
     *
     * @return lista con DatosVisitaTerreno
     */
    public ArrayList<DatosVisitaTerreno> getListaDatosVisitaTerreno(Integer codigo) throws SQLException {
        Cursor visitas = null;
        ArrayList<DatosVisitaTerreno> mVisitaTerrenos = new ArrayList<DatosVisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.DAT_VIS_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearDatosVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }


    public ArrayList<DatosVisitaTerreno> getListaDatosVisitaTerreno() throws SQLException {
        Cursor visitas = null;
        ArrayList<DatosVisitaTerreno> mVisitaTerrenos = new ArrayList<DatosVisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.DAT_VIS_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearDatosVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }

    public ArrayList<DatosVisitaTerreno> getListaDatosVisitaTerrenoHoy() throws SQLException {
        Cursor visitas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<DatosVisitaTerreno> mVisitaTerrenos = new ArrayList<DatosVisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.DAT_VIS_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearDatosVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }

    /**
     * Crea una VisitaTerreno
     *
     * @return VisitaTerreno
     */
    public VisitaTerreno crearVisitaTerreno(Cursor visitascampo){
        Date fecha = new Date(visitascampo.getLong(visitascampo.getColumnIndex(ConstantsDB.TODAY)));
        VisitaTerreno mVisitaCasa = new VisitaTerreno();
        VisitaTerrenoId visitasId = new VisitaTerrenoId();
        visitasId.setCodigo(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.CODIGO)));
        visitasId.setFechaVisita(new Date(visitascampo.getLong(visitascampo.getColumnIndex(ConstantsDB.FECHA_VISITA))));
        mVisitaCasa.setVisitaId(visitasId);
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.VISITASN))) mVisitaCasa.setVisitaSN(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.VISITASN)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.MOTNOVIS))) mVisitaCasa.setMotNoVisita(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.MOTNOVIS)));
        mVisitaCasa.setAcomp(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.ACOMP_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.REL_VIS))) mVisitaCasa.setRelacionFam(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.REL_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.ASENT_VIS))) mVisitaCasa.setAsentimiento(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.ASENT_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.otrorecurso1))) mVisitaCasa.setOtrorecurso1(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.otrorecurso2))) mVisitaCasa.setOtrorecurso2(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.otrorecurso2)));
        mVisitaCasa.setOtraRelacionFam(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.otraRelacionFam)));
        mVisitaCasa.setCarnetSN(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.carnetSN)));
        Boolean borrado = visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.DELETED))>0;
        mVisitaCasa.setMovilInfo(new MovilInfo(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.FILE_PATH)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.STATUS)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.START)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.END)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.DEVICE_ID)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.REC1)),
                visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.REC2))));
        return mVisitaCasa;
    }

    /**
     * Crea una DatosVisitaTerreno
     *
     * @return DatosVisitaTerreno
     */
    public DatosVisitaTerreno crearDatosVisitaTerreno(Cursor visitascampo){
        Date fecha = new Date(visitascampo.getLong(visitascampo.getColumnIndex(ConstantsDB.TODAY)));
        DatosVisitaTerreno mVisitaCasa = new DatosVisitaTerreno();
        DatosVisitaTerrenoId visitasId = new DatosVisitaTerrenoId();
        visitasId.setCodigo(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.CODIGO)));
        visitasId.setFechaVisita(new Date(visitascampo.getLong(visitascampo.getColumnIndex(ConstantsDB.FECHA_VISITA))));

        mVisitaCasa.setVisitaId(visitasId);
        mVisitaCasa.setCodCasa(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.COD_CASA)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.CDOM_VIS))) mVisitaCasa.setcDom(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.CDOM_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.BARRIO_VIS))) mVisitaCasa.setBarrio(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.BARRIO_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.MANZ_VIS)))mVisitaCasa.setManzana(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.MANZ_VIS)));
        mVisitaCasa.setDireccion(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.DIRE_VIS)));
        mVisitaCasa.setCoordenadas(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.COORD_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.LAT_VIS))) mVisitaCasa.setLatitud(visitascampo.getDouble(visitascampo.getColumnIndex(ConstantsDB.LAT_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.LON_VIS))) mVisitaCasa.setLongitud(visitascampo.getDouble(visitascampo.getColumnIndex(ConstantsDB.LON_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.otrorecurso1))) mVisitaCasa.setOtrorecurso1(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.otrorecurso2))) mVisitaCasa.setOtrorecurso2(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.otrorecurso2)));
        mVisitaCasa.setTelefonoClasif1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoClasif1)));
        mVisitaCasa.setTelefonoConv1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoConv1)));
        mVisitaCasa.setTelefonoCel1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoCel1)));
        mVisitaCasa.setTelefonoEmpresa1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoEmpresa1)));
        mVisitaCasa.setTelefono2SN(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefono2SN)));
        mVisitaCasa.setTelefonoClasif2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoClasif2)));
        mVisitaCasa.setTelefonoConv2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoConv2)));
        mVisitaCasa.setTelefonoCel2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoCel2)));
        mVisitaCasa.setTelefonoEmpresa2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoEmpresa2)));
        mVisitaCasa.setTelefono3SN(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefono3SN)));
        mVisitaCasa.setTelefonoClasif3(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoClasif3)));
        mVisitaCasa.setTelefonoConv3(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoConv3)));
        mVisitaCasa.setTelefonoCel3(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoCel3)));
        mVisitaCasa.setTelefonoEmpresa3(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoEmpresa3)));
        mVisitaCasa.setTelefono4SN(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefono4SN)));
        mVisitaCasa.setTelefonoClasif4(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoClasif4)));
        mVisitaCasa.setTelefonoConv4(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoConv4)));
        mVisitaCasa.setTelefonoCel4(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoCel4)));
        mVisitaCasa.setTelefonoEmpresa4(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoEmpresa4)));
        mVisitaCasa.setCandidatoNI(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.candidatoNI)));
        mVisitaCasa.setNombreCandNI1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.nombreCandNI1)));
        mVisitaCasa.setNombreCandNI2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.nombreCandNI2)));
        mVisitaCasa.setApellidoCandNI1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.apellidoCandNI1)));
        mVisitaCasa.setApellidoCandNI2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.apellidoCandNI2)));
        mVisitaCasa.setNombreptTutorCandNI(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.nombreptTutorCandNI)));
        mVisitaCasa.setNombreptTutorCandNI2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.nombreptTutorCandNI2)));
        mVisitaCasa.setApellidoptTutorCandNI(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.apellidoptTutorCandNI)));
        mVisitaCasa.setApellidoptTutorCandNI2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.apellidoptTutorCandNI2)));
        mVisitaCasa.setRelacionFamCandNI(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.relacionFamCandNI)));
        mVisitaCasa.setOtraRelacionFamCandNI(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.otraRelacionFamCandNI)));
        Boolean borrado = visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.DELETED))>0;
        mVisitaCasa.setMovilInfo(new MovilInfo(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.FILE_PATH)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.STATUS)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.START)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.END)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.DEVICE_ID)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.REC1)),
                visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.REC2))));
        return mVisitaCasa;
    }


    /**
     * Obtiene Lista todas las recons sin enviar
     *
     * @return lista con ReconsentimientoDen
     */
    public List<ReConsentimientoDen> getListaReConsentimientoDensSinEnviar() throws SQLException {
        Cursor recons = null;
        List<ReConsentimientoDen> mRecons = new ArrayList<ReConsentimientoDen>();
        recons = mDb.query(true, ConstantsDB.RECONS_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mRecons.clear();
            do{
                mRecons.add(crearReConsentimientoDen(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mRecons;
    }

    /**
     * Obtiene Lista todas los ReConsentimientoDen de un codigo
     *
     * @return lista con ReConsentimientoDen
     */
    public ArrayList<ReConsentimientoDen> getListaReConsentimientoDen(Integer codigo) throws SQLException {
        Cursor recons = null;
        ArrayList<ReConsentimientoDen> mReConsentimientoDen = new ArrayList<ReConsentimientoDen>();
        recons = mDb.query(true, ConstantsDB.RECONS_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mReConsentimientoDen.clear();
            do{
                mReConsentimientoDen.add(crearReConsentimientoDen(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mReConsentimientoDen;
    }

    /**
     * Obtiene Lista todas los ReConsentimientoDen de un codigo
     *
     * @return lista con ReConsentimientoDen
     */
    public ArrayList<ReConsentimientoDen> getListaReConsentimientoDen() throws SQLException {
        Cursor recons = null;
        ArrayList<ReConsentimientoDen> mReConsentimientoDen = new ArrayList<ReConsentimientoDen>();
        recons = mDb.query(true, ConstantsDB.RECONS_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mReConsentimientoDen.clear();
            do{
                mReConsentimientoDen.add(crearReConsentimientoDen(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mReConsentimientoDen;
    }

    public ArrayList<ReConsentimientoDen> getListaReConsentimientoDenHoy() throws SQLException {
        Cursor recons = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<ReConsentimientoDen> mReConsentimientoDen = new ArrayList<ReConsentimientoDen>();
        recons = mDb.query(true, ConstantsDB.RECONS_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mReConsentimientoDen.clear();
            do{
                mReConsentimientoDen.add(crearReConsentimientoDen(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mReConsentimientoDen;
    }



    /**
     * Crea una ReConsentimientoDen
     *
     * @return ReConsentimientoDen
     */
    public ReConsentimientoDen crearReConsentimientoDen(Cursor reconsens){
        ReConsentimientoDen mReConsentimiento = new ReConsentimientoDen();
        Date fecha = new Date(reconsens.getLong(reconsens.getColumnIndex(ConstantsDB.TODAY)));
        ReConsentimientoDenId reconsId = new ReConsentimientoDenId();
        reconsId.setCodigo(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.CODIGO)));
        reconsId.setFechaCons(new Date(reconsens.getLong(reconsens.getColumnIndex(ConstantsDB.FECHA_CONS))));
        mReConsentimiento.setReconsdenId(reconsId);
        mReConsentimiento.setAutsup(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.autsup)));
        mReConsentimiento.setParteADen(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.parteaden)));
        mReConsentimiento.setRechazoDen(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.rechazoden)));
        mReConsentimiento.setOtroRechazoDen(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.otrorechazoden)));
        mReConsentimiento.setIncDen(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.incden)));
        mReConsentimiento.setAutsup2(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.autsup2)));
        mReConsentimiento.setExcDen(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.excden)));
        mReConsentimiento.setEnfCronSN(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.enfcronsn)));
        mReConsentimiento.setEnfCronica(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.enfCronica)));
        mReConsentimiento.setoEnfCronica(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.oEnfCronica)));
        mReConsentimiento.setTomaTx(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.tomatx)));
        mReConsentimiento.setCualesTx(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.cualestx)));
        mReConsentimiento.setAutsup3(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.autsup3)));
        mReConsentimiento.setCmDomicilio(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.cmdomicilio)));
        mReConsentimiento.setBarrio(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.barrio)));
        mReConsentimiento.setAutsup4(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.autsup4)));
        mReConsentimiento.setDire(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.dire)));
        mReConsentimiento.setManzana(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.manzana)));
        mReConsentimiento.setTelefono(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefono)));
        mReConsentimiento.setAsentimiento(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.asentimiento)));
        mReConsentimiento.setParteBDen(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.partebden)));
        mReConsentimiento.setParteCDen(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.partecden)));
        mReConsentimiento.setParteDDen(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.partedden)));
        mReConsentimiento.setAsentimientoesc(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.asentimientoesc)));
        mReConsentimiento.setParteEDen(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.parteeden)));
        mReConsentimiento.setFirmcarta(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.firmcarta)));
        mReConsentimiento.setRelacionFam(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.relacionfam)));
        mReConsentimiento.setCoordenadas(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.coordenadas)));
        Boolean borrado = reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.DELETED))>0;
        mReConsentimiento.setMovilInfo(new MovilInfo(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.FILE_PATH)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.STATUS)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.START)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.END)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.DEVICE_ID)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.REC1)),
                reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.REC2))));
        return mReConsentimiento;
    }


    /**
     * Obtiene Lista todas las conschik sin enviar
     *
     * @return lista con ConsentimientoChik
     */
    public List<ConsentimientoChik> getListaConsentimientoChikSinEnviar() throws SQLException {
        Cursor conschik = null;
        List<ConsentimientoChik> mConsChik = new ArrayList<ConsentimientoChik>();
        conschik = mDb.query(true, ConstantsDB.CONSCHIK_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (conschik != null && conschik.getCount() > 0) {
            conschik.moveToFirst();
            mConsChik.clear();
            do{
                mConsChik.add(crearConsentimientoChik(conschik));
            } while (conschik.moveToNext());
        }
        conschik.close();
        return mConsChik;
    }

    /**
     * Obtiene Lista todas los ConsentimientoChik de un codigo
     *
     * @return lista con ConsentimientoChik
     */
    public ArrayList<ConsentimientoChik> getListaConsentimientoChik(Integer codigo) throws SQLException {
        Cursor conschik = null;
        ArrayList<ConsentimientoChik> mConsentimientoChik = new ArrayList<ConsentimientoChik>();
        conschik = mDb.query(true, ConstantsDB.CONSCHIK_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (conschik != null && conschik.getCount() > 0) {
            conschik.moveToFirst();
            mConsentimientoChik.clear();
            do{
                mConsentimientoChik.add(crearConsentimientoChik(conschik));
            } while (conschik.moveToNext());
        }
        conschik.close();
        return mConsentimientoChik;
    }

    /**
     * Obtiene Lista todas los ConsentimientoChik de un codigo
     *
     * @return lista con ConsentimientoChik
     */
    public ArrayList<ConsentimientoChik> getListaConsentimientoChik() throws SQLException {
        Cursor conschik = null;
        ArrayList<ConsentimientoChik> mConsentimientoChik = new ArrayList<ConsentimientoChik>();
        conschik = mDb.query(true, ConstantsDB.CONSCHIK_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (conschik != null && conschik.getCount() > 0) {
            conschik.moveToFirst();
            mConsentimientoChik.clear();
            do{
                mConsentimientoChik.add(crearConsentimientoChik(conschik));
            } while (conschik.moveToNext());
        }
        conschik.close();
        return mConsentimientoChik;
    }



    /**
     * Crea una ConsentimientoChik
     *
     * @return ConsentimientoChik
     */
    public ConsentimientoChik crearConsentimientoChik(Cursor conschikcur){
        ConsentimientoChik mConsentimientoChik = new ConsentimientoChik();
        Date fecha = new Date(conschikcur.getLong(conschikcur.getColumnIndex(ConstantsDB.TODAY)));
        ConsentimientoChikId conschikId = new ConsentimientoChikId();
        conschikId.setCodigo(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.CODIGO)));
        conschikId.setFechaCons(new Date(conschikcur.getLong(conschikcur.getColumnIndex(ConstantsDB.FECHA_CONS))));
        mConsentimientoChik.setConsChikId(conschikId);
        mConsentimientoChik.setAutsup(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.autsup)));
        mConsentimientoChik.setParteFDen(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.partefden)));
        mConsentimientoChik.setRechazoDen(conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.rechazoden)));
        mConsentimientoChik.setOtroRechazoDen(conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.otrorechazoden)));
        mConsentimientoChik.setIncDen(conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.incden)));
        mConsentimientoChik.setAutsup2(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.autsup2)));
        mConsentimientoChik.setExcDen(conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.excden)));
        mConsentimientoChik.setEnfCronSN(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.enfcronsn)));
        mConsentimientoChik.setEnfCronica(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.enfCronica)));
        mConsentimientoChik.setoEnfCronica(conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.oEnfCronica)));
        mConsentimientoChik.setTomaTx(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.tomatx)));
        mConsentimientoChik.setCualesTx(conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.cualestx)));
        mConsentimientoChik.setAutsup3(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.autsup3)));
        mConsentimientoChik.setCmDomicilio(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.cmdomicilio)));
        mConsentimientoChik.setBarrio(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.barrio)));
        mConsentimientoChik.setAutsup4(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.autsup4)));
        mConsentimientoChik.setDire(conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.dire)));
        mConsentimientoChik.setManzana(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.manzana)));
        mConsentimientoChik.setTelefono(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.telefono)));
        mConsentimientoChik.setAsentimiento(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.asentimiento)));
        mConsentimientoChik.setFirmcarta(conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.firmcarta)));
        mConsentimientoChik.setRelacionFam(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.relacionfam)));
        mConsentimientoChik.setCoordenadas(conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.coordenadas)));
        Boolean borrado = conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.DELETED))>0;
        mConsentimientoChik.setMovilInfo(new MovilInfo(conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.FILE_PATH)),
                conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.STATUS)),
                conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.START)),
                conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.END)),
                conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.DEVICE_ID)),
                conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                conschikcur.getString(conschikcur.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.REC1)),
                conschikcur.getInt(conschikcur.getColumnIndex(ConstantsDB.REC2))));
        return mConsentimientoChik;
    }


    /**
     * Obtiene Lista todas las cambios sin enviar
     *
     * @return lista con CambioEstudio
     */
    public List<CambioEstudio> getListaCambiosEstudioSinEnviar() throws SQLException {
        Cursor cambios = null;
        List<CambioEstudio> mCambios = new ArrayList<CambioEstudio>();
        cambios = mDb.query(true, ConstantsDB.CEST_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (cambios != null && cambios.getCount() > 0) {
            cambios.moveToFirst();
            mCambios.clear();
            do{
                mCambios.add(crearCambioEstudio(cambios));
            } while (cambios.moveToNext());
        }
        cambios.close();
        return mCambios;
    }

    /**
     * Obtiene Lista todas los CambioEstudio de un codigo
     *
     * @return lista con CambioEstudio
     */
    public ArrayList<CambioEstudio> getListaCambiosEstudio(Integer codigo) throws SQLException {
        Cursor cambios = null;
        ArrayList<CambioEstudio> mCambioEstudio = new ArrayList<CambioEstudio>();
        cambios = mDb.query(true, ConstantsDB.CEST_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (cambios != null && cambios.getCount() > 0) {
            cambios.moveToFirst();
            mCambioEstudio.clear();
            do{
                mCambioEstudio.add(crearCambioEstudio(cambios));
            } while (cambios.moveToNext());
        }
        cambios.close();
        return mCambioEstudio;
    }

    /**
     * Obtiene Lista todas los CambioEstudio
     *
     * @return lista con CambioEstudio
     */
    public ArrayList<CambioEstudio> getListaCambioEstudio() throws SQLException {
        Cursor cambios = null;
        ArrayList<CambioEstudio> mCambioEstudio = new ArrayList<CambioEstudio>();
        cambios = mDb.query(true, ConstantsDB.CEST_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (cambios != null && cambios.getCount() > 0) {
            cambios.moveToFirst();
            mCambioEstudio.clear();
            do{
                mCambioEstudio.add(crearCambioEstudio(cambios));
            } while (cambios.moveToNext());
        }
        cambios.close();
        return mCambioEstudio;
    }



    /**
     * Crea una CambioEstudio
     *
     * @return CambioEstudio
     */
    public CambioEstudio crearCambioEstudio(Cursor cambio){
        CambioEstudio mCambioEstudio = new CambioEstudio();
        Date fecha = new Date(cambio.getLong(cambio.getColumnIndex(ConstantsDB.TODAY)));
        CambioEstudioId cambioId = new CambioEstudioId();
        cambioId.setCodigo(cambio.getInt(cambio.getColumnIndex(ConstantsDB.CODIGO)));
        cambioId.setFechaCambio(new Date(cambio.getLong(cambio.getColumnIndex(ConstantsDB.FECHA_CAMBIO))));
        mCambioEstudio.setCambioEstudioId(cambioId);
        mCambioEstudio.setParteAFlu(cambio.getInt(cambio.getColumnIndex(ConstantsDB.parteaflu)));
        mCambioEstudio.setRechazoFlu(cambio.getString(cambio.getColumnIndex(ConstantsDB.rechazoflu)));

        mCambioEstudio.setParteADen(cambio.getInt(cambio.getColumnIndex(ConstantsDB.parteaden)));
        mCambioEstudio.setRechazoDen(cambio.getString(cambio.getColumnIndex(ConstantsDB.rechazoden)));

        mCambioEstudio.setIncFlu(cambio.getString(cambio.getColumnIndex(ConstantsDB.incflu)));
        mCambioEstudio.setExcFlu(cambio.getString(cambio.getColumnIndex(ConstantsDB.excflu)));

        mCambioEstudio.setIncDen(cambio.getString(cambio.getColumnIndex(ConstantsDB.incden)));
        mCambioEstudio.setExcDen(cambio.getString(cambio.getColumnIndex(ConstantsDB.excden)));

        mCambioEstudio.setEnfCronSN(cambio.getInt(cambio.getColumnIndex(ConstantsDB.enfcronsn)));
        mCambioEstudio.setEnfCronica(cambio.getInt(cambio.getColumnIndex(ConstantsDB.enfCronica)));
        mCambioEstudio.setoEnfCronica(cambio.getString(cambio.getColumnIndex(ConstantsDB.oEnfCronica)));
        mCambioEstudio.setTomaTx(cambio.getInt(cambio.getColumnIndex(ConstantsDB.tomatx)));
        mCambioEstudio.setCualesTx(cambio.getString(cambio.getColumnIndex(ConstantsDB.cualestx)));

        mCambioEstudio.setAsentimiento(cambio.getInt(cambio.getColumnIndex(ConstantsDB.asentimiento)));

        mCambioEstudio.setParteBFlu(cambio.getInt(cambio.getColumnIndex(ConstantsDB.partebflu)));
        mCambioEstudio.setParteCFlu(cambio.getInt(cambio.getColumnIndex(ConstantsDB.partecflu)));

        mCambioEstudio.setParteBDen(cambio.getInt(cambio.getColumnIndex(ConstantsDB.partebden)));
        mCambioEstudio.setParteCDen(cambio.getInt(cambio.getColumnIndex(ConstantsDB.partecden)));
        mCambioEstudio.setParteDDen(cambio.getInt(cambio.getColumnIndex(ConstantsDB.partedden)));
        mCambioEstudio.setParteEDen(cambio.getInt(cambio.getColumnIndex(ConstantsDB.parteeden)));
        mCambioEstudio.setParteFDen(cambio.getInt(cambio.getColumnIndex(ConstantsDB.partefden)));

        mCambioEstudio.setAsentimientoesc(cambio.getInt(cambio.getColumnIndex(ConstantsDB.asentimientoesc)));

        mCambioEstudio.setFirmcarta(cambio.getString(cambio.getColumnIndex(ConstantsDB.firmcarta)));
        mCambioEstudio.setRelacionFam(cambio.getInt(cambio.getColumnIndex(ConstantsDB.relacionfam)));

        Boolean borrado = cambio.getInt(cambio.getColumnIndex(ConstantsDB.DELETED))>0;
        mCambioEstudio.setMovilInfo(new MovilInfo(cambio.getInt(cambio.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                cambio.getString(cambio.getColumnIndex(ConstantsDB.FILE_PATH)),
                cambio.getString(cambio.getColumnIndex(ConstantsDB.STATUS)),
                cambio.getString(cambio.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                cambio.getString(cambio.getColumnIndex(ConstantsDB.START)),
                cambio.getString(cambio.getColumnIndex(ConstantsDB.END)),
                cambio.getString(cambio.getColumnIndex(ConstantsDB.DEVICE_ID)),
                cambio.getString(cambio.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                cambio.getString(cambio.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                cambio.getString(cambio.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                cambio.getInt(cambio.getColumnIndex(ConstantsDB.REC1)),
                cambio.getInt(cambio.getColumnIndex(ConstantsDB.REC2))));
        return mCambioEstudio;
    }


    /**
     * Obtiene Lista todas las BHC sin enviar
     *
     * @return lista con BHC
     */
    public List<RecepcionBHC> getListaRecepcionBHCSinEnviar() throws SQLException {
        Cursor bhcs = null;
        List<RecepcionBHC> mBHCs = new ArrayList<RecepcionBHC>();
        bhcs = mDb.query(true, ConstantsDB.BHC_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (bhcs != null && bhcs.getCount() > 0) {
            bhcs.moveToFirst();
            mBHCs.clear();
            do{
                mBHCs.add(crearRecepcionBHC(bhcs));
            } while (bhcs.moveToNext());
        }
        bhcs.close();
        return mBHCs;
    }

    /**
     * Obtiene Lista todas los RecepcionBHC de un codigo
     *
     * @return lista con RecepcionBHC
     */
    public ArrayList<RecepcionBHC> getListaRecepcionBHC(Integer codigo) throws SQLException {
        Cursor bhcs = null;
        ArrayList<RecepcionBHC> mRecepcionBHC = new ArrayList<RecepcionBHC>();
        bhcs = mDb.query(true, ConstantsDB.BHC_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (bhcs != null && bhcs.getCount() > 0) {
            bhcs.moveToFirst();
            mRecepcionBHC.clear();
            do{
                mRecepcionBHC.add(crearRecepcionBHC(bhcs));
            } while (bhcs.moveToNext());
        }
        bhcs.close();
        return mRecepcionBHC;
    }

    /**
     * Crea una RecepcionBHC
     *
     * @return RecepcionBHC
     */
    public RecepcionBHC crearRecepcionBHC(Cursor tubosbhc){
        RecepcionBHC mTubo = new RecepcionBHC();
        RecepcionBHCId tuboId = new RecepcionBHCId();
        tuboId.setCodigo(tubosbhc.getInt(tubosbhc.getColumnIndex(ConstantsDB.CODIGO)));
        tuboId.setFechaRecBHC(new Date(tubosbhc.getLong(tubosbhc.getColumnIndex(ConstantsDB.FECHA_BHC))));
        mTubo.setRecBhcId(tuboId);
        mTubo.setPaxgene(tubosbhc.getInt(tubosbhc.getColumnIndex(ConstantsDB.PAXGENE))>0);
        mTubo.setVolumen(tubosbhc.getDouble(tubosbhc.getColumnIndex(ConstantsDB.VOLBHC)));
        mTubo.setLugar(tubosbhc.getString(tubosbhc.getColumnIndex(ConstantsDB.LUGAR)));
        mTubo.setObservacion(tubosbhc.getString(tubosbhc.getColumnIndex(ConstantsDB.OBSBHC)));
        mTubo.setUsername(tubosbhc.getString(tubosbhc.getColumnIndex(ConstantsDB.USERNAME)));
        mTubo.setEstado(tubosbhc.getString(tubosbhc.getColumnIndex(ConstantsDB.STATUS)));
        mTubo.setFecreg(new Date(tubosbhc.getLong(tubosbhc.getColumnIndex(ConstantsDB.TODAY))));
        return mTubo;
    }


    /**
     * Obtiene Lista todas las Sero sin enviar
     *
     * @return lista con Sero
     */
    public List<RecepcionSero> getListaRecepcionSeroSinEnviar() throws SQLException {
        Cursor rojos = null;
        List<RecepcionSero> mRecepcionSero = new ArrayList<RecepcionSero>();
        rojos = mDb.query(true, ConstantsDB.SERO_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (rojos != null && rojos.getCount() > 0) {
            rojos.moveToFirst();
            mRecepcionSero.clear();
            do{
                mRecepcionSero.add(crearRecepcionSero(rojos));
            } while (rojos.moveToNext());
        }
        rojos.close();
        return mRecepcionSero;
    }

    /**
     * Obtiene Lista todas los RecepcionSero de un codigo
     *
     * @return lista con RecepcionSero
     */
    public ArrayList<RecepcionSero> getListaRecepcionSero(Integer codigo) throws SQLException {
        Cursor rojos = null;
        ArrayList<RecepcionSero> mRecepcionSero = new ArrayList<RecepcionSero>();
        rojos = mDb.query(true, ConstantsDB.SERO_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (rojos != null && rojos.getCount() > 0) {
            rojos.moveToFirst();
            mRecepcionSero.clear();
            do{
                mRecepcionSero.add(crearRecepcionSero(rojos));
            } while (rojos.moveToNext());
        }
        rojos.close();
        return mRecepcionSero;
    }

    /**
     * Crea una RecepcionSero
     *
     * @return RecepcionSero
     */
    public RecepcionSero crearRecepcionSero(Cursor tubossero){
        RecepcionSero mTubo = new RecepcionSero();
        RecepcionSeroId tuboId = new RecepcionSeroId();
        tuboId.setCodigo(tubossero.getInt(tubossero.getColumnIndex(ConstantsDB.CODIGO)));
        tuboId.setFechaRecSero(new Date(tubossero.getLong(tubossero.getColumnIndex(ConstantsDB.FECHA_SERO))));
        mTubo.setRecSeroId(tuboId);
        mTubo.setVolumen(tubossero.getDouble(tubossero.getColumnIndex(ConstantsDB.VOLSERO)));
        mTubo.setLugar(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.LUGAR)));
        mTubo.setObservacion(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.OBSSERO)));
        mTubo.setUsername(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.USERNAME)));
        mTubo.setEstado(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.STATUS)));
        mTubo.setFecreg(new Date(tubossero.getLong(tubossero.getColumnIndex(ConstantsDB.TODAY))));
        return mTubo;
    }

    /**
     * Obtiene Lista todas las Pinchazos sin enviar
     *
     * @return lista con Pinchazos
     */
    public List<Pinchazo> getListaPinchazosSinEnviar() throws SQLException {
        Cursor pins = null;
        List<Pinchazo> mPinchazo = new ArrayList<Pinchazo>();
        pins = mDb.query(true, ConstantsDB.PIN_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (pins != null && pins.getCount() > 0) {
            pins.moveToFirst();
            mPinchazo.clear();
            do{
                mPinchazo.add(crearPinchazo(pins));
            } while (pins.moveToNext());
        }
        pins.close();
        return mPinchazo;
    }

    /**
     * Obtiene Lista todas los Pinchazo de un codigo
     *
     * @return lista con Pinchazo
     */
    public ArrayList<Pinchazo> getListaPinchazo(Integer codigo) throws SQLException {
        Cursor pins = null;
        ArrayList<Pinchazo> mPinchazo = new ArrayList<Pinchazo>();
        pins = mDb.query(true, ConstantsDB.PIN_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (pins != null && pins.getCount() > 0) {
            pins.moveToFirst();
            mPinchazo.clear();
            do{
                mPinchazo.add(crearPinchazo(pins));
            } while (pins.moveToNext());
        }
        pins.close();
        return mPinchazo;
    }

    /**
     * Crea una Pinchazo
     *
     * @return Pinchazo
     */
    public Pinchazo crearPinchazo(Cursor pinmasuno){
        Pinchazo mPin = new Pinchazo();
        PinchazoId pinId = new PinchazoId();
        pinId.setCodigo(pinmasuno.getInt(pinmasuno.getColumnIndex(ConstantsDB.CODIGO)));
        pinId.setFechaPinchazo(new Date(pinmasuno.getLong(pinmasuno.getColumnIndex(ConstantsDB.FECHA_PIN))));
        mPin.setPinId(pinId);
        mPin.setNumPin(pinmasuno.getInt(pinmasuno.getColumnIndex(ConstantsDB.PINCHAZOS)));
        mPin.setLugar(pinmasuno.getString(pinmasuno.getColumnIndex(ConstantsDB.LUGAR)));
        mPin.setObservacion(pinmasuno.getString(pinmasuno.getColumnIndex(ConstantsDB.OBSPIN)));
        mPin.setUsername(pinmasuno.getString(pinmasuno.getColumnIndex(ConstantsDB.USERNAME)));
        mPin.setEstado(pinmasuno.getString(pinmasuno.getColumnIndex(ConstantsDB.STATUS)));
        mPin.setFecreg(new Date(pinmasuno.getLong(pinmasuno.getColumnIndex(ConstantsDB.TODAY))));
        return mPin;
    }


    /**
     * Obtiene Lista todas las RazonNoData sin enviar
     *
     * @return lista con RazonNoData
     */
    public List<RazonNoData> getListaRazonNoDataSinEnviar() throws SQLException {
        Cursor rnds = null;
        List<RazonNoData> mRazonNoData = new ArrayList<RazonNoData>();
        rnds = mDb.query(true, ConstantsDB.NO_DATA_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (rnds != null && rnds.getCount() > 0) {
            rnds.moveToFirst();
            mRazonNoData.clear();
            do{
                mRazonNoData.add(crearRazonNoData(rnds));
            } while (rnds.moveToNext());
        }
        rnds.close();
        return mRazonNoData;
    }

    /**
     * Crea una RazonNoData
     *
     * @return RazonNoData
     */
    public RazonNoData crearRazonNoData(Cursor rnds){
        RazonNoData mRnd = new RazonNoData();
        RazonNoDataId rndId = new RazonNoDataId();
        rndId.setCodigo(rnds.getInt(rnds.getColumnIndex(ConstantsDB.CODIGO)));
        rndId.setFechaRegistro(new Date(rnds.getLong(rnds.getColumnIndex(ConstantsDB.TODAY))));
        mRnd.setRndId(rndId);
        mRnd.setRazon(rnds.getInt(rnds.getColumnIndex(ConstantsDB.RAZON)));
        mRnd.setOtraRazon(rnds.getString(rnds.getColumnIndex(ConstantsDB.ORAZON)));
        mRnd.setUsername(rnds.getString(rnds.getColumnIndex(ConstantsDB.USERNAME)));
        mRnd.setEstado(rnds.getString(rnds.getColumnIndex(ConstantsDB.STATUS)));
        return mRnd;
    }


    /**
     * Obtiene Lista todas las Documentos sin enviar
     *
     * @return lista con Documentos
     */
    public List<Documentos> getDocumentosSinEnviar() throws SQLException {
        Cursor docs = null;
        List<Documentos> mDocumentos = new ArrayList<Documentos>();
        //docs = mDb.rawQuery("SELECT codigo,fechaDocumento,tipoDoc,username,estado,fechaRecepcion,fecha_registro FROM documentacion where "+ ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'" , null);
        docs = mDb.rawQuery("SELECT codigo,fechaDocumento,tipoDoc,username,estado,fechaRecepcion,fecha_registro FROM documentacion", null);
        if (docs != null && docs.getCount() > 0) {
            docs.moveToFirst();
            mDocumentos.clear();
            do{
                mDocumentos.add(DocumentosHelper.crearDocumentos2(docs));
            } while (docs.moveToNext());
        }
        docs.close();
        return mDocumentos;
    }

    /**
     * Obtiene una Documentos de la base de datos
     *
     * @return Documentos
     */
    public Documentos getDocumentos(String filtro) throws SQLException {
        Documentos mDocumentos = null;
        Cursor cursorDocumentos = mDb.rawQuery("SELECT codigo,fechaDocumento,documento,tipoDoc,username,estado,fechaRecepcion,fecha_registro FROM documentacion where " + filtro , null);
        if (cursorDocumentos != null && cursorDocumentos.getCount() > 0) {
            cursorDocumentos.moveToFirst();
            mDocumentos= DocumentosHelper.crearDocumentos(cursorDocumentos);
        }
        if (!cursorDocumentos.isClosed()) cursorDocumentos.close();
        return mDocumentos;
    }



    /**
     * Obtiene Lista todas las TempRojoBhc sin enviar
     *
     * @return lista con TempRojoBhc
     */
    public List<TempRojoBhc> getListaTempRojoBhcSinEnviar() throws SQLException {
        Cursor temps = null;
        List<TempRojoBhc> mTempRojoBhc = new ArrayList<TempRojoBhc>();
        temps = mDb.query(true, ConstantsDB.TRB_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (temps != null && temps.getCount() > 0) {
            temps.moveToFirst();
            mTempRojoBhc.clear();
            do{
                mTempRojoBhc.add(crearTempRojoBhc(temps));
            } while (temps.moveToNext());
        }
        temps.close();
        return mTempRojoBhc;
    }

    /**
     * Obtiene Lista todas los TempRojoBhc de un codigo
     *
     * @return lista con TempRojoBhc
     */
    public ArrayList<TempRojoBhc> getListaTempRojoBhc(Integer codigo) throws SQLException {
        Cursor temps = null;
        ArrayList<TempRojoBhc> mTempRojoBhc = new ArrayList<TempRojoBhc>();
        temps = mDb.query(true, ConstantsDB.TRB_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (temps != null && temps.getCount() > 0) {
            temps.moveToFirst();
            mTempRojoBhc.clear();
            do{
                mTempRojoBhc.add(crearTempRojoBhc(temps));
            } while (temps.moveToNext());
        }
        temps.close();
        return mTempRojoBhc;
    }

    /**
     * Crea una TempRojoBhc
     *
     * @return TempRojoBhc
     */
    public TempRojoBhc crearTempRojoBhc(Cursor temprojos){
        TempRojoBhc mTemp = new TempRojoBhc();
        TempRojoBhcId tempId = new TempRojoBhcId();
        tempId.setRecurso(temprojos.getString(temprojos.getColumnIndex(ConstantsDB.RECURSO)));
        tempId.setFechaTempRojoBhc(new Date(temprojos.getLong(temprojos.getColumnIndex(ConstantsDB.FECHA_TEMP))));
        mTemp.setTempRojoBhcId(tempId);
        mTemp.setTemperatura(temprojos.getDouble(temprojos.getColumnIndex(ConstantsDB.TEMP)));
        mTemp.setLugar(temprojos.getString(temprojos.getColumnIndex(ConstantsDB.LUGARTEMP)));
        mTemp.setObservacion(temprojos.getString(temprojos.getColumnIndex(ConstantsDB.OBSTEMP)));
        mTemp.setUsername(temprojos.getString(temprojos.getColumnIndex(ConstantsDB.USERNAME)));
        mTemp.setEstado(temprojos.getString(temprojos.getColumnIndex(ConstantsDB.STATUS)));
        mTemp.setFecreg(new Date(temprojos.getLong(temprojos.getColumnIndex(ConstantsDB.TODAY))));
        return mTemp;
    }

    /**
     * Obtiene Lista todas las TempPbmc sin enviar
     *
     * @return lista con TempPbmc
     */
    public List<TempPbmc> getListaTempPbmcSinEnviar() throws SQLException {
        Cursor temps = null;
        List<TempPbmc> mTempPbmc = new ArrayList<TempPbmc>();
        temps = mDb.query(true, ConstantsDB.TPBMC_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (temps != null && temps.getCount() > 0) {
            temps.moveToFirst();
            mTempPbmc.clear();
            do{
                mTempPbmc.add(crearTempPbmc(temps));
            } while (temps.moveToNext());
        }
        temps.close();
        return mTempPbmc;
    }

    /**
     * Obtiene Lista todas los TempPbmc de un codigo
     *
     * @return lista con TempPbmc
     */
    public ArrayList<TempPbmc> getListaTempPbmc(Integer codigo) throws SQLException {
        Cursor temps = null;
        ArrayList<TempPbmc> mTempPbmc = new ArrayList<TempPbmc>();
        temps = mDb.query(true, ConstantsDB.TPBMC_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (temps != null && temps.getCount() > 0) {
            temps.moveToFirst();
            mTempPbmc.clear();
            do{
                mTempPbmc.add(crearTempPbmc(temps));
            } while (temps.moveToNext());
        }
        temps.close();
        return mTempPbmc;
    }

    /**
     * Crea una TempPbmc
     *
     * @return TempPbmc
     */
    public TempPbmc crearTempPbmc(Cursor temppbmcs){
        TempPbmc mTemp = new TempPbmc();
        TempPbmcId tempId = new TempPbmcId();
        tempId.setRecurso(temppbmcs.getString(temppbmcs.getColumnIndex(ConstantsDB.RECURSO)));
        tempId.setFechaTempPbmc(new Date(temppbmcs.getLong(temppbmcs.getColumnIndex(ConstantsDB.FECHA_TEMP))));
        mTemp.setTempPbmcId(tempId);
        mTemp.setTemperatura(temppbmcs.getDouble(temppbmcs.getColumnIndex(ConstantsDB.TEMP)));
        mTemp.setLugar(temppbmcs.getString(temppbmcs.getColumnIndex(ConstantsDB.LUGARTEMP)));
        mTemp.setObservacion(temppbmcs.getString(temppbmcs.getColumnIndex(ConstantsDB.OBSTEMP)));
        mTemp.setUsername(temppbmcs.getString(temppbmcs.getColumnIndex(ConstantsDB.USERNAME)));
        mTemp.setEstado(temppbmcs.getString(temppbmcs.getColumnIndex(ConstantsDB.STATUS)));
        mTemp.setFecreg(new Date(temppbmcs.getLong(temppbmcs.getColumnIndex(ConstantsDB.TODAY))));
        return mTemp;
    }


    /**
     * Obtiene Lista todas las EncuestaSatisfaccion sin enviar
     *
     * @return lista con EncuestaSatisfaccion
     */
    public List<EncuestaSatisfaccion> getEncuestaSatisfaccionSinEnviar() throws SQLException {
        Cursor encuestas = null;
        List<EncuestaSatisfaccion> mEncuestaSatisfaccions = new ArrayList<EncuestaSatisfaccion>();
        encuestas = mDb.query(true, ConstantsDB.ENC_SAT_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (encuestas != null && encuestas.getCount() > 0) {
            encuestas.moveToFirst();
            mEncuestaSatisfaccions.clear();
            do{
                mEncuestaSatisfaccions.add(crearEncuestaSatisfaccion(encuestas));
            } while (encuestas.moveToNext());
        }
        encuestas.close();
        return mEncuestaSatisfaccions;
    }

    /**
     * Crea una EncuestaSatisfaccion
     *
     * @return EncuestaSatisfaccion
     */
    public EncuestaSatisfaccion crearEncuestaSatisfaccion(Cursor encsats){
        Date fecha = new Date(encsats.getLong(encsats.getColumnIndex(ConstantsDB.TODAY)));
        EncuestaSatisfaccion mEncSat = new EncuestaSatisfaccion();
        mEncSat.setFechaEncuesta(new Date(encsats.getLong(encsats.getColumnIndex(ConstantsDB.FECHA_ENC_SAT))));
        mEncSat.setEstudio(encsats.getString(encsats.getColumnIndex(ConstantsDB.ESTUDIOSAT)));
        mEncSat.setAtenPerEst(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ATENPEREST)));
        mEncSat.setTiemAten(encsats.getInt(encsats.getColumnIndex(ConstantsDB.TIEMATEN)));
        mEncSat.setAtenPerAdm(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ATENPERADM)));
        mEncSat.setAtenPerEnferm(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ATENPERENFERM)));
        mEncSat.setAtenPerMed(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ATENPERMED)));
        mEncSat.setAmbAten(encsats.getInt(encsats.getColumnIndex(ConstantsDB.AMBATEN)));
        mEncSat.setAtenPerLab(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ATENPERLAB)));
        mEncSat.setExplDxEnf(encsats.getInt(encsats.getColumnIndex(ConstantsDB.EXPLDXENF)));
        mEncSat.setFludenSN(encsats.getInt(encsats.getColumnIndex(ConstantsDB.FLUDENSN)));
        mEncSat.setFluConImp(encsats.getInt(encsats.getColumnIndex(ConstantsDB.FLUCONIMP)));
        mEncSat.setDenConImp(encsats.getInt(encsats.getColumnIndex(ConstantsDB.DENCONIMP)));
        mEncSat.setExplPeligEnf(encsats.getInt(encsats.getColumnIndex(ConstantsDB.EXPLPELIGENF)));
        mEncSat.setExpMedCuid(encsats.getInt(encsats.getColumnIndex(ConstantsDB.EXPMEDCUID)));
        if(!encsats.isNull(encsats.getColumnIndex(ConstantsDB.otrorecurso1))) mEncSat.setOtrorecurso1(encsats.getInt(encsats.getColumnIndex(ConstantsDB.otrorecurso1)));
        Boolean borrado = encsats.getInt(encsats.getColumnIndex(ConstantsDB.DELETED))>0;
        mEncSat.setMovilInfo(new MovilInfo(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.FILE_PATH)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.STATUS)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.START)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.END)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.DEVICE_ID)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                encsats.getString(encsats.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                encsats.getInt(encsats.getColumnIndex(ConstantsDB.REC1)),
                encsats.getInt(encsats.getColumnIndex(ConstantsDB.REC2))));
        return mEncSat;
    }

    /**
     * Busca un rol de la base de datos
     *
     * @return true or false
     */

    public Boolean buscarRol(String username, String Rol) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, MainDBConstants.ROLE_TABLE, null,
                ConstantsDB.USERNAME + "='" + username + "' and " + MainDBConstants.role + "='" + Rol + "'" , null, null, null, null, null);

        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            c.close();
            return true;
        }
        return false;
    }


    /**
     * Obtiene Lista todas las recons sin enviar
     *
     * @return lista con ReconsentimientoDen
     */
    public List<ReConsentimientoDen2015> getListaReConsentimientoDen2015sSinEnviar() throws SQLException {
        Cursor recons = null;
        List<ReConsentimientoDen2015> mRecons = new ArrayList<ReConsentimientoDen2015>();
        recons = mDb.query(true, ConstantsDB.RECONS_TABLE_2015, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mRecons.clear();
            do{
                mRecons.add(crearReConsentimientoDen2015(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mRecons;
    }


    /**
     * Obtiene Lista todas las cons sin enviar
     *
     * @return lista con ConsentimientoZika
     */
    public List<ConsentimientoZika> getListaConsentimientoZikasSinEnviar() throws SQLException {
        Cursor conss = null;
        List<ConsentimientoZika> mCons = new ArrayList<ConsentimientoZika>();
        conss = mDb.query(true, ConstantsDB.ZIKA_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (conss != null && conss.getCount() > 0) {
            conss.moveToFirst();
            mCons.clear();
            do{
                mCons.add(crearConsentimientoZika(conss));
            } while (conss.moveToNext());
        }
        conss.close();
        return mCons;
    }

    /**
     * Obtiene Lista todas los ReConsentimientoDen de un codigo
     *
     * @return lista con ReConsentimientoDen
     */
    public ArrayList<ReConsentimientoDen2015> getListaReConsentimientoDen2015(Integer codigo) throws SQLException {
        Cursor recons = null;
        ArrayList<ReConsentimientoDen2015> mReConsentimientoDen = new ArrayList<ReConsentimientoDen2015>();
        recons = mDb.query(true, ConstantsDB.RECONS_TABLE_2015, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mReConsentimientoDen.clear();
            do{
                mReConsentimientoDen.add(crearReConsentimientoDen2015(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mReConsentimientoDen;
    }

    /**
     * Obtiene Lista todas los ConsentimientoZika de un codigo
     *
     * @return lista con ConsentimientoZika
     */
    public ArrayList<ConsentimientoZika> getListaConsentimientoZika(Integer codigo) throws SQLException {
        Cursor consZika = null;
        ArrayList<ConsentimientoZika> mConsentimientoZika = new ArrayList<ConsentimientoZika>();
        consZika = mDb.query(true, ConstantsDB.ZIKA_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (consZika != null && consZika.getCount() > 0) {
            consZika.moveToFirst();
            mConsentimientoZika.clear();
            do{
                mConsentimientoZika.add(crearConsentimientoZika(consZika));
            } while (consZika.moveToNext());
        }
        consZika.close();
        return mConsentimientoZika;
    }

    /**
     * Obtiene Lista todas los ConsentimientoZika de un codigo
     *
     * @return lista con ConsentimientoZika
     */
    public ArrayList<ConsentimientoZika> getListaConsentimientoZikaHoy() throws SQLException {
        Cursor consZika = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<ConsentimientoZika> mConsentimientoZika = new ArrayList<ConsentimientoZika>();
        consZika = mDb.query(true, ConstantsDB.ZIKA_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, null, null);
        if (consZika != null && consZika.getCount() > 0) {
            consZika.moveToFirst();
            mConsentimientoZika.clear();
            do{
                mConsentimientoZika.add(crearConsentimientoZika(consZika));
            } while (consZika.moveToNext());
        }
        consZika.close();
        return mConsentimientoZika;
    }

    /**
     * Obtiene Lista todas los ReConsentimientoDen de un codigo
     *
     * @return lista con ReConsentimientoDen
     */
    public ArrayList<ReConsentimientoDen2015> getListaReConsentimientoDen2015Hoy() throws SQLException {
        Cursor recons = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<ReConsentimientoDen2015> mReConsentimientoDen = new ArrayList<ReConsentimientoDen2015>();
        recons = mDb.query(true, ConstantsDB.RECONS_TABLE_2015, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mReConsentimientoDen.clear();
            do{
                mReConsentimientoDen.add(crearReConsentimientoDen2015(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mReConsentimientoDen;
    }

    public ArrayList<ReConsentimientoDen2015> getListaReConsentimientoDen2015() throws SQLException {
        Cursor recons = null;
        ArrayList<ReConsentimientoDen2015> mReConsentimientoDen = new ArrayList<ReConsentimientoDen2015>();
        recons = mDb.query(true, ConstantsDB.RECONS_TABLE_2015, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mReConsentimientoDen.clear();
            do{
                mReConsentimientoDen.add(crearReConsentimientoDen2015(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mReConsentimientoDen;
    }



    /**
     * Crea una ReConsentimientoDen
     *
     * @return ReConsentimientoDen
     */
    public ReConsentimientoDen2015 crearReConsentimientoDen2015(Cursor reconsens){
        ReConsentimientoDen2015 mReConsentimiento = new ReConsentimientoDen2015();
        Date fecha = new Date(reconsens.getLong(reconsens.getColumnIndex(ConstantsDB.TODAY)));
        ReConsentimientoDen2015Id reconsId = new ReConsentimientoDen2015Id();
        reconsId.setCodigo(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.CODIGO)));
        reconsId.setFechaCons(new Date(reconsens.getLong(reconsens.getColumnIndex(ConstantsDB.FECHA_CONS))));
        mReConsentimiento.setReconsdenId(reconsId);

        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.visExit))) mReConsentimiento.setVisExit(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.visExit)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.razonVisNoExit))) mReConsentimiento.setRazonVisNoExit(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.razonVisNoExit)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.personaDejoCarta))) mReConsentimiento.setPersonaDejoCarta(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.personaDejoCarta)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.personaCasa))) mReConsentimiento.setPersonaCasa(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.personaCasa)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.relacionFamPersonaCasa))) mReConsentimiento.setRelacionFamPersonaCasa(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.relacionFamPersonaCasa)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.otraRelacionPersonaCasa))) mReConsentimiento.setOtraRelacionPersonaCasa(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.otraRelacionPersonaCasa)));

        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoPersonaCasa))) mReConsentimiento.setTelefonoPersonaCasa(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoPersonaCasa)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.emancipado))) mReConsentimiento.setEmancipado(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.emancipado)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.descEmancipado))) mReConsentimiento.setDescEmancipado(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.descEmancipado)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.incDen))) mReConsentimiento.setIncDen(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.incDen)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.excDen))) mReConsentimiento.setExcDen(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.excDen)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.enfCronSN))) mReConsentimiento.setEnfCronSN(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.enfCronSN)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.enfCronica))) mReConsentimiento.setEnfCronica(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.enfCronica)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.oEnfCronica))) mReConsentimiento.setoEnfCronica(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.oEnfCronica)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.tomaTx))) mReConsentimiento.setTomaTx(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.tomaTx)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.cualesTx))) mReConsentimiento.setCualesTx(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.cualesTx)));

        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.assite))) mReConsentimiento.setAssite(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.assite)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.centrosalud))) mReConsentimiento.setCentrosalud(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.centrosalud)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.ocentrosalud))) mReConsentimiento.setOcentrosalud(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.ocentrosalud)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.puestosalud))) mReConsentimiento.setPuestosalud(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.puestosalud)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.asentimiento))) mReConsentimiento.setAsentimiento(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.asentimiento)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.parteADen))) mReConsentimiento.setParteADen(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.parteADen)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.parteBDen))) mReConsentimiento.setParteBDen(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.parteBDen)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.parteCDen))) mReConsentimiento.setParteCDen(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.parteCDen)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.parteDDen))) mReConsentimiento.setParteDDen(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.parteDDen)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.rechDen))) mReConsentimiento.setRechDen(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.rechDen)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.nombrept))) mReConsentimiento.setNombrept(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombrept)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.nombrept2))) mReConsentimiento.setNombrept2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombrept2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.apellidopt))) mReConsentimiento.setApellidopt(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidopt)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.apellidopt2))) mReConsentimiento.setApellidopt2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidopt2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.relacionFam))) mReConsentimiento.setRelacionFam(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.relacionFam)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.otraRelacionFam))) mReConsentimiento.setOtraRelacionFam(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.otraRelacionFam)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.mismoTutorSN))) mReConsentimiento.setMismoTutorSN(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.mismoTutorSN)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.motivoDifTutor))) mReConsentimiento.setMotivoDifTutor(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.motivoDifTutor)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.otroMotivoDifTutor))) mReConsentimiento.setOtroMotivoDifTutor(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.otroMotivoDifTutor)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.quePaisTutor))) mReConsentimiento.setQuePaisTutor(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.quePaisTutor)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.alfabetoTutor))) mReConsentimiento.setAlfabetoTutor(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.alfabetoTutor)));

        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.testigoSN))) mReConsentimiento.setTestigoSN(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.testigoSN)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.nombretest1))) mReConsentimiento.setNombretest1(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombretest1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.nombretest2))) mReConsentimiento.setNombretest2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombretest2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.apellidotest1))) mReConsentimiento.setApellidotest1(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidotest1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.apellidotest2))) mReConsentimiento.setApellidotest2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidotest2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.cmDomicilio))) mReConsentimiento.setCmDomicilio(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.cmDomicilio)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.barrio))) mReConsentimiento.setBarrio(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.barrio)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.otrobarrio))) mReConsentimiento.setOtrobarrio(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.otrobarrio)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.dire))) mReConsentimiento.setDire(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.dire)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.autsup))) mReConsentimiento.setAutsup(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.autsup)));

        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoClasif1))) mReConsentimiento.setTelefonoClasif1(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoClasif1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoConv1))) mReConsentimiento.setTelefonoConv1(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoConv1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoCel1))) mReConsentimiento.setTelefonoCel1(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoCel1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefono2SN))) mReConsentimiento.setTelefono2SN(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefono2SN)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoClasif2))) mReConsentimiento.setTelefonoClasif2(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoClasif2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoConv2))) mReConsentimiento.setTelefonoConv2(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoConv2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoCel2))) mReConsentimiento.setTelefonoCel2(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoCel2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefono3SN))) mReConsentimiento.setTelefono3SN(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefono3SN)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoClasif3))) mReConsentimiento.setTelefonoClasif3(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoClasif3)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoConv3))) mReConsentimiento.setTelefonoConv3(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoConv3)));

        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoCel3))) mReConsentimiento.setTelefonoCel3(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoCel3)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.jefenom))) mReConsentimiento.setJefenom(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.jefenom)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.jefenom2))) mReConsentimiento.setJefenom2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.jefenom2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.jefeap))) mReConsentimiento.setJefeap(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.jefeap)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.jefeap2))) mReConsentimiento.setJefeap2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.jefeap2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.nomContacto))) mReConsentimiento.setNomContacto(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nomContacto)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.barrioContacto))) mReConsentimiento.setBarrioContacto(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.barrioContacto)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.otrobarrioContacto))) mReConsentimiento.setOtrobarrioContacto(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.otrobarrioContacto)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.direContacto))) mReConsentimiento.setDireContacto(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.direContacto)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telContacto1))) mReConsentimiento.setTelContacto1(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telContacto1)));

        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telContactoConv1))) mReConsentimiento.setTelContactoConv1(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telContactoConv1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telContactoCel1))) mReConsentimiento.setTelContactoCel1(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telContactoCel1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telContacto2SN))) mReConsentimiento.setTelContacto2SN(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telContacto2SN)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telContactoClasif2))) mReConsentimiento.setTelContactoClasif2(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telContactoClasif2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telContactoConv2))) mReConsentimiento.setTelContactoConv2(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telContactoConv2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telContactoCel2))) mReConsentimiento.setTelContactoCel2(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telContactoCel2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.nombrepadre))) mReConsentimiento.setNombrepadre(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombrepadre)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.nombrepadre2))) mReConsentimiento.setNombrepadre2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombrepadre2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.apellidopadre))) mReConsentimiento.setApellidopadre(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidopadre)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.apellidopadre2))) mReConsentimiento.setApellidopadre2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidopadre2)));

        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.nombremadre))) mReConsentimiento.setNombremadre(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombremadre)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.nombremadre2))) mReConsentimiento.setNombremadre2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombremadre2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.apellidomadre))) mReConsentimiento.setApellidomadre(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidomadre)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.apellidomadre2))) mReConsentimiento.setApellidomadre2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidomadre2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.copiaFormato))) mReConsentimiento.setCopiaFormato(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.copiaFormato)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.firmo_cartcons))) mReConsentimiento.setFirmo_cartcons(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.firmo_cartcons)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.fecho_cartcons))) mReConsentimiento.setFecho_cartcons(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.fecho_cartcons)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.huella_dig))) mReConsentimiento.setHuella_dig(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.huella_dig)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.fech_firm_testigo))) mReConsentimiento.setFech_firm_testigo(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.fech_firm_testigo)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.entiende))) mReConsentimiento.setEntiende(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.entiende)));

        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.georef))) mReConsentimiento.setGeoref(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.georef)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.Manzana))) mReConsentimiento.setManzana(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.Manzana)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.georef_razon))) mReConsentimiento.setGeoref_razon(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.georef_razon)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.georef_otraRazon))) mReConsentimiento.setGeoref_otraRazon(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.georef_otraRazon)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.local))) mReConsentimiento.setLocal(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.local)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.otrorecurso1))) mReConsentimiento.setOtrorecurso1(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.otrorecurso2))) mReConsentimiento.setOtrorecurso2(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.otrorecurso2)));

        Boolean borrado = reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.DELETED))>0;
        mReConsentimiento.setMovilInfo(new MovilInfo(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.FILE_PATH)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.STATUS)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.START)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.END)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.DEVICE_ID)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.REC1)),
                reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.REC2))));
        return mReConsentimiento;
    }

    /**
     * Crea una ConsentimientoZika
     *
     * @return ConsentimientoZika
     */
    public ConsentimientoZika crearConsentimientoZika(Cursor consens){
        ConsentimientoZika mConsentimientoZika = new ConsentimientoZika();
        Date fecha = new Date(consens.getLong(consens.getColumnIndex(ConstantsDB.TODAY)));
        ConsentimientoZikaId consZikaId = new ConsentimientoZikaId();
        consZikaId.setCodigo(consens.getInt(consens.getColumnIndex(ConstantsDB.CODIGO)));
        consZikaId.setFechaCons(new Date(consens.getLong(consens.getColumnIndex(ConstantsDB.FECHA_CONS))));
        mConsentimientoZika.setConsZikaId(consZikaId);

        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.parteDDen))) mConsentimientoZika.setParteDDen(consens.getInt(consens.getColumnIndex(ConstantsDB.parteDDen)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.rechDDen))) mConsentimientoZika.setRechDDen(consens.getString(consens.getColumnIndex(ConstantsDB.rechDDen)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.nombrept))) mConsentimientoZika.setNombrept(consens.getString(consens.getColumnIndex(ConstantsDB.nombrept)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.nombrept2))) mConsentimientoZika.setNombrept2(consens.getString(consens.getColumnIndex(ConstantsDB.nombrept2)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.apellidopt))) mConsentimientoZika.setApellidopt(consens.getString(consens.getColumnIndex(ConstantsDB.apellidopt)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.apellidopt2))) mConsentimientoZika.setApellidopt2(consens.getString(consens.getColumnIndex(ConstantsDB.apellidopt2)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.relacionFam))) mConsentimientoZika.setRelacionFam(consens.getInt(consens.getColumnIndex(ConstantsDB.relacionFam)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.otraRelacionFam))) mConsentimientoZika.setOtraRelacionFam(consens.getString(consens.getColumnIndex(ConstantsDB.otraRelacionFam)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.mismoTutorSN))) mConsentimientoZika.setMismoTutorSN(consens.getInt(consens.getColumnIndex(ConstantsDB.mismoTutorSN)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.motivoDifTutor))) mConsentimientoZika.setMotivoDifTutor(consens.getInt(consens.getColumnIndex(ConstantsDB.motivoDifTutor)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.otroMotivoDifTutor))) mConsentimientoZika.setOtroMotivoDifTutor(consens.getString(consens.getColumnIndex(ConstantsDB.otroMotivoDifTutor)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.quePaisTutor))) mConsentimientoZika.setQuePaisTutor(consens.getString(consens.getColumnIndex(ConstantsDB.quePaisTutor)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.alfabetoTutor))) mConsentimientoZika.setAlfabetoTutor(consens.getInt(consens.getColumnIndex(ConstantsDB.alfabetoTutor)));

        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.testigoSN))) mConsentimientoZika.setTestigoSN(consens.getInt(consens.getColumnIndex(ConstantsDB.testigoSN)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.nombretest1))) mConsentimientoZika.setNombretest1(consens.getString(consens.getColumnIndex(ConstantsDB.nombretest1)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.nombretest2))) mConsentimientoZika.setNombretest2(consens.getString(consens.getColumnIndex(ConstantsDB.nombretest2)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.apellidotest1))) mConsentimientoZika.setApellidotest1(consens.getString(consens.getColumnIndex(ConstantsDB.apellidotest1)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.apellidotest2))) mConsentimientoZika.setApellidotest2(consens.getString(consens.getColumnIndex(ConstantsDB.apellidotest2)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.otrorecurso1))) mConsentimientoZika.setOtrorecurso1(consens.getInt(consens.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!consens.isNull(consens.getColumnIndex(ConstantsDB.otrorecurso2))) mConsentimientoZika.setOtrorecurso2(consens.getInt(consens.getColumnIndex(ConstantsDB.otrorecurso2)));

        Boolean borrado = consens.getInt(consens.getColumnIndex(ConstantsDB.DELETED))>0;
        mConsentimientoZika.setMovilInfo(new MovilInfo(consens.getInt(consens.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                consens.getString(consens.getColumnIndex(ConstantsDB.FILE_PATH)),
                consens.getString(consens.getColumnIndex(ConstantsDB.STATUS)),
                consens.getString(consens.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                consens.getString(consens.getColumnIndex(ConstantsDB.START)),
                consens.getString(consens.getColumnIndex(ConstantsDB.END)),
                consens.getString(consens.getColumnIndex(ConstantsDB.DEVICE_ID)),
                consens.getString(consens.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                consens.getString(consens.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                consens.getString(consens.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                consens.getInt(consens.getColumnIndex(ConstantsDB.REC1)),
                consens.getInt(consens.getColumnIndex(ConstantsDB.REC2))));
        return mConsentimientoZika;
    }

    /**
     * Obtiene Lista todas las CodigosCasas sin enviar
     *
     * @return lista con CodigosCasas
     */
    public List<CodigosCasas> getListaCodigosCasasSinEnviar() throws SQLException {
        Cursor ccs = null;
        List<CodigosCasas> mCodigosCasas = new ArrayList<CodigosCasas>();
        ccs = mDb.query(true, ConstantsDB.COD_REL_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (ccs != null && ccs.getCount() > 0) {
            ccs.moveToFirst();
            mCodigosCasas.clear();
            do{
                mCodigosCasas.add(crearCodigosCasas(ccs));
            } while (ccs.moveToNext());
        }
        ccs.close();
        return mCodigosCasas;
    }

    /**
     * Crea una CodigosCasas
     *
     * @return CodigosCasas
     */
    public CodigosCasas crearCodigosCasas(Cursor rccs){
        CodigosCasas mCcs = new CodigosCasas();

        mCcs.setFechaRegistro(new Date(rccs.getLong(rccs.getColumnIndex(ConstantsDB.TODAY))));
        mCcs.setCodCasa(rccs.getInt(rccs.getColumnIndex(ConstantsDB.COD_CASA_R)));
        mCcs.setCodigoComun(rccs.getInt(rccs.getColumnIndex(ConstantsDB.COD_COMUN)));
        mCcs.setCodigoRelacionado(rccs.getInt(rccs.getColumnIndex(ConstantsDB.COD_RELA)));
        mCcs.setUsername(rccs.getString(rccs.getColumnIndex(ConstantsDB.USERNAME)));
        mCcs.setEstado(rccs.getString(rccs.getColumnIndex(ConstantsDB.STATUS)));
        return mCcs;
    }

    /**
     * Obtiene Lista todas los CodigosCasas de un codigo
     *
     * @return lista con CodigosCasas
     */
    public ArrayList<CodigosCasas> getListaCodigosCasas(Integer codigo) throws SQLException {
        Cursor codigos = null;
        ArrayList<CodigosCasas> mCodigosCasas = new ArrayList<CodigosCasas>();
        codigos = mDb.query(true, ConstantsDB.COD_REL_TABLE, null,
                ConstantsDB.COD_COMUN + "=" + codigo, null, null, null, null, null);
        if (codigos != null && codigos.getCount() > 0) {
            codigos.moveToFirst();
            mCodigosCasas.clear();
            do{
                mCodigosCasas.add(crearCodigosCasas(codigos));
            } while (codigos.moveToNext());
        }
        codigos.close();
        return mCodigosCasas;
    }

    /**
     * Obtiene Lista todas los CodigosCasas
     *
     * @return lista con CodigosCasas
     */
    public ArrayList<CodigosCasas> getListaCodigosCasas() throws SQLException {
        Cursor codigos = null;
        ArrayList<CodigosCasas> mCodigosCasas = new ArrayList<CodigosCasas>();
        codigos = mDb.query(true, ConstantsDB.COD_REL_TABLE, null,
                null, null, null, null, ConstantsDB.TODAY, null);
        if (codigos != null && codigos.getCount() > 0) {
            codigos.moveToFirst();
            mCodigosCasas.clear();
            do{
                mCodigosCasas.add(crearCodigosCasas(codigos));
            } while (codigos.moveToNext());
        }
        codigos.close();
        return mCodigosCasas;
    }



    /**
     * Obtiene Lista todas las recons sin enviar
     *
     * @return lista con ReConsentimientoFlu2015
     */
    public List<ReConsentimientoFlu2015> getListaReConsentimientoFlu2015sSinEnviar() throws SQLException {
        Cursor recons = null;
        List<ReConsentimientoFlu2015> mRecons = new ArrayList<ReConsentimientoFlu2015>();
        recons = mDb.query(true, ConstantsDB.RECONSFLU_TABLE_2015, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mRecons.clear();
            do{
                mRecons.add(crearReConsentimientoFlu2015(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mRecons;
    }

    /**
     * Obtiene Lista todas los ReConsentimientoFlu2015 de un codigo
     *
     * @return lista con ReConsentimientoFlu2015
     */
    public ArrayList<ReConsentimientoFlu2015> getListaReConsentimientoFlu2015(Integer codigo) throws SQLException {
        Cursor recons = null;
        ArrayList<ReConsentimientoFlu2015> mReConsentimientoFlu = new ArrayList<ReConsentimientoFlu2015>();
        recons = mDb.query(true, ConstantsDB.RECONSFLU_TABLE_2015, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mReConsentimientoFlu.clear();
            do{
                mReConsentimientoFlu.add(crearReConsentimientoFlu2015(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mReConsentimientoFlu;
    }

    /**
     * Obtiene Lista todas los ReConsentimientoFlu2015
     *
     * @return lista con ReConsentimientoFlu2015
     */
    public ArrayList<ReConsentimientoFlu2015> getListaReConsentimientoFlu2015() throws SQLException {
        Cursor recons = null;
        ArrayList<ReConsentimientoFlu2015> mReConsentimientoFlu = new ArrayList<ReConsentimientoFlu2015>();
        recons = mDb.query(true, ConstantsDB.RECONSFLU_TABLE_2015, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mReConsentimientoFlu.clear();
            do{
                mReConsentimientoFlu.add(crearReConsentimientoFlu2015(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mReConsentimientoFlu;
    }

    /**
     * Obtiene Lista todas los ReConsentimientoFlu2015
     *
     * @return lista con ReConsentimientoFlu2015
     */
    public ArrayList<ReConsentimientoFlu2015> getListaReConsentimientoFlu2015Hoy() throws SQLException {
        Cursor recons = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<ReConsentimientoFlu2015> mReConsentimientoFlu = new ArrayList<ReConsentimientoFlu2015>();
        recons = mDb.query(true, ConstantsDB.RECONSFLU_TABLE_2015, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (recons != null && recons.getCount() > 0) {
            recons.moveToFirst();
            mReConsentimientoFlu.clear();
            do{
                mReConsentimientoFlu.add(crearReConsentimientoFlu2015(recons));
            } while (recons.moveToNext());
        }
        recons.close();
        return mReConsentimientoFlu;
    }



    /**
     * Crea una ReConsentimientoFlu
     *
     * @return ReConsentimientoFlu
     */
    public ReConsentimientoFlu2015 crearReConsentimientoFlu2015(Cursor reconsens){
        ReConsentimientoFlu2015 mReConsentimiento = new ReConsentimientoFlu2015();
        Date fecha = new Date(reconsens.getLong(reconsens.getColumnIndex(ConstantsDB.TODAY)));
        ReConsentimientoFlu2015Id reconsId = new ReConsentimientoFlu2015Id();
        reconsId.setCodigo(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.CODIGO)));
        reconsId.setFechaCons(new Date(reconsens.getLong(reconsens.getColumnIndex(ConstantsDB.FECHA_CONS))));
        mReConsentimiento.setReconsfluId(reconsId);

        mReConsentimiento.setVisExit(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.visExit)));
        mReConsentimiento.setNoexitosa(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.razonVisNoExit)));
        mReConsentimiento.setNombrept(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombrept)));
        mReConsentimiento.setNombrept2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombrept2)));
        mReConsentimiento.setApellidopt(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidopt)));
        mReConsentimiento.setApellidopt2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidopt2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.relacionFam))) mReConsentimiento.setRelacionFam(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.relacionFam)));
        mReConsentimiento.setOtraRelacionFam(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.otraRelacionFam)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.alfabetoTutor))) mReConsentimiento.setAlfabetoTutor(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.alfabetoTutor)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.testigoSN))) mReConsentimiento.setTestigoSN(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.testigoSN)));
        mReConsentimiento.setNombretest1(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombretest1)));
        mReConsentimiento.setNombretest2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.nombretest2)));
        mReConsentimiento.setApellidotest1(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidotest1)));
        mReConsentimiento.setApellidotest2(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.apellidotest2)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.cmDomicilio))) mReConsentimiento.setCmDomicilio(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.cmDomicilio)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.barrio))) mReConsentimiento.setBarrio(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.barrio)));
        mReConsentimiento.setOtrobarrio(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.otrobarrio)));
        mReConsentimiento.setDire(reconsens.getString(reconsens.getColumnIndex(ConstantsDB.dire)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telContacto1))) mReConsentimiento.setTelefContact(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telContacto1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoConv1))) mReConsentimiento.setTelefonoConv1(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoConv1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoCel1))) mReConsentimiento.setTelefonoCel1(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoCel1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.telefonoCel2))) mReConsentimiento.setTelefonoCel2(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.telefonoCel2)));


        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.asentimiento))) mReConsentimiento.setAsentimiento(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.asentimiento)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.parteAFlu))) mReConsentimiento.setParteAFlu(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.parteAFlu)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.parteBFlu))) mReConsentimiento.setParteBFlu(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.parteBFlu)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.parteCFlu))) mReConsentimiento.setParteCFlu(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.parteCFlu)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.rechFlu))) mReConsentimiento.setPorqueno(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.rechFlu)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.contacto_futuro))) mReConsentimiento.setContacto_futuro(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.contacto_futuro)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.local))) mReConsentimiento.setLocal(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.local)));

        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.otrorecurso1))) mReConsentimiento.setOtrorecurso1(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!reconsens.isNull(reconsens.getColumnIndex(ConstantsDB.otrorecurso2))) mReConsentimiento.setOtrorecurso2(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.otrorecurso2)));

        Boolean borrado = reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.DELETED))>0;
        mReConsentimiento.setMovilInfo(new MovilInfo(reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.FILE_PATH)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.STATUS)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.START)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.END)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.DEVICE_ID)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                reconsens.getString(reconsens.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.REC1)),
                reconsens.getInt(reconsens.getColumnIndex(ConstantsDB.REC2))));
        return mReConsentimiento;
    }


    /**
     * Obtiene Lista todas las CambiosCasas sin enviar
     *
     * @return lista con CambiosCasas
     */
    public List<CambiosCasas> getListaCambiosCasasSinEnviar() throws SQLException {
        Cursor ccs = null;
        List<CambiosCasas> mCambiosCasas = new ArrayList<CambiosCasas>();
        ccs = mDb.query(true, ConstantsDB.CAMB_CASA_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (ccs != null && ccs.getCount() > 0) {
            ccs.moveToFirst();
            mCambiosCasas.clear();
            do{
                mCambiosCasas.add(crearCambiosCasas(ccs));
            } while (ccs.moveToNext());
        }
        ccs.close();
        return mCambiosCasas;
    }

    /**
     * Crea una CambiosCasas
     *
     * @return CambiosCasas
     */
    public CambiosCasas crearCambiosCasas(Cursor rccs){
        CambiosCasas mCcs = new CambiosCasas();

        mCcs.setFechaRegistro(new Date(rccs.getLong(rccs.getColumnIndex(ConstantsDB.TODAY))));
        mCcs.setCodigo(rccs.getInt(rccs.getColumnIndex(ConstantsDB.codigo)));
        mCcs.setCodCasaAnterior(rccs.getInt(rccs.getColumnIndex(ConstantsDB.codCasaAnterior)));
        mCcs.setCodCasaActual(rccs.getInt(rccs.getColumnIndex(ConstantsDB.codCasaActual)));
        mCcs.setUsername(rccs.getString(rccs.getColumnIndex(ConstantsDB.USERNAME)));
        mCcs.setEstado(rccs.getString(rccs.getColumnIndex(ConstantsDB.STATUS)));
        return mCcs;
    }

}
