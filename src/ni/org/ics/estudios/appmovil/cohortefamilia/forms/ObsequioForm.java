package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.List;

public class ObsequioForm extends AbstractWizardModel {


	private ObsequioFormLabels labels;
	int index = 0;
    private String[] catSiNo;
    private EstudiosAdapter estudiosAdapter;

    public ObsequioForm(Context context, String pass) {
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
    	labels = new ObsequioFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = fillCatalog("CHF_CAT_SINO");
        Page obsequioSN = new SingleFixedChoicePage(this, labels.getObsequioSN(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
		Page persona = new SelectParticipantPage(this,labels.getPersonaRecibe(),"", Constants.WIZARD, false).setRequired(true);
        Page observaciones = new TextPage(this,labels.getObservaciones(),"", Constants.WIZARD, true).setRequired(false);
        return new PageList(obsequioSN, persona, observaciones);
    }

	public ObsequioFormLabels getLabels() {
		return labels;
	}

	public void setLabels(ObsequioFormLabels labels) {
		this.labels = labels;
	}
    
}
