package ni.org.ics.estudios.appmovil.utils;

import ni.org.ics.estudios.appmovil.domain.Participante;
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

}
