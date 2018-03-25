package tbox.data.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppEntity implements Serializable {

	private static final long serialVersionUID = -5090068559724834508L;

	@Id
	@Column(name = "app_id")
	private String appId;

	@Column(name = "icon_path")
	private String iconPath;

	@Column(name = "clz_id")
	private Integer clzId;

	@Column(name = "clz_name")
	private String clzName;

	@Column(name = "app_name")
	private String name;
	
	@Column(name = "app_eng_name")
	private String engName;

	@Column(name = "link")
	private String link;

	@Column(name = "version")
	private String version;

	@Column(name = "publish_time")
	private Timestamp publishTime;
	
	@Column(name = "pkg_name")
	private String pkgName;
	
	@Column(name = "app_desc")
	private String desc;

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * @param iconPath
	 *            the iconPath to set
	 */
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	/**
	 * @return the clzId
	 */
	public Integer getClzId() {
		return clzId;
	}

	/**
	 * @param clzId
	 *            the clzId to set
	 */
	public void setClzId(Integer clzId) {
		this.clzId = clzId;
	}

	/**
	 * @return the clzName
	 */
	public String getClzName() {
		return clzName;
	}

	/**
	 * @param clzName
	 *            the clzName to set
	 */
	public void setClzName(String clzName) {
		this.clzName = clzName;
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

	

	/**
	 * @return the pkgName
	 */
	public String getPkgName() {
		return pkgName;
	}

	/**
	 * @param pkgName the pkgName to set
	 */
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	
	public String getEngName() {
		return engName;
	}
	
	public void setEngName(String engName) {
		this.engName = engName;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppEntity [appId=");
		builder.append(appId);
		builder.append(", iconPath=");
		builder.append(iconPath);
		builder.append(", clzId=");
		builder.append(clzId);
		builder.append(", clzName=");
		builder.append(clzName);
		builder.append(", name=");
		builder.append(name);
		builder.append(", engName=");
		builder.append(engName);
		builder.append(", link=");
		builder.append(link);
		builder.append(", version=");
		builder.append(version);
		builder.append(", publishTime=");
		builder.append(publishTime);
		builder.append(", pkgName=");
		builder.append(pkgName);
		builder.append(", desc=");
		builder.append(desc);
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
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
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
		AppEntity other = (AppEntity) obj;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		return true;
	}

}
