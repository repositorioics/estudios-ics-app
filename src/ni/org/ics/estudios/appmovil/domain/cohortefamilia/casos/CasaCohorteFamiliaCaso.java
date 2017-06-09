package ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos;

import java.util.Date;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;


/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */
public class CasaCohorteFamiliaCaso extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCaso;
	private CasaCohorteFamilia casa;
	private Date fechaInicio;
	private String inactiva;
	private Date fechaInactiva;
	
    
    public String getCodigoCaso() {
		return codigoCaso;
	}

	public void setCodigoCaso(String codigoCaso) {
		this.codigoCaso = codigoCaso;
	}

	public CasaCohorteFamilia getCasa() {
		return casa;
	}

	public void setCasa(CasaCohorteFamilia casa) {
		this.casa = casa;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public String getInactiva() {
		return inactiva;
	}

	public void setInactiva(String inactiva) {
		this.inactiva = inactiva;
	}

	public Date getFechaInactiva() {
		return fechaInactiva;
	}

	public void setFechaInactiva(Date fechaInactiva) {
		this.fechaInactiva = fechaInactiva;
	}

	@Override
	public String toString(){
		return casa.getCodigoCHF() + "-" + fechaInicio;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasaCohorteFamiliaCaso)) return false;

        CasaCohorteFamiliaCaso that = (CasaCohorteFamiliaCaso) o;

        if (!codigoCaso.equals(that.codigoCaso)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoCaso.hashCode();
        result = 31 * result + casa.hashCode();
        return result;
    }

}
