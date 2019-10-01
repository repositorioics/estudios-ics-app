package ni.org.ics.estudios.appmovil.influenzauo1.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.VisitaSeguimientoCasoSintomas;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.SintomasVisitaCasoUO1;

import java.text.SimpleDateFormat;
import java.util.List;

public class SintomasVisitaCasoUO1Adapter extends ArrayAdapter<SintomasVisitaCasoUO1> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");

	public SintomasVisitaCasoUO1Adapter(Context context, int textViewResourceId,
                                        List<SintomasVisitaCasoUO1> items) {
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
		SintomasVisitaCasoUO1 p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null && p.getFechaSintomas()!=null) {
				textView.setText(this.getContext().getString(R.string.diaSintomaUO1)+": "+p.getDia()+ " - " + mDateFormat.format(p.getFechaSintomas()));
			}
			
		}
		return v;
	}
}
