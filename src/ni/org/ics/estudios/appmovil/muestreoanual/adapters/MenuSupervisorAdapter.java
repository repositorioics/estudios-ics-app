package ni.org.ics.estudios.appmovil.muestreoanual.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;

public class MenuSupervisorAdapter extends ArrayAdapter<String> {

	private final String[] values;
	public MenuSupervisorAdapter(Context context, int textViewResourceId,
                                 String[] values) {
		super(context, textViewResourceId, values);
		this.values = values;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.menu_list, null);
		}
		ImageView imageView = (ImageView) v.findViewById(R.id.icon);
		TextView textView = (TextView) v.findViewById(R.id.label);
		textView.setText(values[position]);

		// Change icon based on position
		
		switch (position){
			case 0: imageView.setImageResource(R.drawable.bhctubes);
					break;
			case 1: imageView.setImageResource(R.drawable.redtubes);
				break;
			case 2: imageView.setImageResource(R.drawable.redtubes);
				break;
			case 3: imageView.setImageResource(R.drawable.blue_thermo);
				break;
			case 4: imageView.setImageResource(R.drawable.red_thermo);
				break;
			case 5: imageView.setImageResource(R.drawable.ic_pinchazos);
				break;
			default: imageView.setImageResource(R.drawable.ic_launcher);
					break;
			}

		return v;
	}
}
