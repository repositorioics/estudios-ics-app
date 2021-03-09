/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.*;
import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.RecepcionSero;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.RecepcionSeroId;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.DeviceInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author William Aviles
 */
public class RecepcionSeroActivity extends AbstractAsyncActivity{

	protected static final String TAG = RecepcionSeroActivity.class.getSimpleName();
	private Integer codigo;
	private Double volumen;
	private String observacion;
	private String lugarText;
	private ImageButton mBarcodeButton;
	private EditText editCodigo;
	private EditText editVolumen;
	private EditText editObs;
	private TextView labelVolumen;
	private Spinner lugar;
    private Spinner mMetodoView;
    private Date todayWithZeroTime = null;
	private String username;
	private SharedPreferences settings;

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

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recepcionsero_pdcs);
		editCodigo = (EditText) findViewById(R.id.codigo);
		/*editCodigo.setFocusable(true);
		editCodigo.setEnabled(false);
		editCodigo.requestFocus();*/
		mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
		mBarcodeButton.setOnClickListener(new OnClickListener() {
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
			todayWithZeroTime =formatter.parse(formatter.format(today));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        infoMovil = new DeviceInfo(RecepcionSeroActivity.this);
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


        editCodigo.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus == false)
                {
                    try{
                        if (!editCodigo.getText().toString().isEmpty())
                            codigo = Integer.valueOf(editCodigo.getText().toString());
                    }
                    catch(Exception e){
                        codigo = null;
                        editCodigo.setText(null);
                        showToast("Código Inválido!!!!",1);
                        return;
                    }

                    if (codigo!=null && (codigo<=0 || codigo >15000)){
                        editCodigo.setText(null);
                        codigo = null;
                        showToast("Código Inválido!!!!",1);
                    } else {
						if (!validarMaximoRegistrosPermitidos()) {
							editCodigo.setText(null);
							codigo = null;
							showToast("Ya ingresó dos registros para este código!!!!",1);
						}
					}
                }
            }
        });

		labelVolumen = ((TextView) findViewById(R.id.label_volumen));
		editVolumen = ((EditText) findViewById(R.id.volumen));
		editVolumen.setFocusableInTouchMode(true);
		editVolumen.setOnFocusChangeListener(new OnFocusChangeListener()
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
					//volumen permitido hasta 16ml. 20/10/2020. Solicitado para Muestreo covid19
					if (!(volumen>=0.5 && volumen<=16)){
						labelVolumen.setText("Volumen Inválido");
						labelVolumen.setTextColor(Color.RED);
					}
					else{
						labelVolumen.setText("Volumen");
						labelVolumen.setTextColor(Color.BLACK);
					}
				}
			}
		});
		editObs = ((EditText) findViewById(R.id.obs));
		lugar = (Spinner) findViewById(R.id.lugar);
		list = new ArrayList<String>();
		list.add("Seleccionar..");
		list.add("Auditorio");
		list.add("Terreno");
		ArrayAdapter<String> dataAdapterL = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
        dataAdapterL.setDropDownViewResource(R.layout.spinner_item);
		lugar.setAdapter(dataAdapterL);

		final Button saveButton = (Button) findViewById(R.id.save);
		saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//captura entrada de la muestra
                editCodigo.requestFocus();
				try{
					volumen = Double.valueOf(editVolumen.getText().toString());
				}
				catch(Exception e){
					showToast("No ingresó volumen válido!!!",1);
					return;
				}
				observacion = editObs.getText().toString();
				lugarText = lugar.getSelectedItem().toString();
				if(validarEntrada()){
					RecepcionSero tubo = new RecepcionSero();
					tubo.setCodigo(codigo);
                    tubo.setFechaRecSero(todayWithZeroTime);
					tubo.setId(infoMovil.getId());
					tubo.setVolumen(volumen);
					tubo.setObservacion(observacion);
					tubo.setLugar(lugarText);
					tubo.setUsername(username);
					tubo.setEstado(Constants.STATUS_NOT_SUBMITTED);
					tubo.setFecreg(new Date());
                    try {
                        ca.open();
                        ca.crearRecepcionSero(tubo);
                        showToast("Registro Guardado",0);
                        reiniciar();
                    }catch (Exception ex){
                        ex.printStackTrace();
                        showToast("Error al guardar registro. "+ex.getLocalizedMessage(),0);
                    }finally {
                        ca.close();
                    }
				}
			}

		});

		final Button endButton = (Button) findViewById(R.id.finish);
		endButton.setOnClickListener(new OnClickListener() {
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
					MenuMuestreoAnualActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		case MENU_VIEW:
			i = new Intent(getApplicationContext(),
					ListRojosActivity.class);
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
			if (codigo !=null & codigo>0 && codigo <=15000){
				if (!validarMaximoRegistrosPermitidos()) {
					showToast("Ya ingresó dos registros para este código!!!!",1);
				} else {
					editCodigo.setText(codigo.toString());
				}
			}
			else
			{
				showToast("Lectura Inválida!!!!",1);
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	private boolean validarEntrada() {
		//Valida la entrada 
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
		}//volumen permitido hasta 16ml. 20/10/2020. Solicitado para Muestreo covid19
		else if (!(volumen>=0.5 && volumen<=16)){
			showToast(this.getString( R.string.error_volumen),1);
			return false;
		}
		else if (!(codigo>0 && codigo<=15000)){
			showToast(this.getString( R.string.error_codigo),1);
			return false;
		}
		else{
			return true;
		}
	}

	/****
	 * Valida si ya re registraron la cantidad de registros permitidos por dia para un participante.
	 * El máximo son dos registros
	 * @return true no se ha llegado al máximo, false ya se llegó al máximo
	 */
	private boolean validarMaximoRegistrosPermitidos(){
		boolean permitido = true;
		ca.open();
		Cursor c  = null;
		c = ca.buscarRecepcionSero(codigo, todayWithZeroTime);
		if (c != null && c.getCount() > 1) {
			permitido = false;
		}
		ca.close();
		return permitido;
	}

	private void reiniciar(){
		editCodigo.setText(null);
		codigo = null;
		editVolumen.setText(null);
		volumen = null;
		lugarText = null;
		editObs.setText(null);
		observacion =null;
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

}
