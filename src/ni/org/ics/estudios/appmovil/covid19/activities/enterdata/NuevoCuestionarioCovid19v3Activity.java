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
import ni.org.ics.estudios.appmovil.covid19.activities.fragments.NuevoCuestionarioCovid19v3Fragment;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuInfoActivity;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

/**
 * Created by Miguel Salinas on 18/09/2020.
 * V1.0
 */
public class NuevoCuestionarioCovid19v3Activity extends FragmentActivity {

    public static final String TAG = "NuevoCuestionarioCovid19v3Activity";

    private Button mCancelView;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private Participante mParticipante;
    private boolean visExitosa = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_data_enter);
        mParticipante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        visExitosa = getIntent().getBooleanExtra(ConstantsDB.VIS_EXITO,false);
        mCancelView = (Button) this.findViewById(R.id.cancel_button);
        mCancelView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(EXIT);
            }
        });
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            NuevoCuestionarioCovid19v3Fragment fragment = new NuevoCuestionarioCovid19v3Fragment();
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
                        Intent i = new Intent(getApplicationContext(),
                                MenuInfoActivity.class);
                        i.putExtra(ConstantsDB.CODIGO, mParticipante.getCodigo());
                        i.putExtra(ConstantsDB.VIS_EXITO, visExitosa);
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
