package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;


public class EncuestaParticipanteId implements Serializable {
	/**
	 * Objeto que representa la clave unica de una encuesta de participante
	 * 
	 * @author Brenda Lopez
	 **/

	private static final long serialVersionUID = 1L;
	private Integer codigo;
	private Date fechaEncPar;

	public EncuestaParticipanteId() {

	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EncuestaParticipanteId))
			return false;
		EncuestaParticipanteId castOther = (EncuestaParticipanteId) other;
		return (this.getCodigo() == castOther.getCodigo())
				&& (this.getFechaEncPar() == castOther.getFechaEncPar());
	}

	public int hashCode() {
		int result = 17;
		result = 37 * 3 + this.getCodigo();
		result = 37 * result + Integer.valueOf(this.getFechaEncPar().toString());
		return result;
	}
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Date getFechaEncPar() {
		return fechaEncPar;
	}

	public void setFechaEncPar(Date fechaEncPar) {
		this.fechaEncPar = fechaEncPar;
	}
	
	@Override
	public String toString() {
		return this.codigo.toString();
	}

}