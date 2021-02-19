package ni.org.ics.estudios.appmovil.muestreoanual.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.cohortefamilia.dto.DatosCHF;
import ni.org.ics.estudios.appmovil.covid19.dto.DatosCovid19;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.ParticipanteCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaVacunaUO1;
import ni.org.ics.estudios.appmovil.influenzauo1.dto.DatosUO1;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ParticipantPhotographActivity extends Activity {

	public static final int BARCODE_CAPTURE = 2;
	private static final int EXIT = 3;
	private int opcion;
	
	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private ImageView mImageView;
	private Bitmap mImageBitmap;

	private String mCurrentPhotoPath;

	private static final String JPEG_FILE_SUFFIX = ".jpg";

	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
	
	private AlertDialog alertDialog;
	private Spinner mMetodoView;
	private EditText mParametroView;
	private ImageButton mBarcodeButton;
	private ImageButton mFindButton;

	private String username;
	private SharedPreferences settings;
	private Date todayWithZeroTime = null;
	private String codigoParticipante = null;

	private EstudiosAdapter estudiosAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.participant_photograph);

		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);

		mImageView = (ImageView) findViewById(R.id.imvPhotograph);
		mImageBitmap = null;

		mMetodoView = (Spinner) findViewById(R.id.metodo_busqueda);
		List<String> list = new ArrayList<String>();
		list.add(getString(R.string.desc_barcode));
		list.add(getString(R.string.enter)+" "+getString(R.string.code));

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(R.layout.spinner_item);
		mMetodoView.setAdapter(dataAdapter);

		mMetodoView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
						mParametroView.setHint(getString(R.string.code));
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
				if ((mParametroView.getText().toString()==null) || (mParametroView.getText().toString().matches(""))){
					mParametroView.requestFocus();
					mParametroView.setError(getString(R.string.search_hint));
					return;
				}
				loadImage(mParametroView.getText().toString());
			}
		});

		mFindButton.setVisibility(View.GONE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}

		mImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.equals(mImageView)) {
					//if (codigoParticipante != null) {
						/*Intent i = new Intent(getApplicationContext(),
								SelecPartActivity.class);
						i.putExtra(Constants.MENU_INFO, true);
						i.putExtra(Constants.PARTICIPANTE, codigoParticipante);
						startActivity(i);*/
					/*} else {
						showToast("No se encontr");
					}*/
					try {
						estudiosAdapter.open();
						Participante mParticipante = estudiosAdapter.getParticipante(MainDBConstants.codigo + " = " + codigoParticipante, null);

						if (mParticipante != null && mParticipante.getCodigo() != null) {
							Intent i = new Intent(getApplicationContext(),
									MenuInfoActivity.class);
							i.putExtra(Constants.DESDE_FOTO, true);
							i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
							i.putExtra(ConstantsDB.VIS_EXITO, false);
							startActivity(i);
							finish();
						} else {
							showToast(getString(R.string.code_notfound) + "(" + codigoParticipante + ")");
						}
					} catch (Exception e) {
						showToast(getString(R.string.code_notfound) + "(" + codigoParticipante + ")");
						return;
					} finally {
						//ca.close();
						estudiosAdapter.close();
					}
				}
			}
		});
	}

	// Some lifecycle callbacks so that the image can survive orientation change
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		mImageView.setImageBitmap(mImageBitmap);
	}

	@Override
	public void onBackPressed (){
		createDialog(EXIT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
									Intent intent) {
		if (requestCode == BARCODE_CAPTURE && intent != null) {
			String sb = intent.getStringExtra("SCAN_RESULT");
			if (sb != null && sb.length() > 0) {
				try{
					loadImage(sb);
				}
				catch(Exception e){
					showToast(e.getLocalizedMessage());
					return;
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);

	}

	private void loadImage(String codigo) {
		try {
			codigoParticipante = codigo;
			File albumF = getAlbumDir();
			if (albumF!=null) {
				File imageF = new File(albumF.getAbsolutePath() + "/" + codigo + JPEG_FILE_SUFFIX);
				mCurrentPhotoPath = imageF.getAbsolutePath();
				if (mCurrentPhotoPath != null) {
					if (!setPic()) {
						imageF = new File(albumF.getAbsolutePath() + "/notfound" + JPEG_FILE_SUFFIX);
						mCurrentPhotoPath = imageF.getAbsolutePath();
						setPic();
						showToast(getString(R.string.photograph_notfound, codigo));
					}
				}
			}else {
				showToast("Directorio no encontrado: "+getAlbumDir());
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/* Photo album for this application */
	private String getAlbumName() {
		return getString(R.string.album_name);
	}


	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d("DocumentosCohorte", "failed to create directory");
						return null;
					}
				}
			}

		} else {
			Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}

		return storageDir;
	}

	private boolean setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
		mImageView.setImageBitmap(bitmap);
		mImageBitmap = bitmap;
		mImageView.setVisibility(View.VISIBLE);

		return bitmap != null;
	}

	private void showToast(String mensaje){
		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(R.layout.toast,
				(ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(mensaje);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case EXIT:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.exiting));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Finish app
					dialog.dismiss();
					finish();
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
		default:
			break;
		}
		alertDialog = builder.create();
		alertDialog.show();
	}
}