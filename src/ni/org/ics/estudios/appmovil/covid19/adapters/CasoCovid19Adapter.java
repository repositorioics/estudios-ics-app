package ni.org.ics.estudios.appmovil.covid19.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.covid19.CasoCovid19;

import java.text.SimpleDateFormat;
import java.util.List;

public class CasoCovid19Adapter extends ArrayAdapter<CasoCovid19> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");

	public CasoCovid19Adapter(Context context, int textViewResourceId,
							  List<CasoCovid19> items) {
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
		CasoCovid19 p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				if (p.getCasa()!=null) {
					textView.setText(this.getContext().getString(R.string.casa) + ": " + p.getCasa().getCodigoCHF());
				}else
					textView.setText(this.getContext().getString(R.string.participant) + ": PENDIENTE");
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getFechaIngreso()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTextColor(Color.BLACK);
			String nameCompleto = "";
			if (p.getCasa()!=null) {
				nameCompleto = p.getCasa().getNombre1JefeFamilia() + " " + p.getCasa().getApellido1JefeFamilia();
			}else nameCompleto = "PENDIENTE";
			if (textView != null) {
				textView.setText(nameCompleto);
			}
		}
		return v;
	}
}
