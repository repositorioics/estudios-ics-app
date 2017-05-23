package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 5/20/2017.
 * V1.0
 */
public class MuestrasFormLabels {

    protected String tomaMxSn;
    protected String codigoMx;
    //protected String hora;
    protected String horaInicio;
    protected String horaFin;
    protected String horaHint;
    protected String volumen;
    protected String observacion;
    protected String descOtraObservacion;
    protected String numPinchazos;
    protected String razonNoToma;
    protected String descOtraRazonNoToma;
    protected String tubo;
    protected String tipoMuestra;
    protected String proposito;
    protected String horaInicioPax;
    protected String horaFinPax;
    protected String rojo3ml;
    protected String rojo6ml;
    protected String bhc2ml;
    protected String volumenPaxgene;
    protected String volumenHint;
    protected String descOtraObservacionHint;
    protected String descOtraRazonNoTomaHint;
    protected String rojo12ml;
    protected String volumenPaxgene14;
    protected String bhc2ml14;

    public MuestrasFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        tomaMxSn = res.getString(R.string.tomaMxSn);
        codigoMx = res.getString(R.string.codigoMx);
        //hora = res.getString(R.string.hora);
        horaInicio = res.getString(R.string.horaInicio);
        horaFin = res.getString(R.string.horaFin);
        horaHint = res.getString(R.string.horaHint);
        volumen = res.getString(R.string.volumen);
        observacion = res.getString(R.string.observacion);
        descOtraObservacion = res.getString(R.string.descOtraObservacion);
        numPinchazos = res.getString(R.string.numPinchazos);
        razonNoToma = res.getString(R.string.razonNoToma);
        descOtraRazonNoToma = res.getString(R.string.descOtraRazonNoToma);
        tubo = res.getString(R.string.tubo);
        tipoMuestra = res.getString(R.string.tipoMuestra);
        proposito = res.getString(R.string.proposito);
        horaInicioPax = res.getString(R.string.horaInicioPax);
        horaFinPax = res.getString(R.string.horaFinPax);
        rojo3ml = res.getString(R.string.rojo3ml);
        rojo6ml = res.getString(R.string.rojo6ml);
        bhc2ml = res.getString(R.string.bhc2ml);
        volumenPaxgene = res.getString(R.string.volumenPaxgene);
        volumenHint = res.getString(R.string.volumenHint);
        descOtraObservacionHint = res.getString(R.string.descOtraObservacionHint);
        descOtraRazonNoTomaHint = res.getString(R.string.descOtraRazonNoTomaHint);
        rojo12ml = res.getString(R.string.rojo12ml);
        volumenPaxgene14 = res.getString(R.string.volumenPaxgene14);
        bhc2ml14 = res.getString(R.string.bhc2ml14);

    }

    public String getTomaMxSn() {
        return tomaMxSn;
    }

    public void setTomaMxSn(String tomaMxSn) {
        this.tomaMxSn = tomaMxSn;
    }

    public String getCodigoMx() {
        return codigoMx;
    }

    public void setCodigoMx(String codigoMx) {
        this.codigoMx = codigoMx;
    }
/*
    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
*/
    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDescOtraObservacion() {
        return descOtraObservacion;
    }

    public void setDescOtraObservacion(String descOtraObservacion) {
        this.descOtraObservacion = descOtraObservacion;
    }

    public String getNumPinchazos() {
        return numPinchazos;
    }

    public void setNumPinchazos(String numPinchazos) {
        this.numPinchazos = numPinchazos;
    }

    public String getRazonNoToma() {
        return razonNoToma;
    }

    public void setRazonNoToma(String razonNoToma) {
        this.razonNoToma = razonNoToma;
    }

    public String getDescOtraRazonNoToma() {
        return descOtraRazonNoToma;
    }

    public void setDescOtraRazonNoToma(String descOtraRazonNoToma) {
        this.descOtraRazonNoToma = descOtraRazonNoToma;
    }

    public String getTubo() {
        return tubo;
    }

    public void setTubo(String tubo) {
        this.tubo = tubo;
    }

    public String getTipoMuestra() {
        return tipoMuestra;
    }

    public void setTipoMuestra(String tipoMuestra) {
        this.tipoMuestra = tipoMuestra;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getHoraHint() {
        return horaHint;
    }

    public void setHoraHint(String horaHint) {
        this.horaHint = horaHint;
    }

    public String getHoraInicioPax() {
        return horaInicioPax;
    }

    public void setHoraInicioPax(String horaInicioPax) {
        this.horaInicioPax = horaInicioPax;
    }

    public String getHoraFinPax() {
        return horaFinPax;
    }

    public void setHoraFinPax(String horaFinPax) {
        this.horaFinPax = horaFinPax;
    }

    public String getRojo3ml() {
        return rojo3ml;
    }

    public void setRojo3ml(String rojo3ml) {
        this.rojo3ml = rojo3ml;
    }

    public String getRojo6ml() {
        return rojo6ml;
    }

    public void setRojo6ml(String rojo6ml) {
        this.rojo6ml = rojo6ml;
    }

    public String getBhc2ml() {
        return bhc2ml;
    }

    public void setBhc2ml(String bhc2ml) {
        this.bhc2ml = bhc2ml;
    }

    public String getVolumenPaxgene() {
        return volumenPaxgene;
    }

    public void setVolumenPaxgene(String volumenPaxgene) {
        this.volumenPaxgene = volumenPaxgene;
    }

    public String getVolumenHint() {
        return volumenHint;
    }

    public void setVolumenHint(String volumenHint) {
        this.volumenHint = volumenHint;
    }

    public String getDescOtraObservacionHint() {
        return descOtraObservacionHint;
    }

    public void setDescOtraObservacionHint(String descOtraObservacionHint) {
        this.descOtraObservacionHint = descOtraObservacionHint;
    }

    public String getDescOtraRazonNoTomaHint() {
        return descOtraRazonNoTomaHint;
    }

    public void setDescOtraRazonNoTomaHint(String descOtraRazonNoTomaHint) {
        this.descOtraRazonNoTomaHint = descOtraRazonNoTomaHint;
    }

    public String getRojo12ml() {
        return rojo12ml;
    }

    public void setRojo12ml(String rojo12ml) {
        this.rojo12ml = rojo12ml;
    }

    public String getVolumenPaxgene14() {
        return volumenPaxgene14;
    }

    public void setVolumenPaxgene14(String volumenPaxgene14) {
        this.volumenPaxgene14 = volumenPaxgene14;
    }

    public String getBhc2ml14() {
        return bhc2ml14;
    }

    public void setBhc2ml14(String bhc2ml14) {
        this.bhc2ml14 = bhc2ml14;
    }
}
