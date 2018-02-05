package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;


public class TempPbmcId implements Serializable {
	/**
	 * Objeto que representa la clave unica
	 * 
	 * @author Brenda Lopez
	 **/

	private static final long serialVersionUID = 1L;
	private String recurso;
	private Date fechaTempPbmc;

	public TempPbmcId() {

	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TempPbmcId))
			return false;
		TempPbmcId castOther = (TempPbmcId) other;
		return (this.getRecurso() == castOther.getRecurso())
				&& (this.getFechaTempPbmc() == castOther.getFechaTempPbmc());
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + Integer.valueOf(this.getFechaTempPbmc().toString());
		return result;
	}



	public Date getFechaTempPbmc() {
		return fechaTempPbmc;
	}

	public void setFechaTempPbmc(Date fechaTempPbmc) {
		this.fechaTempPbmc = fechaTempPbmc;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}



}