package ni.org.ics.estudios.appmovil.database;

/**
 * Adaptador de la base de datos Cohorte
 * 
 * @author William Aviles
 */

import java.util.ArrayList;
import java.util.List;

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

}
