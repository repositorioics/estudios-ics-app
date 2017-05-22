package ni.org.ics.estudios.appmovil.cohortefamilia.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Habitacion;
import java.text.SimpleDateFormat;
import java.util.List;

public class HabitacionAdapter extends ArrayAdapter<Habitacion> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public HabitacionAdapter(Context context, int textViewResourceId,
                          List<Habitacion> items) {
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
		Habitacion p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.cantidadCamas) + ": " + p.getCantidadCamas());
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getRecordDate()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.codigoHabitacion) + ": " + p.getCodigoHabitacion());
			}
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				if (p.getAncho()!=null) {
					imageView.setImageResource(R.drawable.proposed_house);
				} else {
					imageView.setImageResource(R.drawable.no_proposed_house);
				}
			}
		}
		return v;
	}
}
