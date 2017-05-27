package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


import java.util.ArrayList;
import java.util.List;

import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarPersonaCamaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevaPersonaCamaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.PersonaCamaAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cama;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.PersonaCama;
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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListaPersonasCamaActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddButton;
	private static Cama cama = new Cama();
	private PersonaCama pc = new PersonaCama();
	private ArrayAdapter<PersonaCama> mPersonaCamaAdapter;
	private List<PersonaCama> mPersonasCama = new ArrayList<PersonaCama>();
	private EstudiosAdapter estudiosAdapter;
	private AlertDialog alertDialog;
	
	private static final int EDITAR_PERSONA = 1;
	private static final int BORRAR_PERSONA = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add);
		
		registerForContextMenu(getListView());
		
		cama = (Cama) getIntent().getExtras().getSerializable(Constants.CAMA);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_share);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCamaTask().execute(cama.getCodigoCama());
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.new_pbed));

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
		pc = (PersonaCama)this.getListAdapter().getItem(position);
		listView.showContextMenuForChild(view);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionspersonascamas, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {    	
		switch(item.getItemId()) {
		case R.id.MENU_BORRAR_PCAMA:
			createDialog(BORRAR_PERSONA);
			return true;
		/*case R.id.MENU_EDITAR_PCAMA:
			createDialog(EDITAR_PERSONA);	
			return true;*/
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case EDITAR_PERSONA:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(getString(R.string.edit_pbed));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					new OpenDataEditActivityTask().execute();
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
		case BORRAR_PERSONA:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(getString(R.string.remove_pbed));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					pc.setPasive('1');
					pc.setEstado('0');
					new UpdatePersonaCamaTask().execute();
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
	public void onBackPressed (){
		Bundle arguments = new Bundle();
		Intent i;
		if (cama!=null) arguments.putSerializable(Constants.CUARTO , cama.getCuarto());
		i = new Intent(getApplicationContext(),
				ListaCamasActivity.class);
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
		        if (cama!=null) arguments.putSerializable(Constants.CAMA , cama);
				Intent i = new Intent(getApplicationContext(),
						NuevaPersonaCamaActivity.class);
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
	
	
	// ***************************************
		// Private classes
		// ***************************************
		private class OpenDataEditActivityTask extends AsyncTask<String, Void, String> {
			@Override
			protected void onPreExecute() {
				// before the request begins, show a progress indicator
				showLoadingProgressDialog();
			}

			@Override
			protected String doInBackground(String... values) {
				try {
					Bundle arguments = new Bundle();
			        if (cama!=null) arguments.putSerializable(Constants.CAMA , cama);
			        if (pc!=null) arguments.putSerializable(Constants.PERSONACAMA , pc);
					Intent i = new Intent(getApplicationContext(),
							EditarPersonaCamaActivity.class);
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
	
	private class FetchDataCamaTask extends AsyncTask<String, Void, String> {
		private String codigoCama = null;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			codigoCama = values[0];
			try {
				estudiosAdapter.open();
				mPersonasCama = estudiosAdapter.getPersonasCama(MainDBConstants.cama +" = '" + codigoCama + "' and " + MainDBConstants.pasive + " ='0'", MainDBConstants.codigoPersona);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.pbeds)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+cama.getCuarto().getCasa().getCodigoCHF()+"\n"+ getString(R.string.codigoHabitacion)+ ": "+cama.getCuarto().getCodigoHabitacion());
			mPersonaCamaAdapter = new PersonaCamaAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mPersonasCama);
			setListAdapter(mPersonaCamaAdapter);
			dismissProgressDialog();
		}

	}
	
	
	private class UpdatePersonaCamaTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				estudiosAdapter.editarPersonaCama(pc);
				mPersonasCama = estudiosAdapter.getPersonasCama(MainDBConstants.cama +" = '" + pc.getCama().getCodigoCama() + "' and " + MainDBConstants.pasive + " ='0'", MainDBConstants.codigoPersona);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.pbeds)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+cama.getCuarto().getCasa().getCodigoCHF()+"\n"+ getString(R.string.codigoHabitacion)+ ": "+cama.getCuarto().getCodigoHabitacion());
			mPersonaCamaAdapter = new PersonaCamaAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mPersonasCama);
			setListAdapter(mPersonaCamaAdapter);
			dismissProgressDialog();
		}

	}

}
