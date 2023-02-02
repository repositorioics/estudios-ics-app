package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;
/**
 * Objeto que representa la clave unica de una encuesta de participante
 *
 * @author Ing. Santiago Carballo
 **/

public class PerimetroAbdominalId implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer codigo;
    private Date fecha;

    public PerimetroAbdominalId() {

    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof PesoyTallaId))
            return false;
        PerimetroAbdominalId castOther = (PerimetroAbdominalId) other;
        return (this.getCodigo() == castOther.getCodigo())
                && (this.getFecha() == castOther.getFecha());
    }

    public int hashCode() {
        int result = 17;
        result = 37 * 3 + this.getCodigo();
        result = 37 * result + Integer.valueOf(this.getFecha().toString());
        return result;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return this.codigo.toString();
    }
}
