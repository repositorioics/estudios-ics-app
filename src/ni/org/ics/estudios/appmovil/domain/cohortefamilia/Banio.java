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
    
    
	public Banio() {
	}

	public Banio(String codigo, Double largo, Double ancho,
			Double totalM2, Integer numVentanas, CasaCohorteFamilia casa,
			String tipo, String conVentana) {
		super(codigo, largo, ancho, totalM2, numVentanas, casa, tipo, null);
		this.conVentana = conVentana;
	}

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
