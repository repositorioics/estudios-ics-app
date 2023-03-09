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
    private Double volRetomaPbmc;
    private String datosParto;
    private String mi;
    private String casaCHF;
    //private Integer relacionFam;
    private Integer cuantasPers;
    private String posZika;
    private String enCasaChf;
    private String enCasaSa;
    private String encPartSa;
    //private String tutor;
    private String consSa;
    private String coordenadas; //cambio de domicilio
    //MA2019
    private String obsequioChf;
    private String cDatosParto;//completar datos parto campos que no se pedian antes del 2018
    private String reConsChf18;//reconsentimiento a participantes de familia que cumplen 18 anios
    //22052019
    private String posDengue;
    //Muestras de superficie
    private String mxSuperficie; //1:asent mx superficie, 2:consent manos, 3:Ambos, 0 o null:No aplica
    //MA2020
    private String mostrarAlfabeto;
    private String mostrarPadreAlfabeto;
    private String mostrarMadreAlfabeta;
    private String mostrarNumParto;
    private String antecedenteTutorCP;
    //Covid19
    private String consCovid19;
    private String subEstudios;
    //Parte E CHF para toma mx adicional Covid19
    private String consChf;
    private String cuestCovid;
    private String muestraCovid;
    //Texto que indica si el participante ha sido positivo para Covid19(SARS-COV2)
    private String posCovid;
    //Parte E Dengue. MUESTRA DE SANGRE ADICIONAL
    private String consDenParteE;
    private String mxDenParteE;
    private String informacionRetiro;

    //Indica si esta pendiente de la toma del perimetro abdominal
    private String perimetroAbdominal;

    //Indica realizar encuensta de satisfaccion de usuario
    private String esatUsuario;

    //Indica realizar encuensta de satisfaccion de usuario - control de calidad
    private String esatUsuarioCc;

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

    /*public Integer getRelacionFam() {
        return relacionFam;
    }

    public void setRelacionFam(Integer relacionFam) {
        this.relacionFam = relacionFam;
    }*/

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

    /*public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }*/

    public Double getVolRetomaPbmc() {
        return volRetomaPbmc;
    }

    public void setVolRetomaPbmc(Double volRetomaPbmc) {
        this.volRetomaPbmc = volRetomaPbmc;
    }

    public String getConsSa() {
        return consSa;
    }

    public void setConsSa(String consSa) {
        this.consSa = consSa;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getObsequioChf() {
        return obsequioChf;
    }

    public void setObsequioChf(String obsequioChf) {
        this.obsequioChf = obsequioChf;
    }

    public String getcDatosParto() {
        return cDatosParto;
    }

    public void setcDatosParto(String cDatosParto) {
        this.cDatosParto = cDatosParto;
    }

    public String getReConsChf18() {
        return reConsChf18;
    }

    public void setReConsChf18(String reConsChf18) {
        this.reConsChf18 = reConsChf18;
    }

    public String getPosDengue() {
        return posDengue;
    }

    public void setPosDengue(String posDengue) {
        this.posDengue = posDengue;
    }

    public String getMxSuperficie() {
        return mxSuperficie;
    }

    public void setMxSuperficie(String mxSuperficie) {
        this.mxSuperficie = mxSuperficie;
    }

    public String getMostrarAlfabeto() {
        return mostrarAlfabeto;
    }

    public void setMostrarAlfabeto(String mostrarAlfabeto) {
        this.mostrarAlfabeto = mostrarAlfabeto;
    }

    public String getMostrarPadreAlfabeto() {
        return mostrarPadreAlfabeto;
    }

    public void setMostrarPadreAlfabeto(String mostrarPadreAlfabeto) {
        this.mostrarPadreAlfabeto = mostrarPadreAlfabeto;
    }

    public String getMostrarMadreAlfabeta() {
        return mostrarMadreAlfabeta;
    }

    public void setMostrarMadreAlfabeta(String mostrarMadreAlfabeta) {
        this.mostrarMadreAlfabeta = mostrarMadreAlfabeta;
    }

    public String getMostrarNumParto() {
        return mostrarNumParto;
    }

    public void setMostrarNumParto(String mostrarNumParto) {
        this.mostrarNumParto = mostrarNumParto;
    }

    public String getAntecedenteTutorCP() {
        return antecedenteTutorCP;
    }

    public void setAntecedenteTutorCP(String antecedenteTutorCP) {
        this.antecedenteTutorCP = antecedenteTutorCP;
    }

    public String getConsCovid19() {
        return consCovid19;
    }

    public void setConsCovid19(String consCovid19) {
        this.consCovid19 = consCovid19;
    }

    public String getSubEstudios() {
        return subEstudios;
    }

    public void setSubEstudios(String subEstudios) {
        this.subEstudios = subEstudios;
    }

    public String getConsChf() {
        return consChf;
    }

    public void setConsChf(String consChf) {
        this.consChf = consChf;
    }

    public String getCuestCovid() {
        return cuestCovid;
    }

    public void setCuestCovid(String cuestCovid) {
        this.cuestCovid = cuestCovid;
    }

    public String getMuestraCovid() {
        return muestraCovid;
    }

    public void setMuestraCovid(String muestraCovid) {
        this.muestraCovid = muestraCovid;
    }

    public String getPosCovid() {
        return posCovid;
    }

    public void setPosCovid(String posCovid) {
        this.posCovid = posCovid;
    }

    public String getConsDenParteE() {
        return consDenParteE;
    }

    public void setConsDenParteE(String consDenParteE) {
        this.consDenParteE = consDenParteE;
    }

    public String getMxDenParteE() {
        return mxDenParteE;
    }

    public void setMxDenParteE(String mxDenParteE) {
        this.mxDenParteE = mxDenParteE;
    }

    public String getInformacionRetiro() {
        return informacionRetiro;
    }

    public void setInformacionRetiro(String informacionRetiro) {
        this.informacionRetiro = informacionRetiro;
    }

    public String getPerimetroAbdominal() {
        return perimetroAbdominal;
    }

    public void setPerimetroAbdominal(String perimetroAbdominal) {
        this.perimetroAbdominal = perimetroAbdominal;
    }

    public String getEsatUsuario() {
        return esatUsuario;
    }

    public void setEsatUsuario(String esatUsuario) {
        this.esatUsuario = esatUsuario;
    }

    public String getEsatUsuarioCc() {
        return esatUsuarioCc;
    }

    public void setEsatUsuarioCc(String esatUsuarioCc) {
        this.esatUsuarioCc = esatUsuarioCc;
    }
}

