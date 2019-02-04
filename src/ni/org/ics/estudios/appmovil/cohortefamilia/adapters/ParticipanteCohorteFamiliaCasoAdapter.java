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
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.ParticipanteCohorteFamiliaCasoData;

import java.text.SimpleDateFormat;
import java.util.List;

public class ParticipanteCohorteFamiliaCasoAdapter extends ArrayAdapter<ParticipanteCohorteFamiliaCasoData> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public ParticipanteCohorteFamiliaCasoAdapter(Context context, int textViewResourceId,
                          List<ParticipanteCohorteFamiliaCasoData> items) {
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
		ParticipanteCohorteFamiliaCasoData p = getItem(position);
		if (p != null && p.getParticipante()!=null && p.getParticipante().getParticipante()!=null && p.getParticipante().getParticipante().getParticipante()!=null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.code) + ": " + p.getParticipante().getParticipante().getParticipante().getCodigo());
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getParticipante().getParticipante().getParticipante().getFechaNac()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTextColor(Color.BLACK);
			String nameCompleto = p.getParticipante().getParticipante().getParticipante().getNombreCompleto();
			if (textView != null) {
				textView.setText(nameCompleto);
			}
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				if (p.getParticipante().getParticipante().getParticipante().getSexo().equals("M")) {
					imageView.setImageResource(R.drawable.male);
				} else if (p.getParticipante().getParticipante().getParticipante().getSexo().equals("F")) {
					imageView.setImageResource(R.drawable.female);
				}
			}
			textView = (TextView) v.findViewById(R.id.infoc_text);
			textView.setTextColor(Color.GRAY);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.numVisits) + String.valueOf(p.getNumVisitas()));
				if(p.getFechaUltimaVisita()!=null){
					mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					textView.setText(textView.getText().toString()	+ " " + this.getContext().getString(R.string.ultVisit) + mDateFormat.format(p.getFechaUltimaVisita()));
				}
			}
		}
		return v;
	}
}
