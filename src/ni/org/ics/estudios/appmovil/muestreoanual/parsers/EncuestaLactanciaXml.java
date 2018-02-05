package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 * 
 * @author William Aviles
 **/

public class EncuestaLactanciaXml {
	
	@Element(required=false)
	private Integer edad;
	@Element(required=false)
	private Integer dioPecho;
	@Element(required=false)
	private Integer tiemPecho;
	@Element(required=false)
	private Integer mesDioPecho;
	@Element(required=false)
	private Integer pechoExc;
	@Element(required=false)
	private Integer pechoExcAntes;
	@Element(required=false)
	private Integer tiempPechoExcAntes;
	@Element(required=false)
	private Integer mestPechoExc;
	@Element(required=false)
	private Integer formAlim;
	@Element(required=false)
	private String otraAlim;
	@Element(required=false)
	private Integer edadLiqDistPecho;
	@Element(required=false)
	private Integer mesDioLiqDisLeche;
	@Element(required=false)
	private Integer edadLiqDistLeche;
	@Element(required=false)
	private Integer mesDioLiqDisPecho;
	@Element(required=false)
	private Integer edAlimSolidos;
	@Element(required=false)
	private Integer mesDioAlimSol;
	
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
	private String titulo6;
	@Element(required=false)
	private String titulo7;
	@Element(required=false)
	private String titulo8;
	
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
	
	public EncuestaLactanciaXml(){
		
	}

	public Integer getDioPecho() {
		return dioPecho;
	}

	public void setDioPecho(Integer dioPecho) {
		this.dioPecho = dioPecho;
	}

	public Integer getTiemPecho() {
		return tiemPecho;
	}

	public void setTiemPecho(Integer tiemPecho) {
		this.tiemPecho = tiemPecho;
	}

	public Integer getMesDioPecho() {
		return mesDioPecho;
	}

	public void setMesDioPecho(Integer mesDioPecho) {
		this.mesDioPecho = mesDioPecho;
	}

	public Integer getPechoExc() {
		return pechoExc;
	}

	public void setPechoExc(Integer pechoExc) {
		this.pechoExc = pechoExc;
	}
	
	public Integer getPechoExcAntes() {
		return pechoExcAntes;
	}

	public void setPechoExcAntes(Integer pechoExcAntes) {
		this.pechoExcAntes = pechoExcAntes;
	}

	public Integer getTiempPechoExcAntes() {
		return tiempPechoExcAntes;
	}

	public void setTiempPechoExcAntes(Integer tiempPechoExcAntes) {
		this.tiempPechoExcAntes = tiempPechoExcAntes;
	}

	public Integer getMestPechoExc() {
		return mestPechoExc;
	}

	public void setMestPechoExc(Integer mestPechoExc) {
		this.mestPechoExc = mestPechoExc;
	}


	public Integer getFormAlim() {
		return formAlim;
	}

	public void setFormAlim(Integer formAlim) {
		this.formAlim = formAlim;
	}

	public String getOtraAlim() {
		return otraAlim;
	}

	public void setOtraAlim(String otraAlim) {
		this.otraAlim = otraAlim;
	}

	public Integer getEdadLiqDistPecho() {
		return edadLiqDistPecho;
	}

	public void setEdadLiqDistPecho(Integer edadLiqDistPecho) {
		this.edadLiqDistPecho = edadLiqDistPecho;
	}

	public Integer getMesDioLiqDisLeche() {
		return mesDioLiqDisLeche;
	}

	public void setMesDioLiqDisLeche(Integer mesDioLiqDisLeche) {
		this.mesDioLiqDisLeche = mesDioLiqDisLeche;
	}

	public Integer getEdadLiqDistLeche() {
		return edadLiqDistLeche;
	}

	public void setEdadLiqDistLeche(Integer edadLiqDistLeche) {
		this.edadLiqDistLeche = edadLiqDistLeche;
	}

	public Integer getMesDioLiqDisPecho() {
		return mesDioLiqDisPecho;
	}

	public void setMesDioLiqDisPecho(Integer mesDioLiqDisPecho) {
		this.mesDioLiqDisPecho = mesDioLiqDisPecho;
	}

	public Integer getEdAlimSolidos() {
		return edAlimSolidos;
	}

	public void setEdAlimSolidos(Integer edAlimSolidos) {
		this.edAlimSolidos = edAlimSolidos;
	}

	public Integer getMesDioAlimSol() {
		return mesDioAlimSol;
	}

	public void setMesDioAlimSol(Integer mesDioAlimSol) {
		this.mesDioAlimSol = mesDioAlimSol;
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
	
	

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
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

	public String getTitulo5() {
		return titulo5;
	}

	public void setTitulo5(String titulo5) {
		this.titulo5 = titulo5;
	}

	public String getTitulo6() {
		return titulo6;
	}

	public void setTitulo6(String titulo6) {
		this.titulo6 = titulo6;
	}

	public String getTitulo7() {
		return titulo7;
	}

	public void setTitulo7(String titulo7) {
		this.titulo7 = titulo7;
	}

	public String getTitulo8() {
		return titulo8;
	}

	public void setTitulo8(String titulo8) {
		this.titulo8 = titulo8;
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

	public Integer getOtrorecurso2() {
		return otrorecurso2;
	}

	public void setOtrorecurso2(Integer otrorecurso2) {
		this.otrorecurso2 = otrorecurso2;
	}
	
	

}
