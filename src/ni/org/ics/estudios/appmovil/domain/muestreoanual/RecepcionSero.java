package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Simple objeto de dominio que representa los datos de la toma de muestra
 * 
 * @author Brenda Lopez
 **/

public class RecepcionSero {

	/**
	 * 
	 */
	
	private RecepcionSeroId recSeroId;
	private Double volumen;
	private String lugar;
	private String observacion;
	private String username;
	private String estado;
	private Date fecreg;

	public RecepcionSeroId getRecSeroId() {
		return recSeroId;
	}

	public void setRecSeroId(RecepcionSeroId recSeroId) {
		this.recSeroId = recSeroId;
	}

	public Double getVolumen() {
		return volumen;
	}

	public void setVolumen(Double volumen) {
		this.volumen = volumen;
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
	public String toString() {
		SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return this.recSeroId.getCodigo() + " "+ mDateFormat.format(this.recSeroId.getFechaRecSero());
	}

}
