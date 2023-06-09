package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarVentanaBanioNivel1Activity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevaVentanaBanioNivel1Activity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.VentanaAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Banio;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Ventana;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.ArrayList;
import java.util.List;

public class ListaVentanasBanioNivel1Activity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddButton;
	//private static AreaAmbiente area = new AreaAmbiente();
	private static Banio banio = new Banio();

    private Ventana ventana = new Ventana();
	private ArrayAdapter<Ventana> mAreaAdapter;
	private List<Ventana> mVentanas = new ArrayList<Ventana>();
	private EstudiosAdapter estudiosAdapter;

    private static final int EDITAR_VENTANA = 1;
	private static final int BORRAR_VENTANA = 2;
	private AlertDialog alertDialog;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add);
		registerForContextMenu(getListView());
		//area = (AreaAmbiente) getIntent().getExtras().getSerializable(Constants.AREA);
		banio = (Banio) getIntent().getExtras().getSerializable(Constants.BANIO);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_selectall_holo_light);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataAreaTask().execute();
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.new_window));

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
		ventana = (Ventana)this.getListAdapter().getItem(position);
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
			createDialog(BORRAR_VENTANA);
			return true;
            case R.id.MENU_EDITAR_PCAMA:
                createDialog(EDITAR_VENTANA);
                return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
            case EDITAR_VENTANA:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.edit_window));
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
            case BORRAR_VENTANA:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(getString(R.string.remove_window));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					new UpdateVentanaTask().execute();
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
		//Regresar al menu de lista de baños del ambiente
		Bundle arguments = new Bundle();
		Intent i;
		//if (banio!=null) arguments.putSerializable(Constants.CASA , banio.getAreaAmbiente().getCasa());
		if (banio!=null) arguments.putSerializable(Constants.CASA , banio.getCasa());
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
				if (banio!=null) arguments.putSerializable(Constants.BANIO , banio);
				Intent i = new Intent(getApplicationContext(),
						NuevaVentanaBanioNivel1Activity.class);
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
				mVentanas = estudiosAdapter.getVentanas(MainDBConstants.areaBanio +" = '" + banio.getCodigo() +"' and " + MainDBConstants.tipo + " ='ventana' and " + MainDBConstants.pasive + " ='0'", null);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.ventana)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+banio.getCasa().getCodigoCHF() +"\n");
			mAreaAdapter = new VentanaAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mVentanas);
			setListAdapter(mAreaAdapter);
			dismissProgressDialog();
		}

	}	
	
	private class UpdateVentanaTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				ventana.setPasive('1');
				ventana.setEstado('0');
				estudiosAdapter.editarVentana(ventana);
				mVentanas = estudiosAdapter.getVentanas(MainDBConstants.areaBanio +" = '" + banio.getCodigo() +"' and " + MainDBConstants.tipo + " ='ventana' and " + MainDBConstants.pasive + " ='0'", null);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.ventana)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+banio.getCasa().getCodigoCHF());
			mAreaAdapter = new VentanaAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mVentanas);
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
				if (banio!=null) arguments.putSerializable(Constants.BANIO , banio);
				if (ventana!=null) arguments.putSerializable(Constants.VENTANA , ventana);
                Intent i = new Intent(getApplicationContext(),
						EditarVentanaBanioNivel1Activity.class);
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
