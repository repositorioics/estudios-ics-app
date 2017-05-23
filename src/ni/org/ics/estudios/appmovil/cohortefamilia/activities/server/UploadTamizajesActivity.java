
package ni.org.ics.estudios.appmovil.cohortefamilia.activities.server;

import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.tasks.UploadTamizajesTask;
import ni.org.ics.estudios.appmovil.listeners.UploadListener;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class UploadTamizajesActivity extends Activity implements UploadListener{

	protected static final String TAG = UploadTamizajesActivity.class.getSimpleName();

	private String username;
	private String password;
	private String url;
	private SharedPreferences settings;
	private UploadTamizajesTask uploadTamizajesTask;

	private final static int PROGRESS_DIALOG = 1;
	private ProgressDialog mProgressDialog;

	// ***************************************
	// Metodos de la actividad
	// ***************************************

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(getString(R.string.app_name) + " > "
				+ getString(R.string.upload));

		if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			setResult(RESULT_CANCELED);
			finish();
		}
		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		url =
				settings.getString(PreferencesActivity.KEY_SERVER_URL, this.getString(R.string.default_server_url));
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);

		password =
				((MyIcsApplication) this.getApplication()).getPassApp();

		// get the task if we've changed orientations. If it's null it's a new upload.
		uploadTamizajesTask = (UploadTamizajesTask) getLastNonConfigurationInstance();
		if (uploadTamizajesTask == null) {
			uploadAll();
		}
	}


	@Override
	public void uploadComplete(String result) {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
		if(result!=null){
			if (result.matches("Datos recibidos!")) {
				setResult(RESULT_OK);
			} else {
				Intent intent = new Intent();
				intent.putExtra("resultado", result);
				setResult(RESULT_CANCELED, intent);
			}
		}
		else{
			Intent intent = new Intent();
			intent.putExtra("resultado", getString(R.string.error));
			setResult(RESULT_CANCELED, intent);
		}
		uploadTamizajesTask = null;
		finish();

	}

	@Override
	public void progressUpdate(String message, int progress, int max) {

		mProgressDialog.setMax(max);
		mProgressDialog.setProgress(progress);
		mProgressDialog.setTitle(message);

	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		if (uploadTamizajesTask != null && uploadTamizajesTask.getStatus() != AsyncTask.Status.FINISHED)
			return uploadTamizajesTask;

		return null;
	}

	@Override
	protected void onDestroy() {
		if (uploadTamizajesTask != null) {
			uploadTamizajesTask.setUploadListener(null);
			if (uploadTamizajesTask.getStatus() == AsyncTask.Status.FINISHED) {
				uploadTamizajesTask.cancel(true);
			}
		}

		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (uploadTamizajesTask != null) {
			uploadTamizajesTask.setUploadListener(this);
		}
	}

	@Override
	protected void onPause() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		super.onPause();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == PROGRESS_DIALOG) {
			mProgressDialog = createUploadDialog();
			return mProgressDialog;
		}
		return null;
	}

	private ProgressDialog createUploadDialog() {

		ProgressDialog dialog = new ProgressDialog(this);
		DialogInterface.OnClickListener loadingButtonListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				uploadTamizajesTask.setUploadListener(null);
				uploadTamizajesTask.cancel(true);
				Intent intent = new Intent();
				intent.putExtra("resultado", getString(R.string.error));
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		};
		dialog.setTitle(getString(R.string.loading));
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setIndeterminate(false);
		dialog.setCancelable(false);
		dialog.setButton(getString(R.string.cancel),
				loadingButtonListener);
		return dialog;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {

		if (id == PROGRESS_DIALOG) {
			ProgressDialog progress = (ProgressDialog) dialog;
			progress.setTitle(getString(R.string.loading));
			progress.setProgress(0);
		}
	}

	private void uploadAll() {
		uploadTamizajesTask =  new UploadTamizajesTask(this.getApplicationContext());
		uploadTamizajesTask.setUploadListener(UploadTamizajesActivity.this);
		uploadTamizajesTask.execute(url,username,password);
		showDialog(PROGRESS_DIALOG);
	} 

}
