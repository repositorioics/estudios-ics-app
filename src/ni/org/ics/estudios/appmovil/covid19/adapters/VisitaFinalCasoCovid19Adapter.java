package ni.org.ics.estudios.appmovil.covid19.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaFinalCasoCovid19;

import java.text.SimpleDateFormat;
import java.util.List;

public class VisitaFinalCasoCovid19Adapter extends ArrayAdapter<VisitaFinalCasoCovid19> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");

	public VisitaFinalCasoCovid19Adapter(Context context, int textViewResourceId,
                                         List<VisitaFinalCasoCovid19> items) {
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
		VisitaFinalCasoCovid19 p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			
			textView = (TextView) v.findViewById(R.id.der_text);
			textView.setTextColor(Color.BLACK);
            textView.setText(mDateFormat.format(p.getFechaVisita()));

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTextColor(Color.BLACK);
            textView.setText(this.getContext().getString(R.string.visit_final));


		}
		return v;
	}
}
