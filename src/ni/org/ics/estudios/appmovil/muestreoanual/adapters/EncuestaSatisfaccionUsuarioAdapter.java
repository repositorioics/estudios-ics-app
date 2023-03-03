package ni.org.ics.estudios.appmovil.muestreoanual.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaSatisfaccionUsuario;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.PerimetroAbdominal;

import java.text.DateFormat;
import java.util.List;

import static ni.org.ics.estudios.appmovil.MyIcsApplication.getContext;

public class EncuestaSatisfaccionUsuarioAdapter extends ArrayAdapter<EncuestaSatisfaccionUsuario> {
    DateFormat mediumDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);

    public EncuestaSatisfaccionUsuarioAdapter(Context context, int textViewResourceId,
                                     List<EncuestaSatisfaccionUsuario> items) {
        super(context, textViewResourceId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.complex_list_item , null);
        }
        EncuestaSatisfaccionUsuario encuestaSatisfaccionUsuario = getItem(position);
        if (encuestaSatisfaccionUsuario != null) {

            TextView textView = (TextView) v.findViewById(R.id.identifier_text);
            if (textView != null) {
                //textView.setText(encuestaSatisfaccionUsuario.getEsId().getCodigo().toString());
            }

            textView = (TextView) v.findViewById(R.id.name_text);
            if (textView != null) {
                //textView.setText(mediumDf.format(encuestaSatisfaccionUsuario.getEsId().getFecha()));
            }

            textView = (TextView) v.findViewById(R.id.der_text);
            if (textView != null) {
                textView.setText(encuestaSatisfaccionUsuario.getUsuarioRegistro());
            }
        }
        return v;
    }
}
