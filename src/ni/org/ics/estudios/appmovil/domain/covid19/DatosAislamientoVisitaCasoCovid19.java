package ni.org.ics.estudios.appmovil.domain.covid19;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

import java.util.Date;

public class DatosAislamientoVisitaCasoCovid19 extends BaseMetaData {
    private static final long serialVersionUID = 1L;
    private String codigoAislamiento;
    private VisitaSeguimientoCasoCovid19 codigoCasoVisita;
    private Date fechaDato;
    private String aislado;
    private String dormirSoloComparte;
    private String banioPropioComparte;
    private String alejadoFamilia;
    private String tieneContacto;
    private String conQuienTieneContacto;
    private String lugares;
    private String otrosLugares;

    public String getCodigoAislamiento() {
        return codigoAislamiento;
    }

    public void setCodigoAislamiento(String codigoAislamiento) {
        this.codigoAislamiento = codigoAislamiento;
    }

    public VisitaSeguimientoCasoCovid19 getCodigoCasoVisita() {
        return codigoCasoVisita;
    }

    public void setCodigoCasoVisita(VisitaSeguimientoCasoCovid19 codigoCasoVisita) {
        this.codigoCasoVisita = codigoCasoVisita;
    }

    public Date getFechaDato() {
        return fechaDato;
    }

    public void setFechaDato(Date fechaDato) {
        this.fechaDato = fechaDato;
    }

    public String getAislado() {
        return aislado;
    }

    public void setAislado(String aislado) {
        this.aislado = aislado;
    }

    public String getDormirSoloComparte() {
        return dormirSoloComparte;
    }

    public void setDormirSoloComparte(String dormirSoloComparte) {
        this.dormirSoloComparte = dormirSoloComparte;
    }

    public String getBanioPropioComparte() {
        return banioPropioComparte;
    }

    public void setBanioPropioComparte(String banioPropioComparte) {
        this.banioPropioComparte = banioPropioComparte;
    }

    public String getAlejadoFamilia() {
        return alejadoFamilia;
    }

    public void setAlejadoFamilia(String alejadoFamilia) {
        this.alejadoFamilia = alejadoFamilia;
    }

    public String getTieneContacto() {
        return tieneContacto;
    }

    public void setTieneContacto(String tieneContacto) {
        this.tieneContacto = tieneContacto;
    }

    public String getConQuienTieneContacto() {
        return conQuienTieneContacto;
    }

    public void setConQuienTieneContacto(String conQuienTieneContacto) {
        this.conQuienTieneContacto = conQuienTieneContacto;
    }

    public String getLugares() {
        return lugares;
    }

    public void setLugares(String lugares) {
        this.lugares = lugares;
    }

    public String getOtrosLugares() {
        return otrosLugares;
    }

    public void setOtrosLugares(String otrosLugares) {
        this.otrosLugares = otrosLugares;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatosAislamientoVisitaCasoCovid19 that = (DatosAislamientoVisitaCasoCovid19) o;
        return codigoAislamiento.equals(that.codigoAislamiento);
    }

    @Override
    public int hashCode() {
        int result = codigoAislamiento.hashCode();
        result = 31 * result + fechaDato.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DatosAislamientoVisitaCasoCovid19{" +
                "codigoAislamiento='" + codigoAislamiento + '\'' +
                ", fechaDato=" + fechaDato +
                '}';
    }
}
