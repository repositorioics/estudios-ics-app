package ni.org.ics.estudios.appmovil.muestreoanual.activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.*;
import android.widget.*;
import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.*;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecepcionPbmcActivity extends AbstractAsyncActivity  {
    protected static final String TAG = RecepcionPbmcActivity.class.getSimpleName();
    private Integer codigo;
    private Double volumen;
    private String observacion;
    private String lugarText;
    private ImageButton mBarcodeButton;
    private EditText editCodigo;
    private CheckBox chkRojoAdicional;
    private Boolean rojoAdicional;
    private EditText editVolumen;
    private EditText editObs;
    private TextView labelVolumen;
    private Spinner lugar;
    private Spinner mMetodoView;
    private Date todayWithZeroTime = null;
    private String username;
    private SharedPreferences settings;

    private EditText editVolumenRojoAdicional;
    private Double volumenRojoAdicional;
    private TextView labelVolumenRojoAdicional;

    public static final int BARCODE_CAPTURE = 2;
    private static final int MENU_VIEW = Menu.FIRST + 2;

    private AlertDialog mAlertDialog;
    private boolean mAlertShowing;
    private static final String ALERT_SHOWING = "alertshowing";
    private DeviceInfo infoMovil;

    private boolean mAlertExitShowing;
    private static final String ALERT_EXIT_SHOWING = "alertexitshowing";
    private static final String KEEP_CODIGO = "keepcodigo";

    private int opcionTipoEntrada=-1;
    private EstudiosAdapter ca = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recepcionpbmc);

        editCodigo = (EditText) findViewById(R.id.codigo_pbmc);

        mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button_pbmc);
        mBarcodeButton.setOnClickListener(new View.OnClickListener() {
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        try {
            todayWithZeroTime = formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        infoMovil = new DeviceInfo(RecepcionPbmcActivity.this);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        ca = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);

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

        mMetodoView = (Spinner) findViewById(R.id.metodo_busqueda_pbmc);
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
                editCodigo.setText("");
                editCodigo.setHint(getString(R.string.code));
                opcionTipoEntrada = position;
                if (position==0){
                    editCodigo.setEnabled(false);
                    mBarcodeButton.setVisibility(View.VISIBLE);
                }
                else{
                    editCodigo.setFocusable(true);
                    editCodigo.setEnabled(true);
                    editCodigo.setFocusableInTouchMode(true);
                    mBarcodeButton.setVisibility(View.GONE);
                    editCodigo.requestFocus();
                    if (position==1){
                        editCodigo.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        editCodigo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    try{
                        if (!editCodigo.getText().toString().isEmpty())
                            codigo = Integer.valueOf(editCodigo.getText().toString());
                    }
                    catch(Exception e) {
                        codigo = null;
                        editCodigo.setText(null);
                        showToast("Código Inválido!!!!",1);
                        return;
                    }
                        //MA 2024 rango hasta 19999
                    if (codigo != null && codigo > 0 && codigo <= 19999) {
                        DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                        String date = null;
                        date = targetFormat.format(new Date());
                        ca.open();
                        RecepcionPbmc c  = null;
                        c = ca.buscarRecepcionPbmc(codigo, date); //Buscar las recepciones de pbmc
                        if (c != null) {
                            editCodigo.setText(null);
                            codigo = null;
                            showToast("Ya ingresó este código!!!!",1);
                        }
                        ca.close();
                    } /*else {
                        editCodigo.setText(null);
                        codigo = null;
                        showToast("Código Inválido!!!!",1);
                    }*/
                }
            }
        });

        chkRojoAdicional = (CheckBox) findViewById(R.id.checkRojoAdicional);
        chkRojoAdicional.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked) {
                    if (codigo == null) {
                        chkRojoAdicional.setChecked(false);
                        TextView lblRojoA = ((TextView) findViewById(R.id.label_volumen_rojo_adicional));
                        EditText edtxVolRojoA = ((EditText) findViewById(R.id.volumen_rojo_adicional));
                        edtxVolRojoA.setText("");
                        lblRojoA.setVisibility(View.GONE);
                        edtxVolRojoA.setVisibility(View.GONE);
                        showToast("Código inválido",1);
                        return;
                    } else {
                        TextView lblRojoA = ((TextView) findViewById(R.id.label_volumen_rojo_adicional));
                        EditText edtxVolRojoA = ((EditText) findViewById(R.id.volumen_rojo_adicional));
                        lblRojoA.setVisibility(View.VISIBLE);
                        edtxVolRojoA.setVisibility(View.VISIBLE);
                    }
                } else {
                    TextView lblRojoA = ((TextView) findViewById(R.id.label_volumen_rojo_adicional));
                    EditText edtxVolRojoA = ((EditText) findViewById(R.id.volumen_rojo_adicional));
                    edtxVolRojoA.setText("");
                    lblRojoA.setVisibility(View.GONE);
                    edtxVolRojoA.setVisibility(View.GONE);
                }
            }
        });

        labelVolumen = ((TextView) findViewById(R.id.label_volumen_pbmc));
        editVolumen = ((EditText) findViewById(R.id.volumen_pbmc));
        editVolumen.setFocusableInTouchMode(true);
        editVolumen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    try {
                        volumen = Double.valueOf(editVolumen.getText().toString());
                    }
                    catch(Exception e) {
                        return;
                    }
                    if (volumen < 0.5 || volumen > 12) {
                        labelVolumen.setText("Volumen Inválido");
                        labelVolumen.setTextColor(Color.RED);
                    } else {
                        labelVolumen.setText("Volumen");
                        labelVolumen.setTextColor(Color.BLACK);
                    }
                }
            }
        });

        labelVolumenRojoAdicional = ((TextView) findViewById(R.id.label_volumen_rojo_adicional));
        editVolumenRojoAdicional = ((EditText) findViewById(R.id.volumen_rojo_adicional));
        editVolumenRojoAdicional.setFocusableInTouchMode(true);
        editVolumenRojoAdicional.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    try {
                        volumenRojoAdicional = Double.valueOf(editVolumenRojoAdicional.getText().toString());
                    }
                    catch(Exception e) {
                        return;
                    }
                    if (volumenRojoAdicional < 0.5 || volumenRojoAdicional > 8.0) {
                        labelVolumenRojoAdicional.setText("Volumen Inválido");
                        labelVolumenRojoAdicional.setTextColor(Color.RED);
                    } else {
                        labelVolumenRojoAdicional.setText("Volumen");
                        labelVolumenRojoAdicional.setTextColor(Color.BLACK);
                    }
                }
            }
        });
        editObs = ((EditText) findViewById(R.id.obs_pbmc));
        lugar = (Spinner) findViewById(R.id.lugar_pbmc);
        List<String> list2 = new ArrayList<String>();
        list2.add("Seleccionar..");
        list2.add("Auditorio");
        list2.add("Terreno");
        ArrayAdapter<String> dataAdapterLugar = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapterLugar.setDropDownViewResource(R.layout.spinner_item);
        lugar.setAdapter(dataAdapterLugar);

        final Button saveButton = (Button) findViewById(R.id.save_pbmc);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //captura entrada de la muestra
                editCodigo.requestFocus();
                try{
                    volumen = Double.valueOf(editVolumen.getText().toString());
                }
                catch(Exception e){
                    showToast("No ingresó volumen",1);
                    return;
                }
                observacion = editObs.getText().toString();
                lugarText = lugar.getSelectedItem().toString();

                if (chkRojoAdicional.isChecked()) {
                    rojoAdicional=true;
                    //Validar que lleve el volumen del rojo adicional
                    try{
                        volumenRojoAdicional = Double.valueOf(editVolumenRojoAdicional.getText().toString());
                    }
                    catch(Exception e){
                        showToast("No ingresó volumen del rojo adicional",1);
                        return;
                    }
                } else {
                    rojoAdicional=false;
                }
                if(validarEntrada()) {
                    ca.open();
                    String estudio = ca.estudioParticipanteProcesosByCodigo(codigo);
                    ca.close();
                    DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    String date = null;
                    date = targetFormat.format(new Date());
                    RecepcionPbmc recepcionPbmc = new RecepcionPbmc();
                    recepcionPbmc.setCodigo(codigo);
                    recepcionPbmc.setFechaPbmc(date);
                    recepcionPbmc.setVolPbmc(volumen);
                    recepcionPbmc.setRojoAdicional(rojoAdicional);
                    recepcionPbmc.setVolRojoAdicional(volumenRojoAdicional);
                    recepcionPbmc.setLugar(lugarText);
                    recepcionPbmc.setObservacion(observacion);
                    recepcionPbmc.setUsername(username);
                    if (!estudio.equals("")) {
                        recepcionPbmc.setEstudio(estudio);
                    } else {
                        recepcionPbmc.setEstudio("");
                    }

                    recepcionPbmc.setEstado(Constants.STATUS_NOT_SUBMITTED);
                    Date strDate = new Date();
                    recepcionPbmc.setFechaCreacion(strDate.toString());
                    try {
                        ca.open();
                        ca.crearRecepcionPbmc(recepcionPbmc);
                        showToast("Registro Guardado", 0);
                        reiniciar();
                    } catch (Exception ex){
                        ex.printStackTrace();
                        showToast("Error al guardar registro. "+ex.getLocalizedMessage(),0);
                    } finally {
                        ca.close();
                    }
                }
            }
        });

        final Button endButton = (Button) findViewById(R.id.finish_pbmc);
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
        getMenuInflater().inflate(R.menu.general, menu);
        menu.add(0, MENU_VIEW, 0, getString(R.string.list))
                .setIcon(R.drawable.ic_btn_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.MENU_BACK:
                finish();
                return true;
            case R.id.MENU_HOME:
                i = new Intent(getApplicationContext(),
                        MenuSupervisorActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                return true;
            case MENU_VIEW:
                i = new Intent(getApplicationContext(),
                        ListPBMCsActivity.class); // CAMBIAR A LISTA PBMC
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        if (requestCode == BARCODE_CAPTURE && intent != null) {
            String sb = intent.getStringExtra("SCAN_RESULT");
            if (sb != null && sb.length() > 0) {
                try{
                    codigo = Integer.parseInt(sb);
                }
                catch(Exception e){
                    showToast("Lectura Inválida!!!!",1);
                    return;
                }
            }
            if (codigo != null && codigo>0 && codigo <= 19999){
                DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                String date = null;
                date = targetFormat.format(new Date());
                ca.open();
                RecepcionPbmc c  = null;
                c = ca.buscarRecepcionPbmc(codigo, date);
                if (c != null) {
                    showToast("Ya ingresó este código!!!!",1);
                }else{
                    editCodigo.setText(codigo.toString());
                }
                ca.close();
            }
            else
            {
                showToast("Lectura Inválida!!!!",1);
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onBackPressed (){
        createExitDialog();
    }

    private boolean validarEntrada() {
        //Valida la entrada
        Boolean result = true;
        EditText volumen_pbmc = (EditText) findViewById(R.id.volumen_pbmc);
        EditText volumen_rojo_adicional = (EditText) findViewById(R.id.volumen_rojo_adicional);
        String pbmc = volumen_pbmc.getText().toString();
        String rA = volumen_rojo_adicional.getText().toString();
        Double vPbmc = 0.0;
        Double vRadicional = 0.0;
        if (pbmc != null && pbmc.length() > 0) {
            vPbmc = Double.parseDouble(volumen_pbmc.getText().toString());
        }
        if (rA != null && rA.length() > 0) {
            vRadicional = Double.parseDouble(volumen_rojo_adicional.getText().toString());
        }
        if (codigo == null) {
            showToast(this.getString( R.string.error_codigo),1);
            result = false;
        } else if (volumen == null) {
            showToast(this.getString( R.string.error_volumen),1);
            result = false;
        } else if (lugarText.matches("Seleccionar..")) {
            showToast(this.getString( R.string.error_lugar),1);
            result = false;
        } else if (vPbmc < 0.5 || vPbmc > 12) {
            showToast(this.getString( R.string.error_volumen_pbmc),1);
            result = false;
        } else if (!(codigo>0 && codigo<=19999)) {
            showToast(this.getString( R.string.error_codigo),1);
            result = false;
        } else if (rojoAdicional && volumenRojoAdicional == null) {
            showToast(this.getString( R.string.volRojoAdicional),1);
            result = false;
        } else if (rA != null && rA.length() > 0) {
            if (vRadicional < 0.5 || vRadicional > 8.0) {
                showToast(this.getString( R.string.error_volumen_rojo_adicional),1);
                result = false;
            }
        } /*else {
            result = true;
        }*/
        return result;
    }

    private void reiniciar(){
        editCodigo.setText(null);
        codigo = null;
        editVolumen.setText(null);
        volumen = null;
        editVolumenRojoAdicional.setText(null);
        volumenRojoAdicional = null;
        lugarText = null;
        lugar.setSelection(0);
        editObs.setText(null);
        observacion =null;
        chkRojoAdicional.setChecked(false);
    }

    private void showToast(String mensaje, int numImage) {
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
}
