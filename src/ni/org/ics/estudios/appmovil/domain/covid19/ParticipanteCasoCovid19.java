package ni.org.ics.estudios.appmovil.domain.covid19;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;

import java.util.Date;

/**
 * Created by Miguel Salinas on 01/06/2020.
 * V1.0
 */
public class ParticipanteCasoCovid19 extends BaseMetaData implements Comparable<ParticipanteCasoCovid19> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCasoParticipante;
	private CasoCovid19 codigoCaso;
	private Participante participante;
	private String enfermo;
	private Date fis;
	private Date fif;
	private String positivoPor;
	private String consentimiento;

	public String getCodigoCasoParticipante() {
		return codigoCasoParticipante;
	}

	public void setCodigoCasoParticipante(String codigoCasoParticipante) {
		this.codigoCasoParticipante = codigoCasoParticipante;
	}

	public CasoCovid19 getCodigoCaso() {
		return codigoCaso;
	}

	public void setCodigoCaso(CasoCovid19 codigoCaso) {
		this.codigoCaso = codigoCaso;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public String getEnfermo() {
		return enfermo;
	}

	public void setEnfermo(String enfermo) {
		this.enfermo = enfermo;
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

	@Override
	public String toString(){
		return participante.getCodigo() + "-" + codigoCaso.getFechaIngreso();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipanteCasoCovid19)) return false;

        ParticipanteCasoCovid19 that = (ParticipanteCasoCovid19) o;

        if (!codigoCasoParticipante.equals(that.codigoCasoParticipante)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoCasoParticipante.hashCode();
        result = 31 * result + participante.hashCode();
        return result;
    }

	@Override
	public int compareTo(ParticipanteCasoCovid19 o) {
		if (this.codigoCaso.getCasa() != null) {
			return Integer.compare(Integer.parseInt(this.codigoCaso.getCasa().getCodigoCHF()), Integer.parseInt(o.codigoCaso.getCasa().getCodigoCHF()));
		} else {
			return Integer.compare(this.participante.getCodigo(), o.participante.getCodigo());
		}
		/*
		if ((this.codigoCaso.getCasa() != null && Integer.parseInt(this.codigoCaso.getCasa().getCodigoCHF()) < (Integer.parseInt(o.codigoCaso.getCasa().getCodigoCHF())))
			|| this.participante.getCodigo() < o.participante.getCodigo())
			return -1;

		if ((this.codigoCaso.getCasa() != null && Integer.parseInt(this.codigoCaso.getCasa().getCodigoCHF()) > (Integer.parseInt(o.codigoCaso.getCasa().getCodigoCHF())))
				|| this.participante.getCodigo() > o.participante.getCodigo())
			return 1;
*/
	}
}
