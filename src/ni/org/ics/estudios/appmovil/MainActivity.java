package ni.org.ics.estudios.appmovil;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.EditText;
import ni.org.ics.estudios.appmovil.adapters.MainActivityAdapter;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuCohorteFamiliaActivity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.DownloadBaseActivity;
import ni.org.ics.estudios.appmovil.muestreoanual.activities.MenuMuestreoAnualActivity;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.utils.Constants;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AbstractAsyncActivity {

	private static final int UPDATE_EQUIPO = 11;
	private static final int UPDATE_SERVER = 12;

	
	private static final int EXIT = 1;
	private static final int DOWNLOAD = 2;
	private static final int UPLOAD = 3;
	private static final int VERIFY = 4;
    private static final int VERIFY_DOWNLOAD = 5;
	
	private AlertDialog alertDialog;

    private EstudiosAdapter estudiosAdapter;

    private ListView listView;

    private VerificarPinTask mAuthTask = null;
    private Boolean successLogin;
    private String url;
    private SharedPreferences settings;
    private String mUser;
    private String mPassword;
    protected static final String TAG = LoginActivity.class.getSimpleName();

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String[] menu_main = getResources().getStringArray(R.array.menu_main);

        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        url =
                settings.getString(PreferencesActivity.KEY_SERVER_URL, this.getString(R.string.default_server_url));
        mUser =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);

        mPassword = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPassword,false,false);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new MainActivityAdapter(this, R.layout.menu_item, menu_main));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Opcion de menu seleccionada
                Intent i;
                switch(position){
                    case 0:
                        i = new Intent(getApplicationContext(),
                                MenuCohorteFamiliaActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(getApplicationContext(),
                                MenuMuestreoAnualActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        break;
                    default:
                        String s = (String) listView.getAdapter().getItem(position);
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.MENU_DESCARGA:
			createDialog(VERIFY_DOWNLOAD);
			return true;
		case R.id.MENU_CARGA:
			createDialog(UPLOAD);
			return true;
		case R.id.MENU_EXIT:
			createDialog(EXIT);
			return true;
		case R.id.MENU_PREFERENCES:
			Intent ig = new Intent(this, PreferencesActivity.class);
			startActivity(ig);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		// Opcion de menu seleccionada
		Intent i;
		switch(position){
		case 0: 
			i = new Intent(getApplicationContext(),
					MenuCohorteFamiliaActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			break;
            case 1:
                i = new Intent(getApplicationContext(),
                        MenuMuestreoAnualActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
		default: 
			String s = (String) listView.getAdapter().getItem(position);
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
		}
	}*/

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}


	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onBackPressed (){
		createDialog(EXIT);
	}

	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog) {
            case EXIT:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.exiting));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish app
                        dialog.dismiss();
                        finish();
                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        System.exit(0);
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
            case DOWNLOAD:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.downloading));
                builder.setIcon(android.R.drawable.ic_menu_help);
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        estudiosAdapter.open();
                        boolean hayDatos = estudiosAdapter.verificarData();
                        estudiosAdapter.close();
                        if (hayDatos) {
                            createDialog(VERIFY);
                        } else {
                            Intent ie = new Intent(getApplicationContext(), DownloadBaseActivity.class);
                            startActivityForResult(ie, UPDATE_EQUIPO);
                        }
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
            case UPLOAD:

                break;
            case VERIFY:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.data_not_sent));
                builder.setIcon(android.R.drawable.ic_menu_help);
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent ie = new Intent(getApplicationContext(), DownloadBaseActivity.class);
                        startActivityForResult(ie, UPDATE_EQUIPO);
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
            case VERIFY_DOWNLOAD:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.enter_pin_download));
                builder.setIcon(R.drawable.ic_menu_login);
                // Set an EditText view to get user input
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (input.getText().toString().length()>0) {
                            String mPin = input.getText().toString();
                            new VerificarPinTask().execute(mPin);
                        }else{
                            createDialog(VERIFY_DOWNLOAD);
                            Toast.makeText(getApplicationContext(),	getString(R.string.pin_required), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton(this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
		alertDialog = builder.create();
		alertDialog.show();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {	
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_CANCELED) {
			if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getApplicationContext().getString(R.string.error));
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				builder.setMessage(intent.getStringExtra("resultado"))
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//do things
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
				return;
			}
		}
		else{
			if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(android.R.drawable.ic_dialog_info);
				builder.setMessage(getApplicationContext().getString(R.string.success))
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//do things
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
			return;
		}
	}

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class VerificarPinTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            String pinUser = params[0];
            //showLoadingProgressDialog();
            String urlRequest = url + "/movil/pin/{pinUser}";
            //successLogin=false;
            // Agrega la autenticacion basica HTTP
            HttpAuthentication authHeader = new HttpBasicAuthentication(mUser, mPassword);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            requestHeaders.setContentType(MediaType.TEXT_PLAIN);
            // Crea una nueva instancia de RestTemplate
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            try {
                // Hace la solicitud a la red
                Log.d(TAG, urlRequest);
                ResponseEntity<String> pinFromServer = restTemplate.exchange(urlRequest, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), String.class, pinUser);
                return pinFromServer.getBody();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return e.getLocalizedMessage();
            }
        }


        @Override
        protected void onPostExecute(final String respuesta) {
            mAuthTask = null;
            dismissProgressDialog();
            if (respuesta.equalsIgnoreCase(Constants.OK)){
                createDialog(DOWNLOAD);
            }else if(respuesta.contains("I/O error: failed to connect to")){
                Toast.makeText(getApplicationContext(),	getString(R.string.error_io_server), Toast.LENGTH_LONG).show();
            }
            else if(respuesta.contains("404")){
                Toast.makeText(getApplicationContext(),	getString(R.string.error_notfound_server), Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),	getString(R.string.pin_error), Toast.LENGTH_LONG).show();
                createDialog(VERIFY_DOWNLOAD);
            }
        }

        @Override
        protected void onCancelled() {
            dismissProgressDialog();
            mAuthTask = null;
        }
    }
}
