package ni.org.ics.estudios.appmovil.muestreoanual.activities;
/**
 * Actividad que presenta la lista de Participantes
 * 
 * @author William Aviles
 **/

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.*;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.ParticipanteAdapter;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.ArrayList;
import java.util.List;


public class ListParticipantesCasaActivity extends ListActivity{

	private ImageButton mBarcodeButton;
	private Button mSearchButton;
	private EditText mSearchText;
	private TextWatcher mFilterTextWatcher;
	private ArrayAdapter<Participante> mParticipanteAdapter;
	private List<Participante> mParticipantes = new ArrayList<Participante>();
	private Participante mParticipante = new Participante();
	private Integer codCasa;
	private Integer codComun;//Buscar si ya tiene hecha la ec

	public static final int BARCODE_CAPTURE = 2;
	private static final int MENU_ADD_PART = Menu.FIRST + 4;

	private String username;
	private SharedPreferences settings;

    private EstudiosAdapter cat = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.find_item);
		setTitle(getString(R.string.app_name)+ " > "
				+ getString(R.string.selec_part));

		registerForContextMenu(getListView());

		if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}

		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        cat = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		codCasa = getIntent().getIntExtra(ConstantsDB.COD_CASA,-1);
		codComun = getIntent().getIntExtra("codComun",-1);


		//Configuracion para la busqueda
		mFilterTextWatcher = new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (mParticipanteAdapter != null) {
					mParticipanteAdapter.getFilter().filter(s);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}
		};

		mSearchText = (EditText) findViewById(R.id.search_text);
		mSearchText.setEnabled(false);
		mSearchText.setVisibility(View.GONE);
		mSearchText.addTextChangedListener(mFilterTextWatcher);

		mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
		mBarcodeButton.setEnabled(false);
		mBarcodeButton.setVisibility(View.GONE);
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

		mSearchButton = (Button) findViewById(R.id.search_button);
		mSearchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i;
				i = new Intent(getApplicationContext(),
						SelecPartActivity.class);
				i.putExtra(Constants.MENU_INFO,false);
                i.putExtra(Constants.MENU_ZIKA,false);
				i.putExtra(ConstantsDB.COD_CASA,codCasa);
				i.putExtra(ConstantsDB.CODIGO,codComun);
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.general, menu);
		menu.add(0, MENU_ADD_PART, 0, getString(R.string.add_part))
		.setIcon(R.drawable.ic_menu_add);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {    	
		switch(item.getItemId()) {
		case R.id.MENU_BORRAR:
			if (mParticipante.getCodigo() != null){
				createVerificarDialog();	
			}
			return true;
		default:
			return super.onContextItemSelected(item);
		}
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
		case MENU_ADD_PART:
			i = new Intent(getApplicationContext(),
					SelecPartActivity.class);
			i.putExtra(Constants.MENU_INFO,false);
			i.putExtra(ConstantsDB.COD_CASA,codCasa);
			i.putExtra(ConstantsDB.CODIGO,codComun);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {

		mParticipante = (Participante) getListAdapter().getItem(position);
		listView.showContextMenuForChild(view);

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (requestCode == BARCODE_CAPTURE && intent != null) {
			String sb = intent.getStringExtra("SCAN_RESULT");
			if (sb != null && sb.length() > 0) {
				mSearchText.setText(sb);
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);

	}

	private void getParticipantes() {
		cat.open();
		mParticipantes = cat.getParticipantes(MainDBConstants.casa +"="+codCasa, null);
		cat.close();
		refreshView();
	}

	private void refreshView() {

		mParticipanteAdapter = new ParticipanteAdapter(this, R.layout.complex_list_item,
				mParticipantes);
		setListAdapter(mParticipanteAdapter);

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		mSearchText.removeTextChangedListener(mFilterTextWatcher);

	}

	@Override
	protected void onResume() {
		super.onResume();
		getParticipantes();
	}


	private void createVerificarDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(this.getString(R.string.confirm));
		builder.setMessage("Desea quitar al participante " + mParticipante.getCodigo().toString()+ " de la casa " +mParticipante.getCasa().getCodigo().toString()+ "?");
		builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Finish app
				dialog.dismiss();
				cat.open();
                cat.createCambioCasa(mParticipante.getCodigo(), mParticipante.getCasa().getCodigo(), 9999, username);
                cat.updateCasaParticipante(9999,mParticipante.getCodigo(),username, "Si");
                cat.close();
				getParticipantes();
				Toast toast = Toast.makeText(getApplicationContext(),"Actualizado!!!!!",Toast.LENGTH_LONG);
				toast.show();
			}
		});
		builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
				dialog.dismiss();
			}
		});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

}
