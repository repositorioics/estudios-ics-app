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
import ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipanteId;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.muestreoanual.parsers.EncuestaParticipanteXml;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.Date;


public class NewEpActivity extends AbstractAsyncActivity {

	protected static final String TAG = NewEpActivity.class.getSimpleName();

	private static Integer casaId = 0;
	private static Integer codigo = 0;
	public static final int ADD_PART =1;
	private String username;
	private SharedPreferences settings;
	private static Participante mParticipante = new Participante();
	private boolean visExitosa = false;
	private static EncuestaParticipante mEncuestaParticipante = new EncuestaParticipante();
	Dialog dialogInit;
    private String esChf=Constants.NO;
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
		message.setText(getString(R.string.verify_ep));

		//add some action to the buttons

		Button yes = (Button) dialogInit.findViewById(R.id.yesnoYes);
		yes.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dialogInit.dismiss();
				addEncParticipante();
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
				parseEstacionEP(idInstancia,instanceFilePath,cambio);
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

	public void parseEstacionEP(Integer idInstancia, String instanceFilePath, String ultimoCambio) {
		Serializer serializer = new Persister();
		File source = new File(instanceFilePath);
		try {
			EncuestaParticipanteXml em = new EncuestaParticipanteXml();
			em = serializer.read(EncuestaParticipanteXml.class, source);
			EncuestaParticipanteId epId = new EncuestaParticipanteId();
			epId.setCodigo(codigo);
			epId.setFechaEncPar(new Date());
			mEncuestaParticipante.setEpId(epId);
			mEncuestaParticipante.setFiebre(em.getFiebre());
			mEncuestaParticipante.setTiemFieb(em.getTiemFieb());
			mEncuestaParticipante.setLugarCons(em.getLugarCons());
			mEncuestaParticipante.setNoCs(em.getNoCs());
			mEncuestaParticipante.setAutomed(em.getAutomed());
			mEncuestaParticipante.setEscuela(em.getEscuela());
			mEncuestaParticipante.setGrado(em.getGrado());
			mEncuestaParticipante.setTurno(em.getTurno());
			mEncuestaParticipante.setnEscuela(em.getnEscuela());
			mEncuestaParticipante.setOtraEscuela(em.getOtraEscuela());
			mEncuestaParticipante.setCuidan(em.getCuidan());
			mEncuestaParticipante.setCuantosCuidan(em.getCuantosCuidan());
			mEncuestaParticipante.setCqVive(em.getCqVive());
			mEncuestaParticipante.setLugarPart(em.getLugarPart());
			mEncuestaParticipante.setPapaAlf(em.getPapaAlf());
			mEncuestaParticipante.setPapaNivel(em.getPapaNivel());
			mEncuestaParticipante.setPapaTra(em.getPapaTra());
			mEncuestaParticipante.setPapaTipoTra(em.getPapaTipoTra());
			mEncuestaParticipante.setMamaAlf(em.getMamaAlf());
			mEncuestaParticipante.setMamaNivel(em.getMamaNivel());
			mEncuestaParticipante.setMamaTra(em.getMamaTra());
			mEncuestaParticipante.setMamaTipoTra(em.getMamaTipoTra());
			mEncuestaParticipante.setComparteHab(em.getComparteHab());
			mEncuestaParticipante.setHab1(em.getHab1());
			mEncuestaParticipante.setHab2(em.getHab2());
			mEncuestaParticipante.setHab3(em.getHab3());
			mEncuestaParticipante.setHab4(em.getHab4());
			mEncuestaParticipante.setHab5(em.getHab5());
			mEncuestaParticipante.setHab6(em.getHab6());
			mEncuestaParticipante.setComparteCama(em.getComparteCama());
			mEncuestaParticipante.setCama1(em.getCama1());
			mEncuestaParticipante.setCama2(em.getCama2());
			mEncuestaParticipante.setCama3(em.getCama3());
			mEncuestaParticipante.setCama4(em.getCama4());
			mEncuestaParticipante.setCama5(em.getCama5());
			mEncuestaParticipante.setCama6(em.getCama6());
			mEncuestaParticipante.setAsma(em.getAsma());
			mEncuestaParticipante.setSilb12m(em.getSilb12m());
			mEncuestaParticipante.setSitResf(em.getSitResf());
			mEncuestaParticipante.setSitEjer(em.getSitEjer());
			mEncuestaParticipante.setSilbMesPas(em.getSilbMesPas());
			mEncuestaParticipante.setDifHablar(em.getDifHablar());
			mEncuestaParticipante.setVecHablar(em.getVecHablar());
			mEncuestaParticipante.setDifDormir(em.getDifDormir());
			mEncuestaParticipante.setSuenoPer(em.getSuenoPer());
			mEncuestaParticipante.setTos12m(em.getTos12m());
			mEncuestaParticipante.setVecesTos(em.getVecesTos());
			mEncuestaParticipante.setTos3Dias(em.getTos3Dias());
			mEncuestaParticipante.setHosp12m(em.getHosp12m());
			mEncuestaParticipante.setMed12m(em.getMed12m());
			mEncuestaParticipante.setDep12m(em.getDep12m());
			mEncuestaParticipante.setCrisis(em.getCrisis());
			mEncuestaParticipante.setFrecAsma(em.getFrecAsma());
			mEncuestaParticipante.setFumaSN(em.getFumaSN());
			mEncuestaParticipante.setQuienFuma(em.getQuienFuma());
			mEncuestaParticipante.setCantCigarrosMadre(em.getCantCigarrosMadre());
			mEncuestaParticipante.setCantCigarrosOtros(em.getCantCigarrosOtros());
			mEncuestaParticipante.setCantCigarrosPadre(em.getCantCigarrosPadre());
			
			mEncuestaParticipante.setRash(em.getRash());
			mEncuestaParticipante.setMesActual(em.getMesActual());
			mEncuestaParticipante.setYearActual(em.getYearActual());
			mEncuestaParticipante.setRash_year(em.getRash_year());
			mEncuestaParticipante.setRash_mes(em.getRash_mes());
			mEncuestaParticipante.setRash_mesact(em.getRash_mesact());
			mEncuestaParticipante.setRashCara(em.getRashCara());
			mEncuestaParticipante.setRashMiembrosSup(em.getRashMiembrosSup());
			mEncuestaParticipante.setRashTorax(em.getRashTorax());
			mEncuestaParticipante.setRashAbdomen(em.getRashAbdomen());
			mEncuestaParticipante.setRashMiembrosInf(em.getRashMiembrosInf());
			mEncuestaParticipante.setRashDias(em.getRashDias());
			mEncuestaParticipante.setOjoRojo(em.getOjoRojo());
			mEncuestaParticipante.setOjoRojo_year(em.getOjoRojo_year());
			mEncuestaParticipante.setOjoRojo_mes(em.getOjoRojo_mes());
			mEncuestaParticipante.setOjoRojo_mesact(em.getOjoRojo_mesact());
			mEncuestaParticipante.setOjoRojo_Dias(em.getOjoRojo_Dias());
			mEncuestaParticipante.setHormigueo(em.getHormigueo());
			mEncuestaParticipante.setHormigueo_year(em.getHormigueo_year());
			mEncuestaParticipante.setHormigueo_mes(em.getHormigueo_mes());
			mEncuestaParticipante.setHormigueo_mesact(em.getHormigueo_mesact());
			mEncuestaParticipante.setHormigueo_Dias(em.getHormigueo_Dias());
			mEncuestaParticipante.setConsultaRashHormigueo(em.getConsultaRashHormigueo());
			mEncuestaParticipante.setuSaludRashHormigueo(em.getuSaludRashHormigueo());
			mEncuestaParticipante.setcSaludRashHormigueo(em.getcSaludRashHormigueo());
			mEncuestaParticipante.setoCSRashHormigueo(em.getoCSRashHormigueo());
			mEncuestaParticipante.setpSRashHormigueo(em.getpSRashHormigueo());
			mEncuestaParticipante.setoPSRashHormigueo(em.getoPSRashHormigueo());
			mEncuestaParticipante.setDiagRashHormigueo(em.getDiagRashHormigueo());
			mEncuestaParticipante.setChPapaMama(em.getChPapaMama());
			mEncuestaParticipante.setFechana_papa(em.getFechana_papa());
			mEncuestaParticipante.setCal_edad_papa(em.getCal_edad_papa());
			mEncuestaParticipante.setCal_edad2_papa(em.getCal_edad2_papa());
			mEncuestaParticipante.setNombpapa1(em.getNombpapa1());
			mEncuestaParticipante.setNombpapa2(em.getNombpapa2());
			mEncuestaParticipante.setApellipapa1(em.getApellipapa1());
			mEncuestaParticipante.setApellipapa2(em.getApellipapa2());
			mEncuestaParticipante.setFechana_mama(em.getFechana_mama());
			mEncuestaParticipante.setCal_edad_mama(em.getCal_edad_mama());
			mEncuestaParticipante.setCal_edad2_mama(em.getCal_edad2_mama());
			mEncuestaParticipante.setNombmama1(em.getNombmama1());
			mEncuestaParticipante.setNombmama2(em.getNombmama2());
			mEncuestaParticipante.setApellimama1(em.getApellimama1());
			mEncuestaParticipante.setApellimama2(em.getApellimama2());

            mEncuestaParticipante.setOtrorecurso1(em.getOtrorecurso1());
            mEncuestaParticipante.setOtrorecurso2(em.getOtrorecurso2());

            //CHF + NUEVAS PREGUNTAS MA2018
            mEncuestaParticipante.setEmancipado(em.getEMANCIPADO());
            mEncuestaParticipante.setDescEnmancipado(em.getDescEnmancipado());
            mEncuestaParticipante.setOtraDescEmanc(em.getOtraDescEmanc());
            mEncuestaParticipante.setEmbarazada(em.getEMBARAZADA());
            mEncuestaParticipante.setSemanasEmbarazo(em.getSEMANAS_EMBARAZO());
            mEncuestaParticipante.setAlfabeto(em.getALFABETO());
            mEncuestaParticipante.setNivelEducacion(em.getNIVEL_EDUCACION());
            mEncuestaParticipante.setTrabaja(em.getTRABAJA());
            mEncuestaParticipante.setTipoTrabajo(em.getTIPO_TRABAJO());
            mEncuestaParticipante.setOcupacionActual(em.getOCUPACION_ACTUAL());
            mEncuestaParticipante.setNinoAsisteEscuela(em.getNINO_ASISTE_ESCUELA());
            mEncuestaParticipante.setGradoEstudiaNino(em.getGRADO_ESTUDIA_NINO());
            mEncuestaParticipante.setNinoTrabaja(em.getNINO_TRABAJA());
            mEncuestaParticipante.setOcupacionActualNino(em.getOCUPACION_ACTUAL_NINO());
            mEncuestaParticipante.setPadreEstudio(em.getPADRE_ESTUDIO());
            mEncuestaParticipante.setCodigoPadreEstudio(em.getCODIGO_PADRE_ESTUDIO());
            mEncuestaParticipante.setPadreAlfabeto(em.getPADRE_ALFABETO());
            mEncuestaParticipante.setTrabajaPadre(em.getTRABAJA_PADRE());
            mEncuestaParticipante.setMadreEstudio(em.getMADRE_ESTUDIO());
            mEncuestaParticipante.setCodigoMadreEstudio(em.getCODIGO_MADRE_ESTUDIO());
            mEncuestaParticipante.setMadreAlfabeta(em.getMADRE_ALFABETA());
            mEncuestaParticipante.setTrabajaMadre(em.getTRABAJA_MADRE());
            mEncuestaParticipante.setFuma(em.getFUMA());
            mEncuestaParticipante.setPeriodicidadFuna(em.getPERIODICIDAD_FUNA());
            mEncuestaParticipante.setCantidadCigarrillos(em.getCANTIDAD_CIGARRILLOS());
            mEncuestaParticipante.setFumaDentroCasa(em.getFUMA_DENTRO_CASA());
            mEncuestaParticipante.setTuberculosisPulmonarActual(em.getTUBERCULOSIS_PULMONAR_ACTUAL());
            String fechaDiagnosticoCompuestaAct = null;
            if (em.getA_FECHA_DIAG_TUBPUL_ACTUAL()!=null)
                fechaDiagnosticoCompuestaAct = em.getA_FECHA_DIAG_TUBPUL_ACTUAL().toString();
            if (em.getM_FECHA_DIAG_TUBPUL_ACTUAL()!=null)
                fechaDiagnosticoCompuestaAct += "/"+ em.getM_FECHA_DIAG_TUBPUL_ACTUAL();
            mEncuestaParticipante.setFechaDiagTubpulActual(fechaDiagnosticoCompuestaAct);

            mEncuestaParticipante.setTratamientoTubpulActual(em.getTRATAMIENTO_TUBPUL_ACTUAL());
            mEncuestaParticipante.setCompletoTratamientoTubpulActual(em.getCOMPLETO_TRATAMIENTO_TUBPUL_ACTUAL());
            mEncuestaParticipante.setTuberculosisPulmonarPasado(em.getTUBERCULOSIS_PULMONAR_PASADO());
            mEncuestaParticipante.setFechaDiagTubpulPasadoDes(em.getFECHA_DIAG_TUBPUL_PASADO_DES());
            String fechaDiagnosticoCompuestaPas = null;
            if (em.getA_FECHA_DIAG_TUBPUL_PASADO()!=null)
                fechaDiagnosticoCompuestaPas = em.getA_FECHA_DIAG_TUBPUL_PASADO().toString();
            if (em.getM_FECHA_DIAG_TUBPUL_PASADO()!=null)
                fechaDiagnosticoCompuestaPas += "/"+ em.getM_FECHA_DIAG_TUBPUL_PASADO();
            mEncuestaParticipante.setFechaDiagTubpulPasado(fechaDiagnosticoCompuestaPas);

            mEncuestaParticipante.setTratamientoTubpulPasado(em.getTRATAMIENTO_TUBPUL_PASADO());
            mEncuestaParticipante.setCompletoTratamientoTubpulPas(em.getCOMPLETO_TRATAMIENTO_TUBPUL_PAS());
            mEncuestaParticipante.setAlergiaRespiratoria(em.getALERGIA_RESPIRATORIA());
            mEncuestaParticipante.setCardiopatia(em.getCARDIOPATIA());
            mEncuestaParticipante.setEnfermPulmonarObstCronica(em.getENFERM_PULMONAR_OBST_CRONICA());
            mEncuestaParticipante.setDiabetes(em.getDIABETES());
            mEncuestaParticipante.setPresionAlta(em.getPRESION_ALTA());
            mEncuestaParticipante.setTosSinFiebreResfriado(em.getTOS_SIN_FIEBRE_RESFRIADO());
            mEncuestaParticipante.setCantEnfermCuadrosResp(em.getCANT_ENFERM_CUADROS_RESP());
            mEncuestaParticipante.setOtrasEnfermedades(em.getOTRAS_ENFERMEDADES());
            mEncuestaParticipante.setDescOtrasEnfermedades(em.getDESC_OTRAS_ENFERMEDADES());
            mEncuestaParticipante.setVacunaInfluenza(em.getVACUNA_INFLUENZA());
            mEncuestaParticipante.setAnioVacunaInfluenza(em.getANIO_VACUNA_INFLUENZA());
            mEncuestaParticipante.setRash6m(em.getRash6m());
            mEncuestaParticipante.setOjoRojo6m(em.getOjoRojo6m());
            mEncuestaParticipante.setEstudiosAct(mParticipante.getProcesos().getEstudio());
            //MA2019
            mEncuestaParticipante.setVacunaInfluenzaMes(em.getVacunaInfluenzaMes());
            mEncuestaParticipante.setVacunaInfluenzaCSSF(em.getVacunaInfluenzaCSSF());
            mEncuestaParticipante.setVacunaInfluenzaOtro(em.getVacunaInfluenzaOtro());
            mEncuestaParticipante.setNombreCDI(em.getNombreCDI());
            mEncuestaParticipante.setDireccionCDI(em.getDireccionCDI());

            mEncuestaParticipante.setMovilInfo(new MovilInfo(idInstancia,
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
			estudiosAdapter.crearEncuestaParticipante(mEncuestaParticipante);
			mParticipante.getProcesos().setEncPart("No");
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
	private void addEncParticipante() {
		try{
			//campos de proveedor de collect
			String[] projection = new String[] {
					"_id","jrFormId","displayName"};
			//cursor que busca el formulario
			Cursor c = getContentResolver().query(Constants.CONTENT_URI, projection,
					"jrFormId = 'encuestaparticipante' and displayName = 'EncuestaParticipante'", null, null);
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
            String edad[] = mParticipante.getEdad().split("/");
            String anios = "0";
            if (edad.length > 0) {
                anios = edad[0];
            }
            valores[0] = "edad";
            valores[1] =  anios;
            valores[2] = "sexo";
            valores[3] =  mParticipante.getSexo();
            valores[4] = "chf";
            valores[5] =  esChf;

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
        if (mParticipante.getProcesos().getEstudio().contains("CH Familia")) {
            esChf = Constants.YES;
        }
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
