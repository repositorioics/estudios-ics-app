package ni.org.ics.estudios.appmovil.influenzauo1.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

public class VisitaCasoUO1FormLabels {

    protected String fechaVisita;
    protected String visita;
    protected String visitaExitosa;
    protected String razonVisitaFallida;
    protected String otraRazon;
    protected String positivoPor;
    protected String fif;
    protected String vacunaFlu3Semanas;
    protected String fechaVacuna;

    public VisitaCasoUO1FormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        fechaVisita = res.getString(R.string.fechaVisitaUO1);
        visita = res.getString(R.string.visitaUO1);
        visitaExitosa = res.getString(R.string.visitaExitosaUO1);
        razonVisitaFallida = res.getString(R.string.razonVisitaFallidaUO1);
        otraRazon = res.getString(R.string.otraRazonUO1);
        positivoPor = res.getString(R.string.positivoPorUO1);
        fif = res.getString(R.string.fif);
        vacunaFlu3Semanas = res.getString(R.string.vacunaInfluenzaUO1);
        fechaVacuna = res.getString(R.string.fechaVacunaUO1);

    }

    public String getFechaVisita() {
        return fechaVisita;
    }

    public String getVisita() {
        return visita;
    }

    public String getVisitaExitosa() {
        return visitaExitosa;
    }

    public String getRazonVisitaFallida() {
        return razonVisitaFallida;
    }

    public String getOtraRazon() {
        return otraRazon;
    }

    public String getPositivoPor() {
        return positivoPor;
    }

    public String getFif() {
        return fif;
    }

    public String getVacunaFlu3Semanas() {
        return vacunaFlu3Semanas;
    }

    public String getFechaVacuna() {
        return fechaVacuna;
    }
}
