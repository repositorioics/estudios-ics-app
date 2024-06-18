package ni.org.ics.estudios.appmovil.utils.zbar;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import java.util.Iterator;
import net.sourceforge.zbar.*;
import ni.org.ics.estudios.appmovil.utils.zbar.core.BarcodeScannerView;
import ni.org.ics.estudios.appmovil.utils.zbar.core.DisplayUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

public class ZBarScannerView extends BarcodeScannerView {
    private static final String TAG = "ZBarScannerView";
    private ImageScanner mScanner;
    private List<ZBarcodeFormat> mFormats;
    private ZBarScannerView.ResultHandler mResultHandler;

    public ZBarScannerView(Context context) {
        super(context);
        this.setupScanner();
    }

    public ZBarScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setupScanner();
    }

    public void setFormats(List<ZBarcodeFormat> formats) {
        this.mFormats = formats;
        this.setupScanner();
    }

    public void setResultHandler(ZBarScannerView.ResultHandler resultHandler) {
        this.mResultHandler = resultHandler;
    }

    public Collection<ZBarcodeFormat> getFormats() {
        return this.mFormats == null ? ZBarcodeFormat.ALL_FORMATS : this.mFormats;
    }

    public void setupScanner() {
        this.mScanner = new ImageScanner();
        this.mScanner.setConfig(0, 256, 3);
        this.mScanner.setConfig(0, 257, 3);
        this.mScanner.setConfig(0, 0, 0);
        /*Iterator var1 = this.getFormats().iterator();

        while(var1.hasNext()) {
            ZBarcodeFormat format = (ZBarcodeFormat)var1.next();
            this.mScanner.setConfig(format.getId(), 0, 1);
        }*/
        this.mScanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
        for(ZBarcodeFormat format : getFormats()) {
            this.mScanner.setConfig(format.getId(), Config.ENABLE, 1);
        }
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        if (this.mResultHandler != null) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size size = parameters.getPreviewSize();
                int width = size.width;
                int height = size.height;
                if (DisplayUtils.getScreenOrientation(this.getContext()) == 1) {
                    int rotationCount = this.getRotationCount();
                    if (rotationCount == 1 || rotationCount == 3) {
                        int tmp = width;
                        width = height;
                        height = tmp;
                    }

                    data = this.getRotatedData(data, camera);
                }

                Rect rect = this.getFramingRectInPreview(width, height);
                Image barcode = new Image(width, height, "Y800");
                barcode.setData(data);
                barcode.setCrop(rect.left, rect.top, rect.width(), rect.height());
                int result = this.mScanner.scanImage(barcode);
                if (result != 0) {
                    SymbolSet syms = this.mScanner.getResults();
                    final Result rawResult = new Result();
                    Iterator var12 = syms.iterator();

                    while(var12.hasNext()) {
                        Symbol sym = (Symbol)var12.next();
                        String symData;
                        if (Build.VERSION.SDK_INT >= 19) {
                            symData = new String(sym.getDataBytes(), StandardCharsets.UTF_8);
                        } else {
                            symData = sym.getData();
                        }

                        if (!TextUtils.isEmpty(symData)) {
                            rawResult.setContents(symData);
                            rawResult.setBarcodeFormat(ZBarcodeFormat.getFormatById(sym.getType()));
                            break;
                        }
                    }

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            ZBarScannerView.ResultHandler tmpResultHandler = ZBarScannerView.this.mResultHandler;
                            ZBarScannerView.this.mResultHandler = null;
                            ZBarScannerView.this.stopCameraPreview();
                            if (tmpResultHandler != null) {
                                tmpResultHandler.handleResult(rawResult);
                            }

                        }
                    });
                } else {
                    camera.setOneShotPreviewCallback(this);
                }
            } catch (RuntimeException var15) {
                Log.e("ZBarScannerView", var15.toString(), var15);
            }

        }
    }

    public void resumeCameraPreview(ZBarScannerView.ResultHandler resultHandler) {
        this.mResultHandler = resultHandler;
        super.resumeCameraPreview();
    }

    static {
        System.loadLibrary("iconv");
    }

    public interface ResultHandler {
        void handleResult(Result var1);
    }
}
