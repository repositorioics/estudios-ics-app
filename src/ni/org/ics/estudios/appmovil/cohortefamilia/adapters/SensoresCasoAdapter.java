package ni.org.ics.estudios.appmovil.cohortefamilia.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.TelefonoContacto;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.SensorCaso;

import java.text.SimpleDateFormat;
import java.util.List;

public class SensoresCasoAdapter extends ArrayAdapter<SensorCaso> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");

	public SensoresCasoAdapter(Context context, int textViewResourceId,
                               List<SensorCaso> items) {
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
        SensorCaso p = getItem(position);
		if (p != null) {
            if (p.getSensorSN().equalsIgnoreCase("1")) {

                TextView textView = (TextView) v.findViewById(R.id.identifier_text);
                textView.setTextColor(Color.BLACK);
                if (textView != null) {
                    if (p.getArea() != null) textView.setText(p.getArea().toString());
                    if (p.getCuarto() != null) textView.setText(p.getCuarto().toString());
                }

                textView = (TextView) v.findViewById(R.id.der_text);
                textView.setTextColor(Color.BLACK);
                if (textView != null) {
                    if (p.getFechaColocacion() != null)
                        textView.setText(mDateFormat.format(p.getFechaColocacion()));
                }

                textView = (TextView) v.findViewById(R.id.name_text);
                textView.setTextColor(Color.BLACK);
                if (textView != null) {
                    if (p.getFechaRetiro() != null) {
                        textView.setText(p.getNumeroSensor() + " (Retirado - " + mDateFormat.format(p.getFechaRetiro()) + ")");
                    } else {
                        textView.setText(p.getNumeroSensor() + " (Pendiente retiro)");
                    }
                }
            }else{
                TextView textView = (TextView) v.findViewById(R.id.name_text);
                textView.setTextColor(Color.BLACK);
                textView.setText("Sensor no colocado");
            }
		
		}
		return v;
	}
}
