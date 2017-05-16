package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.DatePage;
import ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage;
import android.content.Context;

public class TamizajeForm extends AbstractWizardModel {
	
	int index = 0;
	private String[] catSexo;
	private String[] catSiNo;
	private String[] catRazonNoParticipa;
	private TamizajeFormLabels labels;
	private EstudiosAdapter estudiosAdapter;
	
	private Page scSexo;
	private Page dpFechaNac;
	private Page scAceptaTamizaje;
	private Page scRazonNoParticipa;
	private Page mcCriteriosInclusion;
	private Page scDondeAsisteProblemasSalud;
	private Page scAsisteCSSF;
	private Page tpOtroCentroSalud;
	private Page tpPuestoSalud;
	private Page scSiEnfermaSoloAsistirCSSF;
	private Page scElegible;
		
    public TamizajeForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new TamizajeFormLabels();
    	this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
    	estudiosAdapter.open();
		List<MessageResource> mCatSexo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CAT_SEXO'", CatalogosDBConstants.order);
		catSexo = new String[mCatSexo.size()];
		index = 0;
		for (MessageResource message: mCatSexo){
			catSexo[index] = message.getSpanish();
			index++;
		}
		List<MessageResource> mCatSiNo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CAT_SINO'", CatalogosDBConstants.order);
		catSiNo = new String[mCatSiNo.size()];
		index = 0;
		for (MessageResource message: mCatSiNo){
			catSiNo[index] = message.getSpanish();
			index++;
		}
		List<MessageResource> mCatRazonNoParticipa = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CAT_RAZON_NP'", CatalogosDBConstants.order);
		catRazonNoParticipa = new String[mCatRazonNoParticipa.size()];
		index = 0;
		for (MessageResource message: mCatRazonNoParticipa){
			catRazonNoParticipa[index] = message.getSpanish();
			index++;
		}
		estudiosAdapter.close();
		scSexo = new SingleFixedChoicePage(this,labels.getSexo(), labels.getSexoHint(), Constants.WIZARD, true).setChoices(catSexo).setRequired(true);
		DateMidnight dmDesde = DateMidnight.parse("01/01/1900", DateTimeFormat.forPattern("dd/MM/yyyy"));
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		dpFechaNac = new DatePage(this,labels.getFechaNacimiento(), labels.getFechaNacimientoHint(), Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
		scAceptaTamizaje = new SingleFixedChoicePage(this,labels.getAceptaTamizajePersona(), labels.getAceptaTamizajePersonaHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
		scRazonNoParticipa = new SingleFixedChoicePage(this,labels.getRazonNoParticipaPersona(), labels.getRazonNoParticipaPersonaHint(), Constants.WIZARD, true).setChoices(catRazonNoParticipa).setRequired(true);
		mcCriteriosInclusion = new MultipleFixedChoicePage(this,labels.getCriteriosInclusion(), labels.getCriteriosInclusionHint(), Constants.WIZARD, true).setChoices(catSexo).setRequired(false);
		scDondeAsisteProblemasSalud = new SingleFixedChoicePage(this,labels.getDondeAsisteProblemasSalud(), labels.getDondeAsisteProblemasSaludHint(), Constants.WIZARD, true).setChoices(catRazonNoParticipa).setRequired(true);
		return new PageList(scSexo,dpFechaNac,scAceptaTamizaje,scRazonNoParticipa,mcCriteriosInclusion);
    }

	public TamizajeFormLabels getLabels() {
		return labels;
	}

	public void setLabels(TamizajeFormLabels labels) {
		this.labels = labels;
	}
    
}
