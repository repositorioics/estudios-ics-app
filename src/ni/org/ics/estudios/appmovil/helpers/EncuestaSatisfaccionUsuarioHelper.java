package ni.org.ics.estudios.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaSatisfaccionUsuario;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaSatisfaccionUsuarioId;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.PerimetroAbdominal;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.PerimetroAbdominalId;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ing. Santiago Carballo on 25/02/2023.
 */
public class EncuestaSatisfaccionUsuarioHelper {
    public static ContentValues crearEncuenstaSatisfaccionUsuarioContentValues(EncuestaSatisfaccionUsuario encuestaSatisfaccionUsuario) {
        ContentValues cv = new ContentValues();

        //cv.put(MainDBConstants.codigo, encuestaSatisfaccionUsuario.getEsId());
        cv.put(MainDBConstants.codigoParticipante, encuestaSatisfaccionUsuario.getCodigoParticipante());
        cv.put(MainDBConstants.atencionMedica, encuestaSatisfaccionUsuario.getAtencionMedica());
        cv.put(MainDBConstants.familaAmistades, encuestaSatisfaccionUsuario.getFamilaAmistades());
        cv.put(MainDBConstants.desicionPropia, encuestaSatisfaccionUsuario.getDesicionPropia());
        cv.put(MainDBConstants.obsequiosOfreceEstudio, encuestaSatisfaccionUsuario.getObsequiosOfreceEstudio());
        cv.put(MainDBConstants.ayudaRecibeComunidad, encuestaSatisfaccionUsuario.getAyudaRecibeComunidad());
        cv.put(MainDBConstants.examenesLaboratorio, encuestaSatisfaccionUsuario.getExamenesLaboratorio());
        cv.put(MainDBConstants.interesCientificoPersonalP1, encuestaSatisfaccionUsuario.getInteresCientificoPersonalP1());
        cv.put(MainDBConstants.informacionAyudaOtros, encuestaSatisfaccionUsuario.getInformacionAyudaOtros());
        cv.put(MainDBConstants.otraP1, encuestaSatisfaccionUsuario.getOtraP1());
        cv.put(MainDBConstants.calidadAtencionMedica, encuestaSatisfaccionUsuario.getCalidadAtencionMedica());
        cv.put(MainDBConstants.atencionOportuna, encuestaSatisfaccionUsuario.getAtencionOportuna());
        cv.put(MainDBConstants.buenaCoordinacionhosp, encuestaSatisfaccionUsuario.getBuenaCoordinacionhosp());
        cv.put(MainDBConstants.infoEstadoSalud, encuestaSatisfaccionUsuario.getInfoEstadoSalud());
        cv.put(MainDBConstants.enseniaPrevEnfermedades, encuestaSatisfaccionUsuario.getEnseniaPrevEnfermedades());
        cv.put(MainDBConstants.infoConsMejoraConocimientos, encuestaSatisfaccionUsuario.getInfoConsMejoraConocimientos());
        cv.put(MainDBConstants.interesCientificoPersonalP2, encuestaSatisfaccionUsuario.getInteresCientificoPersonalP2());
        cv.put(MainDBConstants.mejorarTratamientoDengue, encuestaSatisfaccionUsuario.getMejorarTratamientoDengue());
        cv.put(MainDBConstants.unicaManeraRecibirAtencionMed, encuestaSatisfaccionUsuario.getUnicaManeraRecibirAtencionMed());
        cv.put(MainDBConstants.otraP2, encuestaSatisfaccionUsuario.getOtraP2());
        cv.put(MainDBConstants.difBuscarAtencionMed, encuestaSatisfaccionUsuario.getDifBuscarAtencionMed());
        cv.put(MainDBConstants.centroSaludLejos, encuestaSatisfaccionUsuario.getCentroSaludLejos());
        cv.put(MainDBConstants.costoTrasnporteElevado, encuestaSatisfaccionUsuario.getCostoTrasnporteElevado());
        cv.put(MainDBConstants.trabajoTiempo, encuestaSatisfaccionUsuario.getTrabajoTiempo());
        cv.put(MainDBConstants.noQueriapasarConsulta, encuestaSatisfaccionUsuario.getNoQueriapasarConsulta());
        cv.put(MainDBConstants.horarioClases, encuestaSatisfaccionUsuario.getHorarioClases());
        cv.put(MainDBConstants.temorTomenMx, encuestaSatisfaccionUsuario.getTemorTomenMx());
        cv.put(MainDBConstants.temorInfoPersonal, encuestaSatisfaccionUsuario.getTemorInfoPersonal());
        cv.put(MainDBConstants.otraP3, encuestaSatisfaccionUsuario.getOtraP3());
        cv.put(MainDBConstants.recomendariaAmigoFamiliar, encuestaSatisfaccionUsuario.getRecomendariaAmigoFamiliar());
        cv.put(MainDBConstants.atencionCalidad, encuestaSatisfaccionUsuario.getAtencionCalidad());
        cv.put(MainDBConstants.respNecesidadesSaludOportuna, encuestaSatisfaccionUsuario.getRespNecesidadesSaludOportuna());
        cv.put(MainDBConstants.tiempoEsperaCorto, encuestaSatisfaccionUsuario.getTiempoEsperaCorto());
        cv.put(MainDBConstants.mejorAtencionQueSeguro, encuestaSatisfaccionUsuario.getMejorAtencionQueSeguro());
        cv.put(MainDBConstants.examenLabNecesarios, encuestaSatisfaccionUsuario.getExamenLabNecesarios());
        cv.put(MainDBConstants.conocimientosImportantes, encuestaSatisfaccionUsuario.getConocimientosImportantes());
        cv.put(MainDBConstants.pocosRequisitos, encuestaSatisfaccionUsuario.getPocosRequisitos());
        cv.put(MainDBConstants.otraP_4_1, encuestaSatisfaccionUsuario.getOtraP_4_1());
        cv.put(MainDBConstants.atencionPersonalMala, encuestaSatisfaccionUsuario.getAtencionPersonalMala());
        cv.put(MainDBConstants.noDanRespNecesidades, encuestaSatisfaccionUsuario.getNoDanRespNecesidades());
        cv.put(MainDBConstants.tiempoEsperaLargo, encuestaSatisfaccionUsuario.getTiempoEsperaLargo());
        cv.put(MainDBConstants.mejorAtencionOtraUnidadSalud, encuestaSatisfaccionUsuario.getMejorAtencionOtraUnidadSalud());
        cv.put(MainDBConstants.solicitaDemasiadaMx, encuestaSatisfaccionUsuario.getSolicitaDemasiadaMx());
        cv.put(MainDBConstants.muchosRequisitos, encuestaSatisfaccionUsuario.getMuchosRequisitos());
        cv.put(MainDBConstants.noExplicanHacenMx, encuestaSatisfaccionUsuario.getNoExplicanHacenMx());
        cv.put(MainDBConstants.noConfianza, encuestaSatisfaccionUsuario.getNoConfianza());
        cv.put(MainDBConstants.otraP_4_2, encuestaSatisfaccionUsuario.getOtraP_4_2());
        cv.put(MainDBConstants.comprendeProcedimientos, encuestaSatisfaccionUsuario.getComprendeProcedimientos());
        cv.put(MainDBConstants.noComodoRealizarPreg, encuestaSatisfaccionUsuario.getNoComodoRealizarPreg());
        cv.put(MainDBConstants.noRespondieronPreg, encuestaSatisfaccionUsuario.getNoRespondieronPreg());
        cv.put(MainDBConstants.explicacionRapida, encuestaSatisfaccionUsuario.getExplicacionRapida());
        cv.put(MainDBConstants.noDejaronHacerPreg, encuestaSatisfaccionUsuario.getNoDejaronHacerPreg());
        cv.put(MainDBConstants.otraP_5_1, encuestaSatisfaccionUsuario.getOtraP_5_1());
        cv.put(MainDBConstants.brindaronConsejosPrevencionEnfer, encuestaSatisfaccionUsuario.getBrindaronConsejosPrevencionEnfer());
        cv.put(MainDBConstants.entiendoProcedimientosEstudios, encuestaSatisfaccionUsuario.getEntiendoProcedimientosEstudios());
        cv.put(MainDBConstants.satisfecho, encuestaSatisfaccionUsuario.getSatisfecho());
        cv.put(MainDBConstants.comodoInfoRecolectada, encuestaSatisfaccionUsuario.getComodoInfoRecolectada());
        cv.put(MainDBConstants.noComodoPreguntas, encuestaSatisfaccionUsuario.getNoComodoPreguntas());
        cv.put(MainDBConstants.recomendacionMejorarAtencion, encuestaSatisfaccionUsuario.getRecomendacionMejorarAtencion());
        cv.put(MainDBConstants.importanciaRecibirInformacion, encuestaSatisfaccionUsuario.getImportanciaRecibirInformacion());

        cv.put(MainDBConstants.identificadoEquipo, encuestaSatisfaccionUsuario.getIdentificadoEquipo());
        cv.put(MainDBConstants.estado, String.valueOf(encuestaSatisfaccionUsuario.getEstado()));
        cv.put(MainDBConstants.pasive, String.valueOf(encuestaSatisfaccionUsuario.getPasivo()));
        cv.put(MainDBConstants.fechaRegistro, String.valueOf(encuestaSatisfaccionUsuario.getFechaRegistro()));
        cv.put(MainDBConstants.creado, String.valueOf(encuestaSatisfaccionUsuario.getCreado()));
        cv.put(MainDBConstants.usuarioRegistro, encuestaSatisfaccionUsuario.getUsuarioRegistro());

        return cv;
    }

    public static EncuestaSatisfaccionUsuario crearEncuestaSatisfaccionUsuario(Cursor encuestaSatisfaccion) {

        EncuestaSatisfaccionUsuario mEncSatUsuario = new EncuestaSatisfaccionUsuario();

        //if (encuestaSatisfaccion.getInt(encuestaSatisfaccion.getColumnIndex(MainDBConstants.codigo)) > 0) mEncSatUsuario.setEsId(encuestaSatisfaccion.getInt(encuestaSatisfaccion.getColumnIndex(MainDBConstants.codigo)));
        //if (encuestaSatisfaccion.getInt(encuestaSatisfaccion.getColumnIndex(MainDBConstants.codigoParticipante)) > 0) mEncSatUsuario.setCodigoParticipante(encuestaSatisfaccion.getInt(encuestaSatisfaccion.getColumnIndex(MainDBConstants.codigoParticipante)));
        mEncSatUsuario.setCodigoParticipante(encuestaSatisfaccion.getInt(encuestaSatisfaccion.getColumnIndex(MainDBConstants.codigoParticipante)));
        mEncSatUsuario.setAtencionMedica(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.atencionMedica)));
        mEncSatUsuario.setFamilaAmistades(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.familaAmistades)));
        mEncSatUsuario.setDesicionPropia(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.desicionPropia)));
        mEncSatUsuario.setObsequiosOfreceEstudio(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.obsequiosOfreceEstudio)));
        mEncSatUsuario.setAyudaRecibeComunidad(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.ayudaRecibeComunidad)));
        mEncSatUsuario.setExamenesLaboratorio(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.examenesLaboratorio)));
        mEncSatUsuario.setInteresCientificoPersonalP1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.interesCientificoPersonalP1)));
        mEncSatUsuario.setInformacionAyudaOtros(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.informacionAyudaOtros)));
        mEncSatUsuario.setOtraP1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.otraP1)));
        mEncSatUsuario.setCalidadAtencionMedica(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.calidadAtencionMedica)));
        mEncSatUsuario.setAtencionOportuna(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.atencionOportuna)));
        mEncSatUsuario.setBuenaCoordinacionhosp(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.buenaCoordinacionhosp)));
        mEncSatUsuario.setInfoEstadoSalud(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.infoEstadoSalud)));
        mEncSatUsuario.setEnseniaPrevEnfermedades(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.enseniaPrevEnfermedades)));
        mEncSatUsuario.setInfoConsMejoraConocimientos(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.infoConsMejoraConocimientos)));
        mEncSatUsuario.setInteresCientificoPersonalP2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.interesCientificoPersonalP2)));
        mEncSatUsuario.setMejorarTratamientoDengue(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.mejorarTratamientoDengue)));
        mEncSatUsuario.setUnicaManeraRecibirAtencionMed(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.unicaManeraRecibirAtencionMed)));
        mEncSatUsuario.setOtraP2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.otraP2)));
        mEncSatUsuario.setDifBuscarAtencionMed(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.difBuscarAtencionMed)));
        mEncSatUsuario.setCentroSaludLejos(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.centroSaludLejos)));
        mEncSatUsuario.setCostoTrasnporteElevado(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.costoTrasnporteElevado)));
        mEncSatUsuario.setTrabajoTiempo(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.trabajoTiempo)));
        mEncSatUsuario.setNoQueriapasarConsulta(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.noQueriapasarConsulta)));
        mEncSatUsuario.setHorarioClases(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.horarioClases)));
        mEncSatUsuario.setTemorTomenMx(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.temorTomenMx)));
        mEncSatUsuario.setTemorInfoPersonal(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.temorInfoPersonal)));
        mEncSatUsuario.setOtraP3(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.otraP3)));
        mEncSatUsuario.setRecomendariaAmigoFamiliar(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.recomendariaAmigoFamiliar)));
        mEncSatUsuario.setAtencionCalidad(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.atencionCalidad)));
        mEncSatUsuario.setRespNecesidadesSaludOportuna(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.respNecesidadesSaludOportuna)));
        mEncSatUsuario.setTiempoEsperaCorto(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.tiempoEsperaCorto)));
        mEncSatUsuario.setMejorAtencionQueSeguro(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.mejorAtencionQueSeguro)));
        mEncSatUsuario.setExamenLabNecesarios(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.examenLabNecesarios)));
        mEncSatUsuario.setConocimientosImportantes(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.conocimientosImportantes)));
        mEncSatUsuario.setPocosRequisitos(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.pocosRequisitos)));
        mEncSatUsuario.setOtraP_4_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.otraP_4_1)));
        mEncSatUsuario.setAtencionPersonalMala(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.atencionPersonalMala)));
        mEncSatUsuario.setNoDanRespNecesidades(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.noDanRespNecesidades)));
        mEncSatUsuario.setTiempoEsperaLargo(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.tiempoEsperaLargo)));
        mEncSatUsuario.setMejorAtencionOtraUnidadSalud(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.mejorAtencionOtraUnidadSalud)));
        mEncSatUsuario.setSolicitaDemasiadaMx(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.solicitaDemasiadaMx)));
        mEncSatUsuario.setMuchosRequisitos(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.muchosRequisitos)));
        mEncSatUsuario.setNoExplicanHacenMx(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.noExplicanHacenMx)));
        mEncSatUsuario.setNoConfianza(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.noConfianza)));
        mEncSatUsuario.setOtraP_4_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.otraP_4_2)));
        mEncSatUsuario.setComprendeProcedimientos(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.comprendeProcedimientos)));
        mEncSatUsuario.setNoComodoRealizarPreg(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.noComodoRealizarPreg)));
        mEncSatUsuario.setNoRespondieronPreg(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.noRespondieronPreg)));
        mEncSatUsuario.setExplicacionRapida(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.explicacionRapida)));
        mEncSatUsuario.setNoDejaronHacerPreg(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.noDejaronHacerPreg)));
        mEncSatUsuario.setOtraP_5_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.otraP_5_1)));
        mEncSatUsuario.setBrindaronConsejosPrevencionEnfer(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.brindaronConsejosPrevencionEnfer)));
        mEncSatUsuario.setEntiendoProcedimientosEstudios(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.entiendoProcedimientosEstudios)));
        mEncSatUsuario.setSatisfecho(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.satisfecho)));
        mEncSatUsuario.setComodoInfoRecolectada(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.comodoInfoRecolectada)));
        mEncSatUsuario.setNoComodoPreguntas(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.noComodoPreguntas)));
        mEncSatUsuario.setIdentificadoEquipo(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.identificadoEquipo)));
        mEncSatUsuario.setEstado(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mEncSatUsuario.setPasivo(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mEncSatUsuario.setFechaRegistro(null);
        mEncSatUsuario.setCreado(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.creado)));


        mEncSatUsuario.setUsuarioRegistro(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.usuarioRegistro)));

        return mEncSatUsuario;
    }
}
