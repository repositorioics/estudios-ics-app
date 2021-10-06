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
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.MuestraAdapter;
import ni.org.ics.estudios.appmovil.covid19.activities.MenuVisitaCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.MenuVisitaFinalCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.enterdata.NuevaMuestraRespCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.enterdata.NuevaMuestraTuboBhcCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.enterdata.NuevaMuestraTuboPbmcCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.enterdata.NuevaMuestraTuboRojoCovid19Activity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaFinalCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaSeguimientoCasoCovid19;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.MuestrasDBConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaMuestrasParticipanteCasoCovid19Activity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddBhcButton;
	private Button mAddRojoButton;
	private Button mAddPbmcButton;
	private Button mAddRespButton;
	private Button mReviewButton;
	private Button mButton;
	//Viene de la actividad principal
	private static VisitaSeguimientoCasoCovid19 visitaCaso = new VisitaSeguimientoCasoCovid19();
    private static VisitaFinalCasoCovid19 visitaFinalCaso = new VisitaFinalCasoCovid19();
	//Tipo de lista
    private static ParticipanteCasoCovid19 participanteCasoCovid19 = new ParticipanteCasoCovid19();
    private static Date fechaVisita = new Date();
    //Adaptador del objeto de la lista
	private ArrayAdapter<Muestra> mMuestraAdapter;
	//Lista de objetos 
	private List<Muestra> mMuestras = new ArrayList<Muestra>();
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
        visitaFinalCaso = (VisitaFinalCasoCovid19) getIntent().getExtras().getSerializable(Constants.VISITA_FINAL);
        if (visitaCaso!=null) {
            participanteCasoCovid19 = visitaCaso.getCodigoParticipanteCaso();
            fechaVisita = visitaCaso.getFechaVisita();
        }
        if (visitaFinalCaso!=null) {
			participanteCasoCovid19= visitaFinalCaso.getCodigoParticipanteCaso();
            fechaVisita = visitaFinalCaso.getFechaVisita();
        }
        textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_samples_seg);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		//Obtener objetos para llenar la lista
		new FetchDataMuestrasCasosTask().execute();

		mButton = (Button) findViewById(R.id.add_visit_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.fail_visit_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.new_sint_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.final_visit_button_cv19);
		mButton.setVisibility(View.GONE);

		mButton = (Button) findViewById(R.id.send_case_cv19);
		mButton.setVisibility(View.GONE);

		mAddBhcButton = (Button) findViewById(R.id.new_bhc_button_cv19);
		mAddRojoButton = (Button) findViewById(R.id.new_rojo_button_cv19);
		mAddPbmcButton = (Button) findViewById(R.id.new_pbmc_button_cv19);
		mAddRespButton = (Button) findViewById(R.id.new_resp_button_cv19);
		if (!participanteCasoCovid19.getParticipante().getProcesos().getEstudio().contains(Constants.T_COVID19)){
			mAddRespButton.setVisibility(View.GONE);
			mAddBhcButton.setVisibility(View.GONE);
		}

		mReviewButton = (Button) findViewById(R.id.view_samp_button_cv19);
		mReviewButton.setVisibility(View.GONE);

		mAddBhcButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				//volumenTotalPermitido = 1D;
				createDialogMuestras(Constants.CODIGO_TUBO_BHC);
				//new OpenDataEnterActivityTask().execute(Constants.CODIGO_TUBO_BHC);
			}
		});

		mAddRojoButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialogMuestras(Constants.CODIGO_TUBO_ROJO);
				//new OpenDataEnterActivityTask().execute(Constants.CODIGO_TUBO_ROJO);
			}
		});
		
		mAddRespButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				new OpenDataEnterActivityTask().execute(Constants.CODIGO_TIPO_RESP);
			}
		});
		
		mAddPbmcButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialogMuestras(Constants.CODIGO_TUBO_PBMC);
				//new OpenDataEnterActivityTask().execute(Constants.CODIGO_TUBO_PBMC);
			}
		});
		
		/*mReviewButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				Bundle arguments = new Bundle();
				if (participanteCasoCovid19 != null) arguments.putSerializable(Constants.PARTICIPANTE, participanteCasoCovid19);
	            Intent i = new Intent(getApplicationContext(),
	                    ListaMuestrasActivity.class);
	            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            i.putExtra(Constants.ACCION, Constants.REVIEWING);
	            i.putExtras(arguments);
	            startActivity(i);
			}
		});*/

        if (visitaFinalCaso!=null){
            mAddRespButton.setVisibility(View.GONE);
            mAddBhcButton.setVisibility(View.GONE);
        }
		
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
        if (visitaCaso!=null) {
            arguments.putSerializable(Constants.VISITA, visitaCaso);
            i = new Intent(getApplicationContext(),
                    MenuVisitaCasoCovid19Activity.class);
        }else{
            arguments.putSerializable(Constants.VISITA_FINAL, visitaFinalCaso);
            i = new Intent(getApplicationContext(),
                    MenuVisitaFinalCasoCovid19Activity.class);
        }
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

	private double getVolumenExistente(String tipoTubo){
		double volumenTotal = 0D;
		for (Muestra muestra : mMuestras) {
			if (muestra.getTubo().equalsIgnoreCase(tipoTubo))
				if (muestra.getVolumen()!=null) volumenTotal += muestra.getVolumen();
		}
		return volumenTotal;
	}

	/****
	 * Metodo para mostrar en pantalla la sugerencia de cuanto volumen es el requerido segun la edad y el estudio
	 *
	 ************************** UO1 **************************
	 * 		INICIAL				|		FINAL
	 * Menores de 6 meses 		|  Menores de 6 meses
	 * 		2 ml Pbmc			| 		2 ml Pbmc
	 * NOTA: SINO SE TOMA PBMC SE TOMARÁ 2ML ROJO
	 *
	 * 6 meses –menor de 2 años	| 6 meses - menor de 2 años
	 * 		2 ml Pbmc			|		2 ml Pbmc
	 * 		2 ml rojo			|		2 ml rojo
	 * NOTA: SINO SE TOMA PBMC SE TOMARÁ 4ML ROJO
	 *
	 * Mayores de 2 años. 		|  Mayores de 2 años.
	 *  	6 ml Pbmc	 		|		6 ml Pbmc
	 *  	2 ml rojo	 		|		2 ml rojo
	 * NOTA: SINO SE TOMA PBMC SE TOMARÁ 8ML ROJO
	 *
	 ******** COHORTE  - FAMILA (TRASMISIÓN COVID ) ***********
	 * 		INICIAL							|		FINAL
	 * Menor de 6 meses 	  				|	Menor de 6 meses
	 * 		2ml rojo						|		2ml rojo
	 *
	 * Mayor de 6 meses y menor de 3 años.	|  Mayor de 6 meses y menor  de 3 años.
	 * 		1 ml Rojo						|		1 ml Rojo
	 * 		3 ml PBMC						|		3 ml PBMC
	 * NOTA: SINO SE TOMA PBMC SE TOMARÁ 4ML ROJO
	 *
	 * 3 años - 14 años.					| 3 años - 14 años.
	 * 		1 ml BHC
	 * 		2 ml Rojo						|		2 ml Rojo
	 * 		6 ml Pbmc						|		6 ml Pbmc
	 * NOTA: SINO SE TOMA PBMC SE TOMARÁ 8ML ROJO
	 *
	 * 15 años a mas (Adultos)				|  15 años a mas (Adultos)
	 * 		1 ml BHC
	 * 		6 ml Rojo						|		6 ml Rojo
	 * 		6 ml Pbmc						|		6 ml Pbmc
	 * NOTA: SINO SE TOMA PBMC SE TOMARÁ 12ML ROJO
	 *
	 *  @param opcion tipo de tubo
	 */
	private void createDialogMuestras(final String opcion) {
		String labelMuestra = "";
		String labelVolumenPermitido = "";
		int edadMeses = participanteCasoCovid19.getParticipante().getEdadMeses();
		if (!participanteCasoCovid19.getParticipante().getEdad().equalsIgnoreCase("ND")){
			//UO1
			if (!participanteCasoCovid19.getParticipante().getProcesos().getEstudio().contains(Constants.T_COVID19)){
				if (opcion.equalsIgnoreCase(Constants.CODIGO_TUBO_ROJO)) {
					//Mayores de 2 años
					if (edadMeses >= 24) {
						labelMuestra = getString(R.string.rojo_covid19_mas2_uo1);
						labelVolumenPermitido = getString(R.string.rojo_covid19_mas2_perm_uo1);
						volumenTotalPermitido = 9D;//permitir 1 ml de desviación
					} //6 meses –menor de 2 años
					else if (edadMeses>= 6) {
						labelMuestra = getString(R.string.rojo_covid19_2a6m_uo1);
						labelVolumenPermitido = getString(R.string.rojo_covid19_2a6m_perm_uo1);
						volumenTotalPermitido = 5D;//permitir 1 ml de desviación
					} else {
						labelMuestra = getString(R.string.rojo_covid19_6m_uo1);
						labelVolumenPermitido = getString(R.string.rojo_covid19_6m_perm_uo1);
						volumenTotalPermitido = 3D;
					}
				} else if (opcion.equalsIgnoreCase(Constants.CODIGO_TUBO_PBMC)) {
					//Mayores de 2 años
					if (edadMeses >= 24) {
						labelMuestra = getString(R.string.pbmc_covid19_mas2_uo1);
						labelVolumenPermitido = getString(R.string.pbmc_covid19_mas2_uo1);
						volumenTotalPermitido = 7D;//permitir 1 ml de desviación
					} //6 meses –menor de 2 años
					else if (edadMeses>= 6) {
						labelMuestra = getString(R.string.pbmc_covid19_2a6m_uo1);
						labelVolumenPermitido = getString(R.string.pbmc_covid19_2a6m_perm_uo1);
						volumenTotalPermitido = 3D;//permitir 1 ml de desviación
					} else {
						labelMuestra = getString(R.string.pbmc_covid19_6m_uo1);
						labelVolumenPermitido = getString(R.string.pbmc_covid19_6m_perm_uo1);
						volumenTotalPermitido = 3D;
					}
				}
			}  //COHORTE  - FAMILA (TRASMISIÓN COVID )
			else {
				if (opcion.equalsIgnoreCase(Constants.CODIGO_TUBO_ROJO)) {
					//DE 15 AÑOS A MAS
					if (edadMeses>=180) {
						labelMuestra = getString(R.string.rojo_covid19_mas15_chf);
						labelVolumenPermitido = getString(R.string.rojo_covid19_mas15_perm_chf);
						volumenTotalPermitido = 13D;//permitir 1 ml de desviación
					} else if (edadMeses >= 36) { //MAYOR DE 3 AÑOS A 14 AÑOS
						labelMuestra = getString(R.string.rojo_covid19_14a3a_chf);
						labelVolumenPermitido = getString(R.string.rojo_covid19_14a3a_perm_chf);
						volumenTotalPermitido = 9D;//permitir 1 ml de desviación
					} else if (edadMeses >= 6) { //MAYOR DE 6 MESES Y MENOR DE 3 AÑOS
						labelMuestra = getString(R.string.rojo_covid19_3a6m_chf);
						labelVolumenPermitido = getString(R.string.rojo_covid19_3a6m_perm_chf);
						volumenTotalPermitido = 5D;//permitir 1 ml de desviación
					} else { //menor de 6 meses
						labelMuestra = getString(R.string.rojo_covid19_6m_chf);
						labelVolumenPermitido = getString(R.string.rojo_covid19_6m_perm_chf);
						volumenTotalPermitido = 3D;//permitir 1 ml de desviación
					}
				} else if (opcion.equalsIgnoreCase(Constants.CODIGO_TUBO_PBMC)) {
					//DE 15 AÑOS A MAS
					if (edadMeses>=180) {
						labelMuestra = getString(R.string.pbmc_covid19_mas15_chf);
						labelVolumenPermitido = getString(R.string.pbmc_covid19_mas15_perm_chf);
						volumenTotalPermitido = 7D;//permitir 1 ml de desviación
					}else if (edadMeses >= 36) {//MAYOR DE 3 AÑOS A 14 AÑOS
						labelMuestra = getString(R.string.pbmc_covid19_14a3a_chf);
						labelVolumenPermitido = getString(R.string.pbmc_covid19_14a3a_perm_chf);
						volumenTotalPermitido = 7D;//permitir 1 ml de desviación
					} else if (edadMeses >= 6) { //MAYOR DE 6 MESES Y MENOR DE 3 AÑOS
						labelMuestra = getString(R.string.pbmc_covid19_3a6m_chf);
						labelVolumenPermitido = getString(R.string.pbmc_covid19_3a6m_perm_chf);
						volumenTotalPermitido = 4D;//permitir 1 ml de desviación
					} else { //< de 6 meses
						labelMuestra = getString(R.string.pbmc_covid19_6m_chf);
						labelVolumenPermitido = getString(R.string.pbmc_covid19_6m_perm_chf);
						volumenTotalPermitido = 0D;//permitir 1 ml de desviación
					}

				} else if (opcion.equalsIgnoreCase(Constants.CODIGO_TUBO_BHC)){
					//DE 15 AÑOS A MAS
					if (edadMeses>=180) {
						labelMuestra = getString(R.string.bhc_covid19_mas15_chf);
						labelVolumenPermitido = getString(R.string.bhc_covid19_mas15_perm_chf);
						volumenTotalPermitido = 7D;//permitir 1 ml de desviación
					}else if (edadMeses >= 36) {//MAYOR DE 3 AÑOS A 14 AÑOS
						labelMuestra = getString(R.string.bhc_covid19_3a14a_chf);
						labelVolumenPermitido = getString(R.string.bhc_covid19_3a14a_chf);
						volumenTotalPermitido = 2D;//permitir 1 ml de desviación
					} else {
						labelMuestra = getString(R.string.bhc_covid19_menos3a_chf);
						volumenTotalPermitido = 0D;//permitir 1 ml de desviación
					}

				}
			}
		}

		Double volumenExistente = getVolumenExistente(opcion);
		if (volumenTotalPermitido > volumenExistente) {
			volumenTotalPermitido = volumenTotalPermitido - volumenExistente;
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(this.getString(R.string.remember));
			builder.setMessage(labelMuestra + "\n" + getString(R.string.code) + ": " + participanteCasoCovid19.getParticipante().getCodigo() + " - " + participanteCasoCovid19.getParticipante().getNombre1() + " " + participanteCasoCovid19.getParticipante().getApellido1());
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
			builder.setMessage(labelVolumenPermitido + "\n" + getString(R.string.code) + ": " + participanteCasoCovid19.getParticipante().getCodigo() + " - " + participanteCasoCovid19.getParticipante().getNombre1() + " " + participanteCasoCovid19.getParticipante().getApellido1());
			builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			alertDialog = builder.create();
			alertDialog.show();
		}
	}

	private void crearFomulario(String tipo){
		new OpenDataEnterActivityTask().execute(tipo);
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
				if (participanteCasoCovid19.getParticipante().getProcesos().getEstudio().contains(Constants.T_COVID19))
					mMuestras = estudiosAdapter.getMuestras(MuestrasDBConstants.participante + " = " + participanteCasoCovid19.getParticipante().getCodigo()
							+ " and " + MainDBConstants.pasive + " ='0' and " + MuestrasDBConstants.proposito + " ='" + Constants.CODIGO_PROPOSITO_T_COVID + "'" +
							" and " + MainDBConstants.recordDate + "= " + fechaVisita.getTime(), MuestrasDBConstants.tipoMuestra);
				else
					mMuestras = estudiosAdapter.getMuestras(MuestrasDBConstants.participante + " = " + participanteCasoCovid19.getParticipante().getCodigo()
							+ " and " + MainDBConstants.pasive + " ='0' and " + MuestrasDBConstants.proposito + " ='" + Constants.CODIGO_PROPOSITO_COVID_CP + "'" +
							" and " + MainDBConstants.recordDate + "= " + fechaVisita.getTime(), MuestrasDBConstants.tipoMuestra);
				mTiposMuestra = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_TIP0_MX_RESP'" + " or " + CatalogosDBConstants.catRoot + "='CHF_CAT_TIP_TUBO_MX'" + " or " + CatalogosDBConstants.catRoot + "='CHF_CAT_RAZON_NO_MX'", null);
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
            if (visitaFinalCaso==null) {
                textView.setText(getString(R.string.main_4) + "\n" + getString(R.string.follow_up_list_samp) + "\n" +
                        getString(R.string.code) + ": " + participanteCasoCovid19.getParticipante().getCodigo() + " - " + getString(R.string.visit) + ": " + visitaCaso.getVisita());
            }else{
                textView.setText(getString(R.string.main_4) + "\n" + getString(R.string.follow_up_list_samp) + "\n" +
                        getString(R.string.code) + ": " + participanteCasoCovid19.getParticipante().getCodigo() + " - " + getString(R.string.visit_final));
            }
			mMuestraAdapter = new MuestraAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item, mMuestras, mTiposMuestra);
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
                if (visitaFinalCaso != null) arguments.putSerializable(Constants.VISITA, visitaFinalCaso);
				Intent i;
				switch (opcion) {
					case Constants.CODIGO_TUBO_BHC:
						i = new Intent(getApplicationContext(),
								NuevaMuestraTuboBhcCovid19Activity.class);
						if (participanteCasoCovid19.getParticipante().getProcesos().getEstudio().contains(Constants.T_COVID19))
							i.putExtra(Constants.ACCION, Constants.CODIGO_PROPOSITO_T_COVID);
						else i.putExtra(Constants.ACCION, Constants.CODIGO_PROPOSITO_COVID_CP);
						break;
					case Constants.CODIGO_TUBO_ROJO:
						i = new Intent(getApplicationContext(),
								NuevaMuestraTuboRojoCovid19Activity.class);
						if (participanteCasoCovid19.getParticipante().getProcesos().getEstudio().contains(Constants.T_COVID19))
							i.putExtra(Constants.ACCION, Constants.CODIGO_PROPOSITO_T_COVID);
						else i.putExtra(Constants.ACCION, Constants.CODIGO_PROPOSITO_COVID_CP);
						break;
					case Constants.CODIGO_TUBO_PBMC:
						i = new Intent(getApplicationContext(),
								NuevaMuestraTuboPbmcCovid19Activity.class);
						if (participanteCasoCovid19.getParticipante().getProcesos().getEstudio().contains(Constants.T_COVID19))
							i.putExtra(Constants.ACCION, Constants.CODIGO_PROPOSITO_T_COVID);
						else i.putExtra(Constants.ACCION, Constants.CODIGO_PROPOSITO_COVID_CP);
						break;
					case Constants.CODIGO_TIPO_RESP:
						i = new Intent(getApplicationContext(),
								NuevaMuestraRespCovid19Activity.class);
						//i.putExtra(Constants.ACCION, Constants.CODIGO_PROPOSITO_T_COVID);
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
