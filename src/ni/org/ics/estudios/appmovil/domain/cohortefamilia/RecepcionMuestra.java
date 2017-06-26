package ni.org.ics.estudios.appmovil.domain.cohortefamilia;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import java.util.Date;

/**
 * Simple objeto de dominio que representa los datos de una recepción de muestra
 * 
 * @author Miguel Salinas
 **/
public class RecepcionMuestra extends BaseMetaData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
    private String codigo;
    private String codigoMx;
    private Date fechaRecepcion;
    private String paxgene;
	private Double volumen;
	private String lugar;
	private String observacion;
    private String tubo;
    private String tipoMuestra;
    private String proposito;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoMx() {
        return codigoMx;
    }

    public void setCodigoMx(String codigoMx) {
        this.codigoMx = codigoMx;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getPaxgene() {
        return paxgene;
    }

    public void setPaxgene(String paxgene) {
        this.paxgene = paxgene;
    }

    public Double getVolumen() {
        return volumen;
    }

    public void setVolumen(Double volumen) {
        this.volumen = volumen;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTubo() {
        return tubo;
    }

    public void setTubo(String tubo) {
        this.tubo = tubo;
    }

    public String getTipoMuestra() {
        return tipoMuestra;
    }

    public void setTipoMuestra(String tipoMuestra) {
        this.tipoMuestra = tipoMuestra;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    @Override
    public String toString() {
        return "RecepcionMuestra{'" + codigo +'}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecepcionMuestra)) return false;

        RecepcionMuestra that = (RecepcionMuestra) o;

        if (!codigo.equals(that.codigo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
