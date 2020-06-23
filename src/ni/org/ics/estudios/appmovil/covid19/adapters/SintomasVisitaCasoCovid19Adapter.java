package ni.org.ics.estudios.appmovil.covid19.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCasoSintomas;
import ni.org.ics.estudios.appmovil.domain.covid19.SintomasVisitaCasoCovid19;

import java.text.SimpleDateFormat;
import java.util.List;

public class SintomasVisitaCasoCovid19Adapter extends ArrayAdapter<SintomasVisitaCasoCovid19> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");

	public SintomasVisitaCasoCovid19Adapter(Context context, int textViewResourceId,
                                            List<SintomasVisitaCasoCovid19> items) {
		super(context, textViewResourceId, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.complex_list_item, null);
		}
		SintomasVisitaCasoCovid19 p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.name_text);
			
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getFechaSintomas()));
			}
			
			
		}
		return v;
	}
}
