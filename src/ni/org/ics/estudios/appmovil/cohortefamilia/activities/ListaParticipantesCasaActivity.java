package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


import java.util.ArrayList;
import java.util.List;

import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.ParticipanteCHFAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class ListaParticipantesCasaActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddButton;
	private static String casaCHF;
    private ParticipanteCohorteFamilia participanteCHF = new ParticipanteCohorteFamilia();
	private ArrayAdapter<ParticipanteCohorteFamilia> mParticipanteCohorteFamiliaAdapter;
	private List<ParticipanteCohorteFamilia> mParticipantes = new ArrayList<ParticipanteCohorteFamilia>();
	private EstudiosAdapter estudiosAdapter;
	private static final int SELECT_PART = 1;
	private AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add);
		
		casaCHF = getIntent().getStringExtra(Constants.CASA);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_allfriends);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataParticipantesTask().execute(casaCHF);
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setVisibility(View.GONE);

		
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
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case SELECT_PART:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(getString(R.string.select)+"\n"+getString(R.string.code)+": " + participanteCHF.getParticipante().getCodigo() + " " + participanteCHF.getParticipante().getNombre1() + " " + participanteCHF.getParticipante().getApellido1());
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					seleccionarParticipante();
				}
			});
			builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Do nothing
					dialog.dismiss();
				}
			});
			break;		
		default:
			break;
		}
		alertDialog = builder.create();
		alertDialog.show();
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		participanteCHF = (ParticipanteCohorteFamilia) getListAdapter().getItem(position);
		createDialog(SELECT_PART);
	}
	
	private void seleccionarParticipante(){
    	Intent intent1 = new Intent();
		intent1.putExtra("CODE_RESULT", participanteCHF.getParticipante().getCodigo().toString());
		setResult(RESULT_OK, intent1);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.participants)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCHF);
			mParticipanteCohorteFamiliaAdapter = new ParticipanteCHFAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mParticipantes);
			setListAdapter(mParticipanteCohorteFamiliaAdapter);
			dismissProgressDialog();
		}

	}	

}
