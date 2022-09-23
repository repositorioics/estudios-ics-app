package ni.org.ics.estudios.appmovil.entomologia.activities;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioPuntoClave;
import ni.org.ics.estudios.appmovil.entomologia.parsers.CuestionarioPuntoClaveXml;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuMuestreoAnualActivity;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;


public class NuevoCuestionarioPuntoClaveActivity extends AbstractAsyncActivity {

	protected static final String TAG = NuevoCuestionarioPuntoClaveActivity.class.getSimpleName();

	public static final int ADD_CUEST =1;
	private String username;
	private SharedPreferences settings;
	private DeviceInfo infoMovil;

	private static CuestionarioPuntoClave mPuntoClave = new CuestionarioPuntoClave();
	Dialog dialogInit;

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
		infoMovil = new DeviceInfo(NuevoCuestionarioPuntoClaveActivity.this);
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
		message.setText(getString(R.string.confirmar_cuestionariopc));

		//add some action to the buttons

		Button yes = (Button) dialogInit.findViewById(R.id.yesnoYes);
		yes.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dialogInit.dismiss();
				addCuestionario();
			}
		});

		Button no = (Button) dialogInit.findViewById(R.id.yesnoNo);
		no.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Cierra
				dialogInit.dismiss();
				openMenuEnto();
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
		switch (item.getItemId()) {
		case R.id.MENU_BACK:
			finish();
			return true;
		case R.id.MENU_HOME:
			openMenuEnto();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed (){
		openMenuEnto();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		//Si todo salio bien en ODK Collect
		if (resultCode == RESULT_OK) {
			Uri instanceUri = intent.getData();
			//Busca la instancia resultado
			String[] projection = new String[] {
					"_id","instanceFilePath", "status","displaySubtext"};
			Cursor c = getContentResolver().query(instanceUri, projection,
					null, null, null);
			c.moveToFirst();
			//Captura la id de la instancia y la ruta del archivo para agregarlo al participante
			Integer idInstancia = c.getInt(c.getColumnIndex("_id"));
			String instanceFilePath = c.getString(c.getColumnIndex("instanceFilePath"));
			String complete = c.getString(c.getColumnIndex("status"));
			String cambio = c.getString(c.getColumnIndex("displaySubtext"));
			//cierra el cursor
			if (c != null) {
				c.close();
			}
			if (complete.matches("complete")){
				//Parsear el resultado obteniendo un participante si esta completa
				parseEstacionES(idInstancia,instanceFilePath,cambio);
			}
			else{
				Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.err_not_completed),Toast.LENGTH_LONG);
				toast.show();
			}
		}
		else{
			openMenuEnto();
			finish();
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	public void parseEstacionES(Integer idInstancia, String instanceFilePath, String ultimoCambio) {
		Serializer serializer = new Persister();
		File source = new File(instanceFilePath);
		try {
			CuestionarioPuntoClaveXml es = new CuestionarioPuntoClaveXml();
			es = serializer.read(CuestionarioPuntoClaveXml.class, source);
			mPuntoClave.setCodigoCuestionario(infoMovil.getId());
			mPuntoClave.setFechaCuestionario(es.getFecha());
			mPuntoClave.setBarrio(es.getBarrio());
			mPuntoClave.setNombrePuntoClave(es.getNombrePuntoClave());
			mPuntoClave.setDireccionPuntoClave(es.getDireccion());
			mPuntoClave.setTipoPuntoClave(es.getTipo());
			mPuntoClave.setTipoPuntoProductividad(es.getClasProductividad());
			mPuntoClave.setTipoPuntoProductividadOtro(es.getOtroProductividad());
			mPuntoClave.setTipoPuntoAglomeracion(es.getClasAglomeracion());
			mPuntoClave.setTipoPuntoAglomeracionOtro(es.getOtroAglomeracion());
			mPuntoClave.setCuantasPersonasReunen(es.getNroPersonas());
			mPuntoClave.setCuantosDiasSemanaReunen(es.getNroDiasPorSemana());
			mPuntoClave.setHoraInicioReunion(es.getHoraInicio());
			mPuntoClave.setHoraFinReunion(es.getHoraFin());
			mPuntoClave.setPuntoGps(es.getPuntoGPS());
			if (es.getPuntoGPS()!=null){
				String[] data = es.getPuntoGPS().split("\\s+");
				mPuntoClave.setLatitud(Double.valueOf(data[0]));
				mPuntoClave.setLongitud(Double.valueOf(data[1]));
			}
			else{
				mPuntoClave.setLatitud(null);
				mPuntoClave.setLongitud(null);
			}

			mPuntoClave.setTipoIngresoCodigoSitio(es.getTipoIngresoCodSitio());
			mPuntoClave.setCodigoSitio((es.getCodSitioManual() != null? es.getCodSitioManual() : es.getCodSitioQR()));

			mPuntoClave.setHayAmbientePERI(es.getAmbPERI());
			mPuntoClave.setHoraCapturaPERI(es.getHoraCapturaPERI());
			mPuntoClave.setHumedadRelativaPERI(es.getHumedadRelativaPERI());
			mPuntoClave.setTemperaturaPERI(es.getTemperaturaPERI());
			mPuntoClave.setTipoIngresoCodigoPERI(es.getTipoIngresoCodPERI());
			mPuntoClave.setCodigoPERI((es.getCodPERIManual() != null? es.getCodPERIManual() : es.getCodPERIQR()));

			mPuntoClave.setHayAmbienteINTRA(es.getAmbINTRA());
			mPuntoClave.setHoraCapturaINTRA(es.getHoraCapturaINTRA());
			mPuntoClave.setHumedadRelativaINTRA(es.getHumedadRelativaINTRA());
			mPuntoClave.setTemperaturaINTRA(es.getTemperaturaINTRA());
			mPuntoClave.setTipoIngresoCodigoINTRA(es.getTipoIngresoCodINTRA());
			mPuntoClave.setCodigoINTRA((es.getCodINTRAManual() != null? es.getCodINTRAManual() : es.getCodINTRAQR()));
			mPuntoClave.setNombrePersonaContesta(es.getNombrePersonaInfo());


			mPuntoClave.setMovilInfo(new MovilInfo(idInstancia,
					instanceFilePath,
					Constants.STATUS_NOT_SUBMITTED,
					ultimoCambio,
					es.getStart(),
					es.getEnd(),
					es.getDeviceid(),
					es.getSimserial(),
					es.getPhonenumber(),
					es.getToday(),
					username,
					false, null, null));
			//Guarda en la base de datos local
            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
			EstudiosAdapter ca = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
			ca.open();
			ca.crearCuestionarioPuntoClave(mPuntoClave);
			ca.close();
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
			toast.show();
			openMenuEnto();
		} catch (Exception e) {
			// Presenta el error al parsear el xml
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error),Toast.LENGTH_LONG);
			toast.show();
			e.printStackTrace();
		}

	}

	private void openMenuEnto() {
		Intent i = new Intent(getApplicationContext(),
				MenuEntomologiaActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}
	/**
	 * 
	 */
	private void addCuestionario() {
		try{
			//campos de proveedor de collect
			String[] projection = new String[] {
					"_id","jrFormId","displayName"};
			//cursor que busca el formulario
			Cursor c = getContentResolver().query(Constants.CONTENT_URI, projection,
					"jrFormId = 'CuestionarioPuntosClaves' and displayName = 'Cuestionario de Puntos Claves'", null, null);
			c.moveToFirst();
			//captura el id del formulario
			Integer id = Integer.parseInt(c.getString(0));
			//cierra el cursor
			if (c != null) {
				c.close();
			}
			//forma el uri para ODK Collect
			Uri formUri = ContentUris.withAppendedId(Constants.CONTENT_URI,id);
			//Arranca la actividad ODK Collect en busca de resultado
			Intent odkA =  new Intent(Intent.ACTION_EDIT,formUri);
			startActivityForResult(odkA, ADD_CUEST);
		}
		catch(Exception e){
			//No existe el formulario en el equipo
			Log.e(TAG, e.getMessage(), e);
			Toast.makeText( this.getApplicationContext(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
		}
	}

}
