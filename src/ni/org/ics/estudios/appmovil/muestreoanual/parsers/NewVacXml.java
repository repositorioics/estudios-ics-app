package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 * 
 * @author William Aviles
 **/

public class NewVacXml {
	
	@Element(required=false)
	private String titulo_vacuna;
	@Element(required=false)
	private String vacuna_sn;
	@Element(required=false)
	private String tvacunano;
	@Element(required=false)
	private String otroMotivoTvacunano;
	
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
	
	public NewVacXml(){
		
	}

	public String getTitulo_vacuna() {
		return titulo_vacuna;
	}

	public void setTitulo_vacuna(String titulo_vacuna) {
		this.titulo_vacuna = titulo_vacuna;
	}
	
	public String getVacuna_sn() {
		return vacuna_sn;
	}

	public void setVacuna_sn(String vacuna_sn) {
		this.vacuna_sn = vacuna_sn;
	}

	public String getTvacunano() {
		return tvacunano;
	}

	public void setTvacunano(String tvacunano) {
		this.tvacunano = tvacunano;
	}

	public String getOtroMotivoTvacunano() {
		return otroMotivoTvacunano;
	}

	public void setOtroMotivoTvacunano(String otroMotivoTvacunano) {
		this.otroMotivoTvacunano = otroMotivoTvacunano;
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
