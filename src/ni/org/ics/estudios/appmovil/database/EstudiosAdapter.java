package ni.org.ics.estudios.appmovil.database;

/**
 * Adaptador de la base de datos Cohorte
 * 
 * @author William Aviles
 */

import java.util.ArrayList;
import java.util.List;

import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.domain.CartaConsentimiento;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.Tamizaje;
import ni.org.ics.estudios.appmovil.domain.VisitaTerreno;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.*;
import ni.org.ics.estudios.appmovil.domain.users.Authority;
import ni.org.ics.estudios.appmovil.domain.users.UserSistema;
import ni.org.ics.estudios.appmovil.helpers.*;
import ni.org.ics.estudios.appmovil.utils.*;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteQueryBuilder;

public class EstudiosAdapter {

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mContext;
	private final String mPassword;
	private final boolean mFromServer;
	private final boolean mCleanDb;
	

	public EstudiosAdapter(Context context, String password, boolean fromServer, boolean cleanDb) {
		mContext = context;
		mPassword = password;
		mFromServer = fromServer;
		mCleanDb = cleanDb;
	}
	
	private static class DatabaseHelper extends EstudiosSQLiteOpenHelper {

		DatabaseHelper(Context context, String password, boolean fromServer, boolean cleanDb) {
			super(FileUtils.DATABASE_PATH, MainDBConstants.DATABASE_NAME, MainDBConstants.DATABASE_VERSION, context,
					password, fromServer, cleanDb);
			createStorage();
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(MainDBConstants.CREATE_USER_TABLE);
			db.execSQL(MainDBConstants.CREATE_ROLE_TABLE);
			db.execSQL(CatalogosDBConstants.CREATE_MESSAGES_TABLE);
			db.execSQL(CatalogosDBConstants.CREATE_BARRIO_TABLE);
			db.execSQL(CatalogosDBConstants.CREATE_ESTUDIO_TABLE);
			db.execSQL(MainDBConstants.CREATE_CASA_TABLE); 
			db.execSQL(MainDBConstants.CREATE_VISITA_TABLE);
			db.execSQL(MainDBConstants.CREATE_PRETAMIZAJE_TABLE);
            db.execSQL(MainDBConstants.CREATE_CASA_CHF_TABLE);
            db.execSQL(MainDBConstants.CREATE_PARTICIPANTE_TALBE);
            db.execSQL(MainDBConstants.CREATE_PARTICIPANTE_CHF_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_CASA_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_PARTOBB_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_PARTICIPANTE_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_PESOTALLA_TABLE);
            db.execSQL(MainDBConstants.CREATE_CARTACONSENTIMIENTO_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_LACTANCIAMAT_TABLE);
            db.execSQL(MuestrasDBConstants.CREATE_MUESTRA_TABLE);
            db.execSQL(MuestrasDBConstants.CREATE_PAXGENE_TABLE);
            db.execSQL(MainDBConstants.CREATE_AREA_AMBIENTE_TABLE);
            db.execSQL(MainDBConstants.CREATE_CAMA_TABLE);
            
			db.execSQL("INSERT INTO `barrios` (`CODIGO`, `identificador_equipo`, `ESTADO`, `PASIVE`, `recordDate`, `recordUser`, `NOMBRE`) VALUES (1, 'server', '1', '0', '2017-05-10 11:01:26', 'admin', 'Cuba')");
			db.execSQL("INSERT INTO `estudios` (`CODIGO`, `identificador_equipo`, `ESTADO`, `PASIVE`, `recordDate`, `recordUser`, `NOMBRE`) VALUES (1, 'server', '1', '0', '2017-05-10 11:01:26', 'admin', 'Cohorte Familia')");
			db.execSQL("INSERT INTO `casas` (`CODIGO`, `IDENTIFICADOR_EQUIPO`, `ESTADO`, `PASIVE`, `recordDate`, `recordUser`, `apellido1JefeFamilia`, `apellido2JefeFamilia`, `DIRECCION`, `MANZANA`, `nombre1JefeFamilia`, `nombre2JefeFamilia`, `barrio`) VALUES (1, 'server', '1', '0', '2017-05-10 11:22:29', 'admin', 'Lopez', 'Martinez', 'sfgsgsgsd', '3', 'Pedro', 'Ramon', 1)");
			db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('no', '0', 'CAT_SINO', NULL, '0', 2, '0', 'No');");
			db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('yes', '1', 'CAT_SINO', NULL, '0', 1, '0', 'Si');");
			db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('male', 'M', 'CAT_SEXO', NULL, '0', 1, '0', 'Masculino');");
			db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('female', 'F', 'CAT_SEXO', NULL, '0', 2, '0', 'Femenino');");
			db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('noquiere', 'NQ', 'CAT_RAZON_NP', NULL, '0', 2, '0', 'No quiere participar');");
			db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('nomuestra', 'NM', 'CAT_RAZON_NP', NULL, '0', 1, '0', 'No quiere que le tomen muestras');");
            db.execSQL("INSERT INTO `chf_casas_cohorte_familia` (`codigoCHF`, `IDENTIFICADOR_EQUIPO`, `ESTADO`, `PASIVE`, `recordDate`, `recordUser`, `apellido1JefeFamilia`, `apellido2JefeFamilia`, `nombre1JefeFamilia`, `nombre2JefeFamilia`, `casa`) VALUES ('5678', 'server', '1', '0', '2017-05-10 11:22:29', 'admin', 'Lopez', 'Martinez', 'Pedro', 'Ramon', 1)");
            db.execSQL("INSERT INTO `participantes` (`codigo`, `nombre1`, `apellido1`, `nombre1Padre`, `apellido1Padre`, `nombre1Madre`, `apellido1Madre`, `casa`, `estado`, `fechaNac`, `sexo`, `PASIVE`) " +
            		"VALUES (123, 'Jose', 'Perez', 'Juan', 'Perez', 'Mirna', 'Lopez','1','1', '2013-09-12 13:36:45', 'M','0')");
            db.execSQL("INSERT INTO `chf_participantes` (`participanteCHF`, `participante`, `casaCHF`, `estado`, `PASIVE`) " +
            		"VALUES ('00-43883', '123', '5678','1','0')");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('dentro', 'DENTRO', 'CAT_DENTROFUERA', NULL, '0', 1, '0', 'Fuera');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('fuera', 'FUERA', 'CAT_DENTROFUERA', NULL, '0', 2, '0', 'Dentro');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('dentrofuera', 'DEFU', 'CAT_DENTROFUERA', NULL, '0', 3, '0', 'Dentro y Fuera');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('shared', 'COMPART', 'CAT_COMPARTIDO', NULL, '0', 1, '0', 'Compartido');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('not_shared', 'NOCOMPART', 'CAT_COMPARTIDO', NULL, '0', 2, '0', 'No compartido');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('madera', 'MADEP', 'CAT_MAT_PARED', NULL, '0', 1, '0', 'Madera');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('concretopared', 'CONCP', 'CAT_MAT_PARED', NULL, '0', 2, '0', 'Concreto');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('plasticopared', 'PLASTP', 'CAT_MAT_PARED', NULL, '0', 3, '0', 'Plástico');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('carton', 'CARTP', 'CAT_MAT_PARED', NULL, '0', 4, '0', 'Cartón');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('adobe', 'ADOBP', 'CAT_MAT_PARED', NULL, '0', 5, '0', 'Adobe');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('zincpared', 'ZINCP', 'CAT_MAT_PARED', NULL, '0', 6, '0', 'Zinc');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('otro', 'OTROP', 'CAT_MAT_PARED', NULL, '0', 7, '0', 'Otro');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('concretopiso', 'CONCPS', 'CAT_MAT_PISO', NULL, '0', 1, '0', 'Concreto');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('ladrillos', 'LADRPS', 'CAT_MAT_PISO', NULL, '0', 2, '0', 'Ladrillos');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('tierra', 'TIERPS', 'CAT_MAT_PISO', NULL, '0', 3, '0', 'Piso de tierra');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('ceramica', 'CERAPS', 'CAT_MAT_PISO', NULL, '0', 4, '0', 'Cerámica');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('otropiso', 'OTROPS', 'CAT_MAT_PISO', NULL, '0', 5, '0', 'Otro');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('zinctecho', 'ZINCT', 'CAT_MAT_TECHO', NULL, '0', 1, '0', 'Zinc');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('plasticotecho', 'PLAST', 'CAT_MAT_TECHO', NULL, '0', 2, '0', 'Plástico');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('teja', 'TEJAT', 'CAT_MAT_TECHO', NULL, '0', 3, '0', 'Tejas');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('otrotecho', 'OTROT', 'CAT_MAT_TECHO', NULL, '0', 4, '0', 'Otro');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('dia', 'DIA', 'CAT_FUN_ABANICO', NULL, '0', 1, '0', 'Día');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('noche', 'NOCHE', 'CAT_FUN_ABANICO', NULL, '0', 2, '0', 'Noche');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('diario', 'DR', 'CAT_PERIOD_COCINA', NULL, '0', 1, '0', 'Diario');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('semanal', 'SM', 'CAT_PERIOD_COCINA', NULL, '0', 2, '0', 'Semanal');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('quincenal', 'QC', 'CAT_PERIOD_COCINA', NULL, '0', 3, '0', 'Quincenal');");
            db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('mensual', 'MS', 'CAT_PERIOD_COCINA', NULL, '0', 4, '0', 'Mensual');");

        }

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			onCreate(db);
			if(oldVersion==0) return;
		}	
	}

	public EstudiosAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mContext,mPassword,mFromServer,mCleanDb);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}
	
	/**
	 * Crea un cursor desde la base de datos
	 * 
	 * @return cursor
	 */
	public Cursor crearCursor(String tabla, String whereString, String projection[], String ordenString) throws SQLException {
		Cursor c = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(tabla);
		c = qb.query(mDb,projection,whereString,null,null,null,ordenString);
		return c;
	}

	public static boolean createStorage() {
		return FileUtils.createFolder(FileUtils.DATABASE_PATH);
	}
	
	/**
	 * Metodos para usuarios en la base de datos
	 * 
	 * @param user
	 *            Objeto Usuario que contiene la informacion
	 *
	 */
	//Crear nuevo usuario en la base de datos
	public void crearUsuario(UserSistema user) {
		ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
		mDb.insert(MainDBConstants.USER_TABLE, null, cv);
	}
	//Editar usuario existente en la base de datos
	public boolean editarUsuario(UserSistema user) {
		ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
		return mDb.update(MainDBConstants.USER_TABLE, cv, MainDBConstants.username + "='" 
				+ user.getUsername()+"'", null) > 0;
	}
	//Limpiar la tabla de usuarios de la base de datos
	public boolean borrarUsuarios() {
		return mDb.delete(MainDBConstants.USER_TABLE, null, null) > 0;
	}
	//Obtener un usuario de la base de datos
	public UserSistema getUsuario(String filtro, String orden) throws SQLException {
		UserSistema mUser = null;
		Cursor cursorUser = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
		if (cursorUser != null && cursorUser.getCount() > 0) {
			cursorUser.moveToFirst();
			mUser=UserSistemaHelper.crearUserSistema(cursorUser);
		}
		if (!cursorUser.isClosed()) cursorUser.close();
		return mUser;
	}
	//Obtener una lista de usuarios de la base de datos
	public List<UserSistema> getUsuarios(String filtro, String orden) throws SQLException {
		List<UserSistema> mUsuarios = new ArrayList<UserSistema>();
		Cursor cursorUsuarios = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
		if (cursorUsuarios != null && cursorUsuarios.getCount() > 0) {
			cursorUsuarios.moveToFirst();
			mUsuarios.clear();
			do{
				UserSistema mUser = null;
				mUser = UserSistemaHelper.crearUserSistema(cursorUsuarios);
				mUsuarios.add(mUser);
			} while (cursorUsuarios.moveToNext());
		}
		if (!cursorUsuarios.isClosed()) cursorUsuarios.close();
		return mUsuarios;
	}
	
	/**
	 * Metodos para roles en la base de datos
	 * 
	 * @param rol
	 *            Objeto Authority que contiene la informacion
	 *
	 */
	//Crear nuevo rol en la base de datos
	public void crearRol(Authority rol) {
		ContentValues cv = UserSistemaHelper.crearRolValues(rol);
		mDb.insert(MainDBConstants.ROLE_TABLE, null, cv);
	}
	//Limpiar la tabla de roles de la base de datos
	public boolean borrarRoles() {
		return mDb.delete(MainDBConstants.ROLE_TABLE, null, null) > 0;
	}
	//Verificar un rol de usuario
	public Boolean buscarRol(String username, String Rol) throws SQLException {
		Cursor c = mDb.query(true, MainDBConstants.ROLE_TABLE, null,
				MainDBConstants.username + "='" + username + "' and " + MainDBConstants.role + "='" + Rol + "'" , null, null, null, null, null);
		boolean result = c != null && c.getCount()>0; 
		c.close();
		return result;
	}
	
	
	/**
	 * Metodos para barrios en la base de datos
	 * 
	 * @param barrio
	 *            Objeto Barrio que contiene la informacion
	 *
	 */
	//Crear nuevo barrio en la base de datos
	public void crearBarrio(Barrio barrio) {
		ContentValues cv = BarrioHelper.crearBarrioContentValues(barrio);
		mDb.insert(CatalogosDBConstants.BARRIO_TABLE, null, cv);
	}
	//Editar barrio existente en la base de datos
	public boolean editarBarrio(Barrio barrio) {
		ContentValues cv = BarrioHelper.crearBarrioContentValues(barrio);
		return mDb.update(CatalogosDBConstants.BARRIO_TABLE , cv, MainDBConstants.codigo + "=" 
				+ barrio.getCodigo(), null) > 0;
	}
	//Limpiar la tabla de barrios de la base de datos
	public boolean borrarBarrios() {
		return mDb.delete(CatalogosDBConstants.BARRIO_TABLE, null, null) > 0;
	}
	//Obtener un barrio de la base de datos
	public Barrio getBarrio(String filtro, String orden) throws SQLException {
		Barrio mBarrio = null;
		Cursor cursorBarrio = crearCursor(CatalogosDBConstants.BARRIO_TABLE , filtro, null, orden);
		if (cursorBarrio != null && cursorBarrio.getCount() > 0) {
			cursorBarrio.moveToFirst();
			mBarrio=BarrioHelper.crearBarrio(cursorBarrio);
		}
		if (!cursorBarrio.isClosed()) cursorBarrio.close();
		return mBarrio;
	}
	//Obtener una lista de barrios de la base de datos
	public List<Barrio> getBarrios(String filtro, String orden) throws SQLException {
		List<Barrio> mBarrios = new ArrayList<Barrio>();
		Cursor cursorBarrios = crearCursor(CatalogosDBConstants.BARRIO_TABLE, filtro, null, orden);
		if (cursorBarrios != null && cursorBarrios.getCount() > 0) {
			cursorBarrios.moveToFirst();
			mBarrios.clear();
			do{
				Barrio mBarrio = null;
				mBarrio = BarrioHelper.crearBarrio(cursorBarrios);
				mBarrios.add(mBarrio);
			} while (cursorBarrios.moveToNext());
		}
		if (!cursorBarrios.isClosed()) cursorBarrios.close();
		return mBarrios;
	}
	
	
	/**
	 * Metodos para estudios en la base de datos
	 * 
	 * @param estudio
	 *            Objeto Estudio que contiene la informacion
	 *
	 */
	//Crear nuevo estudio en la base de datos
	public void crearEstudio(Estudio estudio) {
		ContentValues cv = EstudiosHelper.crearEstudioContentValues(estudio);
		mDb.insert(CatalogosDBConstants.ESTUDIO_TABLE, null, cv);
	}
	//Editar estudio existente en la base de datos
	public boolean editarEstudio(Estudio estudio) {
		ContentValues cv = EstudiosHelper.crearEstudioContentValues(estudio);
		return mDb.update(CatalogosDBConstants.ESTUDIO_TABLE , cv, MainDBConstants.codigo + "=" 
				+ estudio.getCodigo(), null) > 0;
	}
	//Limpiar la tabla de estudios de la base de datos
	public boolean borrarEstudios() {
		return mDb.delete(CatalogosDBConstants.ESTUDIO_TABLE, null, null) > 0;
	}
	//Obtener un estudio de la base de datos
	public Estudio getEstudio(String filtro, String orden) throws SQLException {
		Estudio mEstudio = null;
		Cursor cursorEstudio = crearCursor(CatalogosDBConstants.ESTUDIO_TABLE , filtro, null, orden);
		if (cursorEstudio != null && cursorEstudio.getCount() > 0) {
			cursorEstudio.moveToFirst();
			mEstudio=EstudiosHelper.crearEstudio(cursorEstudio);
		}
		if (!cursorEstudio.isClosed()) cursorEstudio.close();
		return mEstudio;
	}
	//Obtener una lista de estudios de la base de datos
	public List<Estudio> getEstudios(String filtro, String orden) throws SQLException {
		List<Estudio> mEstudios = new ArrayList<Estudio>();
		Cursor cursorEstudios = crearCursor(CatalogosDBConstants.ESTUDIO_TABLE, filtro, null, orden);
		if (cursorEstudios != null && cursorEstudios.getCount() > 0) {
			cursorEstudios.moveToFirst();
			mEstudios.clear();
			do{
				Estudio mEstudio = null;
				mEstudio = EstudiosHelper.crearEstudio(cursorEstudios);
				mEstudios.add(mEstudio);
			} while (cursorEstudios.moveToNext());
		}
		if (!cursorEstudios.isClosed()) cursorEstudios.close();
		return mEstudios;
	}	
	
	
	/**
	 * Metodos para casas en la base de datos
	 * 
	 * @param casa
	 *            Objeto Casa que contiene la informacion
	 *
	 */
	//Crear nuevo Casa en la base de datos
	public void crearCasa(Casa casa) {
		ContentValues cv = CasaHelper.crearCasaContentValues(casa);
		mDb.insert(MainDBConstants.CASA_TABLE, null, cv);
	}
	//Editar Casa existente en la base de datos
	public boolean editarCasa(Casa casa) {
		ContentValues cv = CasaHelper.crearCasaContentValues(casa);
		return mDb.update(MainDBConstants.CASA_TABLE , cv, MainDBConstants.codigo + "=" 
				+ casa.getCodigo(), null) > 0;
	}
	//Limpiar la tabla de casas de la base de datos
	public boolean borrarCasas() {
		return mDb.delete(MainDBConstants.CASA_TABLE, null, null) > 0;
	}
	//Obtener un casa de la base de datos
	public Casa getCasa(String filtro, String orden) throws SQLException {
		Casa mCasa = null;
		Cursor cursorCasa = crearCursor(MainDBConstants.CASA_TABLE , filtro, null, orden);
		if (cursorCasa != null && cursorCasa.getCount() > 0) {
			cursorCasa.moveToFirst();
			mCasa=CasaHelper.crearCasa(cursorCasa);
			Barrio barrio = this.getBarrio(MainDBConstants.codigo + "=" +cursorCasa.getInt(cursorCasa.getColumnIndex(MainDBConstants.barrio)), orden);
			mCasa.setBarrio(barrio);
		}
		if (!cursorCasa.isClosed()) cursorCasa.close();
		return mCasa;
	}
	//Obtener una lista de casas de la base de datos
	public List<Casa> getCasas(String filtro, String orden) throws SQLException {
		List<Casa> mCasas = new ArrayList<Casa>();
		Cursor cursorCasas = crearCursor(MainDBConstants.CASA_TABLE, filtro, null, orden);
		if (cursorCasas != null && cursorCasas.getCount() > 0) {
			cursorCasas.moveToFirst();
			mCasas.clear();
			do{
				Casa mCasa = null;
				mCasa = CasaHelper.crearCasa(cursorCasas);
				Barrio barrio = this.getBarrio(MainDBConstants.codigo + "=" +cursorCasas.getInt(cursorCasas.getColumnIndex(MainDBConstants.barrio)), orden);
				mCasa.setBarrio(barrio);
				mCasas.add(mCasa);
			} while (cursorCasas.moveToNext());
		}
		if (!cursorCasas.isClosed()) cursorCasas.close();
		return mCasas;
	}
	
	
	/**
	 * Metodos para mensajes en la base de datos
	 * 
	 * @param mensaje
	 *            Objeto MessageResource que contiene la informacion
	 *
	 */
	//Crear nuevo MessageResource en la base de datos
	public void crearMessageResource(MessageResource mensaje) {
		ContentValues cv = MessageResourceHelper.crearMessageResourceValues(mensaje);
		mDb.insert(CatalogosDBConstants.MESSAGES_TABLE, null, cv);
	}
	//Editar MessageResource existente en la base de datos
	public boolean editarMessageResource(MessageResource mensaje) {
		ContentValues cv = MessageResourceHelper.crearMessageResourceValues(mensaje);
		return mDb.update(CatalogosDBConstants.MESSAGES_TABLE , cv, CatalogosDBConstants.messageKey + "='" 
				+ mensaje.getMessageKey() + "'", null) > 0;
	}
	//Limpiar la tabla de MessageResource de la base de datos
	public boolean borrarMessageResource() {
		return mDb.delete(CatalogosDBConstants.MESSAGES_TABLE, null, null) > 0;
	}
	//Obtener un MessageResource de la base de datos
	public MessageResource getMessageResource(String filtro, String orden) throws SQLException {
		MessageResource mMessageResource = null;
		Cursor cursorMessageResource = crearCursor(CatalogosDBConstants.MESSAGES_TABLE , filtro, null, orden);
		if (cursorMessageResource != null && cursorMessageResource.getCount() > 0) {
			cursorMessageResource.moveToFirst();
			mMessageResource=MessageResourceHelper.crearMessageResource(cursorMessageResource);
		}
		if (!cursorMessageResource.isClosed()) cursorMessageResource.close();
		return mMessageResource;
	}
	//Obtener una lista de MessageResource de la base de datos
	public List<MessageResource> getMessageResources(String filtro, String orden) throws SQLException {
		List<MessageResource> mMessageResources = new ArrayList<MessageResource>();
		Cursor cursorMessageResources = crearCursor(CatalogosDBConstants.MESSAGES_TABLE, filtro, null, orden);
		if (cursorMessageResources != null && cursorMessageResources.getCount() > 0) {
			cursorMessageResources.moveToFirst();
			mMessageResources.clear();
			do{
				MessageResource mMessageResource = null;
				mMessageResource = MessageResourceHelper.crearMessageResource(cursorMessageResources);
				mMessageResources.add(mMessageResource);
			} while (cursorMessageResources.moveToNext());
		}
		if (!cursorMessageResources.isClosed()) cursorMessageResources.close();
		return mMessageResources;
	}
	
	/**
	 * Metodos para visitas en la base de datos
	 * 
	 * @param visitaTerreno
	 *            Objeto VisitaTereno que contiene la informacion
	 *
	 */
	//Crear nuevo VisitaTerreno en la base de datos
	public void crearVisitaTereno(VisitaTerreno visitaTerreno) {
		ContentValues cv = VisitaTerrenoHelper.crearVisitaTerrenoContentValues(visitaTerreno);
		mDb.insert(MainDBConstants.VISITA_TABLE, null, cv);
	}
	//Editar VisitaTerreno existente en la base de datos
	public boolean editarVisitaTerreno(VisitaTerreno visitaTerreno) {
		ContentValues cv = VisitaTerrenoHelper.crearVisitaTerrenoContentValues(visitaTerreno);
		return mDb.update(MainDBConstants.VISITA_TABLE , cv, MainDBConstants.codigoVisita + "='" 
				+ visitaTerreno.getCodigoVisita()+ "'", null) > 0;
	}
	//Limpiar la tabla de VisitaTerreno de la base de datos
	public boolean borrarVisitasTerreno() {
		return mDb.delete(MainDBConstants.VISITA_TABLE, null, null) > 0;
	}
	//Obtener un VisitaTerreno de la base de datos
	public VisitaTerreno getVisitaTerreno(String filtro, String orden) throws SQLException {
		VisitaTerreno mVisitaTerreno = null;
		Cursor cursorVisitaTerreno = crearCursor(MainDBConstants.VISITA_TABLE , filtro, null, orden);
		if (cursorVisitaTerreno != null && cursorVisitaTerreno.getCount() > 0) {
			cursorVisitaTerreno.moveToFirst();
			mVisitaTerreno=VisitaTerrenoHelper.crearVisitaTerreno(cursorVisitaTerreno);
			Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorVisitaTerreno.getInt(cursorVisitaTerreno.getColumnIndex(MainDBConstants.casa)), null);
			mVisitaTerreno.setCasa(casa);
		}
		if (!cursorVisitaTerreno.isClosed()) cursorVisitaTerreno.close();
		return mVisitaTerreno;
	}
	//Obtener una lista de VisitaTerreno de la base de datos
	public List<VisitaTerreno> getVisitasTerreno(String filtro, String orden) throws SQLException {
		List<VisitaTerreno> mVisitasTerreno = new ArrayList<VisitaTerreno>();
		Cursor cursorVisitasTerreno = crearCursor(MainDBConstants.VISITA_TABLE, filtro, null, orden);
		if (cursorVisitasTerreno != null && cursorVisitasTerreno.getCount() > 0) {
			cursorVisitasTerreno.moveToFirst();
			mVisitasTerreno.clear();
			do{
				VisitaTerreno mVisitaTerreno = null;
				mVisitaTerreno = VisitaTerrenoHelper.crearVisitaTerreno(cursorVisitasTerreno);
				Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorVisitasTerreno.getInt(cursorVisitasTerreno.getColumnIndex(MainDBConstants.casa)), null);
				mVisitaTerreno.setCasa(casa);
				mVisitasTerreno.add(mVisitaTerreno);
			} while (cursorVisitasTerreno.moveToNext());
		}
		if (!cursorVisitasTerreno.isClosed()) cursorVisitasTerreno.close();
		return mVisitasTerreno;
	}	
	
	/**
	 * Metodos para pretamizajes en la base de datos
	 * 
	 * @param preTamizaje
	 *            Objeto PreTamizaje que contiene la informacion
	 *
	 */
	//Crear nuevo PreTamizaje en la base de datos
	public void crearPreTamizaje(PreTamizaje preTamizaje) {
		ContentValues cv = PreTamizajeHelper.crearPreTamizajeContentValues(preTamizaje);
		mDb.insert(MainDBConstants.PRETAMIZAJE_TABLE, null, cv);
	}
	//Editar PreTamizaje existente en la base de datos
	public boolean editarPreTamizaje(PreTamizaje preTamizaje) {
		ContentValues cv = PreTamizajeHelper.crearPreTamizajeContentValues(preTamizaje);
		return mDb.update(MainDBConstants.PRETAMIZAJE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ preTamizaje.getCodigo()+ "'", null) > 0;
	}
	//Limpiar la tabla de PreTamizaje de la base de datos
	public boolean borrarPreTamizajes() {
		return mDb.delete(MainDBConstants.PRETAMIZAJE_TABLE, null, null) > 0;
	}
	//Obtener un PreTamizaje de la base de datos
	public PreTamizaje getPreTamizaje(String filtro, String orden) throws SQLException {
		PreTamizaje mPreTamizaje = null;
		Cursor cursorPreTamizaje = crearCursor(MainDBConstants.PRETAMIZAJE_TABLE , filtro, null, orden);
		if (cursorPreTamizaje != null && cursorPreTamizaje.getCount() > 0) {
			cursorPreTamizaje.moveToFirst();
			mPreTamizaje=PreTamizajeHelper.crearPreTamizaje(cursorPreTamizaje);
			Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorPreTamizaje.getInt(cursorPreTamizaje.getColumnIndex(MainDBConstants.casa)), null);
			mPreTamizaje.setCasa(casa);
			Estudio estudio = this.getEstudio(MainDBConstants.codigo + "=" +cursorPreTamizaje.getInt(cursorPreTamizaje.getColumnIndex(MainDBConstants.estudio)), null);
			mPreTamizaje.setEstudio(estudio);
		}
		if (!cursorPreTamizaje.isClosed()) cursorPreTamizaje.close();
		return mPreTamizaje;
	}
	//Obtener una lista de PreTamizaje de la base de datos
	public List<PreTamizaje> getPreTamizajes(String filtro, String orden) throws SQLException {
		List<PreTamizaje> mPreTamizajes = new ArrayList<PreTamizaje>();
		Cursor cursorPreTamizajes = crearCursor(MainDBConstants.PRETAMIZAJE_TABLE, filtro, null, orden);
		if (cursorPreTamizajes != null && cursorPreTamizajes.getCount() > 0) {
			cursorPreTamizajes.moveToFirst();
			mPreTamizajes.clear();
			do{
				PreTamizaje mPreTamizaje = null;
				mPreTamizaje = PreTamizajeHelper.crearPreTamizaje(cursorPreTamizajes);
				Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorPreTamizajes.getInt(cursorPreTamizajes.getColumnIndex(MainDBConstants.casa)), null);
				mPreTamizaje.setCasa(casa);
				Estudio estudio = this.getEstudio(MainDBConstants.codigo + "=" +cursorPreTamizajes.getInt(cursorPreTamizajes.getColumnIndex(MainDBConstants.estudio)), null);
				mPreTamizaje.setEstudio(estudio);
				mPreTamizajes.add(mPreTamizaje);
			} while (cursorPreTamizajes.moveToNext());
		}
		if (!cursorPreTamizajes.isClosed()) cursorPreTamizajes.close();
		return mPreTamizajes;
	}

	/**
	 * Metodos para CasaCohorteFamilia en la base de datos
	 * 
	 * @param casaCHF
	 *            Objeto CasaCohorteFamilia que contiene la informacion
	 *
	 */
	//Crear nuevo CasaCohorteFamilia en la base de datos
	public void crearCasaCohorteFamilia(CasaCohorteFamilia casaCHF) {
		ContentValues cv = CasaCohorteFamiliaHelper.crearCasaCHFontentValues(casaCHF);
		mDb.insert(MainDBConstants.CASA_CHF_TABLE, null, cv);
	}
	//Editar CasaCohorteFamilia existente en la base de datos
	public boolean editarCasaCohorteFamilia(CasaCohorteFamilia casaCHF) {
		ContentValues cv = CasaCohorteFamiliaHelper.crearCasaCHFontentValues(casaCHF);
		return mDb.update(MainDBConstants.CASA_CHF_TABLE , cv, MainDBConstants.codigoCHF + "='" 
				+ casaCHF.getCodigoCHF()+ "'", null) > 0;
	}
	//Limpiar la tabla de CasaCohorteFamilia de la base de datos
	public boolean borrarCasaCohorteFamilias() {
		return mDb.delete(MainDBConstants.CASA_CHF_TABLE, null, null) > 0;
	}
	//Obtener una CasaCohorteFamilia de la base de datos
	public CasaCohorteFamilia getCasaCohorteFamilia(String filtro, String orden) throws SQLException {
		CasaCohorteFamilia mCasaCohorteFamilia = null;
		Cursor cursorCasaCohorteFamilia = crearCursor(MainDBConstants.CASA_CHF_TABLE , filtro, null, orden);
		if (cursorCasaCohorteFamilia != null && cursorCasaCohorteFamilia.getCount() > 0) {
			cursorCasaCohorteFamilia.moveToFirst();
			mCasaCohorteFamilia=CasaCohorteFamiliaHelper.crearCasaCHF(cursorCasaCohorteFamilia);
			Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorCasaCohorteFamilia.getInt(cursorCasaCohorteFamilia.getColumnIndex(MainDBConstants.casa)), null);
			mCasaCohorteFamilia.setCasa(casa);
		}
		if (!cursorCasaCohorteFamilia.isClosed()) cursorCasaCohorteFamilia.close();
		return mCasaCohorteFamilia;
	}
	//Obtener una lista de CasaCohorteFamilia de la base de datos
	public List<CasaCohorteFamilia> getCasaCohorteFamilias(String filtro, String orden) throws SQLException {
		List<CasaCohorteFamilia> mCasaCohorteFamilias = new ArrayList<CasaCohorteFamilia>();
		Cursor cursorCasaCohorteFamilia = crearCursor(MainDBConstants.CASA_CHF_TABLE, filtro, null, orden);
		if (cursorCasaCohorteFamilia != null && cursorCasaCohorteFamilia.getCount() > 0) {
			cursorCasaCohorteFamilia.moveToFirst();
			mCasaCohorteFamilias.clear();
			do{
				CasaCohorteFamilia mCasaCohorteFamilia = null;
				mCasaCohorteFamilia = CasaCohorteFamiliaHelper.crearCasaCHF(cursorCasaCohorteFamilia);
				Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorCasaCohorteFamilia.getInt(cursorCasaCohorteFamilia.getColumnIndex(MainDBConstants.casa)), null);
				mCasaCohorteFamilia.setCasa(casa);
				mCasaCohorteFamilias.add(mCasaCohorteFamilia);
			} while (cursorCasaCohorteFamilia.moveToNext());
		}
		if (!cursorCasaCohorteFamilia.isClosed()) cursorCasaCohorteFamilia.close();
		return mCasaCohorteFamilias;
	}
	
	/**
	 * Metodos para participantes en la base de datos
	 * 
	 * @param participante
	 *            Objeto Participante que contiene la informacion
	 *
	 */
	//Crear nuevo Participante en la base de datos
	public void crearParticipante(Participante participante) {
		ContentValues cv = ParticipanteHelper.crearParticipanteContentValues(participante);
		mDb.insert(MainDBConstants.PARTICIPANTE_TABLE, null, cv);
	}
	//Editar Participante existente en la base de datos
	public boolean editarParticipante(Participante participante) {
		ContentValues cv = ParticipanteHelper.crearParticipanteContentValues(participante);
		return mDb.update(MainDBConstants.PARTICIPANTE_TABLE , cv, MainDBConstants.codigo + "=" 
				+ participante.getCodigo(), null) > 0;
	}
	//Limpiar la tabla de Participante de la base de datos
	public boolean borrarParticipantes() {
		return mDb.delete(MainDBConstants.PARTICIPANTE_TABLE, null, null) > 0;
	}
	//Obtener una Participante de la base de datos
	public Participante getParticipante(String filtro, String orden) throws SQLException {
		Participante mParticipante = null;
		Cursor cursorParticipante = crearCursor(MainDBConstants.PARTICIPANTE_TABLE , filtro, null, orden);
		if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
			cursorParticipante.moveToFirst();
			mParticipante=ParticipanteHelper.crearParticipante(cursorParticipante);
			Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.casa)), null);
			mParticipante.setCasa(casa);
		}
		if (!cursorParticipante.isClosed()) cursorParticipante.close();
		return mParticipante;
	}
	//Obtener una lista de Participante de la base de datos
	public List<Participante> getParticipantes(String filtro, String orden) throws SQLException {
		List<Participante> mParticipantes = new ArrayList<Participante>();
		Cursor cursorParticipante = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, filtro, null, orden);
		if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
			cursorParticipante.moveToFirst();
			mParticipantes.clear();
			do{
				Participante mParticipante = null;
				mParticipante = ParticipanteHelper.crearParticipante(cursorParticipante);
				Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.casa)), null);
				mParticipante.setCasa(casa);
				mParticipantes.add(mParticipante);
			} while (cursorParticipante.moveToNext());
		}
		if (!cursorParticipante.isClosed()) cursorParticipante.close();
		return mParticipantes;
	}
	
	/**
	 * Metodos para ParticipanteCohorteFamilia en la base de datos
	 * 
	 * @param participanteCohorteFamilia
	 *            Objeto ParticipanteCohorteFamilia que contiene la informacion
	 *
	 */
	//Crear nuevo ParticipanteCohorteFamilia en la base de datos
	public void crearParticipanteCohorteFamilia(ParticipanteCohorteFamilia participanteCohorteFamilia) {
		ContentValues cv = ParticipanteCohorteFamiliaHelper.crearParticipanteCohorteFamiliaContentValues(participanteCohorteFamilia);
		mDb.insert(MainDBConstants.PARTICIPANTE_CHF_TABLE, null, cv);
	}
	//Editar ParticipanteCohorteFamilia existente en la base de datos
	public boolean editarParticipanteCohorteFamilia(ParticipanteCohorteFamilia participanteCohorteFamilia) {
		ContentValues cv = ParticipanteCohorteFamiliaHelper.crearParticipanteCohorteFamiliaContentValues(participanteCohorteFamilia);
		return mDb.update(MainDBConstants.PARTICIPANTE_CHF_TABLE , cv, MainDBConstants.participanteCHF + "='" 
				+ participanteCohorteFamilia.getParticipanteCHF()+"'", null) > 0;
	}
	//Limpiar la tabla de ParticipanteCohorteFamilia de la base de datos
	public boolean borrarParticipanteCohorteFamilias() {
		return mDb.delete(MainDBConstants.PARTICIPANTE_CHF_TABLE, null, null) > 0;
	}
	//Obtener una ParticipanteCohorteFamilia de la base de datos
	public ParticipanteCohorteFamilia getParticipanteCohorteFamilia(String filtro, String orden) throws SQLException {
		ParticipanteCohorteFamilia mParticipanteCohorteFamilia = null;
		Cursor cursorParticipanteCohorteFamilia = crearCursor(MainDBConstants.PARTICIPANTE_CHF_TABLE , filtro, null, orden);
		if (cursorParticipanteCohorteFamilia != null && cursorParticipanteCohorteFamilia.getCount() > 0) {
			cursorParticipanteCohorteFamilia.moveToFirst();
			mParticipanteCohorteFamilia=ParticipanteCohorteFamiliaHelper.crearParticipanteCohorteFamilia(cursorParticipanteCohorteFamilia);
			CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" +cursorParticipanteCohorteFamilia.getInt(cursorParticipanteCohorteFamilia.getColumnIndex(MainDBConstants.casaCHF)), null);
			mParticipanteCohorteFamilia.setCasaCHF(cchf);
			Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorParticipanteCohorteFamilia.getInt(cursorParticipanteCohorteFamilia.getColumnIndex(MainDBConstants.participante)), null);
			mParticipanteCohorteFamilia.setParticipante(participante);
		}
		if (!cursorParticipanteCohorteFamilia.isClosed()) cursorParticipanteCohorteFamilia.close();
		return mParticipanteCohorteFamilia;
	}
    //Obtener una lista de ParticipanteCohorteFamilia de la base de datos
    public ArrayList<ParticipanteCohorteFamilia> getParticipanteCohorteFamilias(String filtro, String orden) throws SQLException {
        ArrayList<ParticipanteCohorteFamilia> mParticipanteCohorteFamilias = new ArrayList<ParticipanteCohorteFamilia>();
        Cursor cursorParticipanteCohorteFamilia = crearCursor(MainDBConstants.PARTICIPANTE_CHF_TABLE, filtro, null, orden);
        if (cursorParticipanteCohorteFamilia != null && cursorParticipanteCohorteFamilia.getCount() > 0) {
            cursorParticipanteCohorteFamilia.moveToFirst();
            mParticipanteCohorteFamilias.clear();
            do{
                ParticipanteCohorteFamilia mParticipanteCohorteFamilia = null;
                mParticipanteCohorteFamilia = ParticipanteCohorteFamiliaHelper.crearParticipanteCohorteFamilia(cursorParticipanteCohorteFamilia);
                CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" +cursorParticipanteCohorteFamilia.getInt(cursorParticipanteCohorteFamilia.getColumnIndex(MainDBConstants.casaCHF)), null);
                mParticipanteCohorteFamilia.setCasaCHF(cchf);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorParticipanteCohorteFamilia.getInt(cursorParticipanteCohorteFamilia.getColumnIndex(MainDBConstants.participante)), null);
                mParticipanteCohorteFamilia.setParticipante(participante);
                mParticipanteCohorteFamilias.add(mParticipanteCohorteFamilia);
            } while (cursorParticipanteCohorteFamilia.moveToNext());
        }
        if (!cursorParticipanteCohorteFamilia.isClosed()) cursorParticipanteCohorteFamilia.close();
        return mParticipanteCohorteFamilias;
    }
    
	/**
	 * Metodos para tamizajes en la base de datos
	 * 
	 * @param tamizaje
	 *            Objeto Tamizaje que contiene la informacion
	 *
	 */
	//Crear nuevo Tamizaje en la base de datos
	public void crearTamizaje(Tamizaje tamizaje) {
		ContentValues cv = TamizajeHelper.crearTamizajeContentValues(tamizaje);
		mDb.insert(MainDBConstants.TAMIZAJE_TABLE, null, cv);
	}
	//Editar Tamizaje existente en la base de datos
	public boolean editarTamizaje(Tamizaje tamizaje) {
		ContentValues cv = TamizajeHelper.crearTamizajeContentValues(tamizaje);
		return mDb.update(MainDBConstants.TAMIZAJE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ tamizaje.getCodigo()+ "'", null) > 0;
	}
	//Limpiar la tabla de Tamizaje de la base de datos
	public boolean borrarTamizajes() {
		return mDb.delete(MainDBConstants.TAMIZAJE_TABLE, null, null) > 0;
	}
	//Obtener un Tamizaje de la base de datos
	public Tamizaje getTamizaje(String filtro, String orden) throws SQLException {
		Tamizaje mTamizaje = null;
		Cursor cursorTamizaje = crearCursor(MainDBConstants.TAMIZAJE_TABLE , filtro, null, orden);
		if (cursorTamizaje != null && cursorTamizaje.getCount() > 0) {
			cursorTamizaje.moveToFirst();
			mTamizaje=TamizajeHelper.crearTamizaje(cursorTamizaje);
			Estudio estudio = this.getEstudio(MainDBConstants.codigo + "=" +cursorTamizaje.getInt(cursorTamizaje.getColumnIndex(MainDBConstants.estudio)), null);
			mTamizaje.setEstudio(estudio);
		}
		if (!cursorTamizaje.isClosed()) cursorTamizaje.close();
		return mTamizaje;
	}
	//Obtener una lista de Tamizaje de la base de datos
	public List<Tamizaje> getTamizajes(String filtro, String orden) throws SQLException {
		List<Tamizaje> mTamizajes = new ArrayList<Tamizaje>();
		Cursor cursorTamizajes = crearCursor(MainDBConstants.TAMIZAJE_TABLE, filtro, null, orden);
		if (cursorTamizajes != null && cursorTamizajes.getCount() > 0) {
			cursorTamizajes.moveToFirst();
			mTamizajes.clear();
			do{
				Tamizaje mTamizaje = null;
				mTamizaje = TamizajeHelper.crearTamizaje(cursorTamizajes);
				Estudio estudio = this.getEstudio(MainDBConstants.codigo + "=" +cursorTamizajes.getInt(cursorTamizajes.getColumnIndex(MainDBConstants.estudio)), null);
				mTamizaje.setEstudio(estudio);
				mTamizajes.add(mTamizaje);
			} while (cursorTamizajes.moveToNext());
		}
		if (!cursorTamizajes.isClosed()) cursorTamizajes.close();
		return mTamizajes;
	}
	
	/**
	 * Metodos para carta de consentimiento en la base de datos
	 * 
	 * @param cartaConsentimiento
	 *            Objeto CartaConsentimiento que contiene la informacion
	 *
	 */
	//Crear nuevo CartaConsentimiento en la base de datos
	public void crearCartaConsentimiento(CartaConsentimiento cartaConsentimiento) {
		ContentValues cv = CartaConsentimientoHelper.crearCartaConsentimientoContentValues(cartaConsentimiento);
		mDb.insert(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, null, cv);
	}

    /**
     * Metodos para EncuestaCasa en la base de datos
     *
     * @param encuestaCasa
     *            Objeto EncuestasCasa que contiene la informacion
     *
     */
    //Crear nuevo EncuestasCasa en la base de datos
    public void crearEncuestaCasa(EncuestaCasa encuestaCasa) {
        ContentValues cv = EncuestaCasaHelper.crearEncuestaCasaContentValues(encuestaCasa);
        mDb.insert(EncuestasDBConstants.ENCUESTA_CASA_TABLE, null, cv);
    }
    //Editar EncuestasCasa existente en la base de datos
    public boolean editarEncuestaCasa(EncuestaCasa encuestaCasa) {
        ContentValues cv = EncuestaCasaHelper.crearEncuestaCasaContentValues(encuestaCasa);
        return mDb.update(EncuestasDBConstants.ENCUESTA_CASA_TABLE, cv, EncuestasDBConstants.casa_chf + "='"
                + encuestaCasa.getCasa().getCodigoCHF() + "'", null) > 0;
    }
    //Limpiar la tabla de EncuestasCasa de la base de datos
    public boolean borrarEncuestaCasas() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_CASA_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaCasa de la base de datos
    public EncuestaCasa getEncuestaCasa(String filtro, String orden) throws SQLException {
        EncuestaCasa mEncuestaCasa = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_CASA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestaCasa=EncuestaCasaHelper.crearEncuestaCasa(cursor);
            CasaCohorteFamilia casaCohorteFamilia = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.casa_chf)), null);
            if (casaCohorteFamilia != null) mEncuestaCasa.setCasa(casaCohorteFamilia);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestaCasa;
    }
    //Obtener una lista de EncuestasCasa de la base de datos
    public List<EncuestaCasa> getEncuestaCasas(String filtro, String orden) throws SQLException {
        List<EncuestaCasa> mEncuestas = new ArrayList<EncuestaCasa>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_CASA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaCasa mEncuesta = null;
                mEncuesta = EncuestaCasaHelper.crearEncuestaCasa(cursor);
                CasaCohorteFamilia casaCohorteFamilia = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.casa_chf)), null);
                if (casaCohorteFamilia != null) mEncuesta.setCasa(casaCohorteFamilia);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para EncuestaDatosPartoBB en la base de datos
     *
     * @param encuestaDatosPartoBB
     *            Objeto EncuestasDatosPartoBB que contiene la informacion
     *
     */
    //Crear nuevo EncuestasDatosPartoBB en la base de datos
    public void crearEncuestasDatosPartoBB(EncuestaDatosPartoBB encuestaDatosPartoBB) {
        ContentValues cv = EncuestaDatosPartoBBHelper.crearEncuestaDatosPartoBBContentValues(encuestaDatosPartoBB);
        mDb.insert(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE, null, cv);
    }
    //Editar EncuestasDatosPartoBB existente en la base de datos
    public boolean editarEncuestasDatosPartoBB(EncuestaDatosPartoBB encuestaDatosPartoBB) {
        ContentValues cv = EncuestaDatosPartoBBHelper.crearEncuestaDatosPartoBBContentValues(encuestaDatosPartoBB);
        return mDb.update(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE, cv, EncuestasDBConstants.participante_chf + "='"
                + encuestaDatosPartoBB.getParticipante().getParticipanteCHF() + "'", null) > 0;
    }
    //Limpiar la tabla de EncuestasDatosPartoBB de la base de datos
    public boolean borrarEncuestasDatosPartoBBs() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaDatosPartoBB de la base de datos
    public EncuestaDatosPartoBB getEncuestasDatosPartoBB(String filtro, String orden) throws SQLException {
        EncuestaDatosPartoBB mEncuestasDatosPartoBB = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasDatosPartoBB=EncuestaDatosPartoBBHelper.crearEncuestaDatosPartoBB(cursor);
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participanteCHF + "='" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante_chf)) + "'", null);
            if (participanteCohorteFamilia != null) mEncuestasDatosPartoBB.setParticipante(participanteCohorteFamilia);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasDatosPartoBB;
    }
    //Obtener una lista de EncuestasDatosPartoBB de la base de datos
    public List<EncuestaDatosPartoBB> getEncuestasDatosPartoBBs(String filtro, String orden) throws SQLException {
        List<EncuestaDatosPartoBB> mEncuestas = new ArrayList<EncuestaDatosPartoBB>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaDatosPartoBB mEncuesta = null;
                mEncuesta = EncuestaDatosPartoBBHelper.crearEncuestaDatosPartoBB(cursor);
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participanteCHF + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante_chf)), null);
                if (participanteCohorteFamilia != null) mEncuesta.setParticipante(participanteCohorteFamilia);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para EncuestaParticipante en la base de datos
     *
     * @param encuestaParticipante
     *            Objeto EncuestasParticipante que contiene la informacion
     *
     */
    //Crear nuevo EncuestasParticipante en la base de datos
    public void crearEncuestasParticipante(EncuestaParticipante encuestaParticipante) {
        ContentValues cv = EncuestaParticipanteHelper.crearEncuestaParticipanteContentValues(encuestaParticipante);
        mDb.insert(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, null, cv);
    }
    //Editar EncuestasParticipante existente en la base de datos
    public boolean editarEncuestasParticipante(EncuestaParticipante encuestaParticipante) {
        ContentValues cv = EncuestaParticipanteHelper.crearEncuestaParticipanteContentValues(encuestaParticipante);
        return mDb.update(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, cv, EncuestasDBConstants.participante_chf + "='"
                + encuestaParticipante.getParticipante().getParticipanteCHF() + "'", null) > 0;
    }
    //Limpiar la tabla de EncuestasParticipante de la base de datos
    public boolean borrarEncuestasParticipantes() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaParticipante de la base de datos
    public EncuestaParticipante getEncuestasParticipante(String filtro, String orden) throws SQLException {
        EncuestaParticipante mEncuestasParticipante = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasParticipante=EncuestaParticipanteHelper.crearEncuestaParticipante(cursor);
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participanteCHF + "='" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante_chf)) + "'", null);
            if (participanteCohorteFamilia != null) mEncuestasParticipante.setParticipante(participanteCohorteFamilia);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasParticipante;
    }
    //Obtener una lista de EncuestasParticipante de la base de datos
    public List<EncuestaParticipante> getEncuestasParticipantes(String filtro, String orden) throws SQLException {
        List<EncuestaParticipante> mEncuestas = new ArrayList<EncuestaParticipante>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaParticipante mEncuesta = null;
                mEncuesta = EncuestaParticipanteHelper.crearEncuestaParticipante(cursor);
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participanteCHF + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante_chf)), null);
                if (participanteCohorteFamilia != null) mEncuesta.setParticipante(participanteCohorteFamilia);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para EncuestaPesoTalla en la base de datos
     *
     * @param encuestaPesoTalla
     *            Objeto EncuestasPesoTalla que contiene la informacion
     *
     */
    //Crear nuevo EncuestasPesoTalla en la base de datos
    public void crearEncuestasPesoTalla(EncuestaPesoTalla encuestaPesoTalla) {
        ContentValues cv = EncuestaPesoTallaHelper.crearEncuestaPesoTallaContentValues(encuestaPesoTalla);
        mDb.insert(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, null, cv);
    }
    //Editar EncuestasPesoTalla existente en la base de datos
    public boolean editarEncuestasPesoTalla(EncuestaPesoTalla encuestaPesoTalla) {
        ContentValues cv = EncuestaPesoTallaHelper.crearEncuestaPesoTallaContentValues(encuestaPesoTalla);
        return mDb.update(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, cv, EncuestasDBConstants.participante_chf + "='"
                + encuestaPesoTalla.getParticipante().getParticipanteCHF() + "'", null) > 0;
    }
    //Limpiar la tabla de EncuestasPesoTalla de la base de datos
    public boolean borrarEncuestasPesoTallas() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaPesoTalla de la base de datos
    public EncuestaPesoTalla getEncuestasPesoTalla(String filtro, String orden) throws SQLException {
        EncuestaPesoTalla mEncuestasPesoTalla = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasPesoTalla=EncuestaPesoTallaHelper.crearEncuestaPesoTalla(cursor);
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participanteCHF + "='" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante_chf)) + "'", null);
            if (participanteCohorteFamilia != null) mEncuestasPesoTalla.setParticipante(participanteCohorteFamilia);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasPesoTalla;
    }
    //Obtener una lista de EncuestasPesoTalla de la base de datos
    public List<EncuestaPesoTalla> getEncuestasPesoTallas(String filtro, String orden) throws SQLException {
        List<EncuestaPesoTalla> mEncuestas = new ArrayList<EncuestaPesoTalla>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaPesoTalla mEncuesta = null;
                mEncuesta = EncuestaPesoTallaHelper.crearEncuestaPesoTalla(cursor);
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participanteCHF + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante_chf)), null);
                if (participanteCohorteFamilia != null) mEncuesta.setParticipante(participanteCohorteFamilia);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para EncuestaLactanciaMaterna en la base de datos
     *
     * @param encuestaLactanciaMaterna
     *            Objeto EncuestasLactanciaMaterna que contiene la informacion
     *
     */
    //Crear nuevo EncuestasLactanciaMaterna en la base de datos
    public void crearEncuestasLactanciaMaterna(EncuestaLactanciaMaterna encuestaLactanciaMaterna) {
        ContentValues cv = EncuestaLactanciaMatHelper.crearEncuestaLactanciaMaternaContentValues(encuestaLactanciaMaterna);
        mDb.insert(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE, null, cv);
    }
    //Editar EncuestasLactanciaMaterna existente en la base de datos
    public boolean editarEncuestasLactanciaMaterna(EncuestaLactanciaMaterna encuestaLactanciaMaterna) {
        ContentValues cv = EncuestaLactanciaMatHelper.crearEncuestaLactanciaMaternaContentValues(encuestaLactanciaMaterna);
        return mDb.update(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE, cv, EncuestasDBConstants.participante_chf + "='"
                + encuestaLactanciaMaterna.getParticipante().getParticipanteCHF() + "'", null) > 0;
    }
    //Limpiar la tabla de EncuestasLactanciaMaterna de la base de datos
    public boolean borrarEncuestasLactanciaMaternas() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaLactanciaMaterna de la base de datos
    public EncuestaLactanciaMaterna getEncuestasLactanciaMaterna(String filtro, String orden) throws SQLException {
        EncuestaLactanciaMaterna mEncuestasLactanciaMaterna = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasLactanciaMaterna=EncuestaLactanciaMatHelper.crearEncuestaLactanciaMaterna(cursor);
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participanteCHF + "='" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante_chf)) + "'", null);
            if (participanteCohorteFamilia != null) mEncuestasLactanciaMaterna.setParticipante(participanteCohorteFamilia);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasLactanciaMaterna;
    }
    //Obtener una lista de EncuestasLactanciaMaterna de la base de datos
    public List<EncuestaLactanciaMaterna> getEncuestasLactanciaMaternas(String filtro, String orden) throws SQLException {
        List<EncuestaLactanciaMaterna> mEncuestas = new ArrayList<EncuestaLactanciaMaterna>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaLactanciaMaterna mEncuesta = null;
                mEncuesta = EncuestaLactanciaMatHelper.crearEncuestaLactanciaMaterna(cursor);
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participanteCHF + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante_chf)), null);
                if (participanteCohorteFamilia != null) mEncuesta.setParticipante(participanteCohorteFamilia);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para Muestra en la base de datos
     *
     * @param muestra
     *            Objeto Muestras que contiene la informacion
     *
     */
    //Crear nuevo Muestras en la base de datos
    public void crearMuestras(Muestra muestra) {
        ContentValues cv = MuestraHelper.crearMuestraContentValues(muestra);
        mDb.insert(MuestrasDBConstants.MUESTRA_TABLE, null, cv);
    }
    //Editar Muestras existente en la base de datos
    public boolean editarMuestras(Muestra muestra) {
        ContentValues cv = MuestraHelper.crearMuestraContentValues(muestra);
        return mDb.update(MuestrasDBConstants.MUESTRA_TABLE, cv, MuestrasDBConstants.codigo + "='"
                + muestra.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de Muestras de la base de datos
    public boolean borrarMuestras() {
        return mDb.delete(MuestrasDBConstants.MUESTRA_TABLE, null, null) > 0;
    }
    //Obtener una Muestra de la base de datos
    public Muestra getMuestra(String filtro, String orden) throws SQLException {
        Muestra mMuestras = null;
        Cursor cursor = crearCursor(MuestrasDBConstants.MUESTRA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mMuestras=MuestraHelper.crearMuestra(cursor);
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participanteCHF + "='" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.participanteCHF)) + "'", null);
            if (participanteCohorteFamilia != null) mMuestras.setParticipanteCHF(participanteCohorteFamilia);
        }
        if (!cursor.isClosed()) cursor.close();
        return mMuestras;
    }
    //Obtener una lista de Muestras de la base de datos
    public List<Muestra> getMuestras(String filtro, String orden) throws SQLException {
        List<Muestra> mMuestras = new ArrayList<Muestra>();
        Cursor cursor = crearCursor(MuestrasDBConstants.MUESTRA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mMuestras.clear();
            do{
                Muestra mMuestra = null;
                mMuestra = MuestraHelper.crearMuestra(cursor);
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participanteCHF + "=" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.participanteCHF)), null);
                if (participanteCohorteFamilia != null) mMuestra.setParticipanteCHF(participanteCohorteFamilia);
                mMuestras.add(mMuestra);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mMuestras;
    }

    /**
     * Metodos para Paxgene en la base de datos
     *
     * @param paxgene
     *            Objeto Paxgenes que contiene la informacion
     *
     */
    //Crear nuevo Paxgenes en la base de datos
    public void crearPaxgenes(Paxgene paxgene) {
        ContentValues cv = MuestraHelper.crearPaxgeneContentValues(paxgene);
        mDb.insert(MuestrasDBConstants.PAXGENE_TABLE, null, cv);
    }
    //Editar Paxgenes existente en la base de datos
    public boolean editarPaxgenes(Paxgene paxgene) {
        ContentValues cv = MuestraHelper.crearPaxgeneContentValues(paxgene);
        return mDb.update(MuestrasDBConstants.PAXGENE_TABLE, cv, MuestrasDBConstants.codigoMx + "='"
                + paxgene.getMuestra().getCodigoMx() + "'", null) > 0;
    }
    //Limpiar la tabla de Paxgenes de la base de datos
    public boolean borrarPaxgeness() {
        return mDb.delete(MuestrasDBConstants.PAXGENE_TABLE, null, null) > 0;
    }
    //Obtener una Paxgene de la base de datos
    public Paxgene getPaxgene(String filtro, String orden) throws SQLException {
        Paxgene mPaxgene = null;
        Cursor cursor = crearCursor(MuestrasDBConstants.PAXGENE_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mPaxgene=MuestraHelper.crearPaxgene(cursor);
            Muestra muestra = this.getMuestra(MuestrasDBConstants.codigoMx + "='" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.codigoMx)) + "'", null);
            if (muestra != null) mPaxgene.setMuestra(muestra);
        }
        if (!cursor.isClosed()) cursor.close();
        return mPaxgene;
    }
    //Obtener una lista de Paxgenes de la base de datos
    public List<Paxgene> getPaxgenes(String filtro, String orden) throws SQLException {
        List<Paxgene> mPaxgenes = new ArrayList<Paxgene>();
        Cursor cursor = crearCursor(MuestrasDBConstants.PAXGENE_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mPaxgenes.clear();
            do{
                Paxgene mPaxgene = null;
                mPaxgene = MuestraHelper.crearPaxgene(cursor);
                Muestra muestra = this.getMuestra(MuestrasDBConstants.codigoMx + "='" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.codigoMx)) + "'", null);
                if (muestra != null) mPaxgene.setMuestra(muestra);
                mPaxgenes.add(mPaxgene);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mPaxgenes;
    }
    
	/**
	 * Metodos para Habitaciones en la base de datos
	 * 
	 * @param Habitacion
	 *            Objeto Habitacion que contiene la informacion
	 *
	 */
	//Crear nuevo Habitacion en la base de datos
	public void crearHabitacion(Habitacion habitacion) {
		ContentValues cv = AreaAmbienteHelper.crearHabitacionContentValues(habitacion);
		mDb.insert(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Habitacion existente en la base de datos
	public boolean editarHabitacion(Habitacion habitacion) {
		ContentValues cv = AreaAmbienteHelper.crearHabitacionContentValues(habitacion);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ habitacion.getCodigo()+ "'", null) > 0;
	}
	//Limpiar la tabla de AREA_AMBIENTE_TABLE de la base de datos
	public boolean borrarAreasAmbiente() {
		return mDb.delete(MainDBConstants.AREA_AMBIENTE_TABLE, null, null) > 0;
	}
	//Obtener un Habitacion de la base de datos
	public Habitacion getHabitacion(String filtro, String orden) throws SQLException {
		Habitacion mHabitacion = null;
		Cursor cursorHabitacion = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorHabitacion != null && cursorHabitacion.getCount() > 0) {
			cursorHabitacion.moveToFirst();
			mHabitacion=AreaAmbienteHelper.crearHabitacion(cursorHabitacion);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorHabitacion.getString(cursorHabitacion.getColumnIndex(MainDBConstants.casa))+"'", null);
			mHabitacion.setCasa(casa);
		}
		if (!cursorHabitacion.isClosed()) cursorHabitacion.close();
		return mHabitacion;
	}
	//Obtener una lista de Habitacion de la base de datos
	public List<Habitacion> getHabitaciones(String filtro, String orden) throws SQLException {
		List<Habitacion> mHabitaciones = new ArrayList<Habitacion>();
		Cursor cursorHabitaciones = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorHabitaciones != null && cursorHabitaciones.getCount() > 0) {
			cursorHabitaciones.moveToFirst();
			mHabitaciones.clear();
			do{
				Habitacion mHabitacion = null;
				mHabitacion = AreaAmbienteHelper.crearHabitacion(cursorHabitaciones);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorHabitaciones.getString(cursorHabitaciones.getColumnIndex(MainDBConstants.casa))+"'", null);
				mHabitacion.setCasa(casa);
				mHabitaciones.add(mHabitacion);
			} while (cursorHabitaciones.moveToNext());
		}
		if (!cursorHabitaciones.isClosed()) cursorHabitaciones.close();
		return mHabitaciones;
	}   
	
	/**
	 * Metodos para Camas en la base de datos
	 * 
	 * @param Cama
	 *            Objeto Cama que contiene la informacion
	 *
	 */
	//Crear nuevo Cama en la base de datos
	public void crearCama(Cama cama) {
		ContentValues cv = CamasHelper.crearCamaContentValues(cama);
		mDb.insert(MainDBConstants.CAMA_TABLE, null, cv);
	}
	//Editar Cama existente en la base de datos
	public boolean editarCama(Cama cama) {
		ContentValues cv = CamasHelper.crearCamaContentValues(cama);
		return mDb.update(MainDBConstants.CAMA_TABLE , cv, MainDBConstants.codigoCama + "='" 
				+ cama.getCodigoCama()+ "'", null) > 0;
	}
	//Limpiar la tabla de Camas de la base de datos
	public boolean borrarCamas() {
		return mDb.delete(MainDBConstants.CAMA_TABLE, null, null) > 0;
	}
	//Obtener un Cama de la base de datos
	public Cama getCama(String filtro, String orden) throws SQLException {
		Cama mCama = null;
		Cursor cursorCama = crearCursor(MainDBConstants.CAMA_TABLE , filtro, null, orden);
		if (cursorCama != null && cursorCama.getCount() > 0) {
			cursorCama.moveToFirst();
			mCama=CamasHelper.crearCama(cursorCama);
			Habitacion hab = this.getHabitacion(MainDBConstants.codigo + "='" +cursorCama.getString(cursorCama.getColumnIndex(MainDBConstants.habitacion))+"'", null);
			mCama.setHabitacion(hab);
		}
		if (!cursorCama.isClosed()) cursorCama.close();
		return mCama;
	}
	//Obtener una lista de Cama de la base de datos
	public List<Cama> getCamas(String filtro, String orden) throws SQLException {
		List<Cama> mCamas = new ArrayList<Cama>();
		Cursor cursorCamas = crearCursor(MainDBConstants.CAMA_TABLE, filtro, null, orden);
		if (cursorCamas != null && cursorCamas.getCount() > 0) {
			cursorCamas.moveToFirst();
			mCamas.clear();
			do{
				Cama mCama = null;
				mCama = CamasHelper.crearCama(cursorCamas);
				Habitacion hab = this.getHabitacion(MainDBConstants.codigo + "='" +cursorCamas.getString(cursorCamas.getColumnIndex(MainDBConstants.habitacion))+"'", null);
				mCama.setHabitacion(hab);
				mCamas.add(mCama);
			} while (cursorCamas.moveToNext());
		}
		if (!cursorCamas.isClosed()) cursorCamas.close();
		return mCamas;
	}
}
