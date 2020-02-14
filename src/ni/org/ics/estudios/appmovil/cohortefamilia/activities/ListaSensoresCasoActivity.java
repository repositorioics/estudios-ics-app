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
import android.widget.*;
import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarTelefonoContactoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevoSensorCasoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.RetirarSensoresActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.BanioAdapter;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.SensoresCasoAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.CasaCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.SensorCaso;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListaSensoresCasoActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddButton;
    private Button mRemoveButton;
    private static CasaCohorteFamiliaCaso casaCaso = new CasaCohorteFamiliaCaso();
    private SensorCaso sensorCaso = new SensorCaso();
	private ArrayAdapter<SensorCaso> mSensoresAdapter;
	private List<SensorCaso> mSensoresCasos = new ArrayList<SensorCaso>();
	private EstudiosAdapter estudiosAdapter;

    private static final int BORRAR_SENSOR = 2;
	private AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add_sensors);
		
		registerForContextMenu(getListView());

        casaCaso = (CasaCohorteFamiliaCaso) getIntent().getExtras().getSerializable(Constants.CASA);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_sens_temphum);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCasaTask().execute();
		
		mAddButton = (Button) findViewById(R.id.add_button);
        mAddButton.setText(R.string.add_sensor);
		mAddButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				new OpenDataEnterActivityTask().execute();
			}
		});

        mRemoveButton = (Button) findViewById(R.id.remove_button);
        mRemoveButton.setText(R.string.remove_sensor);
        mRemoveButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                if (sensoresPendientes())
                    new OpenDataRemoveActivityTask().execute();
                else
                    Toast.makeText(v.getContext(), getString(R.string.no_pending_sensors), Toast.LENGTH_LONG).show();
            }
        });
		
	}


    private boolean sensoresPendientes(){
        for(SensorCaso sensorCaso: mSensoresCasos) {
            if (sensorCaso.getSensorSN().equalsIgnoreCase("1") && sensorCaso.getHoraRetiro()==null)
                return true;
        }
        return false;
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
		sensorCaso = (SensorCaso)this.getListAdapter().getItem(position);
		listView.showContextMenuForChild(view);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionssensors, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {    	
		switch(item.getItemId()) {
		case R.id.MENU_BORRAR_SENSOR:
			createDialog(BORRAR_SENSOR);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	@Override
	public void onBackPressed (){
		Bundle arguments = new Bundle();
		Intent i;
		if (casaCaso!=null) arguments.putSerializable(Constants.CASA , casaCaso);
		i = new Intent(getApplicationContext(),
				ListaParticipantesCasosActivity.class);
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

    private void createDialog(int dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(dialog){
            case BORRAR_SENSOR:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.confirm_remove_sensor));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new UpdateSensorTask().execute();
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
		        if (casaCaso!=null) arguments.putSerializable(Constants.CASA , casaCaso);
				Intent i = new Intent(getApplicationContext(),
						NuevoSensorCasoActivity.class);
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

    private class UpdateSensorTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                estudiosAdapter.open();
                sensorCaso.setPasive('1');
                sensorCaso.setEstado('0');
                estudiosAdapter.editarSensorCaso(sensorCaso);
                mSensoresCasos = estudiosAdapter.getSensoresCasos(CasosDBConstants.codigoCaso + " = '" + casaCaso.getCodigoCaso() + "' and " + MainDBConstants.pasive + " ='0'", CasosDBConstants.numeroSensor);
                estudiosAdapter.close();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            textView.setText("");
            textView.setTextColor(Color.BLACK);
            textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.sensors)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCaso.getCasa().getCodigoCHF()+ " - " + formatter.format(casaCaso.getFechaInicio()));
            mSensoresAdapter = new SensoresCasoAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item, mSensoresCasos);
            setListAdapter(mSensoresAdapter);
            dismissProgressDialog();
        }

    }

    private class OpenDataRemoveActivityTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                Bundle arguments = new Bundle();
                if (casaCaso!=null) arguments.putSerializable(Constants.CASA , casaCaso);
                Intent i = new Intent(getApplicationContext(),
                        RetirarSensoresActivity.class);
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
				mSensoresCasos = estudiosAdapter.getSensoresCasos(CasosDBConstants.codigoCaso + " = '" + casaCaso.getCodigoCaso() + "' and " + MainDBConstants.pasive + " ='0'", CasosDBConstants.numeroSensor);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			textView.setText("");
			textView.setTextColor(Color.BLACK);
            textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.sensors)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCaso.getCasa().getCodigoCHF()+ " - " + formatter.format(casaCaso.getFechaInicio()));
			mSensoresAdapter = new SensoresCasoAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item, mSensoresCasos);
			setListAdapter(mSensoresAdapter);
			dismissProgressDialog();
		}

	}	
	


}
