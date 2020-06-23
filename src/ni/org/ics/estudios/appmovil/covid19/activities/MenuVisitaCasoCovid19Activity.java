package ni.org.ics.estudios.appmovil.covid19.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
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
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.covid19.activities.enterdata.NuevoDAislamientoVisitaCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaMuestrasParticipanteCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaSintomasVisitaCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaVisitasParticipanteCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.adapters.MenuVisitaCasoCovid19Adapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.ObsequioGeneral;
import ni.org.ics.estudios.appmovil.domain.covid19.DatosAislamientoVisitaCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaSeguimientoCasoCovid19;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class MenuVisitaCasoCovid19Activity extends AbstractAsyncActivity {

    private GridView gridView;
    private TextView textView;
    private String[] menu_visita;
    private VisitaSeguimientoCasoCovid19 visitaCaso = new VisitaSeguimientoCasoCovid19();
    private EstudiosAdapter estudiosAdapter;
    private ObsequioGeneral obsequioGeneral = new ObsequioGeneral();
    private List<DatosAislamientoVisitaCasoCovid19> mDatosAislamiento = new ArrayList<DatosAislamientoVisitaCasoCovid19>();
    //private List<MuestraSuperficie> mMuestras = new ArrayList<MuestraSuperficie>();
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
        menu_visita = getResources().getStringArray(R.array.menu_visitas_covid19);
        visitaCaso = (VisitaSeguimientoCasoCovid19) getIntent().getExtras().getSerializable(Constants.VISITA);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
        new FetchDataVisitaTask().execute();

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
            	Intent i;
            	Bundle arguments = new Bundle();
                switch (position) {
                    case 0:
                        arguments.putSerializable(Constants.VISITA, visitaCaso);
                        i = new Intent(getApplicationContext(),
                                ListaMuestrasParticipanteCasoCovid19Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        finish();
                        break;
                    case 1:
                        arguments.putSerializable(Constants.VISITA, visitaCaso);
                        i = new Intent(getApplicationContext(),
                                ListaSintomasVisitaCasoCovid19Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        finish();
                        break;
                    case 2:
                        if (mDatosAislamiento.size()>0){
                            Toast.makeText(getApplicationContext(), R.string.aislamientoExist ,Toast.LENGTH_LONG).show();
                        }else {
                            arguments.putSerializable(Constants.VISITA, visitaCaso);
                            i = new Intent(getApplicationContext(),
                                    NuevoDAislamientoVisitaCasoCovid19Activity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                            finish();
                        }
                        break;
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
	public void onBackPressed (){
		Bundle arguments = new Bundle();
		Intent i;
		ParticipanteCasoCovid19 partCaso = visitaCaso.getCodigoParticipanteCaso();
		if (partCaso!=null) arguments.putSerializable(Constants.PARTICIPANTE , partCaso);
		i = new Intent(getApplicationContext(),
				ListaVisitasParticipanteCasoCovid19Activity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtras(arguments);
		startActivity(i);
		finish();
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

    private class FetchDataVisitaTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                estudiosAdapter.open();
                obsequioGeneral = estudiosAdapter.getObsequioGeneral(MainDBConstants.seguimiento +" = '"+visitaCaso.getCodigoParticipanteCaso().getCodigoCaso().getCodigoCaso()+
                        "' and "+MainDBConstants.numVisitaSeguimiento+" != 'F' and "+MainDBConstants.obsequioSN+" = 1", null);
                /*mMuestras = estudiosAdapter.getMuestrasSuperficie(
                        MuestrasDBConstants.caso + " = '" + visitaCaso.getCodigoParticipanteCaso().getCodigoCaso().getCodigoCaso() + "' and " + MainDBConstants.pasive + " ='0' ",
                        MuestrasDBConstants.tipoMuestra);*/
                String filtro = Covid19DBConstants.codigoCasoVisita + "='" + visitaCaso.getCodigoCasoVisita()+"'";
                mDatosAislamiento = estudiosAdapter.getDatosAislamientoVisitasCasosCovid19(filtro, null);

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
            textView.setText(getString(R.string.main_4)+ "\n" + visitaCaso.getCodigoParticipanteCaso().getParticipante().getNombreCompleto()
            		+ "\n" + getString(R.string.visit) +": " + visitaCaso.getVisita() +" - "+ formatter.format(visitaCaso.getFechaVisita()));

            gridView.setAdapter(new MenuVisitaCasoCovid19Adapter(getApplicationContext(), R.layout.menu_item_2, menu_visita, mDatosAislamiento.size()>0));
            dismissProgressDialog();
        }
    }

}

