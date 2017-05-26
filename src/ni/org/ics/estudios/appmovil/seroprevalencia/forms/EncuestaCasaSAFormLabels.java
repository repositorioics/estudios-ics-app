package ni.org.ics.estudios.appmovil.seroprevalencia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 5/26/2017.
 * V1.0
 */
public class EncuestaCasaSAFormLabels {

    protected String sedazoPuertasVentanas;
    protected String compraProdEvitarZancudos;
    protected String tienePatioJardin;
    protected String utilizaAbate;
    protected String fumiga;
    protected String cadaCuantoFumiga;

    protected String miembroFamConZikaSn;
    protected String cantMiembrosZika;
    protected String fechaDxZika;
    protected String relacionFamZika;
    protected String otraRelacionFamZika;

    protected String miembroFamConDengueSn;
    protected String cantMiembrosDengue;
    protected String fechaDxDengue;
    protected String relacionFamDengue;
    protected String otraRelacionFamDengue;

    protected String miembroFamConChikSn;
    protected String cantMiembrosChik;
    protected String fechaDxChik;
    protected String relacionFamChik;
    protected String otraRelacionFamChik;

    public EncuestaCasaSAFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        sedazoPuertasVentanas = res.getString(R.string.sedazoPuertasVentanas);
        compraProdEvitarZancudos = res.getString(R.string.compraProdEvitarZancudos);
        tienePatioJardin = res.getString(R.string.tienePatioJardin);
        utilizaAbate = res.getString(R.string.utilizaAbate);
        fumiga = res.getString(R.string.fumiga);
        cadaCuantoFumiga = res.getString(R.string.cadaCuantoFumiga);

        miembroFamConZikaSn = res.getString(R.string.miembroFamConZikaSn);
        cantMiembrosZika = res.getString(R.string.cantMiembrosZika);
        fechaDxZika = res.getString(R.string.fechaDxZika);
        relacionFamZika = res.getString(R.string.relacionFamZika);
        otraRelacionFamZika = res.getString(R.string.otraRelacionFamZika);

        miembroFamConDengueSn = res.getString(R.string.miembroFamConDengueSn);
        cantMiembrosDengue = res.getString(R.string.cantMiembrosDengue);
        fechaDxDengue = res.getString(R.string.fechaDxDengue);
        relacionFamDengue = res.getString(R.string.relacionFamDengue);
        otraRelacionFamDengue = res.getString(R.string.otraRelacionFamDengue);

        miembroFamConChikSn = res.getString(R.string.miembroFamConChikSn);
        cantMiembrosChik = res.getString(R.string.cantMiembrosChik);
        fechaDxChik = res.getString(R.string.fechaDxChik);
        relacionFamChik = res.getString(R.string.relacionFamChik);
        otraRelacionFamChik = res.getString(R.string.otraRelacionFamChik);
    }

    public String getSedazoPuertasVentanas() {
        return sedazoPuertasVentanas;
    }

    public void setSedazoPuertasVentanas(String sedazoPuertasVentanas) {
        this.sedazoPuertasVentanas = sedazoPuertasVentanas;
    }

    public String getCompraProdEvitarZancudos() {
        return compraProdEvitarZancudos;
    }

    public void setCompraProdEvitarZancudos(String compraProdEvitarZancudos) {
        this.compraProdEvitarZancudos = compraProdEvitarZancudos;
    }

    public String getTienePatioJardin() {
        return tienePatioJardin;
    }

    public void setTienePatioJardin(String tienePatioJardin) {
        this.tienePatioJardin = tienePatioJardin;
    }

    public String getUtilizaAbate() {
        return utilizaAbate;
    }

    public void setUtilizaAbate(String utilizaAbate) {
        this.utilizaAbate = utilizaAbate;
    }

    public String getFumiga() {
        return fumiga;
    }

    public void setFumiga(String fumiga) {
        this.fumiga = fumiga;
    }

    public String getCadaCuantoFumiga() {
        return cadaCuantoFumiga;
    }

    public void setCadaCuantoFumiga(String cadaCuantoFumiga) {
        this.cadaCuantoFumiga = cadaCuantoFumiga;
    }

    public String getMiembroFamConZikaSn() {
        return miembroFamConZikaSn;
    }

    public void setMiembroFamConZikaSn(String miembroFamConZikaSn) {
        this.miembroFamConZikaSn = miembroFamConZikaSn;
    }

    public String getCantMiembrosZika() {
        return cantMiembrosZika;
    }

    public void setCantMiembrosZika(String cantMiembrosZika) {
        this.cantMiembrosZika = cantMiembrosZika;
    }

    public String getFechaDxZika() {
        return fechaDxZika;
    }

    public void setFechaDxZika(String fechaDxZika) {
        this.fechaDxZika = fechaDxZika;
    }

    public String getRelacionFamZika() {
        return relacionFamZika;
    }

    public void setRelacionFamZika(String relacionFamZika) {
        this.relacionFamZika = relacionFamZika;
    }

    public String getOtraRelacionFamZika() {
        return otraRelacionFamZika;
    }

    public void setOtraRelacionFamZika(String otraRelacionFamZika) {
        this.otraRelacionFamZika = otraRelacionFamZika;
    }

    public String getMiembroFamConDengueSn() {
        return miembroFamConDengueSn;
    }

    public void setMiembroFamConDengueSn(String miembroFamConDengueSn) {
        this.miembroFamConDengueSn = miembroFamConDengueSn;
    }

    public String getCantMiembrosDengue() {
        return cantMiembrosDengue;
    }

    public void setCantMiembrosDengue(String cantMiembrosDengue) {
        this.cantMiembrosDengue = cantMiembrosDengue;
    }

    public String getFechaDxDengue() {
        return fechaDxDengue;
    }

    public void setFechaDxDengue(String fechaDxDengue) {
        this.fechaDxDengue = fechaDxDengue;
    }

    public String getRelacionFamDengue() {
        return relacionFamDengue;
    }

    public void setRelacionFamDengue(String relacionFamDengue) {
        this.relacionFamDengue = relacionFamDengue;
    }

    public String getOtraRelacionFamDengue() {
        return otraRelacionFamDengue;
    }

    public void setOtraRelacionFamDengue(String otraRelacionFamDengue) {
        this.otraRelacionFamDengue = otraRelacionFamDengue;
    }

    public String getMiembroFamConChikSn() {
        return miembroFamConChikSn;
    }

    public void setMiembroFamConChikSn(String miembroFamConChikSn) {
        this.miembroFamConChikSn = miembroFamConChikSn;
    }

    public String getCantMiembrosChik() {
        return cantMiembrosChik;
    }

    public void setCantMiembrosChik(String cantMiembrosChik) {
        this.cantMiembrosChik = cantMiembrosChik;
    }

    public String getFechaDxChik() {
        return fechaDxChik;
    }

    public void setFechaDxChik(String fechaDxChik) {
        this.fechaDxChik = fechaDxChik;
    }

    public String getRelacionFamChik() {
        return relacionFamChik;
    }

    public void setRelacionFamChik(String relacionFamChik) {
        this.relacionFamChik = relacionFamChik;
    }

    public String getOtraRelacionFamChik() {
        return otraRelacionFamChik;
    }

    public void setOtraRelacionFamChik(String otraRelacionFamChik) {
        this.otraRelacionFamChik = otraRelacionFamChik;
    }
}
