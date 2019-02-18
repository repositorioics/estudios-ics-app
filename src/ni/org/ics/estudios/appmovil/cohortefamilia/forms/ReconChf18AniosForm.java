package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.List;

/**
 * Created by Miguel Salinas on 04/02/2019.
 * V1.0
 */
public class ReconChf18AniosForm extends AbstractWizardModel {

    int index = 0;
    private ReconChf18AniosFormLabels labels;
    private EstudiosAdapter estudiosAdapter;

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

    public ReconChf18AniosForm(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {

        labels = new ReconChf18AniosFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext, mPass, false, false);

        estudiosAdapter.open();
        String[] catSiNo = fillCatalog("CHF_CAT_SINO");
        String[] catRazonNoParticipaPersona = fillCatalog("CHF_CAT_NPP");
        String[] catVerificaTutor = fillCatalog("CP_CAT_VERIFTUTOR");
        String[] catCriteriosInclusion = fillCatalog("CHF_CAT_CI");
        String[] catDondeAsisteProblemasSalud = fillCatalog("CHF_CAT_DONDEASISTE");
        String[] catPuestoSalud = fillCatalog("CHF_CAT_PUESTO");
        estudiosAdapter.close();

        Page aceptaTamizajePersona = new SingleFixedChoicePage(this,labels.getAceptaTamizajePersona(), labels.getAceptaTamizajePersonaHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page razonNoParticipaPersona = new SingleFixedChoicePage(this,labels.getRazonNoParticipaPersona(), labels.getRazonNoParticipaPersonaHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoParticipaPersona = new TextPage(this,labels.getOtraRazonNoParticipaPersona(),labels.getOtraRazonNoParticipaPersonaHint(),Constants.WIZARD,false).setRequired(true);
        Page criteriosInclusion = new MultipleFixedChoicePage(this,labels.getCriteriosInclusion(), labels.getCriteriosInclusionHint(), Constants.WIZARD, false).setChoices(catCriteriosInclusion).setRequired(false);
        Page enfermedad = new SingleFixedChoicePage(this,labels.getEnfermedad(), labels.getEnfermedadHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page dondeAsisteProblemasSalud = new SingleFixedChoicePage(this,labels.getDondeAsisteProblemasSalud(), labels.getDondeAsisteProblemasSaludHint(), Constants.WIZARD, false).setChoices(catDondeAsisteProblemasSalud).setRequired(true);
        Page otroCentroSalud = new TextPage(this,labels.getOtroCentroSalud(),labels.getOtroCentroSaludHint(),Constants.WIZARD,false).setRequired(true);
        Page puestoSalud = new SingleFixedChoicePage(this,labels.getPuestoSalud(), labels.getPuestoSaludHint(), Constants.WIZARD, false).setChoices(catPuestoSalud).setRequired(true);
        Page aceptaAtenderCentro = new SingleFixedChoicePage(this,labels.getAceptaAtenderCentro(), labels.getAceptaAtenderCentroHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page esElegible = new SingleFixedChoicePage(this,labels.getEsElegible(), labels.getEsElegibleHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page aceptaParticipar = new SingleFixedChoicePage(this,labels.getAceptaParticipar(), labels.getAceptaParticiparHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaParticipar = new SingleFixedChoicePage(this,labels.getRazonNoAceptaParticipar(), labels.getRazonNoAceptaParticiparHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaParticipar = new TextPage(this,labels.getOtraRazonNoAceptaParticipar(),labels.getOtraRazonNoAceptaParticiparHint(),Constants.WIZARD,false).setRequired(true);

        Page nombre1 = new TextPage(this,labels.getNombre1(),labels.getNombre1Hint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2 = new TextPage(this,labels.getNombre2(),labels.getNombre1Hint(),Constants.WIZARD,false).setRequired(false);
        Page apellido1 = new TextPage(this,labels.getApellido1(),labels.getApellido1Hint(),Constants.WIZARD,false).setRequired(true);
        Page apellido2 = new TextPage(this,labels.getApellido2(),labels.getApellido1Hint(),Constants.WIZARD,false).setRequired(false);

        Page participanteOTutorAlfabeto = new SingleFixedChoicePage(this,labels.getParticipanteOTutorAlfabeto(), labels.getParticipanteOTutorAlfabetoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page testigoPresente = new SingleFixedChoicePage(this,labels.getTestigoPresente(), labels.getTestigoPresenteHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page nombre1Testigo = new TextPage(this,labels.getNombre1Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2Testigo = new TextPage(this,labels.getNombre2Testigo(),labels.getNombre2Testigo(),Constants.WIZARD,false).setRequired(false);
        Page apellido1Testigo = new TextPage(this,labels.getApellido1Testigo(),labels.getApellido1Testigo(),Constants.WIZARD,false).setRequired(true);
        Page apellido2Testigo = new TextPage(this,labels.getApellido2Testigo(),labels.getApellido2Testigo(),Constants.WIZARD,false).setRequired(false);

        Page aceptaParteA = new SingleFixedChoicePage(this,labels.getAceptaParteA(), labels.getAceptaParteAHint(), Constants.WIZARD, false).setChoices("Si").setRequired(true);
        Page motivoRechazoParteA = new SingleFixedChoicePage(this,labels.getMotivoRechazoParteA(), labels.getMotivoRechazoParteAHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page aceptaContactoFuturo = new SingleFixedChoicePage(this,labels.getAceptaContactoFuturo(), labels.getAceptaContactoFuturoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page aceptaParteB = new SingleFixedChoicePage(this,labels.getAceptaParteB(), labels.getAceptaParteBHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page aceptaParteC = new SingleFixedChoicePage(this,labels.getAceptaParteC(), labels.getAceptaParteCHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page verifTutor = new MultipleFixedChoicePage(this,labels.getVerifTutor(), "", Constants.WIZARD, false).setChoices(catVerificaTutor).setRequired(true);

        Page finReconLabel = new LabelPage(this,labels.getFinReconLabel(),"", Constants.WIZARD, false).setRequired(false);

        return new PageList(aceptaTamizajePersona, razonNoParticipaPersona, otraRazonNoParticipaPersona, criteriosInclusion, enfermedad, dondeAsisteProblemasSalud, otroCentroSalud, puestoSalud, aceptaAtenderCentro, esElegible,
                aceptaParticipar, razonNoAceptaParticipar, otraRazonNoAceptaParticipar, nombre1, nombre2, apellido1, apellido2, participanteOTutorAlfabeto,testigoPresente,nombre1Testigo,nombre2Testigo,apellido1Testigo,apellido2Testigo,
                aceptaParteA, motivoRechazoParteA, aceptaContactoFuturo, aceptaParteB, aceptaParteC, verifTutor, finReconLabel);
    }

    public ReconChf18AniosFormLabels getLabels() {
        return labels;
    }

    public void setLabels(ReconChf18AniosFormLabels labels) {
        this.labels = labels;
    }
}
