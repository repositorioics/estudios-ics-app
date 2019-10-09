package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import java.util.Date;
import java.util.List;

import ni.org.ics.estudios.appmovil.wizard.model.*;
import org.joda.time.DateMidnight;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
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

		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		DateMidnight dmDesde = dmHasta.minusDays(10);

		Page partContacto = new SelectParticipantPage(this,labels.getPartContacto(),"", Constants.WIZARD, true).setRequired(true);
		Page cantidadDias = new NumberPage(this,labels.getCantidadDias(),labels.getCantidadDiasHint(), Constants.WIZARD, true).setRangeValidation(true,1,5).setRequired(true);
    	Page fechaContacto = new NewDatePage(this,labels.getFechaContacto(), "", Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
		Page tiempoInteraccion = new SingleFixedChoicePage(this,labels.getTiempoInteraccion(),labels.getTiempoInteraccionHint(), Constants.WIZARD, true).setChoices(catTiempoInteraccion).setRequired(true);
		Page tipoInteraccion = new MultipleFixedChoicePage(this,labels.getTipoInteraccion(), labels.getTipoInteraccionHint(), Constants.WIZARD, false).setChoices(catTipoInteraccion).setRequired(true);
//
		Page fechaContacto2 = new NewDatePage(this,labels.getFechaContacto2(), "", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
		Page tiempoInteraccion2 = new SingleFixedChoicePage(this,labels.getTiempoInteraccion2(),labels.getTiempoInteraccionHint(), Constants.WIZARD, false).setChoices(catTiempoInteraccion).setRequired(true);
		Page tipoInteraccion2 = new MultipleFixedChoicePage(this,labels.getTipoInteraccion2(), labels.getTipoInteraccionHint(), Constants.WIZARD, false).setChoices(catTipoInteraccion).setRequired(true);
//
		Page fechaContacto3 = new NewDatePage(this,labels.getFechaContacto3(), "", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
		Page tiempoInteraccion3 = new SingleFixedChoicePage(this,labels.getTiempoInteraccion3(),labels.getTiempoInteraccionHint(), Constants.WIZARD, false).setChoices(catTiempoInteraccion).setRequired(true);
		Page tipoInteraccion3 = new MultipleFixedChoicePage(this,labels.getTipoInteraccion3(), labels.getTipoInteraccionHint(), Constants.WIZARD, false).setChoices(catTipoInteraccion).setRequired(true);
//
		Page fechaContacto4 = new NewDatePage(this,labels.getFechaContacto4(), "", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
		Page tiempoInteraccion4 = new SingleFixedChoicePage(this,labels.getTiempoInteraccion4(),labels.getTiempoInteraccionHint(), Constants.WIZARD, false).setChoices(catTiempoInteraccion).setRequired(true);
		Page tipoInteraccion4 = new MultipleFixedChoicePage(this,labels.getTipoInteraccion4(), labels.getTipoInteraccionHint(), Constants.WIZARD, false).setChoices(catTipoInteraccion).setRequired(true);
//
		Page fechaContacto5 = new NewDatePage(this,labels.getFechaContacto5(), "", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
		Page tiempoInteraccion5 = new SingleFixedChoicePage(this,labels.getTiempoInteraccion5(),labels.getTiempoInteraccionHint(), Constants.WIZARD, false).setChoices(catTiempoInteraccion).setRequired(true);
		Page tipoInteraccion5 = new MultipleFixedChoicePage(this,labels.getTipoInteraccion5(), labels.getTipoInteraccionHint(), Constants.WIZARD, false).setChoices(catTipoInteraccion).setRequired(true);

        return new PageList(partContacto, cantidadDias, fechaContacto,tiempoInteraccion,tipoInteraccion, fechaContacto2,tiempoInteraccion2,tipoInteraccion2, fechaContacto3,tiempoInteraccion3,tipoInteraccion3, fechaContacto4,tiempoInteraccion4,tipoInteraccion4, fechaContacto5,tiempoInteraccion5,tipoInteraccion5);
    }

	public FormularioContactoCasoFormLabels getLabels() {
		return labels;
	}

	public void setLabels(FormularioContactoCasoFormLabels labels) {
		this.labels = labels;
	}
    
}
