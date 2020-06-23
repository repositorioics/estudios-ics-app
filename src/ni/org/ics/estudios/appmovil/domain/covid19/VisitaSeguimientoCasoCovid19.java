package ni.org.ics.estudios.appmovil.domain.covid19;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;

import java.util.Date;

/**
 * Created by Miguel Salinas on 12/06/2020.
 * V1.0
 */

public class VisitaSeguimientoCasoCovid19 extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCasoVisita;
	private ParticipanteCasoCovid19 codigoParticipanteCaso;
	private Date fechaVisita;
	private String visita;
	private String horaProbableVisita;
	private String expCS;
	private Float temp;
	private Integer saturacionO2;
	private Integer frecResp;
	private Date fecIniPrimerSintoma;
	private String primerSintoma;

	public String getCodigoCasoVisita() {
		return codigoCasoVisita;
	}

	public void setCodigoCasoVisita(String codigoCasoVisita) {
		this.codigoCasoVisita = codigoCasoVisita;
	}

	public ParticipanteCasoCovid19 getCodigoParticipanteCaso() {
		return codigoParticipanteCaso;
	}

	public void setCodigoParticipanteCaso(ParticipanteCasoCovid19 codigoParticipanteCaso) {
		this.codigoParticipanteCaso = codigoParticipanteCaso;
	}

	public Date getFechaVisita() {
		return fechaVisita;
	}

	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	public String getVisita() {
		return visita;
	}

	public void setVisita(String visita) {
		this.visita = visita;
	}

	public String getHoraProbableVisita() {
		return horaProbableVisita;
	}

	public void setHoraProbableVisita(String horaProbableVisita) {
		this.horaProbableVisita = horaProbableVisita;
	}

	public String getExpCS() {
		return expCS;
	}

	public void setExpCS(String expCS) {
		this.expCS = expCS;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	public Integer getSaturacionO2() {
		return saturacionO2;
	}

	public void setSaturacionO2(Integer saturacionO2) {
		this.saturacionO2 = saturacionO2;
	}

	public Integer getFrecResp() {
		return frecResp;
	}

	public void setFrecResp(Integer frecResp) {
		this.frecResp = frecResp;
	}

	public Date getFecIniPrimerSintoma() {
		return fecIniPrimerSintoma;
	}

	public void setFecIniPrimerSintoma(Date fecIniPrimerSintoma) {
		this.fecIniPrimerSintoma = fecIniPrimerSintoma;
	}

	public String getPrimerSintoma() {
		return primerSintoma;
	}

	public void setPrimerSintoma(String primerSintoma) {
		this.primerSintoma = primerSintoma;
	}

	@Override
	public String toString(){
		return codigoParticipanteCaso.getParticipante().getCodigo() + "-" + codigoParticipanteCaso.getCodigoCaso().getFechaIngreso();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitaSeguimientoCasoCovid19)) return false;

        VisitaSeguimientoCasoCovid19 that = (VisitaSeguimientoCasoCovid19) o;

        if (!codigoCasoVisita.equals(that.codigoCasoVisita)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoCasoVisita.hashCode();
        result = 31 * result + codigoParticipanteCaso.getParticipante().hashCode();
        return result;
    }
}
