package ni.org.ics.estudios.appmovil.cohortefamilia.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.FormularioContactoCaso;

import java.text.SimpleDateFormat;
import java.util.List;

public class FormularioContactoCasoAdapter extends ArrayAdapter<FormularioContactoCaso> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public FormularioContactoCasoAdapter(Context context, int textViewResourceId,
                          List<FormularioContactoCaso> items) {
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
		FormularioContactoCaso p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.name_text);
			
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(p.getPartContacto().getParticipante().getNombreCompleto());
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getFechaContacto()));
			}
			
			textView = (TextView) v.findViewById(R.id.identifier_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.code) + ": " + p.getPartContacto().getParticipante().getCodigo());
			}
			
			
		}
		return v;
	}
}
