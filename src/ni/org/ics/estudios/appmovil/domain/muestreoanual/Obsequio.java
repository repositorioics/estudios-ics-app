package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;

/**
 * Simple objeto de dominio que representa los datos de la entrega de obsequios
 * 
 * @author Brenda Lopez
 **/

public class Obsequio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private ObsequioId obId;
	private Integer obseqSN;
	private Integer carnetSN;
	
	private String persRecCarnet;
	private Integer relacionFam;
	private String otroRelacionFam;
	private Integer telefono;
	private Integer cmDomicilio;
	private Integer barrio;
	private String dire;
	private String observaciones;
	
	private MovilInfo movilInfo;
	private Integer otrorecurso1;

	public Integer getOtrorecurso1() {
		return otrorecurso1;
	}

	public void setOtrorecurso1(Integer otrorecurso1) {
		this.otrorecurso1 = otrorecurso1;
	}

	public Integer getObseqSN() {
		return obseqSN;
	}

	public void setObseqSN(Integer obseqSN) {
		this.obseqSN = obseqSN;
	}

	public Integer getCarnetSN() {
		return carnetSN;
	}

	public void setCarnetSN(Integer carnetSN) {
		this.carnetSN = carnetSN;
	}

	public String getPersRecCarnet() {
		return persRecCarnet;
	}

	public void setPersRecCarnet(String persRecCarnet) {
		this.persRecCarnet = persRecCarnet;
	}

	public Integer getRelacionFam() {
		return relacionFam;
	}

	public void setRelacionFam(Integer relacionFam) {
		this.relacionFam = relacionFam;
	}

	public String getOtroRelacionFam() {
		return otroRelacionFam;
	}

	public void setOtroRelacionFam(String otroRelacionFam) {
		this.otroRelacionFam = otroRelacionFam;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public Integer getCmDomicilio() {
		return cmDomicilio;
	}

	public void setCmDomicilio(Integer cmDomicilio) {
		this.cmDomicilio = cmDomicilio;
	}

	public Integer getBarrio() {
		return barrio;
	}

	public void setBarrio(Integer barrio) {
		this.barrio = barrio;
	}

	public String getDire() {
		return dire;
	}

	public void setDire(String dire) {
		this.dire = dire;
	}

	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
	}

	public ObsequioId getObId() {
		return obId;
	}

	public void setObId(ObsequioId obId) {
		this.obId = obId;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	@Override
	public String toString() {
		return this.getObId().getCodigo().toString();
	}
}
