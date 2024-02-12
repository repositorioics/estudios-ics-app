package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.AbstractWizardModel;
import ni.org.ics.estudios.appmovil.wizard.model.BarcodePage;
import ni.org.ics.estudios.appmovil.wizard.model.LabelPage;
import ni.org.ics.estudios.appmovil.wizard.model.MultipleFixedChoicePage;
import ni.org.ics.estudios.appmovil.wizard.model.NewDatePage;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import ni.org.ics.estudios.appmovil.wizard.model.PageList;
import ni.org.ics.estudios.appmovil.wizard.model.SelectParticipantPage;
import ni.org.ics.estudios.appmovil.wizard.model.SingleFixedChoicePage;
import ni.org.ics.estudios.appmovil.wizard.model.TextPage;
import android.content.Context;

public class TamizajeForm extends AbstractWizardModel {
	
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
	
    public TamizajeForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	
    	labels = new TamizajeFormLabels();
    	this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
    	
    	estudiosAdapter.open();
    	String[] catSiNo = fillCatalog("CHF_CAT_SINO");
    	String[] catSexo = fillCatalog("CHF_CAT_SEXO");
    	String[] catRazonNoParticipaPersona = fillCatalog("CHF_CAT_NPP");
    	String[] catCriteriosInclusion = fillCatalog("CHF_CAT_CI");
    	String[] catDondeAsisteProblemasSalud = fillCatalog("CHF_CAT_DONDEASISTE");
    	String[] catPuestoSalud = fillCatalog("CHF_CAT_PUESTO");
    	String[] catRelacionFamiliarTutor = fillCatalog("CHF_CAT_RFTUTOR");
        String[] catVerificaTutor = fillCatalog("CP_CAT_VERIFTUTOR");
		estudiosAdapter.close();
		
		Page sexo = new SingleFixedChoicePage(this,labels.getSexo(), labels.getSexoHint(), Constants.WIZARD, true).setChoices(catSexo).setRequired(true);
		DateMidnight dmDesde = DateMidnight.parse("01/01/1900", DateTimeFormat.forPattern("dd/MM/yyyy"));
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
		Page fechaNacimiento = new NewDatePage(this,labels.getFechaNacimiento(), labels.getFechaNacimientoHint(), Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
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
        Page asentimientoVerbal = new SingleFixedChoicePage(this,labels.getAsentimientoVerbal(), labels.getAsentimientoVerbalHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
		Page participadoCohortePediatrica = new SingleFixedChoicePage(this,labels.getParticipadoCohortePediatrica(), labels.getParticipadoCohortePediatricaHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
		Page codigoCohorte = new SelectParticipantPage(this,labels.getCodigoCohorte(),labels.getNombre1Hint(),Constants.WIZARD,false).setRequired(true);
		Page codigoNuevoParticipante = new BarcodePage(this,labels.getCodigoNuevoParticipante(),"",Constants.WIZARD,false).setRequired(true);
		Page nombre1 = new TextPage(this,labels.getNombre1(),labels.getNombre1Hint(),Constants.WIZARD,false).setRequired(true);
		Page nombre2 = new TextPage(this,labels.getNombre2(),labels.getNombre1Hint(),Constants.WIZARD,false).setRequired(false);
		Page apellido1 = new TextPage(this,labels.getApellido1(),labels.getApellido1Hint(),Constants.WIZARD,false).setRequired(true);
		Page apellido2 = new TextPage(this,labels.getApellido2(),labels.getApellido1Hint(),Constants.WIZARD,false).setRequired(false);
		Page nombre1Padre = new TextPage(this,labels.getNombre1Padre(),labels.getNombre1PadreHint(),Constants.WIZARD,false).setRequired(true);
		Page nombre2Padre = new TextPage(this,labels.getNombre2Padre(),labels.getNombre2Padre(),Constants.WIZARD,false).setRequired(false);
		Page apellido1Padre = new TextPage(this,labels.getApellido1Padre(),labels.getApellido1PadreHint(),Constants.WIZARD,false).setRequired(true);
		Page apellido2Padre = new TextPage(this,labels.getApellido2Padre(),labels.getApellido2PadreHint(),Constants.WIZARD,false).setRequired(false);
		Page nombre1Madre = new TextPage(this,labels.getNombre1Madre(),labels.getNombre1MadreHint(),Constants.WIZARD,false).setRequired(true);
		Page nombre2Madre = new TextPage(this,labels.getNombre2Madre(),labels.getNombre2Madre(),Constants.WIZARD,false).setRequired(false);
		Page apellido1Madre = new TextPage(this,labels.getApellido1Madre(),labels.getApellido1Madre(),Constants.WIZARD,false).setRequired(true);
		Page apellido2Madre = new TextPage(this,labels.getApellido2Madre(),labels.getApellido2MadreHint(),Constants.WIZARD,false).setRequired(false);
		
		Page emancipado = new SingleFixedChoicePage(this,labels.getEmancipado(), labels.getEmancipadoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
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
		
		Page aceptaParteA = new SingleFixedChoicePage(this,labels.getAceptaParteA(), labels.getAceptaParteAHint(), Constants.WIZARD, false).setChoices("Si").setRequired(true);
		Page motivoRechazoParteA = new SingleFixedChoicePage(this,labels.getMotivoRechazoParteA(), labels.getMotivoRechazoParteAHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
		Page aceptaContactoFuturo = new SingleFixedChoicePage(this,labels.getAceptaContactoFuturo(), labels.getAceptaContactoFuturoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
		Page aceptaParteB = new SingleFixedChoicePage(this,labels.getAceptaParteB(), labels.getAceptaParteBHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
		Page aceptaParteC = new SingleFixedChoicePage(this,labels.getAceptaParteC(), labels.getAceptaParteCHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page aceptaSeroprevalencia = new SingleFixedChoicePage(this,labels.getAceptaSeroprevalencia(), labels.getAceptaSeroprevalenciaHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaSeroprevalencia = new SingleFixedChoicePage(this,labels.getRazonNoAceptaSeroprevalencia(), labels.getRazonNoAceptaSeroprevalenciaHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaSeroprevalencia = new TextPage(this,labels.getOtraRazonNoAceptaSeroprevalencia(),labels.getOtraRazonNoAceptaSeroprevalenciaHint(),Constants.WIZARD,false).setRequired(true);

        Page aceptaCohorteDengue = new SingleFixedChoicePage(this,labels.getAceptaCohorteDengue(), labels.getAceptaCohorteDengueHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page aceptaParteD = new SingleFixedChoicePage(this,labels.getAceptaParteD(), labels.getAceptaParteD(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaDengue = new SingleFixedChoicePage(this,labels.getRazonNoAceptaDengue(), labels.getRazonNoAceptaDengueHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaDengue = new TextPage(this,labels.getOtraRazonNoAceptaDengue(),labels.getOtraRazonNoAceptaDengueHint(),Constants.WIZARD,false).setRequired(true);

        Page aceptaCohorteInfluenza = new SingleFixedChoicePage(this,labels.getAceptaCohorteInfluenza(), labels.getAceptaCohorteInfluenzaHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaInfluenza = new SingleFixedChoicePage(this,labels.getRazonNoAceptaInfluenza(), labels.getRazonNoAceptaInfluenzaHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaInfluenza = new TextPage(this,labels.getOtraRazonNoAceptaInfluenza(),labels.getOtraRazonNoAceptaInfluenzaHint(),Constants.WIZARD,false).setRequired(true);

        Page verifTutor = new MultipleFixedChoicePage(this,labels.getVerifTutor(), "", Constants.WIZARD, false).setChoices(catVerificaTutor).setRequired(true);

        Page finTamizajeLabel = new LabelPage(this,labels.getFinTamizajeLabel(),"", Constants.WIZARD, false).setRequired(false);
		
		
		/*return new PageList(sexo,fechaNacimiento,aceptaTamizajePersona,razonNoParticipaPersona,otraRazonNoParticipaPersona,
				criteriosInclusion,enfermedad,dondeAsisteProblemasSalud,otroCentroSalud,puestoSalud,aceptaAtenderCentro,esElegible,aceptaParticipar,razonNoAceptaParticipar,otraRazonNoAceptaParticipar,asentimientoVerbal,
				participadoCohortePediatrica,codigoCohorte,codigoNuevoParticipante,nombre1,nombre2,apellido1,apellido2,nombre1Padre,nombre2Padre,apellido1Padre,apellido2Padre,nombre1Madre,nombre2Madre,apellido1Madre,apellido2Madre,
				emancipado,nombre1Tutor,nombre2Tutor,apellido1Tutor,apellido2Tutor,relacionFamiliarTutor,participanteOTutorAlfabeto,testigoPresente,nombre1Testigo,nombre2Testigo,apellido1Testigo,apellido2Testigo,
				aceptaParteA,motivoRechazoParteA,aceptaContactoFuturo,aceptaParteB,aceptaParteC,aceptaSeroprevalencia, razonNoAceptaSeroprevalencia, otraRazonNoAceptaSeroprevalencia,aceptaCohorteDengue,
                aceptaParteD, razonNoAceptaDengue, otraRazonNoAceptaDengue,aceptaCohorteInfluenza, razonNoAceptaInfluenza, otraRazonNoAceptaInfluenza, verifTutor, finTamizajeLabel);*/

		return new PageList(sexo,fechaNacimiento,aceptaTamizajePersona,razonNoParticipaPersona,otraRazonNoParticipaPersona,
				criteriosInclusion,enfermedad,dondeAsisteProblemasSalud,otroCentroSalud,puestoSalud,aceptaAtenderCentro,esElegible,aceptaParticipar,razonNoAceptaParticipar,otraRazonNoAceptaParticipar,asentimientoVerbal,
				participadoCohortePediatrica,codigoCohorte,codigoNuevoParticipante,nombre1,nombre2,apellido1,apellido2,nombre1Padre,nombre2Padre,apellido1Padre,apellido2Padre,nombre1Madre,nombre2Madre,apellido1Madre,apellido2Madre,
				emancipado,nombre1Tutor,nombre2Tutor,apellido1Tutor,apellido2Tutor,relacionFamiliarTutor,participanteOTutorAlfabeto,testigoPresente,nombre1Testigo,nombre2Testigo,apellido1Testigo,apellido2Testigo,
				aceptaParteA,motivoRechazoParteA,aceptaContactoFuturo,aceptaParteB,aceptaParteC,aceptaCohorteDengue,
				aceptaParteD, razonNoAceptaDengue, otraRazonNoAceptaDengue,aceptaCohorteInfluenza, razonNoAceptaInfluenza, otraRazonNoAceptaInfluenza, verifTutor, finTamizajeLabel);
    }

	public TamizajeFormLabels getLabels() {
		return labels;
	}

	public void setLabels(TamizajeFormLabels labels) {
		this.labels = labels;
	}
    
}
