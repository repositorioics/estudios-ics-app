package ni.org.ics.estudios.appmovil.influenzauo1.activities.list;


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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.ListaMuestrasActivity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.ParticipanteCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaCasoUO1;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.MenuVisitaCasoUO1Activity;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.enterdata.NuevaMuestraTuboPbmcUO1Activity;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.enterdata.NuevaMuestraTuboRojoUO1Activity;
import ni.org.ics.estudios.appmovil.influenzauo1.adapters.MuestraUO1Adapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.MuestrasDBConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaMuestrasParticipanteCasoUO1Activity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddRojoButton;
	private Button mAddPbmcButton;
	private Button mButton;
	//Viene de la actividad principal
	private static VisitaCasoUO1 visitaCaso = new VisitaCasoUO1();
	//Tipo de lista
    private static ParticipanteCasoUO1 participanteCasoUO1 = new ParticipanteCasoUO1();
    private static Date fechaVisita = new Date();
    //Adaptador del objeto de la lista
	private ArrayAdapter<Muestra> mMuestraAdapter;
	//Lista de objetos 
	private List<Muestra> mMuestras = new ArrayList<Muestra>();
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
		mButton = (Button) findViewById(R.id.add_symptom_uo1_button);
		mButton.setVisibility(View.GONE);
		
		mAddRojoButton = (Button) findViewById(R.id.new_rojo_uo1_button);
		mAddPbmcButton = (Button) findViewById(R.id.new_pbmc_uo1_button);

		mAddRojoButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialogMuestras(Constants.CODIGO_TUBO_ROJO);
			}
		});
		
		mAddPbmcButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialogMuestras(Constants.CODIGO_TUBO_PBMC);
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

	private void crearFomulario(String tipo){
		new OpenDataEnterActivityTask().execute(tipo);
	}

	private double getVolumenExistente(String tipoTubo){
		double volumenTotal = 0D;
		for (Muestra muestra : mMuestras) {
			if (muestra.getTubo().equalsIgnoreCase(tipoTubo))
				if (muestra.getVolumen()!=null) volumenTotal += muestra.getVolumen();
		}
		return volumenTotal;
	}
	private void createDialogMuestras(final String opcion) {
		String labelMuestra = "";
		String labelVolumenPermitido = "";
		int edadMeses = participanteCasoUO1.getParticipante().getEdadMeses();
		/*Volumen de muestra para Serologia UO1 (Aguda y Convaleciente)
		 * < de 6 meses	| >= 6 meses y < de 2 años	| >= 2 años
		 *      N/A 	|           2 ml	        |   2 ml
		 */
		if (opcion.equalsIgnoreCase(Constants.CODIGO_TUBO_ROJO) && !participanteCasoUO1.getParticipante().getEdad().equalsIgnoreCase("ND")) {
			//>= 2 años
			if (edadMeses >= 24) {
				labelMuestra = getString(R.string.rojo2mlUO1_3);
				labelVolumenPermitido = getString(R.string.rojo2mlUO1Permitido_3);
				volumenTotalPermitido = 3D;//permitir 1 ml de desviación
			} else if (edadMeses >= 6) { //>= 6 meses y < de 2 años
				labelMuestra = getString(R.string.rojo2mlUO1_2);
				labelVolumenPermitido = getString(R.string.rojo2mlUO1Permitido_2);
				volumenTotalPermitido = 3D;//permitir 1 ml de desviación
			} else { //< de 6 meses
				labelMuestra = getString(R.string.rojo2mlUO1_1);
				labelVolumenPermitido = "";
				volumenTotalPermitido = 0D;
			}

		}
		/*Volumen de muestra para PBMC UO1 (Aguda y Convaleciente)
		 * < de 6 meses	| >= 6 meses y < de 2 años	| >= 2 años
		 *      2 ml 	|           2 ml	        |   6 ml
		 */
		if (opcion.equalsIgnoreCase(Constants.CODIGO_TUBO_PBMC) && !participanteCasoUO1.getParticipante().getEdad().equalsIgnoreCase("ND")) {
			//>= 2 años
			if (edadMeses >= 24) {
				labelMuestra = getString(R.string.pbmc2mlUO1_3);
				labelVolumenPermitido = getString(R.string.pbmc2mlUO1Permitido_3);
				volumenTotalPermitido = 7D;//permitir 1 ml de desviación
			} else if (edadMeses >= 6) { //>= 6 meses y < de 2 años
				labelMuestra = getString(R.string.pbmc2mlUO1_2);
				labelVolumenPermitido = getString(R.string.pbmc2mlUO1Permitido_2);
				volumenTotalPermitido = 3D;//permitir 1 ml de desviación
			} else {//< de 6 meses
				labelMuestra = getString(R.string.pbmc2mlUO1_1);
				labelVolumenPermitido = getString(R.string.pbmc2mlUO1Permitido_1);
				volumenTotalPermitido = 3D;//permitir 1 ml de desviación
			}

		}

		Double volumenExistente = getVolumenExistente(opcion);
		if (volumenTotalPermitido > volumenExistente) {
			volumenTotalPermitido = volumenTotalPermitido - volumenExistente;
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(this.getString(R.string.remember));
			builder.setMessage(labelMuestra + "\n" + getString(R.string.code) + ": " + participanteCasoUO1.getParticipante().getCodigo() + " - " + participanteCasoUO1.getParticipante().getNombre1() + " " + participanteCasoUO1.getParticipante().getApellido1());
			builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					crearFomulario(opcion);
				}
			});
			alertDialog = builder.create();
			alertDialog.show();
		}else{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(this.getString(R.string.notavailable));
			builder.setMessage(labelVolumenPermitido + "\n" + getString(R.string.code) + ": " + participanteCasoUO1.getParticipante().getCodigo() + " - " + participanteCasoUO1.getParticipante().getNombre1() + " " + participanteCasoUO1.getParticipante().getApellido1());
			builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			alertDialog = builder.create();
			alertDialog.show();
		}
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
				mMuestras = estudiosAdapter.getMuestras(MuestrasDBConstants.participante + " = " + participanteCasoUO1.getParticipante().getCodigo()
						+" and " + MainDBConstants.pasive + " ='0' and " + MuestrasDBConstants.proposito + " ='" + Constants.CODIGO_PROPOSITO_UO1 + "'" +
								" and " + MainDBConstants.recordDate + "= " + fechaVisita.getTime(), MuestrasDBConstants.tipoMuestra);
				mTiposMuestra = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_TIP_TUBO_MX'" + " or " + CatalogosDBConstants.catRoot + "='CHF_CAT_RAZON_NO_MX'", null);
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
			textView.setText(getString(R.string.main_3) + "\n" + getString(R.string.follow_up_list_samp) + "\n" +
                        getString(R.string.code) + ": " + participanteCasoUO1.getParticipante().getCodigo() + " - " +
					(visitaCaso.getVisita().equalsIgnoreCase("I")?getString(R.string.visita_inicial):getString(R.string.visita_final)));

			mMuestraAdapter = new MuestraUO1Adapter(getApplication().getApplicationContext(), R.layout.complex_list_item, mMuestras, mTiposMuestra);
			setListAdapter(mMuestraAdapter);
			dismissProgressDialog();
		}

	}
	
	// ***************************************
	// Private classes
	// ***************************************
	private class OpenDataEnterActivityTask extends AsyncTask<String, Void, String> {
		String opcion;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				opcion = values[0];
				Bundle arguments = new Bundle();				
				if (visitaCaso != null) arguments.putSerializable(Constants.VISITA, visitaCaso);
				arguments.putSerializable(Constants.VOLUMEN , volumenTotalPermitido);
				Intent i;
				switch (opcion) {
					case Constants.CODIGO_TUBO_PBMC:
						i = new Intent(getApplicationContext(),
								NuevaMuestraTuboPbmcUO1Activity.class);
						i.putExtra(Constants.ACCION, Constants.CODIGO_PROPOSITO_UO1);
						break;
					case Constants.CODIGO_TUBO_ROJO:
						i = new Intent(getApplicationContext(),
								NuevaMuestraTuboRojoUO1Activity.class);
						i.putExtra(Constants.ACCION, Constants.CODIGO_PROPOSITO_UO1);
						break;
					default:
						return "error";
				}
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
