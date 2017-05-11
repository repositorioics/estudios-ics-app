package ni.org.ics.estudios.appmovil.domain.cohortefamilia;


import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */
public class Cama extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCama;
    private Habitacion habitacion;

    public String getCodigoCama() {
        return codigoCama;
    }

    public void setCodigoCama(String codigoCama) {
        this.codigoCama = codigoCama;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    @Override
    public String toString() {
        return "Cama{" + codigoCama + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cama)) return false;

        Cama cama = (Cama) o;

        return (!codigoCama.equals(cama.codigoCama));
    }

    @Override
    public int hashCode() {
        return codigoCama.hashCode();
    }
}
