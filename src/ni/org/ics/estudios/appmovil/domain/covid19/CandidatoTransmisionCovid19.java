package ni.org.ics.estudios.appmovil.domain.covid19;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;

import java.util.Date;

/**
 * Created by Miguel Salinas on 01/06/2020.
 * V1.0
 */
public class CandidatoTransmisionCovid19 extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	private Participante participante;
    private String casaCHF;
	private Date fis;
	private Date fif;
	private String positivoPor;
	private String consentimiento;
    private String estActuales;
    
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

    public String getCasaCHF() {
        return casaCHF;
    }

    public void setCasaCHF(String casaCHF) {
        this.casaCHF = casaCHF;
    }

	public Date getFis() {
		return fis;
	}

	public void setFis(Date fis) {
		this.fis = fis;
	}

	public Date getFif() {
		return fif;
	}

	public void setFif(Date fif) {
		this.fif = fif;
	}

	public String getPositivoPor() {
		return positivoPor;
	}

	public void setPositivoPor(String positivoPor) {
		this.positivoPor = positivoPor;
	}

    public String getConsentimiento() {
        return consentimiento;
    }

    public void setConsentimiento(String consentimiento) {
        this.consentimiento = consentimiento;
    }

    public String getEstActuales() {
        return estActuales;
    }

    public void setEstActuales(String estActuales) {
        this.estActuales = estActuales;
    }

    @Override
	public String toString(){
		return participante.getCodigo().toString();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CandidatoTransmisionCovid19)) return false;

        CandidatoTransmisionCovid19 that = (CandidatoTransmisionCovid19) o;

        if (!codigo.equals(that.codigo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigo.hashCode();
        result = 31 * result + participante.hashCode();
        return result;
    }
}
