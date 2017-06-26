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
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCaso;

import java.text.SimpleDateFormat;
import java.util.List;

public class ParticipanteCohorteFamiliaCasoAdapter extends ArrayAdapter<ParticipanteCohorteFamiliaCaso> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public ParticipanteCohorteFamiliaCasoAdapter(Context context, int textViewResourceId,
                          List<ParticipanteCohorteFamiliaCaso> items) {
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
		ParticipanteCohorteFamiliaCaso p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.code) + ": " + p.getParticipante().getParticipante().getCodigo());
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getParticipante().getParticipante().getFechaNac()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTextColor(Color.BLACK);
			String nameCompleto = p.getParticipante().getParticipante().getNombreCompleto();
			if (textView != null) {
				textView.setText(nameCompleto);
			}
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				if (p.getParticipante().getParticipante().getSexo().equals("M")) {
					imageView.setImageResource(R.drawable.male);
				} else if (p.getParticipante().getParticipante().getSexo().equals("F")) {
					imageView.setImageResource(R.drawable.female);
				}
			}
		}
		return v;
	}
}
