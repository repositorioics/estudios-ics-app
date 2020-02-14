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
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarPersonaCamaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevaPersonaCamaActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.PersonaCamaAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cama;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cuarto;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.PersonaCama;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.CasaCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.ArrayList;
import java.util.List;

public class ListaPersonasCamasCuartosCasoActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddButton;
	//private static CasaCohorteFamiliaCaso caso = new CasaCohorteFamiliaCaso();
	private PersonaCama pc = new PersonaCama();
	private ArrayAdapter<PersonaCama> mPersonaCamaAdapter;
	private List<PersonaCama> mPersonasCama = new ArrayList<PersonaCama>();
	private EstudiosAdapter estudiosAdapter;
	private AlertDialog alertDialog;
    private static String codigoCuarto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add);
		
		registerForContextMenu(getListView());

        codigoCuarto = getIntent().getStringExtra(Constants.CUARTO);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_share);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCamaTask().execute();
		
		mAddButton = (Button) findViewById(R.id.add_button);
        mAddButton.setVisibility(View.GONE);
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
		pc = (PersonaCama)this.getListAdapter().getItem(position);
		createDialog();
	}

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(this.getString(R.string.confirm));
        builder.setMessage(getString(R.string.select)+"\n"+getString(R.string.codigoHabitacion)+": " + pc.getCama().getCuarto().getCodigoHabitacion());
        builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                seleccionarCuarto();
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

    private void seleccionarCuarto(){
        Intent intent1 = new Intent();
        intent1.putExtra("CODE_RESULT", pc.getCama().getCuarto().getCodigo());
        setResult(RESULT_OK, intent1);
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

	private class FetchDataCamaTask extends AsyncTask<String, Void, String> {
        String casaChf;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
                /*caso = estudiosAdapter.getCasaCohorteFamiliaCaso(CasosDBConstants.codigoCaso + "='" + codigoCaso + "'", null);
                if (caso!=null) {
                    List<Cuarto> mCuartos = estudiosAdapter.getCuartos(MainDBConstants.casa + " = '" + caso.getCasa().getCodigoCHF() + "' and " + MainDBConstants.pasive + " ='0'", MainDBConstants.codigoHabitacion);
                    for (Cuarto cuarto : mCuartos) {*/
                        List<Cama> mCamas = estudiosAdapter.getCamas(MainDBConstants.habitacion + " = '" + codigoCuarto + "'", MainDBConstants.codigoCama);
                        for (Cama cama : mCamas) {
                            casaChf = cama.getCuarto().getCasa().getCodigoCHF();
                            mPersonasCama.addAll(estudiosAdapter.getPersonasCama(MainDBConstants.cama + " = '" + cama.getCodigoCama() + "'", MainDBConstants.codigoPersona));
                        }
                    /*}
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
			textView.setText("");
			textView.setTextColor(Color.BLACK);
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.pbeds)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+ casaChf);
			mPersonaCamaAdapter = new PersonaCamaAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mPersonasCama);
			setListAdapter(mPersonaCamaAdapter);
			dismissProgressDialog();
		}

	}

}
