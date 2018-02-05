package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;

/**
 * Simple objeto de dominio que representa los datos de reconsentimiento de Dengue
 * 
 * @author Brenda Lopez
 **/

public class CambioEstudioXml {

	/**
	 * 
	 */
	
	@Element(required=false)
	private String cons_flu;
	@Element(required=false)
	private String cons_deng;
	@Element(required=false)
	private Integer edad;
	
	
	
	@Element(required=false)
	private Integer parteAFlu;
	@Element(required=false)
	private String rechazoFlu;
	@Element(required=false)
	private Integer parteADen;
	@Element(required=false)
	private String rechazoDen;
	@Element(required=false)
	private String incFlu;
	@Element(required=false)
	private String excFlu;
	@Element(required=false)
	private String incDen;
	@Element(required=false)
	private String excDen;
	@Element(required=false)
	private Integer enfCronSN;
	@Element(required=false)
	private Integer enfCronica;
	@Element(required=false)
	private String oEnfCronica;
	@Element(required=false)
	private Integer tomaTx;
	@Element(required=false)
	private String cualesTx;
	@Element(required=false)
	private Integer asentimiento;
	@Element(required=false)
	private Integer parteBFlu;
	@Element(required=false)
	private Integer parteCFlu;
	@Element(required=false)
	private Integer parteBDen;
	@Element(required=false)
	private Integer parteCDen;
	@Element(required=false)
	private Integer parteDDen;
	@Element(required=false)
	private Integer parteEDen;
	@Element(required=false)
	private Integer parteFDen;
	@Element(required=false)
	private Integer asentimientoesc;
	@Element(required=false)
	private String firmcarta;
	@Element(required=false)
	private Integer relacionFam;
	
	@Element(required=false)
	private String titulo1;
	@Element(required=false)
	private String titulo2;
	@Element(required=false)
	private String titulo3;
	@Element(required=false)
	private String titulo4;
	@Element(required=false)
	private String titulo5;
	@Element(required=false)
	private String titulo6;
	@Element(required=false)
	private String titulo7;
	@Element(required=false)
	private String titulo8;
	@Element(required=false)
	private String titulo9;
	@Element(required=false)
	private String titulo10;
	@Element(required=false)
	private String titulo11;
	@Element(required=false)
	private String titulo12;
	
	@Element(required=false)
	private String start;
	@Element(required=false)
	private String end;
	@Element(required=false)
	private String deviceid;
	@Element(required=false)
	private String simserial;
	@Element(required=false)
	private String phonenumber;
	@Element(required=false)
	private Date today;
	@Element(required=false)
	private Integer recurso1;
	@Element(required=false)
	private Integer recurso2;
	@Attribute
	private String id;
	
	
	@Element(required=false)
	private Meta meta;
	
	
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

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public String getTitulo3() {
		return titulo3;
	}

	public void setTitulo3(String titulo3) {
		this.titulo3 = titulo3;
	}

	public String getTitulo4() {
		return titulo4;
	}

	public void setTitulo4(String titulo4) {
		this.titulo4 = titulo4;
	}

	public String getTitulo5() {
		return titulo5;
	}

	public void setTitulo5(String titulo5) {
		this.titulo5 = titulo5;
	}

	public String getTitulo6() {
		return titulo6;
	}

	public void setTitulo6(String titulo6) {
		this.titulo6 = titulo6;
	}

	public String getTitulo7() {
		return titulo7;
	}

	public void setTitulo7(String titulo7) {
		this.titulo7 = titulo7;
	}

	public String getTitulo8() {
		return titulo8;
	}

	public void setTitulo8(String titulo8) {
		this.titulo8 = titulo8;
	}

	public String getTitulo9() {
		return titulo9;
	}

	public void setTitulo9(String titulo9) {
		this.titulo9 = titulo9;
	}

	public String getTitulo10() {
		return titulo10;
	}

	public void setTitulo10(String titulo10) {
		this.titulo10 = titulo10;
	}

	public String getTitulo11() {
		return titulo11;
	}

	public void setTitulo11(String titulo11) {
		this.titulo11 = titulo11;
	}

	public String getTitulo12() {
		return titulo12;
	}

	public void setTitulo12(String titulo12) {
		this.titulo12 = titulo12;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getSimserial() {
		return simserial;
	}

	public void setSimserial(String simserial) {
		this.simserial = simserial;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public Integer getRecurso1() {
		return recurso1;
	}

	public void setRecurso1(Integer recurso1) {
		this.recurso1 = recurso1;
	}

	public Integer getRecurso2() {
		return recurso2;
	}

	public void setRecurso2(Integer recurso2) {
		this.recurso2 = recurso2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public String getCons_flu() {
		return cons_flu;
	}

	public void setCons_flu(String cons_flu) {
		this.cons_flu = cons_flu;
	}

	public String getCons_deng() {
		return cons_deng;
	}

	public void setCons_deng(String cons_deng) {
		this.cons_deng = cons_deng;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}


	
	

}
