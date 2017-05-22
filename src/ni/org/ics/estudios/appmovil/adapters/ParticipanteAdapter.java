package ni.org.ics.estudios.appmovil.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.Participante;
import java.text.SimpleDateFormat;
import java.util.List;

public class ParticipanteAdapter extends ArrayAdapter<Participante> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public ParticipanteAdapter(Context context, int textViewResourceId,
                          List<Participante> items) {
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
		Participante p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.code) + ": " + p.getCodigo());
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getFechaNac()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			String nameCompleto = p.getNombre1();
			if (p.getNombre2()!=null) nameCompleto = nameCompleto + " "+  p.getNombre2();
			nameCompleto = nameCompleto +" "+ p.getApellido1();
			if (p.getApellido2()!=null) nameCompleto = nameCompleto + " "+  p.getApellido2();
			if (textView != null) {
				textView.setText(nameCompleto);
			}
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				if (p.getSexo().equals("M")) {
					imageView.setImageResource(R.drawable.male);
				} else if (p.getSexo().equals("F")) {
					imageView.setImageResource(R.drawable.female);
				}
			}
		}
		return v;
	}
}
