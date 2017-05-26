package ni.org.ics.estudios.appmovil.seroprevalencia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 5/26/2017.
 * V1.0
 */
public class EncuestaParticipanteSAFormLabels {

    protected String escuchadoZikaSn;
    protected String queEsSika;
    protected String otroQueEsSika;
    protected String otroQueEsSikaHint;
    protected String transmiteZika;
    protected String otroTransmiteZika;
    protected String otroTransmiteZikaHint;
    protected String sintomas;
    protected String tenidoZikaSn;
    protected String fechaZika;
    protected String sintomasZika;
    protected String zikaConfirmadoMedico;
    protected String tenidoDengueSn;
    protected String fechaDengue;
    protected String dengueConfirmadoMedico;
    protected String tenidoChikSn;
    protected String fechaChik;
    protected String chikConfirmadoMedico;
    protected String vacunaFiebreAmarillaSn;
    protected String fechaVacunaFiebreAmar;
    protected String transfusionSangreSn;
    protected String fechaTransfusionSangre;
    protected String usaRepelentes;
    protected String conoceLarvas;
    protected String lugaresLarvas;
    protected String otrosLugaresLarvas;
    protected String otrosLugaresLarvasHint;
    protected String tenidoHijos;
    protected String usaPlanificacionFam;
    protected String usaCondon;
    protected String usaOtroMetodo;

    public EncuestaParticipanteSAFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        escuchadoZikaSn = res.getString(R.string.escuchadoZikaSn);
        queEsSika = res.getString(R.string.queEsSika);
        otroQueEsSika = res.getString(R.string.otroQueEsSika);
        otroQueEsSikaHint = res.getString(R.string.otroQueEsSikaHint);
        transmiteZika = res.getString(R.string.transmiteZika);
        otroTransmiteZika = res.getString(R.string.otroTransmiteZika);
        otroTransmiteZikaHint = res.getString(R.string.otroTransmiteZikaHint);
        sintomas = res.getString(R.string.sintomas);
        tenidoZikaSn = res.getString(R.string.tenidoZikaSn);
        fechaZika = res.getString(R.string.fechaZika);
        sintomasZika = res.getString(R.string.sintomasZika);
        zikaConfirmadoMedico = res.getString(R.string.zikaConfirmadoMedico);
        tenidoDengueSn = res.getString(R.string.tenidoDengueSn);
        fechaDengue = res.getString(R.string.fechaDengue);
        dengueConfirmadoMedico = res.getString(R.string.dengueConfirmadoMedico);
        tenidoChikSn = res.getString(R.string.tenidoChikSn);
        fechaChik = res.getString(R.string.fechaChik);
        chikConfirmadoMedico = res.getString(R.string.chikConfirmadoMedico);
        vacunaFiebreAmarillaSn = res.getString(R.string.vacunaFiebreAmarillaSn);
        fechaVacunaFiebreAmar = res.getString(R.string.fechaVacunaFiebreAmar);
        transfusionSangreSn = res.getString(R.string.transfusionSangreSn);
        fechaTransfusionSangre = res.getString(R.string.fechaTransfusionSangre);
        usaRepelentes = res.getString(R.string.usaRepelentes);
        conoceLarvas = res.getString(R.string.conoceLarvas);
        lugaresLarvas = res.getString(R.string.lugaresLarvas);
        otrosLugaresLarvas = res.getString(R.string.otrosLugaresLarvas);
        otrosLugaresLarvasHint = res.getString(R.string.otrosLugaresLarvasHint);
        tenidoHijos = res.getString(R.string.tenidoHijos);
        usaPlanificacionFam = res.getString(R.string.usaPlanificacionFam);
        usaCondon = res.getString(R.string.usaCondon);
        usaOtroMetodo = res.getString(R.string.usaOtroMetodo);
    }

    public String getEscuchadoZikaSn() {
        return escuchadoZikaSn;
    }

    public void setEscuchadoZikaSn(String escuchadoZikaSn) {
        this.escuchadoZikaSn = escuchadoZikaSn;
    }

    public String getQueEsSika() {
        return queEsSika;
    }

    public void setQueEsSika(String queEsSika) {
        this.queEsSika = queEsSika;
    }

    public String getOtroQueEsSika() {
        return otroQueEsSika;
    }

    public void setOtroQueEsSika(String otroQueEsSika) {
        this.otroQueEsSika = otroQueEsSika;
    }

    public String getOtroQueEsSikaHint() {
        return otroQueEsSikaHint;
    }

    public void setOtroQueEsSikaHint(String otroQueEsSikaHint) {
        this.otroQueEsSikaHint = otroQueEsSikaHint;
    }

    public String getTransmiteZika() {
        return transmiteZika;
    }

    public void setTransmiteZika(String transmiteZika) {
        this.transmiteZika = transmiteZika;
    }

    public String getOtroTransmiteZika() {
        return otroTransmiteZika;
    }

    public void setOtroTransmiteZika(String otroTransmiteZika) {
        this.otroTransmiteZika = otroTransmiteZika;
    }

    public String getOtroTransmiteZikaHint() {
        return otroTransmiteZikaHint;
    }

    public void setOtroTransmiteZikaHint(String otroTransmiteZikaHint) {
        this.otroTransmiteZikaHint = otroTransmiteZikaHint;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getTenidoZikaSn() {
        return tenidoZikaSn;
    }

    public void setTenidoZikaSn(String tenidoZikaSn) {
        this.tenidoZikaSn = tenidoZikaSn;
    }

    public String getFechaZika() {
        return fechaZika;
    }

    public void setFechaZika(String fechaZika) {
        this.fechaZika = fechaZika;
    }

    public String getSintomasZika() {
        return sintomasZika;
    }

    public void setSintomasZika(String sintomasZika) {
        this.sintomasZika = sintomasZika;
    }

    public String getZikaConfirmadoMedico() {
        return zikaConfirmadoMedico;
    }

    public void setZikaConfirmadoMedico(String zikaConfirmadoMedico) {
        this.zikaConfirmadoMedico = zikaConfirmadoMedico;
    }

    public String getTenidoDengueSn() {
        return tenidoDengueSn;
    }

    public void setTenidoDengueSn(String tenidoDengueSn) {
        this.tenidoDengueSn = tenidoDengueSn;
    }

    public String getFechaDengue() {
        return fechaDengue;
    }

    public void setFechaDengue(String fechaDengue) {
        this.fechaDengue = fechaDengue;
    }

    public String getDengueConfirmadoMedico() {
        return dengueConfirmadoMedico;
    }

    public void setDengueConfirmadoMedico(String dengueConfirmadoMedico) {
        this.dengueConfirmadoMedico = dengueConfirmadoMedico;
    }

    public String getTenidoChikSn() {
        return tenidoChikSn;
    }

    public void setTenidoChikSn(String tenidoChikSn) {
        this.tenidoChikSn = tenidoChikSn;
    }

    public String getFechaChik() {
        return fechaChik;
    }

    public void setFechaChik(String fechaChik) {
        this.fechaChik = fechaChik;
    }

    public String getChikConfirmadoMedico() {
        return chikConfirmadoMedico;
    }

    public void setChikConfirmadoMedico(String chikConfirmadoMedico) {
        this.chikConfirmadoMedico = chikConfirmadoMedico;
    }

    public String getVacunaFiebreAmarillaSn() {
        return vacunaFiebreAmarillaSn;
    }

    public void setVacunaFiebreAmarillaSn(String vacunaFiebreAmarillaSn) {
        this.vacunaFiebreAmarillaSn = vacunaFiebreAmarillaSn;
    }

    public String getFechaVacunaFiebreAmar() {
        return fechaVacunaFiebreAmar;
    }

    public void setFechaVacunaFiebreAmar(String fechaVacunaFiebreAmar) {
        this.fechaVacunaFiebreAmar = fechaVacunaFiebreAmar;
    }

    public String getTransfusionSangreSn() {
        return transfusionSangreSn;
    }

    public void setTransfusionSangreSn(String transfusionSangreSn) {
        this.transfusionSangreSn = transfusionSangreSn;
    }

    public String getFechaTransfusionSangre() {
        return fechaTransfusionSangre;
    }

    public void setFechaTransfusionSangre(String fechaTransfusionSangre) {
        this.fechaTransfusionSangre = fechaTransfusionSangre;
    }

    public String getUsaRepelentes() {
        return usaRepelentes;
    }

    public void setUsaRepelentes(String usaRepelentes) {
        this.usaRepelentes = usaRepelentes;
    }

    public String getConoceLarvas() {
        return conoceLarvas;
    }

    public void setConoceLarvas(String conoceLarvas) {
        this.conoceLarvas = conoceLarvas;
    }

    public String getLugaresLarvas() {
        return lugaresLarvas;
    }

    public void setLugaresLarvas(String lugaresLarvas) {
        this.lugaresLarvas = lugaresLarvas;
    }

    public String getOtrosLugaresLarvas() {
        return otrosLugaresLarvas;
    }

    public void setOtrosLugaresLarvas(String otrosLugaresLarvas) {
        this.otrosLugaresLarvas = otrosLugaresLarvas;
    }

    public String getOtrosLugaresLarvasHint() {
        return otrosLugaresLarvasHint;
    }

    public void setOtrosLugaresLarvasHint(String otrosLugaresLarvasHint) {
        this.otrosLugaresLarvasHint = otrosLugaresLarvasHint;
    }

    public String getTenidoHijos() {
        return tenidoHijos;
    }

    public void setTenidoHijos(String tenidoHijos) {
        this.tenidoHijos = tenidoHijos;
    }

    public String getUsaPlanificacionFam() {
        return usaPlanificacionFam;
    }

    public void setUsaPlanificacionFam(String usaPlanificacionFam) {
        this.usaPlanificacionFam = usaPlanificacionFam;
    }

    public String getUsaCondon() {
        return usaCondon;
    }

    public void setUsaCondon(String usaCondon) {
        this.usaCondon = usaCondon;
    }

    public String getUsaOtroMetodo() {
        return usaOtroMetodo;
    }

    public void setUsaOtroMetodo(String usaOtroMetodo) {
        this.usaOtroMetodo = usaOtroMetodo;
    }
}
