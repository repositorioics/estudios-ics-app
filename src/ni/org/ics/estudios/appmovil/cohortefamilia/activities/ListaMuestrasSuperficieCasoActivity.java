package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.*;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.MuestraSuperficieAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.MuestraSuperficie;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCaso;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.MuestrasDBConstants;

import java.util.ArrayList;
import java.util.List;

public class ListaMuestrasSuperficieCasoActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mSurfaceButton;
    private Button mHandButton;
	private Button mButton;
	//Viene de la actividad principal
	private static VisitaSeguimientoCaso visitaCaso = new VisitaSeguimientoCaso();
	//Tipo de lista
    private static ParticipanteCohorteFamilia participantechf = new ParticipanteCohorteFamilia();
    //Adaptador del objeto de la lista
	private ArrayAdapter<MuestraSuperficie> mMuestraAdapter;
	//Lista de objetos 
	private List<MuestraSuperficie> mMuestras = new ArrayList<MuestraSuperficie>();
    private List<MuestraSuperficie> mMuestrasMano = new ArrayList<MuestraSuperficie>();
	private List<MessageResource> mTiposMuestra = new ArrayList<MessageResource>();
    private boolean pedirAsentimientoCasa = false;
    private boolean pedirConsentimiento = false;
    private AlertDialog alertDialog;

    private EstudiosAdapter estudiosAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add_casos);
		//Obtener objeto que viene del menú
        visitaCaso = (VisitaSeguimientoCaso) getIntent().getExtras().getSerializable(Constants.VISITA);
        if (visitaCaso!=null) {
            participantechf = visitaCaso.getCodigoParticipanteCaso().getParticipante();
        }
        textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_samples_seg);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		//Obtener objetos para llenar la lista
		new FetchDataMuestrasCasosTask().execute();
		
		mButton = (Button) findViewById(R.id.add_part_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.datos_casa_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.add_visit_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.new_sint_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.new_cont_button);
		mButton.setVisibility(View.GONE);
		
		mButton = (Button) findViewById(R.id.fail_visit_button);
		mButton.setVisibility(View.GONE);

        mButton = (Button) findViewById(R.id.final_visit_button);
        mButton.setVisibility(View.GONE);

        mButton = (Button) findViewById(R.id.new_bhc_button);
        mButton.setVisibility(View.GONE);

        mButton = (Button) findViewById(R.id.new_rojo_button);
        mButton.setVisibility(View.GONE);

        mButton = (Button) findViewById(R.id.new_pbmc_button);
        mButton.setVisibility(View.GONE);

        mButton = (Button) findViewById(R.id.new_resp_button);
        mButton.setVisibility(View.GONE);

        mButton = (Button) findViewById(R.id.view_samp_button);
        mButton.setVisibility(View.GONE);

        mButton = (Button) findViewById(R.id.sensors_button);
        mButton.setVisibility(View.GONE);

		mSurfaceButton = (Button) findViewById(R.id.new_surface_button);

        mSurfaceButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
                if (pedirAsentimientoCasa){
                    createDialog(1);
                }else {
                    new OpenDataEnterActivityTask().execute(Constants.MX_SUPERFICIE_1);
                }
			}
		});

        mHandButton = (Button) findViewById(R.id.new_hand_button);

        mHandButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                    if (pedirConsentimiento) {
                        createDialog(1);
                    } else {
                        if (visitaCaso.getCodigoParticipanteCaso().getEnfermo().equalsIgnoreCase("N"))
                            createDialog(2);
                        else
                            new OpenDataEnterActivityTask().execute(Constants.MX_SUPERFICIE_2);
                    }
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
                MenuVisitaSeguimientoCasoActivity.class);
        if (visitaCaso!=null) {
            arguments.putSerializable(Constants.VISITA, visitaCaso);
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

    private void createDialog(int tipo){
        if (tipo==1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(this.getString(R.string.remember));
            String message = "";
            if (pedirConsentimiento && pedirAsentimientoCasa) message = this.getString(R.string.missingMxSuper3);
            else if (pedirConsentimiento) message = this.getString(R.string.missingMxSuper1);
            else if (pedirAsentimientoCasa) message = this.getString(R.string.missingMxSuper2);
            builder.setMessage(message);
            builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    crearFomulario();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        }
        if (tipo==2){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(this.getString(R.string.remember));
            String message = this.getString(R.string.positiveMxHands);
            builder.setMessage(message);
            builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void crearFomulario(){
        new OpenTamizajeActivityTask().execute();
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
				mMuestras = estudiosAdapter.getMuestrasSuperficie(
                        MuestrasDBConstants.caso + " = '" + visitaCaso.getCodigoParticipanteCaso().getCodigoCaso().getCodigoCaso() + "' and " + MainDBConstants.pasive + " ='0' ",
                        MuestrasDBConstants.tipoMuestra);
                mMuestrasMano = estudiosAdapter.getMuestrasSuperficie(
                        MuestrasDBConstants.caso + " = '" + visitaCaso.getCodigoParticipanteCaso().getCodigoCaso().getCodigoCaso() + "' and " + MainDBConstants.pasive + " ='0' and "+ MuestrasDBConstants.tipoMuestra + " = '13' ",
                        MuestrasDBConstants.tipoMuestra);

                mTiposMuestra = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_TIPO_MX_SUP'", null);
                //1:asent mx superficie, 2:consent manos, 3:Ambos, 0 o null:No aplica
                ParticipanteProcesos procesos = visitaCaso.getCodigoParticipanteCaso().getParticipante().getParticipante().getProcesos();
                if (procesos.getMxSuperficie()!=null && (procesos.getMxSuperficie().equalsIgnoreCase("1") || procesos.getMxSuperficie().equalsIgnoreCase("3"))){
                    pedirAsentimientoCasa = true;
                }
                if (procesos.getMxSuperficie()!=null && (procesos.getMxSuperficie().equalsIgnoreCase("2") || procesos.getMxSuperficie().equalsIgnoreCase("3"))){
                    pedirConsentimiento = true;
                }
                if (visitaCaso.getCodigoParticipanteCaso().getEnfermo().equalsIgnoreCase("N")) pedirConsentimiento = false;
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
            textView.setText(getString(R.string.main_1) + "\n" + getString(R.string.follow_up_list_samp) + "\n" +
                    getString(R.string.code) + ": " + participantechf.getParticipante().getCodigo() + " - " + getString(R.string.visit) + ": " + visitaCaso.getVisita());
            if (mMuestras.size()>1) {
                for(MuestraSuperficie m : mMuestras){
                    if (!m.getTipoMuestra().equalsIgnoreCase("13")){
                        mSurfaceButton.setVisibility(View.GONE);
                        break;
                    }
                }
                for(MuestraSuperficie m : mMuestras){
                    if (m.getTipoMuestra().equalsIgnoreCase("13")){
                        mHandButton.setVisibility(View.GONE);
                        break;
                    }
                }
            }

            mMuestraAdapter = new MuestraSuperficieAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item, mMuestras, mTiposMuestra);
            setListAdapter(mMuestraAdapter);
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
            String opcion = values[0];
            try {
                Bundle arguments = new Bundle();
                if (visitaCaso != null) arguments.putSerializable(Constants.VISITA, visitaCaso);
                Intent i = new Intent(getApplicationContext(),
                        NuevaMuestrasSuperficieActivity.class);
                i.putExtras(arguments);
                i.putExtra(Constants.MX_SUPERFICIE, Integer.valueOf(opcion));
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

    // ***************************************
    // Private classes
    // ***************************************
    private class OpenTamizajeActivityTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                Bundle arguments = new Bundle();
                Intent i;
                arguments.putSerializable(Constants.VISITA, visitaCaso);
                i = new Intent(getApplicationContext(),
                        NuevoTamizajeMxSuperficieActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtras(arguments);
                i.putExtra(Constants.MX_SUPERFICIE_CON, pedirConsentimiento);
                i.putExtra(Constants.MX_SUPERFICIE_ASEN, pedirAsentimientoCasa);
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
