package ni.org.ics.estudios.appmovil;


import ni.org.ics.estudios.appmovil.adapters.MainActivityAdapter;
import ni.org.ics.estudios.appmovil.bluetooth.activity.ChatActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuCohorteFamiliaActivity;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private static final int UPDATE_EQUIPO = 11;
	private static final int UPDATE_SERVER = 12;

	
	private static final int EXIT = 1;
	private static final int DOWNLOAD = 2;
	private static final int UPLOAD = 3;
	private static final int VERIFY = 4;
	
	private AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String[] menu_main = getResources().getStringArray(R.array.menu_main);
		setListAdapter(new MainActivityAdapter(this, R.layout.menu_item, menu_main));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.MENU_DESCARGA:
			createDialog(DOWNLOAD);
			return true;
		case R.id.MENU_CARGA:
			createDialog(UPLOAD);
			return true;
		case R.id.MENU_EXIT:
			createDialog(EXIT);
			return true;
		case R.id.MENU_PREFERENCES:
			Intent ig = new Intent(this, PreferencesActivity.class);
			startActivity(ig);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		// Opcion de menu seleccionada
		Intent i;
		switch(position){
		case 0: 
			i = new Intent(getApplicationContext(),
					MenuCohorteFamiliaActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			break;
		default: 
			String s = (String) getListAdapter().getItem(position);
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
		}
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
	public void onBackPressed (){
		createDialog(EXIT);
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
					int pid = android.os.Process.myPid();
			        android.os.Process.killProcess(pid);
			        System.exit(0);
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
			
			break;
		case UPLOAD:
			
			break;
		case VERIFY:
			
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
		if (resultCode == RESULT_CANCELED) {
			if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getApplicationContext().getString(R.string.error));
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				builder.setMessage(intent.getStringExtra("resultado"))
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
		else{
			if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(android.R.drawable.ic_dialog_info);
				builder.setMessage(getApplicationContext().getString(R.string.success))
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//do things
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
			return;
		}
	}
}
