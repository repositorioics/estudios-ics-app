package ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos;

import java.util.Date;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */

public class VisitaFinalCaso extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ParticipanteCohorteFamiliaCaso codigoParticipanteCaso;
	private Date fechaVisita;
	private String enfermo;
	private String consTerreno;
	private String referidoCs;
	private String tratamiento;
	private String cualAntibiotico;
	private String sintResp;
	private String fiebre;
	private String tos;
	private String dolorGarganta;
	private String secrecionNasal;
	private Date fif;
	private Date fff;
	private Date fitos;
	private Date fftos;
	private Date figg;
	private Date ffgg;
	private Date fisn;
	private Date ffsn;
    
	public ParticipanteCohorteFamiliaCaso getCodigoParticipanteCaso() {
		return codigoParticipanteCaso;
	}

	public void setCodigoParticipanteCaso(ParticipanteCohorteFamiliaCaso codigoParticipanteCaso) {
		this.codigoParticipanteCaso = codigoParticipanteCaso;
	}
	
	public Date getFechaVisita() {
		return fechaVisita;
	}

	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	public String getEnfermo() {
		return enfermo;
	}

	public void setEnfermo(String enfermo) {
		this.enfermo = enfermo;
	}

	public String getConsTerreno() {
		return consTerreno;
	}

	public void setConsTerreno(String consTerreno) {
		this.consTerreno = consTerreno;
	}

	public String getReferidoCs() {
		return referidoCs;
	}

	public void setReferidoCs(String referidoCs) {
		this.referidoCs = referidoCs;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getCualAntibiotico() {
		return cualAntibiotico;
	}

	public void setCualAntibiotico(String cualAntibiotico) {
		this.cualAntibiotico = cualAntibiotico;
	}

	public String getSintResp() {
		return sintResp;
	}

	public void setSintResp(String sintResp) {
		this.sintResp = sintResp;
	}

	public String getFiebre() {
		return fiebre;
	}

	public void setFiebre(String fiebre) {
		this.fiebre = fiebre;
	}

	public String getTos() {
		return tos;
	}

	public void setTos(String tos) {
		this.tos = tos;
	}

	public String getDolorGarganta() {
		return dolorGarganta;
	}

	public void setDolorGarganta(String dolorGarganta) {
		this.dolorGarganta = dolorGarganta;
	}

	public String getSecrecionNasal() {
		return secrecionNasal;
	}

	public void setSecrecionNasal(String secrecionNasal) {
		this.secrecionNasal = secrecionNasal;
	}

	public Date getFif() {
		return fif;
	}

	public void setFif(Date fif) {
		this.fif = fif;
	}

	public Date getFff() {
		return fff;
	}

	public void setFff(Date fff) {
		this.fff = fff;
	}

	public Date getFitos() {
		return fitos;
	}

	public void setFitos(Date fitos) {
		this.fitos = fitos;
	}

	public Date getFftos() {
		return fftos;
	}

	public void setFftos(Date fftos) {
		this.fftos = fftos;
	}

	public Date getFigg() {
		return figg;
	}

	public void setFigg(Date figg) {
		this.figg = figg;
	}

	public Date getFfgg() {
		return ffgg;
	}

	public void setFfgg(Date ffgg) {
		this.ffgg = ffgg;
	}

	public Date getFisn() {
		return fisn;
	}

	public void setFisn(Date fisn) {
		this.fisn = fisn;
	}

	public Date getFfsn() {
		return ffsn;
	}

	public void setFfsn(Date ffsn) {
		this.ffsn = ffsn;
	}

	@Override
	public String toString(){
		return codigoParticipanteCaso.getCodigoCaso().getCasa().getCodigoCHF() + "-" + codigoParticipanteCaso.getParticipante().getParticipante().getCodigo() + "-" + codigoParticipanteCaso.getCodigoCaso().getFechaInicio();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitaFinalCaso)) return false;

        VisitaFinalCaso that = (VisitaFinalCaso) o;

        if (!codigoParticipanteCaso.equals(that.codigoParticipanteCaso)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoParticipanteCaso.hashCode();
        result = 31 * result + codigoParticipanteCaso.getParticipante().hashCode();
        return result;
    }
}
