package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevaVisitaSeguimientoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.VisitaSeguimientoCasoAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCaso;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListaVisitasParticipantesCasosActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mButton;
	private Button mAddVisitButton;
	private Button mFailVisitsButton;
    private Button mFinalVisitsButton;
	private Button mDatosPartButton;
	private static ParticipanteCohorteFamiliaCaso partCaso = new ParticipanteCohorteFamiliaCaso();
    private VisitaSeguimientoCaso visitaCaso = new VisitaSeguimientoCaso();
	private ArrayAdapter<VisitaSeguimientoCaso> mVisitaSeguimientoCasoAdapter;
	private List<VisitaSeguimientoCaso> mVisitaSeguimientoCasos = new ArrayList<VisitaSeguimientoCaso>();
	private EstudiosAdapter estudiosAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add_casos);
		
		partCaso = (ParticipanteCohorteFamiliaCaso) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_today);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataVisitasCasosTask().execute(partCaso.getCodigoCasoParticipante());
		
		mButton = (Button) findViewById(R.id.add_part_button);
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

        mButton = (Button) findViewById(R.id.new_surface_button);
        mButton.setVisibility(View.GONE);

        mButton = (Button) findViewById(R.id.new_hand_button);
        mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.sensors_button);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.new_MA_button);
		mButton.setVisibility(View.GONE);

        mAddVisitButton = (Button) findViewById(R.id.add_visit_button);

		mAddVisitButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String today = formatter.format(new Date());
				for (VisitaSeguimientoCaso vsc:mVisitaSeguimientoCasos ){
					 if(today.equals(formatter.format(vsc.getFechaVisita()))){
						 Toast.makeText(getApplicationContext(),getString(R.string.duplicateDate) , Toast.LENGTH_LONG).show();
						 return;
					 }
				}
				Bundle arguments = new Bundle();
				arguments.putSerializable(Constants.PARTICIPANTE , partCaso);
				Intent i = new Intent(getApplicationContext(),
						NuevaVisitaSeguimientoActivity.class);
				i.putExtras(arguments);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});
		
		mFailVisitsButton = (Button) findViewById(R.id.fail_visit_button);
		mFailVisitsButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				Bundle arguments = new Bundle();
				arguments.putSerializable(Constants.PARTICIPANTE , partCaso);
				Intent i = new Intent(getApplicationContext(),
						ListaVisitasFallidasCasosActivity.class);
				i.putExtras(arguments);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});

        mFinalVisitsButton = (Button) findViewById(R.id.final_visit_button);
        mFinalVisitsButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Bundle arguments = new Bundle();
                arguments.putSerializable(Constants.PARTICIPANTE , partCaso);
                Intent i = new Intent(getApplicationContext(),
                        ListaVisitaFinalCasosActivity.class);
                i.putExtras(arguments);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

		mDatosPartButton = (Button) findViewById(R.id.datos_casa_button);
		mDatosPartButton.setText(getString(R.string.datos_part));
		mDatosPartButton.setEnabled(false); /* SE BLOQUEA EL BOTON - 13/02/2024 */
		mDatosPartButton.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_menu_friendslist), null, null);
		mDatosPartButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				Bundle arguments = new Bundle();
				Intent i;
				arguments.putSerializable(Constants.PARTICIPANTE , partCaso);
				i = new Intent(getApplicationContext(),
						MenuParticipanteCasoActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        i.putExtras(arguments);
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
        visitaCaso = (VisitaSeguimientoCaso)this.getListAdapter().getItem(position);
        // Opcion de menu seleccionada
        Bundle arguments = new Bundle();
		Intent i;
		arguments.putSerializable(Constants.VISITA , visitaCaso);
		i = new Intent(getApplicationContext(),
				MenuVisitaSeguimientoCasoActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtras(arguments);
		startActivity(i);
		finish();
	}
	
	@Override
	public void onBackPressed (){
		Bundle arguments = new Bundle();
		Intent i;
		arguments.putSerializable(Constants.CASA , partCaso.getCodigoCaso());
		i = new Intent(getApplicationContext(),
				ListaParticipantesCasosActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtras(arguments);
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
	
	
	private class FetchDataVisitasCasosTask extends AsyncTask<String, Void, String> {
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
				mVisitaSeguimientoCasos = estudiosAdapter.getVisitaSeguimientoCasos(CasosDBConstants.codigoCasoParticipante +" = '" + codigoCaso +"'", MainDBConstants.fechaVisita);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			textView.setText("");
			textView.setTextColor(Color.BLACK);
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.follow_up_list_visit)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.participant)+ ": "+partCaso.getParticipante().getParticipante().getCodigo());
			mVisitaSeguimientoCasoAdapter = new VisitaSeguimientoCasoAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mVisitaSeguimientoCasos);
			setListAdapter(mVisitaSeguimientoCasoAdapter);
			dismissProgressDialog();
		}

	}	

}
