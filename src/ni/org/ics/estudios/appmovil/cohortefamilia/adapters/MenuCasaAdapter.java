package ni.org.ics.estudios.appmovil.cohortefamilia.adapters;


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

public class MenuCasaAdapter extends ArrayAdapter<String> {

	private final String[] values;
	private final int numPart;
	public MenuCasaAdapter(Context context, int textViewResourceId,
                           String[] values, int numPart) {
		super(context, textViewResourceId, values);
		this.values = values;
		this.numPart=numPart;
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
		textView.setText(values[position]);
		textView.setTextColor(Color.BLACK);

		// Change icon based on position
		Drawable img = null;
		switch (position){
		case 0: 
			textView.setText(values[position] + "(" + numPart + ")");
			img=getContext().getResources().getDrawable(R.drawable.ic_menu_allfriends);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			if (numPart < 1){
				textView.setTextColor(Color.RED);
				textView.setTypeface(null, Typeface.BOLD);
			}
			break;
		case 1: 
			img=getContext().getResources().getDrawable(R.drawable.ic_menu_search);
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
