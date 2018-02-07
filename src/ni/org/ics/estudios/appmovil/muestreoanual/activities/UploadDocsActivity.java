
package ni.org.ics.estudios.appmovil.muestreoanual.activities;


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
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.listeners.UploadListener;
import ni.org.ics.estudios.appmovil.muestreoanual.tasks.UploadDocsTask;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.FileUtils;

@SuppressWarnings("deprecation")
public class UploadDocsActivity extends Activity implements UploadListener {

	protected static final String TAG = UploadDocsActivity.class.getSimpleName();

	private String username;
	private String password;
	private String url;
	private SharedPreferences settings;
	private UploadDocsTask uploadDocsTask;

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

/*
		CohorteAdapter ca = new CohorteAdapter();
		ca.open();
		String pass = ca.buscarPassword(username);
		ca.close();

		password =
				pass;*/

        password =
                ((MyIcsApplication) this.getApplication()).getPassApp();

		// get the task if we've changed orientations. If it's null it's a new upload.
		uploadDocsTask = (UploadDocsTask) getLastNonConfigurationInstance();
		if (uploadDocsTask == null) {
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
		uploadDocsTask = null;
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
		if (uploadDocsTask != null && uploadDocsTask.getStatus() != AsyncTask.Status.FINISHED)
			return uploadDocsTask;

		return null;
	}

	@Override
	protected void onDestroy() {
		if (uploadDocsTask != null) {
			uploadDocsTask.setUploadListener(null);
			if (uploadDocsTask.getStatus() == AsyncTask.Status.FINISHED) {
				uploadDocsTask.cancel(true);
			}
		}

		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (uploadDocsTask != null) {
			uploadDocsTask.setUploadListener(this);
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
				uploadDocsTask.setUploadListener(null);
				uploadDocsTask.cancel(true);
				Intent intent = new Intent();
				intent.putExtra("resultado", getString(R.string.err_cancel));
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
			progress.setTitle(getString(R.string.uploading));
			progress.setProgress(0);
		}
	}

	private void uploadAll() {
		uploadDocsTask =  new UploadDocsTask(this.getApplicationContext());
		uploadDocsTask.setUploadListener(UploadDocsActivity.this);
		uploadDocsTask.execute(url,username,password);
		showDialog(PROGRESS_DIALOG);
	} 

}
