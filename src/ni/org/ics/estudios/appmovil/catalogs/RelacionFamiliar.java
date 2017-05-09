package ni.org.ics.estudios.appmovil.catalogs;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

/**
 * Created by FIRSTICT on 4/28/2017.
 * V1.0
 */
public class RelacionFamiliar extends BaseMetaData {

    private Integer codigo;
    private String nombre;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "'" + codigo + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelacionFamiliar)) return false;

        RelacionFamiliar relacionFamiliar = (RelacionFamiliar) o;

        return (!codigo.equals(relacionFamiliar.codigo));
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

}
