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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.*;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.MenuParticipanteAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaDatosPartoBB;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaLactanciaMaterna;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaParticipante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaPesoTalla;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.EncuestasDBConstants;


public class MenuParticipanteActivity extends AbstractAsyncActivity {

    private GridView gridView;
    private TextView textView;
    private String[] menu_participante;
    private static ParticipanteCohorteFamilia participanteCHF = new ParticipanteCohorteFamilia();
    private EstudiosAdapter estudiosAdapter;
    private boolean habilitarBHC = false;
    private boolean habilitarRojo = false;
    private boolean habilitarPaxG = false;

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
        new FetchDataCasaTask().execute(participanteCHF.getParticipanteCHF());


        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                boolean existeencuesta = false;
                String tituloEncuesta = "";
                String labelMuestra = "";
                //estudiosAdapter.open();
                switch (position){
                    case 0:
                        //EncuestaParticipante encuesta = estudiosAdapter.getEncuestasParticipante(EncuestasDBConstants.participante_chf + "='" + participanteCHF.getParticipanteCHF() + "'", EncuestasDBConstants.participante_chf);
                        //if (encuesta!=null) {
                            //existeencuesta = true;
                        //}
                        tituloEncuesta = getString(R.string.new_participant_survey);
                        break;
                    case 1:
                        //EncuestaDatosPartoBB encuestaDatosPartoBB = estudiosAdapter.getEncuestasDatosPartoBB(EncuestasDBConstants.participante_chf + "='" + participanteCHF.getParticipanteCHF() + "'", EncuestasDBConstants.participante_chf);
                        //if (encuestaDatosPartoBB!=null) {
                            //existeencuesta = true;
                        //}
                        tituloEncuesta = getString(R.string.new_birthdata_survey);
                        break;
                    case 2:
                        //EncuestaPesoTalla encuestaPesoTalla = estudiosAdapter.getEncuestasPesoTalla(EncuestasDBConstants.participante_chf + "='" + participanteCHF.getParticipanteCHF() + "'", EncuestasDBConstants.participante_chf);
                        //if (encuestaPesoTalla!=null) {
                            //existeencuesta = true;
                        //}
                        tituloEncuesta = getString(R.string.new_Weighheight_survey);
                        break;
                    case 3:
                        //EncuestaLactanciaMaterna encuestaLactanciaMaterna = estudiosAdapter.getEncuestasLactanciaMaterna(EncuestasDBConstants.participante_chf + "='" + participanteCHF.getParticipanteCHF() + "'", EncuestasDBConstants.participante_chf);
                        //if (encuestaLactanciaMaterna!=null) {
                            //existeencuesta = true;
                        //}
                        tituloEncuesta = getString(R.string.new_breastfeed_survey);
                        break;
                    case 4:
                        if (!participanteCHF.getParticipante().getEdad().equalsIgnoreCase("ND")) {
                            int anios = 0;
                            int meses = 0;
                            int dias = 0;
                            String edad[] = participanteCHF.getParticipante().getEdad().split("/");
                            if (edad.length > 0) {
                                anios = Integer.valueOf(edad[0]);
                                meses = Integer.valueOf(edad[1]);
                                dias = Integer.valueOf(edad[2]);
                                if (anios>=14) labelMuestra = getString(R.string.bhc2ml14);
                                else {
                                    //BHC y Paxgene (2 años a 13 años)
                                    if (anios>=2 && anios<13) {
                                        labelMuestra = getString(R.string.bhc2ml);
                                    }else if (anios == 13 && meses == 0 && dias == 0){
                                        labelMuestra = getString(R.string.bhc2ml);
                                    }
                                }
                            }
                        }
                        tituloEncuesta = getString(R.string.new_sample_bhc);
                        break;
                    case 5:
                        tituloEncuesta = getString(R.string.new_sample_rojo);
                        if (!participanteCHF.getParticipante().getEdad().equalsIgnoreCase("ND")) {
                            int anios = 0;
                            int meses = 0;
                            int dias = 0;
                            boolean visible3ml = false;
                            boolean visible6ml = false;
                            boolean visible12ml = false;
                            String edad[] = participanteCHF.getParticipante().getEdad().split("/");
                            if (edad.length > 0) {
                                anios = Integer.valueOf(edad[0]);
                                meses = Integer.valueOf(edad[1]);
                                dias = Integer.valueOf(edad[2]);
                                //Rojo (2 años a 13 años)
                                if (anios >= 2 && anios < 13) {
                                    visible6ml = true;
                                } else if (anios == 13) {
                                    if (meses > 0)
                                        visible6ml = false;
                                    else {
                                        visible6ml = (dias <= 0);
                                    }
                                } else {
                                    visible6ml = false;
                                }
                                //Rojo (6 meses y menos de 2 años)
                                if (!visible6ml) {
                                    if (anios == 1) visible3ml = true;
                                    else if (anios == 0) {
                                        if (meses >= 6) visible3ml = true;
                                    } else visible3ml = false;
                                }
                                if (anios >= 14){
                                    visible12ml = true;
                                }
                            }
                            if (visible3ml) labelMuestra = getString(R.string.rojo3ml);
                            if (visible6ml) labelMuestra = getString(R.string.rojo6ml);
                            if (visible12ml) labelMuestra = getString(R.string.rojo12ml);
                        }
                        break;
                    case 6:
                        if (!participanteCHF.getParticipante().getEdad().equalsIgnoreCase("ND")) {
                            int anios = 0;
                            int meses = 0;
                            int dias = 0;
                            String edad[] = participanteCHF.getParticipante().getEdad().split("/");
                            if (edad.length > 0) {
                                anios = Integer.valueOf(edad[0]);
                                meses = Integer.valueOf(edad[1]);
                                dias = Integer.valueOf(edad[2]);
                                if (anios>=14) labelMuestra = getString(R.string.volumenPaxgene14);
                                else {
                                    //BHC y Paxgene (2 años a 13 años)
                                    if (anios>=2 && anios<13) {
                                        labelMuestra = getString(R.string.volumenPaxgene);
                                    }else if (anios == 13 && meses == 0 && dias == 0){
                                        labelMuestra = getString(R.string.volumenPaxgene);
                                    }
                                }
                            }
                        }
                        tituloEncuesta = getString(R.string.new_sample_paxgene);
                        break;
                    default:
                        break;
                }
                //estudiosAdapter.close();
                //if (existeencuesta) {
                    //createDialogExiste(tituloEncuesta);
                //}
                //else {
                    if(!labelMuestra.isEmpty())
                        createDialogMuestras(position, labelMuestra);
                    else createDialog(position, tituloEncuesta);
                //}

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

    private void createDialogExiste(final String opcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getString(R.string.notavailable));
        builder.setMessage(String.format(this.getString(R.string.exist_survey_participant), opcion)+"\n"+getString(R.string.code)+": " + participanteCHF.getParticipante().getCodigo() + " - " + participanteCHF.getParticipante().getNombre1() + " " + participanteCHF.getParticipante().getApellido1());
        builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void createDialogMuestras(final int opcion, final String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getString(R.string.remember));
        builder.setMessage(mensaje+"\n"+getString(R.string.code)+": " + participanteCHF.getParticipante().getCodigo() + " - " + participanteCHF.getParticipante().getNombre1() + " " + participanteCHF.getParticipante().getApellido1());
        builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                crearFomulario(opcion);
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
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
    private void crearFomulario(int position){
        new OpenDataEnterActivityTask().execute(String.valueOf(position));
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
                switch (position){
                    case 0:
                        if (participanteCHF!=null) arguments.putSerializable(Constants.PARTICIPANTE , participanteCHF);
                        i = new Intent(getApplicationContext(),
                                NuevaEncuestaParticipanteActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        break;
                    case 1:
                        if (participanteCHF!=null) arguments.putSerializable(Constants.PARTICIPANTE , participanteCHF);
                        i = new Intent(getApplicationContext(),
                                NuevaEncuestaDatosPartoBBActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        break;
                    case 2:
                        if (participanteCHF!=null) arguments.putSerializable(Constants.PARTICIPANTE , participanteCHF);
                        i = new Intent(getApplicationContext(),
                                NuevaEncuestaPesoTallaActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        break;
                    case 3:
                        if (participanteCHF!=null) arguments.putSerializable(Constants.PARTICIPANTE , participanteCHF);
                        i = new Intent(getApplicationContext(),
                                NuevaEncuestaLactanciaMatActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        break;
                    case 4:
                        if (participanteCHF!=null) arguments.putSerializable(Constants.PARTICIPANTE , participanteCHF);
                        i = new Intent(getApplicationContext(),
                                NuevaMuestraBHCActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        break;
                    case 5:
                        if (participanteCHF!=null) arguments.putSerializable(Constants.PARTICIPANTE , participanteCHF);
                        i = new Intent(getApplicationContext(),
                                NuevaMuestraTuboRojoActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        break;
                    case 6:
                        if (participanteCHF!=null) arguments.putSerializable(Constants.PARTICIPANTE , participanteCHF);
                        i = new Intent(getApplicationContext(),
                                NuevaMuestraPaxgeneActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        break;
                    default:
                        break;

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
                //estudiosAdapter.open();
                //mParticipantes = estudiosAdapter.getParticipanteCohorteFamilias(MainDBConstants.casaCHF +" = " + codigoCasaCHF, MainDBConstants.participante);
                //estudiosAdapter.close();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            boolean existeencuestaParticip = false;
            boolean existeencuestaParto = false;
            boolean existeencuestaPeso = false;
            boolean existeencuestaLact = false;
            estudiosAdapter.open();
            EncuestaParticipante encuesta = estudiosAdapter.getEncuestasParticipante(EncuestasDBConstants.participante_chf + "='" + participanteCHF.getParticipanteCHF() + "'", EncuestasDBConstants.participante_chf);
            if (encuesta!=null) {
                existeencuestaParticip = true;
            }

            EncuestaDatosPartoBB encuestaDatosPartoBB = estudiosAdapter.getEncuestasDatosPartoBB(EncuestasDBConstants.participante_chf + "='" + participanteCHF.getParticipanteCHF() + "'", EncuestasDBConstants.participante_chf);
            if (encuestaDatosPartoBB!=null) {
                existeencuestaParto = true;
            }

            EncuestaPesoTalla encuestaPesoTalla = estudiosAdapter.getEncuestasPesoTalla(EncuestasDBConstants.participante_chf + "='" + participanteCHF.getParticipanteCHF() + "'", EncuestasDBConstants.participante_chf);
            if (encuestaPesoTalla!=null) {
                existeencuestaPeso = true;
            }

            EncuestaLactanciaMaterna encuestaLactanciaMaterna = estudiosAdapter.getEncuestasLactanciaMaterna(EncuestasDBConstants.participante_chf + "='" + participanteCHF.getParticipanteCHF() + "'", EncuestasDBConstants.participante_chf);
            if (encuestaLactanciaMaterna!=null) {
                existeencuestaLact = true;
            }
            estudiosAdapter.close();
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

            gridView.setAdapter(new MenuParticipanteAdapter(getApplicationContext(), R.layout.menu_item_2, menu_participante, participanteCHF, existeencuestaParticip, existeencuestaParto, existeencuestaPeso, existeencuestaLact));
            dismissProgressDialog();
        }

    }

}

