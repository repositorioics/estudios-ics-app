package ni.org.ics.estudios.appmovil.utils;

import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaSeguimientoCasoCovid19;

import java.util.Date;

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
    public static final String sabeFIS = "sabeFIS";
    public static final String mesInicioSintoma = "mesInicioSintoma";
    public static final String anioInicioSintoma = "anioInicioSintoma";
    public static final String padecidoCovid19 = "padecidoCovid19";
    public static final String conoceLugarExposicion = "conoceLugarExposicion";
    public static final String lugarExposicion = "lugarExposicion";
    public static final String buscoAyuda = "buscoAyuda";
    public static final String dondeBuscoAyuda = "dondeBuscoAyuda";
    public static final String nombreCentroSalud = "nombreCentroSalud";
    public static final String nombreHospital = "nombreHospital";
    public static final String recibioSeguimiento = "recibioSeguimiento";
    public static final String tipoSeguimiento = "tipoSeguimiento";
    public static final String tmpDespuesBuscoAyuda = "tmpDespuesBuscoAyuda";
    public static final String unaNocheHospital = "unaNocheHospital";
    public static final String queHospital = "queHospital";
    public static final String sabeCuantasNoches = "sabeCuantasNoches";
    public static final String cuantasNochesHosp = "cuantasNochesHosp";
    public static final String sabeFechaAdmision = "sabeFechaAdmision";
    public static final String fechaAdmisionHosp = "fechaAdmisionHosp";
    public static final String sabeFechaAlta = "sabeFechaAlta";
    public static final String fechaAltaHosp = "fechaAltaHosp";
    public static final String estuvoUCI = "estuvoUCI";
    public static final String recuperadoCovid19 = "recuperadoCovid19";
    public static final String febricula = "febricula";
    public static final String cansancio = "cansancio";
    public static final String dificultadRespirar = "dificultadRespirar";
    public static final String palpitaciones = "palpitaciones";
    public static final String dolorArticulaciones = "dolorArticulaciones";
    public static final String paralisis = "paralisis";
    public static final String mareos = "mareos";
    public static final String pensamientoNublado = "pensamientoNublado";
    public static final String problemasDormir = "problemasDormir";
    public static final String depresion = "depresion";
    public static final String otrosSintomas = "otrosSintomas";
    public static final String cualesSintomas = "cualesSintomas";
    public static final String sabeTiempoRecuperacion= "sabeTiempoRecuperacion";
    public static final String tiempoRecuperacion = "tiempoRecuperacion";
    public static final String tiempoRecuperacionEn = "tiempoRecuperacionEn";
    public static final String severidadEnfermedad = "severidadEnfermedad";
    public static final String tomoMedicamento = "tomoMedicamento";
    public static final String queMedicamento = "queMedicamento";
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
    public static final String fumadoPrevioEnfermedad = "fumadoPrevioEnfermedad";
    public static final String fumaActualmente = "fumaActualmente";
    //MA2021
    public static final String embarazada = "embarazada";
    public static final String recuerdaSemanasEmb = "recuerdaSemanasEmb";
    public static final String semanasEmbarazo = "semanasEmbarazo";
    public static final String finalEmbarazo = "finalEmbarazo";
    public static final String otroFinalEmbarazo = "otroFinalEmbarazo";
    public static final String dabaPecho = "dabaPecho";
    public static final String trabajadorSalud = "trabajadorSalud";

    //Crear SintomasVisitaFinalCovid19
    public static final String CREATE_COVID_CUESTIONARIO_TABLE = "create table if not exists "
            + COVID_CUESTIONARIO_TABLE + " ("
            + codigo + " text not null, "
            + participante + " integer not null, "
            + feb20Febricula + " text, "
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
            + sabeFIS + " text, "
            + fis + " date, "
            + mesInicioSintoma + " text, "
            + anioInicioSintoma + " text, "
            + padecidoCovid19 + " text, "
            + conoceLugarExposicion + " text, "
            + lugarExposicion + " text, "
            + buscoAyuda + " text, "
            + dondeBuscoAyuda + " text, "
            + nombreCentroSalud + " text, "
            + nombreHospital + " text, "
            + recibioSeguimiento + " text, "
            + tipoSeguimiento + " text, "
            + tmpDespuesBuscoAyuda + " text, "
            + unaNocheHospital + " text, "
            + queHospital + " text, "
            + sabeCuantasNoches + " text, "
            + cuantasNochesHosp + " integer, "
            + sabeFechaAdmision + " text, "
            + fechaAdmisionHosp + " date, "
            + sabeFechaAlta + " text, "
            + fechaAltaHosp + " date, "
            + utilizoOxigeno + " text, "
            + estuvoUCI + " text, "
            + fueIntubado + " text, "
            + recuperadoCovid19 + " text, "
            + febricula + " text, "
            + cansancio + " text, "
            + dolorCabeza + " text, "
            + perdidaOlfato + " text, "
            + perdidaGusto + " text, "
            + tos + " text, "
            + dificultadRespirar + " text, "
            + dolorPecho + " text, "
            + palpitaciones + " text, "
            + dolorArticulaciones + " text, "
            + paralisis + " text, "
            + mareos + " text, "
            + pensamientoNublado + " text, "
            + problemasDormir + " text, "
            + depresion + " text, "
            + otrosSintomas + " text, "
            + cualesSintomas + " text, "
            + sabeTiempoRecuperacion + " text, "
            + tiempoRecuperacion + " text, "
            + tiempoRecuperacionEn + " text, "
            + severidadEnfermedad + " text, "
            + tomoMedicamento + " text, "
            + queMedicamento + " text, "
            + otroMedicamento + " text, "
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
            + fumadoPrevioEnfermedad + " text, "
            + fumaActualmente + " text, "
            + embarazada + " text, "
            + recuerdaSemanasEmb + " text, "
            + semanasEmbarazo + " integer, "
            + finalEmbarazo + " text, "
            + otroFinalEmbarazo + " text, "
            + dabaPecho + " text, "
            + trabajadorSalud + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigo + "));";

}
