package ni.org.ics.estudios.appmovil.muestreoanual.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 06/06/2020.
 * V1.0
 */
public class ConsFluCEIRSFormLabels {


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

    private String aceptaCohorteFLuParteF;
    private String aceptaCohorteFLuParteFHint;
    protected String razonNoAceptaParteF;
    protected String otraRazonNoAceptaParteF;
    protected String razonNoAceptaParteFHint;
    protected String otraRazonNoAceptaParteFHint;

    private String aceptaContactoFuturo;
    private String aceptaContactoFuturoHint;
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
    private String telefono1SN;
    private String telefonoClasif1;
    private String telefonoCel1;
    private String telefonoOper1;
    private String telefono2SN;
    private String telefonoClasif2;
    private String telefonoCel2;
    private String telefonoOper2;
    private String telefono3SN;
    private String telefonoClasif3;
    private String telefonoCel3;
    private String telefonoOper3;
    private String nomContacto;
    private String barrioContacto;
    private String otrobarrioContacto;
    private String direContacto;
    private String telContacto1SN;
    private String telContacto1;
    private String telContactoCel1;
    private String telContactoOper1;
    private String telContacto2SN;
    private String telContactoClasif2;
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
    private String tutor;
    private String padre;
    private String madre;
    private String domicilio;
    private String notaCmDomicilio;

    private String noCumpleIncDen;

    public ConsFluCEIRSFormLabels() {
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

        aceptaCohorteFLuParteF = res.getString(R.string.aceptaParteFCEIRS);
        aceptaCohorteFLuParteFHint = res.getString(R.string.aceptaParteFCEIRSHint);

        razonNoAceptaParteF = res.getString(R.string.razonNoAceptaParteFCEIRS);
        razonNoAceptaParteFHint = res.getString(R.string.razonNoAceptaParteFCEIRSHint);
        otraRazonNoAceptaParteF = res.getString(R.string.otraRazonNoAceptaParteFCEIRS);
        otraRazonNoAceptaParteFHint = res.getString(R.string.otraRazonNoAceptaParteFCEIRSHint);


        asentimiento = res.getString(R.string.asentimientoVerbal);
        asentimientoHint = res.getString(R.string.asentimientoCovid19Hint);
        noAsentimiento = res.getString(R.string.noAsentimiento);
        aceptaContactoFuturo = res.getString(R.string.aceptaContactoFuturo);
        aceptaContactoFuturoHint = res.getString(R.string.aceptaContactoFuturoHint);

        tutor = res.getString(R.string.tutor);
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

        domicilio = res.getString(R.string.domicilio);
        cmDomicilio = res.getString(R.string.cmDomicilio);
        notaCmDomicilio = res.getString(R.string.notaCmDomicilio);

        telefono1SN = res.getString(R.string.telefono1SN);
        telefonoClasif1 = res.getString(R.string.telefonoClasif1);
        telefonoCel1 = res.getString(R.string.telefonoCel1);
        telefonoOper1 = res.getString(R.string.telefonoOper1);
        telefono2SN = res.getString(R.string.telefono2SN);
        telefonoClasif2 = res.getString(R.string.telefonoClasif2);
        telefonoCel2 = res.getString(R.string.telefonoCel2);
        telefonoOper2 = res.getString(R.string.telefonoOper2);
        telefono3SN = res.getString(R.string.telefono3SN);
        telefonoClasif3 = res.getString(R.string.telefonoClasif3);
        telefonoCel3 = res.getString(R.string.telefonoCel3);
        telefonoOper3 = res.getString(R.string.telefonoOper3);
        nomContacto = res.getString(R.string.nomContacto);
        barrioContacto = res.getString(R.string.barrioContacto211);
        otrobarrioContacto = res.getString(R.string.otrobarrioContacto);
        direContacto = res.getString(R.string.direContacto);
        telContacto1SN = res.getString(R.string.telContacto1SN);
        telContacto1 = res.getString(R.string.telContacto1);
        telContactoCel1 = res.getString(R.string.telContactoCel1);
        telContactoOper1 = res.getString(R.string.telContactoOper1);
        telContacto2SN = res.getString(R.string.telContacto2SN);
        telContactoClasif2 = res.getString(R.string.telContactoClasif2);
        telContactoCel2 = res.getString(R.string.telContactoCel2);
        telContactoOper2 = res.getString(R.string.telContactoOper2);

        padre = res.getString(R.string.padre);
        cambiarPadre = res.getString(R.string.cambiarPadre);
        nombrepadre = res.getString(R.string.nombrepadre);
        nombrepadre2 = res.getString(R.string.nombrepadre2);
        apellidopadre = res.getString(R.string.apellidopadre);
        apellidopadre2 = res.getString(R.string.apellidopadre2);

        madre = res.getString(R.string.madre);
        cambiarMadre = res.getString(R.string.cambiarMadre);
        nombremadre = res.getString(R.string.nombremadre);
        nombremadre2 = res.getString(R.string.nombremadre2);
        apellidomadre = res.getString(R.string.apellidomadre);
        apellidomadre2 = res.getString(R.string.apellidomadre2);
        verifTutor = res.getString(R.string.verifTutor);

        noCumpleIncDen = res.getString(R.string.noCumpleIncUO1);

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

    public String getAceptaCohorteFLuParteF() {
        return aceptaCohorteFLuParteF;
    }

    public void setAceptaCohorteFLuParteF(String aceptaCohorteFLuParteF) {
        this.aceptaCohorteFLuParteF = aceptaCohorteFLuParteF;
    }

    public String getAceptaCohorteFLuParteFHint() {
        return aceptaCohorteFLuParteFHint;
    }

    public void setAceptaCohorteFLuParteFHint(String aceptaCohorteFLuParteFHint) {
        this.aceptaCohorteFLuParteFHint = aceptaCohorteFLuParteFHint;
    }

    public String getRazonNoAceptaParteF() {
        return razonNoAceptaParteF;
    }

    public void setRazonNoAceptaParteF(String razonNoAceptaParteF) {
        this.razonNoAceptaParteF = razonNoAceptaParteF;
    }

    public String getOtraRazonNoAceptaParteF() {
        return otraRazonNoAceptaParteF;
    }

    public void setOtraRazonNoAceptaParteF(String otraRazonNoAceptaParteF) {
        this.otraRazonNoAceptaParteF = otraRazonNoAceptaParteF;
    }

    public String getRazonNoAceptaParteFHint() {
        return razonNoAceptaParteFHint;
    }

    public void setRazonNoAceptaParteFHint(String razonNoAceptaParteFHint) {
        this.razonNoAceptaParteFHint = razonNoAceptaParteFHint;
    }

    public String getOtraRazonNoAceptaParteFHint() {
        return otraRazonNoAceptaParteFHint;
    }

    public void setOtraRazonNoAceptaParteFHint(String otraRazonNoAceptaParteFHint) {
        this.otraRazonNoAceptaParteFHint = otraRazonNoAceptaParteFHint;
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

    public String getNoCumpleIncDen() {
        return noCumpleIncDen;
    }

    public void setNoCumpleIncDen(String noCumpleIncDen) {
        this.noCumpleIncDen = noCumpleIncDen;
    }
}
