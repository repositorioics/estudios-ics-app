package ni.org.ics.estudios.appmovil.domain;


import ni.org.ics.estudios.appmovil.catalogs.Estudio;

import java.util.Date;

/**
 * Created by FIRSTICT on 4/28/2017.
 * V1.0
 */
public class Tamizaje extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
    private Estudio estudio;
    private String sexo;
    private Date fechaNacimiento;
    private char aceptaTamizaje;
    private String razonNoParticipa;
    private String criteriosInclusion;
    private String dondeAsisteProblemasSalud;
    private char asisteCSSF;
    private String otroCentroSalud;
    private String puestoSalud;
    private char siEnfermaSoloAsistirCSSF;
    private char elegible;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Estudio getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public char getAceptaTamizaje() {
        return aceptaTamizaje;
    }

    public void setAceptaTamizaje(char aceptaTamizaje) {
        this.aceptaTamizaje = aceptaTamizaje;
    }

    public String getRazonNoParticipa() {
        return razonNoParticipa;
    }

    public void setRazonNoParticipa(String razonNoParticipa) {
        this.razonNoParticipa = razonNoParticipa;
    }

    public String getCriteriosInclusion() {
		return criteriosInclusion;
	}

	public void setCriteriosInclusion(String criteriosInclusion) {
		this.criteriosInclusion = criteriosInclusion;
	}

	public char getElegible() {
        return elegible;
    }

    public void setElegible(char elegible) {
        this.elegible = elegible;
    }

    public String getDondeAsisteProblemasSalud() {
        return dondeAsisteProblemasSalud;
    }

    public void setDondeAsisteProblemasSalud(String dondeAsisteProblemasSalud) {
        this.dondeAsisteProblemasSalud = dondeAsisteProblemasSalud;
    }

    public char getAsisteCSSF() {
        return asisteCSSF;
    }

    public void setAsisteCSSF(char asisteCSSF) {
        this.asisteCSSF = asisteCSSF;
    }

    public String getOtroCentroSalud() {
        return otroCentroSalud;
    }

    public void setOtroCentroSalud(String otroCentroSalud) {
        this.otroCentroSalud = otroCentroSalud;
    }

    public String getPuestoSalud() {
        return puestoSalud;
    }

    public void setPuestoSalud(String puestoSalud) {
        this.puestoSalud = puestoSalud;
    }

    public char getSiEnfermaSoloAsistirCSSF() {
        return siEnfermaSoloAsistirCSSF;
    }

    public void setSiEnfermaSoloAsistirCSSF(char siEnfermaSoloAsistirCSSF) {
        this.siEnfermaSoloAsistirCSSF = siEnfermaSoloAsistirCSSF;
    }

    @Override
    public String toString() {
        return "'" + codigo + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tamizaje)) return false;

        Tamizaje tamizaje = (Tamizaje) o;

        return (!codigo.equals(tamizaje.codigo));
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
