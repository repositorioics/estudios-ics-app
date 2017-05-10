package ni.org.ics.estudios.appmovil.domain.encuestas;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Casa;

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
	private Casa casa;
    private int cantidadCuartos;
    private int cantidadCuartosDormir;
    private Date fechaEncuestas;
    private String encuestador;
    private int hrsSinServicioAgua;
    private String ubicacionLlaveAgua;
    private char llaveCompartida;
    private char almacenaAgua;
    private char almacenaEnBarriles;
    private char almacenaEnTanques;
    private char almacenaEnPilas;
    private char almacenaOtrosRecipientes;
    private String otrosRecipientes;
    private Integer numBarriles;
    private Integer numTanques;
    private Integer numPilas;
    private Integer numOtrosRecipientes;
    private char barrilesTapados;
    private char tanquesTapados;
    private char pilasTapadas;
    private char otrosRecipientesTapados;
    private char barrilesConAbate;
    private char tanquesConAbate;
    private char pilasConAbate;
    private char otrosRecipientesConAbate;
    private String ubicacionLavandero;
    private String materialParedes;
    private String materialPiso;
    private String materialTecho;
    private String otroMaterialParedes;
    private String otroMaterialPiso;
    private String otroMaterialTecho;
    private char casaPropia;
    private char tieneAbanico;
    private char tieneTelevisor;
    private char tieneRefrigerador;
    private char tienAireAcondicionado;
    private String aireAcondicionadoFuncionando;
    private Integer numAbanicos;
    private Integer numTelevisores;
    private Integer numRefrigeradores;
    private char tieneMoto;
    private char tieneCarro;
    private char tienMicrobus;
    private char tieneCamioneta;
    private char tieneCamion;
    private char tieneOtroMedioTransAuto;
    private String otroMedioTransAuto;
    private char cocinaConLenia;
    private String ubicacionCocinaLenia;
    private String periodicidadCocinaLenia;
    private Integer numDiarioCocinaLenia;   //# de veces que cocina
    private Integer numSemanalCocinaLenia;  //# de veces que cocina semanalmente
    private Integer numQuincenalCocinaLenia;    //# de veces que cocina quincenalmente
    private Integer numMensualCocinaLenia;  //# de veces que cocina al mes
    private char tieneAnimales;
    private char tieneGallinas;
    private char tienePatos;
    private char tieneCerdos;
    private Integer cantidadGallinas;
    private Integer cantidadPatos;
    private Integer cantidadCerdos;
    private char gallinasDentroCasa;
    private char patosDentroCasa;
    private char cerdosDentroCasa;
    private char personaFumaDentroCasa;  //Alguna persona que no pertenece al estudio fuma dentro de la casa
    private char madreFuma;
    private char padreFuma;
    private char otrosFuman;
    private Integer cantidadOtrosFuman;
    private Integer cantidadCigarrilosMadre; // diarios
    private Integer cantidadCigarrillosPadre; // diarios
    private Integer cantidadCigarrillosOtros; // diarios

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
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

    public char getLlaveCompartida() {
        return llaveCompartida;
    }

    public void setLlaveCompartida(char llaveCompartida) {
        this.llaveCompartida = llaveCompartida;
    }

    public char getAlmacenaAgua() {
        return almacenaAgua;
    }

    public void setAlmacenaAgua(char almacenaAgua) {
        this.almacenaAgua = almacenaAgua;
    }

    public char getAlmacenaEnBarriles() {
        return almacenaEnBarriles;
    }

    public void setAlmacenaEnBarriles(char almacenaEnBarriles) {
        this.almacenaEnBarriles = almacenaEnBarriles;
    }

    public char getAlmacenaEnTanques() {
        return almacenaEnTanques;
    }

    public void setAlmacenaEnTanques(char almacenaEnTanques) {
        this.almacenaEnTanques = almacenaEnTanques;
    }

    public char getAlmacenaEnPilas() {
        return almacenaEnPilas;
    }

    public void setAlmacenaEnPilas(char almacenaEnPilas) {
        this.almacenaEnPilas = almacenaEnPilas;
    }

    public char getAlmacenaOtrosRecipientes() {
        return almacenaOtrosRecipientes;
    }

    public void setAlmacenaOtrosRecipientes(char almacenaOtrosRecipientes) {
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

    public char getBarrilesTapados() {
        return barrilesTapados;
    }

    public void setBarrilesTapados(char barrilesTapados) {
        this.barrilesTapados = barrilesTapados;
    }

    public char getTanquesTapados() {
        return tanquesTapados;
    }

    public void setTanquesTapados(char tanquesTapados) {
        this.tanquesTapados = tanquesTapados;
    }

    public char getPilasTapadas() {
        return pilasTapadas;
    }

    public void setPilasTapadas(char pilasTapadas) {
        this.pilasTapadas = pilasTapadas;
    }

    public char getOtrosRecipientesTapados() {
        return otrosRecipientesTapados;
    }

    public void setOtrosRecipientesTapados(char otrosRecipientesTapados) {
        this.otrosRecipientesTapados = otrosRecipientesTapados;
    }

    public char getBarrilesConAbate() {
        return barrilesConAbate;
    }

    public void setBarrilesConAbate(char barrilesConAbate) {
        this.barrilesConAbate = barrilesConAbate;
    }

    public char getTanquesConAbate() {
        return tanquesConAbate;
    }

    public void setTanquesConAbate(char tanquesConAbate) {
        this.tanquesConAbate = tanquesConAbate;
    }

    public char getPilasConAbate() {
        return pilasConAbate;
    }

    public void setPilasConAbate(char pilasConAbate) {
        this.pilasConAbate = pilasConAbate;
    }

    public char getOtrosRecipientesConAbate() {
        return otrosRecipientesConAbate;
    }

    public void setOtrosRecipientesConAbate(char otrosRecipientesConAbate) {
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

    public char getCasaPropia() {
        return casaPropia;
    }

    public void setCasaPropia(char casaPropia) {
        this.casaPropia = casaPropia;
    }

    public char getTieneAbanico() {
        return tieneAbanico;
    }

    public void setTieneAbanico(char tieneAbanico) {
        this.tieneAbanico = tieneAbanico;
    }

    public char getTieneTelevisor() {
        return tieneTelevisor;
    }

    public void setTieneTelevisor(char tieneTelevisor) {
        this.tieneTelevisor = tieneTelevisor;
    }

    public char getTieneRefrigerador() {
        return tieneRefrigerador;
    }

    public void setTieneRefrigerador(char tieneRefrigerador) {
        this.tieneRefrigerador = tieneRefrigerador;
    }

    public char getTienAireAcondicionado() {
        return tienAireAcondicionado;
    }

    public void setTienAireAcondicionado(char tienAireAcondicionado) {
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

    public char getTieneMoto() {
        return tieneMoto;
    }

    public void setTieneMoto(char tieneMoto) {
        this.tieneMoto = tieneMoto;
    }

    public char getTieneCarro() {
        return tieneCarro;
    }

    public void setTieneCarro(char tieneCarro) {
        this.tieneCarro = tieneCarro;
    }

    public char getTienMicrobus() {
        return tienMicrobus;
    }

    public void setTienMicrobus(char tienMicrobus) {
        this.tienMicrobus = tienMicrobus;
    }

    public char getTieneCamioneta() {
        return tieneCamioneta;
    }

    public void setTieneCamioneta(char tieneCamioneta) {
        this.tieneCamioneta = tieneCamioneta;
    }

    public char getTieneCamion() {
        return tieneCamion;
    }

    public void setTieneCamion(char tieneCamion) {
        this.tieneCamion = tieneCamion;
    }

    public char getTieneOtroMedioTransAuto() {
        return tieneOtroMedioTransAuto;
    }

    public void setTieneOtroMedioTransAuto(char tieneOtroMedioTransAuto) {
        this.tieneOtroMedioTransAuto = tieneOtroMedioTransAuto;
    }

    public String getOtroMedioTransAuto() {
        return otroMedioTransAuto;
    }

    public void setOtroMedioTransAuto(String otroMedioTransAuto) {
        this.otroMedioTransAuto = otroMedioTransAuto;
    }

    public char getCocinaConLenia() {
        return cocinaConLenia;
    }

    public void setCocinaConLenia(char cocinaConLenia) {
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

    public char getTieneAnimales() {
        return tieneAnimales;
    }

    public void setTieneAnimales(char tieneAnimales) {
        this.tieneAnimales = tieneAnimales;
    }

    public char getTieneGallinas() {
        return tieneGallinas;
    }

    public void setTieneGallinas(char tieneGallinas) {
        this.tieneGallinas = tieneGallinas;
    }

    public char getTienePatos() {
        return tienePatos;
    }

    public void setTienePatos(char tienePatos) {
        this.tienePatos = tienePatos;
    }

    public char getTieneCerdos() {
        return tieneCerdos;
    }

    public void setTieneCerdos(char tieneCerdos) {
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

    public char getGallinasDentroCasa() {
        return gallinasDentroCasa;
    }

    public void setGallinasDentroCasa(char gallinasDentroCasa) {
        this.gallinasDentroCasa = gallinasDentroCasa;
    }

    public char getPatosDentroCasa() {
        return patosDentroCasa;
    }

    public void setPatosDentroCasa(char patosDentroCasa) {
        this.patosDentroCasa = patosDentroCasa;
    }

    public char getCerdosDentroCasa() {
        return cerdosDentroCasa;
    }

    public void setCerdosDentroCasa(char cerdosDentroCasa) {
        this.cerdosDentroCasa = cerdosDentroCasa;
    }

    public char getPersonaFumaDentroCasa() {
        return personaFumaDentroCasa;
    }

    public void setPersonaFumaDentroCasa(char personaFumaDentroCasa) {
        this.personaFumaDentroCasa = personaFumaDentroCasa;
    }

    public char getMadreFuma() {
        return madreFuma;
    }

    public void setMadreFuma(char madreFuma) {
        this.madreFuma = madreFuma;
    }

    public char getPadreFuma() {
        return padreFuma;
    }

    public void setPadreFuma(char padreFuma) {
        this.padreFuma = padreFuma;
    }

    public char getOtrosFuman() {
        return otrosFuman;
    }

    public void setOtrosFuman(char otrosFuman) {
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
        return "EncuestaCasa{" + casa.getCodigo() +
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
