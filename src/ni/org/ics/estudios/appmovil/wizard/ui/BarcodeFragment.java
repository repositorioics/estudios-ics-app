package ni.org.ics.estudios.appmovil.wizard.ui;

import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.utils.zbar.SimpleScannerActivity;
import ni.org.ics.estudios.appmovil.utils.zbar.ZBarConstants;
import ni.org.ics.estudios.appmovil.wizard.model.Page;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class BarcodeFragment extends Fragment {
	protected static final String ARG_KEY = "key";
	protected static final int BARCODE_CAPTURE = 2;

	private PageFragmentCallbacks mCallbacks;
	private String mKey;
	private Page mPage;

	protected TextView mTitleTextInput;
	protected TextView mHintTextInput;
	protected EditText mEditTextInput;
	protected ImageButton mButtonBarcode;

	private static final int ZBAR_QR_SCANNER_REQUEST = 1;

	public static BarcodeFragment create(String key) {
		Bundle args = new Bundle();
		args.putString(ARG_KEY, key);

		BarcodeFragment f = new BarcodeFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		mKey = args.getString(ARG_KEY);
		mPage = mCallbacks.onGetPage(mKey);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_page_barcode,
				container, false);
		
		mTitleTextInput = (TextView) rootView.findViewById(android.R.id.title);
		mTitleTextInput.setText(mPage.getTitle());
		mTitleTextInput.setTextColor(Color.parseColor(mPage.getTextColor()));
		
		mHintTextInput = (TextView) rootView.findViewById(R.id.label_hint);
		mHintTextInput.setText(mPage.getHint());
		mHintTextInput.setTextColor(Color.parseColor(mPage.getmHintTextColor()));
		
		mEditTextInput = (EditText) rootView.findViewById(R.id.editTextInput);
		mEditTextInput.setText(mPage.getData().getString(Page.SIMPLE_DATA_KEY));
		mEditTextInput.setEnabled(false);
		mButtonBarcode = (ImageButton) rootView.findViewById(R.id.barcode_button);
		mButtonBarcode.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				//Intent i = new Intent("com.google.zxing.client.android.SCAN");
				Intent intent = new Intent(getActivity().getApplicationContext(), SimpleScannerActivity.class);
				try {
					//startActivityForResult(i, BARCODE_CAPTURE);
					startActivityForResult(intent, ZBAR_QR_SCANNER_REQUEST);
				} catch (ActivityNotFoundException e) {
					
				}
			}
		});
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (!(activity instanceof PageFragmentCallbacks)) {
			throw new ClassCastException(
					"Activity must implement PageFragmentCallbacks");
		}

		mCallbacks = (PageFragmentCallbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		//if (requestCode == BARCODE_CAPTURE && intent != null) {
		if (requestCode == ZBAR_QR_SCANNER_REQUEST && intent != null) {
			if (mPage.getmCodePosicion()<0) {//tomar en cuenta el texto completo
                //mEditTextInput.setText(intent.getStringExtra("SCAN_RESULT"));
				mEditTextInput.setText(intent.getStringExtra(ZBarConstants.SCAN_RESULT));
                mPage.getData().putString(Page.SIMPLE_DATA_KEY, intent.getStringExtra("SCAN_RESULT"));

            }else {
                //String code = intent.getStringExtra("SCAN_RESULT");
				String code = intent.getStringExtra(ZBarConstants.SCAN_RESULT);
                if (code.contains("  ")) code = code.replaceAll(" {2}", " "); //Covid19
                String[] codeparts = code.split(" ");
                if (codeparts.length > mPage.getmCodePosicion() && codeparts[mPage.getmCodePosicion()] != null){
                	code = codeparts[mPage.getmCodePosicion()];
				}
				mEditTextInput.setText(code.trim());
                mPage.getData().putString(Page.SIMPLE_DATA_KEY, code);
            }
            mPage.notifyDataChanged();
		}
		super.onActivityResult(requestCode, resultCode, intent);

	}
}
