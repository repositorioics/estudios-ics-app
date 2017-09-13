package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 9/13/2017.
 * V1.0
 */
public class VisitaFinalCasoFormLabels {

    private String fechaVisita;
    private String enfermo;
    private String consTerreno;
    private String referidoCs;
    private String tratamiento;
    private String cualAntibiotico;
    private String sintResp;
    private String sintRespHint;
    private String fiebre;
    private String tos;
    private String dolorGarganta;
    private String secrecionNasal;
    private String fif;
    private String fff;
    private String fitos;
    private String fftos;
    private String figg;
    private String ffgg;
    private String fisn;
    private String ffsn;

    public VisitaFinalCasoFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        fechaVisita = res.getString(R.string.fechaVisita);
        enfermo = res.getString(R.string.enfermo);
        consTerreno = res.getString(R.string.consTerreno);
        referidoCs = res.getString(R.string.referidoCs);
        tratamiento = res.getString(R.string.tratamiento);
        cualAntibiotico = res.getString(R.string.cualAntibiotico);
        sintResp = res.getString(R.string.sintResp);
        sintRespHint = res.getString(R.string.sintRespHint);
        fiebre = res.getString(R.string.fiebre);
        tos = res.getString(R.string.tos);
        dolorGarganta = res.getString(R.string.dolorGarganta);
        secrecionNasal = res.getString(R.string.secrecionNasal);
        fif = res.getString(R.string.fif);
        fff = res.getString(R.string.fff);
        fitos = res.getString(R.string.fitos);
        fftos = res.getString(R.string.fftos);
        figg = res.getString(R.string.figg);
        ffgg = res.getString(R.string.ffgg);
        fisn = res.getString(R.string.fisn);
        ffsn = res.getString(R.string.ffsn);
    }

    public String getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(String fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public String getEnfermo() {
        return enfermo;
    }

    public void setEnfermo(String enfermo) {
        this.enfermo = enfermo;
    }

    public String getConsTerreno() {
        return consTerreno;
    }

    public void setConsTerreno(String consTerreno) {
        this.consTerreno = consTerreno;
    }

    public String getReferidoCs() {
        return referidoCs;
    }

    public void setReferidoCs(String referidoCs) {
        this.referidoCs = referidoCs;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getCualAntibiotico() {
        return cualAntibiotico;
    }

    public void setCualAntibiotico(String cualAntibiotico) {
        this.cualAntibiotico = cualAntibiotico;
    }

    public String getSintResp() {
        return sintResp;
    }

    public void setSintResp(String sintResp) {
        this.sintResp = sintResp;
    }

    public String getSintRespHint() {
        return sintRespHint;
    }

    public void setSintRespHint(String sintRespHint) {
        this.sintRespHint = sintRespHint;
    }

    public String getFiebre() {
        return fiebre;
    }

    public void setFiebre(String fiebre) {
        this.fiebre = fiebre;
    }

    public String getTos() {
        return tos;
    }

    public void setTos(String tos) {
        this.tos = tos;
    }

    public String getDolorGarganta() {
        return dolorGarganta;
    }

    public void setDolorGarganta(String dolorGarganta) {
        this.dolorGarganta = dolorGarganta;
    }

    public String getSecrecionNasal() {
        return secrecionNasal;
    }

    public void setSecrecionNasal(String secrecionNasal) {
        this.secrecionNasal = secrecionNasal;
    }

    public String getFif() {
        return fif;
    }

    public void setFif(String fif) {
        this.fif = fif;
    }

    public String getFff() {
        return fff;
    }

    public void setFff(String fff) {
        this.fff = fff;
    }

    public String getFitos() {
        return fitos;
    }

    public void setFitos(String fitos) {
        this.fitos = fitos;
    }

    public String getFftos() {
        return fftos;
    }

    public void setFftos(String fftos) {
        this.fftos = fftos;
    }

    public String getFigg() {
        return figg;
    }

    public void setFigg(String figg) {
        this.figg = figg;
    }

    public String getFfgg() {
        return ffgg;
    }

    public void setFfgg(String ffgg) {
        this.ffgg = ffgg;
    }

    public String getFisn() {
        return fisn;
    }

    public void setFisn(String fisn) {
        this.fisn = fisn;
    }

    public String getFfsn() {
        return ffsn;
    }

    public void setFfsn(String ffsn) {
        this.ffsn = ffsn;
    }
}
