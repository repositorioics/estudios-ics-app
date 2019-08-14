package ni.org.ics.estudios.appmovil.domain.cohortefamilia;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;

import java.io.Serializable;

/**
 * Created by Miguel Salinas on 29/07/2019.
 * V1.0
 */
public class MuestraSuperficie extends BaseMetaData implements Serializable {

    private String codigo;
    private String tipoMuestra;
    private String otraSuperficie;
    private String codigoMx;
    private CasaCohorteFamilia casaChf;
    private ParticipanteCohorteFamilia participanteChf;
    private String visita;
    private String caso;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoMuestra() {
        return tipoMuestra;
    }

    public void setTipoMuestra(String tipoMuestra) {
        this.tipoMuestra = tipoMuestra;
    }

    public String getOtraSuperficie() {
        return otraSuperficie;
    }

    public void setOtraSuperficie(String otraSuperficie) {
        this.otraSuperficie = otraSuperficie;
    }

    public String getCodigoMx() {
        return codigoMx;
    }

    public void setCodigoMx(String codigoMx) {
        this.codigoMx = codigoMx;
    }

    public ParticipanteCohorteFamilia getParticipanteChf() {
        return participanteChf;
    }

    public void setParticipanteChf(ParticipanteCohorteFamilia participanteChf) {
        this.participanteChf = participanteChf;
    }

    public CasaCohorteFamilia getCasaChf() {
        return casaChf;
    }

    public void setCasaChf(CasaCohorteFamilia casaChf) {
        this.casaChf = casaChf;
    }

    public String getVisita() {
        return visita;
    }

    public void setVisita(String visita) {
        this.visita = visita;
    }

    public String getCaso() {
        return caso;
    }

    public void setCaso(String caso) {
        this.caso = caso;
    }
}
