package ni.org.ics.estudios.appmovil.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class DescargarApkActivity extends Activity {
    public ProgressDialog PD_CREATE;
    private Context CONTEXT;
    private String strUrlContext;
    private SharedPreferences settings;
    /*public static String URL_DOWNLOAD_APK = "";

    static {
        try {
            Properties props = new Properties();
            InputStream inputStream = new FileInputStream("/sdcard/icsApk/config/config.properties");
            props.load(inputStream);
            URL_DOWNLOAD_APK = props.getProperty("DOWNLOAD_ICS_APP_APK");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.CONTEXT = this;
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        strUrlContext =
                settings.getString(PreferencesActivity.KEY_SERVER_URL, this.getString(R.string.default_server_url));
                //settings.getString(PreferencesActivity.KEY_SERVER_URL, URL_DOWNLOAD_APK);
        /*Intent i;
        PackageManager manager = getPackageManager();
        try {
            i = manager.getLaunchIntentForPackage("ni.org.ics.estudios.appmovil");
            if (i == null)
                throw new PackageManager.NameNotFoundException();
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {*/
            UpdateApk downloadAndInstall = new UpdateApk();
            //progress.setCancelable(false);
            //progress.setMessage("Downloading...");
            downloadAndInstall.setContext(getApplicationContext());
            downloadAndInstall.execute(getResources().getString(R.string.update_app_preferences));
        //}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class UpdateApk extends AsyncTask<String,Void,Void> {
        int status = 0;

        private Context context;
        public void setContext(Context context){
            this.context = context;
        }

        public void onPreExecute() {
            PD_CREATE = new ProgressDialog(CONTEXT);
            PD_CREATE.setTitle(getResources().getString(R.string.downloadingApp));
            PD_CREATE.setMessage(getResources().getString(R.string.pleasewait));
            PD_CREATE.setCancelable(false);
            PD_CREATE.setIndeterminate(true);
            PD_CREATE.show();
        }

        @Override
        protected Void doInBackground(String... arg0) {
            try {
                URL url = new URL(strUrlContext+getString(R.string.url_apk));
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.connect();

                String sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
                String fileName = context.getString(R.string.apk_name);
                sdcard += fileName;
                final Uri uri = Uri.parse("file://" + sdcard);

                File myDir = new File(sdcard);
                myDir.mkdirs();
                if(myDir.exists()){
                    myDir.delete();
                }
                FileOutputStream fos = new FileOutputStream(myDir);

                InputStream is = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                }
                fos.flush();
                fos.close();
                is.close();

                //Llamando a la funcion que instala el APK
                ApkInstaller.installApplication(CONTEXT);

            } catch (FileNotFoundException fnfe) {
                status = 1;
                Log.e("File", "FileNotFoundException! " + fnfe);
            } catch (ConnectException fnfe) {
                status = 2;
                Log.e("Error", "ConnectException! " + fnfe);
            }
            catch(Exception e)
            {
                Log.e("UpdateAPP", "Exception " + e);
            }
            return null;
        }

        public void onPostExecute(Void unused) {
            //progressDialog.dismiss();
            PD_CREATE.dismiss();
            if(status == 1)
                Toast.makeText(CONTEXT,"Apk no disponible",Toast.LENGTH_LONG).show();

            if(status == 2)
                Toast.makeText(CONTEXT,"No se pudo conectar con el servidor",Toast.LENGTH_LONG).show();

            finish();
        }
    }
}
