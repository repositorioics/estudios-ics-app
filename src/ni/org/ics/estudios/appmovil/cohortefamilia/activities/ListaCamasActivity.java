package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


import java.util.ArrayList;
import java.util.List;

import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarCamaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevaCamaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.CamaAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cama;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cuarto;
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

public class ListaCamasActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddButton;
	private static Cuarto cuarto = new Cuarto();
    private Cama cama = new Cama();
	private ArrayAdapter<Cama> mCamaAdapter;
	private List<Cama> mCamas = new ArrayList<Cama>();
	private EstudiosAdapter estudiosAdapter;
	private static final int EDITAR_CAMA = 1;
    private static final int BORRAR_CAMA = 2;
	private AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add);
		registerForContextMenu(getListView());
		
		cuarto = (Cuarto) getIntent().getExtras().getSerializable(Constants.CUARTO);
		if (cuarto == null) { //15/06/2023
			cuarto = (Cuarto) getIntent().getExtras().getSerializable(Constants.HABITACION);
		}
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_share);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		//cambio realizado 15/06/2023
		if (cuarto != null) {
			new FetchDataHabitacionTask().execute(cuarto.getCodigo());
		}

		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.new_bed));

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
		cama = (Cama)this.getListAdapter().getItem(position);
		listView.showContextMenuForChild(view);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionscamas, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {    	
		switch(item.getItemId()) {
            case R.id.MENU_VER_PCAMAS:
                Bundle arguments = new Bundle();
                Intent i;
                arguments.putSerializable(Constants.CAMA , cama);
                i = new Intent(getApplicationContext(),
                        ListaPersonasCamaActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtras(arguments);
                startActivity(i);
                finish();
                return true;
            case R.id.MENU_EDITAR_CAMA:
                createDialog(EDITAR_CAMA);
                return true;
            case R.id.MENU_BORRAR_CAMA:
                createDialog(BORRAR_CAMA);
                return true;
            default:
                return super.onContextItemSelected(item);
		}
	}
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case BORRAR_CAMA:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(getString(R.string.remove_bed));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					cama.setPasive('1');
					cama.setEstado('0');
					new UpdateCamaTask().execute();
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
            case EDITAR_CAMA:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.edit_bed));
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
		if (cuarto!=null) arguments.putSerializable(Constants.CASA , cuarto.getCasa());
		i = new Intent(getApplicationContext(),
				ListaCuartosActivity.class);
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
		        if (cuarto!=null) arguments.putSerializable(Constants.HABITACION , cuarto);
				Intent i = new Intent(getApplicationContext(),
						NuevaCamaActivity.class);
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
	
	private class FetchDataHabitacionTask extends AsyncTask<String, Void, String> {
		private String codigoHabitacion = null;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			codigoHabitacion = values[0];
			try {
				estudiosAdapter.open();
				mCamas = estudiosAdapter.getCamas(MainDBConstants.habitacion +" = '" + codigoHabitacion + "' and " + MainDBConstants.pasive + " ='0'", MainDBConstants.codigoCama);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.beds)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+cuarto.getCasa().getCodigoCHF()+"\n"+ getString(R.string.codigoHabitacion)+ ": "+cuarto.getCodigoHabitacion());
			mCamaAdapter = new CamaAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mCamas);
			setListAdapter(mCamaAdapter);
			dismissProgressDialog();
		}

	}	
	
	
	private class UpdateCamaTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				List<PersonaCama> mPersonasCama = estudiosAdapter.getPersonasCama(MainDBConstants.cama +" = '" + cama.getCodigoCama() + "'", MainDBConstants.codigoPersona);
				for (PersonaCama pc:mPersonasCama){
					pc.setPasive('1');
					pc.setEstado('0');
					estudiosAdapter.editarPersonaCama(pc);
				}
				estudiosAdapter.editarCama(cama);
				mCamas = estudiosAdapter.getCamas(MainDBConstants.habitacion +" = '" + cuarto.getCodigo() + "' and " + MainDBConstants.pasive + " ='0'", MainDBConstants.codigoCama);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.beds)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+cuarto.getCasa().getCodigoCHF()+"\n"+ getString(R.string.codigoHabitacion)+ ": "+cuarto.getCodigoHabitacion());
			mCamaAdapter = new CamaAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mCamas);
			setListAdapter(mCamaAdapter);
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
                if (cuarto!=null) arguments.putSerializable(Constants.CUARTO , cuarto);
                if (cama!=null) arguments.putSerializable(Constants.CAMA , cama);
                Intent i = new Intent(getApplicationContext(),
                        EditarCamaActivity.class);
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

}
