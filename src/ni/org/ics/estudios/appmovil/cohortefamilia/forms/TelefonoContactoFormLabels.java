package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import android.content.res.Resources;

/**
 * Created by Miguel Salinas on 5/15/2017.
 * V1.0
 */
public class TelefonoContactoFormLabels {
	
	protected String tipo;
	protected String operadora;
	protected String numero;
	protected String participante;
	
	public TelefonoContactoFormLabels(){
		Resources res = MyIcsApplication.getContext().getResources();
		tipo = res.getString(R.string.tipoTel);
		operadora = res.getString(R.string.operadora);
		numero = res.getString(R.string.numero);
		participante = res.getString(R.string.participante);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOperadora() {
		return operadora;
	}

	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getParticipante() {
		return participante;
	}

	public void setParticipante(String participante) {
		this.participante = participante;
	}

	
	
}
