package ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos;

import java.util.Date;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */

public class VisitaFallidaCaso extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoFallaVisita;
	private ParticipanteCohorteFamiliaCaso codigoParticipanteCaso;
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
		return codigoParticipanteCaso.getCodigoCaso().getCasa().getCodigoCHF() + "-" + codigoParticipanteCaso.getParticipante().getParticipante().getCodigo() + "-" + codigoParticipanteCaso.getCodigoCaso().getFechaInicio();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitaFallidaCaso)) return false;

        VisitaFallidaCaso that = (VisitaFallidaCaso) o;

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
