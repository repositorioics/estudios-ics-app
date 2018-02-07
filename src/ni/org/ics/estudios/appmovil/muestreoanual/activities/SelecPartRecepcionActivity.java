package ni.org.ics.estudios.appmovil.muestreoanual.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.adapters.ParticipanteAdapter;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Participante;

import java.util.ArrayList;
import java.util.List;

public class SelecPartRecepcionActivity extends ListActivity {


	private Spinner mMetodoView;
	private EditText mCodigoView;
	private TextView mPartTextView;
	private ImageButton mBarcodeButton;
	private ImageButton mFindButton;
	private ImageButton mSelectButton;

	public static final int BARCODE_CAPTURE = 2;

	private Integer codigo;
	private String cadenaBusqueda;
	private Integer opcion;

	private ArrayAdapter<Participante> mParticipanteAdapter;
	private List<Participante> mParticipantes = new ArrayList<Participante>();

    private EstudiosAdapter ca;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selec_part_pdcs_list);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        ca = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		mMetodoView = (Spinner) findViewById(R.id.metodo_busqueda);
		List<String> list = new ArrayList<String>();
		list.add(getString(R.string.desc_barcode));
		list.add(getString(R.string.enter_code));
		list.add(getString(R.string.enter_name));
		list.add(getString(R.string.enter_lastname));

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		mMetodoView.setAdapter(dataAdapter);

		mMetodoView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				if (position==0){
					mCodigoView.setVisibility(View.GONE);
					mFindButton.setVisibility(View.GONE);
					mBarcodeButton.setVisibility(View.VISIBLE);
				}
				else{
					mCodigoView.setVisibility(View.VISIBLE);
					mFindButton.setVisibility(View.VISIBLE);
					mBarcodeButton.setVisibility(View.GONE);
					mCodigoView.requestFocus();
					opcion = position;
					switch(position){
					case 1:
						mCodigoView.setHint(getString(R.string.enter_code));
						mCodigoView.setInputType(InputType.TYPE_CLASS_NUMBER);
						break;
					case 2:
						mCodigoView.setHint(getString(R.string.enter_name));
						mCodigoView.setInputType(InputType.TYPE_CLASS_TEXT);
						break;
					case 3:	
						mCodigoView.setHint(getString(R.string.enter_lastname));
						mCodigoView.setInputType(InputType.TYPE_CLASS_TEXT);
						break;
					default:
						mCodigoView.setHint(getString(R.string.enter_code));
						break;

					}

				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});

		mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
		mFindButton = (ImageButton) findViewById(R.id.find_button);
		mSelectButton = (ImageButton) findViewById(R.id.select_button);

		mBarcodeButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				mParticipantes.clear();
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

		mFindButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				switch(opcion){
				case 1:
					mParticipantes.clear();
					try{
						codigo = Integer.valueOf(mCodigoView.getText().toString());
					}
					catch(Exception e){
						mCodigoView.requestFocus();
						mCodigoView.setError(getString(R.string.code_error));
						return;
					}

					if (!(codigo>=1 && codigo<=75000)){
						mCodigoView.requestFocus();
						mCodigoView.setError(getString(R.string.code_error));
						return;
					}
					buscarParticipante(codigo);
					break;
				case 2:
					cadenaBusqueda = mCodigoView.getText().toString();
					if (!(cadenaBusqueda!=null)){
						mCodigoView.requestFocus();
						mCodigoView.setError(getString(R.string.search_hint));
						return;
					}
					buscarParticipanteNombres(cadenaBusqueda);
					break;
				case 3:
					cadenaBusqueda = mCodigoView.getText().toString();
					if (!(cadenaBusqueda!=null)){
						mCodigoView.requestFocus();
						mCodigoView.setError(getString(R.string.search_hint));
						return;
					}
					buscarParticipanteApellidos(cadenaBusqueda);
					break;
				default:
					break;
				}
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mFindButton.getWindowToken(), 0);
			}
		});

		mSelectButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				//TODO implementar busqueda
			}
		});

		mFindButton.setVisibility(View.GONE);
		mSelectButton.setVisibility(View.GONE);

		mCodigoView = (EditText) findViewById(R.id.codigo);
		mCodigoView.setVisibility(View.GONE);

		mPartTextView = (TextView) findViewById(R.id.parametro);
		mPartTextView.setVisibility(View.GONE);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
	}

	private void refreshView() {
		mParticipanteAdapter = new ParticipanteAdapter(this, R.layout.complex_list_item,
				mParticipantes);
		setListAdapter(mParticipanteAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.MENU_BACK:
			finish();
			return true;
		case R.id.MENU_HOME:
			Intent i = new Intent(getApplicationContext(),
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
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
/*
		Participante mParticipante = (Participante) getListAdapter().getItem(position);
		codigo = mParticipante.getCodigo();
		Intent i = new Intent(getApplicationContext(),
				MenuRecepcionActivity.class);
		i.putExtra(ConstantsDB.CODIGO, codigo);
		startActivity(i);*/

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		Integer codigoScanned =0;
		if (requestCode == BARCODE_CAPTURE && intent != null) {
			String sb = intent.getStringExtra("SCAN_RESULT");
			if (sb != null && sb.length() > 0) {

				try{
					codigoScanned = Integer.parseInt(sb);
					//CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
					ca.open();
					Participante mParticipante = ca.getParticipante(codigoScanned);
					ca.close();
					if (mParticipante.getCodigo() != null){
						/*codigo = mParticipante.getCodigo();
						Intent i = new Intent(getApplicationContext(),
								MenuRecepcionActivity.class);
						i.putExtra(ConstantsDB.CODIGO, codigo);
						startActivity(i);*/
                        showToast("("+codigoScanned+") - " + getString(R.string.ok));
					}
					else {
						//mSearchText.setText(sb);
						showToast("(" + codigoScanned + ") - " + getString(R.string.code_notfound));
					}
				}
				catch(Exception e){
					showToast(getString(R.string.scan_error));
					return;
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, intent);

	}

	public void buscarParticipante(Integer codigoScanned){
		//CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
		ca.open();
		Participante mParticipante = ca.getParticipante(codigoScanned);
		ca.close();
		if (mParticipante.getCodigo() != null){
			mParticipantes.add(mParticipante);
		}
		else {
			//mSearchText.setText(sb);
			showToast("("+codigoScanned+") - " + getString(R.string.code_notfound));
		}
		refreshView();
	}
	
	public void buscarParticipanteNombres(String name){
		//CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
		ca.open();
		mParticipantes = ca.getListaParticipantesName(name);
		ca.close();
		refreshView();
	}
	
	public void buscarParticipanteApellidos(String lastname){
		//CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
		ca.open();
		mParticipantes = ca.getListaParticipantesLastName(lastname);
		ca.close();
		refreshView();
	}


	private void showToast(String mensaje){
		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(R.layout.toast,
				(ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(mensaje);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();

	}

}
