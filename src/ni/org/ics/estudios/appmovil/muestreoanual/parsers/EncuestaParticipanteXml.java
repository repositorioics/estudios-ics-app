package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 * 
 * @author William Aviles
 **/

public class EncuestaParticipanteXml {

	@Element(required=false)
	private Integer fiebre;
	@Element(required=false)
	private Integer tiemFieb;
	@Element(required=false)
	private Integer lugarCons;
	@Element(required=false)
	private Integer noCs;
	@Element(required=false)
	private String automed;
	@Element(required=false)
	private Integer escuela;
	@Element(required=false)
	private Integer grado;
	@Element(required=false)
	private Integer turno;
	@Element(required=false)
	private Integer nEscuela;
	@Element(required=false)
	private String otraEscuela;
	@Element(required=false)
	private Integer cuidan;
	@Element(required=false)
	private Integer cuantosCuidan;
	@Element(required=false)
	private Integer cqVive;
	@Element(required=false)
	private Integer lugarPart;
	@Element(required=false)
	private Integer papaAlf;
	@Element(required=false)
	private Integer papaNivel;
	@Element(required=false)
	private Integer papaTra;
	@Element(required=false)
	private Integer papaTipoTra;
	@Element(required=false)
	private Integer mamaAlf;
	@Element(required=false)
	private Integer mamaNivel;
	@Element(required=false)
	private Integer mamaTra;
	@Element(required=false)
	private Integer mamaTipoTra;
	@Element(required=false)
	private Integer comparteHab;
	@Element(required=false)
	private Integer hab1;
	@Element(required=false)
	private Integer hab2;
	@Element(required=false)
	private Integer hab3;
	@Element(required=false)
	private Integer hab4;
	@Element(required=false)
	private Integer hab5;
	@Element(required=false)
	private Integer hab6;
	@Element(required=false)
	private Integer comparteCama;
	@Element(required=false)
	private Integer cama1;
	@Element(required=false)
	private Integer cama2;
	@Element(required=false)
	private Integer cama3;
	@Element(required=false)
	private Integer cama4;
	@Element(required=false)
	private Integer cama5;
	@Element(required=false)
	private Integer cama6;
	@Element(required=false)
	private Integer asma;
	@Element(required=false)
	private Integer silb12m;
	@Element(required=false)
	private Integer sitResf;
	@Element(required=false)
	private Integer sitEjer;
	@Element(required=false)
	private Integer silbMesPas;
	@Element(required=false)
	private Integer difHablar;
	@Element(required=false)
	private Integer vecHablar;
	@Element(required=false)
	private Integer difDormir;
	@Element(required=false)
	private Integer suenoPer;
	@Element(required=false)
	private Integer tos12m;
	@Element(required=false)
	private Integer vecesTos;
	@Element(required=false)
	private Integer tos3Dias;
	@Element(required=false)
	private Integer hosp12m;
	@Element(required=false)
	private Integer med12m;
	@Element(required=false)
	private Integer dep12m;
	@Element(required=false)
	private Integer crisis;
	@Element(required=false)
	private Integer frecAsma;
	@Element(required=false)
	private Integer FumaSN;
	@Element(required=false)
	private String quienFuma;
	@Element(required=false)
	private Integer cantCigarrosMadre;
	@Element(required=false)
	private Integer cantCigarrosPadre;
	@Element(required=false)
	private Integer cantCigarrosOtros;
	
	@Element(required=false)
	private Integer rash;
	@Element(required=false)
	private Integer mesActual;
	@Element(required=false)
	private Integer yearActual;
	@Element(required=false)
	private Integer rash_year;
	@Element(required=false)
	private Integer rash_mes;
	@Element(required=false)
	private Integer rash_mesact;
	@Element(required=false)
	private Integer rashCara;
	@Element(required=false)
	private Integer rashMiembrosSup;
	@Element(required=false)
	private Integer rashTorax;
	@Element(required=false)
	private Integer rashAbdomen;
	@Element(required=false)
	private Integer rashMiembrosInf;
	@Element(required=false)
	private Integer rash_Dias;
	@Element(required=false)
	private Integer ojoRojo;
	@Element(required=false)
	private Integer ojoRojo_year;
	@Element(required=false)
	private Integer ojoRojo_mes;
	@Element(required=false)
	private Integer ojoRojo_mesact;
	@Element(required=false)
	private Integer ojoRojo_Dias;
	@Element(required=false)
	private Integer hormigueo;
	@Element(required=false)
	private Integer hormigueo_year;
	@Element(required=false)
	private Integer hormigueo_mes;
	@Element(required=false)
	private Integer hormigueo_mesact;
	@Element(required=false)
	private Integer hormigueo_Dias;
	@Element(required=false)
	private Integer consultaRashHormigueo;
	@Element(required=false)
	private Integer uSaludRashHormigueo;
	@Element(required=false)
	private Integer cSaludRashHormigueo;
	@Element(required=false)
	private String oCSRashHormigueo;
	@Element(required=false)
	private Integer pSRashHormigueo;
	@Element(required=false)
	private String oPSRashHormigueo;
	@Element(required=false)
	private String diagRashHormigueo;
	@Element(required=false)
	private String chPapaMama;
	@Element(required=false)
	private Date fechana_papa;
	@Element(required=false)
	private Integer cal_edad_papa;
	@Element(required=false)
	private Integer cal_edad2_papa;
	@Element(required=false)
	private String nombpapa1;
	@Element(required=false)
	private String nombpapa2;
	@Element(required=false)
	private String apellipapa1;
	@Element(required=false)
	private String apellipapa2;
	@Element(required=false)
	private Date fechana_mama;
	@Element(required=false)
	private Integer cal_edad_mama;
	@Element(required=false)
	private Integer cal_edad2_mama;
	@Element(required=false)
	private String nombmama1;
	@Element(required=false)
	private String nombmama2;
	@Element(required=false)
	private String apellimama1;
	@Element(required=false)
	private String apellimama2;
	
	@Element(required=false)
	private Integer otrorecurso1;
	@Element(required=false)
	private Integer otrorecurso2;
	
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
	private String nompapa;
	@Element(required=false)
	private String nommama;
	@Element(required=false)
	private String rashParteDelCuerpo;
	@Element(required=false)
	private String generated_table_list_label_158;
	@Element(required=false)
	private String reserved_name_for_field_list_labels_159;

    //CHF + NUEVAS PREGUNTAS MA2018
    @Element(required=false)
    private Integer edad;
    @Element(required=false)
    private String sexo;
    @Element(required=false)
    private String chf;
    @Element(required=false)
    private String EMANCIPADO;
    @Element(required=false)
    private String descEnmancipado;
    @Element(required=false)
    private String otraDescEmanc;
    @Element(required=false)
    private String EMBARAZADA;
    @Element(required=false)
    private Integer SEMANAS_EMBARAZO;
    @Element(required=false)
    private String ALFABETO;
    @Element(required=false)
    private String NIVEL_EDUCACION;
    @Element(required=false)
    private String TRABAJA;
    @Element(required=false)
    private String TIPO_TRABAJO;
    @Element(required=false)
    private String OCUPACION_ACTUAL;
    @Element(required=false)
    private String NINO_ASISTE_ESCUELA;
    @Element(required=false)
    private String GRADO_ESTUDIA_NINO;
    @Element(required=false)
    private String NINO_TRABAJA;
    @Element(required=false)
    private String OCUPACION_ACTUAL_NINO;
    @Element(required=false)
    private String PADRE_ESTUDIO;
    @Element(required=false)
    private String CODIGO_PADRE_ESTUDIO;
    @Element(required=false)
    private String PADRE_ALFABETO;
    @Element(required=false)
    private String TRABAJA_PADRE;
    @Element(required=false)
    private String MADRE_ESTUDIO;
    @Element(required=false)
    private String CODIGO_MADRE_ESTUDIO;
    @Element(required=false)
    private String MADRE_ALFABETA;
    @Element(required=false)
    private String TRABAJA_MADRE;
    @Element(required=false)
    private String FUMA;
    @Element(required=false)
    private String PERIODICIDAD_FUNA;
    @Element(required=false)
    private Integer CANTIDAD_CIGARRILLOS;
    @Element(required=false)
    private String FUMA_DENTRO_CASA;
    @Element(required=false)
    private String TUBERCULOSIS_PULMONAR_ACTUAL;
    @Element(required=false)
    private Integer A_FECHA_DIAG_TUBPUL_ACTUAL;
    @Element(required=false)
    private String M_FECHA_DIAG_TUBPUL_ACTUAL;
    @Element(required=false)
    private String TRATAMIENTO_TUBPUL_ACTUAL;
    @Element(required=false)
    private String COMPLETO_TRATAMIENTO_TUBPUL_ACTUAL;
    @Element(required=false)
    private String TUBERCULOSIS_PULMONAR_PASADO;
    @Element(required=false)
    private String FECHA_DIAG_TUBPUL_PASADO_DES;
    @Element(required=false)
    private Integer A_FECHA_DIAG_TUBPUL_PASADO;
    @Element(required=false)
    private String M_FECHA_DIAG_TUBPUL_PASADO;
    @Element(required=false)
    private String TRATAMIENTO_TUBPUL_PASADO;
    @Element(required=false)
    private String COMPLETO_TRATAMIENTO_TUBPUL_PAS;
    @Element(required=false)
    private String ALERGIA_RESPIRATORIA;
    @Element(required=false)
    private String CARDIOPATIA;
    @Element(required=false)
    private String ENFERM_PULMONAR_OBST_CRONICA;
    @Element(required=false)
    private String DIABETES;
    @Element(required=false)
    private String PRESION_ALTA;
    @Element(required=false)
    private String TOS_SIN_FIEBRE_RESFRIADO;
    @Element(required=false)
    private Integer CANT_ENFERM_CUADROS_RESP;
    @Element(required=false)
    private String OTRAS_ENFERMEDADES;
    @Element(required=false)
    private String DESC_OTRAS_ENFERMEDADES;
    @Element(required=false)
    private String VACUNA_INFLUENZA;
    @Element(required=false)
    private Integer ANIO_VACUNA_INFLUENZA;
    @Element(required=false)
    private String rash6m;
    @Element(required=false)
    private String ojoRojo6m;
    //MA2019
    @Element(required=false)
    private Integer vacunaInfluenzaMes;
    @Element(required=false)
    private String vacunaInfluenzaCSSF;
    @Element(required=false)
    private String vacunaInfluenzaOtro;
    @Element(required=false)
    private String nombreCDI;
    @Element(required=false)
    private String direccionCDI;
	//MA2020
	@Element(required=false)
	private String informacion;
	@Element(required=false)
	private String influenza;
	@Element(required=false)
	private String dengue;
	@Element(required=false)
	private String mostrarAlfabeto;
	@Element(required=false)
	private String mostrarPadreAlfabeto;
	@Element(required=false)
	private String mostrarMadreAlfabeta;
	@Element(required=false)
	private String mostrarNumParto;
	@Element(required=false)
	private String antecedenteTutorCP;
	@Element(required=false)
	private String otroLugarCuidan;
	@Element(required=false)
	private String enfermedadCronica;
	@Element(required=false)
	private String tenidoDengue;
	@Element(required=false)
	private String unidadSaludDengue;
	@Element(required=false)
	private String centroSaludDengue;
	@Element(required=false)
	private String otroCentroSaludDengue;
	@Element(required=false)
	private String puestoSaludDengue;
	@Element(required=false)
	private String otroPuestoSaludDengue;
	@Element(required=false)
	private String hospitalDengue;
	@Element(required=false)
	private String otroHospitalDengue;
	@Element(required=false)
	private String hospitalizadoDengue;
	@Element(required=false)
	private String ambulatorioDengue;
	@Element(required=false)
	private String diagMedicoDengue;
	@Element(required=false)
	private String rashUA; //sustitución de rash6m
	@Element(required=false)
	private String consultaRashUA; //sustitución de consultaRashHormigueo

    @Element(required=false)
	private String start;
	@Element(required=false)
	private String end;
	@Element(required=false)
	private String deviceid;
	@Element(required=false)
	private String simserial;
	@Element(required=false)
	private String imei;
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
    @Attribute
    private String version;

	
	@Element(required=false)
	private Meta meta;
	
	public EncuestaParticipanteXml(){
		
	}

	public Integer getFiebre() {
		return fiebre;
	}

	public void setFiebre(Integer fiebre) {
		this.fiebre = fiebre;
	}

	public Integer getTiemFieb() {
		return tiemFieb;
	}

	public void setTiemFieb(Integer tiemFieb) {
		this.tiemFieb = tiemFieb;
	}

	public Integer getLugarCons() {
		return lugarCons;
	}

	public void setLugarCons(Integer lugarCons) {
		this.lugarCons = lugarCons;
	}

	public Integer getNoCs() {
		return noCs;
	}

	public void setNoCs(Integer noCs) {
		this.noCs = noCs;
	}

	public String getAutomed() {
		return automed;
	}

	public void setAutomed(String automed) {
		this.automed = automed;
	}

	public Integer getEscuela() {
		return escuela;
	}

	public void setEscuela(Integer escuela) {
		this.escuela = escuela;
	}

	public Integer getGrado() {
		return grado;
	}

	public void setGrado(Integer grado) {
		this.grado = grado;
	}

	public Integer getTurno() {
		return turno;
	}

	public void setTurno(Integer turno) {
		this.turno = turno;
	}

	public Integer getnEscuela() {
		return nEscuela;
	}

	public void setnEscuela(Integer nEscuela) {
		this.nEscuela = nEscuela;
	}

	public String getOtraEscuela() {
		return otraEscuela;
	}

	public void setOtraEscuela(String otraEscuela) {
		this.otraEscuela = otraEscuela;
	}

	public Integer getCuidan() {
		return cuidan;
	}

	public void setCuidan(Integer cuidan) {
		this.cuidan = cuidan;
	}

	public Integer getCuantosCuidan() {
		return cuantosCuidan;
	}

	public void setCuantosCuidan(Integer cuantosCuidan) {
		this.cuantosCuidan = cuantosCuidan;
	}

	public Integer getCqVive() {
		return cqVive;
	}

	public void setCqVive(Integer cqVive) {
		this.cqVive = cqVive;
	}
	
	

	public Integer getLugarPart() {
		return lugarPart;
	}

	public void setLugarPart(Integer lugarPart) {
		this.lugarPart = lugarPart;
	}

	public Integer getPapaAlf() {
		return papaAlf;
	}

	public void setPapaAlf(Integer papaAlf) {
		this.papaAlf = papaAlf;
	}

	public Integer getPapaNivel() {
		return papaNivel;
	}

	public void setPapaNivel(Integer papaNivel) {
		this.papaNivel = papaNivel;
	}

	public Integer getPapaTra() {
		return papaTra;
	}

	public void setPapaTra(Integer papaTra) {
		this.papaTra = papaTra;
	}

	public Integer getPapaTipoTra() {
		return papaTipoTra;
	}

	public void setPapaTipoTra(Integer papaTipoTra) {
		this.papaTipoTra = papaTipoTra;
	}

	public Integer getMamaAlf() {
		return mamaAlf;
	}

	public void setMamaAlf(Integer mamaAlf) {
		this.mamaAlf = mamaAlf;
	}

	public Integer getMamaNivel() {
		return mamaNivel;
	}

	public void setMamaNivel(Integer mamaNivel) {
		this.mamaNivel = mamaNivel;
	}

	public Integer getMamaTra() {
		return mamaTra;
	}

	public void setMamaTra(Integer mamaTra) {
		this.mamaTra = mamaTra;
	}

	public Integer getMamaTipoTra() {
		return mamaTipoTra;
	}

	public void setMamaTipoTra(Integer mamaTipoTra) {
		this.mamaTipoTra = mamaTipoTra;
	}

	public Integer getComparteHab() {
		return comparteHab;
	}

	public void setComparteHab(Integer comparteHab) {
		this.comparteHab = comparteHab;
	}

	public Integer getHab1() {
		return hab1;
	}

	public void setHab1(Integer hab1) {
		this.hab1 = hab1;
	}

	public Integer getHab2() {
		return hab2;
	}

	public void setHab2(Integer hab2) {
		this.hab2 = hab2;
	}

	public Integer getHab3() {
		return hab3;
	}

	public void setHab3(Integer hab3) {
		this.hab3 = hab3;
	}

	public Integer getHab4() {
		return hab4;
	}

	public void setHab4(Integer hab4) {
		this.hab4 = hab4;
	}

	public Integer getHab5() {
		return hab5;
	}

	public void setHab5(Integer hab5) {
		this.hab5 = hab5;
	}

	public Integer getHab6() {
		return hab6;
	}

	public void setHab6(Integer hab6) {
		this.hab6 = hab6;
	}

	public Integer getComparteCama() {
		return comparteCama;
	}

	public void setComparteCama(Integer comparteCama) {
		this.comparteCama = comparteCama;
	}

	public Integer getCama1() {
		return cama1;
	}

	public void setCama1(Integer cama1) {
		this.cama1 = cama1;
	}

	public Integer getCama2() {
		return cama2;
	}

	public void setCama2(Integer cama2) {
		this.cama2 = cama2;
	}

	public Integer getCama3() {
		return cama3;
	}

	public void setCama3(Integer cama3) {
		this.cama3 = cama3;
	}

	public Integer getCama4() {
		return cama4;
	}

	public void setCama4(Integer cama4) {
		this.cama4 = cama4;
	}

	public Integer getCama5() {
		return cama5;
	}

	public void setCama5(Integer cama5) {
		this.cama5 = cama5;
	}

	public Integer getCama6() {
		return cama6;
	}

	public void setCama6(Integer cama6) {
		this.cama6 = cama6;
	}

	public Integer getAsma() {
		return asma;
	}

	public void setAsma(Integer asma) {
		this.asma = asma;
	}

	public Integer getSilb12m() {
		return silb12m;
	}

	public void setSilb12m(Integer silb12m) {
		this.silb12m = silb12m;
	}

	public Integer getSitResf() {
		return sitResf;
	}

	public void setSitResf(Integer sitResf) {
		this.sitResf = sitResf;
	}

	public Integer getSitEjer() {
		return sitEjer;
	}

	public void setSitEjer(Integer sitEjer) {
		this.sitEjer = sitEjer;
	}

	public Integer getSilbMesPas() {
		return silbMesPas;
	}

	public void setSilbMesPas(Integer silbMesPas) {
		this.silbMesPas = silbMesPas;
	}

	public Integer getDifHablar() {
		return difHablar;
	}

	public void setDifHablar(Integer difHablar) {
		this.difHablar = difHablar;
	}

	public Integer getVecHablar() {
		return vecHablar;
	}

	public void setVecHablar(Integer vecHablar) {
		this.vecHablar = vecHablar;
	}

	public Integer getDifDormir() {
		return difDormir;
	}

	public void setDifDormir(Integer difDormir) {
		this.difDormir = difDormir;
	}

	public Integer getSuenoPer() {
		return suenoPer;
	}

	public void setSuenoPer(Integer suenoPer) {
		this.suenoPer = suenoPer;
	}

	public Integer getTos12m() {
		return tos12m;
	}

	public void setTos12m(Integer tos12m) {
		this.tos12m = tos12m;
	}

	public Integer getVecesTos() {
		return vecesTos;
	}

	public void setVecesTos(Integer vecesTos) {
		this.vecesTos = vecesTos;
	}

	public Integer getTos3Dias() {
		return tos3Dias;
	}

	public void setTos3Dias(Integer tos3Dias) {
		this.tos3Dias = tos3Dias;
	}

	public Integer getHosp12m() {
		return hosp12m;
	}

	public void setHosp12m(Integer hosp12m) {
		this.hosp12m = hosp12m;
	}

	public Integer getMed12m() {
		return med12m;
	}

	public void setMed12m(Integer med12m) {
		this.med12m = med12m;
	}

	public Integer getDep12m() {
		return dep12m;
	}

	public void setDep12m(Integer dep12m) {
		this.dep12m = dep12m;
	}

	public Integer getCrisis() {
		return crisis;
	}

	public void setCrisis(Integer crisis) {
		this.crisis = crisis;
	}

	public Integer getFrecAsma() {
		return frecAsma;
	}

	public void setFrecAsma(Integer frecAsma) {
		this.frecAsma = frecAsma;
	}

	public Integer getFumaSN() {
		return FumaSN;
	}

	public void setFumaSN(Integer fumaSN) {
		FumaSN = fumaSN;
	}

	public String getQuienFuma() {
		return quienFuma;
	}

	public void setQuienFuma(String quienFuma) {
		this.quienFuma = quienFuma;
	}
	
	public Integer getCantCigarrosMadre() {
		return cantCigarrosMadre;
	}

	public void setCantCigarrosMadre(Integer cantCigarrosMadre) {
		this.cantCigarrosMadre = cantCigarrosMadre;
	}

	public Integer getCantCigarrosPadre() {
		return cantCigarrosPadre;
	}

	public void setCantCigarrosPadre(Integer cantCigarrosPadre) {
		this.cantCigarrosPadre = cantCigarrosPadre;
	}

	public Integer getCantCigarrosOtros() {
		return cantCigarrosOtros;
	}

	public void setCantCigarrosOtros(Integer cantCigarrosOtros) {
		this.cantCigarrosOtros = cantCigarrosOtros;
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

	public Integer getRash() {
		return rash;
	}

	public void setRash(Integer rash) {
		this.rash = rash;
	}

	public Integer getMesActual() {
		return mesActual;
	}

	public void setMesActual(Integer mesActual) {
		this.mesActual = mesActual;
	}

	public Integer getYearActual() {
		return yearActual;
	}

	public void setYearActual(Integer yearActual) {
		this.yearActual = yearActual;
	}

	public Integer getRash_year() {
		return rash_year;
	}

	public void setRash_year(Integer rash_year) {
		this.rash_year = rash_year;
	}

	public Integer getRash_mes() {
		return rash_mes;
	}

	public void setRash_mes(Integer rash_mes) {
		this.rash_mes = rash_mes;
	}

	public Integer getRash_mesact() {
		return rash_mesact;
	}

	public void setRash_mesact(Integer rash_mesact) {
		this.rash_mesact = rash_mesact;
	}

	public Integer getRashCara() {
		return rashCara;
	}

	public void setRashCara(Integer rashCara) {
		this.rashCara = rashCara;
	}

	public Integer getRashMiembrosSup() {
		return rashMiembrosSup;
	}

	public void setRashMiembrosSup(Integer rashMiembrosSup) {
		this.rashMiembrosSup = rashMiembrosSup;
	}

	public Integer getRashTorax() {
		return rashTorax;
	}

	public void setRashTorax(Integer rashTorax) {
		this.rashTorax = rashTorax;
	}

	public Integer getRashAbdomen() {
		return rashAbdomen;
	}

	public void setRashAbdomen(Integer rashAbdomen) {
		this.rashAbdomen = rashAbdomen;
	}

	public Integer getRashMiembrosInf() {
		return rashMiembrosInf;
	}

	public void setRashMiembrosInf(Integer rashMiembrosInf) {
		this.rashMiembrosInf = rashMiembrosInf;
	}

	public Integer getRashDias() {
		return rash_Dias;
	}

	public void setRashDias(Integer rashDias) {
		this.rash_Dias = rashDias;
	}

	public Integer getOjoRojo() {
		return ojoRojo;
	}

	public void setOjoRojo(Integer ojoRojo) {
		this.ojoRojo = ojoRojo;
	}

	public Integer getOjoRojo_year() {
		return ojoRojo_year;
	}

	public void setOjoRojo_year(Integer ojoRojo_year) {
		this.ojoRojo_year = ojoRojo_year;
	}

	public Integer getOjoRojo_mes() {
		return ojoRojo_mes;
	}

	public void setOjoRojo_mes(Integer ojoRojo_mes) {
		this.ojoRojo_mes = ojoRojo_mes;
	}

	public Integer getOjoRojo_mesact() {
		return ojoRojo_mesact;
	}

	public void setOjoRojo_mesact(Integer ojoRojo_mesact) {
		this.ojoRojo_mesact = ojoRojo_mesact;
	}

	public Integer getOjoRojo_Dias() {
		return ojoRojo_Dias;
	}

	public void setOjoRojo_Dias(Integer ojoRojo_Dias) {
		this.ojoRojo_Dias = ojoRojo_Dias;
	}

	public Integer getHormigueo() {
		return hormigueo;
	}

	public void setHormigueo(Integer hormigueo) {
		this.hormigueo = hormigueo;
	}

	public Integer getHormigueo_year() {
		return hormigueo_year;
	}

	public void setHormigueo_year(Integer hormigueo_year) {
		this.hormigueo_year = hormigueo_year;
	}

	public Integer getHormigueo_mes() {
		return hormigueo_mes;
	}

	public void setHormigueo_mes(Integer hormigueo_mes) {
		this.hormigueo_mes = hormigueo_mes;
	}

	public Integer getHormigueo_mesact() {
		return hormigueo_mesact;
	}

	public void setHormigueo_mesact(Integer hormigueo_mesact) {
		this.hormigueo_mesact = hormigueo_mesact;
	}

	public Integer getHormigueo_Dias() {
		return hormigueo_Dias;
	}

	public void setHormigueo_Dias(Integer hormigueo_Dias) {
		this.hormigueo_Dias = hormigueo_Dias;
	}

	public Integer getConsultaRashHormigueo() {
		return consultaRashHormigueo;
	}

	public void setConsultaRashHormigueo(Integer consultaRashHormigueo) {
		this.consultaRashHormigueo = consultaRashHormigueo;
	}

	public Integer getuSaludRashHormigueo() {
		return uSaludRashHormigueo;
	}

	public void setuSaludRashHormigueo(Integer uSaludRashHormigueo) {
		this.uSaludRashHormigueo = uSaludRashHormigueo;
	}

	public Integer getcSaludRashHormigueo() {
		return cSaludRashHormigueo;
	}

	public void setcSaludRashHormigueo(Integer cSaludRashHormigueo) {
		this.cSaludRashHormigueo = cSaludRashHormigueo;
	}

	public String getoCSRashHormigueo() {
		return oCSRashHormigueo;
	}

	public void setoCSRashHormigueo(String oCSRashHormigueo) {
		this.oCSRashHormigueo = oCSRashHormigueo;
	}

	public Integer getpSRashHormigueo() {
		return pSRashHormigueo;
	}

	public void setpSRashHormigueo(Integer pSRashHormigueo) {
		this.pSRashHormigueo = pSRashHormigueo;
	}

	public String getoPSRashHormigueo() {
		return oPSRashHormigueo;
	}

	public void setoPSRashHormigueo(String oPSRashHormigueo) {
		this.oPSRashHormigueo = oPSRashHormigueo;
	}

	public String getDiagRashHormigueo() {
		return diagRashHormigueo;
	}

	public void setDiagRashHormigueo(String diagRashHormigueo) {
		this.diagRashHormigueo = diagRashHormigueo;
	}

	public String getChPapaMama() {
		return chPapaMama;
	}

	public void setChPapaMama(String chPapaMama) {
		this.chPapaMama = chPapaMama;
	}

	public Date getFechana_papa() {
		return fechana_papa;
	}

	public void setFechana_papa(Date fechana_papa) {
		this.fechana_papa = fechana_papa;
	}

	public Integer getCal_edad_papa() {
		return cal_edad_papa;
	}

	public void setCal_edad_papa(Integer cal_edad_papa) {
		this.cal_edad_papa = cal_edad_papa;
	}

	public Integer getCal_edad2_papa() {
		return cal_edad2_papa;
	}

	public void setCal_edad2_papa(Integer cal_edad2_papa) {
		this.cal_edad2_papa = cal_edad2_papa;
	}

	public String getNombpapa1() {
		return nombpapa1;
	}

	public void setNombpapa1(String nombpapa1) {
		this.nombpapa1 = nombpapa1;
	}

	public String getNombpapa2() {
		return nombpapa2;
	}

	public void setNombpapa2(String nombpapa2) {
		this.nombpapa2 = nombpapa2;
	}

	public String getApellipapa1() {
		return apellipapa1;
	}

	public void setApellipapa1(String apellipapa1) {
		this.apellipapa1 = apellipapa1;
	}

	public String getApellipapa2() {
		return apellipapa2;
	}

	public void setApellipapa2(String apellipapa2) {
		this.apellipapa2 = apellipapa2;
	}

	public Date getFechana_mama() {
		return fechana_mama;
	}

	public void setFechana_mama(Date fechana_mama) {
		this.fechana_mama = fechana_mama;
	}

	public Integer getCal_edad_mama() {
		return cal_edad_mama;
	}

	public void setCal_edad_mama(Integer cal_edad_mama) {
		this.cal_edad_mama = cal_edad_mama;
	}

	public Integer getCal_edad2_mama() {
		return cal_edad2_mama;
	}

	public void setCal_edad2_mama(Integer cal_edad2_mama) {
		this.cal_edad2_mama = cal_edad2_mama;
	}

	public String getNombmama1() {
		return nombmama1;
	}

	public void setNombmama1(String nombmama1) {
		this.nombmama1 = nombmama1;
	}

	public String getNombmama2() {
		return nombmama2;
	}

	public void setNombmama2(String nombmama2) {
		this.nombmama2 = nombmama2;
	}

	public String getApellimama1() {
		return apellimama1;
	}

	public void setApellimama1(String apellimama1) {
		this.apellimama1 = apellimama1;
	}

	public String getApellimama2() {
		return apellimama2;
	}

	public void setApellimama2(String apellimama2) {
		this.apellimama2 = apellimama2;
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

	public String getTitulo10() {
		return titulo10;
	}

	public void setTitulo10(String titulo10) {
		this.titulo10 = titulo10;
	}

	public String getNompapa() {
		return nompapa;
	}

	public void setNompapa(String nompapa) {
		this.nompapa = nompapa;
	}

	public String getNommama() {
		return nommama;
	}

	public void setNommama(String nommama) {
		this.nommama = nommama;
	}

	public String getRashParteDelCuerpo() {
		return rashParteDelCuerpo;
	}

	public void setRashParteDelCuerpo(String rashParteDelCuerpo) {
		this.rashParteDelCuerpo = rashParteDelCuerpo;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getGenerated_table_list_label_158() {
		return generated_table_list_label_158;
	}

	public void setGenerated_table_list_label_158(String generated_table_list_label_158) {
		this.generated_table_list_label_158 = generated_table_list_label_158;
	}

	public String getReserved_name_for_field_list_labels_159() {
		return reserved_name_for_field_list_labels_159;
	}

	public void setReserved_name_for_field_list_labels_159(String reserved_name_for_field_list_labels_159) {
		this.reserved_name_for_field_list_labels_159 = reserved_name_for_field_list_labels_159;
	}

	public Integer getRash_Dias() {
		return rash_Dias;
	}

	public void setRash_Dias(Integer rash_Dias) {
		this.rash_Dias = rash_Dias;
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


    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getChf() {
        return chf;
    }

    public void setChf(String chf) {
        this.chf = chf;
    }

    public String getEMANCIPADO() {
        return EMANCIPADO;
    }

    public void setEMANCIPADO(String EMANCIPADO) {
        this.EMANCIPADO = EMANCIPADO;
    }

    public String getDescEnmancipado() {
        return descEnmancipado;
    }

    public void setDescEnmancipado(String descEnmancipado) {
        this.descEnmancipado = descEnmancipado;
    }

    public String getOtraDescEmanc() {
        return otraDescEmanc;
    }

    public void setOtraDescEmanc(String otraDescEmanc) {
        this.otraDescEmanc = otraDescEmanc;
    }

    public String getEMBARAZADA() {
        return EMBARAZADA;
    }

    public void setEMBARAZADA(String EMBARAZADA) {
        this.EMBARAZADA = EMBARAZADA;
    }

    public Integer getSEMANAS_EMBARAZO() {
        return SEMANAS_EMBARAZO;
    }

    public void setSEMANAS_EMBARAZO(Integer SEMANAS_EMBARAZO) {
        this.SEMANAS_EMBARAZO = SEMANAS_EMBARAZO;
    }

    public String getALFABETO() {
        return ALFABETO;
    }

    public void setALFABETO(String ALFABETO) {
        this.ALFABETO = ALFABETO;
    }

    public String getNIVEL_EDUCACION() {
        return NIVEL_EDUCACION;
    }

    public void setNIVEL_EDUCACION(String NIVEL_EDUCACION) {
        this.NIVEL_EDUCACION = NIVEL_EDUCACION;
    }

    public String getTRABAJA() {
        return TRABAJA;
    }

    public void setTRABAJA(String TRABAJA) {
        this.TRABAJA = TRABAJA;
    }

    public String getTIPO_TRABAJO() {
        return TIPO_TRABAJO;
    }

    public void setTIPO_TRABAJO(String TIPO_TRABAJO) {
        this.TIPO_TRABAJO = TIPO_TRABAJO;
    }

    public String getOCUPACION_ACTUAL() {
        return OCUPACION_ACTUAL;
    }

    public void setOCUPACION_ACTUAL(String OCUPACION_ACTUAL) {
        this.OCUPACION_ACTUAL = OCUPACION_ACTUAL;
    }

    public String getNINO_ASISTE_ESCUELA() {
        return NINO_ASISTE_ESCUELA;
    }

    public void setNINO_ASISTE_ESCUELA(String NINO_ASISTE_ESCUELA) {
        this.NINO_ASISTE_ESCUELA = NINO_ASISTE_ESCUELA;
    }

    public String getGRADO_ESTUDIA_NINO() {
        return GRADO_ESTUDIA_NINO;
    }

    public void setGRADO_ESTUDIA_NINO(String GRADO_ESTUDIA_NINO) {
        this.GRADO_ESTUDIA_NINO = GRADO_ESTUDIA_NINO;
    }

    public String getNINO_TRABAJA() {
        return NINO_TRABAJA;
    }

    public void setNINO_TRABAJA(String NINO_TRABAJA) {
        this.NINO_TRABAJA = NINO_TRABAJA;
    }

    public String getOCUPACION_ACTUAL_NINO() {
        return OCUPACION_ACTUAL_NINO;
    }

    public void setOCUPACION_ACTUAL_NINO(String OCUPACION_ACTUAL_NINO) {
        this.OCUPACION_ACTUAL_NINO = OCUPACION_ACTUAL_NINO;
    }

    public String getPADRE_ESTUDIO() {
        return PADRE_ESTUDIO;
    }

    public void setPADRE_ESTUDIO(String PADRE_ESTUDIO) {
        this.PADRE_ESTUDIO = PADRE_ESTUDIO;
    }

    public String getCODIGO_PADRE_ESTUDIO() {
        return CODIGO_PADRE_ESTUDIO;
    }

    public void setCODIGO_PADRE_ESTUDIO(String CODIGO_PADRE_ESTUDIO) {
        this.CODIGO_PADRE_ESTUDIO = CODIGO_PADRE_ESTUDIO;
    }

    public String getPADRE_ALFABETO() {
        return PADRE_ALFABETO;
    }

    public void setPADRE_ALFABETO(String PADRE_ALFABETO) {
        this.PADRE_ALFABETO = PADRE_ALFABETO;
    }

    public String getTRABAJA_PADRE() {
        return TRABAJA_PADRE;
    }

    public void setTRABAJA_PADRE(String TRABAJA_PADRE) {
        this.TRABAJA_PADRE = TRABAJA_PADRE;
    }

    public String getMADRE_ESTUDIO() {
        return MADRE_ESTUDIO;
    }

    public void setMADRE_ESTUDIO(String MADRE_ESTUDIO) {
        this.MADRE_ESTUDIO = MADRE_ESTUDIO;
    }

    public String getCODIGO_MADRE_ESTUDIO() {
        return CODIGO_MADRE_ESTUDIO;
    }

    public void setCODIGO_MADRE_ESTUDIO(String CODIGO_MADRE_ESTUDIO) {
        this.CODIGO_MADRE_ESTUDIO = CODIGO_MADRE_ESTUDIO;
    }

    public String getMADRE_ALFABETA() {
        return MADRE_ALFABETA;
    }

    public void setMADRE_ALFABETA(String MADRE_ALFABETA) {
        this.MADRE_ALFABETA = MADRE_ALFABETA;
    }

    public String getTRABAJA_MADRE() {
        return TRABAJA_MADRE;
    }

    public void setTRABAJA_MADRE(String TRABAJA_MADRE) {
        this.TRABAJA_MADRE = TRABAJA_MADRE;
    }

    public String getFUMA() {
        return FUMA;
    }

    public void setFUMA(String FUMA) {
        this.FUMA = FUMA;
    }

    public String getPERIODICIDAD_FUNA() {
        return PERIODICIDAD_FUNA;
    }

    public void setPERIODICIDAD_FUNA(String PERIODICIDAD_FUNA) {
        this.PERIODICIDAD_FUNA = PERIODICIDAD_FUNA;
    }

    public Integer getCANTIDAD_CIGARRILLOS() {
        return CANTIDAD_CIGARRILLOS;
    }

    public void setCANTIDAD_CIGARRILLOS(Integer CANTIDAD_CIGARRILLOS) {
        this.CANTIDAD_CIGARRILLOS = CANTIDAD_CIGARRILLOS;
    }

    public String getFUMA_DENTRO_CASA() {
        return FUMA_DENTRO_CASA;
    }

    public void setFUMA_DENTRO_CASA(String FUMA_DENTRO_CASA) {
        this.FUMA_DENTRO_CASA = FUMA_DENTRO_CASA;
    }

    public String getTUBERCULOSIS_PULMONAR_ACTUAL() {
        return TUBERCULOSIS_PULMONAR_ACTUAL;
    }

    public void setTUBERCULOSIS_PULMONAR_ACTUAL(String TUBERCULOSIS_PULMONAR_ACTUAL) {
        this.TUBERCULOSIS_PULMONAR_ACTUAL = TUBERCULOSIS_PULMONAR_ACTUAL;
    }

    public Integer getA_FECHA_DIAG_TUBPUL_ACTUAL() {
        return A_FECHA_DIAG_TUBPUL_ACTUAL;
    }

    public void setA_FECHA_DIAG_TUBPUL_ACTUAL(Integer a_FECHA_DIAG_TUBPUL_ACTUAL) {
        A_FECHA_DIAG_TUBPUL_ACTUAL = a_FECHA_DIAG_TUBPUL_ACTUAL;
    }

    public String getM_FECHA_DIAG_TUBPUL_ACTUAL() {
        return M_FECHA_DIAG_TUBPUL_ACTUAL;
    }

    public void setM_FECHA_DIAG_TUBPUL_ACTUAL(String m_FECHA_DIAG_TUBPUL_ACTUAL) {
        M_FECHA_DIAG_TUBPUL_ACTUAL = m_FECHA_DIAG_TUBPUL_ACTUAL;
    }

    public String getTRATAMIENTO_TUBPUL_ACTUAL() {
        return TRATAMIENTO_TUBPUL_ACTUAL;
    }

    public void setTRATAMIENTO_TUBPUL_ACTUAL(String TRATAMIENTO_TUBPUL_ACTUAL) {
        this.TRATAMIENTO_TUBPUL_ACTUAL = TRATAMIENTO_TUBPUL_ACTUAL;
    }

    public String getCOMPLETO_TRATAMIENTO_TUBPUL_ACTUAL() {
        return COMPLETO_TRATAMIENTO_TUBPUL_ACTUAL;
    }

    public void setCOMPLETO_TRATAMIENTO_TUBPUL_ACTUAL(String COMPLETO_TRATAMIENTO_TUBPUL_ACTUAL) {
        this.COMPLETO_TRATAMIENTO_TUBPUL_ACTUAL = COMPLETO_TRATAMIENTO_TUBPUL_ACTUAL;
    }

    public String getTUBERCULOSIS_PULMONAR_PASADO() {
        return TUBERCULOSIS_PULMONAR_PASADO;
    }

    public void setTUBERCULOSIS_PULMONAR_PASADO(String TUBERCULOSIS_PULMONAR_PASADO) {
        this.TUBERCULOSIS_PULMONAR_PASADO = TUBERCULOSIS_PULMONAR_PASADO;
    }

    public String getFECHA_DIAG_TUBPUL_PASADO_DES() {
        return FECHA_DIAG_TUBPUL_PASADO_DES;
    }

    public void setFECHA_DIAG_TUBPUL_PASADO_DES(String FECHA_DIAG_TUBPUL_PASADO_DES) {
        this.FECHA_DIAG_TUBPUL_PASADO_DES = FECHA_DIAG_TUBPUL_PASADO_DES;
    }

    public Integer getA_FECHA_DIAG_TUBPUL_PASADO() {
        return A_FECHA_DIAG_TUBPUL_PASADO;
    }

    public void setA_FECHA_DIAG_TUBPUL_PASADO(Integer a_FECHA_DIAG_TUBPUL_PASADO) {
        A_FECHA_DIAG_TUBPUL_PASADO = a_FECHA_DIAG_TUBPUL_PASADO;
    }

    public String getM_FECHA_DIAG_TUBPUL_PASADO() {
        return M_FECHA_DIAG_TUBPUL_PASADO;
    }

    public void setM_FECHA_DIAG_TUBPUL_PASADO(String m_FECHA_DIAG_TUBPUL_PASADO) {
        M_FECHA_DIAG_TUBPUL_PASADO = m_FECHA_DIAG_TUBPUL_PASADO;
    }

    public String getTRATAMIENTO_TUBPUL_PASADO() {
        return TRATAMIENTO_TUBPUL_PASADO;
    }

    public void setTRATAMIENTO_TUBPUL_PASADO(String TRATAMIENTO_TUBPUL_PASADO) {
        this.TRATAMIENTO_TUBPUL_PASADO = TRATAMIENTO_TUBPUL_PASADO;
    }

    public String getCOMPLETO_TRATAMIENTO_TUBPUL_PAS() {
        return COMPLETO_TRATAMIENTO_TUBPUL_PAS;
    }

    public void setCOMPLETO_TRATAMIENTO_TUBPUL_PAS(String COMPLETO_TRATAMIENTO_TUBPUL_PAS) {
        this.COMPLETO_TRATAMIENTO_TUBPUL_PAS = COMPLETO_TRATAMIENTO_TUBPUL_PAS;
    }

    public String getALERGIA_RESPIRATORIA() {
        return ALERGIA_RESPIRATORIA;
    }

    public void setALERGIA_RESPIRATORIA(String ALERGIA_RESPIRATORIA) {
        this.ALERGIA_RESPIRATORIA = ALERGIA_RESPIRATORIA;
    }

    public String getCARDIOPATIA() {
        return CARDIOPATIA;
    }

    public void setCARDIOPATIA(String CARDIOPATIA) {
        this.CARDIOPATIA = CARDIOPATIA;
    }

    public String getENFERM_PULMONAR_OBST_CRONICA() {
        return ENFERM_PULMONAR_OBST_CRONICA;
    }

    public void setENFERM_PULMONAR_OBST_CRONICA(String ENFERM_PULMONAR_OBST_CRONICA) {
        this.ENFERM_PULMONAR_OBST_CRONICA = ENFERM_PULMONAR_OBST_CRONICA;
    }

    public String getDIABETES() {
        return DIABETES;
    }

    public void setDIABETES(String DIABETES) {
        this.DIABETES = DIABETES;
    }

    public String getPRESION_ALTA() {
        return PRESION_ALTA;
    }

    public void setPRESION_ALTA(String PRESION_ALTA) {
        this.PRESION_ALTA = PRESION_ALTA;
    }

    public String getTOS_SIN_FIEBRE_RESFRIADO() {
        return TOS_SIN_FIEBRE_RESFRIADO;
    }

    public void setTOS_SIN_FIEBRE_RESFRIADO(String TOS_SIN_FIEBRE_RESFRIADO) {
        this.TOS_SIN_FIEBRE_RESFRIADO = TOS_SIN_FIEBRE_RESFRIADO;
    }

    public Integer getCANT_ENFERM_CUADROS_RESP() {
        return CANT_ENFERM_CUADROS_RESP;
    }

    public void setCANT_ENFERM_CUADROS_RESP(Integer CANT_ENFERM_CUADROS_RESP) {
        this.CANT_ENFERM_CUADROS_RESP = CANT_ENFERM_CUADROS_RESP;
    }

    public String getOTRAS_ENFERMEDADES() {
        return OTRAS_ENFERMEDADES;
    }

    public void setOTRAS_ENFERMEDADES(String OTRAS_ENFERMEDADES) {
        this.OTRAS_ENFERMEDADES = OTRAS_ENFERMEDADES;
    }

    public String getDESC_OTRAS_ENFERMEDADES() {
        return DESC_OTRAS_ENFERMEDADES;
    }

    public void setDESC_OTRAS_ENFERMEDADES(String DESC_OTRAS_ENFERMEDADES) {
        this.DESC_OTRAS_ENFERMEDADES = DESC_OTRAS_ENFERMEDADES;
    }

    public String getVACUNA_INFLUENZA() {
        return VACUNA_INFLUENZA;
    }

    public void setVACUNA_INFLUENZA(String VACUNA_INFLUENZA) {
        this.VACUNA_INFLUENZA = VACUNA_INFLUENZA;
    }

    public Integer getANIO_VACUNA_INFLUENZA() {
        return ANIO_VACUNA_INFLUENZA;
    }

    public void setANIO_VACUNA_INFLUENZA(Integer ANIO_VACUNA_INFLUENZA) {
        this.ANIO_VACUNA_INFLUENZA = ANIO_VACUNA_INFLUENZA;
    }

    public String getRash6m() {
        return rash6m;
    }

    public void setRash6m(String rash6m) {
        this.rash6m = rash6m;
    }

    public String getOjoRojo6m() {
        return ojoRojo6m;
    }

    public void setOjoRojo6m(String ojoRojo6m) {
        this.ojoRojo6m = ojoRojo6m;
    }

    public Integer getVacunaInfluenzaMes() {
        return vacunaInfluenzaMes;
    }

    public void setVacunaInfluenzaMes(Integer vacunaInfluenzaMes) {
        this.vacunaInfluenzaMes = vacunaInfluenzaMes;
    }

    public String getVacunaInfluenzaCSSF() {
        return vacunaInfluenzaCSSF;
    }

    public void setVacunaInfluenzaCSSF(String vacunaInfluenzaCSSF) {
        this.vacunaInfluenzaCSSF = vacunaInfluenzaCSSF;
    }

    public String getVacunaInfluenzaOtro() {
        return vacunaInfluenzaOtro;
    }

    public void setVacunaInfluenzaOtro(String vacunaInfluenzaOtro) {
        this.vacunaInfluenzaOtro = vacunaInfluenzaOtro;
    }

    public String getNombreCDI() {
        return nombreCDI;
    }

    public void setNombreCDI(String nombreCDI) {
        this.nombreCDI = nombreCDI;
    }

    public String getDireccionCDI() {
        return direccionCDI;
    }

    public void setDireccionCDI(String direccionCDI) {
        this.direccionCDI = direccionCDI;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	public String getInfluenza() {
		return influenza;
	}

	public void setInfluenza(String influenza) {
		this.influenza = influenza;
	}

	public String getDengue() {
		return dengue;
	}

	public void setDengue(String dengue) {
		this.dengue = dengue;
	}

	public String getMostrarAlfabeto() {
		return mostrarAlfabeto;
	}

	public void setMostrarAlfabeto(String mostrarAlfabeto) {
		this.mostrarAlfabeto = mostrarAlfabeto;
	}

	public String getMostrarPadreAlfabeto() {
		return mostrarPadreAlfabeto;
	}

	public void setMostrarPadreAlfabeto(String mostrarPadreAlfabeto) {
		this.mostrarPadreAlfabeto = mostrarPadreAlfabeto;
	}

	public String getMostrarMadreAlfabeta() {
		return mostrarMadreAlfabeta;
	}

	public void setMostrarMadreAlfabeta(String mostrarMadreAlfabeta) {
		this.mostrarMadreAlfabeta = mostrarMadreAlfabeta;
	}

	public String getMostrarNumParto() {
		return mostrarNumParto;
	}

	public void setMostrarNumParto(String mostrarNumParto) {
		this.mostrarNumParto = mostrarNumParto;
	}

	public String getAntecedenteTutorCP() {
		return antecedenteTutorCP;
	}

	public void setAntecedenteTutorCP(String antecedenteTutorCP) {
		this.antecedenteTutorCP = antecedenteTutorCP;
	}

	public String getOtroLugarCuidan() {
		return otroLugarCuidan;
	}

	public void setOtroLugarCuidan(String otroLugarCuidan) {
		this.otroLugarCuidan = otroLugarCuidan;
	}

	public String getEnfermedadCronica() {
		return enfermedadCronica;
	}

	public void setEnfermedadCronica(String enfermedadCronica) {
		this.enfermedadCronica = enfermedadCronica;
	}

	public String getTenidoDengue() {
		return tenidoDengue;
	}

	public void setTenidoDengue(String tenidoDengue) {
		this.tenidoDengue = tenidoDengue;
	}

	public String getUnidadSaludDengue() {
		return unidadSaludDengue;
	}

	public void setUnidadSaludDengue(String unidadSaludDengue) {
		this.unidadSaludDengue = unidadSaludDengue;
	}

	public String getCentroSaludDengue() {
		return centroSaludDengue;
	}

	public void setCentroSaludDengue(String centroSaludDengue) {
		this.centroSaludDengue = centroSaludDengue;
	}

	public String getOtroCentroSaludDengue() {
		return otroCentroSaludDengue;
	}

	public void setOtroCentroSaludDengue(String otroCentroSaludDengue) {
		this.otroCentroSaludDengue = otroCentroSaludDengue;
	}

	public String getPuestoSaludDengue() {
		return puestoSaludDengue;
	}

	public void setPuestoSaludDengue(String puestoSaludDengue) {
		this.puestoSaludDengue = puestoSaludDengue;
	}

	public String getOtroPuestoSaludDengue() {
		return otroPuestoSaludDengue;
	}

	public void setOtroPuestoSaludDengue(String otroPuestoSaludDengue) {
		this.otroPuestoSaludDengue = otroPuestoSaludDengue;
	}

	public String getHospitalDengue() {
		return hospitalDengue;
	}

	public void setHospitalDengue(String hospitalDengue) {
		this.hospitalDengue = hospitalDengue;
	}

	public String getOtroHospitalDengue() {
		return otroHospitalDengue;
	}

	public void setOtroHospitalDengue(String otroHospitalDengue) {
		this.otroHospitalDengue = otroHospitalDengue;
	}

	public String getHospitalizadoDengue() {
		return hospitalizadoDengue;
	}

	public void setHospitalizadoDengue(String hospitalizadoDengue) {
		this.hospitalizadoDengue = hospitalizadoDengue;
	}

	public String getAmbulatorioDengue() {
		return ambulatorioDengue;
	}

	public void setAmbulatorioDengue(String ambulatorioDengue) {
		this.ambulatorioDengue = ambulatorioDengue;
	}

	public String getDiagMedicoDengue() {
		return diagMedicoDengue;
	}

	public void setDiagMedicoDengue(String diagMedicoDengue) {
		this.diagMedicoDengue = diagMedicoDengue;
	}

	public String getRashUA() {
		return rashUA;
	}

	public void setRashUA(String rashUA) {
		this.rashUA = rashUA;
	}

	public String getConsultaRashUA() {
		return consultaRashUA;
	}

	public void setConsultaRashUA(String consultaRashUA) {
		this.consultaRashUA = consultaRashUA;
	}
}
