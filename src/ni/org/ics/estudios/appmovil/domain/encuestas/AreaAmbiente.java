package ni.org.ics.estudios.appmovil.domain.encuestas;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Casa;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */
public class AreaAmbiente extends BaseMetaData {

    private String codigo;
    private Double largo;
    private Double ancho;
    private Double totalM2;
    private Integer numVentanas;
    private Casa casa;

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

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }
}
