package ni.org.ics.estudios.appmovil.influenzauo1.activities.enterdata;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaCasoUO1;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.fragments.NuevoSintomaVisitaCasoUO1Fragment;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.list.ListaSintomasVisitaCasoUO1Activity;
import ni.org.ics.estudios.appmovil.utils.Constants;


public class NuevoSintomaVisitaCasoUO1Activity extends FragmentActivity {

    public static final String TAG = "NuevoSintomaVisitaCasoUO1Activity";
    
    private Button mCancelView;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private VisitaCasoUO1 mVisitaSeguimientoCaso;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_data_enter);
        mVisitaSeguimientoCaso = (VisitaCasoUO1) getIntent().getExtras().getSerializable(Constants.VISITA);
        mCancelView = (Button) this.findViewById(R.id.cancel_button);
		mCancelView.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(EXIT);
			}
		});
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            NuevoSintomaVisitaCasoUO1Fragment fragment = new NuevoSintomaVisitaCasoUO1Fragment();
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
							ListaSintomasVisitaCasoUO1Activity.class);
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
