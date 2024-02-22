package ni.org.ics.estudios.appmovil.domain.covid19;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;

import java.util.Date;

/**
 * Created by miguel on 10/9/2020.
 */
public class CuestionarioCovid19 extends BaseMetaData {

    private String codigo;
    private Participante participante;
    //2. ¿Desde febrero de 2021 ha tenido usted o su niño/a alguno de los siguientes síntomas?
/*    private String feb20Febricula;
    private String feb20Fiebre;
    private String feb20Escalofrio;
    private String feb20TemblorEscalofrio;
    private String feb20DolorMuscular;
    private String feb20DolorArticular;
    private String feb20SecresionNasal;
    private String feb20DolorGarganta;
    private String feb20Tos;
    private String feb20DificultadResp;
    private String feb20DolorPecho;
    private String feb20NauseasVomito;
    private String feb20DolorCabeza;
    private String feb20DolorAbdominal;
    private String feb20Diarrea;
    private String feb20DificultadDormir;
    private String feb20Debilidad;
    private String feb20PerdidaOlfatoGusto;
    private String feb20Mareo;
    private String feb20Sarpullido;
    private String feb20Desmayo;
    private String feb20QuedoCama;*/
    /*3.	[Si uno o mas de los síntomas en la sección] Que fecha exacta o aproximada empezaron estos síntomas*/
    private String e1SabeFIS;
    private Date e1Fis;
    private String e1MesInicioSintoma;
    private String e1AnioInicioSintoma;
    /*3.	¿Usted cree que en el año 2020 que usted o su niño/a pudo haber padecido de COVID-19 o su medico le informo que padeció de COVID-19?*/
    //en verison 3.0 del formulario para muestreo familia nov 2021 se elimino esta pregunta
    //private String padecidoCovid19;
    /*4.	¿Conoce en donde usted o su niño/a estuvieron expuestos a COVID-19?*/
    private String e1ConoceLugarExposicion;
    private String e1LugarExposicion;
    /*5.	¿Busco ayuda medica?*/
    private String e1BuscoAyuda;
    /*6.	[Si P5==”Si”] Donde busco ayuda médica?*/
    private String e1DondeBuscoAyuda;
    private String e1NombreCentroSalud;
    private String e1NombreHospital;
    private String e1RecibioSeguimiento;
    private String e1TipoSeguimiento;
    /*7.	[Si P5==”Si”] Cuanto tiempo después de que iniciaron los síntomas de COVID-19 busco ayuda medica? */
    private String e1TmpDespuesBuscoAyuda;
    /*8.	[Si P5==”Si”] Paso al menos una noche en el hospital por COVID-19?*/
    private String e1UnaNocheHospital;
    /*Si P7==”Si”] A que hospital acudió?  */
    private String e1QueHospital;
    /*10.	[Si P8 == “Si”] Cuantas noches estuvo en el hospital? */
    private String e1SabeCuantasNoches;
    private Integer e1CuantasNochesHosp;
    /*  11.	[Si P8== “Si”] Cual fue su fecha de admisión al hospital? */
    private String e1SabeFechaAdmision;
    private Date e1FechaAdmisionHosp;
    /*12.	 [Si P8 == “Si”] Cual fue su fecha de alta medica? */
    private String e1SabeFechaAlta;
    private Date e1FechaAltaHosp;
    /*13.	[Si P8==”Si”] Mientras estuvo en el hospital, requirió oxigeno?*/
    private String e1UtilizoOxigeno;
    /*14.	[Si P8==”Si”] Mientras usted o su niño/a estuvo en el hospital, en algún momento requirió de Unidad de Cuidados Intensivos?*/
    private String e1EstuvoUCI;
    private String e1FueIntubado;
    /*  15.	[Si la P1 == “Si”] Se ha recuperado del COVID-19 y posee ahora un estado de salud normal o igual al que tenia antes de enfermedad?*/
    private String e1RecuperadoCovid19;
    /*16.	[Si P15 == “No” o “No Sabe/No esta seguro”/no contesto] ¿Usted o su niño/a que síntomas presenta aun?*/
    private String e1TieneFebricula;
    private String e1TieneCansancio;
    private String e1TieneDolorCabeza;
    private String e1TienePerdidaOlfato;
    private String e1TienePerdidaGusto;
    private String e1TieneTos;
    private String e1TieneDificultadRespirar;
    private String e1TieneDolorPecho;
    private String e1TienePalpitaciones;
    private String e1TieneDolorArticulaciones;
    private String e1TieneParalisis;
    private String e1TieneMareos;
    private String e1TienePensamientoNublado;
    private String e1TieneProblemasDormir;
    private String e1TieneDepresion;
    private String e1TieneOtrosSintomas;
    private String e1cualesSintomas;
    /*  17.	[Si P15 == “Si”] ¿ A usted o su niño/a cuanto tiempo le tomo en recuperarse de los síntomas secundarios a COVID-19 y recobrar su salud normal?*/
    private String e1SabeTiempoRecuperacion;
    private String e1TiempoRecuperacion;
    private String e1TiempoRecuperacionEn;
    /*18.	[Si P1==”Si”] En el momento más severo de su enfermedad, diría que su enfermedad o la de su niño/a fue?*/
    private String e1SeveridadEnfermedad;
    /*  19.	[Si P1==”Si”] Que medicamentos tomo para COVID-19*/
    private String e1TomoMedicamento;
    private String e1QueMedicamento;
    private String e1OtroMedicamento;
    //20. ¿A Ud. o su niño/a le administraron oxígeno en su domicilio?
    private String e1OxigenoDomicilio;
    //20. 1 ¿Por cuanto tiempo le administraron?
    private String e1TiempoOxigenoDom;
    // evento2
    private String e2SabeFIS;
    private Date e2Fis;
    private String e2MesInicioSintoma;
    private String e2AnioInicioSintoma;
    private String e2ConoceLugarExposicion;
    private String e2LugarExposicion;
    private String e2BuscoAyuda;
    private String e2DondeBuscoAyuda;
    private String e2NombreCentroSalud;
    private String e2NombreHospital;
    private String e2RecibioSeguimiento;
    private String e2TipoSeguimiento;
    private String e2TmpDespuesBuscoAyuda;
    private String e2UnaNocheHospital;
    private String e2QueHospital;
    private String e2SabeCuantasNoches;
    private Integer e2CuantasNochesHosp;
    private String e2SabeFechaAdmision;
    private Date e2FechaAdmisionHosp;
    private String e2SabeFechaAlta;
    private Date e2FechaAltaHosp;
    private String e2UtilizoOxigeno;
    private String e2EstuvoUCI;
    private String e2FueIntubado;
    private String e2RecuperadoCovid19;
    private String e2TieneFebricula;
    private String e2TieneCansancio;
    private String e2TieneDolorCabeza;
    private String e2TienePerdidaOlfato;
    private String e2TienePerdidaGusto;
    private String e2TieneTos;
    private String e2TieneDificultadRespirar;
    private String e2TieneDolorPecho;
    private String e2TienePalpitaciones;
    private String e2TieneDolorArticulaciones;
    private String e2TieneParalisis;
    private String e2TieneMareos;
    private String e2TienePensamientoNublado;
    private String e2TieneProblemasDormir;
    private String e2TieneDepresion;
    private String e2TieneOtrosSintomas;
    private String e2cualesSintomas;
    private String e2SabeTiempoRecuperacion;
    private String e2TiempoRecuperacion;
    private String e2TiempoRecuperacionEn;
    private String e2SeveridadEnfermedad;
    private String e2TomoMedicamento;
    private String e2QueMedicamento;
    private String e2OtroMedicamento;
    private String e2OxigenoDomicilio;
    private String e2TiempoOxigenoDom;
    //evento3
    private String e3SabeFIS;
    private Date e3Fis;
    private String e3MesInicioSintoma;
    private String e3AnioInicioSintoma;
    private String e3ConoceLugarExposicion;
    private String e3LugarExposicion;
    private String e3BuscoAyuda;
    private String e3DondeBuscoAyuda;
    private String e3NombreCentroSalud;
    private String e3NombreHospital;
    private String e3RecibioSeguimiento;
    private String e3TipoSeguimiento;
    private String e3TmpDespuesBuscoAyuda;
    private String e3UnaNocheHospital;
    private String e3QueHospital;
    private String e3SabeCuantasNoches;
    private Integer e3CuantasNochesHosp;
    private String e3SabeFechaAdmision;
    private Date e3FechaAdmisionHosp;
    private String e3SabeFechaAlta;
    private Date e3FechaAltaHosp;
    private String e3UtilizoOxigeno;
    private String e3EstuvoUCI;
    private String e3FueIntubado;
    private String e3RecuperadoCovid19;
    private String e3TieneFebricula;
    private String e3TieneCansancio;
    private String e3TieneDolorCabeza;
    private String e3TienePerdidaOlfato;
    private String e3TienePerdidaGusto;
    private String e3TieneTos;
    private String e3TieneDificultadRespirar;
    private String e3TieneDolorPecho;
    private String e3TienePalpitaciones;
    private String e3TieneDolorArticulaciones;
    private String e3TieneParalisis;
    private String e3TieneMareos;
    private String e3TienePensamientoNublado;
    private String e3TieneProblemasDormir;
    private String e3TieneDepresion;
    private String e3TieneOtrosSintomas;
    private String e3cualesSintomas;
    private String e3SabeTiempoRecuperacion;
    private String e3TiempoRecuperacion;
    private String e3TiempoRecuperacionEn;
    private String e3SeveridadEnfermedad;
    private String e3TomoMedicamento;
    private String e3QueMedicamento;
    private String e3OtroMedicamento;
    private String e3OxigenoDomicilio;
    private String e3TiempoOxigenoDom;

    /*21.	Algún medico u otro profesional de la salud le ha dicho alguna vez que usted o su niño/a padece alguna de estas condiciones?*/
    private String padeceEnfisema;
    private String padeceAsma;
    private String padeceDiabetes;
    private String padeceEnfCoronaria;
    private String padecePresionAlta;
    private String padeceEnfHigado;
    private String padeceEnfRenal;
    private String padeceInfartoDerrameCer;
    private String padeceCancer;
    private String padeceCondicionInmuno;
    private String padeceEnfAutoinmune;
    private String padeceDiscapacidadFis;
    private String padeceCondPsicPsiq;
    private String padeceOtraCondicion;
    private String queOtraCondicion;
    /*  22.	¿Ha fumado alguna vez en su vida? . para mayores 14 anios*/
    private String fumado;
    /*  23.	¿Ha fumado al menos 100 cigarrillos en su vida?*/
    private String fumadoCienCigarrillos;
    /*  24.	[Si P1==”Si”] ¿En los días previos a que se enfermara, usted fumaba cigarrillos todos los días o algunos días previo a que se enfermara? */
    private String e1FumadoPrevioEnfermedad;
    /*25.	¿Usted fumaba cigarrillos todos los días o algunos días ahora? */
    private String e1FumaActualmente;
    /*26 ¿Usted estaba embarazada al momento de ser diagnosticada o sospechosa de COVID19? Preguntar si es Si P1==Si, Y si es mujer*/
    private String e1Embarazada;
    /*Si es Si, Recuerda las semanas de embarazo que tenia*/
    private String e1RecuerdaSemanasEmb;
    private Integer e1SemanasEmbarazo;
    /*Si es Si, como finalizo el embarzo*/
    private String e1FinalEmbarazo;
    private String e1OtroFinalEmbarazo;
    /*27.  [Si P26==Si] Si estaba embarazada recientemente al momento de ser diagnosticada con COVID19, ¿Usted estaba dando pecho materno?*/
    private String e1DabaPecho;
    /*28. ¿Usted estuvo empleado como trabajador de la salud desde el 1 de febrero de 2020?*/
    private String trabajadorSalud;
    private String periodoSintomas;//Almacena desde que perido se estan preguntando los sintomas de la pregunta 1
    //evento2
    private String e2FumadoPrevioEnfermedad;
    private String e2FumaActualmente;
    private String e2Embarazada;
    private String e2RecuerdaSemanasEmb;
    private Integer e2SemanasEmbarazo;
    private String e2FinalEmbarazo;
    private String e2OtroFinalEmbarazo;
    private String e2DabaPecho;
    //evento3
    private String e3FumadoPrevioEnfermedad;
    private String e3FumaActualmente;
    private String e3Embarazada;
    private String e3RecuerdaSemanasEmb;
    private Integer e3SemanasEmbarazo;
    private String e3FinalEmbarazo;
    private String e3OtroFinalEmbarazo;
    private String e3DabaPecho;

    //Muestreo Familia Nov 2021
    //1. ¿Desde febrero 2021 a usted o su niño/a le han diagnosticado o ha estado enfermo con la COVID19?
    private String enfermoCovid19;
    //¿Cuantas veces ha estado enfermo en este periodo?
    private String cuantasVecesEnfermo;
    private String sabeEvento1;
    private Date evento1;
    private String anioEvento1;
    private String mesEvento1;
    private String sabeEvento2;
    private Date evento2;
    private String anioEvento2;
    private String mesEvento2;
    private String sabeEvento3;
    private Date evento3;
    private String anioEvento3;
    private String mesEvento3;
    //sintomas evento1
    private String e1Febricula;
    private String e1Fiebre;
    private String e1Escalofrio;
    private String e1TemblorEscalofrio;
    private String e1DolorMuscular;
    private String e1DolorArticular;
    private String e1SecresionNasal;
    private String e1DolorGarganta;
    private String e1Tos;
    private String e1DificultadResp;
    private String e1DolorPecho;
    private String e1NauseasVomito;
    private String e1DolorCabeza;
    private String e1DolorAbdominal;
    private String e1Diarrea;
    private String e1DificultadDormir;
    private String e1Debilidad;
    private String e1PerdidaOlfatoGusto;
    private String e1Mareo;
    private String e1Sarpullido;
    private String e1Desmayo;
    private String e1QuedoCama;
    //sintomas evento2
    private String e2Febricula;
    private String e2Fiebre;
    private String e2Escalofrio;
    private String e2TemblorEscalofrio;
    private String e2DolorMuscular;
    private String e2DolorArticular;
    private String e2SecresionNasal;
    private String e2DolorGarganta;
    private String e2Tos;
    private String e2DificultadResp;
    private String e2DolorPecho;
    private String e2NauseasVomito;
    private String e2DolorCabeza;
    private String e2DolorAbdominal;
    private String e2Diarrea;
    private String e2DificultadDormir;
    private String e2Debilidad;
    private String e2PerdidaOlfatoGusto;
    private String e2Mareo;
    private String e2Sarpullido;
    private String e2Desmayo;
    private String e2QuedoCama;
    //sintomas evento3
    private String e3Febricula;
    private String e3Fiebre;
    private String e3Escalofrio;
    private String e3TemblorEscalofrio;
    private String e3DolorMuscular;
    private String e3DolorArticular;
    private String e3SecresionNasal;
    private String e3DolorGarganta;
    private String e3Tos;
    private String e3DificultadResp;
    private String e3DolorPecho;
    private String e3NauseasVomito;
    private String e3DolorCabeza;
    private String e3DolorAbdominal;
    private String e3Diarrea;
    private String e3DificultadDormir;
    private String e3Debilidad;
    private String e3PerdidaOlfatoGusto;
    private String e3Mareo;
    private String e3Sarpullido;
    private String e3Desmayo;
    private String e3QuedoCama;
    //29. ¿Usted o su niño/a han sido vacunado en la jornada de vacunación voluntaria contra la COVID-19
    private String vacunadoCovid19;
    //30. ¿Puede mostrarnos su tarjeta de vacunación contra la COVID 19?
    private String muestraTarjetaVac;
    //Si no es No, Sabe cuál vacuna le fue aplicada
    private String sabeNombreVacuna;
    private String nombreVacuna;
    private String anioVacuna;
    private String mesVacuna;
    //Si es SI que vacuna está registrada (Permita el ingreso de la primera y segunda dosis)
    private String cuantasDosis;
    private String nombreDosis1;
    private String otraVacunaDosis1;
    private String loteDosis1;
    private Date fechaDosis1;
    private String nombreDosis2;
    private String otraVacunaDosis2;
    private String loteDosis2;
    private Date fechaDosis2;
    private String nombreDosis3;
    private String otraVacunaDosis3;
    private String loteDosis3;
    private Date fechaDosis3;
    //31. ¿Posterior a ser vacunado contra la COVID19 Ud. o su niño/a presento alguna molestia o síntoma posterior a un máximo de 72 horas a la vacunación?
    private String presentoSintomasDosis1;
    //31.1 ¿Cual o Cuales de estos síntomas presento?
    private String dolorSitioDosis1;
    private String fiebreDosis1;
    private String cansancioDosis1;
    private String dolorCabezaDosis1;
    private String diarreaDosis1;
    private String vomitoDosis1;
    private String dolorMuscularDosis1;
    private String escalofriosDosis1;
    private String reaccionAlergicaDosis1;
    private String infeccionSitioDosis1;
    private String nauseasDosis1;
    private String bultosDosis1;
    private String otrosDosis1;
    private String desOtrosDosis1;
    //dosis2
    private String presentoSintomasDosis2;
    private String dolorSitioDosis2;
    private String fiebreDosis2;
    private String cansancioDosis2;
    private String dolorCabezaDosis2;
    private String diarreaDosis2;
    private String vomitoDosis2;
    private String dolorMuscularDosis2;
    private String escalofriosDosis2;
    private String reaccionAlergicaDosis2;
    private String infeccionSitioDosis2;
    private String nauseasDosis2;
    private String bultosDosis2;
    private String otrosDosis2;
    private String desOtrosDosis2;
    //dosis3
    private String presentoSintomasDosis3;
    private String dolorSitioDosis3;
    private String fiebreDosis3;
    private String cansancioDosis3;
    private String dolorCabezaDosis3;
    private String diarreaDosis3;
    private String vomitoDosis3;
    private String dolorMuscularDosis3;
    private String escalofriosDosis3;
    private String reaccionAlergicaDosis3;
    private String infeccionSitioDosis3;
    private String nauseasDosis3;
    private String bultosDosis3;
    private String otrosDosis3;
    private String desOtrosDosis3;
    //32. ¿Ud. o su niño presentaron síntomas que sospecharon enfermedad por Covid-19 Posterior a haber recibido la vacuna de Covid-19?
    private String covid19PosteriorVacuna;
    private String fechaEventoEnfermoPostVac;
    private String sabeFechaEnfPostVac;
    private Date fechaEnfPostVac;
    private String anioEnfPostVac;
    private String mesEnfPostVac;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    /*
    public String getFeb20Febricula() {
        return feb20Febricula;
    }

    public void setFeb20Febricula(String feb20Febricula) {
        this.feb20Febricula = feb20Febricula;
    }

    public String getFeb20Fiebre() {
        return feb20Fiebre;
    }

    public void setFeb20Fiebre(String feb20Fiebre) {
        this.feb20Fiebre = feb20Fiebre;
    }

    public String getFeb20Escalofrio() {
        return feb20Escalofrio;
    }

    public void setFeb20Escalofrio(String feb20Escalofrio) {
        this.feb20Escalofrio = feb20Escalofrio;
    }

    public String getFeb20TemblorEscalofrio() {
        return feb20TemblorEscalofrio;
    }

    public void setFeb20TemblorEscalofrio(String feb20TemblorEscalofrio) {
        this.feb20TemblorEscalofrio = feb20TemblorEscalofrio;
    }

    public String getFeb20DolorMuscular() {
        return feb20DolorMuscular;
    }

    public void setFeb20DolorMuscular(String feb20DolorMuscular) {
        this.feb20DolorMuscular = feb20DolorMuscular;
    }

    public String getFeb20DolorArticular() {
        return feb20DolorArticular;
    }

    public void setFeb20DolorArticular(String feb20DolorArticular) {
        this.feb20DolorArticular = feb20DolorArticular;
    }

    public String getFeb20SecresionNasal() {
        return feb20SecresionNasal;
    }

    public void setFeb20SecresionNasal(String feb20SecresionNasal) {
        this.feb20SecresionNasal = feb20SecresionNasal;
    }

    public String getFeb20DolorGarganta() {
        return feb20DolorGarganta;
    }

    public void setFeb20DolorGarganta(String feb20DolorGarganta) {
        this.feb20DolorGarganta = feb20DolorGarganta;
    }

    public String getFeb20Tos() {
        return feb20Tos;
    }

    public void setFeb20Tos(String feb20Tos) {
        this.feb20Tos = feb20Tos;
    }

    public String getFeb20DificultadResp() {
        return feb20DificultadResp;
    }

    public void setFeb20DificultadResp(String feb20DificultadResp) {
        this.feb20DificultadResp = feb20DificultadResp;
    }

    public String getFeb20DolorPecho() {
        return feb20DolorPecho;
    }

    public void setFeb20DolorPecho(String feb20DolorPecho) {
        this.feb20DolorPecho = feb20DolorPecho;
    }

    public String getFeb20NauseasVomito() {
        return feb20NauseasVomito;
    }

    public void setFeb20NauseasVomito(String feb20NauseasVomito) {
        this.feb20NauseasVomito = feb20NauseasVomito;
    }

    public String getFeb20DolorCabeza() {
        return feb20DolorCabeza;
    }

    public void setFeb20DolorCabeza(String feb20DolorCabeza) {
        this.feb20DolorCabeza = feb20DolorCabeza;
    }

    public String getFeb20DolorAbdominal() {
        return feb20DolorAbdominal;
    }

    public void setFeb20DolorAbdominal(String feb20DolorAbdominal) {
        this.feb20DolorAbdominal = feb20DolorAbdominal;
    }

    public String getFeb20Diarrea() {
        return feb20Diarrea;
    }

    public void setFeb20Diarrea(String feb20Diarrea) {
        this.feb20Diarrea = feb20Diarrea;
    }

    public String getFeb20DificultadDormir() {
        return feb20DificultadDormir;
    }

    public void setFeb20DificultadDormir(String feb20DificultadDormir) {
        this.feb20DificultadDormir = feb20DificultadDormir;
    }

    public String getFeb20Debilidad() {
        return feb20Debilidad;
    }

    public void setFeb20Debilidad(String feb20Debilidad) {
        this.feb20Debilidad = feb20Debilidad;
    }

    public String getFeb20PerdidaOlfatoGusto() {
        return feb20PerdidaOlfatoGusto;
    }

    public void setFeb20PerdidaOlfatoGusto(String feb20PerdidaOlfatoGusto) {
        this.feb20PerdidaOlfatoGusto = feb20PerdidaOlfatoGusto;
    }

    public String getFeb20Mareo() {
        return feb20Mareo;
    }

    public void setFeb20Mareo(String feb20Mareo) {
        this.feb20Mareo = feb20Mareo;
    }

    public String getFeb20Sarpullido() {
        return feb20Sarpullido;
    }

    public void setFeb20Sarpullido(String feb20Sarpullido) {
        this.feb20Sarpullido = feb20Sarpullido;
    }

    public String getFeb20Desmayo() {
        return feb20Desmayo;
    }

    public void setFeb20Desmayo(String feb20Desmayo) {
        this.feb20Desmayo = feb20Desmayo;
    }

    public String getFeb20QuedoCama() {
        return feb20QuedoCama;
    }

    public void setFeb20QuedoCama(String feb20QuedoCama) {
        this.feb20QuedoCama = feb20QuedoCama;
    }
*/
    public String getE1SabeFIS() {
        return e1SabeFIS;
    }

    public void setE1SabeFIS(String e1SabeFIS) {
        this.e1SabeFIS = e1SabeFIS;
    }

    public Date getE1Fis() {
        return e1Fis;
    }

    public void setE1Fis(Date e1Fis) {
        this.e1Fis = e1Fis;
    }

    public String getE1MesInicioSintoma() {
        return e1MesInicioSintoma;
    }

    public void setE1MesInicioSintoma(String e1MesInicioSintoma) {
        this.e1MesInicioSintoma = e1MesInicioSintoma;
    }

    public String getE1AnioInicioSintoma() {
        return e1AnioInicioSintoma;
    }

    public void setE1AnioInicioSintoma(String e1AnioInicioSintoma) {
        this.e1AnioInicioSintoma = e1AnioInicioSintoma;
    }
/*
    public String getPadecidoCovid19() {
        return padecidoCovid19;
    }

    public void setPadecidoCovid19(String padecidoCovid19) {
        this.padecidoCovid19 = padecidoCovid19;
    }
*/
    public String getE1ConoceLugarExposicion() {
        return e1ConoceLugarExposicion;
    }

    public void setE1ConoceLugarExposicion(String e1ConoceLugarExposicion) {
        this.e1ConoceLugarExposicion = e1ConoceLugarExposicion;
    }

    public String getE1LugarExposicion() {
        return e1LugarExposicion;
    }

    public void setE1LugarExposicion(String e1LugarExposicion) {
        this.e1LugarExposicion = e1LugarExposicion;
    }

    public String getE1BuscoAyuda() {
        return e1BuscoAyuda;
    }

    public void setE1BuscoAyuda(String e1BuscoAyuda) {
        this.e1BuscoAyuda = e1BuscoAyuda;
    }

    public String getE1DondeBuscoAyuda() {
        return e1DondeBuscoAyuda;
    }

    public void setE1DondeBuscoAyuda(String e1DondeBuscoAyuda) {
        this.e1DondeBuscoAyuda = e1DondeBuscoAyuda;
    }

    public String getE1NombreCentroSalud() {
        return e1NombreCentroSalud;
    }

    public void setE1NombreCentroSalud(String e1NombreCentroSalud) {
        this.e1NombreCentroSalud = e1NombreCentroSalud;
    }

    public String getE1NombreHospital() {
        return e1NombreHospital;
    }

    public void setE1NombreHospital(String e1NombreHospital) {
        this.e1NombreHospital = e1NombreHospital;
    }

    public String getE1RecibioSeguimiento() {
        return e1RecibioSeguimiento;
    }

    public void setE1RecibioSeguimiento(String e1RecibioSeguimiento) {
        this.e1RecibioSeguimiento = e1RecibioSeguimiento;
    }

    public String getE1TipoSeguimiento() {
        return e1TipoSeguimiento;
    }

    public void setE1TipoSeguimiento(String e1TipoSeguimiento) {
        this.e1TipoSeguimiento = e1TipoSeguimiento;
    }

    public String getE1TmpDespuesBuscoAyuda() {
        return e1TmpDespuesBuscoAyuda;
    }

    public void setE1TmpDespuesBuscoAyuda(String e1TmpDespuesBuscoAyuda) {
        this.e1TmpDespuesBuscoAyuda = e1TmpDespuesBuscoAyuda;
    }

    public String getE1UnaNocheHospital() {
        return e1UnaNocheHospital;
    }

    public void setE1UnaNocheHospital(String e1UnaNocheHospital) {
        this.e1UnaNocheHospital = e1UnaNocheHospital;
    }

    public String getE1QueHospital() {
        return e1QueHospital;
    }

    public void setE1QueHospital(String e1QueHospital) {
        this.e1QueHospital = e1QueHospital;
    }

    public String getE1SabeCuantasNoches() {
        return e1SabeCuantasNoches;
    }

    public void setE1SabeCuantasNoches(String e1SabeCuantasNoches) {
        this.e1SabeCuantasNoches = e1SabeCuantasNoches;
    }

    public Integer getE1CuantasNochesHosp() {
        return e1CuantasNochesHosp;
    }

    public void setE1CuantasNochesHosp(Integer e1CuantasNochesHosp) {
        this.e1CuantasNochesHosp = e1CuantasNochesHosp;
    }

    public String getE1SabeFechaAdmision() {
        return e1SabeFechaAdmision;
    }

    public void setE1SabeFechaAdmision(String e1SabeFechaAdmision) {
        this.e1SabeFechaAdmision = e1SabeFechaAdmision;
    }

    public Date getE1FechaAdmisionHosp() {
        return e1FechaAdmisionHosp;
    }

    public void setE1FechaAdmisionHosp(Date e1FechaAdmisionHosp) {
        this.e1FechaAdmisionHosp = e1FechaAdmisionHosp;
    }

    public String getE1SabeFechaAlta() {
        return e1SabeFechaAlta;
    }

    public void setE1SabeFechaAlta(String e1SabeFechaAlta) {
        this.e1SabeFechaAlta = e1SabeFechaAlta;
    }

    public Date getE1FechaAltaHosp() {
        return e1FechaAltaHosp;
    }

    public void setE1FechaAltaHosp(Date e1FechaAltaHosp) {
        this.e1FechaAltaHosp = e1FechaAltaHosp;
    }

    public String getE1UtilizoOxigeno() {
        return e1UtilizoOxigeno;
    }

    public void setE1UtilizoOxigeno(String e1UtilizoOxigeno) {
        this.e1UtilizoOxigeno = e1UtilizoOxigeno;
    }

    public String getE1EstuvoUCI() {
        return e1EstuvoUCI;
    }

    public void setE1EstuvoUCI(String e1EstuvoUCI) {
        this.e1EstuvoUCI = e1EstuvoUCI;
    }

    public String getE1FueIntubado() {
        return e1FueIntubado;
    }

    public void setE1FueIntubado(String e1FueIntubado) {
        this.e1FueIntubado = e1FueIntubado;
    }

    public String getE1RecuperadoCovid19() {
        return e1RecuperadoCovid19;
    }

    public void setE1RecuperadoCovid19(String e1RecuperadoCovid19) {
        this.e1RecuperadoCovid19 = e1RecuperadoCovid19;
    }

    public String getE1TieneFebricula() {
        return e1TieneFebricula;
    }

    public void setE1TieneFebricula(String e1TieneFebricula) {
        this.e1TieneFebricula = e1TieneFebricula;
    }

    public String getE1TieneCansancio() {
        return e1TieneCansancio;
    }

    public void setE1TieneCansancio(String e1TieneCansancio) {
        this.e1TieneCansancio = e1TieneCansancio;
    }

    public String getE1TieneDolorCabeza() {
        return e1TieneDolorCabeza;
    }

    public void setE1TieneDolorCabeza(String e1TieneDolorCabeza) {
        this.e1TieneDolorCabeza = e1TieneDolorCabeza;
    }

    public String getE1TienePerdidaOlfato() {
        return e1TienePerdidaOlfato;
    }

    public void setE1TienePerdidaOlfato(String e1TienePerdidaOlfato) {
        this.e1TienePerdidaOlfato = e1TienePerdidaOlfato;
    }

    public String getE1TienePerdidaGusto() {
        return e1TienePerdidaGusto;
    }

    public void setE1TienePerdidaGusto(String e1TienePerdidaGusto) {
        this.e1TienePerdidaGusto = e1TienePerdidaGusto;
    }

    public String getE1TieneTos() {
        return e1TieneTos;
    }

    public void setE1TieneTos(String e1TieneTos) {
        this.e1TieneTos = e1TieneTos;
    }

    public String getE1TieneDificultadRespirar() {
        return e1TieneDificultadRespirar;
    }

    public void setE1TieneDificultadRespirar(String e1TieneDificultadRespirar) {
        this.e1TieneDificultadRespirar = e1TieneDificultadRespirar;
    }

    public String getE1TieneDolorPecho() {
        return e1TieneDolorPecho;
    }

    public void setE1TieneDolorPecho(String e1TieneDolorPecho) {
        this.e1TieneDolorPecho = e1TieneDolorPecho;
    }

    public String getE1TienePalpitaciones() {
        return e1TienePalpitaciones;
    }

    public void setE1TienePalpitaciones(String e1TienePalpitaciones) {
        this.e1TienePalpitaciones = e1TienePalpitaciones;
    }

    public String getE1TieneDolorArticulaciones() {
        return e1TieneDolorArticulaciones;
    }

    public void setE1TieneDolorArticulaciones(String e1TieneDolorArticulaciones) {
        this.e1TieneDolorArticulaciones = e1TieneDolorArticulaciones;
    }

    public String getE1TieneParalisis() {
        return e1TieneParalisis;
    }

    public void setE1TieneParalisis(String e1TieneParalisis) {
        this.e1TieneParalisis = e1TieneParalisis;
    }

    public String getE1TieneMareos() {
        return e1TieneMareos;
    }

    public void setE1TieneMareos(String e1TieneMareos) {
        this.e1TieneMareos = e1TieneMareos;
    }

    public String getE1TienePensamientoNublado() {
        return e1TienePensamientoNublado;
    }

    public void setE1TienePensamientoNublado(String e1TienePensamientoNublado) {
        this.e1TienePensamientoNublado = e1TienePensamientoNublado;
    }

    public String getE1TieneProblemasDormir() {
        return e1TieneProblemasDormir;
    }

    public void setE1TieneProblemasDormir(String e1TieneProblemasDormir) {
        this.e1TieneProblemasDormir = e1TieneProblemasDormir;
    }

    public String getE1TieneDepresion() {
        return e1TieneDepresion;
    }

    public void setE1TieneDepresion(String e1TieneDepresion) {
        this.e1TieneDepresion = e1TieneDepresion;
    }

    public String getE1TieneOtrosSintomas() {
        return e1TieneOtrosSintomas;
    }

    public void setE1TieneOtrosSintomas(String e1TieneOtrosSintomas) {
        this.e1TieneOtrosSintomas = e1TieneOtrosSintomas;
    }

    public String getE1cualesSintomas() {
        return e1cualesSintomas;
    }

    public void setE1cualesSintomas(String e1cualesSintomas) {
        this.e1cualesSintomas = e1cualesSintomas;
    }

    public String getE1SabeTiempoRecuperacion() {
        return e1SabeTiempoRecuperacion;
    }

    public void setE1SabeTiempoRecuperacion(String e1SabeTiempoRecuperacion) {
        this.e1SabeTiempoRecuperacion = e1SabeTiempoRecuperacion;
    }

    public String getE1TiempoRecuperacion() {
        return e1TiempoRecuperacion;
    }

    public void setE1TiempoRecuperacion(String e1TiempoRecuperacion) {
        this.e1TiempoRecuperacion = e1TiempoRecuperacion;
    }

    public String getE1TiempoRecuperacionEn() {
        return e1TiempoRecuperacionEn;
    }

    public void setE1TiempoRecuperacionEn(String e1TiempoRecuperacionEn) {
        this.e1TiempoRecuperacionEn = e1TiempoRecuperacionEn;
    }

    public String getE1SeveridadEnfermedad() {
        return e1SeveridadEnfermedad;
    }

    public void setE1SeveridadEnfermedad(String e1SeveridadEnfermedad) {
        this.e1SeveridadEnfermedad = e1SeveridadEnfermedad;
    }

    public String getE1TomoMedicamento() {
        return e1TomoMedicamento;
    }

    public void setE1TomoMedicamento(String e1TomoMedicamento) {
        this.e1TomoMedicamento = e1TomoMedicamento;
    }

    public String getE1QueMedicamento() {
        return e1QueMedicamento;
    }

    public void setE1QueMedicamento(String e1QueMedicamento) {
        this.e1QueMedicamento = e1QueMedicamento;
    }

    public String getE1OtroMedicamento() {
        return e1OtroMedicamento;
    }

    public void setE1OtroMedicamento(String e1OtroMedicamento) {
        this.e1OtroMedicamento = e1OtroMedicamento;
    }

    public String getE1OxigenoDomicilio() {
        return e1OxigenoDomicilio;
    }

    public void setE1OxigenoDomicilio(String e1OxigenoDomicilio) {
        this.e1OxigenoDomicilio = e1OxigenoDomicilio;
    }

    public String getE1TiempoOxigenoDom() {
        return e1TiempoOxigenoDom;
    }

    public void setE1TiempoOxigenoDom(String e1TiempoOxigenoDom) {
        this.e1TiempoOxigenoDom = e1TiempoOxigenoDom;
    }

    public String getE2SabeFIS() {
        return e2SabeFIS;
    }

    public void setE2SabeFIS(String e2SabeFIS) {
        this.e2SabeFIS = e2SabeFIS;
    }

    public Date getE2Fis() {
        return e2Fis;
    }

    public void setE2Fis(Date e2Fis) {
        this.e2Fis = e2Fis;
    }

    public String getE2MesInicioSintoma() {
        return e2MesInicioSintoma;
    }

    public void setE2MesInicioSintoma(String e2MesInicioSintoma) {
        this.e2MesInicioSintoma = e2MesInicioSintoma;
    }

    public String getE2AnioInicioSintoma() {
        return e2AnioInicioSintoma;
    }

    public void setE2AnioInicioSintoma(String e2AnioInicioSintoma) {
        this.e2AnioInicioSintoma = e2AnioInicioSintoma;
    }

    public String getE2ConoceLugarExposicion() {
        return e2ConoceLugarExposicion;
    }

    public void setE2ConoceLugarExposicion(String e2ConoceLugarExposicion) {
        this.e2ConoceLugarExposicion = e2ConoceLugarExposicion;
    }

    public String getE2LugarExposicion() {
        return e2LugarExposicion;
    }

    public void setE2LugarExposicion(String e2LugarExposicion) {
        this.e2LugarExposicion = e2LugarExposicion;
    }

    public String getE2BuscoAyuda() {
        return e2BuscoAyuda;
    }

    public void setE2BuscoAyuda(String e2BuscoAyuda) {
        this.e2BuscoAyuda = e2BuscoAyuda;
    }

    public String getE2DondeBuscoAyuda() {
        return e2DondeBuscoAyuda;
    }

    public void setE2DondeBuscoAyuda(String e2DondeBuscoAyuda) {
        this.e2DondeBuscoAyuda = e2DondeBuscoAyuda;
    }

    public String getE2NombreCentroSalud() {
        return e2NombreCentroSalud;
    }

    public void setE2NombreCentroSalud(String e2NombreCentroSalud) {
        this.e2NombreCentroSalud = e2NombreCentroSalud;
    }

    public String getE2NombreHospital() {
        return e2NombreHospital;
    }

    public void setE2NombreHospital(String e2NombreHospital) {
        this.e2NombreHospital = e2NombreHospital;
    }

    public String getE2RecibioSeguimiento() {
        return e2RecibioSeguimiento;
    }

    public void setE2RecibioSeguimiento(String e2RecibioSeguimiento) {
        this.e2RecibioSeguimiento = e2RecibioSeguimiento;
    }

    public String getE2TipoSeguimiento() {
        return e2TipoSeguimiento;
    }

    public void setE2TipoSeguimiento(String e2TipoSeguimiento) {
        this.e2TipoSeguimiento = e2TipoSeguimiento;
    }

    public String getE2TmpDespuesBuscoAyuda() {
        return e2TmpDespuesBuscoAyuda;
    }

    public void setE2TmpDespuesBuscoAyuda(String e2TmpDespuesBuscoAyuda) {
        this.e2TmpDespuesBuscoAyuda = e2TmpDespuesBuscoAyuda;
    }

    public String getE2UnaNocheHospital() {
        return e2UnaNocheHospital;
    }

    public void setE2UnaNocheHospital(String e2UnaNocheHospital) {
        this.e2UnaNocheHospital = e2UnaNocheHospital;
    }

    public String getE2QueHospital() {
        return e2QueHospital;
    }

    public void setE2QueHospital(String e2QueHospital) {
        this.e2QueHospital = e2QueHospital;
    }

    public String getE2SabeCuantasNoches() {
        return e2SabeCuantasNoches;
    }

    public void setE2SabeCuantasNoches(String e2SabeCuantasNoches) {
        this.e2SabeCuantasNoches = e2SabeCuantasNoches;
    }

    public Integer getE2CuantasNochesHosp() {
        return e2CuantasNochesHosp;
    }

    public void setE2CuantasNochesHosp(Integer e2CuantasNochesHosp) {
        this.e2CuantasNochesHosp = e2CuantasNochesHosp;
    }

    public String getE2SabeFechaAdmision() {
        return e2SabeFechaAdmision;
    }

    public void setE2SabeFechaAdmision(String e2SabeFechaAdmision) {
        this.e2SabeFechaAdmision = e2SabeFechaAdmision;
    }

    public Date getE2FechaAdmisionHosp() {
        return e2FechaAdmisionHosp;
    }

    public void setE2FechaAdmisionHosp(Date e2FechaAdmisionHosp) {
        this.e2FechaAdmisionHosp = e2FechaAdmisionHosp;
    }

    public String getE2SabeFechaAlta() {
        return e2SabeFechaAlta;
    }

    public void setE2SabeFechaAlta(String e2SabeFechaAlta) {
        this.e2SabeFechaAlta = e2SabeFechaAlta;
    }

    public Date getE2FechaAltaHosp() {
        return e2FechaAltaHosp;
    }

    public void setE2FechaAltaHosp(Date e2FechaAltaHosp) {
        this.e2FechaAltaHosp = e2FechaAltaHosp;
    }

    public String getE2UtilizoOxigeno() {
        return e2UtilizoOxigeno;
    }

    public void setE2UtilizoOxigeno(String e2UtilizoOxigeno) {
        this.e2UtilizoOxigeno = e2UtilizoOxigeno;
    }

    public String getE2EstuvoUCI() {
        return e2EstuvoUCI;
    }

    public void setE2EstuvoUCI(String e2EstuvoUCI) {
        this.e2EstuvoUCI = e2EstuvoUCI;
    }

    public String getE2FueIntubado() {
        return e2FueIntubado;
    }

    public void setE2FueIntubado(String e2FueIntubado) {
        this.e2FueIntubado = e2FueIntubado;
    }

    public String getE2TieneFebricula() {
        return e2TieneFebricula;
    }

    public void setE2TieneFebricula(String e2TieneFebricula) {
        this.e2TieneFebricula = e2TieneFebricula;
    }

    public String getE2TieneCansancio() {
        return e2TieneCansancio;
    }

    public void setE2TieneCansancio(String e2TieneCansancio) {
        this.e2TieneCansancio = e2TieneCansancio;
    }

    public String getE2TieneDolorCabeza() {
        return e2TieneDolorCabeza;
    }

    public void setE2TieneDolorCabeza(String e2TieneDolorCabeza) {
        this.e2TieneDolorCabeza = e2TieneDolorCabeza;
    }

    public String getE2TienePerdidaOlfato() {
        return e2TienePerdidaOlfato;
    }

    public void setE2TienePerdidaOlfato(String e2TienePerdidaOlfato) {
        this.e2TienePerdidaOlfato = e2TienePerdidaOlfato;
    }

    public String getE2TienePerdidaGusto() {
        return e2TienePerdidaGusto;
    }

    public void setE2TienePerdidaGusto(String e2TienePerdidaGusto) {
        this.e2TienePerdidaGusto = e2TienePerdidaGusto;
    }

    public String getE2TieneTos() {
        return e2TieneTos;
    }

    public void setE2TieneTos(String e2TieneTos) {
        this.e2TieneTos = e2TieneTos;
    }

    public String getE2TieneDificultadRespirar() {
        return e2TieneDificultadRespirar;
    }

    public void setE2TieneDificultadRespirar(String e2TieneDificultadRespirar) {
        this.e2TieneDificultadRespirar = e2TieneDificultadRespirar;
    }

    public String getE2TieneDolorPecho() {
        return e2TieneDolorPecho;
    }

    public void setE2TieneDolorPecho(String e2TieneDolorPecho) {
        this.e2TieneDolorPecho = e2TieneDolorPecho;
    }

    public String getE2TienePalpitaciones() {
        return e2TienePalpitaciones;
    }

    public void setE2TienePalpitaciones(String e2TienePalpitaciones) {
        this.e2TienePalpitaciones = e2TienePalpitaciones;
    }

    public String getE2TieneDolorArticulaciones() {
        return e2TieneDolorArticulaciones;
    }

    public void setE2TieneDolorArticulaciones(String e2TieneDolorArticulaciones) {
        this.e2TieneDolorArticulaciones = e2TieneDolorArticulaciones;
    }

    public String getE2TieneParalisis() {
        return e2TieneParalisis;
    }

    public void setE2TieneParalisis(String e2TieneParalisis) {
        this.e2TieneParalisis = e2TieneParalisis;
    }

    public String getE2TieneMareos() {
        return e2TieneMareos;
    }

    public void setE2TieneMareos(String e2TieneMareos) {
        this.e2TieneMareos = e2TieneMareos;
    }

    public String getE2TienePensamientoNublado() {
        return e2TienePensamientoNublado;
    }

    public void setE2TienePensamientoNublado(String e2TienePensamientoNublado) {
        this.e2TienePensamientoNublado = e2TienePensamientoNublado;
    }

    public String getE2TieneProblemasDormir() {
        return e2TieneProblemasDormir;
    }

    public void setE2TieneProblemasDormir(String e2TieneProblemasDormir) {
        this.e2TieneProblemasDormir = e2TieneProblemasDormir;
    }

    public String getE2TieneDepresion() {
        return e2TieneDepresion;
    }

    public void setE2TieneDepresion(String e2TieneDepresion) {
        this.e2TieneDepresion = e2TieneDepresion;
    }

    public String getE2TieneOtrosSintomas() {
        return e2TieneOtrosSintomas;
    }

    public void setE2TieneOtrosSintomas(String e2TieneOtrosSintomas) {
        this.e2TieneOtrosSintomas = e2TieneOtrosSintomas;
    }

    public String getE2cualesSintomas() {
        return e2cualesSintomas;
    }

    public void setE2cualesSintomas(String e2cualesSintomas) {
        this.e2cualesSintomas = e2cualesSintomas;
    }

    public String getE2SabeTiempoRecuperacion() {
        return e2SabeTiempoRecuperacion;
    }

    public void setE2SabeTiempoRecuperacion(String e2SabeTiempoRecuperacion) {
        this.e2SabeTiempoRecuperacion = e2SabeTiempoRecuperacion;
    }

    public String getE2TiempoRecuperacion() {
        return e2TiempoRecuperacion;
    }

    public void setE2TiempoRecuperacion(String e2TiempoRecuperacion) {
        this.e2TiempoRecuperacion = e2TiempoRecuperacion;
    }

    public String getE2TiempoRecuperacionEn() {
        return e2TiempoRecuperacionEn;
    }

    public void setE2TiempoRecuperacionEn(String e2TiempoRecuperacionEn) {
        this.e2TiempoRecuperacionEn = e2TiempoRecuperacionEn;
    }

    public String getE2SeveridadEnfermedad() {
        return e2SeveridadEnfermedad;
    }

    public void setE2SeveridadEnfermedad(String e2SeveridadEnfermedad) {
        this.e2SeveridadEnfermedad = e2SeveridadEnfermedad;
    }

    public String getE2TomoMedicamento() {
        return e2TomoMedicamento;
    }

    public void setE2TomoMedicamento(String e2TomoMedicamento) {
        this.e2TomoMedicamento = e2TomoMedicamento;
    }

    public String getE2QueMedicamento() {
        return e2QueMedicamento;
    }

    public void setE2QueMedicamento(String e2QueMedicamento) {
        this.e2QueMedicamento = e2QueMedicamento;
    }

    public String getE2OtroMedicamento() {
        return e2OtroMedicamento;
    }

    public void setE2OtroMedicamento(String e2OtroMedicamento) {
        this.e2OtroMedicamento = e2OtroMedicamento;
    }

    public String getE2OxigenoDomicilio() {
        return e2OxigenoDomicilio;
    }

    public void setE2OxigenoDomicilio(String e2OxigenoDomicilio) {
        this.e2OxigenoDomicilio = e2OxigenoDomicilio;
    }

    public String getE2TiempoOxigenoDom() {
        return e2TiempoOxigenoDom;
    }

    public void setE2TiempoOxigenoDom(String e2TiempoOxigenoDom) {
        this.e2TiempoOxigenoDom = e2TiempoOxigenoDom;
    }

    public String getE3SabeFIS() {
        return e3SabeFIS;
    }

    public void setE3SabeFIS(String e3SabeFIS) {
        this.e3SabeFIS = e3SabeFIS;
    }

    public Date getE3Fis() {
        return e3Fis;
    }

    public void setE3Fis(Date e3Fis) {
        this.e3Fis = e3Fis;
    }

    public String getE3MesInicioSintoma() {
        return e3MesInicioSintoma;
    }

    public void setE3MesInicioSintoma(String e3MesInicioSintoma) {
        this.e3MesInicioSintoma = e3MesInicioSintoma;
    }

    public String getE3AnioInicioSintoma() {
        return e3AnioInicioSintoma;
    }

    public void setE3AnioInicioSintoma(String e3AnioInicioSintoma) {
        this.e3AnioInicioSintoma = e3AnioInicioSintoma;
    }

    public String getE3ConoceLugarExposicion() {
        return e3ConoceLugarExposicion;
    }

    public void setE3ConoceLugarExposicion(String e3ConoceLugarExposicion) {
        this.e3ConoceLugarExposicion = e3ConoceLugarExposicion;
    }

    public String getE3LugarExposicion() {
        return e3LugarExposicion;
    }

    public void setE3LugarExposicion(String e3LugarExposicion) {
        this.e3LugarExposicion = e3LugarExposicion;
    }

    public String getE3BuscoAyuda() {
        return e3BuscoAyuda;
    }

    public void setE3BuscoAyuda(String e3BuscoAyuda) {
        this.e3BuscoAyuda = e3BuscoAyuda;
    }

    public String getE3DondeBuscoAyuda() {
        return e3DondeBuscoAyuda;
    }

    public void setE3DondeBuscoAyuda(String e3DondeBuscoAyuda) {
        this.e3DondeBuscoAyuda = e3DondeBuscoAyuda;
    }

    public String getE3NombreCentroSalud() {
        return e3NombreCentroSalud;
    }

    public void setE3NombreCentroSalud(String e3NombreCentroSalud) {
        this.e3NombreCentroSalud = e3NombreCentroSalud;
    }

    public String getE3NombreHospital() {
        return e3NombreHospital;
    }

    public void setE3NombreHospital(String e3NombreHospital) {
        this.e3NombreHospital = e3NombreHospital;
    }

    public String getE3RecibioSeguimiento() {
        return e3RecibioSeguimiento;
    }

    public void setE3RecibioSeguimiento(String e3RecibioSeguimiento) {
        this.e3RecibioSeguimiento = e3RecibioSeguimiento;
    }

    public String getE3TipoSeguimiento() {
        return e3TipoSeguimiento;
    }

    public void setE3TipoSeguimiento(String e3TipoSeguimiento) {
        this.e3TipoSeguimiento = e3TipoSeguimiento;
    }

    public String getE3TmpDespuesBuscoAyuda() {
        return e3TmpDespuesBuscoAyuda;
    }

    public void setE3TmpDespuesBuscoAyuda(String e3TmpDespuesBuscoAyuda) {
        this.e3TmpDespuesBuscoAyuda = e3TmpDespuesBuscoAyuda;
    }

    public String getE3UnaNocheHospital() {
        return e3UnaNocheHospital;
    }

    public void setE3UnaNocheHospital(String e3UnaNocheHospital) {
        this.e3UnaNocheHospital = e3UnaNocheHospital;
    }

    public String getE3QueHospital() {
        return e3QueHospital;
    }

    public void setE3QueHospital(String e3QueHospital) {
        this.e3QueHospital = e3QueHospital;
    }

    public String getE3SabeCuantasNoches() {
        return e3SabeCuantasNoches;
    }

    public void setE3SabeCuantasNoches(String e3SabeCuantasNoches) {
        this.e3SabeCuantasNoches = e3SabeCuantasNoches;
    }

    public Integer getE3CuantasNochesHosp() {
        return e3CuantasNochesHosp;
    }

    public void setE3CuantasNochesHosp(Integer e3CuantasNochesHosp) {
        this.e3CuantasNochesHosp = e3CuantasNochesHosp;
    }

    public String getE3SabeFechaAdmision() {
        return e3SabeFechaAdmision;
    }

    public void setE3SabeFechaAdmision(String e3SabeFechaAdmision) {
        this.e3SabeFechaAdmision = e3SabeFechaAdmision;
    }

    public Date getE3FechaAdmisionHosp() {
        return e3FechaAdmisionHosp;
    }

    public void setE3FechaAdmisionHosp(Date e3FechaAdmisionHosp) {
        this.e3FechaAdmisionHosp = e3FechaAdmisionHosp;
    }

    public String getE3SabeFechaAlta() {
        return e3SabeFechaAlta;
    }

    public void setE3SabeFechaAlta(String e3SabeFechaAlta) {
        this.e3SabeFechaAlta = e3SabeFechaAlta;
    }

    public Date getE3FechaAltaHosp() {
        return e3FechaAltaHosp;
    }

    public void setE3FechaAltaHosp(Date e3FechaAltaHosp) {
        this.e3FechaAltaHosp = e3FechaAltaHosp;
    }

    public String getE3UtilizoOxigeno() {
        return e3UtilizoOxigeno;
    }

    public void setE3UtilizoOxigeno(String e3UtilizoOxigeno) {
        this.e3UtilizoOxigeno = e3UtilizoOxigeno;
    }

    public String getE3EstuvoUCI() {
        return e3EstuvoUCI;
    }

    public void setE3EstuvoUCI(String e3EstuvoUCI) {
        this.e3EstuvoUCI = e3EstuvoUCI;
    }

    public String getE3FueIntubado() {
        return e3FueIntubado;
    }

    public void setE3FueIntubado(String e3FueIntubado) {
        this.e3FueIntubado = e3FueIntubado;
    }

    public String getE3TieneFebricula() {
        return e3TieneFebricula;
    }

    public void setE3TieneFebricula(String e3TieneFebricula) {
        this.e3TieneFebricula = e3TieneFebricula;
    }

    public String getE3TieneCansancio() {
        return e3TieneCansancio;
    }

    public void setE3TieneCansancio(String e3TieneCansancio) {
        this.e3TieneCansancio = e3TieneCansancio;
    }

    public String getE3TieneDolorCabeza() {
        return e3TieneDolorCabeza;
    }

    public void setE3TieneDolorCabeza(String e3TieneDolorCabeza) {
        this.e3TieneDolorCabeza = e3TieneDolorCabeza;
    }

    public String getE3TienePerdidaOlfato() {
        return e3TienePerdidaOlfato;
    }

    public void setE3TienePerdidaOlfato(String e3TienePerdidaOlfato) {
        this.e3TienePerdidaOlfato = e3TienePerdidaOlfato;
    }

    public String getE3TienePerdidaGusto() {
        return e3TienePerdidaGusto;
    }

    public void setE3TienePerdidaGusto(String e3TienePerdidaGusto) {
        this.e3TienePerdidaGusto = e3TienePerdidaGusto;
    }

    public String getE3TieneTos() {
        return e3TieneTos;
    }

    public void setE3TieneTos(String e3TieneTos) {
        this.e3TieneTos = e3TieneTos;
    }

    public String getE3TieneDificultadRespirar() {
        return e3TieneDificultadRespirar;
    }

    public void setE3TieneDificultadRespirar(String e3TieneDificultadRespirar) {
        this.e3TieneDificultadRespirar = e3TieneDificultadRespirar;
    }

    public String getE3TieneDolorPecho() {
        return e3TieneDolorPecho;
    }

    public void setE3TieneDolorPecho(String e3TieneDolorPecho) {
        this.e3TieneDolorPecho = e3TieneDolorPecho;
    }

    public String getE3TienePalpitaciones() {
        return e3TienePalpitaciones;
    }

    public void setE3TienePalpitaciones(String e3TienePalpitaciones) {
        this.e3TienePalpitaciones = e3TienePalpitaciones;
    }

    public String getE3TieneDolorArticulaciones() {
        return e3TieneDolorArticulaciones;
    }

    public void setE3TieneDolorArticulaciones(String e3TieneDolorArticulaciones) {
        this.e3TieneDolorArticulaciones = e3TieneDolorArticulaciones;
    }

    public String getE3TieneParalisis() {
        return e3TieneParalisis;
    }

    public void setE3TieneParalisis(String e3TieneParalisis) {
        this.e3TieneParalisis = e3TieneParalisis;
    }

    public String getE3TieneMareos() {
        return e3TieneMareos;
    }

    public void setE3TieneMareos(String e3TieneMareos) {
        this.e3TieneMareos = e3TieneMareos;
    }

    public String getE3TienePensamientoNublado() {
        return e3TienePensamientoNublado;
    }

    public void setE3TienePensamientoNublado(String e3TienePensamientoNublado) {
        this.e3TienePensamientoNublado = e3TienePensamientoNublado;
    }

    public String getE3TieneProblemasDormir() {
        return e3TieneProblemasDormir;
    }

    public void setE3TieneProblemasDormir(String e3TieneProblemasDormir) {
        this.e3TieneProblemasDormir = e3TieneProblemasDormir;
    }

    public String getE3TieneDepresion() {
        return e3TieneDepresion;
    }

    public void setE3TieneDepresion(String e3TieneDepresion) {
        this.e3TieneDepresion = e3TieneDepresion;
    }

    public String getE3TieneOtrosSintomas() {
        return e3TieneOtrosSintomas;
    }

    public void setE3TieneOtrosSintomas(String e3TieneOtrosSintomas) {
        this.e3TieneOtrosSintomas = e3TieneOtrosSintomas;
    }

    public String getE3cualesSintomas() {
        return e3cualesSintomas;
    }

    public void setE3cualesSintomas(String e3cualesSintomas) {
        this.e3cualesSintomas = e3cualesSintomas;
    }

    public String getE3SabeTiempoRecuperacion() {
        return e3SabeTiempoRecuperacion;
    }

    public void setE3SabeTiempoRecuperacion(String e3SabeTiempoRecuperacion) {
        this.e3SabeTiempoRecuperacion = e3SabeTiempoRecuperacion;
    }

    public String getE3TiempoRecuperacion() {
        return e3TiempoRecuperacion;
    }

    public void setE3TiempoRecuperacion(String e3TiempoRecuperacion) {
        this.e3TiempoRecuperacion = e3TiempoRecuperacion;
    }

    public String getE3TiempoRecuperacionEn() {
        return e3TiempoRecuperacionEn;
    }

    public void setE3TiempoRecuperacionEn(String e3TiempoRecuperacionEn) {
        this.e3TiempoRecuperacionEn = e3TiempoRecuperacionEn;
    }

    public String getE3SeveridadEnfermedad() {
        return e3SeveridadEnfermedad;
    }

    public void setE3SeveridadEnfermedad(String e3SeveridadEnfermedad) {
        this.e3SeveridadEnfermedad = e3SeveridadEnfermedad;
    }

    public String getE3TomoMedicamento() {
        return e3TomoMedicamento;
    }

    public void setE3TomoMedicamento(String e3TomoMedicamento) {
        this.e3TomoMedicamento = e3TomoMedicamento;
    }

    public String getE3QueMedicamento() {
        return e3QueMedicamento;
    }

    public void setE3QueMedicamento(String e3QueMedicamento) {
        this.e3QueMedicamento = e3QueMedicamento;
    }

    public String getE3OtroMedicamento() {
        return e3OtroMedicamento;
    }

    public void setE3OtroMedicamento(String e3OtroMedicamento) {
        this.e3OtroMedicamento = e3OtroMedicamento;
    }

    public String getE3OxigenoDomicilio() {
        return e3OxigenoDomicilio;
    }

    public void setE3OxigenoDomicilio(String e3OxigenoDomicilio) {
        this.e3OxigenoDomicilio = e3OxigenoDomicilio;
    }

    public String getE3TiempoOxigenoDom() {
        return e3TiempoOxigenoDom;
    }

    public void setE3TiempoOxigenoDom(String e3TiempoOxigenoDom) {
        this.e3TiempoOxigenoDom = e3TiempoOxigenoDom;
    }

    public String getPadeceEnfisema() {
        return padeceEnfisema;
    }

    public void setPadeceEnfisema(String padeceEnfisema) {
        this.padeceEnfisema = padeceEnfisema;
    }

    public String getPadeceAsma() {
        return padeceAsma;
    }

    public void setPadeceAsma(String padeceAsma) {
        this.padeceAsma = padeceAsma;
    }

    public String getPadeceDiabetes() {
        return padeceDiabetes;
    }

    public void setPadeceDiabetes(String padeceDiabetes) {
        this.padeceDiabetes = padeceDiabetes;
    }

    public String getPadeceEnfCoronaria() {
        return padeceEnfCoronaria;
    }

    public void setPadeceEnfCoronaria(String padeceEnfCoronaria) {
        this.padeceEnfCoronaria = padeceEnfCoronaria;
    }

    public String getPadecePresionAlta() {
        return padecePresionAlta;
    }

    public void setPadecePresionAlta(String padecePresionAlta) {
        this.padecePresionAlta = padecePresionAlta;
    }

    public String getPadeceEnfHigado() {
        return padeceEnfHigado;
    }

    public void setPadeceEnfHigado(String padeceEnfHigado) {
        this.padeceEnfHigado = padeceEnfHigado;
    }

    public String getPadeceEnfRenal() {
        return padeceEnfRenal;
    }

    public void setPadeceEnfRenal(String padeceEnfRenal) {
        this.padeceEnfRenal = padeceEnfRenal;
    }

    public String getPadeceInfartoDerrameCer() {
        return padeceInfartoDerrameCer;
    }

    public void setPadeceInfartoDerrameCer(String padeceInfartoDerrameCer) {
        this.padeceInfartoDerrameCer = padeceInfartoDerrameCer;
    }

    public String getPadeceCancer() {
        return padeceCancer;
    }

    public void setPadeceCancer(String padeceCancer) {
        this.padeceCancer = padeceCancer;
    }

    public String getPadeceCondicionInmuno() {
        return padeceCondicionInmuno;
    }

    public void setPadeceCondicionInmuno(String padeceCondicionInmuno) {
        this.padeceCondicionInmuno = padeceCondicionInmuno;
    }

    public String getPadeceEnfAutoinmune() {
        return padeceEnfAutoinmune;
    }

    public void setPadeceEnfAutoinmune(String padeceEnfAutoinmune) {
        this.padeceEnfAutoinmune = padeceEnfAutoinmune;
    }

    public String getPadeceDiscapacidadFis() {
        return padeceDiscapacidadFis;
    }

    public void setPadeceDiscapacidadFis(String padeceDiscapacidadFis) {
        this.padeceDiscapacidadFis = padeceDiscapacidadFis;
    }

    public String getPadeceCondPsicPsiq() {
        return padeceCondPsicPsiq;
    }

    public void setPadeceCondPsicPsiq(String padeceCondPsicPsiq) {
        this.padeceCondPsicPsiq = padeceCondPsicPsiq;
    }

    public String getPadeceOtraCondicion() {
        return padeceOtraCondicion;
    }

    public void setPadeceOtraCondicion(String padeceOtraCondicion) {
        this.padeceOtraCondicion = padeceOtraCondicion;
    }

    public String getQueOtraCondicion() {
        return queOtraCondicion;
    }

    public void setQueOtraCondicion(String queOtraCondicion) {
        this.queOtraCondicion = queOtraCondicion;
    }

    public String getFumado() {
        return fumado;
    }

    public void setFumado(String fumado) {
        this.fumado = fumado;
    }

    public String getFumadoCienCigarrillos() {
        return fumadoCienCigarrillos;
    }

    public void setFumadoCienCigarrillos(String fumadoCienCigarrillos) {
        this.fumadoCienCigarrillos = fumadoCienCigarrillos;
    }

    public String getE1FumadoPrevioEnfermedad() {
        return e1FumadoPrevioEnfermedad;
    }

    public void setE1FumadoPrevioEnfermedad(String e1FumadoPrevioEnfermedad) {
        this.e1FumadoPrevioEnfermedad = e1FumadoPrevioEnfermedad;
    }

    public String getE1FumaActualmente() {
        return e1FumaActualmente;
    }

    public void setE1FumaActualmente(String e1FumaActualmente) {
        this.e1FumaActualmente = e1FumaActualmente;
    }

    public String getE1Embarazada() {
        return e1Embarazada;
    }

    public void setE1Embarazada(String e1Embarazada) {
        this.e1Embarazada = e1Embarazada;
    }

    public String getE1RecuerdaSemanasEmb() {
        return e1RecuerdaSemanasEmb;
    }

    public void setE1RecuerdaSemanasEmb(String e1RecuerdaSemanasEmb) {
        this.e1RecuerdaSemanasEmb = e1RecuerdaSemanasEmb;
    }

    public Integer getE1SemanasEmbarazo() {
        return e1SemanasEmbarazo;
    }

    public void setE1SemanasEmbarazo(Integer e1SemanasEmbarazo) {
        this.e1SemanasEmbarazo = e1SemanasEmbarazo;
    }

    public String getE1FinalEmbarazo() {
        return e1FinalEmbarazo;
    }

    public void setE1FinalEmbarazo(String e1FinalEmbarazo) {
        this.e1FinalEmbarazo = e1FinalEmbarazo;
    }

    public String getE1OtroFinalEmbarazo() {
        return e1OtroFinalEmbarazo;
    }

    public void setE1OtroFinalEmbarazo(String e1OtroFinalEmbarazo) {
        this.e1OtroFinalEmbarazo = e1OtroFinalEmbarazo;
    }

    public String getE1DabaPecho() {
        return e1DabaPecho;
    }

    public void setE1DabaPecho(String e1DabaPecho) {
        this.e1DabaPecho = e1DabaPecho;
    }

    public String getTrabajadorSalud() {
        return trabajadorSalud;
    }

    public void setTrabajadorSalud(String trabajadorSalud) {
        this.trabajadorSalud = trabajadorSalud;
    }

    public String getPeriodoSintomas() {
        return periodoSintomas;
    }

    public void setPeriodoSintomas(String periodoSintomas) {
        this.periodoSintomas = periodoSintomas;
    }

    public String getE2FumadoPrevioEnfermedad() {
        return e2FumadoPrevioEnfermedad;
    }

    public void setE2FumadoPrevioEnfermedad(String e2FumadoPrevioEnfermedad) {
        this.e2FumadoPrevioEnfermedad = e2FumadoPrevioEnfermedad;
    }

    public String getE2FumaActualmente() {
        return e2FumaActualmente;
    }

    public void setE2FumaActualmente(String e2FumaActualmente) {
        this.e2FumaActualmente = e2FumaActualmente;
    }

    public String getE2Embarazada() {
        return e2Embarazada;
    }

    public void setE2Embarazada(String e2Embarazada) {
        this.e2Embarazada = e2Embarazada;
    }

    public String getE2RecuerdaSemanasEmb() {
        return e2RecuerdaSemanasEmb;
    }

    public void setE2RecuerdaSemanasEmb(String e2RecuerdaSemanasEmb) {
        this.e2RecuerdaSemanasEmb = e2RecuerdaSemanasEmb;
    }

    public Integer getE2SemanasEmbarazo() {
        return e2SemanasEmbarazo;
    }

    public void setE2SemanasEmbarazo(Integer e2SemanasEmbarazo) {
        this.e2SemanasEmbarazo = e2SemanasEmbarazo;
    }

    public String getE2FinalEmbarazo() {
        return e2FinalEmbarazo;
    }

    public void setE2FinalEmbarazo(String e2FinalEmbarazo) {
        this.e2FinalEmbarazo = e2FinalEmbarazo;
    }

    public String getE2OtroFinalEmbarazo() {
        return e2OtroFinalEmbarazo;
    }

    public void setE2OtroFinalEmbarazo(String e2OtroFinalEmbarazo) {
        this.e2OtroFinalEmbarazo = e2OtroFinalEmbarazo;
    }

    public String getE2DabaPecho() {
        return e2DabaPecho;
    }

    public void setE2DabaPecho(String e2DabaPecho) {
        this.e2DabaPecho = e2DabaPecho;
    }

    public String getE3FumadoPrevioEnfermedad() {
        return e3FumadoPrevioEnfermedad;
    }

    public void setE3FumadoPrevioEnfermedad(String e3FumadoPrevioEnfermedad) {
        this.e3FumadoPrevioEnfermedad = e3FumadoPrevioEnfermedad;
    }

    public String getE3FumaActualmente() {
        return e3FumaActualmente;
    }

    public void setE3FumaActualmente(String e3FumaActualmente) {
        this.e3FumaActualmente = e3FumaActualmente;
    }

    public String getE3Embarazada() {
        return e3Embarazada;
    }

    public void setE3Embarazada(String e3Embarazada) {
        this.e3Embarazada = e3Embarazada;
    }

    public String getE3RecuerdaSemanasEmb() {
        return e3RecuerdaSemanasEmb;
    }

    public void setE3RecuerdaSemanasEmb(String e3RecuerdaSemanasEmb) {
        this.e3RecuerdaSemanasEmb = e3RecuerdaSemanasEmb;
    }

    public Integer getE3SemanasEmbarazo() {
        return e3SemanasEmbarazo;
    }

    public void setE3SemanasEmbarazo(Integer e3SemanasEmbarazo) {
        this.e3SemanasEmbarazo = e3SemanasEmbarazo;
    }

    public String getE3FinalEmbarazo() {
        return e3FinalEmbarazo;
    }

    public void setE3FinalEmbarazo(String e3FinalEmbarazo) {
        this.e3FinalEmbarazo = e3FinalEmbarazo;
    }

    public String getE3OtroFinalEmbarazo() {
        return e3OtroFinalEmbarazo;
    }

    public void setE3OtroFinalEmbarazo(String e3OtroFinalEmbarazo) {
        this.e3OtroFinalEmbarazo = e3OtroFinalEmbarazo;
    }

    public String getE3DabaPecho() {
        return e3DabaPecho;
    }

    public void setE3DabaPecho(String e3DabaPecho) {
        this.e3DabaPecho = e3DabaPecho;
    }

    public String getEnfermoCovid19() {
        return enfermoCovid19;
    }

    public void setEnfermoCovid19(String enfermoCovid19) {
        this.enfermoCovid19 = enfermoCovid19;
    }

    public String getCuantasVecesEnfermo() {
        return cuantasVecesEnfermo;
    }

    public void setCuantasVecesEnfermo(String cuantasVecesEnfermo) {
        this.cuantasVecesEnfermo = cuantasVecesEnfermo;
    }

    public String getSabeEvento1() {
        return sabeEvento1;
    }

    public void setSabeEvento1(String sabeEvento1) {
        this.sabeEvento1 = sabeEvento1;
    }

    public Date getEvento1() {
        return evento1;
    }

    public void setEvento1(Date evento1) {
        this.evento1 = evento1;
    }

    public String getAnioEvento1() {
        return anioEvento1;
    }

    public void setAnioEvento1(String anioEvento1) {
        this.anioEvento1 = anioEvento1;
    }

    public String getMesEvento1() {
        return mesEvento1;
    }

    public void setMesEvento1(String mesEvento1) {
        this.mesEvento1 = mesEvento1;
    }

    public String getSabeEvento2() {
        return sabeEvento2;
    }

    public void setSabeEvento2(String sabeEvento2) {
        this.sabeEvento2 = sabeEvento2;
    }

    public Date getEvento2() {
        return evento2;
    }

    public void setEvento2(Date evento2) {
        this.evento2 = evento2;
    }

    public String getAnioEvento2() {
        return anioEvento2;
    }

    public void setAnioEvento2(String anioEvento2) {
        this.anioEvento2 = anioEvento2;
    }

    public String getMesEvento2() {
        return mesEvento2;
    }

    public void setMesEvento2(String mesEvento2) {
        this.mesEvento2 = mesEvento2;
    }

    public String getSabeEvento3() {
        return sabeEvento3;
    }

    public void setSabeEvento3(String sabeEvento3) {
        this.sabeEvento3 = sabeEvento3;
    }

    public Date getEvento3() {
        return evento3;
    }

    public void setEvento3(Date evento3) {
        this.evento3 = evento3;
    }

    public String getAnioEvento3() {
        return anioEvento3;
    }

    public void setAnioEvento3(String anioEvento3) {
        this.anioEvento3 = anioEvento3;
    }

    public String getMesEvento3() {
        return mesEvento3;
    }

    public void setMesEvento3(String mesEvento3) {
        this.mesEvento3 = mesEvento3;
    }

    public String getE1Febricula() {
        return e1Febricula;
    }

    public void setE1Febricula(String e1Febricula) {
        this.e1Febricula = e1Febricula;
    }

    public String getE1Fiebre() {
        return e1Fiebre;
    }

    public void setE1Fiebre(String e1Fiebre) {
        this.e1Fiebre = e1Fiebre;
    }

    public String getE1Escalofrio() {
        return e1Escalofrio;
    }

    public void setE1Escalofrio(String e1Escalofrio) {
        this.e1Escalofrio = e1Escalofrio;
    }

    public String getE1TemblorEscalofrio() {
        return e1TemblorEscalofrio;
    }

    public void setE1TemblorEscalofrio(String e1TemblorEscalofrio) {
        this.e1TemblorEscalofrio = e1TemblorEscalofrio;
    }

    public String getE1DolorMuscular() {
        return e1DolorMuscular;
    }

    public void setE1DolorMuscular(String e1DolorMuscular) {
        this.e1DolorMuscular = e1DolorMuscular;
    }

    public String getE1DolorArticular() {
        return e1DolorArticular;
    }

    public void setE1DolorArticular(String e1DolorArticular) {
        this.e1DolorArticular = e1DolorArticular;
    }

    public String getE1SecresionNasal() {
        return e1SecresionNasal;
    }

    public void setE1SecresionNasal(String e1SecresionNasal) {
        this.e1SecresionNasal = e1SecresionNasal;
    }

    public String getE1DolorGarganta() {
        return e1DolorGarganta;
    }

    public void setE1DolorGarganta(String e1DolorGarganta) {
        this.e1DolorGarganta = e1DolorGarganta;
    }

    public String getE1Tos() {
        return e1Tos;
    }

    public void setE1Tos(String e1Tos) {
        this.e1Tos = e1Tos;
    }

    public String getE1DificultadResp() {
        return e1DificultadResp;
    }

    public void setE1DificultadResp(String e1DificultadResp) {
        this.e1DificultadResp = e1DificultadResp;
    }

    public String getE1DolorPecho() {
        return e1DolorPecho;
    }

    public void setE1DolorPecho(String e1DolorPecho) {
        this.e1DolorPecho = e1DolorPecho;
    }

    public String getE1NauseasVomito() {
        return e1NauseasVomito;
    }

    public void setE1NauseasVomito(String e1NauseasVomito) {
        this.e1NauseasVomito = e1NauseasVomito;
    }

    public String getE1DolorCabeza() {
        return e1DolorCabeza;
    }

    public void setE1DolorCabeza(String e1DolorCabeza) {
        this.e1DolorCabeza = e1DolorCabeza;
    }

    public String getE1DolorAbdominal() {
        return e1DolorAbdominal;
    }

    public void setE1DolorAbdominal(String e1DolorAbdominal) {
        this.e1DolorAbdominal = e1DolorAbdominal;
    }

    public String getE1Diarrea() {
        return e1Diarrea;
    }

    public void setE1Diarrea(String e1Diarrea) {
        this.e1Diarrea = e1Diarrea;
    }

    public String getE1DificultadDormir() {
        return e1DificultadDormir;
    }

    public void setE1DificultadDormir(String e1DificultadDormir) {
        this.e1DificultadDormir = e1DificultadDormir;
    }

    public String getE1Debilidad() {
        return e1Debilidad;
    }

    public void setE1Debilidad(String e1Debilidad) {
        this.e1Debilidad = e1Debilidad;
    }

    public String getE1PerdidaOlfatoGusto() {
        return e1PerdidaOlfatoGusto;
    }

    public void setE1PerdidaOlfatoGusto(String e1PerdidaOlfatoGusto) {
        this.e1PerdidaOlfatoGusto = e1PerdidaOlfatoGusto;
    }

    public String getE1Mareo() {
        return e1Mareo;
    }

    public void setE1Mareo(String e1Mareo) {
        this.e1Mareo = e1Mareo;
    }

    public String getE1Sarpullido() {
        return e1Sarpullido;
    }

    public void setE1Sarpullido(String e1Sarpullido) {
        this.e1Sarpullido = e1Sarpullido;
    }

    public String getE1Desmayo() {
        return e1Desmayo;
    }

    public void setE1Desmayo(String e1Desmayo) {
        this.e1Desmayo = e1Desmayo;
    }

    public String getE1QuedoCama() {
        return e1QuedoCama;
    }

    public void setE1QuedoCama(String e1QuedoCama) {
        this.e1QuedoCama = e1QuedoCama;
    }

    public String getE2Febricula() {
        return e2Febricula;
    }

    public void setE2Febricula(String e2Febricula) {
        this.e2Febricula = e2Febricula;
    }

    public String getE2Fiebre() {
        return e2Fiebre;
    }

    public void setE2Fiebre(String e2Fiebre) {
        this.e2Fiebre = e2Fiebre;
    }

    public String getE2Escalofrio() {
        return e2Escalofrio;
    }

    public void setE2Escalofrio(String e2Escalofrio) {
        this.e2Escalofrio = e2Escalofrio;
    }

    public String getE2TemblorEscalofrio() {
        return e2TemblorEscalofrio;
    }

    public void setE2TemblorEscalofrio(String e2TemblorEscalofrio) {
        this.e2TemblorEscalofrio = e2TemblorEscalofrio;
    }

    public String getE2DolorMuscular() {
        return e2DolorMuscular;
    }

    public void setE2DolorMuscular(String e2DolorMuscular) {
        this.e2DolorMuscular = e2DolorMuscular;
    }

    public String getE2DolorArticular() {
        return e2DolorArticular;
    }

    public void setE2DolorArticular(String e2DolorArticular) {
        this.e2DolorArticular = e2DolorArticular;
    }

    public String getE2SecresionNasal() {
        return e2SecresionNasal;
    }

    public void setE2SecresionNasal(String e2SecresionNasal) {
        this.e2SecresionNasal = e2SecresionNasal;
    }

    public String getE2DolorGarganta() {
        return e2DolorGarganta;
    }

    public void setE2DolorGarganta(String e2DolorGarganta) {
        this.e2DolorGarganta = e2DolorGarganta;
    }

    public String getE2Tos() {
        return e2Tos;
    }

    public void setE2Tos(String e2Tos) {
        this.e2Tos = e2Tos;
    }

    public String getE2DificultadResp() {
        return e2DificultadResp;
    }

    public void setE2DificultadResp(String e2DificultadResp) {
        this.e2DificultadResp = e2DificultadResp;
    }

    public String getE2DolorPecho() {
        return e2DolorPecho;
    }

    public void setE2DolorPecho(String e2DolorPecho) {
        this.e2DolorPecho = e2DolorPecho;
    }

    public String getE2NauseasVomito() {
        return e2NauseasVomito;
    }

    public void setE2NauseasVomito(String e2NauseasVomito) {
        this.e2NauseasVomito = e2NauseasVomito;
    }

    public String getE2DolorCabeza() {
        return e2DolorCabeza;
    }

    public void setE2DolorCabeza(String e2DolorCabeza) {
        this.e2DolorCabeza = e2DolorCabeza;
    }

    public String getE2DolorAbdominal() {
        return e2DolorAbdominal;
    }

    public void setE2DolorAbdominal(String e2DolorAbdominal) {
        this.e2DolorAbdominal = e2DolorAbdominal;
    }

    public String getE2Diarrea() {
        return e2Diarrea;
    }

    public void setE2Diarrea(String e2Diarrea) {
        this.e2Diarrea = e2Diarrea;
    }

    public String getE2DificultadDormir() {
        return e2DificultadDormir;
    }

    public void setE2DificultadDormir(String e2DificultadDormir) {
        this.e2DificultadDormir = e2DificultadDormir;
    }

    public String getE2Debilidad() {
        return e2Debilidad;
    }

    public void setE2Debilidad(String e2Debilidad) {
        this.e2Debilidad = e2Debilidad;
    }

    public String getE2PerdidaOlfatoGusto() {
        return e2PerdidaOlfatoGusto;
    }

    public void setE2PerdidaOlfatoGusto(String e2PerdidaOlfatoGusto) {
        this.e2PerdidaOlfatoGusto = e2PerdidaOlfatoGusto;
    }

    public String getE2Mareo() {
        return e2Mareo;
    }

    public void setE2Mareo(String e2Mareo) {
        this.e2Mareo = e2Mareo;
    }

    public String getE2Sarpullido() {
        return e2Sarpullido;
    }

    public void setE2Sarpullido(String e2Sarpullido) {
        this.e2Sarpullido = e2Sarpullido;
    }

    public String getE2Desmayo() {
        return e2Desmayo;
    }

    public void setE2Desmayo(String e2Desmayo) {
        this.e2Desmayo = e2Desmayo;
    }

    public String getE2QuedoCama() {
        return e2QuedoCama;
    }

    public void setE2QuedoCama(String e2QuedoCama) {
        this.e2QuedoCama = e2QuedoCama;
    }

    public String getE3Febricula() {
        return e3Febricula;
    }

    public void setE3Febricula(String e3Febricula) {
        this.e3Febricula = e3Febricula;
    }

    public String getE3Fiebre() {
        return e3Fiebre;
    }

    public void setE3Fiebre(String e3Fiebre) {
        this.e3Fiebre = e3Fiebre;
    }

    public String getE3Escalofrio() {
        return e3Escalofrio;
    }

    public void setE3Escalofrio(String e3Escalofrio) {
        this.e3Escalofrio = e3Escalofrio;
    }

    public String getE3TemblorEscalofrio() {
        return e3TemblorEscalofrio;
    }

    public void setE3TemblorEscalofrio(String e3TemblorEscalofrio) {
        this.e3TemblorEscalofrio = e3TemblorEscalofrio;
    }

    public String getE3DolorMuscular() {
        return e3DolorMuscular;
    }

    public void setE3DolorMuscular(String e3DolorMuscular) {
        this.e3DolorMuscular = e3DolorMuscular;
    }

    public String getE3DolorArticular() {
        return e3DolorArticular;
    }

    public void setE3DolorArticular(String e3DolorArticular) {
        this.e3DolorArticular = e3DolorArticular;
    }

    public String getE3SecresionNasal() {
        return e3SecresionNasal;
    }

    public void setE3SecresionNasal(String e3SecresionNasal) {
        this.e3SecresionNasal = e3SecresionNasal;
    }

    public String getE3DolorGarganta() {
        return e3DolorGarganta;
    }

    public void setE3DolorGarganta(String e3DolorGarganta) {
        this.e3DolorGarganta = e3DolorGarganta;
    }

    public String getE3Tos() {
        return e3Tos;
    }

    public void setE3Tos(String e3Tos) {
        this.e3Tos = e3Tos;
    }

    public String getE3DificultadResp() {
        return e3DificultadResp;
    }

    public void setE3DificultadResp(String e3DificultadResp) {
        this.e3DificultadResp = e3DificultadResp;
    }

    public String getE3DolorPecho() {
        return e3DolorPecho;
    }

    public void setE3DolorPecho(String e3DolorPecho) {
        this.e3DolorPecho = e3DolorPecho;
    }

    public String getE3NauseasVomito() {
        return e3NauseasVomito;
    }

    public void setE3NauseasVomito(String e3NauseasVomito) {
        this.e3NauseasVomito = e3NauseasVomito;
    }

    public String getE3DolorCabeza() {
        return e3DolorCabeza;
    }

    public void setE3DolorCabeza(String e3DolorCabeza) {
        this.e3DolorCabeza = e3DolorCabeza;
    }

    public String getE3DolorAbdominal() {
        return e3DolorAbdominal;
    }

    public void setE3DolorAbdominal(String e3DolorAbdominal) {
        this.e3DolorAbdominal = e3DolorAbdominal;
    }

    public String getE3Diarrea() {
        return e3Diarrea;
    }

    public void setE3Diarrea(String e3Diarrea) {
        this.e3Diarrea = e3Diarrea;
    }

    public String getE3DificultadDormir() {
        return e3DificultadDormir;
    }

    public void setE3DificultadDormir(String e3DificultadDormir) {
        this.e3DificultadDormir = e3DificultadDormir;
    }

    public String getE3Debilidad() {
        return e3Debilidad;
    }

    public void setE3Debilidad(String e3Debilidad) {
        this.e3Debilidad = e3Debilidad;
    }

    public String getE3PerdidaOlfatoGusto() {
        return e3PerdidaOlfatoGusto;
    }

    public void setE3PerdidaOlfatoGusto(String e3PerdidaOlfatoGusto) {
        this.e3PerdidaOlfatoGusto = e3PerdidaOlfatoGusto;
    }

    public String getE3Mareo() {
        return e3Mareo;
    }

    public void setE3Mareo(String e3Mareo) {
        this.e3Mareo = e3Mareo;
    }

    public String getE3Sarpullido() {
        return e3Sarpullido;
    }

    public void setE3Sarpullido(String e3Sarpullido) {
        this.e3Sarpullido = e3Sarpullido;
    }

    public String getE3Desmayo() {
        return e3Desmayo;
    }

    public void setE3Desmayo(String e3Desmayo) {
        this.e3Desmayo = e3Desmayo;
    }

    public String getE3QuedoCama() {
        return e3QuedoCama;
    }

    public void setE3QuedoCama(String e3QuedoCama) {
        this.e3QuedoCama = e3QuedoCama;
    }

    public String getVacunadoCovid19() {
        return vacunadoCovid19;
    }

    public void setVacunadoCovid19(String vacunadoCovid19) {
        this.vacunadoCovid19 = vacunadoCovid19;
    }

    public String getMuestraTarjetaVac() {
        return muestraTarjetaVac;
    }

    public void setMuestraTarjetaVac(String muestraTarjetaVac) {
        this.muestraTarjetaVac = muestraTarjetaVac;
    }

    public String getSabeNombreVacuna() {
        return sabeNombreVacuna;
    }

    public void setSabeNombreVacuna(String sabeNombreVacuna) {
        this.sabeNombreVacuna = sabeNombreVacuna;
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public void setNombreVacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }

    public String getAnioVacuna() {
        return anioVacuna;
    }

    public void setAnioVacuna(String anioVacuna) {
        this.anioVacuna = anioVacuna;
    }

    public String getMesVacuna() {
        return mesVacuna;
    }

    public void setMesVacuna(String mesVacuna) {
        this.mesVacuna = mesVacuna;
    }

    public String getCuantasDosis() {
        return cuantasDosis;
    }

    public void setCuantasDosis(String cuantasDosis) {
        this.cuantasDosis = cuantasDosis;
    }

    public String getNombreDosis1() {
        return nombreDosis1;
    }

    public void setNombreDosis1(String nombreDosis1) {
        this.nombreDosis1 = nombreDosis1;
    }

    public String getOtraVacunaDosis1() {
        return otraVacunaDosis1;
    }

    public void setOtraVacunaDosis1(String otraVacunaDosis1) {
        this.otraVacunaDosis1 = otraVacunaDosis1;
    }

    public String getLoteDosis1() {
        return loteDosis1;
    }

    public void setLoteDosis1(String loteDosis1) {
        this.loteDosis1 = loteDosis1;
    }

    public Date getFechaDosis1() {
        return fechaDosis1;
    }

    public void setFechaDosis1(Date fechaDosis1) {
        this.fechaDosis1 = fechaDosis1;
    }

    public String getNombreDosis2() {
        return nombreDosis2;
    }

    public void setNombreDosis2(String nombreDosis2) {
        this.nombreDosis2 = nombreDosis2;
    }

    public String getOtraVacunaDosis2() {
        return otraVacunaDosis2;
    }

    public void setOtraVacunaDosis2(String otraVacunaDosis2) {
        this.otraVacunaDosis2 = otraVacunaDosis2;
    }

    public String getLoteDosis2() {
        return loteDosis2;
    }

    public void setLoteDosis2(String loteDosis2) {
        this.loteDosis2 = loteDosis2;
    }

    public Date getFechaDosis2() {
        return fechaDosis2;
    }

    public void setFechaDosis2(Date fechaDosis2) {
        this.fechaDosis2 = fechaDosis2;
    }

    public String getNombreDosis3() {
        return nombreDosis3;
    }

    public void setNombreDosis3(String nombreDosis3) {
        this.nombreDosis3 = nombreDosis3;
    }

    public String getOtraVacunaDosis3() {
        return otraVacunaDosis3;
    }

    public void setOtraVacunaDosis3(String otraVacunaDosis3) {
        this.otraVacunaDosis3 = otraVacunaDosis3;
    }

    public String getLoteDosis3() {
        return loteDosis3;
    }

    public void setLoteDosis3(String loteDosis3) {
        this.loteDosis3 = loteDosis3;
    }

    public Date getFechaDosis3() {
        return fechaDosis3;
    }

    public void setFechaDosis3(Date fechaDosis3) {
        this.fechaDosis3 = fechaDosis3;
    }

    public String getPresentoSintomasDosis1() {
        return presentoSintomasDosis1;
    }

    public void setPresentoSintomasDosis1(String presentoSintomasDosis1) {
        this.presentoSintomasDosis1 = presentoSintomasDosis1;
    }

    public String getDolorSitioDosis1() {
        return dolorSitioDosis1;
    }

    public void setDolorSitioDosis1(String dolorSitioDosis1) {
        this.dolorSitioDosis1 = dolorSitioDosis1;
    }

    public String getFiebreDosis1() {
        return fiebreDosis1;
    }

    public void setFiebreDosis1(String fiebreDosis1) {
        this.fiebreDosis1 = fiebreDosis1;
    }

    public String getCansancioDosis1() {
        return cansancioDosis1;
    }

    public void setCansancioDosis1(String cansancioDosis1) {
        this.cansancioDosis1 = cansancioDosis1;
    }

    public String getDolorCabezaDosis1() {
        return dolorCabezaDosis1;
    }

    public void setDolorCabezaDosis1(String dolorCabezaDosis1) {
        this.dolorCabezaDosis1 = dolorCabezaDosis1;
    }

    public String getDiarreaDosis1() {
        return diarreaDosis1;
    }

    public void setDiarreaDosis1(String diarreaDosis1) {
        this.diarreaDosis1 = diarreaDosis1;
    }

    public String getVomitoDosis1() {
        return vomitoDosis1;
    }

    public void setVomitoDosis1(String vomitoDosis1) {
        this.vomitoDosis1 = vomitoDosis1;
    }

    public String getDolorMuscularDosis1() {
        return dolorMuscularDosis1;
    }

    public void setDolorMuscularDosis1(String dolorMuscularDosis1) {
        this.dolorMuscularDosis1 = dolorMuscularDosis1;
    }

    public String getEscalofriosDosis1() {
        return escalofriosDosis1;
    }

    public void setEscalofriosDosis1(String escalofriosDosis1) {
        this.escalofriosDosis1 = escalofriosDosis1;
    }

    public String getReaccionAlergicaDosis1() {
        return reaccionAlergicaDosis1;
    }

    public void setReaccionAlergicaDosis1(String reaccionAlergicaDosis1) {
        this.reaccionAlergicaDosis1 = reaccionAlergicaDosis1;
    }

    public String getInfeccionSitioDosis1() {
        return infeccionSitioDosis1;
    }

    public void setInfeccionSitioDosis1(String infeccionSitioDosis1) {
        this.infeccionSitioDosis1 = infeccionSitioDosis1;
    }

    public String getNauseasDosis1() {
        return nauseasDosis1;
    }

    public void setNauseasDosis1(String nauseasDosis1) {
        this.nauseasDosis1 = nauseasDosis1;
    }

    public String getBultosDosis1() {
        return bultosDosis1;
    }

    public void setBultosDosis1(String bultosDosis1) {
        this.bultosDosis1 = bultosDosis1;
    }

    public String getOtrosDosis1() {
        return otrosDosis1;
    }

    public void setOtrosDosis1(String otrosDosis1) {
        this.otrosDosis1 = otrosDosis1;
    }

    public String getDesOtrosDosis1() {
        return desOtrosDosis1;
    }

    public void setDesOtrosDosis1(String desOtrosDosis1) {
        this.desOtrosDosis1 = desOtrosDosis1;
    }

    public String getPresentoSintomasDosis2() {
        return presentoSintomasDosis2;
    }

    public void setPresentoSintomasDosis2(String presentoSintomasDosis2) {
        this.presentoSintomasDosis2 = presentoSintomasDosis2;
    }

    public String getDolorSitioDosis2() {
        return dolorSitioDosis2;
    }

    public void setDolorSitioDosis2(String dolorSitioDosis2) {
        this.dolorSitioDosis2 = dolorSitioDosis2;
    }

    public String getFiebreDosis2() {
        return fiebreDosis2;
    }

    public void setFiebreDosis2(String fiebreDosis2) {
        this.fiebreDosis2 = fiebreDosis2;
    }

    public String getCansancioDosis2() {
        return cansancioDosis2;
    }

    public void setCansancioDosis2(String cansancioDosis2) {
        this.cansancioDosis2 = cansancioDosis2;
    }

    public String getDolorCabezaDosis2() {
        return dolorCabezaDosis2;
    }

    public void setDolorCabezaDosis2(String dolorCabezaDosis2) {
        this.dolorCabezaDosis2 = dolorCabezaDosis2;
    }

    public String getDiarreaDosis2() {
        return diarreaDosis2;
    }

    public void setDiarreaDosis2(String diarreaDosis2) {
        this.diarreaDosis2 = diarreaDosis2;
    }

    public String getVomitoDosis2() {
        return vomitoDosis2;
    }

    public void setVomitoDosis2(String vomitoDosis2) {
        this.vomitoDosis2 = vomitoDosis2;
    }

    public String getDolorMuscularDosis2() {
        return dolorMuscularDosis2;
    }

    public void setDolorMuscularDosis2(String dolorMuscularDosis2) {
        this.dolorMuscularDosis2 = dolorMuscularDosis2;
    }

    public String getEscalofriosDosis2() {
        return escalofriosDosis2;
    }

    public void setEscalofriosDosis2(String escalofriosDosis2) {
        this.escalofriosDosis2 = escalofriosDosis2;
    }

    public String getReaccionAlergicaDosis2() {
        return reaccionAlergicaDosis2;
    }

    public void setReaccionAlergicaDosis2(String reaccionAlergicaDosis2) {
        this.reaccionAlergicaDosis2 = reaccionAlergicaDosis2;
    }

    public String getInfeccionSitioDosis2() {
        return infeccionSitioDosis2;
    }

    public void setInfeccionSitioDosis2(String infeccionSitioDosis2) {
        this.infeccionSitioDosis2 = infeccionSitioDosis2;
    }

    public String getNauseasDosis2() {
        return nauseasDosis2;
    }

    public void setNauseasDosis2(String nauseasDosis2) {
        this.nauseasDosis2 = nauseasDosis2;
    }

    public String getBultosDosis2() {
        return bultosDosis2;
    }

    public void setBultosDosis2(String bultosDosis2) {
        this.bultosDosis2 = bultosDosis2;
    }

    public String getOtrosDosis2() {
        return otrosDosis2;
    }

    public void setOtrosDosis2(String otrosDosis2) {
        this.otrosDosis2 = otrosDosis2;
    }

    public String getDesOtrosDosis2() {
        return desOtrosDosis2;
    }

    public void setDesOtrosDosis2(String desOtrosDosis2) {
        this.desOtrosDosis2 = desOtrosDosis2;
    }

    public String getPresentoSintomasDosis3() {
        return presentoSintomasDosis3;
    }

    public void setPresentoSintomasDosis3(String presentoSintomasDosis3) {
        this.presentoSintomasDosis3 = presentoSintomasDosis3;
    }

    public String getDolorSitioDosis3() {
        return dolorSitioDosis3;
    }

    public void setDolorSitioDosis3(String dolorSitioDosis3) {
        this.dolorSitioDosis3 = dolorSitioDosis3;
    }

    public String getFiebreDosis3() {
        return fiebreDosis3;
    }

    public void setFiebreDosis3(String fiebreDosis3) {
        this.fiebreDosis3 = fiebreDosis3;
    }

    public String getCansancioDosis3() {
        return cansancioDosis3;
    }

    public void setCansancioDosis3(String cansancioDosis3) {
        this.cansancioDosis3 = cansancioDosis3;
    }

    public String getDolorCabezaDosis3() {
        return dolorCabezaDosis3;
    }

    public void setDolorCabezaDosis3(String dolorCabezaDosis3) {
        this.dolorCabezaDosis3 = dolorCabezaDosis3;
    }

    public String getDiarreaDosis3() {
        return diarreaDosis3;
    }

    public void setDiarreaDosis3(String diarreaDosis3) {
        this.diarreaDosis3 = diarreaDosis3;
    }

    public String getVomitoDosis3() {
        return vomitoDosis3;
    }

    public void setVomitoDosis3(String vomitoDosis3) {
        this.vomitoDosis3 = vomitoDosis3;
    }

    public String getDolorMuscularDosis3() {
        return dolorMuscularDosis3;
    }

    public void setDolorMuscularDosis3(String dolorMuscularDosis3) {
        this.dolorMuscularDosis3 = dolorMuscularDosis3;
    }

    public String getEscalofriosDosis3() {
        return escalofriosDosis3;
    }

    public void setEscalofriosDosis3(String escalofriosDosis3) {
        this.escalofriosDosis3 = escalofriosDosis3;
    }

    public String getReaccionAlergicaDosis3() {
        return reaccionAlergicaDosis3;
    }

    public void setReaccionAlergicaDosis3(String reaccionAlergicaDosis3) {
        this.reaccionAlergicaDosis3 = reaccionAlergicaDosis3;
    }

    public String getInfeccionSitioDosis3() {
        return infeccionSitioDosis3;
    }

    public void setInfeccionSitioDosis3(String infeccionSitioDosis3) {
        this.infeccionSitioDosis3 = infeccionSitioDosis3;
    }

    public String getNauseasDosis3() {
        return nauseasDosis3;
    }

    public void setNauseasDosis3(String nauseasDosis3) {
        this.nauseasDosis3 = nauseasDosis3;
    }

    public String getBultosDosis3() {
        return bultosDosis3;
    }

    public void setBultosDosis3(String bultosDosis3) {
        this.bultosDosis3 = bultosDosis3;
    }

    public String getOtrosDosis3() {
        return otrosDosis3;
    }

    public void setOtrosDosis3(String otrosDosis3) {
        this.otrosDosis3 = otrosDosis3;
    }

    public String getDesOtrosDosis3() {
        return desOtrosDosis3;
    }

    public void setDesOtrosDosis3(String desOtrosDosis3) {
        this.desOtrosDosis3 = desOtrosDosis3;
    }

    public String getCovid19PosteriorVacuna() {
        return covid19PosteriorVacuna;
    }

    public void setCovid19PosteriorVacuna(String covid19PosteriorVacuna) {
        this.covid19PosteriorVacuna = covid19PosteriorVacuna;
    }

    public String getSabeFechaEnfPostVac() {
        return sabeFechaEnfPostVac;
    }

    public void setSabeFechaEnfPostVac(String sabeFechaEnfPostVac) {
        this.sabeFechaEnfPostVac = sabeFechaEnfPostVac;
    }

    public Date getFechaEnfPostVac() {
        return fechaEnfPostVac;
    }

    public void setFechaEnfPostVac(Date fechaEnfPostVac) {
        this.fechaEnfPostVac = fechaEnfPostVac;
    }

    public String getAnioEnfPostVac() {
        return anioEnfPostVac;
    }

    public void setAnioEnfPostVac(String anioEnfPostVac) {
        this.anioEnfPostVac = anioEnfPostVac;
    }

    public String getMesEnfPostVac() {
        return mesEnfPostVac;
    }

    public void setMesEnfPostVac(String mesEnfPostVac) {
        this.mesEnfPostVac = mesEnfPostVac;
    }

    public String getE2RecuperadoCovid19() {
        return e2RecuperadoCovid19;
    }

    public void setE2RecuperadoCovid19(String e2RecuperadoCovid19) {
        this.e2RecuperadoCovid19 = e2RecuperadoCovid19;
    }

    public String getE3RecuperadoCovid19() {
        return e3RecuperadoCovid19;
    }

    public void setE3RecuperadoCovid19(String e3RecuperadoCovid19) {
        this.e3RecuperadoCovid19 = e3RecuperadoCovid19;
    }

    public String getFechaEventoEnfermoPostVac() {
        return fechaEventoEnfermoPostVac;
    }

    public void setFechaEventoEnfermoPostVac(String fechaEventoEnfermoPostVac) {
        this.fechaEventoEnfermoPostVac = fechaEventoEnfermoPostVac;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CuestionarioCovid19 that = (CuestionarioCovid19) o;

        if (!codigo.equals(that.codigo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    @Override
    public String toString() {
        return "CuestionarioCovid19{" +
                "codigo='" + codigo + '\'' +
                ", participante=" + participante +
                '}';
    }
}
