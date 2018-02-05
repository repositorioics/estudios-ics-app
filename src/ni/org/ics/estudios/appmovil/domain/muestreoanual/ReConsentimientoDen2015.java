package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;

/**
 * Simple objeto de dominio que representa los datos de reconsentimiento de Dengue
 * 
 * @author Brenda Lopez
 **/

public class ReConsentimientoDen2015 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private ReConsentimientoDen2015Id reconsdenId;
	
	private String visExit;
	private String razonVisNoExit;
	private String personaDejoCarta;
	private String personaCasa;
	private String relacionFamPersonaCasa;
	private String otraRelacionPersonaCasa;
	private Integer telefonoPersonaCasa;
	private String emancipado;
	private String descEmancipado;
	private String incDen;
	private String excDen;
	private Integer enfCronSN;
	private Integer enfCronica;
	private String oEnfCronica;
	private Integer tomaTx;
	private String cualesTx;
	private Integer assite;
	private Integer centrosalud;
	private String ocentrosalud;
	private Integer puestosalud;
	private Integer asentimiento;
	private Integer parteADen;
	private Integer parteBDen;
	private Integer parteCDen;
	private Integer parteDDen;
	private String rechDen;
	private String nombrept;
	private String nombrept2;
	private String apellidopt;
	private String apellidopt2;
	private Integer relacionFam;
	private String otraRelacionFam;
	private Integer mismoTutorSN;
	private Integer motivoDifTutor;
	private String otroMotivoDifTutor;
	private String quePaisTutor;
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
	private Integer autsup;
	private Integer telefonoClasif1;
	private Integer	telefonoConv1;
	private Integer	telefonoCel1;
	private Integer telefono2SN;
	private Integer telefonoClasif2;
	private Integer	telefonoConv2;
	private Integer	telefonoCel2;
	private Integer telefono3SN;
	private Integer telefonoClasif3;
	private Integer	telefonoConv3;
	private Integer	telefonoCel3;
	private String jefenom;
	private String jefenom2;
	private String jefeap;
	private String jefeap2;
	private String nomContacto;
	private Integer	barrioContacto;
	private String otrobarrioContacto;
	private String direContacto;
	private Integer telContacto1;
	private Integer	telContactoConv1;
	private Integer	telContactoCel1;
	private Integer telContacto2SN;
	private Integer telContactoClasif2;
	private Integer	telContactoConv2;
	private Integer	telContactoCel2;
	private String nombrepadre;
	private String nombrepadre2;
	private String apellidopadre;
	private String apellidopadre2;
	private String nombremadre;
	private String nombremadre2;
	private String apellidomadre;
	private String apellidomadre2;
	private Integer copiaFormato;
	private Integer firmo_cartcons;
	private Integer fecho_cartcons;
	private Integer huella_dig;
	private Integer fech_firm_testigo;
	private Integer entiende;
	private String georef;
	private Integer Manzana;
	private String georef_razon;
	private String georef_otraRazon;
	private Integer local;
	private MovilInfo movilInfo;	
	private Integer otrorecurso1;
	private Integer otrorecurso2;

	

	public ReConsentimientoDen2015Id getReconsdenId() {
		return reconsdenId;
	}

	public void setReconsdenId(ReConsentimientoDen2015Id reconsdenId) {
		this.reconsdenId = reconsdenId;
	}

	
	public Integer getAutsup() {
		return autsup;
	}

	public void setAutsup(Integer autsup) {
		this.autsup = autsup;
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


	public Integer getAsentimiento() {
		return asentimiento;
	}

	public void setAsentimiento(Integer asentimiento) {
		this.asentimiento = asentimiento;
	}

	public Integer getParteADen() {
		return parteADen;
	}

	public void setParteADen(Integer parteADen) {
		this.parteADen = parteADen;
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

	public Integer getRelacionFam() {
		return relacionFam;
	}

	public void setRelacionFam(Integer relacionFam) {
		this.relacionFam = relacionFam;
	}

	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
	}

	public String getDire() {
		return dire;
	}

	public void setDire(String dire) {
		this.dire = dire;
	}

	public String getVisExit() {
		return visExit;
	}

	public void setVisExit(String visExit) {
		this.visExit = visExit;
	}

	public String getRazonVisNoExit() {
		return razonVisNoExit;
	}

	public void setRazonVisNoExit(String razonVisNoExit) {
		this.razonVisNoExit = razonVisNoExit;
	}

	public String getPersonaDejoCarta() {
		return personaDejoCarta;
	}

	public void setPersonaDejoCarta(String personaDejoCarta) {
		this.personaDejoCarta = personaDejoCarta;
	}

	public String getPersonaCasa() {
		return personaCasa;
	}

	public void setPersonaCasa(String personaCasa) {
		this.personaCasa = personaCasa;
	}

	public String getRelacionFamPersonaCasa() {
		return relacionFamPersonaCasa;
	}

	public void setRelacionFamPersonaCasa(String relacionFamPersonaCasa) {
		this.relacionFamPersonaCasa = relacionFamPersonaCasa;
	}

	public String getOtraRelacionPersonaCasa() {
		return otraRelacionPersonaCasa;
	}

	public void setOtraRelacionPersonaCasa(String otraRelacionPersonaCasa) {
		this.otraRelacionPersonaCasa = otraRelacionPersonaCasa;
	}

	public Integer getTelefonoPersonaCasa() {
		return telefonoPersonaCasa;
	}

	public void setTelefonoPersonaCasa(Integer telefonoPersonaCasa) {
		this.telefonoPersonaCasa = telefonoPersonaCasa;
	}

	public String getEmancipado() {
		return emancipado;
	}

	public void setEmancipado(String emancipado) {
		this.emancipado = emancipado;
	}

	public String getDescEmancipado() {
		return descEmancipado;
	}

	public void setDescEmancipado(String descEmancipado) {
		this.descEmancipado = descEmancipado;
	}

	public Integer getAssite() {
		return assite;
	}

	public void setAssite(Integer assite) {
		this.assite = assite;
	}

	public Integer getCentrosalud() {
		return centrosalud;
	}

	public void setCentrosalud(Integer centrosalud) {
		this.centrosalud = centrosalud;
	}

	public String getOcentrosalud() {
		return ocentrosalud;
	}

	public void setOcentrosalud(String ocentrosalud) {
		this.ocentrosalud = ocentrosalud;
	}

	public Integer getPuestosalud() {
		return puestosalud;
	}

	public void setPuestosalud(Integer puestosalud) {
		this.puestosalud = puestosalud;
	}

	public String getRechDen() {
		return rechDen;
	}

	public void setRechDen(String rechDen) {
		this.rechDen = rechDen;
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

	public String getOtrobarrio() {
		return otrobarrio;
	}

	public void setOtrobarrio(String otrobarrio) {
		this.otrobarrio = otrobarrio;
	}

	public Integer getTelefonoClasif1() {
		return telefonoClasif1;
	}

	public void setTelefonoClasif1(Integer telefonoClasif1) {
		this.telefonoClasif1 = telefonoClasif1;
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

	public Integer getTelefono2SN() {
		return telefono2SN;
	}

	public void setTelefono2SN(Integer telefono2sn) {
		telefono2SN = telefono2sn;
	}

	public Integer getTelefonoClasif2() {
		return telefonoClasif2;
	}

	public void setTelefonoClasif2(Integer telefonoClasif2) {
		this.telefonoClasif2 = telefonoClasif2;
	}

	public Integer getTelefonoConv2() {
		return telefonoConv2;
	}

	public void setTelefonoConv2(Integer telefonoConv2) {
		this.telefonoConv2 = telefonoConv2;
	}

	public Integer getTelefonoCel2() {
		return telefonoCel2;
	}

	public void setTelefonoCel2(Integer telefonoCel2) {
		this.telefonoCel2 = telefonoCel2;
	}

	public Integer getTelefono3SN() {
		return telefono3SN;
	}

	public void setTelefono3SN(Integer telefono3sn) {
		telefono3SN = telefono3sn;
	}

	public Integer getTelefonoClasif3() {
		return telefonoClasif3;
	}

	public void setTelefonoClasif3(Integer telefonoClasif3) {
		this.telefonoClasif3 = telefonoClasif3;
	}

	public Integer getTelefonoConv3() {
		return telefonoConv3;
	}

	public void setTelefonoConv3(Integer telefonoConv3) {
		this.telefonoConv3 = telefonoConv3;
	}

	public Integer getTelefonoCel3() {
		return telefonoCel3;
	}

	public void setTelefonoCel3(Integer telefonoCel3) {
		this.telefonoCel3 = telefonoCel3;
	}

	public String getJefenom() {
		return jefenom;
	}

	public void setJefenom(String jefenom) {
		this.jefenom = jefenom;
	}

	public String getJefenom2() {
		return jefenom2;
	}

	public void setJefenom2(String jefenom2) {
		this.jefenom2 = jefenom2;
	}

	public String getJefeap() {
		return jefeap;
	}

	public void setJefeap(String jefeap) {
		this.jefeap = jefeap;
	}

	public String getJefeap2() {
		return jefeap2;
	}

	public void setJefeap2(String jefeap2) {
		this.jefeap2 = jefeap2;
	}

	public String getNomContacto() {
		return nomContacto;
	}

	public void setNomContacto(String nomContacto) {
		this.nomContacto = nomContacto;
	}

	public Integer getBarrioContacto() {
		return barrioContacto;
	}

	public void setBarrioContacto(Integer barrioContacto) {
		this.barrioContacto = barrioContacto;
	}

	public String getOtrobarrioContacto() {
		return otrobarrioContacto;
	}

	public void setOtrobarrioContacto(String otrobarrioContacto) {
		this.otrobarrioContacto = otrobarrioContacto;
	}

	public String getDireContacto() {
		return direContacto;
	}

	public void setDireContacto(String direContacto) {
		this.direContacto = direContacto;
	}

	public Integer getTelContacto1() {
		return telContacto1;
	}

	public void setTelContacto1(Integer telContacto1) {
		this.telContacto1 = telContacto1;
	}

	public Integer getTelContactoConv1() {
		return telContactoConv1;
	}

	public void setTelContactoConv1(Integer telContactoConv1) {
		this.telContactoConv1 = telContactoConv1;
	}

	public Integer getTelContactoCel1() {
		return telContactoCel1;
	}

	public void setTelContactoCel1(Integer telContactoCel1) {
		this.telContactoCel1 = telContactoCel1;
	}

	public Integer getTelContacto2SN() {
		return telContacto2SN;
	}

	public void setTelContacto2SN(Integer telContacto2SN) {
		this.telContacto2SN = telContacto2SN;
	}

	public Integer getTelContactoClasif2() {
		return telContactoClasif2;
	}

	public void setTelContactoClasif2(Integer telContactoClasif2) {
		this.telContactoClasif2 = telContactoClasif2;
	}

	public Integer getTelContactoConv2() {
		return telContactoConv2;
	}

	public void setTelContactoConv2(Integer telContactoConv2) {
		this.telContactoConv2 = telContactoConv2;
	}

	public Integer getTelContactoCel2() {
		return telContactoCel2;
	}

	public void setTelContactoCel2(Integer telContactoCel2) {
		this.telContactoCel2 = telContactoCel2;
	}

	public String getNombrepadre() {
		return nombrepadre;
	}

	public void setNombrepadre(String nombrepadre) {
		this.nombrepadre = nombrepadre;
	}

	public String getNombrepadre2() {
		return nombrepadre2;
	}

	public void setNombrepadre2(String nombrepadre2) {
		this.nombrepadre2 = nombrepadre2;
	}

	public String getApellidopadre() {
		return apellidopadre;
	}

	public void setApellidopadre(String apellidopadre) {
		this.apellidopadre = apellidopadre;
	}

	public String getApellidopadre2() {
		return apellidopadre2;
	}

	public void setApellidopadre2(String apellidopadre2) {
		this.apellidopadre2 = apellidopadre2;
	}

	public String getNombremadre() {
		return nombremadre;
	}

	public void setNombremadre(String nombremadre) {
		this.nombremadre = nombremadre;
	}

	public String getNombremadre2() {
		return nombremadre2;
	}

	public void setNombremadre2(String nombremadre2) {
		this.nombremadre2 = nombremadre2;
	}

	public String getApellidomadre() {
		return apellidomadre;
	}

	public void setApellidomadre(String apellidomadre) {
		this.apellidomadre = apellidomadre;
	}

	public String getApellidomadre2() {
		return apellidomadre2;
	}

	public void setApellidomadre2(String apellidomadre2) {
		this.apellidomadre2 = apellidomadre2;
	}

	public Integer getCopiaFormato() {
		return copiaFormato;
	}

	public void setCopiaFormato(Integer copiaFormato) {
		this.copiaFormato = copiaFormato;
	}

	public Integer getFirmo_cartcons() {
		return firmo_cartcons;
	}

	public void setFirmo_cartcons(Integer firmo_cartcons) {
		this.firmo_cartcons = firmo_cartcons;
	}

	public Integer getFecho_cartcons() {
		return fecho_cartcons;
	}

	public void setFecho_cartcons(Integer fecho_cartcons) {
		this.fecho_cartcons = fecho_cartcons;
	}

	public Integer getHuella_dig() {
		return huella_dig;
	}

	public void setHuella_dig(Integer huella_dig) {
		this.huella_dig = huella_dig;
	}

	public Integer getFech_firm_testigo() {
		return fech_firm_testigo;
	}

	public void setFech_firm_testigo(Integer fech_firm_testigo) {
		this.fech_firm_testigo = fech_firm_testigo;
	}

	public Integer getEntiende() {
		return entiende;
	}

	public void setEntiende(Integer entiende) {
		this.entiende = entiende;
	}
	
	public String getGeoref() {
		return georef;
	}

	public void setGeoref(String georef) {
		this.georef = georef;
	}

	public Integer getManzana() {
		return Manzana;
	}

	public void setManzana(Integer manzana) {
		Manzana = manzana;
	}

	public String getGeoref_razon() {
		return georef_razon;
	}

	public void setGeoref_razon(String georef_razon) {
		this.georef_razon = georef_razon;
	}

	public String getGeoref_otraRazon() {
		return georef_otraRazon;
	}

	public void setGeoref_otraRazon(String georef_otraRazon) {
		this.georef_otraRazon = georef_otraRazon;
	}

	public Integer getLocal() {
		return local;
	}

	public void setLocal(Integer local) {
		this.local = local;
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
	
	@Override
	public String toString() {
		return this.getReconsdenId().getCodigo().toString();
	}

}
