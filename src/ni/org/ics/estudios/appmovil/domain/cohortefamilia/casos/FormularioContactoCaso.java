package ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos;

import java.util.Date;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;


/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */

public class FormularioContactoCaso extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCasoContacto;
	private ParticipanteCohorteFamiliaCaso codigoParticipanteCaso;
	private Date fechaVisita;
	private ParticipanteCohorteFamilia partContacto;
	private String tiempoInteraccion;
	private String tipoInteraccion;
    
	public String getCodigoCasoContacto() {
		return codigoCasoContacto;
	}

	public void setCodigoCasoContacto(String codigoCasoContacto) {
		this.codigoCasoContacto = codigoCasoContacto;
	}

	public ParticipanteCohorteFamiliaCaso getCodigoParticipanteCaso() {
		return codigoParticipanteCaso;
	}

	public void setCodigoParticipanteCaso(ParticipanteCohorteFamiliaCaso codigoParticipanteCaso) {
		this.codigoParticipanteCaso = codigoParticipanteCaso;
	}
	
	public Date getFechaVisita() {
		return fechaVisita;
	}

	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	public ParticipanteCohorteFamilia getPartContacto() {
		return partContacto;
	}

	public void setPartContacto(ParticipanteCohorteFamilia partContacto) {
		this.partContacto = partContacto;
	}

	public String getTiempoInteraccion() {
		return tiempoInteraccion;
	}

	public void setTiempoInteraccion(String tiempoInteraccion) {
		this.tiempoInteraccion = tiempoInteraccion;
	}

	public String getTipoInteraccion() {
		return tipoInteraccion;
	}

	public void setTipoInteraccion(String tipoInteraccion) {
		this.tipoInteraccion = tipoInteraccion;
	}

	@Override
	public String toString(){
		return codigoParticipanteCaso.getCodigoCaso().getCasa().getCodigoCHF() + "-" + codigoParticipanteCaso.getParticipante().getParticipante().getCodigo() + "-" + codigoParticipanteCaso.getCodigoCaso().getFechaInicio();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormularioContactoCaso)) return false;

        FormularioContactoCaso that = (FormularioContactoCaso) o;

        if (!codigoCasoContacto.equals(that.codigoCasoContacto)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoCasoContacto.hashCode();
        result = 31 * result + codigoParticipanteCaso.getParticipante().hashCode();
        return result;
    }
}
