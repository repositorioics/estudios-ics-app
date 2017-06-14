package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import java.util.List;

import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.BarcodePage;
import ni.org.ics.estudios.appmovil.wizard.model.LabelPage;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage;
import ni.org.ics.estudios.appmovil.wizard.model.TextPage;
import android.content.Context;

public class PreTamizajeForm extends AbstractWizardModel {
	
	int index = 0;
	private String[] catSiNo;
	private String[] catRazonVisitaNoExitosa;
	private String[] catRazonNoAceptaTamizajeCasa;
	private PreTamizajeFormLabels labels;
	private EstudiosAdapter estudiosAdapter;
	
	private Page visitaExitosa;
	private Page razonVisitaNoExitosa;
    private Page otraRazonVisitaNoExitosa;
	private Page aceptaTamizajeCasa;
	private Page razonNoAceptaTamizajeCasa;
    private Page otraRazonNoAceptaTamizajeCasa;
	private Page razonNoAceptaTamizajeCasaLabel;
	private Page codigoCHF;
	private Page mismoJefe;
	private Page nombre1JefeFamilia;
	private Page nombre2JefeFamilia;
	private Page apellido1JefeFamilia;
	private Page apellido2JefeFamilia;
	private Page finTamizajeLabel;

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
	
    public PreTamizajeForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new PreTamizajeFormLabels();
    	this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
    	estudiosAdapter.open();
    	catSiNo = fillCatalog("CHF_CAT_SINO");
    	catRazonVisitaNoExitosa = fillCatalog("CHF_CAT_NV");
    	catRazonNoAceptaTamizajeCasa = fillCatalog("CHF_CAT_NPT");
		estudiosAdapter.close();
		
		visitaExitosa = new SingleFixedChoicePage(this,labels.getVisitaExitosa(),labels.getVisitaExitosaHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
		razonVisitaNoExitosa = new SingleFixedChoicePage(this,labels.getRazonVisitaNoExitosa(),labels.getRazonVisitaNoExitosaHint(), Constants.WIZARD, false).setChoices(catRazonVisitaNoExitosa).setRequired(true);
        otraRazonVisitaNoExitosa = new TextPage(this,labels.getOtraRazonVisitaNoExitosa(),labels.getOtraRazonVisitaNoExitosaHint(), Constants.WIZARD, false).setRequired(true);
		aceptaTamizajeCasa = new SingleFixedChoicePage(this,labels.getAceptaTamizajeCasa(),labels.getAceptaTamizajeCasaHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
		razonNoAceptaTamizajeCasa = new SingleFixedChoicePage(this,labels.getRazonNoAceptaTamizajeCasa(),labels.getRazonNoAceptaTamizajeCasaHint(), Constants.WIZARD, false).setChoices(catRazonNoAceptaTamizajeCasa).setRequired(true);
        otraRazonNoAceptaTamizajeCasa = new TextPage(this,labels.getOtraRazonNoAceptaTamizajeCasa(),labels.getOtraRazonNoAceptaTamizajeCasaHint(), Constants.WIZARD, false).setRequired(true);
        razonNoAceptaTamizajeCasaLabel = new LabelPage(this,labels.getRazonNoAceptaTamizajeCasaLabel(),"", Constants.ROJO, false).setRequired(false);
		
		codigoCHF = new BarcodePage(this,labels.getCodigoCHF(),labels.getMismoJefeHint(), Constants.WIZARD, false).setRequired(true);
		mismoJefe = new SingleFixedChoicePage(this,labels.getMismoJefe(),labels.getMismoJefeHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
		nombre1JefeFamilia = new TextPage(this,labels.getNombre1JefeFamilia(),labels.getJefeFamiliaCHFHint(), Constants.WIZARD, false).setRequired(true);
		nombre2JefeFamilia = new TextPage(this,labels.getNombre2JefeFamilia(),labels.getJefeFamiliaCHFHint(), Constants.WIZARD, false).setRequired(false);
		apellido1JefeFamilia = new TextPage(this,labels.getApellido1JefeFamilia(),labels.getJefeFamiliaCHFHint(), Constants.WIZARD, false).setRequired(true);
		apellido2JefeFamilia = new TextPage(this,labels.getApellido2JefeFamilia(),labels.getJefeFamiliaCHFHint(), Constants.WIZARD, false).setRequired(false);
		finTamizajeLabel = new LabelPage(this,labels.getFinTamizajeLabel(),"", Constants.WIZARD, false).setRequired(false);
		
        return new PageList(visitaExitosa,razonVisitaNoExitosa,otraRazonVisitaNoExitosa,aceptaTamizajeCasa,razonNoAceptaTamizajeCasa,otraRazonNoAceptaTamizajeCasa,razonNoAceptaTamizajeCasaLabel,
        		codigoCHF,mismoJefe,nombre1JefeFamilia,nombre2JefeFamilia,apellido1JefeFamilia,apellido2JefeFamilia,finTamizajeLabel);
    }

	public PreTamizajeFormLabels getLabels() {
		return labels;
	}

	public void setLabels(PreTamizajeFormLabels labels) {
		this.labels = labels;
	}
    
}
