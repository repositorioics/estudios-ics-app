package ni.org.ics.estudios.appmovil.covid19.activities.list;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.covid19.activities.server.DownloadCasosCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.adapters.CasoCovid19Adapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.covid19.CasoCovid19;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ListaCasasCasosCovid19Activity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mButton;
	private Button mUpdateButton;
	private ArrayAdapter<CasoCovid19> mCasoAdapter;
	private CasoCovid19 casaCaso;
	private List<CasoCovid19> mCasaCohorteFamiliaCasos = new ArrayList<CasoCovid19>();
	private EstudiosAdapter estudiosAdapter;

	private static final int DOWNLOAD = 1;
	private static final int VERIFY = 3;
	private static final int UPDATE_EQUIPO = 11;
	
	private AlertDialog alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add_casos_covid19);
		
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_today);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCasasCasosTask().execute();
		

		mButton = (Button) findViewById(R.id.add_visit_button_cv19);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.fail_visit_button_cv19);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.new_sint_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.new_bhc_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.new_rojo_button_cv19);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.new_pbmc_button_cv19);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.new_resp_button_cv19);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.view_samp_button_cv19);
		mButton.setVisibility(View.GONE);

        mButton = (Button) findViewById(R.id.final_visit_button_cv19);
        mButton.setVisibility(View.GONE);

		mUpdateButton = (Button) findViewById(R.id.send_case_cv19);
		mUpdateButton.setVisibility(View.GONE);
		/*mUpdateButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(DOWNLOAD);
			}
		});*/
		
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
		casaCaso = (CasoCovid19)this.getListAdapter().getItem(position);
        // Opcion de menu seleccionada
        Bundle arguments = new Bundle();
		Intent i;
		arguments.putSerializable(Constants.CASA , casaCaso);
		i = new Intent(getApplicationContext(),
				ListaParticipantesCasoCovid19Activity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtras(arguments);
		startActivity(i);
		finish();
	}
	
	@Override
	public void onBackPressed (){
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
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case DOWNLOAD:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.downloading));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					estudiosAdapter.open();
					if(estudiosAdapter.verificarData()){
						createDialog(VERIFY);
					}
					else{
						Intent ie = new Intent(getApplicationContext(), DownloadCasosCovid19Activity.class);
						startActivityForResult(ie, UPDATE_EQUIPO);
					}
					estudiosAdapter.close();
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
		case VERIFY:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.data_not_sent));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent ie = new Intent(getApplicationContext(), DownloadCasosCovid19Activity.class);
					startActivityForResult(ie, UPDATE_EQUIPO);
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
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {	
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == UPDATE_EQUIPO){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			String mensaje = "";
			if (resultCode == RESULT_CANCELED) {
				builder.setTitle(getApplicationContext().getString(R.string.error));
				builder.setIcon(R.drawable.ic_menu_close_clear_cancel);
				mensaje = intent.getStringExtra("resultado");
			}
			else{
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(R.drawable.ic_menu_info_details);
				mensaje = getApplicationContext().getString(R.string.success);
                new FetchDataCasasCasosTask().execute();
			}

			builder.setMessage(mensaje)
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					//do things
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			return;
		}
	}
	
	private class FetchDataCasasCasosTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				mCasaCohorteFamiliaCasos = estudiosAdapter.getCasosCovid19("", "CAST("+ CasosDBConstants.casa+" AS INTEGER)");
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
			textView.setText(getString(R.string.main_4) +"\n"+ getString(R.string.follow_up_list));
			mCasoAdapter = new CasoCovid19Adapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mCasaCohorteFamiliaCasos);
			setListAdapter(mCasoAdapter);
			dismissProgressDialog();
		}

	}	

}
