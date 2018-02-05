package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;

public class RecepcionSeroId implements Serializable {
	/**
	 * Objeto que representa la clave unica
	 * 
	 * @author Brenda Lopez
	 **/

	private static final long serialVersionUID = 1L;
	private Integer codigo;
	private Date fechaRecSero;

	public RecepcionSeroId() {

	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RecepcionSeroId))
			return false;
		RecepcionSeroId castOther = (RecepcionSeroId) other;
		return (this.getCodigo() == castOther.getCodigo())
				&& (this.getFechaRecSero() == castOther.getFechaRecSero());
	}

	public int hashCode() {
		int result = 17;
		result = 37 * 3 + this.getCodigo();
		result = 37 * result + Integer.valueOf(this.getFechaRecSero().toString());
		return result;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Date getFechaRecSero() {
		return fechaRecSero;
	}

	public void setFechaRecSero(Date fechaRecSero) {
		this.fechaRecSero = fechaRecSero;
	}


}