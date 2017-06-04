package ni.org.ics.estudios.appmovil.domain.seroprevalencia;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;

import java.io.Serializable;

/**
 * Simple objeto de dominio que representa un participante del estudio seroprevalencia arbovirus
 * 
 * @author William Aviles
 **/

public class ParticipanteSeroprevalencia extends BaseMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//private String participanteSA;
	private Participante participante;
    private CasaCohorteFamilia casaCHF;
/*
    public String getParticipanteSA() {
		return participanteSA;
	}

	public void setParticipanteSA(String participanteSA) {
		this.participanteSA = participanteSA;
	}
*/
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
        if (!(o instanceof ParticipanteSeroprevalencia)) return false;

        ParticipanteSeroprevalencia participante = (ParticipanteSeroprevalencia) o;

        return (!this.participante.getCodigo().equals(participante.getParticipante().getCodigo()));
    }

    @Override
    public int hashCode() {
        return participante.getCodigo().hashCode();
    }

}
