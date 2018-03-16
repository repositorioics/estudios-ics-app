package ni.org.ics.estudios.appmovil.muestreoanual.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.wizard.model.*;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;
import java.util.List;

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

    private String[] fillBarrios(){
        String[] catalogo;
        List<Barrio> barrios = estudiosAdapter.getBarrios(null, CatalogosDBConstants.nombre);
        catalogo = new String[barrios.size()];
        index = 0;
        for (Barrio message: barrios){
            catalogo[index] = message.getNombre();
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
    	String[] catSexo = fillCatalog("CHF_CAT_SEXO");
        String[] catSiNo = fillCatalog("CHF_CAT_SINO");
        String[] catSiNoDes = fillCatalog("CHF_CAT_SND");
        String[] catRelacionFamiliarTutor = fillCatalog("CP_CAT_RFTUTOR");
        String[] barrios = fillBarrios();
        String[] catDondeAsisteProblemasSalud = fillCatalog("CHF_CAT_DONDEASISTE");
        String[] catPuestoSalud = fillCatalog("CHF_CAT_PUESTO");
        String[] catCriteriosInclusion = fillCatalog("CP_CAT_CI");
        String[] catTiempoResid = fillCatalog("CP_CAT_TR");
        String[] catRazonNoParticipaPersona = fillCatalog("CHF_CAT_NPP");
        String[] catTipoIngreso = {"Dengue","Influenza","Ambos"};
        estudiosAdapter.close();

        DateMidnight dmDesde = DateMidnight.parse("01/01/1900", DateTimeFormat.forPattern("dd/MM/yyyy"));
        DateMidnight dmHasta = new DateMidnight(new Date().getTime());

        Page tipoIngreso = new SingleFixedChoicePage(this,labels.getTipoIngreso(), labels.getTipoIngresoHint(), Constants.WIZARD, true).setChoices(catTipoIngreso).setRequired(true);
		Page sexo = new SingleFixedChoicePage(this,labels.getSexo(), labels.getSexoHint(), Constants.WIZARD, true).setChoices(catSexo).setRequired(true);
		Page fechaNacimiento = new NewDatePage(this,labels.getFechaNacimiento(), labels.getFechaNacimientoHint(), Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);

        Page aceptaTamizajePersona = new SingleFixedChoicePage(this,labels.getAceptaTamizajePersona(), labels.getAceptaTamizajePersonaHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoParticipaPersona = new SingleFixedChoicePage(this,labels.getRazonNoParticipaPersona(), labels.getRazonNoParticipaPersonaHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoParticipaPersona = new TextPage(this,labels.getOtraRazonNoParticipaPersona(),labels.getOtraRazonNoParticipaPersonaHint(),Constants.WIZARD, false).setRequired(true);
        Page asentimientoVerbal = new SingleFixedChoicePage(this,labels.getAsentimientoVerbal(), labels.getAsentimientoVerbalHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page criteriosInclusion = new MultipleFixedChoicePage(this,labels.getCriteriosInclusion(), labels.getCriteriosInclusionHint(), Constants.WIZARD, false).setChoices(catCriteriosInclusion).setRequired(true);
        Page tiempoResidencia = new SingleFixedChoicePage(this,labels.getTiempoResidencia(), "", Constants.WIZARD, false).setChoices(catTiempoResid).setRequired(true);

        Page dondeAsisteProblemasSalud = new SingleFixedChoicePage(this,labels.getDondeAsisteProblemasSalud(), labels.getDondeAsisteProblemasSaludHint(), Constants.WIZARD, false).setChoices(catDondeAsisteProblemasSalud).setRequired(true);
        Page otroCentroSalud = new TextPage(this,labels.getOtroCentroSalud(),labels.getOtroCentroSaludHint(),Constants.WIZARD, false).setRequired(true);
        Page puestoSalud = new SingleFixedChoicePage(this,labels.getPuestoSalud(), labels.getPuestoSaludHint(), Constants.WIZARD, false).setChoices(catPuestoSalud).setRequired(true);
        Page aceptaAtenderCentro = new SingleFixedChoicePage(this,labels.getAceptaAtenderCentro(), labels.getAceptaAtenderCentroHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page enfermedad = new SingleFixedChoicePage(this,labels.getEnfermedad(), labels.getEnfermedadHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page cualEnfermedad = new TextPage(this,labels.getCualEnfermedad(),"", Constants.WIZARD, false).setRequired(true);
        Page tratamiento = new SingleFixedChoicePage(this,labels.getTratamiento(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page cualTratamiento = new TextPage(this,labels.getCualTratamiento(),"", Constants.WIZARD, false).setRequired(true);
        Page diagDengue = new SingleFixedChoicePage(this,labels.getDiagDengue(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page fechaDiagDengue = new NewDatePage(this,labels.getFechaDiagDengue(), "", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
        Page hospDengue = new SingleFixedChoicePage(this,labels.getHospDengue(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page fechaHospDengue = new NewDatePage(this,labels.getFechaHospDengue(), "", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);

        Page aceptaCohorteDengue = new SingleFixedChoicePage(this,labels.getAceptaCohorteDengue(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaDengue = new SingleFixedChoicePage(this,labels.getRazonNoAceptaDengue(), labels.getRazonNoAceptaDengueHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaDengue = new TextPage(this,labels.getOtraRazonNoAceptaDengue(),labels.getOtraRazonNoAceptaDengueHint(),Constants.WIZARD, false).setRequired(true);
        Page aceptaParteB = new SingleFixedChoicePage(this,labels.getAceptaParteB(), labels.getAceptaParteBHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page aceptaParteC = new SingleFixedChoicePage(this,labels.getAceptaParteC(), labels.getAceptaParteCHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page aceptaParteD = new SingleFixedChoicePage(this,labels.getAceptaParteD(), labels.getAceptaParteDHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page aceptaCohorteInfluenza = new SingleFixedChoicePage(this,labels.getAceptaCohorteInfluenza(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page pretermino = new SingleFixedChoicePage(this,labels.getPretermino(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page enfermedadInmuno = new SingleFixedChoicePage(this,labels.getEnfermedadInmuno(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaInfluenza = new SingleFixedChoicePage(this,labels.getRazonNoAceptaInfluenza(), labels.getRazonNoAceptaInfluenzaHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaInfluenza = new TextPage(this,labels.getOtraRazonNoAceptaInfluenza(),labels.getOtraRazonNoAceptaInfluenzaHint(),Constants.WIZARD, false).setRequired(true);
        Page aceptaParteBInf = new SingleFixedChoicePage(this,labels.getAceptaParteBInf(), labels.getAceptaParteBInfHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page aceptaParteCInf = new SingleFixedChoicePage(this,labels.getAceptaParteCInf(), labels.getAceptaParteCInfHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page casaPerteneceCohorte = new SingleFixedChoicePage(this,labels.getCasaPerteneceCohorte(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page codigoCasaCohorte = new SelectCasaPage(this,labels.getCodigoCasaCohorte(),labels.getCodigoCasaCohorteHint(),Constants.WIZARD, false).setRequired(true);
        Page codigoNuevaCasaCohorte = new NumberPage(this,labels.getCodigoNuevaCasaCohorte(),"",Constants.WIZARD, false).setRequired(true);
        Page nombre1JefeFamilia = new TextPage(this,labels.getNombre1JefeFamilia(), labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(true);
        Page nombre2JefeFamilia = new TextPage(this,labels.getNombre2JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(false);
        Page apellido1JefeFamilia = new TextPage(this,labels.getApellido1JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(true);
        Page apellido2JefeFamilia = new TextPage(this,labels.getApellido2JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(false);
        Page barrio = new SingleFixedChoicePage(this,labels.getBarrio(), "", Constants.WIZARD, false).setChoices(barrios).setRequired(true);
        Page manzana = new NumberPage(this,labels.getManzana(),"",Constants.WIZARD, false).setRangeValidation(true,0,86).setRequired(true);
        Page direccion = new TextPage(this,labels.getDireccion(),labels.getDireccionHint(), Constants.WIZARD, false).setRequired(true);

		Page codigoNuevoParticipante = new BarcodePage(this,labels.getCodigoNuevoParticipante(),"",Constants.WIZARD, false).setRequired(true);
        Page nombre1 = new TextPage(this,labels.getNombre1(),labels.getNombre1Hint(),Constants.WIZARD, false).setRequired(true);
        Page nombre2 = new TextPage(this,labels.getNombre2(),labels.getNombre1Hint(),Constants.WIZARD, false).setRequired(false);
        Page apellido1 = new TextPage(this,labels.getApellido1(),labels.getApellido1Hint(),Constants.WIZARD, false).setRequired(true);
        Page apellido2 = new TextPage(this,labels.getApellido2(),labels.getApellido1Hint(),Constants.WIZARD, false).setRequired(false);
        Page nombre1Padre = new TextPage(this,labels.getNombre1Padre(),labels.getNombre1PadreHint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2Padre = new TextPage(this,labels.getNombre2Padre(),labels.getNombre2Padre(),Constants.WIZARD,false).setRequired(false);
        Page apellido1Padre = new TextPage(this,labels.getApellido1Padre(),labels.getApellido1PadreHint(),Constants.WIZARD,false).setRequired(true);
        Page apellido2Padre = new TextPage(this,labels.getApellido2Padre(),labels.getApellido2PadreHint(),Constants.WIZARD,false).setRequired(false);
        Page nombre1Madre = new TextPage(this,labels.getNombre1Madre(),labels.getNombre1MadreHint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2Madre = new TextPage(this,labels.getNombre2Madre(),labels.getNombre2Madre(),Constants.WIZARD,false).setRequired(false);
        Page apellido1Madre = new TextPage(this,labels.getApellido1Madre(),labels.getApellido1Madre(),Constants.WIZARD,false).setRequired(true);
        Page apellido2Madre = new TextPage(this,labels.getApellido2Madre(),labels.getApellido2MadreHint(),Constants.WIZARD,false).setRequired(false);

        Page nombre1Tutor = new TextPage(this,labels.getNombre1Tutor(),labels.getNombre1TutorHint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2Tutor = new TextPage(this,labels.getNombre2Tutor(),labels.getNombre2TutorHint(),Constants.WIZARD,false).setRequired(false);
        Page apellido1Tutor = new TextPage(this,labels.getApellido1Tutor(),labels.getApellido1TutorHint(),Constants.WIZARD,false).setRequired(true);
        Page apellido2Tutor = new TextPage(this,labels.getApellido2Tutor(),labels.getApellido2TutorHint(),Constants.WIZARD,false).setRequired(false);
        Page relacionFamiliarTutor = new SingleFixedChoicePage(this,labels.getRelacionFamiliarTutor(), "", Constants.WIZARD, false).setChoices(catRelacionFamiliarTutor).setRequired(true);

        Page participanteOTutorAlfabeto = new SingleFixedChoicePage(this,labels.getParticipanteOTutorAlfabeto(), labels.getParticipanteOTutorAlfabetoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page testigoPresente = new SingleFixedChoicePage(this,labels.getTestigoPresente(), labels.getTestigoPresenteHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page nombre1Testigo = new TextPage(this,labels.getNombre1Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2Testigo = new TextPage(this,labels.getNombre2Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(false);
        Page apellido1Testigo = new TextPage(this,labels.getApellido1Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(true);
        Page apellido2Testigo = new TextPage(this,labels.getApellido2Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(false);

        Page finTamizajeLabel = new LabelPage(this,labels.getFinTamizajeLabel(),"", Constants.WIZARD, true).setRequired(false);

		return new PageList(tipoIngreso, sexo, fechaNacimiento, aceptaTamizajePersona, razonNoParticipaPersona, otraRazonNoParticipaPersona,
                criteriosInclusion, tiempoResidencia, dondeAsisteProblemasSalud, otroCentroSalud, puestoSalud, aceptaAtenderCentro,
                enfermedad, cualEnfermedad, tratamiento, cualTratamiento, diagDengue, fechaDiagDengue, hospDengue, fechaHospDengue,
                asentimientoVerbal, aceptaCohorteDengue, razonNoAceptaDengue, otraRazonNoAceptaDengue,
                aceptaCohorteInfluenza, enfermedadInmuno, pretermino, razonNoAceptaInfluenza, otraRazonNoAceptaInfluenza,
                casaPerteneceCohorte, codigoCasaCohorte, codigoNuevaCasaCohorte, nombre1JefeFamilia, nombre2JefeFamilia, apellido1JefeFamilia, apellido2JefeFamilia, barrio, manzana, direccion,
                codigoNuevoParticipante, nombre1, nombre2, apellido1, apellido2,
                nombre1Padre, nombre2Padre, apellido1Padre, apellido2Padre, nombre1Madre, nombre2Madre, apellido1Madre, apellido2Madre,
                nombre1Tutor, nombre2Tutor, apellido1Tutor, apellido2Tutor, relacionFamiliarTutor,
                participanteOTutorAlfabeto, testigoPresente, nombre1Testigo, nombre2Testigo, apellido1Testigo, apellido2Testigo,
                aceptaParteB, aceptaParteC, aceptaParteD, aceptaParteBInf, aceptaParteCInf,
                finTamizajeLabel);
    }

	public TamizajeFormLabels getLabels() {
		return labels;
	}

	public void setLabels(TamizajeFormLabels labels) {
		this.labels = labels;
	}
    
}
