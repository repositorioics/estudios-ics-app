package ni.org.ics.estudios.appmovil.entomologia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class CuestionarioHogarForm extends AbstractWizardModel {

    private CuestionarioHogarFormLabels labels;
    private EstudiosAdapter estudiosAdapter;
    int index = 0;
    private String[] catSiNo;
    private String[] catP01;
    private String[] catP03;
    private String[] catP04;
    private String[] catP12;
    private String[] catP15;
    private String[] catP17;
    private String[] catP19;
    private String[] catP20;
    private String[] catP21y22;
    private String[] catP23y28;
    private String[] catP25;
    private String[] catP27;
    private String[] catP30;

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

    public CuestionarioHogarForm(Context context, String pass){
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new CuestionarioHogarFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);

        estudiosAdapter.open();
        catSiNo = fillCatalog("CHF_CAT_SINO");
        catP01 = fillCatalog("ENTO_CAT_P01");
        catP03 = fillCatalog("ENTO_CAT_P03");
        catP04 = fillCatalog("ENTO_CAT_P04");
        catP12 = fillCatalog("ENTO_CAT_P12");
        catP15 = fillCatalog("ENTO_CAT_P15");
        catP17 = fillCatalog("ENTO_CAT_P17");
        catP19 = fillCatalog("ENTO_CAT_P19");
        catP20 = fillCatalog("ENTO_CAT_P20");
        catP21y22 = fillCatalog("ENTO_CAT_P21");
        catP23y28 = fillCatalog("ENTO_CAT_P23");
        catP25 = fillCatalog("ENTO_CAT_P25");
        catP27 = fillCatalog("ENTO_CAT_P27");
        catP30 = fillCatalog("ENTO_CAT_P30");
        estudiosAdapter.close();

        Page quienContesta = new SingleFixedChoicePage(this,labels.getQuienContesta(),"", Constants.WIZARD, true).setChoices(catP01).setRequired(true);
        Page quienContestaOtro = new TextPage(this,labels.getQuienContestaOtro(),"", Constants.WIZARD, true).setRequired(true);
        Page edadContest = new TextPage(this,labels.getEdadContest(),"", Constants.WIZARD, true).setPatternValidation(true, "^[M|F]{1}\\d{2}$").setRequired(true);
        Page escolaridadContesta = new SingleFixedChoicePage(this,labels.getEscolaridadContesta(),"", Constants.WIZARD, true).setChoices(catP03).setRequired(true);
        Page tiempoVivirBarrio = new SingleFixedChoicePage(this,labels.getTiempoVivirBarrio(),"", Constants.WIZARD, true).setChoices(catP04).setRequired(true);
        Page cuantasPersonasViven = new NumberPage(this,labels.getCuantasPersonasViven(),"", Constants.WIZARD, true).setRangeValidation(true, 1, 25).setRequired(true);
        Page edadMujeres = new TextPage(this,labels.getEdadMujeres(),labels.getEdadHint(), Constants.WIZARD, true).setRequired(true);
        Page edadHombres = new TextPage(this,labels.getEdadHombres(),labels.getEdadHint(), Constants.WIZARD, true).setRequired(true);

        Page usaronMosquitero = new SingleFixedChoicePage(this,labels.getUsaronMosquitero(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page quienesUsaronMosquitero = new MultipleFixedChoicePage(this,labels.getQuienesUsaronMosquitero(),"", Constants.WIZARD, true).setRequired(true);
        Page usaronRepelente = new SingleFixedChoicePage(this,labels.getUsaronRepelente(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page quienesUsaronRepelente = new MultipleFixedChoicePage(this,labels.getQuienesUsaronRepelente(),"", Constants.WIZARD, true).setRequired(true);
        Page conoceLarvas = new SingleFixedChoicePage(this,labels.getConoceLarvas(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page alguienVisEliminarLarvas = new SingleFixedChoicePage(this,labels.getAlguienVisEliminarLarvas(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page quienVisEliminarLarvas = new SingleFixedChoicePage(this,labels.getQuienVisEliminarLarvas(),"", Constants.WIZARD, true).setChoices(catP12).setRequired(true);
        Page quienVisEliminarLarvasOtro = new TextPage(this,labels.getQuienVisEliminarLarvasOtro(),"", Constants.WIZARD, true).setRequired(true);
        Page alguienDedicaElimLarvasCasa = new SingleFixedChoicePage(this,labels.getAlguienDedicaElimLarvasCasa(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page quienDedicaElimLarvasCasa = new MultipleFixedChoicePage(this,labels.getQuienDedicaElimLarvasCasa(),"", Constants.WIZARD, true).setRequired(true);
        Page tiempoElimanCridaros = new SingleFixedChoicePage(this,labels.getTiempoElimanCridaros(),"", Constants.WIZARD, true).setChoices(catP15).setRequired(true);
        Page hayBastanteZancudos = new SingleFixedChoicePage(this,labels.getHayBastanteZancudos(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page queFaltaCasaEvitarZancudos = new MultipleFixedChoicePage(this,labels.getQueFaltaCasaEvitarZancudos(),"", Constants.WIZARD, true).setChoices(catP17).setRequired(true);
        Page queFaltaCasaEvitarZancudosOtros = new TextPage(this,labels.getQueFaltaCasaEvitarZancudosOtros(),"", Constants.WIZARD, true).setRequired(true);
        Page gastaronDineroProductos = new SingleFixedChoicePage(this,labels.getGastaronDineroProductos(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page queProductosCompraron = new MultipleFixedChoicePage(this,labels.getQueProductosCompraron(),"", Constants.WIZARD, true).setChoices(catP19).setRequired(true);
        Page queProductosCompraronOtros = new TextPage(this,labels.getQueProductosCompraronOtros(),"", Constants.WIZARD, true).setRequired(true);
        Page cuantoGastaron = new SingleFixedChoicePage(this,labels.getCuantoGastaron(),"", Constants.WIZARD, true).setChoices(catP20).setRequired(true);
        Page ultimaVisitaMinsaBTI = new SingleFixedChoicePage(this,labels.getUltimaVisitaMinsaBTI(),"", Constants.WIZARD, true).setChoices(catP21y22).setRequired(true);
        Page ultimaVezMinsaFumigo = new SingleFixedChoicePage(this,labels.getUltimaVezMinsaFumigo(),"", Constants.WIZARD, true).setChoices(catP21y22).setRequired(true);
        Page riesgoCasaDengue = new SingleFixedChoicePage(this,labels.getRiesgoCasaDengue(),"", Constants.WIZARD, true).setChoices(catP23y28).setRequired(true);
        Page problemasAbastecimiento = new SingleFixedChoicePage(this,labels.getProblemasAbastecimiento(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page cadaCuantoVaAgua = new SingleFixedChoicePage(this,labels.getCadaCuantoVaAgua(),"", Constants.WIZARD, true).setChoices(catP25).setRequired(true);
        Page cadaCuantoVaAguaOtro = new TextPage(this,labels.getCadaCuantoVaAguaOtro(),"", Constants.WIZARD, true).setRequired(true);
        Page horasSinAguaDia = new NumberPage(this,labels.getHorasSinAguaDia(),"", Constants.WIZARD, true).setRangeValidation(true, 0, 24).setRequired(true);
        Page queHacenBasuraHogar = new SingleFixedChoicePage(this,labels.getQueHacenBasuraHogar(),"", Constants.WIZARD, true).setChoices(catP27).setRequired(true);
        Page queHacenBasuraHogarOtro = new TextPage(this,labels.getQueHacenBasuraHogarOtro(),"", Constants.WIZARD, true).setRequired(true);
        Page riesgoBarrioDengue = new SingleFixedChoicePage(this,labels.getRiesgoBarrioDengue(),"", Constants.WIZARD, true).setChoices(catP23y28).setRequired(true);
        Page accionesCriaderosBarrio = new SingleFixedChoicePage(this,labels.getAccionesCriaderosBarrio(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page queAcciones = new MultipleFixedChoicePage(this,labels.getQueAcciones(),"", Constants.WIZARD, true).setChoices(catP30).setRequired(true);
        Page queAccionesOtro = new TextPage(this,labels.getQueAccionesOtro(),"", Constants.WIZARD, true).setRequired(true);
        Page alguienParticipo = new SingleFixedChoicePage(this,labels.getAlguienParticipo(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page quienParticipo = new MultipleFixedChoicePage(this,labels.getQuienParticipo(),"", Constants.WIZARD, true).setRequired(true);
        Page mayorCriaderoBarrio = new TextPage(this,labels.getMayorCriaderoBarrio(),"", Constants.WIZARD, true).setRequired(true);


        return new PageList( quienContesta, quienContestaOtro, edadContest, escolaridadContesta, tiempoVivirBarrio, cuantasPersonasViven, edadMujeres, edadHombres, usaronMosquitero, quienesUsaronMosquitero, usaronRepelente, quienesUsaronRepelente,
                conoceLarvas, alguienVisEliminarLarvas, quienVisEliminarLarvas, quienVisEliminarLarvasOtro, alguienDedicaElimLarvasCasa, quienDedicaElimLarvasCasa, tiempoElimanCridaros,
                hayBastanteZancudos, queFaltaCasaEvitarZancudos, queFaltaCasaEvitarZancudosOtros, gastaronDineroProductos, queProductosCompraron, queProductosCompraronOtros, cuantoGastaron,
                ultimaVisitaMinsaBTI, ultimaVezMinsaFumigo, riesgoCasaDengue, problemasAbastecimiento, cadaCuantoVaAgua, cadaCuantoVaAguaOtro, horasSinAguaDia,
                queHacenBasuraHogar, queHacenBasuraHogarOtro, riesgoBarrioDengue, accionesCriaderosBarrio, queAcciones, queAccionesOtro, alguienParticipo, quienParticipo, mayorCriaderoBarrio);
    }
}
