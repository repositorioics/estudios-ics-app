package ni.org.ics.estudios.appmovil.influenzauo1.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DatosUO1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean convalesciente;//Uo1
    private boolean convalescienteFlu;//CEIRS
    private Date fechaInicioCaso;
    private boolean vacunado; //se aplico vacuna
    private boolean mxTomada;//se tomo mx antes de la vacuna
    private String razonNoMx; //porque no tomo la muestra antes de la vacuna
    private boolean visitaExitosa; //visita exitosa vacuna
    //private Date fechaVisita;
    private Date fechaVacuna;
    private boolean mxFinalTomada; //indica si ya tiene mx final de vacuna

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

    public boolean isMxTomada() {
        return mxTomada;
    }

    public void setMxTomada(boolean mxTomada) {
        this.mxTomada = mxTomada;
    }

    public String getRazonNoMx() {
        return razonNoMx;
    }

    public void setRazonNoMx(String razonNoMx) {
        this.razonNoMx = razonNoMx;
    }

    public boolean isVisitaExitosa() {
        return visitaExitosa;
    }

    public void setVisitaExitosa(boolean visitaExitosa) {
        this.visitaExitosa = visitaExitosa;
    }

    public boolean isConvalescienteFlu() {
        return convalescienteFlu;
    }

    public void setConvalescienteFlu(boolean convalescienteFlu) {
        this.convalescienteFlu = convalescienteFlu;
    }

    public boolean isMxFinalTomada() {
        return mxFinalTomada;
    }

    public void setMxFinalTomada(boolean mxFinalTomada) {
        this.mxFinalTomada = mxFinalTomada;
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
