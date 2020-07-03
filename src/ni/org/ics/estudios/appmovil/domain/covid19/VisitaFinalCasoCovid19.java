package ni.org.ics.estudios.appmovil.domain.covid19;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

import java.util.Date;

/**
 * Created by Miguel Salinas on 01/07/2020.
 * V1.0
 */

public class VisitaFinalCasoCovid19 extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoVisitaFinal;
	private ParticipanteCasoCovid19 codigoParticipanteCaso;
	private Date fechaVisita;
	private String enfermo;
	private String consTerreno;
	private String referidoCS;
	private String tratamiento;
	private String queAntibiotico;
	private String otroMedicamento;
	private String sintResp;
	private String fueHospitalizado;
	private Date fechaIngreso;
	private Date fechaEgreso;
	private Integer diasHospitalizado;
	private String cualHospital;
	private String utilizoOxigeno;
	private String fueIntubado;
	private String estadoSalud;
	private String faltoTrabajoEscuela;
	private Integer diasFaltoTrabajoEscuela;

	public String getCodigoVisitaFinal() {
		return codigoVisitaFinal;
	}

	public void setCodigoVisitaFinal(String codigoVisitaFinal) {
		this.codigoVisitaFinal = codigoVisitaFinal;
	}

	public ParticipanteCasoCovid19 getCodigoParticipanteCaso() {
		return codigoParticipanteCaso;
	}

	public void setCodigoParticipanteCaso(ParticipanteCasoCovid19 codigoParticipanteCaso) {
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

	public String getReferidoCS() {
		return referidoCS;
	}

	public void setReferidoCS(String referidoCS) {
		this.referidoCS = referidoCS;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getQueAntibiotico() {
		return queAntibiotico;
	}

	public void setQueAntibiotico(String queAntibiotico) {
		this.queAntibiotico = queAntibiotico;
	}

	public String getOtroMedicamento() {
		return otroMedicamento;
	}

	public void setOtroMedicamento(String otroMedicamento) {
		this.otroMedicamento = otroMedicamento;
	}

	public String getSintResp() {
		return sintResp;
	}

	public void setSintResp(String sintResp) {
		this.sintResp = sintResp;
	}

	public String getFueHospitalizado() {
		return fueHospitalizado;
	}

	public void setFueHospitalizado(String fueHospitalizado) {
		this.fueHospitalizado = fueHospitalizado;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaEgreso() {
		return fechaEgreso;
	}

	public void setFechaEgreso(Date fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}

	public Integer getDiasHospitalizado() {
		return diasHospitalizado;
	}

	public void setDiasHospitalizado(Integer diasHospitalizado) {
		this.diasHospitalizado = diasHospitalizado;
	}

	public String getCualHospital() {
		return cualHospital;
	}

	public void setCualHospital(String cualHospital) {
		this.cualHospital = cualHospital;
	}

	public String getUtilizoOxigeno() {
		return utilizoOxigeno;
	}

	public void setUtilizoOxigeno(String utilizoOxigeno) {
		this.utilizoOxigeno = utilizoOxigeno;
	}

	public String getFueIntubado() {
		return fueIntubado;
	}

	public void setFueIntubado(String fueIntubado) {
		this.fueIntubado = fueIntubado;
	}

	public String getEstadoSalud() {
		return estadoSalud;
	}

	public void setEstadoSalud(String estadoSalud) {
		this.estadoSalud = estadoSalud;
	}

	public String getFaltoTrabajoEscuela() {
		return faltoTrabajoEscuela;
	}

	public void setFaltoTrabajoEscuela(String faltoTrabajoEscuela) {
		this.faltoTrabajoEscuela = faltoTrabajoEscuela;
	}

	public Integer getDiasFaltoTrabajoEscuela() {
		return diasFaltoTrabajoEscuela;
	}

	public void setDiasFaltoTrabajoEscuela(Integer diasFaltoTrabajoEscuela) {
		this.diasFaltoTrabajoEscuela = diasFaltoTrabajoEscuela;
	}

	@Override
	public String toString(){
		return codigoParticipanteCaso.getParticipante().getCodigo() + "-" + codigoParticipanteCaso.getCodigoCaso().getFechaIngreso();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitaFinalCasoCovid19)) return false;

        VisitaFinalCasoCovid19 that = (VisitaFinalCasoCovid19) o;

        if (!codigoVisitaFinal.equals(that.codigoVisitaFinal)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoVisitaFinal.hashCode();
        result = 31 * result + codigoParticipanteCaso.getParticipante().hashCode();
        return result;
    }
}
