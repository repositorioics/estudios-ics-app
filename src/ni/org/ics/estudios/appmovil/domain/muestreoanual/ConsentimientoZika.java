package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;

/**
 * Simple objeto de dominio que representa los datos de consentimiento de Zika
 * 
 * 
 * @author Brenda Lopez
 **/

public class ConsentimientoZika implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private ConsentimientoZikaId consZikaId;
	
	private Integer parteDDen;
	private String rechDDen;
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
	private MovilInfo movilInfo;	
	private Integer otrorecurso1;
	private Integer otrorecurso2;

	
	public ConsentimientoZikaId getConsZikaId() {
		return consZikaId;
	}

	public void setConsZikaId(ConsentimientoZikaId consZikaId) {
		this.consZikaId = consZikaId;
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

}
