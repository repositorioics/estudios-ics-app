package ni.org.ics.estudios.appmovil.entomologia.domain;

import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by miguel on 1/9/2022.
 */
public class CuestionarioPuntoClave implements Serializable {

    private String codigoCuestionario;
    private Date fechaCuestionario;
    private Integer barrio;
    private String nombrePuntoClave;
    private String direccionPuntoClave;
    private String tipoPuntoClave;
    private String tipoPuntoProductividad;
    private String tipoPuntoProductividadOtro;
    private String tipoPuntoAglomeracion;
    private String tipoPuntoAglomeracionOtro;
    private Integer cuantasPersonasReunen;
    private Integer cuantosDiasSemanaReunen;
    private String horaInicioReunion;
    private String horaFinReunion;
    private String puntoGps;
    private Double latitud;
    private Double longitud;
    private String tipoIngresoCodigoSitio;
    private String codigoSitio;

    private String hayAmbientePERI;
    private String horaCapturaPERI;
    private Double humedadRelativaPERI;
    private Double temperaturaPERI;
    private String tipoIngresoCodigoPERI;
    private String codigoPERI;

    private String hayAmbienteINTRA;
    private String horaCapturaINTRA;
    private Double humedadRelativaINTRA;
    private Double temperaturaINTRA;
    private String tipoIngresoCodigoINTRA;
    private String codigoINTRA;

    private String nombrePersonaContesta;
    //odk
    private MovilInfo movilInfo;

    public String getCodigoCuestionario() {
        return codigoCuestionario;
    }

    public void setCodigoCuestionario(String codigoCuestionario) {
        this.codigoCuestionario = codigoCuestionario;
    }

    public Date getFechaCuestionario() {
        return fechaCuestionario;
    }

    public void setFechaCuestionario(Date fechaCuestionario) {
        this.fechaCuestionario = fechaCuestionario;
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

    public String getDireccionPuntoClave() {
        return direccionPuntoClave;
    }

    public void setDireccionPuntoClave(String direccionPuntoClave) {
        this.direccionPuntoClave = direccionPuntoClave;
    }

    public String getTipoPuntoClave() {
        return tipoPuntoClave;
    }

    public void setTipoPuntoClave(String tipoPuntoClave) {
        this.tipoPuntoClave = tipoPuntoClave;
    }

    public String getTipoPuntoProductividad() {
        return tipoPuntoProductividad;
    }

    public void setTipoPuntoProductividad(String tipoPuntoProductividad) {
        this.tipoPuntoProductividad = tipoPuntoProductividad;
    }

    public String getTipoPuntoProductividadOtro() {
        return tipoPuntoProductividadOtro;
    }

    public void setTipoPuntoProductividadOtro(String tipoPuntoProductividadOtro) {
        this.tipoPuntoProductividadOtro = tipoPuntoProductividadOtro;
    }

    public String getTipoPuntoAglomeracion() {
        return tipoPuntoAglomeracion;
    }

    public void setTipoPuntoAglomeracion(String tipoPuntoAglomeracion) {
        this.tipoPuntoAglomeracion = tipoPuntoAglomeracion;
    }

    public String getTipoPuntoAglomeracionOtro() {
        return tipoPuntoAglomeracionOtro;
    }

    public void setTipoPuntoAglomeracionOtro(String tipoPuntoAglomeracionOtro) {
        this.tipoPuntoAglomeracionOtro = tipoPuntoAglomeracionOtro;
    }

    public Integer getCuantasPersonasReunen() {
        return cuantasPersonasReunen;
    }

    public void setCuantasPersonasReunen(Integer cuantasPersonasReunen) {
        this.cuantasPersonasReunen = cuantasPersonasReunen;
    }

    public Integer getCuantosDiasSemanaReunen() {
        return cuantosDiasSemanaReunen;
    }

    public void setCuantosDiasSemanaReunen(Integer cuantosDiasSemanaReunen) {
        this.cuantosDiasSemanaReunen = cuantosDiasSemanaReunen;
    }

    public String getHoraInicioReunion() {
        return horaInicioReunion;
    }

    public void setHoraInicioReunion(String horaInicioReunion) {
        this.horaInicioReunion = horaInicioReunion;
    }

    public String getHoraFinReunion() {
        return horaFinReunion;
    }

    public void setHoraFinReunion(String horaFinReunion) {
        this.horaFinReunion = horaFinReunion;
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

    public String getTipoIngresoCodigoSitio() {
        return tipoIngresoCodigoSitio;
    }

    public void setTipoIngresoCodigoSitio(String tipoIngresoCodigoSitio) {
        this.tipoIngresoCodigoSitio = tipoIngresoCodigoSitio;
    }

    public String getCodigoSitio() {
        return codigoSitio;
    }

    public void setCodigoSitio(String codigoSitio) {
        this.codigoSitio = codigoSitio;
    }

    public String getHayAmbientePERI() {
        return hayAmbientePERI;
    }

    public void setHayAmbientePERI(String hayAmbientePERI) {
        this.hayAmbientePERI = hayAmbientePERI;
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
        return temperaturaPERI;
    }

    public void setTemperaturaPERI(Double temperaturaPERI) {
        this.temperaturaPERI = temperaturaPERI;
    }

    public String getTipoIngresoCodigoPERI() {
        return tipoIngresoCodigoPERI;
    }

    public void setTipoIngresoCodigoPERI(String tipoIngresoCodigoPERI) {
        this.tipoIngresoCodigoPERI = tipoIngresoCodigoPERI;
    }

    public String getCodigoPERI() {
        return codigoPERI;
    }

    public void setCodigoPERI(String codigoPERI) {
        this.codigoPERI = codigoPERI;
    }

    public String getHayAmbienteINTRA() {
        return hayAmbienteINTRA;
    }

    public void setHayAmbienteINTRA(String hayAmbienteINTRA) {
        this.hayAmbienteINTRA = hayAmbienteINTRA;
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
        return temperaturaINTRA;
    }

    public void setTemperaturaINTRA(Double temperaturaINTRA) {
        this.temperaturaINTRA = temperaturaINTRA;
    }

    public String getTipoIngresoCodigoINTRA() {
        return tipoIngresoCodigoINTRA;
    }

    public void setTipoIngresoCodigoINTRA(String tipoIngresoCodigoINTRA) {
        this.tipoIngresoCodigoINTRA = tipoIngresoCodigoINTRA;
    }

    public String getCodigoINTRA() {
        return codigoINTRA;
    }

    public void setCodigoINTRA(String codigoINTRA) {
        this.codigoINTRA = codigoINTRA;
    }

    public String getNombrePersonaContesta() {
        return nombrePersonaContesta;
    }

    public void setNombrePersonaContesta(String nombrePersonaContesta) {
        this.nombrePersonaContesta = nombrePersonaContesta;
    }

    public MovilInfo getMovilInfo() {
        return movilInfo;
    }

    public void setMovilInfo(MovilInfo movilInfo) {
        this.movilInfo = movilInfo;
    }

    @Override
    public String toString() {
        return "CuestionarioPuntoClave{" +codigoCuestionario + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuestionarioPuntoClave)) return false;

        CuestionarioPuntoClave that = (CuestionarioPuntoClave) o;

        if (!codigoCuestionario.equals(that.codigoCuestionario)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return codigoCuestionario.hashCode();
    }
}
