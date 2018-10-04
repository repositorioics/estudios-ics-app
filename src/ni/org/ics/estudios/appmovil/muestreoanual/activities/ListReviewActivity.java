package ni.org.ics.estudios.appmovil.muestreoanual.activities;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
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
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.DatosCoordenadas;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaCasaSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaParticipanteSA;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.muestreoanual.adapters.*;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.*;
import ni.org.ics.estudios.appmovil.preferences.PreferencesActivity;
import ni.org.ics.estudios.appmovil.seroprevalencia.adapters.ConsentimientoSAAdapter;
import ni.org.ics.estudios.appmovil.seroprevalencia.adapters.EncuestaCasaSaAdapter;
import ni.org.ics.estudios.appmovil.seroprevalencia.adapters.EncuestaParticipanteSaAdapter;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.ArrayList;
import java.util.Date;

public class ListReviewActivity extends ListActivity {
	private String titulo;	
	private ImageButton mBarcodeButton;
	private EditText mSearchText;
	private TextWatcher mFilterTextWatcher;
	private Button mSearchButton;
	ArrayAdapter<EncuestaParticipante> mEncuestaParticipanteAdapter=null;
	ArrayAdapter<EncuestaCasa> mEncuestaCasaAdapter=null;
	ArrayAdapter<LactanciaMaterna> mLactanciaMaternaAdapter=null;
	ArrayAdapter<PesoyTalla> mPesoyTallaAdapter=null;
	ArrayAdapter<Muestra> mMuestraAdapter=null;
	ArrayAdapter<Obsequio> mObsequioAdapter=null;
    ArrayAdapter<NewVacuna> mVacunaAdapter=null;
	ArrayAdapter<VisitaTerreno> mVisitaTerrenoAdapter=null;
	ArrayAdapter<ReConsentimientoDen2015> mReconsentimientoAdapter=null;
	ArrayAdapter<ReConsentimientoFlu2015> mReconsentimientoFluAdapter=null;
	ArrayAdapter<CodigosCasas> mCodigosCasasAdapter=null;
	ArrayAdapter<ConsentimientoChik> mConsentimientoChikAdapter=null;
	ArrayAdapter<CambioEstudio> mCambioEstudioAdapter=null;
	ArrayAdapter<ConsentimientoZika> mConsentimientoZikaAdapter=null;
	ArrayAdapter<EncuestaSatisfaccion> mEncuestasSAdapter=null;
    ArrayAdapter<DatosPartoBB> mDatosPartoBBAdapter=null;
    ArrayAdapter<DatosVisitaTerreno> mDatosVisitaTerrenoAdapter=null;
    ArrayAdapter<Documentos> mDocumentosAdapter=null;
    ArrayAdapter<EncuestaCasaSA> mEncuestasCasasSaAdapter =null;
    ArrayAdapter<EncuestaParticipanteSA> mEncuestasParticipantesSaAdapter = null;
    ArrayAdapter<ParticipanteSeroprevalencia> mParticipantesSAAdapter =null;
    ArrayAdapter<DatosCoordenadas> mDatosCoordenadasAdapter =null;
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
		codCasa = getIntent().getIntExtra("codCasa",-1);
		codComun = getIntent().getIntExtra("codComun",-1);

		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        ca = new EstudiosAdapter(this.getApplicationContext(),mPass,false,false);
		//TextView titleView = (TextView) findViewById(R.id.title);
		//titleView.setText(titulo);
		//titleView.setTextColor(getResources().getColor(R.color.text_light));

		//Configuracion para la busqueda
		mFilterTextWatcher = new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (mEncuestaParticipanteAdapter != null) {
					mEncuestaParticipanteAdapter.getFilter().filter(s);
				}
				if (mEncuestaCasaAdapter != null) {
					mEncuestaCasaAdapter.getFilter().filter(s);
				}
				if (mLactanciaMaternaAdapter != null) {
					mLactanciaMaternaAdapter.getFilter().filter(s);
				}
				if (mPesoyTallaAdapter != null) {
					mPesoyTallaAdapter.getFilter().filter(s);
				}
				if (mMuestraAdapter != null) {
					mMuestraAdapter.getFilter().filter(s);
				}
				if (mObsequioAdapter != null) {
					mObsequioAdapter.getFilter().filter(s);
				}
				if (mVacunaAdapter != null) {
					mVacunaAdapter.getFilter().filter(s);
				}
				if (mVisitaTerrenoAdapter != null) {
					mVisitaTerrenoAdapter.getFilter().filter(s);
				}
				if (mReconsentimientoAdapter != null) {
					mReconsentimientoAdapter.getFilter().filter(s);
				}
				if (mReconsentimientoFluAdapter != null) {
					mReconsentimientoFluAdapter.getFilter().filter(s);
				}
				if (mConsentimientoChikAdapter != null) {
					mConsentimientoChikAdapter.getFilter().filter(s);
				}
				if (mCambioEstudioAdapter != null) {
					mCambioEstudioAdapter.getFilter().filter(s);
				}
				if (mCodigosCasasAdapter != null) {
					mCodigosCasasAdapter.getFilter().filter(s);
				}
				if (mConsentimientoZikaAdapter != null) {
					mConsentimientoZikaAdapter.getFilter().filter(s);
				}
                if (mDatosPartoBBAdapter != null) {
                    mDatosPartoBBAdapter.getFilter().filter(s);
                }
                if (mEncuestasSAdapter != null) {
                    mEncuestasSAdapter.getFilter().filter(s);
                }
                if (mDatosPartoBBAdapter != null) {
                    mDatosPartoBBAdapter.getFilter().filter(s);
                }
                if (mDatosVisitaTerrenoAdapter != null) {
                    mDatosVisitaTerrenoAdapter.getFilter().filter(s);
                }
                if (mDocumentosAdapter != null) {
                    mDocumentosAdapter.getFilter().filter(s);
                }
                if (mEncuestasParticipantesSaAdapter != null) {
                    mEncuestasParticipantesSaAdapter.getFilter().filter(s);
                }
                if (mEncuestasCasasSaAdapter != null) {
                    mEncuestasCasasSaAdapter.getFilter().filter(s);
                }
                if (mParticipantesSAAdapter != null) {
                    mParticipantesSAAdapter.getFilter().filter(s);
                }
                if (mDatosCoordenadasAdapter != null) {
                    mDatosCoordenadasAdapter.getFilter().filter(s);
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

		if (titulo.matches(getString(R.string.info_survey1))){
			
			mEncuestaParticipanteAdapter = new EncuestaParticipanteAdapter(this, R.layout.list_item_review,
					(ArrayList<EncuestaParticipante>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mEncuestaParticipanteAdapter);
			showToast("Total = "+ mEncuestaParticipanteAdapter.getCount());
		} 

		if (titulo.matches(getString(R.string.info_survey2))){
		
			mEncuestaCasaAdapter = new EncuestaCasaAdapter(this, R.layout.list_item_review,
					(ArrayList<EncuestaCasa>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mEncuestaCasaAdapter);
			showToast("Total = "+ mEncuestaCasaAdapter.getCount());
		}

        if (titulo.matches(getString(R.string.info_participantesa))){

            mEncuestasParticipantesSaAdapter = new EncuestaParticipanteSaAdapter(this, R.layout.list_item_review,
                    (ArrayList<EncuestaParticipanteSA>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
            setListAdapter(mEncuestasParticipantesSaAdapter);
            showToast("Total = "+ mEncuestasParticipantesSaAdapter.getCount());
        }

        if (titulo.matches(getString(R.string.info_casasa))){

            mEncuestasCasasSaAdapter = new EncuestaCasaSaAdapter(this, R.layout.list_item_review,
                    (ArrayList<EncuestaCasaSA>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
            setListAdapter(mEncuestasCasasSaAdapter);
            showToast("Total = "+ mEncuestasCasasSaAdapter.getCount());
        }

        if (titulo.matches(getString(R.string.info_survey3))){
			
			mLactanciaMaternaAdapter = new LactanciaMaternaAdapter(this, R.layout.list_item_review,
					(ArrayList<LactanciaMaterna>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mLactanciaMaternaAdapter);
			showToast("Total = "+ mLactanciaMaternaAdapter.getCount());
		} 

		if (titulo.matches(getString(R.string.info_weight))){
			
			mPesoyTallaAdapter = new PesoyTallaAdapter(this, R.layout.list_item_review,
					(ArrayList<PesoyTalla>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mPesoyTallaAdapter);
			showToast("Total = "+ mPesoyTallaAdapter.getCount());
		} 

		if (titulo.matches(getString(R.string.info_sample))){
			
			mMuestraAdapter = new MuestraAdapter(this, R.layout.list_item_review,
					(ArrayList<Muestra>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mMuestraAdapter);
			showToast("Total = "+ mMuestraAdapter.getCount());
		}

		if (titulo.matches(getString(R.string.info_gift))){
			
			mObsequioAdapter = new ObsequioAdapter(this, R.layout.list_item_review,
					(ArrayList<Obsequio>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mObsequioAdapter);
			showToast("Total = "+ mObsequioAdapter.getCount());
		}

		if (titulo.matches(getString(R.string.info_vacc))){

            mVacunaAdapter = new NewVacunaAdapter(this, R.layout.list_item_review,
                    (ArrayList<NewVacuna>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
            setListAdapter(mVacunaAdapter);
			showToast("Total = "+ mVacunaAdapter.getCount());
		}

		if (titulo.matches(getString(R.string.info_visit))){
			
			mVisitaTerrenoAdapter = new VisitaTerrenoAdapter(this, R.layout.list_item_review,
					(ArrayList<VisitaTerreno>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mVisitaTerrenoAdapter);
			showToast("Total = "+ mVisitaTerrenoAdapter.getCount());
		}

		if (titulo.matches(getString(R.string.info_recon))){
			
			mReconsentimientoAdapter = new Reconsentimiento2015Adapter(this, R.layout.list_item_review,
					(ArrayList<ReConsentimientoDen2015>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mReconsentimientoAdapter);
			showToast("Total = "+ mReconsentimientoAdapter.getCount());
		}
		
		if (titulo.matches(getString(R.string.info_reconflu))){
			
			mReconsentimientoFluAdapter = new ReconsentimientoFlu2015Adapter(this, R.layout.list_item_review,
					(ArrayList<ReConsentimientoFlu2015>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mReconsentimientoFluAdapter);
			showToast("Total = "+ mReconsentimientoFluAdapter.getCount());
		}
		
		if (titulo.matches(getString(R.string.info_conschik))){
			
			mConsentimientoChikAdapter = new ConsentimientoChikAdapter(this, R.layout.list_item_review,
					(ArrayList<ConsentimientoChik>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mConsentimientoChikAdapter);
			showToast("Total = "+ mConsentimientoChikAdapter.getCount());
		}
		
		if (titulo.matches(getString(R.string.info_chgest))){
			
			mCambioEstudioAdapter = new CambioEstudioAdapter(this, R.layout.list_item_review,
					(ArrayList<CambioEstudio>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mCambioEstudioAdapter);
			showToast("Total = "+ mCambioEstudioAdapter.getCount());
		}
		
		if (titulo.matches(getString(R.string.info_codscasa))){
			
			mCodigosCasasAdapter = new CodigosCasasAdapter(this, R.layout.list_item_review,
					(ArrayList<CodigosCasas>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mCodigosCasasAdapter);
			showToast("Total = "+ mCodigosCasasAdapter.getCount());
		}
		
		if (titulo.matches(getString(R.string.info_zika))){
			
			mConsentimientoZikaAdapter = new ConsentimientoZikaAdapter(this, R.layout.list_item_review,
					(ArrayList<ConsentimientoZika>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mConsentimientoZikaAdapter);
			showToast("Total = "+ mConsentimientoZikaAdapter.getCount());
		}

        if (titulo.matches(getString(R.string.datos_parto))){

            mDatosPartoBBAdapter = new DatosPartoBBAdapter(this, R.layout.list_item_review,
                    (ArrayList<DatosPartoBB>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
            setListAdapter(mDatosPartoBBAdapter);
            showToast("Total = "+ mDatosPartoBBAdapter.getCount());
        }

        if (titulo.matches(getString(R.string.datos_casa))){

            mDatosVisitaTerrenoAdapter = new DatosVisitaTerrenoAdapter(this, R.layout.list_item_review,
                    (ArrayList<DatosVisitaTerreno>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
            setListAdapter(mDatosVisitaTerrenoAdapter);
            showToast("Total = "+ mDatosVisitaTerrenoAdapter.getCount());
        }

        if (titulo.matches(getString(R.string.info_docs))){

            mDocumentosAdapter = new DocumentosAdapter(this, R.layout.list_item_review,
                    (ArrayList<Documentos>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
            setListAdapter(mDocumentosAdapter);
            showToast("Total = "+ mDocumentosAdapter.getCount());
        }

		if (titulo.matches(getString(R.string.main_survey))){
			
			mEncuestasSAdapter = new EncuestaSatisfaccionAdapter(this, R.layout.list_item_review,
					(ArrayList<EncuestaSatisfaccion>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
			setListAdapter(mEncuestasSAdapter);
			showToast("Total = "+ mEncuestasSAdapter.getCount());
		}
        if (titulo.matches(getString(R.string.info_casachf))){

            mEncuestaCasaAdapter = new EncuestaCasaAdapter(this, R.layout.list_item_review,
                    (ArrayList<EncuestaCasa>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
            setListAdapter(mEncuestaCasaAdapter);
            showToast("Total = "+ mEncuestaCasaAdapter.getCount());
        }

        if (titulo.matches(getString(R.string.info_sa))){

            mParticipantesSAAdapter = new ConsentimientoSAAdapter(this, R.layout.list_item_review,
                    (ArrayList<ParticipanteSeroprevalencia>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
            setListAdapter(mParticipantesSAAdapter);
            showToast("Total = "+ mParticipantesSAAdapter.getCount());
        }

        if (titulo.matches(getString(R.string.info_coordenadas))){

            mDatosCoordenadasAdapter = new DatosCoordenadaAdapter(this, R.layout.list_item_review,
                    (ArrayList<DatosCoordenadas>) getIntent().getExtras().getSerializable(Constants.OBJECTO));
            setListAdapter(mDatosCoordenadasAdapter);
            showToast("Total = "+ mDatosCoordenadasAdapter.getCount());
        }

        ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
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
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		Bundle arguments = new Bundle();
		Intent i = null;
		if (titulo.matches(getString(R.string.info_survey2)) || titulo.matches(getString(R.string.info_casachf))){
			EncuestaCasa encuestacasa = (EncuestaCasa) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, (titulo.matches(getString(R.string.info_survey2))?getString(R.string.info_survey2):getString(R.string.info_casachf)));
			if (encuestacasa!=null) arguments.putSerializable(Constants.OBJECTO , encuestacasa);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
        if (titulo.matches(getString(R.string.info_casasa))){
            EncuestaCasaSA encuestacasa = (EncuestaCasaSA) getListAdapter().getItem(position);
            arguments.putString(Constants.TITLE, getString(R.string.info_casasa));
            if (encuestacasa!=null) arguments.putSerializable(Constants.OBJECTO , encuestacasa);
            i = new Intent(getApplicationContext(),
                    ReviewActivity.class);
        }
		if (titulo.matches(getString(R.string.info_survey1))){
			EncuestaParticipante encuestapart = (EncuestaParticipante) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_survey1));
			if (encuestapart!=null) arguments.putSerializable(Constants.OBJECTO , encuestapart);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
        if (titulo.matches(getString(R.string.info_participantesa))){
            EncuestaParticipanteSA encuestapart = (EncuestaParticipanteSA) getListAdapter().getItem(position);
            arguments.putString(Constants.TITLE, getString(R.string.info_participantesa));
            if (encuestapart!=null) arguments.putSerializable(Constants.OBJECTO , encuestapart);
            i = new Intent(getApplicationContext(),
                    ReviewActivity.class);
        }
		if (titulo.matches(getString(R.string.info_survey3))){
			LactanciaMaterna lactanciamat = (LactanciaMaterna) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_survey3));
			if (lactanciamat!=null) arguments.putSerializable(Constants.OBJECTO , lactanciamat);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		if (titulo.matches(getString(R.string.info_weight))){
			PesoyTalla pyt = (PesoyTalla) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_weight));
			if (pyt!=null) arguments.putSerializable(Constants.OBJECTO , pyt);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		if (titulo.matches(getString(R.string.info_sample))){
			Muestra muestra = (Muestra) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_sample));
			if (muestra!=null) arguments.putSerializable(Constants.OBJECTO , muestra);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		if (titulo.matches(getString(R.string.info_gift))){
			Obsequio obsequio = (Obsequio) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_gift));
			if (obsequio!=null) arguments.putSerializable(Constants.OBJECTO , obsequio);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		if (titulo.matches(getString(R.string.info_vacc))){
            NewVacuna vacuna = (NewVacuna) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_gift));
			if (vacuna!=null) arguments.putSerializable(Constants.OBJECTO , vacuna);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		if (titulo.matches(getString(R.string.info_visit))){
			VisitaTerreno visita = (VisitaTerreno) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_visit));
			if (visita!=null) arguments.putSerializable(Constants.OBJECTO , visita);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		if (titulo.matches(getString(R.string.info_recon))){
			ReConsentimientoDen2015 recons = (ReConsentimientoDen2015) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_recon));
			if (recons!=null) arguments.putSerializable(Constants.OBJECTO , recons);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		
		if (titulo.matches(getString(R.string.info_reconflu))){
			ReConsentimientoFlu2015 recons = (ReConsentimientoFlu2015) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_reconflu));
			if (recons!=null) arguments.putSerializable(Constants.OBJECTO , recons);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		if (titulo.matches(getString(R.string.info_conschik))){
			ConsentimientoChik conschik = (ConsentimientoChik) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_conschik));
			if (conschik!=null) arguments.putSerializable(Constants.OBJECTO , conschik);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		if (titulo.matches(getString(R.string.info_chgest))){
			CambioEstudio cambioEst = (CambioEstudio) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_chgest));
			if (cambioEst!=null) arguments.putSerializable(Constants.OBJECTO , cambioEst);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		if (titulo.matches(getString(R.string.info_codscasa))){
			CodigosCasas cods = (CodigosCasas) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_codscasa));
			if (cods!=null) arguments.putSerializable(Constants.OBJECTO , cods);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
		if (titulo.matches(getString(R.string.info_zika))){
			ConsentimientoZika cons = (ConsentimientoZika) getListAdapter().getItem(position);
			arguments.putString(Constants.TITLE, getString(R.string.info_zika));
			if (cons!=null) arguments.putSerializable(Constants.OBJECTO , cons);
			i = new Intent(getApplicationContext(),
					ReviewActivity.class);
		}
        if (titulo.matches(getString(R.string.info_zika))){
            ConsentimientoZika cons = (ConsentimientoZika) getListAdapter().getItem(position);
            arguments.putString(Constants.TITLE, getString(R.string.info_zika));
            if (cons!=null) arguments.putSerializable(Constants.OBJECTO , cons);
            i = new Intent(getApplicationContext(),
                    ReviewActivity.class);
        }
        if (titulo.matches(getString(R.string.datos_parto))){
            DatosPartoBB datosParto = (DatosPartoBB) getListAdapter().getItem(position);
            arguments.putString(Constants.TITLE, getString(R.string.datos_parto));
            if (datosParto!=null) arguments.putSerializable(Constants.OBJECTO , datosParto);
            i = new Intent(getApplicationContext(),
                    ReviewActivity.class);
        }
        if (titulo.matches(getString(R.string.datos_casa))){
            DatosVisitaTerreno datosVisita = (DatosVisitaTerreno) getListAdapter().getItem(position);
            arguments.putString(Constants.TITLE, getString(R.string.datos_casa));
            if (datosVisita!=null) arguments.putSerializable(Constants.OBJECTO , datosVisita);
            i = new Intent(getApplicationContext(),
                    ReviewActivity.class);
        }
        if (titulo.matches(getString(R.string.info_docs))){
            Documentos documento = (Documentos) getListAdapter().getItem(position);
            arguments.putString(Constants.TITLE, getString(R.string.info_docs));
            if (documento!=null) arguments.putSerializable(Constants.OBJECTO , documento);
            i = new Intent(getApplicationContext(),
                    ReviewActivity.class);
        }
        if (titulo.matches(getString(R.string.main_survey))){
            EncuestaSatisfaccion cons = (EncuestaSatisfaccion) getListAdapter().getItem(position);
            arguments.putString(Constants.TITLE, getString(R.string.main_survey));
            if (cons!=null) arguments.putSerializable(Constants.OBJECTO , cons);
            i = new Intent(getApplicationContext(),
                    ReviewActivity.class);
        }
        if (titulo.matches(getString(R.string.info_sa))){
            ParticipanteSeroprevalencia cons = (ParticipanteSeroprevalencia) getListAdapter().getItem(position);
            arguments.putString(Constants.TITLE, getString(R.string.info_sa));
            if (cons!=null) arguments.putSerializable(Constants.OBJECTO , cons);
            i = new Intent(getApplicationContext(),
                    ReviewActivity.class);
        }
        if (titulo.matches(getString(R.string.info_coordenadas))){
            DatosCoordenadas cons = (DatosCoordenadas) getListAdapter().getItem(position);
            arguments.putString(Constants.TITLE, getString(R.string.info_coordenadas));
            if (cons!=null) arguments.putSerializable(Constants.OBJECTO , cons);
            i = new Intent(getApplicationContext(),
                    ReviewActivity.class);
        }
		i.putExtras(arguments);
		startActivity(i);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		Integer codigoScanned =0;
		if (requestCode == BARCODE_CAPTURE && intent != null && titulo.matches(getString(R.string.info_codscasa))) {
			String sb = intent.getStringExtra("SCAN_RESULT");
			if (sb != null && sb.length() > 0) {

				try{
					codigoScanned = Integer.parseInt(sb);
					ca.open();
					Participante mParticipante = ca.getParticipante(codigoScanned);

					if (mParticipante.getCodigo() != null){
						codigo = mParticipante.getCodigo();
						if (codigo.intValue() == codComun.intValue()){
							showToast("Es el mismo codigo");
							return;
						}
						if(ca.checkCodigosCasas(codigoScanned)) {
							showToast("Ya existe relacionado a otro codigo");
						}
						else{
							createConfirmDialog();
						}
					}
					else {
						//mSearchText.setText(sb);
						showToast("("+codigoScanned+") - " + getString(R.string.code_notfound));
					}
                    ca.close();
				}
				catch(Exception e){
					showToast(getString(R.string.scan_error));
					return;
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, intent);

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
	
	private void createConfirmDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(this.getString(R.string.confirm));
		builder.setMessage("Desea relacionar al participante " + codigo + " con el codigo " + codComun + "?");
		builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Finish app
				dialog.dismiss();
				CodigosCasas cc = new CodigosCasas();
				cc.setFechaRegistro(new Date());
				cc.setCodCasa(codCasa);
				cc.setCodigoComun(codComun);
				cc.setCodigoRelacionado(codigo);
				cc.setEstado(Constants.STATUS_NOT_SUBMITTED);
				cc.setUsername(username);
				ca.open();
				ca.crearCodigosCasas(cc);
				ca.close();
				finish();
				Intent i = new Intent(getApplicationContext(),
						MenuInfoActivity.class);
				i.putExtra(ConstantsDB.COD_CASA, codCasa);
				i.putExtra(ConstantsDB.CODIGO, codComun);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
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
}
