package ni.org.ics.estudios.appmovil.seroprevalencia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;


/**
 * Constantes usadas en formulario de consentimiento de seroprevalencia
 * 
 * @author Miguel Salinas
 * 
 */
public class ConsentimientoSAFormLabels {

    private String visExit;
    private String razonVisNoExit;
    private String otraRazonVisitaNoExitosa;
    private String otraRazonVisitaNoExitosaHint;
    private String personaCasa;
    private String personaCasaHint;
    private String relacionFamPersonaCasa;
    private String relacionFamPersonaCasaHint;
    private String otraRelacionPersonaCasa;
    private String telefonoPersonaCasa;

    protected String aceptaTamizajePersona;
    protected String aceptaTamizajePersonaHint;
    protected String razonNoParticipaPersona;
    protected String razonNoParticipaPersonaHint;
    protected String otraRazonNoParticipaPersona;
    protected String otraRazonNoParticipaPersonaHint;
    protected String asentimientoVerbal;
    protected String asentimientoVerbalHint;

    protected String aceptaSeroprevalencia;
    protected String razonNoAceptaSeroprevalencia;
    protected String razonNoAceptaSeroprevalenciaHint;
    protected String otraRazonNoAceptaSeroprevalencia;
    protected String otraRazonNoAceptaSeroprevalenciaHint;

    private String tutor;
    private String mismoTutorSN;
    private String motivoDifTutor;
    private String otroMotivoDifTutor;
    protected String nombre1Tutor;
    protected String nombre1TutorHint;
    protected String nombre2Tutor;
    protected String nombre2TutorHint;
    protected String apellido1Tutor;
    protected String apellido1TutorHint;
    protected String apellido2Tutor;
    protected String apellido2TutorHint;
    protected String relacionFamiliarTutor;
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
    private String verifTutor;
    protected String finTamizajeLabel;
    private String noCumpleIncDen;

	public ConsentimientoSAFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();

        visExit = res.getString(R.string.visExit);
        razonVisNoExit = res.getString(R.string.razonVisNoExit);
        otraRazonVisitaNoExitosa = res.getString(R.string.otraRazonVisitaNoExitosa);
        otraRazonVisitaNoExitosaHint = res.getString(R.string.otraRazonVisitaNoExitosaHint);

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
        asentimientoVerbal = res.getString(R.string.asentimientoVerbal);
        asentimientoVerbalHint = res.getString(R.string.asentimientoVerbalHint);

        aceptaSeroprevalencia = res.getString(R.string.aceptaSeroprevalencia);
        razonNoAceptaSeroprevalencia = res.getString(R.string.razonNoAceptaSeroprevalencia);
        razonNoAceptaSeroprevalenciaHint = res.getString(R.string.razonNoAceptaSeroprevalenciaHint);
        otraRazonNoAceptaSeroprevalencia = res.getString(R.string.otraRazonNoAceptaSeroprevalencia);
        otraRazonNoAceptaSeroprevalenciaHint = res.getString(R.string.otraRazonNoAceptaSeroprevalenciaHint);

        tutor = res.getString(R.string.tutor);
        mismoTutorSN = res.getString(R.string.mismoTutorSN);
        motivoDifTutor = res.getString(R.string.motivoDifTutor);
        otroMotivoDifTutor = res.getString(R.string.otroMotivoDifTutor);
        nombre1Tutor = res.getString(R.string.nombre1Tutor);
        nombre1TutorHint = res.getString(R.string.nombre1TutorHint);
        nombre2Tutor = res.getString(R.string.nombre2Tutor);
        nombre2TutorHint = res.getString(R.string.nombre2TutorHint);
        apellido1Tutor = res.getString(R.string.apellido1Tutor);
        apellido1TutorHint = res.getString(R.string.apellido1TutorHint);
        apellido2Tutor = res.getString(R.string.apellido2Tutor);
        apellido2TutorHint = res.getString(R.string.apellido2TutorHint);
        relacionFamiliarTutor = res.getString(R.string.relacionFamiliarTutor);
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
        verifTutor = res.getString(R.string.verifTutor);
        finTamizajeLabel = res.getString(R.string.finTamizajeLabel);
        noCumpleIncDen = res.getString(R.string.noCumpleIncUO1);
		
	}

    public String getVisExit() {
        return visExit;
    }

    public String getRazonVisNoExit() {
        return razonVisNoExit;
    }

    public String getOtraRazonVisitaNoExitosa() {
        return otraRazonVisitaNoExitosa;
    }

    public String getOtraRazonVisitaNoExitosaHint() {
        return otraRazonVisitaNoExitosaHint;
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

    public String getAsentimientoVerbal() {
        return asentimientoVerbal;
    }

    public String getAsentimientoVerbalHint() {
        return asentimientoVerbalHint;
    }

    public String getAceptaSeroprevalencia() {
        return aceptaSeroprevalencia;
    }

    public String getRazonNoAceptaSeroprevalencia() {
        return razonNoAceptaSeroprevalencia;
    }

    public String getRazonNoAceptaSeroprevalenciaHint() {
        return razonNoAceptaSeroprevalenciaHint;
    }

    public String getOtraRazonNoAceptaSeroprevalencia() {
        return otraRazonNoAceptaSeroprevalencia;
    }

    public String getOtraRazonNoAceptaSeroprevalenciaHint() {
        return otraRazonNoAceptaSeroprevalenciaHint;
    }

    public String getTutor() {
        return tutor;
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

    public String getNombre1Tutor() {
        return nombre1Tutor;
    }

    public String getNombre1TutorHint() {
        return nombre1TutorHint;
    }

    public String getNombre2Tutor() {
        return nombre2Tutor;
    }

    public String getNombre2TutorHint() {
        return nombre2TutorHint;
    }

    public String getApellido1Tutor() {
        return apellido1Tutor;
    }

    public String getApellido1TutorHint() {
        return apellido1TutorHint;
    }

    public String getApellido2Tutor() {
        return apellido2Tutor;
    }

    public String getApellido2TutorHint() {
        return apellido2TutorHint;
    }

    public String getRelacionFamiliarTutor() {
        return relacionFamiliarTutor;
    }

    public String getParticipanteOTutorAlfabeto() {
        return participanteOTutorAlfabeto;
    }

    public String getParticipanteOTutorAlfabetoHint() {
        return participanteOTutorAlfabetoHint;
    }

    public String getTestigoPresente() {
        return testigoPresente;
    }

    public String getTestigoPresenteHint() {
        return testigoPresenteHint;
    }

    public String getNombre1Testigo() {
        return nombre1Testigo;
    }

    public String getNombre1TestigoHint() {
        return nombre1TestigoHint;
    }

    public String getNombre2Testigo() {
        return nombre2Testigo;
    }

    public String getNombre2TestigoHint() {
        return nombre2TestigoHint;
    }

    public String getApellido1Testigo() {
        return apellido1Testigo;
    }

    public String getApellido1TestigoHint() {
        return apellido1TestigoHint;
    }

    public String getApellido2Testigo() {
        return apellido2Testigo;
    }

    public String getApellido2TestigoHint() {
        return apellido2TestigoHint;
    }

    public String getVerifTutor() {
        return verifTutor;
    }

    public String getFinTamizajeLabel() {
        return finTamizajeLabel;
    }

    public String getNoCumpleIncDen() {
        return noCumpleIncDen;
    }
}
