package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


import java.util.ArrayList;
import java.util.List;

import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevoHabitacionActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.HabitacionAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Habitacion;
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

public class ListaHabitacionesActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddButton;
	private static CasaCohorteFamilia casaCHF = new CasaCohorteFamilia();
    private Habitacion habitacion = new Habitacion();
	private ArrayAdapter<Habitacion> mHabitacionAdapter;
	private List<Habitacion> mHabitaciones = new ArrayList<Habitacion>();
	private EstudiosAdapter estudiosAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add);
		
		casaCHF = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASA);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_share);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataParticipantesTask().execute(casaCHF.getCodigoCHF());
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.new_room));

		mAddButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				new OpenDataEnterActivityTask().execute();
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
        /*habitacion = (Habitacion)this.getListAdapter().getItem(position);
        // Opcion de menu seleccionada
        Bundle arguments = new Bundle();
		Intent i;
		arguments.putSerializable(Constants.HABITACION , habitacion);
		i = new Intent(getApplicationContext(),
				MenuParticipanteActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtras(arguments);
		startActivity(i);
		finish();*/
	}
	
	@Override
	public void onBackPressed (){
		Bundle arguments = new Bundle();
		Intent i;
		if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
		i = new Intent(getApplicationContext(),
				MenuCasaActivity.class);
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
	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class OpenDataEnterActivityTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				Bundle arguments = new Bundle();
		        if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
				Intent i = new Intent(getApplicationContext(),
						NuevoHabitacionActivity.class);
				i.putExtras(arguments);
		        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			dismissProgressDialog();
		}

	}
	
	private class FetchDataParticipantesTask extends AsyncTask<String, Void, String> {
		private String codigoCasaCHF = null;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			codigoCasaCHF = values[0];
			try {
				estudiosAdapter.open();
				mHabitaciones = estudiosAdapter.getHabitaciones(MainDBConstants.casa +" = " + codigoCasaCHF + " and " + MainDBConstants.tipo + " ='habitacion'", MainDBConstants.codigoHabitacion);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.rooms)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCHF.getCodigoCHF());
			mHabitacionAdapter = new HabitacionAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mHabitaciones);
			setListAdapter(mHabitacionAdapter);
			dismissProgressDialog();
		}

	}	

}
