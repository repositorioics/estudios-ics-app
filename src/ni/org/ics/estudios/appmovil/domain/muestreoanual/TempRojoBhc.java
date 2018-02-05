package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple objeto de dominio que representa los datos de la toma de muestra
 * 
 * @author Brenda Lopez
 **/

public class TempRojoBhc {

	/**
	 * 
	 */
	
	private TempRojoBhcId tempRojoBhcId;
	private Double temperatura;
	private String lugar;
	private String observacion;
	private String username;
	private String estado;
	private Date fecreg;

	public TempRojoBhcId getTempRojoBhcId() {
		return tempRojoBhcId;
	}

	public void setTempRojoBhcId(TempRojoBhcId tempRojoBhcId) {
		this.tempRojoBhcId = tempRojoBhcId;
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

	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}

	
	@Override
	public String toString(){
		SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return this.tempRojoBhcId.getRecurso()+ " "+ mDateFormat.format(this.getFecreg());
	}
}
