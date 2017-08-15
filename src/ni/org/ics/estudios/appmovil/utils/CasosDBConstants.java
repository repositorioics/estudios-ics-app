package ni.org.ics.estudios.appmovil.utils;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class CasosDBConstants {

    //tabla CasaCohorteFamiliaCaso
    public static final String CASAS_CASOS_TABLE = "chf_casas_casos";
    //campos tabla CasaCohorteFamiliaCaso
    public static final String codigoCaso = "codigoCaso";
    public static final String casa = "casa";
    public static final String fechaInicio = "fechaInicio";
    public static final String inactiva = "inactiva";
    public static final String fechaInactiva = "fechaInactiva";

    //crear tabla Muestras
    public static final String CREATE_CASAS_CASOS_TABLE = "create table if not exists "
            + CASAS_CASOS_TABLE + " ("
            + codigoCaso + " text not null, "
            + casa + " text not null, "
            + fechaInicio + " date, "
            + inactiva + " text, "
            + fechaInactiva + " date, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCaso + "));";

    //Tabla ParticipanteCohorteFamiliaCaso
    public static final String PARTICIPANTES_CASOS_TABLE = "chf_participantes_casos";

    //Campos ParticipanteCohorteFamiliaCaso
    public static final String codigoCasoParticipante = "codigoCasoParticipante";
    public static final String participante = "participante";
    public static final String enfermo = "enfermo";
    public static final String fechaEnfermedad = "fechaEnfermedad";

    //Crear ParticipanteCohorteFamiliaCaso
    public static final String CREATE_PARTICIPANTES_CASOS_TABLE = "create table if not exists "
            + PARTICIPANTES_CASOS_TABLE + " ("
            + codigoCasoParticipante + " text not null, "
            + codigoCaso + " text not null, "
            + participante + " integer not null, "
            + enfermo + " text, "
            + fechaEnfermedad + " date, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCasoParticipante + "));";
    
    //Tabla FormularioContactoCaso
    public static final String CONTACTOS_CASOS_TABLE = "chf_contactos_casos";

    //Campos FormularioContactoCaso
    public static final String codigoCasoContacto = "codigoCasoContacto";
    public static final String codigoVisitaCaso = "codigoVisitaCaso";
    public static final String partContacto = "partContacto";
    public static final String fechaContacto = "fechaContacto";
    public static final String tiempoInteraccion = "tiempoInteraccion";
    public static final String tipoInteraccion = "tipoInteraccion";

    //Crear FormularioContactoCaso
    public static final String CREATE_CONTACTOS_CASOS_TABLE = "create table if not exists "
            + CONTACTOS_CASOS_TABLE + " ("
            + codigoCasoContacto + " text not null, "
            + codigoVisitaCaso + " text not null, "
            + partContacto + " integer, "
            + fechaContacto + " date, "
            + tiempoInteraccion + " text, "
            + tipoInteraccion + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCasoContacto + "));";

    //Tabla VisitaSeguimientoCaso
    public static final String VISITAS_CASOS_TABLE = "chf_visitas_casos";

    //Campos VisitaSeguimientoCaso
    public static final String codigoCasoVisita = "codigoCasoVisita";
    public static final String fechaVisita = "fechaVisita";
    public static final String visita = "visita";
    public static final String horaProbableVisita = "horaProbableVisita";
    public static final String expCS = "expCS";
    public static final String temp = "temp";

    //Crear VisitaSeguimientoCaso
    public static final String CREATE_VISITAS_CASOS_TABLE = "create table if not exists "
            + VISITAS_CASOS_TABLE + " ("
            + codigoCasoVisita + " text not null, "
            + codigoCasoParticipante + " text not null, "
            + fechaVisita + " date, "
            + visita + " text, "
            + horaProbableVisita + " text, "
            + expCS + " text, "
            + temp + " real, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCasoVisita + "));";
    
    
    //Tabla VisitaSeguimientoCasoSintomas
    public static final String SINTOMAS_CASOS_TABLE = "chf_sintomas_casos";

    //Campos VisitaSeguimientoCasoSintomas
    public static final String codigoCasoSintoma = "codigoCasoSintoma";
    public static final String fechaSintomas = "fechaSintomas";
    public static final String periodo = "periodo";
    public static final String fiebre = "fiebre";
    public static final String fif = "fif";
    public static final String fiebreCuantificada = "fiebreCuantificada";
    public static final String valorFiebreCuantificada = "valorFiebreCuantificada";
    public static final String dolorCabeza = "dolorCabeza";
    public static final String dolorArticular = "dolorArticular";
    public static final String dolorMuscular = "dolorMuscular";
    public static final String dificultadRespiratoria = "dificultadRespiratoria";
    public static final String fdr = "fdr";
    public static final String secrecionNasal = "secrecionNasal";
    public static final String fsn = "fsn";
    public static final String tos = "tos";
    public static final String ftos = "ftos";
    public static final String pocoApetito = "pocoApetito";
    public static final String dolorGarganta = "dolorGarganta";
    public static final String diarrea = "diarrea";
    public static final String quedoCama = "quedoCama";
    public static final String respiracionRuidosa = "respiracionRuidosa";
    public static final String frr = "frr";
    public static final String oseltamivir = "oseltamivir";
    public static final String antibiotico = "antibiotico";
    public static final String cualAntibiotico = "cualAntibiotico";
    public static final String prescritoMedico = "prescritoMedico";


    //Crear VisitaSeguimientoCasoSintomas
    public static final String CREATE_SINTOMAS_CASOS_TABLE = "create table if not exists "
            + SINTOMAS_CASOS_TABLE + " ("
            + codigoCasoSintoma + " text not null, "
            + codigoCasoVisita + " text not null, "
            + fechaSintomas + " date, "
            + periodo + " text, "
            + fiebre + " text, "
            + fif + " date, "
            + fiebreCuantificada + " text, "
            + valorFiebreCuantificada + " real, "            
            + dolorCabeza + " text, "
            + dolorArticular + " text, "
            + dolorMuscular + " text, "
            + dificultadRespiratoria + " text, "
            + fdr + " date, "
            + secrecionNasal + " text, "
            + fsn + " date, "
            + tos + " text, "
            + ftos + " date, "
            + pocoApetito + " text, "
            + dolorGarganta + " text, "
            + diarrea + " text, "
            + quedoCama + " text, "
            + respiracionRuidosa + " text, "
            + frr + " date, "
            + oseltamivir + " text, "
            + antibiotico + " text, "
            + cualAntibiotico + " text, "
            + prescritoMedico + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoCasoSintoma + "));";
    
    //Tabla VisitaFallidaCaso
    public static final String VISITAS_FALLIDAS_CASOS_TABLE = "chf_visitas_fallidas_casos";

    //Campos VisitaFallidaCaso
    public static final String codigoFallaVisita = "codigoFallaVisita";
    public static final String codigoParticipanteCaso = "codigoParticipanteCaso";
    public static final String razonVisitaFallida = "razonVisitaFallida";
    public static final String otraRazon = "otraRazon";
    

    //Crear VisitaFallidaCaso
    public static final String CREATE_VISITAS_FALLIDAS_CASOS_TABLE = "create table if not exists "
            + VISITAS_FALLIDAS_CASOS_TABLE + " ("
            + codigoFallaVisita + " text not null, "
            + codigoParticipanteCaso + " text not null, "
            + fechaVisita + " date, "
            + razonVisitaFallida + " text, "
            + otraRazon + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoFallaVisita + "));";
    
    //Tabla VisitaFinalCaso
    public static final String VISITAS_FINALES_CASOS_TABLE = "chf_visitas_finales_casos";

    //Campos VisitaFinalCaso
    //public static final String codigoParticipanteCaso = "codigoParticipanteCaso";
    //public static final String fechaVisita = "fechaVisita";
    //public static final String enfermo = "enfermo";
    public static final String consTerreno = "consTerreno";
    public static final String referidoCs = "referidoCs";
    public static final String tratamiento = "tratamiento";
    //public static final String cualAntibiotico = "cualAntibiotico";
    public static final String sintResp = "sintResp";
    //public static final String fiebre = "fiebre";
    //public static final String tos = "tos";
    //public static final String dolorGarganta = "dolorGarganta";
    //public static final String secrecionNasal = "secrecionNasal";
    //public static final String fif = "fif";
    public static final String fff = "fff";
    public static final String fitos = "fitos";
    public static final String fftos = "fftos";
    public static final String figg = "figg";
    public static final String ffgg = "ffgg";
    public static final String fisn = "fisn";
    public static final String ffsn = "ffsn";

    //Crear VisitaFinalCaso
    public static final String CREATE_VISITAS_FINALES_CASOS_TABLE = "create table if not exists "
            + VISITAS_FINALES_CASOS_TABLE + " ("
            + codigoParticipanteCaso + " text not null, "
            + fechaVisita + " date, "
            + enfermo + " text, "
            + consTerreno + " text, "
            + referidoCs + " text, "
            + tratamiento + " text, "
            + cualAntibiotico + " text, "
            + sintResp + " text, "
            + fiebre + " text, "
            + tos + " text, "
            + dolorGarganta + " text, "
            + secrecionNasal + " text, "
            + fif + " date, "
            + fff + " date, "
            + fitos + " date, "
            + fftos + " date, "
            + figg + " date, "
            + ffgg + " date, "
            + fisn + " date, "
            + ffsn + " date, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoParticipanteCaso + "));";
    
    //Tabla InformacionNoCompletaCaso
    public static final String NO_DATA_CASOS_TABLE = "chf_no_data_casos";

    //Campos InformacionNoCompletaCaso
    public static final String codigoNoDataVisita = "codigoNoDataVisita";
    public static final String razonNoCompletaInformacion = "razonNoCompletaInformacion";
    
    //Crear InformacionNoCompletaCaso
    public static final String CREATE_NO_DATA_CASOS_TABLE = "create table if not exists "
            + NO_DATA_CASOS_TABLE + " ("
            + codigoNoDataVisita + " text not null, "
            + codigoVisitaCaso + " text not null, "
            + razonNoCompletaInformacion + " text, "
            + otraRazon + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoNoDataVisita + "));";

}
