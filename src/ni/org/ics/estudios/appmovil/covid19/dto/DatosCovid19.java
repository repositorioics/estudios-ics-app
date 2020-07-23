package ni.org.ics.estudios.appmovil.covid19.dto;

import java.io.Serializable;

public class DatosCovid19 implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean seguimiento;
    private String mensajeSeguimiento;

    public boolean isSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(boolean seguimiento) {
        this.seguimiento = seguimiento;
    }

    public String getMensajeSeguimiento() {
        return mensajeSeguimiento;
    }

    public void setMensajeSeguimiento(String mensajeSeguimiento) {
        this.mensajeSeguimiento = mensajeSeguimiento;
    }
}
