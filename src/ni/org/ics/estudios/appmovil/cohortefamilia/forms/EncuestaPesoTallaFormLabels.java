package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 5/19/2017.
 * V1.0
 */
public class EncuestaPesoTallaFormLabels {

    protected String tomoMedidaSn;
    protected String razonNoTomoMedidas;
    protected String peso1;
    protected String peso2;
    protected String peso3;
    protected String talla1;
    protected String talla2;
    protected String talla3;
    protected String imc1;
    protected String imc2;
    protected String imc3;
    protected String difPeso;
    protected String difTalla;
    protected String pesoHint;
    protected String tallaHint;
    protected String difMediciones;

    public EncuestaPesoTallaFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        tomoMedidaSn = res.getString(R.string.tomoMedidaSn);
        razonNoTomoMedidas = res.getString(R.string.razonNoTomoMedidas);
        peso1 = res.getString(R.string.peso1);
        peso2 = res.getString(R.string.peso2);
        peso3 = res.getString(R.string.peso3);
        talla1 = res.getString(R.string.talla1);
        talla2 = res.getString(R.string.talla2);
        talla3 = res.getString(R.string.talla3);
        imc1 = res.getString(R.string.imc1);
        imc2 = res.getString(R.string.imc2);
        imc3 = res.getString(R.string.imc3);
        difPeso = res.getString(R.string.difPeso);
        difTalla = res.getString(R.string.difTalla);
        pesoHint = res.getString(R.string.pesoHint);
        tallaHint = res.getString(R.string.tallaHint);
        difMediciones = res.getString(R.string.difMediciones);

    }

    public String getPeso1() {
        return peso1;
    }

    public void setPeso1(String peso1) {
        this.peso1 = peso1;
    }

    public String getPeso2() {
        return peso2;
    }

    public void setPeso2(String peso2) {
        this.peso2 = peso2;
    }

    public String getPeso3() {
        return peso3;
    }

    public void setPeso3(String peso3) {
        this.peso3 = peso3;
    }

    public String getTalla1() {
        return talla1;
    }

    public void setTalla1(String talla1) {
        this.talla1 = talla1;
    }

    public String getTalla2() {
        return talla2;
    }

    public void setTalla2(String talla2) {
        this.talla2 = talla2;
    }

    public String getTalla3() {
        return talla3;
    }

    public void setTalla3(String talla3) {
        this.talla3 = talla3;
    }

    public String getImc1() {
        return imc1;
    }

    public void setImc1(String imc1) {
        this.imc1 = imc1;
    }

    public String getImc2() {
        return imc2;
    }

    public void setImc2(String imc2) {
        this.imc2 = imc2;
    }

    public String getImc3() {
        return imc3;
    }

    public void setImc3(String imc3) {
        this.imc3 = imc3;
    }

    public String getDifPeso() {
        return difPeso;
    }

    public void setDifPeso(String difPeso) {
        this.difPeso = difPeso;
    }

    public String getDifTalla() {
        return difTalla;
    }

    public void setDifTalla(String difTalla) {
        this.difTalla = difTalla;
    }

    public String getPesoHint() {
        return pesoHint;
    }

    public void setPesoHint(String pesoHint) {
        this.pesoHint = pesoHint;
    }

    public String getTallaHint() {
        return tallaHint;
    }

    public void setTallaHint(String tallaHint) {
        this.tallaHint = tallaHint;
    }

    public String getDifMediciones() {
        return difMediciones;
    }

    public void setDifMediciones(String difMediciones) {
        this.difMediciones = difMediciones;
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
}
