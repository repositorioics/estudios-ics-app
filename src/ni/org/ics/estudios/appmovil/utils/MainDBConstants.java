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
            + estado + " text not null, "
			+ "primary key (" + codigo + "));";

    //Tabla Carta consentimiento
    public static final String CARTA_CONSENTIMIENTO_TABLE = "cartas_consentimientos";
    //Campos cartas_consentimientos
    public static final String fechaFirma = "fechaFirma";
    public static final String tamizaje = "tamizaje";
    public static final String participante= "participante";
    public static final String participadoCohortePediatrica = "participadoCohortePediatrica";
    public static final String cohortePediatrica = "cohortePediatrica"; //Dengue, Influenza, Ambas (dengue/influenza)
    public static final String codigoReactivado = "codigoReactivado";
    public static final String emancipado = "emancipado";
    public static final String asentimientoVerbal = "asentimientoVerbal"; //Para Niños de 6 a 17 años
    public static final String nombre1Tutor = "nombre1Tutor";
    public static final String nombre2Tutor = "nombre2Tutor";
    public static final String apellido1Tutor = "apellido1Tutor";
    public static final String apellido2Tutor = "apellido2Tutor";
    public static final String relacionFamiliarTutor = "relacionFamiliarTutor";
    public static final String participanteOTutorAlfabeto = "participanteOTutorAlfabeto";
    public static final String testigoPresente = "testigoPresente";
    public static final String nombre1Testigo = "nombre1Testigo";
    public static final String nombre2Testigo = "nombre2Testigo";
    public static final String apellido1Testigo = "apellido1Testigo";
    public static final String apellido2Testigo = "apellido2Testigo";
    public static final String aceptaParteA = "aceptaParteA";
    public static final String motivoRechazoParteA = "motivoRechazoParteA";
    public static final String aceptaContactoFuturo = "aceptaContactoFuturo";
    public static final String aceptaParteB = "aceptaParteB"; //Consentimiento para almacenamiento y uso de muestras en estudios futuros
    public static final String aceptaParteC = "aceptaParteC"; //Consentimiento adicional para estudios genéticos

    //crear tabla cartas_consentimientos
    public static final String CREATE_CARTACONSENTIMIENTO_TABLE = "create table if not exists "
            + CARTA_CONSENTIMIENTO_TABLE + " ("
            + codigo + " integer not null, "
            + fechaFirma + " date not null, "
            + tamizaje + " text not null, "
            + participante + " integer not null, "
            + participadoCohortePediatrica + " text, "
            + cohortePediatrica + " text, "
            + codigoReactivado + " text, "
            + emancipado + " text, "
            + asentimientoVerbal + " text, "
            + nombre1Tutor + " text, "
            + nombre2Tutor + " text, "
            + apellido1Tutor + " text, "
            + apellido2Tutor + " text, "
            + relacionFamiliarTutor + " text, "
            + participanteOTutorAlfabeto + " text, "
            + testigoPresente + " text, "
            + nombre1Testigo + " text, "
            + nombre2Testigo + " text, "
            + apellido1Testigo + " text, "
            + apellido2Testigo + " text, "
            + aceptaParteA + " text, "
            + motivoRechazoParteA + " text, "
            + aceptaContactoFuturo + " text, "
            + aceptaParteB + " text, "
            + aceptaParteC + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";

    //Tabla Participantes
    public static final String PARTICIPANTE_TABLE = "participantes";

    // Campos Participantes
    public static final String nombre1 = "nombre1";
    public static final String nombre2 = "nombre2";
    public static final String apellido1 = "apellido1";
    public static final String apellido2 = "apellido2";
    public static final String sexo = "sexo";
    public static final String fechaNac = "fechaNac";
    public static final String nombre1Padre = "nombre1Padre";
    public static final String nombre2Padre = "nombre2Padre";
    public static final String apellido1Padre = "apellido1Padre";
    public static final String apellido2Padre = "apellido2Padre";
    public static final String nombre1Madre = "nombre1Madre";
    public static final String nombre2Madre = "nombre2Madre";
    public static final String apellido1Madre = "apellido1Madre";
    public static final String apellido2Madre = "apellido2Madre";
    public static final String casa = "casa";

    //Crear tabla participantes
    public static final String CREATE_PARTICIPANTE_TALBE = "create table if not exists "
            + PARTICIPANTE_TABLE + " ("
            + codigo + " integer not null, "
            + nombre1 + " text not null, "
            + nombre2 + " text, "
            + apellido1 + " text not null, "
            + apellido2 + " text, "
            + sexo + " text, "
            + fechaNac + " date, "
            + nombre1Padre + " text not null, "
            + nombre2Padre + " text, "
            + apellido1Padre + " text not null, "
            + apellido2Padre + " text, "
            + nombre1Madre + " text not null, "
            + nombre2Madre + " text, "
            + apellido1Madre + " text not null, "
            + apellido2Madre + " text, "
            + casa + " integer not null, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";


    //Tabla Pretamizaje
    public static final String PRETAMIZAJE_TABLE = "chf_pre_tamizajes";

    //Campos tabla Pretamizajes
    public static final String estudio = "estudio";
    public static final String aceptaTamizaje = "aceptaTamizaje";
    public static final String razonNoParticipa = "razonNoParticipa";

    //crear tabla Pretamizajes
    public static final String CREATE_PRETAMIZAJE_TABLE = "create table if not exists "
            + PRETAMIZAJE_TABLE + " ("
            + codigo + " text not null, "
            + aceptaTamizaje + " text not null, "
            + razonNoParticipa + " text, "
            + casa + " integer not null, "
            + estudio + " integer not null, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";

    // Tabla Tamizaje
    public static final String TAMIZAJE_TABLE = "";

    //Campos tabla tamizaje
    public static final String fechaNacimiento = "fechaNacimiento";
    public static final String areaCobertura = "areaCobertura";
    public static final String ninoMenor12Anios = "ninoMenor12Anios";
    public static final String intencionPermanecerArea = "intencionPermanecerArea";
    public static final String tieneTarjetaVacunaOIdentificacion = "tieneTarjetaVacunaOIdentificacion";
    public static final String enfermedadAgudaCronica = "enfermedadAgudaCronica";
    public static final String elegible = "elegible";
    public static final String dondeAsisteProblemasSalud = "dondeAsisteProblemasSalud";
    public static final String asisteCSSF = "asisteCSSF";
    public static final String otroCentroSalud = "otroCentroSalud";
    public static final String puestoSalud = "puestoSalud";
    public static final String siEnfermaSoloAsistirCSSF = "siEnfermaSoloAsistirCSSF";
    public static final String tomaPuntoGPSCasa = "tomaPuntoGPSCasa";
    public static final String razonNoGeoreferenciacion = "razonNoGeoreferenciacion";
    public static final String otraRazonNoGeoreferenciacion = "otraRazonNoGeoreferenciacion";

    //crear tabla tamizaje
    public static final String CREATE_TAMIZAJE_TABLE = "create table if not exists "
            + TAMIZAJE_TABLE + " ("
            + codigo + " text not null, "
            + estudio + " integer not null, "
            + sexo + " text, "
            + fechaNacimiento + " date, "
            + aceptaTamizaje + " text not null, "
            + razonNoParticipa + " text, "
            + areaCobertura + " text, "
            + ninoMenor12Anios + " text, "
            + intencionPermanecerArea + " text, "
            + tieneTarjetaVacunaOIdentificacion + " text, "
            + enfermedadAgudaCronica + " text, "
            + elegible + " text, "
            + dondeAsisteProblemasSalud + " text, "
            + asisteCSSF + " text, "
            + otroCentroSalud + " text, "
            + puestoSalud + " text, "
            + siEnfermaSoloAsistirCSSF + " text, "
            + tomaPuntoGPSCasa + " text, "
            + razonNoGeoreferenciacion + " text, "
            + otraRazonNoGeoreferenciacion + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";

    //Tabla TelefonoContacto
    public static final String TELEFONO_CONTACTO_TABLE = "telefonos_contacto";

    //Campos tabla telefonocontacto
    public static final String id = "id";
    public static final String numero = "numero";
    public static final String operadora = "operadora";

    //crear tabla telefonocontacto
    public static final String CREATE_TELEFONO_CONTACTO_TABLE = "create table if not exists "
            + TELEFONO_CONTACTO_TABLE + " ("
            + id + " integer not null, "
            + numero + " text not null, "
            + operadora + " integer not null, "
            + casa + " integer not null, "
            + participante + " integer not null, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + id + "));";

    //Tabla Area Ambiente
    public static final String AREA_AMBIENTE_TABLE = "chf_areas_ambiente";

    //Campos tabla area ambiente
    public static final String largo = "largo";
    public static final String ancho = "ancho";
    public static final String totalM2 = "totalM2";
    public static final String numVentanas = "numVentanas";
    public static final String tipo = "tipo";
    public static final String habitacion = "habitacion";
    public static final String conVentana = "conVentana";
    public static final String cantidadCamas = "cantidadCamas";
    public static final String areaAmbiente = "areaAmbiente"; // para las ventanas
    public static final String alto = "alto";
    public static final String abierta = "abierta";

    //crear tabla telefonocontacto
    public static final String CREATE_AREA_AMBIENTE_TABLE = "create table if not exists "
            + AREA_AMBIENTE_TABLE + " ("
            + codigo + " text not null, "
            + casa + " integer not null, "
            + largo + " real, "
            + ancho + " real, "
            + totalM2 + " real, "
            + numVentanas + " integer, "
            + tipo + " text not null, "
            + habitacion + " text, "
            + conVentana + " text, "
            + cantidadCamas + " integer, "
            + areaAmbiente + " text, "
            + alto + " real, "
            + abierta + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";

    //tabla Camas
    public static final String CAMA_TABLE = "chf_camas_habitacion";

    //campos tabla Camas
    public static final String codigoCama = "codigoCama";

    // crear tabla Camas
    public static final String CREATE_CAMA_TABLE = "create table if not exists "
            + CAMA_TABLE + " ("
            + codigoCama + " text not null, "
            + habitacion + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigoCama + "));";

    //tabla PersonaCama
    public static final String PERSONACAMA_TABLE = "chf_persona_cama";

    //Campos tabla persona cama
    public static final String codigoPersona = "codigoPersona";
    public static final String estaEnEstudio = "estaEnEstudio"; //0 No, 1 Si
    public static final String edad = "edad";   //si no participa
    public static final String cama = "cama";

    //crear tabla peronsa cama
    public static final String CREATE_PERSONACAMA_TABLE = "create table if not exists "
            + PERSONACAMA_TABLE + " ("
            + codigoCama + " text not null, "
            + habitacion + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigoCama + "));";

    //tabla CasaCohorteFamiliar
    public static final String CASA_CHF_TABLE = "chf_casas_cohorte_familia";

    //Campos tabla CasaCohorteFamiliar
    public static final String codigoCHF = "codigoCHF";

    //Crear tabla CasaCohorteFamiliar
    public static final String CREATE_CASA_CHF_TABLE = "create table if not exists "
            + CASA_CHF_TABLE + " ("
            + codigoCHF + " text not null, "
            + casa + " integer not null, "
            + nombre1JefeFamilia + " text not null, "
            + nombre2JefeFamilia + " text , "
            + apellido1JefeFamilia + " text not null, "
            + apellido2JefeFamilia + " text , "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigoCama + "));";
}
