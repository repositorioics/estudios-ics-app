package ni.org.ics.estudios.appmovil.muestreoanual.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;


/**
 * Constantes usadas en formulario de tamizaje
 * 
 * @author William Aviles
 * 
 */
public class TamizajeFormLabels {

    protected String tipoIngreso;
    protected String tipoIngresoHint;

	protected String fechaNacimiento;
	protected String fechaNacimientoHint;
    protected String sexo;
    protected String sexoHint;
    protected String pretermino;

    protected String aceptaTamizajePersona;
    protected String aceptaTamizajePersonaHint;
    protected String razonNoParticipaPersona;
    protected String razonNoParticipaPersonaHint;
    protected String otraRazonNoParticipaPersona;
    protected String otraRazonNoParticipaPersonaHint;
    protected String asentimientoVerbal;
    protected String asentimientoVerbalHint;

    protected String criteriosInclusion;
    protected String criteriosInclusionHint;
    protected String enfermedadInmuno;
    protected String cualEnfermedad;
    protected String otraEnfCronica;
    protected String enfCronicaAnio1;
    protected String enfCronicaMes1;
    protected String enfCronicaAnio2;
    protected String enfCronicaMes2;
    protected String enfCronicaAnio3;
    protected String enfCronicaMes3;
    protected String enfCronicaAnio4;
    protected String enfCronicaMes4;
    protected String enfCronicaAnio5;
    protected String enfCronicaMes5;
    protected String enfCronicaAnio6;
    protected String enfCronicaMes6;
    protected String enfCronicaAnio7;
    protected String enfCronicaMes7;
    protected String enfCronicaAnio8;
    protected String enfCronicaMes8;
    protected String enfCronicaAnio9;
    protected String enfCronicaMes9;
    protected String enfCronicaAnio10;
    protected String enfCronicaMes10;
    protected String enfCronicaAnio11;
    protected String enfCronicaMes11;
    protected String enfCronicaAnio12;
    protected String enfCronicaMes12;
    protected String enfCronicaAnio13;
    protected String enfCronicaMes13;
    protected String enfCronicaAnio14;
    protected String enfCronicaMes14;

    protected String tratamiento;
    protected String cualTratamiento;
    protected String otroTratamiento;
    protected String diagDengue;
    protected String fechaDiagDengue;
    protected String hospDengue;
    protected String fechaHospDengue;
    protected String tiempoResidencia;
    protected String vivienda;

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

    protected String aceptaCohorteDengue;
    protected String razonNoAceptaDengue;
    protected String otraRazonNoAceptaDengue;
    protected String razonNoAceptaDengueHint;
    protected String otraRazonNoAceptaDengueHint;
    protected String aceptaParteB;
    protected String aceptaParteBHint;
    protected String aceptaParteC;
    protected String aceptaParteCHint;
    protected String aceptaParteD;
    protected String aceptaParteDHint;

    protected String aceptaCohorteInfluenza;
    protected String razonNoAceptaInfluenza;
    protected String otraRazonNoAceptaInfluenza;
    protected String razonNoAceptaInfluenzaHint;
    protected String otraRazonNoAceptaInfluenzaHint;
    protected String aceptaParteBInf;
    protected String aceptaParteBInfHint;
    protected String aceptaParteCInf;
    protected String aceptaParteCInfHint;

    private String aceptaContactoFuturo;
    private String aceptaContactoFuturoHint;

    protected String casaPerteneceCohorte;
    protected String codigoCasaCohorte;
    protected String codigoCasaCohorteHint;
    protected String codigoNuevaCasaCohorte;
    protected String nombre1JefeFamilia;
    protected String nombre2JefeFamilia;
    protected String apellido1JefeFamilia;
    protected String apellido2JefeFamilia;
    protected String jefeFamiliaHint;
    protected String direccion;
    protected String direccionHint;
    protected String barrio;
    protected String manzana;

    protected String codigoNuevoParticipante;
    protected String nombre1;
    protected String nombre1Hint;
    protected String nombre2;
    protected String nombre2Hint;
    protected String apellido1;
    protected String apellido1Hint;
    protected String apellido2;
    protected String apellido2Hint;
    protected String nombre1Padre;
    protected String nombre1PadreHint;
    protected String nombre2Padre;
    protected String nombre2PadreHint;
    protected String apellido1Padre;
    protected String apellido1PadreHint;
    protected String apellido2Padre;
    protected String apellido2PadreHint;
    protected String nombre1Madre;
    protected String nombre1MadreHint;
    protected String nombre2Madre;
    protected String nombre2MadreHint;
    protected String apellido1Madre;
    protected String apellido1MadreHint;
    protected String apellido2Madre;
    protected String apellido2MadreHint;
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

    protected String nombreContacto;
    protected String nombreContactoHint;
    protected String barrioContacto;
    protected String barrioContactoHint;
    protected String direccionContacto;
    protected String direccionContactoHint;
    protected String numTelefono1;
    protected String numTelefono1Hint;
    protected String numTelefono2;
    protected String numTelefono2Hint;
    protected String operadoraTelefono1;
    protected String operadoraTelefono1Hint;
    protected String operadoraTelefono2;
    protected String operadoraTelefono2Hint;
    protected String tipoTelefono1;
    protected String tipoTelefono1Hint;
    protected String tipoTelefono2;
    protected String tipoTelefono2Hint;
    protected String tieneTelefono;
    protected String tieneOtroTelefono;
    protected String verifTutor;
    protected String finTamizajeLabel;
    //UO1
    protected String aceptaCohorteInfluenzaUO1;
    protected String razonNoAceptaInfluenzaUO1;
    protected String otraRazonNoAceptaInfluenzaUO1;
    protected String razonNoAceptaInfluenzaUO1Hint;
    protected String otraRazonNoAceptaInfluenzaUO1Hint;

	public TamizajeFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();

        tipoIngreso = res.getString(R.string.tipoIngreso);
        tipoIngresoHint = res.getString(R.string.tipoIngresoHint);
		fechaNacimiento = res.getString(R.string.fechaNacimiento);
        sexo = res.getString(R.string.sexo);

        aceptaTamizajePersona = res.getString(R.string.aceptaTamizajePersona);
        aceptaTamizajePersonaHint = res.getString(R.string.aceptaTamizajePersonaHint);
        razonNoParticipaPersona = res.getString(R.string.razonNoParticipaPersona);
        razonNoParticipaPersonaHint = res.getString(R.string.razonNoParticipaPersonaHint);
        otraRazonNoParticipaPersona = res.getString(R.string.otraRazonNoParticipaPersona);
        otraRazonNoParticipaPersonaHint = res.getString(R.string.otraRazonNoParticipaPersonaHint);
        asentimientoVerbal = res.getString(R.string.asentimientoVerbal);
        asentimientoVerbalHint = res.getString(R.string.asentimientoVerbalHint);

        criteriosInclusion = res.getString(R.string.criteriosInclusion);
        criteriosInclusionHint = res.getString(R.string.criteriosInclusionHint);

        enfermedadInmuno = res.getString(R.string.enfermedadInmuno);
        cualEnfermedad = res.getString(R.string.cualEnfermedad);
        otraEnfCronica = res.getString(R.string.oEnfCronica);
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

        tratamiento = res.getString(R.string.tratamientoIngreso);
        cualTratamiento = res.getString(R.string.cualTratamiento);
        otroTratamiento = res.getString(R.string.otroTx);
        diagDengue = res.getString(R.string.diagDengue);
        fechaDiagDengue = res.getString(R.string.fechaDiagDengue);
        hospDengue = res.getString(R.string.hospDengue);
        fechaHospDengue = res.getString(R.string.fechaHospDengue);
        tiempoResidencia = res.getString(R.string.tiempoResidencia);
        vivienda = res.getString(R.string.tipoVivienda);

        enfermedad = res.getString(R.string.enfermedadCronica);
        enfermedadHint = ""; //res.getString(R.string.enfermedadHint);
        dondeAsisteProblemasSalud = res.getString(R.string.dondeAsisteProblemasSalud);
        dondeAsisteProblemasSaludHint = res.getString(R.string.dondeAsisteProblemasSaludHint);
        otroCentroSalud = res.getString(R.string.otroCentroSalud);
        otroCentroSaludHint = res.getString(R.string.otroCentroSaludHint);
        puestoSalud = res.getString(R.string.puestoSalud);
        puestoSaludHint = res.getString(R.string.puestoSaludHint);

        aceptaAtenderCentro = res.getString(R.string.aceptaAtenderCentro);
        aceptaAtenderCentroHint = res.getString(R.string.aceptaAtenderCentroHint);

        pretermino = res.getString(R.string.pretermino);
        aceptaCohorteDengue = res.getString(R.string.aceptaCohorteDengue);
        razonNoAceptaDengue = res.getString(R.string.razonNoAceptaDengue);
        razonNoAceptaDengueHint = res.getString(R.string.razonNoAceptaDengueHint);
        otraRazonNoAceptaDengue = res.getString(R.string.otraRazonNoAceptaDengue);
        otraRazonNoAceptaDengueHint = res.getString(R.string.otraRazonNoAceptaDengueHint);
        aceptaParteB = res.getString(R.string.aceptaParteBDengue);
        aceptaParteBHint = res.getString(R.string.aceptaParteBDengueHint);
        aceptaParteC = res.getString(R.string.aceptaParteCDengue);
        aceptaParteCHint = res.getString(R.string.aceptaParteCDengueHint);
        aceptaParteD = res.getString(R.string.aceptaParteDDengue);
        aceptaParteDHint = res.getString(R.string.aceptaParteDDengueHint);

        aceptaCohorteInfluenza = res.getString(R.string.aceptaCohorteInfluenza);
        razonNoAceptaInfluenza = res.getString(R.string.razonNoAceptaInfluenza);
        razonNoAceptaInfluenzaHint = res.getString(R.string.razonNoAceptaInfluenzaHint);
        otraRazonNoAceptaInfluenza = res.getString(R.string.otraRazonNoAceptaInfluenza);
        otraRazonNoAceptaInfluenzaHint = res.getString(R.string.otraRazonNoAceptaInfluenzaHint);
        aceptaParteBInf = res.getString(R.string.aceptaParteBInf);
        aceptaParteBInfHint = res.getString(R.string.aceptaParteBInfHint);
        aceptaParteCInf = res.getString(R.string.aceptaParteCInf);
        aceptaParteCInfHint = res.getString(R.string.aceptaParteCInfHint);

        casaPerteneceCohorte = res.getString(R.string.casaPerteneceCohorte);
        codigoCasaCohorte = res.getString(R.string.codigoCasaCohorte);
        codigoCasaCohorteHint = res.getString(R.string.codigoCasaCohorteHint);
        codigoNuevaCasaCohorte = res.getString(R.string.codigoNuevaCasaCohorte);
        nombre1JefeFamilia = res.getString(R.string.nombre1JefeFamilia);
        nombre2JefeFamilia = res.getString(R.string.nombre2JefeFamilia);
        apellido1JefeFamilia = res.getString(R.string.apellido1JefeFamilia);
        apellido2JefeFamilia = res.getString(R.string.apellido2JefeFamilia);
        jefeFamiliaHint = res.getString(R.string.jefeFamiliaHint);
        direccion = res.getString(R.string.direccion);
        direccionHint = res.getString(R.string.direccionHint);
        barrio = res.getString(R.string.barrio);
        manzana = res.getString(R.string.manzana);

		codigoNuevoParticipante = res.getString(R.string.codigoNuevoParticipante);
        nombre1 = res.getString(R.string.nombre1);
        nombre1Hint = res.getString(R.string.nombre1Hint);
        nombre2 = res.getString(R.string.nombre2);
        nombre2Hint = res.getString(R.string.nombre2Hint);
        apellido1 = res.getString(R.string.apellido1);
        apellido1Hint = res.getString(R.string.apellido1Hint);
        apellido2 = res.getString(R.string.apellido2);
        apellido2Hint = res.getString(R.string.apellido2Hint);
        nombre1Padre = res.getString(R.string.nombre1Padre);
        nombre1PadreHint = res.getString(R.string.nombre1PadreHint);
        nombre2Padre = res.getString(R.string.nombre2Padre);
        nombre2PadreHint = res.getString(R.string.nombre2PadreHint);
        apellido1Padre = res.getString(R.string.apellido1Padre);
        apellido1PadreHint = res.getString(R.string.apellido1PadreHint);
        apellido2Padre = res.getString(R.string.apellido2Padre);
        apellido2PadreHint = res.getString(R.string.apellido2PadreHint);
        nombre1Madre = res.getString(R.string.nombre1Madre);
        nombre1MadreHint = res.getString(R.string.nombre1MadreHint);
        nombre2Madre = res.getString(R.string.nombre2Madre);
        nombre2MadreHint = res.getString(R.string.nombre2MadreHint);
        apellido1Madre = res.getString(R.string.apellido1Madre);
        apellido1MadreHint = res.getString(R.string.apellido1MadreHint);
        apellido2Madre = res.getString(R.string.apellido2Madre);
        apellido2MadreHint = res.getString(R.string.apellido2MadreHint);
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

        nombreContacto = res.getString(R.string.nombreContacto);
        nombreContactoHint = res.getString(R.string.nombreContactoHint);
        barrioContacto = res.getString(R.string.barrioContacto);
        barrioContactoHint = res.getString(R.string.barrioContactoHint);
        direccionContacto = res.getString(R.string.direccionContacto);
        direccionContactoHint = res.getString(R.string.direccionContactoHint);
        numTelefono1 = res.getString(R.string.numTelefono1);
        numTelefono1Hint = res.getString(R.string.numTelefono1Hint);
        numTelefono2 = res.getString(R.string.numTelefono2);
        numTelefono2Hint = res.getString(R.string.numTelefono2Hint);
        operadoraTelefono1 = res.getString(R.string.operadoraTelefono1);
        operadoraTelefono1Hint = res.getString(R.string.operadoraTelefono1Hint);
        operadoraTelefono2 = res.getString(R.string.operadoraTelefono2);
        operadoraTelefono2Hint = res.getString(R.string.operadoraTelefono2Hint);
        tipoTelefono1 = res.getString(R.string.tipoTelefono1);
        tipoTelefono1Hint = res.getString(R.string.tipoTelefono1Hint);
        tipoTelefono2 = res.getString(R.string.tipoTelefono2);
        tipoTelefono2Hint = res.getString(R.string.tipoTelefono2Hint);
        tieneTelefono = res.getString(R.string.tieneTelefono);
        tieneOtroTelefono = res.getString(R.string.tieneOtroTelefono);
        verifTutor = res.getString(R.string.verifTutor).replaceAll("25. ","");
        finTamizajeLabel = res.getString(R.string.finTamizajeLabel);

        //UO1
        aceptaCohorteInfluenzaUO1 = res.getString(R.string.aceptaCohorteInfluenzaUO1);
        razonNoAceptaInfluenzaUO1 = res.getString(R.string.razonNoAceptaInfluenzaUO1);
        razonNoAceptaInfluenzaUO1Hint = res.getString(R.string.razonNoAceptaInfluenzaUO1Hint);
        otraRazonNoAceptaInfluenzaUO1 = res.getString(R.string.otraRazonNoAceptaInfluenzaUO1);
        otraRazonNoAceptaInfluenzaUO1Hint = res.getString(R.string.otraRazonNoAceptaInfluenzaUO1Hint);

        aceptaContactoFuturo = res.getString(R.string.aceptaContactoFuturo);
        aceptaContactoFuturoHint = res.getString(R.string.aceptaContactoFuturoHint);
		
	}

    public String getTipoIngreso() {
        return tipoIngreso;
    }

    public String getTipoIngresoHint() {
        return tipoIngresoHint;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getFechaNacimientoHint() {
        return fechaNacimientoHint;
    }

    public String getSexo() {
        return sexo;
    }

    public String getSexoHint() {
        return sexoHint;
    }

    public String getPretermino() {
        return pretermino;
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

    public String getCriteriosInclusion() {
        return criteriosInclusion;
    }

    public String getCriteriosInclusionHint() {
        return criteriosInclusionHint;
    }

    public String getEnfermedadInmuno() {
        return enfermedadInmuno;
    }

    public String getCualEnfermedad() {
        return cualEnfermedad;
    }

    public String getOtraEnfCronica() {
        return otraEnfCronica;
    }

    public String getEnfCronicaAnio1() {
        return enfCronicaAnio1;
    }

    public String getEnfCronicaMes1() {
        return enfCronicaMes1;
    }

    public String getEnfCronicaAnio2() {
        return enfCronicaAnio2;
    }

    public String getEnfCronicaMes2() {
        return enfCronicaMes2;
    }

    public String getEnfCronicaAnio3() {
        return enfCronicaAnio3;
    }

    public String getEnfCronicaMes3() {
        return enfCronicaMes3;
    }

    public String getEnfCronicaAnio4() {
        return enfCronicaAnio4;
    }

    public String getEnfCronicaMes4() {
        return enfCronicaMes4;
    }

    public String getEnfCronicaAnio5() {
        return enfCronicaAnio5;
    }

    public String getEnfCronicaMes5() {
        return enfCronicaMes5;
    }

    public String getEnfCronicaAnio6() {
        return enfCronicaAnio6;
    }

    public String getEnfCronicaMes6() {
        return enfCronicaMes6;
    }

    public String getEnfCronicaAnio7() {
        return enfCronicaAnio7;
    }

    public String getEnfCronicaMes7() {
        return enfCronicaMes7;
    }

    public String getEnfCronicaAnio8() {
        return enfCronicaAnio8;
    }

    public String getEnfCronicaMes8() {
        return enfCronicaMes8;
    }

    public String getEnfCronicaAnio9() {
        return enfCronicaAnio9;
    }

    public String getEnfCronicaMes9() {
        return enfCronicaMes9;
    }

    public String getEnfCronicaAnio10() {
        return enfCronicaAnio10;
    }

    public String getEnfCronicaMes10() {
        return enfCronicaMes10;
    }

    public String getEnfCronicaAnio11() {
        return enfCronicaAnio11;
    }

    public String getEnfCronicaMes11() {
        return enfCronicaMes11;
    }

    public String getEnfCronicaAnio12() {
        return enfCronicaAnio12;
    }

    public String getEnfCronicaMes12() {
        return enfCronicaMes12;
    }

    public String getEnfCronicaAnio13() {
        return enfCronicaAnio13;
    }

    public String getEnfCronicaMes13() {
        return enfCronicaMes13;
    }

    public String getEnfCronicaAnio14() {
        return enfCronicaAnio14;
    }

    public String getEnfCronicaMes14() {
        return enfCronicaMes14;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public String getCualTratamiento() {
        return cualTratamiento;
    }

    public String getOtroTratamiento() {
        return otroTratamiento;
    }

    public String getDiagDengue() {
        return diagDengue;
    }

    public String getFechaDiagDengue() {
        return fechaDiagDengue;
    }

    public String getHospDengue() {
        return hospDengue;
    }

    public String getFechaHospDengue() {
        return fechaHospDengue;
    }

    public String getTiempoResidencia() {
        return tiempoResidencia;
    }

    public String getVivienda() {
        return vivienda;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public String getEnfermedadHint() {
        return enfermedadHint;
    }

    public String getDondeAsisteProblemasSalud() {
        return dondeAsisteProblemasSalud;
    }

    public String getDondeAsisteProblemasSaludHint() {
        return dondeAsisteProblemasSaludHint;
    }

    public String getOtroCentroSalud() {
        return otroCentroSalud;
    }

    public String getOtroCentroSaludHint() {
        return otroCentroSaludHint;
    }

    public String getPuestoSalud() {
        return puestoSalud;
    }

    public String getPuestoSaludHint() {
        return puestoSaludHint;
    }

    public String getAceptaAtenderCentro() {
        return aceptaAtenderCentro;
    }

    public String getAceptaAtenderCentroHint() {
        return aceptaAtenderCentroHint;
    }

    public String getAceptaCohorteDengue() {
        return aceptaCohorteDengue;
    }

    public String getRazonNoAceptaDengue() {
        return razonNoAceptaDengue;
    }

    public String getOtraRazonNoAceptaDengue() {
        return otraRazonNoAceptaDengue;
    }

    public String getRazonNoAceptaDengueHint() {
        return razonNoAceptaDengueHint;
    }

    public String getOtraRazonNoAceptaDengueHint() {
        return otraRazonNoAceptaDengueHint;
    }

    public String getAceptaParteB() {
        return aceptaParteB;
    }

    public String getAceptaParteBHint() {
        return aceptaParteBHint;
    }

    public String getAceptaParteC() {
        return aceptaParteC;
    }

    public String getAceptaParteCHint() {
        return aceptaParteCHint;
    }

    public String getAceptaParteD() {
        return aceptaParteD;
    }

    public String getAceptaParteDHint() {
        return aceptaParteDHint;
    }

    public String getAceptaCohorteInfluenza() {
        return aceptaCohorteInfluenza;
    }

    public String getRazonNoAceptaInfluenza() {
        return razonNoAceptaInfluenza;
    }

    public String getOtraRazonNoAceptaInfluenza() {
        return otraRazonNoAceptaInfluenza;
    }

    public String getRazonNoAceptaInfluenzaHint() {
        return razonNoAceptaInfluenzaHint;
    }

    public String getOtraRazonNoAceptaInfluenzaHint() {
        return otraRazonNoAceptaInfluenzaHint;
    }

    public String getAceptaParteBInf() {
        return aceptaParteBInf;
    }

    public String getAceptaParteBInfHint() {
        return aceptaParteBInfHint;
    }

    public String getAceptaParteCInf() {
        return aceptaParteCInf;
    }

    public String getAceptaParteCInfHint() {
        return aceptaParteCInfHint;
    }

    public String getCasaPerteneceCohorte() {
        return casaPerteneceCohorte;
    }

    public String getCodigoCasaCohorte() {
        return codigoCasaCohorte;
    }

    public String getCodigoCasaCohorteHint() {
        return codigoCasaCohorteHint;
    }

    public String getCodigoNuevaCasaCohorte() {
        return codigoNuevaCasaCohorte;
    }

    public String getNombre1JefeFamilia() {
        return nombre1JefeFamilia;
    }

    public String getNombre2JefeFamilia() {
        return nombre2JefeFamilia;
    }

    public String getApellido1JefeFamilia() {
        return apellido1JefeFamilia;
    }

    public String getApellido2JefeFamilia() {
        return apellido2JefeFamilia;
    }

    public String getJefeFamiliaHint() {
        return jefeFamiliaHint;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDireccionHint() {
        return direccionHint;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getManzana() {
        return manzana;
    }

    public String getCodigoNuevoParticipante() {
        return codigoNuevoParticipante;
    }

    public String getNombre1() {
        return nombre1;
    }

    public String getNombre1Hint() {
        return nombre1Hint;
    }

    public String getNombre2() {
        return nombre2;
    }

    public String getNombre2Hint() {
        return nombre2Hint;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido1Hint() {
        return apellido1Hint;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getApellido2Hint() {
        return apellido2Hint;
    }

    public String getNombre1Padre() {
        return nombre1Padre;
    }

    public String getNombre1PadreHint() {
        return nombre1PadreHint;
    }

    public String getNombre2Padre() {
        return nombre2Padre;
    }

    public String getNombre2PadreHint() {
        return nombre2PadreHint;
    }

    public String getApellido1Padre() {
        return apellido1Padre;
    }

    public String getApellido1PadreHint() {
        return apellido1PadreHint;
    }

    public String getApellido2Padre() {
        return apellido2Padre;
    }

    public String getApellido2PadreHint() {
        return apellido2PadreHint;
    }

    public String getNombre1Madre() {
        return nombre1Madre;
    }

    public String getNombre1MadreHint() {
        return nombre1MadreHint;
    }

    public String getNombre2Madre() {
        return nombre2Madre;
    }

    public String getNombre2MadreHint() {
        return nombre2MadreHint;
    }

    public String getApellido1Madre() {
        return apellido1Madre;
    }

    public String getApellido1MadreHint() {
        return apellido1MadreHint;
    }

    public String getApellido2Madre() {
        return apellido2Madre;
    }

    public String getApellido2MadreHint() {
        return apellido2MadreHint;
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

    public String getNombreContacto() {
        return nombreContacto;
    }

    public String getNombreContactoHint() {
        return nombreContactoHint;
    }

    public String getBarrioContacto() {
        return barrioContacto;
    }

    public String getBarrioContactoHint() {
        return barrioContactoHint;
    }

    public String getDireccionContacto() {
        return direccionContacto;
    }

    public String getDireccionContactoHint() {
        return direccionContactoHint;
    }

    public String getNumTelefono1() {
        return numTelefono1;
    }

    public String getNumTelefono1Hint() {
        return numTelefono1Hint;
    }

    public String getNumTelefono2() {
        return numTelefono2;
    }

    public String getNumTelefono2Hint() {
        return numTelefono2Hint;
    }

    public String getOperadoraTelefono1() {
        return operadoraTelefono1;
    }

    public String getOperadoraTelefono1Hint() {
        return operadoraTelefono1Hint;
    }

    public String getOperadoraTelefono2() {
        return operadoraTelefono2;
    }

    public String getOperadoraTelefono2Hint() {
        return operadoraTelefono2Hint;
    }

    public String getTipoTelefono1() {
        return tipoTelefono1;
    }

    public String getTipoTelefono1Hint() {
        return tipoTelefono1Hint;
    }

    public String getTipoTelefono2() {
        return tipoTelefono2;
    }

    public String getTipoTelefono2Hint() {
        return tipoTelefono2Hint;
    }

    public String getTieneTelefono() {
        return tieneTelefono;
    }

    public String getTieneOtroTelefono() {
        return tieneOtroTelefono;
    }

    public String getFinTamizajeLabel() {
        return finTamizajeLabel;
    }

    public String getVerifTutor() {
        return verifTutor;
    }

    public void setVerifTutor(String verifTutor) {
        this.verifTutor = verifTutor;
    }

    public String getAceptaCohorteInfluenzaUO1() {
        return aceptaCohorteInfluenzaUO1;
    }

    public String getRazonNoAceptaInfluenzaUO1() {
        return razonNoAceptaInfluenzaUO1;
    }

    public String getOtraRazonNoAceptaInfluenzaUO1() {
        return otraRazonNoAceptaInfluenzaUO1;
    }

    public String getRazonNoAceptaInfluenzaUO1Hint() {
        return razonNoAceptaInfluenzaUO1Hint;
    }

    public String getOtraRazonNoAceptaInfluenzaUO1Hint() {
        return otraRazonNoAceptaInfluenzaUO1Hint;
    }

    public String getAceptaContactoFuturo() {
        return aceptaContactoFuturo;
    }

    public String getAceptaContactoFuturoHint() {
        return aceptaContactoFuturoHint;
    }
}
