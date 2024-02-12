package ni.org.ics.estudios.appmovil.muestreoanual.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.RecepcionPbmc;

import java.util.List;

public class PBMCAdapter extends ArrayAdapter<RecepcionPbmc> {

    public PBMCAdapter(Context context, int textViewResourceId,
                      List<RecepcionPbmc> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.complex_list_item, null);
        }
        RecepcionPbmc tubo = getItem(position);
        if (tubo != null) {
            TextView textView = (TextView) v.findViewById(R.id.name_text);
            if (textView != null) {
                textView.setText(this.getContext().getString(R.string.code) + " " + tubo.getCodigo());
                if (tubo.getRojoAdicional()) {
                    textView.append("   -   Con rojo adicional");
                }
            }

            textView = (TextView) v.findViewById(R.id.identifier_text);
            if (textView != null) {
                textView.setText(tubo.getFechaPbmc());
            }

            textView = (TextView) v.findViewById(R.id.der_text);
            if (textView != null) {
                textView.setText(tubo.getLugar());
            }

        }
        return v;
    }
}
