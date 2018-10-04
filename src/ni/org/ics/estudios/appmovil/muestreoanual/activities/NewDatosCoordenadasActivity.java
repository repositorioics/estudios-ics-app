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
import ni.org.ics.estudios.appmovil.domain.DatosCoordenadas;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.MovilInfo;
import ni.org.ics.estudios.appmovil.muestreoanual.parsers.CoordenadasXml;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
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
import java.util.UUID;


public class NewDatosCoordenadasActivity extends AbstractAsyncActivity {

	protected static final String TAG = NewDatosCoordenadasActivity.class.getSimpleName();

	private static Integer casaId = 0;
	
	private static Integer codigo = 0;
	public static final int VISITA =100;
	private String username;
	private SharedPreferences settings;
	private static Participante mParticipante = new Participante();
	private static DatosCoordenadas mCoordenadas = new DatosCoordenadas();
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
		message.setText(getString(R.string.verify_coor));

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
		try {
            estudiosAdapter.open();
            CoordenadasXml em = new CoordenadasXml();
			em = serializer.read(CoordenadasXml.class, source);
            mCoordenadas.setParticipante(mParticipante);
			mCoordenadas.setCodigoCasa(mParticipante.getCasa().getCodigo());
            mCoordenadas.setEstudios(mParticipante.getProcesos().getEstudio());
			mCoordenadas.setMotivo(em.getMotivo());
            if (em.getMotivo().equals("1") || em.getMotivo().equals("2")) {
                mCoordenadas.setBarrio(estudiosAdapter.getBarrio(CatalogosDBConstants.codigo + "=" + em.getBarrio(), null));
                mCoordenadas.setManzana(em.getManzana());
                mCoordenadas.setDireccion(em.getDireccion());
            }else{
                //solo sin punto
                if (em.getConfirmar().equals(Constants.NOKEYSND)){
                    mCoordenadas.setBarrio(estudiosAdapter.getBarrio(CatalogosDBConstants.codigo + "=" + em.getBarrio(), null));
                    mCoordenadas.setManzana(em.getManzana());
                    mCoordenadas.setDireccion(em.getDireccion());
                }else {
                    mCoordenadas.setBarrio(mParticipante.getCasa().getBarrio());
                    mCoordenadas.setManzana(mParticipante.getCasa().getManzana() != null ? Integer.valueOf(mParticipante.getCasa().getManzana()) : 0);
                    mCoordenadas.setDireccion(mParticipante.getCasa().getDireccion());
                }
            }
            mCoordenadas.setOtroBarrio(em.getOtroBarrio());
			mCoordenadas.setPuntoGps(em.getCoordenadas());

            mCoordenadas.setRazonNoGeoref(em.getNoGeorefRazon());
            mCoordenadas.setOtraRazonNoGeoref(em.getNoGeorefOtraRazon());
			if (em.getCoordenadas()!=null){
				String[] data = em.getCoordenadas().split("\\s+");
				mCoordenadas.setLatitud(Double.valueOf(data[0]));
				mCoordenadas.setLongitud(Double.valueOf(data[1]));
                mCoordenadas.setConpunto(Constants.YESKEYSND);
			}
			else{
				mCoordenadas.setLatitud(null);
				mCoordenadas.setLongitud(null);
                mCoordenadas.setConpunto(Constants.NOKEYSND);
			}

			mCoordenadas.setMovilInfo(new MovilInfo(idInstancia,
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
					false, null, null));


			//Guarda en la base de datos local

			if (em.getTodosSN()!=null && em.getTodosSN().equalsIgnoreCase("1")) {
                mParticipantes = estudiosAdapter.getParticipantes(MainDBConstants.casa + "=" + mParticipante.getCasa().getCodigo(), null);
                for (Participante participante : mParticipantes) {
                    if (participante.getCasa().getCodigo() != 9999) {
                        UUID deviceUuid = new UUID(em.getDeviceid().hashCode(),new Date().hashCode());
                        mCoordenadas.setCodigo(deviceUuid.toString());
                        mCoordenadas.setParticipante(participante);
                        mCoordenadas.setEstudios(participante.getProcesos().getEstudio());
                        estudiosAdapter.crearDatosCoordenadas(mCoordenadas);
                        if (em.getCoordenadas()!=null){
                            participante.getProcesos().setCoordenadas("0");
                        }else {
                            participante.getProcesos().setCoordenadas("3");
                        }

                        estudiosAdapter.actualizarParticipanteProcesos(participante.getProcesos());
                    }
                }
            }else{
                UUID deviceUuid = new UUID(em.getDeviceid().hashCode(),new Date().hashCode());
                mCoordenadas.setCodigo(deviceUuid.toString());
                estudiosAdapter.crearDatosCoordenadas(mCoordenadas);
                if (em.getCoordenadas()!=null){
                    mParticipante.getProcesos().setCoordenadas("0");
                }else {
                    mParticipante.getProcesos().setCoordenadas("3");
                }
                estudiosAdapter.actualizarParticipanteProcesos(mParticipante.getProcesos());
            }
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
					"jrFormId = 'Coordenadas' and displayName = 'Coordenadas'", null, null);
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
			String valores[] = new String[8];
			valores[0] = "motivo";
			valores[1] = mParticipante.getProcesos().getCoordenadas();
            valores[2] = "barrioa";
            valores[3] = mParticipante.getCasa().getBarrio().getNombre();
            valores[4] = "manzanaa";
            valores[5] = mParticipante.getCasa().getManzana();
            valores[6] = "direcciona";
            valores[7] = mParticipante.getCasa().getDireccion();
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
