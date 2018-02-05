package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.util.Date;

/**
 * Simple objeto de dominio que representa los datos de la codigos en la casa
 * 
 * @author Brenda Lopez
 **/

public class CambiosCasas {

	/**
	 * 
	 */
	
	private Date fechaRegistro;
	private Integer codigo;
	private Integer codCasaAnterior;
	private Integer codCasaActual;
	private String username;
	private String estado;

	
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodCasaAnterior() {
		return codCasaAnterior;
	}

	public void setCodCasaAnterior(Integer codCasaAnterior) {
		this.codCasaAnterior = codCasaAnterior;
	}

	public Integer getCodCasaActual() {
		return codCasaActual;
	}

	public void setCodCasaActual(Integer codCasaActual) {
		this.codCasaActual = codCasaActual;
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

}
