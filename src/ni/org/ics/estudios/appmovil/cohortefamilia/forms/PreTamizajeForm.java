package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import java.util.List;

import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.BarcodePage;
import ni.org.ics.estudios.appmovil.wizard.model.BranchPage;
import ni.org.ics.estudios.appmovil.wizard.model.LabelPage;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage;
import android.content.Context;

public class PreTamizajeForm extends AbstractWizardModel {
	
	int index = 0;
	private String[] catSiNo;
	private String[] catRazonNoParticipa;
	private PreTamizajeFormLabels labels;
	public static final String nombreForm = Constants.FORM_NUEVO_TAMIZAJE_CASA;
	private EstudiosAdapter estudiosAdapter;
	
    public PreTamizajeForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new PreTamizajeFormLabels();
    	this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
    	estudiosAdapter.open();
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
        return new PageList(
        		new BranchPage(this, labels.getAceptaTamizaje(), labels.getAceptaTamizajeHint(), Constants.WIZARD)
                .addBranch(catSiNo[0],
                		new BarcodePage(this,labels.getCodigoCHF(),labels.getCodigoCHFHint(),Constants.WIZARD)
			                .setRequired(true))
                .addBranch(catSiNo[1],
		        		new SingleFixedChoicePage(this,labels.getRazonNoParticipa(),labels.getRazonNoParticipaHint(),Constants.WIZARD)
		                	.setChoices(catRazonNoParticipa)
		                	.setRequired(true),
		                new LabelPage(this,labels.getNoAcepta(),"",Constants.ROJO)
                			.setRequired(false))
        );
    }

	public PreTamizajeFormLabels getLabels() {
		return labels;
	}

	public void setLabels(PreTamizajeFormLabels labels) {
		this.labels = labels;
	}
    
    
}
