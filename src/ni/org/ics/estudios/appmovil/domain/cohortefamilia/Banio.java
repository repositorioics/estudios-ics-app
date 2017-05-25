package ni.org.ics.estudios.appmovil.domain.cohortefamilia;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */

public class Banio extends  AreaAmbiente {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AreaAmbiente areaAmbiente;
    private String conVentana;

    public AreaAmbiente getAreaAmbiente() {
        return areaAmbiente;
    }

    public void setAreaAmbiente(AreaAmbiente areaAmbiente) {
        this.areaAmbiente = areaAmbiente;
    }
    public String getConVentana() {
        return conVentana;
    }

    public void setConVentana(String conVentana) {
        this.conVentana = conVentana;
    }
}
