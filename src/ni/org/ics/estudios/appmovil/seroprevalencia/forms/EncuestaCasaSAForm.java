package ni.org.ics.estudios.appmovil.seroprevalencia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.forms.EncuestaCasaFormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Miguel Salinas on 5/26/2017.
 * V1.0
 */
public class EncuestaCasaSAForm extends AbstractWizardModel {

    int index = 0;
    private String[] catSiNo;
    private String[] catFumiga;
    private String[] catRelacionFam;
    private EstudiosAdapter estudiosAdapter;
    private EncuestaCasaSAFormLabels labels;

    public EncuestaCasaSAForm(Context context, String pass) {
        super(context,pass);
    }

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

    @Override
    protected PageList onNewRootPageList() {

        labels = new EncuestaCasaSAFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = fillCatalog("CHF_CAT_SINO");
        catFumiga = fillCatalog("SA_CAT_FUMIGA");
        catRelacionFam = fillCatalog("SA_CAT_RELFAM");
        estudiosAdapter.close();

        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(new Date());
        int anioFin = calendarToday.get(Calendar.YEAR);

        Page scSedazoPuertasVentanas = new SingleFixedChoicePage(this,labels.getSedazoPuertasVentanas(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scCompraProdEvitarZancudos = new SingleFixedChoicePage(this,labels.getCompraProdEvitarZancudos(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scTienePatioJardin = new SingleFixedChoicePage(this,labels.getTienePatioJardin(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scUtilizaAbate = new SingleFixedChoicePage(this,labels.getUtilizaAbate(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scFumiga = new SingleFixedChoicePage(this,labels.getFumiga(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scCadaCuantoFumiga = new SingleFixedChoicePage(this,labels.getCadaCuantoFumiga(), "",Constants.WIZARD, false).setChoices(catFumiga).setRequired(true);

        Page scMiembroFamConZikaSn = new SingleFixedChoicePage(this,labels.getMiembroFamConZikaSn(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npCantMiembrosZika = new NumberPage(this, labels.getCantMiembrosZika(), "", Constants.WIZARD, false).setRangeValidation(true, 0, 20).setRequired(true);
        Page npFechaDxZika = new NumberPage(this, labels.getFechaDxZika(), "", Constants.WIZARD, false).setRangeValidation(true, 1900, anioFin).setRequired(true);
        Page scRelacionFamZika = new MultipleFixedChoicePage(this,labels.getRelacionFamZika(), "",Constants.WIZARD, false).setChoices(catRelacionFam).setRequired(true);
        Page tpOtraRelacionFamZika = new TextPage(this, labels.getOtraRelacionFamZika(), "", Constants.WIZARD, false).setRequired(true);

        Page scMiembroFamConDengueSn = new SingleFixedChoicePage(this,labels.getMiembroFamConDengueSn(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npCantMiembrosDengue = new NumberPage(this, labels.getCantMiembrosDengue(), "", Constants.WIZARD, false).setRangeValidation(true, 0, 20).setRequired(true);
        Page npFechaDxDengue = new NumberPage(this, labels.getFechaDxDengue(), "", Constants.WIZARD, false).setRangeValidation(true, 1900, anioFin).setRequired(true);
        Page scRelacionFamDengue = new MultipleFixedChoicePage(this,labels.getRelacionFamDengue(), "",Constants.WIZARD, false).setChoices(catRelacionFam).setRequired(true);
        Page tpOtraRelacionFamDengue = new TextPage(this, labels.getOtraRelacionFamDengue(), "", Constants.WIZARD, false).setRequired(true);

        Page scMiembroFamConChikSn = new SingleFixedChoicePage(this,labels.getMiembroFamConChikSn(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npCantMiembrosChik = new NumberPage(this, labels.getCantMiembrosChik(), "", Constants.WIZARD, false).setRangeValidation(true, 0, 20).setRequired(true);
        Page npFechaDxChik = new NumberPage(this, labels.getFechaDxChik(), "", Constants.WIZARD, false).setRangeValidation(true, 1900, anioFin).setRequired(true);
        Page scRelacionFamChik = new MultipleFixedChoicePage(this,labels.getRelacionFamChik(), "",Constants.WIZARD, false).setChoices(catRelacionFam).setRequired(true);
        Page tpOtraRelacionFamChik = new TextPage(this, labels.getOtraRelacionFamChik(), "", Constants.WIZARD, false).setRequired(true);

        return new PageList(scSedazoPuertasVentanas, scCompraProdEvitarZancudos,scTienePatioJardin,scUtilizaAbate,scFumiga,scCadaCuantoFumiga,scMiembroFamConZikaSn,npCantMiembrosZika,npFechaDxZika,scRelacionFamZika,
                tpOtraRelacionFamZika,scMiembroFamConDengueSn,npCantMiembrosDengue,npFechaDxDengue,scRelacionFamDengue,tpOtraRelacionFamDengue,scMiembroFamConChikSn,npCantMiembrosChik,npFechaDxChik,scRelacionFamChik,tpOtraRelacionFamChik);
    }
}
