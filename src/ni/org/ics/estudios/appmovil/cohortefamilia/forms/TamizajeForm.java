package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage;

import java.util.List;

/**
 * Created by Miguel Salinas on 5/11/2017.
 * V1.0
 */
public class TamizajeForm extends AbstractWizardModel {

    int index = 0;
    private String[] catSiNo;
    private TamizajeFormLabels labels;
    public static final String nombreForm = Constants.FORM_NUEVO_TAMIZAJE_PERS;
    private EstudiosAdapter estudiosAdapter;

    public TamizajeForm(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new TamizajeFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        List<MessageResource> mCatSiNo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CAT_SINO'", CatalogosDBConstants.order);
        catSiNo = new String[mCatSiNo.size()];
        index = 0;
        for (MessageResource message: mCatSiNo){
            catSiNo[index] = message.getSpanish();
            index++;
        }
        estudiosAdapter.close();
        return new PageList(
                new SingleFixedChoicePage(this,labels.getAreaCobertura(),null,Constants.WIZARD)
                        .setChoices(catSiNo)
                        .setRequired(true),
                new SingleFixedChoicePage(this,labels.getNinoMenor12Anios(),null,Constants.WIZARD)
                        .setChoices(catSiNo)
                        .setRequired(true),
                new SingleFixedChoicePage(this,labels.getIntencionPermanecerArea(),null,Constants.WIZARD)
                        .setChoices(catSiNo)
                        .setRequired(true),
                new SingleFixedChoicePage(this,labels.getTieneTarjetaVacunaOIdentificacion(),null,Constants.WIZARD)
                        .setChoices(catSiNo)
                        .setRequired(true),
                new SingleFixedChoicePage(this,labels.getEnfermedadAgudaCronica(),null,Constants.WIZARD)
                        .setChoices(catSiNo)
                        .setRequired(true)
        );
    }

    public TamizajeFormLabels getLabels() {
        return labels;
    }

    public void setLabels(TamizajeFormLabels labels) {
        this.labels = labels;
    }
}
