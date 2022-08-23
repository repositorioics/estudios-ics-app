package ni.org.ics.estudios.appmovil.entomologia.activities;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.entomologia.adapters.CuestionarioHogarAdapter;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogar;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.ReviewActivity;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;

import java.util.ArrayList;

public class ListReviewActivity extends ListActivity {
	private String titulo;	
	private ImageButton mBarcodeButton;
	private EditText mSearchText;
	private TextWatcher mFilterTextWatcher;
	private Button mSearchButton;
	ArrayAdapter<CuestionarioHogar> mCuestionarioHogarAdapter =null;

	public static final int BARCODE_CAPTURE = 2;
	
	private Integer codigo;
	private Integer codCasa;
	private Integer codComun;
	private AlertDialog alertDialog;
	private String username;
	private SharedPreferences settings;

    private EstudiosAdapter ca;

	@SuppressWarnings("unchecked")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_item);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		titulo = getIntent().getExtras().getString(Constants.TITLE);

		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        ca = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		//Configuracion para la busqueda
		mFilterTextWatcher = new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (mCuestionarioHogarAdapter != null) {
					mCuestionarioHogarAdapter.getFilter().filter(s);
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
		mSearchText.setHint(titulo);
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

		if (titulo.matches(getString(R.string.cuestionario_hogar))){
			
			mCuestionarioHogarAdapter = new CuestionarioHogarAdapter(this, R.layout.list_item_review,
					(ArrayList<CuestionarioHogar>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mCuestionarioHogarAdapter);
			showToast("Total = "+ mCuestionarioHogarAdapter.getCount());
		}

        ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_ento, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case android.R.id.home:
			case R.id.MENU_BACK:
			case R.id.MENU_HOME:
				i = new Intent(getApplicationContext(),
					MenuEntomologiaActivity.class);
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
		Bundle arguments = new Bundle();
		Intent i = null;

		if (titulo.matches(getString(R.string.cuestionario_hogar))){
			CuestionarioHogar encuestapart = (CuestionarioHogar) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.cuestionario_hogar));
			if (encuestapart!=null) arguments.putSerializable(Constants.OBJECTO , encuestapart);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		i.putExtras(arguments);
		startActivity(i);
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
