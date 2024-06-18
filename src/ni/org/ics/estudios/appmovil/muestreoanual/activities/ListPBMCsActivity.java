package ni.org.ics.estudios.appmovil.muestreoanual.activities;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.RecepcionBHC;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.RecepcionBHCId;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.RecepcionPbmc;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.BHCAdapter;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.PBMCAdapter;
import ni.org.ics.estudios.appmovil.utils.FileUtils;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;
import ni.org.ics.estudios.appmovil.utils.zbar.SimpleScannerActivity;
import ni.org.ics.estudios.appmovil.utils.zbar.ZBarConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListPBMCsActivity extends ListActivity {
    private ImageButton mBarcodeButton;
    private EditText mSearchText;
    private ArrayAdapter<RecepcionPbmc> mTuboAdapter;
    private ArrayList<RecepcionPbmc> mTubos = new ArrayList<RecepcionPbmc>();
    private Button mSearchButton;
    private static final int MENU_VIEW = Menu.FIRST + 2;
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

        mSearchText = (EditText) findViewById(R.id.search_text);
        mSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //EditText editText = (EditText) findViewById(R.id.search_text);
                String codigo = mSearchText.getText().toString();
                if (codigo != null && !codigo.equals("")) {
                    String mPass = ((MyIcsApplication) getApplication()).getPassApp();
                    EstudiosAdapter ca = new EstudiosAdapter(getApplicationContext(),mPass,false,false);
                    DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    String date = null;
                    date = targetFormat.format(new Date());
                    ca.open();
                    RecepcionPbmc c  = null;
                    c = ca.buscarRecepcionPbmc(Integer.valueOf(codigo), date); //Buscar las recepciones de pbmc
                    if (c != null) {
                        ArrayList<RecepcionPbmc> arrayList = new ArrayList<RecepcionPbmc>();
                        arrayList.add(c);
                        mTuboAdapter = new PBMCAdapter(getApplicationContext(), R.layout.complex_list_item,
                                arrayList);
                        setListAdapter(mTuboAdapter);
                    }
                    ca.close();
                }
            }
        });
        //mSearchText.addTextChangedListener(mFilterTextWatcher);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mSearchButton.setVisibility(View.GONE);

        mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
        mBarcodeButton.setOnClickListener(new View.OnClickListener() {
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
        menu.add(0, MENU_VIEW, 0, "Recargar")
                .setIcon(R.drawable.ic_menu_refresh);

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
            case MENU_VIEW:
                refreshView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) { }

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

    private void getMuestras() {

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        EstudiosAdapter ca = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
        ca.open();
        DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String date = null;
        date = targetFormat.format(new Date());
        ca.open();
        mTubos = ca.allRecepcionPbmcDelDia(date);
        if (mTubos != null) {
            ca.close();
        }
        refreshView();
        ca.close();
    }

    private void refreshView() {
        mTuboAdapter = new PBMCAdapter(this, R.layout.complex_list_item,
                mTubos);
        setListAdapter(mTuboAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mSearchText.removeTextChangedListener(mFilterTextWatcher);
        mSearchText.setText(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMuestras();
        // filtrar nuevamente
        mSearchText.setText(mSearchText.getText().toString());
        showToast("Total de muestras = "+ mTuboAdapter.getCount() ,2);
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
