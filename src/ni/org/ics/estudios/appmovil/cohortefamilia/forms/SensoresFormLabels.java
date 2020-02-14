package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;


/**
 * Constantes usadas en formulario de pre tamizaje
 * 
 * @author William Aviles
 * 
 */
public class SensoresFormLabels {

	protected String numSensor;
    protected String numSensorHint;
    protected String enHabitacion;
    protected String enHabitacionHint;
    protected String habitacionSensor;
    protected String habitacionSensorHint;
    protected String areaSensor;
    protected String areaSensorHint ;
    protected String fechaSensor;
    protected String fechaSensorHint;
    protected String horaRetiraSensor;
    protected String horaRetiraSensorHint;
    protected String sensorSN;
    protected String razonNoColocaSensor;
    protected String razonNoColocaSensorHint;

	public SensoresFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
        numSensor = res.getString(R.string.numSensor);
        numSensorHint = res.getString(R.string.numSensorHint);
        enHabitacion = res.getString(R.string.enHabitacion);
        enHabitacionHint = res.getString(R.string.enHabitacionHint);
        habitacionSensor = res.getString(R.string.habitacionSensor);
        habitacionSensorHint = res.getString(R.string.habitacionSensorHint);
        areaSensor = res.getString(R.string.areaSensor);
        areaSensorHint = res.getString(R.string.areaSensorHint);
        fechaSensor = res.getString(R.string.fechaSensor);
        fechaSensorHint = res.getString(R.string.fechaSensorHint);
        horaRetiraSensor = res.getString(R.string.horaRetiraSensor);
        horaRetiraSensorHint = res.getString(R.string.horaRetiraSensorHint);
        sensorSN = res.getString(R.string.sensorSN);
        razonNoColocaSensor =res.getString(R.string.razonNoColocaSensor);
        razonNoColocaSensorHint = res.getString(R.string.razonNoColocaSensorHint);
	}

    public String getNumSensor() {
        return numSensor;
    }

    public void setNumSensor(String numSensor) {
        this.numSensor = numSensor;
    }

    public String getNumSensorHint() {
        return numSensorHint;
    }

    public void setNumSensorHint(String numSensorHint) {
        this.numSensorHint = numSensorHint;
    }

    public String getEnHabitacion() {
        return enHabitacion;
    }

    public void setEnHabitacion(String enHabitacion) {
        this.enHabitacion = enHabitacion;
    }

    public String getEnHabitacionHint() {
        return enHabitacionHint;
    }

    public void setEnHabitacionHint(String enHabitacionHint) {
        this.enHabitacionHint = enHabitacionHint;
    }

    public String getHabitacionSensor() {
        return habitacionSensor;
    }

    public void setHabitacionSensor(String habitacionSensor) {
        this.habitacionSensor = habitacionSensor;
    }

    public String getHabitacionSensorHint() {
        return habitacionSensorHint;
    }

    public void setHabitacionSensorHint(String habitacionSensorHint) {
        this.habitacionSensorHint = habitacionSensorHint;
    }

    public String getAreaSensor() {
        return areaSensor;
    }

    public void setAreaSensor(String areaSensor) {
        this.areaSensor = areaSensor;
    }

    public String getAreaSensorHint() {
        return areaSensorHint;
    }

    public void setAreaSensorHint(String areaSensorHint) {
        this.areaSensorHint = areaSensorHint;
    }

    public String getFechaSensor() {
        return fechaSensor;
    }

    public void setFechaSensor(String fechaSensor) {
        this.fechaSensor = fechaSensor;
    }

    public String getFechaSensorHint() {
        return fechaSensorHint;
    }

    public void setFechaSensorHint(String fechaSensorHint) {
        this.fechaSensorHint = fechaSensorHint;
    }

    public String getHoraRetiraSensor() {
        return horaRetiraSensor;
    }

    public void setHoraRetiraSensor(String horaRetiraSensor) {
        this.horaRetiraSensor = horaRetiraSensor;
    }

    public String getHoraRetiraSensorHint() {
        return horaRetiraSensorHint;
    }

    public void setHoraRetiraSensorHint(String horaRetiraSensorHint) {
        this.horaRetiraSensorHint = horaRetiraSensorHint;
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

    public String getRazonNoColocaSensorHint() {
        return razonNoColocaSensorHint;
    }

    public void setRazonNoColocaSensorHint(String razonNoColocaSensorHint) {
        this.razonNoColocaSensorHint = razonNoColocaSensorHint;
    }
}
