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
	public static final int DATABASE_VERSION = 11;
	
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
            + latitud + " real, "
			+ longitud + " real, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigoCHF + "));"; 
	
    //Tabla Participantes cohorte familia
    public static final String PARTICIPANTE_CHF_TABLE = "chf_participantes";

    // Campos Participantes cohorte familia
    //public static final String participanteCHF = "participanteCHF";
    public static final String casaCHF = "casaCHF";
    public static final String participante= "participante";
    
    //Crear tabla participantes cohorte familia
    public static final String CREATE_PARTICIPANTE_CHF_TABLE = "create table if not exists "
            + PARTICIPANTE_CHF_TABLE + " ("
            //+ participanteCHF + " text not null, "
            + participante + " integer not null, "
            + casaCHF + " integer not null, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + participante + "));";
    
    //Tabla Visitas
    public static final String VISITA_TABLE = "visitas";

    //Campos tabla Visitas
    public static final String codigoVisita = "codigoVisita";
    public static final String fechaVisita = "fechaVisita";
    public static final String visitaExitosa = "visitaExitosa";
    public static final String razonVisitaNoExitosa = "razonVisitaNoExitosa";
    public static final String otraRazonVisitaNoExitosa = "otraRazonVisitaNoExitosa";

    //crear tabla Visitas
    public static final String CREATE_VISITA_TABLE = "create table if not exists "
            + VISITA_TABLE + " ("
            + codigoVisita + " text not null, "
            + fechaVisita + " date not null, "
            + visitaExitosa + " text, "
            + casa + " integer not null, "
            + razonVisitaNoExitosa + " text, "
            + otraRazonVisitaNoExitosa + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigoVisita + "));";

    //reconsentimiento dengue 2018
    //Tabla Visitas participante
    public static final String VISITAPART_TABLE = "visitas_participante";

    //campos tabla Visitas participante
    public static final String dejoCarta = "dejoCarta";
    public static final String personaDejoCarta = "personaDejoCarta";
    public static final String relFamPersonaDejoCarta = "relFamPersonaDejoCarta";
    public static final String personaCasa = "personaCasa";
    public static final String relacionFamPersonaCasa = "relacionFamPersonaCasa";
    public static final String otraRelacionPersonaCasa = "otraRelacionPersonaCasa";
    public static final String telefonoPersonaCasa = "telefonoPersonaCasa";

    //crear tabla Visitas a participantes
    public static final String CREATE_VISITAPART_TABLE = "create table if not exists "
            + VISITAPART_TABLE + " ("
            + codigoVisita + " text not null, "
            + fechaVisita + " date not null, "
            + visitaExitosa + " text, "
            + participante + " integer not null, "
            + razonVisitaNoExitosa + " text, "
            + otraRazonVisitaNoExitosa + " text, "
            + dejoCarta + " text, "
            + personaDejoCarta + " text, "
            + relFamPersonaDejoCarta + " text, "
            + personaCasa + " text, "
            + relacionFamPersonaCasa + " text, "
            + otraRelacionPersonaCasa + " text, "
            + telefonoPersonaCasa + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigoVisita + "));";
    //Fin reconsentimiento dengue 2018
    //Tabla Pretamizaje
    public static final String PRETAMIZAJE_TABLE = "chf_pre_tamizajes";

    //Campos tabla Pretamizajes
    public static final String estudio = "estudio";
    public static final String aceptaTamizajeCasa = "aceptaTamizajeCasa";
    public static final String razonNoAceptaTamizajeCasa = "razonNoAceptaTamizajeCasa";
    public static final String otraRazonNoAceptaTamizajeCasa = "otraRazonNoAceptaTamizajeCasa";

    //crear tabla Pretamizajes
    public static final String CREATE_PRETAMIZAJE_TABLE = "create table if not exists "
            + PRETAMIZAJE_TABLE + " ("
            + codigo + " text not null, "
            + aceptaTamizajeCasa + " text not null, "
            + razonNoAceptaTamizajeCasa + " text, "
            + otraRazonNoAceptaTamizajeCasa + " text, "
            + casa + " integer not null, "
            + estudio + " integer not null, "
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
    public static final String aceptaParteD = "aceptaParteD"; //Consentimiento adicional para ZIKA (Estudio Cohorte Dengue)
    public static final String version = "version"; //Indicar la versión actual al momento de registrar la carta
    //reconsentimiento dengue 2018
    public static final String otroMotivoRechazoParteA = "otroMotivoRechazoParteA";
    public static final String motivoRechazoParteDExt = "motivoRechazoParteDExt";
    public static final String otroMotivoRechazoParteDExt = "otroMotivoRechazoParteDExt";
    public static final String mismoTutor = "mismoTutor";
    public static final String motivoDifTutor = "motivoDifTutor";
    public static final String otroMotivoDifTutor = "otroMotivoDifTutor";
    public static final String otraRelacionFamTutor = "otraRelacionFamTutor";
    public static final String verifTutor = "verifTutor";
    public static final String reconsentimiento = "reconsentimiento";


    //crear tabla cartas_consentimientos
    public static final String CREATE_CARTACONSENTIMIENTO_TABLE = "create table if not exists "
            + CARTA_CONSENTIMIENTO_TABLE + " ("
            + codigo + " text not null, "
            + fechaFirma + " date not null, "
            + tamizaje + " text not null, "
            + participante + " integer not null, "
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
            + aceptaParteD + " text, "
            + version + " text, "
            + otroMotivoRechazoParteA + " text, " //reconsentimiento dengue 2018
            + motivoRechazoParteDExt + " text, "
            + otroMotivoRechazoParteDExt + " text, "
            + mismoTutor + " text, "
            + motivoDifTutor + " text, "
            + otroMotivoDifTutor + " text, "
            + otraRelacionFamTutor + " text, "
            + verifTutor + " text, "
            + reconsentimiento + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";


    // Tabla Tamizaje
    public static final String TAMIZAJE_TABLE = "tamizajes";

    //Campos tabla tamizaje
    public static final String fechaNacimiento = "fechaNacimiento";
    public static final String aceptaTamizajePersona = "aceptaTamizajePersona";
    public static final String razonNoAceptaTamizajePersona = "razonNoAceptaTamizajePersona";
    public static final String otraRazonNoAceptaTamizajePersona = "otraRazonNoAceptaTamizajePersona";
    public static final String criteriosInclusion = "criteriosInclusion";
    public static final String enfermedad = "enfermedad";
    public static final String esElegible = "esElegible";
    public static final String dondeAsisteProblemasSalud = "dondeAsisteProblemasSalud";
    public static final String otroCentroSalud = "otroCentroSalud";
    public static final String puestoSalud = "puestoSalud";
    public static final String aceptaAtenderCentro = "aceptaAtenderCentro";
    public static final String aceptaParticipar = "aceptaParticipar";
    public static final String razonNoAceptaParticipar = "razonNoAceptaParticipar";
    public static final String otraRazonNoAceptaParticipar = "otraRazonNoAceptaParticipar";
    public static final String asentimientoVerbal = "asentimientoVerbal"; //Para Niños de 6 a 17 años
    //nuevo ingreso MA2018
    public static final String pretermino = "pretermino";
    public static final String cohorte = "cohorte";
    public static final String enfermedadInmuno = "enfermedadInmuno";
    public static final String cualEnfermedad = "cualEnfermedad";
    public static final String tratamiento = "tratamiento";
    public static final String cualTratamiento = "cualTratamiento";
    public static final String diagDengue = "diagDengue";
    public static final String fechaDiagDengue = "fechaDiagDengue";
    public static final String hospDengue = "hospDengue";
    public static final String fechaHospDengue = "fechaHospDengue";
    public static final String tiempoResidencia = "tiempoResidencia";
    //reconsentimiento dengue 2018
    public static final String tipoVivienda = "tipoVivienda";
    public static final String otraEnfCronica = "otraEnfCronica";
    public static final String enfCronicaAnio = "enfCronicaAnio";
    public static final String enfCronicaMes = "enfCronicaMes";
    public static final String otroTx = "otroTx";
    public static final String autorizaSupervisor = "autorizaSupervisor";
    public static final String emancipado = "emancipado";
    public static final String razonEmancipacion = "razonEmancipacion";
    public static final String otraRazonEmancipacion = "otraRazonEmancipacion";
    public static final String enfermedadCronica = "enfermedadCronica";
    public static final String codigoParticipanteRecon = "codigoParticipanteRecon";
    //crear tabla tamizaje
    public static final String CREATE_TAMIZAJE_TABLE = "create table if not exists "
            + TAMIZAJE_TABLE + " ("
            + codigo + " text not null, "
            + estudio + " integer not null, "
            + sexo + " text, "
            + fechaNacimiento + " date, "
            + aceptaTamizajePersona + " text, "
            + razonNoAceptaTamizajePersona + " text, "
            + otraRazonNoAceptaTamizajePersona + " text, "
            + criteriosInclusion + " text, "
            + enfermedad + " text, "
            + dondeAsisteProblemasSalud + " text, "
            + otroCentroSalud + " text, "
            + puestoSalud + " text, "
            + aceptaAtenderCentro + " text, "
            + esElegible + " text, "
            + aceptaParticipar + " text, "
            + razonNoAceptaParticipar + " text, "
            + otraRazonNoAceptaParticipar + " text, "
            + asentimientoVerbal + " text, "
            + pretermino + " text, "         //nuevo ingreso MA2018
            + cohorte + " text, "
            + enfermedadInmuno + " text, "
            //+ cualEnfermedad + " text, "
            + tratamiento + " text, "
            + cualTratamiento + " text, "
            + diagDengue + " text, "
            + fechaDiagDengue + " date, "
            + hospDengue + " text, "
            + fechaHospDengue + " date, "
            + tiempoResidencia + " text, "
            + tipoVivienda + " text, " //reconsentimiento dengue 2018
            //+ otraEnfCronica + " text, "
            //+ enfCronicaAnio + " text, "
            //+ enfCronicaMes + " text, "
            + otroTx + " text, "
            + autorizaSupervisor + " text, "
            + emancipado + " text, "
            + razonEmancipacion + " text, "
            + otraRazonEmancipacion + " text, "
            + enfermedadCronica + " text, "
            + codigoParticipanteRecon + " integer, "
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
    public static final String tipotel = "tipotel";

    //crear tabla telefonocontacto
    public static final String CREATE_TELEFONO_CONTACTO_TABLE = "create table if not exists "
            + TELEFONO_CONTACTO_TABLE + " ("
            + id + " text not null, "
            + numero + " text not null, "
            + operadora + " text, "
            + tipotel + " text, "
            + casa + " integer, "
            + participante + " integer, "
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
    public static final String conVentana = "conVentana";
    public static final String cantidadCamas = "cantidadCamas";
    public static final String areaAmbiente = "areaAmbiente"; // para las ventanas
    public static final String alto = "alto";
    public static final String abierta = "abierta";
    public static final String codigoHabitacion = "codigoHabitacion";
    

    //crear tabla telefonocontacto
    public static final String CREATE_AREA_AMBIENTE_TABLE = "create table if not exists "
            + AREA_AMBIENTE_TABLE + " ("
            + codigo + " text not null, "
            + casa + " text not null, "
            + largo + " real, "
            + ancho + " real, "
            + totalM2 + " real, "
            + numVentanas + " integer, "
            + tipo + " text not null, "
            + conVentana + " text, "
            + cantidadCamas + " integer, "
            + areaAmbiente + " text, "
            + abierta + " text, "
            + codigoHabitacion + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";
    
    //Tabla Cuartos
    public static final String CUARTO_TABLE = "chf_cuartos";

    //Campos tabla Cuartos

    

    //crear tabla telefonocontacto
    public static final String CREATE_CUARTO_TABLE = "create table if not exists "
            + CUARTO_TABLE + " ("
            + codigo + " text not null, "
            + casa + " text not null, "
            + cantidadCamas + " integer, "
            + codigoHabitacion + " text, "
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
    public static final String descCama = "descCama";
    public static final String habitacion = "habitacion";

    // crear tabla Camas
    public static final String CREATE_CAMA_TABLE = "create table if not exists "
            + CAMA_TABLE + " ("
            + codigoCama + " text not null, "
            + habitacion + " text not null, "
            + descCama + " text, "
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
            + codigoPersona + " text not null, "
            + cama + " text not null, "
            + estaEnEstudio + " text, "
            + participante + " integer, "
            + edad + " integer, "
            + sexo + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigoPersona + "));";

    //Tabla TelefonoContacto
    public static final String CONTACTO_PARTICIPANTE_TABLE = "contacto_participante";

    //Campos tabla contacto_participante
    public static final String numero1 = "numero1";
    public static final String operadora1 = "operadora1";
    public static final String tipo1 = "tipo1";
    public static final String numero2 = "numero2";
    public static final String operadora2 = "operadora2";
    public static final String tipo2 = "tipo2";
    //reconsentimiento dengue 2018
    public static final String numero3 = "numero3";
    public static final String operadora3 = "operadora3";
    public static final String tipo3 = "tipo3";
    public static final String esPropio = "esPropio";
    public static final String otroBarrio = "otroBarrio";

    //crear tabla contacto_participante
    public static final String CREATE_CONTACTO_PARTICIPANTE_TABLE = "create table if not exists "
            + CONTACTO_PARTICIPANTE_TABLE + " ("
            + id + " text not null, "
            + nombre1 + " text, "
            + direccion + " text, "
            + barrio + " text, "
            + numero1 + " text, "
            + operadora1 + " text, "
            + tipo1 + " text, "
            + numero2 + " text, "
            + operadora2 + " text, "
            + tipo2 + " text, "
            + numero3 + " text, "
            + operadora3 + " text, "
            + tipo3 + " text, "
            + esPropio + " text, "
            + otroBarrio + " text, "
            + participante + " integer, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + id + "));";

    //Tabla CambioDomicilio
    public static final String CAMBIO_DOMICILIO_TABLE = "cambio_domicilio";

    //Campos CambioDomicilio
    public static final String puntoGps = "puntoGps";
    public static final String razonNoGeoref = "razonNoGeoref";
    public static final String otraRazonNoGeoref = "otraRazonNoGeoref";

    //Crear tabla casas
    public static final String CREATE_CAMBIO_DOMICILIO_TABLE = "create table if not exists "
            + CAMBIO_DOMICILIO_TABLE + " ("
            + codigo + " text not null, "
            + participante + " integer, "
            + barrio + " integer, "
            + otroBarrio + " text, "
            + direccion + " text, "
            + manzana + " text, "
            + latitud + " real, "
            + longitud + " real, "
            + puntoGps + " text, "
            + razonNoGeoref + " text, "
            + otraRazonNoGeoref + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";

    // Tabla Tamizaje
    public static final String ENFCRONICA_TABLE = "enfermedades_cronicas";

    public static final String CREATE_ENFCRONICA_TABLE = "create table if not exists "
            + ENFCRONICA_TABLE + " ("
            + id + " text not null, "
            + tamizaje + " text, "
            + enfermedad + " text not null, "
            + otraEnfCronica + " text, "
            + enfCronicaAnio + " text, "
            + enfCronicaMes + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + id + "));";
}
