package tbox.data.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ggd.auth.vo.AdmGroup;

@Entity
@Table(name = "company")
public class Company implements Serializable {

	private static final long serialVersionUID = 2790177970557710941L;

	@Id
	@Column(name = "EIN")
	private String EIN;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "area_id", insertable = false, updatable = false)
	private Area area;

	@Column(name = "logo_url")
	private String logoURL;

	@Column(name = "background_url")
	private String backgroundURL;

	@OneToOne
	@JoinColumn(name = "group_id", updatable = false, insertable = false)
	private AdmGroup group;

	public Company() {
	}

	public Company(String EIN, String name, Area area, String logoURL, String backgroundURL, AdmGroup group) {
		super();
		this.EIN = EIN;
		this.name = name;
		this.area = area;
		this.logoURL = logoURL;
		this.backgroundURL = backgroundURL;
		this.group = group;
	}

	public String getEIN() {
		return EIN;
	}

	public void setEIN(String eIN) {
		EIN = eIN;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the logoURL
	 */
	public String getLogoURL() {
		return logoURL;
	}

	/**
	 * @param logoURL
	 *            the logoURL to set
	 */
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	/**
	 * @return the backgroundURL
	 */
	public String getBackgroundURL() {
		return backgroundURL;
	}

	/**
	 * @param backgroundURL
	 *            the backgroundURL to set
	 */
	public void setBackgroundURL(String backgroundURL) {
		this.backgroundURL = backgroundURL;
	}

	/**
	 * @return the group
	 */
	public AdmGroup getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(AdmGroup group) {
		this.group = group;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Company [EIN=");
		builder.append(EIN);
		builder.append(", name=");
		builder.append(name);
		builder.append(", area=");
		builder.append(area);
		builder.append(", logoURL=");
		builder.append(logoURL);
		builder.append(", backgroundURL=");
		builder.append(backgroundURL);
		builder.append(", group=");
		builder.append(group);
		builder.append("]");
		return builder.toString();
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
		result = prime * result + ((EIN == null) ? 0 : EIN.hashCode());
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
		Company other = (Company) obj;
		if (EIN == null) {
			if (other.EIN != null)
				return false;
		} else if (!EIN.equals(other.EIN))
			return false;
		return true;
	}

}
