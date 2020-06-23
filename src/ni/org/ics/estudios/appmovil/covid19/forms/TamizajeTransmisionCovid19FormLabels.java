package ni.org.ics.estudios.appmovil.covid19.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 06/06/2020.
 * V1.0
 */
public class TamizajeTransmisionCovid19FormLabels {


    private String visExit;
    private String razonVisNoExit;
    private String personaCasa;
    private String personaCasaHint;
    private String relacionFamPersonaCasa;
    private String relacionFamPersonaCasaHint;
    private String otraRelacionPersonaCasa;
    private String telefonoPersonaCasa;

    private String aceptaTamizajePersona;
    private String aceptaTamizajePersonaHint;
    private String razonNoParticipaPersona;
    private String razonNoParticipaPersonaHint;
    private String otraRazonNoParticipaPersona;
    private String otraRazonNoParticipaPersonaHint;
    private String criteriosInclusionIndice;
    private String criteriosInclusionMiembro;
    private String criteriosInclusionHint;

    /*private String aceptaParticipar;
    private String razonNoAceptaParticipar;
    private String otraRazonNoAceptaParticipar;
    private String otraRazonNoAceptaParticiparHint;*/
    private String asentimiento;
    private String asentimientoHint;
    private String noAsentimiento;
    private String aceptaParteA;
    private String aceptaParteAIndiceHint;
    private String aceptaParteAMiembroHint;
    private String razonNoAceptaParteA;
    private String otraRazonNoAceptaParteA;
    private String razonNoAceptaParteAHint;
    private String otraRazonNoAceptaParteAHint;
    private String aceptaParteB;
    private String aceptaParteBIndiceHint;
    private String aceptaParteBMiembroHint;
    private String aceptaContactoFuturo;
    private String aceptaContactoFuturoHint;

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

    public TamizajeTransmisionCovid19FormLabels() {
        Resources res = MyIcsApplication.getContext().getResources();
        visExit = res.getString(R.string.visExit);
        razonVisNoExit = res.getString(R.string.razonVisNoExit);

        personaCasa = res.getString(R.string.personaCasa);
        personaCasaHint = res.getString(R.string.personaCasaHint);
        relacionFamPersonaCasa = res.getString(R.string.relacionFamPersonaCasa);
        relacionFamPersonaCasaHint = res.getString(R.string.relacionFamPersonaCasaHint);
        otraRelacionPersonaCasa = res.getString(R.string.otraRelacionPersonaCasa);
        telefonoPersonaCasa = res.getString(R.string.telefonoPersonaCasa);

        aceptaTamizajePersona = res.getString(R.string.aceptaTamizajePersona);
        aceptaTamizajePersonaHint = res.getString(R.string.aceptaTamizajePersonaHint);
        razonNoParticipaPersona = res.getString(R.string.razonNoParticipaPersona);
        razonNoParticipaPersonaHint = res.getString(R.string.razonNoParticipaPersonaHint);
        otraRazonNoParticipaPersona = res.getString(R.string.otraRazonNoParticipaPersona);
        otraRazonNoParticipaPersonaHint = res.getString(R.string.otraRazonNoParticipaPersonaHint);

        criteriosInclusionIndice = res.getString(R.string.criteriosInclusionIndice);
        criteriosInclusionMiembro = res.getString(R.string.criteriosInclusionMiembro);
        criteriosInclusionHint = res.getString(R.string.criteriosInclusionHint);

/*        aceptaParticipar = res.getString(R.string.aceptaParticipar);
        razonNoAceptaParticipar = res.getString(R.string.razonNoAceptaParticipar);
        //razonNoAceptaParticiparHint = res.getString(R.string.razonNoAceptaDengueHint);
        otraRazonNoAceptaParticipar = res.getString(R.string.otraRazonNoAceptaParticipar);
        otraRazonNoAceptaParticiparHint = res.getString(R.string.otraRazonNoAceptaParticiparHint);
*/

        asentimiento = res.getString(R.string.asentimientoVerbal);
        asentimientoHint = res.getString(R.string.asentimientoCovid19Hint);
        noAsentimiento = res.getString(R.string.noAsentimiento);

        aceptaParteA = res.getString(R.string.aceptaParteA);
        aceptaParteAIndiceHint = res.getString(R.string.aceptaParteAIndiceHint);
        aceptaParteAMiembroHint = res.getString(R.string.aceptaParteAMiembroHint);
        razonNoAceptaParteA = res.getString(R.string.razonNoAceptaParteA);
        razonNoAceptaParteAHint = res.getString(R.string.razonNoAceptaParteAHint);
        otraRazonNoAceptaParteA = res.getString(R.string.otraRazonNoAceptaParteACovid);
        otraRazonNoAceptaParteAHint = res.getString(R.string.otraRazonNoAceptaParteACovidHint);

        aceptaContactoFuturo = res.getString(R.string.aceptaContactoFuturo);
        aceptaContactoFuturoHint = res.getString(R.string.aceptaContactoFuturoHint);

        aceptaParteB = res.getString(R.string.aceptaParteB);
        aceptaParteBIndiceHint = res.getString(R.string.aceptaParteBIndiceHint);
        aceptaParteBMiembroHint = res.getString(R.string.aceptaParteBMiembroHint);

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
        alfabetoTutor = res.getString(R.string.participanteOTutorAlfabetoHint);
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

    public String getRazonVisNoExit() {
        return razonVisNoExit;
    }

    public String getPersonaCasa() {
        return personaCasa;
    }

    public String getPersonaCasaHint() {
        return personaCasaHint;
    }

    public String getRelacionFamPersonaCasa() {
        return relacionFamPersonaCasa;
    }

    public String getRelacionFamPersonaCasaHint() {
        return relacionFamPersonaCasaHint;
    }

    public String getOtraRelacionPersonaCasa() {
        return otraRelacionPersonaCasa;
    }

    public String getTelefonoPersonaCasa() {
        return telefonoPersonaCasa;
    }

    public String getAceptaTamizajePersona() {
        return aceptaTamizajePersona;
    }

    public String getAceptaTamizajePersonaHint() {
        return aceptaTamizajePersonaHint;
    }

    public String getRazonNoParticipaPersona() {
        return razonNoParticipaPersona;
    }

    public String getRazonNoParticipaPersonaHint() {
        return razonNoParticipaPersonaHint;
    }

    public String getOtraRazonNoParticipaPersona() {
        return otraRazonNoParticipaPersona;
    }

    public String getOtraRazonNoParticipaPersonaHint() {
        return otraRazonNoParticipaPersonaHint;
    }

    public String getCriteriosInclusionIndice() {
        return criteriosInclusionIndice;
    }

    public String getCriteriosInclusionMiembro() {
        return criteriosInclusionMiembro;
    }

    public String getCriteriosInclusionHint() {
        return criteriosInclusionHint;
    }

    public String getAsentimiento() {
        return asentimiento;
    }

    public String getAsentimientoHint() {
        return asentimientoHint;
    }

    public String getNoAsentimiento() {
        return noAsentimiento;
    }

    public String getAceptaParteA() {
        return aceptaParteA;
    }

    public String getAceptaParteAIndiceHint() {
        return aceptaParteAIndiceHint;
    }

    public String getAceptaParteAMiembroHint() {
        return aceptaParteAMiembroHint;
    }

    public String getRazonNoAceptaParteA() {
        return razonNoAceptaParteA;
    }

    public String getOtraRazonNoAceptaParteA() {
        return otraRazonNoAceptaParteA;
    }

    public String getRazonNoAceptaParteAHint() {
        return razonNoAceptaParteAHint;
    }

    public String getOtraRazonNoAceptaParteAHint() {
        return otraRazonNoAceptaParteAHint;
    }

    public String getAceptaParteB() {
        return aceptaParteB;
    }

    public String getAceptaParteBIndiceHint() {
        return aceptaParteBIndiceHint;
    }

    public String getAceptaParteBMiembroHint() {
        return aceptaParteBMiembroHint;
    }

    public String getAceptaContactoFuturo() {
        return aceptaContactoFuturo;
    }

    public String getAceptaContactoFuturoHint() {
        return aceptaContactoFuturoHint;
    }

    public String getNombrept() {
        return nombrept;
    }

    public String getNombrept2() {
        return nombrept2;
    }

    public String getApellidopt() {
        return apellidopt;
    }

    public String getApellidopt2() {
        return apellidopt2;
    }

    public String getRelacionFam() {
        return relacionFam;
    }

    public String getOtraRelacionFam() {
        return otraRelacionFam;
    }

    public String getMismoTutorSN() {
        return mismoTutorSN;
    }

    public String getMotivoDifTutor() {
        return motivoDifTutor;
    }

    public String getOtroMotivoDifTutor() {
        return otroMotivoDifTutor;
    }

    public String getAlfabetoTutor() {
        return alfabetoTutor;
    }

    public String getTestigoSN() {
        return testigoSN;
    }

    public String getTestigoSNHint() {
        return testigoSNHint;
    }

    public String getNombretest1() {
        return nombretest1;
    }

    public String getNombretest2() {
        return nombretest2;
    }

    public String getApellidotest1() {
        return apellidotest1;
    }

    public String getApellidotest2() {
        return apellidotest2;
    }

    public String getCmDomicilio() {
        return cmDomicilio;
    }

    public String getTelefono1SN() {
        return telefono1SN;
    }

    public String getTelefonoClasif1() {
        return telefonoClasif1;
    }

    public String getTelefonoCel1() {
        return telefonoCel1;
    }

    public String getTelefonoOper1() {
        return telefonoOper1;
    }

    public String getTelefono2SN() {
        return telefono2SN;
    }

    public String getTelefonoClasif2() {
        return telefonoClasif2;
    }

    public String getTelefonoCel2() {
        return telefonoCel2;
    }

    public String getTelefonoOper2() {
        return telefonoOper2;
    }

    public String getTelefono3SN() {
        return telefono3SN;
    }

    public String getTelefonoClasif3() {
        return telefonoClasif3;
    }

    public String getTelefonoCel3() {
        return telefonoCel3;
    }

    public String getTelefonoOper3() {
        return telefonoOper3;
    }

    public String getCambiarPadre() {
        return cambiarPadre;
    }

    public String getNombrepadre() {
        return nombrepadre;
    }

    public String getNombrepadre2() {
        return nombrepadre2;
    }

    public String getApellidopadre() {
        return apellidopadre;
    }

    public String getApellidopadre2() {
        return apellidopadre2;
    }

    public String getCambiarMadre() {
        return cambiarMadre;
    }

    public String getNombremadre() {
        return nombremadre;
    }

    public String getNombremadre2() {
        return nombremadre2;
    }

    public String getApellidomadre() {
        return apellidomadre;
    }

    public String getApellidomadre2() {
        return apellidomadre2;
    }

    public String getVerifTutor() {
        return verifTutor;
    }

    public String getTutor() {
        return tutor;
    }

    public String getPadre() {
        return padre;
    }

    public String getMadre() {
        return madre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getNotaCmDomicilio() {
        return notaCmDomicilio;
    }

    public String getNoCumpleIncDen() {
        return noCumpleIncDen;
    }
}
