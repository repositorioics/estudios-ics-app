package ni.org.ics.estudios.appmovil.entomologia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogar;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioPuntoClave;
import ni.org.ics.estudios.appmovil.utils.DateUtil;

import java.text.DateFormat;
import java.util.List;

public class CuestionarioPuntoClaveAdapter extends ArrayAdapter<CuestionarioPuntoClave> {

	public CuestionarioPuntoClaveAdapter(Context context, int textViewResourceId,
                                         List<CuestionarioPuntoClave> items) {
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
		CuestionarioPuntoClave encuesta = getItem(position);
		if (encuesta != null) {

			TextView textView = (TextView) v.findViewById(R.id.name_text);
			if (textView != null) {
                String text = encuesta.getNombrePuntoClave();
                textView.setText(text);
			}

			textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
				textView.setText(DateUtil.DateToString(encuesta.getFechaCuestionario(), "dd/MM/yyyy"));
			}

			textView = (TextView) v.findViewById(R.id.infoc_text);
			if (textView != null) {
				String text = encuesta.getCodigoSitio();
				textView.setText(text);
			}
		}
		return v;
	}
}
