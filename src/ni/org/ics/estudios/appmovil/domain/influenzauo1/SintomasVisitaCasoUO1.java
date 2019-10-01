package ni.org.ics.estudios.appmovil.domain.influenzauo1;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

import java.util.Date;

/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */
public class SintomasVisitaCasoUO1 extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoSintoma;
	private VisitaCasoUO1 codigoCasoVisita;
	private Date fechaSintomas;
	private String dia;
	private String consultaInicial;
	private String fiebre;
	private String fiebreIntensidad;
	private String tos;
	private String tosIntensidad;
	private String secrecionNasal;
	private String secrecionNasalIntensidad;
	private String dolorGarganta;
	private String dolorGargantaIntensidad;
	private String congestionNasal;
	private String dolorCabeza;
	private String dolorCabezaIntensidad;
	private String faltaApetito;
	private String dolorMuscular;
	private String dolorMuscularIntensidad;
	private String dolorArticular;
	private String dolorArticularIntensidad;
	private String dolorOido;
	private String respiracionRapida;
	private String dificultadRespiratoria;
	private String faltaEscuelta;
	private String quedoCama;

	public String getCodigoSintoma() {
		return codigoSintoma;
	}

	public void setCodigoSintoma(String codigoSintoma) {
		this.codigoSintoma = codigoSintoma;
	}

	public VisitaCasoUO1 getCodigoCasoVisita() {
		return codigoCasoVisita;
	}

	public void setCodigoCasoVisita(VisitaCasoUO1 codigoCasoVisita) {
		this.codigoCasoVisita = codigoCasoVisita;
	}

	public Date getFechaSintomas() {
		return fechaSintomas;
	}

	public void setFechaSintomas(Date fechaSintomas) {
		this.fechaSintomas = fechaSintomas;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getConsultaInicial() {
		return consultaInicial;
	}

	public void setConsultaInicial(String consultaInicial) {
		this.consultaInicial = consultaInicial;
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

	public String getCongestionNasal() {
		return congestionNasal;
	}

	public void setCongestionNasal(String congestionNasal) {
		this.congestionNasal = congestionNasal;
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

	public String getFaltaApetito() {
		return faltaApetito;
	}

	public void setFaltaApetito(String faltaApetito) {
		this.faltaApetito = faltaApetito;
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

	public String getDolorOido() {
		return dolorOido;
	}

	public void setDolorOido(String dolorOido) {
		this.dolorOido = dolorOido;
	}

	public String getRespiracionRapida() {
		return respiracionRapida;
	}

	public void setRespiracionRapida(String respiracionRapida) {
		this.respiracionRapida = respiracionRapida;
	}

	public String getDificultadRespiratoria() {
		return dificultadRespiratoria;
	}

	public void setDificultadRespiratoria(String dificultadRespiratoria) {
		this.dificultadRespiratoria = dificultadRespiratoria;
	}

	public String getFaltaEscuelta() {
		return faltaEscuelta;
	}

	public void setFaltaEscuelta(String faltaEscuelta) {
		this.faltaEscuelta = faltaEscuelta;
	}

	public String getQuedoCama() {
		return quedoCama;
	}

	public void setQuedoCama(String quedoCama) {
		this.quedoCama = quedoCama;
	}

	@Override
	public String toString(){
		return codigoCasoVisita.getParticipanteCasoUO1().getParticipante().getCasa().getCodigo() + "-" + codigoCasoVisita.getParticipanteCasoUO1().getParticipante().getCodigo() + "-" + codigoCasoVisita.getParticipanteCasoUO1().getFechaIngreso();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SintomasVisitaCasoUO1)) return false;

        SintomasVisitaCasoUO1 that = (SintomasVisitaCasoUO1) o;

        if (!codigoSintoma.equals(that.codigoSintoma)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoSintoma.hashCode();
        result = 31 * result + codigoCasoVisita.getParticipanteCasoUO1().getParticipante().hashCode();
        return result;
    }

}
