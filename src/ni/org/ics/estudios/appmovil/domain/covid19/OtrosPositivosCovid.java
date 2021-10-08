package ni.org.ics.estudios.appmovil.domain.covid19;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ICS on 23/11/2020.
 */

public class OtrosPositivosCovid extends BaseMetaData implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long codigo;
    private CandidatoTransmisionCovid19 candidatoTransmisionCovid19;
    private  Integer codigo_participante;
    private String casaCHF;
    private Date fis;
    private Date fif;
    private String positivoPor;
    private String estActuales;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public CandidatoTransmisionCovid19 getCandidatoTransmisionCovid19() {
        return candidatoTransmisionCovid19;
    }

    public void setCandidatoTransmisionCovid19(CandidatoTransmisionCovid19 candidatoTransmisionCovid19) {
        this.candidatoTransmisionCovid19 = candidatoTransmisionCovid19;
    }

    public Integer getCodigo_participante() {
        return codigo_participante;
    }

    public void setCodigo_participante(Integer codigo_participante) {
        this.codigo_participante = codigo_participante;
    }

    public String getCasaCHF() {
        return casaCHF;
    }

    public void setCasaCHF(String casaCHF) {
        this.casaCHF = casaCHF;
    }

    public Date getFis() {
        return fis;
    }

    public void setFis(Date fis) {
        this.fis = fis;
    }

    public Date getFif() {
        return fif;
    }

    public void setFif(Date fif) {
        this.fif = fif;
    }

    public String getPositivoPor() {
        return positivoPor;
    }

    public void setPositivoPor(String positivoPor) {
        this.positivoPor = positivoPor;
    }

    public String getEstActuales() {
        return estActuales;
    }

    public void setEstActuales(String estActuales) {
        this.estActuales = estActuales;
    }

}
