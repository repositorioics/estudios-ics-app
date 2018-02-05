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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import ni.org.ics.estudios.appmovil.AbstractAsyncActivity;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuMuestreoAnualActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.database.muestreoanual.CohorteAdapter;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.RazonNoData;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.RazonNoDataId;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author William Aviles
 */
public class RazonNoDataActivity extends AbstractAsyncActivity{

	protected static final String TAG = RazonNoDataActivity.class.getSimpleName();
	private Integer codigo;
	private Integer razon;
	private String oRazon="";
	private Spinner rndSpinner;
	private TextView labelOrazon;
	private EditText editOrazon;

	private SharedPreferences settings;
	private String username;

    private CohorteAdapter ca;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rnd);

		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        ca = new CohorteAdapter(this.getApplicationContext(),mPass,false,false);
		codigo = getIntent().getIntExtra(ConstantsDB.CODIGO,-1);
		rndSpinner = (Spinner) findViewById(R.id.razon);
		List<String> rnds = new ArrayList<String>();
		rnds.add("Seleccionar..");
		rnds.add("Acompañante desconoce informacion");
		rnds.add("No se pudo tomar todas las muestras");
		rnds.add("Abandono de Muestreo Anual");
		rnds.add("No desea dar informacion");
		rnds.add("Falta de tiempo por parte del acompañante");
		rnds.add("Solo revisaba información");
		rnds.add("Otra razón");
		ArrayAdapter<String> dataRndAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, rnds);
		dataRndAdapter.setDropDownViewResource(R.layout.spinner_item);
		rndSpinner.setAdapter(dataRndAdapter);
		
		rndSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				razon = arg2;
				if(arg2==7){
					editOrazon.setVisibility(View.VISIBLE);
					labelOrazon.setVisibility(View.VISIBLE);
				}
				else{
					editOrazon.setVisibility(View.GONE);
					labelOrazon.setVisibility(View.GONE);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		    
		}); 
		
		editOrazon = (EditText) findViewById(R.id.orazon);
		labelOrazon = (TextView) findViewById(R.id.label_orazon);
		editOrazon.setVisibility(View.GONE);
		labelOrazon.setVisibility(View.GONE);
		
		
		final Button saveButton = (Button) findViewById(R.id.save);
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//captura entrada de la muestra
				oRazon = editOrazon.getText().toString();
				if(validarEntrada()){
					RazonNoData rnd = new RazonNoData();
					RazonNoDataId rndId = new RazonNoDataId();
					rndId.setCodigo(codigo);
					rndId.setFechaRegistro(new Date());
					rnd.setRndId(rndId);
					rnd.setRazon(razon);
					rnd.setOtraRazon(oRazon);
					rnd.setUsername(username);
					rnd.setEstado(Constants.STATUS_NOT_SUBMITTED);
					//CohorteAdapter ca = new CohorteAdapter();
					ca.open();
					ca.crearRazonNoData(rnd);
					ca.close();
					Toast.makeText(getApplicationContext(), getString( R.string.success),Toast.LENGTH_LONG).show();
					Intent i;
					i = new Intent(getApplicationContext(),
                            MenuMuestreoAnualActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
			}

		});
	}

	private boolean validarEntrada() {
		if (rndSpinner.getSelectedItem().toString().matches("Seleccionar..")){
			Toast.makeText(getApplicationContext(), this.getString( R.string.error_rnd),Toast.LENGTH_LONG).show();
			return false;
		}
		else if (rndSpinner.getSelectedItem().toString().matches("Otra razón") && oRazon.matches("")){
			Toast.makeText(getApplicationContext(), this.getString( R.string.error_rnd),Toast.LENGTH_LONG).show();
			return false;
		}
		else{
			return true;
		}
	}
}
