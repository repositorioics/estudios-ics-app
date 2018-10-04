package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;


/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario Coordenadas
 * 
 * @author Miguel Salinas
 **/

public class CoordenadasXml {

	@Element(required=false)
	private Integer barrio;
    @Element(required=false)
    private String otroBarrio;
    @Element(required=false)
	private Integer manzana;
	@Element(required=false)
	private String direccion;
	@Element(required=false)
	private String coordenadas;
    @Element(required=false)
    private String todosSN;
    @Element(required=false)
    private String noGeorefRazon;
    @Element(required=false)
    private String noGeorefOtraRazon;

    @Element(required=false)
    private String informacion;
    @Element(required=false)
    private String motivo;
    @Element(required=false)
    private String barrioa;
    @Element(required=false)
    private Integer manzanaa;
    @Element(required=false)
    private String direcciona;
    @Element(required=false)
    private String confirmar;

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

	public CoordenadasXml(){
		
	}

    public Integer getBarrio() {
        return barrio;
    }

    public void setBarrio(Integer barrio) {
        this.barrio = barrio;
    }

    public String getOtroBarrio() {
        return otroBarrio;
    }

    public void setOtroBarrio(String otroBarrio) {
        this.otroBarrio = otroBarrio;
    }

    public Integer getManzana() {
        return manzana;
    }

    public void setManzana(Integer manzana) {
        this.manzana = manzana;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getTodosSN() {
        return todosSN;
    }

    public void setTodosSN(String todosSN) {
        this.todosSN = todosSN;
    }

    public String getNoGeorefRazon() {
        return noGeorefRazon;
    }

    public void setNoGeorefRazon(String noGeorefRazon) {
        this.noGeorefRazon = noGeorefRazon;
    }

    public String getNoGeorefOtraRazon() {
        return noGeorefOtraRazon;
    }

    public void setNoGeorefOtraRazon(String noGeorefOtraRazon) {
        this.noGeorefOtraRazon = noGeorefOtraRazon;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getBarrioa() {
        return barrioa;
    }

    public void setBarrioa(String barrioa) {
        this.barrioa = barrioa;
    }

    public Integer getManzanaa() {
        return manzanaa;
    }

    public void setManzanaa(Integer manzanaa) {
        this.manzanaa = manzanaa;
    }

    public String getDirecciona() {
        return direcciona;
    }

    public void setDirecciona(String direcciona) {
        this.direcciona = direcciona;
    }

    public String getConfirmar() {
        return confirmar;
    }

    public void setConfirmar(String confirmar) {
        this.confirmar = confirmar;
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
}
