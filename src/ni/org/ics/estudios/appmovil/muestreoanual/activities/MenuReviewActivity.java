package ni.org.ics.estudios.appmovil.muestreoanual.activities;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.MenuReviewAdapter;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.*;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.SeroprevalenciaDBConstants;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MenuReviewActivity extends AbstractAsyncActivity {

	private ArrayList<VisitaTerreno> mVisitasTerreno = new ArrayList<VisitaTerreno>();
	private ArrayList<PesoyTalla> mPyTs = new ArrayList<PesoyTalla>();
	private ArrayList<EncuestaCasa> mEncuestasCasas = new ArrayList<EncuestaCasa>();
	private ArrayList<EncuestaParticipante> mEncuestasParticipantes = new ArrayList<EncuestaParticipante>();
	private ArrayList<LactanciaMaterna> mEncuestasLactancias = new ArrayList<LactanciaMaterna>();
    private ArrayList<NewVacuna> mVacunas = new ArrayList<NewVacuna>();
	private ArrayList<EncuestaSatisfaccion> mEncuestasS = new ArrayList<EncuestaSatisfaccion>();
	private ArrayList<Muestra> mMuestras = new ArrayList<Muestra>();
	private ArrayList<Obsequio> mObsequios = new ArrayList<Obsequio>();
    private ArrayList<DatosPartoBB> mDatosPartoBBs = new ArrayList<DatosPartoBB>();
    private ArrayList<DatosVisitaTerreno> mDatosVisitaTerreno = new ArrayList<DatosVisitaTerreno>();
    private ArrayList<Documentos> mDocumentos = new ArrayList<Documentos>();
    private ArrayList<EncuestaCasa> mEncuestasCasasChf = new ArrayList<EncuestaCasa>();
    //MA2020
    /*private ArrayList<EncuestaCasaSA> mEncuestasCasasSa = new ArrayList<EncuestaCasaSA>();
    private ArrayList<EncuestaParticipanteSA> mEncuestasParticipantesSa = new ArrayList<EncuestaParticipanteSA>();*/
    private ArrayList<ParticipanteSeroprevalencia> mConSA = new ArrayList<ParticipanteSeroprevalencia>();
	private GridView gridView;
	private TextView textView;

    private EstudiosAdapter ca;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_screen);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        ca = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		getData();
		textView = (TextView) findViewById(R.id.label);
		textView.setText(getString(R.string.main_report));
		gridView = (GridView) findViewById(R.id.gridView1);

        new FetchDataTask().execute();

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
				Intent i;
				switch(position) {

                    case 0:
                        arguments.putString(Constants.TITLE, getString(R.string.info_visit));
                        if (mVisitasTerreno != null) arguments.putSerializable(Constants.OBJECTO, mVisitasTerreno);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 1:
                        arguments.putString(Constants.TITLE, getString(R.string.info_weight));
                        if (mPyTs != null) arguments.putSerializable(Constants.OBJECTO, mPyTs);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 2:
                        arguments.putString(Constants.TITLE, getString(R.string.info_survey2));
                        if (mEncuestasCasas != null) arguments.putSerializable(Constants.OBJECTO, mEncuestasCasas);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 3:
                        arguments.putString(Constants.TITLE, getString(R.string.info_survey1));
                        if (mEncuestasParticipantes != null)
                            arguments.putSerializable(Constants.OBJECTO, mEncuestasParticipantes);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 4:
                        arguments.putString(Constants.TITLE, getString(R.string.info_survey3));
                        if (mEncuestasLactancias != null)
                            arguments.putSerializable(Constants.OBJECTO, mEncuestasLactancias);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 5:
                        arguments.putString(Constants.TITLE, getString(R.string.info_vacc));
                        if (mVacunas != null) arguments.putSerializable(Constants.OBJECTO, mVacunas);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 6:
                        arguments.putString(Constants.TITLE, getString(R.string.main_survey));
                        if (mEncuestasS != null) arguments.putSerializable(Constants.OBJECTO, mEncuestasS);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 7:
                        arguments.putString(Constants.TITLE, getString(R.string.info_sample));
                        if (mMuestras != null) arguments.putSerializable(Constants.OBJECTO, mMuestras);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 8:
                        arguments.putString(Constants.TITLE, getString(R.string.info_gift));
                        if (mObsequios != null) arguments.putSerializable(Constants.OBJECTO, mObsequios);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 9:
                        arguments.putString(Constants.TITLE, getString(R.string.info_sa));
                        if (mConSA != null)
                            arguments.putSerializable(Constants.OBJECTO, mConSA);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 10:
                        arguments.putString(Constants.TITLE, getString(R.string.datos_parto));
                        if (mDatosPartoBBs != null) arguments.putSerializable(Constants.OBJECTO, mDatosPartoBBs);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 11:
                        arguments.putString(Constants.TITLE, getString(R.string.datos_casa));
                        if (mDatosVisitaTerreno != null)
                            arguments.putSerializable(Constants.OBJECTO, mDatosVisitaTerreno);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 12:
                        arguments.putString(Constants.TITLE, getString(R.string.info_docs));
                        if (mDocumentos != null) arguments.putSerializable(Constants.OBJECTO, mDocumentos);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 13:
                        arguments.putString(Constants.TITLE, getString(R.string.info_casachf));
                        if (mEncuestasCasasChf!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasCasasChf);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    //MA2020
                    /*case 16:
                        arguments.putString(Constants.TITLE, getString(R.string.info_casasa));
                        if (mEncuestasCasasSa!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasCasasSa);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;
                    case 17:
                        arguments.putString(Constants.TITLE, getString(R.string.info_participantesa));
                        if (mEncuestasParticipantesSa!=null) arguments.putSerializable(Constants.OBJECTO , mEncuestasParticipantesSa);
                        i = new Intent(getApplicationContext(),
                                ListReviewActivity.class);
                        break;*/
                    default:
                        i = new Intent(getApplicationContext(),
                                MenuReviewActivity.class);
                        break;
                }
				i.putExtras(arguments);
				startActivity(i);
			}
		});

	}

	private void getData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
		ca.open();
        mVisitasTerreno=ca.getListaVisitaTerrenoHoy();
        mPyTs = ca.getListaPesoyTallasHoy();
        mEncuestasCasas = ca.getListaEncuestaCasasHoy();
        mEncuestasParticipantes = ca.getListaEncuestaParticipantesHoy();
        mEncuestasLactancias = ca.getListaLactanciaMaternasHoy();
        mVacunas=ca.getListaNewVacunasHoy();
        mMuestras=ca.getListaMuestrasHoy();
        mDatosPartoBBs = ca.getListaDatosPartoBBHoy();
        mObsequios=ca.getListaObsequiosHoy();
        mEncuestasS = ca.getEncuestaSatisfaccionHoy();
        mDocumentos = ca.getListaDocumentosHoy();
        mDatosVisitaTerreno = ca.getListaDatosVisitaTerrenoHoy();
        mEncuestasCasasChf = ca.getListaEncuestaCasasChfHoy();
        //MA2020
        /*mEncuestasCasasSa = (ArrayList)ca.getEncuestasCasaSA(MainDBConstants.recordDate + ">=" + timeStamp.getTime(), MainDBConstants.codigo+ " , " +MainDBConstants.recordDate);
        mEncuestasParticipantesSa = (ArrayList)ca.getEncuestasParticipanteSA(MainDBConstants.recordDate + " >= " + timeStamp.getTime(), MainDBConstants.codigo+ " , " +MainDBConstants.recordDate);*/
        mConSA=(ArrayList)ca.getParticipantesSeroprevalencia(MainDBConstants.recordDate + ">=" + timeStamp.getTime(), SeroprevalenciaDBConstants.participante+ " , " +MainDBConstants.recordDate);
		ca.close();	
	}	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.MENU_BACK:
			finish();
			return true;
		case R.id.MENU_HOME:
			i = new Intent(getApplicationContext(),
					MenuMuestreoAnualActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
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
                getData();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            String[] menu_info = getResources().getStringArray(R.array.menu_review);
            gridView.setAdapter(new MenuReviewAdapter(getApplicationContext(), R.layout.menu_item_2, menu_info,
                    mVisitasTerreno.size(), mPyTs.size(), mEncuestasCasas.size(), mEncuestasParticipantes.size(),
                    mEncuestasLactancias.size(), mVacunas.size(), mEncuestasS.size(), mMuestras.size(), mObsequios.size(), mConSA.size(), mDatosPartoBBs.size(), mDatosVisitaTerreno.size(), mDocumentos.size(),
                    mEncuestasCasasChf.size()));

            dismissProgressDialog();
        }
    }
}

	
