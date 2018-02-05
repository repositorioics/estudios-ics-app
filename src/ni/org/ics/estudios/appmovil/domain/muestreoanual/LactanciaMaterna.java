package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;


/**
 * Simple objeto de dominio que representa los datos de la lactancia materna
 * 
 * @author Brenda Lopez
 **/

public class LactanciaMaterna implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private LactanciaMaternaId lmId;
	private Integer edad;
	private Integer dioPecho;
	private Integer tiemPecho;
	private Integer mesDioPecho;
	private Integer pechoExc;
	private Integer pechoExcAntes;
	private Integer tiempPechoExcAntes;
	private Integer mestPechoExc;
	private Integer formAlim;
	private String otraAlim;
	private Integer edadLiqDistPecho;
	private Integer mesDioLiqDisLeche;
	private Integer edadLiqDistLeche;
	private Integer mesDioLiqDisPecho;
	private Integer edAlimSolidos;
	private Integer mesDioAlimSol;
	private MovilInfo movilInfo;
	private Integer otrorecurso1;
	private Integer otrorecurso2;

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

	public LactanciaMaternaId getLmId() {
		return lmId;
	}

	public void setLmId(LactanciaMaternaId lmId) {
		this.lmId = lmId;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
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

	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
	}
	
	@Override
	public String toString() {
		return this.lmId.getCodigo().toString();
	}

}
