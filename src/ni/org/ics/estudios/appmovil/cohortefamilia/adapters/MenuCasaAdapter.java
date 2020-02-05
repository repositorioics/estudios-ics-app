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
	private final int numCuartos;
	private final int numAreas;
	private final int numTelefonos;
    private final boolean existeencuestaCasa;
    private final boolean habilitarencuestaCasaSA;
    private final boolean existeencuestaCasaSA;
	public MenuCasaAdapter(Context context, int textViewResourceId,
                           String[] values, int numPart, int numCuartos, boolean existeEncuestaCasa, int numAreas, boolean habilitarencuestaCasaSA, boolean existeencuestaCasaSA, int numTelefonos) {
		super(context, textViewResourceId, values);
		this.values = values;
		this.numPart=numPart;
		this.numCuartos=numCuartos;
        this.existeencuestaCasa = existeEncuestaCasa;
        this.numAreas=numAreas;
        this.numTelefonos=numTelefonos;
        this.existeencuestaCasaSA = existeencuestaCasaSA;
        this.habilitarencuestaCasaSA = habilitarencuestaCasaSA;
	}

    @Override
    public boolean isEnabled(int position) {
        // Disable the first item of GridView
        boolean habilitado = true;
        //MA2020
        /*if (position == 5){
            habilitado = habilitarencuestaCasaSA;
        }*/
        return habilitado;
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
                if (existeencuestaCasa) {
                    textView.setTextColor(Color.BLUE);
                    textView.setText(textView.getText()+"\n"+ getContext().getResources().getString(R.string.done));
                }else{
                    textView.setText(textView.getText()+"\n"+ getContext().getResources().getString(R.string.pending));
                }
                img=getContext().getResources().getDrawable(R.drawable.ic_menu_archive);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			    break;
            case 2:
            	textView.setText(values[position] + "(" + numCuartos + ")");
                img=getContext().getResources().getDrawable(R.drawable.ic_menu_share);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                if (numCuartos < 1){
                    textView.setTextColor(Color.RED);
                    textView.setTypeface(null, Typeface.BOLD);
                }
			    break;			    
            case 3:
            	textView.setText(values[position] + "(" + numAreas + ")");
                img=getContext().getResources().getDrawable(R.drawable.ic_menu_selectall_holo_light);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                if (numAreas < 1){
                    textView.setTextColor(Color.RED);
                    textView.setTypeface(null, Typeface.BOLD);
                }
                break;
            case 4:
            	textView.setText(values[position] + "(" + numTelefonos + ")");
                img=getContext().getResources().getDrawable(R.drawable.ic_menu_call);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                if (numTelefonos < 1){
                    textView.setTextColor(Color.RED);
                    textView.setTypeface(null, Typeface.BOLD);
                }
                break;
            //MA2020
            /*case 5:
                if (!habilitarencuestaCasaSA) {
                    textView.setTextColor(Color.GRAY);
                    textView.setText(textView.getText()+"\n"+ getContext().getResources().getString(R.string.notavailable));
                }else{
                    if (existeencuestaCasaSA) {
                        textView.setTextColor(Color.BLUE);
                        textView.setText(textView.getText()+"\n"+ getContext().getResources().getString(R.string.done));
                    }else{
                        textView.setText(textView.getText()+"\n"+ getContext().getResources().getString(R.string.pending));
                    }
                }
                img=getContext().getResources().getDrawable(R.drawable.ic_menu_paste_holo_light);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;*/
            case 5:
            	textView.setText(values[position]);
                img=getContext().getResources().getDrawable(R.drawable.ic_menu_chat_dashboard);
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
