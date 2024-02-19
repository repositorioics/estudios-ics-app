package ni.org.ics.estudios.appmovil.utils;

public class Covid19DBConstants {
    //Tabla covid_participantes
    public static final String PARTICIPANTE_COVID_TABLE = "covid_participantes";

    // Campos Participantes cohorte familia
    public static final String participante= "participante";

    //Crear tabla participantes cohorte familia
    public static final String CREATE_PARTICIPANTE_COVID_TABLE = "create table if not exists "
            + PARTICIPANTE_COVID_TABLE + " ("
            + participante + " integer not null, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + participante + "));";

    //tabla CasoCovid19
    public static final String COVID_CASOS_TABLE = "covid_casos";
    public static final String codigoCaso = "codigoCaso";
    public static final String casa = "casa";
    public static final String fechaIngreso = "fechaIngreso";
    public static final String inactivo = "inactivo";
    public static final String fechaInactivo = "fechaInactivo";

    //crear tabla ParticipanteCasoCovid19
    public static final String CREATE_COVID_CASOS_TABLE = "create table if not exists "
            + COVID_CASOS_TABLE + " ("
            + codigoCaso + " text not null, "
            + casa + " text null, "
            + inactivo + " text, "
            + fechaIngreso + " date, "
            + fechaInactivo + " date, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCaso + "));";

    //tabla ParticipanteCasoUO1
    public static final String COVID_PARTICIPANTES_CASOS_TABLE = "covid_participantes_casos";
    //campos tabla ParticipanteCasoCovid19
    public static final String codigoCasoParticipante = "codigoCasoParticipante";
    public static final String enfermo = "enfermo";
    public static final String fis = "fis";
    public static final String fif = "fif";
    public static final String positivoPor = "positivoPor";
    public static final String consentimiento = "consentimiento";
    //crear tabla ParticipanteCasoCovid19
    public static final String CREATE_COVID_PARTICIPANTES_CASOS_TABLE = "create table if not exists "
            + COVID_PARTICIPANTES_CASOS_TABLE + " ("
            + codigoCasoParticipante + " text not null, "
            + codigoCaso + " text not null, "
            + participante + " integer not null, "
            + enfermo + " text, "
            + positivoPor + " text, "
            + fif + " date, "
            + fis + " date, "
            + consentimiento + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCasoParticipante + "));";

    //Tabla VisitaSeguimientoCaso
    public static final String COVID_VISITAS_CASOS_TABLE = "covid_visitas_casos";

    //Campos VisitaSeguimientoCaso
    public static final String codigoCasoVisita = "codigoCasoVisita";
    public static final String fechaVisita = "fechaVisita";
    public static final String visita = "visita";
    public static final String horaProbableVisita = "horaProbableVisita";
    public static final String expCS = "expCS";
    public static final String temp = "temp";
    public static final String saturacionO2 = "saturacionO2";
    public static final String frecResp = "frecResp";
    public static final String fecIniPrimerSintoma = "fecIniPrimerSintoma";
    public static final String primerSintoma = "primerSintoma";

    //Crear VisitaSeguimientoCaso
    public static final String CREATE_COVID_VISITAS_CASOS_TABLE = "create table if not exists "
            + COVID_VISITAS_CASOS_TABLE + " ("
            + codigoCasoVisita + " text not null, "
            + codigoCasoParticipante + " text not null, "
            + fechaVisita + " date, "
            + visita + " text, "
            + horaProbableVisita + " text, "
            + expCS + " text, "
            + temp + " real, "
            + saturacionO2 + " integer, "
            + frecResp + " integer, "
            + fecIniPrimerSintoma + " date, "
            + primerSintoma + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCasoVisita + "));";

    //Tabla VisitaFallidaCasoCovid19
    public static final String COVID_VISITAS_FALLIDAS_CASOS_TABLE = "covid_visitas_fallidas_casos";

    //Campos VisitaFallidaCasoCovid19
    public static final String codigoFallaVisita = "codigoFallaVisita";
    public static final String codigoParticipanteCaso = "codigoParticipanteCaso";
    public static final String razonVisitaFallida = "razonVisitaFallida";
    public static final String otraRazon = "otraRazon";


    //Crear VisitaFallidaCasoCovid19
    public static final String CREATE_COVID_VISITAS_FALLIDAS_CASOS_TABLE = "create table if not exists "
            + COVID_VISITAS_FALLIDAS_CASOS_TABLE + " ("
            + codigoFallaVisita + " text not null, "
            + codigoParticipanteCaso + " text not null, "
            + fechaVisita + " date, "
            + razonVisitaFallida + " text, "
            + otraRazon + " text, "
            + visita + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoFallaVisita + "));";

    //Tabla CandidatoTransmisionCovid19
    public static final String COVID_CANDIDATO_TRANSMISION_TABLE = "covid_candidato_transmision";

    //Campos CandidatoTransmisionCovid19
    public static final String codigo = "codigo";
    public static final String casaCHF = "casaCHF";
    public static final String estActuales = "estActuales";
    public static final String indice = "indice";

    //Crear CandidatoTransmisionCovid19
    public static final String CREATE_COVID_CANDIDATO_TRANSMISION_TABLE = "create table if not exists "
            + COVID_CANDIDATO_TRANSMISION_TABLE + " ("
            + codigo + " text not null, "
            + participante + " integer not null, "
            + casaCHF + " text, "
            + fis + " date, "
            + fif + " date, "
            + positivoPor + " text, "
            + consentimiento + " text, "
            + estActuales + " text, "
            + fechaIngreso + " date, "
            + indice + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigo + "));";

    //Tabla SintomasVisitaCasoCovid19
    public static final String COVID_SINTOMAS_VISITA_CASO_TABLE = "covid_sintomas_visita_caso";

    //Campos tabla SintomasVisitaCasoCovid19
    public static final String codigoCasoSintoma = "codigoCasoSintoma";
    public static final String fechaSintomas = "fechaSintomas";
    public static final String fiebre = "fiebre";
    public static final String fiebreIntensidad = "fiebreIntensidad";
    public static final String fiebreCuantificada = "fiebreCuantificada";
    public static final String valorFiebreCuantificada = "valorFiebreCuantificada";
    public static final String dolorCabeza = "dolorCabeza";
    public static final String dolorCabezaIntensidad = "dolorCabezaIntensidad";
    public static final String dolorArticular = "dolorArticular";
    public static final String dolorArticularIntensidad = "dolorArticularIntensidad";
    public static final String dolorMuscular = "dolorMuscular";
    public static final String dolorMuscularIntensidad = "dolorMuscularIntensidad";
    public static final String dificultadRespiratoria = "dificultadRespiratoria";
    public static final String secrecionNasal = "secrecionNasal";
    public static final String secrecionNasalIntensidad = "secrecionNasalIntensidad";
    public static final String tos = "tos";
    public static final String tosIntensidad = "tosIntensidad";
    public static final String pocoApetito = "pocoApetito";
    public static final String dolorGarganta = "dolorGarganta";
    public static final String dolorGargantaIntensidad = "dolorGargantaIntensidad";
    public static final String picorGarganta = "picorGarganta";
    public static final String congestionNasal = "congestionNasal";
    public static final String rash = "rash";
    public static final String urticaria = "urticaria";
    public static final String conjuntivitis = "conjuntivitis";
    public static final String expectoracion = "expectoracion";
    public static final String diarrea = "diarrea";
    public static final String vomito = "vomito";
    public static final String fatiga = "fatiga";
    public static final String respiracionRuidosa = "respiracionRuidosa";
    public static final String respiracionRapida = "respiracionRapida";
    public static final String perdidaOlfato = "perdidaOlfato";
    public static final String perdidaGusto = "perdidaGusto";
    public static final String desmayos = "desmayos";
    public static final String sensacionPechoApretado = "sensacionPechoApretado";
    public static final String dolorPecho = "dolorPecho";
    public static final String sensacionFaltaAire = "sensacionFaltaAire";
    public static final String quedoCama = "quedoCama";
    public static final String tratamiento = "tratamiento";
    public static final String fechaInicioTx = "fechaInicioTx";
    public static final String cualMedicamento = "cualMedicamento";
    public static final String otroMedicamento = "otroMedicamento";
    public static final String antibiotico = "antibiotico";
    public static final String prescritoMedico = "prescritoMedico";
    public static final String factorRiesgo = "factorRiesgo";
    public static final String otroFactorRiesgo = "otroFactorRiesgo";
    public static final String otraPersonaEnferma = "otraPersonaEnferma";
    public static final String cuantasPersonas = "cuantasPersonas";
    public static final String trasladoHospital = "trasladoHospital";
    public static final String fechaAlta = "fechaAlta";
    public static final String cualHospital = "cualHospital";
    public static final String hospitalizado = "hospitalizado";

    //Crear CandidatoTransmisionCovid19
    public static final String CREATE_COVID_SINTOMAS_VISITA_CASO_TABLE = "create table if not exists "
            + COVID_SINTOMAS_VISITA_CASO_TABLE + " ("
            +  codigoCasoSintoma + " text not null, "
            +  codigoCasoVisita + " text not null, "
            +  fechaSintomas + " text, "
            +  fiebre + " text, "
            +  fiebreIntensidad + " text, "
            +  fif + " date, "
            +  fiebreCuantificada + " text, "
            +  valorFiebreCuantificada + " real, "
            +  dolorCabeza + " text, "
            +  dolorCabezaIntensidad + " text, "
            +  dolorArticular + " text, "
            +  dolorArticularIntensidad + " text, "
            +  dolorMuscular + " text, "
            +  dolorMuscularIntensidad + " text, "
            +  dificultadRespiratoria + " text, "
            +  secrecionNasal + " text, "
            +  secrecionNasalIntensidad + " text, "
            +  tos + " text, "
            +  tosIntensidad + " text, "
            +  pocoApetito + " text, "
            +  dolorGarganta + " text, "
            +  dolorGargantaIntensidad + " text, "
            +  picorGarganta + " text, "
            +  congestionNasal + " text, "
            +  rash + " text, "
            +  urticaria + " text, "
            +  conjuntivitis + " text, "
            +  expectoracion + " text, "
            +  diarrea + " text, "
            +  vomito + " text, "
            +  fatiga + " text, "
            +  respiracionRuidosa + " text, "
            +  respiracionRapida + " text, "
            +  perdidaOlfato + " text, "
            +  perdidaGusto + " text, "
            +  desmayos + " text, "
            +  sensacionPechoApretado + " text, "
            +  dolorPecho + " text, "
            +  sensacionFaltaAire + " text, "
            +  quedoCama + " text, "
            +  tratamiento + " text, "
            +  fechaInicioTx + " date, "
            +  cualMedicamento + " text, "
            +  otroMedicamento + " text, "
            +  antibiotico + " text, "
            +  prescritoMedico + " text, "
            +  factorRiesgo + " text, "
            +  otroFactorRiesgo + " text, "
            +  otraPersonaEnferma + " text, "
            +  cuantasPersonas + " integer, "
            +  trasladoHospital + " text, "
            +  cualHospital + " text, "
            +  hospitalizado + " text, "
            +  fechaAlta + " date, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCasoSintoma + "));";


    //Tabla DatosAislamientoVisitaCasoCovid19
    public static final String COVID_DATOS_AISLAMIENTO_VC_TABLE = "covid_datos_aislamiento_vc";

    //Campos tabla DatosAislamientoVisitaCasoCovid19
    public static final String codigoAislamiento = "codigoAislamiento";
    public static final String fechaDato = "fechaDato";
    public static final String aislado = "aislado";
    public static final String dormirSoloComparte = "dormirSoloComparte";
    public static final String banioPropioComparte = "banioPropioComparte";
    public static final String alejadoFamilia = "alejadoFamilia";
    public static final String tieneContacto = "tieneContacto";
    public static final String conQuienTieneContacto = "conQuienTieneContacto";
    public static final String lugares = "lugares";
    public static final String otrosLugares = "otrosLugares";

    //Crear DatosAislamientoVisitaCasoCovid19
    public static final String CREATE_COVID_DATOS_AISLAMIENTO_VC_TABLE = "create table if not exists "
            + COVID_DATOS_AISLAMIENTO_VC_TABLE + " ("
            + codigoAislamiento + " text not null, "
            + codigoCasoVisita + " text not null, "
            + fechaDato + " date not null, "
            + aislado + " text, "
            + dormirSoloComparte + " text, "
            + banioPropioComparte + " text, "
            + alejadoFamilia + " text, "
            + tieneContacto + " text, "
            + conQuienTieneContacto + " text, "
            + lugares + " text, "
            + otrosLugares + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoAislamiento + "));";


    //Tabla VisitaFinalCasoCovid19
    public static final String COVID_VISITA_FINAL_CASO_TABLE = "covid_visita_final_caso";

    //Campos VisitaFinalCasoCovid19
    public static final String codigoVisitaFinal = "codigoVisitaFinal";
    public static final String consTerreno = "consTerreno";
    public static final String referidoCS = "referidoCS";
    public static final String queAntibiotico = "queAntibiotico";
    public static final String sintResp = "sintResp";
    public static final String fueHospitalizado = "fueHospitalizado";
    public static final String fechaEgreso = "fechaEgreso";
    public static final String diasHospitalizado = "diasHospitalizado";
    public static final String utilizoOxigeno = "utilizoOxigeno";
    public static final String fueIntubado = "fueIntubado";
    public static final String estadoSalud = "estadoSalud";
    public static final String faltoTrabajoEscuela = "faltoTrabajoEscuela";
    public static final String diasFaltoTrabajoEscuela = "diasFaltoTrabajoEscuela";

    //Crear VisitaFinalCasoCovid19
    public static final String CREATE_COVID_VISITA_FINAL_CASO_TABLE = "create table if not exists "
            + COVID_VISITA_FINAL_CASO_TABLE + " ("
            +  codigoVisitaFinal + " text not null, "
            +  codigoParticipanteCaso + " text not null, "
            +  fechaVisita + " date, "
            +  enfermo + " text, "
            +  consTerreno + " text, "
            +  referidoCS + " text, "
            +  tratamiento + " text, "
            +  queAntibiotico + " text, "
            +  otroMedicamento + " text, "
            +  sintResp + " text, "
            +  fueHospitalizado + " text, "
            +  fechaIngreso + " date, "
            +  fechaEgreso + " date, "
            +  diasHospitalizado + " integer, "
            +  cualHospital + " text, "
            +  utilizoOxigeno + " text, "
            +  fueIntubado + " text, "
            +  estadoSalud + " text, "
            +  faltoTrabajoEscuela + " text, "
            +  diasFaltoTrabajoEscuela + " integer, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoVisitaFinal + "));";

    //Tabla SintomasVisitaFinalCovid19
    public static final String COVID_SINT_VISITA_FINAL_CASO_TABLE = "covid_sintomas_visita_final_caso";

    //Campos tabla SintomasVisitaFinalCovid19
    public static final String fiebreFecIni = "fiebreFecIni";
    public static final String tosFecIni = "tosFecIni";
    public static final String dolorCabezaFecIni = "dolorCabezaFecIni";
    public static final String dolorArticularFecIni = "dolorArticularFecIni";
    public static final String dolorMuscularFecIni = "dolorMuscularFecIni";
    public static final String dificultadRespiratoriaFecIni = "dificultadRespiratoriaFecIni";
    public static final String pocoApetitoFecIni = "pocoApetitoFecIni";
    public static final String dolorGargantaFecIni = "dolorGargantaFecIni";
    public static final String secrecionNasalFecIni = "secrecionNasalFecIni";
    public static final String picorGargantaFecIni = "picorGargantaFecIni";
    public static final String expectoracionFecIni = "expectoracionFecIni";
    public static final String rashFecIni = "rashFecIni";
    public static final String urticariaFecIni = "urticariaFecIni";
    public static final String conjuntivitisFecIni = "conjuntivitisFecIni";
    public static final String diarreaFecIni = "diarreaFecIni";
    public static final String vomitoFecIni = "vomitoFecIni";
    public static final String quedoCamaFecIni = "quedoCamaFecIni";
    public static final String respiracionRuidosaFecIni = "respiracionRuidosaFecIni";
    public static final String respiracionRapidaFecIni = "respiracionRapidaFecIni";
    public static final String perdidaOlfatoFecIni = "perdidaOlfatoFecIni";
    public static final String congestionNasalFecIni = "congestionNasalFecIni";
    public static final String perdidaGustoFecIni = "perdidaGustoFecIni";
    public static final String desmayosFecIni = "desmayosFecIni";
    public static final String sensacionPechoApretadoFecIni = "sensacionPechoApretadoFecIni";
    public static final String dolorPechoFecIni = "dolorPechoFecIni";
    public static final String sensacionFaltaAireFecIni = "sensacionFaltaAireFecIni";
    public static final String fatigaFecIni = "fatigaFecIni";
    public static final String fiebreFecFin = "fiebreFecFin";
    public static final String tosFecFin = "tosFecFin";
    public static final String dolorCabezaFecFin = "dolorCabezaFecFin";
    public static final String dolorArticularFecFin = "dolorArticularFecFin";
    public static final String dolorMuscularFecFin = "dolorMuscularFecFin";
    public static final String dificultadRespiratoriaFecFin = "dificultadRespiratoriaFecFin";
    public static final String pocoApetitoFecFin = "pocoApetitoFecFin";
    public static final String dolorGargantaFecFin = "dolorGargantaFecFin";
    public static final String secrecionNasalFecFin = "secrecionNasalFecFin";
    public static final String picorGargantaFecFin = "picorGargantaFecFin";
    public static final String expectoracionFecFin = "expectoracionFecFin";
    public static final String rashFecFin = "rashFecFin";
    public static final String urticariaFecFin = "urticariaFecFin";
    public static final String conjuntivitisFecFin = "conjuntivitisFecFin";
    public static final String diarreaFecFin = "diarreaFecFin";
    public static final String vomitoFecFin = "vomitoFecFin";
    public static final String quedoCamaFecFin = "quedoCamaFecFin";
    public static final String respiracionRuidosaFecFin = "respiracionRuidosaFecFin";
    public static final String respiracionRapidaFecFin = "respiracionRapidaFecFin";
    public static final String perdidaOlfatoFecFin = "perdidaOlfatoFecFin";
    public static final String congestionNasalFecFin = "congestionNasalFecFin";
    public static final String perdidaGustoFecFin = "perdidaGustoFecFin";
    public static final String desmayosFecFin = "desmayosFecFin";
    public static final String sensacionPechoApretadoFecFin = "sensacionPechoApretadoFecFin";
    public static final String dolorPechoFecFin = "dolorPechoFecFin";
    public static final String sensacionFaltaAireFecFin = "sensacionFaltaAireFecFin";
    public static final String fatigaFecFin = "fatigaFecFin";

    //Crear SintomasVisitaFinalCovid19
    public static final String CREATE_COVID_SINT_VISITA_FINAL_CASO_TABLE = "create table if not exists "
            + COVID_SINT_VISITA_FINAL_CASO_TABLE + " ("
            + codigoCasoSintoma + " text not null, "
            + codigoVisitaFinal + " text not null, "
            + fiebre + " text, "
            + tos + " text, "
            + dolorCabeza + " text, "
            + dolorArticular + " text, "
            + dolorMuscular + " text, "
            + dificultadRespiratoria + " text, "
            + pocoApetito + " text, "
            + dolorGarganta + " text, "
            + secrecionNasal + " text, "
            + picorGarganta + " text, "
            + expectoracion + " text, "
            + rash + " text, "
            + urticaria + " text, "
            + conjuntivitis + " text, "
            + diarrea + " text, "
            + vomito + " text, "
            + quedoCama + " text, "
            + respiracionRuidosa + " text, "
            + respiracionRapida + " text, "
            + perdidaOlfato + " text, "
            + congestionNasal + " text, "
            + perdidaGusto + " text, "
            + desmayos + " text, "
            + sensacionPechoApretado + " text, "
            + dolorPecho + " text, "
            + sensacionFaltaAire + " text, "
            + fatiga + " text, "
            + fiebreFecIni + " date, "
            + tosFecIni + " date, "
            + dolorCabezaFecIni + " date, "
            + dolorArticularFecIni + " date, "
            + dolorMuscularFecIni + " date, "
            + dificultadRespiratoriaFecIni + " date, "
            + pocoApetitoFecIni + " date, "
            + dolorGargantaFecIni + " date, "
            + secrecionNasalFecIni + " date, "
            + picorGargantaFecIni + " date, "
            + expectoracionFecIni + " date, "
            + rashFecIni + " date, "
            + urticariaFecIni + " date, "
            + conjuntivitisFecIni + " date, "
            + diarreaFecIni + " date, "
            + vomitoFecIni + " date, "
            + quedoCamaFecIni + " date, "
            + respiracionRuidosaFecIni + " date, "
            + respiracionRapidaFecIni + " date, "
            + perdidaOlfatoFecIni + " date, "
            + congestionNasalFecIni + " date, "
            + perdidaGustoFecIni + " date, "
            + desmayosFecIni + " date, "
            + sensacionPechoApretadoFecIni + " date, "
            + dolorPechoFecIni + " date, "
            + sensacionFaltaAireFecIni + " date, "
            + fatigaFecIni + " date, "
            + fiebreFecFin + " date, "
            + tosFecFin + " date, "
            + dolorCabezaFecFin + " date, "
            + dolorArticularFecFin + " date, "
            + dolorMuscularFecFin + " date, "
            + dificultadRespiratoriaFecFin + " date, "
            + pocoApetitoFecFin + " date, "
            + dolorGargantaFecFin + " date, "
            + secrecionNasalFecFin + " date, "
            + picorGargantaFecFin + " date, "
            + expectoracionFecFin + " date, "
            + rashFecFin + " date, "
            + urticariaFecFin + " date, "
            + conjuntivitisFecFin + " date, "
            + diarreaFecFin + " date, "
            + vomitoFecFin + " date, "
            + quedoCamaFecFin + " date, "
            + respiracionRuidosaFecFin + " date, "
            + respiracionRapidaFecFin + " date, "
            + perdidaOlfatoFecFin + " date, "
            + congestionNasalFecFin + " date, "
            + perdidaGustoFecFin + " date, "
            + desmayosFecFin + " date, "
            + sensacionPechoApretadoFecFin + " date, "
            + dolorPechoFecFin + " date, "
            + sensacionFaltaAireFecFin + " date, "
            + fatigaFecFin + " date, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCasoSintoma + "));";

    //Tabla covid_cuestionario
    public static final String COVID_CUESTIONARIO_TABLE = "covid_cuestionario";

    //Campos tabla covid_cuestionario
    public static final String feb20Febricula = "feb20Febricula";
    public static final String feb20Fiebre = "feb20Fiebre";
    public static final String feb20Escalofrio = "feb20Escalofrio";
    public static final String feb20TemblorEscalofrio = "feb20TemblorEscalofrio";
    public static final String feb20DolorMuscular = "feb20DolorMuscular";
    public static final String feb20DolorArticular = "feb20DolorArticular";
    public static final String feb20SecresionNasal = "feb20SecresionNasal";
    public static final String feb20DolorGarganta = "feb20DolorGarganta";
    public static final String feb20Tos = "feb20Tos";
    public static final String feb20DificultadResp = "feb20DificultadResp";
    public static final String feb20DolorPecho = "feb20DolorPecho";
    public static final String feb20NauseasVomito = "feb20NauseasVomito";
    public static final String feb20DolorCabeza = "feb20DolorCabeza";
    public static final String feb20DolorAbdominal = "feb20DolorAbdominal";
    public static final String feb20Diarrea = "feb20Diarrea";
    public static final String feb20DificultadDormir = "feb20DificultadDormir";
    public static final String feb20Debilidad = "feb20Debilidad";
    public static final String feb20PerdidaOlfatoGusto = "feb20PerdidaOlfatoGusto";
    public static final String feb20Mareo = "feb20Mareo";
    public static final String feb20Sarpullido = "feb20Sarpullido";
    public static final String feb20Desmayo = "feb20Desmayo";
    public static final String feb20QuedoCama = "feb20QuedoCama";

    public static final String e1SabeFIS = "sabeFISE1";
    public static final String e1FIS = "e1FIS";
    public static final String e1MesInicioSintoma = "mesInicioSintomaE1";
    public static final String e1AnioInicioSintoma = "anioInicioSintomaE1";
    public static final String padecidoCovid19 = "padecidoCovid19";
    public static final String e1ConoceLugarExposicion = "conoceLugarExposicionE1";
    public static final String e1LugarExposicion = "lugarExposicionE1";
    public static final String e1BuscoAyuda = "buscoAyudaE1";
    public static final String e1DondeBuscoAyuda = "dondeBuscoAyudaE1";
    public static final String e1NombreCentroSalud = "nombreCentroSaludE1";
    public static final String e1NombreHospital = "nombreHospitalE1";
    public static final String e1RecibioSeguimiento = "recibioSeguimientoE1";
    public static final String e1TipoSeguimiento = "tipoSeguimientoE1";
    public static final String e1TmpDespuesBuscoAyuda = "tmpDespuesBuscoAyudaE1";
    public static final String e1UnaNocheHospital = "unaNocheHospitalE1";
    public static final String e1QueHospital = "queHospitalE1";
    public static final String e1SabeCuantasNoches = "sabeCuantasNochesE1";
    public static final String e1CuantasNochesHosp = "cuantasNochesHospE1";
    public static final String e1SabeFechaAdmision = "sabeFechaAdmisionE1";
    public static final String e1FechaAdmisionHosp = "fechaAdmisionHospE1";
    public static final String e1SabeFechaAlta = "sabeFechaAltaE1";
    public static final String e1FechaAltaHosp = "fechaAltaHospE1";
    public static final String e1UtilizoOxigeno = "utilizoOxigenoE1";
    public static final String e1EstuvoUCI = "estuvoUCIE1";
    public static final String e1FueIntubado = "fueIntubadoE1";
    public static final String e1RecuperadoCovid19 = "recuperadoCovid19E1";
    public static final String e1TenidoFebricula = "febriculaE1Tenido";
    public static final String e1TenidoCansancio = "cansancioE1Tenido";
    public static final String e1TenidoDolorCabeza = "dolorCabezaE1";
    public static final String e1TenidoPerdidaOlfato = "perdidaOlfatoE1";
    public static final String e1TenidoPerdidaGusto = "perdidaGustoE1";
    public static final String e1TenidoTos = "tosE1";
    public static final String e1TenidoDificultadRespirar = "dificultadRespirarE1Tenido";
    public static final String e1TenidoDolorPecho = "dolorPechoE1Tenido";
    public static final String e1TenidoPalpitaciones = "palpitacionesE1Tenido";
    public static final String e1TenidoDolorArticulaciones = "dolorArticulacionesE1Tenido";
    public static final String e1TenidoParalisis = "paralisisE1Tenido";
    public static final String e1TenidoMareos = "mareosE1Tenido";
    public static final String e1TenidoPensamientoNublado = "pensamientoNubladoE1Tenido";
    public static final String e1TenidoProblemasDormir = "problemasDormirE1Tenido";
    public static final String e1TenidoDepresion = "depresionE1Tenido";
    public static final String e1TenidoOtrosSintomas = "otrosSintomasE1Tenido";
    public static final String e1CualesSintomas = "cualesSintomasE1";
    public static final String e1SabeTiempoRecuperacion = "sabeTiempoRecuperacionE1";
    public static final String e1TiempoRecuperacion = "tiempoRecuperacionE1";
    public static final String e1TiempoRecuperacionEn = "tiempoRecuperacionEnE1";
    public static final String e1SeveridadEnfermedad = "severidadEnfermedadE1";
    public static final String e1TomoMedicamento = "tomoMedicamentoE1";
    public static final String e1QueMedicamento = "queMedicamentoE1";
    public static final String e1OtroMedicamento = "otroMedicamentoE1";
    public static final String e1OxigenoDomicilio = "oxigenoDomicilioE1";
    public static final String e1TiempoOxigenoDom = "tiempoOxigenoDomE1";
    //evento2
    public static final String e2SabeFIS = "sabeFISE2";
    public static final String e2FIS = "e2FIS";
    public static final String e2MesInicioSintoma = "mesInicioSintomaE2";
    public static final String e2AnioInicioSintoma = "anioInicioSintomaE2";
    public static final String e2ConoceLugarExposicion = "conoceLugarExposicionE2";
    public static final String e2LugarExposicion = "lugarExposicionE2";
    public static final String e2BuscoAyuda = "buscoAyudaE2";
    public static final String e2DondeBuscoAyuda = "dondeBuscoAyudaE2";
    public static final String e2NombreCentroSalud = "nombreCentroSaludE2";
    public static final String e2NombreHospital = "nombreHospitalE2";
    public static final String e2RecibioSeguimiento = "recibioSeguimientoE2";
    public static final String e2TipoSeguimiento = "tipoSeguimientoE2";
    public static final String e2TmpDespuesBuscoAyuda = "tmpDespuesBuscoAyudaE2";
    public static final String e2UnaNocheHospital = "unaNocheHospitalE2";
    public static final String e2QueHospital = "queHospitalE2";
    public static final String e2SabeCuantasNoches = "sabeCuantasNochesE2";
    public static final String e2CuantasNochesHosp = "cuantasNochesHospE2";
    public static final String e2SabeFechaAdmision = "sabeFechaAdmisionE2";
    public static final String e2FechaAdmisionHosp = "fechaAdmisionHospE2";
    public static final String e2SabeFechaAlta = "sabeFechaAltaE2";
    public static final String e2FechaAltaHosp = "fechaAltaHospE2";
    public static final String e2UtilizoOxigeno = "utilizoOxigenoE2";
    public static final String e2EstuvoUCI = "estuvoUCIE2";
    public static final String e2FueIntubado = "fueIntubadoE2";
    public static final String e2RecuperadoCovid19 = "recuperadoCovid19E2";
    public static final String e2TenidoFebricula = "febriculaE2Tenido";
    public static final String e2TenidoCansancio = "cansancioE2Tenido";
    public static final String e2TenidoDolorCabeza = "dolorCabezaE2";
    public static final String e2TenidoPerdidaOlfato = "perdidaOlfatoE2";
    public static final String e2TenidoPerdidaGusto = "perdidaGustoE2";
    public static final String e2TenidoTos = "tosE2";
    public static final String e2TenidoDificultadRespirar = "dificultadRespirarE2Tenido";
    public static final String e2TenidoDolorPecho = "dolorPechoE2Tenido";
    public static final String e2TenidoPalpitaciones = "palpitacionesE2Tenido";
    public static final String e2TenidoDolorArticulaciones = "dolorArticulacionesE2Tenido";
    public static final String e2TenidoParalisis = "paralisisE2Tenido";
    public static final String e2TenidoMareos = "mareosE2Tenido";
    public static final String e2TenidoPensamientoNublado = "pensamientoNubladoE2Tenido";
    public static final String e2TenidoProblemasDormir = "problemasDormirE2Tenido";
    public static final String e2TenidoDepresion = "depresionE2Tenido";
    public static final String e2TenidoOtrosSintomas = "otrosSintomasE2Tenido";
    public static final String e2CualesSintomas = "cualesSintomasE2";
    public static final String e2SabeTiempoRecuperacion = "sabeTiempoRecuperacionE2";
    public static final String e2TiempoRecuperacion = "tiempoRecuperacionE2";
    public static final String e2TiempoRecuperacionEn = "tiempoRecuperacionEnE2";
    public static final String e2SeveridadEnfermedad = "severidadEnfermedadE2";
    public static final String e2TomoMedicamento = "tomoMedicamentoE2";
    public static final String e2QueMedicamento = "queMedicamentoE2";
    public static final String e2OtroMedicamento = "otroMedicamentoE2";
    public static final String e2OxigenoDomicilio = "oxigenoDomicilioE2";
    public static final String e2TiempoOxigenoDom = "tiempoOxigenoDomE2";
    //evento3
    public static final String e3SabeFIS = "sabeFISE3";
    public static final String e3FIS = "e3FIS";
    public static final String e3MesInicioSintoma = "mesInicioSintomaE3";
    public static final String e3AnioInicioSintoma = "anioInicioSintomaE3";
    public static final String e3ConoceLugarExposicion = "conoceLugarExposicionE3";
    public static final String e3LugarExposicion = "lugarExposicionE3";
    public static final String e3BuscoAyuda = "buscoAyudaE3";
    public static final String e3DondeBuscoAyuda = "dondeBuscoAyudaE3";
    public static final String e3NombreCentroSalud = "nombreCentroSaludE3";
    public static final String e3NombreHospital = "nombreHospitalE3";
    public static final String e3RecibioSeguimiento = "recibioSeguimientoE3";
    public static final String e3TipoSeguimiento = "tipoSeguimientoE3";
    public static final String e3TmpDespuesBuscoAyuda = "tmpDespuesBuscoAyudaE3";
    public static final String e3UnaNocheHospital = "unaNocheHospitalE3";
    public static final String e3QueHospital = "queHospitalE3";
    public static final String e3SabeCuantasNoches = "sabeCuantasNochesE3";
    public static final String e3CuantasNochesHosp = "cuantasNochesHospE3";
    public static final String e3SabeFechaAdmision = "sabeFechaAdmisionE3";
    public static final String e3FechaAdmisionHosp = "fechaAdmisionHospE3";
    public static final String e3SabeFechaAlta = "sabeFechaAltaE3";
    public static final String e3FechaAltaHosp = "fechaAltaHospE3";
    public static final String e3UtilizoOxigeno = "utilizoOxigenoE3";
    public static final String e3EstuvoUCI = "estuvoUCIE3";
    public static final String e3FueIntubado = "fueIntubadoE3";
    public static final String e3RecuperadoCovid19 = "recuperadoCovid19E3";
    public static final String e3TenidoFebricula = "febriculaE3Tenido";
    public static final String e3TenidoCansancio = "cansancioE3Tenido";
    public static final String e3TenidoDolorCabeza = "dolorCabezaE3";
    public static final String e3TenidoPerdidaOlfato = "perdidaOlfatoE3";
    public static final String e3TenidoPerdidaGusto = "perdidaGustoE3";
    public static final String e3TenidoTos = "tosE3";
    public static final String e3TenidoDificultadRespirar = "dificultadRespirarE3Tenido";
    public static final String e3TenidoDolorPecho = "dolorPechoE3Tenido";
    public static final String e3TenidoPalpitaciones = "palpitacionesE3Tenido";
    public static final String e3TenidoDolorArticulaciones = "dolorArticulacionesE3Tenido";
    public static final String e3TenidoParalisis = "paralisisE3Tenido";
    public static final String e3TenidoMareos = "mareosE3Tenido";
    public static final String e3TenidoPensamientoNublado = "pensamientoNubladoE3Tenido";
    public static final String e3TenidoProblemasDormir = "problemasDormirE3Tenido";
    public static final String e3TenidoDepresion = "depresionE3Tenido";
    public static final String e3TenidoOtrosSintomas = "otrosSintomasE3Tenido";
    public static final String e3CualesSintomas = "cualesSintomasE3";
    public static final String e3SabeTiempoRecuperacion = "sabeTiempoRecuperacionE3";
    public static final String e3TiempoRecuperacion = "tiempoRecuperacionE3";
    public static final String e3TiempoRecuperacionEn = "tiempoRecuperacionEnE3";
    public static final String e3SeveridadEnfermedad = "severidadEnfermedadE3";
    public static final String e3TomoMedicamento = "tomoMedicamentoE3";
    public static final String e3QueMedicamento = "queMedicamentoE3";
    public static final String e3OtroMedicamento = "otroMedicamentoE3";
    public static final String e3OxigenoDomicilio = "oxigenoDomicilioE3";
    public static final String e3TiempoOxigenoDom = "tiempoOxigenoDomE3";

    public static final String padeceEnfisema = "padeceEnfisema";
    public static final String padeceAsma = "padeceAsma";
    public static final String padeceDiabetes = "padeceDiabetes";
    public static final String padeceEnfCoronaria = "padeceEnfCoronaria";
    public static final String padecePresionAlta = "padecePresionAlta";
    public static final String padeceEnfHigado = "padeceEnfHigado";
    public static final String padeceEnfRenal = "padeceEnfRenal";
    public static final String padeceInfartoDerrameCer = "padeceInfartoDerrameCer";
    public static final String padeceCancer = "padeceCancer";
    public static final String padeceCondicionInmuno = "padeceCondicionInmuno";
    public static final String padeceEnfAutoinmune = "padeceEnfAutoinmune";
    public static final String padeceDiscapacidadFis = "padeceDiscapacidadFis";
    public static final String padeceCondPsicPsiq = "padeceCondPsicPsiq";
    public static final String padeceOtraCondicion = "padeceOtraCondicion";
    public static final String queOtraCondicion = "queOtraCondicion";
    public static final String fumado = "fumado";
    public static final String fumadoCienCigarrillos = "fumadoCienCigarrillos";

    public static final String e1FumadoPrevioEnfermedad = "fumadoPrevioEnfermedadE1";
    public static final String e1FumaActualmente = "fumaActualmenteE1";
    //MA2021
    public static final String e1Embarazada = "embarazadaE1";
    public static final String e1RecuerdaSemanasEmb = "recuerdaSemanasEmbE1";
    public static final String e1SemanasEmbarazo = "semanasEmbarazoE1";
    public static final String e1FinalEmbarazo = "finalEmbarazoE1";
    public static final String e1OtroFinalEmbarazo = "otroFinalEmbarazoE1";
    public static final String e1DabaPecho = "dabaPechoE1";
    //evento2
    public static final String e2FumadoPrevioEnfermedad = "fumadoPrevioEnfermedadE2";
    public static final String e2FumaActualmente = "fumaActualmenteE2";
    public static final String e2Embarazada = "embarazadaE2";
    public static final String e2RecuerdaSemanasEmb = "recuerdaSemanasEmbE2";
    public static final String e2SemanasEmbarazo = "semanasEmbarazoE2";
    public static final String e2FinalEmbarazo = "finalEmbarazoE2";
    public static final String e2OtroFinalEmbarazo = "otroFinalEmbarazoE2";
    public static final String e2DabaPecho = "dabaPechoE2";
    //evento3
    public static final String e3FumadoPrevioEnfermedad = "fumadoPrevioEnfermedadE3";
    public static final String e3FumaActualmente = "fumaActualmenteE3";
    public static final String e3Embarazada = "embarazadaE3";
    public static final String e3RecuerdaSemanasEmb = "recuerdaSemanasEmbE3";
    public static final String e3SemanasEmbarazo = "semanasEmbarazoE3";
    public static final String e3FinalEmbarazo = "finalEmbarazoE3";
    public static final String e3OtroFinalEmbarazo = "otroFinalEmbarazoE3";
    public static final String e3DabaPecho = "dabaPechoE3";

    public static final String trabajadorSalud = "trabajadorSalud";
    public static final String periodoSintomas = "periodoSintomas";
    //Muestreo Familia Nov 2021
    public static final String enfermoCovid19 = "enfermoCovid19";
    public static final String cuantasVecesEnfermo = "cuantasVecesEnfermo";
    public static final String sabeEvento1 = "sabeEvento1";
    public static final String evento1 = "evento1";
    public static final String anioEvento1 = "anioEvento1";
    public static final String mesEvento1 = "mesEvento1";
    public static final String sabeEvento2 = "sabeEvento2";
    public static final String evento2 = "evento2";
    public static final String anioEvento2 = "anioEvento2";
    public static final String mesEvento2 = "mesEvento2";
    public static final String sabeEvento3 = "sabeEvento3";
    public static final String evento3 = "evento3";
    public static final String anioEvento3 = "anioEvento3";
    public static final String mesEvento3 = "mesEvento3";

    public static final String e1Febricula = "e1Febricula";
    public static final String e1Fiebre = "e1Fiebre";
    public static final String e1Escalofrio = "e1Escalofrio";
    public static final String e1TemblorEscalofrio = "e1TemblorEscalofrio";
    public static final String e1DolorMuscular = "e1DolorMuscular";
    public static final String e1DolorArticular = "e1DolorArticular";
    public static final String e1SecresionNasal = "e1SecresionNasal";
    public static final String e1DolorGarganta = "e1DolorGarganta";
    public static final String e1Tos = "e1Tos";
    public static final String e1DificultadResp = "e1DificultadResp";
    public static final String e1DolorPecho = "e1DolorPecho";
    public static final String e1NauseasVomito = "e1NauseasVomito";
    public static final String e1DolorCabeza = "e1DolorCabeza";
    public static final String e1DolorAbdominal = "e1DolorAbdominal";
    public static final String e1Diarrea = "e1Diarrea";
    public static final String e1DificultadDormir = "e1DificultadDormir";
    public static final String e1Debilidad = "e1Debilidad";
    public static final String e1PerdidaOlfatoGusto = "e1PerdidaOlfatoGusto";
    public static final String e1Mareo = "e1Mareo";
    public static final String e1Sarpullido = "e1Sarpullido";
    public static final String e1Desmayo = "e1Desmayo";
    public static final String e1QuedoCama = "e1QuedoCama";
    public static final String e2Febricula = "e2Febricula";
    public static final String e2Fiebre = "e2Fiebre";
    public static final String e2Escalofrio = "e2Escalofrio";
    public static final String e2TemblorEscalofrio = "e2TemblorEscalofrio";
    public static final String e2DolorMuscular = "e2DolorMuscular";
    public static final String e2DolorArticular = "e2DolorArticular";
    public static final String e2SecresionNasal = "e2SecresionNasal";
    public static final String e2DolorGarganta = "e2DolorGarganta";
    public static final String e2Tos = "e2Tos";
    public static final String e2DificultadResp = "e2DificultadResp";
    public static final String e2DolorPecho = "e2DolorPecho";
    public static final String e2NauseasVomito = "e2NauseasVomito";
    public static final String e2DolorCabeza = "e2DolorCabeza";

    public static final String e2DolorAbdominal = "e2DolorAbdominal";
    public static final String e2Diarrea = "e2Diarrea";
    public static final String e2DificultadDormir = "e2DificultadDormir";
    public static final String e2Debilidad = "e2Debilidad";
    public static final String e2PerdidaOlfatoGusto = "e2PerdidaOlfatoGusto";
    public static final String e2Mareo = "e2Mareo";
    public static final String e2Sarpullido = "e2Sarpullido";
    public static final String e2Desmayo = "e2Desmayo";
    public static final String e2QuedoCama = "e2QuedoCama";

    public static final String e3Febricula = "e3Febricula";
    public static final String e3Fiebre = "e3Fiebre";
    public static final String e3Escalofrio = "e3Escalofrio";
    public static final String e3TemblorEscalofrio = "e3TemblorEscalofrio";
    public static final String e3DolorMuscular = "e3DolorMuscular";
    public static final String e3DolorArticular = "e3DolorArticular";
    public static final String e3SecresionNasal = "e3SecresionNasal";
    public static final String e3DolorGarganta = "e3DolorGarganta";
    public static final String e3Tos = "e3Tos";
    public static final String e3DificultadResp = "e3DificultadResp";
    public static final String e3DolorPecho = "e3DolorPecho";
    public static final String e3NauseasVomito = "e3NauseasVomito";
    public static final String e3DolorCabeza = "e3DolorCabeza";
    public static final String e3DolorAbdominal = "e3DolorAbdominal";
    public static final String e3Diarrea = "e3Diarrea";
    public static final String e3DificultadDormir = "e3DificultadDormir";
    public static final String e3Debilidad = "e3Debilidad";
    public static final String e3PerdidaOlfatoGusto = "e3PerdidaOlfatoGusto";
    public static final String e3Mareo = "e3Mareo";
    public static final String e3Sarpullido = "e3Sarpullido";
    public static final String e3Desmayo = "e3Desmayo";
    public static final String e3QuedoCama = "e3QuedoCama";

    public static final String vacunadoCovid19 = "vacunadoCovid19";
    public static final String muestraTarjetaVac = "muestraTarjetaVac";
    public static final String sabeNombreVacuna = "sabeNombreVacuna";
    public static final String nombreVacuna = "nombreVacuna";
    public static final String anioVacuna = "anioVacuna";
    public static final String mesVacuna = "mesVacuna";
    public static final String cuantasDosis = "cuantasDosis";
    public static final String nombreDosis1 = "nombreDosis1";
    public static final String otraVacunaDosis1 = "otraVacunaDosis1";
    public static final String loteDosis1 = "loteDosis1";
    public static final String fechaDosis1 = "fechaDosis1";
    public static final String nombreDosis2 = "nombreDosis2";
    public static final String otraVacunaDosis2 = "otraVacunaDosis2";
    public static final String loteDosis2 = "loteDosis2";
    public static final String fechaDosis2 = "fechaDosis2";
    public static final String nombreDosis3 = "nombreDosis3";
    public static final String otraVacunaDosis3 = "otraVacunaDosis3";
    public static final String loteDosis3 = "loteDosis3";
    public static final String fechaDosis3 = "fechaDosis3";

    public static final String presentoSintomasDosis1 = "presentoSintomasDosis1";
    public static final String dolorSitioDosis1 = "dolorSitioDosis1";
    public static final String fiebreDosis1 = "fiebreDosis1";
    public static final String cansancioDosis1 = "cansancioDosis1";
    public static final String dolorCabezaDosis1 = "dolorCabezaDosis1";
    public static final String diarreaDosis1 = "diarreaDosis1";
    public static final String vomitoDosis1 = "vomitoDosis1";
    public static final String dolorMuscularDosis1 = "dolorMuscularDosis1";
    public static final String escalofriosDosis1 = "escalofriosDosis1";
    public static final String reaccionAlergicaDosis1 = "reaccionAlergicaDosis1";
    public static final String infeccionSitioDosis1 = "infeccionSitioDosis1";
    public static final String nauseasDosis1 = "nauseasDosis1";
    public static final String bultosDosis1 = "bultosDosis1";
    public static final String otrosDosis1 = "otrosDosis1";
    public static final String desOtrosDosis1 = "desOtrosDosis1";
    //dosis2
    public static final String presentoSintomasDosis2 = "presentoSintomasDosis2";
    public static final String dolorSitioDosis2 = "dolorSitioDosis2";
    public static final String fiebreDosis2 = "fiebreDosis2";
    public static final String cansancioDosis2 = "cansancioDosis2";
    public static final String dolorCabezaDosis2 = "dolorCabezaDosis2";
    public static final String diarreaDosis2 = "diarreaDosis2";
    public static final String vomitoDosis2 = "vomitoDosis2";
    public static final String dolorMuscularDosis2 = "dolorMuscularDosis2";
    public static final String escalofriosDosis2 = "escalofriosDosis2";
    public static final String reaccionAlergicaDosis2 = "reaccionAlergicaDosis2";
    public static final String infeccionSitioDosis2 = "infeccionSitioDosis2";
    public static final String nauseasDosis2 = "nauseasDosis2";
    public static final String bultosDosis2 = "bultosDosis2";
    public static final String otrosDosis2 = "otrosDosis2";
    public static final String desOtrosDosis2 = "desOtrosDosis2";
    //dosis3
    public static final String presentoSintomasDosis3 = "presentoSintomasDosis3";
    public static final String dolorSitioDosis3 = "dolorSitioDosis3";
    public static final String fiebreDosis3 = "fiebreDosis3";
    public static final String cansancioDosis3 = "cansancioDosis3";
    public static final String dolorCabezaDosis3 = "dolorCabezaDosis3";
    public static final String diarreaDosis3 = "diarreaDosis3";
    public static final String vomitoDosis3 = "vomitoDosis3";
    public static final String dolorMuscularDosis3 = "dolorMuscularDosis3";
    public static final String escalofriosDosis3 = "escalofriosDosis3";
    public static final String reaccionAlergicaDosis3 = "reaccionAlergicaDosis3";
    public static final String infeccionSitioDosis3 = "infeccionSitioDosis3";
    public static final String nauseasDosis3 = "nauseasDosis3";
    public static final String bultosDosis3 = "bultosDosis3";
    public static final String otrosDosis3 = "otrosDosis3";
    public static final String desOtrosDosis3 = "desOtrosDosis3";

    public static final String covid19PosteriorVacuna = "covid19PosteriorVacuna";
    public static final String sabeFechaEnfPostVac = "sabeFechaEnfPostVac";
    public static final String fechaEventoEnfermoPostVac = "fechaEventoEnfermoPostVac";
    public static final String fechaEnfPostVac = "fechaEnfPostVac";
    public static final String anioEnfPostVac = "anioEnfPostVac";
    public static final String mesEnfPostVac = "mesEnfPostVac";

    //MA 2024
    public static final String dxEnfermoCovid19 = "dxEnfermoCovid19";
    public static final String mesUltEnf = "mesUltEnf";
    public static final String anioUltEnf = "anioUltEnf";
    public static final String sabeFechaUltEnf = "sabeFechaUltEnf";


    //Crear SintomasVisitaFinalCovid19
    public static final String CREATE_COVID_CUESTIONARIO_TABLE = "create table if not exists "
            + COVID_CUESTIONARIO_TABLE + " ("
            + codigo + " text not null, "
            + participante + " integer not null, "
            /*+ feb20Febricula + " text, "
            + feb20Fiebre + " text, "
            + feb20Escalofrio + " text, "
            + feb20TemblorEscalofrio + " text, "
            + feb20DolorMuscular + " text, "
            + feb20DolorArticular + " text, "
            + feb20SecresionNasal + " text, "
            + feb20DolorGarganta + " text, "
            + feb20Tos + " text, "
            + feb20DificultadResp + " text, "
            + feb20DolorPecho + " text, "
            + feb20NauseasVomito + " text, "
            + feb20DolorCabeza + " text, "
            + feb20DolorAbdominal + " text, "
            + feb20Diarrea + " text, "
            + feb20DificultadDormir + " text, "
            + feb20Debilidad + " text, "
            + feb20PerdidaOlfatoGusto + " text, "
            + feb20Mareo + " text, "
            + feb20Sarpullido + " text, "
            + feb20Desmayo + " text, "
            + feb20QuedoCama + " text, "
            + padecidoCovid19 + " text, "*/
            + e1SabeFIS + " text, "
            + e1FIS + " date, "
            + e1MesInicioSintoma + " text, "
            + e1AnioInicioSintoma + " text, "
            + e1ConoceLugarExposicion + " text, "
            + e1LugarExposicion + " text, "
            + e1BuscoAyuda + " text, "
            + e1DondeBuscoAyuda + " text, "
            + e1NombreCentroSalud + " text, "
            + e1NombreHospital + " text, "
            + e1RecibioSeguimiento + " text, "
            + e1TipoSeguimiento + " text, "
            + e1TmpDespuesBuscoAyuda + " text, "
            + e1UnaNocheHospital + " text, "
            + e1QueHospital + " text, "
            + e1SabeCuantasNoches + " text, "
            + e1CuantasNochesHosp + " integer, "
            + e1SabeFechaAdmision + " text, "
            + e1FechaAdmisionHosp + " date, "
            + e1SabeFechaAlta + " text, "
            + e1FechaAltaHosp + " date, "
            + e1UtilizoOxigeno + " text, "
            + e1EstuvoUCI + " text, "
            + e1FueIntubado + " text, "
            + e1RecuperadoCovid19 + " text, "
            + e1TenidoFebricula + " text, "
            + e1TenidoCansancio + " text, "
            + e1TenidoDolorCabeza + " text, "
            + e1TenidoPerdidaOlfato + " text, "
            + e1TenidoPerdidaGusto + " text, "
            + e1TenidoTos + " text, "
            + e1TenidoDificultadRespirar + " text, "
            + e1TenidoDolorPecho + " text, "
            + e1TenidoPalpitaciones + " text, "
            + e1TenidoDolorArticulaciones + " text, "
            + e1TenidoParalisis + " text, "
            + e1TenidoMareos + " text, "
            + e1TenidoPensamientoNublado + " text, "
            + e1TenidoProblemasDormir + " text, "
            + e1TenidoDepresion + " text, "
            + e1TenidoOtrosSintomas + " text, "
            + e1CualesSintomas + " text, "
            + e1SabeTiempoRecuperacion + " text, "
            + e1TiempoRecuperacion + " text, "
            + e1TiempoRecuperacionEn + " text, "
            + e1SeveridadEnfermedad + " text, "
            + e1TomoMedicamento + " text, "
            + e1QueMedicamento + " text, "
            + e1OtroMedicamento + " text, "
            + e1OxigenoDomicilio + " text, "
            + e1TiempoOxigenoDom + " text, "

            + e2SabeFIS + " text, "
            + e2FIS + " date, "
            + e2MesInicioSintoma + " text, "
            + e2AnioInicioSintoma + " text, "
            + e2ConoceLugarExposicion + " text, "
            + e2LugarExposicion + " text, "
            + e2BuscoAyuda + " text, "
            + e2DondeBuscoAyuda + " text, "
            + e2NombreCentroSalud + " text, "
            + e2NombreHospital + " text, "
            + e2RecibioSeguimiento + " text, "
            + e2TipoSeguimiento + " text, "
            + e2TmpDespuesBuscoAyuda + " text, "
            + e2UnaNocheHospital + " text, "
            + e2QueHospital + " text, "
            + e2SabeCuantasNoches + " text, "
            + e2CuantasNochesHosp + " integer, "
            + e2SabeFechaAdmision + " text, "
            + e2FechaAdmisionHosp + " date, "
            + e2SabeFechaAlta + " text, "
            + e2FechaAltaHosp + " date, "
            + e2UtilizoOxigeno + " text, "
            + e2EstuvoUCI + " text, "
            + e2FueIntubado + " text, "
            + e2RecuperadoCovid19 + " text, "
            + e2TenidoFebricula + " text, "
            + e2TenidoCansancio + " text, "
            + e2TenidoDolorCabeza + " text, "
            + e2TenidoPerdidaOlfato + " text, "
            + e2TenidoPerdidaGusto + " text, "
            + e2TenidoTos + " text, "
            + e2TenidoDificultadRespirar + " text, "
            + e2TenidoDolorPecho + " text, "
            + e2TenidoPalpitaciones + " text, "
            + e2TenidoDolorArticulaciones + " text, "
            + e2TenidoParalisis + " text, "
            + e2TenidoMareos + " text, "
            + e2TenidoPensamientoNublado + " text, "
            + e2TenidoProblemasDormir + " text, "
            + e2TenidoDepresion + " text, "
            + e2TenidoOtrosSintomas + " text, "
            + e2CualesSintomas + " text, "
            + e2SabeTiempoRecuperacion + " text, "
            + e2TiempoRecuperacion + " text, "
            + e2TiempoRecuperacionEn + " text, "
            + e2SeveridadEnfermedad + " text, "
            + e2TomoMedicamento + " text, "
            + e2QueMedicamento + " text, "
            + e2OtroMedicamento + " text, "
            + e2OxigenoDomicilio + " text, "
            + e2TiempoOxigenoDom + " text, "

            + e3SabeFIS + " text, "
            + e3FIS + " date, "
            + e3MesInicioSintoma + " text, "
            + e3AnioInicioSintoma + " text, "
            + e3ConoceLugarExposicion + " text, "
            + e3LugarExposicion + " text, "
            + e3BuscoAyuda + " text, "
            + e3DondeBuscoAyuda + " text, "
            + e3NombreCentroSalud + " text, "
            + e3NombreHospital + " text, "
            + e3RecibioSeguimiento + " text, "
            + e3TipoSeguimiento + " text, "
            + e3TmpDespuesBuscoAyuda + " text, "
            + e3UnaNocheHospital + " text, "
            + e3QueHospital + " text, "
            + e3SabeCuantasNoches + " text, "
            + e3CuantasNochesHosp + " integer, "
            + e3SabeFechaAdmision + " text, "
            + e3FechaAdmisionHosp + " date, "
            + e3SabeFechaAlta + " text, "
            + e3FechaAltaHosp + " date, "
            + e3UtilizoOxigeno + " text, "
            + e3EstuvoUCI + " text, "
            + e3FueIntubado + " text, "
            + e3RecuperadoCovid19 + " text, "
            + e3TenidoFebricula + " text, "
            + e3TenidoCansancio + " text, "
            + e3TenidoDolorCabeza + " text, "
            + e3TenidoPerdidaOlfato + " text, "
            + e3TenidoPerdidaGusto + " text, "
            + e3TenidoTos + " text, "
            + e3TenidoDificultadRespirar + " text, "
            + e3TenidoDolorPecho + " text, "
            + e3TenidoPalpitaciones + " text, "
            + e3TenidoDolorArticulaciones + " text, "
            + e3TenidoParalisis + " text, "
            + e3TenidoMareos + " text, "
            + e3TenidoPensamientoNublado + " text, "
            + e3TenidoProblemasDormir + " text, "
            + e3TenidoDepresion + " text, "
            + e3TenidoOtrosSintomas + " text, "
            + e3CualesSintomas + " text, "
            + e3SabeTiempoRecuperacion + " text, "
            + e3TiempoRecuperacion + " text, "
            + e3TiempoRecuperacionEn + " text, "
            + e3SeveridadEnfermedad + " text, "
            + e3TomoMedicamento + " text, "
            + e3QueMedicamento + " text, "
            + e3OtroMedicamento + " text, "
            + e3OxigenoDomicilio + " text, "
            + e3TiempoOxigenoDom + " text, "

            + padeceEnfisema + " text, "
            + padeceAsma + " text, "
            + padeceDiabetes + " text, "
            + padeceEnfCoronaria + " text, "
            + padecePresionAlta + " text, "
            + padeceEnfHigado + " text, "
            + padeceEnfRenal + " text, "
            + padeceInfartoDerrameCer + " text, "
            + padeceCancer + " text, "
            + padeceCondicionInmuno + " text, "
            + padeceEnfAutoinmune + " text, "
            + padeceDiscapacidadFis + " text, "
            + padeceCondPsicPsiq + " text, "
            + padeceOtraCondicion + " text, "
            + queOtraCondicion + " text, "
            + fumado + " text, "
            + fumadoCienCigarrillos + " text, "

            + e1FumadoPrevioEnfermedad + " text, "
            + e1FumaActualmente + " text, "
            + e1Embarazada + " text, "
            + e1RecuerdaSemanasEmb + " text, "
            + e1SemanasEmbarazo + " integer, "
            + e1FinalEmbarazo + " text, "
            + e1OtroFinalEmbarazo + " text, "
            + e1DabaPecho + " text, "
            + e2FumadoPrevioEnfermedad + " text, "
            + e2FumaActualmente + " text, "
            + e2Embarazada + " text, "
            + e2RecuerdaSemanasEmb + " text, "
            + e2SemanasEmbarazo + " integer, "
            + e2FinalEmbarazo + " text, "
            + e2OtroFinalEmbarazo + " text, "
            + e2DabaPecho + " text, "
            + e3FumadoPrevioEnfermedad + " text, "
            + e3FumaActualmente + " text, "
            + e3Embarazada + " text, "
            + e3RecuerdaSemanasEmb + " text, "
            + e3SemanasEmbarazo + " integer, "
            + e3FinalEmbarazo + " text, "
            + e3OtroFinalEmbarazo + " text, "
            + e3DabaPecho + " text, "

            + trabajadorSalud + " text, "
            + periodoSintomas + " text, "
            + enfermoCovid19 + " text, "
            + cuantasVecesEnfermo + " text, "
            + sabeEvento1 + " text, "
            + evento1 + " date, "
            + anioEvento1 + " text, "
            + mesEvento1 + " text, "
            + sabeEvento2 + " text, "
            + evento2 + " date, "
            + anioEvento2 + " text, "
            + mesEvento2 + " text, "
            + sabeEvento3 + " text, "
            + evento3 + " date, "
            + anioEvento3 + " text, "
            + mesEvento3 + " text, "
            + e1Febricula + " text, "
            + e1Fiebre + " text, "
            + e1Escalofrio + " text, "
            + e1TemblorEscalofrio + " text, "
            + e1DolorMuscular + " text, "
            + e1DolorArticular + " text, "
            + e1SecresionNasal + " text, "
            + e1DolorGarganta + " text, "
            + e1Tos + " text, "
            + e1DificultadResp + " text, "
            + e1DolorPecho + " text, "
            + e1NauseasVomito + " text, "
            + e1DolorCabeza + " text, "
            + e1DolorAbdominal + " text, "
            + e1Diarrea + " text, "
            + e1DificultadDormir + " text, "
            + e1Debilidad + " text, "
            + e1PerdidaOlfatoGusto + " text, "
            + e1Mareo + " text, "
            + e1Sarpullido + " text, "
            + e1Desmayo + " text, "
            + e1QuedoCama + " text, "
            + e2Febricula + " text, "
            + e2Fiebre + " text, "
            + e2Escalofrio + " text, "
            + e2TemblorEscalofrio + " text, "
            + e2DolorMuscular + " text, "
            + e2DolorArticular + " text, "
            + e2SecresionNasal + " text, "
            + e2DolorGarganta + " text, "
            + e2Tos + " text, "
            + e2DificultadResp + " text, "
            + e2DolorPecho + " text, "
            + e2NauseasVomito + " text, "
            + e2DolorCabeza + " text, "
            + e2DolorAbdominal + " text, "
            + e2Diarrea + " text, "
            + e2DificultadDormir + " text, "
            + e2Debilidad + " text, "
            + e2PerdidaOlfatoGusto + " text, "
            + e2Mareo + " text, "
            + e2Sarpullido + " text, "
            + e2Desmayo + " text, "
            + e2QuedoCama + " text, "
            + e3Febricula + " text, "
            + e3Fiebre + " text, "
            + e3Escalofrio + " text, "
            + e3TemblorEscalofrio + " text, "
            + e3DolorMuscular + " text, "
            + e3DolorArticular + " text, "
            + e3SecresionNasal + " text, "
            + e3DolorGarganta + " text, "
            + e3Tos + " text, "
            + e3DificultadResp + " text, "
            + e3DolorPecho + " text, "
            + e3NauseasVomito + " text, "
            + e3DolorCabeza + " text, "
            + e3DolorAbdominal + " text, "
            + e3Diarrea + " text, "
            + e3DificultadDormir + " text, "
            + e3Debilidad + " text, "
            + e3PerdidaOlfatoGusto + " text, "
            + e3Mareo + " text, "
            + e3Sarpullido + " text, "
            + e3Desmayo + " text, "
            + e3QuedoCama + " text, "

            + vacunadoCovid19 + " text, "
            + muestraTarjetaVac + " text, "
            + sabeNombreVacuna + " text, "
            + nombreVacuna + " text, "
            + anioVacuna + " text, "
            + mesVacuna + " text, "
            + cuantasDosis + " text, "
            + nombreDosis1 + " text, "
            + otraVacunaDosis1 + " text, "
            + loteDosis1 + " text, "
            + fechaDosis1 + " date, "
            + nombreDosis2 + " text, "
            + otraVacunaDosis2 + " text, "
            + loteDosis2 + " text, "
            + fechaDosis2 + " date, "
            + nombreDosis3 + " text, "
            + otraVacunaDosis3 + " text, "
            + loteDosis3 + " text, "
            + fechaDosis3 + " date, "

            + presentoSintomasDosis1 + " text, "
            + dolorSitioDosis1 + " text, "
            + fiebreDosis1 + " text, "
            + cansancioDosis1 + " text, "
            + dolorCabezaDosis1 + " text, "
            + diarreaDosis1 + " text, "
            + vomitoDosis1 + " text, "
            + dolorMuscularDosis1 + " text, "
            + escalofriosDosis1 + " text, "
            + reaccionAlergicaDosis1 + " text, "
            + infeccionSitioDosis1 + " text, "
            + nauseasDosis1 + " text, "
            + bultosDosis1 + " text, "
            + otrosDosis1 + " text, "
            + desOtrosDosis1 + " text, "

            + presentoSintomasDosis2 + " text, "
            + dolorSitioDosis2 + " text, "
            + fiebreDosis2 + " text, "
            + cansancioDosis2 + " text, "
            + dolorCabezaDosis2 + " text, "
            + diarreaDosis2 + " text, "
            + vomitoDosis2 + " text, "
            + dolorMuscularDosis2 + " text, "
            + escalofriosDosis2 + " text, "
            + reaccionAlergicaDosis2 + " text, "
            + infeccionSitioDosis2 + " text, "
            + nauseasDosis2 + " text, "
            + bultosDosis2 + " text, "
            + otrosDosis2 + " text, "
            + desOtrosDosis2 + " text, "

            + presentoSintomasDosis3 + " text, "
            + dolorSitioDosis3 + " text, "
            + fiebreDosis3 + " text, "
            + cansancioDosis3 + " text, "
            + dolorCabezaDosis3 + " text, "
            + diarreaDosis3 + " text, "
            + vomitoDosis3 + " text, "
            + dolorMuscularDosis3 + " text, "
            + escalofriosDosis3 + " text, "
            + reaccionAlergicaDosis3 + " text, "
            + infeccionSitioDosis3 + " text, "
            + nauseasDosis3 + " text, "
            + bultosDosis3 + " text, "
            + otrosDosis3 + " text, "
            + desOtrosDosis3 + " text, "
            + covid19PosteriorVacuna + " text, "
            + sabeFechaEnfPostVac + " text, "
            + fechaEventoEnfermoPostVac + " text, "
            + fechaEnfPostVac + " date, "
            + anioEnfPostVac + " text, "
            + mesEnfPostVac + " text, "

            + dxEnfermoCovid19 + " text, "
            + sabeFechaUltEnf + " text, "
            + mesUltEnf + " text, "
            + anioUltEnf + " text, "

            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigo + "));";

    /*BULK INSERT*/
    public static final String INSERT_COVID_CASOS_TABLE = "insert into "
            + COVID_CASOS_TABLE + " ("
            + codigoCaso + ","
            + casa + "  , "
            + inactivo + ","
            + fechaIngreso + ","
            + fechaInactivo + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?)"; //10

    public static final String INSERT_COVID_PARTICIPANTES_CASOS_TABLE = "insert into "
            + COVID_PARTICIPANTES_CASOS_TABLE + " ("
            + codigoCasoParticipante + ","
            + codigoCaso + ","
            + participante + ","
            + enfermo + ","
            + positivoPor + ","
            + fif + ","
            + fis + ","
            + consentimiento + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?)";


    public static final String INSERT_COVID_VISITAS_CASOS_TABLE = "insert into "
            + COVID_VISITAS_CASOS_TABLE + " ("
            + codigoCasoVisita + ","
            + codigoCasoParticipante + ","
            + fechaVisita + ","
            + visita + ","
            + horaProbableVisita + ","
            + expCS + ","
            + temp + ","
            + saturacionO2 + ","
            + frecResp + ","
            + fecIniPrimerSintoma + ","
            + primerSintoma + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    public static final String INSERT_COVID_VISITAS_FALLIDAS_CASOS_TABLE = "insert into "
            + COVID_VISITAS_FALLIDAS_CASOS_TABLE + " ("
            + codigoFallaVisita + ","
            + codigoParticipanteCaso + ","
            + fechaVisita + ","
            + razonVisitaFallida + ","
            + otraRazon + ","
            + visita + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_COVID_CANDIDATO_TRANSMISION_TABLE = "insert into "
            + COVID_CANDIDATO_TRANSMISION_TABLE + " ("
            + codigo + ","
            + participante + ","
            + casaCHF + ","
            + fis + ","
            + fif + ","
            + positivoPor + ","
            + consentimiento + ","
            + estActuales + ","
            + fechaIngreso + ","
            + indice + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_COVID_SINTOMAS_VISITA_CASO_TABLE = "insert into "
            + COVID_SINTOMAS_VISITA_CASO_TABLE + " ("
            +  codigoCasoSintoma + ","
            +  codigoCasoVisita + ","
            +  fechaSintomas + ","
            +  fiebre + ","
            +  fiebreIntensidad + ","
            +  fif + ","
            +  fiebreCuantificada + ","
            +  valorFiebreCuantificada + ","
            +  dolorCabeza + ","
            +  dolorCabezaIntensidad + ","
            +  dolorArticular + ","
            +  dolorArticularIntensidad + ","
            +  dolorMuscular + ","
            +  dolorMuscularIntensidad + ","
            +  dificultadRespiratoria + ","
            +  secrecionNasal + ","
            +  secrecionNasalIntensidad + ","
            +  tos + ","
            +  tosIntensidad + ","
            +  pocoApetito + ","
            +  dolorGarganta + ","
            +  dolorGargantaIntensidad + ","
            +  picorGarganta + ","
            +  congestionNasal + ","
            +  rash + ","
            +  urticaria + ","
            +  conjuntivitis + ","
            +  expectoracion + ","
            +  diarrea + ","
            +  vomito + ","
            +  fatiga + ","
            +  respiracionRuidosa + ","
            +  respiracionRapida + ","
            +  perdidaOlfato + ","
            +  perdidaGusto + ","
            +  desmayos + ","
            +  sensacionPechoApretado + ","
            +  dolorPecho + ","
            +  sensacionFaltaAire + ","
            +  quedoCama + ","
            +  tratamiento + ","
            +  fechaInicioTx + ","
            +  cualMedicamento + ","
            +  otroMedicamento + ","
            +  antibiotico + ","
            +  prescritoMedico + ","
            +  factorRiesgo + ","
            +  otroFactorRiesgo + ","
            +  otraPersonaEnferma + ","
            +  cuantasPersonas + ","
            +  trasladoHospital + ","
            +  cualHospital + ","
            +  hospitalizado + ","
            +  fechaAlta + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_COVID_DATOS_AISLAMIENTO_VC_TABLE = "insert into "
            + COVID_DATOS_AISLAMIENTO_VC_TABLE + " ("
            + codigoAislamiento + ","
            + codigoCasoVisita + ","
            + fechaDato + ","
            + aislado + ","
            + dormirSoloComparte + ","
            + banioPropioComparte + ","
            + alejadoFamilia + ","
            + tieneContacto + ","
            + conQuienTieneContacto + ","
            + lugares + ","
            + otrosLugares + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_COVID_VISITA_FINAL_CASO_TABLE = "insert into "
            + COVID_VISITA_FINAL_CASO_TABLE + " ("
            +  codigoVisitaFinal + ","
            +  codigoParticipanteCaso + ","
            +  fechaVisita + ","
            +  enfermo + ","
            +  consTerreno + ","
            +  referidoCS + ","
            +  tratamiento + ","
            +  queAntibiotico + ","
            +  otroMedicamento + ","
            +  sintResp + ","
            +  fueHospitalizado + ","
            +  fechaIngreso + ","
            +  fechaEgreso + ","
            +  diasHospitalizado + ","
            +  cualHospital + ","
            +  utilizoOxigeno + ","
            +  fueIntubado + ","
            +  estadoSalud + ","
            +  faltoTrabajoEscuela + ","
            +  diasFaltoTrabajoEscuela + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_COVID_SINT_VISITA_FINAL_CASO_TABLE = "insert into "
            + COVID_SINT_VISITA_FINAL_CASO_TABLE + " ("
            + codigoCasoSintoma + ","
            + codigoVisitaFinal + ","
            + fiebre + ","
            + tos + ","
            + dolorCabeza + ","
            + dolorArticular + ","
            + dolorMuscular + ","
            + dificultadRespiratoria + ","
            + pocoApetito + ","
            + dolorGarganta + ","
            + secrecionNasal + ","
            + picorGarganta + ","
            + expectoracion + ","
            + rash + ","
            + urticaria + ","
            + conjuntivitis + ","
            + diarrea + ","
            + vomito + ","
            + quedoCama + ","
            + respiracionRuidosa + ","
            + respiracionRapida + ","
            + perdidaOlfato + ","
            + congestionNasal + ","
            + perdidaGusto + ","
            + desmayos + ","
            + sensacionPechoApretado + ","
            + dolorPecho + ","
            + sensacionFaltaAire + ","
            + fatiga + ","
            + fiebreFecIni + ","
            + tosFecIni + ","
            + dolorCabezaFecIni + ","
            + dolorArticularFecIni + ","
            + dolorMuscularFecIni + ","
            + dificultadRespiratoriaFecIni + ","
            + pocoApetitoFecIni + ","
            + dolorGargantaFecIni + ","
            + secrecionNasalFecIni + ","
            + picorGargantaFecIni + ","
            + expectoracionFecIni + ","
            + rashFecIni + ","
            + urticariaFecIni + ","
            + conjuntivitisFecIni + ","
            + diarreaFecIni + ","
            + vomitoFecIni + ","
            + quedoCamaFecIni + ","
            + respiracionRuidosaFecIni + ","
            + respiracionRapidaFecIni + ","
            + perdidaOlfatoFecIni + ","
            + congestionNasalFecIni + ","
            + perdidaGustoFecIni + ","
            + desmayosFecIni + ","
            + sensacionPechoApretadoFecIni + ","
            + dolorPechoFecIni + ","
            + sensacionFaltaAireFecIni + ","
            + fatigaFecIni + ","
            + fiebreFecFin + ","
            + tosFecFin + ","
            + dolorCabezaFecFin + ","
            + dolorArticularFecFin + ","
            + dolorMuscularFecFin + ","
            + dificultadRespiratoriaFecFin + ","
            + pocoApetitoFecFin + ","
            + dolorGargantaFecFin + ","
            + secrecionNasalFecFin + ","
            + picorGargantaFecFin + ","
            + expectoracionFecFin + ","
            + rashFecFin + ","
            + urticariaFecFin + ","
            + conjuntivitisFecFin + ","
            + diarreaFecFin + ","
            + vomitoFecFin + ","
            + quedoCamaFecFin + ","
            + respiracionRuidosaFecFin + ","
            + respiracionRapidaFecFin + ","
            + perdidaOlfatoFecFin + ","
            + congestionNasalFecFin + ","
            + perdidaGustoFecFin + ","
            + desmayosFecFin + ","
            + sensacionPechoApretadoFecFin + ","
            + dolorPechoFecFin + ","
            + sensacionFaltaAireFecFin + ","
            + fatigaFecFin + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_COVID_CUESTIONARIO_TABLE = "insert into "
            + COVID_CUESTIONARIO_TABLE + " ("
            + codigo + ","
            + participante + ","
            + feb20Febricula + ","
            + feb20Fiebre + ","
            + feb20Escalofrio + ","
            + feb20TemblorEscalofrio + ","
            + feb20DolorMuscular + ","
            + feb20DolorArticular + ","
            + feb20SecresionNasal + ","
            + feb20DolorGarganta + ","
            + feb20Tos + ","
            + feb20DificultadResp + ","
            + feb20DolorPecho + ","
            + feb20NauseasVomito + ","
            + feb20DolorCabeza + ","
            + feb20DolorAbdominal + ","
            + feb20Diarrea + ","
            + feb20DificultadDormir + ","
            + feb20Debilidad + ","
            + feb20PerdidaOlfatoGusto + ","
            + feb20Mareo + ","
            + feb20Sarpullido + ","
            + feb20Desmayo + ","
            + feb20QuedoCama + ","
            + e1SabeFIS + ","
            + fis + ","
            + e1MesInicioSintoma + ","
            + e1AnioInicioSintoma + ","
            + padecidoCovid19 + ","
            + e1ConoceLugarExposicion + ","
            + e1LugarExposicion + ","
            + e1BuscoAyuda + ","
            + e1DondeBuscoAyuda + ","
            + e1NombreCentroSalud + ","
            + e1NombreHospital + ","
            + e1RecibioSeguimiento + ","
            + e1TipoSeguimiento + ","
            + e1TmpDespuesBuscoAyuda + ","
            + e1UnaNocheHospital + ","
            + e1QueHospital + ","
            + e1SabeCuantasNoches + ","
            + e1CuantasNochesHosp + ","
            + e1SabeFechaAdmision + ","
            + e1FechaAdmisionHosp + ","
            + e1SabeFechaAlta + ","
            + e1FechaAltaHosp + ","
            + utilizoOxigeno + ","
            + e1EstuvoUCI + ","
            + fueIntubado + ","
            + e1RecuperadoCovid19 + ","
            + e1TenidoFebricula + ","
            + e1TenidoCansancio + ","
            + e1TenidoDolorCabeza + ","
            + e1TenidoPerdidaOlfato + ","
            + e1TenidoPerdidaGusto + ","
            + e1TenidoTos + ","
            + e1TenidoDificultadRespirar + ","
            + e1TenidoDolorPecho + ","
            + e1TenidoPalpitaciones + ","
            + e1TenidoDolorArticulaciones + ","
            + e1TenidoParalisis + ","
            + e1TenidoMareos + ","
            + e1TenidoPensamientoNublado + ","
            + e1TenidoProblemasDormir + ","
            + e1TenidoDepresion + ","
            + e1TenidoOtrosSintomas + ","
            + e1CualesSintomas + ","
            + e1SabeTiempoRecuperacion + ","
            + e1TiempoRecuperacion + ","
            + e1TiempoRecuperacionEn + ","
            + e1SeveridadEnfermedad + ","
            + e1TomoMedicamento + ","
            + e1QueMedicamento + ","
            + e1OtroMedicamento + ","
            + padeceEnfisema + ","
            + padeceAsma + ","
            + padeceDiabetes + ","
            + padeceEnfCoronaria + ","
            + padecePresionAlta + ","
            + padeceEnfHigado + ","
            + padeceEnfRenal + ","
            + padeceInfartoDerrameCer + ","
            + padeceCancer + ","
            + padeceCondicionInmuno + ","
            + padeceEnfAutoinmune + ","
            + padeceDiscapacidadFis + ","
            + padeceCondPsicPsiq + ","
            + padeceOtraCondicion + ","
            + queOtraCondicion + ","
            + fumado + ","
            + fumadoCienCigarrillos + ","
            + e1FumadoPrevioEnfermedad + ","
            + e1FumaActualmente + ","
            + e1Embarazada + ","
            + e1RecuerdaSemanasEmb + ","
            + e1SemanasEmbarazo + ","
            + e1FinalEmbarazo + ","
            + e1OtroFinalEmbarazo + ","
            + e1DabaPecho + ","
            + trabajadorSalud + ","
            + periodoSintomas + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_PARTICIPANTE_COVID_TABLE = "insert into "
            + PARTICIPANTE_COVID_TABLE + " ("
            + participante + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?)";

    //Tabla OtrosPositivosCovid
    public static final String COVID_OTROS_POSITIVOS_TABLE = "covid_otros_positivos";

    //Campos OtrosPositivosCovid
    public static final String codigoCandidato = "codigo_candidato";

    //Crear OtrosPositivosCovid
    public static final String CREATE_COVID_OTROS_POSITIVOS_TABLE = "create table if not exists "
            + COVID_OTROS_POSITIVOS_TABLE + " ("
            + codigo + " integer not null, "
            + codigoCandidato + " text not null, "
            + participante + " integer not null, "
            + casaCHF + " text, "
            + fis + " date, "
            + fif + " date, "
            + positivoPor + " text, "
            + estActuales + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigo + "));";

    //Bulk OtrosPositivosCovid
    public static final String INSERT_CCOVID_OTROS_POSITIVOS_TABLE = "insert into "
            + COVID_OTROS_POSITIVOS_TABLE + " ("
            + codigo + ","
            + codigoCandidato + ","
            + participante + ","
            + casaCHF + ","
            + fis + ","
            + fif + ","
            + positivoPor + ","
            + estActuales + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

}
