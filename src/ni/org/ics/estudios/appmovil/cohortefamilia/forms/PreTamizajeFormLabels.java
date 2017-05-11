package ni.org.ics.estudios.appmovil.cohortefamilia.forms;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import android.content.res.Resources;


/**
 * Constantes usadas en formulario de vivienda
 * 
 * @author William Aviles
 * 
 */
public class PreTamizajeFormLabels {
	
	protected String aceptaTamizaje;
	protected String aceptaTamizajeHint;
	protected String razonNoParticipa;
	protected String razonNoParticipaHint;
	
	
	public PreTamizajeFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		aceptaTamizaje = res.getString(R.string.aceptaTamizaje);
		aceptaTamizajeHint = res.getString(R.string.aceptaTamizajeHint);
		razonNoParticipa = res.getString(R.string.razonNoParticipa);
		razonNoParticipaHint = res.getString(R.string.razonNoParticipaHint);
		
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
	
	

	
}
