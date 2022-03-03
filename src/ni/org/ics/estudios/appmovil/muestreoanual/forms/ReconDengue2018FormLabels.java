package ni.org.ics.estudios.appmovil.muestreoanual.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 31/07/2018.
 * V1.0
 */
public class ReconDengue2018FormLabels {


    private String visExit;
    private String razonVisNoExit;
    private String dejoCarta;
    private String personaDejoCarta;
    private String relFamPersonaDejoCarta;
    private String relFamPersonaDejoCartaHint;
    private String noDejoCarta;
    private String personaCasa;
    private String personaCasaHint;
    private String relacionFamPersonaCasa;
    private String relacionFamPersonaCasaHint;
    private String otraRelacionPersonaCasa;
    private String telefonoPersonaCasa;
    protected String aceptaParticipar;
    protected String razonNoAceptaDengue;
    protected String otraRazonNoAceptaDengue;
    protected String razonNoAceptaDengueHint;
    protected String otraRazonNoAceptaDengueHint;
    private String emancipado;
    private String razonEmancipacion;
    private String otraRazonEmancipacion;
    private String incDen;
    private String incDenHint;
    private String noCumpleIncDen;
    private String vivienda;
    private String tiempoResidencia;
    private String aceptaAtenderCentro;
    private String aceptaAtenderCentroHint;
    //private String excDen;
    //private String excDenHint;
    //private String noCumpleExcDen;
    private String enfCronSN;
    private String enfCronica;
    private String oEnfCronica;
    private String enfCronicaAnio1;
    private String enfCronicaMes1;
    private String enfCronicaAnio2;
    private String enfCronicaMes2;
    private String enfCronicaAnio3;
    private String enfCronicaMes3;
    private String enfCronicaAnio4;
    private String enfCronicaMes4;
    private String enfCronicaAnio5;
    private String enfCronicaMes5;
    private String enfCronicaAnio6;
    private String enfCronicaMes6;
    private String enfCronicaAnio7;
    private String enfCronicaMes7;
    private String enfCronicaAnio8;
    private String enfCronicaMes8;
    private String enfCronicaAnio9;
    private String enfCronicaMes9;
    private String enfCronicaAnio10;
    private String enfCronicaMes10;
    private String enfCronicaAnio11;
    private String enfCronicaMes11;
    private String enfCronicaAnio12;
    private String enfCronicaMes12;
    private String enfCronicaAnio13;
    private String enfCronicaMes13;
    private String enfCronicaAnio14;
    private String enfCronicaMes14;
    private String tomaTx;
    private String cualesTx;
    private String otroTx;
    private String asiste;
    private String ocentrosalud;
    private String puestosalud;
    private String parteADen;
    private String rechDen;
    private String otroRechDen;
    private String aceptaContactoFuturo;
    private String aceptaContactoFuturoHint;
    private String parteBDen;
    private String parteBDenHint;
    private String parteCDen;
    private String parteCDenHint;
    //MA2022. Version de Carta no incluye parte D. Solo A, B, C
    //private String parteDDen;
    //private String parteDDenHint;
    //private String rechDenExtEdad;
    //private String otroRechDenExtEdad;
    private String asentimiento;
    private String asentimientoHint;
    private String noAsentimiento;
    private String nombrept;
    private String nombrept2;
    private String apellidopt;
    private String apellidopt2;
    private String relacionFam;
    private String otraRelacionFam;
    private String mismoTutorSN;
    private String motivoDifTutor;
    private String otroMotivoDifTutor;
    private String alfabetoTutor;
    private String testigoSN;
    private String testigoSNHint;
    private String nombretest1;
    private String nombretest2;
    private String apellidotest1;
    private String apellidotest2;
    private String cmDomicilio;
    //private String barrio;
    //private String otrobarrio;
    //private String dire;
    //private String autsup;
    //private String autsupHint;
    private String telefono1SN;
    private String telefonoClasif1;
    //private String telefonoConv1;
    private String telefonoCel1;
    private String telefonoOper1;
    private String telefono2SN;
    private String telefonoClasif2;
    //private String telefonoConv2;
    private String telefonoCel2;
    private String telefonoOper2;
    private String telefono3SN;
    private String telefonoClasif3;
    //private String telefonoConv3;
    private String telefonoCel3;
    private String telefonoOper3;
    private String cambiarJefe;
    private String jefenom;
    private String jefenom2;
    private String jefeap;
    private String jefeap2;
    private String nomContacto;
    private String barrioContacto;
    private String otrobarrioContacto;
    private String direContacto;
    private String telContacto1SN;
    private String telContacto1;
    //private String telContactoConv1;
    private String telContactoCel1;
    private String telContactoOper1;
    private String telContacto2SN;
    private String telContactoClasif2;
    //private String telContactoConv2;
    private String telContactoCel2;
    private String telContactoOper2;
    private String cambiarPadre;
    private String nombrepadre;
    private String nombrepadre2;
    private String apellidopadre;
    private String apellidopadre2;
    private String cambiarMadre;
    private String nombremadre;
    private String nombremadre2;
    private String apellidomadre;
    private String apellidomadre2;
    private String verifTutor;
    //private String georef;
    //private String Manzana;
    //private String georef_razon;
    //private String georef_razonHint;
    //private String georef_otraRazon;
    //private String georef_otraRazonHint;
    private String tutor;
    private String jefeFam;
    private String padre;
    private String madre;
    private String domicilio;
    private String notaCmDomicilio;

    public ReconDengue2018FormLabels() {
        Resources res = MyIcsApplication.getContext().getResources();
        visExit = res.getString(R.string.visExit);
        razonVisNoExit = res.getString(R.string.razonVisNoExit);
        dejoCarta = res.getString(R.string.dejoCarta);
        personaDejoCarta = res.getString(R.string.personaDejoCarta);
        relFamPersonaDejoCarta = res.getString(R.string.relFamPersonaDejoCarta);
        relFamPersonaDejoCartaHint = res.getString(R.string.relFamPersonaDejoCartaHint);
        noDejoCarta = res.getString(R.string.noDejoCarta);
        personaCasa = res.getString(R.string.personaCasa);
        personaCasaHint = res.getString(R.string.personaCasaHint);
        relacionFamPersonaCasa = res.getString(R.string.relacionFamPersonaCasa);
        relacionFamPersonaCasaHint = res.getString(R.string.relacionFamPersonaCasaHint);
        otraRelacionPersonaCasa = res.getString(R.string.otraRelacionPersonaCasa);
        telefonoPersonaCasa = res.getString(R.string.telefonoPersonaCasa);
        aceptaParticipar = res.getString(R.string.aceptaCohorteDengueRC2018);
        razonNoAceptaDengue = res.getString(R.string.razonNoAceptaDengue);
        razonNoAceptaDengueHint = res.getString(R.string.razonNoAceptaDengueHint);
        otraRazonNoAceptaDengue = res.getString(R.string.otraRazonNoAceptaDengue);
        otraRazonNoAceptaDengueHint = res.getString(R.string.otraRazonNoAceptaDengueHint);
        emancipado = res.getString(R.string.emancipado2);
        razonEmancipacion = res.getString(R.string.razonEmancipacion);
        otraRazonEmancipacion = res.getString(R.string.otraRazonEmancipacion);
        incDen = res.getString(R.string.incDen);
        incDenHint = res.getString(R.string.incDenHint);
        noCumpleIncDen = res.getString(R.string.noCumpleIncDen);
        //excDen = res.getString(R.string.excDen);
        //excDenHint = res.getString(R.string.excDenHint);
        //noCumpleExcDen = res.getString(R.string.noCumpleExcDen);
        vivienda = res.getString(R.string.vivienda);
        tiempoResidencia = res.getString(R.string.tiempoResid);
        aceptaAtenderCentro = res.getString(R.string.aceptaAtenderCentroRC2018);
        aceptaAtenderCentroHint = res.getString(R.string.aceptaAtenderCentroHint);
        enfCronSN = res.getString(R.string.enfCronSN);
        enfCronica = res.getString(R.string.enfCronica);
        oEnfCronica = res.getString(R.string.oEnfCronica);
        enfCronicaAnio1 = res.getString(R.string.enfCronicaAnio1);
        enfCronicaMes1 = res.getString(R.string.enfCronicaMes1);
        enfCronicaAnio2 = res.getString(R.string.enfCronicaAnio2);
        enfCronicaMes2 = res.getString(R.string.enfCronicaMes2);
        enfCronicaAnio3 = res.getString(R.string.enfCronicaAnio3);
        enfCronicaMes3 = res.getString(R.string.enfCronicaMes3);
        enfCronicaAnio4 = res.getString(R.string.enfCronicaAnio4);
        enfCronicaMes4 = res.getString(R.string.enfCronicaMes4);
        enfCronicaAnio5 = res.getString(R.string.enfCronicaAnio5);
        enfCronicaMes5 = res.getString(R.string.enfCronicaMes5);
        enfCronicaAnio6 = res.getString(R.string.enfCronicaAnio6);
        enfCronicaMes6 = res.getString(R.string.enfCronicaMes6);
        enfCronicaAnio7 = res.getString(R.string.enfCronicaAnio7);
        enfCronicaMes7 = res.getString(R.string.enfCronicaMes7);
        enfCronicaAnio8 = res.getString(R.string.enfCronicaAnio8);
        enfCronicaMes8 = res.getString(R.string.enfCronicaMes8);
        enfCronicaAnio9 = res.getString(R.string.enfCronicaAnio9);
        enfCronicaMes9 = res.getString(R.string.enfCronicaMes9);
        enfCronicaAnio10 = res.getString(R.string.enfCronicaAnio10);
        enfCronicaMes10 = res.getString(R.string.enfCronicaMes10);
        enfCronicaAnio11 = res.getString(R.string.enfCronicaAnio11);
        enfCronicaMes11 = res.getString(R.string.enfCronicaMes11);
        enfCronicaAnio12 = res.getString(R.string.enfCronicaAnio12);
        enfCronicaMes12 = res.getString(R.string.enfCronicaMes12);
        enfCronicaAnio13 = res.getString(R.string.enfCronicaAnio13);
        enfCronicaMes13 = res.getString(R.string.enfCronicaMes13);
        enfCronicaAnio14 = res.getString(R.string.enfCronicaAnio14);
        enfCronicaMes14 = res.getString(R.string.enfCronicaMes14);

        tomaTx = res.getString(R.string.tomaTx);
        cualesTx = res.getString(R.string.cualesTx);
        otroTx = res.getString(R.string.otroTx);
        asiste = res.getString(R.string.asiste);
        ocentrosalud = res.getString(R.string.ocentrosalud);
        puestosalud = res.getString(R.string.puestosalud);
        asentimiento = res.getString(R.string.asentimiento);
        asentimientoHint = res.getString(R.string.asentimientoHint);
        noAsentimiento = res.getString(R.string.noAsentimiento);
        parteADen = res.getString(R.string.parteADen);
        aceptaContactoFuturo = res.getString(R.string.aceptaContactoFuturoRC2018);
        aceptaContactoFuturoHint = res.getString(R.string.aceptaContactoFuturoHint);
        parteBDen = res.getString(R.string.parteBDen);
        parteBDenHint = res.getString(R.string.parteBDenHint);
        parteCDen = res.getString(R.string.parteCDen);
        parteCDenHint = res.getString(R.string.parteCDenHint);
        /*parteDDen = res.getString(R.string.parteDDen);
        parteDDenHint = res.getString(R.string.parteDDenHint);
        rechDenExtEdad = res.getString(R.string.rechDenExtEdad);
        otroRechDenExtEdad = res.getString(R.string.otroRechDenExtEdad);
        */rechDen = res.getString(R.string.rechDen);
        otroRechDen = res.getString(R.string.otroRechDen);
        nombrept = res.getString(R.string.nombrept);
        nombrept2 = res.getString(R.string.nombrept2);
        apellidopt = res.getString(R.string.apellidopt);
        apellidopt2 = res.getString(R.string.apellidopt2);
        relacionFam = res.getString(R.string.relacionFam);
        otraRelacionFam = res.getString(R.string.otraRelacionFam);
        mismoTutorSN = res.getString(R.string.mismoTutorSN);
        motivoDifTutor = res.getString(R.string.motivoDifTutor);
        otroMotivoDifTutor = res.getString(R.string.otroMotivoDifTutor);
        alfabetoTutor = res.getString(R.string.alfabetoTutor);
        testigoSN = res.getString(R.string.testigoSN);
        testigoSNHint = res.getString(R.string.testigoSNHint);
        nombretest1 = res.getString(R.string.nombretest1);
        nombretest2 = res.getString(R.string.nombretest2);
        apellidotest1 = res.getString(R.string.apellidotest1);
        apellidotest2 = res.getString(R.string.apellidotest2);
        cmDomicilio = res.getString(R.string.cmDomicilio);
        /*barrio = res.getString(R.string.barrioRC2018);
        otrobarrio = res.getString(R.string.otrobarrio);
        dire = res.getString(R.string.dire);
        autsup = res.getString(R.string.autsup);
        autsupHint = res.getString(R.string.autsupHint);
        */telefono1SN = res.getString(R.string.telefono1SN);
        telefonoClasif1 = res.getString(R.string.telefonoClasif1);
        //telefonoConv1 = res.getString(R.string.telefonoConv1);
        telefonoCel1 = res.getString(R.string.telefonoCel1);
        telefonoOper1 = res.getString(R.string.telefonoOper1);
        telefono2SN = res.getString(R.string.telefono2SN);
        telefonoClasif2 = res.getString(R.string.telefonoClasif2);
        //telefonoConv2 = res.getString(R.string.telefonoConv2);
        telefonoCel2 = res.getString(R.string.telefonoCel2);
        telefonoOper2 = res.getString(R.string.telefonoOper2);
        telefono3SN = res.getString(R.string.telefono3SN);
        telefonoClasif3 = res.getString(R.string.telefonoClasif3);
        //telefonoConv3 = res.getString(R.string.telefonoConv3);
        telefonoCel3 = res.getString(R.string.telefonoCel3);
        telefonoOper3 = res.getString(R.string.telefonoOper3);
        cambiarJefe = res.getString(R.string.cambiarJefe);
        jefenom = res.getString(R.string.jefenom);
        jefenom2 = res.getString(R.string.jefenom2);
        jefeap = res.getString(R.string.jefeap);
        jefeap2 = res.getString(R.string.jefeap2);
        nomContacto = res.getString(R.string.nomContacto);
        barrioContacto = res.getString(R.string.barrioContacto211);
        otrobarrioContacto = res.getString(R.string.otrobarrioContacto);
        direContacto = res.getString(R.string.direContacto);
        telContacto1SN = res.getString(R.string.telContacto1SN);
        telContacto1 = res.getString(R.string.telContacto1);
        //telContactoConv1 = res.getString(R.string.telContactoConv1);
        telContactoCel1 = res.getString(R.string.telContactoCel1);
        telContactoOper1 = res.getString(R.string.telContactoOper1);
        telContacto2SN = res.getString(R.string.telContacto2SN);
        telContactoClasif2 = res.getString(R.string.telContactoClasif2);
        //telContactoConv2 = res.getString(R.string.telContactoConv2);
        telContactoCel2 = res.getString(R.string.telContactoCel2);
        telContactoOper2 = res.getString(R.string.telContactoOper2);
        cambiarPadre = res.getString(R.string.cambiarPadre);
        nombrepadre = res.getString(R.string.nombrepadre);
        nombrepadre2 = res.getString(R.string.nombrepadre2);
        apellidopadre = res.getString(R.string.apellidopadre);
        apellidopadre2 = res.getString(R.string.apellidopadre2);
        cambiarMadre = res.getString(R.string.cambiarMadre);
        nombremadre = res.getString(R.string.nombremadre);
        nombremadre2 = res.getString(R.string.nombremadre2);
        apellidomadre = res.getString(R.string.apellidomadre);
        apellidomadre2 = res.getString(R.string.apellidomadre2);
        verifTutor = res.getString(R.string.verifTutor);
        /*georef = res.getString(R.string.georef);
        Manzana = res.getString(R.string.Manzana);
        georef_razon = res.getString(R.string.georef_razon);
        georef_razonHint = res.getString(R.string.georef_razonHint);
        georef_otraRazon = res.getString(R.string.georef_otraRazon);
        georef_otraRazonHint = res.getString(R.string.georef_otraRazonHint);
        */tutor = res.getString(R.string.tutor);
        jefeFam = res.getString(R.string.jefeFam);
        padre = res.getString(R.string.padre);
        madre = res.getString(R.string.madre);
        domicilio = res.getString(R.string.domicilio);
        notaCmDomicilio = res.getString(R.string.notaCmDomicilio);
    }

    public String getVisExit() {
        return visExit;
    }

    public void setVisExit(String visExit) {
        this.visExit = visExit;
    }

    public String getRazonVisNoExit() {
        return razonVisNoExit;
    }

    public void setRazonVisNoExit(String razonVisNoExit) {
        this.razonVisNoExit = razonVisNoExit;
    }

    public String getDejoCarta() {
        return dejoCarta;
    }

    public void setDejoCarta(String dejoCarta) {
        this.dejoCarta = dejoCarta;
    }

    public String getPersonaDejoCarta() {
        return personaDejoCarta;
    }

    public void setPersonaDejoCarta(String personaDejoCarta) {
        this.personaDejoCarta = personaDejoCarta;
    }

    public String getRelFamPersonaDejoCarta() {
        return relFamPersonaDejoCarta;
    }

    public void setRelFamPersonaDejoCarta(String relFamPersonaDejoCarta) {
        this.relFamPersonaDejoCarta = relFamPersonaDejoCarta;
    }

    public String getRelFamPersonaDejoCartaHint() {
        return relFamPersonaDejoCartaHint;
    }

    public void setRelFamPersonaDejoCartaHint(String relFamPersonaDejoCartaHint) {
        this.relFamPersonaDejoCartaHint = relFamPersonaDejoCartaHint;
    }

    public String getNoDejoCarta() {
        return noDejoCarta;
    }

    public void setNoDejoCarta(String noDejoCarta) {
        this.noDejoCarta = noDejoCarta;
    }

    public String getPersonaCasa() {
        return personaCasa;
    }

    public void setPersonaCasa(String personaCasa) {
        this.personaCasa = personaCasa;
    }

    public String getPersonaCasaHint() {
        return personaCasaHint;
    }

    public void setPersonaCasaHint(String personaCasaHint) {
        this.personaCasaHint = personaCasaHint;
    }

    public String getRelacionFamPersonaCasa() {
        return relacionFamPersonaCasa;
    }

    public void setRelacionFamPersonaCasa(String relacionFamPersonaCasa) {
        this.relacionFamPersonaCasa = relacionFamPersonaCasa;
    }

    public String getRelacionFamPersonaCasaHint() {
        return relacionFamPersonaCasaHint;
    }

    public void setRelacionFamPersonaCasaHint(String relacionFamPersonaCasaHint) {
        this.relacionFamPersonaCasaHint = relacionFamPersonaCasaHint;
    }

    public String getOtraRelacionPersonaCasa() {
        return otraRelacionPersonaCasa;
    }

    public void setOtraRelacionPersonaCasa(String otraRelacionPersonaCasa) {
        this.otraRelacionPersonaCasa = otraRelacionPersonaCasa;
    }

    public String getTelefonoPersonaCasa() {
        return telefonoPersonaCasa;
    }

    public void setTelefonoPersonaCasa(String telefonoPersonaCasa) {
        this.telefonoPersonaCasa = telefonoPersonaCasa;
    }

    public String getAceptaParticipar() {
        return aceptaParticipar;
    }

    public void setAceptaParticipar(String aceptaParticipar) {
        this.aceptaParticipar = aceptaParticipar;
    }

    public String getRazonNoAceptaDengue() {
        return razonNoAceptaDengue;
    }

    public void setRazonNoAceptaDengue(String razonNoAceptaDengue) {
        this.razonNoAceptaDengue = razonNoAceptaDengue;
    }

    public String getOtraRazonNoAceptaDengue() {
        return otraRazonNoAceptaDengue;
    }

    public void setOtraRazonNoAceptaDengue(String otraRazonNoAceptaDengue) {
        this.otraRazonNoAceptaDengue = otraRazonNoAceptaDengue;
    }

    public String getRazonNoAceptaDengueHint() {
        return razonNoAceptaDengueHint;
    }

    public void setRazonNoAceptaDengueHint(String razonNoAceptaDengueHint) {
        this.razonNoAceptaDengueHint = razonNoAceptaDengueHint;
    }

    public String getOtraRazonNoAceptaDengueHint() {
        return otraRazonNoAceptaDengueHint;
    }

    public void setOtraRazonNoAceptaDengueHint(String otraRazonNoAceptaDengueHint) {
        this.otraRazonNoAceptaDengueHint = otraRazonNoAceptaDengueHint;
    }

    public String getEmancipado() {
        return emancipado;
    }

    public void setEmancipado(String emancipado) {
        this.emancipado = emancipado;
    }

    public String getRazonEmancipacion() {
        return razonEmancipacion;
    }

    public void setRazonEmancipacion(String razonEmancipacion) {
        this.razonEmancipacion = razonEmancipacion;
    }

    public String getOtraRazonEmancipacion() {
        return otraRazonEmancipacion;
    }

    public void setOtraRazonEmancipacion(String otraRazonEmancipacion) {
        this.otraRazonEmancipacion = otraRazonEmancipacion;
    }

    public String getIncDen() {
        return incDen;
    }

    public void setIncDen(String incDen) {
        this.incDen = incDen;
    }

    public String getIncDenHint() {
        return incDenHint;
    }

    public void setIncDenHint(String incDenHint) {
        this.incDenHint = incDenHint;
    }

    public String getNoCumpleIncDen() {
        return noCumpleIncDen;
    }

    public void setNoCumpleIncDen(String noCumpleIncDen) {
        this.noCumpleIncDen = noCumpleIncDen;
    }

    public String getVivienda() {
        return vivienda;
    }

    public void setVivienda(String vivienda) {
        this.vivienda = vivienda;
    }

    public String getTiempoResidencia() {
        return tiempoResidencia;
    }

    public void setTiempoResidencia(String tiempoResidencia) {
        this.tiempoResidencia = tiempoResidencia;
    }

    public String getAceptaAtenderCentro() {
        return aceptaAtenderCentro;
    }

    public void setAceptaAtenderCentro(String aceptaAtenderCentro) {
        this.aceptaAtenderCentro = aceptaAtenderCentro;
    }

    public String getAceptaAtenderCentroHint() {
        return aceptaAtenderCentroHint;
    }

    public void setAceptaAtenderCentroHint(String aceptaAtenderCentroHint) {
        this.aceptaAtenderCentroHint = aceptaAtenderCentroHint;
    }

    public String getEnfCronSN() {
        return enfCronSN;
    }

    public void setEnfCronSN(String enfCronSN) {
        this.enfCronSN = enfCronSN;
    }

    public String getEnfCronica() {
        return enfCronica;
    }

    public void setEnfCronica(String enfCronica) {
        this.enfCronica = enfCronica;
    }

    public String getoEnfCronica() {
        return oEnfCronica;
    }

    public void setoEnfCronica(String oEnfCronica) {
        this.oEnfCronica = oEnfCronica;
    }

    public String getEnfCronicaAnio1() {
        return enfCronicaAnio1;
    }

    public void setEnfCronicaAnio1(String enfCronicaAnio1) {
        this.enfCronicaAnio1 = enfCronicaAnio1;
    }

    public String getEnfCronicaMes1() {
        return enfCronicaMes1;
    }

    public void setEnfCronicaMes1(String enfCronicaMes1) {
        this.enfCronicaMes1 = enfCronicaMes1;
    }

    public String getEnfCronicaAnio2() {
        return enfCronicaAnio2;
    }

    public void setEnfCronicaAnio2(String enfCronicaAnio2) {
        this.enfCronicaAnio2 = enfCronicaAnio2;
    }

    public String getEnfCronicaMes2() {
        return enfCronicaMes2;
    }

    public void setEnfCronicaMes2(String enfCronicaMes2) {
        this.enfCronicaMes2 = enfCronicaMes2;
    }

    public String getEnfCronicaAnio3() {
        return enfCronicaAnio3;
    }

    public void setEnfCronicaAnio3(String enfCronicaAnio3) {
        this.enfCronicaAnio3 = enfCronicaAnio3;
    }

    public String getEnfCronicaMes3() {
        return enfCronicaMes3;
    }

    public void setEnfCronicaMes3(String enfCronicaMes3) {
        this.enfCronicaMes3 = enfCronicaMes3;
    }

    public String getEnfCronicaAnio4() {
        return enfCronicaAnio4;
    }

    public void setEnfCronicaAnio4(String enfCronicaAnio4) {
        this.enfCronicaAnio4 = enfCronicaAnio4;
    }

    public String getEnfCronicaMes4() {
        return enfCronicaMes4;
    }

    public void setEnfCronicaMes4(String enfCronicaMes4) {
        this.enfCronicaMes4 = enfCronicaMes4;
    }

    public String getEnfCronicaAnio5() {
        return enfCronicaAnio5;
    }

    public void setEnfCronicaAnio5(String enfCronicaAnio5) {
        this.enfCronicaAnio5 = enfCronicaAnio5;
    }

    public String getEnfCronicaMes5() {
        return enfCronicaMes5;
    }

    public void setEnfCronicaMes5(String enfCronicaMes5) {
        this.enfCronicaMes5 = enfCronicaMes5;
    }

    public String getEnfCronicaAnio6() {
        return enfCronicaAnio6;
    }

    public void setEnfCronicaAnio6(String enfCronicaAnio6) {
        this.enfCronicaAnio6 = enfCronicaAnio6;
    }

    public String getEnfCronicaMes6() {
        return enfCronicaMes6;
    }

    public void setEnfCronicaMes6(String enfCronicaMes6) {
        this.enfCronicaMes6 = enfCronicaMes6;
    }

    public String getEnfCronicaAnio7() {
        return enfCronicaAnio7;
    }

    public void setEnfCronicaAnio7(String enfCronicaAnio7) {
        this.enfCronicaAnio7 = enfCronicaAnio7;
    }

    public String getEnfCronicaMes7() {
        return enfCronicaMes7;
    }

    public void setEnfCronicaMes7(String enfCronicaMes7) {
        this.enfCronicaMes7 = enfCronicaMes7;
    }

    public String getEnfCronicaAnio8() {
        return enfCronicaAnio8;
    }

    public void setEnfCronicaAnio8(String enfCronicaAnio8) {
        this.enfCronicaAnio8 = enfCronicaAnio8;
    }

    public String getEnfCronicaMes8() {
        return enfCronicaMes8;
    }

    public void setEnfCronicaMes8(String enfCronicaMes8) {
        this.enfCronicaMes8 = enfCronicaMes8;
    }

    public String getEnfCronicaAnio9() {
        return enfCronicaAnio9;
    }

    public void setEnfCronicaAnio9(String enfCronicaAnio9) {
        this.enfCronicaAnio9 = enfCronicaAnio9;
    }

    public String getEnfCronicaMes9() {
        return enfCronicaMes9;
    }

    public void setEnfCronicaMes9(String enfCronicaMes9) {
        this.enfCronicaMes9 = enfCronicaMes9;
    }

    public String getEnfCronicaAnio10() {
        return enfCronicaAnio10;
    }

    public void setEnfCronicaAnio10(String enfCronicaAnio10) {
        this.enfCronicaAnio10 = enfCronicaAnio10;
    }

    public String getEnfCronicaMes10() {
        return enfCronicaMes10;
    }

    public void setEnfCronicaMes10(String enfCronicaMes10) {
        this.enfCronicaMes10 = enfCronicaMes10;
    }

    public String getEnfCronicaAnio11() {
        return enfCronicaAnio11;
    }

    public void setEnfCronicaAnio11(String enfCronicaAnio11) {
        this.enfCronicaAnio11 = enfCronicaAnio11;
    }

    public String getEnfCronicaMes11() {
        return enfCronicaMes11;
    }

    public void setEnfCronicaMes11(String enfCronicaMes11) {
        this.enfCronicaMes11 = enfCronicaMes11;
    }

    public String getEnfCronicaAnio12() {
        return enfCronicaAnio12;
    }

    public void setEnfCronicaAnio12(String enfCronicaAnio12) {
        this.enfCronicaAnio12 = enfCronicaAnio12;
    }

    public String getEnfCronicaMes12() {
        return enfCronicaMes12;
    }

    public void setEnfCronicaMes12(String enfCronicaMes12) {
        this.enfCronicaMes12 = enfCronicaMes12;
    }

    public String getEnfCronicaAnio13() {
        return enfCronicaAnio13;
    }

    public void setEnfCronicaAnio13(String enfCronicaAnio13) {
        this.enfCronicaAnio13 = enfCronicaAnio13;
    }

    public String getEnfCronicaMes13() {
        return enfCronicaMes13;
    }

    public void setEnfCronicaMes13(String enfCronicaMes13) {
        this.enfCronicaMes13 = enfCronicaMes13;
    }

    public String getEnfCronicaAnio14() {
        return enfCronicaAnio14;
    }

    public void setEnfCronicaAnio14(String enfCronicaAnio14) {
        this.enfCronicaAnio14 = enfCronicaAnio14;
    }

    public String getEnfCronicaMes14() {
        return enfCronicaMes14;
    }

    public void setEnfCronicaMes14(String enfCronicaMes14) {
        this.enfCronicaMes14 = enfCronicaMes14;
    }

    public String getTomaTx() {
        return tomaTx;
    }

    public void setTomaTx(String tomaTx) {
        this.tomaTx = tomaTx;
    }

    public String getCualesTx() {
        return cualesTx;
    }

    public void setCualesTx(String cualesTx) {
        this.cualesTx = cualesTx;
    }

    public String getOtroTx() {
        return otroTx;
    }

    public void setOtroTx(String otroTx) {
        this.otroTx = otroTx;
    }

    public String getAsiste() {
        return asiste;
    }

    public void setAsiste(String asiste) {
        this.asiste = asiste;
    }

    public String getOcentrosalud() {
        return ocentrosalud;
    }

    public void setOcentrosalud(String ocentrosalud) {
        this.ocentrosalud = ocentrosalud;
    }

    public String getPuestosalud() {
        return puestosalud;
    }

    public void setPuestosalud(String puestosalud) {
        this.puestosalud = puestosalud;
    }

    public String getParteADen() {
        return parteADen;
    }

    public void setParteADen(String parteADen) {
        this.parteADen = parteADen;
    }

    public String getRechDen() {
        return rechDen;
    }

    public void setRechDen(String rechDen) {
        this.rechDen = rechDen;
    }

    public String getOtroRechDen() {
        return otroRechDen;
    }

    public void setOtroRechDen(String otroRechDen) {
        this.otroRechDen = otroRechDen;
    }

    public String getAceptaContactoFuturo() {
        return aceptaContactoFuturo;
    }

    public void setAceptaContactoFuturo(String aceptaContactoFuturo) {
        this.aceptaContactoFuturo = aceptaContactoFuturo;
    }

    public String getAceptaContactoFuturoHint() {
        return aceptaContactoFuturoHint;
    }

    public void setAceptaContactoFuturoHint(String aceptaContactoFuturoHint) {
        this.aceptaContactoFuturoHint = aceptaContactoFuturoHint;
    }

    public String getParteBDen() {
        return parteBDen;
    }

    public void setParteBDen(String parteBDen) {
        this.parteBDen = parteBDen;
    }

    public String getParteBDenHint() {
        return parteBDenHint;
    }

    public void setParteBDenHint(String parteBDenHint) {
        this.parteBDenHint = parteBDenHint;
    }

    public String getParteCDen() {
        return parteCDen;
    }

    public void setParteCDen(String parteCDen) {
        this.parteCDen = parteCDen;
    }

    public String getParteCDenHint() {
        return parteCDenHint;
    }

    public void setParteCDenHint(String parteCDenHint) {
        this.parteCDenHint = parteCDenHint;
    }
/*
    public String getParteDDen() {
        return parteDDen;
    }

    public void setParteDDen(String parteDDen) {
        this.parteDDen = parteDDen;
    }

    public String getParteDDenHint() {
        return parteDDenHint;
    }

    public void setParteDDenHint(String parteDDenHint) {
        this.parteDDenHint = parteDDenHint;
    }

    public String getRechDenExtEdad() {
        return rechDenExtEdad;
    }

    public void setRechDenExtEdad(String rechDenExtEdad) {
        this.rechDenExtEdad = rechDenExtEdad;
    }

    public String getOtroRechDenExtEdad() {
        return otroRechDenExtEdad;
    }

    public void setOtroRechDenExtEdad(String otroRechDenExtEdad) {
        this.otroRechDenExtEdad = otroRechDenExtEdad;
    }
*/
    public String getAsentimiento() {
        return asentimiento;
    }

    public void setAsentimiento(String asentimiento) {
        this.asentimiento = asentimiento;
    }

    public String getAsentimientoHint() {
        return asentimientoHint;
    }

    public void setAsentimientoHint(String asentimientoHint) {
        this.asentimientoHint = asentimientoHint;
    }

    public String getNoAsentimiento() {
        return noAsentimiento;
    }

    public void setNoAsentimiento(String noAsentimiento) {
        this.noAsentimiento = noAsentimiento;
    }

    public String getNombrept() {
        return nombrept;
    }

    public void setNombrept(String nombrept) {
        this.nombrept = nombrept;
    }

    public String getNombrept2() {
        return nombrept2;
    }

    public void setNombrept2(String nombrept2) {
        this.nombrept2 = nombrept2;
    }

    public String getApellidopt() {
        return apellidopt;
    }

    public void setApellidopt(String apellidopt) {
        this.apellidopt = apellidopt;
    }

    public String getApellidopt2() {
        return apellidopt2;
    }

    public void setApellidopt2(String apellidopt2) {
        this.apellidopt2 = apellidopt2;
    }

    public String getRelacionFam() {
        return relacionFam;
    }

    public void setRelacionFam(String relacionFam) {
        this.relacionFam = relacionFam;
    }

    public String getOtraRelacionFam() {
        return otraRelacionFam;
    }

    public void setOtraRelacionFam(String otraRelacionFam) {
        this.otraRelacionFam = otraRelacionFam;
    }

    public String getMismoTutorSN() {
        return mismoTutorSN;
    }

    public void setMismoTutorSN(String mismoTutorSN) {
        this.mismoTutorSN = mismoTutorSN;
    }

    public String getMotivoDifTutor() {
        return motivoDifTutor;
    }

    public void setMotivoDifTutor(String motivoDifTutor) {
        this.motivoDifTutor = motivoDifTutor;
    }

    public String getOtroMotivoDifTutor() {
        return otroMotivoDifTutor;
    }

    public void setOtroMotivoDifTutor(String otroMotivoDifTutor) {
        this.otroMotivoDifTutor = otroMotivoDifTutor;
    }

    public String getAlfabetoTutor() {
        return alfabetoTutor;
    }

    public void setAlfabetoTutor(String alfabetoTutor) {
        this.alfabetoTutor = alfabetoTutor;
    }

    public String getTestigoSN() {
        return testigoSN;
    }

    public void setTestigoSN(String testigoSN) {
        this.testigoSN = testigoSN;
    }

    public String getTestigoSNHint() {
        return testigoSNHint;
    }

    public void setTestigoSNHint(String testigoSNHint) {
        this.testigoSNHint = testigoSNHint;
    }

    public String getNombretest1() {
        return nombretest1;
    }

    public void setNombretest1(String nombretest1) {
        this.nombretest1 = nombretest1;
    }

    public String getNombretest2() {
        return nombretest2;
    }

    public void setNombretest2(String nombretest2) {
        this.nombretest2 = nombretest2;
    }

    public String getApellidotest1() {
        return apellidotest1;
    }

    public void setApellidotest1(String apellidotest1) {
        this.apellidotest1 = apellidotest1;
    }

    public String getApellidotest2() {
        return apellidotest2;
    }

    public void setApellidotest2(String apellidotest2) {
        this.apellidotest2 = apellidotest2;
    }

    public String getCmDomicilio() {
        return cmDomicilio;
    }

    public void setCmDomicilio(String cmDomicilio) {
        this.cmDomicilio = cmDomicilio;
    }

    public String getTelefono1SN() {
        return telefono1SN;
    }

    public void setTelefono1SN(String telefono1SN) {
        this.telefono1SN = telefono1SN;
    }

    public String getTelefonoClasif1() {
        return telefonoClasif1;
    }

    public void setTelefonoClasif1(String telefonoClasif1) {
        this.telefonoClasif1 = telefonoClasif1;
    }

    public String getTelefonoCel1() {
        return telefonoCel1;
    }

    public void setTelefonoCel1(String telefonoCel1) {
        this.telefonoCel1 = telefonoCel1;
    }

    public String getTelefonoOper1() {
        return telefonoOper1;
    }

    public void setTelefonoOper1(String telefonoOper1) {
        this.telefonoOper1 = telefonoOper1;
    }

    public String getTelefono2SN() {
        return telefono2SN;
    }

    public void setTelefono2SN(String telefono2SN) {
        this.telefono2SN = telefono2SN;
    }

    public String getTelefonoClasif2() {
        return telefonoClasif2;
    }

    public void setTelefonoClasif2(String telefonoClasif2) {
        this.telefonoClasif2 = telefonoClasif2;
    }

    public String getTelefonoCel2() {
        return telefonoCel2;
    }

    public void setTelefonoCel2(String telefonoCel2) {
        this.telefonoCel2 = telefonoCel2;
    }

    public String getTelefonoOper2() {
        return telefonoOper2;
    }

    public void setTelefonoOper2(String telefonoOper2) {
        this.telefonoOper2 = telefonoOper2;
    }

    public String getTelefono3SN() {
        return telefono3SN;
    }

    public void setTelefono3SN(String telefono3SN) {
        this.telefono3SN = telefono3SN;
    }

    public String getTelefonoClasif3() {
        return telefonoClasif3;
    }

    public void setTelefonoClasif3(String telefonoClasif3) {
        this.telefonoClasif3 = telefonoClasif3;
    }

    public String getTelefonoCel3() {
        return telefonoCel3;
    }

    public void setTelefonoCel3(String telefonoCel3) {
        this.telefonoCel3 = telefonoCel3;
    }

    public String getTelefonoOper3() {
        return telefonoOper3;
    }

    public void setTelefonoOper3(String telefonoOper3) {
        this.telefonoOper3 = telefonoOper3;
    }

    public String getCambiarJefe() {
        return cambiarJefe;
    }

    public void setCambiarJefe(String cambiarJefe) {
        this.cambiarJefe = cambiarJefe;
    }

    public String getJefenom() {
        return jefenom;
    }

    public void setJefenom(String jefenom) {
        this.jefenom = jefenom;
    }

    public String getJefenom2() {
        return jefenom2;
    }

    public void setJefenom2(String jefenom2) {
        this.jefenom2 = jefenom2;
    }

    public String getJefeap() {
        return jefeap;
    }

    public void setJefeap(String jefeap) {
        this.jefeap = jefeap;
    }

    public String getJefeap2() {
        return jefeap2;
    }

    public void setJefeap2(String jefeap2) {
        this.jefeap2 = jefeap2;
    }

    public String getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(String nomContacto) {
        this.nomContacto = nomContacto;
    }

    public String getBarrioContacto() {
        return barrioContacto;
    }

    public void setBarrioContacto(String barrioContacto) {
        this.barrioContacto = barrioContacto;
    }

    public String getOtrobarrioContacto() {
        return otrobarrioContacto;
    }

    public void setOtrobarrioContacto(String otrobarrioContacto) {
        this.otrobarrioContacto = otrobarrioContacto;
    }

    public String getDireContacto() {
        return direContacto;
    }

    public void setDireContacto(String direContacto) {
        this.direContacto = direContacto;
    }

    public String getTelContacto1SN() {
        return telContacto1SN;
    }

    public void setTelContacto1SN(String telContacto1SN) {
        this.telContacto1SN = telContacto1SN;
    }

    public String getTelContacto1() {
        return telContacto1;
    }

    public void setTelContacto1(String telContacto1) {
        this.telContacto1 = telContacto1;
    }

    public String getTelContactoCel1() {
        return telContactoCel1;
    }

    public void setTelContactoCel1(String telContactoCel1) {
        this.telContactoCel1 = telContactoCel1;
    }

    public String getTelContactoOper1() {
        return telContactoOper1;
    }

    public void setTelContactoOper1(String telContactoOper1) {
        this.telContactoOper1 = telContactoOper1;
    }

    public String getTelContacto2SN() {
        return telContacto2SN;
    }

    public void setTelContacto2SN(String telContacto2SN) {
        this.telContacto2SN = telContacto2SN;
    }

    public String getTelContactoClasif2() {
        return telContactoClasif2;
    }

    public void setTelContactoClasif2(String telContactoClasif2) {
        this.telContactoClasif2 = telContactoClasif2;
    }

    public String getTelContactoCel2() {
        return telContactoCel2;
    }

    public void setTelContactoCel2(String telContactoCel2) {
        this.telContactoCel2 = telContactoCel2;
    }

    public String getTelContactoOper2() {
        return telContactoOper2;
    }

    public void setTelContactoOper2(String telContactoOper2) {
        this.telContactoOper2 = telContactoOper2;
    }

    public String getCambiarPadre() {
        return cambiarPadre;
    }

    public void setCambiarPadre(String cambiarPadre) {
        this.cambiarPadre = cambiarPadre;
    }

    public String getNombrepadre() {
        return nombrepadre;
    }

    public void setNombrepadre(String nombrepadre) {
        this.nombrepadre = nombrepadre;
    }

    public String getNombrepadre2() {
        return nombrepadre2;
    }

    public void setNombrepadre2(String nombrepadre2) {
        this.nombrepadre2 = nombrepadre2;
    }

    public String getApellidopadre() {
        return apellidopadre;
    }

    public void setApellidopadre(String apellidopadre) {
        this.apellidopadre = apellidopadre;
    }

    public String getApellidopadre2() {
        return apellidopadre2;
    }

    public void setApellidopadre2(String apellidopadre2) {
        this.apellidopadre2 = apellidopadre2;
    }

    public String getCambiarMadre() {
        return cambiarMadre;
    }

    public void setCambiarMadre(String cambiarMadre) {
        this.cambiarMadre = cambiarMadre;
    }

    public String getNombremadre() {
        return nombremadre;
    }

    public void setNombremadre(String nombremadre) {
        this.nombremadre = nombremadre;
    }

    public String getNombremadre2() {
        return nombremadre2;
    }

    public void setNombremadre2(String nombremadre2) {
        this.nombremadre2 = nombremadre2;
    }

    public String getApellidomadre() {
        return apellidomadre;
    }

    public void setApellidomadre(String apellidomadre) {
        this.apellidomadre = apellidomadre;
    }

    public String getApellidomadre2() {
        return apellidomadre2;
    }

    public void setApellidomadre2(String apellidomadre2) {
        this.apellidomadre2 = apellidomadre2;
    }

    public String getVerifTutor() {
        return verifTutor;
    }

    public void setVerifTutor(String verifTutor) {
        this.verifTutor = verifTutor;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getJefeFam() {
        return jefeFam;
    }

    public void setJefeFam(String jefeFam) {
        this.jefeFam = jefeFam;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getMadre() {
        return madre;
    }

    public void setMadre(String madre) {
        this.madre = madre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNotaCmDomicilio() {
        return notaCmDomicilio;
    }

    public void setNotaCmDomicilio(String notaCmDomicilio) {
        this.notaCmDomicilio = notaCmDomicilio;
    }
}
