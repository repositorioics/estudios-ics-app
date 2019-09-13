package ni.org.ics.estudios.appmovil.domain.influenzauo1;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

import java.util.Date;

public class VisitaCasoUO1 extends BaseMetaData {
    private String codigoCasoVisita;
    private ParticipanteCasoUO1 participanteCasoUO1;
    private Date fechaVisita;
    private String visita;
    private String visitaExitosa;
    private String razonVisitaFallida;
    private String otraRazon;
    private String positivoPor;
    private Date fif;
    private String vacunaFlu3Semanas;
    private Date fechaVacuna;
    private String lugar;

    public String getCodigoCasoVisita() {
        return codigoCasoVisita;
    }

    public void setCodigoCasoVisita(String codigoCasoVisita) {
        this.codigoCasoVisita = codigoCasoVisita;
    }

    public ParticipanteCasoUO1 getParticipanteCasoUO1() {
        return participanteCasoUO1;
    }

    public void setParticipanteCasoUO1(ParticipanteCasoUO1 participanteCasoUO1) {
        this.participanteCasoUO1 = participanteCasoUO1;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public String getVisita() {
        return visita;
    }

    public void setVisita(String visita) {
        this.visita = visita;
    }

    public String getVisitaExitosa() {
        return visitaExitosa;
    }

    public void setVisitaExitosa(String visitaExitosa) {
        this.visitaExitosa = visitaExitosa;
    }

    public String getRazonVisitaFallida() {
        return razonVisitaFallida;
    }

    public void setRazonVisitaFallida(String razonVisitaFallida) {
        this.razonVisitaFallida = razonVisitaFallida;
    }

    public String getOtraRazon() {
        return otraRazon;
    }

    public void setOtraRazon(String otraRazon) {
        this.otraRazon = otraRazon;
    }

    public String getPositivoPor() {
        return positivoPor;
    }

    public void setPositivoPor(String positivoPor) {
        this.positivoPor = positivoPor;
    }

    public Date getFif() {
        return fif;
    }

    public void setFif(Date fif) {
        this.fif = fif;
    }

    public String getVacunaFlu3Semanas() {
        return vacunaFlu3Semanas;
    }

    public void setVacunaFlu3Semanas(String vacunaFlu3Semanas) {
        this.vacunaFlu3Semanas = vacunaFlu3Semanas;
    }

    public Date getFechaVacuna() {
        return fechaVacuna;
    }

    public void setFechaVacuna(Date fechaVacuna) {
        this.fechaVacuna = fechaVacuna;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
