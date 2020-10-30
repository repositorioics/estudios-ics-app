package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.covid19.forms.ConsCHFParteECovid19FormLabels;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.List;

/**
 * Created by Miguel Salinas on 02/08/2018.
 * V1.0
 */
public class ConsSeroprevalenciaForm extends AbstractWizardModel {

    int index = 0;
    private ConsSeroprevalenciaFormLabels labels;
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

    public ConsSeroprevalenciaForm(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new ConsSeroprevalenciaFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);

        estudiosAdapter.open();
        String[] catSiNo = fillCatalog("CHF_CAT_SINO");
        String[] catVisNoExitosa = fillCatalog("CP_CAT_NV");
        String[] catRelacionFamiliar = fillCatalog("CP_CAT_RFTUTOR");
        String[] catDifTutor = fillCatalog("CP_CAT_DIFTUTOR");
        String[] catVerificaTutor = fillCatalog("CP_CAT_VERIFTUTOR");
        String[] catMotivoRechazo = fillCatalog("CPD_CAT_MOTRECHAZO");
        String[] catRazonNoParticipaPersona = fillCatalog("CHF_CAT_NPP");
        estudiosAdapter.close();

        Page visExit = new SingleFixedChoicePage(this,labels.getVisExit(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page visNoExit = new SingleFixedChoicePage(this,labels.getRazonVisNoExit(), "", Constants.WIZARD, false).setChoices(catVisNoExitosa).setRequired(true);
        Page otraRazonVisitaNoExitosa = new TextPage(this,labels.getOtraRazonVisitaNoExitosa(), labels.getOtraRazonVisitaNoExitosaHint(), Constants.WIZARD, false).setRequired(true);
        Page personaCasa = new TextPage(this,labels.getPersonaCasa(), labels.getPersonaCasaHint(), Constants.WIZARD, false).setRequired(true);
        Page relacionFamPersonaCasa = new SingleFixedChoicePage(this,labels.getRelacionFamPersonaCasa(), labels.getRelacionFamPersonaCasaHint(), Constants.WIZARD, false).setChoices(catRelacionFamiliar).setRequired(true);
        Page otraRelacionPersonaCasa = new TextPage(this,labels.getOtraRelacionPersonaCasa(), "", Constants.WIZARD, false).setRequired(true);
        Page telefonoPersonaCasa = new NumberPage(this,labels.getTelefonoPersonaCasa(), "", Constants.WIZARD, false).setRangeValidation(true, Constants.MINIMO_NUM_CONVENCIONAL, Constants.MAXIMO_NUM_CELULAR).setRequired(false);

        Page aceptaTamizajePersona = new SingleFixedChoicePage(this,labels.getAceptaTamizajePersona(), labels.getAceptaTamizajePersonaHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoParticipaPersona = new SingleFixedChoicePage(this,labels.getRazonNoParticipaPersona(), labels.getRazonNoParticipaPersonaHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoParticipaPersona = new TextPage(this,labels.getOtraRazonNoParticipaPersona(),labels.getOtraRazonNoParticipaPersonaHint(),Constants.WIZARD, false).setRequired(true);
        Page aceptaSeroprevalencia = new SingleFixedChoicePage(this,labels.getAceptaSeroprevalencia(), labels.getAceptaSeroprevalenciaHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaSeroprevalencia = new SingleFixedChoicePage(this,labels.getRazonNoAceptaSeroprevalencia(), labels.getRazonNoAceptaSeroprevalenciaHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaSeroprevalencia = new TextPage(this,labels.getOtraRazonNoAceptaSeroprevalencia(),labels.getOtraRazonNoAceptaSeroprevalenciaHint(),Constants.WIZARD,false).setRequired(true);

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

        Page verifTutor = new MultipleFixedChoicePage(this,labels.getVerifTutor(), "", Constants.WIZARD, false).setChoices(catVerificaTutor).setRequired(true);

        return new PageList(visExit, visNoExit, otraRazonVisitaNoExitosa, personaCasa, relacionFamPersonaCasa, otraRelacionPersonaCasa, telefonoPersonaCasa,
                aceptaTamizajePersona, razonNoParticipaPersona, otraRazonNoParticipaPersona, aceptaSeroprevalencia, razonNoAceptaSeroprevalencia, otraRazonNoAceptaSeroprevalencia,
                tutor, mismoTutorSN, nombrept, nombrept2, apellidopt, apellidopt2, relacionFam, otraRelacionFam, motivoDifTutor, otroMotivoDifTutor, alfabetoTutor, testigoSN, nombretest1, nombretest2, apellidotest1, apellidotest2, verifTutor);
    }
}
