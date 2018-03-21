package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;

public class ReConsentimientoFlu2015Xml {
	
	@Element(required=false)
	private String nombrept;
	@Element(required=false)
	private String nombrept2;
	@Element(required=false)
	private String apellidopt;
	@Element(required=false)
	private String apellidopt2;
	@Element(required=false)
	private Integer relacionFam;
	@Element(required=false)
	private String otraRelacionFam;
	@Element(required=false)
	private Integer alfabetoTutor;
	@Element(required=false)
	private Integer testigoSN;
	@Element(required=false)
	private String nombretest1;
	@Element(required=false)
	private String nombretest2;
	@Element(required=false)
	private String apellidotest1;
	@Element(required=false)
	private String apellidotest2;
	@Element(required=false)
	private Integer cmDomicilio;
	@Element(required=false)
	private Integer barrio;
	@Element(required=false)
	private String otrobarrio;
	@Element(required=false)
	private String dire;
	@Element(required=false)
	private Integer telefContact;
	@Element(required=false)
	private Integer	telefonoConv1;
	@Element(required=false)
	private Integer	telefonoCel1;
	@Element(required=false)
	private Integer	telefonoCel2;
    @Element(required=false)
    private Integer	telefonoCel3;
    @Element(required=false)
	private Integer asentimiento;
	@Element(required=false)
	private Integer parteAFlu;
	@Element(required=false)
	private Integer contacto_futuro;
	@Element(required=false)
	private Integer porqueno;
	@Element(required=false)
	private Integer parteBFlu;
	@Element(required=false)
	private Integer parteCFlu;

	@Element(required=false)
	private Integer otrorecurso1;
	@Element(required=false)
	private Integer otrorecurso2;

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
	private String edad;
	@Element(required=false)
	private String tutor;
	@Element(required=false)
	private String testigo;
	@Element(required=false)
	private String telefonos;
		
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
	@Element(required=false)
	private String imei;
	@Attribute
	private String id;
	
	
	@Element(required=false)
	private Meta meta;
	
	public ReConsentimientoFlu2015Xml(){
		
	}

	public String getNombrept() {
		return nombrept;
	}

	public void setNombrept(String nombrept) {
		this.nombrept = nombrept;
	}

	public String getNombrept2() {
		return nombrept2;
	}

	public void setNombrept2(String nombrept2) {
		this.nombrept2 = nombrept2;
	}

	public String getApellidopt() {
		return apellidopt;
	}

	public void setApellidopt(String apellidopt) {
		this.apellidopt = apellidopt;
	}

	public String getApellidopt2() {
		return apellidopt2;
	}

	public void setApellidopt2(String apellidopt2) {
		this.apellidopt2 = apellidopt2;
	}

	public Integer getAlfabetoTutor() {
		return alfabetoTutor;
	}

	public void setAlfabetoTutor(Integer alfabetoTutor) {
		this.alfabetoTutor = alfabetoTutor;
	}

	public Integer getTestigoSN() {
		return testigoSN;
	}

	public void setTestigoSN(Integer testigoSN) {
		this.testigoSN = testigoSN;
	}

	public String getNombretest1() {
		return nombretest1;
	}

	public void setNombretest1(String nombretest1) {
		this.nombretest1 = nombretest1;
	}

	public String getNombretest2() {
		return nombretest2;
	}

	public void setNombretest2(String nombretest2) {
		this.nombretest2 = nombretest2;
	}

	public String getApellidotest1() {
		return apellidotest1;
	}

	public void setApellidotest1(String apellidotest1) {
		this.apellidotest1 = apellidotest1;
	}

	public String getApellidotest2() {
		return apellidotest2;
	}

	public void setApellidotest2(String apellidotest2) {
		this.apellidotest2 = apellidotest2;
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

	public String getOtrobarrio() {
		return otrobarrio;
	}

	public void setOtrobarrio(String otrobarrio) {
		this.otrobarrio = otrobarrio;
	}

	public String getDire() {
		return dire;
	}

	public void setDire(String dire) {
		this.dire = dire;
	}

	public Integer getTelefContact() {
		return telefContact;
	}

	public void setTelefContact(Integer telefContact) {
		this.telefContact = telefContact;
	}

	public Integer getTelefonoConv1() {
		return telefonoConv1;
	}

	public void setTelefonoConv1(Integer telefonoConv1) {
		this.telefonoConv1 = telefonoConv1;
	}

	public Integer getTelefonoCel1() {
		return telefonoCel1;
	}

	public void setTelefonoCel1(Integer telefonoCel1) {
		this.telefonoCel1 = telefonoCel1;
	}

	public Integer getTelefonoCel2() {
		return telefonoCel2;
	}

	public void setTelefonoCel2(Integer telefonoCel2) {
		this.telefonoCel2 = telefonoCel2;
	}

	public Integer getParteAFlu() {
		return parteAFlu;
	}

	public void setParteAFlu(Integer parteAFlu) {
		this.parteAFlu = parteAFlu;
	}

	public Integer getContacto_futuro() {
		return contacto_futuro;
	}

	public void setContacto_futuro(Integer contacto_futuro) {
		this.contacto_futuro = contacto_futuro;
	}

	public Integer getPorqueno() {
		return porqueno;
	}

	public void setPorqueno(Integer porqueno) {
		this.porqueno = porqueno;
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

	public Integer getOtrorecurso1() {
		return otrorecurso1;
	}

	public void setOtrorecurso1(Integer otrorecurso1) {
		this.otrorecurso1 = otrorecurso1;
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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
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

	public String getTutor() {
		return tutor;
	}

	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	public String getTestigo() {
		return testigo;
	}

	public void setTestigo(String testigo) {
		this.testigo = testigo;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public Integer getRelacionFam() {
		return relacionFam;
	}

	public void setRelacionFam(Integer relacionFam) {
		this.relacionFam = relacionFam;
	}

	public String getOtraRelacionFam() {
		return otraRelacionFam;
	}

	public void setOtraRelacionFam(String otraRelacionFam) {
		this.otraRelacionFam = otraRelacionFam;
	}

	public Integer getAsentimiento() {
		return asentimiento;
	}

	public void setAsentimiento(Integer asentimiento) {
		this.asentimiento = asentimiento;
	}

	public Integer getOtrorecurso2() {
		return otrorecurso2;
	}

	public void setOtrorecurso2(Integer otrorecurso2) {
		this.otrorecurso2 = otrorecurso2;
	}

	public String getTitulo5() {
		return titulo5;
	}

	public void setTitulo5(String titulo5) {
		this.titulo5 = titulo5;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

    public Integer getTelefonoCel3() {
        return telefonoCel3;
    }

    public void setTelefonoCel3(Integer telefonoCel3) {
        this.telefonoCel3 = telefonoCel3;
    }
}
