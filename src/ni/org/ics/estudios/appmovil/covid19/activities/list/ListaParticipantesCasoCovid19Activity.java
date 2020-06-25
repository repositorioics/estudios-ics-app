package ni.org.ics.estudios.appmovil.covid19.activities.list;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaMuestrasSuperficieCasoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevoTamizajeMxSuperficieActivity;
import ni.org.ics.estudios.appmovil.covid19.activities.enterdata.NuevoTamizajeTransmisionCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.adapters.ParticipanteCasoCovid19Adapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.CasaCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.covid19.CasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListaParticipantesCasoCovid19Activity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mButton;
	private ArrayAdapter<ParticipanteCasoCovid19> mParticipantesCovid19Adapter;
	private List<ParticipanteCasoCovid19> participanteCasoCovid19List = new ArrayList<ParticipanteCasoCovid19>();
	private List<MessageResource> mPositivoPor = new ArrayList<MessageResource>();
	private EstudiosAdapter estudiosAdapter;
	private ParticipanteCasoCovid19 participanteCasoCovid19;
	private AlertDialog alertDialog;
	private static CasoCovid19 casaCaso = new CasoCovid19();

	private static final int UPDATE_EQUIPO = 11;
	private static final String VER_VISITAS="VISITAS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add_casos_covid19);
		casaCaso = (CasoCovid19) getIntent().getExtras().getSerializable(Constants.CASA);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_today);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCasasCasosTask().execute();

		mButton = (Button) findViewById(R.id.add_visit_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.fail_visit_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.new_sint_button_cv19);
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

		mButton = (Button) findViewById(R.id.final_visit_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.update_cases_cv19);
		mButton.setVisibility(View.GONE);

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
		 participanteCasoCovid19 = (ParticipanteCasoCovid19) this.getListAdapter().getItem(position);
		if (!participanteCasoCovid19.getConsentimiento().equalsIgnoreCase("2")) {
			crearFomulario("VISITAS");
		}else{
			if (participanteCasoCovid19.getEnfermo().equalsIgnoreCase("N")){//solo miembros de familia
				createDialog(3);
			}else if (participanteCasoCovid19.getParticipante().getProcesos().getEstudio().contains("Influenza"))
				createDialog(2);
			else createDialog(1); //UO1
		}
	}

	private void createDialog(int tipo){
		if (tipo==1) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(this.getString(R.string.remember));
			String message = "Participante esta pendiente de consentimiento Covid19, favor comunicarse con su supervisor";
			builder.setMessage(message);
			builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					crearFomulario(VER_VISITAS);
				}
			});
			alertDialog = builder.create();
			alertDialog.show();
		}
		if (tipo==2){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(this.getString(R.string.remember));
			String message = "Participante esta pendiente de reconsentimiento Influenza, favor comunicarse con su supervisor";
			builder.setMessage(message);
			builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					crearFomulario(VER_VISITAS);
				}
			});
			alertDialog = builder.create();
			alertDialog.show();
		}
		if (tipo==3){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(this.getString(R.string.confirm));
			String message = "Participante esta pendiente Tamizaje Transmisión Covid19. Desea realizarlo?";
			builder.setMessage(message);
			builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					crearFomulario(Constants.T_COVID19);
				}
			}).setNegativeButton(this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			alertDialog = builder.create();
			alertDialog.show();
		}
	}

	private void crearFomulario(String formulario){
		Bundle arguments = new Bundle();
		Intent i;
		if (formulario.equalsIgnoreCase(Constants.T_COVID19)){
			arguments.putSerializable(Constants.PARTICIPANTE, participanteCasoCovid19.getParticipante());
			i = new Intent(getApplicationContext(),
					NuevoTamizajeTransmisionCovid19Activity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtras(arguments);
			i.putExtra(Constants.ES_CANDIDATO, false);
			startActivity(i);
			//finish();
		}else {
			arguments.putSerializable(Constants.PARTICIPANTE, participanteCasoCovid19);
			i = new Intent(getApplicationContext(),
					ListaVisitasParticipanteCasoCovid19Activity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtras(arguments);
			startActivity(i);
			//finish();
		}

		//new OpenActivityTask().execute(formulario);
	}
	
	@Override
	public void onBackPressed (){
		Intent i;
		i = new Intent(getApplicationContext(),
				ListaParticipantesPosCasoCovid19Activity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {	
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == UPDATE_EQUIPO){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			String mensaje = "";
			if (resultCode == RESULT_CANCELED) {
				builder.setTitle(getApplicationContext().getString(R.string.error));
				builder.setIcon(R.drawable.ic_menu_close_clear_cancel);
				mensaje = intent.getStringExtra("resultado");
			}
			else{
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(R.drawable.ic_menu_info_details);
				mensaje = getApplicationContext().getString(R.string.success);
                new FetchDataCasasCasosTask().execute();
			}

			builder.setMessage(mensaje)
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
	
	private class FetchDataCasasCasosTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				participanteCasoCovid19List = estudiosAdapter.getParticipantesCasosCovid19(Covid19DBConstants.codigoCaso+"='"+casaCaso.getCodigoCaso()+"' and "+ MainDBConstants.pasive + "='0'", null);
				mPositivoPor = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='COVID_CAT_POSITIVO_POR'", null);
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
			textView.setText(getString(R.string.main_4) +"\n"+ getString(R.string.follow_up_list_par)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCaso.getCasa().getCodigoCHF() + " - " + formatter.format(casaCaso.getFechaIngreso()));
			mParticipantesCovid19Adapter = new ParticipanteCasoCovid19Adapter(getApplication().getApplicationContext(), R.layout.complex_list_item, participanteCasoCovid19List, mPositivoPor);
			setListAdapter(mParticipantesCovid19Adapter);
			dismissProgressDialog();
		}

	}

	// ***************************************
	// Private classes
	// ***************************************
	private class OpenActivityTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			String formulario = values[0];
			try {
				Bundle arguments = new Bundle();
				Intent i;
				if (formulario.equalsIgnoreCase(Constants.T_COVID19)){
					arguments.putSerializable(Constants.PARTICIPANTE, participanteCasoCovid19.getParticipante());
					i = new Intent(getApplicationContext(),
							NuevoTamizajeTransmisionCovid19Activity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtras(arguments);
					i.putExtra(Constants.ES_CANDIDATO, false);
					startActivity(i);
					finish();
				}else {
					arguments.putSerializable(Constants.PARTICIPANTE, participanteCasoCovid19);
					i = new Intent(getApplicationContext(),
							ListaVisitasParticipanteCasoCovid19Activity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtras(arguments);
					startActivity(i);
					finish();
				}
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
