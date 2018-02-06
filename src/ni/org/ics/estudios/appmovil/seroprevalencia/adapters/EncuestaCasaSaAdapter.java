package ni.org.ics.estudios.appmovil.seroprevalencia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaCasaSA;

import java.text.DateFormat;
import java.util.List;

public class EncuestaCasaSaAdapter extends ArrayAdapter<EncuestaCasaSA> {

	DateFormat mediumDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);
	
	
	public EncuestaCasaSaAdapter(Context context, int textViewResourceId,
                                 List<EncuestaCasaSA> items) {
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
        EncuestaCasaSA encuesta = getItem(position);
		if (encuesta != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
                textView.setText("Casa "+encuesta.getCasa().getCodigo().toString());
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			if (textView != null) {
				textView.setText(mediumDf.format(encuesta.getRecordDate()));
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
				textView.setText(encuesta.getRecordUser());
			}
		}
		return v;
	}
}
