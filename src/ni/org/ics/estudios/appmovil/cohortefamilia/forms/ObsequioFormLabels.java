package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 06/11/2018.
 * V1.0
 */
public class ObsequioFormLabels {

    private String obsequioSN;
    private String personaRecibe;
    private String relacionFam;
    private String otraRelacionFam;
    private String telefono;
    private String observaciones;

    public ObsequioFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        obsequioSN = res.getString(R.string.obsequioSN);
        personaRecibe = res.getString(R.string.personaRecibe);
        relacionFam = res.getString(R.string.relacionFamObs);
        otraRelacionFam = res.getString(R.string.otraRelacionFamObs);
        telefono = res.getString(R.string.telefono);
        observaciones = res.getString(R.string.observaciones);
    }

    public String getObsequioSN() {
        return obsequioSN;
    }

    public void setObsequioSN(String obsequioSN) {
        this.obsequioSN = obsequioSN;
    }

    public String getPersonaRecibe() {
        return personaRecibe;
    }

    public void setPersonaRecibe(String personaRecibe) {
        this.personaRecibe = personaRecibe;
    }

    public String getRelacionFam() {
        return relacionFam;
    }

    public void setRelacionFam(String relacionFam) {
        this.relacionFam = relacionFam;
    }

    public String getOtraRelacionFam() {
        return otraRelacionFam;
    }

    public void setOtraRelacionFam(String otraRelacionFam) {
        this.otraRelacionFam = otraRelacionFam;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
