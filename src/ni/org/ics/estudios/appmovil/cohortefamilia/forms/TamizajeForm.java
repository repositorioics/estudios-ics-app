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
import ni.org.ics.estudios.appmovil.wizard.model.BarcodePage;
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
	private Page bcCodigoChf;
	private Page mcCriteriosInclusion;
	/*private Page scDondeAsisteProblemasSalud;
	private Page scAsisteCSSF;
	private Page tpOtroCentroSalud;
	private Page tpPuestoSalud;
	private Page scSiEnfermaSoloAsistirCSSF;
	private Page scElegible;*/

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
	
    public TamizajeForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new TamizajeFormLabels();
    	this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
    	estudiosAdapter.open();
    	catSiNo = fillCatalog("CAT_SINO");
    	catSexo = fillCatalog("CAT_SEXO");
    	catRazonNoParticipa = fillCatalog("CAT_RAZON_NP");
		estudiosAdapter.close();
		scSexo = new SingleFixedChoicePage(this,labels.getSexo(), labels.getSexoHint(), Constants.WIZARD, true).setChoices(catSexo).setRequired(true);
		DateMidnight dmDesde = DateMidnight.parse("01/01/1900", DateTimeFormat.forPattern("dd/MM/yyyy"));
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		dpFechaNac = new DatePage(this,labels.getFechaNacimiento(), labels.getFechaNacimientoHint(), Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
		scAceptaTamizaje = new SingleFixedChoicePage(this,labels.getAceptaTamizajePersona(), labels.getAceptaTamizajePersonaHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
		bcCodigoChf = new BarcodePage(this,labels.getAceptaParteA(),labels.getAceptaParteAHint(),Constants.WIZARD, false).setRequired(true);
		scRazonNoParticipa = new SingleFixedChoicePage(this,labels.getRazonNoParticipaPersona(), labels.getRazonNoParticipaPersonaHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipa).setRequired(true);
		mcCriteriosInclusion = new MultipleFixedChoicePage(this,labels.getCriteriosInclusion(), labels.getCriteriosInclusionHint(), Constants.WIZARD, false).setChoices("67","68").setRequired(false);
		//scDondeAsisteProblemasSalud = new SingleFixedChoicePage(this,labels.getDondeAsisteProblemasSalud(), labels.getDondeAsisteProblemasSaludHint(), Constants.WIZARD, true).setChoices("0").setRequired(true);
		return new PageList(scSexo,dpFechaNac,scAceptaTamizaje,bcCodigoChf,scRazonNoParticipa,mcCriteriosInclusion);
    }

	public TamizajeFormLabels getLabels() {
		return labels;
	}

	public void setLabels(TamizajeFormLabels labels) {
		this.labels = labels;
	}
    
}
