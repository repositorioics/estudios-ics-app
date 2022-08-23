package ni.org.ics.estudios.appmovil.entomologia.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.adapters.CasaAdapter;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata.NuevoTamizajeCasaActivity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.entomologia.constants.EntomologiaBConstants;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogar;
import ni.org.ics.estudios.appmovil.entomologia.helpers.EntomologiaHelper;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import java.util.ArrayList;
import java.util.List;

public class BuscarCasaActivity extends AbstractAsyncListActivity {


	private Spinner mMetodoView;
	private EditText mParametroView;
	private ImageButton mBarcodeButton;
	private ImageButton mFindButton;
	
	public static final int BARCODE_CAPTURE = 2;
	private final int AGREGAR = 0;
	private final int NO_AGREGAR = 1;
	private int opcion;

	private EstudiosAdapter estudiosAdapter;
	private Casa mCasa = new Casa();
	private List<Casa> mCasas = new ArrayList<Casa>();
	private AlertDialog alertDialog;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selec_casa_list);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		mMetodoView = (Spinner) findViewById(R.id.metodo_busqueda);
		List<String> list = new ArrayList<String>();
		list.add(getString(R.string.desc_barcode));
		list.add(getString(R.string.enter)+" "+getString(R.string.casa_id));
		list.add(getString(R.string.enter)+" "+getString(R.string.casa_jefe_familia_nombre));
		list.add(getString(R.string.enter)+" "+getString(R.string.casa_jefe_familia_apellido));
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		mMetodoView.setAdapter(dataAdapter);

		mMetodoView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				opcion = position;
				if (position==0){
					mParametroView.setVisibility(View.GONE);
					mFindButton.setVisibility(View.GONE);
					mBarcodeButton.setVisibility(View.VISIBLE);
				}
				else{
					mParametroView.setVisibility(View.VISIBLE);
					mFindButton.setVisibility(View.VISIBLE);
					mBarcodeButton.setVisibility(View.GONE);
					mParametroView.requestFocus();
					if (position==1){
						mParametroView.setInputType(InputType.TYPE_CLASS_NUMBER);
						mParametroView.setHint(getString(R.string.casa_id));
					}
					else if (position==2){
						mParametroView.setInputType(InputType.TYPE_CLASS_TEXT);
						mParametroView.setHint(getString(R.string.casa_jefe_familia_nombre));
					}
					else if (position==3){
						mParametroView.setInputType(InputType.TYPE_CLASS_TEXT);
						mParametroView.setHint(getString(R.string.casa_jefe_familia_apellido));
					}
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});
		
		mParametroView = (EditText) findViewById(R.id.parametro);
		mParametroView.setVisibility(View.GONE);


		mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
		mFindButton = (ImageButton) findViewById(R.id.find_button);

		mBarcodeButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				Intent i = new Intent("com.google.zxing.client.android.SCAN");
				try {
					startActivityForResult(i, BARCODE_CAPTURE);
				} catch (ActivityNotFoundException e) {
					Toast t = Toast.makeText(getApplicationContext(),
							getString(R.string.error, R.string.barcode_error),
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show();
				}
			}
		});

		mFindButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				mCasas.clear();
				if ((mParametroView.getText().toString()==null) || (mParametroView.getText().toString().matches(""))){
					mParametroView.requestFocus();
					mParametroView.setError(getString(R.string.search_hint));
					return;
				}
				buscarCasa(mParametroView.getText().toString(),opcion);
			}
		});

		mFindButton.setVisibility(View.GONE);

	}

	private void createDialog(final int option) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(option){
			case AGREGAR:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(getString(R.string.confirmar_agregar)+"\n"+getString(R.string.code)+": " + mCasa.getCodigo() + " " + mCasa.getNombre1JefeFamilia() + " " + mCasa.getApellido1JefeFamilia());
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					crearCuestionarioHogar();
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
			case NO_AGREGAR:
				builder.setTitle(this.getString(R.string.validating));
				builder.setMessage(getString(R.string.errorCuestExiste, mCasa.getCodigo()));
				builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
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
	
	private void showToast(String mensaje){
		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(R.layout.toast,
				(ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(mensaje);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		mCasa = (Casa) getListAdapter().getItem(position);
			estudiosAdapter.open();
			List<CuestionarioHogar> cuestionarioCasa = estudiosAdapter.getCuestionariosHogar(EntomologiaBConstants.codigoCasa + " = " + mCasa.getCodigo(), null);
			estudiosAdapter.close();
		if (cuestionarioCasa.size() > 0)
			createDialog(NO_AGREGAR);
		else createDialog(AGREGAR);
	}

    private void crearCuestionarioHogar(){
    	new OpenDataEnterActivityTask().execute();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_ento, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			Intent i = new Intent(getApplicationContext(),
					MenuEntomologiaActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		else if(item.getItemId()==R.id.MENU_BACK){
			Intent i = new Intent(getApplicationContext(),
					MenuEntomologiaActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		else if(item.getItemId()==R.id.MENU_HOME){
			Intent i = new Intent(getApplicationContext(),
					MenuEntomologiaActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		else{
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == BARCODE_CAPTURE && intent != null) {
			String sb = intent.getStringExtra("SCAN_RESULT");
			if (sb != null && sb.length() > 0) {
				try{
					Integer.parseInt(sb);
					buscarCasa(sb,0);
				}
				catch(Exception e){
					showToast(e.getLocalizedMessage());
					return;
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);

	}
	
	public void buscarCasa(String parametro, int opcion){
		String filtro = "";
		switch (opcion) {
		case 0:
			filtro = MainDBConstants.codigo + "=" + parametro;
			break;
		case 1:
			filtro = MainDBConstants.codigo + "=" + parametro;
			break;	
		case 2:
			filtro = MainDBConstants.nombre1JefeFamilia + " like '%" + parametro + "%' or " + MainDBConstants.nombre2JefeFamilia + " like '%" + parametro + "%'";
			break;
		case 3:
			filtro = MainDBConstants.apellido1JefeFamilia + " like '%" + parametro + "%' or " + MainDBConstants.apellido1JefeFamilia + " like '%" + parametro + "%'";
			break;			
		}
		new FetchDataTask().execute(filtro);
	}
	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class FetchDataTask extends AsyncTask<String, Void, String> {
		private String filtro = null;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			filtro = values[0];
			try {
				estudiosAdapter.open();
				mCasas = estudiosAdapter.getCasas(filtro, MainDBConstants.codigo);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the network request completes, hide the progress indicator
			dismissProgressDialog();
			showResult(resultado);
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
			try {
				Bundle arguments = new Bundle();
		        if (mCasa!=null) arguments.putSerializable(Constants.CASA , mCasa);
		        Intent i = new Intent(getApplicationContext(),
		        		NuevoCuestionarioHogarActivity.class);
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

	// ***************************************
	// Private methods
	// ***************************************
	private void showResult(String resultado) {
		CasaAdapter adapter = new CasaAdapter(this, R.layout.complex_list_item, mCasas);
		setListAdapter(adapter);
		if (mCasas.isEmpty()) showToast(getString(R.string.no_items));
	}	

	
	
}
