package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.TextPage;
import android.content.Context;

public class PreTamizajeForm extends AbstractWizardModel {
	int index = 0;
	private PreTamizajeFormLabels labels;
	public static final String nombreForm = Constants.FORM_NUEVO_TAMIZAJE_CASA;
    public PreTamizajeForm(Context context) {    	
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new PreTamizajeFormLabels();	
        return new PageList(
        		new TextPage(this, labels.getAceptaTamizaje(), labels.getAceptaTamizajeHint(),Constants.WIZARD)
        			.setPatternValidation(true, ".{2,50}").setRequired(false)
        );
    }

	public PreTamizajeFormLabels getLabels() {
		return labels;
	}

	public void setLabels(PreTamizajeFormLabels labels) {
		this.labels = labels;
	}
    
    
}
