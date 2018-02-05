package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;


public class DatosPartoBBId implements Serializable {
	/**
	 * Objeto que representa la clave unica de una encuesta de participante
	 * 
	 * @author Brenda Lopez
	 **/

	private static final long serialVersionUID = 1L;
	private Integer codigo;
	private Date fechaDatosParto;

	public DatosPartoBBId() {

	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DatosPartoBBId))
			return false;
		DatosPartoBBId castOther = (DatosPartoBBId) other;
		return ((this.getCodigo() == castOther.getCodigo())
				&& (this.getFechaDatosParto() == castOther.getFechaDatosParto()));
	}

	public int hashCode() {
		int result = 17;
		result = 37 * 3 + this.getCodigo();
		result = 37 * result + Integer.valueOf(this.getFechaDatosParto().toString());
		return result;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Date getFechaDatosParto() {
		return fechaDatosParto;
	}

	public void setFechaDatosParto(Date fechaDatosParto) {
		this.fechaDatosParto = fechaDatosParto;
	}



}