package ni.org.ics.estudios.appmovil.cohortefamilia.activities;


import java.util.ArrayList;
import java.util.List;

import android.widget.*;
import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

import ni.org.ics.estudios.appmovil.cohortefamilia.activities.editdata.EditarCuartoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevoCuartoActivity;
import ni.org.ics.estudios.appmovil.cohortefamilia.adapters.CuartoAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cama;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Cuarto;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.PersonaCama;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.CasaCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import ni.org.ics.estudios.appmovil.wizard.model.Page;

public class ListaCuartosActivity extends AbstractAsyncListActivity {
	
	private TextView textView;
	private Drawable img = null;
	private Button mAddButton;
	private static CasaCohorteFamiliaCaso casaCaso = new CasaCohorteFamiliaCaso();
	private static CasaCohorteFamilia casaCHF = new CasaCohorteFamilia();
    private Cuarto cuarto = new Cuarto();
	private ArrayAdapter<Cuarto> mCuartoAdapter;
	private List<Cuarto> mCuartos = new ArrayList<Cuarto>();
	private EstudiosAdapter estudiosAdapter;

    private static final int EDITAR_CUARTO = 1;
	private static final int BORRAR_CUARTO = 2;
	private boolean fromCasos;

	private AlertDialog alertDialog;
    private static String codigoCasaChf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add);
		
		registerForContextMenu(getListView());
		
		
		fromCasos = getIntent().getBooleanExtra(Constants.DESDE_CASOS, false);
		if(fromCasos){
			casaCaso = (CasaCohorteFamiliaCaso) getIntent().getExtras().getSerializable(Constants.CASA);
			casaCHF=casaCaso.getCasa();
		}
		else{
			casaCHF = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASA);
		}
        codigoCasaChf = getIntent().getStringExtra(Constants.CASO);
		textView = (TextView) findViewById(R.id.label);
		img=getResources().getDrawable(R.drawable.ic_menu_share);
		textView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCasaTask().execute(casaCHF!=null?casaCHF.getCodigoCHF():codigoCasaChf);
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.new_room));

		mAddButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				new OpenDataEnterActivityTask().execute();
			}
		});

        if (codigoCasaChf!=null)
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
		cuarto = (Cuarto)this.getListAdapter().getItem(position);
        if (codigoCasaChf==null)
            listView.showContextMenuForChild(view);
        else
        {
            Intent i = new Intent(this.getApplicationContext(),ListaPersonasCamasCuartosCasoActivity.class);
            i.putExtra(Constants.CUARTO, cuarto.getCodigo());
            startActivityForResult(i, 2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent intent) {
        if (requestCode == 2 && intent != null) {
            String codigo = intent.getStringExtra("CODE_RESULT");
            Intent intent1 = new Intent();
            intent1.putExtra("CODE_RESULT", codigo);
            setResult(RESULT_OK, intent1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, intent);

    }

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionscuartos, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {    	
		switch(item.getItemId()) {
            case R.id.MENU_VER_CAMAS:
                Bundle arguments = new Bundle();
                Intent i;
                arguments.putSerializable(Constants.CUARTO , cuarto);
                i = new Intent(getApplicationContext(),
                        ListaCamasActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtras(arguments);
                startActivity(i);
                finish();
                return true;
            case R.id.MENU_EDITAR_CUARTO:
                createDialog(EDITAR_CUARTO);
                return true;
            case R.id.MENU_BORRAR_CUARTO:
                createDialog(BORRAR_CUARTO);
                return true;

		default:
			return super.onContextItemSelected(item);
		}
	}
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case BORRAR_CUARTO:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(getString(R.string.remove_room));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					new UpdateCuartoTask().execute();
				}
			});
			builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Do nothing
					dialog.dismiss();
				}
			});
			break;
            case EDITAR_CUARTO:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.edit_room));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new OpenDataEditActivityTask().execute();
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                break;
		default:
			break;
		}
		alertDialog = builder.create();
		alertDialog.show();
	}
	
	@Override
	public void onBackPressed (){

        if (codigoCasaChf==null) {
            Bundle arguments = new Bundle();
            Intent i;
            if (fromCasos) {
                if (casaCaso != null) arguments.putSerializable(Constants.CASA, casaCaso);
                i = new Intent(getApplicationContext(),
                        MenuCasaCasoActivity.class);
            } else {
                if (casaCHF != null) arguments.putSerializable(Constants.CASA, casaCHF);
                i = new Intent(getApplicationContext(),
                        MenuCasaActivity.class);
            }
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtras(arguments);
            startActivity(i);
            finish();
        }else {
            finish();
        }

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
			try {
				Bundle arguments = new Bundle();
				if(fromCasos){
					if (casaCaso!=null) arguments.putSerializable(Constants.CASA , casaCaso);
				}
				else{
					if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
				}
				Intent i = new Intent(getApplicationContext(),
						NuevoCuartoActivity.class);
				i.putExtras(arguments);
				i.putExtra(Constants.DESDE_CASOS, fromCasos);
		        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
				estudiosAdapter.open();
                if (casaCHF==null)
                    casaCHF = estudiosAdapter.getCasaCohorteFamilia(MainDBConstants.codigoCHF +" = '" + codigoCasaCHF + "'", null);
				mCuartos = estudiosAdapter.getCuartos(MainDBConstants.casa +" = '" + codigoCasaCHF + "' and " + MainDBConstants.pasive + " ='0'", MainDBConstants.codigoHabitacion);
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
			//cambio realizado 15/06/2023
			if (casaCHF != null) {
				textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.rooms)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCHF.getCodigoCHF());
			} else {
				textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.rooms)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+codigoCasaCHF);
			}
			mCuartoAdapter = new CuartoAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mCuartos);
			setListAdapter(mCuartoAdapter);
			dismissProgressDialog();
		}

	}	
	
	private class UpdateCuartoTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				List<Cama> mCamas = estudiosAdapter.getCamas(MainDBConstants.habitacion +" = '" + cuarto.getCodigo() + "'", MainDBConstants.codigoCama);
				for(Cama cama: mCamas){
					cama.setPasive('1');
					cama.setEstado('0');
					List<PersonaCama> mPersonasCama = estudiosAdapter.getPersonasCama(MainDBConstants.cama +" = '" + cama.getCodigoCama() + "'", MainDBConstants.codigoPersona);
					for (PersonaCama pc:mPersonasCama){
						pc.setPasive('1');
						pc.setEstado('0');
						estudiosAdapter.editarPersonaCama(pc);
					}
					estudiosAdapter.editarCama(cama);
				}
				cuarto.setPasive('1');
				cuarto.setEstado('0');
				estudiosAdapter.editarCuarto(cuarto);
				mCuartos = estudiosAdapter.getCuartos(MainDBConstants.casa +" = '" + casaCHF.getCodigoCHF() + "' and " + MainDBConstants.pasive + " ='0'", MainDBConstants.codigoHabitacion);
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
			textView.setText(getString(R.string.main_1) +"\n"+ getString(R.string.rooms)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casaCHF.getCodigoCHF());
			mCuartoAdapter = new CuartoAdapter(getApplication().getApplicationContext(), R.layout.complex_list_item,mCuartos);
			setListAdapter(mCuartoAdapter);
			dismissProgressDialog();
		}

	}

    // ***************************************
    // Private classes
    // ***************************************
    private class OpenDataEditActivityTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                Bundle arguments = new Bundle();
                if (casaCHF!=null) arguments.putSerializable(Constants.CASA , casaCHF);
                if (cuarto!=null) arguments.putSerializable(Constants.CUARTO , cuarto);
                Intent i = new Intent(getApplicationContext(),
                        EditarCuartoActivity.class);
                i.putExtras(arguments);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
