package ni.org.ics.estudios.appmovil.influenzauo1.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DatosUO1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean convalesciente;
    private Date fechaInicioCaso;
    private boolean vacunado;
    private Date fechaVacuna;

    public boolean isConvalesciente() {
        return convalesciente;
    }

    public void setConvalesciente(boolean convalesciente) {
        this.convalesciente = convalesciente;
    }

    public Date getFechaInicioCaso() {
        return fechaInicioCaso;
    }

    public void setFechaInicioCaso(Date fechaInicioCaso) {
        this.fechaInicioCaso = fechaInicioCaso;
    }

    public boolean isVacunado() {
        return vacunado;
    }

    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }

    public Date getFechaVacuna() {
        return fechaVacuna;
    }

    public void setFechaVacuna(Date fechaVacuna) {
        this.fechaVacuna = fechaVacuna;
    }

    public long getDiasConvalesciente(){
        TimeUnit timeUnit = TimeUnit.DAYS;
        if (this.fechaInicioCaso!=null){
            long diffInMillies = new Date().getTime() - this.fechaInicioCaso.getTime();
            return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
        }else
            return 0;
    }

    public long getDiasVacuna(){
        TimeUnit timeUnit = TimeUnit.DAYS;
        if (this.fechaVacuna!=null){
            long diffInMillies = new Date().getTime() - this.fechaVacuna.getTime();
            return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
        }else
            return 0;
    }
}
