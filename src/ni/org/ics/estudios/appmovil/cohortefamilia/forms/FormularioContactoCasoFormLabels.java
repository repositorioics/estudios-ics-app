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
	protected String cantidadDias;
	protected String cantidadDiasHint;
	protected String fechaContacto2;
	protected String tiempoInteraccion2;
	protected String tipoInteraccion2;
	protected String fechaContacto3;
	protected String tiempoInteraccion3;
	protected String tipoInteraccion3;
	protected String fechaContacto4;
	protected String tiempoInteraccion4;
	protected String tipoInteraccion4;
	protected String fechaContacto5;
	protected String tiempoInteraccion5;
	protected String tipoInteraccion5;

	public FormularioContactoCasoFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		partContacto = res.getString(R.string.partContacto);
		fechaContacto = res.getString(R.string.fechaContacto);
		tiempoInteraccion = res.getString(R.string.tiempoInteraccion);
		tiempoInteraccionHint = res.getString(R.string.tiempoInteraccionHint);
		tipoInteraccion = res.getString(R.string.tipoInteraccion);
		tipoInteraccionHint = res.getString(R.string.tipoInteraccionHint);
		cantidadDias = res.getString(R.string.cantidadDias);
		cantidadDiasHint = res.getString(R.string.cantidadDiasHint);
		fechaContacto2 = res.getString(R.string.fechaContacto2);
		tiempoInteraccion2 = res.getString(R.string.tiempoInteraccion2);
		tipoInteraccion2 = res.getString(R.string.tipoInteraccion2);
		fechaContacto3 = res.getString(R.string.fechaContacto3);
		tiempoInteraccion3 = res.getString(R.string.tiempoInteraccion3);
		tipoInteraccion3 = res.getString(R.string.tipoInteraccion3);
		fechaContacto4 = res.getString(R.string.fechaContacto4);
		tiempoInteraccion4 = res.getString(R.string.tiempoInteraccion4);
		tipoInteraccion4 = res.getString(R.string.tipoInteraccion4);
		fechaContacto5 = res.getString(R.string.fechaContacto5);
		tiempoInteraccion5 = res.getString(R.string.tiempoInteraccion5);
		tipoInteraccion5 = res.getString(R.string.tipoInteraccion5);
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

	public String getCantidadDias() {
		return cantidadDias;
	}

	public String getCantidadDiasHint() {
		return cantidadDiasHint;
	}

	public String getFechaContacto2() {
		return fechaContacto2;
	}

	public String getTiempoInteraccion2() {
		return tiempoInteraccion2;
	}

	public String getTipoInteraccion2() {
		return tipoInteraccion2;
	}

	public String getFechaContacto3() {
		return fechaContacto3;
	}

	public String getTiempoInteraccion3() {
		return tiempoInteraccion3;
	}

	public String getTipoInteraccion3() {
		return tipoInteraccion3;
	}

	public String getFechaContacto4() {
		return fechaContacto4;
	}

	public String getTiempoInteraccion4() {
		return tiempoInteraccion4;
	}

	public String getTipoInteraccion4() {
		return tipoInteraccion4;
	}

	public String getFechaContacto5() {
		return fechaContacto5;
	}

	public String getTiempoInteraccion5() {
		return tiempoInteraccion5;
	}

	public String getTipoInteraccion5() {
		return tipoInteraccion5;
	}
}
