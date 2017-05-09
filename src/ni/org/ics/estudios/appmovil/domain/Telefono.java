package ni.org.ics.estudios.appmovil.domain;


import ni.org.ics.estudios.appmovil.catalogs.Operadora;

/**
 * Objeto que representa un número telefónico asociado a una casa o participante
 * Created by Miguel Salinas on 4/28/2017.
 * V1.0
 */
public class Telefono extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String numero;
    private Operadora operadora;
    private Casa casa;
    private Participante participante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Operadora getOperadora() {
        return operadora;
    }

    public void setOperadora(Operadora operadora) {
        this.operadora = operadora;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Telefono)) return false;

        Telefono telefono = (Telefono) o;

        return  (!id.equals(telefono.id));
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
