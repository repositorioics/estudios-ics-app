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
public class CamaFormLabels {
	
	protected String descCama;
	
	
	
	public CamaFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		descCama = res.getString(R.string.descCama);
		
	}



	public String getDescCama() {
		return descCama;
	}



	public void setDescCama(String descCama) {
		this.descCama = descCama;
	}


	
	
}
