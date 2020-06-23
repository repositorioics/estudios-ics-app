package ni.org.ics.estudios.appmovil.domain.covid19;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

import java.util.Date;

/**
 * Created by Miguel Salinas on 19/06/2020.
 * V1.0
 */
public class SintomasVisitaCasoCovid19 extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCasoSintoma;
	private VisitaSeguimientoCasoCovid19 codigoCasoVisita;
	private Date fechaSintomas;
	private String fiebre;
	private String fiebreIntensidad;
	//private Date fif;
	private String fiebreCuantificada;
	private Float valorFiebreCuantificada;
	private String dolorCabeza;
	private String dolorCabezaIntensidad;
	private String dolorArticular;
	private String dolorArticularIntensidad;
	private String dolorMuscular;
	private String dolorMuscularIntensidad;
	private String dificultadRespiratoria;
	private String secrecionNasal;
	private String secrecionNasalIntensidad;
	private String tos;
	private String tosIntensidad;
	private String pocoApetito;
	private String dolorGarganta;
	private String dolorGargantaIntensidad;
	private String picorGarganta;
	private String congestionNasal;
	private String rash;
	private String urticaria;
	private String conjuntivitis;
	private String expectoracion;
	private String diarrea;
	private String vomito;
	private String fatiga;
	private String respiracionRuidosa;
	private String respiracionRapida;
	private String perdidaOlfato;
	private String perdidaGusto;
	private String desmayos;
	private String sensacionPechoApretado;
	private String dolorPecho;
	private String sensacionFaltaAire;
	private String quedoCama;
	private String tratamiento;
	private Date fechaInicioTx;
	private String cualMedicamento;
	private String otroMedicamento;
	private String antibiotico;
	private String prescritoMedico;
	private String factorRiesgo;
	private String otroFactorRiesgo;
	private String otraPersonaEnferma;
	private Integer cuantasPersonas;
	private String trasladoHospital;
	private String cualHospital;
	private String hospitalizado;
	private Date fechaAlta;
	//private Date frr;
	//private Date fdr;
	//private Date fsn;
	//private Date ftos;


	public String getCodigoCasoSintoma() {
		return codigoCasoSintoma;
	}

	public void setCodigoCasoSintoma(String codigoCasoSintoma) {
		this.codigoCasoSintoma = codigoCasoSintoma;
	}

	public VisitaSeguimientoCasoCovid19 getCodigoCasoVisita() {
		return codigoCasoVisita;
	}

	public void setCodigoCasoVisita(VisitaSeguimientoCasoCovid19 codigoCasoVisita) {
		this.codigoCasoVisita = codigoCasoVisita;
	}

	public Date getFechaSintomas() {
		return fechaSintomas;
	}

	public void setFechaSintomas(Date fechaSintomas) {
		this.fechaSintomas = fechaSintomas;
	}

	public String getFiebre() {
		return fiebre;
	}

	public void setFiebre(String fiebre) {
		this.fiebre = fiebre;
	}

	public String getFiebreIntensidad() {
		return fiebreIntensidad;
	}

	public void setFiebreIntensidad(String fiebreIntensidad) {
		this.fiebreIntensidad = fiebreIntensidad;
	}

	/*public Date getFif() {
		return fif;
	}

	public void setFif(Date fif) {
		this.fif = fif;
	}*/

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

	public String getDolorCabezaIntensidad() {
		return dolorCabezaIntensidad;
	}

	public void setDolorCabezaIntensidad(String dolorCabezaIntensidad) {
		this.dolorCabezaIntensidad = dolorCabezaIntensidad;
	}

	public String getDolorArticular() {
		return dolorArticular;
	}

	public void setDolorArticular(String dolorArticular) {
		this.dolorArticular = dolorArticular;
	}

	public String getDolorArticularIntensidad() {
		return dolorArticularIntensidad;
	}

	public void setDolorArticularIntensidad(String dolorArticularIntensidad) {
		this.dolorArticularIntensidad = dolorArticularIntensidad;
	}

	public String getDolorMuscular() {
		return dolorMuscular;
	}

	public void setDolorMuscular(String dolorMuscular) {
		this.dolorMuscular = dolorMuscular;
	}

	public String getDolorMuscularIntensidad() {
		return dolorMuscularIntensidad;
	}

	public void setDolorMuscularIntensidad(String dolorMuscularIntensidad) {
		this.dolorMuscularIntensidad = dolorMuscularIntensidad;
	}

	public String getDificultadRespiratoria() {
		return dificultadRespiratoria;
	}

	public void setDificultadRespiratoria(String dificultadRespiratoria) {
		this.dificultadRespiratoria = dificultadRespiratoria;
	}

	public String getSecrecionNasal() {
		return secrecionNasal;
	}

	public void setSecrecionNasal(String secrecionNasal) {
		this.secrecionNasal = secrecionNasal;
	}

	public String getSecrecionNasalIntensidad() {
		return secrecionNasalIntensidad;
	}

	public void setSecrecionNasalIntensidad(String secrecionNasalIntensidad) {
		this.secrecionNasalIntensidad = secrecionNasalIntensidad;
	}

	public String getTos() {
		return tos;
	}

	public void setTos(String tos) {
		this.tos = tos;
	}

	public String getTosIntensidad() {
		return tosIntensidad;
	}

	public void setTosIntensidad(String tosIntensidad) {
		this.tosIntensidad = tosIntensidad;
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

	public String getDolorGargantaIntensidad() {
		return dolorGargantaIntensidad;
	}

	public void setDolorGargantaIntensidad(String dolorGargantaIntensidad) {
		this.dolorGargantaIntensidad = dolorGargantaIntensidad;
	}

	public String getPicorGarganta() {
		return picorGarganta;
	}

	public void setPicorGarganta(String picorGarganta) {
		this.picorGarganta = picorGarganta;
	}

	public String getCongestionNasal() {
		return congestionNasal;
	}

	public void setCongestionNasal(String congestionNasal) {
		this.congestionNasal = congestionNasal;
	}

	public String getRash() {
		return rash;
	}

	public void setRash(String rash) {
		this.rash = rash;
	}

	public String getUrticaria() {
		return urticaria;
	}

	public void setUrticaria(String urticaria) {
		this.urticaria = urticaria;
	}

	public String getConjuntivitis() {
		return conjuntivitis;
	}

	public void setConjuntivitis(String conjuntivitis) {
		this.conjuntivitis = conjuntivitis;
	}

	public String getExpectoracion() {
		return expectoracion;
	}

	public void setExpectoracion(String expectoracion) {
		this.expectoracion = expectoracion;
	}

	public String getDiarrea() {
		return diarrea;
	}

	public void setDiarrea(String diarrea) {
		this.diarrea = diarrea;
	}

	public String getVomito() {
		return vomito;
	}

	public void setVomito(String vomito) {
		this.vomito = vomito;
	}

	public String getFatiga() {
		return fatiga;
	}

	public void setFatiga(String fatiga) {
		this.fatiga = fatiga;
	}

	public String getRespiracionRuidosa() {
		return respiracionRuidosa;
	}

	public void setRespiracionRuidosa(String respiracionRuidosa) {
		this.respiracionRuidosa = respiracionRuidosa;
	}

	public String getRespiracionRapida() {
		return respiracionRapida;
	}

	public void setRespiracionRapida(String respiracionRapida) {
		this.respiracionRapida = respiracionRapida;
	}

	public String getPerdidaOlfato() {
		return perdidaOlfato;
	}

	public void setPerdidaOlfato(String perdidaOlfato) {
		this.perdidaOlfato = perdidaOlfato;
	}

	public String getPerdidaGusto() {
		return perdidaGusto;
	}

	public void setPerdidaGusto(String perdidaGusto) {
		this.perdidaGusto = perdidaGusto;
	}

	public String getDesmayos() {
		return desmayos;
	}

	public void setDesmayos(String desmayos) {
		this.desmayos = desmayos;
	}

	public String getSensacionPechoApretado() {
		return sensacionPechoApretado;
	}

	public void setSensacionPechoApretado(String sensacionPechoApretado) {
		this.sensacionPechoApretado = sensacionPechoApretado;
	}

	public String getDolorPecho() {
		return dolorPecho;
	}

	public void setDolorPecho(String dolorPecho) {
		this.dolorPecho = dolorPecho;
	}

	public String getSensacionFaltaAire() {
		return sensacionFaltaAire;
	}

	public void setSensacionFaltaAire(String sensacionFaltaAire) {
		this.sensacionFaltaAire = sensacionFaltaAire;
	}

	public String getQuedoCama() {
		return quedoCama;
	}

	public void setQuedoCama(String quedoCama) {
		this.quedoCama = quedoCama;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public Date getFechaInicioTx() {
		return fechaInicioTx;
	}

	public void setFechaInicioTx(Date fechaInicioTx) {
		this.fechaInicioTx = fechaInicioTx;
	}

	public String getCualMedicamento() {
		return cualMedicamento;
	}

	public void setCualMedicamento(String cualMedicamento) {
		this.cualMedicamento = cualMedicamento;
	}

	public String getOtroMedicamento() {
		return otroMedicamento;
	}

	public void setOtroMedicamento(String otroMedicamento) {
		this.otroMedicamento = otroMedicamento;
	}

	public String getAntibiotico() {
		return antibiotico;
	}

	public void setAntibiotico(String antibiotico) {
		this.antibiotico = antibiotico;
	}

	public String getPrescritoMedico() {
		return prescritoMedico;
	}

	public void setPrescritoMedico(String prescritoMedico) {
		this.prescritoMedico = prescritoMedico;
	}

	public String getFactorRiesgo() {
		return factorRiesgo;
	}

	public void setFactorRiesgo(String factorRiesgo) {
		this.factorRiesgo = factorRiesgo;
	}

	public String getOtroFactorRiesgo() {
		return otroFactorRiesgo;
	}

	public void setOtroFactorRiesgo(String otroFactorRiesgo) {
		this.otroFactorRiesgo = otroFactorRiesgo;
	}

	public String getOtraPersonaEnferma() {
		return otraPersonaEnferma;
	}

	public void setOtraPersonaEnferma(String otraPersonaEnferma) {
		this.otraPersonaEnferma = otraPersonaEnferma;
	}

	public Integer getCuantasPersonas() {
		return cuantasPersonas;
	}

	public void setCuantasPersonas(Integer cuantasPersonas) {
		this.cuantasPersonas = cuantasPersonas;
	}

	public String getTrasladoHospital() {
		return trasladoHospital;
	}

	public void setTrasladoHospital(String trasladoHospital) {
		this.trasladoHospital = trasladoHospital;
	}

	public String getCualHospital() {
		return cualHospital;
	}

	public void setCualHospital(String cualHospital) {
		this.cualHospital = cualHospital;
	}

	public String getHospitalizado() {
		return hospitalizado;
	}

	public void setHospitalizado(String hospitalizado) {
		this.hospitalizado = hospitalizado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Override
	public String toString(){
		return codigoCasoVisita.getCodigoParticipanteCaso().getParticipante().getCodigo() + "-" + fechaSintomas;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SintomasVisitaCasoCovid19)) return false;

        SintomasVisitaCasoCovid19 that = (SintomasVisitaCasoCovid19) o;

        if (!codigoCasoSintoma.equals(that.codigoCasoSintoma)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoCasoSintoma.hashCode();
        result = 31 * result + codigoCasoVisita.getCodigoParticipanteCaso().getParticipante().hashCode();
        return result;
    }

}
