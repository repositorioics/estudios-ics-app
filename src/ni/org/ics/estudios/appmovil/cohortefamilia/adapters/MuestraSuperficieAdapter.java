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
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.MuestraSuperficie;

import java.text.SimpleDateFormat;
import java.util.List;

public class MuestraSuperficieAdapter extends ArrayAdapter<MuestraSuperficie> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
    private List<MessageResource> tiposMuestra;
	public MuestraSuperficieAdapter(Context context, int textViewResourceId,
                                    List<MuestraSuperficie> items, List<MessageResource> tiposMuestra) {
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
		MuestraSuperficie p = getItem(position);
		if (p != null) {

            TextView textView = (TextView) v.findViewById(R.id.identifier_text);
            textView.setTextSize(18);
            textView.setTextColor(Color.BLUE);
            if (textView != null) {
                textView.setText(getDescripcionCatalogo(p.getTipoMuestra(), "CHF_CAT_TIPO_MX_SUP"));
            }
            textView = (TextView) v.findViewById(R.id.der_text);
            if (textView != null) {
                textView.setTextColor(Color.BLACK);
                textView.setText(mDateFormat.format(p.getRecordDate()));
            }
            textView = (TextView) v.findViewById(R.id.name_text);
            if (textView != null) {
                textView.setTextColor(Color.BLACK);
                if (p.getOtraSuperficie() != null && !p.getOtraSuperficie().isEmpty()) {
                    textView.setText(this.getContext().getString(R.string.otraSuperficie) + ": " + p.getOtraSuperficie());

                }else{
                    textView.setText("");
                }
                if (p.getParticipanteChf() != null) {
                    textView.setText(this.getContext().getString(R.string.participant) + ": " + p.getParticipanteChf().getParticipante().getCodigo());

                }else{
                    if (p.getOtraSuperficie() == null || p.getOtraSuperficie().isEmpty()) textView.setText("");
                }
            }

        }
		return v;
	}
}
