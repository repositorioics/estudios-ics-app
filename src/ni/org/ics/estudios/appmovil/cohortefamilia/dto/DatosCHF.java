package ni.org.ics.estudios.appmovil.cohortefamilia.dto;

import java.io.Serializable;

public class DatosCHF implements Serializable {

    private boolean enMonitoreoIntensivo;

    public boolean isEnMonitoreoIntensivo() {
        return enMonitoreoIntensivo;
    }

    public void setEnMonitoreoIntensivo(boolean enMonitoreoIntensivo) {
        this.enMonitoreoIntensivo = enMonitoreoIntensivo;
    }
}
