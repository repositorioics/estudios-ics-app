package ni.org.ics.estudios.appmovil.muestreoanual.activities;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.*;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaCasaSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaParticipanteSA;
import ni.org.ics.estudios.appmovil.domain.users.UserPermissions;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.MenuInfoAdapter;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.seroprevalencia.activities.NuevaEncuestaCasaSAActivity;
import ni.org.ics.estudios.appmovil.seroprevalencia.activities.NuevaEncuestaParticipanteSAActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.SeroprevalenciaDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

public class MenuInfoActivity extends AbstractAsyncActivity {

    private Integer codigo;
    private static Participante mParticipante = new Participante();
    private static UserPermissions mUser = new UserPermissions();
    private static ArrayList<ReConsentimientoFlu2015> mReConsentimientoFlu = new ArrayList<ReConsentimientoFlu2015>();
    private ArrayList<VisitaTerreno> mVisitasTerreno = new ArrayList<VisitaTerreno>();
    private ArrayList<PesoyTalla> mPyTs = new ArrayList<PesoyTalla>();
    private ArrayList<EncuestaCasa> mEncuestasCasas = new ArrayList<EncuestaCasa>();
    private ArrayList<EncuestaParticipante> mEncuestasParticipantes = new ArrayList<EncuestaParticipante>();
    private ArrayList<LactanciaMaterna> mEncuestasLactancias = new ArrayList<LactanciaMaterna>();
    private ArrayList<NewVacuna> mVacunas = new ArrayList<NewVacuna>();
    private ArrayList<ReConsentimientoDen2015> mReConsentimientoDen = new ArrayList<ReConsentimientoDen2015>();
    private ArrayList<Muestra> mMuestras = new ArrayList<Muestra>();
    private ArrayList<Obsequio> mObsequios = new ArrayList<Obsequio>();
    private ArrayList<ConsentimientoZika> mConsentimientoZikas = new ArrayList<ConsentimientoZika>();
    private ArrayList<DatosPartoBB> mDatosPartoBBs = new ArrayList<DatosPartoBB>();
    private ArrayList<DatosVisitaTerreno> mDatosVisitaTerreno = new ArrayList<DatosVisitaTerreno>();
    private ArrayList<Documentos> mDocumentos = new ArrayList<Documentos>();
    private ArrayList<EncuestaCasa> mEncuestasCasasChf = new ArrayList<EncuestaCasa>();
    private ArrayList<EncuestaCasaSA> mEncuestasCasasSa = new ArrayList<EncuestaCasaSA>();
    private ArrayList<EncuestaParticipanteSA> mEncuestasParticipantesSa = new ArrayList<EncuestaParticipanteSA>();
    private List<MessageResource> catRelacionFamiliar = new ArrayList<MessageResource>();

    private String username;
    private SharedPreferences settings;
    private GridView gridView;
    private TextView textView;
    private AlertDialog alertDialog;
    private static final int EXIT = 1;
    private boolean mExitShowing;
    private boolean visExitosa = false;
    private boolean pendiente = false;
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
    private MenuItem zikaItem;
    private MenuItem partoItem;
    private MenuItem datosCasaItem;
    private MenuItem encCasaChfItem;
    private MenuItem encCasaSaItem;
    private MenuItem encPartSaItem;

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
        //mParticipante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        textView = (TextView) findViewById(R.id.label);
        gridView = (GridView) findViewById(R.id.gridView1);

        //if (mParticipante == null){
            estudiosAdapter.open();
            mParticipante = estudiosAdapter.getParticipante(MainDBConstants.codigo  + "="+codigo, null);
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
                        arguments.putString(Constants.TITLE, getString(R.string.info_reconflu));
                        if (mReConsentimientoFlu!=null) arguments.putSerializable(Constants.OBJECTO , mReConsentimientoFlu);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 2:
                        arguments.putString(Constants.TITLE, getString(R.string.info_visit));
                        if (mVisitasTerreno!=null) arguments.putSerializable(Constants.OBJECTO , mVisitasTerreno);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 3:
                        arguments.putString(Constants.TITLE, getString(R.string.info_weight));
                        if (mPyTs!=null) arguments.putSerializable(Constants.OBJECTO , mPyTs);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 4:
                        arguments.putString(Constants.TITLE, getString(R.string.info_survey2));
                        if (mEncuestasCasas!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasCasas);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 5:
                        arguments.putString(Constants.TITLE, getString(R.string.info_survey1));
                        if (mEncuestasParticipantes!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasParticipantes);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 6:
                        arguments.putString(Constants.TITLE, getString(R.string.info_survey3));
                        if (mEncuestasLactancias!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasLactancias);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 7:
                        arguments.putString(Constants.TITLE, getString(R.string.info_vacc));
                        if (mVacunas!=null) arguments.putSerializable(Constants.OBJECTO , mVacunas);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 8:
                        arguments.putInt("codCasa", mParticipante.getCasa().getCodigo());
                        arguments.putInt("codComun", mParticipante.getCodigo());

                        i = new Intent(getApplicationContext(),
                                ListParticipantesCasaActivity.class);
                        break;
                    case 9:
                        arguments.putString(Constants.TITLE, getString(R.string.info_recon));
                        if (mReConsentimientoDen!=null) arguments.putSerializable(Constants.OBJECTO , mReConsentimientoDen);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 10:
                        arguments.putString(Constants.TITLE, getString(R.string.info_sample));
                        if (mMuestras!=null) arguments.putSerializable(Constants.OBJECTO , mMuestras);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 11:
                        arguments.putString(Constants.TITLE, getString(R.string.info_gift));
                        if (mObsequios!=null) arguments.putSerializable(Constants.OBJECTO , mObsequios);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 12:
                        arguments.putString(Constants.TITLE, getString(R.string.info_zika));
                        if (mConsentimientoZikas!=null) arguments.putSerializable(Constants.OBJECTO , mConsentimientoZikas);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 13:
                        arguments.putString(Constants.TITLE, getString(R.string.datos_parto));
                        if (mDatosPartoBBs!=null) arguments.putSerializable(Constants.OBJECTO , mDatosPartoBBs);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 14:
                        arguments.putString(Constants.TITLE, getString(R.string.datos_casa));
                        if (mDatosVisitaTerreno!=null) arguments.putSerializable(Constants.OBJECTO , mDatosVisitaTerreno);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 15:
                        arguments.putString(Constants.TITLE, getString(R.string.info_docs));
                        if (mDocumentos!=null) arguments.putSerializable(Constants.OBJECTO , mDocumentos);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 16:
                        arguments.putString(Constants.TITLE, getString(R.string.info_casachf));
                        if (mEncuestasCasasChf!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasCasasChf);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 17:
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
            zikaItem = menu.findItem(R.id.ZIKA);
            partoItem = menu.findItem(R.id.PARTO);
            datosCasaItem = menu.findItem(R.id.DAT_CASA);
            encCasaChfItem = menu.findItem(R.id.ENCASA_CHF);
            encCasaSaItem = menu.findItem(R.id.ENCASA_SA);
            encPartSaItem = menu.findItem(R.id.ENPART_SA);

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
                if(mUser.getVisitas()){
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
                                NewRecFlu2015Activity.class);
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
            case R.id.SAMPLE:
                if(mUser.getMuestra()){
                    if(mParticipante.getProcesos().getConmx().matches("No")||mParticipante.getProcesos().getConmxbhc().matches("No")){
                        if (mParticipante.getProcesos().getConvalesciente().matches("Na")){
                            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.convless14),Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else{
                            i = new Intent(getApplicationContext(),
                                    NewSampleActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra(ConstantsDB.COD_CASA, mParticipante.getCasa().getCodigo());
                            i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                            i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
                            startActivity(i);
                        }
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
                    if(mParticipante.getProcesos().getDatosParto().matches("Si")){
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
            case R.id.ENCASA_SA:
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
        zikaItem.setVisible(false);
        partoItem.setVisible(false);
        datosCasaItem.setVisible(false);
        encCasaChfItem.setVisible(false);
        encCasaSaItem.setVisible(false);
        encPartSaItem.setVisible(false);
        //la opción de reconsentimiento dengue siempre se va a mostrar
        if ((mParticipante.getProcesos().getConsDeng().matches("Si") || mParticipante.getProcesos().getReConsDeng().matches("Si")) && mUser.getConsentimiento()) reConsDenItem.setVisible(true);

        if ((mUser.getVisitas()&&!visExitosa)){
            visitaItem.setVisible(true);
        }
        else{
            if((mParticipante.getProcesos().getConmx().matches("No") || mParticipante.getProcesos().getConmxbhc().matches("No"))  && mUser.getMuestra() && mParticipante.getEdadMeses()>5) muestraItem.setVisible(true);
            if((mParticipante.getProcesos().getEnCasa().matches("Si") && mUser.getEncuestaCasa())) encCasaItem.setVisible(true);
            if((mParticipante.getProcesos().getEncPart().matches("Si") && mUser.getEncuestaParticipante())) encPartItem.setVisible(true);
            if ((mParticipante.getProcesos().getConsFlu().matches("Si") && mUser.getConsentimiento())) reConsFluItem.setVisible(true);
            if((mParticipante.getProcesos().getPesoTalla().matches("Si") && mUser.getPesoTalla())) pesoTallaItem.setVisible(true);
            if((mParticipante.getProcesos().getEncLacMat().matches("Si") && mUser.getEncuestaLactancia())) encLactItem.setVisible(true);
            if((mParticipante.getProcesos().getInfoVacuna().matches("Si") && mUser.getVacunas())) vacunaItem.setVisible(true);
            if ((mParticipante.getProcesos().getZika().matches("Si") && mUser.getConsentimiento())) zikaItem.setVisible(true);
            if ((mParticipante.getProcesos().getObsequio().matches("Si") && mUser.getObsequio())) obsequioItem.setVisible(true);
            if ((mParticipante.getProcesos().getDatosParto().matches("Si") && mUser.getDatosparto())) partoItem.setVisible(true);
            if ((mParticipante.getProcesos().getDatosVisita().matches("Si") && mUser.getVisitas())) datosCasaItem.setVisible(true);
            if((mParticipante.getProcesos().getEnCasaChf().matches("Si") && mUser.getEncuestaCasa())) encCasaChfItem.setVisible(true);
            if((mParticipante.getProcesos().getEnCasaSa().matches("Si") && mUser.getEncuestaCasa())) encCasaSaItem.setVisible(true);
            if((mParticipante.getProcesos().getEncPartSa().matches("Si") && mUser.getEncuestaParticipante())) encPartSaItem.setVisible(true);
            visitaItem.setVisible(false);
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

    public String getRelacionFamiliar(Integer codigo) {
        String relacionFamiliar = this.getApplicationContext().getString(R.string.sinRelacFam);
        try {
            for (MessageResource message : catRelacionFamiliar) {
                if (message.getCatKey().equalsIgnoreCase(String.valueOf(codigo))) {
                    relacionFamiliar = message.getSpanish();
                    break;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return relacionFamiliar;
    }

    private void getParticipanteData() {
        estudiosAdapter.open();
        if (mParticipante == null)
            mParticipante = estudiosAdapter.getParticipante(MainDBConstants.codigo  + "="+codigo, null);
        mReConsentimientoFlu = estudiosAdapter.getListaReConsentimientoFlu2015(codigo);
        mVisitasTerreno=estudiosAdapter.getListaVisitaTerreno(codigo);
        mPyTs = estudiosAdapter.getListaPesoyTallas(codigo);
        if(mParticipante.getCasa().getCodigo()!=9999) mEncuestasCasas = estudiosAdapter.getListaEncuestaCasas(mParticipante.getCasa().getCodigo());
        mEncuestasParticipantes = estudiosAdapter.getListaEncuestaParticipantes(codigo);
        mEncuestasLactancias = estudiosAdapter.getListaLactanciaMaternas(codigo);
        mReConsentimientoDen=estudiosAdapter.getListaReConsentimientoDen2015(codigo);
        mMuestras=estudiosAdapter.getListaMuestras(codigo);
        mObsequios=estudiosAdapter.getListaObsequios(codigo);
        mConsentimientoZikas=estudiosAdapter.getListaConsentimientoZika(codigo);
        mDatosVisitaTerreno = estudiosAdapter.getListaDatosVisitaTerreno(codigo);
        mUser = estudiosAdapter.getPermisosUsuario(ConstantsDB.USERNAME + "='" +username+"'", null);
        String filtro = ConstantsDB.CODIGO + "=" + codigo;
        mDatosPartoBBs = estudiosAdapter.getDatosPartoBBs(filtro, null);
        mVacunas=estudiosAdapter.getNewVacunas(filtro, null);
        mDocumentos =estudiosAdapter.getDocumentoss(filtro, null);
        if(mParticipante.getCasa().getCodigo()!=9999){
            if (mParticipante.getProcesos().getCasaCHF()!=null && !mParticipante.getProcesos().getCasaCHF().isEmpty())
                mEncuestasCasasChf = estudiosAdapter.getListaEncuestaCasasChf(mParticipante.getProcesos().getCasaCHF());
            mEncuestasCasasSa = (ArrayList)estudiosAdapter.getEncuestasCasaSA(SeroprevalenciaDBConstants.casa + "=" + mParticipante.getCasa().getCodigo() , null);
        }
        mEncuestasParticipantesSa = (ArrayList)estudiosAdapter.getEncuestasParticipanteSA(SeroprevalenciaDBConstants.participante + "=" + mParticipante.getCodigo() , null);
        catRelacionFamiliar = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CP_CAT_RFTUTOR'", null);
        estudiosAdapter.close();
    }

    private void refreshView() {
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


        Integer relacionFam = mParticipante.getProcesos().getRelacionFam();
        String relacion = getRelacionFamiliar(relacionFam);

        labelHeader = labelHeader + "<small><font color='black'>"+mParticipante.getProcesos().getTutor();
        labelHeader = labelHeader +" - ("+ relacion +")" +"</font></small><br />";
        labelHeader = labelHeader + "<small><font color='black'>Personas en casa: "+mParticipante.getProcesos().getCuantasPers()+"</font></small><br />";

        if (mParticipante.getProcesos().getEstPart().equals(0) && (mParticipante.getProcesos().getReConsDeng()==null || mParticipante.getProcesos().getReConsDeng().matches("No"))){
            labelHeader = labelHeader + "<small><font color='red'>" + getString(R.string.retired_error) + "</font></small><br />";
        }
        else{
            if (mParticipante.getProcesos().getEstPart().equals(0) && (mParticipante.getProcesos().getReConsDeng()!=null && mParticipante.getProcesos().getReConsDeng().matches("Si"))){
                labelHeader = labelHeader + "<small><font color='red'>" + getString(R.string.retired_error) + "</font></small><br />";
            }
            if (mParticipante.getProcesos().getPosZika().matches("Si")) labelHeader = labelHeader + "<small><font color='red'>Participante positivo a ZIKA</font></small><br />";

            if (mParticipante.getProcesos().getConsFlu().matches("Si")|| mParticipante.getProcesos().getPesoTalla().matches("Si")
                    || mParticipante.getProcesos().getEnCasa().matches("Si")||mParticipante.getProcesos().getEncPart().matches("Si")
                    || mParticipante.getProcesos().getEnCasaChf().matches("Si")|| mParticipante.getProcesos().getEnCasaSa().matches("Si")||mParticipante.getProcesos().getEncPartSa().matches("Si")
                    || mParticipante.getProcesos().getEncLacMat().matches("Si")||mParticipante.getProcesos().getInfoVacuna().matches("Si")
                    || mParticipante.getProcesos().getConsDeng().matches("Si") || mParticipante.getProcesos().getObsequio().matches("Si")
                    || mParticipante.getProcesos().getConmx().matches("No") || mParticipante.getProcesos().getConmxbhc().matches("No")|| mParticipante.getProcesos().getZika().matches("Si")
                    || mParticipante.getProcesos().getAdn().matches("Si")|| mParticipante.getProcesos().getDatosParto().matches("Si")|| mParticipante.getProcesos().getDatosVisita().matches("Si")
                    || !mParticipante.getProcesos().getConvalesciente().matches("No")
                    || (mParticipante.getProcesos().getReConsDeng()!=null && mParticipante.getProcesos().getReConsDeng().matches("Si"))){
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
                        if(mParticipante.getEdadMeses()<6){
                            labelHeader = labelHeader + "<font color='red'>No se toman muestras<br /></font>";
                        }else
                        if(mParticipante.getProcesos().getEstudio().equals("Cohorte BB")){
                            if (mParticipante.getEdadMeses()>=6){
                                if (mParticipante.getProcesos().getConmx().matches("No")){
                                    if (mParticipante.getProcesos().getPbmc().matches("Si")){
                                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font></small>";
                                    }
                                    else{
                                        labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                                    }
                                    pendiente =true;
                                }
                                if (mParticipante.getProcesos().getConmxbhc().matches("No")){
                                    if (mParticipante.getProcesos().getPaxgene().matches("Si")){
                                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                    }
                                    else{
                                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                    }
                                    pendiente =true;
                                }
                            }
                        }
                        else if(mParticipante.getProcesos().getEstudio().equals("Influenza")){
                            if(mParticipante.getEdadMeses()>=6 && mParticipante.getEdadMeses()<24){
                                if (mParticipante.getProcesos().getConmx().matches("No")){
                                    if (mParticipante.getProcesos().getPbmc().matches("Si")){
                                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font></small>";
                                        labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                                        labelHeader = labelHeader + "<small><font color='#B941E0'>No tomar BHC<br /></font></small>";
                                    }
                                    else{
                                        labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                                    }
                                    pendiente =true;
                                }
                                if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")){
                                    if (mParticipante.getProcesos().getPaxgene().matches("Si")){
                                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                    }
                                    else{
                                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                    }
                                    pendiente =true;
                                }
                            }
                            else {
                                if (mParticipante.getProcesos().getConmx().matches("No")){
                                    if (mParticipante.getProcesos().getPbmc().matches("Si")){
                                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                                        labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                                    }
                                    else{
                                        labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                                    }
                                    pendiente =true;
                                }
                                if (mParticipante.getProcesos().getConmxbhc().matches("No")){
                                    if (mParticipante.getProcesos().getPaxgene().matches("Si")){
                                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                    }
                                    else{
                                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                    }
                                    pendiente =true;
                                }
                            }
                        }
                        else if(mParticipante.getProcesos().getEstudio().equals("Influenza  Cohorte BB")){
                            if(mParticipante.getEdadMeses()>=6 && mParticipante.getEdadMeses()<24){
                                if (mParticipante.getProcesos().getConmx().matches("No")){
                                    if (mParticipante.getProcesos().getPbmc().matches("Si")){
                                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font></small>";
                                        labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                                        labelHeader = labelHeader + "<small><font color='#B941E0'>No tomar BHC<br /></font></small>";
                                    }
                                    else{
                                        labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                                    }
                                    pendiente =true;
                                }
                                if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")){
                                    if (mParticipante.getProcesos().getPaxgene().matches("Si")){
                                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                    }
                                    else{
                                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                    }
                                    pendiente =true;
                                }
                            }
                            else {
                                if (mParticipante.getProcesos().getConmx().matches("No")){
                                    if (mParticipante.getProcesos().getPbmc().matches("Si")){
                                        labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                                        labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                                    }
                                    else{
                                        labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                                    }
                                    pendiente =true;
                                }
                                if (mParticipante.getProcesos().getConmxbhc().matches("No")){
                                    if (mParticipante.getProcesos().getPaxgene().matches("Si")){
                                        labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                    }
                                    else{
                                        labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                    }
                                    pendiente =true;
                                }
                            }
                        }
                        else if(mParticipante.getProcesos().getEstudio().equals("Dengue")){
                            if (mParticipante.getProcesos().getConmx().matches("No")){
                                if (mParticipante.getProcesos().getPbmc().matches("Si")){
                                    labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                                }
                                else{
                                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                                }
                                pendiente =true;
                            }
                            if (mParticipante.getProcesos().getConmxbhc().matches("No")){
                                if (mParticipante.getProcesos().getPaxgene().matches("Si")){
                                    labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                }
                                else{
                                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                }
                                pendiente =true;
                            }
                        }
                        else if(mParticipante.getProcesos().getEstudio().equals("Dengue  Influenza")){
                            if (mParticipante.getProcesos().getConmx().matches("No")){
                                if (mParticipante.getProcesos().getPbmc().matches("Si")){
                                    labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                                    labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                                }
                                else{
                                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                                }
                                pendiente =true;
                            }
                            if (mParticipante.getProcesos().getConmxbhc().matches("No")){
                                if (mParticipante.getProcesos().getPaxgene().matches("Si")){
                                    labelHeader = labelHeader + "<small><font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                                }
                                else{
                                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                                }
                                pendiente =true;
                            }
                        }
                        //MA 2018
                        else if(mParticipante.getProcesos().getEstudio().equals("CH Familia")){
                            labelHeader += getVolumenCHF();

                        } else if(mParticipante.getProcesos().getEstudio().equals("Influenza  CH Familia")){
                            labelHeader += getVolumenInfluenzaCHF();

                        } else if(mParticipante.getProcesos().getEstudio().equals("Dengue    CH Familia")){
                            labelHeader += getVolumenDengueCHF();

                        } else if(mParticipante.getProcesos().getEstudio().equals("Dengue  Influenza  CH Familia")){
                            labelHeader += getVolumenDengueInfluenzaCHF();

                        }
                        if (mParticipante.getProcesos().getConvalesciente().matches("Si")&& mParticipante.getEdadMeses()>=24){
                            if(!mParticipante.getProcesos().getEstudio().equals("Influenza  CH Familia")) {
                                //AL finalizar MA 2018, se solicita siempre muestre mensaje de muestra convasleciente.  28062018
                                //if (mParticipante.getProcesos().getConmx().matches("No")) {
                                //if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                                labelHeader = labelHeader + "<small><font color='#de3163'>Tomar 5cc de convaleciente<br /></font></small>";
                                    /*} else {
                                        labelHeader = labelHeader + "<small><font color='#de3163'>Tomar 5cc de convaleciente<br /></font></small>";
                                    }
                                }*/
                            }
                        }
                        if ((mParticipante.getProcesos().getRetoma()!=null && mParticipante.getProcesos().getVolRetoma()!=null)){
                            if ((mParticipante.getProcesos().getRetoma().matches("Si")) && mUser.getMuestra()){
                                labelHeader = labelHeader + "<small><font color='red'>" + getString(R.string.retoma) +": " + mParticipante.getProcesos().getVolRetoma() + "cc </font></small><br />";
                            }
                        }
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
                if (mParticipante.getProcesos().getEnCasaSa().matches("Si") && mUser.getEncuestaCasa()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.housesa_survey_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getEncPart().matches("Si") && mUser.getEncuestaParticipante()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.part_survey_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getEncPartSa().matches("Si") && mUser.getEncuestaParticipante()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.partsa_survey_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getConsFlu().matches("Si") && mUser.getConsentimiento()){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.consflu_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getConsDeng().matches("Si") && mUser.getConsentimiento()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.consden_missing_abc) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getReConsDeng().matches("Si") && mUser.getConsentimiento()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.consden_missing_d) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getZika().matches("Si") && mUser.getConsentimiento()) {
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.zika_missing) + "</font></small><br />";
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
                if (mParticipante.getProcesos().getDatosParto().matches("Si") && mUser.getDatosparto()){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.parto_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getInfoVacuna().matches("Si") && mUser.getVacunas()){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.vaccines_missing) + "</font></small><br />";
                    pendiente=true;
                }
                if (mParticipante.getProcesos().getDatosVisita().matches("Si") && mUser.getVisitas()){
                    labelHeader = labelHeader + "<small><font color='blue'>Pediente datos casa</font></small><br />";
                    pendiente=true;
                }
                if ((mParticipante.getProcesos().getObsequio().matches("Si"))){
                    labelHeader = labelHeader + "<small><font color='blue'>" + getString(R.string.gift_missing) + "</font></small><br />";
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
        //De 6 meses a <2 años
        if(mParticipante.getEdadMeses()>=6 && mParticipante.getEdadMeses()<24){
            if (mParticipante.getProcesos().getConmx().matches("No")){
                labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                pendiente =true;
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No")){
                labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                pendiente =true;
            }
        }else //De 2 años - < 14 Años
            if(mParticipante.getEdadMeses()>=24 && mParticipante.getEdadMeses()<168){
                if (mParticipante.getProcesos().getConmx().matches("No")){
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                    pendiente =true;
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No")){
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                    pendiente =true;
                }
            }else //De 14 años y más
            {
                if (mParticipante.getProcesos().getConmx().matches("No")){
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 12cc en tubo Rojo<br /></font></small>";
                    pendiente =true;
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No")){
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                    pendiente =true;
                }
            }
        return labelHeader;
    }

    private String getVolumenInfluenzaCHF(){
        String labelHeader = "";
        //De 6 meses a <2 años
        if(mParticipante.getEdadMeses()>=6 && mParticipante.getEdadMeses()<24){
            if (mParticipante.getProcesos().getConmx().matches("No")){
                if (mParticipante.getProcesos().getPbmc().matches("Si")){
                    labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                    labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
                }else{
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                }
                pendiente =true;
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")){
                if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                }else{
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
                pendiente =true;
            } //De 2 años y < 14 Años
        }else if(mParticipante.getEdadMeses()>=24 && mParticipante.getEdadMeses()<168) {
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
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
                pendiente = true;
            }
        }else //De 14 años y más
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
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
                pendiente = true;
            }
        }
        return labelHeader;
    }

    private String getVolumenDengueCHF(){
        //De 2 años  y < 14 a años
        String labelHeader = "";
        if(mParticipante.getEdadMeses()>=24 && mParticipante.getEdadMeses()<168) {
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
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
                pendiente = true;
            }
        }else //De 14 años y más
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
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
                pendiente = true;
            }
        }
        return labelHeader;
    }

    private String getVolumenDengueInfluenzaCHF(){
        String labelHeader = "";
        //Desde 2 años y < 14 Años
        if(mParticipante.getEdadMeses()>=24 && mParticipante.getEdadMeses()<168) {
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
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
                pendiente = true;
            }
        }else //De 14 años y más
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
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
                pendiente = true;
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
            }finally {
                //estudiosAdapter.close();
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            try {
            onPrepareOptionsMenu();
            String[] menu_info = getResources().getStringArray(R.array.menu_info);
            gridView.setAdapter(new MenuInfoAdapter(getApplicationContext(), R.layout.menu_item_2, menu_info, mReConsentimientoFlu.size()
                    ,mVisitasTerreno.size(),mPyTs.size(),mEncuestasCasas.size(),mEncuestasParticipantes.size(),
                    mEncuestasLactancias.size(),mVacunas.size(),mReConsentimientoDen.size(),mMuestras.size(),mObsequios.size(),mConsentimientoZikas.size(), mDatosPartoBBs.size(), mDatosVisitaTerreno.size() , mDocumentos.size()
                    ,mEncuestasCasasChf.size(), mEncuestasCasasSa.size(), mEncuestasParticipantesSa.size()));
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

