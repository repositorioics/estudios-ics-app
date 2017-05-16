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
	
	protected String aceptaTamizaje;
	protected String aceptaTamizajeHint;
	protected String razonNoParticipa;
	protected String razonNoParticipaHint;
	protected String codigoCHF;
	protected String codigoCHFHint;
	protected String noAcepta;
	
	public PreTamizajeFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		aceptaTamizaje = res.getString(R.string.aceptaTamizajeCasa);
		aceptaTamizajeHint = res.getString(R.string.aceptaTamizajeCasaHint);
		razonNoParticipa = res.getString(R.string.razonNoParticipa);
		razonNoParticipaHint = res.getString(R.string.razonNoParticipaHint);
		codigoCHF = res.getString(R.string.codigoCHF);
		codigoCHFHint = res.getString(R.string.codigoCHFHint);
		noAcepta = res.getString(R.string.noAcepta);
	}


	public String getAceptaTamizaje() {
		return aceptaTamizaje;
	}


	public void setAceptaTamizaje(String aceptaTamizaje) {
		this.aceptaTamizaje = aceptaTamizaje;
	}


	public String getAceptaTamizajeHint() {
		return aceptaTamizajeHint;
	}


	public void setAceptaTamizajeHint(String aceptaTamizajeHint) {
		this.aceptaTamizajeHint = aceptaTamizajeHint;
	}


	public String getRazonNoParticipa() {
		return razonNoParticipa;
	}


	public void setRazonNoParticipa(String razonNoParticipa) {
		this.razonNoParticipa = razonNoParticipa;
	}


	public String getRazonNoParticipaHint() {
		return razonNoParticipaHint;
	}


	public void setRazonNoParticipaHint(String razonNoParticipaHint) {
		this.razonNoParticipaHint = razonNoParticipaHint;
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


	public String getNoAcepta() {
		return noAcepta;
	}


	public void setNoAcepta(String noAcepta) {
		this.noAcepta = noAcepta;
	}

	
}
