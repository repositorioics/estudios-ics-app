package ni.org.ics.estudios.appmovil.entomologia.constants;

import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

public class EntomologiaBConstants {
    //tabla CuestionarioHogar
    public static final String ENTO_CUESTIONARIO_HOGAR_TABLE = "ento_cuestionario_hogar";
    //campos tabla CuestionarioHogar
    public static final String codigoEncuesta = "codigoEncuesta";

    //00.	Código de la casa
    public static final String codigoCasa = "codigoCasa";

    //01.	¿Quién está contestando éste cuestionario?
    public static final String quienContesta = "quienContesta";
    public static final String quienContestaOtro = "quienContestaOtro";

    //02.	Me podría decir ¿Qué edad tiene usted?
    public static final String edadContest = "edadContest";

    //03.	¿Cuál es su último año aprobado?
    public static final String escolaridadContesta = "escolaridadContesta";

    //04.	¿Cuánto tiempo tienen de vivir en este barrio?
    public static final String tiempoVivirBarrio = "tiempoVivirBarrio";

    //05.	 ¿Cuántas personas viven en esta casa?
    public static final String cuantasPersonasViven = "cuantasPersonasViven";

    //06.	¿Qué edad tienen las niñas y mujeres que viven en esta casa, me    puede decir sus edades de menor a mayor?
    public static final String edadesFemenino = "edadesFemenino";

    //07.	¿Qué edad tienen los niños y hombres que viven en esta casa, me puede decir sus edades de menor a mayor?
    public static final String edadesMasculino = "edadesMasculino";

    //08.	De todas las personas que me mencionó, ¿Alguna de ellas usó mosquitero para dormir el día de ayer?
    public static final String usaronMosquitero = "usaronMosquitero";
    public static final String quienesUsaronMosquitero = "quienesUsaronMosquitero";

    //09.	De todas las personas que me mencionó, ¿Alguna de ellas usó repelente en su piel, para protegerse de los zancudos el día de ayer?
    public static final String usaronRepelente = "usaronRepelente";
    public static final String quienesUsaronRepelente = "quienesUsaronRepelente";

    //10.	¿Conoce Usted las larvas o clavitos de los zancudos?
    public static final String conoceLarvas = "conoceLarvas";

    //11.	¿Alguien les ha visitado para enseñarles como buscar y eliminar las larvas o clavitos de los zancudos?
    public static final String alguienVisEliminarLarvas = "alguienVisEliminarLarvas";

    //12.	¿Quién?
    public static final String quienVisEliminarLarvas = "quienVisEliminarLarvas";
    public static final String quienVisEliminarLarvasOtro = "quienVisEliminarLarvasOtro";

    //13. ¿Alguien de esta casa dedica tiempo para buscar y eliminar criaderos de zancudos aquí en su casa?
    public static final String alguienDedicaElimLarvasCasa = "alguienDedicaElimLarvasCasa";

    //14.	¿Quién?
    public static final String quienDedicaElimLarvasCasa = "quienDedicaElimLarvasCasa";

    //15. ¿Cada cuánto tiempo buscan y eliminan criaderos de zancudos aquí en su casa?
    public static final String tiempoElimanCridaros = "tiempoElimanCridaros";

    //16. ¿Hay bastante zancudos aquí en su casa?
    public static final String hayBastanteZancudos = "hayBastanteZancudos";

    //17.	¿Qué hace falta en esta casa para evitar los criaderos de zancudos?
    public static final String queFaltaCasaEvitarZancudos = "queFaltaCasaEvitarZancudos";
    public static final String queFaltaCasaEvitarZancudosOtros = "queFaltaCasaEvitarZancudosOtros";

    //18.	El mes pasado, gastaron dinero en compra de productos para evitar los Zancudos?
    public static final String gastaronDineroProductos = "gastaronDineroProductos";

    //19.	¿Qué productos compraron?
    public static final String queProductosCompraron = "queProductosCompraron";
    public static final String queProductosCompraronOtros = "queProductosCompraronOtros";

    //20.	¿Cuánto gastaron?
    public static final String cuantoGastaron = "cuantoGastaron";

    //21	¿Cuándo fue la última vez que el MINSA visitó su casa para aplicar BTI en sus recipientes con agua?
    public static final String ultimaVisitaMinsaBTI = "ultimaVisitaMinsaBTI";

    //22	¿Cuándo fue la última vez que el MINSA fumigó su casa?
    public static final String ultimaVezMinsaFumigo = "ultimaVezMinsaFumigo";

    //23 ¿Qué tanto riesgo hay en su casa de enfermar por el virus del dengue?
    public static final String riesgoCasaDengue = "riesgoCasaDengue";

    //24.	¿Hay problemas con el abastecimiento de agua en este barrio?
    public static final String problemasAbastecimiento = "problemasAbastecimiento";

    //25.	¿Cada cuánto se les va el agua?
    public static final String cadaCuantoVaAgua = "cadaCuantoVaAgua";
    public static final String cadaCuantoVaAguaOtro = "cadaCuantoVaAguaOtro";

    //26.	¿Cuantas horas al día se les va?
    public static final String horasSinAguaDia = "horasSinAguaDia";

    //27.	¿Qué hacen con la basura del hogar?
    public static final String queHacenBasuraHogar = "queHacenBasuraHogar";
    public static final String queHacenBasuraHogarOtro = "queHacenBasuraHogarOtro";

    //28.	¿Qué tanto riesgo hay en este barrio de enfermar por el virus del dengue?
    public static final String riesgoBarrioDengue = "riesgoBarrioDengue";

    //29.	¿En los últimos tres meses, en este barrio han realizado acciones para eliminar criaderos de zancudos del barrio?
    public static final String accionesCriaderosBarrio = "accionesCriaderosBarrio";

    //30.	¿Qué acciones realizaron?
    public static final String queAcciones = "queAcciones";
    public static final String queAccionesOtro = "queAccionesOtro";

    //31.	Alguien de la casa participó en esa actividad?
    public static final String alguienParticipo = "alguienParticipo";

    //32 Quién?
    public static final String quienParticipo = "quienParticipo";

    //33.	¿Cuál es el mayor criadero de Zancudos de este barrio?
    public static final String mayorCriaderoBarrio = "mayorCriaderoBarrio";

    public static final String anio = "anio";

    //crear tabla CuestionarioHogar
    public static final String CREATE_ENTO_CUESTIONARIO_HOGAR_TABLE = "create table if not exists "
            + ENTO_CUESTIONARIO_HOGAR_TABLE + " ("
            + codigoEncuesta + " text not null, "
            + codigoCasa + " integer, "
            + quienContesta + " text, "
            + quienContestaOtro + " text, "
            + edadContest + " text, "
            + escolaridadContesta + " text, "
            + tiempoVivirBarrio + " text, "
            + cuantasPersonasViven + " integer, "
            + usaronMosquitero + " text, "
            + quienesUsaronMosquitero + " text, "
            + usaronRepelente + " text, "
            + quienesUsaronRepelente + " text, "
            + conoceLarvas + " text, "
            + alguienVisEliminarLarvas + " text, "
            + quienVisEliminarLarvas + " text, "
            + quienVisEliminarLarvasOtro + " text, "
            + alguienDedicaElimLarvasCasa + " text, "
            + quienDedicaElimLarvasCasa + " text, "
            + tiempoElimanCridaros + " text, "
            + hayBastanteZancudos + " text, "
            + queFaltaCasaEvitarZancudos + " text, "
            + queFaltaCasaEvitarZancudosOtros + " text, "
            + gastaronDineroProductos + " text, "
            + queProductosCompraron + " text, "
            + queProductosCompraronOtros + " text, "
            + cuantoGastaron + " text, "
            + ultimaVisitaMinsaBTI + " text, "
            + ultimaVezMinsaFumigo + " text, "
            + riesgoCasaDengue + " text, "
            + problemasAbastecimiento + " text, "
            + cadaCuantoVaAgua + " text, "
            + cadaCuantoVaAguaOtro + " text, "
            + horasSinAguaDia + " integer, "
            + queHacenBasuraHogar + " text, "
            + queHacenBasuraHogarOtro + " text, "
            + riesgoBarrioDengue + " text, "
            + accionesCriaderosBarrio + " text, "
            + queAcciones + " text, "
            + queAccionesOtro + " text, "
            + mayorCriaderoBarrio + " text, "
            + edadesFemenino + " text, "
            + edadesMasculino + " text, "
            + alguienParticipo + " text, "
            + quienParticipo + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + anio + " text,"
            + "primary key (" + codigoEncuesta + "));";


    /*****BULK INSERT****/
    public static final String INSERT_ENTO_CUESTIONARIO_HOGAR_TABLE = "insert into "
            + ENTO_CUESTIONARIO_HOGAR_TABLE + " ("
            + codigoEncuesta + " , "
            + codigoCasa + " , "
            + quienContesta + " , "
            + quienContestaOtro + " , "
            + edadContest + " , "
            + escolaridadContesta + " , "
            + tiempoVivirBarrio + " , "
            + cuantasPersonasViven + " , "
            + usaronMosquitero + " , "
            + quienesUsaronMosquitero + " , "
            + usaronRepelente + " , "
            + quienesUsaronRepelente + " , "
            + conoceLarvas + " , "
            + alguienVisEliminarLarvas + " , "
            + quienVisEliminarLarvas + " , "
            + quienVisEliminarLarvasOtro + " , "
            + alguienDedicaElimLarvasCasa + " , "
            + quienDedicaElimLarvasCasa + " , "
            + tiempoElimanCridaros + " , "
            + hayBastanteZancudos + " , "
            + queFaltaCasaEvitarZancudos + " , "
            + queFaltaCasaEvitarZancudosOtros + " , "
            + gastaronDineroProductos + " , "
            + queProductosCompraron + " , "
            + queProductosCompraronOtros + " , "
            + cuantoGastaron + " , "
            + ultimaVisitaMinsaBTI + " , "
            + ultimaVezMinsaFumigo + " , "
            + riesgoCasaDengue + " , "
            + problemasAbastecimiento + " , "
            + cadaCuantoVaAgua + " , "
            + cadaCuantoVaAguaOtro + " , "
            + horasSinAguaDia + " , "
            + queHacenBasuraHogar + " , "
            + queHacenBasuraHogarOtro + " , "
            + riesgoBarrioDengue + " , "
            + accionesCriaderosBarrio + " , "
            + queAcciones + " , "
            + queAccionesOtro + " , "
            + mayorCriaderoBarrio + " , "
            + edadesFemenino + " , "
            + edadesMasculino + " , "
            + alguienParticipo + " , "
            + quienParticipo + " , "
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado + " , "
            + anio
            + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    //tabla CuestionarioHogarPoblacion
    public static final String ENTO_CUESTIONARIO_HOGAR_POB_TABLE = "ento_cuestionario_hogar_pob";
    //campos tabla CuestionarioHogarPoblacion
    public static final String codigoPoblacion = "codigoPoblacion";
    public static final String codificado = "codificado";
    public static final String edad = "edad";
    public static final String sexo = "sexo";

    //crear tabla CuestionarioHogarPoblacion
    public static final String CREATE_ENTO_CUESTIONARIO_HOGAR_POB_TABLE = "create table if not exists "
            + ENTO_CUESTIONARIO_HOGAR_POB_TABLE + " ("
            + codigoPoblacion + " text not null, "
            + codigoEncuesta + " text not null, "
            + codificado + " text not null, "
            + edad + " text not null, "
            + sexo + " text not null, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigoPoblacion + "));";

    public static final String INSERT_ENTO_CUESTIONARIO_HOGAR_POB_TABLE = "insert into "
            + ENTO_CUESTIONARIO_HOGAR_POB_TABLE + " ("
            + codigoPoblacion + ","
            + codigoEncuesta + " , "
            + codificado + " , "
            + edad + " , "
            + sexo + " , "
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.deviceId + ","
            + MainDBConstants.estado
            + ") values(?,?,?,?,?,?,?,?,?,?)";


    //tabla CuestionarioPuntoClave
    public static final String ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE = "ento_cuestionario_punto_clave";
    //campos tabla CuestionarioPuntoClave
    public static final String codigoCuestionario = "codigoCuestionario";
    public static final String fechaCuestionario = "fechaCuestionario";
    public static final String barrio = "barrio";
    public static final String nombrePuntoClave = "nombrePuntoClave";
    public static final String direccionPuntoClave = "direccionPuntoClave";
    public static final String tipoPuntoClave = "tipoPuntoClave";
    public static final String tipoPuntoProductividad = "tipoPuntoProductividad";
    public static final String tipoPuntoProductividadOtro = "tipoPuntoProductividadOtro";
    public static final String tipoPuntoAglomeracion = "tipoPuntoAglomeracion";
    public static final String tipoPuntoAglomeracionOtro = "tipoPuntoAglomeracionOtro";
    public static final String cuantasPersonasReunen = "cuantasPersonasReunen";
    public static final String cuantosDiasSemanaReunen = "cuantosDiasSemanaReunen";
    public static final String horaInicioReunion = "horaInicioReunion";
    public static final String horaFinReunion = "horaFinReunion";
    public static final String puntoGps = "puntoGps";
    public static final String latitud = "latitud";
    public static final String longitud = "longitud";
    public static final String tipoIngresoCodigoSitio = "tipoIngresoCodigoSitio";
    public static final String codigoSitio = "codigoSitio";
    public static final String hayAmbientePERI = "hayAmbientePERI";
    public static final String horaCapturaPERI = "horaCapturaPERI";
    public static final String humedadRelativaPERI = "humedadRelativaPERI";
    public static final String temperaturaPERI = "temperaturaPERI";
    public static final String tipoIngresoCodigoPERI = "tipoIngresoCodigoPERI";
    public static final String codigoPERI = "codigoPERI";

    public static final String hayAmbienteINTRA = "hayAmbienteINTRA";
    public static final String horaCapturaINTRA = "horaCapturaINTRA";
    public static final String humedadRelativaINTRA = "humedadRelativaINTRA";
    public static final String temperaturaINTRA = "temperaturaINTRA";

    public static final String tipoIngresoCodigoINTRA = "tipoIngresoCodigoINTRA";
    public static final String codigoINTRA = "codigoINTRA";
    public static final String nombrePersonaContesta = "nombrePersonaContesta";

    //crear tabla CuestionarioPuntoClave
    public static final String CREATE_ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE = "create table if not exists "
            + ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE + " ("
            + codigoCuestionario + " text not null, "
            + fechaCuestionario  + " date, "
            + barrio  + " integer, "
            + nombrePuntoClave  + " text, "
            + direccionPuntoClave  + " text, "
            + tipoPuntoClave  + " text, "
            + tipoPuntoProductividad  + " text, "
            + tipoPuntoProductividadOtro  + " text, "
            + tipoPuntoAglomeracion  + " text, "
            + tipoPuntoAglomeracionOtro  + " text, "
            + cuantasPersonasReunen  + " integer, "
            + cuantosDiasSemanaReunen  + " integer, "
            + horaInicioReunion  + " text, "
            + horaFinReunion  + " text, "
            + puntoGps  + " text, "
            + latitud  + " double, "
            + longitud  + " double, "
            + tipoIngresoCodigoSitio  + " text, "
            + codigoSitio  + " text, "
            + hayAmbientePERI + " text, "
            + horaCapturaPERI + " text, "
            + humedadRelativaPERI + " double, "
            + temperaturaPERI + " double, "
            + tipoIngresoCodigoPERI  + " text, "
            + codigoPERI  + " text, "
            + hayAmbienteINTRA + " text, "
            + horaCapturaINTRA + " text, "
            + humedadRelativaINTRA + " double, "
            + temperaturaINTRA + " double, "
            + tipoIngresoCodigoINTRA  + " text, "
            + codigoINTRA  + " text, "
            + nombrePersonaContesta  + " text, "
            + ConstantsDB.ID_INSTANCIA + " integer not null,"
            + ConstantsDB.FILE_PATH + " text not null,"
            + ConstantsDB.STATUS + " text not null, "
            + ConstantsDB.WHEN_UPDATED + " text not null, "
            + ConstantsDB.START  + " text, "
            + ConstantsDB.END  + " text, "
            + ConstantsDB.DEVICE_ID  + " text, "
            + ConstantsDB.SIM_SERIAL + " text, "
            + ConstantsDB.PHONE_NUMBER  + " text, "
            + ConstantsDB.TODAY  + " date, "
            + ConstantsDB.USUARIO  + " text, "
            + ConstantsDB.DELETED  + " boolean, "
            + "primary key (" + codigoCuestionario + "));";
}
