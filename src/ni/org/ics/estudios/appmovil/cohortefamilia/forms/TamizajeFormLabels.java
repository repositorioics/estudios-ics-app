package ni.org.ics.estudios.appmovil.cohortefamilia.forms;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import android.content.res.Resources;


/**
 * Constantes usadas en formulario de tamizaje
 * 
 * @author William Aviles
 * 
 */
public class TamizajeFormLabels {
	
	protected String sexo;
	protected String sexoHint;
	protected String fechaNacimiento;
	protected String fechaNacimientoHint;
	protected String aceptaTamizajePersona;
	protected String aceptaTamizajePersonaHint;
	protected String razonNoParticipaPersona;
	protected String razonNoParticipaPersonaHint;
	protected String criteriosInclusion;
	protected String criteriosInclusionHint;
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
	protected String asentimientoVerbal;
	protected String asentimientoVerbalHint;
	
	protected String participadoCohortePediatrica;
	protected String participadoCohortePediatricaHint;
	protected String codigoCohorte;
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

	protected String emancipado;
	protected String emancipadoHint;
	protected String nombre1Tutor;
	protected String nombre1TutorHint;
	protected String nombre2Tutor;
	protected String nombre2TutorHint;
	protected String apellido1Tutor;
	protected String apellido1TutorHint;
	protected String apellido2Tutor;
	protected String apellido2TutorHint;
	protected String relacionFamiliarTutor;
	protected String relacionFamiliarTutorHint;
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
	
	protected String finTamizajeLabel;

	
	
	public TamizajeFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		
		sexo = res.getString(R.string.sexo);
		sexoHint = res.getString(R.string.sexoHint);
		fechaNacimiento = res.getString(R.string.fechaNacimiento);
		fechaNacimientoHint = res.getString(R.string.fechaNacimientoHint);
		aceptaTamizajePersona = res.getString(R.string.aceptaTamizajePersona);
		aceptaTamizajePersonaHint = res.getString(R.string.aceptaTamizajePersonaHint);
		razonNoParticipaPersona = res.getString(R.string.razonNoParticipaPersona);
		razonNoParticipaPersonaHint = res.getString(R.string.razonNoParticipaPersonaHint);
		criteriosInclusion = res.getString(R.string.criteriosInclusion);
		criteriosInclusionHint = res.getString(R.string.criteriosInclusionHint);
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
		asentimientoVerbal = res.getString(R.string.asentimientoVerbal);
		asentimientoVerbalHint = res.getString(R.string.asentimientoVerbalHint);
		
		participadoCohortePediatrica = res.getString(R.string.participadoCohortePediatrica);
		participadoCohortePediatricaHint = res.getString(R.string.participadoCohortePediatricaHint);
		codigoCohorte = res.getString(R.string.codigoCohorte);
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
		
		emancipado = res.getString(R.string.emancipado);
		emancipadoHint = res.getString(R.string.emancipadoHint);
		nombre1Tutor = res.getString(R.string.nombre1Tutor);
		nombre1TutorHint = res.getString(R.string.nombre1TutorHint);
		nombre2Tutor = res.getString(R.string.nombre2Tutor);
		nombre2TutorHint = res.getString(R.string.nombre2TutorHint);
		apellido1Tutor = res.getString(R.string.apellido1Tutor);
		apellido1TutorHint = res.getString(R.string.apellido1TutorHint);
		apellido2Tutor = res.getString(R.string.apellido2Tutor);
		apellido2TutorHint = res.getString(R.string.apellido2TutorHint);	
		relacionFamiliarTutor = res.getString(R.string.relacionFamiliarTutor);
		relacionFamiliarTutorHint = res.getString(R.string.relacionFamiliarTutorHint);
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
		
		finTamizajeLabel = res.getString(R.string.finTamizajeLabel);
		
	}





	public String getSexo() {
		return sexo;
	}





	public void setSexo(String sexo) {
		this.sexo = sexo;
	}





	public String getSexoHint() {
		return sexoHint;
	}





	public void setSexoHint(String sexoHint) {
		this.sexoHint = sexoHint;
	}





	public String getFechaNacimiento() {
		return fechaNacimiento;
	}





	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}





	public String getFechaNacimientoHint() {
		return fechaNacimientoHint;
	}





	public void setFechaNacimientoHint(String fechaNacimientoHint) {
		this.fechaNacimientoHint = fechaNacimientoHint;
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





	public String getDondeAsisteProblemasSalud() {
		return dondeAsisteProblemasSalud;
	}





	public void setDondeAsisteProblemasSalud(String dondeAsisteProblemasSalud) {
		this.dondeAsisteProblemasSalud = dondeAsisteProblemasSalud;
	}





	public String getDondeAsisteProblemasSaludHint() {
		return dondeAsisteProblemasSaludHint;
	}





	public void setDondeAsisteProblemasSaludHint(
			String dondeAsisteProblemasSaludHint) {
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





	public String getParticipadoCohortePediatrica() {
		return participadoCohortePediatrica;
	}





	public void setParticipadoCohortePediatrica(String participadoCohortePediatrica) {
		this.participadoCohortePediatrica = participadoCohortePediatrica;
	}





	public String getParticipadoCohortePediatricaHint() {
		return participadoCohortePediatricaHint;
	}





	public void setParticipadoCohortePediatricaHint(
			String participadoCohortePediatricaHint) {
		this.participadoCohortePediatricaHint = participadoCohortePediatricaHint;
	}





	public String getCodigoCohorte() {
		return codigoCohorte;
	}





	public void setCodigoCohorte(String codigoCohorte) {
		this.codigoCohorte = codigoCohorte;
	}





	public String getCodigoNuevoParticipante() {
		return codigoNuevoParticipante;
	}





	public void setCodigoNuevoParticipante(String codigoNuevoParticipante) {
		this.codigoNuevoParticipante = codigoNuevoParticipante;
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





	public String getNombre1Padre() {
		return nombre1Padre;
	}





	public void setNombre1Padre(String nombre1Padre) {
		this.nombre1Padre = nombre1Padre;
	}





	public String getNombre1PadreHint() {
		return nombre1PadreHint;
	}





	public void setNombre1PadreHint(String nombre1PadreHint) {
		this.nombre1PadreHint = nombre1PadreHint;
	}





	public String getNombre2Padre() {
		return nombre2Padre;
	}





	public void setNombre2Padre(String nombre2Padre) {
		this.nombre2Padre = nombre2Padre;
	}





	public String getNombre2PadreHint() {
		return nombre2PadreHint;
	}





	public void setNombre2PadreHint(String nombre2PadreHint) {
		this.nombre2PadreHint = nombre2PadreHint;
	}





	public String getApellido1Padre() {
		return apellido1Padre;
	}





	public void setApellido1Padre(String apellido1Padre) {
		this.apellido1Padre = apellido1Padre;
	}





	public String getApellido1PadreHint() {
		return apellido1PadreHint;
	}





	public void setApellido1PadreHint(String apellido1PadreHint) {
		this.apellido1PadreHint = apellido1PadreHint;
	}





	public String getApellido2Padre() {
		return apellido2Padre;
	}





	public void setApellido2Padre(String apellido2Padre) {
		this.apellido2Padre = apellido2Padre;
	}





	public String getApellido2PadreHint() {
		return apellido2PadreHint;
	}





	public void setApellido2PadreHint(String apellido2PadreHint) {
		this.apellido2PadreHint = apellido2PadreHint;
	}





	public String getNombre1Madre() {
		return nombre1Madre;
	}





	public void setNombre1Madre(String nombre1Madre) {
		this.nombre1Madre = nombre1Madre;
	}





	public String getNombre1MadreHint() {
		return nombre1MadreHint;
	}





	public void setNombre1MadreHint(String nombre1MadreHint) {
		this.nombre1MadreHint = nombre1MadreHint;
	}





	public String getNombre2Madre() {
		return nombre2Madre;
	}





	public void setNombre2Madre(String nombre2Madre) {
		this.nombre2Madre = nombre2Madre;
	}





	public String getNombre2MadreHint() {
		return nombre2MadreHint;
	}





	public void setNombre2MadreHint(String nombre2MadreHint) {
		this.nombre2MadreHint = nombre2MadreHint;
	}





	public String getApellido1Madre() {
		return apellido1Madre;
	}





	public void setApellido1Madre(String apellido1Madre) {
		this.apellido1Madre = apellido1Madre;
	}





	public String getApellido1MadreHint() {
		return apellido1MadreHint;
	}





	public void setApellido1MadreHint(String apellido1MadreHint) {
		this.apellido1MadreHint = apellido1MadreHint;
	}





	public String getApellido2Madre() {
		return apellido2Madre;
	}





	public void setApellido2Madre(String apellido2Madre) {
		this.apellido2Madre = apellido2Madre;
	}





	public String getApellido2MadreHint() {
		return apellido2MadreHint;
	}





	public void setApellido2MadreHint(String apellido2MadreHint) {
		this.apellido2MadreHint = apellido2MadreHint;
	}





	public String getEmancipado() {
		return emancipado;
	}





	public void setEmancipado(String emancipado) {
		this.emancipado = emancipado;
	}





	public String getEmancipadoHint() {
		return emancipadoHint;
	}





	public void setEmancipadoHint(String emancipadoHint) {
		this.emancipadoHint = emancipadoHint;
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





	public String getRelacionFamiliarTutorHint() {
		return relacionFamiliarTutorHint;
	}





	public void setRelacionFamiliarTutorHint(String relacionFamiliarTutorHint) {
		this.relacionFamiliarTutorHint = relacionFamiliarTutorHint;
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





	public void setParticipanteOTutorAlfabetoHint(
			String participanteOTutorAlfabetoHint) {
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





	public String getFinTamizajeLabel() {
		return finTamizajeLabel;
	}





	public void setFinTamizajeLabel(String finTamizajeLabel) {
		this.finTamizajeLabel = finTamizajeLabel;
	}

	
	
	
}
