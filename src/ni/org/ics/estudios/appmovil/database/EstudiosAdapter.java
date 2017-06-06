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
import ni.org.ics.estudios.appmovil.domain.TelefonoContacto;
import ni.org.ics.estudios.appmovil.domain.VisitaTerreno;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.*;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaCasaSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaParticipanteSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.domain.users.Authority;
import ni.org.ics.estudios.appmovil.domain.users.UserSistema;
import ni.org.ics.estudios.appmovil.helpers.*;
import ni.org.ics.estudios.appmovil.helpers.seroprevalencia.EncuestaCasaSAHelper;
import ni.org.ics.estudios.appmovil.helpers.seroprevalencia.EncuestaParticipanteSAHelper;
import ni.org.ics.estudios.appmovil.helpers.seroprevalencia.ParticipanteSeroprevalenciaHelper;
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
            db.execSQL(MainDBConstants.CREATE_AREA_AMBIENTE_TABLE);
            db.execSQL(MainDBConstants.CREATE_CAMA_TABLE);
            db.execSQL(MainDBConstants.CREATE_PERSONACAMA_TABLE);
            db.execSQL(MainDBConstants.CREATE_TAMIZAJE_TABLE);
            db.execSQL(MainDBConstants.CREATE_CUARTO_TABLE);
            db.execSQL(SeroprevalenciaDBConstants.CREATE_PARTICIPANTESA_TABLE);
            db.execSQL(SeroprevalenciaDBConstants.CREATE_ENCUESTA_CASASA_TABLE);
            db.execSQL(SeroprevalenciaDBConstants.CREATE_ENCUESTA_PARTICIPANTESA_TABLE);
            db.execSQL(MainDBConstants.CREATE_TELEFONO_CONTACTO_TABLE);
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
		return mDb.update(MainDBConstants.PARTICIPANTE_CHF_TABLE , cv, MainDBConstants.participante + "="
				+ participanteCohorteFamilia.getParticipante().getCodigo(), null) > 0;
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
    //Editar EncuestasCasa existente en la base de datos
    public boolean editarCartaConsentimiento(CartaConsentimiento cartaConsentimiento) {
        ContentValues cv = CartaConsentimientoHelper.crearCartaConsentimientoContentValues(cartaConsentimiento);
        return mDb.update(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, cv, MainDBConstants.codigo + "='"
                + cartaConsentimiento.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de Tamizaje de la base de datos
    public boolean borrarCartasConsentimiento() {
        return mDb.delete(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, null, null) > 0;
    }

    //Obtener una lista de ParticipanteCohorteFamilia de la base de datos
    public CartaConsentimiento getCartaConsentimientos(String filtro, String orden) throws SQLException {
        CartaConsentimiento cartaConsentimiento = null;
        Cursor cursorCarta = crearCursor(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE , filtro, null, orden);
        if (cursorCarta != null && cursorCarta.getCount() > 0) {
            cursorCarta.moveToFirst();
            cartaConsentimiento=CartaConsentimientoHelper.crearCartaConsentimiento(cursorCarta);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorCarta.getInt(cursorCarta.getColumnIndex(MainDBConstants.participante)), null);
            cartaConsentimiento.setParticipante(participante);
            Tamizaje tamizaje = this.getTamizaje(MainDBConstants.codigo + "='" +cursorCarta.getString(cursorCarta.getColumnIndex(MainDBConstants.tamizaje))+"'", null);
            cartaConsentimiento.setTamizaje(tamizaje);
        }
        if (!cursorCarta.isClosed()) cursorCarta.close();
        return cartaConsentimiento;
    }
    //Obtener una lista de ParticipanteCohorteFamilia de la base de datos
    public ArrayList<CartaConsentimiento> getCartasConsentimientos(String filtro, String orden) throws SQLException {
        ArrayList<CartaConsentimiento> mParticipanteCohorteFamilias = new ArrayList<CartaConsentimiento>();
        Cursor cursorCarta = crearCursor(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, filtro, null, orden);
        if (cursorCarta != null && cursorCarta.getCount() > 0) {
            cursorCarta.moveToFirst();
            mParticipanteCohorteFamilias.clear();
            do{
                CartaConsentimiento cartaConsentimiento = null;
                cartaConsentimiento = CartaConsentimientoHelper.crearCartaConsentimiento(cursorCarta);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorCarta.getInt(cursorCarta.getColumnIndex(MainDBConstants.participante)), null);
                cartaConsentimiento.setParticipante(participante);
                Tamizaje tamizaje = this.getTamizaje(MainDBConstants.codigo + "='" +cursorCarta.getString(cursorCarta.getColumnIndex(MainDBConstants.tamizaje))+"'", null);
                cartaConsentimiento.setTamizaje(tamizaje);
                mParticipanteCohorteFamilias.add(cartaConsentimiento);
            } while (cursorCarta.moveToNext());
        }
        if (!cursorCarta.isClosed()) cursorCarta.close();
        return mParticipanteCohorteFamilias;
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
        return mDb.update(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE, cv, EncuestasDBConstants.participante + "="
                + encuestaDatosPartoBB.getParticipante().getParticipante().getCodigo(), null) > 0;
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
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
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
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
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
        return mDb.update(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, cv, EncuestasDBConstants.participante + "="
                + encuestaParticipante.getParticipante().getParticipante().getCodigo(), null) > 0;
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
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
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
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
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
        return mDb.update(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, cv, EncuestasDBConstants.participante + "="
                + encuestaPesoTalla.getParticipante().getParticipante().getCodigo(), null) > 0;
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
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
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
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
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
        return mDb.update(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE, cv, EncuestasDBConstants.participante + "="
                + encuestaLactanciaMaterna.getParticipante().getParticipante().getCodigo(), null) > 0;
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
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
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
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
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
            Participante participante = this.getParticipante(MuestrasDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.participante)), null);
            if (participante != null) mMuestras.setParticipante(participante);
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
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.participante)), null);
                if (participante != null) mMuestra.setParticipante(participante);
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
    /*Crear nuevo Paxgenes en la base de datos
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
    }*/
    
	/**
	 * Metodos para Habitaciones en la base de datos
	 * 
	 * @param habitacion
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
	 * @param cama
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
			Cuarto cuarto = this.getCuarto(MainDBConstants.codigo + "='" + cursorCama.getString(cursorCama.getColumnIndex(MainDBConstants.habitacion)) + "'", null);
			mCama.setCuarto(cuarto);
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
				Cuarto cuarto = this.getCuarto(MainDBConstants.codigo + "='" + cursorCamas.getString(cursorCamas.getColumnIndex(MainDBConstants.habitacion)) + "'", null);
				mCama.setCuarto(cuarto);
				mCamas.add(mCama);
			} while (cursorCamas.moveToNext());
		}
		if (!cursorCamas.isClosed()) cursorCamas.close();
		return mCamas;
	}
	
	
	/**
	 * Metodos para PersonaCama en la base de datos
	 * 
	 * @param personacama
	 *            Objeto PersonaCama que contiene la informacion
	 *
	 */
	//Crear nuevo PersonaCama en la base de datos
	public void crearPersonaCama(PersonaCama personacama) {
		ContentValues cv = CamasHelper.crearPersonaCamaContentValues(personacama);
		mDb.insert(MainDBConstants.PERSONACAMA_TABLE, null, cv);
	}
	//Editar Cama existente en la base de datos
	public boolean editarPersonaCama(PersonaCama personacama) {
		ContentValues cv = CamasHelper.crearPersonaCamaContentValues(personacama);
		return mDb.update(MainDBConstants.PERSONACAMA_TABLE , cv, MainDBConstants.codigoPersona + "='" 
				+ personacama.getCodigoPersona()+ "'", null) > 0;
	}
	//Limpiar la tabla de PersonaCama de la base de datos
	public boolean borrarPersonasCama() {
		return mDb.delete(MainDBConstants.PERSONACAMA_TABLE, null, null) > 0;
	}
	//Obtener un PersonaCama de la base de datos
	public PersonaCama getPersonaCama(String filtro, String orden) throws SQLException {
		PersonaCama mPersonaCama = null;
		Cursor cursorPersonaCama = crearCursor(MainDBConstants.PERSONACAMA_TABLE , filtro, null, orden);
		if (cursorPersonaCama != null && cursorPersonaCama.getCount() > 0) {
			cursorPersonaCama.moveToFirst();
			mPersonaCama=CamasHelper.crearPersonaCama(cursorPersonaCama);
			Cama cama = this.getCama(MainDBConstants.codigoCama + "='" + cursorPersonaCama.getString(cursorPersonaCama.getColumnIndex(MainDBConstants.cama)) + "'", null);
			Participante part = this.getParticipante(MainDBConstants.codigo + "=" + cursorPersonaCama.getString(cursorPersonaCama.getColumnIndex(MainDBConstants.participante)), null);
			mPersonaCama.setCama(cama);
			if (part!= null) mPersonaCama.setParticipante(part);
		}
		if (!cursorPersonaCama.isClosed()) cursorPersonaCama.close();
		return mPersonaCama;
	}
	//Obtener una lista de PersonaCama de la base de datos
	public List<PersonaCama> getPersonasCama(String filtro, String orden) throws SQLException {
		List<PersonaCama> mPersonasCama = new ArrayList<PersonaCama>();
		Cursor cursorPersonasCama = crearCursor(MainDBConstants.PERSONACAMA_TABLE, filtro, null, orden);
		if (cursorPersonasCama != null && cursorPersonasCama.getCount() > 0) {
			cursorPersonasCama.moveToFirst();
			mPersonasCama.clear();
			do{
				PersonaCama mPersonaCama = null;
				mPersonaCama = CamasHelper.crearPersonaCama(cursorPersonasCama);
				Cama cama = this.getCama(MainDBConstants.codigoCama + "='" + cursorPersonasCama.getString(cursorPersonasCama.getColumnIndex(MainDBConstants.cama)) + "'", null);
				Participante part = this.getParticipante(MainDBConstants.codigo + "=" + cursorPersonasCama.getString(cursorPersonasCama.getColumnIndex(MainDBConstants.participante)), null);
				if (part!= null) mPersonaCama.setParticipante(part);
				mPersonaCama.setCama(cama);
				mPersonasCama.add(mPersonaCama);
			} while (cursorPersonasCama.moveToNext());
		}
		if (!cursorPersonasCama.isClosed()) cursorPersonasCama.close();
		return mPersonasCama;
	}
	
	/**
	 * Metodos para AreaAmbiente en la base de datos
	 * 
	 * @param area
	 *            Objeto AreaAmbiente que contiene la informacion
	 *
	 */
	//Crear nuevo AreaAmbiente en la base de datos
	public void crearAreaAmbiente(AreaAmbiente area) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(area);
		mDb.insert(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar AreaAmbiente existente en la base de datos
	public boolean editarAreaAmbiente(AreaAmbiente area) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(area);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ area.getCodigo()+ "'", null) > 0;
	}

	//Obtener un AreaAmbiente de la base de datos
	public AreaAmbiente getAreaAmbiente(String filtro, String orden) throws SQLException {
		AreaAmbiente mAreaAmbiente = null;
		Cursor cursorAreaAmbiente = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorAreaAmbiente != null && cursorAreaAmbiente.getCount() > 0) {
			cursorAreaAmbiente.moveToFirst();
			mAreaAmbiente=AreaAmbienteHelper.crearAreaAmbiente(cursorAreaAmbiente);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorAreaAmbiente.getString(cursorAreaAmbiente.getColumnIndex(MainDBConstants.casa))+"'", null);
			mAreaAmbiente.setCasa(casa);
		}
		if (!cursorAreaAmbiente.isClosed()) cursorAreaAmbiente.close();
		return mAreaAmbiente;
	}
	//Obtener una lista de AreaAmbiente de la base de datos
	public List<AreaAmbiente> getAreasAmbiente(String filtro, String orden) throws SQLException {
		List<AreaAmbiente> mAreasAmbiente = new ArrayList<AreaAmbiente>();
		Cursor cursorAreasAmbiente = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorAreasAmbiente != null && cursorAreasAmbiente.getCount() > 0) {
			cursorAreasAmbiente.moveToFirst();
			mAreasAmbiente.clear();
			do{
				AreaAmbiente mAreaAmbiente = null;
				mAreaAmbiente = AreaAmbienteHelper.crearAreaAmbiente(cursorAreasAmbiente);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorAreasAmbiente.getString(cursorAreasAmbiente.getColumnIndex(MainDBConstants.casa))+"'", null);
				mAreaAmbiente.setCasa(casa);
				mAreasAmbiente.add(mAreaAmbiente);
			} while (cursorAreasAmbiente.moveToNext());
		}
		if (!cursorAreasAmbiente.isClosed()) cursorAreasAmbiente.close();
		return mAreasAmbiente;
	}  
	
	/**
	 * Metodos para Banio en la base de datos
	 * 
	 * @param banio
	 *            Objeto Banio que contiene la informacion
	 *
	 */
	//Crear nuevo Banio en la base de datos
	public void crearBanio(Banio banio) {
		ContentValues cv = AreaAmbienteHelper.crearBanioContentValues(banio);
		mDb.insert(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Banio existente en la base de datos
	public boolean editarBanio(Banio banio) {
		ContentValues cv = AreaAmbienteHelper.crearBanioContentValues(banio);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ banio.getCodigo()+ "'", null) > 0;
	}

	//Obtener un Banio de la base de datos
	public Banio getBanio(String filtro, String orden) throws SQLException {
		Banio mBanio = null;
		Cursor cursorBanio = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorBanio != null && cursorBanio.getCount() > 0) {
			cursorBanio.moveToFirst();
			mBanio=AreaAmbienteHelper.crearBanio(cursorBanio);
			AreaAmbiente area = this.getAreaAmbiente(MainDBConstants.codigo + "='" + cursorBanio.getString(cursorBanio.getColumnIndex(MainDBConstants.areaAmbiente))+ "'", null);
			if(area != null){
				mBanio.setAreaAmbiente(area);
				mBanio.setCasa(area.getCasa());
			}
			else{
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorBanio.getString(cursorBanio.getColumnIndex(MainDBConstants.casa))+"'", null);
				mBanio.setCasa(casa);
			}
		}
		if (!cursorBanio.isClosed()) cursorBanio.close();
		return mBanio;
	}
	//Obtener una lista de Banio de la base de datos
	public List<Banio> getBanios(String filtro, String orden) throws SQLException {
		List<Banio> mBanios = new ArrayList<Banio>();
		Cursor cursorBanios = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorBanios != null && cursorBanios.getCount() > 0) {
			cursorBanios.moveToFirst();
			mBanios.clear();
			do{
				Banio mBanio = null;
				mBanio = AreaAmbienteHelper.crearBanio(cursorBanios);
				AreaAmbiente area = this.getAreaAmbiente(MainDBConstants.codigo + "='" + cursorBanios.getString(cursorBanios.getColumnIndex(MainDBConstants.areaAmbiente))+ "'", null);
				if(area != null){
					mBanio.setAreaAmbiente(area);
					mBanio.setCasa(area.getCasa());
					
				}
				else{
					CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorBanios.getString(cursorBanios.getColumnIndex(MainDBConstants.casa))+"'", null);
					mBanio.setCasa(casa);
				}
				mBanios.add(mBanio);
			} while (cursorBanios.moveToNext());
		}
		if (!cursorBanios.isClosed()) cursorBanios.close();
		return mBanios;
	}  
	
	/**
	 * Metodos para sala en la base de datos
	 * 
	 * @param sala
	 *            Objeto Sala que contiene la informacion
	 *
	 */
	//Crear nuevo Sala en la base de datos
	public void crearSala(Sala sala) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(sala);
		mDb.insert(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Sala existente en la base de datos
	public boolean editarSala(Sala sala) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(sala);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ sala.getCodigo()+ "'", null) > 0;
	}

	//Obtener un Sala de la base de datos
	public Sala getSala(String filtro, String orden) throws SQLException {
		Sala mSala = null;
		Cursor cursorSala = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorSala != null && cursorSala.getCount() > 0) {
			cursorSala.moveToFirst();
			mSala= AreaAmbienteHelper.crearSala(cursorSala);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorSala.getString(cursorSala.getColumnIndex(MainDBConstants.casa))+"'", null);
			mSala.setCasa(casa);
		}
		if (!cursorSala.isClosed()) cursorSala.close();
		return mSala;
	}
	//Obtener una lista de Sala de la base de datos
	public List<Sala> getSalas(String filtro, String orden) throws SQLException {
		List<Sala> mSalas = new ArrayList<Sala>();
		Cursor cursorSalas = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorSalas != null && cursorSalas.getCount() > 0) {
			cursorSalas.moveToFirst();
			mSalas.clear();
			do{
				Sala mSala = null;
				mSala = AreaAmbienteHelper.crearSala(cursorSalas);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorSalas.getString(cursorSalas.getColumnIndex(MainDBConstants.casa))+"'", null);
				mSala.setCasa(casa);
				mSalas.add(mSala);
			} while (cursorSalas.moveToNext());
		}
		if (!cursorSalas.isClosed()) cursorSalas.close();
		return mSalas;
	}  
	
	/**
	 * Metodos para Cocina en la base de datos
	 * 
	 * @param cocina
	 *            Objeto Cocina que contiene la informacion
	 *
	 */
	//Crear nuevo Cocina en la base de datos
	public void crearCocina(Cocina cocina) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(cocina);
		mDb.insert(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Cocina existente en la base de datos
	public boolean editarCocina(Cocina cocina) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(cocina);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ cocina.getCodigo()+ "'", null) > 0;
	}

	//Obtener un Cocina de la base de datos
	public Cocina getCocina(String filtro, String orden) throws SQLException {
		Cocina mCocina = null;
		Cursor cursorCocina = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorCocina != null && cursorCocina.getCount() > 0) {
			cursorCocina.moveToFirst();
			mCocina=AreaAmbienteHelper.crearCocina(cursorCocina);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorCocina.getString(cursorCocina.getColumnIndex(MainDBConstants.casa))+"'", null);
			mCocina.setCasa(casa);
		}
		if (!cursorCocina.isClosed()) cursorCocina.close();
		return mCocina;
	}
	//Obtener una lista de Cocina de la base de datos
	public List<Cocina> getCocinas(String filtro, String orden) throws SQLException {
		List<Cocina> mCocinas = new ArrayList<Cocina>();
		Cursor cursorCocinas = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorCocinas != null && cursorCocinas.getCount() > 0) {
			cursorCocinas.moveToFirst();
			mCocinas.clear();
			do{
				Cocina mCocina = null;
				mCocina =AreaAmbienteHelper.crearCocina(cursorCocinas);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorCocinas.getString(cursorCocinas.getColumnIndex(MainDBConstants.casa))+"'", null);
				mCocina.setCasa(casa);
				mCocinas.add(mCocina);
			} while (cursorCocinas.moveToNext());
		}
		if (!cursorCocinas.isClosed()) cursorCocinas.close();
		return mCocinas;
	}  
	
	/**
	 * Metodos para Comedor en la base de datos
	 * 
	 * @param comedor
	 *            Objeto Comedor que contiene la informacion
	 *
	 */
	//Crear nuevo Comedor en la base de datos
	public void crearComedor(Comedor comedor) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(comedor);
		mDb.insert(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Comedor existente en la base de datos
	public boolean editarComedor(Comedor comedor) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(comedor);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ comedor.getCodigo()+ "'", null) > 0;
	}

	//Obtener un Comedor de la base de datos
	public Comedor getComedor(String filtro, String orden) throws SQLException {
		Comedor mComedor = null;
		Cursor cursorComedor = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorComedor != null && cursorComedor.getCount() > 0) {
			cursorComedor.moveToFirst();
			mComedor=AreaAmbienteHelper.crearComedor(cursorComedor);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorComedor.getString(cursorComedor.getColumnIndex(MainDBConstants.casa))+"'", null);
			mComedor.setCasa(casa);
		}
		if (!cursorComedor.isClosed()) cursorComedor.close();
		return mComedor;
	}
	//Obtener una lista de Comedor de la base de datos
	public List<Comedor> getComedores(String filtro, String orden) throws SQLException {
		List<Comedor> mComedores = new ArrayList<Comedor>();
		Cursor cursorComedores = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorComedores != null && cursorComedores.getCount() > 0) {
			cursorComedores.moveToFirst();
			mComedores.clear();
			do{
				Comedor mComedor = null;
				mComedor = AreaAmbienteHelper.crearComedor(cursorComedores);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorComedores.getString(cursorComedores.getColumnIndex(MainDBConstants.casa))+"'", null);
				mComedor.setCasa(casa);
				mComedores.add(mComedor);
			} while (cursorComedores.moveToNext());
		}
		if (!cursorComedores.isClosed()) cursorComedores.close();
		return mComedores;
	}  

	/**
	 * Metodos para Ventana en la base de datos
	 * 
	 * @param ventana
	 *            Objeto Ventana que contiene la informacion
	 *
	 */
	//Crear nuevo Ventana en la base de datos
	public void crearVentana(Ventana ventana) {
		ContentValues cv = AreaAmbienteHelper.crearVentanaContentValues(ventana);
		mDb.insert(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Ventana existente en la base de datos
	public boolean editarVentana(Ventana ventana) {
		ContentValues cv = AreaAmbienteHelper.crearVentanaContentValues(ventana);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ ventana.getCodigo()+ "'", null) > 0;
	}

	//Obtener un Ventana de la base de datos
	public Ventana getVentana(String filtro, String orden) throws SQLException {
		Ventana mVentana = null;
		Cursor cursorVentana = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorVentana != null && cursorVentana.getCount() > 0) {
			cursorVentana.moveToFirst();
			mVentana=AreaAmbienteHelper.crearVentana(cursorVentana);
			AreaAmbiente area = this.getAreaAmbiente(MainDBConstants.codigo + "='" + cursorVentana.getString(cursorVentana.getColumnIndex(MainDBConstants.areaAmbiente))+ "'", null);
			if(area != null){
				mVentana.setAreaAmbiente(area);
				mVentana.setCasa(area.getCasa());
			}
		}
		if (!cursorVentana.isClosed()) cursorVentana.close();
		return mVentana;
	}
	//Obtener una lista de Ventana de la base de datos
	public List<Ventana> getVentanas(String filtro, String orden) throws SQLException {
		List<Ventana> mVentanas = new ArrayList<Ventana>();
		Cursor cursorVentanas = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorVentanas != null && cursorVentanas.getCount() > 0) {
			cursorVentanas.moveToFirst();
			mVentanas.clear();
			do{
				Ventana mVentana = null;
				mVentana = AreaAmbienteHelper.crearVentana(cursorVentanas);
				AreaAmbiente area = this.getAreaAmbiente(MainDBConstants.codigo + "='" + cursorVentanas.getString(cursorVentanas.getColumnIndex(MainDBConstants.areaAmbiente))+ "'", null);
				if(area != null){
					mVentana.setAreaAmbiente(area);
					mVentana.setCasa(area.getCasa());
					
				}
				mVentanas.add(mVentana);
			} while (cursorVentanas.moveToNext());
		}
		if (!cursorVentanas.isClosed()) cursorVentanas.close();
		return mVentanas;
	}  
	
	
	/**
	 * Metodos para Cuartos en la base de datos
	 * 
	 * @param cuarto
	 *            Objeto Cuarto que contiene la informacion
	 *
	 */
	//Crear nuevo Cuarto en la base de datos
	public void crearCuarto(Cuarto cuarto) {
		ContentValues cv = CuartoHelper.crearCuartoContentValues(cuarto);
		mDb.insert(MainDBConstants.CUARTO_TABLE, null, cv);
	}
	//Editar Cuarto existente en la base de datos
	public boolean editarCuarto(Cuarto cuarto) {
		ContentValues cv = CuartoHelper.crearCuartoContentValues(cuarto);
		return mDb.update(MainDBConstants.CUARTO_TABLE , cv, MainDBConstants.codigo + "='" 
				+ cuarto.getCodigo()+ "'", null) > 0;
	}
	//Limpiar la tabla de cuartos de la base de datos
	public boolean borrarCuartos() {
		return mDb.delete(MainDBConstants.CUARTO_TABLE, null, null) > 0;
	}
	//Obtener un Cuarto de la base de datos
	public Cuarto getCuarto(String filtro, String orden) throws SQLException {
		Cuarto mCuarto = null;
		Cursor cursorCuarto = crearCursor(MainDBConstants.CUARTO_TABLE , filtro, null, orden);
		if (cursorCuarto != null && cursorCuarto.getCount() > 0) {
			cursorCuarto.moveToFirst();
			mCuarto=CuartoHelper.crearCuarto(cursorCuarto);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorCuarto.getString(cursorCuarto.getColumnIndex(MainDBConstants.casa))+"'", null);
			mCuarto.setCasa(casa);
		}
		if (!cursorCuarto.isClosed()) cursorCuarto.close();
		return mCuarto;
	}
	//Obtener una lista de Cuarto de la base de datos
	public List<Cuarto> getCuartos(String filtro, String orden) throws SQLException {
		List<Cuarto> mCuartos = new ArrayList<Cuarto>();
		Cursor cursorCuartos = crearCursor(MainDBConstants.CUARTO_TABLE, filtro, null, orden);
		if (cursorCuartos != null && cursorCuartos.getCount() > 0) {
			cursorCuartos.moveToFirst();
			mCuartos.clear();
			do{
				Cuarto mCuarto = null;
				mCuarto = CuartoHelper.crearCuarto(cursorCuartos);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorCuartos.getString(cursorCuartos.getColumnIndex(MainDBConstants.casa))+"'", null);
				mCuarto.setCasa(casa);
				mCuartos.add(mCuarto);
			} while (cursorCuartos.moveToNext());
		}
		if (!cursorCuartos.isClosed()) cursorCuartos.close();
		return mCuartos;
	}

    /**
     * Metodos para ParticipanteSeroprevalencia en la base de datos
     *
     * @param participanteSeroprevalencia
     *            Objeto ParticipanteSeroprevalencias que contiene la informacion
     *
     */
    //Crear nuevo ParticipanteSeroprevalencias en la base de datos
    public void crearParticipanteSeroprevalencia(ParticipanteSeroprevalencia participanteSeroprevalencia) {
        ContentValues cv = ParticipanteSeroprevalenciaHelper.crearParticipanteSeroprevalenciaContentValues(participanteSeroprevalencia);
        mDb.insert(SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE, null, cv);
    }
    //Editar ParticipanteSeroprevalencia existente en la base de datos
    public boolean editarParticipanteSeroprevalencia(ParticipanteSeroprevalencia participanteSeroprevalencia) {
        ContentValues cv = ParticipanteSeroprevalenciaHelper.crearParticipanteSeroprevalenciaContentValues(participanteSeroprevalencia);
        return mDb.update(SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE, cv, SeroprevalenciaDBConstants.participante + "='"
                + participanteSeroprevalencia.getParticipante().getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de ParticipanteSeroprevalencia de la base de datos
    public boolean borrarParticipanteSeroprevalencia() {
        return mDb.delete(SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE, null, null) > 0;
    }
    //Obtener una ParticipanteSeroprevalencia de la base de datos
    public ParticipanteSeroprevalencia getParticipanteSeroprevalencia(String filtro, String orden) throws SQLException {
        ParticipanteSeroprevalencia mParticipanteSeroprevalencia = null;
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mParticipanteSeroprevalencia= ParticipanteSeroprevalenciaHelper.crearParticipanteSeroprevalencia(cursor);
            CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.casaCHF)), null);
            mParticipanteSeroprevalencia.setCasaCHF(cchf);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.participante)), null);
            mParticipanteSeroprevalencia.setParticipante(participante);
        }
        if (!cursor.isClosed()) cursor.close();
        return mParticipanteSeroprevalencia;
    }
    //Obtener una lista de ParticipanteSeroprevalencia de la base de datos
    public List<ParticipanteSeroprevalencia> getParticipantesSeroprevalencia(String filtro, String orden) throws SQLException {
        List<ParticipanteSeroprevalencia> mParticipanteSeroprevalencias = new ArrayList<ParticipanteSeroprevalencia>();
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mParticipanteSeroprevalencias.clear();
            do{
                ParticipanteSeroprevalencia mParticipanteSeroprevalencia = null;
                mParticipanteSeroprevalencia = ParticipanteSeroprevalenciaHelper.crearParticipanteSeroprevalencia(cursor);
                CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.casaCHF)), null);
                mParticipanteSeroprevalencia.setCasaCHF(cchf);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.participante)), null);
                mParticipanteSeroprevalencia.setParticipante(participante);
                mParticipanteSeroprevalencias.add(mParticipanteSeroprevalencia);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mParticipanteSeroprevalencias;
    }

    /**
     * Metodos para EncuestaCasaSA en la base de datos
     *
     * @param encuestaCasaSA
     *            Objeto EncuestaCasaSA que contiene la informacion
     *
     */
    //Crear nuevo EncuestaCasaSA en la base de datos
    public void crearEncuestaCasaSA(EncuestaCasaSA encuestaCasaSA) {
        ContentValues cv = EncuestaCasaSAHelper.crearEncuestaCasaSAContentValues(encuestaCasaSA);
        mDb.insert(SeroprevalenciaDBConstants.ENCUESTA_CASASA_TABLE, null, cv);
    }
    //Editar EncuestaCasaSA existente en la base de datos
    public boolean editarEncuestaCasaSA(EncuestaCasaSA encuestaCasaSA) {
        ContentValues cv = EncuestaCasaSAHelper.crearEncuestaCasaSAContentValues(encuestaCasaSA);
        return mDb.update(SeroprevalenciaDBConstants.ENCUESTA_CASASA_TABLE, cv, SeroprevalenciaDBConstants.casaCHF + "='"
                + encuestaCasaSA.getCasaCHF().getCodigoCHF() + "'", null) > 0;
    }
    //Limpiar la tabla de EncuestaCasaSA de la base de datos
    public boolean borrarEncuestaCasaSA() {
        return mDb.delete(SeroprevalenciaDBConstants.ENCUESTA_CASASA_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaCasaSA de la base de datos
    public EncuestaCasaSA getEncuestaCasaSA(String filtro, String orden) throws SQLException {
        EncuestaCasaSA mEncuestaCasaSA = null;
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.ENCUESTA_CASASA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestaCasaSA= EncuestaCasaSAHelper.crearEncuestaCasaSA(cursor);
            CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.casaCHF)), null);
            mEncuestaCasaSA.setCasaCHF(cchf);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestaCasaSA;
    }
    //Obtener una lista de EncuestaCasaSA de la base de datos
    public List<EncuestaCasaSA> getEncuestasCasaSA(String filtro, String orden) throws SQLException {
        List<EncuestaCasaSA> mEncuestaCasaSAs = new ArrayList<EncuestaCasaSA>();
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.ENCUESTA_CASASA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestaCasaSAs.clear();
            do{
                EncuestaCasaSA mEncuestaCasaSA = null;
                mEncuestaCasaSA = EncuestaCasaSAHelper.crearEncuestaCasaSA(cursor);
                CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.casaCHF)), null);
                mEncuestaCasaSA.setCasaCHF(cchf);
                mEncuestaCasaSAs.add(mEncuestaCasaSA);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestaCasaSAs;
    }

    /**
     * Metodos para EncuestaParticipanteSA en la base de datos
     *
     * @param encuestaParticipanteSA
     *            Objeto EncuestaParticipanteSA que contiene la informacion
     *
     */
    //Crear nuevo EncuestaParticipanteSA en la base de datos
    public void crearEncuestaParticipanteSA(EncuestaParticipanteSA encuestaParticipanteSA) {
        ContentValues cv = EncuestaParticipanteSAHelper.crearEncuestaParticipanteSAContentValues(encuestaParticipanteSA);
        mDb.insert(SeroprevalenciaDBConstants.ENCUESTA_PARTICIPANTESA_TABLE, null, cv);
    }
    //Editar EncuestaParticipanteSA existente en la base de datos
    public boolean editarEncuestaParticipanteSA(EncuestaParticipanteSA encuestaParticipanteSA) {
        ContentValues cv = EncuestaParticipanteSAHelper.crearEncuestaParticipanteSAContentValues(encuestaParticipanteSA);
        return mDb.update(SeroprevalenciaDBConstants.ENCUESTA_PARTICIPANTESA_TABLE, cv, SeroprevalenciaDBConstants.participante + "="
                + encuestaParticipanteSA.getParticipanteSA().getParticipante().getCodigo(), null) > 0;
    }
    //Limpiar la tabla de EncuestaParticipanteSA de la base de datos
    public boolean borrarEncuestaParticipanteSA() {
        return mDb.delete(SeroprevalenciaDBConstants.ENCUESTA_PARTICIPANTESA_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaParticipanteSA de la base de datos
    public EncuestaParticipanteSA getEncuestaParticipanteSA(String filtro, String orden) throws SQLException {
        EncuestaParticipanteSA mEncuestaParticipanteSA = null;
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.ENCUESTA_PARTICIPANTESA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestaParticipanteSA= EncuestaParticipanteSAHelper.crearEncuestaParticipanteSA(cursor);
            ParticipanteSeroprevalencia participante = this.getParticipanteSeroprevalencia(SeroprevalenciaDBConstants.participante + " = " + cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.participante)), null);
            mEncuestaParticipanteSA.setParticipanteSA(participante);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestaParticipanteSA;
    }
    //Obtener una lista de EncuestaParticipanteSA de la base de datos
    public List<EncuestaParticipanteSA> getEncuestasParticipanteSA(String filtro, String orden) throws SQLException {
        List<EncuestaParticipanteSA> mEncuestasParticipanteSA = new ArrayList<EncuestaParticipanteSA>();
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.ENCUESTA_PARTICIPANTESA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasParticipanteSA.clear();
            do{
                EncuestaParticipanteSA mEncuestaParticipanteSA = null;
                mEncuestaParticipanteSA = EncuestaParticipanteSAHelper.crearEncuestaParticipanteSA(cursor);
                ParticipanteSeroprevalencia participante = this.getParticipanteSeroprevalencia(SeroprevalenciaDBConstants.participante + " = " + cursor.getString(cursor.getColumnIndex(SeroprevalenciaDBConstants.participante)), null);
                mEncuestaParticipanteSA.setParticipanteSA(participante);
                mEncuestasParticipanteSA.add(mEncuestaParticipanteSA);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasParticipanteSA;
    }
    
	/**
	 * Metodos para Telefonos en la base de datos
	 * 
	 * @param tel
	 *            Objeto TelefonoContacto que contiene la informacion
	 *
	 */
	//Crear nuevo TelefonoContacto en la base de datos
	public void crearTelefonoContacto(TelefonoContacto tel) {
		ContentValues cv = TelefonoContactoHelper.crearTelefContactoContentValues(tel);
		mDb.insert(MainDBConstants.TELEFONO_CONTACTO_TABLE, null, cv);
	}
	//Editar TelefonoContacto existente en la base de datos
	public boolean editarTelefonoContacto(TelefonoContacto tel) {
		ContentValues cv = TelefonoContactoHelper.crearTelefContactoContentValues(tel);
		return mDb.update(MainDBConstants.TELEFONO_CONTACTO_TABLE , cv, MainDBConstants.id + "='" 
				+ tel.getId()+ "'", null) > 0;
	}
	//Limpiar la tabla de TelefonoContacto de la base de datos
	public boolean borrarTelefonoContacto() {
		return mDb.delete(MainDBConstants.TELEFONO_CONTACTO_TABLE, null, null) > 0;
	}
	//Obtener un TelefonoContacto de la base de datos
	public TelefonoContacto getTelefonoContacto(String filtro, String orden) throws SQLException {
		TelefonoContacto mTelefonoContacto = null;
		Cursor cursorTelefonoContacto = crearCursor(MainDBConstants.TELEFONO_CONTACTO_TABLE , filtro, null, orden);
		if (cursorTelefonoContacto != null && cursorTelefonoContacto.getCount() > 0) {
			cursorTelefonoContacto.moveToFirst();
			mTelefonoContacto=TelefonoContactoHelper.crearTelefonoContacto(cursorTelefonoContacto);
			Casa casa = this.getCasa(MainDBConstants.codigo + "='" +cursorTelefonoContacto.getString(cursorTelefonoContacto.getColumnIndex(MainDBConstants.casa))+"'", null);
			mTelefonoContacto.setCasa(casa);
			Participante part = this.getParticipante(MainDBConstants.codigo + "='" +cursorTelefonoContacto.getString(cursorTelefonoContacto.getColumnIndex(MainDBConstants.participante))+"'", null);
			mTelefonoContacto.setParticipante(part);
		}
		if (!cursorTelefonoContacto.isClosed()) cursorTelefonoContacto.close();
		return mTelefonoContacto;
	}
	//Obtener una lista de TelefonoContacto de la base de datos
	public List<TelefonoContacto> getTelefonosContacto(String filtro, String orden) throws SQLException {
		List<TelefonoContacto> mTelefonosContacto = new ArrayList<TelefonoContacto>();
		Cursor cursorTelefonosContacto = crearCursor(MainDBConstants.TELEFONO_CONTACTO_TABLE, filtro, null, orden);
		if (cursorTelefonosContacto != null && cursorTelefonosContacto.getCount() > 0) {
			cursorTelefonosContacto.moveToFirst();
			mTelefonosContacto.clear();
			do{
				TelefonoContacto mTelefonoContacto = null;
				mTelefonoContacto = TelefonoContactoHelper.crearTelefonoContacto(cursorTelefonosContacto);
				Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorTelefonosContacto.getInt(cursorTelefonosContacto.getColumnIndex(MainDBConstants.casa)), null);
				mTelefonoContacto.setCasa(casa);
				Participante part = this.getParticipante(MainDBConstants.codigo + "=" +cursorTelefonosContacto.getInt(cursorTelefonosContacto.getColumnIndex(MainDBConstants.participante)), null);
				mTelefonoContacto.setParticipante(part);
				mTelefonosContacto.add(mTelefonoContacto);
			} while (cursorTelefonosContacto.moveToNext());
		}
		if (!cursorTelefonosContacto.isClosed()) cursorTelefonosContacto.close();
		return mTelefonosContacto;
	}
}
