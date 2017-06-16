package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


import java.util.ArrayList;
import java.util.List;

import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;


import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarAreaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevaAreaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.AreaAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.AreaAmbiente;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
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

public class ListaAreasActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddButton;
	private static CasaCohorteFamilia casaCHF = new CasaCohorteFamilia();
    private AreaAmbiente area = new AreaAmbiente();
	private ArrayAdapter<AreaAmbiente> mAreaAdapter;
	private List<AreaAmbiente> mAreas = new ArrayList<AreaAmbiente>();
	private EstudiosAdapter estudiosAdapter;
	
	private static final int EDITAR_AREA = 1;
    private static final int BORRAR_AREA = 2;
	private AlertDialog alertDialog;
	
	private MenuItem verBanios; 
	private MenuItem verVentanas; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add);
		registerForContextMenu(getListView());
		casaCHF = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASA);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_selectall_holo_light);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCasaTask().execute();
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.new_area));

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
		area = (AreaAmbiente)this.getListAdapter().getItem(position);
		listView.showContextMenuForChild(view);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionsareas, menu);
		verBanios = menu.findItem(R.id.MENU_VER_BANIOS);
		verVentanas = menu.findItem(R.id.MENU_VER_VENTANAS);
		verBanios.setVisible(false);
		verVentanas.setVisible(true);
		if (area.getTipo().equals("habitacion")){
			verBanios.setVisible(true);
		}
		if (area.getTipo().equals("banio")){
			verVentanas.setVisible(false);
		}
		
	}
	

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Bundle arguments = new Bundle();
		Intent i;
		switch(item.getItemId()) {
		case R.id.MENU_VER_BANIOS:
			arguments.putSerializable(Constants.AREA, area);
			i = new Intent(getApplicationContext(),
					ListaBaniosActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        i.putExtras(arguments);
			startActivity(i);
			finish();
			return true;
		case R.id.MENU_VER_VENTANAS:
			arguments.putSerializable(Constants.AREA , area);
			i = new Intent(getApplicationContext(),
					ListaVentanasActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        i.putExtras(arguments);
			startActivity(i);
			finish();
			return true;
		case R.id.MENU_BORRAR_AREA:
			createDialog(BORRAR_AREA);
			return true;
            case R.id.MENU_EDITAR_AREA:
                createDialog(EDITAR_AREA);
                return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case BORRAR_AREA:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(getString(R.string.remove_area));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					new UpdateAreaTask().execute();
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
            case EDITAR_AREA:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.edit_area));
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
						NuevaAreaActivity.class);
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
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {      
			try {
				estudiosAdapter.open();
				mAreas = estudiosAdapter.getAreasAmbiente(MainDBConstants.casa +" = '" + casaCHF.getCodigoCHF() + "' and " + MainDBConstants.areaAmbiente + " is null and " + MainDBConstants.pasive + " ='0'", MainDBConstants.tipo);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.areas)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCHF.getCodigoCHF());
			mAreaAdapter = new AreaAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mAreas);
			setListAdapter(mAreaAdapter);
			dismissProgressDialog();
		}

	}	
	
	private class UpdateAreaTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				area.setPasive('1');
				area.setEstado('0');
				estudiosAdapter.editarAreaAmbiente(area);
				mAreas = estudiosAdapter.getAreasAmbiente(MainDBConstants.casa +" = '" + area.getCasa().getCodigoCHF() + "' and " + MainDBConstants.areaAmbiente + " is null and " + MainDBConstants.pasive + " ='0'", MainDBConstants.tipo);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.areas)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCHF.getCodigoCHF());
			mAreaAdapter = new AreaAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mAreas);
			setListAdapter(mAreaAdapter);
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
                if (area!=null) arguments.putSerializable(Constants.AREA , area);
                if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
                Intent i = new Intent(getApplicationContext(),
                        EditarAreaActivity.class);
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
