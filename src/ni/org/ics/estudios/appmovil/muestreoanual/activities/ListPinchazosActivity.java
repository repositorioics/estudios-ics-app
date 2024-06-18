package ni.org.ics.estudios.appmovil.muestreoanual.activities;
/**
 * Actividad que presenta la lista de Participantes
 * 
 * @author William Aviles
 **/

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import ni.org.ics.estudios.appmovil.MainActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.PinchazoAdapter;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.Pinchazo;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.PinchazoId;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.zbar.SimpleScannerActivity;
import ni.org.ics.estudios.appmovil.utils.zbar.ZBarConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ListPinchazosActivity extends ListActivity{

	private ImageButton mBarcodeButton;
	private EditText mSearchText;
	private TextWatcher mFilterTextWatcher;
	private ArrayAdapter<Pinchazo> mPinAdapter;
	private ArrayList<Pinchazo> mPins = new ArrayList<Pinchazo>();
	private Date todayWithZeroTime = null;
	private Button mSearchButton;

	public static final int BARCODE_CAPTURE = 2;

	private static final int ZBAR_QR_SCANNER_REQUEST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.find_item);
		setTitle(getString(R.string.app_name));

		if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}


		//Configuracion para la busqueda
		mFilterTextWatcher = new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (mPinAdapter != null) {
					mPinAdapter.getFilter().filter(s);
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
		mSearchText.addTextChangedListener(mFilterTextWatcher);
		mSearchButton = (Button) findViewById(R.id.search_button);
		mSearchButton.setVisibility(View.GONE);

		mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
		mBarcodeButton.setOnClickListener(new OnClickListener() {
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.MENU_BACK:
			finish();
			return true;
		case R.id.MENU_HOME:
			Intent i = new Intent(getApplicationContext(),
                    MenuMuestreoAnualActivity.class);
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
		
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		//if (requestCode == BARCODE_CAPTURE && intent != null) {
		if (requestCode == ZBAR_QR_SCANNER_REQUEST && intent != null) {
			//String sb = intent.getStringExtra("SCAN_RESULT");
			String sb = intent.getStringExtra(ZBarConstants.SCAN_RESULT);
			if (sb != null && sb.length() > 0) {
				mSearchText.setText(sb);
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);

	}

	private void getPinchazos() {
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		EstudiosAdapter ca = new EstudiosAdapter(this.getApplicationContext(), mPass, false, false);
		
		ca.open();
		Cursor pinchos = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		try {
			todayWithZeroTime =formatter.parse(formatter.format(today));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pinchos = ca.obtenerPinchazo(todayWithZeroTime);

		if (pinchos != null && pinchos.getCount() > 0) {

			mPins.clear();

			Pinchazo mPin;
			do{
				mPin = new Pinchazo();
				PinchazoId pinId = new PinchazoId();
				pinId.setCodigo(pinchos.getInt(pinchos.getColumnIndex(ConstantsDB.CODIGO)));
				pinId.setFechaPinchazo(new Date(pinchos.getLong(pinchos.getColumnIndex(ConstantsDB.FECHA_PIN))));
				mPin.setPinId(pinId);
				mPin.setNumPin(pinchos.getInt(pinchos.getColumnIndex(ConstantsDB.PINCHAZOS)));
				mPin.setLugar(pinchos.getString(pinchos.getColumnIndex(ConstantsDB.LUGAR)));
				mPin.setObservacion(pinchos.getString(pinchos.getColumnIndex(ConstantsDB.OBSPIN)));
				mPin.setUsername(pinchos.getString(pinchos.getColumnIndex(ConstantsDB.USERNAME)));
				mPin.setEstado(pinchos.getString(pinchos.getColumnIndex(ConstantsDB.STATUS)));
				mPin.setFecreg(new Date(pinchos.getLong(pinchos.getColumnIndex(ConstantsDB.TODAY))));
				mPins.add(mPin);
			} while (pinchos.moveToNext());
		}
		
		refreshView();

		if (pinchos != null) {
			pinchos.close();
		}
		ca.close();
		
	}

	private void refreshView() {

		mPinAdapter = new PinchazoAdapter(this, R.layout.complex_list_item,
				mPins);
		setListAdapter(mPinAdapter);

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		mSearchText.removeTextChangedListener(mFilterTextWatcher);

	}

	@Override
	protected void onResume() {
		super.onResume();
		getPinchazos();
		// filtrar nuevamente
		mSearchText.setText(mSearchText.getText().toString());
		showToast("Total de registros = "+ mPinAdapter.getCount() ,4);
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
			
		case 2:
			image.setImageResource(R.drawable.bhctubes);
			break;
			
			
		case 3:
			image.setImageResource(R.drawable.redtubes);
			break;
		case 4:
			image.setImageResource(R.drawable.ic_pinchazos);
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
