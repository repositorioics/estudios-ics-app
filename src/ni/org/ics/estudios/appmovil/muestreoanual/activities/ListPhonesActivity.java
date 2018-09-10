package ni.org.ics.estudios.appmovil.muestreoanual.activities;
/**
 * Actividad que presenta la lista de Participantes
 * 
 * @author William Aviles
 **/

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.ContactoParticipante;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.PhonesAdapter;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.ArrayList;
import java.util.List;


public class ListPhonesActivity extends ListActivity{

	private EditText mSearchText;
	private ArrayAdapter<ContactoParticipante> mPhoneAdapter;
	private List<ContactoParticipante> mPhones = new ArrayList<ContactoParticipante>();
    private List<Barrio> barrios = new ArrayList<Barrio>();
    private static Participante participante = new Participante();
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
        participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
		mSearchText = (EditText) findViewById(R.id.search_text);
        mSearchText.setVisibility(View.GONE);
        Button mSearchButton = (Button) findViewById(R.id.search_button);
		mSearchButton.setVisibility(View.GONE);
        ImageButton mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
        mBarcodeButton.setVisibility(View.GONE);
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
                    MenuInfoActivity.class);
            i.putExtra(ConstantsDB.CODIGO, participante.getCodigo());
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

	private void getPhones() {

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        EstudiosAdapter ca = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		
		ca.open();
		mPhones = ca.getContactosParticipantes(MainDBConstants.participante + " = " + participante.getCodigo() + " and " + MainDBConstants.numero1 +" > 0 " , null);
        barrios = ca.getBarrios(null, CatalogosDBConstants.nombre);
        ca.close();
		refreshView();

	}

	private void refreshView() {

		mPhoneAdapter = new PhonesAdapter(this, R.layout.complex_list_item,
                mPhones, participante);
		setListAdapter(mPhoneAdapter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPhones();
	}

}
