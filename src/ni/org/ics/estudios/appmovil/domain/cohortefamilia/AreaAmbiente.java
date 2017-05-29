package ni.org.ics.estudios.appmovil.domain.cohortefamilia;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

import java.io.Serializable;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */
public class AreaAmbiente extends BaseMetaData implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
    private Double largo;
    private Double ancho;
    private Double totalM2;
    private Integer numVentanas;
    private CasaCohorteFamilia casa;
    private String tipo;
    
    

    public AreaAmbiente() {
	}

	public AreaAmbiente(String codigo, Double largo, Double ancho,
			Double totalM2, Integer numVentanas, CasaCohorteFamilia casa,
			String tipo) {
		super();
		this.codigo = codigo;
		this.largo = largo;
		this.ancho = ancho;
		this.totalM2 = totalM2;
		this.numVentanas = numVentanas;
		this.casa = casa;
		this.tipo = tipo;
	}

	public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getLargo() {
        return largo;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getTotalM2() {
        return totalM2;
    }

    public void setTotalM2(Double totalM2) {
        this.totalM2 = totalM2;
    }

    public Integer getNumVentanas() {
        return numVentanas;
    }

    public void setNumVentanas(Integer numVentanas) {
        this.numVentanas = numVentanas;
    }

    public CasaCohorteFamilia getCasa() {
        return casa;
    }

    public void setCasa(CasaCohorteFamilia casa) {
        this.casa = casa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
