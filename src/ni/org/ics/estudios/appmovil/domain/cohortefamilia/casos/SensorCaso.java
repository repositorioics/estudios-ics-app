package ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.AreaAmbiente;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cuarto;

import java.util.Date;

/**
 * Created by Miguel Salinas on 11/10/2018.
 * V1.0
 */
public class SensorCaso extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoSensor;
	private CasaCohorteFamiliaCaso codigoCaso;
    private String numeroSensor;
    private AreaAmbiente area;
    private Cuarto cuarto;
	private Date fechaColocacion;
    private Date fechaRetiro;
    private String horaRetiro;
    private String observacionRetiro;
    private String sensorSN;
    private String razonNoColocaSensor;

    public String getCodigoSensor() {
        return codigoSensor;
    }

    public void setCodigoSensor(String codigoSensor) {
        this.codigoSensor = codigoSensor;
    }

    public CasaCohorteFamiliaCaso getCodigoCaso() {
        return codigoCaso;
    }

    public void setCodigoCaso(CasaCohorteFamiliaCaso codigoCaso) {
        this.codigoCaso = codigoCaso;
    }

    public String getNumeroSensor() {
        return numeroSensor;
    }

    public void setNumeroSensor(String numeroSensor) {
        this.numeroSensor = numeroSensor;
    }

    public AreaAmbiente getArea() {
        return area;
    }

    public void setArea(AreaAmbiente area) {
        this.area = area;
    }

    public Cuarto getCuarto() {
        return cuarto;
    }

    public void setCuarto(Cuarto cuarto) {
        this.cuarto = cuarto;
    }

    public Date getFechaColocacion() {
        return fechaColocacion;
    }

    public void setFechaColocacion(Date fechaColocacion) {
        this.fechaColocacion = fechaColocacion;
    }

    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public String getHoraRetiro() {
        return horaRetiro;
    }

    public void setHoraRetiro(String horaRetiro) {
        this.horaRetiro = horaRetiro;
    }

    public String getObservacionRetiro() {
        return observacionRetiro;
    }

    public void setObservacionRetiro(String observacionRetiro) {
        this.observacionRetiro = observacionRetiro;
    }

    public String getSensorSN() {
        return sensorSN;
    }

    public void setSensorSN(String sensorSN) {
        this.sensorSN = sensorSN;
    }

    public String getRazonNoColocaSensor() {
        return razonNoColocaSensor;
    }

    public void setRazonNoColocaSensor(String razonNoColocaSensor) {
        this.razonNoColocaSensor = razonNoColocaSensor;
    }

    @Override
	public String toString(){
		return codigoCaso.getCodigoCaso() + "-" + numeroSensor;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorCaso)) return false;

        SensorCaso that = (SensorCaso) o;

        if (!codigoSensor.equals(that.codigoSensor)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoSensor.hashCode();
        result = 31 * result + codigoCaso.hashCode();
        return result;
    }

}
