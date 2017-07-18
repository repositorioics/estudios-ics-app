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
public class MuestraRespiratoriaForm extends AbstractWizardModel {

    int index = 0;
    private String[] catSiNo;
    private String[] catRazon;
    private String[] catObservacion;
    private String[] catTipoMx;

    private MuestrasFormLabels labels;
    private EstudiosAdapter estudiosAdapter;

    public MuestraRespiratoriaForm(Context context, String pass) {
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
        catRazon = fillCatalog("CHF_CAT_RAZON_NO_MX_RESP");
        catObservacion = fillCatalog("CHF_CAT_OBSERV_MX");
        catTipoMx = fillCatalog("CHF_CAT_TIP0_MX_RESP");

        estudiosAdapter.close();

        Page scTomaMxSn = new SingleFixedChoicePage(this, labels.getTomaMxSn(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scRazonNoToma = new SingleFixedChoicePage(this, labels.getRazonNoToma(), "", Constants.WIZARD, false).setChoices(catRazon).setRequired(true);
        Page tpDescOtraRazonNoToma = new TextPage(this, labels.getDescOtraRazonNoToma(), labels.getDescOtraRazonNoTomaHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx = new BarcodePage(this, labels.getCodigoMx(), "", Constants.WIZARD, false).setRequired(true);
        Page scTipoMx = new SingleFixedChoicePage(this, labels.getTipoMuestra(), "", Constants.WIZARD, false).setChoices(catTipoMx).setRequired(true);
        Page npVolumen = new NumberPage(this, labels.getVolumen(), labels.getVolumenHint(), Constants.WIZARD, false).setRangeValidation(true, 0, 12).setRequired(true);
        Page scObservacion = new SingleFixedChoicePage(this, labels.getObservacion(), "", Constants.WIZARD, false).setChoices(catObservacion).setRequired(true);
        Page tpDescOtraObservacion = new TextPage(this, labels.getDescOtraObservacion(), labels.getDescOtraObservacionHint(), Constants.WIZARD, false).setRequired(true);
        return new PageList(scTomaMxSn,scRazonNoToma,tpDescOtraRazonNoToma, bcCodigoMx, scTipoMx, npVolumen, scObservacion, tpDescOtraObservacion);
    }
}
