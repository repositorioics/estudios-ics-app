package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;


/**
 * Simple objeto de dominio que representa los datos de parto de los
 * participantes en el estudio
 * 
 * @author William Aviles
 **/

public class DatosPartoBB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private DatosPartoBBId datosPartoId;
	private String tipoParto;
	private String tiempoEmb_sndr;
	private Integer tiempoEmbSemana;
	private String docMedTiempoEmb_sn;
	private String docMedTiempoEmb;
	private String otroDocMedTiempoEmb;
	private Date fum;
	private Integer sg;
	private String fumFueraRango_sn;
	private String fumFueraRango_razon;
	private Integer edadGest;
	private String docMedEdadGest_sn;
	private String docMedEdadGest;
	private String OtroDocMedEdadGest;
	private String prematuro_sndr;
	private String pesoBB_sndr;
	private String pesoBB;
	private String docMedPesoBB_sn;
	private String docMedPesoBB;
	private String otroDocMedPesoBB;
	private MovilInfo movilInfo;
	private Integer otrorecurso1;
	private Integer otrorecurso2;
    private String tallaBB_sndr;
    private String tallaBB;
    private String docMedTallaBB_sn;
    private String docMedTallaBB;
    private String otroDocMedTallaBB;
    private String vacFluMadre_sn;
    private Date fechaVacInf;
    private String docMedFecVacInfMadre_sn;
    private String docMedFecVacInfMadre;
    private String otroDocMedFecVacInfMadre;
    //MA2019
    private String docMedFUM_sn;
	
	public String getTipoParto() {
		return tipoParto;
	}

	public void setTipoParto(String tipoParto) {
		this.tipoParto = tipoParto;
	}

	public String getTiempoEmb_sndr() {
		return tiempoEmb_sndr;
	}

	public void setTiempoEmb_sndr(String tiempoEmb_sndr) {
		this.tiempoEmb_sndr = tiempoEmb_sndr;
	}

	public Integer getTiempoEmbSemana() {
		return tiempoEmbSemana;
	}

	public void setTiempoEmbSemana(Integer tiempoEmbSemana) {
		this.tiempoEmbSemana = tiempoEmbSemana;
	}

	public String getDocMedTiempoEmb_sn() {
		return docMedTiempoEmb_sn;
	}

	public void setDocMedTiempoEmb_sn(String docMedTiempoEmb_sn) {
		this.docMedTiempoEmb_sn = docMedTiempoEmb_sn;
	}

	public String getDocMedTiempoEmb() {
		return docMedTiempoEmb;
	}

	public void setDocMedTiempoEmb(String docMedTiempoEmb) {
		this.docMedTiempoEmb = docMedTiempoEmb;
	}

	public String getOtroDocMedTiempoEmb() {
		return otroDocMedTiempoEmb;
	}

	public void setOtroDocMedTiempoEmb(String otroDocMedTiempoEmb) {
		this.otroDocMedTiempoEmb = otroDocMedTiempoEmb;
	}

	public Date getFum() {
		return fum;
	}

	public void setFum(Date fum) {
		this.fum = fum;
	}

	public Integer getSg() {
		return sg;
	}

	public void setSg(Integer sg) {
		this.sg = sg;
	}

	public Integer getEdadGest() {
		return edadGest;
	}

	public void setEdadGest(Integer edadGest) {
		this.edadGest = edadGest;
	}

	public String getDocMedEdadGest_sn() {
		return docMedEdadGest_sn;
	}

	public void setDocMedEdadGest_sn(String docMedEdadGest_sn) {
		this.docMedEdadGest_sn = docMedEdadGest_sn;
	}

	public String getDocMedEdadGest() {
		return docMedEdadGest;
	}

	public void setDocMedEdadGest(String docMedEdadGest) {
		this.docMedEdadGest = docMedEdadGest;
	}

	public String getOtroDocMedEdadGest() {
		return OtroDocMedEdadGest;
	}

	public void setOtroDocMedEdadGest(String otroDocMedEdadGest) {
		OtroDocMedEdadGest = otroDocMedEdadGest;
	}

	public String getPrematuro_sndr() {
		return prematuro_sndr;
	}

	public void setPrematuro_sndr(String prematuro_sndr) {
		this.prematuro_sndr = prematuro_sndr;
	}

	public String getPesoBB_sndr() {
		return pesoBB_sndr;
	}

	public void setPesoBB_sndr(String pesoBB_sndr) {
		this.pesoBB_sndr = pesoBB_sndr;
	}

	public String getPesoBB() {
		return pesoBB;
	}

	public void setPesoBB(String pesoBB) {
		this.pesoBB = pesoBB;
	}

	public String getDocMedPesoBB_sn() {
		return docMedPesoBB_sn;
	}

	public void setDocMedPesoBB_sn(String docMedPesoBB_sn) {
		this.docMedPesoBB_sn = docMedPesoBB_sn;
	}

	public String getDocMedPesoBB() {
		return docMedPesoBB;
	}

	public void setDocMedPesoBB(String docMedPesoBB) {
		this.docMedPesoBB = docMedPesoBB;
	}

	public String getOtroDocMedPesoBB() {
		return otroDocMedPesoBB;
	}

	public void setOtroDocMedPesoBB(String otroDocMedPesoBB) {
		this.otroDocMedPesoBB = otroDocMedPesoBB;
	}

	public Integer getOtrorecurso1() {
		return otrorecurso1;
	}

	public void setOtrorecurso1(Integer otrorecurso1) {
		this.otrorecurso1 = otrorecurso1;
	}

	public Integer getOtrorecurso2() {
		return otrorecurso2;
	}

	public void setOtrorecurso2(Integer otrorecurso2) {
		this.otrorecurso2 = otrorecurso2;
	}

	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
	}

	public DatosPartoBBId getDatosPartoId() {
		return datosPartoId;
	}

	public void setDatosPartoId(DatosPartoBBId datosPartoId) {
		this.datosPartoId = datosPartoId;
	}

	public String getFumFueraRango_sn() {
		return fumFueraRango_sn;
	}

	public void setFumFueraRango_sn(String fumFueraRango_sn) {
		this.fumFueraRango_sn = fumFueraRango_sn;
	}

	public String getFumFueraRango_razon() {
		return fumFueraRango_razon;
	}

	public void setFumFueraRango_razon(String fumFueraRango_razon) {
		this.fumFueraRango_razon = fumFueraRango_razon;
	}

    public String getTallaBB_sndr() {
        return tallaBB_sndr;
    }

    public void setTallaBB_sndr(String tallaBB_sndr) {
        this.tallaBB_sndr = tallaBB_sndr;
    }

    public String getTallaBB() {
        return tallaBB;
    }

    public void setTallaBB(String tallaBB) {
        this.tallaBB = tallaBB;
    }

    public String getDocMedTallaBB_sn() {
        return docMedTallaBB_sn;
    }

    public void setDocMedTallaBB_sn(String docMedTallaBB_sn) {
        this.docMedTallaBB_sn = docMedTallaBB_sn;
    }

    public String getDocMedTallaBB() {
        return docMedTallaBB;
    }

    public void setDocMedTallaBB(String docMedTallaBB) {
        this.docMedTallaBB = docMedTallaBB;
    }

    public String getOtroDocMedTallaBB() {
        return otroDocMedTallaBB;
    }

    public void setOtroDocMedTallaBB(String otroDocMedTallaBB) {
        this.otroDocMedTallaBB = otroDocMedTallaBB;
    }

    public String getVacFluMadre_sn() {
        return vacFluMadre_sn;
    }

    public void setVacFluMadre_sn(String vacFluMadre_sn) {
        this.vacFluMadre_sn = vacFluMadre_sn;
    }

    public Date getFechaVacInf() {
        return fechaVacInf;
    }

    public void setFechaVacInf(Date fechaVacInf) {
        this.fechaVacInf = fechaVacInf;
    }

    public String getDocMedFecVacInfMadre_sn() {
        return docMedFecVacInfMadre_sn;
    }

    public void setDocMedFecVacInfMadre_sn(String docMedFecVacInfMadre_sn) {
        this.docMedFecVacInfMadre_sn = docMedFecVacInfMadre_sn;
    }

    public String getDocMedFecVacInfMadre() {
        return docMedFecVacInfMadre;
    }

    public void setDocMedFecVacInfMadre(String docMedFecVacInfMadre) {
        this.docMedFecVacInfMadre = docMedFecVacInfMadre;
    }

    public String getOtroDocMedFecVacInfMadre() {
        return otroDocMedFecVacInfMadre;
    }

    public void setOtroDocMedFecVacInfMadre(String otroDocMedFecVacInfMadre) {
        this.otroDocMedFecVacInfMadre = otroDocMedFecVacInfMadre;
    }

    public String getDocMedFUM_sn() {
        return docMedFUM_sn;
    }

    public void setDocMedFUM_sn(String docMedFUM_sn) {
        this.docMedFUM_sn = docMedFUM_sn;
    }
}
