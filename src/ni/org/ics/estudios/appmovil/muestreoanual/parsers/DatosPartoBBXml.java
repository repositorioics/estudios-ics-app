package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;

/**
 * Simple objeto de dominio que representa los datos de consentimiento de Zika
 * 
 * 
 * @author Brenda Lopez
 **/

public class DatosPartoBBXml {

	/**
	 * 
	 */
	@Element(required=false)
	private String tipoParto;
	@Element(required=false)
	private String tiempoEmb_sndr;
	@Element(required=false)
	private Integer tiempoEmbSemana;
	@Element(required=false)
	private String docMedTiempoEmb_sn;
	@Element(required=false)
	private String docMedTiempoEmb;
	@Element(required=false)
	private String otroDocMedTiempoEmb;
	@Element(required=false)
	private Date fum;
	@Element(required=false)
	private Integer sg;
	@Element(required=false)
	private String fumFueraRango_sn;
	@Element(required=false)
	private String fumFueraRango_razon;
	@Element(required=false)
	private Integer edadGest;
	@Element(required=false)
	private String docMedEdadGest_sn;
	@Element(required=false)
	private String docMedEdadGest;
	@Element(required=false)
	private String OtroDocMedEdadGest;
	@Element(required=false)
	private String prematuro_sndr;
	@Element(required=false)
	private String pesoBB_sndr;
	@Element(required=false)
	private String pesoBB;
	@Element(required=false)
	private String docMedPesoBB_sn;
	@Element(required=false)
	private String docMedPesoBB;
	@Element(required=false)
	private String otroDocMedPesoBB;
	@Element(required=false)
	private Integer otrorecurso1;
	@Element(required=false)
	private Integer otrorecurso2;
    @Element(required=false)
    private String tallaBB_sndr;
    @Element(required=false)
    private String tallabb;
    @Element(required=false)
    private String docMedTallaBB_sn;
    @Element(required=false)
    private String docMedTallaBB;
    @Element(required=false)
    private String otroDocMedTallaBB;
    @Element(required=false)
    private String vacflumadre_sn;
    @Element(required=false)
    private Date fechavacinf;
    @Element(required=false)
    private String docMedFecVacInfMadre_sn;
    @Element(required=false)
    private String docMedFecVacInfMadre;
    @Element(required=false)
    private String otroDocMedFecVacInfMadre;


    @Element(required=false)
	private Integer codigo;
	@Element(required=false)
	private Date fechanac;
	@Element(required=false)
	private String titulo1;
	@Element(required=false)
	private String titulo2;
	@Element(required=false)
	private String titulo3;
	@Element(required=false)
	private String titulo4;
	@Element(required=false)
	private String titulo5;
	@Element(required=false)
	private String titulo6;
	@Element(required=false)
	private String titulo7;
	@Element(required=false)
	private String titulo_final;
	@Element(required=false)
	private String calSG;
    @Element(required=false)
    private String completar;//1 si el formulario se usa solo para completar información que antes del 2018 no se solicitaba
	
	@Element(required=false)
	private String start;
	@Element(required=false)
	private String end;
	@Element(required=false)
	private String deviceid;
	@Element(required=false)
	private String simserial;
	@Element(required=false)
	private String phonenumber;
	@Element(required=false)
	private Date today;
	@Element(required=false)
	private Integer recurso1;
	@Element(required=false)
	private Integer recurso2;
	@Attribute
	private String id;
	
	
	@Element(required=false)
	private Meta meta;
	@Element(required=false)
	private String imei;


	

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getSimserial() {
		return simserial;
	}

	public void setSimserial(String simserial) {
		this.simserial = simserial;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public Integer getRecurso1() {
		return recurso1;
	}

	public void setRecurso1(Integer recurso1) {
		this.recurso1 = recurso1;
	}

	public Integer getRecurso2() {
		return recurso2;
	}

	public void setRecurso2(Integer recurso2) {
		this.recurso2 = recurso2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

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

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Date getFechanac() {
		return fechanac;
	}

	public void setFechanac(Date fechanac) {
		this.fechanac = fechanac;
	}

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public String getTitulo3() {
		return titulo3;
	}

	public void setTitulo3(String titulo3) {
		this.titulo3 = titulo3;
	}

	public String getTitulo4() {
		return titulo4;
	}

	public void setTitulo4(String titulo4) {
		this.titulo4 = titulo4;
	}

	public String getTitulo5() {
		return titulo5;
	}

	public void setTitulo5(String titulo5) {
		this.titulo5 = titulo5;
	}

	public String getTitulo6() {
		return titulo6;
	}

	public void setTitulo6(String titulo6) {
		this.titulo6 = titulo6;
	}

	public String getTitulo7() {
		return titulo7;
	}

	public void setTitulo7(String titulo7) {
		this.titulo7 = titulo7;
	}

	public String getTitulo_final() {
		return titulo_final;
	}

	public void setTitulo_final(String titulo_final) {
		this.titulo_final = titulo_final;
	}

	public String getCalSG() {
		return calSG;
	}

	public void setCalSG(String calSG) {
		this.calSG = calSG;
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

    public String getTallabb() {
        return tallabb;
    }

    public void setTallabb(String tallabb) {
        this.tallabb = tallabb;
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

    public String getVacflumadre_sn() {
        return vacflumadre_sn;
    }

    public void setVacflumadre_sn(String vacflumadre_sn) {
        this.vacflumadre_sn = vacflumadre_sn;
    }

    public Date getFechavacinf() {
        return fechavacinf;
    }

    public void setFechavacinf(Date fechavacinf) {
        this.fechavacinf = fechavacinf;
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

    public String getCompletar() {
        return completar;
    }

    public void setCompletar(String completar) {
        this.completar = completar;
    }
}
