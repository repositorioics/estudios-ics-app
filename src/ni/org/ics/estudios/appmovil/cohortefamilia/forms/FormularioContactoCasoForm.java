package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage;
import ni.org.ics.estudios.appmovil.wizard.model.NewDatePage;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.SelectParticipantPage;
import ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage;
import android.content.Context;

public class FormularioContactoCasoForm extends AbstractWizardModel {
	

	private FormularioContactoCasoFormLabels labels;
	private EstudiosAdapter estudiosAdapter;
	int index = 0;
	private String[] catTipoInteraccion;
	private String[] catTiempoInteraccion;
	
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

	
    public FormularioContactoCasoForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new FormularioContactoCasoFormLabels();
    	this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
    	
    	estudiosAdapter.open();
    	catTipoInteraccion = fillCatalog("CHF_CAT_TIPO_INTERACCION");
    	catTiempoInteraccion = fillCatalog("CHF_CAT_TIEMPO_INTERACCION");
    	estudiosAdapter.close();
    	
    	Page partContacto = new SelectParticipantPage(this,labels.getPartContacto(),"", Constants.WIZARD, true).setRequired(true);
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		DateMidnight dmDesde = dmHasta.minusDays(10);
    	Page fechaContacto = new NewDatePage(this,labels.getFechaContacto(), "", Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
		Page tiempoInteraccion = new SingleFixedChoicePage(this,labels.getTiempoInteraccion(),labels.getTiempoInteraccionHint(), Constants.WIZARD, true).setChoices(catTiempoInteraccion).setRequired(true);
		Page tipoInteraccion = new MultipleFixedChoicePage(this,labels.getTipoInteraccion(), labels.getTipoInteraccionHint(), Constants.WIZARD, false).setChoices(catTipoInteraccion).setRequired(true);
			
        return new PageList(partContacto,fechaContacto,tiempoInteraccion,tipoInteraccion);
    }

	public FormularioContactoCasoFormLabels getLabels() {
		return labels;
	}

	public void setLabels(FormularioContactoCasoFormLabels labels) {
		this.labels = labels;
	}
    
}
