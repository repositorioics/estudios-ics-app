package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.NumberPage;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import android.content.Context;

public class HabitacionForm extends AbstractWizardModel {
	

	private HabitacionFormLabels labels;
	

	
    public HabitacionForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new HabitacionFormLabels();
		
		Page codigoHabitacion = new NumberPage(this,labels.getCodigoHabitacion(),"", Constants.WIZARD, true).setRangeValidation(true, 1, 10).setRequired(true);
		Page cantidadCamas = new NumberPage(this,labels.getCantidadCamas(),"", Constants.WIZARD, true).setRangeValidation(true, 1, 5).setRequired(true);

		
        return new PageList(codigoHabitacion,cantidadCamas);
    }

	public HabitacionFormLabels getLabels() {
		return labels;
	}

	public void setLabels(HabitacionFormLabels labels) {
		this.labels = labels;
	}
    
}
