package ni.org.ics.estudios.appmovil.domain.encuestas;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */
public class Ventana extends AreaAmbiente {

    private AreaAmbiente areaAmbiente;
    private Double alto;
    private char abierta;

    public Double getAlto() {
        return alto;
    }

    public void setAlto(Double alto) {
        this.alto = alto;
    }

    public char getAbierta() {
        return abierta;
    }

    public void setAbierta(char abierta) {
        this.abierta = abierta;
    }

    public AreaAmbiente getAreaAmbiente() {
        return areaAmbiente;
    }

    public void setAreaAmbiente(AreaAmbiente areaAmbiente) {
        this.areaAmbiente = areaAmbiente;
    }
}
