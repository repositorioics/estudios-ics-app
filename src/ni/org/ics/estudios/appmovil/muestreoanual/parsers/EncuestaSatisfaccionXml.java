package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 * 
 * @author William Aviles
 **/

public class EncuestaSatisfaccionXml {
	
	@Element(required=false)
	private String estudio;
	@Element(required=false)
	private Integer atenPerEst;
	@Element(required=false)
	private Integer tiemAten;
	@Element(required=false)
	private Integer atenPerAdm;
	@Element(required=false)
	private Integer atenPerEnferm;
	@Element(required=false)
	private Integer atenPerMed;
	@Element(required=false)
	private Integer ambAten;
	@Element(required=false)
	private Integer atenPerLab;
	@Element(required=false)
	private Integer explDxEnf;
	@Element(required=false)
	private Integer fludenSN;
	@Element(required=false)
	private Integer fluConImp;
	@Element(required=false)
	private Integer denConImp;
	@Element(required=false)
	private Integer explPeligEnf;
	@Element(required=false)
	private Integer expMedCuid;
	
	@Element(required=false)
	private Integer otrorecurso1;
	
	@Element(required=false)
	private String titulo1;
	@Element(required=false)
	private String titulo2;
	@Element(required=false)
	private String titulo3;
	
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
	
	public EncuestaSatisfaccionXml(){
		
	}

	public String getEstudio() {
		return estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public Integer getAtenPerEst() {
		return atenPerEst;
	}

	public void setAtenPerEst(Integer atenPerEst) {
		this.atenPerEst = atenPerEst;
	}

	public Integer getTiemAten() {
		return tiemAten;
	}

	public void setTiemAten(Integer tiemAten) {
		this.tiemAten = tiemAten;
	}

	public Integer getAtenPerAdm() {
		return atenPerAdm;
	}

	public void setAtenPerAdm(Integer atenPerAdm) {
		this.atenPerAdm = atenPerAdm;
	}

	public Integer getAtenPerEnferm() {
		return atenPerEnferm;
	}

	public void setAtenPerEnferm(Integer atenPerEnferm) {
		this.atenPerEnferm = atenPerEnferm;
	}

	public Integer getAtenPerMed() {
		return atenPerMed;
	}

	public void setAtenPerMed(Integer atenPerMed) {
		this.atenPerMed = atenPerMed;
	}

	public Integer getAmbAten() {
		return ambAten;
	}

	public void setAmbAten(Integer ambAten) {
		this.ambAten = ambAten;
	}

	public Integer getAtenPerLab() {
		return atenPerLab;
	}

	public void setAtenPerLab(Integer atenPerLab) {
		this.atenPerLab = atenPerLab;
	}

	public Integer getExplDxEnf() {
		return explDxEnf;
	}

	public void setExplDxEnf(Integer explDxEnf) {
		this.explDxEnf = explDxEnf;
	}

	public Integer getFludenSN() {
		return fludenSN;
	}

	public void setFludenSN(Integer fludenSN) {
		this.fludenSN = fludenSN;
	}

	public Integer getFluConImp() {
		return fluConImp;
	}

	public void setFluConImp(Integer fluConImp) {
		this.fluConImp = fluConImp;
	}

	public Integer getDenConImp() {
		return denConImp;
	}

	public void setDenConImp(Integer denConImp) {
		this.denConImp = denConImp;
	}

	public Integer getExplPeligEnf() {
		return explPeligEnf;
	}

	public void setExplPeligEnf(Integer explPeligEnf) {
		this.explPeligEnf = explPeligEnf;
	}

	public Integer getExpMedCuid() {
		return expMedCuid;
	}

	public void setExpMedCuid(Integer expMedCuid) {
		this.expMedCuid = expMedCuid;
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
