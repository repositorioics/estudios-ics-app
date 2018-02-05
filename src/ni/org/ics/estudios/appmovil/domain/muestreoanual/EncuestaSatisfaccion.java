package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;

/**
 * Simple objeto de dominio que representa los datos de la lactancia materna
 * 
 * @author Brenda Lopez
 **/

public class EncuestaSatisfaccion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private Date fechaEncuesta;
	private String estudio;
	private Integer atenPerEst;
	private Integer tiemAten;
	private Integer atenPerAdm;
	private Integer atenPerEnferm;
	private Integer atenPerMed;
	private Integer ambAten;
	private Integer atenPerLab;
	private Integer explDxEnf;
	private Integer fludenSN;
	private Integer fluConImp;
	private Integer denConImp;
	private Integer explPeligEnf;
	private Integer expMedCuid;
	private MovilInfo movilInfo;
	private Integer otrorecurso1;
	
	public Integer getOtrorecurso1() {
		return otrorecurso1;
	}

	public void setOtrorecurso1(Integer otrorecurso1) {
		this.otrorecurso1 = otrorecurso1;
	}

	public Date getFechaEncuesta() {
		return fechaEncuesta;
	}

	public void setFechaEncuesta(Date fechaEncuesta) {
		this.fechaEncuesta = fechaEncuesta;
	}

	public String getEstudio() {
		return estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public Integer getAtenPerEst() {
		return atenPerEst;
	}

	public void setAtenPerEst(Integer atenPerEst) {
		this.atenPerEst = atenPerEst;
	}

	public Integer getTiemAten() {
		return tiemAten;
	}

	public void setTiemAten(Integer tiemAten) {
		this.tiemAten = tiemAten;
	}

	public Integer getAtenPerAdm() {
		return atenPerAdm;
	}

	public void setAtenPerAdm(Integer atenPerAdm) {
		this.atenPerAdm = atenPerAdm;
	}

	public Integer getAtenPerEnferm() {
		return atenPerEnferm;
	}

	public void setAtenPerEnferm(Integer atenPerEnferm) {
		this.atenPerEnferm = atenPerEnferm;
	}

	public Integer getAtenPerMed() {
		return atenPerMed;
	}

	public void setAtenPerMed(Integer atenPerMed) {
		this.atenPerMed = atenPerMed;
	}

	public Integer getAmbAten() {
		return ambAten;
	}

	public void setAmbAten(Integer ambAten) {
		this.ambAten = ambAten;
	}

	public Integer getAtenPerLab() {
		return atenPerLab;
	}

	public void setAtenPerLab(Integer atenPerLab) {
		this.atenPerLab = atenPerLab;
	}

	public Integer getExplDxEnf() {
		return explDxEnf;
	}

	public void setExplDxEnf(Integer explDxEnf) {
		this.explDxEnf = explDxEnf;
	}

	public Integer getFludenSN() {
		return fludenSN;
	}

	public void setFludenSN(Integer fludenSN) {
		this.fludenSN = fludenSN;
	}

	public Integer getFluConImp() {
		return fluConImp;
	}

	public void setFluConImp(Integer fluConImp) {
		this.fluConImp = fluConImp;
	}

	public Integer getDenConImp() {
		return denConImp;
	}

	public void setDenConImp(Integer denConImp) {
		this.denConImp = denConImp;
	}

	public Integer getExplPeligEnf() {
		return explPeligEnf;
	}

	public void setExplPeligEnf(Integer explPeligEnf) {
		this.explPeligEnf = explPeligEnf;
	}

	public Integer getExpMedCuid() {
		return expMedCuid;
	}

	public void setExpMedCuid(Integer expMedCuid) {
		this.expMedCuid = expMedCuid;
	}

	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
	}
	
	@Override
	public String toString() {
		return this.getFechaEncuesta().toString();
	}

}
