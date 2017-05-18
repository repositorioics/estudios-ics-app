package ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;

import java.util.Date;

/**
 * Simple objeto de dominio que representa los datos de parto de los
 * participantes en el estudio
 * 
 * @author William Aviles
 **/

public class EncuestaDatosPartoBB extends BaseMetaData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
    private ParticipanteCohorteFamilia participante;
	private String tipoParto;
	private String tiempoEmb_sndr;
	private Integer tiempoEmbSemana;
	private String docMedTiempoEmb_sn;
	private String docMedTiempoEmb;
	private String otroDocMedTiempoEmb;
	private Date fum;
	private Integer sg;
	private String fumFueraRango_sn;
	private String fumFueraRango_razon;
	private Integer edadGest;
	private String docMedEdadGest_sn;
	private String docMedEdadGest;
	private String OtroDocMedEdadGest;
	private String prematuro_sndr;
	private String pesoBB_sndr;
	private Integer pesoBB;
	private String docMedPesoBB_sn;
	private String docMedPesoBB;
	private String otroDocMedPesoBB;
	private Integer otrorecurso1;
	private Integer otrorecurso2;

    public ParticipanteCohorteFamilia getParticipante() {
        return participante;
    }

    public void setParticipante(ParticipanteCohorteFamilia participante) {
        this.participante = participante;
    }

    public String getTipoParto() {
        return tipoParto;
    }

    public void setTipoParto(String tipoParto) {
        this.tipoParto = tipoParto;
    }

    public String getTiempoEmb_sndr() {
        return tiempoEmb_sndr;
    }

    public void setTiempoEmb_sndr(String tiempoEmb_sndr) {
        this.tiempoEmb_sndr = tiempoEmb_sndr;
    }

    public Integer getTiempoEmbSemana() {
        return tiempoEmbSemana;
    }

    public void setTiempoEmbSemana(Integer tiempoEmbSemana) {
        this.tiempoEmbSemana = tiempoEmbSemana;
    }

    public String getDocMedTiempoEmb_sn() {
        return docMedTiempoEmb_sn;
    }

    public void setDocMedTiempoEmb_sn(String docMedTiempoEmb_sn) {
        this.docMedTiempoEmb_sn = docMedTiempoEmb_sn;
    }

    public String getDocMedTiempoEmb() {
        return docMedTiempoEmb;
    }

    public void setDocMedTiempoEmb(String docMedTiempoEmb) {
        this.docMedTiempoEmb = docMedTiempoEmb;
    }

    public String getOtroDocMedTiempoEmb() {
        return otroDocMedTiempoEmb;
    }

    public void setOtroDocMedTiempoEmb(String otroDocMedTiempoEmb) {
        this.otroDocMedTiempoEmb = otroDocMedTiempoEmb;
    }

    public Date getFum() {
        return fum;
    }

    public void setFum(Date fum) {
        this.fum = fum;
    }

    public Integer getSg() {
        return sg;
    }

    public void setSg(Integer sg) {
        this.sg = sg;
    }

    public String getFumFueraRango_sn() {
        return fumFueraRango_sn;
    }

    public void setFumFueraRango_sn(String fumFueraRango_sn) {
        this.fumFueraRango_sn = fumFueraRango_sn;
    }

    public String getFumFueraRango_razon() {
        return fumFueraRango_razon;
    }

    public void setFumFueraRango_razon(String fumFueraRango_razon) {
        this.fumFueraRango_razon = fumFueraRango_razon;
    }

    public Integer getEdadGest() {
        return edadGest;
    }

    public void setEdadGest(Integer edadGest) {
        this.edadGest = edadGest;
    }

    public String getDocMedEdadGest_sn() {
        return docMedEdadGest_sn;
    }

    public void setDocMedEdadGest_sn(String docMedEdadGest_sn) {
        this.docMedEdadGest_sn = docMedEdadGest_sn;
    }

    public String getDocMedEdadGest() {
        return docMedEdadGest;
    }

    public void setDocMedEdadGest(String docMedEdadGest) {
        this.docMedEdadGest = docMedEdadGest;
    }

    public String getOtroDocMedEdadGest() {
        return OtroDocMedEdadGest;
    }

    public void setOtroDocMedEdadGest(String otroDocMedEdadGest) {
        OtroDocMedEdadGest = otroDocMedEdadGest;
    }

    public String getPrematuro_sndr() {
        return prematuro_sndr;
    }

    public void setPrematuro_sndr(String prematuro_sndr) {
        this.prematuro_sndr = prematuro_sndr;
    }

    public String getPesoBB_sndr() {
        return pesoBB_sndr;
    }

    public void setPesoBB_sndr(String pesoBB_sndr) {
        this.pesoBB_sndr = pesoBB_sndr;
    }

    public Integer getPesoBB() {
        return pesoBB;
    }

    public void setPesoBB(Integer pesoBB) {
        this.pesoBB = pesoBB;
    }

    public String getDocMedPesoBB_sn() {
        return docMedPesoBB_sn;
    }

    public void setDocMedPesoBB_sn(String docMedPesoBB_sn) {
        this.docMedPesoBB_sn = docMedPesoBB_sn;
    }

    public String getDocMedPesoBB() {
        return docMedPesoBB;
    }

    public void setDocMedPesoBB(String docMedPesoBB) {
        this.docMedPesoBB = docMedPesoBB;
    }

    public String getOtroDocMedPesoBB() {
        return otroDocMedPesoBB;
    }

    public void setOtroDocMedPesoBB(String otroDocMedPesoBB) {
        this.otroDocMedPesoBB = otroDocMedPesoBB;
    }

    public Integer getOtrorecurso1() {
        return otrorecurso1;
    }

    public void setOtrorecurso1(Integer otrorecurso1) {
        this.otrorecurso1 = otrorecurso1;
    }

    public Integer getOtrorecurso2() {
        return otrorecurso2;
    }

    public void setOtrorecurso2(Integer otrorecurso2) {
        this.otrorecurso2 = otrorecurso2;
    }

    @Override
    public String toString() {
        return "EncuestaDatosPartoBB{" + participante.getParticipanteCHF() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncuestaDatosPartoBB)) return false;

        EncuestaDatosPartoBB that = (EncuestaDatosPartoBB) o;

        return  (!participante.equals(that.participante));
    }

    @Override
    public int hashCode() {
        return participante.hashCode();
    }
}
