package ni.org.ics.estudios.appmovil.covid19.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;

public class MenuVisitaFinalCasoCovid19Adapter extends ArrayAdapter<String> {

	private final String[] values;
	public MenuVisitaFinalCasoCovid19Adapter(Context context, int textViewResourceId,
                                             String[] values) {
		super(context, textViewResourceId, values);
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.menu_item_2, null);
		}
		TextView textView = (TextView) v.findViewById(R.id.label);
		textView.setTypeface(null, Typeface.BOLD);
		textView.setText(values[position]);
		textView.setTextColor(Color.BLACK);

		// Change icon based on position
		Drawable img = null;
		switch (position){
            case 0:
                img=getContext().getResources().getDrawable(R.drawable.ic_samples_seg);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case 1:
                img=getContext().getResources().getDrawable(R.drawable.ic_sintomas);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
			default:
				img=getContext().getResources().getDrawable(R.drawable.ic_menu_help);
				textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
				break;
		}
		return v;
	}
}
