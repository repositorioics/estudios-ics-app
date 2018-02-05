package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;

/**
 * Simple objeto de dominio que representa los datos de reconsentimiento de Dengue
 * 
 * @author Brenda Lopez
 **/

public class ReConsentimientoFlu2015 implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private ReConsentimientoFlu2015Id reconsfluId;
	private String visExit;
	private String noexitosa;
	private String nombrept;
	private String nombrept2;
	private String apellidopt;
	private String apellidopt2;
	private Integer relacionFam;
	private String otraRelacionFam;
	private Integer alfabetoTutor;
	private Integer testigoSN;
	private String nombretest1;
	private String nombretest2;
	private String apellidotest1;
	private String apellidotest2;
	private Integer cmDomicilio;
	private Integer barrio;
	private String otrobarrio;
	private String dire;
	private Integer telefContact;
	private Integer	telefonoConv1;
	private Integer	telefonoCel1;
	private Integer	telefonoCel2;
	private Integer asentimiento;
	private Integer parteAFlu;
	private Integer contacto_futuro;
	private Integer porqueno;
	private Integer parteBFlu;
	private Integer parteCFlu;
	private Integer local;
	
	private MovilInfo movilInfo;	
	private Integer otrorecurso1;
	private Integer otrorecurso2;
	
	
	

	public ReConsentimientoFlu2015Id getReconsfluId() {
		return reconsfluId;
	}

	public void setReconsfluId(ReConsentimientoFlu2015Id reconsfluId) {
		this.reconsfluId = reconsfluId;
	}
	
	public String getNoexitosa() {
		return noexitosa;
	}

	public void setNoexitosa(String noexitosa) {
		this.noexitosa = noexitosa;
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

	public Integer getPorqueno() {
		return porqueno;
	}

	public void setPorqueno(Integer porqueno) {
		this.porqueno = porqueno;
	}
	

	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
	}

	public Integer getOtrorecurso1() {
		return otrorecurso1;
	}

	public void setOtrorecurso1(Integer otrorecurso1) {
		this.otrorecurso1 = otrorecurso1;
	}
	
	public String getVisExit() {
		return visExit;
	}

	public void setVisExit(String visExit) {
		this.visExit = visExit;
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

	public Integer getLocal() {
		return local;
	}

	public void setLocal(Integer local) {
		this.local = local;
	}
	
}
