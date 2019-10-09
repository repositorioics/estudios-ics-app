package ni.org.ics.estudios.appmovil.influenzauo1.activities.list;


import android.app.AlertDialog;
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
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.ParticipanteCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.SintomasVisitaCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaCasoUO1;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.MenuVisitaCasoUO1Activity;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.enterdata.NuevoSintomaVisitaCasoUO1Activity;
import ni.org.ics.estudios.appmovil.influenzauo1.adapters.SintomasVisitaCasoUO1Adapter;
import ni.org.ics.estudios.appmovil.utils.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaSintomasVisitaCasoUO1Activity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddSymptom;
	private Button mButton;
	//Viene de la actividad principal
	private static VisitaCasoUO1 visitaCaso = new VisitaCasoUO1();
	//Tipo de lista
    private static ParticipanteCasoUO1 participanteCasoUO1 = new ParticipanteCasoUO1();
    private static Date fechaVisita = new Date();
    //Adaptador del objeto de la lista
	private ArrayAdapter<SintomasVisitaCasoUO1> mSintomasAdapter;
	//Lista de objetos 
	private List<SintomasVisitaCasoUO1> mSintomas = new ArrayList<SintomasVisitaCasoUO1>();
	private List<MessageResource> mTiposMuestra = new ArrayList<MessageResource>();
	private Double volumenTotalPermitido = 0D;
	private EstudiosAdapter estudiosAdapter;
	private AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add_uo1);
		//Obtener objeto que viene del menú
        visitaCaso = (VisitaCasoUO1) getIntent().getExtras().getSerializable(Constants.VISITA);
        if (visitaCaso!=null) {
            participanteCasoUO1 = visitaCaso.getParticipanteCasoUO1();
            fechaVisita = visitaCaso.getFechaVisita();
        }

        textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_samples_seg);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		//Obtener objetos para llenar la lista
		new FetchDataMuestrasCasosTask().execute();
		
		mButton = (Button) findViewById(R.id.add_visit_uo1_button);
		mButton.setVisibility(View.GONE);
		mButton = (Button) findViewById(R.id.new_rojo_uo1_button);
		mButton.setVisibility(View.GONE);
		mButton = (Button) findViewById(R.id.new_pbmc_uo1_button);
		mButton.setVisibility(View.GONE);

		mAddSymptom = (Button) findViewById(R.id.add_symptom_uo1_button);

		mAddSymptom.setOnClickListener(new View.OnClickListener()  {
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
        
        // Opcion de menu seleccionada tiene que editar o quitar
       
	}
	
	@Override
	public void onBackPressed (){
		//Llamar al menu anterior
		Bundle arguments = new Bundle();
		Intent i;
		i = new Intent(getApplicationContext(),
				MenuVisitaCasoUO1Activity.class);
		arguments.putSerializable(Constants.VISITA, visitaCaso);
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

	private class FetchDataMuestrasCasosTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				//Llenar el objeto de lista de esta View
				mSintomas = estudiosAdapter.getSintomasVisitasCasosUO1(InfluenzaUO1DBConstants.codigoCasoVisita + " = '" + visitaCaso.getCodigoCasoVisita() + "' ", InfluenzaUO1DBConstants.fechaSintomas);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			//Actualizar el encabezado de esta view y enlazar el adapter
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			textView.setText("");
			textView.setTextColor(Color.BLACK);
			textView.setText(getString(R.string.main_3) + "\n" + getString(R.string.sint) + "\n" +
                        getString(R.string.code) + ": " + participanteCasoUO1.getParticipante().getCodigo() + " - " +
					(visitaCaso.getVisita().equalsIgnoreCase("I")?getString(R.string.visita_inicial):getString(R.string.visita_final))+" - "+ formatter.format(visitaCaso.getFechaVisita()));

			mSintomasAdapter = new SintomasVisitaCasoUO1Adapter(getApplication().getApplicationContext(), R.layout.complex_list_item, mSintomas);
			setListAdapter(mSintomasAdapter);
			dismissProgressDialog();
		}

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
				if (visitaCaso != null) arguments.putSerializable(Constants.VISITA, visitaCaso);
				Intent i = new Intent(getApplicationContext(),
								NuevoSintomaVisitaCasoUO1Activity.class);
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
}
