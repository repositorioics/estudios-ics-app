package ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;

/**
 * Simple objeto de dominio que representa los datos de la lactancia materna
 * 
 * @author Brenda Lopez
 **/

public class EncuestaLactanciaMaterna extends BaseMetaData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

    private ParticipanteCohorteFamilia participante;
	private String dioPecho;
	private String tiemPecho;
	private Integer mesDioPecho;
	private String pechoExc;
    private String formAlim;
    private String pechoExcAntes;
	private String tiempPechoExcAntes;
	private Integer mestPechoExc;
    private String otraAlim;
    private String edadLiqDistPecho;
	private Integer mesDioLiqDisPecho;
	private String edadLiqDistLeche;
	private Integer mesDioLiqDisLeche;
	private String edAlimSolidos;
	private Integer mesDioAlimSol;
	private String recurso1;
	private String otrorecurso1;

    public ParticipanteCohorteFamilia getParticipante() {
        return participante;
    }

    public void setParticipante(ParticipanteCohorteFamilia participante) {
        this.participante = participante;
    }

    public String getDioPecho() {
        return dioPecho;
    }

    public void setDioPecho(String dioPecho) {
        this.dioPecho = dioPecho;
    }

    public String getTiemPecho() {
        return tiemPecho;
    }

    public void setTiemPecho(String tiemPecho) {
        this.tiemPecho = tiemPecho;
    }

    public Integer getMesDioPecho() {
        return mesDioPecho;
    }

    public void setMesDioPecho(Integer mesDioPecho) {
        this.mesDioPecho = mesDioPecho;
    }

    public String getPechoExc() {
        return pechoExc;
    }

    public void setPechoExc(String pechoExc) {
        this.pechoExc = pechoExc;
    }

    public String getPechoExcAntes() {
        return pechoExcAntes;
    }

    public void setPechoExcAntes(String pechoExcAntes) {
        this.pechoExcAntes = pechoExcAntes;
    }

    public String getTiempPechoExcAntes() {
        return tiempPechoExcAntes;
    }

    public void setTiempPechoExcAntes(String tiempPechoExcAntes) {
        this.tiempPechoExcAntes = tiempPechoExcAntes;
    }

    public Integer getMestPechoExc() {
        return mestPechoExc;
    }

    public void setMestPechoExc(Integer mestPechoExc) {
        this.mestPechoExc = mestPechoExc;
    }

    public String getFormAlim() {
        return formAlim;
    }

    public void setFormAlim(String formAlim) {
        this.formAlim = formAlim;
    }

    public String getOtraAlim() {
        return otraAlim;
    }

    public void setOtraAlim(String otraAlim) {
        this.otraAlim = otraAlim;
    }

    public String getEdadLiqDistPecho() {
        return edadLiqDistPecho;
    }

    public void setEdadLiqDistPecho(String edadLiqDistPecho) {
        this.edadLiqDistPecho = edadLiqDistPecho;
    }

    public Integer getMesDioLiqDisPecho() {
        return mesDioLiqDisPecho;
    }

    public void setMesDioLiqDisPecho(Integer mesDioLiqDisPecho) {
        this.mesDioLiqDisPecho = mesDioLiqDisPecho;
    }

    public String getEdadLiqDistLeche() {
        return edadLiqDistLeche;
    }

    public void setEdadLiqDistLeche(String edadLiqDistLeche) {
        this.edadLiqDistLeche = edadLiqDistLeche;
    }

    public Integer getMesDioLiqDisLeche() {
        return mesDioLiqDisLeche;
    }

    public void setMesDioLiqDisLeche(Integer mesDioLiqDisLeche) {
        this.mesDioLiqDisLeche = mesDioLiqDisLeche;
    }

    public String getEdAlimSolidos() {
        return edAlimSolidos;
    }

    public void setEdAlimSolidos(String edAlimSolidos) {
        this.edAlimSolidos = edAlimSolidos;
    }

    public Integer getMesDioAlimSol() {
        return mesDioAlimSol;
    }

    public void setMesDioAlimSol(Integer mesDioAlimSol) {
        this.mesDioAlimSol = mesDioAlimSol;
    }

    public String getRecurso1() {
        return recurso1;
    }

    public void setRecurso1(String recurso1) {
        this.recurso1 = recurso1;
    }

    public String getOtrorecurso1() {
        return otrorecurso1;
    }

    public void setOtrorecurso1(String otrorecurso1) {
        this.otrorecurso1 = otrorecurso1;
    }

    @Override
    public String toString() {
        return "EncuestaLactanciaMaterna{" + participante.getParticipanteCHF() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncuestaLactanciaMaterna)) return false;

        EncuestaLactanciaMaterna that = (EncuestaLactanciaMaterna) o;

        return  (!participante.equals(that.participante));
    }

    @Override
    public int hashCode() {
        return participante.hashCode();
    }
}
