package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;


/**
 * Simple objeto de dominio que representa los datos de vacunas recibidas
 * 
 * @author Brenda Lopez
 **/

public class NewVacuna implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private NewVacunaId vacunaId;
	private String vacuna_sn;
	private String tvacunano;
	private String otroMotivoTvacunano;
	private MovilInfo movilInfo;
	private Integer otrorecurso1;

	public NewVacunaId getVacunaId() {
		return vacunaId;
	}

	public void setVacunaId(NewVacunaId vacunaId) {
		this.vacunaId = vacunaId;
	}

	

	
	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
	}
	
	public Integer getOtrorecurso1() {
		return otrorecurso1;
	}

	public void setOtrorecurso1(Integer otrorecurso1) {
		this.otrorecurso1 = otrorecurso1;
	}

	public String getVacuna_sn() {
		return vacuna_sn;
	}

	public void setVacuna_sn(String vacuna_sn) {
		this.vacuna_sn = vacuna_sn;
	}

	public String getTvacunano() {
		return tvacunano;
	}

	public void setTvacunano(String tvacunano) {
		this.tvacunano = tvacunano;
	}

	public String getOtroMotivoTvacunano() {
		return otroMotivoTvacunano;
	}

	public void setOtroMotivoTvacunano(String otroMotivoTvacunano) {
		this.otroMotivoTvacunano = otroMotivoTvacunano;
	}



}
