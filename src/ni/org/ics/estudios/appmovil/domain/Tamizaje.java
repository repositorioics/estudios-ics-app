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
    private String otraRazonNoAceptaTamizajePersona;
    private String criteriosInclusion;
    private String enfermedad;
    private String dondeAsisteProblemasSalud;
    private String otroCentroSalud;
    private String puestoSalud;
    private String aceptaAtenderCentro;
    private String esElegible;
    private String aceptaParticipar;
    private String razonNoAceptaParticipar;
    private String otraRazonNoAceptaParticipar;
    private String asentimientoVerbal;
    //nuevo ingreso MA2018
    private String pretermino;
    private String cohorte;
    private String enfermedadInmuno;
    //private String cualEnfermedad;
    private String enfermedadCronica;
    private String tratamiento;
    private String cualTratamiento;
    private String diagDengue;
    private Date fechaDiagDengue;
    private String hospDengue;
    private Date fechaHospDengue;
    private String tiempoResidencia;
    //reconsentimiento Dengue 2018
    private String tipoVivienda;
    //private String otraEnfCronica;
    //private String enfCronicaAnio;
    //private String enfCronicaMes;
    private String otroTx;
    private String autorizaSupervisor;
    private String emancipado;
    private String razonEmancipacion;
    private String otraRazonEmancipacion;
    private Integer codigoParticipanteRecon;

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

    public String getOtraRazonNoAceptaParticipar() {
        return otraRazonNoAceptaParticipar;
    }

    public void setOtraRazonNoAceptaParticipar(String otraRazonNoAceptaParticipar) {
        this.otraRazonNoAceptaParticipar = otraRazonNoAceptaParticipar;
    }

    public String getOtraRazonNoAceptaTamizajePersona() {
        return otraRazonNoAceptaTamizajePersona;
    }

    public void setOtraRazonNoAceptaTamizajePersona(String otraRazonNoAceptaTamizajePersona) {
        this.otraRazonNoAceptaTamizajePersona = otraRazonNoAceptaTamizajePersona;
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

    public String getPretermino() {
        return pretermino;
    }

    public void setPretermino(String pretermino) {
        this.pretermino = pretermino;
    }

    public String getCohorte() {
        return cohorte;
    }

    public void setCohorte(String cohorte) {
        this.cohorte = cohorte;
    }

    public String getEnfermedadInmuno() {
        return enfermedadInmuno;
    }

    public void setEnfermedadInmuno(String enfermedadInmuno) {
        this.enfermedadInmuno = enfermedadInmuno;
    }

    public String getEnfermedadCronica() {
        return enfermedadCronica;
    }

    public void setEnfermedadCronica(String enfermedadCronica) {
        this.enfermedadCronica = enfermedadCronica;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getCualTratamiento() {
        return cualTratamiento;
    }

    public void setCualTratamiento(String cualTratamiento) {
        this.cualTratamiento = cualTratamiento;
    }

    public String getDiagDengue() {
        return diagDengue;
    }

    public void setDiagDengue(String diagDengue) {
        this.diagDengue = diagDengue;
    }

    public Date getFechaDiagDengue() {
        return fechaDiagDengue;
    }

    public void setFechaDiagDengue(Date fechaDiagDengue) {
        this.fechaDiagDengue = fechaDiagDengue;
    }

    public String getHospDengue() {
        return hospDengue;
    }

    public void setHospDengue(String hospDengue) {
        this.hospDengue = hospDengue;
    }

    public Date getFechaHospDengue() {
        return fechaHospDengue;
    }

    public void setFechaHospDengue(Date fechaHospDengue) {
        this.fechaHospDengue = fechaHospDengue;
    }

    public String getTiempoResidencia() {
        return tiempoResidencia;
    }

    public void setTiempoResidencia(String tiempoResidencia) {
        this.tiempoResidencia = tiempoResidencia;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getOtroTx() {
        return otroTx;
    }

    public void setOtroTx(String otroTx) {
        this.otroTx = otroTx;
    }

    public String getAutorizaSupervisor() {
        return autorizaSupervisor;
    }

    public void setAutorizaSupervisor(String autorizaSupervisor) {
        this.autorizaSupervisor = autorizaSupervisor;
    }

    public String getEmancipado() {
        return emancipado;
    }

    public void setEmancipado(String emancipado) {
        this.emancipado = emancipado;
    }

    public String getRazonEmancipacion() {
        return razonEmancipacion;
    }

    public void setRazonEmancipacion(String razonEmancipacion) {
        this.razonEmancipacion = razonEmancipacion;
    }

    public String getOtraRazonEmancipacion() {
        return otraRazonEmancipacion;
    }

    public void setOtraRazonEmancipacion(String otraRazonEmancipacion) {
        this.otraRazonEmancipacion = otraRazonEmancipacion;
    }

    public Integer getCodigoParticipanteRecon() {
        return codigoParticipanteRecon;
    }

    public void setCodigoParticipanteRecon(Integer codigoParticipanteRecon) {
        this.codigoParticipanteRecon = codigoParticipanteRecon;
    }
}
