package ni.org.ics.estudios.appmovil.cohortefamilia.forms;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import android.content.res.Resources;


/**
 * Constantes usadas en formulario de pre tamizaje
 * 
 * @author William Aviles
 * 
 */
public class PreTamizajeFormLabels {
	
	protected String fechaVisita;
	protected String fechaVisitaHint;
	protected String visitaExitosa;
	protected String visitaExitosaHint;
	protected String razonVisitaNoExitosa;
	protected String razonVisitaNoExitosaHint;
	protected String aceptaTamizajeCasa;
	protected String aceptaTamizajeCasaHint;
	protected String razonNoAceptaTamizajeCasa;
	protected String razonNoAceptaTamizajeCasaHint;
	protected String razonNoAceptaTamizajeCasaLabel;
	
	protected String mismoJefe;
	protected String mismoJefeHint;
	
	protected String codigoCHF;
	protected String codigoCHFHint;
	protected String nombre1JefeFamilia;
	protected String nombre2JefeFamilia;
	protected String apellido1JefeFamilia;
	protected String apellido2JefeFamilia;
	protected String jefeFamiliaCHFHint;
	
	protected String finTamizajeLabel;
	
	public PreTamizajeFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		fechaVisita = res.getString(R.string.fechaVisita);
		fechaVisitaHint = res.getString(R.string.fechaVisitaHint);
		visitaExitosa = res.getString(R.string.visitaExitosa);
		visitaExitosaHint = res.getString(R.string.visitaExitosaHint);
		razonVisitaNoExitosa = res.getString(R.string.razonVisitaNoExitosa);
		razonVisitaNoExitosaHint = res.getString(R.string.razonVisitaNoExitosaHint);
		aceptaTamizajeCasa = res.getString(R.string.aceptaTamizajeCasa);
		aceptaTamizajeCasaHint = res.getString(R.string.aceptaTamizajeCasaHint);
		razonNoAceptaTamizajeCasa = res.getString(R.string.razonNoAceptaTamizajeCasa);
		razonNoAceptaTamizajeCasaHint = res.getString(R.string.razonNoAceptaTamizajeCasaHint);
		razonNoAceptaTamizajeCasaLabel = res.getString(R.string.razonNoAceptaTamizajeCasaLabel);
		
		mismoJefe = res.getString(R.string.mismoJefe);
		mismoJefeHint = res.getString(R.string.mismoJefeHint);
		
		codigoCHF = res.getString(R.string.codigoCHF);
		codigoCHFHint = res.getString(R.string.codigoCHFHint);
		nombre1JefeFamilia = res.getString(R.string.nombre1JefeFamilia);
		nombre2JefeFamilia = res.getString(R.string.nombre2JefeFamilia);
		apellido1JefeFamilia = res.getString(R.string.apellido1JefeFamilia);
		apellido2JefeFamilia = res.getString(R.string.apellido2JefeFamilia);
		jefeFamiliaCHFHint = res.getString(R.string.jefeFamiliaCHFHint);
		
		finTamizajeLabel = res.getString(R.string.finTamizajeLabel);
	}

	public String getFechaVisita() {
		return fechaVisita;
	}

	public void setFechaVisita(String fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	public String getFechaVisitaHint() {
		return fechaVisitaHint;
	}

	public void setFechaVisitaHint(String fechaVisitaHint) {
		this.fechaVisitaHint = fechaVisitaHint;
	}

	public String getVisitaExitosa() {
		return visitaExitosa;
	}

	public void setVisitaExitosa(String visitaExitosa) {
		this.visitaExitosa = visitaExitosa;
	}

	public String getVisitaExitosaHint() {
		return visitaExitosaHint;
	}

	public void setVisitaExitosaHint(String visitaExitosaHint) {
		this.visitaExitosaHint = visitaExitosaHint;
	}

	public String getRazonVisitaNoExitosa() {
		return razonVisitaNoExitosa;
	}

	public void setRazonVisitaNoExitosa(String razonVisitaNoExitosa) {
		this.razonVisitaNoExitosa = razonVisitaNoExitosa;
	}

	public String getRazonVisitaNoExitosaHint() {
		return razonVisitaNoExitosaHint;
	}

	public void setRazonVisitaNoExitosaHint(String razonVisitaNoExitosaHint) {
		this.razonVisitaNoExitosaHint = razonVisitaNoExitosaHint;
	}

	public String getAceptaTamizajeCasa() {
		return aceptaTamizajeCasa;
	}

	public void setAceptaTamizajeCasa(String aceptaTamizajeCasa) {
		this.aceptaTamizajeCasa = aceptaTamizajeCasa;
	}

	public String getAceptaTamizajeCasaHint() {
		return aceptaTamizajeCasaHint;
	}

	public void setAceptaTamizajeCasaHint(String aceptaTamizajeCasaHint) {
		this.aceptaTamizajeCasaHint = aceptaTamizajeCasaHint;
	}

	public String getRazonNoAceptaTamizajeCasa() {
		return razonNoAceptaTamizajeCasa;
	}

	public void setRazonNoAceptaTamizajeCasa(String razonNoAceptaTamizajeCasa) {
		this.razonNoAceptaTamizajeCasa = razonNoAceptaTamizajeCasa;
	}

	public String getRazonNoAceptaTamizajeCasaHint() {
		return razonNoAceptaTamizajeCasaHint;
	}

	public void setRazonNoAceptaTamizajeCasaHint(
			String razonNoAceptaTamizajeCasaHint) {
		this.razonNoAceptaTamizajeCasaHint = razonNoAceptaTamizajeCasaHint;
	}

	public String getRazonNoAceptaTamizajeCasaLabel() {
		return razonNoAceptaTamizajeCasaLabel;
	}

	public void setRazonNoAceptaTamizajeCasaLabel(String razonNoAceptaTamizajeCasaLabel) {
		this.razonNoAceptaTamizajeCasaLabel = razonNoAceptaTamizajeCasaLabel;
	}

	public String getMismoJefe() {
		return mismoJefe;
	}

	public void setMismoJefe(String mismoJefe) {
		this.mismoJefe = mismoJefe;
	}

	public String getMismoJefeHint() {
		return mismoJefeHint;
	}

	public void setMismoJefeHint(String mismoJefeHint) {
		this.mismoJefeHint = mismoJefeHint;
	}

	public String getCodigoCHF() {
		return codigoCHF;
	}

	public void setCodigoCHF(String codigoCHF) {
		this.codigoCHF = codigoCHF;
	}

	public String getCodigoCHFHint() {
		return codigoCHFHint;
	}

	public void setCodigoCHFHint(String codigoCHFHint) {
		this.codigoCHFHint = codigoCHFHint;
	}

	public String getNombre1JefeFamilia() {
		return nombre1JefeFamilia;
	}

	public void setNombre1JefeFamilia(String nombre1JefeFamilia) {
		this.nombre1JefeFamilia = nombre1JefeFamilia;
	}

	public String getNombre2JefeFamilia() {
		return nombre2JefeFamilia;
	}

	public void setNombre2JefeFamilia(String nombre2JefeFamilia) {
		this.nombre2JefeFamilia = nombre2JefeFamilia;
	}

	public String getApellido1JefeFamilia() {
		return apellido1JefeFamilia;
	}

	public void setApellido1JefeFamilia(String apellido1JefeFamilia) {
		this.apellido1JefeFamilia = apellido1JefeFamilia;
	}

	public String getApellido2JefeFamilia() {
		return apellido2JefeFamilia;
	}

	public void setApellido2JefeFamilia(String apellido2JefeFamilia) {
		this.apellido2JefeFamilia = apellido2JefeFamilia;
	}

	public String getJefeFamiliaCHFHint() {
		return jefeFamiliaCHFHint;
	}

	public void setJefeFamiliaCHFHint(String jefeFamiliaCHFHint) {
		this.jefeFamiliaCHFHint = jefeFamiliaCHFHint;
	}

	public String getFinTamizajeLabel() {
		return finTamizajeLabel;
	}

	public void setFinTamizajeLabel(String finTamizajeLabel) {
		this.finTamizajeLabel = finTamizajeLabel;
	}



	
}
