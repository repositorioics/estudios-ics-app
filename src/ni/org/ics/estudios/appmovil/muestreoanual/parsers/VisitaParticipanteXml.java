package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 * 
 * @author William Aviles
 **/

public class VisitaParticipanteXml {
	
	@Element(required=false)
	private Integer edad;
	@Element(required=false)
	private Integer visitaSN;
	@Element(required=false)
	private Integer motNoVisita;
	@Element(required=false)
	private String acomp;
	@Element(required=false)
	private Integer relacionFam;
	@Element(required=false)
	private Integer asentimiento;
	
	@Element(required=false)
	private Integer otrorecurso1;
	@Element(required=false)
	private Integer otrorecurso2;
	
	@Element(required=false)
	private String otraRelacionFam;
	@Element(required=false)
	private String carnetSN;
	
	
	@Element(required=false)
	private String informacion;
	@Element(required=false)
	private String confirmar;
	
	@Element(required=false)
	private String titulo1;
	

	
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
	@Element
	private Integer recurso2;
	@Attribute
	private String id;
	
	
	@Element(required=false)
	private Meta meta;
	
	public VisitaParticipanteXml(){
		
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

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
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

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
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

	public String getOtraRelacionFam() {
		return otraRelacionFam;
	}

	public String getCarnetSN() {
		return carnetSN;
	}

	public void setCarnetSN(String carnetSN) {
		this.carnetSN = carnetSN;
	}

	
}
