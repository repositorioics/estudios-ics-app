package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


import java.util.ArrayList;
import java.util.List;

import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;


import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarBanioActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevoBanioActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.BanioAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.AreaAmbiente;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Banio;
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

public class ListaBaniosActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddButton;
	private static AreaAmbiente area = new AreaAmbiente();
    private Banio banio = new Banio();
	private ArrayAdapter<Banio> mBanioAdapter;
	private List<Banio> mBanios = new ArrayList<Banio>();
	private EstudiosAdapter estudiosAdapter;

    private static final int EDITAR_BANIO = 1;
	private static final int BORRAR_BANIO = 2;
	private AlertDialog alertDialog;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add);
		registerForContextMenu(getListView());
		area = (AreaAmbiente) getIntent().getExtras().getSerializable(Constants.AREA);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_selectall_holo_light);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataAreaTask().execute();
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.new_bath));

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
		banio = (Banio)this.getListAdapter().getItem(position);
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
			createDialog(BORRAR_BANIO);
			return true;
            case R.id.MENU_EDITAR_PCAMA:
                createDialog(EDITAR_BANIO);
                return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case BORRAR_BANIO:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(getString(R.string.remove_bath));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					new UpdateBanioTask().execute();
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
            case EDITAR_BANIO:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.edit_bath));
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
		if (area!=null) arguments.putSerializable(Constants.CASA , area.getCasa());
		i = new Intent(getApplicationContext(),
				ListaAreasActivity.class);
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
		        if (area!=null) arguments.putSerializable(Constants.AREA , area);
				Intent i = new Intent(getApplicationContext(),
						NuevoBanioActivity.class);
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
	
	private class FetchDataAreaTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {      
			try {
				estudiosAdapter.open();
				mBanios = estudiosAdapter.getBanios(MainDBConstants.areaAmbiente +" = '" + area.getCodigo() +"' and " + MainDBConstants.tipo + " ='banio' and " + MainDBConstants.pasive + " ='0'", null);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.banio)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+area.getCasa().getCodigoCHF());
			mBanioAdapter = new BanioAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mBanios);
			setListAdapter(mBanioAdapter);
			dismissProgressDialog();
		}

	}	
	
	private class UpdateBanioTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				banio.setPasive('1');
				banio.setEstado('0');
				estudiosAdapter.editarBanio(banio);
				mBanios = estudiosAdapter.getBanios(MainDBConstants.areaAmbiente +" = '" + area.getCodigo() +"' and " + MainDBConstants.tipo + " ='banio' and " + MainDBConstants.pasive + " ='0'", null);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.banio)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+area.getCasa().getCodigoCHF());
			mBanioAdapter = new BanioAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mBanios);
			setListAdapter(mBanioAdapter);
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
                if (banio!=null) arguments.putSerializable(Constants.BANIO , banio);
                Intent i = new Intent(getApplicationContext(),
                        EditarBanioActivity.class);
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
