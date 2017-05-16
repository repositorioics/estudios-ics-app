package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.List;

/**
 * Created by Miguel Salinas on 5/12/2017.
 * V1.0
 */
public class EncuestaCasaForm extends AbstractWizardModel {
    int index = 0;
    private String[] catSiNo;
    private String[] catDentroFuera;
    private String[] catCompartido;
    private String[] catMaterialParedes;
    private String[] catMaterialPiso;
    private String[] catMaterialTecho;
    private String[] catDiaNoche;
    private String[] catPeriodoCocina;
    public static final String nombreForm = Constants.FORM_NUEVA_ENCUESTA_CASA;
    private EstudiosAdapter estudiosAdapter;
    private EncuestaCasaFormLabels labels;

    public EncuestaCasaForm(Context context, String pass) {
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
        labels = new EncuestaCasaFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = fillCatalog("CAT_SINO");
        catDentroFuera = fillCatalog("CAT_DENTROFUERA");
        catCompartido = fillCatalog("CAT_COMPARTIDO");
        catMaterialParedes = fillCatalog("CAT_MAT_PARED");
        catMaterialPiso = fillCatalog("CAT_MAT_PISO");
        catMaterialTecho = fillCatalog("CAT_MAT_TECHO");
        catDiaNoche = fillCatalog("CAT_FUN_ABANICO");
        catPeriodoCocina = fillCatalog("CAT_PERIOD_COCINA");

        estudiosAdapter.close();

        Page npCuartos = new NumberPage(this, labels.getCuantoCuartos(), "", Constants.WIZARD, true).setRequired(true);
        Page npCuartosDormir = new NumberPage(this, labels.getCuartosDormir(), "", Constants.WIZARD, true).setRequired(true);
        Page npHorasSinAgua = new NumberPage(this, labels.getHorasSinAgua(), labels.getHorasSinAguaHint(), Constants.WIZARD, true).setRequired(true);
        Page scLlaveAgua = new SingleFixedChoicePage(this,labels.getLlaveAgua(), "",Constants.WIZARD, true).setChoices(catDentroFuera).setRequired(true);
        Page scLlaveCompartida = new SingleFixedChoicePage(this,labels.getLlaveCompartida(),labels.getLlaveCompartidaHint(),Constants.WIZARD, false).setChoices(catCompartido).setRequired(true);
        Page scAlmacenaAgua = new SingleFixedChoicePage(this, labels.getAlmacenaAgua(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scEnBarriles = new SingleFixedChoicePage(this, labels.getAlmacenaEnBarriles(), labels.getAlmacenaEnBarrilesHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npNumBarriles = new NumberPage(this, labels.getNumBarriles(), labels.getNumBarrilesHint(), Constants.WIZARD, false).setRequired(true);
        Page scBarrilesTapados = new SingleFixedChoicePage(this,labels.getBarrilesTapados(),labels.getBarrilesTapadosHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scBarrilesAbate = new SingleFixedChoicePage(this,labels.getBarrilesConAbate(),labels.getBarrilesConAbateHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scEnTanques = new SingleFixedChoicePage(this, labels.getAlmacenaEnTanques(), labels.getAlmacenaEnTanquesHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npNumTanques = new NumberPage(this, labels.getNumTanques(), labels.getNumTanquesHint(), Constants.WIZARD, false).setRequired(true);
        Page scTanquesTapados = new SingleFixedChoicePage(this,labels.getTanquesTapados(),labels.getTanquesTapadosHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scTanquesAbate = new SingleFixedChoicePage(this,labels.getTanquesConAbate(),labels.getTanquesConAbateHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scEnPilas = new SingleFixedChoicePage(this, labels.getAlmacenaEnPilas(), labels.getAlmacenaEnPilasHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npNumPilas = new NumberPage(this, labels.getNumPilas(), labels.getNumPilasHint(), Constants.WIZARD, false).setRequired(true);
        Page scPilasTapadas = new SingleFixedChoicePage(this,labels.getPilasTapadas(),labels.getPilasTapadasHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scPilasAbate = new SingleFixedChoicePage(this,labels.getPilasConAbate(),labels.getPilasConAbateHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scEnOtrosRec = new SingleFixedChoicePage(this, labels.getAlmacenaOtrosRecipientes(), labels.getAlmacenaOtrosRecipientesHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page tpDescOtrosRec = new TextPage(this, labels.getOtrosRecipientes(), labels.getOtrosRecipientesHint(), Constants.WIZARD, false).setRequired(true);
        Page npNumOtrosRec = new NumberPage(this, labels.getNumOtrosRecipientes(), labels.getNumOtrosRecipientesHint(), Constants.WIZARD, false).setRequired(true);
        Page scOtrosRecTapados = new SingleFixedChoicePage(this,labels.getOtrosRecipientesTapados(),labels.getOtrosRecipientesTapadosHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scOtrosRecAbate = new SingleFixedChoicePage(this,labels.getOtrosRecipientesConAbate(),labels.getOtrosRecipientesConAbateHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scLavandero = new SingleFixedChoicePage(this,labels.getUbicacionLavandero(),"",Constants.WIZARD, true).setChoices(catDentroFuera).setRequired(true);
        Page mcParedes = new MultipleFixedChoicePage(this,labels.getMaterialParedes(),"",Constants.WIZARD, true).setChoices(catMaterialParedes).setRequired(true);
        Page tpParedesOtroDesc = new TextPage(this, labels.getOtroMaterialParedes(), "", Constants.WIZARD, false).setRequired(true);
        Page mcPiso = new MultipleFixedChoicePage(this,labels.getMaterialPiso(),"",Constants.WIZARD, true).setChoices(catMaterialPiso).setRequired(true);
        Page tpPisoOtroDesc = new TextPage(this, labels.getOtroMaterialPiso(), "", Constants.WIZARD, false).setRequired(true);
        Page scTecho = new SingleFixedChoicePage(this,labels.getMaterialTecho(),"",Constants.WIZARD, true).setChoices(catMaterialTecho).setRequired(true);
        Page tpTechoOtroDesc = new TextPage(this, labels.getOtroMaterialTecho(), "", Constants.WIZARD, false).setRequired(true);
        Page scCasaPropia = new SingleFixedChoicePage(this,labels.getCasaPropia(),"",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scTelevisor = new SingleFixedChoicePage(this, labels.getTieneTelevisor(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npNumTelevisor = new NumberPage(this, labels.getNumTelevisores(), "", Constants.WIZARD, false).setRequired(true);
        Page scAbanico = new SingleFixedChoicePage(this, labels.getTieneAbanico(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npAbanico = new NumberPage(this, labels.getNumAbanicos(), "", Constants.WIZARD, false).setRequired(true);
        Page scRefrigerador = new SingleFixedChoicePage(this, labels.getTieneRefrigerador(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npNumRefrigerador = new NumberPage(this, labels.getNumRefrigeradores(), "", Constants.WIZARD, false).setRequired(true);
        Page scAireAcondicionado = new SingleFixedChoicePage(this, labels.getTienAireAcondicionado(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scAireAcondicionadoFun = new SingleFixedChoicePage(this, labels.getAireAcondicionadoFuncionando(), labels.getAireAcondicionadoFuncionandoHint(), Constants.WIZARD, false).setChoices(catDiaNoche).setRequired(true);
        Page scMoto = new SingleFixedChoicePage(this, labels.getTieneMoto(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scMTCarro = new SingleFixedChoicePage(this, labels.getTieneCarro(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scMTMicrobus = new SingleFixedChoicePage(this, labels.getTienMicrobus(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scMTCamioneta = new SingleFixedChoicePage(this, labels.getTieneCamioneta(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scMTCamion = new SingleFixedChoicePage(this, labels.getTieneCamion(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scMTOtro = new SingleFixedChoicePage(this, labels.getTieneOtroMedioTransAuto(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page tpMTOtroDesc =  new TextPage(this, labels.getOtroMedioTransAuto(), "", Constants.WIZARD, false).setRequired(true);
        Page scCocina = new SingleFixedChoicePage(this, labels.getCocinaConLenia(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scCocinaUbicacion = new SingleFixedChoicePage(this, labels.getUbicacionCocinaLenia(), labels.getUbicacionCocinaLeniaHint(), Constants.WIZARD, false).setChoices(catDentroFuera[0],catDentroFuera[1]).setRequired(true);
        Page scCocinaPeriodicidad = new SingleFixedChoicePage(this, labels.getPeriodicidadCocinaLenia(), "", Constants.WIZARD, false).setChoices(catPeriodoCocina).setRequired(true);
        Page npNumCocinaD = new NumberPage(this, labels.getNumDiarioCocinaLenia(), labels.getNumDiarioCocinaLeniaHint(), Constants.WIZARD, false).setRequired(true);
        Page npNumCocinaS = new NumberPage(this, labels.getNumSemanalCocinaLenia(), labels.getNumSemanalCocinaLeniaHint(), Constants.WIZARD, false).setRequired(true);
        Page npNumCocinaQ = new NumberPage(this, labels.getNumQuincenalCocinaLenia(), labels.getNumQuincenalCocinaLeniaHint(), Constants.WIZARD, false).setRequired(true);
        Page npNumCocinaM = new NumberPage(this, labels.getNumMensualCocinaLenia(), labels.getNumMensualCocinaLeniaHint(), Constants.WIZARD, false).setRequired(true);
        Page scAnimales = new SingleFixedChoicePage(this, labels.getTieneAnimales(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scAnimalesGallinas = new SingleFixedChoicePage(this, labels.getTieneGallinas(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantGallinas = new NumberPage(this, labels.getCantidadGallinas(), "", Constants.WIZARD, false).setRequired(true);
        Page scGallinasDC =  new SingleFixedChoicePage(this, labels.getGallinasDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scAnimalesPatos = new SingleFixedChoicePage(this, labels.getTienePatos(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantPatos = new NumberPage(this, labels.getCantidadPatos(), "", Constants.WIZARD, false).setRequired(true);
        Page scPatosDC = new SingleFixedChoicePage(this, labels.getPatosDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scAnimalesCerdos = new SingleFixedChoicePage(this, labels.getTieneCerdos(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantCerdos = new NumberPage(this, labels.getCantidadCerdos(), "", Constants.WIZARD, false).setRequired(true);
        Page scCerdosDC = new SingleFixedChoicePage(this, labels.getCerdosDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scFuman = new SingleFixedChoicePage(this, labels.getPersonaFumaDentroCasa(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scMadreFuma = new SingleFixedChoicePage(this, labels.getMadreFuma(), labels.getMadreFumaHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantCigarMadre = new NumberPage(this, labels.getCantidadCigarrilosMadre(), labels.getCantidadCigarrilosMadreHint(), Constants.WIZARD, false).setRequired(true);
        Page scPafreFuma = new SingleFixedChoicePage(this, labels.getPadreFuma(), labels.getPadreFumaHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantCigarPadre = new NumberPage(this, labels.getCantidadCigarrillosPadre(), labels.getCantidadCigarrillosPadreHint(), Constants.WIZARD, false).setRequired(true);
        Page scOtrosFuma = new SingleFixedChoicePage(this, labels.getOtrosFuman(), labels.getOtrosFumanHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantOtrosFuma = new NumberPage(this, labels.getCantidadOtrosFuman(), labels.getCantidadOtrosFumanHint(), Constants.WIZARD, false).setRequired(true);
        Page npCantCigarOtrosF = new NumberPage(this, labels.getCantidadCigarrillosOtros(), labels.getCantidadCigarrillosOtrosHint(), Constants.WIZARD, false).setRequired(true);

        return new PageList(
                npCuartos, npCuartosDormir, npHorasSinAgua, scLlaveAgua, scLlaveCompartida, scAlmacenaAgua, scEnBarriles, npNumBarriles, scBarrilesTapados, scBarrilesAbate,
                scEnTanques, npNumTanques, scTanquesTapados, scTanquesAbate, scEnPilas, npNumPilas, scPilasTapadas, scPilasAbate, scEnOtrosRec, tpDescOtrosRec, npNumOtrosRec,
                scOtrosRecTapados, scOtrosRecAbate, scLavandero, mcParedes, tpParedesOtroDesc, mcPiso, tpPisoOtroDesc, scTecho, tpTechoOtroDesc, scCasaPropia, scAbanico, npAbanico,
                scTelevisor, npNumTelevisor, scRefrigerador, npNumRefrigerador, scAireAcondicionado, scAireAcondicionadoFun, scMoto, scMTCarro, scMTMicrobus, scMTCamioneta,
                scMTCamion, scMTOtro, tpMTOtroDesc, scCocina, scCocinaUbicacion, scCocinaPeriodicidad, npNumCocinaD, npNumCocinaS, npNumCocinaQ, npNumCocinaM, scAnimales,
                scAnimalesGallinas, npCantGallinas, scGallinasDC, scAnimalesPatos, npCantPatos, scPatosDC, scAnimalesCerdos, npCantCerdos, scCerdosDC, scFuman, scMadreFuma,
                npCantCigarMadre, scPafreFuma, npCantCigarPadre, scOtrosFuma, npCantOtrosFuma, npCantCigarOtrosF
                        );
    }
}
