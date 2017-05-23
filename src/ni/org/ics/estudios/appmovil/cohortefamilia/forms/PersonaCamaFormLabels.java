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
public class PersonaCamaFormLabels {
	
	protected String estaEnEstudio;
	protected String participante;
	protected String sexo;
	protected String edad;
	
	
	
	
	public PersonaCamaFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		estaEnEstudio = res.getString(R.string.estaEnEstudio);
		participante = res.getString(R.string.participante);
		sexo = res.getString(R.string.sexo);
		edad = res.getString(R.string.edad);
	}




	public String getEstaEnEstudio() {
		return estaEnEstudio;
	}




	public void setEstaEnEstudio(String estaEnEstudio) {
		this.estaEnEstudio = estaEnEstudio;
	}




	public String getParticipante() {
		return participante;
	}




	public void setParticipante(String participante) {
		this.participante = participante;
	}




	public String getSexo() {
		return sexo;
	}




	public void setSexo(String sexo) {
		this.sexo = sexo;
	}




	public String getEdad() {
		return edad;
	}




	public void setEdad(String edad) {
		this.edad = edad;
	}





	
	
}
