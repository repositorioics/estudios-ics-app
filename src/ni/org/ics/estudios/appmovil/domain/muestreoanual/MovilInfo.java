package ni.org.ics.estudios.appmovil.domain.muestreoanual;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class MovilInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Manejo ODK
	private Integer idInstancia;
	private String instancePath;
	private String estado;
	private Boolean eliminado;
	private String ultimoCambio;
	//Metadata del formulario movil
	private String start;
	private String end;
	private String deviceid;
	private String simserial;
	private String phonenumber;
	private Date today;	
	private String username; 
	private Integer recurso1;
	private Integer recurso2;	
	public MovilInfo(){
		
	}
	
	public MovilInfo(Integer idInstancia, String instancePath, String estado
			, String ultimoCambio, String start, String end, String deviceid,
			String simserial, String phonenumber, Date today, String username, Boolean eliminado,
			Integer recurso1, Integer recurso2){
		
		this.setIdInstancia(idInstancia);
		this.setInstancePath(instancePath);
		this.setEstado(estado);
		this.setUltimoCambio(ultimoCambio);
		this.setStart(start);
		this.setEnd(end);
		this.setDeviceid(deviceid);
		this.setSimserial(simserial);
		this.setPhonenumber(phonenumber);
		this.setToday(today);
		this.setUsername(username);
		this.setEliminado(eliminado);
		this.setRecurso1(recurso1);
		this.setRecurso2(recurso2);
	}
	
	public Integer getIdInstancia() {
		return idInstancia;
	}

	public void setIdInstancia(Integer idInstancia) {
		this.idInstancia = idInstancia;
	}

	
	public String getInstancePath() {
		return instancePath;
	}

	public void setInstancePath(String instancePath) {
		this.instancePath = instancePath;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUltimoCambio() {
		return ultimoCambio;
	}

	public void setUltimoCambio(String ultimoCambio) {
		this.ultimoCambio = ultimoCambio;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getSimserial() {
		return simserial;
	}

	public void setSimserial(String simserial) {
		this.simserial = simserial;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}
	
	
	public Integer getRecurso1() {
		return recurso1;
	}

	public void setRecurso1(Integer recurso1) {
		this.recurso1 = recurso1;
	}
		
	
	public Integer getRecurso2() {
		return recurso2;
	}

	public void setRecurso2(Integer recurso2) {
		this.recurso2 = recurso2;
	}
	
	@Override
	public String toString(){
		DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
		return mediumDf.format(this.getToday());
	}

}
