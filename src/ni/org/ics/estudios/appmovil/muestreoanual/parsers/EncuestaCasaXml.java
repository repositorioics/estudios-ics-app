package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 * 
 * @author William Aviles
 **/

public class EncuestaCasaXml {
	
	
	
	@Element(required=false)
	private Integer cvivencasa1;
	@Element(required=false)
	private Integer cvivencasa2;
	@Element(required=false)
	private Integer cvivencasa3;
	@Element(required=false)
	private Integer cvivencasa4;
	@Element(required=false)
	private Integer cvivencasa5;
	@Element(required=false)
	private Integer cvivencasa6;
	@Element(required=false)
	private Integer cvivencasa7;
	@Element(required=false)
	private Integer ccuartos;
	@Element(required=false)
	private Integer grifo;
	@Element(required=false)
	private Integer grifoComSN;
	@Element(required=false)
	private Integer horasagua;
	@Element(required=false)
	private String mcasa;
	@Element(required=false)
	private String ocasa;
	@Element(required=false)
	private String piso;
	@Element(required=false)
	private String opiso;
	@Element(required=false)
	private Integer techo;
	@Element(required=false)
	private String otecho;
	@Element(required=false)
	private Integer cpropia;
	@Element(required=false)
	private Integer cabanicos;
	@Element(required=false)
	private Integer ctelevisores;
	@Element(required=false)
	private Integer crefrigeradores;
	@Element(required=false)
	private Integer moto;
	@Element(required=false)
	private Integer carro;
	@Element(required=false)
	private Integer cocinalena;
	@Element(required=false)
	private Integer animalesSN;
	@Element(required=false)
	private Integer pollos;
	@Element(required=false)
	private Integer polloscasa;
	@Element(required=false)
	private Integer patos;
	@Element(required=false)
	private Integer patoscasa;
	@Element(required=false)
	private Integer perros;
	@Element(required=false)
	private Integer perroscasa;
	@Element(required=false)
	private Integer gatos;
	@Element(required=false)
	private Integer gatoscasa;
	@Element(required=false)
	private Integer cerdos;
	@Element(required=false)
	private Integer cerdoscasa;
	
	@Element(required=false)
	private Integer otrorecurso1;
	@Element(required=false)
	private Integer otrorecurso2;
    //CHF + NUEVAS PREGUNTAS MA2018
    @Element(required=false)
    private String viveEmbEnCasa;
    @Element(required=false)
    private Integer CANTIDAD_CUARTOS;
    @Element(required=false)
    private String ALMACENA_AGUA;
    @Element(required=false)
    private String ALMACENA_EN_BARRILES;
    @Element(required=false)
    private Integer NUMERO_BARRILES;
    @Element(required=false)
    private String BARRILES_TAPADOS;
    @Element(required=false)
    private String BARRILES_CON_ABATE;
    @Element(required=false)
    private String ALMACENA_EN_TANQUES;
    @Element(required=false)
    private Integer NUMERO_TANQUES;
    @Element(required=false)
    private String TANQUES_TAPADOS;
    @Element(required=false)
    private String TANQUES_CON_ABATE;
    @Element(required=false)
    private String ALMACENA_EN_PILAS;
    @Element(required=false)
    private Integer NUMERO_PILAS;
    @Element(required=false)
    private String PILAS_TAPADAS;
    @Element(required=false)
    private String PILAS_CON_ABATE;
    @Element(required=false)
    private String ALMACENA_EN_OTROSRECIP;
    @Element(required=false)
    private String DESC_OTROS_RECIPIENTES;
    @Element(required=false)
    private Integer NUMERO_OTROS_RECIPIENTES;
    @Element(required=false)
    private String OTROS_RECIP_TAPADOS;
    @Element(required=false)
    private String OTROSRECIP_CON_ABATE;
    @Element(required=false)
    private String UBICACION_LAVANDERO;
    @Element(required=false)
    private String TIENE_ABANICO;
    @Element(required=false)
    private String TIENE_TELEVISOR;
    @Element(required=false)
    private String TIENE_REFRIGERADOR_FREEZER;
    @Element(required=false)
    private String TIENE_AIRE_ACONDICIONADO;
    @Element(required=false)
    private String FUNCIONAMIENTO_AIRE;
    @Element(required=false)
    private String opcFabCarro;
    @Element(required=false)
    private Integer yearNow;
    @Element(required=false)
    private Integer yearFabCarro;
    @Element(required=false)
    private String TIENE_MICROBUS;
    @Element(required=false)
    private String TIENE_CAMIONETA;
    @Element(required=false)
    private String TIENE_CAMION;
    @Element(required=false)
    private String TIENE_OTRO_MEDIO_TRANS;
    @Element(required=false)
    private String DESC_OTRO_MEDIO_TRANS;
    @Element(required=false)
    private String COCINA_CON_LENIA;
    @Element(required=false)
    private String UBICACION_COCINA_LENIA;
    @Element(required=false)
    private String PERIODICIDAD_COCINA_LENIA;
    @Element(required=false)
    private Integer NUM_DIARIO_COCINA_LENIA;
    @Element(required=false)
    private Integer NUM_SEMANAL_COCINA_LENIA;
    @Element(required=false)
    private Integer NUM_QUINCENAL_COCINA_LENIA;
    @Element(required=false)
    private Integer NUM_MENSUAL_COCINA_LENIA;
    @Element(required=false)
    private String TIENE_GALLINAS;
    @Element(required=false)
    private String TIENE_PATOS;
    @Element(required=false)
    private String TIENE_PERROS;
    @Element(required=false)
    private String TIENE_GATOS;
    @Element(required=false)
    private String TIENE_CERDOS;
    @Element(required=false)
    private String PERS_FUMA_DENTRO_CASA;
    @Element(required=false)
    private String MADRE_FUMA;
    @Element(required=false)
    private Integer CANT_CIGARRILLOS_MADRE;
    @Element(required=false)
    private String PADRE_FUMA;
    @Element(required=false)
    private Integer CANT_CIGARRILLOS_PADRE;
    @Element(required=false)
    private String OTROS_FUMAN;
    @Element(required=false)
    private Integer CANT_OTROS_FUMAN;
    @Element(required=false)
    private Integer CANT_CIGARRILLOS_OTROS;
    @Element(required=false)
    private String servRecolBasura;
    @Element(required=false)
    private Integer frecServRecolBasura;
    @Element(required=false)
    private String llantasOtrosContConAgua;
    @Element(required=false)
    private String opcFabMicrobus;
    @Element(required=false)
    private Integer yearFabMicrobus;
    @Element(required=false)
    private String opcFabCamioneta;
    @Element(required=false)
    private Integer yearFabCamioneta;
    @Element(required=false)
    private String opcFabCamion;
    @Element(required=false)
    private Integer yearFabCamion;
    @Element(required=false)
    private String opcFabOtroMedioTrans;
    @Element(required=false)
    private Integer yearFabOtroMedioTrans;
    //MA2020
	@Element(required=false)
	private String variables;
	@Element(required=false)
	private String medir;
	@Element(required=false)
	private String almacena;
	@Element(required=false)
	private String cambiado_casa;
	@Element(required=false)
	private String remodelacion_casa;
	@Element(required=false)
	private String tiene_vehiculo;
	@Element(required=false)
	private String titulo3;

	@Element(required=false)
	private String titulo1;
	@Element(required=false)
	private String titulo2;
	@Element(required=false)
	private String titulo5;
	@Element(required=false)
	private String titulo4;
	@Element(required=false)
	private String titulo6;
	
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
	
	public EncuestaCasaXml(){
		
	}

	public Integer getCvivencasa1() {
		return cvivencasa1;
	}

	public void setCvivencasa1(Integer cvivencasa1) {
		this.cvivencasa1 = cvivencasa1;
	}

	public Integer getCvivencasa2() {
		return cvivencasa2;
	}

	public void setCvivencasa2(Integer cvivencasa2) {
		this.cvivencasa2 = cvivencasa2;
	}

	public Integer getCvivencasa3() {
		return cvivencasa3;
	}

	public void setCvivencasa3(Integer cvivencasa3) {
		this.cvivencasa3 = cvivencasa3;
	}

	public Integer getCvivencasa4() {
		return cvivencasa4;
	}

	public void setCvivencasa4(Integer cvivencasa4) {
		this.cvivencasa4 = cvivencasa4;
	}

	public Integer getCvivencasa5() {
		return cvivencasa5;
	}

	public void setCvivencasa5(Integer cvivencasa5) {
		this.cvivencasa5 = cvivencasa5;
	}

	public Integer getCvivencasa6() {
		return cvivencasa6;
	}

	public void setCvivencasa6(Integer cvivencasa6) {
		this.cvivencasa6 = cvivencasa6;
	}

	public Integer getCcuartos() {
		return ccuartos;
	}

	public void setCcuartos(Integer ccuartos) {
		this.ccuartos = ccuartos;
	}

	public Integer getGrifo() {
		return grifo;
	}

	public void setGrifo(Integer grifo) {
		this.grifo = grifo;
	}

	public Integer getGrifoComSN() {
		return grifoComSN;
	}

	public void setGrifoComSN(Integer grifoComSN) {
		this.grifoComSN = grifoComSN;
	}

	public Integer getHorasagua() {
		return horasagua;
	}

	public void setHorasagua(Integer horasagua) {
		this.horasagua = horasagua;
	}

	public String getMcasa() {
		return mcasa;
	}

	public void setMcasa(String mcasa) {
		this.mcasa = mcasa;
	}

	public String getOcasa() {
		return ocasa;
	}

	public void setOcasa(String ocasa) {
		this.ocasa = ocasa;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getOpiso() {
		return opiso;
	}

	public void setOpiso(String opiso) {
		this.opiso = opiso;
	}

	public Integer getTecho() {
		return techo;
	}

	public void setTecho(Integer techo) {
		this.techo = techo;
	}

	public String getOtecho() {
		return otecho;
	}

	public void setOtecho(String otecho) {
		this.otecho = otecho;
	}

	public Integer getCpropia() {
		return cpropia;
	}

	public void setCpropia(Integer cpropia) {
		this.cpropia = cpropia;
	}

	public Integer getCabanicos() {
		return cabanicos;
	}

	public void setCabanicos(Integer cabanicos) {
		this.cabanicos = cabanicos;
	}

	public Integer getCtelevisores() {
		return ctelevisores;
	}

	public void setCtelevisores(Integer ctelevisores) {
		this.ctelevisores = ctelevisores;
	}

	public Integer getCrefrigeradores() {
		return crefrigeradores;
	}

	public void setCrefrigeradores(Integer crefrigeradores) {
		this.crefrigeradores = crefrigeradores;
	}

	public Integer getMoto() {
		return moto;
	}

	public void setMoto(Integer moto) {
		this.moto = moto;
	}

	public Integer getCarro() {
		return carro;
	}

	public void setCarro(Integer carro) {
		this.carro = carro;
	}

	public Integer getCocinalena() {
		return cocinalena;
	}

	public void setCocinalena(Integer cocinalena) {
		this.cocinalena = cocinalena;
	}

	public Integer getAnimalesSN() {
		return animalesSN;
	}

	public void setAnimalesSN(Integer animalesSN) {
		this.animalesSN = animalesSN;
	}

	public Integer getPollos() {
		return pollos;
	}

	public void setPollos(Integer pollos) {
		this.pollos = pollos;
	}

	public Integer getPolloscasa() {
		return polloscasa;
	}

	public void setPolloscasa(Integer polloscasa) {
		this.polloscasa = polloscasa;
	}

	public Integer getPatos() {
		return patos;
	}

	public void setPatos(Integer patos) {
		this.patos = patos;
	}

	public Integer getPatoscasa() {
		return patoscasa;
	}

	public void setPatoscasa(Integer patoscasa) {
		this.patoscasa = patoscasa;
	}

	public Integer getPerros() {
		return perros;
	}

	public void setPerros(Integer perros) {
		this.perros = perros;
	}

	public Integer getPerroscasa() {
		return perroscasa;
	}

	public void setPerroscasa(Integer perroscasa) {
		this.perroscasa = perroscasa;
	}

	public Integer getGatos() {
		return gatos;
	}

	public void setGatos(Integer gatos) {
		this.gatos = gatos;
	}

	public Integer getGatoscasa() {
		return gatoscasa;
	}

	public void setGatoscasa(Integer gatoscasa) {
		this.gatoscasa = gatoscasa;
	}

	public Integer getCerdos() {
		return cerdos;
	}

	public void setCerdos(Integer cerdos) {
		this.cerdos = cerdos;
	}

	public Integer getCerdoscasa() {
		return cerdoscasa;
	}

	public void setCerdoscasa(Integer cerdoscasa) {
		this.cerdoscasa = cerdoscasa;
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

	public Integer getCvivencasa7() {
		return cvivencasa7;
	}

	public void setCvivencasa7(Integer cvivencasa7) {
		this.cvivencasa7 = cvivencasa7;
	}

    public String getViveEmbEnCasa() {
        return viveEmbEnCasa;
    }

    public void setViveEmbEnCasa(String viveEmbEnCasa) {
        this.viveEmbEnCasa = viveEmbEnCasa;
    }

    public Integer getCANTIDAD_CUARTOS() {
        return CANTIDAD_CUARTOS;
    }

    public void setCANTIDAD_CUARTOS(Integer CANTIDAD_CUARTOS) {
        this.CANTIDAD_CUARTOS = CANTIDAD_CUARTOS;
    }

    public String getALMACENA_AGUA() {
        return ALMACENA_AGUA;
    }

    public void setALMACENA_AGUA(String ALMACENA_AGUA) {
        this.ALMACENA_AGUA = ALMACENA_AGUA;
    }

    public String getALMACENA_EN_BARRILES() {
        return ALMACENA_EN_BARRILES;
    }

    public void setALMACENA_EN_BARRILES(String ALMACENA_EN_BARRILES) {
        this.ALMACENA_EN_BARRILES = ALMACENA_EN_BARRILES;
    }

    public Integer getNUMERO_BARRILES() {
        return NUMERO_BARRILES;
    }

    public void setNUMERO_BARRILES(Integer NUMERO_BARRILES) {
        this.NUMERO_BARRILES = NUMERO_BARRILES;
    }

    public String getBARRILES_TAPADOS() {
        return BARRILES_TAPADOS;
    }

    public void setBARRILES_TAPADOS(String BARRILES_TAPADOS) {
        this.BARRILES_TAPADOS = BARRILES_TAPADOS;
    }

    public String getBARRILES_CON_ABATE() {
        return BARRILES_CON_ABATE;
    }

    public void setBARRILES_CON_ABATE(String BARRILES_CON_ABATE) {
        this.BARRILES_CON_ABATE = BARRILES_CON_ABATE;
    }

    public String getALMACENA_EN_TANQUES() {
        return ALMACENA_EN_TANQUES;
    }

    public void setALMACENA_EN_TANQUES(String ALMACENA_EN_TANQUES) {
        this.ALMACENA_EN_TANQUES = ALMACENA_EN_TANQUES;
    }

    public Integer getNUMERO_TANQUES() {
        return NUMERO_TANQUES;
    }

    public void setNUMERO_TANQUES(Integer NUMERO_TANQUES) {
        this.NUMERO_TANQUES = NUMERO_TANQUES;
    }

    public String getTANQUES_TAPADOS() {
        return TANQUES_TAPADOS;
    }

    public void setTANQUES_TAPADOS(String TANQUES_TAPADOS) {
        this.TANQUES_TAPADOS = TANQUES_TAPADOS;
    }

    public String getTANQUES_CON_ABATE() {
        return TANQUES_CON_ABATE;
    }

    public void setTANQUES_CON_ABATE(String TANQUES_CON_ABATE) {
        this.TANQUES_CON_ABATE = TANQUES_CON_ABATE;
    }

    public String getALMACENA_EN_PILAS() {
        return ALMACENA_EN_PILAS;
    }

    public void setALMACENA_EN_PILAS(String ALMACENA_EN_PILAS) {
        this.ALMACENA_EN_PILAS = ALMACENA_EN_PILAS;
    }

    public Integer getNUMERO_PILAS() {
        return NUMERO_PILAS;
    }

    public void setNUMERO_PILAS(Integer NUMERO_PILAS) {
        this.NUMERO_PILAS = NUMERO_PILAS;
    }

    public String getPILAS_TAPADAS() {
        return PILAS_TAPADAS;
    }

    public void setPILAS_TAPADAS(String PILAS_TAPADAS) {
        this.PILAS_TAPADAS = PILAS_TAPADAS;
    }

    public String getPILAS_CON_ABATE() {
        return PILAS_CON_ABATE;
    }

    public void setPILAS_CON_ABATE(String PILAS_CON_ABATE) {
        this.PILAS_CON_ABATE = PILAS_CON_ABATE;
    }

    public String getALMACENA_EN_OTROSRECIP() {
        return ALMACENA_EN_OTROSRECIP;
    }

    public void setALMACENA_EN_OTROSRECIP(String ALMACENA_EN_OTROSRECIP) {
        this.ALMACENA_EN_OTROSRECIP = ALMACENA_EN_OTROSRECIP;
    }

    public String getDESC_OTROS_RECIPIENTES() {
        return DESC_OTROS_RECIPIENTES;
    }

    public void setDESC_OTROS_RECIPIENTES(String DESC_OTROS_RECIPIENTES) {
        this.DESC_OTROS_RECIPIENTES = DESC_OTROS_RECIPIENTES;
    }

    public Integer getNUMERO_OTROS_RECIPIENTES() {
        return NUMERO_OTROS_RECIPIENTES;
    }

    public void setNUMERO_OTROS_RECIPIENTES(Integer NUMERO_OTROS_RECIPIENTES) {
        this.NUMERO_OTROS_RECIPIENTES = NUMERO_OTROS_RECIPIENTES;
    }

    public String getOTROS_RECIP_TAPADOS() {
        return OTROS_RECIP_TAPADOS;
    }

    public void setOTROS_RECIP_TAPADOS(String OTROS_RECIP_TAPADOS) {
        this.OTROS_RECIP_TAPADOS = OTROS_RECIP_TAPADOS;
    }

    public String getOTROSRECIP_CON_ABATE() {
        return OTROSRECIP_CON_ABATE;
    }

    public void setOTROSRECIP_CON_ABATE(String OTROSRECIP_CON_ABATE) {
        this.OTROSRECIP_CON_ABATE = OTROSRECIP_CON_ABATE;
    }

    public String getUBICACION_LAVANDERO() {
        return UBICACION_LAVANDERO;
    }

    public void setUBICACION_LAVANDERO(String UBICACION_LAVANDERO) {
        this.UBICACION_LAVANDERO = UBICACION_LAVANDERO;
    }

    public String getTIENE_ABANICO() {
        return TIENE_ABANICO;
    }

    public void setTIENE_ABANICO(String TIENE_ABANICO) {
        this.TIENE_ABANICO = TIENE_ABANICO;
    }

    public String getTIENE_TELEVISOR() {
        return TIENE_TELEVISOR;
    }

    public void setTIENE_TELEVISOR(String TIENE_TELEVISOR) {
        this.TIENE_TELEVISOR = TIENE_TELEVISOR;
    }

    public String getTIENE_REFRIGERADOR_FREEZER() {
        return TIENE_REFRIGERADOR_FREEZER;
    }

    public void setTIENE_REFRIGERADOR_FREEZER(String TIENE_REFRIGERADOR_FREEZER) {
        this.TIENE_REFRIGERADOR_FREEZER = TIENE_REFRIGERADOR_FREEZER;
    }

    public String getTIENE_AIRE_ACONDICIONADO() {
        return TIENE_AIRE_ACONDICIONADO;
    }

    public void setTIENE_AIRE_ACONDICIONADO(String TIENE_AIRE_ACONDICIONADO) {
        this.TIENE_AIRE_ACONDICIONADO = TIENE_AIRE_ACONDICIONADO;
    }

    public String getFUNCIONAMIENTO_AIRE() {
        return FUNCIONAMIENTO_AIRE;
    }

    public void setFUNCIONAMIENTO_AIRE(String FUNCIONAMIENTO_AIRE) {
        this.FUNCIONAMIENTO_AIRE = FUNCIONAMIENTO_AIRE;
    }

    public String getOpcFabCarro() {
        return opcFabCarro;
    }

    public void setOpcFabCarro(String opcFabCarro) {
        this.opcFabCarro = opcFabCarro;
    }

    public Integer getYearNow() {
        return yearNow;
    }

    public void setYearNow(Integer yearNow) {
        this.yearNow = yearNow;
    }

    public Integer getYearFabCarro() {
        return yearFabCarro;
    }

    public void setYearFabCarro(Integer yearFabCarro) {
        this.yearFabCarro = yearFabCarro;
    }

    public String getTIENE_MICROBUS() {
        return TIENE_MICROBUS;
    }

    public void setTIENE_MICROBUS(String TIENE_MICROBUS) {
        this.TIENE_MICROBUS = TIENE_MICROBUS;
    }

    public String getTIENE_CAMIONETA() {
        return TIENE_CAMIONETA;
    }

    public void setTIENE_CAMIONETA(String TIENE_CAMIONETA) {
        this.TIENE_CAMIONETA = TIENE_CAMIONETA;
    }

    public String getTIENE_CAMION() {
        return TIENE_CAMION;
    }

    public void setTIENE_CAMION(String TIENE_CAMION) {
        this.TIENE_CAMION = TIENE_CAMION;
    }

    public String getTIENE_OTRO_MEDIO_TRANS() {
        return TIENE_OTRO_MEDIO_TRANS;
    }

    public void setTIENE_OTRO_MEDIO_TRANS(String TIENE_OTRO_MEDIO_TRANS) {
        this.TIENE_OTRO_MEDIO_TRANS = TIENE_OTRO_MEDIO_TRANS;
    }

    public String getDESC_OTRO_MEDIO_TRANS() {
        return DESC_OTRO_MEDIO_TRANS;
    }

    public void setDESC_OTRO_MEDIO_TRANS(String DESC_OTRO_MEDIO_TRANS) {
        this.DESC_OTRO_MEDIO_TRANS = DESC_OTRO_MEDIO_TRANS;
    }

    public String getCOCINA_CON_LENIA() {
        return COCINA_CON_LENIA;
    }

    public void setCOCINA_CON_LENIA(String COCINA_CON_LENIA) {
        this.COCINA_CON_LENIA = COCINA_CON_LENIA;
    }

    public String getUBICACION_COCINA_LENIA() {
        return UBICACION_COCINA_LENIA;
    }

    public void setUBICACION_COCINA_LENIA(String UBICACION_COCINA_LENIA) {
        this.UBICACION_COCINA_LENIA = UBICACION_COCINA_LENIA;
    }

    public String getPERIODICIDAD_COCINA_LENIA() {
        return PERIODICIDAD_COCINA_LENIA;
    }

    public void setPERIODICIDAD_COCINA_LENIA(String PERIODICIDAD_COCINA_LENIA) {
        this.PERIODICIDAD_COCINA_LENIA = PERIODICIDAD_COCINA_LENIA;
    }

    public Integer getNUM_DIARIO_COCINA_LENIA() {
        return NUM_DIARIO_COCINA_LENIA;
    }

    public void setNUM_DIARIO_COCINA_LENIA(Integer NUM_DIARIO_COCINA_LENIA) {
        this.NUM_DIARIO_COCINA_LENIA = NUM_DIARIO_COCINA_LENIA;
    }

    public Integer getNUM_SEMANAL_COCINA_LENIA() {
        return NUM_SEMANAL_COCINA_LENIA;
    }

    public void setNUM_SEMANAL_COCINA_LENIA(Integer NUM_SEMANAL_COCINA_LENIA) {
        this.NUM_SEMANAL_COCINA_LENIA = NUM_SEMANAL_COCINA_LENIA;
    }

    public Integer getNUM_QUINCENAL_COCINA_LENIA() {
        return NUM_QUINCENAL_COCINA_LENIA;
    }

    public void setNUM_QUINCENAL_COCINA_LENIA(Integer NUM_QUINCENAL_COCINA_LENIA) {
        this.NUM_QUINCENAL_COCINA_LENIA = NUM_QUINCENAL_COCINA_LENIA;
    }

    public Integer getNUM_MENSUAL_COCINA_LENIA() {
        return NUM_MENSUAL_COCINA_LENIA;
    }

    public void setNUM_MENSUAL_COCINA_LENIA(Integer NUM_MENSUAL_COCINA_LENIA) {
        this.NUM_MENSUAL_COCINA_LENIA = NUM_MENSUAL_COCINA_LENIA;
    }

    public String getTIENE_GALLINAS() {
        return TIENE_GALLINAS;
    }

    public void setTIENE_GALLINAS(String TIENE_GALLINAS) {
        this.TIENE_GALLINAS = TIENE_GALLINAS;
    }

    public String getTIENE_PATOS() {
        return TIENE_PATOS;
    }

    public void setTIENE_PATOS(String TIENE_PATOS) {
        this.TIENE_PATOS = TIENE_PATOS;
    }

    public String getTIENE_PERROS() {
        return TIENE_PERROS;
    }

    public void setTIENE_PERROS(String TIENE_PERROS) {
        this.TIENE_PERROS = TIENE_PERROS;
    }

    public String getTIENE_GATOS() {
        return TIENE_GATOS;
    }

    public void setTIENE_GATOS(String TIENE_GATOS) {
        this.TIENE_GATOS = TIENE_GATOS;
    }

    public String getTIENE_CERDOS() {
        return TIENE_CERDOS;
    }

    public void setTIENE_CERDOS(String TIENE_CERDOS) {
        this.TIENE_CERDOS = TIENE_CERDOS;
    }

    public String getPERS_FUMA_DENTRO_CASA() {
        return PERS_FUMA_DENTRO_CASA;
    }

    public void setPERS_FUMA_DENTRO_CASA(String PERS_FUMA_DENTRO_CASA) {
        this.PERS_FUMA_DENTRO_CASA = PERS_FUMA_DENTRO_CASA;
    }

    public String getMADRE_FUMA() {
        return MADRE_FUMA;
    }

    public void setMADRE_FUMA(String MADRE_FUMA) {
        this.MADRE_FUMA = MADRE_FUMA;
    }

    public Integer getCANT_CIGARRILLOS_MADRE() {
        return CANT_CIGARRILLOS_MADRE;
    }

    public void setCANT_CIGARRILLOS_MADRE(Integer CANT_CIGARRILLOS_MADRE) {
        this.CANT_CIGARRILLOS_MADRE = CANT_CIGARRILLOS_MADRE;
    }

    public String getPADRE_FUMA() {
        return PADRE_FUMA;
    }

    public void setPADRE_FUMA(String PADRE_FUMA) {
        this.PADRE_FUMA = PADRE_FUMA;
    }

    public Integer getCANT_CIGARRILLOS_PADRE() {
        return CANT_CIGARRILLOS_PADRE;
    }

    public void setCANT_CIGARRILLOS_PADRE(Integer CANT_CIGARRILLOS_PADRE) {
        this.CANT_CIGARRILLOS_PADRE = CANT_CIGARRILLOS_PADRE;
    }

    public String getOTROS_FUMAN() {
        return OTROS_FUMAN;
    }

    public void setOTROS_FUMAN(String OTROS_FUMAN) {
        this.OTROS_FUMAN = OTROS_FUMAN;
    }

    public Integer getCANT_OTROS_FUMAN() {
        return CANT_OTROS_FUMAN;
    }

    public void setCANT_OTROS_FUMAN(Integer CANT_OTROS_FUMAN) {
        this.CANT_OTROS_FUMAN = CANT_OTROS_FUMAN;
    }

    public Integer getCANT_CIGARRILLOS_OTROS() {
        return CANT_CIGARRILLOS_OTROS;
    }

    public void setCANT_CIGARRILLOS_OTROS(Integer CANT_CIGARRILLOS_OTROS) {
        this.CANT_CIGARRILLOS_OTROS = CANT_CIGARRILLOS_OTROS;
    }

    public String getServRecolBasura() {
        return servRecolBasura;
    }

    public void setServRecolBasura(String servRecolBasura) {
        this.servRecolBasura = servRecolBasura;
    }

    public Integer getFrecServRecolBasura() {
        return frecServRecolBasura;
    }

    public void setFrecServRecolBasura(Integer frecServRecolBasura) {
        this.frecServRecolBasura = frecServRecolBasura;
    }

    public String getLlantasOtrosContConAgua() {
        return llantasOtrosContConAgua;
    }

    public void setLlantasOtrosContConAgua(String llantasOtrosContConAgua) {
        this.llantasOtrosContConAgua = llantasOtrosContConAgua;
    }

    public String getOpcFabMicrobus() {
        return opcFabMicrobus;
    }

    public void setOpcFabMicrobus(String opcFabMicrobus) {
        this.opcFabMicrobus = opcFabMicrobus;
    }

    public Integer getYearFabMicrobus() {
        return yearFabMicrobus;
    }

    public void setYearFabMicrobus(Integer yearFabMicrobus) {
        this.yearFabMicrobus = yearFabMicrobus;
    }

    public String getOpcFabCamioneta() {
        return opcFabCamioneta;
    }

    public void setOpcFabCamioneta(String opcFabCamioneta) {
        this.opcFabCamioneta = opcFabCamioneta;
    }

    public Integer getYearFabCamioneta() {
        return yearFabCamioneta;
    }

    public void setYearFabCamioneta(Integer yearFabCamioneta) {
        this.yearFabCamioneta = yearFabCamioneta;
    }

    public String getOpcFabCamion() {
        return opcFabCamion;
    }

    public void setOpcFabCamion(String opcFabCamion) {
        this.opcFabCamion = opcFabCamion;
    }

    public Integer getYearFabCamion() {
        return yearFabCamion;
    }

    public void setYearFabCamion(Integer yearFabCamion) {
        this.yearFabCamion = yearFabCamion;
    }

    public String getOpcFabOtroMedioTrans() {
        return opcFabOtroMedioTrans;
    }

    public void setOpcFabOtroMedioTrans(String opcFabOtroMedioTrans) {
        this.opcFabOtroMedioTrans = opcFabOtroMedioTrans;
    }

    public Integer getYearFabOtroMedioTrans() {
        return yearFabOtroMedioTrans;
    }

    public void setYearFabOtroMedioTrans(Integer yearFabOtroMedioTrans) {
        this.yearFabOtroMedioTrans = yearFabOtroMedioTrans;
    }

	public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}

	public String getMedir() {
		return medir;
	}

	public void setMedir(String medir) {
		this.medir = medir;
	}

	public String getAlmacena() {
		return almacena;
	}

	public void setAlmacena(String almacena) {
		this.almacena = almacena;
	}

	public String getCambiado_casa() {
		return cambiado_casa;
	}

	public void setCambiado_casa(String cambiado_casa) {
		this.cambiado_casa = cambiado_casa;
	}

	public String getRemodelacion_casa() {
		return remodelacion_casa;
	}

	public void setRemodelacion_casa(String remodelacion_casa) {
		this.remodelacion_casa = remodelacion_casa;
	}

	public String getTiene_vehiculo() {
		return tiene_vehiculo;
	}

	public void setTiene_vehiculo(String tiene_vehiculo) {
		this.tiene_vehiculo = tiene_vehiculo;
	}
}
