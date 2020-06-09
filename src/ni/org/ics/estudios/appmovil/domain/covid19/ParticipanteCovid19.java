package ni.org.ics.estudios.appmovil.domain.covid19;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;

import java.io.Serializable;

/**
 * Simple objeto de dominio que representa un participante del estudio Covid19
 * 
 * @author Miguel Salinas
 **/

public class ParticipanteCovid19 extends BaseMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Participante participante;

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	@Override
    public String toString() {
        return "'" + this.participante.getCodigo() + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipanteCovid19)) return false;

        ParticipanteCovid19 participante = (ParticipanteCovid19) o;

        return (this.participante.getCodigo().equals(participante.participante.getCodigo()));
    }

    @Override
    public int hashCode() {
        return participante.getCodigo().hashCode();
    }

}
