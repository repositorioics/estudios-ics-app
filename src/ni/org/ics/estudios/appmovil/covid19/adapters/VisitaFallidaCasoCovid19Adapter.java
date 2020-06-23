package ni.org.ics.estudios.appmovil.covid19.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.domain.covid19.VisitaFallidaCasoCovid19;

import java.text.SimpleDateFormat;
import java.util.List;

public class VisitaFallidaCasoCovid19Adapter extends ArrayAdapter<VisitaFallidaCasoCovid19> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
	private List<MessageResource> razonNoVisita;

	public VisitaFallidaCasoCovid19Adapter(Context context, int textViewResourceId,
                                           List<VisitaFallidaCasoCovid19> items, List<MessageResource> razonNoVisita) {
		super(context, textViewResourceId, items);
		this.razonNoVisita = razonNoVisita;
	}

	private String getDescripcionCatalogo(String codigo){
        for(MessageResource rnv : razonNoVisita){
            if (rnv.getCatKey().equalsIgnoreCase(codigo))
                return rnv.getSpanish();
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
		VisitaFallidaCasoCovid19 p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			
			textView = (TextView) v.findViewById(R.id.der_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getFechaVisita()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
                if (p.getVisita() != null && !p.getVisita().isEmpty()) {
                    textView.setText(this.getContext().getString(R.string.visit) + ": " + p.getVisita() + " - " + getDescripcionCatalogo(p.getRazonVisitaFallida()));
                } else {
                    textView.setText(getDescripcionCatalogo(p.getRazonVisitaFallida()));
                }
            }
			
		}
		return v;
	}
}
