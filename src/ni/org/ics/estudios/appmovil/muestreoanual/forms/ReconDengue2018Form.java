package ni.org.ics.estudios.appmovil.muestreoanual.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.List;

/**
 * Created by Miguel Salinas on 02/08/2018.
 * V1.0
 */
public class ReconDengue2018Form extends AbstractWizardModel {

    int index = 0;
    private ReconDengue2018FormLabels labels;
    private EstudiosAdapter estudiosAdapter;

    private String[] fillCatalog(String codigoCatalogo){
        return estudiosAdapter.getSpanishMessageResources(CatalogosDBConstants.catRoot + "='"+codigoCatalogo+"'", CatalogosDBConstants.order);
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

    public ReconDengue2018Form(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new ReconDengue2018FormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);

        estudiosAdapter.open();
        String[] catSiNo = fillCatalog("CHF_CAT_SINO");
        String[] catVisNoExitosa = fillCatalog("CP_CAT_NV");
        String[] catRelacionFamiliar = fillCatalog("CP_CAT_RFTUTOR");
        String[] catCritInclusion = fillCatalog("CP_CAT_CI");
        String[] catTiempoResid = fillCatalog("CP_CAT_TR");
        String[] catTipoViv = fillCatalog("CP_CAT_TV");
        String[] catEnfCron = fillCatalog("ENFERMEDAD_CRN");
        String[] catTramiento = fillCatalog("CPD_CAT_TRATAMIENTO");
        String[] catDondeAsisteProblemasSalud = fillCatalog("CHF_CAT_DONDEASISTE");
        String[] catPuestoSalud = fillCatalog("CHF_CAT_PUESTO");
        String[] catMotivoRechazo = fillCatalog("CPD_CAT_MOTRECHAZO");
        String[] catDifTutor = fillCatalog("CP_CAT_DIFTUTOR");
        String[] catBarrios = fillBarrios();
        String[] catTipoTel = fillCatalog("CAT_TIPO_TEL");
        String[] catOperadora = fillCatalog("CAT_OPER_TEL");
        String[] catVerificaTutor = fillCatalog("CP_CAT_VERIFTUTOR");
        String[] catNoGeoref = fillCatalog("CP_CAT_NOGEO");
        String[] catMeses = fillCatalog("CHF_CAT_MESES");
        estudiosAdapter.close();

        Page visExit = new SingleFixedChoicePage(this,labels.getVisExit(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page visNoExit = new SingleFixedChoicePage(this,labels.getRazonVisNoExit(), "", Constants.WIZARD, false).setChoices(catVisNoExitosa).setRequired(true);
        Page dejoCarta = new SingleFixedChoicePage(this,labels.getDejoCarta(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page noDejoCarta = new LabelPage(this,labels.getNoDejoCarta(), "", Constants.ROJO, false).setRequired(false);
        Page personaDejoCarta = new TextPage(this,labels.getPersonaDejoCarta(), "", Constants.WIZARD, false).setRequired(true);
        Page relFamPersonaDejoCarta = new SingleFixedChoicePage(this,labels.getRelFamPersonaDejoCarta(), labels.getRelFamPersonaDejoCartaHint(), Constants.WIZARD, false).setChoices(catRelacionFamiliar).setRequired(true);
        Page personaCasa = new TextPage(this,labels.getPersonaCasa(), labels.getPersonaCasaHint(), Constants.WIZARD, false).setRequired(true);
        Page relacionFamPersonaCasa = new SingleFixedChoicePage(this,labels.getRelacionFamPersonaCasa(), labels.getRelacionFamPersonaCasaHint(), Constants.WIZARD, false).setChoices(catRelacionFamiliar).setRequired(true);
        Page otraRelacionPersonaCasa = new TextPage(this,labels.getOtraRelacionPersonaCasa(), "", Constants.WIZARD, false).setRequired(true);
        Page telefonoPersonaCasa = new NumberPage(this,labels.getTelefonoPersonaCasa(), "", Constants.WIZARD, false).setRangeValidation(true, Constants.MINIMO_NUM_CONVENCIONAL, Constants.MAXIMO_NUM_CELULAR).setRequired(false);
        Page aceptaCohorteDengue = new SingleFixedChoicePage(this,labels.getAceptaParticipar(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaDengue = new SingleFixedChoicePage(this,labels.getRazonNoAceptaDengue(), labels.getRazonNoAceptaDengueHint(), Constants.WIZARD, false).setChoices(catMotivoRechazo).setRequired(true);
        Page otraRazonNoAceptaDengue = new TextPage(this,labels.getOtraRazonNoAceptaDengue(),labels.getOtraRazonNoAceptaDengueHint(),Constants.WIZARD, false).setRequired(true);

        Page emancipado = new SingleFixedChoicePage(this,labels.getEmancipado(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonEmancipacion = new SingleFixedChoicePage(this,labels.getRazonEmancipacion(), "", Constants.WIZARD, false).setRequired(true);
        Page otraRazonEmancipacion = new TextPage(this,labels.getOtraRazonEmancipacion(), "",Constants.WIZARD, false).setRequired(true);
        Page incDen = new MultipleFixedChoicePage(this,labels.getIncDen(), labels.getIncDenHint(), Constants.WIZARD, false).setChoices(catCritInclusion).setRequired(true);
        Page vivienda = new SingleFixedChoicePage(this,labels.getVivienda(), "", Constants.WIZARD, false).setChoices(catTipoViv).setRequired(true);
        Page tiempoResidencia = new SingleFixedChoicePage(this,labels.getTiempoResidencia(), "", Constants.WIZARD, false).setChoices(catTiempoResid).setRequired(true);
        Page aceptaAtenderCentro = new SingleFixedChoicePage(this,labels.getAceptaAtenderCentro(), labels.getAceptaAtenderCentroHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page enfCronSN = new SingleFixedChoicePage(this,labels.getEnfCronSN(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page enfCronica = new MultipleFixedChoicePage(this,labels.getEnfCronica(), "", Constants.WIZARD, false).setChoices(catEnfCron).setRequired(true);
        Page oEnfCronica = new TextPage(this,labels.getoEnfCronica(), "", Constants.WIZARD, false).setRequired(true);
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

        Page tomaTx = new SingleFixedChoicePage(this,labels.getTomaTx(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page cualesTx = new MultipleFixedChoicePage(this,labels.getCualesTx(), "", Constants.WIZARD, false).setChoices(catTramiento).setRequired(true);
        Page otroTx = new TextPage(this,labels.getOtroTx(), "", Constants.WIZARD, false).setRequired(true);

        Page asiste = new SingleFixedChoicePage(this,labels.getAsiste(), "", Constants.WIZARD, true).setChoices(catDondeAsisteProblemasSalud).setRequired(true);
        Page ocentrosalud = new TextPage(this,labels.getOcentrosalud(), "", Constants.WIZARD, false).setRequired(true);
        Page puestosalud = new SingleFixedChoicePage(this,labels.getPuestosalud(), "", Constants.WIZARD, false).setChoices(catPuestoSalud).setRequired(true);

        Page parteADen = new SingleFixedChoicePage(this,labels.getParteADen(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page rechDen = new SingleFixedChoicePage(this,labels.getRechDen(), "", Constants.WIZARD, false).setChoices(catMotivoRechazo).setRequired(true);
        Page otroRechDen = new TextPage(this,labels.getOtroRechDen(), "", Constants.WIZARD, false).setRequired(true);
        Page aceptaContactoFuturo = new SingleFixedChoicePage(this,labels.getAceptaContactoFuturo(), labels.getAceptaContactoFuturoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page parteBDen = new SingleFixedChoicePage(this,labels.getParteBDen(), labels.getParteBDenHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page parteCDen = new SingleFixedChoicePage(this,labels.getParteCDen(), labels.getParteCDenHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        //MA2022. Version de Carta no incluye parte D. Solo A, B, C
        /*Page parteDDen = new SingleFixedChoicePage(this,labels.getParteDDen(), labels.getParteDDenHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page rechDenExtEdad = new SingleFixedChoicePage(this,labels.getRechDenExtEdad(), "", Constants.WIZARD, false).setChoices(catMotivoRechazo).setRequired(true);
        Page otroRechDenExtEdad = new TextPage(this,labels.getOtroRechDenExtEdad(), "", Constants.WIZARD, false).setRequired(true);
*/
        Page asentimiento = new SingleFixedChoicePage(this,labels.getAsentimiento(), labels.getAsentimientoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page tutor = new LabelPage(this,labels.getTutor(), "", Constants.WIZARD, false).setRequired(false);
        Page mismoTutorSN = new SingleFixedChoicePage(this,labels.getMismoTutorSN(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page nombrept = new TextPage(this,labels.getNombrept(), "", Constants.WIZARD, false).setRequired(true);
        Page nombrept2 = new TextPage(this,labels.getNombrept2(), "", Constants.WIZARD, false).setRequired(false);
        Page apellidopt = new TextPage(this,labels.getApellidopt(), "", Constants.WIZARD, false).setRequired(true);
        Page apellidopt2 = new TextPage(this,labels.getApellidopt2(), "", Constants.WIZARD, false).setRequired(false);
        Page relacionFam = new SingleFixedChoicePage(this,labels.getRelacionFam(), "", Constants.WIZARD, false).setChoices(catRelacionFamiliar).setRequired(true);
        Page otraRelacionFam = new TextPage(this,labels.getOtraRelacionFam(), "", Constants.WIZARD, false).setRequired(true);
        Page motivoDifTutor = new SingleFixedChoicePage(this,labels.getMotivoDifTutor(), "", Constants.WIZARD, false).setChoices(catDifTutor).setRequired(true);
        Page otroMotivoDifTutor = new TextPage(this,labels.getOtroMotivoDifTutor(), "", Constants.WIZARD, false).setRequired(true);

        Page alfabetoTutor = new SingleFixedChoicePage(this,labels.getAlfabetoTutor(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page testigoSN = new SingleFixedChoicePage(this,labels.getTestigoSN(), labels.getTestigoSNHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page nombretest1 = new TextPage(this,labels.getNombretest1(), "", Constants.WIZARD, false).setRequired(true);
        Page nombretest2 = new TextPage(this,labels.getNombretest2(), "", Constants.WIZARD, false).setRequired(false);
        Page apellidotest1 = new TextPage(this,labels.getApellidotest1(), "", Constants.WIZARD, false).setRequired(true);
        Page apellidotest2 = new TextPage(this,labels.getApellidotest2(), "", Constants.WIZARD, false).setRequired(false);

        Page domicilio = new LabelPage(this,labels.getDomicilio(), "", Constants.WIZARD, false).setRequired(false);
        Page cmDomicilio = new SingleFixedChoicePage(this,labels.getCmDomicilio(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page notaCmDomicilio = new LabelPage(this,labels.getNotaCmDomicilio(), "", Constants.ROJO, false).setRequired(false);
        //Page barrio = new SingleFixedChoicePage(this,labels.getBarrio(), "", Constants.WIZARD, false).setChoices(catBarrios).setRequired(true);
        //Page otrobarrio = new TextPage(this,labels.getOtrobarrio(), "", Constants.WIZARD, false).setRequired(true);
        //Page dire = new TextPage(this,labels.getDire(), "", Constants.WIZARD, false).setRequired(true);
        //Page autsup = new SingleFixedChoicePage(this,labels.getAutsup(), labels.getAutsupHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page telefono1SN = new SingleFixedChoicePage(this,labels.getTelefono1SN(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page telefonoClasif1 = new SingleFixedChoicePage(this,labels.getTelefonoClasif1(), "", Constants.WIZARD, false).setChoices(catTipoTel).setRequired(true);
        Page telefonoCel1 = new NumberPage(this,labels.getTelefonoCel1(), "", Constants.WIZARD, false).setRequired(true);
        Page telefonoOper1 = new SingleFixedChoicePage(this,labels.getTelefonoOper1(), "", Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);
        Page telefono2SN = new SingleFixedChoicePage(this,labels.getTelefono2SN(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page telefonoClasif2 = new SingleFixedChoicePage(this,labels.getTelefonoClasif2(), "", Constants.WIZARD, false).setChoices(catTipoTel).setRequired(true);
        Page telefonoCel2 = new NumberPage(this,labels.getTelefonoCel2(), "", Constants.WIZARD, false).setRequired(true);
        Page telefonoOper2 = new SingleFixedChoicePage(this,labels.getTelefonoOper2(), "", Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);
        Page telefono3SN = new SingleFixedChoicePage(this,labels.getTelefono3SN(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page telefonoClasif3 = new SingleFixedChoicePage(this,labels.getTelefonoClasif3(), "", Constants.WIZARD, false).setChoices(catTipoTel).setRequired(true);
        Page telefonoCel3 = new NumberPage(this,labels.getTelefonoCel3(), "", Constants.WIZARD, false).setRequired(true);
        Page telefonoOper3 = new SingleFixedChoicePage(this,labels.getTelefonoOper3(), "", Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);

        Page jefeFam = new LabelPage(this,labels.getJefeFam(), "", Constants.WIZARD, false).setRequired(false);
        Page cambiarJefe = new SingleFixedChoicePage(this,labels.getCambiarJefe(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page jefenom = new TextPage(this,labels.getJefenom(), "", Constants.WIZARD, false).setRequired(true);
        Page jefenom2 = new TextPage(this,labels.getJefenom2(), "", Constants.WIZARD, false).setRequired(false);
        Page jefeap = new TextPage(this,labels.getJefeap(), "", Constants.WIZARD, false).setRequired(true);
        Page jefeap2 = new TextPage(this,labels.getJefeap2(), "", Constants.WIZARD, false).setRequired(false);

        Page nomContacto = new TextPage(this,labels.getNomContacto(), "", Constants.WIZARD, false).setRequired(true);
        Page barrioContacto = new SingleFixedChoicePage(this,labels.getBarrioContacto(), "", Constants.WIZARD, false).setChoices(catBarrios).setRequired(true);
        Page otrobarrioContacto = new TextPage(this,labels.getOtrobarrioContacto(), "", Constants.WIZARD, false).setRequired(true);
        Page direContacto = new TextPage(this,labels.getDireContacto(), "", Constants.WIZARD, false).setRequired(true);
        Page telContacto1SN = new SingleFixedChoicePage(this,labels.getTelContacto1SN(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page telContacto1 = new SingleFixedChoicePage(this,labels.getTelContacto1(), "", Constants.WIZARD, false).setChoices(catTipoTel).setRequired(true);
        Page telContactoCel1 = new NumberPage(this,labels.getTelContactoCel1(), "", Constants.WIZARD, false).setRequired(true);
        Page telContactoOper1 = new SingleFixedChoicePage(this,labels.getTelContactoOper1(), "", Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);
        Page telContacto2SN = new SingleFixedChoicePage(this,labels.getTelContacto2SN(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page telContactoClasif2 = new SingleFixedChoicePage(this,labels.getTelContactoClasif2(), "", Constants.WIZARD, false).setChoices(catTipoTel).setRequired(true);
        Page telContactoCel2 = new NumberPage(this,labels.getTelContactoCel2(), "", Constants.WIZARD, false).setRequired(true);
        Page telContactoOper2 = new SingleFixedChoicePage(this,labels.getTelContactoOper2(), "", Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);

        Page padre = new LabelPage(this,labels.getPadre(), "", Constants.WIZARD, false).setRequired(false);
        Page cambiarPadre = new SingleFixedChoicePage(this,labels.getCambiarPadre(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page nombrepadre = new TextPage(this,labels.getNombrepadre(), "", Constants.WIZARD, false).setRequired(true);
        Page nombrepadre2 = new TextPage(this,labels.getNombrepadre2(), "", Constants.WIZARD, false).setRequired(false);
        Page apellidopadre = new TextPage(this,labels.getApellidopadre(), "", Constants.WIZARD, false).setRequired(true);
        Page apellidopadre2 = new TextPage(this,labels.getApellidopadre2(), "", Constants.WIZARD, false).setRequired(false);
        Page madre = new LabelPage(this,labels.getMadre(), "", Constants.WIZARD, false).setRequired(false);
        Page cambiarMadre = new SingleFixedChoicePage(this,labels.getCambiarMadre(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page nombremadre = new TextPage(this,labels.getNombremadre(), "", Constants.WIZARD, false).setRequired(true);
        Page nombremadre2 = new TextPage(this,labels.getNombremadre2(), "", Constants.WIZARD, false).setRequired(false);
        Page apellidomadre = new TextPage(this,labels.getApellidomadre(), "", Constants.WIZARD, false).setRequired(true);
        Page apellidomadre2 = new TextPage(this,labels.getApellidomadre2(), "", Constants.WIZARD, false).setRequired(false);
        Page verifTutor = new MultipleFixedChoicePage(this,labels.getVerifTutor(), "", Constants.WIZARD, false).setChoices(catVerificaTutor).setRequired(true);

        //Page georef = new SingleFixedChoicePage(this,labels.getGeoref(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        //Page manzana = new NumberPage(this,labels.getManzana(), "", Constants.WIZARD, false).setRangeValidation(true, 0, 88).setRequired(true);
        //Page georef_razon = new SingleFixedChoicePage(this,labels.getGeoref_razon(), labels.getGeoref_razonHint(), Constants.WIZARD, false).setChoices(catNoGeoref).setRequired(true);
        //Page georef_otraRazon = new TextPage(this,labels.getGeoref_otraRazon(), labels.getGeoref_otraRazonHint(), Constants.WIZARD, false).setRequired(true);

        return new PageList(visExit, visNoExit, dejoCarta, noDejoCarta, personaDejoCarta, relFamPersonaDejoCarta, personaCasa, relacionFamPersonaCasa, otraRelacionPersonaCasa, telefonoPersonaCasa,
                aceptaCohorteDengue, razonNoAceptaDengue, otraRazonNoAceptaDengue, emancipado, razonEmancipacion, otraRazonEmancipacion, incDen, vivienda, tiempoResidencia, aceptaAtenderCentro, enfCronSN, enfCronica, oEnfCronica,
                enfCronicaAnio1, enfCronicaMes1, enfCronicaAnio2, enfCronicaMes2, enfCronicaAnio3, enfCronicaMes3, enfCronicaAnio4, enfCronicaMes4, enfCronicaAnio5, enfCronicaMes5, enfCronicaAnio6, enfCronicaMes6, enfCronicaAnio7, enfCronicaMes7,
                enfCronicaAnio8, enfCronicaMes8, enfCronicaAnio9, enfCronicaMes9, enfCronicaAnio10, enfCronicaMes10, enfCronicaAnio11, enfCronicaMes11, enfCronicaAnio12, enfCronicaMes12, enfCronicaAnio13, enfCronicaMes13, enfCronicaAnio14, enfCronicaMes14,
                tomaTx, cualesTx, otroTx, asiste, ocentrosalud, puestosalud, parteADen, rechDen, otroRechDen, aceptaContactoFuturo, parteBDen, parteCDen, /*parteDDen, rechDenExtEdad, otroRechDenExtEdad,*/ asentimiento,
                tutor, mismoTutorSN, nombrept, nombrept2, apellidopt, apellidopt2, relacionFam, otraRelacionFam, motivoDifTutor, otroMotivoDifTutor, alfabetoTutor, testigoSN, nombretest1, nombretest2, apellidotest1, apellidotest2,
                domicilio, cmDomicilio, notaCmDomicilio,//barrio, otrobarrio, dire, autsup,
                telefono1SN, telefonoClasif1, telefonoCel1, telefonoOper1, telefono2SN, telefonoClasif2, telefonoCel2, telefonoOper2, telefono3SN,
                telefonoClasif3, telefonoCel3, telefonoOper3, jefeFam, cambiarJefe, jefenom, jefenom2, jefeap, jefeap2, nomContacto, barrioContacto, otrobarrioContacto, direContacto,
                telContacto1SN, telContacto1, telContactoCel1, telContactoOper1, telContacto2SN, telContactoClasif2, telContactoCel2, telContactoOper2, padre, cambiarPadre,
                nombrepadre, nombrepadre2, apellidopadre, apellidopadre2, madre, cambiarMadre, nombremadre, nombremadre2, apellidomadre, apellidomadre2, verifTutor);
                //,georef, manzana, georef_razon, georef_otraRazon);
    }
}
