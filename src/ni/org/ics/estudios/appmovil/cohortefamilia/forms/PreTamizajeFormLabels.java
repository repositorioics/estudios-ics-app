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
	
	
	public PreTamizajeFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		aceptaTamizaje = res.getString(R.string.aceptaTamizaje);
		aceptaTamizajeHint = res.getString(R.string.aceptaTamizajeHint);
		
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

	
}
