package ni.org.ics.estudios.appmovil.domain.cohortefamilia;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;

import java.io.Serializable;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class Paxgene extends BaseMetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String muestra;
    private String horaInicio;
    private String horaFin;

    public String getMuestra() {
        return muestra;
    }

    public void setMuestra(String muestra) {
        this.muestra = muestra;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return "Paxgene{" + muestra +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paxgene)) return false;

        Paxgene paxgene = (Paxgene) o;

        return  (!muestra.equals(paxgene.muestra));
    }

    @Override
    public int hashCode() {
        return muestra.hashCode();
    }
}
