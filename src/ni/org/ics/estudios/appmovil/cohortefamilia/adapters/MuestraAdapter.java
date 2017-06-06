package ni.org.ics.estudios.appmovil.cohortefamilia.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;

import java.text.SimpleDateFormat;
import java.util.List;

public class MuestraAdapter extends ArrayAdapter<Muestra> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
    private List<MessageResource> tiposMuestra;
	public MuestraAdapter(Context context, int textViewResourceId,
                          List<Muestra> items, List<MessageResource> tiposMuestra) {
		super(context, textViewResourceId, items);
        this.tiposMuestra = tiposMuestra;
	}

    private String getDescripcionCatalogo(String codigo, String catalogo){
        for(MessageResource tipoMuestra : tiposMuestra){
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
		Muestra p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
            textView.setTextSize(18);
            if (p.getTubo().equalsIgnoreCase("1"))
                textView.setTextColor(Color.RED);
            else if (p.getTubo().equalsIgnoreCase("2"))
                textView.setTextColor(Color.MAGENTA);
            else if (p.getTubo().equalsIgnoreCase("3")){
                textView.setTextColor(Color.BLUE);
            }else{
                textView.setTextColor(Color.BLACK);
            }
            if (textView != null) {
				textView.setText(getDescripcionCatalogo(p.getTubo(), "CHF_CAT_TIP_TUBO_MX"));
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			textView.setTextColor(Color.BLACK);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getRecordDate()));
			}
            textView = (TextView) v.findViewById(R.id.name_text);
            textView.setTextColor(Color.BLACK);
            if (textView != null) {
                if (p.getRazonNoToma() == null) {
                    textView.setText(this.getContext().getString(R.string.hora) + ": " + (p.getHora()!=null?p.getHora():"") + " - " + this.getContext().getString(R.string.volumen) + ": " + p.getVolumen() + this.getContext().getString(R.string.ml)
                     + (p.getDescOtraObservacion()!=null? "\n" +p.getDescOtraObservacion():""));

                } else {
                    textView.setText(this.getContext().getString(R.string.noSeTomoMuestra)+". "+
                            (p.getDescOtraRazonNoToma()!=null?p.getDescOtraRazonNoToma():getDescripcionCatalogo(p.getRazonNoToma(),"CHF_CAT_RAZON_NO_MX")));
                }
            }
		}
		return v;
	}
}
