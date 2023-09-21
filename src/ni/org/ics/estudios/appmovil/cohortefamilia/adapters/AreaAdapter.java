package ni.org.ics.estudios.appmovil.cohortefamilia.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.AreaAmbiente;
import java.text.SimpleDateFormat;
import java.util.List;

public class AreaAdapter extends ArrayAdapter<AreaAmbiente> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public AreaAdapter(Context context, int textViewResourceId,
                          List<AreaAmbiente> items) {
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
		AreaAmbiente p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.tipo) + ": " + p.getTipo());
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getRecordDate()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.largo) + ": " + p.getLargo() + " - " + this.getContext().getString(R.string.ancho) + ": " + p.getAncho()+" - " +this.getContext().getString(R.string.area) + ": " + p.getTotalM2());
			}

			if (p.getTipo().equalsIgnoreCase("habitacion")) {
				textView = (TextView) v.findViewById(R.id.infoc_text);
				textView.setTextColor(Color.BLUE);
				if (textView != null) {
					textView.setText("Cuarto: "+p.getNumeroCuarto());
				}
			}else{
				textView = (TextView) v.findViewById(R.id.infoc_text);
				textView.setTextColor(Color.BLUE);
				textView.setText("");
			}
		}
		return v;
	}
}
