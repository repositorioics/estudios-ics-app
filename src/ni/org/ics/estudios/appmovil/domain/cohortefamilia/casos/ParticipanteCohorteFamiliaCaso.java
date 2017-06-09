package ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos;

import java.util.Date;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;

/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */
public class ParticipanteCohorteFamiliaCaso extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCasoParticipante;
	private CasaCohorteFamiliaCaso codigoCaso;
	private ParticipanteCohorteFamilia participante;
	private String enfermo;
	private Date fechaEnfermedad;
	
	public String getCodigoCasoParticipante() {
		return codigoCasoParticipante;
	}

	public void setCodigoCasoParticipante(String codigoCasoParticipante) {
		this.codigoCasoParticipante = codigoCasoParticipante;
	}

	public CasaCohorteFamiliaCaso getCodigoCaso() {
		return codigoCaso;
	}

	public void setCodigoCaso(CasaCohorteFamiliaCaso codigoCaso) {
		this.codigoCaso = codigoCaso;
	}
	
	public ParticipanteCohorteFamilia getParticipante() {
		return participante;
	}

	public void setParticipante(ParticipanteCohorteFamilia participante) {
		this.participante = participante;
	}

	public String getEnfermo() {
		return enfermo;
	}

	public void setEnfermo(String enfermo) {
		this.enfermo = enfermo;
	}

	public Date getFechaEnfermedad() {
		return fechaEnfermedad;
	}

	public void setFechaEnfermedad(Date fechaEnfermedad) {
		this.fechaEnfermedad = fechaEnfermedad;
	}
	@Override
	public String toString(){
		return codigoCaso.getCasa().getCodigoCHF() + "-" + participante.getParticipante().getCodigo() + "-" + codigoCaso.getFechaInicio();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipanteCohorteFamiliaCaso)) return false;

        ParticipanteCohorteFamiliaCaso that = (ParticipanteCohorteFamiliaCaso) o;

        if (!codigoCasoParticipante.equals(that.codigoCasoParticipante)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoCasoParticipante.hashCode();
        result = 31 * result + participante.hashCode();
        return result;
    }

}
