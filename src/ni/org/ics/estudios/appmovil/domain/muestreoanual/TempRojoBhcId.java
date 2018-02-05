package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;

public class TempRojoBhcId implements Serializable {
	/**
	 * Objeto que representa la clave unica
	 * 
	 * @author Brenda Lopez
	 **/

	private static final long serialVersionUID = 1L;
	private String recurso;
	private Date fechaTempRojoBhc;

	public TempRojoBhcId() {

	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TempRojoBhcId))
			return false;
		TempRojoBhcId castOther = (TempRojoBhcId) other;
		return (this.getRecurso() == castOther.getRecurso())
				&& (this.getFechaTempRojoBhc() == castOther.getFechaTempRojoBhc());
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + Integer.valueOf(this.getFechaTempRojoBhc().toString());
		return result;
	}


	public Date getFechaTempRojoBhc() {
		return fechaTempRojoBhc;
	}

	public void setFechaTempRojoBhc(Date fechaTempRojoBhc) {
		this.fechaTempRojoBhc = fechaTempRojoBhc;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

}