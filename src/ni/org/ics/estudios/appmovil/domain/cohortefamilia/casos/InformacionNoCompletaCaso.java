package ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;


/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */
public class InformacionNoCompletaCaso extends BaseMetaData  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoNoDataVisita;
	private VisitaSeguimientoCaso codigoVisitaCaso;
	private String razonNoCompletaInformacion;
	private String otraRazon;
	

	public String getCodigoNoDataVisita() {
		return codigoNoDataVisita;
	}

	public void setCodigoNoDataVisita(String codigoNoDataVisita) {
		this.codigoNoDataVisita = codigoNoDataVisita;
	}

	public VisitaSeguimientoCaso getCodigoVisitaCaso() {
		return codigoVisitaCaso;
	}

	public void setCodigoVisitaCaso(VisitaSeguimientoCaso codigoVisitaCaso) {
		this.codigoVisitaCaso = codigoVisitaCaso;
	}
	
	public String getRazonNoCompletaInformacion() {
		return razonNoCompletaInformacion;
	}

	public void setRazonNoCompletaInformacion(String razonNoCompletaInformacion) {
		this.razonNoCompletaInformacion = razonNoCompletaInformacion;
	}

	public String getOtraRazon() {
		return otraRazon;
	}

	public void setOtraRazon(String otraRazon) {
		this.otraRazon = otraRazon;
	}

	@Override
	public String toString(){
		return codigoVisitaCaso.getFechaVisita().toString();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InformacionNoCompletaCaso)) return false;

        InformacionNoCompletaCaso that = (InformacionNoCompletaCaso) o;

        if (!codigoNoDataVisita.equals(that.codigoNoDataVisita)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoNoDataVisita.hashCode();
        result = 31 * result + codigoVisitaCaso.hashCode();
        return result;
    }
}
