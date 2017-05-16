package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import android.content.res.Resources;

/**
 * Created by Miguel Salinas on 5/12/2017.
 * V1.0
 */
public class EncuestaCasaFormLabels {

    protected String cuantoCuartos; //1
    protected String cuartosDormir; //2
    protected String horasSinAgua; //6
    protected String llaveAgua; //7
    protected String llaveCompartida;//7.1
    protected String almacenaAgua;//8
    protected String almacenaEnBarriles; //8.1
    protected String almacenaEnTanques;
    protected String almacenaEnPilas;
    protected String almacenaOtrosRecipientes;
    protected String otrosRecipientes;
    protected String numBarriles; //8.2
    protected String numTanques;
    protected String numPilas;
    protected String numOtrosRecipientes;
    protected String barrilesTapados; //8.3
    protected String tanquesTapados;
    protected String pilasTapadas;
    protected String otrosRecipientesTapados;
    protected String barrilesConAbate; //8.4
    protected String tanquesConAbate;
    protected String pilasConAbate;
    protected String otrosRecipientesConAbate;
    protected String ubicacionLavandero; //9
    protected String materialParedes; //10
    protected String materialPiso; //11
    protected String materialTecho; //12
    protected String otroMaterialParedes;
    protected String otroMaterialPiso;
    protected String otroMaterialTecho;
    protected String casaPropia; //13
    protected String tieneAbanico; //14
    protected String tieneTelevisor;
    protected String tieneRefrigerador;
    protected String tienAireAcondicionado;
    protected String aireAcondicionadoFuncionando;
    protected String numAbanicos;
    protected String numTelevisores;
    protected String numRefrigeradores;
    protected String aireAcondicionadoFuncionandoHint;
    protected String tieneMoto; //15
    protected String tieneCarro;
    protected String tienMicrobus;
    protected String tieneCamioneta;
    protected String tieneCamion;
    protected String tieneOtroMedioTransAuto;
    protected String otroMedioTransAuto;
    protected String cocinaConLenia;//16
    protected String ubicacionCocinaLenia;
    protected String periodicidadCocinaLenia;
    protected String numDiarioCocinaLenia;
    protected String numSemanalCocinaLenia;
    protected String numQuincenalCocinaLenia;
    protected String numMensualCocinaLenia;
    protected String tieneAnimales; //17
    protected String tieneGallinas;
    protected String tienePatos;
    protected String tieneCerdos;
    protected String cantidadGallinas;
    protected String cantidadPatos;
    protected String cantidadCerdos;
    protected String gallinasDentroCasa;
    protected String patosDentroCasa;
    protected String cerdosDentroCasa;
    protected String personaFumaDentroCasa; //18
    protected String madreFuma;
    protected String padreFuma;
    protected String otrosFuman;
    protected String cantidadOtrosFuman;
    protected String cantidadCigarrilosMadre;
    protected String cantidadCigarrillosPadre;
    protected String cantidadCigarrillosOtros;

    protected String horasSinAguaHint;
    protected String almacenaEnBarrilesHint;
    protected String almacenaEnTanquesHint;
    protected String almacenaEnPilasHint;
    protected String almacenaOtrosRecipientesHint;
    protected String otrosRecipientesHint;
    protected String numBarrilesHint;
    protected String numTanquesHint;
    protected String numPilasHint;
    protected String numOtrosRecipientesHint;
    protected String barrilesTapadosHint;
    protected String tanquesTapadosHint;
    protected String pilasTapadasHint;
    protected String otrosRecipientesTapadosHint;
    protected String barrilesConAbateHint;
    protected String tanquesConAbateHint;
    protected String pilasConAbateHint;
    protected String otrosRecipientesConAbateHint;
    protected String llaveCompartidaHint;//7.1
    protected String ubicacionCocinaLeniaHint;
    protected String numDiarioCocinaLeniaHint;
    protected String numSemanalCocinaLeniaHint;
    protected String numQuincenalCocinaLeniaHint;
    protected String numMensualCocinaLeniaHint;
    protected String madreFumaHint;
    protected String padreFumaHint;
    protected String otrosFumanHint;
    protected String cantidadCigarrilosMadreHint;
    protected String cantidadCigarrillosPadreHint;
    protected String cantidadCigarrillosOtrosHint;
    protected String cantidadOtrosFumanHint;

    public EncuestaCasaFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        cuantoCuartos = res.getString(R.string.cuantoCuartos); //1
        cuartosDormir = res.getString(R.string.cuartosDormir); //2
        horasSinAgua = res.getString(R.string.horasSinAgua); //6
        llaveAgua = res.getString(R.string.llaveAgua); //7
        llaveCompartida = res.getString(R.string.llaveCompartida);//7.1
        almacenaAgua = res.getString(R.string.almacenaAgua);//8
        almacenaEnBarriles = res.getString(R.string.almacenaEnBarriles); //8.1
        almacenaEnTanques = res.getString(R.string.almacenaEnTanques);
        almacenaEnPilas = res.getString(R.string.almacenaEnPilas);
        almacenaOtrosRecipientes = res.getString(R.string.almacenaOtrosRecipientes);
        otrosRecipientes = res.getString(R.string.otrosRecipientes);
        numBarriles = res.getString(R.string.numBarriles); //8.2
        numTanques = res.getString(R.string.numTanques);
        numPilas = res.getString(R.string.numPilas);
        numOtrosRecipientes = res.getString(R.string.numOtrosRecipientes);
        barrilesTapados = res.getString(R.string.barrilesTapados); //8.3
        tanquesTapados = res.getString(R.string.tanquesTapados);
        pilasTapadas = res.getString(R.string.pilasTapadas);
        otrosRecipientesTapados = res.getString(R.string.otrosRecipientesTapados);
        barrilesConAbate = res.getString(R.string.barrilesConAbate); //8.4
        tanquesConAbate = res.getString(R.string.tanquesConAbate);
        pilasConAbate = res.getString(R.string.pilasConAbate);
        otrosRecipientesConAbate = res.getString(R.string.otrosRecipientesConAbate);
        ubicacionLavandero = res.getString(R.string.ubicacionLavandero); //9
        materialParedes = res.getString(R.string.materialParedes); //10
        materialPiso = res.getString(R.string.materialPiso); //11
        materialTecho = res.getString(R.string.materialTecho); //12
        otroMaterialParedes = res.getString(R.string.otroMaterialParedes);
        otroMaterialPiso = res.getString(R.string.otroMaterialPiso);
        otroMaterialTecho = res.getString(R.string.otroMaterialTecho);
        casaPropia = res.getString(R.string.casaPropia); //13
        tieneAbanico = res.getString(R.string.tieneAbanico); //14
        tieneTelevisor = res.getString(R.string.tieneTelevisor);
        tieneRefrigerador = res.getString(R.string.tieneRefrigerador);
        tienAireAcondicionado = res.getString(R.string.tienAireAcondicionado);
        aireAcondicionadoFuncionando = res.getString(R.string.aireAcondicionadoFuncionando);
        numAbanicos = res.getString(R.string.numAbanicos);
        numTelevisores = res.getString(R.string.numTelevisores);
        numRefrigeradores = res.getString(R.string.numRefrigeradores);
        tieneMoto = res.getString(R.string.tieneMoto); //15
        tieneCarro = res.getString(R.string.tieneCarro);
        tienMicrobus = res.getString(R.string.tienMicrobus);
        tieneCamioneta = res.getString(R.string.tieneCamioneta);
        tieneCamion = res.getString(R.string.tieneCamion);
        tieneOtroMedioTransAuto = res.getString(R.string.tieneOtroMedioTransAuto);
        otroMedioTransAuto = res.getString(R.string.otroMedioTransAuto);
        cocinaConLenia = res.getString(R.string.cocinaConLenia);//16
        ubicacionCocinaLenia = res.getString(R.string.ubicacionCocinaLenia);
        periodicidadCocinaLenia = res.getString(R.string.periodicidadCocinaLenia);
        numDiarioCocinaLenia = res.getString(R.string.numDiarioCocinaLenia);
        numSemanalCocinaLenia = res.getString(R.string.numSemanalCocinaLenia);
        numQuincenalCocinaLenia = res.getString(R.string.numQuincenalCocinaLenia);
        numMensualCocinaLenia = res.getString(R.string.numMensualCocinaLenia);
        tieneAnimales = res.getString(R.string.tieneAnimales); //17
        tieneGallinas = res.getString(R.string.tieneGallinas);
        tienePatos = res.getString(R.string.tienePatos);
        tieneCerdos = res.getString(R.string.tieneCerdos);
        cantidadGallinas = res.getString(R.string.cantidadGallinas);
        cantidadPatos = res.getString(R.string.cantidadPatos);
        cantidadCerdos = res.getString(R.string.cantidadCerdos);
        gallinasDentroCasa = res.getString(R.string.gallinasDentroCasa);
        patosDentroCasa = res.getString(R.string.patosDentroCasa);
        cerdosDentroCasa = res.getString(R.string.cerdosDentroCasa);
        personaFumaDentroCasa = res.getString(R.string.personaFumaDentroCasa); //18
        madreFuma = res.getString(R.string.madreFuma);
        padreFuma = res.getString(R.string.padreFuma);
        otrosFuman = res.getString(R.string.otrosFuman);
        cantidadOtrosFuman = res.getString(R.string.cantidadOtrosFuman);
        cantidadCigarrilosMadre = res.getString(R.string.cantidadCigarrilosMadre);
        cantidadCigarrillosPadre = res.getString(R.string.cantidadCigarrillosPadre);
        cantidadCigarrillosOtros = res.getString(R.string.cantidadCigarrillosOtros);

        horasSinAguaHint = res.getString(R.string.horasSinAguaHint);
        almacenaEnBarrilesHint = res.getString(R.string.almacenaEnBarrilesHint);
        almacenaEnTanquesHint = res.getString(R.string.almacenaEnTanquesHint);
        almacenaEnPilasHint = res.getString(R.string.almacenaEnPilasHint);
        almacenaOtrosRecipientesHint = res.getString(R.string.almacenaOtrosRecipientesHint);
        otrosRecipientesHint = res.getString(R.string.otrosRecipientesHint);
        numBarrilesHint = res.getString(R.string.numBarrilesHint);
        numTanquesHint = res.getString(R.string.numTanquesHint);
        numPilasHint = res.getString(R.string.numPilasHint);
        numOtrosRecipientesHint = res.getString(R.string.numOtrosRecipientesHint);
        barrilesTapadosHint = res.getString(R.string.barrilesTapadosHint);
        tanquesTapadosHint = res.getString(R.string.tanquesTapadosHint);
        pilasTapadasHint = res.getString(R.string.pilasTapadasHint);
        otrosRecipientesTapadosHint = res.getString(R.string.otrosRecipientesTapadosHint);
        barrilesConAbateHint = res.getString(R.string.barrilesConAbateHint);
        tanquesConAbateHint = res.getString(R.string.tanquesConAbateHint);
        pilasConAbateHint = res.getString(R.string.pilasConAbateHint);
        otrosRecipientesConAbateHint = res.getString(R.string.otrosRecipientesConAbateHint);
        aireAcondicionadoFuncionandoHint = res.getString(R.string.aireAcondicionadoFuncionandoHint);
        llaveCompartidaHint = res.getString(R.string.llaveCompartidaHint);//7.1
        ubicacionCocinaLeniaHint = res.getString(R.string.ubicacionCocinaLeniaHint);
        numDiarioCocinaLeniaHint = res.getString(R.string.numDiarioCocinaLeniaHint);
        numSemanalCocinaLeniaHint = res.getString(R.string.numSemanalCocinaLeniaHint);
        numQuincenalCocinaLeniaHint = res.getString(R.string.numQuincenalCocinaLeniaHint);
        numMensualCocinaLeniaHint = res.getString(R.string.numMensualCocinaLeniaHint);
        madreFumaHint = res.getString(R.string.madreFumaHint);
        padreFumaHint = res.getString(R.string.padreFumaHint);
        otrosFumanHint = res.getString(R.string.otrosFumanHint);
        cantidadCigarrilosMadreHint = res.getString(R.string.cantidadCigarrilosMadreHint);
        cantidadCigarrillosPadreHint = res.getString(R.string.cantidadCigarrillosPadreHint);
        cantidadOtrosFumanHint = res.getString(R.string.cantidadOtrosFumanHint);
        cantidadCigarrillosOtrosHint = res.getString(R.string.cantidadCigarrillosOtrosHint);
    }


    public String getCuantoCuartos() {
        return cuantoCuartos;
    }

    public void setCuantoCuartos(String cuantoCuartos) {
        this.cuantoCuartos = cuantoCuartos;
    }

    public String getCuartosDormir() {
        return cuartosDormir;
    }

    public void setCuartosDormir(String cuartosDormir) {
        this.cuartosDormir = cuartosDormir;
    }

    public String getHorasSinAgua() {
        return horasSinAgua;
    }

    public void setHorasSinAgua(String horasSinAgua) {
        this.horasSinAgua = horasSinAgua;
    }

    public String getLlaveAgua() {
        return llaveAgua;
    }

    public void setLlaveAgua(String llaveAgua) {
        this.llaveAgua = llaveAgua;
    }

    public String getLlaveCompartida() {
        return llaveCompartida;
    }

    public void setLlaveCompartida(String llaveCompartida) {
        this.llaveCompartida = llaveCompartida;
    }

    public String getAlmacenaAgua() {
        return almacenaAgua;
    }

    public void setAlmacenaAgua(String almacenaAgua) {
        this.almacenaAgua = almacenaAgua;
    }

    public String getAlmacenaEnBarriles() {
        return almacenaEnBarriles;
    }

    public void setAlmacenaEnBarriles(String almacenaEnBarriles) {
        this.almacenaEnBarriles = almacenaEnBarriles;
    }

    public String getAlmacenaEnTanques() {
        return almacenaEnTanques;
    }

    public void setAlmacenaEnTanques(String almacenaEnTanques) {
        this.almacenaEnTanques = almacenaEnTanques;
    }

    public String getAlmacenaEnPilas() {
        return almacenaEnPilas;
    }

    public void setAlmacenaEnPilas(String almacenaEnPilas) {
        this.almacenaEnPilas = almacenaEnPilas;
    }

    public String getAlmacenaOtrosRecipientes() {
        return almacenaOtrosRecipientes;
    }

    public void setAlmacenaOtrosRecipientes(String almacenaOtrosRecipientes) {
        this.almacenaOtrosRecipientes = almacenaOtrosRecipientes;
    }

    public String getOtrosRecipientes() {
        return otrosRecipientes;
    }

    public void setOtrosRecipientes(String otrosRecipientes) {
        this.otrosRecipientes = otrosRecipientes;
    }

    public String getNumBarriles() {
        return numBarriles;
    }

    public void setNumBarriles(String numBarriles) {
        this.numBarriles = numBarriles;
    }

    public String getNumTanques() {
        return numTanques;
    }

    public void setNumTanques(String numTanques) {
        this.numTanques = numTanques;
    }

    public String getNumPilas() {
        return numPilas;
    }

    public void setNumPilas(String numPilas) {
        this.numPilas = numPilas;
    }

    public String getNumOtrosRecipientes() {
        return numOtrosRecipientes;
    }

    public void setNumOtrosRecipientes(String numOtrosRecipientes) {
        this.numOtrosRecipientes = numOtrosRecipientes;
    }

    public String getBarrilesTapados() {
        return barrilesTapados;
    }

    public void setBarrilesTapados(String barrilesTapados) {
        this.barrilesTapados = barrilesTapados;
    }

    public String getTanquesTapados() {
        return tanquesTapados;
    }

    public void setTanquesTapados(String tanquesTapados) {
        this.tanquesTapados = tanquesTapados;
    }

    public String getPilasTapadas() {
        return pilasTapadas;
    }

    public void setPilasTapadas(String pilasTapadas) {
        this.pilasTapadas = pilasTapadas;
    }

    public String getOtrosRecipientesTapados() {
        return otrosRecipientesTapados;
    }

    public void setOtrosRecipientesTapados(String otrosRecipientesTapados) {
        this.otrosRecipientesTapados = otrosRecipientesTapados;
    }

    public String getBarrilesConAbate() {
        return barrilesConAbate;
    }

    public void setBarrilesConAbate(String barrilesConAbate) {
        this.barrilesConAbate = barrilesConAbate;
    }

    public String getTanquesConAbate() {
        return tanquesConAbate;
    }

    public void setTanquesConAbate(String tanquesConAbate) {
        this.tanquesConAbate = tanquesConAbate;
    }

    public String getPilasConAbate() {
        return pilasConAbate;
    }

    public void setPilasConAbate(String pilasConAbate) {
        this.pilasConAbate = pilasConAbate;
    }

    public String getOtrosRecipientesConAbate() {
        return otrosRecipientesConAbate;
    }

    public void setOtrosRecipientesConAbate(String otrosRecipientesConAbate) {
        this.otrosRecipientesConAbate = otrosRecipientesConAbate;
    }

    public String getUbicacionLavandero() {
        return ubicacionLavandero;
    }

    public void setUbicacionLavandero(String ubicacionLavandero) {
        this.ubicacionLavandero = ubicacionLavandero;
    }

    public String getMaterialParedes() {
        return materialParedes;
    }

    public void setMaterialParedes(String materialParedes) {
        this.materialParedes = materialParedes;
    }

    public String getMaterialPiso() {
        return materialPiso;
    }

    public void setMaterialPiso(String materialPiso) {
        this.materialPiso = materialPiso;
    }

    public String getMaterialTecho() {
        return materialTecho;
    }

    public void setMaterialTecho(String materialTecho) {
        this.materialTecho = materialTecho;
    }

    public String getOtroMaterialParedes() {
        return otroMaterialParedes;
    }

    public void setOtroMaterialParedes(String otroMaterialParedes) {
        this.otroMaterialParedes = otroMaterialParedes;
    }

    public String getOtroMaterialPiso() {
        return otroMaterialPiso;
    }

    public void setOtroMaterialPiso(String otroMaterialPiso) {
        this.otroMaterialPiso = otroMaterialPiso;
    }

    public String getOtroMaterialTecho() {
        return otroMaterialTecho;
    }

    public void setOtroMaterialTecho(String otroMaterialTecho) {
        this.otroMaterialTecho = otroMaterialTecho;
    }

    public String getCasaPropia() {
        return casaPropia;
    }

    public void setCasaPropia(String casaPropia) {
        this.casaPropia = casaPropia;
    }

    public String getTieneAbanico() {
        return tieneAbanico;
    }

    public void setTieneAbanico(String tieneAbanico) {
        this.tieneAbanico = tieneAbanico;
    }

    public String getTieneTelevisor() {
        return tieneTelevisor;
    }

    public void setTieneTelevisor(String tieneTelevisor) {
        this.tieneTelevisor = tieneTelevisor;
    }

    public String getTieneRefrigerador() {
        return tieneRefrigerador;
    }

    public void setTieneRefrigerador(String tieneRefrigerador) {
        this.tieneRefrigerador = tieneRefrigerador;
    }

    public String getTienAireAcondicionado() {
        return tienAireAcondicionado;
    }

    public void setTienAireAcondicionado(String tienAireAcondicionado) {
        this.tienAireAcondicionado = tienAireAcondicionado;
    }

    public String getAireAcondicionadoFuncionando() {
        return aireAcondicionadoFuncionando;
    }

    public void setAireAcondicionadoFuncionando(String aireAcondicionadoFuncionando) {
        this.aireAcondicionadoFuncionando = aireAcondicionadoFuncionando;
    }

    public String getNumAbanicos() {
        return numAbanicos;
    }

    public void setNumAbanicos(String numAbanicos) {
        this.numAbanicos = numAbanicos;
    }

    public String getNumTelevisores() {
        return numTelevisores;
    }

    public void setNumTelevisores(String numTelevisores) {
        this.numTelevisores = numTelevisores;
    }

    public String getNumRefrigeradores() {
        return numRefrigeradores;
    }

    public void setNumRefrigeradores(String numRefrigeradores) {
        this.numRefrigeradores = numRefrigeradores;
    }

    public String getAireAcondicionadoFuncionandoHint() {
        return aireAcondicionadoFuncionandoHint;
    }

    public void setAireAcondicionadoFuncionandoHint(String aireAcondicionadoFuncionandoHint) {
        this.aireAcondicionadoFuncionandoHint = aireAcondicionadoFuncionandoHint;
    }

    public String getTieneMoto() {
        return tieneMoto;
    }

    public void setTieneMoto(String tieneMoto) {
        this.tieneMoto = tieneMoto;
    }

    public String getTieneCarro() {
        return tieneCarro;
    }

    public void setTieneCarro(String tieneCarro) {
        this.tieneCarro = tieneCarro;
    }

    public String getTienMicrobus() {
        return tienMicrobus;
    }

    public void setTienMicrobus(String tienMicrobus) {
        this.tienMicrobus = tienMicrobus;
    }

    public String getTieneCamioneta() {
        return tieneCamioneta;
    }

    public void setTieneCamioneta(String tieneCamioneta) {
        this.tieneCamioneta = tieneCamioneta;
    }

    public String getTieneCamion() {
        return tieneCamion;
    }

    public void setTieneCamion(String tieneCamion) {
        this.tieneCamion = tieneCamion;
    }

    public String getTieneOtroMedioTransAuto() {
        return tieneOtroMedioTransAuto;
    }

    public void setTieneOtroMedioTransAuto(String tieneOtroMedioTransAuto) {
        this.tieneOtroMedioTransAuto = tieneOtroMedioTransAuto;
    }

    public String getOtroMedioTransAuto() {
        return otroMedioTransAuto;
    }

    public void setOtroMedioTransAuto(String otroMedioTransAuto) {
        this.otroMedioTransAuto = otroMedioTransAuto;
    }

    public String getCocinaConLenia() {
        return cocinaConLenia;
    }

    public void setCocinaConLenia(String cocinaConLenia) {
        this.cocinaConLenia = cocinaConLenia;
    }

    public String getUbicacionCocinaLenia() {
        return ubicacionCocinaLenia;
    }

    public void setUbicacionCocinaLenia(String ubicacionCocinaLenia) {
        this.ubicacionCocinaLenia = ubicacionCocinaLenia;
    }

    public String getPeriodicidadCocinaLenia() {
        return periodicidadCocinaLenia;
    }

    public void setPeriodicidadCocinaLenia(String periodicidadCocinaLenia) {
        this.periodicidadCocinaLenia = periodicidadCocinaLenia;
    }

    public String getNumDiarioCocinaLenia() {
        return numDiarioCocinaLenia;
    }

    public void setNumDiarioCocinaLenia(String numDiarioCocinaLenia) {
        this.numDiarioCocinaLenia = numDiarioCocinaLenia;
    }

    public String getNumSemanalCocinaLenia() {
        return numSemanalCocinaLenia;
    }

    public void setNumSemanalCocinaLenia(String numSemanalCocinaLenia) {
        this.numSemanalCocinaLenia = numSemanalCocinaLenia;
    }

    public String getNumQuincenalCocinaLenia() {
        return numQuincenalCocinaLenia;
    }

    public void setNumQuincenalCocinaLenia(String numQuincenalCocinaLenia) {
        this.numQuincenalCocinaLenia = numQuincenalCocinaLenia;
    }

    public String getNumMensualCocinaLenia() {
        return numMensualCocinaLenia;
    }

    public void setNumMensualCocinaLenia(String numMensualCocinaLenia) {
        this.numMensualCocinaLenia = numMensualCocinaLenia;
    }

    public String getTieneAnimales() {
        return tieneAnimales;
    }

    public void setTieneAnimales(String tieneAnimales) {
        this.tieneAnimales = tieneAnimales;
    }

    public String getTieneGallinas() {
        return tieneGallinas;
    }

    public void setTieneGallinas(String tieneGallinas) {
        this.tieneGallinas = tieneGallinas;
    }

    public String getTienePatos() {
        return tienePatos;
    }

    public void setTienePatos(String tienePatos) {
        this.tienePatos = tienePatos;
    }

    public String getTieneCerdos() {
        return tieneCerdos;
    }

    public void setTieneCerdos(String tieneCerdos) {
        this.tieneCerdos = tieneCerdos;
    }

    public String getCantidadGallinas() {
        return cantidadGallinas;
    }

    public void setCantidadGallinas(String cantidadGallinas) {
        this.cantidadGallinas = cantidadGallinas;
    }

    public String getCantidadPatos() {
        return cantidadPatos;
    }

    public void setCantidadPatos(String cantidadPatos) {
        this.cantidadPatos = cantidadPatos;
    }

    public String getCantidadCerdos() {
        return cantidadCerdos;
    }

    public void setCantidadCerdos(String cantidadCerdos) {
        this.cantidadCerdos = cantidadCerdos;
    }

    public String getGallinasDentroCasa() {
        return gallinasDentroCasa;
    }

    public void setGallinasDentroCasa(String gallinasDentroCasa) {
        this.gallinasDentroCasa = gallinasDentroCasa;
    }

    public String getPatosDentroCasa() {
        return patosDentroCasa;
    }

    public void setPatosDentroCasa(String patosDentroCasa) {
        this.patosDentroCasa = patosDentroCasa;
    }

    public String getCerdosDentroCasa() {
        return cerdosDentroCasa;
    }

    public void setCerdosDentroCasa(String cerdosDentroCasa) {
        this.cerdosDentroCasa = cerdosDentroCasa;
    }

    public String getPersonaFumaDentroCasa() {
        return personaFumaDentroCasa;
    }

    public void setPersonaFumaDentroCasa(String personaFumaDentroCasa) {
        this.personaFumaDentroCasa = personaFumaDentroCasa;
    }

    public String getMadreFuma() {
        return madreFuma;
    }

    public void setMadreFuma(String madreFuma) {
        this.madreFuma = madreFuma;
    }

    public String getPadreFuma() {
        return padreFuma;
    }

    public void setPadreFuma(String padreFuma) {
        this.padreFuma = padreFuma;
    }

    public String getOtrosFuman() {
        return otrosFuman;
    }

    public void setOtrosFuman(String otrosFuman) {
        this.otrosFuman = otrosFuman;
    }

    public String getCantidadOtrosFuman() {
        return cantidadOtrosFuman;
    }

    public void setCantidadOtrosFuman(String cantidadOtrosFuman) {
        this.cantidadOtrosFuman = cantidadOtrosFuman;
    }

    public String getCantidadCigarrilosMadre() {
        return cantidadCigarrilosMadre;
    }

    public void setCantidadCigarrilosMadre(String cantidadCigarrilosMadre) {
        this.cantidadCigarrilosMadre = cantidadCigarrilosMadre;
    }

    public String getCantidadCigarrillosPadre() {
        return cantidadCigarrillosPadre;
    }

    public void setCantidadCigarrillosPadre(String cantidadCigarrillosPadre) {
        this.cantidadCigarrillosPadre = cantidadCigarrillosPadre;
    }

    public String getCantidadCigarrillosOtros() {
        return cantidadCigarrillosOtros;
    }

    public void setCantidadCigarrillosOtros(String cantidadCigarrillosOtros) {
        this.cantidadCigarrillosOtros = cantidadCigarrillosOtros;
    }

    public String getAlmacenaEnBarrilesHint() {
        return almacenaEnBarrilesHint;
    }

    public void setAlmacenaEnBarrilesHint(String almacenaEnBarrilesHint) {
        this.almacenaEnBarrilesHint = almacenaEnBarrilesHint;
    }

    public String getAlmacenaEnTanquesHint() {
        return almacenaEnTanquesHint;
    }

    public void setAlmacenaEnTanquesHint(String almacenaEnTanquesHint) {
        this.almacenaEnTanquesHint = almacenaEnTanquesHint;
    }

    public String getAlmacenaEnPilasHint() {
        return almacenaEnPilasHint;
    }

    public void setAlmacenaEnPilasHint(String almacenaEnPilasHint) {
        this.almacenaEnPilasHint = almacenaEnPilasHint;
    }

    public String getAlmacenaOtrosRecipientesHint() {
        return almacenaOtrosRecipientesHint;
    }

    public void setAlmacenaOtrosRecipientesHint(String almacenaOtrosRecipientesHint) {
        this.almacenaOtrosRecipientesHint = almacenaOtrosRecipientesHint;
    }

    public String getOtrosRecipientesHint() {
        return otrosRecipientesHint;
    }

    public void setOtrosRecipientesHint(String otrosRecipientesHint) {
        this.otrosRecipientesHint = otrosRecipientesHint;
    }

    public String getNumBarrilesHint() {
        return numBarrilesHint;
    }

    public void setNumBarrilesHint(String numBarrilesHint) {
        this.numBarrilesHint = numBarrilesHint;
    }

    public String getNumTanquesHint() {
        return numTanquesHint;
    }

    public void setNumTanquesHint(String numTanquesHint) {
        this.numTanquesHint = numTanquesHint;
    }

    public String getNumPilasHint() {
        return numPilasHint;
    }

    public void setNumPilasHint(String numPilasHint) {
        this.numPilasHint = numPilasHint;
    }

    public String getNumOtrosRecipientesHint() {
        return numOtrosRecipientesHint;
    }

    public void setNumOtrosRecipientesHint(String numOtrosRecipientesHint) {
        this.numOtrosRecipientesHint = numOtrosRecipientesHint;
    }

    public String getBarrilesTapadosHint() {
        return barrilesTapadosHint;
    }

    public void setBarrilesTapadosHint(String barrilesTapadosHint) {
        this.barrilesTapadosHint = barrilesTapadosHint;
    }

    public String getTanquesTapadosHint() {
        return tanquesTapadosHint;
    }

    public void setTanquesTapadosHint(String tanquesTapadosHint) {
        this.tanquesTapadosHint = tanquesTapadosHint;
    }

    public String getPilasTapadasHint() {
        return pilasTapadasHint;
    }

    public void setPilasTapadasHint(String pilasTapadasHint) {
        this.pilasTapadasHint = pilasTapadasHint;
    }

    public String getOtrosRecipientesTapadosHint() {
        return otrosRecipientesTapadosHint;
    }

    public void setOtrosRecipientesTapadosHint(String otrosRecipientesTapadosHint) {
        this.otrosRecipientesTapadosHint = otrosRecipientesTapadosHint;
    }

    public String getBarrilesConAbateHint() {
        return barrilesConAbateHint;
    }

    public void setBarrilesConAbateHint(String barrilesConAbateHint) {
        this.barrilesConAbateHint = barrilesConAbateHint;
    }

    public String getTanquesConAbateHint() {
        return tanquesConAbateHint;
    }

    public void setTanquesConAbateHint(String tanquesConAbateHint) {
        this.tanquesConAbateHint = tanquesConAbateHint;
    }

    public String getPilasConAbateHint() {
        return pilasConAbateHint;
    }

    public void setPilasConAbateHint(String pilasConAbateHint) {
        this.pilasConAbateHint = pilasConAbateHint;
    }

    public String getOtrosRecipientesConAbateHint() {
        return otrosRecipientesConAbateHint;
    }

    public void setOtrosRecipientesConAbateHint(String otrosRecipientesConAbateHint) {
        this.otrosRecipientesConAbateHint = otrosRecipientesConAbateHint;
    }

    public String getLlaveCompartidaHint() {
        return llaveCompartidaHint;
    }

    public void setLlaveCompartidaHint(String llaveCompartidaHint) {
        this.llaveCompartidaHint = llaveCompartidaHint;
    }

    public String getUbicacionCocinaLeniaHint() {
        return ubicacionCocinaLeniaHint;
    }

    public void setUbicacionCocinaLeniaHint(String ubicacionCocinaLeniaHint) {
        this.ubicacionCocinaLeniaHint = ubicacionCocinaLeniaHint;
    }

    public String getNumDiarioCocinaLeniaHint() {
        return numDiarioCocinaLeniaHint;
    }

    public void setNumDiarioCocinaLeniaHint(String numDiarioCocinaLeniaHint) {
        this.numDiarioCocinaLeniaHint = numDiarioCocinaLeniaHint;
    }

    public String getNumSemanalCocinaLeniaHint() {
        return numSemanalCocinaLeniaHint;
    }

    public void setNumSemanalCocinaLeniaHint(String numSemanalCocinaLeniaHint) {
        this.numSemanalCocinaLeniaHint = numSemanalCocinaLeniaHint;
    }

    public String getNumQuincenalCocinaLeniaHint() {
        return numQuincenalCocinaLeniaHint;
    }

    public void setNumQuincenalCocinaLeniaHint(String numQuincenalCocinaLeniaHint) {
        this.numQuincenalCocinaLeniaHint = numQuincenalCocinaLeniaHint;
    }

    public String getNumMensualCocinaLeniaHint() {
        return numMensualCocinaLeniaHint;
    }

    public void setNumMensualCocinaLeniaHint(String numMensualCocinaLeniaHint) {
        this.numMensualCocinaLeniaHint = numMensualCocinaLeniaHint;
    }

    public String getMadreFumaHint() {
        return madreFumaHint;
    }

    public void setMadreFumaHint(String madreFumaHint) {
        this.madreFumaHint = madreFumaHint;
    }

    public String getPadreFumaHint() {
        return padreFumaHint;
    }

    public void setPadreFumaHint(String padreFumaHint) {
        this.padreFumaHint = padreFumaHint;
    }

    public String getOtrosFumanHint() {
        return otrosFumanHint;
    }

    public void setOtrosFumanHint(String otrosFumanHint) {
        this.otrosFumanHint = otrosFumanHint;
    }

    public String getCantidadCigarrilosMadreHint() {
        return cantidadCigarrilosMadreHint;
    }

    public void setCantidadCigarrilosMadreHint(String cantidadCigarrilosMadreHint) {
        this.cantidadCigarrilosMadreHint = cantidadCigarrilosMadreHint;
    }

    public String getCantidadCigarrillosPadreHint() {
        return cantidadCigarrillosPadreHint;
    }

    public void setCantidadCigarrillosPadreHint(String cantidadCigarrillosPadreHint) {
        this.cantidadCigarrillosPadreHint = cantidadCigarrillosPadreHint;
    }

    public String getCantidadCigarrillosOtrosHint() {
        return cantidadCigarrillosOtrosHint;
    }

    public void setCantidadCigarrillosOtrosHint(String cantidadCigarrillosOtrosHint) {
        this.cantidadCigarrillosOtrosHint = cantidadCigarrillosOtrosHint;
    }

    public String getHorasSinAguaHint() {
        return horasSinAguaHint;
    }

    public void setHorasSinAguaHint(String horasSinAguaHint) {
        this.horasSinAguaHint = horasSinAguaHint;
    }

    public String getCantidadOtrosFumanHint() {
        return cantidadOtrosFumanHint;
    }

    public void setCantidadOtrosFumanHint(String cantidadOtrosFumanHint) {
        this.cantidadOtrosFumanHint = cantidadOtrosFumanHint;
    }
}
