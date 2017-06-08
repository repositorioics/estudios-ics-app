package ni.org.ics.estudios.appmovil.cohortefamilia.adapters;


import ni.org.ics.estudios.appmovil.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuCohorteFamiliaAdapter extends ArrayAdapter<String> {

	private final String[] values;
    private final int numCatalogs;
	public MenuCohorteFamiliaAdapter(Context context, int textViewResourceId,
			String[] values, int numCatalogs) {
		super(context, textViewResourceId, values);
		this.values = values;
        this.numCatalogs = numCatalogs;
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
			img=getContext().getResources().getDrawable(R.drawable.ic_menu_add);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 1: 
			img=getContext().getResources().getDrawable(R.drawable.ic_menu_search);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 2: 
			img=getContext().getResources().getDrawable(R.drawable.ic_menu_download);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 3: 
			img=getContext().getResources().getDrawable(R.drawable.ic_menu_upload);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 4:
            textView.setText(values[position] + "(" + numCatalogs + ")");
			img=getContext().getResources().getDrawable(R.drawable.ic_menu_manage);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
            if (numCatalogs < 1){
                textView.setTextColor(Color.RED);
                textView.setTypeface(null, Typeface.BOLD);
            }
			break;
		case 5: 
			img=getContext().getResources().getDrawable(R.drawable.ic_menu_chat_dashboard);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
            case 6:
                img=getContext().getResources().getDrawable(R.drawable.ic_menu_goto);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case 7:
                img=getContext().getResources().getDrawable(R.drawable.ic_menu_goto);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
		case 8:
			img=getContext().getResources().getDrawable(R.drawable.ic_menu_revert);
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
