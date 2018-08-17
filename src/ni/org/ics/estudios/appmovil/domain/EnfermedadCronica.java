package ni.org.ics.estudios.appmovil.domain;

import java.io.Serializable;

/**
 * Created by Miguel Salinas on 16/08/2018.
 * V1.0
 */
public class EnfermedadCronica extends BaseMetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String Id;
    private Tamizaje tamizaje;
    private String enfermedad;
    private String anioEnfermedad;
    private String mesEnfermedad;
    private String otraEnfermedad;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Tamizaje getTamizaje() {
        return tamizaje;
    }

    public void setTamizaje(Tamizaje tamizaje) {
        this.tamizaje = tamizaje;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getAnioEnfermedad() {
        return anioEnfermedad;
    }

    public void setAnioEnfermedad(String anioEnfermedad) {
        this.anioEnfermedad = anioEnfermedad;
    }

    public String getMesEnfermedad() {
        return mesEnfermedad;
    }

    public void setMesEnfermedad(String mesEnfermedad) {
        this.mesEnfermedad = mesEnfermedad;
    }

    public String getOtraEnfermedad() {
        return otraEnfermedad;
    }

    public void setOtraEnfermedad(String otraEnfermedad) {
        this.otraEnfermedad = otraEnfermedad;
    }

    @Override
    public String toString() {
        return "EnfermedadCronica{'" + Id + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnfermedadCronica)) return false;

        EnfermedadCronica that = (EnfermedadCronica) o;

        if (!Id.equals(that.Id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Id.hashCode();
    }
}
