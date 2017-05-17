package ni.org.ics.estudios.appmovil.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Simple objeto de dominio que representa un participante de los estudios
 * 
 * @author William Aviles
 **/

public class Participante extends BaseMetaData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Integer codigo;
	private String nombre1;
	private String nombre2;
	private String apellido1;
	private String apellido2;
	private String sexo;
	private Date fechaNac;
    private String nombre1Padre;
    private String nombre2Padre;
    private String apellido1Padre;
    private String apellido2Padre;
    private String nombre1Madre;
    private String nombre2Madre;
    private String apellido1Madre;
    private String apellido2Madre;
    private Casa casa;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

    public String getNombre1Padre() {
        return nombre1Padre;
    }

    public void setNombre1Padre(String nombre1Padre) {
        this.nombre1Padre = nombre1Padre;
    }

    public String getNombre2Padre() {
        return nombre2Padre;
    }

    public void setNombre2Padre(String nombre2Padre) {
        this.nombre2Padre = nombre2Padre;
    }

    public String getApellido1Padre() {
        return apellido1Padre;
    }

    public void setApellido1Padre(String apellido1Padre) {
        this.apellido1Padre = apellido1Padre;
    }

    public String getApellido2Padre() {
        return apellido2Padre;
    }

    public void setApellido2Padre(String apellido2Padre) {
        this.apellido2Padre = apellido2Padre;
    }

    public String getNombre1Madre() {
        return nombre1Madre;
    }

    public void setNombre1Madre(String nombre1Madre) {
        this.nombre1Madre = nombre1Madre;
    }

    public String getNombre2Madre() {
        return nombre2Madre;
    }

    public void setNombre2Madre(String nombre2Madre) {
        this.nombre2Madre = nombre2Madre;
    }

    public String getApellido1Madre() {
        return apellido1Madre;
    }

    public void setApellido1Madre(String apellido1Madre) {
        this.apellido1Madre = apellido1Madre;
    }

    public String getApellido2Madre() {
        return apellido2Madre;
    }

    public void setApellido2Madre(String apellido2Madre) {
        this.apellido2Madre = apellido2Madre;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    /*
    Calcular edad del participante según su fecha de nacimiento y fecha actual del sistema
     */
    public String getEdad(){
        if (this.getFechaNac()!=null) {
            Date fecha = new Date();
            Integer iedadEnAnios;
            Integer iedadEnMeses;
            Integer iedadEnDias;

            Calendar calendarDOB = Calendar.getInstance();
            Calendar calendarToday = Calendar.getInstance();

            calendarToday.setTime(fecha);
            calendarDOB.setTime(this.fechaNac);
            iedadEnAnios = calendarToday.get(Calendar.YEAR) - calendarDOB.get(Calendar.YEAR);

            if (calendarToday.before(new GregorianCalendar(calendarToday.get(Calendar.YEAR), calendarDOB.get(Calendar.MONTH), calendarDOB.get(Calendar.DAY_OF_MONTH)))) {
                iedadEnAnios--;
                iedadEnMeses = (12 - (calendarDOB.get(Calendar.MONTH) + 1)) + (calendarDOB.get(Calendar.MONTH));

                if (calendarDOB.get(Calendar.DAY_OF_MONTH) > calendarToday.get(Calendar.DAY_OF_MONTH)) {
                    iedadEnDias = calendarDOB.get(Calendar.DAY_OF_MONTH) - calendarToday.get(Calendar.DAY_OF_MONTH);
                } else if (calendarDOB.get(Calendar.DAY_OF_MONTH) < calendarToday.get(Calendar.DAY_OF_MONTH)) {
                    iedadEnDias = calendarToday.get(Calendar.DAY_OF_MONTH) - calendarDOB.get(Calendar.DAY_OF_MONTH);
                } else {
                    iedadEnDias = 0;
                }
            } else if (calendarToday.after(new GregorianCalendar(calendarToday.get(Calendar.YEAR), calendarDOB.get(Calendar.MONTH), calendarDOB.get(Calendar.DAY_OF_MONTH)))) {
                iedadEnMeses = (calendarToday.get(Calendar.MONTH) - (calendarDOB.get(Calendar.MONTH)));
                if (calendarDOB.get(Calendar.DAY_OF_MONTH) > calendarToday.get(Calendar.DAY_OF_MONTH))
                    iedadEnDias = calendarDOB.get(Calendar.DAY_OF_MONTH) - calendarToday.get(Calendar.DAY_OF_MONTH) - calendarDOB.get(Calendar.DAY_OF_MONTH);
                else if (calendarDOB.get(Calendar.DAY_OF_MONTH) < calendarToday.get(Calendar.DAY_OF_MONTH)) {
                    iedadEnDias = calendarToday.get(Calendar.DAY_OF_MONTH) - calendarDOB.get(Calendar.DAY_OF_MONTH);
                } else
                    iedadEnDias = 0;
            } else {
                iedadEnAnios = calendarToday.get(Calendar.YEAR) - calendarDOB.get(Calendar.YEAR);
                iedadEnMeses = 0;
                iedadEnDias = 0;
            }

            return iedadEnAnios.toString() + " años " + iedadEnMeses.toString() + " meses " + iedadEnDias.toString() + " dias";
        }else{
            return "ND";
        }
    }

    @Override
    public String toString() {
        return "'" + codigo + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participante)) return false;

        Participante participante = (Participante) o;

        return (!codigo.equals(participante.codigo));
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
