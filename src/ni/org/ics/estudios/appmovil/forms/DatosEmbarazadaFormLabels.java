package ni.org.ics.estudios.appmovil.forms;

import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import android.content.res.Resources;


/**
 * Constantes usadas en formulario de vivienda
 * 
 * @author William Aviles
 * 
 */
public class DatosEmbarazadaFormLabels {
	
	protected String cs;
	protected String csHint;
	protected String nombre1;
	protected String nombre1Hint;
	protected String nombre2;
	protected String nombre2Hint;
	protected String apellido1;
	protected String apellido1Hint;
	protected String apellido2;
	protected String apellido2Hint;
	protected String fechaNac;
	protected String fechaNacHint;
	protected String direccion;
	protected String direccionHint;
	protected String telefonoContacto;
	protected String telefonoContactoHint;
	
	public DatosEmbarazadaFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		cs = res.getString(R.string.available);
		csHint = res.getString(R.string.available);
		nombre1 = res.getString(R.string.available);
		nombre1Hint = res.getString(R.string.available);
		nombre2 = res.getString(R.string.available);
		nombre2Hint = res.getString(R.string.available);
		apellido1 = res.getString(R.string.available);
		apellido1Hint = res.getString(R.string.available);
		apellido2 = res.getString(R.string.available);
		apellido2Hint = res.getString(R.string.available);
		fechaNac = res.getString(R.string.available);
		fechaNacHint = res.getString(R.string.available);
		direccion = res.getString(R.string.available);
		direccionHint = res.getString(R.string.available);
		telefonoContacto = res.getString(R.string.available);
		telefonoContactoHint = res.getString(R.string.available);
	}

	public String getCs() {
		return cs;
	}



	public void setCs(String cs) {
		this.cs = cs;
	}



	public String getCsHint() {
		return csHint;
	}



	public void setCsHint(String csHint) {
		this.csHint = csHint;
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



	public String getFechaNac() {
		return fechaNac;
	}



	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}



	public String getFechaNacHint() {
		return fechaNacHint;
	}



	public void setFechaNacHint(String fechaNacHint) {
		this.fechaNacHint = fechaNacHint;
	}



	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccionHint() {
		return direccionHint;
	}

	public void setDireccionHint(String direccionHint) {
		this.direccionHint = direccionHint;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public String getTelefonoContactoHint() {
		return telefonoContactoHint;
	}

	public void setTelefonoContactoHint(String telefonoContactoHint) {
		this.telefonoContactoHint = telefonoContactoHint;
	}
	
	
}
