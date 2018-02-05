package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;

public class ReConsentimientoDenXml {
	
	@Element(required=false)
	private Integer autsup;
	@Element(required=false)
	private Integer parteADen;
	@Element(required=false)
	private String rechazoDen;
	@Element(required=false)
	private String otroRechazoDen;
	@Element(required=false)
	private String incDen;
	@Element(required=false)
	private Integer autsup2;
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
	private Integer autsup3;
	@Element(required=false)
	private Integer cmDomicilio;
	@Element(required=false)
	private Integer barrio;
	@Element(required=false)
	private Integer autsup4;
	@Element(required=false)
	private String dire;
	@Element(required=false)
	private Integer manzana;
	@Element(required=false)
	private Integer telefono;
	@Element(required=false)
	private Integer asentimiento;
	@Element(required=false)
	private Integer parteBDen;
	@Element(required=false)
	private Integer parteCDen;
	@Element(required=false)
	private Integer parteDDen;
	@Element(required=false)
	private Integer asentimientoesc;
	@Element(required=false)
	private Integer parteEDen;
	@Element(required=false)
	private String firmcarta;
	@Element(required=false)
	private Integer relacionFam;
	@Element(required=false)
	private String coordenadas;
	@Element(required=false)
	private Date fechaNac;
	@Element(required=false)
	private Integer cal_edad;
	@Element(required=false)
	private Integer edad;
	@Element(required=false)
	private String titulo1;
	@Element(required=false)
	private String titulo2;
	@Element(required=false)
	private Integer cal_edad2;
	@Element(required=false)
	private String titulo3;
	@Element(required=false)
	private String titulo4;
	@Element(required=false)
	private String titulo5;
	@Element(required=false)
	private String titulo6;
	
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
	
	public ReConsentimientoDenXml(){
		
	}

	public Integer getAutsup() {
		return autsup;
	}

	public void setAutsup(Integer autsup) {
		this.autsup = autsup;
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

	public String getOtroRechazoDen() {
		return otroRechazoDen;
	}

	public void setOtroRechazoDen(String otroRechazoDen) {
		this.otroRechazoDen = otroRechazoDen;
	}

	public String getIncDen() {
		return incDen;
	}

	public void setIncDen(String incDen) {
		this.incDen = incDen;
	}

	public Integer getAutsup2() {
		return autsup2;
	}

	public void setAutsup2(Integer autsup2) {
		this.autsup2 = autsup2;
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

	public Integer getAutsup3() {
		return autsup3;
	}

	public void setAutsup3(Integer autsup3) {
		this.autsup3 = autsup3;
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

	public Integer getAutsup4() {
		return autsup4;
	}

	public void setAutsup4(Integer autsup4) {
		this.autsup4 = autsup4;
	}

	public String getDire() {
		return dire;
	}

	public void setDire(String dire) {
		this.dire = dire;
	}

	public Integer getManzana() {
		return manzana;
	}

	public void setManzana(Integer manzana) {
		this.manzana = manzana;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public Integer getAsentimiento() {
		return asentimiento;
	}

	public void setAsentimiento(Integer asentimiento) {
		this.asentimiento = asentimiento;
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

	public Integer getAsentimientoesc() {
		return asentimientoesc;
	}

	public void setAsentimientoesc(Integer asentimientoesc) {
		this.asentimientoesc = asentimientoesc;
	}

	public Integer getParteEDen() {
		return parteEDen;
	}

	public void setParteEDen(Integer parteEDen) {
		this.parteEDen = parteEDen;
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

	public String getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Integer getCal_edad() {
		return cal_edad;
	}

	public void setCal_edad(Integer cal_edad) {
		this.cal_edad = cal_edad;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
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

	public Integer getCal_edad2() {
		return cal_edad2;
	}

	public void setCal_edad2(Integer cal_edad2) {
		this.cal_edad2 = cal_edad2;
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

	public Integer getRecurso2() {
		return recurso2;
	}

	public void setRecurso2(Integer recurso2) {
		this.recurso2 = recurso2;
	}

	public void setRecurso1(Integer recurso1) {
		this.recurso1 = recurso1;
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
	
	

}
