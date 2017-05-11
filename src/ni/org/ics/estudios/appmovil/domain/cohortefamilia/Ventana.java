package ni.org.ics.estudios.appmovil.domain.cohortefamilia;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */
public class Ventana extends AreaAmbiente {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AreaAmbiente areaAmbiente; //area a la que pertenece la ventana.. puede ser bano, sala, habitación, cocina o comedor
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
