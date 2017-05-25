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
public class CuartoFormLabels {
	
	protected String codigoHabitacion;
	protected String cantidadCamas;
	
	
	public CuartoFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		codigoHabitacion = res.getString(R.string.codigoHabitacion);
		cantidadCamas = res.getString(R.string.cantidadCamas);
		
	}


	public String getCodigoHabitacion() {
		return codigoHabitacion;
	}


	public void setCodigoHabitacion(String codigoHabitacion) {
		this.codigoHabitacion = codigoHabitacion;
	}


	public String getCantidadCamas() {
		return cantidadCamas;
	}


	public void setCantidadCamas(String cantidadCamas) {
		this.cantidadCamas = cantidadCamas;
	}
	
	
	
}
