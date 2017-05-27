package ni.org.ics.estudios.appmovil.utils;

import java.util.Calendar;
import java.util.Date;


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
            Calendar calendarDOB = Calendar.getInstance();
            Calendar calendarToday = Calendar.getInstance();

            calendarToday.setTime(new Date());
            calendarDOB.setTime(this.fechaNac);
            Integer diaInicio = calendarDOB.get(Calendar.DAY_OF_MONTH);
            Integer mesInicio = calendarDOB.get(Calendar.MONTH)+1;
            Integer anioInicio = calendarDOB.get(Calendar.YEAR);

            Integer diaActual = calendarToday.get(Calendar.DAY_OF_MONTH);
            Integer mesActual = calendarToday.get(Calendar.MONTH)+1;
            Integer anioActual = calendarToday.get(Calendar.YEAR);

            System.out.println(diaActual);
            System.out.println(mesActual);
            System.out.println(anioActual);
            int b = 0;
            Integer dias = 0;
            Integer anios = 0;
            Integer meses = 0;
            b = calendarDOB.getActualMaximum(Calendar.DAY_OF_MONTH);
            if ((anioInicio > anioActual) || (anioInicio.equals(anioActual) && mesInicio > mesActual)
                    || (anioInicio.equals(anioActual) && mesInicio.equals(mesActual) && diaInicio > diaActual)) {
                return "ND";
            } else {
                if (mesInicio <= mesActual) {
                    anios = anioActual - anioInicio;
                    if (diaInicio <= diaActual) {
                        meses = mesActual - mesInicio;
                        dias = (diaActual - diaInicio);
                    } else {
                        if (mesActual.equals(mesInicio)) {
                            anios = anios - 1;
                        }
                        meses = (mesActual - mesInicio - 1 + 12) % 12;
                        dias = b - (diaInicio - diaActual);
                    }
                } else {
                    anios = anioActual - anioInicio - 1;
                    System.out.println(anios);
                    if (diaInicio > diaActual) {
                        meses = mesActual - mesInicio - 1 + 12;
                        dias = b - (diaInicio - diaActual);
                    } else {
                        meses = mesActual - mesInicio + 12;
                        dias = diaActual - diaInicio;
                    }
                }
            }
            return anios.toString() + "/" + meses.toString() + "/" + dias.toString();
        }else{
            return "ND";
        }
    }

}
