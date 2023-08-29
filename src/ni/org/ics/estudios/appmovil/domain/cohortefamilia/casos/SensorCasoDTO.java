package ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.AreaAmbiente;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cuarto;

import java.util.Date;

public class SensorCasoDTO extends BaseMetaData {
    private static final long serialVersionUID = 1L;
    private String codigoSensor;
    private String codigoCaso;
    private String numeroSensor;
    private String area;
    private String cuarto;
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

    public String getCodigoCaso() {
        return codigoCaso;
    }

    public void setCodigoCaso(String codigoCaso) {
        this.codigoCaso = codigoCaso;
    }

    public String getNumeroSensor() {
        return numeroSensor;
    }

    public void setNumeroSensor(String numeroSensor) {
        this.numeroSensor = numeroSensor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCuarto() {
        return cuarto;
    }

    public void setCuarto(String cuarto) {
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
}
