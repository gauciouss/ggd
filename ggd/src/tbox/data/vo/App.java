package tbox.data.vo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "app")
public class App implements Serializable {

	private static final long serialVersionUID = -2962129287213476303L;

	@Id
	@Column(name = "app_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String appId;

	@Column(name = "app_name")
	private String appName;

	@Column(name = "app_eng_name")
	private String appEngName;

	@ManyToOne
	@JoinColumn(name = "clz_id", insertable = false, updatable = false)
	private AppClz clz;

	@Column(name = "icon_path")
	private String iconPath;

	@Column(name = "app_desc")
	private String appDesc;
	
	@OneToMany
	@JoinColumn(name = "app_id", insertable = false, updatable = false)
	private Set<AppVersion> versions;

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
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the appEngName
	 */
	public String getAppEngName() {
		return appEngName;
	}

	/**
	 * @param appEngName
	 *            the appEngName to set
	 */
	public void setAppEngName(String appEngName) {
		this.appEngName = appEngName;
	}

	/**
	 * @return the clz
	 */
	public AppClz getClz() {
		return clz;
	}

	/**
	 * @param clz
	 *            the clz to set
	 */
	public void setClz(AppClz clz) {
		this.clz = clz;
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
	 * @return the appDesc
	 */
	public String getAppDesc() {
		return appDesc;
	}

	/**
	 * @param appDesc
	 *            the appDesc to set
	 */
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}
	
	public Set<AppVersion> getVersions() {
		return versions;
	}
	
	public void setVersions(Set<AppVersion> versions) {
		this.versions = versions;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("App [appId=");
		builder.append(appId);
		builder.append(", appName=");
		builder.append(appName);
		builder.append(", appEngName=");
		builder.append(appEngName);
		builder.append(", clz=");
		builder.append(clz);
		builder.append(", iconPath=");
		builder.append(iconPath);
		builder.append(", appDesc=");
		builder.append(appDesc);
		builder.append(", versions=");
		builder.append(versions);
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
		App other = (App) obj;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		return true;
	}

}
