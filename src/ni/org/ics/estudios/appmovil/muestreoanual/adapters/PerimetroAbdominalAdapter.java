package ni.org.ics.estudios.appmovil.muestreoanual.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.PerimetroAbdominal;

import java.text.DateFormat;
import java.util.List;

public class PerimetroAbdominalAdapter extends ArrayAdapter<PerimetroAbdominal> {

    DateFormat mediumDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);

    public PerimetroAbdominalAdapter(Context context, int textViewResourceId,
                             List<PerimetroAbdominal> items) {
        super(context, textViewResourceId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.complex_list_item , null);
        }
        PerimetroAbdominal perimetroAbdominal = getItem(position);
        if (perimetroAbdominal != null) {

            TextView textView = (TextView) v.findViewById(R.id.identifier_text);
            if (textView != null) {
                textView.setText(perimetroAbdominal.getPaId().getCodigo().toString());
            }

            textView = (TextView) v.findViewById(R.id.name_text);
            if (textView != null) {
                textView.setText(mediumDf.format(perimetroAbdominal.getPaId().getFecha()));
            }

            textView = (TextView) v.findViewById(R.id.der_text);
            if (textView != null) {
                textView.setText(perimetroAbdominal.getMovilInfo().getUsername());
            }
        }
        return v;
    }
}
