package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by Miguel Salinas on 9/13/2017.
 * V1.0
 */
public class VisitaFinalCasoForm extends AbstractWizardModel {

    private VisitaFinalCasoFormLabels labels;
    private EstudiosAdapter estudiosAdapter;
    int index = 0;
    private String[] catSiNo;
    private String[] catTratamiento;

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

    public VisitaFinalCasoForm(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new VisitaFinalCasoFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);

        estudiosAdapter.open();
        catSiNo = fillCatalog("CHF_CAT_SINO");
        catTratamiento = fillCatalog("CHF_CAT_TRATAMIENTO");
        estudiosAdapter.close();

        DateMidnight dmDesde = DateMidnight.parse("01/01/1900", DateTimeFormat.forPattern("dd/MM/yyyy"));
        DateMidnight dmHasta = new DateMidnight(new Date().getTime());

        Page enfermo = new SingleFixedChoicePage(this,labels.getEnfermo(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page consTerreno = new SingleFixedChoicePage(this,labels.getConsTerreno(),"", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page referidoCs = new SingleFixedChoicePage(this,labels.getReferidoCs(),"", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page tratamiento = new MultipleFixedChoicePage(this,labels.getTratamiento(),"", Constants.WIZARD, false).setChoices(catTratamiento).setRequired(true);
        Page cualAntibiotico = new TextPage(this,labels.getCualAntibiotico(),"", Constants.WIZARD, false).setRequired(true);
        Page sintResp = new SingleFixedChoicePage(this,labels.getSintResp(),labels.getSintRespHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page fiebre = new SingleFixedChoicePage(this,labels.getFiebre(),"", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page fif = new NewDatePage(this,labels.getFif(),"", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
        Page fff = new NewDatePage(this,labels.getFff(),"", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);

        Page tos = new SingleFixedChoicePage(this,labels.getTos(),"", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page fitos = new NewDatePage(this,labels.getFitos(),"", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
        Page fftos = new NewDatePage(this,labels.getFftos(),"", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);

        Page dolorGarganta = new SingleFixedChoicePage(this,labels.getDolorGarganta(),"", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page figg = new NewDatePage(this,labels.getFigg(),"", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
        Page ffgg = new NewDatePage(this,labels.getFfgg(),"", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);

        Page secrecionNasal = new SingleFixedChoicePage(this,labels.getSecrecionNasal(),"", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page fisn = new NewDatePage(this,labels.getFisn(),"", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
        Page ffsn = new NewDatePage(this,labels.getFfsn(),"", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);

        Page fechaVisita = new LabelPage(this,labels.getFechaVisita(),"", Constants.WIZARD, true).setRequired(false);

        return new PageList(enfermo, consTerreno, referidoCs, tratamiento, cualAntibiotico, sintResp, fiebre, fif, fff, tos, fitos, fftos, dolorGarganta, figg, ffgg, secrecionNasal, fisn, ffsn, fechaVisita);
    }
}
