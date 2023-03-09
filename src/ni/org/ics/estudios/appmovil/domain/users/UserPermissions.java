package ni.org.ics.estudios.appmovil.domain.users;

import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;

/**
 * Simple objeto de dominio que representa los permisos de los usuario en los procesos
 * 
 * @author William Aviles
 **/
public class UserPermissions {
	private String username;
	private Boolean muestra=false;
	private Boolean encuestaCasa=false;
	private Boolean encuestaParticipante=false;
	private Boolean encuestaLactancia=false;
	private Boolean encuestaSatisfaccion=false;
	private Boolean obsequio=false;
	private Boolean pesoTalla=false;
	private Boolean vacunas=false;
	private Boolean visitas=false;
	private Boolean recepcion=false;
	private Boolean consentimiento=false;
	private Boolean casazika=false;
	private Boolean tamizajezika=false;
	private Boolean datosparto=false;
	private Boolean pAbdominal=false;
	private Boolean encSatUsu=false;
	private Boolean encSatUsuCc=false;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getMuestra() {
		return muestra;
	}
	public void setMuestra(Boolean muestra) {
		this.muestra = muestra;
	}

	public Boolean getEncuestaCasa() {
		return encuestaCasa;
	}
	public void setEncuestaCasa(Boolean encuestaCasa) {
		this.encuestaCasa = encuestaCasa;
	}

	public Boolean getEncuestaParticipante() {
		return encuestaParticipante;
	}
	public void setEncuestaParticipante(Boolean encuestaParticipante) {
		this.encuestaParticipante = encuestaParticipante;
	}

	public Boolean getEncuestaLactancia() {
		return encuestaLactancia;
	}
	public void setEncuestaLactancia(Boolean encuestaLactancia) {
		this.encuestaLactancia = encuestaLactancia;
	}

	public Boolean getEncuestaSatisfaccion() {
		return encuestaSatisfaccion;
	}
	public void setEncuestaSatisfaccion(Boolean encuestaSatisfaccion) {
		this.encuestaSatisfaccion = encuestaSatisfaccion;
	}

	public Boolean getObsequio() {
		return obsequio;
	}
	public void setObsequio(Boolean obsequio) {
		this.obsequio = obsequio;
	}

	public Boolean getPesoTalla() {
		return pesoTalla;
	}
	public void setPesoTalla(Boolean pesoTalla) {
		this.pesoTalla = pesoTalla;
	}

	public Boolean getVacunas() {
		return vacunas;
	}
	public void setVacunas(Boolean vacunas) {
		this.vacunas = vacunas;
	}

	public Boolean getVisitas() {
		return visitas;
	}
	public void setVisitas(Boolean visitas) {
		this.visitas = visitas;
	}

	public Boolean getRecepcion() {
		return recepcion;
	}
	public void setRecepcion(Boolean recepcion) {
		this.recepcion = recepcion;
	}

	public Boolean getConsentimiento() {
		return consentimiento;
	}
	public void setConsentimiento(Boolean consentimiento) {
		this.consentimiento = consentimiento;
	}

	public Boolean getCasazika() {
		return casazika;
	}
	public void setCasazika(Boolean casazika) {
		this.casazika = casazika;
	}

	public Boolean getTamizajezika() {
		return tamizajezika;
	}
	public void setTamizajezika(Boolean tamizajezika) {
		this.tamizajezika = tamizajezika;
	}

	public Boolean getDatosparto() {
		return datosparto;
	}
	public void setDatosparto(Boolean datosparto) {
		this.datosparto = datosparto;
	}

	public Boolean getpAbdominal() {
		return pAbdominal;
	}

	public void setpAbdominal(Boolean pAbdominal) {
		this.pAbdominal = pAbdominal;
	}

	public Boolean getEncSatUsu() {
		return encSatUsu;
	}

	public void setEncSatUsu(Boolean encSatUsu) {
		this.encSatUsu = encSatUsu;
	}

	public Boolean getEncSatUsuCc() {
		return encSatUsuCc;
	}

	public void setEncSatUsuCc(Boolean encSatUsuCc) {
		this.encSatUsuCc = encSatUsuCc;
	}
}
