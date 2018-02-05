package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;

public class PinchazoId implements Serializable {
	/**
	 * Objeto que representa la clave unica
	 * 
	 * @author Brenda Lopez
	 **/

	private static final long serialVersionUID = 1L;
	private Integer codigo;
	private Date fechaPinchazo;

	public PinchazoId() {

	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PinchazoId))
			return false;
		PinchazoId castOther = (PinchazoId) other;
		return (this.getCodigo() == castOther.getCodigo())
				&& (this.getFechaPinchazo() == castOther.getFechaPinchazo());
	}

	public int hashCode() {
		int result = 17;
		result = 37 * 3 + this.getCodigo();
		result = 37 * result + Integer.valueOf(this.getFechaPinchazo().toString());
		return result;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public Date getFechaPinchazo() {
		return fechaPinchazo;
	}

	public void setFechaPinchazo(Date fechaPinchazo) {
		this.fechaPinchazo = fechaPinchazo;
	}




}