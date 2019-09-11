package ni.org.ics.estudios.appmovil.influenzauo1.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

public class VisitaVacunaUO1FormLabels {

    protected String fechaVisita;
    protected String visita;
    protected String visitaExitosa;
    protected String razonVisitaFallida;
    protected String otraRazon;
    protected String vacuna;
    protected String fechaVacuna;
    protected String tomaMxAntes;
    protected String razonNoTomaMx;
    protected String segundaDosis;
    protected String fechaSegundaDosis;
    protected String reprogramarTomaMx;
    protected String fechaReprogramacionTomaMx;

    public VisitaVacunaUO1FormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        fechaVisita = res.getString(R.string.fechaVisitaUO1);
        visita = res.getString(R.string.visitaUO1);
        visitaExitosa = res.getString(R.string.visitaExitosaUO1);
        razonVisitaFallida = res.getString(R.string.razonVisitaFallidaUO1);
        otraRazon = res.getString(R.string.otraRazonUO1);
        vacuna = res.getString(R.string.administraVacFluUO1);
        fechaVacuna = res.getString(R.string.fechaVacunaUO1);
        tomaMxAntes = res.getString(R.string.tomamxAntesVacuna);
        razonNoTomaMx = res.getString(R.string.razonNoTomamxAntes);
        segundaDosis = res.getString(R.string.segundaDosis);
        fechaSegundaDosis = res.getString(R.string.fechaVacunaSegundaDosis);
        reprogramarTomaMx = res.getString(R.string.reprogramarTomaMx);
        fechaReprogramacionTomaMx = res.getString(R.string.fechaReprogramacionTomaMx);

    }

    public String getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(String fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public String getVisita() {
        return visita;
    }

    public void setVisita(String visita) {
        this.visita = visita;
    }

    public String getVisitaExitosa() {
        return visitaExitosa;
    }

    public void setVisitaExitosa(String visitaExitosa) {
        this.visitaExitosa = visitaExitosa;
    }

    public String getRazonVisitaFallida() {
        return razonVisitaFallida;
    }

    public void setRazonVisitaFallida(String razonVisitaFallida) {
        this.razonVisitaFallida = razonVisitaFallida;
    }

    public String getOtraRazon() {
        return otraRazon;
    }

    public void setOtraRazon(String otraRazon) {
        this.otraRazon = otraRazon;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public String getFechaVacuna() {
        return fechaVacuna;
    }

    public void setFechaVacuna(String fechaVacuna) {
        this.fechaVacuna = fechaVacuna;
    }

    public String getTomaMxAntes() {
        return tomaMxAntes;
    }

    public void setTomaMxAntes(String tomaMxAntes) {
        this.tomaMxAntes = tomaMxAntes;
    }

    public String getRazonNoTomaMx() {
        return razonNoTomaMx;
    }

    public void setRazonNoTomaMx(String razonNoTomaMx) {
        this.razonNoTomaMx = razonNoTomaMx;
    }

    public String getSegundaDosis() {
        return segundaDosis;
    }

    public void setSegundaDosis(String segundaDosis) {
        this.segundaDosis = segundaDosis;
    }

    public String getFechaSegundaDosis() {
        return fechaSegundaDosis;
    }

    public void setFechaSegundaDosis(String fechaSegundaDosis) {
        this.fechaSegundaDosis = fechaSegundaDosis;
    }

    public String getReprogramarTomaMx() {
        return reprogramarTomaMx;
    }

    public void setReprogramarTomaMx(String reprogramarTomaMx) {
        this.reprogramarTomaMx = reprogramarTomaMx;
    }

    public String getFechaReprogramacionTomaMx() {
        return fechaReprogramacionTomaMx;
    }

    public void setFechaReprogramacionTomaMx(String fechaReprogramacionTomaMx) {
        this.fechaReprogramacionTomaMx = fechaReprogramacionTomaMx;
    }
}
