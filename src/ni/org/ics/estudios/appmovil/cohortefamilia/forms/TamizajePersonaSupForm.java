package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

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

public class TamizajePersonaSupForm extends AbstractWizardModel {

	int index = 0;
	private TamizajeFormLabels labels;
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

    public TamizajePersonaSupForm(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	
    	labels = new TamizajeFormLabels();
    	this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
    	
    	estudiosAdapter.open();
    	String[] catSiNo = fillCatalog("CHF_CAT_SINO");
    	String[] catRazonNoParticipaPersona = fillCatalog("CHF_CAT_NPP");
    	String[] catRelacionFamiliarTutor = fillCatalog("CHF_CAT_RFTUTOR");
        String[] catVerificaTutor = fillCatalog("CP_CAT_VERIFTUTOR");
		estudiosAdapter.close();
		
		Page aceptaParticipar = new SingleFixedChoicePage(this,labels.getAceptaParticipar(), labels.getAceptaParticiparHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
		Page razonNoAceptaParticipar = new SingleFixedChoicePage(this,labels.getRazonNoAceptaParticipar(), labels.getRazonNoAceptaParticiparHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaParticipar = new TextPage(this,labels.getOtraRazonNoAceptaParticipar(),labels.getOtraRazonNoAceptaParticiparHint(),Constants.WIZARD,false).setRequired(true);
        Page asentimientoVerbal = new SingleFixedChoicePage(this,labels.getAsentimientoVerbal(), labels.getAsentimientoVerbalHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

		Page nombre1Tutor = new TextPage(this,labels.getNombre1Tutor(),labels.getNombre1TutorHint(),Constants.WIZARD,false).setRequired(true);
		Page nombre2Tutor = new TextPage(this,labels.getNombre2Tutor(),labels.getNombre2TutorHint(),Constants.WIZARD,false).setRequired(false);
		Page apellido1Tutor = new TextPage(this,labels.getApellido1Tutor(),labels.getApellido1TutorHint(),Constants.WIZARD,false).setRequired(true);
		Page apellido2Tutor = new TextPage(this,labels.getApellido2Tutor(),labels.getApellido2TutorHint(),Constants.WIZARD,false).setRequired(false);
		Page relacionFamiliarTutor = new SingleFixedChoicePage(this,labels.getRelacionFamiliarTutor(), labels.getRelacionFamiliarTutorHint(), Constants.WIZARD, false).setChoices(catRelacionFamiliarTutor).setRequired(true);
		Page participanteOTutorAlfabeto = new SingleFixedChoicePage(this,labels.getParticipanteOTutorAlfabeto(), labels.getParticipanteOTutorAlfabetoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);	
		Page testigoPresente = new SingleFixedChoicePage(this,labels.getTestigoPresente(), labels.getTestigoPresenteHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);	
		Page nombre1Testigo = new TextPage(this,labels.getNombre1Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(true);
		Page nombre2Testigo = new TextPage(this,labels.getNombre2Testigo(),labels.getNombre2Testigo(),Constants.WIZARD,false).setRequired(false);
		Page apellido1Testigo = new TextPage(this,labels.getApellido1Testigo(),labels.getApellido1Testigo(),Constants.WIZARD,false).setRequired(true);
		Page apellido2Testigo = new TextPage(this,labels.getApellido2Testigo(),labels.getApellido2Testigo(),Constants.WIZARD,false).setRequired(false);
		
		Page aceptaParteD = new SingleFixedChoicePage(this,labels.getAceptaParteDChf(), labels.getAceptaParteDChfHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaParteD = new SingleFixedChoicePage(this,labels.getRazonNoAceptaParteDChf(), labels.getRazonNoAceptaParteDChfHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaParteD = new TextPage(this,labels.getOtraRazonNoAceptaParteDChf(),labels.getOtraRazonNoAceptaParteDChfHint(),Constants.WIZARD,false).setRequired(true);

        Page verifTutor = new MultipleFixedChoicePage(this,labels.getVerifTutor(), "", Constants.WIZARD, false).setChoices(catVerificaTutor).setRequired(true);

        Page asentimientoVerbalCasa = new SingleFixedChoicePage(this,labels.getAsentimientoVerbalMxSuper(), labels.getAsentimientoVerbalMxSuperHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page nombre1MxSup = new TextPage(this,labels.getNombre1MxSuperficie(),labels.getNombre1MxSuperficieHint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2MxSup = new TextPage(this,labels.getNombre2MxSuperficie(),labels.getNombre1MxSuperficieHint(),Constants.WIZARD,false).setRequired(false);
        Page apellido1MxSup = new TextPage(this,labels.getApellido1MxSuperficie(),labels.getNombre1MxSuperficieHint(),Constants.WIZARD,false).setRequired(true);
        Page apellido2MxSup = new TextPage(this,labels.getApellido2MxSuperficie(),labels.getNombre1MxSuperficieHint(),Constants.WIZARD,false).setRequired(false);

        Page finTamizajeLabel = new LabelPage(this,labels.getFinTamizajeLabel(),"", Constants.WIZARD, false).setRequired(false);
		
		
		return new PageList(aceptaParticipar,razonNoAceptaParticipar,otraRazonNoAceptaParticipar,asentimientoVerbal,nombre1Tutor,nombre2Tutor,apellido1Tutor,apellido2Tutor,relacionFamiliarTutor,participanteOTutorAlfabeto,testigoPresente,nombre1Testigo,nombre2Testigo,apellido1Testigo,apellido2Testigo,
				aceptaParteD, razonNoAceptaParteD, otraRazonNoAceptaParteD, verifTutor, asentimientoVerbalCasa, nombre1MxSup, nombre2MxSup, apellido1MxSup, apellido2MxSup, finTamizajeLabel);
    }

	public TamizajeFormLabels getLabels() {
		return labels;
	}

	public void setLabels(TamizajeFormLabels labels) {
		this.labels = labels;
	}
    
}
