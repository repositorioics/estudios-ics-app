package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.widget.*;
import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevaVisitaFallidaCasaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevoTamizajePersonaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.ParticipanteCohorteFamiliaCasoAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.CasaCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCasoData;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ListaParticipantesCasosActivity extends AbstractAsyncListActivity {

	private TextView textView;
	private Drawable img = null;
	private Button mButton;
	private Button mDatosCasaButton;
	private Button mVisitaFallidaButton;
    private Button mAddPartButton;
	private static CasaCohorteFamiliaCaso casaCaso = new CasaCohorteFamiliaCaso();
    private ParticipanteCohorteFamiliaCasoData participanteCaso = new ParticipanteCohorteFamiliaCasoData();
	private ArrayAdapter<ParticipanteCohorteFamiliaCasoData> mParticipanteCohorteFamiliaCasoAdapter;
	private List<ParticipanteCohorteFamiliaCasoData> mParticipanteCohorteFamiliaCasos = new ArrayList<ParticipanteCohorteFamiliaCasoData>();
	private EstudiosAdapter estudiosAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add_casos);
		casaCaso = (CasaCohorteFamiliaCaso) getIntent().getExtras().getSerializable(Constants.CASA);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_today);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataParticipantesCasosTask().execute(casaCaso.getCodigoCaso());
		
		mButton = (Button) findViewById(R.id.add_visit_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.new_sint_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.new_cont_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.new_bhc_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.new_rojo_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.new_pbmc_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.new_resp_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.view_samp_button);
		mButton.setVisibility(View.GONE);

        mButton = (Button) findViewById(R.id.final_visit_button);
        mButton.setVisibility(View.GONE);
		
		mDatosCasaButton = (Button) findViewById(R.id.datos_casa_button);
		mDatosCasaButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				Bundle arguments = new Bundle();
		        if (casaCaso!=null) arguments.putSerializable(Constants.CASA , casaCaso);
		        Intent i = new Intent(getApplicationContext(),
		        		MenuCasaCasoActivity.class);
		        i.putExtras(arguments);
		        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(i);
		        finish();
				
			}
		});
		
		mVisitaFallidaButton = (Button) findViewById(R.id.fail_visit_button);
		mVisitaFallidaButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				Bundle arguments = new Bundle();
		        if (casaCaso!=null) arguments.putSerializable(Constants.CASA , casaCaso);
		        Intent i = new Intent(getApplicationContext(),
		        		NuevaVisitaFallidaCasaActivity.class);
		        i.putExtras(arguments);
		        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(i);
		        finish();
				
			}
		});

        mAddPartButton = (Button) findViewById(R.id.add_part_button);
        mAddPartButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Bundle arguments = new Bundle();
                if (casaCaso!=null) arguments.putSerializable(Constants.CASA , casaCaso.getCasa());
                Intent i = new Intent(getApplicationContext(),
                        NuevoTamizajePersonaActivity.class);
                i.putExtras(arguments);
                i.putExtra(Constants.DESDE_CASOS, true);
                i.putExtra(Constants.CASO, casaCaso.getCodigoCaso());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
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
					MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
        participanteCaso = (ParticipanteCohorteFamiliaCasoData)this.getListAdapter().getItem(position);
        // Opcion de menu seleccionada
        Bundle arguments = new Bundle();
		Intent i;
		arguments.putSerializable(Constants.PARTICIPANTE , participanteCaso.getParticipante());
		i = new Intent(getApplicationContext(),
				ListaVisitasParticipantesCasosActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtras(arguments);
		startActivity(i);
		finish();
	}
	
	@Override
	public void onBackPressed (){
		Intent i;
		i = new Intent(getApplicationContext(),
				ListaCasasCasosActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
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
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	
	private class FetchDataParticipantesCasosTask extends AsyncTask<String, Void, String> {
		private String codigoCaso = null;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			codigoCaso = values[0];
			try {
				estudiosAdapter.open();
				mParticipanteCohorteFamiliaCasos = estudiosAdapter.getParticipanteCohorteFamiliaCasosDatos(CasosDBConstants.codigoCaso +" = '" + codigoCaso +"'", MainDBConstants.participante);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			textView.setText("");
			textView.setTextColor(Color.BLACK);
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.follow_up_list_par)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCaso.getCasa().getCodigoCHF() + " - " + formatter.format(casaCaso.getFechaInicio()));
			mParticipanteCohorteFamiliaCasoAdapter = new ParticipanteCohorteFamiliaCasoAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mParticipanteCohorteFamiliaCasos);
			setListAdapter(mParticipanteCohorteFamiliaCasoAdapter);
			dismissProgressDialog();
		}

	}	

}
