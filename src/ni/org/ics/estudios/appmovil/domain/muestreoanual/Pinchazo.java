package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.util.Date;


/**
 * Simple objeto de dominio que representa los datos de la toma de muestra
 * 
 * @author Brenda Lopez
 **/

public class Pinchazo {

	/**
	 * 
	 */
	
	private PinchazoId pinId;
	private Integer numPin;
	private String lugar;
	private String observacion;
	private String username;
	private String estado;
	private Date fecreg;

	public PinchazoId getPinId() {
		return pinId;
	}

	public void setPinId(PinchazoId pinId) {
		this.pinId = pinId;
	}

	public Integer getNumPin() {
		return numPin;
	}

	public void setNumPin(Integer numPin) {
		this.numPin = numPin;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getFecreg() {
		return fecreg;
	}

	public void setFecreg(Date fecreg) {
		this.fecreg = fecreg;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString(){
		return this.getPinId().getCodigo().toString();
	}
}
