package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.List;

/**
 * Created by Miguel Salinas on 5/16/2017.
 * V1.0
 */
public class MuestraPaxgeneForm extends AbstractWizardModel {

    int index = 0;
    private String[] catSiNo;
    private String[] catRazon;
    private String[] catObservacion;
    private String[] catPinchazos;

    private MuestrasFormLabels labels;
    private EstudiosAdapter estudiosAdapter;

    public MuestraPaxgeneForm(Context context, String pass) {
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

        Page lpInicio = new LabelPage(this, labels.getVolumenPaxgene(), "", Constants.ROJO, true).setRequired(false);
        Page scTomaMxSn = new SingleFixedChoicePage(this, labels.getTomaMxSn(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scRazonNoToma = new SingleFixedChoicePage(this, labels.getRazonNoToma(), "", Constants.WIZARD, false).setChoices(catRazon).setRequired(true);
        Page tpDescOtraRazonNoToma = new TextPage(this, labels.getDescOtraRazonNoToma(), "", Constants.WIZARD, false).setRequired(false);
        Page bcCodigoMx = new BarcodePage(this, labels.getCodigoMx(), "", Constants.WIZARD, false).setRangeValidation(true,1,15000).setRequired(true);
        //Page tpHora = new TextPage(this, labels.getHora(), labels.getHoraHint(), Constants.WIZARD, false).setRequired(true);
        Page npVolumen = new NumberPage(this, labels.getVolumen(), labels.getVolumenHint(), Constants.WIZARD, false).setRequired(true);
        Page scObservacion = new SingleFixedChoicePage(this, labels.getObservacion(), "", Constants.WIZARD, false).setChoices(catObservacion).setRequired(true);
        Page tpDescOtraObservacion = new TextPage(this, labels.getDescOtraObservacion(), "", Constants.WIZARD, false).setRequired(true);
        Page scNumPinchazos = new SingleFixedChoicePage(this, labels.getNumPinchazos(), "", Constants.WIZARD, false).setChoices(catPinchazos).setRequired(true);
        Page lpHoraInicioPax = new LabelPage(this, labels.getHoraInicioPax(), "", Constants.WIZARD, false).setRequired(true);
        //Page lpHoraFinPax = new LabelPage(this, labels.getHoraFinPax(), "", Constants.WIZARD, false).setRequired(true);

        return new PageList(lpInicio, scTomaMxSn,scRazonNoToma,tpDescOtraRazonNoToma, bcCodigoMx, npVolumen, scObservacion, tpDescOtraObservacion, scNumPinchazos, lpHoraInicioPax);
    }
}
