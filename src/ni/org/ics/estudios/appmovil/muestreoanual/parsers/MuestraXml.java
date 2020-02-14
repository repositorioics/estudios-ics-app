package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 * 
 * @author William Aviles
 **/

public class MuestraXml {
	
	@Element(required=false)
	private String confirmar;
	@Element(required=false)
	private String pbmc;
	@Element(required=false)
	private String paxgene;
	@Element(required=false)
	private String conmx;
	@Element(required=false)
	private String conmxbhc;
	@Element(required=false)
	private String informacion;
	@Element(required=false)
	private String convalesciente;
	@Element(required=false)
	private Integer edad;
	@Element(required=false)
	private String estudio;
	@Element(required=false)
	private String muestranoinf;
	@Element(required=false)
	private String muestrainf;
	@Element(required=false)
	private String leuco;
	@Element(required=false)
	private String bhc;
    @Element(required=false)
    private String ed;
    @Element(required=false)
    private String hd_sn;
    @Element(required=false)
    private String hdPorqueNo;
	//MA2020
	@Element(required=false)
	private String muestranoinf03;
	@Element(required=false)
	private String muestraoinf04;
	@Element(required=false)
	private String muestraoinf05;
	@Element(required=false)
	private String muestraoinf06;
	@Element(required=false)
	private String muestraoinf07;
	@Element(required=false)
	private String muestraoinf08;
	@Element(required=false)
	private String muestraoinf09;
	@Element(required=false)
	private String muestraoinf10;


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
	private String titulo8;
	@Element(required=false)
	private String titulo9;
	@Element(required=false)
	private String titulo10;
	@Element(required=false)
	private String titulo11;
	@Element(required=false)
	private String titulo12;
	@Element(required=false)
	private String titulo13;
	@Element(required=false)
	private String titulo14;
	@Element(required=false)
	private String titulo15;
	@Element(required=false)
	private String titulo16;
	@Element(required=false)
	private String titulo17;
	@Element(required=false)
	private String titulo18;
	@Element(required=false)
	private String titulo19;
	@Element(required=false)
	private String titulo20;
	@Element(required=false)
	private String titulo21;
	@Element(required=false)
	private String titulo22;
	@Element(required=false)
	private String titulo23;
	@Element(required=false)
	private String titulo24;
	@Element(required=false)
	private String titulo25;
	@Element(required=false)
	private String titulo26;
	@Element(required=false)
	private String titulo27;
	@Element(required=false)
	private String titulo28;
	@Element(required=false)
	private String titulo29;
	@Element(required=false)
	private String titulo30;
    @Element(required=false)
    private String titulo31;
    @Element(required=false)
    private String titulo32;
	//MA2020
	@Element(required=false)
	private String titulo50;
	@Element(required=false)
	private String titulo51;
	@Element(required=false)
	private String titulo52;
	@Element(required=false)
	private String titulo53;
	@Element(required=false)
	private String titulo54;
	@Element(required=false)
	private String titulo55;
	@Element(required=false)
	private String titulo56;
	@Element(required=false)
	private String titulo57;
	@Element(required=false)
	private String titulo58;
	@Element(required=false)
	private String titulo59;
	@Element(required=false)
	private String titulo60;
	@Element(required=false)
	private String titulo61;
	@Element(required=false)
	private String titulo62;
	@Element(required=false)
	private String titulo63;
	@Element(required=false)
	private String titulo64;
	@Element(required=false)
	private String titulo65;
	@Element(required=false)
	private String titulo66;
	@Element(required=false)
	private String titulo67;
	@Element(required=false)
	private String titulo68;
	@Element(required=false)
	private String titulo69;
	@Element(required=false)
	private String titulo70;
	@Element(required=false)
	private String titulo71;
	@Element(required=false)
	private String titulo72;
	@Element(required=false)
	private String titulo73;
	@Element(required=false)
	private String titulo74;
	@Element(required=false)
	private String titulo75;
	@Element(required=false)
	private String titulo76;
	@Element(required=false)
	private String titulo77;
	@Element(required=false)
	private String titulo78;
	@Element(required=false)
	private String titulo79;
	@Element(required=false)
	private String titulo80;
	@Element(required=false)
	private String titulo81;
	@Element(required=false)
	private String titulo82;
	@Element(required=false)
	private String titulo83;

	@Element(required=false)
	private Integer fiebreM;
	@Element(required=false)
	private Integer consulta;
	@Element(required=false)
	private Integer tuboBHC;
	@Element(required=false)
	private Integer bhc_razonNo;
	@Element(required=false)
	private String bhc_otraRazonNo;
	@Element(required=false)
	private Integer tuboRojo;
	@Element(required=false)
	private Integer rojo_razonNo;
	@Element(required=false)
	private String rojo_otraRazonNo;
	@Element(required=false)
	private Integer tuboLeu;
	@Element(required=false)
	private Integer pbmc_razonNo;
	@Element(required=false)
	private String pbmc_otraRazonNo;
	@Element(required=false)
	private String horaBHC;
	@Element(required=false)
	private String horaPBMC;
	@Element(required=false)
	private String horaInicioPax;
	@Element(required=false)
	private String horaFinPax;
	@Element(required=false)
	private String codPax;
	@Element(required=false)
	private String terreno;
	@Element(required=false)
	private Integer pinchazos;
	
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
	
	public MuestraXml(){
		
	}

	public String getPbmc() {
		return pbmc;
	}

	public void setPbmc(String pbmc) {
		this.pbmc = pbmc;
	}

	public String getPaxgene() {
		return paxgene;
	}

	public void setPaxgene(String paxgene) {
		this.paxgene = paxgene;
	}

	public String getConmx() {
		return conmx;
	}

	public void setConmx(String conmx) {
		this.conmx = conmx;
	}

	public String getConmxbhc() {
		return conmxbhc;
	}

	public void setConmxbhc(String conmxbhc) {
		this.conmxbhc = conmxbhc;
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	public String getConvalesciente() {
		return convalesciente;
	}

	public void setConvalesciente(String convalesciente) {
		this.convalesciente = convalesciente;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
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

	public String getTitulo8() {
		return titulo8;
	}

	public void setTitulo8(String titulo8) {
		this.titulo8 = titulo8;
	}

	public String getTitulo9() {
		return titulo9;
	}

	public void setTitulo9(String titulo9) {
		this.titulo9 = titulo9;
	}

	public Integer getFiebreM() {
		return fiebreM;
	}

	public void setFiebreM(Integer fiebreM) {
		this.fiebreM = fiebreM;
	}

	public Integer getConsulta() {
		return consulta;
	}

	public void setConsulta(Integer consulta) {
		this.consulta = consulta;
	}

	public Integer getTuboBHC() {
		return tuboBHC;
	}

	public void setTuboBHC(Integer tuboBHC) {
		this.tuboBHC = tuboBHC;
	}

	public Integer getTuboRojo() {
		return tuboRojo;
	}

	public void setTuboRojo(Integer tuboRojo) {
		this.tuboRojo = tuboRojo;
	}

	public Integer getTuboLeu() {
		return tuboLeu;
	}

	public void setTuboLeu(Integer tuboLeu) {
		this.tuboLeu = tuboLeu;
	}

	public Integer getPinchazos() {
		return pinchazos;
	}

	public void setPinchazos(Integer pinchazos) {
		this.pinchazos = pinchazos;
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

	public String getConfirmar() {
		return confirmar;
	}

	public void setConfirmar(String confirmar) {
		this.confirmar = confirmar;
	}

	public String getEstudio() {
		return estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public String getTitulo10() {
		return titulo10;
	}

	public void setTitulo10(String titulo10) {
		this.titulo10 = titulo10;
	}

	public Integer getBhc_razonNo() {
		return bhc_razonNo;
	}

	public void setBhc_razonNo(Integer bhc_razonNo) {
		this.bhc_razonNo = bhc_razonNo;
	}

	public String getBhc_otraRazonNo() {
		return bhc_otraRazonNo;
	}

	public void setBhc_otraRazonNo(String bhc_otraRazonNo) {
		this.bhc_otraRazonNo = bhc_otraRazonNo;
	}

	public Integer getRojo_razonNo() {
		return rojo_razonNo;
	}

	public void setRojo_razonNo(Integer rojo_razonNo) {
		this.rojo_razonNo = rojo_razonNo;
	}

	public String getRojo_otraRazonNo() {
		return rojo_otraRazonNo;
	}

	public void setRojo_otraRazonNo(String rojo_otraRazonNo) {
		this.rojo_otraRazonNo = rojo_otraRazonNo;
	}

	public Integer getPbmc_razonNo() {
		return pbmc_razonNo;
	}

	public void setPbmc_razonNo(Integer pbmc_razonNo) {
		this.pbmc_razonNo = pbmc_razonNo;
	}

	public String getPbmc_otraRazonNo() {
		return pbmc_otraRazonNo;
	}

	public void setPbmc_otraRazonNo(String pbmc_otraRazonNo) {
		this.pbmc_otraRazonNo = pbmc_otraRazonNo;
	}

	public String getTitulo11() {
		return titulo11;
	}

	public void setTitulo11(String titulo11) {
		this.titulo11 = titulo11;
	}

	public String getTitulo12() {
		return titulo12;
	}

	public void setTitulo12(String titulo12) {
		this.titulo12 = titulo12;
	}

	public String getTitulo13() {
		return titulo13;
	}

	public void setTitulo13(String titulo13) {
		this.titulo13 = titulo13;
	}

	public String getTitulo14() {
		return titulo14;
	}

	public void setTitulo14(String titulo14) {
		this.titulo14 = titulo14;
	}

	public String getTitulo15() {
		return titulo15;
	}

	public void setTitulo15(String titulo15) {
		this.titulo15 = titulo15;
	}

	public String getTitulo16() {
		return titulo16;
	}

	public void setTitulo16(String titulo16) {
		this.titulo16 = titulo16;
	}

	public String getTitulo17() {
		return titulo17;
	}

	public void setTitulo17(String titulo17) {
		this.titulo17 = titulo17;
	}

	public String getTitulo18() {
		return titulo18;
	}

	public void setTitulo18(String titulo18) {
		this.titulo18 = titulo18;
	}

	public String getTitulo19() {
		return titulo19;
	}

	public void setTitulo19(String titulo19) {
		this.titulo19 = titulo19;
	}

	public String getTitulo20() {
		return titulo20;
	}

	public void setTitulo20(String titulo20) {
		this.titulo20 = titulo20;
	}

	public String getTitulo21() {
		return titulo21;
	}

	public void setTitulo21(String titulo21) {
		this.titulo21 = titulo21;
	}

	public String getTitulo22() {
		return titulo22;
	}

	public void setTitulo22(String titulo22) {
		this.titulo22 = titulo22;
	}

	public String getTitulo23() {
		return titulo23;
	}

	public void setTitulo23(String titulo23) {
		this.titulo23 = titulo23;
	}

	public String getTitulo24() {
		return titulo24;
	}

	public void setTitulo24(String titulo24) {
		this.titulo24 = titulo24;
	}

	public String getTitulo25() {
		return titulo25;
	}

	public void setTitulo25(String titulo25) {
		this.titulo25 = titulo25;
	}

	public String getTitulo26() {
		return titulo26;
	}

	public void setTitulo26(String titulo26) {
		this.titulo26 = titulo26;
	}

	public String getTitulo27() {
		return titulo27;
	}

	public void setTitulo27(String titulo27) {
		this.titulo27 = titulo27;
	}

	public String getTitulo28() {
		return titulo28;
	}

	public void setTitulo28(String titulo28) {
		this.titulo28 = titulo28;
	}

	public String getTitulo29() {
		return titulo29;
	}

	public void setTitulo29(String titulo29) {
		this.titulo29 = titulo29;
	}

	public String getTitulo30() {
		return titulo30;
	}

	public void setTitulo30(String titulo30) {
		this.titulo30 = titulo30;
	}

    public String getTitulo31() {
        return titulo31;
    }

    public void setTitulo31(String titulo31) {
        this.titulo31 = titulo31;
    }

    public String getTitulo32() {
        return titulo32;
    }

    public void setTitulo32(String titulo32) {
        this.titulo32 = titulo32;
    }

    public String getMuestranoinf() {
		return muestranoinf;
	}

	public void setMuestranoinf(String muestranoinf) {
		this.muestranoinf = muestranoinf;
	}

	public String getMuestrainf() {
		return muestrainf;
	}

	public void setMuestrainf(String muestrainf) {
		this.muestrainf = muestrainf;
	}

	public String getHoraBHC() {
		return horaBHC;
	}

	public void setHoraBHC(String horaBHC) {
		this.horaBHC = horaBHC;
	}

	public String getHoraPBMC() {
		return horaPBMC;
	}

	public void setHoraPBMC(String horaPBMC) {
		this.horaPBMC = horaPBMC;
	}

	public String getHoraInicioPax() {
		return horaInicioPax;
	}

	public void setHoraInicioPax(String horaInicioPax) {
		this.horaInicioPax = horaInicioPax;
	}

	public String getHoraFinPax() {
		return horaFinPax;
	}

	public void setHoraFinPax(String horaFinPax) {
		this.horaFinPax = horaFinPax;
	}

	public String getCodPax() {
		return codPax;
	}

	public void setCodPax(String codPax) {
		this.codPax = codPax;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	public String getLeuco() {
		return leuco;
	}

	public void setLeuco(String leuco) {
		this.leuco = leuco;
	}

	public String getBhc() {
		return bhc;
	}

	public void setBhc(String bhc) {
		this.bhc = bhc;
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

    public String getHd_sn() {
        return hd_sn;
    }

    public void setHd_sn(String hd_sn) {
        this.hd_sn = hd_sn;
    }

    public String getHdPorqueNo() {
        return hdPorqueNo;
    }

    public void setHdPorqueNo(String hdPorqueNo) {
        this.hdPorqueNo = hdPorqueNo;
    }
}
