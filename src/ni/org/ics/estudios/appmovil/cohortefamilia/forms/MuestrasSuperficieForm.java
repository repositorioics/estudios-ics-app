package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.Context;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.wizard.model.*;

import java.util.List;

/**
 * Created by Miguel Salinas on 5/16/2017.
 * V1.0
 */
public class MuestrasSuperficieForm extends AbstractWizardModel {

    int index = 0;
    private String[] catSiNo;
    private String[] catRazon;
    private String[] catObservacion;
    private String[] catPinchazos;

    private MuestrasSuperficieFormLabels labels;
    private EstudiosAdapter estudiosAdapter;

    public MuestrasSuperficieForm(Context context, String pass) {
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
        labels = new MuestrasSuperficieFormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = fillCatalog("CHF_CAT_SINO");

        estudiosAdapter.close();

        Page mx1 = new SingleFixedChoicePage(this, labels.getManecillaPuerta(), labels.getManecillaPuertaHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx1 = new TextPage(this, labels.getOsManecillaPuerta(), labels.getOsManecillaPuertaHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx1 = new BarcodePage(this, labels.getCodigoMx1(), labels.getManecillaPuerta(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.01.[0|1]$").setRequired(true);

        Page mx2 = new SingleFixedChoicePage(this, labels.getLlaveBanio(), labels.getLlaveBanioHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx2 = new TextPage(this, labels.getOsLlaveBanio(), labels.getOsLlaveBanioHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx2 = new BarcodePage(this, labels.getCodigoMx2(), labels.getLlaveBanio(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.02.[0|1]$").setRequired(true);

        Page mx3 = new SingleFixedChoicePage(this, labels.getManijaRefrigerador(), labels.getManijaRefrigeradorHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx3 = new TextPage(this, labels.getOsManijaRefrigerador(), labels.getOsManijaRefrigeradorHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx3 = new BarcodePage(this, labels.getCodigoMx3(), labels.getManijaRefrigerador(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.03.[0|1]$").setRequired(true);

        Page mx4 = new SingleFixedChoicePage(this, labels.getInterruptorLuz(), labels.getInterruptorLuzHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx4 = new TextPage(this, labels.getOsInterruptorLuz(), labels.getOsInterruptorLuzHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx4 = new BarcodePage(this, labels.getCodigoMx4(), labels.getInterruptorLuz(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.04.[0|1]$").setRequired(true);

        Page mx5 = new SingleFixedChoicePage(this, labels.getJugueteNino(), labels.getJugueteNinoHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx5 = new TextPage(this, labels.getOsJugueteNino(), labels.getOsJugueteNinoHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx5 = new BarcodePage(this, labels.getCodigoMx5(), labels.getJugueteNino(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.05.[0|1]$").setRequired(true);

        Page mx6 = new SingleFixedChoicePage(this, labels.getTelefonoCelular(), labels.getTelefonoCelularHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx6 = new TextPage(this, labels.getOsTelefonoCelular(), labels.getOsTelefonoCelularHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx6 = new BarcodePage(this, labels.getCodigoMx6(), labels.getTelefonoCelular(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.06.[0|1]$").setRequired(true);

        Page mx7 = new SingleFixedChoicePage(this, labels.getMesaComedor(), labels.getMesaComedorHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx7 = new TextPage(this, labels.getOsMesaComedor(), labels.getOsMesaComedorHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx7 = new BarcodePage(this, labels.getCodigoMx7(), labels.getMesaComedor(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.07.[0|1]$").setRequired(true);

        Page mx8 = new SingleFixedChoicePage(this, labels.getGrifoPrincipal(), labels.getGrifoPrincipalHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx8 = new TextPage(this, labels.getOsGrifoPrincipal(), labels.getOsGrifoPrincipalHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx8 = new BarcodePage(this, labels.getCodigoMx8(), labels.getGrifoPrincipal(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.08.[0|1]$").setRequired(true);

        Page mx9 = new SingleFixedChoicePage(this, labels.getEncimaRefrigerador(), labels.getEncimaRefrigeradorHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx9 = new TextPage(this, labels.getOsEncimaRefrigerador(), labels.getOsEncimaRefrigeradorHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx9 = new BarcodePage(this, labels.getCodigoMx9(), labels.getEncimaRefrigerador(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.09.[0|1]$").setRequired(true);

        Page mx10 = new SingleFixedChoicePage(this, labels.getMuebleCercaCama(), labels.getMuebleCercaCamaHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx10 = new TextPage(this, labels.getOsMuebleCercaCama(), labels.getOsMuebleCercaCamaHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx10 = new BarcodePage(this, labels.getCodigoMx10(), labels.getMuebleCercaCama(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.10.[0|1]$").setRequired(true);

        Page mx11 = new SingleFixedChoicePage(this, labels.getParedDetrasCama(), labels.getParedDetrasCamaHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx11 = new TextPage(this, labels.getOsParedDetrasCama(), labels.getOsParedDetrasCamaHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx11 = new BarcodePage(this, labels.getCodigoMx11(), labels.getParedDetrasCama(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.11.[0|1]$").setRequired(true);

        Page mx12 = new SingleFixedChoicePage(this, labels.getParedBanio(), labels.getParedBanioHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page osMx12 = new TextPage(this, labels.getOsParedBanio(), labels.getOsParedBanioHint(), Constants.WIZARD, false).setRequired(true);
        Page bcCodigoMx12 = new BarcodePage(this, labels.getCodigoMx12(), labels.getParedBanio(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.12.[0|1]$").setRequired(true);

        Page mx13 = new SingleFixedChoicePage(this, labels.getManos(), labels.getManosHint(), Constants.WIZARD, true).setChoices("Si").setRequired(true);
        Page bcCodigoMx13 = new BarcodePage(this, labels.getCodigoMx13(), labels.getManos(), Constants.WIZARD, true).setPatternValidation(true, "^CA.\\d{1,4}.\\d{2}.13.[0]$").setRequired(true);

        return new PageList(mx1, osMx1, bcCodigoMx1, mx2, osMx2, bcCodigoMx2, mx3, osMx3, bcCodigoMx3, mx4, osMx4, bcCodigoMx4, mx5, osMx5, bcCodigoMx5,
                mx6, osMx6, bcCodigoMx6, mx7, osMx7, bcCodigoMx7, mx8, osMx8, bcCodigoMx8, mx9, osMx9, bcCodigoMx9, mx10, osMx10, bcCodigoMx10,
                mx11, osMx11, bcCodigoMx11, mx12, osMx12, bcCodigoMx12, mx13, bcCodigoMx13);
    }
}
