package ni.org.ics.estudios.appmovil.domain.cohortefamilia;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;


/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */

public class Cuarto extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	private CasaCohorteFamilia casa;
	private String codigoHabitacion;
    private Integer cantidadCamas;
    
    
    public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


    public CasaCohorteFamilia getCasa() {
        return casa;
    }

	public void setCasa(CasaCohorteFamilia casa) {
		this.casa = casa;
	}


    public String getCodigoHabitacion() {
        return codigoHabitacion;
    }

    public void setCodigoHabitacion(String codigoHabitacion) {
        this.codigoHabitacion = codigoHabitacion;
    }


    public Integer getCantidadCamas() {
        return cantidadCamas;
    }

    public void setCantidadCamas(Integer cantidadCamas) {
        this.cantidadCamas = cantidadCamas;
    }

    @Override
    public String toString() {
        return "Cuarto " + codigoHabitacion;
    }
}
