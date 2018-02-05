package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;

/**
 * Simple objeto de dominio que representa los datos de consentimiento de Zika
 * 
 * 
 * @author Brenda Lopez
 **/

public class ConsentimientoZikaXml {

	/**
	 * 
	 */
	@Element(required=false)
	private Integer parteDDen;
	@Element(required=false)
	private String rechDDen;
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
	private Integer mismoTutorSN;
	@Element(required=false)
	private Integer motivoDifTutor;
	@Element(required=false)
	private String otroMotivoDifTutor;
	@Element(required=false)
	private String quePaisTutor;
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
	private Integer otrorecurso1;
	@Element(required=false)
	private Integer otrorecurso2;
	
	@Element(required=false)
	private String titulo1;
	@Element(required=false)
	private String tutor;
	@Element(required=false)
	private String titulo7;
	@Element(required=false)
	private String testigo;
	
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
	@Element(required=false)
	private String imei;


	public Integer getParteDDen() {
		return parteDDen;
	}

	public void setParteDDen(Integer parteDDen) {
		this.parteDDen = parteDDen;
	}

	public Integer getRelacionFam() {
		return relacionFam;
	}

	public void setRelacionFam(Integer relacionFam) {
		this.relacionFam = relacionFam;
	}

	public String getRechDDen() {
		return rechDDen;
	}

	public void setRechDDen(String rechDDen) {
		this.rechDDen = rechDDen;
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

	public String getOtraRelacionFam() {
		return otraRelacionFam;
	}

	public void setOtraRelacionFam(String otraRelacionFam) {
		this.otraRelacionFam = otraRelacionFam;
	}

	public Integer getMismoTutorSN() {
		return mismoTutorSN;
	}

	public void setMismoTutorSN(Integer mismoTutorSN) {
		this.mismoTutorSN = mismoTutorSN;
	}

	public Integer getMotivoDifTutor() {
		return motivoDifTutor;
	}

	public void setMotivoDifTutor(Integer motivoDifTutor) {
		this.motivoDifTutor = motivoDifTutor;
	}

	public String getOtroMotivoDifTutor() {
		return otroMotivoDifTutor;
	}

	public void setOtroMotivoDifTutor(String otroMotivoDifTutor) {
		this.otroMotivoDifTutor = otroMotivoDifTutor;
	}

	public String getQuePaisTutor() {
		return quePaisTutor;
	}

	public void setQuePaisTutor(String quePaisTutor) {
		this.quePaisTutor = quePaisTutor;
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

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTutor() {
		return tutor;
	}

	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	public String getTitulo7() {
		return titulo7;
	}

	public void setTitulo7(String titulo7) {
		this.titulo7 = titulo7;
	}

	public String getTestigo() {
		return testigo;
	}

	public void setTestigo(String testigo) {
		this.testigo = testigo;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	
	

}
