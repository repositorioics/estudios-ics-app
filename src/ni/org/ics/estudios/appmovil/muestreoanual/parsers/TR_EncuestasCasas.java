package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * User: alfasin
 * Date: 8/19/13
 */
@Root(name="TR_EncuestasCasas")
public class TR_EncuestasCasas {
	@Attribute
	private String id;
    @ElementList(entry="cuartos_c", inline=true)
    private List<Cuartos> cuartos_c;

    public TR_EncuestasCasas(){};

    public List<Cuartos>  getMatches() {
        return cuartos_c;
    }
}
