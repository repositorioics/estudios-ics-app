package ni.org.ics.estudios.appmovil.seroprevalencia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.List;

public class ConsentimientoSAForm extends AbstractWizardModel {

	int index = 0;
	private ConsentimientoSAFormLabels labels;
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


    public ConsentimientoSAForm(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {

    	labels = new ConsentimientoSAFormLabels();
    	this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);

    	estudiosAdapter.open();
        String[] catSiNo = fillCatalog("CHF_CAT_SINO");
        String[] catVisNoExitosa = fillCatalog("CP_CAT_NV");
        String[] catRelacionFamiliar = fillCatalog("CP_CAT_RFTUTOR");
        String[] catRazonNoParticipaPersona = fillCatalog("CHF_CAT_NPP");
        String[] catVerificaTutor = fillCatalog("CP_CAT_VERIFTUTOR");
        String[] catDifTutor = fillCatalog("CP_CAT_DIFTUTOR");
        estudiosAdapter.close();

        Page visExit = new SingleFixedChoicePage(this,labels.getVisExit(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page visNoExit = new SingleFixedChoicePage(this,labels.getRazonVisNoExit(), "", Constants.WIZARD, false).setChoices(catVisNoExitosa).setRequired(true);
        Page otraRazonVisitaNoExitosa = new TextPage(this,labels.getOtraRazonVisitaNoExitosa(), labels.getOtraRazonVisitaNoExitosaHint(), Constants.WIZARD, false).setRequired(true);
        Page personaCasa = new TextPage(this,labels.getPersonaCasa(), labels.getPersonaCasaHint(), Constants.WIZARD, false).setRequired(true);
        Page relacionFamPersonaCasa = new SingleFixedChoicePage(this,labels.getRelacionFamPersonaCasa(), labels.getRelacionFamPersonaCasaHint(), Constants.WIZARD, false).setChoices(catRelacionFamiliar).setRequired(true);
        Page otraRelacionPersonaCasa = new TextPage(this,labels.getOtraRelacionPersonaCasa(), "", Constants.WIZARD, false).setRequired(true);
        Page telefonoPersonaCasa = new NumberPage(this,labels.getTelefonoPersonaCasa(), "", Constants.WIZARD, false).setRangeValidation(true, Constants.MINIMO_NUM_CONVENCIONAL, Constants.MAXIMO_NUM_CELULAR).setRequired(false);

        Page aceptaTamizajePersona = new SingleFixedChoicePage(this,labels.getAceptaTamizajePersona(), labels.getAceptaTamizajePersonaHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page razonNoParticipaPersona = new SingleFixedChoicePage(this,labels.getRazonNoParticipaPersona(), labels.getRazonNoParticipaPersonaHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoParticipaPersona = new TextPage(this,labels.getOtraRazonNoParticipaPersona(),labels.getOtraRazonNoParticipaPersonaHint(), Constants.WIZARD, false).setRequired(true);
        Page asentimientoVerbal = new SingleFixedChoicePage(this,labels.getAsentimientoVerbal(), labels.getAsentimientoVerbalHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page aceptaSeroprevalencia = new SingleFixedChoicePage(this,labels.getAceptaSeroprevalencia(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaSeroprevalencia = new SingleFixedChoicePage(this,labels.getRazonNoAceptaSeroprevalencia(), labels.getRazonNoAceptaSeroprevalenciaHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaSeroprevalencia = new TextPage(this,labels.getOtraRazonNoAceptaSeroprevalencia(),labels.getOtraRazonNoAceptaSeroprevalenciaHint(), Constants.WIZARD, false).setRequired(true);

        Page tutor = new LabelPage(this,labels.getTutor(), "", Constants.WIZARD, false).setRequired(false);
        Page mismoTutorSN = new SingleFixedChoicePage(this,labels.getMismoTutorSN(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page nombre1Tutor = new TextPage(this,labels.getNombre1Tutor(),labels.getNombre1TutorHint(), Constants.WIZARD,false).setRequired(true);
        Page nombre2Tutor = new TextPage(this,labels.getNombre2Tutor(),labels.getNombre2TutorHint(), Constants.WIZARD,false).setRequired(false);
        Page apellido1Tutor = new TextPage(this,labels.getApellido1Tutor(),labels.getApellido1TutorHint(), Constants.WIZARD,false).setRequired(true);
        Page apellido2Tutor = new TextPage(this,labels.getApellido2Tutor(),labels.getApellido2TutorHint(), Constants.WIZARD,false).setRequired(false);
        Page relacionFamiliarTutor = new SingleFixedChoicePage(this,labels.getRelacionFamiliarTutor(), "", Constants.WIZARD, false).setChoices(catRelacionFamiliar).setRequired(true);
        Page motivoDifTutor = new SingleFixedChoicePage(this,labels.getMotivoDifTutor(), "", Constants.WIZARD, false).setChoices(catDifTutor).setRequired(true);
        Page otroMotivoDifTutor = new TextPage(this,labels.getOtroMotivoDifTutor(), "", Constants.WIZARD, false).setRequired(true);

        Page participanteOTutorAlfabeto = new SingleFixedChoicePage(this,labels.getParticipanteOTutorAlfabeto(), labels.getParticipanteOTutorAlfabetoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page testigoPresente = new SingleFixedChoicePage(this,labels.getTestigoPresente(), labels.getTestigoPresenteHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page nombre1Testigo = new TextPage(this,labels.getNombre1Testigo(),labels.getNombre1TestigoHint(), Constants.WIZARD,false).setRequired(true);
        Page nombre2Testigo = new TextPage(this,labels.getNombre2Testigo(),labels.getNombre1TestigoHint(), Constants.WIZARD,false).setRequired(false);
        Page apellido1Testigo = new TextPage(this,labels.getApellido1Testigo(),labels.getNombre1TestigoHint(), Constants.WIZARD,false).setRequired(true);
        Page apellido2Testigo = new TextPage(this,labels.getApellido2Testigo(),labels.getNombre1TestigoHint(), Constants.WIZARD,false).setRequired(false);

        Page verifTutor = new MultipleFixedChoicePage(this,labels.getVerifTutor(), "", Constants.WIZARD, false).setChoices(catVerificaTutor).setRequired(true);
        Page finTamizajeLabel = new LabelPage(this,labels.getFinTamizajeLabel(),"", Constants.WIZARD, true).setRequired(false);

		/*return new PageList(visExit, visNoExit, otraRazonVisitaNoExitosa, personaCasa, relacionFamPersonaCasa, otraRelacionPersonaCasa, telefonoPersonaCasa,
                aceptaTamizajePersona, razonNoParticipaPersona, otraRazonNoParticipaPersona,
                asentimientoVerbal, aceptaSeroprevalencia, razonNoAceptaSeroprevalencia, otraRazonNoAceptaSeroprevalencia,
                tutor, mismoTutorSN, nombre1Tutor, nombre2Tutor, apellido1Tutor, apellido2Tutor, relacionFamiliarTutor, motivoDifTutor, otroMotivoDifTutor,
                participanteOTutorAlfabeto, testigoPresente, nombre1Testigo, nombre2Testigo, apellido1Testigo, apellido2Testigo,
                verifTutor, finTamizajeLabel);*/

        return new PageList(visExit, visNoExit, otraRazonVisitaNoExitosa, personaCasa, relacionFamPersonaCasa, otraRelacionPersonaCasa, telefonoPersonaCasa,
                aceptaTamizajePersona, razonNoParticipaPersona, otraRazonNoParticipaPersona,
                asentimientoVerbal, tutor, mismoTutorSN, nombre1Tutor, nombre2Tutor, apellido1Tutor, apellido2Tutor, relacionFamiliarTutor, motivoDifTutor, otroMotivoDifTutor,
                participanteOTutorAlfabeto, testigoPresente, nombre1Testigo, nombre2Testigo, apellido1Testigo, apellido2Testigo,
                verifTutor, finTamizajeLabel);
    }

	public ConsentimientoSAFormLabels getLabels() {
		return labels;
	}

	public void setLabels(ConsentimientoSAFormLabels labels) {
		this.labels = labels;
	}
    
}
