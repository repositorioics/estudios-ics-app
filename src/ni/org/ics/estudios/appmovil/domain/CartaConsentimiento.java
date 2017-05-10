package ni.org.ics.estudios.appmovil.domain;

import ni.org.ics.estudios.appmovil.catalogs.Estudio;

import java.util.Date;

/**
 * Created by FIRSTICT on 4/28/2017.
 * V1.0
 */
public class CartaConsentimiento extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
    private Date fechaFirma;
    private Tamizaje tamizaje;
    private Estudio estudio;
    private char participadoCohortePediatrica;
    private String cohortePediatrica; //Dengue, Influenza, Ambas (dengue/influenza)
    private char codigoReactivado;
    private char emancipado;
    private char asentimientoVerbal; //Para Niños de 6 a 17 años
    private String nombre1Tutor;
    private String nombre2Tutor;
    private String apellido1Tutor;
    private String apellido2Tutor;
    private String relacionFamiliarTutor;
    private char participanteOTutorAlfabeto;
    private char testigoPresente;
    private String nombre1Testigo;
    private String nombre2Testigo;
    private String apellido1Testigo;
    private String apellido2Testigo;
    private char aceptaParteA;
    private String motivoRechazoParteA;
    private char aceptaContactoFuturo;
    private char aceptaParteB; //Consentimiento para almacenamiento y uso de muestras en estudios futuros
    private char aceptaParteC; //Consentimiento adicional para estudios genéticos

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(Date fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public char getParticipadoCohortePediatrica() {
        return participadoCohortePediatrica;
    }

    public void setParticipadoCohortePediatrica(char participadoCohortePediatrica) {
        this.participadoCohortePediatrica = participadoCohortePediatrica;
    }

    public String getCohortePediatrica() {
        return cohortePediatrica;
    }

    public void setCohortePediatrica(String cohortePediatrica) {
        this.cohortePediatrica = cohortePediatrica;
    }

    public char getCodigoReactivado() {
        return codigoReactivado;
    }

    public void setCodigoReactivado(char codigoReactivado) {
        this.codigoReactivado = codigoReactivado;
    }

    public char getEmancipado() {
        return emancipado;
    }

    public void setEmancipado(char emancipado) {
        this.emancipado = emancipado;
    }

    public char getAsentimientoVerbal() {
        return asentimientoVerbal;
    }

    public void setAsentimientoVerbal(char asentimientoVerbal) {
        this.asentimientoVerbal = asentimientoVerbal;
    }

    public String getNombre1Tutor() {
        return nombre1Tutor;
    }

    public void setNombre1Tutor(String nombre1Tutor) {
        this.nombre1Tutor = nombre1Tutor;
    }

    public String getNombre2Tutor() {
        return nombre2Tutor;
    }

    public void setNombre2Tutor(String nombre2Tutor) {
        this.nombre2Tutor = nombre2Tutor;
    }

    public String getApellido1Tutor() {
        return apellido1Tutor;
    }

    public void setApellido1Tutor(String apellido1Tutor) {
        this.apellido1Tutor = apellido1Tutor;
    }

    public String getApellido2Tutor() {
        return apellido2Tutor;
    }

    public void setApellido2Tutor(String apellido2Tutor) {
        this.apellido2Tutor = apellido2Tutor;
    }

    public char getParticipanteOTutorAlfabeto() {
        return participanteOTutorAlfabeto;
    }

    public void setParticipanteOTutorAlfabeto(char participanteOTutorAlfabeto) {
        this.participanteOTutorAlfabeto = participanteOTutorAlfabeto;
    }

    public char getTestigoPresente() {
        return testigoPresente;
    }

    public void setTestigoPresente(char testigoPresente) {
        this.testigoPresente = testigoPresente;
    }

    public String getNombre1Testigo() {
        return nombre1Testigo;
    }

    public void setNombre1Testigo(String nombre1Testigo) {
        this.nombre1Testigo = nombre1Testigo;
    }

    public String getNombre2Testigo() {
        return nombre2Testigo;
    }

    public void setNombre2Testigo(String nombre2Testigo) {
        this.nombre2Testigo = nombre2Testigo;
    }

    public String getApellido1Testigo() {
        return apellido1Testigo;
    }

    public void setApellido1Testigo(String apellido1Testigo) {
        this.apellido1Testigo = apellido1Testigo;
    }

    public String getApellido2Testigo() {
        return apellido2Testigo;
    }

    public void setApellido2Testigo(String apellido2Testigo) {
        this.apellido2Testigo = apellido2Testigo;
    }

    public char getAceptaParteA() {
        return aceptaParteA;
    }

    public void setAceptaParteA(char aceptaParteA) {
        this.aceptaParteA = aceptaParteA;
    }

    public String getMotivoRechazoParteA() {
        return motivoRechazoParteA;
    }

    public void setMotivoRechazoParteA(String motivoRechazoParteA) {
        this.motivoRechazoParteA = motivoRechazoParteA;
    }

    public char getAceptaContactoFuturo() {
        return aceptaContactoFuturo;
    }

    public void setAceptaContactoFuturo(char aceptaContactoFuturo) {
        this.aceptaContactoFuturo = aceptaContactoFuturo;
    }

    public char getAceptaParteB() {
        return aceptaParteB;
    }

    public void setAceptaParteB(char aceptaParteB) {
        this.aceptaParteB = aceptaParteB;
    }

    public char getAceptaParteC() {
        return aceptaParteC;
    }

    public void setAceptaParteC(char aceptaParteC) {
        this.aceptaParteC = aceptaParteC;
    }

    public Tamizaje getTamizaje() {
        return tamizaje;
    }

    public void setTamizaje(Tamizaje tamizaje) {
        this.tamizaje = tamizaje;
    }

    public Estudio getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
    }

    public String getRelacionFamiliarTutor() {
        return relacionFamiliarTutor;
    }

    public void setRelacionFamiliarTutor(String relacionFamiliar) {
        this.relacionFamiliarTutor = relacionFamiliar;
    }

    @Override
    public String toString() {
        return this.codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartaConsentimiento)) return false;

        CartaConsentimiento that = (CartaConsentimiento) o;

        return  (!codigo.equals(that.codigo));
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
