package ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;

import java.util.Date;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */
public class EncuestaCasa extends BaseMetaData {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private CasaCohorteFamilia casa;
    private int cantidadCuartos;
    private int cantidadCuartosDormir;
    private Date fechaEncuestas;
    private String encuestador;
    private int hrsSinServicioAgua;
    private String ubicacionLlaveAgua;
    private String llaveCompartida;
    private String almacenaAgua;
    private String almacenaEnBarriles;
    private String almacenaEnTanques;
    private String almacenaEnPilas;
    private String almacenaOtrosRecipientes;
    private String otrosRecipientes;
    private Integer numBarriles;
    private Integer numTanques;
    private Integer numPilas;
    private Integer numOtrosRecipientes;
    private String barrilesTapados;
    private String tanquesTapados;
    private String pilasTapadas;
    private String otrosRecipientesTapados;
    private String barrilesConAbate;
    private String tanquesConAbate;
    private String pilasConAbate;
    private String otrosRecipientesConAbate;
    private String ubicacionLavandero;
    private String materialParedes;
    private String materialPiso;
    private String materialTecho;
    private String otroMaterialParedes;
    private String otroMaterialPiso;
    private String otroMaterialTecho;
    private String casaPropia;
    private String tieneAbanico;
    private String tieneTelevisor;
    private String tieneRefrigerador;
    private String tienAireAcondicionado;
    private String aireAcondicionadoFuncionando;
    private Integer numAbanicos;
    private Integer numTelevisores;
    private Integer numRefrigeradores;
    private String tieneMoto;
    private String tieneCarro;
    private String tienMicrobus;
    private String tieneCamioneta;
    private String tieneCamion;
    private String tieneOtroMedioTransAuto;
    private String otroMedioTransAuto;
    private String cocinaConLenia;
    private String ubicacionCocinaLenia;
    private String periodicidadCocinaLenia;
    private Integer numDiarioCocinaLenia;   //# de veces que cocina
    private Integer numSemanalCocinaLenia;  //# de veces que cocina semanalmente
    private Integer numQuincenalCocinaLenia;    //# de veces que cocina quincenalmente
    private Integer numMensualCocinaLenia;  //# de veces que cocina al mes
    private String tieneAnimales;
    private String tieneGallinas;
    private String tienePatos;
    private String tieneCerdos;
    private Integer cantidadGallinas;
    private Integer cantidadPatos;
    private Integer cantidadCerdos;
    private String gallinasDentroCasa;
    private String patosDentroCasa;
    private String cerdosDentroCasa;
    private String personaFumaDentroCasa;  //Alguna persona que no pertenece al estudio fuma dentro de la casa
    private String madreFuma;
    private String padreFuma;
    private String otrosFuman;
    private Integer cantidadOtrosFuman;
    private Integer cantidadCigarrilosMadre; // diarios
    private Integer cantidadCigarrillosPadre; // diarios
    private Integer cantidadCigarrillosOtros; // diarios

    public CasaCohorteFamilia getCasa() {
        return casa;
    }

    public void setCasa(CasaCohorteFamilia casa) {
        this.casa = casa;
    }

    public int getCantidadCuartos() {
        return cantidadCuartos;
    }

    public void setCantidadCuartos(int cantidadCuartos) {
        this.cantidadCuartos = cantidadCuartos;
    }

    public int getCantidadCuartosDormir() {
        return cantidadCuartosDormir;
    }

    public void setCantidadCuartosDormir(int cantidadCuartosDormir) {
        this.cantidadCuartosDormir = cantidadCuartosDormir;
    }

    public Date getFechaEncuestas() {
        return fechaEncuestas;
    }

    public void setFechaEncuestas(Date fechaEncuestas) {
        this.fechaEncuestas = fechaEncuestas;
    }

    public String getEncuestador() {
        return encuestador;
    }

    public void setEncuestador(String encuestador) {
        this.encuestador = encuestador;
    }

    public int getHrsSinServicioAgua() {
        return hrsSinServicioAgua;
    }

    public void setHrsSinServicioAgua(int hrsSinServicioAgua) {
        this.hrsSinServicioAgua = hrsSinServicioAgua;
    }

    public String getUbicacionLlaveAgua() {
        return ubicacionLlaveAgua;
    }

    public void setUbicacionLlaveAgua(String ubicacionLlaveAgua) {
        this.ubicacionLlaveAgua = ubicacionLlaveAgua;
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

    public Integer getNumBarriles() {
        return numBarriles;
    }

    public void setNumBarriles(Integer numBarriles) {
        this.numBarriles = numBarriles;
    }

    public Integer getNumTanques() {
        return numTanques;
    }

    public void setNumTanques(Integer numTanques) {
        this.numTanques = numTanques;
    }

    public Integer getNumPilas() {
        return numPilas;
    }

    public void setNumPilas(Integer numPilas) {
        this.numPilas = numPilas;
    }

    public Integer getNumOtrosRecipientes() {
        return numOtrosRecipientes;
    }

    public void setNumOtrosRecipientes(Integer numOtrosRecipientes) {
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

    public Integer getNumAbanicos() {
        return numAbanicos;
    }

    public void setNumAbanicos(Integer numAbanicos) {
        this.numAbanicos = numAbanicos;
    }

    public Integer getNumTelevisores() {
        return numTelevisores;
    }

    public void setNumTelevisores(Integer numTelevisores) {
        this.numTelevisores = numTelevisores;
    }

    public Integer getNumRefrigeradores() {
        return numRefrigeradores;
    }

    public void setNumRefrigeradores(Integer numRefrigeradores) {
        this.numRefrigeradores = numRefrigeradores;
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

    public Integer getNumDiarioCocinaLenia() {
        return numDiarioCocinaLenia;
    }

    public void setNumDiarioCocinaLenia(Integer numDiarioCocinaLenia) {
        this.numDiarioCocinaLenia = numDiarioCocinaLenia;
    }

    public Integer getNumSemanalCocinaLenia() {
        return numSemanalCocinaLenia;
    }

    public void setNumSemanalCocinaLenia(Integer numSemanalCocinaLenia) {
        this.numSemanalCocinaLenia = numSemanalCocinaLenia;
    }

    public Integer getNumQuincenalCocinaLenia() {
        return numQuincenalCocinaLenia;
    }

    public void setNumQuincenalCocinaLenia(Integer numQuincenalCocinaLenia) {
        this.numQuincenalCocinaLenia = numQuincenalCocinaLenia;
    }

    public Integer getNumMensualCocinaLenia() {
        return numMensualCocinaLenia;
    }

    public void setNumMensualCocinaLenia(Integer numMensualCocinaLenia) {
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

    public Integer getCantidadGallinas() {
        return cantidadGallinas;
    }

    public void setCantidadGallinas(Integer cantidadGallinas) {
        this.cantidadGallinas = cantidadGallinas;
    }

    public Integer getCantidadPatos() {
        return cantidadPatos;
    }

    public void setCantidadPatos(Integer cantidadPatos) {
        this.cantidadPatos = cantidadPatos;
    }

    public Integer getCantidadCerdos() {
        return cantidadCerdos;
    }

    public void setCantidadCerdos(Integer cantidadCerdos) {
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

    public Integer getCantidadOtrosFuman() {
        return cantidadOtrosFuman;
    }

    public void setCantidadOtrosFuman(Integer cantidadOtrosFuman) {
        this.cantidadOtrosFuman = cantidadOtrosFuman;
    }

    public Integer getCantidadCigarrilosMadre() {
        return cantidadCigarrilosMadre;
    }

    public void setCantidadCigarrilosMadre(Integer cantidadCigarrilosMadre) {
        this.cantidadCigarrilosMadre = cantidadCigarrilosMadre;
    }

    public Integer getCantidadCigarrillosPadre() {
        return cantidadCigarrillosPadre;
    }

    public void setCantidadCigarrillosPadre(Integer cantidadCigarrillosPadre) {
        this.cantidadCigarrillosPadre = cantidadCigarrillosPadre;
    }

    public Integer getCantidadCigarrillosOtros() {
        return cantidadCigarrillosOtros;
    }

    public void setCantidadCigarrillosOtros(Integer cantidadCigarrillosOtros) {
        this.cantidadCigarrillosOtros = cantidadCigarrillosOtros;
    }

    @Override
    public String toString() {
        return "EncuestaCasa{" + casa.getCodigoCHF() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncuestaCasa)) return false;

        EncuestaCasa that = (EncuestaCasa) o;

        return  (!casa.equals(that.casa));
    }

    @Override
    public int hashCode() {
        return casa.hashCode();
    }
}
