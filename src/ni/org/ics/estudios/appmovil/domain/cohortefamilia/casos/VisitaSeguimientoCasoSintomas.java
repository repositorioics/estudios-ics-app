package ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos;

import java.util.Date;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */
public class VisitaSeguimientoCasoSintomas extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCasoSintoma;
	private VisitaSeguimientoCaso codigoVisitaCaso;
	private Date fechaSintomas;
	private String periodo;
	private String fiebre;
	private Date fif;
	private String fiebreCuantificada;
	private Float valorFiebreCuantificada;
	private String dolorCabeza;
	private String dolorArticular;
	private String dolorMuscular;
	private String dificultadRespiratoria;
	private Date fdr;
	private String secrecionNasal;
	private Date fsn;
	private String tos;
	private Date ftos;
	private String pocoApetito;
	private String dolorGarganta;
	private String diarrea;
	private String quedoCama;
	private String respiracionRuidosa;
	private Date frr;
	private String oseltamivir;
	private String antibiotico;
	private String cualAntibiotico;
	private String prescritoMedico;
    private String respiracionRapida;
    //se agrega intensidad sintomas
	private String fiebreIntensidad;
	private String dolorCabezaIntensidad;
	private String dolorArticularIntensidad;
	private String dolorMuscularIntensidad;
	private String secrecionNasalIntensidad;
	private String tosIntensidad;
	private String dolorGargantaIntensidad;

	public String getCodigoCasoSintoma() {
		return codigoCasoSintoma;
	}

	public void setCodigoCasoSintoma(String codigoCasoSintoma) {
		this.codigoCasoSintoma = codigoCasoSintoma;
	}

	public VisitaSeguimientoCaso getCodigoVisitaCaso() {
		return codigoVisitaCaso;
	}

	public void setCodigoVisitaCaso(VisitaSeguimientoCaso codigoVisitaCaso) {
		this.codigoVisitaCaso = codigoVisitaCaso;
	}
	
	public Date getFechaSintomas() {
		return fechaSintomas;
	}

	public void setFechaSintomas(Date fechaSintomas) {
		this.fechaSintomas = fechaSintomas;
	}
	
	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getFiebre() {
		return fiebre;
	}

	public void setFiebre(String fiebre) {
		this.fiebre = fiebre;
	}
	
	public Date getFif() {
		return fif;
	}

	public void setFif(Date fif) {
		this.fif = fif;
	}

	public String getFiebreCuantificada() {
		return fiebreCuantificada;
	}

	public void setFiebreCuantificada(String fiebreCuantificada) {
		this.fiebreCuantificada = fiebreCuantificada;
	}
	
	public Float getValorFiebreCuantificada() {
		return valorFiebreCuantificada;
	}

	public void setValorFiebreCuantificada(Float valorFiebreCuantificada) {
		this.valorFiebreCuantificada = valorFiebreCuantificada;
	}

	public String getDolorCabeza() {
		return dolorCabeza;
	}

	public void setDolorCabeza(String dolorCabeza) {
		this.dolorCabeza = dolorCabeza;
	}

	public String getDolorArticular() {
		return dolorArticular;
	}

	public void setDolorArticular(String dolorArticular) {
		this.dolorArticular = dolorArticular;
	}

	public String getDolorMuscular() {
		return dolorMuscular;
	}

	public void setDolorMuscular(String dolorMuscular) {
		this.dolorMuscular = dolorMuscular;
	}

	public String getDificultadRespiratoria() {
		return dificultadRespiratoria;
	}

	public void setDificultadRespiratoria(String dificultadRespiratoria) {
		this.dificultadRespiratoria = dificultadRespiratoria;
	}
	
	public Date getFdr() {
		return fdr;
	}

	public void setFdr(Date fdr) {
		this.fdr = fdr;
	}

	public String getSecrecionNasal() {
		return secrecionNasal;
	}

	public void setSecrecionNasal(String secrecionNasal) {
		this.secrecionNasal = secrecionNasal;
	}
	
	public Date getFsn() {
		return fsn;
	}

	public void setFsn(Date fsn) {
		this.fsn = fsn;
	}

	public String getTos() {
		return tos;
	}

	public void setTos(String tos) {
		this.tos = tos;
	}
	
	public Date getFtos() {
		return ftos;
	}

	public void setFtos(Date ftos) {
		this.ftos = ftos;
	}

	public String getPocoApetito() {
		return pocoApetito;
	}

	public void setPocoApetito(String pocoApetito) {
		this.pocoApetito = pocoApetito;
	}

	public String getDolorGarganta() {
		return dolorGarganta;
	}

	public void setDolorGarganta(String dolorGarganta) {
		this.dolorGarganta = dolorGarganta;
	}

	public String getDiarrea() {
		return diarrea;
	}

	public void setDiarrea(String diarrea) {
		this.diarrea = diarrea;
	}

	public String getQuedoCama() {
		return quedoCama;
	}

	public void setQuedoCama(String quedoCama) {
		this.quedoCama = quedoCama;
	}

	public String getRespiracionRuidosa() {
		return respiracionRuidosa;
	}

	public void setRespiracionRuidosa(String respiracionRuidosa) {
		this.respiracionRuidosa = respiracionRuidosa;
	}
	
	public Date getFrr() {
		return frr;
	}

	public void setFrr(Date frr) {
		this.frr = frr;
	}

	public String getOseltamivir() {
		return oseltamivir;
	}

	public void setOseltamivir(String oseltamivir) {
		this.oseltamivir = oseltamivir;
	}

	public String getAntibiotico() {
		return antibiotico;
	}

	public void setAntibiotico(String antibiotico) {
		this.antibiotico = antibiotico;
	}

	public String getCualAntibiotico() {
		return cualAntibiotico;
	}

	public void setCualAntibiotico(String cualAntibiotico) {
		this.cualAntibiotico = cualAntibiotico;
	}

	public String getPrescritoMedico() {
		return prescritoMedico;
	}

	public void setPrescritoMedico(String prescritoMedico) {
		this.prescritoMedico = prescritoMedico;
	}

    public String getRespiracionRapida() {
        return respiracionRapida;
    }

    public void setRespiracionRapida(String respiracionRapida) {
        this.respiracionRapida = respiracionRapida;
    }

	public String getFiebreIntensidad() {
		return fiebreIntensidad;
	}

	public void setFiebreIntensidad(String fiebreIntensidad) {
		this.fiebreIntensidad = fiebreIntensidad;
	}

	public String getDolorCabezaIntensidad() {
		return dolorCabezaIntensidad;
	}

	public void setDolorCabezaIntensidad(String dolorCabezaIntensidad) {
		this.dolorCabezaIntensidad = dolorCabezaIntensidad;
	}

	public String getDolorArticularIntensidad() {
		return dolorArticularIntensidad;
	}

	public void setDolorArticularIntensidad(String dolorArticularIntensidad) {
		this.dolorArticularIntensidad = dolorArticularIntensidad;
	}

	public String getDolorMuscularIntensidad() {
		return dolorMuscularIntensidad;
	}

	public void setDolorMuscularIntensidad(String dolorMuscularIntensidad) {
		this.dolorMuscularIntensidad = dolorMuscularIntensidad;
	}

	public String getSecrecionNasalIntensidad() {
		return secrecionNasalIntensidad;
	}

	public void setSecrecionNasalIntensidad(String secrecionNasalIntensidad) {
		this.secrecionNasalIntensidad = secrecionNasalIntensidad;
	}

	public String getTosIntensidad() {
		return tosIntensidad;
	}

	public void setTosIntensidad(String tosIntensidad) {
		this.tosIntensidad = tosIntensidad;
	}

	public String getDolorGargantaIntensidad() {
		return dolorGargantaIntensidad;
	}

	public void setDolorGargantaIntensidad(String dolorGargantaIntensidad) {
		this.dolorGargantaIntensidad = dolorGargantaIntensidad;
	}

	@Override
	public String toString(){
		return codigoVisitaCaso.getCodigoParticipanteCaso().getCodigoCaso().getCasa().getCodigoCHF() + "-" + codigoVisitaCaso.getCodigoParticipanteCaso().getParticipante().getParticipante().getCodigo() + "-" + codigoVisitaCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaInicio();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitaSeguimientoCasoSintomas)) return false;

        VisitaSeguimientoCasoSintomas that = (VisitaSeguimientoCasoSintomas) o;

        if (!codigoCasoSintoma.equals(that.codigoCasoSintoma)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoCasoSintoma.hashCode();
        result = 31 * result + codigoVisitaCaso.getCodigoParticipanteCaso().getParticipante().hashCode();
        return result;
    }

}
