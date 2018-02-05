package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;


/**
 * Simple objeto de dominio que representa los datos de la toma de muestra
 * 
 * @author Brenda Lopez
 **/


public class Muestra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private MuestraId mId;
	private Integer fiebreM;
	private Integer consulta;
	private Integer tuboBHC;
	private Integer bhc_razonNo;
	private String bhc_otraRazonNo;
	private Integer tuboRojo;
	private Integer rojo_razonNo;
	private String rojo_otraRazonNo;
	private Integer tuboLeu;
	private Integer pbmc_razonNo;
	private String pbmc_otraRazonNo;
	private String horaBHC;
	private String horaPBMC;
	private String horaInicioPax;
	private String horaFinPax;
	private String codPax;
	private String terreno;
	private Integer pinchazos;
	private MovilInfo movilInfo;
	private Integer otrorecurso1;
	private Integer otrorecurso2;


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

	public Integer getConsulta() {
		return consulta;
	}

	public void setConsulta(Integer consulta) {
		this.consulta = consulta;
	}

	public Integer getTuboBHC() {
		return tuboBHC;
	}

	public void setTuboBHC(Integer tuboBHC) {
		this.tuboBHC = tuboBHC;
	}

	public Integer getTuboRojo() {
		return tuboRojo;
	}

	public void setTuboRojo(Integer tuboRojo) {
		this.tuboRojo = tuboRojo;
	}

	public Integer getTuboLeu() {
		return tuboLeu;
	}

	public void setTuboLeu(Integer tuboLeu) {
		this.tuboLeu = tuboLeu;
	}

	public Integer getPinchazos() {
		return pinchazos;
	}

	public void setPinchazos(Integer pinchazos) {
		this.pinchazos = pinchazos;
	}

	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
	}

	public MuestraId getmId() {
		return mId;
	}

	public void setmId(MuestraId mId) {
		this.mId = mId;
	}

	public Integer getFiebreM() {
		return fiebreM;
	}

	public void setFiebreM(Integer fiebreM) {
		this.fiebreM = fiebreM;
	}
	
	
	
	public Integer getBhc_razonNo() {
		return bhc_razonNo;
	}

	public void setBhc_razonNo(Integer bhc_razonNo) {
		this.bhc_razonNo = bhc_razonNo;
	}

	public String getBhc_otraRazonNo() {
		return bhc_otraRazonNo;
	}

	public void setBhc_otraRazonNo(String bhc_otraRazonNo) {
		this.bhc_otraRazonNo = bhc_otraRazonNo;
	}

	public Integer getRojo_razonNo() {
		return rojo_razonNo;
	}

	public void setRojo_razonNo(Integer rojo_razonNo) {
		this.rojo_razonNo = rojo_razonNo;
	}

	public String getRojo_otraRazonNo() {
		return rojo_otraRazonNo;
	}

	public void setRojo_otraRazonNo(String rojo_otraRazonNo) {
		this.rojo_otraRazonNo = rojo_otraRazonNo;
	}

	public Integer getPbmc_razonNo() {
		return pbmc_razonNo;
	}

	public void setPbmc_razonNo(Integer pbmc_razonNo) {
		this.pbmc_razonNo = pbmc_razonNo;
	}

	public String getPbmc_otraRazonNo() {
		return pbmc_otraRazonNo;
	}

	public void setPbmc_otraRazonNo(String pbmc_otraRazonNo) {
		this.pbmc_otraRazonNo = pbmc_otraRazonNo;
	}
	

	public String getHoraBHC() {
		return horaBHC;
	}

	public void setHoraBHC(String horaBHC) {
		this.horaBHC = horaBHC;
	}

	public String getHoraPBMC() {
		return horaPBMC;
	}

	public void setHoraPBMC(String horaPBMC) {
		this.horaPBMC = horaPBMC;
	}

	public String getHoraInicioPax() {
		return horaInicioPax;
	}

	public void setHoraInicioPax(String horaInicioPax) {
		this.horaInicioPax = horaInicioPax;
	}

	public String getHoraFinPax() {
		return horaFinPax;
	}

	public void setHoraFinPax(String horaFinPax) {
		this.horaFinPax = horaFinPax;
	}

	public String getCodPax() {
		return codPax;
	}

	public void setCodPax(String codPax) {
		this.codPax = codPax;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	@Override
	public String toString() {
		return this.getmId().getCodigo().toString();
	}
}
