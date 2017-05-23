package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.TextPage;
import android.content.Context;

public class CamaForm extends AbstractWizardModel {
	

	private CamaFormLabels labels;
	

	
    public CamaForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new CamaFormLabels();
		
		Page descCama = new TextPage(this,labels.getDescCama(),"", Constants.WIZARD, true).setRequired(true);

		
        return new PageList(descCama);
    }

	public CamaFormLabels getLabels() {
		return labels;
	}

	public void setLabels(CamaFormLabels labels) {
		this.labels = labels;
	}
    
}
