package ni.org.ics.estudios.appmovil.influenzauo1.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

public class SintomasVisitaCasoUO1FormLabels {
    protected String fechaSintomas;
    protected String dia;
    protected String consultaInicial;
    protected String fiebre;
    protected String fiebreIntensidad;
    protected String tos;
    protected String tosIntensidad;
    protected String secrecionNasal;
    protected String secrecionNasalIntensidad;
    protected String dolorGarganta;
    protected String dolorGargantaIntensidad;
    protected String congestionNasal;
    protected String dolorCabeza;
    protected String dolorCabezaIntensidad;
    protected String faltaApetito;
    protected String dolorMuscular;
    protected String dolorMuscularIntensidad;
    protected String dolorArticular;
    protected String dolorArticularIntensidad;
    protected String dolorOido;
    protected String respiracionRapida;
    protected String dificultadRespiratoria;
    protected String faltaEscuelta;
    protected String quedoCama;

    public SintomasVisitaCasoUO1FormLabels() {
        Resources res = MyIcsApplication.getContext().getResources();
        fechaSintomas = res.getString(R.string.fechaSintomaUO1);
        dia = res.getString(R.string.diaSintomaUO1);
        consultaInicial = res.getString(R.string.consultaInicialUO1);
        fiebre = res.getString(R.string.fiebreUO1);
        fiebreIntensidad = res.getString(R.string.fiebreIntensidadUO1);
        tos = res.getString(R.string.tosUO1);
        tosIntensidad = res.getString(R.string.tosIntensidadUO1);
        secrecionNasal = res.getString(R.string.secrecionNasalUO1);
        secrecionNasalIntensidad = res.getString(R.string.secrecionNasalIntensidadUO1);
        dolorGarganta = res.getString(R.string.dolorGargantaUO1);
        dolorGargantaIntensidad = res.getString(R.string.dolorGargantaIntensidadUO1);
        congestionNasal = res.getString(R.string.congestionNasalUO1);
        dolorCabeza = res.getString(R.string.dolorCabezaUO1);
        dolorCabezaIntensidad = res.getString(R.string.dolorCabezaIntensidadUO1);
        faltaApetito = res.getString(R.string.faltaApetitoUO1);
        dolorMuscular = res.getString(R.string.dolorMuscularUO1);
        dolorMuscularIntensidad = res.getString(R.string.dolorMuscularIntensidadUO1);
        dolorArticular = res.getString(R.string.dolorArticularUO1);
        dolorArticularIntensidad = res.getString(R.string.dolorArticularIntensidadUO1);
        dolorOido = res.getString(R.string.dolorOidoUO1);
        respiracionRapida = res.getString(R.string.respiracionRapidaUO1);
        dificultadRespiratoria = res.getString(R.string.dificultadRespiratoriaUO1);
        faltaEscuelta = res.getString(R.string.faltaEscueltaUO1);
        quedoCama = res.getString(R.string.quedoCamaUO1);
    }

    public String getFechaSintomas() {
        return fechaSintomas;
    }

    public String getDia() {
        return dia;
    }

    public String getConsultaInicial() {
        return consultaInicial;
    }

    public String getFiebre() {
        return fiebre;
    }

    public String getFiebreIntensidad() {
        return fiebreIntensidad;
    }

    public String getTos() {
        return tos;
    }

    public String getTosIntensidad() {
        return tosIntensidad;
    }

    public String getSecrecionNasal() {
        return secrecionNasal;
    }

    public String getSecrecionNasalIntensidad() {
        return secrecionNasalIntensidad;
    }

    public String getDolorGarganta() {
        return dolorGarganta;
    }

    public String getDolorGargantaIntensidad() {
        return dolorGargantaIntensidad;
    }

    public String getCongestionNasal() {
        return congestionNasal;
    }

    public String getDolorCabeza() {
        return dolorCabeza;
    }

    public String getDolorCabezaIntensidad() {
        return dolorCabezaIntensidad;
    }

    public String getFaltaApetito() {
        return faltaApetito;
    }

    public String getDolorMuscular() {
        return dolorMuscular;
    }

    public String getDolorMuscularIntensidad() {
        return dolorMuscularIntensidad;
    }

    public String getDolorArticular() {
        return dolorArticular;
    }

    public String getDolorArticularIntensidad() {
        return dolorArticularIntensidad;
    }

    public String getDolorOido() {
        return dolorOido;
    }

    public String getRespiracionRapida() {
        return respiracionRapida;
    }

    public String getDificultadRespiratoria() {
        return dificultadRespiratoria;
    }

    public String getFaltaEscuelta() {
        return faltaEscuelta;
    }

    public String getQuedoCama() {
        return quedoCama;
    }
}
