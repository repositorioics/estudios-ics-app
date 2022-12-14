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
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevaMuestraBHCPaxgeneActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevaMuestraTuboRojoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.MuestraAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.MuestrasDBConstants;

import java.util.ArrayList;
import java.util.List;

public class ListaMuestrasActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddBHCButton;
    private Button mAddRojoCButton;
	private static ParticipanteCohorteFamilia participanteCHF = new ParticipanteCohorteFamilia();
	private ArrayAdapter<Muestra> mMuestraAdapter;
	private List<Muestra> mMuestras = new ArrayList<Muestra>();
    private List<MessageResource> mTiposMuestra = new ArrayList<MessageResource>();
	private EstudiosAdapter estudiosAdapter;
    private Double volumenTotalPermitido = 0D;
    private int anios = 0;
    private int meses = 0;
    private boolean habilitarBhc;
    private boolean habilitarRojo;
    private boolean desdeMenuInfo = false;
    private AlertDialog alertDialog;
    private String accion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add_sample);
		registerForContextMenu(getListView());
		participanteCHF = (ParticipanteCohorteFamilia) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
		accion = getIntent().getStringExtra(Constants.ACCION);
        desdeMenuInfo = getIntent().getBooleanExtra(Constants.MENU_INFO,false);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_samples);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCasaTask().execute();

        String edad[] = participanteCHF.getParticipante().getEdad().split("/");
        if (edad.length > 0) {
            anios = Integer.valueOf(edad[0]);
            meses = Integer.valueOf(edad[1]);
        }

        //BHC y Paxgene (2 a??os a 13 a??os y 14 a??os y mas)
        habilitarBhc = anios >= 2;
        //Rojo (6 meses y menos de 2 a??os y 2 a??os a 13 a??os Y 14 a??os y mas)
        if (anios == 0) habilitarRojo = meses >= 6;
        else habilitarRojo = anios >= 1;

		mAddBHCButton = (Button) findViewById(R.id.add_button1);
		mAddBHCButton.setText(getString(R.string.add) + " " + getString(R.string.new_sample_bhc));
        mAddBHCButton.setEnabled(habilitarBhc);
		mAddBHCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogMuestras(getString(R.string.new_mx_bhc));
            }
        });

        mAddRojoCButton = (Button) findViewById(R.id.add_button2);
        mAddRojoCButton.setText(getString(R.string.add) + " " + getString(R.string.new_sample_rojo));
        mAddRojoCButton.setEnabled(habilitarRojo);
        mAddRojoCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogMuestras(getString(R.string.new_mx_rojo));
            }
        });
        if(accion.matches(Constants.REVIEWING)){
        	mAddBHCButton.setVisibility(View.GONE);
        	mAddRojoCButton.setVisibility(View.GONE);
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
		//muestra = (Muestra)this.getListAdapter().getItem(position);
		//listView.showContextMenuForChild(view);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

	}


	@Override
	public void onBackPressed (){
		Bundle arguments = new Bundle();
		Intent i;
		if(!accion.matches(Constants.REVIEWING) && !desdeMenuInfo){
			if (participanteCHF !=null) arguments.putSerializable(Constants.PARTICIPANTE , participanteCHF);
			i = new Intent(getApplicationContext(),
					MenuParticipanteActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtras(arguments);
			startActivity(i);
		}
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

    private boolean getMuestraAnual(){
        for (Muestra muestra : mMuestras) {
            if (muestra.getCodigo().contains("MA2017")){
                return true;
            }
        }
        return false;
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
        String tipoTubo = "0";
        String labelVolumenPermitido = "";
        boolean validarVolumenExistente = true;
        if (opcion.equalsIgnoreCase(getString(R.string.new_mx_bhc)) && !participanteCHF.getParticipante().getEdad().equalsIgnoreCase("ND")) {
            if (anios >= 14){
                labelMuestra = getString(R.string.volumenPaxgene14);
                labelVolumenPermitido = getString(R.string.bhcMlPermitido);
            }
            else {
                //BHC y Paxgene (2 a??os a 13 a??os)
                if (anios >= 2 && anios <= 13) {
                    labelMuestra = getString(R.string.volumenPaxgene);
                    labelVolumenPermitido = getString(R.string.bhcMlPermitido2a13);
                }
            }
            volumenTotalPermitido = 2D;

            tipoTubo = "2";
        }

        if (opcion.equalsIgnoreCase(getString(R.string.new_mx_rojo)) && !participanteCHF.getParticipante().getEdad().equalsIgnoreCase("ND")) {
            if (getMuestraAnual()){
                validarVolumenExistente = false;
                labelVolumenPermitido = getString(R.string.existeMA2017);
            }else {

                boolean visible3ml = false;
                boolean visible6ml = false;
                boolean visible12ml = false;

                //Rojo (2 a??os a 13 a??os)
                if (anios >= 2 && anios <= 13) {
                    visible6ml = true;
                } else {
                    visible6ml = false;
                }
                //Rojo (6 meses y menos de 2 a??os)
                if (!visible6ml) {
                    if (anios == 1) visible3ml = true;
                    else if (anios == 0) {
                        if (meses >= 6) visible3ml = true;
                    } else visible3ml = false;
                }
                if (anios >= 14) {
                    visible12ml = true;
                }

                if (visible3ml) {
                    labelMuestra = getString(R.string.rojo3ml);
                    labelVolumenPermitido = getString(R.string.rojo3mlPermitido);
                    volumenTotalPermitido = 3D;
                }
                if (visible6ml) {
                    labelMuestra = getString(R.string.rojo6ml);
                    labelVolumenPermitido = getString(R.string.rojo6mlPermitido);
                    volumenTotalPermitido = 6D;
                }
                if (visible12ml) {
                    labelMuestra = getString(R.string.rojo12ml);
                    labelVolumenPermitido = getString(R.string.rojo12mlPermitido);
                    volumenTotalPermitido = 12D;
                }
                tipoTubo = "1";
            }
        }

        Double volumenExistente = getVolumenExistente(tipoTubo);
        if (validarVolumenExistente && volumenTotalPermitido > volumenExistente) {
            volumenTotalPermitido = volumenTotalPermitido - volumenExistente;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(this.getString(R.string.remember));
            builder.setMessage(labelMuestra + "\n" + getString(R.string.code) + ": " + participanteCHF.getParticipante().getCodigo() + " - " + participanteCHF.getParticipante().getNombre1() + " " + participanteCHF.getParticipante().getApellido1());
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
            builder.setMessage(labelVolumenPermitido + "\n" + getString(R.string.code) + ": " + participanteCHF.getParticipante().getCodigo() + " - " + participanteCHF.getParticipante().getNombre1() + " " + participanteCHF.getParticipante().getApellido1());
            builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
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
            String tipoMx = values[0];
			try {
                Bundle arguments = new Bundle();
                Intent i;

                if (participanteCHF!=null) arguments.putSerializable(Constants.PARTICIPANTE , participanteCHF);
                arguments.putSerializable(Constants.VOLUMEN , volumenTotalPermitido);

                if (tipoMx.equalsIgnoreCase(getString(R.string.new_mx_bhc))) {
                    i = new Intent(getApplicationContext(),
                            NuevaMuestraBHCPaxgeneActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtras(arguments);
                    i.putExtra(Constants.ACCION, Constants.CODIGO_PROPOSITO_MA);
                    i.putExtra(Constants.MENU_INFO, desdeMenuInfo);
                    startActivity(i);
                }else if (tipoMx.equalsIgnoreCase(getString(R.string.new_mx_rojo))) {

                    i = new Intent(getApplicationContext(),
                            NuevaMuestraTuboRojoActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra(Constants.ACCION, Constants.CODIGO_PROPOSITO_MA);
                    i.putExtra(Constants.MENU_INFO, desdeMenuInfo);
                    i.putExtras(arguments);
                    startActivity(i);
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
				mMuestras = estudiosAdapter.getMuestras(MuestrasDBConstants.participante + " = " + participanteCHF.getParticipante().getCodigo() +" and " + MainDBConstants.pasive + " ='0'", MuestrasDBConstants.tipoMuestra);
                mTiposMuestra = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='CHF_CAT_TIP_TUBO_MX'" + " or " + CatalogosDBConstants.catRoot + "='CHF_CAT_RAZON_NO_MX'", null);
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}finally {
                estudiosAdapter.close();
            }
            return "exito";
		}

		protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            String edadFormateada = "";
            if (!participanteCHF.getParticipante().getEdad().equalsIgnoreCase("ND")) {
                String edad[] = participanteCHF.getParticipante().getEdad().split("/");
                if (edad.length > 0) {
                    edadFormateada = edad[0] + " a??os " + edad[1] + " meses " + edad[2] + " dias";
                }
            }
            textView.setText("");
            textView.setTextColor(Color.BLACK);
            textView.setText(getString(R.string.main_1) + "\n" + getString(R.string.muestras) + "\n" + getString(R.string.code) + " " + getString(R.string.participant) + ": " + participanteCHF.getParticipante().getCodigo()
                    + "\n" + getString(R.string.participant)+ ": "+ participanteCHF.getParticipante().getNombreCompleto() +"\n"
                    + getString(R.string.edad) + ": " + edadFormateada);
            mMuestraAdapter = new MuestraAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item, mMuestras, mTiposMuestra);
            setListAdapter(mMuestraAdapter);
            dismissProgressDialog();
		}
	}
}
