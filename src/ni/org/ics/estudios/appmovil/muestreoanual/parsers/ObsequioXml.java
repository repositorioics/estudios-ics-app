package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 * 
 * @author William Aviles
 **/

public class ObsequioXml {
	
	
	
	
	@Element(required=false)
	private String informacion;
	@Element(required=false)
	private String confirmar;
	@Element(required=false)
	private String conmx;
	@Element(required=false)
	private String conmxbhc;
	@Element(required=false)
	private String titulo1;
	@Element(required=false)
	private String titulo2;
	@Element(required=false)
	private String titulo3;
	
	@Element(required=false)
	private Integer obseqSN;
	@Element(required=false)
	private Integer carnetSN;
	@Element(required=false)
	private String persRecCarnet;
	@Element(required=false)
	private Integer relacionFam;
	@Element(required=false)
	private String otroRelacionFam;
	@Element(required=false)
	private Integer telefono;
	@Element(required=false)
	private Integer cmDomicilio;
	@Element(required=false)
	private Integer barrio;
	@Element(required=false)
	private String dire;
	@Element(required=false)
	private String observaciones;
	@Element(required=false)
	private Integer otrorecurso1;
	
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
	@Element
	private Integer recurso1;
	@Attribute
	private String id;
	
	
	@Element(required=false)
	private Meta meta;
	
	public ObsequioXml(){
		
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	public String getConfirmar() {
		return confirmar;
	}

	public void setConfirmar(String confirmar) {
		this.confirmar = confirmar;
	}

	public String getConmx() {
		return conmx;
	}

	public void setConmx(String conmx) {
		this.conmx = conmx;
	}

	public String getConmxbhc() {
		return conmxbhc;
	}

	public void setConmxbhc(String conmxbhc) {
		this.conmxbhc = conmxbhc;
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

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

	public Integer getOtrorecurso1() {
		return otrorecurso1;
	}

	public void setOtrorecurso1(Integer otrorecurso1) {
		this.otrorecurso1 = otrorecurso1;
	}

	

}
