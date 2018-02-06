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
import ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasaId;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.muestreoanual.parsers.EncuestaCasaXml;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NewEcActivity extends AbstractAsyncActivity {

	protected static final String TAG = NewEcActivity.class.getSimpleName();


	private static Integer casaId = 0;
    private static String casaChfId = null;
	private static Integer codigo = 0;
	public static final int ADD_PART =1;
	private String username;
	private SharedPreferences settings;
	private static Participante mParticipante = new Participante();
	private List<Participante> mParticipantes = new ArrayList<Participante>();
	private static EncuestaCasa mEC = new EncuestaCasa();
	private boolean visExitosa = false;
	Dialog dialogInit;
    private DeviceInfo infoMovil;

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
        infoMovil = new DeviceInfo(NewEcActivity.this);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        ca = new CohorteAdapter(this.getApplicationContext(),mPass,false,false);
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);

//        if (getIntent().getExtras().get(ConstantsDB.COD_CASA) instanceof String){
            casaChfId = getIntent().getStringExtra(Constants.CASACHF);
//        }else{
            casaId = getIntent().getIntExtra(ConstantsDB.COD_CASA,-1);
        //}
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
		message.setText(getString(R.string.verify_ec));

		//add some action to the buttons

		Button yes = (Button) dialogInit.findViewById(R.id.yesnoYes);
		yes.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dialogInit.dismiss();
				addEncCasa();
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
				parseEstacionEC(idInstancia,instanceFilePath,cambio);
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

	public void parseEstacionEC(Integer idInstancia, String instanceFilePath, String ultimoCambio) {
		Serializer serializer = new Persister();
		File source = new File(instanceFilePath);
		try {
			EncuestaCasaXml em = new EncuestaCasaXml();
			em = serializer.read(EncuestaCasaXml.class, source);

			//EncuestaCasaId ecId = new EncuestaCasaId();
			//ecId.setCodCasa(casaId);
			//ecId.setFechaEncCasa(new Date());
			//mEC.setEncCasaId(ecId);
            mEC.setCodigo(infoMovil.getId());
            if (casaId!=null && casaId > 0) mEC.setCodCasa(casaId);
            mEC.setCodCasaChf(casaChfId);
            mEC.setFechaEncCasa(new Date());
			mEC.setCvivencasa1(em.getCvivencasa1());
			mEC.setCvivencasa2(em.getCvivencasa2());
			mEC.setCvivencasa3(em.getCvivencasa3());
			mEC.setCvivencasa4(em.getCvivencasa4());
			mEC.setCvivencasa5(em.getCvivencasa5());
			mEC.setCvivencasa6(em.getCvivencasa6());
			mEC.setCcuartos(em.getCcuartos());
			mEC.setGrifo(em.getGrifo());
			mEC.setGrifoComSN(em.getGrifoComSN());
			mEC.sethorasagua(em.getHorasagua());
			mEC.setMcasa(em.getMcasa());
			mEC.setOcasa(em.getOcasa());
			mEC.setPiso(em.getPiso());
			mEC.setOpiso(em.getOpiso());
			mEC.setTecho(em.getTecho());
			mEC.setOtecho(em.getOtecho());
			mEC.setCpropia(em.getCpropia());
			mEC.setCabanicos(em.getCabanicos());
			mEC.setCtelevisores(em.getCtelevisores());
			mEC.setCrefrigeradores(em.getCrefrigeradores());
			mEC.setMoto(em.getMoto());
			mEC.setCarro(em.getCarro());
			mEC.setCocinalena(em.getCocinalena());
			mEC.setAnimalesSN(em.getAnimalesSN());
			mEC.setPollos(em.getPollos());
			mEC.setPolloscasa(em.getPolloscasa());
			mEC.setPatos(em.getPatos());
			mEC.setPatoscasa(em.getPatoscasa());
			mEC.setPerros(em.getPerros());
			mEC.setPerroscasa(em.getPerroscasa());
			mEC.setGatos(em.getGatos());
			mEC.setGatoscasa(em.getGatoscasa());
			mEC.setCerdos(em.getCerdos());
			mEC.setCerdoscasa(em.getCerdoscasa());
            mEC.setOtrorecurso1(em.getOtrorecurso1());
            mEC.setOtrorecurso2(em.getOtrorecurso2());

            //CHF + NUEVAS PREGUNTAS MA2018
            mEC.setViveEmbEnCasa(em.getViveEmbEnCasa());
            mEC.setCantidadCuartos(em.getCANTIDAD_CUARTOS());
            mEC.setAlmacenaAgua(em.getALMACENA_AGUA());
            mEC.setAlmacenaEnBarriles(em.getALMACENA_EN_BARRILES());
            mEC.setNumeroBarriles(em.getNUMERO_BARRILES());
            mEC.setBarrilesTapados(em.getBARRILES_TAPADOS());
            mEC.setBarrilesConAbate(em.getBARRILES_CON_ABATE());
            mEC.setAlmacenaEnTanques(em.getALMACENA_EN_TANQUES());
            mEC.setNumeroTanques(em.getNUMERO_TANQUES());
            mEC.setTanquesTapados(em.getTANQUES_TAPADOS());
            mEC.setTanquesConAbate(em.getTANQUES_CON_ABATE());
            mEC.setAlmacenaEnPilas(em.getALMACENA_EN_PILAS());
            mEC.setNumeroPilas(em.getNUMERO_PILAS());
            mEC.setPilasTapadas(em.getPILAS_TAPADAS());
            mEC.setPilasConAbate(em.getPILAS_CON_ABATE());
            mEC.setAlmacenaEnOtrosrecip(em.getALMACENA_EN_OTROSRECIP());
            mEC.setDescOtrosRecipientes(em.getDESC_OTROS_RECIPIENTES());
            mEC.setNumeroOtrosRecipientes(em.getNUMERO_OTROS_RECIPIENTES());
            mEC.setOtrosRecipTapados(em.getOTROS_RECIP_TAPADOS());
            mEC.setOtrosrecipConAbate(em.getOTROSRECIP_CON_ABATE());
            mEC.setUbicacionLavandero(em.getUBICACION_LAVANDERO());
            mEC.setTieneAbanico(em.getTIENE_ABANICO());
            mEC.setTieneTelevisor(em.getTIENE_TELEVISOR());
            mEC.setTieneRefrigeradorFreezer(em.getTIENE_REFRIGERADOR_FREEZER());
            mEC.setTieneAireAcondicionado(em.getTIENE_AIRE_ACONDICIONADO());
            mEC.setFuncionamientoAire(em.getFUNCIONAMIENTO_AIRE());
            mEC.setOpcFabCarro(em.getOpcFabCarro());
            mEC.setYearNow(em.getYearNow());
            mEC.setYearFabCarro(em.getYearFabCarro());
            mEC.setTieneMicrobus(em.getTIENE_MICROBUS());
            mEC.setTieneCamioneta(em.getTIENE_CAMIONETA());
            mEC.setTieneCamion(em.getTIENE_CAMION());
            mEC.setTieneOtroMedioTrans(em.getTIENE_OTRO_MEDIO_TRANS());
            mEC.setDescOtroMedioTrans(em.getDESC_OTRO_MEDIO_TRANS());
            mEC.setCocinaConLenia(em.getCOCINA_CON_LENIA());
            mEC.setUbicacionCocinaLenia(em.getUBICACION_COCINA_LENIA());
            mEC.setPeriodicidadCocinaLenia(em.getPERIODICIDAD_COCINA_LENIA());
            mEC.setNumDiarioCocinaLenia(em.getNUM_DIARIO_COCINA_LENIA());
            mEC.setNumSemanalCocinaLenia(em.getNUM_SEMANAL_COCINA_LENIA());
            mEC.setNumQuincenalCocinaLenia(em.getNUM_QUINCENAL_COCINA_LENIA());
            mEC.setNumMensualCocinaLenia(em.getNUM_MENSUAL_COCINA_LENIA());
            mEC.setTieneGallinas(em.getTIENE_GALLINAS());
            mEC.setTienePatos(em.getTIENE_PATOS());
            mEC.setTienePerros(em.getTIENE_PERROS());
            mEC.setTieneGatos(em.getTIENE_GATOS());
            mEC.setTieneCerdos(em.getTIENE_CERDOS());
            mEC.setPersFumaDentroCasa(em.getPERS_FUMA_DENTRO_CASA());
            mEC.setMadreFuma(em.getMADRE_FUMA());
            mEC.setCantCigarrillosMadre(em.getCANT_CIGARRILLOS_MADRE());
            mEC.setPadreFuma(em.getPADRE_FUMA());
            mEC.setCantCigarrillosPadre(em.getCANT_CIGARRILLOS_PADRE());
            mEC.setOtrosFuman(em.getOTROS_FUMAN());
            mEC.setCantOtrosFuman(em.getCANT_OTROS_FUMAN());
            mEC.setCantCigarrillosOtros(em.getCANT_CIGARRILLOS_OTROS());
            mEC.setServRecolBasura(em.getServRecolBasura());
            mEC.setFrecServRecolBasura(em.getFrecServRecolBasura());
            mEC.setLlantasOtrosContConAgua(em.getLlantasOtrosContConAgua());
            mEC.setOpcFabMicrobus(em.getOpcFabMicrobus());
            mEC.setYearFabMicrobus(em.getYearFabMicrobus());
            mEC.setOpcFabCamioneta(em.getOpcFabCamioneta());
            mEC.setYearFabCamioneta(em.getYearFabCamioneta());
            mEC.setOpcFabCamion(em.getOpcFabCamion());
            mEC.setYearFabCamion(em.getYearFabCamion());
            mEC.setOpcFabOtroMedioTrans(em.getOpcFabOtroMedioTrans());
            mEC.setYearFabOtroMedioTrans(em.getYearFabOtroMedioTrans());

            mEC.setMovilInfo(new MovilInfo(idInstancia,
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
            estudiosAdapter.open();
            int totalCasasChf = estudiosAdapter.countCasasChfByCasa(mParticipante.getCasa().getCodigo());
            if (totalCasasChf == 1){
                mEC.setCodCasaChf(mParticipante.getProcesos().getCasaCHF());
                mEC.setCodCasa(mParticipante.getCasa().getCodigo());
            }
            ca.crearEncuestaCasa(mEC);
            if (mParticipante.getCasa().getCodigo()!=9999) mParticipantes = estudiosAdapter.getParticipantes(MainDBConstants.casa + "=" + mParticipante.getCasa().getCodigo(), null);
            for(Participante participante:mParticipantes){
				if (participante.getCasa().getCodigo()!=9999) {
                    ParticipanteProcesos procesos = participante.getProcesos();
                    if (totalCasasChf == 1){ // es una unica casa cohorte pediatrica y una unica casa cohorte familia, solo realizar encuesta de casa una vez
                        procesos.setEnCasaChf("No");
                        procesos.setEnCasa("No");
                    }else {
                        if (casaChfId != null) procesos.setEnCasaChf("No");
                        if (casaId != null && casaId > 0) procesos.setEnCasa("No");
                    }
                    procesos.setMovilInfo(new MovilInfo(idInstancia,
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
                    estudiosAdapter.actualizarParticipanteProcesos(procesos);
                }
			}
            /*if (totalCasasChf == 1){// es una unica casa cohorte pediatrica y una unica casa cohorte familia, solo realizar encuesta de casa una vez
                mParticipante.getProcesos().setEnCasaChf("No");
                mParticipante.getProcesos().setEnCasa("No");
            }else {
                if (casaChfId != null) mParticipante.getProcesos().setEnCasaChf("No");
                if (casaId != null && casaId > 0) mParticipante.getProcesos().setEnCasa("No");
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
            estudiosAdapter.actualizarParticipanteProcesos(mParticipante.getProcesos());*/
            estudiosAdapter.close();
			ca.close();
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
	private void addEncCasa() {
		try{
			//campos de proveedor de collect
			String[] projection = new String[] {
					"_id","jrFormId","displayName"};
			//cursor que busca el formulario
			Cursor c = getContentResolver().query(Constants.CONTENT_URI, projection,
					"jrFormId = 'encuestacasa' and displayName = 'EncuestaCasa'", null, null);
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
