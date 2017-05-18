package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 5/16/2017.
 * V1.0
 */
public class EncuestaDatosPartoBBFormLabels {

    protected String tipoParto;
    protected String tiempoEmbSndr;
    protected String tiempoEmbSemana;
    protected String docMedTiempoEmbSn;
    protected String docMedTiempoEmb;
    protected String otroDocMedTiempoEmb;
    protected String otroDocMedTiempoEmbHint;
    protected String fum;
    protected String fumFueraRangoSn;
    protected String fumFueraRangoSnHint;
    protected String fumFueraRangoRazon;
    protected String fumFueraRangoRazonHint;
    protected String docMedEdadGestSn;
    protected String edadGest;
    protected String edadGestHint;
    protected String docMedEdadGest;
    protected String docMedEdadGestHint;
    protected String OtroDocMedEdadGest;
    protected String OtroDocMedEdadGestHint;
    protected String prematuroSndr;
    protected String pesoBBSndr;
    protected String pesoBB;
    protected String docMedPesoBBSn;
    protected String docMedPesoBB;
    protected String otroDocMedPesoBB;
    protected String otroDocMedPesoBBHint;
    protected String reingresarFUM;

    public EncuestaDatosPartoBBFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        tipoParto = res.getString(R.string.tipoParto);
        tiempoEmbSndr = res.getString(R.string.tiempoEmb_sndr);
        tiempoEmbSemana = res.getString(R.string.tiempoEmbSemana);
        docMedTiempoEmbSn = res.getString(R.string.docMedTiempoEmb_sn);
        docMedTiempoEmb = res.getString(R.string.docMedTiempoEmb);
        otroDocMedTiempoEmb = res.getString(R.string.otroDocMedTiempoEmb);
        otroDocMedTiempoEmbHint = res.getString(R.string.otroDocMedTiempoEmbHint);
        fum = res.getString(R.string.fum);
        fumFueraRangoSn = res.getString(R.string.fumFueraRango_sn);
        fumFueraRangoSnHint = res.getString(R.string.fumFueraRango_snHint);
        fumFueraRangoRazon = res.getString(R.string.fumFueraRango_razon);
        fumFueraRangoRazonHint = res.getString(R.string.fumFueraRango_razonHint);
        docMedEdadGestSn = res.getString(R.string.docMedEdadGest_sn);
        edadGest = res.getString(R.string.edadGest);
        edadGestHint = res.getString(R.string.edadGestHint);
        docMedEdadGest = res.getString(R.string.docMedEdadGest);
        docMedEdadGestHint = res.getString(R.string.docMedEdadGestHint);
        OtroDocMedEdadGest = res.getString(R.string.otroDocMedEdadGest);
        OtroDocMedEdadGestHint = res.getString(R.string.OtroDocMedEdadGestHint);
        prematuroSndr = res.getString(R.string.prematuro_sndr);
        pesoBBSndr = res.getString(R.string.pesoBB_sndr);
        pesoBB = res.getString(R.string.pesoBB);
        docMedPesoBBSn = res.getString(R.string.docMedPesoBB_sn);
        docMedPesoBB = res.getString(R.string.docMedPesoBB);
        otroDocMedPesoBB = res.getString(R.string.otroDocMedPesoBB);
        otroDocMedPesoBBHint = res.getString(R.string.otroDocMedPesoBBHint);
        reingresarFUM = res.getString(R.string.reingresarFUM);
    }

    public String getTipoParto() {
        return tipoParto;
    }

    public void setTipoParto(String tipoParto) {
        this.tipoParto = tipoParto;
    }

    public String getTiempoEmbSndr() {
        return tiempoEmbSndr;
    }

    public void setTiempoEmbSndr(String tiempoEmbSndr) {
        this.tiempoEmbSndr = tiempoEmbSndr;
    }

    public String getTiempoEmbSemana() {
        return tiempoEmbSemana;
    }

    public void setTiempoEmbSemana(String tiempoEmbSemana) {
        this.tiempoEmbSemana = tiempoEmbSemana;
    }

    public String getDocMedTiempoEmbSn() {
        return docMedTiempoEmbSn;
    }

    public void setDocMedTiempoEmbSn(String docMedTiempoEmbSn) {
        this.docMedTiempoEmbSn = docMedTiempoEmbSn;
    }

    public String getDocMedTiempoEmb() {
        return docMedTiempoEmb;
    }

    public void setDocMedTiempoEmb(String docMedTiempoEmb) {
        this.docMedTiempoEmb = docMedTiempoEmb;
    }

    public String getOtroDocMedTiempoEmb() {
        return otroDocMedTiempoEmb;
    }

    public void setOtroDocMedTiempoEmb(String otroDocMedTiempoEmb) {
        this.otroDocMedTiempoEmb = otroDocMedTiempoEmb;
    }

    public String getFum() {
        return fum;
    }

    public void setFum(String fum) {
        this.fum = fum;
    }

    public String getFumFueraRangoSn() {
        return fumFueraRangoSn;
    }

    public void setFumFueraRangoSn(String fumFueraRangoSn) {
        this.fumFueraRangoSn = fumFueraRangoSn;
    }

    public String getFumFueraRangoSnHint() {
        return fumFueraRangoSnHint;
    }

    public void setFumFueraRangoSnHint(String fumFueraRangoSnHint) {
        this.fumFueraRangoSnHint = fumFueraRangoSnHint;
    }

    public String getFumFueraRangoRazon() {
        return fumFueraRangoRazon;
    }

    public void setFumFueraRangoRazon(String fumFueraRangoRazon) {
        this.fumFueraRangoRazon = fumFueraRangoRazon;
    }

    public String getFumFueraRangoRazonHint() {
        return fumFueraRangoRazonHint;
    }

    public void setFumFueraRangoRazonHint(String fumFueraRangoRazonHint) {
        this.fumFueraRangoRazonHint = fumFueraRangoRazonHint;
    }

    public String getDocMedEdadGestSn() {
        return docMedEdadGestSn;
    }

    public void setDocMedEdadGestSn(String docMedEdadGestSn) {
        this.docMedEdadGestSn = docMedEdadGestSn;
    }

    public String getEdadGest() {
        return edadGest;
    }

    public void setEdadGest(String edadGest) {
        this.edadGest = edadGest;
    }

    public String getEdadGestHint() {
        return edadGestHint;
    }

    public void setEdadGestHint(String edadGestHint) {
        this.edadGestHint = edadGestHint;
    }

    public String getDocMedEdadGest() {
        return docMedEdadGest;
    }

    public void setDocMedEdadGest(String docMedEdadGest) {
        this.docMedEdadGest = docMedEdadGest;
    }

    public String getDocMedEdadGestHint() {
        return docMedEdadGestHint;
    }

    public void setDocMedEdadGestHint(String docMedEdadGestHint) {
        this.docMedEdadGestHint = docMedEdadGestHint;
    }

    public String getOtroDocMedEdadGest() {
        return OtroDocMedEdadGest;
    }

    public void setOtroDocMedEdadGest(String otroDocMedEdadGest) {
        OtroDocMedEdadGest = otroDocMedEdadGest;
    }

    public String getOtroDocMedEdadGestHint() {
        return OtroDocMedEdadGestHint;
    }

    public void setOtroDocMedEdadGestHint(String otroDocMedEdadGestHint) {
        OtroDocMedEdadGestHint = otroDocMedEdadGestHint;
    }

    public String getPrematuroSndr() {
        return prematuroSndr;
    }

    public void setPrematuroSndr(String prematuroSndr) {
        this.prematuroSndr = prematuroSndr;
    }

    public String getPesoBBSndr() {
        return pesoBBSndr;
    }

    public void setPesoBBSndr(String pesoBBSndr) {
        this.pesoBBSndr = pesoBBSndr;
    }

    public String getPesoBB() {
        return pesoBB;
    }

    public void setPesoBB(String pesoBB) {
        this.pesoBB = pesoBB;
    }

    public String getDocMedPesoBBSn() {
        return docMedPesoBBSn;
    }

    public void setDocMedPesoBBSn(String docMedPesoBBSn) {
        this.docMedPesoBBSn = docMedPesoBBSn;
    }

    public String getDocMedPesoBB() {
        return docMedPesoBB;
    }

    public void setDocMedPesoBB(String docMedPesoBB) {
        this.docMedPesoBB = docMedPesoBB;
    }

    public String getOtroDocMedPesoBB() {
        return otroDocMedPesoBB;
    }

    public void setOtroDocMedPesoBB(String otroDocMedPesoBB) {
        this.otroDocMedPesoBB = otroDocMedPesoBB;
    }

    public String getReingresarFUM() {
        return reingresarFUM;
    }

    public void setReingresarFUM(String reingresarFUM) {
        this.reingresarFUM = reingresarFUM;
    }

    public String getOtroDocMedTiempoEmbHint() {
        return otroDocMedTiempoEmbHint;
    }

    public void setOtroDocMedTiempoEmbHint(String otroDocMedTiempoEmbHint) {
        this.otroDocMedTiempoEmbHint = otroDocMedTiempoEmbHint;
    }

    public String getOtroDocMedPesoBBHint() {
        return otroDocMedPesoBBHint;
    }

    public void setOtroDocMedPesoBBHint(String otroDocMedPesoBBHint) {
        this.otroDocMedPesoBBHint = otroDocMedPesoBBHint;
    }
}
