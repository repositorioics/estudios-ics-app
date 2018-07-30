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
		Participante participante = getItem(position);
        ParticipanteProcesos procesos = participante.getProcesos();
		if (procesos != null) {
			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.code) + " " + procesos.getCodigo());
				if(procesos.getCasaCHF()!=null){
					textView.setText(textView.getText().toString()+ "-CHF:" + procesos.getCasaCHF());
				}
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			String nameCompleto = participante.getNombre1();
			if (participante.getNombre2()!=null) nameCompleto = nameCompleto + " "+  participante.getNombre2();
			nameCompleto = nameCompleto +" "+ participante.getApellido1();
			if (participante.getApellido2()!=null) nameCompleto = nameCompleto + " "+  participante.getApellido2();
			if (textView != null) {
				textView.setText(nameCompleto);
			}

			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
				textView.setText(mDateFormat.format(participante.getFechaNac()));
			}
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				if (participante.getSexo().equals("M")) {
					imageView.setImageResource(R.drawable.male);
				} else if (participante.getSexo().equals("F")) {
					imageView.setImageResource(R.drawable.female);
				}
			}
			String labelHeader="";
			
			labelHeader = labelHeader + "Estudio: " + procesos.getEstudio()+"<br />";
			labelHeader = labelHeader + "Barrio: " + participante.getCasa().getBarrio().getNombre()+"<br />";
			labelHeader = labelHeader + "Dirección: " + participante.getCasa().getDireccion()+"<br />";
			labelHeader = labelHeader + "Manzana: " + participante.getCasa().getManzana()+"<br />";
			labelHeader = labelHeader + "Personas en la casa: " + procesos.getCuantasPers()+"<br />";
			labelHeader = labelHeader + "------------<br />";
			if (procesos.getEstPart().equals(0)){
				labelHeader = labelHeader + "<br /><font color='red'>Participante retirado</font>";
			}
			else{
				if (procesos.getPosZika().matches("Si")) labelHeader = labelHeader + "<font color='red'><b>Participante positivo a ZIKA</b></font><br />";
				if (procesos.getMi().matches("Si")) labelHeader = labelHeader + "<font color='red'><b>Participante en monitoreo intensivo CHF</b></font><br />";
				//if (participante.getCand().matches("Si")) labelHeader = labelHeader + "<font color='red'><b>Participante candidato a CHF</b></font><br />";
				if (procesos.getConsFlu().matches("Si")|| procesos.getPesoTalla().matches("Si")
						|| procesos.getEnCasa().matches("Si")||procesos.getEncPart().matches("Si")
                        || procesos.getEnCasaChf().matches("Si") || procesos.getEnCasaSa().matches("Si") || procesos.getEncPartSa().matches("Si")
						|| procesos.getEncLacMat().matches("Si")||procesos.getInfoVacuna().matches("Si")
						|| procesos.getConsDeng().matches("Si") || procesos.getObsequio().matches("Si")
						|| procesos.getConmx().matches("No") || procesos.getConmxbhc().matches("No")|| procesos.getZika().matches("Si")
						|| procesos.getAdn().matches("Si")|| procesos.getDatosParto().matches("Si")|| procesos.getDatosVisita().matches("Si")
                        || !procesos.getConvalesciente().matches("No")){
					labelHeader = labelHeader + "<font color='red'>Pendiente: <br /></font>";
					
					//Primero muestras
					//'#B941E0'purple
					//'#11BDF7' blue
					//'#32B507' green
					if (procesos.getConvalesciente().matches("Na")){
						labelHeader = labelHeader + "<font color='red'>" + this.getContext().getString(R.string.convless14) + "</font><br />";
					}
					else{
                        if(participante.getEdadMeses()<6){
                            labelHeader = labelHeader + "<font color='red'>No se toman muestras<br /></font>";
                        }else
						if(procesos.getEstudio().equals("Cohorte BB")){
							if (participante.getEdadMeses()>=6){
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
							if(participante.getEdadMeses()>=6 && participante.getEdadMeses()<24){
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
							if(participante.getEdadMeses()>=6 && participante.getEdadMeses()<24){
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
						}//MA 2018
                        else if(procesos.getEstudio().equals("CH Familia")){
                            labelHeader += getVolumenCHF(participante);

                        } else if(procesos.getEstudio().equals("Influenza  CH Familia")){
                            labelHeader += getVolumenInfluenzaCHF(participante);

                        } else if(procesos.getEstudio().equals("Dengue    CH Familia")){
                            labelHeader += getVolumenDengueCHF(participante);

                        } else if(procesos.getEstudio().equals("Dengue  Influenza  CH Familia")){
                            labelHeader += getVolumenDengueInfluenzaCHF(participante);

                        }
                        if (procesos.getConvalesciente().matches("Si")&& participante.getEdadMeses()>=24){
                            if(!procesos.getEstudio().equals("Influenza  CH Familia")){
                                //AL finalizar MA 2018, se solicita siempre muestre mensaje de muestra convasleciente.  28062018
                                //if (procesos.getConmx().matches("No")) {
                                //if (procesos.getPbmc().matches("Si")) {
                                labelHeader = labelHeader + "<font color='#de3163'>Tomar 5cc de convaleciente<br /></font>";
                                    /*} else {
                                        labelHeader = labelHeader + "<font color='#de3163'>Tomar 5cc de convaleciente<br /></font>";
                                    }*/
                                //}
                            }
                        }
					}
					
					//Nuevo orden
					
					if (procesos.getEnCasa().matches("Si")) labelHeader = labelHeader + "Encuesta de Casa<br />";
                    if (procesos.getEnCasaChf().matches("Si")) labelHeader = labelHeader + this.getContext().getString(R.string.housechf_survey_missing) + "<br />";
                    if (procesos.getEnCasaSa().matches("Si")) labelHeader = labelHeader +  this.getContext().getString(R.string.housesa_survey_missing) + "<br />";
                    if (procesos.getEncPartSa().matches("Si")) labelHeader = labelHeader + this.getContext().getString(R.string.partsa_survey_missing) + "<br />";
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

    private String getVolumenCHF(Participante mParticipante){
        String labelHeader = "";
        //De 6 meses a <2 años
        if(mParticipante.getEdadMeses()>=6 && mParticipante.getEdadMeses()<24){
            if (mParticipante.getProcesos().getConmx().matches("No")){
                labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No")){
                labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
            }
        }else //De 2 años - < 14 Años
            if(mParticipante.getEdadMeses()>=24 && mParticipante.getEdadMeses()<168){
                if (mParticipante.getProcesos().getConmx().matches("No")){
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No")){
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
            }else //De 14 años y más
            {
                if (mParticipante.getProcesos().getConmx().matches("No")){
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 12cc en tubo Rojo<br /></font></small>";
                }
                if (mParticipante.getProcesos().getConmxbhc().matches("No")){
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
            }
        return labelHeader;
    }

    private String getVolumenInfluenzaCHF(Participante mParticipante){
        String labelHeader = "";
        //De 6 meses a <2 años
        if(mParticipante.getEdadMeses()>=6 && mParticipante.getEdadMeses()<24){
            if (mParticipante.getProcesos().getConmx().matches("No")){
                if (mParticipante.getProcesos().getPbmc().matches("Si")){
                    labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 2cc en tubo PBMC<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                    labelHeader = labelHeader + "<font color='#B941E0'>No tomar BHC<br /></font>";
                }else{
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 2cc en tubo Rojo<br /></font></small>";
                }
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No") && mParticipante.getProcesos().getPbmc().matches("No")){
                if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                }else{
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
            } //De 2 años y < 14 Años
        }else if(mParticipante.getEdadMeses()>=24 && mParticipante.getEdadMeses()<168) {
            if (mParticipante.getProcesos().getConmx().matches("No")) {
                if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                }
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
            }
        }else //De 14 años y más
        {
            if (mParticipante.getProcesos().getConmx().matches("No")) {
                if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 12cc en tubo Rojo<br /></font></small>";
                }
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
            }
        }
        return labelHeader;
    }

    private String getVolumenDengueCHF(Participante mParticipante){
        //De 2 años  y < 14 a años
        String labelHeader = "";
        if(mParticipante.getEdadMeses()>=24 && mParticipante.getEdadMeses()<168) {
            if (mParticipante.getProcesos().getConmx().matches("No")) {
                if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                }
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
            }
        }else //De 14 años y más
        {
            if (mParticipante.getProcesos().getConmx().matches("No")) {
                if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 12cc en tubo Rojo<br /></font></small>";
                }
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
            }
        }
        return labelHeader;
    }

    private String getVolumenDengueInfluenzaCHF(Participante mParticipante){
        String labelHeader = "";
        //Desde 2 años y < 14 Años
        if(mParticipante.getEdadMeses()>=24 && mParticipante.getEdadMeses()<168) {
            if (mParticipante.getProcesos().getConmx().matches("No")) {
                if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 1cc en tubo Rojo<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                }
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
            }
        }else //De 14 años y más
        {
            if (mParticipante.getProcesos().getConmx().matches("No")) {
                if (mParticipante.getProcesos().getPbmc().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#11BDF7'>Tomar 6cc en tubo PBMC<br /></font></small>";
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 6cc en tubo Rojo<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='red'>Tomar 12cc en tubo Rojo<br /></font></small>";
                }
            }
            if (mParticipante.getProcesos().getConmxbhc().matches("No")) {
                if (mParticipante.getProcesos().getPaxgene().matches("Si")) {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC (Paxgene)<br /></font></small>";
                } else {
                    labelHeader = labelHeader + "<small><font color='#B941E0'>Tomar 1cc para BHC<br /></font></small>";
                }
            }
        }
        return labelHeader;
    }

}
