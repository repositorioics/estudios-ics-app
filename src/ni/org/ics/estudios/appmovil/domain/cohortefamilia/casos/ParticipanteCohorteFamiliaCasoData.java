package ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */
public class ParticipanteCohorteFamiliaCasoData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ParticipanteCohorteFamiliaCaso participante;
	private int numVisitas;
	private Date fechaUltimaVisita;
	
	public ParticipanteCohorteFamiliaCaso getParticipante() {
		return participante;
	}
	public void setParticipante(ParticipanteCohorteFamiliaCaso participante) {
		this.participante = participante;
	}
	public int getNumVisitas() {
		return numVisitas;
	}
	public void setNumVisitas(int numVisitas) {
		this.numVisitas = numVisitas;
	}
	public Date getFechaUltimaVisita() {
		return fechaUltimaVisita;
	}
	public void setFechaUltimaVisita(Date fechaUltimaVisita) {
		this.fechaUltimaVisita = fechaUltimaVisita;
	}

}
