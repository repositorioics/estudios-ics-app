package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;

import java.io.Serializable;
import java.util.Date;

public class EncuestaSatisfaccionUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    //private Integer esId;
    private Integer codigoParticipante;
    private String atencionMedica;
    private String familaAmistades;
    private String desicionPropia;
    private String obsequiosOfreceEstudio;
    private String ayudaRecibeComunidad;
    private String examenesLaboratorio;
    private String interesCientificoPersonalP1;
    private String informacionAyudaOtros;
    private String otraP1;
    private String calidadAtencionMedica;
    private String atencionOportuna;
    private String buenaCoordinacionhosp;
    private String infoEstadoSalud;
    private String enseniaPrevEnfermedades;
    private String infoConsMejoraConocimientos;
    private String interesCientificoPersonalP2;
    private String mejorarTratamientoDengue;
    private String unicaManeraRecibirAtencionMed;
    private String otraP2;
    private String difBuscarAtencionMed;
    private String centroSaludLejos;
    private String costoTrasnporteElevado;
    private String trabajoTiempo;
    private String noQueriapasarConsulta;
    private String horarioClases;
    private String temorTomenMx;
    private String temorInfoPersonal;
    private String otraP3;
    private String recomendariaAmigoFamiliar;
    private String atencionCalidad;
    private String respNecesidadesSaludOportuna;
    private String tiempoEsperaCorto;
    private String mejorAtencionQueSeguro;
    private String examenLabNecesarios;
    private String conocimientosImportantes;
    private String pocosRequisitos;
    private String otraP_4_1;
    private String atencionPersonalMala;
    private String noDanRespNecesidades;
    private String tiempoEsperaLargo;
    private String mejorAtencionOtraUnidadSalud;
    private String solicitaDemasiadaMx;
    private String muchosRequisitos;
    private String noExplicanHacenMx;
    private String noConfianza;
    private String otraP_4_2;
    private String comprendeProcedimientos;
    private String noComodoRealizarPreg;
    private String noRespondieronPreg;
    private String explicacionRapida;
    private String noDejaronHacerPreg;
    private String otraP_5_1;
    private String brindaronConsejosPrevencionEnfer;
    private String entiendoProcedimientosEstudios;
    private String satisfecho;
    private String comodoInfoRecolectada;
    private String noComodoPreguntas;
    private String recomendacionMejorarAtencion;
    private String importanciaRecibirInformacion;
    private String identificadoEquipo;
    private char estado;
    private char pasivo;
    private Date fechaRegistro;
    private String creado;
    private String usuarioRegistro;

    //Nuevos campos agregados 06/03/2023
    private String nombre1Tutor;
    private String nombre2Tutor;
    private String apellido1Tutor;
    private String apellido2Tutor;
    private Integer codigoCasa;
    private String casaChf;
    private String estudio;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /*public Integer getEsId() {
        return esId;
    }

    public void setEsId(Integer esId) {
        this.esId = esId;
    }*/

    public String getAtencionMedica() {
        return atencionMedica;
    }

    public void setAtencionMedica(String atencionMedica) {
        this.atencionMedica = atencionMedica;
    }

    public String getFamilaAmistades() {
        return familaAmistades;
    }

    public void setFamilaAmistades(String familaAmistades) {
        this.familaAmistades = familaAmistades;
    }

    public String getDesicionPropia() {
        return desicionPropia;
    }

    public void setDesicionPropia(String desicionPropia) {
        this.desicionPropia = desicionPropia;
    }

    public String getObsequiosOfreceEstudio() {
        return obsequiosOfreceEstudio;
    }

    public void setObsequiosOfreceEstudio(String obsequiosOfreceEstudio) {
        this.obsequiosOfreceEstudio = obsequiosOfreceEstudio;
    }

    public String getAyudaRecibeComunidad() {
        return ayudaRecibeComunidad;
    }

    public void setAyudaRecibeComunidad(String ayudaRecibeComunidad) {
        this.ayudaRecibeComunidad = ayudaRecibeComunidad;
    }

    public String getExamenesLaboratorio() {
        return examenesLaboratorio;
    }

    public void setExamenesLaboratorio(String examenesLaboratorio) {
        this.examenesLaboratorio = examenesLaboratorio;
    }

    public String getInteresCientificoPersonalP1() {
        return interesCientificoPersonalP1;
    }

    public void setInteresCientificoPersonalP1(String interesCientificoPersonalP1) {
        this.interesCientificoPersonalP1 = interesCientificoPersonalP1;
    }

    public String getInformacionAyudaOtros() {
        return informacionAyudaOtros;
    }

    public void setInformacionAyudaOtros(String informacionAyudaOtros) {
        this.informacionAyudaOtros = informacionAyudaOtros;
    }

    public String getOtraP1() {
        return otraP1;
    }

    public void setOtraP1(String otraP1) {
        this.otraP1 = otraP1;
    }

    public String getCalidadAtencionMedica() {
        return calidadAtencionMedica;
    }

    public void setCalidadAtencionMedica(String calidadAtencionMedica) {
        this.calidadAtencionMedica = calidadAtencionMedica;
    }

    public String getBuenaCoordinacionhosp() {
        return buenaCoordinacionhosp;
    }

    public void setBuenaCoordinacionhosp(String buenaCoordinacionhosp) {
        this.buenaCoordinacionhosp = buenaCoordinacionhosp;
    }

    public String getInfoEstadoSalud() {
        return infoEstadoSalud;
    }

    public void setInfoEstadoSalud(String infoEstadoSalud) {
        this.infoEstadoSalud = infoEstadoSalud;
    }

    public String getEnseniaPrevEnfermedades() {
        return enseniaPrevEnfermedades;
    }

    public void setEnseniaPrevEnfermedades(String enseniaPrevEnfermedades) {
        this.enseniaPrevEnfermedades = enseniaPrevEnfermedades;
    }

    public String getInfoConsMejoraConocimientos() {
        return infoConsMejoraConocimientos;
    }

    public void setInfoConsMejoraConocimientos(String infoConsMejoraConocimientos) {
        this.infoConsMejoraConocimientos = infoConsMejoraConocimientos;
    }

    public String getInteresCientificoPersonalP2() {
        return interesCientificoPersonalP2;
    }

    public void setInteresCientificoPersonalP2(String interesCientificoPersonalP2) {
        this.interesCientificoPersonalP2 = interesCientificoPersonalP2;
    }

    public String getMejorarTratamientoDengue() {
        return mejorarTratamientoDengue;
    }

    public void setMejorarTratamientoDengue(String mejorarTratamientoDengue) {
        this.mejorarTratamientoDengue = mejorarTratamientoDengue;
    }

    public String getUnicaManeraRecibirAtencionMed() {
        return unicaManeraRecibirAtencionMed;
    }

    public void setUnicaManeraRecibirAtencionMed(String unicaManeraRecibirAtencionMed) {
        this.unicaManeraRecibirAtencionMed = unicaManeraRecibirAtencionMed;
    }

    public String getOtraP2() {
        return otraP2;
    }

    public void setOtraP2(String otraP2) {
        this.otraP2 = otraP2;
    }

    public String getDifBuscarAtencionMed() {
        return difBuscarAtencionMed;
    }

    public void setDifBuscarAtencionMed(String difBuscarAtencionMed) {
        this.difBuscarAtencionMed = difBuscarAtencionMed;
    }

    public String getCentroSaludLejos() {
        return centroSaludLejos;
    }

    public void setCentroSaludLejos(String centroSaludLejos) {
        this.centroSaludLejos = centroSaludLejos;
    }

    public String getCostoTrasnporteElevado() {
        return costoTrasnporteElevado;
    }

    public void setCostoTrasnporteElevado(String costoTrasnporteElevado) {
        this.costoTrasnporteElevado = costoTrasnporteElevado;
    }

    public String getTrabajoTiempo() {
        return trabajoTiempo;
    }

    public void setTrabajoTiempo(String trabajoTiempo) {
        this.trabajoTiempo = trabajoTiempo;
    }

    public String getNoQueriapasarConsulta() {
        return noQueriapasarConsulta;
    }

    public void setNoQueriapasarConsulta(String noQueriapasarConsulta) {
        this.noQueriapasarConsulta = noQueriapasarConsulta;
    }

    public String getHorarioClases() {
        return horarioClases;
    }

    public void setHorarioClases(String horarioClases) {
        this.horarioClases = horarioClases;
    }

    public String getTemorTomenMx() {
        return temorTomenMx;
    }

    public void setTemorTomenMx(String temorTomenMx) {
        this.temorTomenMx = temorTomenMx;
    }

    public String getTemorInfoPersonal() {
        return temorInfoPersonal;
    }

    public void setTemorInfoPersonal(String temorInfoPersonal) {
        this.temorInfoPersonal = temorInfoPersonal;
    }

    public String getOtraP3() {
        return otraP3;
    }

    public void setOtraP3(String otraP3) {
        this.otraP3 = otraP3;
    }

    public String getRecomendariaAmigoFamiliar() {
        return recomendariaAmigoFamiliar;
    }

    public void setRecomendariaAmigoFamiliar(String recomendariaAmigoFamiliar) {
        this.recomendariaAmigoFamiliar = recomendariaAmigoFamiliar;
    }

    public String getAtencionCalidad() {
        return atencionCalidad;
    }

    public void setAtencionCalidad(String atencionCalidad) {
        this.atencionCalidad = atencionCalidad;
    }

    public String getRespNecesidadesSaludOportuna() {
        return respNecesidadesSaludOportuna;
    }

    public void setRespNecesidadesSaludOportuna(String respNecesidadesSaludOportuna) {
        this.respNecesidadesSaludOportuna = respNecesidadesSaludOportuna;
    }

    public String getTiempoEsperaCorto() {
        return tiempoEsperaCorto;
    }

    public void setTiempoEsperaCorto(String tiempoEsperaCorto) {
        this.tiempoEsperaCorto = tiempoEsperaCorto;
    }

    public String getMejorAtencionQueSeguro() {
        return mejorAtencionQueSeguro;
    }

    public void setMejorAtencionQueSeguro(String mejorAtencionQueSeguro) {
        this.mejorAtencionQueSeguro = mejorAtencionQueSeguro;
    }

    public String getExamenLabNecesarios() {
        return examenLabNecesarios;
    }

    public void setExamenLabNecesarios(String examenLabNecesarios) {
        this.examenLabNecesarios = examenLabNecesarios;
    }

    public String getConocimientosImportantes() {
        return conocimientosImportantes;
    }

    public void setConocimientosImportantes(String conocimientosImportantes) {
        this.conocimientosImportantes = conocimientosImportantes;
    }

    public String getPocosRequisitos() {
        return pocosRequisitos;
    }

    public void setPocosRequisitos(String pocosRequisitos) {
        this.pocosRequisitos = pocosRequisitos;
    }

    public String getOtraP_4_1() {
        return otraP_4_1;
    }

    public void setOtraP_4_1(String otraP_4_1) {
        this.otraP_4_1 = otraP_4_1;
    }

    public String getAtencionPersonalMala() {
        return atencionPersonalMala;
    }

    public void setAtencionPersonalMala(String atencionPersonalMala) {
        this.atencionPersonalMala = atencionPersonalMala;
    }

    public String getNoDanRespNecesidades() {
        return noDanRespNecesidades;
    }

    public void setNoDanRespNecesidades(String noDanRespNecesidades) {
        this.noDanRespNecesidades = noDanRespNecesidades;
    }

    public String getTiempoEsperaLargo() {
        return tiempoEsperaLargo;
    }

    public void setTiempoEsperaLargo(String tiempoEsperaLargo) {
        this.tiempoEsperaLargo = tiempoEsperaLargo;
    }

    public String getMejorAtencionOtraUnidadSalud() {
        return mejorAtencionOtraUnidadSalud;
    }

    public void setMejorAtencionOtraUnidadSalud(String mejorAtencionOtraUnidadSalud) {
        this.mejorAtencionOtraUnidadSalud = mejorAtencionOtraUnidadSalud;
    }

    public String getSolicitaDemasiadaMx() {
        return solicitaDemasiadaMx;
    }

    public void setSolicitaDemasiadaMx(String solicitaDemasiadaMx) {
        this.solicitaDemasiadaMx = solicitaDemasiadaMx;
    }

    public String getMuchosRequisitos() {
        return muchosRequisitos;
    }

    public void setMuchosRequisitos(String muchosRequisitos) {
        this.muchosRequisitos = muchosRequisitos;
    }

    public String getNoExplicanHacenMx() {
        return noExplicanHacenMx;
    }

    public void setNoExplicanHacenMx(String noExplicanHacenMx) {
        this.noExplicanHacenMx = noExplicanHacenMx;
    }

    public String getNoConfianza() {
        return noConfianza;
    }

    public void setNoConfianza(String noConfianza) {
        this.noConfianza = noConfianza;
    }

    public String getOtraP_4_2() {
        return otraP_4_2;
    }

    public void setOtraP_4_2(String otraP_4_2) {
        this.otraP_4_2 = otraP_4_2;
    }

    public String getComprendeProcedimientos() {
        return comprendeProcedimientos;
    }

    public void setComprendeProcedimientos(String comprendeProcedimientos) {
        this.comprendeProcedimientos = comprendeProcedimientos;
    }

    public String getNoComodoRealizarPreg() {
        return noComodoRealizarPreg;
    }

    public void setNoComodoRealizarPreg(String noComodoRealizarPreg) {
        this.noComodoRealizarPreg = noComodoRealizarPreg;
    }

    public String getNoRespondieronPreg() {
        return noRespondieronPreg;
    }

    public void setNoRespondieronPreg(String noRespondieronPreg) {
        this.noRespondieronPreg = noRespondieronPreg;
    }

    public String getExplicacionRapida() {
        return explicacionRapida;
    }

    public void setExplicacionRapida(String explicacionRapida) {
        this.explicacionRapida = explicacionRapida;
    }

    public String getNoDejaronHacerPreg() {
        return noDejaronHacerPreg;
    }

    public void setNoDejaronHacerPreg(String noDejaronHacerPreg) {
        this.noDejaronHacerPreg = noDejaronHacerPreg;
    }

    public String getOtraP_5_1() {
        return otraP_5_1;
    }

    public void setOtraP_5_1(String otraP_5_1) {
        this.otraP_5_1 = otraP_5_1;
    }

    public String getBrindaronConsejosPrevencionEnfer() {
        return brindaronConsejosPrevencionEnfer;
    }

    public void setBrindaronConsejosPrevencionEnfer(String brindaronConsejosPrevencionEnfer) {
        this.brindaronConsejosPrevencionEnfer = brindaronConsejosPrevencionEnfer;
    }

    public String getEntiendoProcedimientosEstudios() {
        return entiendoProcedimientosEstudios;
    }

    public void setEntiendoProcedimientosEstudios(String entiendoProcedimientosEstudios) {
        this.entiendoProcedimientosEstudios = entiendoProcedimientosEstudios;
    }

    public String getSatisfecho() {
        return satisfecho;
    }

    public void setSatisfecho(String satisfecho) {
        this.satisfecho = satisfecho;
    }

    public String getComodoInfoRecolectada() {
        return comodoInfoRecolectada;
    }

    public void setComodoInfoRecolectada(String comodoInfoRecolectada) {
        this.comodoInfoRecolectada = comodoInfoRecolectada;
    }

    public String getNoComodoPreguntas() {
        return noComodoPreguntas;
    }

    public void setNoComodoPreguntas(String noComodoPreguntas) {
        this.noComodoPreguntas = noComodoPreguntas;
    }

    public String getRecomendacionMejorarAtencion() {
        return recomendacionMejorarAtencion;
    }

    public void setRecomendacionMejorarAtencion(String recomendacionMejorarAtencion) {
        this.recomendacionMejorarAtencion = recomendacionMejorarAtencion;
    }

    public String getImportanciaRecibirInformacion() {
        return importanciaRecibirInformacion;
    }

    public void setImportanciaRecibirInformacion(String importanciaRecibirInformacion) {
        this.importanciaRecibirInformacion = importanciaRecibirInformacion;
    }

    public Integer getCodigoParticipante() {
        return codigoParticipante;
    }

    public void setCodigoParticipante(Integer codigoParticipante) {
        this.codigoParticipante = codigoParticipante;
    }

    public String getAtencionOportuna() {
        return atencionOportuna;
    }

    public void setAtencionOportuna(String atencionOportuna) {
        this.atencionOportuna = atencionOportuna;
    }

    public String getIdentificadoEquipo() {
        return identificadoEquipo;
    }

    public void setIdentificadoEquipo(String identificadoEquipo) {
        this.identificadoEquipo = identificadoEquipo;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public char getPasivo() {
        return pasivo;
    }

    public void setPasivo(char pasivo) {
        this.pasivo = pasivo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

    public String getNombre1Tutor() {
        return nombre1Tutor;
    }

    public void setNombre1Tutor(String nombre1Tutor) {
        this.nombre1Tutor = nombre1Tutor;
    }

    public String getNombre2Tutor() {
        return nombre2Tutor;
    }

    public void setNombre2Tutor(String nombre2Tutor) {
        this.nombre2Tutor = nombre2Tutor;
    }

    public String getApellido1Tutor() {
        return apellido1Tutor;
    }

    public void setApellido1Tutor(String apellido1Tutor) {
        this.apellido1Tutor = apellido1Tutor;
    }

    public String getApellido2Tutor() {
        return apellido2Tutor;
    }

    public void setApellido2Tutor(String apellido2Tutor) {
        this.apellido2Tutor = apellido2Tutor;
    }

    public Integer getCodigoCasa() {
        return codigoCasa;
    }

    public void setCodigoCasa(Integer codigoCasa) {
        this.codigoCasa = codigoCasa;
    }

    public String getCasaChf() {
        return casaChf;
    }

    public void setCasaChf(String casaChf) {
        this.casaChf = casaChf;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }
}
