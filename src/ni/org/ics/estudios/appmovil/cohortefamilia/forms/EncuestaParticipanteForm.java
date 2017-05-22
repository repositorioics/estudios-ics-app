package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.List;

/**
 * Created by Miguel Salinas on 5/18/2017.
 * V1.0
 */
public class EncuestaParticipanteForm extends AbstractWizardModel {

    int index = 0;
    private String[] catSiNo;
    private String[] catNivelEdu;
    private String[] catTipoTrab;
    private String[] catGrado;
    private String[] catTurno;
    private String[] catCuidan;
    private String[] catViveNino;
    private String[] catSiNoDe;
    private String[] catFuma;
    private String[] catMeses;

    private EstudiosAdapter estudiosAdapter;
    private EncuestaParticipanteFormLabels labels;

    public EncuestaParticipanteForm(Context context, String pass) {
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
        labels = new EncuestaParticipanteFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();

        catSiNo = fillCatalog("CHF_CAT_SINO");
        catNivelEdu = fillCatalog("CHF_CAT_NIV_EDU");
        catTipoTrab = fillCatalog("CHF_CAT_TIP_TRABAJO");
        catGrado = fillCatalog("CHF_CAT_GRD_EDU");
        catTurno = fillCatalog("CHF_CAT_TURNO");
        catCuidan = fillCatalog("CHF_CAT_CUIDAN_NINO");
        catViveNino = fillCatalog("CHF_CAT_VIVE_NINO");
        catSiNoDe = fillCatalog("CHF_CAT_SND");
        catFuma = fillCatalog("CHF_CAT_FREC_FUMA");
        catMeses = fillCatalog("CHF_CAT_MESES");

        estudiosAdapter.close();

        //Page lpInicio = new LabelPage(this, labels.getLabelInicio(), "", Constants.ROJO, true).setRequired(false);
        Page scEstaEmbarazada = new SingleFixedChoicePage(this, labels.getEstaEmbarazada(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npSemanasEmbarazo = new NumberPage(this, labels.getSemanasEmbarazo(), "", Constants.WIZARD, false).setRequired(true);
        Page scEsAlfabeto = new SingleFixedChoicePage(this, labels.getEsAlfabeto(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scNivelEducacion = new SingleFixedChoicePage(this, labels.getNivelEducacion(), "", Constants.WIZARD, false).setChoices(catNivelEdu).setRequired(true);
        Page scTrabaja = new SingleFixedChoicePage(this, labels.getTrabaja(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scTipoTrabajo = new SingleFixedChoicePage(this, labels.getTipoTrabajo(), "", Constants.WIZARD, false).setChoices(catTipoTrab).setRequired(true);
        Page tpOcupacionActual = new TextPage(this, labels.getOcupacionActual(), "", Constants.WIZARD, false).setRequired(true);
        Page scVaNinoEscuela = new SingleFixedChoicePage(this, labels.getVaNinoEscuela(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scGradoCursa = new SingleFixedChoicePage(this, labels.getGradoCursa(), "", Constants.WIZARD, false).setChoices(catGrado).setRequired(true);
        Page sTurno = new SingleFixedChoicePage(this, labels.getTurno(), "", Constants.WIZARD, false).setChoices(catTurno).setRequired(true);
        Page tpNombreCentroEstudio = new TextPage(this, labels.getNombreCentroEstudio(), "", Constants.WIZARD, false).setRequired(true);
        Page scDondeCuidanNino = new SingleFixedChoicePage(this, labels.getDondeCuidanNino(), "", Constants.WIZARD, false).setChoices(catCuidan).setRequired(true);
        Page scNinoTrabaja = new SingleFixedChoicePage(this, labels.getNinoTrabaja(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page tpOcupacionActualNino = new TextPage(this, labels.getOcupacionActualNino(), "", Constants.WIZARD, false).setRequired(true);
        Page npCantNinosLugarCuidan = new NumberPage(this, labels.getCantNinosLugarCuidan(), "", Constants.WIZARD, false).setRangeValidation(true,1,999).setRequired(true);
        Page scConQuienViveNino = new SingleFixedChoicePage(this, labels.getConQuienViveNino(), "", Constants.WIZARD, false).setChoices(catViveNino).setRequired(true);
        Page tpDescOtroViveNino = new TextPage(this, labels.getDescOtroViveNino(), "", Constants.WIZARD, false).setRequired(true);
        Page scPadreEnEstudio = new SingleFixedChoicePage(this, labels.getPadreEnEstudio(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page bpCodigoPadreEstudio = new BarcodePage(this, labels.getCodigoPadreEstudio(), "", Constants.WIZARD, false).setRequired(true);
        Page scPadreAlfabeto = new SingleFixedChoicePage(this, labels.getPadreAlfabeto(), "", Constants.WIZARD, false).setChoices(catSiNoDe).setRequired(true);
        Page scNivelEducacionPadre = new SingleFixedChoicePage(this, labels.getNivelEducacionPadre(), "", Constants.WIZARD, false).setChoices(catNivelEdu).setRequired(true);
        Page scTrabajaPadre = new SingleFixedChoicePage(this, labels.getTrabajaPadre(), "", Constants.WIZARD, false).setChoices(catSiNoDe).setRequired(true);
        Page scTipoTrabajoPadre = new SingleFixedChoicePage(this, labels.getTipoTrabajoPadre(), "", Constants.WIZARD, false).setChoices(catTipoTrab).setRequired(true);
        Page scMadreEnEstudio = new SingleFixedChoicePage(this, labels.getMadreEnEstudio(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page bpCodigoMadreEstudio = new BarcodePage(this, labels.getCodigoMadreEstudio(), "", Constants.WIZARD, false).setRequired(true);
        Page scMadreAlfabeto = new SingleFixedChoicePage(this, labels.getMadreAlfabeto(), "", Constants.WIZARD, false).setChoices(catSiNoDe).setRequired(true);
        Page scNivelEducacionMadre = new SingleFixedChoicePage(this, labels.getNivelEducacionMadre(), "", Constants.WIZARD, false).setChoices(catNivelEdu).setRequired(true);
        Page scTrabajaMadre = new SingleFixedChoicePage(this, labels.getTrabajaMadre(), "", Constants.WIZARD, false).setChoices(catSiNoDe).setRequired(true);
        Page scTipoTrabajoMadre = new SingleFixedChoicePage(this, labels.getTipoTrabajoMadre(), "", Constants.WIZARD, false).setChoices(catTipoTrab).setRequired(true);
        Page scFuma = new SingleFixedChoicePage(this, labels.getFuma(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scPeriodicidadFuma = new SingleFixedChoicePage(this, labels.getPeriodicidadFuma(), "", Constants.WIZARD, false).setChoices(catFuma).setRequired(true);
        Page npCantidadCigarrillos = new NumberPage(this, labels.getCantidadCigarrillos(), "", Constants.WIZARD, false).setRequired(true);
        Page scFumaDentroCasa = new SingleFixedChoicePage(this, labels.getFumaDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scTuberculosisPulmonarActual = new SingleFixedChoicePage(this, labels.getTuberculosisPulmonarActual(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);

        Page npAnioFechaDiagnosticoTubPulActual = new NumberPage(this, labels.getAnioFechaDiagnosticoTubPulActual(), "", Constants.WIZARD, false).setRequired(true);
        Page scMesFechaDiagnosticoTubPulActual = new SingleFixedChoicePage(this, labels.getMesFechaDiagnosticoTubPulActual(), "", Constants.WIZARD, false).setChoices(catMeses).setRequired(true);

        Page scTomaTratamientoTubPulActual = new SingleFixedChoicePage(this, labels.getTomaTratamientoTubPulActual(), "", Constants.WIZARD, false).setChoices(catSiNoDe).setRequired(true);
        Page scCompletoTratamientoTubPulActual = new SingleFixedChoicePage(this, labels.getCompletoTratamientoTubPulActual(), "", Constants.WIZARD, false).setChoices(catSiNoDe).setRequired(true);
        Page scTuberculosisPulmonarPasado = new SingleFixedChoicePage(this, labels.getTuberculosisPulmonarPasado(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);

        Page scFechaDiagnosticoTubPulPasadoSn = new SingleFixedChoicePage(this, labels.getFechaDiagnosticoTubPulPasadoSn(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npAnioFechaDiagnosticoTubPulPasado = new NumberPage(this, labels.getAnioFechaDiagnosticoTubPulPasado(), "", Constants.WIZARD, false).setRequired(true);
        Page scMesFechaDiagnosticoTubPulPasado = new SingleFixedChoicePage(this, labels.getMesFechaDiagnosticoTubPulPasado(), "", Constants.WIZARD, false).setChoices(catMeses).setRequired(true);

        Page scTomaTratamientoTubPulPasado = new SingleFixedChoicePage(this, labels.getTomaTratamientoTubPulPasado(), "", Constants.WIZARD, false).setChoices(catSiNoDe).setRequired(true);
        Page scCompletoTratamientoTubPulPasado = new SingleFixedChoicePage(this, labels.getCompletoTratamientoTubPulPasado(), "", Constants.WIZARD, false).setChoices(catSiNoDe).setRequired(true);
        Page scAlergiaRespiratoria = new SingleFixedChoicePage(this, labels.getAlergiaRespiratoria(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page scCardiopatia = new SingleFixedChoicePage(this, labels.getCardiopatia(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page scEnfermedadPulmonarOC = new SingleFixedChoicePage(this, labels.getEnfermedadPulmonarOC(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page scDiabetes = new SingleFixedChoicePage(this, labels.getDiabetes(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page scPresionAlta = new SingleFixedChoicePage(this, labels.getPresionAlta(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page scAsma = new SingleFixedChoicePage(this, labels.getAsma(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page scSilbidoRespirarPechoApretado = new SingleFixedChoicePage(this, labels.getSilbidoRespirarPechoApretado(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page scTosSinFiebreResfriado = new SingleFixedChoicePage(this, labels.getTosSinFiebreResfriado(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page scUsaInhaladoresSpray = new SingleFixedChoicePage(this, labels.getUsaInhaladoresSpray(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page scCrisisAsma = new SingleFixedChoicePage(this, labels.getCrisisAsma(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page npCantidadCrisisAsma = new NumberPage(this, labels.getCantidadCrisisAsma(), "", Constants.WIZARD, false).setRequired(true);
        Page npVecesEnfermoEnfermedadesRes = new NumberPage(this, labels.getVecesEnfermoEnfermedadesRes(), "", Constants.WIZARD, true).setRequired(true);
        Page scOtrasEnfermedades = new SingleFixedChoicePage(this, labels.getOtrasEnfermedades(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page descOtrasEnfermedades = new TextPage(this, labels.getDescOtrasEnfermedades(), "", Constants.WIZARD, false).setRequired(true);
        Page scVacunaInfluenza = new SingleFixedChoicePage(this, labels.getVacunaInfluenza(), "", Constants.WIZARD, true).setChoices(catSiNoDe).setRequired(true);
        Page anioVacunaInfluenza = new NumberPage(this, labels.getAnioVacunaInfluenza(), "", Constants.WIZARD, false).setRequired(true);

        return new PageList(scEstaEmbarazada, npSemanasEmbarazo, scEsAlfabeto, scNivelEducacion, scTrabaja, scTipoTrabajo, tpOcupacionActual, scVaNinoEscuela, scGradoCursa, sTurno, tpNombreCentroEstudio, scDondeCuidanNino, scNinoTrabaja, tpOcupacionActualNino,
                npCantNinosLugarCuidan, scConQuienViveNino, tpDescOtroViveNino, scPadreEnEstudio, bpCodigoPadreEstudio, scPadreAlfabeto, scNivelEducacionPadre, scTrabajaPadre, scTipoTrabajoPadre, scMadreEnEstudio, bpCodigoMadreEstudio, scMadreAlfabeto,
                scNivelEducacionMadre, scTrabajaMadre, scTipoTrabajoMadre, scFuma, scPeriodicidadFuma, npCantidadCigarrillos, scFumaDentroCasa, scTuberculosisPulmonarActual, npAnioFechaDiagnosticoTubPulActual, scMesFechaDiagnosticoTubPulActual, scTomaTratamientoTubPulActual, scCompletoTratamientoTubPulActual,
                scTuberculosisPulmonarPasado, scFechaDiagnosticoTubPulPasadoSn, npAnioFechaDiagnosticoTubPulPasado, scMesFechaDiagnosticoTubPulPasado, scTomaTratamientoTubPulPasado, scCompletoTratamientoTubPulPasado, scAlergiaRespiratoria, scCardiopatia, scEnfermedadPulmonarOC, scDiabetes, scPresionAlta, scAsma, scSilbidoRespirarPechoApretado,
                scTosSinFiebreResfriado, scUsaInhaladoresSpray, scCrisisAsma, npCantidadCrisisAsma, npVecesEnfermoEnfermedadesRes, scOtrasEnfermedades, descOtrasEnfermedades, scVacunaInfluenza, anioVacunaInfluenza);
    }

    public EncuestaParticipanteFormLabels getLabels() {
        return labels;
    }

    public void setLabels(EncuestaParticipanteFormLabels labels) {
        this.labels = labels;
    }
}
