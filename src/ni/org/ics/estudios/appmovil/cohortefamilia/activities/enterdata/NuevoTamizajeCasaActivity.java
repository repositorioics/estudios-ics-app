package ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata;

import java.util.Date;

import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.activities.DataEnterActivity;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuCasaActivity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.PreTamizaje;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.GPSTracker;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class NuevoTamizajeCasaActivity extends AbstractAsyncActivity {

	protected static final String TAG = NuevoTamizajeCasaActivity.class.getSimpleName();
	public static final int ADD_PRETAMIZAJE = 1;
	Dialog dialogInit;
	GPSTracker gps;
	DeviceInfo infoMovil;
	private static Casa casa = new Casa();
	private EstudiosAdapter estudiosAdapter;
	private String username;
	private SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		gps = new GPSTracker(NuevoTamizajeCasaActivity.this);
		infoMovil = new DeviceInfo(NuevoTamizajeCasaActivity.this);
		casa = (Casa) getIntent().getExtras().getSerializable(Constants.CASA);
		createInitDialog();
	}

	/**
	 * Presenta dialogo inicial
	 */

	private void createInitDialog() {
		dialogInit = new Dialog(this, R.style.FullHeightDialog); 
		dialogInit.setContentView(R.layout.yesno); 
		dialogInit.setCancelable(false);

		//to set the message
		TextView message =(TextView) dialogInit.findViewById(R.id.yesnotext);
		message.setText(getString(R.string.add)+ " " + getString(R.string.new_screen));

		//add some action to the buttons

		Button yes = (Button) dialogInit.findViewById(R.id.yesnoYes);
		yes.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dialogInit.dismiss();
				addPretamizaje();
			}
		});

		Button no = (Button) dialogInit.findViewById(R.id.yesnoNo);
		no.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Cierra
				dialogInit.dismiss();
				finish();
			}
		});
		dialogInit.show();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.MENU_BACK){
			finish();
			return true;
		}
		else if(item.getItemId()==R.id.MENU_HOME){
			Intent i = new Intent(getApplicationContext(),
					MainActivity.class);
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

		if(requestCode == ADD_PRETAMIZAJE) {
	        if(resultCode == RESULT_OK) {
	        	//Datos que vienen de DataEnterActivity
	    		Bundle bundle = intent.getExtras();
	    		String acepta = bundle.getString(this.getString(R.string.aceptaTamizaje));
	    		String razonNP = bundle.getString(acepta+":"+this.getString(R.string.razonNoParticipa));
	    		String codigoCasa = bundle.getString(acepta+":"+this.getString(R.string.codigoCHF));
	        	String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
	    		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
	    		estudiosAdapter.open();
	    		//Recupera los catalogos de la base de datos
	    		Estudio estudio = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=1", null);
	    		MessageResource aceptaTamizaje = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + acepta + "' and " + CatalogosDBConstants.catRoot + "='CAT_SINO'", null);
	    		MessageResource razonNoParticipa = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + razonNP + "' and " + CatalogosDBConstants.catRoot + "='CAT_RAZON_NP'", null);
	    		//Crea un Nuevo Registro de pretamizaje
	        	PreTamizaje preTamizaje =  new PreTamizaje();
	        	preTamizaje.setCodigo(infoMovil.getId());
	        	preTamizaje.setAceptaTamizaje(aceptaTamizaje.getCatKey().charAt(0));
	        	if (razonNoParticipa!=null) preTamizaje.setRazonNoParticipa(razonNoParticipa.getCatKey());
	        	preTamizaje.setCasa(casa);
	        	preTamizaje.setEstudio(estudio);
	        	preTamizaje.setRecordDate(new Date());
	        	preTamizaje.setRecordUser(username);
	        	preTamizaje.setDeviceid(infoMovil.getDeviceId());
	        	preTamizaje.setEstado('0');
	        	preTamizaje.setPasive('0');
	        	//Inserta un Nuevo Registro de Pretamizaje
	        	estudiosAdapter.crearPreTamizaje(preTamizaje);
	        	//Pregunta si acepta el tamizaje
	        	if (preTamizaje.getAceptaTamizaje()=='1') {
		        	//Si la respuesta es si crea un nuevo registro de casa CHF
		        	CasaCohorteFamilia cchf = new CasaCohorteFamilia();
		        	cchf.setCasa(casa);
		        	cchf.setCodigoCHF(codigoCasa);
		        	cchf.setNombre1JefeFamilia(casa.getNombre1JefeFamilia());
		        	cchf.setNombre2JefeFamilia(casa.getNombre2JefeFamilia());
		        	cchf.setApellido1JefeFamilia(casa.getApellido1JefeFamilia());
		        	cchf.setApellido2JefeFamilia(casa.getApellido2JefeFamilia());
		        	cchf.setRecordDate(new Date());
		        	cchf.setRecordUser(username);
		        	cchf.setDeviceid(infoMovil.getDeviceId());
		        	cchf.setEstado('0');
		        	cchf.setPasive('0');
		        	//Inserta una nueva casa CHF
		        	estudiosAdapter.crearCasaCohorteFamilia(cchf);
		        	Bundle arguments = new Bundle();
		            if (cchf!=null) arguments.putSerializable(Constants.CASA , cchf);
		            Intent i = new Intent(getApplicationContext(),
		            		MenuCasaActivity.class);
		            i.putExtras(arguments);
		            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		            startActivity(i);
	        	}
	        	else{
	        		Intent i = new Intent(getApplicationContext(),
	        				MainActivity.class);
	        		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        		startActivity(i);
	        		finish();
	        	}
	        	estudiosAdapter.close();
	        }
	        else{
	        	Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.err_not_completed),Toast.LENGTH_LONG);
				toast.show();
				finish();
	        }
	    }
		super.onActivityResult(requestCode, resultCode, intent);
	}

	/**
	 * 
	 */
	private void addPretamizaje() {
		try{
			new OpenDataEnterActivityTask().execute();
		}
		catch(Exception e){
			Log.e(TAG, e.getMessage(), e);
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
				Intent i = new Intent(getApplicationContext(),
						DataEnterActivity.class);
				i.putExtra(Constants.FORM_NAME, Constants.FORM_NUEVO_TAMIZAJE_CASA);
				startActivityForResult(i , ADD_PRETAMIZAJE);
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
