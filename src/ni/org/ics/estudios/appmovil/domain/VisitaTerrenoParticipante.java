package ni.org.ics.estudios.appmovil.domain;

import java.util.Date;

/**
 * Created by FIRSTICT on 08/09/2018.
 * V1.0
 */
public class VisitaTerrenoParticipante extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoVisita;
	private Participante participante;
	private Date fechaVisita;
	private String visitaExitosa;
	private String razonVisitaNoExitosa;
    private String otraRazonVisitaNoExitosa;
    private String dejoCarta;
    private String personaDejoCarta;
    private String relFamPersonaDejoCarta;
    private String personaCasa;
    private String relacionFamPersonaCasa;
    private String otraRelacionPersonaCasa;
    private String telefonoPersonaCasa;
    private String estudio;

    public String getCodigoVisita() {
        return codigoVisita;
    }

    public void setCodigoVisita(String codigoVisita) {
        this.codigoVisita = codigoVisita;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public String getVisitaExitosa() {
        return visitaExitosa;
    }

    public void setVisitaExitosa(String visitaExitosa) {
        this.visitaExitosa = visitaExitosa;
    }

    public String getRazonVisitaNoExitosa() {
        return razonVisitaNoExitosa;
    }

    public void setRazonVisitaNoExitosa(String razonVisitaNoExitosa) {
        this.razonVisitaNoExitosa = razonVisitaNoExitosa;
    }

    public String getOtraRazonVisitaNoExitosa() {
        return otraRazonVisitaNoExitosa;
    }

    public void setOtraRazonVisitaNoExitosa(String otraRazonVisitaNoExitosa) {
        this.otraRazonVisitaNoExitosa = otraRazonVisitaNoExitosa;
    }

    public String getDejoCarta() {
        return dejoCarta;
    }

    public void setDejoCarta(String dejoCarta) {
        this.dejoCarta = dejoCarta;
    }

    public String getPersonaDejoCarta() {
        return personaDejoCarta;
    }

    public void setPersonaDejoCarta(String personaDejoCarta) {
        this.personaDejoCarta = personaDejoCarta;
    }

    public String getRelFamPersonaDejoCarta() {
        return relFamPersonaDejoCarta;
    }

    public void setRelFamPersonaDejoCarta(String relFamPersonaDejoCarta) {
        this.relFamPersonaDejoCarta = relFamPersonaDejoCarta;
    }

    public String getPersonaCasa() {
        return personaCasa;
    }

    public void setPersonaCasa(String personaCasa) {
        this.personaCasa = personaCasa;
    }

    public String getRelacionFamPersonaCasa() {
        return relacionFamPersonaCasa;
    }

    public void setRelacionFamPersonaCasa(String relacionFamPersonaCasa) {
        this.relacionFamPersonaCasa = relacionFamPersonaCasa;
    }

    public String getOtraRelacionPersonaCasa() {
        return otraRelacionPersonaCasa;
    }

    public void setOtraRelacionPersonaCasa(String otraRelacionPersonaCasa) {
        this.otraRelacionPersonaCasa = otraRelacionPersonaCasa;
    }

    public String getTelefonoPersonaCasa() {
        return telefonoPersonaCasa;
    }

    public void setTelefonoPersonaCasa(String telefonoPersonaCasa) {
        this.telefonoPersonaCasa = telefonoPersonaCasa;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    @Override
    public String toString() {
        return "'" + codigoVisita + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitaTerrenoParticipante)) return false;

        VisitaTerrenoParticipante visita = (VisitaTerrenoParticipante) o;

        return (!codigoVisita.equals(visita.codigoVisita));
    }

    @Override
    public int hashCode() {
        return codigoVisita.hashCode();
    }
}
