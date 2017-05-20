package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 5/19/2017.
 * V1.0
 */
public class EncuestaLactanciaMatFormLabels {

    protected String dioPecho;
    protected String tiemPecho;
    protected String mesDioPecho;
    protected String pechoExc;
    protected String pechoExcHint;
    protected String formAlim;
    protected String pechoExcAntes;
    protected String tiempPechoExcAntes;
    protected String mestPechoExc;
    protected String otraAlim;
    protected String otraAlimHint;
    protected String edadLiqDistPecho;
    protected String edadLiqDistPechoHint;
    protected String mesDioLiqDisPecho;
    protected String edadLiqDistLeche;
    protected String edadLiqDistLecheHint;
    protected String mesDioLiqDisLeche;
    protected String edAlimSolidos;
    protected String edAlimSolidosHint;
    protected String mesDioAlimSol;

    public EncuestaLactanciaMatFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();

        dioPecho = res.getString(R.string.dioPecho);
        tiemPecho = res.getString(R.string.tiemPecho);
        mesDioPecho = res.getString(R.string.mesDioPecho);
        pechoExc = res.getString(R.string.pechoExc);
        pechoExcHint = res.getString(R.string.pechoExcHint);
        formAlim = res.getString(R.string.formAlim);
        pechoExcAntes = res.getString(R.string.pechoExcAntes);
        tiempPechoExcAntes = res.getString(R.string.tiempPechoExcAntes);
        mestPechoExc = res.getString(R.string.mestPechoExc);
        otraAlim = res.getString(R.string.otraAlim);
        otraAlimHint = res.getString(R.string.otraAlimHint);
        edadLiqDistPecho = res.getString(R.string.edadLiqDistPecho);
        edadLiqDistPechoHint = res.getString(R.string.edadLiqDistPechoHint);
        mesDioLiqDisPecho = res.getString(R.string.mesDioLiqDisPecho);
        edadLiqDistLeche = res.getString(R.string.edadLiqDistLeche);
        edadLiqDistLecheHint = res.getString(R.string.edadLiqDistLecheHint);
        mesDioLiqDisLeche = res.getString(R.string.mesDioLiqDisLeche);
        edAlimSolidos = res.getString(R.string.edAlimSolidos);
        edAlimSolidosHint = res.getString(R.string.edAlimSolidosHint);
        mesDioAlimSol = res.getString(R.string.mesDioAlimSol);

    }

    public String getDioPecho() {
        return dioPecho;
    }

    public void setDioPecho(String dioPecho) {
        this.dioPecho = dioPecho;
    }

    public String getTiemPecho() {
        return tiemPecho;
    }

    public void setTiemPecho(String tiemPecho) {
        this.tiemPecho = tiemPecho;
    }

    public String getMesDioPecho() {
        return mesDioPecho;
    }

    public void setMesDioPecho(String mesDioPecho) {
        this.mesDioPecho = mesDioPecho;
    }

    public String getPechoExc() {
        return pechoExc;
    }

    public void setPechoExc(String pechoExc) {
        this.pechoExc = pechoExc;
    }

    public String getPechoExcHint() {
        return pechoExcHint;
    }

    public void setPechoExcHint(String pechoExcHint) {
        this.pechoExcHint = pechoExcHint;
    }

    public String getFormAlim() {
        return formAlim;
    }

    public void setFormAlim(String formAlim) {
        this.formAlim = formAlim;
    }

    public String getPechoExcAntes() {
        return pechoExcAntes;
    }

    public void setPechoExcAntes(String pechoExcAntes) {
        this.pechoExcAntes = pechoExcAntes;
    }

    public String getTiempPechoExcAntes() {
        return tiempPechoExcAntes;
    }

    public void setTiempPechoExcAntes(String tiempPechoExcAntes) {
        this.tiempPechoExcAntes = tiempPechoExcAntes;
    }

    public String getMestPechoExc() {
        return mestPechoExc;
    }

    public void setMestPechoExc(String mestPechoExc) {
        this.mestPechoExc = mestPechoExc;
    }

    public String getOtraAlim() {
        return otraAlim;
    }

    public void setOtraAlim(String otraAlim) {
        this.otraAlim = otraAlim;
    }

    public String getOtraAlimHint() {
        return otraAlimHint;
    }

    public void setOtraAlimHint(String otraAlimHint) {
        this.otraAlimHint = otraAlimHint;
    }

    public String getEdadLiqDistPecho() {
        return edadLiqDistPecho;
    }

    public void setEdadLiqDistPecho(String edadLiqDistPecho) {
        this.edadLiqDistPecho = edadLiqDistPecho;
    }

    public String getMesDioLiqDisPecho() {
        return mesDioLiqDisPecho;
    }

    public void setMesDioLiqDisPecho(String mesDioLiqDisPecho) {
        this.mesDioLiqDisPecho = mesDioLiqDisPecho;
    }

    public String getEdadLiqDistLeche() {
        return edadLiqDistLeche;
    }

    public void setEdadLiqDistLeche(String edadLiqDistLeche) {
        this.edadLiqDistLeche = edadLiqDistLeche;
    }

    public String getMesDioLiqDisLeche() {
        return mesDioLiqDisLeche;
    }

    public void setMesDioLiqDisLeche(String mesDioLiqDisLeche) {
        this.mesDioLiqDisLeche = mesDioLiqDisLeche;
    }

    public String getEdAlimSolidos() {
        return edAlimSolidos;
    }

    public void setEdAlimSolidos(String edAlimSolidos) {
        this.edAlimSolidos = edAlimSolidos;
    }

    public String getMesDioAlimSol() {
        return mesDioAlimSol;
    }

    public void setMesDioAlimSol(String mesDioAlimSol) {
        this.mesDioAlimSol = mesDioAlimSol;
    }

    public String getEdadLiqDistPechoHint() {
        return edadLiqDistPechoHint;
    }

    public void setEdadLiqDistPechoHint(String edadLiqDistPechoHint) {
        this.edadLiqDistPechoHint = edadLiqDistPechoHint;
    }

    public String getEdadLiqDistLecheHint() {
        return edadLiqDistLecheHint;
    }

    public void setEdadLiqDistLecheHint(String edadLiqDistLecheHint) {
        this.edadLiqDistLecheHint = edadLiqDistLecheHint;
    }

    public String getEdAlimSolidosHint() {
        return edAlimSolidosHint;
    }

    public void setEdAlimSolidosHint(String edAlimSolidosHint) {
        this.edAlimSolidosHint = edAlimSolidosHint;
    }
}
