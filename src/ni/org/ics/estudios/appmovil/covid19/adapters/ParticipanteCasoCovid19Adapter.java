package ni.org.ics.estudios.appmovil.covid19.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;

import java.text.SimpleDateFormat;
import java.util.List;

public class ParticipanteCasoCovid19Adapter extends ArrayAdapter<ParticipanteCasoCovid19> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private List<MessageResource> catalogos;
	public ParticipanteCasoCovid19Adapter(Context context, int textViewResourceId,
                                          List<ParticipanteCasoCovid19> items, List<MessageResource> catalogos) {
		super(context, textViewResourceId, items);
		this.catalogos = catalogos;
	}

	private String getDescripcionCatalogo(String codigo, String catalogo){
		for(MessageResource tipoMuestra : catalogos){
			if (tipoMuestra.getCatRoot().equalsIgnoreCase(catalogo) && tipoMuestra.getCatKey().equalsIgnoreCase(codigo))
				return tipoMuestra.getSpanish();
		}
		return "-";
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.complex_list_item, null);
		}
		ParticipanteCasoCovid19 p = getItem(position);
		if (p != null && p.getParticipante()!=null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.code) + ": " + p.getParticipante().getCodigo());
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getParticipante().getFechaNac()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			if (!p.getEnfermo().equalsIgnoreCase("N")) {
				textView.setTextColor(Color.RED);
			}else{
				textView.setTextColor(Color.BLACK);
			}
			String nameCompleto = p.getParticipante().getNombreCompleto();
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
			textView = (TextView) v.findViewById(R.id.infoc_text);
			textView.setTextColor(Color.GRAY);
			if (textView != null) {

				if (!p.getEnfermo().equalsIgnoreCase("N")) {
					if (p.getPositivoPor() != null && p.getFis() != null) {
						textView.setText(this.getContext().getString(R.string.positivoPorUO1) + ": " + this.getDescripcionCatalogo(p.getPositivoPor(), "COVID_CAT_POSITIVO_POR") + " - " + formatter.format(p.getFis()));
					} else {
						if (p.getPositivoPor() != null) {
							textView.setText(this.getContext().getString(R.string.positivoPorUO1) + ": " + this.getDescripcionCatalogo(p.getPositivoPor(), "COVID_CAT_POSITIVO_POR"));
						}
						if (p.getFis() != null) {
							textView.setText(mDateFormat.format(p.getFis()));
						}
					}
				}else{
					textView.setText("");
				}
			}
		}
		return v;
	}
}
