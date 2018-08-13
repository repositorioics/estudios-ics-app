package ni.org.ics.estudios.appmovil.domain;

import ni.org.ics.estudios.appmovil.catalogs.Barrio;

/**
 * Objeto que representa un número telefónico asociado a una casa o participante
 * Created by Miguel Salinas on 4/28/2017.
 * V1.0
 */
public class ContactoParticipante extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private Participante participante;
    private String nombre;
    private String Direccion;
    private Barrio barrio;
    private String numero1;
    private String operadora1;
    private String tipo1;
    private String numero2;
    private String operadora2;
    private String tipo2;
    //reconsentimiento 2018
    private String numero3;
    private String operadora3;
    private String tipo3;
    private String esPropio;
    private String otroBarrio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    public String getNumero1() {
        return numero1;
    }

    public void setNumero1(String numero1) {
        this.numero1 = numero1;
    }

    public String getOperadora1() {
        return operadora1;
    }

    public void setOperadora1(String operadora1) {
        this.operadora1 = operadora1;
    }

    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getNumero2() {
        return numero2;
    }

    public void setNumero2(String numero2) {
        this.numero2 = numero2;
    }

    public String getOperadora2() {
        return operadora2;
    }

    public void setOperadora2(String operadora2) {
        this.operadora2 = operadora2;
    }

    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    public String getNumero3() {
        return numero3;
    }

    public void setNumero3(String numero3) {
        this.numero3 = numero3;
    }

    public String getOperadora3() {
        return operadora3;
    }

    public void setOperadora3(String operadora3) {
        this.operadora3 = operadora3;
    }

    public String getTipo3() {
        return tipo3;
    }

    public void setTipo3(String tipo3) {
        this.tipo3 = tipo3;
    }

    public String getEsPropio() {
        return esPropio;
    }

    public void setEsPropio(String esPropio) {
        this.esPropio = esPropio;
    }

    public String getOtroBarrio() {
        return otroBarrio;
    }

    public void setOtroBarrio(String otroBarrio) {
        this.otroBarrio = otroBarrio;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactoParticipante)) return false;

        ContactoParticipante telefonoContacto = (ContactoParticipante) o;

        return  (!id.equals(telefonoContacto.id));
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
