package tbox.data.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "os_version")
public class OSVersion implements Serializable {

	private static final long serialVersionUID = 5250779016216872974L;

	@Id
	@Column(name = "serial_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer serialNo;

	@Column(name = "version")
	private String version;

	@Column(name = "description")
	private String description;

	@Column(name = "link")
	private String link;

	@Column(name = "publish_time")	
	private Timestamp publishTime;
	
	public OSVersion() {
	}

	public OSVersion(Integer serialNo, String version, String description, String link, Timestamp publishTime) {
		super();
		this.serialNo = serialNo;
		this.version = version;
		this.description = description;
		this.link = link;
		this.publishTime = publishTime;
	}
	
	

	public OSVersion(String version, String description, String link, Timestamp publishTime) {
		super();
		this.version = version;
		this.description = description;
		this.link = link;
		this.publishTime = publishTime;
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
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the publishTime
	 */
	public Timestamp getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime
	 *            the publishTime to set
	 */
	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OSVersion [serialNo=");
		builder.append(serialNo);
		builder.append(", version=");
		builder.append(version);
		builder.append(", description=");
		builder.append(description);
		builder.append(", link=");
		builder.append(link);
		builder.append(", publishTime=");
		builder.append(publishTime);
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
		OSVersion other = (OSVersion) obj;
		if (serialNo == null) {
			if (other.serialNo != null)
				return false;
		} else if (!serialNo.equals(other.serialNo))
			return false;
		return true;
	}

}
