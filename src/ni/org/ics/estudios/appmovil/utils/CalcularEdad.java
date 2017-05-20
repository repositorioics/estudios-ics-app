package ni.org.ics.estudios.appmovil.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Constantes usadas en multiples clases de la aplicacion
 * 
 * @author William Aviles
 * 
 */
public class CalcularEdad {
	
	private Date fechaNac;
	
	
	
	
	public CalcularEdad(Date fechaNac) {
		super();
		this.fechaNac = fechaNac;
	}




	/*
    Calcular edad del participante según su fecha de nacimiento y fecha actual del sistema
     */
    public String getEdad(){
        if (this.fechaNac!=null) {
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

            return iedadEnAnios.toString() + "/" + iedadEnMeses.toString() + "/" + iedadEnDias.toString();
        }else{
            return "ND";
        }
    }

}
