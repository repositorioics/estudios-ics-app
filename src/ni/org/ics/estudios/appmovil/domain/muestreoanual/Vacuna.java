package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;

/**
 * Simple objeto de dominio que representa los datos de vacunas recibidas
 * 
 * @author Brenda Lopez
 **/

public class Vacuna implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private VacunaId vacunaId;
	private Integer vacuna;
	private Date fechaVac;
	private String tipovacuna;
	private Integer tarjetaSN;
	private Integer ndosis;
	private Date fechaInf1;
	private Date fechaInf2;
	private Date fechaInf3;
	private Date fechaInf4;
	private Date fechaInf5;
	private Date fechaInf6;
	private Date fechaInf7;
	private Date fechaInf8;
	private Date fechaInf9;
	private Date fechaInf10;
	private MovilInfo movilInfo;
	private Integer otrorecurso1;

	public Integer getOtrorecurso1() {
		return otrorecurso1;
	}

	public void setOtrorecurso1(Integer otrorecurso1) {
		this.otrorecurso1 = otrorecurso1;
	}

	public VacunaId getVacunaId() {
		return vacunaId;
	}

	public void setVacunaId(VacunaId vacunaId) {
		this.vacunaId = vacunaId;
	}

	public Integer getVacuna() {
		return vacuna;
	}

	public void setVacuna(Integer vacuna) {
		this.vacuna = vacuna;
	}

	public Date getFechaVac() {
		return fechaVac;
	}

	public void setFechaVac(Date fechaVac) {
		this.fechaVac = fechaVac;
	}

	public String getTipovacuna() {
		return tipovacuna;
	}

	public void setTipovacuna(String tipovacuna) {
		this.tipovacuna = tipovacuna;
	}

	public Integer getTarjetaSN() {
		return tarjetaSN;
	}

	public void setTarjetaSN(Integer tarjetaSN) {
		this.tarjetaSN = tarjetaSN;
	}

	public Integer getNdosis() {
		return ndosis;
	}

	public void setNdosis(Integer ndosis) {
		this.ndosis = ndosis;
	}

	public Date getFechaInf1() {
		return fechaInf1;
	}

	public void setFechaInf1(Date fechaInf1) {
		this.fechaInf1 = fechaInf1;
	}

	public Date getFechaInf2() {
		return fechaInf2;
	}

	public void setFechaInf2(Date fechaInf2) {
		this.fechaInf2 = fechaInf2;
	}

	public Date getFechaInf3() {
		return fechaInf3;
	}

	public void setFechaInf3(Date fechaInf3) {
		this.fechaInf3 = fechaInf3;
	}

	public Date getFechaInf4() {
		return fechaInf4;
	}

	public void setFechaInf4(Date fechaInf4) {
		this.fechaInf4 = fechaInf4;
	}

	public Date getFechaInf5() {
		return fechaInf5;
	}

	public void setFechaInf5(Date fechaInf5) {
		this.fechaInf5 = fechaInf5;
	}

	public Date getFechaInf6() {
		return fechaInf6;
	}

	public void setFechaInf6(Date fechaInf6) {
		this.fechaInf6 = fechaInf6;
	}

	public Date getFechaInf7() {
		return fechaInf7;
	}

	public void setFechaInf7(Date fechaInf7) {
		this.fechaInf7 = fechaInf7;
	}

	public Date getFechaInf8() {
		return fechaInf8;
	}

	public void setFechaInf8(Date fechaInf8) {
		this.fechaInf8 = fechaInf8;
	}

	public Date getFechaInf9() {
		return fechaInf9;
	}

	public void setFechaInf9(Date fechaInf9) {
		this.fechaInf9 = fechaInf9;
	}

	public Date getFechaInf10() {
		return fechaInf10;
	}

	public void setFechaInf10(Date fechaInf10) {
		this.fechaInf10 = fechaInf10;
	}

	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
	}

}
