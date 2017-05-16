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
import android.content.Context;

public class PreTamizajeForm extends AbstractWizardModel {
	
	int index = 0;
	private String[] catSiNo;
	private String[] catRazonNoParticipa;
	private PreTamizajeFormLabels labels;
	private EstudiosAdapter estudiosAdapter;
	
	private Page scAceptaTamizaje;
	private Page bcCodigoChf;
	private Page scRazonNoParticipa;
	private Page lpNoAcepta;

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
    	catRazonNoParticipa = fillCatalog("CHF_CAT_NP");
		estudiosAdapter.close();
		scAceptaTamizaje = new SingleFixedChoicePage(this,labels.getAceptaTamizaje(), labels.getAceptaTamizajeHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
		bcCodigoChf = new BarcodePage(this,labels.getCodigoCHF(),labels.getCodigoCHFHint(),Constants.WIZARD, false).setRequired(true);
		scRazonNoParticipa = new SingleFixedChoicePage(this,labels.getRazonNoParticipa(),labels.getRazonNoParticipaHint(),Constants.WIZARD, false).setChoices(catRazonNoParticipa).setRequired(true);
		lpNoAcepta = new LabelPage(this,labels.getNoAcepta(),"",Constants.ROJO, false).setRequired(false);
        return new PageList(scAceptaTamizaje,bcCodigoChf,scRazonNoParticipa,lpNoAcepta);
    }

	public PreTamizajeFormLabels getLabels() {
		return labels;
	}

	public void setLabels(PreTamizajeFormLabels labels) {
		this.labels = labels;
	}
    
}
