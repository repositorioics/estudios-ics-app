package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;


public class PesoyTallaId implements Serializable {
	/**
	 * Objeto que representa la clave unica de una encuesta de participante
	 * 
	 * @author Brenda Lopez
	 **/

	private static final long serialVersionUID = 1L;
	private Integer codigo;
	private Date fechaPT;

	public PesoyTallaId() {

	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PesoyTallaId))
			return false;
		PesoyTallaId castOther = (PesoyTallaId) other;
		return (this.getCodigo() == castOther.getCodigo())
				&& (this.getFechaPT() == castOther.getFechaPT());
	}

	public int hashCode() {
		int result = 17;
		result = 37 * 3 + this.getCodigo();
		result = 37 * result + Integer.valueOf(this.getFechaPT().toString());
		return result;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Date getFechaPT() {
		return fechaPT;
	}

	public void setFechaPT(Date fechaPT) {
		this.fechaPT = fechaPT;
	}

	@Override
	public String toString() {
		return this.codigo.toString();
	}

}