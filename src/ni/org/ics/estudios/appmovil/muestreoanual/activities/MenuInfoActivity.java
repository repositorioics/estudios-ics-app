package ni.org.ics.estudios.appmovil.muestreoanual.activities;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaMuestrasActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevoObsequioActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevoRecon18AniosActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.dto.DatosCHF;
import ni.org.ics.estudios.appmovil.covid19.activities.enterdata.*;
import ni.org.ics.estudios.appmovil.covid19.dto.DatosCovid19;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.DatosCoordenadas;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.covid19.CasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.ParticipanteCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaVacunaUO1;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.*;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.domain.users.UserPermissions;
import ni.org.ics.estudios.appmovil.influenzauo1.dto.DatosUO1;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.MenuInfoAdapter;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.seroprevalencia.activities.NuevoConsentimientoSAActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

public class MenuInfoActivity extends AbstractAsyncActivity {

    private Integer codigo;
    private static Participante mParticipante = new Participante();
    private static ParticipanteCohorteFamilia mParticipanteChf = new ParticipanteCohorteFamilia();
    private static UserPermissions mUser = new UserPermissions();
    private ArrayList<VisitaTerreno> mVisitasTerreno = new ArrayList<VisitaTerreno>();
    private ArrayList<PesoyTalla> mPyTs = new ArrayList<PesoyTalla>();
    private ArrayList<EncuestaCasa> mEncuestasCasas = new ArrayList<EncuestaCasa>();
    private ArrayList<EncuestaParticipante> mEncuestasParticipantes = new ArrayList<EncuestaParticipante>();
    private ArrayList<LactanciaMaterna> mEncuestasLactancias = new ArrayList<LactanciaMaterna>();
    private ArrayList<NewVacuna> mVacunas = new ArrayList<NewVacuna>();
    private ArrayList<Muestra> mMuestras = new ArrayList<Muestra>();
    private ArrayList<Obsequio> mObsequios = new ArrayList<Obsequio>();
    private ArrayList<DatosPartoBB> mDatosPartoBBs = new ArrayList<DatosPartoBB>();
    private ArrayList<DatosVisitaTerreno> mDatosVisitaTerreno = new ArrayList<DatosVisitaTerreno>();
    private ArrayList<Documentos> mDocumentos = new ArrayList<Documentos>();
    private ArrayList<EncuestaCasa> mEncuestasCasasChf = new ArrayList<EncuestaCasa>();
    //private ArrayList<EncuestaCasaSA> mEncuestasCasasSa = new ArrayList<EncuestaCasaSA>();//MA2020
    //private ArrayList<EncuestaParticipanteSA> mEncuestasParticipantesSa = new ArrayList<EncuestaParticipanteSA>();//MA2020
    private List<MessageResource> catRelacionFamiliar = new ArrayList<MessageResource>();
    private ArrayList<DatosCoordenadas> mDatosCoordenadas = new ArrayList<DatosCoordenadas>();
    private ArrayList<ParticipanteSeroprevalencia> mConSA = new ArrayList<ParticipanteSeroprevalencia>();

    private String username;
    private SharedPreferences settings;
    private GridView gridView;
    private TextView textView;
    private AlertDialog alertDialog;
    private static final int EXIT = 1;
    private boolean mExitShowing;
    private boolean visExitosa = false;
    private boolean ingresoChf = false;
    private boolean pendiente = false;
    private boolean desdeFotografia = false;
    private static final String EXIT_SHOWING = "exitshowing";
    public static final int VISITA =100;
    private MenuItem reConsFluItem;
    private MenuItem pesoTallaItem;
    private MenuItem encCasaItem;
    private MenuItem encPartItem;
    private MenuItem encLactItem;
    private MenuItem vacunaItem;
    private MenuItem visitaItem;
    private MenuItem reConsDenItem;
    private MenuItem muestraItem;
    private MenuItem obsequioItem;
    private MenuItem obsequioChfItem;
    private MenuItem zikaItem;
    private MenuItem partoItem;
    private MenuItem datosCasaItem;
    private MenuItem encCasaChfItem;
    //MA2020
    //private MenuItem encCasaSaItem;
    //private MenuItem encPartSaItem;
    private MenuItem coordenadasItem;
    private MenuItem recon18ChfItem;
    private MenuItem consFluUO1Item;
    private MenuItem consCovid19;
    private MenuItem consParteEChf;
    private MenuItem cuestCovid19;
    private MenuItem mxAdicionalCovid19;
    private MenuItem consSAItem;
    private MenuItem consDenParteEItem;
    private MenuItem mxDenParteEItem;
    private MenuItem documentosItem;
    private MenuItem consChf;//MA2022

    private EstudiosAdapter estudiosAdapter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EXIT_SHOWING)) {
                mExitShowing = savedInstanceState.getBoolean(EXIT_SHOWING, false);
                visExitosa = savedInstanceState.getBoolean("YAVISITADO", false);
            }
        }
        codigo = getIntent().getIntExtra(ConstantsDB.CODIGO,-1);
        visExitosa = getIntent().getBooleanExtra(ConstantsDB.VIS_EXITO,false);
        ingresoChf = getIntent().getBooleanExtra(Constants.INGRESO_CHF,false);
        desdeFotografia = getIntent().getBooleanExtra(Constants.DESDE_FOTO,false);
        //mParticipante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        textView = (TextView) findViewById(R.id.label);
        gridView = (GridView) findViewById(R.id.gridView1);

        //if (mParticipante == null){
        estudiosAdapter.open();
        mParticipante = estudiosAdapter.getParticipante(MainDBConstants.codigo  + "="+codigo, null);
        mParticipanteChf = estudiosAdapter.getParticipanteCohorteFamilia(MainDBConstants.participante  + "="+codigo, null);
        estudiosAdapter.close();
        //}


        new FetchDataTask().execute();

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Bundle arguments = new Bundle();
                Intent i;
                switch(position){

                    case 0:
                        arguments.putString(Constants.TITLE, getString(R.string.info_participante));
                        if (mParticipante!=null) arguments.putSerializable(Constants.OBJECTO , mParticipante);
                        i = new Intent(getApplicationContext(),
                                ReviewActivity.class);
                                break;
                    case 1:
                        arguments.putString(Constants.TITLE, getString(R.string.info_visit));
                        if (mVisitasTerreno!=null) arguments.putSerializable(Constants.OBJECTO , mVisitasTerreno);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 2:
                        arguments.putString(Constants.TITLE, getString(R.string.info_weight));
                        if (mPyTs!=null) arguments.putSerializable(Constants.OBJECTO , mPyTs);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 3:
                        arguments.putString(Constants.TITLE, getString(R.string.info_survey2));
                        if (mEncuestasCasas!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasCasas);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 4:
                        arguments.putString(Constants.TITLE, getString(R.string.info_survey1));
                        if (mEncuestasParticipantes!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasParticipantes);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 5:
                        arguments.putString(Constants.TITLE, getString(R.string.info_survey3));
                        if (mEncuestasLactancias!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasLactancias);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 6:
                        arguments.putString(Constants.TITLE, getString(R.string.info_vacc));
                        if (mVacunas!=null) arguments.putSerializable(Constants.OBJECTO , mVacunas);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 7:
                        arguments.putInt("codCasa", mParticipante.getCasa().getCodigo());
                        arguments.putInt("codComun", mParticipante.getCodigo());

                        i = new Intent(getApplicationContext(),
                                ListParticipantesCasaActivity.class);
                        break;
                    /*case 9:
                        arguments.putString(Constants.TITLE, getString(R.string.info_recon));
                        if (mReConsentimientoDen!=null) arguments.putSerializable(Constants.OBJECTO , mReConsentimientoDen);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;*/
                    case 8:
                        arguments.putString(Constants.TITLE, getString(R.string.info_sample));
                        if (mMuestras!=null) arguments.putSerializable(Constants.OBJECTO , mMuestras);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 9:
                        arguments.putString(Constants.TITLE, getString(R.string.info_gift));
                        if (mObsequios!=null) arguments.putSerializable(Constants.OBJECTO , mObsequios);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 10:
                        arguments.putString(Constants.TITLE, getString(R.string.info_sa));
                        if (mConSA!=null) arguments.putSerializable(Constants.OBJECTO , mConSA);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 11:
                        arguments.putString(Constants.TITLE, getString(R.string.datos_parto));
                        if (mDatosPartoBBs!=null) arguments.putSerializable(Constants.OBJECTO , mDatosPartoBBs);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 12:
                        arguments.putString(Constants.TITLE, getString(R.string.datos_casa));
                        if (mDatosVisitaTerreno!=null) arguments.putSerializable(Constants.OBJECTO , mDatosVisitaTerreno);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 13:
                        arguments.putString(Constants.TITLE, getString(R.string.info_docs));
                        if (mDocumentos!=null) arguments.putSerializable(Constants.OBJECTO , mDocumentos);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 14:
                        arguments.putString(Constants.TITLE, getString(R.string.info_casachf));
                        if (mEncuestasCasasChf!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasCasasChf);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    //MA2020
                    /*case 17:
                        arguments.putString(Constants.TITLE, getString(R.string.info_casasa));
                        if (mEncuestasCasasSa!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasCasasSa);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 18:
                        arguments.putString(Constants.TITLE, getString(R.string.info_participantesa));
                        if (mEncuestasParticipantesSa!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasParticipantesSa);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                        */
                    case 15:
                        arguments.putString(Constants.TITLE, getString(R.string.info_telefonos));

                        i = new Intent(getApplicationContext(),
                                ListPhonesActivity.class);
                        if (mParticipante!=null) i.putExtra(Constants.PARTICIPANTE, mParticipante);

                        break;
                    case 16:
                        arguments.putString(Constants.TITLE, getString(R.string.info_coordenadas));
                        if (mDatosCoordenadas!=null) arguments.putSerializable(Constants.OBJECTO , mDatosCoordenadas);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    default:
                        arguments.putString(Constants.TITLE, getString(R.string.info_participante));
                        if (mParticipante!=null) arguments.putSerializable(Constants.OBJECTO , mParticipante);
                        i = new Intent(getApplicationContext(),
                                ReviewActivity.class);
                        break;
                }
                i.putExtras(arguments);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (mParticipante.getProcesos().getEstPart().equals(0) && (mParticipante.getProcesos().getReConsDeng()==null || mParticipante.getProcesos().getReConsDeng().matches("No"))){
            getMenuInflater().inflate(R.menu.general, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.menu_info, menu);
            reConsFluItem = menu.findItem(R.id.RECONSFLU);
            pesoTallaItem = menu.findItem(R.id.WEIGHT);
            encCasaItem = menu.findItem(R.id.SURVEY2);
            encPartItem = menu.findItem(R.id.SURVEY1);
            encLactItem = menu.findItem(R.id.SURVEY3);
            vacunaItem = menu.findItem(R.id.VACC);
            visitaItem = menu.findItem(R.id.MENU_VISIT);
            reConsDenItem = menu.findItem(R.id.RECONS);
            muestraItem = menu.findItem(R.id.SAMPLE);
            obsequioItem = menu.findItem(R.id.GIFT);
            obsequioChfItem = menu.findItem(R.id.GIFT_CHF);
            zikaItem = menu.findItem(R.id.ZIKA);
            partoItem = menu.findItem(R.id.PARTO);
            datosCasaItem = menu.findItem(R.id.DAT_CASA);
            encCasaChfItem = menu.findItem(R.id.ENCASA_CHF);
            //MA2020
            //encCasaSaItem = menu.findItem(R.id.ENCASA_SA);
            //encPartSaItem = menu.findItem(R.id.ENPART_SA);
            coordenadasItem = menu.findItem(R.id.COORDENADAS);
            recon18ChfItem = menu.findItem(R.id.RECONS_CHF);
            consFluUO1Item = menu.findItem(R.id.CONSFLUUO1);
            consCovid19 = menu.findItem(R.id.CONSCOVID19);
            consParteEChf = menu.findItem(R.id.CONS_PARTEE_CHF);
            cuestCovid19 = menu.findItem(R.id.CUESTCOVID19);
            mxAdicionalCovid19 = menu.findItem(R.id.MXADICOVID19);
            consSAItem = menu.findItem(R.id.CONSSA);
            consDenParteEItem = menu.findItem(R.id.CONS_PARTEE_DEN);
            mxDenParteEItem = menu.findItem(R.id.MX_PARTEE_DEN);
            documentosItem = menu.findItem(R.id.DOC);
            consChf = menu.findItem(R.id.CONS_CHF);
        }
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXIT_SHOWING, mExitShowing);
        outState.putBoolean("YAVISITADO", visExitosa);
    }

    @Override
    protected void onResume() {
        if (mExitShowing) {
            createDialog(EXIT);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        Bundle arguments = new Bundle();
        CasaCohorteFamilia mCasasCHF = null;
        switch (item.getItemId()) {
            case android.R.id.home:
                if (pendiente){
                    createDialog(EXIT);
                }
                else{
                    finish();
                }
                return true;
            case R.id.MENU_BACK:
                if (pendiente){
                    createDialog(EXIT);
                }
                else{
                    if (desdeFotografia){
                        i = new Intent(getApplicationContext(),
                                ParticipantPhotographActivity.class);
                    } else {
                        i = new Intent(getApplicationContext(),
                                SelecPartActivity.class);
                        i.putExtra(Constants.MENU_INFO, true);
                    }
                    startActivity(i);
                    finish();
                }
                return true;
            case R.id.MENU_HOME:
                if (pendiente){
                    createDialog(EXIT);
                }
                else{
                    i = new Intent(getApplicationContext(),
                            MenuMuestreoAnualActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
                return true;

            case R.id.MENU_VISIT:
                if(mUser.getVisitas()){
                    i = new Intent(getApplicationContext(),
                            NewVisitaActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                    i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                    i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                    startActivity(i);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;

            case R.id.DAT_CASA:
                //if(mUser.getVisitas()){
                    if(mParticipante.getProcesos().getDatosVisita().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NewDatosVisitaActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                /*}
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }*/
                return true;

            case R.id.CONSCOVID19:
                if(mUser.getConsentimiento()){
                    if(mParticipante.getProcesos().getConsCovid19().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NuevoConsCovid19Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.CONS_PARTEE_CHF:
                if(mUser.getConsentimiento()){
                    if(mParticipante.getProcesos().getConsChf().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NuevoConsCHFParteECovid19Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.CONS_CHF:
                if(mUser.getConsentimiento()){
                    if(mParticipante.getProcesos().getConsChf().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NuevoConsChfActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.CUESTCOVID19:
                if(mUser.getConsentimiento()){
                    if(!mParticipante.getProcesos().getCuestCovid().matches("No")){
                        i = new Intent(getApplicationContext(),
                                NuevoCuestionarioCovid19v2Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.MXADICOVID19:
                if(mUser.getMuestra()){
                    if(mParticipante.getProcesos().getMuestraCovid().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NuevaMuestraAdicRojoCovid19Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    } else if(mParticipante.getProcesos().getMuestraCovid().matches(Constants.MX_EDTA_CITRATO)){
                        i = new Intent(getApplicationContext(),
                                NuevaMuestraAdicRojoEDTACitratoCovid19Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.CONSFLUUO1:
                if(mUser.getConsentimiento()){
                    if(mParticipante.getProcesos().getConsFlu().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NewConFluUO1Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.RECONSFLU:
                if(mUser.getConsentimiento()){
                    if(mParticipante.getProcesos().getConsFlu().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NuevoConsFluCEIRSActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;

            case R.id.WEIGHT:
                if(mUser.getPesoTalla()){
                    if(mParticipante.getProcesos().getPesoTalla().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NewPtActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;

            case R.id.SURVEY2:
                if(mUser.getEncuestaCasa()){
                    if(mParticipante.getProcesos().getEnCasa().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NewEcActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.SURVEY1:
                if(mUser.getEncuestaParticipante()){
                    if(mParticipante.getProcesos().getEncPart().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NewEpActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.SURVEY3:
                if(mUser.getEncuestaLactancia()){
                    if(mParticipante.getProcesos().getEncLacMat().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NewElActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.VACC:
                if(mUser.getVacunas()){
                    if(mParticipante.getProcesos().getInfoVacuna().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NewNewVaccActivity.class);
                        //i = new Intent(getApplicationContext(),
                        //		VacunaActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.RECONS:
                if(mUser.getConsentimiento()){
                    if(mParticipante.getProcesos().getConsDeng().matches("Si") || mParticipante.getProcesos().getReConsDeng().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NewReconDengue2018Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        //i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.CONS_PARTEE_DEN:
                if(mUser.getConsentimiento()){
                    if(mParticipante.getProcesos().getConsDenParteE().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NuevoConsDENParteEActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.MX_PARTEE_DEN:
                if(mUser.getMuestra()){
                    if(mParticipante.getProcesos().getMxDenParteE().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NuevaMuestraAdicRojoDenActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.RECONS_CHF:
                if(mUser.getConsentimiento()){
                    if(mParticipante.getProcesos().getReConsChf18()!=null && mParticipante.getProcesos().getReConsChf18().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NuevoRecon18AniosActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipanteChf);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.SAMPLE:
                if(mUser.getMuestra()){
                    if (!ingresoChf) {
                        if (mParticipante.getProcesos().getConmx().matches("No") || mParticipante.getProcesos().getConmxbhc().matches("No")) {
                            if (mParticipante.getProcesos().getConvalesciente().matches("Na")) {
                                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.convless14), Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                i = new Intent(getApplicationContext(),
                                        NewSampleActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                                i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                                i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                                startActivity(i);
                            }
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.e_error), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }else{

                        if (mParticipanteChf != null) arguments.putSerializable(Constants.PARTICIPANTE, mParticipanteChf);
                        i = new Intent(getApplicationContext(),
                                ListaMuestrasActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        i.putExtra(Constants.ACCION, Constants.ENTERING);
                        i.putExtra(Constants.MENU_INFO, true);
                        startActivity(i);
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.GIFT:
                if(mUser.getObsequio()){
                    if(mParticipante.getProcesos().getObsequio().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NewObActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.GIFT_CHF:
                if(mUser.getObsequio()){
                    if(mParticipante.getProcesos().getObsequioChf().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NuevoObsequioActivity.class);

                        if (mParticipante.getProcesos().getCasaCHF()!=null && !mParticipante.getProcesos().getCasaCHF().isEmpty()) {
                            estudiosAdapter.open();
                            mCasasCHF = estudiosAdapter.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + mParticipante.getProcesos().getCasaCHF(), MainDBConstants.codigoCHF);
                            estudiosAdapter.close();
                        }
                        if (mCasasCHF != null) arguments.putSerializable(Constants.CASACHF, mCasasCHF);
                        arguments.putSerializable(Constants.PARTICIPANTE, mParticipante.getCodigo());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        i.putExtras(arguments);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            /*case R.id.ZIKA:
                if(mUser.getConsentimiento()){
                    if(mParticipante.getProcesos().getZika().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NewZikaActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;*/
            case R.id.PARTO:
                if(mUser.getDatosparto()){
                    if(mParticipante.getProcesos().getDatosParto().matches("Si") || mParticipante.getProcesos().getcDatosParto().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NewDatosPartoBBActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.DOC:
                i = new Intent(getApplicationContext(),
                        DocumentosActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                startActivity(i);
                return true;
            case R.id.ENCASA_CHF:
                if(mUser.getEncuestaCasa()){
                    if(mParticipante.getProcesos().getEnCasaChf().matches("Si") && (mParticipante.getProcesos().getCasaCHF()!=null && !mParticipante.getProcesos().getCasaCHF().isEmpty())){
                        i = new Intent(getApplicationContext(),
                                NewEcActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.CASACHF, mParticipante.getProcesos().getCasaCHF());
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
                //MA2020
            /*case R.id.ENCASA_SA:
                if(mUser.getEncuestaCasa()) {
                    if(mParticipante.getProcesos().getEnCasaSa().matches("Si")) {
                        i = new Intent(getApplicationContext(),
                                NuevaEncuestaCasaSAActivity.class);
                        if (mParticipante.getProcesos().getCasaCHF()!=null && !mParticipante.getProcesos().getCasaCHF().isEmpty()) {
                            estudiosAdapter.open();
                            mCasasCHF = estudiosAdapter.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + mParticipante.getProcesos().getCasaCHF(), MainDBConstants.codigoCHF);
                            estudiosAdapter.close();
                        }
                        if (mCasasCHF != null) arguments.putSerializable(Constants.CASACHF, mCasasCHF);
                        arguments.putSerializable(Constants.CASA, mParticipante.getCasa());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(Constants.MENU_INFO, true);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.ENPART_SA:
                if(mUser.getEncuestaParticipante()){
                    if(mParticipante.getProcesos().getEncPartSa().matches("Si")) {
                        estudiosAdapter.open();
                        ParticipanteCohorteFamilia participanteCHF = estudiosAdapter.getParticipanteCohorteFamilia(MainDBConstants.participante + " = " + mParticipante.getCodigo(), null);
                        //ParticipanteSeroprevalencia participanteSA = estudiosAdapter.getParticipanteSeroprevalencia(SeroprevalenciaDBConstants.participante + " = " + mParticipante.getCodigo(), null);
                        estudiosAdapter.close();
                        if (participanteCHF != null)
                            arguments.putSerializable(Constants.PARTICIPANTE, participanteCHF);
                        //if (participanteSA != null)
                        arguments.putSerializable(Constants.PARTICIPANTE_SA, mParticipante);
                        i = new Intent(getApplicationContext(),
                                NuevaEncuestaParticipanteSAActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        i.putExtra(Constants.MENU_INFO, true);
                        i.putExtra(ConstantsDB.VIS_EXITO,visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;*/
            case R.id.COORDENADAS:
                if(!mParticipante.getProcesos().getCoordenadas().equals("0")) {
                    i = new Intent(getApplicationContext(),
                            NewDatosCoordenadasActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                    i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                    i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                    startActivity(i);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.e_error), Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            case R.id.CONSSA:
                if(mUser.getConsentimiento()){
                    if(mParticipante.getProcesos().getConsSa().matches("Si")){
                        i = new Intent(getApplicationContext(),
                                NuevoConsentimientoSAActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.PARTICIPANTE, mParticipante);
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.e_error),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.perm_error),Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed (){
        if (pendiente){
            createDialog(EXIT);
        }
        else{
            Intent i;
            if (desdeFotografia){
                i = new Intent(getApplicationContext(),
                        ParticipantPhotographActivity.class);
            } else {
                i = new Intent(getApplicationContext(),
                        SelecPartActivity.class);
                i.putExtra(Constants.MENU_INFO, true);
            }
            startActivity(i);
            finish();
        }
    }

    private void createDialog(int dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(dialog){
            case EXIT:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.confirm_pendiente));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish app
                        dialog.dismiss();
                        Intent i = new Intent(getApplicationContext(),
                                RazonNoDataActivity.class);
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        startActivity(i);
                        finish();
                        mExitShowing=false;
                    }

                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                        mExitShowing=false;
                    }
                });
                mExitShowing=true;
                break;
            default:
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean onPrepareOptionsMenu() {
        reConsFluItem.setVisible(false);
        pesoTallaItem.setVisible(false);
        encCasaItem.setVisible(false);
        encPartItem.setVisible(false);
        encLactItem.setVisible(false);
        vacunaItem.setVisible(false);
        visitaItem.setVisible(false);
        reConsDenItem.setVisible(false);
        muestraItem.setVisible(false);
        obsequioItem.setVisible(false);
        obsequioChfItem.setVisible(false);
        zikaItem.setVisible(false);
        partoItem.setVisible(false);
        datosCasaItem.setVisible(false);
        encCasaChfItem.setVisible(false);
        //MA2020 encCasaSaItem.setVisible(false);
        //MA2020 encPartSaItem.setVisible(false);
        coordenadasItem.setVisible(false);
        recon18ChfItem.setVisible(false);
        consFluUO1Item.setVisible(false);
        consCovid19.setVisible(false);
        consParteEChf.setVisible(false);
        cuestCovid19.setVisible(false);
        mxAdicionalCovid19.setVisible(false);
        consSAItem.setVisible(false);
        consDenParteEItem.setVisible(false);
        mxDenParteEItem.setVisible(false);
        documentosItem.setVisible(false); //siempre oculto
        consChf.setVisible(false);

        if (!mParticipante.getProcesos().getEstudio().trim().contains("Influenza")){
            consCovid19.setTitle(R.string.info_cons_covid19);
        }else{
            consCovid19.setTitle(R.string.info_cons_covid19_flu);
        }
        //la opción de reconsentimiento dengue siempre se va a mostrar
        if ((mParticipante.getProcesos().getConsDeng().matches("Si") || mParticipante.getProcesos().getReConsDeng().matches("Si")) && mUser.getConsentimiento()) reConsDenItem.setVisible(true);
        if ((!mParticipante.getProcesos().getCoordenadas().equals("0") && mUser.getConsentimiento())) coordenadasItem.setVisible(true);
        if ((mUser.getVisitas()&&!visExitosa)){
            visitaItem.setVisible(true);
        }
        else{
            if((mParticipante.getProcesos().getConmx().matches("No") || (mParticipante.getProcesos().getConmxbhc().matches("No") && !mParticipante.getProcesos().getEstudio().contains("UO1") && mParticipante.getEdadMeses()>24 )) //excluir UO1 menores de 24 que no aplica bhc
                    && mUser.getMuestra()
                    && (mParticipante.getEdadMeses()>5
                    || (mParticipante.getEdadMeses()<=5 && mParticipante.getProcesos().getEstudio().contains("UO1")))) muestraItem.setVisible(true);

            if((mParticipante.getProcesos().getEnCasa().matches("Si") && mUser.getEncuestaCasa())) encCasaItem.setVisible(true);
            if((mParticipante.getProcesos().getEncPart().matches("Si") && mUser.getEncuestaParticipante())) encPartItem.setVisible(true);
            if ((mParticipante.getProcesos().getConsFlu().matches("Si") && mUser.getConsentimiento())) reConsFluItem.setVisible(true);
            if ((mParticipante.getProcesos().getConsCovid19().matches("Si") && mUser.getConsentimiento())) consCovid19.setVisible(true);
            //if ((mParticipante.getProcesos().getConsFlu().matches("Si") && mUser.getConsentimiento())) consFluUO1Item.setVisible(true);
            if((mParticipante.getProcesos().getPesoTalla().matches("Si") && mUser.getPesoTalla())) pesoTallaItem.setVisible(true);
            if((mParticipante.getProcesos().getEncLacMat().matches("Si") && mUser.getEncuestaLactancia())) encLactItem.setVisible(true);
            if((mParticipante.getProcesos().getInfoVacuna().matches("Si") && mUser.getVacunas())) vacunaItem.setVisible(true);
            if ((mParticipante.getProcesos().getZika().matches("Si") && mUser.getConsentimiento())) zikaItem.setVisible(true);
            if ((mParticipante.getProcesos().getObsequio().matches("Si") && mUser.getObsequio())) obsequioItem.setVisible(true);
            if ((mParticipante.getProcesos().getObsequioChf().matches("Si") && mUser.getObsequio())) obsequioChfItem.setVisible(true);
            if (((mParticipante.getProcesos().getDatosParto().matches("Si") || mParticipante.getProcesos().getcDatosParto().matches("Si")) && mUser.getDatosparto())) partoItem.setVisible(true);
            if (mParticipante.getProcesos().getDatosVisita().matches("Si")) datosCasaItem.setVisible(true); //15032018. permitir aúnque no sea en terreno if ((mParticipante.getProcesos().getDatosVisita().matches("Si") && mUser.getVisitas())) datosCasaItem.setVisible(true);
            if((mParticipante.getProcesos().getEnCasaChf().matches("Si") && mUser.getEncuestaCasa())) encCasaChfItem.setVisible(true);
            //MA2020 if((mParticipante.getProcesos().getEnCasaSa().matches("Si") && mUser.getEncuestaCasa())) encCasaSaItem.setVisible(true);
            //MA2020 if((mParticipante.getProcesos().getEncPartSa().matches("Si") && mUser.getEncuestaParticipante())) encPartSaItem.setVisible(true);
            if ((mParticipante.getProcesos().getReConsChf18()!=null && mParticipante.getProcesos().getReConsChf18().matches("Si") && mUser.getConsentimiento())) recon18ChfItem.setVisible(true);
            visitaItem.setVisible(false);
            //MA2022
            //if ((mParticipante.getProcesos().getConsChf()!=null && mParticipante.getProcesos().getConsChf().matches("Si") && mUser.getConsentimiento())) consParteEChf.setVisible(true);
            if ((mParticipante.getProcesos().getConsChf()!=null && mParticipante.getProcesos().getConsChf().matches("Si") && mUser.getConsentimiento())) consChf.setVisible(true);
            if ((mParticipante.getProcesos().getCuestCovid()!=null && !mParticipante.getProcesos().getCuestCovid().matches("No") && mUser.getEncuestaParticipante())) cuestCovid19.setVisible(true);
            if ((mParticipante.getProcesos().getConmx().matches("Si") && mParticipante.getProcesos().getConmxbhc().matches("Si")) &&
                    (mParticipante.getProcesos().getMuestraCovid()!=null && (mParticipante.getProcesos().getMuestraCovid().matches("Si") || mParticipante.getProcesos().getMuestraCovid().matches(Constants.MX_EDTA_CITRATO)) && mUser.getMuestra())) mxAdicionalCovid19.setVisible(true);
            //Deshabilitar en MA2021. Habilitar muestreo familia nov 2021
            if ((mParticipante.getProcesos().getConsSa()!=null && mParticipante.getProcesos().getConsSa().matches("Si") && mUser.getConsentimiento()))consSAItem.setVisible(true);
            //ParteE Dengue
            if ((mParticipante.getProcesos().getConsDenParteE()!=null && mParticipante.getProcesos().getConsDenParteE().matches("Si") && mUser.getEncuestaParticipante())) consDenParteEItem.setVisible(true);
            /*if ((mParticipante.getProcesos().getConmx().matches("Si") && mParticipante.getProcesos().getConmxbhc().matches("Si")) &&
                    (mParticipante.getProcesos().getMxDenParteE()!=null && mParticipante.getProcesos().getMxDenParteE().matches("Si") && mUser.getMuestra())) mxDenParteEItem.setVisible(true);
             *///Deshabilitar en MA2021
        }
        return true;
    }

    public int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if(dateOfBirth!= null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if(born.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if(now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR))  {
                age-=1;
            }
        }
        return age;
    }

    private void getParticipanteData() {
        estudiosAdapter.open();
        if (mParticipante == null)
            mParticipante = estudiosAdapter.getParticipante(MainDBConstants.codigo  + "="+codigo, null);
        mVisitasTerreno=estudiosAdapter.getListaVisitaTerreno(codigo);
        mPyTs = estudiosAdapter.getListaPesoyTallas(codigo);
        if(mParticipante.getCasa().getCodigo()!=9999) mEncuestasCasas = estudiosAdapter.getListaEncuestaCasas(mParticipante.getCasa().getCodigo());
        mEncuestasParticipantes = estudiosAdapter.getListaEncuestaParticipantes(codigo);
        mEncuestasLactancias = estudiosAdapter.getListaLactanciaMaternas(codigo);
        mMuestras=estudiosAdapter.getListaMuestras(codigo);
        mObsequios=estudiosAdapter.getListaObsequios(codigo);
        mDatosVisitaTerreno = estudiosAdapter.getListaDatosVisitaTerreno(codigo);
        mUser = estudiosAdapter.getPermisosUsuario(ConstantsDB.USERNAME + "='" +username+"'", null);
        String filtro = ConstantsDB.CODIGO + "=" + codigo;
        mDatosPartoBBs = estudiosAdapter.getDatosPartoBBs(filtro, null);
        mVacunas=estudiosAdapter.getNewVacunas(filtro, null);
        mDocumentos =estudiosAdapter.getDocumentoss(filtro, null);
        if(mParticipante.getCasa().getCodigo()!=9999){
            if (mParticipante.getProcesos().getCasaCHF()!=null && !mParticipante.getProcesos().getCasaCHF().isEmpty())
                mEncuestasCasasChf = estudiosAdapter.getListaEncuestaCasasChf(mParticipante.getProcesos().getCasaCHF());
            //MA2020 mEncuestasCasasSa = (ArrayList)estudiosAdapter.getEncuestasCasaSA(SeroprevalenciaDBConstants.casa + "=" + mParticipante.getCasa().getCodigo() , null);
        }
        mDatosCoordenadas = (ArrayList)estudiosAdapter.getDatosCoordenadas(MainDBConstants.participante + "=" + mParticipante.getCodigo(), null);
        //MA2020 mEncuestasParticipantesSa = (ArrayList)estudiosAdapter.getEncuestasParticipanteSA(SeroprevalenciaDBConstants.participante + "=" + mParticipante.getCodigo() , null);
        catRelacionFamiliar = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", null);
        mConSA = (ArrayList)estudiosAdapter.getParticipantesSeroprevalencia(MainDBConstants.participante + "=" + mParticipante.getCodigo(), null);
        //procesos CHF
        if (mParticipante.getProcesos().getEstudio().toLowerCase().contains("ch familia")){
            ParticipanteCohorteFamiliaCaso existePartCaso = estudiosAdapter.getParticipanteCohorteFamiliaCaso(CasosDBConstants.participante+"="+mParticipante.getCodigo(),null);
            DatosCHF datosCHF = new DatosCHF();
            //existe caso seguimiento activo
            if (existePartCaso!=null && existePartCaso.getCodigoCaso()!=null && existePartCaso.getCodigoCaso().getInactiva().equalsIgnoreCase("0")){
                datosCHF.setEnMonitoreoIntensivo(true);
            }else{
                datosCHF.setEnMonitoreoIntensivo(false);
            }
            mParticipante.setDatosCHF(datosCHF);
        }
        //procesos UO1
        if (mParticipante.getProcesos().getEstudio().contains("UO1")) {
            ParticipanteCasoUO1 casoUO1 = estudiosAdapter.getParticipanteCasoUO1(InfluenzaUO1DBConstants.participante + "=" + mParticipante.getCodigo(), null);
            DatosUO1 datosUO1 = new DatosUO1();
            if (casoUO1 != null) {
                datosUO1.setConvalesciente(true);
                datosUO1.setFechaInicioCaso(casoUO1.getFechaIngreso());
            }
            //sacar la ultima visita inicial por vacuna uo1, si tiene evaluar si tiene menos de 30 dias de vacunado
            List<VisitaVacunaUO1> mVisitasCasos = estudiosAdapter.getVisitasVacunasUO1(InfluenzaUO1DBConstants.participante + " = " + mParticipante.getCodigo() + " and visitaExitosa = '1' and visita = 'I' ",
                    InfluenzaUO1DBConstants.fechaVisita + " desc ");
            if (mVisitasCasos.size() > 0) {
                VisitaVacunaUO1 ultimaVisita = mVisitasCasos.get(0);
                if (DateUtil.getDateDiff(ultimaVisita.getFechaVisita(), new Date(), TimeUnit.DAYS) < 30) {
                    datosUO1.setVacunado(ultimaVisita.getVacuna().equalsIgnoreCase(Constants.YESKEYSND));
                    datosUO1.setFechaVacuna(ultimaVisita.getFechaVacuna());
                    datosUO1.setMxTomada(ultimaVisita.getTomaMxAntes().equalsIgnoreCase(Constants.YESKEYSND));
                    datosUO1.setRazonNoMx(ultimaVisita.getRazonNoTomaMx());
                    datosUO1.setVisitaExitosa(ultimaVisita.getVisitaExitosa().equalsIgnoreCase(Constants.YESKEYSND));
                }
            }
            mParticipante.setDatosUO1(datosUO1);
        }  //procesos CEIRS
        else if (mParticipante.getProcesos().getEstudio().contains("Influenza")) {
            ParticipanteCasoUO1 casoUO1 = estudiosAdapter.getParticipanteCasoUO1(InfluenzaUO1DBConstants.participante + "=" + mParticipante.getCodigo(), null);
            DatosUO1 datosUO1 = new DatosUO1();
            if (casoUO1 != null) {
                datosUO1.setConvalescienteFlu(true);
                datosUO1.setFechaInicioCaso(casoUO1.getFechaIngreso());
            }
            mParticipante.setDatosUO1(datosUO1);
        }
        //procesos Covid19
        //if (mParticipante.getProcesos().getSubEstudios().contains("2")) {
            ParticipanteCasoCovid19 pCasoCovid19 = estudiosAdapter.getParticipanteCasoCovid19(Covid19DBConstants.participante + "=" + mParticipante.getCodigo(), null);
            DatosCovid19 datosCovid19 = new DatosCovid19();
            if (pCasoCovid19 != null) {
                datosCovid19.setSeguimiento(true);
                //2 es consentimiento pendiente
                if (!pCasoCovid19.getConsentimiento().equalsIgnoreCase("2")) {
                    datosCovid19.setMensajeSeguimiento((mParticipante.getProcesos().getEstudio().contains(Constants.T_COVID19) ? getString(R.string.enTransmisionCovid19) : getString(R.string.enSeguimientoCovid19)));
                } else {
                    datosCovid19.setMensajeSeguimiento(getString(R.string.noEnroladoTransmisionCovid19));
                }
            } else {
                CasoCovid19 casoCovid19 = null;
                if (mParticipante.getProcesos().getCasaCHF()!=null && !mParticipante.getProcesos().getCasaCHF().isEmpty())
                    casoCovid19 = estudiosAdapter.getCasoCovid19(Covid19DBConstants.casa + "='" + mParticipante.getProcesos().getCasaCHF() + "' and " + Covid19DBConstants.inactivo + "='0'", null);
                if (casoCovid19 != null) { // la casa esta en seguimiento covid19, pero el participante no esta enrolado
                    datosCovid19.setSeguimiento(true);
                    datosCovid19.setMensajeSeguimiento(getString(R.string.noEnroladoTransmisionCovid19));
                }
            }
            mParticipante.setDatosCovid19(datosCovid19);
        //}
        estudiosAdapter.close();
    }

    private void refreshView() throws Exception{
        pendiente = false;
        Integer edad = getAge(mParticipante.getFechaNac());
        String labelHeader = "<b>"+mParticipante.getCodigo()+" - "+ mParticipante.getNombre1();
        if (mParticipante.getNombre2()!=null) labelHeader = labelHeader + " "+  mParticipante.getNombre2();
        labelHeader = labelHeader +" "+ mParticipante.getApellido1();
        if (mParticipante.getApellido2()!=null) labelHeader = labelHeader + " "+  mParticipante.getApellido2();
        labelHeader = labelHeader +" - ("+ mParticipante.getProcesos().getEstudio()+")";
        if (edad==0){
            edad = mParticipante.getEdadMeses();
            labelHeader = labelHeader +" - "+ edad +"M</b><br />";
        }
        else{
            labelHeader = labelHeader +" - "+ edad +"A</b><br />";
        }

        String relacion = MessageResourceUtil.getRelacionFamiliar(catRelacionFamiliar, mParticipante.getRelacionFamiliarTutor());

        labelHeader = labelHeader + "<small><font color='black'>"+mParticipante.getTutor();
        labelHeader = labelHeader +" - ("+ relacion +")" +"</font></small><br />";
        labelHeader = labelHeader + "<small><font color='black'>Personas en casa: "+mParticipante.getProcesos().getCuantasPers()+"</font></small><br />";

        if (mParticipante.getProcesos().getEstPart().equals(0) && (mParticipante.getProcesos().getReConsDeng()==null || mParticipante.getProcesos().getReConsDeng().matches("No"))){
            labelHeader = labelHeader + "<small><font color='red'>" + getString(R.string.retired_error) + "</font></small><br />";
            if (mParticipante.getProcesos().getInformacionRetiro() != null)
                labelHeader = labelHeader + "<small><font color='red'>" + mParticipante.getProcesos().getInformacionRetiro() + "</font></small><br />";
        }
        else{
            if (mParticipante.getProcesos().getEstPart().equals(0) && (mParticipante.getProcesos().getReConsDeng()!=null && mParticipante.getProcesos().getReConsDeng().matches("Si"))){
                labelHeader = labelHeader + "<small><font color='red'>" + getString(R.string.retired_error) + "</font></small><br />";
            }
            if (mParticipante.getProcesos().getPosZika().matches("Si")) labelHeader = labelHeader + "<small><font color='red'>Participante positivo a ZIKA</font></small><br />";
            if ((mParticipante.getDatosCHF()!=null && mParticipante.getDatosCHF().isEnMonitoreoIntensivo()) || mParticipante.getProcesos().getMi().matches("Si")) labelHeader = labelHeader + "<font color='red'><b>Participante en monitoreo intensivo CHF</b></font><br />";
            if (mParticipante.getProcesos().getPosDengue()!=null) labelHeader = labelHeader + "<small><font color='red'>"+mParticipante.getProcesos().getPosDengue()+"</font></small><br />";
            if (mParticipante.getDatosUO1()!=null &&  mParticipante.getDatosUO1().isConvalesciente() && mParticipante.getDatosUO1().getDiasConvalesciente() < 30){
                labelHeader = labelHeader + "<small><font color='red'>Convaleciente UO1 con menos de 30 días. No tomar muestra</font></small><br />";
            }
            if (mParticipante.getDatosUO1()!=null &&  mParticipante.getDatosUO1().isConvalescienteFlu() && mParticipante.getDatosUO1().getDiasConvalesciente() < 30){
                labelHeader = labelHeader + "<small><font color='red'>Convaleciente Flu con menos de 30 días. No tomar muestra</font></small><br />";
            }
            if (mParticipante.getDatosUO1()!=null &&  mParticipante.getDatosUO1().isVisitaExitosa() && mParticipante.getDatosUO1().getDiasVacuna() < 30){
                if (mParticipante.getDatosUO1().isVacunado()) {
                    if (mParticipante.getDatosUO1().isMxTomada())
                        labelHeader = labelHeader + "<small><font color='red'>Vacuna UO1 con menos de 30 días. No tomar muestra</font></small><br />";
                    else
                        labelHeader = labelHeader + "<small><font color='red'>Vacuna UO1 menor 30 días sin muestra: "+mParticipante.getDatosUO1().getRazonNoMx()+"</font></small><br />";
                } else {
                    labelHeader = labelHeader + "<small><font color='red'>Visita Vac UO1 menor 30 días sin muestra: "+mParticipante.getDatosUO1().getRazonNoMx()+"</font></small><br />";
                }
            }

            if (mParticipante.getProcesos().getPosCovid()!=null) labelHeader = labelHeader + "<small><font color='red'>"+mParticipante.getProcesos().getPosCovid()+"</font></small><br />";
            if (mParticipante.getDatosCovid19()!=null &&  mParticipante.getDatosCovid19().isSeguimiento()){
                labelHeader = labelHeader + "<small><font color='red'>"+mParticipante.getDatosCovid19().getMensajeSeguimiento()+"</font></small><br />";
            }

            if (mParticipante.getProcesos().getConsFlu().matches("Si")|| mParticipante.getProcesos().getPesoTalla().matches("Si")
                    || mParticipante.getProcesos().getEnCasa().matches("Si")||mParticipante.getProcesos().getEncPart().matches("Si")
                    || mParticipante.getProcesos().getEnCasaChf().matches("Si")//MA2020|| mParticipante.getProcesos().getEnCasaSa().matches("Si")||mParticipante.getProcesos().getEncPartSa().matches("Si")
                    || mParticipante.getProcesos().getEncLacMat().matches("Si")||mParticipante.getProcesos().getInfoVacuna().matches("Si")
                    || mParticipante.getProcesos().getConsDeng().matches("Si") || mParticipante.getProcesos().getObsequio().matches("Si")
                    || mParticipante.getProcesos().getConmx().matches("No") || mParticipante.getProcesos().getConmxbhc().matches("No")|| mParticipante.getProcesos().getZika().matches("Si")
                    || mParticipante.getProcesos().getAdn().matches("Si") || (mParticipante.getProcesos().getDatosParto().matches("Si") || mParticipante.getProcesos().getcDatosParto().matches("Si"))|| mParticipante.getProcesos().getDatosVisita().matches("Si")
                    || !mParticipante.getProcesos().getConvalesciente().matches("No")
                    || (mParticipante.getProcesos().getReConsDeng()!=null && mParticipante.getProcesos().getReConsDeng().matches("Si"))
                    || !mParticipante.getProcesos().getCoordenadas().equals("0")
                    || mParticipante.getProcesos().getObsequioChf().matches("Si")
                    || (mParticipante.getProcesos().getReConsChf18()!=null && mParticipante.getProcesos().getReConsChf18().matches("Si"))
                    || mParticipante.getProcesos().getConsCovid19().matches("Si")
                    || (mParticipante.getProcesos().getConsChf() != null && mParticipante.getProcesos().getConsChf().matches("Si"))
                    || (mParticipante.getProcesos().getCuestCovid() != null && !mParticipante.getProcesos().getCuestCovid().matches("No"))
                    || (mParticipante.getProcesos().getMuestraCovid() != null && (mParticipante.getProcesos().getMuestraCovid().matches("Si") || mParticipante.getProcesos().getMuestraCovid().matches(Constants.MX_EDTA_CITRATO))) //se deshabilita para MA2021
                    || (mParticipante.getProcesos().getConsSa() != null && mParticipante.getProcesos().getConsSa().matches("Si"))
                    || (mParticipante.getProcesos().getConsDenParteE()!=null && mParticipante.getProcesos().getConsDenParteE().matches("Si"))
                    //|| (mParticipante.getProcesos().getMxDenParteE()!=null && mParticipante.getProcesos().getMxDenParteE().matches("Si")) //se deshabilita para MA2021
            ){
                labelHeader = labelHeader + "<small><font color='red'>Pendiente: <br /></font></small>";


                //Primero muestras
                //'#B941E0'purple
                //'#11BDF7' blue
                //'#32B507' green
                if(mUser.getMuestra()){
                    if (mParticipante.getProcesos().getConvalesciente().matches("Na")){
                        labelHeader = labelHeader + "<small><font color='red'>" + getString(R.string.convless14) + "</font></small><br />";
                    }
                    else{
                        if(mParticipante.getEdadMeses()<6 && !mParticipante.getProcesos().getEstudio().contains("UO1")){
                            labelHeader = labelHeader + "<font color='red'>No se toman muestras<br /></font>";
                        }else {
                            if ((mParticipante.getProcesos().getConmx().matches("No") || mParticipante.getProcesos().getConmxbhc().matches("No")) && (mParticipante.getProcesos().getConsDeng().matches("Si") || mParticipante.getProcesos().getReConsDeng().matches("Si"))){
                                labelHeader = labelHeader + "<font color='blue'>" + getString(R.string.pendiente_condengue) +"</font><br/>";
                            }
                            if (mParticipante.getProcesos().getEstudio().equals("Cohorte BB")) {
                                if (mParticipante.getEdadMeses() >= 6) {
                                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                    if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                                        if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                }
                            } else if (mParticipante.getProcesos().getEstudio().equals("Influenza")) {
                                if (mParticipante.getEdadMeses() >= 6 && mParticipante.getEdadMeses() < 24) {
                                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font></small>";
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                                            labelHeader = labelHeader + "<small><font color='#B941E0'>No tomar BHC<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                    if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")) {
                                        if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                } else {
                                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 7cc en tubo Rojo<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                    if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                                        if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                }
                            } else if (mParticipante.getProcesos().getEstudio().equals("Influenza  Cohorte BB")) {
                                if (mParticipante.getEdadMeses() >= 6 && mParticipante.getEdadMeses() < 24) {
                                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font></small>";
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                                            labelHeader = labelHeader + "<small><font color='#B941E0'>No tomar BHC<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                    if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")) {
                                        if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                } else {
                                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                    if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                                        if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                }
                            } else if (mParticipante.getProcesos().getEstudio().equals("Dengue")) {
                                //De 2 años a 14 años
                                if (mParticipante.getEdadMeses() >= 24 && mParticipante.getEdadMeses() < 180) {
                                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 7cc en tubo Rojo<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                    if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                                        if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                } else { //De 15 Años a más
                                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                                            //MA2020. 6 PBMC Y 6 ROJO
                                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 12cc en tubo Rojo<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                    if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                                        if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                }
                            } else if (mParticipante.getProcesos().getEstudio().equals("Dengue  Influenza")) {
                                //De 2 años a 14 años
                                if (mParticipante.getEdadMeses() >= 24 && mParticipante.getEdadMeses() < 180) {
                                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='red'>Tomar 7cc en tubo Rojo<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                    if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                                        if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                                            labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                        } else {
                                            labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                        }
                                        pendiente = true;
                                    }
                                }
                            }
                            //MA 2018
                            else if (mParticipante.getProcesos().getEstudio().equals("CH Familia") || mParticipante.getProcesos().getEstudio().equals("CH Familia  Tcovid")) {
                                labelHeader += getVolumenCHF();

                            } else if (mParticipante.getProcesos().getEstudio().equals("Influenza  CH Familia")) {
                                labelHeader += getVolumenInfluenzaCHF();

                            } else if (mParticipante.getProcesos().getEstudio().equals("Dengue    CH Familia") || mParticipante.getProcesos().getEstudio().equals("Dengue    CH Familia  Tcovid")) {
                                labelHeader += getVolumenDengueCHF();

                            } else if (mParticipante.getProcesos().getEstudio().equals("Dengue  Influenza  CH Familia") || mParticipante.getProcesos().getEstudio().equals("Dengue  Influenza  CH Familia  Tcovid")) {
                                labelHeader += getVolumenDengueInfluenzaCHF();

                                //MA2020 Volumnes UO1 y combinaciones
                            } else if (mParticipante.getProcesos().getEstudio().equals("UO1")) {
                                labelHeader += getVolumenUO1(mParticipante);

                            } else if (mParticipante.getProcesos().getEstudio().equals("UO1  CH Familia") || mParticipante.getProcesos().getEstudio().equals("UO1  CH Familia  Tcovid")) {
                                labelHeader += getVolumenUO1CHF(mParticipante);

                            } else if (mParticipante.getProcesos().getEstudio().equals("Dengue  UO1")) {
                                labelHeader += getVolumenUO1DengueOrUO1CHFDengue(mParticipante);

                            } else if (mParticipante.getProcesos().getEstudio().equals("Dengue  UO1  CH Familia") || mParticipante.getProcesos().getEstudio().equals("Dengue  UO1  CH Familia  Tcovid")) {
                                labelHeader += getVolumenUO1DengueOrUO1CHFDengue(mParticipante);

                            }
                            if (mParticipante.getProcesos().getConvalesciente().matches("Si") && mParticipante.getEdadMeses() >= 24) {
                                //MA2021. Quitar restriccion para UO1-PBMC. Mostrar siempre mensaje convaleciente
                                /*if (mParticipante.getProcesos().getEstudio().contains("UO1")) {//MA2020 solo tomar convaleciente si es PBMC
                                    if (mParticipante.getProcesos().getConmx().matches("No") && mParticipante.getProcesos().getPbmc().matches("Si"))
                                        labelHeader = labelHeader + "<font color='#de3163'>Tomar 5cc de convaleciente (Tubo rojo o PBMC)<br /></font>";
                                }else*/
                                if (!mParticipante.getProcesos().getEstudio().equals("Influenza  CH Familia")) {
                                    //AL finalizar MA 2018, se solicita siempre muestre mensaje de muestra convasleciente.  28062018
                                    //if (mParticipante.getProcesos().getConmx().matches("No")) {
                                    //if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                                    labelHeader = labelHeader + "<small><font color='#de3163'>Tomar 5cc de convaleciente (Tubo rojo o PBMC)<br /></font></small>";
                                    /*} else {
                                        labelHeader = labelHeader + "<small><font color='#de3163'>Tomar 5cc de convaleciente<br /></font></small>";
                                        }
                                    }*/
                                }
                            }
                            if ((mParticipante.getProcesos().getRetoma() != null && mParticipante.getProcesos().getVolRetoma() != null)) {
                                if ((mParticipante.getProcesos().getRetoma().matches("Si")) && mUser.getMuestra()) {
                                    labelHeader = labelHeader + "<small><font color='red'>" + getString(R.string.retoma) + ": " + mParticipante.getProcesos().getVolRetoma() + "cc </font></small><br />";
                                }
                            }
                        }
                         //deshabilitar adicional CHF para Covid19 y Muestra adicional Dengue parte E para MA2021
                        //Muestra adicional CHF para Covid19
                        if (mParticipante.getProcesos().getMuestraCovid()!=null && (mParticipante.getProcesos().getMuestraCovid().matches("Si") || mParticipante.getProcesos().getMuestraCovid().matches(Constants.MX_EDTA_CITRATO))){
                            labelHeader += getVolumenCHFAdicionalCovid19();
                            pendiente=true;
                        }/*
                        //Muestra adicional Dengue parte E
                        if (mParticipante.getProcesos().getMxDenParteE()!=null && mParticipante.getProcesos().getMxDenParteE().matches("Si")){
                            if (mParticipante.getProcesos().getConmx().matches("Si") && mParticipante.getProcesos().getConmxbhc().matches("Si")) {
                                labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                                pendiente=true;
                            }
                        }*/
                    }
                }

                //Nuevo orden
                if (mParticipante.getProcesos().getEnCasa().matches("Si") && mUser.getEncuestaCasa()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.house_survey_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getEnCasaChf().matches("Si") && mUser.getEncuestaCasa()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.housechf_survey_missing) + "</font></small><br />";
                    pendiente=true;
                }
                //MA2020
                /*if (mParticipante.getProcesos().getEnCasaSa().matches("Si") && mUser.getEncuestaCasa()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.housesa_survey_missing) + "</font></small><br />";
                    pendiente=true;
                }*/
                if (mParticipante.getProcesos().getEncPart().matches("Si") && mUser.getEncuestaParticipante()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.part_survey_missing) + "</font></small><br />";
                    pendiente=true;
                }
                //MA2020
                /*if (mParticipante.getProcesos().getEncPartSa().matches("Si") && mUser.getEncuestaParticipante()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.partsa_survey_missing) + "</font></small><br />";
                    pendiente=true;
                }*/
                if (mParticipante.getProcesos().getConsCovid19().matches("Si") && mUser.getConsentimiento()){
                    if (!mParticipante.getProcesos().getEstudio().trim().contains("Influenza")){
                        labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.cons_missing_covid19) + "</font></small><br />";
                    }else{
                        labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.reconsflu_missing) + "</font></small><br />";
                    }
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getConsChf()!=null && mParticipante.getProcesos().getConsChf().matches("Si") && mUser.getConsentimiento()){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.chf_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getCuestCovid()!=null && !mParticipante.getProcesos().getCuestCovid().matches("No")){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.cuest_covid19_missing) + "</font></small><br />";
                    pendiente=true;
                }

                if (mParticipante.getProcesos().getConsFlu().matches("Si") && mUser.getConsentimiento()){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.consflu_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getConsDeng().matches("Si") && mUser.getConsentimiento()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.consden_missing) + "</font></small><br />";//MA2021
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getReConsDeng().matches("Si") && mUser.getConsentimiento()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.consden_missing_d) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getConsDenParteE()!=null && mParticipante.getProcesos().getConsDenParteE().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.consden_missing_e) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getReConsChf18()!=null && mParticipante.getProcesos().getReConsChf18().matches("Si") && mUser.getConsentimiento()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.reconchf18_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getZika().matches("Si") && mUser.getConsentimiento()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.zika_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getConsSa()!=null && mParticipante.getProcesos().getConsSa().matches("Si") && mUser.getConsentimiento()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.cons_sa_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getEncLacMat().matches("Si") && mUser.getEncuestaLactancia()){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.maternal_survey_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getPesoTalla().matches("Si") && mUser.getPesoTalla()){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.weigth_heigth_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if ((mParticipante.getProcesos().getDatosParto().matches("Si") || mParticipante.getProcesos().getcDatosParto().matches("Si")) && mUser.getDatosparto()){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.parto_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getInfoVacuna().matches("Si") && mUser.getVacunas()){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.vaccines_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getDatosVisita().matches("Si")){ //15032019. Permitir aunque no sea terreno if (mParticipante.getProcesos().getDatosVisita().matches("Si") && mUser.getVisitas()){
                    labelHeader = labelHeader + "<small><font color='blue'>Pediente datos casa</font></small><br />";
                    pendiente=true;
                }
                if (!mParticipante.getProcesos().getCoordenadas().equals("0") && mUser.getConsentimiento()){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.addresschange_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if ((mParticipante.getProcesos().getObsequio().matches("Si"))){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.gift_missing) + "</font></small><br />";
                }
                if ((mParticipante.getProcesos().getObsequioChf().matches("Si"))){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.gift_chf_missing) + "</font></small><br />";
                }
                if (mParticipante.getProcesos().getAdn().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='red'>Pendiente de ADN, Informar a LAB para toma.</font></small><br />";
                }
            }
            else{
                labelHeader = labelHeader + "<small><font color='blue'>No tiene procedimientos pendientes<br /></font></small>";
            }
        }
        textView.setText(Html.fromHtml(labelHeader));
    }

    private String getVolumenCHF(){
        String labelHeader = "";
        if (!ingresoChf) {
            //De 6 meses a <2 años
            if (mParticipante.getEdadMeses() >= 6 && mParticipante.getEdadMeses() < 24) {
                if (mParticipante.getProcesos().getConmx().matches("No")) {
                    if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font></small>";
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                    }
                    pendiente = true;
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")) {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                    pendiente = true;
                }
            } else //De 2 años - < 14 Años
                if (mParticipante.getEdadMeses() >= 24 && mParticipante.getEdadMeses() < 168) {
                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                            labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                        }else{
                            labelHeader = labelHeader + "<small><font color='red'>Tomar 7cc en tubo Rojo<br /></font></small>";
                        }
                        pendiente = true;
                    }
                    if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                        pendiente = true;
                    }
                } else //De 14 años y más
                {
                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                            labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                        }else{
                            labelHeader = labelHeader + "<small><font color='red'>Tomar 12cc en tubo Rojo<br /></font></small>";
                        }

                        pendiente = true;
                    }
                    if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                        pendiente = true;
                    }
                }
        }else{
            labelHeader = labelHeader + "<font color='blue'>Tomar muestra<br /></font>";
            pendiente = true;
        }
        return labelHeader;
    }

    private String getVolumenInfluenzaCHF(){
        String labelHeader = "";
        if (!ingresoChf) {
            //De 6 meses a <2 años
            if (mParticipante.getEdadMeses() >= 6 && mParticipante.getEdadMeses() < 24) {
                if (mParticipante.getProcesos().getConmx().matches("No")) {
                    if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font></small>";
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                        labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                    }
                    pendiente = true;
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")) {
                    if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                    }
                    pendiente = true;
                } //De 2 años y < 14 Años
            } else if (mParticipante.getEdadMeses() >= 24 && mParticipante.getEdadMeses() < 168) {
                if (mParticipante.getProcesos().getConmx().matches("No")) {
                    if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 7cc en tubo Rojo<br /></font></small>";
                    }
                    pendiente = true;
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                    if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                    }
                    pendiente = true;
                }
            } else //De 14 años y más
            {
                if (mParticipante.getProcesos().getConmx().matches("No")) {
                    if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 12cc en tubo Rojo<br /></font></small>";
                    }
                    pendiente = true;
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                    if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                    }
                    pendiente = true;
                }
            }
        }else{
            labelHeader = labelHeader + "<font color='blue'>Tomar muestra<br /></font>";
            pendiente = true;
        }
        return labelHeader;
    }

    private String getVolumenDengueCHF(){
        String labelHeader = "";
        if (!ingresoChf) {
            //De 2 años  y < 14 a años
            if (mParticipante.getEdadMeses() >= 24 && mParticipante.getEdadMeses() < 168) {
                if (mParticipante.getProcesos().getConmx().matches("No")) {
                    if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 7cc en tubo Rojo<br /></font></small>";
                    }
                    pendiente = true;
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                    if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                    }
                    pendiente = true;
                }
            } else //De 14 años y más
            {
                if (mParticipante.getProcesos().getConmx().matches("No")) {
                    if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 12cc en tubo Rojo<br /></font></small>";
                    }
                    pendiente = true;
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                    if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                    }
                    pendiente = true;
                }
            }
        }else{
            labelHeader = labelHeader + "<font color='blue'>Tomar muestra<br /></font>";
            pendiente = true;
        }
        return labelHeader;
    }

    private String getVolumenDengueInfluenzaCHF(){
        String labelHeader = "";
        if (!ingresoChf) {
            //Desde 2 años y < 14 Años
            if (mParticipante.getEdadMeses() >= 24 && mParticipante.getEdadMeses() < 168) {
                if (mParticipante.getProcesos().getConmx().matches("No")) {
                    if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 7cc en tubo Rojo<br /></font></small>";
                    }
                    pendiente = true;
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                    if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                    }
                    pendiente = true;
                }
            } else //De 14 años y más
            {
                if (mParticipante.getProcesos().getConmx().matches("No")) {
                    if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 12cc en tubo Rojo<br /></font></small>";
                    }
                    pendiente = true;
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                    if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                    }
                    pendiente = true;
                }
            }
        }else{
            labelHeader = labelHeader + "<font color='blue'>Tomar muestra<br /></font>";
            pendiente = true;
        }
        return labelHeader;
    }

    private String getVolumenUO1(Participante mParticipante) {
        String labelHeader = "";
        //menores de 6 meses
        if (mParticipante.getEdadMeses() < 6) {
            if (mParticipante.getProcesos().getConmx().matches("No")) {
                if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                    labelHeader = labelHeader + "<font color='red'>No tomar muestras<br /></font>";
                } else {
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                    pendiente=true;
                }
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")) {
                labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
            }
        } else //De 6 meses a <2 años
            if (mParticipante.getEdadMeses() >= 6 && mParticipante.getEdadMeses() < 24) {
                if (mParticipante.getProcesos().getConmx().matches("No")) {
                    if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 3cc en tubo PBMC<br /></font></small>";
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                        labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
                        pendiente=true;
                    } else {
                        labelHeader = labelHeader + "<font color='red'>No tomar serología<br /></font>";
                    }
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")) {
                    labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
                }
            } else //De 2 años - < 14 Años
                if (mParticipante.getEdadMeses() >= 24 && mParticipante.getEdadMeses() < 168) {
                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                            labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                            pendiente=true;
                        } else {
                            labelHeader = labelHeader + "<font color='red'>No tomar serología<br /></font>";
                        }
                    }
                    if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                            if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                                labelHeader = labelHeader + "<font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font>";
                            } else {
                                labelHeader = labelHeader + "<font color='#B941E0'>Tomar 1cc para BHC<br /></font>";
                            }
                            pendiente=true;
                        }else {
                            labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
                        }
                    }
                }
        return labelHeader;
    }

    private String getVolumenUO1CHF(Participante mParticipante) {
        String labelHeader = "";
        //menores de 6 meses
        if (mParticipante.getEdadMeses() < 6) {
            if (mParticipante.getProcesos().getConmx().matches("No")) {
                if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                    labelHeader = labelHeader + "<font color='red'>No tomar muestras<br /></font>";
                } else {
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                    pendiente=true;
                }
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")) {
                labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
            }
        } else //De 6 meses a <2 años
            if (mParticipante.getEdadMeses() >= 6 && mParticipante.getEdadMeses() < 24) {
                if (mParticipante.getProcesos().getConmx().matches("No")) {
                    if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 3cc en tubo PBMC<br /></font></small>";
                        labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                        labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
                        pendiente=true;
                    } else {
                        labelHeader = labelHeader + "<font color='red'>No tomar serología<br /></font>";
                    }
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")) {
                    labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
                }
            } else //De 2 años - < 14 Años
                if (mParticipante.getEdadMeses() >= 24 && mParticipante.getEdadMeses() < 168) {
                    if (mParticipante.getProcesos().getConmx().matches("No")) {
                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                            labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                            pendiente=true;
                        } else {
                            labelHeader = labelHeader + "<font color='red'>No tomar serología<br /></font>";
                        }
                    }
                    if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                        if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                            if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                                labelHeader = labelHeader + "<font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font>";
                            } else {
                                labelHeader = labelHeader + "<font color='#B941E0'>Tomar 1cc para BHC<br /></font>";
                            }
                            pendiente=true;
                        }else {
                            labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
                        }
                    }
                }
        return labelHeader;
    }

    private String getVolumenUO1DengueOrUO1CHFDengue(Participante mParticipante) {
        String labelHeader = "";
        if (mParticipante.getEdadMeses() >= 24 && mParticipante.getEdadMeses() < 168) {
            if (mParticipante.getProcesos().getConmx().matches("No")) {
                if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                    pendiente=true;
                } else {
                    labelHeader = labelHeader + "<font color='red'>No tomar serología<br /></font>";
                }
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                    if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                    } else {
                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                    }
                    pendiente=true;
                } else {
                    labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
                }
            }
        }
        return labelHeader;
    }

    private String getVolumenCHFAdicionalCovid19() {
        String labelHeader = "";
        if (mParticipante.getProcesos().getMuestraCovid() != null && mParticipante.getProcesos().getMuestraCovid().equalsIgnoreCase(Constants.MX_EDTA_CITRATO)) {
            labelHeader = labelHeader + "<small><font color='red'>Tomar 4cc en tubo Rojo<br /></font></small>";
            labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 4cc en tubo PBMC<br /></font></small>";
            labelHeader = labelHeader + "<small><font color='#bc7201'>Tomar 6cc en tubo Amarillo<br /></font></small>";
        } else {
            //no tiene que tener pendiente mx principal
            if (mParticipante.getProcesos().getConmx().matches("Si") && mParticipante.getProcesos().getConmxbhc().matches("Si")) {
                //menores de 6 meses
                if (mParticipante.getEdadMeses() < 6) {
                    //labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>No tomar muestra<br /></font></small>";
                    pendiente = true;
                } //De 6 meses a <2 años
                else if (mParticipante.getEdadMeses() >= 6 && mParticipante.getEdadMeses() < 24) {
                    //labelHeader = labelHeader + "<small><font color='red'>Tomar 4cc en tubo Rojo<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 4cc en tubo Rojo<br /></font></small>";
                    pendiente = true;
                }  //De 2 años - < 14 Años
                else if (mParticipante.getEdadMeses() >= 24 && mParticipante.getEdadMeses() < 168) {
                    //labelHeader = labelHeader + "<small><font color='red'>Tomar 8cc en tubo Rojo<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                    pendiente = true;
                } else //De 14 años y más
                {
                    //labelHeader = labelHeader + "<small><font color='red'>Tomar 12cc en tubo Rojo<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                    pendiente = true;
                }
            }
        }
        return labelHeader;
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                getParticipanteData();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            try {
                onPrepareOptionsMenu();
                String[] menu_info = getResources().getStringArray(R.array.menu_info);
                gridView.setAdapter(new MenuInfoAdapter(getApplicationContext(), R.layout.menu_item_2, menu_info
                        ,mVisitasTerreno.size(),mPyTs.size(),mEncuestasCasas.size(),mEncuestasParticipantes.size(),
                        mEncuestasLactancias.size(),mVacunas.size(),mMuestras.size(),mObsequios.size(), mConSA.size(), mDatosPartoBBs.size(), mDatosVisitaTerreno.size() , mDocumentos.size()
                        ,mEncuestasCasasChf.size(), mDatosCoordenadas.size()));
                refreshView();
            }catch (Exception ex){
                dismissProgressDialog();
                Toast toast = Toast.makeText(getApplicationContext(),"ERROR: "+ex.getLocalizedMessage(),Toast.LENGTH_LONG);
                toast.show();
                finish();
            }finally {
                dismissProgressDialog();
            }
        }
    }
}

