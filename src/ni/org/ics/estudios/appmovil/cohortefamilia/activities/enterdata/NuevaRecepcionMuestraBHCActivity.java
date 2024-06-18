package ni.org.ics.estudios.appmovil.cohortefamilia.activities.enterdata;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.*;
import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.RecepcionMuestra;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;
import ni.org.ics.estudios.appmovil.utils.zbar.SimpleScannerActivity;
import ni.org.ics.estudios.appmovil.utils.zbar.ZBarConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Miguel Salinas on 6/7/2017.
 * V1.0
 */
public class NuevaRecepcionMuestraBHCActivity extends AbstractAsyncActivity {

    protected static final String TAG = NuevaRecepcionMuestraBHCActivity.class.getSimpleName();
    private Integer codigo;
    private Double volumen;
    private String paxgeneText;
    private String observacion;
    private String lugarText;
    private ImageButton mBarcodeButton;
    private EditText editCodigo;
    private EditText editVolumen;
    private EditText editObs;
    private TextView labelVolumen;
    private Spinner paxgeneS;
    private Spinner lugar;
    private Date todayWithZeroTime = null;
    private String username;
    private SharedPreferences settings;
    private EstudiosAdapter estudiosAdapter;
    private DeviceInfo infoMovil;

    public static final int BARCODE_CAPTURE = 2;
    private static final int MENU_VIEW = Menu.FIRST + 2;

    private AlertDialog mAlertDialog;
    private boolean mAlertShowing;
    private boolean mAlertExitShowing;
    private static final String ALERT_SHOWING = "alertshowing";
    private static final String ALERT_EXIT_SHOWING = "alertexitshowing";
    private static final String KEEP_CODIGO = "keepcodigo";

    private static final int ZBAR_QR_SCANNER_REQUEST = 1;

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
        infoMovil = new DeviceInfo(NuevaRecepcionMuestraBHCActivity.this);
        setContentView(R.layout.recepcionbhc);
        editCodigo = (EditText) findViewById(R.id.codigo);
        editCodigo.setFocusable(true);
        editCodigo.setEnabled(false);
        editCodigo.requestFocus();
        mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
        mBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent("com.google.zxing.client.android.SCAN");
                Intent intent = new Intent(getApplicationContext(), SimpleScannerActivity.class);
                try {
                    //startActivityForResult(i, BARCODE_CAPTURE);
                    startActivityForResult(intent, ZBAR_QR_SCANNER_REQUEST);
                } catch (ActivityNotFoundException e) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            getString(R.string.error, R.string.barcode_error),
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    t.show();
                }

            }
        });

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        try {
            todayWithZeroTime =formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,null);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ALERT_SHOWING)) {
                mAlertShowing = savedInstanceState.getBoolean(ALERT_SHOWING, false);
            }
            if (savedInstanceState.containsKey(ALERT_EXIT_SHOWING)) {
                mAlertExitShowing = savedInstanceState.getBoolean(ALERT_EXIT_SHOWING, false);
            }
            if (savedInstanceState.containsKey(KEEP_CODIGO)) {
                codigo = savedInstanceState.getInt(KEEP_CODIGO, -1);
            }
        }

        estudiosAdapter.open();
        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.select)+"..");
        list.addAll(fillCatalog("CHF_CAT_SINO"));
        List<String> listLugar = new ArrayList<String>();
        listLugar.add(getString(R.string.select)+"..");
        listLugar.addAll(fillCatalog("CAT_LUGAR_RECEP"));
        estudiosAdapter.close();

        paxgeneS = (Spinner) findViewById(R.id.paxgene);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        paxgeneS.setAdapter(dataAdapter);

        labelVolumen = ((TextView) findViewById(R.id.label_volumen));
        editVolumen = ((EditText) findViewById(R.id.volumen));
        editVolumen.setFocusableInTouchMode(true);
        editVolumen.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus == false)
                {
                    try{
                        volumen = Double.valueOf(editVolumen.getText().toString());
                    }
                    catch(Exception e){
                        return;
                    }

                    if (!(volumen>=0.1 && volumen<=2)){
                        labelVolumen.setText(getString(R.string.error_volumen));
                        labelVolumen.setTextColor(Color.RED);
                    }
                    else{
                        labelVolumen.setText(getString(R.string.volumenR));
                        labelVolumen.setTextColor(Color.BLACK);
                    }
                }
            }
        });
        editObs = ((EditText) findViewById(R.id.obs));
        lugar = (Spinner) findViewById(R.id.lugar);
        ArrayAdapter<String> dataAdapterLugar = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listLugar);
        dataAdapterLugar.setDropDownViewResource(R.layout.spinner_item);
        lugar.setAdapter(dataAdapterLugar);
        final Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //captura entrada de la muestra
                editObs.requestFocus();
                try{
                    volumen = Double.valueOf(editVolumen.getText().toString());
                }
                catch(Exception e){
                    showToast(getString(R.string.error_volumen),1);
                    return;
                }
                observacion = editObs.getText().toString();
                lugarText = lugar.getSelectedItem().toString();
                paxgeneText = paxgeneS.getSelectedItem().toString();
                if(validarEntrada()){
                    new FetchDataTask().execute();
                }
            }

        });

        final Button endButton = (Button) findViewById(R.id.finish);
        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //finaliza la actividad
                createExitDialog();
            }

        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ALERT_SHOWING, mAlertShowing);
        outState.putBoolean(ALERT_EXIT_SHOWING, mAlertExitShowing);
        if (codigo!=null) outState.putInt(KEEP_CODIGO, codigo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.MENU_BACK:
                finish();
                return true;
            case R.id.MENU_HOME:
                i = new Intent(getApplicationContext(),
                        MainActivity.class);
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

        //if (requestCode == BARCODE_CAPTURE && intent != null) {
        if (requestCode == ZBAR_QR_SCANNER_REQUEST && intent != null) {
            //String sb = intent.getStringExtra("SCAN_RESULT");
            String sb = intent.getStringExtra(ZBarConstants.SCAN_RESULT);
            if (sb != null && sb.length() > 0) {
                try{
                    codigo = Integer.parseInt(sb);
                }
                catch(Exception e){
                    codigo = null;
                    editCodigo.setText(null);
                    showToast(getString(R.string.error_codigoMx),1);
                    return;
                }
            }
            if (codigo>0 && codigo <=10500){
                    editCodigo.setText(codigo.toString());
            }
            else
            {
                codigo = null;
                editCodigo.setText(null);
                showToast(getString(R.string.error_codigoMx),1);
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private boolean validarEntrada() {
        //Valida la entrada
        if (codigo == null){
            showToast(this.getString( R.string.error_codigo),1);
            return false;
        }
        else if (volumen == null){
            showToast(this.getString( R.string.error_volumen),1);
            return false;
        }
        else if (lugarText.matches("Seleccionar..")){
            showToast(this.getString( R.string.error_lugar),1);
            return false;
        }
        else if (!(volumen>=0.1 && volumen<=2)){
            showToast(this.getString( R.string.error_volumen),1);
            return false;
        }
        else if (!(codigo>0 && codigo<=10500)){
            showToast(this.getString( R.string.error_codigo),1);
            return false;
        }
        else{
            return true;
        }
    }

    private void reiniciar(){
        editCodigo.setText(null);
        codigo = null;
        editVolumen.setText(null);
        paxgeneS.setSelection(0,true);
        volumen = null;
        lugarText = null;
        editObs.setText(null);
        observacion =null;
        paxgeneText = null;
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

    private List<String> fillCatalog(String codigoCatalogo){
        List<String> catalogo = new ArrayList<String>();
        List<MessageResource> mCatalogo = estudiosAdapter.getMessageResources(CatalogosDBConstants.catRoot + "='"+codigoCatalogo+"'", CatalogosDBConstants.order);
        int index = 0;
        for (MessageResource message: mCatalogo){
            catalogo.add(message.getSpanish());
            index++;
        }
        return catalogo;
    }

    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
    }

    @Override
    public void onBackPressed (){
        createExitDialog();
    }
    @SuppressWarnings("deprecation")
    private void createExitDialog() {
        // Pregunta si desea salir o no
        mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.setIcon(android.R.drawable.ic_dialog_info);
        mAlertDialog.setTitle(getString(R.string.confirm));
        mAlertDialog.setMessage(getString(R.string.exit));

        DialogInterface.OnClickListener uploadDialogListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case DialogInterface.BUTTON1: // yes
                        mAlertExitShowing = false;
                        finish();
                    case DialogInterface.BUTTON2: // no
                        mAlertExitShowing = false;
                        dialog.dismiss();
                        break;
                }
            }
        };
        mAlertDialog.setCancelable(false);
        mAlertDialog.setButton(getString(R.string.yes), uploadDialogListener);
        mAlertDialog.setButton2(getString(R.string.no), uploadDialogListener);
        mAlertExitShowing = true;
        mAlertDialog.show();
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {
        protected String message;
        protected int codeMessage;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                estudiosAdapter.open();
                RecepcionMuestra recepcionMuestra = new RecepcionMuestra();
                recepcionMuestra.setCodigo(infoMovil.getId());
                recepcionMuestra.setFechaRecepcion(todayWithZeroTime);
                recepcionMuestra.setCodigoMx(String.valueOf(codigo));
                recepcionMuestra.setVolumen(volumen);
                if (tieneValor(paxgeneText)){
                    MessageResource msPaxgene = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + paxgeneText + "' and "
                            + CatalogosDBConstants.catRoot + "='CHF_CAT_SINO'", null);
                    if (msPaxgene != null) recepcionMuestra.setPaxgene(msPaxgene.getCatKey());
                }
                recepcionMuestra.setObservacion(observacion);
                if (tieneValor(lugarText)){
                    MessageResource msLugar = estudiosAdapter.getMessageResource(CatalogosDBConstants.spanish + "='" + lugarText + "' and "
                            + CatalogosDBConstants.catRoot + "='CAT_LUGAR_RECEP'", null);
                    if (msLugar != null) recepcionMuestra.setLugar(msLugar.getCatKey());
                }
                recepcionMuestra.setRecordUser(username);
                recepcionMuestra.setEstado(Constants.STATUS_NOT_SUBMITTED.charAt(0));
                recepcionMuestra.setRecordDate(new Date());
                recepcionMuestra.setPasive('0');
                recepcionMuestra.setDeviceid(infoMovil.getDeviceId());
                recepcionMuestra.setTipoMuestra(Constants.CODIGO_TIPO_SANGRE); //Sangre
                recepcionMuestra.setTubo(Constants.CODIGO_TUBO_BHC); //BHC
                recepcionMuestra.setProposito(Constants.CODIGO_PROPOSITO_MA);//Muestreo anual

                estudiosAdapter.crearRecepcionMuestra(recepcionMuestra);
                message = getString(R.string.success);
                codeMessage = 0;
            }catch (Exception ex){
                message = ex.getMessage();
                codeMessage = 1;
                return "error";
            }finally {
                estudiosAdapter.close();
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            dismissProgressDialog();
            if (codeMessage == 0) reiniciar();
            showToast(message, codeMessage);
        }

    }
}
