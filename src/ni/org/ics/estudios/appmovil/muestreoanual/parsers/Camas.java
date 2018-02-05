package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * User: alfasin
 * Date: 8/19/13
 */
@Root(name="camas_c",strict = false)
public class Camas {

    @Element(name = "personas_cama", required = false)
    public String personas_cama;

}