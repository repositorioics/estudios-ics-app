package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 * 
 * @author William Aviles
 **/

public class DatosVisitaXml {
	
	@Element(required=false)
	private Integer cDom;
	@Element(required=false)
	private Integer barrio;
	@Element(required=false)
	private Integer manzana;
	@Element(required=false)
	private String direccion;
	@Element(required=false)
	private String coordenadas;
	@Element(required=false)
	private Integer otrorecurso1;
	@Element(required=false)
	private Integer otrorecurso2;
	
	@Element(required=false)
	private String telefonoClasif1;
	@Element(required=false)
	private String telefonoConv1;
	@Element(required=false)
	private String telefonoCel1;
	@Element(required=false)
	private String telefonoEmpresa1;
	@Element(required=false)
	private String telefono2SN;
	@Element(required=false)
	private String telefonoClasif2;
	@Element(required=false)
	private String telefonoConv2;
	@Element(required=false)
	private String telefonoCel2;
	@Element(required=false)
	private String telefonoEmpresa2;
	@Element(required=false)
	private String telefono3SN;
	@Element(required=false)
	private String telefonoClasif3;
	@Element(required=false)
	private String telefonoConv3;
	@Element(required=false)
	private String telefonoCel3;
	@Element(required=false)
	private String telefonoEmpresa3;
	@Element(required=false)
	private String telefono4SN;
	@Element(required=false)
	private String telefonoClasif4;
	@Element(required=false)
	private String telefonoConv4;
	@Element(required=false)
	private String telefonoCel4;
	@Element(required=false)
	private String telefonoEmpresa4;
	@Element(required=false)
	private String candidatoNI;
	@Element(required=false)
	private String nombreCandNI1;
	@Element(required=false)
	private String nombreCandNI2;
	@Element(required=false)
	private String apellidoCandNI1;
	@Element(required=false)
	private String apellidoCandNI2;
	@Element(required=false)
	private String nombreptTutorCandNI;
	@Element(required=false)
	private String nombreptTutorCandNI2;
	@Element(required=false)
	private String apellidoptTutorCandNI;
	@Element(required=false)
	private String apellidoptTutorCandNI2;
	@Element(required=false)
	private String relacionFamCandNI;
	@Element(required=false)
	private String otraRelacionFamCandNI;
	
	
	
	@Element(required=false)
	private String punto;
	
	@Element(required=false)
	private String informacion;
	@Element(required=false)
	private String confirmar;
	
	@Element(required=false)
	private String titulo1;
	
	@Element(required=false)
	private String nombreCandidatoNI;
	@Element(required=false)
	private String tutorCandidatoNI;
	
	
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
	
	public DatosVisitaXml(){
		
	}

	public Integer getcDom() {
		return cDom;
	}

	public void setcDom(Integer cDom) {
		this.cDom = cDom;
	}

	public Integer getBarrio() {
		return barrio;
	}

	public void setBarrio(Integer barrio) {
		this.barrio = barrio;
	}

	public Integer getManzana() {
		return manzana;
	}

	public void setManzana(Integer manzana) {
		this.manzana = manzana;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	public String getPunto() {
		return punto;
	}

	public void setPunto(String punto) {
		this.punto = punto;
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
	public String getTelefonoClasif1() {
		return telefonoClasif1;
	}

	public void setTelefonoClasif1(String telefonoClasif1) {
		this.telefonoClasif1 = telefonoClasif1;
	}

	public String getTelefonoConv1() {
		return telefonoConv1;
	}

	public void setTelefonoConv1(String telefonoConv1) {
		this.telefonoConv1 = telefonoConv1;
	}

	public String getTelefonoCel1() {
		return telefonoCel1;
	}

	public void setTelefonoCel1(String telefonoCel1) {
		this.telefonoCel1 = telefonoCel1;
	}

	public String getTelefonoEmpresa1() {
		return telefonoEmpresa1;
	}

	public void setTelefonoEmpresa1(String telefonoEmpresa1) {
		this.telefonoEmpresa1 = telefonoEmpresa1;
	}

	public String getTelefono2SN() {
		return telefono2SN;
	}

	public void setTelefono2SN(String telefono2sn) {
		telefono2SN = telefono2sn;
	}

	public String getTelefonoClasif2() {
		return telefonoClasif2;
	}

	public void setTelefonoClasif2(String telefonoClasif2) {
		this.telefonoClasif2 = telefonoClasif2;
	}

	public String getTelefonoConv2() {
		return telefonoConv2;
	}

	public void setTelefonoConv2(String telefonoConv2) {
		this.telefonoConv2 = telefonoConv2;
	}

	public String getTelefonoCel2() {
		return telefonoCel2;
	}

	public void setTelefonoCel2(String telefonoCel2) {
		this.telefonoCel2 = telefonoCel2;
	}

	public String getTelefonoEmpresa2() {
		return telefonoEmpresa2;
	}

	public void setTelefonoEmpresa2(String telefonoEmpresa2) {
		this.telefonoEmpresa2 = telefonoEmpresa2;
	}

	public String getTelefono3SN() {
		return telefono3SN;
	}

	public void setTelefono3SN(String telefono3sn) {
		telefono3SN = telefono3sn;
	}

	public String getTelefonoClasif3() {
		return telefonoClasif3;
	}

	public void setTelefonoClasif3(String telefonoClasif3) {
		this.telefonoClasif3 = telefonoClasif3;
	}

	public String getTelefonoConv3() {
		return telefonoConv3;
	}

	public void setTelefonoConv3(String telefonoConv3) {
		this.telefonoConv3 = telefonoConv3;
	}

	public String getTelefonoCel3() {
		return telefonoCel3;
	}

	public void setTelefonoCel3(String telefonoCel3) {
		this.telefonoCel3 = telefonoCel3;
	}

	public String getTelefonoEmpresa3() {
		return telefonoEmpresa3;
	}

	public void setTelefonoEmpresa3(String telefonoEmpresa3) {
		this.telefonoEmpresa3 = telefonoEmpresa3;
	}

	public String getTelefono4SN() {
		return telefono4SN;
	}

	public void setTelefono4SN(String telefono4sn) {
		telefono4SN = telefono4sn;
	}

	public String getTelefonoClasif4() {
		return telefonoClasif4;
	}

	public void setTelefonoClasif4(String telefonoClasif4) {
		this.telefonoClasif4 = telefonoClasif4;
	}

	public String getTelefonoConv4() {
		return telefonoConv4;
	}

	public void setTelefonoConv4(String telefonoConv4) {
		this.telefonoConv4 = telefonoConv4;
	}

	public String getTelefonoCel4() {
		return telefonoCel4;
	}

	public void setTelefonoCel4(String telefonoCel4) {
		this.telefonoCel4 = telefonoCel4;
	}

	public String getTelefonoEmpresa4() {
		return telefonoEmpresa4;
	}

	public void setTelefonoEmpresa4(String telefonoEmpresa4) {
		this.telefonoEmpresa4 = telefonoEmpresa4;
	}

	public String getCandidatoNI() {
		return candidatoNI;
	}

	public void setCandidatoNI(String candidatoNI) {
		this.candidatoNI = candidatoNI;
	}

	public String getNombreCandNI1() {
		return nombreCandNI1;
	}

	public void setNombreCandNI1(String nombreCandNI1) {
		this.nombreCandNI1 = nombreCandNI1;
	}

	public String getNombreCandNI2() {
		return nombreCandNI2;
	}

	public void setNombreCandNI2(String nombreCandNI2) {
		this.nombreCandNI2 = nombreCandNI2;
	}

	public String getApellidoCandNI1() {
		return apellidoCandNI1;
	}

	public void setApellidoCandNI1(String apellidoCandNI1) {
		this.apellidoCandNI1 = apellidoCandNI1;
	}

	public String getApellidoCandNI2() {
		return apellidoCandNI2;
	}

	public void setApellidoCandNI2(String apellidoCandNI2) {
		this.apellidoCandNI2 = apellidoCandNI2;
	}

	public String getNombreptTutorCandNI() {
		return nombreptTutorCandNI;
	}

	public void setNombreptTutorCandNI(String nombreptTutorCandNI) {
		this.nombreptTutorCandNI = nombreptTutorCandNI;
	}

	public String getNombreptTutorCandNI2() {
		return nombreptTutorCandNI2;
	}

	public void setNombreptTutorCandNI2(String nombreptTutorCandNI2) {
		this.nombreptTutorCandNI2 = nombreptTutorCandNI2;
	}

	public String getApellidoptTutorCandNI() {
		return apellidoptTutorCandNI;
	}

	public void setApellidoptTutorCandNI(String apellidoptTutorCandNI) {
		this.apellidoptTutorCandNI = apellidoptTutorCandNI;
	}

	public String getApellidoptTutorCandNI2() {
		return apellidoptTutorCandNI2;
	}

	public void setApellidoptTutorCandNI2(String apellidoptTutorCandNI2) {
		this.apellidoptTutorCandNI2 = apellidoptTutorCandNI2;
	}

	public String getRelacionFamCandNI() {
		return relacionFamCandNI;
	}

	public void setRelacionFamCandNI(String relacionFamCandNI) {
		this.relacionFamCandNI = relacionFamCandNI;
	}

	public String getOtraRelacionFamCandNI() {
		return otraRelacionFamCandNI;
	}

	public void setOtraRelacionFamCandNI(String otraRelacionFamCandNI) {
		this.otraRelacionFamCandNI = otraRelacionFamCandNI;
	}
	
	

	
}
