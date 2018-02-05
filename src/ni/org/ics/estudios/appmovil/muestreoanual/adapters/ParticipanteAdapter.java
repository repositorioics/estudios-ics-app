package ni.org.ics.estudios.appmovil.muestreoanual.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;

import java.text.SimpleDateFormat;
import java.util.List;

public class ParticipanteAdapter extends ArrayAdapter<Participante> {
	
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");


	public ParticipanteAdapter(Context context, int textViewResourceId,
			List<Participante> items) {
		super(context, textViewResourceId, items);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.complex_list_item, null);
		}
		Participante parti = getItem(position);
        ParticipanteProcesos procesos = parti.getProcesos();
		if (procesos != null) {
			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.code) + " " + procesos.getCodigo());
				if(procesos.getCasaCHF()!=null){
					textView.setText(textView.getText().toString()+ "-CHF:" + procesos.getCasaCHF());
				}
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			String nameCompleto = parti.getNombre1();
			if (parti.getNombre2()!=null) nameCompleto = nameCompleto + " "+  parti.getNombre2();
			nameCompleto = nameCompleto +" "+ parti.getApellido1();
			if (parti.getApellido2()!=null) nameCompleto = nameCompleto + " "+  parti.getApellido2();
			if (textView != null) {
				textView.setText(nameCompleto);
			}

			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
				textView.setText(mDateFormat.format(parti.getFechaNac()));
			}
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				if (parti.getSexo().equals("M")) {
					imageView.setImageResource(R.drawable.male);
				} else if (parti.getSexo().equals("F")) {
					imageView.setImageResource(R.drawable.female);
				}
			}
			String labelHeader="";
			
			labelHeader = labelHeader + "Estudio: " + procesos.getEstudio()+"<br />";
			labelHeader = labelHeader + "Barrio: " + parti.getCasa().getBarrio().getNombre()+"<br />";
			labelHeader = labelHeader + "Dirección: " + parti.getCasa().getDireccion()+"<br />";
			labelHeader = labelHeader + "Manzana: " + parti.getCasa().getManzana()+"<br />";
			//labelHeader = labelHeader + "Personas en la casa: " + parti.getCuantasPers()+"<br />";
			labelHeader = labelHeader + "------------<br />";
			if (procesos.getEstPart().equals(0)){
				labelHeader = labelHeader + "<br /><font color='red'>Participante retirado</font>";
			}
			else{
				//if (participante.getPosZika().matches("Si")) labelHeader = labelHeader + "<font color='red'><b>Participante positivo a ZIKA</b></font><br />";
				if (procesos.getMi().matches("Si")) labelHeader = labelHeader + "<font color='red'><b>Participante en monitoreo intensivo CHF</b></font><br />";
				//if (participante.getCand().matches("Si")) labelHeader = labelHeader + "<font color='red'><b>Participante candidato a CHF</b></font><br />";
				if (procesos.getConsFlu().matches("Si")|| procesos.getPesoTalla().matches("Si")
						|| procesos.getEnCasa().matches("Si")||procesos.getEncPart().matches("Si")
						|| procesos.getEncLacMat().matches("Si")||procesos.getInfoVacuna().matches("Si")
						|| procesos.getConsDeng().matches("Si") || procesos.getObsequio().matches("Si")
						|| procesos.getConmx().matches("No") || procesos.getConmxbhc().matches("No")|| procesos.getZika().matches("Si")
						|| procesos.getAdn().matches("Si")|| procesos.getDatosParto().matches("Si")|| procesos.getDatosVisita().matches("Si")){
					labelHeader = labelHeader + "<font color='red'>Pendiente: <br /></font>";
					
					//Primero muestras
					//'#B941E0'purple
					//'#11BDF7' blue
					//'#32B507' green
					if (procesos.getConvalesciente().matches("Na")){
						labelHeader = labelHeader + "<font color='red'>" + this.getContext().getString(R.string.convless14) + "</font><br />";
					}
					else{
						if(procesos.getEstudio().equals("Cohorte BB")){
							if (parti.getEdadMeses()>=6){
								if (procesos.getConmx().matches("No")){
									if (procesos.getPbmc().matches("Si")){
										labelHeader = labelHeader + "<font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font>";
									}
									else{
										labelHeader = labelHeader + "<font color='red'>Tomar 2cc en tubo Rojo<br /></font>";
									}
								}
								if (procesos.getConmxbhc().matches("No")){
									if (procesos.getPaxgene().matches("Si")){
										labelHeader = labelHeader + "<font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font>";
									}
									else{
										labelHeader = labelHeader + "<font color='#B941E0'>Tomar 1cc para BHC<br /></font>";
									}
								}
							}
						}
						else if(procesos.getEstudio().equals("Influenza")){
							if(parti.getEdadMeses()>=6 && parti.getEdadMeses()<24){
								if (procesos.getConmx().matches("No")){
									if (procesos.getPbmc().matches("Si")){
										labelHeader = labelHeader + "<font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font>";
										labelHeader = labelHeader + "<font color='red'>Tomar 1cc en tubo Rojo<br /></font>";
										labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
									}
									else{
										labelHeader = labelHeader + "<font color='red'>Tomar 2cc en tubo Rojo<br /></font>";
									}
								}
								if (procesos.getConmxbhc().matches("No") && procesos.getPbmc().matches("No")){
									if (procesos.getPaxgene().matches("Si")){
										labelHeader = labelHeader + "<font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font>";
									}
									else{
										labelHeader = labelHeader + "<font color='#B941E0'>Tomar 1cc para BHC<br /></font>";
									}
								}
							}
							else {
								if (procesos.getConmx().matches("No")){
									if (procesos.getPbmc().matches("Si")){
										labelHeader = labelHeader + "<font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font>";
										labelHeader = labelHeader + "<font color='red'>Tomar 1cc en tubo Rojo<br /></font>";
									}
									else{
										labelHeader = labelHeader + "<font color='red'>Tomar 6cc en tubo Rojo<br /></font>";
									}
								}
								if (procesos.getConmxbhc().matches("No")){
									if (procesos.getPaxgene().matches("Si")){
										labelHeader = labelHeader + "<font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font>";
									}
									else{
										labelHeader = labelHeader + "<font color='#B941E0'>Tomar 1cc para BHC<br /></font>";
									}
								}
							}
						}
						else if(procesos.getEstudio().equals("Influenza  Cohorte BB")){
							if(parti.getEdadMeses()>=6 && parti.getEdadMeses()<24){
								if (procesos.getConmx().matches("No")){
									if (procesos.getPbmc().matches("Si")){
										labelHeader = labelHeader + "<font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font>";
										labelHeader = labelHeader + "<font color='red'>Tomar 1cc en tubo Rojo<br /></font>";
										labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
									}
									else{
										labelHeader = labelHeader + "<font color='red'>Tomar 2cc en tubo Rojo<br /></font>";
									}
								}
								if (procesos.getConmxbhc().matches("No") && procesos.getPbmc().matches("No")){
									if (procesos.getPaxgene().matches("Si")){
										labelHeader = labelHeader + "<font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font>";
									}
									else{
										labelHeader = labelHeader + "<font color='#B941E0'>Tomar 1cc para BHC<br /></font>";
									}
								}
							}
							else {
								if (procesos.getConmx().matches("No")){
									if (procesos.getPbmc().matches("Si")){
										labelHeader = labelHeader + "<font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font>";
										labelHeader = labelHeader + "<font color='red'>Tomar 1cc en tubo Rojo<br /></font>";
									}
									else{
										labelHeader = labelHeader + "<font color='red'>Tomar 6cc en tubo Rojo<br /></font>";
									}
								}
								if (procesos.getConmxbhc().matches("No")){
									if (procesos.getPaxgene().matches("Si")){
										labelHeader = labelHeader + "<font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font>";
									}
									else{
										labelHeader = labelHeader + "<font color='#B941E0'>Tomar 1cc para BHC<br /></font>";
									}
								}
							}
						}
						else if(procesos.getEstudio().equals("Dengue")){
							if (procesos.getConmx().matches("No")){
								if (procesos.getPbmc().matches("Si")){
									labelHeader = labelHeader + "<font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font>";
								}
								else{
									labelHeader = labelHeader + "<font color='red'>Tomar 6cc en tubo Rojo<br /></font>";
								}
							}
							if (procesos.getConmxbhc().matches("No")){
								if (procesos.getPaxgene().matches("Si")){
									labelHeader = labelHeader + "<font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font>";
								}
								else{
									labelHeader = labelHeader + "<font color='#B941E0'>Tomar 1cc para BHC<br /></font>";
								}
							}
						}
						else if(procesos.getEstudio().equals("Dengue  Influenza")){
							if (procesos.getConmx().matches("No")){
								if (procesos.getPbmc().matches("Si")){
									labelHeader = labelHeader + "<font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font>";
									labelHeader = labelHeader + "<font color='red'>Tomar 1cc en tubo Rojo<br /></font>";
								}
								else{
									labelHeader = labelHeader + "<font color='red'>Tomar 6cc en tubo Rojo<br /></font>";
								}
							}
							if (procesos.getConmxbhc().matches("No")){
								if (procesos.getPaxgene().matches("Si")){
									labelHeader = labelHeader + "<font color='#32B507'>Tomar 1cc para BHC (Paxgene)<br /></font>";
								}
								else{
									labelHeader = labelHeader + "<font color='#B941E0'>Tomar 1cc para BHC<br /></font>";
								}
							}
						}
						if (procesos.getConvalesciente().matches("Si")&& parti.getEdadMeses()>=24){
							if (procesos.getConmx().matches("No")){
								if (procesos.getPbmc().matches("Si")){
									labelHeader = labelHeader + "<font color='#de3163'>Tomar 5cc de convaleciente<br /></font>";
								}
								else{
									labelHeader = labelHeader + "<font color='#de3163'>Tomar 5cc de convaleciente<br /></font>";
								}
							}
						}
					}
					
					//Nuevo orden
					
					if (procesos.getEnCasa().matches("Si")) labelHeader = labelHeader + "Encuesta de Casa<br />";
					if (procesos.getEncPart().matches("Si")) labelHeader = labelHeader + "Encuesta de Participante<br />";
					if (procesos.getConsFlu().matches("Si")) labelHeader = labelHeader + "Consentimiento Influenza <br />";
					if (procesos.getConsDeng().matches("Si")) labelHeader = labelHeader + "Consentimiento Dengue<br />";
					if (procesos.getZika().matches("Si")) labelHeader = labelHeader + "Consentimiento Zika<br />";
					if (procesos.getEncLacMat().matches("Si")) labelHeader = labelHeader + "Encuesta de Lactancia Materna<br />";
					if (procesos.getPesoTalla().matches("Si")) labelHeader = labelHeader + "Peso y Talla <br />";
					if (procesos.getDatosParto().matches("Si")) labelHeader = labelHeader + "Datos Parto BB<br />";
					if (procesos.getInfoVacuna().matches("Si")) labelHeader = labelHeader + "Vacunas<br />";
					if (procesos.getDatosVisita().matches("Si")) labelHeader = labelHeader + "Pendiente datos de casa<br />";
					if ((procesos.getObsequio().matches("Si"))){
						labelHeader = labelHeader + "<font color='blue'>" + this.getContext().getString(R.string.gift_missing) + "</font><br />";
					}
					if (procesos.getAdn().matches("Si")) labelHeader = labelHeader + "<font color='red'><b>Participante pendiente de ADN, Informar a LAB para toma.</b></font><br />";
					if ((procesos.getRetoma()!=null && procesos.getVolRetoma()!=null)){
						if ((procesos.getRetoma().matches("Si"))){
							labelHeader = labelHeader + "<font color='red'>" + this.getContext().getString(R.string.retoma) +": " + procesos.getVolRetoma() + "cc </font><br/>";
						}
					}
				}
				else{
					labelHeader = labelHeader + "<font color='blue'>No tiene procedimientos pendientes<br /></font>";
				}
			}
			textView = (TextView) v.findViewById(R.id.infoc_text);
			if (textView != null) {
				textView.setText(Html.fromHtml(labelHeader));
			}
		}
		return v;
	}
}
