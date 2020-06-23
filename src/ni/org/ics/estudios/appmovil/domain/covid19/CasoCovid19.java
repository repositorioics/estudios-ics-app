package ni.org.ics.estudios.appmovil.domain.covid19;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;

import java.util.Date;

/**
 * Created by Miguel Salinas on 01/06/2020.
 * V1.0
 */
public class CasoCovid19 extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCaso;
	private CasaCohorteFamilia casa;
	private Date fechaIngreso;
	private String inactivo;
	private Date fechaInactivo;

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

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getInactivo() {
		return inactivo;
	}

	public void setInactivo(String inactivo) {
		this.inactivo = inactivo;
	}

	public Date getFechaInactivo() {
		return fechaInactivo;
	}

	public void setFechaInactivo(Date fechaInactivo) {
		this.fechaInactivo = fechaInactivo;
	}

	@Override
	public String toString(){
		return codigoCaso + "-" + fechaIngreso;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasoCovid19)) return false;

        CasoCovid19 that = (CasoCovid19) o;

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
