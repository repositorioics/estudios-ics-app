package ni.org.ics.estudios.appmovil.muestreoanual.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Date;

/**
 * Objeto para capturar informacion del Xml producido por ODK en el formulario casa
 *
 * @author Ing. Santiago Carballo
 **/
public class PerimetroAbdominalXml {
    @Element(required=false)
    private String titulo1;

    @Element(required=false)
    private String titulo_final_pt;

    @Element(required=false)
    private Double pabdominal1;

    @Element(required=false)
    private Double pabdominal2;

    @Element(required=false)
    private Double pabdominal3;

    @Element(required=false)
    private Double difpabdominal;

    @Element(required=false)
    private String toma_medidas;

    @Element(required=false)
    private String razon_no_toma_medidas;

    @Element(required=false)
    private Integer otrorecurso1;

    @Element(required=false)
    private Integer otrorecurso2;

    @Element(required=false)
    private String start;

    @Element(required=false)
    private String end;

    @Element(required=false)
    private String deviceid;

    @Element(required=false)
    private String simserial;

    @Element(required=false)
    private String phonenumber;

    @Element(required=false)
    private Date today;

    @Element
    private Integer recurso1;

    @Element
    private Integer recurso2;

    @Attribute
    private String id;

    @Element(required=false)
    private Meta meta;

    public PerimetroAbdominalXml(){

    }

    public String getTitulo1() {
        return titulo1;
    }

    public void setTitulo1(String titulo1) {
        this.titulo1 = titulo1;
    }

    public String getTitulo_final_pt() {
        return titulo_final_pt;
    }

    public void setTitulo_final_pt(String titulo_final_pt) {
        this.titulo_final_pt = titulo_final_pt;
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

    public String getToma_medidas() {
        return toma_medidas;
    }

    public void setToma_medidas(String toma_medidas) {
        this.toma_medidas = toma_medidas;
    }

    public String getRazon_no_toma_medidas() {
        return razon_no_toma_medidas;
    }

    public void setRazon_no_toma_medidas(String razon_no_toma_medidas) {
        this.razon_no_toma_medidas = razon_no_toma_medidas;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getSimserial() {
        return simserial;
    }

    public void setSimserial(String simserial) {
        this.simserial = simserial;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public Integer getRecurso1() {
        return recurso1;
    }

    public void setRecurso1(Integer recurso1) {
        this.recurso1 = recurso1;
    }

    public Integer getRecurso2() {
        return recurso2;
    }

    public void setRecurso2(Integer recurso2) {
        this.recurso2 = recurso2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }


}
