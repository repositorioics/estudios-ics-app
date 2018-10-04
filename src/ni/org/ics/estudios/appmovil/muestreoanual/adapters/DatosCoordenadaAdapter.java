package ni.org.ics.estudios.appmovil.muestreoanual.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.DatosCoordenadas;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.DatosVisitaTerreno;

import java.text.DateFormat;
import java.util.List;

public class DatosCoordenadaAdapter extends ArrayAdapter<DatosCoordenadas> {

	DateFormat mediumDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);


	public DatosCoordenadaAdapter(Context context, int textViewResourceId,
                                  List<DatosCoordenadas> items) {
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
        DatosCoordenadas coordenadas = getItem(position);
		if (coordenadas != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
				textView.setText(coordenadas.getParticipante().getCodigo().toString());
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			if (textView != null) {
				textView.setText(mediumDf.format(coordenadas.getMovilInfo().getToday()));
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
				textView.setText(coordenadas.getMovilInfo().getUsername());
			}
		}
		return v;
	}
}
