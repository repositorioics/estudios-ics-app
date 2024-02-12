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

    private String[] fillBarrios(boolean incluirFueraSector){
        String[] catalogo;
        List<Barrio> barrios = estudiosAdapter.getBarrios(incluirFueraSector?null:CatalogosDBConstants.nombre+" <> 'Fuera de Sector'", CatalogosDBConstants.nombre);
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
        String[] barrios = fillBarrios(false);
        String[] barriosCon = fillBarrios(true);
        String[] catDondeAsisteProblemasSalud = fillCatalog("CHF_CAT_DONDEASISTE");
        String[] catPuestoSalud = fillCatalog("CHF_CAT_PUESTO");
        String[] catCriteriosInclusion = fillCatalog("CP_CAT_CI");
        String[] catTiempoResid = fillCatalog("CP_CAT_TR");
        String[] catRazonNoParticipaPersona = fillCatalog("CHF_CAT_NPP");
        String[] catTipoIngreso = {"Dengue","UO1"};//Ya no hay nuevo ingreso influenza. MA2021. Brenda
        String[] catTipo = fillCatalog("CAT_TIPO_TEL");
        String[] catOperadora = fillCatalog("CAT_OPER_TEL");
        String[] catTipoViv = fillCatalog("CP_CAT_TV");
        String[] catMeses = fillCatalog("CHF_CAT_MESES");
        String[] catEnfCron = fillCatalog("ENFERMEDAD_CRN");
        String[] catTramiento = fillCatalog("CPD_CAT_TRATAMIENTO");
        String[] catVerificaTutor = fillCatalog("CP_CAT_VERIFTUTOR");
        String[] catMotivoRechazo = fillCatalog("CPD_CAT_MOTRECHAZO");
        String[] catSiNoDesSinFecha = fillCatalog("CAT_SND_SINFECHA");
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
        Page vivienda = new SingleFixedChoicePage(this,labels.getVivienda(), "", Constants.WIZARD, false).setChoices(catTipoViv).setRequired(true);
        Page tiempoResidencia = new SingleFixedChoicePage(this,labels.getTiempoResidencia(), "", Constants.WIZARD, false).setChoices(catTiempoResid).setRequired(true);

        Page dondeAsisteProblemasSalud = new SingleFixedChoicePage(this,labels.getDondeAsisteProblemasSalud(), labels.getDondeAsisteProblemasSaludHint(), Constants.WIZARD, false).setChoices(catDondeAsisteProblemasSalud).setRequired(true);
        Page otroCentroSalud = new TextPage(this,labels.getOtroCentroSalud(),labels.getOtroCentroSaludHint(),Constants.WIZARD, false).setRequired(true);
        Page puestoSalud = new SingleFixedChoicePage(this,labels.getPuestoSalud(), labels.getPuestoSaludHint(), Constants.WIZARD, false).setChoices(catPuestoSalud).setRequired(true);
        Page aceptaAtenderCentro = new SingleFixedChoicePage(this,labels.getAceptaAtenderCentro(), labels.getAceptaAtenderCentroHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page enfermedad = new SingleFixedChoicePage(this,labels.getEnfermedad(), labels.getEnfermedadHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page cualEnfermedad = new MultipleFixedChoicePage(this,labels.getCualEnfermedad(),"", Constants.WIZARD, false).setChoices(catEnfCron).setRequired(true);
        Page oEnfCronica = new TextPage(this,labels.getOtraEnfCronica(), "", Constants.WIZARD, false).setRequired(true);
        Page enfCronicaAnio1 = new NumberPage(this,labels.getEnfCronicaAnio1(), catEnfCron[0], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes1 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes1(), catEnfCron[0], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio2 = new NumberPage(this,labels.getEnfCronicaAnio2(), catEnfCron[1], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes2 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes2(), catEnfCron[1], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio3 = new NumberPage(this,labels.getEnfCronicaAnio3(), catEnfCron[2], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes3 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes3(), catEnfCron[2], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio4 = new NumberPage(this,labels.getEnfCronicaAnio4(), catEnfCron[3], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes4 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes4(), catEnfCron[3], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio5 = new NumberPage(this,labels.getEnfCronicaAnio5(), catEnfCron[4], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes5 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes5(), catEnfCron[4], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio6 = new NumberPage(this,labels.getEnfCronicaAnio6(), catEnfCron[5], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes6 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes6(), catEnfCron[5], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio7 = new NumberPage(this,labels.getEnfCronicaAnio7(), catEnfCron[6], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes7 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes7(), catEnfCron[6], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio8 = new NumberPage(this,labels.getEnfCronicaAnio8(), catEnfCron[7], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes8 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes8(), catEnfCron[7], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio9 = new NumberPage(this,labels.getEnfCronicaAnio9(), catEnfCron[8], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes9 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes9(), catEnfCron[8], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio10 = new NumberPage(this,labels.getEnfCronicaAnio10(), catEnfCron[9], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes10 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes10(), catEnfCron[9], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio11 = new NumberPage(this,labels.getEnfCronicaAnio11(), catEnfCron[10], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes11 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes11(), catEnfCron[10], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio12 = new NumberPage(this,labels.getEnfCronicaAnio12(), catEnfCron[11], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes12 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes12(), catEnfCron[11], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio13 = new NumberPage(this,labels.getEnfCronicaAnio13(), catEnfCron[12], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes13 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes13(), catEnfCron[12], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page enfCronicaAnio14 = new NumberPage(this,labels.getEnfCronicaAnio14(), catEnfCron[13], Constants.WIZARD, false).setRequired(true);
        Page enfCronicaMes14 = new SingleFixedChoicePage(this,labels.getEnfCronicaMes14(), catEnfCron[13], Constants.WIZARD, false).setChoices(catMeses).setRequired(true);

        Page tratamiento = new SingleFixedChoicePage(this,labels.getTratamiento(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page cualTratamiento = new MultipleFixedChoicePage(this,labels.getCualTratamiento(),"", Constants.WIZARD, false).setChoices(catTramiento).setRequired(true);
        Page otroTx = new TextPage(this,labels.getOtroTratamiento(), "", Constants.WIZARD, false).setRequired(true);

        Page diagDengue = new SingleFixedChoicePage(this,labels.getDiagDengue(), "", Constants.WIZARD, false).setChoices(catSiNoDesSinFecha).setRequired(true);
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
        Page aceptaCohorteInfluenzaUO1 = new SingleFixedChoicePage(this,labels.getAceptaCohorteInfluenzaUO1(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page pretermino = new SingleFixedChoicePage(this,labels.getPretermino(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page enfermedadInmuno = new SingleFixedChoicePage(this,labels.getEnfermedadInmuno(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaInfluenza = new SingleFixedChoicePage(this,labels.getRazonNoAceptaInfluenza(), labels.getRazonNoAceptaInfluenzaHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaInfluenza = new TextPage(this,labels.getOtraRazonNoAceptaInfluenza(),labels.getOtraRazonNoAceptaInfluenzaHint(),Constants.WIZARD, false).setRequired(true);
        Page razonNoAceptaInfluenzaUO1 = new SingleFixedChoicePage(this,labels.getRazonNoAceptaInfluenzaUO1(), labels.getRazonNoAceptaInfluenzaUO1Hint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaInfluenzaUO1 = new TextPage(this,labels.getOtraRazonNoAceptaInfluenzaUO1(),labels.getOtraRazonNoAceptaInfluenzaUO1Hint(),Constants.WIZARD, false).setRequired(true);
        Page aceptaParteBInf = new SingleFixedChoicePage(this,labels.getAceptaParteBInf(), labels.getAceptaParteBInfHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page aceptaParteCInf = new SingleFixedChoicePage(this,labels.getAceptaParteCInf(), labels.getAceptaParteCInfHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        //Covid19
        Page aceptaCohorteUO1ParteDCovid = new SingleFixedChoicePage(this,labels.getAceptaCohorteUO1ParteDCovid(), labels.getAceptaCohorteUO1ParteDCovidHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaParteD = new SingleFixedChoicePage(this,labels.getRazonNoAceptaParteD(), labels.getRazonNoAceptaParteDHint(), Constants.WIZARD, false).setChoices(catMotivoRechazo).setRequired(true);
        Page otraRazonNoAceptaParteD = new TextPage(this,labels.getOtraRazonNoAceptaParteD(),labels.getOtraRazonNoAceptaParteDHint(),Constants.WIZARD, false).setRequired(true);

        Page aceptaContactoFuturo = new SingleFixedChoicePage(this,labels.getAceptaContactoFuturo(), labels.getAceptaContactoFuturoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page casaPerteneceCohorte = new SingleFixedChoicePage(this,labels.getCasaPerteneceCohorte(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        //minimo al momento se hacer el cambio: 24092019
        Page codigoCasaCohorte = new SelectCasaPage(this,labels.getCodigoCasaCohorte(),labels.getCodigoCasaCohorteHint(),Constants.WIZARD, false).setRequired(true);
        Page codigoNuevaCasaCohorte = new BarcodePage(this,labels.getCodigoNuevaCasaCohorte(),"",Constants.WIZARD, false).setRangeValidation(true, 5193, 8999).setRequired(true);
        Page nombre1JefeFamilia = new TextPage(this,labels.getNombre1JefeFamilia(), labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(true);
        Page nombre2JefeFamilia = new TextPage(this,labels.getNombre2JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(false);
        Page apellido1JefeFamilia = new TextPage(this,labels.getApellido1JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(true);
        Page apellido2JefeFamilia = new TextPage(this,labels.getApellido2JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(false);
        Page barrio = new SingleFixedChoicePage(this,labels.getBarrio(), "", Constants.WIZARD, false).setChoices(barrios).setRequired(true);
        //Page manzana = new NumberPage(this,labels.getManzana(),"",Constants.WIZARD, false).setRangeValidation(true,0,114).setRequired(true);
        Page manzana = new NumberPage(this,labels.getManzana(),"",Constants.WIZARD, false).setRangeValidation(true,0,130).setRequired(true);
        Page direccion = new TextPage(this,labels.getDireccion(),labels.getDireccionHint(), Constants.WIZARD, false).setRequired(true);

        Page codigoNuevoParticipante = new BarcodePage(this,labels.getCodigoNuevoParticipante(),"",Constants.WIZARD, false).setPatternValidation(true, "^\\d{5}$").setRequired(true);
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
        Page otraRelacionFamTutor = new TextPage(this,labels.getOtraRelacionFamTutor(), "", Constants.WIZARD,false).setRequired(true);

        Page participanteOTutorAlfabeto = new SingleFixedChoicePage(this,labels.getParticipanteOTutorAlfabeto(), labels.getParticipanteOTutorAlfabetoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page testigoPresente = new SingleFixedChoicePage(this,labels.getTestigoPresente(), labels.getTestigoPresenteHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page nombre1Testigo = new TextPage(this,labels.getNombre1Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2Testigo = new TextPage(this,labels.getNombre2Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(false);
        Page apellido1Testigo = new TextPage(this,labels.getApellido1Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(true);
        Page apellido2Testigo = new TextPage(this,labels.getApellido2Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(false);


        Page nombreContacto = new TextPage(this,labels.getNombreContacto(),labels.getNombreContactoHint(),Constants.WIZARD, false).setRequired(true);
        Page barrioContacto = new SingleFixedChoicePage(this,labels.getBarrioContacto(), labels.getBarrioContactoHint(), Constants.WIZARD, false).setChoices(barriosCon).setRequired(true);
        Page direccionContacto = new TextPage(this,labels.getDireccionContacto(),labels.getDireccionContactoHint(), Constants.WIZARD, false).setRequired(true);
        Page tieneTelefono = new SingleFixedChoicePage(this,labels.getTieneTelefono(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page tipoTel1 = new SingleFixedChoicePage(this,labels.getTipoTelefono1(),labels.getTipoTelefono1Hint(), Constants.WIZARD, false).setChoices(catTipo).setRequired(true);
        Page operadoraTel1 = new SingleFixedChoicePage(this,labels.getOperadoraTelefono1(),labels.getOperadoraTelefono1Hint(), Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);
        Page numeroTel1 = new TextPage(this,labels.getNumTelefono1(),labels.getNumTelefono1Hint(), Constants.WIZARD, false).setPatternValidation(true, "^$|^[3|8|5|7|2]{1}\\d{7}$").setRequired(true);
        Page tieneOtroTelefono = new SingleFixedChoicePage(this,labels.getTieneOtroTelefono(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page tipoTel2 = new SingleFixedChoicePage(this,labels.getTipoTelefono2(),labels.getTipoTelefono2Hint(), Constants.WIZARD, false).setChoices(catTipo).setRequired(true);
        Page operadoraTel2 = new SingleFixedChoicePage(this,labels.getOperadoraTelefono2(),labels.getOperadoraTelefono2Hint(), Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);
        Page numeroTel2 = new TextPage(this,labels.getNumTelefono2(),labels.getNumTelefono2Hint(), Constants.WIZARD, false).setPatternValidation(true, "^$|^[3|8|5|7|2]{1}\\d{7}$").setRequired(true);
        Page verifTutor = new MultipleFixedChoicePage(this,labels.getVerifTutor(), "", Constants.WIZARD, false).setChoices(catVerificaTutor).setRequired(true);

        Page finTamizajeLabel = new LabelPage(this,labels.getFinTamizajeLabel(),"", Constants.WIZARD, true).setRequired(false);

		return new PageList(tipoIngreso, sexo, fechaNacimiento, aceptaTamizajePersona, razonNoParticipaPersona, otraRazonNoParticipaPersona,
                criteriosInclusion, vivienda, tiempoResidencia, dondeAsisteProblemasSalud, otroCentroSalud, puestoSalud, aceptaAtenderCentro, enfermedad, cualEnfermedad, oEnfCronica,
                enfCronicaAnio1, enfCronicaMes1, enfCronicaAnio2, enfCronicaMes2, enfCronicaAnio3, enfCronicaMes3, enfCronicaAnio4, enfCronicaMes4, enfCronicaAnio5, enfCronicaMes5, enfCronicaAnio6, enfCronicaMes6, enfCronicaAnio7, enfCronicaMes7,
                enfCronicaAnio8, enfCronicaMes8, enfCronicaAnio9, enfCronicaMes9, enfCronicaAnio10, enfCronicaMes10, enfCronicaAnio11, enfCronicaMes11, enfCronicaAnio12, enfCronicaMes12, enfCronicaAnio13, enfCronicaMes13, enfCronicaAnio14, enfCronicaMes14,
                tratamiento, cualTratamiento, otroTx, diagDengue, fechaDiagDengue, hospDengue, fechaHospDengue,
                asentimientoVerbal, aceptaCohorteDengue, razonNoAceptaDengue, otraRazonNoAceptaDengue,
                aceptaCohorteInfluenza, aceptaCohorteInfluenzaUO1, enfermedadInmuno, pretermino, razonNoAceptaInfluenza, otraRazonNoAceptaInfluenza,razonNoAceptaInfluenzaUO1, otraRazonNoAceptaInfluenzaUO1,
                casaPerteneceCohorte, codigoCasaCohorte, codigoNuevaCasaCohorte, nombre1JefeFamilia, nombre2JefeFamilia, apellido1JefeFamilia, apellido2JefeFamilia, barrio, manzana, direccion,
                codigoNuevoParticipante, nombre1, nombre2, apellido1, apellido2,
                nombre1Padre, nombre2Padre, apellido1Padre, apellido2Padre, nombre1Madre, nombre2Madre, apellido1Madre, apellido2Madre,
                nombre1Tutor, nombre2Tutor, apellido1Tutor, apellido2Tutor, relacionFamiliarTutor, otraRelacionFamTutor,
                participanteOTutorAlfabeto, testigoPresente, nombre1Testigo, nombre2Testigo, apellido1Testigo, apellido2Testigo,
                aceptaParteB, aceptaParteC, aceptaParteD, aceptaParteBInf, aceptaParteCInf, aceptaCohorteUO1ParteDCovid, razonNoAceptaParteD, otraRazonNoAceptaParteD, aceptaContactoFuturo,
                nombreContacto, barrioContacto, direccionContacto, tieneTelefono, tipoTel1, operadoraTel1, numeroTel1, tieneOtroTelefono, tipoTel2, operadoraTel2, numeroTel2, verifTutor,
                finTamizajeLabel);
    }

	public TamizajeFormLabels getLabels() {
		return labels;
	}

	public void setLabels(TamizajeFormLabels labels) {
		this.labels = labels;
	}
    
}
