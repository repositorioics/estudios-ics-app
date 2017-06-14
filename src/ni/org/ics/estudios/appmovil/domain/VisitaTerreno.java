package ni.org.ics.estudios.appmovil.domain;

import java.util.Date;

/**
 * Created by FIRSTICT on 4/28/2017.
 * V1.0
 */
public class VisitaTerreno extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoVisita;
	private Casa casa;
	private Date fechaVisita;
	private String visitaExitosa;
	private String razonVisitaNoExitosa;
    private String otraRazonVisitaNoExitosa;

    public String getCodigoVisita() {
		return codigoVisita;
	}

	public void setCodigoVisita(String codigoVisita) {
		this.codigoVisita = codigoVisita;
	}

	public Casa getCasa() {
		return casa;
	}

	public void setCasa(Casa casa) {
		this.casa = casa;
	}

	public Date getFechaVisita() {
		return fechaVisita;
	}

	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	public String getVisitaExitosa() {
		return visitaExitosa;
	}

	public void setVisitaExitosa(String visitaExitosa) {
		this.visitaExitosa = visitaExitosa;
	}

	public String getRazonVisitaNoExitosa() {
		return razonVisitaNoExitosa;
	}

	public void setRazonVisitaNoExitosa(String razonVisitaNoExitosa) {
		this.razonVisitaNoExitosa = razonVisitaNoExitosa;
	}

    public String getOtraRazonVisitaNoExitosa() {
        return otraRazonVisitaNoExitosa;
    }

    public void setOtraRazonVisitaNoExitosa(String otraRazonVisitaNoExitosa) {
        this.otraRazonVisitaNoExitosa = otraRazonVisitaNoExitosa;
    }

    @Override
    public String toString() {
        return "'" + codigoVisita + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitaTerreno)) return false;

        VisitaTerreno visita = (VisitaTerreno) o;

        return (!codigoVisita.equals(visita.codigoVisita));
    }

    @Override
    public int hashCode() {
        return codigoVisita.hashCode();
    }
}
