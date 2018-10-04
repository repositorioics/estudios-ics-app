package ni.org.ics.estudios.appmovil.domain;

import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;

import java.io.Serializable;


/**
 *  Objeto que representa CambioDomicilio
 * @author Miguel Salinas
 **/

public class DatosCoordenadas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String codigo;
    private Integer codigoCasa;
    private String estudios;
    private Participante participante;
    private String motivo;
	private Barrio barrio;
    private String otroBarrio;
	private String direccion;
    private Integer manzana;
    private String conpunto;
    private String puntoGps;
	private Double latitud;
	private Double longitud;
    private String razonNoGeoref;
    private String otraRazonNoGeoref;

    private MovilInfo movilInfo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoCasa() {
        return codigoCasa;
    }

    public void setCodigoCasa(Integer codigoCasa) {
        this.codigoCasa = codigoCasa;
    }

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    public String getOtroBarrio() {
        return otroBarrio;
    }

    public void setOtroBarrio(String otroBarrio) {
        this.otroBarrio = otroBarrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getManzana() {
        return manzana;
    }

    public void setManzana(Integer manzana) {
        this.manzana = manzana;
    }

    public String getConpunto() {
        return conpunto;
    }

    public void setConpunto(String conpunto) {
        this.conpunto = conpunto;
    }

    public String getPuntoGps() {
        return puntoGps;
    }

    public void setPuntoGps(String puntoGps) {
        this.puntoGps = puntoGps;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getRazonNoGeoref() {
        return razonNoGeoref;
    }

    public void setRazonNoGeoref(String razonNoGeoref) {
        this.razonNoGeoref = razonNoGeoref;
    }

    public String getOtraRazonNoGeoref() {
        return otraRazonNoGeoref;
    }

    public void setOtraRazonNoGeoref(String otraRazonNoGeoref) {
        this.otraRazonNoGeoref = otraRazonNoGeoref;
    }

    public MovilInfo getMovilInfo() {
        return movilInfo;
    }

    public void setMovilInfo(MovilInfo movilInfo) {
        this.movilInfo = movilInfo;
    }

    @Override
	public String toString(){
		return this.codigo + " " + this.latitud+" "+this.longitud;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatosCoordenadas)) return false;

        DatosCoordenadas casa = (DatosCoordenadas) o;

        return (!codigo.equals(casa.codigo));
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
