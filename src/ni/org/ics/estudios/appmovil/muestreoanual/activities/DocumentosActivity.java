package ni.org.ics.estudios.appmovil.muestreoanual.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.database.muestreoanual.CohorteAdapter;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.Documentos;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.DocumentosId;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;


public class DocumentosActivity extends Activity {

	private static final int ACTION_TAKE_PHOTO_B = 1;
	private static final int EXIT = 2;
	
	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private ImageView mImageView;
	private Bitmap mImageBitmap;

	private String mCurrentPhotoPath;

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
	
	private AlertDialog alertDialog;
	private Spinner tipo;
	
	private String username;
	private SharedPreferences settings;
	private Date todayWithZeroTime = null;
	private static Integer codigo = 0;
	private static Integer casaId = 0;
	private boolean visExitosa = false;

	
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

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}

	private File setUpPhotoFile() throws IOException {
		
		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();
		
		return f;
	}

	private void setPic() {

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
	}


	private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		switch(actionCode) {
		case ACTION_TAKE_PHOTO_B:
			File f = null;
			if (mCurrentPhotoPath!= null){
				FileUtils.deleteFile(mCurrentPhotoPath);
				mCurrentPhotoPath = null;
			}
			try {
				f = setUpPhotoFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCurrentPhotoPath = null;
			}
			break;

		default:
			break;			
		} // switch

		startActivityForResult(takePictureIntent, actionCode);
	}
	
	private void handleBigCameraPhoto() {

		if (mCurrentPhotoPath != null) {
			setPic();
		}

	}


	OnClickListener mTakePicOnClickListener =
		new OnClickListener() {
		@Override
		public void onClick(View v) {
			dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
		}
	};

	OnClickListener mSavePhotoOnClickListener =
		new OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};

	OnClickListener mExitActOnClickListener =
		new OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.documentos);

		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);

		casaId = getIntent().getIntExtra(ConstantsDB.COD_CASA,-1);
		codigo = getIntent().getIntExtra(ConstantsDB.CODIGO,-1);
		visExitosa = getIntent().getBooleanExtra(ConstantsDB.VIS_EXITO,false);

		mImageView = (ImageView) findViewById(R.id.imageView1);
		mImageBitmap = null;

		tipo = (Spinner) findViewById(R.id.tipo);
		List<String> list = new ArrayList<String>();
		list.add("Seleccionar..");
		list.add("Control Prenatal");
		list.add("Tarjeta de Vacuna");
		list.add("Expediente Cl�nico");
		list.add("Otro");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(R.layout.spinner_item);
		tipo.setAdapter(dataAdapter);

		Button picBtn = (Button) findViewById(R.id.btnIntend);
		setBtnListenerOrDisable(
				picBtn,
				mTakePicOnClickListener,
				MediaStore.ACTION_IMAGE_CAPTURE
		);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		try {
			todayWithZeroTime =formatter.parse(formatter.format(today));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Button saveBtn = (Button) findViewById(R.id.btnSave);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(validarEntrada()){
					savePhotoToDataBase();
				}
			}

		});

		Button exitBtn = (Button) findViewById(R.id.btnExit);
		exitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createDialog(EXIT);
			}

		});

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTION_TAKE_PHOTO_B: {
			if (resultCode == RESULT_OK) {
				handleBigCameraPhoto();
			}
			break;
		}
		} // switch
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

	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
	 *
	 * @param context The application's environment.
	 * @param action The Intent action to check for availability.
	 *
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list =
			packageManager.queryIntentActivities(intent,
					PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	private void setBtnListenerOrDisable(
			Button btn,
			OnClickListener onClickListener,
			String intentName
	) {
		if (isIntentAvailable(this, intentName)) {
			btn.setOnClickListener(onClickListener);        	
		} else {
			btn.setText( 
				getText(R.string.cannot).toString() + " " + btn.getText());
			btn.setClickable(false);
		}
	}
	@Override
	public void onBackPressed (){
		createDialog(EXIT);
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
					if (mCurrentPhotoPath!= null){
						FileUtils.deleteFile(mCurrentPhotoPath);
						mCurrentPhotoPath = null;
					}
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
	
	private void savePhotoToDataBase(){
		Documentos documento = new Documentos();
		DocumentosId docId = new DocumentosId();
		docId.setCodigo(codigo);
		docId.setFechaDocumento(new Date());
		documento.setDocsId(docId);
		documento.setTipoDoc(tipo.getSelectedItem().toString());
		//leer y guardar foto
		
		try {
			FileInputStream fis = new FileInputStream(mCurrentPhotoPath);
			byte[] imagen = new byte[fis.available()];
			fis.read(imagen);
			documento.setDocumento(imagen);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		documento.setEstado(Constants.STATUS_NOT_SUBMITTED);
		documento.setUsuario(username);
		documento.setFechaRecepcion(new Date());
		documento.setToday(todayWithZeroTime);
        String password = ((MyIcsApplication) this.getApplication()).getPassApp();
		CohorteAdapter ca = new CohorteAdapter(this.getApplicationContext(), password,false, false);
		ca.open();
		ca.crearDocumentos(documento);
		ca.close();
		FileUtils.deleteFile(mCurrentPhotoPath);
		mCurrentPhotoPath = null;
		Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show();
		Intent i = new Intent(getApplicationContext(),
				MenuInfoActivity.class);
		i.putExtra(ConstantsDB.COD_CASA, casaId);
		i.putExtra(ConstantsDB.CODIGO, codigo);
		i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	private boolean validarEntrada() {
		//Valida la entrada 
		if(tipo.getSelectedItemPosition()==0){
			Toast.makeText(this, R.string.required, Toast.LENGTH_LONG).show();
			return false;
		}
		else if(mImageBitmap==null){
			Toast.makeText(this, R.string.required, Toast.LENGTH_LONG).show();
			return false;
		}
		else{
			return true;
		}
	}

}