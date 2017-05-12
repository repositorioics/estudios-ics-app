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
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.PreTamizaje;
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
			db.execSQL(MainDBConstants.CREATE_PRETAMIZAJE_TABLE);
            db.execSQL(MainDBConstants.CREATE_CASA_CHF_TABLE);
            db.execSQL(MainDBConstants.CREATE_PARTICIPANTE_TALBE);
            db.execSQL(MainDBConstants.CREATE_PARTICIPANTE_CHF_TABLE);
            
			db.execSQL("INSERT INTO `barrios` (`CODIGO`, `identificador_equipo`, `ESTADO`, `PASIVE`, `recordDate`, `recordUser`, `NOMBRE`) VALUES (1, 'server', '1', '0', '2017-05-10 11:01:26', 'admin', 'Cuba')");
			db.execSQL("INSERT INTO `estudios` (`CODIGO`, `identificador_equipo`, `ESTADO`, `PASIVE`, `recordDate`, `recordUser`, `NOMBRE`) VALUES (1, 'server', '1', '0', '2017-05-10 11:01:26', 'admin', 'Cohorte Familia')");
			db.execSQL("INSERT INTO `casas` (`CODIGO`, `IDENTIFICADOR_EQUIPO`, `ESTADO`, `PASIVE`, `recordDate`, `recordUser`, `apellido1JefeFamilia`, `apellido2JefeFamilia`, `DIRECCION`, `MANZANA`, `nombre1JefeFamilia`, `nombre2JefeFamilia`, `barrio`) VALUES (1, 'server', '1', '0', '2017-05-10 11:22:29', 'admin', 'Lopez', 'Martinez', 'sfgsgsgsd', '3', 'Pedro', 'Ramon', 1)");
			db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('no', '0', 'CAT_SINO', NULL, '0', 2, '0', 'No');");
			db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('yes', '1', 'CAT_SINO', NULL, '0', 1, '0', 'Si');");
			db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('noquiere', 'NQ', 'CAT_RAZON_NP', NULL, '0', 2, '0', 'No quiere participar');");
			db.execSQL("INSERT INTO `mensajes` (`messageKey`, `catKey`, `catRoot`, `english`, `isCat`, `orden`, `pasive`, `spanish`) VALUES ('nomuestra', 'NM', 'CAT_RAZON_NP', NULL, '0', 1, '0', 'No quiere que le tomen muestras');");
            db.execSQL("INSERT INTO `chf_casas_cohorte_familia` (`codigoCHF`, `IDENTIFICADOR_EQUIPO`, `ESTADO`, `PASIVE`, `recordDate`, `recordUser`, `apellido1JefeFamilia`, `apellido2JefeFamilia`, `nombre1JefeFamilia`, `nombre2JefeFamilia`, `casa`) VALUES ('1', 'server', '1', '0', '2017-05-10 11:22:29', 'admin', 'Lopez', 'Martinez', 'Pedro', 'Ramon', 1)");
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
	public List<ParticipanteCohorteFamilia> getParticipanteCohorteFamilias(String filtro, String orden) throws SQLException {
		List<ParticipanteCohorteFamilia> mParticipanteCohorteFamilias = new ArrayList<ParticipanteCohorteFamilia>();
		Cursor cursorParticipanteCohorteFamilia = crearCursor(MainDBConstants.PARTICIPANTE_CHF_TABLE, filtro, null, orden);
		if (cursorParticipanteCohorteFamilia != null && cursorParticipanteCohorteFamilia.getCount() > 0) {
			cursorParticipanteCohorteFamilia.moveToFirst();
			mParticipanteCohorteFamilias.clear();
			do{
				ParticipanteCohorteFamilia mParticipanteCohorteFamilia = null;
				mParticipanteCohorteFamilia = ParticipanteCohorteFamiliaHelper.crearParticipanteCohorteFamilia(cursorParticipanteCohorteFamilia);
				Cursor cursorCasaCHF = crearCursor(MainDBConstants.CASA_CHF_TABLE , MainDBConstants.codigoCHF + "=" +cursorParticipanteCohorteFamilia.getInt(cursorParticipanteCohorteFamilia.getColumnIndex(MainDBConstants.casaCHF)), null, MainDBConstants.codigoCHF);
				cursorCasaCHF.moveToFirst();
				if (cursorCasaCHF != null && cursorCasaCHF.getCount() > 0) {
					mParticipanteCohorteFamilia.setCasaCHF(CasaCohorteFamiliaHelper.crearCasaCHF(cursorCasaCHF));
				}
				if (!cursorCasaCHF.isClosed()) cursorCasaCHF.close();
				mParticipanteCohorteFamilias.add(mParticipanteCohorteFamilia);
			} while (cursorParticipanteCohorteFamilia.moveToNext());
		}
		if (!cursorParticipanteCohorteFamilia.isClosed()) cursorParticipanteCohorteFamilia.close();
		return mParticipanteCohorteFamilias;
	}	

}
