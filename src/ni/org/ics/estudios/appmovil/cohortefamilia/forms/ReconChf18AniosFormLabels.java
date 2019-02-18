package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 04/02/2019.
 * V1.0
 */
public class ReconChf18AniosFormLabels {

    protected String aceptaTamizajePersona;
    protected String aceptaTamizajePersonaHint;
    protected String razonNoParticipaPersona;
    protected String razonNoParticipaPersonaHint;
    protected String otraRazonNoParticipaPersona;
    protected String otraRazonNoParticipaPersonaHint;
    protected String criteriosInclusion;
    protected String criteriosInclusionHint;
    protected String enfermedad;
    protected String enfermedadHint;
    protected String dondeAsisteProblemasSalud;
    protected String dondeAsisteProblemasSaludHint;
    protected String otroCentroSalud;
    protected String otroCentroSaludHint;
    protected String puestoSalud;
    protected String puestoSaludHint;
    protected String aceptaAtenderCentro;
    protected String aceptaAtenderCentroHint;
    protected String esElegible;
    protected String esElegibleHint;
    protected String aceptaParticipar;
    protected String aceptaParticiparHint;
    protected String razonNoAceptaParticipar;
    protected String razonNoAceptaParticiparHint;
    protected String otraRazonNoAceptaParticipar;
    protected String otraRazonNoAceptaParticiparHint;
    protected String nombre1;
    protected String nombre1Hint;
    protected String nombre2;
    protected String nombre2Hint;
    protected String apellido1;
    protected String apellido1Hint;
    protected String apellido2;
    protected String apellido2Hint;
    protected String participanteOTutorAlfabeto;
    protected String participanteOTutorAlfabetoHint;
    protected String testigoPresente;
    protected String testigoPresenteHint;
    protected String nombre1Testigo;
    protected String nombre1TestigoHint;
    protected String nombre2Testigo;
    protected String nombre2TestigoHint;
    protected String apellido1Testigo;
    protected String apellido1TestigoHint;
    protected String apellido2Testigo;
    protected String apellido2TestigoHint;
    protected String aceptaParteA;
    protected String aceptaParteAHint;
    protected String motivoRechazoParteA;
    protected String motivoRechazoParteAHint;
    protected String aceptaContactoFuturo;
    protected String aceptaContactoFuturoHint;
    protected String aceptaParteB;
    protected String aceptaParteBHint;
    protected String aceptaParteC;
    protected String aceptaParteCHint;
    protected String verifTutor;
    protected String finReconLabel;

    public ReconChf18AniosFormLabels() {
        Resources res = MyIcsApplication.getContext().getResources();

        aceptaTamizajePersona = res.getString(R.string.aceptaTamizajePersona);
        aceptaTamizajePersonaHint = res.getString(R.string.aceptaTamizajePersonaHint);
        razonNoParticipaPersona = res.getString(R.string.razonNoParticipaPersona);
        razonNoParticipaPersonaHint = res.getString(R.string.razonNoParticipaPersonaHint);
        otraRazonNoParticipaPersona = res.getString(R.string.otraRazonNoParticipaPersona);
        otraRazonNoParticipaPersonaHint = res.getString(R.string.otraRazonNoParticipaPersonaHint);
        criteriosInclusion = res.getString(R.string.criteriosInclusion);
        criteriosInclusionHint = res.getString(R.string.criteriosInclusionHint);
        enfermedad = res.getString(R.string.enfermedad);
        enfermedadHint = res.getString(R.string.enfermedadHint);
        dondeAsisteProblemasSalud = res.getString(R.string.dondeAsisteProblemasSalud);
        dondeAsisteProblemasSaludHint = res.getString(R.string.dondeAsisteProblemasSaludHint);
        otroCentroSalud = res.getString(R.string.otroCentroSalud);
        otroCentroSaludHint = res.getString(R.string.otroCentroSaludHint);
        puestoSalud = res.getString(R.string.puestoSalud);
        puestoSaludHint = res.getString(R.string.puestoSaludHint);

        aceptaAtenderCentro = res.getString(R.string.aceptaAtenderCentro);
        aceptaAtenderCentroHint = res.getString(R.string.aceptaAtenderCentroHint);
        esElegible = res.getString(R.string.esElegible);
        esElegibleHint = res.getString(R.string.esElegibleHint);
        aceptaParticipar = res.getString(R.string.aceptaParticipar);
        aceptaParticiparHint = res.getString(R.string.aceptaParticiparHint);
        razonNoAceptaParticipar = res.getString(R.string.razonNoAceptaParticipar);
        razonNoAceptaParticiparHint = res.getString(R.string.razonNoAceptaParticiparHint);
        otraRazonNoAceptaParticipar = res.getString(R.string.otraRazonNoAceptaParticipar);
        otraRazonNoAceptaParticiparHint = res.getString(R.string.otraRazonNoAceptaParticiparHint);
        nombre1 = res.getString(R.string.nombre1);
        nombre1Hint = res.getString(R.string.nombre1Hint);
        nombre2 = res.getString(R.string.nombre2);
        nombre2Hint = res.getString(R.string.nombre2Hint);
        apellido1 = res.getString(R.string.apellido1);
        apellido1Hint = res.getString(R.string.apellido1Hint);
        apellido2 = res.getString(R.string.apellido2);
        apellido2Hint = res.getString(R.string.apellido2Hint);

        participanteOTutorAlfabeto = res.getString(R.string.participanteOTutorAlfabeto);
        participanteOTutorAlfabetoHint = res.getString(R.string.participanteOTutorAlfabetoHint);
        testigoPresente = res.getString(R.string.testigoPresente);
        testigoPresenteHint = res.getString(R.string.testigoPresenteHint);
        nombre1Testigo = res.getString(R.string.nombre1Testigo);
        nombre1TestigoHint = res.getString(R.string.nombre1TestigoHint);
        nombre2Testigo = res.getString(R.string.nombre2Testigo);
        nombre2TestigoHint = res.getString(R.string.nombre2TestigoHint);
        apellido1Testigo = res.getString(R.string.apellido1Testigo);
        apellido1TestigoHint = res.getString(R.string.apellido1TestigoHint);
        apellido2Testigo = res.getString(R.string.apellido2Testigo);
        apellido2TestigoHint = res.getString(R.string.apellido2TestigoHint);

        aceptaParteA = res.getString(R.string.aceptaParteA);
        aceptaParteAHint = res.getString(R.string.aceptaParteAHint);
        motivoRechazoParteA = res.getString(R.string.motivoRechazoParteA);
        motivoRechazoParteAHint = res.getString(R.string.motivoRechazoParteAHint);
        aceptaContactoFuturo = res.getString(R.string.aceptaContactoFuturo);
        aceptaContactoFuturoHint = res.getString(R.string.aceptaContactoFuturoHint);
        aceptaParteB = res.getString(R.string.aceptaParteB);
        aceptaParteBHint = res.getString(R.string.aceptaParteBHint);
        aceptaParteC = res.getString(R.string.aceptaParteC);
        aceptaParteCHint = res.getString(R.string.aceptaParteCHint);

        verifTutor = res.getString(R.string.verifTutor).replaceAll("25. ","");

        finReconLabel = res.getString(R.string.finTamizajeLabel);

    }

    public String getAceptaTamizajePersona() {
        return aceptaTamizajePersona;
    }

    public void setAceptaTamizajePersona(String aceptaTamizajePersona) {
        this.aceptaTamizajePersona = aceptaTamizajePersona;
    }

    public String getAceptaTamizajePersonaHint() {
        return aceptaTamizajePersonaHint;
    }

    public void setAceptaTamizajePersonaHint(String aceptaTamizajePersonaHint) {
        this.aceptaTamizajePersonaHint = aceptaTamizajePersonaHint;
    }

    public String getRazonNoParticipaPersona() {
        return razonNoParticipaPersona;
    }

    public void setRazonNoParticipaPersona(String razonNoParticipaPersona) {
        this.razonNoParticipaPersona = razonNoParticipaPersona;
    }

    public String getRazonNoParticipaPersonaHint() {
        return razonNoParticipaPersonaHint;
    }

    public void setRazonNoParticipaPersonaHint(String razonNoParticipaPersonaHint) {
        this.razonNoParticipaPersonaHint = razonNoParticipaPersonaHint;
    }

    public String getOtraRazonNoParticipaPersona() {
        return otraRazonNoParticipaPersona;
    }

    public void setOtraRazonNoParticipaPersona(String otraRazonNoParticipaPersona) {
        this.otraRazonNoParticipaPersona = otraRazonNoParticipaPersona;
    }

    public String getOtraRazonNoParticipaPersonaHint() {
        return otraRazonNoParticipaPersonaHint;
    }

    public void setOtraRazonNoParticipaPersonaHint(String otraRazonNoParticipaPersonaHint) {
        this.otraRazonNoParticipaPersonaHint = otraRazonNoParticipaPersonaHint;
    }

    public String getCriteriosInclusion() {
        return criteriosInclusion;
    }

    public void setCriteriosInclusion(String criteriosInclusion) {
        this.criteriosInclusion = criteriosInclusion;
    }

    public String getCriteriosInclusionHint() {
        return criteriosInclusionHint;
    }

    public void setCriteriosInclusionHint(String criteriosInclusionHint) {
        this.criteriosInclusionHint = criteriosInclusionHint;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getEnfermedadHint() {
        return enfermedadHint;
    }

    public void setEnfermedadHint(String enfermedadHint) {
        this.enfermedadHint = enfermedadHint;
    }

    public String getDondeAsisteProblemasSalud() {
        return dondeAsisteProblemasSalud;
    }

    public void setDondeAsisteProblemasSalud(String dondeAsisteProblemasSalud) {
        this.dondeAsisteProblemasSalud = dondeAsisteProblemasSalud;
    }

    public String getDondeAsisteProblemasSaludHint() {
        return dondeAsisteProblemasSaludHint;
    }

    public void setDondeAsisteProblemasSaludHint(String dondeAsisteProblemasSaludHint) {
        this.dondeAsisteProblemasSaludHint = dondeAsisteProblemasSaludHint;
    }

    public String getOtroCentroSalud() {
        return otroCentroSalud;
    }

    public void setOtroCentroSalud(String otroCentroSalud) {
        this.otroCentroSalud = otroCentroSalud;
    }

    public String getOtroCentroSaludHint() {
        return otroCentroSaludHint;
    }

    public void setOtroCentroSaludHint(String otroCentroSaludHint) {
        this.otroCentroSaludHint = otroCentroSaludHint;
    }

    public String getPuestoSalud() {
        return puestoSalud;
    }

    public void setPuestoSalud(String puestoSalud) {
        this.puestoSalud = puestoSalud;
    }

    public String getPuestoSaludHint() {
        return puestoSaludHint;
    }

    public void setPuestoSaludHint(String puestoSaludHint) {
        this.puestoSaludHint = puestoSaludHint;
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

    public String getEsElegible() {
        return esElegible;
    }

    public void setEsElegible(String esElegible) {
        this.esElegible = esElegible;
    }

    public String getEsElegibleHint() {
        return esElegibleHint;
    }

    public void setEsElegibleHint(String esElegibleHint) {
        this.esElegibleHint = esElegibleHint;
    }

    public String getAceptaParticipar() {
        return aceptaParticipar;
    }

    public void setAceptaParticipar(String aceptaParticipar) {
        this.aceptaParticipar = aceptaParticipar;
    }

    public String getAceptaParticiparHint() {
        return aceptaParticiparHint;
    }

    public void setAceptaParticiparHint(String aceptaParticiparHint) {
        this.aceptaParticiparHint = aceptaParticiparHint;
    }

    public String getRazonNoAceptaParticipar() {
        return razonNoAceptaParticipar;
    }

    public void setRazonNoAceptaParticipar(String razonNoAceptaParticipar) {
        this.razonNoAceptaParticipar = razonNoAceptaParticipar;
    }

    public String getRazonNoAceptaParticiparHint() {
        return razonNoAceptaParticiparHint;
    }

    public void setRazonNoAceptaParticiparHint(String razonNoAceptaParticiparHint) {
        this.razonNoAceptaParticiparHint = razonNoAceptaParticiparHint;
    }

    public String getOtraRazonNoAceptaParticipar() {
        return otraRazonNoAceptaParticipar;
    }

    public void setOtraRazonNoAceptaParticipar(String otraRazonNoAceptaParticipar) {
        this.otraRazonNoAceptaParticipar = otraRazonNoAceptaParticipar;
    }

    public String getOtraRazonNoAceptaParticiparHint() {
        return otraRazonNoAceptaParticiparHint;
    }

    public void setOtraRazonNoAceptaParticiparHint(String otraRazonNoAceptaParticiparHint) {
        this.otraRazonNoAceptaParticiparHint = otraRazonNoAceptaParticiparHint;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre1Hint() {
        return nombre1Hint;
    }

    public void setNombre1Hint(String nombre1Hint) {
        this.nombre1Hint = nombre1Hint;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getNombre2Hint() {
        return nombre2Hint;
    }

    public void setNombre2Hint(String nombre2Hint) {
        this.nombre2Hint = nombre2Hint;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido1Hint() {
        return apellido1Hint;
    }

    public void setApellido1Hint(String apellido1Hint) {
        this.apellido1Hint = apellido1Hint;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getApellido2Hint() {
        return apellido2Hint;
    }

    public void setApellido2Hint(String apellido2Hint) {
        this.apellido2Hint = apellido2Hint;
    }

    public String getParticipanteOTutorAlfabeto() {
        return participanteOTutorAlfabeto;
    }

    public void setParticipanteOTutorAlfabeto(String participanteOTutorAlfabeto) {
        this.participanteOTutorAlfabeto = participanteOTutorAlfabeto;
    }

    public String getParticipanteOTutorAlfabetoHint() {
        return participanteOTutorAlfabetoHint;
    }

    public void setParticipanteOTutorAlfabetoHint(String participanteOTutorAlfabetoHint) {
        this.participanteOTutorAlfabetoHint = participanteOTutorAlfabetoHint;
    }

    public String getTestigoPresente() {
        return testigoPresente;
    }

    public void setTestigoPresente(String testigoPresente) {
        this.testigoPresente = testigoPresente;
    }

    public String getTestigoPresenteHint() {
        return testigoPresenteHint;
    }

    public void setTestigoPresenteHint(String testigoPresenteHint) {
        this.testigoPresenteHint = testigoPresenteHint;
    }

    public String getNombre1Testigo() {
        return nombre1Testigo;
    }

    public void setNombre1Testigo(String nombre1Testigo) {
        this.nombre1Testigo = nombre1Testigo;
    }

    public String getNombre1TestigoHint() {
        return nombre1TestigoHint;
    }

    public void setNombre1TestigoHint(String nombre1TestigoHint) {
        this.nombre1TestigoHint = nombre1TestigoHint;
    }

    public String getNombre2Testigo() {
        return nombre2Testigo;
    }

    public void setNombre2Testigo(String nombre2Testigo) {
        this.nombre2Testigo = nombre2Testigo;
    }

    public String getNombre2TestigoHint() {
        return nombre2TestigoHint;
    }

    public void setNombre2TestigoHint(String nombre2TestigoHint) {
        this.nombre2TestigoHint = nombre2TestigoHint;
    }

    public String getApellido1Testigo() {
        return apellido1Testigo;
    }

    public void setApellido1Testigo(String apellido1Testigo) {
        this.apellido1Testigo = apellido1Testigo;
    }

    public String getApellido1TestigoHint() {
        return apellido1TestigoHint;
    }

    public void setApellido1TestigoHint(String apellido1TestigoHint) {
        this.apellido1TestigoHint = apellido1TestigoHint;
    }

    public String getApellido2Testigo() {
        return apellido2Testigo;
    }

    public void setApellido2Testigo(String apellido2Testigo) {
        this.apellido2Testigo = apellido2Testigo;
    }

    public String getApellido2TestigoHint() {
        return apellido2TestigoHint;
    }

    public void setApellido2TestigoHint(String apellido2TestigoHint) {
        this.apellido2TestigoHint = apellido2TestigoHint;
    }

    public String getAceptaParteA() {
        return aceptaParteA;
    }

    public void setAceptaParteA(String aceptaParteA) {
        this.aceptaParteA = aceptaParteA;
    }

    public String getAceptaParteAHint() {
        return aceptaParteAHint;
    }

    public void setAceptaParteAHint(String aceptaParteAHint) {
        this.aceptaParteAHint = aceptaParteAHint;
    }

    public String getMotivoRechazoParteA() {
        return motivoRechazoParteA;
    }

    public void setMotivoRechazoParteA(String motivoRechazoParteA) {
        this.motivoRechazoParteA = motivoRechazoParteA;
    }

    public String getMotivoRechazoParteAHint() {
        return motivoRechazoParteAHint;
    }

    public void setMotivoRechazoParteAHint(String motivoRechazoParteAHint) {
        this.motivoRechazoParteAHint = motivoRechazoParteAHint;
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

    public String getAceptaParteB() {
        return aceptaParteB;
    }

    public void setAceptaParteB(String aceptaParteB) {
        this.aceptaParteB = aceptaParteB;
    }

    public String getAceptaParteBHint() {
        return aceptaParteBHint;
    }

    public void setAceptaParteBHint(String aceptaParteBHint) {
        this.aceptaParteBHint = aceptaParteBHint;
    }

    public String getAceptaParteC() {
        return aceptaParteC;
    }

    public void setAceptaParteC(String aceptaParteC) {
        this.aceptaParteC = aceptaParteC;
    }

    public String getAceptaParteCHint() {
        return aceptaParteCHint;
    }

    public void setAceptaParteCHint(String aceptaParteCHint) {
        this.aceptaParteCHint = aceptaParteCHint;
    }

    public String getVerifTutor() {
        return verifTutor;
    }

    public void setVerifTutor(String verifTutor) {
        this.verifTutor = verifTutor;
    }

    public String getFinReconLabel() {
        return finReconLabel;
    }

    public void setFinReconLabel(String finReconLabel) {
        this.finReconLabel = finReconLabel;
    }
}
