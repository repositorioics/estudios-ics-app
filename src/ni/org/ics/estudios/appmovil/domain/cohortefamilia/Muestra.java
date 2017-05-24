package ni.org.ics.estudios.appmovil.domain.cohortefamilia;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

import java.io.Serializable;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class Muestra extends BaseMetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String tomaMxSn;
    private String codigoMx;
    private String hora;
    private String horaFin;
    private Double volumen;
    private String observacion;
    private String descOtraObservacion;
    private String numPinchazos;
    private String razonNoToma;
    private String descOtraRazonNoToma;
    private String tubo;
    private String tipoMuestra;
    private String proposito;
    private ParticipanteCohorteFamilia participanteCHF;
    private String realizaPaxgene;
    private String horaInicioPax;
    private String horaFinPax;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTomaMxSn() {
        return tomaMxSn;
    }

    public void setTomaMxSn(String tomaMxSn) {
        this.tomaMxSn = tomaMxSn;
    }

    public String getCodigoMx() {
        return codigoMx;
    }

    public void setCodigoMx(String codigoMx) {
        this.codigoMx = codigoMx;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Double getVolumen() {
        return volumen;
    }

    public void setVolumen(Double volumen) {
        this.volumen = volumen;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDescOtraObservacion() {
        return descOtraObservacion;
    }

    public void setDescOtraObservacion(String descOtraObservacion) {
        this.descOtraObservacion = descOtraObservacion;
    }

    public String getNumPinchazos() {
        return numPinchazos;
    }

    public void setNumPinchazos(String numPinchazos) {
        this.numPinchazos = numPinchazos;
    }

    public String getRazonNoToma() {
        return razonNoToma;
    }

    public void setRazonNoToma(String razonNoToma) {
        this.razonNoToma = razonNoToma;
    }

    public String getDescOtraRazonNoToma() {
        return descOtraRazonNoToma;
    }

    public void setDescOtraRazonNoToma(String descOtraRazonNoToma) {
        this.descOtraRazonNoToma = descOtraRazonNoToma;
    }

    public String getTubo() {
        return tubo;
    }

    public void setTubo(String tubo) {
        this.tubo = tubo;
    }

    public String getTipoMuestra() {
        return tipoMuestra;
    }

    public void setTipoMuestra(String tipoMuestra) {
        this.tipoMuestra = tipoMuestra;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public ParticipanteCohorteFamilia getParticipanteCHF() {
        return participanteCHF;
    }

    public void setParticipanteCHF(ParticipanteCohorteFamilia participanteCHF) {
        this.participanteCHF = participanteCHF;
    }

    public String getRealizaPaxgene() {
        return realizaPaxgene;
    }

    public void setRealizaPaxgene(String realizaPaxgene) {
        this.realizaPaxgene = realizaPaxgene;
    }

    public String getHoraInicioPax() {
        return horaInicioPax;
    }

    public void setHoraInicioPax(String horaInicioPax) {
        this.horaInicioPax = horaInicioPax;
    }

    public String getHoraFinPax() {
        return horaFinPax;
    }

    public void setHoraFinPax(String horaFinPax) {
        this.horaFinPax = horaFinPax;
    }

    @Override
    public String toString() {
        return "Muestra{" + codigo +
                ", '" + codigoMx + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Muestra)) return false;

        Muestra muestra = (Muestra) o;

        return  (!codigo.equals(muestra.codigo));
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
