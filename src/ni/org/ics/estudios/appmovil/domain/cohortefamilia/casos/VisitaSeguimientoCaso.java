package ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos;

import java.util.Date;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */

public class VisitaSeguimientoCaso extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCasoVisita;
	private ParticipanteCohorteFamiliaCaso codigoParticipanteCaso;
	private Date fechaVisita;
	private String visita;
	private String horaProbableVisita;
	private String expCS;
	private float temp;
	
	public String getCodigoCasoVisita() {
		return codigoCasoVisita;
	}

	public void setCodigoCasoVisita(String codigoCasoVisita) {
		this.codigoCasoVisita = codigoCasoVisita;
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

	public String getVisita() {
		return visita;
	}

	public void setVisita(String visita) {
		this.visita = visita;
	}

	public String getHoraProbableVisita() {
		return horaProbableVisita;
	}

	public void setHoraProbableVisita(String horaProbableVisita) {
		this.horaProbableVisita = horaProbableVisita;
	}

	public String getExpCS() {
		return expCS;
	}

	public void setExpCS(String expCS) {
		this.expCS = expCS;
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

	@Override
	public String toString(){
		return codigoParticipanteCaso.getCodigoCaso().getCasa().getCodigoCHF() + "-" + codigoParticipanteCaso.getParticipante().getParticipante().getCodigo() + "-" + codigoParticipanteCaso.getCodigoCaso().getFechaInicio();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitaSeguimientoCaso)) return false;

        VisitaSeguimientoCaso that = (VisitaSeguimientoCaso) o;

        if (!codigoCasoVisita.equals(that.codigoCasoVisita)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoCasoVisita.hashCode();
        result = 31 * result + codigoParticipanteCaso.getParticipante().hashCode();
        return result;
    }
}
