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
	private VisitaSeguimientoCaso codigoVisitaCaso;
	private ParticipanteCohorteFamilia partContacto;
	private Date fechaContacto;
	private String tiempoInteraccion;
	private String tipoInteraccion;
    
	public String getCodigoCasoContacto() {
		return codigoCasoContacto;
	}

	public void setCodigoCasoContacto(String codigoCasoContacto) {
		this.codigoCasoContacto = codigoCasoContacto;
	}


	public VisitaSeguimientoCaso getCodigoVisitaCaso() {
		return codigoVisitaCaso;
	}

	public void setCodigoVisitaCaso(VisitaSeguimientoCaso codigoVisitaCaso) {
		this.codigoVisitaCaso = codigoVisitaCaso;
	}

	public ParticipanteCohorteFamilia getPartContacto() {
		return partContacto;
	}

	public void setPartContacto(ParticipanteCohorteFamilia partContacto) {
		this.partContacto = partContacto;
	}
	

	public Date getFechaContacto() {
		return fechaContacto;
	}

	public void setFechaContacto(Date fechaContacto) {
		this.fechaContacto = fechaContacto;
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
		return codigoVisitaCaso.getCodigoParticipanteCaso().getCodigoCaso().getCasa().getCodigoCHF() + "-" + codigoVisitaCaso.getCodigoParticipanteCaso().getParticipante().getParticipante().getCodigo() + "-" + codigoVisitaCaso.getCodigoParticipanteCaso().getCodigoCaso().getFechaInicio();
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
        result = 31 * result + codigoVisitaCaso.getCodigoParticipanteCaso().getParticipante().hashCode();
        return result;
    }
}
