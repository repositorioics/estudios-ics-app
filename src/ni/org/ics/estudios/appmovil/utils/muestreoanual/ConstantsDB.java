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

package ni.org.ics.estudios.appmovil.utils.muestreoanual;

/**
 * Constantes usadas en la base de datos de la aplicacion
 *
 * @author William Aviles
 *
 */
public class ConstantsDB {

    public static final String VIS_EXITO = "VisitaExitosa";

    //Base de datos y tablas
    public static final String USER_PERM_TABLE = "usuarios_permisos";
    public static final String CASA_TABLE = "casas";
    public static final String PART_TABLE = "participantes";
    public static final String PART_PROCESOS_TABLE = "participantes_procesos";
    public static final String ENC_CASA_TABLE = "encuestacasas";
    public static final String ENC_PART_TABLE = "encuestaparticipantes";
    public static final String LACT_TABLE = "lactanciamaterna";
    public static final String PT_TABLE = "pesoytalla";
    public static final String MUESTRA_TABLE = "muestras";
    public static final String OB_TABLE = "obsequios";
    public static final String VIS_TABLE = "visitasterreno";
    public static final String DAT_VIS_TABLE = "datosvisitasterreno";
    public static final String BHC_TABLE = "recepcionbhc";
    public static final String SERO_TABLE = "recepcionsero";
    public static final String TPBMC_TABLE = "temp_pbmc";
    public static final String TRB_TABLE = "temp_rojo_bhc";
    public static final String PIN_TABLE = "pinchazos";
    public static final String ENC_SAT_TABLE = "encuestasatisfaccion";
    public static final String NO_DATA_TABLE = "nodata";
    public static final String CAMB_CASA_TABLE = "cambios_casas";
    public static final String DATOSPARTOBB_TABLE = "partobb";
    public static final String NEWVAC_TABLE = "nuevas_vacunas";
    public static final String DOCS_TABLE = "documentacion";

    //Campos roles
    //public static final String AUTH = "rol";

    //Campos usuarios
    public static final String USERNAME = "username";
    public static final String ENABLED = "enabled";
    public static final String PASSWORD = "password";
    public static final String U_MUESTRA = "muestra";
    public static final String U_ECASA = "ecasa";
    public static final String U_EPART = "eparticipante";
    public static final String U_ELACT = "elactancia";
    public static final String U_ESAT = "esatisfaccion";
    public static final String U_OBSEQUIO = "obsequio";
    public static final String U_PYT = "pesotalla";
    public static final String U_VAC = "vacunas";
    public static final String U_VISITA = "visitas";
    public static final String U_RECEPCION = "recepcion";
    public static final String U_CONS = "consentimiento";
    public static final String U_CASAZIKA = "casazika";
    public static final String U_TAMZIKA = "tamizajezika";
    public static final String U_PARTO = "datosparto";

    //Campos casa
    public static final String COD_CASA = "codCasa";
    public static final String BARRIO = "barrio";
    public static final String DIRECCION = "direccion";
    public static final String MANZANA = "manzana";
    public static final String COORD = "coordenadas";
    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";
    public static final String TEL_CASA = "telCasa";
    public static final String NOM_JEFE = "jefenom";
    public static final String NOM_JEFE_2 = "jefenom2";
    public static final String APE_JEFE = "jefeap";
    public static final String APE_JEFE_2 = "jefeap2";
    public static final String TELEFONO1 = "telefono1";
    public static final String TELEFONO2 = "telefono2";
    public static final String NOMCONTACTO = "nom_contacto";
    public static final String DIRCONTACTO = "dire_contacto";
    public static final String BARRIOCONTACTO = "barrio_contacto";
    public static final String OBARRIOCONTACTO = "otrobarriocontacto";
    public static final String TELCONTACTO1 = "tel_contacto1";
    public static final String TELCONTACTO2 = "tel_contacto2";
    public static final String VIVIENDA = "vivienda";
    public static final String RESD = "resd";
    public static final String INTEN = "inten";
    public static final String ENC_CASA = "en_casa";

    //Campos tabla CasaCohorteFamiliar
    public static final String codigoCHF = "codigoCHF";

    //Campos participante_procesos
    public static final String CODIGO = "codigo";
    public static final String ESTADO_PAR = "est_par";
    public static final String ESTUDIO = "estudio";
    public static final String PBMC = "pbmc";
    public static final String CONSFLU = "cons_flu";
    public static final String CONSDENG = "cons_deng";
    public static final String ZIKA = "zika";
    public static final String ADN = "adn";
    public static final String CONSCHIK = "cons_chik";
    public static final String MUESTRA = "conmx";
    public static final String MUESTRABHC = "conmxbhc";
    public static final String LACT_MAT = "enc_lacmat";
    public static final String PESOTALLA = "peso_talla";
    public static final String ENC_PAR = "en_part";
    public static final String OBSEQUIO = "obsequio";
    public static final String CONVAL = "convalesciente";
    public static final String INFOVAC = "info_vacuna";
    public static final String PAXGENE = "paxgene";
    public static final String RETOMA = "retoma";
    public static final String VOLRETOMA = "vol_retoma";
    public static final String VOLRETOMAPBMC = "vol_retoma_pbmc";
    public static final String MANZANA_PART = "manzana_participante";
    public static final String BARRIO_PART = "barrio_participante";
    public static final String BARRIO_DESC = "barrio_descripcion";
    public static final String DIRE_FICHA1 = "direccion_ficha1";
    public static final String RECONSDENG = "recons_deng";
    public static final String CONPTO = "conpto";
    public static final String NUMPERS = "cuantas_personas";
    public static final String datosParto = "datosParto";
    public static final String posZika = "posZika";
    public static final String datosVisita = "datosVisita";
    public static final String mi = "mi";
    public static final String cand = "cand";
    public static final String casaCHF = "casaCHF";
    public static final String RELFAMT = "relacion_fam";
    public static final String enCasaChf = "enCasaChf";
    public static final String enCasaSa = "enCasaSa";
    public static final String encPartSa = "encPartSa";
    public static final String tutor = "tutor";
    public static final String coordenada = "coordenada"; //indica si tomar coordenadas por ingreso, por cambio de domicilio o por falta de punto
    public static final String consSa = "consSa"; //Consentimiento Seroprevalencia
    public static final String OBSEQUIOCHF = "obsequioChf"; //MA2019
    public static final String cDatosParto = "cDatosParto";
    public static final String reConsChf18 = "reConsChf18";
    public static final String posDengue = "posDengue";//22052019
    public static final String mxSuperficie = "mxSuperficie";//Muestras de superficie
    //MA2020
    public static final String mostrarAlfabeto = "mostrarAlfabeto";
    public static final String mostrarPadreAlfabeto = "mostrarPadreAlfabeto";
    public static final String mostrarMadreAlfabeta = "mostrarMadreAlfabeta";
    public static final String mostrarNumParto = "mostrarNumParto";
    public static final String antecedenteTutorCP = "antecedenteTutorCP";
    //Covid19
    public static final String consCovid19 = "consCovid19";
    public static final String subEstudios = "subEstudios";
    //Parte E CHF para toma mx adicional Covid19
    public static final String consChf = "consChf";
    public static final String cuestCovid = "cuestCovid";
    public static final String muestraCovid = "muestraCovid";
    //Texto que indica si el participante ha sido positivo para Covid19(SARS-COV2)
    public static final String posCovid = "posCovid";


    //Campos encuesta casa
    public static final String FECHA_ENC_CASA = "fecha_encuesta";
    public static final String CVIVEN1 = "cvivencasa1";
    public static final String CVIVEN2 = "cvivencasa2";
    public static final String CVIVEN3 = "cvivencasa3";
    public static final String CVIVEN4 = "cvivencasa4";
    public static final String CVIVEN5 = "cvivencasa5";
    public static final String CVIVEN6 = "cvivencasa6";
    public static final String CVIVEN7 = "cvivencasa7";
    public static final String CCUARTOS = "ccuartos";
    public static final String GRIFO = "grifo";
    public static final String GRIFOCOM = "grifocomsn";
    public static final String HORASAGUA = "horasagua";
    public static final String MCASA = "mcasa";
    public static final String OCASA = "ocasa";
    public static final String PISO = "piso";
    public static final String OPISO = "opiso";
    public static final String TECHO = "techo";
    public static final String OTECHO = "otecho";
    public static final String CPROPIA = "cpropia";
    public static final String ABANICOS = "cabanicos";
    public static final String TVS = "ctelevisores";
    public static final String REFRI = "crefrigeradores";
    public static final String MOTO = "moto";
    public static final String CARRO = "carro";
    public static final String LENA = "cocinalena";
    public static final String ANIMALES = "animalessn";
    public static final String POLLOS = "pollos";
    public static final String POLLOSCASA = "polloscasa";
    public static final String PATOS = "patos";
    public static final String PATOSCASA = "patoscasa";
    public static final String PERROS = "perros";
    public static final String PERROSCASA = "perroscasa";
    public static final String GATOS = "gatos";
    public static final String GATOSCASA = "gatoscasa";
    public static final String CERDOS = "cerdos";
    public static final String CERDOSCASA = "cerdoscasa";
    public static final String viveEmbEnCasa = "viveEmbEnCasa";
    public static final String CANTIDAD_CUARTOS = "CANTIDAD_CUARTOS";
    public static final String ALMACENA_AGUA = "ALMACENA_AGUA";
    public static final String ALMACENA_EN_BARRILES = "ALMACENA_EN_BARRILES";
    public static final String NUMERO_BARRILES = "NUMERO_BARRILES";
    public static final String BARRILES_TAPADOS = "BARRILES_TAPADOS";
    public static final String BARRILES_CON_ABATE = "BARRILES_CON_ABATE";
    public static final String ALMACENA_EN_TANQUES = "ALMACENA_EN_TANQUES";
    public static final String NUMERO_TANQUES = "NUMERO_TANQUES";
    public static final String TANQUES_TAPADOS = "TANQUES_TAPADOS";
    public static final String TANQUES_CON_ABATE = "TANQUES_CON_ABATE";
    public static final String ALMACENA_EN_PILAS = "ALMACENA_EN_PILAS";
    public static final String NUMERO_PILAS = "NUMERO_PILAS";
    public static final String PILAS_TAPADAS = "PILAS_TAPADAS";
    public static final String PILAS_CON_ABATE = "PILAS_CON_ABATE";
    public static final String ALMACENA_EN_OTROSRECIP = "ALMACENA_EN_OTROSRECIP";
    public static final String DESC_OTROS_RECIPIENTES = "DESC_OTROS_RECIPIENTES";
    public static final String NUMERO_OTROS_RECIPIENTES = "NUMERO_OTROS_RECIPIENTES";
    public static final String OTROS_RECIP_TAPADOS = "OTROS_RECIP_TAPADOS";
    public static final String OTROSRECIP_CON_ABATE = "OTROSRECIP_CON_ABATE";
    public static final String UBICACION_LAVANDERO = "UBICACION_LAVANDERO";
    public static final String TIENE_ABANICO = "TIENE_ABANICO";
    public static final String TIENE_TELEVISOR = "TIENE_TELEVISOR";
    public static final String TIENE_REFRIGERADOR_FREEZER = "TIENE_REFRIGERADOR_FREEZER";
    public static final String TIENE_AIRE_ACONDICIONADO = "TIENE_AIRE_ACONDICIONADO";
    public static final String FUNCIONAMIENTO_AIRE = "FUNCIONAMIENTO_AIRE";
    public static final String opcFabCarro = "opcFabCarro";
    public static final String yearNow = "yearNow";
    public static final String yearFabCarro = "yearFabCarro";
    public static final String TIENE_MICROBUS = "TIENE_MICROBUS";
    public static final String TIENE_CAMIONETA = "TIENE_CAMIONETA";
    public static final String TIENE_CAMION = "TIENE_CAMION";
    public static final String TIENE_OTRO_MEDIO_TRANS = "TIENE_OTRO_MEDIO_TRANS";
    public static final String DESC_OTRO_MEDIO_TRANS = "DESC_OTRO_MEDIO_TRANS";
    public static final String COCINA_CON_LENIA = "COCINA_CON_LENIA";
    public static final String UBICACION_COCINA_LENIA = "UBICACION_COCINA_LENIA";
    public static final String PERIODICIDAD_COCINA_LENIA = "PERIODICIDAD_COCINA_LENIA";
    public static final String NUM_DIARIO_COCINA_LENIA = "NUM_DIARIO_COCINA_LENIA";
    public static final String NUM_SEMANAL_COCINA_LENIA = "NUM_SEMANAL_COCINA_LENIA";
    public static final String NUM_QUINCENAL_COCINA_LENIA = "NUM_QUINCENAL_COCINA_LENIA";
    public static final String NUM_MENSUAL_COCINA_LENIA = "NUM_MENSUAL_COCINA_LENIA";
    public static final String TIENE_GALLINAS = "TIENE_GALLINAS";
    public static final String TIENE_PATOS = "TIENE_PATOS";
    public static final String TIENE_PERROS = "TIENE_PERROS";
    public static final String TIENE_GATOS = "TIENE_GATOS";
    public static final String TIENE_CERDOS = "TIENE_CERDOS";
    public static final String PERS_FUMA_DENTRO_CASA = "PERS_FUMA_DENTRO_CASA";
    public static final String MADRE_FUMA = "MADRE_FUMA";
    public static final String CANT_CIGARRILLOS_MADRE = "CANT_CIGARRILLOS_MADRE";
    public static final String PADRE_FUMA = "PADRE_FUMA";
    public static final String CANT_CIGARRILLOS_PADRE = "CANT_CIGARRILLOS_PADRE";
    public static final String OTROS_FUMAN = "OTROS_FUMAN";
    public static final String CANT_OTROS_FUMAN = "CANT_OTROS_FUMAN";
    public static final String CANT_CIGARRILLOS_OTROS = "CANT_CIGARRILLOS_OTROS";
    public static final String servRecolBasura = "servRecolBasura";
    public static final String frecServRecolBasura = "frecServRecolBasura";
    public static final String llantasOtrosContConAgua = "llantasOtrosContConAgua";
    public static final String opcFabMicrobus = "opcFabMicrobus";
    public static final String yearFabMicrobus = "yearFabMicrobus";
    public static final String opcFabCamioneta = "opcFabCamioneta";
    public static final String yearFabCamioneta = "yearFabCamioneta";
    public static final String opcFabCamion = "opcFabCamion";
    public static final String yearFabCamion = "yearFabCamion";
    public static final String opcFabOtroMedioTrans = "opcFabOtroMedioTrans";
    public static final String yearFabOtroMedioTrans = "yearFabOtroMedioTrans";
    //MA2020
    public static final String cambiadoCasa = "cambiadoCasa";
    public static final String remodelacionCasa = "remodelacionCasa";
    public static final String tieneVehiculo = "tieneVehiculo";
    public static final String participante = "participante";

    //Campos encuesta participante
    public static final String FECHA_ENC_PAR = "fecha_encuesta";
    public static final String FIEBRE = "fiebre";
    public static final String TFIEBRE = "tiemfieb";
    public static final String LUGCONS = "lugarcons";
    public static final String NOCS = "nocs";
    public static final String AUTOMED = "automed";
    public static final String ESC = "escuela";
    public static final String GRADO = "grado";
    public static final String TURNO = "turno";
    public static final String NESC = "nescuela";
    public static final String OESC = "otraescuela";
    public static final String CUIDAN = "cuidan";
    public static final String CCUIDAN = "cuantoscuidan";
    public static final String CQVIVE = "cqvive";
    public static final String LUGPARTO = "num_parto";
    public static final String PAPAALF = "papaalf";
    public static final String PAPANIVEL = "papanivel";
    public static final String PAPATRA = "papatra";
    public static final String PAPATIPOT = "papatipotra";
    public static final String MAMAALF = "mamaalf";
    public static final String MAMANIVEL = "mamanivel";
    public static final String MAMATRA = "mamatra";
    public static final String MAMATIPOT = "mamatipotra";
    public static final String COMPARTEHAB = "compartehab";
    public static final String HAB1 = "hab1";
    public static final String HAB2 = "hab2";
    public static final String HAB3 = "hab3";
    public static final String HAB4 = "hab4";
    public static final String HAB5 = "hab5";
    public static final String HAB6 = "hab6";
    public static final String COMPARTECAMA = "compartecama";
    public static final String CAMA1 = "cama1";
    public static final String CAMA2 = "cama2";
    public static final String CAMA3 = "cama3";
    public static final String CAMA4 = "cama4";
    public static final String CAMA5 = "cama5";
    public static final String CAMA6 = "cama6";
    public static final String ASMA = "asma";
    public static final String SILB12 = "silb12m";
    public static final String SIT1 = "sitresf";
    public static final String SIT2 = "sitejer";
    public static final String SILB01 = "silbmespas";
    public static final String DIFHAB = "difhablar";
    public static final String VECDIFHAB = "vechablar";
    public static final String DIFDOR = "difdormir";
    public static final String SUENOPER = "suenoper";
    public static final String TOS12 = "tos12m";
    public static final String VECESTOS = "vecestos";
    public static final String TOS3DIAS = "tos3dias";
    public static final String HOSP12M = "hosp12m";
    public static final String MED12M = "med12m";
    public static final String DEP12M = "dep12m";
    public static final String CRISIS = "crisis";
    public static final String FRECASMA = "frecasma";
    public static final String FUMA = "fumasn";
    public static final String QUIENFUMA = "quienfuma";
    public static final String CIGMADRE = "cant_cigarros_madre";
    public static final String CIGOTRO = "cant_cigarros_otros";
    public static final String CIGPADRE = "cant_cigarros_padre";

    public static final String rash = "rash";
    public static final String mesActual = "mesActual";
    public static final String yearActual = "yearActual";
    public static final String rash_year = "rash_year";
    public static final String rash_mes = "rash_mes";
    public static final String rash_mesact = "rash_mesact";
    public static final String rashCara = "rashCara";
    public static final String rashMiembrosSup = "rashMiembrosSup";
    public static final String rashTorax = "rashTorax";
    public static final String rashAbdomen = "rashAbdomen";
    public static final String rashMiembrosInf = "rashMiembrosInf";
    public static final String rashDias = "rashDias";
    public static final String ojoRojo = "ojoRojo";
    public static final String ojoRojo_year = "ojoRojo_year";
    public static final String ojoRojo_mes = "ojoRojo_mes";
    public static final String ojoRojo_mesact = "ojoRojo_mesact";
    public static final String ojoRojo_Dias = "ojoRojo_Dias";
    public static final String hormigueo = "hormigueo";
    public static final String hormigueo_year = "hormigueo_year";
    public static final String hormigueo_mes = "hormigueo_mes";
    public static final String hormigueo_mesact = "hormigueo_mesact";
    public static final String hormigueo_Dias = "hormigueo_Dias";
    public static final String consultaRashHormigueo = "consultaRashHormigueo";
    public static final String uSaludRashHormigueo = "uSaludRashHormigueo";
    public static final String cSaludRashHormigueo = "cSaludRashHormigueo";
    public static final String oCSRashHormigueo = "oCSRashHormigueo";
    public static final String pSRashHormigueo = "pSRashHormigueo";
    public static final String oPSRashHormigueo = "oPSRashHormigueo";
    public static final String diagRashHormigueo = "diagRashHormigueo";
    public static final String chPapaMama = "chPapaMama";
    public static final String fechana_papa = "fechana_papa";
    public static final String cal_edad_papa = "cal_edad_papa";
    public static final String cal_edad2_papa = "cal_edad2_papa";
    public static final String nombpapa1 = "nombpapa1";
    public static final String nombpapa2 = "nombpapa2";
    public static final String apellipapa1 = "apellipapa1";
    public static final String apellipapa2 = "apellipapa2";
    public static final String fechana_mama = "fechana_mama";
    public static final String cal_edad_mama = "cal_edad_mama";
    public static final String cal_edad2_mama = "cal_edad2_mama";
    public static final String nombmama1 = "nombmama1";
    public static final String nombmama2 = "nombmama2";
    public static final String apellimama1 = "apellimama1";
    public static final String apellimama2 = "apellimama2";

    //ENCUESTA PARTICPANTE CHF + NUEVAS PREGUNTAS MA2018
    public static final String EMANCIPADO = "EMANCIPADO";
    public static final String descEnmancipado = "descEnmancipado";
    public static final String otraDescEmanc = "otraDescEmanc";
    public static final String EMBARAZADA = "EMBARAZADA";
    public static final String SEMANAS_EMBARAZO = "SEMANAS_EMBARAZO";
    public static final String ALFABETO = "ALFABETO";
    public static final String NIVEL_EDUCACION = "NIVEL_EDUCACION";
    public static final String TRABAJA = "TRABAJA";
    public static final String TIPO_TRABAJO = "TIPO_TRABAJO";
    public static final String OCUPACION_ACTUAL = "OCUPACION_ACTUAL";
    public static final String NINO_ASISTE_ESCUELA = "NINO_ASISTE_ESCUELA";
    public static final String GRADO_ESTUDIA_NINO = "GRADO_ESTUDIA_NINO";
    public static final String NINO_TRABAJA = "NINO_TRABAJA";
    public static final String OCUPACION_ACTUAL_NINO = "OCUPACION_ACTUAL_NINO";
    public static final String PADRE_ESTUDIO = "PADRE_ESTUDIO";
    public static final String CODIGO_PADRE_ESTUDIO = "CODIGO_PADRE_ESTUDIO";
    public static final String PADRE_ALFABETO = "PADRE_ALFABETO";
    public static final String TRABAJA_PADRE = "TRABAJA_PADRE";
    public static final String MADRE_ESTUDIO = "MADRE_ESTUDIO";
    public static final String CODIGO_MADRE_ESTUDIO = "CODIGO_MADRE_ESTUDIO";
    public static final String MADRE_ALFABETA = "MADRE_ALFABETA";
    public static final String TRABAJA_MADRE = "TRABAJA_MADRE";
    public static final String FUMACHF = "FUMA";
    public static final String PERIODICIDAD_FUNA = "PERIODICIDAD_FUNA";
    public static final String CANTIDAD_CIGARRILLOS = "CANTIDAD_CIGARRILLOS";
    public static final String FUMA_DENTRO_CASA = "FUMA_DENTRO_CASA";
    public static final String TUBERCULOSIS_PULMONAR_ACTUAL = "TUBERCULOSIS_PULMONAR_ACTUAL";
    public static final String FECHA_DIAG_TUBPUL_ACTUAL = "FECHA_DIAG_TUBPUL_ACTUAL";
    public static final String TRATAMIENTO_TUBPUL_ACTUAL = "TRATAMIENTO_TUBPUL_ACTUAL";
    public static final String COMPLETO_TRATAMIENTO_TUBPUL_ACTUAL = "COMPLETO_TRATAMIENTO_TUBPUL_ACTUAL";
    public static final String TUBERCULOSIS_PULMONAR_PASADO = "TUBERCULOSIS_PULMONAR_PASADO";
    public static final String FECHA_DIAG_TUBPUL_PASADO_DES = "FECHA_DIAG_TUBPUL_PASADO_DES";
    public static final String FECHA_DIAG_TUBPUL_PASADO = "FECHA_DIAG_TUBPUL_PASADO";
    public static final String TRATAMIENTO_TUBPUL_PASADO = "TRATAMIENTO_TUBPUL_PASADO";
    public static final String COMPLETO_TRATAMIENTO_TUBPUL_PAS = "COMPLETO_TRATAMIENTO_TUBPUL_PAS";
    public static final String ALERGIA_RESPIRATORIA = "ALERGIA_RESPIRATORIA";
    public static final String CARDIOPATIA = "CARDIOPATIA";
    public static final String ENFERM_PULMONAR_OBST_CRONICA = "ENFERM_PULMONAR_OBST_CRONICA";
    public static final String DIABETES = "DIABETES";
    public static final String PRESION_ALTA = "PRESION_ALTA";
    public static final String TOS_SIN_FIEBRE_RESFRIADO = "TOS_SIN_FIEBRE_RESFRIADO";
    public static final String CANT_ENFERM_CUADROS_RESP = "CANT_ENFERM_CUADROS_RESP";
    public static final String OTRAS_ENFERMEDADES = "OTRAS_ENFERMEDADES";
    public static final String DESC_OTRAS_ENFERMEDADES = "DESC_OTRAS_ENFERMEDADES";
    public static final String VACUNA_INFLUENZA = "VACUNA_INFLUENZA";
    public static final String ANIO_VACUNA_INFLUENZA = "ANIO_VACUNA_INFLUENZA";
    public static final String rash6m = "rash6m";
    public static final String ojoRojo6m = "ojoRojo6m";
    public static final String estudiosAct = "estudiosAct";
    public static final String vacunaInfluenzaMes = "vacunaInfluenzaMes";
    public static final String vacunaInfluenzaCSSF = "vacunaInfluenzaCSSF";
    public static final String vacunaInfluenzaOtro = "vacunaInfluenzaOtro";
    public static final String nombreCDI = "nombreCDI";
    public static final String direccionCDI = "direccionCDI";
    //MA2020
    public static final String otroLugarCuidan = "otroLugarCuidan";
    public static final String enfermedadCronica = "enfermedadCronica";
    public static final String tenidoDengue = "tenidoDengue";
    public static final String unidadSaludDengue = "unidadSaludDengue";
    public static final String centroSaludDengue = "centroSaludDengue";
    public static final String otroCentroSaludDengue = "otroCentroSaludDengue";
    public static final String puestoSaludDengue = "puestoSaludDengue";
    public static final String otroPuestoSaludDengue = "otroPuestoSaludDengue";
    public static final String hospitalDengue = "hospitalDengue";
    public static final String otroHospitalDengue = "otroHospitalDengue";
    public static final String hospitalizadoDengue = "hospitalizadoDengue";
    public static final String ambulatorioDengue = "ambulatorioDengue";
    public static final String diagMedicoDengue = "diagMedicoDengue";
    public static final String rashUA = "rashUA";
    public static final String consultaRashUA = "consultaRashUA";

    //Campos encuesta lactancia materna
    public static final String FECHA_ENC_LACT = "fecha_encuesta";
    public static final String DIOPECHO = "diopecho";
    public static final String TIEMPECHO = "tiempecho";
    public static final String MESDIOPECHO = "mesdiopecho";
    public static final String PECHOEXC = "pechoexc";
    public static final String PECHOEXCANT = "pechoexcantes";
    public static final String TPECHOEXCANT = "tiempopechoexcantes";
    public static final String MPECHOEXCANT = "mespechoexcantes";
    public static final String FORMALIM = "formalim";
    public static final String OTRALIM = "otraalim";
    public static final String EDADLIQDP = "edadliqdistpecho";
    public static final String MESLIQDL = "mesdioliqdisleche";
    public static final String EDADLIQDL = "edadliqdistleche";
    public static final String MESLIQDP = "mesdioliqdispecho";
    public static final String EDADALIMS = "edalimsolidos";
    public static final String MESALIMS = "mesdioalimsol";
    public static final String EDAD = "edad";
    //Campos peso y talla
    public static final String FECHA_PT = "fecha_pt";
    public static final String PESO1 = "peso1";
    public static final String PESO2 = "peso2";
    public static final String PESO3 = "peso3";
    public static final String TALLA1 = "talla1";
    public static final String TALLA2 = "talla2";
    public static final String TALLA3 = "talla3";
    public static final String IMC1 = "imc1";
    public static final String IMC2 = "imc2";
    public static final String IMC3 = "imc3";
    public static final String DIFPESO = "difpeso";
    public static final String DIFTALLA = "diftalla";
    public static final String tomoMedidaSn = "tomoMedidaSn";
    public static final String razonNoTomoMedidas = "razonNoTomoMedidas";

    //Campos muestras
    public static final String FECHA_MUESTRA = "fecha_muestra";
    public static final String MFIEBRE = "fiebre";
    public static final String CONSULTA = "consulta";
    public static final String BHC = "tuboBHC";
    public static final String ROJO = "tuboRojo";
    public static final String LEU = "tuboLeu";

    public static final String bhc_razonNo = "bhc_razonNo";
    public static final String bhc_otraRazonNo = "bhc_otraRazonNo";
    public static final String rojo_razonNo = "rojo_razonNo";
    public static final String rojo_otraRazonNo = "rojo_otraRazonNo";
    public static final String pbmc_razonNo = "pbmc_razonNo";
    public static final String pbmc_otraRazonNo = "pbmc_otraRazonNo";
    public static final String horaBHC = "horaBHC";
    public static final String horaPBMC = "horaPBMC";

    public static final String horaInicioPax = "horaInicioPax";
    public static final String horaFinPax = "horaFinPax";
    public static final String codPax = "codPax";
    public static final String terreno = "terreno";


    public static final String PIN = "pinchazos";
//MA 2019
    public static final String hd_sn = "hd_sn";
    public static final String hdPorqueNo = "hdPorqueNo";
    //MA2020
    public static final String tuboPax = "tuboPax";

    //Campos obsequios
    public static final String FECHA_OB = "fecha_ob";
    public static final String OBSEQ = "obseqsn";
    public static final String CARNET = "carnetsn";
    public static final String PERRETIRA = "persona_retira";
    public static final String PERRETIRAREL = "rel_fam";
    public static final String PERRETIRAOREL = "o_rel_fam";
    public static final String OBS_TEL = "telefono";
    public static final String CDOM = "cdom";
    public static final String OBS = "observaciones";


    //Campos vacunas
    public static final String FECHA_VACUNA = "fecha_vacuna";
    public static final String VACUNA = "vacuna";
    public static final String FV = "fechaVacunado";
    public static final String TIPOVAC = "tipovacuna";
    public static final String TARJETA = "tarjetasn";
    public static final String NDOSIS = "ndosis";
    public static final String FECHAINF1 = "fechainf1";
    public static final String FECHAINF2 = "fechainf2";
    public static final String FECHAINF3 = "fechainf3";
    public static final String FECHAINF4 = "fechainf4";
    public static final String FECHAINF5 = "fechainf5";
    public static final String FECHAINF6 = "fechainf6";
    public static final String FECHAINF7 = "fechainf7";
    public static final String FECHAINF8 = "fechainf8";
    public static final String FECHAINF9 = "fechainf9";
    public static final String FECHAINF10 = "fechainf10";

    //Campos visitasterreno
    public static final String FECHA_VISITA = "fecha_visita";
    public static final String VISITASN = "visitasn";
    public static final String MOTNOVIS = "motnovisita";
    public static final String ACOMP_VIS = "acomp";
    public static final String REL_VIS = "relacion_fam";
    public static final String ASENT_VIS = "asentimiento";
    public static final String CDOM_VIS = "cdom";
    public static final String BARRIO_VIS = "barrio";
    public static final String MANZ_VIS = "manzana";
    public static final String DIRE_VIS = "direccion";
    public static final String COORD_VIS = "coordenadas";
    public static final String LAT_VIS = "latitud";
    public static final String LON_VIS = "longitud";
    //public static final String otraRelacionFam;
    public static final String carnetSN = "carnetSN";
    //public static final String telefonoClasif1;
    //public static final String telefonoConv1;
    //public static final String telefonoCel1;
    public static final String telefonoEmpresa1 = "telefonoEmpresa1";
    //public static final String telefono2SN;
    //public static final String telefonoClasif2;
    //public static final String telefonoConv2;
    //public static final String telefonoCel2;
    public static final String telefonoEmpresa2 = "telefonoEmpresa2";
    //public static final String telefono3SN;
    //public static final String telefonoClasif3;
    //public static final String telefonoConv3;
    //public static final String telefonoCel3;
    public static final String telefonoEmpresa3 = "telefonoEmpresa3";
    public static final String telefono4SN = "telefono4SN";
    public static final String telefonoClasif4 = "telefonoClasif4";
    public static final String telefonoConv4 = "telefonoConv4";
    public static final String telefonoCel4 = "telefonoCel4";
    public static final String telefonoEmpresa4 = "telefonoEmpresa4";
    public static final String candidatoNI = "candidatoNI";
    public static final String nombreCandNI1 = "nombreCandNI1";
    public static final String nombreCandNI2 = "nombreCandNI2";
    public static final String apellidoCandNI1 = "apellidoCandNI1";
    public static final String apellidoCandNI2 = "apellidoCandNI2";
    public static final String nombreptTutorCandNI = "nombreptTutorCandNI";
    public static final String nombreptTutorCandNI2 = "nombreptTutorCandNI2";
    public static final String apellidoptTutorCandNI = "apellidoptTutorCandNI";
    public static final String apellidoptTutorCandNI2 = "apellidoptTutorCandNI2";
    public static final String relacionFamCandNI = "relacionFamCandNI";
    public static final String otraRelacionFamCandNI = "otraRelacionFamCandNI";
    //para peso y talla muestreo 2020
    public static final String estudiaSN = "estudiaSN";
    public static final String nEscuela = "nEscuela";
    public static final String otraEscuela = "otraEscuela";
    public static final String turno = "turno";
    public static final String otroMotNoVisita = "otroMotNoVisita";//MA2020

    //Campos reconsentimiento
    public static final String FECHA_CONS = "fecha_cons";
    public static final String autsup = "autsup";
    public static final String parteaden = "parteaden";
    public static final String rechazoden = "rechazoden";
    public static final String partefden = "partefden";
    public static final String otrorechazoden = "otrorechazoden";
    public static final String incden = "incden";
    public static final String autsup2 = "autsup2";
    public static final String excden = "excden";
    public static final String enfcronsn = "enfcronsn";
    public static final String enfCronica = "enfCronica";
    public static final String oEnfCronica = "oEnfCronica";
    public static final String tomatx = "tomatx";
    public static final String cualestx = "cualestx";
    public static final String autsup3 = "autsup3";
    public static final String cmdomicilio = "cmdomicilio";
    public static final String barrio = "barrio";
    public static final String autsup4 = "autsup4";
    public static final String dire = "dire";
    public static final String manzana = "manzana";
    public static final String telefono = "telefono";
    public static final String asentimiento = "asentimiento";
    public static final String partebden = "partebden";
    public static final String partecden = "partecden";
    public static final String partedden = "partedden";
    public static final String asentimientoesc = "asentimientoesc";
    public static final String parteeden = "parteeden";
    public static final String firmcarta = "firmcarta";
    public static final String relacionfam = "relacionfam";
    public static final String coordenadas = "coordenadas";

    //Campos cambio estudio
    public static final String FECHA_CAMBIO = "fecha_cambio";
    public static final String parteaflu = "parteaflu";
    public static final String rechazoflu = "rechazoflu";
    public static final String partebflu = "partebflu";
    public static final String partecflu = "partecflu";
    public static final String incflu = "incflu";
    public static final String excflu = "excflu";

    //Campos recepcion BHC
    public static final String FECHA_BHC = "fecha_bhc";
    public static final String VOLBHC = "volbhc";
    public static final String LUGAR = "lugar_toma";
    public static final String OBSBHC = "observacion";

    //Campos recepcion SERO
    public static final String ID = "id";
    public static final String FECHA_SERO = "fecha_sero";
    public static final String VOLSERO = "volsero";
    public static final String OBSSERO = "observacion";


    //Campos temperatura PBMC y RojoBhc
    public static final String FECHA_TEMP = "fecha_temp";
    public static final String RECURSO = "recurso";
    public static final String TEMP = "temperatura";
    public static final String LUGARTEMP = "lugar_temp";
    public static final String OBSTEMP = "observacion";

    //Campos no data
    public static final String RAZON = "razon";
    public static final String ORAZON = "orazon";

    //Campos codigos casas
    public static final String COD_CASA_R = "codCasa";
    public static final String COD_COMUN = "codigoComun";
    public static final String COD_RELA = "codigoRelacionado";

    //Campos cambios casas
    public static final String codigo = "codigo";
    public static final String codCasaAnterior = "codCasaAnterior";
    public static final String codCasaActual = "codCasaActual";

    //Campos pinchazos
    public static final String FECHA_PIN = "fecha_pinchazo";
    public static final String PINCHAZOS = "num_pin";
    public static final String OBSPIN = "observacion";

    //Campos encuesta satisfaccion
    public static final String FECHA_ENC_SAT = "fecha_encuesta";
    public static final String ESTUDIOSAT="estudio";
    public static final String ATENPEREST="atenperest";
    public static final String TIEMATEN="tiematen";
    public static final String ATENPERADM="atenperadm";
    public static final String ATENPERENFERM="atenperenferm";
    public static final String ATENPERMED="atenpermed";
    public static final String AMBATEN="ambaten";
    public static final String ATENPERLAB="atenperlab";
    public static final String EXPLDXENF="expldxenf";
    public static final String FLUDENSN="fludensn";
    public static final String FLUCONIMP="fluconimp";
    public static final String DENCONIMP="denconimp";
    public static final String EXPLPELIGENF="explpeligenf";
    public static final String EXPMEDCUID="expmedcuid";

    //Campos comunes para manejo ODK
    public static final String ID_INSTANCIA = "id_instancia";
    public static final String FILE_PATH = "path_instancia";
    public static final String STATUS = "estado";
    public static final String WHEN_UPDATED = "ultimo_cambio";

    //Campos comunes para metadata movil
    public static final String START = "creado";
    public static final String END = "finalizado";
    public static final String DEVICE_ID = "identificador_equipo";
    public static final String SIM_SERIAL = "serie_sim";
    public static final String PHONE_NUMBER = "numero_telefono";
    public static final String TODAY = "fecha_registro";
    public static final String USUARIO = "username";
    public static final String DELETED = "eliminado";
    public static final String REC1 = "recurso1";
    public static final String REC2 = "recurso2";

    //Campos reconsentimiento 2015

    public static final String visExit = "visExit";
    public static final String razonVisNoExit = "razonVisNoExit";
    public static final String personaDejoCarta = "personaDejoCarta";
    public static final String personaCasa = "personaCasa";
    public static final String relacionFamPersonaCasa = "relacionFamPersonaCasa";
    public static final String otraRelacionPersonaCasa = "otraRelacionPersonaCasa";
    public static final String telefonoPersonaCasa = "telefonoPersonaCasa";
    public static final String emancipado = "emancipado";
    public static final String descEmancipado = "descEmancipado";
    public static final String incDen = "incDen";
    public static final String excDen = "excDen";
    public static final String enfCronSN = "enfCronSN";
    //public static final String enfCronica = "enfCronica";
    //public static final String oEnfCronica = "oEnfCronica";
    public static final String tomaTx = "tomaTx";
    public static final String cualesTx = "cualesTx";
    public static final String assite = "assite";
    public static final String centrosalud = "centrosalud";
    public static final String ocentrosalud = "ocentrosalud";
    public static final String puestosalud = "puestosalud";
    //public static final String asentimiento = "asentimiento";
    public static final String parteADen = "parteADen";
    public static final String parteBDen = "parteBDen";
    public static final String parteCDen = "parteCDen";
    public static final String parteDDen = "parteDDen";
    public static final String parteAFlu = "parteAFlu";
    public static final String parteBFlu = "parteBFlu";
    public static final String parteCFlu = "parteCFlu";
    public static final String rechDen = "rechDen";
    public static final String rechDDen = "rechDDen";
    public static final String rechFlu = "rechFlu";
    public static final String contacto_futuro = "contacto_futuro";
    public static final String nombrept = "nombrept";
    public static final String nombrept2 = "nombrept2";
    public static final String apellidopt = "apellidopt";
    public static final String apellidopt2 = "apellidopt2";
    public static final String relacionFam = "relacionFam";
    public static final String otraRelacionFam = "otraRelacionFam";
    public static final String mismoTutorSN = "mismoTutorSN";
    public static final String motivoDifTutor = "motivoDifTutor";
    public static final String otroMotivoDifTutor = "otroMotivoDifTutor";
    public static final String quePaisTutor = "quePaisTutor";
    public static final String alfabetoTutor = "alfabetoTutor";
    public static final String testigoSN = "testigoSN";
    public static final String nombretest1 = "nombretest1";
    public static final String nombretest2 = "nombretest2";
    public static final String apellidotest1 = "apellidotest1";
    public static final String apellidotest2 = "apellidotest2";
    public static final String cmDomicilio = "cmDomicilio";
    //public static final String barrio = "barrio";
    public static final String otrobarrio = "otrobarrio";
    //public static final String dire = "dire";
    //public static final String autsup = "autsup";
    public static final String telefonoClasif1 = "telefonoClasif1";
    public static final String telefonoConv1 = "telefonoConv1";
    public static final String telefonoCel1 = "telefonoCel1";
    public static final String telefono2SN = "telefono2SN";
    public static final String telefonoClasif2 = "telefonoClasif2";
    public static final String telefonoConv2 = "telefonoConv2";
    public static final String telefonoCel2 = "telefonoCel2";
    public static final String telefono3SN = "telefono3SN";
    public static final String telefonoClasif3 = "telefonoClasif3";
    public static final String telefonoConv3 = "telefonoConv3";
    public static final String telefonoCel3 = "telefonoCel3";
    public static final String jefenom = "jefenom";
    public static final String jefenom2 = "jefenom2";
    public static final String jefeap = "jefeap";
    public static final String jefeap2 = "jefeap2";
    public static final String nomContacto = "nomContacto";
    public static final String barrioContacto = "barrioContacto";
    public static final String otrobarrioContacto = "otrobarrioContacto";
    public static final String direContacto = "direContacto";
    public static final String telContacto1 = "telContacto1";
    public static final String telContactoConv1 = "telContactoConv1";
    public static final String telContactoCel1 = "telContactoCel1";
    public static final String telContacto2SN = "telContacto2SN";
    public static final String telContactoClasif2 = "telContactoClasif2";
    public static final String telContactoConv2 = "telContactoConv2";
    public static final String telContactoCel2 = "telContactoCel2";
    public static final String nombrepadre = "nombrepadre";
    public static final String nombrepadre2 = "nombrepadre2";
    public static final String apellidopadre = "apellidopadre";
    public static final String apellidopadre2 = "apellidopadre2";
    public static final String nombremadre = "nombremadre";
    public static final String nombremadre2 = "nombremadre2";
    public static final String apellidomadre = "apellidomadre";
    public static final String apellidomadre2 = "apellidomadre2";
    public static final String copiaFormato = "copiaFormato";
    public static final String firmo_cartcons = "firmo_cartcons";
    public static final String fecho_cartcons = "fecho_cartcons";
    public static final String huella_dig = "huella_dig";
    public static final String fech_firm_testigo = "fech_firm_testigo";
    public static final String entiende = "entiende";
    public static final String georef = "georef";
    public static final String Manzana = "Manzana";
    public static final String georef_razon = "georef_razon";
    public static final String georef_otraRazon = "georef_otraRazon";
    public static final String local = "local";
    public static final String otrorecurso1 = "otrorecurso1";
    public static final String otrorecurso2 = "otrorecurso2";


    //Campos tabla DatosParto
    public static final String fechaDatosParto = "fechaDatosParto";
    public static final String tipoParto = "tipoParto";
    public static final String tiempoEmb_sndr = "tiempoEmb_sndr";
    public static final String tiempoEmbSemana = "tiempoEmbSemana";
    public static final String docMedTiempoEmb_sn = "docMedTiempoEmb_sn";
    public static final String docMedTiempoEmb = "docMedTiempoEmb";
    public static final String otroDocMedTiempoEmb = "otroDocMedTiempoEmb";
    public static final String fum = "fum";
    public static final String sg = "sg";
    public static final String fumFueraRango_sn = "fumFueraRango_sn";
    public static final String fumFueraRango_razon = "fumFueraRango_razon";
    public static final String edadGest = "edadGest";
    public static final String docMedEdadGest_sn = "docMedEdadGest_sn";
    public static final String docMedEdadGest = "docMedEdadGest";
    public static final String OtroDocMedEdadGest = "OtroDocMedEdadGest";
    public static final String prematuro_sndr = "prematuro_sndr";
    public static final String pesoBB_sndr = "pesoBB_sndr";
    public static final String pesoBB = "pesoBB";
    public static final String docMedPesoBB_sn = "docMedPesoBB_sn";
    public static final String docMedPesoBB = "docMedPesoBB";
    public static final String otroDocMedPesoBB = "otroDocMedPesoBB";
    public static final String tallaBB_sndr = "tallaBB_sndr";
    public static final String tallaBB = "tallaBB";
    public static final String docMedTallaBB_sn = "docMedTallaBB_sn";
    public static final String docMedTallaBB = "docMedTallaBB";
    public static final String otroDocMedTallaBB = "otroDocMedTallaBB";
    public static final String vacFluMadre_sn = "vacFluMadre_sn";
    public static final String fechaVacInf = "fechaVacInf";
    public static final String docMedFecVacInfMadre_sn = "docMedFecVacInfMadre_sn";
    public static final String docMedFecVacInfMadre = "docMedFecVacInfMadre";
    public static final String otroDocMedFecVacInfMadre = "otroDocMedFecVacInfMadre";
    public static final String docMedFUM_sn = "docMedFUM_sn";

    //Campos nuevas vacunas
    public static final String fechaRegistroVacuna = "fechaRegistroVacuna";
    public static final String vacuna_sn = "vacuna_sn";
    public static final String tvacunano = "tvacunano";
    public static final String otroMotivoTvacunano = "otroMotivoTvacunano";

    //Campos documentacion
    public static final String fechaDocumento = "fechaDocumento";
    public static final String tipoDoc = "tipoDoc";
    public static final String documento = "documento";
    public static final String fechaRecepcion = "fechaRecepcion";

    //Campos zika tabla tamizaje
    public static final String idTamizaje = "idTamizaje";
    public static final String fechaTamizaje = "fechaTamizaje";
    public static final String genero = "genero";
    public static final String acepta_cons = "acepta_cons";
    public static final String porque_no = "porque_no";
    public static final String desc_porque_no = "desc_porque_no";
    public static final String incTrZ = "incTrZ";
    public static final String alfabeto = "alfabeto";
    public static final String testigo = "testigo";
    public static final String parteATrZ = "parteATrZ";
    public static final String parteBTrZ = "parteBTrZ";
    public static final String parteCTrZ = "parteCTrZ";
    public static final String porqueno = "porqueno";


    //Campos zika tabla casas
    public static final String codigo_casa = "codigo_casa";
    public static final String direccion = "direccion";
    public static final String telefono1 = "telefono1";
    public static final String telefono2 = "telefono2";
    public static final String encuesta = "encuesta";
    public static final String encuestaEnt = "encuestaEnt";

    //Campos zika tabla participantes
    public static final String codigo_indice = "codigo_indice";
    public static final String indice = "indice";
    public static final String cohorte = "cohorte";
    public static final String fechana = "fechana";
    public static final String nombre1 = "nombre1";
    public static final String nombre2 = "nombre2";
    public static final String apellido1 = "apellido1";
    public static final String apellido2 = "apellido2";
    public static final String dondesalud = "dondesalud";
    public static final String puestosal = "puestosal";
    public static final String otropuestosal = "otropuestosal";
    public static final String solocssf = "solocssf";
    public static final String sint1 = "sint1";
    public static final String sint2 = "sint2";
    public static final String sint3 = "sint3";
    public static final String sint4 = "sint4";
    public static final String sint5 = "sint5";
    public static final String sint6 = "sint6";
    public static final String sint7 = "sint7";
    public static final String sint8 = "sint8";
    public static final String sint9 = "sint9";
    public static final String sint10 = "sint10";
    public static final String sint11 = "sint11";
    public static final String sint12 = "sint12";
    public static final String sint13 = "sint13";
    public static final String sint14 = "sint14";
    public static final String sint15 = "sint15";
    public static final String sint16 = "sint16";
    public static final String sint17 = "sint17";
    public static final String sint18 = "sint18";
    public static final String sint19 = "sint19";
    public static final String sint20 = "sint20";
    public static final String sint21 = "sint21";
    public static final String sint22 = "sint22";
    public static final String sint23 = "sint23";
    public static final String sint24 = "sint24";
    public static final String sint25 = "sint25";
    public static final String sint26 = "sint26";
    public static final String sint27 = "sint27";
    public static final String sint28 = "sint28";

    //Campos zika tabla participantes
    public static final String idSintoma = "idSintoma";
    public static final String fechaSint = "fechaSint";
    public static final String diaSint = "diaSint";
    public static final String fiebre = "fiebre";
    public static final String astenia = "astenia";

    public static final String CREATE_USER_PERM_TABLE = "create table if not exists "
            + USER_PERM_TABLE + " ("
            + USERNAME + " text not null, "
            + U_MUESTRA + " boolean, "
            + U_ECASA + " boolean, "
            + U_EPART + " boolean, "
            + U_ELACT + " boolean, "
            + U_ESAT + " boolean, "
            + U_OBSEQUIO + " boolean, "
            + U_PYT + " boolean, "
            + U_VAC + " boolean, "
            + U_VISITA + " boolean, "
            + U_RECEPCION + " boolean, "
            + U_CONS + " boolean, "
            + U_CASAZIKA + " boolean, "
            + U_TAMZIKA + " boolean, "
            + U_PARTO + " boolean, "
            + "primary key (" + USERNAME + "));";

    public static final String CREATE_PARTPROC_TABLE = "create table if not exists "
            + PART_PROCESOS_TABLE + " ("
            + CODIGO + " integer not null, "
            + ESTADO_PAR  + " integer not null, "
            + RECONSDENG  + " text, "
            + CONPTO  + " text, "
            + ESTUDIO  + " text, "
            + PBMC  + " text, "
            + CONSDENG  + " text, "
            + CONSFLU  + " text, "
            + CONSCHIK  + " text, "
            + MUESTRA  + " text, "
            + MUESTRABHC  + " text, "
            + LACT_MAT  + " text, "
            + PESOTALLA  + " text, "
            + ENC_PAR  + " text, "
            + ENC_CASA  + " text, "
            + OBSEQUIO  + " text, "
            + CONVAL  + " text, "
            + INFOVAC  + " text, "
            + PAXGENE  + " text, "
            + RETOMA  + " text, "
            + VOLRETOMA  + " real, " //para rojo
            + VOLRETOMAPBMC  + " real, " //para pbmc
            + telefono + " text, "
            + ZIKA  + " text, "
            + ADN  + " text, "
            + RELFAMT  + " integer not null, "
            + NUMPERS  + " integer, "
            + datosParto  + " text, "
            + posZika  + " text, "
            + datosVisita  + " text, "
            + mi  + " text, "
            + casaCHF  + " text, "
            + enCasaChf  + " text, "
            + enCasaSa  + " text, "
            + encPartSa  + " text, "
            + tutor  + " text, "
            + coordenada + " text, "
            + consSa  + " text, "
            + OBSEQUIOCHF  + " text, "
            + cDatosParto  + " text, "
            + reConsChf18  + " text, "
            + posDengue  + " text, "
            + mxSuperficie  + " text, "
            + mostrarAlfabeto  + " text, " //MA2020
            + mostrarMadreAlfabeta  + " text, "
            + mostrarNumParto  + " text, "
            + mostrarPadreAlfabeto  + " text, "
            + antecedenteTutorCP  + " text, "
            + consCovid19 + " text, "
            + subEstudios + " text, "
            + consChf + " text, "
            + cuestCovid + " text, "
            + muestraCovid + " text, "
            + posCovid + " text, "
            + ID_INSTANCIA + " integer,"
            + FILE_PATH + " text,"
            + STATUS + " text, "
            + WHEN_UPDATED + " text, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key (" + CODIGO + "));";

    public static final String CREATE_ENCCASA_TABLE = "create table if not exists "
            + ENC_CASA_TABLE + " ("
            + codigo + " text not null, "
            + COD_CASA + " integer, "
            + codigoCHF + " text, "
            + FECHA_ENC_CASA  + " date not null, "
            + CVIVEN1  + " integer, "
            + CVIVEN2  + " integer, "
            + CVIVEN3  + " integer, "
            + CVIVEN4  + " integer, "
            + CVIVEN5  + " integer, "
            + CVIVEN6  + " integer, "
            + CVIVEN7  + " integer, "
            + CCUARTOS  + " integer, "
            + GRIFO  + " integer, "
            + GRIFOCOM  + " integer, "
            + HORASAGUA  + " integer, "
            + MCASA  + " integer, "
            + OCASA  + " text, "
            + PISO  + " text, "
            + OPISO  + " text, "
            + TECHO  + " integer, "
            + OTECHO  + " text, "
            + CPROPIA  + " integer, "
            + ABANICOS  + " integer, "
            + TVS  + " integer, "
            + REFRI  + " integer, "
            + MOTO  + " integer, "
            + CARRO  + " integer, "
            + LENA  + " integer, "
            + ANIMALES  + " integer, "
            + POLLOS  + " integer, "
            + POLLOSCASA  + " integer, "
            + PATOS  + " integer, "
            + PATOSCASA  + " integer, "
            + PERROS  + " integer, "
            + PERROSCASA  + " integer, "
            + GATOS  + " integer, "
            + GATOSCASA  + " integer, "
            + CERDOS  + " integer, "
            + CERDOSCASA  + " integer, "
            + otrorecurso1  + " integer, "
            + otrorecurso2  + " integer, "
            //+CHF + NUEVAS PREGUNTAS MA2018
             + viveEmbEnCasa  + " text, "
             + CANTIDAD_CUARTOS  + " integer, "
             + ALMACENA_AGUA  + " text, "
             + ALMACENA_EN_BARRILES  + " text, "
             + NUMERO_BARRILES  + " integer, "
             + BARRILES_TAPADOS  + " text, "
             + BARRILES_CON_ABATE  + " text, "
             + ALMACENA_EN_TANQUES  + " text, "
             + NUMERO_TANQUES  + " integer, "
             + TANQUES_TAPADOS  + " text, "
             + TANQUES_CON_ABATE  + " text, "
             + ALMACENA_EN_PILAS  + " text, "
             + NUMERO_PILAS  + " integer, "
             + PILAS_TAPADAS  + " text, "
             + PILAS_CON_ABATE  + " text, "
             + ALMACENA_EN_OTROSRECIP  + " text, "
             + DESC_OTROS_RECIPIENTES  + " text, "
             + NUMERO_OTROS_RECIPIENTES  + " integer, "
             + OTROS_RECIP_TAPADOS  + " text, "
             + OTROSRECIP_CON_ABATE  + " text, "
             + UBICACION_LAVANDERO  + " text, "
             + TIENE_ABANICO  + " text, "
             + TIENE_TELEVISOR  + " text, "
             + TIENE_REFRIGERADOR_FREEZER  + " text, "
             + TIENE_AIRE_ACONDICIONADO  + " text, "
             + FUNCIONAMIENTO_AIRE  + " text, "
             + opcFabCarro + " text, "
             + yearNow  + " integer, "
             + yearFabCarro + " integer, "
             + TIENE_MICROBUS  + " text, "
             + TIENE_CAMIONETA  + " text, "
             + TIENE_CAMION  + " text, "
             + TIENE_OTRO_MEDIO_TRANS  + " text, "
             + DESC_OTRO_MEDIO_TRANS  + " text, "
             + COCINA_CON_LENIA  + " text, "
             + UBICACION_COCINA_LENIA  + " text, "
             + PERIODICIDAD_COCINA_LENIA  + " text, "
             + NUM_DIARIO_COCINA_LENIA  + " integer, "
             + NUM_SEMANAL_COCINA_LENIA  + " integer, "
             + NUM_QUINCENAL_COCINA_LENIA  + " integer, "
             + NUM_MENSUAL_COCINA_LENIA  + " integer, "
             + TIENE_GALLINAS  + " text, "
             + TIENE_PATOS  + " text, "
             + TIENE_PERROS  + " text, "
             + TIENE_GATOS  + " text, "
             + TIENE_CERDOS  + " text, "
             + PERS_FUMA_DENTRO_CASA  + " text, "
             + MADRE_FUMA + " text, "
             + CANT_CIGARRILLOS_MADRE  + " integer, "
             + PADRE_FUMA  + " text, "
             + CANT_CIGARRILLOS_PADRE  + " integer, "
             + OTROS_FUMAN  + " text, "
             + CANT_OTROS_FUMAN  + " integer, "
             + CANT_CIGARRILLOS_OTROS  + " integer, "
             + servRecolBasura  + " text, "
             + frecServRecolBasura  + " integer, "
             + llantasOtrosContConAgua  + " text, "
            + opcFabMicrobus  + " text, "
            + yearFabMicrobus  + " integer, "
            + opcFabCamioneta  + " text, "
            + yearFabCamioneta  + " integer, "
            + opcFabCamion  + " text, "
            + yearFabCamion  + " integer, "
            + opcFabOtroMedioTrans  + " text, "
            + yearFabOtroMedioTrans  + " integer, "
            + cambiadoCasa  + " text, "
            + remodelacionCasa  + " text, "
            + tieneVehiculo  + " text, "
            + participante  + " integer, "
            + ID_INSTANCIA + " integer not null,"
            + FILE_PATH + " text not null,"
            + STATUS + " text not null, "
            + WHEN_UPDATED + " text not null, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key (" + codigo + "));";

    public static final String CREATE_ENCPART_TABLE = "create table if not exists "
            + ENC_PART_TABLE + " ("
            + CODIGO + " integer not null, "
            + FECHA_ENC_PAR  + " date not null, "
            + FIEBRE + " integer, "
            + TFIEBRE + " integer, "
            + LUGCONS + " integer, "
            + NOCS + " integer, "
            + AUTOMED + " text, "
            + ESC + " integer, "
            + GRADO + " integer, "
            + TURNO + " integer, "
            + NESC + " integer, "
            + OESC + " text, "
            + CUIDAN + " integer, "
            + CCUIDAN + " integer, "
            + CQVIVE + " integer, "
            + LUGPARTO + " integer, "
            + PAPAALF + " integer, "
            + PAPANIVEL + " integer, "
            + PAPATRA + " integer, "
            + PAPATIPOT + " integer, "
            + MAMAALF + " integer, "
            + MAMANIVEL + " integer, "
            + MAMATRA + " integer, "
            + MAMATIPOT + " integer, "
            + COMPARTEHAB + " integer, "
            + HAB1 + " integer, "
            + HAB2 + " integer, "
            + HAB3 + " integer, "
            + HAB4 + " integer, "
            + HAB5 + " integer, "
            + HAB6 + " integer, "
            + COMPARTECAMA + " integer, "
            + CAMA1 + " integer, "
            + CAMA2 + " integer, "
            + CAMA3 + " integer, "
            + CAMA4 + " integer, "
            + CAMA5 + " integer, "
            + CAMA6 + " integer, "
            + ASMA + " integer, "
            + SILB12 + " integer, "
            + SIT1 + " integer, "
            + SIT2 + " integer, "
            + SILB01 + " integer, "
            + DIFHAB + " integer, "
            + VECDIFHAB + " integer, "
            + DIFDOR + " integer, "
            + SUENOPER + " integer, "
            + TOS12 + " integer, "
            + VECESTOS + " integer, "
            + TOS3DIAS + " integer, "
            + HOSP12M + " integer, "
            + MED12M + " integer, "
            + DEP12M + " integer, "
            + CRISIS + " integer, "
            + FRECASMA + " integer, "
            + FUMA + " integer, "
            + QUIENFUMA + " text, "
            + CIGMADRE + " integer, "
            + CIGOTRO + " integer, "
            + CIGPADRE + " integer, "
            + rash + " integer, "
            + mesActual + " integer, "
            + yearActual + " integer, "
            + rash_year + " integer, "
            + rash_mes + " integer, "
            + rash_mesact + " integer, "
            + rashCara + " integer, "
            + rashMiembrosSup + " integer, "
            + rashTorax + " integer, "
            + rashAbdomen + " integer, "
            + rashMiembrosInf + " integer, "
            + rashDias + " integer, "
            + ojoRojo + " integer, "
            + ojoRojo_year + " integer, "
            + ojoRojo_mes + " integer, "
            + ojoRojo_mesact + " integer, "
            + ojoRojo_Dias + " integer, "
            + hormigueo + " integer, "
            + hormigueo_year + " integer, "
            + hormigueo_mes + " integer, "
            + hormigueo_mesact + " integer, "
            + hormigueo_Dias + " integer, "
            + consultaRashHormigueo + " integer, "
            + uSaludRashHormigueo + " integer, "
            + cSaludRashHormigueo + " integer, "
            + oCSRashHormigueo + " text, "
            + pSRashHormigueo + " integer, "
            + oPSRashHormigueo + " text, "
            + diagRashHormigueo + " text, "
            + chPapaMama + " text, "
            + fechana_papa + " date, "
            + cal_edad_papa + " integer, "
            + cal_edad2_papa + " integer, "
            + nombpapa1 + " text, "
            + nombpapa2 + " text, "
            + apellipapa1 + " text, "
            + apellipapa2 + " text, "
            + fechana_mama + " date, "
            + cal_edad_mama + " integer, "
            + cal_edad2_mama + " integer, "
            + nombmama1 + " text, "
            + nombmama2 + " text, "
            + apellimama1 + " text, "
            + apellimama2 + " text, "
            + otrorecurso1  + " integer, "
            + otrorecurso2  + " integer, "
            //+CHF + NUEVAS PREGUNTAS MA2018
            + EMANCIPADO + " text, "
            + descEnmancipado + " text, "
            + otraDescEmanc + " text, "
            + EMBARAZADA + " text, "
            + SEMANAS_EMBARAZO + " integer, "
            + ALFABETO + " text, "
            + NIVEL_EDUCACION + " text, "
            + TRABAJA + " text, "
            + TIPO_TRABAJO + " text, "
            + OCUPACION_ACTUAL + " text, "
            + NINO_ASISTE_ESCUELA + " text, "
            + GRADO_ESTUDIA_NINO + " text, "
            + NINO_TRABAJA + " text, "
            + OCUPACION_ACTUAL_NINO + " text, "
            + PADRE_ESTUDIO + " text, "
            + CODIGO_PADRE_ESTUDIO + " text, "
            + PADRE_ALFABETO + " text, "
            + TRABAJA_PADRE + " text, "
            + MADRE_ESTUDIO + " text, "
            + CODIGO_MADRE_ESTUDIO + " text, "
            + MADRE_ALFABETA + " text, "
            + TRABAJA_MADRE + " text, "
            + FUMACHF + " text, "
            + PERIODICIDAD_FUNA + " text, "
            + CANTIDAD_CIGARRILLOS + " integer, "
            + FUMA_DENTRO_CASA + " text, "
            + TUBERCULOSIS_PULMONAR_ACTUAL + " text, "
            + FECHA_DIAG_TUBPUL_ACTUAL + " text, "
            + TRATAMIENTO_TUBPUL_ACTUAL + " text, "
            + COMPLETO_TRATAMIENTO_TUBPUL_ACTUAL + " text, "
            + TUBERCULOSIS_PULMONAR_PASADO + " text, "
            + FECHA_DIAG_TUBPUL_PASADO_DES + " text, "
            + FECHA_DIAG_TUBPUL_PASADO + " text, "
            + TRATAMIENTO_TUBPUL_PASADO + " text, "
            + COMPLETO_TRATAMIENTO_TUBPUL_PAS + " text, "
            + ALERGIA_RESPIRATORIA + " text, "
            + CARDIOPATIA + " text, "
            + ENFERM_PULMONAR_OBST_CRONICA + " text, "
            + DIABETES + " text, "
            + PRESION_ALTA + " text, "
            + TOS_SIN_FIEBRE_RESFRIADO + " text, "
            + CANT_ENFERM_CUADROS_RESP + " integer, "
            + OTRAS_ENFERMEDADES + " text, "
            + DESC_OTRAS_ENFERMEDADES + " text, "
            + VACUNA_INFLUENZA + " text, "
            + ANIO_VACUNA_INFLUENZA + " integer, "
            + rash6m + " text, "
            + ojoRojo6m + " text, "
            + estudiosAct + " text, "
            //MA2019
            + vacunaInfluenzaMes + " integer, "
            + vacunaInfluenzaCSSF + " text, "
            + vacunaInfluenzaOtro + " text, "
            + nombreCDI + " text, "
            + direccionCDI + " text, "
            //MA2020
            + otroLugarCuidan + " text, "
            + enfermedadCronica + " text, "
            + tenidoDengue + " text, "
            + unidadSaludDengue + " text, "
            + centroSaludDengue + " text, "
            + otroCentroSaludDengue + " text, "
            + puestoSaludDengue + " text, "
            + otroPuestoSaludDengue + " text, "
            + hospitalDengue + " text, "
            + otroHospitalDengue + " text, "
            + hospitalizadoDengue + " text, "
            + ambulatorioDengue + " text, "
            + diagMedicoDengue + " text, "
            + rashUA + " text, "
            + consultaRashUA + " text, "
            + ID_INSTANCIA + " integer not null,"
            + FILE_PATH + " text not null,"
            + STATUS + " text not null, "
            + WHEN_UPDATED + " text not null, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key (" + FECHA_ENC_PAR + "," + CODIGO + "));";

    public static final String CREATE_ENCLAC_TABLE = "create table if not exists "
            + LACT_TABLE + " ("
            + CODIGO + " integer not null, "
            + FECHA_ENC_LACT  + " date not null, "
            + EDAD + " integer, "
            + DIOPECHO + " integer, "
            + TIEMPECHO + " integer, "
            + MESDIOPECHO + " integer, "
            + PECHOEXC + " integer, "
            + PECHOEXCANT + " integer, "
            + TPECHOEXCANT + " integer, "
            + MPECHOEXCANT + " integer, "
            + FORMALIM + " integer, "
            + OTRALIM + " text, "
            + EDADLIQDP + " integer, "
            + MESLIQDL + " integer, "
            + EDADLIQDL + " integer, "
            + MESLIQDP + " integer, "
            + EDADALIMS + " integer, "
            + MESALIMS + " integer, "
            + otrorecurso1  + " integer, "
            + otrorecurso2  + " integer, "
            + ID_INSTANCIA + " integer not null,"
            + FILE_PATH + " text not null,"
            + STATUS + " text not null, "
            + WHEN_UPDATED + " text not null, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key (" + FECHA_ENC_LACT + "," + CODIGO + "));";

    public static final String CREATE_PT_TABLE = "create table if not exists "
            + PT_TABLE + " ("
            + CODIGO + " integer not null, "
            + FECHA_PT  + " date not null, "
            + PESO1 + " real, "
            + PESO2 + " real, "
            + PESO3 + " real, "
            + TALLA1 + " real, "
            + TALLA2 + " real, "
            + TALLA3 + " real, "
            + IMC1 + " real, "
            + IMC2 + " real, "
            + IMC3 + " real, "
            + DIFPESO + " real, "
            + DIFTALLA + " real, "
            + tomoMedidaSn  + " text, "
            + razonNoTomoMedidas  + " text, "
            + estudiosAct + " text, "
            + otrorecurso1  + " integer, "
            + otrorecurso2  + " integer, "
            + ID_INSTANCIA + " integer not null,"
            + FILE_PATH + " text not null,"
            + STATUS + " text not null, "
            + WHEN_UPDATED + " text not null, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key (" + FECHA_PT + "," + CODIGO + "));";

    public static final String CREATE_MUESTRA_TABLE = "create table if not exists "
            + MUESTRA_TABLE + " ("
            + CODIGO + " integer not null, "
            + FECHA_MUESTRA  + " date not null, "
            + MFIEBRE + " integer, "
            + CONSULTA + " integer, "
            + BHC + " integer, "
            + ROJO + " integer, "
            + LEU + " integer, "
            + PIN + " integer, "
            + bhc_razonNo + " integer, "
            + rojo_razonNo + " integer, "
            + pbmc_razonNo + " integer, "
            + bhc_otraRazonNo  + " text, "
            + rojo_otraRazonNo  + " text, "
            + pbmc_otraRazonNo  + " text, "
            + horaBHC  + " text, "
            + horaPBMC  + " text, "
            + horaInicioPax  + " text, "
            + horaFinPax  + " text, "
            + codPax  + " text, "
            + terreno  + " text, "
            + otrorecurso1  + " integer, "
            + otrorecurso2  + " integer, "
            + estudiosAct + " text, "
            + hd_sn  + " text, "
            + hdPorqueNo  + " text, "
            + tuboPax  + " integer, "
            + ID_INSTANCIA + " integer not null,"
            + FILE_PATH + " text not null,"
            + STATUS + " text not null, "
            + WHEN_UPDATED + " text not null, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key (" + FECHA_MUESTRA + "," + CODIGO + "));";

    public static final String CREATE_OB_TABLE = "create table if not exists "
            + OB_TABLE + " ("
            + CODIGO + " integer not null, "
            + FECHA_OB  + " date not null, "
            + OBSEQ + " integer, "
            + CARNET + " integer, "
            + PERRETIRA  + " text, "
            + PERRETIRAREL + " integer, "
            + PERRETIRAOREL  + " text, "
            + OBS_TEL + " integer, "
            + CDOM  + " integer, "
            + BARRIO  + " integer, "
            + DIRECCION  + " text, "
            + OBS + " text, "
            + otrorecurso1  + " integer, "
            + ID_INSTANCIA + " integer not null,"
            + FILE_PATH + " text not null,"
            + STATUS + " text not null, "
            + WHEN_UPDATED + " text not null, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key ("+ FECHA_OB + "," + CODIGO + "));";

    public static final String CREATE_VIS_TABLE = "create table if not exists "
            + VIS_TABLE + " ("
            + CODIGO + " integer not null, "
            + FECHA_VISITA  + " date not null, "
            + VISITASN + " integer, "
            + MOTNOVIS + " integer, "
            + ACOMP_VIS + " text, "
            + REL_VIS + " integer, "
            + ASENT_VIS + " integer, "
            + otrorecurso1  + " integer, "
            + otrorecurso2  + " integer, "
            + otraRelacionFam + " text, "
            + carnetSN + " text, "
            + estudiaSN + " text, "
            + nEscuela + " text, "
            + otraEscuela + " text, "
            + turno + " text, "
            + otroMotNoVisita + " text, "
            + ID_INSTANCIA + " integer not null,"
            + FILE_PATH + " text not null,"
            + STATUS + " text not null, "
            + WHEN_UPDATED + " text not null, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key (" + FECHA_VISITA + "," + CODIGO + "));";


    public static final String CREATE_DAT_VIS_TABLE = "create table if not exists "
            + DAT_VIS_TABLE + " ("
            + CODIGO + " integer not null, "
            + COD_CASA + " integer not null, "
            + FECHA_VISITA  + " date not null, "
            + CDOM_VIS + " integer, "
            + BARRIO_VIS + " integer, "
            + MANZ_VIS + " integer, "
            + DIRE_VIS + " text, "
            + COORD_VIS + " text, "
            + LAT_VIS + " real, "
            + LON_VIS + " real, "
            + otrorecurso1  + " integer, "
            + otrorecurso2  + " integer, "
            + telefonoClasif1 + " text, "
            + telefonoConv1 + " text, "
            + telefonoCel1 + " text, "
            + telefonoEmpresa1 + " text, "
            + telefono2SN + " text, "
            + telefonoClasif2 + " text, "
            + telefonoConv2 + " text, "
            + telefonoCel2 + " text, "
            + telefonoEmpresa2 + " text, "
            + telefono3SN + " text, "
            + telefonoClasif3 + " text, "
            + telefonoConv3 + " text, "
            + telefonoCel3 + " text, "
            + telefonoEmpresa3 + " text, "
            + telefono4SN + " text, "
            + telefonoClasif4 + " text, "
            + telefonoConv4 + " text, "
            + telefonoCel4 + " text, "
            + telefonoEmpresa4 + " text, "
            + candidatoNI + " text, "
            + nombreCandNI1 + " text, "
            + nombreCandNI2 + " text, "
            + apellidoCandNI1 + " text, "
            + apellidoCandNI2 + " text, "
            + nombreptTutorCandNI + " text, "
            + nombreptTutorCandNI2 + " text, "
            + apellidoptTutorCandNI + " text, "
            + apellidoptTutorCandNI2 + " text, "
            + relacionFamCandNI + " text, "
            + otraRelacionFamCandNI + " text, "


            + ID_INSTANCIA + " integer not null,"
            + FILE_PATH + " text not null,"
            + STATUS + " text not null, "
            + WHEN_UPDATED + " text not null, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key (" + FECHA_VISITA + "," + CODIGO + "));";

    public static final String CREATE_BHC_TABLE = "create table if not exists "
            + BHC_TABLE + " ("
            + CODIGO + " integer not null, "
            + FECHA_BHC  + " date not null, "
            + PAXGENE  + " boolean, "
            + VOLBHC  + " real, "
            + LUGAR  + " text, "
            + OBSBHC  + " text, "
            + USUARIO  + " text, "
            + STATUS + " text not null, "
            + TODAY  + " date, "
            + "primary key (" + FECHA_BHC + "," + CODIGO + "));";

    public static final String CREATE_SERO_TABLE = "create table if not exists "
            + SERO_TABLE + " ("
            + ID + " text not null, "
            + CODIGO + " integer not null, "
            + FECHA_SERO  + " date not null, "
            + VOLSERO  + " real, "
            + LUGAR  + " text, "
            + OBSSERO  + " text, "
            + USUARIO  + " text, "
            + STATUS + " text not null, "
            + TODAY  + " date, "
            + "primary key (" + ID + "));";

    public static final String CREATE_TPBMC_TABLE = "create table if not exists "
            + TPBMC_TABLE + " ("
            + RECURSO + " text not null, "
            + FECHA_TEMP  + " date not null, "
            + TEMP  + " real, "
            + LUGARTEMP  + " text, "
            + OBSTEMP  + " text, "
            + USUARIO  + " text, "
            + STATUS + " text not null, "
            + TODAY  + " date, "
            + "primary key (" + FECHA_TEMP + "," + RECURSO + "));";

    public static final String CREATE_TRB_TABLE = "create table if not exists "
            + TRB_TABLE + " ("
            + RECURSO + " text not null, "
            + FECHA_TEMP  + " date not null, "
            + TEMP  + " real, "
            + LUGARTEMP  + " text, "
            + OBSTEMP  + " text, "
            + USUARIO  + " text, "
            + STATUS + " text not null, "
            + TODAY  + " date, "
            + "primary key (" + FECHA_TEMP + "," + RECURSO + "));";

    public static final String CREATE_PIN_TABLE = "create table if not exists "
            + PIN_TABLE + " ("
            + CODIGO + " integer not null, "
            + FECHA_PIN  + " date not null, "
            + PINCHAZOS  + " integer, "
            + LUGAR  + " text, "
            + OBSPIN  + " text, "
            + USUARIO  + " text, "
            + STATUS + " text not null, "
            + TODAY  + " date, "
            + "primary key (" + FECHA_PIN + "," + CODIGO + "));";


    public static final String CREATE_RND_TABLE = "create table if not exists "
            + NO_DATA_TABLE + " ("
            + CODIGO + " integer not null, "
            + TODAY  + " date not null, "
            + RAZON  + " integer, "
            + ORAZON  + " text, "
            + USUARIO  + " text, "
            + STATUS + " text not null, "
            + "primary key (" + TODAY + "," + CODIGO + "));";

    public static final String CREATE_CAMBCASA_TABLE = "create table if not exists "
            + CAMB_CASA_TABLE + " ("
            + TODAY  + " date not null, "
            + codigo  + " integer, "
            + codCasaAnterior  + " integer, "
            + codCasaActual  + " integer, "
            + USUARIO  + " text, "
            + STATUS + " text not null, "
            + "primary key (" + TODAY + "));";

    public static final String CREATE_ENCSAT_TABLE = "create table if not exists "
            + ENC_SAT_TABLE + " ("
            + FECHA_ENC_SAT  + " date not null, "
            + ESTUDIO + " text, "
            + ATENPEREST + " integer, "
            + TIEMATEN + " integer, "
            + ATENPERADM + " integer, "
            + ATENPERENFERM + " integer, "
            + ATENPERMED + " integer, "
            + AMBATEN + " integer, "
            + ATENPERLAB + " integer, "
            + EXPLDXENF + " integer, "
            + FLUDENSN + " integer, "
            + FLUCONIMP + " integer, "
            + DENCONIMP + " integer, "
            + EXPLPELIGENF + " integer, "
            + EXPMEDCUID + " integer, "
            + otrorecurso1  + " integer, "
            + ID_INSTANCIA + " integer not null,"
            + FILE_PATH + " text not null,"
            + STATUS + " text not null, "
            + WHEN_UPDATED + " text not null, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key (" + FECHA_ENC_SAT + "));";

    public static final String CREATE_DATOSPARTOBB_TABLE = "create table if not exists "
            + DATOSPARTOBB_TABLE + " ("
            + CODIGO + " integer not null, "
            + fechaDatosParto  + " date not null, "
            + tipoParto + " text, "
            + tiempoEmb_sndr + " text, "
            + tiempoEmbSemana + " integer, "
            + docMedTiempoEmb_sn + " text, "
            + docMedTiempoEmb + " text, "
            + otroDocMedTiempoEmb + " text, "
            + fum  + " date, "
            + sg  + " integer, "
            + fumFueraRango_sn + " text, "
            + fumFueraRango_razon + " text, "
            + edadGest + " integer, "
            + docMedEdadGest_sn + " text, "
            + docMedEdadGest + " text, "
            + OtroDocMedEdadGest + " text, "
            + prematuro_sndr + " text, "
            + pesoBB_sndr  + " text, "
            + pesoBB  + " text, "
            + docMedPesoBB_sn  + " text, "
            + docMedPesoBB  + " text, "
            + otroDocMedPesoBB  + " text, "
            + tallaBB_sndr  + " text, "
            + tallaBB  + " text, "
            + docMedTallaBB_sn  + " text, "
            + docMedTallaBB  + " text, "
            + otroDocMedTallaBB  + " text, "
            + vacFluMadre_sn  + " text, "
            + fechaVacInf  + " date, "
            + docMedFecVacInfMadre_sn + " text, "
            + docMedFecVacInfMadre + " text, "
            + otroDocMedFecVacInfMadre + " text, "
            + docMedFUM_sn + " text, "
            + otrorecurso1  + " integer, "
            + otrorecurso2  + " integer, "
            + ID_INSTANCIA + " integer not null,"
            + FILE_PATH + " text not null,"
            + STATUS + " text not null, "
            + WHEN_UPDATED + " text not null, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key (" + fechaDatosParto + "," + CODIGO + "));";

    public static final String CREATE_NEWVAC_TABLE = "create table if not exists "
            + NEWVAC_TABLE + " ("
            + CODIGO + " integer not null, "
            + fechaRegistroVacuna  + " date not null, "
            + vacuna_sn  + " text, "
            + tvacunano  + " text, "
            + otroMotivoTvacunano  + " text, "
            + otrorecurso1  + " integer, "
            + ID_INSTANCIA + " integer not null,"
            + FILE_PATH + " text not null,"
            + STATUS + " text not null, "
            + WHEN_UPDATED + " text not null, "
            + START  + " text, "
            + END  + " text, "
            + DEVICE_ID  + " text, "
            + SIM_SERIAL + " text, "
            + PHONE_NUMBER  + " text, "
            + TODAY  + " date, "
            + USUARIO  + " text, "
            + DELETED  + " boolean, "
            + REC1    + " integer, "
            + REC2    + " integer, "
            + "primary key (" + fechaRegistroVacuna + "," + CODIGO + "));";

    public static final String CREATE_DOCS_TABLE = "create table if not exists "
            + DOCS_TABLE + " ("
            + CODIGO + " integer not null, "
            + fechaDocumento  + " date not null, "
            + tipoDoc  + " text, "
            + documento  + " blob, "
            + STATUS + " text not null, "
            + fechaRecepcion  + " date, "
            + USUARIO  + " text, "
            + TODAY  + " date, "
            + "primary key (" + fechaDocumento + "," + CODIGO + "));";
}
