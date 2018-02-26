package tbox.data.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	@Column(name = "machine_sn")
	private String machineSN;

	@Id
	@Column(name = "wifi_mac")
	private String wifiMAC;

	@Id
	@Column(name = "ethernet_mac")
	private String ethernetMAC;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "areaId", insertable = false, updatable = false)
	private Area area;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cid", insertable = false, updatable = false)
	private Company company;

	@Column(name = "last_login_time")
	private Timestamp lastLoginTime;

	@Column(name = "login_ip")
	private String loginIP;

	@Column(name = "phone")
	private String phone;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "start_date")
	private Timestamp startDate;

	@Column(name = "isEnabled")
	private boolean isEnabled;

	@Column(name = "authorized_start_date")
	private Timestamp authorizedStartDate;

	@Column(name = "authorized_end_date")
	private Timestamp authorizedEndDate;

	public MachineBox() {
	}

	public MachineBox(String machineSN, String wifiMAC, String ethernetMAC, Area area, Company company,
			Timestamp lastLoginTime, String loginIP, String phone, String customerName, Timestamp startDate,
			boolean isEnabled, Timestamp authorizedStartDate, Timestamp authorizedEndDate) {
		super();
		this.machineSN = machineSN;
		this.wifiMAC = wifiMAC;
		this.ethernetMAC = ethernetMAC;
		this.area = area;
		this.company = company;
		this.lastLoginTime = lastLoginTime;
		this.loginIP = loginIP;
		this.phone = phone;
		this.customerName = customerName;
		this.startDate = startDate;
		this.isEnabled = isEnabled;
		this.authorizedStartDate = authorizedStartDate;
		this.authorizedEndDate = authorizedEndDate;
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
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MachineBox [machineSN=");
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
