package ni.org.ics.estudios.appmovil.domain;

import ni.org.ics.estudios.appmovil.catalogs.Barrio;

import java.io.Serializable;


/**
 *  Objeto que representa CambioDomicilio
 * @author Miguel Salinas
 **/

public class CambioDomicilio extends BaseMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String codigo;
    private Participante participante;
	private Barrio barrio;
    private String otroBarrio;
	private String direccion;
    private String manzana;
    private String puntoGps;
	private Double latitud;
	private Double longitud;
    private String razonNoGeoref;
    private String otraRazonNoGeoref;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
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

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
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

    @Override
	public String toString(){
		return this.codigo + " " + this.latitud+" "+this.longitud;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CambioDomicilio)) return false;

        CambioDomicilio casa = (CambioDomicilio) o;

        return (!codigo.equals(casa.codigo));
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
