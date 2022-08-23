package ni.org.ics.estudios.appmovil.entomologia.domain;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by miguel on 15/8/2022.
 */
public class CuestionarioHogarPoblacion extends BaseMetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigoPoblacion;
    private String codigoEncuesta;
    private String codificado;
    private String edad;
    private String sexo;

    public String getCodigoPoblacion() {
        return codigoPoblacion;
    }

    public void setCodigoPoblacion(String codigoPoblacion) {
        this.codigoPoblacion = codigoPoblacion;
    }

    public String getCodigoEncuesta() {
        return codigoEncuesta;
    }

    public void setCodigoEncuesta(String codigoEncuesta) {
        this.codigoEncuesta = codigoEncuesta;
    }

    public String getCodificado() {
        return codificado;
    }

    public void setCodificado(String codificado) {
        this.codificado = codificado;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "CuestionarioHogarPoblacion{" +
                "codigoPoblacion='" + codigoPoblacion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuestionarioHogarPoblacion)) return false;

        CuestionarioHogarPoblacion that = (CuestionarioHogarPoblacion) o;

        if (!codigoPoblacion.equals(that.codigoPoblacion)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return codigoPoblacion.hashCode();
    }
}
