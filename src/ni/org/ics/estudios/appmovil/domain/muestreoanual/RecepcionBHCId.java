package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;

public class RecepcionBHCId implements Serializable {
	/**
	 * Objeto que representa la clave unica de una encuesta de participante
	 * 
	 * @author Brenda Lopez
	 **/

	private static final long serialVersionUID = 1L;
	private Integer codigo;
	private Date fechaRecBHC;

	public RecepcionBHCId() {

	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RecepcionBHCId))
			return false;
		RecepcionBHCId castOther = (RecepcionBHCId) other;
		return (this.getCodigo() == castOther.getCodigo())
				&& (this.getFechaRecBHC() == castOther.getFechaRecBHC());
	}

	public int hashCode() {
		int result = 17;
		result = 37 * 3 + this.getCodigo();
		result = 37 * result + Integer.valueOf(this.getFechaRecBHC().toString());
		return result;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Date getFechaRecBHC() {
		return fechaRecBHC;
	}

	public void setFechaRecBHC(Date fechaRecBHC) {
		this.fechaRecBHC = fechaRecBHC;
	}

}