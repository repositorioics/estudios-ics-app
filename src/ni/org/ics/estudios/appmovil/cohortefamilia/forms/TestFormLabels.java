package ni.org.ics.estudios.appmovil.cohortefamilia.forms;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import android.content.res.Resources;


/**
 * Constantes usadas en formulario de tamizaje
 * 
 * @author William Aviles
 * 
 */
public class TestFormLabels {
	
	protected String tp1;
	protected String tp2;
	protected String tp3;
	protected String tp4;
	protected String tp5;
	protected String tp6;
		
	public TestFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		tp1 = res.getString(R.string.tp1);
		tp2 = res.getString(R.string.tp2);
		tp3 = res.getString(R.string.tp3);
		tp4 = res.getString(R.string.tp4);
		tp5 = res.getString(R.string.tp5);
		tp6 = res.getString(R.string.tp6);
	}

	public String getTp1() {
		return tp1;
	}

	public void setTp1(String tp1) {
		this.tp1 = tp1;
	}

	public String getTp2() {
		return tp2;
	}

	public void setTp2(String tp2) {
		this.tp2 = tp2;
	}

	public String getTp3() {
		return tp3;
	}

	public void setTp3(String tp3) {
		this.tp3 = tp3;
	}

	public String getTp4() {
		return tp4;
	}

	public void setTp4(String tp4) {
		this.tp4 = tp4;
	}

	public String getTp5() {
		return tp5;
	}

	public void setTp5(String tp5) {
		this.tp5 = tp5;
	}

	public String getTp6() {
		return tp6;
	}

	public void setTp6(String tp6) {
		this.tp6 = tp6;
	}

	
	
}
