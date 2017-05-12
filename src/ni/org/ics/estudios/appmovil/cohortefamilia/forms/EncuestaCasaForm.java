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
        return new PageList(
                new NumberPage(this, labels.getCuantoCuartos(), "", Constants.WIZARD).setRequired(true),
                new NumberPage(this, labels.getCuartosDormir(), "", Constants.WIZARD).setRequired(true),
                new NumberPage(this, labels.getHorasSinAgua(), labels.getHorasSinAguaHint(), Constants.WIZARD).setRequired(true),
                new BranchPage(this, labels.getLlaveAgua(), "", Constants.WIZARD)
                        .addBranch(catDentroFuera[0],
                                new SingleFixedChoicePage(this,labels.getLlaveCompartida(),labels.getLlaveCompartidaHint(),Constants.WIZARD)
                                        .setChoices(catCompartido)
                                        .setRequired(true))
                        .addBranch(catDentroFuera[1])
                        .addBranch(catDentroFuera[2]),
                new BranchPage(this, labels.getAlmacenaAgua(), "", Constants.WIZARD)
                        .addBranch(catSiNo[0],
                                new BranchPage(this, labels.getAlmacenaEnBarriles(), labels.getAlmacenaEnBarrilesHint(), Constants.WIZARD) //barriles
                                        .addBranch(catSiNo[0],
                                                new NumberPage(this, labels.getNumBarriles(), labels.getNumBarrilesHint(), Constants.WIZARD).setRequired(true),
                                                new SingleFixedChoicePage(this,labels.getBarrilesTapados(),labels.getBarrilesTapadosHint(),Constants.WIZARD)
                                                        .setChoices(catSiNo)
                                                        .setRequired(true),
                                                new SingleFixedChoicePage(this,labels.getBarrilesConAbate(),labels.getBarrilesConAbateHint(),Constants.WIZARD)
                                                        .setChoices(catSiNo)
                                                        .setRequired(true)
                                        ) // fin SI barriles
                                        .addBranch(catSiNo[1]),
                                new BranchPage(this, labels.getAlmacenaEnTanques(), labels.getAlmacenaEnTanquesHint(), Constants.WIZARD) //tanques
                                        .addBranch(catSiNo[0],
                                                new NumberPage(this, labels.getNumTanques(), labels.getNumTanquesHint(), Constants.WIZARD).setRequired(true),
                                                new SingleFixedChoicePage(this,labels.getTanquesTapados(),labels.getTanquesTapadosHint(),Constants.WIZARD)
                                                        .setChoices(catSiNo)
                                                        .setRequired(true),
                                                new SingleFixedChoicePage(this,labels.getTanquesConAbate(),labels.getTanquesConAbateHint(),Constants.WIZARD)
                                                        .setChoices(catSiNo)
                                                        .setRequired(true)
                                        )//fin SI tanques
                                        .addBranch(catSiNo[1]),
                                new BranchPage(this, labels.getAlmacenaEnPilas(), labels.getAlmacenaEnPilasHint(), Constants.WIZARD) //pilas
                                        .addBranch(catSiNo[0],
                                                new NumberPage(this, labels.getNumPilas(), labels.getNumPilasHint(), Constants.WIZARD).setRequired(true),
                                                new SingleFixedChoicePage(this,labels.getPilasTapadas(),labels.getPilasTapadasHint(),Constants.WIZARD)
                                                        .setChoices(catSiNo)
                                                        .setRequired(true),
                                                new SingleFixedChoicePage(this,labels.getPilasConAbate(),labels.getPilasConAbateHint(),Constants.WIZARD)
                                                        .setChoices(catSiNo)
                                                        .setRequired(true)
                                        )//fin SI pilas
                                        .addBranch(catSiNo[1]),
                                new BranchPage(this, labels.getAlmacenaOtrosRecipientes(), labels.getAlmacenaOtrosRecipientesHint(), Constants.WIZARD) //otros recipientes
                                        .addBranch(catSiNo[0],
                                                new TextPage(this, labels.getOtrosRecipientes(), labels.getOtrosRecipientesHint(), Constants.WIZARD).setRequired(true),
                                                new NumberPage(this, labels.getNumOtrosRecipientes(), labels.getNumOtrosRecipientesHint(), Constants.WIZARD).setRequired(true),
                                                new SingleFixedChoicePage(this,labels.getOtrosRecipientesTapados(),labels.getOtrosRecipientesTapadosHint(),Constants.WIZARD)
                                                        .setChoices(catSiNo)
                                                        .setRequired(true),
                                                new SingleFixedChoicePage(this,labels.getOtrosRecipientesConAbate(),labels.getOtrosRecipientesConAbateHint(),Constants.WIZARD)
                                                        .setChoices(catSiNo)
                                                        .setRequired(true)
                                        )//fin SI otros recipientes
                                        .addBranch(catSiNo[1])
                        ) //fin si almacena agua
                        .addBranch(catSiNo[1])
                        .setRequired(true),
                new SingleFixedChoicePage(this,labels.getUbicacionLavandero(),"",Constants.WIZARD)
                        .setChoices(catDentroFuera)
                        .setRequired(true),
                new MultipleFixedChoicePage(this,labels.getMaterialParedes(),"",Constants.WIZARD)
                        .setChoices(catMaterialParedes)
                        .setRequired(true),
                new TextPage(this, labels.getOtroMaterialParedes(), "", Constants.WIZARD).setRequired(true),
                new MultipleFixedChoicePage(this,labels.getMaterialPiso(),"",Constants.WIZARD)
                        .setChoices(catMaterialPiso)
                        .setRequired(true),
                new TextPage(this, labels.getOtroMaterialPiso(), "", Constants.WIZARD).setRequired(true),
                new SingleFixedChoicePage(this,labels.getMaterialTecho(),"",Constants.WIZARD)
                        .setChoices(catMaterialTecho)
                        .setRequired(true),
                new TextPage(this, labels.getOtroMaterialTecho(), "", Constants.WIZARD).setRequired(true),
                new SingleFixedChoicePage(this,labels.getCasaPropia(),"",Constants.WIZARD)
                        .setChoices(catSiNo)
                        .setRequired(true),
                /*new BranchPage(this, labels.getTieneTelevisor(), "", Constants.WIZARD) //televisor
                        .addBranch(catSiNo[0],
                                new NumberPage(this, labels.getNumTelevisores(), "", Constants.WIZARD).setRequired(true)
                        ) // fin SI televisor
                        .addBranch(catSiNo[1]),
                new BranchPage(this, labels.getTieneAbanico(), "", Constants.WIZARD) //abanicos
                        .addBranch(catSiNo[0],
                                new NumberPage(this, labels.getNumAbanicos(), "", Constants.WIZARD).setRequired(true)
                        ) // fin SI abanicos
                        .addBranch(catSiNo[1]),
                new BranchPage(this, labels.getTieneRefrigerador(), "", Constants.WIZARD) //Refrigerador
                        .addBranch(catSiNo[0],
                                new NumberPage(this, labels.getNumRefrigeradores(), "", Constants.WIZARD).setRequired(true)
                                ) // fin SI Refrigerador
                        .addBranch(catSiNo[1]),
                new BranchPage(this, labels.getTienAireAcondicionado(), "", Constants.WIZARD) //aire acondicionado
                        .addBranch(catSiNo[0],
                                new SingleFixedChoicePage(this, labels.getAireAcondicionadoFuncionando(), labels.getAireAcondicionadoFuncionandoHint(), Constants.WIZARD)
                                        .setChoices(catDiaNoche)
                                        .setRequired(true)
                                ) // fin SI aire acondicionado
                        .addBranch(catSiNo[1])
                        .setRequired(true),*/
                new SingleFixedChoicePage(this, labels.getTieneMoto(), "", Constants.WIZARD)
                        .setChoices(catSiNo)
                        .setRequired(true),
                new SingleFixedChoicePage(this, labels.getTieneCarro(), "", Constants.WIZARD)
                        .setChoices(catSiNo)
                        .setRequired(true),
                new SingleFixedChoicePage(this, labels.getTienMicrobus(), "", Constants.WIZARD)
                        .setChoices(catSiNo)
                        .setRequired(true),
                new SingleFixedChoicePage(this, labels.getTieneCamioneta(), "", Constants.WIZARD)
                        .setChoices(catSiNo)
                        .setRequired(true),
                new SingleFixedChoicePage(this, labels.getTieneCamion(), "", Constants.WIZARD)
                        .setChoices(catSiNo)
                        .setRequired(true),
                new BranchPage(this, labels.getTieneOtroMedioTransAuto(), "", Constants.WIZARD) //otro medio transporte
                        .addBranch(catSiNo[0],
                                new TextPage(this, labels.getOtroMedioTransAuto(), "", Constants.WIZARD).setRequired(true)
                        ) // fin SI otro medio transporte
                        .addBranch(catSiNo[1])
                        .setRequired(true),
                new BranchPage(this, labels.getCocinaConLenia(), "", Constants.WIZARD) //cocina con leña
                        .addBranch(catSiNo[0],
                                new SingleFixedChoicePage(this, labels.getUbicacionCocinaLenia(), labels.getUbicacionCocinaLeniaHint(), Constants.WIZARD)
                                        .setChoices(catDentroFuera[0],catDentroFuera[1])
                                        .setRequired(true),
                                new BranchPage(this, labels.getPeriodicidadCocinaLenia(), "", Constants.WIZARD)
                                        .addBranch(catPeriodoCocina[0], // cocina diario
                                                new NumberPage(this, labels.getNumDiarioCocinaLenia(), labels.getNumDiarioCocinaLeniaHint(), Constants.WIZARD).setRequired(true)
                                        )
                                        .addBranch(catPeriodoCocina[1], // cocina semanal
                                                new NumberPage(this, labels.getNumSemanalCocinaLenia(), labels.getNumSemanalCocinaLeniaHint(), Constants.WIZARD).setRequired(true)
                                        )
                                        .addBranch(catPeriodoCocina[2], // cocina quincenal
                                                new NumberPage(this, labels.getNumQuincenalCocinaLenia(), labels.getNumQuincenalCocinaLeniaHint(), Constants.WIZARD).setRequired(true)
                                        )
                                        .addBranch(catPeriodoCocina[3], // cocina mensual
                                                new NumberPage(this, labels.getNumMensualCocinaLenia(), labels.getNumMensualCocinaLeniaHint(), Constants.WIZARD).setRequired(true)
                                        )
                        ) // fin SI cocina con lena
                        .addBranch(catSiNo[1]),
                new BranchPage(this, labels.getTieneAnimales(), "", Constants.WIZARD) //tiene animales
                        .addBranch(catSiNo[0],
                                new BranchPage(this, labels.getTieneGallinas(), "", Constants.WIZARD) //tiene Pollos, gallinas, y gallos
                                        .addBranch(catSiNo[0],
                                                new NumberPage(this, labels.getCantidadGallinas(), "", Constants.WIZARD).setRequired(true),
                                                new SingleFixedChoicePage(this, labels.getGallinasDentroCasa(), "", Constants.WIZARD)
                                                        .setChoices(catSiNo)
                                                        .setRequired(true)
                                        )// fin SI tiene Gallinas
                                        .addBranch(catSiNo[1]),
                                new BranchPage(this, labels.getTienePatos(), "", Constants.WIZARD) //tiene patos
                                        .addBranch(catSiNo[0],
                                                new NumberPage(this, labels.getCantidadPatos(), "", Constants.WIZARD).setRequired(true),
                                                new SingleFixedChoicePage(this, labels.getPatosDentroCasa(), "", Constants.WIZARD)
                                                        .setChoices(catSiNo)
                                                        .setRequired(true)
                                        )// fin SI tiene patos
                                        .addBranch(catSiNo[1]),
                                new BranchPage(this, labels.getTieneCerdos(), "", Constants.WIZARD) //tiene cerdos
                                        .addBranch(catSiNo[0],
                                                new NumberPage(this, labels.getCantidadCerdos(), "", Constants.WIZARD).setRequired(true),
                                                new SingleFixedChoicePage(this, labels.getCerdosDentroCasa(), "", Constants.WIZARD)
                                                        .setChoices(catSiNo)
                                                        .setRequired(true)
                                        )// fin SI tiene cerdos
                                        .addBranch(catSiNo[1])
                        ) // fin SI tiene animales
                        .addBranch(catSiNo[1]),
                new BranchPage(this, labels.getPersonaFumaDentroCasa(), "", Constants.WIZARD) //persona que no pertenece al estudio fuma dentro de la casa
                        .addBranch(catSiNo[0],
                                new BranchPage(this, labels.getMadreFuma(), "", Constants.WIZARD) //madre fuma
                                        .addBranch(catSiNo[0],
                                                new NumberPage(this, labels.getCantidadCigarrilosMadre(), labels.getCantidadCigarrilosMadreHint(), Constants.WIZARD)
                                                        .setRequired(true)
                                        )// fin SI madre fuma
                                        .addBranch(catSiNo[1]),
                                new BranchPage(this, labels.getPadreFuma(), "", Constants.WIZARD) //padre fuma
                                        .addBranch(catSiNo[0],
                                                new NumberPage(this, labels.getCantidadCigarrillosPadre(), labels.getCantidadCigarrillosPadreHint(), Constants.WIZARD)
                                                        .setRequired(true)
                                        )// fin SI padre fuma
                                        .addBranch(catSiNo[1]),
                                new BranchPage(this, labels.getOtrosFuman(), labels.getOtrosFumanHint(), Constants.WIZARD) //otros fuma
                                        .addBranch(catSiNo[0],
                                                new NumberPage(this, labels.getCantidadOtrosFuman(), "", Constants.WIZARD)
                                                        .setRequired(true),
                                                new NumberPage(this, labels.getCantidadCigarrillosOtros(), labels.getCantidadCigarrillosOtrosHint(), Constants.WIZARD)
                                                        .setRequired(true)
                                        )// fin SI Otros fuma
                                        .addBranch(catSiNo[1])
                        ) // fin SI otro medio transporte
                        .addBranch(catSiNo[1])

        );
    }
}
