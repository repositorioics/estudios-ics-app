package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;

/**
 * Simple objeto de dominio que representa un participante de los estudios
 * 
 * @author William Aviles
 **/
public class ParticipanteProcesos implements Serializable {

	/**
	 * 
	 */
    private Integer codigo;
    private String conPto;
    private Integer estPart;
    private String estudio;
    private String pbmc;
    private String consDeng;
    private String reConsDeng;
    private String zika;
    private String consFlu;
    private String consChik;
    private String conmx;
    private String conmxbhc;
    private String encLacMat;
    private String pesoTalla;
    private String encPart;
    private String enCasa;
    private String datosVisita;
    private String obsequio;
    private String convalesciente;
    private String infoVacuna;
    private String paxgene;
    private String adn;
    private String retoma;
    private Double volRetoma;
    private String datosParto;
    private String mi;
    private String casaCHF;
    private Integer relacionFam;
    private Integer cuantasPers;
    private String posZika;
    private String enCasaChf;
    private String enCasaSa;
    private String encPartSa;

    private MovilInfo movilInfo;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getConPto() {
        return conPto;
    }

    public void setConPto(String conPto) {
        this.conPto = conPto;
    }

    public Integer getEstPart() {
        return estPart;
    }

    public void setEstPart(Integer estPart) {
        this.estPart = estPart;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public String getPbmc() {
        return pbmc;
    }

    public void setPbmc(String pbmc) {
        this.pbmc = pbmc;
    }

    public String getConsDeng() {
        return consDeng;
    }

    public void setConsDeng(String consDeng) {
        this.consDeng = consDeng;
    }

    public String getReConsDeng() {
        return reConsDeng;
    }

    public void setReConsDeng(String reConsDeng) {
        this.reConsDeng = reConsDeng;
    }

    public String getZika() {
        return zika;
    }

    public void setZika(String zika) {
        this.zika = zika;
    }

    public String getConsFlu() {
        return consFlu;
    }

    public void setConsFlu(String consFlu) {
        this.consFlu = consFlu;
    }

    public String getConsChik() {
        return consChik;
    }

    public void setConsChik(String consChik) {
        this.consChik = consChik;
    }

    public String getConmx() {
        return conmx;
    }

    public void setConmx(String conmx) {
        this.conmx = conmx;
    }

    public String getConmxbhc() {
        return conmxbhc;
    }

    public void setConmxbhc(String conmxbhc) {
        this.conmxbhc = conmxbhc;
    }

    public String getEncLacMat() {
        return encLacMat;
    }

    public void setEncLacMat(String encLacMat) {
        this.encLacMat = encLacMat;
    }

    public String getPesoTalla() {
        return pesoTalla;
    }

    public void setPesoTalla(String pesoTalla) {
        this.pesoTalla = pesoTalla;
    }

    public String getEncPart() {
        return encPart;
    }

    public void setEncPart(String encPart) {
        this.encPart = encPart;
    }

    public String getEnCasa() {
        return enCasa;
    }

    public void setEnCasa(String enCasa) {
        this.enCasa = enCasa;
    }

    public String getDatosVisita() {
        return datosVisita;
    }

    public void setDatosVisita(String datosVisita) {
        this.datosVisita = datosVisita;
    }

    public String getObsequio() {
        return obsequio;
    }

    public void setObsequio(String obsequio) {
        this.obsequio = obsequio;
    }

    public String getConvalesciente() {
        return convalesciente;
    }

    public void setConvalesciente(String convalesciente) {
        this.convalesciente = convalesciente;
    }

    public String getInfoVacuna() {
        return infoVacuna;
    }

    public void setInfoVacuna(String infoVacuna) {
        this.infoVacuna = infoVacuna;
    }

    public String getPaxgene() {
        return paxgene;
    }

    public void setPaxgene(String paxgene) {
        this.paxgene = paxgene;
    }

    public String getAdn() {
        return adn;
    }

    public void setAdn(String adn) {
        this.adn = adn;
    }

    public String getRetoma() {
        return retoma;
    }

    public void setRetoma(String retoma) {
        this.retoma = retoma;
    }

    public Double getVolRetoma() {
        return volRetoma;
    }

    public void setVolRetoma(Double volRetoma) {
        this.volRetoma = volRetoma;
    }

    public String getDatosParto() {
        return datosParto;
    }

    public void setDatosParto(String datosParto) {
        this.datosParto = datosParto;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getCasaCHF() {
        return casaCHF;
    }

    public void setCasaCHF(String casaCHF) {
        this.casaCHF = casaCHF;
    }

    public Integer getRelacionFam() {
        return relacionFam;
    }

    public void setRelacionFam(Integer relacionFam) {
        this.relacionFam = relacionFam;
    }

    public Integer getCuantasPers() {
        return cuantasPers;
    }

    public void setCuantasPers(Integer cuantasPers) {
        this.cuantasPers = cuantasPers;
    }

    public String getPosZika() {
        return posZika;
    }

    public void setPosZika(String posZika) {
        this.posZika = posZika;
    }

    public MovilInfo getMovilInfo() {
        return movilInfo;
    }

    public void setMovilInfo(MovilInfo movilInfo) {
        this.movilInfo = movilInfo;
    }

    public String getEnCasaChf() {
        return enCasaChf;
    }

    public void setEnCasaChf(String enCasaChf) {
        this.enCasaChf = enCasaChf;
    }

    public String getEnCasaSa() {
        return enCasaSa;
    }

    public void setEnCasaSa(String enCasaSa) {
        this.enCasaSa = enCasaSa;
    }

    public String getEncPartSa() {
        return encPartSa;
    }

    public void setEncPartSa(String encPartSa) {
        this.encPartSa = encPartSa;
    }
}
