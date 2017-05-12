package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

/**
 * Created by Miguel Salinas on 5/11/2017.
 * V1.0
 */
public class TamizajeFormLabels {

    protected String areaCobertura;
    protected String ninoMenor12Anios;
    protected String intencionPermanecerArea;
    protected String tieneTarjetaVacunaOIdentificacion;
    protected String enfermedadAgudaCronica;


    public TamizajeFormLabels(){
        areaCobertura = "Vive en área de cobertura del CSSFV.";
        ninoMenor12Anios = "Vive en un hogar con al menos 1 niño/a menor de 12 años.";
        intencionPermanecerArea = "Tiene la intención de permanecer en el área durante el estudio";
        tieneTarjetaVacunaOIdentificacion = "Tener una tarjeta de vacunación (niños/as) o un documento de identificación (adultos) para confirmar la edad y la residencia";
        enfermedadAgudaCronica = "EL participante está enfermo actualmente ? (enfermedad aguda y/o crónica)";
    }

    public String getAreaCobertura() {
        return areaCobertura;
    }

    public void setAreaCobertura(String areaCobertura) {
        this.areaCobertura = areaCobertura;
    }

    public String getNinoMenor12Anios() {
        return ninoMenor12Anios;
    }

    public void setNinoMenor12Anios(String ninoMenor12Anios) {
        this.ninoMenor12Anios = ninoMenor12Anios;
    }

    public String getIntencionPermanecerArea() {
        return intencionPermanecerArea;
    }

    public void setIntencionPermanecerArea(String intencionPermanecerArea) {
        this.intencionPermanecerArea = intencionPermanecerArea;
    }

    public String getTieneTarjetaVacunaOIdentificacion() {
        return tieneTarjetaVacunaOIdentificacion;
    }

    public void setTieneTarjetaVacunaOIdentificacion(String tieneTarjetaVacunaOIdentificacion) {
        this.tieneTarjetaVacunaOIdentificacion = tieneTarjetaVacunaOIdentificacion;
    }

    public String getEnfermedadAgudaCronica() {
        return enfermedadAgudaCronica;
    }

    public void setEnfermedadAgudaCronica(String enfermedadAgudaCronica) {
        this.enfermedadAgudaCronica = enfermedadAgudaCronica;
    }
}
