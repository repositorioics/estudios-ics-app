package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 * 
 * @author William Aviles
 **/

public class EstacionMuestraXml {
	
	@Element(required=false)
	private String titulo_vacuna;
	@Element(required=false)
	private String titulo_finalVac;
	
	@Element(required=false)
	private Integer vacuna;
	@Element(required=false)
	private Date fechaVac;
	@Element(required=false)
	private String tipovacuna;
	@Element(required=false)
	private Integer tarjetaSN;
	@Element(required=false)
	private Integer ndosis;
	@Element(required=false)
	private Date fechaInf1;
	@Element(required=false)
	private Date fechaInf2;
	@Element(required=false)
	private Date fechaInf3;
	@Element(required=false)
	private Date fechaInf4;
	@Element(required=false)
	private Date fechaInf5;
	@Element(required=false)
	private Date fechaInf6;
	@Element(required=false)
	private Date fechaInf7;
	@Element(required=false)
	private Date fechaInf8;
	@Element(required=false)
	private Date fechaInf9;
	@Element(required=false)
	private Date fechaInf10;
	
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
	@Element
	private Integer recurso2;
	@Attribute
	private String id;
	
	
	@Element(required=false)
	private Meta meta;
	
	public EstacionMuestraXml(){
		
	}

	public String getTitulo_vacuna() {
		return titulo_vacuna;
	}

	public void setTitulo_vacuna(String titulo_vacuna) {
		this.titulo_vacuna = titulo_vacuna;
	}

	public String getTitulo_finalVac() {
		return titulo_finalVac;
	}

	public void setTitulo_finalVac(String titulo_finalVac) {
		this.titulo_finalVac = titulo_finalVac;
	}

	public Integer getVacuna() {
		return vacuna;
	}

	public void setVacuna(Integer vacuna) {
		this.vacuna = vacuna;
	}

	public Date getFechaVac() {
		return fechaVac;
	}

	public void setFechaVac(Date fechaVac) {
		this.fechaVac = fechaVac;
	}

	public String getTipovacuna() {
		return tipovacuna;
	}

	public void setTipovacuna(String tipovacuna) {
		this.tipovacuna = tipovacuna;
	}

	public Integer getTarjetaSN() {
		return tarjetaSN;
	}

	public void setTarjetaSN(Integer tarjetaSN) {
		this.tarjetaSN = tarjetaSN;
	}

	public Integer getNdosis() {
		return ndosis;
	}

	public void setNdosis(Integer ndosis) {
		this.ndosis = ndosis;
	}

	public Date getFechaInf1() {
		return fechaInf1;
	}

	public void setFechaInf1(Date fechaInf1) {
		this.fechaInf1 = fechaInf1;
	}

	public Date getFechaInf2() {
		return fechaInf2;
	}

	public void setFechaInf2(Date fechaInf2) {
		this.fechaInf2 = fechaInf2;
	}

	public Date getFechaInf3() {
		return fechaInf3;
	}

	public void setFechaInf3(Date fechaInf3) {
		this.fechaInf3 = fechaInf3;
	}

	public Date getFechaInf4() {
		return fechaInf4;
	}

	public void setFechaInf4(Date fechaInf4) {
		this.fechaInf4 = fechaInf4;
	}

	public Date getFechaInf5() {
		return fechaInf5;
	}

	public void setFechaInf5(Date fechaInf5) {
		this.fechaInf5 = fechaInf5;
	}

	public Date getFechaInf6() {
		return fechaInf6;
	}

	public void setFechaInf6(Date fechaInf6) {
		this.fechaInf6 = fechaInf6;
	}

	public Date getFechaInf7() {
		return fechaInf7;
	}

	public void setFechaInf7(Date fechaInf7) {
		this.fechaInf7 = fechaInf7;
	}

	public Date getFechaInf8() {
		return fechaInf8;
	}

	public void setFechaInf8(Date fechaInf8) {
		this.fechaInf8 = fechaInf8;
	}

	public Date getFechaInf9() {
		return fechaInf9;
	}

	public void setFechaInf9(Date fechaInf9) {
		this.fechaInf9 = fechaInf9;
	}

	public Date getFechaInf10() {
		return fechaInf10;
	}

	public void setFechaInf10(Date fechaInf10) {
		this.fechaInf10 = fechaInf10;
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

	public Integer getOtrorecurso1() {
		return otrorecurso1;
	}

	public void setOtrorecurso1(Integer otrorecurso1) {
		this.otrorecurso1 = otrorecurso1;
	}

	
}
