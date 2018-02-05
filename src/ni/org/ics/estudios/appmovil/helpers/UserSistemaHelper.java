package ni.org.ics.estudios.appmovil.helpers;

import java.util.Date;

import ni.org.ics.estudios.appmovil.domain.users.Authority;
import ni.org.ics.estudios.appmovil.domain.users.AuthorityId;
import ni.org.ics.estudios.appmovil.domain.users.UserPermissions;
import ni.org.ics.estudios.appmovil.domain.users.UserSistema;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

public class UserSistemaHelper {
	
	public static ContentValues crearUserSistemaContentValues(UserSistema user){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.username, user.getUsername());
		if (user.getCreated() != null) cv.put(MainDBConstants.created, user.getCreated().getTime());
		if (user.getModified() != null) cv.put(MainDBConstants.modified, user.getModified().getTime());
		if (user.getLastAccess() != null) cv.put(MainDBConstants.lastAccess, user.getLastAccess().getTime());
		cv.put(MainDBConstants.password, user.getPassword());
		cv.put(MainDBConstants.completeName, user.getCompleteName());
		cv.put(MainDBConstants.email, user.getEmail());
		cv.put(MainDBConstants.enabled, user.getEnabled());
		cv.put(MainDBConstants.accountNonExpired, user.getAccountNonExpired());
		cv.put(MainDBConstants.credentialsNonExpired, user.getCredentialsNonExpired());
		if (user.getLastCredentialChange() != null) cv.put(MainDBConstants.lastCredentialChange, user.getLastCredentialChange().getTime());
		cv.put(MainDBConstants.accountNonLocked, user.getAccountNonLocked());
		cv.put(MainDBConstants.createdBy, user.getCreatedBy());
		cv.put(MainDBConstants.modifiedBy, user.getModifiedBy());
		return cv; 
	}	
	
	public static UserSistema crearUserSistema(Cursor cursorUser){
		
		UserSistema mUser = new UserSistema();
		mUser.setUsername(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.username)));
		if(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.created))>0) mUser.setCreated(new Date(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.created))));
		if(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.modified))>0) mUser.setModified(new Date(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.modified))));
		if(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.lastAccess))>0) mUser.setLastAccess(new Date(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.lastAccess))));
		mUser.setPassword(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.password)));
		mUser.setCompleteName(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.completeName)));
		mUser.setEmail(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.email)));
		mUser.setEnabled(cursorUser.getInt(cursorUser.getColumnIndex(MainDBConstants.enabled))>0);
		mUser.setAccountNonExpired(cursorUser.getInt(cursorUser.getColumnIndex(MainDBConstants.accountNonExpired))>0);
		mUser.setCredentialsNonExpired(cursorUser.getInt(cursorUser.getColumnIndex(MainDBConstants.credentialsNonExpired))>0);
		if(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.lastCredentialChange))>0) mUser.setLastCredentialChange(new Date(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.lastCredentialChange))));
		mUser.setAccountNonLocked(cursorUser.getInt(cursorUser.getColumnIndex(MainDBConstants.accountNonLocked))>0);
		mUser.setCreatedBy(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.createdBy)));
		mUser.setModifiedBy(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.modifiedBy)));
		return mUser;
	}
	
	public static ContentValues crearRolValues(Authority rol){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.username, rol.getAuthId().getUsername());
		cv.put(MainDBConstants.role, rol.getAuthId().getAuthority());
		return cv; 
	}	
	
	public static Authority crearRol(Cursor cursorRol){
		
		Authority mRol = new Authority();
		mRol.setAuthId(new AuthorityId(cursorRol.getString(cursorRol.getColumnIndex(MainDBConstants.username)),cursorRol.getString(cursorRol.getColumnIndex(MainDBConstants.role))));
		return mRol;
	}

    /**
     * Inserta un usuario en la base de datos
     *
     * @param userPermissions
     *            Objeto UserPermissions que contiene la informacion
     *
     */
    public static ContentValues crearPermisosUsuario(UserPermissions userPermissions) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.USERNAME, userPermissions.getUsername());
        cv.put(ConstantsDB.U_ECASA, userPermissions.getEncuestaCasa());
        cv.put(ConstantsDB.U_EPART, userPermissions.getEncuestaParticipante());
        cv.put(ConstantsDB.U_ELACT, userPermissions.getEncuestaLactancia());
        cv.put(ConstantsDB.U_ESAT, userPermissions.getEncuestaSatisfaccion());
        cv.put(ConstantsDB.U_MUESTRA, userPermissions.getMuestra());
        cv.put(ConstantsDB.U_OBSEQUIO, userPermissions.getObsequio());
        cv.put(ConstantsDB.U_PYT, userPermissions.getPesoTalla());
        cv.put(ConstantsDB.U_VAC, userPermissions.getVacunas());
        cv.put(ConstantsDB.U_VISITA, userPermissions.getVisitas());
        cv.put(ConstantsDB.U_RECEPCION, userPermissions.getRecepcion());
        cv.put(ConstantsDB.U_CONS, userPermissions.getConsentimiento());
        cv.put(ConstantsDB.U_CASAZIKA, userPermissions.getCasazika());
        cv.put(ConstantsDB.U_TAMZIKA, userPermissions.getTamizajezika());
        cv.put(ConstantsDB.U_PARTO, userPermissions.getDatosparto());
        return cv;
    }

    /**
     * Obtiene un usuario
     *
     * @return User
     */
    public static UserPermissions crearUserPermissions(Cursor usuarios) {
        UserPermissions mUser = new UserPermissions();
        mUser.setUsername(usuarios.getString(usuarios.getColumnIndex(MainDBConstants.username)));
        Boolean enCasa = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_ECASA)) > 0;
        Boolean enPart = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_EPART)) > 0;
        Boolean enLact = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_ELACT)) > 0;
        Boolean pesoTalla = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_PYT)) > 0;
        Boolean muestra = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_MUESTRA)) > 0;
        Boolean obsequio = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_OBSEQUIO)) > 0;
        Boolean vacuna = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_VAC)) > 0;
        Boolean terreno = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_VISITA)) > 0;
        Boolean recepcion = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_RECEPCION)) > 0;
        Boolean consentimiento = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_CONS)) > 0;
        Boolean tamizajezika = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_TAMZIKA)) > 0;
        Boolean casazika = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_CASAZIKA)) > 0;
        Boolean parto = usuarios.getInt(usuarios.getColumnIndex(ConstantsDB.U_PARTO)) > 0;
        mUser.setEncuestaCasa(enCasa);
        mUser.setEncuestaParticipante(enPart);
        mUser.setEncuestaLactancia(enLact);
        mUser.setPesoTalla(pesoTalla);
        mUser.setMuestra(muestra);
        mUser.setObsequio(obsequio);
        mUser.setVacunas(vacuna);
        mUser.setVisitas(terreno);
        mUser.setRecepcion(recepcion);
        mUser.setConsentimiento(consentimiento);
        mUser.setTamizajezika(tamizajezika);
        mUser.setCasazika(casazika);
        mUser.setDatosparto(parto);
        return mUser;
    }
}
