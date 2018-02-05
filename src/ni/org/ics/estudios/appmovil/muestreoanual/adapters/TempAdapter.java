package ni.org.ics.estudios.appmovil.muestreoanual.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.TempPbmc;

import java.text.DateFormat;
import java.util.List;

public class TempAdapter extends ArrayAdapter<TempPbmc> {
	
	DateFormat mediumDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);

	public TempAdapter(Context context, int textViewResourceId,
                       List<TempPbmc> items) {
		super(context, textViewResourceId, items);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.complex_list_item, null);
		}
		TempPbmc temp = getItem(position);
		if (temp != null) {
			TextView textView = (TextView) v.findViewById(R.id.name_text);
			if (textView != null) {
				textView.setText("Lab: " + temp.getTempPbmcId().getRecurso());
			}

			textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
				textView.setText(mediumDf.format(temp.getTempPbmcId().getFechaTempPbmc()));
			}

			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
					textView.setText(temp.getLugar());
			}
		}
		return v;
	}
}
