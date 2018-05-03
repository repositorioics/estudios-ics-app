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

    protected String finTamizajeLabel;

	public ConsentimientoSAFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();

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

        finTamizajeLabel = res.getString(R.string.finTamizajeLabel);
		
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

    public String getAsentimientoVerbal() {
        return asentimientoVerbal;
    }

    public void setAsentimientoVerbal(String asentimientoVerbal) {
        this.asentimientoVerbal = asentimientoVerbal;
    }

    public String getAsentimientoVerbalHint() {
        return asentimientoVerbalHint;
    }

    public void setAsentimientoVerbalHint(String asentimientoVerbalHint) {
        this.asentimientoVerbalHint = asentimientoVerbalHint;
    }

    public String getAceptaSeroprevalencia() {
        return aceptaSeroprevalencia;
    }

    public void setAceptaSeroprevalencia(String aceptaSeroprevalencia) {
        this.aceptaSeroprevalencia = aceptaSeroprevalencia;
    }

    public String getRazonNoAceptaSeroprevalencia() {
        return razonNoAceptaSeroprevalencia;
    }

    public void setRazonNoAceptaSeroprevalencia(String razonNoAceptaSeroprevalencia) {
        this.razonNoAceptaSeroprevalencia = razonNoAceptaSeroprevalencia;
    }

    public String getRazonNoAceptaSeroprevalenciaHint() {
        return razonNoAceptaSeroprevalenciaHint;
    }

    public void setRazonNoAceptaSeroprevalenciaHint(String razonNoAceptaSeroprevalenciaHint) {
        this.razonNoAceptaSeroprevalenciaHint = razonNoAceptaSeroprevalenciaHint;
    }

    public String getOtraRazonNoAceptaSeroprevalencia() {
        return otraRazonNoAceptaSeroprevalencia;
    }

    public void setOtraRazonNoAceptaSeroprevalencia(String otraRazonNoAceptaSeroprevalencia) {
        this.otraRazonNoAceptaSeroprevalencia = otraRazonNoAceptaSeroprevalencia;
    }

    public String getOtraRazonNoAceptaSeroprevalenciaHint() {
        return otraRazonNoAceptaSeroprevalenciaHint;
    }

    public void setOtraRazonNoAceptaSeroprevalenciaHint(String otraRazonNoAceptaSeroprevalenciaHint) {
        this.otraRazonNoAceptaSeroprevalenciaHint = otraRazonNoAceptaSeroprevalenciaHint;
    }

    public String getNombre1Tutor() {
        return nombre1Tutor;
    }

    public void setNombre1Tutor(String nombre1Tutor) {
        this.nombre1Tutor = nombre1Tutor;
    }

    public String getNombre1TutorHint() {
        return nombre1TutorHint;
    }

    public void setNombre1TutorHint(String nombre1TutorHint) {
        this.nombre1TutorHint = nombre1TutorHint;
    }

    public String getNombre2Tutor() {
        return nombre2Tutor;
    }

    public void setNombre2Tutor(String nombre2Tutor) {
        this.nombre2Tutor = nombre2Tutor;
    }

    public String getNombre2TutorHint() {
        return nombre2TutorHint;
    }

    public void setNombre2TutorHint(String nombre2TutorHint) {
        this.nombre2TutorHint = nombre2TutorHint;
    }

    public String getApellido1Tutor() {
        return apellido1Tutor;
    }

    public void setApellido1Tutor(String apellido1Tutor) {
        this.apellido1Tutor = apellido1Tutor;
    }

    public String getApellido1TutorHint() {
        return apellido1TutorHint;
    }

    public void setApellido1TutorHint(String apellido1TutorHint) {
        this.apellido1TutorHint = apellido1TutorHint;
    }

    public String getApellido2Tutor() {
        return apellido2Tutor;
    }

    public void setApellido2Tutor(String apellido2Tutor) {
        this.apellido2Tutor = apellido2Tutor;
    }

    public String getApellido2TutorHint() {
        return apellido2TutorHint;
    }

    public void setApellido2TutorHint(String apellido2TutorHint) {
        this.apellido2TutorHint = apellido2TutorHint;
    }

    public String getRelacionFamiliarTutor() {
        return relacionFamiliarTutor;
    }

    public void setRelacionFamiliarTutor(String relacionFamiliarTutor) {
        this.relacionFamiliarTutor = relacionFamiliarTutor;
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

    public String getFinTamizajeLabel() {
        return finTamizajeLabel;
    }

    public void setFinTamizajeLabel(String finTamizajeLabel) {
        this.finTamizajeLabel = finTamizajeLabel;
    }
}
