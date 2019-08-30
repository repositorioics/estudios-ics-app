package ni.org.ics.estudios.appmovil.influenzauo1.forms;

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

public class VisitaCasoUO1Form extends AbstractWizardModel {

    private VisitaCasoUO1FormLabels labels;
    private EstudiosAdapter estudiosAdapter;
    int index = 0;
    private String[] catSiNo;
    private String[] catVisita;
    private String[] catVisitaFallida;

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

    public VisitaCasoUO1Form(Context context, String pass){
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new VisitaCasoUO1FormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);

        estudiosAdapter.open();
        catSiNo = fillCatalog("CHF_CAT_SINO");
        catVisita = fillCatalog("UO1_CAT_VISITA");
        catVisitaFallida = fillCatalog("CHF_CAT_VISITA_NO_P");
        estudiosAdapter.close();

        DateMidnight dmDesde = DateMidnight.parse("01/01/1900", DateTimeFormat.forPattern("dd/MM/yyyy"));
        DateMidnight dmHasta = new DateMidnight(new Date().getTime());

        Page visita = new SingleFixedChoicePage(this,labels.getVisita(),"", Constants.WIZARD, true).setChoices(catVisita).setRequired(true);
        Page fechaVisita = new LabelPage(this,labels.getFechaVisita(), "", Constants.WIZARD, true).setRequired(true);
        Page visitaExitosa = new SingleFixedChoicePage(this,labels.getVisitaExitosa(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page visitaFallida = new SingleFixedChoicePage(this,labels.getRazonVisitaFallida(),"", Constants.WIZARD, false).setChoices(catVisitaFallida).setRequired(true);
        Page otraRazon = new TextPage(this,labels.getOtraRazon(),"", Constants.WIZARD, false).setRequired(true);
        Page vacuna = new SingleFixedChoicePage(this,labels.getVacunaFlu3Semanas(),"", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page fechaVacuna = new NewDatePage(this,labels.getFechaVacuna(), "", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);

        return new PageList(visita, fechaVisita, visitaExitosa, visitaFallida, otraRazon, vacuna, fechaVacuna);
    }
}
