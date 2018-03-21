package tbox.service.entity;

import java.util.List;

import net.dongliu.apk.parser.bean.ApkMeta;

public class ApkInfoEntity {

	private String appName;
	private String pkgName;
	private long versionCode;
	private String versionName;
	private List<String> iconPath;
	
	
	public ApkInfoEntity(ApkMeta meta) {
		this.appName = meta.getName();
		this.pkgName = meta.getPackageName();
		this.versionCode = meta.getVersionCode();
		this.versionName = meta.getVersionName();	
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
	 * @return the pkgName
	 */
	public String getPkgName() {
		return pkgName;
	}

	/**
	 * @param pkgName
	 *            the pkgName to set
	 */
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	/**
	 * @return the versionCode
	 */
	public long getVersionCode() {
		return versionCode;
	}

	/**
	 * @param versionCode
	 *            the versionCode to set
	 */
	public void setVersionCode(long versionCode) {
		this.versionCode = versionCode;
	}

	/**
	 * @return the versionName
	 */
	public String getVersionName() {
		return versionName;
	}

	/**
	 * @param versionName
	 *            the versionName to set
	 */
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	/**
	 * @return the iconPath
	 */
	public List<String> getIconPath() {
		return iconPath;
	}

	/**
	 * @param iconPath
	 *            the iconPath to set
	 */
	public void setIconPath(List<String> iconPath) {
		this.iconPath = iconPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApkInfoEntity [appName=");
		builder.append(appName);
		builder.append(", pkgName=");
		builder.append(pkgName);
		builder.append(", versionCode=");
		builder.append(versionCode);
		builder.append(", versionName=");
		builder.append(versionName);
		builder.append(", iconPath=");
		builder.append(iconPath);
		builder.append("]");
		return builder.toString();
	}

}
