package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;

/**
 * Simple objeto de dominio que representa los datos de la codigos en la casa
 * 
 * @author Brenda Lopez
 **/

public class CodigosCasas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private Date fechaRegistro;
	private Integer codCasa;
	private Integer codigoComun;
	private Integer codigoRelacionado;
	private String username;
	private String estado;

	
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	public Integer getCodCasa() {
		return codCasa;
	}

	public void setCodCasa(Integer codCasa) {
		this.codCasa = codCasa;
	}

	public Integer getCodigoComun() {
		return codigoComun;
	}

	public void setCodigoComun(Integer codigoComun) {
		this.codigoComun = codigoComun;
	}

	public Integer getCodigoRelacionado() {
		return codigoRelacionado;
	}

	public void setCodigoRelacionado(Integer codigoRelacionado) {
		this.codigoRelacionado = codigoRelacionado;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString(){
		return this.getCodigoRelacionado().toString();
	}

}
