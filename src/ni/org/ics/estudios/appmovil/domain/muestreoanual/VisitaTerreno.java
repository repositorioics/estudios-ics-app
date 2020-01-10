package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;


/**
 * Simple objeto de dominio que representa los datos de la visita al terreno
 * 
 * @author Brenda Lopez
 **/

public class VisitaTerreno implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private VisitaTerrenoId visitaId;
	private Integer visitaSN;
	private Integer motNoVisita;
	private String acomp;
	private Integer relacionFam;
	private Integer asentimiento;
	
	private MovilInfo movilInfo;
	private Integer otrorecurso1;
	private Integer otrorecurso2;
	
	private String otraRelacionFam;
	private String carnetSN;
	//para peso y talla muestreo 2020
	private String estudiaSN;
	private String nEscuela;
	private String otraEscuela;
	private String turno;


	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
	}

	public Integer getOtrorecurso1() {
		return otrorecurso1;
	}

	public void setOtrorecurso1(Integer otrorecurso1) {
		this.otrorecurso1 = otrorecurso1;
	}

	public Integer getOtrorecurso2() {
		return otrorecurso2;
	}

	public void setOtrorecurso2(Integer otrorecurso2) {
		this.otrorecurso2 = otrorecurso2;
	}

	public VisitaTerrenoId getVisitaId() {
		return visitaId;
	}

	public void setVisitaId(VisitaTerrenoId visitaId) {
		this.visitaId = visitaId;
	}

	public Integer getVisitaSN() {
		return visitaSN;
	}

	public void setVisitaSN(Integer visitaSN) {
		this.visitaSN = visitaSN;
	}

	public Integer getMotNoVisita() {
		return motNoVisita;
	}

	public void setMotNoVisita(Integer motNoVisita) {
		this.motNoVisita = motNoVisita;
	}

	public String getAcomp() {
		return acomp;
	}

	public void setAcomp(String acomp) {
		this.acomp = acomp;
	}

	public Integer getRelacionFam() {
		return relacionFam;
	}

	public void setRelacionFam(Integer relacionFam) {
		this.relacionFam = relacionFam;
	}
	
	public Integer getAsentimiento() {
		return asentimiento;
	}

	public void setAsentimiento(Integer asentimiento) {
		this.asentimiento = asentimiento;
	}

	public String getOtraRelacionFam() {
		return otraRelacionFam;
	}

	public void setOtraRelacionFam(String otraRelacionFam) {
		this.otraRelacionFam = otraRelacionFam;
	}

	public String getCarnetSN() {
		return carnetSN;
	}

	public void setCarnetSN(String carnetSN) {
		this.carnetSN = carnetSN;
	}

	public String getEstudiaSN() {
		return estudiaSN;
	}

	public void setEstudiaSN(String estudiaSN) {
		this.estudiaSN = estudiaSN;
	}

	public String getnEscuela() {
		return nEscuela;
	}

	public void setnEscuela(String nEscuela) {
		this.nEscuela = nEscuela;
	}

	public String getOtraEscuela() {
		return otraEscuela;
	}

	public void setOtraEscuela(String otraEscuela) {
		this.otraEscuela = otraEscuela;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
}
