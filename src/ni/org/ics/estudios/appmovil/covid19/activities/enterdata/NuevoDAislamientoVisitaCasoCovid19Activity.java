package ni.org.ics.estudios.appmovil.covid19.activities.enterdata;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.covid19.activities.MenuVisitaCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.covid19.activities.fragments.NuevoDAislamientoVisitaCasoCovid19Fragment;
import ni.org.ics.estudios.appmovil.covid19.activities.fragments.NuevoSintomaVisitaCasoCovid19Fragment;
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaSintomasVisitaCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaSeguimientoCasoCovid19;
import ni.org.ics.estudios.appmovil.utils.Constants;


public class NuevoDAislamientoVisitaCasoCovid19Activity extends FragmentActivity {

    public static final String TAG = "NuevoSintomasVisitaCasoCovid19Activity";
    
    private Button mCancelView;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private VisitaSeguimientoCasoCovid19 mVisitaSeguimientoCaso;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_data_enter);
        mVisitaSeguimientoCaso = (VisitaSeguimientoCasoCovid19) getIntent().getExtras().getSerializable(Constants.VISITA);
        mCancelView = (Button) this.findViewById(R.id.cancel_button);
		mCancelView.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(EXIT);
			}
		});
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			NuevoDAislamientoVisitaCasoCovid19Fragment fragment = new NuevoDAislamientoVisitaCasoCovid19Fragment();
            transaction.replace(R.id.data_content_fragment, fragment);
            transaction.commit();
        }
    }
    
	@Override
	public void onBackPressed (){
		createDialog(EXIT);
	}
    
    private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case EXIT:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.exiting));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Finish app
					dialog.dismiss();
					Bundle arguments = new Bundle();
					Intent i;
					arguments.putSerializable(Constants.VISITA , mVisitaSeguimientoCaso);
					i = new Intent(getApplicationContext(),
							MenuVisitaCasoCovid19Activity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        i.putExtras(arguments);
					startActivity(i);
					finish();
				}
			});
			builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Do nothing
					dialog.dismiss();
				}
			});
			break;
		}
		alertDialog = builder.create();
		alertDialog.show();
	}

}
