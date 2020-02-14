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
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MuestraId;
import ni.org.ics.estudios.appmovil.muestreoanual.parsers.MuestraXml;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.Date;


public class NewSampleActivity extends AbstractAsyncActivity {

	protected static final String TAG = NewSampleActivity.class.getSimpleName();

	private static Integer casaId = 0;
	private static Integer codigo = 0;
	public static final int ADD_PART =1;
	private String username;
	private SharedPreferences settings;
	private static Participante mParticipante = new Participante();
	private boolean visExitosa = false;
	private static Muestra mMuestra = new Muestra();
	Dialog dialogInit;

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
		message.setText(getString(R.string.verify_sm));

		//add some action to the buttons

		Button yes = (Button) dialogInit.findViewById(R.id.yesnoYes);
		yes.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dialogInit.dismiss();
				addMuestra();
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
				parseEstacionMuestra(idInstancia,instanceFilePath,cambio);
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

	public void parseEstacionMuestra(Integer idInstancia, String instanceFilePath, String ultimoCambio) {
		Serializer serializer = new Persister();
		File source = new File(instanceFilePath);
		try {
            MuestraXml em = new MuestraXml();
            em = serializer.read(MuestraXml.class, source);
            MuestraId mId = new MuestraId();
            mId.setCodigo(codigo);
            mId.setFechaMuestra(new Date());
            mMuestra.setmId(mId);
            mMuestra.setFiebreM(em.getFiebreM());
            mMuestra.setConsulta(em.getConsulta());
            mMuestra.setTuboBHC(em.getTuboBHC());
            mMuestra.setTuboLeu(em.getTuboLeu());
            mMuestra.setTuboRojo(em.getTuboRojo());
            mMuestra.setEstudiosAct(mParticipante.getProcesos().getEstudio());
            mMuestra.setBhc_razonNo(em.getBhc_razonNo());
            mMuestra.setRojo_razonNo(em.getRojo_razonNo());
            mMuestra.setPbmc_razonNo(em.getPbmc_razonNo());

            mMuestra.setBhc_otraRazonNo(em.getBhc_otraRazonNo());
            mMuestra.setRojo_otraRazonNo(em.getRojo_otraRazonNo());
            mMuestra.setPbmc_otraRazonNo(em.getPbmc_otraRazonNo());

            mMuestra.setHoraBHC(em.getHoraBHC());
            mMuestra.setHoraPBMC(em.getHoraPBMC());
            mMuestra.setHoraInicioPax(em.getHoraInicioPax());

            mMuestra.setHoraFinPax(em.getHoraFinPax());
            mMuestra.setTerreno(em.getTerreno());
            mMuestra.setCodPax(em.getCodPax());

            mMuestra.setPinchazos(em.getPinchazos());
            mMuestra.setOtrorecurso1(em.getOtrorecurso1());
            mMuestra.setOtrorecurso2(em.getOtrorecurso2());
            //MA 2019
            mMuestra.setHd_sn(em.getHd_sn());
            mMuestra.setHdPorqueNo(em.getHdPorqueNo());

            mMuestra.setMovilInfo(new MovilInfo(idInstancia,
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
            estudiosAdapter.crearMuestra(mMuestra);

            String mxSer = "";
            if (em.getTuboRojo() != null) {
                if (em.getTuboRojo().toString().matches("1")) {
                    mxSer = "Si";
                } else {
                    mxSer = "No";
                }
                mParticipante.getProcesos().setConmx(mxSer);
            }
            if (em.getTuboLeu() != null) {
                if (em.getTuboLeu().toString().matches("1")) {
                    mxSer = "Si";
                } else {
                    mxSer = "No";
                }
                mParticipante.getProcesos().setConmx(mxSer);
            }
            String mxBhc = "";
            if (em.getTuboBHC() != null) {
                if (em.getTuboBHC().toString().matches("1")) {
                    mxBhc = "Si";
                } else {
                    mxBhc = "No";
                }
                mParticipante.getProcesos().setConmxbhc(mxBhc);
            }
            if (mParticipante.getProcesos().getEstudio().equals("Influenza") || mParticipante.getProcesos().getEstudio().equals("Influenza  Cohorte BB")) {
                if (mParticipante.getEdadMeses() >= 6 && mParticipante.getEdadMeses() < 24) {
                    if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                        mParticipante.getProcesos().setConmxbhc("Si");
                    }
                }
            }
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

            estudiosAdapter.actualizarParticipanteProcesos(mParticipante.getProcesos());
            estudiosAdapter.close();
            showToast(getApplicationContext().getString(R.string.success), 0);
            loadMenuInfo();
        } catch (Exception e) {
			// Presenta el error al parsear el xml
			showToast(e.toString(),1);
			e.printStackTrace();
            loadMenuInfo();
		}

	}

    private void loadMenuInfo(){
        Intent i = new Intent(getApplicationContext(),
                MenuInfoActivity.class);
        i.putExtra(ConstantsDB.COD_CASA, casaId);
        i.putExtra(ConstantsDB.CODIGO, codigo);
        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

    }

	/**
	 * 
	 */
	private void addMuestra() {
		try{
			//campos de proveedor de collect
			String[] projection = new String[] {
					"_id","jrFormId","displayName"};
			//cursor que busca el formulario
			Cursor c = getContentResolver().query(Constants.CONTENT_URI, projection,
					"jrFormId = 'muestra' and displayName = 'Muestra'", null, null);
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
			String valores[] = new String[18];
			valores[0] = "pbmc";
			valores[1] = mParticipante.getProcesos().getPbmc();
			valores[2] = "conmx";
			valores[3] = mParticipante.getProcesos().getConmx();
			valores[4] = "conmxbhc";
			valores[5] = mParticipante.getProcesos().getConmxbhc();
			valores[6] = "edad";
			valores[7] = mParticipante.getEdadMeses().toString();
			valores[8] = "convalesciente";
			valores[9] = mParticipante.getProcesos().getConvalesciente();
			valores[10] = "paxgene";
			valores[11] = mParticipante.getProcesos().getPaxgene();
			valores[12] = "confirmar";
			valores[13] = getString(R.string.begin_sample);
			valores[14] = "estudio";
			if (!mParticipante.getProcesos().getEstudio().contains("UO1")) {
				valores[15] = mParticipante.getProcesos().getEstudio();//"Dengue UO1 CH Familia"; //
			}else {
				valores[15] = mParticipante.getProcesos().getEstudio().replaceAll(" {2}"," ");//"Dengue UO1 CH Familia"; //
			}
			valores[16] = "terreno";
			if (visExitosa){
				valores[17] = "Si";
			}
			else{
				valores[17] = "No";
			}
			
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
