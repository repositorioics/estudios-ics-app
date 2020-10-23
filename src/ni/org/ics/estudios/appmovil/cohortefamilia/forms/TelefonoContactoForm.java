package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import java.util.List;

import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.SelectParticipantPage;
import ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage;
import ni.org.ics.estudios.appmovil.wizard.model.TextPage;
import android.content.Context;

public class TelefonoContactoForm extends AbstractWizardModel {
	

	private TelefonoContactoFormLabels labels;
	private EstudiosAdapter estudiosAdapter;
	int index = 0;
	private String[] catTipo;
	private String[] catOperadora;
	
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

	
    public TelefonoContactoForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new TelefonoContactoFormLabels();
    	this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
    	
    	estudiosAdapter.open();
    	catTipo = fillCatalog("CAT_TIPO_TEL");
    	catOperadora = fillCatalog("CAT_OPER_TEL");
    	estudiosAdapter.close();
    	
		Page tipo = new SingleFixedChoicePage(this,labels.getTipo(),"", Constants.WIZARD, true).setChoices(catTipo).setRequired(true);
		Page operadora = new SingleFixedChoicePage(this,labels.getOperadora(),"", Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);	
		Page numero = new TextPage(this,labels.getNumero(),"", Constants.WIZARD, true).setPatternValidation(true, "^$|^[3|8|5|7|2]{1}\\d{7}$").setRequired(true);
		Page participante = new SelectParticipantPage(this,labels.getParticipante(),"", Constants.WIZARD, true).setRequired(false);	
        return new PageList(tipo,operadora,numero,participante);
    }

	public TelefonoContactoFormLabels getLabels() {
		return labels;
	}

	public void setLabels(TelefonoContactoFormLabels labels) {
		this.labels = labels;
	}
    
}
