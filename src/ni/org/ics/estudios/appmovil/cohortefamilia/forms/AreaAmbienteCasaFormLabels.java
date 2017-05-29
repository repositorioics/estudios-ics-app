package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import android.content.res.Resources;

/**
 * Created by Miguel Salinas on 5/15/2017.
 * V1.0
 */
public class AreaAmbienteCasaFormLabels {
	
	protected String tipo;
	protected String numVentanas;
	protected String codigoHabitacion;
	protected String largo;
	protected String ancho;
	protected String totalM2;
	protected String conVentana;
	protected String abierta;
	
	public AreaAmbienteCasaFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		tipo = res.getString(R.string.tipo);
		numVentanas = res.getString(R.string.numVentanas);
		codigoHabitacion = res.getString(R.string.codigoHabitacion);
		largo = res.getString(R.string.largo);
		ancho = res.getString(R.string.ancho);
		totalM2 = res.getString(R.string.area);
		conVentana = res.getString(R.string.conVentana);
		abierta = res.getString(R.string.abierta);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumVentanas() {
		return numVentanas;
	}

	public void setNumVentanas(String numVentanas) {
		this.numVentanas = numVentanas;
	}

	public String getCodigoHabitacion() {
		return codigoHabitacion;
	}

	public void setCodigoHabitacion(String codigoHabitacion) {
		this.codigoHabitacion = codigoHabitacion;
	}

	public String getLargo() {
		return largo;
	}

	public void setLargo(String largo) {
		this.largo = largo;
	}

	public String getAncho() {
		return ancho;
	}

	public void setAncho(String ancho) {
		this.ancho = ancho;
	}

	public String getTotalM2() {
		return totalM2;
	}

	public void setTotalM2(String totalM2) {
		this.totalM2 = totalM2;
	}

	public String getConVentana() {
		return conVentana;
	}

	public void setConVentana(String conVentana) {
		this.conVentana = conVentana;
	}

	public String getAbierta() {
		return abierta;
	}

	public void setAbierta(String abierta) {
		this.abierta = abierta;
	}
}
