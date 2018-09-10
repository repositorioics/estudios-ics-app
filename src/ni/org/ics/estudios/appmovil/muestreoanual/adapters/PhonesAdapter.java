package ni.org.ics.estudios.appmovil.muestreoanual.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.domain.ContactoParticipante;
import ni.org.ics.estudios.appmovil.domain.Participante;

import java.util.List;

public class PhonesAdapter extends ArrayAdapter<ContactoParticipante> {

    protected Participante participante = null;
	public PhonesAdapter(Context context, int textViewResourceId,
                         List<ContactoParticipante> items, Participante participante) {
		super(context, textViewResourceId, items);
        this.participante = participante;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.complex_list_item, null);
		}
		ContactoParticipante tubo = getItem(position);
		if (tubo != null && tubo.getNumero1()!=null) {
			TextView textView = (TextView) v.findViewById(R.id.name_text);
            String datos = this.getContext().getString(R.string.phones) + ": " + tubo.getNumero1();
            if (tubo.getNumero2()!=null) datos += ", " + tubo.getNumero2();
            if (tubo.getNumero3()!=null) datos += ", " + tubo.getNumero3();
            if (tubo.getEsPropio().equalsIgnoreCase("0")) {
                if (tubo.getBarrio() != null) {
                    datos += "<br />" + this.getContext().getString(R.string.barrio) + ": " + tubo.getBarrio().getNombre();
                    if (tubo.getOtroBarrio()!=null) datos += "(" + tubo.getOtroBarrio() + ")";
                }
                if (tubo.getDireccion() != null) {
                    datos += "<br />" + this.getContext().getString(R.string.direccion) + ": " + tubo.getDireccion();
                }
            }
			if (textView != null) {
				textView.setText(Html.fromHtml(datos));
			}

			textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
                if (tubo.getEsPropio().equalsIgnoreCase("1")) {
                    String nameCompleto = participante.getNombre1();
                    if (participante.getNombre2() != null)
                        nameCompleto = nameCompleto + " " + participante.getNombre2();
                    nameCompleto = nameCompleto + " " + participante.getApellido1();
                    if (participante.getApellido2() != null)
                        nameCompleto = nameCompleto + " " + participante.getApellido2();
                    textView.setText(nameCompleto);
                }else
                    textView.setText(tubo.getNombre());
			}

			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
					textView.setText(tubo.getEsPropio().equalsIgnoreCase("0")?"Contacto":"Propio");
			}
		}
		return v;
	}
}
