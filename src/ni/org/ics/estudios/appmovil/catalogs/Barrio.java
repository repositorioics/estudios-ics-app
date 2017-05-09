package ni.org.ics.estudios.appmovil.catalogs;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;


/**
 * Simple objeto de dominio que representa un barrio
 * 
 * @author William Aviles
 **/

public class Barrio extends BaseMetaData {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
        if (!(o instanceof Barrio)) return false;

        Barrio barrio = (Barrio) o;

        return (!codigo.equals(barrio.codigo));
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
