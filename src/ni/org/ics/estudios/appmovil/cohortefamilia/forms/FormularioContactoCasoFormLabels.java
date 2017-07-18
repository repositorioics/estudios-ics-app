package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import android.content.res.Resources;

/**
 * Created by Miguel Salinas on 5/15/2017.
 * V1.0
 */
public class FormularioContactoCasoFormLabels {
	
	protected String partContacto;
	protected String fechaContacto;
	protected String tiempoInteraccion;
	protected String tiempoInteraccionHint;
	protected String tipoInteraccion;
	protected String tipoInteraccionHint;
	
	public FormularioContactoCasoFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		partContacto = res.getString(R.string.partContacto);
		fechaContacto = res.getString(R.string.fechaContacto);
		tiempoInteraccion = res.getString(R.string.tiempoInteraccion);
		tiempoInteraccionHint = res.getString(R.string.tiempoInteraccionHint);
		tipoInteraccion = res.getString(R.string.tipoInteraccion);
		tipoInteraccionHint = res.getString(R.string.tipoInteraccionHint);
	}

	public String getPartContacto() {
		return partContacto;
	}

	public void setPartContacto(String partContacto) {
		this.partContacto = partContacto;
	}

	public String getFechaContacto() {
		return fechaContacto;
	}

	public void setFechaContacto(String fechaContacto) {
		this.fechaContacto = fechaContacto;
	}

	public String getTiempoInteraccion() {
		return tiempoInteraccion;
	}

	public void setTiempoInteraccion(String tiempoInteraccion) {
		this.tiempoInteraccion = tiempoInteraccion;
	}

	public String getTiempoInteraccionHint() {
		return tiempoInteraccionHint;
	}

	public void setTiempoInteraccionHint(String tiempoInteraccionHint) {
		this.tiempoInteraccionHint = tiempoInteraccionHint;
	}

	public String getTipoInteraccion() {
		return tipoInteraccion;
	}

	public void setTipoInteraccion(String tipoInteraccion) {
		this.tipoInteraccion = tipoInteraccion;
	}

	public String getTipoInteraccionHint() {
		return tipoInteraccionHint;
	}

	public void setTipoInteraccionHint(String tipoInteraccionHint) {
		this.tipoInteraccionHint = tipoInteraccionHint;
	}
	
	
	
}
