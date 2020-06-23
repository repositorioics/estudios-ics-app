package ni.org.ics.estudios.appmovil.domain.covid19;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

import java.util.Date;

/**
 * Created by Miguel Salinas on 15/06/2020.
 * V1.0
 */

public class VisitaFallidaCasoCovid19 extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoFallaVisita;
	private ParticipanteCasoCovid19 codigoParticipanteCaso;
	private Date fechaVisita;
	private String razonVisitaFallida;
	private String otraRazon;
    private String visita;
	
	public String getCodigoFallaVisita() {
		return codigoFallaVisita;
	}

	public void setCodigoFallaVisita(String codigoFallaVisita) {
		this.codigoFallaVisita = codigoFallaVisita;
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

	public String getRazonVisitaFallida() {
		return razonVisitaFallida;
	}

	public void setRazonVisitaFallida(String razonVisitaFallida) {
		this.razonVisitaFallida = razonVisitaFallida;
	}

	public String getOtraRazon() {
		return otraRazon;
	}

	public void setOtraRazon(String otraRazon) {
		this.otraRazon = otraRazon;
	}

    public String getVisita() {
        return visita;
    }

    public void setVisita(String visita) {
        this.visita = visita;
    }

    @Override
	public String toString(){
		return codigoParticipanteCaso.getCodigoCaso().getCasa().getCodigoCHF() + "-" + codigoParticipanteCaso.getParticipante().getCodigo() + "-" + codigoParticipanteCaso.getCodigoCaso().getFechaIngreso();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitaFallidaCasoCovid19)) return false;

        VisitaFallidaCasoCovid19 that = (VisitaFallidaCasoCovid19) o;

        if (!codigoFallaVisita.equals(that.codigoFallaVisita)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoFallaVisita.hashCode();
        result = 31 * result + codigoParticipanteCaso.getParticipante().hashCode();
        return result;
    }
}
