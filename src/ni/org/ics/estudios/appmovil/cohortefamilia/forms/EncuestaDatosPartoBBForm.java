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
public class EncuestaDatosPartoBBForm extends AbstractWizardModel {

    int index = 0;
    private String[] catSiNo;
    private String[] catTipoParto;
    private String[] catDocMedico;
    private String[] catSndr;

    private EstudiosAdapter estudiosAdapter;
    private EncuestaDatosPartoBBFormLabels labels;

    public EncuestaDatosPartoBBForm(Context context, String pass) {
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
        labels = new EncuestaDatosPartoBBFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();

        catSiNo = fillCatalog("CHF_CAT_SINO");
        catTipoParto = fillCatalog("CHF_CAT_TIPO_PARTO");
        catDocMedico = fillCatalog("CHF_CAT_DOC_MEDICO");
        catSndr = fillCatalog("CHF_CAT_SNDR");

        estudiosAdapter.close();

        Page scTipoParto = new SingleFixedChoicePage(this,labels.getTipoParto(), "",Constants.WIZARD, true).setChoices(catTipoParto).setRequired(true);
        Page scTiempoEmbSndr = new SingleFixedChoicePage(this,labels.getTiempoEmbSndr(), "",Constants.WIZARD, true).setChoices(catSndr).setRequired(true);
        Page npTiempoEmbSemana = new NumberPage(this, labels.getTiempoEmbSemana(), "", Constants.WIZARD, false).setRequired(true);
        Page scDocMedTiempoEmbSn = new SingleFixedChoicePage(this,labels.getDocMedTiempoEmbSn(), "",Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scDocMedTiempoEmb = new SingleFixedChoicePage(this,labels.getDocMedTiempoEmb(), "",Constants.WIZARD, false).setChoices(catDocMedico).setRequired(true);
        Page tpOtroDocMedTiempoEmb = new TextPage(this, labels.getOtroDocMedTiempoEmb(), labels.getOtroDocMedTiempoEmbHint(), Constants.WIZARD, false).setRequired(true);
        Page dpFum = new DatePage(this, labels.getFum(), "", Constants.WIZARD, false).setRequired(true);
        Page scFumFueraRangoSn = new SingleFixedChoicePage(this,labels.getFumFueraRangoSn(), labels.getFumFueraRangoSnHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page tpFumFueraRangoRazon = new TextPage(this, labels.getFumFueraRangoRazon(), labels.getFumFueraRangoRazonHint(), Constants.WIZARD, false).setRequired(true);
        Page lpReingresarFUM = new LabelPage(this, labels.getReingresarFUM(), "", Constants.ROJO, false);
        Page scDocMedEdadGestSn = new SingleFixedChoicePage(this,labels.getDocMedEdadGestSn(), "",Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npEdadGest = new NumberPage(this, labels.getEdadGest(), labels.getEdadGestHint(), Constants.WIZARD, false).setRequired(true);
        Page scDocMedEdadGest = new SingleFixedChoicePage(this,labels.getDocMedEdadGest(), labels.getDocMedEdadGestHint(), Constants.WIZARD, false).setChoices(catDocMedico).setRequired(true);
        Page tpOtroDocMedEdadGest = new TextPage(this, labels.getOtroDocMedEdadGest(), labels.getOtroDocMedEdadGestHint(), Constants.WIZARD, false).setRequired(true);
        Page scPrematuroSndr = new SingleFixedChoicePage(this,labels.getPrematuroSndr(), "",Constants.WIZARD, true).setChoices(catSndr).setRequired(true);
        Page scPesoBBSndr = new SingleFixedChoicePage(this,labels.getPesoBBSndr(), "",Constants.WIZARD, true).setChoices(catSndr).setRequired(true);
        Page npPesoBB = new NumberPage(this, labels.getPesoBB(), "", Constants.WIZARD, true).setRequired(true);
        Page scDocMedPesoBBSn = new SingleFixedChoicePage(this,labels.getDocMedPesoBBSn(), "",Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scDocMedPesoBB = new SingleFixedChoicePage(this,labels.getDocMedPesoBB(), "",Constants.WIZARD, false).setChoices(catDocMedico).setRequired(true);
        Page tpOtroDocMedPesoBB = new TextPage(this, labels.getOtroDocMedPesoBB(), labels.getOtroDocMedPesoBBHint(), Constants.WIZARD, false).setRequired(true);

        return new PageList(scTipoParto,scTiempoEmbSndr,npTiempoEmbSemana,scDocMedTiempoEmbSn,scDocMedTiempoEmb,tpOtroDocMedTiempoEmb,dpFum,scFumFueraRangoSn,tpFumFueraRangoRazon, lpReingresarFUM, scDocMedEdadGestSn,
                npEdadGest,scDocMedEdadGest,tpOtroDocMedEdadGest,scPrematuroSndr,scPesoBBSndr,npPesoBB,scDocMedPesoBBSn,scDocMedPesoBB,tpOtroDocMedPesoBB);
    }
}
