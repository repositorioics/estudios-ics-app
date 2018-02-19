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

import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.RecepcionSero;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.RecepcionSeroId;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.RojoAdapter;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import ni.org.ics.estudios.appmovil.utils.FileUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ListRojosActivity extends ListActivity{

	private ImageButton mBarcodeButton;
	private EditText mSearchText;
	private TextWatcher mFilterTextWatcher;
	private ArrayAdapter<RecepcionSero> mTuboAdapter;
	private ArrayList<RecepcionSero> mTubos = new ArrayList<RecepcionSero>();
	private Date todayWithZeroTime = null;
	private Button mSearchButton;

	public static final int BARCODE_CAPTURE = 2;

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
				if (mTuboAdapter != null) {
					mTuboAdapter.getFilter().filter(s);
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
                    MenuSupervisorActivity.class);
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

		if (requestCode == BARCODE_CAPTURE && intent != null) {
			String sb = intent.getStringExtra("SCAN_RESULT");
			if (sb != null && sb.length() > 0) {
				mSearchText.setText(sb);
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);

	}

	private void getMuestras() {

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        EstudiosAdapter ca = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		
		ca.open();
		Cursor tubossero = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		try {
			todayWithZeroTime =formatter.parse(formatter.format(today));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tubossero = ca.obtenerRecepcionSero(todayWithZeroTime);

		if (tubossero != null && tubossero.getCount() > 0) {

			mTubos.clear();

			RecepcionSero mTubo;
			do{
				mTubo = new RecepcionSero();
                mTubo.setCodigo(tubossero.getInt(tubossero.getColumnIndex(ConstantsDB.CODIGO)));
                mTubo.setFechaRecSero(new Date(tubossero.getLong(tubossero.getColumnIndex(ConstantsDB.FECHA_SERO))));
				mTubo.setId(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.ID)));
				mTubo.setVolumen(tubossero.getDouble(tubossero.getColumnIndex(ConstantsDB.VOLSERO)));
				mTubo.setLugar(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.LUGAR)));
				mTubo.setObservacion(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.OBSSERO)));
				mTubo.setUsername(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.USERNAME)));
				mTubo.setEstado(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.STATUS)));
				mTubo.setFecreg(new Date(tubossero.getLong(tubossero.getColumnIndex(ConstantsDB.TODAY))));
				mTubos.add(mTubo);
			} while (tubossero.moveToNext());
		}
		
		refreshView();

		if (tubossero != null) {
			tubossero.close();
		}
		ca.close();
		
	}

	private void refreshView() {

		mTuboAdapter = new RojoAdapter(this, R.layout.complex_list_item,
				mTubos);
		setListAdapter(mTuboAdapter);

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		mSearchText.removeTextChangedListener(mFilterTextWatcher);

	}

	@Override
	protected void onResume() {
		super.onResume();
		getMuestras();
		// filtrar nuevamente
		mSearchText.setText(mSearchText.getText().toString());
		showToast("Total de muestras = "+ mTuboAdapter.getCount() ,3);
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
