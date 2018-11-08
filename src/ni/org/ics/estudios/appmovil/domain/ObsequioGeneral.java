package ni.org.ics.estudios.appmovil.domain;

import java.io.Serializable;

/**
 * Representa los datos de la entrega de obsequios
 * 
 * @author Miguel Salinas
 **/

public class ObsequioGeneral extends BaseMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String id;
    private Integer participante;
    private String casa;
    private String casaChf;
    private String seguimiento;
    private String numVisitaSeguimiento;
    private String motivo;//1 Pediatrica, 2 Familia, 3 Seguimiento_inicial, 4 Seguimiento_final
	private Integer obsequioSN;
	private String personaRecibe;
	private Integer relacionFam;
	private String otraRelacionFam;
	private String telefono;
	private String observaciones;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getParticipante() {
        return participante;
    }

    public void setParticipante(Integer participante) {
        this.participante = participante;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public String getCasaChf() {
        return casaChf;
    }

    public void setCasaChf(String casaChf) {
        this.casaChf = casaChf;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public String getNumVisitaSeguimiento() {
        return numVisitaSeguimiento;
    }

    public void setNumVisitaSeguimiento(String numVisitaSeguimiento) {
        this.numVisitaSeguimiento = numVisitaSeguimiento;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getObsequioSN() {
        return obsequioSN;
    }

    public void setObsequioSN(Integer obsequioSN) {
        this.obsequioSN = obsequioSN;
    }

    public String getPersonaRecibe() {
        return personaRecibe;
    }

    public void setPersonaRecibe(String personaRecibe) {
        this.personaRecibe = personaRecibe;
    }

    public Integer getRelacionFam() {
        return relacionFam;
    }

    public void setRelacionFam(Integer relacionFam) {
        this.relacionFam = relacionFam;
    }

    public String getOtraRelacionFam() {
        return otraRelacionFam;
    }

    public void setOtraRelacionFam(String otraRelacionFam) {
        this.otraRelacionFam = otraRelacionFam;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return id;
    }
}
