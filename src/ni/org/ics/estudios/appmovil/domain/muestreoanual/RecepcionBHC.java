package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Simple objeto de dominio que representa los datos de la toma de muestra
 * 
 * @author Brenda Lopez
 **/

public class RecepcionBHC {

	/**
	 * 
	 */

	private RecepcionBHCId recBhcId;
	private Boolean paxgene;
	private Double volumen;
	private String lugar;
	private String observacion;
	private String username;
	private String estado;
	private Date fecreg;

	public RecepcionBHCId getRecBhcId() {
		return recBhcId;
	}

	public void setRecBhcId(RecepcionBHCId recBhcId) {
		this.recBhcId = recBhcId;
	}

	public Boolean getPaxgene() {
		return paxgene;
	}

	public void setPaxgene(Boolean paxgene) {
		this.paxgene = paxgene;
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

	@Override
	public String toString() {
		SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return this.recBhcId.getCodigo() + " "+ mDateFormat.format(this.recBhcId.getFechaRecBHC());
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
