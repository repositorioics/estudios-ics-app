package ni.org.ics.estudios.appmovil.cohortefamilia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;

import java.text.SimpleDateFormat;
import java.util.List;

public class ParticipanteCHFAdapter extends ArrayAdapter<ParticipanteCohorteFamilia> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public ParticipanteCHFAdapter(Context context, int textViewResourceId,
                          List<ParticipanteCohorteFamilia> items) {
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
		ParticipanteCohorteFamilia p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.code) + ": " + p.getParticipante().getCodigo());
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getParticipante().getFechaNac()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			String nameCompleto = p.getParticipante().getNombre1();
			if (p.getParticipante().getNombre2()!=null) nameCompleto = nameCompleto + " "+  p.getParticipante().getNombre2();
			nameCompleto = nameCompleto +" "+ p.getParticipante().getApellido1();
			if (p.getParticipante().getApellido2()!=null) nameCompleto = nameCompleto + " "+  p.getParticipante().getApellido2();
			if (textView != null) {
				textView.setText(nameCompleto);
			}
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				if (p.getParticipante().getSexo().equals("M")) {
					imageView.setImageResource(R.drawable.male);
				} else if (p.getParticipante().getSexo().equals("F")) {
					imageView.setImageResource(R.drawable.female);
				}
			}
		}
		return v;
	}
}
