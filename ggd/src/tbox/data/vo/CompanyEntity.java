package tbox.data.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CompanyEntity {

	@Id
	@Column(name = "EIN")
	private String EIN;

	@Column(name = "name")
	private String name;

	@Column(name = "area")
	private String area;

	@Column(name = "isEnabled")
	private boolean isEnabled;

	@Column(name = "isApproved")
	private boolean isApproved;

	/**
	 * @return the eIN
	 */
	public String getEIN() {
		return EIN;
	}

	/**
	 * @param eIN
	 *            the eIN to set
	 */
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
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
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
	 * @return the isApproved
	 */
	public boolean isApproved() {
		return isApproved;
	}

	/**
	 * @param isApproved
	 *            the isApproved to set
	 */
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompanyEntity [EIN=");
		builder.append(EIN);
		builder.append(", name=");
		builder.append(name);
		builder.append(", area=");
		builder.append(area);
		builder.append(", isEnabled=");
		builder.append(isEnabled);
		builder.append(", isApproved=");
		builder.append(isApproved);
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
		CompanyEntity other = (CompanyEntity) obj;
		if (EIN == null) {
			if (other.EIN != null)
				return false;
		} else if (!EIN.equals(other.EIN))
			return false;
		return true;
	}

}
