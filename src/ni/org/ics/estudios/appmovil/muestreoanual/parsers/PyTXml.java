package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 * 
 * @author William Aviles
 **/

public class PyTXml {
		
	@Element(required=false)
	private String titulo_pt;
	
	@Element(required=false)
	private String titulo_dp1p2_cal;
	
	@Element(required=false)
	private String titulo_dt1t2_cal;
	
	@Element(required=false)
	private String titulo_imcc1_cal;
	@Element(required=false)
	private String titulo_imcc2_cal;
	@Element(required=false)
	private String titulo_imcc3_cal;
	@Element(required=false)
	private String titulo_final_pt;
	
	@Element(required=false)
	private Double peso1;
	@Element(required=false)
	private Double peso2;
	@Element(required=false)
	private Double peso3;
	@Element(required=false)
	private Double talla1;
	@Element(required=false)
	private Double talla2;
	@Element(required=false)
	private Double talla3;
	@Element(required=false)
	private Double imc1;
	@Element(required=false)
	private Double imc2;
	@Element(required=false)
	private Double imc3;
	@Element(required=false)
	private Double difPeso;
	@Element(required=false)
	private Double difTalla;
    @Element(required=false)
    private String TOMA_MEDIDAS;
    @Element(required=false)
    private String RAZON_NO_TOMA_MEDIDAS;
	
	@Element(required=false)
	private Integer otrorecurso1;
	@Element(required=false)
	private Integer otrorecurso2;
	
	
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
	@Element
	private Integer recurso1;
	@Element
	private Integer recurso2;
	@Attribute
	private String id;
	
	
	@Element(required=false)
	private Meta meta;
	
	public PyTXml(){
		
	}
	
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

	public Double getPeso1() {
		return peso1;
	}


	public void setPeso1(Double peso1) {
		this.peso1 = peso1;
	}


	public Double getPeso2() {
		return peso2;
	}


	public void setPeso2(Double peso2) {
		this.peso2 = peso2;
	}


	public Double getPeso3() {
		return peso3;
	}


	public void setPeso3(Double peso3) {
		this.peso3 = peso3;
	}


	public Double getTalla1() {
		return talla1;
	}


	public void setTalla1(Double talla1) {
		this.talla1 = talla1;
	}


	public Double getTalla2() {
		return talla2;
	}


	public void setTalla2(Double talla2) {
		this.talla2 = talla2;
	}


	public Double getTalla3() {
		return talla3;
	}


	public void setTalla3(Double talla3) {
		this.talla3 = talla3;
	}


	public Double getImc1() {
		return imc1;
	}


	public void setImc1(Double imc1) {
		this.imc1 = imc1;
	}


	public Double getImc2() {
		return imc2;
	}


	public void setImc2(Double imc2) {
		this.imc2 = imc2;
	}


	public Double getImc3() {
		return imc3;
	}


	public void setImc3(Double imc3) {
		this.imc3 = imc3;
	}


	public Double getDifPeso() {
		return difPeso;
	}


	public void setDifPeso(Double difPeso) {
		this.difPeso = difPeso;
	}


	public Double getDifTalla() {
		return difTalla;
	}


	public void setDifTalla(Double difTalla) {
		this.difTalla = difTalla;
	}

	public String getTitulo_pt() {
		return titulo_pt;
	}

	public void setTitulo_pt(String titulo_pt) {
		this.titulo_pt = titulo_pt;
	}

	public String getTitulo_dp1p2_cal() {
		return titulo_dp1p2_cal;
	}

	public void setTitulo_dp1p2_cal(String titulo_dp1p2_cal) {
		this.titulo_dp1p2_cal = titulo_dp1p2_cal;
	}

	public String getTitulo_dt1t2_cal() {
		return titulo_dt1t2_cal;
	}

	public void setTitulo_dt1t2_cal(String titulo_dt1t2_cal) {
		this.titulo_dt1t2_cal = titulo_dt1t2_cal;
	}

	public String getTitulo_imcc1_cal() {
		return titulo_imcc1_cal;
	}

	public void setTitulo_imcc1_cal(String titulo_imcc1_cal) {
		this.titulo_imcc1_cal = titulo_imcc1_cal;
	}

	public String getTitulo_imcc2_cal() {
		return titulo_imcc2_cal;
	}

	public void setTitulo_imcc2_cal(String titulo_imcc2_cal) {
		this.titulo_imcc2_cal = titulo_imcc2_cal;
	}

	public String getTitulo_imcc3_cal() {
		return titulo_imcc3_cal;
	}

	public void setTitulo_imcc3_cal(String titulo_imcc3_cal) {
		this.titulo_imcc3_cal = titulo_imcc3_cal;
	}

	public String getTitulo_final_pt() {
		return titulo_final_pt;
	}

	public void setTitulo_final_pt(String titulo_final_pt) {
		this.titulo_final_pt = titulo_final_pt;
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

    public String getTOMA_MEDIDAS() {
        return TOMA_MEDIDAS;
    }

    public void setTOMA_MEDIDAS(String TOMA_MEDIDAS) {
        this.TOMA_MEDIDAS = TOMA_MEDIDAS;
    }

    public String getRAZON_NO_TOMA_MEDIDAS() {
        return RAZON_NO_TOMA_MEDIDAS;
    }

    public void setRAZON_NO_TOMA_MEDIDAS(String RAZON_NO_TOMA_MEDIDAS) {
        this.RAZON_NO_TOMA_MEDIDAS = RAZON_NO_TOMA_MEDIDAS;
    }
}
