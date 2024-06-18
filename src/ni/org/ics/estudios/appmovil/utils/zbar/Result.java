package ni.org.ics.estudios.appmovil.utils.zbar;

public class Result {
    private String mContents;
    private ZBarcodeFormat mBarcodeFormat;

    public void setContents(String contents) {
        mContents = contents;
    }

    public void setBarcodeFormat(ZBarcodeFormat format) {
        mBarcodeFormat = format;
    }

    public ZBarcodeFormat getBarcodeFormat() {
        return mBarcodeFormat;
    }

    public String getContents() {
        return mContents;
    }
}
