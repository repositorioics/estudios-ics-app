package ni.org.ics.estudios.appmovil.muestreoanual.activities;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuMuestreoAnualActivity;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.DatoAdapter;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.Dato;
import ni.org.ics.estudios.appmovil.utils.Constants;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.ArrayList;

public class ReviewActivity extends ListActivity {
	private Object mObjeto;
	private String titulo;
	private ArrayAdapter<Dato> mDatosAdapter;
	private ArrayList<Dato> mDatos = new ArrayList<Dato>();
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		
		titulo = getIntent().getExtras().getString(Constants.TITLE);
		mObjeto = getIntent().getExtras().getSerializable(Constants.OBJECTO);
		
		TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(titulo);
        titleView.setTextColor(getResources().getColor(R.color.text_light));
		
        mDatos.clear();
        Field[] fields = mObjeto.getClass().getDeclaredFields();
        for (Field aField : fields) {
        	aField.setAccessible(true);
        	Dato dato = new Dato();
        	dato.setNombre(aField.getName());
        	try {
        		if(aField.get(mObjeto)!=null){
        			if (aField.getType().toString().equals("class java.util.Date")){
        				DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
        				dato.setValor(mediumDf.format(aField.get(mObjeto)));
        			}
        			else{
        				dato.setValor(aField.get(mObjeto).toString());
        			}
        		}
        		else{
        			dato.setValor(getString(R.string.is_null));
        		}
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				aField.setAccessible(false);
			}
        	mDatos.add(dato);
          }
        
        mDatosAdapter = new DatoAdapter(this, R.layout.list_item_review,
				mDatos);
        
        ListView listView = (ListView) findViewById(android.R.id.list);
        setListAdapter(mDatosAdapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
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
					MenuMuestreoAnualActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
