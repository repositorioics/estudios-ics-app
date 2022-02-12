package ni.org.ics.estudios.appmovil.muestreoanual.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.*;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import ni.org.ics.estudios.appmovil.AbstractAsyncListActivity;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.cohortefamilia.dto.DatosCHF;
import ni.org.ics.estudios.appmovil.covid19.dto.DatosCovid19;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;
import ni.org.ics.estudios.appmovil.domain.covid19.CasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.ParticipanteCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaVacunaUO1;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.influenzauo1.activities.list.ListaVisitasVacunaUO1Activity;
import ni.org.ics.estudios.appmovil.influenzauo1.dto.DatosUO1;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.ParticipanteAdapter;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.utils.*;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SelecPartActivity extends AbstractAsyncListActivity {


	private Spinner mMetodoView;
	private EditText mCodigoView;
	private TextView mPartTextView;
	private ImageButton mBarcodeButton;
	private ImageButton mFindButton;
	private ImageButton mSelectButton;
	private String username;
	private SharedPreferences settings;
	private boolean desdeMenuPrincipal=false;
	private boolean desdeMenuZika=false;
	private boolean desdeMenuUO1 = false;
	private AlertDialog alertDialog;
	
	public static final int BARCODE_CAPTURE = 2;

	private Integer codigo;
	private Integer codigoComun;
	private Integer codigoCasa;
	private Integer codigoCasaAnt;
	private String codigoParticipante;

    private EstudiosAdapter estudiosAdapter;

	private ArrayAdapter<Participante> mParticipanteAdapter;
	private List<Participante> mParticipantes = new ArrayList<Participante>();
	private ParticipanteProcesos participanteComun = new ParticipanteProcesos();
	
	
	private String cadenaBusqueda;
	private Integer opcion;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selec_part_pdcs_list);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);

		desdeMenuPrincipal = getIntent().getBooleanExtra(Constants.MENU_INFO, true);
		desdeMenuZika = getIntent().getBooleanExtra(Constants.MENU_ZIKA, true);
		desdeMenuUO1 = getIntent().getBooleanExtra(Constants.MENU_UO1, false);
		codigoCasa = getIntent().getIntExtra(ConstantsDB.COD_CASA,-1);
		codigoComun = getIntent().getIntExtra(ConstantsDB.CODIGO,-1);
		codigoParticipante = getIntent().getStringExtra(Constants.PARTICIPANTE);
		
		mMetodoView = (Spinner) findViewById(R.id.metodo_busqueda);
		List<String> list = new ArrayList<String>();
		list.add(getString(R.string.desc_barcode));
		list.add(getString(R.string.enter_code));
		list.add(getString(R.string.enter_name));
		list.add(getString(R.string.enter_lastname));
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		mMetodoView.setAdapter(dataAdapter);

		mCodigoView = (EditText) findViewById(R.id.codigo);

		mMetodoView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				if (position==0){
					mCodigoView.setVisibility(View.GONE);
					mFindButton.setVisibility(View.GONE);
					mBarcodeButton.setVisibility(View.VISIBLE);
				}
				else{
					mCodigoView.setVisibility(View.VISIBLE);
					mFindButton.setVisibility(View.VISIBLE);
					mBarcodeButton.setVisibility(View.GONE);
					mCodigoView.requestFocus();
					opcion = position;
					switch(position){
					case 1:
						mCodigoView.setHint(getString(R.string.enter_code));
						mCodigoView.setInputType(InputType.TYPE_CLASS_NUMBER);
						break;
					case 2:
						mCodigoView.setHint(getString(R.string.enter_name));
						mCodigoView.setInputType(InputType.TYPE_CLASS_TEXT);
						break;
					case 3:	
						mCodigoView.setHint(getString(R.string.enter_lastname));
						mCodigoView.setInputType(InputType.TYPE_CLASS_TEXT);
						break;
					default:
						mCodigoView.setHint(getString(R.string.enter_code));
						break;

					}
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});

		mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
		mFindButton = (ImageButton) findViewById(R.id.find_button);
		mSelectButton = (ImageButton) findViewById(R.id.select_button);

		mBarcodeButton.setOnClickListener(new View.OnClickListener()  {
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

		mFindButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
                mParticipantes.clear();
                if ((mCodigoView.getText().toString()==null) || (mCodigoView.getText().toString().matches(""))){
                    mCodigoView.requestFocus();
                    mCodigoView.setError(getString(R.string.search_hint));
                    return;
                }
                buscarParticipante(mCodigoView.getText().toString(),opcion);
			}
		});

		mSelectButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				
			}
		});

		if (codigoParticipante != null) {
			mBarcodeButton.setVisibility(View.GONE);
			mFindButton.setVisibility(View.VISIBLE);
			mCodigoView.setVisibility(View.VISIBLE);
			mCodigoView.setText(codigoParticipante);
			mMetodoView.setSelection(1, false);
			opcion = 1;
			mFindButton.callOnClick();
		}else {
			mFindButton.setVisibility(View.GONE);
			mCodigoView.setVisibility(View.GONE);
		}

		mSelectButton.setVisibility(View.GONE);
		mPartTextView = (TextView) findViewById(R.id.participante);
		mPartTextView.setVisibility(View.GONE);



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


	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		Integer codigoScanned =0;
		if (requestCode == BARCODE_CAPTURE && intent != null) {
			String sb = intent.getStringExtra("SCAN_RESULT");
			if (sb != null && sb.length() > 0) {

				try{
					codigoScanned = Integer.parseInt(sb);
					//CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
					//ca.open();
                    estudiosAdapter.open();
					Participante mParticipante = estudiosAdapter.getParticipante(MainDBConstants.codigo+ " = "+codigoScanned, null);

					if (mParticipante!= null && mParticipante.getCodigo() != null){
						codigo = mParticipante.getCodigo();
						//procesos CHF
						if (mParticipante.getProcesos().getEstudio().toLowerCase().contains("ch familia")){
							ParticipanteCohorteFamiliaCaso existePartCaso = estudiosAdapter.getParticipanteCohorteFamiliaCaso(CasosDBConstants.participante+"="+mParticipante.getCodigo(),null);
							DatosCHF datosCHF = new DatosCHF();
							//existe caso seguimiento activo
							if (existePartCaso!=null && existePartCaso.getCodigoCaso()!=null && existePartCaso.getCodigoCaso().getInactiva().equalsIgnoreCase("0")){
								datosCHF.setEnMonitoreoIntensivo(true);
							}else{
								datosCHF.setEnMonitoreoIntensivo(false);
							}
							mParticipante.setDatosCHF(datosCHF);
						}
						if (mParticipante.getProcesos().getEstudio().contains("UO1")) {
							ParticipanteCasoUO1 casoUO1 = estudiosAdapter.getParticipanteCasoUO1(InfluenzaUO1DBConstants.participante + "=" + mParticipante.getCodigo(), null);
							DatosUO1 datosUO1 = new DatosUO1();
							if (casoUO1 != null) {
								datosUO1.setConvalesciente(true);
								datosUO1.setFechaInicioCaso(casoUO1.getFechaIngreso());
							}
							//sacar la ultima visita inicial por vacuna uo1, si tiene evaluar si tiene menos de 30 dias de vacunado
							List<VisitaVacunaUO1> mVisitasCasos = estudiosAdapter.getVisitasVacunasUO1(InfluenzaUO1DBConstants.participante + " = " + mParticipante.getCodigo() + " and visitaExitosa = '1' and visita = 'I' ",
									InfluenzaUO1DBConstants.fechaVisita + " desc ");
							if (mVisitasCasos.size()>0) {
								VisitaVacunaUO1 ultimaVisita = mVisitasCasos.get(0);
								if (DateUtil.getDateDiff(ultimaVisita.getFechaVisita(), new Date(), TimeUnit.DAYS) < 30){
									datosUO1.setVacunado(ultimaVisita.getVacuna().equalsIgnoreCase(Constants.YESKEYSND));
									datosUO1.setFechaVacuna(ultimaVisita.getFechaVacuna());
									datosUO1.setMxTomada(ultimaVisita.getTomaMxAntes().equalsIgnoreCase(Constants.YESKEYSND));
									datosUO1.setRazonNoMx(ultimaVisita.getRazonNoTomaMx());
									datosUO1.setVisitaExitosa(ultimaVisita.getVisitaExitosa().equalsIgnoreCase(Constants.YESKEYSND));
								}
							}
							mParticipante.setDatosUO1(datosUO1);
						}  //procesos CEIRS
						else if (mParticipante.getProcesos().getEstudio().contains("Influenza")) {
							ParticipanteCasoUO1 casoUO1 = estudiosAdapter.getParticipanteCasoUO1(InfluenzaUO1DBConstants.participante + "=" + mParticipante.getCodigo(), null);
							DatosUO1 datosUO1 = new DatosUO1();
							if (casoUO1 != null) {
								datosUO1.setConvalescienteFlu(true);
								datosUO1.setFechaInicioCaso(casoUO1.getFechaIngreso());
							}
							mParticipante.setDatosUO1(datosUO1);
						}
						//procesos Covid19
						//if (mParticipante.getProcesos().getSubEstudios().contains("2")) {
							ParticipanteCasoCovid19 participanteCasoCovid19 = estudiosAdapter.getParticipanteCasoCovid19(Covid19DBConstants.participante + "=" + mParticipante.getCodigo(), null);
							DatosCovid19 datosCovid19 = new DatosCovid19();
							if (participanteCasoCovid19 != null) {
								datosCovid19.setSeguimiento(true);
								//2 es consentimiento pendiente
								if (!participanteCasoCovid19.getConsentimiento().equalsIgnoreCase("2")) {
									datosCovid19.setMensajeSeguimiento((mParticipante.getProcesos().getEstudio().contains(Constants.T_COVID19) ? getString(R.string.enTransmisionCovid19) : getString(R.string.enSeguimientoCovid19)));
								} else {
									datosCovid19.setMensajeSeguimiento(getString(R.string.noEnroladoTransmisionCovid19));
								}
							} else {
								CasoCovid19 casoCovid19 = null;
								if (mParticipante.getProcesos().getCasaCHF()!=null && !mParticipante.getProcesos().getCasaCHF().isEmpty())
									casoCovid19 = estudiosAdapter.getCasoCovid19(Covid19DBConstants.casa + "='" + mParticipante.getProcesos().getCasaCHF() + "' and " + Covid19DBConstants.inactivo + "='0'", null);
								if (casoCovid19 != null) { // la casa esta en seguimiento covid19, pero el participante no esta enrolado
									datosCovid19.setSeguimiento(true);
									datosCovid19.setMensajeSeguimiento(getString(R.string.noEnroladoTransmisionCovid19));
								}
							}
							mParticipante.setDatosCovid19(datosCovid19);
						//}
						if (desdeMenuPrincipal){
                            ParticipanteProcesos mParticipanteProc = mParticipante.getProcesos(); //ca.getParticipanteProceso(codigoScanned);
                            if (mParticipanteProc != null && (mParticipanteProc.getEstudio() != null && !mParticipanteProc.getEstudio().isEmpty())
                                    || (mParticipanteProc != null && mParticipanteProc.getReConsDeng()!=null && mParticipanteProc.getReConsDeng().matches("Si"))) {
                                Bundle arguments = new Bundle();
                                arguments.putSerializable(Constants.PARTICIPANTE , mParticipante);
                                Intent i = new Intent(getApplicationContext(),
                                        MenuInfoActivity.class);
                                i.putExtra(ConstantsDB.CODIGO, codigo);
                                i.putExtra(ConstantsDB.VIS_EXITO, false);
                                i.putExtras(arguments);
                                startActivity(i);
                                finish();
                            }else{
                                showToast("("+codigoScanned+") - " + getString(R.string.retired_error));
                            }
						}
						else if (desdeMenuZika){
							Intent intent1 = new Intent();
							intent1.putExtra("codigo", codigo);
							setResult(RESULT_OK, intent1);
							finish();
						}
						else if (desdeMenuUO1){
							Bundle arguments = new Bundle();
							Intent i;
							if (mParticipante!=null) arguments.putSerializable(Constants.PARTICIPANTE , mParticipante);
							i = new Intent(getApplicationContext(),
									ListaVisitasVacunaUO1Activity.class);
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							i.putExtras(arguments);
							startActivity(i);
							finish();
						}
						else{
							createConfirmDialog();
						}
					}
					else {
						//mSearchText.setText(sb);
						showToast("("+codigoScanned+") - " + getString(R.string.code_notfound));
					}
				}
				catch(Exception e){
					showToast(getString(R.string.scan_error)+ "("+codigoScanned+") - " +e.getLocalizedMessage());
					return;
				}finally {
                    //ca.close();
                    estudiosAdapter.close();
                }
            }
		}

		super.onActivityResult(requestCode, resultCode, intent);

	}

	private void refreshView() {
        mParticipanteAdapter = new ParticipanteAdapter(this, R.layout.complex_list_item, mParticipantes);
        setListAdapter(mParticipanteAdapter);
        if (mParticipantes.isEmpty()) showToast(getString(R.string.no_items));
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
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
        try {
            Participante mParticipante = (Participante) getListAdapter().getItem(position);
            codigo = mParticipante.getCodigo();
            if (mParticipante.getCasa() != null) {
                codigoCasaAnt = mParticipante.getCasa().getCodigo();
            }
            estudiosAdapter.open();
			ParticipanteProcesos mParticipanteProc = mParticipante.getProcesos();

			if (desdeMenuPrincipal) {
                if (mParticipanteProc != null && (mParticipanteProc.getEstudio() != null && !mParticipanteProc.getEstudio().isEmpty())
                        || (mParticipanteProc != null && mParticipanteProc.getReConsDeng()!=null && mParticipanteProc.getReConsDeng().matches("Si"))) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(Constants.PARTICIPANTE , mParticipante);
                    Intent i = new Intent(getApplicationContext(),
                            MenuInfoActivity.class);
                    i.putExtra(ConstantsDB.CODIGO, codigo);
                    i.putExtra(ConstantsDB.VIS_EXITO, false);
                    i.putExtras(arguments);
                    startActivity(i);
                    finish();
                } else {
                    showToast("(" + codigo + ") - " + getString(R.string.retired_error));
                }
            } else if (desdeMenuUO1){
				if (mParticipanteProc != null && mParticipanteProc.getEstudio() != null && !mParticipanteProc.getEstudio().isEmpty())
				{
					if (mParticipanteProc.getEstudio().contains("UO1")){
						Bundle arguments = new Bundle();
						Intent i;
						if (mParticipante != null) arguments.putSerializable(Constants.PARTICIPANTE, mParticipante);
						i = new Intent(getApplicationContext(),
								ListaVisitasVacunaUO1Activity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						i.putExtras(arguments);
						startActivity(i);
						finish();
					}else {
						showToast("(" + codigo + ") - " + getString(R.string.no_pertenece_uo1));
					}
				} else {
					showToast("(" + codigo + ") - " + getString(R.string.retired_error));
				}
			}
            else {
                createConfirmDialog();
            }
        }
        catch(Exception e){
            showToast(e.getMessage());
            return;
        }finally {
            estudiosAdapter.close();
        }
	}
	
	private void createConfirmDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(this.getString(R.string.confirm));
		builder.setMessage("Desea agregar al participante " + codigo + " a la casa " + codigoCasa + "?");
		builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Finish app
				dialog.dismiss();

                estudiosAdapter.open();
                estudiosAdapter.createCambioCasa(codigo, codigoCasaAnt, codigoCasa, username);
				participanteComun = estudiosAdapter.getParticipanteProcesos(ConstantsDB.CODIGO+"="+codigoComun.toString(), null);
                estudiosAdapter.updateCasaParticipante(codigoCasa, codigo, username, participanteComun.getEnCasa());
                estudiosAdapter.close();

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
		alertDialog = builder.create();
		alertDialog.show();
	}

    public void buscarParticipante(String parametro, int opcion){
        String filtro = "";
        switch (opcion) {
            case 0:
                filtro = MainDBConstants.codigo + "=" + parametro;
                break;
            case 1:
                filtro = MainDBConstants.codigo + "=" + parametro;
                break;
            case 2:
                filtro = MainDBConstants.nombre1 + " like '%" + parametro + "%' or " + MainDBConstants.nombre2 + " like '%" + parametro + "%'";
                break;
            case 3:
                filtro = MainDBConstants.apellido1 + " like '%" + parametro + "%' or " + MainDBConstants.apellido1 + " like '%" + parametro + "%'";
                break;
        }
        new FetchDataTask().execute(filtro);
    }



    // ***************************************
    // Private classes
    // ***************************************
    private class FetchDataTask extends AsyncTask<String, Void, String> {
        private String filtro = null;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            filtro = values[0];
            try {
                estudiosAdapter.open();
                mParticipantes = estudiosAdapter.getParticipantes(filtro, MainDBConstants.codigo);
                int indice =0;
                for(Participante p : mParticipantes){
					//procesos CHF
					if (p.getProcesos().getEstudio().toLowerCase().contains("ch familia")){
						ParticipanteCohorteFamiliaCaso existePartCaso = estudiosAdapter.getParticipanteCohorteFamiliaCaso(CasosDBConstants.participante+"="+p.getCodigo(),null);
						DatosCHF datosCHF = new DatosCHF();
						//existe caso seguimiento activo
						if (existePartCaso!=null && existePartCaso.getCodigoCaso()!=null && existePartCaso.getCodigoCaso().getInactiva().equalsIgnoreCase("0")){
							datosCHF.setEnMonitoreoIntensivo(true);
						}else{
							datosCHF.setEnMonitoreoIntensivo(false);
						}
						mParticipantes.get(indice).setDatosCHF(datosCHF);
					}
                	if (p.getProcesos().getEstudio().contains("UO1")) {
						ParticipanteCasoUO1 casoUO1 = estudiosAdapter.getParticipanteCasoUO1(InfluenzaUO1DBConstants.participante + "=" + p.getCodigo(), null);
						DatosUO1 datosUO1 = new DatosUO1();
						if (casoUO1 != null) {
							datosUO1.setConvalesciente(true);
							datosUO1.setFechaInicioCaso(casoUO1.getFechaIngreso());

						}
						//sacar la ultima visita inicial por vacuna uo1, si tiene evaluar si tiene menos de 30 dias de vacunado
						List<VisitaVacunaUO1> mVisitasCasos = estudiosAdapter.getVisitasVacunasUO1(InfluenzaUO1DBConstants.participante + " = " + p.getCodigo() + " and visitaExitosa = '1' and visita = 'I' ",
								InfluenzaUO1DBConstants.fechaVisita + " desc ");
						if (mVisitasCasos.size()>0) {
							VisitaVacunaUO1 ultimaVisita = mVisitasCasos.get(0);
							if (DateUtil.getDateDiff(ultimaVisita.getFechaVisita(), new Date(), TimeUnit.DAYS) < 30){
								datosUO1.setVacunado(ultimaVisita.getVacuna().equalsIgnoreCase(Constants.YESKEYSND));
								datosUO1.setFechaVacuna(ultimaVisita.getFechaVacuna());
								datosUO1.setMxTomada(ultimaVisita.getTomaMxAntes().equalsIgnoreCase(Constants.YESKEYSND));
								datosUO1.setRazonNoMx(ultimaVisita.getRazonNoTomaMx());
								datosUO1.setVisitaExitosa(ultimaVisita.getVisitaExitosa().equalsIgnoreCase(Constants.YESKEYSND));
							}

						}
						mParticipantes.get(indice).setDatosUO1(datosUO1);
					}  //procesos CEIRS
                	else if (p.getProcesos().getEstudio().contains("Influenza")) {
						ParticipanteCasoUO1 casoUO1 = estudiosAdapter.getParticipanteCasoUO1(InfluenzaUO1DBConstants.participante + "=" + p.getCodigo(), null);
						DatosUO1 datosUO1 = new DatosUO1();
						if (casoUO1 != null) {
							datosUO1.setConvalescienteFlu(true);
							datosUO1.setFechaInicioCaso(casoUO1.getFechaIngreso());
						}
						p.setDatosUO1(datosUO1);
					}
					//procesos Covid19
					//if (p.getProcesos().getSubEstudios().contains("2")) {
						ParticipanteCasoCovid19 participanteCasoCovid19 = estudiosAdapter.getParticipanteCasoCovid19(Covid19DBConstants.participante + "=" + p.getCodigo(), null);
						DatosCovid19 datosCovid19 = new DatosCovid19();
						if (participanteCasoCovid19 != null) {
							datosCovid19.setSeguimiento(true);
							//2 es consentimiento pendiente. La casa está en transmision
							if (!participanteCasoCovid19.getConsentimiento().equalsIgnoreCase("2")) {
								datosCovid19.setMensajeSeguimiento((p.getProcesos().getEstudio().contains(Constants.T_COVID19) ? getString(R.string.enTransmisionCovid19) : getString(R.string.enSeguimientoCovid19)));
							} else {
								datosCovid19.setMensajeSeguimiento(getString(R.string.noEnroladoTransmisionCovid19));
							}
						} else {
							CasoCovid19 casoCovid19 = null;
							if (p.getProcesos().getCasaCHF()!=null && !p.getProcesos().getCasaCHF().isEmpty())
								casoCovid19 = estudiosAdapter.getCasoCovid19(Covid19DBConstants.casa + "='" + p.getProcesos().getCasaCHF() + "' and " + Covid19DBConstants.inactivo + "='0'", null);
							if (casoCovid19 != null) { // la casa esta en transmision covid19, pero el participante no esta enrolado
								datosCovid19.setSeguimiento(true);
								datosCovid19.setMensajeSeguimiento(getString(R.string.noEnroladoTransmisionCovid19));
							}
						}
						mParticipantes.get(indice).setDatosCovid19(datosCovid19);
					//}
                	indice++;
				}
                estudiosAdapter.close();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the network request completes, hide the progress indicator
            dismissProgressDialog();
            refreshView();
        }

    }

}
