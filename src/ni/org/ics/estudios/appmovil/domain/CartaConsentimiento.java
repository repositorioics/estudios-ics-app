package ni.org.ics.estudios.appmovil.domain;

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
    private Participante participante;
    //private String emancipado;
    private String nombre1Tutor;
    private String nombre2Tutor;
    private String apellido1Tutor;
    private String apellido2Tutor;
    private String relacionFamiliarTutor;
    private String participanteOTutorAlfabeto;
    private String testigoPresente;
    private String nombre1Testigo;
    private String nombre2Testigo;
    private String apellido1Testigo;
    private String apellido2Testigo;
    private String aceptaParteA;
    private String motivoRechazoParteA;
    private String aceptaContactoFuturo;
    private String aceptaParteB; //Consentimiento para almacenamiento y uso de muestras en estudios futuros
    private String aceptaParteC; //Consentimiento adicional para estudios genéticos
    private String aceptaParteD; //Consentimiento adicional para ZIKA (Estudio Cohorte Dengue)
    private String aceptaParteE; //Consentimiento para almacenamiento y uso de meustras en estudios futuros EstudioFLu, muestra adicional chf para covid19
    private String aceptaParteF;//re-enrolamiento y obtención de muestras de sangre adicionales-CEIRS. MA2022
    private String version; //Indicar la versión actual al momento de registrar la carta
    //reconsentimiento dengue 2018
    private String otroMotivoRechazoParteA;
    private String motivoRechazoParteDExt;
    private String otroMotivoRechazoParteDExt;
    private String mismoTutor;
    private String motivoDifTutor;
    private String otroMotivoDifTutor;
    private String otraRelacionFamTutor;
    private String verifTutor;
    private String reconsentimiento; //indica si es carta por reconsentimiento del estudio
    //consentimiento muestras superficie casas
    private String nombre1MxSuperficie;
    private String nombre2MxSuperficie;
    private String apellido1MxSuperficie;
    private String apellido2MxSuperficie;
    private String casaChf;
    //muestra adicional chf covid19
    private String motivoRechazoParteE;
    private String otroMotivoRechazoParteE;
    //re-enrolamiento y obtención de muestras de sangre adicionales-CEIRS. MA2022
    private String motivoRechazoParteF;
    private String otroMotivoRechazoParteF;

    private String aceptaParteG;//re-enrolamiento y obtención de muestras de sangre adicionales-CEIRS. MA2023
    private String motivoRechazoParteG;
    private String otroMotivoRechazoParteG;


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

    public Tamizaje getTamizaje() {
        return tamizaje;
    }

    public void setTamizaje(Tamizaje tamizaje) {
        this.tamizaje = tamizaje;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }
/*
    public String getEmancipado() {
        return emancipado;
    }

    public void setEmancipado(String emancipado) {
        this.emancipado = emancipado;
    }
*/
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

    public String getTestigoPresente() {
        return testigoPresente;
    }

    public void setTestigoPresente(String testigoPresente) {
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

    public String getAceptaParteA() {
        return aceptaParteA;
    }

    public void setAceptaParteA(String aceptaParteA) {
        this.aceptaParteA = aceptaParteA;
    }

    public String getMotivoRechazoParteA() {
        return motivoRechazoParteA;
    }

    public void setMotivoRechazoParteA(String motivoRechazoParteA) {
        this.motivoRechazoParteA = motivoRechazoParteA;
    }

    public String getAceptaContactoFuturo() {
        return aceptaContactoFuturo;
    }

    public void setAceptaContactoFuturo(String aceptaContactoFuturo) {
        this.aceptaContactoFuturo = aceptaContactoFuturo;
    }

    public String getAceptaParteB() {
        return aceptaParteB;
    }

    public void setAceptaParteB(String aceptaParteB) {
        this.aceptaParteB = aceptaParteB;
    }

    public String getAceptaParteC() {
        return aceptaParteC;
    }

    public void setAceptaParteC(String aceptaParteC) {
        this.aceptaParteC = aceptaParteC;
    }

    public String getAceptaParteD() {
        return aceptaParteD;
    }

    public void setAceptaParteD(String aceptaParteD) {
        this.aceptaParteD = aceptaParteD;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOtroMotivoRechazoParteA() {
        return otroMotivoRechazoParteA;
    }

    public void setOtroMotivoRechazoParteA(String otroMotivoRechazoParteA) {
        this.otroMotivoRechazoParteA = otroMotivoRechazoParteA;
    }

    public String getMotivoRechazoParteDExt() {
        return motivoRechazoParteDExt;
    }

    public void setMotivoRechazoParteDExt(String motivoRechazoParteDExt) {
        this.motivoRechazoParteDExt = motivoRechazoParteDExt;
    }

    public String getOtroMotivoRechazoParteDExt() {
        return otroMotivoRechazoParteDExt;
    }

    public void setOtroMotivoRechazoParteDExt(String otroMotivoRechazoParteDExt) {
        this.otroMotivoRechazoParteDExt = otroMotivoRechazoParteDExt;
    }

    public String getMismoTutor() {
        return mismoTutor;
    }

    public void setMismoTutor(String mismoTutor) {
        this.mismoTutor = mismoTutor;
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

    public String getOtraRelacionFamTutor() {
        return otraRelacionFamTutor;
    }

    public void setOtraRelacionFamTutor(String otraRelacionFamTutor) {
        this.otraRelacionFamTutor = otraRelacionFamTutor;
    }

    public String getVerifTutor() {
        return verifTutor;
    }

    public void setVerifTutor(String verifTutor) {
        this.verifTutor = verifTutor;
    }

    public String getReconsentimiento() {
        return reconsentimiento;
    }

    public void setReconsentimiento(String reconsentimiento) {
        this.reconsentimiento = reconsentimiento;
    }

    public String getNombre1MxSuperficie() {
        return nombre1MxSuperficie;
    }

    public void setNombre1MxSuperficie(String nombre1MxSuperficie) {
        this.nombre1MxSuperficie = nombre1MxSuperficie;
    }

    public String getNombre2MxSuperficie() {
        return nombre2MxSuperficie;
    }

    public void setNombre2MxSuperficie(String nombre2MxSuperficie) {
        this.nombre2MxSuperficie = nombre2MxSuperficie;
    }

    public String getApellido2MxSuperficie() {
        return apellido2MxSuperficie;
    }


    public String getApellido1MxSuperficie() {
        return apellido1MxSuperficie;
    }

    public void setApellido1MxSuperficie(String apellido1MxSuperficie) {
        this.apellido1MxSuperficie = apellido1MxSuperficie;
    }

    public void setApellido2MxSuperficie(String apellido2MxSuperficie) {
        this.apellido2MxSuperficie = apellido2MxSuperficie;
    }


    public String getCasaChf() {
        return casaChf;
    }

    public void setCasaChf(String casaChf) {
        this.casaChf = casaChf;
    }

    public String getAceptaParteE() {
        return aceptaParteE;
    }

    public void setAceptaParteE(String aceptaParteE) {
        this.aceptaParteE = aceptaParteE;
    }

    public String getMotivoRechazoParteE() {
        return motivoRechazoParteE;
    }

    public void setMotivoRechazoParteE(String motivoRechazoParteE) {
        this.motivoRechazoParteE = motivoRechazoParteE;
    }

    public String getOtroMotivoRechazoParteE() {
        return otroMotivoRechazoParteE;
    }

    public void setOtroMotivoRechazoParteE(String otroMotivoRechazoParteE) {
        this.otroMotivoRechazoParteE = otroMotivoRechazoParteE;
    }

    public String getAceptaParteF() {
        return aceptaParteF;
    }

    public void setAceptaParteF(String aceptaParteF) {
        this.aceptaParteF = aceptaParteF;
    }

    public String getMotivoRechazoParteF() {
        return motivoRechazoParteF;
    }

    public void setMotivoRechazoParteF(String motivoRechazoParteF) {
        this.motivoRechazoParteF = motivoRechazoParteF;
    }

    public String getOtroMotivoRechazoParteF() {
        return otroMotivoRechazoParteF;
    }

    public void setOtroMotivoRechazoParteF(String otroMotivoRechazoParteF) {
        this.otroMotivoRechazoParteF = otroMotivoRechazoParteF;
    }

    public String getAceptaParteG() {
        return aceptaParteG;
    }

    public void setAceptaParteG(String aceptaParteG) {
        this.aceptaParteG = aceptaParteG;
    }

    public String getMotivoRechazoParteG() {
        return motivoRechazoParteG;
    }

    public void setMotivoRechazoParteG(String motivoRechazoParteG) {
        this.motivoRechazoParteG = motivoRechazoParteG;
    }

    public String getOtroMotivoRechazoParteG() {
        return otroMotivoRechazoParteG;
    }

    public void setOtroMotivoRechazoParteG(String otroMotivoRechazoParteG) {
        this.otroMotivoRechazoParteG = otroMotivoRechazoParteG;
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
