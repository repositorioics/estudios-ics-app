package ni.org.ics.estudios.appmovil.muestreoanual.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuMuestreoAnualActivity;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.MenuSupervisorAdapter;

public class MenuSupervisorActivity extends ListActivity  {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] menu_sup = getResources().getStringArray(R.array.menu_sup);
        setContentView(R.layout.activity_menu);
        setListAdapter(new MenuSupervisorAdapter(this, R.layout.menu_list, menu_sup));
 
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
		// Opcion de menu seleccionada
		Intent i;
		switch(position){
		case 0: 
			i = new Intent(getApplicationContext(),
					RecepcionBhcActivity.class);
			startActivity(i);
			break;
		case 1: 
			i = new Intent(getApplicationContext(),
					RecepcionSeroActivity.class);
			startActivity(i);
			break;
		case 2: 
			i = new Intent(getApplicationContext(),
					TempPbmcActivity.class);
			startActivity(i);
			break;
			
		case 3: 
			i = new Intent(getApplicationContext(),
					TempRojoActivity.class);
			startActivity(i);
			break;
			
		case 4: 
			i = new Intent(getApplicationContext(),
					PinchazoActivity.class);
			startActivity(i);
			break;
			
		default: 
			String s = (String) getListAdapter().getItem(position);
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
		}
	}
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (resultCode == RESULT_CANCELED) {

			return;
		}		
		super.onActivityResult(requestCode, resultCode, intent);

	}
    
}
