package ni.org.ics.estudios.appmovil.domain.cohortefamilia;

import java.io.Serializable;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;

/**
 * Simple objeto de dominio que representa un participante de los estudios de cohorte de familia
 * 
 * @author William Aviles
 **/

public class ParticipanteCohorteFamilia extends BaseMetaData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Participante participante;
    private CasaCohorteFamilia casaCHF;

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public CasaCohorteFamilia getCasaCHF() {
		return casaCHF;
	}

	public void setCasaCHF(CasaCohorteFamilia casaCHF) {
		this.casaCHF = casaCHF;
	}

	@Override
    public String toString() {
        return "'" + participante.getCodigo() + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipanteCohorteFamilia)) return false;

        ParticipanteCohorteFamilia participante = (ParticipanteCohorteFamilia) o;

        return (!this.participante.getCodigo().equals(participante.participante.getCodigo()));
    }

    @Override
    public int hashCode() {
        return participante.getCodigo().hashCode();
    }
}
