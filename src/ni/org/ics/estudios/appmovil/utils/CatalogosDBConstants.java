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
public class CatalogosDBConstants {
	
	//Tabla barrios
	public static final String BARRIO_TABLE = "barrios";
	//Campos barrios
	public static final String codigo = "codigo";
	public static final String nombre = "nombre";
	//Crear tabla barrios
	public static final String CREATE_BARRIO_TABLE = "create table if not exists "
			+ BARRIO_TABLE + " ("
			+ codigo + " integer not null, "  
			+ nombre + " text not null, "
			+ MainDBConstants.recordDate + " date, " 
			+ MainDBConstants.recordUser + " text, "
			+ MainDBConstants.pasive + " text, "
			+ MainDBConstants.estado + " text, "
			+ MainDBConstants.deviceId + " text, "
			+ "primary key (" + codigo + "));";
	
	//Tabla estudios
	public static final String ESTUDIO_TABLE = "estudios";
	//Campos estudios
	//Crear tabla estudios
	public static final String CREATE_ESTUDIO_TABLE = "create table if not exists "
			+ ESTUDIO_TABLE + " ("
			+ codigo + " integer not null, "  
			+ nombre + " text not null, "
			+ MainDBConstants.recordDate + " date, " 
			+ MainDBConstants.recordUser + " text, "
			+ MainDBConstants.pasive + " text, "
			+ MainDBConstants.estado + " text, "
			+ MainDBConstants.deviceId + " text, "
			+ "primary key (" + codigo + "));";	
	
	//Tabla mensajes
	public static final String MESSAGES_TABLE = "mensajes";
	//Campos mensajes
	public static final String messageKey = "messageKey";
	public static final String catRoot = "catRoot";
	public static final String catKey = "catKey";
	public static final String isCat = "isCat";
	public static final String order = "orden";
	public static final String spanish = "spanish";
	public static final String english = "english";
	
	//Crear tabla mensajes
	public static final String CREATE_MESSAGES_TABLE = "create table if not exists "
			+ MESSAGES_TABLE + " ("
			+ messageKey + " text not null, "
			+ catRoot + " text , "
			+ catKey + " text, "
			+ isCat + " text , "
			+ order + " integer , "
			+ spanish + " text not null, "
			+ english + " text , "
			+ MainDBConstants.pasive + " text, "
			+ "primary key (" + messageKey + "));";	

}
