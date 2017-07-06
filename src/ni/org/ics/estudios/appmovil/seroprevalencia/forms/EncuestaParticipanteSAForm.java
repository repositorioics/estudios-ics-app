package ni.org.ics.estudios.appmovil.seroprevalencia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import org.joda.time.DateMidnight;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Miguel Salinas on 5/26/2017.
 * V1.0
 */
public class EncuestaParticipanteSAForm  extends AbstractWizardModel {

    int index = 0;
    private String[] catSiNo;
    private String[] catZika;
    private String[] catTransmision;
    private String[] catSintomas;
    private String[] catSintomasZika;
    private String[] catLugares;
    private EstudiosAdapter estudiosAdapter;
    private EncuestaParticipanteSAFormLabels labels;

    public EncuestaParticipanteSAForm(Context context, String pass) {
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

        labels = new EncuestaParticipanteSAFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = fillCatalog("CHF_CAT_SINO");
        catZika = fillCatalog("SA_CAT_DEF_ZIKA");
        catTransmision = fillCatalog("SA_CAT_TRA_ZIKA");
        catSintomas = fillCatalog("SA_CAT_SINTOMAS");
        catSintomasZika = fillCatalog("SA_CAT_SINT_ZIKA");
        catLugares = fillCatalog("SA_CAT_LUG_LARVA");
        estudiosAdapter.close();

        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(new Date());
        int anioInicio = calendarToday.get(Calendar.YEAR)-2;
        int anioFin = calendarToday.get(Calendar.YEAR);

        DateMidnight dmHasta = new DateMidnight(calendarToday.getTime());
        calendarToday.add(Calendar.MONTH, -18);
        DateMidnight dmDesde = new DateMidnight(calendarToday.getTime());

        Page scEscuchadoZikaSn = new SingleFixedChoicePage(this,labels.getEscuchadoZikaSn(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scQueEsSika = new MultipleFixedChoicePage(this,labels.getQueEsSika(), "",Constants.WIZARD, true).setChoices(catZika).setRequired(true);
        Page tpOtroQueEsSika = new TextPage(this,labels.getOtroQueEsSika(), labels.getOtroQueEsSikaHint(),Constants.WIZARD, false).setRequired(true);
        Page scTransmiteZika = new MultipleFixedChoicePage(this,labels.getTransmiteZika(), "",Constants.WIZARD, true).setChoices(catTransmision).setRequired(true);
        Page tpOtroTransmiteZika = new TextPage(this,labels.getOtroTransmiteZika(), labels.getOtroTransmiteZikaHint(),Constants.WIZARD, false).setRequired(true);
        Page scSintomas = new MultipleFixedChoicePage(this,labels.getSintomas(), "",Constants.WIZARD, true).setChoices(catSintomas).setRequired(true);
        Page scTenidoZikaSn = new SingleFixedChoicePage(this,labels.getTenidoZikaSn(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npFechaZika = new NumberPage(this, labels.getFechaZika(), "", Constants.WIZARD, false).setRangeValidation(true, anioInicio, anioFin).setRequired(true);
        Page scSintomasZika = new MultipleFixedChoicePage(this,labels.getSintomasZika(), "",Constants.WIZARD, false).setChoices(catSintomasZika).setRequired(true);
        Page scZikaConfirmadoMedico = new SingleFixedChoicePage(this,labels.getZikaConfirmadoMedico(), "",Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scTenidoDengueSn = new SingleFixedChoicePage(this,labels.getTenidoDengueSn(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npFechaDengue = new NumberPage(this, labels.getFechaDengue(), "", Constants.WIZARD, false).setRangeValidation(true, anioInicio, anioFin).setRequired(true);
        Page scDengueConfirmadoMedico = new SingleFixedChoicePage(this,labels.getDengueConfirmadoMedico(), "",Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scTenidoChikSn = new SingleFixedChoicePage(this,labels.getTenidoChikSn(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npFechaChik = new NumberPage(this, labels.getFechaChik(), "", Constants.WIZARD, false).setRangeValidation(true, anioInicio, anioFin).setRequired(true);
        Page scChikConfirmadoMedico = new SingleFixedChoicePage(this,labels.getChikConfirmadoMedico(), "",Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scVacunaFiebreAmarillaSn = new SingleFixedChoicePage(this,labels.getVacunaFiebreAmarillaSn(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npFechaVacunaFiebreAmar = new NumberPage(this, labels.getFechaVacunaFiebreAmar(), "", Constants.WIZARD, false).setRequired(true);
        Page scTransfusionSangreSn = new SingleFixedChoicePage(this,labels.getTransfusionSangreSn(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page dpFechaTransfusionSangre = new NewDatePage(this, labels.getFechaTransfusionSangre(), "", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
        Page scUsaRepelentes = new SingleFixedChoicePage(this,labels.getUsaRepelentes(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scConoceLarvas = new SingleFixedChoicePage(this,labels.getConoceLarvas(), "",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scLugaresLarvas = new MultipleFixedChoicePage(this,labels.getLugaresLarvas(), "",Constants.WIZARD, false).setChoices(catLugares).setRequired(true);
        Page tpOtrosLugaresLarvas = new TextPage(this, labels.getOtrosLugaresLarvas(), labels.getOtrosLugaresLarvasHint(), Constants.WIZARD, false).setRequired(true);
        Page scTenidoHijos = new SingleFixedChoicePage(this,labels.getTenidoHijos(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scUsaPlanificacionFam = new SingleFixedChoicePage(this,labels.getUsaPlanificacionFam(), "",Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scUsaCondon = new SingleFixedChoicePage(this,labels.getUsaCondon(), "",Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scUsaOtroMetodo = new SingleFixedChoicePage(this,labels.getUsaOtroMetodo(), "",Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        return new PageList(scEscuchadoZikaSn,scQueEsSika,tpOtroQueEsSika,scTransmiteZika,tpOtroTransmiteZika,scSintomas,scTenidoZikaSn,npFechaZika,scSintomasZika,scZikaConfirmadoMedico,scTenidoDengueSn,npFechaDengue,scDengueConfirmadoMedico,
                scTenidoChikSn,npFechaChik,scChikConfirmadoMedico,scVacunaFiebreAmarillaSn,npFechaVacunaFiebreAmar,scTransfusionSangreSn,dpFechaTransfusionSangre,scUsaRepelentes,scConoceLarvas,scLugaresLarvas,tpOtrosLugaresLarvas,scTenidoHijos,
                scUsaPlanificacionFam,scUsaCondon,scUsaOtroMetodo);
    }
}
