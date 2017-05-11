package ni.org.ics.estudios.appmovil.cohortefamilia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.Casa;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;

import java.util.List;

public class CasaCHFAdapter extends ArrayAdapter<CasaCohorteFamilia> {


	public CasaCHFAdapter(Context context, int textViewResourceId,
                          List<CasaCohorteFamilia> items) {
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
		CasaCohorteFamilia p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.code) + ": " + p.getCodigoCHF());
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.barrio) + ": " + p.getCasa().getCodigo() + " , " + this.getContext().getString(R.string.manzana) + ": " + p.getCasa().getManzana());
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			if (textView != null) {
				textView.setText(p.getNombre1JefeFamilia() + " " +p.getApellido1JefeFamilia());
			}
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				imageView.setImageResource(R.drawable.proposed_house);
			}
		}
		return v;
	}
}
