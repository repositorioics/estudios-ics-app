package ni.org.ics.estudios.appmovil.domain.influenzauo1;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;

import java.util.Date;

/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */
public class ParticipanteCasoUO1 extends BaseMetaData implements Comparable<ParticipanteCasoUO1>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCasoParticipante;
	private Participante participante;
	private String activo;
	private String positivoPor;
	private Date fif;
	private Date fechaIngreso;
	private Date fechaDesactivacion;
	private Date fis;
	
	public String getCodigoCasoParticipante() {
		return codigoCasoParticipante;
	}

	public void setCodigoCasoParticipante(String codigoCasoParticipante) {
		this.codigoCasoParticipante = codigoCasoParticipante;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
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

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaDesactivacion() {
		return fechaDesactivacion;
	}

	public void setFechaDesactivacion(Date fechaDesactivacion) {
		this.fechaDesactivacion = fechaDesactivacion;
	}

	public Date getFis() {
		return fis;
	}

	public void setFis(Date fis) {
		this.fis = fis;
	}

	@Override
	public String toString(){
		return participante.getCodigo().toString();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipanteCasoUO1)) return false;

        ParticipanteCasoUO1 that = (ParticipanteCasoUO1) o;

        if (!codigoCasoParticipante.equals(that.codigoCasoParticipante)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoCasoParticipante.hashCode();
        result = 31 * result + participante.hashCode();
        return result;
    }

	@Override
	public int compareTo(ParticipanteCasoUO1 o) {
		return Integer.compare(this.participante.getCodigo(), o.participante.getCodigo());
	}
}
