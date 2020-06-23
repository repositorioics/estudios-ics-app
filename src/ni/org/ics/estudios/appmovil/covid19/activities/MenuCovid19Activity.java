package ni.org.ics.estudios.appmovil.covid19.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaCanditatosTransmisionCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaCasasCasosCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaParticipantesPosCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.server.DownloadCasosCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.server.UploadAllCasosCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.adapters.MenuCovid19Adapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.utils.Constants;

public class MenuCovid19Activity extends AbstractAsyncActivity {

	private GridView gridView;
	private TextView textView;
	private String[] menu_covid19;
	
	private static final int UPDATE_EQUIPO = 11;
	private static final int UPDATE_SERVER = 12;
	
	private static final int DOWNLOAD = 1;
	private static final int UPLOAD = 2;
	private static final int VERIFY = 3;
	private static final int EXIT = 4;
	
	private AlertDialog alertDialog;

    private EstudiosAdapter estudiosAdapter;

    //private List<MessageResource> mCatalogos = new ArrayList<MessageResource>();

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_influenza_uo1);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

        textView = (TextView) findViewById(R.id.label);
        gridView = (GridView) findViewById(R.id.gridView1);
        menu_covid19 = getResources().getStringArray(R.array.menu_covid19);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
        new FetchDataCasaTask().execute();

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent i;
				switch (position) {
					case 0:
						i = new Intent(getApplicationContext(),
								ListaParticipantesPosCasoCovid19Activity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						break;
					case 1:
						i = new Intent(getApplicationContext(),
								ListaCanditatosTransmisionCovid19Activity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						break;
					case 2:
						createDialog(DOWNLOAD);
						break;
					case 3:
						createDialog(UPLOAD);
						break;
					case 4:
						createDialog(EXIT);
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
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case EXIT:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.exiting));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Finish app
					dialog.dismiss();
					finish();
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
		case DOWNLOAD:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.downloading));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					estudiosAdapter.open();
					boolean hayDatos = estudiosAdapter.verificarData();
					estudiosAdapter.close();
					if(hayDatos){
						createDialog(VERIFY);
					}
					else{
						Intent ie = new Intent(getApplicationContext(), DownloadCasosCovid19Activity.class);
						startActivityForResult(ie, UPDATE_EQUIPO);
					}
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
		case UPLOAD:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.uploading));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent ie = new Intent(getApplicationContext(), UploadAllCasosCovid19Activity.class);
					startActivityForResult(ie, UPDATE_SERVER);
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
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String mensaje = "";
		if (requestCode == UPDATE_SERVER){
			if (resultCode == RESULT_CANCELED) {
				builder.setTitle(getApplicationContext().getString(R.string.error));
				builder.setIcon(R.drawable.ic_menu_close_clear_cancel);
				mensaje = intent.getStringExtra("resultado");
			}
			else{
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(R.drawable.ic_menu_info_details);
				mensaje = getApplicationContext().getString(R.string.success);
			}
		}
		if (requestCode == UPDATE_EQUIPO) {
			if (resultCode == RESULT_CANCELED) {
				builder.setTitle(getApplicationContext().getString(R.string.error));
				builder.setIcon(R.drawable.ic_menu_close_clear_cancel);
				mensaje = intent.getStringExtra("resultado");
			} else {
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(R.drawable.ic_menu_info_details);
				mensaje = getApplicationContext().getString(R.string.success);
				//new FetchDataCasasCasosTask().execute();
			}
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

    private class FetchDataCasaTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            return "exito";
        }

        protected void onPostExecute(String resultado) {

            textView.setText("");
            textView.setTextColor(Color.BLACK);
            textView.setText(getString(R.string.main_4)+"\n"+getString(R.string.header_main));
            gridView.setAdapter(new MenuCovid19Adapter(getApplicationContext(), R.layout.menu_item_2, menu_covid19));
            dismissProgressDialog();
        }
    }
		
}
	
