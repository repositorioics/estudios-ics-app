package ni.org.ics.estudios.appmovil.domain.seroprevalencia;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;

import java.io.Serializable;

/**
 * Created by Miguel Salinas on 5/25/2017.
 * V1.0
 */
public class EncuestaCasaSA extends BaseMetaData implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String codigo;
	private Casa casa;
    private CasaCohorteFamilia casaChf;
    private String sedazoPuertasVentanas;
    private String compraProdEvitarZancudos;
    private String tienePatioJardin;
    private String utilizaAbate;
    private String fumiga;
    private String cadaCuantoFumiga;

    private String miembroFamConZikaSn;
    private Integer cantMiembrosZika;
    private String fechaDxZika;
    private String relacionFamZika;
    private String otraRelacionFamZika;

    private String miembroFamConDengueSn;
    private Integer cantMiembrosDengue;
    private String fechaDxDengue;
    private String relacionFamDengue;
    private String otraRelacionFamDengue;

    private String miembroFamConChikSn;
    private Integer cantMiembrosChik;
    private String fechaDxChik;
    private String relacionFamChik;
    private String otraRelacionFamChik;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
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

    public Integer getCantMiembrosZika() {
        return cantMiembrosZika;
    }

    public void setCantMiembrosZika(Integer cantMiembrosZika) {
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

    public Integer getCantMiembrosDengue() {
        return cantMiembrosDengue;
    }

    public void setCantMiembrosDengue(Integer cantMiembrosDengue) {
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

    public Integer getCantMiembrosChik() {
        return cantMiembrosChik;
    }

    public void setCantMiembrosChik(Integer cantMiembrosChik) {
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

    public CasaCohorteFamilia getCasaChf() {
        return casaChf;
    }

    public void setCasaChf(CasaCohorteFamilia casaChf) {
        this.casaChf = casaChf;
    }

    @Override
    public String toString() {
        return "EncuestaCasa{" + codigo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncuestaCasaSA)) return false;

        EncuestaCasaSA that = (EncuestaCasaSA) o;

        if (!codigo.equals(that.codigo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
