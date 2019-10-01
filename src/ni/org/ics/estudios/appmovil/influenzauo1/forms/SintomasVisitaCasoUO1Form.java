package ni.org.ics.estudios.appmovil.influenzauo1.forms;

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

public class SintomasVisitaCasoUO1Form extends AbstractWizardModel {

    private String[] catSiNoDesNa;
    private String[] catIntensidad;
    private SintomasVisitaCasoUO1FormLabels labels;
    private EstudiosAdapter estudiosAdapter;

    public SintomasVisitaCasoUO1Form(Context context, String pass){
        super(context,pass);
    }

    private String[] fillCatalog(String codigoCatalogo){
        int index = 0;
        String[] catalogo;
        List<MessageResource> mCatalogo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='"+codigoCatalogo+"'", CatalogosDBConstants.order);
        catalogo = new String[mCatalogo.size()];
        for (MessageResource message: mCatalogo){
            catalogo[index] = message.getSpanish();
            index++;
        }
        return catalogo;
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new SintomasVisitaCasoUO1FormLabels();
        this.estudiosAdapter = new EstudiosAdapter(mContext,mPass,false,false);

        estudiosAdapter.open();
        catSiNoDesNa = fillCatalog("UO1_CAT_SNDNA");
        catIntensidad = fillCatalog("CAT_INTENSIDAD_SIN");
        String[] catDias = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        estudiosAdapter.close();
        DateMidnight dmDesde = DateMidnight.parse("01/01/1900", DateTimeFormat.forPattern("dd/MM/yyyy"));
        DateMidnight dmHasta = new DateMidnight(new Date().getTime());

        Page fechaSintomas = new NewDatePage(this,labels.getFechaSintomas(), "", Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
        Page dia = new SingleFixedChoicePage(this,labels.getDia(),"", Constants.WIZARD, true).setChoices(catDias).setRequired(true);
        Page consultaInicial = new SingleFixedChoicePage(this,labels.getConsultaInicial(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page fiebre = new SingleFixedChoicePage(this,labels.getFiebre(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page fiebreIntensidad = new SingleFixedChoicePage(this,labels.getFiebreIntensidad(),"", Constants.WIZARD, false).setChoices(catIntensidad).setRequired(true);
        Page tos = new SingleFixedChoicePage(this,labels.getTos(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page tosIntensidad = new SingleFixedChoicePage(this,labels.getTosIntensidad(),"", Constants.WIZARD, false).setChoices(catIntensidad).setRequired(true);
        Page secrecionNasal = new SingleFixedChoicePage(this,labels.getSecrecionNasal(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page secrecionNasalIntensidad = new SingleFixedChoicePage(this,labels.getSecrecionNasalIntensidad(),"", Constants.WIZARD, false).setChoices(catIntensidad).setRequired(true);
        Page dolorGarganta = new SingleFixedChoicePage(this,labels.getDolorGarganta(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page dolorGargantaIntensidad = new SingleFixedChoicePage(this,labels.getDolorGargantaIntensidad(),"", Constants.WIZARD, false).setChoices(catIntensidad).setRequired(true);
        Page congestionNasal = new SingleFixedChoicePage(this,labels.getCongestionNasal(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page dolorCabeza = new SingleFixedChoicePage(this,labels.getDolorCabeza(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page dolorCabezaIntensidad = new SingleFixedChoicePage(this,labels.getDolorCabezaIntensidad(),"", Constants.WIZARD, false).setChoices(catIntensidad).setRequired(true);
        Page faltaApetito = new SingleFixedChoicePage(this,labels.getFaltaApetito(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page dolorMuscular = new SingleFixedChoicePage(this,labels.getDolorMuscular(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page dolorMuscularIntensidad = new SingleFixedChoicePage(this,labels.getDolorMuscularIntensidad(),"", Constants.WIZARD, false).setChoices(catIntensidad).setRequired(true);
        Page dolorArticular = new SingleFixedChoicePage(this,labels.getDolorArticular(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page dolorArticularIntensidad = new SingleFixedChoicePage(this,labels.getDolorArticularIntensidad(),"", Constants.WIZARD, false).setChoices(catIntensidad).setRequired(true);
        Page dolorOido = new SingleFixedChoicePage(this,labels.getDolorOido(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page respiracionRapida = new SingleFixedChoicePage(this,labels.getRespiracionRapida(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page dificultadRespiratoria = new SingleFixedChoicePage(this,labels.getDificultadRespiratoria(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page faltaEscuelta = new SingleFixedChoicePage(this,labels.getFaltaEscuelta(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);
        Page quedoCama = new SingleFixedChoicePage(this,labels.getQuedoCama(),"", Constants.WIZARD, true).setChoices(catSiNoDesNa).setRequired(true);

        return new PageList(fechaSintomas, dia, consultaInicial, fiebre, fiebreIntensidad, tos, tosIntensidad, secrecionNasal, secrecionNasalIntensidad, dolorGarganta, dolorGargantaIntensidad, congestionNasal, dolorCabeza, dolorCabezaIntensidad, faltaApetito, dolorMuscular, dolorMuscularIntensidad, dolorArticular, dolorArticularIntensidad, dolorOido, respiracionRapida, dificultadRespiratoria, faltaEscuelta, quedoCama);
    }
}
