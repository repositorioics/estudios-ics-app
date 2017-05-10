package ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata;

import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.activities.DataEnterActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.GPSTracker;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
	public static final int ADD_VIVIENDA = 1;
	Dialog dialogInit;
	GPSTracker gps;
	DeviceInfo infoMovil;
	String codComunidad;
	String codUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
		gps = new GPSTracker(NuevoTamizajeCasaActivity.this);
		infoMovil = new DeviceInfo(NuevoTamizajeCasaActivity.this);
		codComunidad = getIntent().getStringExtra(MainDBConstants.codigo);
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
		message.setText(getString(R.string.add)+ " " + getString(R.string.casa_id));

		//add some action to the buttons

		Button yes = (Button) dialogInit.findViewById(R.id.yesnoYes);
		yes.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dialogInit.dismiss();
				addVivienda();
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

		if(requestCode == ADD_VIVIENDA) {
	        if(resultCode == RESULT_OK) {
	        	/*Vivienda vivienda = new Vivienda();
	        	vivienda.setNumVivienda(Integer.parseInt(intent.getExtras().getString(this.getString(R.string.numVivienda))));
	        	vivienda.setNomResp(intent.getExtras().getString(this.getString(R.string.nomResp)));
	        	vivienda.setApeResp(intent.getExtras().getString(this.getString(R.string.apeResp)));
	        	vivienda.setDireccion(intent.getExtras().getString(this.getString(R.string.direccion)));
	        	vivienda.setTelefonoContacto(intent.getExtras().getString(this.getString(R.string.telefonoContacto)));
	        	if(gps.canGetLocation()){
	                vivienda.setLatitud(BigDecimal.valueOf(gps.getLatitude()));
	                vivienda.setLongitud(BigDecimal.valueOf(gps.getLongitude()));
	        	}
	        	MovilInfo mi = new MovilInfo('0', infoMovil.getDeviceId());
	        	vivienda.setMovilinfo(mi);
	        	VigentoAdapter va = new VigentoAdapter();
	        	va.open();
	        	String filtro = ConstantsDB.COM_CODIGO + "='" + codComunidad + "'";
	        	Comunidades comunidad = va.getComunidad(filtro, null);
	        	vivienda.setComunidad(comunidad);
	        	vivienda.setIdVivienda(infoMovil.getId());
	        	va.crearVivienda(vivienda);
	        	va.close();*/
	        }
	        else{
	        	
	        }
	    }
		Intent i = new Intent(getApplicationContext(),
				MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
		super.onActivityResult(requestCode, resultCode, intent);
	}

	/**
	 * 
	 */
	private void addVivienda() {
		try{
			Intent i = new Intent(getApplicationContext(),
					DataEnterActivity.class);
			i.putExtra(Constants.FORM_NAME, Constants.FORM_NUEVO_TAMIZAJE_CASA);
			startActivityForResult(i , ADD_VIVIENDA);
		}
		catch(Exception e){
			Log.e(TAG, e.getMessage(), e);
		}
	}

}
