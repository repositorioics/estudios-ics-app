package ni.org.ics.estudios.appmovil.cohortefamilia.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevaEncuestaCasaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.MenuCasaAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Habitacion;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaCasa;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;


public class MenuCasaActivity extends AbstractAsyncActivity {

	private GridView gridView;
	private TextView textView;
	private String[] menu_casa;
	private static CasaCohorteFamilia casaCHF = new CasaCohorteFamilia();
	private List<ParticipanteCohorteFamilia> mParticipantes = new ArrayList<ParticipanteCohorteFamilia>();
	private List<Habitacion> mHabitaciones = new ArrayList<Habitacion>();
    private AlertDialog alertDialog;
    boolean existeencuestaCasa = false;

	private EstudiosAdapter estudiosAdapter;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_cohorte_familia);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		textView = (TextView) findViewById(R.id.label);
		gridView = (GridView) findViewById(R.id.gridView1);
		menu_casa = getResources().getStringArray(R.array.menu_casa);
		casaCHF = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASA);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCasaTask().execute(casaCHF.getCodigoCHF());
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
				Intent i;
				switch (position){
				case 0:
					if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
					i = new Intent(getApplicationContext(),
							ListaParticipantesActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtras(arguments);
					startActivity(i);
		        	break;
                    case 1:
                        createDialog(position);
                        break;
                    case 2:
                    	if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
    					i = new Intent(getApplicationContext(),
    							ListaHabitacionesActivity.class);
    					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    					i.putExtras(arguments);
    					startActivity(i);break;
                    case 3:
                    	if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
    					i = new Intent(getApplicationContext(),
    							ListaAreasActivity.class);
    					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    					i.putExtras(arguments);
    					startActivity(i);break;    					
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
            case 1:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.confirm_house_survey) + "\n" + getString(R.string.code) + ": " + casaCHF.getCasa().getCodigo() + " " + casaCHF.getCasa().getNombre1JefeFamilia() + " " + casaCHF.getCasa().getApellido1JefeFamilia());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        crearEncuestaCasa();
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

    private void crearEncuestaCasa(){
        new OpenDataEnterActivityTask().execute();
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
            Bundle arguments = new Bundle();
            Intent i;
            try {
                  if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
                        i = new Intent(getApplicationContext(),
                                NuevaEncuestaCasaActivity.class);
                        i.putExtras(arguments);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

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
				mHabitaciones = estudiosAdapter.getHabitaciones(MainDBConstants.casa +" = " + codigoCasaCHF + " and " + MainDBConstants.tipo + " ='habitacion'", MainDBConstants.codigoHabitacion);
                EncuestaCasa encuestaExiste = estudiosAdapter.getEncuestaCasa(EncuestasDBConstants.casa_chf + "=" + casaCHF.getCodigoCHF(), EncuestasDBConstants.casa_chf);
                if (encuestaExiste != null)
                    existeencuestaCasa = true;
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.header_casa)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCHF.getCodigoCHF());
			gridView.setAdapter(new MenuCasaAdapter(getApplicationContext(), R.layout.menu_item_2, menu_casa, mParticipantes.size(), mHabitaciones.size(), existeencuestaCasa));
			dismissProgressDialog();
		}

	}	
		
}
	
