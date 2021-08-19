package ni.org.ics.estudios.appmovil.utils;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;

import java.util.List;

/***
 * Created by Miguel Salinas
 * 28-04-2021
 */
public class MessageResourceUtil {

    public static String getRelacionFamiliar(List<MessageResource> catRelacionFamiliar, String codigo) {
        Resources res = MyIcsApplication.getContext().getResources();
        String relacionFamiliar = res.getString(R.string.sinRelacFam);
        try {
            for (MessageResource message : catRelacionFamiliar) {
                if (message.getCatKey().equalsIgnoreCase(codigo)) {
                    relacionFamiliar = message.getSpanish();
                    break;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return relacionFamiliar;
    }
}
