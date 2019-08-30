
package ni.org.ics.estudios.appmovil.influenzauo1.activities.server;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.influenzauo1.task.DownloadCasosUO1Task;
import ni.org.ics.estudios.appmovil.listeners.DownloadListener;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.FileUtils;

@SuppressWarnings("deprecation")
public class DownloadUO1Activity extends Activity implements DownloadListener{

	protected static final String TAG = DownloadUO1Activity.class.getSimpleName();

	private String username;
	private String password;
	private String url;
	private SharedPreferences settings;
	private DownloadCasosUO1Task downloadCasosUO1Task;
	private final static int PROGRESS_DIALOG = 1;
	private ProgressDialog mProgressDialog;
	

	// ***************************************
	// Metodos de la actividad
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(getString(R.string.app_name) + " > "
				+getString(R.string.main_3) + " > "
				+ getString(R.string.download));

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
		
		downloadCasosUO1Task = (DownloadCasosUO1Task) getLastNonConfigurationInstance();
		if (downloadCasosUO1Task == null) {
			downloadAllData();
		}
	}

	private void downloadAllData(){   
		if (downloadCasosUO1Task != null)
			return;
		showDialog(PROGRESS_DIALOG);
		downloadCasosUO1Task = new DownloadCasosUO1Task(this.getApplicationContext());
		downloadCasosUO1Task.setDownloadListener(DownloadUO1Activity.this);
		downloadCasosUO1Task.execute(url, username, password);
	}

	@Override
	public void downloadComplete(String result) {

		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}

		if (result != null) {
			Intent intent = new Intent();
			intent.putExtra("resultado", result);
			setResult(RESULT_CANCELED, intent);
		} else {
			setResult(RESULT_OK);
		}

		downloadCasosUO1Task = null;
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
		return downloadCasosUO1Task;
	}

	@Override
	protected void onDestroy() {
		if (downloadCasosUO1Task != null) {
			downloadCasosUO1Task.setDownloadListener(null);
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (downloadCasosUO1Task != null) {
			downloadCasosUO1Task.setDownloadListener(this);
		}

		if (mProgressDialog != null && !mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == PROGRESS_DIALOG) {
			mProgressDialog = createDownloadDialog();
			return mProgressDialog;
		}
		return null;
	}

	private ProgressDialog createDownloadDialog() {

		ProgressDialog dialog = new ProgressDialog(this);
		DialogInterface.OnClickListener loadingButtonListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				downloadCasosUO1Task.setDownloadListener(null);
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
			progress.setTitle(getString(R.string.loading));
			progress.setProgress(0);
		}
	}

}
