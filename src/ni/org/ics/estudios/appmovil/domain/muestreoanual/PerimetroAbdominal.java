package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;

/**
 * Simple objeto de dominio que representa los datos del perimetro abdominal
 * participantes en el estudio
 *
 * @author Ing. Santiago Carballo
 **/
public class PerimetroAbdominal implements Serializable {

    private static final long serialVersionUID = 1L;
    private PerimetroAbdominalId paId;
    private Double pabdominal1;
    private Double pabdominal2;
    private Double pabdominal3;
    private Double difpabdominal;
    private MovilInfo movilInfo;
    private Integer otrorecurso1;
    private Integer otrorecurso2;
    private String tomoMedidaSn;
    private String razonNoTomoMedidas;
    private String estudiosAct; // estudios actuales al momento de llenar la encuestaprivate Integer otrorecurso2;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public PerimetroAbdominalId getPaId() {
        return paId;
    }

    public void setPaId(PerimetroAbdominalId paId) {
        this.paId = paId;
    }

    public Double getPabdominal1() {
        return pabdominal1;
    }

    public void setPabdominal1(Double pabdominal1) {
        this.pabdominal1 = pabdominal1;
    }

    public Double getPabdominal2() {
        return pabdominal2;
    }

    public void setPabdominal2(Double pabdominal2) {
        this.pabdominal2 = pabdominal2;
    }

    public Double getPabdominal3() {
        return pabdominal3;
    }

    public void setPabdominal3(Double pabdominal3) {
        this.pabdominal3 = pabdominal3;
    }

    public Double getDifpabdominal() {
        return difpabdominal;
    }

    public void setDifpabdominal(Double difpabdominal) {
        this.difpabdominal = difpabdominal;
    }

    public MovilInfo getMovilInfo() {
        return movilInfo;
    }

    public void setMovilInfo(MovilInfo movilInfo) {
        this.movilInfo = movilInfo;
    }

    public Integer getOtrorecurso1() {
        return otrorecurso1;
    }

    public void setOtrorecurso1(Integer otrorecurso1) {
        this.otrorecurso1 = otrorecurso1;
    }

    public Integer getOtrorecurso2() {
        return otrorecurso2;
    }

    public void setOtrorecurso2(Integer otrorecurso2) {
        this.otrorecurso2 = otrorecurso2;
    }

    public String getTomoMedidaSn() {
        return tomoMedidaSn;
    }

    public void setTomoMedidaSn(String tomoMedidaSn) {
        this.tomoMedidaSn = tomoMedidaSn;
    }

    public String getRazonNoTomoMedidas() {
        return razonNoTomoMedidas;
    }

    public void setRazonNoTomoMedidas(String razonNoTomoMedidas) {
        this.razonNoTomoMedidas = razonNoTomoMedidas;
    }

    public String getEstudiosAct() {
        return estudiosAct;
    }

    public void setEstudiosAct(String estudiosAct) {
        this.estudiosAct = estudiosAct;
    }
}
