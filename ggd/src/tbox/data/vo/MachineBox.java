package tbox.data.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "machine_box")
public class MachineBox implements Serializable {

	private static final long serialVersionUID = -3637417535721807561L;

	@Id
	@Column(name = "serial_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer serialNo;

	@Column(name = "machine_sn")
	private String machineSN;

	@Column(name = "wifi_mac")
	private String wifiMAC;

	@Column(name = "ethernet_mac")
	private String ethernetMAC;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "area_id", insertable = false, updatable = false)
	private Area area;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EIN", insertable = false, updatable = false, nullable = true)
	private Company company;

	@Column(name = "last_login_time")
	private Timestamp lastLoginTime;

	@Column(name = "login_ip")
	private String loginIP;

	@Column(name = "phone")
	private String phone;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "email")
	private String email;

	@Column(name = "start_date")
	private Timestamp startDate;

	@Column(name = "isEnabled")
	private Boolean isEnabled;

	@Column(name = "authorized_start_date")
	private Timestamp authorizedStartDate;

	@Column(name = "authorized_end_date")
	private Timestamp authorizedEndDate;
	
	@Column(name = "password")
	private String password;

	public MachineBox() {
	}

	public MachineBox(Integer serialNo, String machineSN, String wifiMAC, String ethernetMAC, Area area,
			Company company, Timestamp lastLoginTime, String loginIP, String phone, String customerName, String email,
			Timestamp startDate, Boolean isEnabled, Timestamp authorizedStartDate, Timestamp authorizedEndDate) {
		super();
		this.serialNo = serialNo;
		this.machineSN = machineSN;
		this.wifiMAC = wifiMAC;
		this.ethernetMAC = ethernetMAC;
		this.area = area;
		this.company = company;
		this.lastLoginTime = lastLoginTime;
		this.loginIP = loginIP;
		this.phone = phone;
		this.customerName = customerName;
		this.email = email;
		this.startDate = startDate;
		this.isEnabled = isEnabled;
		this.authorizedStartDate = authorizedStartDate;
		this.authorizedEndDate = authorizedEndDate;
	}

	/**
	 * @return the serialNo
	 */
	public Integer getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the machineSN
	 */
	public String getMachineSN() {
		return machineSN;
	}

	/**
	 * @param machineSN
	 *            the machineSN to set
	 */
	public void setMachineSN(String machineSN) {
		this.machineSN = machineSN;
	}

	/**
	 * @return the wifiMAC
	 */
	public String getWifiMAC() {
		return wifiMAC;
	}

	/**
	 * @param wifiMAC
	 *            the wifiMAC to set
	 */
	public void setWifiMAC(String wifiMAC) {
		this.wifiMAC = wifiMAC;
	}

	/**
	 * @return the ethernetMAC
	 */
	public String getEthernetMAC() {
		return ethernetMAC;
	}

	/**
	 * @param ethernetMAC
	 *            the ethernetMAC to set
	 */
	public void setEthernetMAC(String ethernetMAC) {
		this.ethernetMAC = ethernetMAC;
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime
	 *            the lastLoginTime to set
	 */
	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the loginIP
	 */
	public String getLoginIP() {
		return loginIP;
	}

	/**
	 * @param loginIP
	 *            the loginIP to set
	 */
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the startDate
	 */
	public Timestamp getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the isEnabled
	 */
	public Boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */
	public void setEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the authorizedStartDate
	 */
	public Timestamp getAuthorizedStartDate() {
		return authorizedStartDate;
	}

	/**
	 * @param authorizedStartDate
	 *            the authorizedStartDate to set
	 */
	public void setAuthorizedStartDate(Timestamp authorizedStartDate) {
		this.authorizedStartDate = authorizedStartDate;
	}

	/**
	 * @return the authorizedEndDate
	 */
	public Timestamp getAuthorizedEndDate() {
		return authorizedEndDate;
	}

	/**
	 * @param authorizedEndDate
	 *            the authorizedEndDate to set
	 */
	public void setAuthorizedEndDate(Timestamp authorizedEndDate) {
		this.authorizedEndDate = authorizedEndDate;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		MachineBox other = (MachineBox) obj;
		if (serialNo == null) {
			if (other.serialNo != null)
				return false;
		} else if (!serialNo.equals(other.serialNo))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MachineBox [serialNo=");
		builder.append(serialNo);
		builder.append(", machineSN=");
		builder.append(machineSN);
		builder.append(", wifiMAC=");
		builder.append(wifiMAC);
		builder.append(", ethernetMAC=");
		builder.append(ethernetMAC);
		builder.append(", area=");
		builder.append(area);
		builder.append(", company=");
		builder.append(company);
		builder.append(", lastLoginTime=");
		builder.append(lastLoginTime);
		builder.append(", loginIP=");
		builder.append(loginIP);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", isEnabled=");
		builder.append(isEnabled);
		builder.append(", authorizedStartDate=");
		builder.append(authorizedStartDate);
		builder.append(", authorizedEndDate=");
		builder.append(authorizedEndDate);
		builder.append("]");
		return builder.toString();
	}
	
	

}
