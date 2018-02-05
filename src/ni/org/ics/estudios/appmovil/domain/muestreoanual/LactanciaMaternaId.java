package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;

public class LactanciaMaternaId implements Serializable {
	/**
	 * Objeto que representa la clave unica de una encuesta de participante
	 * 
	 * @author Brenda Lopez
	 **/

	private static final long serialVersionUID = 1L;
	private Integer codigo;
	private Date fechaEncLM;

	public LactanciaMaternaId() {

	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LactanciaMaternaId))
			return false;
		LactanciaMaternaId castOther = (LactanciaMaternaId) other;
		return (this.getCodigo() == castOther.getCodigo())
				&& (this.getFechaEncLM() == castOther.getFechaEncLM());
	}

	public int hashCode() {
		int result = 17;
		result = 37 * 3 + this.getCodigo();
		result = 37 * result + Integer.valueOf(this.getFechaEncLM().toString());
		return result;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Date getFechaEncLM() {
		return fechaEncLM;
	}

	public void setFechaEncLM(Date fechaEncLM) {
		this.fechaEncLM = fechaEncLM;
	}

	@Override
	public String toString() {
		return this.codigo.toString();
	}
}