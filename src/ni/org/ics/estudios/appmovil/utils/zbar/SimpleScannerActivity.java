package ni.org.ics.estudios.appmovil.utils.zbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import ni.org.ics.estudios.appmovil.R;

public class SimpleScannerActivity extends Activity implements ZBarScannerView.ResultHandler, ZBarConstants {

    private ZBarScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_scanner);

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZBarScannerView(this);
        contentFrame.addView(mScannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        if (rawResult != null) {
            ZBarcodeFormat zBarcodeFormat = ZBarcodeFormat.getFormatById(rawResult.getBarcodeFormat().getId());
            if (!zBarcodeFormat.getName().trim().equals("NONE")) {
                Intent dataIntent = new Intent();
                dataIntent.putExtra(SCAN_RESULT, rawResult.getContents());
                dataIntent.putExtra(SCAN_RESULT_TYPE, rawResult.getBarcodeFormat().getId());
                setResult(Activity.RESULT_OK, dataIntent);
                mScannerView.stopCamera();
                finish();
            }
        }
    }
}
