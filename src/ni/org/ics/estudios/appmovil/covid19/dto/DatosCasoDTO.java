package ni.org.ics.estudios.appmovil.covid19.dto;

import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;

import java.util.Date;

public class DatosCasoDTO {
    private ParticipanteCasoCovid19 participante;
    private String tipoCaso;
    public ParticipanteCasoCovid19 getParticipante() {
        return participante;
    }

    public void setParticipante(ParticipanteCasoCovid19 participante) {
        this.participante = participante;
    }

    public String getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(String tipoCaso) {
        this.tipoCaso = tipoCaso;
    }
}
