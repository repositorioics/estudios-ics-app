/*
 * Copyright (C) 2013 ICS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package ni.org.ics.estudios.appmovil.utils;

/**
 * Constantes usadas en la base de datos de la aplicacion
 * 
 * @author William Aviles
 * 
 */
public class MainDBConstants {

	//Base de datos y tablas
	public static final String DATABASE_NAME = "icsestudioscryp.sqlite3";
	public static final int DATABASE_VERSION = 1;
	
	//Campos metadata
	public static final String recordDate = "recordDate";
	public static final String recordUser = "recordUser";
	public static final String pasive = "pasive";
	public static final String deviceId = "identificador_equipo";
	public static final String estado = "estado";
	
	
	//Tabla usuarios
	public static final String USER_TABLE = "users";
	//Campos usuarios
	public static final String username = "username";
	public static final String created = "created";
	public static final String modified = "modified";
	public static final String lastAccess = "lastaccess";
	public static final String password = "password";
	public static final String completeName = "completename";
	public static final String email = "email";
	public static final String enabled = "enabled";
	public static final String accountNonExpired = "accountnonexpired";
	public static final String credentialsNonExpired = "credentialsnonexpired";
	public static final String lastCredentialChange = "lastcredentialchange";
	public static final String accountNonLocked = "accountnonlocked";
	public static final String createdBy = "createdby";
	public static final String modifiedBy = "modifiedby";
	//Crear tabla usuarios
	public static final String CREATE_USER_TABLE = "create table if not exists "
			+ USER_TABLE + " ("
			+ username + " text not null, "  
			+ created + " date, " 
			+ modified + " date, "
			+ lastAccess + " date, "
			+ password + " text not null, "
			+ completeName + " text, "
			+ email + " text, "
			+ enabled  + " boolean, " 
			+ accountNonExpired  + " boolean, "
			+ credentialsNonExpired  + " boolean, "
			+ lastCredentialChange + " date, "
			+ accountNonLocked  + " boolean, "
			+ createdBy + " text, "
			+ modifiedBy + " text, "
			+ "primary key (" + username + "));";
	
	//Tabla roles
	public static final String ROLE_TABLE = "roles";
	//Campos roles
	public static final String role = "role";
	//Crear tabla roles
	public static final String CREATE_ROLE_TABLE = "create table if not exists "
			+ ROLE_TABLE + " ("
			+ username + " text not null, "  
			+ role + " text not null, "
			+ "primary key (" + username + "," + role + "));";
	
	//Tabla casas
	public static final String CASA_TABLE = "casas";
	//Campos casas
	public static final String codigo = "codigo";
	public static final String barrio = "barrio";
	public static final String direccion = "direccion";
	public static final String manzana = "manzana";
	public static final String latitud = "latitud";
	public static final String longitud = "longitud";
	public static final String nombre1JefeFamilia = "nombre1JefeFamilia";
	public static final String nombre2JefeFamilia = "nombre2JefeFamilia";
	public static final String apellido1JefeFamilia = "apellido1JefeFamilia";
	public static final String apellido2JefeFamilia = "apellido2JefeFamilia";
	
	//Crear tabla casas
	public static final String CREATE_CASA_TABLE = "create table if not exists "
			+ CASA_TABLE + " ("
			+ codigo + " integer not null, "
			+ barrio + " integer not null, "
			+ direccion + " text not null, "
			+ manzana + " text, "
			+ latitud + " real, "
			+ longitud + " real, "
			+ nombre1JefeFamilia + " text not null, "
			+ nombre2JefeFamilia + " text , "
			+ apellido1JefeFamilia + " text not null, "
			+ apellido2JefeFamilia + " text , "
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
			+ "primary key (" + codigo + "));";

}
