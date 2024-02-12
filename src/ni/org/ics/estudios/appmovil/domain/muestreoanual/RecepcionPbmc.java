package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.util.Date;

public class RecepcionPbmc {
    private Integer codigo;
    private String fechaPbmc;
    private Double volPbmc;
    private Boolean rojoAdicional;
    private Double volRojoAdicional;
    private String lugar;
    private String observacion;
    private String username;
    private String estudio;
    private String estado;
    private String fechaCreacion;
    private Date fechaRegistro;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getFechaPbmc() {
        return fechaPbmc;
    }

    public void setFechaPbmc(String fechaPbmc) {
        this.fechaPbmc = fechaPbmc;
    }

    public Double getVolPbmc() {
        return volPbmc;
    }

    public void setVolPbmc(Double volPbmc) {
        this.volPbmc = volPbmc;
    }

    public Boolean getRojoAdicional() {
        return rojoAdicional;
    }

    public void setRojoAdicional(Boolean rojoAdicional) {
        this.rojoAdicional = rojoAdicional;
    }

    public Double getVolRojoAdicional() {
        return volRojoAdicional;
    }

    public void setVolRojoAdicional(Double volRojoAdicional) {
        this.volRojoAdicional = volRojoAdicional;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
