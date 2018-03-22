package tbox.data.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class MachineEntity implements Serializable {

	private static final long serialVersionUID = -1934297560517294307L;

	@Id
	@Column(name = "serial_no")
	private String serialNo;
	
	@Column(name = "machine_sn")
	private String machineSN;
	
	@Column(name = "ethernet_mac")
	private String mac;
	
	@Column(name = "wifi_mac")
	private String wifiMac;
	
	@Column(name = "name")
	private String companyName;
	
	@Transient
	private String EIN;
	
	@Transient
	private Integer areaId;
	
	@Column(name = "isEnabled")
	private Boolean isEnabled;
	
	@Column(name = "start_date")
	private Timestamp startDate;
	
	@Column(name = "authorized_start_date")
	private Timestamp authStartDate;
	
	@Column(name = "authorized_end_date")
	private Timestamp authEndDate;
	
	public MachineEntity() {}
	
	

	public MachineEntity(String machineSN, String mac, String wifiMac, String eIN, Integer areaId, Timestamp startDate,
			Timestamp authStartDate, Timestamp authEndDate) {
		super();
		this.machineSN = machineSN;
		this.mac = mac;
		this.wifiMac = wifiMac;
		EIN = eIN;
		this.areaId = areaId;
		this.startDate = startDate;
		this.authStartDate = authStartDate;
		this.authEndDate = authEndDate;
	}



	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the machineSN
	 */
	public String getMachineSN() {
		return machineSN;
	}

	/**
	 * @param machineSN the machineSN to set
	 */
	public void setMachineSN(String machineSN) {
		this.machineSN = machineSN;
	}

	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * @return the wifiMac
	 */
	public String getWifiMac() {
		return wifiMac;
	}

	/**
	 * @param wifiMac the wifiMac to set
	 */
	public void setWifiMac(String wifiMac) {
		this.wifiMac = wifiMac;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the isEnabled
	 */
	public Boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the startDate
	 */
	public Timestamp getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the authStartDate
	 */
	public Timestamp getAuthStartDate() {
		return authStartDate;
	}

	/**
	 * @param authStartDate the authStartDate to set
	 */
	public void setAuthStartDate(Timestamp authStartDate) {
		this.authStartDate = authStartDate;
	}

	/**
	 * @return the authEndDate
	 */
	public Timestamp getAuthEndDate() {
		return authEndDate;
	}

	/**
	 * @param authEndDate the authEndDate to set
	 */
	public void setAuthEndDate(Timestamp authEndDate) {
		this.authEndDate = authEndDate;
	}
	
	public String getEIN() {
		return EIN;
	}
	
	public void setEIN(String eIN) {
		EIN = eIN;
	}
	
	public Integer getAreaId() {
		return areaId;
	}
	
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MachineEntity [serialNo=");
		builder.append(serialNo);
		builder.append(", machineSN=");
		builder.append(machineSN);
		builder.append(", mac=");
		builder.append(mac);
		builder.append(", wifiMac=");
		builder.append(wifiMac);
		builder.append(", companyName=");
		builder.append(companyName);
		builder.append(", isEnabled=");
		builder.append(isEnabled);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", authStartDate=");
		builder.append(authStartDate);
		builder.append(", authEndDate=");
		builder.append(authEndDate);
		builder.append("]");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MachineEntity other = (MachineEntity) obj;
		if (serialNo == null) {
			if (other.serialNo != null)
				return false;
		} else if (!serialNo.equals(other.serialNo))
			return false;
		return true;
	}
	
	
}
