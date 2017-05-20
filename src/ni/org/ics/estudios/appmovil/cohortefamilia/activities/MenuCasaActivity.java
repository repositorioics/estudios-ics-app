package ni.org.ics.estudios.appmovil.cohortefamilia.activities;

import java.util.ArrayList;
import android.annotation.TargetApi;
import android.app.ActionBar;
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
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevaEncuestaCasaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.MenuCasaAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;


public class MenuCasaActivity extends AbstractAsyncActivity {

	private GridView gridView;
	private TextView textView;
	private String[] menu_casa;
	private static CasaCohorteFamilia casaCHF = new CasaCohorteFamilia();
	private ArrayList<ParticipanteCohorteFamilia> mParticipantes = new ArrayList<ParticipanteCohorteFamilia>();
	
	private EstudiosAdapter estudiosAdapter;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_cohorte_familia);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		textView = (TextView) findViewById(R.id.label);
		gridView = (GridView) findViewById(R.id.gridView1);
		menu_casa = getResources().getStringArray(R.array.menu_casa);
		casaCHF = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASA);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCasaTask().execute(casaCHF.getCodigoCHF());
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
				Intent i;
				switch (position){
				case 0:
					if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
					if (mParticipantes!=null) arguments.putSerializable(Constants.PARTICIPANTES , mParticipantes);
					i = new Intent(getApplicationContext(),
							ListaParticipantesActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtras(arguments);
					startActivity(i);
		        	break;
                    case 1:
                        new OpenDataEnterActivityTask().execute(String.valueOf(position));
                        break;
				    default:
                        break;
		        }
			}
		});

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
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
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
	
	// ***************************************
	// Private classes
	// ***************************************
    private class OpenDataEnterActivityTask extends AsyncTask<String, Void, String> {
        private int position = 0;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            position = Integer.valueOf(values[0]);
            Bundle arguments = new Bundle();
            Intent i;
            try {
                  if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
                        i = new Intent(getApplicationContext(),
                                NuevaEncuestaCasaActivity.class);
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

    private class FetchDataCasaTask extends AsyncTask<String, Void, String> {
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
				mParticipantes = estudiosAdapter.getParticipanteCohorteFamilias(MainDBConstants.casaCHF +" = " + codigoCasaCHF, MainDBConstants.participante);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.header_casa)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCHF.getCodigoCHF());
			gridView.setAdapter(new MenuCasaAdapter(getApplicationContext(), R.layout.menu_item_2, menu_casa, mParticipantes.size()));
			dismissProgressDialog();
		}

	}	
		
}
	
