package ni.org.ics.estudios.appmovil.cohortefamilia.activities;

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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarEncuestaDatosPartoBBActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarEncuestaLactanciaMatActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarEncuestaParticipanteActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarEncuestaPesoTallaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.*;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.MenuParticipanteAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaDatosPartoBB;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaLactanciaMaterna;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaParticipante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaPesoTalla;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaParticipanteSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.seroprevalencia.activities.NuevaEncuestaParticipanteSAActivity;
import ni.org.ics.estudios.appmovil.seroprevalencia.activities.editdata.EditarEncuestaParticipanteSAActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;
import ni.org.ics.estudios.appmovil.utils.SeroprevalenciaDBConstants;


public class MenuParticipanteActivity extends AbstractAsyncActivity {

    private GridView gridView;
    private TextView textView;
    private String[] menu_participante;
    private static ParticipanteCohorteFamilia participanteCHF = new ParticipanteCohorteFamilia();
    //MA2020 private static ParticipanteSeroprevalencia participanteSA = new ParticipanteSeroprevalencia();
    private EstudiosAdapter estudiosAdapter;
    boolean existeencuestaParticip = false;
    boolean existeencuestaParto = false;
    boolean existeencuestaPeso = false;
    boolean existeencuestaLact = false;
    boolean existeencuestaParticipSA = false;
    boolean habilitarEncuestaParticipSA = false;

    private final int OPCION_ENCUESTA_PARTICIPANTE = 0;
    private final int OPCION_ENCUESTA_DATOSPARTO = 1;
    private final int OPCION_ENCUESTA_PESOTALLA = 2;
    private final int OPCION_ENCUESTA_LACTANCIA = 3;
    private final int OPCION_ENCUESTA_MUESTRAS = 4;
    private final int OPCION_ENCUESTA_PARTICIPANTESA = 5;
    private final int OPCION_IR_CASA = 6;

    private AlertDialog alertDialog;

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
        menu_participante = getResources().getStringArray(R.array.menu_participante);
        participanteCHF = (ParticipanteCohorteFamilia) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
        new FetchDataCasaTask().execute();


        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String tituloEncuesta = "";
                boolean existeEncuesta = false;
                switch (position){
                    case OPCION_ENCUESTA_PARTICIPANTE:
                        tituloEncuesta = getString(R.string.new_participant_survey);
                        existeEncuesta = existeencuestaParticip;
                        break;
                    case OPCION_ENCUESTA_DATOSPARTO:
                        tituloEncuesta = getString(R.string.new_birthdata_survey);
                        existeEncuesta = existeencuestaParto;
                        break;
                    case OPCION_ENCUESTA_PESOTALLA:
                        tituloEncuesta = getString(R.string.new_Weighheight_survey);
                        existeEncuesta = existeencuestaPeso;
                        break;
                    case OPCION_ENCUESTA_LACTANCIA:
                        tituloEncuesta = getString(R.string.new_breastfeed_survey);
                        existeEncuesta = existeencuestaLact;
                        break;
                    case OPCION_ENCUESTA_MUESTRAS:
                        crearFomulario(position); break;
                    /*case OPCION_IR_CASA:
                        crearFomulario(position); break;*/
                    //MA2020
                    /*case OPCION_ENCUESTA_PARTICIPANTESA:
                        tituloEncuesta = getString(R.string.new_participant_sa_survey);
                        existeEncuesta = existeencuestaParticipSA;
                        break;*/
                    default:
                        break;
                }
                if (!tituloEncuesta.isEmpty()){
                    if (!existeEncuesta)
                        createDialog(position, tituloEncuesta);
                    else
                        createEditDialog(position, tituloEncuesta);
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
		CasaCohorteFamilia casaCHF = participanteCHF.getCasaCHF();
		if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
		i = new Intent(getApplicationContext(),
				ListaParticipantesActivity.class);
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

    private void createDialog(final int opcion, final String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getString(R.string.confirm));
        builder.setMessage(String.format(getString(R.string.add_info_participant), mensaje)+"\n"+getString(R.string.code)+": " + participanteCHF.getParticipante().getCodigo() + " - " + participanteCHF.getParticipante().getNombre1() + " " + participanteCHF.getParticipante().getApellido1());
        builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                crearFomulario(opcion);
            }
        });
        builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void createEditDialog(final int opcion, final String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getString(R.string.confirm));
        builder.setMessage(String.format(getString(R.string.edit_info_participant), mensaje)+"\n"+getString(R.string.code)+": " + participanteCHF.getParticipante().getCodigo() + " - " + participanteCHF.getParticipante().getNombre1() + " " + participanteCHF.getParticipante().getApellido1());
        builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                crearFomulario(opcion);
            }
        });
        builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void crearFomulario(int position){
        if (position == OPCION_ENCUESTA_MUESTRAS) {
            Bundle arguments = new Bundle();
            Intent i;
            if (participanteCHF != null) arguments.putSerializable(Constants.PARTICIPANTE, participanteCHF);
            i = new Intent(getApplicationContext(),
                    ListaMuestrasActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtras(arguments);
            i.putExtra(Constants.ACCION, Constants.ENTERING);
            startActivity(i);
        }else {
            new OpenDataEnterActivityTask().execute(String.valueOf(position));
        }
    }
    // ***************************************
    // Private classes
    // ***************************************
    private class OpenDataEnterActivityTask extends AsyncTask<String, Void, String> {
        private int position = 0;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            position = Integer.valueOf(values[0]);
            Bundle arguments = new Bundle();
            Intent i;
            try {
                estudiosAdapter.open();
                String filtro = EncuestasDBConstants.participante + "=" + participanteCHF.getParticipante().getCodigo();
                switch (position){
                    case OPCION_ENCUESTA_PARTICIPANTE:
                        if (!existeencuestaParticip) {
                            if (participanteCHF != null)
                                arguments.putSerializable(Constants.PARTICIPANTE, participanteCHF);
                            i = new Intent(getApplicationContext(),
                                    NuevaEncuestaParticipanteActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                        }else{
                            EncuestaParticipante encuesta = estudiosAdapter.getEncuestasParticipante(filtro, EncuestasDBConstants.participante);
                            if (encuesta!=null)
                                arguments.putSerializable(Constants.ENCUESTA, encuesta);
                            i = new Intent(getApplicationContext(),
                                    EditarEncuestaParticipanteActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                        }
                        break;
                    case OPCION_ENCUESTA_DATOSPARTO:
                        if (!existeencuestaParto) {
                            if (participanteCHF != null)
                                arguments.putSerializable(Constants.PARTICIPANTE, participanteCHF);
                            i = new Intent(getApplicationContext(),
                                    NuevaEncuestaDatosPartoBBActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                        }else{
                            EncuestaDatosPartoBB encuestaDatosPartoBB = estudiosAdapter.getEncuestasDatosPartoBB(filtro, EncuestasDBConstants.participante);
                            if (encuestaDatosPartoBB!=null)
                                arguments.putSerializable(Constants.ENCUESTA, encuestaDatosPartoBB);
                            i = new Intent(getApplicationContext(),
                                    EditarEncuestaDatosPartoBBActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                        }
                        break;
                    case OPCION_ENCUESTA_PESOTALLA:
                        if (!existeencuestaPeso) {
                            if (participanteCHF != null)
                                arguments.putSerializable(Constants.PARTICIPANTE, participanteCHF);
                            i = new Intent(getApplicationContext(),
                                    NuevaEncuestaPesoTallaActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                        }else{
                            EncuestaPesoTalla encuestaPesoTalla = estudiosAdapter.getEncuestasPesoTalla(filtro, EncuestasDBConstants.participante);
                            if (encuestaPesoTalla!=null)
                                arguments.putSerializable(Constants.ENCUESTA, encuestaPesoTalla);
                            i = new Intent(getApplicationContext(),
                                    EditarEncuestaPesoTallaActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                        }
                        break;
                    case OPCION_ENCUESTA_LACTANCIA:
                        if (!existeencuestaLact) {
                            if (participanteCHF != null)
                                arguments.putSerializable(Constants.PARTICIPANTE, participanteCHF);
                            i = new Intent(getApplicationContext(),
                                    NuevaEncuestaLactanciaMatActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                        }else{
                            EncuestaLactanciaMaterna encuestaLactanciaMaterna = estudiosAdapter.getEncuestasLactanciaMaterna(filtro, EncuestasDBConstants.participante);
                            if (encuestaLactanciaMaterna!=null)
                                arguments.putSerializable(Constants.ENCUESTA, encuestaLactanciaMaterna);
                            i = new Intent(getApplicationContext(),
                                    EditarEncuestaLactanciaMatActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                        }
                        break;
                    /*case OPCION_ENCUESTA_MUESTRAS:
                        if (participanteCHF!=null) arguments.putSerializable(Constants.PARTICIPANTE , participanteCHF);
                        i = new Intent(getApplicationContext(),
                                ListaMuestrasActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        break;*/
                    //MA2020
                    /*case OPCION_ENCUESTA_PARTICIPANTESA:
                        if (!existeencuestaParticipSA) {
                            if (participanteCHF != null)
                                arguments.putSerializable(Constants.PARTICIPANTE, participanteCHF);
                            if (participanteSA != null)
                                arguments.putSerializable(Constants.PARTICIPANTE_SA, participanteSA);
                            i = new Intent(getApplicationContext(),
                                    NuevaEncuestaParticipanteSAActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                        }else{
                            arguments.putSerializable(Constants.PARTICIPANTE, participanteCHF);
                            EncuestaParticipanteSA encuestaParticipanteSA = estudiosAdapter.getEncuestaParticipanteSA(SeroprevalenciaDBConstants.participante + "='" + participanteSA.getParticipante().getCodigo() + "'", null);
                            if (encuestaParticipanteSA != null)
                                arguments.putSerializable(Constants.ENCUESTA, encuestaParticipanteSA);
                            i = new Intent(getApplicationContext(),
                                    EditarEncuestaParticipanteSAActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                        }
                        break;*/
                    /*case OPCION_IR_CASA:
                        arguments.putSerializable(Constants.CASA, participanteCHF.getCasaCHF());
                        i = new Intent(getApplicationContext(),
                                MenuCasaActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);*/
                    default:
                        break;

                }

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
                String filtro = EncuestasDBConstants.participante + "=" + participanteCHF.getParticipante().getCodigo();

                EncuestaParticipante encuesta = estudiosAdapter.getEncuestasParticipante(filtro, EncuestasDBConstants.participante);
                if (encuesta!=null) {
                    existeencuestaParticip = true;
                }

                EncuestaDatosPartoBB encuestaDatosPartoBB = estudiosAdapter.getEncuestasDatosPartoBB(filtro, EncuestasDBConstants.participante);
                if (encuestaDatosPartoBB!=null) {
                    existeencuestaParto = true;
                }

                EncuestaPesoTalla encuestaPesoTalla = estudiosAdapter.getEncuestasPesoTalla(filtro, EncuestasDBConstants.participante);
                if (encuestaPesoTalla!=null) {
                    existeencuestaPeso = true;
                }

                EncuestaLactanciaMaterna encuestaLactanciaMaterna = estudiosAdapter.getEncuestasLactanciaMaterna(filtro, EncuestasDBConstants.participante);
                if (encuestaLactanciaMaterna!=null) {
                    existeencuestaLact = true;
                }
//MA2020
                /*participanteSA = estudiosAdapter.getParticipanteSeroprevalencia(SeroprevalenciaDBConstants.participante + " = " + participanteCHF.getParticipante().getCodigo(), null);
                if (participanteSA!=null) {
                    habilitarEncuestaParticipSA = true;
                    EncuestaParticipanteSA encuestaParticipanteSA = estudiosAdapter.getEncuestaParticipanteSA(SeroprevalenciaDBConstants.participante + "='" + participanteSA.getParticipante().getCodigo() + "'", null);
                    if (encuestaParticipanteSA != null) {
                        existeencuestaParticipSA = true;
                    }
                }*/

                estudiosAdapter.close();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            String edadFormateada = "";
            if (!participanteCHF.getParticipante().getEdad().equalsIgnoreCase("ND")) {
                String edad[] = participanteCHF.getParticipante().getEdad().split("/");
                if (edad.length > 0) {
                    edadFormateada = edad[0] + " años " + edad[1] + " meses " + edad[2] + " dias";
                }
            }
            textView.setText("");
            textView.setTextColor(Color.BLACK);
            textView.setText(getString(R.string.main_1) + "\n" + getString(R.string.code) + " " + getString(R.string.casa) + ": " + participanteCHF.getCasaCHF().getCodigoCHF()+ "\n"
                    + getString(R.string.code)+ " "+ getString(R.string.participant)+ ": "+participanteCHF.getParticipante().getCodigo()+ "\n"
                    + getString(R.string.participant)+ ": "+ participanteCHF.getParticipante().getNombreCompleto() +"\n"
                    + getString(R.string.edad) + ": " + edadFormateada + " - " + getString(R.string.sexo) + ": " +participanteCHF.getParticipante().getSexo());

            gridView.setAdapter(new MenuParticipanteAdapter(getApplicationContext(), R.layout.menu_item_2, menu_participante, participanteCHF, existeencuestaParticip, existeencuestaParto, existeencuestaPeso, existeencuestaLact,
                    existeencuestaParticipSA, habilitarEncuestaParticipSA));
            dismissProgressDialog();
        }
    }
}

