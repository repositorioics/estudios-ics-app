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
    private String aceptaTamizajePersona;
    private String razonNoAceptaTamizajePersona;
    private String criteriosInclusion;
    private String enfermedad;
    private String dondeAsisteProblemasSalud;
    private String otroCentroSalud;
    private String puestoSalud;
    private String aceptaAtenderCentro;
    private String esElegible;
    private String aceptaParticipar;
    private String razonNoAceptaParticipar;
    private String asentimientoVerbal;
 
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

	public String getAceptaTamizajePersona() {
		return aceptaTamizajePersona;
	}

	public void setAceptaTamizajePersona(String aceptaTamizajePersona) {
		this.aceptaTamizajePersona = aceptaTamizajePersona;
	}

	public String getRazonNoAceptaTamizajePersona() {
		return razonNoAceptaTamizajePersona;
	}

	public void setRazonNoAceptaTamizajePersona(String razonNoAceptaTamizajePersona) {
		this.razonNoAceptaTamizajePersona = razonNoAceptaTamizajePersona;
	}

	public String getCriteriosInclusion() {
		return criteriosInclusion;
	}

	public void setCriteriosInclusion(String criteriosInclusion) {
		this.criteriosInclusion = criteriosInclusion;
	}

	public String getEnfermedad() {
		return enfermedad;
	}

	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}

	public String getDondeAsisteProblemasSalud() {
		return dondeAsisteProblemasSalud;
	}

	public void setDondeAsisteProblemasSalud(String dondeAsisteProblemasSalud) {
		this.dondeAsisteProblemasSalud = dondeAsisteProblemasSalud;
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

	public String getAceptaAtenderCentro() {
		return aceptaAtenderCentro;
	}

	public void setAceptaAtenderCentro(String aceptaAtenderCentro) {
		this.aceptaAtenderCentro = aceptaAtenderCentro;
	}

	public String getEsElegible() {
		return esElegible;
	}

	public void setEsElegible(String esElegible) {
		this.esElegible = esElegible;
	}

	public String getAceptaParticipar() {
		return aceptaParticipar;
	}

	public void setAceptaParticipar(String aceptaParticipar) {
		this.aceptaParticipar = aceptaParticipar;
	}

	public String getRazonNoAceptaParticipar() {
		return razonNoAceptaParticipar;
	}

	public void setRazonNoAceptaParticipar(String razonNoAceptaParticipar) {
		this.razonNoAceptaParticipar = razonNoAceptaParticipar;
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

	public String getAsentimientoVerbal() {
		return asentimientoVerbal;
	}

	public void setAsentimientoVerbal(String asentimientoVerbal) {
		this.asentimientoVerbal = asentimientoVerbal;
	}
}
