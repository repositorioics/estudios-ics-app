package ni.org.ics.estudios.appmovil.covid19.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.MuestrasFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.List;

/**
 * Created by Miguel Salinas on 14/06/2020.
 * V1.0
 */
public class MuestraAdicRojoEDTACitratoCovid19Form extends AbstractWizardModel {

    int index = 0;
    private String[] catSiNo;
    private String[] catRazon;
    private String[] catObservacion;
    private String[] catPinchazos;

    private MuestrasFormLabels labels;
    private EstudiosAdapter estudiosAdapter;

    public MuestraAdicRojoEDTACitratoCovid19Form(Context context, String pass) {
        super(context,pass);
    }

    private String[] fillCatalog(String codigoCatalogo){
        String[] catalogo;
        List<MessageResource> mCatalogo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='"+codigoCatalogo+"'", CatalogosDBConstants.order);
        catalogo = new String[mCatalogo.size()];
        index = 0;
        for (MessageResource message: mCatalogo){
            catalogo[index] = message.getSpanish();
            index++;
        }
        return catalogo;
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new MuestrasFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = fillCatalog("CHF_CAT_SINO");
        catRazon = fillCatalog("CHF_CAT_RAZON_NO_MX");
        catObservacion = fillCatalog("CHF_CAT_OBSERV_MX");
        catPinchazos = fillCatalog("CHF_CAT_PINCH_MX");

        estudiosAdapter.close();

        //rojo
        Page scTomaMxSn = new SingleFixedChoicePage(this, labels.getTomaMxSn(), labels.getTuboRojoHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scRazonNoToma = new SingleFixedChoicePage(this, labels.getRazonNoToma(), "", Constants.WIZARD, false).setChoices(catRazon).setRequired(true);
        Page tpDescOtraRazonNoToma = new TextPage(this, labels.getDescOtraRazonNoToma(), labels.getDescOtraRazonNoTomaHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx = new BarcodePage(this, labels.getCodigoMx(), "", Constants.WIZARD, false).setRequired(true);
        Page npVolumen = new NumberPage(this, labels.getVolumen(), labels.getVolumenHint(), Constants.WIZARD, false).setRangeValidation(true, 0, 5).setRequired(true);
        Page scObservacion = new SingleFixedChoicePage(this, labels.getObservacion(), "", Constants.WIZARD, false).setChoices(catObservacion).setRequired(true);
        Page tpDescOtraObservacion = new TextPage(this, labels.getDescOtraObservacion(), labels.getDescOtraObservacionHint(), Constants.WIZARD, false).setRequired(true);

        //EDTA
        Page scTomaMxSnEDTA = new SingleFixedChoicePage(this, labels.getTomaMxSnEDTA(), labels.getTuboEDTAHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scRazonNoTomaEDTA = new SingleFixedChoicePage(this, labels.getRazonNoTomaEDTA(), "", Constants.WIZARD, false).setChoices(catRazon).setRequired(true);
        Page tpDescOtraRazonNoTomaEDTA = new TextPage(this, labels.getDescOtraRazonNoTomaEDTA(), labels.getDescOtraRazonNoTomaHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMxEDTA = new BarcodePage(this, labels.getCodigoMxEDTA(), "", Constants.WIZARD, false).setRequired(true);
        Page npVolumenEDTA = new NumberPage(this, labels.getVolumenEDTA(), labels.getVolumenHint(), Constants.WIZARD, false).setRangeValidation(true, 0, 5).setRequired(true);
        Page scObservacionEDTA = new SingleFixedChoicePage(this, labels.getObservacionEDTA(), "", Constants.WIZARD, false).setChoices(catObservacion).setRequired(true);
        Page tpDescOtraObservacionEDTA = new TextPage(this, labels.getDescOtraObservacionEDTA(), labels.getDescOtraObservacionHint(), Constants.WIZARD, false).setRequired(true);
        //Page scNumPinchazosEDTA = new SingleFixedChoicePage(this, labels.getNumPinchazos(), "", Constants.WIZARD, false).setChoices(catPinchazos).setRequired(true);
        //Citrato
        Page scTomaMxSnCit = new SingleFixedChoicePage(this, labels.getTomaMxSnCit(), labels.getTuboCitratoHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scRazonNoTomaCit = new SingleFixedChoicePage(this, labels.getRazonNoTomaCit(), "", Constants.WIZARD, false).setChoices(catRazon).setRequired(true);
        Page tpDescOtraRazonNoTomaCit = new TextPage(this, labels.getDescOtraRazonNoTomaCit(), labels.getDescOtraRazonNoTomaHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMxCit = new BarcodePage(this, labels.getCodigoMxCit(), "", Constants.WIZARD, false).setRequired(true);
        Page npVolumenCit = new NumberPage(this, labels.getVolumenCit(), labels.getVolumenHint(), Constants.WIZARD, false).setRangeValidation(true, 0, 7).setRequired(true);
        Page scObservacionCit = new SingleFixedChoicePage(this, labels.getObservacionCit(), "", Constants.WIZARD, false).setChoices(catObservacion).setRequired(true);
        Page tpDescOtraObservacionCit = new TextPage(this, labels.getDescOtraObservacionCit(), labels.getDescOtraObservacionHint(), Constants.WIZARD, false).setRequired(true);
        //TOdos
        Page scNumPinchazos = new SingleFixedChoicePage(this, labels.getNumPinchazos(), "", Constants.WIZARD, false).setChoices(catPinchazos).setRequired(true);

        return new PageList(scTomaMxSn, scRazonNoToma, tpDescOtraRazonNoToma, bcCodigoMx, npVolumen, scObservacion, tpDescOtraObservacion,
                scTomaMxSnEDTA, scRazonNoTomaEDTA, tpDescOtraRazonNoTomaEDTA, bcCodigoMxEDTA, npVolumenEDTA, scObservacionEDTA, tpDescOtraObservacionEDTA,
                scTomaMxSnCit, scRazonNoTomaCit, tpDescOtraRazonNoTomaCit, bcCodigoMxCit, npVolumenCit, scObservacionCit, tpDescOtraObservacionCit, scNumPinchazos
        );
    }
}
