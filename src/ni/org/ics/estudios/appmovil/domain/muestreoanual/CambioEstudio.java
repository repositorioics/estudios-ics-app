package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;

/**
 * Simple objeto de dominio que representa los datos de reconsentimiento de Dengue
 * 
 * @author Brenda Lopez
 **/

public class CambioEstudio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private CambioEstudioId cambioEstudioId;
	
	private Integer parteAFlu;
	private String rechazoFlu;
	private Integer parteADen;
	private String rechazoDen;
	private String incFlu;
	private String excFlu;
	private String incDen;
	private String excDen;
	
	private Integer enfCronSN;
	private Integer enfCronica;
	private String oEnfCronica;
	private Integer tomaTx;
	private String cualesTx;
	
	private Integer asentimiento;
	
	private Integer parteBFlu;
	private Integer parteCFlu;
	
	private Integer parteBDen;
	private Integer parteCDen;
	private Integer parteDDen;
	private Integer parteEDen;
	private Integer parteFDen;
	private Integer asentimientoesc;
	private String firmcarta;
	private Integer relacionFam;
	private MovilInfo movilInfo;	

	
	public CambioEstudioId getCambioEstudioId() {
		return cambioEstudioId;
	}

	public void setCambioEstudioId(CambioEstudioId cambioEstudioId) {
		this.cambioEstudioId = cambioEstudioId;
	}
	
	public Integer getParteAFlu() {
		return parteAFlu;
	}

	public void setParteAFlu(Integer parteAFlu) {
		this.parteAFlu = parteAFlu;
	}

	public String getRechazoFlu() {
		return rechazoFlu;
	}

	public void setRechazoFlu(String rechazoFlu) {
		this.rechazoFlu = rechazoFlu;
	}

	public Integer getParteADen() {
		return parteADen;
	}

	public void setParteADen(Integer parteADen) {
		this.parteADen = parteADen;
	}

	public String getRechazoDen() {
		return rechazoDen;
	}

	public void setRechazoDen(String rechazoDen) {
		this.rechazoDen = rechazoDen;
	}
	
	public String getIncFlu() {
		return incFlu;
	}

	public void setIncFlu(String incFlu) {
		this.incFlu = incFlu;
	}
	
	public String getExcFlu() {
		return excFlu;
	}

	public void setExcFlu(String excFlu) {
		this.excFlu = excFlu;
	}

	public String getIncDen() {
		return incDen;
	}

	public void setIncDen(String incDen) {
		this.incDen = incDen;
	}
	
	public String getExcDen() {
		return excDen;
	}

	public void setExcDen(String excDen) {
		this.excDen = excDen;
	}

	public Integer getEnfCronSN() {
		return enfCronSN;
	}

	public void setEnfCronSN(Integer enfCronSN) {
		this.enfCronSN = enfCronSN;
	}
	
	public Integer getEnfCronica() {
		return enfCronica;
	}
	
	public void setEnfCronica(Integer enfCronica) {
		this.enfCronica = enfCronica;
	}
	
	public String getoEnfCronica() {
		return oEnfCronica;
	}

	public void setoEnfCronica(String oEnfCronica) {
		this.oEnfCronica = oEnfCronica;
	}
	
	public Integer getTomaTx() {
		return tomaTx;
	}

	public void setTomaTx(Integer tomaTx) {
		this.tomaTx = tomaTx;
	}

	public String getCualesTx() {
		return cualesTx;
	}

	public void setCualesTx(String cualesTx) {
		this.cualesTx = cualesTx;
	}

	
	public Integer getAsentimiento() {
		return asentimiento;
	}

	public void setAsentimiento(Integer asentimiento) {
		this.asentimiento = asentimiento;
	}
	
	public Integer getParteBFlu() {
		return parteBFlu;
	}

	public void setParteBFlu(Integer parteBFlu) {
		this.parteBFlu = parteBFlu;
	}

	public Integer getParteCFlu() {
		return parteCFlu;
	}

	public void setParteCFlu(Integer parteCFlu) {
		this.parteCFlu = parteCFlu;
	}
	
	public Integer getParteBDen() {
		return parteBDen;
	}

	public void setParteBDen(Integer parteBDen) {
		this.parteBDen = parteBDen;
	}

	public Integer getParteCDen() {
		return parteCDen;
	}

	public void setParteCDen(Integer parteCDen) {
		this.parteCDen = parteCDen;
	}

	public Integer getParteDDen() {
		return parteDDen;
	}

	public void setParteDDen(Integer parteDDen) {
		this.parteDDen = parteDDen;
	}

	public Integer getParteEDen() {
		return parteEDen;
	}

	public void setParteEDen(Integer parteEDen) {
		this.parteEDen = parteEDen;
	}
	
	public Integer getParteFDen() {
		return parteFDen;
	}

	public void setParteFDen(Integer parteFDen) {
		this.parteFDen = parteFDen;
	}
	
	public Integer getAsentimientoesc() {
		return asentimientoesc;
	}

	public void setAsentimientoesc(Integer asentimientoesc) {
		this.asentimientoesc = asentimientoesc;
	}

	public String getFirmcarta() {
		return firmcarta;
	}

	public void setFirmcarta(String firmcarta) {
		this.firmcarta = firmcarta;
	}

	public Integer getRelacionFam() {
		return relacionFam;
	}

	public void setRelacionFam(Integer relacionFam) {
		this.relacionFam = relacionFam;
	}

	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
	}

}
