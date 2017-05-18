package ni.org.ics.estudios.appmovil.domain.cohortefamilia;


import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Casa;

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
    private char aceptaTamizajeCasa;
    private String razonNoAceptaTamizajeCasa;
    private Casa casa;
    private Estudio estudio;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public char getAceptaTamizajeCasa() {
        return aceptaTamizajeCasa;
    }

    public void setAceptaTamizajeCasa(char aceptaTamizajeCasa) {
        this.aceptaTamizajeCasa = aceptaTamizajeCasa;
    }

    public String getRazonNoAceptaTamizajeCasa() {
        return razonNoAceptaTamizajeCasa;
    }

    public void setRazonNoAceptaTamizajeCasa(String razonNoAceptaTamizajeCasa) {
        this.razonNoAceptaTamizajeCasa = razonNoAceptaTamizajeCasa;
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
