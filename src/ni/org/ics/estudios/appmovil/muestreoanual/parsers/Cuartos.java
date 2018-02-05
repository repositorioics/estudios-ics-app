package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * User: alfasin
 * Date: 8/19/13
 */
@Root(name="cuartos_c",strict = false)
public class Cuartos {

    @Element(name = "sensor_cuarto", required = false)
    public String sensor_cuarto;

    @Element(name = "sensor_cuartoCod", required = false)
    public String sensor_cuartoCod;

    @Element(name = "camas_cuarto", required = false)
    public String camas_cuarto;
    
    @ElementList(entry="camas_c", inline=true)
    private List<Camas> camas_c;

}