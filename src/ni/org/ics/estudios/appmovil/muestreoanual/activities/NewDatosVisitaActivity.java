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
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.DatosVisitaTerreno;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.DatosVisitaTerrenoId;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.muestreoanual.parsers.DatosVisitaXml;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NewDatosVisitaActivity extends AbstractAsyncActivity {

	protected static final String TAG = NewDatosVisitaActivity.class.getSimpleName();

	private static Integer casaId = 0;
	
	private static Integer codigo = 0;
	public static final int VISITA =100;
	private String username;
	private SharedPreferences settings;
	private static Participante mParticipante = new Participante();
	private static DatosVisitaTerreno mVisitaTerreno = new DatosVisitaTerreno();
	private List<Participante> mParticipantes = new ArrayList<Participante>();
	Dialog dialogInit;
	private boolean visExitosa = false;

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
		message.setText(getString(R.string.verify_vt));

		//add some action to the buttons

		Button yes = (Button) dialogInit.findViewById(R.id.yesnoYes);
		yes.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dialogInit.dismiss();
				addVisita();
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
				if (requestCode==VISITA){
					parseVisita(idInstancia,instanceFilePath,cambio);
				}
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

	public void parseVisita(Integer idInstancia, String instanceFilePath, String ultimoCambio) {
		Serializer serializer = new Persister();
		File source = new File(instanceFilePath);
		Double latitud = null;
		Double longitud = null;
		try {
			DatosVisitaXml em = new DatosVisitaXml();
			em = serializer.read(DatosVisitaXml.class, source);
			DatosVisitaTerrenoId vtId = new DatosVisitaTerrenoId();
			vtId.setCodigo(codigo);
			vtId.setFechaVisita(new Date());
			mVisitaTerreno.setVisitaId(vtId);
			mVisitaTerreno.setCodCasa(mParticipante.getCasa().getCodigo());
			mVisitaTerreno.setcDom(em.getcDom());
			mVisitaTerreno.setBarrio(em.getBarrio());
			mVisitaTerreno.setManzana(em.getManzana());
			mVisitaTerreno.setDireccion(em.getDireccion());
			mVisitaTerreno.setCoordenadas(em.getCoordenadas());
			if (em.getCoordenadas()!=null){
				String[] data = em.getCoordenadas().split("\\s+");
				mVisitaTerreno.setLatitud(Double.valueOf(data[0]));
				mVisitaTerreno.setLongitud(Double.valueOf(data[1]));
				latitud = mVisitaTerreno.getLatitud();
				longitud = mVisitaTerreno.getLongitud();
			}
			else{
				mVisitaTerreno.setLatitud(null);
				mVisitaTerreno.setLongitud(null);
			}
			mVisitaTerreno.setOtrorecurso1(em.getOtrorecurso1());
			mVisitaTerreno.setOtrorecurso2(em.getOtrorecurso2());
			mVisitaTerreno.setTelefonoClasif1(em.getTelefonoClasif1());
			mVisitaTerreno.setTelefonoConv1(em.getTelefonoConv1());
			mVisitaTerreno.setTelefonoCel1(em.getTelefonoCel1());
			mVisitaTerreno.setTelefonoEmpresa1(em.getTelefonoEmpresa1());
			mVisitaTerreno.setTelefono2SN(em.getTelefono2SN());
			mVisitaTerreno.setTelefonoClasif2(em.getTelefonoClasif2());
			mVisitaTerreno.setTelefonoConv2(em.getTelefonoConv2());
			mVisitaTerreno.setTelefonoCel2(em.getTelefonoCel2());
			mVisitaTerreno.setTelefonoEmpresa2(em.getTelefonoEmpresa2());
			mVisitaTerreno.setTelefono3SN(em.getTelefono3SN());
			mVisitaTerreno.setTelefonoClasif3(em.getTelefonoClasif3());
			mVisitaTerreno.setTelefonoConv3(em.getTelefonoConv3());
			mVisitaTerreno.setTelefonoCel3(em.getTelefonoCel3());
			mVisitaTerreno.setTelefonoEmpresa3(em.getTelefonoEmpresa3());
			mVisitaTerreno.setTelefono4SN(em.getTelefono4SN());
			mVisitaTerreno.setTelefonoClasif4(em.getTelefonoClasif4());
			mVisitaTerreno.setTelefonoConv4(em.getTelefonoConv4());
			mVisitaTerreno.setTelefonoCel4(em.getTelefonoCel4());
			mVisitaTerreno.setTelefonoEmpresa4(em.getTelefonoEmpresa4());
			mVisitaTerreno.setCandidatoNI(em.getCandidatoNI());
			mVisitaTerreno.setNombreCandNI1(em.getNombreCandNI1());
			mVisitaTerreno.setNombreCandNI2(em.getNombreCandNI2());
			mVisitaTerreno.setApellidoCandNI1(em.getApellidoCandNI1());
			mVisitaTerreno.setApellidoCandNI2(em.getApellidoCandNI2());
			mVisitaTerreno.setNombreptTutorCandNI(em.getNombreptTutorCandNI());
			mVisitaTerreno.setNombreptTutorCandNI2(em.getNombreptTutorCandNI2());
			mVisitaTerreno.setApellidoptTutorCandNI(em.getApellidoptTutorCandNI());
			mVisitaTerreno.setApellidoptTutorCandNI2(em.getApellidoptTutorCandNI2());
			mVisitaTerreno.setRelacionFamCandNI(em.getRelacionFamCandNI());
			mVisitaTerreno.setOtraRelacionFamCandNI(em.getOtraRelacionFamCandNI());
	
			
			mVisitaTerreno.setMovilInfo(new MovilInfo(idInstancia,
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
            estudiosAdapter.open();
			estudiosAdapter.crearDatosVisita(mVisitaTerreno);
            mParticipantes = estudiosAdapter.getParticipantes(MainDBConstants.casa + "=" + mParticipante.getCasa().getCodigo(), null);
			for(Participante participante:mParticipantes){
				if (participante.getCasa().getCodigo()!=9999){
					participante.getProcesos().setDatosVisita("No");
                    estudiosAdapter.actualizarParticipanteProcesos(participante.getProcesos());
				}
			}
			mParticipante.getProcesos().setDatosVisita("No");
			//no será necesario actualizar casa
			/*if (em.getCoordenadas()!=null){
				mParticipante.getCasa().setLatitud(latitud);
				mParticipante.getCasa().setLongitud(longitud);
                mParticipante.getCasa().setEstado(Constants.STATUS_NOT_SUBMITTED.charAt(0));
                estudiosAdapter.editarCasa(mParticipante.getCasa());
			}*/
            estudiosAdapter.actualizarParticipanteProcesos(mParticipante.getProcesos());
            estudiosAdapter.close();
			showToast(getApplicationContext().getString(R.string.success),0);
			
			
			
			Intent i = new Intent(getApplicationContext(),
					MenuInfoActivity.class);
			i.putExtra(ConstantsDB.COD_CASA, casaId);
			i.putExtra(ConstantsDB.CODIGO, codigo);
			i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			
		} catch (Exception e) {
			// Presenta el error al parsear el xml
			showToast(e.toString(),1);
			e.printStackTrace();
		}

	}

	
	/**
	 * 
	 */
	private void addVisita() {
		try{
			//campos de proveedor de collect
			String[] projection = new String[] {
					"_id","jrFormId","displayName"};
			//cursor que busca el formulario
			Cursor c = getContentResolver().query(Constants.CONTENT_URI, projection,
					"jrFormId = 'DatosDeVisita' and displayName = 'DatosDeVisita'", null, null);
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
			String valores[] = new String[2];
			valores[0] = "punto";
			valores[1] = mParticipante.getProcesos().getConPto();
			odkA.putExtra("vc", valores);
			startActivityForResult(odkA,VISITA);
		}
		catch(Exception e){
			//No existe el formulario en el equipo
			Log.e(TAG, e.getMessage(), e);
			showToast(e.getLocalizedMessage(),1);
		}
	}

    private void getData() {
        estudiosAdapter.open();
        mParticipante = estudiosAdapter.getParticipante(MainDBConstants.codigo +"="+ codigo, null);
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
