package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import java.util.List;

import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.NumberPage;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.SelectParticipantPage;
import ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage;
import android.content.Context;

public class PersonaCamaForm extends AbstractWizardModel {
	

	private PersonaCamaFormLabels labels;
	private EstudiosAdapter estudiosAdapter;
	int index = 0;

	
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
	

	
    public PersonaCamaForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new PersonaCamaFormLabels();
    	this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
    	estudiosAdapter.open();
    	String[] catSexo = fillCatalog("CHF_CAT_SEXO");
    	String[] catSiNo = fillCatalog("CHF_CAT_SINO");
    	estudiosAdapter.close();
    	
		Page estaEnEstudio = new SingleFixedChoicePage(this,labels.getEstaEnEstudio(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
		Page participante = new SelectParticipantPage(this,labels.getParticipante(),"", Constants.WIZARD, false).setRequired(true);
		Page edad = new NumberPage(this,labels.getEdad(),"", Constants.WIZARD, false).setRangeValidation(true, 0, 100).setRequired(true);
		Page sexo = new SingleFixedChoicePage(this,labels.getSexo(), "", Constants.WIZARD, true).setChoices(catSexo).setRequired(true);
        return new PageList(estaEnEstudio,participante,edad,sexo);
    }

	public PersonaCamaFormLabels getLabels() {
		return labels;
	}

	public void setLabels(PersonaCamaFormLabels labels) {
		this.labels = labels;
	}
    
}
