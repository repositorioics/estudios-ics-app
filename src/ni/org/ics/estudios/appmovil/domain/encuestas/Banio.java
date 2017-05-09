package ni.org.ics.estudios.appmovil.domain.encuestas;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */

public class Banio extends  AreaAmbiente {

    private Habitacion habitacion;
    private char conVentana;

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public char getConVentana() {
        return conVentana;
    }

    public void setConVentana(char conVentana) {
        this.conVentana = conVentana;
    }
}
