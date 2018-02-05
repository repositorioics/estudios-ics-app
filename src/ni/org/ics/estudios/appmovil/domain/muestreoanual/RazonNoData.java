package ni.org.ics.estudios.appmovil.domain.muestreoanual;

/**
 * Simple objeto de dominio que representa los datos de la toma de muestra
 * 
 * @author Brenda Lopez
 **/

public class RazonNoData {


	/**
	 * 
	 */
	
	private RazonNoDataId rndId;
	private Integer razon;
	private String otraRazon;
	private String username;
	private String estado;

	public RazonNoDataId getRndId() {
		return rndId;
	}

	public void setRndId(RazonNoDataId rndId) {
		this.rndId = rndId;
	}
	
	public Integer getRazon() {
		return razon;
	}

	public void setRazon(Integer razon) {
		this.razon = razon;
	}

	public String getOtraRazon() {
		return otraRazon;
	}

	public void setOtraRazon(String otraRazon) {
		this.otraRazon = otraRazon;
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
