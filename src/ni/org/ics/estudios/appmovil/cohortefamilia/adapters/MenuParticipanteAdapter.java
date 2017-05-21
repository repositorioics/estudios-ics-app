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
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;

public class MenuParticipanteAdapter extends ArrayAdapter<String> {

	private final String[] values;
    private boolean habilitarLactancia = false;
    private boolean habilitarBhc = false;
    private boolean habilitarRojo = false;
    private boolean habilitarPax = false;

    private final Context context;
    private final ParticipanteCohorteFamilia participanteCHF;
	public MenuParticipanteAdapter(Context context, int textViewResourceId,
                                   String[] values, ParticipanteCohorteFamilia participanteCHF) {
		super(context, textViewResourceId, values);
        this.context = context;
		this.values = values;
        this.participanteCHF = participanteCHF;
        validarOpcionesMenu();
	}


    private void validarOpcionesMenu(){
        int anios = 0;
        int meses = 0;
        int dias = 0;
        if (!participanteCHF.getParticipante().getEdad().equalsIgnoreCase("ND")) {
            String edad[] = participanteCHF.getParticipante().getEdad().split("/");
            if (edad.length > 0) {
                anios = Integer.valueOf(edad[0]);
                meses = Integer.valueOf(edad[1]);
                dias = Integer.valueOf(edad[2]);
                //lactancia materna
                if (anios<3) habilitarLactancia = true;
                else if (anios == 3){
                    if (meses > 0)
                        habilitarLactancia = false;
                    else{
                        habilitarLactancia = (dias <= 0);
                    }
                }else habilitarLactancia = false;

                //BHC y Paxgene (2 años a 13 años)
                if (anios>=2 && anios<13) {
                    habilitarBhc = true;
                    habilitarPax = true;
                }else if (anios == 13){
                    if (meses > 0) {
                        habilitarBhc = false;
                        habilitarPax = false;
                    }else{
                        habilitarBhc = (dias <= 0);
                        habilitarPax = (dias <= 0);
                    }
                }else {
                    habilitarBhc = false;
                    habilitarPax = false;
                }
                //Rojo (6 meses y menos de 2 años y 2 años a 13 años)
                if (anios>=1 && anios<13) habilitarRojo = true;
                else if (anios == 13){
                    if (meses > 0)
                        habilitarRojo = false;
                    else{
                        habilitarRojo = (dias <= 0);
                    }
                }else if (anios == 0){
                    habilitarRojo = meses >= 6;
                }
                else habilitarRojo = false;
            }
        }
    }

    @Override
    public boolean isEnabled(int position) {
        // Disable the first item of GridView
        boolean habilitado = true;
        switch (position){
            case 0:break;
            case 1:break;
            case 2:break;
            case 3 : habilitado = habilitarLactancia;
                break;
            case 4 : habilitado = habilitarBhc;
                break;
            case 5 : habilitado = habilitarRojo;
                break;
            case 6 : habilitado = habilitarPax;
                break;
            default: break;
        }
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
		textView.setTypeface(null, Typeface.BOLD);
		textView.setText(values[position]);
		textView.setTextColor(Color.BLACK);

		// Change icon based on position
		Drawable img = null;
		switch (position){
            case 0:
                img=getContext().getResources().getDrawable(R.drawable.male);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case 1:
                img=getContext().getResources().getDrawable(R.drawable.ic_baby);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case 2:
                img=getContext().getResources().getDrawable(R.drawable.ic_weight);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case 3:
                if (!habilitarLactancia) {
                    textView.setTextColor(Color.GRAY);
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.notavailable));
                }
                img = getContext().getResources().getDrawable(R.drawable.ic_breastfeeding);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
            case 4:
                if (!habilitarBhc) {
                    textView.setTextColor(Color.GRAY);
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.notavailable));
                }
                img=getContext().getResources().getDrawable(R.drawable.bhctubes);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case 5:
                if (!habilitarRojo) {
                    textView.setTextColor(Color.GRAY);
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.notavailable));
                }
                img=getContext().getResources().getDrawable(R.drawable.redtubes);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case 6:
                if (!habilitarPax) {
                    textView.setTextColor(Color.GRAY);
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.notavailable));
                }
                img=getContext().getResources().getDrawable(R.drawable.ic_greentube);
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
