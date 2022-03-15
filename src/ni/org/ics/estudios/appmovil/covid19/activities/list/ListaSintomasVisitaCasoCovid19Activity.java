package ni.org.ics.estudios.appmovil.covid19.activities.list;


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
import ni.org.ics.estudios.appmovil.covid19.activities.MenuVisitaCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.enterdata.NuevoSintomasVisitaCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.adapters.SintomasVisitaCasoCovid19Adapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.SintomasVisitaCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaSeguimientoCasoCovid19;
import ni.org.ics.estudios.appmovil.utils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaSintomasVisitaCasoCovid19Activity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddSintButton;
	private Button mButton;
	//Viene de la actividad principal
	private static VisitaSeguimientoCasoCovid19 visitaCaso = new VisitaSeguimientoCasoCovid19();
    //private static VisitaFinalCaso visitaFinalCaso = new VisitaFinalCaso();
	//Tipo de lista
    private static ParticipanteCasoCovid19 participanteCasoCovid19 = new ParticipanteCasoCovid19();
    private static Date fechaVisita = new Date();
    //Adaptador del objeto de la lista
	private ArrayAdapter<SintomasVisitaCasoCovid19> mMuestraAdapter;
	//Lista de objetos 
	private List<SintomasVisitaCasoCovid19> mSintomas = new ArrayList<SintomasVisitaCasoCovid19>();
	private List<MessageResource> mTiposMuestra = new ArrayList<MessageResource>();
	private Double volumenTotalPermitido = 0D;
	private AlertDialog alertDialog;
	
	private EstudiosAdapter estudiosAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add_casos_covid19);
		//Obtener objeto que viene del menú
        visitaCaso = (VisitaSeguimientoCasoCovid19) getIntent().getExtras().getSerializable(Constants.VISITA);
        //visitaFinalCaso = (VisitaFinalCaso) getIntent().getExtras().getSerializable(Constants.VISITA_FINAL);
        if (visitaCaso!=null) {
            participanteCasoCovid19 = visitaCaso.getCodigoParticipanteCaso();
            fechaVisita = visitaCaso.getFechaVisita();
        }
        /*if (visitaFinalCaso!=null) {
            participantechf= visitaFinalCaso.getCodigoParticipanteCaso().getParticipante();
            fechaVisita = visitaFinalCaso.getFechaVisita();
        }*/
        textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_samples_seg);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		//Obtener objetos para llenar la lista
		new FetchDataSintomasCasosTask().execute();

		mButton = (Button) findViewById(R.id.add_visit_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.fail_visit_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.final_visit_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.send_case_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.new_bhc_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.new_rojo_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.new_pbmc_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.new_resp_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.view_samp_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.new_MA_button_cv19);
		mButton.setVisibility(View.GONE);

		mAddSintButton = (Button) findViewById(R.id.new_sint_button_cv19);
		mAddSintButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				Bundle arguments = new Bundle();
				if (visitaCaso != null) arguments.putSerializable(Constants.VISITA, visitaCaso);
				Intent i;
				i = new Intent(getApplicationContext(),
						NuevoSintomasVisitaCasoCovid19Activity.class);
				i.putExtras(arguments);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
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
        //if (visitaCaso!=null) {
            arguments.putSerializable(Constants.VISITA, visitaCaso);
            i = new Intent(getApplicationContext(),
                    MenuVisitaCasoCovid19Activity.class);
        /*}else{
            arguments.putSerializable(Constants.VISITA_FINAL, visitaFinalCaso);
            i = new Intent(getApplicationContext(),
                    MenuVisitaFinalCasoActivity.class);
        }*/
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

	private class FetchDataSintomasCasosTask extends AsyncTask<String, Void, String> {
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
					mSintomas = estudiosAdapter.getSintomasVisitasCasosCovid19(Covid19DBConstants.codigoCasoVisita + " = '" + visitaCaso.getCodigoCasoVisita()+"'", Covid19DBConstants.fechaSintomas);
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
			textView.setText("");
			textView.setTextColor(Color.BLACK);
            //if (visitaFinalCaso==null) {
                textView.setText(getString(R.string.main_4) + "\n" + getString(R.string.follow_up_list_sint) + "\n" +
                        getString(R.string.code) + ": " + participanteCasoCovid19.getParticipante().getCodigo() + " - " + getString(R.string.visit) + ": " + visitaCaso.getVisita());
            /*}else{
                textView.setText(getString(R.string.main_4) + "\n" + getString(R.string.follow_up_list_samp) + "\n" +
                        getString(R.string.code) + ": " + participanteCasoCovid19.getParticipante().getCodigo() + " - " + getString(R.string.visit_final));
            }*/
			mMuestraAdapter = new SintomasVisitaCasoCovid19Adapter(getApplication().getApplicationContext(), R.layout.complex_list_item, mSintomas);
			setListAdapter(mMuestraAdapter);
			dismissProgressDialog();
		}

	}

}
