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
    private boolean existeencuestaParticip = false;
    private boolean existeencuestaParticipSA = false;
    private boolean existeencuestaParto = false;
    private boolean existeencuestaPeso = false;
    private boolean existeencuestaLact = false;
    private boolean habilitarEncuestaParticipSA = false;
    private final int OPCION_ENCUESTA_PARTICIPANTE = 0;
    private final int OPCION_ENCUESTA_DATOSPARTO = 1;
    private final int OPCION_ENCUESTA_PESOTALLA = 2;
    private final int OPCION_ENCUESTA_LACTANCIA = 3;
    private final int OPCION_ENCUESTA_MUESTRAS = 4;
    private final int OPCION_ENCUESTA_PARTICIPANTESA = 5;

    private final Context context;
    private final ParticipanteCohorteFamilia participanteCHF;
	public MenuParticipanteAdapter(Context context, int textViewResourceId,
                                   String[] values, ParticipanteCohorteFamilia participanteCHF, boolean existeencuestaParticip, boolean existeencuestaParto, boolean existeencuestaPeso, boolean existeencuestaLact, boolean existeencuestaParticipSA, boolean habilitarEncuestaParticipSA) {
		super(context, textViewResourceId, values);
        this.context = context;
		this.values = values;
        this.participanteCHF = participanteCHF;
        this.existeencuestaParticip = existeencuestaParticip;
        this.existeencuestaParto = existeencuestaParto;
        this.existeencuestaPeso = existeencuestaPeso;
        this.existeencuestaLact = existeencuestaLact;
        this.existeencuestaParticipSA = existeencuestaParticipSA;
        this.habilitarEncuestaParticipSA = habilitarEncuestaParticipSA;
        validarOpcionesMenu();
	}


    private void validarOpcionesMenu() {
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
                if (anios < 3) habilitarLactancia = true;
                else if (anios == 3) {
                    if (meses > 0)
                        habilitarLactancia = false;
                    else {
                        habilitarLactancia = (dias <= 0);
                    }
                } else habilitarLactancia = false;

                //BHC y Paxgene (2 años a 13 años y 14 años y mas)
                habilitarBhc = anios >= 2;
                //Rojo (6 meses y menos de 2 años y 2 años a 13 años Y 14 años y mas)
                if (anios == 0) habilitarRojo = meses >= 6;
                else habilitarRojo = anios >= 1;
            }
        }

    }

    @Override
    public boolean isEnabled(int position) {
        // Disable the first item of GridView
        boolean habilitado = true;
        switch (position){
            case OPCION_ENCUESTA_PARTICIPANTE:
                habilitado = !existeencuestaParticip;
                break;
            case OPCION_ENCUESTA_DATOSPARTO:
                habilitado = !existeencuestaParto;
                break;
            case OPCION_ENCUESTA_PESOTALLA:
                habilitado = !existeencuestaPeso;
                break;
            case OPCION_ENCUESTA_LACTANCIA :
                habilitado = habilitarLactancia && !existeencuestaLact;
                break;
            case OPCION_ENCUESTA_MUESTRAS : habilitado = true;
                break;
            case OPCION_ENCUESTA_PARTICIPANTESA :
                habilitado = habilitarEncuestaParticipSA && !existeencuestaParticipSA;
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
            case OPCION_ENCUESTA_PARTICIPANTE:
                if (existeencuestaParticip) {
                    textView.setTextColor(Color.BLUE);
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.done));
                }else{
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.pending));
                }
                img=getContext().getResources().getDrawable(R.drawable.ic_menu_myplaces);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case OPCION_ENCUESTA_DATOSPARTO:
                if (existeencuestaParto) {
                    textView.setTextColor(Color.BLUE);
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.done));
                }else{
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.pending));
                }
                img=getContext().getResources().getDrawable(R.drawable.ic_baby);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case OPCION_ENCUESTA_PESOTALLA:
                if (existeencuestaPeso) {
                    textView.setTextColor(Color.BLUE);
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.done));
                }else{
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.pending));
                }
                img=getContext().getResources().getDrawable(R.drawable.ic_weight);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case OPCION_ENCUESTA_LACTANCIA:
                if (!habilitarLactancia) {
                    textView.setTextColor(Color.GRAY);
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.notavailable));
                }else{
                    if (existeencuestaLact) {
                        textView.setTextColor(Color.BLUE);
                        textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.done));
                    }else{
                        textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.pending));
                    }
                }
                img = getContext().getResources().getDrawable(R.drawable.ic_breastfeeding);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
            case OPCION_ENCUESTA_MUESTRAS:
                img=getContext().getResources().getDrawable(R.drawable.ic_samples);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            /*
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
            */
            case OPCION_ENCUESTA_PARTICIPANTESA:
                if (!habilitarEncuestaParticipSA) {
                    textView.setTextColor(Color.GRAY);
                    textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.notavailable));
                }else{
                    if (existeencuestaParticipSA) {
                        textView.setTextColor(Color.BLUE);
                        textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.done));
                    }else{
                        textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.pending));
                    }
                }
                img=getContext().getResources().getDrawable(R.drawable.ic_menu_friendslist);
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
