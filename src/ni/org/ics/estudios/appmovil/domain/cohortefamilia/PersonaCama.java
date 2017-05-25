package ni.org.ics.estudios.appmovil.domain.cohortefamilia;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */
public class PersonaCama extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoPersona;
    private String estaEnEstudio; //0 No, 1 Si
    private Participante participante; //si es participante
    private String sexo;  //si no participa
    private Integer edad;   //si no participa
    private Cama cama;

    public String getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(String codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    public String getEstaEnEstudio() {
        return estaEnEstudio;
    }

    public void setEstaEnEstudio(String estaEnEstudio) {
        this.estaEnEstudio = estaEnEstudio;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Cama getCama() {
        return cama;
    }

    public void setCama(Cama cama) {
        this.cama = cama;
    }
}
