package ni.org.ics.estudios.appmovil.cohortefamilia.forms;

import android.content.res.Resources;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;

/**
 * Created by Miguel Salinas on 5/18/2017.
 * V1.0
 */
public class EncuestaParticipanteFormLabels {

    protected String labelInicio;
    protected String estaEmbarazada;
    protected String semanasEmbarazo;
    protected String esAlfabeto;
    protected String nivelEducacion;
    protected String trabaja;
    protected String tipoTrabajo;
    protected String ocupacionActual;
    protected String vaNinoEscuela;
    protected String gradoCursa;
    protected String turno;
    protected String centroEstudio;
    protected String nombreCentroEstudio;
    protected String dondeCuidanNino;
    protected String ninoTrabaja;
    protected String ocupacionActualNino;
    protected String cantNinosLugarCuidan;
    protected String conQuienViveNino;
    protected String descOtroViveNino;
    protected String padreEnEstudio;
    protected String codigoPadreEstudio;
    protected String padreAlfabeto;
    protected String nivelEducacionPadre;
    protected String trabajaPadre;
    protected String tipoTrabajoPadre;
    protected String madreEnEstudio;
    protected String codigoMadreEstudio;
    protected String madreAlfabeto;
    protected String nivelEducacionMadre;
    protected String trabajaMadre;
    protected String tipoTrabajoMadre;
    protected String fuma;
    protected String periodicidadFuma;
    protected String cantidadCigarrillos;
    protected String fumaDentroCasa;
    protected String tuberculosisPulmonarActual;
    protected String fechaDiagnosticoTubPulActual;
    protected String anioFechaDiagnosticoTubPulActual;
    protected String mesFechaDiagnosticoTubPulActual;
    protected String tomaTratamientoTubPulActual;
    protected String completoTratamientoTubPulActual;
    protected String tuberculosisPulmonarPasado;
    protected String fechaDiagnosticoTubPulPasadoSn;
    protected String fechaDiagnosticoTubPulPasado;
    protected String anioFechaDiagnosticoTubPulPasado;
    protected String mesFechaDiagnosticoTubPulPasado;
    protected String tomaTratamientoTubPulPasado;
    protected String completoTratamientoTubPulPasado;
    protected String alergiaRespiratoria;
    protected String cardiopatia;
    protected String enfermedadPulmonarOC;
    protected String diabetes;
    protected String presionAlta;
    protected String asma;
    protected String silbidoRespirarPechoApretado;
    protected String tosSinFiebreResfriado;
    protected String usaInhaladoresSpray;
    protected String crisisAsma;
    protected String cantidadCrisisAsma;
    protected String vecesEnfermoEnfermedadesRes;
    protected String otrasEnfermedades;
    protected String descOtrasEnfermedades;
    protected String vacunaInfluenza;
    protected String anioVacunaInfluenza;
    protected String silbidoRespirarPechoApretadoHint;
    protected String tosSinFiebreResfriadoHint;
    protected String usaInhaladoresSprayHint;


    public EncuestaParticipanteFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        labelInicio = res.getString(R.string.new_participant_survey);
        estaEmbarazada = res.getString(R.string.estaEmbarazada);
        semanasEmbarazo = res.getString(R.string.semanasEmbarazo);
        esAlfabeto = res.getString(R.string.esAlfabeto);
        nivelEducacion = res.getString(R.string.nivelEducacion);
        trabaja = res.getString(R.string.trabaja);
        tipoTrabajo = res.getString(R.string.tipoTrabajo);
        ocupacionActual = res.getString(R.string.ocupacionActual);
        vaNinoEscuela = res.getString(R.string.vaNinoEscuela);
        gradoCursa = res.getString(R.string.gradoCursa);
        turno = res.getString(R.string.turno);
        centroEstudio = res.getString(R.string.centroEstudio);
        nombreCentroEstudio = res.getString(R.string.nombreCentroEstudio);
        dondeCuidanNino = res.getString(R.string.dondeCuidanNino);
        ninoTrabaja = res.getString(R.string.ninoTrabaja);
        ocupacionActualNino = res.getString(R.string.ocupacionActualNino);
        cantNinosLugarCuidan = res.getString(R.string.cantNinosLugarCuidan);
        conQuienViveNino = res.getString(R.string.conQuienViveNino);
        descOtroViveNino = res.getString(R.string.descOtroViveNino);
        padreEnEstudio = res.getString(R.string.padreEnEstudio);
        codigoPadreEstudio = res.getString(R.string.codigoPadreEstudio);
        padreAlfabeto = res.getString(R.string.padreAlfabeto);
        nivelEducacionPadre = res.getString(R.string.nivelEducacionPadre);
        trabajaPadre = res.getString(R.string.trabajaPadre);
        tipoTrabajoPadre = res.getString(R.string.tipoTrabajoPadre);
        madreEnEstudio = res.getString(R.string.madreEnEstudio);
        codigoMadreEstudio = res.getString(R.string.codigoMadreEstudio);
        madreAlfabeto = res.getString(R.string.madreAlfabeto);
        nivelEducacionMadre = res.getString(R.string.nivelEducacionMadre);
        trabajaMadre = res.getString(R.string.trabajaMadre);
        tipoTrabajoMadre = res.getString(R.string.tipoTrabajoMadre);
        fuma = res.getString(R.string.fuma);
        periodicidadFuma = res.getString(R.string.periodicidadFuma);
        cantidadCigarrillos = res.getString(R.string.cantidadCigarrillos);
        fumaDentroCasa = res.getString(R.string.fumaDentroCasa);
        tuberculosisPulmonarActual = res.getString(R.string.tuberculosisPulmonarActual);
        fechaDiagnosticoTubPulActual = res.getString(R.string.fechaDiagnosticoTubPulActual);
        anioFechaDiagnosticoTubPulActual = res.getString(R.string.anioFechaDiagnosticoTubPulActual);
        mesFechaDiagnosticoTubPulActual = res.getString(R.string.mesFechaDiagnosticoTubPulActual);
        tomaTratamientoTubPulActual = res.getString(R.string.tomaTratamientoTubPulActual);
        completoTratamientoTubPulActual = res.getString(R.string.completoTratamientoTubPulActual);
        tuberculosisPulmonarPasado = res.getString(R.string.tuberculosisPulmonarPasado);
        fechaDiagnosticoTubPulPasadoSn = res.getString(R.string.fechaDiagnosticoTubPulPasadoSn);
        fechaDiagnosticoTubPulPasado = res.getString(R.string.fechaDiagnosticoTubPulPasado);
        anioFechaDiagnosticoTubPulPasado = res.getString(R.string.anioFechaDiagnosticoTubPulPasado);
        mesFechaDiagnosticoTubPulPasado = res.getString(R.string.mesFechaDiagnosticoTubPulPasado);
        tomaTratamientoTubPulPasado = res.getString(R.string.tomaTratamientoTubPulPasado);
        completoTratamientoTubPulPasado = res.getString(R.string.completoTratamientoTubPulPasado);
        alergiaRespiratoria = res.getString(R.string.alergiaRespiratoria);
        cardiopatia = res.getString(R.string.cardiopatia);
        enfermedadPulmonarOC = res.getString(R.string.enfermedadPulmonarOC);
        diabetes = res.getString(R.string.diabetes);
        presionAlta = res.getString(R.string.presionAlta);
        asma = res.getString(R.string.asma);
        silbidoRespirarPechoApretado = res.getString(R.string.silbidoRespirarPechoApretado);
        tosSinFiebreResfriado = res.getString(R.string.tosSinFiebreResfriado);
        usaInhaladoresSpray = res.getString(R.string.usaInhaladoresSpray);
        crisisAsma = res.getString(R.string.crisisAsma);
        cantidadCrisisAsma = res.getString(R.string.cantidadCrisisAsma);
        vecesEnfermoEnfermedadesRes = res.getString(R.string.vecesEnfermoEnfermedadesRes);
        otrasEnfermedades = res.getString(R.string.otrasEnfermedades);
        descOtrasEnfermedades = res.getString(R.string.descOtrasEnfermedades);
        vacunaInfluenza = res.getString(R.string.vacunaInfluenza);
        anioVacunaInfluenza = res.getString(R.string.anioVacunaInfluenza);
        silbidoRespirarPechoApretadoHint = res.getString(R.string.silbidoRespirarPechoApretadoHint);
        tosSinFiebreResfriadoHint = res.getString(R.string.tosSinFiebreResfriadoHint);
        usaInhaladoresSprayHint = res.getString(R.string.usaInhaladoresSprayHint);
    }

    public String getLabelInicio() {
        return labelInicio;
    }

    public void setLabelInicio(String labelInicio) {
        this.labelInicio = labelInicio;
    }

    public String getEstaEmbarazada() {
        return estaEmbarazada;
    }

    public void setEstaEmbarazada(String estaEmbarazada) {
        this.estaEmbarazada = estaEmbarazada;
    }

    public String getSemanasEmbarazo() {
        return semanasEmbarazo;
    }

    public void setSemanasEmbarazo(String semanasEmbarazo) {
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

    public String getCentroEstudio() {
        return centroEstudio;
    }

    public void setCentroEstudio(String centroEstudio) {
        this.centroEstudio = centroEstudio;
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

    public String getCantNinosLugarCuidan() {
        return cantNinosLugarCuidan;
    }

    public void setCantNinosLugarCuidan(String cantNinosLugarCuidan) {
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

    public String getCantidadCigarrillos() {
        return cantidadCigarrillos;
    }

    public void setCantidadCigarrillos(String cantidadCigarrillos) {
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

    public String getCantidadCrisisAsma() {
        return cantidadCrisisAsma;
    }

    public void setCantidadCrisisAsma(String cantidadCrisisAsma) {
        this.cantidadCrisisAsma = cantidadCrisisAsma;
    }

    public String getVecesEnfermoEnfermedadesRes() {
        return vecesEnfermoEnfermedadesRes;
    }

    public void setVecesEnfermoEnfermedadesRes(String vecesEnfermoEnfermedadesRes) {
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

    public String getAnioVacunaInfluenza() {
        return anioVacunaInfluenza;
    }

    public void setAnioVacunaInfluenza(String anioVacunaInfluenza) {
        this.anioVacunaInfluenza = anioVacunaInfluenza;
    }

    public String getAnioFechaDiagnosticoTubPulActual() {
        return anioFechaDiagnosticoTubPulActual;
    }

    public void setAnioFechaDiagnosticoTubPulActual(String anioFechaDiagnosticoTubPulActual) {
        this.anioFechaDiagnosticoTubPulActual = anioFechaDiagnosticoTubPulActual;
    }

    public String getMesFechaDiagnosticoTubPulActual() {
        return mesFechaDiagnosticoTubPulActual;
    }

    public void setMesFechaDiagnosticoTubPulActual(String mesFechaDiagnosticoTubPulActual) {
        this.mesFechaDiagnosticoTubPulActual = mesFechaDiagnosticoTubPulActual;
    }

    public String getFechaDiagnosticoTubPulPasadoSn() {
        return fechaDiagnosticoTubPulPasadoSn;
    }

    public void setFechaDiagnosticoTubPulPasadoSn(String fechaDiagnosticoTubPulPasadoSn) {
        this.fechaDiagnosticoTubPulPasadoSn = fechaDiagnosticoTubPulPasadoSn;
    }

    public String getAnioFechaDiagnosticoTubPulPasado() {
        return anioFechaDiagnosticoTubPulPasado;
    }

    public void setAnioFechaDiagnosticoTubPulPasado(String anioFechaDiagnosticoTubPulPasado) {
        this.anioFechaDiagnosticoTubPulPasado = anioFechaDiagnosticoTubPulPasado;
    }

    public String getMesFechaDiagnosticoTubPulPasado() {
        return mesFechaDiagnosticoTubPulPasado;
    }

    public void setMesFechaDiagnosticoTubPulPasado(String mesFechaDiagnosticoTubPulPasado) {
        this.mesFechaDiagnosticoTubPulPasado = mesFechaDiagnosticoTubPulPasado;
    }

    public String getSilbidoRespirarPechoApretadoHint() {
        return silbidoRespirarPechoApretadoHint;
    }

    public void setSilbidoRespirarPechoApretadoHint(String silbidoRespirarPechoApretadoHint) {
        this.silbidoRespirarPechoApretadoHint = silbidoRespirarPechoApretadoHint;
    }

    public String getTosSinFiebreResfriadoHint() {
        return tosSinFiebreResfriadoHint;
    }

    public void setTosSinFiebreResfriadoHint(String tosSinFiebreResfriadoHint) {
        this.tosSinFiebreResfriadoHint = tosSinFiebreResfriadoHint;
    }

    public String getUsaInhaladoresSprayHint() {
        return usaInhaladoresSprayHint;
    }

    public void setUsaInhaladoresSprayHint(String usaInhaladoresSprayHint) {
        this.usaInhaladoresSprayHint = usaInhaladoresSprayHint;
    }
}
