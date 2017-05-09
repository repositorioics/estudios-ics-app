package ni.org.ics.estudios.appmovil.catalogs;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

/**
 * Simple objeto de dominio que representa un operadora telefónica
 * 
 * @author Miguel Salinas
 **/
public class Operadora extends BaseMetaData {
	
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
        if (!(o instanceof Operadora)) return false;

        Operadora operadora = (Operadora) o;

        return (!codigo.equals(operadora.codigo));
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
