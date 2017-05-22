package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.NewDatePage;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.SelectParticipantPage;
import ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage;
import android.content.Context;

public class TestForm extends AbstractWizardModel {
	
	private TestFormLabels labels;
	
    public TestForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	
    	labels = new TestFormLabels();
    	DateMidnight dmDesde = DateMidnight.parse("04/05/2017", DateTimeFormat.forPattern("dd/MM/yyyy"));
		DateMidnight dmHasta = DateMidnight.parse("15/05/2017", DateTimeFormat.forPattern("dd/MM/yyyy"));
		Page tp1 = new NewDatePage(this,labels.getTp1(),"",Constants.WIZARD,true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
		Page tp2 = new SingleFixedChoicePage(this,labels.getTp2(),"",Constants.WIZARD,true).setChoices("Si","No").setRequired(true);
		Page tp3 = new SelectParticipantPage(this,labels.getTp3(),"",Constants.WIZARD,false).setRequired(true);
		Page tp4 = new SingleFixedChoicePage(this,labels.getTp4(),"",Constants.WIZARD,false).setChoices("Si","No").setRequired(true);
		Page tp5 = new SingleFixedChoicePage(this,labels.getTp5(),"",Constants.WIZARD,false).setChoices("Si","No").setRequired(true);
		Page tp6 = new NewDatePage(this,labels.getTp6(),"",Constants.WIZARD,false).setRequired(true);
		return new PageList(tp1,tp2,tp3,tp4,tp5,tp6);
    }

	public TestFormLabels getLabels() {
		return labels;
	}

	public void setLabels(TestFormLabels labels) {
		this.labels = labels;
	}
    
}
