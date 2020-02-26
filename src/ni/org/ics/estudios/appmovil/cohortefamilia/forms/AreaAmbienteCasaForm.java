package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import java.util.List;

import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.LabelPage;
import ni.org.ics.estudios.appmovil.wizard.model.NumberPage;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage;
import android.content.Context;

public class AreaAmbienteCasaForm extends AbstractWizardModel {
	

	private AreaAmbienteCasaFormLabels labels;
	private EstudiosAdapter estudiosAdapter;
	int index = 0;
	private String[] catTipo;
	private String[] catConVentana;
	
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

	
    public AreaAmbienteCasaForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new AreaAmbienteCasaFormLabels();
    	this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
    	
    	estudiosAdapter.open();
    	catTipo = fillCatalog("CHF_CAT_TIPO_AREA");
    	catConVentana = fillCatalog("CHF_CAT_SINO");
    	estudiosAdapter.close();
    	
		Page tipo = new SingleFixedChoicePage(this,labels.getTipo(),"", Constants.WIZARD, true).setChoices(catTipo).setRequired(true);
		Page cuarto = new NumberPage(this,labels.getNumeroCuarto(),labels.getNumeroCuartoHint(), Constants.WIZARD, false).setRangeValidation(true, 1, 20).setRequired(true);
		Page largo = new NumberPage(this,labels.getLargo(),"", Constants.WIZARD, true).setRangeValidation(true, 0, 20).setRequired(true);
		Page ancho = new NumberPage(this,labels.getAncho(),"", Constants.WIZARD, true).setRangeValidation(true, 0, 20).setRequired(true);
		Page totalM2 = new LabelPage(this,labels.getTotalM2(),"", Constants.WIZARD, true).setRequired(true);
		Page numVentanas = new NumberPage(this,labels.getNumVentanas(),"", Constants.WIZARD, false).setRangeValidation(true, 0, 20).setRequired(true);
		Page conVentana = new SingleFixedChoicePage(this,labels.getConVentana(),"", Constants.WIZARD, false).setChoices(catConVentana).setRequired(true);	
        return new PageList(tipo, cuarto, largo,ancho,totalM2,numVentanas,conVentana);
    }

	public AreaAmbienteCasaFormLabels getLabels() {
		return labels;
	}

	public void setLabels(AreaAmbienteCasaFormLabels labels) {
		this.labels = labels;
	}
    
}
