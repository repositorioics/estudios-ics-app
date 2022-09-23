package ni.org.ics.estudios.appmovil.entomologia.parsers;

import ni.org.ics.estudios.appmovil.muestreoanual.parsers.Meta;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario Coordenadas
 * 
 * @author Miguel Salinas
 **/

public class CuestionarioPuntoClaveXml {

	@Element(required=false)
	private Date fecha;

    @Element(required=false)
    private Integer barrio;
    @Element(required=false)
    private String nombrePuntoClave;
    @Element(required=false)
    private String direccion;
    @Element(required=false)
    private String tipo;
    @Element(required=false)
    private String clasProductividad;
    @Element(required=false)
    private String otroProductividad;
    @Element(required=false)
    private String clasAglomeracion;
    @Element(required=false)
    private String otroAglomeracion;
    @Element(required=false)
    private Integer nroPersonas;
    @Element(required=false)
    private Integer nroDiasPorSemana;
    @Element(required=false)
    private String group1;
    @Element(required=false)
    private String horaInicio;
    @Element(required=false)
    private String horaFin;
    @Element(required=false)
    private String puntoGPS;

    @Element(required=false)
    private String group2;
    @Element(required=false)
    private String tipoIngresoCodSitio;
    @Element(required=false)
    private String codSitioManual;
    @Element(required=false)
    private String codSitioQR;

    @Element(required=false)
    private String codSitioManual1;
    @Element(required=false)
    private String codSitioQR1;

    @Element(required=false)
    private String ambPERI;
    @Element(required=false)
    private String horaCapturaPERI;
    @Element(required=false)
    private Double humedadRelativaPERI;
    @Element(required=false)
    private Double TemperaturaPERI;

    @Element(required=false)
    private String group3;
    @Element(required=false)
    private String tipoIngresoCodPERI;
    @Element(required=false)
    private String codPERIManual;
    @Element(required=false)
    private String codPERIQR;
    @Element(required=false)
    private String ambINTRA;
    @Element(required=false)
    private String horaCapturaINTRA;
    @Element(required=false)
    private Double humedadRelativaINTRA;
    @Element(required=false)
    private Double TemperaturaINTRA;
    @Element(required=false)
    private String group4;
    @Element(required=false)
    private String tipoIngresoCodINTRA;
    @Element(required=false)
    private String codINTRAManual;
    @Element(required=false)
    private String codINTRAQR;
    @Element(required=false)
    private String nombrePersonaInfo;

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

	@Attribute
	private String id;


	@Element(required=false)
	private Meta meta;

	public CuestionarioPuntoClaveXml(){
		
	}

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getBarrio() {
        return barrio;
    }

    public void setBarrio(Integer barrio) {
        this.barrio = barrio;
    }

    public String getNombrePuntoClave() {
        return nombrePuntoClave;
    }

    public void setNombrePuntoClave(String nombrePuntoClave) {
        this.nombrePuntoClave = nombrePuntoClave;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClasProductividad() {
        return clasProductividad;
    }

    public void setClasProductividad(String clasProductividad) {
        this.clasProductividad = clasProductividad;
    }

    public String getOtroProductividad() {
        return otroProductividad;
    }

    public void setOtroProductividad(String otroProductividad) {
        this.otroProductividad = otroProductividad;
    }

    public String getClasAglomeracion() {
        return clasAglomeracion;
    }

    public void setClasAglomeracion(String clasAglomeracion) {
        this.clasAglomeracion = clasAglomeracion;
    }

    public String getOtroAglomeracion() {
        return otroAglomeracion;
    }

    public void setOtroAglomeracion(String otroAglomeracion) {
        this.otroAglomeracion = otroAglomeracion;
    }

    public Integer getNroPersonas() {
        return nroPersonas;
    }

    public void setNroPersonas(Integer nroPersonas) {
        this.nroPersonas = nroPersonas;
    }

    public Integer getNroDiasPorSemana() {
        return nroDiasPorSemana;
    }

    public void setNroDiasPorSemana(Integer nroDiasPorSemana) {
        this.nroDiasPorSemana = nroDiasPorSemana;
    }

    public String getGroup1() {
        return group1;
    }

    public void setGroup1(String group1) {
        this.group1 = group1;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getPuntoGPS() {
        return puntoGPS;
    }

    public void setPuntoGPS(String puntoGPS) {
        this.puntoGPS = puntoGPS;
    }

    public String getGroup2() {
        return group2;
    }

    public void setGroup2(String group2) {
        this.group2 = group2;
    }

    public String getTipoIngresoCodSitio() {
        return tipoIngresoCodSitio;
    }

    public void setTipoIngresoCodSitio(String tipoIngresoCodSitio) {
        this.tipoIngresoCodSitio = tipoIngresoCodSitio;
    }

    public String getCodSitioManual() {
        return codSitioManual;
    }

    public void setCodSitioManual(String codSitioManual) {
        this.codSitioManual = codSitioManual;
    }

    public String getCodSitioQR() {
        return codSitioQR;
    }

    public void setCodSitioQR(String codSitioQR) {
        this.codSitioQR = codSitioQR;
    }

    public String getAmbPERI() {
        return ambPERI;
    }

    public void setAmbPERI(String ambPERI) {
        this.ambPERI = ambPERI;
    }

    public String getHoraCapturaPERI() {
        return horaCapturaPERI;
    }

    public void setHoraCapturaPERI(String horaCapturaPERI) {
        this.horaCapturaPERI = horaCapturaPERI;
    }

    public Double getHumedadRelativaPERI() {
        return humedadRelativaPERI;
    }

    public void setHumedadRelativaPERI(Double humedadRelativaPERI) {
        this.humedadRelativaPERI = humedadRelativaPERI;
    }

    public Double getTemperaturaPERI() {
        return TemperaturaPERI;
    }

    public void setTemperaturaPERI(Double temperaturaPERI) {
        TemperaturaPERI = temperaturaPERI;
    }

    public String getGroup3() {
        return group3;
    }

    public void setGroup3(String group3) {
        this.group3 = group3;
    }

    public String getTipoIngresoCodPERI() {
        return tipoIngresoCodPERI;
    }

    public void setTipoIngresoCodPERI(String tipoIngresoCodPERI) {
        this.tipoIngresoCodPERI = tipoIngresoCodPERI;
    }

    public String getCodPERIManual() {
        return codPERIManual;
    }

    public void setCodPERIManual(String codPERIManual) {
        this.codPERIManual = codPERIManual;
    }

    public String getCodPERIQR() {
        return codPERIQR;
    }

    public void setCodPERIQR(String codPERIQR) {
        this.codPERIQR = codPERIQR;
    }

    public String getAmbINTRA() {
        return ambINTRA;
    }

    public void setAmbINTRA(String ambINTRA) {
        this.ambINTRA = ambINTRA;
    }

    public String getHoraCapturaINTRA() {
        return horaCapturaINTRA;
    }

    public void setHoraCapturaINTRA(String horaCapturaINTRA) {
        this.horaCapturaINTRA = horaCapturaINTRA;
    }

    public Double getHumedadRelativaINTRA() {
        return humedadRelativaINTRA;
    }

    public void setHumedadRelativaINTRA(Double humedadRelativaINTRA) {
        this.humedadRelativaINTRA = humedadRelativaINTRA;
    }

    public Double getTemperaturaINTRA() {
        return TemperaturaINTRA;
    }

    public void setTemperaturaINTRA(Double temperaturaINTRA) {
        TemperaturaINTRA = temperaturaINTRA;
    }

    public String getGroup4() {
        return group4;
    }

    public void setGroup4(String group4) {
        this.group4 = group4;
    }

    public String getTipoIngresoCodINTRA() {
        return tipoIngresoCodINTRA;
    }

    public void setTipoIngresoCodINTRA(String tipoIngresoCodINTRA) {
        this.tipoIngresoCodINTRA = tipoIngresoCodINTRA;
    }

    public String getCodINTRAManual() {
        return codINTRAManual;
    }

    public void setCodINTRAManual(String codINTRAManual) {
        this.codINTRAManual = codINTRAManual;
    }

    public String getCodINTRAQR() {
        return codINTRAQR;
    }

    public void setCodINTRAQR(String codINTRAQR) {
        this.codINTRAQR = codINTRAQR;
    }

    public String getNombrePersonaInfo() {
        return nombrePersonaInfo;
    }

    public void setNombrePersonaInfo(String nombrePersonaInfo) {
        this.nombrePersonaInfo = nombrePersonaInfo;
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

    public String getCodSitioManual1() {
        return codSitioManual1;
    }

    public void setCodSitioManual1(String codSitioManual1) {
        this.codSitioManual1 = codSitioManual1;
    }

    public String getCodSitioQR1() {
        return codSitioQR1;
    }

    public void setCodSitioQR1(String codSitioQR1) {
        this.codSitioQR1 = codSitioQR1;
    }
}
