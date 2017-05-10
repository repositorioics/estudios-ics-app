package ni.org.ics.estudios.appmovil.domain;


import ni.org.ics.estudios.appmovil.catalogs.Estudio;

/**
 * Created by FIRSTICT on 4/28/2017.
 * V1.0
 */
public class PreTamizaje extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
    private char aceptaTamizaje;
    private String razonNoParticipa;
    private Casa casa;
    private Estudio estudio;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public char getAceptaTamizaje() {
        return aceptaTamizaje;
    }

    public void setAceptaTamizaje(char aceptaTamizaje) {
        this.aceptaTamizaje = aceptaTamizaje;
    }

    public String getRazonNoParticipa() {
        return razonNoParticipa;
    }

    public void setRazonNoParticipa(String razonNoParticipa) {
        this.razonNoParticipa = razonNoParticipa;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public Estudio getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
    }

    @Override
    public String toString() {
        return "'" + codigo + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreTamizaje)) return false;

        PreTamizaje preTamizaje = (PreTamizaje) o;

        return (!codigo.equals(preTamizaje.codigo));
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
