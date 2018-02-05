package ni.org.ics.estudios.appmovil.muestreoanual.activities;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.database.muestreoanual.CohorteAdapter;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ReConsentimientoDen2015;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ReConsentimientoDen2015Id;
import ni.org.ics.estudios.appmovil.muestreoanual.parsers.ReConsentimientoDen2015Xml;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.Date;


public class NewRec2015Activity extends AbstractAsyncActivity {

	protected static final String TAG = NewRec2015Activity.class.getSimpleName();

	private static Integer casaId = 0;
	private static Integer codigo = 0;
	public static final int ADD_PART =1;
	private String username;
	private SharedPreferences settings;
	private static Participante mParticipante = new Participante();
	private boolean visExitosa = false;
	private static ReConsentimientoDen2015 mReConsentimiento = new ReConsentimientoDen2015();
	Dialog dialogInit;

    private CohorteAdapter ca;
    private EstudiosAdapter estudiosAdapter;

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

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        ca = new CohorteAdapter(this.getApplicationContext(),mPass,false,false);
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);

		casaId = getIntent().getIntExtra(ConstantsDB.COD_CASA,-1);
		codigo = getIntent().getIntExtra(ConstantsDB.CODIGO,-1);
		visExitosa = getIntent().getBooleanExtra(ConstantsDB.VIS_EXITO,false);
		getData();
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
		message.setText(getString(R.string.verify_rc));

		//add some action to the buttons

		Button yes = (Button) dialogInit.findViewById(R.id.yesnoYes);
		yes.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dialogInit.dismiss();
				addReConsentimientoDen();
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
		switch (item.getItemId()) {
		case R.id.MENU_BACK:
			finish();
			return true;
		case R.id.MENU_HOME:
			Intent i = new Intent(getApplicationContext(),
					MenuMuestreoAnualActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
				parseReConsentimientoDen(idInstancia,instanceFilePath,cambio);
			}
			else{
				showToast(getApplicationContext().getString(R.string.err_not_completed),1);
			}
		}
		else{
			finish();
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	public void parseReConsentimientoDen(Integer idInstancia, String instanceFilePath, String ultimoCambio) {
		Serializer serializer = new Persister();
		File source = new File(instanceFilePath);
		try {
			ReConsentimientoDen2015Xml em = new ReConsentimientoDen2015Xml();
			em = serializer.read(ReConsentimientoDen2015Xml.class, source);
			ReConsentimientoDen2015Id reconsId = new ReConsentimientoDen2015Id();
			reconsId.setCodigo(codigo);
			reconsId.setFechaCons(new Date());
			mReConsentimiento.setReconsdenId(reconsId);
			mReConsentimiento.setVisExit(em.getVisExit());
			mReConsentimiento.setRazonVisNoExit(em.getRazonVisNoExit());
			mReConsentimiento.setPersonaDejoCarta(em.getPersonaDejoCarta());
			mReConsentimiento.setPersonaCasa(em.getPersonaCasa());
			mReConsentimiento.setRelacionFamPersonaCasa(em.getRelacionFamPersonaCasa());
			mReConsentimiento.setOtraRelacionPersonaCasa(em.getOtraRelacionPersonaCasa());
			mReConsentimiento.setTelefonoPersonaCasa(em.getTelefonoPersonaCasa());
			mReConsentimiento.setEmancipado(em.getEmancipado());
			mReConsentimiento.setDescEmancipado(em.getDescEmancipado());
			mReConsentimiento.setIncDen(em.getIncDen());
			mReConsentimiento.setExcDen(em.getExcDen());
			mReConsentimiento.setEnfCronSN(em.getEnfCronSN());
			mReConsentimiento.setEnfCronica(em.getEnfCronica());
			mReConsentimiento.setoEnfCronica(em.getoEnfCronica());
			mReConsentimiento.setTomaTx(em.getTomaTx());
			mReConsentimiento.setCualesTx(em.getCualesTx());
			mReConsentimiento.setAssite(em.getAssite());
			mReConsentimiento.setCentrosalud(em.getCentrosalud());
			mReConsentimiento.setOcentrosalud(em.getOcentrosalud());
			mReConsentimiento.setPuestosalud(em.getPuestosalud());
			mReConsentimiento.setAsentimiento(em.getAsentimiento());
			mReConsentimiento.setParteADen(em.getParteADen());
			mReConsentimiento.setRechDen(em.getRechDen());
			mReConsentimiento.setParteBDen(em.getParteBDen());
			mReConsentimiento.setParteCDen(em.getParteCDen());
			mReConsentimiento.setParteDDen(em.getParteDDen());
			mReConsentimiento.setNombrept(em.getNombrept());
			mReConsentimiento.setNombrept2(em.getNombrept2());
			mReConsentimiento.setApellidopt(em.getApellidopt());
			mReConsentimiento.setApellidopt2(em.getApellidopt2());
			mReConsentimiento.setRelacionFam(em.getRelacionFam());
			mReConsentimiento.setOtraRelacionFam(em.getOtraRelacionFam());
			mReConsentimiento.setMismoTutorSN(em.getMismoTutorSN());
			mReConsentimiento.setMotivoDifTutor(em.getMotivoDifTutor());
			mReConsentimiento.setOtroMotivoDifTutor(em.getOtroMotivoDifTutor());
			mReConsentimiento.setQuePaisTutor(em.getQuePaisTutor());
			mReConsentimiento.setAlfabetoTutor(em.getAlfabetoTutor());
			mReConsentimiento.setTestigoSN(em.getTestigoSN());
			mReConsentimiento.setNombretest1(em.getNombretest1());
			mReConsentimiento.setNombretest2(em.getNombretest2());
			mReConsentimiento.setApellidotest1(em.getApellidotest1());
			mReConsentimiento.setApellidotest2(em.getApellidotest2());
			mReConsentimiento.setCmDomicilio(em.getCmDomicilio());
			mReConsentimiento.setBarrio(em.getBarrio());
			mReConsentimiento.setOtrobarrio(em.getOtrobarrio());
			mReConsentimiento.setDire(em.getDire());
			mReConsentimiento.setAutsup(em.getAutsup());
			mReConsentimiento.setTelefonoClasif1(em.getTelefonoClasif1());
			mReConsentimiento.setTelefonoConv1(em.getTelefonoConv1());
			mReConsentimiento.setTelefonoCel1(em.getTelefonoCel1());
			mReConsentimiento.setTelefono2SN(em.getTelefono2SN());
			mReConsentimiento.setTelefonoClasif2(em.getTelefonoClasif2());
			mReConsentimiento.setTelefonoConv2(em.getTelefonoConv2());
			mReConsentimiento.setTelefonoCel2(em.getTelefonoCel2());
			mReConsentimiento.setTelefono3SN(em.getTelefono3SN());
			mReConsentimiento.setTelefonoClasif3(em.getTelefonoClasif3());
			mReConsentimiento.setTelefonoConv3(em.getTelefonoConv3());
			mReConsentimiento.setTelefonoCel3(em.getTelefonoCel3());
			mReConsentimiento.setJefenom(em.getJefenom());
			mReConsentimiento.setJefenom2(em.getJefenom2());
			mReConsentimiento.setJefeap(em.getJefeap());
			mReConsentimiento.setJefeap2(em.getJefeap2());
			mReConsentimiento.setNomContacto(em.getNomContacto());
			mReConsentimiento.setBarrioContacto(em.getBarrioContacto());
			mReConsentimiento.setOtrobarrioContacto(em.getOtrobarrioContacto());
			mReConsentimiento.setDireContacto(em.getDireContacto());
			mReConsentimiento.setTelContacto1(em.getTelContacto1());
			mReConsentimiento.setTelContactoConv1(em.getTelContactoConv1());
			mReConsentimiento.setTelContactoCel1(em.getTelContactoCel1());
			mReConsentimiento.setTelContacto2SN(em.getTelContacto2SN());
			mReConsentimiento.setTelContactoClasif2(em.getTelContactoClasif2());
			mReConsentimiento.setTelContactoConv2(em.getTelContactoConv2());
			mReConsentimiento.setTelContactoCel2(em.getTelContactoCel2());
			mReConsentimiento.setNombrepadre(em.getNombrepadre());
			mReConsentimiento.setNombrepadre2(em.getNombrepadre2());
			mReConsentimiento.setApellidopadre(em.getApellidopadre());
			mReConsentimiento.setApellidopadre2(em.getApellidopadre2());
			mReConsentimiento.setNombremadre(em.getNombremadre());
			mReConsentimiento.setNombremadre2(em.getNombremadre2());
			mReConsentimiento.setApellidomadre(em.getApellidomadre());
			mReConsentimiento.setApellidomadre2(em.getApellidomadre2());
			mReConsentimiento.setCopiaFormato(em.getCopiaFormato());
			mReConsentimiento.setFirmo_cartcons(em.getFirmo_cartcons());
			mReConsentimiento.setFecho_cartcons(em.getFecho_cartcons());
			mReConsentimiento.setHuella_dig(em.getHuella_dig());
			mReConsentimiento.setFech_firm_testigo(em.getFech_firm_testigo());
			mReConsentimiento.setEntiende(em.getEntiende());
			mReConsentimiento.setGeoref(em.getGeoref());
			mReConsentimiento.setManzana(em.getManzana());
			mReConsentimiento.setGeoref_razon(em.getGeoref_razon());
			mReConsentimiento.setGeoref_otraRazon(em.getGeoref_otraRazon());
			mReConsentimiento.setLocal(em.getLocal());
			mReConsentimiento.setOtrorecurso1(em.getOtrorecurso1());
			mReConsentimiento.setOtrorecurso2(em.getOtrorecurso2());
			
			mReConsentimiento.setMovilInfo(new MovilInfo(idInstancia,
					instanceFilePath,
					Constants.STATUS_NOT_SUBMITTED,
					ultimoCambio,
					em.getStart(),
					em.getEnd(),
					em.getDeviceid(),
					em.getSimserial(),
					em.getPhonenumber(),
					em.getToday(),
					username,
					false, em.getRecurso1(), em.getRecurso2()));
			
			//Guarda en la base de datos local
			ca.open();
			ca.crearReConsentimiento2015(mReConsentimiento);
            ca.close();
			if (em.getVisExit().matches("1")){
				mParticipante.getProcesos().setConsDeng("No");
				mParticipante.getProcesos().setMovilInfo(new MovilInfo(idInstancia,
                        instanceFilePath,
                        Constants.STATUS_NOT_SUBMITTED,
                        ultimoCambio,
                        em.getStart(),
                        em.getEnd(),
                        em.getDeviceid(),
                        em.getSimserial(),
                        em.getPhonenumber(),
                        em.getToday(),
                        username,
                        false, em.getRecurso1(), em.getRecurso2()));
                estudiosAdapter.open();
				estudiosAdapter.actualizarParticipanteProcesos(mParticipante.getProcesos());

                estudiosAdapter.close();
				showToast(getApplicationContext().getString(R.string.success), 0);
				Intent i = new Intent(getApplicationContext(),
						MenuInfoActivity.class);
				i.putExtra(ConstantsDB.COD_CASA, casaId);
				i.putExtra(ConstantsDB.CODIGO, codigo);
				i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
			else{
				showToast(getApplicationContext().getString(R.string.success),0);
				Intent i = new Intent(getApplicationContext(),
						MenuMuestreoAnualActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		} catch (Exception e) {
			// Presenta el error al parsear el xml
			showToast(e.toString(),1);
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	private void addReConsentimientoDen() {
		try{
			//campos de proveedor de collect
			String[] projection = new String[] {
					"_id","jrFormId","displayName"};
			//cursor que busca el formulario
			Cursor c = getContentResolver().query(Constants.CONTENT_URI, projection,
					"jrFormId = 'DEN_Reconsentimiento' and displayName = 'DEN_Reconsentimiento'", null, null);
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
			String valores[] = new String[6];
			valores[0] = "recons_den";
			valores[1] = mParticipante.getProcesos().getReConsDeng();
			valores[2] = "edad";
			valores[3] = mParticipante.getEdadMeses().toString();
			valores[4] = "conpto";
			valores[5] = mParticipante.getProcesos().getConPto();
			odkA.putExtra("vc", valores);
			startActivityForResult(odkA,ADD_PART);
		}
		catch(Exception e){
			//No existe el formulario en el equipo
			Log.e(TAG, e.getMessage(), e);
			showToast(e.getLocalizedMessage(),1);
		}
	}

    private void getData() {
        estudiosAdapter.open();
        mParticipante = estudiosAdapter.getParticipante(MainDBConstants.codigo + "=" + codigo, null);
        estudiosAdapter.close();
    }


	private void showToast(String mensaje, int numImage){
		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(R.layout.toast,
				(ViewGroup) findViewById(R.id.toast_layout_root));

		ImageView image = (ImageView) layout.findViewById(R.id.image);

		switch(numImage){
		case 0:
			image.setImageResource(R.drawable.ic_ok);
			break;
		case 1:
			image.setImageResource(R.drawable.ic_error);
			break;
		default:
			image.setImageResource(R.drawable.ic_launcher);
			break;
		}


		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(mensaje);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();

	}

}
