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
public class EncuestaLactanciaMatForm extends AbstractWizardModel {

    int index = 0;
    private String[] catSiNoDes;
    private String[] catExclusivo;
    private String[] catTiemPecho;
    private String[] catFormAlim;
    private String[] catAlimExclu;
    private String[] catOtroAlim;
    private String[] catAlimDisPecho;
    private String[] catAlimDisLeche;
    private String[] catAlimSolido;

    private EstudiosAdapter estudiosAdapter;
    private EncuestaLactanciaMatFormLabels labels;

    public EncuestaLactanciaMatForm(Context context, String pass) {
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
        labels = new EncuestaLactanciaMatFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();

        catSiNoDes = fillCatalog("CHF_CAT_SND");
        catExclusivo = fillCatalog("CHF_CAT_EXCLU");
        catAlimExclu = fillCatalog("CHF_CAT_ALIM_EXC");
        catOtroAlim = fillCatalog("CHF_CAT_OTRA_ALIM");
        catTiemPecho = fillCatalog("CHF_CAT_TIEMPECHO");
        catFormAlim = fillCatalog("CHF_CAT_FORM_ALIM");
        catAlimDisPecho = fillCatalog("CHF_CAT_ALIM_DLP");
        catAlimDisLeche = fillCatalog("CHF_CAT_ALIM_DL");
        catAlimSolido = fillCatalog("CHF_CAT_ALIM_SOL");
        estudiosAdapter.close();

        Page dioPecho = new SingleFixedChoicePage(this,labels.getDioPecho(), "",Constants.WIZARD, true).setChoices(catSiNoDes).setRequired(true);
        Page tiemPecho = new SingleFixedChoicePage(this,labels.getTiemPecho(), "",Constants.WIZARD, false).setChoices(catTiemPecho).setRequired(true);
        Page mesDioPecho = new NumberPage(this, labels.getMesDioPecho(), "", Constants.WIZARD, false).setRangeValidation(true,2,36).setRequired(true);
        Page pechoExc = new SingleFixedChoicePage(this,labels.getPechoExc(), labels.getPechoExcHint(),Constants.WIZARD, false).setChoices(catExclusivo).setRequired(true);
        Page formAlim = new SingleFixedChoicePage(this,labels.getFormAlim(), "",Constants.WIZARD, false).setChoices(catFormAlim).setRequired(true);
        Page pechoExcAntes = new SingleFixedChoicePage(this,labels.getPechoExcAntes(), "",Constants.WIZARD, false).setChoices(catExclusivo).setRequired(true);
        Page tiempPechoExcAntes = new SingleFixedChoicePage(this,labels.getTiempPechoExcAntes(), "",Constants.WIZARD, false).setChoices(catAlimExclu).setRequired(true);
        Page mestPechoExc = new NumberPage(this, labels.getMestPechoExc(), "", Constants.WIZARD, false).setRangeValidation(true,2,36).setRequired(true);
        Page otraAlim = new MultipleFixedChoicePage(this,labels.getOtraAlim(), labels.getOtraAlimHint(), Constants.WIZARD, true).setChoices(catOtroAlim).setRequired(true);
        Page edadLiqDistPecho = new SingleFixedChoicePage(this,labels.getEdadLiqDistPecho(), labels.getEdadLiqDistPechoHint(),Constants.WIZARD, true).setChoices(catAlimDisPecho).setRequired(true);
        Page mesDioLiqDisPecho = new NumberPage(this, labels.getMesDioLiqDisPecho(), "", Constants.WIZARD, false).setRangeValidation(true,0,24).setRequired(true);
        Page edadLiqDistLeche = new SingleFixedChoicePage(this,labels.getEdadLiqDistLeche(), labels.getEdadLiqDistLecheHint(),Constants.WIZARD, true).setChoices(catAlimDisLeche).setRequired(true);
        Page mesDioLiqDisLeche = new NumberPage(this, labels.getMesDioLiqDisLeche(), "", Constants.WIZARD, false).setRequired(true);
        Page edAlimSolidos = new SingleFixedChoicePage(this,labels.getEdAlimSolidos(), labels.getEdAlimSolidosHint(),Constants.WIZARD, true).setChoices(catAlimSolido).setRequired(true);
        Page mesDioAlimSol = new NumberPage(this, labels.getMesDioAlimSol(), "", Constants.WIZARD, false).setRangeValidation(true,0,36).setRequired(true);

        return new PageList(dioPecho,tiemPecho,mesDioPecho,pechoExc,formAlim, pechoExcAntes, tiempPechoExcAntes,
                mestPechoExc,otraAlim,edadLiqDistPecho,mesDioLiqDisPecho,edadLiqDistLeche, mesDioLiqDisLeche,edAlimSolidos,mesDioAlimSol);
    }
}
