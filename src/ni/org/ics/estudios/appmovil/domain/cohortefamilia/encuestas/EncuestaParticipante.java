package ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas;

import ni.org.ics.estudios.appmovil.domain.BaseMetaData;
import ni.org.ics.estudios.appmovil.domain.Participante;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */
public class EncuestaParticipante extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Participante participante;
    //Adulto   (Mayores o igual a 18 años; Mujeres menores de 18 años con niños o embarazadas)
    private char emancipado;
    private String motivoEmacipacion;
    private String otroMotivoEmancipacion;
    private char estaEmbarazada;
    private Integer semanasEmbarazo;
    private char esAlfabeto;
    private String nivelEducacion;
    private char trabaja;
    private String tipoTrabajo;
    private String ocupacionActual;
    //Niño
    private char vaNinoEscuela;
    private String gradoCursa;
    private String turno;
    private String nombreCentroEstudio;
    private String dondeCuidanNino;
    private char ninoTrabaja;
    private String ocupacionActualNino;
    private Integer cantNinosLugarCuidan; //Cuántos niños aproximadamente hay en el lugar que cuidan al niño
    //Datos generales de los padres y factores de hacinamiento
    private String conQuienViveNino;
    private String descOtroViveNino;
    private char padreEnEstudio;
    private Integer codigoPadreEstudio;
    private char padreAlfabeto;
    private String nivelEducacionPadre;
    private char trabajaPadre;
    private String tipoTrabajoPadre;
    private char madreEnEstudio;
    private Integer codigoMadreEstudio;
    private char madreAlfabeto;
    private String nivelEducacionMadre;
    private char trabajaMadre;
    private String tipoTrabajoMadre;
    // Para todos los participantes
    private char fuma;
    private String periodicidadFuma;
    private Integer cantidadCigarrillos;
    private char fumaDentroCasa;
    //Antecedentes de Salud
    private char tuberculosisPulmonarActual;
    private String fechaDiagnosticoTubPulActual;
    private char tomaTratamientoTubPulActual;
    private char completoTratamientoTubPulActual;
    private char tuberculosisPulmonarPasado;
    private String fechaDiagnosticoTubPulPasado;
    private char tomaTratamientoTubPulPasado;
    private char completoTratamientoTubPulPasado;
    private char alergiaRespiratoria;
    private char cardiopatia;
    private char enfermedadPulmonarOC; // enfermedad pulmonar obstructiva crónica
    private char diabetes;
    private char presionAlta;
    private char asma;
    private char silvidoRespirarPechoApretado;
    private char tosSinFiebreResfriado;
    private char usaInhaladoresSpray;
    private char crisisAsma;
    private Integer cantidadCrisisAsma;
    private Integer vecesEnfermoEnfermedadesRes; //veces enfermo en el último año por cuadros o enfermedades respiratorias
    private char otrasEnfermedades;
    private String descOtrasEnfermedades;
    private char vacunaInfluenza;
    private Integer anioVacunaInfluenza;

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public char getEmancipado() {
        return emancipado;
    }

    public void setEmancipado(char emancipado) {
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

    public char getEstaEmbarazada() {
        return estaEmbarazada;
    }

    public void setEstaEmbarazada(char estaEmbarazada) {
        this.estaEmbarazada = estaEmbarazada;
    }

    public Integer getSemanasEmbarazo() {
        return semanasEmbarazo;
    }

    public void setSemanasEmbarazo(Integer semanasEmbarazo) {
        this.semanasEmbarazo = semanasEmbarazo;
    }

    public char getEsAlfabeto() {
        return esAlfabeto;
    }

    public void setEsAlfabeto(char esAlfabeto) {
        this.esAlfabeto = esAlfabeto;
    }

    public String getNivelEducacion() {
        return nivelEducacion;
    }

    public void setNivelEducacion(String nivelEducacion) {
        this.nivelEducacion = nivelEducacion;
    }

    public char getTrabaja() {
        return trabaja;
    }

    public void setTrabaja(char trabaja) {
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

    public char getVaNinoEscuela() {
        return vaNinoEscuela;
    }

    public void setVaNinoEscuela(char vaNinoEscuela) {
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

    public char getNinoTrabaja() {
        return ninoTrabaja;
    }

    public void setNinoTrabaja(char ninoTrabaja) {
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

    public char getPadreEnEstudio() {
        return padreEnEstudio;
    }

    public void setPadreEnEstudio(char padreEnEstudio) {
        this.padreEnEstudio = padreEnEstudio;
    }

    public Integer getCodigoPadreEstudio() {
        return codigoPadreEstudio;
    }

    public void setCodigoPadreEstudio(Integer codigoPadreEstudio) {
        this.codigoPadreEstudio = codigoPadreEstudio;
    }

    public char getPadreAlfabeto() {
        return padreAlfabeto;
    }

    public void setPadreAlfabeto(char padreAlfabeto) {
        this.padreAlfabeto = padreAlfabeto;
    }

    public String getNivelEducacionPadre() {
        return nivelEducacionPadre;
    }

    public void setNivelEducacionPadre(String nivelEducacionPadre) {
        this.nivelEducacionPadre = nivelEducacionPadre;
    }

    public char getTrabajaPadre() {
        return trabajaPadre;
    }

    public void setTrabajaPadre(char trabajaPadre) {
        this.trabajaPadre = trabajaPadre;
    }

    public String getTipoTrabajoPadre() {
        return tipoTrabajoPadre;
    }

    public void setTipoTrabajoPadre(String tipoTrabajoPadre) {
        this.tipoTrabajoPadre = tipoTrabajoPadre;
    }

    public char getMadreEnEstudio() {
        return madreEnEstudio;
    }

    public void setMadreEnEstudio(char madreEnEstudio) {
        this.madreEnEstudio = madreEnEstudio;
    }

    public Integer getCodigoMadreEstudio() {
        return codigoMadreEstudio;
    }

    public void setCodigoMadreEstudio(Integer codigoMadreEstudio) {
        this.codigoMadreEstudio = codigoMadreEstudio;
    }

    public char getMadreAlfabeto() {
        return madreAlfabeto;
    }

    public void setMadreAlfabeto(char madreAlfabeto) {
        this.madreAlfabeto = madreAlfabeto;
    }

    public String getNivelEducacionMadre() {
        return nivelEducacionMadre;
    }

    public void setNivelEducacionMadre(String nivelEducacionMadre) {
        this.nivelEducacionMadre = nivelEducacionMadre;
    }

    public char getTrabajaMadre() {
        return trabajaMadre;
    }

    public void setTrabajaMadre(char trabajaMadre) {
        this.trabajaMadre = trabajaMadre;
    }

    public String getTipoTrabajoMadre() {
        return tipoTrabajoMadre;
    }

    public void setTipoTrabajoMadre(String tipoTrabajoMadre) {
        this.tipoTrabajoMadre = tipoTrabajoMadre;
    }

    public char getFuma() {
        return fuma;
    }

    public void setFuma(char fuma) {
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

    public char getFumaDentroCasa() {
        return fumaDentroCasa;
    }

    public void setFumaDentroCasa(char fumaDentroCasa) {
        this.fumaDentroCasa = fumaDentroCasa;
    }

    public char getTuberculosisPulmonarActual() {
        return tuberculosisPulmonarActual;
    }

    public void setTuberculosisPulmonarActual(char tuberculosisPulmonarActual) {
        this.tuberculosisPulmonarActual = tuberculosisPulmonarActual;
    }

    public String getFechaDiagnosticoTubPulActual() {
        return fechaDiagnosticoTubPulActual;
    }

    public void setFechaDiagnosticoTubPulActual(String fechaDiagnosticoTubPulActual) {
        this.fechaDiagnosticoTubPulActual = fechaDiagnosticoTubPulActual;
    }

    public char getTomaTratamientoTubPulActual() {
        return tomaTratamientoTubPulActual;
    }

    public void setTomaTratamientoTubPulActual(char tomaTratamientoTubPulActual) {
        this.tomaTratamientoTubPulActual = tomaTratamientoTubPulActual;
    }

    public char getCompletoTratamientoTubPulActual() {
        return completoTratamientoTubPulActual;
    }

    public void setCompletoTratamientoTubPulActual(char completoTratamientoTubPulActual) {
        this.completoTratamientoTubPulActual = completoTratamientoTubPulActual;
    }

    public char getTuberculosisPulmonarPasado() {
        return tuberculosisPulmonarPasado;
    }

    public void setTuberculosisPulmonarPasado(char tuberculosisPulmonarPasado) {
        this.tuberculosisPulmonarPasado = tuberculosisPulmonarPasado;
    }

    public String getFechaDiagnosticoTubPulPasado() {
        return fechaDiagnosticoTubPulPasado;
    }

    public void setFechaDiagnosticoTubPulPasado(String fechaDiagnosticoTubPulPasado) {
        this.fechaDiagnosticoTubPulPasado = fechaDiagnosticoTubPulPasado;
    }

    public char getTomaTratamientoTubPulPasado() {
        return tomaTratamientoTubPulPasado;
    }

    public void setTomaTratamientoTubPulPasado(char tomaTratamientoTubPulPasado) {
        this.tomaTratamientoTubPulPasado = tomaTratamientoTubPulPasado;
    }

    public char getCompletoTratamientoTubPulPasado() {
        return completoTratamientoTubPulPasado;
    }

    public void setCompletoTratamientoTubPulPasado(char completoTratamientoTubPulPasado) {
        this.completoTratamientoTubPulPasado = completoTratamientoTubPulPasado;
    }

    public char getAlergiaRespiratoria() {
        return alergiaRespiratoria;
    }

    public void setAlergiaRespiratoria(char alergiaRespiratoria) {
        this.alergiaRespiratoria = alergiaRespiratoria;
    }

    public char getCardiopatia() {
        return cardiopatia;
    }

    public void setCardiopatia(char cardiopatia) {
        this.cardiopatia = cardiopatia;
    }

    public char getEnfermedadPulmonarOC() {
        return enfermedadPulmonarOC;
    }

    public void setEnfermedadPulmonarOC(char enfermedadPulmonarOC) {
        this.enfermedadPulmonarOC = enfermedadPulmonarOC;
    }

    public char getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(char diabetes) {
        this.diabetes = diabetes;
    }

    public char getPresionAlta() {
        return presionAlta;
    }

    public void setPresionAlta(char presionAlta) {
        this.presionAlta = presionAlta;
    }

    public char getAsma() {
        return asma;
    }

    public void setAsma(char asma) {
        this.asma = asma;
    }

    public char getSilvidoRespirarPechoApretado() {
        return silvidoRespirarPechoApretado;
    }

    public void setSilvidoRespirarPechoApretado(char silvidoRespirarPechoApretado) {
        this.silvidoRespirarPechoApretado = silvidoRespirarPechoApretado;
    }

    public char getTosSinFiebreResfriado() {
        return tosSinFiebreResfriado;
    }

    public void setTosSinFiebreResfriado(char tosSinFiebreResfriado) {
        this.tosSinFiebreResfriado = tosSinFiebreResfriado;
    }

    public char getUsaInhaladoresSpray() {
        return usaInhaladoresSpray;
    }

    public void setUsaInhaladoresSpray(char usaInhaladoresSpray) {
        this.usaInhaladoresSpray = usaInhaladoresSpray;
    }

    public char getCrisisAsma() {
        return crisisAsma;
    }

    public void setCrisisAsma(char crisisAsma) {
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

    public char getOtrasEnfermedades() {
        return otrasEnfermedades;
    }

    public void setOtrasEnfermedades(char otrasEnfermedades) {
        this.otrasEnfermedades = otrasEnfermedades;
    }

    public String getDescOtrasEnfermedades() {
        return descOtrasEnfermedades;
    }

    public void setDescOtrasEnfermedades(String descOtrasEnfermedades) {
        this.descOtrasEnfermedades = descOtrasEnfermedades;
    }

    public char getVacunaInfluenza() {
        return vacunaInfluenza;
    }

    public void setVacunaInfluenza(char vacunaInfluenza) {
        this.vacunaInfluenza = vacunaInfluenza;
    }

    public Integer getAnioVacunaInfluenza() {
        return anioVacunaInfluenza;
    }

    public void setAnioVacunaInfluenza(Integer anioVacunaInfluenza) {
        this.anioVacunaInfluenza = anioVacunaInfluenza;
    }

    @Override
    public String toString() {
        return "EncuestaParticipante{" + participante.getCodigo() +
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
