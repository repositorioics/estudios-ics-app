package ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */
public class EncuestaParticipante extends BaseMetaData {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ParticipanteCohorteFamilia participante;
    //Adulto   (Mayores o igual a 18 años; Mujeres menores de 18 años con niños o embarazadas)
    //private String emancipado;
    //private String motivoEmacipacion;
    //private String otroMotivoEmancipacion;
    private String estaEmbarazada;
    private Integer semanasEmbarazo;
    private String esAlfabeto;
    private String nivelEducacion;
    private String trabaja;
    private String tipoTrabajo;
    private String ocupacionActual;
    //Niño
    private String vaNinoEscuela;
    private String gradoCursa;
    private String turno;
    private String nombreCentroEstudio;
    private String dondeCuidanNino;
    private String ninoTrabaja;
    private String ocupacionActualNino;
    private Integer cantNinosLugarCuidan; //Cuántos niños aproximadamente hay en el lugar que cuidan al niño
    //Datos generales de los padres y factores de hacinamiento
    private String conQuienViveNino;
    private String descOtroViveNino;
    private String padreEnEstudio;
    private String codigoPadreEstudio;
    private String padreAlfabeto;
    private String nivelEducacionPadre;
    private String trabajaPadre;
    private String tipoTrabajoPadre;
    private String madreEnEstudio;
    private String codigoMadreEstudio;
    private String madreAlfabeto;
    private String nivelEducacionMadre;
    private String trabajaMadre;
    private String tipoTrabajoMadre;
    // Para todos los participantes
    private String fuma;
    private String periodicidadFuma;
    private Integer cantidadCigarrillos;
    private String fumaDentroCasa;
    //Antecedentes de Salud
    private String tuberculosisPulmonarActual;
    private String fechaDiagnosticoTubPulActual;
    private String tomaTratamientoTubPulActual;
    private String completoTratamientoTubPulActual;
    private String tuberculosisPulmonarPasado;
    private String fechaDiagnosticoTubPulPasado;
    private String fechaDiagnosticoTubPulPasadoDes;//fecha de diagnóstico desconocida
    private String tomaTratamientoTubPulPasado;
    private String completoTratamientoTubPulPasado;
    private String alergiaRespiratoria;
    private String cardiopatia;
    private String enfermedadPulmonarOC; // enfermedad pulmonar obstructiva crónica
    private String diabetes;
    private String presionAlta;
    private String asma;
    private String silbidoRespirarPechoApretado;
    private String tosSinFiebreResfriado;
    private String usaInhaladoresSpray;
    private String crisisAsma;
    private Integer cantidadCrisisAsma;
    private Integer vecesEnfermoEnfermedadesRes; //veces enfermo en el último año por cuadros o enfermedades respiratorias
    private String otrasEnfermedades;
    private String descOtrasEnfermedades;
    private String vacunaInfluenza;
    private Integer anioVacunaInfluenza;
    private String recurso1;
    private String otrorecurso1;

    public ParticipanteCohorteFamilia getParticipante() {
        return participante;
    }

    public void setParticipante(ParticipanteCohorteFamilia participante) {
        this.participante = participante;
    }
    /*
        public String getEmancipado() {
            return emancipado;
        }

        public void setEmancipado(String emancipado) {
            this.emancipado = emancipado;
        }

        public String getMotivoEmacipacion() {
            return motivoEmacipacion;
        }

        public void setMotivoEmacipacion(String motivoEmacipacion) {
            this.motivoEmacipacion = motivoEmacipacion;
        }

        public String getOtroMotivoEmancipacion() {
            return otroMotivoEmancipacion;
        }

        public void setOtroMotivoEmancipacion(String otroMotivoEmancipacion) {
            this.otroMotivoEmancipacion = otroMotivoEmancipacion;
        }
    */
    public String getEstaEmbarazada() {
        return estaEmbarazada;
    }

    public void setEstaEmbarazada(String estaEmbarazada) {
        this.estaEmbarazada = estaEmbarazada;
    }

    public Integer getSemanasEmbarazo() {
        return semanasEmbarazo;
    }

    public void setSemanasEmbarazo(Integer semanasEmbarazo) {
        this.semanasEmbarazo = semanasEmbarazo;
    }

    public String getEsAlfabeto() {
        return esAlfabeto;
    }

    public void setEsAlfabeto(String esAlfabeto) {
        this.esAlfabeto = esAlfabeto;
    }

    public String getNivelEducacion() {
        return nivelEducacion;
    }

    public void setNivelEducacion(String nivelEducacion) {
        this.nivelEducacion = nivelEducacion;
    }

    public String getTrabaja() {
        return trabaja;
    }

    public void setTrabaja(String trabaja) {
        this.trabaja = trabaja;
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public String getOcupacionActual() {
        return ocupacionActual;
    }

    public void setOcupacionActual(String ocupacionActual) {
        this.ocupacionActual = ocupacionActual;
    }

    public String getVaNinoEscuela() {
        return vaNinoEscuela;
    }

    public void setVaNinoEscuela(String vaNinoEscuela) {
        this.vaNinoEscuela = vaNinoEscuela;
    }

    public String getGradoCursa() {
        return gradoCursa;
    }

    public void setGradoCursa(String gradoCursa) {
        this.gradoCursa = gradoCursa;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getNombreCentroEstudio() {
        return nombreCentroEstudio;
    }

    public void setNombreCentroEstudio(String nombreCentroEstudio) {
        this.nombreCentroEstudio = nombreCentroEstudio;
    }

    public String getDondeCuidanNino() {
        return dondeCuidanNino;
    }

    public void setDondeCuidanNino(String dondeCuidanNino) {
        this.dondeCuidanNino = dondeCuidanNino;
    }

    public String getNinoTrabaja() {
        return ninoTrabaja;
    }

    public void setNinoTrabaja(String ninoTrabaja) {
        this.ninoTrabaja = ninoTrabaja;
    }

    public String getOcupacionActualNino() {
        return ocupacionActualNino;
    }

    public void setOcupacionActualNino(String ocupacionActualNino) {
        this.ocupacionActualNino = ocupacionActualNino;
    }

    public Integer getCantNinosLugarCuidan() {
        return cantNinosLugarCuidan;
    }

    public void setCantNinosLugarCuidan(Integer cantNinosLugarCuidan) {
        this.cantNinosLugarCuidan = cantNinosLugarCuidan;
    }

    public String getConQuienViveNino() {
        return conQuienViveNino;
    }

    public void setConQuienViveNino(String conQuienViveNino) {
        this.conQuienViveNino = conQuienViveNino;
    }

    public String getDescOtroViveNino() {
        return descOtroViveNino;
    }

    public void setDescOtroViveNino(String descOtroViveNino) {
        this.descOtroViveNino = descOtroViveNino;
    }

    public String getPadreEnEstudio() {
        return padreEnEstudio;
    }

    public void setPadreEnEstudio(String padreEnEstudio) {
        this.padreEnEstudio = padreEnEstudio;
    }

    public String getCodigoPadreEstudio() {
        return codigoPadreEstudio;
    }

    public void setCodigoPadreEstudio(String codigoPadreEstudio) {
        this.codigoPadreEstudio = codigoPadreEstudio;
    }

    public String getPadreAlfabeto() {
        return padreAlfabeto;
    }

    public void setPadreAlfabeto(String padreAlfabeto) {
        this.padreAlfabeto = padreAlfabeto;
    }

    public String getNivelEducacionPadre() {
        return nivelEducacionPadre;
    }

    public void setNivelEducacionPadre(String nivelEducacionPadre) {
        this.nivelEducacionPadre = nivelEducacionPadre;
    }

    public String getTrabajaPadre() {
        return trabajaPadre;
    }

    public void setTrabajaPadre(String trabajaPadre) {
        this.trabajaPadre = trabajaPadre;
    }

    public String getTipoTrabajoPadre() {
        return tipoTrabajoPadre;
    }

    public void setTipoTrabajoPadre(String tipoTrabajoPadre) {
        this.tipoTrabajoPadre = tipoTrabajoPadre;
    }

    public String getMadreEnEstudio() {
        return madreEnEstudio;
    }

    public void setMadreEnEstudio(String madreEnEstudio) {
        this.madreEnEstudio = madreEnEstudio;
    }

    public String getCodigoMadreEstudio() {
        return codigoMadreEstudio;
    }

    public void setCodigoMadreEstudio(String codigoMadreEstudio) {
        this.codigoMadreEstudio = codigoMadreEstudio;
    }

    public String getMadreAlfabeto() {
        return madreAlfabeto;
    }

    public void setMadreAlfabeto(String madreAlfabeto) {
        this.madreAlfabeto = madreAlfabeto;
    }

    public String getNivelEducacionMadre() {
        return nivelEducacionMadre;
    }

    public void setNivelEducacionMadre(String nivelEducacionMadre) {
        this.nivelEducacionMadre = nivelEducacionMadre;
    }

    public String getTrabajaMadre() {
        return trabajaMadre;
    }

    public void setTrabajaMadre(String trabajaMadre) {
        this.trabajaMadre = trabajaMadre;
    }

    public String getTipoTrabajoMadre() {
        return tipoTrabajoMadre;
    }

    public void setTipoTrabajoMadre(String tipoTrabajoMadre) {
        this.tipoTrabajoMadre = tipoTrabajoMadre;
    }

    public String getFuma() {
        return fuma;
    }

    public void setFuma(String fuma) {
        this.fuma = fuma;
    }

    public String getPeriodicidadFuma() {
        return periodicidadFuma;
    }

    public void setPeriodicidadFuma(String periodicidadFuma) {
        this.periodicidadFuma = periodicidadFuma;
    }

    public Integer getCantidadCigarrillos() {
        return cantidadCigarrillos;
    }

    public void setCantidadCigarrillos(Integer cantidadCigarrillos) {
        this.cantidadCigarrillos = cantidadCigarrillos;
    }

    public String getFumaDentroCasa() {
        return fumaDentroCasa;
    }

    public void setFumaDentroCasa(String fumaDentroCasa) {
        this.fumaDentroCasa = fumaDentroCasa;
    }

    public String getTuberculosisPulmonarActual() {
        return tuberculosisPulmonarActual;
    }

    public void setTuberculosisPulmonarActual(String tuberculosisPulmonarActual) {
        this.tuberculosisPulmonarActual = tuberculosisPulmonarActual;
    }

    public String getFechaDiagnosticoTubPulActual() {
        return fechaDiagnosticoTubPulActual;
    }

    public void setFechaDiagnosticoTubPulActual(String fechaDiagnosticoTubPulActual) {
        this.fechaDiagnosticoTubPulActual = fechaDiagnosticoTubPulActual;
    }

    public String getTomaTratamientoTubPulActual() {
        return tomaTratamientoTubPulActual;
    }

    public void setTomaTratamientoTubPulActual(String tomaTratamientoTubPulActual) {
        this.tomaTratamientoTubPulActual = tomaTratamientoTubPulActual;
    }

    public String getCompletoTratamientoTubPulActual() {
        return completoTratamientoTubPulActual;
    }

    public void setCompletoTratamientoTubPulActual(String completoTratamientoTubPulActual) {
        this.completoTratamientoTubPulActual = completoTratamientoTubPulActual;
    }

    public String getTuberculosisPulmonarPasado() {
        return tuberculosisPulmonarPasado;
    }

    public void setTuberculosisPulmonarPasado(String tuberculosisPulmonarPasado) {
        this.tuberculosisPulmonarPasado = tuberculosisPulmonarPasado;
    }

    public String getFechaDiagnosticoTubPulPasado() {
        return fechaDiagnosticoTubPulPasado;
    }

    public void setFechaDiagnosticoTubPulPasado(String fechaDiagnosticoTubPulPasado) {
        this.fechaDiagnosticoTubPulPasado = fechaDiagnosticoTubPulPasado;
    }

    public String getFechaDiagnosticoTubPulPasadoDes() {
        return fechaDiagnosticoTubPulPasadoDes;
    }

    public void setFechaDiagnosticoTubPulPasadoDes(String fechaDiagnosticoTubPulPasadoDes) {
        this.fechaDiagnosticoTubPulPasadoDes = fechaDiagnosticoTubPulPasadoDes;
    }

    public String getTomaTratamientoTubPulPasado() {
        return tomaTratamientoTubPulPasado;
    }

    public void setTomaTratamientoTubPulPasado(String tomaTratamientoTubPulPasado) {
        this.tomaTratamientoTubPulPasado = tomaTratamientoTubPulPasado;
    }

    public String getCompletoTratamientoTubPulPasado() {
        return completoTratamientoTubPulPasado;
    }

    public void setCompletoTratamientoTubPulPasado(String completoTratamientoTubPulPasado) {
        this.completoTratamientoTubPulPasado = completoTratamientoTubPulPasado;
    }

    public String getAlergiaRespiratoria() {
        return alergiaRespiratoria;
    }

    public void setAlergiaRespiratoria(String alergiaRespiratoria) {
        this.alergiaRespiratoria = alergiaRespiratoria;
    }

    public String getCardiopatia() {
        return cardiopatia;
    }

    public void setCardiopatia(String cardiopatia) {
        this.cardiopatia = cardiopatia;
    }

    public String getEnfermedadPulmonarOC() {
        return enfermedadPulmonarOC;
    }

    public void setEnfermedadPulmonarOC(String enfermedadPulmonarOC) {
        this.enfermedadPulmonarOC = enfermedadPulmonarOC;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    public String getPresionAlta() {
        return presionAlta;
    }

    public void setPresionAlta(String presionAlta) {
        this.presionAlta = presionAlta;
    }

    public String getAsma() {
        return asma;
    }

    public void setAsma(String asma) {
        this.asma = asma;
    }

    public String getSilbidoRespirarPechoApretado() {
        return silbidoRespirarPechoApretado;
    }

    public void setSilbidoRespirarPechoApretado(String silbidoRespirarPechoApretado) {
        this.silbidoRespirarPechoApretado = silbidoRespirarPechoApretado;
    }

    public String getTosSinFiebreResfriado() {
        return tosSinFiebreResfriado;
    }

    public void setTosSinFiebreResfriado(String tosSinFiebreResfriado) {
        this.tosSinFiebreResfriado = tosSinFiebreResfriado;
    }

    public String getUsaInhaladoresSpray() {
        return usaInhaladoresSpray;
    }

    public void setUsaInhaladoresSpray(String usaInhaladoresSpray) {
        this.usaInhaladoresSpray = usaInhaladoresSpray;
    }

    public String getCrisisAsma() {
        return crisisAsma;
    }

    public void setCrisisAsma(String crisisAsma) {
        this.crisisAsma = crisisAsma;
    }

    public Integer getCantidadCrisisAsma() {
        return cantidadCrisisAsma;
    }

    public void setCantidadCrisisAsma(Integer cantidadCrisisAsma) {
        this.cantidadCrisisAsma = cantidadCrisisAsma;
    }

    public Integer getVecesEnfermoEnfermedadesRes() {
        return vecesEnfermoEnfermedadesRes;
    }

    public void setVecesEnfermoEnfermedadesRes(Integer vecesEnfermoEnfermedadesRes) {
        this.vecesEnfermoEnfermedadesRes = vecesEnfermoEnfermedadesRes;
    }

    public String getOtrasEnfermedades() {
        return otrasEnfermedades;
    }

    public void setOtrasEnfermedades(String otrasEnfermedades) {
        this.otrasEnfermedades = otrasEnfermedades;
    }

    public String getDescOtrasEnfermedades() {
        return descOtrasEnfermedades;
    }

    public void setDescOtrasEnfermedades(String descOtrasEnfermedades) {
        this.descOtrasEnfermedades = descOtrasEnfermedades;
    }

    public String getVacunaInfluenza() {
        return vacunaInfluenza;
    }

    public void setVacunaInfluenza(String vacunaInfluenza) {
        this.vacunaInfluenza = vacunaInfluenza;
    }

    public Integer getAnioVacunaInfluenza() {
        return anioVacunaInfluenza;
    }

    public void setAnioVacunaInfluenza(Integer anioVacunaInfluenza) {
        this.anioVacunaInfluenza = anioVacunaInfluenza;
    }

    public String getRecurso1() {
        return recurso1;
    }

    public void setRecurso1(String recurso1) {
        this.recurso1 = recurso1;
    }

    public String getOtrorecurso1() {
        return otrorecurso1;
    }

    public void setOtrorecurso1(String otrorecurso1) {
        this.otrorecurso1 = otrorecurso1;
    }

    @Override
    public String toString() {
        return "EncuestaParticipante{" + participante.getParticipanteCHF() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncuestaParticipante)) return false;

        EncuestaParticipante that = (EncuestaParticipante) o;

        return  (!participante.equals(that.participante));
    }

    @Override
    public int hashCode() {
        return participante.hashCode();
    }
}
