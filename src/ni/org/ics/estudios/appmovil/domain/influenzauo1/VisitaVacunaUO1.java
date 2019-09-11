package ni.org.ics.estudios.appmovil.domain.influenzauo1;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;

import java.util.Date;

public class VisitaVacunaUO1 extends BaseMetaData {
    private String codigoVisita;
    private Participante participante;
    private Date fechaVisita;
    private String visita;
    private String visitaExitosa;
    private String razonVisitaFallida;
    private String otraRazon;
    private String vacuna;
    private Date fechaVacuna;
    private String tomaMxAntes;
    private String razonNoTomaMx;
    private String segundaDosis;
    private Date fechaSegundaDosis;
    private Date fechaReprogramacion;

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

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public Date getFechaVacuna() {
        return fechaVacuna;
    }

    public void setFechaVacuna(Date fechaVacuna) {
        this.fechaVacuna = fechaVacuna;
    }

    public String getTomaMxAntes() {
        return tomaMxAntes;
    }

    public void setTomaMxAntes(String tomaMxAntes) {
        this.tomaMxAntes = tomaMxAntes;
    }

    public String getRazonNoTomaMx() {
        return razonNoTomaMx;
    }

    public void setRazonNoTomaMx(String razonNoTomaMx) {
        this.razonNoTomaMx = razonNoTomaMx;
    }

    public String getSegundaDosis() {
        return segundaDosis;
    }

    public void setSegundaDosis(String segundaDosis) {
        this.segundaDosis = segundaDosis;
    }

    public Date getFechaSegundaDosis() {
        return fechaSegundaDosis;
    }

    public void setFechaSegundaDosis(Date fechaSegundaDosis) {
        this.fechaSegundaDosis = fechaSegundaDosis;
    }

    public Date getFechaReprogramacion() {
        return fechaReprogramacion;
    }

    public void setFechaReprogramacion(Date fechaReprogramacion) {
        this.fechaReprogramacion = fechaReprogramacion;
    }
}
