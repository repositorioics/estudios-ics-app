package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.util.Date;

/**
 * Simple objeto de dominio que representa una encuesta de casa que se le
 * realiza a cada participante de los
 * 
 * estudios
 * 
 * @author Brenda Lopez
 **/

public class EncuestaCasa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3374445419709747964L;
	/**
	 * 
	 */
    private String codigo;
    private Integer codCasa;
    private Date fechaEncCasa;
    private String codCasaChf;
    //private EncuestaCasaId encCasaId;
	private Integer cvivencasa1;
	private Integer cvivencasa2;
	private Integer cvivencasa3;
	private Integer cvivencasa4;
	private Integer cvivencasa5;
	private Integer cvivencasa6;
	private Integer cvivencasa7;
	private Integer ccuartos;
	private Integer grifo;
	private Integer grifoComSN;
	private Integer horasagua;
	private String mcasa;
	private String ocasa;
	private String piso;
	private String opiso;
	private Integer techo;
	private String otecho;
	private Integer cpropia;
	private Integer cabanicos;
	private Integer ctelevisores;
	private Integer crefrigeradores;
	private Integer moto;
	private Integer carro;
	private Integer cocinalena;
	private Integer animalesSN;
	private Integer pollos;
	private Integer polloscasa;
	private Integer patos;
	private Integer patoscasa;
	private Integer perros;
	private Integer perroscasa;
	private Integer gatos;
	private Integer gatoscasa;
	private Integer cerdos;
	private Integer cerdoscasa;
	private MovilInfo movilInfo;
	private Integer otrorecurso1;
	private Integer otrorecurso2;
    //CHF + NUEVAS PREGUNTAS MA2018
    private String viveEmbEnCasa;
    private Integer cantidadCuartos;
    private String almacenaAgua;
    private String almacenaEnBarriles;
    private Integer numeroBarriles;
    private String barrilesTapados;
    private String barrilesConAbate;
    private String almacenaEnTanques;
    private Integer numeroTanques;
    private String tanquesTapados;
    private String tanquesConAbate;
    private String almacenaEnPilas;
    private Integer numeroPilas;
    private String pilasTapadas;
    private String pilasConAbate;
    private String almacenaEnOtrosrecip;
    private String descOtrosRecipientes;
    private Integer numeroOtrosRecipientes;
    private String otrosRecipTapados;
    private String otrosrecipConAbate;
    private String ubicacionLavandero;
    private String tieneAbanico;
    private String tieneTelevisor;
    private String tieneRefrigeradorFreezer;
    private String tieneAireAcondicionado;
    private String funcionamientoAire;
    private String opcFabCarro;
    private Integer yearNow;
    private Integer yearFabCarro;
    private String tieneMicrobus;
    private String tieneCamioneta;
    private String tieneCamion;
    private String tieneOtroMedioTrans;
    private String descOtroMedioTrans;
    private String cocinaConLenia;
    private String ubicacionCocinaLenia;
    private String periodicidadCocinaLenia;
    private Integer numDiarioCocinaLenia;
    private Integer numSemanalCocinaLenia;
    private Integer numQuincenalCocinaLenia;
    private Integer numMensualCocinaLenia;
    private String tieneGallinas;
    private String tienePatos;
    private String tienePerros;
    private String tieneGatos;
    private String tieneCerdos;
    private String persFumaDentroCasa;
    private String madreFuma;
    private Integer cantCigarrillosMadre;
    private String padreFuma;
    private Integer cantCigarrillosPadre;
    private String otrosFuman;
    private Integer cantOtrosFuman;
    private Integer cantCigarrillosOtros;
    private String servRecolBasura;
    private Integer frecServRecolBasura;
    private String llantasOtrosContConAgua;
    private String opcFabMicrobus;
    private Integer yearFabMicrobus;
    private String opcFabCamioneta;
    private Integer yearFabCamioneta;
    private String opcFabCamion;
    private Integer yearFabCamion;
    private String opcFabOtroMedioTrans;
    private Integer yearFabOtroMedioTrans;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCodCasa() {
        return codCasa;
    }

    public void setCodCasa(Integer codCasa) {
        this.codCasa = codCasa;
    }

    public Date getFechaEncCasa() {
        return fechaEncCasa;
    }

    public void setFechaEncCasa(Date fechaEncCasa) {
        this.fechaEncCasa = fechaEncCasa;
    }

    public String getCodCasaChf() {
        return codCasaChf;
    }

    public void setCodCasaChf(String codCasaChf) {
        this.codCasaChf = codCasaChf;
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

	/*public EncuestaCasaId getEncCasaId() {
		return encCasaId;
	}

	public void setEncCasaId(EncuestaCasaId encCasaId) {
		this.encCasaId = encCasaId;
	}*/

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

	public Integer gethorasagua() {
		return horasagua;
	}

	public void sethorasagua(Integer horasagua) {
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

	public MovilInfo getMovilInfo() {
		return movilInfo;
	}

	public void setMovilInfo(MovilInfo movilInfo) {
		this.movilInfo = movilInfo;
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

    public Integer getCantidadCuartos() {
        return cantidadCuartos;
    }

    public void setCantidadCuartos(Integer cantidadCuartos) {
        this.cantidadCuartos = cantidadCuartos;
    }

    public String getAlmacenaAgua() {
        return almacenaAgua;
    }

    public void setAlmacenaAgua(String almacenaAgua) {
        this.almacenaAgua = almacenaAgua;
    }

    public String getAlmacenaEnBarriles() {
        return almacenaEnBarriles;
    }

    public void setAlmacenaEnBarriles(String almacenaEnBarriles) {
        this.almacenaEnBarriles = almacenaEnBarriles;
    }

    public Integer getNumeroBarriles() {
        return numeroBarriles;
    }

    public void setNumeroBarriles(Integer numeroBarriles) {
        this.numeroBarriles = numeroBarriles;
    }

    public String getBarrilesTapados() {
        return barrilesTapados;
    }

    public void setBarrilesTapados(String barrilesTapados) {
        this.barrilesTapados = barrilesTapados;
    }

    public String getBarrilesConAbate() {
        return barrilesConAbate;
    }

    public void setBarrilesConAbate(String barrilesConAbate) {
        this.barrilesConAbate = barrilesConAbate;
    }

    public String getAlmacenaEnTanques() {
        return almacenaEnTanques;
    }

    public void setAlmacenaEnTanques(String almacenaEnTanques) {
        this.almacenaEnTanques = almacenaEnTanques;
    }

    public Integer getNumeroTanques() {
        return numeroTanques;
    }

    public void setNumeroTanques(Integer numeroTanques) {
        this.numeroTanques = numeroTanques;
    }

    public String getTanquesTapados() {
        return tanquesTapados;
    }

    public void setTanquesTapados(String tanquesTapados) {
        this.tanquesTapados = tanquesTapados;
    }

    public String getTanquesConAbate() {
        return tanquesConAbate;
    }

    public void setTanquesConAbate(String tanquesConAbate) {
        this.tanquesConAbate = tanquesConAbate;
    }

    public String getAlmacenaEnPilas() {
        return almacenaEnPilas;
    }

    public void setAlmacenaEnPilas(String almacenaEnPilas) {
        this.almacenaEnPilas = almacenaEnPilas;
    }

    public Integer getNumeroPilas() {
        return numeroPilas;
    }

    public void setNumeroPilas(Integer numeroPilas) {
        this.numeroPilas = numeroPilas;
    }

    public String getPilasTapadas() {
        return pilasTapadas;
    }

    public void setPilasTapadas(String pilasTapadas) {
        this.pilasTapadas = pilasTapadas;
    }

    public String getPilasConAbate() {
        return pilasConAbate;
    }

    public void setPilasConAbate(String pilasConAbate) {
        this.pilasConAbate = pilasConAbate;
    }

    public String getAlmacenaEnOtrosrecip() {
        return almacenaEnOtrosrecip;
    }

    public void setAlmacenaEnOtrosrecip(String almacenaEnOtrosrecip) {
        this.almacenaEnOtrosrecip = almacenaEnOtrosrecip;
    }

    public String getDescOtrosRecipientes() {
        return descOtrosRecipientes;
    }

    public void setDescOtrosRecipientes(String descOtrosRecipientes) {
        this.descOtrosRecipientes = descOtrosRecipientes;
    }

    public Integer getNumeroOtrosRecipientes() {
        return numeroOtrosRecipientes;
    }

    public void setNumeroOtrosRecipientes(Integer numeroOtrosRecipientes) {
        this.numeroOtrosRecipientes = numeroOtrosRecipientes;
    }

    public String getOtrosRecipTapados() {
        return otrosRecipTapados;
    }

    public void setOtrosRecipTapados(String otrosRecipTapados) {
        this.otrosRecipTapados = otrosRecipTapados;
    }

    public String getOtrosrecipConAbate() {
        return otrosrecipConAbate;
    }

    public void setOtrosrecipConAbate(String otrosrecipConAbate) {
        this.otrosrecipConAbate = otrosrecipConAbate;
    }

    public String getUbicacionLavandero() {
        return ubicacionLavandero;
    }

    public void setUbicacionLavandero(String ubicacionLavandero) {
        this.ubicacionLavandero = ubicacionLavandero;
    }

    public String getTieneAbanico() {
        return tieneAbanico;
    }

    public void setTieneAbanico(String tieneAbanico) {
        this.tieneAbanico = tieneAbanico;
    }

    public String getTieneTelevisor() {
        return tieneTelevisor;
    }

    public void setTieneTelevisor(String tieneTelevisor) {
        this.tieneTelevisor = tieneTelevisor;
    }

    public String getTieneRefrigeradorFreezer() {
        return tieneRefrigeradorFreezer;
    }

    public void setTieneRefrigeradorFreezer(String tieneRefrigeradorFreezer) {
        this.tieneRefrigeradorFreezer = tieneRefrigeradorFreezer;
    }

    public String getTieneAireAcondicionado() {
        return tieneAireAcondicionado;
    }

    public void setTieneAireAcondicionado(String tieneAireAcondicionado) {
        this.tieneAireAcondicionado = tieneAireAcondicionado;
    }

    public String getFuncionamientoAire() {
        return funcionamientoAire;
    }

    public void setFuncionamientoAire(String funcionamientoAire) {
        this.funcionamientoAire = funcionamientoAire;
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

    public String getTieneMicrobus() {
        return tieneMicrobus;
    }

    public void setTieneMicrobus(String tieneMicrobus) {
        this.tieneMicrobus = tieneMicrobus;
    }

    public String getTieneCamioneta() {
        return tieneCamioneta;
    }

    public void setTieneCamioneta(String tieneCamioneta) {
        this.tieneCamioneta = tieneCamioneta;
    }

    public String getTieneCamion() {
        return tieneCamion;
    }

    public void setTieneCamion(String tieneCamion) {
        this.tieneCamion = tieneCamion;
    }

    public String getTieneOtroMedioTrans() {
        return tieneOtroMedioTrans;
    }

    public void setTieneOtroMedioTrans(String tieneOtroMedioTrans) {
        this.tieneOtroMedioTrans = tieneOtroMedioTrans;
    }

    public String getDescOtroMedioTrans() {
        return descOtroMedioTrans;
    }

    public void setDescOtroMedioTrans(String descOtroMedioTrans) {
        this.descOtroMedioTrans = descOtroMedioTrans;
    }

    public String getCocinaConLenia() {
        return cocinaConLenia;
    }

    public void setCocinaConLenia(String cocinaConLenia) {
        this.cocinaConLenia = cocinaConLenia;
    }

    public String getUbicacionCocinaLenia() {
        return ubicacionCocinaLenia;
    }

    public void setUbicacionCocinaLenia(String ubicacionCocinaLenia) {
        this.ubicacionCocinaLenia = ubicacionCocinaLenia;
    }

    public String getPeriodicidadCocinaLenia() {
        return periodicidadCocinaLenia;
    }

    public void setPeriodicidadCocinaLenia(String periodicidadCocinaLenia) {
        this.periodicidadCocinaLenia = periodicidadCocinaLenia;
    }

    public Integer getNumDiarioCocinaLenia() {
        return numDiarioCocinaLenia;
    }

    public void setNumDiarioCocinaLenia(Integer numDiarioCocinaLenia) {
        this.numDiarioCocinaLenia = numDiarioCocinaLenia;
    }

    public Integer getNumSemanalCocinaLenia() {
        return numSemanalCocinaLenia;
    }

    public void setNumSemanalCocinaLenia(Integer numSemanalCocinaLenia) {
        this.numSemanalCocinaLenia = numSemanalCocinaLenia;
    }

    public Integer getNumQuincenalCocinaLenia() {
        return numQuincenalCocinaLenia;
    }

    public void setNumQuincenalCocinaLenia(Integer numQuincenalCocinaLenia) {
        this.numQuincenalCocinaLenia = numQuincenalCocinaLenia;
    }

    public Integer getNumMensualCocinaLenia() {
        return numMensualCocinaLenia;
    }

    public void setNumMensualCocinaLenia(Integer numMensualCocinaLenia) {
        this.numMensualCocinaLenia = numMensualCocinaLenia;
    }

    public String getTieneGallinas() {
        return tieneGallinas;
    }

    public void setTieneGallinas(String tieneGallinas) {
        this.tieneGallinas = tieneGallinas;
    }

    public String getTienePatos() {
        return tienePatos;
    }

    public void setTienePatos(String tienePatos) {
        this.tienePatos = tienePatos;
    }

    public String getTienePerros() {
        return tienePerros;
    }

    public void setTienePerros(String tienePerros) {
        this.tienePerros = tienePerros;
    }

    public String getTieneGatos() {
        return tieneGatos;
    }

    public void setTieneGatos(String tieneGatos) {
        this.tieneGatos = tieneGatos;
    }

    public String getTieneCerdos() {
        return tieneCerdos;
    }

    public void setTieneCerdos(String tieneCerdos) {
        this.tieneCerdos = tieneCerdos;
    }

    public String getPersFumaDentroCasa() {
        return persFumaDentroCasa;
    }

    public void setPersFumaDentroCasa(String persFumaDentroCasa) {
        this.persFumaDentroCasa = persFumaDentroCasa;
    }

    public String getMadreFuma() {
        return madreFuma;
    }

    public void setMadreFuma(String madreFuma) {
        this.madreFuma = madreFuma;
    }

    public Integer getCantCigarrillosMadre() {
        return cantCigarrillosMadre;
    }

    public void setCantCigarrillosMadre(Integer cantCigarrillosMadre) {
        this.cantCigarrillosMadre = cantCigarrillosMadre;
    }

    public String getPadreFuma() {
        return padreFuma;
    }

    public void setPadreFuma(String padreFuma) {
        this.padreFuma = padreFuma;
    }

    public Integer getCantCigarrillosPadre() {
        return cantCigarrillosPadre;
    }

    public void setCantCigarrillosPadre(Integer cantCigarrillosPadre) {
        this.cantCigarrillosPadre = cantCigarrillosPadre;
    }

    public String getOtrosFuman() {
        return otrosFuman;
    }

    public void setOtrosFuman(String otrosFuman) {
        this.otrosFuman = otrosFuman;
    }

    public Integer getCantOtrosFuman() {
        return cantOtrosFuman;
    }

    public void setCantOtrosFuman(Integer cantOtrosFuman) {
        this.cantOtrosFuman = cantOtrosFuman;
    }

    public Integer getCantCigarrillosOtros() {
        return cantCigarrillosOtros;
    }

    public void setCantCigarrillosOtros(Integer cantCigarrillosOtros) {
        this.cantCigarrillosOtros = cantCigarrillosOtros;
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

    @Override
	public String toString() {
		return this.getCodigo();
	}

}
