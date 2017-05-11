package ni.org.ics.estudios.appmovil.utils;

/**
 * Created by Miguel Salinas on 5/10/2017.
 * V1.0
 */
public class EncuestasDBConstants {

    //tabla EncuestaCasa
    public static final String ENCUESTA_CASA_TABLE = "encuestas_casa";

    //campos tabla EncuestaCasa
    public static final String casa = "codigo_casa";
    public static final String cantidadCuartos = "cantidadCuartos";
    public static final String cantidadCuartosDormir = "cantidadCuartosDormir";
    public static final String fechaEncuestas = "fechaEncuestas";
    public static final String encuestador = "encuestador";
    public static final String hrsSinServicioAgua = "hrsSinServicioAgua";
    public static final String ubicacionLlaveAgua = "ubicacionLlaveAgua";
    public static final String llaveCompartida = "llaveCompartida";
    public static final String almacenaAgua = "almacenaAgua";
    public static final String almacenaEnBarriles = "almacenaEnBarriles";
    public static final String almacenaEnTanques = "almacenaEnTanques";
    public static final String almacenaEnPilas = "almacenaEnPilas";
    public static final String almacenaOtrosRecipientes = "almacenaOtrosRecipientes";
    public static final String otrosRecipientes = "otrosRecipientes";
    public static final String numBarriles = "numBarriles";
    public static final String numTanques = "numTanques";
    public static final String numPilas = "numPilas";
    public static final String numOtrosRecipientes = "numOtrosRecipientes";
    public static final String barrilesTapados = "barrilesTapados";
    public static final String tanquesTapados = "tanquesTapados";
    public static final String pilasTapadas = "pilasTapadas";
    public static final String otrosRecipientesTapados = "otrosRecipientesTapados";
    public static final String barrilesConAbate = "barrilesConAbate";
    public static final String tanquesConAbate = "tanquesConAbate";
    public static final String pilasConAbate = "pilasConAbate";
    public static final String otrosRecipientesConAbate = "otrosRecipientesConAbate";
    public static final String ubicacionLavandero = "ubicacionLavandero";
    public static final String materialParedes = "materialParedes";
    public static final String materialPiso = "materialPiso";
    public static final String materialTecho = "materialTecho";
    public static final String otroMaterialParedes = "otroMaterialParedes";
    public static final String otroMaterialPiso = "otroMaterialPiso";
    public static final String otroMaterialTecho = "otroMaterialTecho";
    public static final String casaPropia = "casaPropia";
    public static final String tieneAbanico = "tieneAbanico";
    public static final String tieneTelevisor = "tieneTelevisor";
    public static final String tieneRefrigerador = "tieneRefrigerador";
    public static final String tienAireAcondicionado = "tienAireAcondicionado";
    public static final String aireAcondicionadoFuncionando = "aireAcondicionadoFuncionando";
    public static final String numAbanicos = "numAbanicos";
    public static final String numTelevisores = "numTelevisores";
    public static final String numRefrigeradores = "numRefrigeradores";
    public static final String tieneMoto = "tieneMoto";
    public static final String tieneCarro = "tieneCarro";
    public static final String tienMicrobus = "tienMicrobus";
    public static final String tieneCamioneta = "tieneCamioneta";
    public static final String tieneCamion = "tieneCamion";
    public static final String tieneOtroMedioTransAuto = "tieneOtroMedioTransAuto";
    public static final String otroMedioTransAuto = "otroMedioTransAuto";
    public static final String cocinaConLenia = "cocinaConLenia";
    public static final String ubicacionCocinaLenia = "ubicacionCocinaLenia";
    public static final String periodicidadCocinaLenia = "periodicidadCocinaLenia";
    public static final String numDiarioCocinaLenia = "numDiarioCocinaLenia";   //# de veces que cocina
    public static final String numSemanalCocinaLenia = "numSemanalCocinaLenia";  //# de veces que cocina semanalmente
    public static final String numQuincenalCocinaLenia = "numQuincenalCocinaLenia";    //# de veces que cocina quincenalmente
    public static final String numMensualCocinaLenia = "numMensualCocinaLenia";  //# de veces que cocina al mes
    public static final String tieneAnimales = "tieneAnimales";
    public static final String tieneGallinas = "tieneGallinas";
    public static final String tienePatos = "tienePatos";
    public static final String tieneCerdos = "tieneCerdos";
    public static final String cantidadGallinas = "cantidadGallinas";
    public static final String cantidadPatos = "cantidadPatos";
    public static final String cantidadCerdos = "cantidadCerdos";
    public static final String gallinasDentroCasa = "gallinasDentroCasa";
    public static final String patosDentroCasa = "patosDentroCasa";
    public static final String cerdosDentroCasa = "cerdosDentroCasa";
    public static final String personaFumaDentroCasa = "personaFumaDentroCasa";  //Alguna persona que no pertenece al estudio fuma dentro de la casa
    public static final String madreFuma = "madreFuma";
    public static final String padreFuma = "padreFuma";
    public static final String otrosFuman = "otrosFuman";
    public static final String cantidadOtrosFuman = "cantidadOtrosFuman";
    public static final String cantidadCigarrilosMadre = "cantidadCigarrilosMadre"; // diarios
    public static final String cantidadCigarrillosPadre = "cantidadCigarrillosPadre"; // diarios
    public static final String cantidadCigarrillosOtros = "cantidadCigarrillosOtros"; // diarios

    //crear tabla EncuestaCasa
    public static final String CREATE_ENCUESTA_CASA_TABLE = "create table if not exists "
            + ENCUESTA_CASA_TABLE + " ("
            + casa + " integer not null, "
            + cantidadCuartos  + " integer, "
            + cantidadCuartosDormir  + " integer, "
            + fechaEncuestas  + " date not null, "
            + encuestador  + " text, "
            + hrsSinServicioAgua  + " integer, "
            + ubicacionLlaveAgua  + " text, "
            + llaveCompartida  + " text, "
            + almacenaAgua  + " text, "
            + almacenaEnBarriles  + " text, "
            + almacenaEnTanques  + " text, "
            + almacenaEnPilas  + " text, "
            + almacenaOtrosRecipientes  + " text, "
            + otrosRecipientes  + " text, "
            + numBarriles  + " integer, "
            + numTanques  + " integer, "
            + numPilas  + " integer, "
            + numOtrosRecipientes  + " integer, "
            + barrilesTapados  + " text, "
            + tanquesTapados  + " text, "
            + pilasTapadas  + " text, "
            + otrosRecipientesTapados  + " text, "
            + barrilesConAbate  + " text, "
            + tanquesConAbate  + " text, "
            + pilasConAbate  + " text, "
            + otrosRecipientesConAbate  + " text, "
            + ubicacionLavandero  + " text, "
            + materialParedes  + " text, "
            + materialPiso  + " text, "
            + materialTecho  + " text, "
            + otroMaterialParedes  + " text, "
            + otroMaterialPiso  + " text, "
            + otroMaterialTecho  + " text, "
            + casaPropia  + " text, "
            + tieneAbanico  + " text, "
            + tieneTelevisor  + " text, "
            + tieneRefrigerador  + " text, "
            + tienAireAcondicionado  + " text, "
            + aireAcondicionadoFuncionando  + " text, "
            + numAbanicos  + " integer, "
            + numTelevisores  + " integer, "
            + numRefrigeradores  + " integer, "
            + tieneMoto  + " text, "
            + tieneCarro  + " text, "
            + tienMicrobus  + " text, "
            + tieneCamioneta  + " text, "
            + tieneCamion  + " text, "
            + tieneOtroMedioTransAuto  + " text, "
            + otroMedioTransAuto  + " text, "
            + cocinaConLenia  + " text, "
            + ubicacionCocinaLenia  + " text, "
            + periodicidadCocinaLenia  + " text, "
            + numDiarioCocinaLenia  + " integer, "
            + numSemanalCocinaLenia  + " integer, "
            + numQuincenalCocinaLenia  + " integer, "
            + numMensualCocinaLenia  + " integer, "
            + tieneAnimales  + " text, "
            + tieneGallinas  + " text, "
            + tienePatos  + " text, "
            + tieneCerdos  + " text, "
            + cantidadGallinas  + " integer, "
            + cantidadPatos  + " integer, "
            + cantidadCerdos  + " integer, "
            + gallinasDentroCasa  + " text, "
            + patosDentroCasa  + " text, "
            + cerdosDentroCasa  + " text, "
            + personaFumaDentroCasa  + " text, "
            + madreFuma  + " text, "
            + padreFuma  + " text, "
            + otrosFuman  + " text, "
            + cantidadOtrosFuman  + " integer, "
            + cantidadCigarrilosMadre  + " integer, "
            + cantidadCigarrillosPadre  + " integer, "
            + cantidadCigarrillosOtros  + " integer, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + casa + "));";

    //tabla EncuestaDatosPartoBB
    public static final String ENCUESTA_PARTOBB_TABLE = "encuestas_partobb";

    //campos tabla EncuestaDatosPartoBB
    public static final String participante = "codigo_participante";
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
    public static final String otrorecurso1 = "otrorecurso1";
    public static final String otrorecurso2 = "otrorecurso2";

    //crear tabla EncuestaDatosPartoBB
    public static final String CREATE_ENCUESTA_PARTOBB_TABLE = "create table if not exists "
            + ENCUESTA_PARTOBB_TABLE + " ("
            + participante + " integer not null, "
            + tipoParto  + " text, "
            + tiempoEmb_sndr  + " text, "
            + tiempoEmbSemana  + " integer, "
            + docMedTiempoEmb_sn  + " text, "
            + docMedTiempoEmb  + " text, "
            + otroDocMedTiempoEmb  + " text, "
            + fum  + " text, "
            + sg  + " integer, "
            + fumFueraRango_sn  + " text, "
            + fumFueraRango_razon  + " text, "
            + edadGest  + " integer, "
            + docMedEdadGest_sn  + " text, "
            + docMedEdadGest  + " text, "
            + OtroDocMedEdadGest  + " text, "
            + prematuro_sndr  + " text, "
            + pesoBB_sndr  + " text, "
            + pesoBB  + " text, "
            + docMedPesoBB_sn  + " text, "
            + docMedPesoBB  + " text, "
            + otroDocMedPesoBB  + " text, "
            + otrorecurso1  + " integer, "
            + otrorecurso2  + " integer, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + participante + "));";

    //tabla EncuestaLactanciaMaterna
    public static final String ENCUESTA_LACTANCIAMAT_TABLE = "encuestas_lactancia_materna";

    //campos tabla EncuestaLactanciaMaterna
    public static final String edad = "edad";
    public static final String dioPecho = "dioPecho";
    public static final String tiemPecho = "tiemPecho";
    public static final String mesDioPecho = "mesDioPecho";
    public static final String pechoExc = "pechoExc";
    public static final String pechoExcAntes = "pechoExcAntes";
    public static final String tiempPechoExcAntes = "tiempPechoExcAntes";
    public static final String mestPechoExc = "mestPechoExc";
    public static final String formAlim = "formAlim";
    public static final String otraAlim = "otraAlim";
    public static final String edadLiqDistPecho = "edadLiqDistPecho";
    public static final String mesDioLiqDisPecho = "mesDioLiqDisPecho";
    public static final String edadLiqDistLeche = "edadLiqDistLeche";
    public static final String mesDioLiqDisLeche = "mesDioLiqDisLeche";
    public static final String edAlimSolidos = "edAlimSolidos";
    public static final String mesDioAlimSol = "mesDioAlimSol";

    //crar tabla EncuestaLactanciaMaterna
    public static final String CREATE_ENCUESTA_LACTANCIAMAT_TABLE = "create table if not exists "
            + ENCUESTA_LACTANCIAMAT_TABLE + " ("
            + participante + " integer not null, "
            + edad  + " integer, "
            + dioPecho  + " integer, "
            + tiemPecho  + " integer, "
            + mesDioPecho  + " integer, "
            + pechoExc  + " integer, "
            + pechoExcAntes  + " integer, "
            + tiempPechoExcAntes  + " integer, "
            + mestPechoExc  + " integer, "
            + formAlim  + " integer, "
            + otraAlim  + " text, "
            + edadLiqDistPecho  + " integer, "
            + mesDioLiqDisPecho  + " integer, "
            + edadLiqDistLeche  + " integer, "
            + mesDioLiqDisLeche  + " integer, "
            + edAlimSolidos  + " integer, "
            + mesDioAlimSol  + " integer, "
            + otrorecurso1  + " integer, "
            + otrorecurso2  + " integer, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + participante + "));";

    //tabla EncuestaParticipante
    public static final String ENCUESTA_PARTICIPANTE_TABLE = "encuestas_participante";

    //campos tabla EncuestaParticipante
    public static final String emancipado = "emancipado";
    public static final String motivoEmacipacion = "motivoEmacipacion";
    public static final String otroMotivoEmancipacion = "otroMotivoEmancipacion";
    public static final String estaEmbarazada = "estaEmbarazada";
    public static final String semanasEmbarazo = "semanasEmbarazo";
    public static final String esAlfabeto = "esAlfabeto";
    public static final String nivelEducacion = "nivelEducacion";
    public static final String trabaja = "trabaja";
    public static final String tipoTrabajo = "tipoTrabajo";
    public static final String ocupacionActual = "ocupacionActual";
    //Niño
    public static final String vaNinoEscuela = "vaNinoEscuela";
    public static final String gradoCursa = "gradoCursa";
    public static final String turno = "turno";
    public static final String nombreCentroEstudio = "nombreCentroEstudio";
    public static final String dondeCuidanNino = "dondeCuidanNino";
    public static final String ninoTrabaja = "ninoTrabaja";
    public static final String ocupacionActualNino = "ocupacionActualNino";
    public static final String cantNinosLugarCuidan = "cantNinosLugarCuidan"; //Cuántos niños aproximadamente hay en el lugar que cuidan al niño
    //Datos generales de los padres y factores de hacinamiento
    public static final String conQuienViveNino = "conQuienViveNino";
    public static final String descOtroViveNino = "descOtroViveNino";
    public static final String padreEnEstudio = "padreEnEstudio";
    public static final String codigoPadreEstudio = "codigoPadreEstudio";
    public static final String padreAlfabeto = "padreAlfabeto";
    public static final String nivelEducacionPadre = "nivelEducacionPadre";
    public static final String trabajaPadre = "trabajaPadre";
    public static final String tipoTrabajoPadre = "tipoTrabajoPadre";
    public static final String madreEnEstudio = "madreEnEstudio";
    public static final String codigoMadreEstudio = "codigoMadreEstudio";
    public static final String madreAlfabeto = "madreAlfabeto";
    public static final String nivelEducacionMadre = "nivelEducacionMadre";
    public static final String trabajaMadre = "trabajaMadre";
    public static final String tipoTrabajoMadre = "tipoTrabajoMadre";
    // Para todos los participantes
    public static final String fuma = "fuma";
    public static final String periodicidadFuma = "periodicidadFuma";
    public static final String cantidadCigarrillos = "cantidadCigarrillos";
    public static final String fumaDentroCasa = "fumaDentroCasa";
    //Antecedentes de Salud
    public static final String tuberculosisPulmonarActual = "tuberculosisPulmonarActual";
    public static final String fechaDiagnosticoTubPulActual = "fechaDiagnosticoTubPulActual";
    public static final String tomaTratamientoTubPulActual = "tomaTratamientoTubPulActual";
    public static final String completoTratamientoTubPulActual = "completoTratamientoTubPulActual";
    public static final String tuberculosisPulmonarPasado = "tuberculosisPulmonarPasado";
    public static final String fechaDiagnosticoTubPulPasado = "fechaDiagnosticoTubPulPasado";
    public static final String tomaTratamientoTubPulPasado = "tomaTratamientoTubPulPasado";
    public static final String completoTratamientoTubPulPasado = "completoTratamientoTubPulPasado";
    public static final String alergiaRespiratoria = "alergiaRespiratoria";
    public static final String cardiopatia = "cardiopatia";
    public static final String enfermedadPulmonarOC = "enfermedadPulmonarOC"; // enfermedad pulmonar obstructiva crónica
    public static final String diabetes = "diabetes";
    public static final String presionAlta = "presionAlta";
    public static final String asma = "asma";
    public static final String silvidoRespirarPechoApretado = "silvidoRespirarPechoApretado";
    public static final String tosSinFiebreResfriado = "tosSinFiebreResfriado";
    public static final String usaInhaladoresSpray = "usaInhaladoresSpray";
    public static final String crisisAsma = "crisisAsma";
    public static final String cantidadCrisisAsma = "cantidadCrisisAsma";
    public static final String vecesEnfermoEnfermedadesRes = "vecesEnfermoEnfermedadesRes"; //veces enfermo en el último año por cuadros o enfermedades respiratorias
    public static final String otrasEnfermedades = "otrasEnfermedades";
    public static final String descOtrasEnfermedades = "descOtrasEnfermedades";
    public static final String vacunaInfluenza = "vacunaInfluenza";
    public static final String anioVacunaInfluenza = "anioVacunaInfluenza";

    //crear tabla EncuestaParticipante
    public static final String CREATE_ENCUESTA_PARTICIPANTE_TABLE = "create table if not exists "
            + ENCUESTA_PARTICIPANTE_TABLE + " ("
            + participante + " integer not null, "
            + emancipado  + " text, "
            + motivoEmacipacion  + " text, "
            + otroMotivoEmancipacion  + " text, "
            + estaEmbarazada  + " text, "
            + semanasEmbarazo  + " integer, "
            + esAlfabeto  + " text, "
            + nivelEducacion  + " text, "
            + trabaja  + " text, "
            + tipoTrabajo  + " text, "
            + ocupacionActual  + " text, "
            //Niño
            + vaNinoEscuela  + " text, "
            + gradoCursa  + " text, "
            + turno  + " text, "
            + nombreCentroEstudio  + " text, "
            + dondeCuidanNino  + " text, "
            + ninoTrabaja  + " text, "
            + ocupacionActualNino  + " text, "
            + cantNinosLugarCuidan  + " integer, "
            + conQuienViveNino  + " text, "
            + descOtroViveNino  + " text, "
            + padreEnEstudio  + " text, "
            + codigoPadreEstudio  + " integer, "
            + padreAlfabeto  + " text, "
            + nivelEducacionPadre  + " text, "
            + trabajaPadre  + " text, "
            + tipoTrabajoPadre  + " text, "
            + madreEnEstudio  + " text, "
            + codigoMadreEstudio  + " integer, "
            + madreAlfabeto  + " text, "
            + nivelEducacionMadre  + " text, "
            + trabajaMadre  + " text, "
            + tipoTrabajoMadre  + " text, "
            + fuma  + " text, "
            + periodicidadFuma  + " text, "
            + cantidadCigarrillos  + " integer, "
            + fumaDentroCasa  + " text, "
            + tuberculosisPulmonarActual  + " text, "
            + fechaDiagnosticoTubPulActual  + " text, "
            + tomaTratamientoTubPulActual  + " text, "
            + completoTratamientoTubPulActual  + " text, "
            + tuberculosisPulmonarPasado  + " text, "
            + fechaDiagnosticoTubPulPasado  + " text, "
            + tomaTratamientoTubPulPasado  + " text, "
            + completoTratamientoTubPulPasado  + " text, "
            + alergiaRespiratoria  + " text, "
            + cardiopatia  + " text, "
            + enfermedadPulmonarOC  + " text, "
            + diabetes  + " text, "
            + presionAlta  + " text, "
            + asma  + " text, "
            + silvidoRespirarPechoApretado  + " text, "
            + tosSinFiebreResfriado  + " text, "
            + usaInhaladoresSpray  + " text, "
            + crisisAsma  + " text, "
            + cantidadCrisisAsma  + " integer, "
            + vecesEnfermoEnfermedadesRes  + " integer, "
            + otrasEnfermedades  + " text, "
            + descOtrasEnfermedades  + " text, "
            + vacunaInfluenza  + " text, "
            + anioVacunaInfluenza  + " integer, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + participante + "));";

    //tabla EncuestaPesoTalla
    public static final String ENCUESTA_PESOTALLA_TABLE = "encuestas_pesotalla";

    //campos tabla EncuestaPesoTalla
    public static final String peso1 = "peso1";
    public static final String peso2 = "peso2";
    public static final String peso3 = "peso3";
    public static final String talla1 = "talla1";
    public static final String talla2 = "talla2";
    public static final String talla3 = "talla3";
    public static final String imc1 = "imc1";
    public static final String imc2 = "imc2";
    public static final String imc3 = "imc3";
    public static final String difPeso = "difPeso";
    public static final String difTalla = "difTalla";

    //crear tabla EncuestaPesoTalla
    public static final String CREATE_ENCUESTA_PESOTALLA_TABLE = "create table if not exists "
            + ENCUESTA_PESOTALLA_TABLE + " ("
            + participante + " integer not null, "
            + peso1 + " real, "
            + peso2 + " real, "
            + peso3 + " real, "
            + talla1 + " real, "
            + talla2 + " real, "
            + talla3 + " real, "
            + imc1 + " real, "
            + imc2 + " real, "
            + imc3 + " real, "
            + difPeso + " real, "
            + difTalla + " real, "
            + otrorecurso1 + " integer, "
            + otrorecurso2 + " integer, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + participante + "));";

    //tabla EncuestaVacuna
    public static final String ENCUESTA_VACUNA_TABLE = "encuestas_vacuna";

    //campos tabla EncuestaVacuna
    public static final String vacuna = "vacuna";
    public static final String fechaVac = "fechaVac";
    public static final String tipovacuna = "tipovacuna";
    public static final String tarjetaSN = "tarjetaSN";
    public static final String ndosis = "ndosis";
    public static final String fechaInf1 = "fechaInf1";
    public static final String fechaInf2 = "fechaInf2";
    public static final String fechaInf3 = "fechaInf3";
    public static final String fechaInf4 = "fechaInf4";
    public static final String fechaInf5 = "fechaInf5";
    public static final String fechaInf6 = "fechaInf6";
    public static final String fechaInf7 = "fechaInf7";
    public static final String fechaInf8 = "fechaInf8";
    public static final String fechaInf9 = "fechaInf9";
    public static final String fechaInf10 = "fechaInf10";

    //creat tabla EncuestaVacuna
    public static final String CREATE_ENCUESTA_VACUNA_TABLE = "create table if not exists "
            + ENCUESTA_VACUNA_TABLE + " ("
            + participante + " integer not null, "
            + vacuna + " integer, "
            + fechaVac + " date, "
            + tipovacuna + " text, "
            + tarjetaSN + " integer, "
            + ndosis + " integer, "
            + fechaInf1 + " date, "
            + fechaInf2 + " date, "
            + fechaInf3 + " date, "
            + fechaInf4 + " date, "
            + fechaInf5 + " date, "
            + fechaInf6 + " date, "
            + fechaInf7 + " date, "
            + fechaInf8 + " date, "
            + fechaInf9 + " date, "
            + fechaInf10 + " date, "
            + otrorecurso1 + " integer, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + participante + "));";

}
