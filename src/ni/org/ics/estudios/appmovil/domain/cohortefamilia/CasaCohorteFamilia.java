package ni.org.ics.estudios.appmovil.domain.cohortefamilia;


import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Casa;

/**
 * Created by Miguel Salinas on 5/11/2017.
 * V1.0
 */
public class CasaCohorteFamilia extends BaseMetaData {

    private String codigoCHF;
    private Casa casa;
    private String nombre1JefeFamilia;
    private String nombre2JefeFamilia;
    private String apellido1JefeFamilia;
    private String apellido2JefeFamilia;

    public String getCodigoCHF() {
        return codigoCHF;
    }

    public void setCodigoCHF(String codigoCHF) {
        this.codigoCHF = codigoCHF;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public String getNombre1JefeFamilia() {
        return nombre1JefeFamilia;
    }

    public void setNombre1JefeFamilia(String nombre1JefeFamilia) {
        this.nombre1JefeFamilia = nombre1JefeFamilia;
    }

    public String getNombre2JefeFamilia() {
        return nombre2JefeFamilia;
    }

    public void setNombre2JefeFamilia(String nombre2JefeFamilia) {
        this.nombre2JefeFamilia = nombre2JefeFamilia;
    }

    public String getApellido1JefeFamilia() {
        return apellido1JefeFamilia;
    }

    public void setApellido1JefeFamilia(String apellido1JefeFamilia) {
        this.apellido1JefeFamilia = apellido1JefeFamilia;
    }

    public String getApellido2JefeFamilia() {
        return apellido2JefeFamilia;
    }

    public void setApellido2JefeFamilia(String apellido2JefeFamilia) {
        this.apellido2JefeFamilia = apellido2JefeFamilia;
    }

    @Override
    public String toString() {
        return "CasaCohorteFamilia{" + casa.getCodigo() +
                ", " + codigoCHF + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasaCohorteFamilia)) return false;

        CasaCohorteFamilia that = (CasaCohorteFamilia) o;

        if (!casa.equals(that.casa)) return false;
        if (!codigoCHF.equals(that.codigoCHF)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoCHF.hashCode();
        result = 31 * result + casa.hashCode();
        return result;
    }
}
