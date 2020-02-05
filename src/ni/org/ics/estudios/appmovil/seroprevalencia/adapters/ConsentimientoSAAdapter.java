package ni.org.ics.estudios.appmovil.seroprevalencia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;

import java.text.DateFormat;
import java.util.List;

public class ConsentimientoSAAdapter extends ArrayAdapter<ParticipanteSeroprevalencia> {

	DateFormat mediumDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);


	public ConsentimientoSAAdapter(Context context, int textViewResourceId,
                                   List<ParticipanteSeroprevalencia> items) {
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
        ParticipanteSeroprevalencia seroprevalencia = getItem(position);
		if (seroprevalencia != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
				textView.setText(seroprevalencia.getParticipante().getCodigo().toString());
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			if (textView != null) {
				textView.setText(mediumDf.format(seroprevalencia.getRecordDate()));
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
				textView.setText(seroprevalencia.getRecordUser());
			}
		}
		return v;
	}
}
