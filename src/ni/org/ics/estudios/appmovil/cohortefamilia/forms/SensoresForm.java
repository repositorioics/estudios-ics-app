package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.List;

public class SensoresForm extends AbstractWizardModel {


	private SensoresFormLabels labels;
    private EstudiosAdapter estudiosAdapter;
    private String[] catSiNo;

    public SensoresForm(Context context, String pass) {
        super(context,pass);
    }

    private String[] fillCatalog(String codigoCatalogo){
        String[] catalogo;
        List<MessageResource> mCatalogo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='"+codigoCatalogo+"'", CatalogosDBConstants.order);
        catalogo = new String[mCatalogo.size()];
        int index = 0;
        for (MessageResource message: mCatalogo){
            catalogo[index] = message.getSpanish();
            index++;
        }
        return catalogo;
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new SensoresFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);

        estudiosAdapter.open();
        catSiNo = fillCatalog("CHF_CAT_SINO");
        estudiosAdapter.close();
        Page sensorSN = new SingleFixedChoicePage(this, labels.getSensorSN(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page razonN = new TextPage(this, labels.getRazonNoColocaSensor(), labels.getRazonNoColocaSensorHint(), Constants.WIZARD, false).setRequired(true);
		Page numSensor = new BarcodePage(this,labels.getNumSensor(),labels.getNumSensorHint(), Constants.WIZARD, false).setRequired(true);
        Page enHabitacion = new SingleFixedChoicePage(this, labels.getEnHabitacion(), labels.getEnHabitacionHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page cuartoSensor = new SelectCuartoPage(this,labels.getHabitacionSensor(),labels.getHabitacionSensorHint(), Constants.WIZARD, false).setRequired(true);
        Page areaSensor = new SelectAreaAmbientePage(this,labels.getAreaSensor(),labels.getAreaSensorHint(), Constants.WIZARD, false).setRequired(true);
        Page fechaSensor = new NewDatePage(this,labels.getFechaSensor(),labels.getFechaSensorHint(), Constants.WIZARD, false).setRequired(true);

        return new PageList(sensorSN, razonN, numSensor, enHabitacion, cuartoSensor, areaSensor, fechaSensor);
    }

    public SensoresFormLabels getLabels() {
        return labels;
    }

    public void setLabels(SensoresFormLabels labels) {
        this.labels = labels;
    }
}
