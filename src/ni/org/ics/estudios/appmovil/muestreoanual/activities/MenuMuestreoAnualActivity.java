package ni.org.ics.estudios.appmovil.muestreoanual.activities;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.MenuPrincipalAdapter;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

public class MenuMuestreoAnualActivity extends ListActivity {

    private static final int UPDATE_EQUIPO = 1;
    private static final int UPDATE_SERVER = 2;

    private static final int EXIT = 1;
    private static final int DOWNLOAD = 2;
    private static final int UPLOAD = 3;
    private static final int UPLOAD_DOCS = 6;
    private static final int VERIFY = 4;
    private static final int DOWNLOAD_USER = 5;
    private static final int DOWNLOAD_PROC = 7;
    public static final int BARCODE_CAPTURE = 22;

    private static final String EXIT_SHOWING = "exitshowing";
    private static final String SEND_SHOWING = "sendshowing";
    private static final String RECEIVE_SHOWING = "receiveshowing";
    private static final String ALERT_SHOWING = "alertshowing";
    private boolean mExitShowing;
    private boolean mSendShowing;
    private boolean mReceiveShowing;
    private boolean mAlertShowing;

    private AlertDialog alertDialog;
    private String username;
    private SharedPreferences settings;

    private EstudiosAdapter cae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pdcs);
        String[] menu_main = getResources().getStringArray(R.array.menu_main_ma);
        setListAdapter(new MenuPrincipalAdapter(this, R.layout.menu_item, menu_main));
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        cae = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EXIT_SHOWING)) {
                mExitShowing = savedInstanceState.getBoolean(EXIT_SHOWING, false);
            }
            if (savedInstanceState.containsKey(SEND_SHOWING)) {
                mSendShowing = savedInstanceState.getBoolean(SEND_SHOWING, false);
            }
            if (savedInstanceState.containsKey(RECEIVE_SHOWING)) {
                mReceiveShowing = savedInstanceState.getBoolean(RECEIVE_SHOWING, false);
            }
            if (savedInstanceState.containsKey(ALERT_SHOWING)) {
                mAlertShowing = savedInstanceState.getBoolean(ALERT_SHOWING, false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pdcs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MENU_DESCARGA:
                //CohorteAdapterGetObjects cat = new CohorteAdapterGetObjects();
                //cae.open();
                //Boolean esSuperV = cat.buscarRol(username, "ROLE_SUPER");
                //cae.close();
                //if (esSuperV){
                    cae.open();
                    Boolean verificar = cae.verificarData();
                    cae.close();
                    if(verificar){
                        createDialog(VERIFY);
                    }
                    else{
                        createDialog(DOWNLOAD);
                    }
                /*}
                else{
                    showToast(getApplicationContext().getString(R.string.perm_error), 1);
                }*/
                return true;
            case R.id.MENU_CARGA:
                createDialog(UPLOAD);
                return true;
            /*case R.id.MENU_CARGA_DOCS:
                createDialog(UPLOAD_DOCS);
                return true;*/
            case R.id.MENU_EXIT:
                createDialog(EXIT);
                return true;
            case R.id.MENU_DOWN_PROC:
                createDialog(DOWNLOAD_PROC);
                return true;
            case R.id.MENU_DOWN_USER:
                createDialog(DOWNLOAD_USER);
                return true;
            case R.id.MENU_DOWN_PART:
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
                return true;
            case R.id.MENU_PREFERENCES:
                Intent ig = new Intent(this, PreferencesActivity.class);
                startActivity(ig);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position,
                                   long id) {
        // Opcion de menu seleccionada
        Intent i;
        switch(position){
            case 0:
                i = new Intent(getApplicationContext(),
                        SelecPartActivity.class);
                i.putExtra(Constants.MENU_INFO, true);
                startActivity(i);
                break;
            case 1:
                i = new Intent(getApplicationContext(),
                        MenuReviewActivity.class);
                i.putExtra(Constants.MENU_INFO, true);
                startActivity(i);
                break;
            case 2:
                //CohorteAdapterGetObjects cat = new CohorteAdapterGetObjects();
                cae.open();
                Boolean esSuperV = cae.buscarRol(username, "ROLE_SUPER");
                cae.close();
                if (esSuperV){
                    i = new Intent(getApplicationContext(),
                            MenuSupervisorActivity.class);
                    startActivity(i);
                }
                else{
                    showToast(getApplicationContext().getString(R.string.perm_error), 1);
                }
                break;
            case 3:
                i = new Intent(getApplicationContext(),
                        NewEsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case 4:
                i = new Intent(getApplicationContext(),
                        NewTamizajeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            default:
                String s = (String) getListAdapter().getItem(position);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXIT_SHOWING, mExitShowing);
        outState.putBoolean(SEND_SHOWING, mSendShowing);
        outState.putBoolean(RECEIVE_SHOWING, mReceiveShowing);
        outState.putBoolean(ALERT_SHOWING, mAlertShowing);
    }


    @Override
    protected void onResume() {
        if (mExitShowing) {
            createDialog(EXIT);
        }
        if (mSendShowing) {
            createDialog(UPLOAD);
        }
        if (mReceiveShowing) {
            createDialog(DOWNLOAD);
        }
        if (mAlertShowing) {
            createDialog(VERIFY);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
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
                /*builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.exiting));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish app
                        dialog.dismiss();
                        mExitShowing=false;
                        finish();
                    }

                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                        mExitShowing=false;
                    }
                });
                mExitShowing=true;*/
                mExitShowing=false;
                finish();
                break;
            case DOWNLOAD:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.downloading));
                builder.setIcon(android.R.drawable.ic_menu_help);
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mReceiveShowing=false;
                        Intent ie = new Intent(getApplicationContext(), DownloadAllActivity.class);
                        startActivityForResult(ie, UPDATE_EQUIPO);
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                        mReceiveShowing=false;
                    }
                });
                mReceiveShowing=true;
                break;
            case DOWNLOAD_USER:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.downloading));
                builder.setIcon(android.R.drawable.ic_menu_help);
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mReceiveShowing=false;
                        Intent ie = new Intent(getApplicationContext(), DownloadUsersActivity.class);
                        startActivityForResult(ie, UPDATE_EQUIPO);
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                        mReceiveShowing=false;
                    }
                });
                mReceiveShowing=true;
                break;
            case VERIFY:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.data_not_sent));
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mAlertShowing=false;
                        Intent ie = new Intent(getApplicationContext(), DownloadAllActivity.class);
                        startActivityForResult(ie, UPDATE_EQUIPO);
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                        mAlertShowing=false;
                    }
                });
                mAlertShowing=true;
                break;
            case UPLOAD:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.uploading));
                builder.setIcon(android.R.drawable.ic_menu_help);
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mSendShowing=false;
                        Intent ie = new Intent(getApplicationContext(), UploadAllActivity.class);
                        startActivityForResult(ie, UPDATE_SERVER);
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                        mSendShowing=false;
                    }
                });
                mSendShowing=true;
                break;
            case UPLOAD_DOCS:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.uploading_docs));
                builder.setIcon(android.R.drawable.ic_menu_help);
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mSendShowing=false;
                        Intent ie = new Intent(getApplicationContext(), UploadDocsActivity.class);
                        startActivityForResult(ie, UPDATE_SERVER);
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                        mSendShowing=false;
                    }
                });
                mSendShowing=true;
                break;
            case DOWNLOAD_PROC:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.downloadingproc));
                builder.setIcon(android.R.drawable.ic_menu_help);
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mReceiveShowing=false;
                        Intent ie = new Intent(getApplicationContext(), DownloadProcessesActivity.class);
                        startActivityForResult(ie, UPDATE_EQUIPO);
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                        mReceiveShowing=false;
                    }
                });
                mReceiveShowing=true;
                break;
            default:
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_CANCELED) {
            if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getApplicationContext().getString(R.string.error));
                builder.setIcon(R.drawable.ic_error);
                builder.setMessage(intent.getStringExtra("resultado"))
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }
        }
        else{
            if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getApplicationContext().getString(R.string.confirm));
                builder.setIcon(R.drawable.ic_ok);
                builder.setMessage(getApplicationContext().getString(R.string.success))
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            if(requestCode == BARCODE_CAPTURE){
                Integer codigoScanned =0;
                if (requestCode == BARCODE_CAPTURE && intent != null) {
                    String sb = intent.getStringExtra("SCAN_RESULT");
                    if (sb != null && sb.length() > 0) {

                        try{
                            codigoScanned = Integer.parseInt(sb);
                            Intent ie = new Intent(getApplicationContext(), DownloadParticipanteActivity.class);
                            ie.putExtra(ConstantsDB.CODIGO, codigoScanned);
                            startActivityForResult(ie, UPDATE_EQUIPO);
                        }
                        catch(Exception e){
                            showToast(getString(R.string.scan_error),1);
                            return;
                        }
                    }
                }
            }
            return;
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
