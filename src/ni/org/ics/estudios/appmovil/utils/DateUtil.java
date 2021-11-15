package ni.org.ics.estudios.appmovil.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    /**
     * Convierte un string a Date segun el formato indicado
     * @param strFecha cadena a convertir
     * @param formato formato solicitado
     * @return Fecha
     * @throws java.text.ParseException
     */
    public static Date StringToDate(String strFecha, String formato) throws ParseException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
            return simpleDateFormat.parse(strFecha);
        }catch (Exception ex){
            return null;
        }
    }
}
