package ni.org.ics.estudios.appmovil.muestreoanual.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa;

import java.text.DateFormat;
import java.util.List;

public class EncuestaCasaAdapter extends ArrayAdapter<EncuestaCasa> {

	DateFormat mediumDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);
	
	
	public EncuestaCasaAdapter(Context context, int textViewResourceId,
			List<EncuestaCasa> items) {
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
		EncuestaCasa encuesta = getItem(position);
		if (encuesta != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
                String text = "";
                if (encuesta.getCodCasa()!=null && encuesta.getCodCasa()>0)
                    text = "Casa "+encuesta.getCodCasa().toString();

                if (encuesta.getCodCasaChf()!=null)
                    text += " - Casa CHF " + encuesta.getCodCasaChf();

                textView.setText(text);
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			if (textView != null) {
				textView.setText(mediumDf.format(encuesta.getFechaEncCasa()));
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
				textView.setText(encuesta.getMovilInfo().getUsername());
			}
		}
		return v;
	}
}
