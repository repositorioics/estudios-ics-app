package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


import java.util.ArrayList;
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

public class ListaVisitasParticipantesCasosActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddButton;
	private static ParticipanteCohorteFamiliaCaso partCaso = new ParticipanteCohorteFamiliaCaso();
    private VisitaSeguimientoCaso visitaCaso = new VisitaSeguimientoCaso();
	private ArrayAdapter<VisitaSeguimientoCaso> mVisitaSeguimientoCasoAdapter;
	private List<VisitaSeguimientoCaso> mVisitaSeguimientoCasos = new ArrayList<VisitaSeguimientoCaso>();
	private EstudiosAdapter estudiosAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add);
		
		partCaso = (ParticipanteCohorteFamiliaCaso) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_today);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataVisitasCasosTask().execute(partCaso.getCodigoCasoParticipante());
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.new_visit));

		mAddButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				Bundle arguments = new Bundle();
				arguments.putSerializable(Constants.PARTICIPANTE , partCaso);
				Intent i = new Intent(getApplicationContext(),
						NuevaVisitaSeguimientoActivity.class);
				i.putExtras(arguments);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
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
